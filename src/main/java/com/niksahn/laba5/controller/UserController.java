package com.niksahn.laba5.controller;


import com.niksahn.laba5.model.User;
import com.niksahn.laba5.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.ObjectError;
import org.springframework.validation.FieldError;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/user_page")
    public List<User> user_page(@RequestBody Map<String, String> login) {
        List<User> userList = new ArrayList<>();
        userList.addAll(userRepository.findAllByLogin(login.get("login")));
        return userList;
    }

    @PostMapping("/")
    public String login(@RequestBody User user) {
        for (User u : userRepository.findAll()) {
            if (u.getLogin().equals(user.getLogin()))
                if (u.getPassword().equals(user.getPassword())) {
                    u.setEnterCounter(u.getEnterCounter() + 1);
                    userRepository.save(u);
                    return "news_page";
                } else return "Пароль неверный";
        }
        return "Такого пользователя не существует";
    }

    @PostMapping(value = "/registration_page", consumes =
            MediaType.APPLICATION_JSON_VALUE)
    public String addUser(@RequestBody @Valid User user,
                          BindingResult bindingResult) {
        List<User> userList = new ArrayList<>();
        userRepository.findAll().forEach(it -> userList.add(it));
        if (bindingResult.hasErrors()) {
            String result = "";
            List<ObjectError> errors =
                    bindingResult.getAllErrors();
            for (ObjectError i : errors) {
                String fieldErrors = ((FieldError) i).getField();
                result += (fieldErrors + " - " +
                        i.getDefaultMessage() + "\n");
            }
            return result;
        }

        for (User user1 : userRepository.findAll()) {
            if (user1.getLogin().equals(user.getLogin()))
                return "Такой логин уже существует";
            else if (user1.getEmail().equals(user.getEmail()))
                return "Такая почта уже существует";
        }

        userRepository.save(user);
        return "OK";
    }
}