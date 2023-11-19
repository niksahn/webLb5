package com.niksahn.laba5.controller;


import com.niksahn.laba5.manager.SessionService;
import com.niksahn.laba5.model.LoginResponse;
import com.niksahn.laba5.model.dao.UserDto;
import com.niksahn.laba5.model.UserResponse;
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

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;
    private final SessionService sessionService;

    @Autowired
    public UserController(UserRepository userRepository, SessionService sessionService) {
        this.userRepository = userRepository;
        this.sessionService = sessionService;
    }

    @GetMapping(value = "/info")
    public ResponseEntity<?> user_page(@RequestHeader("Authorization") Long session_id, @RequestParam String login) {
        var user = userRepository.findByLogin(login);
        if (sessionService.sessionIsValid(session_id, user.getId()))
            return ResponseEntity.status(HttpStatus.OK).body(UserResponse.fromUserDto(user));
        else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestParam String login, @RequestParam String password) {
        System.out.println("AAAaA");
        var user_inf = userRepository.findByLogin(login);
        System.out.println(user_inf);
        sessionService.deleteOutdates();
        if (user_inf == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Пользователь не существует");
        } else if (!Objects.equals(user_inf.getPassword(), password)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Неверный пароль");
        } else {
            var session = sessionService.getNewSession(user_inf.getId());
            user_inf.setEnterCounter(user_inf.getEnterCounter() + 1);
            userRepository.save(user_inf);
            return ResponseEntity.status(HttpStatus.OK).body(new LoginResponse(UserResponse.fromUserDto(user_inf), session));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestParam String login, @RequestHeader("Authorization") Long session_id) {
        sessionService.deleteSession(session_id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PostMapping(value = "/registration", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addUser(@RequestBody @Valid UserDto user, BindingResult bindingResult) {
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
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Пользователь уже зарегистрирован");
        }
    }
}