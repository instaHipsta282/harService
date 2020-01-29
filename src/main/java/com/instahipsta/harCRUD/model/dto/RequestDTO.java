package com.instahipsta.harCRUD.model.dto;

import org.springframework.http.HttpMethod;

import java.util.Map;

public class RequestDTO implements Transferable {

    private Long id;
    private String url;
    private String body;
    private Map<String, String> headers;
    private Map<String, String> params;
    private HttpMethod method;
    private Double perc;
    private long testProfileId;

    public RequestDTO(Long id, String url, String body,
                      Map<String, String> headers, Map<String, String> params,
                      HttpMethod method, Double perc, long testProfileId) {
        this.id = id;
        this.url = url;
        this.body = body;
        this.headers = headers;
        this.params = params;
        this.method = method;
        this.perc = perc;
        this.testProfileId = testProfileId;
    }

    public RequestDTO() {}

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }

    public String getBody() { return body; }

    public void setBody(String body) { this.body = body; }

    public Map<String, String> getHeaders() { return headers; }

    public void setHeaders(Map<String, String> headers) { this.headers = headers; }

    public Map<String, String> getParams() { return params; }

    public void setParams(Map<String, String> params) { this.params = params; }

    public HttpMethod getMethod() { return method; }

    public void setMethod(HttpMethod method) { this.method = method; }

    public Double getPerc() { return perc; }

    public void setPerc(Double perc) { this.perc = perc; }

    public long getTestProfileId() { return testProfileId; }

    public void setTestProfileId(long testProfileId) { this.testProfileId = testProfileId; }

}
