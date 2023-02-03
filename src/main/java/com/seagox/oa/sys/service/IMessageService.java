package com.seagox.oa.sys.service;

import com.seagox.oa.common.ResultData;

public interface IMessageService {

    /**
     * 是否有未读消息
     */
    public ResultData queryUnRead(Long companyId, Long userId);

    /**
     * 分页查询
     *
     * @param pageNo    起始页
     * @param pageSize  每页大小
     * @param companyId 公司id
     * @param userId    用户id
     */
    public ResultData queryByPage(Integer pageNo, Integer pageSize, Long companyId, Long userId, Integer status, String title);

    /**
     * 处理消息
     */
    public ResultData update(Long userId, Long id);

    /**
     * 处理消息(所有未读)
     */
    public ResultData updateAll(Long userId);

    /**
     * 查询预警数据
     */
    public ResultData queryWarn(Long companyId, Long userId);

    /**
     * 发起会议
     */
    public ResultData meeting(Long companyId, Long userId, String url, String memberValues);

}
