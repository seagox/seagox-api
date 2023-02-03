package com.seagull.oa.export.controller;

import com.seagull.oa.annotation.SysLogPoint;
import com.seagull.oa.common.ResultData;
import com.seagull.oa.export.entity.JellyExportDimension;
import com.seagull.oa.export.service.IJellyExportDimensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/exportDimension")
public class JellyExportDimensionController {

    @Autowired
    private IJellyExportDimensionService exportDimensionService;

    /**
     * 分页查询
     *
     * @param pageNo   起始页
     * @param pageSize 每页大小
     * @param odsId    odsId
     * @param userId   用户id
     */
    @GetMapping("/queryByPage")
    public ResultData queryByPage(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                  Long odsId,
                                  Long userId) {
        return exportDimensionService.queryByPage(pageNo, pageSize, odsId, userId);
    }

    /**
     * 根据odsId查询全部
     */
    @GetMapping("/queryAll")
    public ResultData queryAll(Long odsId) {
        return exportDimensionService.queryAll(odsId);
    }

    /**
     * 新增
     */
    @PostMapping("/insert")
    @SysLogPoint("新增规则")
    public ResultData insert(@Valid JellyExportDimension exportDimension) {
        return exportDimensionService.insert(exportDimension);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @SysLogPoint("修改规则")
    public ResultData update(@Valid JellyExportDimension exportDimension) {
        return exportDimensionService.update(exportDimension);
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    @SysLogPoint("删除规则")
    public ResultData delete(@PathVariable Long id) {
        return exportDimensionService.delete(id);
    }

    /**
     * 查询区域
     */
    @GetMapping("/queryArea")
    public ResultData queryArea() {
        return exportDimensionService.queryArea();
    }

}
