package com.seagull.oa.excel.service;

import com.seagull.oa.common.ResultData;
import com.seagull.oa.excel.entity.JellyTableColumn;

public interface IJellyTableColumnService {

    /**
     * 查询所有
     */
    public ResultData queryByClassifyId(Long classifyId);

    /**
     * 分页查询
     */
    public ResultData queryByPage(Integer pageNo, Integer pageSize, Long classifyId);

    /**
     * 新增
     */
    public ResultData insert(JellyTableColumn tableColumn);

    /**
     * 更新
     */
    public ResultData update(JellyTableColumn tableColumn);

    /**
     * 删除
     */
    public ResultData delete(Long id);
}
