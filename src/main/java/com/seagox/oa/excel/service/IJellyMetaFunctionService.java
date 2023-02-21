package com.seagox.oa.excel.service;

import org.springframework.web.bind.annotation.PathVariable;

import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyMetaFunction;

public interface IJellyMetaFunctionService {

	/**
     * 分页查询
     *
     * @param pageNo    起始页
     * @param pageSize  每页大小
     * @param companyId 公司id
     * @param name      名称
     * @param path      路径
     * @param type      类型
     */
    public ResultData queryByPage(Integer pageNo, Integer pageSize, Long companyId, String name, String path, Integer type);
    
    /**
     * 查询所有
     *
     * @param companyId 公司id
     */
    public ResultData queryByCompanyId(Long companyId);
    
    /**
     * 添加
     */
    public ResultData insert(JellyMetaFunction metaFunction);

    /**
     * 修改
     */
    public ResultData update(JellyMetaFunction metaFunction);
    
    /**
     * 通过id获取
     */
    public ResultData queryById(@PathVariable Long id);
    
    /**
     * 删除
     */
    public ResultData delete(Long id, String path);

    /**
     * 通过路径查询规则
     */
    public String queryByPath(String path);

}
