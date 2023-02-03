package com.seagull.oa.excel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.seagull.oa.common.ResultCode;
import com.seagull.oa.common.ResultData;
import com.seagull.oa.excel.entity.JellyMetaFunction;
import com.seagull.oa.excel.mapper.JellyMetaFunctionMapper;
import com.seagull.oa.excel.service.IJellyMetaFunctionService;
import com.seagull.oa.util.TreeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class JellyMetaFunctionService implements IJellyMetaFunctionService {

    @Autowired
    private JellyMetaFunctionMapper metaFunctionMapper;


    @Override
    public ResultData queryByCompanyId(Long companyId) {
        LambdaQueryWrapper<JellyMetaFunction> qw = new LambdaQueryWrapper<>();
        qw.eq(JellyMetaFunction::getCompanyId, companyId);
        return ResultData.success(TreeUtils.categoryTreeHandle(metaFunctionMapper.selectMaps(qw), "parent_id", 0L));
    }

    @Override
    public ResultData insert(JellyMetaFunction metaFunction) {
        LambdaQueryWrapper<JellyMetaFunction> qw = new LambdaQueryWrapper<>();
        qw.eq(JellyMetaFunction::getPath, metaFunction.getPath());
        int count = metaFunctionMapper.selectCount(qw);
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
            int count = metaFunctionMapper.selectCount(qw);
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
        if (path.contains("/")) {
            return metaFunctionMapper.queryMultiByPath(path.split("/")[0], path.split("/")[1]);
        } else {
            LambdaQueryWrapper<JellyMetaFunction> qw = new LambdaQueryWrapper<>();
            qw.eq(JellyMetaFunction::getPath, path);
            JellyMetaFunction metaFunction = metaFunctionMapper.selectOne(qw);
            if (metaFunction != null) {
                return metaFunction.getScript();
            }
        }
        return null;
    }

}
