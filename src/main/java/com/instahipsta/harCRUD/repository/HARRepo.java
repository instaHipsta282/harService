package com.instahipsta.harCRUD.repository;

import com.instahipsta.harCRUD.model.entity.HAR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface HARRepo extends JpaRepository<HAR, Long> {

}
