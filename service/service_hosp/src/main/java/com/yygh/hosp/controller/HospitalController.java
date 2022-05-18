package com.yygh.hosp.controller;

import com.yygh.Result;
import com.yygh.hosp.service.HospitalService;
import com.yygh.model.hosp.Hospital;
import com.yygh.vo.hosp.HospitalQueryVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author 便利店狗砸ha
 *
 * 提供接口和cmn(数据字典一起调用)
 *
 */
@RestController
@RequestMapping("/admin/hosp/hospital")
//@CrossOrigin
public class HospitalController {

    @Autowired
    HospitalService hospitalService;

    /**
     * 条件分页联合查询结合数据字典表和医院信息表一起查询
     *
     * @param page            页码
     * @param limit           显示条数
     * @param hospitalQueryVo 条件
     * @return 结果集
     */
    @ApiOperation("条件分页查询")
    @GetMapping("list/{page}/{limit}")
    public Result listHosp(@PathVariable Integer page,
                           @PathVariable Integer limit,
                           HospitalQueryVo hospitalQueryVo) {

        //先查询在mongodb表中的数据(Page实现了Iterable接口)
        Page<Hospital> hospitals = hospitalService.selectHospPage(page, limit, hospitalQueryVo);

        List<Hospital> content = hospitals.getContent();
        long totalElements = hospitals.getTotalElements();

        return Result.ok(hospitals);
    }

    /**
     * 更新医院上线状态
     * @param id 医院的id
     * @param status 医院的状态
     * @return 返回成功
     */
    @ApiOperation(value = "更新医院上线状态")
    @GetMapping("updateHospStatus/{id}/{status}")
    public Result updateHospStatus(@PathVariable String id, @PathVariable Integer status) {

        hospitalService.updateStatus(id,status);
        return Result.ok();
    }

    /**
     * 医院详情信息
     * @param id 医院的信息
     * @return 返回医院的基本信息加上医院等级信息
     */
    @ApiOperation(value = "医院详情信息")
    @GetMapping("showHospDetail/{id}")
    public Result showHospDetail(@PathVariable String id) {

        Map<String, Object> map = hospitalService.getHospById(id);
        return Result.ok(map);
    }
}
