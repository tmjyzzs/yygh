package com.yygh.cmn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yygh.model.cmn.Dict;


import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface DicService extends IService<Dict> {
    /**
     * 查找数据字典的子节点
     */
    List<Dict> findChildData(Long childId);

    /**
     * 判断是否有子节点
     */
    boolean idChild(Long id);

    /**
     * 到处数据字典
     */
    void exportDictData(HttpServletResponse response);

    String getDictName(String dictCode, String value);


    List<Dict> findByDictCode(String dictCode);

    List<Dict> findChlidData(Long id);
}
