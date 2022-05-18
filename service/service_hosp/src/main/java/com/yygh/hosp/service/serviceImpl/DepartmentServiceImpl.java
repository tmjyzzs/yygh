package com.yygh.hosp.service.serviceImpl;

import com.alibaba.fastjson.JSONObject;

import com.yygh.hosp.repository.DepartmentRepository;
import com.yygh.hosp.service.DepartmentService;


import com.yygh.model.hosp.Department;
import com.yygh.vo.hosp.DepartmentQueryVo;
import com.yygh.vo.hosp.DepartmentVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;

    @Override
    public void save(Map<String, Object> paramMap) {
        //把paramMap转换成为department对象需要上传对象数据
        String s = JSONObject.toJSONString(paramMap);
        Department department = JSONObject.parseObject(s, Department.class);
        //医院的编号、排班编号
        String hosCode = (String) paramMap.get("hoscode");
        String depCode = (String) paramMap.get("depcode");
        Department resultDepartment = departmentRepository.getDepartmentByHoscodeAndDepcode(hosCode, depCode);

        //判断department对象是否有数据
        if (resultDepartment == null) {
            //没有这个对象数据上传
            department.setCreateTime(new Date());
            department.setUpdateTime(new Date());
            department.setIsDeleted(0);
            departmentRepository.save(department);
        } else {
            //有这个对象则修改对象信息
            department.setUpdateTime(new Date());
            departmentRepository.save(department);
        }
    }

    @Override
    public Page<Department> findPageDepartment(int page, int limit,
                                               DepartmentQueryVo departmentQueryVo) {

        //开始查询
        Pageable pageable = PageRequest.of(page - 1, limit);
        // 创建Example对象
        Department department = new Department();
        BeanUtils.copyProperties(departmentQueryVo, department);
        department.setIsDeleted(0);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase(true);
        Example<Department> example = Example.of(department, matcher);

        Page<Department> all = departmentRepository.findAll(example, pageable);
        return all;
    }


    @Override
    public void delDepartment(String hosCode, String depCode) {

        Department department = departmentRepository.getDepartmentByHoscodeAndDepcode(hosCode, depCode);
        if (department != null) {
            //调用方法删除
            departmentRepository.deleteById(department.getId());
        }
    }

    @Override
    public List<DepartmentVo> findDeptTree(String hoscode) {
        //创建一个list集合存放医院的科室信息(有层级结构)
        List<DepartmentVo> departmentVos = new ArrayList<>();

        //设置mongodb查询条件
        Department department = new Department();
        department.setHoscode(hoscode);
        List<Department> departments = departmentRepository.findAll(Example.of(department));

        //根据大科室编号  bigcode 分组，获取每个大科室里面下级子科室
        Map<String, List<Department>> deparmentMap =
                departments.stream().collect(Collectors.groupingBy(Department::getBigcode));

        //遍历大科室
        for (Map.Entry<String,List<Department>> entry: deparmentMap.entrySet()) {
            String key = entry.getKey();
            List<Department> value = entry.getValue();

            DepartmentVo departmentVo = new DepartmentVo();
            departmentVo.setDepcode(key);
            departmentVo.setDepname(value.get(0).getDepname());

            //封装小科室信息到大科室的child中
            ArrayList<DepartmentVo> childDepartment = new ArrayList<>();
            for (Department child: value) {
                DepartmentVo departmentVo2 = new DepartmentVo();
                departmentVo2.setDepcode(child.getDepcode());
                departmentVo2.setDepname(child.getDepname());
                childDepartment.add(departmentVo2);
            }
            departmentVo.setChildren(childDepartment);
            departmentVos.add(departmentVo);
        }


        return departmentVos;
    }

    @Override
    public String getDepName(String hoscode, String depcode) {
        Department department = departmentRepository.getDepartmentByHoscodeAndDepcode(hoscode, depcode);
        if(department != null) {
            return department.getDepname();
        }
        return null;
    }

    @Override
    public Department getDepartment(String hoscode, String depcode) {
        return departmentRepository.getDepartmentByHoscodeAndDepcode(hoscode, depcode);
    }
}
