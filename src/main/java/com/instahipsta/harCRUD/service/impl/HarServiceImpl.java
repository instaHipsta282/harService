package com.instahipsta.harCRUD.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.instahipsta.harCRUD.model.dto.HarDTO;
import com.instahipsta.harCRUD.model.entity.Har;
import com.instahipsta.harCRUD.repository.HarRepo;
import com.instahipsta.harCRUD.service.HarService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class HarServiceImpl implements HarService {
    @NonNull
    private ObjectMapper objectMapper;
    @NonNull
    private HarRepo harRepo;
    @NonNull
    private RabbitTemplate rabbitTemplate;
    @NotNull
    ModelMapper mapper;
    @Value("${rabbitmq.harExchange}")
    private String harExchange;
    @Value("${rabbitmq.harRoutingKey}")
    private String harRoutingKey;

    @Override
    public Har save(Har har) {
        return harRepo.save(har);
    }

    @Override
    public HarDTO harToDto(Har har) {
        return mapper.map(har, HarDTO.class);
    }

    @Override
    public Har create(String version,
                      String browser,
                      String browserVersion,
                      JsonNode content) {

        Har har = new Har();
        har.setVersion(version);
        har.setBrowser(browser);
        har.setBrowserVersion(browserVersion);
        har.setContent(content);
        return har;
    }

    @Override
    public Har createHarFromFile(byte[] content) throws IOException {
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
            System.out.println("What>");
            return create(version, browser, browserVersion, jsonContent);
    }

    @Override
    public void sendHarInQueue(JsonNode entries) {
        rabbitTemplate.convertAndSend(harExchange, harRoutingKey, entries);
    }
}
