package com.instahipsta.harCRUD.mapper;

import com.instahipsta.harCRUD.model.dto.RequestDTO;
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
import java.util.Map;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class RequestMapperTest {

    @Autowired
    RequestService requestService;
    @Autowired
    TestProfileService testProfileService;
    @Autowired
    AbstractMapper<Request, RequestDTO> mapper;
    Request request;
    RequestDTO requestDTO;
    Request emptyRequest = new Request();
    RequestDTO emptyRequestDTO = new RequestDTO();

    @Before
    public void initEntityAndDto() {
        Map<String, String> headers = new HashMap<>();
        Map<String, String> params = new HashMap<>();
        TestProfile testProfile = new TestProfile();
        String url = "https://yandex.ru";
        String body = "{}";
        HttpMethod method = HttpMethod.GET;
        long id = 1L;
        double perc = 0;
        long testProfileId = 1L;

        headers.put("header1", "hvalue1");
        headers.put("header2", "hvalue2");

        params.put("param1", "value1");
        params.put("param2", "value2");

        testProfile.setRequestsCount(0);
        testProfile.setRequests(new ArrayList<>());
        TestProfile savedTestProfile = testProfileService.save(testProfile);

        this.request = requestService.save(new Request(url, body, headers, params, method, savedTestProfile));
        this.requestDTO = new RequestDTO(id, url, body, headers, params, method, perc,testProfileId);
    }

    @Test
    public void mapSpecificFieldsEntityDtoTest() throws Exception {
        mapper.mapSpecificFields(request, emptyRequestDTO);
        Assert.assertEquals(request.getTestProfile().getId(), (Long)emptyRequestDTO.getTestProfileId());
    }

    @Test
    public void mapSpecificFieldsDtoEntityTest() throws Exception {
        mapper.mapSpecificFields(requestDTO, emptyRequest);
        Assert.assertEquals((Long)requestDTO.getTestProfileId(), emptyRequest.getTestProfile().getId());
    }

    @Test(expected = NullPointerException.class)
    public void getIdNullSourceTest() throws Exception {
        mapper.mapSpecificFields(null, emptyRequestDTO);
    }

    @Test(expected = NullPointerException.class)
    public void getIdNullSourceIdTest() throws Exception {
        mapper.mapSpecificFields(emptyRequest, emptyRequestDTO);
    }

    @Test
    public void getIdTest() throws Exception {
        mapper.mapSpecificFields(request, emptyRequestDTO);
    }
}
