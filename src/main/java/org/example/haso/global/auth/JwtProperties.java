package org.example.haso.global.auth;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperties (

//        @Length(min = 16)
        String secretKey,

        @NotBlank
        long accessExpire,

        @NotBlank
        long refreshExpire

) {
        public String getSecretKey() {
                return secretKey;
        }

        public long getAccessExpire() {
                return accessExpire;
        }

        public long getRefreshExpire() {
                return refreshExpire;
        }

}
