package com.seagull.oa.excel.controller;

import com.seagull.oa.annotation.SysLogPoint;
import com.seagull.oa.common.ResultData;
import com.seagull.oa.excel.entity.JellyOpenApi;
import com.seagull.oa.excel.service.IJellyOpenApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * openApi
 */
@RestController
@RequestMapping("/jellyOpenApi")
public class JellyOpenApiController {

    @Autowired
    private IJellyOpenApiService openApiService;

    /**
     * 分页查询
     *
     * @param pageNo   起始页
     * @param pageSize 每页大小
     */
    @GetMapping("/queryByPage")
    public ResultData queryByPage(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, Long companyId) {
        return openApiService.queryByPage(pageNo, pageSize, companyId);
    }

    /**
     * 新增
     */
    @PostMapping("/insert")
    @SysLogPoint("新增openApi")
    public ResultData insert(@Valid JellyOpenApi openApi) {
        return openApiService.insert(openApi);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @SysLogPoint("修改openApi")
    public ResultData update(@Valid JellyOpenApi openApi) {
        return openApiService.update(openApi);
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    @SysLogPoint("删除openApi")
    public ResultData delete(@PathVariable Long id, String appid) {
        return openApiService.delete(id, appid);
    }

}
