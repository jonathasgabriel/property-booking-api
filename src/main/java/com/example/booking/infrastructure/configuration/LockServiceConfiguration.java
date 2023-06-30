package com.example.booking.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;

import java.time.Duration;

@Configuration
public class LockServiceConfiguration {

    private static final String LOCK_NAME = "property-lock";
    private static final Duration RELEASE_TIME_DURATION = Duration.ofSeconds(30);

    @Bean
    public RedisLockRegistry redisLockRegistry(RedisConnectionFactory redisConnectionFactory) {
        RedisLockRegistry redisLockRegistry =
                new RedisLockRegistry(redisConnectionFactory, LOCK_NAME, RELEASE_TIME_DURATION.toMillis());

        return redisLockRegistry;
    }
}
