package com.instahipsta.harCRUD.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.instahipsta.harCRUD.model.dto.HarDTO;
import com.instahipsta.harCRUD.model.entity.Har;
import com.instahipsta.harCRUD.repository.HarRepo;
import com.instahipsta.harCRUD.service.impl.HarServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
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
//
//    @BeforeEach
//    void initFields() throws Exception {
//        MockitoAnnotations.initMocks(TestProfileServiceTest.class);
//        content = objectMapper.readTree("{\n" +
//                "  \"headers\": {\n" +
//                "    \"name\": \"Last-Modified\",\n" +
//                "    \"value\": \"Sun, 01 Dec 2019 21:32:09 GMT\"\n" +
//                "  }\n" +
//                "}");
//
//        har = harService.create("1", "Firefox", "1", content);
//
//        Path path = Paths.get(filesForTests + "/test_archive.har");
//        Path wrongPath = Paths.get(filesForTests + "/oyo50.jpg");
//
//        String name = "file";
//        String originalFileName = "test_archive.har";
//        String contentType = "application/json";
//        byte[] fileContent = null;
//        byte[] wrongContent = null;
//
//        try {
//            fileContent = Files.readAllBytes(path);
//            wrongContent = Files.readAllBytes(wrongPath);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        multipartFile = new MockMultipartFile(name, originalFileName, contentType, fileContent);
//        wrongMultipartFile = new MockMultipartFile(name, originalFileName, contentType, wrongContent);
//    }

    static MultipartFile uploadFile(String filename) throws IOException {

        Path wrongPath = Paths.get("filesForTests/" + filename);
        String name = "file";
        String originalFileName = "test_archive.har";
        String contentType = "application/json";

        byte[] wrongFileContent = Files.readAllBytes(wrongPath);
        return new MockMultipartFile(name, originalFileName, contentType, wrongFileContent);
    }

    static Stream<Arguments> saveSource() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode content = objectMapper.readTree("{\n" +
                "  \"headers\": {\n" +
                "    \"name\": \"Last-Modified\",\n" +
                "    \"value\": \"Sun, 01 Dec 2019 21:32:09 GMT\"\n" +
                "  }\n" +
                "}");

        Har har = new Har(0L, "1", "Firefox", "1", content);
        return Stream.of(Arguments.of(har));
    }

    @ParameterizedTest
    @MethodSource("saveSource")
    void saveTest(Har har) {
        doReturn(har).when(harRepo).save(har);

        Har savedHar = harService.save(har);

        Assertions.assertEquals(har, savedHar);
    }

    @ParameterizedTest
    @MethodSource("saveSource")
    void harToDto(Har har) {

        HarDTO harDTO = harService.harToDto(har);
        Assertions.assertEquals("Firefox", harDTO.getBrowser());
        Assertions.assertEquals("1", harDTO.getBrowserVersion());
        Assertions.assertEquals("1", harDTO.getVersion());
        Assertions.assertEquals(0, harDTO.getId());
    }

    static Stream<Arguments> createHarFromFileSource() throws IOException {
        return Stream.of(Arguments.of(uploadFile("test_archive.har")));
    }


    @ParameterizedTest
    @MethodSource("createHarFromFileSource")
    void createHarFromFileTest(MultipartFile file) {
        Har har = harService.createHarFromFile(file);

        Assertions.assertEquals("1.2", har.getVersion());
        Assertions.assertEquals("70.0.1", har.getBrowserVersion());
        Assertions.assertEquals("Firefox", har.getBrowser());
        Assertions.assertNotNull(har.getContent());
    }

    static Stream<Arguments> createHarFromFileCatchSource() throws IOException {
        return Stream.of(Arguments.of(uploadFile("oyo50.jpg")));
    }

    @ParameterizedTest
    @MethodSource("createHarFromFileCatchSource")
    void createHarFromFileCatchTest(MultipartFile wrongMultipartFile) {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                harService.createHarFromFile(wrongMultipartFile));
    }

    static Stream<Arguments> sendHarInQueueSource() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode content = objectMapper.readTree("{\n" +
                "  \"headers\": {\n" +
                "    \"name\": \"Last-Modified\",\n" +
                "    \"value\": \"Sun, 01 Dec 2019 21:32:09 GMT\"\n" +
                "  }\n" +
                "}");
        return Stream.of(Arguments.of(content));
    }

    @ParameterizedTest
    @MethodSource("sendHarInQueueSource")
    void sendHarInQueueTest(JsonNode node) {
        Assertions.assertDoesNotThrow(() -> harService.sendHarInQueue(node));
    }

    @ParameterizedTest
    @ValueSource(longs = 6)
    void deleteTest(long id) {
        Assertions.assertDoesNotThrow(() -> harService.delete(id));
    }

    @ParameterizedTest
    @MethodSource("saveSource")
    void findTest(Har har) {
        doReturn(Optional.of(har)).when(harRepo).findById(98L);
        ResponseEntity<HarDTO> responseEntity = harService.find(98L);

        HarDTO findHar = responseEntity.getBody();

        Assertions.assertEquals(har.getVersion(), findHar.getVersion());
        Assertions.assertEquals(har.getBrowser(), findHar.getBrowser());
        Assertions.assertEquals(har.getBrowserVersion(), findHar.getBrowserVersion());
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
//
//    @Test
//    void updateTest() {
//        HarDTO harDTO = new HarDTO(0L, "999", "Chrome", "110");
//        Har newHar = harService.create("999", "Chrome", "110", content);
//
//        doReturn(newHar).when(harRepo).save(har);
//
//        Har savedHar = harService.update(har, harDTO);
//
//        Assertions.assertEquals("999", savedHar.getVersion());
//        Assertions.assertEquals("Chrome", savedHar.getBrowser());
//        Assertions.assertEquals("110", savedHar.getBrowserVersion());
//        Assertions.assertEquals(content, savedHar.getContent());
//    }
}
