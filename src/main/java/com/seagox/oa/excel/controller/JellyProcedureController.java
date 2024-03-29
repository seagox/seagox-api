package com.seagox.oa.excel.controller;

import com.seagox.oa.annotation.SysLogPoint;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyProcedure;
import com.seagox.oa.excel.service.IJellyProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 存储过程
 */
@RestController
@RequestMapping("/procedure")
public class JellyProcedureController {

    @Autowired
    private IJellyProcedureService procedureService;

    /**
     * 查询全部
     */
    @GetMapping("/queryAll")
    public ResultData queryAll(Long companyId, String name, String remark) {
        return procedureService.queryAll(companyId, name, remark);
    }

    /**
     * 新增
     */
    @PostMapping("/insert")
    @SysLogPoint("新增存储过程")
    public ResultData insert(@Valid JellyProcedure procedure) {
        return procedureService.insert(procedure);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @SysLogPoint("修改存储过程")
    public ResultData update(@Valid JellyProcedure procedure) {
        return procedureService.update(procedure);
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    @SysLogPoint("删除存储过程")
    public ResultData delete(@PathVariable Long id) {
        return procedureService.delete(id);
    }

    /**
     * 执行
     */
    @PostMapping("/execute/{id}")
    @SysLogPoint("执行存储过程")
    public ResultData execute(@PathVariable Long id, HttpServletRequest request) {
        return procedureService.execute(id, request);
    }

}
