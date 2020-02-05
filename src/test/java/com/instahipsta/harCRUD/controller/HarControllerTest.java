package com.instahipsta.harCRUD.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.instahipsta.harCRUD.model.dto.HarDTO;
import com.instahipsta.harCRUD.model.entity.Har;
import com.instahipsta.harCRUD.repository.HarRepo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
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

    static Stream<Arguments> updateTestSource() throws JsonProcessingException {
        HarDTO harDTO = new HarDTO(5L, "1.2", "Firefox", "70.0.1");
        Har har = new Har(1, "1.0.1", "Explorer", "1.0.0.", null);
        return Stream.of(
                Arguments.of(new ObjectMapper().writeValueAsString(harDTO), har));
    }

    @ParameterizedTest
    @MethodSource("updateTestSource")
    void updateTest(String harDto, Har har) throws Exception {

        doReturn(Optional.of(har)).when(harRepo).findById(1L);
        doReturn(har).when(harRepo).save(har);

        mockMvc.perform(put("/har/1")
                .content(harDto)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content()
                        .json("{\"id\":1,\"version\":\"1.2\",\"browser\":\"Firefox\",\"browserVersion\":\"70.0.1\"}"));
    }


    static Stream<Arguments> getTestSource() {
        Har har = new Har(1, "1.0.1", "Explorer", "1.0.0", null);
        return Stream.of(Arguments.of(har, 1L));
    }

    @ParameterizedTest
    @MethodSource("getTestSource")
    void getTest(Har har, long id) throws Exception {

        doReturn(Optional.of(har)).when(harRepo).findById(id);

        mockMvc.perform(get("/har/1"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content()
                        .json("{\"id\":1,\"version\":\"1.0.1\",\"browser\":\"Explorer\",\"browserVersion\":\"1.0.0\"}"));
    }

    @ParameterizedTest
    @MethodSource("getTestSource")
    void deleteTest(Har har, long id) throws Exception {

        doReturn(Optional.of(har)).when(harRepo).findById(id);

        mockMvc.perform(delete("/har/1"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    static Stream<Arguments> addTestSource() throws IOException {
        String name = "file";
        String contentType = "application/json";
        String originalFileName = "test_archive.har";
        Path path = Paths.get("filesForTests/test_archive.har");
        byte[] content = Files.readAllBytes(path);

        MultipartFile multipartFile = new MockMultipartFile(name, originalFileName, contentType, content);

        Har har = new Har(1, "1.0.1", "Explorer", "1.0.0", null);

        return Stream.of(Arguments.of(har, multipartFile));
    }

    @ParameterizedTest
    @MethodSource("addTestSource")
    void addTest(Har har, MockMultipartFile file) throws Exception {

        doReturn(har).when(harRepo).save(any(Har.class));

        mockMvc.perform(multipart("/har")
                .file(file)
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content()
                        .json("{\"id\":1,\"version\":\"1.0.1\",\"browser\":\"Explorer\",\"browserVersion\":\"1.0.0\"}"));
    }
}
