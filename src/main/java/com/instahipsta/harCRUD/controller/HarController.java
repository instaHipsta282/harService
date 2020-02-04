package com.instahipsta.harCRUD.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.instahipsta.harCRUD.model.dto.HarDTO;
import com.instahipsta.harCRUD.model.entity.Har;
import com.instahipsta.harCRUD.service.HarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/har")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class HarController {

    private final ObjectMapper objectMapper;
    private final HarService harService;

    @PutMapping("update/{id}")
    public String updateHar(@RequestBody HarDTO har,
                                            @PathVariable long id) {

        String harDtoString = null;
        Har findHar = harService.find(id);
        if (findHar == null) return null;
        Har updatedHar = harService.update(har);

        try {
            harDtoString = objectMapper.writeValueAsString(harService.harToDto(updatedHar));
        } catch (JsonProcessingException e) {
            log.error("Error parsing har {} {}", updatedHar, e.getMessage());
        }

        return harDtoString;
    }

    @GetMapping("get")
    public String getHar(@RequestParam long id) {
        Har findHar = harService.find(id);
        String harDtoString = null;

        if (findHar == null) return null;

        try {
            harDtoString = objectMapper.writeValueAsString(harService.harToDto(findHar));
        } catch (JsonProcessingException e) {
            log.error("Error parsing har {} {}", findHar, e.getMessage());
        }

        return harDtoString;
    }

    @GetMapping("delete")
    public String deleteHar(@RequestParam long id) {
        Har deletedHar = harService.find(id);
        String harDtoString = null;

        if (deletedHar == null) return null;

        harService.delete(id);
        try {
            harDtoString = objectMapper.writeValueAsString(harService.harToDto(deletedHar));
        } catch (JsonProcessingException e) {
            log.error("Error parsing har {} {}", deletedHar, e.getMessage());
        }

        return harDtoString;
    }

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
