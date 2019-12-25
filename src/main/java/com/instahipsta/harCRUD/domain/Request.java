package com.instahipsta.harCRUD.domain;

import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpMethod;

import javax.persistence.*;
import java.util.Map;

@Entity
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "request_id_seq")
    @SequenceGenerator(name = "request_id_seq", sequenceName = "request_id_seq", allocationSize = 1)
    private Long id;

    @Length(max = 65000)
    private String url;
    @Lob
    private String body;

    @ElementCollection
    @MapKeyColumn(name = "headers_key", length = 25000)
    @Column(name = "headers_val", length = 25000)
    private Map<String, String> headers;
    @ElementCollection
    @MapKeyColumn(name = "params_key", length = 25000)
    @Column(name = "params_val", length = 25000)
    private Map<String, String> params;
    private HttpMethod method;
    private Double perc = 0.0;

    @ManyToOne
    private TestProfile testProfile;

    public Request(String url, String body, Map<String, String> headers, Map<String, String> params,
                   HttpMethod method, TestProfile testProfile) {
        this.url = url;
        this.body = body;
        this.headers = headers;
        this.params = params;
        this.method = method;
        this.testProfile = testProfile;
    }

    public Request() { }

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

    public TestProfile getTestProfile() { return testProfile; }

    public void setTestProfile(TestProfile testProfile) { this.testProfile = testProfile; }
}
