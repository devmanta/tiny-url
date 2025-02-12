package com.devmanta.tinyurl.api.controller;

import com.devmanta.tinyurl.api.service.RedisService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/url")
public class TestContoller {

    private final RedisService redisService;

    public TestContoller(RedisService redisService) {
        this.redisService = redisService;
    }

    @GetMapping("/set")
    public String setRedisData() {
        redisService.setValues("mykey", "myvalue");
        return "Success";
    }

    @GetMapping("/get")
    public String getRedisData() {
        return redisService.getValues("mykey");
    }

}
