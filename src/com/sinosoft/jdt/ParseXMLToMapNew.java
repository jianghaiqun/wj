package com.sinosoft.jdt;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.utility.LogUtil;
import com.sinosoft.framework.utility.StringUtil;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.xml.sax.InputSource;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.utility.LogUtil;
import com.sinosoft.framework.utility.StringUtil;

public class ParseXMLToMapNew
{
	private static final Logger logger = LoggerFactory.getLogger(ParseXMLToMapNew.class);
	
	public static final String STR_PASS_CODE = "000000";
	public static final String STR_NOPASS = "nopass";
	public static final String STR_PASS = "pass";
	/**
	 * @param args 
	 */
	public static void main(String[] args)
	{
		//承保测试
		//new com.sinosoft.jdt.InsureTransfer().callInsTransInterface("1015", "3013200011111152");
		
		ParseXMLToMapNew tParseXMLToMap = new ParseXMLToMapNew();
		//核保测试
		HashMap<String, Object> map = tParseXMLToMap.dealData("01", "1015", "3013200011111152","");
		//二次核保测试
		//HashMap<String, Object> map = tParseXMLToMap.dealData("0102", "1015", "3013200011111152");
		//承保测试
		//HashMap<String, Object> map = tParseXMLToMap.dealData("09", "1015", "3013200011111152");
		
		java.util.Set<String> keys = map.keySet();
		java.util.Iterator<String> iterator = keys.iterator();
		while (iterator.hasNext())
		{
			String key = iterator.next();
		}
		
		
		/*
		//以下为已知发送报文内容的核保测试
		java.io.FileInputStream fis = null;
		InputSource is = null;
		Document doc = null;
		try
		{
			fis = new java.io.FileInputStream("g:/a.xml");
			is = new InputSource(fis);
			doc = (new SAXBuilder()).build(is);
			
			StringBuffer sbMethodName = new StringBuffer();
			StringBuffer sbServiceURL = new StringBuffer();
			
			tParseXMLToMap.getServiceURLAndMethod("01", "2011", sbMethodName, sbServiceURL);
			
			//转换xml为map
			Document excuteResult = ServiceClient.execute(doc, "UTF-8", sbServiceURL.toString(), sbMethodName.toString());
			
			XMLOutputter xmlOutput = new XMLOutputter();
			if (null != doc)
			{
				xmlOutput.output(doc, new PrintWriter(System.out));
			}
			if (null != excuteResult)
			{
				xmlOutput.output(excuteResult, new PrintWriter(System.out));
			}
		}
		catch (JDOMException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		if (null != fis)
		{
			try { fis.close(); } catch (IOException e) {}
		}
		*/
	}
	/** 
	 * 进行核保或承保的xml处理
	 * 方法重写支持多被保人循环发送报文
	 *
	 */
	public HashMap<String, Object> dealData(String BusinessLogic,String ManageCom,String OrderNo,String strInsuredSn)
	{
		//生成待发送报文
		
		/* 保存编码 */
		String[] encoding = new String[1];
		String docstr = null;
		try {
			logger.info("{} xml报文生成开始", strInsuredSn);
			/* zhangjinquan 11180 修改为新接口处理 2012-11-07 */
			docstr = new PacketXmlProducerNew().generatePacket(BusinessLogic, ManageCom, OrderNo, encoding,strInsuredSn);
			logger.info("{} xml报文生成结束", strInsuredSn);
		} catch (Exception e) {
			logger.error("获得核保成功发送报文失败：核保承保标志="+
					BusinessLogic+",保险公司编号="+ManageCom+",订单号="+OrderNo + e.getMessage(), e);
			return null;
		}
		
		InputSource is = null;
		Document doc = null;
		Document excuteResult = null;
		HashMap<String, Object> returnMap = null;
		try {
			is = new InputSource(new ByteArrayInputStream(docstr.getBytes(encoding[0])));
			
			doc = (new SAXBuilder()).build(is);
			
			StringBuffer sbMethodName = new StringBuffer();
			StringBuffer sbServiceURL = new StringBuffer();
			
			/* 获得核保或承包的接口信息 */
			this.getServiceURLAndMethod(BusinessLogic, ManageCom, sbMethodName, sbServiceURL);
			
			excuteResult = ServiceClient.execute(doc, "UTF-8", sbServiceURL.toString(), sbMethodName.toString(),ManageCom);
			logger.info("{} 保险公司返回xml完成", OrderNo);

			/* zhangjinquan 11180 修改处理方法，不再将xml转化为map 2012-11-08 */
			returnMap = this.getSpecfiyDataFromXml(BusinessLogic, ManageCom, excuteResult);
			logger.info("{}解析返回xml完成", OrderNo);
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
		
		if ((null == returnMap) || (STR_NOPASS.equals(returnMap.get("passFlag"))))
		{
			String[] argArr = {BusinessLogic, ManageCom, OrderNo};
			logger.error("核保承保前后发生错误：核保承保标志={},保险公司编号={},订单号={}", argArr);
			try
			{
				XMLOutputter xmlOutput = new XMLOutputter();
				if (null != doc)
				{
					xmlOutput.output(doc, new PrintWriter(System.out));
				}
				if (null != excuteResult)
				{
					xmlOutput.output(excuteResult, new PrintWriter(System.out));
				}
			}
			catch (IOException e) {}
		}
		
		return returnMap;
	}
	/**
	 * 根据保险公司获取服务地址ServiceURL和方法名MethodName
	 * @return
	 * @author zhangjinquan 11180
	 * @createDate 2012-11-05
	 */
	private boolean getServiceURLAndMethod(String BusinessLogic, String stManageCom, StringBuffer sbMethodName, StringBuffer sbServiceURL)
	{
		// 调用保险公司接口进行核保
		if ("01".equals(BusinessLogic))
		{
			/* zhangjinquan 11180 目前太平洋财险2011和泰康1015都是该规则，如果后续有新的规则，则需要增加判断，例如 if ("2011".equals(stManageCom)) 2012-11-05 */
			sbMethodName.append("service");
			sbServiceURL.append(Config.interfaceMap.get("InsureTransfer").toString());
		}
		// 调用保险公司接口进行承保
		else if ("09".equals(BusinessLogic))
		{
			/* zhangjinquan 11180 目前太平洋财险2011和泰康1015都是该规则，如果后续有新的规则，则需要增加判断，例如 if ("2011".equals(stManageCom)) 2012-11-05 */
			sbMethodName.append("Insurance");
			sbServiceURL.append(Config.interfaceMap.get("Underwriting").toString());
		} else if ("02".equals(BusinessLogic)) {
			/* 撤单功能*/
			sbMethodName.append("DataUpload");
			sbServiceURL.append(Config.interfaceMap.get("CancelContService").toString());
		}
		/* 调用保险公司接口进行二次核保 */
		else if ("0102".equals(BusinessLogic))
		{
			/* zhangjinquan 11180 目前泰康二次核保使用该规则（从会员中心点击继续支付进行的核保） */
			sbMethodName.append("other");
			sbServiceURL.append(Config.interfaceMap.get("SecondUW").toString());
		}
		else
		{
			sbMethodName.append("service");
			sbServiceURL.append(Config.interfaceMap.get("InsureTransfer").toString());
		}
		
		return true;
	}
	
	private HashMap<String, String> getDataFromXmlMap(HashMap<String, Object> xmlMap)
	{
		HashMap<String, String> resultMap = new HashMap<String, String>();
		/* 取得xml文件的根结点 */

		HashMap<String, Object> rootMap = (HashMap<String, Object>)xmlMap.get("SQTBRequestXml");
		if (null == rootMap)
		{
			throw new NullPointerException();
		}
		
		HashMap<String, Object> headerMap = (HashMap<String, Object>)rootMap.get("Header");
		if (null == headerMap)
		{
			throw new NullPointerException();
		}
		
		/* 取得返回结果编码 */
		String passStr = STR_PASS_CODE.equals((String)headerMap.get("PA_RSLT_CODE"))? STR_PASS : STR_NOPASS;
		
		/* 取得返回结果信息 */
		String resultMsg = (String)headerMap.get("PA_RSLT_MESG");
		
		/* 保存通过标记, 000000表示成功 */
		resultMap.put("passFlag", passStr);
		
		/* 保存结果信息 */
		resultMap.put("rtnMessage", resultMsg);
		
		String thirdPartyOrderNo = null;
		
		String thirdPartySeqNo = null;
		
		String applyPolicyNo = null;
		
		String policyNo = null;
		
		String totalPremium = null;
		
		try
		{
			HashMap<String, Object> policyInfoMap = (HashMap<String, Object>)((HashMap<String, Object>)rootMap.get("Response")).get("policyInfo");
			if (null == policyInfoMap)
			{
				throw new NullPointerException();
			}
			
			/* 取得第三方系统订单号 */
			thirdPartyOrderNo = (String)policyInfoMap.get("OrderNo");
			
			/* 取得第三方系统内部流水号 */
			thirdPartySeqNo = (String)policyInfoMap.get("PayAppNo");
			
			// add by guobin 承保使用
			/* 投保单号 */
			applyPolicyNo = (String)policyInfoMap.get("applyPolicyNo");
			/* 保单号 */
			policyNo = (String)policyInfoMap.get("policyNo");
			/* 保单保费 */
			totalPremium = (String)policyInfoMap.get("totalPremium");
			
			resultMap.put("applyPolicyNo", applyPolicyNo);
			resultMap.put("policyNo", policyNo);
			resultMap.put("totalPremium", totalPremium);
			
			/* 取得返回的总金额 */
			String originalAmount = (String)policyInfoMap.get("OrderAmount");
			
			/* 取得返回的折扣金额 */
			String discountAmount = (String)policyInfoMap.get("ActalPayAmount");
			
			/* 保存返回的总金额 */
			resultMap.put("originalAmount", originalAmount);
			
			/* 保存返回的折扣金额 */
			resultMap.put("discountAmount", discountAmount);
			
			/* 保存第三方系统订单号 */
			resultMap.put("tpySysSn", thirdPartyOrderNo);
			/* 保存第三方系统内部流水号 */
			resultMap.put("tpySysPaySn", thirdPartySeqNo);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			/* 如果是不通过则返回map */
			if (STR_NOPASS.equals(passStr))
			{
				return resultMap;
			}
		}
		
		if ((null == passStr) || (null == thirdPartyOrderNo) || (null == thirdPartySeqNo))
		{
			return null;
		}
		
		/* 返回获取的结果信息 */
		return resultMap;
	}

	/**
	 * 根据保险公司编码和保险公司返回的报文，决定获取指定数据的方法
	 * @param ManageCom String类型, 保险公司编码
	 * @param excuteResult org.jdom.Document类型, 保险公司返回的报文
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private HashMap<String, Object> getSpecfiyDataFromXml(String BusinessLogic, String stManageCom, Document excuteResult)
	{
		if (null == excuteResult)
		{
			return null;
		}

		/* 根据保险公司决定调用哪个方法 */
		Method method = null;
		try
		{
			Class<?>[] parameterTypes = new Class[]{String.class, Document.class};
			method = ParseXMLToMapNew.class.getMethod("getXmlDataToMap" + stManageCom, parameterTypes);
			Object[] args = new Object[]{BusinessLogic, excuteResult};
			return (HashMap<String, Object>)(method.invoke(this, args));
		}
		catch (SecurityException e)
		{
			logger.error(e.getMessage(), e);
			return null;
		}
		catch (NoSuchMethodException e)
		{
			/* 默认情况使用的方法 */
			return this.getXmlDataToMap(BusinessLogic, excuteResult);
		}
		catch (IllegalArgumentException e)
		{
			logger.error(e.getMessage(), e);
			return null;
		}
		catch (IllegalAccessException e)
		{
			logger.error(e.getMessage(), e);
			return null;
		}
		catch (InvocationTargetException e)
		{
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	/**
	 * 解析太平洋财险返回的xml，并获取指定数据通过HashMap(String, String)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc
	 * @return
	 */
	public HashMap<String, Object> getXmlDataToMap2011(String BusinessLogic, Document doc) {
		return getXmlDataToMapCommon(BusinessLogic, doc);
	}
	
	/**
	 * 解析泰康返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	public HashMap<String, Object> getXmlDataToMap1015(String BusinessLogic, Document doc)
	{
		return getXmlDataToMapCommon(BusinessLogic, doc);
	}
	
	

	/**
	 * 从保险公司返回的xml报文中读取指定数据，并存入HashMap中返回
	 * @param BusinessLogic 字符串格式，承保核保标记
	 * @param excuteResult org.jdom.Document格式, 从保险公司返回的xml报文
	 * @return 指定的java.util.HashMap<String, String>
	 * @author zhangjinquan 11180
	 */
	private HashMap<String, Object> getXmlDataToMap(String BusinessLogic, Document excuteResult)
	{
		if (null == excuteResult)
		{
			return null;
		}
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		/* 取得xml文件的根结点 */
		Element root = excuteResult.getRootElement();
		
		/* 取得返回结果编码 */
		String passStr = root.getChild("Header").getChildTextTrim("PA_RSLT_CODE");		
		
		Element policyInfo = root.getChild("Response").getChild("policyInfo");
		
		/* 取得第三方系统订单号 */
		String thirdPartyOrderNo = policyInfo.getChildText("OrderNo");
		
		/* 取得第三方系统内部流水号 */
		String thirdPartySeqNo = policyInfo.getChildText("PayAppNo");
		
		/* 保存通过标记, 000000表示成功 */
		hashMap.put("passFlag", STR_PASS_CODE.equals(passStr)? STR_PASS : STR_NOPASS);
		/* 保存第三方系统订单号 */
		hashMap.put("tpySysSn", thirdPartyOrderNo);
		/* 保存第三方系统内部流水号 */
		hashMap.put("tpySysPaySn", thirdPartySeqNo);
		
		/* 返回获取的结果信息 */
		return hashMap;
	}
	
	/**
	 * 解析华泰大连分公司返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	public HashMap<String, Object> getXmlDataToMap2255(String BusinessLogic, Document doc)
	{
		return getXmlDataToMapCommon(BusinessLogic, doc);
	}
	
	/**
	 * 解析华泰返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	public HashMap<String, Object> getXmlDataToMap2023(String BusinessLogic, Document doc)
	{
		return getXmlDataToMapCommon(BusinessLogic, doc);
	}
	/**
	 * 解析丰泰返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	public HashMap<String, Object> getXmlDataToMap2096(String BusinessLogic, Document doc)
	{
		return getXmlDataToMapCommon(BusinessLogic, doc);
	}
	/**
	 * 解析大众返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	public HashMap<String, Object> getXmlDataToMap2071(String BusinessLogic, Document doc)
	{
		return getXmlDataToMapCommon(BusinessLogic, doc);
	}
	/**
	 * 解析合众返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	public HashMap<String, Object> getXmlDataToMap1061(String BusinessLogic, Document doc)
	{
		return getXmlDataToMapCommon(BusinessLogic, doc);
	}
	/**
	 * 解析合众返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	public HashMap<String, Object> getXmlDataToMap1018(String BusinessLogic, Document doc)
	{
		return getXmlDataToMapCommon(BusinessLogic, doc);
//		resultMap.put("BDZT", elePolicyInfo.getChildTextTrim("BDZT"));
	}
	/**
	 * 解析平安返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	public HashMap<String, Object> getXmlDataToMap2007(String BusinessLogic, Document doc)
	{
		return getXmlDataToMapCommon(BusinessLogic, doc);
	}
	/**
	 * 解析大都会返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	public HashMap<String, Object> getXmlDataToMap1048(String BusinessLogic, Document doc) {
		return getXmlDataToMapCommon(BusinessLogic, doc);
	}
	/**
	 * 解析昆仑返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	public HashMap<String, Object> getXmlDataToMap1065(String BusinessLogic, Document doc) {
		return getXmlDataToMapCommon(BusinessLogic, doc);
	}
	/**
	 * heyang 2013-06-19
	 * 解析阳光返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	public HashMap<String, Object> getXmlDataToMap1087(String BusinessLogic, Document doc) {
		return getXmlDataToMapCommon(BusinessLogic, doc);
	}
	/**
	 * heyang 2013-06-26
	 * 解析中国人寿返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	public HashMap<String, Object> getXmlDataToMap1001(String BusinessLogic, Document doc) {
		return getXmlDataToMapCommon(BusinessLogic, doc);
	}
	/**
	 * heyang 2013-07-16
	 * 解析华安财险返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	public HashMap<String, Object> getXmlDataToMap2043(String BusinessLogic, Document doc) {
		return getXmlDataToMapCommon(BusinessLogic, doc);
	}
	/**
	 * heyang 2013-07-25
	 * 解析人保寿险返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	public HashMap<String, Object> getXmlDataToMap1004(String BusinessLogic, Document doc) {
		return getXmlDataToMapCommon(BusinessLogic, doc);
	}
	/**
	 * heyang 2013-08-1
	 * 解析都邦返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	public HashMap<String, Object> getXmlDataToMap2042(String BusinessLogic, Document doc) {
		return getXmlDataToMapCommon(BusinessLogic, doc);
	}
	/**
	 * 解析华泰返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	public HashMap<String, Object> getXmlDataToMap0005(String BusinessLogic, Document doc)
	{
		return getXmlDataToMapCommon(BusinessLogic, doc);
		
	}
	/**
	 * heyang 2013-09-10
	 * 解析平安养老返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	public HashMap<String, Object> getXmlDataToMap1010(String BusinessLogic, Document doc) {
		return getXmlDataToMapCommon(BusinessLogic, doc);
	}
	/**
	 * heyang 2013-09-23
	 * 解析新华返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	public HashMap<String, Object> getXmlDataToMap1014(String BusinessLogic, Document doc) {
		return getXmlDataToMapCommon(BusinessLogic, doc);
	}
	/**
	 * heyang 2013-10-22
	 * 解析太阳联合返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	public HashMap<String, Object> getXmlDataToMap2074(String BusinessLogic, Document doc) {
		return getXmlDataToMapCommon(BusinessLogic, doc);
	}
	/**
	 * heyang 2013-12-02
	 * 解析国华返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	
//	@SuppressWarnings("unchecked")
//	public HashMap<String, Object> getXmlDataToMap1092(String BusinessLogic, Document doc) {
//		HashMap<String, Object> resultMap = new HashMap<String, Object>();
//		/* 取得xml文件的根结点 */
//		Element root = doc.getRootElement();
//		if (null == root) {
//			throw new NullPointerException(BusinessLogic + "返回报文没有根节点！");
//		}
//		/* 取得header部分 */
//		Element eleHeader = root.getChild("Header");
//		if (null == eleHeader) {
//			throw new NullPointerException();
//		}
//		/* 取得Response子节点 */
//		Element eleResponse = root.getChild("Response");
//		if (null == eleResponse) {
//			Element eleErrCode = eleHeader.getChild("PA_RSLT_CODE");
//			Element eleErrMsg = eleHeader.getChild("PA_RSLT_MESG");
//			if ((eleErrCode!= null) && (eleErrMsg!=null)) {
//				/* 保存通过标记, 000000表示成功 */
//				resultMap.put("passFlag", STR_NOPASS);
//				/* 保存结果信息 */
//				resultMap.put("rtnMessage", eleErrCode.getTextTrim() + ":" + eleErrMsg.getTextTrim());
//				return resultMap;
//			}
//			throw new NullPointerException(BusinessLogic + "返回报文没有Response节点！");
//		} else if (eleResponse.getContentSize()==0) {
//			throw new NullPointerException(BusinessLogic + "返回报文Response节点缺少子节点！");
//		}
//		List<Element> listorderInfo = eleResponse.getChildren("OrderInfo");
//		int iorderInfoSize = listorderInfo.size();
//		if (0 == iorderInfoSize) {
//			throw new NullPointerException(BusinessLogic + "返回报文没有orderInfo节点！");
//		}
//		Element eleOrderInfo = (Element)listorderInfo.get(0);
//		if (eleOrderInfo.getContentSize() == 0) {
//			throw new NullPointerException(BusinessLogic + "返回报文orderInfo缺少子节点！");
//		}		
//		List<Element> listPolicyInfo = eleOrderInfo.getChildren("policyInfo");
//		int iPolicyInfoSize = listPolicyInfo.size();
//		if (0 == iPolicyInfoSize) {
//			throw new NullPointerException(BusinessLogic + "返回报文没有policyInfo节点！");
//		}
//		Element elePolicyInfo = (Element)listPolicyInfo.get(0);
//		if (elePolicyInfo.getContentSize() == 0) {
//			throw new NullPointerException(BusinessLogic + "返回报文policyInfo缺少子节点！");
//		}
//		StringBuffer sbErrMsg = new StringBuffer();
//		String resultCode = elePolicyInfo.getChildTextTrim("MessageStatusSubCode");
//		String resultMsg = elePolicyInfo.getChildTextTrim("MessageStatusSubDescription");
//		if (STR_PASS_CODE.equals(resultCode)) {
//			if (iPolicyInfoSize > 1) {
//				/* 保存通过标记, 000000表示成功 */
//				resultMap.put("passFlag", STR_NOPASS);   
//				/* 保存结果信息 */
//				resultMap.put("rtnMessage", "返回信息有误！");  
//				return resultMap;
//			}
//			resultMap.put("passFlag", STR_PASS);
//			sbErrMsg.append(resultMsg);
//		} else {
//			resultMap.put("passFlag", STR_NOPASS);  
//			sbErrMsg.append(resultMsg);
//		}
//		/* 保存保单号 */
//		resultMap.put("BK_SERIAL", eleHeader.getChildTextTrim("BK_SERIAL"));
//		resultMap.put("PA_RSLT_CODE", eleHeader.getChildTextTrim("PA_RSLT_CODE"));
//		resultMap.put("TpySysSn",eleOrderInfo.getChildTextTrim("OrderNo"));
//		resultMap.put("applyPolicyNo",elePolicyInfo.getChildTextTrim("applyPolicyNo"));
//		resultMap.put("policyNo",elePolicyInfo.getChildTextTrim("policyNo"));
//		resultMap.put("totalPremium",elePolicyInfo.getChildTextTrim("totalPremium"));
//		resultMap.put("rtnMessage", sbErrMsg.toString());
//		return resultMap;  
//	}
	/**
	 * heyang 2013-10-22
	 * 解析太阳联合返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	public HashMap<String, Object> getXmlDataToMap2034(String BusinessLogic, Document doc) {
		return getXmlDataToMapCommon(BusinessLogic, doc);
	}
	
	/**
	 * wangchangyang 2014-01-02
	 * 解析安联返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	public HashMap<String, Object> getXmlDataToMap2049(String BusinessLogic, Document doc) {
		return getXmlDataToMapCommon(BusinessLogic, doc);
	}
	
	/**
	 * 解析富德返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	public HashMap<String, Object> getXmlDataToMap2100(String BusinessLogic, Document doc)
	{
		return getXmlDataToMapCommon(BusinessLogic, doc);
	}
	
	/**
	 * 解析路华返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	public HashMap<String, Object> getXmlDataToMap1099(String BusinessLogic, Document doc)
	{
		return getXmlDataToMapCommon(BusinessLogic, doc);
	}
	
	/**
	 * 解析新华返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	public HashMap<String, Object> getXmlDataToMap1036(String BusinessLogic, Document doc) {
		return getXmlDataToMapCommon(BusinessLogic, doc);
	}
	
	/**
	 * 解析民生返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	public HashMap<String, Object> getXmlDataToMap1038(String BusinessLogic, Document doc) {
		return getXmlDataToMapCommon(BusinessLogic, doc);
	}
	
	/**
	 * 解析中国人寿(青岛分公司)返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	public HashMap<String, Object> getXmlDataToMap1002(String BusinessLogic, Document doc) {
		return getXmlDataToMapCommon(BusinessLogic, doc);
	}
	
	/**
	 * 解析太平网销返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	public HashMap<String, Object> getXmlDataToMap0007(String BusinessLogic, Document doc) {
		return getXmlDataToMapCommon(BusinessLogic, doc);
	}
	
	/**
	 * 解析永安返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	public HashMap<String, Object> getXmlDataToMap2046(String BusinessLogic, Document doc)
	{
		return getXmlDataToMapCommon(BusinessLogic, doc);
	}
	
	/**
	 * 解析人保财险返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	public HashMap<String, Object> getXmlDataToMap2101(String BusinessLogic, Document doc)
	{
		return getXmlDataToMapCommon(BusinessLogic, doc);
	}
	
	/**
	 * 解析百年理财险返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	public HashMap<String, Object> getXmlDataToMap2103(String BusinessLogic, Document doc) {
		return getXmlDataToMapCommon(BusinessLogic, doc);
	}
	
	/**
	 * 
	 * getXmlDataToMap2104:(这里用一句话描述这个方法的作用). <br/>
	 *
	 * @author taoqiwen
	 * @param BusinessLogic
	 * @param doc
	 * @return
	 */
	public HashMap<String, Object> getXmlDataToMap2104(String BusinessLogic, Document doc) {
		return getXmlDataToMapCommon(BusinessLogic, doc);
	}
	
	
	/**
	 * 
	 * getXmlDataToMap2103:(这里用一句话描述这个方法的作用). <br/>
	 *
	 * @author taoqiwen
	 * @param BusinessLogic
	 * @param doc
	 * @return
	 */
	public HashMap<String, Object> getXmlDataToMap2015 (String BusinessLogic, Document doc) {
	
		return getXmlDataToMapCommon(BusinessLogic, doc);
	}

	/**
	 * 解析泰康返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	public HashMap<String, Object> getXmlDataToMap2105(String BusinessLogic, Document doc){
		return getXmlDataToMapCommon(BusinessLogic, doc);
	}
	
	/**
	 * 解析人保财险返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	public HashMap<String, Object> getXmlDataToMap2217(String BusinessLogic, Document doc)
	{
		return getXmlDataToMapCommon(BusinessLogic, doc);
	}
 
	
	/**
	 * 
	 * getXmlDataToMap2216:(这里用一句话描述这个方法的作用). <br/>
	 *
	 * @author taoqiwen
	 * @param BusinessLogic
	 * @param doc
	 * @return
	 */
	public HashMap<String, Object> getXmlDataToMap2216(String BusinessLogic, Document doc)
	{
		return getXmlDataToMapCommon(BusinessLogic, doc);
	}
	
	/**
	 * 解析泰康返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	public HashMap<String, Object> getXmlDataToMap2220(String BusinessLogic, Document doc){
		return getXmlDataToMapCommon(BusinessLogic, doc);
	}
	
	public HashMap<String, Object> getXmlDataToMap2021 (String BusinessLogic, Document doc) {
		return getXmlDataToMapCommon(BusinessLogic, doc);
	}
	
	public HashMap<String, Object> getXmlDataToMap2218(String BusinessLogic, Document doc)
	{
		return getXmlDataToMapCommon(BusinessLogic, doc);
	}

	/**
	 * heyang 2013-07-16
	 * 解析华安财险返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	public HashMap<String, Object> getXmlDataToMap2232(String BusinessLogic, Document doc) {
		return getXmlDataToMapCommon(BusinessLogic, doc);
	}
	
	/**
	 * 解析诚泰保险返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	public HashMap<String, Object> getXmlDataToMap2233(String BusinessLogic, Document doc)
	{
		return getXmlDataToMapCommon(BusinessLogic, doc);
	}
	
	/**
	 * 解析中美大都会返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	public HashMap<String, Object> getXmlDataToMap2236(String BusinessLogic, Document doc){
		return getXmlDataToMapCommon(BusinessLogic, doc);
	}
	
	/**
	 * 解析安心新平台返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	public HashMap<String, Object> getXmlDataToMap2239(String BusinessLogic, Document doc){
		return getXmlDataToMapCommon(BusinessLogic, doc);
	}
	/**
	 * 解析易安返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保、退保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	public HashMap<String, Object> getXmlDataToMap2238(String BusinessLogic, Document doc)
	{
		return getXmlDataToMapCommon(BusinessLogic, doc);
	}
	/**
	 * getXmlDataToMap2237:航意险报文解析. <br/>
	 *
	 * @author dongsheng
	 * @param BusinessLogic
	 * @param doc
	 * @return
	 */
	public HashMap<String, Object> getXmlDataToMap2237(String BusinessLogic, Document doc) {
		return getXmlDataToMapCommon(BusinessLogic, doc);
	}
	/**
	 * 解析永安返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	public HashMap<String, Object> getXmlDataToMap2252(String BusinessLogic, Document doc)
	{
		return getXmlDataToMapCommon(BusinessLogic, doc);
	}
	/**
	 * 解析永安返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	public HashMap<String, Object> getXmlDataToMap2251(String BusinessLogic, Document doc)
	{
		return getXmlDataToMapCommon(BusinessLogic, doc);
	}
	/**
	 * 解析永安返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	public HashMap<String, Object> getXmlDataToMap2250(String BusinessLogic, Document doc)
	{
		return getXmlDataToMapCommon(BusinessLogic, doc);
	}
	/**
	 * 解析永安返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	public HashMap<String, Object> getXmlDataToMap2247(String BusinessLogic, Document doc)
	{
		return getXmlDataToMapCommon(BusinessLogic, doc);
	}
	/**
	 * 解析永安返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	public HashMap<String, Object> getXmlDataToMap2242(String BusinessLogic, Document doc)
	{
		return getXmlDataToMapCommon(BusinessLogic, doc);
	}
	/**
	 * 解析平安健康返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	public HashMap<String, Object> getXmlDataToMap2241(String BusinessLogic, Document doc){
		return getXmlDataToMapCommon(BusinessLogic, doc);
	}
	
	/**
	 * 解析百年浙江分公司返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	public HashMap<String, Object> getXmlDataToMap1100(String BusinessLogic, Document doc) {
		return getXmlDataToMapCommon(BusinessLogic, doc);
	}
	
	/**
	 * 解析众安财险返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic
	 * @param doc
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> getXmlDataToMap2249(String BusinessLogic, Document doc)
	{
		return getXmlDataToMapCommon(BusinessLogic, doc);
	}
	/**
	 * 解析百年康惠返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	public HashMap<String, Object> getXmlDataToMap2248(String BusinessLogic, Document doc) {
		return getXmlDataToMapCommon(BusinessLogic, doc);
	}
	/**
	 * 解析百年康赢返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	public HashMap<String, Object> getXmlDataToMap2257(String BusinessLogic, Document doc) {
		return getXmlDataToMapCommon(BusinessLogic, doc);
	}
	/**
	 * 解析人保健康返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	public HashMap<String, Object> getXmlDataToMap2254(String BusinessLogic, Document doc)
	{
		return getXmlDataToMapCommon(BusinessLogic, doc);
	}
	/**
	 * 解析合众返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	public HashMap<String, Object> getXmlDataToMap2258(String BusinessLogic, Document doc)
	{
		return getXmlDataToMapCommon(BusinessLogic, doc);
	}
	/**
	 * 解析中宏返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	public HashMap<String, Object> getXmlDataToMap2260(String BusinessLogic, Document doc)
	{
		return getXmlDataToMapCommon(BusinessLogic, doc);
	}
	/**
	 * 解析返回的E-TRETURN报文的公共方法，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> getXmlDataToMapCommon(String BusinessLogic, Document doc) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		/* 取得xml文件的根结点 */
		Element root = doc.getRootElement();
		if (null == root) {
			throw new NullPointerException(BusinessLogic + "返回报文没有根节点！");
		}
		/* 取得header部分 */
		Element eleHeader = root.getChild("Header");
		if (null == eleHeader) {
			throw new NullPointerException();
		}
		resultMap.put("TRAN_CODE", eleHeader.getChildTextTrim("TRAN_CODE"));// for 2236,2239
		resultMap.put("BK_ACCT_DATE", eleHeader.getChildTextTrim("BK_ACCT_DATE"));// for 2236,2239
		resultMap.put("BK_ACCT_TIME", eleHeader.getChildTextTrim("BK_ACCT_TIME"));// for 2236,2239
		String insureCom = eleHeader.getChildTextTrim("INSURENCE_CODE");//for 1065
		/* 取得Response子节点 */
		if(!"02".equals(BusinessLogic)) {
			Element eleResponse = root.getChild("Response");
			if (null == eleResponse || eleResponse.getContentSize()==0) {
				resultMap.put("passFlag", STR_NOPASS);/* 保存通过标记, 000000表示成功 */
				resultMap.put("rtnMessage", "报文格式异常：报文无Response节点或Response节点为空,请联系客服！");/* 保存结果信息 */
				return resultMap;
			}
			List<Element> listOrderInfo = eleResponse.getChildren("OrderInfo");
			if(null == listOrderInfo || 0 == listOrderInfo.size()){
				resultMap.put("passFlag", STR_NOPASS);/* 保存通过标记, 000000表示成功 */
				resultMap.put("rtnMessage", "报文格式异常：Response节点无OrderInfo节点,请联系客服！");/* 保存结果信息 */
				return resultMap;
			}
			Element eleOrderInfo = listOrderInfo.get(0);
			List<Element> listPolicyInfo = eleOrderInfo.getChildren("policyInfo");
			int iPolicyInfoSize = listPolicyInfo.size();
			if (null == listPolicyInfo || 0 == listPolicyInfo.size()) {
				resultMap.put("passFlag", STR_NOPASS);/* 保存通过标记, 000000表示成功 */
				resultMap.put("rtnMessage", "报文格式异常：OrderInfo节点无policyInfo节点,请联系客服！");/* 保存结果信息 */
				return resultMap;
			}
			Element elePolicyInfo = (Element)listPolicyInfo.get(0);
			if (elePolicyInfo.getContentSize() == 0) {
				resultMap.put("passFlag", STR_NOPASS);/* 保存通过标记, 000000表示成功 */
				resultMap.put("rtnMessage", "报文格式异常：返回报文policyInfo缺少子节点,请联系客服！");/* 保存结果信息 */
				return resultMap;
			}
			StringBuffer sbErrMsg = new StringBuffer();
			String resultCode = eleHeader.getChildTextTrim("PA_RSLT_CODE");
			String resultMsg = eleHeader.getChildTextTrim("PA_RSLT_MESG");
			String messageStatusSubCode = elePolicyInfo.getChildTextTrim("MessageStatusSubCode");
			String messageStatusSubDescription = elePolicyInfo.getChildTextTrim("MessageStatusSubDescription");
			
			if (STR_PASS_CODE.equals(resultCode)) {
				if (iPolicyInfoSize > 1) {
					resultMap.put("passFlag", STR_NOPASS);   /* 保存通过标记, 000000表示成功 */
					resultMap.put("rtnMessage", "报文格式异常：PolicyInfo节点数量不符合报文规范！");  /* 保存结果信息 */
					return resultMap;
				}
				resultMap.put("passFlag", STR_PASS);
				sbErrMsg.append(resultMsg);
				if("01".equals(BusinessLogic) || "03".equals(BusinessLogic)) {
					
					String orderNo = eleOrderInfo.getChildTextTrim("OrderNo");
					String applyPolicyNo = elePolicyInfo.getChildTextTrim("applyPolicyNo");
					resultMap.put("TpySysSn", StringUtil.isNotEmpty(orderNo)?orderNo:applyPolicyNo);// for 1014(OrderNo),1036(OrderNo),2236(applyPolicyNo)
					resultMap.put("applyPolicyNo",StringUtil.isNotEmpty(applyPolicyNo)?applyPolicyNo:orderNo);
					String payAppNo = elePolicyInfo.getChildTextTrim("PayAppNo");
					resultMap.put("tpySysPaySn", StringUtil.isNotEmpty(payAppNo)?payAppNo:eleHeader.getChildTextTrim("BK_SERIAL"));//for 2011(PayAppNo),2220(BK_SERIAL),2239(BK_SERIAL) /* 第三方系统内部流水号 */
					resultMap.put("TpySysPaySn",elePolicyInfo.getChildTextTrim("applyPolicyNo"));// for 1014,2104
					resultMap.put("totalPremium",elePolicyInfo.getChildTextTrim("totalPremium"));// for 1014,1036,2007,2049
					resultMap.put("tpySysSn", elePolicyInfo.getChildTextTrim("OrderNo"));//for 2011,2104 /* 第三方系统订单号 */
					resultMap.put("discountAmount", elePolicyInfo.getChildTextTrim("ActalPayAmount"));//for 2011 /* 第三方系统订单优惠后金额 */
					resultMap.put("originalAmount", elePolicyInfo.getChildTextTrim("OrderAmount"));//for 2011/* 第三方系统订单金额 */
					resultMap.put("startDate",elePolicyInfo.getChildTextTrim("startDate"));//for 2239
					resultMap.put("endDate",elePolicyInfo.getChildTextTrim("endDate"));//for 2239
					//一步交易保险公司数据保存
					resultMap.put("policyNo",elePolicyInfo.getChildTextTrim("policyNo"));//一步交易保存保单号
					resultMap.put("policyPath", elePolicyInfo.getChildTextTrim("policyUrl"));//一步交易外部电子保单url
					resultMap.put("noticeNo", elePolicyInfo.getChildTextTrim("noticeNo"));//1100,2007,2021
					resultMap.put("validateCode", elePolicyInfo.getChildTextTrim("validateCode"));//2007
					resultMap.put("tpySn", eleHeader.getChildTextTrim("BK_SERIAL"));//2007
					//易安返回的订单信息 退保使用
					resultMap.put("orderCode", eleOrderInfo.getChildTextTrim("OrderNo"));//2238
					resultMap.put("orderExt", eleOrderInfo.getChildTextTrim("PayAppNo"));//2238
					
				}else if("09".equals(BusinessLogic)){
					resultMap.put("tpySn",eleHeader.getChildTextTrim("TRAN_CODE"));// for 1065
					resultMap.put("policyNo",elePolicyInfo.getChildTextTrim("policyNo"));
					resultMap.put("policyUrl",elePolicyInfo.getChildTextTrim("policyUrl"));
					resultMap.put("policyPath", elePolicyInfo.getChildTextTrim("electronic"));// for 1004,1014,1065,2104,2241
					resultMap.put("noticeNo", elePolicyInfo.getChildTextTrim("noticeNo"));// for 1014,2104
					resultMap.put("totalPremium", elePolicyInfo.getChildTextTrim("totalPremium"));//for 2011 /* 保单总保费 */
					resultMap.put("startDate",elePolicyInfo.getChildTextTrim("startDate"));// for 2220
					resultMap.put("endDate",elePolicyInfo.getChildTextTrim("endDate"));// for 2220
				}
			} else {
				resultMap.put("passFlag", STR_NOPASS);  
				sbErrMsg.append(messageStatusSubCode);
				sbErrMsg.append(":");
				sbErrMsg.append(messageStatusSubDescription);
				if("1065".equals(insureCom)){
					String applyPolicyNo = elePolicyInfo.getChildTextTrim("applyPolicyNo");
					resultMap.put("applyPolicyNo",applyPolicyNo);
				}
			}
			resultMap.put("BK_SERIAL", eleHeader.getChildTextTrim("BK_SERIAL"));
			resultMap.put("PA_RSLT_CODE", messageStatusSubCode);
			resultMap.put("PA_RSLT_MESG", messageStatusSubDescription);
			resultMap.put("rtnMessage", sbErrMsg.toString());
		}else{
			String flag = eleHeader.getChildTextTrim("PA_RSLT_CODE");
			if(StringUtil.isNotEmpty(flag) && "000000".equals(flag)){
				resultMap.put("passFlag", STR_PASS);
			}else{
				resultMap.put("passFlag", STR_NOPASS);  
			}
			resultMap.put("rtnMessage", flag+":"+eleHeader.getChildTextTrim("PA_RSLT_MESG"));   
		}
		return resultMap;  
	}
	
}
