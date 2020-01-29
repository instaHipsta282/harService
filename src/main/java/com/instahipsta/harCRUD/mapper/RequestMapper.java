package com.instahipsta.harCRUD.mapper;

import com.instahipsta.harCRUD.model.entity.Request;
import com.instahipsta.harCRUD.model.dto.RequestDTO;
import com.instahipsta.harCRUD.repository.TestProfileRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Component
public class RequestMapper extends AbstractMapper<Request, RequestDTO> {

    private final ModelMapper mapper;
    private final TestProfileRepo testProfileRepo;

    @Autowired
    public RequestMapper(ModelMapper mapper, TestProfileRepo testProfileRepo) {
        super(Request.class, RequestDTO.class);
        this.mapper = mapper;
        this.testProfileRepo = testProfileRepo;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Request.class, RequestDTO.class)
                .addMappings(m -> m.skip(RequestDTO::setTestProfileId)).setPostConverter(toDtoConverter());
        mapper.createTypeMap(RequestDTO.class, Request.class)
                .addMappings(m -> m.skip(Request::setTestProfile)).setPostConverter(toEntityConverter());
    }

    @Override
    void mapSpecificFields(RequestDTO source, Request destination) {
        destination.setTestProfile(testProfileRepo.findById(source.getTestProfileId()).orElse(null));
    }

    @Override
    void mapSpecificFields(Request source, RequestDTO destination) {
        destination.setTestProfileId(getId(source));
    }

    private long getId(Request source) {
        return Objects.isNull(source) || Objects.isNull(source.getId()) ? null : source.getTestProfile().getId();
    }
}
