package com.im.email.controllers;

import com.im.email.configs.security.services.TokenService;
import com.im.email.dtos.AuthenticationRequest;
import com.im.email.dtos.AuthenticationResponse;
import com.im.email.models.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public LoginController(TokenService tokenService, AuthenticationManager authenticationManager) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid AuthenticationRequest auth){
        var usernamePassword = new UsernamePasswordAuthenticationToken(
                auth.email(), auth.password()
        );
        var authentication = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generatedToken((User) authentication.getPrincipal());

        return ResponseEntity.ok(new AuthenticationResponse(token));
    }
}
