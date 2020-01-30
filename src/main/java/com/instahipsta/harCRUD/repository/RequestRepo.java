package com.instahipsta.harCRUD.repository;

import com.instahipsta.harCRUD.model.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepo extends JpaRepository<Request, Long> {

}
