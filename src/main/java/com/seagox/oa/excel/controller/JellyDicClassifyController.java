package com.seagox.oa.excel.controller;

import com.seagox.oa.annotation.SysLogPoint;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyDicClassify;
import com.seagox.oa.excel.service.IJellyDicClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 数据字典分类
 */
@RestController
@RequestMapping("/dictionaryClassify")
public class JellyDicClassifyController {

    @Autowired
    private IJellyDicClassifyService dicClassifyService;

    /**
     * 查询显示
     */
    @GetMapping("/queryDisplay")
    public ResultData queryDisplay(Long companyId) {
        return dicClassifyService.queryDisplay(companyId);
    }

    /**
     * 新增
     */
    @PostMapping("/insert")
    @SysLogPoint("新增字典分类")
    public ResultData insert(@Valid JellyDicClassify dicClassify) {
        return dicClassifyService.insert(dicClassify);
    }

    /**
     * 更新
     */
    @PostMapping("/update")
    @SysLogPoint("更新字典分类")
    public ResultData update(@Valid JellyDicClassify dicClassify) {
        return dicClassifyService.update(dicClassify);
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    @SysLogPoint("删除字典分类")
    public ResultData delete(@PathVariable Long id) {
        return dicClassifyService.delete(id);
    }

    /**
     * 查询通过id
     */
    @GetMapping("/queryById/{id}")
    public ResultData queryById(@PathVariable Long id) {
        return dicClassifyService.queryById(id);
    }

}
