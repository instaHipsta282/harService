//package com.instahipsta.harCRUD.controller;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.instahipsta.harCRUD.HarServiceApplication;
//import com.instahipsta.harCRUD.model.dto.HarDTO;
//import com.instahipsta.harCRUD.model.entity.Har;
//import com.instahipsta.harCRUD.service.HarService;
//import com.instahipsta.harCRUD.service.TestProfileServiceTest;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//import static org.mockito.Mockito.doReturn;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(HarController.class)
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = HarServiceApplication.class)
//public class HarControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private ObjectMapper objectMapper;
//    @MockBean
//    private HarService harService;
//    private MockMultipartFile multipartFile;
//    private MockMultipartFile multipartFileEmptyOriginalFilename;
//    private MockMultipartFile multipartFileWithoutHarVersion;
//    @Value("${file.filesForTests}")
//    private String filesForTests;
//    private JsonNode content;
//
//
//    @BeforeEach
//    public void createMultipartFile() throws Exception {
//        MockitoAnnotations.initMocks(TestProfileServiceTest.class);
////
////        ReflectionTestUtils.setField(harService, "harExchange",
////                harExchange);
////        ReflectionTestUtils.setField(harService, "harRoutingKey",
////                harRoutingKey);
//
//        content = objectMapper.readTree("{\n" +
//                "  \"headers\": {\n" +
//                "    \"name\": \"Last-Modified\",\n" +
//                "    \"value\": \"Sun, 01 Dec 2019 21:32:09 GMT\"\n" +
//                "  }\n" +
//                "}");
//
//
//        Path path = Paths.get(filesForTests + "/test_archive.har");
//
//        String name = "file";
//        String originalFileName = "test_archive.har";
//        String contentType = "application/json";
//        byte[] content = null;
//
//        Path pathWithoutHarVersion = Paths.get(filesForTests + "/test_archive_without_har_version.har");
//        byte[] contentWithoutHarVersion = null;
//
//        try {
//            content = Files.readAllBytes(path);
//            contentWithoutHarVersion = Files.readAllBytes(pathWithoutHarVersion);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        this.multipartFile = new MockMultipartFile(name, originalFileName, contentType, content);
//        this.multipartFileEmptyOriginalFilename = new MockMultipartFile(name, "", contentType, content);
//        this.multipartFileWithoutHarVersion
//                = new MockMultipartFile(name, originalFileName, contentType, contentWithoutHarVersion);
//    }
//
//    @Test
//    public void uploadHarTest() throws Exception {
//        Har har = harService.create("1", "Mozilla", "12", content);
//        doReturn(har).when(harService).createHarFromFile(content.toString().getBytes());
//        doReturn(har).when(harService).save(har);
//
//        this.mockMvc.perform(multipart("/har/upload")
//                .file(multipartFile)
//                .contentType(MediaType.MULTIPART_FORM_DATA))
//                .andExpect(status().is2xxSuccessful())
//                .andDo(print());
////        Assertions.assertTrue(isOk);
//    }
//
//    @Test
//    public void uploadHarSecondIfNegativeTest() throws Exception {
//        boolean isOk = this.mockMvc.perform(multipart("/har/upload")
//                .file(multipartFileWithoutHarVersion)
//                .contentType(MediaType.MULTIPART_FORM_DATA))
//                .andReturn()
//                .getResponse()
//                .getContentAsString()
//                .contains("null");
//        Assertions.assertTrue(isOk);
//    }
//}
