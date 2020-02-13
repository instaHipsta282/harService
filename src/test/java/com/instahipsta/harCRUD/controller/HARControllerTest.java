package com.instahipsta.harCRUD.controller;

import com.instahipsta.harCRUD.model.entity.HAR;
import com.instahipsta.harCRUD.repository.HARRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.AdditionalAnswers;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

    @ParameterizedTest
    @MethodSource("com.instahipsta.harCRUD.arg.HARArgs#fileHarAndIdSource")
    void updateTest(MultipartFile file, HAR har, long id) throws Exception {
        when(harRepo.findById(id)).thenReturn(Optional.of(har));
        when(harRepo.save(any(HAR.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());

        boolean isContains = mockMvc.perform(put("/har/" + id)
                .content(file.getBytes())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andReturn()
                .getResponse()
                .getContentAsString()
                .contains("{\"log\":" +
                        "{\"version\":\"1.2\"," +
                        "\"creator\":" +
                        "{\"name\":\"Firefox\"," +
                        "\"version\":\"70.0.1\"}," +
                        "\"browser\":" +
                        "{\"name\":\"Firefox\"," +
                        "\"version\":\"70.0.1\"}," +
                        "\"pages\":");

        Assertions.assertTrue(isContains);
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
    @MethodSource("com.instahipsta.harCRUD.arg.HARArgs#harAndIdSource")
    void getTest(HAR har, long id) throws Exception {
        when(harRepo.findById(id)).thenReturn(Optional.of(har));

        boolean isContains = mockMvc.perform(get("/har/" + id))
                .andExpect(status().is(200))
                .andReturn()
                .getResponse()
                .getContentAsString()
                .contains("{\"log\":" +
                        "{\"version\":\"1.2\"," +
                        "\"creator\":" +
                        "{\"name\":\"Firefox\"," +
                        "\"version\":\"70.0.1\"}," +
                        "\"browser\":" +
                        "{\"name\":\"Firefox\"," +
                        "\"version\":\"70.0.1\"}," +
                        "\"pages\":");

        Assertions.assertTrue(isContains);
    }

    @ParameterizedTest
    @MethodSource("com.instahipsta.harCRUD.arg.HARArgs#harAndIdSource")
    void getNotFoundTest(HAR har, long id) throws Exception {
        when(harRepo.findById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/har/" + id))
                .andExpect(status().is(404));
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
        when(harRepo.save(any(HAR.class)))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());

        boolean isContains = mockMvc.perform(multipart("/har")
                .file(file)
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().is(200))
                .andReturn()
                .getResponse()
                .getContentAsString()
                .contains("{\"log\":" +
                        "{\"version\":\"1.2\"," +
                        "\"creator\":" +
                        "{\"name\":\"Firefox\"," +
                        "\"version\":\"70.0.1\"}," +
                        "\"browser\":" +
                        "{\"name\":\"Firefox\"," +
                        "\"version\":\"70.0.1\"}");
        Assertions.assertTrue(isContains);
    }

    @ParameterizedTest
    @MethodSource("com.instahipsta.harCRUD.arg.HARArgs#notValidFileSource")
    void addInvalidJsonTest(MockMultipartFile file) throws Exception {
        when(harRepo.save(any(HAR.class)))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());

        mockMvc.perform(multipart("/har")
                .file(file)
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andDo(print())
                .andExpect(status().is(400));
    }
}