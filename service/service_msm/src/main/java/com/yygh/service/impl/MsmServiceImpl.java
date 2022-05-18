package com.yygh.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cloopen.rest.sdk.BodyType;
import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.yygh.service.MsmService;
import com.yygh.vo.msm.MsmVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class MsmServiceImpl implements MsmService {
    @Override
    public boolean send(String phone, String random) {

        String serverIp = "app.cloopen.com";
        //请求端口
        String serverPort = "8883";
        //主账号,登陆云通讯网站后,可在控制台首页看到开发者主账号ACCOUNT SID和主账号令牌AUTH TOKEN
        String accountSId = "8aaf0708806f236e0180945062370747";
        String accountToken = "c8faf9dcedfc4cd498c07acbd4554265";
        //请使用管理控制台中已创建应用的APPID
        String appId = "8aaf0708806f236e01809450633e074e";
        CCPRestSmsSDK sdk = new CCPRestSmsSDK();
        sdk.init(serverIp, serverPort);
        sdk.setAccount(accountSId, accountToken);
        sdk.setAppId(appId);
        sdk.setBodyType(BodyType.Type_JSON);
        //发送短信至手机号
        String to = phone;
        //短信模板
        String templateId = "1";
        System.out.println("手机短信验证码--------------->" + random);
        //验证码为生成的随机数，5分钟内到期
        String[] datas = {random, "5"};
        HashMap<String, Object> result = sdk.sendTemplateSMS(to, templateId, datas);
        if ("000000".equals(result.get("statusCode"))) {
            //正常返回输出data包体信息（map）
            HashMap<String, Object> data = (HashMap<String, Object>) result.get("data");
            Set<String> keySet = data.keySet();
            for (String key : keySet) {
                Object object = data.get(key);
                System.out.println(key + " = " + object);
            }
            return true;
        } else {
            //异常返回输出错误码和错误信息
            System.out.println("错误码=" + result.get("statusCode") + " 错误信息= " + result.get("statusMsg"));
            return false;
        }

    }


    @Override
    public boolean send(MsmVo msmVo) {
        if (!StringUtils.isEmpty(msmVo.getPhone())) {
            boolean isSend = this.send(msmVo.getPhone(),"code");
            return isSend;
        }
        return false;
    }


}
