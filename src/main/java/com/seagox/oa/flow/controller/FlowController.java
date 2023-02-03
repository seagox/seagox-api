package com.seagox.oa.flow.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.seagox.oa.common.ResultCode;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyForm;
import com.seagox.oa.excel.mapper.JellyFormMapper;
import com.seagox.oa.flow.entity.SeaInstance;
import com.seagox.oa.flow.mapper.SeaDefinitionMapper;
import com.seagox.oa.flow.mapper.SeaInstanceMapper;
import com.seagox.oa.flow.service.IRuntimeService;
import com.seagox.oa.sys.entity.SysAccount;
import com.seagox.oa.sys.mapper.SysAccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 工作流
 */
@RestController
@RequestMapping("/flow")
public class FlowController {

    @Autowired
    private IRuntimeService runtimeService;

    @Autowired
    private SysAccountMapper userMapper;

    @Autowired
    private JellyFormMapper formMapper;

    @Autowired
    private SeaDefinitionMapper seaDefinitionMapper;

    @Autowired
    private SeaInstanceMapper seaProcdefMapper;

    /**
     * 启动流程
     */
    @PostMapping("/startProcess")
    public ResultData startProcess(HttpServletRequest request) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("companyId", request.getParameter("companyId"));
        variables.put("userId", request.getParameter("userId"));
        SysAccount user = userMapper.selectById(request.getParameter("userId"));
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        JellyForm form = formMapper.selectById(request.getParameter("businessType"));
        variables.put("name", form.getName() + "(" + user.getName() + " " + sdf.format(date) + ")");
        variables.put("resource", seaDefinitionMapper.selectById(form.getFlowId()).getResources());
        variables.put("businessType", request.getParameter("businessType"));
        variables.put("businessKey", request.getParameter("businessKey"));
        LambdaQueryWrapper<SeaInstance> qw = new LambdaQueryWrapper<>();
        qw.eq(SeaInstance::getBusinessType, request.getParameter("businessType"))
                .eq(SeaInstance::getBusinessKey, request.getParameter("businessKey"));
        SeaInstance seaInstance = seaProcdefMapper.selectOne(qw);
        if (seaInstance == null) {
            runtimeService.startProcess(variables, request);
            return ResultData.success(null);
        } else {
            if (seaInstance.getStatus() == 1) {
                runtimeService.restartProcess(variables, request);
                return ResultData.success(null);
            } else {
                return ResultData.warn(ResultCode.OTHER_ERROR, "审批中，不可再次提交");
            }
        }
    }

    /**
     * 撤回
     */
    @PostMapping("/revoke")
    public ResultData revoke(String businessType, String businessKey, String reason) {
        runtimeService.revoke(businessType, businessKey, reason);
        return ResultData.success(null);
    }

    /**
     * 弃审
     */
    @PostMapping("/abandon")
    public ResultData abandon(Long userId, String businessType, String businessKey) {
        runtimeService.abandon(userId, businessType, businessKey);
        return ResultData.success(null);
    }

    /**
     * 查询节点详情
     */
    @PostMapping("/queryNodeDetail")
    public ResultData queryNodeDetail(Long companyId, Long userId,
                                      Integer type, String businessType,
                                      String businessKey, String nodeId) {
        return runtimeService.queryNodeDetail(companyId, userId, type, businessType, businessKey, nodeId);
    }

    /**
     * 查询流程详情
     */
    @PostMapping("/queryProcessDetail")
    public ResultData queryProcessDetail(String businessType, String businessKey, String nodeName) {
        return runtimeService.queryProcessDetail(businessType, businessKey, nodeName);
    }

    /**
     * 待办事项
     */
    @GetMapping("/queryTodoItem")
    public ResultData queryTodoItem(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                    @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, Long companyId, String userId, Long formId) {
        return runtimeService.queryTodoItem(pageNo, pageSize, companyId, userId, formId);
    }

    /**
     * 已办事项
     */
    @GetMapping("/queryDoneItem")
    public ResultData queryDoneItem(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                    @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, Long companyId, Long userId) {
        return runtimeService.queryDoneItem(pageNo, pageSize, companyId, userId);
    }

    /**
     * 抄送事项
     */
    @GetMapping("/queryCopyItem")
    public ResultData queryCopyItem(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                    @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, Long companyId, Long userId) {
        return runtimeService.queryCopyItem(pageNo, pageSize, companyId, userId);
    }

    /**
     * 我发起的
     */
    @GetMapping("/querySelfItem")
    public ResultData querySelfItem(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                    @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, Long companyId, Long userId, Long formId) {
        return runtimeService.querySelfItem(pageNo, pageSize, companyId, userId, formId);
    }

    /**
     * 待发事项
     */
    @GetMapping("/queryReadyItem")
    public ResultData queryReadyItem(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                     @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, Long companyId, Long userId) {
        return runtimeService.queryReadyItem(pageNo, pageSize, companyId, userId);
    }

    /**
     * 更新抄送状态
     */
    @PostMapping("/updateNotifier/{id}")
    public ResultData updateNotifier(@PathVariable Long id) {
        return runtimeService.updateNotifier(id);
    }

    /**
     * 关闭
     */
    @PostMapping("/close")
    public ResultData close(Long userId, String businessType, String businessKeys, String reason) {
        return runtimeService.close(userId, businessType, businessKeys, reason);
    }

    /**
     * 激活
     */
    @PostMapping("/open")
    public ResultData open(Long userId, String businessType, String businessKeys) {
        return runtimeService.open(userId, businessType, businessKeys);
    }

    /**
     * 流程信息
     */
    @PostMapping("/flowInfo")
    public ResultData flowInfo(HttpServletRequest request) {
        LambdaQueryWrapper<SeaInstance> qw = new LambdaQueryWrapper<>();
        qw.eq(SeaInstance::getBusinessType, request.getParameter("businessType"))
                .eq(SeaInstance::getBusinessKey, request.getParameter("businessKey"));
        SeaInstance seaInstance = seaProcdefMapper.selectOne(qw);
        if (seaInstance != null) {
            return ResultData.success(seaInstance);
        } else {
            return ResultData.warn(ResultCode.OTHER_ERROR, "无流程信息");
        }
    }

    /**
     * 待办事项分页查询
     *
     * @param pageNo          起始页
     * @param pageSize        每页大小
     * @param companyId       单位id
     * @param userId          用户id
     * @param name            流程名称
     * @param statusStr       状态字符串
     * @param businessTypeStr 流程类型字符串
     * @param departmentIds   部门id字符串
     * @return
     */
    @GetMapping("/queryTodoItemByPage")
    public ResultData queryTodoItemByPage(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                          @RequestParam(value = "pageSize", defaultValue = "15") Integer pageSize, Long companyId, Long userId, String name, String statusStr, String businessTypeStr, String departmentIds) {
        return runtimeService.queryWorkTodoItem(companyId, userId, name, statusStr, businessTypeStr, departmentIds, pageNo, pageSize);
    }

    /**
     * 待发事项分页查询
     *
     * @param pageNo    起始页
     * @param pageSize  每页大小
     * @param companyId 单位id
     * @param userId    用户id
     * @param title     标题
     * @return
     */
    @GetMapping("/queryReadyItemByPage")
    public ResultData queryReadyItem(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                     @RequestParam(value = "pageSize", defaultValue = "15") Integer pageSize, Long companyId, Long userId, String title) {
        return runtimeService.queryWorkReadyItem(companyId, userId, title, pageNo, pageSize);
    }

    /**
     * 已办事项分页查询
     *
     * @param pageNo    起始页
     * @param pageSize  每页大小
     * @param companyId 单位id
     * @param userId    用户id
     * @param name      流程名称
     * @return
     */
    @GetMapping("/queryDoneItemByPage")
    public ResultData queryDoneItemByPage(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                          @RequestParam(value = "pageSize", defaultValue = "15") Integer pageSize, Long companyId, Long userId, String name) {
        return runtimeService.queryWorkDoneItem(companyId, userId, name, pageNo, pageSize);
    }

    /**
     * 抄送事项分页查询
     *
     * @param pageNo    起始页
     * @param pageSize  每页大小
     * @param companyId 单位id
     * @param userId    用户id
     * @param name      流程名称
     * @return
     */
    @GetMapping("/queryCopyItemByPage")
    public ResultData queryCopyItemByPage(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                          @RequestParam(value = "pageSize", defaultValue = "15") Integer pageSize, Long companyId, Long userId, String name) {
        return runtimeService.queryWorkCopyItem(companyId, userId, name, pageNo, pageSize);
    }

    /**
     * 我发起的分页查询
     *
     * @param pageNo    起始页
     * @param pageSize  每页大小
     * @param companyId 单位id
     * @param userId    用户id
     * @param name      流程名称
     * @return
     */
    @GetMapping("/querySelfItemByPage")
    public ResultData querySelfItemByPage(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                          @RequestParam(value = "pageSize", defaultValue = "15") Integer pageSize, Long companyId, Long userId, String name) {
        return runtimeService.queryWorkSelfItem(companyId, userId, name, pageNo, pageSize);
    }

    /**
     * 查询流程待办详情
     */
    @GetMapping("/queryFlowTodoList")
    public ResultData queryFlowTodoList(String businessType, String businessKey){
        return runtimeService.queryFlowTodoList(businessType, businessKey);
    }

}
