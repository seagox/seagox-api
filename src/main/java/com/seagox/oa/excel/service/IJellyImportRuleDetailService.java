package com.seagox.oa.excel.service;

import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyImportRuleDetail;

public interface IJellyImportRuleDetailService {

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
    public ResultData insert(JellyImportRuleDetail importRuleDetail);

    /**
     * 修改
     */
    public ResultData update(JellyImportRuleDetail importRuleDetail);

    /**
     * 删除
     */
    public ResultData delete(Long id);

    /**
     * 根据规则id删除所有对应列
     */
    public ResultData deleteByRuleId(Long id);
}
