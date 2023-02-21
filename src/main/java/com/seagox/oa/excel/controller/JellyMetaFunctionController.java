package com.seagox.oa.excel.controller;

import com.seagox.oa.annotation.SysLogPoint;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyMetaFunction;
import com.seagox.oa.excel.service.IJellyMetaFunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 元函数
 */
@RestController
@RequestMapping("/metaFunction")
public class JellyMetaFunctionController {

    @Autowired
    private IJellyMetaFunctionService metaFunctionService;

    /**
     * 分页查询
     *
     * @param pageNo    起始页
     * @param pageSize  每页大小
     * @param companyId 公司id
     * @param name      名称
     * @param path      路径
     * @param type      类型
     */
    @GetMapping("/queryByPage")
    public ResultData queryByPage(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, Long companyId, String name, String path, Integer type) {
        return metaFunctionService.queryByPage(pageNo, pageSize, companyId, name, path, type);
    }
    
    /**
     * 查询所有
     *
     * @param companyId 公司id
     */
    @GetMapping("/queryByCompanyId")
    public ResultData queryByCompanyId(Long companyId) {
        return metaFunctionService.queryByCompanyId(companyId);
    }

    /**
     * 新增
     */
    @PostMapping("/insert")
    @SysLogPoint("新增元函数")
    public ResultData insert(@Valid JellyMetaFunction metaFunction) {
        return metaFunctionService.insert(metaFunction);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @SysLogPoint("修改元函数")
    public ResultData update(@Valid JellyMetaFunction metaFunction) {
        return metaFunctionService.update(metaFunction);
    }
    
    /**
     * 通过id获取
     */
    @GetMapping("/queryById/{id}")
    public ResultData queryById(@PathVariable Long id) {
        return metaFunctionService.queryById(id);
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    @SysLogPoint("删除元函数")
    public ResultData delete(@PathVariable Long id, String path) {
        return metaFunctionService.delete(id, path);
    }

}
