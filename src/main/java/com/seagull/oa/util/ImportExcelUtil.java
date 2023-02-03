package com.seagull.oa.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.FileMagic;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * excel导入工具类
 */
public class ImportExcelUtil {

    private static NumberFormat numberFormat = NumberFormat.getNumberInstance();

    static {
        numberFormat.setGroupingUsed(false);
    }

    /**
     * 根据文件路径读取Workbook
     *
     * @param filePath 文件路径
     * @return Workbook
     * @throws IOException 异常
     */
    public static Workbook readWorkbook(String filePath) throws IOException {
        InputStream fileInputStream = new URL(filePath).openStream();
        InputStream inputStream = FileMagic.prepareToCheckMagic(fileInputStream);
        FileMagic fileMagic = FileMagic.valueOf(inputStream);
        Workbook workbook;
        if (fileMagic.name().equals("OLE2")) {
            // 2003版（.xls）
            workbook = new HSSFWorkbook(inputStream);
        } else if (fileMagic.name().equals("OOXML")) {
            // 2007版（.xlsx）
            workbook = new XSSFWorkbook(inputStream);
        } else {
            throw new NullPointerException("文件格式错误！");
        }
        inputStream.close();
        fileInputStream.close();
        return workbook;
    }

    /**
     * 根据文件路径读取Excel所有sheet名称
     *
     * @param filePath  文件路径
     * @param returnAll 是否返回所有包括空sheet（默认true）
     * @return 所有sheet名称
     * @throws IOException 异常
     */
    public static List<String> readExcelSheets(String filePath, Boolean returnAll) throws IOException {
        Workbook workbook = ImportExcelUtil.readWorkbook(filePath);
        List<String> sheetNameList = new ArrayList<>();
        int sheetNum = workbook.getNumberOfSheets();
        for (int i = 0; i < sheetNum; i++) {
            Sheet sheet = workbook.getSheetAt(i);
            if (returnAll) {
                sheetNameList.add(sheet.getSheetName());
            } else {
                // 判断是否为空sheet
                if (sheet.getLastRowNum() > 0) {
                    sheetNameList.add(sheet.getSheetName());
                }
            }
        }
        workbook.close();
        return sheetNameList;
    }

    /**
     * 获取单元格数据
     *
     * @param cellData         单元格对象
     * @param formulaEvaluator 计算公式
     * @return 单元格数据
     */
    @SuppressWarnings("deprecation")
    public static String getCellValue(Cell cellData, FormulaEvaluator formulaEvaluator, Boolean flag) {
        String cellValue = null;
        if (cellData == null) {
            return null;
        }
        switch (cellData.getCellType()) {
            case STRING://字符串
                String value = cellData.getStringCellValue();
                if (value.trim().length() == 0) {
                    // 判断字符串是否都为空格
                    return null;
                }
                if (flag) {
                    cellValue = "'" + cellData.getStringCellValue() + "'";
                } else {
                    cellValue = cellData.getStringCellValue();
                }
                break;
            case BOOLEAN://布尔
                cellValue = String.valueOf(cellData.getBooleanCellValue());
                break;
            case BLANK://空
                break;
            case NUMERIC://数字（日期、普通数字）
                if (DateUtil.isCellDateFormatted(cellData)) {
                    //如果是日期
                    Date date = cellData.getDateCellValue();
                    if (flag) {
                        cellValue = "'" + new DateTime(date).toString("yyyy-MM-dd") + "'";
                    } else {
                        cellValue = new DateTime(date).toString("yyyy-MM-dd");
                    }
                } else {
                    //转为字符串
                    cellData.setCellType(CellType.STRING);
                    cellValue = cellData.toString();
                }
                break;
            case ERROR://错误
                break;
            case FORMULA://计算公式
                CellType formulaResultType = cellData.getCachedFormulaResultType();
                switch (formulaResultType) {
                    case NUMERIC:
//                        CellValue evaluate = formulaEvaluator.evaluate(cellData);
//                        cellValue = evaluate.formatAsString();
                        // 修复出现精度问题
                        double numericCellValue = cellData.getNumericCellValue();
                        cellValue = numberFormat.format(numericCellValue);
                        break;
                    case STRING:
                        cellValue = cellData.getStringCellValue();
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
        return cellValue;
    }

}
