package com.seagull.oa.excel.service;

import com.seagull.oa.common.ResultData;
import com.seagull.oa.excel.entity.JellyTemplateEngine;

public interface IJellyTemplateEngineService {

    /**
     * 查询所有
     *
     * @param companyId 公司id
     */
    public ResultData queryByCompanyId(Long companyId);

    /**
     * 添加
     */
    public ResultData insert(JellyTemplateEngine templateEngine);

    /**
     * 修改
     */
    public ResultData update(JellyTemplateEngine templateEngine);

    /**
     * 删除
     */
    public ResultData delete(Long id, String path);

    /**
     * 通过路径查询规则
     */
    public String queryByPath(String path);

}
