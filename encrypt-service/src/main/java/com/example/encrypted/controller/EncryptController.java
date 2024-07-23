package com.example.encrypted.controller;

import com.example.encrypted.config.CustomRateLimitConfig;
import com.example.encrypted.model.EncryptedRequest;
import com.example.encrypted.model.EncryptedResponse;
import com.example.encrypted.service.EncryptService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Log
@RestController
public class EncryptController {
    
    @Autowired
    private EncryptService encryptService;
    
    @CustomRateLimitConfig(type = "encrypt")
    @PostMapping("/backend/encrypt/message")
    public EncryptedResponse handle(@RequestBody EncryptedRequest encryptedRequest) {
        log.info("Message received " + encryptedRequest.getMessage());
        try {
            encryptService.performSomeTask();
        } catch (InterruptedException e) {
            throw new RuntimeException("Some error occurred");
        }
        return new EncryptedResponse("Server have received");
    }
}
