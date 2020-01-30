package com.instahipsta.harCRUD.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpMethod;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestDTO implements Transferable {

    private Long id;
    private String url;
    private String body;
    private Map<String, String> headers;
    private Map<String, String> params;
    private HttpMethod method;
    private Double perc;
    private long testProfileId;

}
