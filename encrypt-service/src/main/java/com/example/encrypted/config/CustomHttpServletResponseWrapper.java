package com.example.encrypted.config;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class CustomHttpServletResponseWrapper extends HttpServletResponseWrapper {
    
    private final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    private final ServletOutputStream servletOutputStream = new CustomServletOutputStream(byteArrayOutputStream);
    private final PrintWriter printWriter = new PrintWriter(servletOutputStream);
    
    public CustomHttpServletResponseWrapper(HttpServletResponse response) {
        super(response);
    }
    
    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return servletOutputStream;
    }
    
    @Override
    public PrintWriter getWriter() throws IOException {
        return printWriter;
    }
    
    @Override
    public void flushBuffer() throws IOException {
        super.flushBuffer();
        printWriter.flush();
        servletOutputStream.flush();
    }
    
    public byte[] getContentAsByteArray() {
        return byteArrayOutputStream.toByteArray();
    }
    
    private class CustomServletOutputStream extends ServletOutputStream {
        private final ByteArrayOutputStream baos;
        
        public CustomServletOutputStream(ByteArrayOutputStream baos) {
            this.baos = baos;
        }
        
        @Override
        public boolean isReady() {
            return true;
        }
        
        @Override
        public void setWriteListener(WriteListener writeListener) {
        }
        
        @Override
        public void write(int b) throws IOException {
            baos.write(b);
        }
    }
}
