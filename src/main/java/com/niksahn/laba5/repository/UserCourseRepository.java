package com.niksahn.laba5.repository;

import com.niksahn.laba5.model.dto.CourseDto;
import com.niksahn.laba5.model.dto.UserCoursesDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface UserCourseRepository extends CrudRepository<UserCoursesDto, Long> {
    @Query(value = "select * from user_courses where  user_id = :user_id", nativeQuery = true)
    ArrayList<UserCoursesDto> getAllCoursesByUserId(Long user_id);

    @Query(value = "select * from user_courses where  user_id = :user_id and course_id = :course_id", nativeQuery = true)
    UserCoursesDto getCourseByUserIdCourseId(Long user_id, Long course_id);
}
