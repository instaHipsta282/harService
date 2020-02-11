package com.instahipsta.harCRUD.provider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.instahipsta.harCRUD.model.dto.HARDto;
import com.instahipsta.harCRUD.model.entity.HAR;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class HarProvider {

    static HAR getHAR() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream inputStream = new FileInputStream("filesForTests/test_archive.har")) {
            HARDto dto = objectMapper.readValue(inputStream, HARDto.class);
            HAR har = new HAR();
            har.setVersion(dto.getLog().getVersion());
            har.setBrowser(dto.getLog().getBrowser().getName());
            har.setContent(objectMapper.valueToTree(dto));
            return har;
        }
        catch (IOException ignore) { throw ignore; }
    }

    static HARDto getHARDto() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream inputStream = new FileInputStream("filesForTests/test_archive.har")) {
            return objectMapper.readValue(inputStream, HARDto.class);
        }
        catch (IOException ignore) { throw ignore; }
    }

    static MultipartFile getMultipartFile() throws IOException {
        String name = "file";
        String contentType = "application/json";
        String originalFileName = "test_archive.har";
        Path path = Paths.get("filesForTests/test_archive.har");
        byte[] content = Files.readAllBytes(path);

        return new MockMultipartFile(name, originalFileName, contentType, content);
    }

    static Stream<Arguments> fileHarAndIdSource() throws IOException {
        return Stream.of(Arguments.of(getMultipartFile(), getHAR(), 1L));
    }

    static Stream<Arguments> fileAndIdSource() throws IOException {
        return Stream.of(Arguments.of(getMultipartFile(), 1L));
    }

    static Stream<Arguments> fileSource() throws IOException {
        return Stream.of(Arguments.of(getMultipartFile(), 1L));
    }

    static Stream<Arguments> harAndIdSource() throws IOException {
        return Stream.of(Arguments.of(getHAR(), 1L));
    }

    static Stream<Arguments> harSource() throws IOException {
        return Stream.of(Arguments.of(getHAR()));
    }

    static Stream<Arguments> harDtoHarAndIdSource() throws IOException {
        return Stream.of(
                Arguments.of(getHARDto(), getHAR(), 1L));
    }

    static Stream<Arguments> harDtoAndIdSource() throws IOException {
        return Stream.of(Arguments.of(getHARDto(), 1L));
    }

    static Stream<Arguments> harDtoSource() throws IOException {
        return Stream.of(Arguments.of(getHARDto()));
    }
}
