package com.niksahn.laba5.controller;

import com.niksahn.laba5.model.Role;
import com.niksahn.laba5.model.request.AvatarRequest;
import com.niksahn.laba5.model.response.LoginResponse;
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
    private final String defaultAvatar = avatar_path + "default";

    @Autowired
    public UserController(UserRepository userRepository, SessionService sessionService, FileService fileService) {
        this.userRepository = userRepository;
        this.sessionService = sessionService;
        this.fileService = fileService;
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
            return ResponseEntity.status(HttpStatus.OK).body(new LoginResponse(user_inf.getId(), session));
        }
    }

    @CrossOrigin
    @PostMapping(value = "/logout")
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
        return ResponseEntity.status(HttpStatus.OK).body(new LoginResponse(user_id, session));
    }

    @CrossOrigin
    @PostMapping(value = "/avatar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uploadAvatar(@RequestHeader("Authorization") Long session_id, @RequestBody AvatarRequest file) {
        var auth = checkAuth(session_id, sessionService);
        if (auth != null) return auth;
        Long user_id = sessionService.getUserIdFromSession(session_id);
        var user = userRepository.findByUserId(user_id);
        var name = avatar_path + user.getId();
        if (!fileService.setImage(file.file, name)) {
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
        String image;
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
        String avatar;
        avatar = fileService.getImage(userDto.getAvatar(), defaultAvatar);
        return new UserResponse(userDto.getId(), userDto.getEmail(), userDto.getLogin(), userDto.getRole(), userDto.getEnter_сounter(), avatar);
    }
}