package com.seagull.oa.export.service;

import com.seagull.oa.common.ResultData;
import com.seagull.oa.export.entity.JellyExportDimension;

public interface IJellyExportDimensionService {

    /**
     * 分页查询
     *
     * @param pageNo   起始页
     * @param pageSize 每页大小
     * @param odsId    odsId
     * @param userId   用户id
     */
    public ResultData queryByPage(Integer pageNo, Integer pageSize, Long odsId, Long userId);

    /**
     * 根据odsId查询全部
     */
    public ResultData queryAll(Long odsId);

    /**
     * 添加
     */
    public ResultData insert(JellyExportDimension exportDimension);

    /**
     * 修改
     */
    public ResultData update(JellyExportDimension exportDimension);

    /**
     * 删除
     */
    public ResultData delete(Long id);

    /**
     * 查询区域
     */
    public ResultData queryArea();

}
