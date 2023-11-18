package com.niksahn.laba5.controller;


import com.niksahn.laba5.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
//
//@Controller
//public class GetControllers {
//    private final UserRepository userRepository;
//
//    public GetControllers(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//
//    @GetMapping("/")
//    public String enter(Model model){
//        return "enter_page";
//    }
//
//    @GetMapping("/registration_page")
//    public String registration(Model model){
//        return "registration_page";
//    }
//
//    @GetMapping("/news_page")
//    public String news(Model model){
//        return "news_page";
//    }
//
//    @GetMapping("/news_page2")
//    public String admin_news_page(Model model) {
//        return "news_page2";
//    }
//
//    @GetMapping("/user_page")
//    public String user_page(Model model){
//        return "user_page";
//    }
//
//    @GetMapping("/user_page2")
//    public String admin_user_page(Model model){
//        return "user_page2";
//    }
//
//    @GetMapping("/admin_page")
//    public String admin_page(Model model){
//        return "admin_page";
//    }
//}