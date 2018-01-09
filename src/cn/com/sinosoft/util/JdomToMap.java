package cn.com.sinosoft.util;

import org.jdom.*;
import org.jdom.input.*;
import java.io.*;
import java.util.*;

/**
 * 
 * 本类实现对XML的解析操作
 */
public class JdomToMap {
	public JdomToMap() {
		// 构造函数
	}

	public static void main(String[] args) {
		String ss = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><SQTBRequestXml><Header1><TRAN_CODE>8021</TRAN_CODE></Header1><Header><TRAN_CODE>8021</TRAN_CODE><BK_ACCT_DATE>2012-10-16</BK_ACCT_DATE><BK_ACCT_TIME>13:56:09</BK_ACCT_TIME><PA_RSLT_CODE>111111</PA_RSLT_CODE><PA_RSLT_MESG>TransactionEffectiveDate节点的值不能为空.</PA_RSLT_MESG></Header></SQTBRequestXml>";
		try {
			HashMap map1 = XMLToMap(ss);
//			System.out.println(map1.size());

		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解析XML到HashMap
	 * 
	 * @param doc
	 * @return
	 */
	public static HashMap<String, Object> XMLToMap(Document doc) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		Element root = doc.getRootElement();
		result.put(root.getName(), elementToMap(root.getChildren()));
		return result;
	}

	/**
	 * 解析XML到HashMap
	 * 
	 * @param xml String:传入的XML
	 * @return HashMap
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static HashMap<String, Object> XMLToMap(String xml) throws JDOMException, IOException {
		HashMap<String, Object> result = new HashMap<String, Object>();
		InputStream in = new ByteArrayInputStream(xml.getBytes("utf-8"));
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(in);
		Element root = doc.getRootElement();
		result.put(root.getName(), elementToMap(root.getChildren()));
		return result;
	}

	/**
	 * 把XML的内容的节点取出，转换成 Name = Value (键值对)的形式
	 */
	private static HashMap<String, Object> elementToMap(List list) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		for (int i = 0; i < list.size(); i++) {
			Element emt = (Element) list.get(i);
			if (emt.getTextTrim() != null && emt.getTextTrim().length() > 0) {
				result.put(emt.getName(), emt.getTextTrim());
			}
			List listChildern = emt.getChildren();
			if (listChildern.size() > 0) {
				result.put(emt.getName(), elementToMap(listChildern));
			}
		}
		return result;
	}

}