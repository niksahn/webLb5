package com.niksahn.laba5.model;


import jakarta.persistence.*;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

@Entity
@Table(name = "users")
public class UserDto implements Serializable {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(unique = true)
    @NotEmpty(message = "Почта не введена")
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.]+@[a-zA-Z0-9.]+$", message = "Введите почту правильно")
    public String email;
    @Column(unique = true)
    @NotEmpty(message = "Поле логина не заполнено")
    public String login;
    @Size(min = 4, max = 2000, message = "Размер должен быть от 4 до 2000")
    @NotEmpty(message = "Введите пароль")
    public String password;
    public String role;
    public Long enterCounter = 0L;
    
    public UserDto() {
    }

    public UserDto(String email, String login, String password,
                   String role) {
        this.email = email;
        this.login = login;
        this.password = password;
        this.role = role;
    }
}