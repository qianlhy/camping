package com.camping.life.base.config;

import com.camping.life.admin.service.AdminUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Spring Security 全局核心配置类（全局通用，放在 base 模块）
 * 存放路径：com.camping.life.base.config.SecurityConfig.java
 */
@Configuration // 标记为 Spring 配置类
@EnableWebSecurity // 启用 Spring Security 网页安全功能
public class SecurityConfig {

    // 注入自定义的管理员用户详情服务（对接 admin_user 表）
    @Autowired
    private AdminUserDetailsService adminUserDetailsService;

    /**
     * 1. 密码加密器（必须配置，Spring Security 强制要求密码加密）
     * 与 admin_user 表中的密码加密方式（BCrypt）保持一致
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 2. 配置用户详情服务（告诉 Spring Security 从哪里读取用户信息）
     * 这里使用我们自定义的 AdminUserDetailsService（对接数据库）
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return adminUserDetailsService;
    }

    /**
     * 3. 配置 CORS 源
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:5173");
        configuration.setAllowCredentials(true);
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * 4. 核心安全过滤链（配置登录、权限、退出等核心规则）
     * 贴合露营项目：保护 /admin/** 接口，放开 /api/** 接口（小程序端）
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 启用 CORS
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // 关闭 CSRF 防护（开发环境可关闭，生产环境建议开启并配置白名单）
                .csrf(csrf -> csrf.disable())

                // 配置请求权限规则（核心：控制哪些接口需要登录，哪些可以匿名访问）
                .authorizeHttpRequests(auth -> auth
                        // ① 小程序端接口（/api/**）：允许匿名访问（无需登录，后续可添加 JWT 认证）
                        .requestMatchers("/api/**").permitAll()
                        // 临时放行 /admin/index 用于测试
                        .requestMatchers("/admin/index").permitAll()
//                        .antMatchers(HttpMethod.GET, "/admin/index").permitAll() // 放行 GET 方法
//                        .antMatchers(HttpMethod.POST, "/admin/index").permitAll() // 放行 POST 方法
                        // ② 管理端接口（/admin/**）：必须是 ADMIN 角色才能访问（保护后台）
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        // ③ 登录页、静态资源：允许匿名访问
                        .requestMatchers("/login", "/static/**", "/templates/**", "/error").permitAll()
                        // ④ 其他所有请求：必须登录后才能访问
                        .anyRequest().authenticated()
                )

                // 配置表单登录（使用 Spring Security 默认登录页，后续可自定义）
                .formLogin(form -> form
                        .loginPage("/login") // 指定登录页路径（默认就是 /login，可自定义为 /admin/login）
                        .defaultSuccessUrl("/admin/index", true) // 登录成功后强制跳转管理端首页
                        .failureUrl("/login?error=true") // 登录失败后跳转回登录页，带错误标记
                        .permitAll() // 登录相关请求允许匿名访问
                )

                // 配置退出登录（管理端退出）
                .logout(logout -> logout
                        .logoutUrl("/admin/logout") // 退出登录接口路径
                        .logoutSuccessUrl("/login") // 退出成功后跳转回登录页
                        .invalidateHttpSession(true) // 销毁当前会话（清除登录状态）
                        .deleteCookies("JSESSIONID") // 删除会话 Cookie
                        .permitAll()
                );

        return http.build();
    }
}