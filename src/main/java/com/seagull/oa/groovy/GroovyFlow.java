package com.seagull.oa.groovy;

import com.seagull.oa.common.ResultData;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class GroovyFlow implements IGroovyFlow {

    @Override
    public ResultData execute(HttpServletRequest request, Map<String, Object> params) {
    	// 业务逻辑

        return ResultData.success(null);
    }

}
