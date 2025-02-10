package org.example.haso.domain.chat.bean;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;  // Jackson ObjectMapper
import org.example.haso.domain.auth.entity.MemberEntity;

@Component
public class RedisSession {

    private final ValueOperations<String, String> tokens;
    private final ObjectMapper objectMapper;  // Jackson ObjectMapper

    // 기본 생성자 (ObjectMapper가 주입되지 않은 경우를 처리)
    public RedisSession(StringRedisTemplate redisTemplate) {
        this.tokens = redisTemplate.opsForValue();
        this.objectMapper = new ObjectMapper();  // ObjectMapper 없으면 기본 객체 사용
    }

    @Autowired
    public RedisSession(StringRedisTemplate redisTemplate, ObjectMapper objectMapper) {
        this.tokens = redisTemplate.opsForValue();
        this.objectMapper = objectMapper;  // Jackson ObjectMapper 주입
    }

    // 토큰을 가져오는 메소드
    public String getToken(MemberEntity memberEntity) {
        String tokenKey = memberEntity.getUserId();  // 사용자 아이디로 토큰 관리
        String oldToken = tokens.get(tokenKey);
        if (oldToken != null) {
            return oldToken;
        }
        String newToken = UUID.randomUUID().toString();  // 새로운 UUID를 생성
        tokens.set(tokenKey, newToken, 30, TimeUnit.MINUTES);  // 30분 동안 저장
        return newToken;
    }

    // 사용자 정보와 토큰을 연결하는 메소드
    public void setToken(String token, MemberEntity memberEntity) {
        int timeout = 30;  // 기본 30분
        try {
            String memberJson = objectMapper.writeValueAsString(memberEntity);  // Jackson을 사용하여 객체를 JSON으로 직렬화
            tokens.set(token, memberJson, timeout, TimeUnit.MINUTES);  // 토큰과 사용자 정보를 Redis에 저장
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 토큰으로 사용자 정보를 가져오는 메소드
    public MemberEntity getUserEntity(String token) {
        String json = tokens.get(token);
        if (json == null) return null;
        try {
            return objectMapper.readValue(json, MemberEntity.class);  // Jackson을 사용하여 JSON을 MemberEntity로 변환
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
