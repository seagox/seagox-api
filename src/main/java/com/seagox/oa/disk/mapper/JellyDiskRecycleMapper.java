package com.seagox.oa.disk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seagox.oa.disk.entity.JellyDiskRecycle;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 回收站 Mapper 接口
 */
public interface JellyDiskRecycleMapper extends BaseMapper<JellyDiskRecycle> {

    /**
     * 根据用户id查询
     *
     * @param companyId 公司id
     * @param userId    用户id
     * @return
     */
    List<Map<String, Object>> queryByUserId(@Param("companyId") Long companyId, @Param("userId") Long userId);
}
