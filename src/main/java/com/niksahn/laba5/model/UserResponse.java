package com.niksahn.laba5.model;

public class UserResponse {
    public String email;
    public String login;
    public Role role;
    public Long enterCounter;
    public byte[] avatar;

    public UserResponse() {}

    public UserResponse(String email, String login, Role role, Long enterCounter, byte[] avatar) {
        this.email = email;
        this.role = role;
        this.login = login;
        this.enterCounter = enterCounter;
        this.avatar = avatar;
    }
}


