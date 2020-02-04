package com.instahipsta.harCRUD.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HarDTO {

    private Long id;
    private String version;
    private String browser;
    private String browserVersion;

}
