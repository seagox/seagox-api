package com.seagull.oa.excel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seagull.oa.excel.entity.JellyBusinessField;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 业务字段
 */
public interface JellyBusinessFieldMapper extends BaseMapper<JellyBusinessField> {

    /**
     * 查询全部通过表名
     *
     * @param tableName 表名
     * @param display   显示(0:全部;1:显示;2:隐藏;)
     */
    public List<Map<String, Object>> queryByTableName(@Param("tableName") String tableName, @Param("display") int display);

    /**
     * 查询全部通过表ids
     */
    public List<Map<String, Object>> queryByTableIds(String[] tableIds);

    /**
     * 查询业务字段全部表单id
     */
    public List<Map<String, Object>> queryByFormId(@Param("formId") Long formId);

    /**
     * 查询必填字段全部表单id
     */
    public List<Map<String, Object>> queryRequiredByFormId(@Param("formId") Long formId);

}
