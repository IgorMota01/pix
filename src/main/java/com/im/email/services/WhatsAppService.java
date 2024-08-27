package com.im.email.services;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WhatsAppService {
    @Value("${twilio.whatsapp.from}")
    private String fromNumber;

    public String sendWhatsAppMessage(String toNumber, String messageBody) {
        Message message = Message.creator(
                new PhoneNumber("whatsapp:" + toNumber),
                new PhoneNumber("whatsapp:" + fromNumber),
                messageBody
        ).create();

        return message.getSid();
    }
}
