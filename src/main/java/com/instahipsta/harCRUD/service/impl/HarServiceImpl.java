package com.instahipsta.harCRUD.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.instahipsta.harCRUD.config.property.RabbitmqProperties;
import com.instahipsta.harCRUD.exception.ResourceNotFoundException;
import com.instahipsta.harCRUD.model.dto.HARDto;
import com.instahipsta.harCRUD.model.entity.HAR;
import com.instahipsta.harCRUD.repository.HARRepo;
import com.instahipsta.harCRUD.service.HarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Slf4j
@Service
public class HarServiceImpl implements HarService {

    private ObjectMapper objectMapper;
    private RabbitTemplate rabbitTemplate;
    private RabbitmqProperties rabbitmqProperties;
    private HARRepo harRepo;

    @Autowired
    public HarServiceImpl(ObjectMapper objectMapper,
                          RabbitTemplate rabbitTemplate,
                          RabbitmqProperties rabbitProperties,
                          HARRepo harRepo) {

        this.objectMapper = objectMapper;
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitmqProperties = rabbitProperties;
        this.harRepo = harRepo;
    }

    @Override
    public HAR dtoToEntity(HARDto dto) {
        HAR har = new HAR();
        har.setVersion(dto.getLog().getVersion());
        har.setBrowser(dto.getLog().getBrowser().getName());
        har.setContent(objectMapper.valueToTree(dto));
        return har;
    }

    @Override
    public HARDto entityToDto(HAR har) throws JsonProcessingException {
        try {
            return objectMapper.treeToValue(har.getContent(), HARDto.class);
        }
        catch (IOException e) {
            log.error("Error from convert har with id {} to dto", har.getId());
            throw e;
        }
    }

    @Override
    public ResponseEntity<HARDto> update(HARDto dto, long id) throws JsonProcessingException {
        Optional<HAR> findHAR = harRepo.findById(id);
        HAR updateHAR = findHAR.orElseThrow(ResourceNotFoundException::new);

        updateHAR.setContent(objectMapper.valueToTree(dto));
        updateHAR.setBrowser(dto.getLog().getBrowser().getName());
        updateHAR.setVersion(dto.getLog().getVersion());

        HAR saveHAR = harRepo.save(updateHAR);

        return new ResponseEntity<>(entityToDto(saveHAR), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HARDto> find(long id) throws JsonProcessingException {
        Optional<HAR> findHar = harRepo.findById(id);
        HAR har = findHar.orElseThrow(ResourceNotFoundException::new);
        return new ResponseEntity<>(entityToDto(har), HttpStatus.OK);
    }

    @Override
    public void delete(long id) {
        harRepo.deleteById(id);
    }

    @Override
    public ResponseEntity<HARDto> add(MultipartFile file) throws IOException {
        HARDto dto = createDtoFromFile(file);
        HARDto saveDto = save(dto);

        rabbitTemplate.convertAndSend(rabbitmqProperties.getHarExchange(),
                                      rabbitmqProperties.getHarRoutingKey(),
                                      saveDto);

        return new ResponseEntity<>(saveDto, HttpStatus.OK);
    }

    @Override
    public HARDto createDtoFromFile(MultipartFile multipartFile) throws IOException {
        try (InputStream inputStream = multipartFile.getInputStream()) {
            return objectMapper.readValue(inputStream, HARDto.class);
        } catch (IOException e) {
            log.error("Wrong file {}", multipartFile.getOriginalFilename());
            throw e;
        }
    }

    @Override
    public HARDto save(HARDto dto) throws JsonProcessingException {
        HAR har = dtoToEntity(dto);
        HAR saveHAR = harRepo.save(har);
        return entityToDto(saveHAR);
    }
}
