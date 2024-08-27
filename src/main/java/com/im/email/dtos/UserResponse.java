package com.im.email.dtos;

public record UserResponse(
        Long id,
        String name,
        String email,
        String password,
        String verificationCode,
        Boolean enabled
) {
}
