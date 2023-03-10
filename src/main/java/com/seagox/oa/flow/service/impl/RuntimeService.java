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
import com.seagox.oa.excel.entity.JellyMetaFunction;
import com.seagox.oa.excel.mapper.JellyMetaFunctionMapper;
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
import com.seagox.oa.groovy.GroovyFactory;
import com.seagox.oa.groovy.IGroovyRule;
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
    private JellyMetaFunctionMapper metaFunctionMapper;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private RoleMapper roleMapper;

    /**
     * ????????????
     *
     * @param companyId    ??????id
     * @param userId       ??????id
     * @param name         ??????
     * @param resource     ????????????
     * @param businessType ????????????
     * @param businessKey  ???????????????
     */
    @Transactional
    public void startProcess(Map<String, Object> variables, HttpServletRequest request) {
        // ????????????
        if (StringUtils.isEmpty(variables.get("resource"))) {
            throw new FlowException("???????????????");
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
        // ?????????
        variables.put("sponsorCompany", variables.get("companyId"));
        variables.put("sponsor", variables.get("userId"));
        // ????????????id
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
            // ????????????
            if (nodeLastMap.containsKey(edge.getString("target"))) {
                JSONArray source = nodeLastMap.getJSONArray(edge.getString("target"));
                source.add(edge.getString("source"));
                nodeLastMap.put(edge.getString("target"), source);
            } else {
                JSONArray source = new JSONArray();
                source.add(edge.getString("source"));
                nodeLastMap.put(edge.getString("target"), source);
            }
            // ????????????
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
     * ????????????
     *
     * @param jsonObject ????????????
     * @param resource   ????????????
     * @param companyId  ??????id
     * @param userId     ??????id
     */
    public JSONObject excuteNext(JSONObject nodeMap, JSONObject nodeLastMap, JSONObject nodeNextMap, Map<String, Object> variables,
                                 HttpServletRequest request) {
        JSONObject resourceJson = new JSONObject();
        String currentNodeId = variables.get("currentNodeId").toString();
        // ??????????????????
        JSONArray nextNode = nodeNextMap.getJSONArray(currentNodeId);
        for (int i = 0; i < nextNode.size(); i++) {
            String nextNodeId = nextNode.getString(i);
            JSONObject nextNodeInfo = nodeMap.getJSONObject(nextNodeId);
            if (nextNodeInfo.getString("type").equals("approver")) {
                // ??????????????????
                JSONArray manualSelectionAry = nodeLastMap.getJSONArray(nextNodeId);
                boolean manualSelectionFlag = false;
                for (int j = 0; j < manualSelectionAry.size(); j++) {
                    JSONObject manualSelectionObj = nodeMap.getJSONObject(manualSelectionAry.getString(j));
                    if (manualSelectionObj.getString("type").equals("manualSelection")) {
                        manualSelectionFlag = true;
                        List<String> assigneeSetas = Arrays.asList(variables.get("flowOptionalList").toString().split(","));
                        if (assigneeSetas.contains(nextNodeId)) {
                            // ????????????
                            List<String> nodeUserList = nodeHandel(nextNodeInfo, variables.get("sponsorCompany").toString(),
                                    variables.get("sponsor").toString(), variables.get("approverOptionalList"));
                            if (nodeUserList.size() == 0) {
                                // ???????????????????????????
                                // ????????????id
                                // ??????????????????
                                nextNodeInfo.put("status", 1);
                                variables.put("currentNodeId", nextNodeId);
                                excuteNext(nodeMap, nodeLastMap, nodeNextMap, variables, request);
                            } else {
                                // ???????????????
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
                    // ????????????
                    List<String> nodeUserList = nodeHandel(nextNodeInfo, variables.get("sponsorCompany").toString(),
                            variables.get("sponsor").toString(), variables.get("approverOptionalList"));
                    if (nodeUserList.size() == 0) {
                        // ???????????????????????????
                        // ????????????id
                        // ??????????????????
                        nextNodeInfo.put("status", 1);
                        variables.put("currentNodeId", nextNodeId);
                        excuteNext(nodeMap, nodeLastMap, nodeNextMap, variables, request);
                    } else {
                        // ???????????????
                        nextNodeInfo.put("status", 0);
                        variables.put("nodeName", nextNodeInfo.getString("label"));
                        variables.put("type", 1);
                        variables.put("target", nextNodeInfo.getString("id"));

                        saveNode(variables, nodeUserList);
                    }
                }
            } else if (nextNodeInfo.getString("type").equals("notifier")) {
                // ????????????
                // ??????????????????
                nextNodeInfo.put("status", 1);
                List<String> nodeUserList = nodeHandel(nextNodeInfo, variables.get("sponsorCompany").toString(),
                        variables.get("sponsor").toString(), variables.get("approverOptionalList"));
                if (nodeUserList.size() != 0) {
                    variables.put("nodeName", nextNodeInfo.getString("label"));
                    variables.put("type", 2);
                    variables.put("target", nextNodeInfo.getString("id"));

                    saveNode(variables, nodeUserList);
                }
                // ???????????????
                variables.put("currentNodeId", nextNodeId);
                excuteNext(nodeMap, nodeLastMap, nodeNextMap, variables, request);
            } else if (nextNodeInfo.getString("type").equals("condition")) {
                // ????????????
                boolean result = false;
                if (!StringUtils.isEmpty(nextNodeInfo.getString("condition"))) {
                    variables.put("condition", nextNodeInfo.getString("condition"));
                    result = branchCondition(variables, request);
                } else {
                    // ?????????????????????
                }
                if (result) {
                    // ??????????????????
                    nextNodeInfo.put("status", 1);
                    // ???????????????
                    variables.put("currentNodeId", nextNodeId);
                    excuteNext(nodeMap, nodeLastMap, nodeNextMap, variables, request);
                }
            } else if (nextNodeInfo.getString("type").equals("exclusiveGateway")) {
                // ????????????

            } else if (nextNodeInfo.getString("type").equals("parallelGateWay")) {
                // ????????????
                // ??????????????????
                nextNodeInfo.put("status", 1);
                if (variables.get("isConcurrent").toString().equals("0")) {
                    variables.put("isConcurrent", 1);
                } else {
                    variables.put("isConcurrent", 0);
                }
                // ???????????????
                variables.put("currentNodeId", nextNodeId);
                excuteNext(nodeMap, nodeLastMap, nodeNextMap, variables, request);
            } else if (nextNodeInfo.getString("type").equals("manualSelection")) {
                // ????????????
                // ??????????????????
                nextNodeInfo.put("status", 1);
                if (!variables.get("isConcurrent").toString().equals("0")) {
                    variables.put("isConcurrent", 1);
                } else {
                    variables.put("isConcurrent", 0);
                }
                // ???????????????
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
                // ????????????
                nextNodeInfo.put("status", 1);
                variables.put("flowFlag", true);
            }
        }
        resourceJson.put("nodes", nodeMap);
        return resourceJson;
    }

    /**
     * ????????????
     *
     * @param jsonObject ????????????
     * @param resource   ????????????
     * @param companyId  ??????id
     * @param userId     ??????id
     */
    public JSONObject excuteCurrent(JSONObject nodeMap, JSONObject nodeLastMap, JSONObject nodeNextMap, Map<String, Object> variables,
                                    HttpServletRequest request) {
        JSONObject resourceJson = new JSONObject();
        // ??????????????????
        JSONObject currentNode = nodeMap.getJSONObject(variables.get("currentNodeId").toString());
        String type = currentNode.getString("type");
        if (type.equals("approver")) {
            // ????????????
            List<String> nodeUserList = nodeHandel(currentNode, variables.get("sponsorCompany").toString(),
                    variables.get("sponsor").toString(), variables.get("approverOptionalList"));
            if (nodeUserList.size() == 0) {
                // ???????????????????????????
                // ????????????id
                // ??????????????????
                currentNode.put("status", 1);
                excuteNext(nodeMap, nodeLastMap, nodeNextMap, variables, request);
            } else {
                // ???????????????
                currentNode.put("status", 0);
                variables.put("nodeName", currentNode.getString("label"));
                variables.put("type", 1);
                variables.put("target", currentNode.getString("id"));

                saveNode(variables, nodeUserList);
            }
        } else if (type.equals("notifier")) {
            // ????????????
            // ??????????????????
            currentNode.put("status", 1);
            List<String> nodeUserList = nodeHandel(currentNode, variables.get("sponsorCompany").toString(),
                    variables.get("sponsor").toString(), variables.get("approverOptionalList"));
            if (nodeUserList.size() != 0) {
                variables.put("nodeName", currentNode.getString("label"));
                variables.put("type", 2);
                variables.put("target", currentNode.getString("id"));

                saveNode(variables, nodeUserList);
            }
            // ???????????????
            excuteNext(nodeMap, nodeLastMap, nodeNextMap, variables, request);
        } else if (type.equals("condition")) {
            // ????????????
            boolean result = false;
            if (!StringUtils.isEmpty(currentNode.getString("condition"))) {
                variables.put("condition", currentNode.getString("condition"));
                result = branchCondition(variables, request);
            } else {
                // ?????????????????????
            }
            if (result) {
                // ??????????????????
                currentNode.put("status", 1);
                // ???????????????
                excuteNext(nodeMap, nodeLastMap, nodeNextMap, variables, request);
            }
        } else if (type.equals("exclusiveGateway")) {
            // ????????????

        } else if (type.equals("parallelGateWay")) {
            // ????????????
            if (variables.get("isConcurrent").toString().equals("0")) {
                variables.put("isConcurrent", 1);
            } else {
                variables.put("isConcurrent", 0);
            }
            // ??????????????????
            currentNode.put("status", 1);
            // ???????????????
            excuteNext(nodeMap, nodeLastMap, nodeNextMap, variables, request);
        } else if (type.equals("manualSelection")) {
            // ????????????
            // ??????????????????
            currentNode.put("status", 1);
            if (variables.get("isConcurrent").toString().equals("0")) {
                variables.put("isConcurrent", 1);
            } else {
                variables.put("isConcurrent", 0);
            }
            // ???????????????
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
            // ????????????
            currentNode.put("status", 1);
            variables.put("flowFlag", true);
        }
        resourceJson.put("nodes", nodeMap);
        return resourceJson;
    }

    /**
     * ?????????????????????
     *
     * @param nextNodeInfo         ????????????
     * @param sponsorCompany       ???????????????
     * @param sponsor              ?????????
     * @param approverOptionalList ??????????????????????????????
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
                    // ????????????
                    JSONObject assigneeObject = assigneeArray.getJSONObject(i);
                    assigneeSet.add(assigneeObject.getString("id"));
                } else if (type == 2) {
                    // ??????
                    List<String> userList = userRelateMapper.queryUserByRoleId(assigneeArray.getLong(i));
                    for (int l = 0; l < userList.size(); l++) {
                        assigneeSet.add(userList.get(l));
                    }
                } else if (type == 6) {
                    // ???????????????
                    JSONObject assigneeObject = assigneeArray.getJSONObject(i);
                    assigneeSet.add(assigneeObject.getString("id"));
                }
            }
            if (type == 6 && assigneeSet.size() > 1) {
                // ???????????????
                if (StringUtils.isEmpty(approverOptionalList)) {
                    throw new FlowOptionalException(assigneeArray.toJSONString());
                } else {
                    assigneeSet = Arrays.asList(approverOptionalList.toString().split(","));
                }
            }
        } else if (type == 3) {
            // ????????????
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
            // ????????????
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
            // ?????????????????????????????????
            SysDepartment department = departmentMapper.selectById(nextNodeInfo.getLong("value"));
            if (department != null) {
                if (nextNodeInfo.getIntValue("appoint") == 1) {
                    // ????????????
                    if (!StringUtils.isEmpty(department.getDirector())) {
                        String[] directorArray = department.getDirector().split(",");
                        for (int l = 0; l < directorArray.length; l++) {
                            assigneeSet.add(directorArray[l]);
                        }
                    }
                } else if (nextNodeInfo.getIntValue("appoint") == 2) {
                    // ????????????
                    if (!StringUtils.isEmpty(department.getChargeLeader())) {
                        String[] chargeLeaderArray = department.getChargeLeader().split(",");
                        for (int l = 0; l < chargeLeaderArray.length; l++) {
                            assigneeSet.add(chargeLeaderArray[l]);
                        }
                    }
                }
            }
        } else if (type == 7) {
            // ???????????????
            assigneeSet.add(sponsor);
        }
        return assigneeSet;
    }

    /**
     * ????????????
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
     * ????????????
     *
     * @param condition ??????
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
                    // ???????????????
                    SysCompany company = companyMapper.selectById(sponsorCompany);
                    if (company != null) {
                        condition = condition.replace(element, "'" + company.getName() + "'");
                    }
                } else if (field.equals("departmentName")) {
                    // ???????????????
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
                    // ???????????????
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
                        condition = condition.replace(element, "'??????????????????'");
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
     * ????????????
     *
     * @param businessType ????????????
     * @param businessKey  ???????????????
     * @param assignee     ?????????????????????
     * @param status       ??????(1:??????;2:??????;3:??????;)
     * @param comment      ??????
     */
    @Transactional
    @Override
    public void complete(Map<String, Object> variables, HttpServletRequest request) {
        LambdaQueryWrapper<SeaInstance> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SeaInstance::getBusinessType, variables.get("businessType"))
                .eq(SeaInstance::getBusinessKey, variables.get("businessKey"));
        SeaInstance seaInstance = seaInstanceMapper.selectOne(queryWrapper);
        if (seaInstance == null) {
            throw new FlowException("????????????????????????");
        }

        variables.put("defId", seaInstance.getId());
        variables.put("resource", seaInstance.getResources());
        variables.put("version", seaInstance.getVersion());
        variables.put("title", "?????????" + seaInstance.getName());

        int status = Integer.valueOf(variables.get("status").toString());

        Map<String, Object> currentNode = seaNodeMapper.queryCurrentNodeDetail(String.valueOf(seaInstance.getId()),
                seaInstance.getVersion(), variables.get("userId").toString());
        if (currentNode == null) {
            throw new FlowException("????????????????????????");
        } else {
            // ??????????????????
            variables.put("isConcurrent", currentNode.get("isConcurrent"));

            JSONObject resourceJson = JSONObject.parseObject(variables.get("resource").toString());
            JSONObject nodeMap = resourceJson.getJSONObject("nodes");
            JSONArray edges = resourceJson.getJSONArray("edges");
            JSONObject nodeNextMap = new JSONObject();
            JSONObject nodeLastMap = new JSONObject();

            // ??????????????????
            JSONObject nodeCurMap = nodeMap.getJSONObject(currentNode.get("target").toString());
            if (!StringUtils.isEmpty(nodeCurMap.getString("precondition"))) {
            	JellyMetaFunction flowRule = metaFunctionMapper.selectById(nodeCurMap.getString("precondition"));
                if (flowRule != null) {
                    try {
                    	Map<String, Object> params = new HashMap<String, Object>();
                    	IGroovyRule groovyRule = GroovyFactory.getInstance().getIRuleFromCode(flowRule.getScript());
                        groovyRule.execute(request, params);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new FlowException("????????????????????????");
                    }
                }
            }

            for (int i = 0; i < edges.size(); i++) {
                JSONObject edge = edges.getJSONObject(i);
                // ????????????
                if (nodeNextMap.containsKey(edge.getString("source"))) {
                    JSONArray target = nodeNextMap.getJSONArray(edge.getString("source"));
                    target.add(edge.getString("target"));
                    nodeNextMap.put(edge.getString("source"), target);
                } else {
                    JSONArray target = new JSONArray();
                    target.add(edge.getString("target"));
                    nodeNextMap.put(edge.getString("source"), target);
                }
                // ????????????
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
            // ?????????
            variables.put("sponsorCompany", seaInstance.getCompanyId());
            variables.put("sponsor", seaInstance.getUserId());
            // ??????????????????
            SeaNodeDetail seaNodeDetail = new SeaNodeDetail();
            seaNodeDetail.setId(Long.valueOf(currentNode.get("nodeDetailId").toString()));
            seaNodeDetail.setEndTime(new Date());
            // ????????????
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
            // ????????????(???????????????)
            int way = nodeMap.getJSONObject(currentNode.get("target").toString()).getIntValue("way");
            if (way == 0) {
                // ??????????????????
                if (currentNode.get("target").toString().equals("start")) {
                    SeaNode seaNode = new SeaNode();
                    seaNode.setId(Long.valueOf(currentNode.get("id").toString()));
                    seaNode.setEndTime(new Date());
                    seaNode.setStatus(status);
                    seaNodeMapper.updateById(seaNode);

                    // ??????????????????
                    variables.put("currentNodeId", currentNode.get("target"));
                    variables.put("source", currentNode.get("target"));
                    JSONObject jsonObject = excuteNext(nodeMap, nodeLastMap, nodeNextMap, variables, request);
                    jsonObject.put("edges", edges);
                    seaInstance.setResources(jsonObject.toJSONString());
                    seaInstance.setStatus(0);
                    //????????????
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
            if (status == 1) { //????????????
                if (way == 1 || way == 2) {
                    //??????????????????????????????
                    LambdaQueryWrapper<SeaNodeDetail> qw = new LambdaQueryWrapper<>();
                    qw.eq(SeaNodeDetail::getNodeId, currentNode.get("id")).eq(SeaNodeDetail::getStatus, 0);
                    int count = seaNodeDetailMapper.selectCount(qw);
                    //?????????????????????,?????????????????????????????????????????????
                    if (!(way == 2 && count != 0)) {
                        //????????????????????????
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
                            //????????????
                            if (variables.containsKey("flowFlag") && (boolean) variables.get("flowFlag")) {
                                seaInstance.setStatus(status);
                                seaInstance.setEndTime(new Date());
                            } else {
                                seaInstance.setStatus(0);
                            }
                        } else {
                            // ????????????
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
                                //????????????
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
            } else if (status == 2) { //???????????????
                //????????????????????????
                SeaNode seaNode = new SeaNode();
                seaNode.setId(Long.valueOf(currentNode.get("id").toString()));
                seaNode.setEndTime(new Date());
                seaNode.setStatus(status);
                seaNodeMapper.updateById(seaNode);

                seaInstance.setStatus(status);
                variables.put("version", seaInstance.getVersion());
                if (Integer.valueOf(variables.get("rejectType").toString()) == 1) {
                    // ?????????????????????
                    variables.put("currentNodeId", "start");
                    variables.put("source", "start");

                    //??????????????????????????????
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
                    newSeaNode.setName("????????????");
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

                    //??????????????????
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
                    //??????????????????????????????
                    SeaNode nowSeaNode = seaNodeMapper.selectById(currentNode.get("id").toString());
                    //???????????????????????????????????????
                    LambdaQueryWrapper<SeaNode> otherQw = new LambdaQueryWrapper<>();
                    otherQw.eq(SeaNode::getDefId, nowSeaNode.getDefId())
                            .eq(SeaNode::getSource, nowSeaNode.getSource())
                            .eq(SeaNode::getVersion, nowSeaNode.getVersion())
                            .ne(SeaNode::getTarget, nowSeaNode.getTarget())
                            .ne(SeaNode::getTarget, "start")
                            .orderByDesc(SeaNode::getId);
                    List<SeaNode> seaNodes = seaNodeMapper.selectList(otherQw);
                    //????????????????????????????????????
                    if (seaNodes != null && seaNodes.size() > 0) {
                        //?????????target?????????
                        seaNodes = seaNodes.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(SeaNode::getTarget))), ArrayList::new));
                        //??????????????????????????????
                        for (SeaNode node : seaNodes) {
                            //??????????????????????????????????????????????????????,????????????
                            if (node.getStatus() == 1 && node.getType() == 1) {
                                throw new FlowException("???????????????????????????????????????????????????");
                            }
                            node.setStatus(status);
                            seaNodeMapper.updateById(node);
                            //??????????????????????????????
                            resourceJson.getJSONObject("nodes").getJSONObject(node.getTarget()).remove("status");
                        }
                    }
                    // ?????????????????????
                    List<String> lastNodeTargetList = getLastNode(currentNode.get("target").toString(), currentNode.get("source").toString(), nodeMap, nodeLastMap);
                    if (lastNodeTargetList.size() == 0) {
                        throw new FlowException("?????????????????????????????????");
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
                            newSeaNode.setName("????????????");
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
                                // ??????????????????????????????
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
                                // ????????????id
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
                throw new RuntimeException("????????????");
            }
        }
    }

    /**
     * ??????????????????
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
                            // ?????????????????????????????????
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
                        // ????????????????????????
                        nodeMap.getJSONObject(lastNodeItem.get("id").toString()).remove("status");
                        return getLastNode(lastNodeItem.getString("id"), source, nodeMap, lastNodeMap);
                    }
                } else {
                    // ???????????????
                }
            }
        }
        if (result.size() == 0) {
            result.add("start");
        }
        return result;
    }

    /**
     * ????????????
     */
    @Transactional
    @Override
    public void restartProcess(Map<String, Object> variables, HttpServletRequest request) {
        LambdaQueryWrapper<SeaInstance> qw = new LambdaQueryWrapper<>();
        qw.eq(SeaInstance::getBusinessType, variables.get("businessType"))
                .eq(SeaInstance::getBusinessKey, variables.get("businessKey"));
        SeaInstance seaInstance = seaInstanceMapper.selectOne(qw);
        if (seaInstance == null) {
            throw new FlowException("????????????????????????");
        } else {
            variables.put("isConcurrent", 0);
            if (seaInstance.getStatus() == 2 || seaInstance.getStatus() == 3) {
                // ??????????????????
                Map<String, Object> currentNode = seaNodeMapper.queryCurrentNodeDetail(
                        String.valueOf(seaInstance.getId()), seaInstance.getVersion(),
                        variables.get("userId").toString());
                if (currentNode == null) {
                    throw new FlowException("????????????????????????");
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
                    variables.put("title", "???????????????" + seaInstance.getName());
                    // ?????????
                    variables.put("sponsorCompany", seaInstance.getCompanyId());
                    variables.put("sponsor", seaInstance.getUserId());
                    // ????????????id
                    variables.put("currentNodeId", "start");
                    variables.put("source", "start");
                    JSONObject resourceJson = JSONObject.parseObject(seaInstance.getResources());
                    JSONObject nodeMap = resourceJson.getJSONObject("nodes");
                    JSONArray edges = resourceJson.getJSONArray("edges");
                    JSONObject nodeLastMap = new JSONObject();
                    JSONObject nodeNextMap = new JSONObject();
                    for (int i = 0; i < edges.size(); i++) {
                        JSONObject edge = edges.getJSONObject(i);
                        // ????????????
                        if (nodeLastMap.containsKey(edge.getString("target"))) {
                            JSONArray source = nodeLastMap.getJSONArray(edge.getString("target"));
                            source.add(edge.getString("source"));
                            nodeLastMap.put(edge.getString("target"), source);
                        } else {
                            JSONArray source = new JSONArray();
                            source.add(edge.getString("source"));
                            nodeLastMap.put(edge.getString("target"), source);
                        }
                        // ????????????
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
                throw new FlowException("????????????????????????????????????????????????");
            }
        }
    }

    /**
     * ??????
     *
     * @param businessType ????????????
     * @param businessKey  ????????????
     */
    @Transactional
    @Override
    public void revoke(String businessType, String businessKey, String reason) {
        LambdaQueryWrapper<SeaInstance> qw = new LambdaQueryWrapper<>();
        qw.eq(SeaInstance::getBusinessType, businessType)
                .eq(SeaInstance::getBusinessKey, businessKey);
        SeaInstance seaInstance = seaInstanceMapper.selectOne(qw);
        if (seaInstance == null) {
            throw new FlowException("????????????????????????");
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
                    seaNode.setName("??????");
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
                    startSeaNode.setName("????????????");
                    seaNodeMapper.insert(startSeaNode);

                    SeaNodeDetail startSeaNodeDetail = new SeaNodeDetail();
                    startSeaNodeDetail.setNodeId(startSeaNode.getId());
                    startSeaNodeDetail.setName(user.getName());
                    startSeaNodeDetail.setAssignee(String.valueOf(seaInstance.getUserId()));
                    seaNodeDetailMapper.insert(startSeaNodeDetail);

                    // ??????????????????
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
                    throw new FlowException("??????????????????????????????");
                }
            } else if (seaInstance.getStatus() == 1) {
                throw new FlowException("??????????????????????????????");
            } else if (seaInstance.getStatus() == 2) {
                throw new FlowException("??????????????????????????????");
            } else if (seaInstance.getStatus() == 3) {
                throw new FlowException("??????????????????????????????");
            }
        }
    }

    /**
     * ??????
     *
     * @param assignee     ?????????
     * @param businessType ????????????
     * @param businessKey  ????????????
     */
    @Transactional
    @Override
    public void abandon(Long assignee, String businessType, String businessKey) {
        LambdaQueryWrapper<SeaInstance> qw = new LambdaQueryWrapper<>();
        qw.eq(SeaInstance::getBusinessType, businessType).eq(SeaInstance::getBusinessKey, businessKey);
        SeaInstance seaInstance = seaInstanceMapper.selectOne(qw);
        if (seaInstance == null) {
            throw new FlowException("????????????????????????");
        } else {
            // ?????????????????????
            Map<String, Object> currentNode = seaNodeMapper.queryCurrentNodeByAssignee(seaInstance.getId(),
                    String.valueOf(assignee));
            if (currentNode == null) {
                throw new FlowException("??????????????????????????????");
            } else if ("4".equals(currentNode.get("procdefStatus").toString())) {
                throw new FlowException("????????????????????????????????????");
            } else if (!"1".equals(currentNode.get("status").toString())) {
                throw new FlowException("??????????????????????????????????????????");
            } else {
                // ????????????(????????????????????????)
                List<Map<String, Object>> nextNodes = seaNodeMapper
                        .queryNextNode(Long.parseLong(currentNode.get("id").toString()));
                // ???????????????????????????
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
                            throw new FlowException("??????????????????????????????????????????");
                        }
                    }
                    updateNodeFlag = true;
                } else {
                    // ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                    seaInstance.setStatus(0);
                }

                // ?????????
                List<String> currentAgentList = new ArrayList<>();
                // ??????????????????????????????
                LambdaQueryWrapper<SeaNodeDetail> seaNodeDetailQueryWrapper = new LambdaQueryWrapper<>();
                seaNodeDetailQueryWrapper.eq(SeaNodeDetail::getNodeId, currentNode.get("id"));
                List<SeaNodeDetail> seaNodeDetailList = seaNodeDetailMapper.selectList(seaNodeDetailQueryWrapper);

                if (updateNodeFlag) {
                    // ???????????????????????????
                    for (Map<String, Object> nextNode : nextNodes) {
                        seaNode.setId(Long.parseLong(nextNode.get("id").toString()));
                        seaNode.setStatus(3);
                        seaNode.setEndTime(new Date());
                        seaNodeMapper.updateById(seaNode);
                    }
                }

                // ?????????????????????????????????
                JSONObject jsonObject = JSONObject.parseObject(seaInstance.getResources());
                int way = jsonObject.getJSONObject("nodes").getJSONObject(currentNode.get("target").toString()).getIntValue("way");
                if (way == 2) {
                    // ??????
                    if (!"0".equals(currentNode.get("status").toString())) {
                        // ????????????????????????????????????????????????????????????????????????????????????????????????
                        seaNode = new SeaNode();
                        seaNode.setStatus(0);
                        seaNode.setId(Long.parseLong(currentNode.get("id").toString()));
                        seaNodeMapper.updateById(seaNode);
                    }
                    // ??????????????????
                    SysAccount sysUser = userMapper.selectById(assignee);
                    SeaNodeDetail qsSeaNodeDetail = new SeaNodeDetail();
                    qsSeaNodeDetail.setNodeId(Long.parseLong(currentNode.get("id").toString()));
                    qsSeaNodeDetail.setName(sysUser.getName());
                    qsSeaNodeDetail.setAssignee(String.valueOf(assignee));
                    qsSeaNodeDetail.setStatus(6);
                    qsSeaNodeDetail.setEndTime(new Date());
                    seaNodeDetailMapper.insert(qsSeaNodeDetail);
                    // ??????????????????????????????
                    boolean insertFlag = true;
                    for (SeaNodeDetail detail : seaNodeDetailList) {
                        //????????????????????????
                        if (assignee != null && assignee.equals(Long.valueOf(detail.getAssignee())) && insertFlag) {
                            //??????????????????
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
                    // ???????????????????????????????????????????????????????????????????????????????????????????????????
                    currentAgentList = currentAgentList.stream().distinct().collect(Collectors.toList());
                } else {
                    // ??????
                    // ????????????????????????
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

                    // ??????????????????????????????
                    seaNode = new SeaNode();
                    seaNode.setDefId(Long.parseLong(currentNode.get("defId").toString()));
                    seaNode.setVersion(Integer.parseInt(currentNode.get("version").toString()));
                    seaNode.setSource(currentNode.get("source").toString());
                    seaNode.setTarget(currentNode.get("target").toString());
                    seaNode.setName(currentNode.get("name").toString());
                    seaNode.setType(Integer.parseInt(currentNode.get("type").toString()));
                    seaNodeMapper.insert(seaNode);

                    // ??????????????????????????????
                    for (SeaNodeDetail detail : seaNodeDetailList) {
                        seaNodeDetail = new SeaNodeDetail();
                        seaNodeDetail.setNodeId(seaNode.getId());
                        seaNodeDetail.setName(detail.getName());
                        seaNodeDetail.setAssignee(detail.getAssignee());
                        seaNodeDetailMapper.insert(seaNodeDetail);
                        currentAgentList.add(detail.getName());
                    }
                }
                // ??????????????????
                JSONObject nodes = jsonObject.getJSONObject("nodes");
                JSONArray edges = jsonObject.getJSONArray("edges");
                JSONObject nodeNextMap = new JSONObject();
                for (int i = 0; i < edges.size(); i++) {
                    JSONObject edge = edges.getJSONObject(i);
                    // ????????????
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
                //???????????????????????????????????????/????????????/
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
                    // ???????????????????????????????????????
                    nodes.getJSONObject("end").remove("status");
                }
                jsonObject.put("nodes", nodes);
                seaInstance.setResources(jsonObject.toJSONString());
                // ???????????????
                seaInstance.setCurrentAgent(org.apache.commons.lang.StringUtils.join(currentAgentList, ","));
                seaInstanceMapper.updateById(seaInstance);
            }
        }
    }

    /**
     * ??????????????????
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
     * ????????????????????????
     *
     * @param resource ??????json
     * @param nodeId   ??????id
     */
    @Override
    public String getOperationAuthority(JSONObject jsonObject, String nodeId) {
        return jsonObject.getJSONObject("nodes").getJSONObject(nodeId).getString("authority");
    }

    /**
     * ??????????????????
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
     * ????????????
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
     * ????????????
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
     * ????????????
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
     * ????????????
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
     * ????????????
     */
    @Override
    public ResultData queryReadyItem(Integer pageNo, Integer pageSize, Long companyId, Long userId) {
        PageHelper.startPage(pageNo, pageSize);
        List<Map<String, Object>> list = seaInstanceMapper.queryReadyItem(companyId, String.valueOf(userId));
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        return ResultData.success(pageInfo);
    }

    /**
     * ??????????????????
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
     * ??????
     */
    @Transactional
    @Override
    public ResultData close(Long userId, String businessType, String businessKeys, String reason) {
        if (StringUtils.isEmpty(businessKeys)) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "????????????????????????????????????");
        }
        String[] businessKeyArr = businessKeys.split(",");
        LambdaQueryWrapper<SeaInstance> qw = new LambdaQueryWrapper<>();
        qw.eq(SeaInstance::getBusinessType, businessType).in(SeaInstance::getBusinessKey, Arrays.asList(businessKeyArr));
        List<SeaInstance> seaInstanceList = seaInstanceMapper.selectList(qw);
        if (CollectionUtils.isEmpty(seaInstanceList)) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "????????????");
        }
        SysAccount sysUser = userMapper.selectById(userId);
        String userName = sysUser.getName();

        SeaNode seaNode;
        SeaNodeDetail seaNodeDetail;
        for (SeaInstance seaInstance : seaInstanceList) {
            // ???????????????????????????????????????????????????
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
     * ??????
     */
    @Transactional
    @Override
    public ResultData open(Long userId, String businessType, String businessKeys) {
        if (StringUtils.isEmpty(businessKeys)) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "????????????????????????????????????");
        }
        String[] businessKeyArr = businessKeys.split(",");
        LambdaQueryWrapper<SeaInstance> qw = new LambdaQueryWrapper<>();
        qw.eq(SeaInstance::getBusinessType, businessType).in(SeaInstance::getBusinessKey, Arrays.asList(businessKeyArr));
        List<SeaInstance> seaInstanceList = seaInstanceMapper.selectList(qw);
        if (CollectionUtils.isEmpty(seaInstanceList)) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "????????????");
        }
        SysAccount sysUser = userMapper.selectById(userId);
        String userName = sysUser.getName();

        SeaNode seaNode;
        SeaNodeDetail seaNodeDetail;
        LambdaUpdateWrapper<SeaInstance> updateWrapper;
        for (SeaInstance seaInstance : seaInstanceList) {
            // ??????????????????????????????
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
