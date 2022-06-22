package com.triple.club.config.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

// JWP Properties 정보를 담고 있는 클래스
@Getter
@RequiredArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {
    private final Jwt jwt;

    @Getter
    @RequiredArgsConstructor
    public static class Jwt{
        private final String secret;
        private final int EXPIRATION_TIME = 60 * 60 * 2 * 1000; // JWT Token의 유효기간.
        private final String TOKEN_PREFIX = "Bearer"; // JWT Token의 prefix는 Bearer
        private final String HEADER_STRING = "Authorization"; // JWT Token은 Authorization header로 전달된다.
    }
}
