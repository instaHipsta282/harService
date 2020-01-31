//package com.instahipsta.harCRUD.impl;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.instahipsta.harCRUD.model.entity.Request;
//import com.instahipsta.harCRUD.model.entity.TestProfile;
//import com.instahipsta.harCRUD.service.RequestService;
//import com.instahipsta.harCRUD.service.TestProfileService;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpMethod;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.io.File;
//import java.nio.file.Files;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest
//@ActiveProfiles("test")
//public class TestProfileServiceTest {
//
//    @Value("${file.filesForTests}")
//    private String filesForTests;
//
//    @Autowired
//    ObjectMapper objectMapper;
//    @Autowired
//    private TestProfileService testProfileService;
//    @Autowired
//    private RequestService requestService;
//    private String url;
//    private String body;
//    private Map<String, String> headers = new HashMap<>();
//    private Map<String, String> params = new HashMap<>();
//    private HttpMethod httpMethod;
//
//
//    @Before
//    public void initFields() {
//        url = "https://yandex.ru";
//        body = "{}";
//        httpMethod = HttpMethod.GET;
//
//        headers.put("header1", "hvalue1");
//        headers.put("header2", "hvalue2");
//
//        params.put("param1", "value1");
//        params.put("param2", "value2");
//    }
//
//    @Test
//    public void createTest() throws Exception {
//        TestProfile testProfile = testProfileService.create();
//        Assert.assertNotNull(testProfile);
//    }
//
//    @Test
//    public void createWithConstructorTest() throws Exception {
//        List<Request> list = new ArrayList<>();
//        TestProfile testProfile = testProfileService.create(list);
//        Assert.assertNotNull(testProfile.getRequests());
//    }
//
//    @Test
//    public void harToTestProfileTest() throws Exception {
//        File file = new File(filesForTests + "/test_archive.har");
//        TestProfile testProfile = testProfileService.harToTestProfile(Files.readAllBytes(file.toPath()));
//        Assert.assertNotNull(testProfile);
//    }
//
//    @Test(expected = NullPointerException.class)
//    public void harToTestProfileNegativeTest() throws Exception {
//        File file = new File(filesForTests + "/oyo50.jpg");
//        TestProfile testProfile = testProfileService.harToTestProfile(Files.readAllBytes(file.toPath()));
//    }
//
//
//    @Test
//    public void saveTest() throws Exception {
//        TestProfile testProfile = testProfileService.create(new ArrayList<>());
//        Request request = requestService.create(url, body, headers, params, httpMethod, testProfile);
//        testProfile.getRequests().add(request);
//        TestProfile savedTestProfile = testProfileService.save(testProfile);
//
//        Assert.assertEquals(request, savedTestProfile.getRequests().get(0));
//    }
//
//    @Test
//    public void entryToRequestTest() throws Exception {
//        String url = "https://e.mail.ru/api/v1/utils/xray/batch?p=octavius&" +
//                "email=stepaden%40mail.ru&split=s&pgid=k3zwmb7k.iw&o_ss=AQ%3D%3D.s&o_v=147";
//        File file = new File(filesForTests + "/test2.json");
//        JsonNode jsonNode = objectMapper.readTree(file);
//        Request request = testProfileService.entryToRequest(jsonNode, new TestProfile());
//        Assert.assertNotNull(request);
//        Assert.assertEquals(url, request.getUrl());
//    }
//}
