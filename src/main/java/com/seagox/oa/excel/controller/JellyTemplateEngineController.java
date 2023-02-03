package com.seagox.oa.excel.controller;

import com.seagox.oa.annotation.SysLogPoint;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyTemplateEngine;
import com.seagox.oa.excel.service.IJellyTemplateEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 模版引擎
 */
@RestController
@RequestMapping("/templateEngine")
public class JellyTemplateEngineController {

    @Autowired
    private IJellyTemplateEngineService templateEngineService;

    /**
     * 查询所有
     *
     * @param companyId 公司id
     */
    @GetMapping("/queryByCompanyId")
    public ResultData queryByCompanyId(Long companyId) {
        return templateEngineService.queryByCompanyId(companyId);
    }

    /**
     * 新增
     */
    @PostMapping("/insert")
    @SysLogPoint("新增模版引擎")
    public ResultData insert(@Valid JellyTemplateEngine templateEngine) {
        return templateEngineService.insert(templateEngine);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @SysLogPoint("修改模版引擎")
    public ResultData update(@Valid JellyTemplateEngine templateEngine) {
        return templateEngineService.update(templateEngine);
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    @SysLogPoint("删除模版引擎")
    public ResultData delete(@PathVariable Long id, String path) {
        return templateEngineService.delete(id, path);
    }

}
