package com.seagull.oa.export.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seagull.oa.export.entity.JellyExportRuleDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface JellyExportRuleDetailMapper extends BaseMapper<JellyExportRuleDetail> {

    /**
     * 根据规则id查询
     */
    public List<Map<String, Object>> queryByRuleId(@Param("ruleId") Long ruleId);

}
