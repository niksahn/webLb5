package com.niksahn.laba5.controller;


import com.niksahn.laba5.manager.FileService;
import com.niksahn.laba5.manager.SessionService;
import com.niksahn.laba5.model.LoginResponse;
import com.niksahn.laba5.model.RegistrationRequest;
import com.niksahn.laba5.model.dto.UserDto;
import com.niksahn.laba5.model.UserResponse;
import com.niksahn.laba5.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.ObjectError;
import org.springframework.validation.FieldError;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static com.niksahn.laba5.Constants.image_path;
import static com.niksahn.laba5.controller.Common.checkAuth;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;
    private final SessionService sessionService;
    private final FileService fileService;

    @Autowired
    public UserController(UserRepository userRepository, SessionService sessionService, FileService fileService) {
        this.userRepository = userRepository;
        this.sessionService = sessionService;
        this.fileService = fileService;
    }

    @GetMapping(value = "/info")
    @CrossOrigin
    public ResponseEntity<?> user_page(@RequestHeader("Authorization") Long session_id, @RequestParam String login) {
        var user = userRepository.findByLogin(login);
        var auth = checkAuth(session_id, user.getId(), sessionService);
        if (auth != null) return auth;
        return ResponseEntity.status(HttpStatus.OK).body(fromUserDto(user));
    }

    @GetMapping(value = "/login")
    @CrossOrigin
    public ResponseEntity<?> login(@RequestParam String login, @RequestParam String password) {
        var user_inf = userRepository.findByLogin(login);
        System.out.println(user_inf);
        sessionService.deleteOutdates();
        if (user_inf == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Пользователь не существует");
        } else if (!Objects.equals(user_inf.getPassword(), password)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Неверный пароль");
        } else {
            var session = sessionService.getNewSession(user_inf.getId());
            user_inf.setEnter_сounter(user_inf.getEnter_сounter() + 1);
            userRepository.save(user_inf);
            return ResponseEntity.status(HttpStatus.OK).body(new LoginResponse(fromUserDto(user_inf), session));
        }
    }

    @CrossOrigin
    @PostMapping(value = "/logout")
    public ResponseEntity<?> logout(@RequestParam String login, @RequestHeader("Authorization") Long session_id) {
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
        userRepository.save(new UserDto(user.email, user.login, user.password, user.role, null));
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @CrossOrigin
    @PostMapping(value = "/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@RequestBody MultipartFile file, @RequestParam String login, @RequestHeader("Authorization") Long session_id) {
        var user = userRepository.findByLogin(login);
        var auth = checkAuth(session_id, user.getId(), sessionService);
        if (auth != null) return auth;
        var name = file.getOriginalFilename() + "_avatar_" + user.getLogin();
        if (user.getAvatar() != null) {
            fileService.deleteImage(image_path + user.getAvatar());
        }
        if (!fileService.setImage(file, name)) {
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
    public ResponseEntity<?> getAvatar(@RequestParam String login, @RequestHeader("Authorization") Long session_id) {
        UserDto user = userRepository.findByLogin(login);
        var auth = checkAuth(session_id, user.getId(), sessionService);
        if (auth != null) return auth;
        byte[] image;
        if (user.getAvatar() == null) {
            image = fileService.getImage("default.png");

        } else {
            image = fileService.getImage(user.getAvatar());
        }
        if (image == null) return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Ошибка загрузки аватара");
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_PNG)
                .body(image);
    }
    public  UserResponse fromUserDto(UserDto userDto) {
        byte[] avatar;
        if (userDto.getAvatar() == null) {
            avatar = fileService.getImage("default.png");
        } else {
            avatar = fileService.getImage(image_path + userDto.getAvatar());
        }
        return new UserResponse(userDto.getEmail(), userDto.getLogin(), userDto.getRole(), userDto.getEnter_сounter(), avatar);
    }
}