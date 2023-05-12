package com.seagox.oa.excel.service;

import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyBusinessTable;

public interface IJellyBusinessTableService {

	/**
	 * 查询全部
	 */
	public ResultData queryAll(Long companyId, String name, String remark);

	/**
	 * 添加
	 */
	public ResultData insert(JellyBusinessTable businessTable);

	/**
	 * 修改
	 */
	public ResultData update(JellyBusinessTable businessTable);

	/**
	 * 删除
	 */
	public ResultData delete(Long id);

	/**
	 * 查询通过id
	 */
	public ResultData queryById(Long id);

	/**
	 * 查询通过表ids
	 */
	public ResultData queryByTableIds(String tableIds);

	/**
	 * 复制表
	 */
	public ResultData copyTable(Long id);

	/**
	 * 根据类型查询所有表
	 */
	public ResultData queryByType(String type);

	/**
	 * 根据级联数据
	 */
	public ResultData queryCascader(Long id, String rule);

	/**
	 * 数据模型接口
	 */
	public ResultData queryModel(Long companyId);

}
