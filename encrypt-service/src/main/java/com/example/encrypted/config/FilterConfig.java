package com.example.encrypted.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    
    @Bean
    public FilterRegistrationBean<EncryptionFilter> encryptionFilter() {
        FilterRegistrationBean<EncryptionFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new EncryptionFilter());
        registrationBean.addUrlPatterns("/backend/*");
        return registrationBean;
    }
}
