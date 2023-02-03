package com.seagox.oa.excel.controller;

import com.seagox.oa.annotation.SysLogPoint;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyBusinessRule;
import com.seagox.oa.excel.service.IJellyBusinessRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 业务规则
 */
@RestController
@RequestMapping("/businessRule")
public class JellyBusinessRuleController {

    @Autowired
    private IJellyBusinessRuleService businessRuleService;

    /**
     * 分页查询
     *
     * @param pageNo    起始页
     * @param pageSize  每页大小
     * @param companyId 公司id
     */
    @GetMapping("/queryByPage")
    public ResultData queryByPage(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, Long companyId) {
        return businessRuleService.queryByPage(pageNo, pageSize, companyId);
    }

    /**
     * 查询所有
     *
     * @param companyId 公司id
     */
    @GetMapping("/queryByCompanyId")
    public ResultData queryByCompanyId(Long companyId) {
        return businessRuleService.queryByCompanyId(companyId);
    }

    /**
     * 新增
     */
    @PostMapping("/insert")
    @SysLogPoint("新增业务规则")
    public ResultData insert(@Valid JellyBusinessRule businessRule) {
        return businessRuleService.insert(businessRule);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @SysLogPoint("修改业务规则")
    public ResultData update(@Valid JellyBusinessRule businessRule) {
        return businessRuleService.update(businessRule);
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    @SysLogPoint("删除业务规则")
    public ResultData delete(@PathVariable Long id) {
        return businessRuleService.delete(id);
    }

}
