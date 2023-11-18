package com.niksahn.laba5.model;

import java.io.Serializable;

public class LoginResponse implements Serializable {
    public UserDto user;
    public Long session_id;

    public LoginResponse() {
    }

    public LoginResponse(UserDto user, Long session_id) {
        this.user = user;
        this.session_id = session_id;
    }

}
