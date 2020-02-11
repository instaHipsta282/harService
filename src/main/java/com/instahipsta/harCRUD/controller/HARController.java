package com.instahipsta.harCRUD.controller;

import com.instahipsta.harCRUD.exception.ResourceNotFoundException;
import com.instahipsta.harCRUD.exception.dto.CustomException;
import com.instahipsta.harCRUD.model.dto.HARDto;
import com.instahipsta.harCRUD.service.HarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;


@RestController
@RequestMapping("/har")
@Slf4j
public class HARController {

    private HarService harService;

    public HARController(HarService harService) {
        this.harService = harService;
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateHar(@RequestBody HARDto dto,
                                       @PathVariable long id) {
        try {
            return harService.update(dto, id);
        }
        catch (ResourceNotFoundException ex) {
            log.warn("Har with id {} not found", id);
            return ResponseEntity
                    .status(NOT_FOUND)
                    .body(new CustomException("There is no such har with id " + id));
        }
        catch (Exception e) {
            log.error("Exception in updateHar method, har id is {}, error: {}", id, e);
            return ResponseEntity
                    .status(INTERNAL_SERVER_ERROR)
                    .body(new CustomException("Something went wrong"));
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getHar(@PathVariable long id) {
        try {
            return harService.find(id);
        }
        catch (ResourceNotFoundException ex) {
            log.warn("Har with id {} not found", id);
            return ResponseEntity
                    .status(NOT_FOUND)
                    .body(new CustomException("There is no such har with id " + id));
        }
        catch (Exception e) {
            log.error("Exception in getHar method, har id is {} error: {}", id, e);
            return ResponseEntity
                    .status(INTERNAL_SERVER_ERROR)
                    .body(new CustomException("Something went wrong"));
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteHar(@PathVariable long id) {
        try {
            harService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        catch (Exception e) {
            log.error("Exception in deleteHar method, har id is {}, error: {}", id, e);
            return ResponseEntity
                    .status(INTERNAL_SERVER_ERROR)
                    .body(new CustomException("Something went wrong"));
        }
    }

    @PostMapping
    public ResponseEntity<?> addHar(@RequestParam MultipartFile file) {
        try {
            return harService.add(file);
        }
        catch (Exception e) {
            log.error("Exception in addHar method, file {} error: {}", file.getOriginalFilename(), e);
            return ResponseEntity
                    .status(INTERNAL_SERVER_ERROR)
                    .body(new CustomException("Something went wrong"));
        }
    }
}
