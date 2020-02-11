package com.instahipsta.harCRUD.model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import org.springframework.http.HttpMethod;

@Data
public class RequestDto {
    private long bodySize;
    private HttpMethod method;
    private String url;
    private String httpVersion;
    private JsonNode headers;
    private JsonNode cookies;

    @JsonAlias("content")
    private JsonNode body;

    @JsonAlias("queryString")
    private JsonNode params;

    private long headersSize;
}
