package com.sinosoft.jdt;

import com.sinosoft.framework.utility.XmlMapUtils;
import com.sinosoft.webservice.ProductWebservice;
import org.dom4j.DocumentException;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class ParseXMLToMapCC {
	private static final Logger logger = LoggerFactory.getLogger(ParseXMLToMapCC.class);

	private static final Properties p = new Properties();
	static {
		try
		{
			InputStream ins = ProductWebservice.class.getResourceAsStream("/productInterface.properties");
			p.load(ins);
			ins = null;
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}
	}
	public static final String STR_PASS_CODE = "000000";
	public static final String STR_NOPASS = "nopass";
	public static final String STR_PASS = "pass";

	/**
	 * cardCheck:小额验签. <br/>
	 *
	 * @author wwy
	 * @param param
	 * @return
	 */
	public Map<String, Object> cardCheck(Map<String, Object> param){

		String docstr = null;
		String encoding = "UTF-8";
		try {
			logger.info("小额验签xml报文生成开始");
			docstr = XmlMapUtils.map2xmlBody(param, XmlMapUtils.ROOT_ELEMENT, encoding);
			logger.info("小额验签xml报文生成结束{}", docstr);
		} catch (Exception e) {
			logger.error("获得小额验签结果异常" + param.toString() + e.getMessage(), e);
			return null;
		}

		InputSource is = null;
		Document doc = null;
		Document excuteResult = null;
		Map<String, Object> returnMap = null;
		try {
			is = new InputSource(new ByteArrayInputStream(docstr.getBytes(encoding)));

			doc = (new SAXBuilder()).build(is);
			String sbServiceURL = p.getProperty("CardCheck");
			//String sbServiceURL = "http://localhost:8081/wjjdt/services/cardCheck?wsdl";
			excuteResult = ServiceClient.execute(doc, "UTF-8", sbServiceURL, "cardCheck");

			// XML转字符串
			Format format = Format.getPrettyFormat();
			format.setEncoding("GBK");// 设置xml文件的字符为GBK，解决中文问题
			XMLOutputter xmlout = new XMLOutputter(format);
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			xmlout.output(excuteResult, bo);
			try {
				returnMap = XmlMapUtils.xmlBody2map(bo.toString(), XmlMapUtils.ROOT_ELEMENT);
			} catch (DocumentException e) {
				logger.error("解析返回xml错误。" + e.getMessage(), e);
			}
			logger.info("解析返回xml完成{}", returnMap);
		} catch (JDOMException e) {
			logger.error(e.getMessage(), e);
			returnMap = null;
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			returnMap = null;
		} catch (NullPointerException e) {
			logger.error(e.getMessage(), e);
			returnMap = null;
		}

		return returnMap;
	}
	
	/**
	 * cardCheck:退保. <br/>
	 *
	 * @author wwy
	 * @param param
	 * @return
	 */
	public Map<String, Object> canceltry(Map<String, Object> param){

		String docstr = null;
		String encoding = "UTF-8";
		try {
			logger.info("退保试算xml报文生成开始");
			docstr = XmlMapUtils.map2xmlBody(param, XmlMapUtils.ROOT_ELEMENT, encoding);
			logger.info("退保试算xml报文生成结束");
		} catch (Exception e) {
			logger.error("获得退保试算异常" + param.toString() + e.getMessage(), e);
			return null;
		}

		InputSource is = null;
		Document doc = null;
		Document excuteResult = null;
		Map<String, Object> returnMap = null;
		try {
			is = new InputSource(new ByteArrayInputStream(docstr.getBytes(encoding)));

			doc = (new SAXBuilder()).build(is);
			String sbServiceURL = p.getProperty("CancelTry");
			//String sbServiceURL = "http://localhost:8081/wjjdt/services/cancelTry?wsdl";

			excuteResult = ServiceClient.execute(doc, "UTF-8", sbServiceURL, "cancelTry");

			// XML转字符串
			Format format = Format.getPrettyFormat();
			format.setEncoding("GBK");// 设置xml文件的字符为UTF-8，解决中文问题
			XMLOutputter xmlout = new XMLOutputter(format);
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			xmlout.output(excuteResult, bo);
			try {
				/*if ("Exception".equals(bo.toString())) {
					return PubFun.errMsg("试算异常");
				}*/
				returnMap = XmlMapUtils.xmlBody2map(bo.toString(), XmlMapUtils.ROOT_ELEMENT);
			} catch (DocumentException e) {
				logger.error("解析返回xml错误。" + e.getMessage(), e);
			}
			logger.info("解析返回xml完成");
		} catch (JDOMException e) {
			logger.error(e.getMessage(), e);
			returnMap = null;
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			returnMap = null;
		} catch (NullPointerException e) {
			logger.error(e.getMessage(), e);
			returnMap = null;
		}

		return returnMap;
	}
}
