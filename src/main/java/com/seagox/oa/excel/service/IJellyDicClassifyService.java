package com.seagox.oa.excel.service;

import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyDicClassify;

public interface IJellyDicClassifyService {

    /**
     * 查询显示
     */
    public ResultData queryDisplay(Long companyId);

    /**
     * 新增
     */
    public ResultData insert(JellyDicClassify dicClassify);

    /**
     * 更新
     */
    public ResultData update(JellyDicClassify dicClassify);

    /**
     * 删除
     */
    public ResultData delete(Long id);

    /**
     * 查询通过id
     */
    public ResultData queryById(Long id);

}
