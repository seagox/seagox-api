package com.seagull.oa.export.service;

import com.seagull.oa.common.ResultData;
import com.seagull.oa.export.entity.JellyExportRule;

public interface IJellyExportRuleService {

    /**
     * 查询全部
     */
    public ResultData queryAll(Long companyId);

    /**
     * 添加
     */
    public ResultData insert(JellyExportRule exportRule);

    /**
     * 修改
     */
    public ResultData update(JellyExportRule exportRule);

    /**
     * 删除
     */
    public ResultData delete(Long id);

    /**
     * 复制表
     */
    public ResultData copyRule(Long id);

}
