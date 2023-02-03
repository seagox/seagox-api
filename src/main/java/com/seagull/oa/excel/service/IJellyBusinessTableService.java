package com.seagull.oa.excel.service;

import com.seagull.oa.common.ResultData;
import com.seagull.oa.excel.entity.JellyBusinessTable;
import org.springframework.web.bind.annotation.PathVariable;

public interface IJellyBusinessTableService {

    /**
     * 查询全部
     */
    public ResultData queryAll(Long companyId);

    /**
     * 添加
     */
    public ResultData insert(JellyBusinessTable businessTable);

    /**
     * 修改
     */
    public ResultData update(JellyBusinessTable businessTable);

    /**
     * 删除
     */
    public ResultData delete(Long id);

    /**
     * 查询通过id
     */
    public ResultData queryById(Long id);

    /**
     * 查询通过表ids
     */
    public ResultData queryByTableIds(String tableIds);

    /**
     * 复制表
     */
    public ResultData copyTable(Long id);

    /**
     * 根据类型查询所有表
     */
    public ResultData queryByType(String type);

    /**
     * 根据级联数据
     */
    public ResultData queryCascader(@PathVariable("id") Long id, String rule);

}
