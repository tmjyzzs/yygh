package com.yygh.service;


import com.yygh.vo.msm.MsmVo;
import org.springframework.stereotype.Service;

public interface MsmService {


    /**
     * 发送验证码
     * @param phone 手机号
     * @return 验证码
     */
    boolean send(String phone,String random);



    //mq使用发送短信
    boolean send(MsmVo msmVo);
}
