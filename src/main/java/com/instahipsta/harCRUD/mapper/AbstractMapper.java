package com.instahipsta.harCRUD.mapper;

import com.instahipsta.harCRUD.model.dto.Transferable;
import com.instahipsta.harCRUD.model.entity.Entityable;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

public abstract class AbstractMapper<E extends Entityable, D extends Transferable> implements Mapper<E, D>{

    @Autowired
    private ModelMapper mapper;
    private Class<E> entityClass;
    private Class<D> dtoClass;

    public AbstractMapper(Class<E> entityClass,
                          Class<D> dtoClass) {

        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
    }

    @Override
    public E toEntity(D dto) {
        return Objects.isNull(dto) ? null : mapper.map(dto, entityClass);
    }

    @Override
    public D toDto(E entity) {
        return Objects.isNull(entity) ? null : mapper.map(entity, dtoClass);
    }

    Converter<E, D> toDtoConverter() {
        return context -> {
            E source = context.getSource();
            D destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    Converter<D, E> toEntityConverter() {
        return context -> {
            D source = context.getSource();
            E destination = context.getDestination();
            mapSpecificFields(source, destination);
            return destination;
        };
    }

    void mapSpecificFields(D source, E destination) { }

    void mapSpecificFields(E source, D destination) { }
}
