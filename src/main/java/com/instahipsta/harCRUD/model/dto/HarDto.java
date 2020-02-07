package com.instahipsta.harCRUD.model.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HarDto {

    private Long id;
    private String version;
    private String browser;
    private String browserVersion;
    private JsonNode content;

}
