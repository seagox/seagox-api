package com.seagox.oa.excel.service;

import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellySvg;

public interface IJellySvgService {
    /**
     * 分页查询
     *
     * @param pageNo   起始页
     * @param pageSize 每页大小
     * @param name     名称
     */
    public ResultData queryByPage(Integer pageNo, Integer pageSize, String name);

    /**
     * 查询通过id
     */
    ResultData queryById(Long id);

    /**
     * 新增
     */
    public ResultData insert(JellySvg jellySvg);

    /**
     * 删除
     */
    public ResultData delete(Long id);

}
