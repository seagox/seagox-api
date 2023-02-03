package com.seagox.oa.excel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyDataSheet;
import com.seagox.oa.excel.mapper.JellyDataSheetMapper;
import com.seagox.oa.excel.service.IJellyDataSheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JellyDataSheetService implements IJellyDataSheetService {

    @Autowired
    private JellyDataSheetMapper dataSheetMapper;

    @Override
    public ResultData queryAll(Long formId) {
        LambdaQueryWrapper<JellyDataSheet> queryWrapper = new LambdaQueryWrapper<JellyDataSheet>();
        queryWrapper.eq(JellyDataSheet::getFormId, formId);
        List<JellyDataSheet> list = dataSheetMapper.selectList(queryWrapper);
        return ResultData.success(list);
    }

    @Override
    public ResultData insert(JellyDataSheet dataSheet) {
        dataSheetMapper.insert(dataSheet);
        return ResultData.success(null);
    }

    @Override
    public ResultData update(JellyDataSheet dataSheet) {
        dataSheetMapper.updateById(dataSheet);
        return ResultData.success(null);
    }

    @Override
    public ResultData delete(Long id) {
        dataSheetMapper.deleteById(id);
        return ResultData.success(null);
    }

}
