package com.seagull.oa.excel.service;

import com.seagull.oa.common.ResultData;
import com.seagull.oa.excel.entity.JellyBusinessField;

public interface IJellyBusinessFieldService {

    /**
     * 查询全部
     */
    public ResultData queryAll(String tableName);

    /**
     * 分页查询
     */
    public ResultData queryByPage(Integer pageNo, Integer pageSize, Long businessTableId, String name, String comment);

    /**
     * 添加
     */
    public ResultData insert(JellyBusinessField businessField);

    /**
     * 修改
     */
    public ResultData update(JellyBusinessField businessField);

    /**
     * 删除
     */
    public ResultData delete(Long id);

    /**
     * 根据业务表id查询
     */
    public ResultData queryByTableId(Long tableId);

}
