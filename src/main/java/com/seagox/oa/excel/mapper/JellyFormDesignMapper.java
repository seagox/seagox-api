package com.seagox.oa.excel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seagox.oa.excel.entity.JellyFormDesign;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 表单设计
 */
public interface JellyFormDesignMapper extends BaseMapper<JellyFormDesign> {

    /**
     * 查询列表
     */
    public List<Map<String, Object>> queryByCode(@Param("prefix") String prefix, @Param("name") String name);

}
