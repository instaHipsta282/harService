package com.instahipsta.harCRUD.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.instahipsta.harCRUD.model.dto.Har.Entry;
import com.instahipsta.harCRUD.model.dto.Har.HARDto;
import com.instahipsta.harCRUD.model.entity.Request;
import com.instahipsta.harCRUD.repository.RequestRepo;
import com.instahipsta.harCRUD.service.RequestService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class RequestServiceImpl implements RequestService {

    private RequestRepo requestRepo;
    private ModelMapper modelMapper;

    public RequestServiceImpl(RequestRepo requestRepo,
                              ModelMapper modelMapper) {

        this.requestRepo = requestRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Request> harDtoToRequestList(HARDto harDto) {
        List<Request> requests = new ArrayList<>();
        for (Entry entry : harDto.getLog().getEntries()) {
            Request request = modelMapper.map(entry.getRequest(), Request.class);
            Request saveRequest = requestRepo.save(request);
            requests.add(saveRequest);
        }
        return requests;
    }
}
