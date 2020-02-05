package com.instahipsta.harCRUD.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.instahipsta.harCRUD.model.dto.HarDTO;
import com.instahipsta.harCRUD.model.entity.Har;
import com.instahipsta.harCRUD.service.HarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Optional;


@RestController
@RequestMapping("/har")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class HarController {

    private final HarService harService;

    @PutMapping("{id}")
    public ResponseEntity<HarDTO> updateHar(@RequestBody HarDTO har,
                                            @PathVariable long id) {
        return harService.update(har, id);
    }

    @GetMapping("{id}")
    public ResponseEntity<HarDTO> getHar(@PathVariable long id) {
        return harService.find(id);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<HarDTO> deleteHar(@PathVariable long id) {

        harService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<HarDTO> uploadHar(@RequestParam MultipartFile file) {


        return harService.add(file);
    }
}
