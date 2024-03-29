package com.seagox.oa.excel.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyDoor;
import com.seagox.oa.excel.entity.JellyGauge;
import com.seagox.oa.excel.entity.JellyMetaPage;
import com.seagox.oa.excel.mapper.JellyDoorMapper;
import com.seagox.oa.excel.mapper.JellyGaugeMapper;
import com.seagox.oa.excel.mapper.JellyMetaPageMapper;
import com.seagox.oa.excel.service.IJellyDoorService;
import com.seagox.oa.sys.entity.SysUserRelate;
import com.seagox.oa.sys.mapper.UserRelateMapper;
import com.seagox.oa.util.XmlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JellyDoorService implements IJellyDoorService {

    @Autowired
    private JellyDoorMapper doorMapper;

    @Autowired
    private UserRelateMapper userRelateMapper;

    @Value(value = "${spring.datasource.url}")
    private String datasourceUrl;

    @Autowired
    private JellyMetaPageMapper metaPageMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private JellyGaugeMapper gaugeMapper;

    @Override
    public ResultData queryByPage(Integer pageNo, Integer pageSize, Long companyId) {
        PageHelper.startPage(pageNo, pageSize);
        LambdaQueryWrapper<JellyDoor> qw = new LambdaQueryWrapper<>();
    	qw.eq(JellyDoor::getCompanyId, companyId)
    	.orderByDesc(JellyDoor::getCreateTime);
        List<JellyDoor> list = doorMapper.selectList(qw);
        PageInfo<JellyDoor> pageInfo = new PageInfo<>(list);
        return ResultData.success(pageInfo);
    }

    @Override
    public ResultData insert(JellyDoor door) {
        doorMapper.insert(door);
        return ResultData.success(null);
    }

    @Override
    public ResultData update(JellyDoor door) {
        doorMapper.updateById(door);
        return ResultData.success(null);
    }

    @Override
    public ResultData delete(Long id) {
        doorMapper.deleteById(id);
        return ResultData.success(null);
    }

    @Override
    public ResultData queryById(Long id, Long userId) {
        JellyDoor door = doorMapper.selectById(id);
        return ResultData.success(door);
    }

    @Override
    public ResultData queryAnalysis(Long companyId, String userId) {
        Map<String, Object> params = new HashMap<>();
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
        } else if (datasourceUrl.contains("sqlserver")) {
            params.put("_databaseId", "sqlserver");
        }
        LambdaQueryWrapper<SysUserRelate> qwUserRelate = new LambdaQueryWrapper<>();
        qwUserRelate.eq(SysUserRelate::getCompanyId, companyId).eq(SysUserRelate::getUserId, userId);
        SysUserRelate userRelate = userRelateMapper.selectOne(qwUserRelate);
        JSONObject result = new JSONObject();
        if (userRelate != null && !StringUtils.isEmpty(userRelate.getRoleIds())) {
            List<String> roleList = Arrays.asList(userRelate.getRoleIds().split(","));
            LambdaQueryWrapper<JellyDoor> qw = new LambdaQueryWrapper<>();
            qw.orderByDesc(JellyDoor::getUpdateTime);
            List<JellyDoor> doorList = doorMapper.selectList(qw);
            for (int j = 0; j < doorList.size(); j++) {
                JellyDoor door = doorList.get(j);
                List<String> authorityList = Arrays.asList(door.getAuthority().split(","));
                for (String role : roleList) {
                    if (authorityList.contains(role)) {
                    	result.put("id", door.getId());
                    	result.put("type", door.getType());
                        if (door.getType() == 1) {
                        	JellyGauge gauge = gaugeMapper.selectById(door.getViewId());
                            result.put("data", gauge);
                        } else {
                        	JellyMetaPage metaPage = metaPageMapper.selectById(door.getViewId());
                            result.put("data", metaPage);
                        }
                    }
                }
            }
        }
        return ResultData.success(result);
    }

    @Override
    public ResultData execute(HttpServletRequest request, Long userId, Long id, String key) {
        JellyDoor door = doorMapper.selectById(id);
        JellyGauge gauge = gaugeMapper.selectById(door.getViewId());
        String resultType = XmlUtils.sqlResultTypeById(key, gauge.getTemplateEngine());
        String script = XmlUtils.sqlAnalysis(gauge.getTemplateEngine(), XmlUtils.requestToMap(request), key);
        if (resultType.equals("list")) {
            return ResultData.success(jdbcTemplate.queryForList(script));
        } else if (resultType.equals("map")) {
            List<Map<String, Object>> mapList = jdbcTemplate.queryForList(script);
            if (mapList.size() > 0) {
                return ResultData.success(mapList.get(0));
            } else {
                return ResultData.success(mapList);
            }
        } else {
            return ResultData.success(jdbcTemplate.queryForObject(script, String.class));
        }
    }

}
