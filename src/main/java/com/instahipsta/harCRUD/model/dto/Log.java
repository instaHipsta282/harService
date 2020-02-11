package com.instahipsta.harCRUD.model.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
public class Log {
    private String version;
    private JsonNode creator;
    private Browser browser;
    private JsonNode pages;
    private JsonNode entries;
}
