package com.seagox.oa.excel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seagox.oa.common.ResultCode;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyBusinessTable;
import com.seagox.oa.excel.entity.JellyPrint;
import com.seagox.oa.excel.mapper.JellyBusinessFieldMapper;
import com.seagox.oa.excel.mapper.JellyBusinessTableMapper;
import com.seagox.oa.excel.mapper.JellyPrintMapper;
import com.seagox.oa.excel.service.IJellyPrintService;
import com.seagox.oa.sys.entity.SysCompany;
import com.seagox.oa.sys.mapper.CompanyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class JellyPrintService implements IJellyPrintService {

    @Autowired
    private JellyPrintMapper printMapper;

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
        List<Map<String, Object>> list = printMapper.queryByCode(company.getCode().substring(0, 4), name);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        return ResultData.success(pageInfo);
    }

    @Override
    public ResultData queryAll(Long companyId) {
        LambdaQueryWrapper<JellyPrint> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(JellyPrint::getCompanyId, companyId);
        List<JellyPrint> list = printMapper.selectList(queryWrapper);
        return ResultData.success(list);
    }


    @Transactional
    @Override
    public ResultData insert(JellyPrint print) {
        LambdaQueryWrapper<JellyPrint> qw = new LambdaQueryWrapper<>();
        qw.eq(JellyPrint::getCompanyId, print.getCompanyId()).eq(JellyPrint::getName, print.getName());
        int count = printMapper.selectCount(qw);
        if (count == 0) {
            printMapper.insert(print);
            return ResultData.success(null);
        } else {
            return ResultData.warn(ResultCode.OTHER_ERROR, "名称已经存在");
        }
    }

    @Transactional
    @Override
    public ResultData update(JellyPrint print) {
        JellyPrint originalPrint = printMapper.selectById(print.getId());
        if (originalPrint.getName().equals(print.getName())) {
            printMapper.updateById(print);
            return ResultData.success(null);
        } else {
            LambdaQueryWrapper<JellyPrint> qw = new LambdaQueryWrapper<>();
            qw.eq(JellyPrint::getCompanyId, print.getCompanyId()).eq(JellyPrint::getName, print.getName());
            int count = printMapper.selectCount(qw);
            if (count == 0) {
                printMapper.updateById(print);
                return ResultData.success(null);
            } else {
                return ResultData.warn(ResultCode.OTHER_ERROR, "名称已经存在");
            }
        }
    }

    @Override
    public ResultData delete(Long id) {
        printMapper.deleteById(id);
        return ResultData.success(null);
    }

    @Override
    public ResultData queryById(Long id) {
        JellyPrint print = printMapper.selectById(id);
        return ResultData.success(print);
    }

    @Override
    public ResultData queryBusinessTable(Long id) {
        JellyPrint print = printMapper.selectById(id);
        LambdaQueryWrapper<JellyBusinessTable> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(!StringUtils.isEmpty(print.getDataSource()), JellyBusinessTable::getId, Arrays.asList(print.getDataSource().split(",")));
        List<JellyBusinessTable> list = businessTableMapper.selectList(queryWrapper);
        return ResultData.success(list);
    }

    @Override
    public ResultData queryBusinessField(Long id) {
        JellyPrint print = printMapper.selectById(id);
        List<Map<String, Object>> businessFieldList = businessFieldMapper.queryByTableIds(print.getDataSource().split(","));
        return ResultData.success(businessFieldList);
    }


}
