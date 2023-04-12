package com.seagox.oa.groovy;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.seagox.oa.util.VerifyHandlerResult;

public interface IGroovyImportVerifyRule {

    public VerifyHandlerResult verifyRule(HttpServletRequest request, JSONObject row);

}
