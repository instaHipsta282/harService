package com.instahipsta.harCRUD.model.dto.HAR;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class Log {
    @NotEmpty(message = "version empty")
    private String version;
    @NotNull
    private Creator creator;
    @NotNull
    private Browser browser;
    private Page[] pages;
    @NotNull
    private Entry[] entries;
}
