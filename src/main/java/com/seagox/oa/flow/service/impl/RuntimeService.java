package com.seagox.oa.flow.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seagox.oa.common.ResultCode;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.exception.FlowException;
import com.seagox.oa.exception.FlowManualSelectionException;
import com.seagox.oa.exception.FlowOptionalException;
import com.seagox.oa.exception.FormulaException;
import com.seagox.oa.flow.entity.SeaInstance;
import com.seagox.oa.flow.entity.SeaNode;
import com.seagox.oa.flow.entity.SeaNodeDetail;
import com.seagox.oa.flow.mapper.SeaInstanceMapper;
import com.seagox.oa.flow.mapper.SeaNodeDetailMapper;
import com.seagox.oa.flow.mapper.SeaNodeMapper;
import com.seagox.oa.flow.service.IRuntimeService;
import com.seagox.oa.sys.entity.*;
import com.seagox.oa.sys.mapper.*;
import com.seagox.oa.util.FormulaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class RuntimeService extends ServiceImpl<SeaNodeDetailMapper, SeaNodeDetail> implements IRuntimeService {

    @Autowired
    private SeaInstanceMapper seaInstanceMapper;

    @Autowired
    private SeaNodeMapper seaNodeMapper;

    @Autowired
    private SeaNodeDetailMapper seaNodeDetailMapper;

    @Autowired
    private SysAccountMapper userMapper;

    @Autowired
    private UserRelateMapper userRelateMapper;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 启动流程
     *
     * @param companyId    公司id
     * @param userId       用户id
     * @param name         名称
     * @param resource     流程文件
     * @param businessType 业务类型
     * @param businessKey  业务唯一值
     */
    @Transactional
    public void startProcess(Map<String, Object> variables, HttpServletRequest request) {
        // 流程实例
        if (StringUtils.isEmpty(variables.get("resource"))) {
            throw new FlowException("流程未设置");
        }
        SeaInstance seaInstance = new SeaInstance();
        seaInstance.setCompanyId(Long.valueOf(variables.get("companyId").toString()));
        seaInstance.setUserId(Long.valueOf(variables.get("userId").toString()));
        seaInstance.setName(variables.get("title").toString());
        seaInstance.setResources(variables.get("resource").toString());
        seaInstance.setBusinessType(variables.get("businessType").toString());
        seaInstance.setBusinessKey(variables.get("businessKey").toString());
        seaInstanceMapper.insert(seaInstance);

        variables.put("defId", seaInstance.getId());
        variables.put("version", 1);
        // 发起人
        variables.put("sponsorCompany", variables.get("companyId"));
        variables.put("sponsor", variables.get("userId"));
        // 当前节点id
        variables.put("currentNodeId", "start");
        variables.put("source", "start");
        variables.put("isConcurrent", 0);

        JSONObject resourceJson = JSONObject.parseObject(variables.get("resource").toString());
        JSONObject nodeMap = resourceJson.getJSONObject("nodes");
        JSONArray edges = resourceJson.getJSONArray("edges");
        JSONObject nodeLastMap = new JSONObject();
        JSONObject nodeNextMap = new JSONObject();
        for (int i = 0; i < edges.size(); i++) {
            JSONObject edge = edges.getJSONObject(i);
            // 上个节点
            if (nodeLastMap.containsKey(edge.getString("target"))) {
                JSONArray source = nodeLastMap.getJSONArray(edge.getString("target"));
                source.add(edge.getString("source"));
                nodeLastMap.put(edge.getString("target"), source);
            } else {
                JSONArray source = new JSONArray();
                source.add(edge.getString("source"));
                nodeLastMap.put(edge.getString("target"), source);
            }
            // 下个节点
            if (nodeNextMap.containsKey(edge.getString("source"))) {
                JSONArray target = nodeNextMap.getJSONArray(edge.getString("source"));
                target.add(edge.getString("target"));
                nodeNextMap.put(edge.getString("source"), target);
            } else {
                JSONArray target = new JSONArray();
                target.add(edge.getString("target"));
                nodeNextMap.put(edge.getString("source"), target);
            }
        }
        JSONObject jsonObject = excuteNext(nodeMap, nodeLastMap, nodeNextMap, variables, request);
        jsonObject.put("edges", edges);
        seaInstance.setResources(jsonObject.toJSONString());
        if (variables.containsKey("flowFlag") && (boolean) variables.get("flowFlag")) {
            seaInstance.setStatus(1);
            seaInstance.setEndTime(new Date());
        }
        if (StringUtils.isEmpty(variables.get("currentAgent"))) {
            seaInstance.setCurrentAgent("");
        } else {
            seaInstance.setCurrentAgent(variables.get("currentAgent").toString());
        }
        seaInstanceMapper.updateById(seaInstance);
    }

    /**
     * 流程执行
     *
     * @param jsonObject 节点信息
     * @param resource   流程文件
     * @param companyId  公司id
     * @param userId     用户id
     */
    public JSONObject excuteNext(JSONObject nodeMap, JSONObject nodeLastMap, JSONObject nodeNextMap, Map<String, Object> variables,
                                 HttpServletRequest request) {
        JSONObject resourceJson = new JSONObject();
        String currentNodeId = variables.get("currentNodeId").toString();
        // 下个节点信息
        JSONArray nextNode = nodeNextMap.getJSONArray(currentNodeId);
        for (int i = 0; i < nextNode.size(); i++) {
            String nextNodeId = nextNode.getString(i);
            JSONObject nextNodeInfo = nodeMap.getJSONObject(nextNodeId);
            if (nextNodeInfo.getString("type").equals("approver")) {
                // 流程自选判断
                JSONArray manualSelectionAry = nodeLastMap.getJSONArray(nextNodeId);
                boolean manualSelectionFlag = false;
                for (int j = 0; j < manualSelectionAry.size(); j++) {
                    JSONObject manualSelectionObj = nodeMap.getJSONObject(manualSelectionAry.getString(j));
                    if (manualSelectionObj.getString("type").equals("manualSelection")) {
                        manualSelectionFlag = true;
                        List<String> assigneeSetas = Arrays.asList(variables.get("flowOptionalList").toString().split(","));
                        if (assigneeSetas.contains(nextNodeId)) {
                            // 审批节点
                            List<String> nodeUserList = nodeHandel(nextNodeInfo, variables.get("sponsorCompany").toString(),
                                    variables.get("sponsor").toString(), variables.get("approverOptionalList"));
                            if (nodeUserList.size() == 0) {
                                // 空节点，继续走流程
                                // 下个节点id
                                // 设置为已完成
                                nextNodeInfo.put("status", 1);
                                variables.put("currentNodeId", nextNodeId);
                                excuteNext(nodeMap, nodeLastMap, nodeNextMap, variables, request);
                            } else {
                                // 设置为待办
                                nextNodeInfo.put("status", 0);
                                variables.put("nodeName", nextNodeInfo.getString("label"));
                                variables.put("type", 1);
                                variables.put("target", nextNodeInfo.getString("id"));

                                saveNode(variables, nodeUserList);
                            }
                        }
                    }
                }
                if (!manualSelectionFlag) {
                    // 审批节点
                    List<String> nodeUserList = nodeHandel(nextNodeInfo, variables.get("sponsorCompany").toString(),
                            variables.get("sponsor").toString(), variables.get("approverOptionalList"));
                    if (nodeUserList.size() == 0) {
                        // 空节点，继续走流程
                        // 下个节点id
                        // 设置为已完成
                        nextNodeInfo.put("status", 1);
                        variables.put("currentNodeId", nextNodeId);
                        excuteNext(nodeMap, nodeLastMap, nodeNextMap, variables, request);
                    } else {
                        // 设置为待办
                        nextNodeInfo.put("status", 0);
                        variables.put("nodeName", nextNodeInfo.getString("label"));
                        variables.put("type", 1);
                        variables.put("target", nextNodeInfo.getString("id"));

                        saveNode(variables, nodeUserList);
                    }
                }
            } else if (nextNodeInfo.getString("type").equals("notifier")) {
                // 抄送节点
                // 设置为已完成
                nextNodeInfo.put("status", 1);
                List<String> nodeUserList = nodeHandel(nextNodeInfo, variables.get("sponsorCompany").toString(),
                        variables.get("sponsor").toString(), variables.get("approverOptionalList"));
                if (nodeUserList.size() != 0) {
                    variables.put("nodeName", nextNodeInfo.getString("label"));
                    variables.put("type", 2);
                    variables.put("target", nextNodeInfo.getString("id"));

                    saveNode(variables, nodeUserList);
                }
                // 继续走流程
                variables.put("currentNodeId", nextNodeId);
                excuteNext(nodeMap, nodeLastMap, nodeNextMap, variables, request);
            } else if (nextNodeInfo.getString("type").equals("condition")) {
                // 条件分支
                boolean result = false;
                if (!StringUtils.isEmpty(nextNodeInfo.getString("condition"))) {
                    variables.put("condition", nextNodeInfo.getString("condition"));
                    result = branchCondition(variables, request);
                } else {
                    // 条件为空不处理
                }
                if (result) {
                    // 设置为已完成
                    nextNodeInfo.put("status", 1);
                    // 继续走流程
                    variables.put("currentNodeId", nextNodeId);
                    excuteNext(nodeMap, nodeLastMap, nodeNextMap, variables, request);
                }
            } else if (nextNodeInfo.getString("type").equals("exclusiveGateway")) {
                // 排他网关

            } else if (nextNodeInfo.getString("type").equals("parallelGateWay")) {
                // 并行网关
                // 设置为已完成
                nextNodeInfo.put("status", 1);
                if (variables.get("isConcurrent").toString().equals("0")) {
                    variables.put("isConcurrent", 1);
                } else {
                    variables.put("isConcurrent", 0);
                }
                // 继续走流程
                variables.put("currentNodeId", nextNodeId);
                excuteNext(nodeMap, nodeLastMap, nodeNextMap, variables, request);
            } else if (nextNodeInfo.getString("type").equals("manualSelection")) {
                // 手动选择
                // 设置为已完成
                nextNodeInfo.put("status", 1);
                if (!variables.get("isConcurrent").toString().equals("0")) {
                    variables.put("isConcurrent", 1);
                } else {
                    variables.put("isConcurrent", 0);
                }
                // 继续走流程
                variables.put("currentNodeId", nextNodeId);
                if (StringUtils.isEmpty(variables.get("flowOptionalList"))) {
                    JSONArray manualSelectionResult = new JSONArray();
                    JSONArray manualSelectionAry = nodeNextMap.getJSONArray(nextNodeId);
                    for (int j = 0; j < manualSelectionAry.size(); j++) {
                        manualSelectionResult.add(nodeMap.getJSONObject(manualSelectionAry.getString(j)));
                    }
                    throw new FlowManualSelectionException(manualSelectionResult.toJSONString());
                } else {
                    excuteNext(nodeMap, nodeLastMap, nodeNextMap, variables, request);
                }
            } else if (nextNodeInfo.getString("type").equals("end")) {
                // 流程结束
                nextNodeInfo.put("status", 1);
                variables.put("flowFlag", true);
            }
        }
        resourceJson.put("nodes", nodeMap);
        return resourceJson;
    }

    /**
     * 流程执行
     *
     * @param jsonObject 节点信息
     * @param resource   流程文件
     * @param companyId  公司id
     * @param userId     用户id
     */
    public JSONObject excuteCurrent(JSONObject nodeMap, JSONObject nodeLastMap, JSONObject nodeNextMap, Map<String, Object> variables,
                                    HttpServletRequest request) {
        JSONObject resourceJson = new JSONObject();
        // 当前节点信息
        JSONObject currentNode = nodeMap.getJSONObject(variables.get("currentNodeId").toString());
        String type = currentNode.getString("type");
        if (type.equals("approver")) {
            // 审批节点
            List<String> nodeUserList = nodeHandel(currentNode, variables.get("sponsorCompany").toString(),
                    variables.get("sponsor").toString(), variables.get("approverOptionalList"));
            if (nodeUserList.size() == 0) {
                // 空节点，继续走流程
                // 下个节点id
                // 设置为已完成
                currentNode.put("status", 1);
                excuteNext(nodeMap, nodeLastMap, nodeNextMap, variables, request);
            } else {
                // 设置为待办
                currentNode.put("status", 0);
                variables.put("nodeName", currentNode.getString("label"));
                variables.put("type", 1);
                variables.put("target", currentNode.getString("id"));

                saveNode(variables, nodeUserList);
            }
        } else if (type.equals("notifier")) {
            // 抄送节点
            // 设置为已完成
            currentNode.put("status", 1);
            List<String> nodeUserList = nodeHandel(currentNode, variables.get("sponsorCompany").toString(),
                    variables.get("sponsor").toString(), variables.get("approverOptionalList"));
            if (nodeUserList.size() != 0) {
                variables.put("nodeName", currentNode.getString("label"));
                variables.put("type", 2);
                variables.put("target", currentNode.getString("id"));

                saveNode(variables, nodeUserList);
            }
            // 继续走流程
            excuteNext(nodeMap, nodeLastMap, nodeNextMap, variables, request);
        } else if (type.equals("condition")) {
            // 条件分支
            boolean result = false;
            if (!StringUtils.isEmpty(currentNode.getString("condition"))) {
                variables.put("condition", currentNode.getString("condition"));
                result = branchCondition(variables, request);
            } else {
                // 条件为空不处理
            }
            if (result) {
                // 设置为已完成
                currentNode.put("status", 1);
                // 继续走流程
                excuteNext(nodeMap, nodeLastMap, nodeNextMap, variables, request);
            }
        } else if (type.equals("exclusiveGateway")) {
            // 排他网关

        } else if (type.equals("parallelGateWay")) {
            // 并行网关
            if (variables.get("isConcurrent").toString().equals("0")) {
                variables.put("isConcurrent", 1);
            } else {
                variables.put("isConcurrent", 0);
            }
            // 设置为已完成
            currentNode.put("status", 1);
            // 继续走流程
            excuteNext(nodeMap, nodeLastMap, nodeNextMap, variables, request);
        } else if (type.equals("manualSelection")) {
            // 手动选择
            // 设置为已完成
            currentNode.put("status", 1);
            if (variables.get("isConcurrent").toString().equals("0")) {
                variables.put("isConcurrent", 1);
            } else {
                variables.put("isConcurrent", 0);
            }
            // 继续走流程
            if (StringUtils.isEmpty(variables.get("flowOptionalList"))) {
                JSONArray manualSelectionResult = new JSONArray();
                JSONArray manualSelectionAry = nodeNextMap.getJSONArray(currentNode.getString("id"));
                for (int j = 0; j < manualSelectionAry.size(); j++) {
                    manualSelectionResult.add(nodeMap.getJSONObject(manualSelectionAry.getString(j)));
                }
                throw new FlowManualSelectionException(manualSelectionResult.toJSONString());
            } else {
                excuteNext(nodeMap, nodeLastMap, nodeNextMap, variables, request);
            }
        } else if (type.equals("end")) {
            // 流程结束
            currentNode.put("status", 1);
            variables.put("flowFlag", true);
        }
        resourceJson.put("nodes", nodeMap);
        return resourceJson;
    }

    /**
     * 获取节点审批人
     *
     * @param nextNodeInfo         节点信息
     * @param sponsorCompany       发起人公司
     * @param sponsor              发起人
     * @param approverOptionalList 审批人自选用户字符串
     * @throws FlowOptionalException
     */
    public List<String> nodeHandel(JSONObject nextNodeInfo, String sponsorCompany, String sponsor,
                                   Object approverOptionalList) {
        List<String> assigneeSet = new ArrayList<>();
        int type = nextNodeInfo.getIntValue("approverType");
        if (type == 1 || type == 2 || type == 6) {
            JSONArray assigneeArray = nextNodeInfo.getJSONArray("value");
            for (int i = 0; i < assigneeArray.size(); i++) {
                if (type == 1) {
                    // 指定成员
                    JSONObject assigneeObject = assigneeArray.getJSONObject(i);
                    assigneeSet.add(assigneeObject.getString("id"));
                } else if (type == 2) {
                    // 角色
                    List<String> userList = userRelateMapper.queryUserByRoleId(assigneeArray.getLong(i));
                    for (int l = 0; l < userList.size(); l++) {
                        assigneeSet.add(userList.get(l));
                    }
                } else if (type == 6) {
                    // 审批人自选
                    JSONObject assigneeObject = assigneeArray.getJSONObject(i);
                    assigneeSet.add(assigneeObject.getString("id"));
                }
            }
            if (type == 6 && assigneeSet.size() > 1) {
                // 审批人自选
                if (StringUtils.isEmpty(approverOptionalList)) {
                    throw new FlowOptionalException(assigneeArray.toJSONString());
                } else {
                    assigneeSet = Arrays.asList(approverOptionalList.toString().split(","));
                }
            }
        } else if (type == 3) {
            // 部门主管
            LambdaQueryWrapper<SysUserRelate> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysUserRelate::getCompanyId, sponsorCompany).eq(SysUserRelate::getUserId, sponsor);
            SysUserRelate userRelate = userRelateMapper.selectOne(queryWrapper);
            SysDepartment department = departmentMapper.selectById(userRelate.getDepartmentId());
            if (department != null) {
                boolean directorFlag = false;
                if (!StringUtils.isEmpty(department.getDirector())) {
                    String[] directorArray = department.getDirector().split(",");
                    directorFlag = Arrays.asList(directorArray).contains(sponsor);
                }
                int endIndex = department.getCode().length()
                        - (nextNodeInfo.getIntValue("value") - (directorFlag ? 0 : 1)) * 4;
                if (endIndex > 0) {
                    LambdaQueryWrapper<SysDepartment> departmentQueryWrapper = new LambdaQueryWrapper<>();
                    departmentQueryWrapper.eq(SysDepartment::getCompanyId, sponsorCompany).eq(SysDepartment::getCode,
                            department.getCode().substring(0, endIndex));
                    SysDepartment targetDepartment = departmentMapper.selectOne(departmentQueryWrapper);
                    if (!StringUtils.isEmpty(targetDepartment.getDirector())) {
                        String[] targetDirector = targetDepartment.getDirector().split(",");
                        for (int l = 0; l < targetDirector.length; l++) {
                            assigneeSet.add(targetDirector[l]);
                        }
                    }
                }
            }
        } else if (type == 4) {
            // 分管领导
            LambdaQueryWrapper<SysUserRelate> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysUserRelate::getCompanyId, sponsorCompany).eq(SysUserRelate::getUserId, sponsor);
            SysUserRelate userRelate = userRelateMapper.selectOne(queryWrapper);
            SysDepartment department = departmentMapper.selectById(userRelate.getDepartmentId());
            if (department != null) {
                boolean chargeLeaderFlag = false;
                if (!StringUtils.isEmpty(department.getChargeLeader())) {
                    String[] chargeLeaderArray = department.getChargeLeader().split(",");
                    chargeLeaderFlag = Arrays.asList(chargeLeaderArray).contains(sponsor);
                }
                int endIndex = department.getCode().length()
                        - (nextNodeInfo.getIntValue("value") - (chargeLeaderFlag ? 0 : 1)) * 4;
                if (endIndex > 0) {
                    LambdaQueryWrapper<SysDepartment> departmentQueryWrapper = new LambdaQueryWrapper<>();
                    departmentQueryWrapper.eq(SysDepartment::getCompanyId, sponsorCompany).eq(SysDepartment::getCode,
                            department.getCode().substring(0, endIndex));
                    SysDepartment targetDepartment = departmentMapper.selectOne(departmentQueryWrapper);
                    if (!StringUtils.isEmpty(targetDepartment.getChargeLeader())) {
                        String[] targetChargeLeader = targetDepartment.getChargeLeader().split(",");
                        for (int l = 0; l < targetChargeLeader.length; l++) {
                            assigneeSet.add(targetChargeLeader[l]);
                        }
                    }
                }
            }
        } else if (type == 5) {
            // 指定部门主管或分管领导
            SysDepartment department = departmentMapper.selectById(nextNodeInfo.getLong("value"));
            if (department != null) {
                if (nextNodeInfo.getIntValue("appoint") == 1) {
                    // 部门主管
                    if (!StringUtils.isEmpty(department.getDirector())) {
                        String[] directorArray = department.getDirector().split(",");
                        for (int l = 0; l < directorArray.length; l++) {
                            assigneeSet.add(directorArray[l]);
                        }
                    }
                } else if (nextNodeInfo.getIntValue("appoint") == 2) {
                    // 分管领导
                    if (!StringUtils.isEmpty(department.getChargeLeader())) {
                        String[] chargeLeaderArray = department.getChargeLeader().split(",");
                        for (int l = 0; l < chargeLeaderArray.length; l++) {
                            assigneeSet.add(chargeLeaderArray[l]);
                        }
                    }
                }
            }
        } else if (type == 7) {
            // 发起人自己
            assigneeSet.add(sponsor);
        }
        return assigneeSet;
    }

    /**
     * 保存节点
     */
    public void saveNode(Map<String, Object> variables, List<String> nodeUserList) {
        SeaNode seaNode = new SeaNode();
        seaNode.setDefId(Long.valueOf(variables.get("defId").toString()));
        seaNode.setVersion(Integer.valueOf(variables.get("version").toString()));
        seaNode.setName(variables.get("nodeName").toString());
        seaNode.setSource(variables.get("source").toString());
        seaNode.setTarget(variables.get("target").toString());
        seaNode.setType(Integer.valueOf(variables.get("type").toString()));
        seaNode.setIsConcurrent(Integer.valueOf(variables.get("isConcurrent").toString()));
        if (Integer.valueOf(variables.get("type").toString()) == 2) {
            seaNode.setStatus(1);
            seaNode.setEndTime(new Date());
        }
        seaNodeMapper.insert(seaNode);
        LambdaQueryWrapper<SysAccount> qw = new LambdaQueryWrapper<>();
        qw.in(SysAccount::getId, nodeUserList);
        List<SysAccount> list = userMapper.selectList(qw);
        List<String> currentAgentList = new ArrayList<>();
        List<SeaNodeDetail> seaNodeDetailList = new ArrayList<>();
        for (SysAccount user : list) {
            SeaNodeDetail seaNodeDetail = new SeaNodeDetail();
            seaNodeDetail.setNodeId(seaNode.getId());
            seaNodeDetail.setName(user.getName());
            seaNodeDetail.setAssignee(String.valueOf(user.getId()));
            if (variables.get("comment") != null) {
                seaNodeDetail.setRemark(variables.get("comment").toString());
            }
            seaNodeDetailList.add(seaNodeDetail);
            currentAgentList.add(user.getName());
        }
        this.saveBatch(seaNodeDetailList);
        if (currentAgentList.size() != 0) {
            if (variables.get("currentAgent") == null) {
                variables.put("currentAgent", org.apache.commons.lang.StringUtils.join(currentAgentList, ","));
            } else {
                variables.put("currentAgent", variables.get("currentAgent").toString() + "," + org.apache.commons.lang.StringUtils.join(currentAgentList, ","));
            }
        }
    }

    /**
     * 分支条件
     *
     * @param condition 条件
     */
    public boolean branchCondition(Map<String, Object> variables, HttpServletRequest request) {
        String condition = variables.get("condition").toString();
        String sponsorCompany = variables.get("sponsorCompany").toString();
        String sponsor = variables.get("sponsor").toString();

        Pattern pattern = Pattern.compile("\\$\\{(.*?)\\}");
        Matcher matcher = pattern.matcher(condition);
        List<String> special = new ArrayList<>();
        special.add("companyName");
        special.add("departmentName");
        special.add("roleName");
        while (matcher.find()) {
            String element = matcher.group();
            String field = element.substring(2, element.length() - 1);
            if (special.contains(field)) {
                if (field.equals("companyName")) {
                    // 发起人单位
                    SysCompany company = companyMapper.selectById(sponsorCompany);
                    if (company != null) {
                        condition = condition.replace(element, "'" + company.getName() + "'");
                    }
                } else if (field.equals("departmentName")) {
                    // 发起人部门
                    LambdaQueryWrapper<SysUserRelate> qw = new LambdaQueryWrapper<>();
                    qw.eq(SysUserRelate::getCompanyId, sponsorCompany).eq(SysUserRelate::getUserId, sponsor);
                    SysUserRelate userRelate = userRelateMapper.selectOne(qw);
                    if (userRelate != null) {
                        SysDepartment department = departmentMapper.selectById(userRelate.getDepartmentId());
                        if (department != null) {
                            condition = condition.replace(element, "'" + department.getName() + "'");
                        }
                    }
                } else if (field.equals("roleName")) {
                    // 发起人角色
                    LambdaQueryWrapper<SysUserRelate> userRelateQw = new LambdaQueryWrapper<>();
                    userRelateQw.eq(SysUserRelate::getCompanyId, sponsorCompany).eq(SysUserRelate::getUserId, sponsor);
                    SysUserRelate userRelate = userRelateMapper.selectOne(userRelateQw);
                    if (userRelate != null && !StringUtils.isEmpty(userRelate.getRoleIds())) {
                        LambdaQueryWrapper<SysRole> roleQw = new LambdaQueryWrapper<>();
                        roleQw.in(SysRole::getId, Arrays.asList(userRelate.getRoleIds().split(",")));
                        List<SysRole> roleList = roleMapper.selectList(roleQw);
                        for (int i = 0; i < roleList.size(); i++) {
                            try {
                                Map<String, Object> formulaParams = FormulaUtils.getAllRequestParam(request);
                                if (Boolean.valueOf(FormulaUtils.calculate(condition.replace(element, "'" + roleList.get(i).getName() + "'"), formulaParams).toString())) {
                                    return true;
                                }
                            } catch (Exception e) {
                                throw new FormulaException(e.getMessage());
                            }
                        }
                        condition = condition.replace(element, "'没有匹配角色'");
                    }
                }
            }
        }
        try {
            Map<String, Object> formulaParams = FormulaUtils.getAllRequestParam(request);
            return Boolean.valueOf(FormulaUtils.calculate(condition, formulaParams).toString());
        } catch (Exception e) {
            throw new FormulaException(e.getMessage());
        }
    }

    /**
     * 审批流程
     *
     * @param businessType 业务类型
     * @param businessKey  业务唯一值
     * @param assignee     签收人或被委托
     * @param status       状态(1:同意;2:拒绝;3:已阅;)
     * @param comment      评论
     */
    @Transactional
    @Override
    public void complete(Map<String, Object> variables, HttpServletRequest request) {
        LambdaQueryWrapper<SeaInstance> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SeaInstance::getBusinessType, variables.get("businessType"))
                .eq(SeaInstance::getBusinessKey, variables.get("businessKey"));
        SeaInstance seaInstance = seaInstanceMapper.selectOne(queryWrapper);
        if (seaInstance == null) {
            throw new FlowException("获取不到流程信息");
        }

        variables.put("defId", seaInstance.getId());
        variables.put("resource", seaInstance.getResources());
        variables.put("version", seaInstance.getVersion());
        variables.put("title", "处理了" + seaInstance.getName());

        int status = Integer.valueOf(variables.get("status").toString());

        Map<String, Object> currentNode = seaNodeMapper.queryCurrentNodeDetail(String.valueOf(seaInstance.getId()),
                seaInstance.getVersion(), variables.get("userId").toString());
        if (currentNode == null) {
            throw new FlowException("当前没有审批节点");
        } else {
            // 设置并行网关
            variables.put("isConcurrent", currentNode.get("isConcurrent"));

            JSONObject resourceJson = JSONObject.parseObject(variables.get("resource").toString());
            JSONObject nodeMap = resourceJson.getJSONObject("nodes");
            JSONArray edges = resourceJson.getJSONArray("edges");
            JSONObject nodeNextMap = new JSONObject();
            JSONObject nodeLastMap = new JSONObject();

            for (int i = 0; i < edges.size(); i++) {
                JSONObject edge = edges.getJSONObject(i);
                // 下个节点
                if (nodeNextMap.containsKey(edge.getString("source"))) {
                    JSONArray target = nodeNextMap.getJSONArray(edge.getString("source"));
                    target.add(edge.getString("target"));
                    nodeNextMap.put(edge.getString("source"), target);
                } else {
                    JSONArray target = new JSONArray();
                    target.add(edge.getString("target"));
                    nodeNextMap.put(edge.getString("source"), target);
                }
                // 上个节点
                if (nodeLastMap.containsKey(edge.getString("target"))) {
                    JSONArray source = nodeLastMap.getJSONArray(edge.getString("target"));
                    source.add(edge.getString("source"));
                    nodeLastMap.put(edge.getString("target"), source);
                } else {
                    JSONArray source = new JSONArray();
                    source.add(edge.getString("source"));
                    nodeLastMap.put(edge.getString("target"), source);
                }
            }
            // 发起人
            variables.put("sponsorCompany", seaInstance.getCompanyId());
            variables.put("sponsor", seaInstance.getUserId());
            // 更新节点详情
            SeaNodeDetail seaNodeDetail = new SeaNodeDetail();
            seaNodeDetail.setId(Long.valueOf(currentNode.get("nodeDetailId").toString()));
            seaNodeDetail.setEndTime(new Date());
            // 重新发起
            if ("start".equals(currentNode.get("target").toString())) {
                seaNodeDetail.setStatus(5);
            } else {
                seaNodeDetail.setStatus(status);
            }
            if (variables.get("comment") != null) {
                seaNodeDetail.setRemark(variables.get("comment").toString());
            }
            seaNodeDetailMapper.updateById(seaNodeDetail);
            variables.remove("comment");
            // 审批方式(或签、会签)
            int way = nodeMap.getJSONObject(currentNode.get("target").toString()).getIntValue("way");
            if (way == 0) {
                // 提交重新发起
                if (currentNode.get("target").toString().equals("start")) {
                    SeaNode seaNode = new SeaNode();
                    seaNode.setId(Long.valueOf(currentNode.get("id").toString()));
                    seaNode.setEndTime(new Date());
                    seaNode.setStatus(status);
                    seaNodeMapper.updateById(seaNode);

                    // 继续下个节点
                    variables.put("currentNodeId", currentNode.get("target"));
                    variables.put("source", currentNode.get("target"));
                    JSONObject jsonObject = excuteNext(nodeMap, nodeLastMap, nodeNextMap, variables, request);
                    jsonObject.put("edges", edges);
                    seaInstance.setResources(jsonObject.toJSONString());
                    seaInstance.setStatus(0);
                    //流程结束
                    if (variables.containsKey("flowFlag") && (boolean) variables.get("flowFlag")) {
                        seaInstance.setStatus(1);
                        seaInstance.setEndTime(new Date());
                    }
                    if (StringUtils.isEmpty(variables.get("currentAgent"))) {
                        seaInstance.setCurrentAgent("");
                    } else {
                        seaInstance.setCurrentAgent(variables.get("currentAgent").toString());
                    }
                    seaInstanceMapper.updateById(seaInstance);
                }
                return;
            }
            if (status == 1) { //审批同意
                if (way == 1 || way == 2) {
                    //查询当前节点待审批数
                    LambdaQueryWrapper<SeaNodeDetail> qw = new LambdaQueryWrapper<>();
                    qw.eq(SeaNodeDetail::getNodeId, currentNode.get("id")).eq(SeaNodeDetail::getStatus, 0);
                    int count = seaNodeDetailMapper.selectCount(qw);
                    //当前节点是会签,且节点存在待审批则不必执行流程
                    if (!(way == 2 && count != 0)) {
                        //更新当前节点状态
                        SeaNode seaNode = new SeaNode();
                        seaNode.setId(Long.valueOf(currentNode.get("id").toString()));
                        seaNode.setEndTime(new Date());
                        seaNode.setStatus(status);
                        seaNodeMapper.updateById(seaNode);
                        if (currentNode.get("isConcurrent").toString().equals("0")) {
                            variables.put("currentNodeId", currentNode.get("target"));
                            variables.put("source", currentNode.get("target"));
                            JSONObject currentNodeInfo = nodeMap.getJSONObject(currentNode.get("target").toString());
                            currentNodeInfo.put("status", 1);
                            nodeMap.put(currentNode.get("target").toString(), currentNodeInfo);
                            JSONObject jsonObject = excuteNext(nodeMap, nodeLastMap, nodeNextMap, variables, request);
                            jsonObject.put("edges", edges);
                            seaInstance.setResources(jsonObject.toJSONString());
                            //流程结束
                            if (variables.containsKey("flowFlag") && (boolean) variables.get("flowFlag")) {
                                seaInstance.setStatus(status);
                                seaInstance.setEndTime(new Date());
                            } else {
                                seaInstance.setStatus(0);
                            }
                        } else {
                            // 并行网关
                            LambdaQueryWrapper<SeaNode> concurrentQw = new LambdaQueryWrapper<>();
                            concurrentQw.eq(SeaNode::getDefId, seaInstance.getId())
                                    .eq(SeaNode::getStatus, 0);
                            int concurrentCount = seaNodeMapper.selectCount(concurrentQw);
                            if (concurrentCount == 0) {
                                variables.put("currentNodeId", currentNode.get("target"));
                                variables.put("source", currentNode.get("target"));
                                JSONObject currentNodeInfo = nodeMap.getJSONObject(currentNode.get("target").toString());
                                currentNodeInfo.put("status", 1);
                                nodeMap.put(currentNode.get("target").toString(), currentNodeInfo);
                                JSONObject jsonObject = excuteNext(nodeMap, nodeLastMap, nodeNextMap, variables, request);
                                jsonObject.put("edges", edges);
                                seaInstance.setResources(jsonObject.toJSONString());
                                //流程结束
                                if (variables.containsKey("flowFlag") && (boolean) variables.get("flowFlag")) {
                                    seaInstance.setStatus(status);
                                    seaInstance.setEndTime(new Date());
                                } else {
                                    seaInstance.setStatus(0);
                                }
                            } else {
                                JSONObject currentNodeInfo = nodeMap.getJSONObject(currentNode.get("target").toString());
                                currentNodeInfo.put("status", 1);
                                seaInstance.setResources(resourceJson.toJSONString());
                            }
                        }
                        if (StringUtils.isEmpty(variables.get("currentAgent"))) {
                            seaInstance.setCurrentAgent("");
                        } else {
                            seaInstance.setCurrentAgent(variables.get("currentAgent").toString());
                        }
                        seaInstanceMapper.updateById(seaInstance);
                    }
                }
            } else if (status == 2) { //审批不同意
                //更新当前节点状态
                SeaNode seaNode = new SeaNode();
                seaNode.setId(Long.valueOf(currentNode.get("id").toString()));
                seaNode.setEndTime(new Date());
                seaNode.setStatus(status);
                seaNodeMapper.updateById(seaNode);

                seaInstance.setStatus(status);
                variables.put("version", seaInstance.getVersion());
                if (Integer.valueOf(variables.get("rejectType").toString()) == 1) {
                    // 退回到初始节点
                    variables.put("currentNodeId", "start");
                    variables.put("source", "start");

                    //更新之前所有节点状态
                    SeaNode beforeSeaNode = new SeaNode();
                    beforeSeaNode.setStatus(status);
                    LambdaQueryWrapper<SeaNode> snWq = new LambdaQueryWrapper<>();
                    snWq.eq(SeaNode::getDefId, seaInstance.getId());
                    seaNodeMapper.update(beforeSeaNode, snWq);

                    SeaNode newSeaNode = new SeaNode();
                    newSeaNode.setDefId(seaInstance.getId());
                    newSeaNode.setVersion(seaInstance.getVersion() + 1);
                    seaInstance.setVersion(seaInstance.getVersion() + 1);
                    seaInstance.setReturnNumber(seaInstance.getReturnNumber() + 1);
                    newSeaNode.setName("开始节点");
                    newSeaNode.setSource(currentNode.get("target").toString());
                    newSeaNode.setTarget("start");
                    newSeaNode.setType(4);
                    seaNodeMapper.insert(newSeaNode);
                    SeaNodeDetail newSeaNodeDetail = new SeaNodeDetail();
                    newSeaNodeDetail.setNodeId(newSeaNode.getId());
                    SysAccount user = userMapper.selectById(seaInstance.getUserId());
                    newSeaNodeDetail.setName(user.getName());
                    newSeaNodeDetail.setAssignee(String.valueOf(seaInstance.getUserId()));
                    seaNodeDetailMapper.insert(newSeaNodeDetail);

                    //重置流程状态
                    JSONObject resourceBefore = JSONObject.parseObject(seaInstance.getResources());
                    JSONObject nodeMapBefore = resourceJson.getJSONObject("nodes");
                    Set<String> keys = nodeMapBefore.keySet();
                    for (String key : keys) {
                        if (nodeMapBefore.getJSONObject(key).get("status") != null) {
                            nodeMapBefore.getJSONObject(key).remove("status");
                        }
                    }
                    resourceBefore.put("nodes", nodeMapBefore);
                    seaInstance.setResources(resourceBefore.toJSONString());

                    variables.put("currentAgent", user.getName());
                } else {
                    //获取当前节点其他信息
                    SeaNode nowSeaNode = seaNodeMapper.selectById(currentNode.get("id").toString());
                    //查询当前节点是否有其他节点
                    LambdaQueryWrapper<SeaNode> otherQw = new LambdaQueryWrapper<>();
                    otherQw.eq(SeaNode::getDefId, nowSeaNode.getDefId())
                            .eq(SeaNode::getSource, nowSeaNode.getSource())
                            .eq(SeaNode::getVersion, nowSeaNode.getVersion())
                            .ne(SeaNode::getTarget, nowSeaNode.getTarget())
                            .ne(SeaNode::getTarget, "start")
                            .orderByDesc(SeaNode::getId);
                    List<SeaNode> seaNodes = seaNodeMapper.selectList(otherQw);
                    //是否存在其他并行节点标识
                    if (seaNodes != null && seaNodes.size() > 0) {
                        //根据“target”去重
                        seaNodes = seaNodes.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(SeaNode::getTarget))), ArrayList::new));
                        //更新其他并行节点状态
                        for (SeaNode node : seaNodes) {
                            //并行节点通过且该节点类型等于‘审批’,进行拦截
                            if (node.getStatus() == 1 && node.getType() == 1) {
                                throw new FlowException("退回失败，该并行审批节点已有人同意");
                            }
                            node.setStatus(status);
                            seaNodeMapper.updateById(node);
                            //更新其他并行流程状态
                            resourceJson.getJSONObject("nodes").getJSONObject(node.getTarget()).remove("status");
                        }
                    }
                    // 退回到上个节点
                    List<String> lastNodeTargetList = getLastNode(currentNode.get("target").toString(), currentNode.get("source").toString(), nodeMap, nodeLastMap);
                    if (lastNodeTargetList.size() == 0) {
                        throw new FlowException("无上节点，请联系管理员");
                    } else {
                        JSONObject currentNodeInfo = nodeMap.getJSONObject(currentNode.get("target").toString());
                        currentNodeInfo.remove("status");
                        nodeMap.put(currentNode.get("target").toString(), currentNodeInfo);
                        resourceJson.put("nodes", nodeMap);
                        seaInstance.setResources(resourceJson.toJSONString());
                        seaInstance.setStatus(status);
                        seaInstance.setReturnNumber(seaInstance.getReturnNumber() + 1);
                        if (lastNodeTargetList.contains("start")) {
                            SeaNode newSeaNode = new SeaNode();
                            newSeaNode.setDefId(seaInstance.getId());
                            newSeaNode.setVersion(seaInstance.getVersion() + 1);
                            seaInstance.setVersion(seaInstance.getVersion() + 1);
                            newSeaNode.setName("开始节点");
                            newSeaNode.setSource(currentNode.get("target").toString());
                            newSeaNode.setTarget("start");
                            newSeaNode.setType(4);
                            seaNodeMapper.insert(newSeaNode);
                            SeaNodeDetail newSeaNodeDetail = new SeaNodeDetail();
                            newSeaNodeDetail.setNodeId(newSeaNode.getId());
                            SysAccount user = userMapper.selectById(seaInstance.getUserId());
                            newSeaNodeDetail.setName(user.getName());
                            newSeaNodeDetail.setAssignee(String.valueOf(seaInstance.getUserId()));
                            seaNodeDetailMapper.insert(newSeaNodeDetail);

                            variables.put("currentAgent", user.getName());
                        } else {
                            for (int k = 0; k < lastNodeTargetList.size(); k++) {
                                // 更新上一个节点的状态
                                String lastNodeTarget = lastNodeTargetList.get(k);
                                LambdaQueryWrapper<SeaNode> lastNodeQw = new LambdaQueryWrapper<>();
                                lastNodeQw.eq(SeaNode::getTarget, lastNodeTarget)
                                        .eq(SeaNode::getVersion, nowSeaNode.getVersion())
                                        .eq(SeaNode::getDefId, nowSeaNode.getDefId());
                                SeaNode lastSeaNode = new SeaNode();
                                lastSeaNode.setStatus(status);
                                seaNodeMapper.update(lastSeaNode, lastNodeQw);
                                List<SeaNode> lastSeaNodes = seaNodeMapper.selectList(lastNodeQw);
                                if (lastSeaNodes != null) {
                                    variables.put("isConcurrent", lastSeaNodes.get(0).getIsConcurrent());
                                    variables.put("source", lastSeaNodes.get(0).getSource());
                                }
                                // 当前节点id
                                variables.put("currentNodeId", lastNodeTargetList.get(k));
//								variables.put("source", currentNode.get("target").toString());
                                JSONObject jsonObject = excuteCurrent(nodeMap, nodeLastMap, nodeNextMap, variables, request);
                                jsonObject.put("edges", edges);
                                seaInstance.setResources(jsonObject.toJSONString());
                            }
                        }
                    }
                }
                if (StringUtils.isEmpty(variables.get("currentAgent"))) {
                    seaInstance.setCurrentAgent("");
                } else {
                    seaInstance.setCurrentAgent(variables.get("currentAgent").toString());
                }
                seaInstanceMapper.updateById(seaInstance);
            } else {
                throw new RuntimeException("审批异常");
            }
        }
    }

    /**
     * 获取上个节点
     */
    public List<String> getLastNode(String nodeId, String source, JSONObject nodeMap, JSONObject lastNodeMap) {
        List<String> result = new ArrayList<>();
        if (lastNodeMap.containsKey(nodeId)) {
            JSONArray lastNodeArray = lastNodeMap.getJSONArray(nodeId);
            for (int i = 0; i < lastNodeArray.size(); i++) {
                String lastNodeId = lastNodeArray.getString(i);
                if (nodeMap.containsKey(lastNodeId)) {
                    JSONObject lastNodeItem = nodeMap.getJSONObject(lastNodeId);
                    if (source.equals(lastNodeId)) {
                        if (lastNodeItem.getString("type").equals("notifier")
                                || lastNodeItem.getString("type").equals("condition")) {
                            // 清除流程图条件显示效果
                            if (lastNodeItem.getString("type").equals("condition")) {
                                nodeMap.getJSONObject(lastNodeItem.get("id").toString()).remove("status");
                            }
                            return getLastNode(lastNodeItem.getString("id"), source, nodeMap, lastNodeMap);
                        } else {
                            result.add(lastNodeItem.getString("id"));
                        }
                    } else if (lastNodeItem.getString("type").equals("exclusiveGateway")
                            || lastNodeItem.getString("type").equals("parallelGateWay")
                            || lastNodeItem.getString("type").equals("manualSelection")) {
                        // 清除流程显示效果
                        nodeMap.getJSONObject(lastNodeItem.get("id").toString()).remove("status");
                        return getLastNode(lastNodeItem.getString("id"), source, nodeMap, lastNodeMap);
                    }
                } else {
                    // 无节点信息
                }
            }
        }
        if (result.size() == 0) {
            result.add("start");
        }
        return result;
    }

    /**
     * 重启流程
     */
    @Transactional
    @Override
    public void restartProcess(Map<String, Object> variables, HttpServletRequest request) {
        LambdaQueryWrapper<SeaInstance> qw = new LambdaQueryWrapper<>();
        qw.eq(SeaInstance::getBusinessType, variables.get("businessType"))
                .eq(SeaInstance::getBusinessKey, variables.get("businessKey"));
        SeaInstance seaInstance = seaInstanceMapper.selectOne(qw);
        if (seaInstance == null) {
            throw new FlowException("获取不到流程信息");
        } else {
            variables.put("isConcurrent", 0);
            if (seaInstance.getStatus() == 2 || seaInstance.getStatus() == 3) {
                // 驳回重新提交
                Map<String, Object> currentNode = seaNodeMapper.queryCurrentNodeDetail(
                        String.valueOf(seaInstance.getId()), seaInstance.getVersion(),
                        variables.get("userId").toString());
                if (currentNode == null) {
                    throw new FlowException("当前没有审批节点");
                } else {
                    SeaNode seaNode = new SeaNode();
                    seaNode.setId(Long.valueOf(currentNode.get("id").toString()));
                    seaNode.setStatus(1);
                    seaNode.setEndTime(new Date());
                    seaNodeMapper.updateById(seaNode);

                    SeaNodeDetail seaNodeDetail = new SeaNodeDetail();
                    seaNodeDetail.setId(Long.valueOf(currentNode.get("nodeDetailId").toString()));
                    seaNodeDetail.setStatus(5);
                    seaNodeDetail.setEndTime(new Date());
                    seaNodeDetailMapper.updateById(seaNodeDetail);

                    variables.put("defId", seaInstance.getId());
                    variables.put("version", seaInstance.getVersion());
                    variables.put("title", "重新发起了" + seaInstance.getName());
                    // 发起人
                    variables.put("sponsorCompany", seaInstance.getCompanyId());
                    variables.put("sponsor", seaInstance.getUserId());
                    // 当前节点id
                    variables.put("currentNodeId", "start");
                    variables.put("source", "start");
                    JSONObject resourceJson = JSONObject.parseObject(seaInstance.getResources());
                    JSONObject nodeMap = resourceJson.getJSONObject("nodes");
                    JSONArray edges = resourceJson.getJSONArray("edges");
                    JSONObject nodeLastMap = new JSONObject();
                    JSONObject nodeNextMap = new JSONObject();
                    for (int i = 0; i < edges.size(); i++) {
                        JSONObject edge = edges.getJSONObject(i);
                        // 上个节点
                        if (nodeLastMap.containsKey(edge.getString("target"))) {
                            JSONArray source = nodeLastMap.getJSONArray(edge.getString("target"));
                            source.add(edge.getString("source"));
                            nodeLastMap.put(edge.getString("target"), source);
                        } else {
                            JSONArray source = new JSONArray();
                            source.add(edge.getString("source"));
                            nodeLastMap.put(edge.getString("target"), source);
                        }
                        // 下个节点
                        if (nodeNextMap.containsKey(edge.getString("source"))) {
                            JSONArray target = nodeNextMap.getJSONArray(edge.getString("source"));
                            target.add(edge.getString("target"));
                            nodeNextMap.put(edge.getString("source"), target);
                        } else {
                            JSONArray target = new JSONArray();
                            target.add(edge.getString("target"));
                            nodeNextMap.put(edge.getString("source"), target);
                        }
                    }
                    JSONObject jsonObject = excuteNext(nodeMap, nodeLastMap, nodeNextMap, variables, request);
                    jsonObject.put("edges", edges);
                    seaInstance.setResources(jsonObject.toJSONString());
                    if (variables.containsKey("flowFlag") && (boolean) variables.get("flowFlag")) {
                        seaInstance.setStatus(1);
                        seaInstance.setEndTime(new Date());
                    }
                    if (StringUtils.isEmpty(variables.get("currentAgent"))) {
                        seaInstance.setCurrentAgent("");
                    } else {
                        seaInstance.setCurrentAgent(variables.get("currentAgent").toString());
                    }
                    seaInstance.setStatus(0);
                    seaInstanceMapper.updateById(seaInstance);
                }
            } else {
                throw new FlowException("流程审批中、已结束，不可重启流程");
            }
        }
    }

    /**
     * 撤回
     *
     * @param businessType 业务类型
     * @param businessKey  业务主键
     */
    @Transactional
    @Override
    public void revoke(String businessType, String businessKey, String reason) {
        LambdaQueryWrapper<SeaInstance> qw = new LambdaQueryWrapper<>();
        qw.eq(SeaInstance::getBusinessType, businessType)
                .eq(SeaInstance::getBusinessKey, businessKey);
        SeaInstance seaInstance = seaInstanceMapper.selectOne(qw);
        if (seaInstance == null) {
            throw new FlowException("获取不到流程信息");
        } else {
            if (seaInstance.getStatus() == 0) {
                int count = seaNodeMapper.queryApproverCount(String.valueOf(seaInstance.getId()),
                        seaInstance.getVersion());
                if (count == 0) {
                    seaInstance.setStatus(3);
                    seaInstance.setVersion(seaInstance.getVersion() + 1);

                    SeaNode seaNode = new SeaNode();
                    seaNode.setDefId(seaInstance.getId());
                    seaNode.setSource("revoke");
                    seaNode.setTarget("revoke");
                    seaNode.setVersion(seaInstance.getVersion());
                    seaNode.setName("撤回");
                    seaNode.setType(3);
                    seaNode.setEndTime(new Date());
                    seaNodeMapper.insert(seaNode);

                    SeaNodeDetail seaNodeDetail = new SeaNodeDetail();
                    seaNodeDetail.setNodeId(seaNode.getId());
                    seaNodeDetail.setStatus(4);
                    SysAccount user = userMapper.selectById(seaInstance.getUserId());
                    seaNodeDetail.setName(user.getName());
                    seaNodeDetail.setAssignee(String.valueOf(seaInstance.getUserId()));
                    seaNodeDetail.setEndTime(new Date());
                    seaNodeDetail.setRemark(reason);
                    seaNodeDetailMapper.insert(seaNodeDetail);

                    SeaNode startSeaNode = new SeaNode();
                    startSeaNode.setDefId(seaInstance.getId());
                    startSeaNode.setSource("start");
                    startSeaNode.setTarget("start");
                    startSeaNode.setVersion(seaInstance.getVersion());
                    startSeaNode.setName("开始节点");
                    seaNodeMapper.insert(startSeaNode);

                    SeaNodeDetail startSeaNodeDetail = new SeaNodeDetail();
                    startSeaNodeDetail.setNodeId(startSeaNode.getId());
                    startSeaNodeDetail.setName(user.getName());
                    startSeaNodeDetail.setAssignee(String.valueOf(seaInstance.getUserId()));
                    seaNodeDetailMapper.insert(startSeaNodeDetail);

                    // 更新节点状态
                    JSONObject jsonObject = JSONObject.parseObject(seaInstance.getResources());
                    JSONObject nodes = jsonObject.getJSONObject("nodes");
                    for (String str : nodes.keySet()) {
                        nodes.getJSONObject(str).remove("status");
                    }
                    jsonObject.put("nodes", nodes);
                    seaInstance.setResources(jsonObject.toJSONString());
                    seaInstance.setCurrentAgent(user.getName());
                    seaInstanceMapper.updateById(seaInstance);
                } else {
                    throw new FlowException("流程已审批，不可撤回");
                }
            } else if (seaInstance.getStatus() == 1) {
                throw new FlowException("流程已结束，不可撤回");
            } else if (seaInstance.getStatus() == 2) {
                throw new FlowException("流程已驳回，不可撤回");
            } else if (seaInstance.getStatus() == 3) {
                throw new FlowException("流程已撤回，不可撤回");
            }
        }
    }

    /**
     * 弃审
     *
     * @param assignee     审核人
     * @param businessType 业务类型
     * @param businessKey  业务主键
     */
    @Transactional
    @Override
    public void abandon(Long assignee, String businessType, String businessKey) {
        LambdaQueryWrapper<SeaInstance> qw = new LambdaQueryWrapper<>();
        qw.eq(SeaInstance::getBusinessType, businessType).eq(SeaInstance::getBusinessKey, businessKey);
        SeaInstance seaInstance = seaInstanceMapper.selectOne(qw);
        if (seaInstance == null) {
            throw new FlowException("获取不到流程信息");
        } else {
            // 审核人当前节点
            Map<String, Object> currentNode = seaNodeMapper.queryCurrentNodeByAssignee(seaInstance.getId(),
                    String.valueOf(assignee));
            if (currentNode == null) {
                throw new FlowException("当前没有处理流程节点");
            } else if ("4".equals(currentNode.get("procdefStatus").toString())) {
                throw new FlowException("当前流程已关闭，不可弃审");
            } else if (!"1".equals(currentNode.get("status").toString())) {
                throw new FlowException("当前节点未通过审核，不可弃审");
            } else {
                // 下一节点(可能存在多个节点)
                List<Map<String, Object>> nextNodes = seaNodeMapper
                        .queryNextNode(Long.parseLong(currentNode.get("id").toString()));
                // 筛除同一节点旧操作
                List<Map<String, Object>> qcNextNodes = nextNodes.stream().collect(
                        Collectors.collectingAndThen(
                                Collectors.toCollection(
                                        () -> new TreeSet<>(Comparator.comparing(m -> m.get("target").toString()))
                                ), ArrayList::new
                        )
                );
                SeaNode seaNode = new SeaNode();
                boolean updateNodeFlag = false;
                if (qcNextNodes != null && qcNextNodes.size() > 0) {
                    for (Map<String, Object> nextNode : qcNextNodes) {
                        if (!"0".equals(nextNode.get("status").toString())
                                || seaNodeMapper.queryHandleCount(Long.parseLong(nextNode.get("id").toString())) > 0) {
                            throw new FlowException("下一流程节点已审批，无法弃审");
                        }
                    }
                    updateNodeFlag = true;
                } else {
                    // 没有下一流程节点，说明流程已经结束，需要修改流程状态，或者该流程节点是会签且未审核完成
                    seaInstance.setStatus(0);
                }

                // 待办人
                List<String> currentAgentList = new ArrayList<>();
                // 获取当前审核节点明细
                LambdaQueryWrapper<SeaNodeDetail> seaNodeDetailQueryWrapper = new LambdaQueryWrapper<>();
                seaNodeDetailQueryWrapper.eq(SeaNodeDetail::getNodeId, currentNode.get("id"));
                List<SeaNodeDetail> seaNodeDetailList = seaNodeDetailMapper.selectList(seaNodeDetailQueryWrapper);

                if (updateNodeFlag) {
                    // 更新下一条节点状态
                    for (Map<String, Object> nextNode : nextNodes) {
                        seaNode.setId(Long.parseLong(nextNode.get("id").toString()));
                        seaNode.setStatus(3);
                        seaNode.setEndTime(new Date());
                        seaNodeMapper.updateById(seaNode);
                    }
                }

                // 判断当前节点是否是会签
                JSONObject jsonObject = JSONObject.parseObject(seaInstance.getResources());
                int way = jsonObject.getJSONObject("nodes").getJSONObject(currentNode.get("target").toString()).getIntValue("way");
                if (way == 2) {
                    // 会签
                    if (!"0".equals(currentNode.get("status").toString())) {
                        // 当前节点已审核通过，进入下节点，节点有人弃审，更新当前节点未待审
                        seaNode = new SeaNode();
                        seaNode.setStatus(0);
                        seaNode.setId(Long.parseLong(currentNode.get("id").toString()));
                        seaNodeMapper.updateById(seaNode);
                    }
                    // 新增弃审记录
                    SysAccount sysUser = userMapper.selectById(assignee);
                    SeaNodeDetail qsSeaNodeDetail = new SeaNodeDetail();
                    qsSeaNodeDetail.setNodeId(Long.parseLong(currentNode.get("id").toString()));
                    qsSeaNodeDetail.setName(sysUser.getName());
                    qsSeaNodeDetail.setAssignee(String.valueOf(assignee));
                    qsSeaNodeDetail.setStatus(6);
                    qsSeaNodeDetail.setEndTime(new Date());
                    seaNodeDetailMapper.insert(qsSeaNodeDetail);
                    // 新增节点明细待办记录
                    boolean insertFlag = true;
                    for (SeaNodeDetail detail : seaNodeDetailList) {
                        //判断是否是弃审人
                        if (assignee != null && assignee.equals(Long.valueOf(detail.getAssignee())) && insertFlag) {
                            //新增节点详情
                            SeaNodeDetail seaNodeDetail = new SeaNodeDetail();
                            seaNodeDetail.setNodeId(Long.parseLong(currentNode.get("id").toString()));
                            seaNodeDetail.setName(detail.getName());
                            seaNodeDetail.setAssignee(detail.getAssignee());
                            seaNodeDetail.setStatus(0);
                            seaNodeDetailMapper.insert(seaNodeDetail);
                            insertFlag = false;
                        }
                        currentAgentList.add(detail.getName());
                    }
                    // 审批人去重（当前节点可能进行多次弃审，导致节点详情出现重复审核人）
                    currentAgentList = currentAgentList.stream().distinct().collect(Collectors.toList());
                } else {
                    // 或签
                    // 新增一条弃审数据
                    seaNode = new SeaNode();
                    seaNode.setDefId(Long.parseLong(currentNode.get("defId").toString()));
                    seaNode.setVersion(Integer.parseInt(currentNode.get("version").toString()));
                    seaNode.setSource(currentNode.get("source").toString());
                    seaNode.setTarget(currentNode.get("target").toString());
                    seaNode.setName(currentNode.get("name").toString());
                    seaNode.setType(Integer.parseInt(currentNode.get("type").toString()));
                    seaNode.setStatus(1);
                    seaNode.setEndTime(new Date());
                    seaNodeMapper.insert(seaNode);

                    SysAccount sysUser = userMapper.selectById(assignee);
                    SeaNodeDetail seaNodeDetail = new SeaNodeDetail();
                    seaNodeDetail.setNodeId(seaNode.getId());
                    seaNodeDetail.setName(sysUser.getName());
                    seaNodeDetail.setAssignee(String.valueOf(assignee));
                    seaNodeDetail.setStatus(6);
                    seaNodeDetail.setEndTime(new Date());
                    seaNodeDetailMapper.insert(seaNodeDetail);

                    // 新增一条节点待办记录
                    seaNode = new SeaNode();
                    seaNode.setDefId(Long.parseLong(currentNode.get("defId").toString()));
                    seaNode.setVersion(Integer.parseInt(currentNode.get("version").toString()));
                    seaNode.setSource(currentNode.get("source").toString());
                    seaNode.setTarget(currentNode.get("target").toString());
                    seaNode.setName(currentNode.get("name").toString());
                    seaNode.setType(Integer.parseInt(currentNode.get("type").toString()));
                    seaNodeMapper.insert(seaNode);

                    // 新增节点明细待办记录
                    for (SeaNodeDetail detail : seaNodeDetailList) {
                        seaNodeDetail = new SeaNodeDetail();
                        seaNodeDetail.setNodeId(seaNode.getId());
                        seaNodeDetail.setName(detail.getName());
                        seaNodeDetail.setAssignee(detail.getAssignee());
                        seaNodeDetailMapper.insert(seaNodeDetail);
                        currentAgentList.add(detail.getName());
                    }
                }
                // 更新节点状态
                JSONObject nodes = jsonObject.getJSONObject("nodes");
                JSONArray edges = jsonObject.getJSONArray("edges");
                JSONObject nodeNextMap = new JSONObject();
                for (int i = 0; i < edges.size(); i++) {
                    JSONObject edge = edges.getJSONObject(i);
                    // 下个节点
                    if (nodeNextMap.containsKey(edge.getString("source"))) {
                        JSONArray target = nodeNextMap.getJSONArray(edge.getString("source"));
                        target.add(edge.getString("target"));
                        nodeNextMap.put(edge.getString("source"), target);
                    } else {
                        JSONArray target = new JSONArray();
                        target.add(edge.getString("target"));
                        nodeNextMap.put(edge.getString("source"), target);
                    }
                }
                //判断下一节点是否是手动选择/并行网关/
                JSONArray nextNodeType = nodeNextMap.getJSONArray(currentNode.get("target").toString());
                if (nextNodeType != null && nextNodeType.size() == 1) {
                    String nextNodeId = nextNodeType.getString(0);
                    JSONObject nextNodeInfo = nodes.getJSONObject(nextNodeId);
                    if ("exclusiveGateway".equals(nextNodeInfo.getString("type"))
                            || "parallelGateWay".equals(nextNodeInfo.getString("type"))
                            || "manualSelection".equals(nextNodeInfo.getString("type"))) {
                        nodes.getJSONObject(nextNodeInfo.get("id").toString()).remove("status");
                    }
                }
                nodes.getJSONObject(currentNode.get("target").toString()).put("status", 0);
                if (updateNodeFlag) {
                    for (Map<String, Object> nextNode : nextNodes) {
                        nodes.getJSONObject(nextNode.get("target").toString()).remove("status");
                    }
                } else {
                    // 清除结束状态的节点显示效果
                    nodes.getJSONObject("end").remove("status");
                }
                jsonObject.put("nodes", nodes);
                seaInstance.setResources(jsonObject.toJSONString());
                // 更新待办人
                seaInstance.setCurrentAgent(org.apache.commons.lang.StringUtils.join(currentAgentList, ","));
                seaInstanceMapper.updateById(seaInstance);
            }
        }
    }

    /**
     * 查询节点详情
     */
    @Override
    public ResultData queryNodeDetail(Long companyId, Long userId, Integer type, String businessType,
                                      String businessKey, String nodeId) {
        LambdaQueryWrapper<SeaInstance> qwSeaInstance = new LambdaQueryWrapper<>();
        qwSeaInstance.eq(SeaInstance::getBusinessType, businessType)
                .eq(SeaInstance::getBusinessKey, businessKey);
        SeaInstance seaInstance = seaInstanceMapper.selectOne(qwSeaInstance);
        if (seaInstance != null) {
            List<Map<String, Object>> list = seaNodeDetailMapper.queryNodeDetail(String.valueOf(seaInstance.getId()),
                    seaInstance.getVersion(), nodeId);
            if (list.size() == 0) {
                List<String> userStr = nodeHandel(
                        JSONObject.parseObject(seaInstance.getResources()).getJSONObject("nodes").getJSONObject(nodeId),
                        String.valueOf(seaInstance.getCompanyId()), String.valueOf(seaInstance.getUserId()), null);
                if (userStr.size() != 0) {
                    LambdaQueryWrapper<SysAccount> qw = new LambdaQueryWrapper<>();
                    qw.in(SysAccount::getId, userStr);
                    List<SysAccount> userList = userMapper.selectList(qw);
                    for (int i = 0; i < userList.size(); i++) {
                        Map<String, Object> item = new HashMap<>();
                        item.put("name", userList.get(i).getName());
                        list.add(item);
                    }
                }
            }
            return ResultData.success(list);
        }
        return ResultData.success(null);
    }

    /**
     * 获取节点操作权限
     *
     * @param resource 流程json
     * @param nodeId   节点id
     */
    @Override
    public String getOperationAuthority(JSONObject jsonObject, String nodeId) {
        return jsonObject.getJSONObject("nodes").getJSONObject(nodeId).getString("authority");
    }

    /**
     * 查询流程详情
     */
    @Override
    public ResultData queryProcessDetail(String businessType, String businessKey, String nodeName) {
        LambdaQueryWrapper<SeaInstance> qw = new LambdaQueryWrapper<>();
        qw.eq(SeaInstance::getBusinessType, businessType)
                .eq(SeaInstance::getBusinessKey, businessKey);
        SeaInstance seaInstance = seaInstanceMapper.selectOne(qw);
        if (seaInstance != null) {
            return ResultData.success(seaNodeDetailMapper.queryProcessDetailByNodeName(
                    String.valueOf(seaInstance.getId()), seaInstance.getVersion(), nodeName));
        }
        return ResultData.success(null);
    }

    /**
     * 待办事项
     */
    @Override
    public ResultData queryTodoItem(Integer pageNo, Integer pageSize, Long companyId, String userId, Long formId) {
        SysCompany company = companyMapper.selectById(companyId);
        PageHelper.startPage(pageNo, pageSize);
        List<Map<String, Object>> list = seaInstanceMapper.queryTodoItem(company.getCode().substring(0, 4), userId, formId);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        return ResultData.success(pageInfo);
    }

    /**
     * 跟踪事项
     */
    @Override
    public ResultData queryDoneItem(Integer pageNo, Integer pageSize, Long companyId, Long userId) {
        SysCompany company = companyMapper.selectById(companyId);
        PageHelper.startPage(pageNo, pageSize);
        PageHelper.startPage(pageNo, pageSize);
        List<Map<String, Object>> list = seaInstanceMapper.queryDoneItem(company.getCode().substring(0, 4), String.valueOf(userId));
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        return ResultData.success(pageInfo);
    }

    /**
     * 抄送事项
     */
    @Override
    public ResultData queryCopyItem(Integer pageNo, Integer pageSize, Long companyId, Long userId) {
        SysCompany company = companyMapper.selectById(companyId);
        PageHelper.startPage(pageNo, pageSize);
        List<Map<String, Object>> list = seaInstanceMapper.queryCopyItem(company.getCode().substring(0, 4), String.valueOf(userId));
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        return ResultData.success(pageInfo);
    }

    /**
     * 我发起的
     */
    @Override
    public ResultData querySelfItem(Integer pageNo, Integer pageSize, Long companyId, Long userId, Long formId) {
        SysCompany company = companyMapper.selectById(companyId);
        PageHelper.startPage(pageNo, pageSize);
        List<Map<String, Object>> list = seaInstanceMapper.querySelfItem(company.getCode().substring(0, 4), userId, formId);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        return ResultData.success(pageInfo);
    }

    /**
     * 待发事项
     */
    @Override
    public ResultData queryReadyItem(Integer pageNo, Integer pageSize, Long companyId, Long userId) {
        PageHelper.startPage(pageNo, pageSize);
        List<Map<String, Object>> list = seaInstanceMapper.queryReadyItem(companyId, String.valueOf(userId));
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        return ResultData.success(pageInfo);
    }

    /**
     * 更新抄送状态
     */
    @Override
    public ResultData updateNotifier(Long id) {
        SeaNodeDetail seaNodeDetail = new SeaNodeDetail();
        seaNodeDetail.setId(id);
        seaNodeDetail.setStatus(3);
        seaNodeDetail.setEndTime(new Date());
        seaNodeDetailMapper.updateById(seaNodeDetail);
        return ResultData.success(null);
    }

    /**
     * 关闭
     */
    @Transactional
    @Override
    public ResultData close(Long userId, String businessType, String businessKeys, String reason) {
        if (StringUtils.isEmpty(businessKeys)) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "亲，请先选择要关闭的数据");
        }
        String[] businessKeyArr = businessKeys.split(",");
        LambdaQueryWrapper<SeaInstance> qw = new LambdaQueryWrapper<>();
        qw.eq(SeaInstance::getBusinessType, businessType).in(SeaInstance::getBusinessKey, Arrays.asList(businessKeyArr));
        List<SeaInstance> seaInstanceList = seaInstanceMapper.selectList(qw);
        if (CollectionUtils.isEmpty(seaInstanceList)) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "参数有误");
        }
        SysAccount sysUser = userMapper.selectById(userId);
        String userName = sysUser.getName();

        SeaNode seaNode;
        SeaNodeDetail seaNodeDetail;
        for (SeaInstance seaInstance : seaInstanceList) {
            // 如果是通过的流程或者关闭的流程跳过
            if (seaInstance.getStatus().equals(1) || seaInstance.getStatus().equals(4)) {
                continue;
            }
            seaNode = new SeaNode();
            seaNode.setDefId(seaInstance.getId());
            seaNode.setVersion(seaInstance.getVersion());
            seaNode.setSource("close");
            seaNode.setTarget("close");
            seaNode.setName(userName);
            seaNode.setStatus(1);
            seaNode.setEndTime(new Date());
            seaNodeMapper.insert(seaNode);

            seaNodeDetail = new SeaNodeDetail();
            seaNodeDetail.setNodeId(seaNode.getId());
            seaNodeDetail.setName(userName);
            seaNodeDetail.setAssignee(String.valueOf(userId));
            seaNodeDetail.setStatus(7);
            seaNodeDetail.setRemark(reason);
            seaNodeDetail.setEndTime(new Date());
            seaNodeDetailMapper.insert(seaNodeDetail);

            seaInstance.setCloseStatus(seaInstance.getStatus());
            seaInstance.setStatus(4);
            seaInstanceMapper.updateById(seaInstance);
        }
        return ResultData.success(null);
    }

    /**
     * 激活
     */
    @Transactional
    @Override
    public ResultData open(Long userId, String businessType, String businessKeys) {
        if (StringUtils.isEmpty(businessKeys)) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "亲，请先选择要关闭的数据");
        }
        String[] businessKeyArr = businessKeys.split(",");
        LambdaQueryWrapper<SeaInstance> qw = new LambdaQueryWrapper<>();
        qw.eq(SeaInstance::getBusinessType, businessType).in(SeaInstance::getBusinessKey, Arrays.asList(businessKeyArr));
        List<SeaInstance> seaInstanceList = seaInstanceMapper.selectList(qw);
        if (CollectionUtils.isEmpty(seaInstanceList)) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "参数有误");
        }
        SysAccount sysUser = userMapper.selectById(userId);
        String userName = sysUser.getName();

        SeaNode seaNode;
        SeaNodeDetail seaNodeDetail;
        LambdaUpdateWrapper<SeaInstance> updateWrapper;
        for (SeaInstance seaInstance : seaInstanceList) {
            // 如果是关闭的流程跳过
            if (!seaInstance.getStatus().equals(4)) {
                continue;
            }
            seaNode = new SeaNode();
            seaNode.setDefId(seaInstance.getId());
            seaNode.setVersion(seaInstance.getVersion());
            seaNode.setSource("open");
            seaNode.setTarget("open");
            seaNode.setName(userName);
            seaNode.setStatus(1);
            seaNode.setEndTime(new Date());
            seaNodeMapper.insert(seaNode);

            seaNodeDetail = new SeaNodeDetail();
            seaNodeDetail.setNodeId(seaNode.getId());
            seaNodeDetail.setName(userName);
            seaNodeDetail.setAssignee(String.valueOf(userId));
            seaNodeDetail.setStatus(8);
            seaNodeDetail.setEndTime(new Date());
            seaNodeDetailMapper.insert(seaNodeDetail);

            updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.set(SeaInstance::getCloseStatus, null);
            updateWrapper.eq(SeaInstance::getId, seaInstance.getId());
            seaInstance.setStatus(seaInstance.getCloseStatus());
            seaInstanceMapper.update(seaInstance, updateWrapper);
        }
        return ResultData.success(null);
    }

    @Override
    public ResultData queryWorkTodoItem(Long companyId, Long userId, String name, String statusStr, String businessTypeStr, String departmentIds, int pageNo, int pageSize) {
        SysCompany company = companyMapper.selectById(companyId);
        PageHelper.startPage(pageNo, pageSize);
        List<Map<String, Object>> list = seaInstanceMapper.queryWorkTodoItem(company.getCode().substring(0, 4), String.valueOf(userId), name, statusStr, businessTypeStr, departmentIds);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(list);
        return ResultData.success(pageInfo);
    }

    @Override
    public ResultData queryWorkReadyItem(Long companyId, Long userId, String title, int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        List<Map<String, Object>> list = seaInstanceMapper.queryWorkReadyItem(companyId, userId, title);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(list);
        return ResultData.success(pageInfo);
    }

    @Override
    public ResultData queryWorkDoneItem(Long companyId, Long userId, String name, int pageNo, int pageSize) {
        SysCompany company = companyMapper.selectById(companyId);
        PageHelper.startPage(pageNo, pageSize);
        List<Map<String, Object>> list = seaInstanceMapper.queryWorkDoneItem(company.getCode().substring(0, 4), String.valueOf(userId), name);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(list);
        return ResultData.success(pageInfo);
    }

    @Override
    public ResultData queryWorkCopyItem(Long companyId, Long userId, String name, int pageNo, int pageSize) {
        SysCompany company = companyMapper.selectById(companyId);
        PageHelper.startPage(pageNo, pageSize);
        List<Map<String, Object>> list = seaInstanceMapper.queryWorkCopyItem(company.getCode().substring(0, 4), String.valueOf(userId), name);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(list);
        return ResultData.success(pageInfo);
    }

    @Override
    public ResultData queryWorkSelfItem(Long companyId, Long userId, String name, int pageNo, int pageSize) {
        SysCompany company = companyMapper.selectById(companyId);
        PageHelper.startPage(pageNo, pageSize);
        List<Map<String, Object>> list = seaInstanceMapper.queryWorkSelfItem(company.getCode().substring(0, 4), userId, name);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(list);
        return ResultData.success(pageInfo);
    }

    @Override
    public ResultData queryFlowTodoList(String businessType, String businessKey) {
        LambdaQueryWrapper<SeaInstance> qw = new LambdaQueryWrapper<>();
        qw.eq(SeaInstance::getBusinessType, businessType).eq(SeaInstance::getBusinessKey,
                businessKey);
        SeaInstance seaInstance = seaInstanceMapper.selectOne(qw);
        if (null != seaInstance){
            List<Map<String, Object>> list = seaNodeDetailMapper.selectFlowTodoList(seaInstance.getId(), seaInstance.getVersion(), 1, 0);
            return ResultData.success(list);
        }
        return ResultData.success(null);
    }

}
