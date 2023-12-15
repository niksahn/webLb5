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

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    @Nullable
    private String description;

    public NewsDto(Long user_id, String title, @Nullable String description) {
        this.user_id = user_id;
        this.description = description;
        this.title = title;
    }

    public NewsDto(Long id, Long user_id, String title, @Nullable String description) {
        this.user_id = user_id;
        this.description = description;
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
