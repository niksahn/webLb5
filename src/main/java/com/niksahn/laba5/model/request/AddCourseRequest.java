package com.niksahn.laba5.model.request;

public class AddCourseRequest {
    public String name;
    public String description;
    public byte[] image;

    public AddCourseRequest() {
    }

    public AddCourseRequest(String name, String description, byte[] image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }
}
