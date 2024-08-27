package com.im.email.dtos;

public record AuthenticationRequest(
        String email, String password
) {
}
