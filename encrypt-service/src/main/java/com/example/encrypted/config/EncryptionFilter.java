package com.example.encrypted.config;

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

import java.util.Map;
import java.util.logging.Level;

@Log
public class EncryptionFilter implements Filter {
    
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            
            // Decrypt the request body
            String encryptedRequestBody = new String(httpRequest.getInputStream().readAllBytes());
            String decryptedRequestBody = EncryptionUtil.decrypt(encryptedRequestBody);
            CustomHttpServletRequestWrapper requestWrapper = new CustomHttpServletRequestWrapper(httpRequest, decryptedRequestBody);
            
            CustomHttpServletResponseWrapper responseWrapper = new CustomHttpServletResponseWrapper(httpResponse);
            // Process the request
            chain.doFilter(requestWrapper, responseWrapper);
            
            // Encrypt the response body
            String responseBody = new String(responseWrapper.getContentAsByteArray());
            String encryptedResponseBody = EncryptionUtil.encrypt(responseBody);
            Map<String, Object> responseMap = Map.of("response", encryptedResponseBody);
            String json = new ObjectMapper().writeValueAsString(responseMap);
            httpResponse.getOutputStream().write(json.getBytes());
        } catch (Exception exception) {
            log.log(Level.SEVERE, exception.getMessage(), exception);
        }
    }
}
