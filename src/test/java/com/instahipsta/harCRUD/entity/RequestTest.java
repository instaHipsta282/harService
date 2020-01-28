package com.instahipsta.harCRUD.entity;

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

@ActiveProfiles("test")
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class RequestTest {

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
    private Request request;

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
        this.request = requestService.create(url, body, headers, params, httpMethod, testProfile);
    }

    @Test
    public void constructorWithoutArguments() throws Exception {
        Request request = new Request();
        Assert.assertNotNull(request);
    }

    @Test
    public void constructorWithArguments() throws Exception {
        Request request = requestService.create(url, body, headers, params, httpMethod, testProfile);
        Assert.assertEquals("https://yandex.ru", request.getUrl());
    }

    @Test
    public void getIdTest() throws Exception {
        request.setId(1L);
        Long id = request.getId();
        Assert.assertNotNull(id);
    }

    @Test
    public void setIdTest() throws Exception {
        request.setId(376L);
        long newId = request.getId();
        Assert.assertEquals(376, newId);
    }

    @Test
    public void getUrlTest() throws Exception {
        Assert.assertEquals("https://yandex.ru", request.getUrl());
    }

    @Test
    public void setUrlTest() throws Exception {
        request.setUrl("hohoh");
        Assert.assertEquals("hohoh", request.getUrl());
    }

    @Test
    public void getBodyTest() throws Exception {
        Assert.assertEquals("{}", request.getBody());
    }

    @Test
    public void setBodyTest() throws Exception {
        request.setBody("body");
        Assert.assertEquals("body", request.getBody());
    }

    @Test
    public void getHeadersTest() throws Exception {
        Assert.assertEquals(2, request.getHeaders().size());
    }

    @Test
    public void setHeadersTest() throws Exception {
        Map<String, String> newHeaders = new HashMap<>();
        newHeaders.put("header1k", "header1v");
        newHeaders.put("header2k", "header2v");
        newHeaders.put("header3k", "header3v");
        request.setHeaders(newHeaders);
        Assert.assertEquals(3, request.getHeaders().size());
    }

    @Test
    public void getParamsTest() throws Exception {
        Assert.assertEquals(2, request.getParams().size());
    }

    @Test
    public void setParamsTest() throws Exception {
        Map<String, String> newParams = new HashMap<>();
        newParams.put("param1k", "param1v");
        newParams.put("param2k", "param2v");
        newParams.put("param3k", "param3v");
        request.setParams(newParams);
        Assert.assertEquals(3, request.getParams().size());
    }

    @Test
    public void getMethodTest() throws Exception {
        Assert.assertEquals(HttpMethod.GET, request.getMethod());
    }

    @Test
    public void setMethodTest() throws Exception {
        request.setMethod(HttpMethod.DELETE);
        Assert.assertEquals(HttpMethod.DELETE, request.getMethod());
    }

    @Test
    public void getPercTest() throws Exception {
        Assert.assertEquals((Double)0.0, request.getPerc());
    }

    @Test
    public void setPercTest() throws Exception {
        request.setPerc(0.5);
        Assert.assertEquals((Double)0.5, request.getPerc());
    }

    @Test
    public void getTestProfileTest() throws Exception {
        Assert.assertNotNull(request.getTestProfile());
    }

    @Test
    public void setTestProfileTest() throws Exception {
        List<Request> requests = new ArrayList<>();
        requests.add(requestService.create("yandex.ru", body, headers, params, httpMethod, testProfile));
        requests.add(requestService.create("google.com", body, headers, params, httpMethod, testProfile));
        requests.add(requestService.create("rambler.ru", body, headers, params, httpMethod, testProfile));
        TestProfile newTestProfile = new TestProfile(requests);
        request.setTestProfile(newTestProfile);
        Assert.assertEquals(3, request.getTestProfile().getRequests().size());
    }

}
