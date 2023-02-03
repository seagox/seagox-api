package com.seagox.oa.flow.service;

import com.alibaba.fastjson.JSONObject;
import com.seagox.oa.common.ResultData;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface IRuntimeService {

    /**
     * 启动流程
     */
    public void startProcess(Map<String, Object> variables, HttpServletRequest request);

    /**
     * 重启流程
     */
    public void restartProcess(Map<String, Object> variables, HttpServletRequest request);

    /**
     * 办理
     */
    public void complete(Map<String, Object> variables, HttpServletRequest request);

    /**
     * 撤回
     */
    public void revoke(String businessType, String businessKey, String reason);

    /**
     * 弃审
     */
    public void abandon(Long assignee, String businessType, String businessKey);

    /**
     * 查询节点详情
     */
    public ResultData queryNodeDetail(
            Long companyId, Long userId,
            Integer type, String businessType,
            String businessKey, String nodeId);

    /**
     * 查询流程详情
     */
    public ResultData queryProcessDetail(String businessType, String businessKey, String nodeName);

    /**
     * 待办事项
     */
    public ResultData queryTodoItem(Integer pageNo, Integer pageSize, Long companyId, String userId, Long formId);

    /**
     * 跟踪事项
     */
    public ResultData queryDoneItem(Integer pageNo, Integer pageSize, Long companyId, Long userId);

    /**
     * 抄送事项
     */
    public ResultData queryCopyItem(Integer pageNo, Integer pageSize, Long companyId, Long userId);

    /**
     * 我发起的
     */
    public ResultData querySelfItem(Integer pageNo, Integer pageSize, Long companyId, Long userId, Long formId);

    /**
     * 待发事项
     */
    public ResultData queryReadyItem(Integer pageNo, Integer pageSize, Long companyId, Long userId);

    /**
     * 更新抄送状态
     */
    public ResultData updateNotifier(@PathVariable Long id);

    /**
     * 获取节点操作权限
     *
     * @param resource 流程json
     * @param nodeId   节点id
     */
    public String getOperationAuthority(JSONObject jsonObject, String nodeId);

    /**
     * 关闭
     */
    public ResultData close(Long userId, String businessType, String businessKeys, String reason);

    /**
     * 激活
     */
    public ResultData open(Long userId, String businessType, String businessKeys);

    /**
     * 待办事项
     */
    public ResultData queryWorkTodoItem(Long companyId, Long userId, String name, String statusStr, String businessTypeStr, String departmentIds, int pageNo, int pageSize);

    /**
     * 待发事项
     */
    public ResultData queryWorkReadyItem(Long companyId, Long userId, String title, int pageNo, int pageSize);

    /**
     * 已办事项
     */
    public ResultData queryWorkDoneItem(Long companyId, Long userId, String name, int pageNo, int pageSize);

    /**
     * 抄送事项
     */
    public ResultData queryWorkCopyItem(Long companyId, Long userId, String name, int pageNo, int pageSize);

    /**
     * 我发起的
     */
    public ResultData queryWorkSelfItem(Long companyId, Long userId, String name, int pageNo, int pageSize);

    /**
     * 查询流程待办详情
     */
    public ResultData queryFlowTodoList(String businessType, String businessKey);

}
