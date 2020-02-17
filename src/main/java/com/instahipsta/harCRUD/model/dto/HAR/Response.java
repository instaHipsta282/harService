package com.instahipsta.harCRUD.model.dto.HAR;

import com.instahipsta.harCRUD.model.dto.HAR.Request.Content;
import com.instahipsta.harCRUD.model.dto.HAR.Request.Cookie;
import com.instahipsta.harCRUD.model.dto.HAR.Request.Header;
import lombok.Data;

import java.util.List;

@Data
public class Response {
    private String status;
    private String statusText;
    private String httpVersion;
    private List<Header> headers;
    private List<Cookie> cookies;
    private Content content;
    private String redirectURL;
    private long headersSize;
    private long bodySize;
}
