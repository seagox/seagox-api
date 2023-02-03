package com.seagull.oa.sys.service;

import com.seagull.oa.common.ResultData;

public interface ILogService {

    /**
     * 分页查询
     *
     * @param pageNo    起始页
     * @param pageSize  每页大小
     * @param companyId 公司id
     * @param account   帐号
     * @param name      名称
     */
    public ResultData queryByPage(Integer pageNo, Integer pageSize, Long companyId, String account, String name);

}
