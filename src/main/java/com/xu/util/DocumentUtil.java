package com.xu.util;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class DocumentUtil {
	/**
	 * String 转换为 Document 对象
	 * 
	 * @param xml 字符串
	 * @return Document 对象
	 */
	public static Document string2Doc(String xml) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		Document doc = null;
		InputSource source = null;
		StringReader reader = null;
		try {
			builder = factory.newDocumentBuilder();
			reader = new StringReader(xml);
			source = new InputSource(reader);//使用字符流创建新的输入源  
			doc = builder.parse(source);
			return doc;
		} catch (Exception e) {
			return null;
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
	}
}
