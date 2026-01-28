package com.camping.life.admin.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 管理员实体类（对应数据库 admin_user 表）
 * 存放路径：com.camping.life.admin.domain.AdminUser.java
 */
@Data
@TableName("admin_user") // 映射数据库表名
public class AdminUser {
    @TableId(type = IdType.AUTO)
    private Integer id; // 管理员ID（自增）
    private String username; // 登录用户名（唯一）
    private String password; // 加密后的登录密码（BCrypt）
    private String realName; // 真实姓名
    private String phone; // 联系电话
    private Integer role; // 角色：1-超级管理员 2-运营管理员
    private Integer isEnable; // 是否启用：1-启用 0-禁用
    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 更新时间
}