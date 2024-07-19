package com.example.match.exceptions;

public class UnAuthorizedAccess extends RuntimeException{
    
    public UnAuthorizedAccess(String message) {
        super(message);
    }
}
