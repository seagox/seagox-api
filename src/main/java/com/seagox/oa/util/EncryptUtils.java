package com.seagox.oa.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;

public class EncryptUtils {
    private static final Logger logger = LoggerFactory.getLogger(EncryptUtils.class);

    public static String md5Encode(String origin) {
        String resultString = null;
        try {
            resultString = origin;
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = bytesToHexString(md.digest(resultString.getBytes("utf-8")));//正确的写法
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return resultString;
    }

    public static String sha1Encode(String origin) {
        String resultString = null;
        try {
            resultString = origin;
            MessageDigest md = MessageDigest.getInstance("SHA1");
            resultString = bytesToHexString(md.digest(resultString.getBytes("utf-8")));//正确的写法
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return resultString;
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static byte[] getSignature(String data, String key) {
        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(),
                    mac.getAlgorithm());
            mac.init(signingKey);

            return mac.doFinal(data.getBytes());
        } catch (Exception e) {
            return null;
        }
    }
}
