package com.instahipsta.harCRUD;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HARServiceApplicationTest {

    @Test
    void applicationTest() {
        HarServiceApplication.main(new String[]{"--server.port=9090"});
    }
}
