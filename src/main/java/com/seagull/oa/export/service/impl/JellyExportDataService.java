package com.seagull.oa.export.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seagull.oa.common.ResultCode;
import com.seagull.oa.common.ResultData;
import com.seagull.oa.excel.entity.*;
import com.seagull.oa.excel.mapper.*;
import com.seagull.oa.export.entity.JellyExportData;
import com.seagull.oa.export.entity.JellyExportDimension;
import com.seagull.oa.export.entity.JellyExportRule;
import com.seagull.oa.export.mapper.JellyExportDataMapper;
import com.seagull.oa.export.mapper.JellyExportDimensionMapper;
import com.seagull.oa.export.mapper.JellyExportRuleDetailMapper;
import com.seagull.oa.export.mapper.JellyExportRuleMapper;
import com.seagull.oa.export.service.IJellyExportDataService;
import com.seagull.oa.export.vo.ExportConfigListVo;
import com.seagull.oa.export.vo.ExportConfigVo;
import com.seagull.oa.export.vo.ExportNameAnalysisVo;
import com.seagull.oa.groovy.GroovyFactory;
import com.seagull.oa.groovy.IGroovyRule;
import com.seagull.oa.sys.entity.SysDepartment;
import com.seagull.oa.sys.mapper.DepartmentMapper;
import com.seagull.oa.sys.service.IUserService;
import com.seagull.oa.util.ImportExcelUtil;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class JellyExportDataService implements IJellyExportDataService {

    @Autowired
    private JellyExportRuleMapper ruleMapper;

    @Autowired
    private JellyExportRuleDetailMapper ruleDetailMapper;

    @Autowired
    private JellyExportDataMapper dataMapper;

    @Autowired
    private JellyBusinessTableMapper businessTableMapper;

    @Autowired
    private JellyBusinessFieldMapper businessFieldMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JellyExportDimensionMapper dimensionMapper;

    @Autowired
    private JellyFormMapper formMapper;

    @Autowired
    private JellyDicDetailMapper dicDetailMapper;

    @Autowired
    private IUserService userService;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private JellyRegionsMapper regionsMapper;

    @Autowired
    private JellyBusinessRuleMapper businessRuleMapper;

    private List<String> getLetterList() {
        List<String> letterList = Arrays.asList(
                "A", "B", "C", "D", "E", "F", "G",
                "H", "I", "J", "K", "L", "M", "N",
                "O", "P", "Q", "R", "S", "T", "U",
                "V", "W", "X", "Y", "Z");
        List<String> arrList = new ArrayList<>(letterList);
        List<String> letters = Arrays.asList(
                "A", "B", "C", "D", "E", "F", "G",
                "H", "I", "J", "K", "L", "M", "N",
                "O", "P", "Q", "R", "S", "T", "U",
                "V", "W", "X", "Y", "Z");
        letters.forEach(letter1 -> letters.forEach(letter2 -> {
            arrList.add(letter1 + letter2);
        }));
        return arrList;
    }

    @Override
    public ResultData readExcelSheets(String filePath, String fileName) {
        try {
            List<String> sheetNameList = ImportExcelUtil.readExcelSheets(filePath, false);
            List<ExportConfigVo> list = new ArrayList<>();
            int index = 0;
            for (String name : sheetNameList) {
                ExportConfigVo configVo = new ExportConfigVo();
                configVo.setIndex(index);
                configVo.setUrl(filePath);
                configVo.setFileName(fileName);
                configVo.setSheetName(name);
                configVo.setbRead("1");
                configVo.setEndSwitch(false);
                list.add(configVo);
                index++;
            }
            return ResultData.success(list);
        } catch (Exception ex) {
            return ResultData.warn(ResultCode.OTHER_ERROR, ex.getMessage());
        }
    }


    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional
    @Override
    public ResultData readExcelData(Long companyId, Long userId, Workbook workbook, ExportNameAnalysisVo exportNameAnalysis, ExportConfigListVo exportConfigList, String fileName) {
        String year = null;
        String month = null;
        String rgCode = null;
        String rgName = null;
        String deptCode = null;
        String deptName = null;
        // 读取规则（1:无；2:预算单位；3:XXXX年XX月_区划编码_名称）
        Integer readRule = exportNameAnalysis.getReadRule();
        try {
            if (readRule == 1 && !StringUtils.isEmpty(exportNameAnalysis.getYear())) {
                year = exportNameAnalysis.getYear().toString();
            } else if (readRule == 2) {
                String[] values = this.analysisExcelName2(fileName, exportNameAnalysis);
                if (!StringUtils.isEmpty(exportNameAnalysis.getYear())) {
                    year = exportNameAnalysis.getYear().toString();
                }
                deptCode = values[0];
                deptName = values[1];
            } else if (readRule == 3) {
                String[] values = this.analysisExcelName3(fileName, exportNameAnalysis);
                year = values[0];
                month = values[1];
                rgCode = values[2];
                rgName = values[3];
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
        // 遍历sheet数据
        List<ExportConfigVo> configList = exportConfigList.getConfigList();
        for (ExportConfigVo config : configList) {
            // 判断是否读取sheet
            if ("2".equals(config.getbRead())) {
                continue;
            }
            JellyExportRule exportRule = ruleMapper.selectById(config.getRuleId());
            if (exportRule == null) {
                throw new NullPointerException(config.getSheetName() + "对应导入规则查询为空！");
            }
            String exportRuleCode = exportRule.getCode();
            StringBuilder insertSql = new StringBuilder();
            insertSql.append("insert into ");
            // 查询表名
            JellyBusinessTable businessTable = businessTableMapper.selectById(exportRule.getDataSource());
            if (businessTable == null) {
                throw new NullPointerException(config.getSheetName() + "对应数据表查询为空！");
            }
            String tableName = businessTable.getName();
            insertSql.append(tableName).append("( ");
            // 删除条件个数
            int mRequired = 0;
            LambdaQueryWrapper<JellyBusinessField> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(JellyBusinessField::getBusinessTableId, businessTable.getId());
            List<JellyBusinessField> fieldList = businessFieldMapper.selectList(queryWrapper);
            StringBuilder fixedValues = new StringBuilder();
            StringBuilder judgeExitWhereSql = new StringBuilder();
            judgeExitWhereSql.append(" 1 = 1");
            for (JellyBusinessField field : fieldList) {
                String fieldName = field.getName();
                // 固定字段
                if ("company_id".equals(fieldName)) {
                    insertSql.append(fieldName).append(",");
                    fixedValues.append(companyId).append(",");
                } else if ("user_id".equals(fieldName)) {
                    insertSql.append(fieldName).append(",");
                    fixedValues.append(userId).append(",");
                } else if ("depart_id".equals(fieldName)) {
                    insertSql.append("depart_id,");
                    fixedValues.append(config.getDepartId()).append(",");
                } else if ("yw_type".equals(fieldName)) {
                    // 业务类型(人大系统)
                    insertSql.append(fieldName).append(",");
                    fixedValues.append("'").append(exportRuleCode).append("',");
                    judgeExitWhereSql.append(" AND yw_type = '").append(exportRuleCode).append("'");
                }
                // 解析字段
                if (exportNameAnalysis.getYearField().equals(fieldName) && !StringUtils.isEmpty(year)) {
                    // 年份
                    insertSql.append(fieldName).append(",");
                    fixedValues.append(year).append(",");
                    judgeExitWhereSql.append(" AND ").append(exportNameAnalysis.getYearField()).append(" = '").append(year).append("'");
                    mRequired += 1;
                } else if (exportNameAnalysis.getMonthField().equals(fieldName) && !StringUtils.isEmpty(month)) {
                    // 月份
                    insertSql.append(fieldName).append(",");
                    fixedValues.append(month).append(",");
                    if (!StringUtils.isEmpty(month)) {
                        judgeExitWhereSql.append(" AND ").append(exportNameAnalysis.getMonthField()).append(" = '").append(month).append("'");
                    }
                    mRequired += 1;
                } else if (exportNameAnalysis.getRgCodeField().equals(fieldName) && !StringUtils.isEmpty(rgCode)) {
                    // 区域编码
                    insertSql.append(fieldName).append(",");
                    fixedValues.append("'").append(rgCode).append("',");
                    judgeExitWhereSql.append(" AND ").append(exportNameAnalysis.getRgCodeField()).append(" = '").append(rgCode).append("'");
                    mRequired += 1;
                } else if (exportNameAnalysis.getRgNameField().equals(fieldName) && !StringUtils.isEmpty(rgName)) {
                    // 区域名称
                    insertSql.append(fieldName).append(",");
                    fixedValues.append("'").append(rgName).append("',");
                    judgeExitWhereSql.append(" AND ").append(exportNameAnalysis.getRgNameField()).append(" = '").append(rgName).append("'");
                    mRequired += 1;
                } else if (exportNameAnalysis.getDeptCodeField().equals(fieldName) && !StringUtils.isEmpty(deptCode)) {
                    // 单位编码
                    insertSql.append(fieldName).append(",");
                    fixedValues.append("'").append(deptCode).append("',");
                    judgeExitWhereSql.append(" AND ").append(exportNameAnalysis.getDeptCodeField()).append(" = '").append(deptCode).append("'");
                    mRequired += 1;
                } else if (exportNameAnalysis.getDeptNameField().equals(fieldName) && !StringUtils.isEmpty(deptName)) {
                    // 单位名称
                    insertSql.append(fieldName).append(",");
                    fixedValues.append("'").append(deptName).append("',");
                    judgeExitWhereSql.append(" AND ").append(exportNameAnalysis.getDeptNameField()).append(" = '").append(deptName).append("'");
                    mRequired += 1;
                }
            }
            if (mRequired > 0) {
                // 删除已存在数据
                try {
                    String deleteSql = "DELETE FROM " + tableName + " WHERE " + judgeExitWhereSql.toString();
                    jdbcTemplate.update(deleteSql);
                } catch (Exception ex) {
                    throw new RuntimeException("删除旧数据失败！");
                }
            }
            // 字段及对应列
            List<Map<String, Object>> fieldLetterList = ruleDetailMapper.queryByRuleId(exportRule.getId());
            if (fieldLetterList.size() == 0) {
                throw new RuntimeException("导入失败，您未设置导入规则！");
            }
            Map<Object, Map<Object, Object>> fieldLetter = new HashMap<>();
            for (Map<String, Object> map : fieldLetterList) {
                insertSql.append(map.get("name")).append(",");
                // 查询需要转换的数值（1无;2字典;3用户;4部门;5唯一字段;6地址;7sql获取）
                this.queryDetail(map, fieldLetter, companyId);
            }
            insertSql.deleteCharAt(insertSql.length() - 1);
            insertSql.append(" ) values ");
            // Excel计算公式
            FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
            // 获取工作簿
            Sheet sheet = workbook.getSheet(config.getSheetName());
            // 获取有多少行
            int rowCount = sheet.getLastRowNum();
            if (rowCount == 0 || config.getStartLine() - 1 > rowCount) {
                // 空行或者起始行大于实际有数据的行，直接跳过
                continue;
            }
            //收集Excel数据
            List<Map<Object, Object>> excelData = new ArrayList<>();
            for (int rowNum = config.getStartLine() - 1; rowNum <= rowCount; rowNum++) {
                Row rowData = sheet.getRow(rowNum);
                if (rowData == null) {
                    continue;
                }
                // 获取当前行的列数（防止配置字段数量大于读取列数）
                int lastCellNum = rowData.getLastCellNum();
                int cellCount = Math.max(fieldLetterList.size(), lastCellNum);
                Map<Object, Object> currentRow = new LinkedHashMap<>();
                for (int col = 0; col < cellCount; col++) {
                    // 判断该列是否需要存储数据
                    List<String> letterList = this.getLetterList();
                    String letter = letterList.get(col);
                    Map<Object, Object> fieldDetail = fieldLetter.get(letter);
                    if (fieldDetail == null) {
                        continue;
                    }
                    Cell cellData = rowData.getCell(col);
                    String cellValue;
                    if (cellData != null) {
                        cellValue = ImportExcelUtil.getCellValue(cellData, formulaEvaluator, true);
                    } else {
                        cellValue = "";
                    }
                    if (cellValue == null || StringUtils.isEmpty(cellValue)) {
                        cellValue = null;
                    }
                    if (cellValue != null && !StringUtils.isEmpty(cellValue)) {
                        // 转换
                        cellValue = this.convertValue(fieldDetail, cellValue, tableName, letter, rowNum);
                    }
                    currentRow.put(letter, cellValue);
                }
                excelData.add(currentRow);
                // 判断是否结束行
                if (config.getEndSwitch() && config.getEndLine().equals(rowNum)) {
                    break;
                }
            }
            // 业务规则
            Map<String, Object> returnMap = this.verification(exportRule.getBusinessRuleId(), excelData, userId);
            List<LinkedHashMap> finalList = JSON.parseArray(JSON.toJSONString(returnMap.get("list"), SerializerFeature.WriteMapNullValue), LinkedHashMap.class);
            // 遍历组成sql
            for (LinkedHashMap map : finalList) {
                insertSql.append("(").append(fixedValues.toString());
                LinkedHashMap<Object, Object> linkedHashMap = (LinkedHashMap<Object, Object>) map;
                for (Map.Entry<Object, Object> entry : linkedHashMap.entrySet()) {
                    insertSql.append(entry.getValue()).append(",");
                }
                insertSql.deleteCharAt(insertSql.length() - 1);
                insertSql.append("),");
            }
            insertSql.deleteCharAt(insertSql.length() - 1);
            // 执行插入Excel数据
            try {
                jdbcTemplate.update(insertSql.toString());
            } catch (Exception ex) {
                throw new RuntimeException("【" + config.getFileName() + "】的【" + config.getSheetName() + "】配置错误：" + ex.getCause());
            }
        }
        // 保存导入记录
        String configJson = JSONObject.toJSONString(configList);
        JellyExportData exportData = new JellyExportData();
        exportData.setCompanyId(companyId);
        exportData.setUserId(userId);
        exportData.setConfig(configJson);
        exportData.setName(fileName);
        dataMapper.insert(exportData);
        return ResultData.success(null);

    }

    /**
     * 解析excel名称(预算单位)
     *
     * @param fileName           文件名称
     * @param exportNameAnalysis 配置规则
     */
    private String[] analysisExcelName2(String fileName, ExportNameAnalysisVo exportNameAnalysis) {
        // rgCode/rgName
        String[] values = new String[2];
        String[] nameSplit = fileName.split("\\.");
        String rgSql = "SELECT * FROM " + exportNameAnalysis.getDeptSource()
                + " WHERE name = '" + nameSplit[0] + "' AND year = '" + exportNameAnalysis.getYear() + "'";
        try {
            List<Map<String, Object>> mapList = formMapper.queryPublicList(rgSql);
            if (mapList.size() == 1) {
                Map<String, Object> map = mapList.get(0);
                values[0] = map.get("code").toString();
                values[1] = map.get("name").toString();
            } else if (mapList.size() > 1) {
                // 遍历拿到最长code
                String maxCode = "";
                String name = null;
                for (Map<String, Object> map : mapList) {
                    String currentCode = map.get("code").toString();
                    if (currentCode.length() > maxCode.length()) {
                        maxCode = currentCode;
                        name = map.get("name").toString();
                    }
                }
                values[0] = maxCode;
                values[1] = name;
            } else {
                throw new NullPointerException("预算单位【" + nameSplit[0] + "】找不到！");
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
        return values;
    }

    /**
     * 解析excel名称(XXXX年XX月_区划编码_名称)
     *
     * @param fileName           文件名称
     * @param exportNameAnalysis 配置规则
     */
    private String[] analysisExcelName3(String fileName, ExportNameAnalysisVo exportNameAnalysis) {
        // year/month/rgCode/rgName
        String[] values = new String[4];
        // 判断导入名称是否规范
        String[] nameSplit = fileName.split("_");
        if (nameSplit.length != 3) {
            throw new RuntimeException("文件名称格式错误！");
        }
        String date = nameSplit[0];
        if (date.length() == 7) {
            String monthStr = date.substring(5, date.length() - 1);
            if (Integer.parseInt(monthStr) < 1) {
                throw new RuntimeException("文件名称时间格式错误！");
            }
            values[1] = monthStr;
        } else if (date.length() == 8) {
            String monthStr = date.substring(5, date.length() - 1);
            int monthInt = Integer.parseInt(monthStr);
            if (monthInt > 12 || monthInt < 1) {
                throw new RuntimeException("文件名称时间格式错误！");
            }
            values[1] = String.valueOf(monthInt);
        } else if (date.length() != 5) {
            throw new RuntimeException("文件名称时间格式错误！");
        }
        values[0] = date.substring(0, 4);
        // 行政区域判断
        String rgSql = "SELECT * FROM " + exportNameAnalysis.getRgSource() + " WHERE " + " code = '" + nameSplit[1] + "'";
        List<Map<String, Object>> rgList;
        try {
            rgList = formMapper.queryPublicList(rgSql);
        } catch (Exception ex) {
            throw new RuntimeException("行政区划不存在！");
        }
        if (rgList.size() == 0) {
            throw new RuntimeException("区域编码不存在！");
        } else {
            values[2] = rgList.get(0).get("code").toString();
            values[3] = rgList.get(0).get("name").toString();
        }
        return values;
    }


    /**
     * 查询需要转化的列（1无;2字典;3用户;4部门;5唯一字段;6地址;7sql获取）
     *
     * @param map         字段详情
     * @param fieldLetter 字段转换详情
     * @param companyId   公司id
     */
    private void queryDetail(Map<String, Object> map, Map<Object, Map<Object, Object>> fieldLetter, Long companyId) {
        Object type = map.get("type");
        if (type == null) {
            return;
        }
        Map<Object, Object> fieldDetail = new HashMap<>();
        fieldDetail.put("name", map.get("name"));
        fieldDetail.put("type", type);
        fieldLetter.put(map.get("col"), fieldDetail);
        if ("2".equals(type.toString()) && (map.get("source") != null)) {
            // 字典
            LambdaQueryWrapper<JellyDicDetail> qw = new LambdaQueryWrapper<>();
            qw.eq(JellyDicDetail::getClassifyId, map.get("source"));
            fieldDetail.put("list", dicDetailMapper.selectList(qw));
        } else if ("3".equals(type.toString())) {
            // 用户
            fieldDetail.put("list", userService.queryAll(companyId));
        } else if ("4".equals(type.toString())) {
            // 部门
            LambdaQueryWrapper<SysDepartment> qw = new LambdaQueryWrapper<>();
            qw.eq(SysDepartment::getCompanyId, companyId);
            fieldDetail.put("list", departmentMapper.selectList(qw));
        } else if ("6".equals(type.toString())) {
            // 地址
            LambdaQueryWrapper<JellyRegions> qw = new LambdaQueryWrapper<>();
            fieldDetail.put("list", regionsMapper.selectList(qw));
        } else if ("7".equals(type.toString())) {
            // sql转换
            fieldDetail.put("list", jdbcTemplate.queryForList(map.get("sqlSource").toString()));
        }
    }

    /**
     * 转换具体值
     *
     * @param fieldDetail 字段详情
     * @param cellValue   表格数据
     * @param tableName   表名
     * @param letter      列名
     * @param rowNum      行数
     */
    @SuppressWarnings("rawtypes")
	private String convertValue(Map<Object, Object> fieldDetail, String cellValue, String tableName, String letter, int rowNum) {
        Object type = fieldDetail.get("type");
        if ("2".equals(type.toString())) {
            // 字典
            String oldValue = cellValue.substring(1, cellValue.length() - 1);
            List<JellyDicDetail> dicDetails = JSON.parseArray(JSON.toJSONString(fieldDetail.get("list")), JellyDicDetail.class);
            for (JellyDicDetail dicDetail : dicDetails) {
                if (dicDetail.getName().equals(oldValue)) {
                    cellValue = dicDetail.getCode();
                    break;
                }
            }
        } else if ("3".equals(type.toString()) || "4".equals(type.toString())) {
            // 用户、部门
            String oldValue = cellValue.substring(1, cellValue.length() - 1);
            List<Map> mapList = JSON.parseArray(JSON.toJSONString(fieldDetail.get("list")), Map.class);
            for (Map map : mapList) {
                if (map.get("name").equals(oldValue)) {
                    cellValue = map.get("id").toString();
                    break;
                }
            }
        } else if ("5".equals(type.toString())) {
            // 唯一
            // 判断数据库是否重复
            String onlyQuery = "SELECT id FROM " + tableName + " WHERE " + fieldDetail.get("name") + " = " + cellValue;
            List<Map<String, Object>> mapList = jdbcTemplate.queryForList(onlyQuery);
            if (mapList.size() > 0) {
                throw new RuntimeException("第" + letter + "列，第" + (rowNum + 1) + "行数据库已存在该数据！");
            }
            // 判断excel是否存在重复
            Object data = fieldDetail.get("data");
            if (data == null) {
                // 首次进来存入data
                List<String> list = new ArrayList<>();
                list.add(cellValue);
                fieldDetail.put("data", list);
            } else {
                List<String> list = JSON.parseArray(JSON.toJSONString(fieldDetail.get("data")), String.class);
                for (String s : list) {
                    if (s.equals(cellValue)) {
                        throw new RuntimeException("第" + letter + "列，第" + rowNum + "存在重复值！");
                    }
                }
                // 通过则加入
                list.add(cellValue);
                fieldDetail.put("data", list);
            }
        } else if ("6".equals(type.toString())) {
            // 地址
            List<Map> mapList = JSON.parseArray(JSON.toJSONString(fieldDetail.get("list")), Map.class);
            String oldValue = cellValue.substring(1, cellValue.length() - 1);
            List<String> codeList = new ArrayList<>();
            String[] split = oldValue.split("/");
            for (String s : split) {
                for (Map map : mapList) {
                    if (map.get("name").equals(s)) {
                        codeList.add(map.get("code").toString());
                        break;
                    }
                }
            }
            String[] strings = StringUtils.toStringArray(codeList);
            cellValue = "'" + JSONObject.toJSONString(strings) + "'";
        } else if ("7".equals(type.toString())) {
            // sql转换
            String oldValue = cellValue.substring(1, cellValue.length() - 1);
            List<Map> mapList = JSON.parseArray(JSON.toJSONString(fieldDetail.get("list")), Map.class);
            for (Map map : mapList) {
                if (map.get("key").equals(oldValue)) {
                    cellValue = map.get("value").toString();
                    break;
                }
            }
        }
        return cellValue;
    }

    /**
     * 业务规则校验
     *
     * @param businessRule 业务规则id
     * @param list         数据
     */
    private Map<String, Object> verification(String businessRule, List<Map<Object, Object>> list, Long userId) {
        // 导出规则
        if (businessRule != null) {
            JellyBusinessRule exportRule = businessRuleMapper.selectById(businessRule);
            try {
                Map<String, Object> params = new LinkedHashMap<>();
                params.put("list", list);
                params.put("userId", userId);
                IGroovyRule groovyRule = GroovyFactory.getInstance().getIRuleFromCode(exportRule.getScript());
                return groovyRule.businessRule(params, null);
            } catch (Exception e) {
                throw new RuntimeException("业务规则校验出错：" + e.getMessage());
            }
        }
        Map<String, Object> returnMap = new LinkedHashMap<>();
        returnMap.put("list", list);
        return returnMap;
    }

    @Override
    public ResultData queryByPage(Integer pageNo, Integer pageSize, Long userId) {
        PageHelper.startPage(pageNo, pageSize);
        LambdaQueryWrapper<JellyExportData> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(JellyExportData::getUserId, userId).orderByDesc(JellyExportData::getCreateTime);
        List<JellyExportData> jellyExportData = dataMapper.selectList(queryWrapper);
        PageInfo<JellyExportData> pageInfo = new PageInfo<>(jellyExportData);
        return ResultData.success(pageInfo);
    }

    @Override
    public ResultData queryExcelDataByPage(Integer pageNo, Integer pageSize, Long dimensionId, Boolean isConfig, String area, Integer year, Integer month) {
        if (year == null) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "请选择查询年份！");
        }
        JellyExportDimension dimension = dimensionMapper.selectById(dimensionId);
        if (dimension == null) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "当前维度已不存在！");
        }
        // 查询导入数据
        JellyBusinessTable businessTable = businessTableMapper.selectById(dimension.getOdmSource());
        if (businessTable == null) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "报表数据查询失败！");
        }
        // 查询需要配置的字段
        JellyBusinessField odmNameField = businessFieldMapper.selectById(dimension.getOdmNameField());
        if (odmNameField == null) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "名称字段查询为空！");
        }
        JellyBusinessField odmCodeField = businessFieldMapper.selectById(dimension.getOdmCodeField());
        if (odmCodeField == null) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "编码字段查询为空！");
        }
        StringBuilder sql = new StringBuilder();
        // 判断是否匹配
        String whereSql;
        if (isConfig) {
            whereSql = " AND " + odmCodeField.getName() + " IS NOT NULL AND " + odmCodeField.getName() + " != '' ";
        } else {
            whereSql = " AND (" + odmCodeField.getName() + " IS NULL OR " + odmCodeField.getName() + " = '' )";
        }
        sql.append("SELECT id, ").append(odmNameField.getName()).append(", ").append(odmCodeField.getName());
        sql.append(" FROM ").append(businessTable.getName());
        sql.append(" WHERE ").append(odmNameField.getName()).append(" IS NOT NULL AND ").append(odmNameField.getName()).append(" != '' ");
        sql.append(whereSql);
        //  查询表字段是否有date_year、rg_code、month
        LambdaQueryWrapper<JellyBusinessField> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(JellyBusinessField::getBusinessTableId, dimension.getOdmSource());
        List<JellyBusinessField> businessFields = businessFieldMapper.selectList(queryWrapper);
        boolean isYear = businessFields.stream().anyMatch(field -> field.getName().equals("date_year"));
        boolean isArea = businessFields.stream().anyMatch(field -> field.getName().equals("rg_code"));
        boolean isMonth = businessFields.stream().anyMatch(field -> field.getName().equals("month"));
        // 年份为必选
        if (!isYear) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "查询对象表中未找到年份date_year字段！");
        }
        sql.append(" AND date_year = '").append(year).append("'");
        if (isArea && !StringUtils.isEmpty(area)) {
            sql.append(" AND rg_code = '").append(area).append("'");
        }
        if (isMonth && month != null) {
            sql.append(" AND month = '").append(month).append("'");
        }
        sql.append(" ORDER BY id ASC ");
        try {
            // 查询数据
            PageHelper.startPage(pageNo, pageSize);
            List<Map<String, Object>> list = formMapper.queryPublicList(sql.toString());
            PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
            return ResultData.success(pageInfo);
        } catch (Exception ex) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "数据查询失败！");
        }
    }

    @Override
    public ResultData queryDataByTableId(Long tableId, String area, Integer year) {
        JellyBusinessTable businessTable = businessTableMapper.selectById(tableId);
        if (businessTable == null) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "查询业务表为空！");
        }
        //  查询表字段是否有year、rg_code
        LambdaQueryWrapper<JellyBusinessField> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(JellyBusinessField::getBusinessTableId, tableId);
        List<JellyBusinessField> businessFields = businessFieldMapper.selectList(queryWrapper);
        boolean isYear = businessFields.stream().anyMatch(field -> field.getName().equals("year"));
        boolean isArea = businessFields.stream().anyMatch(field -> field.getName().equals("rg_code"));
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM ");
        sql.append(businessTable.getName());
        sql.append(" WHERE 1=1  ");
        if (isYear && !StringUtils.isEmpty(year)) {
            sql.append(" AND year = '").append(year).append("'");
        }
        if (isArea && !StringUtils.isEmpty(area)) {
            sql.append(" AND rg_code = '").append(area).append("'");
        }
        try {
            List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql.toString());
            return ResultData.success(mapList);
        } catch (Exception ex) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "维度表数据查询失败！");
        }
    }

    @Override
    public ResultData matchData(Long tableId, Long rowId, String field, String value) {
        // 查询表名
        JellyBusinessTable businessTable = businessTableMapper.selectById(tableId);
        if (businessTable == null) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "匹配失败，对象表查询失败！");
        }
        StringBuilder updateSql = new StringBuilder();
        updateSql.append("UPDATE ").append(businessTable.getName());
        updateSql.append(" SET ").append(field).append(" = '").append(value).append("'");
        updateSql.append(" WHERE id = ").append(rowId);
        try {
            jdbcTemplate.update(updateSql.toString());
            return ResultData.success(null);
        } catch (Exception e) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "匹配失败！");
        }
    }

    @Override
    public ResultData cancelMatchData(Long tableId, Long rowId, String field) {
        // 查询表名
        JellyBusinessTable businessTable = businessTableMapper.selectById(tableId);
        if (businessTable == null) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "撤销配置失败，对象表查询失败！");
        }
        StringBuilder updateSql = new StringBuilder();
        updateSql.append("UPDATE ").append(businessTable.getName());
        updateSql.append(" SET ").append(field).append(" = ''");
        updateSql.append(" WHERE id = ").append(rowId);
        try {
            jdbcTemplate.update(updateSql.toString());
            return ResultData.success(null);
        } catch (Exception e) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "撤销配置失败！");
        }
    }

    @Override
    public ResultData autoMatchData(Long tableId, String fieldName, String fieldCode, Long dimSourceId, String area, Integer year, Integer month) {
        if (year == null) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "请选择年份！");
        }
        // 查询匹配表
        JellyBusinessTable businessTable = businessTableMapper.selectById(tableId);
        if (businessTable == null) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "匹配失败，对象表查询失败！");
        }
        // 查询表字段是否有rg_code、year、month
        LambdaQueryWrapper<JellyBusinessField> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(JellyBusinessField::getBusinessTableId, tableId);
        List<JellyBusinessField> businessFields = businessFieldMapper.selectList(queryWrapper);
        boolean isYear = businessFields.stream().anyMatch(field -> field.getName().equals("year"));
        boolean isArea = businessFields.stream().anyMatch(field -> field.getName().equals("rg_code"));
        boolean isMonth = businessFields.stream().anyMatch(field -> field.getName().equals("month"));
        // 年份为必选
        if (!isYear) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "对象表中未找到年份date_year字段！");
        }
        // 查询维度表
        JellyBusinessTable dimSourceTable = businessTableMapper.selectById(dimSourceId);
        if (dimSourceId == null) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "匹配失败，维度表失败！");
        }
        // 查询维度表是否含有rg_code、year
        LambdaQueryWrapper<JellyBusinessField> dimQueryWrapper = new LambdaQueryWrapper<>();
        dimQueryWrapper.eq(JellyBusinessField::getBusinessTableId, dimSourceId);
        List<JellyBusinessField> dimFields = businessFieldMapper.selectList(dimQueryWrapper);
        boolean isDimYear = dimFields.stream().anyMatch(field -> field.getName().equals("year"));
        boolean isDimArea = dimFields.stream().anyMatch(field -> field.getName().equals("rg_code"));
        String tableName = businessTable.getName();
        String dimTableName = dimSourceTable.getName();
        StringBuilder updateSql = new StringBuilder();
        updateSql.append(" UPDATE ").append(tableName).append(" a ");
        updateSql.append(" SET ").append(fieldCode).append(" = ").append(" b.code ");
        updateSql.append(" FROM ").append(dimTableName).append(" b ");
        updateSql.append(" WHERE ");
        updateSql.append(" TRIM(BOTH ' ' FROM a.").append(fieldName).append(") = b.name ");
        updateSql.append(" AND a.id IN (");
        updateSql.append(" SELECT a.id FROM ").append(tableName).append(" a ");
        updateSql.append(" INNER JOIN ").append(dimTableName).append(" b ON TRIM(BOTH ' ' FROM a.").append(fieldName).append(") = b.name ");
        updateSql.append(" WHERE (a.").append(fieldCode).append(" IS NULL OR a.").append(fieldCode).append(" = '')");
        updateSql.append(" AND b.code IS NOT NULL ");
        updateSql.append(" AND a.date_year = '").append(year).append("' ");
        if (isDimYear) {
            updateSql.append(" AND b.year = '").append(year).append("' ");
        }
        if (!StringUtils.isEmpty(area)) {
            if (isArea) {
                updateSql.append(" AND a.rg_code = '").append(area).append("' ");
            }
            if (isDimArea) {
                updateSql.append(" AND b.rg_code = '").append(area).append("' ");
            }
        }
        if (isMonth && !StringUtils.isEmpty(month)) {
            updateSql.append(" AND a.month = '").append(month).append("' ");
        }
        updateSql.append(" GROUP BY a.id HAVING COUNT(a.id) = 1");
        updateSql.append(")");
        try {
            jdbcTemplate.update(updateSql.toString());
            return ResultData.success(null);
        } catch (Exception e) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "一键匹配失败！");
        }
    }

    @Override
    public ResultData autoCancelMatchData(Long tableId, String fieldCode, Long dimSourceId, String area, Integer year, Integer month) {
        if (year == null) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "请选择年份！");
        }
        // 查询匹配表
        JellyBusinessTable businessTable = businessTableMapper.selectById(tableId);
        if (businessTable == null) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "一键撤销失败，对象表查询失败！");
        }
        //  查询表字段是否有date_year、rg_code、month
        LambdaQueryWrapper<JellyBusinessField> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(JellyBusinessField::getBusinessTableId, tableId);
        List<JellyBusinessField> businessFields = businessFieldMapper.selectList(queryWrapper);
        boolean isYear = businessFields.stream().anyMatch(field -> field.getName().equals("date_year"));
        boolean isArea = businessFields.stream().anyMatch(field -> field.getName().equals("rg_code"));
        boolean isMonth = businessFields.stream().anyMatch(field -> field.getName().equals("month"));
        StringBuilder updateSql = new StringBuilder();
        updateSql.append("UPDATE ").append(businessTable.getName());
        updateSql.append(" SET ").append(fieldCode).append(" = ''");
        updateSql.append(" WHERE ").append(fieldCode).append(" IS NOT NULL");
        // 年份为必选
        if (!isYear) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "查询对象表中未找到年份date_year字段！");
        }
        updateSql.append(" AND date_year = '").append(year).append("'");
        if (isArea && !StringUtils.isEmpty(area)) {
            updateSql.append(" AND rg_code = '").append(area).append("'");
        }
        if (isMonth && month != null) {
            updateSql.append(" AND month = '").append(month).append("'");
        }
        try {
            jdbcTemplate.update(updateSql.toString());
            return ResultData.success(null);
        } catch (Exception e) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "一键撤销失败！");
        }
    }

}
