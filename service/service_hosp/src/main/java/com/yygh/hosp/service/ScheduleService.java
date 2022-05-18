package com.yygh.hosp.service;



import com.yygh.model.hosp.Schedule;
import com.yygh.vo.hosp.ScheduleOrderVo;
import com.yygh.vo.hosp.ScheduleQueryVo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * 排班相关数据
 * @author 便利店狗砸ha
 */
public interface ScheduleService {
    /**
     *上传排班接口
     */
    void save(Map<String, Object> paramMap);


    /**
     * 查询排班接口
     * @param page  页数
     * @param limit 条数
     * @param scheduleQueryVo 条件
     * @return 结构
     */
    Page<Schedule> findPageSchedule(int page, int limit, ScheduleQueryVo scheduleQueryVo);

    void remove(String hoscode, String hosScheduleId);

    /**
     * 根据医院编号和科室编号
     * @param page 当前页
     * @param limit 每页条数
     * @param hoscode 医院编号
     * @param depcode 科室编号
     * @return map集合的 医院信息
     */
    Map<String, Object> getRuleSchedule(long page, long limit, String hoscode, String depcode);

    List<Schedule> getDetailSchedule(String hoscode, String depcode, String workDate);

    Map<String, Object> getBookingScheduleRule(Integer page, Integer limit, String hoscode, String depcode);

    Schedule getScheduleId(String scheduleId);

    ScheduleOrderVo getScheduleOrderVo(String scheduleId);



    //更新排班数据 用于mp
    void update(Schedule schedule);
}



