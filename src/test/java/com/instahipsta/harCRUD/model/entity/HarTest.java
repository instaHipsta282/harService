package com.instahipsta.harCRUD.model.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.instahipsta.harCRUD.service.HarService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HarTest {

    @Autowired
    private HarService harService;
    @Autowired
    ObjectMapper objectMapper;
    private JsonNode content;
    private Har har;

    @BeforeEach
    public void createHar() throws Exception {
        this.content = objectMapper.readTree("{\n" +
                "  \"headers\": {\n" +
                "    \"name\": \"Last-Modified\",\n" +
                "    \"value\": \"Sun, 01 Dec 2019 21:32:09 GMT\"\n" +
                "  }\n" +
                "}");

        this.har = harService.create("2", "Chrome", "2", content);
    }

    @Test
    public void getVersionTest() {
        Assertions.assertEquals("2", har.getVersion());
    }

    @Test
    public void setVersionTest() {
        har.setVersion("3");
        Assertions.assertEquals("3", har.getVersion());
    }

    @Test
    public void getBrowserTest() {
        Assertions.assertEquals("Chrome", har.getBrowser());
    }

    @Test
    public void setBrowserTest() {
        har.setBrowser("Jojo");
        Assertions.assertEquals("Jojo", har.getBrowser());
    }

    @Test
    public void getBrowserVersionTest() {
        Assertions.assertEquals("2", har.getBrowserVersion());
    }

    @Test
    public void setBrowserVersionTest() {
        har.setBrowserVersion("6");
        Assertions.assertEquals("6", har.getBrowserVersion());
    }

    @Test
    public void getFileNameTest() throws Exception {
        Assertions.assertEquals(content, har.getContent());
    }

    @Test
    public void setFileNameTest() throws Exception {
        JsonNode newContent = objectMapper.readTree("{\n" +
                "  \"headers\": {\n" +
                "    \"name\": \"Fast-Modified\",\n" +
                "    \"value\": \"Sun, 01 Dec 2019 21:32:09 GMT\"\n" +
                "  }\n" +
                "}");
        har.setContent(newContent);
        Assertions.assertEquals(newContent, har.getContent());
    }
}
