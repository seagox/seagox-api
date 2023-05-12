package com.seagox.oa.excel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seagox.oa.common.ResultCode;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyJob;
import com.seagox.oa.excel.entity.JellyMetaFunction;
import com.seagox.oa.excel.mapper.JellyJobMapper;
import com.seagox.oa.excel.mapper.JellyMetaFunctionMapper;
import com.seagox.oa.excel.service.IJellyJobService;
import com.seagox.oa.util.SchedulerUtils;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JellyJobService implements IJellyJobService {

    @Autowired
    private JellyJobMapper jobMapper;

    @Autowired
    private SchedulerUtils schedulerUtils;
    
    @Autowired
    private JellyMetaFunctionMapper metaFunctionMapper;

    @Transactional
    @Override
    public void startJob(Long id) {
        JellyJob job = jobMapper.selectById(id);
        JellyMetaFunction metaFunction = metaFunctionMapper.selectById(job.getRuleId());
        schedulerUtils.start(String.valueOf(id), "seagox", job.getCron(), metaFunction.getScript());
        job.setStatus(1);
        jobMapper.updateById(job);
    }

    @Transactional
    @Override
    public void stopJob(Long id) {
        JellyJob job = jobMapper.selectById(id);
        schedulerUtils.delete(String.valueOf(id), "seagox");
        job.setStatus(0);
        jobMapper.updateById(job);
    }

    @Override
    public ResultData queryByPage(Integer pageNo, Integer pageSize, Long companyId) {
        PageHelper.startPage(pageNo, pageSize);
        LambdaQueryWrapper<JellyJob> qw = new LambdaQueryWrapper<>();
        qw.eq(JellyJob::getCompanyId, companyId);
        List<JellyJob> list = jobMapper.selectList(qw);
        PageInfo<JellyJob> pageInfo = new PageInfo<>(list);
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
                || !job.getRuleId().equals(originalJob.getRuleId())) {
            schedulerUtils.delete(String.valueOf(job.getId()), "seagox");
            JellyMetaFunction metaFunction = metaFunctionMapper.selectById(job.getRuleId());
            schedulerUtils.start(String.valueOf(job.getId()), "seagox", job.getCron(), metaFunction.getScript());
        }
        jobMapper.updateById(job);
        return ResultData.success(null);
    }

    @Transactional
    @Override
    public ResultData delete(Long id) {
        schedulerUtils.delete(String.valueOf(id), "seagox");
        jobMapper.deleteById(id);
        return ResultData.success(null);
    }

}
