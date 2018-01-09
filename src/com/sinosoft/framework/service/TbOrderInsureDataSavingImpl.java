package com.sinosoft.framework.service;

import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.dubbo.interfaces.TbOrderInsureDataSaving;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.TBSDAction;
import com.sinosoft.jdt.ParseXMLToObject;
import org.fusesource.hawtbuf.ByteArrayInputStream;
import org.jdom.Document;
import org.jdom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;
public class TbOrderInsureDataSavingImpl implements TbOrderInsureDataSaving {
	private static final Logger logger = LoggerFactory.getLogger(TbOrderInsureDataSavingImpl.class);
	@Autowired
	private TbOrderInfoDataSave tbOrderInfoDataSave;
	
	@Override
	public String saveData(String xmlReq) {
		logger.info("经代通请求保存淘宝-去哪儿-蚂蚁核保数据报文===={}", xmlReq);
		if(StringUtil.isEmpty(xmlReq)){//空数据校验
			logger.error("==========接收到经代通请求数据为空==========");
			return "EMPTY_DATA";
		}
		ParseXMLToObject parseXMLToObject = new ParseXMLToObject();
		String start = new Random().nextInt(10000) + "-" + System.currentTimeMillis() ;
		parseXMLToObject.writeTXT("经带通请求开始：---"+start + " time："+PubFun.getCurrentDate()+" "+PubFun.getCurrentTime()+"");
		try {
			InputStream ins = new ByteArrayInputStream(xmlReq.getBytes("UTF-8"));
			Document doc = parseXMLToObject.streamToXML(ins);
			String result = readXMLtoMap(doc, parseXMLToObject);
			logger.info("==========处理结果：=========={}", result);
		    return result;
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public String readXMLtoMap(Document doc,ParseXMLToObject parseXMLToObject){
		
		if(null == doc){
			logger.error("==========字符串转换dom失败==========");
			return "trans_error";
		}
		Element rootElm = doc.getRootElement();
		if(null == rootElm){
			logger.error("==========找不到根节点==========");
			return "badxml_emptyroot_error";
		}
		/* 取得header部分 */
		Element headerElm = rootElm.getChild("Header");
		if (null == headerElm) {
			logger.error("==========找不到Header节点==========");
			return "badxml_emptyheader_error";				
		}
		 /*取得订单号*/
		String orderSn = headerElm.getChildTextTrim("orderSn");
		if(StringUtil.isEmpty(orderSn)){
			logger.error("==========订单号为空==========");
			return "empty_ordersn_error";
		}
		/** 是否已经承保*/
		int policyNoCount = new QueryBuilder("select count(1) from sdinformationrisktype where orderSn=? and policyNo IS NOT NULL AND policyNo<>'' ",orderSn).executeInt();
		if(policyNoCount>=1){
			logger.info("订单号：{},已经承保，不需要重新发送核保报文！", orderSn);
			return SUCCESS;
		}
		/* 取得订单信息 */
		Element policyData = rootElm.getChild("Request");
		//订单表结构节点
		List<Element> policyList = policyData.getChildren("SDOrder");
		String tbTradeSeriNo = policyList.get(0).getChildText("tbTradeSeriNo");
		DataTable dt = new QueryBuilder("select tbTradeSeriNo from sdorders where orderSn=? limit 1",orderSn).executeDataTable();
		if(dt!=null && dt.getRowCount()>=1){
			String oldTbTradeSeriNo = String.valueOf(dt.getString(0, 0));
			if(!oldTbTradeSeriNo.equals(tbTradeSeriNo)){
				String[] argArr = {orderSn, oldTbTradeSeriNo, tbTradeSeriNo};
				logger.warn("系统中，订单号：{} 不唯一，已对应交易流水号：{},新交易流水号：{}", argArr);
				return "policy_error";
			}
		}
		LinkedHashMap<Object,String> resultMap = new LinkedHashMap<Object,String>();
		try {
			resultMap = this.readXML(policyData,parseXMLToObject,orderSn);
		} catch (Exception e) {
			logger.error("============淘宝对接数据,数据保存异常！=============="+ e.getMessage(), e);
			return FAIL;
		} 
		//批量存储数据
		if(tbOrderInfoDataSave.saveAll(parseXMLToObject,resultMap)){
			logger.info("============淘宝对接数据,数据存储成功！=============");
			String sdorder_id="";
			int k=1;
			while("".equals(sdorder_id)||sdorder_id==null){
				try {
					if(k==3)break;
					Thread.sleep(500);
					if(!parseXMLToObject.dealDataId(orderSn)){
						logger.error("处理关联表数据失败（{}）", orderSn);
					}
					StringBuffer querySQL = new StringBuffer(" SELECT sdorder_id FROM sdinformation WHERE orderSn = ? ");
					QueryBuilder tQueryBuilder = new QueryBuilder(querySQL.toString());
					tQueryBuilder.add(orderSn);
					sdorder_id = tQueryBuilder.executeString();
					k = k+1;
				} catch (InterruptedException e) {
					logger.error(e.getMessage(), e);
				}
			}
			if(!parseXMLToObject.saveXML(doc,orderSn,"underwriting")){
				logger.error("淘宝订单xml数据保存到本地失败orderSn（{}）", orderSn);
			}
			String end = new Random().nextInt(10000) + "-" + System.currentTimeMillis() ;
			parseXMLToObject.writeTXT("暂存結束：---"+end + " - 订单号-" +orderSn);
			TBSDAction tbsdAction = new TBSDAction();
			if (tbsdAction.isTbsdInsured(orderSn)) {
				tbsdAction.turnChannelToTbsd(orderSn);
			} else {
				if(orderSn.startsWith("QN")){//去哪儿网的处理
					new QueryBuilder(" UPDATE sdorders SET channelSn='qunar' WHERE orderSn=? ", orderSn).executeNoQuery();
				}else if (orderSn.startsWith("TBMY")) {
					// 蚂蚁保险
					new QueryBuilder(" UPDATE sdorders SET channelSn='tb_my' WHERE orderSn=? ", orderSn).executeNoQuery();
				} else {
					new QueryBuilder(" UPDATE sdorders SET channelSn='tb_ht' WHERE orderSn=? ", orderSn).executeNoQuery();
				}
			}
		}else{
			logger.error("===========淘宝对接数据,数据存储失败，请检查订单数据完整性！===============");
			return "save_error";
		}
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public LinkedHashMap<Object, String> readXML(Element policyData,ParseXMLToObject parseXMLToObject,String orderSn) throws Exception{
		LinkedHashMap<Object, String> resultMap = new LinkedHashMap<Object, String>();
		//先根据订单号删除所有订单信息，主要用于保险公司需要二次核保的情况
		if(orderSn!=null && !"".equals(orderSn)){
			if(!parseXMLToObject.deleteAll(orderSn)){
				return null;
			}
		}
		//订单表结构节点
		List<Element> policyList = policyData.getChildren();
		Object tObject = null;
		for(Element element:policyList){
			if("SDInsDuty".equals(element.getName().trim())||"SDInfRiskType".equals(element.getName().trim())||"SDInsured".equals(element.getName().trim())
					||"SDInfElements".equals(element.getName().trim())||"SDItemOth".equals(element.getName().trim())||"SDHealth".equals(element.getName().trim())){
				List<Element> childPolicyList = element.getChildren();
				int tLength = childPolicyList.size();
				List<Object> tList = new ArrayList<Object>();
				if(tLength>=2){
					for(Element element1:childPolicyList){
						tList.add(parseXMLToObject.ElementToObj(element1));
					}
				}else{
					for(Element element1:childPolicyList){
						tObject = parseXMLToObject.ElementToObj(element1);
					}
				} 
				if(tList!=null && tList.size()>=1){
					resultMap.put(tList, "insert");
				}
			}else{
				tObject = parseXMLToObject.ElementToObj(element);
			}
			
			if(tObject!=null){
				resultMap.put(tObject, "insert");
			} 
		}
        if(resultMap.size()>=1){ 
        	return resultMap;
        }
		/* 其他情况返回空 */
		return null;
	}
}
