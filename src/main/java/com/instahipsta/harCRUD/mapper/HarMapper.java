package com.instahipsta.harCRUD.mapper;

import com.instahipsta.harCRUD.dto.HarDTO;
import com.instahipsta.harCRUD.entity.Har;
import org.springframework.stereotype.Component;

@Component
public class HarMapper extends AbstractMapper<Har, HarDTO> {

    public HarMapper() {
        super(Har.class, HarDTO.class);
    }

}
