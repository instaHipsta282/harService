package com.instahipsta.harCRUD.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class HarControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private MockMultipartFile multipartFile;

    @Before
    public void createMultipartFile() {
        Path path = Paths.get("test_downloads/test_archive.har");
        String name = "file";
        String originalFileName = "test_archive.har";
        String contentType = "application/json";
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.multipartFile = new MockMultipartFile(name, originalFileName, contentType, content);
    }

    @Test
    public void uploadHarTest() throws Exception {
         boolean isOk = this.mockMvc.perform(multipart("/har/upload")
                    .file(multipartFile)
                    .contentType(MediaType.MULTIPART_FORM_DATA))
//                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andReturn()
                    .getResponse()
                    .getContentAsString()
                    .contains("\"browser\":\"Firefox\"");
        Assert.assertTrue(isOk);
    }

}
