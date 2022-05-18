package com.yygh.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yygh.model.user.UserInfo;
import com.yygh.vo.user.LoginVo;
import com.yygh.vo.user.UserAuthVo;
import com.yygh.vo.user.UserInfoQueryVo;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author 便利店狗砸ha
 * 处理用户的登入
 */

public interface UserInfoService extends IService<UserInfo> {


    /**
     * 用户的登入
     * @param loginVo 用户登入信息
     * @return 用户的个人信息
     */
    Map<String, Object> loginUser(LoginVo loginVo);

    UserInfo selectWxInfoOpenId(String openid);

    void userAuth(Long userId, UserAuthVo userAuthVo);


    IPage<UserInfo> selectPage(Page<UserInfo> pageParam, UserInfoQueryVo userInfoQueryVo);

    void lock(Long userId, Integer status);

    void approval(Long userId, Integer authStatus);

    /**
     * 更加用户的id获取用户的详细信息
     * @param userId 用户di
     * @return 用户的详细信息
     */
    Map<String, Object> show(Long userId);
}
