package com.seagox.oa.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.seagox.oa.excel.entity.JellyJob;
import com.seagox.oa.excel.mapper.JellyJobMapper;
import com.seagox.oa.util.SchedulerUtils;
import com.seagox.oa.excel.entity.JellyMetaFunction;
import com.seagox.oa.excel.mapper.JellyMetaFunctionMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StartupRunner implements CommandLineRunner {

	@Autowired
    private JellyJobMapper jobMapper;

    @Autowired
    private SchedulerUtils schedulerUtils;
    
    @Autowired
    private JellyMetaFunctionMapper metaFunctionMapper;

    @Override
    public void run(String... args) throws Exception {
        //任务调度
        LambdaQueryWrapper<JellyJob> qw = new LambdaQueryWrapper<>();
        qw.eq(JellyJob::getStatus, 1);
        List<JellyJob> jobList = jobMapper.selectList(qw);
        for (int i = 0; i < jobList.size(); i++) {
            JellyJob job = jobList.get(i);
            JellyMetaFunction metaFunction = metaFunctionMapper.selectById(job.getRuleId());
            schedulerUtils.start(String.valueOf(job.getId()), "seagull", job.getCron(), metaFunction.getScript());
        }
    }
}
