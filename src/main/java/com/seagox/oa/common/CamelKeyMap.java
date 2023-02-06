package com.seagox.oa.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.HashMap;

import com.google.common.base.CaseFormat;

public class CamelKeyMap extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	@Override
	public Object put(String key, Object value) {
		if (Character.isUpperCase(key.charAt(0))) {
			key = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, key);
		}
		if ("CLOB".equals(value.getClass().getSimpleName())) {
			try {
				value = ClobToString((Clob) value);
			} catch (SQLException | IOException e) {
				throw new RuntimeException("Clob类型转String失败," + e.getMessage());
			}
		}
		return super.put(key, value);
	}

	// Clob类型转String
	public String ClobToString(Clob clob) throws SQLException, IOException {
		String res = "";
		Reader is = clob.getCharacterStream();
		BufferedReader br = new BufferedReader(is);
		String s = br.readLine();
		StringBuilder sb = new StringBuilder();
		while (s != null) {
			sb.append(s);
			s = br.readLine();
		}
		res = sb.toString();
		br.close();
		is.close();
		return res;
	}

}
