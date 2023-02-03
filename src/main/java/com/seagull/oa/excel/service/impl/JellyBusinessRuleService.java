package com.seagull.oa.excel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seagull.oa.common.ResultData;
import com.seagull.oa.excel.entity.JellyBusinessRule;
import com.seagull.oa.excel.mapper.JellyBusinessRuleMapper;
import com.seagull.oa.excel.service.IJellyBusinessRuleService;
import com.seagull.oa.sys.entity.SysCompany;
import com.seagull.oa.sys.mapper.CompanyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JellyBusinessRuleService implements IJellyBusinessRuleService {

    @Autowired
    private JellyBusinessRuleMapper businessRuleMapper;

    @Autowired
    private CompanyMapper companyMapper;

    @Override
    public ResultData queryByPage(Integer pageNo, Integer pageSize, Long companyId) {
        SysCompany company = companyMapper.selectById(companyId);
        PageHelper.startPage(pageNo, pageSize);
        List<Map<String, Object>> list = businessRuleMapper.queryByCode(company.getCode().substring(0, 4));
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        return ResultData.success(pageInfo);
    }

    @Override
    public ResultData queryByCompanyId(Long companyId) {
        LambdaQueryWrapper<JellyBusinessRule> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(JellyBusinessRule::getCompanyId, companyId);
        List<JellyBusinessRule> list = businessRuleMapper.selectList(queryWrapper);
        return ResultData.success(list);
    }

    @Override
    public ResultData insert(JellyBusinessRule businessRule) {
        businessRuleMapper.insert(businessRule);
        return ResultData.success(null);
    }

    @Override
    public ResultData update(JellyBusinessRule businessRule) {
        businessRuleMapper.updateById(businessRule);
        return ResultData.success(null);
    }

    @Override
    public ResultData delete(Long id) {
        businessRuleMapper.deleteById(id);
        return ResultData.success(null);
    }

}
