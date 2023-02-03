package com.seagull.oa.export.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.seagull.oa.common.ResultCode;
import com.seagull.oa.common.ResultData;
import com.seagull.oa.export.service.IJellyExportDataService;
import com.seagull.oa.export.vo.ExportConfigListVo;
import com.seagull.oa.export.vo.ExportNameAnalysisVo;
import com.seagull.oa.util.ImportExcelUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 导入数据
 */
@RestController
@RequestMapping("/exportData")
public class JellyExportDataController {

    @Autowired
    private IJellyExportDataService exportDataService;

    /**
     * 读取excel的sheet
     *
     * @param filePath 文件地址
     * @param fileName 文件名
     */
    @GetMapping("/readExcelSheets")
    public ResultData readExcelSheets(String filePath, String fileName) {
        return exportDataService.readExcelSheets(filePath, fileName);
    }

    /**
     * 批量读取excel数据
     *
     * @param companyId          公司id
     * @param userId             用户id
     * @param exportNameAnalysis excel表名分析
     * @param configList         配置集合
     */
    @PostMapping("/readExcelData")
    public ResultData readExcelData(@Param("companyId") Long companyId,
                                    @Param("userId") Long userId,
                                    String exportNameAnalysis,
                                    String configList) {
        try {
            ExportNameAnalysisVo exportNameAnalysisVo = JSONObject.parseObject(exportNameAnalysis, ExportNameAnalysisVo.class);
            List<ExportConfigListVo> configVoList = JSONArray.parseArray(configList, ExportConfigListVo.class);
            if (exportNameAnalysisVo.getUnified() == 2) {
                // 统一处理（所有sheet配置取第一个）
                ExportConfigListVo allConfig = configVoList.get(0);
                for (ExportConfigListVo configListVo : configVoList) {
                    Workbook workbook = ImportExcelUtil.readWorkbook(configListVo.getUrl());
                    exportDataService.readExcelData(companyId, userId, workbook, exportNameAnalysisVo, allConfig, configListVo.getFileName());
                    workbook.close();
                }
            } else {
                // 单一处理
                for (ExportConfigListVo configListVo : configVoList) {
                    Workbook workbook = ImportExcelUtil.readWorkbook(configListVo.getUrl());
                    exportDataService.readExcelData(companyId, userId, workbook, exportNameAnalysisVo, configListVo, configListVo.getFileName());
                    workbook.close();
                }
            }
            return ResultData.success(null);
        } catch (Exception ex) {
            return ResultData.warn(ResultCode.OTHER_ERROR, ex.getMessage());
        }
    }

    /**
     * 分页查询
     *
     * @param pageNo   起始页
     * @param pageSize 每页大小
     * @param userId   用户id
     */
    @GetMapping("/queryByPage")
    public ResultData queryByPage(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                  Long userId) {
        return exportDataService.queryByPage(pageNo, pageSize, userId);
    }

    /**
     * 查询导入数据
     *
     * @param pageNo      起始页
     * @param pageSize    每页大小
     * @param isConfig    是否已匹配
     * @param dimensionId 维度id
     * @param area        区域
     * @param year        年份
     * @param month       月份
     */
    @GetMapping("/queryExcelDataByPage")
    public ResultData queryExcelDataByPage(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                           @RequestParam(value = "pageSize", defaultValue = "15") Integer pageSize,
                                           @RequestParam(value = "isConfig", defaultValue = "false") Boolean isConfig,
                                           Long dimensionId,
                                           String area,
                                           Integer year,
                                           Integer month) {
        return exportDataService.queryExcelDataByPage(pageNo, pageSize, dimensionId, isConfig, area, year, month);
    }

    /**
     * 根据业务表id、年度查询表数据
     *
     * @param tableId 业务表id
     * @param area    区域
     * @param year    年份
     */
    @GetMapping("/queryDataByTableId")
    public ResultData queryDataByTableId(Long tableId, String area, Integer year) {
        return exportDataService.queryDataByTableId(tableId, area, year);
    }

    /**
     * 匹配数据
     *
     * @param tableId 匹配表id
     * @param rowId   匹配行id
     * @param field   匹配字段
     * @param value   匹配值
     */
    @GetMapping("/matchData")
    public ResultData matchData(Long tableId, Long rowId, String field, String value) {
        return exportDataService.matchData(tableId, rowId, field, value);
    }

    /**
     * 匹配数据
     *
     * @param tableId 匹配表id
     * @param rowId   匹配行id
     * @param field   匹配字段
     */
    @GetMapping("/cancelMatchData")
    public ResultData cancelMatchData(Long tableId, Long rowId, String field) {
        return exportDataService.cancelMatchData(tableId, rowId, field);
    }

    /**
     * 自动匹配（100%相似度）
     *
     * @param tableId     匹配表id
     * @param fieldName   匹配字段
     * @param fieldCode   匹配编码字段
     * @param dimSourceId 维度id
     * @param area        区域
     * @param year        年份
     * @param month       月份
     */
    @GetMapping("/autoMatchData")
    public ResultData autoMatchData(Long tableId,
                                    String fieldName,
                                    String fieldCode,
                                    Long dimSourceId,
                                    String area,
                                    Integer year,
                                    Integer month) {
        return exportDataService.autoMatchData(tableId, fieldName, fieldCode, dimSourceId, area, year, month);
    }

    /**
     * 一键撤销
     *
     * @param tableId     匹配表id
     * @param fieldCode   匹配字段编码
     * @param dimSourceId 维度id
     * @param area        区域
     * @param year        年份
     * @param month       月份
     */
    @GetMapping("/autoCancelMatchData")
    public ResultData autoCancelMatchData(Long tableId,
                                          String fieldCode,
                                          Long dimSourceId,
                                          String area,
                                          Integer year,
                                          Integer month) {
        return exportDataService.autoCancelMatchData(tableId, fieldCode, dimSourceId, area, year, month);
    }
}
