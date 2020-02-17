package com.instahipsta.harCRUD.model.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CustomException {
    private String message;
}