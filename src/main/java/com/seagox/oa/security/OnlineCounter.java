package com.seagox.oa.security;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class OnlineCounter {

    //每次打开此类只初始化一次countMap
    private static Map<String, Object> countMap = new ConcurrentHashMap<String, Object>();

    public void insertToken(String token) {
        //获得当前时间(毫秒)
        long currentTime = System.currentTimeMillis();
        //解析token，获得签发时间
        JSONObject payload = JwtTokenUtils.getPayload(token);
        Date issuedAt = new Date(payload.getLongValue("created"));
        //以签发时间为key。当前时间+60s为value存入countMap中
        if (issuedAt != null) {
            countMap.put(issuedAt.toString(), currentTime + 60 * 1000);
        }
    }

    /**
     * 获取当前在线用户数
     */
    public Integer getOnlineCount() {
        int onlineCount = 0;
        for (Map.Entry<String, Object> entry : countMap.entrySet()) {
            Long value = (Long) entry.getValue();
            if (value > System.currentTimeMillis()) {
                //过期时间大于当前时间则没有过期
                onlineCount++;
            }
        }
        return onlineCount;
    }
}