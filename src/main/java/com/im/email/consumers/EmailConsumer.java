package com.im.email.consumers;

import com.im.email.dtos.EmailDto;
import com.im.email.models.EmailModel;
import com.im.email.services.EmailService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    @Autowired
    EmailService emailService;

    public void listen(EmailDto emailDto) {
        EmailModel emailModel = new EmailModel();
        BeanUtils.copyProperties(emailDto, emailModel);
        emailService.sendEmail(emailModel);
        System.out.println("Email status: " + emailModel.getStatusEmail().toString());
    }
}
