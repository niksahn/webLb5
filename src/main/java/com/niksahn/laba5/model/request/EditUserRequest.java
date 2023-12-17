package com.niksahn.laba5.model.request;

import com.niksahn.laba5.model.Role;

public class EditUserRequest {
    public String email;
    public String login;
    public String password;
    public Role role;
    public Long enter_сounter;

    public String avatar;

    EditUserRequest() {
    }

    public EditUserRequest(String email, String login, String password, Role role, Long enter_сounter, String avatar) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.login = login;
        this.enter_сounter = enter_сounter;
        this.avatar = avatar;
    }
}
