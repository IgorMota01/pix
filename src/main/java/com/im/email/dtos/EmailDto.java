package com.im.email.dtos;

import com.im.email.models.EmailModel;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
public class EmailDto {

    @NotBlank
    private String ownerRef;
    @NotBlank @Email
    private String emailFrom;
    @NotBlank @Email
    private String emailTo;
    @NotBlank
    private String subject;
    @NotBlank
    private String text;

    public EmailModel convertToEmailModel(){
        var emailModel = new EmailModel();
        BeanUtils.copyProperties(this, emailModel);
        return emailModel;
    }
}
