package com.sinosoft.jdt;

import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.SDOrder;
import cn.com.sinosoft.service.impl.OrderConfigNewServiceImpl;
import cn.com.sinosoft.util.DownloadNet;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.lis.f1print.HTElectronicPolicy;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.SDInformationInsuredSchema;
import com.sinosoft.schema.SDInformationInsuredSet;
import com.sinosoft.schema.SDInformationRiskTypeSchema;
import com.sinosoft.schema.SDInformationRiskTypeSet;
import com.sinosoft.webservice.ProductWebservice;
import com.sinosoft.webservice.productPrem.ProductPremServiceStub.CalProductPrem;
import com.sinosoft.webservice.productPrem.ProductPremServiceStub.FEMRiskFactorList;
import com.sinosoft.webservice.productPrem.ProductPremServiceStub.ProductPremResponse;
import org.dom4j.DocumentException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 金代通对接淘宝数据，数据处理类
 * 
 * @author sinosoft
 * 
 */ 
public class ParseXMLToObject {
	private static final Logger logger = LoggerFactory.getLogger(ParseXMLToObject.class);

	/**
	 * 根据xml文档节点反射实体类对象，并保存数据
	 * 
	 * @param element
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Object ElementToObj(Element element) throws Exception {
		if (element == null)
			return null; 
		try {

			Object obj = Class.forName("cn.com.sinosoft.entity." + element.getName()).newInstance();
			List<Element> elementList = element.getChildren();
			Method method[] = obj.getClass().getDeclaredMethods();
			for (int i = 0; i < method.length; i++) {
				String name = method[i].getName();
				if (name.indexOf("set") != -1) {
					String methodName = name.replaceAll("set", "");
					for (Element ele : elementList) {
						if (methodName.toUpperCase().equals(ele.getName().toUpperCase())) {
							Class<?>[] paramTypes = method[i].getParameterTypes();
							// 获得一个方法参数数组（getparameterTypes用于返回一个描述参数类型的Class对象数组）
							if (paramTypes.length != 1)
								continue;
							else {
								String menthodParamter = paramTypes[0].getName();// 获取参数
								String value = ele.getTextTrim();// 获取值
								if ("".equals(value))
									continue;
								if (menthodParamter.indexOf("String") != -1) {
									method[i].invoke(obj, new Object[] { value });

								} else if (menthodParamter.indexOf("Double") != -1 || menthodParamter.indexOf("double") != -1) {
									if (value == null || "".equals(value) || "null".equalsIgnoreCase(value)) {
										method[i].invoke(obj, new Object[] { 0 });
									} else {
										method[i].invoke(obj, new Object[] { Double.parseDouble(value) });
									}

								} else if (menthodParamter.indexOf("Integer") != -1 || menthodParamter.indexOf("int") != -1) {
									if (value == null || "".equals(value) || "null".equalsIgnoreCase(value)) {
										method[i].invoke(obj, new Object[] { 0 });
									} else {
										method[i].invoke(obj, new Object[] { Integer.parseInt(value) });
									}

								} else if (menthodParamter.indexOf("Float") != -1 || menthodParamter.indexOf("float") != -1) {
									if (value == null || "".equals(value) || "null".equalsIgnoreCase(value)) {
										method[i].invoke(obj, new Object[] { 0 });
									} else {
										method[i].invoke(obj, new Object[] { Float.parseFloat(value) });
									}

								} else if (menthodParamter.indexOf("BigDecimal") != -1 || menthodParamter.indexOf("Decimal") != -1) {
									if (value == null || "".equals(value) || "null".equalsIgnoreCase(value)) {
										method[i].invoke(obj, new Object[] { 0 });
									} else {
										method[i].invoke(obj, new Object[] { new BigDecimal(value) });
									}

								} else if (menthodParamter.indexOf("Date") != -1 || menthodParamter.indexOf("DATE") != -1) {
									if (value == null || "".equals(value) || "null".equalsIgnoreCase(value)) {
										method[i].invoke(obj, new Object[] { 0 });
									} else {
										SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
										method[i].invoke(obj, new Object[] { sdf.parse(value) });
									}

								} else {
									if (menthodParamter.indexOf("SDOrderStatus") != -1) {
										// 订单表订单状态信息
										method[i].invoke(obj, new Object[] { SDOrder.SDOrderStatus.prepay });
									} else if (menthodParamter.indexOf("SDPayStatus") != -1) {
										// 订单表支付状态信息
										method[i].invoke(obj, new Object[] { SDOrder.SDPayStatus.unpaid });
									} else {
										logger.info("没有发现此类型。默认处理。");
									}
								}
							}
						}
					}
				}
			}
			return obj;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 保存保单数据
	 * 
	 * @param lmap
	 *            实体类集合
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean saveAll(LinkedHashMap<Object, String> lmap) {

		Session session = null;
		try {
			WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
			SessionFactory sessionFactory = (SessionFactory) wac.getBean("sessionFactory");
			session = sessionFactory.getCurrentSession();
			Iterator it = lmap.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<Object, String> entry = (Map.Entry<Object, String>) it.next();
				Object key = entry.getKey();
				String value = entry.getValue();

				if (value != null && !"".equals(value)) {
					if (key.getClass().getName().indexOf("List") != -1) {
						List key2 = (List) key;
						if ("insert".equalsIgnoreCase(value)) {
							for (Object obj : key2) {
								session.save(obj);
							}
						} else if ("delete".equalsIgnoreCase(value)) {
							for (Object obj : key2) {
								session.delete(obj);
							}
						} else if ("update".equalsIgnoreCase(value)) {
							for (Object obj : key2) {
								session.update(obj);
							}
						} else if ("insert&update".equalsIgnoreCase(value)) {
							for (Object obj : key2) {
								session.saveOrUpdate(obj);
							}
						}
					} else {
						if ("insert".equalsIgnoreCase(value)) {
							session.save(key);
						} else if ("delete".equalsIgnoreCase(value)) {
							session.delete(key);
						} else if ("update".equalsIgnoreCase(value)) {
							session.update(key);
						}
					}
				}
			}
			session.flush();
			session.clear();
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;

		} finally {

		}
	}

	/**
	 * 根据订单号删除所有相关数据
	 * 
	 * @param orderSn
	 *            订单号
	 * @return
	 */
	public boolean deleteAll(String orderSn) {

		Transaction trans = new Transaction();
		StringBuffer querySQL = new StringBuffer(" SELECT informationSn FROM sdinformation WHERE orderSn = ? ");
		QueryBuilder tQueryBuilder = new QueryBuilder(querySQL.toString());
		tQueryBuilder.add(orderSn);
		Object informationSn = tQueryBuilder.executeOneValue();
		if (informationSn != null && !"".equals(informationSn)) {
			informationSn = tQueryBuilder.executeOneValue().toString();
		} else {
			logger.info("没有查到该订单相关的详细信息或者订单号不存在，orderSn :{}", orderSn);
			return true;
		}
		// 订单表
		trans.add(new QueryBuilder("DELETE FROM sdorders WHERE orderSn = ?", orderSn));
		// 交易表
		trans.add(new QueryBuilder("DELETE FROM tradeinformation WHERE ordid = ?", orderSn));
		// 订单项表
		trans.add(new QueryBuilder("DELETE FROM sdorderitem WHERE orderSn = ?", orderSn));
		// 订单项表2
		trans.add(new QueryBuilder("DELETE FROM sdorderitemoth WHERE orderSn = ?", orderSn));
		// 订单详细信息表
		trans.add(new QueryBuilder("DELETE FROM sdinformation WHERE informationSn = ?", informationSn));
		// 投保人表
		trans.add(new QueryBuilder("DELETE FROM sdinformationappnt WHERE informationSn = ?", informationSn));
		// 被保人表
		trans.add(new QueryBuilder("DELETE FROM sdinformationinsured WHERE informationSn = ?", informationSn));
		// 受益人表
		trans.add(new QueryBuilder("DELETE FROM sdinformationbnf WHERE informationSn = ?", informationSn));
		// 责任表
		trans.add(new QueryBuilder("DELETE FROM sdinformationduty WHERE informationSn = ?", informationSn));
		// 投保要素表
		trans.add(new QueryBuilder("DELETE FROM sdinformationinsuredelements WHERE informationSn = ?", informationSn));
		// 健康告知表
		trans.add(new QueryBuilder("DELETE FROM sdinsuredhealth WHERE informationSn = ?", informationSn));
		// 保单信息表
		trans.add(new QueryBuilder("DELETE FROM sdinformationrisktype WHERE informationSn = ?", informationSn));
		if (trans.commit()) {
			logger.info("订单信息删除操作成功 orderSn:{}", orderSn);
		} else {
			logger.error("订单信息删除操作失败，请检查订单信心！  orderSn:{}", orderSn);
			return false;
		}
		return true;
	}

