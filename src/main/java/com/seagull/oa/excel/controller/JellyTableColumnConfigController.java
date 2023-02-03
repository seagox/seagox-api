package com.seagull.oa.excel.controller;

import com.seagull.oa.annotation.SysLogPoint;
import com.seagull.oa.common.ResultData;
import com.seagull.oa.excel.entity.JellyTableColumnConfig;
import com.seagull.oa.excel.service.IJellyTableColumnConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 表字段配置
 */
@RestController
@RequestMapping("/tableColumnConfig")
public class JellyTableColumnConfigController {

    @Autowired
    private IJellyTableColumnConfigService tableColumnConfigService;

    /**
     * 新增
     */
    @PostMapping("/insert")
    @SysLogPoint("新增表头配置")
    public ResultData insert(JellyTableColumnConfig tableColumnConfig) {
        return tableColumnConfigService.insert(tableColumnConfig);
    }

}
