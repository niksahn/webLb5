package com.niksahn.laba5.model.response;

import com.niksahn.laba5.model.Role;
import com.niksahn.laba5.model.dto.UserDto;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;

import java.util.ArrayList;

public class AllUsers {
    public Long id;
    public String email;
    public String login;
    public String password;
    public Role role;
    public ArrayList<String> avatar;

    public AllUsers() {
    }

    public AllUsers(Long id,String email, String login, String password, Role role, ArrayList<String> avatar) {
        this.email = email;
        this.login = login;
        this.password = password;
        this.avatar = avatar;
        this.role = role;
        this.id = id;
    }
}