	/**
	 * 承保成功后，处理更新保单数据
	 * 
	 * @param insureDataList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean dealInsuranceData(List<Element> insureDataList, String insureTypeCode) {

		Transaction trans = new Transaction();
		Map<String, String> priceMap = new HashMap<String, String>();
		for (Element element : insureDataList) {

			if ("SDInfRiskType".equals(element.getName().trim())) {
				// 保单信息
				if (!dealRiskTypeData(trans, element.getChildren(), insureTypeCode)) {
					logger.error(" 淘宝对接数据，保单承保成功后更新保单信息失败，请查看保单信息完整性（sdinformationrisktype） ");
				}
			} else if ("SDOrder".equals(element.getName().trim())) {
				// 订单信息
				if (!dealOrderData(trans, element, priceMap)) {
					logger.error(" 淘宝对接数据，保单承保成功后更新订单失败，请查看保单信息完整性（sdorders） ");
				}
			} else if ("SDItemOth".equals(element.getName().trim())) {
				// 订单信息
				if (!dealItemData(trans, element.getChildren())) {
					logger.error(" 淘宝对接数据，保单承保成功后更新订单项信息失败，请查看保单信息完整性（sdorderitem） ");
				}
			} else if ("TradeInformation".equals(element.getName().trim())) {
				// 订单信息
				if (!dealTradeData(trans, element, priceMap)) {
					logger.error(" 淘宝对接数据，保单承保成功后更新订单项信息失败，请查看保单信息完整性（sdorderitem） ");
				}
			}else if("SDInformationAppnt".equals(element.getName().trim())){
				//投保人信息
				if (!dealAppntData(trans, element, priceMap)) {
					logger.error(" 淘宝对接数据，保单承保成功后更新投保人信息失败，请查看保单信息完整性（sdInformationAppnt） ");
				}
			}

		}
		if (trans.commit()) {
			logger.info("======淘宝对接数据，保单承保成功后更新数据成功=======");
		} else {
			logger.error("======淘宝对接数据，保单承保成功后更新数据失败，请查看数据信息完整性=======");
			return false;
		}
		return true;
	}

	/**
	 * 承保成功后，更新保单数据
	 * 
	 * @param trans
	 *            事务处理
	 * @param riskTypeDataList
	 * @return
	 */
	public boolean dealRiskTypeData(Transaction trans, List<Element> riskTypeDataList, String insureTypeCode) {
		// 更新保單信息表
		if(riskTypeDataList==null || riskTypeDataList.size()<=0){
			return false;
		}else{
			for (Element element : riskTypeDataList) {
				String orderSn = element.getChildTextTrim("orderSn");
				String recognizeeSn = element.getChildTextTrim("recognizeeSn");
				String policyNo = element.getChildTextTrim("policyNo");
				// 人保超白金需要生成一个假保单号
				if ("2005".equals(insureTypeCode)){
					policyNo = "RBCX-WJ" + NoUtil.getMaxNo("RBCX-WJ-", 8);
				}
				String applyPolicyNo = element.getChildTextTrim("applyPolicyNo");
				String electronicCout = element.getChildTextTrim("electronicCout");
				String electronicPath = element.getChildTextTrim("electronicPath");
				String insurerFlag = element.getChildTextTrim("insurerFlag");
				String insureMsg = element.getChildTextTrim("insureMsg");
				String appStatus = element.getChildTextTrim("appStatus");
				String noticeNo = element.getChildTextTrim("noticeNo");
				String validateCode = element.getChildTextTrim("validateCode");
				String insuranceTransCode = element.getChildTextTrim("insuranceTransCode");
				String insuranceBankCode = element.getChildTextTrim("insuranceBankCode");
				String insuranceBankSeriNO = element.getChildTextTrim("insuranceBankSeriNO");
				String insuranceBRNO = element.getChildTextTrim("insuranceBRNO");
				String insuranceTELLERNO = element.getChildTextTrim("insuranceTELLERNO");
				String returnPremiums = element.getChildTextTrim("returnPremiums");
				String activityValue = element.getChildTextTrim("activityValue");
				String payPrice = element.getChildTextTrim("payPrice");
				StringBuffer riskTypeSQL = new StringBuffer();
				riskTypeSQL.append("UPDATE sdinformationrisktype ");
				riskTypeSQL.append("SET policyNo = ?, ");
				riskTypeSQL.append("applyPolicyNo = ?, ");
				riskTypeSQL.append("electronicCout = ?, ");
				riskTypeSQL.append("electronicPath = ?, ");
				riskTypeSQL.append("insurerFlag = ?, ");
				riskTypeSQL.append("insureMsg = ?, ");
				riskTypeSQL.append("appStatus = ?, ");
				riskTypeSQL.append("noticeNo = ?, ");
				riskTypeSQL.append("validateCode = ?, ");
				riskTypeSQL.append("insuranceTransCode = ?, ");
				riskTypeSQL.append("insuranceBankCode = ?, ");
				riskTypeSQL.append("insuranceBankSeriNO = ?, ");
				riskTypeSQL.append("insuranceBRNO = ?, ");
				riskTypeSQL.append("insuranceTELLERNO = ?, ");
				riskTypeSQL.append("returnPremiums = ?, "); 
				riskTypeSQL.append("activityValue = ?, ");
				if(StringUtil.isNotEmpty(payPrice)){
					riskTypeSQL.append("payPrice = ?, "); //2014-08-21网站周年庆活动需求
				}else{
					riskTypeSQL.append("payPrice = timePrem, "); //2014-08-21网站周年庆活动需求
				}
				riskTypeSQL.append("insuredate = '" + PubFun.getCurrentDate() + " " + PubFun.getCurrentTime() + "', ");
				riskTypeSQL.append("modifydate = '" + PubFun.getCurrentDate() + " " + PubFun.getCurrentTime() + "' ");
				riskTypeSQL.append("WHERE orderSn = ? AND recognizeeSn = ? ");
				QueryBuilder riskTypeQB = new QueryBuilder();
				riskTypeQB.setSQL(riskTypeSQL.toString());
				riskTypeQB.add(policyNo);
				riskTypeQB.add(applyPolicyNo);
				riskTypeQB.add(electronicCout);
				riskTypeQB.add(electronicPath);
				riskTypeQB.add(insurerFlag);
				riskTypeQB.add(insureMsg);
				riskTypeQB.add(appStatus);
				riskTypeQB.add(noticeNo);
				riskTypeQB.add(validateCode);
				riskTypeQB.add(insuranceTransCode);
				riskTypeQB.add(insuranceBankCode);
				riskTypeQB.add(insuranceBankSeriNO);
				riskTypeQB.add(insuranceBRNO);
				riskTypeQB.add(insuranceTELLERNO);
				riskTypeQB.add(returnPremiums);
				riskTypeQB.add(activityValue);
				if(StringUtil.isNotEmpty(payPrice)){
					riskTypeQB.add(payPrice);
				}
				riskTypeQB.add(orderSn);
				riskTypeQB.add(recognizeeSn);
				trans.add(riskTypeQB);
			}
		}

		return true;
	}

