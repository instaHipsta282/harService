package com.instahipsta.harCRUD.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.instahipsta.harCRUD.config.property.RabbitmqProperties;
import com.instahipsta.harCRUD.exception.ResourceNotFoundException;
import com.instahipsta.harCRUD.model.dto.HarDto;
import com.instahipsta.harCRUD.model.entity.Har;
import com.instahipsta.harCRUD.repository.HarRepo;
import com.instahipsta.harCRUD.service.HarService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
public class HarServiceImpl implements HarService {

    private ObjectMapper objectMapper;
    private HarRepo harRepo;
    private RabbitTemplate rabbitTemplate;
    private ModelMapper modelMapper;
    private RabbitmqProperties rabbitmqProperties;

    @Autowired
    public HarServiceImpl(ObjectMapper objectMapper,
                          HarRepo harRepo,
                          RabbitTemplate rabbitTemplate,
                          ModelMapper modelMapper,
                          RabbitmqProperties rabbitProperties) {

        this.objectMapper = objectMapper;
        this.harRepo = harRepo;
        this.rabbitTemplate = rabbitTemplate;
        this.modelMapper = modelMapper;
        this.rabbitmqProperties = rabbitProperties;
    }

    @Override
    public HarDto save(Har har) {
        return harToDto(harRepo.save(har));
    }

    @Override
    public HarDto harToDto(Har har) {
        return this.modelMapper.map(har, HarDto.class);
    }

    @Override
    public Har createHarFromFile(MultipartFile multipartFile) {

        try {
            byte[] content = multipartFile.getBytes();

            String version = objectMapper.readTree(content).at("/log/version").textValue();
            String browser = objectMapper.readTree(content).at("/log/browser/name").textValue();
            String browserVersion = objectMapper.readTree(content).at("/log/browser/version").textValue();
            JsonNode jsonContent = objectMapper.readTree(content).at("/log/entries");
            Har har2 = new Har();
            return new Har(0, version, browser, browserVersion, jsonContent);
        } catch (IOException e) {
            log.error("Wrong file {}", multipartFile.getOriginalFilename());
            throw new IllegalArgumentException("In file " + multipartFile.getOriginalFilename());
        }
    }

    @Override
    public void sendHarInQueue(JsonNode entries) {
        rabbitTemplate.convertAndSend(rabbitmqProperties.getHarExchange(),
                rabbitmqProperties.getHarRoutingKey(),
                entries);
    }

    @Override
    public void delete(long id) {
        harRepo.deleteById(id);
    }

    @Override
    public ResponseEntity<HarDto> find(long id) {
        Optional<Har> findHar = harRepo.findById(id);
        Har har = findHar.orElseThrow(ResourceNotFoundException::new);
        return new ResponseEntity<>(harToDto(har), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HarDto> update(HarDto harFromRequest, long harId) {
        Optional<Har> findHar = harRepo.findById(harId);
        Har har = findHar.orElseThrow(ResourceNotFoundException::new);

        har.setVersion(harFromRequest.getVersion());
        har.setBrowserVersion(harFromRequest.getBrowserVersion());
        har.setBrowser(harFromRequest.getBrowser());

        return new ResponseEntity<>(save(har), HttpStatus.OK);
}

    @Override
    public ResponseEntity<HarDto> add(MultipartFile file) {
        Har har = createHarFromFile(file);
        HarDto harDto = save(har);

        sendHarInQueue(harDto.getContent());

        return new ResponseEntity<>(harDto, HttpStatus.OK);
    }
}
