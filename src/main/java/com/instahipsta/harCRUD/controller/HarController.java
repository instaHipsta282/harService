package com.instahipsta.harCRUD.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.instahipsta.harCRUD.model.entity.Har;
import com.instahipsta.harCRUD.service.HarService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/har")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class HarController {

    private final ObjectMapper objectMapper;
    private final HarService harService;

    @PostMapping("upload")
    public String uploadHar(@RequestParam MultipartFile file) {
        System.out.println("start");
        Har har;
        Har savedHar = null;
        byte[] content = null;
        String harDtoString = null;

        try {
            content = file.getBytes();
            System.out.println("get bites");
        } catch (IOException e) {
            System.out.println("error get bites");
            log.error("Error get bytes from file {} {}", file.getOriginalFilename(), e.getMessage());
        }

        try {
            har = harService.createHarFromFile(content);
            System.out.println("create har");
            savedHar = harService.save(har);
            System.out.println(savedHar == null);
            System.out.println("save har");
        } catch (IOException e) {
            log.error("Error parsing json from file {} {}", file.getOriginalFilename(), e.getMessage());
            System.out.println("error parsing");
        }

        if (savedHar != null) {
            harService.sendHarInQueue(savedHar.getContent());
            System.out.println("sending");
        }

        try {
            harDtoString = objectMapper.writeValueAsString(harService.harToDto(savedHar));
            System.out.println("hardto");
        } catch (JsonProcessingException e) {
            log.error("Error parsing har {} {}", savedHar, e.getMessage());
            System.out.println("har dto error");
        }
        System.out.println("end");
        return harDtoString;
    }
}
