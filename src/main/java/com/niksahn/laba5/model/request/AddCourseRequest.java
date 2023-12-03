package com.niksahn.laba5.model.request;

public class AddCourseRequest {
    public String name;
    public String description;
    public String image;

    public AddCourseRequest() {
    }

    public AddCourseRequest(String name, String description, String image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }
}
