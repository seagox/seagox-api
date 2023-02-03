package com.seagull.oa.excel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seagull.oa.common.ResultData;
import com.seagull.oa.excel.entity.JellyBusinessTable;
import com.seagull.oa.excel.entity.JellyFormDesign;
import com.seagull.oa.excel.mapper.JellyBusinessFieldMapper;
import com.seagull.oa.excel.mapper.JellyBusinessTableMapper;
import com.seagull.oa.excel.mapper.JellyFormDesignMapper;
import com.seagull.oa.excel.service.IJellyFormDesignService;
import com.seagull.oa.sys.entity.SysCompany;
import com.seagull.oa.sys.mapper.CompanyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class JellyFormDesignService implements IJellyFormDesignService {

    @Autowired
    private JellyFormDesignMapper formDesignMapper;

    @Autowired
    private JellyBusinessTableMapper businessTableMapper;

    @Autowired
    private JellyBusinessFieldMapper businessFieldMapper;

    @Autowired
    private CompanyMapper companyMapper;

    @Override
    public ResultData queryByPage(Integer pageNo, Integer pageSize, Long companyId, String name) {
        SysCompany company = companyMapper.selectById(companyId);
        PageHelper.startPage(pageNo, pageSize);
        List<Map<String, Object>> list = formDesignMapper.queryByCode(company.getCode().substring(0, 4), name);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        return ResultData.success(pageInfo);
    }

    @Override
    public ResultData queryAll(Long companyId) {
        LambdaQueryWrapper<JellyFormDesign> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(JellyFormDesign::getCompanyId, companyId);
        List<JellyFormDesign> list = formDesignMapper.selectList(queryWrapper);
        return ResultData.success(list);
    }

    @Override
    public ResultData insert(JellyFormDesign formDesign) {
        formDesignMapper.insert(formDesign);
        return ResultData.success(null);
    }

    @Transactional
    @Override
    public ResultData update(JellyFormDesign formDesign) {
        formDesignMapper.updateById(formDesign);
        return ResultData.success(null);
    }

    @Override
    public ResultData delete(Long id) {
        formDesignMapper.deleteById(id);
        return ResultData.success(null);
    }

    @Override
    public ResultData queryById(Long id) {
        JellyFormDesign formDesign = formDesignMapper.selectById(id);
        return ResultData.success(formDesign);
    }

    @Override
    public ResultData queryBusinessTable(Long id) {
        JellyFormDesign formDesign = formDesignMapper.selectById(id);
        LambdaQueryWrapper<JellyBusinessTable> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(!StringUtils.isEmpty(formDesign.getDataSource()), JellyBusinessTable::getId, Arrays.asList(formDesign.getDataSource().split(",")));
        List<JellyBusinessTable> list = businessTableMapper.selectList(queryWrapper);
        return ResultData.success(list);
    }

    @Override
    public ResultData queryBusinessField(Long id) {
        JellyFormDesign formDesign = formDesignMapper.selectById(id);
        List<Map<String, Object>> businessFieldList = businessFieldMapper.queryByTableIds(formDesign.getDataSource().split(","));
        return ResultData.success(businessFieldList);
    }


}
