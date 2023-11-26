package com.niksahn.laba5.controller;

import com.niksahn.laba5.manager.SessionService;
import com.niksahn.laba5.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/news")
public class NewsController {
    private final UserRepository userRepository;
    private final SessionService sessionService;
    @Autowired
    public NewsController(UserRepository userRepository, SessionService sessionService) {
        this.userRepository = userRepository;
        this.sessionService = sessionService;
    }

}

