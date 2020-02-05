package com.instahipsta.harCRUD.model.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class HarDtoTest {

    private HarDTO harDTO;

    @BeforeEach
    void createHarDto() {
        this.harDTO = new HarDTO(1L, "1", "Mozilla", "1");
    }

    @Test
    void noArgsConstructorTest() {
        HarDTO newHarDTO = new HarDTO();
        Assertions.assertNotNull(newHarDTO);
    }

    @Test
    void allArgsConstructorTest() {
        HarDTO testHarDTO = new HarDTO(98L, "betta 0.1", "Yandex Browser", "009");
        Assertions.assertEquals(98L, testHarDTO.getId());
        Assertions.assertEquals("betta 0.1", testHarDTO.getVersion());
        Assertions.assertEquals("Yandex Browser", testHarDTO.getBrowser());
        Assertions.assertEquals("009", testHarDTO.getBrowserVersion());
    }

    @Test
    void getIdTest() {
        Assertions.assertEquals(1L, harDTO.getId());
    }

    @Test
    void setIdTest() {
        harDTO.setId(100L);
        Assertions.assertEquals(100L, harDTO.getId());
    }

    @Test
    void getVersionTest() {
        Assertions.assertEquals("1", harDTO.getVersion());
    }

    @Test
    void setVersionTest() {
        harDTO.setVersion("2XL");
        Assertions.assertEquals("2XL", harDTO.getVersion());
    }

    @Test
    void getBrowserTest() {
        Assertions.assertEquals("Mozilla", harDTO.getBrowser());
    }

    @Test
    void setBrowserTest() {
        harDTO.setBrowser("Chrome");
        Assertions.assertEquals("Chrome", harDTO.getBrowser());
    }

    @Test
    void getBrowserVersionTest() {
        Assertions.assertEquals("1", harDTO.getBrowserVersion());
    }

    @Test
    void setBrowserVersionTest() {
        harDTO.setBrowserVersion("1.0.78");
        Assertions.assertEquals("1.0.78", harDTO.getBrowserVersion());
    }

}
