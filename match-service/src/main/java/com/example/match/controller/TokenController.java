package com.example.match.controller;

import com.example.match.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Service to get token to validate
 */
@RestController
public class TokenController {
    
    @Autowired
    private JwtService jwtService;
    
    
    @GetMapping("/token/admin")
    public String getAdminToken() {
        return jwtService.generateToken("admin");
    }
    
    @GetMapping("/token/nonAdmin")
    public String getNonAdminToken() {
        return jwtService.generateToken("nonAdmin");
    }
}
