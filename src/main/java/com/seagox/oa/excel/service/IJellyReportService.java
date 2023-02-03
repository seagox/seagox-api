package com.seagox.oa.excel.service;

import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyReport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IJellyReportService {

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
    public ResultData insert(JellyReport report);

    /**
     * 修改
     */
    public ResultData update(JellyReport report);

    /**
     * 表单详情
     */
    public ResultData queryById(Long userId, Long id);

    /**
     * 删除
     */
    public ResultData delete(Long id);

    /**
     * 查询全部通过公司id
     */
    public ResultData queryByCompanyId(Long companyId);

    /**
     * 查询列表数据
     *
     * @param companyId 公司id
     * @param userId    用户id
     * @param id        报表id
     * @param search    搜索条件
     */
    public ResultData queryListById(Long companyId, Long userId, Long id, String search);

    /**
     * 列表导出
     */
    public void export(HttpServletRequest request, HttpServletResponse response);

}
