package com.niksahn.laba5.model;

import com.niksahn.laba5.model.dto.NewsDto;

public class NewsResponse {
    public String user_login;
    public String title;
    public String description;

    public byte[] image;

    public NewsResponse() {
    }

    public NewsResponse(String userLogin, String title, String description, byte[] image) {
        this.description = description;
        this.title = title;
        this.user_login = userLogin;
        this.image = image;
    }
}
