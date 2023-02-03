package com.seagox.oa.excel.service;

import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyMetaPage;

public interface IJellyMetaPageService {

    /**
     * 查询所有
     *
     * @param companyId 公司id
     */
    public ResultData queryByCompanyId(Long companyId);

    /**
     * 添加
     */
    public ResultData insert(JellyMetaPage metaPage);

    /**
     * 修改
     */
    public ResultData update(JellyMetaPage metaPage);

    /**
     * 删除
     */
    public ResultData delete(Long id, String path);

    /**
     * 通过路径查询元页面信息
     */
    public ResultData selectByPath(String path);

}
