package com.yygh.user.api;

import com.yygh.Result;
import com.yygh.model.user.UserInfo;
import com.yygh.user.service.UserInfoService;
import com.yygh.utils.AuthContextHolder;
import com.yygh.vo.user.LoginVo;
import com.yygh.vo.user.UserAuthVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author 便利店狗砸ha
 * 用户的登入接口
 */
@RestController
@RequestMapping("/api/user")
public class UserInfoApiController {
    @Autowired
    private UserInfoService userInfoService;

    /**
     * 用户手机号登录接口
     * @param loginVo 登入信息
     */
    @PostMapping("login")
    public Result login(@RequestBody LoginVo loginVo) {
        Map<String,Object> info = userInfoService.loginUser(loginVo);
        return Result.ok(info);
    }


    /**
     * 用户认证验证用户的身份信息
     * @param userAuthVo 前端传递的用户信息到后端验证
     *
     * @return 验证结果
     */
    @PostMapping("auth/userAuth")
    public Result userAuth(@RequestBody UserAuthVo userAuthVo, HttpServletRequest request) {
        //传递两个参数，第一个参数用户id，第二个参数认证数据vo对象
        userInfoService.userAuth(AuthContextHolder.getUserId(request), userAuthVo);
        return Result.ok();
    }

    /**
     * 获取根据id获取用户信息接口
     * @param request 前端请求
     *
     */
    @GetMapping("auth/getUserInfo")
    public Result getUserInfo(HttpServletRequest request) {
        //通过工具类解析token信息
        Long userId = AuthContextHolder.getUserId(request);
        UserInfo userInfo = userInfoService.getById(userId);
        return Result.ok(userInfo);
    }
}
