package com.seagox.oa.disk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seagox.oa.disk.entity.JellyDiskShare;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 他人分享 Mapper 接口
 */
public interface JellyDiskShareMapper extends BaseMapper<JellyDiskShare> {

    /**
     * 根据用户id查询
     *
     * @param companyId 公司id
     * @param userId    用户id
     * @return
     */
    List<Map<String, Object>> queryByUserId(@Param("companyId") Long companyId, @Param("userId") Long userId);

}
