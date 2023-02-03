package com.seagull.oa.excel.controller;

import com.seagull.oa.annotation.SysLogPoint;
import com.seagull.oa.common.ResultData;
import com.seagull.oa.excel.entity.JellyBusinessField;
import com.seagull.oa.excel.service.IJellyBusinessFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 业务字段
 */
@RestController
@RequestMapping("/businessField")
public class JellyBusinessFieldController {

    @Autowired
    private IJellyBusinessFieldService businessFieldService;

    /**
     * 查询全部
     */
    @GetMapping("/queryAll")
    public ResultData queryAll(String tableName) {
        return businessFieldService.queryAll(tableName);
    }

    /**
     * 分页查询
     *
     * @param pageNo          起始页
     * @param pageSize        每页大小
     * @param businessTableId 业务表id
     */
    @GetMapping("/queryByPage")
    public ResultData queryByPage(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                  Long businessTableId, String name, String comment) {
        return businessFieldService.queryByPage(pageNo, pageSize, businessTableId, name, comment);
    }

    /**
     * 新增
     */
    @PostMapping("/insert")
    @SysLogPoint("新增业务字段")
    public ResultData insert(@Valid JellyBusinessField businessField) {
        return businessFieldService.insert(businessField);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @SysLogPoint("修改业务字段")
    public ResultData update(@Valid JellyBusinessField businessField) {
        return businessFieldService.update(businessField);
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    @SysLogPoint("删除业务字段")
    public ResultData delete(@PathVariable Long id) {
        return businessFieldService.delete(id);
    }

    /**
     * 查询全部
     */
    @GetMapping("/queryByTableId/{tableId}")
    public ResultData queryByTableId(@PathVariable("tableId") Long tableId) {
        return businessFieldService.queryByTableId(tableId);
    }

}
