package com.seagull.oa.auth.serivce;

import com.seagull.oa.common.ResultData;

/**
 * 权限服务接口
 */
public interface IAuthService {

	/**
	 * 登陆
	 *
	 * @param name     用户名
	 * @param password 密码
	 * @param openid   openid
	 * @param avatar   头像
	 */
	public ResultData login(String account, String password, String openid, String avatar);

	/**
	 * 获取区域数据
	 */
	public ResultData queryRegions();

	/**
	 * 验证登陆
	 *
	 * @param org       组织
	 * @param account   用户名
	 * @param noncestr  随机数
	 * @param timestamp 时间戳
	 * @param sign      签名
	 */
	public ResultData verifyLogin(String org, String account, String noncestr, String timestamp, String sign);

	/**
	 * 登录（小程序）
	 *
	 * @param openid openid
	 */
	public ResultData verifyByOpenid(String openid);

	/**
	 * 登录（小程序）
	 *
	 * @param openid openid
	 */
	public ResultData loginByOpenid(String openid);

}