package com.seagull.oa.excel.service;

import com.seagull.oa.common.ResultData;
import com.seagull.oa.excel.entity.JellyBusinessRule;

public interface IJellyBusinessRuleService {

    /**
     * 分页查询
     */
    public ResultData queryByPage(Integer pageNo, Integer pageSize, Long companyId);

    /**
     * 查询所有
     *
     * @param companyId 公司id
     */
    public ResultData queryByCompanyId(Long companyId);

    /**
     * 添加
     */
    public ResultData insert(JellyBusinessRule businessRule);

    /**
     * 修改
     */
    public ResultData update(JellyBusinessRule businessRule);

    /**
     * 删除
     */
    public ResultData delete(Long id);

}
