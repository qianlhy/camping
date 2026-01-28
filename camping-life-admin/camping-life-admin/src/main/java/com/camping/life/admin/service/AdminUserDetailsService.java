package com.camping.life.admin.service;

import com.camping.life.admin.domain.AdminUser;
import com.camping.life.admin.mapper.AdminUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 自定义用户详情服务（对接 admin_user 表，给 Spring Security 提供用户信息）
 * 存放路径：com.camping.life.admin.service.AdminUserDetailsService.java
 */
@Service // 标记为 Spring 服务，让 Spring 管理
public class AdminUserDetailsService implements UserDetailsService {

    // 注入 AdminUserMapper，用于查询数据库
    @Autowired
    private AdminUserMapper adminUserMapper;

    /**
     * 核心方法：Spring Security 登录时，会调用该方法根据用户名查询用户信息
     * @param username 登录时输入的用户名
     * @return Spring Security 所需的 UserDetails 对象
     * @throws UsernameNotFoundException 用户名不存在时抛出异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. 从数据库查询启用状态的管理员
        AdminUser adminUser = adminUserMapper.selectEnableAdminByUsername(username);
        if (adminUser == null) {
            // 用户名不存在或已禁用，抛出异常，Spring Security 会返回登录失败
            throw new UsernameNotFoundException("管理员账号不存在或已禁用！");
        }

        // 2. 转换为 Spring Security 所需的 UserDetails 对象（关键）
        // 注意：角色格式为 "ROLE_XXX"，Spring Security 会自动处理前缀
        String role = adminUser.getRole() == 1 ? "ADMIN" : "OPERATOR"; // 1=超级管理员，2=运营管理员

        return User.withUsername(adminUser.getUsername())
                .password(adminUser.getPassword()) // 数据库中已加密的密码，直接使用（无需再次加密）
                .roles(role) // 赋予角色，对应 SecurityConfig 中的权限规则
                .build();
    }
}