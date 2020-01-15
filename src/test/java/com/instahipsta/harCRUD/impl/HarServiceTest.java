package com.instahipsta.harCRUD.impl;

import com.instahipsta.harCRUD.domain.Har;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jca.context.SpringContextResourceAdapter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class HarServiceTest {

    @Autowired
    private HarServiceImpl harService;
    private long harId;
    @Value("${file.filesForTests}")
    private String filesToTests;

    @Before
    public void createHar() {
        Har har = new Har("2", "Chrome", "2", "/olollo");
        Har savedHar = harService.save(har);
        harId = savedHar.getId();
    }

    @Test
    public void saveTest() throws Exception {
        Har har = harService.create("1", "Firefox", "1", "/azaza");
        Har resultHar = harService.save(har);
        Assert.assertEquals(resultHar.getFileName(), har.getFileName());
        Assert.assertEquals(resultHar.getBrowser(), har.getBrowser());
        Assert.assertEquals(resultHar.getBrowserVersion(), har.getBrowserVersion());
        Assert.assertEquals(resultHar.getVersion(), har.getVersion());
    }

    @Test
    public void findByIdTest() throws Exception {
        Har har = harService.findById(harId);
        Assert.assertNotNull(har);
    }

    @Test
    public void createHarFromFileTest() throws Exception {
        File file = new File(filesToTests + "/test_archive.har");
        Har har = harService.createHarFromFile(file.toPath());
        Assert.assertEquals("70.0.1", har.getBrowserVersion());
    }

    @Test
    public void createHarFromFileNegativeTest() throws Exception {
        File file = new File(filesToTests + "/test_archive_doesnt_exist.har");
        Har har = harService.createHarFromFile(file.toPath());
        Assert.assertNull(har);
    }

    @Test
    public void createTest() throws Exception {
        Har har = harService.create("1", "Firefox", "1", "/lolol");
        Assert.assertNotNull(har);
        Assert.assertEquals("1", har.getVersion());
        Assert.assertEquals("Firefox", har.getBrowser());
        Assert.assertEquals("1", har.getBrowserVersion());
        Assert.assertEquals("/lolol", har.getFileName());
    }


}
