package com.seagull.oa.export.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seagull.oa.export.entity.JellyExportRule;

import java.util.List;
import java.util.Map;

public interface JellyExportRuleMapper extends BaseMapper<JellyExportRule> {

    public List<Map<String, Object>> queryAll();

}
