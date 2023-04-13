package com.seagox.oa.excel.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seagox.oa.common.ResultCode;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.*;
import com.seagox.oa.excel.mapper.*;
import com.seagox.oa.excel.service.IJellyFormService;
import com.seagox.oa.exception.ConfirmException;
import com.seagox.oa.exception.FlowManualSelectionException;
import com.seagox.oa.exception.FlowOptionalException;
import com.seagox.oa.flow.entity.SeaInstance;
import com.seagox.oa.flow.mapper.SeaDefinitionMapper;
import com.seagox.oa.flow.mapper.SeaInstanceMapper;
import com.seagox.oa.flow.mapper.SeaNodeDetailMapper;
import com.seagox.oa.flow.mapper.SeaNodeMapper;
import com.seagox.oa.flow.service.IRuntimeService;
import com.seagox.oa.groovy.GroovyFactory;
import com.seagox.oa.groovy.IGroovyRule;
import com.seagox.oa.sys.entity.SysAccount;
import com.seagox.oa.sys.entity.SysCompany;
import com.seagox.oa.sys.entity.SysMessage;
import com.seagox.oa.sys.entity.SysUserRelate;
import com.seagox.oa.sys.mapper.*;
import com.seagox.oa.util.ExportUtils;
import com.seagox.oa.util.JdbcTemplateUtils;
import com.seagox.oa.util.TreeUtils;
import com.seagox.oa.util.XmlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class JellyFormService implements IJellyFormService {

    @Autowired
    private JellyFormMapper formMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private JellyBusinessFieldMapper businessFieldMapper;

    @Autowired
    private IRuntimeService runtimeService;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private SysAccountMapper userMapper;

    @Autowired
    private SeaInstanceMapper seaProcdefMapper;

    @Autowired
    private SeaNodeMapper seaNodeMapper;

    @Autowired
    private JellyMetaFunctionMapper metaFunctionMapper;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private SysMessageMapper sysMessageMapper;

    @Autowired
    private JellyRegionsMapper regionsMapper;

    @Autowired
    private SeaNodeDetailMapper seaNodeDetailMapper;

    @Autowired
    private UserRelateMapper userRelateMapper;

    @Autowired
    private SeaDefinitionMapper seaDefinitionMapper;

    @Value(value = "${spring.datasource.url}")
    private String datasourceUrl;
    
    @Autowired
    private JellyBusinessTableMapper businessTableMapper;

    @Override
    public ResultData queryByPage(Integer pageNo, Integer pageSize, Long companyId, String name) {
        PageHelper.startPage(pageNo, pageSize);
        LambdaQueryWrapper<JellyForm> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(JellyForm::getCompanyId, companyId).like(!StringUtils.isEmpty(name), JellyForm::getName, name);
        List<JellyForm> list = formMapper.selectList(queryWrapper);
        PageInfo<JellyForm> pageInfo = new PageInfo<JellyForm>(list);
        return ResultData.success(pageInfo);
    }

    @Override
    public ResultData queryByCompanyId(Long companyId) {
        LambdaQueryWrapper<JellyForm> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(JellyForm::getCompanyId, companyId);
        List<JellyForm> list = formMapper.selectList(queryWrapper);
        return ResultData.success(list);
    }

    @Transactional
    @Override
    public ResultData insert(JellyForm form) {
        String notAllowedKeyWords = "create |alter |drop |grant |deny |revoke |update |insert |delete ";
        String keyStr[] = notAllowedKeyWords.split("\\|");
        String dataSource = form.getDataSource().toLowerCase();
        for (String str : keyStr) {
            if (dataSource.contains(str)) {
                return ResultData.warn(ResultCode.OTHER_ERROR, "sql包含关键字" + str);
            }
        }
        Map<String, Object> params = new HashMap<String, Object>();
        if (datasourceUrl.contains("mysql")) {
            params.put("_databaseId", "mysql");
        } else if (datasourceUrl.contains("postgresql")) {
            params.put("_databaseId", "postgresql");
        } else if (datasourceUrl.contains("kingbase")) {
            params.put("_databaseId", "kingbase");
        } else if (datasourceUrl.contains("oracle")) {
            params.put("_databaseId", "oracle");
        } else if (datasourceUrl.contains("dm")) {
            params.put("_databaseId", "dm");
        }
        XmlUtils.sqlAnalysis(dataSource, params, null);
        formMapper.insert(form);
        return ResultData.success(null);
    }

    @Transactional
    @Override
    public ResultData update(JellyForm form) {
        if (!StringUtils.isEmpty(form.getDataSource())) {
            String notAllowedKeyWords = "create |alter |drop |grant |deny |revoke |update |insert |delete ";
            String keyStr[] = notAllowedKeyWords.split("\\|");
            String dataSource = form.getDataSource().toLowerCase();
            for (String str : keyStr) {
                if (dataSource.contains(str)) {
                    return ResultData.warn(ResultCode.OTHER_ERROR, "sql包含关键字" + str);
                }
            }
            Map<String, Object> params = new HashMap<String, Object>();
            if (datasourceUrl.contains("mysql")) {
                params.put("_databaseId", "mysql");
            } else if (datasourceUrl.contains("postgresql")) {
                params.put("_databaseId", "postgresql");
            } else if (datasourceUrl.contains("kingbase")) {
                params.put("_databaseId", "kingbase");
            } else if (datasourceUrl.contains("oracle")) {
                params.put("_databaseId", "oracle");
            } else if (datasourceUrl.contains("dm")) {
                params.put("_databaseId", "dm");
            }
            XmlUtils.sqlAnalysis(dataSource, params, null);
        }
        formMapper.updateById(form);
        return ResultData.success(null);
    }

    @Transactional
    @Override
    public ResultData delete(Long id) {
        formMapper.deleteById(id);
        return ResultData.success(null);
    }

    @Override
    public ResultData queryById(Long userId, Long id) {
        JellyForm form = formMapper.selectById(id);
        return ResultData.success(form);
    }

    @Transactional
    @Override
    public ResultData insertCustom(HttpServletRequest request) {
        Long formId = Long.valueOf(request.getParameter("$formId"));
        JellyForm form = formMapper.selectById(formId);
        // 必填验证
        List<Map<String, Object>> requiredList = businessFieldMapper.queryRequiredByFormId(String.valueOf(form.getTableId()).split(","));
        // 过滤字段
        List<String> filterField = new ArrayList<>(Arrays.asList("company_id", "user_id", "is_submit"));
        for (int i = 0; i < requiredList.size(); i++) {
            Map<String, Object> item = requiredList.get(i);
            if (!filterField.contains(item.get("name").toString())
                    && StringUtils.isEmpty(request.getParameter(item.get("name").toString()))) {
                return ResultData.warn(ResultCode.PARAMETER_ERROR, item.get("remark") + "不能为空");
            }
        }

        Map<String, Object> params = new HashMap<>();
        params.put("formId", formId);
        params.put("dataSource", form.getDataSource());
        params.put("tableId", form.getTableId());
        Long businessKey = insertLogic(request, params);
        SysAccount user = userMapper.selectById(request.getParameter("userId"));
        if (form.getFlowId() != null) {
            Map<String, Object> variables = new HashMap<>();
            variables.put("companyId", request.getParameter("companyId"));
            variables.put("userId", request.getParameter("userId"));
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            variables.put("title", form.getName() + "上报" + "(" + user.getName() + " " + sdf.format(date) + ")");
            variables.put("resource", seaDefinitionMapper.selectById(form.getFlowId()).getResources());
            variables.put("businessType", formId);
            variables.put("businessKey", businessKey);
            if (!StringUtils.isEmpty(request.getParameter("approverOptionalList"))) {
                variables.put("approverOptionalList", request.getParameter("approverOptionalList"));
            }
            if (!StringUtils.isEmpty(request.getParameter("flowOptionalList"))) {
                variables.put("flowOptionalList", request.getParameter("flowOptionalList"));
            }
            if (StringUtils.isEmpty(request.getParameter("temporaryStorage")) || "false".equals(request.getParameter("temporaryStorage"))) {
                // 提交
                runtimeService.startProcess(variables, request);
            } else {
                // 保存
                SysMessage message = new SysMessage();
                message.setCompanyId(Long.valueOf(variables.get("companyId").toString()));
                message.setType(4);
                message.setFromUserId(Long.valueOf(variables.get("userId").toString()));
                message.setToUserId(Long.valueOf(variables.get("userId").toString()));
                message.setTitle(variables.get("title").toString());
                JSONObject variableObject = new JSONObject();
                variableObject.put("businessType", variables.get("businessType").toString());
                variableObject.put("businessKey", variables.get("businessKey").toString());
                message.setBusinessType(Long.valueOf(variables.get("businessType").toString()));
                message.setBusinessKey(Long.valueOf(variables.get("businessKey").toString()));
                sysMessageMapper.insert(message);
            }
        }

        return ResultData.success(null);
    }

    public Long insertLogic(HttpServletRequest request, Map<String, Object> params) {
        JellyBusinessTable businessTable = businessTableMapper.selectById(params.get("tableId").toString());
        Map<String, Object> fieldParams = new HashMap<String, Object>();
        LambdaQueryWrapper<JellyBusinessField> qw = new LambdaQueryWrapper<>();
        qw.eq(JellyBusinessField::getBusinessTableId, businessTable.getId());
        List<JellyBusinessField> businessFieldList = businessFieldMapper.selectList(qw);
        for (int i = 0; i < businessFieldList.size(); i++) {
        	JellyBusinessField businessField = businessFieldList.get(i);
            if (businessField.getName().equals("user_id")) {
                fieldParams.put("user_id", request.getParameter("userId"));
            } else if (businessField.getName().equals("company_id")) {
                fieldParams.put("company_id", request.getParameter("companyId"));
            } else if (businessField.getName().equals("is_submit")) {
                if (StringUtils.isEmpty(request.getParameter("temporaryStorage")) || "false".equals(request.getParameter("temporaryStorage"))) {
                    fieldParams.put("is_submit", 1);
                } else {
                	fieldParams.put("is_submit", 2);
                }  
            } else {
                String value = request.getParameter(businessField.getName());
                if (!StringUtils.isEmpty(value)) {
                	fieldParams.put(businessField.getName(), value);
                }
            }
        }
        Long businessKey = JdbcTemplateUtils.insert(jdbcTemplate, businessTable.getName(), fieldParams);
        return businessKey;
    }

    @Transactional
    @Override
    public ResultData updateCustom(HttpServletRequest request) {
        JellyForm form = formMapper.selectById(request.getParameter("businessType"));
        // 必填验证
        List<Map<String, Object>> requiredList = businessFieldMapper.queryRequiredByFormId(form.getDataSource().split(","));
        // 过滤字段
        List<String> filterField = new ArrayList<>(Arrays.asList("company_id", "user_id", "is_submit"));
        for (int i = 0; i < requiredList.size(); i++) {
            Map<String, Object> item = requiredList.get(i);
            if (!filterField.contains(item.get("name").toString())
                    && StringUtils.isEmpty(request.getParameter(item.get("tableName") + "." + item.get("name")))) {
                return ResultData.warn(ResultCode.PARAMETER_ERROR, item.get("comment") + "不能为空");
            }
        }
        // 更新前规则
        JSONObject options = JSONObject.parseObject(form.getOptions());
        if (options != null && options.containsKey("updateBeforeRule")) {
        	JellyMetaFunction updateBeforeRule = metaFunctionMapper.selectById(options.getLong("updateBeforeRule"));
            try {
            	IGroovyRule groovyRule = GroovyFactory.getInstance().getIRuleFromCode(updateBeforeRule.getScript());
                groovyRule.execute(request, null);
            } catch (ConfirmException e) {
                throw new ConfirmException(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                return ResultData.warn(ResultCode.OTHER_ERROR, e.getMessage());
            }
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("dataSource", form.getDataSource());
        updateLogic(request, params);
        if (form.getFlowId() != null) {
            LambdaQueryWrapper<SeaInstance> qw = new LambdaQueryWrapper<>();
            qw.eq(SeaInstance::getBusinessType, form.getId())
                    .eq(SeaInstance::getBusinessKey, request.getParameter("businessKey"));
            SeaInstance seaInstance = seaProcdefMapper.selectOne(qw);
            if (seaInstance != null && !Boolean.valueOf(request.getParameter("temporaryStorage"))) {
                // 审批
                Map<String, Object> variables = new HashMap<>();
                variables.put("businessType", form.getId());
                variables.put("businessKey", request.getParameter("businessKey"));
                variables.put("companyId", request.getParameter("companyId"));
                variables.put("userId", request.getParameter("userId"));
                if (!StringUtils.isEmpty(request.getParameter("approverOptionalList"))) {
                    variables.put("approverOptionalList", request.getParameter("approverOptionalList"));
                }
                if (!StringUtils.isEmpty(request.getParameter("flowOptionalList"))) {
                    variables.put("flowOptionalList", request.getParameter("flowOptionalList"));
                }
                if (seaInstance.getStatus() == 0 || seaInstance.getStatus() == 2) {
                    variables.put("status", request.getParameter("status"));
                    variables.put("comment", request.getParameter("comment"));
                    variables.put("rejectType", request.getParameter("rejectType"));
                    try {
                        runtimeService.complete(variables, request);
                    } catch (FlowManualSelectionException e) {
                        throw new FlowManualSelectionException(e.getMessage());
                    } catch (FlowOptionalException e) {
                        throw new FlowOptionalException(e.getMessage());
                    }catch (Exception e) {
                        e.printStackTrace();
                        // 手动回滚
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return ResultData.warn(ResultCode.OTHER_ERROR, e.getMessage());
                    }
                } else if (seaInstance.getStatus() == 3) {
                    runtimeService.restartProcess(variables, request);
                }
            } else {
                // 提交
                Map<String, Object> variables = new HashMap<>();
                variables.put("companyId", request.getParameter("companyId"));
                variables.put("userId", request.getParameter("userId"));
                SysAccount user = userMapper.selectById(request.getParameter("userId"));
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                variables.put("title", form.getName() + "上报" + "(" + user.getName() + " " + sdf.format(date) + ")");

                variables.put("resource", seaDefinitionMapper.selectById(form.getFlowId()).getResources());
                variables.put("businessType", form.getId());
                variables.put("businessKey", request.getParameter("businessKey"));
                if (!StringUtils.isEmpty(request.getParameter("approverOptionalList"))) {
                    variables.put("approverOptionalList", request.getParameter("approverOptionalList"));
                }
                if (!StringUtils.isEmpty(request.getParameter("flowOptionalList"))) {
                    variables.put("flowOptionalList", request.getParameter("flowOptionalList"));
                }
                if (!Boolean.valueOf(request.getParameter("temporaryStorage"))) {
                    sysMessageMapper.deleteMessage(form.getId(), Long.valueOf(request.getParameter("businessKey")));
                    runtimeService.startProcess(variables, request);
                }
            }
        }
        // 更新后规则
        if (options != null && options.containsKey("updateAfterRule")) {
        	JellyMetaFunction updateAfterRule = metaFunctionMapper.selectById(options.getLong("updateAfterRule"));
            try {
            	IGroovyRule groovyRule = GroovyFactory.getInstance().getIRuleFromCode(updateAfterRule.getScript());
                SysAccount user = userMapper.selectById(request.getParameter("userId"));
                Map<String, Object> ruleParams = new HashMap<>();
                ruleParams.put("username", user.getName());
                ruleParams.put("formName", form.getName());
                groovyRule.execute(request, ruleParams);
            } catch (Exception e) {
                e.printStackTrace();
                // 手动回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultData.warn(ResultCode.OTHER_ERROR, e.getMessage());
            }
        }
        return ResultData.success(null);
    }

    public void updateLogic(HttpServletRequest request, Map<String, Object> params) {;
        Map<String, String> map = new HashMap<String, String>();
        List<Map<String, Object>> dataSheetList = businessFieldMapper.queryRelateByTableIds(params.get("dataSource").toString().split(","));
        for (int i = 0; i < dataSheetList.size(); i++) {
        	Map<String, Object> dataSheet = dataSheetList.get(i);

            List<Map<String, Object>> businessFieldList = businessFieldMapper.queryByTableName(dataSheet.get("tableName").toString(),0);
            if (StringUtils.isEmpty(dataSheet.get("targetTableId")) && StringUtils.isEmpty(dataSheet.get("relateTable"))
                    && StringUtils.isEmpty(dataSheet.get("relateField"))) {
                StringBuffer sql = new StringBuffer();
                String[] valueArray = new String[businessFieldList.size()];
                for (int j = 0; j < businessFieldList.size(); j++) {
                    if (businessFieldList.get(j).get("name").equals("is_submit")) {
                        if (Boolean.valueOf(request.getParameter("temporaryStorage")) && !Boolean.valueOf(request.getParameter("existProdef"))) {
                            valueArray[j] = "2";
                        } else {
                            valueArray[j] = "1";
                        }
                        sql.append("is_submit=?,");
                    } else if (businessFieldList.get(j).get("name").equals(dataSheet.get("relateField"))) {
                        valueArray[j] = map.get(dataSheet.get("relateTable"));
                        sql.append(dataSheet.get("relateField") + "=?,");
                    } else {
                        String value = request
                                .getParameter(dataSheet.get("tableName") + "." + businessFieldList.get(j).get("name"));
                        if (StringUtils.isEmpty(value)) {
                            valueArray[j] = null;
                        } else {
                            valueArray[j] = value;
                        }
                        sql.append(businessFieldList.get(j).get("name") + "=?,");
                    }
                }
                String sourceSql = "UPDATE " + dataSheet.get("tableName") + " set "
                        + sql.toString().substring(0, sql.length() - 1) + " WHERE id="
                        + request.getParameter("businessKey");
                System.out.println(sourceSql);
                // KeyHolder keyHolder = new GeneratedKeyHolder();
                jdbcTemplate.update(new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps = connection.prepareStatement(sourceSql);
                        for (int k = 0; k < valueArray.length; k++) {
                            ps.setString(k + 1, valueArray[k]);
                        }
                        return ps;
                    }
                });

                map.put(dataSheet.get("tableName").toString(), request.getParameter("businessKey"));
            } else {
                jdbcTemplate.update("DELETE FROM " + dataSheet.get("tableName") + " WHERE " + dataSheet.get("relateField")
                        + "=" + map.get(dataSheet.get("relateTable")));

                JSONArray jsonArray = JSON.parseArray(request.getParameter(dataSheet.get("tableName").toString()));
                for (int k = 0; k < jsonArray.size(); k++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(k);
                    String fieldSql = "";
                    String valueSql = "";
                    String[] valueArray = new String[businessFieldList.size()];
                    for (int j = 0; j < businessFieldList.size(); j++) {
                        if (businessFieldList.get(j).get("name").equals(dataSheet.get("relateField"))) {
                            valueArray[j] = map.get(dataSheet.get("relateTable"));
                            fieldSql = fieldSql + dataSheet.get("relateField") + ",";
                            valueSql = valueSql + "?,";
                        } else {
                            Object value = jsonObject.get(businessFieldList.get(j).get("name"));
                            if (StringUtils.isEmpty(value)) {
                                valueArray[j] = null;
                            } else {
                                valueArray[j] = value.toString();
                            }
                            fieldSql = fieldSql + businessFieldList.get(j).get("name") + ",";
                            valueSql = valueSql + "?,";
                        }

                    }
                    if (!StringUtils.isEmpty(fieldSql) && !StringUtils.isEmpty(valueSql)) {
                        fieldSql = fieldSql.substring(0, fieldSql.length() - 1);
                        valueSql = valueSql.substring(0, valueSql.length() - 1);

                        String sql = "insert into " + dataSheet.get("tableName") + "(" + fieldSql + ") values(" + valueSql
                                + ")";

                        KeyHolder keyHolder = new GeneratedKeyHolder();
                        jdbcTemplate.update(new PreparedStatementCreator() {
                            @Override
                            public PreparedStatement createPreparedStatement(Connection connection)
                                    throws SQLException {
                                PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
                                for (int k = 0; k < valueArray.length; k++) {
                                    ps.setString(k + 1, valueArray[k]);
                                }
                                return ps;
                            }
                        }, keyHolder);

                        map.put(dataSheet.get("tableName").toString(), String.valueOf(keyHolder.getKey()));
                    }
                }
            }
        }
    }

    @Override
    public ResultData queryDynamic(Long companyId, String path, String type) {
        if (path.equals("department")) {
            SysCompany company = companyMapper.selectById(companyId);
            List<Map<String, Object>> list = departmentMapper.queryByCode(company.getCode().substring(0, 4));
            if (StringUtils.isEmpty(type)) {
                return ResultData.success(TreeUtils.categoryTreeHandle(list, "parentId", 0L));
            } else {
                if (type.equals("tree")) {
                    return ResultData.success(TreeUtils.categoryTreeHandle(list, "parentId", 0L));
                } else {
                    return ResultData.success(list);
                }
            }
        } else if (path.equals("member")) {
            SysCompany company = companyMapper.selectById(companyId);
            String prefix = company.getCode().substring(0, 4);
            return ResultData.success(userMapper.queryByCompanyId(prefix, companyId, null, null));
        }
        return ResultData.success(null);
    }

    @Override
    public ResultData queryListById(Long companyId, Long userId, Long id, Integer pageNo, Integer pageSize,
                                    String search) {
        JellyForm form = formMapper.selectById(id);
        Map<String, Object> resultData = new HashMap<String, Object>();
        resultData.put("formId", form.getId());
        resultData.put("flowFlag", form.getFlowId() != null);
        resultData.put("options", form.getOptions());

        resultData.put("searchJson", form.getSearchJson());
        resultData.put("tableHeader", form.getTableHeader());

        String sql = form.getDataSource();

        // 数据权限
        List<String> userList = queryUserDataAuthority(companyId, userId, form.getAuthority());
        userList.add(String.valueOf(userId));

        Map<String, Object> searchObject = (Map<String, Object>) JSONObject.parseObject(search);
        searchObject.put("authority", org.apache.commons.lang3.StringUtils.join(userList, ","));
        if (datasourceUrl.contains("mysql")) {
            searchObject.put("_databaseId", "mysql");
        } else if (datasourceUrl.contains("postgresql")) {
            searchObject.put("_databaseId", "postgresql");
        } else if (datasourceUrl.contains("kingbase")) {
            searchObject.put("_databaseId", "kingbase");
        } else if (datasourceUrl.contains("oracle")) {
            searchObject.put("_databaseId", "oracle");
        } else if (datasourceUrl.contains("dm")) {
            searchObject.put("_databaseId", "dm");
        }
        searchObject.put("companyId", companyId);
        searchObject.put("userId", userId);
        sql = XmlUtils.sqlAnalysis(sql, searchObject, null);

        PageHelper.startPage(pageNo, pageSize);
        List<Map<String, Object>> list = formMapper.queryPublicList(sql);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(list);

        resultData.put("tableData", pageInfo);

        // 按钮权限
        queryButtonAuthority(resultData, form.getAuthority(), companyId, userId);

        return ResultData.success(resultData);
    }

    public Map<String, Object> queryButtonAuthority(Map<String, Object> resultData, String authority, Long companyId,
                                                    Long userId) {
        boolean addFlag = false;
        boolean editFlag = false;
        boolean deleteFlag = false;
        boolean exportFlag = false;
        boolean openFlag = false;
        boolean closeFlag = false;
        String generateType = "dialog";

        if (!StringUtils.isEmpty(authority)) {
            List<String> roleList = new ArrayList<>();
            LambdaQueryWrapper<SysUserRelate> userRelateQueryWrapper = new LambdaQueryWrapper<>();
            userRelateQueryWrapper.eq(SysUserRelate::getCompanyId, companyId).eq(SysUserRelate::getUserId, userId);
            SysUserRelate userRelate = userRelateMapper.selectOne(userRelateQueryWrapper);
            if (userRelate != null && !StringUtils.isEmpty(userRelate.getRoleIds())) {
                // 将字符串数组转换成集合
                roleList = Arrays.asList(userRelate.getRoleIds().split(","));

            }
            JSONObject jsonObject = JSON.parseObject(authority);
            if (!StringUtils.isEmpty(jsonObject.getString("add"))) {
                List<String> addRoleStr = Arrays.asList(jsonObject.getString("add").split(","));
                for (String str : addRoleStr) {
                    if (roleList.contains(str)) {
                        addFlag = true;
                        break;
                    }
                }
            }
            if (!StringUtils.isEmpty(jsonObject.getString("edit"))) {
                List<String> editRoleStr = Arrays.asList(jsonObject.getString("edit").split(","));
                for (String str : editRoleStr) {
                    if (roleList.contains(str)) {
                        editFlag = true;
                        break;
                    }
                }
            }
            if (!StringUtils.isEmpty(jsonObject.getString("delete"))) {
                List<String> deleteRoleStr = Arrays.asList(jsonObject.getString("delete").split(","));
                for (String str : deleteRoleStr) {
                    if (roleList.contains(str)) {
                        deleteFlag = true;
                        break;
                    }
                }
            }
            if (!StringUtils.isEmpty(jsonObject.getString("export"))) {
                List<String> exportRoleStr = Arrays.asList(jsonObject.getString("export").split(","));
                for (String str : exportRoleStr) {
                    if (roleList.contains(str)) {
                        exportFlag = true;
                        break;
                    }
                }
            }
            if (!StringUtils.isEmpty(jsonObject.getString("open"))) {
                List<String> openRoleStr = Arrays.asList(jsonObject.getString("open").split(","));
                for (String str : openRoleStr) {
                    if (roleList.contains(str)) {
                        openFlag = true;
                        break;
                    }
                }
            }
            if (!StringUtils.isEmpty(jsonObject.getString("close"))) {
                List<String> closeRoleStr = Arrays.asList(jsonObject.getString("close").split(","));
                for (String str : closeRoleStr) {
                    if (roleList.contains(str)) {
                        closeFlag = true;
                        break;
                    }
                }
            }
            if (!StringUtils.isEmpty(jsonObject.getString("generateType"))) {
                generateType = jsonObject.getString("generateType");
            }

        }
        resultData.put("addFlag", addFlag);
        resultData.put("editFlag", editFlag);
        resultData.put("deleteFlag", deleteFlag);
        resultData.put("exportFlag", exportFlag);
        resultData.put("openFlag", openFlag);
        resultData.put("closeFlag", closeFlag);
        resultData.put("generateType", generateType);
        return resultData;
    }

    @Override
    public ResultData queryDetail(Long userId, Long formId, Long id) {
        JellyForm form = formMapper.selectById(formId);
        Map<String, Object> claims = new HashMap<String, Object>();
        JellyBusinessTable businessTable = businessTableMapper.selectById(form.getTableId());
        int isValid = 0;
        String sql = "select * from " + businessTable.getName() + " where id=" + id;
        claims = jdbcTemplate.queryForMap(sql);
        claims.put("isValid", isValid);
        // 有流程
        if (form.getFlowId() != null) {
            LambdaQueryWrapper<SeaInstance> qw = new LambdaQueryWrapper<>();
            qw.eq(SeaInstance::getBusinessType, formId).eq(SeaInstance::getBusinessKey,
                    id);
            SeaInstance seaInstance = seaProcdefMapper.selectOne(qw);
            if (seaInstance != null) {
                if (seaInstance.getStatus() == 0) {
                    // 待审
                    Map<String, Object> currentNode = seaNodeMapper.queryCurrentNodeDetail(
                            String.valueOf(seaInstance.getId()), seaInstance.getVersion(), String.valueOf(userId));
                    if (currentNode != null) {
                        // 审批按钮权限
                        claims.put("flowStatus", 3);
                        claims.put("authority",
                                runtimeService.getOperationAuthority(JSONObject.parseObject(seaInstance.getResources()),
                                        currentNode.get("target").toString()));
                    }
                    if (seaInstance.getUserId().equals(userId)) {
                        int count = seaNodeMapper.queryApproverCount(String.valueOf(seaInstance.getId()),
                                seaInstance.getVersion());
                        if (count == 0) {
                            if (!StringUtils.isEmpty(claims.get("flowStatus"))) {
                                if (Integer.valueOf(claims.get("flowStatus").toString()) == 3) {
                                    // 撤回、审批按钮权限
                                    claims.put("flowStatus", 4);
                                } else {
                                    // 撤回按钮权限
                                    claims.put("flowStatus", 2);
                                }
                            } else {
                                // 撤回按钮权限
                                claims.put("flowStatus", 2);
                            }
                        }
                    }
                } else if (seaInstance.getStatus() == 1) {
                    // 通过
                    claims.put("flowStatus", 0);
                } else if (seaInstance.getStatus() == 2) {
                    // 驳回
                    // 提交按钮权限
                    Map<String, Object> currentNode = seaNodeMapper.queryCurrentNodeDetail(
                            String.valueOf(seaInstance.getId()), seaInstance.getVersion(), String.valueOf(userId));
                    if (currentNode != null) {
                        if (currentNode.get("target").toString().equals("start")) {
                            claims.put("flowStatus", 1);
                        } else {
                            claims.put("flowStatus", 3);
                        }
                    }
                } else if (seaInstance.getStatus() == 3) {
                    // 撤回
                    // 提交按钮权限
                    if (seaInstance.getUserId().equals(userId)) {
                        claims.put("flowStatus", 1);
                    }
                }
                if (claims.get("flowStatus") == null || !claims.get("flowStatus").equals(1)) {
                    claims.remove("temporaryStorage");
                }
                claims.put("reProcdef", seaInstance.getId());
                form.setHistoryJson(JSON.toJSONString(seaProcdefMapper.queryHistoryOpinion(String.valueOf(form.getId()), String.valueOf(id))));
                // 退审权限
                Map<String, Object> currentNode = seaNodeMapper.queryCurrentNodeByAssignee(seaInstance.getId(),
                        String.valueOf(userId));
                // 开始节点、关闭的流程、当前节点不是通过状态都不显示
                if (currentNode != null && !"开始节点".equals(currentNode.get("name").toString())
                        && !currentNode.get("procdefStatus").equals(4)
                        && "1".equals(currentNode.get("status").toString())) {
                    claims.put("abandonFlag", true);
                } else {
                    claims.put("abandonFlag", false);
                }
            }
        }
        claims.put("designJson", form.getDesign());
        return ResultData.success(claims);
    }

    @Transactional
    @Override
    public ResultData deleteCustom(String businessType, String businessKey, HttpServletRequest request) {
        JellyForm form = formMapper.selectById(businessType);
        JellyBusinessTable businessTable = businessTableMapper.selectById(form.getTableId());
        String sql = "DELETE FROM " + businessTable.getName() + " WHERE id = " + businessKey;
        jdbcTemplate.update(sql);
        return ResultData.success(null);
    }

    @Override
    public ResultData queryBusinessField(Long formId) {
    	JellyForm form = formMapper.selectById(formId);
        LambdaQueryWrapper<JellyBusinessField> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(JellyBusinessField::getBusinessTableId, form.getTableId());
        List<JellyBusinessField> list = businessFieldMapper.selectList(queryWrapper);
        return ResultData.success(list);
    }

    @Override
    public ResultData queryBill(Long id, String field, String prefix, String billDate, int digit) {
        JellyForm form = formMapper.selectById(id);
        JellyBusinessTable businessTable = businessTableMapper.selectById(form.getTableId());
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT LEFT(");
        sql.append(field);
        sql.append(", ");
        sql.append(billDate.equals("no") ? (prefix.length() + digit) : (prefix.length() + billDate.length() + digit));
        sql.append(") FROM ");
        sql.append(businessTable.getName());
        sql.append(" WHERE ");
        sql.append(field);
        sql.append(" like ");

        String str = prefix;
        if (billDate.equals("no")) {
            sql.append("'" + str + "%'");
            sql.append(" AND LENGTH(" + field + ") = ");
            sql.append((prefix.length() + digit + 4));
            sql.append(" ORDER BY ");
            sql.append(field);
            sql.append(" DESC LIMIT 1");
        } else {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat(billDate);
            str = prefix + sdf.format(date);
            sql.append("'" + str + "%'");
            sql.append(" AND LENGTH(" + field + ") = ");
            sql.append((prefix.length() + billDate.length() + digit + 4));
            sql.append(" ORDER BY ");
            sql.append(field);
            sql.append(" DESC LIMIT 1");
        }
        String suffix = String.format("%04d", new Random().nextInt(9999));
        try {
            String result = jdbcTemplate.queryForObject(sql.toString(), String.class);
            int max = Integer.valueOf(result.substring(str.length())) + 1;
            return ResultData.success(str + lpad(digit, max) + suffix);
        } catch (Exception e) {
            return ResultData.success(str + lpad(digit, 1) + suffix);
        }
    }

    /**
     * 补齐不足长度
     *
     * @param length 长度
     * @param number 数字
     * @return
     */
    private String lpad(int length, int number) {
        String f = "%0" + length + "d";
        return String.format(f, number);
    }

    @Override
    public ResultData queryListAllById(Long companyId, Long userId, Long formId) {
        JellyForm form = formMapper.selectById(formId);
        String sql = form.getDataSource();
        Map<String, Object> params = new HashMap<String, Object>();
        if (datasourceUrl.contains("mysql")) {
            params.put("_databaseId", "mysql");
        } else if (datasourceUrl.contains("postgresql")) {
            params.put("_databaseId", "postgresql");
        } else if (datasourceUrl.contains("kingbase")) {
            params.put("_databaseId", "kingbase");
        } else if (datasourceUrl.contains("oracle")) {
            params.put("_databaseId", "oracle");
        } else if (datasourceUrl.contains("dm")) {
            params.put("_databaseId", "dm");
        }
        sql = XmlUtils.sqlAnalysis(sql, params, null);

        return ResultData.success(jdbcTemplate.queryForList(sql));
    }

	@Override
    public void export(HttpServletRequest request, HttpServletResponse response) {
        JellyForm form = formMapper.selectById(request.getParameter("id"));
        String sql = form.getDataSource();

        // 数据权限
        List<String> userList = queryUserDataAuthority(Long.valueOf(request.getParameter("companyId")),
                Long.valueOf(request.getParameter("userId")), form.getAuthority());
        userList.add(request.getParameter("userId"));

        Map<String, Object> searchObject = (Map<String, Object>) JSONObject.parseObject(request.getParameter("search"));
        searchObject.put("authority", org.apache.commons.lang3.StringUtils.join(userList, ","));
        if (datasourceUrl.contains("mysql")) {
            searchObject.put("_databaseId", "mysql");
        } else if (datasourceUrl.contains("postgresql")) {
            searchObject.put("_databaseId", "postgresql");
        } else if (datasourceUrl.contains("kingbase")) {
            searchObject.put("_databaseId", "kingbase");
        } else if (datasourceUrl.contains("oracle")) {
            searchObject.put("_databaseId", "oracle");
        } else if (datasourceUrl.contains("dm")) {
            searchObject.put("_databaseId", "dm");
        } else if (datasourceUrl.contains("sqlserver")) {
            searchObject.put("_databaseId", "sqlserver");
        }
        sql = XmlUtils.sqlAnalysis(sql, searchObject, null);

        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        Map<String, Object> resultData = new HashMap<>();
        resultData.put("list", list);
        // 导出规则
        JSONObject options = JSONObject.parseObject(form.getOptions());
        if (options.containsKey("exportRule")) {
        	JellyMetaFunction exportRule = metaFunctionMapper.selectById(options.getLong("exportRule"));
            try {
                Map<String, Object> params = new HashMap<>();
                params.put("list", list);
                IGroovyRule groovyRule = GroovyFactory.getInstance().getIRuleFromCode(exportRule.getScript());
                Map<String, Object> resultMap = (Map<String, Object>) groovyRule.execute(request, params);
                if (resultMap != null) {
                    resultMap.forEach((key, value) -> {
                        resultData.put(key, value);
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        JSONArray jsonArray = options.getJSONArray("exportPath");
        ExportUtils.exportExcel(jsonArray.getJSONObject(0).getString("url"), form.getName(), resultData, request,
                response);
    }

    @Override
    public ResultData queryRegions(String type) {
        LambdaQueryWrapper<JellyRegions> qw = new LambdaQueryWrapper<>();
        if (type.equals("1")) {
            // 省
            qw.eq(JellyRegions::getGrade, 1);
        } else if (type.equals("2")) {
            // 省市
            List<Integer> levelList = new ArrayList<>();
            levelList.add(1);
            levelList.add(2);
            qw.in(JellyRegions::getGrade, levelList);
        } else if (type.equals("3")) {
            // 省市区
            List<Integer> levelList = new ArrayList<>();
            levelList.add(1);
            levelList.add(2);
            levelList.add(3);
            qw.in(JellyRegions::getGrade, levelList);
        } else if (type.equals("4")) {
            // 省市区-街道
        } else {
            // 指定地区
            LambdaQueryWrapper<JellyRegions> qwAppoint = new LambdaQueryWrapper<>();
            qwAppoint.eq(JellyRegions::getCode, type);
            JellyRegions regions = regionsMapper.selectOne(qwAppoint);
            qw.gt(JellyRegions::getGrade, regions.getGrade()).likeRight(JellyRegions::getCode, type);
        }
        JSONArray result = TreeUtils.listToTree(JSONArray.parseArray(JSON.toJSONString(regionsMapper.selectMaps(qw))),
                "code", "children");
        return ResultData.success(result);
    }

    @Override
    public ResultData queryRegionsByCode(String codeStr) {
        String reuslt = "";
        if (!StringUtils.isEmpty(codeStr)) {
            LambdaQueryWrapper<JellyRegions> qw = new LambdaQueryWrapper<>();
            qw.in(JellyRegions::getCode, JSONObject.parseArray(codeStr, String.class));
            List<JellyRegions> list = regionsMapper.selectList(qw);
            for (int i = 0; i < list.size(); i++) {
                reuslt = reuslt + list.get(i).getName();
                if (i != (list.size() - 1)) {
                    reuslt += "/";
                }
            }
        }
        return ResultData.success(reuslt);
    }

    public JSONObject excute(JSONObject jsonObject, Map<String, Object> variables) {
        if (jsonObject.getIntValue("type") == 1) {
            List<Map<String, Object>> list = seaNodeDetailMapper.queryProcessDetailByNodeId(
                    variables.get("flow.id").toString(), Integer.valueOf(variables.get("flow.version").toString()),
                    jsonObject.getString("nodeId"));
            if (list.size() == 0) {
                list.add(new HashMap<>());
            }
            variables.put(jsonObject.getString("nodeId"), list);
            // 审批
            JSONObject childNode = jsonObject.getJSONObject("childNode");
            if (childNode == null || childNode.isEmpty()) {
                return jsonObject;
            } else {
                excute(childNode, variables);
            }
        } else if (jsonObject.getIntValue("type") == 2) {
            // 抄送
            JSONObject childNode = jsonObject.getJSONObject("childNode");
            if (childNode == null || childNode.isEmpty()) {
                return jsonObject;
            } else {
                excute(childNode, variables);
            }
        } else if (jsonObject.getIntValue("type") == 3) {
            // 分支条件
            JSONObject childNode = jsonObject.getJSONObject("childNode");
            if (childNode == null || childNode.isEmpty()) {
                return jsonObject;
            } else {
                excute(childNode, variables);
            }

            JSONArray conditionNodes = jsonObject.getJSONArray("conditionNodes");
            for (int i = 0; i < conditionNodes.size(); i++) {
                JSONObject conditionNode = conditionNodes.getJSONObject(i);
                JSONObject conditionChildNode = conditionNode.getJSONObject("childNode");
                if (conditionChildNode != null && !conditionChildNode.isEmpty()) {
                    excute(conditionChildNode, variables);
                }
            }
        }
        return jsonObject;
    }

    public List<String> queryUserDataAuthority(Long companyId, Long userId, String authority) {
        List<String> userList = new ArrayList<>();
        if (!StringUtils.isEmpty(authority)) {
            List<String> roleList = new ArrayList<>();
            LambdaQueryWrapper<SysUserRelate> userRelateQw = new LambdaQueryWrapper<>();
            userRelateQw.eq(SysUserRelate::getCompanyId, companyId).eq(SysUserRelate::getUserId, userId);
            SysUserRelate userRelate = userRelateMapper.selectOne(userRelateQw);
            if (userRelate != null && !StringUtils.isEmpty(userRelate.getRoleIds())) {
                // 将字符串数组转换成集合
                roleList = Arrays.asList(userRelate.getRoleIds().split(","));

            }
            JSONObject jsonObject = JSON.parseObject(authority);
            String dataAuthority = jsonObject.getString("dataAuthority");
            boolean companyFlag = false;
            boolean deptFlag = false;
            if (!StringUtils.isEmpty(dataAuthority)) {
                JSONObject dataAuthorityJson = JSONObject.parseObject(dataAuthority);
                // 查看本公司数据
                JSONArray companyJSONArray = dataAuthorityJson.getJSONArray("company");
                if (!companyJSONArray.isEmpty()) {
                    List<String> copyList = new ArrayList<>(Arrays.asList(new String[roleList.size()]));
                    Collections.copy(copyList, roleList);
                    copyList.retainAll(Arrays.asList(companyJSONArray.toArray()));
                    if (copyList.size() != 0) {
                        companyFlag = true;
                    }
                }
                // 查看本部门数据
                JSONArray deptmentJSONArray = dataAuthorityJson.getJSONArray("deptment");
                if (!deptmentJSONArray.isEmpty()) {
                    List<String> copyList = new ArrayList<>(Arrays.asList(new String[roleList.size()]));
                    Collections.copy(copyList, roleList);
                    copyList.retainAll(Arrays.asList(deptmentJSONArray.toArray()));
                    if (copyList.size() != 0) {
                        deptFlag = true;
                    }
                }
            }
            if (companyFlag) {
                userList.addAll(userRelateMapper.queryUserByCompanyId(companyId));
            } else if (!companyFlag && deptFlag) {
                userList.addAll(userRelateMapper.queryUserByDepartmentId(userRelate.getDepartmentId()));
            }
        }
        return userList;
    }

    @Override
    public ResultData queryBusinessTypes(Long companyId) {
        LambdaQueryWrapper<JellyForm> qw = new LambdaQueryWrapper<>();
        qw.eq(JellyForm::getCompanyId, companyId);
        qw.isNotNull(JellyForm::getFlowId);
        return ResultData.success(formMapper.selectList(qw));
    }

}
