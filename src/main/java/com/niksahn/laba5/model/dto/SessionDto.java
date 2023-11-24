package com.niksahn.laba5.model.dto;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "sessions")
public class SessionDto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "creation_time")
    private Long time;

    @Column(name = "user_id")
    private Long user_id;

    public SessionDto(Long time, Long user_id) {
        this.time = time;
        this.user_id = user_id;
    }

    public SessionDto() {
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

    public Long getTime() {
        return time;
    }

    public void setTime(Long time1) {
        time = time1;
    }

}
