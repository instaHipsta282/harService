package com.instahipsta.harCRUD.service;

import com.instahipsta.harCRUD.model.dto.HAR.HARDto;
import com.instahipsta.harCRUD.model.entity.Request;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RequestService {
    List<Request> harDtoToRequestList(HARDto dto);
}
