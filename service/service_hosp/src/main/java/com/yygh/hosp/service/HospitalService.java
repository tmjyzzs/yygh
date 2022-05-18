package com.yygh.hosp.service;




import com.yygh.model.hosp.Hospital;
import com.yygh.vo.hosp.HospitalQueryVo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface HospitalService {
    /**
     * 上传医院接口
     */
    void save(Map<String, Object> parapMap);

    /**
     * 查询医院接口
     */
    Hospital find(String hoscode);


    /**
     * 条件分页查询出医院的信息
     */
    Page<Hospital> selectHospPage(Integer page, Integer limit, HospitalQueryVo hospitalQueryVo);

    /**
     * 根据医院id进行更新医院的状态
     * @param id 医院id
     * @param status 医院状态 1 上线 0未上线
     */
    void updateStatus(String id, Integer status);


    Map<String, Object> getHospById(String id);

    String getHospName(String hoscode);

    /**
     * 更加医院的名称进行模糊查询
     * @param hosname 医院名称进行模糊查询
     * @return 返回医院的集合
     */
    List<Hospital> findByHosname(String hosname);

    Map<String, Object> item(String hoscode);

    Hospital getByHoscode(String hoscode);
}
