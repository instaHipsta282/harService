package com.instahipsta.harCRUD.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.instahipsta.harCRUD.model.dto.HAR.HARDto;
import com.instahipsta.harCRUD.model.entity.HAR;
import com.instahipsta.harCRUD.repository.HARRepo;
import com.instahipsta.harCRUD.service.impl.HarServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class HARControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HARRepo harRepo;

    @Autowired
    private ObjectMapper objectMapper;

    static {
        GenericContainer rabbitmq = new GenericContainer("rabbitmq:3-management")
                .withExposedPorts(5672)
                .withEnv("RABBITMQ_DEFAULT_USER", "root")
                .withEnv("RABBITMQ_DEFAULT_PASS", "root");
        rabbitmq.start();

        System.setProperty("spring.rabbitmq.host", rabbitmq.getContainerIpAddress());
        System.setProperty("spring.rabbitmq.port", rabbitmq.getFirstMappedPort().toString());

    }

    @BeforeEach
    void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @ParameterizedTest
    @MethodSource("com.instahipsta.harCRUD.arg.HARArgs#newFileAndHarSource")
    void updateTest(MultipartFile file, HAR har) throws Exception {
        long harId = harRepo.save(har).getId();

        byte[] content = mockMvc.perform(put("/har/" + harId)
                .content(file.getBytes())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andReturn()
                .getResponse().getContentAsByteArray();

        HARDto dto = objectMapper.readValue(content, HARDto.class);

        Assertions.assertEquals("1.15", dto.getLog().getVersion());
        Assertions.assertEquals("Chrome", dto.getLog().getBrowser().getName());
        Assertions.assertEquals(HttpMethod.DELETE, dto.getLog().getEntries().get(0).getRequest().getMethod());
    }

    @ParameterizedTest
    @MethodSource("com.instahipsta.harCRUD.arg.HARArgs#fileAndIdSource")
    void updateNotFoundTest(MultipartFile file, long id) throws Exception {
        harRepo.deleteAll();

        mockMvc.perform(put("/har/" + id)
                .content(file.getBytes())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));
    }

    @ParameterizedTest
    @MethodSource("com.instahipsta.harCRUD.arg.HARArgs#notValidFileAndHarSource")
    void updateNotValidJsonTest(MultipartFile file, HAR har) throws Exception {
        long harId = harRepo.save(har).getId();

        mockMvc.perform(put("/har/" + harId)
                .content(file.getBytes())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }

    @ParameterizedTest
    @MethodSource("com.instahipsta.harCRUD.arg.HARArgs#harSource")
    void getTest(HAR har) throws Exception {
        long harId = harRepo.save(har).getId();

        byte[] content = mockMvc.perform(get("/har/" + harId))
                .andExpect(status().is(200))
                .andReturn()
                .getResponse().getContentAsByteArray();

        HARDto dto = objectMapper.readValue(content, HARDto.class);

        Assertions.assertEquals("1.2", dto.getLog().getVersion());
        Assertions.assertEquals("Firefox", dto.getLog().getBrowser().getName());
        Assertions.assertEquals(HttpMethod.GET, dto.getLog().getEntries().get(0).getRequest().getMethod());
    }

    @ParameterizedTest
    @ValueSource(longs = 1L)
    void getNotFoundTest(long id) throws Exception {
        harRepo.deleteAll();

        mockMvc.perform(get("/har/" + id))
                .andExpect(status().is(404));
    }

    @ParameterizedTest
    @MethodSource("com.instahipsta.harCRUD.arg.HARArgs#harSource")
    void deleteTest(HAR har) throws Exception {
        long harId = harRepo.save(har).getId();

        mockMvc.perform(delete("/har/" + harId))
                .andExpect(status().is(204));
    }

    @ParameterizedTest
    @MethodSource("com.instahipsta.harCRUD.arg.HARArgs#fileSource")
    void addTest(MockMultipartFile file) throws Exception {

        byte[] content = mockMvc.perform(multipart("/har")
                .file(file)
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().is(200))
                .andReturn()
                .getResponse()
                .getContentAsByteArray();

        HARDto dto = objectMapper.readValue(content, HARDto.class);

        Assertions.assertEquals("1.2", dto.getLog().getVersion());
        Assertions.assertEquals("Firefox", dto.getLog().getBrowser().getName());
        Assertions.assertEquals(HttpMethod.GET, dto.getLog().getEntries().get(0).getRequest().getMethod());
    }

    @ParameterizedTest
    @MethodSource("com.instahipsta.harCRUD.arg.HARArgs#notValidFileSource")
    void addInvalidJsonTest(MockMultipartFile file) throws Exception {
        mockMvc.perform(multipart("/har")
                .file(file)
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().is(400));
    }
}