	/**
	 * 承保成功后，更新保单数据
	 * 
	 * @param trans
	 *            事务处理
	 * @param orderDataList
	 * @return
	 */
	public boolean dealOrderData(Transaction trans, Element element, Map<String, String> priceMap) {

		String orderSn = element.getChildTextTrim("orderSn");
		String payAmount = element.getChildTextTrim("payAmount");
		String paySn = element.getChildTextTrim("paySn");
		String orderActivity = element.getChildTextTrim("orderActivity");
		String payPrice = element.getChildTextTrim("payPrice");
		String sumActivity = element.getChildTextTrim("sumActivity");
		priceMap.put("sumActivity", sumActivity);
		if(StringUtil.isNotEmpty(payPrice)){
			priceMap.put("payPrice", payPrice);
		}else{
			priceMap.put("payPrice", payAmount);
		}
		StringBuffer orderSQL = new StringBuffer();
		orderSQL.append("UPDATE sdorders ");
		orderSQL.append("SET payAmount = ?, ");
		orderSQL.append("paySn = ?, ");
		orderSQL.append("orderStatus = '7', ");
		if(orderSn.startsWith("QN")){
			orderSQL.append("payType = '6636', ");
		}else{
			orderSQL.append("payType = 'tbzfb', ");
		}
		orderSQL.append("payStatus = '2', ");
		orderSQL.append("payPrice = totalAmount, ");
		//modified by yuzj for 淘宝蚂蚁的处理 @20160701 begin
		if(orderSn.startsWith("TBMY")){
			orderSQL.append("channelsn = 'tb_my', ");
		}else if(orderSn.startsWith("QN")){
			orderSQL.append("channelsn = 'qunar', ");
		}else{
			orderSQL.append("channelsn = 'tb_ht', ");
		}
		//modified by yuzj for 淘宝蚂蚁的处理 @20160701 end
		orderSQL.append("modifydate = '"+PubFun.getCurrentDate()+" "+PubFun.getCurrentTime()+"', ");
		orderSQL.append("orderActivity = ?, ");
		orderSQL.append("payPrice = ?, ");
		orderSQL.append("sumActivity = ? ");
		orderSQL.append("WHERE orderSn = ? ");
		QueryBuilder orderQB = new QueryBuilder();
		orderQB.setSQL(orderSQL.toString());
		orderQB.add(payAmount);
		orderQB.add(paySn);
		orderQB.add(orderActivity);
		if(StringUtil.isNotEmpty(payPrice)){
			orderQB.add(payPrice);
		}else{
			orderQB.add(payAmount);
		}
		
		orderQB.add(sumActivity);
		orderQB.add(orderSn);
		trans.add(orderQB);

		return true;
	}

	/**
	 * 承保成功后，更新订单项数据
	 * 
	 * @param trans
	 *            事务处理
	 * @param itemDataList
	 * @return
	 */
	public boolean dealItemData(Transaction trans, List<Element> itemDataList) {
		// 更新保單信息表
		if(itemDataList==null || itemDataList.size()<=0){
			return false;
		}else{
			for (Element element : itemDataList) {
				String orderSn = element.getChildTextTrim("orderSn");
				String recognizeeSn = element.getChildTextTrim("recognizeeSn");
				String tpySn = element.getChildTextTrim("tpySn");
				String tpySysSn = element.getChildTextTrim("tpySysSn");
				String tpySysPaySn = element.getChildTextTrim("tpySysPaySn");
				
				StringBuffer itemSQL = new StringBuffer();
				itemSQL.append("UPDATE SDOrderItemOth ");
				itemSQL.append("SET tpySn = ?, ");
				itemSQL.append("tpySysSn = ?, ");
				itemSQL.append("tpySysPaySn = ? ");
				itemSQL.append("WHERE orderSn = ? AND recognizeeSn = ? ");
				QueryBuilder itemQB = new QueryBuilder();
				itemQB.setSQL(itemSQL.toString());
				itemQB.add(tpySn);
				itemQB.add(tpySysSn);
				itemQB.add(tpySysPaySn);
				itemQB.add(orderSn);
				itemQB.add(recognizeeSn);
				trans.add(itemQB);
			}
		}

		return true;
	}

