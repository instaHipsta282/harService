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

    @PutMapping("update/{id}")
    public ResponseEntity<HarDTO> updateHar(@RequestBody HarDTO har,
                                            @PathVariable long id) {

        Optional<Har> findHar = harService.find(id);
        if (!findHar.isPresent()) {
            log.warn("Har with id {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Har savedHar = harService.update(findHar.get(), har);
        HarDTO response = harService.harToDto(savedHar);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<HarDTO> getHar(@PathVariable long id) {

        Optional<Har> findHar = harService.find(id);
        if (!findHar.isPresent()) {
            log.info("Har with id {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        HarDTO response = harService.harToDto(findHar.get());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<HarDTO> deleteHar(@PathVariable long id) {

        Optional<Har> deletedHar = harService.find(id);
        if (!deletedHar.isPresent()) {
            log.info("Har wit id {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        harService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<HarDTO> uploadHar(@RequestParam MultipartFile file,
                                            UriComponentsBuilder uriBuilder) {

        Har har = harService.createHarFromFile(file);
        Har savedHar = harService.save(har);
        if (savedHar == null) {
            log.warn("Can't save har from file {}", file.getOriginalFilename());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        harService.sendHarInQueue(savedHar.getContent());
        HarDTO response = harService.harToDto(savedHar);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriBuilder.path("/har/{id}").buildAndExpand(response.getId()).toUri());

        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
    }
}
