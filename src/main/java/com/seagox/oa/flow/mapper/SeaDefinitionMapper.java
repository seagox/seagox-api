package com.seagox.oa.flow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seagox.oa.flow.entity.SeaDefinition;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 流程定义
 */
public interface SeaDefinitionMapper extends BaseMapper<SeaDefinition> {

    /**
     * 查询列表
     */
    public List<Map<String, Object>> queryByCode(@Param("prefix") String prefix, @Param("name") String name);

}
