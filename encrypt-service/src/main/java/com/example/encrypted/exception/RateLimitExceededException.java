package com.example.encrypted.exception;

public class RateLimitExceededException extends RuntimeException{
    public RateLimitExceededException(String message) {
        super(message);
    }
}
