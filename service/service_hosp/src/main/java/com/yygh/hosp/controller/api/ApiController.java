package com.yygh.hosp.controller.api;

import com.helper.HttpRequestHelper;
import com.requestSwitch.RequestSwitch;
import com.signKeyVerify.SignKeyVerify;
import com.yygh.Result;

import com.yygh.ResultCodeEnum;
import com.yygh.exception.YyghException;


import com.yygh.hosp.service.DepartmentService;
import com.yygh.hosp.service.HospitalService;
import com.yygh.hosp.service.HospitalSetService;

import com.yygh.hosp.service.ScheduleService;
import com.yygh.model.hosp.Department;
import com.yygh.model.hosp.Hospital;
import com.yygh.model.hosp.Schedule;
import com.yygh.vo.hosp.DepartmentQueryVo;
import com.yygh.vo.hosp.ScheduleQueryVo;
import com.yygh_utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 根据医院模块的信息修改数据库
 */
@RestController
@RequestMapping("/api/hosp")
public class ApiController {


    @Autowired
    HospitalSetService hospitalSetService;

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    ScheduleService scheduleService;




    //上传医院接口
    @PostMapping("saveHospital")
    public Result saveHosp(HttpServletRequest request) {
        //获取医院传递过来的信息
        Map<String, String[]> parameterMap = request.getParameterMap();
        //为避免后面遍历，将map中的String[]转换成Object
        Map<String, Object> parampMap = HttpRequestHelper.switchMap(parameterMap);
        //核验签名是否一致
        //1.获取医院系统传递过来的签名
        String signKey = (String) parampMap.get("sign");
        //2.根据传递过来的医院编码，查询数据库，查询签名
        String hoscode = (String) parampMap.get("hoscode");
        String sign = hospitalSetService.getSignKey(hoscode);
        SignKeyVerify.signKeyVerify(sign, signKey);

        //图片数据采取base64工具类传输，在传输过程中“+”转换为了“ ”，因此我们要转换回来
        String logoData = (String) parampMap.get("logoData");
        logoData = logoData.replace(" ", "+");
        parampMap.put("logoData", logoData);

        //调用service的方法
        hospitalService.save(parampMap);
        return Result.ok();
    }

    /**
     * 查询医院的信息接口
     */
    @PostMapping("hospital/show")
    public Result getHospital(HttpServletRequest request) {
        //医院传递来一个post请求
        Map<String, Object> parMap = RequestSwitch.switchMap(request);

        //从map集合中拿到医院的hoscode
        String hoscode = (String) parMap.get("hoscode");

        //传递过来的签名秘钥
        String signKey = (String) parMap.get("sign");
        //从数据库拿到signKey
        String sign = hospitalSetService.getSignKey(hoscode);

        //进行签名比较
        SignKeyVerify.signKeyVerify(sign, signKey);

        Hospital hospital = hospitalService.find(hoscode);

        return Result.ok(hospital);
    }

    /**
     * 上传排班信息接口
     */
    @PostMapping("saveDepartment")
    public Result saveDepartment(HttpServletRequest request) {

        Map<String, Object> parMap = RequestSwitch.switchMap(request);
        //拿到医院编号、排班编号、医院加密签名
        String hosCode = (String) parMap.get("hoscode");
        String depCode = (String) parMap.get("depcode");
        String signKey = (String) parMap.get("sign");

        //签名校验
        String sign = hospitalSetService.getSignKey(hosCode);
        SignKeyVerify.signKeyVerify(sign, signKey);

        //进行医院排班数据的上传
        departmentService.save(parMap);

        return Result.ok();
    }

    /**
     * 分页查询医院科室接口
     */
    @PostMapping("department/list")
    public Result findDepartment(HttpServletRequest request) {
        //对象转换
        Map<String, Object> parMap = RequestSwitch.switchMap(request);
        //获取数据
        //医院编号
        String hoscode = (String) parMap.get("hoscode");
        //当前页 和 每页记录数
        int page = StringUtils.isEmpty(parMap.get("page")) ? 1 : Integer.parseInt((String) parMap.get("page"));
        int limit = StringUtils.isEmpty(parMap.get("limit")) ? 1 : Integer.parseInt((String) parMap.get("limit"));
        //TODO 签名校验

        DepartmentQueryVo departmentQueryVo = new DepartmentQueryVo();
        departmentQueryVo.setHoscode(hoscode);
        //调用service方法
        Page<Department> pageModel = departmentService.findPageDepartment(page,
                limit, departmentQueryVo);
        return Result.ok(pageModel);
    }

    /**
     * 删除科室接口
     *
     * @param request 医院系统请求数据
     * @return 是否成功
     */

    @PostMapping("department/remove")
    public Result removeDepartment(HttpServletRequest request) {

        Map<String, Object> parMap = RequestSwitch.switchMap(request);

        //根据医院编号和科室进行删除
        String hosCode = (String) parMap.get("hoscode");
        String depCode = (String) parMap.get("depcode");

        departmentService.delDepartment(hosCode, depCode);

        return Result.ok();
    }

    /**
     * 上传排班接口
     * @param request 前端传递过来的数据
     * @return 返回ok
     */
    @PostMapping("saveSchedule")
    public Result saveSchedule(HttpServletRequest request) {
        //获取传递过来科室信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        scheduleService.save(paramMap);

        return Result.ok();
    }
    //查询排班接口
    @PostMapping("schedule/list")
    public Result findSchedule(HttpServletRequest request) {
        //获取传递过来科室信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        //医院编号
        String hoscode = (String)paramMap.get("hoscode");

        //科室编号
        String depcode = (String)paramMap.get("depcode");
        //当前页 和 每页记录数
        int page = StringUtils.isEmpty(paramMap.get("page")) ? 1 : Integer.parseInt((String)paramMap.get("page"));
        int limit = StringUtils.isEmpty(paramMap.get("limit")) ? 1 : Integer.parseInt((String)paramMap.get("limit"));
        //TODO 签名校验

        ScheduleQueryVo scheduleQueryVo = new ScheduleQueryVo();
        scheduleQueryVo.setHoscode(hoscode);
        scheduleQueryVo.setDepcode(depcode);
        //调用service方法
        Page<Schedule> pageModel = scheduleService.findPageSchedule(page,limit,scheduleQueryVo);
        return Result.ok(pageModel);
    }

    //删除排班
    @PostMapping("schedule/remove")
    public Result remove(HttpServletRequest request) {
        //获取传递过来科室信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        //获取医院编号和排班编号
        String hoscode = (String)paramMap.get("hoscode");
        String hosScheduleId = (String)paramMap.get("hosScheduleId");

        //TODO 签名校验

        scheduleService.remove(hoscode,hosScheduleId);
        return Result.ok();
    }

}