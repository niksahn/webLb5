package com.niksahn.laba5.model.response;

import java.util.ArrayList;

public class CourseResponse {
    public Long id;
    public String name;
    public String description;
    public ArrayList<String> image;

    public CourseResponse() {
    }

    public CourseResponse(Long id, String name, String description,  ArrayList<String> image) {
        this.description = description;
        this.name = name;
        this.image = image;
        this.id = id;
    }
}
