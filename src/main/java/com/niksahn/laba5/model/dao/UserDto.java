package com.niksahn.laba5.model.dao;


import com.niksahn.laba5.model.Role;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Type;

import java.io.Serializable;


@Entity
@Table(name = "users")
public class UserDto implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotEmpty(message = "Почта не введена")
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.]+@[a-zA-Z0-9.]+$", message = "Введите почту правильно")
    private String email;
    @Column(unique = true)
    @NotEmpty(message = "Поле логина не заполнено")
    private String login;
    @Size(min = 4, max = 2000, message = "Размер должен быть от 4 до 2000")
    @NotEmpty(message = "Введите пароль")
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private Long enterCounter = 0L;

    @Lob
    @Nullable
    @Column(name = "avatar")
    private  byte[] avatar;

    public UserDto() {
    }

    public UserDto(String email, String login, String password, Role role , byte[] avatar
    ) {
        this.email = email;
        this.login = login;
        this.password = password;
        this.role = role;
        this.avatar = avatar;
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

    public Long getEnterCounter() {
        return enterCounter;
    }

    public void setEnterCounter(Long enterCounter) {
        this.enterCounter = enterCounter;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }
}



