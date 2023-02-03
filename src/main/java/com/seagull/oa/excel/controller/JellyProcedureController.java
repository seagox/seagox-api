package com.seagull.oa.excel.controller;

import com.seagull.oa.annotation.SysLogPoint;
import com.seagull.oa.common.ResultData;
import com.seagull.oa.excel.entity.JellyProcedure;
import com.seagull.oa.excel.service.IJellyProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 存储过程
 */
@RestController
@RequestMapping("/jellyProcedure")
public class JellyProcedureController {

    @Autowired
    private IJellyProcedureService procedureService;

    /**
     * 分页查询
     *
     * @param pageNo   起始页
     * @param pageSize 每页大小
     * @param name     名称
     * @param remark   备注
     */
    @GetMapping("/queryByPage")
    public ResultData queryByPage(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, Long companyId,
                                  String name, String remark) {
        return procedureService.queryByPage(pageNo, pageSize, companyId, name, remark);
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

    /**
     * 执行全部
     */
    @PostMapping("/executeAll/{year}")
    @SysLogPoint("执行全部存储过程")
    public ResultData execute(@PathVariable String year) {
        return procedureService.executeAll(year);
    }

}
