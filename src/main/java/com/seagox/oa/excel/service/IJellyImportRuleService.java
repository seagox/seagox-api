package com.seagox.oa.excel.service;

import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyImportRule;

public interface IJellyImportRuleService {

    /**
     * 查询全部
     */
    public ResultData queryAll(Long companyId);

    /**
     * 添加
     */
    public ResultData insert(JellyImportRule importRule);

    /**
     * 修改
     */
    public ResultData update(JellyImportRule importRule);

    /**
     * 删除
     */
    public ResultData delete(Long id);

    /**
     * 复制表
     */
    public ResultData copy(Long id);

}
