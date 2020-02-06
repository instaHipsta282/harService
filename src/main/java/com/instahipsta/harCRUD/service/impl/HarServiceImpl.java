package com.instahipsta.harCRUD.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.instahipsta.harCRUD.model.dto.HarDto;
import com.instahipsta.harCRUD.model.entity.Har;
import com.instahipsta.harCRUD.property.RabbitmqProperties;
import com.instahipsta.harCRUD.repository.HarRepo;
import com.instahipsta.harCRUD.service.HarService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class HarServiceImpl implements HarService {

    private final ObjectMapper objectMapper;
    private final HarRepo harRepo;
    private final RabbitTemplate rabbitTemplate;
    private final ModelMapper mapper;
    private final RabbitmqProperties rabbitmqProperties;
    //    @Value("${rabbitmq.harExchange}")
//    private String harExchange;
//    @Value("${rabbitmq.harRoutingKey}")
//    private String harRoutingKey;

    @Override
    public Har save(Har har) {
        return harRepo.save(har);
    }

    @Override
    public HarDto harToDto(Har har) {
        return this.mapper.map(har, HarDto.class);
    }

    @Override
    public Har createHarFromFile(MultipartFile multipartFile) {

        try {
            byte[] content = multipartFile.getBytes();

            String version = objectMapper
                    .readTree(content)
                    .path("log")
                    .path("version")
                    .toString()
                    .replaceAll("\"", "");

            String browser = objectMapper
                    .readTree(content)
                    .path("log")
                    .path("browser")
                    .path("name")
                    .toString()
                    .replaceAll("\"", "");

            String browserVersion = objectMapper
                    .readTree(content)
                    .path("log")
                    .path("browser")
                    .path("version")
                    .toString()
                    .replaceAll("\"", "");

            JsonNode jsonContent = objectMapper
                    .readTree(content)
                    .path("log")
                    .path("entries");
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
    public ResponseEntity<HarDto> delete(long id) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        Optional<Har> deletedHar = harRepo.findById(id);

        if (!deletedHar.isPresent()) log.info("Har wit id {} not found", id);
        else {
            harRepo.deleteById(id);
            httpStatus = HttpStatus.OK;
        }

        return new ResponseEntity<>(httpStatus);
    }

    @Override
    public ResponseEntity<HarDto> find(long id) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        HarDto response = null;
        Optional<Har> findHar = harRepo.findById(id);

        if (!findHar.isPresent()) log.info("Har wit id {} not found", id);
        else {
            httpStatus = HttpStatus.OK;
            response = harToDto(findHar.get());
        }

        return new ResponseEntity<>(response, httpStatus);
    }

    @Override
    public ResponseEntity<HarDto> update(HarDto harFromRequest, long harId) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        HarDto response = null;
        Optional<Har> findHar = harRepo.findById(harId);

        if (!findHar.isPresent()) log.warn("Har with id {} not found", harId);
        else {
            Har updatedHar = findHar.get();

            updatedHar.setVersion(harFromRequest.getVersion());
            updatedHar.setBrowserVersion(harFromRequest.getBrowserVersion());
            updatedHar.setBrowser(harFromRequest.getBrowser());

            Har savedHar = harRepo.save(updatedHar);
            response = harToDto(savedHar);
            httpStatus = HttpStatus.OK;
        }

        return new ResponseEntity<>(response, httpStatus);
    }

    @Override
    public ResponseEntity<HarDto> add(MultipartFile file) {
        Har har = createHarFromFile(file);
        Har savedHar = harRepo.save(har);

        sendHarInQueue(savedHar.getContent());

        HarDto response = harToDto(savedHar);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
