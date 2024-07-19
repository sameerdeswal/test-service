package com.example.encrypted.service;

import org.springframework.stereotype.Service;

@Service
public class EncryptService {
    
    public void performSomeTask() throws InterruptedException {
        Thread.sleep(Double.valueOf(Math.random() * 5).intValue());
    }
}
