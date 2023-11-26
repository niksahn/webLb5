package com.niksahn.laba5.model;

import com.niksahn.laba5.manager.FileService;
import com.niksahn.laba5.model.dto.UserDto;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import java.io.IOException;

import static com.niksahn.laba5.Constants.image_path;

public class UserResponse {
    public String email;
    public String login;
    public Role role;
    public Long enterCounter;

    public byte[] avatar;

    public UserResponse() {
    }

    public UserResponse(String email, String login, Role role, Long enterCounter, byte[] avatar) {
        this.email = email;
        this.role = role;
        this.login = login;
        this.enterCounter = enterCounter;
        this.avatar = avatar;
    }
}


