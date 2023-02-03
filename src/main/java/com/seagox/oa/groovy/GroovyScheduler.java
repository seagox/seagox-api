package com.seagox.oa.groovy;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Service;


@Service
public class GroovyScheduler implements Job {


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

    }

}
