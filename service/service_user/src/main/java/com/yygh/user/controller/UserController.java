package com.yygh.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yygh.Result;
import com.yygh.model.user.UserInfo;
import com.yygh.user.service.UserInfoService;
import com.yygh.vo.user.UserInfoQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author 便利店狗砸ha
 * 完成微信调用的接口
 */
@RestController
@RequestMapping("/admin/user")
public class UserController {


    @Autowired
    private UserInfoService userInfoService;


    /**
     * 用户列表（条件查询带分页）
     */
    @GetMapping("{page}/{limit}")
    public Result list(@PathVariable Long page,
                       @PathVariable Long limit,
                       UserInfoQueryVo userInfoQueryVo) {
        Page<UserInfo> pageParam = new Page<>(page,limit);
        IPage<UserInfo> pageModel =
                userInfoService.selectPage(pageParam,userInfoQueryVo);
        return Result.ok(pageModel);
    }

    /**
     * 根据用户id进行锁定
     * */
    @GetMapping("lock/{userId}/{status}")
    public Result lock(@PathVariable Long userId,@PathVariable Integer status) {
        userInfoService.lock(userId,status);
        return Result.ok();
    }

    //用户详情
    @GetMapping("show/{userId}")
    public Result show(@PathVariable Long userId) {
        Map<String,Object> map = userInfoService.show(userId);
        return Result.ok(map);
    }

    //认证审批
    @GetMapping("approval/{userId}/{authStatus}")
    public Result approval(@PathVariable Long userId,@PathVariable Integer authStatus) {
        userInfoService.approval(userId,authStatus);
        return Result.ok();
    }

}
