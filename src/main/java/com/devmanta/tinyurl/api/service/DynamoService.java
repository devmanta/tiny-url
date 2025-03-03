package com.devmanta.tinyurl.api.service;

import com.devmanta.tinyurl.api.repository.DynamoTestRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DynamoService {

    private final DynamoTestRepository dynamoTestRepository;

    public DynamoService(DynamoTestRepository dynamoTestRepository) {
        this.dynamoTestRepository = dynamoTestRepository;
    }

    public Map<String, String> testDynamo() {
        dynamoTestRepository.createTable();
        String uuid = dynamoTestRepository.saveUser("Charlotte");
        return dynamoTestRepository.getUser(uuid);
    }

}
