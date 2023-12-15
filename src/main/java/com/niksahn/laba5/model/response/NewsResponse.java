package com.niksahn.laba5.model.response;

import java.util.ArrayList;

public class NewsResponse {
    public Long id;
    public String user_login;
    public String title;
    public String description;
    public ArrayList<ArrayList<String>> images;

    public NewsResponse() {
    }

    public NewsResponse(Long id,String userLogin, String title, String description, ArrayList<ArrayList<String>> image) {
        this.id = id;
        this.description = description;
        this.title = title;
        this.user_login = userLogin;
        this.images = image;
    }
}
