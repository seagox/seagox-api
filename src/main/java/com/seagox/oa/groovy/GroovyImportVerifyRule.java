package com.seagox.oa.groovy;

import com.alibaba.fastjson.JSONObject;
import com.seagox.oa.util.VerifyHandlerResult;

import org.springframework.stereotype.Service;

@Service
public class GroovyImportVerifyRule implements IGroovyImportVerifyRule{


    @Override
    public VerifyHandlerResult verifyRule(JSONObject row) {
    	VerifyHandlerResult result = new VerifyHandlerResult(true);
    	
    	return result;
    }
}
