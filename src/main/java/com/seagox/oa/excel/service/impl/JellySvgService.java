package com.seagox.oa.excel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellySvg;
import com.seagox.oa.excel.mapper.JellySvgMapper;
import com.seagox.oa.excel.service.IJellySvgService;
import com.seagox.oa.util.SvgUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JellySvgService implements IJellySvgService {

    @Autowired
    private JellySvgMapper jellySvgMapper;

    @Override
    public ResultData queryByPage(Integer pageNo, Integer pageSize, String name) {
        PageHelper.startPage(pageNo, pageSize);
        LambdaQueryWrapper<JellySvg> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(name), JellySvg::getName, name);
        List<JellySvg> list = jellySvgMapper.selectList(queryWrapper);
        PageInfo<JellySvg> pageInfo = new PageInfo<>(list);
        return ResultData.success(pageInfo);
    }

    @Override
    public ResultData queryById(Long id) {
        return ResultData.success(jellySvgMapper.selectById(id));
    }

    @Override
    public ResultData insert(JellySvg jellySvg) {
        jellySvg.setContent(SvgUtils.parseString(jellySvg.getContent()));
        jellySvgMapper.insert(jellySvg);
        return ResultData.success(null);
    }

    @Override
    public ResultData delete(Long id) {
        jellySvgMapper.deleteById(id);
        return ResultData.success(null);
    }

}
