package org.example.haso.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 메시지 브로커 설정
        config.enableSimpleBroker("/topic"); // 메시지를 받을 곳
        config.setApplicationDestinationPrefixes("/app"); // 클라이언트로부터의 메시지를 받을 경로
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // STOMP 엔드포인트 등록
        registry.addEndpoint("/ws") // 클라이언트가 연결할 엔드포인트
                .setAllowedOrigins("*") // CORS 설정
                .withSockJS(); // SockJS 설정 (필요한 경우)
    }
}
