package com.seagull.oa.sys.service;

import com.seagull.oa.common.ResultData;

public interface ISysThemeService {

    /**
     * 设置主题
     */
    public ResultData setting(long userId, String color);

}
