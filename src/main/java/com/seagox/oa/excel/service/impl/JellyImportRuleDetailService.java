package com.seagox.oa.excel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seagox.oa.common.ResultCode;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyImportRuleDetail;
import com.seagox.oa.excel.mapper.JellyImportRuleDetailMapper;
import com.seagox.oa.excel.service.IJellyImportRuleDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JellyImportRuleDetailService implements IJellyImportRuleDetailService {

    @Autowired
    private JellyImportRuleDetailMapper importRuleDetailMapper;

    @Override
    public ResultData queryByRuleId(Long ruleId) {
        List<Map<String, Object>> list = importRuleDetailMapper.queryByRuleId(ruleId);
        return ResultData.success(list);
    }

    @Override
    public ResultData queryByPage(Integer pageNo, Integer pageSize, Long ruleId) {
        PageHelper.startPage(pageNo, pageSize);
        List<Map<String, Object>> list = importRuleDetailMapper.queryByRuleId(ruleId);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        return ResultData.success(pageInfo);
    }

    @Override
    public ResultData insert(JellyImportRuleDetail importRuleDetail) {
        // 判断列名、字段名是否重复
        LambdaQueryWrapper<JellyImportRuleDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(JellyImportRuleDetail::getRuleId, importRuleDetail.getRuleId())
                .and(qw -> qw.eq(JellyImportRuleDetail::getCol, importRuleDetail.getCol())
                        .or().eq(JellyImportRuleDetail::getField, importRuleDetail.getField()));
        if (importRuleDetailMapper.selectCount(queryWrapper) > 0) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "该列名或字段已存在，请重新选择！");
        }
        importRuleDetailMapper.insert(importRuleDetail);
        return ResultData.success(importRuleDetail.getId());
    }

    @Override
    public ResultData update(JellyImportRuleDetail importRuleDetail) {
        // 判断列名、字段名是否重复
        LambdaQueryWrapper<JellyImportRuleDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(JellyImportRuleDetail::getRuleId, importRuleDetail.getRuleId())
                .ne(JellyImportRuleDetail::getId, importRuleDetail.getId())
                .and(qw -> qw.eq(JellyImportRuleDetail::getCol, importRuleDetail.getCol())
                        .or().eq(JellyImportRuleDetail::getField, importRuleDetail.getField()));
        if (importRuleDetailMapper.selectCount(queryWrapper) > 0) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "该列名已存在，请重新选择！");
        }
        importRuleDetailMapper.updateById(importRuleDetail);
        return ResultData.success(importRuleDetail.getId());
    }

    @Override
    public ResultData delete(Long id) {
    	importRuleDetailMapper.deleteById(id);
        return ResultData.success(null);
    }

    @Override
    public ResultData deleteByRuleId(Long ruleId) {
        LambdaQueryWrapper<JellyImportRuleDetail> qw = new LambdaQueryWrapper<>();
        qw.eq(JellyImportRuleDetail::getRuleId, ruleId);
        importRuleDetailMapper.delete(qw);
        return ResultData.success(null);
    }
}
