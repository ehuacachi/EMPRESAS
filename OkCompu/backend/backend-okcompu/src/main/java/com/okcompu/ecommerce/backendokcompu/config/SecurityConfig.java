package com.okcompu.ecommerce.backendokcompu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Puedes elegir entre varios encoders
        return new BCryptPasswordEncoder(12); // 12 es el factor de trabajo (strength)
    }
}