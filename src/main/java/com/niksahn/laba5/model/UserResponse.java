package com.niksahn.laba5.model;
import com.niksahn.laba5.model.Role;

public class UserResponse {
    private String email;
    private String login;
    private Role role;
    private Long enterCounter;

    public UserResponse() {
    }

    public UserResponse(String email, String login, Role role, Long enterCounter) {
        this.email = email;
        this.role = role;
        this.login = login;
        this.enterCounter = enterCounter;
    }

    public static UserResponse fromUserDto(UserDto userDto) {
        return new UserResponse(userDto.getEmail(), userDto.getLogin(), userDto.getRole(), userDto.getEnterCounter());
    }
}


