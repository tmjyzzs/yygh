package com.yygh.hosp.controller;


import com.yygh.Result;
import com.yygh.hosp.service.DepartmentService;
import com.yygh.vo.hosp.DepartmentVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 便利店狗砸ha
 * 处理医院的科室信息
 */
@RestController
@RequestMapping("/admin/hosp/department")
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;

    /**
     * 根据医院编号，查询医院所有科室列表
     * @param hoscode 医院编号
     * @return 医院科室列表的信息
     */
    @ApiOperation(value = "查询医院所有科室列表")
    @GetMapping("getDeptList/{hoscode}")
    public Result getDeptList(@PathVariable String hoscode) {
        List<DepartmentVo> list = departmentService.findDeptTree(hoscode);
        return Result.ok(list);
    }
}
