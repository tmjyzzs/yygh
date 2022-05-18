package com.yygh;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 便利店狗砸ha
 * cmn数据字典的Fegin客户端
 * 调用这个接口就可以调用注册到注册中心中的服务
 * 使用使用restful的请求进行调用
 * 内置了ribbon
 * 封装成为接口，因为客服端不止一个地方调用，可能有多个地方调用，方便我们的调用
 */
@Repository
@FeignClient(value = "service-cmn")
public interface DictFeignClient {
    /**
     *把从mongodb中的数据的省、市、医院等级传递到cmn字典中查询
     */
    @GetMapping(value = "/admin/cmn/dict/getName/{dictCode}/{value}",produces= MediaType.APPLICATION_JSON_UTF8_VALUE,consumes = "application/json;charset=UTF-8")
    public String getName(@PathVariable("dictCode") String dictCode,
                          @PathVariable("value") String value);

    /**
     *把从mongodb中的数据的省、市、医院等级传递到cmn字典中查询
     */
    @GetMapping(value = "/admin/cmn/dict/getName/{value}",produces=MediaType.APPLICATION_JSON_UTF8_VALUE,consumes = "application/json;charset=UTF-8")
    String getName(@PathVariable("value") String value);

    
}
