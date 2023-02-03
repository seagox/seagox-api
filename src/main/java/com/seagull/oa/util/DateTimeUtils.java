package com.seagull.oa.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    // 比较时间大小
    public static int compareDate(String time1, String time2) {
        try {
            Date dt1 = sdf.parse(time1);
            Date dt2 = sdf.parse(time2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;// 开始在后
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;// 开始在前
            } else {// 相等
                return 0;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return 3;
        }
    }

    // 判断时间是否大于当天日期
    public static int isSarly(String time) {
        try {
            String now = sdf.format(new Date());
            Date dt1 = sdf.parse(now);
            Date dt2 = sdf.parse(time);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;// 开始在后
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;// 开始在前
            } else {// 相等
                return 0;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return 3;
        }
    }

}
