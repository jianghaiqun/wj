/**
 * Project Name:FrontTrade
 * File Name:SaveAeonlife.java
 * Package Name:com.finance.service.aeonlife
 * Date:2016年5月31日下午5:45:38
 * Copyright (c) 2016, www.kaixinbao.com All Rights Reserved.
 *
 */

package com.sinosoft.asyninter;

import cn.com.sinosoft.service.impl.BaseServiceImpl;
import cn.com.sinosoft.util.CommonUtil;
import cn.com.sinosoft.util.HttpClientUtil;
import com.sinosoft.aeonlife.model.Aeon;
import com.sinosoft.aeonlife.utils.SftpCommon;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.PartnerInfoSchema;
import com.sinosoft.schema.PartnerInfoSet;
import com.sinosoft.schema.SDInformationAppntSchema;
import com.sinosoft.schema.SDInformationInsuredSchema;
import com.sinosoft.schema.SDInformationRiskTypeSchema;
import com.sinosoft.schema.SDInformationSchema;
import com.sinosoft.schema.SDOrderItemSchema;
import com.sinosoft.schema.SDOrdersSchema;
import com.sinosoft.schema.TradeInformationSchema;
import com.sinosoft.schema.TradeSummaryInfoSchema;
import com.sinosoft.webservice.ProductWebservice;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FMRisk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * ClassName:SaveAeonlife <br/>
 * Function:TODO 保存百年订单的信息. <br/>
 * Date:2016年5月31日 下午5:45:38 <br/>
 *
 * @author:chouweigao
 */
public class AsynInterService extends BaseServiceImpl<Object, String> {
	
	public static final String BILL_PAY_TYPE = "bill_qb";

	private static final Logger logger = LoggerFactory.getLogger(AsynInterService.class);

	/**
	 * 保存定义的变量
	 */
	String orderSn = "";
	String channel = "";

	String sdinfomationSn = "";
	String applicantSn = "";
	String insuredSn = "";
	String productId = "";
	String orderid = "";
	String sdinformationid = "";
	String sdinformationRiskTypeid = "";
	String sdinformationappntid = "";
	String sdinformationinsuredid = "";
	String tradeinformationid = "";
	String tradeSummaryID = "";
	String sdOrderItemid = "";

	// 组成被保人insuredId时用
	int index = 1;
	FMRisk fmrisk = null;

	/**
	 * 百年的变量
	 */
	String paysn = "";
	String aeonName = "";
	String aeonCardNum = "";
	String aeonPhone = "";
	String aeonMail = "";
	String aeonAdd = "";
	String aeonAmount = "";
	String aeonPolicyNo = "";
	String aeonProductName = "";
	Date aeonStartDate = null;
	Date aeonEndDate = null;
	String aeonPolicyPath = "";
	/**
	 * 退保时间
	 */
	Date aeonCancelDate = null;
	/**
	 * 保单状态 0：承保失败 1：承保成功
	 */
	String aeonPolicyStauts = "";
	/**
	 * 退保结果
	 */
	String aeonPolicyResult = "";

	Date createDate = null;

	String cancelDate_str = "";

	/**
	 * 
	 * execute:(遍历获得百年的保单信息). <br/>
	 * 
	 * @author chouweigao
	 * @param cInputData
	 * @return
	 * @throws Exception
	 */
	public void executeSave(Map<String, Object> cInputData) {

		Set<String> set = cInputData.keySet();
		for (String key : set) {
			Aeon aeon = (Aeon) cInputData.get(key);
			// 百年订单号
			paysn = aeon.getAeonOrderSn();
			aeonName = aeon.getAeonName();
			aeonCardNum = aeon.getAeonCardNum();
			aeonPhone = aeon.getAeonPhone();
			aeonMail = aeon.getAeonMail();
			aeonAdd = aeon.getAeonAdd();
			aeonAmount = aeon.getAeonAmount();
			aeonPolicyNo = aeon.getAeonPolicyNo();
			aeonProductName = aeon.getAeonProductName();
			aeonPolicyPath = aeon.getAeonPolicyPath();
			aeonStartDate = aeon.getAeonStartDate();
			aeonEndDate = aeon.getAeonEndDate();
			aeonPolicyStauts = aeon.getAeonPolicyStauts();
			aeonPolicyResult = aeon.getAeonPolicyResult();
			channel = aeon.getChannels();

			// 保单保存
			try {
				if(!existPaySn(paysn)){
					executeSave();
				}else{
					logger.warn("百年稳赢保终身寿险订单已存在:{}", paysn);
				}
			} catch (Exception e) {
				logger.error("百年稳赢保终身寿险订单保存失败:" + e.getMessage(), e);

			}
		}

	}

