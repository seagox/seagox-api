package com.seagull.oa.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seagull.oa.sys.entity.SysMessage;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 消息
 */
public interface SysMessageMapper extends BaseMapper<SysMessage> {

    /**
     * 查询所有
     */
    public List<Map<String, Object>> queryAll(@Param("prefix") String prefix, @Param("toUserId") long toUserId, @Param("status") Integer status, @Param("title") String title);

    /**
     * 查询所有数量
     */
    public int queryCount(@Param("prefix") String prefix, @Param("toUserId") long toUserId);

    /**
     * 删除消息记录
     */
    public void deleteMessage(@Param("businessType") Long businessType, @Param("businessKey") Long businessKey);

}
