package com.seagox.oa.excel.controller;

import com.seagox.oa.annotation.SysLogPoint;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyTableClassify;
import com.seagox.oa.excel.service.IJellyTableClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 表分类
 */
@RestController
@RequestMapping("/tableClassify")
public class JellyTableClassifyController {

    @Autowired
    private IJellyTableClassifyService tableClassifyService;

    /**
     * 查询所有
     */
    @GetMapping("/queryAll")
    public ResultData queryAll(Long companyId) {
        return tableClassifyService.queryAll(companyId);
    }

    /**
     * 新增
     */
    @PostMapping("/insert")
    @SysLogPoint("新增表头分类")
    public ResultData insert(@Valid JellyTableClassify tableClassify) {
        return tableClassifyService.insert(tableClassify);
    }

    /**
     * 更新
     */
    @PostMapping("/update")
    @SysLogPoint("更新表头分类")
    public ResultData update(@Valid JellyTableClassify tableClassify) {
        return tableClassifyService.update(tableClassify);
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    @SysLogPoint("删除表头分类")
    public ResultData delete(@PathVariable Long id) {
        return tableClassifyService.delete(id);
    }

    /**
     * 查询通过id
     */
    @GetMapping("/queryById/{id}")
    public ResultData queryById(@PathVariable Long id) {
        return tableClassifyService.queryById(id);
    }

}
