package com.seagull.oa.security;

import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Base64;
import java.util.Date;
import java.util.Map;

public class JwtTokenUtils {

    public static final String HEADER = "Authorization";
    public static final String SECRET = "seagull";
    public static final long EXPIRATION = 86400;
    public static final String TOKENHEAD = "Bearer ";
    public static final String VERSION = "1.0.0";

    public static String generateToken(Map<String, Object> claims, String secret, long expiration) {
        Date exp = new Date(expiration);
        return Jwts.builder().setClaims(claims).setExpiration(exp)
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public static JSONObject getPayload(String token) {
        try {
            String payload = new String(Base64.getMimeDecoder().decode(token.split("\\.")[1]));
            return JSONObject.parseObject(payload);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
