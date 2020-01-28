package com.instahipsta.harCRUD.dto;

import com.instahipsta.harCRUD.entity.Request;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class TestProfileDtoTest {
    /*
        public TestProfileDTO() {}

    public TestProfileDTO(Long id, List<Request> requests, int requestsCount) {
        this.id = id;
        this.requests = requests;
        this.requestsCount = requestsCount;
    }

     */
    private TestProfileDTO testProfileDTO;

    @Before
    public void createTestProfileDto() {
        this.testProfileDTO = new TestProfileDTO(1L, new ArrayList<>(), 5);
    }

    @Test
    public void constructorWithoutParametersTest() throws Exception {
        TestProfileDTO testProfileDTO = new TestProfileDTO();
        Assert.assertNotNull(testProfileDTO);
    }

    @Test
    public void constructorWithParametersTest() throws Exception {
        TestProfileDTO testProfileDTO = new TestProfileDTO(1L, new ArrayList<>(), 5);
        Assert.assertEquals(5, testProfileDTO.getRequestsCount());
    }

    @Test
    public void getIdTest() throws Exception {
        Assert.assertEquals((Long)1L, testProfileDTO.getId());
    }

    @Test
    public void setIdTest() throws Exception {
        testProfileDTO.setId(7L);
        Assert.assertEquals((Long)7L, testProfileDTO.getId());
    }

    @Test
    public void getRequestsTest() throws Exception {
        Assert.assertTrue(testProfileDTO.getRequests().isEmpty());
    }

    @Test
    public void setRequestsTest() throws Exception {
        List<Request> requests = new ArrayList<>();
        requests.add(new Request());
        testProfileDTO.setRequests(requests);
        Assert.assertFalse(testProfileDTO.getRequests().isEmpty());
    }

    @Test
    public void getRequestsCountTest() throws Exception {
        Assert.assertEquals(5, testProfileDTO.getRequestsCount());
    }

    @Test
    public void setRequestsCountTest() throws Exception {
        testProfileDTO.setRequestsCount(891);
        Assert.assertEquals(891, testProfileDTO.getRequestsCount());
    }
}
