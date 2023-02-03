package com.seagox.oa.sys.service;

import com.seagox.oa.common.ResultData;

public interface ISysThemeService {

    /**
     * 设置主题
     */
    public ResultData setting(long userId, String color);

}
