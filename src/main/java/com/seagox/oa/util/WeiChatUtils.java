package com.seagox.oa.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 微信工具类
 */
@Component
public class WeiChatUtils {

    /**
     * 小程序登录凭证
     */
    public static final String APPLETS_LOGIN_CERTIFICATE = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=CODE&grant_type=authorization_code";

    /**
     * 获取小程序及服务号access_token地址
     */
    public static final String ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=SECRET";

    /**
     * 服务号发送模板消息地址
     */
    public static final String SEND_SERVICE_MSG = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESSTOKEN";

    /**
     * 小程序APPID
     */
    @Value("${third-party.applets.appid}")
    private String appletsAppid;

    /**
     * 小程序SERCRET
     */
    @Value("${third-party.applets.sercret}")
    private String appletsSercret;

    /**
     * 服务号APPID
     */
    @Value("${third-party.serviceNumber.appid}")
    private String serviceNumberAppid;

    /**
     * 服务号SERCRET
     */
    @Value("${third-party.serviceNumber.sercret}")
    private String serviceNumberSercret;

    /**
     * 获取小程序登录凭证
     */
    public JSONObject getAppletsLoginCertificate(String code) {
        try {
            String url = APPLETS_LOGIN_CERTIFICATE.replace("APPID", appletsAppid).replace("SECRET", appletsSercret)
                    .replace("CODE", code);
            String result = HttpClientUtils.doGet(url);
            if (!StringUtils.isEmpty(result)) {
                return JSON.parseObject(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取服务号access_token
     */
    public JSONObject getServiceNumberAccessToken() {
        try {
            String url = ACCESS_TOKEN.replace("APPID", serviceNumberAppid).replace("SECRET", serviceNumberSercret);
            String result = HttpClientUtils.doGet(url);
            if (!StringUtils.isEmpty(result)) {
                return JSON.parseObject(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 发送服务号模版消息
     */
    public static JSONObject sendServiceMsg(String accessToken, String openid, String templateId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("touser", openid);
        jsonObject.put("template_id", templateId);
        jsonObject.put("url", "http://www.baidu.com");

        JSONObject data = new JSONObject();
        JSONObject first = new JSONObject();
        first.put("value", "hello");
        first.put("color", "#173177");
        JSONObject keyword1 = new JSONObject();
        keyword1.put("value", "hello");
        keyword1.put("color", "#173177");
        JSONObject keyword2 = new JSONObject();
        keyword2.put("value", "hello");
        keyword2.put("color", "#173177");
        JSONObject keyword3 = new JSONObject();
        keyword3.put("value", "hello");
        keyword3.put("color", "#173177");
        JSONObject keyword4 = new JSONObject();
        keyword4.put("value", "hello");
        keyword4.put("color", "#173177");
        JSONObject keyword5 = new JSONObject();
        keyword5.put("value", "hello");
        keyword5.put("color", "#173177");
        JSONObject remark = new JSONObject();
        remark.put("value", "hello");
        remark.put("color", "#173177");

        data.put("first", first);
        data.put("keyword1", keyword1);
        data.put("keyword2", keyword2);
        data.put("keyword3", keyword3);
        data.put("keyword4", keyword4);
        data.put("keyword5", keyword5);
        data.put("remark", remark);

        jsonObject.put("data", data);

        String url = SEND_SERVICE_MSG.replace("ACCESSTOKEN", accessToken);
        String result = HttpClientUtils.sendPostJsonStr(url, jsonObject.toJSONString(), "text/plain");
        if (!StringUtils.isEmpty(result)) {
            return JSON.parseObject(result);
        }
        return null;
    }

}
