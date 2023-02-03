package com.seagox.oa.excel.service;

import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyDicDetail;

public interface IJellyDicDetailService {

    /**
     * 分页查询
     */
    public ResultData queryByPage(Integer pageNo, Integer pageSize, Long classifyId);

    /**
     * 查询显示
     */
    public ResultData queryDisplay(Long classifyId);

    /**
     * 新增
     */
    public ResultData insert(JellyDicDetail dicDetail);

    /**
     * 更新
     */
    public ResultData update(JellyDicDetail dicDetail);

    /**
     * 删除
     */
    public ResultData delete(Long id);

    /**
     * 字典分类详情
     *
     * @param classifyId 字典分类id
     */
    public ResultData queryByClassifyId(Long classifyId);
}
