package com.seagull.oa.excel.controller;

import com.seagull.oa.common.ResultData;
import com.seagull.oa.excel.entity.JellyGauge;
import com.seagull.oa.excel.service.IJellyGaugeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 仪表盘
 */
@RestController
@RequestMapping("/gauge")
public class JellyGaugeController {

    @Autowired
    private IJellyGaugeService gaugeService;

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
        return gaugeService.queryByPage(pageNo, pageSize, companyId, name);
    }

    /**
     * 新增
     */
    @PostMapping("/insert")
    public ResultData insert(@Valid JellyGauge gauge) {
        return gaugeService.insert(gauge);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public ResultData update(@Valid JellyGauge gauge) {
        return gaugeService.update(gauge);
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    public ResultData delete(@PathVariable Long id) {
        return gaugeService.delete(id);
    }

    /**
     * 信息
     */
    @GetMapping("/queryById/{id}")
    public ResultData queryById(@PathVariable Long id, Long userId) {
        return gaugeService.queryById(id, userId);
    }

    /**
     * 执行sql
     */
    @PostMapping("/execute")
    public ResultData execute(HttpServletRequest request, Long userId, Long id, String name) {
        return gaugeService.execute(request, userId, id, name);
    }

    /**
     * 查询全部通过公司id
     */
    @GetMapping("/queryByCompanyId")
    public ResultData queryByCompanyId(Long companyId) {
        return gaugeService.queryByCompanyId(companyId);
    }

}
