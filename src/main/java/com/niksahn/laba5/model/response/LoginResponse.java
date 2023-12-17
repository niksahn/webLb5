package com.niksahn.laba5.model.response;

import com.niksahn.laba5.model.Role;

public class LoginResponse {
    public Long user_id;
    public Long session_id;

    public Role role;

    public LoginResponse() {
    }

    public LoginResponse(Long user_id, Long session_id, Role role) {
        this.user_id = user_id;
        this.session_id = session_id;
        this.role = role;
    }

}
