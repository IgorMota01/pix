package com.im.email.controllers;

import com.im.email.services.PixSicoobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pix")
public class PixController {

    @Autowired
    private PixSicoobService pixSicoobService;

    @GetMapping("/api/v1/pix/cob")
    public ResponseEntity<String> getPixCobranca(
            @RequestParam String txid,
            @RequestHeader("Authorization") String accessToken) {
        return pixSicoobService.getPixCobranca(txid, accessToken);
    }
}
