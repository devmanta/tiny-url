package com.devmanta.tinyurl.api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RedisServiceTest {

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    @InjectMocks
    private RedisService redisService;

    @BeforeEach
    public void setup() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    public void setValue() {
        // Given
        String key = "testKey";
        String data = "testData";

        // When
        redisService.setValues(key, data);

        // Then
        verify(valueOperations).set(key, data);
    }

    @Test
    public void setValueWithTimeout() {
        // Given
        String key = "testKey";
        String data = "testData";
        long timeout = 1000L;

        // When
        redisService.setValuesWithTimeout(key, data, timeout);

        // Then
        verify(valueOperations).set(key, data, Duration.ofMillis(timeout));
    }

    @Test
    public void getValue() {
        // Given
        String key = "testKey";
        String expectedData = "testData";
        when(valueOperations.get(key)).thenReturn(expectedData);

        // When
        String actualData = redisService.getValues(key);

        // Then
        assertEquals(expectedData, actualData);
        verify(valueOperations).get(key);
    }

    @Test
    public void getValueReturnsNull() {
        // Given
        String key = "nonExistentKey";
        when(valueOperations.get(key)).thenReturn(null);

        // When
        String actualData = redisService.getValues(key);

        // Then
        assertNull(actualData);
        verify(valueOperations).get(key);
    }

}
