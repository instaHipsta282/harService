package com.instahipsta.harCRUD.provider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.instahipsta.harCRUD.model.dto.HarDto;
import com.instahipsta.harCRUD.model.entity.Har;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class HarProvider {

    static Har getHar() throws JsonProcessingException {
        return new Har(1, "1.0.1", "Explorer", "1.0.0", getContent());
    }

    static HarDto getHarDto() throws JsonProcessingException {
        return new HarDto(5L, "1.2", "Firefox", "70.0.1", getContent());
    }

    static JsonNode getContent() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree("{\n" +
                "  \"headers\": {\n" +
                "    \"name\": \"Last-Modified\",\n" +
                "    \"value\": \"Sun, 01 Dec 2019 21:32:09 GMT\"\n" +
                "  }\n" +
                "}");
    }

    static Stream<Arguments> harSource() throws JsonProcessingException {
        return Stream.of(Arguments.of(getHar()));
    }

    static Stream<Arguments> contentSource() throws JsonProcessingException {
        return Stream.of(Arguments.of(getContent()));
    }

    static Stream<Arguments> jsonHarAndIdSource() throws JsonProcessingException {
        return Stream.of(
                Arguments.of(new ObjectMapper().writeValueAsString(getHarDto()), getHar(), 1L));
    }

    static Stream<Arguments> harDtoHarAndIdSource() throws JsonProcessingException {
        return Stream.of(
                Arguments.of(getHarDto(), getHar(), 1L));
    }

    static Stream<Arguments> harDtoAndIdSource() throws JsonProcessingException {
        return Stream.of(Arguments.of(getHarDto(), 1L));
    }

    static Stream<Arguments> harAndIdSource() throws JsonProcessingException {
        return Stream.of(
                Arguments.of(getHar(), 1L));
    }

    static Stream<Arguments> multipartFileSource() throws IOException {
        String name = "file";
        String contentType = "application/json";
        String originalFileName = "test_archive.har";
        Path path = Paths.get("filesForTests/test_archive.har");
        byte[] content = Files.readAllBytes(path);

        MultipartFile multipartFile = new MockMultipartFile(name, originalFileName, contentType, content);

        return Stream.of(Arguments.of(multipartFile));
    }
}
