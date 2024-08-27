package com.im.email.services;

import com.im.email.enums.StatusEmail;
import com.im.email.models.EmailModel;
import com.im.email.models.User;
import com.im.email.repositories.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmailService {
    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private JavaMailSender emailSender;

    @Transactional
    public EmailModel sendEmail(EmailModel emailModel) {
        emailModel.setSendDateEmail(LocalDateTime.now());
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailModel.getEmailFrom());
            message.setTo(emailModel.getEmailTo());
            message.setSubject(emailModel.getSubject());
            message.setText(emailModel.getText());
            emailSender.send(message);

            emailModel.setStatusEmail(StatusEmail.SENT);
        } catch (MailException e) {
            emailModel.setStatusEmail(StatusEmail.ERROR);
        } finally {
            return emailRepository.save(emailModel);
        }
    }

    @Transactional
    public EmailModel sendVerificationEmail(User user) {
        // Verifica se o e-mail já foi enviado anteriormente
        EmailModel existingEmail = emailRepository.findByEmailToAndSubject(user.getEmail(), "Please verify your application");
        if (existingEmail != null && existingEmail.getStatusEmail() == StatusEmail.SENT) {
            // Se o e-mail já foi enviado, não envie novamente
            System.out.println("E-mail já foi enviado anteriormente");
            return existingEmail;
        }

        // Se o e-mail não foi enviado anteriormente, envie-o
        EmailModel emailModel = new EmailModel();
        emailModel.setOwnerRef("Application");
        emailModel.setEmailFrom("igormotafin@gmail.com");
        emailModel.setEmailTo(user.getEmail());
        emailModel.setSubject("Please verify your application");
        emailModel.setSendDateEmail(LocalDateTime.now());

        String content = "Seja bem-vindo, " + user.getName() + ".\n\nClique no link abaixo para verificar sua conta:\n";
        String verifyURL = "http://localhost:8080/users/verify?code=" + user.getVerificationCode();
        emailModel.setText(content + verifyURL);

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailModel.getEmailFrom());
            message.setTo(emailModel.getEmailTo());
            message.setSubject(emailModel.getSubject());
            message.setText(emailModel.getText());


            emailSender.send(message);
            emailModel.setStatusEmail(StatusEmail.SENT);
        } catch (MailException e) {
            emailModel.setStatusEmail(StatusEmail.ERROR);
            System.err.println("Erro ao enviar o e-mail: " + e.getMessage());
            e.printStackTrace();
        } finally {
            return emailRepository.save(emailModel);
        }
    }




    public Page<EmailModel> findAll(Pageable pageable) {
        return emailRepository.findAll(pageable);
    }

    public Optional<EmailModel> findById(UUID emailId) {
        return emailRepository.findById(emailId);

    }
}
