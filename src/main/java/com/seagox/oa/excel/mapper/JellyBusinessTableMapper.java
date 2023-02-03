package com.seagox.oa.excel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seagox.oa.excel.entity.JellyBusinessTable;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 业务表
 */
public interface JellyBusinessTableMapper extends BaseMapper<JellyBusinessTable> {

    /**
     * 查询列表
     */
    public List<Map<String, Object>> queryByCode(@Param("prefix") String prefix);

}
