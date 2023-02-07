package com.seagox.oa.util;

import groovy.lang.GroovyShell;
import groovy.lang.Script;

public class GroovyUtils {
	
	public static Object parseScript(String scriptText, String method, Object params) {
		try {
			//创建GroovyShell
	        GroovyShell groovyShell = new GroovyShell();
	        //装载解析脚本代码
	        Script script = groovyShell.parse(scriptText);
	        //执行
	        Object reuslt = script.invokeMethod(method, params);
	        groovyShell.getClassLoader().clearCache();
	        return reuslt;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("读取groovy脚本异常");
			return null;
		}
    	
    }
}
