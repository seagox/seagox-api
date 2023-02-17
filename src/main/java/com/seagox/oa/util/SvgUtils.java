package com.seagox.oa.util;

import java.net.MalformedURLException;
import java.net.URL;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

public class SvgUtils {

	public static String parseString(String url) {
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read(new URL(url));
			return document.getRootElement().asXML();
		} catch (MalformedURLException | DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
