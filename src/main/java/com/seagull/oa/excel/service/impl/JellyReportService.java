package com.seagull.oa.excel.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seagull.oa.common.ResultCode;
import com.seagull.oa.common.ResultData;
import com.seagull.oa.excel.entity.JellyReport;
import com.seagull.oa.excel.entity.JellyTemplateEngine;
import com.seagull.oa.excel.mapper.JellyReportMapper;
import com.seagull.oa.excel.mapper.JellyTemplateEngineMapper;
import com.seagull.oa.excel.service.IJellyReportService;
import com.seagull.oa.util.ExportUtils;
import com.seagull.oa.util.XmlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JellyReportService implements IJellyReportService {

    @Autowired
    private JellyReportMapper reportMapper;

    @Autowired
    private JellyTemplateEngineMapper templateEngineMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value(value = "${spring.datasource.url}")
    private String datasourceUrl;

    @Override
    public ResultData queryByPage(Integer pageNo, Integer pageSize, Long companyId, String name) {
        PageHelper.startPage(pageNo, pageSize);
        LambdaQueryWrapper<JellyReport> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(JellyReport::getCompanyId, companyId).like(!StringUtils.isEmpty(name), JellyReport::getName, name)
                .orderByDesc(JellyReport::getCreateTime);
        List<JellyReport> list = reportMapper.selectList(queryWrapper);
        PageInfo<JellyReport> pageInfo = new PageInfo<>(list);
        return ResultData.success(pageInfo);
    }

    @Transactional
    @Override
    public ResultData insert(JellyReport report) {
        LambdaQueryWrapper<JellyReport> qw = new LambdaQueryWrapper<>();
        qw.eq(JellyReport::getCompanyId, report.getCompanyId()).eq(JellyReport::getName, report.getName());
        int count = reportMapper.selectCount(qw);
        if (count == 0) {
            reportMapper.insert(report);
            return ResultData.success(null);
        } else {
            return ResultData.warn(ResultCode.OTHER_ERROR, "名称已经存在");
        }
    }

    @Transactional
    @Override
    public ResultData update(JellyReport report) {
        JellyReport originalReport = reportMapper.selectById(report.getId());
        if (originalReport.getName().equals(report.getName())) {
            reportMapper.updateById(report);
            return ResultData.success(null);
        } else {
            LambdaQueryWrapper<JellyReport> qw = new LambdaQueryWrapper<>();
            qw.eq(JellyReport::getCompanyId, report.getCompanyId()).eq(JellyReport::getName, report.getName());
            int count = reportMapper.selectCount(qw);
            if (count == 0) {
                reportMapper.updateById(report);
                return ResultData.success(null);
            } else {
                return ResultData.warn(ResultCode.OTHER_ERROR, "名称已经存在");
            }
        }
    }

    @Override
    public ResultData delete(Long id) {
        reportMapper.deleteById(id);
        return ResultData.success(null);
    }

    @Override
    public ResultData queryById(Long userId, Long id) {
        return ResultData.success(reportMapper.selectById(id));
    }

    @Override
    public ResultData queryByCompanyId(Long companyId) {
        LambdaQueryWrapper<JellyReport> qw = new LambdaQueryWrapper<>();
        qw.eq(JellyReport::getCompanyId, companyId);
        List<JellyReport> list = reportMapper.selectList(qw);
        return ResultData.success(list);
    }

    @Override
    public ResultData queryListById(Long companyId, Long userId, Long id, String search) {
        JellyReport report = reportMapper.selectById(id);
        Map<String, Object> resultData = new HashMap<String, Object>();
        resultData.put("searchJson", report.getSearchJson());
        resultData.put("templateSource", report.getTemplateSource());
        resultData.put("exportPath", JSONArray.parseArray(report.getExportPath()));

        JellyTemplateEngine templateEngine = templateEngineMapper.selectById(report.getDataSource());
        if (templateEngine == null) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "数据源不存在");
        }

        Map<String, Object> searchObject = (Map<String, Object>) JSONObject.parseObject(search);

        if (Boolean.valueOf(searchObject.get("_flag").toString())) {
            JSONArray searchArray = JSONArray.parseArray(report.getSearchJson());
            for (int i = 0; i < searchArray.size(); i++) {
                JSONObject searchItem = searchArray.getJSONObject(i);
                if (!StringUtils.isEmpty(searchItem.getString("defaultValue"))) {
                    searchObject.put(searchItem.getString("field"), searchItem.getString("defaultValue"));
                }
            }
        }
        searchObject.put("userId", userId);
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

        String script = templateEngine.getScript();

        if (!StringUtils.isEmpty(script)) {
            List<Map<String, String>> dataset = XmlUtils.sqlDataset(script);
            for (int i = 0; i < dataset.size(); i++) {
                Map<String, String> item = dataset.get(i);
                String sql = XmlUtils.sqlAnalysis(script, searchObject, item.get("id"));
                String resultType = item.get("resultType");
                String keyColumn = item.get("keyColumn");
                if (resultType.equals("map")) {
                    resultData.put(keyColumn, jdbcTemplate.queryForMap(sql));
                } else if (resultType.equals("list")) {
                    resultData.put(keyColumn, jdbcTemplate.queryForList(sql));
                } else {
                    resultData.put(keyColumn, jdbcTemplate.queryForObject(sql, Object.class));
                }
            }
        }
        return ResultData.success(resultData);
    }

    @Override
    public void export(HttpServletRequest request, HttpServletResponse response) {
        JellyReport report = reportMapper.selectById(request.getParameter("id"));
        JellyTemplateEngine templateEngine = templateEngineMapper.selectById(report.getDataSource());
        Map<String, Object> searchObject = (Map<String, Object>) JSONObject.parseObject(request.getParameter("search"));
        if (Boolean.valueOf(searchObject.get("_flag").toString())) {
            JSONArray searchArray = JSONArray.parseArray(report.getSearchJson());
            for (int i = 0; i < searchArray.size(); i++) {
                JSONObject searchItem = searchArray.getJSONObject(i);
                if (!StringUtils.isEmpty(searchItem.getString("defaultValue"))) {
                    searchObject.put(searchItem.getString("field"), searchItem.getString("defaultValue"));
                }
            }
        }
        searchObject.put("userId", request.getParameter("userId"));
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

        String script = templateEngine.getScript();
        Map<String, Object> resultData = new HashMap<>();
        if (!StringUtils.isEmpty(script)) {
            List<Map<String, String>> dataset = XmlUtils.sqlDataset(script);
            for (int i = 0; i < dataset.size(); i++) {
                Map<String, String> item = dataset.get(i);
                String sql = XmlUtils.sqlAnalysis(script, searchObject, item.get("id"));
                String resultType = item.get("resultType");
                String keyColumn = item.get("keyColumn");
                if (resultType.equals("map")) {
                    resultData.put(keyColumn, jdbcTemplate.queryForMap(sql));
                } else if (resultType.equals("list")) {
                    resultData.put(keyColumn, jdbcTemplate.queryForList(sql));
                } else {
                    resultData.put(keyColumn, jdbcTemplate.queryForObject(sql, Object.class));
                }
            }
        }

        JSONArray jsonArray = JSON.parseArray(report.getExportPath());
        ExportUtils.exportExcel(jsonArray.getJSONObject(0).getString("url"), report.getName(), resultData, request,
                response);
    }


}
