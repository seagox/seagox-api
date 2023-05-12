package com.seagox.oa.excel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seagox.oa.excel.entity.JellyBusinessTable;

import java.util.List;
import java.util.Map;

/**
 * 业务表
 */
public interface JellyBusinessTableMapper extends BaseMapper<JellyBusinessTable> {

    /**
     * 查询一对多
     */
	public List<Map<String, Object>> queryModel(Long companyId);

}
