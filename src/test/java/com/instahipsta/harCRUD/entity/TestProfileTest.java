package com.instahipsta.harCRUD.entity;

import com.instahipsta.harCRUD.model.entity.Request;
import com.instahipsta.harCRUD.model.entity.TestProfile;
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

import static java.util.Arrays.asList;

@ActiveProfiles("test")
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class TestProfileTest {

    @Autowired
    private TestProfileService testProfileService;
    @Autowired
    private RequestService requestService;
    private String url;
    private String body;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> params = new HashMap<>();
    private HttpMethod httpMethod;
    private TestProfile testProfile;
    private TestProfile fullTestProfile;

    @Before
    public void initFields() {
        url = "https://yandex.ru";
        body = "{}";
        httpMethod = HttpMethod.GET;
        testProfile = new TestProfile();

        testProfile = testProfileService.save(testProfile);

        headers.put("header1", "hvalue1");
        headers.put("header2", "hvalue2");

        params.put("param1", "value1");
        params.put("param2", "value2");
        Request request = requestService.create(url, body, headers, params, httpMethod, testProfile);
        fullTestProfile = testProfileService.create(asList(request));
    }

    @Test
    public void constructorWithoutParametersTest() throws Exception {
        TestProfile testProfile = new TestProfile();
        Assert.assertNotNull(testProfile);
    }

    @Test
    public void constructorWithParametersTest() throws Exception {
        Request request = requestService.create(url, body, headers, params, httpMethod, testProfile);
        TestProfile testProfile = testProfileService.create(asList(request));
        Assert.assertEquals("https://yandex.ru", testProfile.getRequests().get(0).getUrl());
    }

    @Test
    public void getIdTest() throws Exception {
        Long id = testProfile.getId();
        Assert.assertNotNull(id);
    }

    @Test
    public void getRequestsTest() throws Exception {
        Assert.assertEquals(1, fullTestProfile.getRequests().size());
    }

    @Test
    public void setRequestsTest() throws Exception {
        List<Request> requests = new ArrayList<>();
        Request request = requestService.create(url, "{body}", headers, params, httpMethod, testProfile);
        testProfile.setRequests(asList(request));
        Assert.assertEquals("{body}", testProfile.getRequests().get(0).getBody());
    }

    @Test
    public void getRequestsCountTest() throws Exception {
        Assert.assertEquals(1, fullTestProfile.getRequestsCount());
    }

    @Test
    public void setRequestsCountTest() throws Exception {
        fullTestProfile.setRequestsCount(561);
        Assert.assertEquals(561, fullTestProfile.getRequestsCount());
    }
}
