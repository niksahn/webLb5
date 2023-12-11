package com.niksahn.laba5.model.response;

import com.niksahn.laba5.model.Role;

import java.util.ArrayList;

public class UserResponse {

    public Long id;
    public String email;
    public String login;
    public Role role;
    public Long enterCounter;
    public ArrayList<String> avatar;

    public UserResponse() {
    }

    public UserResponse(Long id, String email, String login, Role role, Long enterCounter, ArrayList<String> avatar) {
        this.email = email;
        this.role = role;
        this.login = login;
        this.enterCounter = enterCounter;
        this.avatar = avatar;
        this.id = id;
    }
}


