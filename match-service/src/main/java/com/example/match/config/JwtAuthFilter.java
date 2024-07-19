package com.example.match.config;

import com.example.match.exceptions.UnAuthorizedAccess;
import com.example.match.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    
    @Autowired
    private JwtService jwtService;
    @Autowired
    private HandlerExceptionResolver handlerExceptionResolver;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            if (request.getRequestURI().startsWith("/token/")) {
                filterChain.doFilter(request, response);
                return;
            }
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || authHeader.isEmpty()) {
                throw new UnAuthorizedAccess("Token not present");
            }
            final String jwt = authHeader.substring(7);
            boolean isExpired = jwtService.isTokenExpired(jwt);
            if (isExpired) {
                throw new UnAuthorizedAccess("Token expired");
            }
            
        } catch (Exception e) {
            handlerExceptionResolver.resolveException(request, response, null, e);
            return;
        }
        filterChain.doFilter(request, response);
    }
}
