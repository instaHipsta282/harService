package com.instahipsta.harCRUD.messaging.listener;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

@SpringBootTest
@AutoConfigureMockMvc
public class RabbitMqListenerTest {

    @Autowired
    private RabbitMqListener rabbitMqListener;

    static Stream<Arguments> harWorkerSource() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File( "filesForTests/test_archive.har");
        JsonNode node = objectMapper.readTree(file)
                .path("log")
                .path("entries");

        return Stream.of(Arguments.of(node));
    }

    @ParameterizedTest
    @MethodSource("harWorkerSource")
    void harWorkerTest(JsonNode node) {
        Assertions.assertDoesNotThrow(() -> rabbitMqListener.harWorker(node));
    }
}
