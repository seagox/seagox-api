package com.seagox.oa.disk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seagox.oa.disk.entity.JellyDisk;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 网盘 Mapper 接口
 */
public interface JellyDiskMapper extends BaseMapper<JellyDisk> {

    /**
     * 根据用户id查询
     *
     * @param companyId 公司id
     * @param userId    用户id
     * @param parentId  单位id
     * @param name      名称
     * @param status    状态
     * @param typeList  类型数组
     * @return
     */
    List<Map<String, Object>> queryByUserId(@Param("companyId") Long companyId, @Param("userId") Long userId, @Param("parentId") Long parentId, @Param("name") String name,
                                            @Param("status") Integer status, @Param("typeList") List<Integer> typeList);
}
