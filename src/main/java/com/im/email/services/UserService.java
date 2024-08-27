package com.im.email.services;

import com.im.email.dtos.UserResponse;
import com.im.email.models.User;
import com.im.email.repositories.UserRepository;
import com.im.email.util.RandomString;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder pe;
    private final EmailService mailService;

    public UserService(UserRepository repository, PasswordEncoder pe, EmailService mailService) {
        this.repository = repository;
        this.pe = pe;
        this.mailService = mailService;
    }

    public UserResponse save(User user) {
        if (repository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("This email already exists");
        } else {
            String encodedPassword = pe.encode(user.getPassword());
            user.setPassword(encodedPassword);

            String randomCode = RandomString.generatedRandomString(6);
            user.setVerificationCode(randomCode);
            user.setEnabled(false);

            User savedUser = repository.save(user);

            UserResponse userResponse = new UserResponse(
                    savedUser.getId(),
                    savedUser.getName(),
                    savedUser.getEmail(),
                    savedUser.getPassword(),
                    savedUser.getVerificationCode(),
                    savedUser.getEnabled());

            mailService.sendVerificationEmail(user);
            return userResponse;
        }
    }


    @Transactional
    @Modifying
    public boolean verify(String verificationCode) {
        User user = repository.findByVerificationCode(verificationCode);

        if (user == null || user.isEnabled()) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setEnabled(true);
            repository.saveAndFlush(user);

            return true;
        }
    }
}
