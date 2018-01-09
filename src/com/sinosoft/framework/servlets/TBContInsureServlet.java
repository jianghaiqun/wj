package com.sinosoft.framework.servlets;

import cn.com.sinosoft.entity.SDOrder;
import cn.com.sinosoft.entity.SDOrder.SDOrderStatus;
import cn.com.sinosoft.entity.SDOrder.SDPayStatus;
import com.activemq.ProducerMQ;
import com.alibaba.fastjson.JSON;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.inter.ActivityCalculate;
import com.sinosoft.inter.TBSDAction;
import com.sinosoft.jdt.ParseXMLToObject;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.SDTBTradeRecordSchema;
import com.sinosoft.webservice.FCContServiceImpl;
import com.wangjin.cms.orders.QueryOrders;
import com.wangjin.optimize.register.AutoRegister;
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
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 金代通对接淘宝数据，用于承保成功后订单数据处理
 * 
 * @author sinosoft
 * 
 */
public class TBContInsureServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(TBContInsureServlet.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1138717953245092512L;
	ParseXMLToObject tParseXMLToObject = new ParseXMLToObject();

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String result = "un_success";
		String orderSn = "";
		try {
			Random r = new Random();
			String requestIP = ParseXMLToObject.getIpAddr(request);
			String start = r.nextInt(10000) + "-" + System.currentTimeMillis();
			Document document = tParseXMLToObject.deealStreamToXML(request);
			if (document == null) {
				logger.info("==================淘宝保单====================出单数据空 （{}）", orderSn);
				result = "un_error";
			}
			// 重新获取订单号
			Element root = document.getRootElement();
			if (null == root) {
				throw new NullPointerException();
			}
			/* 取得header部分 */
			Element eleHeader = root.getChild("Header");
			if (null == eleHeader) {
				throw new NullPointerException();
			}
			/* 取得订单号 */
			orderSn = eleHeader.getChildTextTrim("orderSn");
			/* 取得保险公司编码 */
			tParseXMLToObject.writeTXT("出单开始：" + "请求IP:" + requestIP + "---"
					+ start + " - 订单号" + orderSn);
			// 解析XML文件
			if (readXMLtoMap(document, request)) {
				logger.info("==================淘宝保单====================发送保单成功邮件、发送电子保单成功！ ");
			} else {
				logger.error("==================淘宝保单====================发送保单成功邮件失败或者发送电子保单失败，请检查数据完整行");
				result = "un_error";
			}
			// 保存xml
			if (!tParseXMLToObject.saveXML(document, orderSn, "insure")) {
				logger.info("==================淘宝保单====================保存报文到本地失败！ （{}）", orderSn);
				result = "un_error";
			}
			String end = r.nextInt(10000) + "-" + System.currentTimeMillis();
			tParseXMLToObject.writeTXT("出单结束：" + "请求IP:" + requestIP + "---"
					+ end + " - 订单号" + orderSn);
			if ("un_success".equals(result)) {
				logger.info("淘宝订单数据--出单数据成功！orderSn（{}）", orderSn);
			}
			// 产品已支付的订单是否有保单号，如果没有则给经带通返回error
			int tCount = new QueryBuilder(
					" SELECT COUNT(1) FROM sdorders a,sdinformationrisktype b WHERE a.ordersn = b.orderSn AND a.orderstatus = '7' AND b.policyNo IS NOT NULL AND b.policyNo!='' AND a.orderSn=? ",
					orderSn).executeInt();
			if (tCount <= 0) {
				logger.info("支付成功，但未查到保单号,orderSn:{}", orderSn);
				result = "un_error";
			}else{
				sendMsg2Mq(orderSn, "N");//发送电子保单下载消息到mq队列
				callFCContService(orderSn);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = "un_error";
		}
		OutputStream out = response.getOutputStream();
		out.write(result.getBytes("utf-8"));
		out.flush();
		out.close();
	}

	/**
	 * @author yuzaijiang
	 * @description 发送消息队列到经代通下载电子保单
	 * @param orderSn 订单号
	 * @param sendEmailFlag 是否发送邮件 Y:发送 N：不发送
	 */ 
	
	private void sendMsg2Mq(String orderSn,String sendEmailFlag){
		try {
			// 发送消息队列到经代通下载电子保单
			Map<String, String> map = new HashMap<String, String>();
			map.put("orderSn", orderSn);
			map.put("policyNo", "");
			map.put("channelCode", "tb");
			map.put("isSendEmail", sendEmailFlag);
			map.put("isRewrite", "Y");
			map.put("paramMap", null);
			ProducerMQ mq = new ProducerMQ();
			mq.sendToJDT(JSON.toJSONString(map));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public boolean readXMLtoMap(Document doc, HttpServletRequest request)
			throws IOException {

		try {
			if (!dealInsureData(doc, request)) {
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public boolean dealInsureData(Document doc, HttpServletRequest request)
			throws Exception {

		ParseXMLToObject tParseXMLToObject = new ParseXMLToObject();
		TBSDAction tbsdAction = new TBSDAction();
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
		/* 取得订单号 */
		String orderSn = eleHeader.getChildTextTrim("orderSn");
		/* 取得保险公司编码 */
		String insureTypeCode = eleHeader.getChildTextTrim("comCode");
		/* 产品ID */
		/* 取得订单信息 */
		Element policyData = root.getChild("Request");
		// 订单表结构节点
		List<Element> policyList = policyData.getChildren();
		// 承包后数据
		if (!tParseXMLToObject.dealInsuranceData(policyList, insureTypeCode)) {
			return false;
		}
		// 淘宝SD信息表判断，将被保人在SD信息表中的订单渠道改为tbsd
		if (tbsdAction.isTbsdInsured(orderSn)) {
			tbsdAction.turnChannelToTbsd(orderSn);
			// 判断承保日期与起保日期是否相差72小时，若是，发送报警邮件
			String dealTime = root.getChild("Request")
					.getChild("TradeInformation")
					.getChildTextTrim("receiveDate");
			Date dealDate = null;
			try {
				dealDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.parse(dealTime);
			} catch (ParseException e) {
				logger.error("TBSD承保日期转换异常！" + dealDate + ":" + e.getMessage(), e);
			}
			Date svaliDate = tbsdAction.getSvaliDate(orderSn);
			if (svaliDate.getTime() - dealDate.getTime() <= 72 * 60 * 60 * 1000) {
				// 发送报警邮件给淘宝管理员
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("DealDate", dealTime);
				map.put("SvaliDate", svaliDate.toString());
				tbsdAction.sendWarnMail(orderSn, "承保日期与起保日期相差低于72小时", map);
			}
		}

		// 处理关联表数据，完善CMS后台淘宝数据订单查询与修改
		// 发送保单成功邮件、发送电子保单邮件
		// 对于华泰，需要发送保单成功邮件以及向网站发送excel保单
		// 根据保险公司判断是否发送保单成功邮件或电子保单邮件或excel

		// 取得产品的淘宝类型
		String taobaoType = "";
		String riskcode = new QueryBuilder(
				"select riskCode from sdinformationrisktype where orderSn = ?",
				orderSn).executeString();
		if (StringUtil.isNotEmpty(riskcode)) {
			taobaoType = new QueryBuilder(
					"select ProductType from jdproductc where ERiskID = ?",
					riskcode).executeString();
		}

		String tradeSwitch = Config.getValue("tradeSwitch");
		if (StringUtil.isEmpty(tradeSwitch)) {
			tradeSwitch = "close";
		}

		// 是否已注册会员
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 淘宝新老并行开关关闭或新老并行并是意健险时 不发送电子保单，平安、华泰生成电子保单
		if ("close".equals(tradeSwitch)
				|| ("open".equals(tradeSwitch) && "yj".equals(taobaoType))) {
			/* modified by yuzj @20170421 for 电子保单流程优化 ===begin===
			if ("2007".equals(insureTypeCode) || "2023".equals(insureTypeCode)) {
				InsureTransferNew tInsureTransferNew = new InsureTransferNew();
				String sql = "select a.id, a.appStatus, b.insuredSn, a.policyNo, a.validateCode, a.electronicCout, a.electronicpath, a.insureDate, a.createDate from sdinformationrisktype a, sdinformationinsured b where a.sdinformationinsured_id = b.id and a.orderSn = ?";
				DataTable dt = new QueryBuilder(sql, orderSn)
						.executeDataTable();
				
				if (dt != null && dt.getRowCount() > 0) {
					Transaction trans = new Transaction();
					int rowCount = dt.getRowCount();
					String id = "";
					// 承保状态
					String appStatus = "";
					// 被保人编码
					String insuredSn = "";
					// 保单号
					String policyNo = "";
					// 验证码
					String validateCode = "";
					// 电子保单下载路径
					String electronicCout = "";
					// 电子保单存储路径
					String electronicpath = "";
					// 承包时间
					String insureDate = "";
					// 险种类型
					String risktype = "";
					String sql1 = "select riskType from SDInformation where ordersn=?";
					DataTable dt1 = new QueryBuilder(sql1, orderSn)
							.executeDataTable();
					if (dt1.getRowCount() > 0) {
						risktype = dt1.getString(0, 0);
					}
					boolean flag = false;
					for (int i = 0; i < rowCount; i++) {
						id = dt.getString(i, "id");
						appStatus = dt.getString(i, "appStatus");
						insuredSn = dt.getString(i, "insuredSn");
						policyNo = dt.getString(i, "policyNo");
						validateCode = dt.getString(i, "validateCode");
						electronicCout = dt.getString(i, "electronicCout");
						electronicpath = dt.getString(i, "electronicpath");
						insureDate = dt.getString(i, "insureDate");
						if (StringUtil.isEmpty(insureDate)) {
							insureDate = dt.getString(i, "createDate");
						}
						// 承包成功的情况下 进行电子保单处理
						if ("1".equals(appStatus)
								&& StringUtil.isNotEmpty(policyNo)) {
							String policyPath = "";
							if ("2007".equals(insureTypeCode)) {
								// 平安生成电子保单
								policyPath = tInsureTransferNew
										.checkElectronicPolicyIsSuccess(
												orderSn, insuredSn, policyNo,
												validateCode, electronicpath,
												insureDate);
							} else {
								// 华泰家财生成电子保单
								if (StringUtil.isNotEmpty(risktype)
										&& "11".equals(risktype)) {
									HTElectronicPolicy htP = new HTElectronicPolicy();
									policyPath = htP.getPolicyPath(orderSn,
											insuredSn, policyNo,
											electronicpath, insureDate);
								} else {
									// 华泰除家财险下载电子保单
									DownloadNet db = new DownloadNet();
									policyPath = db.EpolicyDeal(orderSn,
											electronicCout, insureTypeCode,
											insuredSn, policyNo,
											electronicpath, insureDate);
								}
							}
							// 生成或下载后的电子保单存储路径与之前的存储路径不一致的情况 更新成最新的存储路径
							if (StringUtil.isNotEmpty(policyPath)
									&& !policyPath.equals(electronicpath)) {
								trans.add(new QueryBuilder(
										"update sdinformationrisktype set electronicPath = ? where id = ?",
										policyPath, id));
								flag = true;
							}
						}
					}

					if (flag) {
						if (!trans.commit()) {
							LogUtil.error("====淘宝对接数据=========电子保单路径修改失败！");
							return false;
						}
					}
				}
				
			}
			modified by yuzj @20170421 for 电子保单流程优化 ===end===*/
			
			DataTable dt = new QueryBuilder("select * from sdorders where ordersn = ? limit 0,1", orderSn).executeDataTable();
			if (dt != null && dt.getRowCount() > 0) {
				SDOrder sdorder = new SDOrder();
				sdorder.setId(dt.getString(0, "id"));
				sdorder.setCreateDate(dt.getDate(0, "createdate"));
				sdorder.setModifyDate(dt.getDate(0, "modifyDate"));
				sdorder.setOrderSn(dt.getString(0, "orderSn"));
				sdorder.setMemberId(dt.getString(0, "memberId"));
				if ("7".equals(dt.getString(0, "orderStatus"))) {
					sdorder.setOrderStatus(SDOrderStatus.paid);
				} else if ("5".equals(dt.getString(0, "orderStatus"))) {
					sdorder.setOrderStatus(SDOrderStatus.prepay);
				}
				if ("2".equals(dt.getString(0, "payStatus"))) {
					sdorder.setPayStatus(SDPayStatus.paid);
				} else if ("0".equals(dt.getString(0, "payStatus"))) {
					sdorder.setPayStatus(SDPayStatus.unpaid);
				}
				sdorder.setPayType(dt.getString(0, "payType"));
				sdorder.setProductNum(dt.getInt(0, "productNum"));
				sdorder.setProductTotalPrice(new BigDecimal(dt.getDouble(0, "productTotalPrice")));
				sdorder.setDiscountRates(dt.getString(0, "discountRates"));
				sdorder.setDiscountAmount(dt.getString(0, "discountAmount"));
				sdorder.setTotalAmount(new BigDecimal(dt.getDouble(0, "totalAmount")));
				sdorder.setPayAmount(new BigDecimal(dt.getDouble(0, "payAmount")));
				sdorder.setPaySn(dt.getString(0, "paySn"));
				sdorder.setRemark(dt.getString(0, "remark"));
				sdorder.setTbTradeSeriNo(dt.getString(0, "tbTradeSeriNo"));
				sdorder.setTbComCode(dt.getString(0, "tbComCode"));
				sdorder.setCouponSn(dt.getString(0, "couponsn"));
				sdorder.setOffsetPoint(dt.getString(0, "offsetPoint"));
				sdorder.setCommentId(dt.getLong(0, "commentId"));
				sdorder.setOrderCoupon(dt.getString(0, "orderCoupon"));
				sdorder.setOrderIntegral(dt.getString(0, "orderIntegral"));
				sdorder.setSumCoupon(dt.getString(0, "sumCoupon"));
				sdorder.setSumIntegral(dt.getString(0, "sumIntegral"));
				sdorder.setActivitySn(dt.getString(0, "activitySn"));
				sdorder.setOrderActivity(dt.getString(0, "orderActivity"));
				sdorder.setPayPrice(dt.getString(0, "payPrice"));
				sdorder.setSumActivity(dt.getString(0, "sumActivity"));
				sdorder.setChannelsn(dt.getString(0, "channelsn"));
				sdorder.setDellFlag(dt.getString(0, "dellFlag"));
				
				// 不是淘宝刷单的处理
				if (!"tbsd".equals(sdorder.getChannelsn())) {
					// 赠送积分
					ActivityCalculate.TransactionDealIntegral(sdorder.getPaySn(), sdorder.getChannelsn());
					
					// 判断是否是会员 不是会员自动注册 发送投保成功短信
					AutoRegister ar = new AutoRegister();
					resultMap = ar.userRegistedCheck(sdorder, orderSn, null, request);
					logger.info("淘宝自动注册返回结果resultMap：{}", resultMap.toString());
					// 积分转增用户表
					ActivityCalculate.transPointToMember(sdorder.getPaySn());
				}
				
				// 保存淘宝消费记录（折扣产品不累加消费记录）
				saveTBTradeRecord(sdorder);
				
			} else {
				logger.error("淘宝出单数据更新：订单未找到！订单号：{}", orderSn);
			}
			
		} else {
			if ("2007".equals(insureTypeCode) || "2049".equals(insureTypeCode)
					|| "2023".equals(insureTypeCode)
					|| "2071".equals(insureTypeCode)
					|| "1048".equals(insureTypeCode)) {
				// 发送电子保单邮件
				if (!tParseXMLToObject.dealSendPolicyMail(insureTypeCode,
						orderSn)) {
					return false;
				}
			} else if ("2034".equals(insureTypeCode)) {
				// 发送保单成功邮件，美亚异步交易不发送邮件
				/*
				 * if(!tParseXMLToObject.dealSendSucMail(orderSn, request)){
				 * return false; }
				 */
				// 发送电子保单邮件(向网站)
				if (!tParseXMLToObject.dealSendPolicyMail(insureTypeCode,
						orderSn)) {
					return false;
				}
			} else {
				// 发送保单成功邮件
				if (!tParseXMLToObject.dealSendSucMail(orderSn, request)) {
					return false;
				}
			}
		}
		
		if (null != resultMap && !resultMap.isEmpty() && (Boolean) resultMap.get("sendMessage")) {
			return true;
		}
		// 发送保单下载提醒短信
		logger.info("---------发送淘宝电子保单短信提醒开始......");
		String mobileSql = "SELECT b.applicantMobile FROM sdorders o,SDInformation a, SDInformationAppnt b WHERE a.informationSn=b.informationSn AND o.orderSn=? and o.ordersn=a.ordersn and o.channelsn != 'tbsd'";
		DataTable mobileDt = new QueryBuilder(mobileSql, orderSn).executeDataTable();
		if (mobileDt.getRowCount() > 0) {
			String mobileNo = mobileDt.getString(0, "applicantMobile");
			logger.info("---------发送淘宝电子保单短信提醒,获取手机号成功,手机号:{}", mobileNo);
			
			if (StringUtil.isNotEmpty(mobileNo)) {
				// 获取电子保单下载地址
				DataTable dt = new QueryBuilder("SELECT electronicPath FROM SDInformationRiskType WHERE orderSn = ?", orderSn).executeDataTable();
				for (int i = 0; i < dt.getRowCount(); i++) {
					String electronicPath = dt.getString(i, "electronicPath");
					logger.info("发送淘宝电子保单短信提醒,获取电子保单地址成功,地址:{}", electronicPath);
					if (StringUtil.isNotEmpty(electronicPath)) {
						electronicPath = Config.getFrontServerContextPath() + QueryOrders.replacePath(electronicPath);
						// 暂时修改为 不发送电子保单 wj00119为电子保单下载提示信息短信
						// ActionUtil.sendSms("wj00119", mobileNo, electronicPath);
						ActionUtil.sendSms("wj00118", mobileNo, "");
					} else {
						logger.error("淘宝发送保单下载提醒短信异常，电子保单路径为空！订单号：{}", orderSn);
					}
				}
			} else {
				logger.error("淘宝发送保单下载提醒短信异常，投保人手机号为空！订单号：{}", orderSn);
			}
		}

		return true;
	}
	
	/**
	 * 保存淘宝消费记录（折扣产品不累加消费记录）
	 */
	private void saveTBTradeRecord(SDOrder sdorder) {
		String orderSn = sdorder.getOrderSn();
		logger.info("---------开始保存淘宝消费记录,订单号：{}", orderSn);
		BigDecimal payPrice = BigDecimal.valueOf(Double.valueOf(sdorder.getPayPrice()));
		int flag = payPrice.compareTo(sdorder.getTotalAmount().setScale(2, BigDecimal.ROUND_HALF_UP));
		// 产品原价和实际支付价格进行比较，若不相等则说明是淘宝折扣产品，不累加消费记录
		if (!"tbsd".equals(sdorder.getChannelsn()) && flag == 0) {
			String sql = "SELECT receiveDate FROM TradeInformation WHERE ordId=? ORDER BY createDate DESC LIMIT 0,1";
			QueryBuilder qb = new QueryBuilder(sql, orderSn);
			String receiveDate = qb.executeString();
			//receiveDate = receiveDate.substring(0, 11);
			
			String sql2 = "SELECT applicantName,applicantIdentityType,applicantIdentityTypeName,applicantIdentityId,a.applicantMobile,b.productId "
					+ "FROM sdinformationappnt a,sdinformation b WHERE a.informationSn=b.informationSn AND orderSn=?";
			QueryBuilder qb2 = new QueryBuilder(sql2, orderSn);
			DataTable dt2 = qb2.executeDataTable();
			String appntName = dt2.getString(0, 0);
			String certificateType = dt2.getString(0, 1);
			String certificateTypeName = dt2.getString(0, 2);
			String certificateTypeId = dt2.getString(0, 3);
			String applicantMobile = dt2.getString(0, 4);
			String productId = dt2.getString(0, 5);
			
			int rowCount = new QueryBuilder( "SELECT count(1) FROM SDTBTradeRecord WHERE orderSn=?", orderSn).executeInt();
			if (rowCount == 0) {
				// 没有记录则新增消费记录
				SDTBTradeRecordSchema sdtbTradeRecord = new SDTBTradeRecordSchema();
				sdtbTradeRecord.setId(NoUtil.getMaxNo("TBTradeRecord"));
				sdtbTradeRecord.setAppntName(appntName);
				sdtbTradeRecord.setCertificateType(certificateType);
				sdtbTradeRecord.setCertificateTypeName(certificateTypeName);
				sdtbTradeRecord.setCertificateId(certificateTypeId);
				sdtbTradeRecord.setTradeDate(receiveDate);
				sdtbTradeRecord.setTradeSum(sdorder.getPayPrice());
				sdtbTradeRecord.setUseSum("0");
				sdtbTradeRecord.setappntMobileNo(applicantMobile);
				sdtbTradeRecord.setorderSn(orderSn);
				sdtbTradeRecord.setchannelSn(sdorder.getChannelsn());
				
				String sql3="select a.activitysn from sdcouponactivityinfo a, sdproductactivitylink b where a.status='3' and a.activitysn=b.ActivitySn and b.ActivityChannel=? and b.ProductId=? and DATE_FORMAT(a.endtime, '%Y-%m-%d %H:%m:%s') >= ? and DATE_FORMAT(a.starttime, '%Y-%m-%d %H:%m:%s') <= ?";
				
				QueryBuilder qb3 = new QueryBuilder(sql3);
				qb3.add(sdorder.getChannelsn());
				qb3.add(productId);
				qb3.add(receiveDate);
				qb3.add(receiveDate);
				String activitysn = qb3.executeString();
				if (StringUtil.isNotEmpty(activitysn)) {
					sdtbTradeRecord.setactivitySn(activitysn);
				}
				
				sdtbTradeRecord.insert();
				logger.info("---------新增淘宝消费记录成功，ID：{}", sdtbTradeRecord.getId());
			}
		}
	}
	
	/**
	 * 
	 * @Title: callFCContService
	 * @return String 调用结算中心接口
	 * @author
	 */
	public void  callFCContService(String ordersn) {
		if (StringUtil.isEmpty(ordersn)) {
			return;
		}
		String Sql="";
		Sql =" SELECT s2.insuranceCompany,s1.ordersn,s1.recognizeesn,s1.informationSn,o.paySn,s1.policyNo "
			+" FROM sdorders o, SDInformationRiskType s1, SDInformation s2 "
			+" WHERE  o.ordersn=? and o.ordersn=s1.ordersn and s1.ordersn=s2.ordersn and appStatus='1' "
			+ "and policyNo is not null and policyNo != '' and (s1.balanceStatus NOT IN ('2', '0') OR s1.balanceStatus IS NULL)"; 

		DataTable dt = new QueryBuilder(Sql, ordersn).executeDataTable();
		String comCode="";
		String recognizeeSn="";
		String informationSn="";
		String paySn="";
		String policyNo="";
		if (dt != null && dt.getRowCount() > 0) {
			FCContServiceImpl tFCContServiceImpl = new FCContServiceImpl();
			int totalNum=dt.getRowCount();
			Transaction transaction = new Transaction();
			Sql = "update sdinformationrisktype set balanceStatus = '2', modifyDate = now() "
				+ "where  ordersn=? and appStatus='1' and policyNo is not null and policyNo != '' and (balanceStatus NOT IN ('2', '0') OR balanceStatus IS NULL)";
			transaction.add(new QueryBuilder(Sql, ordersn));
		    transaction.commit();
		    for (int i = 0; i < totalNum; i++) {//一张订单可能存在多张保单
				policyNo=dt.getString(i, "policyNo");
				comCode=dt.getString(i, "insuranceCompany");
				recognizeeSn=dt.getString(i, "recognizeesn");
				informationSn=dt.getString(i, "informationSn");
				paySn=dt.getString(i, "paySn");
				if (StringUtil.isNotEmpty(comCode) && StringUtil.isNotEmpty(informationSn)) {
				    tFCContServiceImpl.callFCContService(comCode, ordersn, recognizeeSn, informationSn, paySn);
				}else{
					Sql = "update sdinformationrisktype set balanceStatus = ''  WHERE policyNo='"+policyNo +"'";
					new QueryBuilder(Sql).executeNoQuery();
					logger.error("==================错误信息====================保单号： "+policyNo +" 保单数据错误，不能结算。");
				}
					
			}
		}else{
			logger.error("==================错误信息====================订单号： "+ordersn +" 不存在可结算数据！");
		}
	}
	
	
	
}
