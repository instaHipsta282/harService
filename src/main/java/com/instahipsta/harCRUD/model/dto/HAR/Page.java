package com.instahipsta.harCRUD.model.dto.HAR;

import lombok.Data;

@Data
public class Page {
    private String startedDateTime;
    private String id;
    private PageTimings pageTimings;
}
