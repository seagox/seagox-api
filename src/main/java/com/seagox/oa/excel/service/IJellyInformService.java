package com.seagox.oa.excel.service;

import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyInform;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IJellyInformService {

    /**
     * 分页查询
     *
     * @param pageNo   起始页
     * @param pageSize 每页大小
     * @param name     名称
     */
    public ResultData queryByPage(Integer pageNo, Integer pageSize, Long companyId, String name);

    /**
     * 添加
     */
    public ResultData insert(JellyInform inform);

    /**
     * 修改
     */
    public ResultData update(JellyInform inform);

    /**
     * 删除
     */
    public ResultData delete(Long id);

    /**
     * 查询全部通过公司id
     */
    public ResultData queryByCompanyId(Long companyId);

    /**
     * 列表导出
     */
    public void export(HttpServletRequest request, HttpServletResponse response);


}
