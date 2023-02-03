package com.seagox.oa.excel.controller;

import com.seagox.oa.annotation.SysLogPoint;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyReport;
import com.seagox.oa.excel.service.IJellyReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 报表管理
 */
@RestController
@RequestMapping("/jellyReport")
public class JellyReportController {

    @Autowired
    private IJellyReportService reportService;

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
        return reportService.queryByPage(pageNo, pageSize, companyId, name);
    }

    /**
     * 新增
     */
    @PostMapping("/insert")
    @SysLogPoint("新增报表")
    public ResultData insert(@Valid JellyReport report) {
        return reportService.insert(report);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @SysLogPoint("修改报表")
    public ResultData update(@Valid JellyReport report) {
        return reportService.update(report);
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    @SysLogPoint("删除报表")
    public ResultData delete(@PathVariable Long id) {
        return reportService.delete(id);
    }

    /**
     * 详情
     */
    @GetMapping("/queryById/{id}")
    public ResultData queryById(@PathVariable Long id, Long userId) {
        return reportService.queryById(userId, id);
    }

    /**
     * 查询全部通过公司id
     */
    @GetMapping("/queryByCompanyId")
    public ResultData queryByCompanyId(Long companyId) {
        return reportService.queryByCompanyId(companyId);
    }

    /**
     * 查询列表数据
     *
     * @param userId 用户id
     * @param id     报表id
     * @param search 搜索条件
     */
    @PostMapping("/queryListById")
    public ResultData queryListById(Long companyId, Long userId, Long id, String search) {
        return reportService.queryListById(companyId, userId, id, search);
    }

    /**
     * 列表导出
     */
    @PostMapping("/export")
    public void export(HttpServletRequest request, HttpServletResponse response) {
        reportService.export(request, response);
    }

}
