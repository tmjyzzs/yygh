package com.yygh.cmn.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.yygh.cmn.mapper.DictMapper;
import com.yygh.cmn.service.DicService;

import com.yygh.model.cmn.Dict;
import com.yygh.vo.cmn.DictEeVo;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DicServiceImpl extends ServiceImpl<DictMapper, Dict> implements DicService {

    /**
     * 在service层完成查询
     *
     * @param childId
     * @return
     */
    @Override
    @Cacheable(value = "dict", keyGenerator = "keyGenerator")
    public List<Dict> findChildData(Long childId) {
        QueryWrapper<Dict> wr = new QueryWrapper<>();
        QueryWrapper<Dict> parentId = wr.eq("parent_id", childId);
        List<Dict> dicts = baseMapper.selectList(parentId);
        for (Dict dict : dicts) {
            if (idChild(dict.getId())) {
                dict.setHasChildren(true);
            }
        }
        return dicts;

    }

    /**
     * 判断是否具有子节点
     *
     * @param id
     * @return
     */
    @Override
    public boolean idChild(Long id) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        QueryWrapper<Dict> parentId = wrapper.eq("parent_id", id);
        Integer integer = baseMapper.selectCount(parentId);
        return integer > 0;
    }

    @Override
    public void exportDictData(HttpServletResponse response) {
        //使用EasyExcel、response
        response.setCharacterEncoding("utf-8");
        String fileName = "dict";
        //设置响应头控制浏览器以下载的形式打开文件
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

        //从数据库中拿到数据字典
        List<Dict> dicts = baseMapper.selectList(null);
        //把数据字典转化成vo(数据库中太多信息里没必要全部让用户下载)
        List<DictEeVo> dictEeVos = new ArrayList<>();
        for (Dict dict : dicts) {
            DictEeVo dictEeVo = new DictEeVo();
            BeanUtils.copyProperties(dict, dictEeVo);
            dictEeVos.add(dictEeVo);
        }
        try {
            EasyExcel.write(response.getOutputStream(), DictEeVo.class).sheet("dict").doWrite(dictEeVos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getDictName(String dictCode, String value) {
        //如果dictCode为空，直接根据value查询
        if(StringUtils.isEmpty(dictCode)) {
            //直接根据value查询
            QueryWrapper<Dict> wrapper = new QueryWrapper<>();
            wrapper.eq("value",value);
            Dict dict = baseMapper.selectOne(wrapper);
            return dict.getName();
        } else {//如果dictCode不为空，根据dictCode和value查询
            //根据dictcode查询dict对象，得到dict的id值
            Dict codeDict = this.getDictByDictCode(dictCode);
            Long parent_id = codeDict.getId();
            //根据parent_id和value进行查询
            Dict finalDict = baseMapper.selectOne(new QueryWrapper<Dict>()
                    .eq("parent_id", parent_id)
                    .eq("value", value));
            return finalDict.getName();
        }
    }

    @Override
    public List<Dict> findByDictCode(String dictCode) {
        //根据dictcode获取对应id
        Dict dict = this.getDictByDictCode(dictCode);
        //根据id获取子节点
        List<Dict> chlidData = this.findChlidData(dict.getId());
        return chlidData;
    }

    @Override
    public List<Dict> findChlidData(Long id) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        List<Dict> dictList = baseMapper.selectList(wrapper);
        //向list集合每个dict对象中设置hasChildren
        for (Dict dict:dictList) {
            Long dictId = dict.getId();
            boolean isChild = this.isChildren(dictId);
            dict.setHasChildren(isChild);
        }
        return dictList;
    }

    private boolean isChildren(Long id) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        Integer count = baseMapper.selectCount(wrapper);
        // 0>0    1>0
        return count>0;
    }

    private Dict getDictByDictCode(String dictCode) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("dict_code",dictCode);
        Dict codeDict = baseMapper.selectOne(wrapper);
        return codeDict;
    }

}
