package com.instahipsta.harCRUD.model.dto.HAR.Request;

import lombok.Data;

@Data
public class Content {
    private String mimeType;
    private long size;
    private String text;
}
