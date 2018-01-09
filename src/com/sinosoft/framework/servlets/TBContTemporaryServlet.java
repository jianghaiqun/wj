package com.sinosoft.framework.servlets;

import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.inter.TBSDAction;
import com.sinosoft.jdt.ParseXMLToObject;
import org.jdom.Document;
import org.jdom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

/**
 * 金代通对接淘宝数据，用于电商数据存储
 * @author sinosoft
 *
 */
public class TBContTemporaryServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(TBContTemporaryServlet.class);
	 /**
	 * 
	 */
	private static final long serialVersionUID = 7859315450853195045L;
	ParseXMLToObject tParseXMLToObject = new ParseXMLToObject();
	 
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	@SuppressWarnings("static-access")
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String result = "in_success";
		String orderSn="";
		// 返回给客户端
		OutputStream out = response.getOutputStream();
		Document document = new Document();
		String requestIP = tParseXMLToObject.getIpAddr(request);
		Random r = new Random();
		String start = r.nextInt(10000) + "-" + System.currentTimeMillis() ;
		tParseXMLToObject.writeTXT("经带通请求开始："+"请求IP:"+requestIP+"---"+start + " time："+PubFun.getCurrentDate()+" "+PubFun.getCurrentTime()+"");
		boolean saveFlag = false;
		try {
			// String tOrderSn = request.getParameter("orderSn");
			// 保存金代通传过来的xml
		    document = tParseXMLToObject.deealStreamToXML(request); 
		    if(document==null){
		    	saveFlag = true;
		    	logger.error("淘宝数据--未接收到经带通发送数据！- 订单号-{}", orderSn);
				result="in_error";
		    }
		    //重新获取订单号
		    Element root = document.getRootElement();
			if (null == root) {
				saveFlag = true;
				throw new NullPointerException();
			} 
			/* 取得header部分 */
			Element eleHeader = root.getChild("Header");
			if (null == eleHeader) {
				saveFlag = true;
				result="in_error";
				throw new NullPointerException();
				
			} 
	        /*取得订单号*/
			orderSn = eleHeader.getChildTextTrim("orderSn");
			//判断该订单号是否已承保,如果已承保，则不进行任何处理
			int policyNoCount = new QueryBuilder("select count(1) from sdinformationrisktype where orderSn=? and policyNo IS NOT NULL AND policyNo<>'' ",orderSn).executeInt();
			if(policyNoCount>=1){
				logger.info("订单号：{},已经承保，不需要重新发送核保报文！", orderSn);
				result = "un_success";
				out.write(result.getBytes("utf-8"));
				out.flush();
				out.close();
				return ;
			}
		    // 解析XML文件
		    if(!readXMLtoMap(document)){
		    	saveFlag = true;
				logger.error("淘宝数据--核保数据保存失败！- 订单号-{}", orderSn);
				result="in_error";
			}
			tParseXMLToObject.writeTXT("暂存開始："+"请求IP:"+requestIP+"---"+start + " - 订单号-" +orderSn);
			logger.info("result:{}", result);
			//记录与金代通交易信息
		} catch (Exception e) {
			saveFlag = true;
			logger.error("处理淘宝交易失败， 订单号-" + orderSn + e.getMessage(), e);
			result="in_error";
		}finally{
			if(!saveFlag){
				logger.info("处理关联表数据开始!");
				String sdorder_id="";
				int k=1;
				while("".equals(sdorder_id)||sdorder_id==null){
					try {
						if(k==3)break;
						Thread.sleep(1000);
						if(!tParseXMLToObject.dealDataId(orderSn)){
							logger.error("处理关联表数据失败（{}）", orderSn);
						}
						StringBuffer querySQL = new StringBuffer(" SELECT sdorder_id FROM sdinformation WHERE orderSn = ? ");
						QueryBuilder tQueryBuilder = new QueryBuilder(querySQL.toString());
						tQueryBuilder.add(orderSn);
						Object sdorderid = tQueryBuilder.executeOneValue();
						if (sdorderid != null && !"".equals(sdorderid)) {
							sdorder_id = tQueryBuilder.executeOneValue().toString();
						}
						k = k+1;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						logger.error(e.getMessage(), e);
					}
				}
				StringBuffer querySQL = new StringBuffer(" SELECT sdorder_id FROM sdinformation WHERE orderSn = ? ");
				QueryBuilder tQueryBuilder = new QueryBuilder(querySQL.toString());
				tQueryBuilder.add(orderSn);
				Object sdorderid = tQueryBuilder.executeOneValue();
				if (sdorderid == null || "".equals(sdorderid)) {
					logger.error("处理关联表数据失败orderSn（{}）", orderSn);
					result="in_error";
				}
				//保存到本地
				if(!tParseXMLToObject.saveXML(document,orderSn,"underwriting")){
					logger.error("淘宝订单xml数据保存到本地失败orderSn（{}）", orderSn);
					result="in_error";
				}
				if("in_success".equals(result)){
					logger.info("淘宝订单数据--核保数据成功！orderSn（{}）", orderSn);
				}
				String end = r.nextInt(10000) + "-" + System.currentTimeMillis() ;
				tParseXMLToObject.writeTXT("暂存結束："+"请求IP:"+requestIP+"---"+end + " - 订单号-" +orderSn);
			}
			
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
			out.write(result.getBytes("utf-8"));
			out.flush();
			out.close();
		}
}

	@SuppressWarnings("unchecked")
	public boolean readXMLtoMap(Document doc) throws IOException {

		//重新获取订单号
	    Element root = doc.getRootElement();
		if (null == root) {
			throw new NullPointerException();
		} 
		/* 取得header部分 */
		Element eleHeader = root.getChild("Header");
		if (null == eleHeader) {
			throw new NullPointerException();
		} 
        /*取得订单号*/
		String orderSn = eleHeader.getChildTextTrim("orderSn");
		/* 取得订单信息 */
		Element policyData = root.getChild("Request");
		//订单表结构节点
		List<Element> policyList = policyData.getChildren("SDOrder");
		Element ele = policyList.get(0);
		String tbTradeSeriNo = ele.getChildText("tbTradeSeriNo");
		DataTable dt = new QueryBuilder("select tbTradeSeriNo from sdorders where orderSn=?",orderSn).executeDataTable();
		if(dt!=null && dt.getRowCount()>=1){
			String oldTbTradeSeriNo = String.valueOf(dt.getString(0, 0));
			if(!oldTbTradeSeriNo.equals(tbTradeSeriNo)){
				String[] argArr = {orderSn, oldTbTradeSeriNo, tbTradeSeriNo};
				logger.info("系统中，订单号：{} 不唯一，已对应交易流水号：{},新交易流水号：{}", argArr);
				return false;
			}
		}
		LinkedHashMap<Object,String> resultMap = new LinkedHashMap<Object,String>();
		try {
			resultMap = this.readXML(doc);
		} catch (Exception e1) {
			logger.error(e1.getMessage(), e1);
			return false;
		}
		//批量存储数据
		ParseXMLToObject tParseXMLToObject = new ParseXMLToObject();
		try {
			if(tParseXMLToObject.saveAll(resultMap)){
				logger.info("=======淘宝对接数据===================数据存储成功！");
			}else{
				logger.error("=======淘宝对接数据===================数据存储失败，请检查订单数据完整性！");
				return false;
			}
		} catch (Exception e) {
			logger.error("=======淘宝对接数据===================数据存储失败，请检查订单数据完整性！" + e.getMessage(), e);
			return false;
		} 
		return true;
	}

	@SuppressWarnings("unchecked")
	public LinkedHashMap<Object, String> readXML(Document doc) throws Exception {
		LinkedHashMap<Object, String> resultMap = new LinkedHashMap<Object, String>();
		ParseXMLToObject tParseXMLToObject = new ParseXMLToObject();
		/* 取得xml文件的根结点 */
		Element root = doc.getRootElement();
		if (null == root) {
			throw new NullPointerException();
		} 
		/* 取得header部分 */
		Element eleHeader = root.getChild("Header");
		if (null == eleHeader) {
			throw new NullPointerException();
		} 
        /*取得订单号*/
		String orderSn = eleHeader.getChildTextTrim("orderSn");
		 //先根据订单号删除所有订单信息，主要用于保险公司需要二次核保的情况
		if(orderSn!=null && !"".equals(orderSn)){
			if(!tParseXMLToObject.deleteAll(orderSn)){
				return null;
			}
		}
		/* 取得订单信息 */
		Element policyData = root.getChild("Request");
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
						tList.add(tParseXMLToObject.ElementToObj(element1));
					}
				}else{
					for(Element element1:childPolicyList){
						tObject = tParseXMLToObject.ElementToObj(element1);
					}
				} 
				if(tList!=null && tList.size()>=1){
					resultMap.put(tList, "insert");
				}
			}else{
				tObject = tParseXMLToObject.ElementToObj(element);
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
