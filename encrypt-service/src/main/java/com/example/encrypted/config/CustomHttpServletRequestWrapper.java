package com.example.encrypted.config;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class CustomHttpServletRequestWrapper extends HttpServletRequestWrapper {
    
    private final String body;
    
    public CustomHttpServletRequestWrapper(HttpServletRequest request, String body) {
        super(request);
        this.body = body;
    }
    
    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return byteArrayInputStream.available() == 0;
            }
            
            @Override
            public boolean isReady() {
                return true;
            }
            
            @Override
            public void setReadListener(ReadListener readListener) {
            }
            
            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }
        };
    }
}

