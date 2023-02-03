package com.seagull.oa.export.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seagull.oa.export.entity.JellyExportDimension;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface JellyExportDimensionMapper extends BaseMapper<JellyExportDimension> {

    /**
     * 根据odsId查询维度管理
     */
    public List<Map<String, Object>> queryByOdsId(@Param("odsId") Long odsId);

}
