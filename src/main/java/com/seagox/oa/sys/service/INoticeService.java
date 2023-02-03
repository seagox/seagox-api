package com.seagox.oa.sys.service;

import com.seagox.oa.common.ResultData;
import com.seagox.oa.sys.entity.SysNotice;

/**
 * 公告 服务类
 */
public interface INoticeService {

    /**
     * 发布
     */
    public ResultData publish(SysNotice sysNotice);

    /**
     * 详情
     */
    public ResultData queryById(long id);

    /**
     * 分页查询
     */
    ResultData queryByPage(Integer pageNo, Integer pageSize, Long companyId, Long userId, Integer status, String title, String queryType);

    /**
     * 删除
     */
    ResultData deleteById(Long id);

    /**
     * 暂存
     */
    ResultData staging(SysNotice sysNotice);

    /**
     * 撤回
     */
    ResultData withdraw(Long id);

    /**
     * 查询已发/接收通知
     */
    ResultData querySendOrRec(Long companyId, Long userId);

    /**
     * 查询通知类型字典
     */
    ResultData queryType();

    /**
     * 查询通知展示详情
     */
    ResultData queryShowById(long id);

}
