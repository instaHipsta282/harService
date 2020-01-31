package com.instahipsta.harCRUD.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.instahipsta.harCRUD.mapper.HarMapper;
import com.instahipsta.harCRUD.model.dto.HarDTO;
import com.instahipsta.harCRUD.model.entity.Har;
import com.instahipsta.harCRUD.repository.HarRepo;
import com.instahipsta.harCRUD.service.HarService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class HarServiceImpl implements HarService {
    @NonNull
    private ObjectMapper objectMapper;
    @NonNull
    private HarMapper mapper;
    @NonNull
    private HarRepo harRepo;
    @NonNull
    private RabbitTemplate rabbitTemplate;
    @Value("${rabbitmq.harExchange}")
    private String harExchange;
    @Value("${rabbitmq.harRoutingKey}")
    private String harRoutingKey;

    @Override
    public HarDTO save(Har har) {
        Har savedHar = harRepo.save(har);
        if (savedHar.getVersion().isEmpty()) {
            return null;
        } else return mapper.toDto(savedHar);
    }

    @Override
    public Har findById(Long id) {
        return harRepo.findById(id).orElse(null);
    }

    @Override
    public Har create(String version,
                      String browser,
                      String browserVersion,
                      String resultFileName) {

        Har har = new Har();
        har.setVersion(version);
        har.setBrowser(browser);
        har.setBrowserVersion(browserVersion);
        har.setFileName(resultFileName);
        return har;
    }

    @Override
    public Har createHarFromFile(Path filePath) {
        try {
            JsonNode log = objectMapper.readTree(filePath.toFile()).path("log");
            String version = log.path("version")
                    .toString().replaceAll("\"", "");
            String browser = log.path("browser")
                    .path("name").toString().replaceAll("\"", "");
            String browserVersion = log.path("browser")
                    .path("version").toString().replaceAll("\"", "");
            return create(version, browser, browserVersion, filePath.getFileName().toString());
        } catch (IOException e) {
            log.error("Failed to read JSON parse", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void sendHarInQueue(byte[] data) {
        log.info("Sending message length: {}", (data.length));
        rabbitTemplate.convertAndSend(harExchange, harRoutingKey, data);
    }
}
