package com.instahipsta.harCRUD.mapper;

import com.instahipsta.harCRUD.dto.Transferable;
import com.instahipsta.harCRUD.entity.Entityable;

public interface Mapper<E extends Entityable, D extends Transferable> {

    E toEntity(D dto);

    D toDto(E entity);
}
