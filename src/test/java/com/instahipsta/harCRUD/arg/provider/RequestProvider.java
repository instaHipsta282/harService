package com.instahipsta.harCRUD.arg.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.instahipsta.harCRUD.model.dto.HAR.Entry;
import com.instahipsta.harCRUD.model.entity.Request;
import com.instahipsta.harCRUD.model.entity.TestProfile;
import javassist.bytecode.stackmap.TypeData;
import org.junit.jupiter.params.provider.Arguments;
import org.modelmapper.ModelMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

public class RequestProvider {

    public static Request getRequest() throws IOException {

        try (InputStream inputStream = TypeData.ClassName.class
                .getClassLoader().getResourceAsStream("data/test5.json")) {

            ObjectMapper objectMapper = new ObjectMapper();
            ModelMapper modelMapper = new ModelMapper();
            Entry entry = objectMapper.readValue(inputStream, Entry.class);
            return modelMapper.map(entry.getRequest(), Request.class);
        }
    }
}
