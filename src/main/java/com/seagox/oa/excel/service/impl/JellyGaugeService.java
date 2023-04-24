package com.seagox.oa.excel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyGauge;
import com.seagox.oa.excel.mapper.JellyGaugeMapper;
import com.seagox.oa.excel.service.IJellyGaugeService;
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

    @Override
    public ResultData queryByPage(Integer pageNo, Integer pageSize, Long companyId, String name) {
        PageHelper.startPage(pageNo, pageSize);
        LambdaQueryWrapper<JellyGauge> qw = new LambdaQueryWrapper<>();
        qw.eq(JellyGauge::getCompanyId, companyId)
        .like(!StringUtils.isEmpty(name), JellyGauge::getName, name);
        List<JellyGauge> list = gaugeMapper.selectList(qw);
        PageInfo<JellyGauge> pageInfo = new PageInfo<>(list);
        return ResultData.success(pageInfo);
    }

    @Override
    public ResultData insert(JellyGauge gauge) {
    	String script = "export function mounted() {\r\n" + 
    			"  console.log(`mounted`)\r\n" + 
    			"}";
    	gauge.setScript(script);
    	String templateEngine = "<mapper>\r\n" + 
    			"    \r\n" + 
    			"</mapper>";
    	gauge.setTemplateEngine(templateEngine);
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
        return ResultData.success(gauge);
    }

    @Override
    public ResultData execute(HttpServletRequest request, Long userId, Long id, String key) {
        JellyGauge gauge = gaugeMapper.selectById(id);
        String resultType = XmlUtils.sqlResultType(gauge.getTemplateEngine());
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

    @Override
    public ResultData queryByCompanyId(Long companyId) {
        LambdaQueryWrapper<JellyGauge> qw = new LambdaQueryWrapper<>();
        qw.eq(JellyGauge::getCompanyId, companyId);
        List<JellyGauge> list = gaugeMapper.selectList(qw);
        return ResultData.success(list);
    }

}
