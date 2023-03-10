package com.seagox.oa.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.seagox.oa.exception.ConfirmException;
import com.seagox.oa.exception.FormulaException;
import com.seagox.oa.groovy.GroovyFactory;
import com.seagox.oa.groovy.IGroovyRule;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.FileMagic;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.util.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class ImportUtils {

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
                // 2007???2007??????
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
                // 2007???2007??????
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
     * ????????????
     *
     * @param sheet ??????sheet??????
     * @return
     * @throws IOException
     */
    public static JSONObject readHSSF(HSSFWorkbook workbook, HSSFSheet sheet) throws IOException {
        JSONArray mergedRegions = getHSSFMergedRegions(sheet);
        //??????json
        JSONObject resultJson = new JSONObject();
        //????????????
        JSONArray widthJson = new JSONArray();
        // ????????????JSON????????????
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
                            //????????????????????????
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
                            String value = getCellValue(cell);//?????????????????????
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
     * ????????????
     *
     * @param sheet ??????sheet??????
     * @return
     * @throws IOException
     */
    public static JSONObject readXSSF(XSSFSheet sheet) throws IOException {
        JSONArray mergedRegions = getXSSFMergedRegions(sheet);
        //??????json
        JSONObject resultJson = new JSONObject();
        //????????????
        JSONArray widthJson = new JSONArray();
        // ????????????JSON????????????
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
                            //????????????????????????
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
                            String value = getCellValue(cell);//?????????????????????
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
     * ????????????????????????
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
     * ????????????????????????
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
     * ????????????????????????
     */
    public static String getCellValue(Cell cell) {
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        }
        // ?????????????????????
        switch (cell.getCellType()) {
            case NUMERIC: // ??????
                //cellValue = String.valueOf(cell.getNumericCellValue());
                NumberFormat nfNumeric = NumberFormat.getInstance();
                cellValue = nfNumeric.format(cell.getNumericCellValue());
                cellValue = cellValue.replaceAll(",", "");
                break;
            case STRING: // ?????????
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case BOOLEAN: // Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case FORMULA: // ??????
                try {
                    cellValue = cell.getStringCellValue();
                } catch (IllegalStateException e) {
                    //cellValue = String.valueOf(cell.getNumericCellValue());
                    NumberFormat nfFormula = NumberFormat.getInstance();
                    cellValue = nfFormula.format(cell.getNumericCellValue());
                    cellValue = cellValue.replaceAll(",", "");
                }
                break;
            case BLANK: // ??????
                cellValue = "";
                break;
            case ERROR: // ??????
                cellValue = "????????????";
                break;
            default:
                cellValue = "????????????";
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
    
    public static List<Map<String, Object>> readSheet(InputStream input, int startRow, JSONObject exportRule, String verifyScript) {
    	List<Map<String, Object>> result = new ArrayList<>();
        InputStream is = null;
        try {
            is = FileMagic.prepareToCheckMagic(input);
            FileMagic fm = FileMagic.valueOf(is);
            Workbook workbook = null;
            if (fm.name().equals("OLE2")) {
                // 2003
                workbook = new HSSFWorkbook(is);
            } else if (fm.name().equals("OOXML")) {
                // 2007???2007??????
                workbook = new XSSFWorkbook(is);
            } else {
                throw new FormulaException("??????????????????");
            }
            result = readCell(workbook.getSheetAt(0), startRow, exportRule, verifyScript);
            workbook.close();
        } catch (Exception e) {
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

    /**
     * ????????????
     *
     * @param sheet ??????sheet??????
     * @return
     * @throws IOException
     */
    public static List<Map<String, Object>> readCell(Sheet sheet, int startRow, JSONObject exportRule, String verifyScript) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (int i = startRow - 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
            	JSONObject rowJson = new JSONObject();
            	short maxColIx = row.getLastCellNum();
           	 	for(short colIx=0; colIx<maxColIx; colIx++) {
           	 		Cell cell = row.getCell(colIx);
           	 		String cellValue = getCellValue(cell);
           	 		// ????????????
           	 		JSONObject fieldRule = exportRule.getJSONObject(letterList.get(colIx));
           	 		if(fieldRule != null) {
	           	 		JSONArray ruleList  = fieldRule.getJSONArray("rule");
	           	 		if(ruleList != null) {
		           	 		for(int j=0;j<ruleList.size();j++) {
		           	 			JSONObject ruleJson = ruleList.getJSONObject(j);
		           	 			String annotation = ruleJson.getString("rule");
		           	 			if(annotation.startsWith("@NotNull")) {
		           	 				JSONObject annotationJson = new JSONObject();
		           	 				String[] annotationAry = annotation.substring(9, annotation.length()-1).split(",");
		           	 				for(int k=0;k<annotationAry.length;k++) {
		           	 					annotationJson.put(annotationAry[k].split("=")[0], annotationAry[k].split("=")[1]);
		           	 				}
		           	 				if(StringUtils.isEmpty(cellValue)) {
		           	 					throw new FormulaException("???" + (i + 1) + "???" + letterList.get(colIx) +"????????????" + annotationJson.getString("message"));
		           	 				}
		           	 				rowJson.put(fieldRule.getString("field"), cellValue);
		           	 			} else if (annotation.startsWith("@Length")) {
			           	 			JSONObject annotationJson = new JSONObject();
		           	 				String[] annotationAry = annotation.substring(8, annotation.length()-1).split(",");
		           	 				for(int k=0;k<annotationAry.length;k++) {
		           	 					annotationJson.put(annotationAry[k].split("=")[0], annotationAry[k].split("=")[1]);
		           	 				}
			           	 			if(annotationJson.containsKey("min")) {
			           	 				if(cellValue.length() <= annotationJson.getInteger("min")) {
			           	 					throw new FormulaException("???" + (i + 1) + "???" + letterList.get(colIx) +"????????????" + annotationJson.getString("message"));
			           	 				}
			           	 			}
				           	 		if(annotationJson.containsKey("max")) {
					           	 		if(cellValue.length() > annotationJson.getInteger("max")) {
			           	 					throw new FormulaException("???" + (i + 1) + "???" + letterList.get(colIx) +"????????????" + annotationJson.getString("message"));
			           	 				}
			           	 			}
				           	 		rowJson.put(fieldRule.getString("field"), cellValue);
		           	 			} else if (annotation.startsWith("@Min")) {
		           	 				int min = Integer.valueOf(annotation.substring(5, annotation.length()-1));
			           	 			if(Integer.valueOf(cellValue) <= min) {
		           	 					throw new FormulaException("???" + (i + 1) + "???" + letterList.get(colIx) +"????????????" + "??????????????????" + min);
		           	 				}
			           	 			rowJson.put(fieldRule.getString("field"), cellValue);
		           	 			} else if (annotation.startsWith("@Max")) {
			           	 			int max = Integer.valueOf(annotation.substring(5, annotation.length()-1));
			           	 			if(Integer.valueOf(cellValue) > max) {
		           	 					throw new FormulaException("???" + (i + 1) + "???" + letterList.get(colIx) +"????????????" + "??????????????????" + max);
		           	 				}
			           	 			rowJson.put(fieldRule.getString("field"), cellValue);
		           	 			} else if (annotation.startsWith("@DecimalMin")) {
			           	 			double decimalMin = Double.valueOf(annotation.substring(5, annotation.length()-1));
			           	 			if(Double.valueOf(cellValue) <= decimalMin) {
		           	 					throw new FormulaException("???" + (i + 1) + "???" + letterList.get(colIx) +"????????????" + "??????????????????" + decimalMin);
		           	 				}
			           	 			rowJson.put(fieldRule.getString("field"), cellValue);
		           	 			} else if (annotation.startsWith("@DecimalMax")) {
			           	 			double decimalMax = Double.valueOf(annotation.substring(5, annotation.length()-1));
			           	 			if(Double.valueOf(cellValue) <= decimalMax) {
		           	 					throw new FormulaException("???" + (i + 1) + "???" + letterList.get(colIx) +"????????????" + "??????????????????" + decimalMax);
		           	 				}
			           	 			rowJson.put(fieldRule.getString("field"), cellValue);
		           	 			} else if (annotation.startsWith("@Range")) {
			           	 			JSONObject annotationJson = new JSONObject();
		           	 				String[] annotationAry = annotation.substring(8, annotation.length()-1).split(",");
		           	 				for(int k=0;k<annotationAry.length;k++) {
		           	 					annotationJson.put(annotationAry[k].split("=")[0], annotationAry[k].split("=")[1]);
		           	 				}
			           	 			if(annotationJson.containsKey("min")) {
			           	 				if(Integer.valueOf(cellValue) <= annotationJson.getInteger("min")) {
			           	 					throw new FormulaException("???" + (i + 1) + "???" + letterList.get(colIx) +"????????????" + annotationJson.getString("message"));
			           	 				}
			           	 			}
				           	 		if(annotationJson.containsKey("max")) {
					           	 		if(Integer.valueOf(cellValue) > annotationJson.getInteger("max")) {
			           	 					throw new FormulaException("???" + (i + 1) + "???" + letterList.get(colIx) +"????????????" + annotationJson.getString("message"));
			           	 				}
			           	 			}
				           	 		rowJson.put(fieldRule.getString("field"), cellValue);
		           	 			} else if (annotation.startsWith("@Pattern")) {
			           	 			JSONObject annotationJson = new JSONObject();
		           	 				String[] annotationAry = annotation.substring(8, annotation.length()-1).split(",");
		           	 				for(int k=0;k<annotationAry.length;k++) {
		           	 					annotationJson.put(annotationAry[k].split("=")[0], annotationAry[k].split("=")[1]);
		           	 				}
		           	 				String regexp = annotationJson.getString("regexp");
		           	 				if(!Pattern.matches(regexp, cellValue)) {
		           	 					throw new FormulaException("???" + (i + 1) + "???" + letterList.get(colIx) +"????????????" + annotationJson.getString("message"));
		           	 				}
		           	 				rowJson.put(fieldRule.getString("field"), cellValue);
		           	 			} else if (annotation.startsWith("@Replace")) {
		           	 				JSONObject options = ruleJson.getJSONObject("options");
		           	 				if(StringUtils.isEmpty(options)) {
		           	 					throw new FormulaException("????????????@Replace");
		           	 				} else {
			           	 				if(!StringUtils.isEmpty(options.get(cellValue))) {
			           	 					rowJson.put(fieldRule.getString("field"), options.get(cellValue));
			           	 				} else {
			           	 					throw new FormulaException("???" + (i + 1) + "???" + letterList.get(colIx) +"????????????" + "????????????????????????");
			           	 				}
		           	 				}
		           	 			} else if (annotation.startsWith("@DateTimeFormat")) {
			           	 			JSONObject annotationJson = new JSONObject();
		           	 				String[] annotationAry = annotation.substring(16, annotation.length()-1).split(",");
		           	 				for(int k=0;k<annotationAry.length;k++) {
		           	 					annotationJson.put(annotationAry[k].split("=")[0], annotationAry[k].split("=")[1]);
		           	 				}
			           	 			if(annotationJson.containsKey("pattern")) {
		           	 					try {
		           	 						String pattern = annotationJson.getString("pattern");
		           	 						SimpleDateFormat sdf = new SimpleDateFormat (pattern);
		           	 						Date date = null;
				           	 				if (!StringUtils.isEmpty(cellValue)) {
				           	 					date = sdf.parse(cellValue);
				           	 				}
			                   	 			rowJson.put(fieldRule.getString("field"), date);
										} catch (Exception e) {
											e.printStackTrace();
			           	 					throw new FormulaException("???" + (i + 1) + "???" + letterList.get(colIx) +"????????????" + "??????????????????");
										}
			           	 			}
		           	 			} else if (annotation.startsWith("@Decimal")) {
			           	 			if (!StringUtils.isEmpty(cellValue)) {
			           	 				try {
											rowJson.put(fieldRule.getString("field"), Double.valueOf(cellValue));
										} catch (NumberFormatException e) {
			           	 					throw new FormulaException("???" + (i + 1) + "???" + letterList.get(colIx) +"????????????" + "??????????????????");
										}
			           	 			} else {
			           	 				rowJson.put(fieldRule.getString("field"), null);
			           	 			}
		           	 			} else {
		           	 				throw new FormulaException("????????????");
		           	 			}
		           	 		}
	           	 		} else {
	           	 			rowJson.put(fieldRule.getString("field"), cellValue);
	           	 		}
           	 		}
           	 	}
           	 	if(!StringUtils.isEmpty(verifyScript)) {
	           	 	Map<String, Object> params = JSON.parseObject(rowJson.toJSONString());
		            try {
		                IGroovyRule groovyRule = GroovyFactory.getInstance().getIRuleFromCode(verifyScript);
		                groovyRule.execute(null, params);
		            } catch (ConfirmException e) {
		                throw new ConfirmException(e.getMessage());
		            } catch (Exception e) {
		                e.printStackTrace();
		            }
           	 	}
           	 	result.add(rowJson);
            }
        }
        return result;
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

}
