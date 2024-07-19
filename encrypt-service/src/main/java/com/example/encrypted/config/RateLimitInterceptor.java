package com.example.encrypted.config;

import com.example.encrypted.exception.RateLimitExceededException;
import com.example.encrypted.service.RateLimitService;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.concurrent.TimeUnit;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {
    
    @Autowired
    private RateLimitService rateLimitService;
    
    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object object) {
        final String ip = request.getRemoteAddr();
        
        CustomRateLimitConfig customRateLimitConfig = ((HandlerMethod) object).getMethodAnnotation(CustomRateLimitConfig.class);
        if (customRateLimitConfig == null) {
            return true;
        }
        final Bucket tokenBucket = rateLimitService.resolveBucketByIp(ip, customRateLimitConfig);
        final ConsumptionProbe consumptionProbe = tokenBucket.tryConsumeAndReturnRemaining(1);
        if (consumptionProbe.isConsumed()) {
            return true;
        }
        throw new RateLimitExceededException("Too Many Requests, Wait for " + TimeUnit.SECONDS.convert(consumptionProbe.getNanosToWaitForReset(), TimeUnit.NANOSECONDS));
    }
}