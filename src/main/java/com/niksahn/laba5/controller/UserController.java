package com.niksahn.laba5.controller;


import com.niksahn.laba5.manager.SessionManager;
import com.niksahn.laba5.model.LoginResponse;
import com.niksahn.laba5.model.UserDto;
import com.niksahn.laba5.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.ObjectError;
import org.springframework.validation.FieldError;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Objects;

@RestController
public class UserController {
    private final UserRepository userRepository;
    private final SessionManager sessionManager;

    @Autowired
    public UserController(UserRepository userRepository, SessionManager sessionManager) {
        this.userRepository = userRepository;
        this.sessionManager = sessionManager;
    }

    @PostMapping(value = "/user_info")
    public ResponseEntity<?> user_page(@RequestHeader("Authorization") Long session_id, @RequestBody String user_login) {
        var user = userRepository.findByLogin(user_login);
        if (sessionManager.sessionIsValid(session_id, user.id)) return ResponseEntity.status(HttpStatus.OK).body(user);
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto user) {
        var user_inf = userRepository.findByLogin(user.login);
        if (user_inf == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else if (!Objects.equals(user_inf.password, user.password)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            var session = sessionManager.getNewSession(user_inf.id);
            return ResponseEntity.status(HttpStatus.OK).body(new LoginResponse(user_inf, session));
        }
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
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}