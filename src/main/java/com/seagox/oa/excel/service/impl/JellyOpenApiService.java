package com.seagox.oa.excel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seagox.oa.common.ResultCode;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyOpenApi;
import com.seagox.oa.excel.mapper.JellyOpenApiMapper;
import com.seagox.oa.excel.service.IJellyOpenApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JellyOpenApiService implements IJellyOpenApiService {

    @Autowired
    private JellyOpenApiMapper openApiMapper;

    @Override
    public ResultData queryByPage(Integer pageNo, Integer pageSize, Long companyId) {
        PageHelper.startPage(pageNo, pageSize);
        LambdaQueryWrapper<JellyOpenApi> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(JellyOpenApi::getCompanyId, companyId);
        List<JellyOpenApi> list = openApiMapper.selectList(queryWrapper);
        PageInfo<JellyOpenApi> pageInfo = new PageInfo<>(list);
        return ResultData.success(pageInfo);
    }

    @Transactional
    @Override
    public ResultData insert(JellyOpenApi openApi) {
        LambdaQueryWrapper<JellyOpenApi> qw = new LambdaQueryWrapper<>();
        qw.eq(JellyOpenApi::getCompanyId, openApi.getCompanyId())
                .eq(JellyOpenApi::getAppid, openApi.getAppid());
        int count = openApiMapper.selectCount(qw);
        if (count == 0) {
            openApiMapper.insert(openApi);
            return ResultData.success(null);
        } else {
            return ResultData.warn(ResultCode.OTHER_ERROR, "appid已经存在");
        }
    }

    @CacheEvict(value = "openApi", key = "#openApi.appid")
    @Transactional
    @Override
    public ResultData update(JellyOpenApi openApi) {
        JellyOpenApi originalInform = openApiMapper.selectById(openApi.getId());
        if (originalInform.getAppid().equals(openApi.getAppid())) {
            openApiMapper.updateById(openApi);
            return ResultData.success(openApi);
        } else {
            LambdaQueryWrapper<JellyOpenApi> qw = new LambdaQueryWrapper<>();
            qw.eq(JellyOpenApi::getCompanyId, openApi.getCompanyId())
                    .eq(JellyOpenApi::getAppid, openApi.getAppid());
            int count = openApiMapper.selectCount(qw);
            if (count == 0) {
                openApiMapper.updateById(openApi);
                return ResultData.success(null);
            } else {
                return ResultData.warn(ResultCode.OTHER_ERROR, "appid已经存在");
            }
        }
    }

    @CacheEvict(value = "openApi", key = "#appid")
    @Override
    public ResultData delete(Long id, String appid) {
        openApiMapper.deleteById(id);
        return ResultData.success(null);
    }

    @Cacheable(value = "openApi", key = "#appid")
    @Override
    public String queryByAppid(String appid) {
        LambdaQueryWrapper<JellyOpenApi> qw = new LambdaQueryWrapper<>();
        qw.eq(JellyOpenApi::getAppid, appid);
        JellyOpenApi openApi = openApiMapper.selectOne(qw);
        if (openApi != null) {
            return openApi.getSecret();
        }
        return null;
    }

}
