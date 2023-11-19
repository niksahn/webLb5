package com.niksahn.laba5.model;

import com.niksahn.laba5.model.dao.UserDto;
import jakarta.annotation.Nullable;


public class UserResponse {
    public String email;
    public String login;
    public Role role;
    public Long enterCounter;
    @Nullable
    public byte[] avatar;

    public UserResponse() {
    }

    public UserResponse(String email, String login, Role role, Long enterCounter, byte @org.jetbrains.annotations.Nullable [] avatar
    ) {
        this.email = email;
        this.role = role;
        this.login = login;
        this.enterCounter = enterCounter;
        this.avatar = avatar;
    }

    public static UserResponse fromUserDto(UserDto userDto) {
        return new UserResponse(userDto.getEmail(), userDto.getLogin(), userDto.getRole(), userDto.getEnterCounter(), userDto.getAvatar()
        );
    }
}


