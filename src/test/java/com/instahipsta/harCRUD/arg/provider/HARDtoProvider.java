package com.instahipsta.harCRUD.arg.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.instahipsta.harCRUD.model.dto.HAR.HARDto;
import javassist.bytecode.stackmap.TypeData;

import java.io.IOException;
import java.io.InputStream;

public class HARDtoProvider {

    public static HARDto getHARDto() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        try (InputStream inputStream = TypeData.ClassName.class
                .getClassLoader().getResourceAsStream("data/test_archive.har")) {

            return objectMapper.readValue(inputStream, HARDto.class);
        }
    }
}
