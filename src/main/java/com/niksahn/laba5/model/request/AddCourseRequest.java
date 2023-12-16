package com.niksahn.laba5.model.request;

import jakarta.annotation.Nullable;

public class AddCourseRequest {
    public @Nullable String name;
    public @Nullable String description;
    public @Nullable String image;

    public AddCourseRequest() {
    }

    public AddCourseRequest( @Nullable String  name,@Nullable String description,@Nullable String image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }
}
