package com.seagull.oa.excel.service;

import com.seagull.oa.common.ResultData;
import com.seagull.oa.excel.entity.JellyProcedure;

import javax.servlet.http.HttpServletRequest;

public interface IJellyProcedureService {

    /**
     * 分页查询
     *
     * @param pageNo   起始页
     * @param pageSize 每页大小
     * @param name     名称
     * @param remark   备注
     */
    public ResultData queryByPage(Integer pageNo, Integer pageSize, Long companyId, String name, String remark);

    /**
     * 添加
     */
    public ResultData insert(JellyProcedure procedure);

    /**
     * 修改
     */
    public ResultData update(JellyProcedure procedure);

    /**
     * 删除
     */
    public ResultData delete(Long id);

    /**
     * 执行
     */
    public ResultData execute(Long id, HttpServletRequest request);

    /**
     * 执行全部
     *
     * @param year 年份
     */
    public ResultData executeAll(String year);
}
