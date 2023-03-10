package com.seagox.oa.excel.controller;

import com.seagox.oa.annotation.SysLogPoint;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyPrint;
import com.seagox.oa.excel.service.IJellyPrintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 打印模版
 */
@RestController
@RequestMapping("/jellyPrint")
public class JellyPrintController {

	@Autowired
	private IJellyPrintService printService;
	
	/**
	 * 分页查询
	 * 
	 * @param pageNo   起始页
     * @param pageSize 每页大小
     * @param companyId 公司id
	 * @param name     名称
	 */
	@GetMapping("/queryByPage")
	public ResultData queryByPage(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, Long companyId, String name) {
		return printService.queryByPage(pageNo, pageSize, companyId, name);
	}
	
	/**
	 * 查询全部
	 */
	@GetMapping("/queryAll")
	public ResultData queryAll(Long companyId) {
		return printService.queryAll(companyId);
	}

	/**
	 * 新增
	 */
	@PostMapping("/insert")
	@SysLogPoint("新增打印模版")
	public ResultData insert(@Valid JellyPrint print) {
		return printService.insert(print);
	}

	/**
	 * 修改
	 */
	@PostMapping("/update")
	@SysLogPoint("修改打印模版")
	public ResultData update(@Valid JellyPrint print) {
		return printService.update(print);
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete/{id}")
	@SysLogPoint("删除打印模版")
	public ResultData delete(@PathVariable Long id) {
		return printService.delete(id);
	}
	
	/**
	 * 详情
	 */
	@GetMapping("/queryById/{id}")
	public ResultData queryById(@PathVariable Long id) {
		return printService.queryById(id);
	}
	
}
