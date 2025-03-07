package org.example.haso.global.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final StringRedisTemplate redisTemplate;



    // 데이터 가져오기
    public String getData(String key) {
        return redisTemplate.opsForValue().get(key);
    }

        // 데이터 저장 (만료 시간 설정)
    public void setDataExpire(String key, String value, long durationInSeconds) {
        redisTemplate.opsForValue().set(key, value, durationInSeconds, TimeUnit.SECONDS);
    }
    // 데이터 삭제
//    public void deleteData(String key) {
//        redisTemplate.delete(key);
//    }
}