	/**
	 * 承保成功后，更新支付交易数据
	 * 
	 * @param trans
	 * @param tradeInfoList
	 * @return
	 */
	public boolean dealTradeData(Transaction trans, Element element, Map<String, String> priceMap) {
		// 更新保單信息表
		String ordID = element.getChildTextTrim("ordID");
		String ordAmt = element.getChildTextTrim("ordAmt");
		String tradeResult = element.getChildTextTrim("tradeResult");
		String tradeSeriNO = element.getChildTextTrim("tradeSeriNO");
		String tradeBank = element.getChildTextTrim("tradeBank");
		// 默认值1 支付返回
		String sendDate = element.getChildTextTrim("sendDate");
		String receiveDate = element.getChildTextTrim("receiveDate");
		String tradeCheckSeriNo = element.getChildTextTrim("tradeCheckSeriNo");
		priceMap.put("TotalAmount", ordAmt);
		priceMap.put("receiveDate", receiveDate);
		StringBuffer tradeSQL = new StringBuffer();
		tradeSQL.append("UPDATE TradeInformation ");
		tradeSQL.append("SET ordAmt = ?, ");
		tradeSQL.append("tradeResult = ?, ");
		tradeSQL.append("tradeSeriNO = ?, ");
		tradeSQL.append("tradeBank = ?, ");
		tradeSQL.append("payStatus = ?, ");
		tradeSQL.append("sendDate = ?, ");
		tradeSQL.append("receiveDate = ?, ");
		tradeSQL.append("tradeCheckSeriNo = ?, ");
		tradeSQL.append("modifyDate = '"+PubFun.getCurrentDate()+" "+PubFun.getCurrentTime()+"', ");
		if(ordID.startsWith("QN")){
			tradeSQL.append("payType = '6636' ");
		}else{
			tradeSQL.append("payType = 'tbzfb' ");
		}
		
		tradeSQL.append("WHERE ordId = ? ");
		QueryBuilder tradeQB = new QueryBuilder();
		tradeQB.setSQL(tradeSQL.toString());
		tradeQB.add(ordAmt);
		tradeQB.add(tradeResult);
		tradeQB.add(tradeSeriNO);
		tradeQB.add(tradeBank);
		tradeQB.add("1");
		tradeQB.add(sendDate);
		tradeQB.add(receiveDate);
		tradeQB.add(tradeCheckSeriNo);
		tradeQB.add(ordID);
		trans.add(tradeQB);
		dealTradeSummary(tradeCheckSeriNo,priceMap,ordID,trans);
		return true;
	}
	/**
	 * 承保成功后，更新投保人数据
	 * 
	 * @param trans
	 * @param tradeInfoList
	 * @return
	 */
	public boolean dealAppntData(Transaction trans, Element element, Map<String, String> priceMap) {
		String mobileTelephone = element.getChildTextTrim("applicantMobile");
		Map<String, String> map = new HashMap<String, String>();
		List<Element> extendInfos = element.getChild("extendInfos").getChildren("extendInfo");
		for(Element extendInfo : extendInfos){
			map.put(extendInfo.getAttributeValue("key"), extendInfo.getAttributeValue("value"));
		}
		String orderSn = map.get("orderSn");
		String recognizeeSn = map.get("recognizeeSn");
		if(StringUtil.isNotEmpty(mobileTelephone) && StringUtil.isNotEmpty(orderSn) && StringUtil.isNotEmpty(recognizeeSn)){
			String appntSql =
					"UPDATE sdinformationappnt app INNER JOIN sdinformationinsured insured  ON app.informationSn = insured.informationSn SET app.applicantMobile = ?"
			+" WHERE insured.orderSn = ? and insured.recognizeeSn = ?";
			QueryBuilder appntQB = new QueryBuilder();
			appntQB.setSQL(appntSql);
			appntQB.add(mobileTelephone);
			appntQB.add(orderSn);
			appntQB.add(recognizeeSn);
			trans.add(appntQB);
		}
		
		return true;
	}
	
