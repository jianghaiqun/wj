package com.sinosoft.framework.utility;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * ClassName: XmlMapUtils <br/>
 * Function: xmlMap 互转. <br/>
 * date: 2016年5月25日 上午8:55:15 <br/>
 *
 * @author wwy
 * @version
 */
public class XmlMapUtils {

	public static String ENCODING = "UTF-8";
	public static String ROOT_ELEMENT = "Data";
	/**
	 * map2xmlBody:根据Map组装xml消息体，值对象仅支持基本数据类型、String、BigInteger、BigDecimal，
	 * 以及包含元素为上述支持数据类型的Map. <br/>
	 *
	 * @author wwy
	 * @param vo
	 * @param rootElement
	 * @return
	 */
	public static String map2xmlBody(Map<String, Object> vo, String rootElement, String encoding) {

		org.dom4j.Document doc = DocumentHelper.createDocument();
		if (StringUtil.isNotEmpty(encoding)) {
			doc.setXMLEncoding(encoding);
		} else {
			doc.setXMLEncoding(ENCODING);
		}
		
		Element body = DocumentHelper.createElement(rootElement);
		doc.add(body);
		__buildMap2xmlBody(body, vo);
		return doc.asXML();
	}

	/**
	 * __buildMap2xmlBody:转xml. <br/>
	 *
	 * @author wwy
	 * @param body
	 * @param vo
	 */
	@SuppressWarnings("unchecked")
	private static void __buildMap2xmlBody(Element body, Map<String, Object> vo) {

		if (vo != null) {
			Iterator<String> it = vo.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				if (StringUtil.isNotEmpty(key)) {
					Object obj = vo.get(key);
					Element element = DocumentHelper.createElement(key);
					if (obj != null) {
						if (obj instanceof java.lang.String) {
							element.setText((String) obj);
						} else {
							if (obj instanceof java.lang.Character || obj instanceof java.lang.Boolean
									|| obj instanceof java.lang.Number
									|| obj instanceof java.math.BigInteger || obj instanceof java.math.BigDecimal) {
								org.dom4j.Attribute attr = DocumentHelper.createAttribute(element, "type", obj.getClass()
										.getCanonicalName());
								element.add(attr);
								element.setText(String.valueOf(obj));
							} else if (obj instanceof java.util.Map) {
								org.dom4j.Attribute attr = DocumentHelper.createAttribute(element, "type",
										java.util.Map.class.getCanonicalName());
								element.add(attr);
								__buildMap2xmlBody(element, (Map<String, Object>) obj);
							} else {
							}
						}
					}
					body.add(element);
				}
			}
		}
	}

	/**
	 * xmlBody2map:xml转Map. <br/>
	 *
	 * @author wwy
	 * @param xml
	 * @param rootElement
	 * @return
	 * @throws DocumentException
	 */
	@SuppressWarnings("rawtypes")
	public static Map xmlBody2map(String xml, String rootElement) throws DocumentException {

		org.dom4j.Document doc = DocumentHelper.parseText(xml);
		Element body = (Element) doc.selectSingleNode("/" + rootElement);
		Map vo = __buildXmlBody2map(body);
		return vo;
	}

	/**
	 * __buildXmlBody2map:转map. <br/>
	 *
	 * @author wwy
	 * @param body
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Map __buildXmlBody2map(Element body) {

		Map vo = new HashMap();
		if (body != null) {
			List<Element> elements = body.elements();
			for (Element element : elements) {
				String key = element.getName();
				if (StringUtil.isNotEmpty(key)) {
					String type = element.attributeValue("type", "java.lang.String");
					String text = element.getText().trim();
					Object value = null;
					if (java.lang.String.class.getCanonicalName().equals(type)) {
						value = text;
					} else if (java.lang.Character.class.getCanonicalName().equals(type)) {
						value = new java.lang.Character(text.charAt(0));
					} else if (java.lang.Boolean.class.getCanonicalName().equals(type)) {
						value = new java.lang.Boolean(text);
					} else if (java.lang.Short.class.getCanonicalName().equals(type)) {
						value = java.lang.Short.parseShort(text);
					} else if (java.lang.Integer.class.getCanonicalName().equals(type)) {
						value = java.lang.Integer.parseInt(text);
					} else if (java.lang.Long.class.getCanonicalName().equals(type)) {
						value = java.lang.Long.parseLong(text);
					} else if (java.lang.Float.class.getCanonicalName().equals(type)) {
						value = java.lang.Float.parseFloat(text);
					} else if (java.lang.Double.class.getCanonicalName().equals(type)) {
						value = java.lang.Double.parseDouble(text);
					} else if (java.math.BigInteger.class.getCanonicalName().equals(type)) {
						value = new java.math.BigInteger(text);
					} else if (java.math.BigDecimal.class.getCanonicalName().equals(type)) {
						value = new java.math.BigDecimal(text);
					} else if (java.util.Map.class.getCanonicalName().equals(type)) {
						value = __buildXmlBody2map(element);
					} else {
					}
					vo.put(key, value);
				}
			}
		}
		return vo;
	}
	
	public static void main(String[] args) throws Exception{

//		Map<String, Object> vo = new HashMap<String, Object>();
//		Map<String, String> inputInfo = new HashMap<String, String>();
//		inputInfo.put("ComCode", "2103");
//		inputInfo.put("TransCode", "00070002");
//
//		inputInfo.put("BussNo", String.valueOf("CC" + NoUtil.getMaxNo("CardCheck", 12)));
//
//		vo.put("InputInfo", inputInfo);
//		String map2xml = map2xmlBody(vo, "Data", "");
//		System.out.println(map2xml);
//
//		System.out.println(xmlBody2map(map2xml, "Data").toString());
	}
}