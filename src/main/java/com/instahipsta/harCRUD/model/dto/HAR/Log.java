package com.instahipsta.harCRUD.model.dto.HAR;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class Log {
    @NotNull
    private String version;
    @NotNull
    @Valid
    private Creator creator;
    @NotNull
    @Valid
    private Browser browser;
    private Page[] pages;
    @NotNull
    @Valid
    private Entry[] entries;
}
