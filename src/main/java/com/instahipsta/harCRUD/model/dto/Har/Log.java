package com.instahipsta.harCRUD.model.dto.Har;

import lombok.Data;

@Data
public class Log {
    private String version;
    private Creator creator;
    private Browser browser;
    private Page[] pages;
    private Entry[] entries;
}
