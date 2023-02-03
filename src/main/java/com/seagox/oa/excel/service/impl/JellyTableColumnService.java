package com.seagox.oa.excel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyTableColumn;
import com.seagox.oa.excel.mapper.JellyTableColumnMapper;
import com.seagox.oa.excel.service.IJellyTableColumnService;
import com.seagox.oa.util.TreeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JellyTableColumnService implements IJellyTableColumnService {

    @Autowired
    private JellyTableColumnMapper tableColumnMapper;

    @Override
    public ResultData queryByClassifyId(Long classifyId) {
        LambdaQueryWrapper<JellyTableColumn> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(JellyTableColumn::getClassifyId, classifyId)
                .orderByAsc(JellyTableColumn::getSort);
        return ResultData.success(TreeUtils.categoryTreeHandle(tableColumnMapper.selectMaps(queryWrapper), "parent_id", 0L));
    }

    /**
     * 分页查询
     */
    @Override
    public ResultData queryByPage(Integer pageNo, Integer pageSize, Long classifyId) {
        PageHelper.startPage(pageNo, pageSize);
        LambdaQueryWrapper<JellyTableColumn> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(JellyTableColumn::getClassifyId, classifyId).orderByAsc(JellyTableColumn::getSort);
        List<JellyTableColumn> list = tableColumnMapper.selectList(queryWrapper);
        PageInfo<JellyTableColumn> pageInfo = new PageInfo<>(list);
        return ResultData.success(pageInfo);
    }


    @Override
    public ResultData insert(JellyTableColumn tableColumn) {
        tableColumnMapper.insert(tableColumn);
        return ResultData.success(null);
    }


    @Override
    public ResultData update(JellyTableColumn tableColumn) {
        tableColumnMapper.updateById(tableColumn);
        return ResultData.success(null);
    }


    @Override
    public ResultData delete(Long id) {
        tableColumnMapper.deleteById(id);
        return ResultData.success(null);
    }

}
