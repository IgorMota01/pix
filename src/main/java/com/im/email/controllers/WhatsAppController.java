package com.im.email.controllers;

import com.im.email.dtos.WhatsAppMessageRequest;
import com.im.email.services.WhatsAppService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/whats")
public class WhatsAppController {
    private final WhatsAppService whatsAppService;

    public WhatsAppController(WhatsAppService whatsAppService) {
        this.whatsAppService = whatsAppService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendWhatsAppMessage(@RequestBody WhatsAppMessageRequest request) {
        String messageSid = whatsAppService.sendWhatsAppMessage(request.toNumber(), request.messageBody());
        return ResponseEntity.ok("Lembrança de dívida" + messageSid);
    }
}
