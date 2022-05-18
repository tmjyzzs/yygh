package com.yygh.cmn.controller;


import com.yygh.Result;

import com.yygh.cmn.service.DicService;

import com.yygh.model.cmn.Dict;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(description = "数据字典接口")
@RestController
@RequestMapping("/admin/cmn/dict")
//@CrossOrigin
public class DictController {


    @Autowired
    DicService dicService;

    /**
     * 完成数据字典的查询
     */
    @ApiOperation(value = "根据数据id查询子数据列表")
    @GetMapping("findChildData/{id}")
    public Result findChildData(@PathVariable Long id) {
        List<Dict> list = dicService.findChlidData(id);
        return Result.ok(list);
    }

    /**
     * w完成数据字典的到出
     */
    @GetMapping("exportData")
    public void exportDict(HttpServletResponse response) {
        dicService.exportDictData(response);
    }

    /**
     * 根据dictCode获取下级节点
     */
    @ApiOperation(value = "根据dictCode获取下级节点")
    @GetMapping("findByDictCode/{dictCode}")
    public Result findByDictCode(@PathVariable String dictCode) {
        List<Dict> list = dicService.findByDictCode(dictCode);
        return Result.ok(list);
    }


    /**
     * 根据dictCode和value查询
     */
    @GetMapping("getName/{dictCode}/{value}")
    public String getName(@PathVariable String dictCode,
                          @PathVariable String value) {
        String dictName = dicService.getDictName(dictCode,value);
        return dictName;
    }

    /**
     *根据value查询
     */

    @GetMapping("getName/{value}")
    public String getName(@PathVariable String value) {
        String dictName = dicService.getDictName("",value);
        return dictName;
    }

}
