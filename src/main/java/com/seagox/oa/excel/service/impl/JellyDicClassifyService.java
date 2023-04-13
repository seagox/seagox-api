package com.seagox.oa.excel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.seagox.oa.common.ResultCode;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyDicClassify;
import com.seagox.oa.excel.entity.JellyDicDetail;
import com.seagox.oa.excel.mapper.JellyDicClassifyMapper;
import com.seagox.oa.excel.mapper.JellyDicDetailMapper;
import com.seagox.oa.excel.service.IJellyDicClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JellyDicClassifyService implements IJellyDicClassifyService {

    @Autowired
    private JellyDicClassifyMapper dicClassifyMapper;

    @Autowired
    private JellyDicDetailMapper dicDetailMapper;

    @Override
    public ResultData queryDisplay(Long companyId) {
    	LambdaQueryWrapper<JellyDicClassify> qw = new LambdaQueryWrapper<>();
    	qw.eq(JellyDicClassify::getCompanyId, companyId);
        List<JellyDicClassify> list = dicClassifyMapper.selectList(qw);
        return ResultData.success(list);
    }

    @Override
    public ResultData insert(JellyDicClassify dicClassify) {
        dicClassifyMapper.insert(dicClassify);
        return ResultData.success(null);
    }


    @Override
    public ResultData update(JellyDicClassify dicClassify) {
        dicClassifyMapper.updateById(dicClassify);
        return ResultData.success(null);
    }


    @Override
    public ResultData delete(Long id) {
        LambdaQueryWrapper<JellyDicDetail> qw = new LambdaQueryWrapper<>();
        qw.eq(JellyDicDetail::getClassifyId, id);
        int count = dicDetailMapper.selectCount(qw);
        if (count > 0) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "字典分类下有数据，不可删除");
        } else {
            dicClassifyMapper.deleteById(id);
            return ResultData.success(null);
        }
    }

    @Override
    public ResultData queryById(Long id) {
        return ResultData.success(dicClassifyMapper.selectById(id));
    }

}
