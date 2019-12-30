package com.instahipsta.harCRUD.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.instahipsta.harCRUD.domain.Har;
import com.instahipsta.harCRUD.repository.HarRepo;
import com.instahipsta.harCRUD.service.HarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class HarServiceImpl implements HarService {

    private ObjectMapper objectMapper;
    private HarRepo harRepo;

    @Value("${file.downloads}")
    private String downloadPath;

    @Autowired
    public HarServiceImpl(HarRepo harRepo, ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.harRepo = harRepo;
    }

    @Override
    public Har save(Har har) {
        return harRepo.save(har);
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
    public Har createHarFromFile(String resultFileName) {
        File file = new File(downloadPath + "/" + resultFileName);
        try {
            JsonNode log = objectMapper.readTree(file).path("log");

            String version = log.path("version")
                    .toString().replaceAll("\"", "");
            String browser = log.path("browser")
                    .path("name").toString().replaceAll("\"", "");
            String browserVersion = log.path("browser")
                    .path("version").toString().replaceAll("\"", "");

            return create(version, browser, browserVersion, resultFileName);
        }
        catch (IOException e) { e.printStackTrace(); }
        return null;
    }
}
