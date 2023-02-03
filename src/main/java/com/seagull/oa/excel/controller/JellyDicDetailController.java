package com.seagull.oa.excel.controller;

import com.seagull.oa.annotation.SysLogPoint;
import com.seagull.oa.common.ResultData;
import com.seagull.oa.excel.entity.JellyDicDetail;
import com.seagull.oa.excel.service.IJellyDicDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 数据字典详情
 */
@RestController
@RequestMapping("/dictionaryDetail")
public class JellyDicDetailController {

    @Autowired
    private IJellyDicDetailService dicDetailService;

    /**
     * 分页查询
     *
     * @param pageNo     起始页
     * @param pageSize   每页大小
     * @param classifyId 字典分类id
     */
    @GetMapping("/queryByPage")
    public ResultData queryByPage(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, Long classifyId) {
        return dicDetailService.queryByPage(pageNo, pageSize, classifyId);
    }

    /**
     * 查询显示
     */
    @GetMapping("/queryDisplay")
    public ResultData queryDisplay(Long classifyId) {
        return dicDetailService.queryDisplay(classifyId);
    }

    /**
     * 新增
     */
    @PostMapping("/insert")
    @SysLogPoint("新增字典")
    public ResultData insert(@Valid JellyDicDetail dicDetail) {
        return dicDetailService.insert(dicDetail);
    }

    /**
     * 更新
     */
    @PostMapping("/update")
    @SysLogPoint("更新字典")
    public ResultData update(@Valid JellyDicDetail dicDetail) {
        return dicDetailService.update(dicDetail);
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    @SysLogPoint("删除字典")
    public ResultData delete(@PathVariable Long id) {
        return dicDetailService.delete(id);
    }

    /**
     * 字典分类详情
     *
     * @param classifyId 字典分类id
     */
    @GetMapping("/queryByClassifyId/{classifyId}")
    public ResultData queryByClassifyId(@PathVariable Long classifyId) {
        return dicDetailService.queryByClassifyId(classifyId);
    }

}
