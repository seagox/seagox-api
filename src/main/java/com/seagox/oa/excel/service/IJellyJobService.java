package com.seagox.oa.excel.service;

import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyJob;

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
    public ResultData queryByPage(Integer pageNo, Integer pageSize, Long companyId, String name);

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
