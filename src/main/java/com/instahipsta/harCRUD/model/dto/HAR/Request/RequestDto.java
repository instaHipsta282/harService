package com.instahipsta.harCRUD.model.dto.HAR.Request;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto {
    private long bodySize;
    @NotNull
    private HttpMethod method;
    @NotNull
    private String url;
    private String httpVersion;
    @NotNull
    private Header[] headers;
    private Cookie[] cookies;

    @JsonAlias({"content", "body"})
    private Content content;

    @JsonAlias({"queryString", "params"})
    private QueryString[] queryString;

    private long headersSize;
}
