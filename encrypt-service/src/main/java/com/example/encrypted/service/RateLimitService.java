package com.example.encrypted.service;

import com.example.encrypted.config.CustomRateLimitConfig;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Log
@Service
public class RateLimitService {
    private final Map<String, Bucket> IP_BUCKETS = new ConcurrentHashMap<>();
    
    @Autowired
    private Environment environment;
    
    public Bucket resolveBucketByIp(String ipAddress, CustomRateLimitConfig customRateLimitConfig) {
        return IP_BUCKETS.computeIfAbsent(ipAddress, userPlan -> newBucket(customRateLimitConfig));
    }
    
    private Bucket newBucket(CustomRateLimitConfig customRateLimitConfig) {
        String key = customRateLimitConfig.type();
        String capacityStr = environment.getProperty("rate.limit." + key + ".capacity");
        if (capacityStr == null) {
            capacityStr = environment.getRequiredProperty("rate.limit.default.capacity");
        }
        String durationInSecondsStr = environment.getProperty("rate.limit." + key + ".durationInSeconds");
        if (durationInSecondsStr == null) {
            durationInSecondsStr = environment.getRequiredProperty("rate.limit.default.durationInSeconds");
        }
        int capacity = Integer.parseInt(capacityStr);
        int durationInSeconds = Integer.parseInt(durationInSecondsStr);
        return Bucket.builder().addLimit(Bandwidth.simple(capacity,
                Duration.ofSeconds(durationInSeconds))).build();
    }
}
