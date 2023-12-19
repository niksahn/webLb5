package com.niksahn.laba5.model.request;

import com.niksahn.laba5.model.Role;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class EditUserRequest {
    @NotEmpty(message = "Почта не введена")
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.]+@[a-zA-Z0-9.]+$", message = "Введите почту правильно")
    public String email;
    @NotEmpty(message = "Поле логина не заполнено")
    public String login;

    @NotEmpty(message = "Поле пароля не заполнено")
    public String password;
    public Role role;
    public @Nullable String avatar;

    EditUserRequest() {
    }

    public EditUserRequest(String email, String login, String password, Role role, @Nullable String avatar) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.login = login;
        this.avatar = avatar;
    }
}
