package com.instahipsta.harCRUD.messaging.listener;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.instahipsta.harCRUD.model.entity.TestProfile;
import com.instahipsta.harCRUD.service.TestProfileService;
import com.instahipsta.harCRUD.service.TestProfileServiceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;

import static org.mockito.Mockito.doThrow;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class RabbitMqListenerTest {

    @Value("${file.filesForTests}")
    private String filesForTests;
    @Autowired
    @InjectMocks
    private RabbitMqListener rabbitMqListener;
    @Mock
    private TestProfileService testProfileService;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void initFields() throws Exception {
        MockitoAnnotations.initMocks(TestProfileServiceTest.class);
    }

    @Test
    public void harWorker() throws Exception {
        File file = new File(filesForTests + "/test_archive.har");
        JsonNode node = objectMapper.readTree(file)
                .path("log")
                .path("entries");
        TestProfile testProfile = new TestProfile();

        doThrow(new RuntimeException()).when(testProfileService).save(testProfile);

        Assertions.assertThrows(RuntimeException.class, () -> rabbitMqListener.harWorker(node));
    }


}
