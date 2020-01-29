package com.instahipsta.harCRUD.repository;

import com.instahipsta.harCRUD.model.entity.TestProfile;
import org.springframework.data.repository.CrudRepository;

public interface TestProfileRepo extends CrudRepository<TestProfile, Long> {

    TestProfile findTestProfileById(Long id);
}
