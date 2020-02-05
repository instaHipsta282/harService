package com.instahipsta.harCRUD.model.dto;

import com.instahipsta.harCRUD.model.entity.Request;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class TestProfileDtoTest {

    private TestProfileDTO testProfileDTO;

    @BeforeEach
    void createTestProfileDto() {
        this.testProfileDTO = new TestProfileDTO(1L, new ArrayList<>(), 5);
    }

    @Test
    void constructorWithoutParametersTest() {
        TestProfileDTO testProfileDTO = new TestProfileDTO();
        Assertions.assertNotNull(testProfileDTO);
    }

    @Test
    void constructorWithParametersTest() {
        TestProfileDTO testProfileDTO = new TestProfileDTO(1L, new ArrayList<>(), 5);
        Assertions.assertEquals(5, testProfileDTO.getRequestsCount());
        Assertions.assertEquals(1L, testProfileDTO.getId());
        Assertions.assertNotNull(testProfileDTO.getRequests());
    }

    @Test
    void getIdTest() {
        Assertions.assertEquals((Long)1L, testProfileDTO.getId());
    }

    @Test
    void setIdTest() {
        testProfileDTO.setId(7L);
        Assertions.assertEquals((Long)7L, testProfileDTO.getId());
    }

    @Test
    void getRequestsTest() {
        Assertions.assertTrue(testProfileDTO.getRequests().isEmpty());
    }

    @Test
    void setRequestsTest() {
        List<Request> requests = new ArrayList<>();
        requests.add(new Request());
        testProfileDTO.setRequests(requests);
        Assertions.assertEquals(1, testProfileDTO.getRequests().size());
    }

    @Test
    void getRequestsCountTest() {
        Assertions.assertEquals(5, testProfileDTO.getRequestsCount());
    }

    @Test
    void setRequestsCountTest() {
        testProfileDTO.setRequestsCount(891);
        Assertions.assertEquals(891, testProfileDTO.getRequestsCount());
    }
}
