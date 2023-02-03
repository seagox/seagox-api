package com.seagox.oa.excel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.seagox.oa.common.ResultCode;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyImportRule;
import com.seagox.oa.excel.entity.JellyImportRuleDetail;
import com.seagox.oa.excel.mapper.JellyImportRuleDetailMapper;
import com.seagox.oa.excel.mapper.JellyImportRuleMapper;
import com.seagox.oa.excel.service.IJellyImportRuleService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JellyImportRuleService implements IJellyImportRuleService {

    @Autowired
    private JellyImportRuleMapper importRuleMapper;

    @Autowired
    private JellyImportRuleDetailMapper importRuleDetailMapper;

    @Override
    public ResultData queryAll(Long companyId) {
    	LambdaQueryWrapper<JellyImportRule> qw = new LambdaQueryWrapper<>();
    	qw.eq(JellyImportRule::getCompanyId, companyId);
    	List<JellyImportRule> list = importRuleMapper.selectList(qw);
        return ResultData.success(list);
    }

    @Override
    public ResultData insert(JellyImportRule importRule) {
        // 判断code是否重复
        LambdaQueryWrapper<JellyImportRule> qw = new LambdaQueryWrapper<>();
        qw.eq(JellyImportRule::getCode, importRule.getCode().toString());
        if (importRuleMapper.selectCount(qw) > 0) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "编码已存在！");
        }
        importRuleMapper.insert(importRule);
        return ResultData.success(importRule.getId());
    }

    @Override
    public ResultData update(JellyImportRule importRule) {
        // 判断code是否重复
        LambdaQueryWrapper<JellyImportRule> qw = new LambdaQueryWrapper<>();
        qw.eq(JellyImportRule::getCode, importRule.getCode().toString()).ne(JellyImportRule::getId, importRule.getId());
        if (importRuleMapper.selectCount(qw) > 0) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "编码已存在！");
        }
        importRuleMapper.updateById(importRule);
        return ResultData.success(importRule.getId());
    }

    @Override
    public ResultData delete(Long id) {
        // 判断规则详情是否存在
        LambdaQueryWrapper<JellyImportRuleDetail> qw = new LambdaQueryWrapper<>();
        qw.eq(JellyImportRuleDetail::getRuleId, id);
        if (importRuleDetailMapper.selectCount(qw) > 0) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "有关联业务字段，不可删除");
        }
        importRuleMapper.deleteById(id);
        return ResultData.success(null);
    }

    @Transactional
    @Override
    public ResultData copy(Long id) {
        JellyImportRule sourceRule = importRuleMapper.selectById(id);
        JellyImportRule targetRule = new JellyImportRule();
        BeanUtils.copyProperties(sourceRule, targetRule);
        targetRule.setCode(targetRule.getCode() + "_copy");
        importRuleMapper.insert(targetRule);
        // 规则详情
        LambdaQueryWrapper<JellyImportRuleDetail> qw = new LambdaQueryWrapper<>();
        qw.eq(JellyImportRuleDetail::getRuleId, id);
        List<JellyImportRuleDetail> ruleDetailList = importRuleDetailMapper.selectList(qw);
        for (JellyImportRuleDetail ruleDetail : ruleDetailList) {
            JellyImportRuleDetail newRuleDetail = new JellyImportRuleDetail();
            newRuleDetail.setRuleId(targetRule.getId());
            newRuleDetail.setField(ruleDetail.getField());
            newRuleDetail.setCol(ruleDetail.getCol());
            importRuleDetailMapper.insert(newRuleDetail);
        }
        return ResultData.success(null);
    }
}
