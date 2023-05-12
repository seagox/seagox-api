package com.seagox.oa.excel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seagox.oa.common.ResultCode;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyMetaFunction;
import com.seagox.oa.excel.mapper.JellyMetaFunctionMapper;
import com.seagox.oa.excel.service.IJellyMetaFunctionService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class JellyMetaFunctionService implements IJellyMetaFunctionService {

    @Autowired
    private JellyMetaFunctionMapper metaFunctionMapper;
    
    @Override
    public ResultData queryByPage(Integer pageNo, Integer pageSize, Long companyId, String name, String path, Integer type) {
    	LambdaQueryWrapper<JellyMetaFunction> qw = new LambdaQueryWrapper<>();
        qw.eq(JellyMetaFunction::getCompanyId, companyId)
        .eq(!StringUtils.isEmpty(type), JellyMetaFunction::getType, type)
        .like(!StringUtils.isEmpty(name), JellyMetaFunction::getName, name)
        .like(!StringUtils.isEmpty(path), JellyMetaFunction::getPath, path)
        .orderByDesc(JellyMetaFunction::getCreateTime);
        PageHelper.startPage(pageNo, pageSize);
        List<JellyMetaFunction> list = metaFunctionMapper.selectList(qw);
        PageInfo<JellyMetaFunction> pageInfo = new PageInfo<>(list);
        return ResultData.success(pageInfo);
    }
    
    @Override
    public ResultData queryByCompanyId(Long companyId, Integer type) {
        LambdaQueryWrapper<JellyMetaFunction> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(JellyMetaFunction::getCompanyId, companyId)
        .eq(!StringUtils.isEmpty(type), JellyMetaFunction::getType, type);
        List<JellyMetaFunction> list = metaFunctionMapper.selectList(queryWrapper);
        return ResultData.success(list);
    }

    @Override
    public ResultData insert(JellyMetaFunction metaFunction) {
        LambdaQueryWrapper<JellyMetaFunction> qw = new LambdaQueryWrapper<>();
        qw.eq(JellyMetaFunction::getPath, metaFunction.getPath());
        Long count = metaFunctionMapper.selectCount(qw);
        if (count == 0) {
            metaFunctionMapper.insert(metaFunction);
            return ResultData.success(null);
        } else {
            return ResultData.warn(ResultCode.OTHER_ERROR, "路径已经存在");
        }
    }

    @CacheEvict(value = "metaFunction", key = "#metaFunction.path")
    @Override
    public ResultData update(JellyMetaFunction metaFunction) {
        JellyMetaFunction originalMetaFunction = metaFunctionMapper.selectById(metaFunction.getId());
        if (originalMetaFunction.getPath().equals(metaFunction.getPath())) {
            metaFunctionMapper.updateById(metaFunction);
            return ResultData.success(null);
        } else {
            LambdaQueryWrapper<JellyMetaFunction> qw = new LambdaQueryWrapper<>();
            qw.eq(JellyMetaFunction::getPath, metaFunction.getPath());
            Long count = metaFunctionMapper.selectCount(qw);
            if (count == 0) {
                metaFunctionMapper.updateById(metaFunction);
                return ResultData.success(null);
            } else {
                return ResultData.warn(ResultCode.OTHER_ERROR, "路径已经存在");
            }
        }
    }

    @CacheEvict(value = "metaFunction", key = "#path")
    @Override
    public ResultData delete(Long id, String path) {
        metaFunctionMapper.deleteById(id);
        return ResultData.success(null);
    }

    @Cacheable(value = "metaFunction", key = "#path")
    @Override
    public String queryByPath(String path) {
    	LambdaQueryWrapper<JellyMetaFunction> qw = new LambdaQueryWrapper<>();
        qw.eq(JellyMetaFunction::getPath, path);
        JellyMetaFunction metaFunction = metaFunctionMapper.selectOne(qw);
        if (metaFunction != null) {
            return metaFunction.getScript();
        }
        return null;
    }

	@Override
	public ResultData queryById(Long id) {
		return ResultData.success(metaFunctionMapper.selectById(id));
	}

}
