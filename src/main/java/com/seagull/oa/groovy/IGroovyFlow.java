package com.seagull.oa.groovy;

import com.seagull.oa.common.ResultData;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface IGroovyFlow {

    public ResultData execute(HttpServletRequest request, Map<String, Object> params);

}
