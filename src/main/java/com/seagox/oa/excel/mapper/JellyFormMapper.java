package com.seagox.oa.excel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seagox.oa.excel.entity.JellyForm;

import java.util.List;
import java.util.Map;

/**
 * 表单管理
 */
public interface JellyFormMapper extends BaseMapper<JellyForm> {

    /**
     * 查询公共数据
     *
     * @param sql sql语句
     */
    public List<Map<String, Object>> queryPublicList(String sql);

}
