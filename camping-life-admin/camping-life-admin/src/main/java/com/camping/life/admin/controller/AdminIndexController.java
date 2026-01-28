package com.camping.life.admin.controller;

import com.camping.life.base.util.ResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 管理端首页控制器
 * 存放路径：com.camping.life.admin.controller.AdminIndexController.java
 */
@RestController
@RequestMapping("/admin")
public class AdminIndexController {

    /**W
     * 管理端首页接口（需要 ADMformLogin(form -> form.loginPage("/login"))IN 角色才能访问）
     */
    @GetMapping("/index")
    public ResultUtil<String> adminIndex() {
        return ResultUtil.success("欢迎进入露营小程序管理后台！");
    }
}