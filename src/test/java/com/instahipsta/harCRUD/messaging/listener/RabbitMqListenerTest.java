//package com.instahipsta.harCRUD.messaging.listener;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//
//@ActiveProfiles("test")
//@SpringBootTest
//@RunWith(SpringJUnit4ClassRunner.class)
//public class RabbitMqListenerTest {
//
//    @Autowired
//    private RabbitMqListener rabbitMqListener;
//    @Value("${file.filesForTests}")
//    private String filesForTests;
//    private StringBuilder message = new StringBuilder();
//
//    @Before
//    public void getMessage() {
//        try (BufferedReader reader = new BufferedReader(new FileReader(filesForTests + "/test_archive.har"))) {
//            String line = "";
//            while ((line = reader.readLine()) != null) {
//                message.append(line).append("\n\r");
//            }
//        }
//        catch (IOException e) { e.printStackTrace(); }
//    }
//
//    @Test
//    public void harWorker() throws Exception {
//        rabbitMqListener.harWorker(message.toString());
//    }
//
//}
