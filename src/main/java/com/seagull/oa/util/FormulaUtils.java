package com.seagull.oa.util;

import com.seagull.oa.exception.FormulaException;
import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.MapContext;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormulaUtils {

    /**
     * 公式
     */
    public static final String[] FUNCTION_REGEX = {
            "IF\\(", "IFS\\(", "AND\\(", "OR\\(", "XOR\\(", "NOT\\(", "ISEMPTY\\(",
            "CONCAT\\(", "LEN\\(", "UPPER\\(", "LOWER\\(", "JOIN\\(", "MID\\(",
            "AVERAGE\\(", "COUNT\\(", "SUM\\(", "MAX\\(", "MIN\\(", "ROUND\\(",
            "CURDATE\\(", "NOW\\(", "UUID\\(", "YEAR\\(", "MONTH\\(",
            "DAY\\(", "HOUR\\(", "MINUTE\\(", "SECOND\\(", "DAYS\\("
    };

    /**
     * 公式替换
     *
     * @param input     输入
     * @param variables 参数
     * @return
     */
    public static Object calculate(String input, Map<String, Object> variables) {
        // 参数替换
        Pattern pattern = Pattern.compile("\\$\\{(.*?)\\}");
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            if (variables.containsKey(matcher.group().substring(2, matcher.group().length() - 1))) {
                input = input.replace(matcher.group(), "\"" + variables.get(matcher.group().substring(2, matcher.group().length() - 1)).toString() + "\"");
            } else {
                throw new FormulaException("表达式中缺少参数");
            }
        }
        // 函数替换
        List<Integer> startRegexList = getStartRegex(input);
        List<Integer> endRegexList = getEndRegex(input);

        String lastKey = "";
        int lastValue = 0;
        int functionNum = 0;
        Pattern functionPattern = Pattern.compile(String.join("|", FUNCTION_REGEX));
        Matcher functionMatcher = functionPattern.matcher(input);
        while (functionMatcher.find()) {
            lastKey = functionMatcher.group();
            lastValue = functionMatcher.start();
            functionNum = functionNum + 1;
        }

        if (startRegexList.size() != endRegexList.size()) {
            // 表达式错误
            throw new FormulaException("表达式中()有误");
        }

        if (!StringUtils.isEmpty(lastKey)) {
            int start = lastValue;
            int end = endRegexList.get(startRegexList.indexOf(lastValue + lastKey.length() - 1)) + 1;
            String type = lastKey.substring(0, lastKey.length() - 1);
            String content = input.substring(start + lastKey.length(), end - 1);
            String result = handleFunction(type, content);
            String newInput = input.replace(input.substring(start, end), result);

            // 循环检查替换
            if (functionNum > 1) {
                return calculate(newInput, variables);
            } else {
                return newInput.replaceAll("\"", "");
            }
        }
        return input.replaceAll("\"", "");
    }

    public static String handleFunction(String type, String content) {
        if (type.equals("IF")) {
            String[] contentArray = content.split(",");
            if (contentArray.length == 3) {
                JexlContext jc = new MapContext();
                JexlExpression e = new JexlBuilder().create().createExpression(contentArray[0]);
                if ((boolean) e.evaluate(jc)) {
                    return "\"" + contentArray[1].trim() + "\"";
                } else {
                    return "\"" + contentArray[2].trim() + "\"";
                }
            } else {
                throw new FormulaException("表达式中IF函数设置有误");
            }
        } else if (type.equals("IFS")) {
            String[] contentArray = content.split(",");
            if (contentArray.length % 2 == 0) {
                JexlContext jc = new MapContext();
                for (int i = 0; i < contentArray.length; i++) {
                    if (i % 2 == 0) {
                        JexlExpression e = new JexlBuilder().create().createExpression(contentArray[i]);
                        if ((boolean) e.evaluate(jc)) {
                            return "\"" + contentArray[i + 1].trim() + "\"";
                        }
                    }
                }
            } else {
                throw new FormulaException("表达式中IFS函数设置有误");
            }
        } else if (type.equals("AND")) {
            String[] contentArray = content.split(",");
            if (contentArray.length == 0) {
                JexlContext jc = new MapContext();
                for (int i = 0; i < contentArray.length; i++) {
                    JexlExpression e = new JexlBuilder().create().createExpression(contentArray[i]);
                    if (!(boolean) e.evaluate(jc)) {
                        return "false";
                    }
                }
                return "true";
            } else {
                throw new FormulaException("表达式中AND函数设置有误");
            }
        } else if (type.equals("OR")) {
            String[] contentArray = content.split(",");
            if (contentArray.length == 0) {
                JexlContext jc = new MapContext();
                for (int i = 0; i < contentArray.length; i++) {
                    JexlExpression e = new JexlBuilder().create().createExpression(contentArray[i]);
                    if ((boolean) e.evaluate(jc)) {
                        return "true";
                    }
                }
                return "false";
            } else {
                throw new FormulaException("表达式中OR函数设置有误");
            }
        } else if (type.equals("XOR")) {
            String[] contentArray = content.split(",");
            if (contentArray.length == 0) {
                int trueResult = 0;
                int falseResult = 0;
                JexlContext jc = new MapContext();
                for (int i = 0; i < contentArray.length; i++) {
                    JexlExpression e = new JexlBuilder().create().createExpression(contentArray[i]);
                    if ((boolean) e.evaluate(jc)) {
                        trueResult = trueResult + 1;
                    } else {
                        falseResult = falseResult + 1;
                    }
                }
                if (trueResult == 0 || falseResult == 0) {
                    return "false";
                }
                return "true";
            } else {
                throw new FormulaException("表达式中OR函数设置有误");
            }
        } else if (type.equals("NOT")) {
            JexlContext jc = new MapContext();
            JexlExpression e = new JexlBuilder().create().createExpression(content);
            if ((boolean) e.evaluate(jc)) {
                return "false";
            }
            return "true";
        } else if (type.equals("ISEMPTY")) {
            if (StringUtils.isEmpty(content)) {
                return "true";
            }
            return "false";
        } else if (type.equals("CONCAT")) {
            String[] contentArray = content.split(",");
            String result = "";
            if (contentArray.length != 0) {
                for (int i = 0; i < contentArray.length; i++) {
                    result = result + contentArray[i];
                }
                return "\"" + result + "\"";
            } else {
                throw new FormulaException("表达式中CONCAT函数设置有误");
            }
        } else if (type.equals("LEN")) {
            return String.valueOf(content.length());
        } else if (type.equals("UPPER")) {
            return content.toUpperCase();
        } else if (type.equals("LOWER")) {
            return content.toLowerCase();
        } else if (type.equals("JOIN")) {
            String[] contentArray = content.split(",");
            if (contentArray.length >= 2) {
                return String.join(contentArray[0], Arrays.copyOfRange(contentArray, 1, contentArray.length));
            } else {
                throw new FormulaException("表达式中JOIN函数设置有误");
            }
        } else if (type.equals("MID")) {
            String[] contentArray = content.split(",");
            if (contentArray.length == 3) {
                return contentArray[0].substring(Integer.valueOf(contentArray[1]), Integer.valueOf(contentArray[2]));
            } else {
                throw new FormulaException("表达式中MID函数设置有误");
            }
        } else if (type.equals("AVERAGE")) {
            String[] contentArray = content.split(",");
            BigDecimal totalAmount = new BigDecimal(0);
            if (contentArray.length != 0) {
                for (int i = 0; i < contentArray.length; i++) {
                    totalAmount.add(new BigDecimal(contentArray[i].trim()));
                }
                return String.valueOf(totalAmount.divide(new BigDecimal(contentArray.length)));
            } else {
                throw new FormulaException("表达式中AVERAGE函数设置有误");
            }
        } else if (type.equals("COUNT")) {
            String[] contentArray = content.split(",");
            return String.valueOf(contentArray.length);
        } else if (type.equals("SUM")) {
            String[] contentArray = content.split(",");
            BigDecimal totalAmount = new BigDecimal(0);
            if (contentArray.length != 0) {
                for (int i = 0; i < contentArray.length; i++) {
                    totalAmount.add(new BigDecimal(contentArray[i].trim()));
                }
                return String.valueOf(totalAmount);
            } else {
                throw new FormulaException("表达式中SUM函数设置有误");
            }
        } else if (type.equals("MAX")) {
            String[] contentArray = content.split(",");
            if (contentArray.length != 0) {
                BigDecimal maxValue = new BigDecimal(contentArray[0]);
                for (int i = 1; i < contentArray.length; i++) {
                    BigDecimal curValue = new BigDecimal(contentArray[i]);
                    if (curValue.compareTo(maxValue) == 1) {
                        maxValue = curValue;
                    }
                }
                return maxValue.toString();
            } else {
                throw new FormulaException("表达式中MAX函数设置有误");
            }
        } else if (type.equals("MIN")) {
            String[] contentArray = content.split(",");
            if (contentArray.length != 0) {
                BigDecimal mixValue = new BigDecimal(contentArray[0]);
                for (int i = 1; i < contentArray.length; i++) {
                    BigDecimal curValue = new BigDecimal(contentArray[i]);
                    if (curValue.compareTo(mixValue) == -1) {
                        mixValue = curValue;
                    }
                }
                return mixValue.toString();
            } else {
                throw new FormulaException("表达式中MIN函数设置有误");
            }
        } else if (type.equals("ROUND")) {
            String[] contentArray = content.split(",");
            if (contentArray.length == 2) {
                return String.format("%." + contentArray[1] + "f", contentArray[0]);
            } else {
                throw new FormulaException("表达式中ROUND函数设置有误");
            }
        } else if (type.equals("CURDATE")) {
            return "\"" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "\"";
        } else if (type.equals("NOW")) {
            return "\"" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\"";
        } else if (type.equals("UUID")) {
            return "\"" + UUID.randomUUID().toString() + "\"";
        } else if (type.equals("YEAR")) {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                cal.setTime(sdf.parse(content));
                return String.valueOf(cal.get(Calendar.YEAR));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else if (type.equals("MONTH")) {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                cal.setTime(sdf.parse(content));
                return String.valueOf(cal.get(Calendar.MONTH) + 1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else if (type.equals("DAY")) {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                cal.setTime(sdf.parse(content));
                return String.valueOf(cal.get(Calendar.DATE));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else if (type.equals("HOUR")) {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                cal.setTime(sdf.parse(content));
                return String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else if (type.equals("MINUTE")) {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                cal.setTime(sdf.parse(content));
                return String.valueOf(cal.get(Calendar.MINUTE));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else if (type.equals("SECOND")) {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                cal.setTime(sdf.parse(content));
                return String.valueOf(cal.get(Calendar.SECOND));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else if (type.equals("DAYS")) {
            String[] contentArray = content.split(",");
            if (contentArray.length == 2) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date startDate;
                Date endDate;
                try {
                    startDate = sdf.parse(contentArray[1].trim());
                    endDate = sdf.parse(contentArray[0].trim());
                    return String.valueOf(
                            TimeUnit.DAYS.convert(endDate.getTime() - startDate.getTime(), TimeUnit.MILLISECONDS));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                throw new FormulaException("表达式中DAYS函数设置有误");
            }
        }
        return null;
    }

    public static List<Integer> getStartRegex(String input) {
        String startRegex = "\\(";
        List<Integer> startRegexList = new ArrayList<>();
        Pattern startPattern = Pattern.compile(startRegex);
        Matcher startMatcher = startPattern.matcher(input);
        while (startMatcher.find()) {
            startRegexList.add(startMatcher.start());
        }
        return startRegexList;
    }

    public static List<Integer> getEndRegex(String input) {
        String endRegex = "\\)";
        List<Integer> endRegexList = new ArrayList<>();
        Pattern endPattern = Pattern.compile(endRegex);
        Matcher endMatcher = endPattern.matcher(input);
        while (endMatcher.find()) {
            endRegexList.add(endMatcher.start());
        }
        Collections.reverse(endRegexList);
        return endRegexList;
    }

    /**
     * 获取客户端请求参数中所有的信息
     *
     * @param request
     * @return
     */
    public static Map<String, Object> getAllRequestParam(HttpServletRequest request) {
        Map<String, Object> res = new HashMap<String, Object>();
        Enumeration<String> enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            res.put(key, request.getParameter(key));
        }
        return res;
    }

}
