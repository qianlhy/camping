package com.camping.life.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.camping.life.admin.domain.AdminUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 管理员 Mapper 接口（数据访问层）
 * 存放路径：com.camping.life.admin.mapper.AdminUserMapper.java
 */
@Mapper // 标记为 MyBatis Mapper 接口，让 Spring 扫描到
public interface AdminUserMapper extends BaseMapper<AdminUser> {

    /**
     * 根据用户名查询启用状态的管理员（核心：Spring Security 登录时需要）
     * @param username 登录用户名
     * @return 管理员信息（null 表示账号不存在或已禁用）
     */
    @Select("SELECT * FROM admin_user WHERE username = #{username} AND is_enable = 1")
    AdminUser selectEnableAdminByUsername(String username);
}