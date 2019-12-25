package com.instahipsta.harCRUD.impl;

import com.instahipsta.harCRUD.domain.Har;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class HarServiceTest {

    @Autowired
    private HarServiceImpl harService;

    @Before
    public void createHar() {
        Har har = new Har("2", "Chrome", "2", "/olollo");
        harService.save(har);
    }

    @Test
    public void saveTest() throws Exception {
        Har har = new Har("1", "Firefox", "1", "/azaza");
        Har resultHar = harService.save(har);
        Assert.assertEquals(resultHar.getFileName(), har.getFileName());
        Har findHar = harService.findById(resultHar.getId());
        Assert.assertEquals(resultHar, harService.findById(resultHar.getId()));
    }

    @Test
    public void findByIdTest() throws Exception {
        Har har = harService.findById(1L);
        Assert.assertNotNull(har);
    }

    @Test
    public void createHarFromFileTest() throws Exception {
        Har har = harService.createHarFromFile("test_archive.har");
        Assert.assertEquals("70.0.1", har.getBrowserVersion());
    }
}
