package com.instahipsta.harCRUD.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.instahipsta.harCRUD.model.dto.HarDTO;
import com.instahipsta.harCRUD.model.entity.Har;
import com.instahipsta.harCRUD.repository.HarRepo;
import com.instahipsta.harCRUD.service.impl.HarServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.AdditionalAnswers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class HarServiceTest {

    @Autowired
    private HarServiceImpl harService;

    @MockBean
    private HarRepo harRepo;

    static MultipartFile uploadFile(String filename) throws IOException {

        Path wrongPath = Paths.get("filesForTests/" + filename);
        String name = "file";
        String originalFileName = "test_archive.har";
        String contentType = "application/json";

        byte[] wrongFileContent = Files.readAllBytes(wrongPath);
        return new MockMultipartFile(name, originalFileName, contentType, wrongFileContent);
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

    static Stream<Arguments> saveSource() throws JsonProcessingException {
        Har har = new Har(0L, "1", "Firefox", "1", getContent());
        return Stream.of(Arguments.of(har));
    }

    @ParameterizedTest
    @MethodSource("saveSource")
    void saveTest(Har har) {
        when(harRepo.save(any(Har.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());

        Har savedHar = harService.save(har);

        Assertions.assertEquals(har, savedHar);
    }

    @ParameterizedTest
    @MethodSource("saveSource")
    void harToDtoTest(Har har) {
        HarDTO harDTO = harService.harToDto(har);

        Assertions.assertEquals("Firefox", harDTO.getBrowser());
        Assertions.assertEquals("1", harDTO.getBrowserVersion());
        Assertions.assertEquals("1", harDTO.getVersion());
        Assertions.assertEquals(0, harDTO.getId());
    }

    @Test
    void createHarFromFileTest() throws IOException {
        Har har = harService.createHarFromFile(uploadFile("test_archive.har"));

        Assertions.assertEquals("1.2", har.getVersion());
        Assertions.assertEquals("70.0.1", har.getBrowserVersion());
        Assertions.assertEquals("Firefox", har.getBrowser());
        Assertions.assertNotNull(har.getContent());
    }

    @Test
    void createHarFromFileCatchTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                harService.createHarFromFile(uploadFile("oyo50.jpg")));
    }

    @Test
    void sendHarInQueueTest() {
        Assertions.assertDoesNotThrow(() -> harService.sendHarInQueue(getContent()));
    }

    @ParameterizedTest
    @ValueSource(longs = 6)
    void deleteTest(long id) {
        Assertions.assertDoesNotThrow(() -> harService.delete(id));
    }

    @ParameterizedTest
    @MethodSource("saveSource")
    void findTest(Har har) {
        when(harRepo.findById(98L)).thenReturn(Optional.of(har));

        ResponseEntity<HarDTO> responseEntity = harService.find(98L);
        HarDTO findHar = responseEntity.getBody();

        Assertions.assertEquals(har.getVersion(), findHar.getVersion());
        Assertions.assertEquals(har.getBrowser(), findHar.getBrowser());
        Assertions.assertEquals(har.getBrowserVersion(), findHar.getBrowserVersion());
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @ParameterizedTest
    @ValueSource(longs = 98L)
    void findNegativeTest(long id) {
        when(harRepo.findById(98L)).thenReturn(Optional.empty());
        ResponseEntity<HarDTO> responseEntity = harService.find(id);

        Assertions.assertNull(responseEntity.getBody());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }


    static Stream<Arguments> updateSource() throws JsonProcessingException {
        HarDTO harDTO = new HarDTO(0L, "999", "Chrome", "110");
        Har har = new Har(0L, "1", "Firefox", "1", getContent());
        return Stream.of(Arguments.of(harDTO, har, 1L));
    }

    @ParameterizedTest
    @MethodSource("updateSource")
    void updateTest(HarDTO harDTO, Har har, long id) {
        when(harRepo.findById(id)).thenReturn(Optional.of(har));
        when(harRepo.save(any(Har.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());

        ResponseEntity<HarDTO> responseEntity = harService.update(harDTO, id);
        HarDTO response = responseEntity.getBody();

        Assertions.assertEquals(har.getVersion(), response.getVersion());
        Assertions.assertEquals(har.getBrowser(), response.getBrowser());
        Assertions.assertEquals(har.getBrowserVersion(), response.getBrowserVersion());
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    static Stream<Arguments> updateNegativeSource() {
        HarDTO harDTO = new HarDTO(0L, "999", "Chrome", "110");
        return Stream.of(Arguments.of(harDTO, 1L));
    }

    @ParameterizedTest
    @MethodSource("updateNegativeSource")
    void updateNegativeTest(HarDTO harDTO, long id) {
        when(harRepo.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<HarDTO> responseEntity = harService.update(harDTO, id);

        Assertions.assertNull(responseEntity.getBody());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
