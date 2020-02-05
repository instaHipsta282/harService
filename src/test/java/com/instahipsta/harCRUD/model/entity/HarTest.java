package com.instahipsta.harCRUD.model.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.instahipsta.harCRUD.service.HarService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class HarTest {

    @Autowired
    private HarService harService;

    @Autowired
    private ObjectMapper objectMapper;

    private JsonNode content;
    private Har har;

    @BeforeEach
    void createHar() throws Exception {
        content = objectMapper.readTree("{\n" +
                "  \"headers\": {\n" +
                "    \"name\": \"Last-Modified\",\n" +
                "    \"value\": \"Sun, 01 Dec 2019 21:32:09 GMT\"\n" +
                "  }\n" +
                "}");

        har = harService.create("2", "Chrome", "2", content);
    }

    @Test
    void getVersionTest() {
        Assertions.assertEquals("2", har.getVersion());
    }

    @Test
    void setVersionTest() {
        har.setVersion("3");
        Assertions.assertEquals("3", har.getVersion());
    }

    @Test
    void getBrowserTest() {
        Assertions.assertEquals("Chrome", har.getBrowser());
    }

    @Test
    void setBrowserTest() {
        har.setBrowser("Jojo");
        Assertions.assertEquals("Jojo", har.getBrowser());
    }

    @Test
    void getBrowserVersionTest() {
        Assertions.assertEquals("2", har.getBrowserVersion());
    }

    @Test
    void setBrowserVersionTest() {
        har.setBrowserVersion("6");
        Assertions.assertEquals("6", har.getBrowserVersion());
    }

    @Test
    void getFileNameTest() {
        Assertions.assertEquals(content, har.getContent());
    }

    @Test
    void setFileNameTest() throws Exception {
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
