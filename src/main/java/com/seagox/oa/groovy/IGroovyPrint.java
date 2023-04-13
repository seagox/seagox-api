package com.seagox.oa.groovy;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface IGroovyPrint {

    public Map<String, Object> execute(HttpServletRequest request, Map<String, Object> params);

}
