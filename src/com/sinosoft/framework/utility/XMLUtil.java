package com.sinosoft.framework.utility;

import org.apache.xpath.XPathAPI;
import org.cyberneko.html.parsers.DOMParser;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * XML工具类
 * 
 */
public class XMLUtil {
	private static final Logger logger = LoggerFactory.getLogger(XMLUtil.class);
	/**
	 * 以UTF-8编码输出节点为字符串
	 */
	public static String toString(Node node) {
		return toString(node, "UTF-8");
	}

	/**
	 * 以指定编码输出节点为字符串
	 */
	public static String toString(Node node, String encoding) {
		try {
			Transformer serializer = TransformerFactory.newInstance().newTransformer();
			if (node.getNodeType() == Node.DOCUMENT_NODE) {
				serializer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			} else {
				serializer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			}
			serializer.setOutputProperty(OutputKeys.ENCODING, encoding);
			serializer.setOutputProperty(OutputKeys.INDENT, "yes");
			serializer.setOutputProperty(OutputKeys.CDATA_SECTION_ELEMENTS, "yes");
			serializer.setOutputProperty(OutputKeys.METHOD, "xml");
			if (isTextNode(node)) {
				return node.getNodeValue();
			} else {
				StringWriter w = new StringWriter();
				serializer.transform(new DOMSource(node), new StreamResult(w));
				return w.toString();
			}
		} catch (TransformerConfigurationException e) {
			logger.error(e.getMessage(), e);
		} catch (TransformerFactoryConfigurationError e) {
			logger.error(e.getMessage(), e);
		} catch (TransformerException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 将节点转为HTML
	 */
	public static String toHTML(Node node) {
		try {
			Transformer serializer = TransformerFactory.newInstance().newTransformer();
			if (isTextNode(node)) {
				return node.getNodeValue();
			} else {
				StringWriter w = new StringWriter();
				serializer.transform(new DOMSource(node), new StreamResult(w));
				return w.toString();
			}
		} catch (TransformerConfigurationException e) {
			logger.error(e.getMessage(), e);
		} catch (TransformerFactoryConfigurationError e) {
			logger.error(e.getMessage(), e);
		} catch (TransformerException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 以UTF-8编码解析HTML，转为规范的XML格式
	 */
	public static String htmlToXML(String html) {
		return htmlToXML(html, "UTF-8");
	}

	/**
	 * 以指定编码解析HTML，转为规范的XML格式
	 */
	public static String htmlToXML(String html, String encoding) {
		DOMParser parser = new DOMParser();
		try {
			parser.parse(new InputSource(new StringReader(html)));
			Document doc = parser.getDocument();
			NodeList list = XPathAPI.selectNodeList(doc, "//SCRIPT|//STYLE");
			for (int i = 0; i < list.getLength(); i++) {
				Node script = list.item(i);
				if (script.hasChildNodes()) {
					script.replaceChild(doc.createCDATASection(script.getFirstChild().getNodeValue()), script
							.getFirstChild());
				}
			}
			return toString(doc);
		} catch (SAXException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} catch (TransformerFactoryConfigurationError e) {
			logger.error(e.getMessage(), e);
		} catch (TransformerException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 判断节点是否是文本节点
	 */
	public static boolean isTextNode(Node node) {
		if (node == null)
			return false;
		short nodeType = node.getNodeType();
		return nodeType == Node.CDATA_SECTION_NODE || nodeType == Node.TEXT_NODE;
	}

	/**
	 * 将w3c的Document对象转为dom4j中的Document对象
	 */
	public static org.dom4j.Document xercesToDom4j(org.w3c.dom.Document doc) {
		return toDom4jDocument(toString(doc));
	}

	/**
	 * 解析XML字符串，转为dom4j中的Document对象
	 */
	public static org.dom4j.Document toDom4jDocument(String content) {
		try {
			SAXReader reader = new SAXReader(false);
			return reader.read(new StringReader(content));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 解析XML字符串，转为w3c的Document对象
	 */
	public static org.w3c.dom.Document htmlToXercesDocument(String content) {
		try {
			DOMParser parser = new DOMParser();
			parser.parse(new InputSource(new StringReader(content)));
			return parser.getDocument();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
}
