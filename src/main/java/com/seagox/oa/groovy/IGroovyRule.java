package com.seagox.oa.groovy;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface IGroovyRule {

    public Object execute(HttpServletRequest request, Map<String, Object> params);

}
