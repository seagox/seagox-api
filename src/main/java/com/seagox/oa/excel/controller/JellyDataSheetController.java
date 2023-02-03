package com.seagox.oa.excel.controller;

import com.seagox.oa.annotation.SysLogPoint;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyDataSheet;
import com.seagox.oa.excel.service.IJellyDataSheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 数据表
 */
@RestController
@RequestMapping("/dataSheet")
public class JellyDataSheetController {

    @Autowired
    private IJellyDataSheetService dataSheetService;

    /**
     * 查询全部
     */
    @GetMapping("/queryAll")
    public ResultData queryAll(Long formId) {
        return dataSheetService.queryAll(formId);
    }

    /**
     * 新增
     */
    @PostMapping("/insert")
    @SysLogPoint("新增数据表")
    public ResultData insert(@Valid JellyDataSheet dataSheet) {
        return dataSheetService.insert(dataSheet);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @SysLogPoint("修改数据表")
    public ResultData update(@Valid JellyDataSheet dataSheet) {
        return dataSheetService.update(dataSheet);
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    @SysLogPoint("删除数据表")
    public ResultData delete(@PathVariable Long id) {
        return dataSheetService.delete(id);
    }

}
