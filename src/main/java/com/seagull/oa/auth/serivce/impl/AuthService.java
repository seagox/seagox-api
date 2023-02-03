package com.seagull.oa.auth.serivce.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.seagull.oa.auth.serivce.IAuthService;
import com.seagull.oa.common.ResultCode;
import com.seagull.oa.common.ResultData;
import com.seagull.oa.excel.service.IJellyRegionsService;
import com.seagull.oa.security.JwtTokenUtils;
import com.seagull.oa.sys.entity.*;
import com.seagull.oa.sys.mapper.*;
import com.seagull.oa.util.EncryptUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthService implements IAuthService {

	@Autowired
	private SysAccountMapper userMapper;

	@Autowired
	private UserRelateMapper userRelateMapper;

	@Autowired
	private CompanyMapper companyMapper;

	@Autowired
	private DepartmentMapper departmentMapper;

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private MenuMapper menuMapper;

	@Autowired
	private SysThemeMapper sysThemeMapper;

	@Value(value = "${spring.datasource.url}")
	private String datasourceUrl;

	@Autowired
	private IJellyRegionsService regionsService;

	@Override
	public ResultData login(String account, String password, String openid, String avatar) {
		LambdaQueryWrapper<SysAccount> queryWrapper = new LambdaQueryWrapper<SysAccount>();
		queryWrapper.eq(SysAccount::getAccount, account).eq(SysAccount::getPassword, EncryptUtils.md5Encode(password))
				.eq(SysAccount::getStatus, 1);
		SysAccount queryUser = userMapper.selectOne(queryWrapper);
		if (queryUser == null) {
			return ResultData.warn(ResultCode.PARAMETER_ERROR, "用户名或密码错误");
		} else {
			// 更新openid
			if (!StringUtils.isEmpty(openid)) {
				queryUser.setOpenid(openid);
				queryUser.setAvatar(avatar);
				userMapper.updateById(queryUser);
			}

			Map<String, Object> claims = new HashMap<String, Object>();
			Map<String, Object> payload = new HashMap<String, Object>();
			claims.put("userId", queryUser.getId());
			payload.put("userId", queryUser.getId());
			LambdaQueryWrapper<SysUserRelate> userRelateQueryWrapper = new LambdaQueryWrapper<>();
			userRelateQueryWrapper.eq(SysUserRelate::getUserId, queryUser.getId())
					.orderByDesc(SysUserRelate::getUpdateTime);
			List<SysUserRelate> userRelateList = userRelateMapper.selectList(userRelateQueryWrapper);
			if (userRelateList.size() > 0) {
				String permissions = "";
				String roleIds = "";
				String roleNames = "";
				List<String> companyIds = new ArrayList<>();
				for (int i = 0; i < userRelateList.size(); i++) {
					companyIds.add(String.valueOf(userRelateList.get(i).getCompanyId()));
					if (!StringUtils.isEmpty(userRelateList.get(i).getRoleIds())) {
						String[] roleArray = userRelateList.get(i).getRoleIds().split(",");
						for (int j = 0; j < roleArray.length; j++) {
							SysRole role = roleMapper.selectById(roleArray[j]);
							if (StringUtils.isEmpty(permissions)) {
								permissions = permissions + role.getPath();
							} else {
								permissions = permissions + "," + role.getPath();
							}
							if (StringUtils.isEmpty(roleIds)) {
								roleIds = roleIds + role.getId();
								roleNames = roleNames + role.getName();
							} else {
								roleIds = roleIds + "," + role.getId();
								roleNames = roleNames + "," + role.getName();
							}
						}
					}
				}
				List<SysMenu> routes = new ArrayList<>();
				if (!org.springframework.util.StringUtils.isEmpty(permissions)) {
					LambdaQueryWrapper<SysMenu> sysMenuQw = new LambdaQueryWrapper<>();
					sysMenuQw.in(SysMenu::getType, Arrays.asList("4,7,9".split(","))).in(SysMenu::getId,
							Arrays.asList(permissions.split(",")));
					routes = menuMapper.selectList(sysMenuQw);
					permissions = StringUtils.join(menuMapper.queryUserMenuStr(permissions.split(",")), ",");
				}
				if (queryUser.getType() == 3) {
					// 超级管理员
					permissions = permissions + ",platformApplication,sysApplication";
				} else if (queryUser.getType() == 2) {
					// 管理员
					permissions = permissions + ",sysApplication";
				}
				claims.put("routes", routes);
				claims.put("permissions", permissions);
				claims.put("roleIds", roleIds);
				claims.put("roleNames", roleNames);

				List<Map<String, Object>> companyList = companyMapper.queryByIds(companyIds.toArray(new String[] {}),
						StringUtils.join(companyIds, ","));
				claims.put("companyId", companyList.get(0).get("id"));
				payload.put("companyId", companyList.get(0).get("id"));
				claims.put("departmentId", userRelateList.get(0).getDepartmentId());
				SysDepartment department = departmentMapper.selectById(userRelateList.get(0).getDepartmentId());
				if (department != null) {
					claims.put("departmentName", department.getName());
				}
				claims.put("company", companyList);
				SysCompany company = companyMapper.selectById(companyList.get(0).get("id").toString());
				claims.put("logo", company.getLogo());
				claims.put("alias", company.getAlias());
				claims.put("mark", company.getMark());
			} else {
				return ResultData.warn(ResultCode.PARAMETER_ERROR, "用户名未绑定单位");
			}
			payload.put("created", new Date());
			payload.put("version", JwtTokenUtils.VERSION);
			String accessToken = JwtTokenUtils.generateToken(payload, JwtTokenUtils.SECRET,
					System.currentTimeMillis() + JwtTokenUtils.EXPIRATION * 1000);
			claims.put("accessToken", accessToken);
			claims.put("tokenType", JwtTokenUtils.TOKENHEAD);
			claims.put("expiresIn", JwtTokenUtils.EXPIRATION);
			claims.put("account", account);
			claims.put("name", queryUser.getName());
			claims.put("avatar", queryUser.getAvatar());
			claims.put("phone", queryUser.getPhone());
			claims.put("position", queryUser.getPosition());
			claims.put("sex", queryUser.getSex());
			LambdaQueryWrapper<SysTheme> sysThemeQueryWrapper = new LambdaQueryWrapper<>();
			sysThemeQueryWrapper.eq(SysTheme::getUserId, queryUser.getId());
			SysTheme sysTheme = sysThemeMapper.selectOne(sysThemeQueryWrapper);
			if (sysTheme != null) {
				claims.put("color", sysTheme.getColor());
			}
			if (datasourceUrl.contains("mysql")) {
				claims.put("datasourceType", "mysql");
			} else if (datasourceUrl.contains("postgresql")) {
				claims.put("datasourceType", "postgresql");
			} else if (datasourceUrl.contains("oracle")) {
				claims.put("datasourceType", "oracle");
			} else if (datasourceUrl.contains("sqlserver")) {
				claims.put("datasourceType", "sqlserver");
			} else if (datasourceUrl.contains("kingbase8")) {
				claims.put("datasourceType", "kingbase8");
			} else if (datasourceUrl.contains("dm")) {
				claims.put("datasourceType", "dm");
			}
			return ResultData.success(claims);
		}
	}

	public String getFileType(String suffix) {
		// 图片格式
		List<String> imglist = new ArrayList<>(Arrays.asList("png", "jpg", "jpeg", "bmp", "gif"));
		// 进行图片匹配
		if (imglist.contains(suffix)) {
			return "image";
		}
		// 匹配txt
		List<String> txtlist = new ArrayList<>(Arrays.asList("txt"));
		if (txtlist.contains(suffix)) {
			return "txt";
		}
		// 匹配 excel
		List<String> excelist = new ArrayList<>(Arrays.asList("xls", "xlsx"));
		if (excelist.contains(suffix)) {
			return "excel";
		}
		// 匹配 word
		List<String> wordlist = new ArrayList<>(Arrays.asList("doc", "docx"));
		if (wordlist.contains(suffix)) {
			return "word";
		}
		// 匹配 pdf
		List<String> pdflist = new ArrayList<>(Arrays.asList("pdf"));
		if (pdflist.contains(suffix)) {
			return "pdf";
		}
		// 匹配 ppt
		List<String> pptlist = new ArrayList<>(Arrays.asList("ppt", "pptx"));
		if (pptlist.contains(suffix)) {
			return "ppt";
		}
		// 匹配 视频
		List<String> videolist = new ArrayList<>(
				Arrays.asList("mp4", "m2v", "mkv", "rmvb", "wmv", "avi", "flv", "mov", "m4v"));
		if (videolist.contains(suffix)) {
			return "video";
		}
		// 匹配 音频
		List<String> radiolist = new ArrayList<>(Arrays.asList("mp3", "wav", "wmv"));
		if (radiolist.contains(suffix)) {
			return "radio";
		}
		// 匹配 压缩
		List<String> compressedlist = new ArrayList<>(Arrays.asList("zip", "rar"));
		if (compressedlist.contains(suffix)) {
			return "compressed";
		}
		// 其他 文件类型
		return "other";
	}

	@Override
	public ResultData queryRegions() {
		return ResultData.success(regionsService.selectList());
	}

	@Override
	public ResultData verifyLogin(String orgstr, String account, String noncestr, String timestamp, String sign) {
		String key = "yVwlsbIrY3q22EnoYYM4nR5zqTmqed05";
		String ratioSign = EncryptUtils.md5Encode("account=" + account + "&noncestr=" + noncestr + "&org=" + orgstr
				+ "&timestamp=" + timestamp + "&key=" + key).toUpperCase();
		if (ratioSign.equals(sign)) {
			SysAccount queryUser = userMapper.selectById(account);
			if (queryUser == null) {
				return ResultData.warn(ResultCode.SIGN_ERROR, "签名错误");
			} else {
				Map<String, Object> payload = new HashMap<String, Object>();
				Map<String, Object> claims = new HashMap<String, Object>();
				payload.put("userId", queryUser.getId());
				payload.put("companyId", orgstr);
				payload.put("created", new Date());
				payload.put("version", JwtTokenUtils.VERSION);
				String accessToken = JwtTokenUtils.generateToken(payload, JwtTokenUtils.SECRET,
						System.currentTimeMillis() + JwtTokenUtils.EXPIRATION * 1000);
				LambdaQueryWrapper<SysUserRelate> userRelateQueryWrapper = new LambdaQueryWrapper<>();
				userRelateQueryWrapper.eq(SysUserRelate::getUserId, queryUser.getId())
						.orderByDesc(SysUserRelate::getUpdateTime);
				List<SysUserRelate> userRelateList = userRelateMapper.selectList(userRelateQueryWrapper);
				if (userRelateList.size() > 0) {
					String permissions = "";
					String roleIds = "";
					String roleNames = "";
					List<String> companyIds = new ArrayList<>();
					for (int i = 0; i < userRelateList.size(); i++) {
						companyIds.add(String.valueOf(userRelateList.get(i).getCompanyId()));
						if (!StringUtils.isEmpty(userRelateList.get(i).getRoleIds())) {
							String[] roleArray = userRelateList.get(i).getRoleIds().split(",");
							for (int j = 0; j < roleArray.length; j++) {
								SysRole role = roleMapper.selectById(roleArray[j]);
								if (StringUtils.isEmpty(permissions)) {
									permissions = permissions + role.getPath();
								} else {
									permissions = permissions + "," + role.getPath();
								}
								if (StringUtils.isEmpty(roleIds)) {
									roleIds = roleIds + role.getId();
									roleNames = roleNames + role.getName();
								} else {
									roleIds = roleIds + "," + role.getId();
									roleNames = roleNames + "," + role.getName();
								}
							}
						}
					}
					List<SysMenu> routes = new ArrayList<>();
					if (!org.springframework.util.StringUtils.isEmpty(permissions)) {
						LambdaQueryWrapper<SysMenu> sysMenuQw = new LambdaQueryWrapper<>();
						sysMenuQw.in(SysMenu::getType, Arrays.asList("4,7,9".split(","))).in(SysMenu::getId,
								Arrays.asList(permissions.split(",")));
						routes = menuMapper.selectList(sysMenuQw);
						permissions = StringUtils.join(menuMapper.queryUserMenuStr(permissions.split(",")), ",");
					}
					if (queryUser.getType() == 3) {
						// 超级管理员
						permissions = permissions + ",platformApplication,sysApplication";
					} else if (queryUser.getType() == 2) {
						// 管理员
						permissions = permissions + ",sysApplication";
					}
					claims.put("routes", routes);
					claims.put("permissions", permissions);
					claims.put("roleIds", roleIds);
					claims.put("roleNames", roleNames);

					List<Map<String, Object>> companyList = companyMapper
							.queryByIds(companyIds.toArray(new String[] {}), StringUtils.join(companyIds, ","));
					claims.put("companyId", companyList.get(0).get("id"));
					payload.put("companyId", companyList.get(0).get("id"));
					claims.put("departmentId", userRelateList.get(0).getDepartmentId());
					SysDepartment department = departmentMapper.selectById(userRelateList.get(0).getDepartmentId());
					if (department != null) {
						claims.put("departmentName", department.getName());
					}
					claims.put("company", companyList);
					SysCompany company = companyMapper.selectById(companyList.get(0).get("id").toString());
					claims.put("logo", company.getLogo());
					claims.put("alias", company.getAlias());
					claims.put("mark", company.getMark());
				} else {
					return ResultData.warn(ResultCode.PARAMETER_ERROR, "用户名未绑定单位");
				}
				payload.put("created", new Date());
				payload.put("version", JwtTokenUtils.VERSION);

				claims.put("userId", queryUser.getId());
				claims.put("accessToken", accessToken);
				claims.put("tokenType", JwtTokenUtils.TOKENHEAD);
				claims.put("expiresIn", JwtTokenUtils.EXPIRATION);
				claims.put("account", queryUser.getAccount());
				claims.put("name", queryUser.getName());
				claims.put("avatar", queryUser.getAvatar());
				claims.put("phone", queryUser.getPhone());
				claims.put("sex", queryUser.getSex());

				LambdaQueryWrapper<SysTheme> sysThemeQueryWrapper = new LambdaQueryWrapper<>();
				sysThemeQueryWrapper.eq(SysTheme::getUserId, queryUser.getId());
				SysTheme sysTheme = sysThemeMapper.selectOne(sysThemeQueryWrapper);
				if (sysTheme != null) {
					claims.put("color", sysTheme.getColor());
				}
				if (datasourceUrl.contains("mysql")) {
					claims.put("datasourceType", "mysql");
				} else if (datasourceUrl.contains("postgresql")) {
					claims.put("datasourceType", "postgresql");
				} else if (datasourceUrl.contains("oracle")) {
					claims.put("datasourceType", "oracle");
				} else if (datasourceUrl.contains("sqlserver")) {
					claims.put("datasourceType", "sqlserver");
				} else if (datasourceUrl.contains("kingbase8")) {
					claims.put("datasourceType", "kingbase8");
				} else if (datasourceUrl.contains("dm")) {
					claims.put("datasourceType", "dm");
				}
				return ResultData.success(claims);

			}
		} else {
			return ResultData.warn(ResultCode.SIGN_ERROR, "签名错误");
		}
	}

	@Override
	public ResultData verifyByOpenid(String openid) {
		LambdaQueryWrapper<SysAccount> qw = new LambdaQueryWrapper<>();
		qw.eq(SysAccount::getOpenid, openid).eq(SysAccount::getStatus, 1);
		SysAccount queryUser = userMapper.selectOne(qw);
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put("openid", openid);
		if (queryUser == null) {
			claims.put("bindFlag", false);
		} else {
			claims.put("bindFlag", true);
		}
		return ResultData.success(claims);
	}

	@Override
	public ResultData loginByOpenid(String openid) {
		LambdaQueryWrapper<SysAccount> queryWrapper = new LambdaQueryWrapper<SysAccount>();
		queryWrapper.eq(SysAccount::getOpenid, openid).eq(SysAccount::getStatus, 1);
		SysAccount queryUser = userMapper.selectOne(queryWrapper);
		if (queryUser == null) {
			return ResultData.warn(ResultCode.PARAMETER_ERROR, "用户名或密码错误");
		} else {
			Map<String, Object> claims = new HashMap<String, Object>();
			Map<String, Object> payload = new HashMap<String, Object>();
			claims.put("userId", queryUser.getId());
			payload.put("userId", queryUser.getId());
			LambdaQueryWrapper<SysUserRelate> userRelateQueryWrapper = new LambdaQueryWrapper<>();
			userRelateQueryWrapper.eq(SysUserRelate::getUserId, queryUser.getId())
					.orderByDesc(SysUserRelate::getUpdateTime);
			List<SysUserRelate> userRelateList = userRelateMapper.selectList(userRelateQueryWrapper);
			if (userRelateList.size() > 0) {
				String permissions = "";
				List<String> companyIds = new ArrayList<>();
				for (int i = 0; i < userRelateList.size(); i++) {
					companyIds.add(String.valueOf(userRelateList.get(i).getCompanyId()));
					if (!StringUtils.isEmpty(userRelateList.get(i).getRoleIds())) {
						String[] roleArray = userRelateList.get(i).getRoleIds().split(",");
						for (int j = 0; j < roleArray.length; j++) {
							SysRole role = roleMapper.selectById(roleArray[j]);
							if (StringUtils.isEmpty(permissions)) {
								permissions = permissions + role.getPath();
							} else {
								permissions = permissions + "," + role.getPath();
							}
						}
					}
				}
				List<SysMenu> routes = new ArrayList<>();
				if (!org.springframework.util.StringUtils.isEmpty(permissions)) {
					LambdaQueryWrapper<SysMenu> sysMenuQw = new LambdaQueryWrapper<>();
					sysMenuQw.in(SysMenu::getType, Arrays.asList("4,7,9".split(","))).in(SysMenu::getId,
							Arrays.asList(permissions.split(",")));
					routes = menuMapper.selectList(sysMenuQw);
					permissions = StringUtils.join(menuMapper.queryUserMenuStr(permissions.split(",")), ",");
				}
				if (queryUser.getType() == 3) {
					// 超级管理员
					permissions = permissions + ",platformApplication,sysApplication";
				} else if (queryUser.getType() == 2) {
					// 管理员
					permissions = permissions + ",sysApplication";
				}
				claims.put("routes", routes);
				claims.put("permissions", permissions);

				List<Map<String, Object>> companyList = companyMapper.queryByIds(companyIds.toArray(new String[] {}),
						StringUtils.join(companyIds, ","));
				claims.put("companyId", companyList.get(0).get("id"));
				payload.put("companyId", companyList.get(0).get("id"));
				claims.put("departmentId", userRelateList.get(0).getDepartmentId());
				SysDepartment department = departmentMapper.selectById(userRelateList.get(0).getDepartmentId());
				if (department != null) {
					claims.put("departmentName", department.getName());
				}
				claims.put("company", companyList);
				SysCompany company = companyMapper.selectById(companyList.get(0).get("id").toString());
				claims.put("logo", company.getLogo());
				claims.put("alias", company.getAlias());
				claims.put("mark", company.getMark());
			} else {
				return ResultData.warn(ResultCode.PARAMETER_ERROR, "用户名未绑定单位");
			}
			payload.put("created", new Date());
			payload.put("version", JwtTokenUtils.VERSION);
			String accessToken = JwtTokenUtils.generateToken(payload, JwtTokenUtils.SECRET,
					System.currentTimeMillis() + JwtTokenUtils.EXPIRATION * 1000);
			claims.put("accessToken", accessToken);
			claims.put("tokenType", JwtTokenUtils.TOKENHEAD);
			claims.put("expiresIn", JwtTokenUtils.EXPIRATION);
			claims.put("account", queryUser.getAccount());
			claims.put("name", queryUser.getName());
			claims.put("avatar", queryUser.getAvatar());
			claims.put("phone", queryUser.getPhone());
			claims.put("position", queryUser.getPosition());
			claims.put("sex", queryUser.getSex());
			LambdaQueryWrapper<SysTheme> sysThemeQueryWrapper = new LambdaQueryWrapper<>();
			sysThemeQueryWrapper.eq(SysTheme::getUserId, queryUser.getId());
			SysTheme sysTheme = sysThemeMapper.selectOne(sysThemeQueryWrapper);
			if (sysTheme != null) {
				claims.put("color", sysTheme.getColor());
			}
			if (datasourceUrl.contains("mysql")) {
				claims.put("datasourceType", "mysql");
			} else if (datasourceUrl.contains("postgresql")) {
				claims.put("datasourceType", "postgresql");
			} else if (datasourceUrl.contains("oracle")) {
				claims.put("datasourceType", "oracle");
			} else if (datasourceUrl.contains("sqlserver")) {
				claims.put("datasourceType", "sqlserver");
			} else if (datasourceUrl.contains("kingbase8")) {
				claims.put("datasourceType", "kingbase8");
			} else if (datasourceUrl.contains("dm")) {
				claims.put("datasourceType", "dm");
			}
			return ResultData.success(claims);
		}
	}
}
