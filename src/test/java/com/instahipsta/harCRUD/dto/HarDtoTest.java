package com.instahipsta.harCRUD.dto;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class HarDtoTest {

    @Test
    public void constructorWithParametersTest() throws Exception {
        HarDTO harDTO = new HarDTO(1L, "1.1", "Firefox", "2.0", "empty");
        Assert.assertEquals("Firefox", harDTO.getBrowser());
    }
}
