package com.seagull.oa.excel.service;

import com.seagull.oa.common.ResultData;
import com.seagull.oa.excel.entity.JellyJob;

public interface IJellyJobService {

    /**
     * 启动任务
     *
     * @param id
     */
    public void startJob(Long id);

    /**
     * 暂停任务
     *
     * @param id
     */
    public void stopJob(Long id);


    /**
     * 分页查询
     */
    public ResultData queryByPage(Integer pageNo, Integer pageSize, Long companyId);

    /**
     * 添加
     */
    public ResultData insert(JellyJob job);

    /**
     * 修改
     */
    public ResultData update(JellyJob job);

    /**
     * 删除
     */
    public ResultData delete(Long id);

}
