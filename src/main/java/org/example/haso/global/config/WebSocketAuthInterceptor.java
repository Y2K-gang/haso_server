package org.example.haso.global.config;

import org.example.haso.domain.auth.entity.MemberEntity;
import org.example.haso.global.auth.JwtUtils;  // JwtUtils 사용
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;

import java.util.Map;

@Component
public class WebSocketAuthInterceptor implements HandshakeInterceptor {

    private final JwtUtils jwtUtils;

    public WebSocketAuthInterceptor(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        String token = request.getHeaders().getFirst("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            String cleanToken = token.replace("Bearer ", "");

            try {
                MemberEntity member = jwtUtils.parse(cleanToken).get("userId", MemberEntity.class);

                if (member != null) {
                    attributes.put("member", member);
                    return true;
                } else {
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    return false;
                }
            } catch (Exception e) {
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return false;
            }
        }

        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
    }
}
