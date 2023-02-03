package com.seagull.oa.export.controller;

import com.seagull.oa.annotation.SysLogPoint;
import com.seagull.oa.common.ResultData;
import com.seagull.oa.export.entity.JellyExportRule;
import com.seagull.oa.export.service.IJellyExportRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 导入规则
 */
@RestController
@RequestMapping("/exportRule")
public class JellyExportRuleController {

    @Autowired
    private IJellyExportRuleService exportRuleService;

    /**
     * 查询全部
     */
    @GetMapping("/queryAll")
    public ResultData queryAll(Long companyId) {
        return exportRuleService.queryAll(companyId);
    }

    /**
     * 新增
     */
    @PostMapping("/insert")
    @SysLogPoint("新增规则")
    public ResultData insert(@Valid JellyExportRule exportRule) {
        return exportRuleService.insert(exportRule);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @SysLogPoint("修改规则")
    public ResultData update(@Valid JellyExportRule exportRule) {
        return exportRuleService.update(exportRule);
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    @SysLogPoint("删除规则")
    public ResultData delete(@PathVariable Long id) {
        return exportRuleService.delete(id);
    }

    /**
     * 复制规则
     */
    @PostMapping("/copyRule/{id}")
    public ResultData copyRule(@PathVariable Long id) {
        return exportRuleService.copyRule(id);
    }

}
