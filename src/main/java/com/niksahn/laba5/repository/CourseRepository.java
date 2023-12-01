package com.niksahn.laba5.repository;

import com.niksahn.laba5.model.dto.CourseDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends CrudRepository<CourseDto,Long> {

}
