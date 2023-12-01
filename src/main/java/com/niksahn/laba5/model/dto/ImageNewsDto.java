package com.niksahn.laba5.model.dto;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;

@Entity
@Table(name = "image_news")
public class ImageNewsDto {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_path")
    @Nullable
    private String image_path;

    @Column(name = "new_id")
    @Nullable
    private Long new_id;

    public ImageNewsDto(){}

    public ImageNewsDto(String image_path, Long new_id){
        this.new_id = new_id;
        this.image_path = image_path;
    }

    public Long getId() {
        return id;
    }

    @Nullable
    public String getImage_path() {
        return image_path;
    }

    @Nullable
    public Long getNew_id() {
        return new_id;
    }

    public void setImage_path(@Nullable String image_path) {
        this.image_path = image_path;
    }

    public void setNew_id(@Nullable Long new_id) {
        this.new_id = new_id;
    }
}
