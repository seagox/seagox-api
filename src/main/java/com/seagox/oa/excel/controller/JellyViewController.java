package com.seagox.oa.excel.controller;

import com.seagox.oa.annotation.SysLogPoint;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyView;
import com.seagox.oa.excel.service.IJellyViewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 视图表
 */
@RestController
@RequestMapping("/view")
public class JellyViewController {

    @Autowired
    private IJellyViewService viewService;

    /**
     * 查询全部
     */
    @GetMapping("/queryAll")
    public ResultData queryAll(Long companyId, String name, String remark) {
        return viewService.queryAll(companyId, name, remark);
    }

    /**
     * 新增
     */
    @PostMapping("/insert")
    @SysLogPoint("新增视图表")
    public ResultData insert(@Valid JellyView view) {
        return viewService.insert(view);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @SysLogPoint("修改视图表")
    public ResultData update(@Valid JellyView view) {
        return viewService.update(view);
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    @SysLogPoint("删除视图表")
    public ResultData delete(@PathVariable Long id) {
        return viewService.delete(id);
    }

    /**
     * 查询通过id
     */
    @GetMapping("/queryById/{id}")
    public ResultData queryById(@PathVariable Long id) {
        return viewService.queryById(id);
    }

}
