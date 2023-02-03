package com.seagox.oa.common;

import java.util.HashMap;

import com.google.common.base.CaseFormat;

public class CamelKeyMap extends HashMap<String, Object> {
	
	private static final long serialVersionUID = 1L;

	@Override
	public Object put(String key, Object value) {
		if(Character.isUpperCase(key.charAt(0))) {
			key = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, key);
		}
		//value = String.valueOf(value);
		return super.put(key, value);
	}
}
