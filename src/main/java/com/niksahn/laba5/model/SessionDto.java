package com.niksahn.laba5.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "session")
public class SessionDto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;

    @Column(name = "creation_time")
    public Long time;

    @Column(name = "user_id")
    public Long user_id;

    public SessionDto(Long time, Long user_id) {
        this.time = time;
        this.user_id = user_id;
    }

    public SessionDto() {
    }
}
