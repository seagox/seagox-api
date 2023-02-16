package com.seagox.oa.excel.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seagox.oa.common.ResultCode;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyMetaFunction;
import com.seagox.oa.excel.entity.JellyPrint;
import com.seagox.oa.excel.mapper.JellyMetaFunctionMapper;
import com.seagox.oa.excel.mapper.JellyPrintMapper;
import com.seagox.oa.excel.service.IJellyPrintService;
import com.seagox.oa.groovy.GroovyFactory;
import com.seagox.oa.groovy.IGroovyRule;
import com.seagox.oa.util.ExportUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@Service
public class JellyPrintService implements IJellyPrintService {

    @Autowired
    private JellyPrintMapper printMapper;
    
    @Autowired
    private JellyMetaFunctionMapper metaFunctionMapper;
    
    @Value(value = "${jodconverter.working-dir}")
    private String workingDir;

    @Override
    public ResultData queryByPage(Integer pageNo, Integer pageSize, Long companyId, String name) {
    	PageHelper.startPage(pageNo, pageSize);
        LambdaQueryWrapper<JellyPrint> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(JellyPrint::getCompanyId, companyId).eq(!StringUtils.isEmpty(name), JellyPrint::getName, name);
        List<JellyPrint> list = printMapper.selectList(queryWrapper);
        PageInfo<JellyPrint> pageInfo = new PageInfo<>(list);
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

	@SuppressWarnings("unchecked")
	@Override
	public String preview(Long id, HttpServletRequest request) {
		JellyPrint print = printMapper.selectById(id);
		JSONArray templateSource = JSONArray.parseArray(print.getTemplateSource());
		String url = templateSource.getJSONObject(0).getString("url");
		JellyMetaFunction metaFunction = metaFunctionMapper.selectById(print.getDataSource());
		String fileName = System.currentTimeMillis() + ".docx";
		if (!StringUtils.isEmpty(metaFunction.getScript())) {
			try {
				IGroovyRule groovyRule= GroovyFactory.getInstance().getIRuleFromCode(metaFunction.getScript());
	            Map<String, Object> params = new HashMap<String, Object>();
	            Map<String, Object> result = (Map<String, Object>) groovyRule.execute(request, params);
	            OutputStream out = new FileOutputStream(workingDir + "/" + fileName);
	            ExportUtils.exportWord(url, result, out, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
		return workingDir + "/" + fileName;
	}

}
