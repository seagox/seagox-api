package com.seagox.oa.groovy;

import com.seagox.oa.common.ResultData;

import javax.servlet.http.HttpServletRequest;

public interface IGroovyCloud {

    public ResultData execute(HttpServletRequest request);

}
