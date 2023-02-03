package com.seagox.oa.util;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.policy.HackLoopTableRenderPolicy;
import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlEngine;
import org.jxls.common.Context;
import org.jxls.expression.JexlExpressionEvaluator;
import org.jxls.transform.Transformer;
import org.jxls.util.JxlsHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ExportUtils {

    /***
     * excel导出公共方法
     * @param templateUrl  模板地址
     * @param fileName      导出文件名
     * @param params        参数
     * @param request       request
     * @param response      response
     */
    public static void exportExcel(String templateUrl, String fileName, Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) {
        OutputStream os = null;
        InputStream in = null;
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName + ".xlsx").getBytes("GBK"), "ISO-8859-1"));
            response.setContentType("application/octet-stream;charset=UTF-8");
            Context context = new Context();
            if (params != null) {
                for (String key : params.keySet()) {
                    context.putVar(key, params.get(key));
                }
            }
            in = new URL(templateUrl).openStream();
            os = response.getOutputStream();
            JxlsHelper jxlsHelper = JxlsHelper.getInstance();
            Transformer transformer = jxlsHelper.createTransformer(in, os);
            //获得配置
            JexlExpressionEvaluator evaluator = (JexlExpressionEvaluator) transformer.getTransformationConfig().getExpressionEvaluator();
            //函数强制，自定义功能
            Map<String, Object> funcs = new HashMap<String, Object>();
            funcs.put("utils", new JxlsUtils()); //添加自定义功能
            JexlBuilder jb = new JexlBuilder();
            jb.namespaces(funcs);
            jb.silent(true); //设置静默模式，不报警告
            JexlEngine je = jb.create();
            evaluator.setJexlEngine(je);
            //必须要这个，否者表格函数统计会错乱
            jxlsHelper.setUseFastFormulaProcessor(false)
                    .processTemplate(context, transformer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /***
     * word导出公共方法
     * @param templateUrl  模板地址
     * @param fileName      导出文件名
     * @param params        参数
     * @param request       request
     * @param response      response
     */
    public static void exportWord(String templateUrl, String fileName, Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) {
        OutputStream out = null;
        InputStream in = null;

        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName + ".docx").getBytes("GBK"), "ISO-8859-1"));
            response.setContentType("application/octet-stream;charset=UTF-8");

            in = new URL(templateUrl).openStream();
            out = response.getOutputStream();
            HackLoopTableRenderPolicy policy = new HackLoopTableRenderPolicy();
            Set<String> set = params.keySet();
            String key = null;
            if (set.iterator().hasNext()) {
                key = set.iterator().next();
            }
            Configure config = Configure.newBuilder().bind(key, policy).build();
            XWPFTemplate template = XWPFTemplate.compile(in, config)
                    .render(params);
            template.write(out);
            template.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
