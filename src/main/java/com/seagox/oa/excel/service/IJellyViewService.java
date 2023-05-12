package com.seagox.oa.excel.service;

import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyView;

public interface IJellyViewService {

	/**
	 * 查询全部
	 */
	public ResultData queryAll(Long companyId, String name, String remark);

	/**
	 * 添加
	 */
	public ResultData insert(JellyView view);

	/**
	 * 修改
	 */
	public ResultData update(JellyView view);

	/**
	 * 删除
	 */
	public ResultData delete(Long id);

	/**
	 * 查询通过id
	 */
	public ResultData queryById(Long id);

}
