package com.yygh.user.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * @author 便利店狗砸ha
 * 配置类扫描mapper文件
 */
@Configuration
@MapperScan("com.yygh.user.mapper")
public class UserConfig {


    @Bean
    public InternalResourceViewResolver viewResolver(){
        return new InternalResourceViewResolver();
    }


}
