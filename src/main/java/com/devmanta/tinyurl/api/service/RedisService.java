package com.devmanta.tinyurl.api.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setValues(String key, String data) {
        redisTemplate.opsForValue().set(key, data);
    }

    public void setValuesWithTimeout(String key, String data, long timeout) {
        redisTemplate.opsForValue().set(key, data, Duration.ofMillis(timeout));
    }

    public String getValues(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    public void deleteValues(String key) {
        redisTemplate.delete(key);
    }

}
