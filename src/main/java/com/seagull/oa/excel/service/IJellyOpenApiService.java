package com.seagull.oa.excel.service;

import com.seagull.oa.common.ResultData;
import com.seagull.oa.excel.entity.JellyOpenApi;

public interface IJellyOpenApiService {

    /**
     * 分页查询
     *
     * @param pageNo   起始页
     * @param pageSize 每页大小
     */
    public ResultData queryByPage(Integer pageNo, Integer pageSize, Long companyId);

    /**
     * 添加
     */
    public ResultData insert(JellyOpenApi openApi);

    /**
     * 修改
     */
    public ResultData update(JellyOpenApi openApi);

    /**
     * 删除
     */
    public ResultData delete(Long id, String appid);

    /**
     * 查询密钥
     */
    public String queryByAppid(String appid);

}
