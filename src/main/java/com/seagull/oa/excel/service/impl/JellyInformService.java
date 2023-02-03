package com.seagull.oa.excel.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seagull.oa.common.ResultCode;
import com.seagull.oa.common.ResultData;
import com.seagull.oa.excel.entity.JellyInform;
import com.seagull.oa.excel.entity.JellyTemplateEngine;
import com.seagull.oa.excel.mapper.JellyInformMapper;
import com.seagull.oa.excel.mapper.JellyTemplateEngineMapper;
import com.seagull.oa.excel.service.IJellyInformService;
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
public class JellyInformService implements IJellyInformService {

    @Autowired
    private JellyInformMapper informMapper;

    @Autowired
    private JellyTemplateEngineMapper templateEngineMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value(value = "${spring.datasource.url}")
    private String datasourceUrl;

    @Override
    public ResultData queryByPage(Integer pageNo, Integer pageSize, Long companyId, String name) {
        PageHelper.startPage(pageNo, pageSize);
        LambdaQueryWrapper<JellyInform> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(JellyInform::getCompanyId, companyId).eq(!StringUtils.isEmpty(name), JellyInform::getName, name);
        List<JellyInform> list = informMapper.selectList(queryWrapper);
        PageInfo<JellyInform> pageInfo = new PageInfo<>(list);
        return ResultData.success(pageInfo);
    }

    @Transactional
    @Override
    public ResultData insert(JellyInform inform) {
        LambdaQueryWrapper<JellyInform> qw = new LambdaQueryWrapper<>();
        qw.eq(JellyInform::getCompanyId, inform.getCompanyId()).eq(JellyInform::getCode, inform.getCode());
        int count = informMapper.selectCount(qw);
        if (count == 0) {
            informMapper.insert(inform);
            return ResultData.success(null);
        } else {
            return ResultData.warn(ResultCode.OTHER_ERROR, "编码已经存在");
        }
    }

    @Transactional
    @Override
    public ResultData update(JellyInform inform) {
        JellyInform originalInform = informMapper.selectById(inform.getId());
        if (originalInform.getCode().equals(inform.getCode())) {
            informMapper.updateById(inform);
            return ResultData.success(null);
        } else {
            LambdaQueryWrapper<JellyInform> qw = new LambdaQueryWrapper<>();
            qw.eq(JellyInform::getCompanyId, inform.getCompanyId()).eq(JellyInform::getCode, inform.getCode());
            int count = informMapper.selectCount(qw);
            if (count == 0) {
                informMapper.updateById(inform);
                return ResultData.success(null);
            } else {
                return ResultData.warn(ResultCode.OTHER_ERROR, "编码已经存在");
            }
        }
    }

    @Override
    public ResultData delete(Long id) {
        informMapper.deleteById(id);
        return ResultData.success(null);
    }

    @Override
    public ResultData queryByCompanyId(Long companyId) {
        LambdaQueryWrapper<JellyInform> qw = new LambdaQueryWrapper<>();
        qw.eq(JellyInform::getCompanyId, companyId);
        List<JellyInform> list = informMapper.selectList(qw);
        return ResultData.success(list);
    }

    @Override
    public void export(HttpServletRequest request, HttpServletResponse response) {
        JellyInform inform = informMapper.selectById(request.getParameter("id"));
        JellyTemplateEngine templateEngine = templateEngineMapper.selectById(inform.getDataSource());
        Map<String, Object> searchObject = (Map<String, Object>) JSONObject.parseObject(request.getParameter("search"));
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
        Map<String, Object> params = new HashMap<>();
        if (!StringUtils.isEmpty(script)) {
            List<Map<String, String>> dataset = XmlUtils.sqlDataset(script);
            for (int i = 0; i < dataset.size(); i++) {
                Map<String, String> item = dataset.get(i);
                String sql = XmlUtils.sqlAnalysis(script, searchObject, item.get("id"));
                String resultType = item.get("resultType");
                String keyColumn = item.get("keyColumn");
                if (resultType.equals("map")) {
                    params.put(keyColumn, jdbcTemplate.queryForMap(sql));
                } else if (resultType.equals("list")) {
                    params.put(keyColumn, jdbcTemplate.queryForList(sql));
                } else {
                    params.put(keyColumn, jdbcTemplate.queryForObject(sql, Object.class));
                }
            }
        }

        JSONArray jsonArray = JSON.parseArray(inform.getTemplateSource());
        if (inform.getType() == 1) {
            ExportUtils.exportWord(jsonArray.getJSONObject(0).getString("url"), inform.getName(), params, request, response);
        } else {
            ExportUtils.exportExcel(jsonArray.getJSONObject(0).getString("url"), inform.getName(), params, request, response);
        }

    }


}
