package com.seagox.oa.excel.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyGauge;
import com.seagox.oa.excel.mapper.JellyGaugeMapper;
import com.seagox.oa.excel.service.IJellyGaugeService;
import com.seagox.oa.sys.entity.SysCompany;
import com.seagox.oa.sys.mapper.CompanyMapper;
import com.seagox.oa.util.XmlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class JellyGaugeService implements IJellyGaugeService {

    @Autowired
    private JellyGaugeMapper gaugeMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CompanyMapper companyMapper;

    @Override
    public ResultData queryByPage(Integer pageNo, Integer pageSize, Long companyId, String name) {
        SysCompany company = companyMapper.selectById(companyId);
        PageHelper.startPage(pageNo, pageSize);
        List<Map<String, Object>> list = gaugeMapper.queryByCode(company.getCode().substring(0, 4), name);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        return ResultData.success(pageInfo);
    }

    @Override
    public ResultData insert(JellyGauge gauge) {
        gaugeMapper.insert(gauge);
        return ResultData.success(null);
    }

    @Override
    public ResultData update(JellyGauge gauge) {
        gaugeMapper.updateById(gauge);
        return ResultData.success(null);
    }

    @Override
    public ResultData delete(Long id) {
        gaugeMapper.deleteById(id);
        return ResultData.success(null);
    }

    @Override
    public ResultData queryById(Long id, Long userId) {
        JellyGauge gauge = gaugeMapper.selectById(id);
        if (gauge != null) {
            if (!StringUtils.isEmpty(gauge.getConfig())) {
                JSONObject result = JSONObject.parseObject(gauge.getConfig());
                return ResultData.success(result);
            }
        }
        return ResultData.success(null);
    }

    @Override
    public ResultData execute(HttpServletRequest request, Long userId, Long id, String name) {
        JellyGauge gauge = gaugeMapper.selectById(id);
        if (gauge != null) {
            JSONObject config = JSONObject.parseObject(gauge.getConfig());
            JSONArray queries = config.getJSONArray("queries");
            for (int i = 0; i < queries.size(); i++) {
                JSONObject query = queries.getJSONObject(i);
                if (query.getString("name").equals(name)) {
                    String resultType = XmlUtils.sqlResultType(query.getString("script"));
                    String script = XmlUtils.sqlAnalysis(query.getString("script"), XmlUtils.requestToMap(request), null);
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
        }
        return ResultData.success(null);
    }

    @Override
    public ResultData queryByCompanyId(Long companyId) {
        LambdaQueryWrapper<JellyGauge> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(JellyGauge::getCompanyId, companyId);
        List<JellyGauge> list = gaugeMapper.selectList(queryWrapper);
        return ResultData.success(list);
    }

}
