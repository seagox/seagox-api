package com.seagox.oa.excel.service;

import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyGauge;

import javax.servlet.http.HttpServletRequest;

public interface IJellyGaugeService {

    /**
     * 分页查询
     */
    public ResultData queryByPage(Integer pageNo, Integer pageSize, Long companyId, String name);

    /**
     * 添加
     */
    public ResultData insert(JellyGauge gauge);

    /**
     * 修改
     */
    public ResultData update(JellyGauge gauge);

    /**
     * 删除
     */
    public ResultData delete(Long id);

    /**
     * 详情
     */
    public ResultData queryById(Long id, Long userId);

    /**
     * 执行sql
     */
    public ResultData execute(HttpServletRequest request, Long userId, Long id, String key);

    /**
     * 查询全部通过公司id
     */
    public ResultData queryByCompanyId(Long companyId);
    
    /**
     * 图标sql
     */
    public ResultData chartSql(String tableName, String dimension, String metrics, String filterData);

}
