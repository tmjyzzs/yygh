package com.yygh.user.client;


import com.yygh.model.user.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 便利店狗砸ha
 */
@FeignClient(value = "service-user")
@Repository
public interface PatientFeignClient {
    /**
     * 根据用户id获取用户
     */
    @GetMapping("/api/user/patient/inner/get/{id}")
    public Patient getPatientOrder(@PathVariable("id") Long id);
}
