package com.seagull.oa.util;

import org.apache.commons.codec.binary.StringUtils;

import java.util.*;

public class ListMapUtils {

    /**
     * 去重
     *
     * @param list
     * @param mapKey
     * @return
     */
    public static List<Map<String, Object>> qc(List<Map<String, Object>> list, String mapKey) {
        if (list.size() <= 0) {
            return null;
        }

        List<Map<String, Object>> listMap = new ArrayList<>();
        Map<String, Map<String, Object>> msp = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = list.get(i);
            String id = map.get(mapKey).toString();
            map.remove(mapKey);
            msp.put(id, map);
        }
        Set<String> mspKey = msp.keySet();
        for (String key : mspKey) {
            Map<String, Object> newMap = msp.get(key);
            newMap.put(mapKey, key);
            listMap.add(newMap);
        }
        return listMap;
    }

    /**
     * 根据字段排序
     *
     * @param list
     * @param feild    排序字段
     * @param sortType desc倒序 asc 正序
     * @return
     */
    public static List<Map<String, Object>> sortByFeild(List<Map<String, Object>> list, String feild, String sortType) {
        if (list.size() > 0) {
            list.sort((m1, m2) -> {
                if (StringUtils.equals(sortType, "desc")) {
                    return Integer.parseInt(m2.get(feild).toString()) > Integer.parseInt(m1.get(feild).toString()) ? 1 : -1;
                } else {
                    return Integer.parseInt(m1.get(feild).toString()) > Integer.parseInt(m2.get(feild).toString()) ? 1 : -1;
                }
            });
        }
        return list;
    }

}