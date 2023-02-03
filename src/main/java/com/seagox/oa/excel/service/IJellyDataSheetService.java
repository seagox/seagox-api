package com.seagox.oa.excel.service;

import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyDataSheet;

public interface IJellyDataSheetService {

    /**
     * 查询全部
     */
    public ResultData queryAll(Long formId);

    /**
     * 添加
     */
    public ResultData insert(JellyDataSheet dataSheet);

    /**
     * 修改
     */
    public ResultData update(JellyDataSheet dataSheet);

    /**
     * 删除
     */
    public ResultData delete(Long id);

}