	/**
	 * 交易汇总信息-用于结算
	* @Title: dealTradeSummary 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @return boolean    返回类型 
	* @author XXX
	 */
	public boolean dealTradeSummary(String TradeSeriNo, Map<String, String> priceMap, String OrderNumber,
			Transaction trans) {
		QueryBuilder tradesummaryinfocountQB = new QueryBuilder(
				" SELECT COUNT(1) FROM tradesummaryinfo WHERE PaySn=? ");
		tradesummaryinfocountQB.add(TradeSeriNo);
		int tradesummaryinfocount = tradesummaryinfocountQB.executeInt();
		if (tradesummaryinfocount <= 0) {
			QueryBuilder inserttradesummaryinfoQB = new QueryBuilder(
					" INSERT INTO tradesummaryinfo VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
			inserttradesummaryinfoQB.add(NoUtil.getMaxNo("TradeSummaryID", 11));
			inserttradesummaryinfoQB.add(TradeSeriNo);
			inserttradesummaryinfoQB.add(TradeSeriNo);
			inserttradesummaryinfoQB.add("0");
			inserttradesummaryinfoQB.add(OrderNumber);
			inserttradesummaryinfoQB.add("");
			inserttradesummaryinfoQB.add(priceMap.get("sumActivity"));
			inserttradesummaryinfoQB.add("");
			inserttradesummaryinfoQB.add("");
			String payType = "tbzfb";
			if(OrderNumber.startsWith("QN")){
				payType="6636";
			} 
			inserttradesummaryinfoQB.add(payType);
			QueryBuilder paytypeqb = new QueryBuilder(
					" SELECT description FROM paybase WHERE payType=? LIMIT 1 ");
			paytypeqb.add(payType);
			inserttradesummaryinfoQB.add(paytypeqb.executeString());
			inserttradesummaryinfoQB.add(priceMap.get("TotalAmount"));
			inserttradesummaryinfoQB.add(priceMap.get("payPrice"));
			inserttradesummaryinfoQB.add(priceMap.get("receiveDate"));
			inserttradesummaryinfoQB.add(priceMap.get("receiveDate"));

			trans.add(inserttradesummaryinfoQB);
		} else {
			QueryBuilder upadtetradesummaryinfoQB = new QueryBuilder(
					" UPDATE tradesummaryinfo SET TradeResult=?,OrderSns=?,ActivitySumAmount=?,TotalAmount=?,TradeAmount=?,ModifyDate=? WHERE paysn=? ");
			upadtetradesummaryinfoQB.add("0");
			upadtetradesummaryinfoQB.add(OrderNumber);
			upadtetradesummaryinfoQB.add(priceMap.get("sumActivity"));
			upadtetradesummaryinfoQB.add(priceMap.get("TotalAmount"));
			upadtetradesummaryinfoQB.add(priceMap.get("payPrice"));
			upadtetradesummaryinfoQB.add(PubFun.getCurrent());
			upadtetradesummaryinfoQB.add(TradeSeriNo);

			trans.add(upadtetradesummaryinfoQB);
		}
		return true;
	}
	/**
	 * 向投保人发送保单成功邮件
	 * 
	 * @param orderSn
	 *            订单号
	 * @param request
	 * @return
	 */
	public boolean dealSendSucMail(String orderSn, HttpServletRequest request) {

		QueryBuilder tQueryBuilder = new QueryBuilder();
		StringBuffer tradeSQL = new StringBuffer(" select ordAmt,tradeSeriNO,receiveDate from TradeInformation where ordID = ? ");
		tQueryBuilder.setSQL(tradeSQL.toString());
		tQueryBuilder.add(orderSn);
		DataTable tradeDT = tQueryBuilder.executeDataTable();
		logger.info("==================淘宝保单====================发送保单成功邮件");
		Map<String, Object> map = new HashMap<String, Object>();
		String mailSQL = "select b.applicantMail from SDInformation a , SDInformationAppnt b where a.informationSn=b.informationSn and a.orderSn=?";
		tQueryBuilder = new QueryBuilder();
		tQueryBuilder.setSQL(mailSQL);
		tQueryBuilder.add(orderSn);
		String email = "";
		email = tQueryBuilder.executeOneValue().toString();
		logger.info("email:{}", email);
		Member member = new Member();
		member.setEmail(email);
		map.put("Member", member);
		map.put("OrderSn", orderSn);
		map.put("MemberName", "");
		StringBuffer sb = new StringBuffer();
		SDInformationInsuredSchema tSDInformationInsuredSchema = new SDInformationInsuredSchema();
		tSDInformationInsuredSchema.setorderSn(orderSn);
		SDInformationInsuredSet tSDInformationInsuredSet = tSDInformationInsuredSchema.query();
		for (int i = 0; i < tSDInformationInsuredSet.size(); i++) {
			SDInformationInsuredSchema sdInsured = tSDInformationInsuredSet.get(i);
			String ordersn = sdInsured.getorderSn();
			String recognizeeSn = sdInsured.getrecognizeeSn();
			String recognizeeName = sdInsured.getrecognizeeName();
			String policyNo = "";
			String productName = "";
			String sql = "select policyNo,riskName from SDInformationRiskType where orderSn = ? and recognizeeSn = ?";
			DataTable dt = new QueryBuilder(sql, ordersn, recognizeeSn).executeDataTable();
			if (dt.getRowCount() > 0) {
				policyNo = dt.getString(0, 0);
				productName = dt.getString(0, 1);
			}
			sb.append("<tr>");
			sb
					.append("<td align=\"center\" style=\"color:#999999;  border-bottom:1px solid  #BCCDE7;  text-align: center;\" height=\"48px;\"><font style=\"color:#3399CC; font-size:12px; \">"
							+ ordersn + "</font></td>");
			sb.append("<td align=\"center\" style=\"color:#999999; border-bottom:1px solid  #BCCDE7;  text-align: center;  font-size:12px;\">" + productName + "</td>");
			sb.append("<td align=\"center\" style=\"color:#999999; border-bottom:1px solid  #BCCDE7;   text-align: center; font-size:12px;\">" + recognizeeName + "</td>");
			sb.append("<td align=\"center\" style=\"color:#F15717; border-bottom:1px solid  #BCCDE7;  text-align: center; font-size:12px; font-family:'Microsoft YaHei' \">" + policyNo
					+ "</td>");
			sb.append("</tr>");
		}
		map.put("detail", sb.toString());
		map.put("paidOrdAmt", tradeDT.getString(0, 0));
		map.put("tradeSerialNO", tradeDT.getString(0, 1));
		map.put("paidData", tradeDT.getString(0, 2).substring(0, 11));// 支付时间
		if (ActionUtil.dealAction("wj00030", map, request)) {
			logger.info("==================淘宝保单成功====================发送保单成功邮件成功");
		} else {
			logger.error("==================淘宝保单失败====================发送保单成功邮件失败");
			return false;
		}

		return true;
	}

	/**
	 * 淘宝回调后，根据保险公司、订单号发送电子保单
	 * 
	 * @param insureTypeCode
	 *            保险公司编码
	 * @param orderSn
	 *            订单号
	 * @return
	 */
	public boolean dealSendPolicyMail(String insureTypeCode, String orderSn) {

		InsureTransferNew tInsureTransferNew = new InsureTransferNew();
		String ErrMsg;
		// HashMap<String, Object> toMap = new HashMap<String, Object>();
		List<HashMap<String, String>> insuredSnList = null;
		List<HashMap<String, String>> pathList = new ArrayList<HashMap<String, String>>();
		DownloadNet db = new DownloadNet();
		try {
			// 调用经代通之前判断，如果该订单返回信息已存在，不允许再访问，防止重复提交
			String sql = "select SDInformationInsured.id,SDInformationInsured.recognizeeSn, SDInformationInsured.insuredSn,SDInformationInsured.recognizeeName,SDInformationRiskType.appStatus, "
					+ " SDInformationRiskType.electronicCout,SDInformationRiskType.electronicPath,SDInformationRiskType.policyNo,SDInformationRiskType.validateCode "
					+ " from SDInformationInsured,SDInformationRiskType where SDInformationInsured.orderSn = SDInformationRiskType.orderSn"
					+ " and SDInformationRiskType.informationSn = SDInformationInsured.informationSn and SDInformationInsured.recognizeeSn = SDInformationRiskType.recognizeeSn and SDInformationInsured.orderSn=?";
			String[] sqltemp = { orderSn };
			insuredSnList = new GetDBdata().query(sql, sqltemp);
			QueryBuilder qb = new QueryBuilder(sql);
			qb.add(orderSn);
			DataTable dt = qb.executeDataTable();
			if (dt != null && dt.getRowCount() > 0) {
				for (int ii=0;ii<dt.getRowCount();ii++) {
					
					String insuredSn = dt.getString(ii, 2);
					String recognizeeSn = dt.getString(ii, 1);
					String recognizeeName = dt.getString(ii, 3);
					String appStatus = dt.getString(ii, 4);
					String policyComPath = dt.getString(ii, 5);
					String policyComDownPath = dt.getString(ii, 6);
					String policyNo = dt.getString(ii, 7);
					String validateCode = dt.getString(ii, 8);
					if (insuredSn != null && !"".equals(insuredSn)) {
						SDInformationRiskTypeSchema sdInformationSchema = new SDInformationRiskTypeSchema();
						SDInformationRiskTypeSet sdInformationSet = new SDInformationRiskTypeSet();
						sdInformationSet = sdInformationSchema.query(new QueryBuilder("where OrderSn = ? and RecognizeeSn = ?", orderSn, recognizeeSn));
						Transaction trans = new Transaction();
						StringBuffer elePathSQL = new StringBuffer();
						if (sdInformationSet != null && sdInformationSet.size() > 0) {
							sdInformationSchema = sdInformationSet.get(0);
							String policyPath = "";
							String path = sdInformationSchema.getelectronicPath();
							String insureDate = sdInformationSchema.getinsureDate();
							if (StringUtil.isEmpty(insureDate)) {
								insureDate = DateUtil.toDateTimeString(sdInformationSchema.getcreateDate());
							}
							if ("2007".equals(insureTypeCode) && "1".equals(appStatus)) {
								// 平安生成电子保单，并发送
								policyPath = tInsureTransferNew.checkElectronicPolicyIsSuccess(orderSn, insuredSn,policyNo,validateCode,path,insureDate);
								if (StringUtil.isNotEmpty(policyPath)) {
									HashMap<String, String> map = new HashMap<String, String>();
									map.put("recognizeeName", recognizeeName);
									map.put("path", policyPath);
									map.put("insuredSn", insuredSn);
									pathList.add(map);
								}
							} else if (("2023".equals(insureTypeCode)) && "1".equals(appStatus)) {
								// 华泰取电子保单路并发送
								try {
									String risktype = "";
									String sql1 = "select riskType from SDInformation where ordersn=?";
									DataTable dt1 = new QueryBuilder(sql1, orderSn).executeDataTable();
									if (dt1.getRowCount() > 0) {
										risktype = dt1.getString(0, 0);
									}
									if (StringUtil.isNotEmpty(risktype) && "11".equals(risktype)) {
										HTElectronicPolicy htP = new HTElectronicPolicy();
										policyPath = htP.getPolicyPath(orderSn, insuredSn,policyNo, path, insureDate);
									} else {
										policyPath = db.EpolicyDeal(orderSn, policyComPath + "", insureTypeCode, insuredSn, policyNo, path, insureDate);
									}
									if (StringUtil.isNotEmpty(policyPath)) {
										HashMap<String, String> map = new HashMap<String, String>();
										map.put("path", policyPath);
										map.put("insuredSn", insuredSn);
										map.put("recognizeeName", recognizeeName);
										pathList.add(map);
									}
								} catch (Exception e) {
									logger.error("电子发送保单异常：" + e.getMessage(), e);
								}
							} else if (("2071".equals(insureTypeCode) ||"2049".equals(insureTypeCode) || "1048".equals(insureTypeCode)) && "1".equals(appStatus)) {
								// 取電子保單發送
								// policyPath = toMap.get("remark1")+"";
								policyPath = policyComDownPath + "";
								if (StringUtil.isNotEmpty(policyPath)) {
									HashMap<String, String> map = new HashMap<String, String>();
									map.put("path", policyPath);
									map.put("recognizeeName", recognizeeName);
									map.put("insuredSn", insuredSn);
									pathList.add(map);
									int i = policyPath.lastIndexOf("/") + 1;
									String name = policyPath.substring(i, policyPath.length());
									db.saveOrdersPrint(orderSn, name, policyPath, insuredSn, "下载开始");
								}
							} else if (insureTypeCode.startsWith("2034")) {
								//tInsureTransferNew.sendExcel(orderSn, insuredSn);
							}
							// 保存电子保单物理路径（网站）
							elePathSQL.append("UPDATE sdinformationrisktype ");
							elePathSQL.append("SET electronicPath = ? ");
							elePathSQL.append("WHERE orderSn = ? AND recognizeeSn = ? ");
							QueryBuilder elePathQB = new QueryBuilder();
							elePathQB.setSQL(elePathSQL.toString());
							elePathQB.add(policyPath);
							elePathQB.add(orderSn);
							elePathQB.add(recognizeeSn);
							trans.add(elePathQB);
						}
						if (!trans.commit()) {
							logger.error("====淘宝对接数据=========电子保单路径修改失败！");
							return false;
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("淘宝对接数据，发送电子保单失败！" + e.getMessage(), e);
			ErrMsg = "淘宝对接数据，发送电子保单失败！";
			ActionUtil.sendInsureAlarmMailByOrderSn(orderSn, ErrMsg);
		} finally {
			if (insuredSnList != null && insuredSnList.size() > 0 && pathList.size() > 0) {
				db.getGeneratepolicy(insuredSnList, pathList, orderSn, insureTypeCode);
			}
		}

		return true;
	}

	/**
	 * 处理各个表的关联关系；完善CMS后台淘宝数据订单查询
	 * @param orderSn
	 * @return
	 */
	public boolean dealDataId(String orderSn){
		
		Transaction trans = new Transaction();
		//订单表SDOrders
		StringBuffer orderIdSQL = new StringBuffer();
		String orderID = "";
		orderIdSQL.append(" SELECT Id FROM SDOrders WHERE OrderSn = ? ");
		DataTable sdorderDT = new QueryBuilder(orderIdSQL.toString(), orderSn).executeDataTable();
		if (sdorderDT.getRowCount() > 0) {
			orderID = sdorderDT.getString(0, 0);
		}else{
			logger.warn(" 没有得到订单信息 （{}）", orderSn);
			return false;
		}
		DataTable sdorder_id = new QueryBuilder("SELECT sdorder_id FROM SDInformation WHERE OrderSn = ?", orderSn).executeDataTable();
		if (sdorder_id.getRowCount() > 0) {
			if(sdorder_id.getString(0, 0)!=null && !"".equals(sdorder_id.getString(0, 0)) 
					&&!"null".equals(sdorder_id.getString(0, 0))){
				return true;
			}
		}else{
			logger.warn(" 没有得到订单信息 （{}）", orderSn);
			return false;
		}
		//订单详细表SDInformation
		StringBuffer informationIdSQL = new StringBuffer();
		String informationID = "";
		String informationSn = "";
		String productID = "";
		// 保险期限
		String period = "";
		// 计划编码
		String planCode = "";
		// 投保年龄
		String textAge = "";
		// 份数
		String appMult = "";
		String chargeYear = "";
		String recognizeeSex = "";
		// 折扣
		String discountRates = "";
		informationIdSQL.append(" SELECT t.Id,t.InformationSn,t.productId,t.ensure,t.planCode,t.textAge,t.appMult,t.discountRates,t.chargeYear,s.recognizeeSex FROM SDInformation t, sdinformationinsured s WHERE t.informationSn = s.informationSn and t.OrderSn = ? ");
		DataTable sdinformationDT = new QueryBuilder(informationIdSQL.toString(), orderSn).executeDataTable();
		if (sdinformationDT.getRowCount() > 0) {
			informationID = sdinformationDT.getString(0, 0);
			informationSn = sdinformationDT.getString(0, 1);
			productID = sdinformationDT.getString(0, 2);
			period = sdinformationDT.getString(0, 3);
			planCode = sdinformationDT.getString(0, 4);
			textAge = sdinformationDT.getString(0, 5);
			appMult = sdinformationDT.getString(0, 6);
			discountRates = sdinformationDT.getString(0, 7);
			chargeYear = sdinformationDT.getString(0, 8);
			recognizeeSex = sdinformationDT.getString(0, 9);
		}else{
			logger.warn(" 没有得到订单详细信息 （{}）", orderSn);
			return false;
		}
		//更新订单详细表
		StringBuffer updateSDInformationSQL = new StringBuffer();
		updateSDInformationSQL.append("UPDATE sdinformation set sdorder_id=? where orderSn=? ");
		QueryBuilder sdinformationQB = new QueryBuilder();
		sdinformationQB.setSQL(updateSDInformationSQL.toString());
		sdinformationQB.add(orderID);
		sdinformationQB.add(orderSn);
		trans.add(sdinformationQB);
		
		//更新订单配置表
		StringBuffer updateSDorderItemSQL = new StringBuffer();
		updateSDorderItemSQL.append("UPDATE sdorderitem set sdorder_id=? where orderSn=? ");
		QueryBuilder sdorderitemQB = new QueryBuilder();
		sdorderitemQB.setSQL(updateSDorderItemSQL.toString());
		sdorderitemQB.add(orderID);
		sdorderitemQB.add(orderSn);
		trans.add(sdorderitemQB);
		
		//更新责任表
		StringBuffer updatesdinformationDutySQL = new StringBuffer();
		updatesdinformationDutySQL.append("UPDATE sdinformationDuty set sdinformation_id=? where informationSn=? ");
		QueryBuilder sdinformationDutyQB = new QueryBuilder();
		sdinformationDutyQB.setSQL(updatesdinformationDutySQL.toString());
		sdinformationDutyQB.add(informationID);
		sdinformationDutyQB.add(informationSn);
		trans.add(sdinformationDutyQB);
		
		//更新投保人表
		StringBuffer updatesdinformationAppntSQL = new StringBuffer();
		updatesdinformationAppntSQL.append("UPDATE sdinformationAppnt set sdinformaiton_id=? where informationSn=? ");
		QueryBuilder sdinformationAppntQB = new QueryBuilder();
		sdinformationAppntQB.setSQL(updatesdinformationAppntSQL.toString());
		sdinformationAppntQB.add(informationID);
		sdinformationAppntQB.add(informationSn);
		trans.add(sdinformationAppntQB);
		
		//更新被保人表
		StringBuffer updatesdinformationInsuredSQL = new StringBuffer();
		updatesdinformationInsuredSQL.append("UPDATE sdinformationInsured set sdinformation_id=? where informationSn=? ");
		QueryBuilder sdinformationInsuredQB = new QueryBuilder();
		sdinformationInsuredQB.setSQL(updatesdinformationInsuredSQL.toString());
		sdinformationInsuredQB.add(informationID);
		sdinformationInsuredQB.add(informationSn);
		trans.add(sdinformationInsuredQB);
		 
		//订单详细表SDInformation
		StringBuffer informationInsuredIdSQL = new StringBuffer();
		
		informationInsuredIdSQL.append(" SELECT Id,RecognizeeSn FROM SDInformationInsured WHERE OrderSn = ? ");
		DataTable sdinformationInsuredDT = new QueryBuilder(informationInsuredIdSQL.toString(), orderSn).executeDataTable();
		if (sdinformationInsuredDT.getRowCount() > 0) {
			for(int i=0;i<sdinformationInsuredDT.getRowCount();i++){
				String informationInsuredID = sdinformationInsuredDT.getString(i, 0);
				String recognizeeSn = sdinformationInsuredDT.getString(i, 1);
				//更新健康告知表
				StringBuffer updatesSDInsuredHealthSQL = new StringBuffer();
				updatesSDInsuredHealthSQL.append("UPDATE SDInsuredHealth set sdinformationinsured_id=? where informationSn=? and recognizeeSn=? ");
				QueryBuilder SDInsuredHealthQB = new QueryBuilder();
				SDInsuredHealthQB.setSQL(updatesSDInsuredHealthSQL.toString());
				SDInsuredHealthQB.add(informationInsuredID);
				SDInsuredHealthQB.add(informationSn);
				SDInsuredHealthQB.add(recognizeeSn);
				trans.add(SDInsuredHealthQB);
				
				//更新配置表2表
				StringBuffer updatesSDOrderItemOthSQL = new StringBuffer();
				updatesSDOrderItemOthSQL.append("UPDATE SDOrderItemOth set sdinformationinsured_id=? where recognizeeSn=? ");
				QueryBuilder SDOrderItemOthQB = new QueryBuilder();
				SDOrderItemOthQB.setSQL(updatesSDOrderItemOthSQL.toString());
				SDOrderItemOthQB.add(informationInsuredID);
				//SDOrderItemOthQB.add(informationSn);
				SDOrderItemOthQB.add(recognizeeSn);
				trans.add(SDOrderItemOthQB);
				
				//更新产品投保要素表
				StringBuffer updatesSDInformationInsuredElementsSQL = new StringBuffer();
				updatesSDInformationInsuredElementsSQL.append("UPDATE SDInformationInsuredElements set sdinformationinsured_id=? where informationSn=? and recognizeeSn=? ");
				QueryBuilder SDInformationInsuredElementsQB = new QueryBuilder();
				SDInformationInsuredElementsQB.setSQL(updatesSDInformationInsuredElementsSQL.toString());
				SDInformationInsuredElementsQB.add(informationInsuredID);
				SDInformationInsuredElementsQB.add(informationSn);
				SDInformationInsuredElementsQB.add(recognizeeSn);
				trans.add(SDInformationInsuredElementsQB);
				
				//更新保单信息表
				StringBuffer updatesSDInformationRiskTypeSQL = new StringBuffer();
				updatesSDInformationRiskTypeSQL.append("UPDATE SDInformationRiskType set sdinformationinsured_id=?,sdorder_id=? where informationSn=? and recognizeeSn=? ");
				QueryBuilder SDInformationRiskTypeQB = new QueryBuilder();
				SDInformationRiskTypeQB.setSQL(updatesSDInformationRiskTypeSQL.toString());
				SDInformationRiskTypeQB.add(informationInsuredID);
				SDInformationRiskTypeQB.add(orderID);
				SDInformationRiskTypeQB.add(informationSn);
				SDInformationRiskTypeQB.add(recognizeeSn);
				trans.add(SDInformationRiskTypeQB);
				
				//更新受益人信息表
				/*StringBuffer updatesSDInformationRiskTypeSQL = new StringBuffer();
				updatesSDInformationRiskTypeSQL.append("UPDATE SDInformationRiskType set sdinformationinsured_id=? where informationSn=? and recognizeeSn=? ");
				QueryBuilder SDInformationRiskTypeQB = new QueryBuilder();
				SDInformationRiskTypeQB.setSQL(updatesSDInformationRiskTypeSQL.toString());
				SDInformationRiskTypeQB.add(informationInsuredID);
				SDInformationRiskTypeQB.add(informationSn);
				SDInformationRiskTypeQB.add(recognizeeSn);
				trans.add(SDInformationRiskTypeQB);*/
			}
		}
		if(!trans.commit()){
			logger.error("淘宝订单 没有得到订单详细信息 ，订单出单信息保存失败（{}）", orderSn);
			return false;
		}
		
		// 判断产品是否是复杂险
		int count = new QueryBuilder("SELECT count(1) FROM sdproduct WHERE ComplicatedFlag = 'Y' AND ProductID = ?", productID).executeInt();
		// 是复杂险取得分项责任保费
		if (count > 0) {
			DataTable appDt = new QueryBuilder("select AppFactorCode,FactorType from FERiskAppFactorB where IsPremCalFacotor = 'Y' and RiskCode = ? and FactorType != 'Duty' ", productID).executeDataTable();
			if (appDt != null && appDt.getRowCount() > 0) {
				int appDtRowCount = appDt.getRowCount();
				FEMRiskFactorList[] fEMRiskFactorList = new FEMRiskFactorList[appDtRowCount];
				String factorType = "";
				for (int i = 0; i < appDtRowCount; i++) {
					fEMRiskFactorList[i] = new FEMRiskFactorList();
					factorType = appDt.getString(i, 1);
					fEMRiskFactorList[i].setIsPremCalFacotor("Y");
					fEMRiskFactorList[i].setAppFactorCode(appDt.getString(i, 0));
					fEMRiskFactorList[i].setFactorType(factorType);
					
					if ("Plan".equals(factorType)) {
						fEMRiskFactorList[i].setFactorValue(planCode);
					} else if ("Period".equals(factorType)) {
						fEMRiskFactorList[i].setFactorValue(period);
					} else if ("TextAge".equals(factorType)) {
						if (StringUtil.isNotEmpty(textAge)) {
							String birthday = new OrderConfigNewServiceImpl().getBrithdayByFactor(null, textAge);
							fEMRiskFactorList[i].setFactorValue(birthday);
						}
					} else if ("Mult".equals(factorType)) {
						fEMRiskFactorList[i].setFactorValue(appMult);
					}else if("Sex".equals(factorType)){
						fEMRiskFactorList[i].setFactorValue(recognizeeSex );
					}else if("FeeYear".equals(factorType)){
						fEMRiskFactorList[i].setFactorValue(chargeYear);
					}
				}
				
				DataTable dutyDt = new QueryBuilder("select dutySn, amt from sdinformationDuty where orderSn = ?", orderSn).executeDataTable();
				if (dutyDt != null && dutyDt.getRowCount() > 0) {
					int dutyDtRowCount = dutyDt.getRowCount();
					CalProductPrem[] calProductPrem = new CalProductPrem[dutyDtRowCount];
					for (int i = 0; i < dutyDtRowCount; i++) {
						calProductPrem[i] = new CalProductPrem();
						calProductPrem[i].setDutyCode(dutyDt.getString(i, 0));
						calProductPrem[i].setAmnt(dutyDt.getString(i, 1));
						calProductPrem[i].setRiskCode(productID);
						calProductPrem[i].setFEMRiskFactorList(fEMRiskFactorList);
					}
					
					Map<String, Object> mp = new HashMap<String, Object>();
					String[] discountPrice = null;
					String[] prem = null;
					mp.put("CalProductPrem", calProductPrem);
					ProductPremResponse ProductPrem;
					try {
						ProductPrem = ProductWebservice.ProductPremSereviceImpl(mp, null);
					
						if ("2".equals(ProductPrem.getResultDTO().getResultCode())) {
							
							String msg = ProductPrem.getResultDTO().getResultInfoDesc();
							if (StringUtil.isEmpty(msg)) {
								msg = "责任保额组合错误！";
							}
							logger.info("淘宝订单{}", msg+"（"+orderSn+"）");
							return false;
						}
						
						discountPrice = ProductPrem.getDiscountPrice();
						prem = ProductPrem.getPrem();
						trans = new Transaction();
						String dutyDiscountRates = "";
						if ("101401030".equals(productID) || "101401032".equals(productID)) {
							dutyDiscountRates = discountRates;
						}
						
						if (discountPrice != null && discountPrice.length > 0 && prem != null && prem.length > 0) {
							String updateSql = "";
							for (int i = 0; i < dutyDtRowCount; i++) {
								updateSql = "";
								if (StringUtil.isNotEmpty(discountPrice[i])) {
									updateSql += (",discountPrice='"+discountPrice[i]+"' ");
								}
								if (StringUtil.isNotEmpty(prem[i])) {
									updateSql += (",premium='"+prem[i]+"' ");
								}
								if (StringUtil.isNotEmpty(dutyDiscountRates)) {
									updateSql += (",discountRates='"+dutyDiscountRates+"' ");
								}
								if (StringUtil.isNotEmpty(updateSql)) {
									trans.add(new QueryBuilder("update sdinformationDuty set "+updateSql.substring(1) + " where orderSn = ? and dutySn = ?", orderSn, dutyDt.getString(i, 0)));
								}
							}
							
							if(!trans.commit()){
								logger.error("淘宝订单更新责任保费失败（{}）", orderSn);
								return false;
							}
						}
						
						
					} catch (Exception e) {
						logger.error("淘宝订单取得责任分项保费调用保费试算接口失败！（"+orderSn+"）" + e.getMessage(), e);
					}
				}
				
			}
		}
		return true;
	}
	/**
	 * 记录经代通发送时间、返回经代通时间
	 * 
	 * @param content
	 *            记录内容
	 */
	public void writeTXT(String content) {
		FileWriter fw = null;
		try {
			String filepath = Config.getContextRealPath() + File.separator + "taobao" + File.separator + PubFun.getCurrentDate() + ".txt";
			File f = new File(filepath);
			if (!f.exists()) {
				f.createNewFile();
			}
			fw = new FileWriter(f, true);
			fw.write(PubFun.getCurrentTime() + " : " + content + "\r\n");
			fw.flush();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);

		} finally {
			try {
				if (fw != null)
					fw.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * 保存金代通传过来的XML文档内容
	 * 
	 * @param request
	 * @return
	 */
	public boolean saveXML(Document document,String orderSn,String dir) {
		//Document document = this.deealStreamToXML(request);
		String filepath = Config.getContextRealPath() + File.separator + "taobao" + File.separator +PubFun.getCurrentDate() +File.separator+dir+File.separator+orderSn+".xml";
		
		File tFile = new File(filepath);
		File parent = tFile.getParentFile(); 
		if(!parent.exists()){ 
			parent.mkdirs(); 
		}
		
		XMLOutputter out;
		try {
			out = new XMLOutputter();
			out.output(document, new FileOutputStream(filepath));
		} catch (Exception e) {
			logger.error("=====淘宝对接数据=====保存金代通发送的xml文件失败！" + e.getMessage(), e);
			return false;
		}
		return true;
	}

	public Document deealStreamToXML(HttpServletRequest request)  {
		try {
			return streamToXML(request.getInputStream());
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return null;

	}
	
	public Document streamToXML(InputStream in)  {
		Document doc = null;
		try {
			SAXBuilder parser=new SAXBuilder();
			doc=parser.build(in);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} 
		Format format = Format.getPrettyFormat();
		format.setEncoding("UTF-8");
		XMLOutputter a = new XMLOutputter(format);
		logger.info(a.outputString(doc));
		return doc;

	}
	/**
	 * 获取请求IP
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

	/**
	 * @param args
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * @throws DocumentException
	 */
	public static void main(String[] args) {
		ParseXMLToObject tParseXMLToObject = new ParseXMLToObject();
		/*
		 * SAXBuilder sb = new SAXBuilder(); Document doc = null; try { doc =
		 * sb.build("C:/SDInsuredHealth.xml"); } catch (JDOMException e) { //
		 * (IOException e) { logger.error(e.getMessage(), e); } // 构造文档对象
		 * 
		 * Element root = doc.getRootElement(); LinkedHashMap<Object, String>
		 * mLMap = new LinkedHashMap<Object, String>(); 取得订单信息 Element
		 * policyData = root.getChild("Request"); List<Element> elementList =
		 * policyData.getChildren();
		 * 
		 * try { for (Element element : elementList) {
		 * if("SDInsDuty".equals(element
		 * .getName().trim())||"SDInfRiskType".equals
		 * (element.getName().trim())||
		 * "SDInsured".equals(element.getName().trim())
		 * ||"SDInfElements".equals(
		 * element.getName().trim())||"SDItemOth".equals
		 * (element.getName().trim()
		 * )||"SDHealth".equals(element.getName().trim())){ List<Element>
		 * childPolicyList = element.getChildren(); for(Element
		 * element1:childPolicyList){
		 * mLMap.put(tParseXMLToObject.ElementToObj(element1), "insert"); }
		 * }else{ mLMap.put(tParseXMLToObject.ElementToObj(element), "insert");
		 * } } } catch (Exception e) {  
		 * logger.error(e.getMessage(), e); }
		 * 
		 * SDOrder order = new SDOrder(); order.setOrderSn(PubFun.GetOrderSn());
		 * order.setOrderStatus(SDOrderStatus.temptorysave);
		 * order.setTotalAmount(new BigDecimal("5.56")); mLMap.put(order,
		 * "insert"); try { // tAction.saveOrder(mLMap);
		 * tParseXMLToObject.saveAll(mLMap);
		 * 
		 * } catch (Exception e) { 
		 * logger.error(e.getMessage(), e); }
		 */
		// tParseXMLToObject.deleteAll("2013000000008030");
		 tParseXMLToObject.dealDataId("TB20130000000067");
		/*tParseXMLToObject.writeTXT("开始1...");
		tParseXMLToObject.writeTXT("结束2...");
		tParseXMLToObject.writeTXT("开始3...");
		tParseXMLToObject.writeTXT("结束4...");*/
	}

}
