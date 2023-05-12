package com.seagox.oa.excel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyTableColumnConfig;
import com.seagox.oa.excel.mapper.JellyTableColumnConfigMapper;
import com.seagox.oa.excel.service.IJellyTableColumnConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JellyTableColumnConfigService implements IJellyTableColumnConfigService {

    @Autowired
    private JellyTableColumnConfigMapper tableColumnConfigMapper;


    @Override
    public ResultData insert(JellyTableColumnConfig tableColumnConfig) {
        LambdaQueryWrapper<JellyTableColumnConfig> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(JellyTableColumnConfig::getUserId, tableColumnConfig.getUserId())
                .eq(JellyTableColumnConfig::getTableColumnId, tableColumnConfig.getTableColumnId());
        Long count = tableColumnConfigMapper.selectCount(queryWrapper);
        if (count == 0) {
            tableColumnConfigMapper.insert(tableColumnConfig);
        } else {
            tableColumnConfigMapper.update(tableColumnConfig, queryWrapper);
        }
        return ResultData.success(null);
    }

}
