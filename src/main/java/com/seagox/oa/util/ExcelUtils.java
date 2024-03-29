package com.seagox.oa.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.FileMagic;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.seagox.oa.exception.FormulaException;
import com.seagox.oa.groovy.GroovyFactory;
import com.seagox.oa.groovy.IGroovyImportVerifyRule;

public class ExcelUtils {

	private static final List<String> letterList = Arrays.asList(
	        "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", 
	        "N", "O", "P", "Q", "R", "S", "T", "U","V", "W", "X", "Y", "Z",
	        "AA", "AB", "AC", "AD", "AE", "AF", "AG", "AH", "AI", "AJ", "AK", "AL", "AM", 
	        "AN", "AO", "AP", "AQ", "AR", "AS", "AT", "AU", "AV", "AW", "AX", "AY", "AZ",
	        "BA", "BB", "BC", "BD", "BE", "BF", "BG", "BH", "BI", "BJ", "BK", "BL", "BM", 
	        "BN", "BO", "BP", "BQ", "BR", "BS", "BT", "BU", "BV", "BW", "BX", "BY", "BZ",
	        "CA", "CB", "CC", "CD", "CE", "CF", "CG", "CH", "CI", "CJ", "CK", "CL", "CM", 
	        "CN", "CO", "CP", "CQ", "CR", "CS", "CT", "CU", "CV", "CW", "CX", "CY", "CZ",
	        "DA", "DB", "DC", "DD", "DE", "DF", "DG", "DH", "DI", "DJ", "DK", "DL", "DM", 
	        "DN", "DO", "DP", "DQ", "DR", "DS", "DT", "DU", "DV", "DW", "DX", "DY", "DZ"
	    );

