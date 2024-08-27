package com.im.email.controllers;

import com.im.email.configs.security.services.TokenService;
import com.im.email.dtos.AuthenticationRequest;
import com.im.email.dtos.AuthenticationResponse;
import com.im.email.dtos.UserRequest;
import com.im.email.dtos.UserResponse;
import com.im.email.models.User;
import com.im.email.services.EmailService;
import com.im.email.services.UserService;
import jakarta.validation.Valid;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final EmailService emailService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public UserController(UserService userService, EmailService emailService, TokenService tokenService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.emailService = emailService;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    public ResponseEntity<UserResponse> insert(@RequestBody @Valid UserRequest obj) {
        User user = obj.toModel();
        try {
            UserResponse userSaved = userService.save(user);

            // Enviar e-mail após salvar o usuário
            emailService.sendVerificationEmail(user);

            return ResponseEntity.ok().body(userSaved);
        } catch (Exception e) {
            // Adicionar log para capturar o erro
            System.err.println("Erro ao salvar o usuário: " + e.getMessage());
            return ResponseEntity.status(500).body(null); // Responder com erro 500
        }
    }





    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code) {
        if (userService.verify(code)) {
            return "verify_success";
        } else {
            return "verify_fail";
        }
    }

}
