package com.instahipsta.harCRUD.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.instahipsta.harCRUD.model.dto.HarDto;
import com.instahipsta.harCRUD.model.entity.Har;
import com.instahipsta.harCRUD.repository.HarRepo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.AdditionalAnswers;
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

    static Har getHar() {
        return new Har(1, "1.0.1", "Explorer", "1.0.0", null);
    }

    static Stream<Arguments> updateSource() throws JsonProcessingException {
        HarDto harDTO = new HarDto(5L, "1.2", "Firefox", "70.0.1");
        return Stream.of(
                Arguments.of(new ObjectMapper().writeValueAsString(harDTO), getHar(), 1L));
    }

    @ParameterizedTest
    @MethodSource("updateSource")
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
    @ValueSource(longs = 1)
    void getTest(long id) throws Exception {
        when(harRepo.findById(id)).thenReturn(Optional.of(getHar()));

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
    @ValueSource(longs = 1)
    void deleteTest(long id) throws Exception {
        when(harRepo.findById(id)).thenReturn(Optional.of(getHar()));

        mockMvc.perform(delete("/har/" + id))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    static Stream<Arguments> addSource() throws IOException {
        String name = "file";
        String contentType = "application/json";
        String originalFileName = "test_archive.har";
        Path path = Paths.get("filesForTests/test_archive.har");
        byte[] content = Files.readAllBytes(path);

        MultipartFile multipartFile = new MockMultipartFile(name, originalFileName, contentType, content);

        return Stream.of(Arguments.of(multipartFile));
    }

    @ParameterizedTest
    @MethodSource("addSource")
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
