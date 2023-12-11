package com.niksahn.laba5.model.response;

import java.util.ArrayList;

public class NewsResponse {
    public String user_login;
    public String title;
    public String description;
    public ArrayList<ArrayList<String>> images;

    public NewsResponse() {
    }

    public NewsResponse(String userLogin, String title, String description, ArrayList<ArrayList<String>> image) {
        this.description = description;
        this.title = title;
        this.user_login = userLogin;
        this.images = image;
    }
}
