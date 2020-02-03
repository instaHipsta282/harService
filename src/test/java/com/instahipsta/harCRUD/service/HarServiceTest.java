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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class HarServiceTest {

    @InjectMocks
    private HarServiceImpl harService;
    @Mock
    private HarRepo harRepo;
    @Spy
    @Autowired
    ObjectMapper objectMapper;
    @Spy
    @Autowired
    ModelMapper mapper;
    @Mock
    RabbitTemplate rabbitTemplate;
    private MockMultipartFile multipartFile;
    @Value("${file.filesForTests}")
    private String filesForTests;
    private JsonNode content;


    @BeforeEach
    public void initFields() throws Exception {
        MockitoAnnotations.initMocks(TestProfileServiceTest.class);

        content = objectMapper.readTree("{\n" +
                "  \"headers\": {\n" +
                "    \"name\": \"Last-Modified\",\n" +
                "    \"value\": \"Sun, 01 Dec 2019 21:32:09 GMT\"\n" +
                "  }\n" +
                "}");

        Path path = Paths.get(filesForTests + "/test_archive.har");

        String name = "file";
        String originalFileName = "test_archive.har";
        String contentType = "application/json";
        byte[] fileContent = null;

        try {
            fileContent = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.multipartFile = new MockMultipartFile(name, originalFileName, contentType, fileContent);

    }

    @Test
    public void saveTest() {
        Har har = harService.create("1", "Firefox", "1", content);
        doReturn(har).when(harRepo).save(har);
        Har savedHar = harService.save(har);
        Assertions.assertEquals(har, savedHar);
    }

    @Test
    public void harToDto() {
        Har har = harService.create("1", "Firefox", "1", content);

        HarDTO harDTO = harService.harToDto(har);
        Assertions.assertEquals("Firefox", harDTO.getBrowser());
        Assertions.assertEquals("1", harDTO.getBrowserVersion());
        Assertions.assertEquals("1", harDTO.getVersion());
    }

    @Test
    public void createTest() {
        Har createdHar = harService.create("1", "Firefox", "1", content);

        Assertions.assertEquals("1", createdHar.getVersion());
        Assertions.assertEquals("Firefox", createdHar.getBrowser());
        Assertions.assertEquals("1", createdHar.getBrowserVersion());
        Assertions.assertEquals(content, createdHar.getContent());
    }

    @Test
    public void createHarFromFileTest() throws Exception {
        Har har = harService.createHarFromFile(multipartFile.getBytes());

        Assertions.assertEquals("1.2", har.getVersion());
        Assertions.assertEquals("70.0.1", har.getBrowserVersion());
        Assertions.assertEquals("Firefox", har.getBrowser());
        Assertions.assertNotNull(har.getContent());
    }

    @Test
    public void sendHarInQueue() {
        doThrow(RuntimeException.class).when(rabbitTemplate).convertAndSend(content);
        Assertions.assertThrows(RuntimeException.class, () ->
                harService.sendHarInQueue(content));
    }
}
