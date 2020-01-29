package com.instahipsta.harCRUD.impl;

import com.instahipsta.harCRUD.service.impl.FileServiceImpl;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FileServiceTest {

    @Value("${file.downloads}")
    private String downloadPath;
    @Autowired
    private FileServiceImpl fileService;
    private MultipartFile multipartFile;
    private MultipartFile longMultipartFile;
    @Value("${file.filesForTests}")
    private String filesForTests;

    @Before
    public void createMultipartFile() {
        String name = "oyo50.jpg";
        String longName = "/1111111111111111111111111111111111111111111111111111111" +
                "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111";
        String originalFileName = "oyo50.jpg";
        String contentType = "image/jpeg";
        byte[] content = null;
        Path path = Paths.get(filesForTests + "/oyo50.jpg");
        try {
            content = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.multipartFile = new MockMultipartFile(name, originalFileName, contentType, content);
        this.longMultipartFile = new MockMultipartFile(longName, longName, contentType, content);
    }

    @Test
    public void saveFileTest() throws Exception {
        String fileName = fileService.saveFile(multipartFile);
        Assert.assertNotNull(fileName);
    }

    @Test
    public void saveFileWithNullTest() throws Exception {
        String resultFileName = fileService.saveFile(null);
        Assert.assertNull(resultFileName);
    }

    @Test
    public void saveFileWithoutDirTest() throws Exception {
        File file = new File(downloadPath);
        FileUtils.deleteDirectory(file);
        String resultFileName = fileService.saveFile(multipartFile);
        Assert.assertNotNull(resultFileName);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void saveFileNegativeTest() throws Exception {
        String fileName = fileService.saveFile(longMultipartFile);
        Assert.assertNull(fileName);
    }
}

