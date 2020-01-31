package com.instahipsta.harCRUD.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.instahipsta.harCRUD.model.dto.HarDTO;
import com.instahipsta.harCRUD.model.entity.Har;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface HarService {
    Har save(Har har);

    HarDTO harToDto(Har har);

    Har findById(Long id);

    Har create(String version,
               String browser,
               String browserVersion,
               JsonNode content);

    Har createHarFromFile(MultipartFile file);

    void sendHarInQueue(JsonNode entries);
}
