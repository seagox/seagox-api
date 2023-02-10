package com.seagox.oa.auth.controller;

import com.alibaba.fastjson.JSONObject;
import com.seagox.oa.annotation.SysLogPoint;
import com.seagox.oa.auth.serivce.IAuthService;
import com.seagox.oa.common.ResultCode;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.groovy.IGroovyCloud;
import com.seagox.oa.security.OnlineCounter;
import com.seagox.oa.util.WeiChatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 认证
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private IAuthService authService;

	@Autowired
	private OnlineCounter onlineCounter;

	@Autowired
	private WeiChatUtils weiChatUtils;

	@Autowired
	private IGroovyCloud groovyCloud;

	/**
	 * 登陆
	 *
	 * @param account  用户名
	 * @param password 密码
	 * @param openid   openid
	 * @param avatar   头像地址
	 */
	@PostMapping("/login")
	@SysLogPoint("登陆")
	public ResultData login(String account, String password, String openid, String avatar) {
		return authService.login(account, password, openid, avatar);
	}
	
	/**
	 * 登陆(控制台)
	 *
	 * @param account  用户名
	 * @param password 密码
	 */
	@PostMapping("/loginConsole")
	@SysLogPoint("登陆(控制台)")
	public ResultData loginConsole(String account, String password) {
		return authService.loginConsole(account, password);
	}

	/**
	 * 获取当前实时在线人数(精确度为60s范围内)
	 */
	@GetMapping("/getOnlineCount")
	public ResultData getRealOnlineCount() {
		return ResultData.success(onlineCounter.getOnlineCount());
	}

	/**
	 * 获取区域数据
	 */
	@GetMapping("/queryRegions")
	public ResultData queryRegions() {
		return authService.queryRegions();
	}

	/**
	 * 验证登陆
	 *
	 * @param org          组织
	 * @param account      用户名
	 * @param noncestr     随机数
	 * @param timestampStr 时间戳
	 * @param sign         签名
	 */
	@PostMapping("/verifyLogin")
	public ResultData verifyLogin(String org, String account, String noncestr, String timestampStr, String sign) {
		return authService.verifyLogin(org, account, noncestr, timestampStr, sign);
	}

	/**
	 * 小程序登录
	 *
	 * @param code 编码
	 */
	@GetMapping("/loginByCode/{code}")
	public ResultData loginByCode(@PathVariable String code) {
		JSONObject jsonObject = weiChatUtils.getAppletsLoginCertificate(code);
		if (jsonObject != null) {
			if (jsonObject.containsKey("openid")) {
				return authService.verifyByOpenid(jsonObject.getString("openid"));
			} else {
				return ResultData.warn(ResultCode.INVALID_CODE);
			}
		} else {
			return ResultData.warn(ResultCode.INVALID_CODE);
		}
	}

	/**
	 * 验证小程序登录
	 *
	 * @param openid openid
	 */
	@GetMapping("/loginByOpenid/{openid}")
	public ResultData loginByOpenid(@PathVariable String openid) {
		return authService.loginByOpenid(openid);
	}

	@RequestMapping("/testCloud")
	public ResultData testCloud(HttpServletRequest request) {
		return groovyCloud.execute(request);
	}

}
