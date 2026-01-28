package com.camping.life.base.util;

import lombok.Data;

/**
 * 全局统一返回结果工具类
 */
@Data
public class ResultUtil<T> {
    // 响应码：200=成功，500=失败
    private Integer code;
    // 响应信息
    private String msg;
    // 响应数据
    private T data;

    // 成功返回（带数据）
    public static <T> ResultUtil<T> success(T data) {
        ResultUtil<T> result = new ResultUtil<>();
        result.setCode(200);
        result.setMsg("操作成功");
        result.setData(data);
        return result;
    }

    // 成功返回（无数据）
    public static <T> ResultUtil<T> success() {
        return success(null);
    }

    // 失败返回
    public static <T> ResultUtil<T> error(String msg) {
        ResultUtil<T> result = new ResultUtil<>();
        result.setCode(500);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }
}