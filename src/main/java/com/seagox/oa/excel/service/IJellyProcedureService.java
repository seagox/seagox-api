package com.seagox.oa.excel.service;

import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyProcedure;

import javax.servlet.http.HttpServletRequest;

public interface IJellyProcedureService {

	/**
     * 查询全部
     */
    public ResultData queryAll(Long companyId, String name, String remark);

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

}
