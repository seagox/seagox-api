package com.seagox.oa.excel.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.seagox.oa.common.ResultCode;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyProcedure;
import com.seagox.oa.excel.mapper.JellyProcedureMapper;
import com.seagox.oa.excel.service.IJellyProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class JellyProcedureService implements IJellyProcedureService {

    @Autowired
    private JellyProcedureMapper procedureMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ResultData queryAll(Long companyId, String name, String remark) {
        LambdaQueryWrapper<JellyProcedure> qw = new LambdaQueryWrapper<>();
        qw.eq(JellyProcedure::getCompanyId, companyId)
        .like(!StringUtils.isEmpty(name), JellyProcedure::getName, name)
        .like(!StringUtils.isEmpty(remark), JellyProcedure::getRemark, remark)
        .orderByAsc(JellyProcedure::getCreateTime);
        List<JellyProcedure> list = procedureMapper.selectList(qw);
        return ResultData.success(list);
    }

    @Transactional
    @Override
    public ResultData insert(JellyProcedure procedure) {
        LambdaQueryWrapper<JellyProcedure> qw = new LambdaQueryWrapper<>();
        qw.eq(JellyProcedure::getCompanyId, procedure.getCompanyId())
                .eq(JellyProcedure::getName, procedure.getName());
        Long count = procedureMapper.selectCount(qw);
        if (count == 0) {
            procedureMapper.insert(procedure);
            return ResultData.success(null);
        } else {
            return ResultData.warn(ResultCode.OTHER_ERROR, "名称已经存在");
        }
    }

    @Transactional
    @Override
    public ResultData update(JellyProcedure procedure) {
        JellyProcedure originalInform = procedureMapper.selectById(procedure.getId());
        if (originalInform.getName().equals(procedure.getName())) {
            procedureMapper.updateById(procedure);
            return ResultData.success(procedure);
        } else {
            LambdaQueryWrapper<JellyProcedure> qw = new LambdaQueryWrapper<>();
            qw.eq(JellyProcedure::getCompanyId, procedure.getCompanyId())
                    .eq(JellyProcedure::getName, procedure.getName());
            Long count = procedureMapper.selectCount(qw);
            if (count == 0) {
                procedureMapper.updateById(procedure);
                return ResultData.success(null);
            } else {
                return ResultData.warn(ResultCode.OTHER_ERROR, "名称已经存在");
            }
        }
    }

    @Override
    public ResultData delete(Long id) {
        procedureMapper.deleteById(id);
        return ResultData.success(null);
    }

    @Override
    public ResultData execute(Long id, HttpServletRequest request) {
        JellyProcedure procedure = procedureMapper.selectById(id);
        StringBuilder sql = new StringBuilder();
        sql.append("call ");
        sql.append(procedure.getName());
        sql.append("(");
        // 输入参数
        JSONArray configArray = JSONArray.parseArray(procedure.getConfig());
        for (int i = 0; i < configArray.size(); i++) {
            JSONObject item = configArray.getJSONObject(i);
            String field = item.getString("field");
            String value = request.getParameter(field);
            sql.append("'").append(value).append("'");
            if (i != (configArray.size() - 1)) {
                sql.append(",");
            }
        }
        sql.append(")");
        try {
            jdbcTemplate.execute(sql.toString());
            return ResultData.success(null);
        } catch (Exception ex) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "执行失败：" + ex.getMessage());
        }
    }

}
