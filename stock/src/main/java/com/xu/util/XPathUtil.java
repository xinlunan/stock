package com.xu.util;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XPathUtil {

	public static Object parse(InputSource source, String expression, QName returnType) {
		XPath xPath = XPathFactory.newInstance().newXPath();
		try {
			XPathExpression expr = xPath.compile(expression);
			NodeList daylyNodes = (NodeList) expr.evaluate(source, returnType);
			return daylyNodes;

		} catch (XPathExpressionException e) {
			throw new RuntimeException(e);
		}
	}

	public static Object parse(Document doc, String expression, QName returnType) {
		XPath xPath = XPathFactory.newInstance().newXPath();
		try {
			XPathExpression expr = xPath.compile(expression);
			NodeList daylyNodes = (NodeList) expr.evaluate(doc, returnType);
			return daylyNodes;

		} catch (XPathExpressionException e) {
			throw new RuntimeException(e);
		}
	}
}
