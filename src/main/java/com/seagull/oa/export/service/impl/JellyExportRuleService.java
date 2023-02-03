package com.seagull.oa.export.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.seagull.oa.common.ResultCode;
import com.seagull.oa.common.ResultData;
import com.seagull.oa.export.entity.JellyExportRule;
import com.seagull.oa.export.entity.JellyExportRuleDetail;
import com.seagull.oa.export.mapper.JellyExportRuleDetailMapper;
import com.seagull.oa.export.mapper.JellyExportRuleMapper;
import com.seagull.oa.export.service.IJellyExportRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JellyExportRuleService implements IJellyExportRuleService {

    @Autowired
    private JellyExportRuleMapper exportRuleMapper;

    @Autowired
    private JellyExportRuleDetailMapper exportRuleDetailMapper;

    @Override
    public ResultData queryAll(Long companyId) {
        return ResultData.success(exportRuleMapper.queryAll());
    }

    @Override
    public ResultData insert(JellyExportRule exportRule) {
        // 判断code是否重复
        LambdaQueryWrapper<JellyExportRule> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(JellyExportRule::getCode, exportRule.getCode().toString());
        if (exportRuleMapper.selectCount(queryWrapper) > 0) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "编码已存在！");
        }
        exportRuleMapper.insert(exportRule);
        return ResultData.success(exportRule.getId());
    }

    @Override
    public ResultData update(JellyExportRule exportRule) {
        // 判断code是否重复
        LambdaQueryWrapper<JellyExportRule> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(JellyExportRule::getCode, exportRule.getCode().toString()).ne(JellyExportRule::getId, exportRule.getId());
        if (exportRuleMapper.selectCount(queryWrapper) > 0) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "编码已存在！");
        }
        exportRuleMapper.updateById(exportRule);
        return ResultData.success(exportRule.getId());
    }

    @Override
    public ResultData delete(Long id) {
        // 判断规则详情是否存在
        LambdaQueryWrapper<JellyExportRuleDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(JellyExportRuleDetail::getExportRuleId, id);
        if (exportRuleDetailMapper.selectCount(queryWrapper) > 0) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "有关联业务字段，不可删除");
        }
        exportRuleMapper.deleteById(id);
        return ResultData.success(null);
    }

    @Transactional
    @Override
    public ResultData copyRule(Long id) {
        JellyExportRule exportRule = exportRuleMapper.selectById(id);
        if (exportRule == null) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "复制失败，规则为空！");
        }
        JellyExportRule newRule = new JellyExportRule();
        newRule.setCompanyId(exportRule.getCompanyId());
        newRule.setCode(exportRule.getCode() + "_copy");
        newRule.setName(exportRule.getName());
        newRule.setDataSource(exportRule.getDataSource());
        exportRuleMapper.insert(newRule);
        // 规则详情
        LambdaQueryWrapper<JellyExportRuleDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(JellyExportRuleDetail::getExportRuleId, id);
        List<JellyExportRuleDetail> ruleDetailList = exportRuleDetailMapper.selectList(queryWrapper);
        for (JellyExportRuleDetail ruleDetail : ruleDetailList) {
            JellyExportRuleDetail newRuleDetail = new JellyExportRuleDetail();
            newRuleDetail.setExportRuleId(newRule.getId());
            newRuleDetail.setField(ruleDetail.getField());
            newRuleDetail.setCol(ruleDetail.getCol());
            exportRuleDetailMapper.insert(newRuleDetail);
        }
        return ResultData.success(null);
    }
}
