package com.seagull.oa.excel.service;

import com.seagull.oa.common.ResultData;
import com.seagull.oa.excel.entity.JellyTableClassify;

public interface IJellyTableClassifyService {

    /**
     * 查询所有
     */
    public ResultData queryAll(Long companyId);

    /**
     * 新增
     */
    public ResultData insert(JellyTableClassify tableClassify);

    /**
     * 更新
     */
    public ResultData update(JellyTableClassify tableClassify);

    /**
     * 删除
     */
    public ResultData delete(Long id);

    /**
     * 查询通过id
     */
    public ResultData queryById(Long id);

}
