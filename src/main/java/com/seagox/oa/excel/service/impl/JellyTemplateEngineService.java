package com.seagox.oa.excel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.seagox.oa.common.ResultCode;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyTemplateEngine;
import com.seagox.oa.excel.mapper.JellyTemplateEngineMapper;
import com.seagox.oa.excel.service.IJellyTemplateEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JellyTemplateEngineService implements IJellyTemplateEngineService {

    @Autowired
    private JellyTemplateEngineMapper templateEngineMapper;


    @Override
    public ResultData queryByCompanyId(Long companyId) {
        LambdaQueryWrapper<JellyTemplateEngine> qw = new LambdaQueryWrapper<>();
        qw.eq(JellyTemplateEngine::getCompanyId, companyId).orderByAsc(JellyTemplateEngine::getPath);
        List<JellyTemplateEngine> list = templateEngineMapper.selectList(qw);
        return ResultData.success(list);
    }

    @Override
    public ResultData insert(JellyTemplateEngine templateEngine) {
        LambdaQueryWrapper<JellyTemplateEngine> qw = new LambdaQueryWrapper<>();
        qw.eq(JellyTemplateEngine::getPath, templateEngine.getPath());
        Long count = templateEngineMapper.selectCount(qw);
        if (count == 0) {
            templateEngineMapper.insert(templateEngine);
            return ResultData.success(null);
        } else {
            return ResultData.warn(ResultCode.OTHER_ERROR, "路径已经存在");
        }
    }

    @CacheEvict(value = "templateEngine", key = "#templateEngine.path")
    @Override
    public ResultData update(JellyTemplateEngine templateEngine) {
        JellyTemplateEngine originalTemplateEngine = templateEngineMapper.selectById(templateEngine.getId());
        if (originalTemplateEngine.getPath().equals(templateEngine.getPath())) {
            templateEngineMapper.updateById(templateEngine);
            return ResultData.success(null);
        } else {
            LambdaQueryWrapper<JellyTemplateEngine> qw = new LambdaQueryWrapper<>();
            qw.eq(JellyTemplateEngine::getPath, templateEngine.getPath());
            Long count = templateEngineMapper.selectCount(qw);
            if (count == 0) {
                templateEngineMapper.updateById(templateEngine);
                return ResultData.success(null);
            } else {
                return ResultData.warn(ResultCode.OTHER_ERROR, "路径已经存在");
            }
        }
    }

    @CacheEvict(value = "templateEngine", key = "#path")
    @Override
    public ResultData delete(Long id, String path) {
        templateEngineMapper.deleteById(id);
        return ResultData.success(null);
    }

    @Cacheable(value = "templateEngine", key = "#path")
    @Override
    public String queryByPath(String path) {
        LambdaQueryWrapper<JellyTemplateEngine> qw = new LambdaQueryWrapper<>();
        qw.eq(JellyTemplateEngine::getPath, path);
        JellyTemplateEngine templateEngine = templateEngineMapper.selectOne(qw);
        if (templateEngine != null) {
            return templateEngine.getScript();
        }
        return null;
    }

}
