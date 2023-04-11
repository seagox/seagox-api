package com.seagox.oa.groovy;

import com.seagox.oa.common.ResultData;
import com.seagox.oa.util.JdbcTemplateUtils;
import com.seagox.oa.util.SpringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class GroovyImportHandle implements IGroovyImportHandle {

    @Override
    public ResultData importHandle(HttpServletRequest request, List<Map<String, Object>> result, Map<String, Object> params) {
        JdbcTemplate jdbcTemplate = SpringUtils.getBean(JdbcTemplate.class);
        String datasourceUrl = params.get("datasourceUrl").toString();
        String tableName = params.get("tableName").toString();
        
        if (datasourceUrl.contains("oracle")) {
            JdbcTemplateUtils.batchInsert(jdbcTemplate, tableName, result, "oracle");
        } else {
            JdbcTemplateUtils.batchInsert(jdbcTemplate, tableName, result);
        }
        
        return ResultData.success(null);
    }
}
