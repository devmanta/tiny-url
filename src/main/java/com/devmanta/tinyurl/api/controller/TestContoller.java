package com.devmanta.tinyurl.api.controller;

import com.devmanta.tinyurl.api.service.DynamoService;
import com.devmanta.tinyurl.api.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/url")
@RequiredArgsConstructor
public class TestContoller {

    private final RedisService redisService;
    private final DynamoService dynamoService;


    @GetMapping("/set")
    public String setRedisData() {
        redisService.setValues("mykey", LocalDateTime.now().toString());
        return "Success";
    }

    @GetMapping("/get")
    public String getRedisData() {
        return redisService.getValues("mykey");
    }

    @GetMapping("/dynamo")
    public Map<String, String> testDynamo() {
        return dynamoService.testDynamo();
    }

}
