package com.devmanta.tinyurl.api.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class DynamoTestRepository {

    private final DynamoDbClient dynamoDbClient;
    private static final String TABLE_NAME = "Users";

    public void createTable() {
        // 테이블 생성 요청
        CreateTableRequest createTableRequest = CreateTableRequest.builder()
                .tableName(TABLE_NAME)
                .keySchema(KeySchemaElement.builder()
                        .attributeName("id")
                        .keyType(KeyType.HASH)  // PK
                        .build())
                .attributeDefinitions(AttributeDefinition.builder()
                        .attributeName("id")
                        .attributeType(ScalarAttributeType.S)  // id data type
                        .build())
                .provisionedThroughput(ProvisionedThroughput.builder()
                        .readCapacityUnits(5L)
                        .writeCapacityUnits(5L)
                        .build())  // set Read Write capa
                .build();

        dynamoDbClient.createTable(createTableRequest);
    }

    public String saveUser(String name) {
        String uuid = UUID.randomUUID().toString();

        Map<String, AttributeValue> item = new HashMap<>();
        item.put("id", AttributeValue.builder().s(uuid).build());
        item.put("name", AttributeValue.builder().s(name).build());

        PutItemRequest request = PutItemRequest.builder()
                .tableName(TABLE_NAME)
                .item(item)
                .build();

        dynamoDbClient.putItem(request);

        return uuid;
    }

    public Map<String, String> getUser(String id) {
        GetItemRequest request = GetItemRequest.builder()
                .tableName(TABLE_NAME)
                .key(Map.of("id", AttributeValue.builder().s(id).build()))
                .build();

        Map<String, AttributeValue> item = dynamoDbClient.getItem(request).item();

        Map<String, String> result = new HashMap<>();
        item.forEach((key, value) -> result.put(key, value.s()));

        return result;
    }

}
