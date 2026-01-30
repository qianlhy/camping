package com.camping.life.base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 跨域配置类（全局 CORS 配置）
 * 解决前端访问后端时的跨域问题
 * 存放路径：com.camping.life.base.config.CorsConfig.java
 */
@Configuration
public class CorsConfig {

    /**
     * 配置 CORS 过滤器
     * 允许前端 http://localhost:5173 访问后端接口
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        // 允许的前端源（开发环境）
        config.addAllowedOrigin("http://localhost:5173");
        // 允许携带 Cookie
        config.setAllowCredentials(true);
        // 允许所有请求头
        config.addAllowedHeader("*");
        // 允许所有请求方法（GET、POST、PUT、DELETE、OPTIONS 等）
        config.addAllowedMethod("*");
        // 预检请求缓存时间（秒）
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 对所有接口生效
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
