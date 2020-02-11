package com.instahipsta.harCRUD.model.dto;

import lombok.Data;

@Data
public class Content {
    private String mimeType;
    private long size;
    private String text;
}
