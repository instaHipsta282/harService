package com.instahipsta.harCRUD.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.instahipsta.harCRUD.model.entity.Har;
import com.instahipsta.harCRUD.service.HarService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/har")
@RequiredArgsConstructor
@Slf4j
public class HarController {

    @NonNull
    private ObjectMapper objectMapper;
    @NonNull
    private HarService harService;

    @PostMapping("upload")
    public String uploadHar(@RequestParam MultipartFile file) {
        Har har;
        Har savedHar = null;
        byte[] content = null;
        String harDtoString = null;

        try {
            content = file.getBytes();
        } catch (IOException e) {
            log.error("Error get bytes from file {} {}", file.getOriginalFilename(), e.getMessage());
        }

        try {
            har = harService.createHarFromFile(content);
            savedHar = harService.save(har);
        } catch (IOException e) {
            log.error("Error parsing json from file {} {}", file.getOriginalFilename(), e.getMessage());
        }

        if (savedHar != null) {
            harService.sendHarInQueue(savedHar.getContent());
        }

        try {
            harDtoString = objectMapper.writeValueAsString(harService.harToDto(savedHar));
        } catch (JsonProcessingException e) {
            log.error("Error parsing har {} {}", savedHar, e.getMessage());
        }
        return harDtoString;
    }
}
