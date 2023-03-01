package com.seagox.oa.excel.controller;

import com.seagox.oa.annotation.SysLogPoint;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellySvg;
import com.seagox.oa.excel.service.IJellySvgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * svg数据
 */
@RestController
@RequestMapping("jellySvg")
public class JellySvgController {
    
    @Autowired
    private IJellySvgService jellySvgService;

    /**
     * 分页查询
     *
     * @param pageNo   起始页
     * @param pageSize 每页大小
     * @param name     名称
     */
    @GetMapping("/queryByPage")
    public ResultData queryByPage(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, String name) {
        return jellySvgService.queryByPage(pageNo, pageSize, name);
    }

    /**
     * 查询通过id
     */
    @GetMapping("/queryById/{id}")
    public ResultData queryById(@PathVariable Long id) {
        return jellySvgService.queryById(id);
    }

    /**
     * 新增
     */
    @PostMapping("/insert")
    @SysLogPoint("新增图标")
    public ResultData insert(@Valid JellySvg jellySvg) {
        return jellySvgService.insert(jellySvg);
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    @SysLogPoint("删除图标")
    public ResultData delete(@PathVariable Long id) {
        return jellySvgService.delete(id);
    }
    
}
