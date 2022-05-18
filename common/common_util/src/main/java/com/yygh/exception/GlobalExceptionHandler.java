package com.yygh.exception;

import com.yygh.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


//把这个处理异常的对象注入到ioc容器中
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
     //当发生异常的时候会转跳的这个方法进行返回
    //处理的异常类型
    @ExceptionHandler(Exception.class)
    //传入异常对象
    public Result gloException(Exception e){
        //打印信息到控制台
       e.printStackTrace();
       return Result.fail();
    }

    @ExceptionHandler(YyghException.class)
    public Result yyghExc(YyghException y){
        y.printStackTrace();
        return Result.fail();
    }
}
