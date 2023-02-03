package com.seagox.oa.excel.controller;

import com.seagox.oa.annotation.SysLogPoint;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyCommonWords;
import com.seagox.oa.excel.service.IJellyCommonWordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 常用语
 */
@RestController
@RequestMapping("/commonWords")
public class JellyCommonWordsController {

    @Autowired
    private IJellyCommonWordsService commonWordsService;

    /**
     * 查询全部
     *
     * @param companyId 公司id
     * @param userId    用户id
     */
    @GetMapping("/queryAll")
    public ResultData queryAll(Long companyId, Long userId) {
        return commonWordsService.queryAll(companyId, userId);
    }

    /**
     * 新增
     */
    @PostMapping("/insert")
    @SysLogPoint("新增常用语")
    public ResultData insert(@Valid JellyCommonWords commonWords) {
        return commonWordsService.insert(commonWords);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @SysLogPoint("修改常用语")
    public ResultData update(@Valid JellyCommonWords commonWords) {
        return commonWordsService.update(commonWords);
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    @SysLogPoint("删除常用语")
    public ResultData delete(@PathVariable Long id) {
        return commonWordsService.delete(id);
    }

}
