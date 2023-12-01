package com.niksahn.laba5.model.dto;


import com.niksahn.laba5.model.Role;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "user")
public class UserDto implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String login;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name = "enter_counter")
    private Long enter_сounter = 0L;

    @Lob
    @Nullable
    @Column(name = "avatar")
    private String avatar;

    public UserDto() {}

    public UserDto(String email, String login, String password, Role role, @Nullable String avatar
    ) {
        this.email = email;
        this.login = login;
        this.password = password;
        this.avatar = avatar;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public Long getEnter_сounter() {
        return enter_сounter;
    }

    public void setEnter_сounter(Long enter_сounter) {
        this.enter_сounter = enter_сounter;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}



