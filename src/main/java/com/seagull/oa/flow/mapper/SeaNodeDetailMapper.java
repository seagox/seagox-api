package com.seagull.oa.flow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seagull.oa.flow.entity.SeaNodeDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 流程节点详情
 */
public interface SeaNodeDetailMapper extends BaseMapper<SeaNodeDetail> {

    /**
     * 查询节点详情
     */
    public List<Map<String, Object>> queryNodeDetail(@Param("defId") String defId, @Param("version") int version, @Param("nodeId") String nodeId);

    /**
     * 更新节点详情
     */
    public int updateNodeDetail(String defId);

    /**
     * 更新节点详情时间
     */
    public int updateNodeDetailEndTime(String defId);

    /**
     * 查询流程节点信息
     */
    public List<Map<String, Object>> queryProcessDetailByNodeName(@Param("defId") String defId, @Param("version") int version, @Param("nodeName") String nodeName);

    /**
     * 查询流程节点信息
     */
    public List<Map<String, Object>> queryProcessDetailByNodeId(@Param("defId") String defId, @Param("version") int version, @Param("nodeId") String nodeId);

    /**
     * 查询流程待办信息
     */
    public List<Map<String, Object>> selectFlowTodoList(@Param("defId") Long defId, @Param("version") Integer version, @Param("type") int type, @Param("status") int status);

}
