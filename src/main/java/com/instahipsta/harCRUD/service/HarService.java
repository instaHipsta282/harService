package com.instahipsta.harCRUD.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.instahipsta.harCRUD.model.dto.HarDto;
import com.instahipsta.harCRUD.model.entity.Har;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface HarService {
    HarDto save(Har har);

    HarDto harToDto(Har har);

    Har createHarFromFile(MultipartFile multipartFile);

    void sendHarInQueue(JsonNode entries);

    void delete(long id);

    ResponseEntity<HarDto> find(long id);

    ResponseEntity<HarDto> update(HarDto harFromRequest, long harId);

    ResponseEntity<HarDto> add(MultipartFile file);
}
