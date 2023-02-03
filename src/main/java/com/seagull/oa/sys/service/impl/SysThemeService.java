package com.seagull.oa.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.seagull.oa.common.ResultData;
import com.seagull.oa.sys.entity.SysTheme;
import com.seagull.oa.sys.mapper.SysThemeMapper;
import com.seagull.oa.sys.service.ISysThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 切换主题
 */
@Service
public class SysThemeService implements ISysThemeService {

    @Autowired
    private SysThemeMapper sysThemeMapper;

    @Override
    public ResultData setting(long userId, String color) {
        LambdaQueryWrapper<SysTheme> qw = new LambdaQueryWrapper<>();
        qw.eq(SysTheme::getUserId, userId);
        SysTheme sysTheme = sysThemeMapper.selectOne(qw);
        if (sysTheme != null) {
            sysTheme.setColor(color);
            sysThemeMapper.updateById(sysTheme);
        } else {
            sysTheme = new SysTheme();
            sysTheme.setUserId(userId);
            sysTheme.setColor(color);
            sysThemeMapper.insert(sysTheme);
        }
        return ResultData.success(null);
    }


}
