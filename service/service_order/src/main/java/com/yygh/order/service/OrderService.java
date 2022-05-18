package com.yygh.order.service;

import com.yygh.vo.order.OrderCountQueryVo;

import java.util.Map;

public interface OrderService {
    Long saveOrder(String scheduleId, Long patientId);

    void patientTips();


    /**
     * 查询订单数据
     */
    Map<String,Object> getCountMap(OrderCountQueryVo orderCountQueryVo );
}
