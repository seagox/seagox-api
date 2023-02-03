package com.seagull.oa.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class EcloudUtils {

    /**
     * 获取token地址
     */
    public static final String TOKEN_URL = "https://rtc.migu.cn:3443/api/app/v1/auth/create?apiId=interactive-live1.0&currentTimeStamp=TIME&grantType=v2.0&random=RANDOM&sign=SIGN";
    /**
     * APPID
     */
    private static final String APPID = "980787330879062016";
    /**
     * SERCRET
     */
    private static final String SERCRET = "5v5abh6slqpg7790nbssc6kvk0hykmfyl8ngtejidpk29hz74g4f7u7zplwmmgue9zp6nbm5ekct3ksu1a0t3g6yxbpxqxa2iylcbai1aijlub4zw2uptz9blvgr487d";

    /**
     * 过期时间(毫秒值)
     */
    private static final Long TIME_OUT = 72000000L;

    /**
     * 移动云 音视频 获取token
     */
    public static JSONObject getToken(String uid, String roomId) {
        try {
            long millis = System.currentTimeMillis();
            String random = getSecret(8);
            long tokenExpired = millis + TIME_OUT;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("appId", APPID);
            jsonObject.put("roomId", roomId);
            jsonObject.put("userId", uid);
            jsonObject.put("tokenExpired", tokenExpired);
            String encode = rawUrlEncoder(jsonObject.toJSONString(), "utf-8");
            String data = "apiId=interactive-live1.0&body=" + encode + "&currentTimeStamp=" + millis
                    + "&grantType=v2.0&random=" + random;
            String sign = HMACSHA256(data, SERCRET);
            String url = TOKEN_URL.replace("SIGN", sign).replace("TIME", millis + "").replace("RANDOM", random);
            String result = HttpClientUtils.sendPostJsonStr(url, jsonObject.toJSONString(), "application/json");
            if (!StringUtils.isEmpty(result)) {
                return JSON.parseObject(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * encode编码
     *
     * @param value
     * @param charset
     * @return
     */
    public static String rawUrlEncoder(String value, String charset) {
        // JAVAUrl编码
        String urlEncoderStr = null;
        try {
            urlEncoderStr = URLEncoder.encode(value, charset);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 文本URL编码(空格、~处理)
        // 空格：会被UrlEncoder转成+，《RFC3986》编码为%20
        // ~:会被UrlEncoder编码为%7E，《RFC3986》允许存在
        // JDK 的URLEncoder.encode 对 * 不处理，需要单独处理
        return urlEncoderStr.replace("+", "%20").replace("%7E", "~").replace("*", "%2A");
    }

    /**
     * HMACSHA256加密
     *
     * @param data
     * @param key
     * @return
     */
    public static String HMACSHA256(String data, String key) {
        Mac sha256_HMAC = null;
        try {
            sha256_HMAC = Mac.getInstance("HmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        SecretKeySpec secret_key = null;
        try {
            secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            sha256_HMAC.init(secret_key);
        } catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        byte[] array = null;
        try {
            array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString();
    }

    /**
     * 生成随机密码
     *
     * @param pwd_len 生成的密码的总长度
     * @return 密码的字符串
     */
    public static String getSecret(int pwd_len) {
        // 35是因为数组是从0开始的，26个字母+10个数字
        final int maxNum = 36;
        int i; // 生成的随机数
        int count = 0; // 生成的密码的长度
        char[] str = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
                't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
                'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
                'Z'};
        StringBuilder pwd = new StringBuilder("");
        Random r = new Random();
        while (count < pwd_len) {
            // 生成随机数，取绝对值，防止生成负数，

            i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1

            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count++;
            }
        }
        return pwd.toString();
    }

}
