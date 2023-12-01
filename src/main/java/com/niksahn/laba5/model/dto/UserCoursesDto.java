package com.niksahn.laba5.model.dto;


import jakarta.persistence.*;

@Entity
@Table(name = "user_courses")
public class UserCoursesDto {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "course_id")
    private Long course_id;

    UserCoursesDto() {
    }

    public UserCoursesDto(Long user_id, Long course_id) {
        this.course_id = course_id;
        this.user_id = user_id;
    }

    public Long getId() {
        return id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getCourse_id() {
        return course_id;
    }

    public void setCourse_id(Long course_id) {
        this.course_id = course_id;
    }
}
