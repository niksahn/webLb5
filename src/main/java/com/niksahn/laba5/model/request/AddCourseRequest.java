package com.niksahn.laba5.model.request;

public class AddCourseRequest {
    public String name;
    public String description;
    public String image;

    public Long user_id;

    public AddCourseRequest() {
    }

    public AddCourseRequest(String name, String description, String image, Long user_id) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.user_id = user_id;
    }
}
