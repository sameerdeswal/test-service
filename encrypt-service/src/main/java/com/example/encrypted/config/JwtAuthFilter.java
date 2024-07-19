package com.example.encrypted.config;

import com.example.encrypted.exception.UnAuthorizedAccess;
import com.example.encrypted.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Set;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    
    private final Set<String> allowedUserSet;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private HandlerExceptionResolver handlerExceptionResolver;
    
    private JwtAuthFilter(Environment environment) {
        allowedUserSet = Set.of(environment.getRequiredProperty("allowed.usernames").split(","));
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            if (!request.getRequestURI().startsWith("/test/")) {
                filterChain.doFilter(request, response);
                return;
            }
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || authHeader.isEmpty()) {
                throw new UnAuthorizedAccess("Token not present");
            }
            final String jwt = authHeader.substring(7);
            String username = jwtService.extractUsername(jwt);
            if (!allowedUserSet.contains(username)) {
                throw new UnAuthorizedAccess("You are not allowed to access.");
            }
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
