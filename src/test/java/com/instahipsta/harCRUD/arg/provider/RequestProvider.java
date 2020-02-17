package com.instahipsta.harCRUD.arg.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.instahipsta.harCRUD.model.dto.HAR.Entry;
import com.instahipsta.harCRUD.model.entity.Request;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

public class RequestProvider {

    public static Request getRequest() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ModelMapper modelMapper = new ModelMapper();

        try (InputStream inputStream = new ClassPathResource("data/test5.json").getInputStream()) {
            Entry entry = objectMapper.readValue(inputStream, Entry.class);
            return modelMapper.map(entry.getRequest(), Request.class);
        }
    }
}
