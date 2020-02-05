package com.instahipsta.harCRUD.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.instahipsta.harCRUD.model.dto.HarDTO;
import com.instahipsta.harCRUD.model.entity.Har;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public interface HarService {
    Har save(Har har);

    HarDTO harToDto(Har har);

    Har createHarFromFile(MultipartFile multipartFile);

    void sendHarInQueue(JsonNode entries);

    ResponseEntity<HarDTO> delete(long id);

    ResponseEntity<HarDTO> find(long id);

    ResponseEntity<HarDTO> update(HarDTO harFromRequest, long harId);

    ResponseEntity<HarDTO> add(MultipartFile file);
}
