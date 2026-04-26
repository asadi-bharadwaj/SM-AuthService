package com.SM.AUTH.security;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    public String generateAccessToken(String username) {
        return "access-" + username + "-" + UUID.randomUUID();
    }

    public String generateRefreshToken(String username) {
        return "refresh-" + username + "-" + UUID.randomUUID();
    }
    
}