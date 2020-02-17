package com.instahipsta.harCRUD.arg.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.instahipsta.harCRUD.model.dto.HAR.HARDto;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

public class HARDtoProvider {

    public static HARDto getHARDto() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        try (InputStream inputStream = new ClassPathResource("data/test_archive.har").getInputStream()) {
            return objectMapper.readValue(inputStream, HARDto.class);
        }
    }
}
