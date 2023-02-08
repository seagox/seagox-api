package com.seagox.oa.excel.service;

import org.springframework.web.bind.annotation.PathVariable;

import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyMetaPage;

public interface IJellyMetaPageService {

	/**
     * 分页查询
     *
     * @param pageNo    起始页
     * @param pageSize  每页大小
     * @param companyId 公司id
     * @param name      名称
     * @param path      路径
     */
    public ResultData queryByPage(Integer pageNo, Integer pageSize, Long companyId, String name, String path);
    
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
     * 通过id获取
     */
    public ResultData queryById(@PathVariable Long id);
    
    /**
     * 删除
     */
    public ResultData delete(Long id, String path);

    /**
     * 通过路径查询元页面信息
     */
    public ResultData selectByPath(String path);

}
