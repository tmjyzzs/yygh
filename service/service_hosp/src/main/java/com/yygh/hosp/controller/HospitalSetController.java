package com.yygh.hosp.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.yygh.Result;


import com.yygh.hosp.service.serviceImpl.HospitalSetServiceImpl;

import com.yygh.model.hosp.HospitalSet;
import com.yygh.vo.hosp.HospitalSetQueryVo;
import com.yygh_utils.MD5;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Random;

@Api(tags = "医院信息管理")
@RestController
@RequestMapping("/admin/hosp/hospitalSet")
//@CrossOrigin
public class HospitalSetController {

    //admin/hosp/hospitalSet/findAll
    //把mapper对象注入进来
    @Autowired
    HospitalSetServiceImpl hospitalSetService;

    //查询表中的数据
    @ApiOperation("查找所以医院信息")
    @GetMapping("findAll")
    public Result findAll() {
        List<HospitalSet> list = hospitalSetService.list();
        return Result.<List<HospitalSet>>ok(list);
    }

    //逻辑删除
    @ApiOperation("更加id逻辑删除医院")
    @DeleteMapping("{id}")
    public Result logDelete(@PathVariable Long id) {
        boolean flag = hospitalSetService.removeById(id);
        if (flag) {
            return Result.fail();
        } else {
            return Result.ok();
        }

    }

    //条件分页查询
    //@CrossOrigin
    @PostMapping({"findPageHospSet/{current}/{limit}"})
    public Result findPageHosp(@PathVariable Long current,
                               @PathVariable Long limit,
                               @RequestBody(required = false) HospitalSetQueryVo hospitalSetQueryVo) {
        //使用mp封装的分页查询功能
        //使用Page对象完成分页查询的条件
        Page<HospitalSet> hospitalSetPage = new Page<>(current, limit);
        //QueryWrapper复杂的条件查询，模糊查询，eq查询
        QueryWrapper<HospitalSet> wrapper = new QueryWrapper<>();
        //把对象中的参数拿出来，进行复杂查询
        String hoscode = hospitalSetQueryVo.getHoscode();
        String hosname = hospitalSetQueryVo.getHosname();
        if (!StringUtils.isEmpty(hosname)) {
            wrapper.like("hosname", hospitalSetQueryVo.getHosname());
        }
        if (!StringUtils.isEmpty(hoscode)) {
            wrapper.eq("hoscode", hospitalSetQueryVo.getHoscode());
        }
        //hospitalServiceMp封装了分页查询
        //把复杂查询结果，分页条件传入
        Page<HospitalSet> page = hospitalSetService.page(hospitalSetPage, wrapper);
        return Result.ok(page);
    }

    //完成医院添加信息
    @PostMapping("saveHospitalSet")
    public Result saveHospitalSet(@RequestBody HospitalSet hospitalSet) {
        //设置状态 1 使用 0 不能使用
        hospitalSet.setStatus(1);
        hospitalSet.setIsDeleted(0);
        //签名秘钥
        Random random = new Random();
        hospitalSet.setSignKey(MD5.encrypt(System.currentTimeMillis() + "" + random.nextInt(1000)));
        //调用service
        boolean save = hospitalSetService.save(hospitalSet);
        if (save) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    //5 根据id获取医院设置
    @GetMapping("getHospSet/{id}")
    public Result getHospSet(@PathVariable Long id) {
//        try {
//            //模拟异常
//            int a = 1/0;
//        }catch (Exception e) {
//            throw new YyghException("失败",201);
//        }

        HospitalSet hospitalSet = hospitalSetService.getById(id);
        return Result.ok(hospitalSet);
    }

    //6 修改医院设置
    @PostMapping("updateHospitalSet")
    public Result updateHospitalSet(@RequestBody HospitalSet hospitalSet) {
        boolean flag = hospitalSetService.updateById(hospitalSet);
        if (flag) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    //7 批量删除医院设置
    @DeleteMapping("batchRemove")
    public Result batchRemoveHospitalSet(@RequestBody List<Long> idList) {
        hospitalSetService.removeByIds(idList);
        return Result.ok();
    }

    //8 医院设置锁定和解锁
    @PutMapping("lockHospitalSet/{id}/{status}")
    public Result lockHospitalSet(@PathVariable Long id,
                                  @PathVariable Integer status) {
        //根据id查询医院设置信息
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        //设置状态
        hospitalSet.setStatus(status);
        //调用方法
        hospitalSetService.updateById(hospitalSet);
        return Result.ok();
    }

    //根据id获取医院信息
    @GetMapping("getHosp/{id}")
    public Result selectHospById(@PathVariable Long id) {
        HospitalSet hosp = hospitalSetService.getById(id);
        return Result.ok(hosp);
    }

    //修改医院信息
    // @PostMapping("updateHosp")
    public Result updateHosp(@RequestBody HospitalSet hospitalSet) {
        boolean flag = hospitalSetService.updateById(hospitalSet);
        if (flag) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    //批量删除医院的信息
    @DeleteMapping("deleteHosp")
    public Result deleteHosp(@RequestBody List<Long> list) {
        boolean flag = hospitalSetService.removeByIds(list);
        if (flag) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    //医院设置锁定和解锁
    @PutMapping("lockHosp/{id}/{status}")
    public Result lockHosp(@PathVariable Long id,
                           @PathVariable Integer status) {
        HospitalSet hosp = hospitalSetService.getById(id);
        hosp.setStatus(status);
        hospitalSetService.updateById(hosp);
        return Result.ok();
    }

    //发送医院的签名秘钥
    @PutMapping("sendKey/{id}")
    public Result sendKey(@PathVariable Long id) {
        HospitalSet hosp = hospitalSetService.getById(id);
        String signKey = hosp.getSignKey();
        Integer status = hosp.getStatus();
        return Result.ok();

    }


}
