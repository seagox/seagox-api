package com.seagox.oa.excel.service;

import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyCommonWords;

public interface IJellyCommonWordsService {

    /**
     * 查询全部
     *
     * @param companyId 公司id
     * @param userId    用户id
     */
    public ResultData queryAll(Long companyId, Long userId);

    /**
     * 添加
     */
    public ResultData insert(JellyCommonWords commonWords);

    /**
     * 修改
     */
    public ResultData update(JellyCommonWords commonWords);

    /**
     * 删除
     */
    public ResultData delete(Long id);

}
