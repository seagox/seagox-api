package com.seagox.oa.groovy;

import com.alibaba.fastjson.JSONObject;
import com.seagox.oa.util.VerifyHandlerResult;

public interface IGroovyImportVerifyRule {

    public VerifyHandlerResult verifyRule(JSONObject row);

}
