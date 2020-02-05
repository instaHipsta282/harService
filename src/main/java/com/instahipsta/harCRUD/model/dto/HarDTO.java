package com.instahipsta.harCRUD.model.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HarDTO {

    private Long id;
    private String version;
    private String browser;
    private String browserVersion;

}
