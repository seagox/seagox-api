package com.seagox.oa.excel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seagox.oa.excel.entity.JellyTableClassify;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 表头分类
 */
public interface JellyTableClassifyMapper extends BaseMapper<JellyTableClassify> {

    /**
     * 查询列表
     */
    public List<Map<String, Object>> queryByCode(@Param("prefix") String prefix);

}
