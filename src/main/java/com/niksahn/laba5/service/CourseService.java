package com.niksahn.laba5.service;

import com.niksahn.laba5.model.OperationRezult;
import com.niksahn.laba5.model.Role;
import com.niksahn.laba5.model.dto.CourseDto;
import com.niksahn.laba5.model.dto.UserCoursesDto;
import com.niksahn.laba5.repository.CourseRepository;
import com.niksahn.laba5.repository.UserCourseRepository;
import com.niksahn.laba5.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Objects;

import static com.niksahn.laba5.Constants.course_path;

@Service
public class CourseService {
    private final UserRepository userRepository;
    private final FileService fileService;
    private final CourseRepository courseRepository;
    private final UserCourseRepository userCourseRepository;

    public CourseService(UserRepository userRepository, FileService fileService, CourseRepository courseRepository, UserCourseRepository userCourseRepository) {
        this.userRepository = userRepository;
        this.fileService = fileService;
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

    @Transactional
    public OperationRezult addCourse(String name, String description, String image, Long user_id) {
        var file_name = fileService.setImage(image, course_path + name);
        var user = userRepository.findByUserId(user_id);
        if (user.getRole() == Role.admin || user.getRole() == Role.moderator) {
            courseRepository.save(new CourseDto(name, description, file_name));
            return OperationRezult.Success;
        } else return OperationRezult.No_Right;
    }

    @Transactional
    public OperationRezult editCourse(Long id, String name, String description, String image, Long user_id) {
        var user = userRepository.findByUserId(user_id);
        var course = courseRepository.findById(id);
        if (course.isEmpty()) return OperationRezult.Not_In_DataBase;
        if (user.getRole() == Role.admin || user.getRole() == Role.moderator) {
            fileService.deleteImage(course.get().getImage_path());
            var file_name = fileService.setImage(image, course_path + name);
            courseRepository.save(new CourseDto(id, name, description, file_name));
            return OperationRezult.Success;
        } else return OperationRezult.No_Right;
    }
}
