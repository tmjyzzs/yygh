package com.yygh.order.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;



@Configuration
@MapperScan("com.yygh.order.mapper")
public class OrderConfig {

}
