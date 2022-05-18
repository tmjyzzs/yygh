package com.yygh.controller;



import com.cloopen.rest.sdk.BodyType;
import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.yygh.Result;
import com.yygh.service.MsmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/msm")
public class ControllerMsn {

    /**
     * 发送短信验证码(只能固定手机号)
     * @param phone 手机号
     */

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @GetMapping("/send/{phone}")
    public Result send(@PathVariable String phone){

        //通过redis获取验证码
        String code = redisTemplate.opsForValue().get(phone);
        //如果有验证码则成功
        if(!StringUtils.isEmpty(code)){
            return Result.ok();
        }

        //如果从redis获取不到，
        // 生成验证码
        String random=(int)((Math.random()*9+1)*100000)+"";
        //调用service发送验证码
        boolean isSend = msmService.send(phone,random);

        if (isSend) {
            //存放到redis中
            redisTemplate.opsForValue().set(phone,random,2, TimeUnit.MINUTES);
           return Result.ok();
        }else {
            return Result.fail().message("发送短信失败");
        }







    }
}
