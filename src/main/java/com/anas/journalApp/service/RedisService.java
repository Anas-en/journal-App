package com.anas.journalApp.service;

import com.anas.journalApp.api.weather.WeatherResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    private RedisTemplate redisTemplate;

    RedisService(RedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
    }


    public <T> T get(String key, Class<T> entityClass){
        try{
            Object o = redisTemplate.opsForValue().get(key);
            if( o == null){
                log.warn("Cache missed  for key: "+key);
                return null;
            }
            ObjectMapper mapper = new ObjectMapper();

            //          the type of Which Class Data we need
            return mapper.readValue(o.toString() , entityClass);
        } catch (Exception e) {
            log.error("Error fetching key ,"+key+" Exception"+e);
            return null;
        }
    }

    public void set(String key , Object o , Long ttl){
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonValue = objectMapper.writeValueAsString(o);
            redisTemplate.opsForValue().set(key,jsonValue, ttl , TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("Exception "+e);
        }
    }

}
