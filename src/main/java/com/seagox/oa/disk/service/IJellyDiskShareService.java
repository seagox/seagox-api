package com.seagox.oa.disk.service;

import java.util.List;
import java.util.Map;

/**
 * 他人分享 服务类
 */
public interface IJellyDiskShareService {

    /**
     * 根据用户id查询
     *
     * @param companyId 公司id
     * @param userId    用户id
     * @return
     */
    List<Map<String, Object>> queryByUserId(Long companyId, Long userId);
}
