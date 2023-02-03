package com.seagull.oa.util;

import com.alibaba.fastjson.JSONObject;
import com.seagull.oa.excel.service.IJellyRegionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class JxlsUtils {

    private static IJellyRegionsService iJellyRegionsService;

    private static Map<String, String> regionsMap;

    // 通过重新set注入
    @Autowired
    public void setIJellyRegionsService(IJellyRegionsService iJellyRegionsService) {
        JxlsUtils.iJellyRegionsService = iJellyRegionsService;
        JxlsUtils.regionsMap = JxlsUtils.iJellyRegionsService.selectList();
    }

    /**
     * 地区区域转换
     */
    public String regionTransform(String target) {
        List<String> result = new ArrayList<>();
        if (!StringUtils.isEmpty(target)) {
            List<String> targetList = JSONObject.parseArray(target, String.class);
            for (String str : targetList) {
                result.add(regionsMap.get(str));
            }
        }
        return org.apache.commons.lang3.StringUtils.join(result, "/");
    }

}
