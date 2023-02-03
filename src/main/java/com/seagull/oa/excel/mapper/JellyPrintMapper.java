package com.seagull.oa.excel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seagull.oa.excel.entity.JellyPrint;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 打印模版
 */
public interface JellyPrintMapper extends BaseMapper<JellyPrint> {

    /**
     * 查询列表
     */
    public List<Map<String, Object>> queryByCode(@Param("prefix") String prefix, @Param("name") String name);

}
