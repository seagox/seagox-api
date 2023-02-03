package com.seagull.oa.excel.service;

import com.seagull.oa.common.ResultData;
import com.seagull.oa.excel.entity.JellyPrint;
import org.springframework.web.bind.annotation.PathVariable;

public interface IJellyPrintService {

    /**
     * 分页查询
     *
     * @param pageNo    起始页
     * @param pageSize  每页大小
     * @param companyId 公司id
     * @param name      名称
     */
    public ResultData queryByPage(Integer pageNo, Integer pageSize, Long companyId, String name);

    /**
     * 查询全部
     */
    public ResultData queryAll(Long companyId);

    /**
     * 添加
     */
    public ResultData insert(JellyPrint print);

    /**
     * 修改
     */
    public ResultData update(JellyPrint print);

    /**
     * 详情
     */
    public ResultData queryById(@PathVariable Long id);

    /**
     * 删除
     */
    public ResultData delete(Long id);

    /**
     * 查询业务表
     */
    public ResultData queryBusinessTable(Long id);

    /**
     * 查询业务字段
     */
    public ResultData queryBusinessField(Long id);


}