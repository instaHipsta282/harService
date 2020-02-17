package com.instahipsta.harCRUD.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.instahipsta.harCRUD.model.dto.HAR.HARDto;
import com.instahipsta.harCRUD.model.entity.HAR;
import com.instahipsta.harCRUD.repository.HARRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.AdditionalAnswers;
import org.mockito.MockitoAnnotations;
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

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HARControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HARRepo harRepo;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @ParameterizedTest
    @MethodSource("com.instahipsta.harCRUD.arg.HARArgs#newFileHarAndIdSource")
    void updateTest(MultipartFile file, HAR har, long id) throws Exception {
        when(harRepo.findById(id)).thenReturn(Optional.of(har));
        when(harRepo.save(any(HAR.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());

        byte[] content = mockMvc.perform(put("/har/" + id)
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
        when(harRepo.findById(id)).thenReturn(Optional.empty());

        mockMvc.perform(put("/har/" + id)
                .content(file.getBytes())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));
    }

    @ParameterizedTest
    @MethodSource("com.instahipsta.harCRUD.arg.HARArgs#notValidFileAndIdSource")
    void updateNotValidJsonTest(MultipartFile file, long id) throws Exception {
        mockMvc.perform(put("/har/" + id)
                .content(file.getBytes())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }

    @ParameterizedTest
    @MethodSource("com.instahipsta.harCRUD.arg.HARArgs#fileAndIdSource")
    void updateRuntimeExTest(MultipartFile file, long id) throws Exception {
        when(harRepo.findById(id)).thenThrow(RuntimeException.class);

         mockMvc.perform(put("/har/" + id)
                .content(file.getBytes())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(500));
    }

    @ParameterizedTest
    @MethodSource("com.instahipsta.harCRUD.arg.HARArgs#harAndIdSource")
    void getTest(HAR har, long id) throws Exception {
        when(harRepo.findById(id)).thenReturn(Optional.of(har));

        byte[] content = mockMvc.perform(get("/har/" + id))
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
        when(harRepo.findById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/har/" + id))
                .andExpect(status().is(404));
    }

    @ParameterizedTest
    @ValueSource(longs = 1L)
    void getRuntimeExTest(long id) throws Exception {
        when(harRepo.findById(id)).thenThrow(RuntimeException.class);

        mockMvc.perform(get("/har/" + id))
                .andExpect(status().is(500));
    }

    @ParameterizedTest
    @MethodSource("com.instahipsta.harCRUD.arg.HARArgs#harAndIdSource")
    void deleteTest(HAR har, long id) throws Exception {
        when(harRepo.findById(id)).thenReturn(Optional.of(har));

        mockMvc.perform(delete("/har/" + id))
                .andExpect(status().is(204));
    }

    @ParameterizedTest
    @MethodSource("com.instahipsta.harCRUD.arg.HARArgs#fileSource")
    void addTest(MockMultipartFile file) throws Exception {
        when(harRepo.save(any()))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());

        doNothing().when(rabbitTemplate).convertAndSend(any());

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
        when(harRepo.save(any(HAR.class)))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());

        mockMvc.perform(multipart("/har")
                .file(file)
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().is(400));
    }

    @ParameterizedTest
    @MethodSource("com.instahipsta.harCRUD.arg.HARArgs#fileSource")
    void addRuntimeExTest(MockMultipartFile file) throws Exception {
        when(harRepo.save(any(HAR.class)))
                .thenThrow(RuntimeException.class);

        mockMvc.perform(multipart("/har")
                .file(file)
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().is(500));
    }
}