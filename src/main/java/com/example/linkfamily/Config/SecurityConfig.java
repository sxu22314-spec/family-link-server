package com.example.linkfamily.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 1. 必须禁用 CSRF，否则 WebSocket 握手请求会被拦截
            .csrf(csrf -> csrf.disable())
            
            // 2. 配置跨域支持（重要：Security 层的跨域）
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            
            // 3. 配置路径放行规则
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/ws/**").permitAll()     // 放行 WebSocket 路径
                .requestMatchers("/photo/**").permitAll()  // 建议同时也放行图片/资源路径
                .anyRequest().permitAll()                  // 开发阶段可以先全部放行进行调试
            );
            
        return http.build();
    }

    // 辅助方法：配置跨域源
    private UrlBasedCorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(Collections.singletonList("*"));
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
