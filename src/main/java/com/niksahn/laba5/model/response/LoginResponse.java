package com.niksahn.laba5.model.response;

public class LoginResponse {
    public Long user_id;
    public Long session_id;

    public LoginResponse() {  }

    public LoginResponse(Long user_id, Long session_id) {
        this.user_id = user_id;
        this.session_id = session_id;
    }

}
