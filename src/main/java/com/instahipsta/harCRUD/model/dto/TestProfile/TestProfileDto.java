package com.instahipsta.harCRUD.model.dto.TestProfile;

import com.instahipsta.harCRUD.model.entity.Request;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestProfileDto {
    private Long id;
    private List<Request> requests;
    private int requestsCount;
}
