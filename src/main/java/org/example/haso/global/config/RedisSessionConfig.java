package org.example.haso.global.config;

import org.example.haso.domain.chat.bean.RedisSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class RedisSessionConfig {
    @Bean
    public RedisSession redisSession(StringRedisTemplate redisTemplate) {
        return new RedisSession(redisTemplate);
    }
}
