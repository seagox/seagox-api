package com.seagox.oa.groovy;

import com.seagox.oa.common.ResultData;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface IGroovyImportHandle {

    public ResultData importHandle(HttpServletRequest request, List<Map<String, Object>> result, Map<String, Object> params);

}
