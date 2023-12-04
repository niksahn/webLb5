package com.niksahn.laba5.service;

import com.niksahn.laba5.model.OperationRezult;
import com.niksahn.laba5.model.Role;
import com.niksahn.laba5.model.dto.CourseDto;
import com.niksahn.laba5.model.dto.UserCoursesDto;
import com.niksahn.laba5.repository.CourseRepository;
import com.niksahn.laba5.repository.UserCourseRepository;
import com.niksahn.laba5.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
public class CourseService {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final UserCourseRepository userCourseRepository;

    public CourseService(UserRepository userRepository, CourseRepository courseRepository, UserCourseRepository userCourseRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.userCourseRepository = userCourseRepository;
    }

    public ArrayList<CourseDto> getAllCourses() {
        var coursesDto = courseRepository.findAll();
        return (ArrayList<CourseDto>) coursesDto;
    }

    public ArrayList<CourseDto> getUserCourses(Long user_id) {
        var userCoursesDto = userCourseRepository.getAllCoursesByUserId(user_id);
        ArrayList<CourseDto> coursesDto = new ArrayList<>();
        userCoursesDto.forEach(userCourse -> coursesDto.add(courseRepository.findById(userCourse.getCourse_id()).get()));
        return coursesDto;
    }

    public OperationRezult addCourseToUser(Long cur_user_id, Long user_id, Long course_id) {
        var user = userRepository.findByUserId(user_id);
        if (!Objects.equals(cur_user_id, user_id) && user.getRole() != Role.admin) return OperationRezult.No_Right;
        if (userCourseRepository.getCourseByUserIdCourseId(user_id, course_id) == null)
            userCourseRepository.save(new UserCoursesDto(user_id, course_id));
        else return OperationRezult.In_DataBase;
        return OperationRezult.Success;
    }

    public OperationRezult delCourseToUser(Long cur_user_id, Long user_id, Long course_id) {
        var user = userRepository.findByUserId(user_id);
        if (!Objects.equals(cur_user_id, user_id) && user.getRole() != Role.admin) return OperationRezult.No_Right;
        var course = userCourseRepository.getCourseByUserIdCourseId(user_id, course_id);
        if (course == null) return OperationRezult.Internal_Error;
        userCourseRepository.deleteById(course.getId());
        return OperationRezult.Success;
    }

    public OperationRezult addCourse(String name, String description, String image_path, Long user_id) {
        var user = userRepository.findByUserId(user_id);
        if (user.getRole() == Role.admin || user.getRole() == Role.moderator) {
            courseRepository.save(new CourseDto(name, description, image_path));
            return OperationRezult.Success;
        } else return OperationRezult.No_Right;
    }
}