	/**
	 *
	 * executeUpdate:( 退保). <br/>
	 *
	 * @author chouweigao
	 * @param cInputData
	 * @param flag
	 * @return
	 */
	public Map<String, Object> executeUpdate(Map<String, Object> cInputData) {

		Set<String> set = cInputData.keySet();
		for (String key : set) {
			Aeon aeon = (Aeon) cInputData.get(key);
			try {
				update(aeon);
			} catch (Exception e) {
				logger.error("百年稳赢保终身寿险订单更新失败:" + e.getMessage(), e);
			}
		}
		
		// 完成推送 按照不同渠道推送。
		// 首先数据库中查询昨天提交退保的订单
		String sql = "SELECT f.PolicyNo,f.OrderSn,f.Total,f.Principal,f.Income,f.InsStatus,f.Prop1,f.Prop2,f.CreateDate,DATE_FORMAT(f.modifydate,'%Y-%m-%d') as modifydate,Fee,f.ReturnType,f.CancelResult,f.CancelMsg, a.CancelDate "
				+ "FROM financinginfo f, ApplyCancelRecord a WHERE DATE_SUB(CURDATE(),INTERVAL 1 DAY) = a.CancelDate AND a.OrderSn = f.OrderSn AND a.channelsn = ?";
		if (!cInputData.isEmpty()) {
			
			PartnerInfoSet partnerInfoSet = new PartnerInfoSchema().query();
			for (int i = 0; i < partnerInfoSet.size(); i++) {
				PartnerInfoSchema partnerInfo = partnerInfoSet.get(i);
				
				// 对应 合作方渠道获取数据
				QueryBuilder qb = new QueryBuilder(sql, partnerInfo.getchannelSn());
				DataTable dt = qb.executeDataTable();
				List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
				
				for (int j = 0; j < dt.getRowCount(); j++) {
					Map<String, String> cancelOrder = new HashMap<String, String>();
					cancelOrder.put("PolicyNo", dt.getString(j, "PolicyNo"));
					cancelOrder.put("OrderSn", dt.getString(j, "OrderSn"));
					cancelOrder.put("CancelResult", dt.getString(j, "CancelResult"));
					cancelOrder.put("ReturnType", dt.getString(j, "ReturnType"));
					cancelOrder.put("Total", dt.getString(j, "Total"));
					cancelOrder.put("Principal", dt.getString(j, "Principal"));
					cancelOrder.put("Income", dt.getString(j, "Income"));
					cancelOrder.put("Fee", dt.getString(j, "Fee"));
					cancelOrder.put("CancelDate", dt.getString(j, "CancelDate"));
					cancelOrder.put("Desc", dt.getString(j, "CancelMsg"));
					dataList.add(cancelOrder);
				}
				// 推送数据
				if (dataList.size() > 0 && StringUtil.isNotEmpty(partnerInfo.getBgCancelResultUrl())) {
					Map<String, Object> sendData = new HashMap<String, Object>();
					sendData.put("orderNos", dataList);
					SendCancelResult send = new SendCancelResult(partnerInfo.getBgCancelResultUrl(), sendData);
					send.run();
					String updateSql = " update ApplyCancelRecord set SendFlag = '" + send.getSendFlag() + "', SendResult = '" + send.getSendResult() + "' "
							+ " where CancelDate = DATE_SUB(CURDATE(),INTERVAL 1 DAY) and channelsn = '" + partnerInfo.getchannelSn() + "'";
					QueryBuilder aqb = new QueryBuilder(updateSql);
					aqb.executeNoQuery();
				}
			}
		}
		return cInputData;
	}

	/**
	 * 
	 * executePolicyValue:(资产同步). <br/>
	 * 
	 * @author chouweigao
	 * @param cInputData
	 * @return
	 */
	public Map<String, Object> executePolicyValue(Map<String, Object> cInputData) {

		Set<String> set = cInputData.keySet();
		for (String key : set) {
			Aeon aeon = (Aeon) cInputData.get(key);
			saveOrUpdateFinancinginfo(aeon);
		}
		return cInputData;
	}

	/**
	 * 
	 * saveOrUpdateFinancinginfo:(资产保存更新问题。). <br/>
	 *
	 * @author chouweigao
	 * @param cInputData
	 */
	private void saveOrUpdateFinancinginfo(Aeon aeon) {

		String[] arr = concatString(aeon);

		String ins = arr[0];
		String update = arr[1];

		logger.info("资产同步insert========={}", ins);
		logger.info("资产同步update========={}", update);

		String sql = "INSERT INTO financinginfo (PolicyNo, OrderSn,  Total, Principal, Income, InsStatus,  CreateDate, ModifyDate) "
				+ "VALUES (" + ins + "NOW(),NOW())  ON DUPLICATE KEY UPDATE " + update + ",ModifyDate=NOW()";

		logger.info("资产同步sql语句========={}", sql);
		QueryBuilder dt = new QueryBuilder(sql);
		dt.executeNoQuery();

		// 保存record表
		saveRefund(aeon);

	}

