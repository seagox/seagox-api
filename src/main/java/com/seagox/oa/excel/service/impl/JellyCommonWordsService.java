package com.seagox.oa.excel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.seagox.oa.common.ResultCode;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyCommonWords;
import com.seagox.oa.excel.mapper.JellyCommonWordsMapper;
import com.seagox.oa.excel.service.IJellyCommonWordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JellyCommonWordsService implements IJellyCommonWordsService {

    @Autowired
    private JellyCommonWordsMapper commonWordsMapper;

    @Override
    public ResultData queryAll(Long companyId, Long userId) {
        LambdaQueryWrapper<JellyCommonWords> qw = new LambdaQueryWrapper<>();
        qw.eq(JellyCommonWords::getCompanyId, companyId)
                .eq(JellyCommonWords::getUserId, userId);
        List<JellyCommonWords> list = commonWordsMapper.selectList(qw);
        return ResultData.success(list);
    }

    @Override
    public ResultData insert(JellyCommonWords commonWords) {
        LambdaQueryWrapper<JellyCommonWords> qw = new LambdaQueryWrapper<>();
        qw.eq(JellyCommonWords::getName, commonWords.getName());
        int count = commonWordsMapper.selectCount(qw);
        if (count == 0) {
            commonWordsMapper.insert(commonWords);
            return ResultData.success(null);
        } else {
            return ResultData.warn(ResultCode.OTHER_ERROR, "名称已经存在");
        }
    }

    @Override
    public ResultData update(JellyCommonWords commonWords) {
        JellyCommonWords originalCommonWords = commonWordsMapper.selectById(commonWords.getId());
        if (originalCommonWords.getName().equals(commonWords.getName())) {
            commonWordsMapper.updateById(commonWords);
            return ResultData.success(null);
        } else {
            LambdaQueryWrapper<JellyCommonWords> qw = new LambdaQueryWrapper<>();
            qw.eq(JellyCommonWords::getName, commonWords.getName());
            int count = commonWordsMapper.selectCount(qw);
            if (count == 0) {
                commonWordsMapper.updateById(commonWords);
                return ResultData.success(null);
            } else {
                return ResultData.warn(ResultCode.OTHER_ERROR, "名称已经存在");
            }
        }
    }

    @Override
    public ResultData delete(Long id) {
        commonWordsMapper.deleteById(id);
        return ResultData.success(null);
    }

}
