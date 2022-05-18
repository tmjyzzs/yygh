package com.yygh.hosp.service;

import com.yygh.model.hosp.Department;
import com.yygh.vo.hosp.DepartmentQueryVo;
import com.yygh.vo.hosp.DepartmentVo;
import org.springframework.data.domain.Page;


import java.util.List;
import java.util.Map;

public interface DepartmentService {
    /**
     * 上传医院接口
     *
     * @param paramMap 医院模块传递的数据
     */
    void save(Map<String, Object> paramMap);


    /**
     * @param page              分页页数
     * @param limit             一页几条数据
     * @param departmentQueryVo 分页查询的条件(标准写法)
     * @return 返回查询到的Department对象
     */
    Page<Department> findPageDepartment(int page, int limit,
                                        DepartmentQueryVo departmentQueryVo);

    void delDepartment(String hosCode, String depCode);

    /**
     * 根据医院的信息对科室进行处理
     * @param hoscode 医院的编号
     * @return 科室信息
     */
    List<DepartmentVo> findDeptTree(String hoscode);

    String getDepName(String hoscode, String depcode);

    Department getDepartment(String hoscode, String depcode);
}


