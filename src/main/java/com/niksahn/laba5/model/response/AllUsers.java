package com.niksahn.laba5.model.response;

import com.niksahn.laba5.model.dto.UserDto;

import java.util.ArrayList;

public class AllUsers {
    ArrayList<UserDto> users;

    public AllUsers() {
    }

    public AllUsers(ArrayList<UserDto> users) {
        this.users = users;
    }
}
