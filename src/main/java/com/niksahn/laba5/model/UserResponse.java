package com.niksahn.laba5.model;

import com.niksahn.laba5.model.dto.UserDto;

public class UserResponse {
    public String email;
    public String login;
    public Role role;
    public Long enterCounter;

    public UserResponse() {
    }

    public UserResponse(String email, String login, Role role, Long enterCounter) {
        this.email = email;
        this.role = role;
        this.login = login;
        this.enterCounter = enterCounter;
    }

    public static UserResponse fromUserDto(UserDto userDto) {
        return new UserResponse(userDto.getEmail(), userDto.getLogin(), userDto.getRole(), userDto.getEnterCounter()
        );
    }
}


