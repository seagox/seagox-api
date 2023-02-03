package com.seagox.oa.util;

import com.seagox.oa.exception.GrammarException;
import org.apache.commons.jexl3.*;
import org.apache.commons.lang.StringEscapeUtils;
import org.dom4j.*;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XmlUtils {

    public static Map<String, Object> requestToMap(HttpServletRequest request) {
        Map<String, Object> params = new HashMap<>();
        Enumeration<String> parametes = request.getParameterNames();
        while (parametes.hasMoreElements()) {
            String name = (String) parametes.nextElement();
            String value = request.getParameter(name);
            if (!StringUtils.isEmpty(name)) {
                params.put(name, value);
            }
        }
        return params;
    }

    public static List<Map<String, String>> sqlDataset(String text) {
        List<Map<String, String>> dataset = new ArrayList<>();
        try {
            text = text.replaceAll("<!--.*?-->", "");
            text = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + text;
            Document document = DocumentHelper.parseText(text);
            Element rootElement = document.getRootElement();
            List<Element> contentList = rootElement.elements("select");
            for (Element element : contentList) {
                Attribute idAttribute = element.attribute("id");
                Attribute resultTypeAttribute = element.attribute("resultType");
                Attribute keyColumnAttribute = element.attribute("keyColumn");
                if (idAttribute != null && resultTypeAttribute != null && keyColumnAttribute != null) {
                    Map<String, String> item = new HashMap<String, String>();
                    item.put("id", idAttribute.getValue());
                    item.put("resultType", resultTypeAttribute.getValue());
                    item.put("keyColumn", keyColumnAttribute.getValue());
                    dataset.add(item);
                }
            }
        } catch (DocumentException e) {
            throw new GrammarException("xml语法错误");
        }
        return dataset;
    }

    public static String sqlAnalysis(String text, Map<String, Object> params, String id) {
        try {
            text = text.replaceAll("<!--.*?-->", "");
            text = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + text;
            Document document = DocumentHelper.parseText(text);
            Element rootElement = document.getRootElement();
            String result = getNodes(id, rootElement, params);
            result = result.replaceAll("&lt;", "<");
            result = result.replaceAll("&lt;=", "<=");
            result = result.replaceAll("&gt;", ">");
            result = result.replaceAll("&gt;=", ">=");

            Pattern pattern = Pattern.compile("(\\#\\{[^}]+\\})");
            Matcher matcher = pattern.matcher(result);
            while (matcher.find()) {
                String field = matcher.group(1).substring(2, matcher.group(1).length() - 1);
                if (params != null) {
                    if (!StringUtils.isEmpty(params.get(field))) {
                        if (params.get(field) instanceof String) {
                            result = result.replaceAll("#\\{" + field + "\\}", "'" + StringEscapeUtils.escapeSql(params.get(field).toString()) + "'");
                        } else {
                            result = result.replaceAll("#\\{" + field + "\\}", params.get(field).toString());
                        }
                    } else {
                        result = result.replaceAll("#\\{" + field + "\\}", "null");
                    }
                } else {
                    result = result.replaceAll("#\\{" + field + "\\}", "null");
                }
            }

            Pattern $pattern = Pattern.compile("(\\$\\{[^}]+\\})");
            Matcher $matcher = $pattern.matcher(result);
            while ($matcher.find()) {
                String field = $matcher.group(1).substring(2, $matcher.group(1).length() - 1);
                if (!StringUtils.isEmpty(params.get(field))) {
                    result = result.replaceAll("\\$\\{" + field + "\\}", StringEscapeUtils.escapeSql(params.get(field).toString()));
                } else {
                    result = result.replaceAll("\\$\\{" + field + "\\}", "null");
                }
            }
            return result;
        } catch (DocumentException e) {
            throw new GrammarException("xml语法错误");
        }
    }

    @SuppressWarnings("unchecked")
    public static String getNodes(String selectId, Element element, Map<String, Object> params)
            throws DocumentException {
        StringBuilder sb = new StringBuilder();
        String nodeName = element.getName();
        if (nodeName.equals("select") || nodeName.equals("insert") || nodeName.equals("update")
                || nodeName.equals("delete")) {
            Attribute attribute = element.attribute("id");
            if (attribute != null) {
                if (attribute.getValue().equals(selectId)) {
                    List<Node> contentList = element.content();
                    for (Node node : contentList) {
                        if (StringUtils.isEmpty(node.getName())) {
                            if (!StringUtils.isEmpty(node.getText())) {
                                sb.append(node.getText());
                            }
                        } else {
                            Document document = DocumentHelper.parseText(node.asXML());
                            String result = getNodes(selectId, document.getRootElement(), params);
                            if (!StringUtils.isEmpty(result)) {
                                sb.append(result);
                            }
                        }
                    }
                }
            } else {
                List<Node> contentList = element.content();
                for (Node node : contentList) {
                    if (StringUtils.isEmpty(node.getName())) {
                        if (!StringUtils.isEmpty(node.getText())) {
                            sb.append(node.getText());
                        }
                    } else {
                        Document document = DocumentHelper.parseText(node.asXML());
                        String result = getNodes(selectId, document.getRootElement(), params);
                        if (!StringUtils.isEmpty(result)) {
                            sb.append(result);
                        }
                    }
                }
            }
        } else if (nodeName.equals("where")) {
            boolean flag = false;
            List<Node> contentList = element.content();
            for (Node node : contentList) {
                if (StringUtils.isEmpty(node.getName())) {
                    if (!StringUtils.isEmpty(node.getText().trim())) {
                        if (!flag) {
                            sb.append(" where ");
                            flag = true;
                        }
                        sb.append(node.getText());
                    }
                } else {
                    Document document = DocumentHelper.parseText(node.asXML());
                    String result = getNodes(selectId, document.getRootElement(), params);
                    if (!StringUtils.isEmpty(result)) {
                        if (!flag) {
                            sb.append(" where ");
                            result = result.trim().replaceFirst("^(?i)and ", " ");
                            flag = true;
                        }
                        sb.append(result);
                    }
                }
            }
        } else if (nodeName.equals("if")) {
            Attribute attribute = element.attribute("test");
            if (attribute != null) {
                String attrValue = attribute.getValue();
                attrValue = attrValue.replaceAll(" and ", " && ");
                attrValue = attrValue.replaceAll(" or ", " || ");
                try {
                    JexlEngine jexl = new JexlBuilder().create();
                    JexlContext context = new MapContext(params);
                    JexlExpression expression = jexl.createExpression(attrValue);
                    if ((boolean) expression.evaluate(context)) {
                        List<Node> contentList = element.content();
                        for (Node node : contentList) {
                            if (StringUtils.isEmpty(node.getName())) {
                                if (!StringUtils.isEmpty(node.getText())) {
                                    sb.append(node.getText());
                                }
                            } else {
                                Document document = DocumentHelper.parseText(node.asXML());
                                String result = getNodes(selectId, document.getRootElement(), params);
                                if (!StringUtils.isEmpty(result)) {
                                    sb.append(result);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    // e.printStackTrace();
                }
            } else {
                // 语法错误
                throw new GrammarException("if语法缺少test属性");
            }
        } else if (nodeName.equals("foreach")) {
            Attribute collectionAttribute = element.attribute("collection");
            Attribute itemAttribute = element.attribute("item");
            Attribute separatorAttribute = element.attribute("separator");
            Attribute openAttribute = element.attribute("open");
            Attribute closeAttribute = element.attribute("close");
            if (collectionAttribute == null) {
                // 语法错误
                throw new GrammarException("foreach语法缺少collection属性");
            }
            String collection = collectionAttribute.getValue();
            if (itemAttribute == null) {
                // 语法错误
                throw new GrammarException("foreach语法缺少item属性");
            }
            // String item = itemAttribute.getValue();
            if (StringUtils.isEmpty(params.get(collectionAttribute.getValue()))) {
                // 语法错误
                throw new GrammarException(collectionAttribute.getValue() + "参数不能为空");
            }
            String separator = ",";
            if (separatorAttribute != null) {
                separator = separatorAttribute.getValue();
            }
            String open = "(";
            if (openAttribute != null) {
                open = openAttribute.getValue();
            }
            String close = ")";
            if (closeAttribute != null) {
                close = closeAttribute.getValue();
            }
            sb.append(open);
            if (params.get(collection).getClass().isArray()) {
                // 数组
                String typeName = params.get(collection).getClass().getComponentType().getName();
                if (typeName.equals("java.lang.String")) {
                    String[] collectionArray = (String[]) params.get(collection);
                    for (int i = 0; i < collectionArray.length; i++) {
                        sb.append("'" + StringEscapeUtils.escapeSql(collectionArray[i]) + "'");
                        if (i != (collectionArray.length - 1)) {
                            sb.append(separator);
                        }
                    }
                } else if (typeName.equals("int") || typeName.equals("double") || typeName.equals("float")
                        || typeName.equals("long")) {
                    Object[] collectionArray = (Object[]) params.get(collection);
                    for (int i = 0; i < collectionArray.length; i++) {
                        sb.append(collectionArray[i]);
                        if (i != (collectionArray.length - 1)) {
                            sb.append(separator);
                        }
                    }
                } else {
                    // 语法错误
                    throw new GrammarException(collectionAttribute.getValue() + "数组参数类型匹配不到");
                }
            } else if (params.get(collection) instanceof Collection) {
                // 集合
                List<Object> collectionArray = (List<Object>) params.get(collection);

                if (collectionArray.get(0) instanceof Integer || collectionArray.get(0) instanceof Double
                        || collectionArray.get(0) instanceof Float || collectionArray.get(0) instanceof Long) {
                    List<Object> collectionList = (List<Object>) params.get(collection);
                    for (int i = 0; i < collectionList.size(); i++) {
                        sb.append(collectionList.get(i));
                        if (i != (collectionArray.size() - 1)) {
                            sb.append(separator);
                        }
                    }
                } else if (collectionArray.get(0) instanceof String) {
                    List<String> collectionList = (List<String>) params.get(collection);
                    for (int i = 0; i < collectionList.size(); i++) {
                        sb.append("'" + StringEscapeUtils.escapeSql(collectionList.get(i)) + "'");
                        if (i != (collectionArray.size() - 1)) {
                            sb.append(separator);
                        }
                    }
                }
            } else {
                // 语法错误
                throw new GrammarException(collectionAttribute.getValue() + "参数类型不合法");
            }
            sb.append(close);
        } else {
            List<Node> contentList = element.content();
            for (Node node : contentList) {
                if (StringUtils.isEmpty(node.getName())) {
                    if (!StringUtils.isEmpty(node.getText())) {
                        sb.append(node.getText());
                    }
                } else {
                    Document document = DocumentHelper.parseText(node.asXML());
                    String result = getNodes(selectId, document.getRootElement(), params);
                    if (!StringUtils.isEmpty(result)) {
                        sb.append(result);
                    }
                }
            }
        }
        return sb.toString();
    }

    public static String sqlResultType(String text) {
        String resultType = "";
        try {
            text = text.replaceAll("<!--.*?-->", "");
            text = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + text;
            Document document = DocumentHelper.parseText(text);
            Element rootElement = document.getRootElement();
            resultType = rootElement.attribute("resultType").getValue();
        } catch (DocumentException e) {
            throw new GrammarException("xml语法错误");
        }
        return resultType;
    }
}
