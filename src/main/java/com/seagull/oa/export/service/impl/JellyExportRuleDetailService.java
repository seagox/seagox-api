package com.seagull.oa.export.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seagull.oa.common.ResultCode;
import com.seagull.oa.common.ResultData;
import com.seagull.oa.export.entity.JellyExportRuleDetail;
import com.seagull.oa.export.mapper.JellyExportRuleDetailMapper;
import com.seagull.oa.export.service.IJellyExportRuleDetailService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JellyExportRuleDetailService implements IJellyExportRuleDetailService {

    @Autowired
    private JellyExportRuleDetailMapper exportRuleDetailMapper;

    @Override
    public ResultData queryByRuleId(Long ruleId) {
        List<Map<String, Object>> list = exportRuleDetailMapper.queryByRuleId(ruleId);
        return ResultData.success(list);
    }

    @Override
    public ResultData queryByPage(Integer pageNo, Integer pageSize, Long ruleId) {
        PageHelper.startPage(pageNo, pageSize);
        List<Map<String, Object>> list = exportRuleDetailMapper.queryByRuleId(ruleId);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        return ResultData.success(pageInfo);
    }

    @Override
    public ResultData insert(JellyExportRuleDetail exportRuleDetail) {
        // 判断列名、字段名是否重复
        LambdaQueryWrapper<JellyExportRuleDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(JellyExportRuleDetail::getExportRuleId, exportRuleDetail.getExportRuleId())
                .and(qw -> qw.eq(JellyExportRuleDetail::getCol, exportRuleDetail.getCol())
                        .or().eq(JellyExportRuleDetail::getField, exportRuleDetail.getField()));
        if (exportRuleDetailMapper.selectCount(queryWrapper) > 0) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "该列名或字段已存在，请重新选择！");
        }
        // 判断sql语句是否正确
        if (!StringUtils.isEmpty(exportRuleDetail.getSqlSource())) {
            String notAllowedKeyWords = "create |alter |drop |grant |deny |revoke |update |insert |delete ";
            String[] keyStr = notAllowedKeyWords.split("\\|");
            String dataSource = exportRuleDetail.getSqlSource().toLowerCase();
            for (String str : keyStr) {
                if (dataSource.contains(str)) {
                    return ResultData.warn(ResultCode.OTHER_ERROR, "sql包含关键字" + str);
                }
            }
        }
        exportRuleDetailMapper.insert(exportRuleDetail);
        return ResultData.success(exportRuleDetail.getId());
    }

    @Override
    public ResultData update(JellyExportRuleDetail exportRuleDetail) {
        // 判断列名、字段名是否重复
        LambdaQueryWrapper<JellyExportRuleDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(JellyExportRuleDetail::getExportRuleId, exportRuleDetail.getExportRuleId())
                .ne(JellyExportRuleDetail::getId, exportRuleDetail.getId())
                .and(qw -> qw.eq(JellyExportRuleDetail::getCol, exportRuleDetail.getCol())
                        .or().eq(JellyExportRuleDetail::getField, exportRuleDetail.getField()));
        if (exportRuleDetailMapper.selectCount(queryWrapper) > 0) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "该列名已存在，请重新选择！");
        }
        // 判断sql语句是否正确
        if (!StringUtils.isEmpty(exportRuleDetail.getSqlSource())) {
            String notAllowedKeyWords = "create |alter |drop |grant |deny |revoke |update |insert |delete ";
            String[] keyStr = notAllowedKeyWords.split("\\|");
            String dataSource = exportRuleDetail.getSqlSource().toLowerCase();
            for (String str : keyStr) {
                if (dataSource.contains(str)) {
                    return ResultData.warn(ResultCode.OTHER_ERROR, "sql包含关键字" + str);
                }
            }
        }
        exportRuleDetailMapper.updateById(exportRuleDetail);
        return ResultData.success(exportRuleDetail.getId());
    }

    @Override
    public ResultData delete(Long id) {
        exportRuleDetailMapper.deleteById(id);
        return ResultData.success(null);
    }

    @Override
    public ResultData deleteByRuleId(Long ruleId) {
        LambdaQueryWrapper<JellyExportRuleDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(JellyExportRuleDetail::getExportRuleId, ruleId);
        exportRuleDetailMapper.delete(queryWrapper);
        return ResultData.success(null);
    }
}
