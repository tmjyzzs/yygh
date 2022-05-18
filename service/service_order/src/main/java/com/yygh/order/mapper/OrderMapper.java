package com.yygh.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yygh.model.order.OrderInfo;
import com.yygh.vo.order.OrderCountQueryVo;
import com.yygh.vo.order.OrderCountVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface OrderMapper extends BaseMapper<OrderInfo> {

    /**
     * 结合sql语句对每日的订单进行查询，返回前端图表显示
     * @param orderCountQueryVo 条件查询
     * @return 查询结果
     */
    List<OrderCountVo> selectOrderCount(@Param("vo") OrderCountQueryVo orderCountQueryVo);
}
