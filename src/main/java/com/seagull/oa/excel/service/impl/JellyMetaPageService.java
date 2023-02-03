package com.seagull.oa.excel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.seagull.oa.common.ResultCode;
import com.seagull.oa.common.ResultData;
import com.seagull.oa.excel.entity.JellyMetaPage;
import com.seagull.oa.excel.mapper.JellyMetaPageMapper;
import com.seagull.oa.excel.service.IJellyMetaPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JellyMetaPageService implements IJellyMetaPageService {

    @Autowired
    private JellyMetaPageMapper metaPageMapper;


    @Override
    public ResultData queryByCompanyId(Long companyId) {
        LambdaQueryWrapper<JellyMetaPage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(JellyMetaPage::getCompanyId, companyId);
        List<JellyMetaPage> list = metaPageMapper.selectList(queryWrapper);
        return ResultData.success(list);
    }

    @Override
    public ResultData insert(JellyMetaPage metaPage) {
        LambdaQueryWrapper<JellyMetaPage> qw = new LambdaQueryWrapper<>();
        qw.eq(JellyMetaPage::getPath, metaPage.getPath());
        int count = metaPageMapper.selectCount(qw);
        if (count == 0) {
            metaPageMapper.insert(metaPage);
            return ResultData.success(null);
        } else {
            return ResultData.warn(ResultCode.OTHER_ERROR, "路径已经存在");
        }
    }

    @CacheEvict(value = "metaPage", key = "#metaPage.path")
    @Override
    public ResultData update(JellyMetaPage metaPage) {
        JellyMetaPage originalMetaFunction = metaPageMapper.selectById(metaPage.getId());
        if (originalMetaFunction.getPath().equals(metaPage.getPath())) {
            metaPageMapper.updateById(metaPage);
            return ResultData.success(null);
        } else {
            LambdaQueryWrapper<JellyMetaPage> qw = new LambdaQueryWrapper<>();
            qw.eq(JellyMetaPage::getPath, metaPage.getPath());
            int count = metaPageMapper.selectCount(qw);
            if (count == 0) {
                metaPageMapper.updateById(metaPage);
                return ResultData.success(null);
            } else {
                return ResultData.warn(ResultCode.OTHER_ERROR, "路径已经存在");
            }
        }
    }

    @CacheEvict(value = "metaPage", key = "#path")
    @Override
    public ResultData delete(Long id, String path) {
        metaPageMapper.deleteById(id);
        return ResultData.success(null);
    }

    @Cacheable(value = "metaPage", key = "#path")
    @Override
    public ResultData selectByPath(String path) {
        LambdaQueryWrapper<JellyMetaPage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(JellyMetaPage::getPath, path);
        return ResultData.success(metaPageMapper.selectOne(queryWrapper));
    }

}
