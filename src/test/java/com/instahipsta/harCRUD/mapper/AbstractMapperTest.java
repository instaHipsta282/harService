package com.instahipsta.harCRUD.mapper;

import com.instahipsta.harCRUD.model.dto.RequestDTO;
import com.instahipsta.harCRUD.model.dto.TestProfileDTO;
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
public class AbstractMapperTest {

    @Autowired
    RequestService requestService;
    @Autowired
    TestProfileService testProfileService;
    @Autowired
    AbstractMapper<Request, RequestDTO> mapper;
    @Autowired
    AbstractMapper<TestProfile, TestProfileDTO> abstractMapper;
    Request request;
    RequestDTO requestDTO;
    TestProfile testProfile;
    TestProfile emptyTestProfile = new TestProfile();
    TestProfileDTO emptyTestProfileDTO = new TestProfileDTO();
    TestProfileDTO testProfileDTO;

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

        Request newRequest = requestService.create(url, body, headers, params, method, savedTestProfile);
        this.request = requestService.save(newRequest);
        this.requestDTO = new RequestDTO(id, url, body, headers, params, method, perc,testProfileId);

        this.testProfile = testProfileService.create(new ArrayList<>());
        this.testProfileDTO = new TestProfileDTO(1L, new ArrayList<>(), 7);
    }

    @Test
    public void toEntityTest() throws Exception {
        Request mappedRequest = mapper.toEntity(requestDTO);
        Assert.assertEquals(mappedRequest.getTestProfile().getId(), requestDTO.getTestProfileId());
    }

    @Test
    public void toEntityWithNullDtoTest() throws Exception {
        Request request = mapper.toEntity(null);
        Assert.assertNull(request);
    }

    @Test
    public void toDtoWithNullEntityTest() throws Exception {
        RequestDTO requestDTO = mapper.toDto(null);
        Assert.assertNull(requestDTO);
    }

    @Test
    public void toDtoTest() throws Exception {
        RequestDTO mappedDTO = mapper.toDto(request);
        Assert.assertEquals(mappedDTO.getTestProfileId(), request.getTestProfile().getId());
    }

    @Test
    public void mapSpecificFieldsDtoEntityTest() throws Exception {
        abstractMapper.mapSpecificFields(testProfileDTO, emptyTestProfile);
    }

    @Test
    public void mapSpecificFieldsEntityDtoTest() throws Exception {
        abstractMapper.mapSpecificFields(testProfile, emptyTestProfileDTO);
    }

}
