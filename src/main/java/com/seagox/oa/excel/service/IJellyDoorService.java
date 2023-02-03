package com.seagox.oa.excel.service;

import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyDoor;

import javax.servlet.http.HttpServletRequest;

public interface IJellyDoorService {

    /**
     * 分页查询
     */
    public ResultData queryByPage(Integer pageNo, Integer pageSize, Long companyId);

    /**
     * 添加
     */
    public ResultData insert(JellyDoor door);

    /**
     * 修改
     */
    public ResultData update(JellyDoor door);

    /**
     * 删除
     */
    public ResultData delete(Long id);

    /**
     * 详情
     */
    public ResultData queryById(Long id, Long userId);

    /**
     * 统计分析
     */
    public ResultData queryAnalysis(Long companyId, String userId);


    /**
     * 执行sql
     */
    public ResultData execute(HttpServletRequest request, Long userId, Long id, String name);

}
