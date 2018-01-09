package com.sinosoft.cms.dataservice;

import cn.com.sinosoft.util.DownloadNet;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.schema.SDTbsdInsuredSchema;
import com.wangjin.cms.orders.QueryOrders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jdom.Document;
import org.jdom.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RBCXUnderwriting extends Page {

	private String comId = "";
	
	public static Mapx initEditDialog(Mapx params) {
		String id = params.getString("ID");
		SDTbsdInsuredSchema tbsdInsured = new SDTbsdInsuredSchema();
		tbsdInsured.setID(id);
		tbsdInsured.fill();
		params = tbsdInsured.toMapx();
		return params;
	}
	
	public static Mapx init(Mapx params) {
		params.put("createDate", PubFun.getPrevMonthDay(DateUtil.getCurrentDate()));
		params.put("endCreateDate", DateUtil.getCurrentDate());
		params.put("underwritingStatusList", HtmlUtil.codeToOptions("YesOrNo", true));
		return params;
	}
	
	public static void dg1DataBind(DataGridAction dga) {
		String createDate = dga.getParams().getString("createDate");
		String endCreateDate = dga.getParams().getString("endCreateDate");
		String tbTradeSeriNo = dga.getParams().getString("tbTradeSeriNo");
		String orderSn = dga.getParams().getString("orderSn");
		String policyNo = dga.getParams().getString("policyNo");
		String underwritingStatus = dga.getParams().getString("underwritingStatus");

		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT a.tbTradeSeriNo, a.orderSn, d.insuredSn, b.policyNo, b.applyPolicyNo,");
		sb.append(" CASE WHEN b.policyNo LIKE 'RBCX-%' THEN '未承保' WHEN b.policyNo LIKE 'PJBS%' THEN '已承保' ELSE '异常' END AS underwritingStatus,");
		sb.append(" e.receiveDate AS receiveDate, b.svaliDate, f.productName, b.timePrem,c.applicantName, c.applicantIdentityTypeName,");
		sb.append(" c.applicantIdentityId, c.applicantBirthday, c.applicantSexName, c.applicantMobile, c.applicantMail,");
		sb.append(" d.recognizeeName, d.recognizeeIdentityTypeName, d.recognizeeIdentityId, d.recognizeeBirthday, d.recognizeeSexName,");
		sb.append(" 'wj' as channel");
		sb.append(" FROM sdOrders a, sdInformationRiskType b, sdInformationAppnt c, sdInformationInsured d, tradeInformation e, sdInformation f");
		sb.append(" WHERE a.ordersn = b.ordersn AND a.ordersn = d.ordersn  AND b.informationSn = c.informationSn AND b.recognizeeSn = d.recognizeeSn");
		sb.append(" AND e.ordid = a.ordersn AND a.ordersn = f.ordersn AND a.orderStatus = '7' AND f.insuranceCompany = '2005'");
		// 起始时间
		if (StringUtil.isNotEmpty(createDate)) {
			sb.append(" AND e.receiveDate >='" + createDate + " 00:00:00' ");
		} 
		if (StringUtil.isNotEmpty(endCreateDate)) {
			sb.append(" AND e.receiveDate <='" + endCreateDate + " 23:59:59' ");
		} 
		// 渠道订单号
		if (StringUtil.isNotEmpty(tbTradeSeriNo)) {
			sb.append(" AND a.tbTradeSeriNo LIKE '%" + tbTradeSeriNo.trim() + "%'");
		}
		// 订单号
		if (StringUtil.isNotEmpty(orderSn)) {
			sb.append(" AND a.orderSn LIKE '%" + orderSn.trim() + "%'");
		}
		// 保单号
		if (StringUtil.isNotEmpty(policyNo)) {
			sb.append(" AND b.policyNo LIKE '%" + policyNo.trim() + "%'");
		}
		// 是否已承保(我方生成的假保单号为未承保)
		if ("N".equals(underwritingStatus)) {
			sb.append(" AND b.policyNo LIKE 'RBCX-%'");
		} else if ("Y".equals(underwritingStatus)) {
			sb.append(" AND b.policyNo LIKE 'PJBS%'");
		}

		// 美行保数据查询(有渠道订单号时未淘宝订单，不需要查询美行保数据库)
		if (StringUtil.isEmpty(tbTradeSeriNo)) {
			sb.append(" UNION SELECT '' AS tbTradeSeriNo, b.ordersn, b.insuredSn, b.policyNo, b.applyPolicyNo,");
			sb.append(" CASE WHEN b.policyNo LIKE 'RBCX-%' THEN '未承保' WHEN b.policyNo LIKE 'PJBS%' THEN '已承保' ELSE '异常' END AS underwritingFlag,");
			sb.append(" e.receiveDate AS receiveDate, a.effective, a.productName, a.totalAmount, c.applicantName,");
			sb.append(" CASE WHEN c.applicantIdentityType = 0 THEN '身份证' WHEN c.applicantIdentityType = 7 THEN '护照' WHEN c.applicantIdentityType = 4 THEN '军人证件' ELSE '其他' END AS applicantIdentityTypeName,");
			sb.append(" c.applicantIdentityId, c.applicantBirthday, CASE WHEN c.applicantSex = 'M' THEN '男' WHEN c.applicantSex = 'F' THEN '女' END AS applicantSexName,");
			sb.append(" c.applicantMobile, c.applicantMail, d.recognizeeName,");
			sb.append(" CASE WHEN d.recognizeeIdentityType = 0 THEN '身份证' WHEN d.recognizeeIdentityType = 7 THEN '护照' WHEN d.recognizeeIdentityType = 4 THEN '军人证件' ELSE '其他' END AS recognizeeIdentityTypeName,");
			sb.append(" d.recognizeeIdentityId, d.recognizeeBirthday, CASE WHEN d.recognizeeSex = 'M' THEN '男' WHEN d.recognizeeSex = 'F' THEN '女' END AS recognizeeSexName,");
			sb.append(" 'b2b' as channel");
			sb.append(" FROM ");
			sb.append(Config.getConfigValue("B2BDataBase"));
			sb.append(".sdOrders a, ");
			sb.append(Config.getConfigValue("B2BDataBase"));
			sb.append(".sdInsuredCompanyReturnData b, ");
			sb.append(Config.getConfigValue("B2BDataBase"));
			sb.append(".sdAppnt c, ");
			sb.append(Config.getConfigValue("B2BDataBase"));
			sb.append(".sdInsured d, ");
			sb.append(Config.getConfigValue("B2BDataBase"));
			sb.append(".sdTradeInformation e");
			sb.append(" WHERE a.id = b.ordersn AND a.appntId = c.id AND a.id = d.ordersn AND b.insuredSn = d.insuredId AND a.id = e.ordID");
			sb.append(" AND a.orderStatus = '2' AND a.insuranceCompanySn = '2005'");
			// 起始时间
			if (StringUtil.isNotEmpty(createDate)) {
				sb.append(" AND e.receiveDate >='" + createDate + " 00:00:00' ");
			} 
			if (StringUtil.isNotEmpty(endCreateDate)) {
				sb.append(" AND e.receiveDate <='" + endCreateDate + " 23:59:59' ");
			} 
			// 订单号
			if (StringUtil.isNotEmpty(orderSn)) {
				sb.append(" AND a.id LIKE '%" + orderSn.trim() + "%'");
			}
			// 保单号
			if (StringUtil.isNotEmpty(policyNo)) {
				sb.append(" AND b.policyNo LIKE '%" + policyNo.trim() + "%'");
			}
			// 是否已承保(我方生成的假保单号为未承保)
			if ("N".equals(underwritingStatus)) {
				sb.append(" AND b.policyNo LIKE 'RBCX-%'");
			} else if ("Y".equals(underwritingStatus)) {
				sb.append(" AND b.policyNo LIKE 'PJBS%'");
			}
		}
		sb.append(" ORDER BY receiveDate DESC");
		
		// 测试输出结果SQL
		QueryBuilder qb = new QueryBuilder(sb.toString());
		logger.info("人保财险数据查询sql:{}", qb.getSQL());
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.setTotal(qb);
		dga.bindData(dt);
	}
	
	/**
	 * 人工承保处理(更新保单号，发送邮件)
	 */
	public boolean dealUnderwriting(String orderSn, String insuredSn, String policyNo, String applyPolicyNo, String channel, String electronicPath) {
		if (StringUtil.isEmpty(orderSn)) {
			Response.setLogInfo(0, "承保失败，订单号为空!");
			return false;
		}
		if (StringUtil.isEmpty(insuredSn)) {
			Response.setLogInfo(0, "承保失败，被保人编号为空!");
			return false;
		}
		if (StringUtil.isEmpty(policyNo)) {
			Response.setLogInfo(0, "承保失败，航延险保单号（保单号）为空!");
			return false;
		}
		if (StringUtil.isEmpty(applyPolicyNo)) {
			Response.setLogInfo(0, "承保失败，航意险保单号（投保单号）为空!");
			return false;
		}
		if (StringUtil.isNotEmpty(channel)) {
			try {
				int k = 0;  
				// 更新主站或美行保的保单表
				if ("wj".equals(channel)) {
					String sql = "UPDATE sdInformationRiskType SET policyNo = ?, applyPolicyNo = ?, electronicPath = ? "
							+ "WHERE orderSn = ? AND recognizeeSn = (SELECT recognizeeSn FROM sdInformationInsured WHERE insuredSn = ? LIMIT 0,1)";
					QueryBuilder qb = new QueryBuilder(sql);
					qb.add(policyNo);
					qb.add(applyPolicyNo);
					qb.add(electronicPath);
					qb.add(orderSn);
					qb.add(insuredSn);
					k = qb.executeNoQuery();
					
					// 更新产品中心结算表
					String sql2 = "UPDATE fcContNew SET contNo = ?, proposalContNo = ? WHERE backup1 = ?";
					QueryBuilder qb2 = new QueryBuilder(sql2);
					qb2.add(policyNo);
					qb2.add(applyPolicyNo);
					qb2.add(insuredSn);
					qb2.executeNoQuery();
					
					// 如果是淘宝渠道，需要淘宝出单回调
					if (orderSn.indexOf("TB") > -1) {
						String sql_tb = "SELECT riskcode, timePrem FROM sdInformationRiskType "
								+ "WHERE orderSn = ? AND recognizeeSn = (SELECT recognizeeSn FROM sdInformationInsured WHERE insuredSn = ? LIMIT 0,1)";
						QueryBuilder qb_tb = new QueryBuilder(sql_tb);
						qb_tb.add(orderSn);
						qb_tb.add(insuredSn);
						DataTable dt_tb = qb_tb.executeDataTable();
						if (dt_tb.getRowCount() > 0) {
							String productCode = dt_tb.getString(0, "riskcode");
							String timePrem = dt_tb.getString(0, "timePrem");
							
							QueryOrders tQueryOrders = new QueryOrders();
							String result = tQueryOrders.transformXMLToString(reponseResultTB(orderSn, policyNo, timePrem, electronicPath));
							tQueryOrders.taoBaoAsync(result, "?comId=" + comId + "&productCode=" + productCode);
						} else {
							Response.setLogInfo(0, "承保失败，淘宝数据回调失败!");
							return false;
						}
					}
					
					if (k > 0) {
						// 发送电子保单邮件
						try {
							DownloadNet db = new DownloadNet();
							List<HashMap<String,String>> insuredSnList = null;
							String sql3 = "SELECT id, recognizeeSn, insuredSn, recognizeeName FROM SDInformationInsured WHERE ordersn=? AND insuredSn=?";
							String[] sqltemp = {orderSn, insuredSn};
							insuredSnList = new GetDBdata().query(sql3, sqltemp);
							
							List<HashMap<String, String>> pathList = new ArrayList<HashMap<String, String>>();
							HashMap<String,String> map = new HashMap<String,String>();
							map.put("path", electronicPath);
							map.put("insuredSn", insuredSn);
							map.put("recognizeeName", insuredSnList.get(0).get("recognizeeName"));
							pathList.add(map);
							
							db.getGeneratepolicy(insuredSnList, pathList, orderSn, "2005");
							Response.setLogInfo(1, "承保成功!");
							return true;
						} catch (Exception e) {
							DownloadNet.sendPrintErrorMail(orderSn, insuredSn, e.getMessage(), new HashMap<String, Object>());
							logger.error("主站人保财险电子保单发送异常：" + e.getMessage(), e);
							Response.setLogInfo(1, "承保成功，但电子保单未发送成功!");
							return true;
						}
					} else {
						Response.setLogInfo(0, "承保失败，没有数据更新!");
						return false;
					}
					
				} else if ("b2b".equals(channel)) {
					String sql = "UPDATE " + Config.getConfigValue("B2BDataBase") +".sdInsuredCompanyReturnData SET policyNo = ?, applyPolicyNo = ?, policyPath = ? WHERE orderSn = ? AND insuredSn = ?";
					QueryBuilder qb = new QueryBuilder(sql);
					qb.add(policyNo);
					qb.add(applyPolicyNo);
					qb.add(electronicPath);
					qb.add(orderSn);
					qb.add(insuredSn);
					k = qb.executeNoQuery();
					
					String sql_orderItem = "UPDATE " + Config.getConfigValue("B2BDataBase") +".sdOrderItem SET ContNo = ? WHERE orderId = ? AND InsuredId = ?";
					QueryBuilder qb_orderItem = new QueryBuilder(sql_orderItem);
					qb_orderItem.add(policyNo);
					qb_orderItem.add(orderSn);
					qb_orderItem.add(insuredSn);
					int k2 = qb_orderItem.executeNoQuery();
					k += k2;
					
					// 更新产品中心结算表
					String sql2 = "UPDATE fcContNew SET contNo = ?, proposalContNo = ? WHERE backup1 = ?";
					QueryBuilder qb2 = new QueryBuilder(sql2);
					qb2.add(policyNo);
					qb2.add(applyPolicyNo);
					qb2.add(insuredSn);
					qb2.executeNoQuery();
					
					if (k > 0) {
						try {
							// 发送电子保单邮件(调用美行保接口)
							String url = Config.getConfigValue("B2BContextPath") + "Platform/RBCXMailSend.jsp?orderID=" + orderSn;
							HttpGet httpGet = new HttpGet(url);
							HttpResponse response = new DefaultHttpClient().execute(httpGet);
							
							if (response.getStatusLine().toString().indexOf("200") != -1) {
								Response.setLogInfo(1, "承保成功!");
								return true;
							} else {
								Response.setLogInfo(1, "承保成功，但电子保单未发送成功!");
								return true;
							}
						} catch (Exception e) {
							logger.error("美行保人保财险电子保单发送异常：" + e.getMessage(), e);
							Response.setLogInfo(1, "承保成功，但电子保单未发送成功!");
							return true;
						}
					} else {
						Response.setLogInfo(0, "承保失败，没有数据更新!");
						return false;
					}
				} else {
					Response.setLogInfo(0, "承保失败，订单渠道不明!渠道编码:" + channel);
					return false;
				}
				
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				Response.setLogInfo(0, "承保失败，操作数据库异常!" + e.getMessage());
				return false;
			}
		} else {
			Response.setLogInfo(0, "承保失败，订单渠道为空!");
			return false;
		}
	}
	
	/**
	 * 获得出单回调的请求数据
	 * 
	 * @return 请求数据
	 * @throws Exception
	 */
	private Document reponseResultTB(String orderSn, String policyNo, String timePrem, String electronicPath) throws Exception {
		String currentDate = DateUtil.getCurrentDate();
		String currentTime = DateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss");
		Element rootPackageList = new Element("PackageList");
		Element rootPackage = new Element("Package");
		Document doc = new Document(rootPackageList); // 要创建的XML文档 - doc

		// 取得唯一编码
		String sql = "select * from SDOrders where orderSn = ?";
		String sqltemp[] = { orderSn };
		List<HashMap<String, String>> list = new GetDBdata().query(sql, sqltemp);
		// 唯一编码
		String uuId = "";
		// 淘宝交易流水号
		String taoBaoSerial = "";
		
		if (list.size() > 0) {
			uuId = list.get(0).get("remark");
			comId = list.get(0).get("tbComCode");
			taoBaoSerial = list.get(0).get("tbTradeSeriNo");
		}

		// header 信息
		Element headerElement = new Element("Header");

		// 交易类型(必填)
		Element RequestTypeElement = new Element("RequestType");
		RequestTypeElement.setText("12");
		headerElement.addContent(RequestTypeElement);

		// 唯一编码(必填)
		Element UUIDElement = new Element("UUID");
		UUIDElement.setText(uuId);
		headerElement.addContent(UUIDElement);

		// 交互保险公司编码(必填)
		Element ComIdElement = new Element("ComId");
		ComIdElement.setText(comId);
		headerElement.addContent(ComIdElement);

		// 发送方编号(必填)
		Element FromElement = new Element("From");
		FromElement.setText(comId);
		headerElement.addContent(FromElement);

		// 发送时间(必填)
		Element SendTimeElement = new Element("SendTime");
		SendTimeElement.setText(currentTime);
		headerElement.addContent(SendTimeElement);

		// 淘宝交易流水号(必填)
		Element TaoBaoSerialElement = new Element("TaoBaoSerial");
		TaoBaoSerialElement.setText(taoBaoSerial);
		headerElement.addContent(TaoBaoSerialElement);

		// 保险公司流水号(非必填)
		Element ComSerialElement = new Element("ComSerial");
		headerElement.addContent(ComSerialElement);

		// end header

		// Callback 信息
		Element CallbackElement = new Element("Callback");

		// Policy
		Element PolicyElement = new Element("Policy");
		CallbackElement.addContent(PolicyElement);

		// 淘宝订单号(必填)
		Element TBOrderIdElement = new Element("TBOrderId");
		TBOrderIdElement.setText(taoBaoSerial);
		PolicyElement.addContent(TBOrderIdElement);

		// 保单号(非必填)
		Element PolicyNoElement = new Element("PolicyNo");
		PolicyNoElement.setText(policyNo);
		PolicyElement.addContent(PolicyNoElement);

		// 投保单号(必填)
		Element ProposalNoElement = new Element("ProposalNo");
		ProposalNoElement.setText(orderSn);
		PolicyElement.addContent(ProposalNoElement);

		// 总保费(必填)
		Element TotalPremiumElement = new Element("TotalPremium");
		if (!StringUtil.isEmpty(timePrem)) {
			// 以分为单位
			TotalPremiumElement.setText(String.valueOf((int) (Double.parseDouble(timePrem) * 100)));
		}

		PolicyElement.addContent(TotalPremiumElement);

		// 出单是否成功(必填)
		Element IsSuccessElement = new Element("IsSuccess");
		IsSuccessElement.setText("1");
		PolicyElement.addContent(IsSuccessElement);

		// 出单失败信息(非必填)
		Element FailReasonElement = new Element("FailReason");
		PolicyElement.addContent(FailReasonElement);

		// 电子保单地址(非必填)
		Element PolicyUrlElement = new Element("PolicyUrl");
		String policyUrl = Config.getFrontServerContextPath() + QueryOrders.replacePath(electronicPath);	
		PolicyUrlElement.setText(policyUrl);
		PolicyElement.addContent(PolicyUrlElement);

		// 账务日期(非必填)
		Element AccountDateElement = new Element("AccountDate");
		AccountDateElement.setText(currentDate);
		PolicyElement.addContent(AccountDateElement);

		// 出单时间(非必填)
		Element IssuedTimeElement = new Element("IssuedTime");
		IssuedTimeElement.setText(currentTime);
		PolicyElement.addContent(IssuedTimeElement);

		rootPackage.addContent(headerElement);
		rootPackage.addContent(CallbackElement);
		rootPackageList.addContent(rootPackage);

		return doc;
	}
	
}
