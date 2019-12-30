package com.instahipsta.harCRUD.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FileServiceTest {

    @Autowired
    private FileServiceImpl fileService;

    private MultipartFile multipartFile;

    @Before
    public void createMultipartFile() {
        String name = "oyo50.jpg";
        String originalFileName = "oyo50.jpg";
        String contentType = "image/jpeg";
        byte[] content = null;
        Path path = Paths.get("test_downloads/oyo50.jpg");
        try {
            content = Files.readAllBytes(path);
        }
        catch (IOException e) { e.printStackTrace(); }
        this.multipartFile = new MockMultipartFile(name, originalFileName, contentType, content);
    }

    @Test
    public void saveFileTest() throws Exception {
        String fileName = fileService.saveFile(multipartFile);
        Assert.assertNotNull(fileName);
    }
}
