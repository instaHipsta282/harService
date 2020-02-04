package com.instahipsta.harCRUD;

import com.instahipsta.harCRUD.config.AppConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(AppConfiguration.class)
@SpringBootApplication
public class HarServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(HarServiceApplication.class, args);
    }
}
