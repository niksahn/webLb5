package com.niksahn.laba5.controller;

import com.niksahn.laba5.model.OperationRezult;
import com.niksahn.laba5.model.Role;
import com.niksahn.laba5.model.request.AvatarRequest;
import com.niksahn.laba5.model.request.EditUserRequest;
import com.niksahn.laba5.model.response.AllUsers;
import com.niksahn.laba5.model.response.LoginResponse;
import com.niksahn.laba5.model.response.RegistrationResponse;
import com.niksahn.laba5.repository.NewsRepository;
import com.niksahn.laba5.repository.UserCourseRepository;
import com.niksahn.laba5.service.FileService;
import com.niksahn.laba5.service.SessionService;
import com.niksahn.laba5.model.request.RegistrationRequest;
import com.niksahn.laba5.model.dto.UserDto;
import com.niksahn.laba5.model.response.UserResponse;
import com.niksahn.laba5.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.ObjectError;
import org.springframework.validation.FieldError;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.niksahn.laba5.Constants.avatar_path;
import static com.niksahn.laba5.controller.Common.checkAuth;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;
    private final SessionService sessionService;
    private final FileService fileService;

    private final NewsRepository newsRepository;
    private final UserCourseRepository userCourseRepository;
    private final String defaultAvatar = avatar_path + "default";

    @Autowired
    public UserController(UserRepository userRepository, SessionService sessionService, FileService fileService, NewsRepository newsRepository, UserCourseRepository userCourseRepository) {
        this.userRepository = userRepository;
        this.sessionService = sessionService;
        this.fileService = fileService;
        this.newsRepository = newsRepository;
        this.userCourseRepository = userCourseRepository;
    }

    @GetMapping(value = "/info")
    @CrossOrigin
    public ResponseEntity<?> user_page(@RequestHeader("Authorization") Long session_id) {
        var auth = checkAuth(session_id, sessionService);
        Long user_id = sessionService.getUserIdFromSession(session_id);
        var user = userRepository.findByUserId(user_id);
        if (auth != null) return auth;
        return ResponseEntity.status(HttpStatus.OK).body(fromUserDto(user));
    }

    @GetMapping(value = "/login")
    @CrossOrigin
    public ResponseEntity<?> login(@RequestParam String login, @RequestParam String password) {
        var user_inf = userRepository.findByLogin(login);
        if (user_inf == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Пользователь не существует");
        } else if (!Objects.equals(user_inf.getPassword(), password)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Неверный пароль");
        } else {
            var session = sessionService.getSession(user_inf.getId());
            user_inf.setEnter_сounter(user_inf.getEnter_сounter() + 1);
            userRepository.save(user_inf);
            return ResponseEntity.status(HttpStatus.OK).body(new LoginResponse(user_inf.getId(), session, user_inf.getRole()));
        }
    }

    @GetMapping(value = "/all")
    @CrossOrigin
    public ResponseEntity<?> getAll(@RequestHeader("Authorization") Long session_id) {
        var auth = checkAuth(session_id, sessionService);
        if (auth != null) return auth;
        var user_id = sessionService.getUserIdFromSession(session_id);
        var user = userRepository.findByUserId(user_id);
        if (user.getRole() != Role.admin) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(OperationRezult.No_Right.toString());
        ArrayList<AllUsers> users = new ArrayList<>();
        userRepository.findAll().forEach(it ->
        {
            var image = fileService.getImage(it.getAvatar(), defaultAvatar);
            users.add(new AllUsers(it.getId(), it.getEmail(), it.getLogin(), it.getPassword(), it.getRole(), image));
        });
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @PostMapping(value = "/delete")
    @CrossOrigin
    public ResponseEntity<?> dellUser(@RequestHeader("Authorization") Long session_id, @RequestParam Long user_id) {
        var auth = checkAuth(session_id, sessionService);
        if (auth != null) return auth;
        var userAdmin_id = sessionService.getUserIdFromSession(session_id);
        var user = userRepository.findByUserId(userAdmin_id);
        if (user.getRole() != Role.admin) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(OperationRezult.No_Right.toString());
        userRepository.deleteById(user_id);
        userCourseRepository.dellByUserId(user_id);
        newsRepository.dellByUserId(user_id);
        return ResponseEntity.status(HttpStatus.OK).body(OperationRezult.Success.toString());
    }


    @PostMapping(value = "/edit")
    @CrossOrigin
    public ResponseEntity<?> editUser(@RequestHeader("Authorization") Long session_id, @RequestParam Long user_id, @RequestBody EditUserRequest request) {
        var auth = checkAuth(session_id, sessionService);
        if (auth != null) return auth;
        var userAdmin_id = sessionService.getUserIdFromSession(session_id);
        var user = userRepository.findByUserId(userAdmin_id);
        if (user.getRole() != Role.admin) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(OperationRezult.No_Right.toString());
        var name = fileService.setImage(request.avatar, avatar_path + user.getId());
        userRepository.save(new UserDto(user_id, request.email, request.login, request.password, request.role, name));
        return ResponseEntity.status(HttpStatus.OK).body(OperationRezult.Success.toString());
    }

    @CrossOrigin
    @GetMapping(value = "/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") Long session_id) {
        sessionService.deleteSession(session_id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @CrossOrigin
    @PostMapping(value = "/registration", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addUser(@RequestBody @Valid RegistrationRequest user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String result = "";
            List<ObjectError> errors = bindingResult.getAllErrors();
            for (ObjectError i : errors) {
                String fieldErrors = ((FieldError) i).getField();
                result += (fieldErrors + " - " + i.getDefaultMessage() + "\n");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
        try {
            userRepository.save(new UserDto(user.email, user.login, user.password, Role.user, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Такой пользователь уже зарегистрирован");
        }
        var user_id = userRepository.findByLogin(user.login).getId();
        var session = sessionService.getSession(user_id);
        return ResponseEntity.status(HttpStatus.OK).body(new RegistrationResponse(user_id, session));
    }

    @CrossOrigin
    @PostMapping(value = "/avatar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uploadAvatar(@RequestHeader("Authorization") Long session_id, @RequestBody AvatarRequest file) {
        var auth = checkAuth(session_id, sessionService);
        if (auth != null) return auth;
        Long user_id = sessionService.getUserIdFromSession(session_id);
        var user = userRepository.findByUserId(user_id);
        var name = fileService.setImage(file.file, avatar_path + user.getId());
        if (name == null) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка загрузки аватара");
        }
        user.setAvatar(name);
        userRepository.save(user);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Успешно");
    }

    @CrossOrigin
    @GetMapping(value = "/avatar")
    @ResponseBody
    public ResponseEntity<?> getAvatar(@RequestHeader("Authorization") Long session_id) {
        Long user_id = sessionService.getUserIdFromSession(session_id);
        var user = userRepository.findByUserId(user_id);
        var auth = checkAuth(session_id, sessionService);
        if (auth != null) return auth;
        ArrayList<String> image;
        image = fileService.getImage(user.getAvatar(), defaultAvatar);
        if (image == null) return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Ошибка загрузки аватара");
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_PNG)
                .body(image);
    }

    public UserResponse fromUserDto(UserDto userDto) {
        ArrayList<String> avatar;
        avatar = fileService.getImage(userDto.getAvatar(), defaultAvatar);
        return new UserResponse(userDto.getId(), userDto.getEmail(), userDto.getLogin(), userDto.getRole(), userDto.getEnter_сounter(), avatar);
    }
}