package com.niksahn.laba5.model.dto;

import com.niksahn.laba5.model.response.CourseResponse;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
@Table(name = "course")
public class CourseDto {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    @Nullable
    private String description;

    @Column(name = "image_path")
    private String image_path;


    public CourseDto() {
    }

    public CourseDto(String name, @Nullable String description, String image_path) {
        this.description = description;
        this.name = name;
        this.image_path = image_path;
    }

    public CourseDto(Long id, String name, @Nullable String description, String image_path) {
        this.description = description;
        this.id = id;
        this.name = name;
        this.image_path = image_path;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getImage_path() {
        return image_path;
    }

    public CourseResponse fromCourseDto(ArrayList<String> image) {
        return new CourseResponse(id, name, description, image);
    }
}
