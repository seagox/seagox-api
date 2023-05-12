package com.seagox.oa.excel.controller;

import com.seagox.oa.annotation.SysLogPoint;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyBusinessTable;
import com.seagox.oa.excel.service.IJellyBusinessTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 业务表
 */
@RestController
@RequestMapping("/businessTable")
public class JellyBusinessTableController {

    @Autowired
    private IJellyBusinessTableService businessTableService;

    /**
     * 查询全部
     */
    @GetMapping("/queryAll")
    public ResultData queryAll(Long companyId, String name, String remark) {
        return businessTableService.queryAll(companyId, name, remark);
    }

    /**
     * 新增
     */
    @PostMapping("/insert")
    @SysLogPoint("新增业务表")
    public ResultData insert(@Valid JellyBusinessTable businessTable) {
        return businessTableService.insert(businessTable);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @SysLogPoint("修改业务表")
    public ResultData update(@Valid JellyBusinessTable businessTable) {
        return businessTableService.update(businessTable);
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    @SysLogPoint("删除业务表")
    public ResultData delete(@PathVariable Long id) {
        return businessTableService.delete(id);
    }

    /**
     * 查询通过id
     */
    @GetMapping("/queryById/{id}")
    public ResultData queryById(@PathVariable Long id) {
        return businessTableService.queryById(id);
    }

    /**
     * 查询通过表名
     */
    @PostMapping("/queryByTableIds")
    public ResultData queryByTableIds(String tableIds) {
        return businessTableService.queryByTableIds(tableIds);
    }

    /**
     * 复制表
     */
    @PostMapping("/copyTable/{id}")
    public ResultData copyTable(@PathVariable Long id) {
        return businessTableService.copyTable(id);
    }

    /**
     * 根据类型查询所有表
     */
    @PostMapping("/queryByType/{type}")
    public ResultData queryByType(@PathVariable("type") String type) {
        return businessTableService.queryByType(type);
    }

    /**
     * 根据级联数据
     */
    @GetMapping("/queryCascader/{id}")
    public ResultData queryCascader(@PathVariable("id") Long id, String rule) {
        return businessTableService.queryCascader(id, rule);
    }
    
    /**
	 * 数据模型接口
	 */
    @GetMapping("/queryModel")
    public ResultData queryModel(Long companyId) {
        return businessTableService.queryModel(companyId);
    }

}
