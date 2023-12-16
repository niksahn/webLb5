package com.niksahn.laba5.controller;

import com.niksahn.laba5.model.request.AddCourseRequest;
import com.niksahn.laba5.model.response.CourseResponse;
import com.niksahn.laba5.model.OperationRezult;
import com.niksahn.laba5.service.CourseService;
import com.niksahn.laba5.service.FileService;
import com.niksahn.laba5.service.SessionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Objects;

import static com.niksahn.laba5.Constants.course_path;
import static com.niksahn.laba5.controller.Common.checkAuth;

@RestController
@CrossOrigin
@RequestMapping("/course")
public class CourseController {
    private final String defaultImg = course_path + "default";
    private final CourseService courseService;
    private final SessionService sessionService;
    private final FileService fileService;

    public CourseController(CourseService courseService, SessionService sessionService, FileService fileService) {
        this.courseService = courseService;
        this.sessionService = sessionService;
        this.fileService = fileService;
    }

    @GetMapping("/all")
    @CrossOrigin
    public ResponseEntity<?> getAllCourse() {
        ArrayList<CourseResponse> courses = new ArrayList<CourseResponse>();
        courseService.getAllCourses().forEach(course -> courses.add(course.fromCourseDto(fileService.getImage(course.getImage_path(), defaultImg))));
        return ResponseEntity.ok().body(courses);
    }

    @GetMapping("/user")
    @CrossOrigin
    public ResponseEntity<?> getUserCourse(@RequestHeader("Authorization") Long session_id, @RequestParam Long user_id) {
        var auth = checkAuth(session_id, sessionService);
        if (auth != null) return auth;
        ArrayList<CourseResponse> courses = new ArrayList<CourseResponse>();
        courseService.getUserCourses(user_id).forEach(course -> courses.add(course.fromCourseDto(fileService.getImage(course.getImage_path(), defaultImg))));
        return ResponseEntity.ok().body(courses);
    }

    @GetMapping(value = "/user/add")
    @CrossOrigin
    public ResponseEntity<?> addUserCourse(@RequestHeader("Authorization") Long session_id, @RequestParam Long course_id, @RequestParam Long user_id) {
        var auth = checkAuth(session_id, sessionService);
        Long cur_user_id = sessionService.getUserIdFromSession(session_id);
        if (auth != null) return auth;
        var responseOperation = courseService.addCourseToUser(cur_user_id, user_id, course_id);
        if (Objects.equals(responseOperation, OperationRezult.Success)) return ResponseEntity.ok().body(responseOperation.toString());
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseOperation.toString());
    }

    @GetMapping(value = "/user/delete")
    @CrossOrigin
    public ResponseEntity<?> delUserCourse(@RequestHeader("Authorization") Long session_id, @RequestParam Long course_id, @RequestParam Long user_id) {
        var auth = checkAuth(session_id, sessionService);
        Long cur_user_id = sessionService.getUserIdFromSession(session_id);
        if (auth != null) return auth;
        var responseOperation = courseService.delCourseToUser(cur_user_id, user_id, course_id);
        if (Objects.equals(responseOperation, OperationRezult.Success)) return ResponseEntity.ok().body(responseOperation.toString());
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseOperation.toString());
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<?> addCourse(@RequestHeader("Authorization") Long session_id, @RequestBody AddCourseRequest request) {
        var auth = checkAuth(session_id, sessionService);
        Long user_id = sessionService.getUserIdFromSession(session_id);
        if (auth != null) return auth;
        if (request.name == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Заголовок не может быть пустым");
        var responseOperation = courseService.addCourse(request.name, request.description, request.image, user_id);
        if (Objects.equals(responseOperation, OperationRezult.Success)) return ResponseEntity.ok().body(responseOperation.toString());
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseOperation.toString());
    }

    @PostMapping(value = "/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<?> editCourse(@RequestHeader("Authorization") Long session_id, @RequestBody AddCourseRequest request, @RequestParam Long id) {
        var auth = checkAuth(session_id, sessionService);
        if (auth != null) return auth;
        Long user_id = sessionService.getUserIdFromSession(session_id);
        if (request.name == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Заголовок не может быть пустым");
        var responseOperation = courseService.editCourse(id, request.name, request.description, request.image, user_id);
        if (Objects.equals(responseOperation, OperationRezult.Success)) return ResponseEntity.ok().body(responseOperation.toString());
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseOperation.toString());
    }

    @PostMapping(value = "/delete")
    @CrossOrigin
    public ResponseEntity<?> delCourse(@RequestHeader("Authorization") Long session_id, @RequestParam Long id) {
        var auth = checkAuth(session_id, sessionService);
        if (auth != null) return auth;
        Long user_id = sessionService.getUserIdFromSession(session_id);
        var responseOperation = courseService.dellCourse(id, user_id);
        if (Objects.equals(responseOperation, OperationRezult.Success)) return ResponseEntity.ok().body(responseOperation.toString());
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseOperation.toString());
    }
}
