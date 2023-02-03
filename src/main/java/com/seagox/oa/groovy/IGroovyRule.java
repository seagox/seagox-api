package com.seagox.oa.groovy;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface IGroovyRule {

    public Map<String, Object> businessRule(Map<String, Object> params, HttpServletRequest request);

}
