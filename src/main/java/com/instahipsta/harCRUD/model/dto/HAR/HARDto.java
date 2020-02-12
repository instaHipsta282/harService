package com.instahipsta.harCRUD.model.dto.HAR;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HARDto implements Serializable {

    @NotNull
    @Valid
    private Log log;

}
