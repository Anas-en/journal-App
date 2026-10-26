package com.anas.journalApp.service;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest
@Slf4j
@ActiveProfiles("dev")
public class RedisCloudTests {

    @Test
    public void connectBasic() {
        try{
            RedisURI uri = RedisURI.Builder
                    .redis("cakes-look-reliable-40304.db.redis.io", 12372)
                    .withAuthentication("default", "XXXXXXXXXXX06")
                    .build();
            RedisClient client = RedisClient.create(uri);
            StatefulRedisConnection<String, String> connection = client.connect();
            RedisCommands<String, String> commands = connection.sync();

            commands.set("foo", "bar");
            String result = commands.get("foo");
            System.out.println(result); // >>> bar

            connection.close();

            client.shutdown();
        } catch (Exception e) {
            log.error("Exception "+e);
        }
    }
}






