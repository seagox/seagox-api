package com.seagox.oa.excel.controller;

import com.seagox.oa.annotation.SysLogPoint;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyFormDesign;
import com.seagox.oa.excel.service.IJellyFormDesignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 表单设计
 */
@RestController
@RequestMapping("/jellyFormDesign")
public class JellyFormDesignController {

    @Autowired
    private IJellyFormDesignService formDesignService;

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
        return formDesignService.queryByPage(pageNo, pageSize, companyId, name);
    }

    /**
     * 查询全部
     */
    @GetMapping("/queryAll")
    public ResultData queryAll(Long companyId) {
        return formDesignService.queryAll(companyId);
    }

    /**
     * 新增
     */
    @PostMapping("/insert")
    @SysLogPoint("新增表单设计")
    public ResultData insert(@Valid JellyFormDesign formDesign) {
        return formDesignService.insert(formDesign);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @SysLogPoint("修改表单设计")
    public ResultData update(@Valid JellyFormDesign formDesign) {
        return formDesignService.update(formDesign);
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    @SysLogPoint("删除表单设计")
    public ResultData delete(@PathVariable Long id) {
        return formDesignService.delete(id);
    }

    /**
     * 详情
     */
    @GetMapping("/queryById/{id}")
    public ResultData queryById(@PathVariable Long id) {
        return formDesignService.queryById(id);
    }

    /**
     * 查询业务表
     */
    @GetMapping("/queryBusinessTable/{id}")
    public ResultData queryBusinessTable(@PathVariable Long id) {
        return formDesignService.queryBusinessTable(id);
    }

    /**
     * 查询业务字段
     */
    @GetMapping("/queryBusinessField/{id}")
    public ResultData queryBusinessField(@PathVariable Long id) {
        return formDesignService.queryBusinessField(id);
    }

}
