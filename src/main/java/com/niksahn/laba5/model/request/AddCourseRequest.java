package com.niksahn.laba5.model.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;

public class AddCourseRequest {
  //  @NotEmpty(message = "Поле названия не заполнено")
    public @Nullable String name;

    public @Nullable String description;

    public @Nullable String image;

    public AddCourseRequest() {
    }

    public AddCourseRequest(@Nullable String name, @Nullable String description, @Nullable String image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }
}
