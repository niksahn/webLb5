package com.niksahn.laba5.model;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegistrationRequest {
    @NotEmpty(message = "Почта не введена")
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.]+@[a-zA-Z0-9.]+$", message = "Введите почту правильно")
    public String email;
    @NotEmpty(message = "Поле логина не заполнено")
    public String login;
    @Size(min = 4, max = 250, message = "Размер должен быть от 4 до 250")
    @NotEmpty(message = "Введите пароль")
    public String password;
    public Role role;
}
