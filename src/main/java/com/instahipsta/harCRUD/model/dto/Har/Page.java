package com.instahipsta.harCRUD.model.dto.Har;

import com.instahipsta.harCRUD.model.dto.PageTimings;
import lombok.Data;

@Data
public class Page {
    private String startedDateTime;
    private String id;
    private PageTimings pageTimings;
}
