package com.instahipsta.harCRUD.controller;

import com.instahipsta.harCRUD.model.entity.Har;
import com.instahipsta.harCRUD.repository.HarRepo;
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

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class HarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HarRepo harRepo;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @ParameterizedTest
    @MethodSource("com.instahipsta.harCRUD.provider.HarProvider#jsonHarAndIdSource")
    void updateTest(String harDto, Har har, long id) throws Exception {
        when(harRepo.findById(id)).thenReturn(Optional.of(har));
        when(harRepo.save(any(Har.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());

        mockMvc.perform(put("/har/" + id)
                .content(harDto)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content()
                        .json("{\"id\":1," +
                                "\"version\":\"1.2\"," +
                                "\"browser\":\"Firefox\"," +
                                "\"browserVersion\":\"70.0.1\"}"));
    }

    @ParameterizedTest
    @MethodSource("com.instahipsta.harCRUD.provider.HarProvider#harAndIdSource")
    void getTest(Har har, long id) throws Exception {
        when(harRepo.findById(id)).thenReturn(Optional.of(har));

        mockMvc.perform(get("/har/" + id))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content()
                        .json("{\"id\":1," +
                                "\"version\":\"1.0.1\"," +
                                "\"browser\":\"Explorer\"," +
                                "\"browserVersion\":\"1.0.0\"}"));
    }

    @ParameterizedTest
    @MethodSource("com.instahipsta.harCRUD.provider.HarProvider#harAndIdSource")
    void deleteTest(Har har, long id) throws Exception {
        when(harRepo.findById(id)).thenReturn(Optional.of(har));

        mockMvc.perform(delete("/har/" + id))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @ParameterizedTest
    @MethodSource("com.instahipsta.harCRUD.provider.HarProvider#multipartFileSource")
    void addTest(MockMultipartFile file) throws Exception {
        when(harRepo.save(any(Har.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());

        mockMvc.perform(multipart("/har")
                .file(file)
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content()
                        .json("{\"id\":0," +
                                "\"version\":\"1.2\"," +
                                "\"browser\":\"Firefox\"," +
                                "\"browserVersion\":\"70.0.1\"}"));
    }
}