package com.example.encrypted.exception;

public class UnAuthorizedAccess extends RuntimeException{
    
    public UnAuthorizedAccess(String message) {
        super(message);
    }
}
