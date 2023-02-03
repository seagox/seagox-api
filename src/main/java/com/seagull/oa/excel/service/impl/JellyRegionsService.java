package com.seagull.oa.excel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.seagull.oa.excel.entity.JellyRegions;
import com.seagull.oa.excel.mapper.JellyRegionsMapper;
import com.seagull.oa.excel.service.IJellyRegionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JellyRegionsService implements IJellyRegionsService {

    @Autowired
    private JellyRegionsMapper regionsMapper;

    @Cacheable(value = "regions")
    @Override
    public Map<String, String> selectList() {
        Map<String, String> regionsMap = new HashMap<>();
        LambdaQueryWrapper<JellyRegions> queryWrapper = new LambdaQueryWrapper<>();
        List<JellyRegions> list = regionsMapper.selectList(queryWrapper);
        for (int i = 0; i < list.size(); i++) {
            JellyRegions regions = list.get(i);
            regionsMap.put(regions.getCode(), regions.getName());
        }
        return regionsMap;
    }

}
