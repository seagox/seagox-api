package com.seagull.oa.security;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seagull.oa.common.ResultCode;
import com.seagull.oa.common.ResultData;
import com.seagull.oa.util.EncryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Value("#{'${ignore.token}'.split(',')}")
    private String[] ignoreToken;

    @Value("#{'${ignore.sign}'.split(',')}")
    private String[] ignoreSign;

    @Autowired
    private OnlineCounter onlineCounter;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String uri = request.getRequestURI();

        if (request.getMethod().equals(RequestMethod.OPTIONS.name())) {
        	response.setHeader("Access-Control-Allow-Origin","*");
            response.setHeader("Access-Control-Allow-Headers","*");
            response.setHeader("Access-Control-Allow-Methods","*");
            response.setHeader("Access-Control-Allow-Credentials","true");
            response.setHeader("Access-Control-Max-Age","3600");
            response.setStatus(HttpStatus.OK.value());
            return;
        }

        boolean doSign = true;
        for (String notSign : ignoreSign) {
            if (uri.indexOf(notSign) != -1) {
                doSign = false;
                break;
            }
        }
        if (doSign) {
            String sign = request.getHeader("Sign");
            String timestamp = request.getHeader("Timestamp");
            if (StringUtils.isEmpty(sign) || StringUtils.isEmpty(timestamp)) {
                ObjectMapper mapper = new ObjectMapper();
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().write(mapper.writeValueAsString(ResultData.warn(ResultCode.SIGN_ERROR, "缺少签名")));
                return;
            }
            long second = (System.currentTimeMillis() - Long.valueOf(timestamp)) / 1000;
            if (second > 1800 || second < -1800) {
                ObjectMapper mapper = new ObjectMapper();
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().write(mapper.writeValueAsString(ResultData.warn(ResultCode.SIGN_ERROR, "时间差有误")));
                return;
            }
            Enumeration<String> parametes = request.getParameterNames();
            List<String> parameteList = new ArrayList<>();
            while (parametes.hasMoreElements()) {
                String name = (String) parametes.nextElement();
                String value = request.getParameter(name);
                if (!StringUtils.isEmpty(name)) {
                    parameteList.add(name + "=" + value);
                }
            }
            parameteList.add("timestamp=" + timestamp);

            String[] keys = parameteList.toArray(new String[]{});
            Arrays.sort(keys);
            StringBuffer reqStr = new StringBuffer();
            for (int i = 0; i < keys.length; i++) {
                if (!StringUtils.isEmpty(keys[i])) {
                    reqStr.append(keys[i] + "&");
                }
            }
            reqStr.append("key=yVwlsbIrY3q22EnoYYM4nR5zqTmqed05");

            String signAfter = EncryptUtils.md5Encode(reqStr.toString()).toUpperCase();
            if (!sign.equals(signAfter)) {
                ObjectMapper mapper = new ObjectMapper();
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().write(mapper.writeValueAsString(ResultData.warn(ResultCode.SIGN_ERROR, "签名错误")));
                return;
            }
        }

        boolean doFilter = true;
        for (String notFilter : ignoreToken) {
            if (uri.indexOf(notFilter) != -1) {
                doFilter = false;
                break;
            }
        }

        if (doFilter) {
            String authHeader = request.getHeader(JwtTokenUtils.HEADER);
            if (authHeader != null && authHeader.startsWith(JwtTokenUtils.TOKENHEAD)) {
                final String authToken = authHeader.substring(JwtTokenUtils.TOKENHEAD.length());
                onlineCounter.insertToken(authToken);
                JSONObject payload = JwtTokenUtils.getPayload(authToken);
                String userId = payload.getString("userId");
                String companyId = payload.getString("companyId");
                if (!StringUtils.isEmpty(userId)) {
                    if (new Date(payload.getLongValue("exp")).after(new Date())) {
                        ObjectMapper mapper = new ObjectMapper();
                        response.setContentType("application/json;charset=utf-8");
                        response.getWriter().write(mapper.writeValueAsString(ResultData.error(ResultCode.UNAUTHORIZED)));
                        return;
                    } else {
                        Map<String, String[]> m = new HashMap<String, String[]>(request.getParameterMap());
                        m.put("userId", new String[]{userId});
                        m.put("companyId", new String[]{companyId});
                        request = new ParameterRequestWrapper(request, m);
                    }
                } else {
                    ObjectMapper mapper = new ObjectMapper();
                    response.setContentType("application/json;charset=utf-8");
                    response.getWriter().write(mapper.writeValueAsString(ResultData.error(ResultCode.UNAUTHORIZED)));
                    return;
                }
            } else {
                ObjectMapper mapper = new ObjectMapper();
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().write(mapper.writeValueAsString(ResultData.error(ResultCode.UNAUTHORIZED)));
                return;
            }
        } else {
            String authHeader = request.getHeader(JwtTokenUtils.HEADER);
            if (authHeader != null && authHeader.startsWith(JwtTokenUtils.TOKENHEAD)) {
                final String authToken = authHeader.substring(JwtTokenUtils.TOKENHEAD.length());
                onlineCounter.insertToken(authToken);
                JSONObject payload = JwtTokenUtils.getPayload(authToken);
                String userId = payload.getString("userId");
                String companyId = payload.getString("companyId");
                if (!StringUtils.isEmpty(userId)) {
                    if (new Date(payload.getLongValue("exp")).before(new Date())) {
                        Map<String, String[]> m = new HashMap<String, String[]>(request.getParameterMap());
                        m.put("userId", new String[]{userId});
                        m.put("companyId", new String[]{companyId});
                        request = new ParameterRequestWrapper(request, m);
                    }
                }
            }
        }

        chain.doFilter(request, response);
    }
}
