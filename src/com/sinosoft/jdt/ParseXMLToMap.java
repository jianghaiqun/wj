package com.sinosoft.jdt;

import com.sinosoft.webservice.ProductWebservice;
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
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class ParseXMLToMap
{
	private static final Logger logger = LoggerFactory.getLogger(ParseXMLToMap.class);

	private static final Properties p = new Properties();
	static
	{
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
	 * @param args
	 */
	public static void main(String[] args)
	{
		//承保测试
		//new com.sinosoft.jdt.InsureTransfer().callInsTransInterface("1015", "3013200011111152");
		
		ParseXMLToMap tParseXMLToMap = new ParseXMLToMap();
		//核保测试
		HashMap<String, Object> map = tParseXMLToMap.dealData("01", "1015", "3013200011111152");
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
			logger.error(e.getMessage(), e);
		}
		catch (IOException e)
		{
			logger.error(e.getMessage(), e);
		}
		if (null != fis)
		{
			try { fis.close(); } catch (IOException e) {}
		}
		*/
	}
	/** 
	 * 进行核保或承保的xml处理
	 * @param BusinessLogic: 核保或承保标记
	 * @param ManageCom: 保险公司编码
	 * @param OrderNo: 订单编号
	 *
	 */
	public HashMap<String, Object> dealData(String BusinessLogic,String ManageCom,String OrderNo)
	{
		//生成待发送报文
		
		/* 保存编码 */
		String[] encoding = new String[1];
		
		String docstr = null;
		try
		{
			logger.info("{} xml报文生成开始", OrderNo);
			/* zhangjinquan 11180 修改为新接口处理 2012-11-07 */
			docstr = new PacketXmlProducer().generatePacket(BusinessLogic, ManageCom, OrderNo, encoding);
			logger.info("{}xml报文生成结束", OrderNo);
		}
		catch (Exception e)
		{
			logger.error("获得核保成功发送报文失败：核保承保标志="+BusinessLogic+",保险公司编号="+ManageCom+",订单号="+OrderNo + e.getMessage(), e);
			return null;
		}
		
		InputSource is = null;
		Document doc = null;
		Document excuteResult = null;
		HashMap<String, Object> returnMap = null;
		try
		{
			is = new InputSource(new ByteArrayInputStream(docstr.getBytes(encoding[0])));
			
			doc = (new SAXBuilder()).build(is);
			
			StringBuffer sbMethodName = new StringBuffer();
			StringBuffer sbServiceURL = new StringBuffer();
			
			/* 获得核保或承包的接口信息 */
			this.getServiceURLAndMethod(BusinessLogic, ManageCom, sbMethodName, sbServiceURL);
			
			excuteResult = ServiceClient.execute(doc, "UTF-8", sbServiceURL.toString(), sbMethodName.toString());
			logger.info("{} 保险公司返回xml完成", OrderNo);
			
			/* zhangjinquan 11180 修改处理方法，不再将xml转化为map 2012-11-08 */
			returnMap = this.getSpecfiyDataFromXml(BusinessLogic, ManageCom, excuteResult);
			logger.info("{} 解析返回xml完成", OrderNo);
		}
		catch (JDOMException e)
		{
			logger.error(e.getMessage(), e);
			returnMap = null;
		}
		catch (IOException e)
		{
			logger.error(e.getMessage(), e);
			returnMap = null;
		}
		catch (NullPointerException e)
		{
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
			sbServiceURL.append(p.getProperty("InsureTransfer"));
		}
		// 调用保险公司接口进行承保
		else if ("09".equals(BusinessLogic))
		{
			/* zhangjinquan 11180 目前太平洋财险2011和泰康1015都是该规则，如果后续有新的规则，则需要增加判断，例如 if ("2011".equals(stManageCom)) 2012-11-05 */
			sbMethodName.append("Insurance");
			sbServiceURL.append(p.getProperty("Underwriting"));
		} else if ("02".equals(BusinessLogic)) {
			sbMethodName.append("DataUpload");
			sbServiceURL.append(p.getProperty("CancelContService"));
		}

		/* 调用保险公司接口进行二次核保 */
		else if ("0102".equals(BusinessLogic))
		{
			/* zhangjinquan 11180 目前泰康二次核保使用该规则（从会员中心点击继续支付进行的核保） */
			sbMethodName.append("other");
			sbServiceURL.append(p.getProperty("SecondUW"));
		}
		else
		{
			sbMethodName.append("service");
			sbServiceURL.append(p.getProperty("InsureTransfer"));
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
			method = ParseXMLToMap.class.getMethod("getXmlDataToMap" + stManageCom, parameterTypes);
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
	public HashMap<String, Object> getXmlDataToMap2011(String BusinessLogic, Document doc)
	{
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		/* 取得xml文件的根结点 */
		Element root = doc.getRootElement();
		if (null == root)
		{
			throw new NullPointerException();
		}
		
		/* 取得header部分 */
		Element eleHeader = root.getChild("Header");
		if (null == eleHeader)
		{
			throw new NullPointerException();
		}
		
		/* 取得结果编码节点 */
		Element eleResultCode = eleHeader.getChild("PA_RSLT_CODE");
		if (null == eleResultCode)
		{
			throw new NullPointerException();
		}
		
		/* 取得结果消息节点 */
		Element eleResultMsg = eleHeader.getChild("PA_RSLT_MESG");
		if (null == eleResultMsg)
		{
			throw new NullPointerException();
		}
		
		/* 取得返回结果编码 */
		String passStr = STR_PASS_CODE.equals(eleResultCode.getText())? STR_PASS : STR_NOPASS;
		
		/* 取得返回结果信息 */
		String resultMsg = eleResultMsg.getText();
		
		/* 保存通过标记, 000000表示成功 */
		resultMap.put("passFlag", passStr);
		
		/* 保存结果信息 */
		resultMap.put("rtnMessage", resultMsg);
		
		/* 取得Response子节点 */
		Element eleResponse = root.getChild("Response");
		if ((null != eleResponse) && (eleResponse.getContentSize()>0))
		{
			Element elePolicyInfo = eleResponse.getChild("policyInfo");
			if ((null != elePolicyInfo) && (elePolicyInfo.getContentSize()>0))
			{
				try
				{
					/* 第三方系统订单号 */
					resultMap.put("tpySysSn", elePolicyInfo.getChildTextTrim("OrderNo"));
					/* 第三方系统订单金额 */
					resultMap.put("originalAmount", elePolicyInfo.getChildTextTrim("OrderAmount"));
					/* 第三方系统订单优惠后金额 */
					resultMap.put("discountAmount", elePolicyInfo.getChildTextTrim("ActalPayAmount"));
					/* 第三方系统内部流水号 */
					resultMap.put("tpySysPaySn", elePolicyInfo.getChildTextTrim("PayAppNo"));
					/* 投保单号 */
					resultMap.put("applyPolicyNo", elePolicyInfo.getChildTextTrim("applyPolicyNo"));
					if ("09".equals(BusinessLogic))
					{
						/* 保存保单号 */
						resultMap.put("policyNo", elePolicyInfo.getChildTextTrim("policyNo"));
						/* 保单总保费 */
						resultMap.put("totalPremium", elePolicyInfo.getChildTextTrim("totalPremium"));
					}
					
					/* 返回数据映射 */
					return resultMap;
				}
				catch (Exception e)
				{
					logger.error(e.getMessage(), e);
				}
			}
		}
		/* 如果是不通过则返回map */
		if (STR_NOPASS.equals(passStr))
		{
			return resultMap;
		}
		
		/* 其他情况返回空 */
		return null;
	}
	
	/**
	 * 解析泰康返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> getXmlDataToMap1015(String BusinessLogic, Document doc)
	{
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		/* 取得xml文件的根结点 */
		Element root = doc.getRootElement();
		if (null == root)
		{
			throw new NullPointerException(BusinessLogic + "返回报文没有根节点！");
		}
		
		/* 取得header部分 */
		Element eleHeader = root.getChild("Header");
		if (null == eleHeader)
		{
			throw new NullPointerException();
		}
		resultMap.put("TRAN_CODE", eleHeader.getChildTextTrim("TRAN_CODE"));
		resultMap.put("BK_ACCT_DATE", eleHeader.getChildTextTrim("BK_ACCT_DATE"));
		resultMap.put("BK_ACCT_TIME", eleHeader.getChildTextTrim("BK_ACCT_TIME"));
		resultMap.put("BK_SERIAL", eleHeader.getChildTextTrim("BK_SERIAL"));
		
		/* 取得Response子节点 */
		Element eleResponse = root.getChild("Response");
		if (null == eleResponse)
		{
			Element eleErrCode = eleHeader.getChild("PA_RSLT_CODE");
			Element eleErrMsg = eleHeader.getChild("PA_RSLT_MESG");
			if ((eleErrCode!= null) && (eleErrMsg!=null))
			{
				/* 保存通过标记, 000000表示成功 */
				resultMap.put("passFlag", STR_NOPASS);
				
				/* 保存结果信息 */
				resultMap.put("rtnMessage", eleErrCode.getTextTrim() + ":" + eleErrMsg.getTextTrim());
				return resultMap;
			}
			throw new NullPointerException(BusinessLogic + "返回报文没有Response节点！");
		}
		else if (eleResponse.getContentSize()==0)
		{
			throw new NullPointerException(BusinessLogic + "返回报文Response节点缺少子节点！");
		}

		List<Element> listPolicyInfo = eleResponse.getChildren("policyInfo");
		int iPolicyInfoSize = listPolicyInfo.size();
		if (0 == iPolicyInfoSize)
		{
			throw new NullPointerException(BusinessLogic + "返回报文没有policyInfo节点！");
		}
		
		Element elePolicyInfo = (Element)listPolicyInfo.get(0);
		if (elePolicyInfo.getContentSize() == 0)
		{
			throw new NullPointerException(BusinessLogic + "返回报文policyInfo缺少子节点！");
		}
		
		String OrderNo = elePolicyInfo.getChildTextTrim("OrderNo");

		/* 第三方系统订单号 */
		resultMap.put("tpySysSn", OrderNo);

		StringBuffer sbErrMsg = new StringBuffer();
		String resultCode = elePolicyInfo.getChildTextTrim("MessageStatusSubCode");
		String resultMsg = elePolicyInfo.getChildTextTrim("MessageStatusSubDescription");
		if (STR_PASS_CODE.equals(resultCode))
		{
			if (iPolicyInfoSize > 1)
			{
				/* 保存通过标记, 000000表示成功 */
				resultMap.put("passFlag", STR_NOPASS);
				
				/* 保存结果信息 */
				resultMap.put("rtnMessage", "返回信息有误！");
				return resultMap;
			}
			resultMap.put("passFlag", STR_PASS);
			sbErrMsg.append(resultMsg);
			String OrderAmount = elePolicyInfo.getChildTextTrim("OrderAmount");
			String ActalPayAmount = elePolicyInfo.getChildTextTrim("ActalPayAmount");
			/* 第三方系统订单金额 */
			resultMap.put("OrderAmount", OrderAmount);
			/* 第三方系统订单优惠后金额 */
			resultMap.put("ActalPayAmount", ActalPayAmount);
		}
		else
		{
			resultMap.put("passFlag", STR_NOPASS);
			sbErrMsg.append("1、");
			sbErrMsg.append(resultCode);
			sbErrMsg.append(":");
			sbErrMsg.append(resultMsg);
		}
		
		String policyNo = null;
		
		if ("09".equals(BusinessLogic))
		{
			/* 投保单号: 由于没有返回投保单号，直接记录OrderNo */
			resultMap.put("applyPolicyNo", OrderNo);
			/* 保存保单号 */
			policyNo = elePolicyInfo.getChildTextTrim("policyNo");
			resultMap.put("policyNo", policyNo);
		}
		
		for (int i=1; i<iPolicyInfoSize; i++)
		{
			elePolicyInfo = (Element)listPolicyInfo.get(i);
			resultCode = elePolicyInfo.getChildTextTrim("MessageStatusSubCode");
			//校验子节点间信息一致性
			if (!OrderNo.equals(elePolicyInfo.getChildTextTrim("OrderNo"))
					|| STR_PASS_CODE.equals(resultCode))
			{
				logger.error("返回订单号等信息有误！节点位置：{}", (i+1));
				return null;
			}
			//校验子节点间承保信息一致性
			if ("09".equals(BusinessLogic))
			{
				if (!policyNo.equals(elePolicyInfo.getChildTextTrim("policyNo")))
				{
					logger.error("返回保单号等信息有误！节点位置：{}", (i+1));
					return null;
				}
			}
			sbErrMsg.append(";");
			sbErrMsg.append(i+1);
			sbErrMsg.append("、");
			sbErrMsg.append(resultCode);
			sbErrMsg.append(":");
			sbErrMsg.append(elePolicyInfo.getChildTextTrim("MessageStatusSubDescription"));
		}
		
		/* 保存结果信息 */
		resultMap.put("rtnMessage", sbErrMsg.toString());
		
		/* 其他情况返回空 */
		return resultMap;
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
	 * 解析华泰返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> getXmlDataToMap2023(String BusinessLogic, Document doc)
	{
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		/* 取得xml文件的根结点 */
		Element root = doc.getRootElement();
		if (null == root)
		{
			throw new NullPointerException(BusinessLogic + "返回报文没有根节点！");
		}
		
		/* 取得header部分 */
		Element eleHeader = root.getChild("Header");
		if (null == eleHeader)
		{
			throw new NullPointerException();
		}
		resultMap.put("TRAN_CODE", eleHeader.getChildTextTrim("TRAN_CODE"));
		resultMap.put("BK_SERIAL", eleHeader.getChildTextTrim("BK_SERIAL"));
		
		/* 取得Response子节点 */
		Element eleResponse = root.getChild("Response");
		if (null == eleResponse)
		{
			Element eleErrCode = eleHeader.getChild("PA_RSLT_CODE");
			Element eleErrMsg = eleHeader.getChild("PA_RSLT_MESG");
			if ((eleErrCode!= null) && (eleErrMsg!=null))
			{
				/* 保存通过标记, 000000表示成功 */
				resultMap.put("passFlag", STR_NOPASS);
				
				/* 保存结果信息 */
				resultMap.put("rtnMessage", eleErrCode.getTextTrim() + ":" + eleErrMsg.getTextTrim());
				return resultMap;
			}
			throw new NullPointerException(BusinessLogic + "返回报文没有Response节点！");
		}
		else if (eleResponse.getContentSize()==0) {
			throw new NullPointerException(BusinessLogic + "返回报文Response节点缺少子节点！");
		}

		List<Element> listPolicyInfo = eleResponse.getChildren("policyInfo");
		int iPolicyInfoSize = listPolicyInfo.size();
		if (0 == iPolicyInfoSize) {
			throw new NullPointerException(BusinessLogic + "返回报文没有policyInfo节点！");
		}
		
		Element elePolicyInfo = (Element)listPolicyInfo.get(0);
		if (elePolicyInfo.getContentSize() == 0) {
			throw new NullPointerException(BusinessLogic + "返回报文policyInfo缺少子节点！");
		}
		
		String OrderNo = elePolicyInfo.getChildTextTrim("applyPolicyNo");

		/* 第三方系统订单号 */
		resultMap.put("tpySysSn", OrderNo);

		StringBuffer sbErrMsg = new StringBuffer();
		String resultCode = elePolicyInfo.getChildTextTrim("MessageStatusSubCode");
		String resultMsg = elePolicyInfo.getChildTextTrim("MessageStatusSubDescription");
		if (STR_PASS_CODE.equals(resultCode))
		{
			if (iPolicyInfoSize > 1)
			{
				/* 保存通过标记, 000000表示成功 */
				resultMap.put("passFlag", STR_NOPASS);
				
				/* 保存结果信息 */
				resultMap.put("rtnMessage", "返回信息有误！");
				return resultMap;
			}
			resultMap.put("passFlag", STR_PASS);
			sbErrMsg.append(resultMsg);
		} else {
			resultMap.put("passFlag", STR_NOPASS);
			sbErrMsg.append("1、");
			sbErrMsg.append(resultCode);
			sbErrMsg.append(":");
			sbErrMsg.append(resultMsg);
		}
		
		String policyNo = null;
		
		/* 投保单号: 由于没有返回投保单号，直接记录OrderNo */
		resultMap.put("applyPolicyNo", OrderNo);
		/* 保存保单号 */
		policyNo = elePolicyInfo.getChildTextTrim("policyNo");
		resultMap.put("policyNo", policyNo);
		
		for (int i=1; i<iPolicyInfoSize; i++) {
			elePolicyInfo = (Element)listPolicyInfo.get(i);
			resultCode = elePolicyInfo.getChildTextTrim("MessageStatusSubCode");
			sbErrMsg.append(";");
			sbErrMsg.append(i+1);
			sbErrMsg.append("、");
			sbErrMsg.append(resultCode);
			sbErrMsg.append(":");
			sbErrMsg.append(elePolicyInfo.getChildTextTrim("MessageStatusSubDescription"));
		}
		if ("02".equals(BusinessLogic)) {
			Element eleErrCode = eleHeader.getChild("PA_RSLT_CODE");
			Element eleErrMsg = eleHeader.getChild("PA_RSLT_MESG");
			resultMap.put("rtnMessage", eleErrMsg.getTextTrim());
			resultMap.put("rtnCode", eleErrCode.getTextTrim());
		} else {
			/* 保存结果信息 */
			resultMap.put("rtnMessage", sbErrMsg.toString());
			resultMap.put("rtnMessage", sbErrMsg.toString());
		}

		
		/* 其他情况返回空 */
		return resultMap;
	}
	/**
	 * 解析丰泰返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> getXmlDataToMap2096(String BusinessLogic, Document doc)
	{
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		/* 取得xml文件的根结点 */
		Element root = doc.getRootElement();
		if (null == root)
		{
			throw new NullPointerException(BusinessLogic + "返回报文没有根节点！");
		}
		
		/* 取得header部分 */
		Element eleHeader = root.getChild("Header");
		if (null == eleHeader)
		{
			throw new NullPointerException();
		}
		resultMap.put("TRAN_CODE", eleHeader.getChildTextTrim("TRAN_CODE"));
		resultMap.put("BK_SERIAL", eleHeader.getChildTextTrim("BK_SERIAL"));
		
		/* 取得Response子节点 */
		Element eleResponse = root.getChild("Response");
		if (null == eleResponse)
		{
			Element eleErrCode = eleHeader.getChild("PA_RSLT_CODE");
			Element eleErrMsg = eleHeader.getChild("PA_RSLT_MESG");
			if ((eleErrCode!= null) && (eleErrMsg!=null))
			{
				/* 保存通过标记, 000000表示成功 */
				resultMap.put("passFlag", STR_NOPASS);
				
				/* 保存结果信息 */
				resultMap.put("rtnMessage", eleErrCode.getTextTrim() + ":" + eleErrMsg.getTextTrim());
				return resultMap;
			}
			throw new NullPointerException(BusinessLogic + "返回报文没有Response节点！");
		}
		else if (eleResponse.getContentSize()==0) {
			throw new NullPointerException(BusinessLogic + "返回报文Response节点缺少子节点！");
		}

		List<Element> listPolicyInfo = eleResponse.getChildren("policyInfo");
		int iPolicyInfoSize = listPolicyInfo.size();
		if (0 == iPolicyInfoSize) {
			throw new NullPointerException(BusinessLogic + "返回报文没有policyInfo节点！");
		}
		
		Element elePolicyInfo = (Element)listPolicyInfo.get(0);
		if (elePolicyInfo.getContentSize() == 0) {
			throw new NullPointerException(BusinessLogic + "返回报文policyInfo缺少子节点！");
		}
		
		String OrderNo = elePolicyInfo.getChildTextTrim("applyPolicyNo");

		/* 第三方系统订单号 */
		resultMap.put("tpySysSn", OrderNo);

		StringBuffer sbErrMsg = new StringBuffer();
		String resultCode = elePolicyInfo.getChildTextTrim("MessageStatusSubCode");
		String resultMsg = elePolicyInfo.getChildTextTrim("MessageStatusSubDescription");
		if (STR_PASS_CODE.equals(resultCode))
		{
			if (iPolicyInfoSize > 1)
			{
				/* 保存通过标记, 000000表示成功 */
				resultMap.put("passFlag", STR_NOPASS);
				
				/* 保存结果信息 */
				resultMap.put("rtnMessage", "返回信息有误！");
				return resultMap;
			}
			resultMap.put("passFlag", STR_PASS);
			sbErrMsg.append(resultMsg);
		} else {
			resultMap.put("passFlag", STR_NOPASS);
			sbErrMsg.append("1、");
			sbErrMsg.append(resultCode);
			sbErrMsg.append(":");
			sbErrMsg.append(resultMsg);
		}
		
		String policyNo = null;
		
		/* 投保单号: 由于没有返回投保单号，直接记录OrderNo */
		resultMap.put("applyPolicyNo", OrderNo);
		/* 保存保单号 */
		policyNo = elePolicyInfo.getChildTextTrim("policyNo");
		String totalPremium = elePolicyInfo.getChildTextTrim("totalPremium");
		resultMap.put("policyNo", policyNo);
		resultMap.put("totalPremium", totalPremium);
		
		for (int i=1; i<iPolicyInfoSize; i++) {
			elePolicyInfo = (Element)listPolicyInfo.get(i);
			resultCode = elePolicyInfo.getChildTextTrim("MessageStatusSubCode");
			sbErrMsg.append(";");
			sbErrMsg.append(i+1);
			sbErrMsg.append("、");
			sbErrMsg.append(resultCode);
			sbErrMsg.append(":");
			sbErrMsg.append(elePolicyInfo.getChildTextTrim("MessageStatusSubDescription"));
		}
		
		/* 保存结果信息 */
		resultMap.put("rtnMessage", sbErrMsg.toString());
		
		/* 其他情况返回空 */
		return resultMap;
	}
	/**
	 * 解析大众返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> getXmlDataToMap2071(String BusinessLogic, Document doc)
	{
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		/* 取得xml文件的根结点 */
		Element root = doc.getRootElement();
		if (null == root)
		{
			throw new NullPointerException(BusinessLogic + "返回报文没有根节点！");
		}
		
		/* 取得header部分 */
		Element eleHeader = root.getChild("Header");
		if (null == eleHeader)
		{
			throw new NullPointerException();
		}
		resultMap.put("TRAN_CODE", eleHeader.getChildTextTrim("TRAN_CODE"));
		resultMap.put("BK_SERIAL", eleHeader.getChildTextTrim("BK_SERIAL"));
		
		/* 取得Response子节点 */
		Element eleResponse = root.getChild("Response");
		if (null == eleResponse)
		{
			Element eleErrCode = eleHeader.getChild("PA_RSLT_CODE");
			Element eleErrMsg = eleHeader.getChild("PA_RSLT_MESG");
			if ((eleErrCode!= null) && (eleErrMsg!=null))
			{
				/* 保存通过标记, 000000表示成功 */
				resultMap.put("passFlag", STR_NOPASS);
				
				/* 保存结果信息 */
				resultMap.put("rtnMessage", eleErrCode.getTextTrim() + ":" + eleErrMsg.getTextTrim());
				return resultMap;
			}
			throw new NullPointerException(BusinessLogic + "返回报文没有Response节点！");
		}
		else if (eleResponse.getContentSize()==0) {
			throw new NullPointerException(BusinessLogic + "返回报文Response节点缺少子节点！");
		}

		List<Element> listPolicyInfo = eleResponse.getChildren("policyInfo");
		int iPolicyInfoSize = listPolicyInfo.size();
		if (0 == iPolicyInfoSize) {
			throw new NullPointerException(BusinessLogic + "返回报文没有policyInfo节点！");
		}
		
		Element elePolicyInfo = (Element)listPolicyInfo.get(0);
		if (elePolicyInfo.getContentSize() == 0) {
			throw new NullPointerException(BusinessLogic + "返回报文policyInfo缺少子节点！");
		}
		
		String OrderNo = elePolicyInfo.getChildTextTrim("applyPolicyNo");

		/* 第三方系统订单号 */
		resultMap.put("tpySysSn", OrderNo);

		StringBuffer sbErrMsg = new StringBuffer();
		String resultCode = elePolicyInfo.getChildTextTrim("MessageStatusSubCode");
		String resultMsg = elePolicyInfo.getChildTextTrim("MessageStatusSubDescription");
		if (STR_PASS_CODE.equals(resultCode))
		{
			if (iPolicyInfoSize > 1)
			{
				/* 保存通过标记, 000000表示成功 */
				resultMap.put("passFlag", STR_NOPASS);   
				
				/* 保存结果信息 */
				resultMap.put("rtnMessage", "返回信息有误！");  
				return resultMap;
			}
			resultMap.put("passFlag", STR_PASS);
			sbErrMsg.append(resultMsg);
		} else {
			resultMap.put("passFlag", STR_NOPASS);  
			sbErrMsg.append("1、");
			sbErrMsg.append(resultCode);
			sbErrMsg.append(":");
			sbErrMsg.append(resultMsg);
		}
		
		String policyNo = null;
		
		/* 投保单号: 由于没有返回投保单号，直接记录OrderNo */
		resultMap.put("applyPolicyNo", OrderNo);
		/* 保存保单号 */
		policyNo = elePolicyInfo.getChildTextTrim("policyNo");
		String totalPremium = elePolicyInfo.getChildTextTrim("totalPremium");
		String noticeNo = elePolicyInfo.getChildTextTrim("noticeNo");
		resultMap.put("policyNo", policyNo);
		resultMap.put("totalPremium", totalPremium);
		resultMap.put("noticeNo", noticeNo); 
		
		for (int i=1; i<iPolicyInfoSize; i++) {
			elePolicyInfo = (Element)listPolicyInfo.get(i);
			resultCode = elePolicyInfo.getChildTextTrim("MessageStatusSubCode");
			sbErrMsg.append(";");
			sbErrMsg.append(i+1);
			sbErrMsg.append("、");
			sbErrMsg.append(resultCode);
			sbErrMsg.append(":");
			sbErrMsg.append(elePolicyInfo.getChildTextTrim("MessageStatusSubDescription"));
		}
		  
		/* 保存结果信息 */
		resultMap.put("rtnMessage", sbErrMsg.toString());
		
		/* 其他情况返回空 */  
		return resultMap;  
	}
	/**
	 * 解析大都会返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> getXmlDataToMap1048(String BusinessLogic, Document doc)
	{
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		/* 取得xml文件的根结点 */
		Element root = doc.getRootElement();
		if (null == root)
		{
			throw new NullPointerException(BusinessLogic + "返回报文没有根节点！");
		}
		
		/* 取得header部分 */ 
		Element eleHeader = root.getChild("Header");
		if (null == eleHeader) {
			throw new NullPointerException();
		}
		resultMap.put("TRAN_CODE", eleHeader.getChildTextTrim("TRAN_CODE"));
		resultMap.put("BK_ACCT_DATE", eleHeader.getChildTextTrim("BK_ACCT_DATE"));
		resultMap.put("BK_ACCT_TIME", eleHeader.getChildTextTrim("BK_ACCT_TIME"));
		resultMap.put("BK_SERIAL", eleHeader.getChildTextTrim("BK_SERIAL"));
		
		/* 取得Response子节点 */
		Element eleResponse = root.getChild("Response");
		if (null == eleResponse) {
			Element eleErrCode = eleHeader.getChild("PA_RSLT_CODE");
			Element eleErrMsg = eleHeader.getChild("PA_RSLT_MESG");
			if ((eleErrCode!= null) && (eleErrMsg!=null)) {
				/* 保存通过标记, 000000表示成功 */
				resultMap.put("passFlag", STR_NOPASS);
				
				/* 保存结果信息 */
				resultMap.put("rtnMessage", eleErrCode.getTextTrim() + ":" + eleErrMsg.getTextTrim());
				return resultMap;
			}
			throw new NullPointerException(BusinessLogic + "返回报文没有Response节点！");
		} else if (eleResponse.getContentSize()==0) {
			throw new NullPointerException(BusinessLogic + "返回报文Response节点缺少子节点！");
		}
		
		List<Element> listOrderInfo = eleResponse.getChildren("OrderInfo");
		int iOrderInfoSize = listOrderInfo.size();
		if (0 == iOrderInfoSize) {
			throw new NullPointerException(BusinessLogic + "返回报文没有OrderInfo节点！");
		}
		Element eleOrderInfo = eleResponse.getChild("OrderInfo");
		String OrderNo = eleOrderInfo.getChildTextTrim("OrderNo");
		List<Element> listPolicyInfo = eleOrderInfo.getChildren("policyInfo");
		int iPolicyInfoSize = listPolicyInfo.size();
		if (0 == iPolicyInfoSize) {
			throw new NullPointerException(BusinessLogic + "返回报文没有policyInfo节点！");
		}
		
		Element elePolicyInfo = (Element)listPolicyInfo.get(0);
		if (elePolicyInfo.getContentSize() == 0) {
			throw new NullPointerException(BusinessLogic + "返回报文policyInfo缺少子节点！");
		}
		

		/* 第三方系统订单号 */
		resultMap.put("tpySysSn", OrderNo);

		StringBuffer sbErrMsg = new StringBuffer();
		String resultCode = elePolicyInfo.getChildTextTrim("MessageStatusSubCode");
		String resultMsg = elePolicyInfo.getChildTextTrim("MessageStatusSubDescription");
		if (STR_PASS_CODE.equals(resultCode)) {
			if (iPolicyInfoSize > 1) {
				/* 保存通过标记, 000000表示成功 */
				resultMap.put("passFlag", STR_NOPASS);
				
				/* 保存结果信息 */
				resultMap.put("rtnMessage", "返回信息有误！");
				return resultMap;
			}
			resultMap.put("passFlag", STR_PASS);
			sbErrMsg.append(resultMsg);
			String OrderAmount = eleOrderInfo.getChildTextTrim("OrderAmount");
			String ActalPayAmount = eleOrderInfo.getChildTextTrim("ActalPayAmount");
			String noticeNo = elePolicyInfo.getChildTextTrim("noticeNo");
			String policyPath = elePolicyInfo.getChildTextTrim("applyPolicyNo");
			/* 第三方系统订单金额 */
			resultMap.put("OrderAmount", OrderAmount);
			/* 第三方系统订单优惠后金额 */
			resultMap.put("ActalPayAmount", ActalPayAmount);
			resultMap.put("noticeNo", noticeNo); 
			resultMap.put("policyPath", policyPath); 
		}
		else {
			resultMap.put("passFlag", STR_NOPASS);
			sbErrMsg.append("1、");
			sbErrMsg.append(resultCode);
			sbErrMsg.append(":");
			sbErrMsg.append(resultMsg);
		}
		
		String policyNo = null;
		
		if ("09".equals(BusinessLogic)) {
			/* 投保单号: 由于没有返回投保单号，直接记录OrderNo */
			resultMap.put("applyPolicyNo", OrderNo);
			/* 保存保单号 */
			policyNo = elePolicyInfo.getChildTextTrim("policyNo");
			resultMap.put("policyNo", policyNo);
		}
		
		for (int i=1; i<iPolicyInfoSize; i++)
		{
			elePolicyInfo = (Element)listPolicyInfo.get(i);
			resultCode = elePolicyInfo.getChildTextTrim("MessageStatusSubCode");
			//校验子节点间信息一致性
			if (!OrderNo.equals(elePolicyInfo.getChildTextTrim("OrderNo"))
					|| STR_PASS_CODE.equals(resultCode))
			{
				logger.error("返回订单号等信息有误！节点位置：{}", (i+1));
				return null;
			}
			//校验子节点间承保信息一致性
			if ("09".equals(BusinessLogic))
			{
				if (!policyNo.equals(elePolicyInfo.getChildTextTrim("policyNo")))
				{
					logger.error("返回保单号等信息有误！节点位置：{}", (i+1));
					return null;
				}
			}
			sbErrMsg.append(";");
			sbErrMsg.append(i+1);
			sbErrMsg.append("、");
			sbErrMsg.append(resultCode);
			sbErrMsg.append(":");
			sbErrMsg.append(elePolicyInfo.getChildTextTrim("MessageStatusSubDescription"));
		}
		
		/* 保存结果信息 */
		resultMap.put("rtnMessage", sbErrMsg.toString());
		
		/* 其他情况返回空 */
		return resultMap;
	}
	/**
	 * 解析昆仑返回的xml，并获取指定数据通过HashMap(String, Object)返回
	 * @param BusinessLogic，字符串格式, 承保核保标记
	 * @param doc, 保险公司返回的xml文档的jdom对象
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> getXmlDataToMap1065(String BusinessLogic, Document doc)
	{
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		/* 取得xml文件的根结点 */
		Element root = doc.getRootElement();
		if (null == root)
		{
			throw new NullPointerException(BusinessLogic + "返回报文没有根节点！");
		}
		
		/* 取得header部分 */ 
		Element eleHeader = root.getChild("Header");
		if (null == eleHeader) {
			throw new NullPointerException();
		}
		resultMap.put("TRAN_CODE", eleHeader.getChildTextTrim("TRAN_CODE"));
		resultMap.put("BK_ACCT_DATE", eleHeader.getChildTextTrim("BK_ACCT_DATE"));
		resultMap.put("BK_ACCT_TIME", eleHeader.getChildTextTrim("BK_ACCT_TIME"));
		resultMap.put("BK_SERIAL", eleHeader.getChildTextTrim("BK_SERIAL"));
		
		/* 取得Response子节点 */
		Element eleResponse = root.getChild("Response");
		if (null == eleResponse) {
			Element eleErrCode = eleHeader.getChild("PA_RSLT_CODE");
			Element eleErrMsg = eleHeader.getChild("PA_RSLT_MESG");
			if ((eleErrCode!= null) && (eleErrMsg!=null)) {
				/* 保存通过标记, 000000表示成功 */
				resultMap.put("passFlag", STR_NOPASS);
				
				/* 保存结果信息 */
				resultMap.put("rtnMessage", eleErrCode.getTextTrim() + ":" + eleErrMsg.getTextTrim());
				return resultMap;
			}
			throw new NullPointerException(BusinessLogic + "返回报文没有Response节点！");
		} else if (eleResponse.getContentSize()==0) {
			throw new NullPointerException(BusinessLogic + "返回报文Response节点缺少子节点！");
		}
		
		List<Element> listOrderInfo = eleResponse.getChildren("OrderInfo");
		int iOrderInfoSize = listOrderInfo.size();
		if (0 == iOrderInfoSize) {
			throw new NullPointerException(BusinessLogic + "返回报文没有OrderInfo节点！");
		}
		Element eleOrderInfo = eleResponse.getChild("OrderInfo");
		String OrderNo = eleOrderInfo.getChildTextTrim("OrderNo");
		List<Element> listPolicyInfo = eleOrderInfo.getChildren("policyInfo");
		int iPolicyInfoSize = listPolicyInfo.size();
		if (0 == iPolicyInfoSize) {
			throw new NullPointerException(BusinessLogic + "返回报文没有policyInfo节点！");
		}
		
		Element elePolicyInfo = (Element)listPolicyInfo.get(0);
		if (elePolicyInfo.getContentSize() == 0) {
			throw new NullPointerException(BusinessLogic + "返回报文policyInfo缺少子节点！");
		}
		

		/* 第三方系统订单号 */
		resultMap.put("tpySysSn", OrderNo);

		StringBuffer sbErrMsg = new StringBuffer();
		String resultCode = elePolicyInfo.getChildTextTrim("MessageStatusSubCode");
		String resultMsg = elePolicyInfo.getChildTextTrim("MessageStatusSubDescription");
		if (STR_PASS_CODE.equals(resultCode)) {
			if (iPolicyInfoSize > 1) {
				/* 保存通过标记, 000000表示成功 */
				resultMap.put("passFlag", STR_NOPASS);
				
				/* 保存结果信息 */
				resultMap.put("rtnMessage", "返回信息有误！");
				return resultMap;
			}
			resultMap.put("passFlag", STR_PASS);
			sbErrMsg.append(resultMsg);
			String OrderAmount = eleOrderInfo.getChildTextTrim("OrderAmount");
			String ActalPayAmount = eleOrderInfo.getChildTextTrim("ActalPayAmount");
			String noticeNo = elePolicyInfo.getChildTextTrim("noticeNo");
			String policyPath = elePolicyInfo.getChildTextTrim("applyPolicyNo");
			/* 第三方系统订单金额 */
			resultMap.put("OrderAmount", OrderAmount);
			/* 第三方系统订单优惠后金额 */
			resultMap.put("ActalPayAmount", ActalPayAmount);
			resultMap.put("noticeNo", noticeNo); 
			resultMap.put("policyPath", policyPath); 
		}
		else {
			resultMap.put("passFlag", STR_NOPASS);
			sbErrMsg.append("1、");
			sbErrMsg.append(resultCode);
			sbErrMsg.append(":");
			sbErrMsg.append(resultMsg);
		}
		
		String policyNo = null;
		
		if ("01".equals(BusinessLogic)) {
			/* 投保单号: 由于没有返回投保单号，直接记录OrderNo */
			resultMap.put("applyPolicyNo", OrderNo);
			/* 保存保单号 */
			policyNo = elePolicyInfo.getChildTextTrim("policyNo");
			resultMap.put("policyNo", policyNo);
		}
		
		for (int i=1; i<iPolicyInfoSize; i++)
		{
			elePolicyInfo = (Element)listPolicyInfo.get(i);
			resultCode = elePolicyInfo.getChildTextTrim("MessageStatusSubCode");
			//校验子节点间信息一致性
			if (!OrderNo.equals(elePolicyInfo.getChildTextTrim("OrderNo"))
					|| STR_PASS_CODE.equals(resultCode))
			{
				logger.error("返回订单号等信息有误！节点位置：{}", (i+1));
				return null;
			}
			//校验子节点间承保信息一致性
			if ("09".equals(BusinessLogic))
			{
				if (!policyNo.equals(elePolicyInfo.getChildTextTrim("policyNo")))
				{
					logger.error("返回保单号等信息有误！节点位置：{}", (i+1));
					return null;
				}
			}
			sbErrMsg.append(";");
			sbErrMsg.append(i+1);
			sbErrMsg.append("、");
			sbErrMsg.append(resultCode);
			sbErrMsg.append(":");
			sbErrMsg.append(elePolicyInfo.getChildTextTrim("MessageStatusSubDescription"));
		}
		
		/* 保存结果信息 */
		resultMap.put("rtnMessage", sbErrMsg.toString());
		
		/* 其他情况返回空 */
		return resultMap;
	}
}
