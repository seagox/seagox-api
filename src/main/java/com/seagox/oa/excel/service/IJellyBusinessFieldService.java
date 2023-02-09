package com.seagox.oa.excel.service;

import java.util.List;

import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyBusinessField;
import com.seagox.oa.template.FieldModel;

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
    
    /**
     * 导入处理
     */
    public ResultData importHandle(Long tableId, List<FieldModel> resultList);

}
