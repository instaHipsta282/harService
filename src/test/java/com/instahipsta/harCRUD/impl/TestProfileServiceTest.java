package com.instahipsta.harCRUD.impl;

import com.instahipsta.harCRUD.domain.Request;
import com.instahipsta.harCRUD.domain.TestProfile;
import com.instahipsta.harCRUD.service.RequestService;
import com.instahipsta.harCRUD.service.TestProfileService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TestProfileServiceTest {

    @Autowired
    private TestProfileService testProfileService;
    @Autowired
    private RequestService requestService;
    private String url;
    private String body;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> params = new HashMap<>();
    private HttpMethod httpMethod;


    @Before
    public void initFields() {
        url = "https://yandex.ru";
        body = "{}";
        httpMethod = HttpMethod.GET;

        headers.put("header1", "hvalue1");
        headers.put("header2", "hvalue2");

        params.put("param1", "value1");
        params.put("param2", "value2");
    }

    @Test
    public void saveTest() throws Exception {
        TestProfile testProfile = new TestProfile(new ArrayList<>());
        Request request = new Request(url, body, headers, params, httpMethod, testProfile);
        testProfile.getRequests().add(request);
        TestProfile savedTestProfile = testProfileService.save(testProfile);

        Assert.assertEquals(request, savedTestProfile.getRequests().get(0));
    }
}
