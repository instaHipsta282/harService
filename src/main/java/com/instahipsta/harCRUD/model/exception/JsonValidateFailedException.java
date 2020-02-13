package com.instahipsta.harCRUD.model.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.ValidationException;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JsonValidateFailedException extends ValidationException {
    private List<String> failList;
}
