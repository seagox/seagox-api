package com.seagox.oa.excel.service;

import java.util.List;

import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyViewField;
import com.seagox.oa.template.FieldModel;

public interface IJellyViewFieldService {

    /**
     * 查询全部
     */
    public ResultData queryAll(String viewName);

    /**
     * 分页查询
     */
    public ResultData queryByPage(Integer pageNo, Integer pageSize, Long viewId, String name, String comment);

    /**
     * 添加
     */
    public ResultData insert(JellyViewField viewField);

    /**
     * 修改
     */
    public ResultData update(JellyViewField viewField);

    /**
     * 删除
     */
    public ResultData delete(Long id);

    /**
     * 根据视图id查询
     */
    public ResultData queryByViewId(Long viewId);
    
    /**
     * 导入处理
     */
    public ResultData importHandle(Long viewId, List<FieldModel> resultList);

}
