package com.seagox.oa.disk.service;

import java.util.List;
import java.util.Map;

/**
 * 回收站 服务类
 */
public interface IJellyDiskRecycleService {

    /**
     * 根据用户id查询
     * @param companyId 公司id
     * @param userId 用户id
     * @return
     */
    List<Map<String,Object>> queryByUserId(Long companyId, Long userId);

    /**
     * 还原
     * @param companyId 单位id
     * @param userId 用户id
     * @param id 主键id
     * @return
     */
    void revert(Long companyId, Long userId, Long id);

    /**
     * 删除
     * @param id 主键id
     * @return
     */
    void delete(Long id);
}
