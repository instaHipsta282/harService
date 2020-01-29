package com.instahipsta.harCRUD.impl;

import com.instahipsta.harCRUD.model.entity.Har;
import com.instahipsta.harCRUD.model.dto.HarDTO;
import com.instahipsta.harCRUD.service.impl.HarServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;

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
        HarDTO savedHar = harService.save(har);
        harId = savedHar.getId();
    }

    @Test
    public void saveTest() throws Exception {
        Har har = harService.create("1", "Firefox", "1", "/azaza");
        HarDTO resultHar = harService.save(har);
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