    public static JSONObject analysisFirstSheet(String filePath) {
        JSONObject result = new JSONObject();
        FileInputStream inp = null;
        InputStream is = null;
        try {
            inp = new FileInputStream(filePath);
            is = FileMagic.prepareToCheckMagic(inp);
            FileMagic fm = FileMagic.valueOf(is);
            System.out.println(fm.name());
            if (fm.name().equals("OLE2")) {
                // 2003
                HSSFWorkbook workbook = new HSSFWorkbook(is);
                result = readHSSF(workbook, workbook.getSheetAt(0));
                workbook.close();
            } else if (fm.name().equals("OOXML")) {
                // 2007及2007以上
                XSSFWorkbook workbook = new XSSFWorkbook(is);
                result = readXSSF(workbook.getSheetAt(0));
                workbook.close();
            } else {
                throw new IOException("Your InputStream was neither an OLE2 stream, nor an OOXML stream");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (inp != null) {
                    inp.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static JSONObject analysis(String filePath) {
        JSONObject result = new JSONObject();
        FileInputStream inp = null;
        InputStream is = null;
        try {
            inp = new FileInputStream(filePath);
            is = FileMagic.prepareToCheckMagic(inp);
            FileMagic fm = FileMagic.valueOf(is);
            if (fm.name().equals("OLE2")) {
                // 2003
                HSSFWorkbook workbook = new HSSFWorkbook(is);
                for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                    HSSFSheet sheet = workbook.getSheetAt(i);
                    result.put(sheet.getSheetName(), readHSSF(workbook, sheet));
                }
                workbook.close();
            } else if (fm.name().equals("OOXML")) {
                // 2007及2007以上
                XSSFWorkbook workbook = new XSSFWorkbook(is);
                for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                    XSSFSheet sheet = workbook.getSheetAt(i);
                    result.put(sheet.getSheetName(), readXSSF(sheet));
                }
                workbook.close();
            } else {
                throw new IOException("Your InputStream was neither an OLE2 stream, nor an OOXML stream");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (inp != null) {
                    inp.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 解析数据
     *
     * @param sheet 表格sheet对象
     * @return
     * @throws IOException
     */
    public static JSONObject readHSSF(HSSFWorkbook workbook, HSSFSheet sheet) throws IOException {
        JSONArray mergedRegions = getHSSFMergedRegions(sheet);
        //结果json
        JSONObject resultJson = new JSONObject();
        //获取宽带
        JSONArray widthJson = new JSONArray();
        // 获取每行JSON对象的值
        JSONObject rowJson = new JSONObject();
        for (int i = 0; i <= sheet.getLastRowNum() + 5; i++) {
            HSSFRow eachRow = sheet.getRow(i);
            JSONObject cellJson = new JSONObject();
            if (eachRow != null) {
                float height = (eachRow.getHeightInPoints() / 72) * 96;
                for (int j = 0; j < 26; j++) {
                    JSONObject map = new JSONObject();
                    boolean isMergeFlag = false;
                    for (int k = 0; k < mergedRegions.size(); k++) {
                        JSONObject mergeItem = mergedRegions.getJSONObject(k);
                        int firstRow = mergeItem.getIntValue("firstRow");
                        int lastRow = mergeItem.getIntValue("lastRow");
                        int firstColumn = mergeItem.getIntValue("firstColumn");
                        int lastColumn = mergeItem.getIntValue("lastColumn");
                        if (i == firstRow && j == firstColumn) {
                            //合并单元格第一个
                            map.put("rowspan", lastRow - firstRow + 1);
                            map.put("colspan", lastColumn - firstColumn + 1);
                            break;
                        } else if (i >= firstRow && i <= lastRow && j >= firstColumn && j <= lastColumn) {
                            isMergeFlag = true;
                            break;
                        }
                    }
                    if (i == 0) {
                        widthJson.add(sheet.getColumnWidthInPixels(j));
                    }
                    if (!isMergeFlag) {
                        HSSFCell cell = eachRow.getCell(j);
                        if (cell != null) {
                            JSONObject cellStyle = getHSSFCellStyle(workbook, cell.getCellStyle());
                            cellStyle.put("width", sheet.getColumnWidthInPixels(j) + "px");
                            cellStyle.put("height", height + "px");
                            map.put("style", cellStyle);
                            String value = getCellValue(cell);//获取单元格数据
                            map.put("value", value);
                        } else {
                            JSONObject cellStyle = new JSONObject();
                            cellStyle.put("width", sheet.getColumnWidthInPixels(j) + "px");
                            cellStyle.put("height", height + "px");
                            map.put("style", cellStyle);
                        }
                        cellJson.put(letterList.get(j), map);
                    }
                }
            } else {
                for (int j = 0; j < 26; j++) {
                    JSONObject map = new JSONObject();
                    JSONObject cellStyle = new JSONObject();
                    cellStyle.put("width", sheet.getColumnWidthInPixels(j) + "px");
                    cellStyle.put("height", "36px");
                    map.put("style", cellStyle);
                    cellJson.put(letterList.get(j), map);
                }
            }
            rowJson.put(String.valueOf(i), cellJson);
        }
        resultJson.put("widthJson", widthJson);
        resultJson.put("rowJson", rowJson);
        return resultJson;
    }

    /**
     * 解析数据
     *
     * @param sheet 表格sheet对象
     * @return
     * @throws IOException
     */
    public static JSONObject readXSSF(XSSFSheet sheet) throws IOException {
        JSONArray mergedRegions = getXSSFMergedRegions(sheet);
        //结果json
        JSONObject resultJson = new JSONObject();
        //获取宽带
        JSONArray widthJson = new JSONArray();
        // 获取每行JSON对象的值
        JSONObject rowJson = new JSONObject();
        for (int i = 0; i <= sheet.getLastRowNum() + 5; i++) {
            XSSFRow eachRow = sheet.getRow(i);
            JSONObject cellJson = new JSONObject();
            if (eachRow != null) {
                float height = (eachRow.getHeightInPoints() / 72) * 96;
                for (int j = 0; j < 26; j++) {
                    JSONObject map = new JSONObject();
                    boolean isMergeFlag = false;
                    for (int k = 0; k < mergedRegions.size(); k++) {
                        JSONObject mergeItem = mergedRegions.getJSONObject(k);
                        int firstRow = mergeItem.getIntValue("firstRow");
                        int lastRow = mergeItem.getIntValue("lastRow");
                        int firstColumn = mergeItem.getIntValue("firstColumn");
                        int lastColumn = mergeItem.getIntValue("lastColumn");
                        if (i == firstRow && j == firstColumn) {
                            //合并单元格第一个
                            map.put("rowspan", lastRow - firstRow + 1);
                            map.put("colspan", lastColumn - firstColumn + 1);
                            break;
                        } else if (i >= firstRow && i <= lastRow && j >= firstColumn && j <= lastColumn) {
                            isMergeFlag = true;
                            break;
                        }
                    }
                    if (i == 0) {
                        widthJson.add(sheet.getColumnWidthInPixels(j));
                    }
                    if (!isMergeFlag) {
                        XSSFCell cell = eachRow.getCell(j);
                        if (cell != null) {
                            JSONObject cellStyle = getXSSFCellStyle(cell.getCellStyle());
                            cellStyle.put("width", sheet.getColumnWidthInPixels(j) + "px");
                            cellStyle.put("height", height + "px");
                            map.put("style", cellStyle);
                            String value = getCellValue(cell);//获取单元格数据
                            map.put("value", value);
                        } else {
                            JSONObject cellStyle = new JSONObject();
                            cellStyle.put("width", sheet.getColumnWidthInPixels(j) + "px");
                            cellStyle.put("height", height + "px");
                            map.put("style", cellStyle);
                        }
                        cellJson.put(letterList.get(j), map);
                    }
                }
            } else {
                for (int j = 0; j < 26; j++) {
                    JSONObject map = new JSONObject();
                    JSONObject cellStyle = new JSONObject();
                    cellStyle.put("width", sheet.getColumnWidthInPixels(j) + "px");
                    cellStyle.put("height", "36px");
                    map.put("style", cellStyle);
                    cellJson.put(letterList.get(j), map);
                }
            }
            rowJson.put(String.valueOf(i), cellJson);
        }
        resultJson.put("widthJson", widthJson);
        resultJson.put("rowJson", rowJson);
        return resultJson;
    }

    /**
     * 获得单元格的样式
     */
    public static JSONObject getXSSFCellStyle(XSSFCellStyle cellStyle) {
        JSONObject result = new JSONObject();
        if (cellStyle != null) {
            result.put("text-align", cellStyle.getAlignment().toString().toLowerCase());
            result.put("vertical-align", cellStyle.getVerticalAlignment().toString().toLowerCase());

            XSSFColor backgroundColor = cellStyle.getFillForegroundXSSFColor();
            if (backgroundColor != null) {
                String rgbColor = backgroundColor.getARGBHex();
                if (!StringUtils.isEmpty(rgbColor)) {
                    result.put("background-color", getColor(rgbColor));
                }
            }

            String borderTopStyle = cellStyle.getBorderTop().toString().toLowerCase();
            if (borderTopStyle.equals("none")) {
                result.put("border-top-style", "solid");
            } else {
                result.put("border-top-style", borderTopStyle);
                result.put("border-top-color", "#000");
            }

            String borderBottomStyle = cellStyle.getBorderBottom().toString().toLowerCase();
            if (borderBottomStyle.equals("none")) {
                result.put("border-bottom-style", "solid");
            } else {
                result.put("border-bottom-style", borderBottomStyle);
                result.put("border-bottom-color", "#000");
            }

            String borderLeftStyle = cellStyle.getBorderLeft().toString().toLowerCase();
            if (borderLeftStyle.equals("none")) {
                result.put("border-left-style", "solid");
            } else {
                result.put("border-left-style", borderLeftStyle);
                result.put("border-left-color", "#000");
            }

            String borderRightStyle = cellStyle.getBorderRight().toString().toLowerCase();
            if (borderRightStyle.equals("none")) {
                result.put("border-right-style", "solid");
            } else {
                result.put("border-right-style", borderRightStyle);
                result.put("border-right-color", "#000");
            }

            XSSFColor topBorderXSSFColor = cellStyle.getTopBorderXSSFColor();
            if (topBorderXSSFColor != null) {
                String rgbColor = topBorderXSSFColor.getARGBHex();
                if (!StringUtils.isEmpty(rgbColor)) {
                    result.put("border-top-color", getColor(rgbColor));
                }
            }
            XSSFColor bottomBorderXSSFColor = cellStyle.getBottomBorderXSSFColor();
            if (bottomBorderXSSFColor != null) {
                String rgbColor = bottomBorderXSSFColor.getARGBHex();
                if (!StringUtils.isEmpty(rgbColor)) {
                    result.put("border-bottom-color", getColor(rgbColor));
                }
            }
            XSSFColor leftBorderXSSFColor = cellStyle.getLeftBorderXSSFColor();
            if (leftBorderXSSFColor != null) {
                String rgbColor = leftBorderXSSFColor.getARGBHex();
                if (!StringUtils.isEmpty(rgbColor)) {
                    result.put("border-left-color", getColor(rgbColor));
                }
            }
            XSSFColor rightBorderXSSFColor = cellStyle.getRightBorderXSSFColor();
            if (rightBorderXSSFColor != null) {
                String rgbColor = rightBorderXSSFColor.getARGBHex();
                if (!StringUtils.isEmpty(rgbColor)) {
                    result.put("border-right-color", getColor(rgbColor));
                }
            }

            XSSFFont font = cellStyle.getFont();
            if (font.getBold()) {
                result.put("font-weight", "bold");
            } else {
                result.put("font-weight", "normal");
            }
            XSSFColor fontColor = font.getXSSFColor();
            if (fontColor != null) {
                String rgbColor = fontColor.getARGBHex();
                if (!StringUtils.isEmpty(rgbColor)) {
                    result.put("color", getColor(rgbColor));
                }
            }
            result.put("font-family", font.getFontName());
            if (font.getItalic()) {
                result.put("font-style", "italic");
            } else {
                result.put("font-style", "normal");
            }
            result.put("font-size", font.getFontHeightInPoints() + "px");
            if (font.getStrikeout()) {
                result.put("text-decoration", "line-through");
            }
            short underline = font.getUnderline();
            if (underline == Font.U_NONE) {
                result.put("text-decoration", "none");
            } else if (underline == Font.U_SINGLE) {
                result.put("text-decoration", "underline");
            }
        }
        return result;
    }

    /**
     * 获得单元格的样式
     */
    public static JSONObject getHSSFCellStyle(HSSFWorkbook workbook, HSSFCellStyle cellStyle) {
        JSONObject result = new JSONObject();
        if (cellStyle != null) {
            result.put("text-align", cellStyle.getAlignment().toString().toLowerCase());
            result.put("vertical-align", cellStyle.getVerticalAlignment().toString().toLowerCase());

            HSSFColor backgroundColor = cellStyle.getFillForegroundColorColor();
            if (backgroundColor != null && cellStyle.getFillPattern().equals(FillPatternType.SOLID_FOREGROUND)) {
                short[] rgb = backgroundColor.getTriplet();
                result.put("background-color", "rgb(" + rgb[0] + "," + rgb[1] + "," + rgb[2] + ")");
            }

            String borderTopStyle = cellStyle.getBorderTop().toString().toLowerCase();
            if (borderTopStyle.equals("none")) {
                result.put("border-top-style", "solid");
            } else {
                result.put("border-top-style", borderTopStyle);
                result.put("border-top-color", "#000");
            }

            String borderBottomStyle = cellStyle.getBorderBottom().toString().toLowerCase();
            if (borderBottomStyle.equals("none")) {
                result.put("border-bottom-style", "solid");
            } else {
                result.put("border-bottom-style", borderBottomStyle);
                result.put("border-bottom-color", "#000");
            }

            String borderLeftStyle = cellStyle.getBorderLeft().toString().toLowerCase();
            if (borderLeftStyle.equals("none")) {
                result.put("border-left-style", "solid");
            } else {
                result.put("border-left-style", borderLeftStyle);
                result.put("border-left-color", "#000");
            }

            String borderRightStyle = cellStyle.getBorderRight().toString().toLowerCase();
            if (borderRightStyle.equals("none")) {
                result.put("border-right-style", "solid");
            } else {
                result.put("border-right-style", borderRightStyle);
                result.put("border-right-color", "#000");
            }

            HSSFPalette palette = workbook.getCustomPalette();
            HSSFColor borderTopColor = palette.getColor(cellStyle.getTopBorderColor());
            if (borderTopColor != null) {
                short[] rgb = borderTopColor.getTriplet();
                result.put("border-top-color", "rgb(" + rgb[0] + "," + rgb[1] + "," + rgb[2] + ")");
            }

            HSSFColor borderBottomColor = palette.getColor(cellStyle.getBottomBorderColor());
            if (borderBottomColor != null) {
                short[] rgb = borderBottomColor.getTriplet();
                result.put("border-bottom-color", "rgb(" + rgb[0] + "," + rgb[1] + "," + rgb[2] + ")");
            }

            HSSFColor borderLeftColor = palette.getColor(cellStyle.getLeftBorderColor());
            if (borderLeftColor != null) {
                short[] rgb = borderLeftColor.getTriplet();
                result.put("border-left-color", "rgb(" + rgb[0] + "," + rgb[1] + "," + rgb[2] + ")");
            }

            HSSFColor borderRightColor = palette.getColor(cellStyle.getRightBorderColor());
            if (borderRightColor != null) {
                short[] rgb = borderRightColor.getTriplet();
                result.put("border-right-color", "rgb(" + rgb[0] + "," + rgb[1] + "," + rgb[2] + ")");
            }

            HSSFFont font = cellStyle.getFont(workbook);
            if (font.getBold()) {
                result.put("font-weight", "bold");
            } else {
                result.put("font-weight", "normal");
            }
            HSSFColor fontColor = font.getHSSFColor(workbook);
            if (fontColor != null) {
                short[] rgb = fontColor.getTriplet();
                result.put("color", "rgb(" + rgb[0] + "," + rgb[1] + "," + rgb[2] + ")");
            }
            result.put("font-family", font.getFontName());
            if (font.getItalic()) {
                result.put("font-style", "italic");
            } else {
                result.put("font-style", "normal");
            }
            result.put("font-size", font.getFontHeightInPoints() + "px");
            if (font.getStrikeout()) {
                result.put("text-decoration", "line-through");
            }
            short underline = font.getUnderline();
            if (underline == Font.U_NONE) {
                result.put("text-decoration", "none");
            } else if (underline == Font.U_SINGLE) {
                result.put("text-decoration", "underline");
            }
        }
        return result;
    }

    /**
     * 获得单元格的数据
     */
    public static String getCellValue(Cell cell) {
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        }
        // 判断数据的类型
        switch (cell.getCellType()) {
            case NUMERIC: // 数字
                //cellValue = String.valueOf(cell.getNumericCellValue());
                NumberFormat nfNumeric = NumberFormat.getInstance();
                cellValue = nfNumeric.format(cell.getNumericCellValue());
                cellValue = cellValue.replaceAll(",", "");
                break;
            case STRING: // 字符串
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case BOOLEAN: // Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case FORMULA: // 公式
                try {
                    cellValue = cell.getStringCellValue();
                } catch (IllegalStateException e) {
                    //cellValue = String.valueOf(cell.getNumericCellValue());
                    NumberFormat nfFormula = NumberFormat.getInstance();
                    cellValue = nfFormula.format(cell.getNumericCellValue());
                    cellValue = cellValue.replaceAll(",", "");
                }
                break;
            case BLANK: // 空值
                cellValue = "";
                break;
            case ERROR: // 故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }

    public static String getColor(String str) {
        StringBuffer sb = new StringBuffer();
        sb.append("#");
        if (str.length() >= 8) {
            str = str.substring(2, 8);
        }
        sb.append(str);
        return sb.toString();
    }

    public static JSONArray getXSSFMergedRegions(XSSFSheet sheet) {
        JSONArray result = new JSONArray();
        int sheetmergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetmergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("firstRow", range.getFirstRow());
            jsonObject.put("lastRow", range.getLastRow());
            jsonObject.put("firstColumn", range.getFirstColumn());
            jsonObject.put("lastColumn", range.getLastColumn());
            result.add(jsonObject);
        }
        return result;
    }

    public static JSONArray getHSSFMergedRegions(HSSFSheet sheet) {
        JSONArray result = new JSONArray();
        int sheetmergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetmergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("firstRow", range.getFirstRow());
            jsonObject.put("lastRow", range.getLastRow());
            jsonObject.put("firstColumn", range.getFirstColumn());
            jsonObject.put("lastColumn", range.getLastColumn());
            result.add(jsonObject);
        }
        return result;
    }
    
    public static ImportResult readSheet(HttpServletRequest request, InputStream input, int startRow, JSONObject exportRule, String verifyScript) {
    	ImportResult importResult = new ImportResult();
        InputStream is = null;
        try {
            is = FileMagic.prepareToCheckMagic(input);
            FileMagic fm = FileMagic.valueOf(is);
            Workbook workbook = null;
            if (fm.name().equals("OLE2")) {
                // 2003
                workbook = new HSSFWorkbook(is);
            } else if (fm.name().equals("OOXML")) {
                // 2007及2007以上
                workbook = new XSSFWorkbook(is);
            } else {
                throw new FormulaException("文件格式不对");
            }
            importResult = readCell(request, workbook.getSheetAt(0), startRow, exportRule, verifyScript);
            workbook.close();
        } catch (Exception e) {
        	e.printStackTrace();
        	throw new FormulaException(e.getMessage());
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (input != null) {
                	input.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return importResult;
    }

    /**
     * 解析数据
     *
     * @param sheet 表格sheet对象
     * @return
     * @throws IOException
     */
    public static ImportResult readCell(HttpServletRequest request, Sheet sheet, int startRow, JSONObject exportRule, String verifyScript) {
    	ImportResult importResult = new ImportResult();
        List<Map<String, Object>> result = new ArrayList<>();
        List<String> failList = new ArrayList<>();
        short maxColIx = 0;
        Row firstRow = sheet.getRow(0);
        if(firstRow != null) {
        	maxColIx = firstRow.getLastCellNum();
        }
        for (int i = startRow - 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                JSONObject rowJson = new JSONObject();
           	 	for(short colIx=0; colIx<maxColIx; colIx++) {
           	 		Cell cell = row.getCell(colIx);
           	 		String cellValue = getCellValue(cell);
           	 		// 验证规则
           	 		JSONObject fieldRule = exportRule.getJSONObject(letterList.get(colIx));
           	 		JSONArray ruleList  = fieldRule != null ? fieldRule.getJSONArray("rule") : null;
                    if(ruleList != null && ruleList.size() > 0) {
	           	 		for(int j=0;j<ruleList.size();j++) {
	           	 			JSONObject ruleJson = ruleList.getJSONObject(j);
	           	 			String annotation = ruleJson.getString("rule");
	           	 			if(annotation.startsWith("@NotNull")) {
	           	 				JSONObject annotationJson = new JSONObject();
	           	 				String[] annotationAry = annotation.substring(9, annotation.length()-1).split(",");
	           	 				for(int k=0;k<annotationAry.length;k++) {
	           	 					annotationJson.put(annotationAry[k].split("=")[0].trim(), annotationAry[k].split("=")[1].trim());
	           	 				}
	           	 				if(StringUtils.isEmpty(cellValue)) {
	           	 					failList.add("第" + (i + 1) + "行" + letterList.get(colIx) +"列错误：" + annotationJson.getString("message").replace("\"", ""));
	           	 				}
	           	 				rowJson.put(fieldRule.getString("field"), cellValue);
	           	 			} else if (annotation.startsWith("@Length")) {
		           	 			JSONObject annotationJson = new JSONObject();
	           	 				String[] annotationAry = annotation.substring(8, annotation.length()-1).split(",");
	           	 				for(int k=0;k<annotationAry.length;k++) {
	           	 					annotationJson.put(annotationAry[k].split("=")[0].trim(), annotationAry[k].split("=")[1].trim());
	           	 				}
		           	 			if(annotationJson.containsKey("min")) {
		           	 				if(cellValue.length() <= annotationJson.getInteger("min")) {
		           	 					failList.add("第" + (i + 1) + "行" + letterList.get(colIx) +"列错误：" + annotationJson.getString("message").replace("\"", ""));
		           	 				}
		           	 			}
			           	 		if(annotationJson.containsKey("max")) {
				           	 		if(cellValue.length() > annotationJson.getInteger("max")) {
				           	 			failList.add("第" + (i + 1) + "行" + letterList.get(colIx) +"列错误：" + annotationJson.getString("message").replace("\"", ""));
		           	 				}
		           	 			}
			           	 		rowJson.put(fieldRule.getString("field"), cellValue);
	           	 			} else if (annotation.startsWith("@Min")) {
	           	 				int min = Integer.valueOf(annotation.substring(5, annotation.length()-1));
		           	 			if(Integer.valueOf(cellValue) <= min) {
		           	 				failList.add("第" + (i + 1) + "行" + letterList.get(colIx) +"列错误：" + "数值不能小于" + min);
	           	 				}
		           	 			rowJson.put(fieldRule.getString("field"), cellValue);
	           	 			} else if (annotation.startsWith("@Max")) {
		           	 			int max = Integer.valueOf(annotation.substring(5, annotation.length()-1));
		           	 			if(Integer.valueOf(cellValue) > max) {
		           	 				failList.add("第" + (i + 1) + "行" + letterList.get(colIx) +"列错误：" + "数值不能大于" + max);
	           	 				}
		           	 			rowJson.put(fieldRule.getString("field"), cellValue);
	           	 			} else if (annotation.startsWith("@DecimalMin")) {
		           	 			double decimalMin = Double.valueOf(annotation.substring(5, annotation.length()-1));
		           	 			if(Double.valueOf(cellValue) <= decimalMin) {
		           	 				failList.add("第" + (i + 1) + "行" + letterList.get(colIx) +"列错误：" + "数值不能小于" + decimalMin);
	           	 				}
		           	 			rowJson.put(fieldRule.getString("field"), cellValue);
	           	 			} else if (annotation.startsWith("@DecimalMax")) {
		           	 			double decimalMax = Double.valueOf(annotation.substring(5, annotation.length()-1));
		           	 			if(Double.valueOf(cellValue) <= decimalMax) {
		           	 				failList.add("第" + (i + 1) + "行" + letterList.get(colIx) +"列错误：" + "数值不能大于" + decimalMax);
	           	 				}
		           	 			rowJson.put(fieldRule.getString("field"), cellValue);
	           	 			} else if (annotation.startsWith("@Range")) {
		           	 			JSONObject annotationJson = new JSONObject();
	           	 				String[] annotationAry = annotation.substring(8, annotation.length()-1).split(",");
	           	 				for(int k=0;k<annotationAry.length;k++) {
	           	 					annotationJson.put(annotationAry[k].split("=")[0].trim(), annotationAry[k].split("=")[1].trim());
	           	 				}
		           	 			if(annotationJson.containsKey("min")) {
		           	 				if(Integer.valueOf(cellValue) <= annotationJson.getInteger("min")) {
		           	 					failList.add("第" + (i + 1) + "行" + letterList.get(colIx) +"列错误：" + annotationJson.getString("message").replace("\"", ""));
		           	 				}
		           	 			}
			           	 		if(annotationJson.containsKey("max")) {
				           	 		if(Integer.valueOf(cellValue) > annotationJson.getInteger("max")) {
				           	 			failList.add("第" + (i + 1) + "行" + letterList.get(colIx) +"列错误：" + annotationJson.getString("message").replace("\"", ""));
		           	 				}
		           	 			}
			           	 		rowJson.put(fieldRule.getString("field"), cellValue);
	           	 			} else if (annotation.startsWith("@Pattern")) {
		           	 			JSONObject annotationJson = new JSONObject();
	           	 				String[] annotationAry = annotation.substring(8, annotation.length()-1).split(",");
	           	 				for(int k=0;k<annotationAry.length;k++) {
	           	 					annotationJson.put(annotationAry[k].split("=")[0].trim(), annotationAry[k].split("=")[1].trim());
	           	 				}
	           	 				String regexp = annotationJson.getString("regexp");
	           	 				if(!Pattern.matches(regexp, cellValue)) {
	           	 					failList.add("第" + (i + 1) + "行" + letterList.get(colIx) +"列错误：" + annotationJson.getString("message").replace("\"", ""));
	           	 				}
	           	 				rowJson.put(fieldRule.getString("field"), cellValue);
	           	 			} else if (annotation.startsWith("@Replace")) {
	           	 			    if (!StringUtils.isEmpty(cellValue)) {
                                    JSONObject options = ruleJson.getJSONObject("options");
                                    if(StringUtils.isEmpty(options)) {
                                    	failList.add("无效注解@Replace");
                                    } else {
                                        if(!StringUtils.isEmpty(options.get(cellValue))) {
                                            rowJson.put(fieldRule.getString("field"), options.get(cellValue));
                                        } else {
                                        	failList.add("第" + (i + 1) + "行" + letterList.get(colIx) +"列错误：" + "数据字典无相应值");
                                        }
                                    }
                                }
	           	 			} else if (annotation.startsWith("@DateTimeFormat")) {
		           	 			JSONObject annotationJson = new JSONObject();
	           	 				String[] annotationAry = annotation.substring(16, annotation.length()-1).split(",");
	           	 				for(int k=0;k<annotationAry.length;k++) {
	           	 					annotationJson.put(annotationAry[k].split("=")[0].trim(), annotationAry[k].split("=")[1].trim());
	           	 				}
		           	 			if(annotationJson.containsKey("pattern")) {
           	 						String patternStr = annotationJson.getString("pattern").substring(1, annotationJson.getString("pattern").length() - 1);
           	 						Timestamp timestamp = null;
		           	 				if (!StringUtils.isEmpty(cellValue)) {
	           	 						boolean isValid = false;
			           	 				for (String pattern : patternStr.split(";")) {
			           	 					if (patternIsValid(cellValue, pattern)) {
			           	 						isValid = true;
			           	 						DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
				           	 					if (pattern.matches("(h|H|mm|ss)")) {
					           	 					LocalDateTime localDateTime = LocalDateTime.parse(cellValue, dateTimeFormatter);
					           	 					timestamp = Timestamp.valueOf(localDateTime);
				           	 					} else {
					           	 					LocalDate localDate = LocalDate.parse(cellValue, dateTimeFormatter);
					           	 					timestamp = Timestamp.valueOf(localDate.atStartOfDay());
				           	 					}
				           	 					break;
			           	 					}
			           	 				}
			           	 				if (!isValid) {
			           	 					failList.add("第" + (i + 1) + "行" + letterList.get(colIx) +"列错误：" + "日期格式错误");
			           	 				}
		           	 				}
		           	 				rowJson.put(fieldRule.getString("field"), timestamp);
		           	 			}
	           	 			} else if (annotation.startsWith("@Decimal")) {
		           	 			if (!StringUtils.isEmpty(cellValue)) {
		           	 				try {
										rowJson.put(fieldRule.getString("field"), Double.valueOf(cellValue));
									} catch (NumberFormatException e) {
										failList.add("第" + (i + 1) + "行" + letterList.get(colIx) +"列错误：" + "数值转换错误");
									}
		           	 			} else {
		           	 				rowJson.put(fieldRule.getString("field"), null);
		           	 			}
	           	 			} else {
	           	 				failList.add("无效注解");
	           	 			}
	           	 		}
           	 		} else {
           	 			rowJson.put(fieldRule.getString("field"), cellValue);
           	 		}
           	 	}
           	 	if(!StringUtils.isEmpty(verifyScript)) {
		            try {
		            	IGroovyImportVerifyRule verifyRule = GroovyFactory.getInstance().getIGroovyImportVerifyRuleFromCode(verifyScript);
		            	VerifyHandlerResult verifyHandlerResult = verifyRule.verifyRule(request, rowJson);
                        if(!verifyHandlerResult.isSuccess()) {
                        	List<String> msgList = verifyHandlerResult.getMsg();
                        	for (int k = 0; k <= sheet.getLastRowNum(); k++) {
                        		failList.add("第" + (i + 1) + "行错误：" + msgList.get(k));
                        	}
                        }
                    } catch (Exception e) {
		                e.printStackTrace();
		            }
           	 	}
           	 	result.add(rowJson);
            }
        }
        importResult.setList(result);
        importResult.setFailList(failList);
        importResult.setVerifyFail(failList.size() != 0);
        return importResult;
    }

    public static JSONArray getMergedRegions(Sheet sheet) {
        JSONArray result = new JSONArray();
        int sheetmergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetmergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("firstRow", range.getFirstRow());
            jsonObject.put("lastRow", range.getLastRow());
            jsonObject.put("firstColumn", range.getFirstColumn());
            jsonObject.put("lastColumn", range.getLastColumn());
            result.add(jsonObject);
        }
        return result;
    }
    
	/**
	 * 日期字符串是否与日期格式相符
	 * 
	 * @param dateStr 日期字符串
	 * @param pattern 日期格式
	 * @return
	 */
	private static boolean patternIsValid(String dateStr, String pattern) {
		if (!StringUtils.isEmpty(dateStr) && !StringUtils.isEmpty(pattern)) {
			try {
				DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
				if (pattern.matches("(h|H|mm|ss)")) {
					LocalDateTime.parse(dateStr, dateTimeFormatter);
				} else {
					LocalDate.parse(dateStr, dateTimeFormatter);
				}
				return true;
			} catch (Exception e) {
				return false;
			}
		}

		return false;
	}
	
	public static JSONObject readImportRuleSheet(InputStream input) {
		JSONObject result = new JSONObject();
        InputStream is = null;
        try {
            is = FileMagic.prepareToCheckMagic(input);
            FileMagic fm = FileMagic.valueOf(is);
            Workbook workbook = null;
            if (fm.name().equals("OLE2")) {
                // 2003
                workbook = new HSSFWorkbook(is);
            } else if (fm.name().equals("OOXML")) {
                // 2007及2007以上
                workbook = new XSSFWorkbook(is);
            } else {
                throw new FormulaException("文件格式不对");
            }
            Sheet sheet = workbook.getSheetAt(0);
            short maxColIx = 0;
            Row row = sheet.getRow(0);
            if(row != null) {
            	maxColIx = row.getLastCellNum();
           	 	for(short colIx=0; colIx<maxColIx; colIx++) {
           	 		Cell cell = row.getCell(colIx);
           	 		String cellValue = getCellValue(cell);
           	 		result.put(cellValue, letterList.get(colIx));
           	 	}  
            }
            workbook.close();
        } catch (Exception e) {
        	e.printStackTrace();
        	throw new FormulaException(e.getMessage());
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (input != null) {
                	input.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
