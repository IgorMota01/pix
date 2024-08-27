package com.im.email.dtos;

import jakarta.validation.constraints.NotBlank;

public record WhatsAppMessageRequest(
        @NotBlank(message = "Favor informar número no formato correto, exemplo: +5583988552293")
        String toNumber,
        String messageBody
) {
}
