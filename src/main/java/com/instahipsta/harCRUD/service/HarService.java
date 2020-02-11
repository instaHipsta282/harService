package com.instahipsta.harCRUD.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.instahipsta.harCRUD.model.dto.HARDto;
import com.instahipsta.harCRUD.model.entity.HAR;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface HarService {

    HAR dtoToEntity(HARDto dto);

    HARDto entityToDto(HAR har) throws JsonProcessingException;

    HARDto save(HARDto dto) throws JsonProcessingException;

    HARDto createDtoFromFile(MultipartFile multipartFile) throws IOException;

    void delete(long id);

    ResponseEntity<HARDto> find(long id) throws JsonProcessingException;

    ResponseEntity<HARDto> update(HARDto dto, long id) throws JsonProcessingException;

    ResponseEntity<HARDto> add(MultipartFile file) throws IOException;
}
