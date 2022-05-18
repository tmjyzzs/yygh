package com.yygh.oss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author 便利店狗砸ha
 * 读取配置文件中的内容
 */
@Component
public class ConstantOssPropertiesUtils implements InitializingBean {
    @Value("${endpointaliyun.oss}")
    private String endpoint ;

    @Value("${aliyun.oss.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.oss.secret}")
    private String accessKeySecret;

    @Value("${aliyun.oss.bucket}")
    private String bucket;

    public static String EDNPOINT;
    public static String ACCESS_KEY_ID;
    public static String SECRECT;
    public static String BUCKET;


    @Override
    public void afterPropertiesSet() throws Exception {
        EDNPOINT=endpoint;
        ACCESS_KEY_ID=accessKeyId;
        SECRECT=accessKeySecret;
        BUCKET=bucket;
    }
}
