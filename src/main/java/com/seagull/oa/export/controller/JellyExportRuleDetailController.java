package com.seagull.oa.export.controller;

import com.seagull.oa.annotation.SysLogPoint;
import com.seagull.oa.common.ResultData;
import com.seagull.oa.export.entity.JellyExportRuleDetail;
import com.seagull.oa.export.service.IJellyExportRuleDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 导入规则明细
 */
@RestController
@RequestMapping("/exportRuleDetail")
public class JellyExportRuleDetailController {

    @Autowired
    private IJellyExportRuleDetailService exportRuleDetailService;

    /**
     * 根据规则id查询
     */
    @GetMapping("/queryByRuleId")
    public ResultData queryByRuleId(Long ruleId) {
        return exportRuleDetailService.queryByRuleId(ruleId);
    }

    /**
     * 分页查询
     *
     * @param pageNo   起始页
     * @param pageSize 每页大小
     * @param ruleId   规则id
     */
    @GetMapping("/queryByPage")
    public ResultData queryByPage(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                  Long ruleId) {
        return exportRuleDetailService.queryByPage(pageNo, pageSize, ruleId);
    }

    /**
     * 新增
     */
    @PostMapping("/insert")
    @SysLogPoint("新增规则明细")
    public ResultData insert(@Valid JellyExportRuleDetail exportRuleDetail) {
        return exportRuleDetailService.insert(exportRuleDetail);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @SysLogPoint("修改规则明细")
    public ResultData update(@Valid JellyExportRuleDetail exportRuleDetail) {
        return exportRuleDetailService.update(exportRuleDetail);
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    @SysLogPoint("删除规则明细")
    public ResultData delete(@PathVariable Long id) {
        return exportRuleDetailService.delete(id);
    }

    /**
     * 根据规则id删除所有对应列
     */
    @PostMapping("/deleteByRuleId/{ruleId}")
    @SysLogPoint("删除规则明细")
    public ResultData deleteByRuleId(@PathVariable("ruleId") Long ruleId) {
        return exportRuleDetailService.deleteByRuleId(ruleId);
    }

}
