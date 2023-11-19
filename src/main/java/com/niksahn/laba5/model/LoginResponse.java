package com.niksahn.laba5.model;

public class LoginResponse {
    public UserResponse user;
    public Long session_id;

    public LoginResponse() {
    }

    public LoginResponse(UserResponse user, Long session_id) {
        this.user = user;
        this.session_id = session_id;
    }

}
