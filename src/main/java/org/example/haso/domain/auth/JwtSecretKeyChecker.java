package org.example.haso.domain.auth;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class JwtSecretKeyChecker {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @PostConstruct
    public void init() {
        System.out.println("🔑 Loaded JWT Secret Key: " + secretKey);
    }
}
