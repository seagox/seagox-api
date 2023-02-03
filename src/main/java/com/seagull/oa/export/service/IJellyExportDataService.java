package com.seagull.oa.export.service;

import com.seagull.oa.common.ResultData;
import com.seagull.oa.export.vo.ExportConfigListVo;
import com.seagull.oa.export.vo.ExportNameAnalysisVo;
import org.apache.poi.ss.usermodel.Workbook;

public interface IJellyExportDataService {

    /**
     * 读取excel的sheet
     *
     * @param filePath 文件地址
     * @param fileName 文件名
     */
    public ResultData readExcelSheets(String filePath, String fileName);

    /**
     * 读取excel数据
     *
     * @param companyId          公司id
     * @param userId             用户id
     * @param workbook           工作簿信息
     * @param exportNameAnalysis excel表名分析
     * @param exportConfigList   导入配置
     * @param fileName           文件名
     */
    public ResultData readExcelData(Long companyId,
                                    Long userId,
                                    Workbook workbook,
                                    ExportNameAnalysisVo exportNameAnalysis,
                                    ExportConfigListVo exportConfigList,
                                    String fileName);

    /**
     * 分页查询
     *
     * @param pageNo   起始页
     * @param pageSize 每页大小
     * @param userId   用户id
     */
    public ResultData queryByPage(Integer pageNo, Integer pageSize, Long userId);

    /**
     * 查询导入数据
     *
     * @param pageNo      起始页
     * @param pageSize    每页大小
     * @param dimensionId 维度id
     * @param isConfig    是否已匹配
     * @param area        区域
     * @param year        年份
     * @param month       月份
     */
    public ResultData queryExcelDataByPage(Integer pageNo,
                                           Integer pageSize,
                                           Long dimensionId,
                                           Boolean isConfig,
                                           String area,
                                           Integer year,
                                           Integer month);

    /**
     * 根据业务表id、年度查询表数据
     *
     * @param tableId 业务表id
     * @param area    区域
     * @param year    年份
     */
    public ResultData queryDataByTableId(Long tableId, String area, Integer year);

    /**
     * 匹配数据
     *
     * @param tableId 匹配表id
     * @param rowId   匹配行id
     * @param field   匹配字段
     * @param value   匹配值
     */
    public ResultData matchData(Long tableId, Long rowId, String field, String value);

    /**
     * 撤回匹配数据
     *
     * @param tableId 匹配表id
     * @param rowId   匹配行id
     * @param field   匹配字段
     */
    public ResultData cancelMatchData(Long tableId, Long rowId, String field);

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
    public ResultData autoMatchData(Long tableId,
                                    String fieldName,
                                    String fieldCode,
                                    Long dimSourceId,
                                    String area,
                                    Integer year,
                                    Integer month);

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
    public ResultData autoCancelMatchData(Long tableId,
                                          String fieldCode,
                                          Long dimSourceId,
                                          String area,
                                          Integer year,
                                          Integer month);

}