	private  synchronized void saveRefund(Aeon aeon) {

		String ordersn = aeon.getAeonOrderSn();
		String stauts = aeon.getAeonPolicyStauts();

		String sel_sql = "SELECT COUNT(1) FROM financingrecord WHERE ordersn ='" + ordersn + "'";
		QueryBuilder dt = new QueryBuilder(sel_sql);
		long row = (Long) dt.executeOneValue();

		/**
		 * 状态是1 行数是0 保存的是投保状态 状态是2 行数是1 保存的是退保状态
		 */
		if ((stauts.equals("1") && row == 0) || (stauts.equals("2") && row == 1)) {
			// 通过订单表取得memberid
			String sel_member = "SELECT memberid FROM sdorders WHERE ordersn ='" + ordersn + "'";
			dt = new QueryBuilder(sel_member);
			DataTable dtab = dt.executeDataTable();
			if (dtab.getRowCount() > 0) {
				String memberid = dtab.getString(0, "memberid");
				// 查询当前的保单数+
				String sel_recode = "SELECT * FROM financingrecord WHERE memberid =(SELECT memberid FROM sdorders WHERE ordersn = '"
						+ ordersn + "') ORDER BY modifydate DESC LIMIT 1";
				dt = new QueryBuilder(sel_recode);
				dtab = dt.executeDataTable();
				String total = "0.0";
				if (dtab.getRowCount() > 0) {
					total = dtab.getString(0, "Total");
					String[] argArr = {memberid, ordersn, total};
					logger.info("会员号：{}|订单号：{}|剩余总金额：{}", argArr);
				}
				if (row == 0) {// 承保
					BigDecimal t1 = new BigDecimal(aeon.getPrincipal());
					BigDecimal t2 = new BigDecimal(total);
					BigDecimal bd = t1.add(t2).setScale(2, BigDecimal.ROUND_HALF_UP);
					// 设置
					aeon.setTotal(bd.toString());
					aeon.setAeonPolicyStauts("0");

				} else {
					BigDecimal t1 = new BigDecimal(total);
					BigDecimal t2 = new BigDecimal(aeon.getPrincipal());
					BigDecimal bd = t1.subtract(t2).setScale(2, BigDecimal.ROUND_HALF_UP);
					// 设置
					aeon.setTotal(bd.toString());
					aeon.setPrincipal("-" + aeon.getPrincipal());
					aeon.setAeonPolicyStauts("1");
				}
				String sql = concatSql(aeon, memberid);
				String ins_sql = "INSERT INTO financingrecord  VALUES(" + sql + ")";
				logger.info("资产同步Record—sql语句========={}", ins_sql);
				dt = new QueryBuilder(ins_sql);
				dt.executeNoQuery();
				try {
					//sql执行太快 modifydate时间不准 加1秒延迟
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}

	/**
	 * 
	 * concatString:(拼装sql语句参数). <br/>
	 *
	 * @author chouweigao
	 * @param aeon
	 * @return
	 */
	private String[] concatString(Aeon aeon) {

		String str = "'";
		String[] arr = new String[2];
		String pno = aeon.getAeonPolicyNo();
		String osn = aeon.getAeonOrderSn();
		String total = aeon.getTotal();
		String princ = aeon.getPrincipal();
		String income = aeon.getIncome();
		String InsStatus = aeon.getAeonPolicyStauts();

		// 拼装insert语句
		StringBuffer sb = new StringBuffer();
		sb.append(str).append(pno).append(str).append(SftpCommon.SPLIT_STR)
				.append(str).append(osn).append(str).append(SftpCommon.SPLIT_STR)
				.append(str).append(total).append(str).append(SftpCommon.SPLIT_STR)
				.append(str).append(princ).append(str).append(SftpCommon.SPLIT_STR)
				.append(str).append(income).append(str).append(SftpCommon.SPLIT_STR)
				.append(str).append(InsStatus).append(str).append(SftpCommon.SPLIT_STR);
		arr[0] = sb.toString();
		// 拼装update语句
		sb = new StringBuffer();
		sb.append("Total=").append(str).append(total).append(str).append(SftpCommon.SPLIT_STR)
				.append("Principal=").append(str).append(princ).append(str).append(SftpCommon.SPLIT_STR)
				.append("Income=").append(str).append(income).append(str);

		arr[1] = sb.toString();
		return arr;
	}

	private String concatSql(Aeon aeon, String memberid) {
		String str = "'";
		String date = DateUtil.getCurrentDateTime();
		String id = CommonUtil.getUUID();
		String pno = aeon.getAeonPolicyNo();
		String osn = aeon.getAeonOrderSn();
		String total = aeon.getTotal();
		String princ = aeon.getPrincipal();
		String InsStatus = aeon.getAeonPolicyStauts();

		StringBuffer sb = new StringBuffer();
		sb.append(str).append(id).append(str).append(SftpCommon.SPLIT_STR)
				.append(str).append(pno).append(str).append(SftpCommon.SPLIT_STR)
				.append(str).append(osn).append(str).append(SftpCommon.SPLIT_STR)
				.append(str).append(princ).append(str).append(SftpCommon.SPLIT_STR)
				.append(str).append(memberid).append(str).append(SftpCommon.SPLIT_STR)
				.append(str).append(total).append(str).append(SftpCommon.SPLIT_STR)
				.append(str).append(InsStatus).append(str).append(SftpCommon.SPLIT_STR)
				.append(str).append(date).append(str).append(SftpCommon.SPLIT_STR)
				.append(str).append(date).append(str);

		return sb.toString();
	}

	/**
	 * BNRS-151106151238143083 update:(更新保单为退保状态). <br/>
	 *
	 * @author chouweigao
	 */
	private void update(Aeon aeon) throws Exception {

		String aeonOrderSn = aeon.getAeonOrderSn();
		Date aeonCancelDate = aeon.getCancelDate();
		String total = aeon.getTotal();
		String principal = aeon.getPrincipal();
		String income = aeon.getIncome();
		String fee = aeon.getFee();
		String returnType = aeon.getReturnType();
		String aeonPolicyResult = aeon.getAeonPolicyResult();
		String aeonMsg = aeon.getAeonMsg();
		String insStatus = "4";

		// 退保状态1：成功 0失败
		if (StringUtil.isNotEmpty(aeonPolicyResult) && "1".equals(aeonPolicyResult)) {
			/**
			 * 更新状态
			 */
			String update_orders = "UPDATE sdorders SET OrderStatus = '9',MODIFYDATE = NOW() WHERE paysn ='" + aeonOrderSn + "' or ordersn ='"+aeonOrderSn+"'";
			logger.info("update_orders=========={}", update_orders);
			QueryBuilder dt = new QueryBuilder(update_orders);
			dt.executeNoQuery();
	
			cancelDate_str = DateUtil.toDateTimeString(aeonCancelDate);
			String update_riskType = "UPDATE sdinformationrisktype SET appStatus ='2',cancelDate ='" + cancelDate_str
					+ "' WHERE ordersn = (SELECT ordersn FROM sdorders WHERE paysn ='" + aeonOrderSn + "' or ordersn ='"+aeonOrderSn+"')";
			logger.info("update_riskType========={}", update_riskType);
			dt = new QueryBuilder(update_riskType);
			dt.executeNoQuery();
			insStatus = "3";
		}

		/**
		 * 更新自己的
		 */
		String update_financinginfo = "UPDATE financinginfo SET InsStatus = '" + insStatus + "',Total='" + total + "',principal='"
				+ principal + "',income='" + income + "' ,fee='" + fee + "' ,returnType='" 
				+ returnType + "' ,cancelResult='" + aeonPolicyResult + "' ,CancelMsg='" + aeonMsg + "',MODIFYDATE = NOW() WHERE OrderSn ='"
				+ aeonOrderSn + "'";
		logger.info("update_financinginfo========={}", update_financinginfo);
		QueryBuilder dt = new QueryBuilder(update_financinginfo);
		dt.executeNoQuery();

		// 退保状态1：成功 0失败
		if (StringUtil.isNotEmpty(aeonPolicyResult) && "1".equals(aeonPolicyResult)) {
			// 保存record表
			aeon.setAeonPolicyStauts("2");
			saveRefund(aeon);
		}
	}

	/**
	 * 
	 * save:(保存订单). <br/>
	 *
	 * @author chouweigao
	 * @throws Exception
	 */
	private void executeSave() throws Exception {

		orderid = CommonUtil.getUUID();
		sdinformationid = CommonUtil.getUUID();
		sdinformationRiskTypeid = CommonUtil.getUUID();
		sdinformationappntid = CommonUtil.getUUID();
		sdinformationinsuredid = CommonUtil.getUUID();
		tradeinformationid = CommonUtil.getUUID();
		sdOrderItemid = CommonUtil.getUUID();
		tradeSummaryID = NoUtil.getMaxNo("TradeSummaryID", 11);

		orderSn = PubFun.GetOrderSn();
		sdinfomationSn = PubFun.GetSDInformationSn();
		applicantSn = PubFun.GetSDAppntSn();
		insuredSn = PubFun.GetSDInsuredSn();

		createDate = new Date();

		// 在zdconfig表中获得理财险
		productId = Config.getValue(SftpCommon.FINANCING);

		if (StringUtil.isEmpty(productId)) {
			logger.info("未在zdconfig的value中查询到百年稳赢保终身寿险");
			return;
		}
		/**
		 * TODO 这段代码为了扩展 在zdconfig设置多个理财险的时候调用 String[] pids =
		 * productId.split(",");
		 */
		String[] pids = { productId };

		// 根据产品id查询
		FMRisk[] fms = getFMRisk(pids);

		if (fms != null) {
			fmrisk = fms[0];
			// 获得Schema
			SDOrdersSchema sdorder = getSDorder();
			SDInformationSchema sdInformation = getSDinformation(fmrisk);
			SDInformationAppntSchema sdinformationappnt = getSDinformationappnt();
			SDInformationInsuredSchema sdinformationinsured = getSDinformationinsured(sdinformationappnt);
			SDInformationRiskTypeSchema sdinformationRiskType = getSDinformationrisktype(sdInformation);
			TradeSummaryInfoSchema tradeSummaryInfoSchema = getTradeSummaryInfoSchema();
			TradeInformationSchema tradeInformationSchema = getTradeInformationSchema();
			SDOrderItemSchema sdOrderItemSchema =  getSDOrderItemSchema();

			// 保存一次保单
			Transaction tr = new Transaction();
			tr.add(sdorder, Transaction.INSERT);
			tr.add(sdInformation, Transaction.INSERT);
			tr.add(sdinformationappnt, Transaction.INSERT);
			tr.add(sdinformationinsured, Transaction.INSERT);
			tr.add(sdinformationRiskType, Transaction.INSERT);
			tr.add(tradeSummaryInfoSchema, Transaction.INSERT);
			tr.add(tradeInformationSchema,Transaction.INSERT);
			tr.add(sdOrderItemSchema,Transaction.INSERT);
			tr.commit();
		} else {
			logger.error("获得FMRisk的Value时异常：productId=={}", productId);
		}

	}

	/**
	 * 
	 * isPaySn:(判断是否已添加过对应的paysn). <br/>
	 *
	 * @author chouweigao
	 * @return
	 */
	private boolean existPaySn(String paysn){
		String sql = "SELECT COUNT(1) FROM sdorders WHERE paysn = ? ";
		QueryBuilder dt = new QueryBuilder(sql);
		dt.add(paysn);
		int row = dt.executeInt();
		if (row > 0) {
			return true;
		}
		return false; 
	}
	
	
	private SDOrdersSchema getSDorder() {
		SDOrdersSchema sdorders = new SDOrdersSchema();
		sdorders.setid(orderid);
		// 创建时间
		sdorders.setcreateDate(createDate);
		// 新订单修改时间与创建时间保持一致
		sdorders.setmodifyDate(sdorders.getcreateDate());
		// 获取订单号
		sdorders.setorderSn(orderSn);
		// 会员ID
		sdorders.setmemberId("");
		// 订单状态为已支付
		sdorders.setorderStatus(7);
		// 支付类型
		sdorders.setpayType(BILL_PAY_TYPE);
		// 待付状态为已支付
		sdorders.setpayStatus(2);
		// 折扣率
		sdorders.setdiscountRates("");
		// 金额
		sdorders.setpayPrice(aeonAmount);
		sdorders.setpayAmount(aeonAmount);
		sdorders.setproductTotalPrice(aeonAmount);
		sdorders.settotalAmount(aeonAmount);
		sdorders.setoffsetPoint("0");
		sdorders.setorderCoupon("0");
		sdorders.setorderIntegral("0");
		sdorders.setorderActivity("0.00");
		sdorders.setsumActivity("0.00");
		// 记录百年订单号 BNRS-151106151238143076
		sdorders.setpaySn(paysn);
		// 产品个数 默认为1
		sdorders.setproductNum("1");

		// 记录渠道
		sdorders.setchannelsn(channel);
		return sdorders;
	}

	private SDInformationSchema getSDinformation(FMRisk fmrisk) {
		SDInformationSchema sdinformation = new SDInformationSchema();
		sdinformation.setId(sdinformationid);
		sdinformation.setcreateDate(createDate);
		sdinformation.setmodifyDate(sdinformation.getcreateDate());
		// 订单明细表编号
		sdinformation.setinformationSn(sdinfomationSn);
		// 订单表
		sdinformation.setorderSn(orderSn);
		// 产品编码
		sdinformation.setproductId(fmrisk.getRiskCode());
		// 保险公司产品编码
		sdinformation.setproductOutCode(fmrisk.getOutRiskCode());
		// 产品名称
		sdinformation.setproductName(fmrisk.getRiskName());
		// 产品折扣率
		sdinformation.setdiscountRates("");
		// 商品原价(剔除产品中心折扣)
		sdinformation.setproductPrice(fmrisk.getDiscountPrice());
		sdinformation.setproductDiscountPrice(fmrisk.getDiscountPrice());
		// 产品详细页地址
		sdinformation.setproductHtmlFilePath("");
		sdinformation.setproductQuantity("1");
		// 保险公司编码
		sdinformation.setinsuranceCompany(fmrisk.getSupplierCode());
		// 开始时间
		sdinformation.setstartDate(aeonStartDate);
		sdinformation.setendDate(aeonEndDate);
		// 内部统计险种中类别
		sdinformation.setriskType(fmrisk.getRiskKind2());
		// 内部统计险种小类别
		sdinformation.setsubRiskType(fmrisk.getRiskKind3());
		// 订单id
		sdinformation.setsdorder_id(orderid);
		// 订单份数
		sdinformation.setpolicyNum("1");
		return sdinformation;
	}

	private SDInformationRiskTypeSchema getSDinformationrisktype(SDInformationSchema sdinformation) {
		SDInformationRiskTypeSchema sdinformationRiskType = new SDInformationRiskTypeSchema();
		sdinformationRiskType.setId(sdinformationRiskTypeid);
		sdinformationRiskType.setcreateDate(createDate);
		sdinformationRiskType.setmodifyDate(sdinformationRiskType.getcreateDate());
		sdinformationRiskType.setorderSn(orderSn);// 订单号
		sdinformationRiskType.setinformationSn(sdinfomationSn);// 订单明细编号
		sdinformationRiskType.setrecognizeeSn(insuredSn);// 被保人编号
		sdinformationRiskType.setapplicantSn(applicantSn);// 投保人编号
		sdinformationRiskType.setriskCode(sdinformation.getproductId());// 产品编码
		sdinformationRiskType.setriskName(sdinformation.getproductName());// 产品名称
		sdinformationRiskType.setamnt(aeonAmount);// 保额
		sdinformationRiskType.setmult("1");// 份数 默认为1
		sdinformationRiskType.setsvaliDate(sdinformation.getstartDate());// 生效日期
		sdinformationRiskType.setevaliDate(sdinformation.getendDate());// 失效日期
		sdinformationRiskType.setperiodFlag(sdinformation.getchargeType());// 缴费年期类型
		sdinformationRiskType.setperiod(sdinformation.getchargeYear());// 缴费年期
		sdinformationRiskType.setpolicyNo(aeonPolicyNo);// 保单号
		sdinformationRiskType.setelectronicCout(aeonPolicyPath);// 电子保单保险公司路径
		sdinformationRiskType.setelectronicPath("");// 电子保单物理路径
		sdinformationRiskType.setinsurerFlag("");
		sdinformationRiskType.setinsureDate(DateUtil.getCurrentDateTime());
		sdinformationRiskType.setcouponValue("0.00");
		sdinformationRiskType.setintegralValue("0.00");
		sdinformationRiskType.setactivityValue("0.00");
		sdinformationRiskType.setpayPrice(aeonAmount);
		// 结算中心 暂时不在保存程序处理 由定时计划执行
		// sdinformationRiskType.setBalanceStatus("0");
		// sdinformationRiskType.setBalanceFlag("1");
		sdinformationRiskType.setappStatus(aeonPolicyStauts);
		sdinformationRiskType.setsdinformationinsured_id(sdinformationinsuredid);
		sdinformationRiskType.setsdorder_id(orderid);
		sdinformationRiskType.setinsureMsg("");
		sdinformationRiskType.setcreateDate(createDate);
		sdinformationRiskType.setmodifyDate(sdinformationRiskType.getcreateDate());
		sdinformationRiskType.settimePrem(aeonAmount);
		sdinformationRiskType.setproductPrice(aeonAmount);
		return sdinformationRiskType;
	}

	private SDInformationAppntSchema getSDinformationappnt() {

		SDInformationAppntSchema sdinformationappnt = new SDInformationAppntSchema();
		sdinformationappnt.setId(sdinformationappntid);
		// 创建时间
		sdinformationappnt.setcreateDate(createDate);
		sdinformationappnt.setmodifyDate(sdinformationappnt.getcreateDate());
		// 订单详细表编号
		sdinformationappnt.setinformationSn(sdinfomationSn);
		// 投保人编号
		sdinformationappnt.setapplicantSn(applicantSn);
		// 投保人姓名
		sdinformationappnt.setapplicantName(aeonName);
		// 身份类型
		sdinformationappnt.setapplicantIdentityType("0");
		sdinformationappnt.setapplicantIdentityTypeName("身份证");
		sdinformationappnt.setapplicantIdentityId(aeonCardNum);
		// 性别
		sdinformationappnt.setapplicantSex(checkSex(aeonCardNum, false));
		sdinformationappnt.setapplicantSexName(checkSex(aeonCardNum, true));
		// 生日
		sdinformationappnt.setapplicantBirthday(getBir(aeonCardNum));
		// 年龄
		int age = com.sinosoft.sms.messageinterface.pubfun.PubFun.getAge(getBir(aeonCardNum));
		sdinformationappnt.setapplicantAge(String.valueOf(age));
		// 邮箱
		sdinformationappnt.setapplicantMail(aeonMail);
		// 地址
		sdinformationappnt.setapplicantAddress(aeonAdd);
		// 电话
		sdinformationappnt.setapplicantMobile(aeonPhone);
		// 订单详细表id
		sdinformationappnt.setsdinformaiton_id(sdinformationid);

		return sdinformationappnt;
	}

	private SDInformationInsuredSchema getSDinformationinsured(SDInformationAppntSchema sdinformationappnt) {
		SDInformationInsuredSchema sdinformationinsured = new SDInformationInsuredSchema();
		sdinformationinsured.setId(sdinformationinsuredid);
		// 创建时间
		sdinformationinsured.setcreateDate(createDate);
		sdinformationinsured.setmodifyDate(sdinformationinsured.getcreateDate());
		// 订单号
		sdinformationinsured.setorderSn(orderSn);
		// 详细表号
		sdinformationinsured.setinformationSn(sdinfomationSn);
		// 被保人编号
		sdinformationinsured.setrecognizeeSn(insuredSn);
		// 投被保人关系
		sdinformationinsured.setrecognizeeAppntRelation("00");
		sdinformationinsured.setrecognizeeAppntRelationName("本人");
		// 人员姓名
		sdinformationinsured.setrecognizeeName(sdinformationappnt.getapplicantName());
		// 身份类型
		sdinformationinsured.setrecognizeeIdentityType(sdinformationappnt.getapplicantIdentityType());
		sdinformationinsured.setrecognizeeIdentityTypeName(sdinformationappnt.getapplicantIdentityTypeName());
		sdinformationinsured.setrecognizeeIdentityId(sdinformationappnt.getapplicantIdentityId());
		// 性别
		sdinformationinsured.setrecognizeeSex(sdinformationappnt.getapplicantSex());
		sdinformationinsured.setrecognizeeSexName(sdinformationappnt.getapplicantSexName());
		// 生日
		sdinformationinsured.setrecognizeeBirthday(sdinformationappnt.getapplicantBirthday());
		sdinformationinsured.setrecognizeeAge(sdinformationappnt.getapplicantAge());
		// 电话
		sdinformationinsured.setrecognizeeMobile(sdinformationappnt.getapplicantMobile());
		// 邮件
		sdinformationinsured.setrecognizeeMail(sdinformationappnt.getapplicantMail());
		// 详细地址
		sdinformationinsured.setrecognizeeAddress(sdinformationappnt.getapplicantAddress());
		// 关系为本人标志 rid_td 批量上传 rid_orther 其他方式
		sdinformationinsured.setisSelf("Y");
		// 被保人唯一编码
		sdinformationinsured.setinsuredSn(orderSn + "_" + index);
		// 保险费
		sdinformationinsured.setrecognizeePrem(aeonAmount);
		// 操作
		sdinformationinsured.setrecognizeeOperate("1");
		sdinformationinsured.setmulInsuredFlag("rid_me");
		// 详细表
		sdinformationinsured.setsdinformation_id(sdinformationid);
		// 总保险费
		sdinformationinsured.setrecognizeeTotalPrem(aeonAmount);
		// key
		sdinformationinsured.setrecognizeeKey(orderSn + "_" + index);
		// 折后价格
		sdinformationinsured.setdiscountPrice(aeonAmount);
		return sdinformationinsured;
	}

	private TradeSummaryInfoSchema getTradeSummaryInfoSchema() {

		TradeSummaryInfoSchema tss = new TradeSummaryInfoSchema();
		tss.setid(tradeSummaryID);
		tss.setCreateDate(createDate);
		tss.setPaySn(paysn);
		tss.setTradeSn(paysn);
		tss.setTradeResult("0");
		tss.setOrderSns(orderSn);
		tss.setPayType(BILL_PAY_TYPE);
		tss.setPayTypeName("");
		tss.setTradeAmount(aeonAmount);
		tss.setTotalAmount(aeonAmount);
		return tss;
	}
	
	private TradeInformationSchema getTradeInformationSchema(){
		TradeInformationSchema tis = new TradeInformationSchema();
		 tis.setid(tradeinformationid);
		 tis.setcreateDate(createDate);
		 tis.setmodifyDate(createDate);
		 tis.setordAmt(aeonAmount);
		 tis.setordID(orderSn);
		 tis.setpayStatus("1");
		 tis.setpayType(BILL_PAY_TYPE);
		 tis.settradeSeriNO(paysn);
		 tis.settradeCheckSeriNo(paysn);
		 tis.setreceiveDate(DateUtil.getCurrentDateTime());
		 tis.setsendDate(DateUtil.getCurrentDateTime());
		return tis;
	}

	private SDOrderItemSchema getSDOrderItemSchema(){
		SDOrderItemSchema sdOrderItemSchema = new SDOrderItemSchema();
		sdOrderItemSchema.setid(sdOrderItemid);
		sdOrderItemSchema.setorderitemSn(PubFun.GetItemNo());
		sdOrderItemSchema.setcreateDate(createDate);
		sdOrderItemSchema.setmodifyDate(createDate);
		sdOrderItemSchema.setorderSn(orderSn);
		sdOrderItemSchema.setchannelCode(channel);
		sdOrderItemSchema.setorderPoint("0");
		sdOrderItemSchema.setpointStatus("1");
		sdOrderItemSchema.setsdorder_id(orderid);
		return sdOrderItemSchema;
	}
	/**
	 * 
	 * checkSex:(性别判断). <br/>
	 *
	 * @author chouweigao
	 * @param aeonCardNum
	 * @param isCn
	 * @return
	 */
	private String checkSex(String aeonCardNum, boolean isCn) {

		String sex_num = "";
		if (aeonCardNum.length() == 15) {
			sex_num = aeonCardNum.substring(aeonCardNum.length() - 1);
		} else if (aeonCardNum.length() == 18) {
			sex_num = aeonCardNum.substring(aeonCardNum.length() - 2, aeonCardNum.length() - 1);
		}
		boolean isM = true;
		if (Integer.parseInt(sex_num) % 2 == 0) {
			isM = false;
		} else {
			isM = true;
		}
		if (isCn) {
			if (isM) {
				return "男";
			} else {
				return "女";
			}
		} else {
			if (isM) {
				return "M";
			} else {
				return "F";
			}
		}
	}

	/**
	 * 
	 * getBir:(获得生日). <br/>
	 *
	 * @author chouweigao
	 * @param Bir
	 * @return
	 */
	public String getBir(String Bir) {

		String ymd = "";
		if (Bir.length() == 18) {
			ymd = Bir.substring(6, 14);
		} else if (Bir.length() == 15) {
			ymd = Bir.substring(6, 12);
		}
		StringBuffer sb = new StringBuffer();
		sb.append(ymd.substring(0, 4));
		sb.append("-");
		sb.append(ymd.substring(4, 6));
		sb.append("-");
		sb.append(ymd.substring(6));
		return sb.toString();
	}

	/**
	 * 
	 * getFMRisk:(获得FMRisk). <br/>
	 *
	 * @author chouweigao
	 * @param productid
	 * @return
	 * @throws Exception
	 */
	private FMRisk[] getFMRisk(String[] productId) throws Exception {

		FMRisk[] fmrisk = null;
		Map<String, Object> paramter = new HashMap<String, Object>();
		paramter.put("RiskCode", productId);
		// paramter.put("BU1", "N");//判断是否是赠险
		try {
			ProductInfoWebServiceStub.ProductInfoResponse prifr = ProductWebservice.ProductInfoSereviceImpl(paramter, null);
			fmrisk = prifr.getFMRisk();
		} catch (Exception e) {
			logger.error("获取产品中心端产品数据失败！{}", productId);
		}
		if (fmrisk != null) {
			return fmrisk;
		} else {
			return null;
		}
	}
}

/**
 * ClassName: CancelResult <br/>
 * Function: 退保结果推送给合作方. <br/>
 * date: 2016年9月2日 下午5:09:38 <br/>
 *
 * @author wwy
 * @version 
 */
class SendCancelResult implements Runnable{
	private static final Logger logger = LoggerFactory.getLogger(SendCancelResult.class);
	/**
	 * sendUrl:推送地址.
	 */
	private String sendUrl;
	/**
	 * sendData:推送数据.
	 */
	private Map<String, Object> sendData;
	
	/**
	 * sendFlag:发送区分 1已发送 其他未发送.
	 */
	private String sendFlag;
	
	/**
	 * sendResult:发送结果区分 1成功2失败.
	 */
	private String sendResult = "2";
	
	public SendCancelResult(String sendUrl, Map<String, Object> sendData) {
		super();
		this.sendUrl = sendUrl;
		this.sendData = sendData;
	}
	
	@Override
	public void run() {
		logger.info("退保结果推送启动时间：{}", DateUtil.getCurrentDateTime());
		boolean isSend = true;
		int i = 1;
		String content = "fial";
		while (isSend) {
			try {
				sendFlag = "1";
				content = HttpClientUtil.doPost(sendUrl, sendData);
				logger.info("退保结果第{}推送,结果：{}", i, content);
			} catch (Exception e) {
				logger.error("退保结果第"+ i + "推送异常" + e.getMessage(), e);
			}
			if ("success".equals(content) || i == 3) {
				if ("success".equals(content)) {
					sendResult = "1";
				}
				isSend = false;
			}else{
				isSend = true;
			}
			try {
				/**
				 * 10秒  重发
				 */
				long s = 10*1000;
				Thread.sleep(s);
			} catch (InterruptedException e) {
				logger.error("等待异常" + e.getMessage(), e);
			}
			i++;
		}
		logger.info("退保结果推送结束时间：{}", DateUtil.getCurrentDateTime());
	}
	public String getSendUrl() {
		return sendUrl;
	}
	public void setSendUrl(String sendUrl) {
		this.sendUrl = sendUrl;
	}
	public Map<String, Object> getSendData() {
		return sendData;
	}
	public void setSendData(Map<String, Object> sendData) {
		this.sendData = sendData;
	}
	public String getSendFlag() {
		return sendFlag;
	}
	public void setSendFlag(String sendFlag) {
		this.sendFlag = sendFlag;
	}
	public String getSendResult() {
		return sendResult;
	}
	public void setSendResult(String sendResult) {
		this.sendResult = sendResult;
	}
}
	