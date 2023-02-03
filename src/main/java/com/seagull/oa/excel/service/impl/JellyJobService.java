package com.seagull.oa.excel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seagull.oa.common.ResultCode;
import com.seagull.oa.common.ResultData;
import com.seagull.oa.excel.entity.JellyJob;
import com.seagull.oa.excel.mapper.JellyJobMapper;
import com.seagull.oa.excel.service.IJellyJobService;
import com.seagull.oa.sys.entity.SysCompany;
import com.seagull.oa.sys.mapper.CompanyMapper;
import com.seagull.oa.util.SchedulerUtils;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class JellyJobService implements IJellyJobService {

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private JellyJobMapper jobMapper;

    /**
     * 任务调度
     */
    @Autowired
    private SchedulerUtils schedulerUtils;

    @Transactional
    @Override
    public void startJob(Long id) {
        JellyJob job = jobMapper.selectById(id);
        schedulerUtils.start(String.valueOf(id), "seagull", job.getCron(), job.getScript());
        job.setStatus(1);
        jobMapper.updateById(job);
    }

    @Transactional
    @Override
    public void stopJob(Long id) {
        JellyJob job = jobMapper.selectById(id);
        schedulerUtils.delete(String.valueOf(id), "seagull");
        job.setStatus(0);
        jobMapper.updateById(job);
    }

    @Override
    public ResultData queryByPage(Integer pageNo, Integer pageSize, Long companyId) {
        SysCompany company = companyMapper.selectById(companyId);
        PageHelper.startPage(pageNo, pageSize);
        List<Map<String, Object>> list = jobMapper.queryByCode(company.getCode().substring(0, 4));
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        return ResultData.success(pageInfo);
    }

    @Override
    public ResultData insert(JellyJob job) {
        boolean cronExpressionFlag = CronExpression.isValidExpression(job.getCron());
        if (!cronExpressionFlag) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "表达式错误");
        }
        jobMapper.insert(job);
        return ResultData.success(null);
    }

    @Transactional
    @Override
    public ResultData update(JellyJob job) {
        boolean cronExpressionFlag = CronExpression.isValidExpression(job.getCron());
        if (!cronExpressionFlag) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "表达式错误");
        }
        JellyJob originalJob = jobMapper.selectById(job.getId());
        if (!job.getCron().equals(originalJob.getCron())
                || !job.getScript().equals(originalJob.getScript())) {
            schedulerUtils.delete(String.valueOf(job.getId()), "seagull");
            schedulerUtils.start(String.valueOf(job.getId()), "seagull", job.getCron(), job.getScript());
        }
        jobMapper.updateById(job);
        return ResultData.success(null);
    }

    @Transactional
    @Override
    public ResultData delete(Long id) {
        schedulerUtils.delete(String.valueOf(id), "seagull");
        jobMapper.deleteById(id);
        return ResultData.success(null);
    }

}
