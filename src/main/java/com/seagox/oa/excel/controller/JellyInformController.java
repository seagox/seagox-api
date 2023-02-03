package com.seagox.oa.excel.controller;

import com.seagox.oa.annotation.SysLogPoint;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyInform;
import com.seagox.oa.excel.service.IJellyInformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 报告模板
 */
@RestController
@RequestMapping("/jellyInform")
public class JellyInformController {

    @Autowired
    private IJellyInformService informService;

    /**
     * 分页查询
     *
     * @param pageNo   起始页
     * @param pageSize 每页大小
     * @param name     名称
     */
    @GetMapping("/queryByPage")
    public ResultData queryByPage(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, Long companyId,
                                  String name) {
        return informService.queryByPage(pageNo, pageSize, companyId, name);
    }


    /**
     * 新增
     */
    @PostMapping("/insert")
    @SysLogPoint("新增报告模板")
    public ResultData insert(@Valid JellyInform inform) {
        return informService.insert(inform);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @SysLogPoint("修改报告模板")
    public ResultData update(@Valid JellyInform inform) {
        return informService.update(inform);
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    @SysLogPoint("删除报告模板")
    public ResultData delete(@PathVariable Long id) {
        return informService.delete(id);
    }

    /**
     * 查询全部通过公司id
     */
    @GetMapping("/queryByCompanyId")
    public ResultData queryByCompanyId(Long companyId) {
        return informService.queryByCompanyId(companyId);
    }


    /**
     * 列表导出
     */
    @PostMapping("/export")
    public void export(HttpServletRequest request, HttpServletResponse response) {
        informService.export(request, response);
    }

}
