package com.seagull.oa.disk.service.impl;

import com.seagull.oa.disk.mapper.JellyDiskShareMapper;
import com.seagull.oa.disk.service.IJellyDiskShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 他人分享 服务实现类
 */
@Service
public class JellyDiskShareServiceImpl implements IJellyDiskShareService {

    @Autowired
    private JellyDiskShareMapper jellyDiskShareMapper;

    @Override
    public List<Map<String, Object>> queryByUserId(Long companyId, Long userId) {
        return jellyDiskShareMapper.queryByUserId(companyId, userId);
    }
}
