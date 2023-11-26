package com.niksahn.laba5.model.dto;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;

@Entity
@Table(name = "news")
public class NewsDto {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "image")
    @Nullable
    private String image;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    @Nullable
    private String description;

    @Column(name = "approved")
    private Boolean approved;

    public NewsDto(Long user_id, String image, String title, String description, Boolean approved) {
        this.user_id = user_id;
        this.approved = approved;
        this.description = description;
        this.image = image;
        this.title = title;
    }

    public NewsDto() {
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return user_id;
    }

    public void setUserId(Long user_id1) {
        user_id = user_id1;
    }

    public Boolean getApproved() {
        return approved;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }
}
