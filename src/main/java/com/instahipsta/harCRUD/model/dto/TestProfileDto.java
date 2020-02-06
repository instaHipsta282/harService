package com.instahipsta.harCRUD.model.dto;

import com.instahipsta.harCRUD.model.entity.Request;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TestProfileDto {

    private Long id;
    private List<Request> requests;
    private int requestsCount;

}
