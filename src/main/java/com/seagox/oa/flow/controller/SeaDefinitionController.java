package com.seagox.oa.flow.controller;

import com.seagox.oa.annotation.SysLogPoint;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.flow.entity.SeaDefinition;
import com.seagox.oa.flow.service.ISeaDefinitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 流程定义
 */
@RestController
@RequestMapping("/seaDefinition")
public class SeaDefinitionController {

    @Autowired
    private ISeaDefinitionService seaDefinitionService;

    /**
     * 分页查询
     *
     * @param pageNo    起始页
     * @param pageSize  每页大小
     * @param companyId 公司id
     * @param name      名称
     */
    @GetMapping("/queryByPage")
    public ResultData queryByPage(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, Long companyId, String name) {
        return seaDefinitionService.queryByPage(pageNo, pageSize, companyId, name);
    }

    /**
     * 查询全部
     */
    @GetMapping("/queryAll")
    public ResultData queryAll(Long companyId) {
        return seaDefinitionService.queryAll(companyId);
    }

    /**
     * 新增
     */
    @PostMapping("/insert")
    @SysLogPoint("新增表单设计")
    public ResultData insert(@Valid SeaDefinition seaDefinition) {
        return seaDefinitionService.insert(seaDefinition);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @SysLogPoint("修改流程定义")
    public ResultData update(@Valid SeaDefinition seaDefinition) {
        return seaDefinitionService.update(seaDefinition);
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    @SysLogPoint("删除流程定义")
    public ResultData delete(@PathVariable Long id) {
        return seaDefinitionService.delete(id);
    }

    /**
     * 详情
     */
    @GetMapping("/queryById/{id}")
    public ResultData queryById(@PathVariable Long id) {
        return seaDefinitionService.queryById(id);
    }

    /**
     * 查询业务表
     */
    @GetMapping("/queryBusinessTable/{id}")
    public ResultData queryBusinessTable(@PathVariable Long id) {
        return seaDefinitionService.queryBusinessTable(id);
    }

    /**
     * 查询业务字段
     */
    @GetMapping("/queryBusinessField/{id}")
    public ResultData queryBusinessField(@PathVariable Long id) {
        return seaDefinitionService.queryBusinessField(id);
    }

}
