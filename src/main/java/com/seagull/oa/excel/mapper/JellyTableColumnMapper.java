package com.seagull.oa.excel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seagull.oa.excel.entity.JellyTableColumn;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 表格表头
 */
public interface JellyTableColumnMapper extends BaseMapper<JellyTableColumn> {

    /**
     * 查询全部通过表名
     *
     * @param classifyId 分类id
     */
    public List<Map<String, Object>> queryConfigByClassifyId(@Param("classifyId") Long classifyId, @Param("userId") Long userId);

    /**
     * 通过格式化更新表格表头
     *
     * @param formatter 格式化
     * @param options   数据源
     */
    public int updateByFormatter(@Param("formatter") Long formatter, @Param("options") String options);

    /**
     * 查询全部通过表名
     *
     * @param classifyId 分类id
     */
    public List<JellyTableColumn> queryConfigByClassifyIdTree(@Param("classifyId") Long classifyId, @Param("userId") Long userId);

}
