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
    private Header[] headers;
    private Cookie[] cookies;

    @JsonAlias({"content", "body"})
    private Content content;

    @JsonAlias({"queryString", "params"})
    private QueryString queryString;

    private long headersSize;
}
