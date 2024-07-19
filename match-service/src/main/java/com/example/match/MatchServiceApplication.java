package com.example.match;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
@SpringBootApplication
public class MatchServiceApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(MatchServiceApplication.class, args);
    }
    
}