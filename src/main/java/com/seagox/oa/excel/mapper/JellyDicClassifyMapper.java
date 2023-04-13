package com.seagox.oa.excel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seagox.oa.excel.entity.JellyDicClassify;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 字典分类
 */
public interface JellyDicClassifyMapper extends BaseMapper<JellyDicClassify> {
    
    /**
     * 查询列表通过名称
     */
    public List<Map<String, Object>> queryByName(@Param("companyId") Long companyId, @Param("name") String name);

}
