package com.seagox.oa.excel.controller;

import com.seagox.oa.annotation.SysLogPoint;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyJob;
import com.seagox.oa.excel.service.IJellyJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 任务调度
 */
@RestController
@RequestMapping("/job")
public class JellyJobController {

    @Autowired
    private IJellyJobService jobService;

    /**
     * 启动
     */
    @PostMapping("/startJob/{id}")
    @SysLogPoint("启动任务调度")
    public ResultData startJob(@PathVariable Long id) {
        jobService.startJob(id);
        return ResultData.success(null);
    }

    /**
     * 暂停
     */
    @PostMapping("/stopJob/{id}")
    @SysLogPoint("暂停任务调度")
    public ResultData stopJob(@PathVariable Long id) {
        jobService.stopJob(id);
        return ResultData.success(null);
    }

    /**
     * 分页查询
     *
     * @param pageNo    起始页
     * @param pageSize  每页大小
     * @param companyId 公司id
     */
    @GetMapping("/queryByPage")
    public ResultData queryByPage(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, Long companyId) {
        return jobService.queryByPage(pageNo, pageSize, companyId);
    }

    /**
     * 新增
     */
    @PostMapping("/insert")
    @SysLogPoint("新增任务调度")
    public ResultData insert(@Valid JellyJob job) {
        return jobService.insert(job);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @SysLogPoint("修改任务调度")
    public ResultData update(@Valid JellyJob job) {
        return jobService.update(job);
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    @SysLogPoint("删除任务调度")
    public ResultData delete(@PathVariable Long id) {
        return jobService.delete(id);
    }

}
