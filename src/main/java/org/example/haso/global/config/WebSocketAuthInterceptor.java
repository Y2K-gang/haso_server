package org.example.haso.global.config;

import org.example.haso.domain.chat.bean.RedisSession;
import org.example.haso.domain.auth.entity.MemberEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;

import java.util.Map;

@Component
public class WebSocketAuthInterceptor implements HandshakeInterceptor {
    @Autowired
    private RedisSession redisSession;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        String token = request.getHeaders().getFirst("Authorization");  // 토큰을 헤더에서 받음
        if (token != null) {
            MemberEntity member = redisSession.getUserEntity(token);  // Redis에서 사용자 정보를 가져옴
            if (member != null) {
                attributes.put("member", member);  // 인증된 사용자 정보 저장
                return true;  // 인증 성공
            }
        }
        return false;  // 인증 실패
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        // 핸드셰이크 후 처리 (필요한 경우)
    }
}
