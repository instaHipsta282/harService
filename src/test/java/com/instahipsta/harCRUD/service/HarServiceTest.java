package com.instahipsta.harCRUD.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.instahipsta.harCRUD.exception.ResourceNotFoundException;
import com.instahipsta.harCRUD.model.dto.HarDto;
import com.instahipsta.harCRUD.model.entity.Har;
import com.instahipsta.harCRUD.repository.HarRepo;
import com.instahipsta.harCRUD.service.impl.HarServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.AdditionalAnswers;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class HarServiceTest {

    @Autowired
    private HarServiceImpl harService;

    @MockBean
    private HarRepo harRepo;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    static MultipartFile uploadFile(String filename) throws IOException {

        Path wrongPath = Paths.get("filesForTests/" + filename);
        String name = "file";
        String originalFileName = "test_archive.har";
        String contentType = "application/json";

        byte[] wrongFileContent = Files.readAllBytes(wrongPath);
        return new MockMultipartFile(name, originalFileName, contentType, wrongFileContent);
    }

    @ParameterizedTest
    @MethodSource("com.instahipsta.harCRUD.provider.HarProvider#harSource")
    void saveTest(Har har) {
        when(harRepo.save(any(Har.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());

        HarDto savedHar = harService.save(har);

        Assertions.assertEquals(har.getVersion(), savedHar.getVersion());
        Assertions.assertEquals(har.getBrowser(), savedHar.getBrowser());
        Assertions.assertEquals(har.getBrowserVersion(), savedHar.getBrowserVersion());
        Assertions.assertEquals(har.getContent(), savedHar.getContent());
        Assertions.assertEquals(har.getId(), savedHar.getId());
    }

    @ParameterizedTest
    @MethodSource("com.instahipsta.harCRUD.provider.HarProvider#harSource")
    void harToDtoTest(Har har) {
        HarDto harDTO = harService.harToDto(har);

        Assertions.assertEquals("Explorer", harDTO.getBrowser());
        Assertions.assertEquals("1.0.0", harDTO.getBrowserVersion());
        Assertions.assertEquals("1.0.1", harDTO.getVersion());
        Assertions.assertEquals(1, harDTO.getId());
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
        Assertions.assertThrows(NullPointerException.class, () ->
                harService.createHarFromFile(uploadFile("test2.json")));
    }

    @ParameterizedTest
    @MethodSource("com.instahipsta.harCRUD.provider.HarProvider#contentSource")
    void sendHarInQueueTest(JsonNode content) {
        Assertions.assertDoesNotThrow(() -> harService.sendHarInQueue(content));
    }

    @ParameterizedTest
    @ValueSource(longs = 6)
    void deleteTest(long id) {
        Assertions.assertDoesNotThrow(() -> harService.delete(id));
    }

    @ParameterizedTest
    @MethodSource("com.instahipsta.harCRUD.provider.HarProvider#harAndIdSource")
    void findTest(Har har, long id) {
        when(harRepo.findById(id)).thenReturn(Optional.of(har));

        ResponseEntity<HarDto> responseEntity = harService.find(id);
        HarDto findHar = responseEntity.getBody();

        Assertions.assertEquals(har.getVersion(), findHar.getVersion());
        Assertions.assertEquals(har.getBrowser(), findHar.getBrowser());
        Assertions.assertEquals(har.getBrowserVersion(), findHar.getBrowserVersion());
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @ParameterizedTest
    @ValueSource(longs = 98L)
    void findNegativeTest(long id) {
        when(harRepo.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> harService.find(id));
    }

    @ParameterizedTest
    @MethodSource("com.instahipsta.harCRUD.provider.HarProvider#harDtoHarAndIdSource")
    void updateTest(HarDto harDTO, Har har, long id) {
        when(harRepo.findById(id)).thenReturn(Optional.of(har));
        when(harRepo.save(any(Har.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());

        ResponseEntity<HarDto> responseEntity = harService.update(harDTO, id);
        HarDto response = responseEntity.getBody();

        Assertions.assertEquals(har.getVersion(), response.getVersion());
        Assertions.assertEquals(har.getBrowser(), response.getBrowser());
        Assertions.assertEquals(har.getBrowserVersion(), response.getBrowserVersion());
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @ParameterizedTest
    @MethodSource("com.instahipsta.harCRUD.provider.HarProvider#harDtoAndIdSource")
    void updateNegativeTest(HarDto harDTO, long id) {
        when(harRepo.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () ->
                harService.update(harDTO, id));
    }
}
