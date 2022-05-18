package com.signKeyVerify;


import com.yygh.ResultCodeEnum;
import com.yygh.exception.YyghException;
import com.yygh_utils.MD5;

/**
 * @author 便利店狗砸ha
 * 医院签名的校验工具类
 */
public class SignKeyVerify {

    /**
     * @param sign 后台管理系统中数据库签名没有加密
     * @param signKey 医院系统中数据库已经完成加密操作
     */
    public static void signKeyVerify(String sign,String signKey){
        //把传递的签名和查询的签名进行比较(传递的签名使用md5加密了)
        String encrypt = MD5.encrypt(sign);

        //进行签名比较
        if(!signKey.equals(encrypt)){
            //一致返回mongodb中的数据
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }
    }
}
