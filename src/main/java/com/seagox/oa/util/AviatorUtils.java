package com.seagox.oa.util;

import java.util.Map;

import com.googlecode.aviator.AviatorEvaluator;

public class AviatorUtils {
	
	public static Object calculate(String expression, Map<String, Object> variables) {
		AviatorEvaluator.addFunction(new SumFunction());
		AviatorEvaluator.addFunction(new IfFunction());
		return AviatorEvaluator.execute(expression, variables);
	}
}
