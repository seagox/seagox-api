package com.seagox.oa.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seagox.oa.sys.entity.SysNotice;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 公告 Mapper 接口
 */
public interface NoticeMapper extends BaseMapper<SysNotice> {

    /**
     * 根据id查询
     *
     * @param id 主键id
     * @return
     */
    Map<String, Object> queryById(@Param("id") Long id);

    /**
     * 查询所有
     */
    List<Map<String, Object>> queryAll(@Param("prefix") String prefix, @Param("userId") Long userId, @Param("status") Integer status, @Param("title") String title);

    /**
     * 查询已发/接收通知
     */
    List<Map<String, Object>> querySendOrRec(@Param("companyId") Long companyId, @Param("userId") Long userId);

    /**
     * 查询通知类型字典
     */
    List<Map<String, Object>> queryType();

    /**
     * 查询通过ids
     */
    List<Map<String, Object>> queryByIds(@Param("relationIdAry") String[] relationIdAry);

    /**
     * 查询通知是否被关联
     */
    Integer selectByRelation(@Param("id") String id);

}
