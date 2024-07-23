package com.example.encrypted.config;

import com.example.encrypted.model.EncryptedRequestBody;
import com.example.encrypted.model.EncryptedResponseBody;
import com.example.encrypted.util.EncryptionUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.java.Log;

import java.util.logging.Level;

@Log
public class EncryptionFilter implements Filter {
    
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            
            String encryptedRequestBody = new String(httpRequest.getInputStream().readAllBytes());
            EncryptedRequestBody encryptedRequest = new ObjectMapper().readValue(encryptedRequestBody, EncryptedRequestBody.class);
            String decryptedRequestBody = EncryptionUtil.decrypt(encryptedRequest.getBody());
            CustomHttpServletRequestWrapper requestWrapper = new CustomHttpServletRequestWrapper(httpRequest, decryptedRequestBody);
            
            CustomHttpServletResponseWrapper responseWrapper = new CustomHttpServletResponseWrapper(httpResponse);
            chain.doFilter(requestWrapper, responseWrapper);
            
            String responseBody = new String(responseWrapper.getContentAsByteArray());
            String encryptedResponseBody = EncryptionUtil.encrypt(responseBody);
            EncryptedResponseBody encryptedResponse = new EncryptedResponseBody();
            encryptedResponse.setResponse(encryptedResponseBody);
            String responseJson = new ObjectMapper().writeValueAsString(encryptedResponse);
            httpResponse.getOutputStream().write(responseJson.getBytes());
        } catch (Exception exception) {
            log.log(Level.SEVERE, exception.getMessage(), exception);
        }
    }
}
