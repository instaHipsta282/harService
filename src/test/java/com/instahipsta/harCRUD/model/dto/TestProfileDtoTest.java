package com.instahipsta.harCRUD.model.dto;

import com.instahipsta.harCRUD.model.entity.Request;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
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

    @BeforeEach
    public void createTestProfileDto() {
        this.testProfileDTO = new TestProfileDTO(1L, new ArrayList<>(), 5);
    }

    @Test
    public void constructorWithoutParametersTest() {
        TestProfileDTO testProfileDTO = new TestProfileDTO();
        Assertions.assertNotNull(testProfileDTO);
    }

    @Test
    public void constructorWithParametersTest() {
        TestProfileDTO testProfileDTO = new TestProfileDTO(1L, new ArrayList<>(), 5);
        Assertions.assertEquals(5, testProfileDTO.getRequestsCount());
        Assertions.assertEquals(1L, testProfileDTO.getId());
        Assertions.assertNotNull(testProfileDTO.getRequests());
    }

    @Test
    public void getIdTest() {
        Assertions.assertEquals((Long)1L, testProfileDTO.getId());
    }

    @Test
    public void setIdTest() {
        testProfileDTO.setId(7L);
        Assertions.assertEquals((Long)7L, testProfileDTO.getId());
    }

    @Test
    public void getRequestsTest() {
        Assertions.assertTrue(testProfileDTO.getRequests().isEmpty());
    }

    @Test
    public void setRequestsTest() {
        List<Request> requests = new ArrayList<>();
        requests.add(new Request());
        testProfileDTO.setRequests(requests);
        Assertions.assertEquals(1, testProfileDTO.getRequests().size());
    }

    @Test
    public void getRequestsCountTest() throws Exception {
        Assertions.assertEquals(5, testProfileDTO.getRequestsCount());
    }

    @Test
    public void setRequestsCountTest() throws Exception {
        testProfileDTO.setRequestsCount(891);
        Assertions.assertEquals(891, testProfileDTO.getRequestsCount());
    }
}
