package com.seagox.oa.sys.controller;

import com.seagox.oa.annotation.SysLogPoint;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.sys.service.ISysThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 主题
 */
@RestController
@RequestMapping("/sysTheme")
public class SysThemeController {

    @Autowired
    private ISysThemeService sysThemeService;

    /**
     * 设置主题
     */
    @PostMapping("/setting")
    @SysLogPoint("设置主题")
    public ResultData setting(long userId, String color) {
        return sysThemeService.setting(userId, color);
    }

}
