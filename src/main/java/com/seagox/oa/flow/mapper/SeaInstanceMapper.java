package com.seagox.oa.flow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seagox.oa.flow.entity.SeaInstance;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 流程定义
 */
public interface SeaInstanceMapper extends BaseMapper<SeaInstance> {

    /**
     * 查询历史处理意见
     */
    public List<Map<String, Object>> queryHistoryOpinion(@Param("businessType") String businessType, @Param("businessKey") String businessKey);

    /**
     * 待办事项
     */
    public List<Map<String, Object>> queryTodoItem(@Param("prefix") String prefix, @Param("userId") String userId, @Param("formId") Long formId);

    /**
     * 已办事项
     */
    public List<Map<String, Object>> queryDoneItem(@Param("prefix") String prefix, @Param("userId") String userId);

    /**
     * 抄送事项
     */
    public List<Map<String, Object>> queryCopyItem(@Param("prefix") String prefix, @Param("userId") String userId);

    /**
     * 我发起的
     */
    public List<Map<String, Object>> querySelfItem(@Param("prefix") String prefix, @Param("userId") Long userId, @Param("formId") Long formId);

    /**
     * 待发事项
     */
    public List<Map<String, Object>> queryReadyItem(@Param("companyId") Long companyId, @Param("userId") String userId);

    /**
     * 我的工作: 待办事项
     */
    public List<Map<String, Object>> queryWorkTodoItem(@Param("prefix") String prefix, @Param("userId") String userId, @Param("name") String name,
                                                       @Param("statusStr") String statusStr, @Param("businessTypeStr") String businessTypeStr,
                                                       @Param("departmentIds") String departmentIds);

    /**
     * 我的工作: 待发事项
     */
    public List<Map<String, Object>> queryWorkReadyItem(@Param("companyId") Long companyId, @Param("userId") Long userId, @Param("title") String title);

    /**
     * 我的工作: 已办事项
     */
    public List<Map<String, Object>> queryWorkDoneItem(@Param("prefix") String prefix, @Param("userId") String userId, @Param("name") String name);

    /**
     * 我的工作: 抄送事项
     */
    public List<Map<String, Object>> queryWorkCopyItem(@Param("prefix") String prefix, @Param("userId") String userId, @Param("name") String name);

    /**
     * 我的工作: 我发起的
     */
    public List<Map<String, Object>> queryWorkSelfItem(@Param("prefix") String prefix, @Param("userId") Long userId, @Param("name") String name);

    /**
     * 删除流程记录
     */
    public void deleteProcess(@Param("businessType") String businessType, @Param("businessKey") String businessKey);

}
