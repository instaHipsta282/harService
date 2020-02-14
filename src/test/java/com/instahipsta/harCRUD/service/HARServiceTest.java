package com.instahipsta.harCRUD.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.instahipsta.harCRUD.config.property.RabbitmqProperties;
import com.instahipsta.harCRUD.model.dto.HAR.HARDto;
import com.instahipsta.harCRUD.model.entity.HAR;
import com.instahipsta.harCRUD.model.exception.JsonValidateFailedException;
import com.instahipsta.harCRUD.model.exception.ResourceNotFoundException;
import com.instahipsta.harCRUD.repository.HARRepo;
import com.instahipsta.harCRUD.service.impl.HarServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HARServiceTest {

    @InjectMocks
    private HarServiceImpl harService;

    @Spy
    private ObjectMapper objectMapper;

    @Spy
    private RabbitmqProperties rabbitmqProperties;

    @Mock
    private HARRepo harRepo;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @BeforeEach
    void setConfig() {
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    @ParameterizedTest
    @MethodSource("com.instahipsta.harCRUD.arg.HARArgs#harDtoSource")
    void dtoToEntityTest(HARDto dto) {
        HAR entity = harService.dtoToEntity(dto);

        Assertions.assertEquals(entity.getVersion(), dto.getLog().getVersion());
        Assertions.assertEquals(entity.getBrowser(), dto.getLog().getBrowser().getName());
        Assertions.assertEquals(entity.getContent(), objectMapper.valueToTree(dto));
    }

    @ParameterizedTest
    @MethodSource("com.instahipsta.harCRUD.arg.HARArgs#harSource")
    void entityToDtoTest(HAR entity) throws JsonProcessingException {
        HARDto dto = harService.entityToDto(entity);

        Assertions.assertEquals(dto, objectMapper.treeToValue(entity.getContent(), HARDto.class));
    }

    @ParameterizedTest
    @MethodSource("com.instahipsta.harCRUD.arg.HARArgs#harSource")
    void entityToDtoNegativeTest(HAR entity) throws JsonProcessingException {
        entity.setContent(objectMapper.readTree("{ \"kek\":\"\"}"));

        Assertions.assertThrows(JsonMappingException.class, () ->
                harService.entityToDto(entity));
    }

    @ParameterizedTest
    @MethodSource("com.instahipsta.harCRUD.arg.HARArgs#harDtoHarAndIdSource")
    void updateTest(HARDto dto, HAR har, long id) throws JsonProcessingException {
        when(harRepo.findById(id)).thenReturn(Optional.of(har));
        when(harRepo.save(any(HAR.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());

        ResponseEntity<HARDto> responseEntity = harService.update(dto, id);
        HARDto response = responseEntity.getBody();

        Assertions.assertEquals(har.getVersion(), response.getLog().getVersion());
        Assertions.assertEquals(har.getBrowser(), response.getLog().getBrowser().getName());
        Assertions.assertEquals(har.getContent(), objectMapper.valueToTree(response));
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }


    @ParameterizedTest
    @MethodSource("com.instahipsta.harCRUD.arg.HARArgs#harDtoAndIdSource")
    void updateNegativeTest(HARDto dto, long id) {
        when(harRepo.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () ->
                harService.update(dto, id));
    }

    @ParameterizedTest
    @MethodSource("com.instahipsta.harCRUD.arg.HARArgs#harAndIdSource")
    void findTest(HAR har, long id) throws JsonProcessingException {
        when(harRepo.findById(id)).thenReturn(Optional.of(har));

        ResponseEntity<HARDto> responseEntity = harService.find(id);
        HARDto findDto = responseEntity.getBody();

        Assertions.assertEquals(har.getVersion(), findDto.getLog().getVersion());
        Assertions.assertEquals(har.getBrowser(), findDto.getLog().getBrowser().getName());
        Assertions.assertEquals(har.getContent(), objectMapper.valueToTree(findDto));
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @ParameterizedTest
    @ValueSource(longs = 98L)
    void findNegativeTest(long id) {
        when(harRepo.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> harService.find(id));
    }

    @ParameterizedTest
    @ValueSource(longs = 6)
    void deleteTest(long id) {
        Assertions.assertDoesNotThrow(() -> harService.delete(id));
    }

    @ParameterizedTest
    @MethodSource("com.instahipsta.harCRUD.arg.HARArgs#fileSource")
    void addTest(MultipartFile file) throws IOException {
        when(harRepo.save(any(HAR.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());

        ResponseEntity<HARDto> entity = harService.add(file);
        HARDto dto = entity.getBody();

        Assertions.assertEquals("1.2", dto.getLog().getVersion());
        Assertions.assertEquals("Firefox", dto.getLog().getBrowser().getName());
        Assertions.assertEquals(HttpStatus.OK, entity.getStatusCode());
    }

    @ParameterizedTest
    @MethodSource("com.instahipsta.harCRUD.arg.HARArgs#fileSource")
    void createDtoFromFileTest(MultipartFile file) throws IOException {
        HARDto dto = harService.createDtoFromFile(file);

        Assertions.assertEquals("1.2", dto.getLog().getVersion());
        Assertions.assertEquals("Firefox", dto.getLog().getBrowser().getName());
    }

    @ParameterizedTest
    @MethodSource("com.instahipsta.harCRUD.arg.HARArgs#notValidFileSource")
    void createDtoFromFileNegativeTest(MultipartFile file) {
        Assertions.assertThrows(JsonValidateFailedException.class, () ->
                harService.createDtoFromFile(file));
    }

    @ParameterizedTest
    @MethodSource("com.instahipsta.harCRUD.arg.HARArgs#jsonMappingExFileSource")
    void createDtoFromFileJsonMappingExTest(MultipartFile file) {
        Assertions.assertThrows(JsonMappingException.class, () ->
                harService.createDtoFromFile(file));
    }

    @ParameterizedTest
    @MethodSource("com.instahipsta.harCRUD.arg.HARArgs#jsonProcessingExFileSource")
    void createDtoFromFileJsonProcessingExTest(MultipartFile file) {
        Assertions.assertThrows(JsonProcessingException.class, () ->
                harService.createDtoFromFile(file));
    }

    @ParameterizedTest
    @MethodSource("com.instahipsta.harCRUD.arg.HARArgs#harDtoSource")
    void saveTest(HARDto dto) throws JsonProcessingException {
        when(harRepo.save(any(HAR.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());
        HARDto saveDto = harService.save(dto);
        Assertions.assertEquals(dto.getLog().getVersion(), saveDto.getLog().getVersion());
        Assertions.assertEquals(dto.getLog().getBrowser().getName(), saveDto.getLog().getBrowser().getName());
        Assertions.assertEquals(dto, saveDto);
    }
}
