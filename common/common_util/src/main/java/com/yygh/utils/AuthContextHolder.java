package com.yygh.utils;

import com.yygh.helper.JwtHelper;

import javax.servlet.http.HttpServletRequest;

/**
 * 工具类
 * 获取token中的用户id和用户名称
 * @author 便利店狗砸ha
 */
public class AuthContextHolder {


    /**
     * 获取用户id
     * @param request 前端请求
     * @return 用户id
     */
    public static Long getUserId(HttpServletRequest request){
        String token = request.getHeader("token");
        Long userId = JwtHelper.getUserId(token);
        return userId;
    }

    /**
     * 获取用户名称
     * @param request  前端请求
     * @return 用户名称
     */
    public static String getUserName(HttpServletRequest request){
        String token = request.getHeader("token");
        String userName = JwtHelper.getUserName(token);
        return userName;
    }
}
