package com.seagox.oa.excel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seagox.oa.excel.entity.JellyJob;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 任务调度
 */
public interface JellyJobMapper extends BaseMapper<JellyJob> {

    /**
     * 查询列表
     */
    public List<Map<String, Object>> queryByCode(@Param("prefix") String prefix);

}
