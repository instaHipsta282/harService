package com.instahipsta.harCRUD.mapper;

import com.instahipsta.harCRUD.model.dto.Transferable;
import com.instahipsta.harCRUD.model.entity.Entityable;

public interface Mapper<E extends Entityable, D extends Transferable> {

    E toEntity(D dto);

    D toDto(E entity);
}
