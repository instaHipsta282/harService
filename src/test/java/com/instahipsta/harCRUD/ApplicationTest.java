package com.instahipsta.harCRUD;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ApplicationTest {

    @Test
    public void applicationTest() throws Exception {
        Application.main(new String[]{"--server.port=8083", "--spring.profiles.active=test"});
    }
}
