package com.niksahn.laba5.model.request;

import java.util.ArrayList;

public class AddNewsRequest {

    public Long user_id;
    public String title;
    public String description;
    public ArrayList<String> images;

    public AddNewsRequest() {
    }

    public AddNewsRequest(Long user_id, String title, String description, ArrayList<String> images ) {
        this.user_id = user_id;
        this.title = title;
        this.description = description;
        this.images = images;
    }
}
