package com.SM.AUTH.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .formLogin(form -> form.disable())

            .httpBasic(basic -> basic.disable())

            .authorizeHttpRequests(auth -> auth

                // Public Auth APIs
                .requestMatchers(
                    "/auth/register",
                    "/auth/register/start",
                    "/auth/register/complete",
                    "/auth/login",
                    "/auth/refresh",
                    "/auth/send-otp",
                    "/auth/verify-otp"
                ).permitAll()

                // Temporary Public User APIs (for development/testing)
                .requestMatchers(
                    "/users/health",
                    "/users/me",
                    "/users/follow/**",
                    "/users/following"
                ).permitAll()

                // Everything else secured
                .anyRequest().authenticated()
            );

        return http.build();
    }
}