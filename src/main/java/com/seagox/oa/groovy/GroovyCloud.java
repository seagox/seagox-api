package com.seagox.oa.groovy;

import com.seagox.oa.common.ResultData;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class GroovyCloud implements IGroovyCloud {

    @Override
    public ResultData entrance(HttpServletRequest request) {
        // 业务逻辑

        return ResultData.success(null);
    }
}