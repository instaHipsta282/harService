package com.instahipsta.harCRUD.mapper;

import com.instahipsta.harCRUD.model.dto.HarDTO;
import com.instahipsta.harCRUD.model.entity.Har;
import org.springframework.stereotype.Component;

@Component
public class HarMapper extends AbstractMapper<Har, HarDTO> {

    public HarMapper() {
        super(Har.class, HarDTO.class);
    }

}
