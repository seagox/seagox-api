package com.seagull.oa.export.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seagull.oa.common.ResultCode;
import com.seagull.oa.common.ResultData;
import com.seagull.oa.excel.mapper.JellyFormMapper;
import com.seagull.oa.export.entity.JellyExportDimension;
import com.seagull.oa.export.mapper.JellyExportDimensionMapper;
import com.seagull.oa.export.service.IJellyExportDimensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JellyExportDimensionService implements IJellyExportDimensionService {

    @Autowired
    private JellyExportDimensionMapper dimensionMapper;

    @Autowired
    private JellyFormMapper formMapper;

    @Override
    public ResultData queryByPage(Integer pageNo, Integer pageSize, Long odsId, Long userId) {
        PageHelper.startPage(pageNo, pageSize);
        List<Map<String, Object>> list = dimensionMapper.queryByOdsId(odsId);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        return ResultData.success(pageInfo);
    }

    @Override
    public ResultData queryAll(Long odsId) {
        List<Map<String, Object>> list = dimensionMapper.queryByOdsId(odsId);
        return ResultData.success(list);
    }

    @Override
    public ResultData insert(JellyExportDimension exportDimension) {
        LambdaQueryWrapper<JellyExportDimension> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(JellyExportDimension::getName, exportDimension.getName());
        if (dimensionMapper.selectCount(queryWrapper) > 0) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "该维度名称已存在！");
        }
        dimensionMapper.insert(exportDimension);
        return ResultData.success(exportDimension.getId());
    }

    @Override
    public ResultData update(JellyExportDimension exportDimension) {
        LambdaQueryWrapper<JellyExportDimension> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(JellyExportDimension::getName, exportDimension.getName())
                .ne(JellyExportDimension::getId, exportDimension.getId());
        if (dimensionMapper.selectCount(queryWrapper) > 0) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "该维度名称已存在！");
        }
        dimensionMapper.updateById(exportDimension);
        return ResultData.success(exportDimension.getId());
    }

    @Override
    public ResultData delete(Long id) {
        dimensionMapper.deleteById(id);
        return ResultData.success(null);
    }

    @Override
    public ResultData queryArea() {
        String sql = "SELECT * FROM rd_dim_admdiv order by code";
        try {
            List<Map<String, Object>> list = formMapper.queryPublicList(sql);
            return ResultData.success(list);
        } catch (Exception ex) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "行政区划rd_dim_admdiv不存在！");
        }
    }
}
