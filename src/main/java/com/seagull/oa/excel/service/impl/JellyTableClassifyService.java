package com.seagull.oa.excel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.seagull.oa.common.ResultCode;
import com.seagull.oa.common.ResultData;
import com.seagull.oa.excel.entity.JellyTableClassify;
import com.seagull.oa.excel.entity.JellyTableColumn;
import com.seagull.oa.excel.mapper.JellyTableClassifyMapper;
import com.seagull.oa.excel.mapper.JellyTableColumnMapper;
import com.seagull.oa.excel.service.IJellyTableClassifyService;
import com.seagull.oa.sys.entity.SysCompany;
import com.seagull.oa.sys.mapper.CompanyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JellyTableClassifyService implements IJellyTableClassifyService {

    @Autowired
    private JellyTableClassifyMapper tableClassifyMapper;

    @Autowired
    private JellyTableColumnMapper tableColumnMapper;

    @Autowired
    private CompanyMapper companyMapper;

    @Override
    public ResultData queryAll(Long companyId) {
        SysCompany company = companyMapper.selectById(companyId);
        List<Map<String, Object>> list = tableClassifyMapper.queryByCode(company.getCode().substring(0, 4));
        return ResultData.success(list);
    }

    @Override
    public ResultData insert(JellyTableClassify tableClassify) {
        tableClassifyMapper.insert(tableClassify);
        return ResultData.success(null);
    }


    @Override
    public ResultData update(JellyTableClassify tableClassify) {
        tableClassifyMapper.updateById(tableClassify);
        return ResultData.success(null);
    }


    @Override
    public ResultData delete(Long id) {
        LambdaQueryWrapper<JellyTableColumn> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(JellyTableColumn::getClassifyId, id);
        int count = tableColumnMapper.selectCount(queryWrapper);
        if (count > 0) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "分类有数据，不可删除");
        } else {
            tableClassifyMapper.deleteById(id);
            return ResultData.success(null);
        }
    }

    @Override
    public ResultData queryById(Long id) {
        return ResultData.success(tableClassifyMapper.selectById(id));
    }

}
