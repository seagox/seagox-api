package com.seagull.oa.excel.service;

import com.seagull.oa.common.ResultData;
import com.seagull.oa.excel.entity.JellyDataSheet;

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
