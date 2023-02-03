package com.seagull.oa.export.service;

import com.seagull.oa.common.ResultData;
import com.seagull.oa.export.entity.JellyExportRuleDetail;

public interface IJellyExportRuleDetailService {

    /**
     * 查询全部
     */
    public ResultData queryByRuleId(Long companyId);

    /**
     * 分页查询
     */
    public ResultData queryByPage(Integer pageNo, Integer pageSize, Long ruleId);

    /**
     * 添加
     */
    public ResultData insert(JellyExportRuleDetail exportRuleDetail);

    /**
     * 修改
     */
    public ResultData update(JellyExportRuleDetail exportRuleDetail);

    /**
     * 删除
     */
    public ResultData delete(Long id);

    /**
     * 根据规则id删除所有对应列
     */
    public ResultData deleteByRuleId(Long id);
}
