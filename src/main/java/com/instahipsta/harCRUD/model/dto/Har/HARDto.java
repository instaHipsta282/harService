package com.instahipsta.harCRUD.model.dto.Har;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HARDto implements Serializable {
    private Log log;
}
