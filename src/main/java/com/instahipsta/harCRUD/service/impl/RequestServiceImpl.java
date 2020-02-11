package com.instahipsta.harCRUD.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.instahipsta.harCRUD.model.dto.Entry;
import com.instahipsta.harCRUD.model.dto.HARDto;
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
    private ObjectMapper objectMapper;
    private ModelMapper modelMapper;

    public RequestServiceImpl(RequestRepo requestRepo,
                              ObjectMapper objectMapper,
                              ModelMapper modelMapper) {

        this.requestRepo = requestRepo;
        this.objectMapper = objectMapper;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Request> harDtoToRequestList(HARDto harDto) throws JsonProcessingException {
        List<Request> requests = new ArrayList<>();
        JsonNode entries = harDto.getLog().getEntries();
        for (JsonNode entry : entries) {
            Entry en = objectMapper.treeToValue(entry, Entry.class);
            Request request = modelMapper.map(en.getRequestDto(), Request.class);
            Request saveRequest = requestRepo.save(request);
            requests.add(saveRequest);
        }
        return requests;
    }
}
