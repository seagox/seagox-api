package com.seagox.oa.excel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seagox.oa.excel.entity.JellyMetaFunction;
import org.apache.ibatis.annotations.Param;

/**
 * 元函数
 */
public interface JellyMetaFunctionMapper extends BaseMapper<JellyMetaFunction> {

    /**
     * 查询多级
     *
     * @param route 路由
     * @param path  路径
     */
    public String queryMultiByPath(@Param("route") String route, @Param("path") String path);

}
