package com.im.email.dtos;

import com.im.email.models.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @NotBlank(message = "Preencha seu nome")
        String name,
        @NotBlank(message = "Preencha seu email")
        @Email
        String email,
        @NotBlank(message = "Informe uma senha válida")
        @Size(min = 6, max = 30)
        String password,
        @NotBlank
        @NotNull
        String role
) {

    public User toModel() {
       return new User(name, email, password, role);
    }
}
