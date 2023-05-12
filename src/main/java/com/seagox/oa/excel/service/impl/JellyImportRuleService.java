package com.seagox.oa.excel.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.seagox.oa.common.ResultCode;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyBusinessField;
import com.seagox.oa.excel.entity.JellyImportRule;
import com.seagox.oa.excel.entity.JellyImportRuleDetail;
import com.seagox.oa.excel.mapper.JellyBusinessFieldMapper;
import com.seagox.oa.excel.mapper.JellyImportRuleDetailMapper;
import com.seagox.oa.excel.mapper.JellyImportRuleMapper;
import com.seagox.oa.excel.service.IJellyImportRuleService;
import com.seagox.oa.util.ExcelUtils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

@Service
public class JellyImportRuleService implements IJellyImportRuleService {

    @Autowired
    private JellyImportRuleMapper importRuleMapper;

    @Autowired
    private JellyImportRuleDetailMapper importRuleDetailMapper;
    
    @Autowired
    private JellyBusinessFieldMapper businessFieldMapper;

    @Override
    public ResultData queryAll(Long companyId) {
    	LambdaQueryWrapper<JellyImportRule> qw = new LambdaQueryWrapper<>();
    	qw.eq(JellyImportRule::getCompanyId, companyId);
    	List<JellyImportRule> list = importRuleMapper.selectList(qw);
        return ResultData.success(list);
    }
    
    @Transactional
    @Override
    public ResultData insert(JellyImportRule importRule) {
        // 判断code是否重复
        LambdaQueryWrapper<JellyImportRule> qw = new LambdaQueryWrapper<>();
        qw.eq(JellyImportRule::getCode, importRule.getCode().toString());
        if (importRuleMapper.selectCount(qw) > 0) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "编码已存在！");
        }
        importRuleMapper.insert(importRule);
        JSONArray as = JSONArray.parseArray(importRule.getTemplateSource());
        String url = as.getJSONObject(0).getString("url");
        InputStream inputStream = null;
        try {
			inputStream = new URL(url).openStream();
			JSONObject result = ExcelUtils.readImportRuleSheet(inputStream);
			LambdaQueryWrapper<JellyBusinessField> businessFieldQw = new LambdaQueryWrapper<>();
			businessFieldQw.eq(JellyBusinessField::getBusinessTableId, importRule.getDataSource());
			List<JellyBusinessField> businessFieldList = businessFieldMapper.selectList(businessFieldQw);
			JSONObject fieldMap = new JSONObject();
			for (int i=0;i<businessFieldList.size();i++) {
				JellyBusinessField businessField = businessFieldList.get(i);
				fieldMap.put(businessField.getRemark(), businessField.getId());
			}
			for (String str : result.keySet()) {
				if(fieldMap.containsKey(str)) {
					JellyImportRuleDetail importRuleDetail = new JellyImportRuleDetail();
					importRuleDetail.setRuleId(importRule.getId());
					importRuleDetail.setField(fieldMap.getLong(str));
					importRuleDetail.setCol(result.getString(str));
					importRuleDetail.setRule("[]");
					importRuleDetailMapper.insert(importRuleDetail);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
		}
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
