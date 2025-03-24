package org.example.haso.global.auth;

import ch.qos.logback.core.net.server.Client;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.haso.domain.auth.entity.MemberEntity;
import org.example.haso.global.exception.GlobalException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component
@RequiredArgsConstructor
public class JwtUtils {

    private final JwtProperties properties;
    private SecretKey secretKey;
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);


    @PostConstruct
    public void init() {
        String secretKey = properties.secretKey();
        System.out.println("JWT Secret Key: " + secretKey);
        // HS256 알고리즘에 사용된 서명 키의 크기가 너무 작음-> 403에러 -> 자동으로 적절한 크기의 비밀 키 생성
//        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

//        byte[] keyBytes = properties.secretKey().getBytes(StandardCharsets.UTF_8);
//        this.secretKey = Keys.hmacShaKeyFor(keyBytes);

        byte[] keyBytes = Base64.getDecoder().decode(properties.secretKey());
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }


    private String createToken(MemberEntity member, Long expiration) {
        long now = System.currentTimeMillis();

        if (member.getUserId() == null) {  // userId가 null일 경우 예외 처리
            throw new IllegalArgumentException("userId cannot be null");
        }

        return Jwts.builder()
                .claim("userId", member.getUserId())
                .issuedAt(new Date())
                .expiration(new Date(now + expiration))  // 만료 시간
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();
    }


    public TokenInfo generate(MemberEntity entity) {
        return new TokenInfo(
                createToken(entity, properties.accessExpire()),
                createToken(entity, properties.refreshExpire())
        );
    }

    public String refreshToken(MemberEntity entity) {
        return createToken(entity, properties.accessExpire());
    }

    public Claims parse(String token) {

        String cleanToken = token.replace("Bearer ", "");
//
//        // 디버깅용 로그
//        System.out.println("Parsing token: " + cleanToken);
//        System.out.println("JWT Secret Key: " + properties.secretKey()); // secretKey 출력

        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token.replace("Bearer ", ""))
                    .getPayload();
//            return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT: {}", e.getMessage());
            throw JwtException.EXPIRED.getException();
        } catch (SignatureException e) {
            logger.error("Invalid JWT Signature: {}", e.getMessage());
            throw JwtException.SIGNATURE.getException();
        } catch (MalformedJwtException e) {
            logger.error("Malformed JWT: {}", e.getMessage());
            throw JwtException.MALFORMED.getException();
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT: {}", e.getMessage());
            throw JwtException.UNSUPPORTED.getException();
        } catch (IllegalArgumentException e) {
            logger.error("JWT Illegal Argument: {}", e.getMessage());
            throw JwtException.ILLEGAL_ARGUMENT.getException();
        } catch (Exception e) {
            logger.error("Unexpected JWT error: {}", e.getMessage());
            throw GlobalException.SERVER_ERROR.getException();
        }

    }
}
