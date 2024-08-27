package com.im.email.repositories;

import com.im.email.models.EmailModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmailRepository extends JpaRepository<EmailModel, UUID> {
    EmailModel findByEmailToAndSubject(String emailTo, String subject);

}
