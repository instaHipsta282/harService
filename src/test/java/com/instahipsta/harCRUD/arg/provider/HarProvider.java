package com.instahipsta.harCRUD.arg.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.instahipsta.harCRUD.model.dto.HAR.HARDto;
import com.instahipsta.harCRUD.model.entity.HAR;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

import static com.fasterxml.jackson.databind.SerializationFeature.FAIL_ON_EMPTY_BEANS;

public class HarProvider {

    public static HAR getHAR(String filename) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper().disable(FAIL_ON_EMPTY_BEANS);
        try (InputStream inputStream = new ClassPathResource("data/" + filename).getInputStream()) {

            HARDto dto = objectMapper.readValue(inputStream, HARDto.class);

            return HAR.builder()
                    .version(dto.getLog().getVersion())
                    .browser(dto.getLog().getBrowser().getName())
                    .content(objectMapper.valueToTree(dto))
                    .build();
        }
    }
}
