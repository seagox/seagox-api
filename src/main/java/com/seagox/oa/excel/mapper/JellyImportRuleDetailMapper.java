package com.seagox.oa.excel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seagox.oa.excel.entity.JellyImportRuleDetail;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface JellyImportRuleDetailMapper extends BaseMapper<JellyImportRuleDetail> {

    /**
     * 根据规则id查询
     */
    public List<Map<String, Object>> queryByRuleId(@Param("ruleId") Long ruleId);

}
