package com.example.encrypted.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;

import java.io.IOException;
import java.io.InputStream;

public class DecodeHttpInputMessage implements HttpInputMessage {
    
    private final HttpHeaders headers;
    
    private final InputStream body;
    
    public DecodeHttpInputMessage(HttpHeaders headers, InputStream body) {
        super();
        this.headers = headers;
        this.body = body;
    }
    
    @Override
    public HttpHeaders getHeaders() {
        return this.headers;
    }
    
    @Override
    public InputStream getBody() throws IOException {
        return this.body;
    }
}