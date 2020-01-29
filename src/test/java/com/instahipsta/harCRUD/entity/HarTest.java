package com.instahipsta.harCRUD.entity;

import com.instahipsta.harCRUD.model.entity.Har;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ActiveProfiles("test")
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class HarTest {

    private String version;
    private String browser;
    private String browserVersion;
    private String fileName;
    private Har har;

    @Before
    public void createHar() {
        this.version = "1";
        this.browser = "Yandex";
        this.browserVersion = "1";
        this.fileName = "/huh";
        this.har = new Har("2", "Chrome", "2", "/olollo");
    }

    @Test
    public void constructorWithoutArguments() throws Exception {
        Har har = new Har();
        Assert.assertNotNull(har);
    }

    @Test
    public void constructorWithArguments() throws Exception {
        Har har = new Har(version, browser, browserVersion, fileName);
        Assert.assertEquals("Yandex", har.getBrowser());
    }

    @Test
    public void getIdTest() throws Exception {
        har.setId(9L);
        Assert.assertNotNull(har.getId());
    }

    @Test
    public void setIdTest() throws Exception {
        har.setId(99L);
        Assert.assertEquals((Long) 99L, har.getId());
    }

    @Test
    public void getVersionTest() throws Exception {
        Assert.assertEquals("2", har.getVersion());
    }

    @Test
    public void setVersionTest() throws Exception {
        har.setVersion("3");
        Assert.assertEquals("3", har.getVersion());
    }

    @Test
    public void getBrowserTest() throws Exception {
        Assert.assertEquals("Chrome", har.getBrowser());
    }

    @Test
    public void setBrowserTest() throws Exception {
        har.setBrowser("Jojo");
        Assert.assertEquals("Jojo", har.getBrowser());
    }

    @Test
    public void getBrowserVersionTest() throws Exception {
        Assert.assertEquals("2", har.getBrowserVersion());
    }

    @Test
    public void setBrowserVersionTest() throws Exception {
        har.setBrowserVersion("6");
        Assert.assertEquals("6", har.getBrowserVersion());
    }

    @Test
    public void getFileNameTest() throws Exception {
        Assert.assertEquals("/olollo", har.getFileName());
    }

    @Test
    public void setFileNameTest() throws Exception {
        har.setFileName("/ohh");
        Assert.assertEquals("/ohh", har.getFileName());
    }
}
