package com.anas.journalApp.service;


import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("dev")
@Disabled
public class RedisTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void testSaveEmail(){
        redisTemplate.opsForValue().set("email" , "anas@email.com");

        Object email = redisTemplate.opsForValue().get("salary");

        int a= 1;
    }
}
