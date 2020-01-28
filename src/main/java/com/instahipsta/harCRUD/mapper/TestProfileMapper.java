package com.instahipsta.harCRUD.mapper;

import com.instahipsta.harCRUD.dto.TestProfileDTO;
import com.instahipsta.harCRUD.entity.TestProfile;
import org.springframework.stereotype.Component;

@Component
public class TestProfileMapper extends AbstractMapper<TestProfile, TestProfileDTO> {

    public TestProfileMapper() {
        super(TestProfile.class, TestProfileDTO.class);
    }
}
