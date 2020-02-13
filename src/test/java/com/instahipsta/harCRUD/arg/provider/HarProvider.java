package com.instahipsta.harCRUD.arg.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.instahipsta.harCRUD.model.dto.HAR.HARDto;
import com.instahipsta.harCRUD.model.entity.HAR;
import javassist.bytecode.stackmap.TypeData;

import java.io.IOException;
import java.io.InputStream;

import static com.fasterxml.jackson.databind.SerializationFeature.FAIL_ON_EMPTY_BEANS;

public class HarProvider {

    public static HAR getHAR(String filename) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        try (InputStream inputStream = TypeData.ClassName.class
                .getClassLoader().getResourceAsStream("data/" + filename)) {

            HARDto dto = objectMapper.readValue(inputStream, HARDto.class);
            HAR har = new HAR();
            har.setVersion(dto.getLog().getVersion());
            har.setBrowser(dto.getLog().getBrowser().getName());
            har.setContent(objectMapper.disable(FAIL_ON_EMPTY_BEANS).valueToTree(dto));

            return har;
        }
    }
}
