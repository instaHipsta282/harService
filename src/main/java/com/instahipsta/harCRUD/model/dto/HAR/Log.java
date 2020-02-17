package com.instahipsta.harCRUD.model.dto.HAR;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

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
    private List<Page> pages;
    @NotNull
    @Valid
    private List<Entry> entries;
}
