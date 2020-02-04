package com.instahipsta.harCRUD.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.instahipsta.harCRUD.model.dto.HarDTO;
import com.instahipsta.harCRUD.model.entity.Har;
import com.instahipsta.harCRUD.repository.HarRepo;
import com.instahipsta.harCRUD.service.HarService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class HarServiceImpl implements HarService {

    private final ObjectMapper objectMapper;
    private final HarRepo harRepo;
    private final RabbitTemplate rabbitTemplate;
    private final ModelMapper mapper;
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
        return this.mapper.map(har, HarDTO.class);
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
            return create(version, browser, browserVersion, jsonContent);
    }

    @Override
    public void sendHarInQueue(JsonNode entries) {
        System.out.println(1);
        System.out.println(harExchange + " " + harRoutingKey);
        rabbitTemplate.convertAndSend(harExchange, harRoutingKey, entries);
        System.out.println(2);
    }
}
