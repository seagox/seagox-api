package com.seagox.oa.excel.service;

import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyFormDesign;
import org.springframework.web.bind.annotation.PathVariable;

public interface IJellyFormDesignService {

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
    public ResultData insert(JellyFormDesign formDesign);

    /**
     * 修改
     */
    public ResultData update(JellyFormDesign formDesign);

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
