package com.instahipsta.harCRUD.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.instahipsta.harCRUD.model.dto.HarDTO;
import com.instahipsta.harCRUD.model.entity.Har;
import com.instahipsta.harCRUD.mapper.HarMapper;
import com.instahipsta.harCRUD.repository.HarRepo;
import com.instahipsta.harCRUD.service.HarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;

@Service
public class HarServiceImpl implements HarService {

    private static final Logger logger = LoggerFactory.getLogger(HarServiceImpl.class);
    private ObjectMapper objectMapper;
    private HarMapper mapper;
    private HarRepo harRepo;

    @Value("${file.downloads}")
    private String downloadsPath;

    @Autowired
    public HarServiceImpl(HarRepo harRepo, ObjectMapper objectMapper, HarMapper mapper) {
        this.objectMapper = objectMapper;
        this.harRepo = harRepo;
        this.mapper = mapper;
    }

    @Override
    public HarDTO save(Har har) {
        Har savedHar =  harRepo.save(har);
        if (savedHar.getVersion().isEmpty()) {
            return null;
        }
        else return mapper.toDto(savedHar);
    }

    @Override
    public Har findById(Long id) {
        return harRepo.findById(id).orElse(null);
    }

    @Override
    public Har create(String version, String browser, String browserVersion, String resultFileName) {
        return new Har(version, browser, browserVersion, resultFileName);
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
        }
        catch (IOException e) {
            logger.error("Failed to read JSON parse", e);
            return null;
        }
    }
}
