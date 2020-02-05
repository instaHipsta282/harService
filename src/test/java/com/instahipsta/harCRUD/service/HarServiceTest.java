package com.instahipsta.harCRUD.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.instahipsta.harCRUD.model.dto.HarDTO;
import com.instahipsta.harCRUD.model.entity.Har;
import com.instahipsta.harCRUD.repository.HarRepo;
import com.instahipsta.harCRUD.service.impl.HarServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class HarServiceTest {

    @Autowired
    private HarServiceImpl harService;

    @MockBean
    private HarRepo harRepo;

    @Spy
    @Autowired
    private ObjectMapper objectMapper;

    @Value("${file.filesForTests}")
    private String filesForTests;

    private MockMultipartFile multipartFile;
    private MockMultipartFile wrongMultipartFile;
    private JsonNode content;
    private Har har;

    @BeforeEach
    void initFields() throws Exception {
        MockitoAnnotations.initMocks(TestProfileServiceTest.class);
        content = objectMapper.readTree("{\n" +
                "  \"headers\": {\n" +
                "    \"name\": \"Last-Modified\",\n" +
                "    \"value\": \"Sun, 01 Dec 2019 21:32:09 GMT\"\n" +
                "  }\n" +
                "}");

        har = harService.create("1", "Firefox", "1", content);

        Path path = Paths.get(filesForTests + "/test_archive.har");
        Path wrongPath = Paths.get(filesForTests + "/oyo50.jpg");

        String name = "file";
        String originalFileName = "test_archive.har";
        String contentType = "application/json";
        byte[] fileContent = null;
        byte[] wrongContent = null;

        try {
            fileContent = Files.readAllBytes(path);
            wrongContent = Files.readAllBytes(wrongPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        multipartFile = new MockMultipartFile(name, originalFileName, contentType, fileContent);
        wrongMultipartFile = new MockMultipartFile(name, originalFileName, contentType, wrongContent);
    }

    @Test
    void saveTest() {
        doReturn(har).when(harRepo).save(har);

        Har savedHar = harService.save(har);

        Assertions.assertEquals(har, savedHar);
    }

    @Test
    void harToDto() {
        Har har = harService.create("1", "Firefox", "1", content);

        HarDTO harDTO = harService.harToDto(har);
        Assertions.assertEquals("Firefox", harDTO.getBrowser());
        Assertions.assertEquals("1", harDTO.getBrowserVersion());
        Assertions.assertEquals("1", harDTO.getVersion());
    }

    @Test
    void createTest() {
        Har createdHar = harService.create("1", "Firefox", "1", content);

        Assertions.assertEquals("1", createdHar.getVersion());
        Assertions.assertEquals("Firefox", createdHar.getBrowser());
        Assertions.assertEquals("1", createdHar.getBrowserVersion());
        Assertions.assertEquals(content, createdHar.getContent());
    }

    @Test
    void createHarFromFileTest() {
        Har har = harService.createHarFromFile(multipartFile);

        Assertions.assertEquals("1.2", har.getVersion());
        Assertions.assertEquals("70.0.1", har.getBrowserVersion());
        Assertions.assertEquals("Firefox", har.getBrowser());
        Assertions.assertNotNull(har.getContent());
    }

    @Test
    void createHarFromFileCatchTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                harService.createHarFromFile(wrongMultipartFile));
    }

    @Test
    void sendHarInQueueTest() {
        Assertions.assertDoesNotThrow(() -> harService.sendHarInQueue(content));
    }

    @Test
    void deleteTest() {
        doThrow(RuntimeException.class).when(harRepo).deleteById(6L);
        Assertions.assertThrows(RuntimeException.class, () ->
                harService.delete(6L));
    }

    @Test
    void findTest() {
        doReturn(Optional.of(har)).when(harRepo).findById(98L);
        Har har = harService.find(98L).get();
        Assertions.assertEquals("1", har.getVersion());
        Assertions.assertEquals("Firefox", har.getBrowser());
        Assertions.assertEquals("1", har.getBrowserVersion());
        Assertions.assertEquals(content, har.getContent());
    }

    @Test
    void updateTest() {
        HarDTO harDTO = new HarDTO(0L, "999", "Chrome", "110");
        Har newHar = harService.create("999", "Chrome", "110", content);

        doReturn(newHar).when(harRepo).save(har);

        Har savedHar = harService.update(har, harDTO);

        Assertions.assertEquals("999", savedHar.getVersion());
        Assertions.assertEquals("Chrome", savedHar.getBrowser());
        Assertions.assertEquals("110", savedHar.getBrowserVersion());
        Assertions.assertEquals(content, savedHar.getContent());
    }

}
