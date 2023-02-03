package com.seagull.oa.flow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seagull.oa.flow.entity.SeaNode;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 流程节点
 */
public interface SeaNodeMapper extends BaseMapper<SeaNode> {

    /**
     * 查询当前节点
     */
    public Map<String, Object> queryCurrentNodeDetail(@Param("defId") String defId, @Param("version") int version, @Param("assignee") String assignee);

    /**
     * 查询是否可撤回
     */
    public int queryApproverCount(@Param("defId") String defId, @Param("version") int version);


    /**
     * 查询审核人当前节点
     */
    public Map<String, Object> queryCurrentNodeByAssignee(@Param("defId") Long defId, @Param("assignee") String assignee);

    /**
     * 根据下一节点
     */
    public List<Map<String, Object>> queryNextNode(@Param("id") Long id);

    /**
     * 查询是否已处理
     */
    public int queryHandleCount(@Param("nodeId") Long nodeId);

}
