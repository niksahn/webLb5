package com.niksahn.laba5.model.response;

import com.niksahn.laba5.model.dto.CourseDto;

public class CourseResponse {
    public Long id;
    public String name;
    public String description;
    public byte[] image;

    public CourseResponse() {
    }

    public CourseResponse(Long id, String name, String description, byte[] image) {
        this.description = description;
        this.name = name;
        this.image = image;
        this.id = id;
    }
}
