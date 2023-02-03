package com.seagull.oa.excel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.seagull.oa.common.ResultCode;
import com.seagull.oa.common.ResultData;
import com.seagull.oa.excel.entity.JellyDicClassify;
import com.seagull.oa.excel.entity.JellyDicDetail;
import com.seagull.oa.excel.mapper.JellyDicClassifyMapper;
import com.seagull.oa.excel.mapper.JellyDicDetailMapper;
import com.seagull.oa.excel.service.IJellyDicClassifyService;
import com.seagull.oa.sys.entity.SysCompany;
import com.seagull.oa.sys.mapper.CompanyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JellyDicClassifyService implements IJellyDicClassifyService {

    @Autowired
    private JellyDicClassifyMapper dicClassifyMapper;

    @Autowired
    private JellyDicDetailMapper dicDetailMapper;

    @Autowired
    private CompanyMapper companyMapper;

    @Override
    public ResultData queryDisplay(Long companyId) {
        SysCompany company = companyMapper.selectById(companyId);
        List<Map<String, Object>> list = dicClassifyMapper.queryByCode(company.getCode().substring(0, 4));
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
