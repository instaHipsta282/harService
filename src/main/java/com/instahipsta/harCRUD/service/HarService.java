package com.instahipsta.harCRUD.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.instahipsta.harCRUD.model.dto.HarDTO;
import com.instahipsta.harCRUD.model.entity.Har;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public interface HarService {
    Har save(Har har);

    HarDTO harToDto(Har har);

    Har create(String version,
               String browser,
               String browserVersion,
               JsonNode content);

    Har createHarFromFile(byte[] content) throws IOException;

    void sendHarInQueue(JsonNode entries);

    void delete(long id);

    Har find(long id);

    Har update(HarDTO updatedHar);
}
