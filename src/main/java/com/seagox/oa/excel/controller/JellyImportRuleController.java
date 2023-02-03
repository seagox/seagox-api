package com.seagox.oa.excel.controller;

import com.seagox.oa.annotation.SysLogPoint;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyImportRule;
import com.seagox.oa.excel.service.IJellyImportRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 导入规则
 */
@RestController
@RequestMapping("/importRule")
public class JellyImportRuleController {

    @Autowired
    private IJellyImportRuleService importRuleService;

    /**
     * 查询全部
     */
    @GetMapping("/queryAll")
    public ResultData queryAll(Long companyId) {
        return importRuleService.queryAll(companyId);
    }

    /**
     * 新增
     */
    @PostMapping("/insert")
    @SysLogPoint("新增规则")
    public ResultData insert(@Valid JellyImportRule importRule) {
        return importRuleService.insert(importRule);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @SysLogPoint("修改规则")
    public ResultData update(@Valid JellyImportRule importRule) {
        return importRuleService.update(importRule);
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    @SysLogPoint("删除规则")
    public ResultData delete(@PathVariable Long id) {
        return importRuleService.delete(id);
    }

    /**
     * 复制规则
     */
    @PostMapping("/copy/{id}")
    public ResultData copy(@PathVariable Long id) {
        return importRuleService.copy(id);
    }

}
