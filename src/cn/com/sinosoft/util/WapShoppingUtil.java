package cn.com.sinosoft.util;

import cn.com.sinosoft.action.shop.BaseWapShopAction;
import cn.com.sinosoft.action.shop.OrderConfigNewAction;
import cn.com.sinosoft.action.shop.uw.UsersUWCheck;
import cn.com.sinosoft.entity.BindInfoForLogin;
import cn.com.sinosoft.entity.CouponInfo;
import cn.com.sinosoft.entity.HealthyInfo;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.OrderDutyFactor;
import cn.com.sinosoft.entity.OrderRiskAppFactor;
import cn.com.sinosoft.entity.SDInformation;
import cn.com.sinosoft.entity.SDInformationAppnt;
import cn.com.sinosoft.entity.SDInformationDuty;
import cn.com.sinosoft.entity.SDInformationInsured;
import cn.com.sinosoft.entity.SDInformationInsuredElements;
import cn.com.sinosoft.entity.SDInformationProperty;
import cn.com.sinosoft.entity.SDInformationRiskType;
import cn.com.sinosoft.entity.SDInsuredHealth;
import cn.com.sinosoft.entity.SDOrder;
import cn.com.sinosoft.entity.SDOrder.SDOrderStatus;
import cn.com.sinosoft.entity.SDOrder.SDPayStatus;
import cn.com.sinosoft.entity.SDOrderItem;
import cn.com.sinosoft.entity.SDOrderItemOth;
import cn.com.sinosoft.entity.SDRelationAppnt;
import cn.com.sinosoft.entity.SDRelationRecognizee;
import cn.com.sinosoft.entity.TradeInformation;
import cn.com.sinosoft.service.AreaService;
import cn.com.sinosoft.service.BindInfoForLoginService;
import cn.com.sinosoft.service.CpsProductBuyService;
import cn.com.sinosoft.service.DealDataService;
import cn.com.sinosoft.service.DictionaryService;
import cn.com.sinosoft.service.HealthyInfoService;
import cn.com.sinosoft.service.MemberService;
import cn.com.sinosoft.service.OrderConfigNewService;
import cn.com.sinosoft.service.SDInsuredHealthService;
import cn.com.sinosoft.service.SDOrderService;
import cn.com.sinosoft.service.SDRelationAppntService;
import cn.com.sinosoft.service.SDRelationRecognizeeService;
import cn.com.sinosoft.service.TradeInformationService;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.inter.ActivityCalculate;
import com.sinosoft.inter.IntegralAction;
import com.sinosoft.inter.MailAction;
import com.sinosoft.jdt.InsureTransferNew;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.points.IntegralConstant;
import com.sinosoft.points.PointsCalculate;
import com.sinosoft.schema.SCSendEmailLogSchema;
import com.sinosoft.schema.SCSendEmailLogSet;
import com.sinosoft.schema.SDIntCalendarSchema;
import com.sinosoft.schema.SDIntCalendarSet;
import com.sinosoft.schema.memberSchema;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FEMDutyAmntPremList;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FEMRiskFactorList;
import com.wangjin.optimize.register.AutoRegister;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WapShoppingUtil {
	private static final Logger logger = LoggerFactory.getLogger(WapShoppingUtil.class);
	private Double dAmnt = 0.0;
	@SuppressWarnings("unused")
	private Double checkPrem = 0.0;

	/**
	 * @Description: 支付确认接口
	 * @param:orderService
	 * @param: tPARAMETERS
	 */
	@SuppressWarnings({ "unused", "static-access", "unchecked" })
	public Map<String, Object> getPayConfInfo(SDOrderService orderService,
			OrderConfigNewService orderConfigNewService,
			Map<String, Object> tPARAMETERS, HttpServletRequest request) {

		// 存放错误代码
		List<String> errList = new ArrayList<String>();

		// 获取请求参数
		String OrderNumber = String.valueOf(tPARAMETERS.get("OrderNumber"));// 订单号
		String PayAmount = String.valueOf(tPARAMETERS.get("PayAmount"));// 支付金额
		String StartDate = String.valueOf(tPARAMETERS.get("StartDate"));//
		String EndDate = String.valueOf(tPARAMETERS.get("EndDate"));//

		Map<String, Object> payResultMap = null;

		Map<String, Object> payConfMap = new HashMap<String, Object>();
		WapShoppingUtil tWapShoppingUtil = new WapShoppingUtil();
		SDOrder order = orderService.getOrderByOrderSn(OrderNumber);
		SDInformation information = order.getSdinformationSet().iterator()
				.next();
		// 保险公司核保
		String sql = "select prop1 from zdcode where CodeType='UWCheckClassName' and ParentCode='UWCheckClassName' and CodeValue=?";
		DataTable dt = new QueryBuilder(sql, information.getInsuranceCompany())
				.executeDataTable();
		if (dt.getRowCount() > 0) {
			String flag = dt.getString(0, 0);
			if (!"N".equals(flag)) {
				UsersUWCheck uwCheck = new UsersUWCheck();
				Map<String, Object> tMap = uwCheck.moreUWCheck(OrderNumber);
				List<Map<String, String>> tList = (List<Map<String, String>>) tMap
						.get("result");
				String tMessage = "";
				for (Map<String, String> m : tList) {
					if ("0".equals(m.get("passFlag"))) {
						tMessage = tMessage + m.get("rtnMessage");
					}
				}
				if (StringUtil.isNotEmpty(tMessage)) {
					payResultMap = new HashMap<String, Object>();
					payResultMap.put("REQUESTTYPE", "KXBJRT0024");
					payResultMap.put("STATYS", "false");
					payResultMap.put("RESULTS", null);
					payResultMap.put("ERRORSKEY", "");
					payResultMap.put("ERRORSMSG", tMessage);
					return payResultMap;
				}
			}
		}
		try {
			errList = tWapShoppingUtil.checkPayInfo(orderService,
					orderConfigNewService, OrderNumber, PayAmount, errList,
					request, payConfMap, tPARAMETERS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			errList.add("G000001");
		}

		if (errList.size() > 0) {

			payConfMap.put("OrderNumber", OrderNumber);
			payConfMap.put("OrderStatus", "False");
			return WapErrorUtil
					.dealErrorInfo(errList, "KXBJRT0024", payConfMap);

		} else {

			payConfMap.put("OrderNumber", OrderNumber);
			payConfMap.put("OrderStatus", "True");

			payResultMap = new HashMap<String, Object>();
			payResultMap.put("USER", "kxb");
			payResultMap.put("REQUESTTYPE", "KXBJRT00024");
			payResultMap.put("STATYS", "true");
			payResultMap.put("RESULTS", payConfMap);
			return payResultMap;

		}

	}

	/**
	 * 频道活动特殊处理
	 */
	@SuppressWarnings("unchecked")
	public static void dealBrandProcedure(Map<String, Object> resultMap, Map<String, Object> mServiceMap, Map<String, Object> tPARAMETERS) {
		String orderSn = String.valueOf(tPARAMETERS.get("OrderNumber")); // 订单号
		String productId = new QueryBuilder("SELECT productId FROM SDInformation WHERE orderSn=?", orderSn).executeString();
		BrandDeal brandDeal = new BrandDeal();
		boolean flag = true;
		
		if ("207101082".equals(productId)) {
			flag = brandDeal.brand207101082(mServiceMap, orderSn);
		}
		if (!flag) {
			List<String> errList = new ArrayList<String>();
			errList.add("G000001");
			resultMap = WapErrorUtil.dealErrorInfo(errList, "KXBJRT0017", (Map<String, Object>) resultMap.get("RESULTS"));
		}
	}
	
	/**
	 * 支付信息同步接口
	 */
	public static Map<String, Object> getPayResultInfo(
			Map<String, Object> mServiceMap, Map<String, Object> tPARAMETERS,
			Member member, HttpServletRequest request) {
		// 获取请求参数
		String OrderNumber = String.valueOf(tPARAMETERS.get("OrderNumber")); // 订单号
		String PayAmount = String.valueOf(tPARAMETERS.get("PayAmount")); // 支付金额
		String TradeSeriNo = String.valueOf(tPARAMETERS.get("TradeSeriNo")); // 支付流水号
		String PayType = String.valueOf(tPARAMETERS.get("PayType")); // 支付类型
		String SendDate = String.valueOf(tPARAMETERS.get("SendDate")); // 支付发送时间
		String ReciveDate = String.valueOf(tPARAMETERS.get("ReciveDate")); // 支付接受时间

		String DiscCouponNumber = String.valueOf(tPARAMETERS
				.get("DiscCouponNumber")); // 优惠劵编码
		// String CouponAmount =
		// String.valueOf(tPARAMETERS.get("CouponAmount")); // 优惠劵优惠金额
		String DiscActivePoint = String.valueOf(tPARAMETERS
				.get("DiscActivePoint")); // 使用积分数量
		// String PointAmount = String.valueOf(tPARAMETERS.get("PointAmount"));
		// // 积分抵值金额
		String DiscActiveNumber = String.valueOf(tPARAMETERS
				.get("DiscActiveNumber")); // 活动编码
		// String SubActiveNumber =
		// String.valueOf(tPARAMETERS.get("SubActiveNumber")); // 子活动编码
		// String ActiveAmount =
		// String.valueOf(tPARAMETERS.get("ActiveAmount")); // 活动优惠金额
		String openID = String.valueOf(tPARAMETERS.get("OpenID"));// 联合登录唯一标识符
		String bindType = String.valueOf(tPARAMETERS.get("BindType"));// 绑定类型（QQ、Sina、Alipay）

		List<String> errList = new ArrayList<String>();
		if (StringUtil.isEmpty(PayAmount) || StringUtil.isEmpty(TradeSeriNo)
				|| StringUtil.isEmpty(PayType) || StringUtil.isEmpty(SendDate)
				|| StringUtil.isEmpty(ReciveDate)) {
			errList.add("wapShopping00011");
		}
		SDOrderService sDOrderService = (SDOrderService) mServiceMap
				.get("SDOrderService");
		TradeInformationService tradeInformationService = (TradeInformationService) mServiceMap
				.get("TradeInformationService");

		Map<String, Object> payResultMap = null;
		Map<String, Object> payConfMap = new HashMap<String, Object>();
		// //add by wangej 20150617 获取会员等级升级数据
		Map<String, Object> memberNextGradeMap = null;

		try {
			// 处理订单表数据（需要生成结算单号）
			SDOrder order = sDOrderService.getOrderByOrderSn(OrderNumber);
			MemberService memberService = (MemberService) mServiceMap
					.get("MemberService");
			if (order == null) {
				errList.add("wapShopping00007");
				payConfMap.put("OrderNumber", OrderNumber);
				payConfMap.put("OrderStatus", "False");
				return WapErrorUtil.dealErrorInfo(errList, "KXBJRT0017",
						payConfMap);
			}
			// 是否已支付
			if (String.valueOf(order.getOrderStatus().ordinal()).equals("7")
					|| String.valueOf(order.getOrderStatus().ordinal()).equals(
							"9")
					|| String.valueOf(order.getOrderStatus().ordinal()).equals(
							"10")
					|| String.valueOf(order.getOrderStatus().ordinal()).equals(
							"11")
					|| String.valueOf(order.getOrderStatus().ordinal()).equals(
							"12")) {
				errList.add("wapShopping00003");
				payConfMap.put("OrderNumber", OrderNumber);
				payConfMap.put("OrderStatus", "False");
				return WapErrorUtil.dealErrorInfo(errList, "KXBJRT0017",
						payConfMap);
			}
			// String paySn = PubFun.GetPaySn(PayAmount, "W");
			order.setOrderStatus(SDOrderStatus.paid);
			order.setPayAmount(new BigDecimal(PayAmount));
			order.setPaySn(TradeSeriNo);
			if (member != null) {
				order.setMemberId(member.getId());
			} else {
				try {
					member = memberService.load(order.getMemberId());
				} catch (Exception e) {
					member = null;
				}
			}

			order.setPayStatus(SDPayStatus.paid);
			order.setModifyDate(new Date());
			order.setCouponSn(DiscCouponNumber);
			// order.setOrderCoupon(CouponAmount);
			order.setOffsetPoint(DiscActivePoint);
			// order.setOrderIntegral(PointAmount);
			order.setActivitySn(DiscActiveNumber);
			// order.setOrderActivity(ActiveAmount);
			order.setPayPrice(PayAmount);

			sDOrderService.update(order);

			// 处理交易表数据（需要生成结算单号）
			TradeInformation tradeInformation = tradeInformationService
					.getTradeInformationByOrdSn(OrderNumber);

			TradeInformation old = tradeInformation;

			tradeInformation.setTradeSeriNO(TradeSeriNo);
			tradeInformation.setPayType(PayType);
			tradeInformation.setPayStatus("1");

			tradeInformation.setOrdAmt(PayAmount);
			tradeInformation.setTradeCheckSeriNo(TradeSeriNo);
			tradeInformation.setSendDate(SendDate);
			tradeInformation.setTradeResult("0");
			tradeInformation.setReceiveDate(ReciveDate);
			tradeInformation.setModifyDate(new Date());

			tradeInformationService.update(tradeInformation);

			order = sDOrderService.getOrderByOrderSn(OrderNumber);

			List<SDOrder> sdorderList = new ArrayList<SDOrder>();
			sdorderList.add(order);
			// 支付成功后给投保人发送短信
			sendMessage(sdorderList, request);
			// 调用经带通处理承保
			if (!callInsureTransfer(order, old, OrderNumber, sDOrderService,
					request, member)) {
				errList.add("wapShopping00009");
				payConfMap.put("OrderNumber", OrderNumber);
				payConfMap.put("OrderStatus", "False");
				return WapErrorUtil.dealErrorInfo(errList, "KXBJRT0017",
						payConfMap);
			}
			SDInformation info = order.getSdinformationSet().iterator().next();
			// 处理产品销量
			addProductSalesVolume(info.getProductId().toString());
			/*
			 * if (!addProductSalesVolume(info.getProductId().toString())) {
			 * errList.add("wapShopping00008"); }
			 */
			// 支付成功-删除待支付邮件.
			MailAction.deleteNoPaymentMail(order.getOrderSn());
			// 处理交易信息
			dealTradeInfo(tPARAMETERS, String.valueOf(order.getTotalAmount()));
			// 处理优惠劵状态
			dealPayCoupon(DiscCouponNumber, TradeSeriNo);
			// 处理支付成功后，分单
			ActivityCalculate.activityeSplit(TradeSeriNo, sdorderList.get(0)
					.getChannelsn());
			// 处理积分
			ActivityCalculate.TransactionDealIntegral(TradeSeriNo, sdorderList
					.get(0).getChannelsn());

			// 匿名购买需要自动注册
			if (member == null) {
				AutoRegister ar = new AutoRegister();
				ar.userRegistedCheck(order, order.getOrderSn(),
						tradeInformation);
			} else if (StringUtil.isNotEmpty(openID)) {
				dealUnionLogin(sdorderList.get(0), tradeInformation,
						mServiceMap, openID, bindType);
			}
			// 积分送会员
			ActivityCalculate.transPointToMember(TradeSeriNo);
			String memberID = new QueryBuilder(
					" SELECT memberid FROM sdorders WHERE paysn=? limit 1",
					TradeSeriNo).executeString();
			// 处理积分抵值
			dealPayPoint(DiscActivePoint, memberID, TradeSeriNo);
			// 处理支付后活动：满送、买送、高倍积分
			ActivityCalculate.TransactionDeal(sdorderList, memberID,
					sdorderList.get(0).getChannelsn());

			// 向代理人推送数据
			dealCpsOrder(sdorderList);
			// 发送支付成功邮件
			if (member != null) {
				// 使用新的member对象发送邮件，防止hibernate自动更新持久化对象
				Member tMember = new Member();
				tMember.setEmail(member.getEmail());
				LoginSuccessPay(order, tMember, tradeInformationService, request);
			}
			// add by wangej 20150617 获取会员等级升级数据
			PointsCalculate pointsCalculate = new PointsCalculate();
			memberNextGradeMap = pointsCalculate.getMemberUpgradeInfo(memberID,
					sdorderList, null);

		} catch (Exception e) {
			errList.add("G000001");
			logger.error(e.getMessage(), e);
		}

		if (errList.size() > 0) {

			payConfMap.put("OrderNumber", OrderNumber);
			payConfMap.put("OrderStatus", "False");
			return WapErrorUtil
					.dealErrorInfo(errList, "KXBJRT0017", payConfMap);

		} else {

			payConfMap.put("OrderNumber", OrderNumber);
			payConfMap.put("OrderStatus", "True");
			// add by wangej 20150617 获取会员等级升级数据
			payConfMap.put("Upgrade", memberNextGradeMap.get("Upgrade"));
			payConfMap.put("CurrentGrade",
					memberNextGradeMap.get("CurrentGrade"));
			payConfMap.put("CurrentGradeName",
					memberNextGradeMap.get("CurrentGradeName"));
			payConfMap.put("HavedNextGrade",
					memberNextGradeMap.get("HavedNextGrade"));
			payConfMap.put("NextGrade", memberNextGradeMap.get("NextGrade"));
			payConfMap.put("NextGradeName",
					memberNextGradeMap.get("NextGradeName"));
			payConfMap.put("UpgradeCount",
					memberNextGradeMap.get("UpgradeCount"));
			payConfMap.put("UpgradeAmount",
					memberNextGradeMap.get("UpgradeAmount"));
			payConfMap.put("OrderGetPoints", memberNextGradeMap.get("Points"));

			payResultMap = new HashMap<String, Object>();
			payResultMap.put("USER", "kxb");
			payResultMap.put("REQUESTTYPE", "KXBJRT0017");
			payResultMap.put("STATYS", "true");
			payResultMap.put("RESULTS", payConfMap);
			return payResultMap;

		}
	}

	/**
	 * CPS保存订单接口
	 */
	public static void dealCpsOrder(List<SDOrder> sdorderList) {
		try {
			for (SDOrder sdorder : sdorderList) {

				String webServiceUrl = null;

				if (sdorder == null
						|| StringUtil.isEmpty(sdorder.getChannelsn())) {
					return;
				}

				if (sdorder.getChannelsn().endsWith("agent")) {
					webServiceUrl = Config.getValue("AGENTSERVERICEURL");

				} else if (sdorder.getChannelsn().endsWith("cps")) {
					webServiceUrl = Config.getValue("CPSSERVERICEURL");

				} else {
					return;
				}

				Service servicemodel = new ObjectServiceFactory()
						.create(CpsProductBuyService.class);
				CpsProductBuyService service = (CpsProductBuyService) new XFireProxyFactory()
						.create(servicemodel, webServiceUrl);
				service.saveOrder(sdorder.getOrderSn());
			}
		} catch (MalformedURLException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 处理交易信息，用于分单处理
	 * 
	 * @Title: dealTradeInfo
	 * @return void 返回类型
	 */
	public static void dealTradeInfo(Map<String, Object> tPARAMETERS,
			String cTotalAmount) {

		String DiscCouponNumber = String.valueOf(tPARAMETERS
				.get("DiscCouponNumber")); // 优惠劵编码
		String CouponAmount = String.valueOf(tPARAMETERS.get("CouponAmount")); // 优惠劵优惠金额
		String DiscActivePoint = String.valueOf(tPARAMETERS
				.get("DiscActivePoint")); // 使用积分数量
		String PointAmount = String.valueOf(tPARAMETERS.get("PointAmount")); // 积分抵值金额
		String DiscActiveNumber = String.valueOf(tPARAMETERS
				.get("DiscActiveNumber")); // 活动编码
		// String SubActiveNumber =
		// String.valueOf(tPARAMETERS.get("SubActiveNumber")); // 子活动编码
		String ActiveAmount = String.valueOf(tPARAMETERS.get("ActiveAmount")); // 活动优惠金额
		String OrderNumber = String.valueOf(tPARAMETERS.get("OrderNumber")); // 订单号
		String PayAmount = String.valueOf(tPARAMETERS.get("PayAmount")); // 支付金额
		String TradeSeriNo = String.valueOf(tPARAMETERS.get("TradeSeriNo")); // 支付流水号
		String PayType = String.valueOf(tPARAMETERS.get("PayType")); // 支付类型
		// String SendDate = String.valueOf(tPARAMETERS.get("SendDate")); //
		// 支付发送时间
		// String ReciveDate = String.valueOf(tPARAMETERS.get("ReciveDate")); //
		// 支付接受时间

		Transaction trans = new Transaction();
		try {
			QueryBuilder tradesummaryinfocountQB = new QueryBuilder(
					" SELECT COUNT(1) FROM tradesummaryinfo WHERE PaySn=? ");
			tradesummaryinfocountQB.add(TradeSeriNo);
			int tradesummaryinfocount = tradesummaryinfocountQB.executeInt();
			if (tradesummaryinfocount <= 0) {
				QueryBuilder inserttradesummaryinfoQB = new QueryBuilder(
						" INSERT INTO tradesummaryinfo VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
				inserttradesummaryinfoQB.add(NoUtil.getMaxNo("TradeSummaryID",
						11));
				inserttradesummaryinfoQB.add(TradeSeriNo);
				inserttradesummaryinfoQB.add(TradeSeriNo);
				inserttradesummaryinfoQB.add("0");
				inserttradesummaryinfoQB.add(OrderNumber);
				if (StringUtil.isNotEmpty(CouponAmount)) {
					inserttradesummaryinfoQB.add(CouponAmount);
				} else {
					inserttradesummaryinfoQB.add("");
				}
				if (StringUtil.isNotEmpty(ActiveAmount)) {
					inserttradesummaryinfoQB.add(ActiveAmount);
				} else {
					inserttradesummaryinfoQB.add("");
				}
				if (StringUtil.isNotEmpty(PointAmount)) {
					inserttradesummaryinfoQB.add(PointAmount);
				} else {
					inserttradesummaryinfoQB.add("");
				}
				inserttradesummaryinfoQB.add(DiscCouponNumber);
				inserttradesummaryinfoQB.add(PayType);
				QueryBuilder paytypeqb = new QueryBuilder(
						" SELECT description FROM paybase WHERE payType=? LIMIT 1 ");
				paytypeqb.add(PayType);
				inserttradesummaryinfoQB.add(paytypeqb.executeString());
				inserttradesummaryinfoQB.add(cTotalAmount);
				inserttradesummaryinfoQB.add(PayAmount);
				inserttradesummaryinfoQB.add(PubFun.getCurrent());
				inserttradesummaryinfoQB.add(PubFun.getCurrent());

				trans.add(inserttradesummaryinfoQB);
			} else {
				QueryBuilder upadtetradesummaryinfoQB = new QueryBuilder(
						" UPDATE tradesummaryinfo SET TradeResult=?,OrderSns=?,CouponSumAmount=?,ActivitySumAmount=?,PointSumAmount=?,PayType=?,TotalAmount=?,TradeAmount=?,ModifyDate=?,CouponSn=? WHERE paysn=? ");
				upadtetradesummaryinfoQB.add("0");
				upadtetradesummaryinfoQB.add(OrderNumber);
				if (StringUtil.isNotEmpty(CouponAmount)) {
					upadtetradesummaryinfoQB.add(CouponAmount);
				} else {
					upadtetradesummaryinfoQB.add("");
				}
				if (StringUtil.isNotEmpty(ActiveAmount)) {
					upadtetradesummaryinfoQB.add(ActiveAmount);
				} else {
					upadtetradesummaryinfoQB.add("");
				}
				if (StringUtil.isNotEmpty(PointAmount)) {
					upadtetradesummaryinfoQB.add(PointAmount);
				} else {
					upadtetradesummaryinfoQB.add("");
				}
				upadtetradesummaryinfoQB.add(PayType);
				upadtetradesummaryinfoQB.add(cTotalAmount);
				upadtetradesummaryinfoQB.add(PayAmount);
				upadtetradesummaryinfoQB.add(PubFun.getCurrent());
				upadtetradesummaryinfoQB.add(DiscCouponNumber);
				upadtetradesummaryinfoQB.add(TradeSeriNo);

				trans.add(upadtetradesummaryinfoQB);
			}
			QueryBuilder tradeinfocountQB = new QueryBuilder(
					" SELECT COUNT(1) FROM tradeinfo WHERE PaySn=? ");
			tradeinfocountQB.add(TradeSeriNo);
			int tradeinfocount = tradeinfocountQB.executeInt();
			if (tradeinfocount <= 0) {
				QueryBuilder inserttradeinfoQB = new QueryBuilder(
						" INSERT INTO tradeinfo VALUES(?,?,?,?,?,?,?,?,?,?) ");
				inserttradeinfoQB.add(TradeSeriNo);
				inserttradeinfoQB.add(OrderNumber);
				if (StringUtil.isNotEmpty(DiscCouponNumber)) {
					inserttradeinfoQB.add(DiscCouponNumber);
				} else {
					inserttradeinfoQB.add("");
				}
				inserttradeinfoQB.add(cTotalAmount);
				if (StringUtil.isNotEmpty(DiscActivePoint)) {
					inserttradeinfoQB.add(DiscActivePoint);
				} else {
					inserttradeinfoQB.add("");
				}
				inserttradeinfoQB.add(PayType);
				inserttradeinfoQB.add("已支付");
				if (StringUtil.isNotEmpty(DiscActiveNumber)) {
					inserttradeinfoQB.add(DiscActiveNumber);
				} else {
					inserttradeinfoQB.add("");
				}
				inserttradeinfoQB.add(PubFun.getCurrent());
				inserttradeinfoQB.add(PubFun.getCurrent());

				trans.add(inserttradeinfoQB);
			} else {
				QueryBuilder upadtetradeinfoQB = new QueryBuilder(
						" UPDATE tradeinfo SET couponSn=?,totalAmnout=?,integral=?,payType=?,modifyDate=? WHERE paySn=? ");
				if (StringUtil.isNotEmpty(DiscCouponNumber)) {
					upadtetradeinfoQB.add(DiscCouponNumber);
				} else {
					upadtetradeinfoQB.add("");
				}
				upadtetradeinfoQB.add(cTotalAmount);
				if (StringUtil.isNotEmpty(DiscActivePoint)) {
					upadtetradeinfoQB.add(DiscActivePoint);
				} else {
					upadtetradeinfoQB.add("");
				}
				upadtetradeinfoQB.add(PayType);
				upadtetradeinfoQB.add(PubFun.getCurrent());
				upadtetradeinfoQB.add(TradeSeriNo);

				trans.add(upadtetradeinfoQB);
			}
			if (!trans.commit()) {
				logger.error("类（WapShoppingUtil.dealTradeInfo）第三方平台生成交易流水数据失败，trans:{}"
						,trans.getExceptionMessage());
			}
		} catch (Exception e) {
			logger.error("类（WapShoppingUtil.dealTradeInfo）第三方平台生成交易流水数据失败，eMsg:"
					+ e.getMessage(),e);
		}

	}

	/**
	 * 处理积分抵值
	 * 
	 * @Title: dealPayPoint
	 */
	public static void dealPayPoint(String cOffsetPoint, String memberID,
			String cPaySn) {
		// 使用积分抵值，积分流水表中插入积分抵值记录
		if (StringUtil.isNotEmpty(cOffsetPoint)) {
			BigDecimal offsetPoint = new BigDecimal(cOffsetPoint);
			if (offsetPoint.compareTo(new BigDecimal("0")) == 1) {
				Transaction trans = new Transaction();
				SDIntCalendarSchema tSDIntCalendarSchema = new SDIntCalendarSchema();
				tSDIntCalendarSchema.setID(NoUtil.getMaxID("IntID") + "");
				tSDIntCalendarSchema.setMemberId(memberID);// 会员号
				tSDIntCalendarSchema.setIntegral(cOffsetPoint);// 积分值
				tSDIntCalendarSchema.setSource("2");// 积分抵值
				tSDIntCalendarSchema.setBusinessid(cPaySn);// 交易流水号
				tSDIntCalendarSchema.setManner("1");// 表示支出
				tSDIntCalendarSchema.setStatus("0"); // 正常
				// add by wangej 20150526 积分轨迹表增加数据信息描述 begin
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("PointsGive", "01");
				try {
					Map<String, Object> map = new PointsCalculate()
							.pointsManage(IntegralConstant.POINT_SEARCH, "2",
									param);
					@SuppressWarnings("unchecked")
					List<Map<String, Object>> list = (List<Map<String, Object>>) map
							.get(IntegralConstant.DATA);
					if (list.size() > 0) {
						Map<String, Object> map_data = list.get(0);
						tSDIntCalendarSchema.setDescription(String
								.valueOf(map_data.get("PointDes")));
					} else {
						tSDIntCalendarSchema.setDescription("购买产品积分抵值");
						logger.error("购买产品积分抵值查询规则无数据，");
					}
				} catch (Exception e) {
					logger.error(e.getMessage(),e);
				}
				// add by wangej 20150526 积分轨迹表增加数据信息描述 end
				tSDIntCalendarSchema.setCreateDate(PubFun.getCurrentDate());
				tSDIntCalendarSchema.setCreateTime(PubFun.getCurrentTime());
				QueryBuilder qb = new QueryBuilder(
						" UPDATE member SET currentValidatePoint = currentValidatePoint-? WHERE id=? ");
				qb.add(Integer.parseInt(cOffsetPoint));
				qb.add(memberID);

				trans.add(tSDIntCalendarSchema, Transaction.INSERT);
				trans.add(qb);
				if (!trans.commit()) {
					logger.error("类WapShoppingUtil.dealPayPoint，处理积分抵值出错，{}",trans.getExceptionMessage());
				}

			}
		}
	}

	/**
	 * 处理优惠劵状态
	 * 
	 * @Title: dealPayCoupon
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @return void 返回类型
	 * @author XXX
	 */
	public static void dealPayCoupon(String cDiscCouponNumber,
			String TradeSeriNo) {

		if (StringUtil.isNotEmpty(cDiscCouponNumber)) {

		}
		QueryBuilder qb = new QueryBuilder(
				" UPDATE couponinfo SET `status`='1',orderSn=?,payTime=now(),modifyDate=now() WHERE couponSn=? ");
		qb.add(TradeSeriNo);
		qb.add(cDiscCouponNumber);
		Transaction trans = new Transaction();
		trans.add(qb);
		if (!trans.commit()) {
			logger.error("类WapShoppingUtil.dealPayCoupon,处理优惠劵状态失败，{}", trans.getExceptionMessage());
		}

	}

	/**
	 * 订单支付完成(已登录).
	 * 
	 * @param sorder
	 * @param old
	 */
	public static boolean LoginSuccessPay(SDOrder sorder, Member member,
			TradeInformationService tradeInformationService,
			HttpServletRequest request) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			List<String> orderSns = new ArrayList<String>();
			map.put("orderSns", orderSns);
			ActionUtil.sendMessage("wj00016", map);
			ActionUtil.dealAction("wj00016", map, request);
		} catch (Exception e) {
			logger.error("订单支付完成(已登录)出现异常: LoginSuccessPay方法出现异常" + e.getMessage(), e);
			return false;
		}
		return true;
	}

	/**
	 * 
	 * 支付成功后给投保人发送短信
	 */
	public static void sendMessage(List<SDOrder> sdorderList,
			HttpServletRequest request) {
		try {
			if (sdorderList != null && sdorderList.size() >= 1) {
				for (SDOrder sdor : sdorderList) {
					String orSn = sdor.getOrderSn();
					Set<SDInformation> informationSet = sdor
							.getSdinformationSet();
					if (informationSet != null && informationSet.size() > 0) {
						SDInformation information = informationSet.iterator()
								.next();
						Set<SDInformationAppnt> appntSet = information
								.getSdinformationappntSet();
						if (appntSet != null && appntSet.size() > 0) {
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("paySn", sdor.getPaySn());
							ActionUtil.sendMessage("wj00091", map);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	/**
	 * 增加产品销量
	 */
	public static boolean addProductSalesVolume(String cProduct) {

		try {
			Transaction trans = new Transaction();
			String sql = "select RelaID from zdcolumnvalue where columncode ='RiskCode' and textvalue= ?";
			DataTable dt;
			String condition = "";

			// 更新产品中心
			trans.add(new QueryBuilder(
					"update femrisk set SalesVolume = (SalesVolume + 1) where RiskCode=?",
					cProduct));

			trans.add(new QueryBuilder(
					"update femriskb set SalesVolume = (SalesVolume + 1) where RiskCode=?",
					cProduct));

			// 更新搜索产品销量数据同步表
			trans.add(new QueryBuilder(
					"update sdsearchrelaproduct set SalesVolume = (SalesVolume + 1) where ProductID=?",
					cProduct));
			condition = "";
			// 取得关联ID
			dt = new QueryBuilder(sql, cProduct).executeDataTable();
			for (int j = 0; j < dt.getRowCount(); j++) {
				condition += dt.getString(j, 0);
				if (j != dt.getRowCount() - 1) {
					condition += ",";
				}
			}
			if (!StringUtil.isEmpty(condition)) {
				// 更新电商cms产品中心产品销量字段
				trans.add(new QueryBuilder(
						"update zdcolumnvalue set TextValue = (TextValue + 1) where RelaID in ("
								+ condition + ") and columncode ='SalesVolume'"));
			}

			if (!trans.commit()) {
				logger.error("产品中心产品增加销量失败");
				return false;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

	/**
	 * 调用经代通处理.
	 * 
	 * @param sorder
	 * @param old
	 * @param OrdId
	 */
	@SuppressWarnings("static-access")
	public static boolean callInsureTransfer(SDOrder sorder,
			TradeInformation old, String OrdId, SDOrderService sdorderService,
			HttpServletRequest request, Member member) {
		String ErrMsg = "";

		// 调用横向接口
		List<String> UWUNSucess = sdorderService.checkUnderwriting(sorder,
				request);
		String comCode = "";
		Set<SDInformationInsured> sdinformationInsuredSet = null;
		SDInformation sdinformation = null;
		String appStatus = "";
		boolean flag = true;
		if (sorder != null) {
			Set<SDInformation> sdInformationSet = sorder.getSdinformationSet();
			for (SDInformation sdi : sdInformationSet) {
				sdinformation = sdi;
			}
			if (sdinformation != null) {
				comCode = sdinformation.getInsuranceCompany();
				sdinformationInsuredSet = sdinformation
						.getSdinformationinsuredSet();
				for (SDInformationInsured sdInsured : sdinformationInsuredSet) {
					String ordersn = sdInsured.getOrderSn();
					String recognizeeSn = sdInsured.getRecognizeeSn();
					String sql = "select appStatus,insureMsg from SDInformationRiskType where orderSn = ? and recognizeeSn = ?";
					DataTable dt = new QueryBuilder(sql, ordersn, recognizeeSn)
							.executeDataTable();
					if (dt.getRowCount() > 0) {
						appStatus = dt.getString(0, 0);
					}
					if ("1".equals(appStatus)) {
						flag = false;
						break;
					}
				}
			}
		}

		if (flag) {
			// 若为空则调用经代通
			String insureMsg = "";
			try {
				InsureTransferNew tInsureTransfer = new InsureTransferNew();
				tInsureTransfer.callInsTransInterface(comCode,
						sorder.getOrderSn(), UWUNSucess);
				for (SDInformationInsured sdInsured : sdinformationInsuredSet) {
					String ordersn = sdInsured.getOrderSn();
					String recognizeeSn = sdInsured.getRecognizeeSn();
					String sql = "select appStatus,insureMsg from SDInformationRiskType where orderSn = ? and recognizeeSn = ?";
					DataTable dt = new QueryBuilder(sql, ordersn, recognizeeSn)
							.executeDataTable();
					if (dt.getRowCount() > 0) {
						appStatus = dt.getString(0, 0);
						insureMsg = dt.getString(0, 1);
					} else {
						logger.error("未查询到SDInformationRiskType表中订单相关信息");
						flag = false;
						break;
					}
					if (!"1".equals(appStatus)) {
						flag = false;
						break;
					}
				}
				if (!flag) {// 表示投保失败
					logger.info("=后台回调方法=经代通失败 调用客户邮件");

					ErrMsg = "经代通失败:" + insureMsg;

					if (StringUtil.isEmpty(insureMsg)) {
						ErrMsg = "经代通失败:未收到保险公司回写结果";
					}
					sdorderService.sendErrorMail(sorder.getOrderSn(), ErrMsg,
							"", request);

				}
			} catch (Exception e) {
				ErrMsg = "callInsureTransfer方法出现异常:" + e.getMessage();
				sdorderService.sendErrorMail(OrdId, ErrMsg, "", request);
				logger.error("调用经代通处理出现异常: callInsureTransfer方法出现异常"
						+ e.getMessage(),e);
				return false;
			}
		}
		return true;
	}

	/**
	 * 计算积分.
	 * 
	 * @param sdorder
	 * @param coupon
	 * @param sdorderCouponsnValue
	 *            优惠劵金额
	 */
	public static void countPoint(List<SDOrder> sdorderList, CouponInfo coupon,
			String sdorderCouponsnValue,
			TradeInformationService tradeInformationService,
			SDOrderService sdorderService) {
		try {
			// 计算积分
			SDInformation sdinformation = null;
			String productId = "";
			Transaction trans = new Transaction();
			String orderinfo = "";
			for (SDOrder sorder : sdorderList) {
				for (SDInformation sdi : sorder.getSdinformationSet()) {
					sdinformation = sdi;
				}
				TradeInformation old = tradeInformationService
						.getTradeInformationByOrdSn(sorder.getOrderSn());
				if (sdinformation != null) {
					productId = sdinformation.getProductId();
					String sql1 = "select ProductType,FeeRate from sdproduct where productid=?";
					String[] sql1temp = { productId };
					GetDBdata db0 = new GetDBdata();
					int Integral = 0;
					try {
						List<HashMap<String, String>> a = db0.query(sql1,
								sql1temp);
						if (a != null && a.get(0) != null) {
							String productType = a.get(0).get("ProductType");
							String feeRate = a.get(0).get("FeeRate");
							Object[] argArr = {sorder.getMemberId(), feeRate, old.getOrdAmt(), productType};
							logger.info("计算积分:{}feeRate:{} tTradeInformation.getOrdAmt()：{} productType{}", argArr);
							// 计算积分前进行优惠劵抵扣
							String orderAmt = old.getOrdAmt();
							if (coupon != null) {
								BigDecimal b1 = new BigDecimal(old.getOrdAmt());
								BigDecimal b2 = coupon.getParValue();
								BigDecimal b3 = b1.subtract(b2);
								orderAmt = String.valueOf(b3.setScale(2,
										BigDecimal.ROUND_HALF_UP));
								logger.info("优惠劵抵扣后的积分计算金额：{}", orderAmt);
							} else if (!("".equals(sdorderCouponsnValue))) {
								BigDecimal b1 = new BigDecimal(old.getOrdAmt());
								BigDecimal b2 = new BigDecimal(
										sdorderCouponsnValue);
								BigDecimal b3 = b1.subtract(b2);
								orderAmt = String.valueOf(b3.setScale(2,
										BigDecimal.ROUND_HALF_UP));
								logger.info("活动抵扣后的积分计算金额：{}", orderAmt);
							}
							Integral = IntegralAction.paySuccess_(
									sorder.getMemberId(), feeRate, orderAmt,
									productType);
						}
					} catch (Exception e) {
						logger.error("计算积分error:" + e.getMessage(),e);
					}
					logger.info("计算积分:{}", Integral);
					QueryBuilder infoQB = new QueryBuilder(
							" update sdinformation set point=? where orderSn=? ",
							Integer.toString(Integral), sorder.getOrderSn());
					trans.add(infoQB);
					orderinfo = orderinfo + "," + sorder.getOrderSn();
				}
			}
			if (!trans.commit()) {
				logger.error("计算积分失败--Order={}", orderinfo);
			}
		} catch (Exception e) {
			logger.error("保存订单状态error:" + e.getMessage(),e);
		}

	}

	/**
	 * 会员下单后，冻结积分处理 sdorderList 订单
	 * 
	 * @param sdorderList
	 */
	public static void transPointToSD(List<SDOrder> sdorderList) {
		try {
			String sql = "";
			for (SDOrder order : sdorderList) {
				sql = "select s2.id,s1.orderSn,s2.point,s2.pointStatus,s1.memberId,s1.OrderStatus  "
						+ "from sdorders s1,SDinformation  s2 where s1.OrderStatus='7' "
						+ "and memberid is not null and (s2.pointStatus is null or s2.pointStatus='0' or s2.pointStatus = '') "
						+ "and  s1.ordersn=s2.ordersn  and s1.orderSn=?";
				QueryBuilder qb = new QueryBuilder(sql);
				qb.add(order.getOrderSn());
				DataTable dt = qb.executeDataTable();

				if (dt != null && dt.getRowCount() != 0) {
					Transaction transaction = new Transaction();
					SDIntCalendarSet tSDIntCalendarSet = new SDIntCalendarSet();
					for (int i = 0; i < dt.getRowCount(); i++) {
						SDIntCalendarSchema tSDIntCalendarSchema = new SDIntCalendarSchema();
						tSDIntCalendarSchema.setID(NoUtil.getMaxID("IntID")
								+ "");
						tSDIntCalendarSchema.setMemberId(dt.getString(i,
								"memberId"));// 会员号
						tSDIntCalendarSchema.setIntegral(dt.getString(i,
								"point"));// 积分值
						tSDIntCalendarSchema.setSource("0");// 表示购买产品
						tSDIntCalendarSchema.setBusinessid(dt.getString(i,
								"orderSn"));// 业务号
						tSDIntCalendarSchema.setManner("0");// 表示收入
						tSDIntCalendarSchema.setStatus("1"); // 保单没有生效冻结
						tSDIntCalendarSchema.setCreateDate(PubFun
								.getCurrentDate());
						tSDIntCalendarSchema.setCreateTime(PubFun
								.getCurrentTime());
						tSDIntCalendarSet.add(tSDIntCalendarSchema);

						memberSchema tmemberSchema = new memberSchema();
						tmemberSchema.setid(dt.getString(i, "memberId"));
						if (tmemberSchema.fill()) {
							// 无法通过单事物完成，积分需要根据会员累计
							Transaction tran = new Transaction();
							// 查询会员更新会员冻结积分
							if (StringUtil.isNotEmpty(dt.getString(i, "point"))) {
								tmemberSchema.setpoint(tmemberSchema.getpoint()
										+ Integer.parseInt(dt.getString(i,
												"point")));
							}
							tran.add(tmemberSchema, Transaction.UPDATE);
							// 同时更新积分状态
							tran.add(new QueryBuilder(
									"update SDinformation set pointStatus = '1' where id =  ? ",
									dt.getString(i, "id")));
							tran.commit();
						}
					}
					transaction.add(tSDIntCalendarSet, Transaction.INSERT);
					if (!transaction.commit()) {
						logger.error("WapShoppingUtil执行方法transPointToSD()会员冻结积分处理失败--orderSn=({})", order.getOrderSn());
					}
				}
			}

		} catch (Exception e) {
			logger.error("类WapShoppingUtil执行方法transPointToSD()发生异常！" + e.getMessage(), e);
		}
	}

	/**
	 * @return 返回错误代码集合
	 */
	@SuppressWarnings({ "unchecked" })
	public List<String> checkPayInfo(SDOrderService orderService,
			OrderConfigNewService orderConfigNewService, String orderSn,
			String payAmount, List<String> errList, HttpServletRequest request,
			Map<String, Object> payConfMap, Map<String, Object> tPARAMETERS) {

		SDOrder order = orderService.getOrderByOrderSn(orderSn);

		if (order == null) {
			errList.add("wapShopping00007");
			return errList;
		}

		SDInformation information = order.getSdinformationSet().iterator()
				.next();

		Set<SDInformationInsured> insureList = information
				.getSdinformationinsuredSet();

		SDInformationAppnt appnt = information.getSdinformationappntSet()
				.iterator().next();

		// TODO 渠道编码从订单中获取，在订单保存时需要保存此信息
		String channelCode = order.getChannelsn();
		// 活动是否有效
		String tDiscActiveNumber = String.valueOf(tPARAMETERS
				.get("DiscActiveNumber"));
		String tSubActiveNumber = String.valueOf(tPARAMETERS
				.get("SubActiveNumber"));
		// 活动优惠（打折、满减）
		String tActiveAmount = dealNullToString(String.valueOf(tPARAMETERS
				.get("ActiveAmount")));
		errList = checkPayActive(tDiscActiveNumber, tSubActiveNumber,
				channelCode, orderSn, tActiveAmount);
		if (errList.size() >= 1) {
			return errList;
		}

		// 优惠劵是否有效
		// String tDiscCouponNumber =
		// String.valueOf(tPARAMETERS.get("DiscCouponNumber"));
		// 优惠劵优惠
		String tCouponAmount = dealNullToString(String.valueOf(tPARAMETERS
				.get("CouponAmount")));
		errList = checkPayCoupon(tPARAMETERS, channelCode, order.getMemberId(),
				order.getOrderSn());
		if (errList.size() >= 1) {
			return errList;
		}

		// 积分是否有效
		String tDiscActivePoint = dealNullToString(String.valueOf(tPARAMETERS
				.get("DiscActivePoint")));
		// 积分抵值优惠
		String tPointAmount = dealNullToString(String.valueOf(tPARAMETERS
				.get("PointAmount")));
		errList = checkPayPoint(tDiscActivePoint, order.getMemberId(),
				tPointAmount);
		if (errList.size() >= 1) {
			return errList;
		}
		// 保费校验--先处理保费校验，用于wap前端返回
		String productCode = information.getProductId();
		Map<String, Object> params = orderService.getProductInformation(
				productCode, "N", "");
		String[] baseInformations = (String[]) params.get("baseInformation");
		List<OrderRiskAppFactor> riskAppFactior = (List<OrderRiskAppFactor>) params
				.get("riskAppFactor");
		List<OrderDutyFactor> dutyFactor = (List<OrderDutyFactor>) params
				.get("dutyFactor");
		List<SDInformationInsured> insuredlist = new ArrayList<SDInformationInsured>();
		for (SDInformationInsured sDInformationInsured : insureList) {
			insuredlist.add(sDInformationInsured);
		}
		BigDecimal totalAmnt = relCalPrem(baseInformations, riskAppFactior,
				dutyFactor, information, insuredlist, orderConfigNewService,
				orderService);
		this.checkPrem = Double.parseDouble(String.valueOf(totalAmnt));
		BigDecimal totalPayAmnt = totalAmnt
				.subtract(new BigDecimal(tCouponAmount))
				.subtract(new BigDecimal(tPointAmount))
				.subtract(new BigDecimal(tActiveAmount));
		payConfMap.put("PayableAmount", String.valueOf(totalPayAmnt));
		BigDecimal newPrem = new BigDecimal(payAmount).setScale(2,
				BigDecimal.ROUND_HALF_UP);
		if (newPrem.compareTo(totalPayAmnt) != 0) {
			errList.add("UnderWriting000026");
			return errList;
		}
		// 实际支付金额
		String tActualAmountPaid = String.valueOf(tPARAMETERS.get("PayAmount"));
		// 参与活动后，保费对比
		BigDecimal offeramount = new BigDecimal(tActualAmountPaid)
				.add(new BigDecimal(tCouponAmount))
				.add(new BigDecimal(tPointAmount))
				.add(new BigDecimal(tActiveAmount));
		if (offeramount.compareTo(totalAmnt) != 0) {
			errList.add("UnderWriting000026");
			return errList;
		}
		// 是否过期
		try {
			/*
			 * // 投保 保险起期 long startTime = sdf_1.parse(
			 * sdf_1.format(information.getStartDate())).getTime(); // 当前日期 long
			 * curentTime = sdf_1.parse(sdf_1.format(new Date())).getTime(); if
			 * (startTime <= curentTime) { errList.add("wapShopping00001");
			 * return errList; }
			 */
			if (!isOutPeriod(order, information.getProductId())) {
				errList.add("wapShopping00001");
				return errList;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			errList.add("wapShopping00001");
			return errList;
		}

		// 支付金额
		// BigDecimal newPrem1 = new
		// BigDecimal(order.getTotalAmount().toString()).setScale(2,
		// BigDecimal.ROUND_HALF_UP);
		if (newPrem.compareTo(totalPayAmnt) != 0) {
			errList.add("wapShopping00002");
			return errList;
		}

		// 是否已支付
		if (String.valueOf(order.getOrderStatus().ordinal()).equals("7")) {
			errList.add("wapShopping00003");
			return errList;
		}
		// 订单是否已取消
		if (String.valueOf(order.getOrderStatus().ordinal()).equals("3")
				|| String.valueOf(order.getOrderStatus().ordinal()).equals("8")) {
			errList.add("wapShopping00004");
			return errList;
		}
		// 已撤单
		Set<SDInformationRiskType> sdinformationrisktypeSet = order
				.getSdinformationrisktypeSet();
		for (SDInformationRiskType sdInformationRiskType : sdinformationrisktypeSet) {
			String appStatus = sdInformationRiskType.getAppStatus();
			// 0 未承保 1 承保 2撤单
			if ("1".equals(appStatus) || "2".equals(appStatus)) {

				errList.add("wapShopping00005");
				return errList;
			}
		}

		// 限购
		if (!isProductDownShelf(information)) {
			errList.add("wapShopping00006");
			return errList;
		}

		// 发送待支付邮件
		Map<String, Object> sdinteraction = new HashMap<String, Object>();// 横向接口调用参数
		Member member = new Member();
		String applicantName = appnt.getApplicantName();
		member.setEmail(appnt.getApplicantMail());
		// ActionUtil actionUtil = new ActionUtil();
		String sql = "select value from zdconfig where type= 'payUrl'";
		GetDBdata db = new GetDBdata();
		String path = "";
		try {
			path = db.getOneValue(sql);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		sdinteraction.put("Member", member);
		sdinteraction.put("MemberName", applicantName);
		sdinteraction.put("OrderSn", order.getOrderSn());
		sdinteraction.put("ProductName", information.getProductName());
		sdinteraction.put("ChannelSn", "wap");
		sdinteraction.put("path",
				(path + "?orderSn=" + order.getOrderSn() + "&payType=0")
						.replace("&", "#"));
		SCSendEmailLogSchema tSCSendEmailLogSchema = new SCSendEmailLogSchema();
		QueryBuilder qb = new QueryBuilder(
				" where ServiceBussNo=? and Subject like '%待支付提醒%' ");
		qb.add(order.getOrderSn());
		SCSendEmailLogSet tSCSendEmailLogSet = tSCSendEmailLogSchema.query(qb);
		if (tSCSendEmailLogSet == null || tSCSendEmailLogSet.size() <= 0) {
			Map<String,Object> data = new HashMap<String,Object>();
			data.put("paySn", order.getPaySn());
			ActionUtil.sendMessage("wj00015", data);
		}

		return errList;

	}

	public static String dealNullToString(String cParam) {

		if (StringUtil.isEmpty(cParam)) {
			return "0";
		}
		return cParam;
	}

	// 活动校验,处理满减活动
	public List<String> checkPayActive(String tDiscActiveNumber,
			String tSubActiveNumber, String channelCode, String orderSn,
			String cActiveAmount) {

		List<String> errList = new ArrayList<String>();
		if (StringUtil.isEmpty(tDiscActiveNumber)) {
			return errList;
		}
		BigDecimal tActiveAmount = new BigDecimal(cActiveAmount);//
		QueryBuilder checkqb = new QueryBuilder(
				"  SELECT COUNT(1) activeCount,s2.type,s3.ActivityData,s2.accumulation,s3.StartAmount,s3.EndAmount FROM sdcouponactivityinfo s2,SdActivityRule s3,SdProductActivityLink s4 "
						+ " WHERE s2.activitysn = s3.ActivitySn AND s2.activitysn=? AND s2.type in('3','6') "
						+ " AND s3.id = ? AND s2.status = '3' AND s2.starttime<=NOW() AND s2.endtime>=NOW() AND s2.activitysn = s4.activitysn AND s4.ActivityChannel=? ");
		checkqb.add(tDiscActiveNumber);
		checkqb.add(tSubActiveNumber);
		checkqb.add(channelCode);
		DataTable dt = checkqb.executeDataTable();
		if (dt == null || dt.getInt(0, "activeCount") <= 0) {
			errList.add("UnderWriting000028");// 活动是否有效
			return errList;
		}
		//
		String accumulation = dt.getString(0, "accumulation");
		String type = dt.getString(0, "type");
		BigDecimal activeStartAmount = new BigDecimal(dt.getString(0,
				"StartAmount"));
		BigDecimal ActivityData = new BigDecimal(
				dt.getString(0, "ActivityData"));
		QueryBuilder amountqb = new QueryBuilder(
				" SELECT a.totalAmount FROM sdorders a WHERE a.orderSn=? ");
		amountqb.add(orderSn);
		BigDecimal orderAmt = new BigDecimal(amountqb.executeString());
		// 满减
		if ("3".equals(type)) {
			// 满减可累计
			if ("0".equals(accumulation)) {
				BigDecimal orderdiv = orderAmt.divide(activeStartAmount,
						BigDecimal.ROUND_HALF_DOWN);
				if (ActivityData.multiply(orderdiv).compareTo(tActiveAmount) != 0) {
					errList.add("UnderWriting000028");// 活动是否有效
					return errList;
				}
			} else if ("1".equals(accumulation)) {
				// 不可累计
				if (ActivityData.compareTo(tActiveAmount) != 0) {
					errList.add("UnderWriting000028");// 活动是否有效
					return errList;
				}
			}
		} else {
			if (orderAmt.multiply(ActivityData).divide(new BigDecimal("10"))
					.compareTo(tActiveAmount) != 0) {
				errList.add("UnderWriting000028");// 活动是否有效
				return errList;
			}
		}

		return errList;
	}

	// 优惠劵校验
	public List<String> checkPayCoupon(Map<String, Object> tPARAMETERS,
			String channelCode, String memberID, String orderSn) {

		String tDiscCouponNumber = String.valueOf(tPARAMETERS
				.get("DiscCouponNumber"));
		String tCouponAmount = dealNullToString(String.valueOf(tPARAMETERS
				.get("CouponAmount")));
		String tPointAmount = dealNullToString(String.valueOf(tPARAMETERS
				.get("PointAmount")));
		List<String> errList = new ArrayList<String>();
		if (StringUtil.isEmpty(tDiscCouponNumber)) {
			return errList;
		}
		QueryBuilder qb = new QueryBuilder(
				" SELECT a.couponSn,a.memberId,a.parValue,prop3,prop4 FROM CouponInfo a "
						+ " WHERE a.couponSn=? AND a.status='2' AND channelsn like '%"
						+ channelCode + "%' "
						+ " AND a.startTime <=NOW() AND a.endTime>=NOW()");
		qb.add(tDiscCouponNumber);
		DataTable dt = qb.executeDataTable();
		int couponLen = dt.getRowCount();
		// 是否查询到优惠劵纪录
		if (couponLen <= 0) {
			errList.add("MemberAction000018");// 优惠劵是否有效
			return errList;
		}
		// 判断优惠劵使用金额是否相等
		BigDecimal cponAmount = new BigDecimal(dt.getString(0, "parValue"));
		// 折扣优惠劵
		String disFlag = String.valueOf(dt.getString(0, "prop3"));
		if ("02".equals(disFlag)) {
			String dis = String.valueOf(dt.getString(0, "prop4"));
			BigDecimal discount = new BigDecimal(1).subtract(new BigDecimal(
					(StringUtil.isNotEmpty(dis) ? dis : "1"))
					.divide(new BigDecimal(10)));
			String tActualAmountPaid = String.valueOf(tPARAMETERS
					.get("PayAmount"));
			// String tActiveAmount =
			// dealNullToString(String.valueOf(tPARAMETERS.get("ActiveAmount")));
			BigDecimal amount = new BigDecimal(tActualAmountPaid)
					.add(new BigDecimal(tCouponAmount))
					.add(new BigDecimal(tPointAmount)).add(new BigDecimal("0"));
			cponAmount = (amount).multiply(discount).setScale(2,
					BigDecimal.ROUND_HALF_UP);
			if (new BigDecimal(tCouponAmount).compareTo(cponAmount) != 0) {
				errList.add("MemberAction000021");// 优惠劵是否有效
				return errList;
			}
		} else {
			if (new BigDecimal(tCouponAmount).compareTo(cponAmount) != 0) {
				errList.add("MemberAction000021");// 优惠劵是否有效
				return errList;
			}
		}
		// 是否已绑定其他会员
		String tmemId = dt.getString(0, "memberId");

		if (!"wap_cps_agent".equals(channelCode)) {
			if (!(tmemId).equals(memberID)) {
				errList.add("MemberAction000022");// 优惠劵是否有效
				return errList;
			}
		}
		if (!WapMemberUtil.checkCouponSn(tDiscCouponNumber, orderSn,
				channelCode, "")) {
			errList.add("UnderWriting000029");// 优惠劵是否有效
			return errList;
		}
		return errList;
	}

	// 积分校验
	public List<String> checkPayPoint(String tDiscActivePoint, String memberID,
			String tPointAmount) {

		List<String> errList = new ArrayList<String>();
		if (StringUtil.isEmpty(tDiscActivePoint)
				|| StringUtil.isEmpty(memberID)) {
			return errList;
		}
		QueryBuilder pointqb = new QueryBuilder(
				" select value from zdconfig where type='PointScalerUnit' ");
		String pointScalerUnit = pointqb.executeString();
		pointqb = new QueryBuilder(
				"   SELECT currentValidatePoint FROM member WHERE id=? ");
		pointqb.add(memberID);
		String memberPoint = pointqb.executeString();
		// BigDecimal usePoint = new BigDecimal(pointScalerUnit).multiply(new
		// BigDecimal(tDiscActivePoint));
		BigDecimal usePoint = new BigDecimal(tDiscActivePoint);
		if (new BigDecimal(tDiscActivePoint).divide(
				new BigDecimal(pointScalerUnit)).compareTo(
				new BigDecimal(tPointAmount)) != 0
				|| new BigDecimal(memberPoint).compareTo(usePoint) < 0) {
			errList.add("UnderWriting000031");// 积分是否有效
			return errList;
		}
		return errList;
	}

	@SuppressWarnings("unused")
	private static boolean isProductDownShelf(SDInformation sdinformation) {
		String productId = "";
		String productName = "";
		if (sdinformation != null) {
			productId = sdinformation.getProductId();
			productName = sdinformation.getProductName();
		}
		if (null == productId) {
			return false;
		}
		String sql = "select LimitCount,Occup,SectionAge,IsPublish from sdproduct where productid=?";
		String[] sqltemp = { productId };
		GetDBdata db = new GetDBdata();
		List<HashMap<String, String>> sdproduct;
		try {
			sdproduct = db.query(sql, sqltemp);
			if (sdproduct == null || sdproduct.size() != 1) {
				return false;
			}
			HashMap<String, String> product = sdproduct.get(0);
			String IsPublish = product.get("IsPublish");
			if (StringUtil.isEmpty(IsPublish) || "N".equals(IsPublish)) {
				return false;
			}
			String qixianSql = "select startDate, endDate from sdinformation where id =? ";
			String[] qixianSqltemp = { sdinformation.getId() };
			List<Map<String, Object>> qixain = db.queryObj(qixianSql,
					qixianSqltemp);
			if (qixain == null || qixain.size() != 1) {
				return false;
			}
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sfTime = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			Map<String, Object> qx = qixain.get(0);
			String qxfail = null;
			String qxeffective = null;
			if (qx.get("endDate") != null) {
				qxfail = qx.get("endDate").toString();
				qxeffective = qx.get("startDate").toString();
			}
			String tDate = "";
			if (productId.equals("204201002")) {
				tDate = com.sinosoft.sms.messageinterface.pubfun.PubFun
						.calSDate(sf.format(new Date()), 0, "D");
			} else {
				tDate = com.sinosoft.sms.messageinterface.pubfun.PubFun
						.calSDate(sf.format(new Date()), 1, "D");
			}
			Date nowDate = com.sinosoft.sms.messageinterface.pubfun.PubFun
					.StringToDate(tDate);
			if (nowDate.getTime() > sfTime.parse(qxfail).getTime()) {
				return false;
			}
			String LimitCount = product.get("LimitCount");
			logger.info("保险公司限购份数：{}", LimitCount);
			if (StringUtil.isNotEmpty(LimitCount) && !"0".equals(LimitCount)) {
				Set<SDInformationInsured> sdinsuredSet = sdinformation
						.getSdinformationinsuredSet();
				for (SDInformationInsured sdinsured : sdinsuredSet) {
					String insuredName = sdinsured.getRecognizeeName();
					String insuredIdType = sdinsured
							.getRecognizeeIdentityType();
					String insuredIdNo = sdinsured.getRecognizeeIdentityId();
					String backup_ = "select count(b.id) from sdinformation a, sdinformationinsured b,SDInformationRiskType c  "
							+ "where a.informationSn = b.informationSn and c.recognizeeSn = b.recognizeeSn "
							+ "and c.appStatus='1' and (('"
							+ qxfail
							+ "'>=a.startDate and a.startDate>='"
							+ qxeffective
							+ "') or ('"
							+ qxeffective
							+ "'<=a.endDate and a.endDate<='"
							+ qxfail
							+ "')) "
							+ "and b.recognizeeIdentityType=? and b.recognizeeIdentityId=? and a.productId=?";
					String[] backupTemp = { insuredIdType, insuredIdNo,
							productId };
					logger.info("保险公司限购份数（sql）：{}", backup_);
					String backup = db.getOneValue(backup_, backupTemp);
					logger.info("保险公司限购份数的值：{} -- {}", backup, LimitCount);
					if (StringUtil.isNotEmpty(backup)) {
						if (Integer.parseInt(backup) >= Integer
								.parseInt(LimitCount)) {
							return false;
						}
					}
				}
			}
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * 订单是否允许支付
	 * 
	 * @param order
	 * @return
	 */
	private static boolean isOutPeriod(SDOrder order, String productId) {
		try {
			GetDBdata db = new GetDBdata();
			String dateSql = "SELECT startDate,endDate FROM sdinformation WHERE orderSn = ? ";
			String periodSql = "SELECT startPeriod FROM productPeriod WHERE riskCode=(SELECT productid FROM sdinformation WHERE ordersn = ? )";
			String[] dateSqltemp = { order.getOrderSn() };
			List<Map<String, Object>> datePeriod = db.queryObj(dateSql,
					dateSqltemp);
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sfTime = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			Map<String, Object> period1 = datePeriod.get(0);
			Date periodfail = null;
			Date periodeffective = null;
			if (period1.get("endDate") != null) {
				periodfail = sfTime.parse(period1.get("endDate").toString());
				periodeffective = sfTime.parse(period1.get("startDate")
						.toString());
			}
			int stertPerid = 1;
			if (StringUtil.isNotEmpty(db.getOneValue(periodSql, dateSqltemp))) {
				stertPerid = Integer.parseInt(db.getOneValue(periodSql,
						dateSqltemp));
			}
			// 默认产品无次日生效，查询zdcode表中被要求次日生效的产品记录
			QueryBuilder qb = new QueryBuilder(
					"SELECT memo FROM zdcode WHERE codetype='ProductPeriod' AND codevalue=?");
			qb.add(productId);
			DataTable dt = qb.executeDataTable();
			if (dt.getRowCount() > 0) {
				stertPerid = Integer.parseInt(dt.get(0, 0).toString());
			}
			String tDate = com.sinosoft.sms.messageinterface.pubfun.PubFun
					.calSDate(sf.format(new Date()), stertPerid, "D");
			Date nowDate = com.sinosoft.sms.messageinterface.pubfun.PubFun
					.StringToDate(tDate);
			if ((periodeffective.getTime() < nowDate.getTime())
					&& (periodfail.getTime() >= periodeffective.getTime())) {
				logger.error("wap站：该订单不允许支付orderSn={}", order.getOrderSn());
				return false;
			}
		} catch (ParseException e) {
			logger.error("wap站：该订单不允许支付（orderSn=" + order.getOrderSn()
					+ "）;Msg:" + e.getMessage(), e);
			return false;
		} catch (Exception e) {
			logger.error("wap站：该订单不允许支付（orderSn=" + order.getOrderSn()
					+ "）;Msg:" + e.getMessage(), e);
			return false;
		}
		return true;

	};

	/**
	 * 产品保费重算方法---非导入类
	 */
	public static BigDecimal relCalPrem(String[] baseInformations,
			List<OrderRiskAppFactor> riskAppFactior,
			List<OrderDutyFactor> dutyFactor, SDInformation info,
			List<SDInformationInsured> insuredlist,
			OrderConfigNewService orderConfigNewService,
			SDOrderService sdorderService) {
		try {

			BigDecimal totalAmnt = new BigDecimal("0.00");// 订单总金额
			SimpleDateFormat sdf_1 = new SimpleDateFormat("yyyy-MM-dd");
			String productId = info.getProductId();
			String cPlanCode = info.getPlanCode();
			Double totlePrem = 0.0;// 总保费
			Double countPrice = 0.0;// 折扣后保费
			// 处理多被保人
			for (SDInformationInsured insured : insuredlist) {
				List<OrderDutyFactor> dutyFactorLast = new ArrayList<OrderDutyFactor>();
				// 处理投保要素
				for (int i = 0; i < riskAppFactior.size(); i++) {
					// OrderRiskAppFactor orderRiskAppFactor =
					// riskAppFactior.get(i);
					if ("TextAge".equals(riskAppFactior.get(i).getFactorType())) {
						Object factorValueTemp = insured
								.getRecognizeeBirthday();
						if ("".equals(factorValueTemp)
								|| factorValueTemp == null) {
							FEMRiskFactorList femr = riskAppFactior.get(i)
									.getFactorValue().get(0);
							List<FEMRiskFactorList> factorValue = new ArrayList<FEMRiskFactorList>();
							FEMRiskFactorList riskFactor = new FEMRiskFactorList();
							if ("".equals(String.valueOf(factorValueTemp))) {
								if (femr != null
										&& femr.getFactorValue() != null) {
									riskFactor
											.setFactorValue(orderConfigNewService.getBrithdayByFactor(
													sdf_1.format(info
															.getStartDate()),
													femr.getFactorValue()));
								} else {
									riskFactor.setFactorValue("1991-01-01");
								}
							} else {
								riskFactor.setFactorValue(factorValueTemp
										.toString());
							}
							riskFactor.setAppFactorCode(riskAppFactior.get(i)
									.getAppFactorCode());
							riskFactor.setFactorType(riskAppFactior.get(i)
									.getFactorType());
							riskFactor.setIsPremCalFacotor(riskAppFactior
									.get(i).getIsPremCalFacotor());
							factorValue.add(riskFactor);
							riskAppFactior.get(i).setFactorValue(factorValue);
						} else {
							riskAppFactior.get(i).getFactorValue().get(0);
							List<FEMRiskFactorList> factorValue = new ArrayList<FEMRiskFactorList>();
							FEMRiskFactorList riskFactor = new FEMRiskFactorList();
							if (factorValueTemp != null)
								riskFactor.setFactorValue(factorValueTemp
										.toString());
							riskFactor.setAppFactorCode(riskAppFactior.get(i)
									.getAppFactorCode());
							riskFactor.setFactorType(riskAppFactior.get(i)
									.getFactorType());
							riskFactor.setIsPremCalFacotor(riskAppFactior
									.get(i).getIsPremCalFacotor());
							factorValue.add(riskFactor);
							riskAppFactior.get(i).setFactorValue(factorValue);
						}
					} else {
						Object factorValueTemp = "";
						if ("Plan"
								.equals(riskAppFactior.get(i).getFactorType())) {
							factorValueTemp = info.getPlanCode();
						} else if ("AppLevel".equals(riskAppFactior.get(i)
								.getFactorType())) {
							factorValueTemp = info.getAppLevel();
						} else if ("AppType".equals(riskAppFactior.get(i)
								.getFactorType())) {
							factorValueTemp = info.getAppType();
						} else if ("FeeYear".equals(riskAppFactior.get(i)
								.getFactorType())) {
							factorValueTemp = info.getChargeYear();
						} else if ("Period".equals(riskAppFactior.get(i)
								.getFactorType())) {
							factorValueTemp = info.getEnsure();
						}

						riskAppFactior.get(i).getFactorValue().get(0);
						List<FEMRiskFactorList> factorValue = new ArrayList<FEMRiskFactorList>();
						FEMRiskFactorList riskFactor = new FEMRiskFactorList();
						if (factorValueTemp != null)
							riskFactor.setFactorValue(factorValueTemp
									.toString());
						riskFactor.setAppFactorCode(riskAppFactior.get(i)
								.getAppFactorCode());
						riskFactor.setFactorType(riskAppFactior.get(i)
								.getFactorType());
						riskFactor.setIsPremCalFacotor(riskAppFactior.get(i)
								.getIsPremCalFacotor());
						factorValue.add(riskFactor);
						riskAppFactior.get(i).setFactorValue(factorValue);
					}
				}
				// 处理产品责任
				List<OrderDutyFactor> tdutyFactor = dutyFactor;
				for (OrderDutyFactor orderDutyFactor : tdutyFactor) {
					List<FEMDutyAmntPremList> fdAmntPremList = new ArrayList<FEMDutyAmntPremList>();
					if (cPlanCode != null && !"".equals(cPlanCode)) {
						for (FEMDutyAmntPremList femd : orderDutyFactor
								.getFdAmntPremList()) {
							if (cPlanCode.equals(femd.getAppFactorValue())) {
								fdAmntPremList.add(femd);
								orderDutyFactor
										.setFdAmntPremList(fdAmntPremList);
								dutyFactorLast.add(orderDutyFactor);
							}
						}
					} else {
						dutyFactorLast.add(orderDutyFactor);
					}

				}

				if (dutyFactorLast.size() == 0) {
					dutyFactorLast = null;
				}
				Map<String, Object> paramter = new HashMap<String, Object>();
				paramter.put("baseInformation", baseInformations);
				paramter.put("riskAppFactor", riskAppFactior);
				paramter.put("dutyFactor", dutyFactorLast);

				if (info.getStartDate() != null) {
					// 保费试算根据保险公司判断是起保日期还是当前日期
					String startdate = sdf_1.format(info.getStartDate());
					if (productId.startsWith("1087")
							|| productId.startsWith("1001")
							|| productId.startsWith("1004")
							|| productId.startsWith("2043")) {
						startdate = PubFun.getCurrentDate();
					}
					paramter.put("effective", startdate);
				}
				Map<String, Object> mapPrem = sdorderService
						.getProductPremDutyAmounts(paramter);

				System.err.println("*****************is|" + mapPrem);
				totlePrem = totlePrem
						+ Double.parseDouble(mapPrem.get("totlePrem")
								.toString());// 总保费
				// 把每个被保人的保费传到前台显示
				countPrice = countPrice
						+ Double.parseDouble(mapPrem.get("countPrice")
								.toString());// 折扣后保费
			}
			if (countPrice != 0.0) {
				totalAmnt = new BigDecimal(countPrice);
			}
			return totalAmnt.setScale(2, BigDecimal.ROUND_HALF_UP);

		} catch (Exception e) {
			logger.error("wap站：保费计算失败:" + e.getMessage(), e);
			return new BigDecimal("0.00");
		}

	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> wapSaveOrderInfo(
			Map<String, Object> tPARAMETERS, Map<String, Object> mServiceMap,
			String USER) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> orderMap = new HashMap<String, Object>();
		List<String> errorinfos = new ArrayList<String>();
		errorinfos = WapUnderWritingUtil.underWriting(tPARAMETERS, mServiceMap);

		if (errorinfos.size() > 0) {
			return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0014",
					resultMap);
		}
		// 解析入参
		try {

			Map<String, Object> tPolicyInfo = BaseWapShopAction
					.getJsonToListMap("PolicyInfo", tPARAMETERS).get(0);
			Map<String, Object> tHolderInfo = BaseWapShopAction
					.getJsonToListMap("HolderInfo", tPARAMETERS).get(0);
			List<Map<String, Object>> tInsuredInfos = BaseWapShopAction
					.getJsonToListMap("InsuredInfos", tPARAMETERS);
			List<Map<String, Object>> tCPSInfoList = BaseWapShopAction
					.getJsonToListMap("CPSInfo", tPARAMETERS);
			List<Map<String, Object>> tHealthyInfoList = BaseWapShopAction
					.getJsonToListMap("HealthyInfo", tPARAMETERS);
			List<Map<String, Object>> tHourseInfoList = BaseWapShopAction
					.getJsonToListMap("HourseInfo", tPARAMETERS);

			Map<String, Object> tCPSInfo = null;
			if (tCPSInfoList != null && tCPSInfoList.size() >= 1) {
				tCPSInfo = tCPSInfoList.get(0);
			}
			SDOrderService sdorderService = (SDOrderService) mServiceMap
					.get("SDOrderService");
			TradeInformationService tTradeInformationService = (TradeInformationService) mServiceMap
					.get("TradeInformationService");
			if (tHolderInfo == null || tInsuredInfos == null
					|| tPolicyInfo == null) {
				errorinfos.add("SaveOrder001");
				return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0014",
						null);
			}
			String productId = String.valueOf(tPolicyInfo.get("ProdCode"));
			String tOrderNumber = String.valueOf(BaseWapShopAction
					.getJsonToListMap("OrderNumber", tPARAMETERS).get(0)
					.get("OrderNumber"));// 订单编号
			String torderSn = "";
			LinkedHashMap<Object, String> mLMap = new LinkedHashMap<Object, String>();
			if (tOrderNumber != null && !"".equals(tOrderNumber)) {
				// 判断订单号是否合法
				if (!checkOrderNumber(tOrderNumber)) {
					errorinfos.add("SaveOrder003");
					return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0014",
							null);
				}
				deleteAll(tOrderNumber, sdorderService,
						tTradeInformationService, mLMap);
				torderSn = tOrderNumber;
			} else {
				// 生成订单编号
				torderSn = PubFun.GetWapSn("WPOrderSn");
			}
			// 获取产品信息
			Map<String, Object> paramter = new HashMap<String, Object>();
			paramter = sdorderService.getProductInformation(productId, "N", "");// 产品ID
			List<OrderRiskAppFactor> riskAppFactior = (List<OrderRiskAppFactor>) paramter
					.get("riskAppFactor");
			List<OrderDutyFactor> dutyFactor = (List<OrderDutyFactor>) paramter
					.get("dutyFactor");
			// 获取订单表信息 sdorders
			SDOrder sdorder = sdorderService.getOrderByOrderSn(torderSn);
			if (sdorder == null) {
				sdorder = new SDOrder();
			}
			sdorder = getSDOrder(sdorder, tPolicyInfo, paramter, tCPSInfo);// 改造后订单，add
																			// by
			// 获取订单明细表信息 sdinformation
			SDInformation sdinformation = getSDInformation(tPolicyInfo,
					paramter);// 改造后订单详细 表（产品表），add by cuishg
			// 设置订单号
			sdorder.setOrderSn(torderSn);
			// 设置会员编码
			MemberService memberService = (MemberService) mServiceMap
					.get("MemberService");

			SDRelationAppntService sdRelationAppntService = (SDRelationAppntService) mServiceMap
					.get("SDRelationAppntService");
			SDRelationRecognizeeService sdRelationRecognizeeService = (SDRelationRecognizeeService) mServiceMap
					.get("SDRelationRecognizeeService");
			if (USER != null && !"".equals(USER)) {
				Member member = memberService
						.getMemberByLoginNameNoBinding(USER);
				if (member != null && !"".equals(member)) {
					sdorder.setMemberId(member.getId());
				}
			}
			sdinformation.setOrderSn(torderSn);
			// 生成订单明细编号
			String tInformationSn = PubFun.GetWapSn("WPInfoSn");
			sdinformation.setInformationSn(tInformationSn);
			Set<SDInformation> tSDInformationSet = new HashSet<SDInformation>();
			tSDInformationSet.add(sdinformation);
			sdorder.setSdinformationSet(tSDInformationSet);
			sdinformation.setSdorder(sdorder);
			// 获取投保人表信息 sdInformationAppnt
			SDInformationAppnt sdInformationAppnt = getSDInformationAppnt(
					tHolderInfo, paramter, mServiceMap,sdinformation.getProductId());
			sdInformationAppnt.setInformationSn(tInformationSn);
			// 保存投保人时将information的投保人信息进行更新
			Set<SDInformationAppnt> appSet = new HashSet<SDInformationAppnt>();
			appSet.add(sdInformationAppnt);
			sdinformation.setSdinformationappntSet(appSet);
			sdInformationAppnt.setSdinformaiton(sdinformation);
			// 获取订单项1
			SDOrderItem sdorderitem = getSDOrderItem(sdinformation, sdorder);
			// 获取被保人表信息 sdInformationAppnt
			List<SDInformationInsured> minsuredList = getSDInformationInsured(
					tInsuredInfos, mServiceMap, tPolicyInfo, sdInformationAppnt);
			List<SDOrderItemOth> sdorderitemothList = new ArrayList<SDOrderItemOth>();
			List<SDInformationRiskType> mSDInformationRiskTypeList = new ArrayList<SDInformationRiskType>();
			List<SDInformationInsuredElements> melementslist = new ArrayList<SDInformationInsuredElements>();
			// 获取责任信息
			WapShoppingUtil tWapShoppingUtil = new WapShoppingUtil();
			List<SDInformationDuty> mdutylist = tWapShoppingUtil
					.getInformationDuty(sdorder, sdinformation, dutyFactor);
			for (int i = 0; i < minsuredList.size(); i++) {
				SDInformationInsured sdInformationInsured = minsuredList.get(i);
				sdInformationInsured.setOrderSn(torderSn);
				sdInformationInsured.setInsuredSn(torderSn + "_" + (i + 1));
				sdInformationInsured.setRecognizeeKey(torderSn + "_" + (i + 1));
				sdInformationInsured.setInformationSn(tInformationSn);
				sdInformationInsured.setSdinformation(sdinformation);
				SDOrderItemOth sdorderitemoth = new SDOrderItemOth();// 获取订单项2
				sdorderitemoth.setOrderSn(torderSn);
				sdorderitemoth.setRecognizeeSn(sdInformationInsured
						.getRecognizeeSn());
				sdorderitemoth.setOrderitemSn(PubFun.GetWapSn("WPItemOthSn"));
				if ("2023".equals(sdinformation.getInsuranceCompany())) {
					String outcode = PubFun.getHTSN(sdinformation
							.getProductOutCode());
					sdorderitemoth.setTpySn(outcode);// 存入华泰特殊编号
				}
				sdorderitemoth.setSdinformationinsured(sdInformationInsured);
				sdorderitemothList.add(sdorderitemoth);
				// 获取保单信息
				String tproductPrice = String.valueOf(tInsuredInfos.get(i).get(
						"ProductPrice"));
				if (StringUtil.isEmpty(tproductPrice)) {
					tproductPrice = String.valueOf(tPolicyInfo.get("Premium"));
				}
				sdInformationInsured.setRecognizeeTotalPrem(tproductPrice);
				SDInformationRiskType sdinformationRiskTypes = tWapShoppingUtil
						.getSDInformationRiskType(sdorder, sdinformation,
								sdInformationAppnt, sdInformationInsured);
				sdInformationInsured
						.setSdinformaitonrisktype(sdinformationRiskTypes);
				mSDInformationRiskTypeList.add(sdinformationRiskTypes);
				// 获取投保要素信息
				Set<SDInformationInsuredElements> sdinforinselementsSet = getInfomationInsuredElements(
						tPolicyInfo, sdorder, sdinformation,
						sdInformationInsured, riskAppFactior);
				for (SDInformationInsuredElements element : sdinforinselementsSet) {
					melementslist.add(element);
				}
				sdInformationInsured
						.setSdinforinselementsSet(sdinforinselementsSet);
			}

			// 处理健康告知信息
			List<SDInsuredHealth> tSDInsuredHealth = getHealthyInfo(
					tHealthyInfoList, mServiceMap, minsuredList, tPolicyInfo);
			// 得到家财险信息
			List<SDInformationProperty> tSDInformationPropertyList = getPropertyInfo(
					tHourseInfoList, minsuredList);
			// 获取交易表信息
			TradeInformation tTradeInformation = tTradeInformationService
					.getTradeInformationByOrdSn(torderSn);
			if (tTradeInformation == null) {
				tTradeInformation = new TradeInformation();
			}
			tTradeInformation.setOrdID(torderSn);
			tTradeInformation.setModifyDate(new Date());
			// 保存订单表
			List<SDOrder> orderList = new ArrayList<SDOrder>();
			orderList.add(sdorder);

			// 处理常用投、被保人信息
			SDRelationAppnt reAppnt;
			String memberID = sdorder.getMemberId();
			if (StringUtil.isNotEmpty(memberID)) {
				Member loginMember = memberService.get(memberID);
				if (loginMember != null) {
					OrderConfigNewAction tOrderConfigNewAction = new OrderConfigNewAction();
					// 根据productID、memberId、appntName查询关联投保人信息
					reAppnt = sdRelationAppntService.getSDRelationAppntInfo(
							sdinformation.getInsuranceCompany(), productId,
							loginMember.getId(),
							sdInformationAppnt.getApplicantName());
					int insuredCount = loginMember.getSdrelationappntSet()
							.size();
					if (reAppnt == null) {
						if (insuredCount <= 9) {
							reAppnt = new SDRelationAppnt();
							reAppnt = tOrderConfigNewAction
									.cotySDRelationAppnt(loginMember, reAppnt,
											sdInformationAppnt, productId,
											sdinformation.getInsuranceCompany());
						}
					} else {
						reAppnt = tOrderConfigNewAction.cotySDRelationAppnt(
								loginMember, reAppnt, sdInformationAppnt,
								productId, sdinformation.getInsuranceCompany());
					}
					if (reAppnt != null) {
						List<SDRelationAppnt> appList = new ArrayList<SDRelationAppnt>();
						appList.add(reAppnt);
						mLMap.put(appList, "insert&update");
					}
					List<SDRelationRecognizee> insuredList = sdrelationrecognizeeSave(
							minsuredList, loginMember,
							sdRelationRecognizeeService, sdinformation);
					// 常用被保人
					if (insuredList != null && insuredList.size() >= 1) {
						mLMap.put(insuredList, "insert&update");
					}
				}
			}
			mLMap.put(orderList, "insert&update");
			// 保存订单详细表
			mLMap.put(sdinformation, "insert");
			// 保存投保人信息
			mLMap.put(sdInformationAppnt, "insert");
			// 保存被保人信息
			mLMap.put(minsuredList, "insert");
			// 保存保单信息
			mLMap.put(mSDInformationRiskTypeList, "insert");
			// 保存订单项1
			mLMap.put(sdorderitem, "insert");
			// 保存订单项2
			mLMap.put(sdorderitemothList, "insert");
			// 保存投保元素
			mLMap.put(melementslist, "insert");
			// 保存责任信息
			mLMap.put(mdutylist, "insert");
			// 健康告知
			if (tSDInsuredHealth != null && tSDInsuredHealth.size() >= 1) {
				// 保存责任信息
				mLMap.put(tSDInsuredHealth, "insert");
			}
			// 家财险信息
			if (tSDInformationPropertyList != null
					&& tSDInformationPropertyList.size() >= 1) {
				mLMap.put(tSDInformationPropertyList, "insert");
			}
			// 保存交易表
			List<TradeInformation> tradeList = new ArrayList<TradeInformation>();
			tradeList.add(tTradeInformation);
			mLMap.put(tradeList, "insert&update");
			resultMap.put("USER", USER);
			resultMap.put("REQUESTTYPE", "KXBJRT0014");
			resultMap
					.put("TotalPrem", String.valueOf(sdorder.getTotalAmount()));
			orderMap.put("OrderNumber", torderSn);
			orderMap.put("ActualAmountPaid",
					String.valueOf(tPolicyInfo.get("ActualAmountPaid")));// 原路返回折扣活动优惠后金额
			DealDataService mDealDataService = (DealDataService) mServiceMap
					.get("DealDataService");
			if (!mDealDataService.saveAll(mLMap)) {
				resultMap.put("STATYS", "false");
				orderMap.put("OrderStatus", "False");
				errorinfos.add("SaveOrder002");

			} else {
				// 处理cps数据
				if (tCPSInfo != null) {
					String cpsUserID = String
							.valueOf(tCPSInfo.get("CPSUserID"));
					if (StringUtil.isNotEmpty(cpsUserID)
							&& StringUtil.isEmpty(tOrderNumber)) {
						// 用户ID;来源：1.图片广告 2.链接转换 3.产品直连; 订单号;产品名称;价格
						OrderConfigNewAction.recordCPS(cpsUserID, "", "",
								String.valueOf(tCPSInfo.get("CPSUserSource")),
								sdorder.getOrderSn(),
								sdinformation.getProductName(),
								String.valueOf(sdorder.getTotalAmount()));
					}
				}
				resultMap.put("STATYS", "true");
				orderMap.put("OrderStatus", "True");
			}
			resultMap.put("RESULTS", orderMap);

		} catch (Exception e) {
			errorinfos.add("SaveOrder002");
			logger.error("错误编码:SaveOrder002;错误信息：" + e.getMessage(), e);
			return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0014",
					orderMap);
		}
		return resultMap;
	}

	/**
	 * 被保人快速录入--保存方法:继承自OrderConfigNewAction
	 * 
	 * @param appnt
	 * @return
	 */
	public static List<SDRelationRecognizee> sdrelationrecognizeeSave(
			List<SDInformationInsured> insuredList, Member loginMember,
			SDRelationRecognizeeService sdRelationRecognizeeService,
			SDInformation info) {
		List<SDRelationRecognizee> sdreInsuredList = new ArrayList<SDRelationRecognizee>();
		SDRelationRecognizee reRecognizee;
		Map<String, Integer> insuredNameMap = new HashMap<String, Integer>();
		if (loginMember != null) {
			for (SDInformationInsured insured : insuredList) {
				// 根据productID、memberId、appntName查询关联投保人信息
				if (StringUtil.isEmpty(insured.getRecognizeeName())) {
					continue;
				}
				String productId = info.getProductId();
				String comCode = info.getInsuranceCompany();
				reRecognizee = sdRelationRecognizeeService
						.getSDRelationRecognizeeInfo(comCode, productId,
								loginMember.getId(),
								insured.getRecognizeeName());
				if (reRecognizee == null) {
					int insuredCount = loginMember.getSdrelationrecognizeeSet()
							.size() + sdreInsuredList.size();
					if (insuredCount >= 10)
						continue;
					reRecognizee = new SDRelationRecognizee();
					insuredCount = insuredCount + 1;
				}
				reRecognizee.setProductId(productId);
				reRecognizee.setRemark(comCode);// 保险公司
				reRecognizee.setRecognizeeName(insured.getRecognizeeName());
				reRecognizee.setRecognizeeEnName(insured.getRecognizeeEnName());
				reRecognizee.setRecognizeeIdentityType(insured
						.getRecognizeeIdentityType());
				reRecognizee.setRecognizeeIdentityId(insured
						.getRecognizeeIdentityId());
				reRecognizee.setRecognizeeIdentityTypeName(insured
						.getRecognizeeIdentityTypeName());
				reRecognizee.setRecognizeeSex(insured.getRecognizeeSex());
				reRecognizee.setRecognizeeSexName(insured
						.getRecognizeeSexName());
				reRecognizee.setRecognizeeBirthday(insured
						.getRecognizeeBirthday());
				if (StringUtil.isNotEmpty(insured.getRecognizeeMail())) {
					reRecognizee.setRecognizeeMail(insured.getRecognizeeMail());
				}
				reRecognizee.setRecognizeeArea1(insured.getRecognizeeArea1());
				reRecognizee.setRecognizeeArea2(insured.getRecognizeeArea2());
				reRecognizee.setRecognizeeArea3(insured.getRecognizeeArea3());
				reRecognizee.setRecognizeeAddress(insured
						.getRecognizeeAddress());
				reRecognizee.setRecognizeeZipCode(insured
						.getRecognizeeZipCode());
				if (StringUtil.isNotEmpty(insured.getRecognizeeMobile())) {
					reRecognizee.setRecognizeeMobile(insured
							.getRecognizeeMobile());
				}
				reRecognizee.setRecognizeeTel(insured.getRecognizeeTel());
				reRecognizee.setRecognizeeOccupation1(insured
						.getRecognizeeOccupation1());
				reRecognizee.setRecognizeeOccupation2(insured
						.getRecognizeeOccupation2());
				reRecognizee.setRecognizeeOccupation3(insured
						.getRecognizeeOccupation3());
				reRecognizee.setRecognizeeStartID(insured
						.getRecognizeeStartID());
				reRecognizee.setRecognizeeEndID(insured.getRecognizeeEndID());
				reRecognizee.setmMember(loginMember);
				reRecognizee.setMemberId(loginMember.getId());
				try {
					reRecognizee.setModifyDate(new Date());
				} catch (Exception e) {
					logger.error("sdrelationrecognizeeSave,日期转型错误："+ e.getMessage(),e);
				}
				if (insuredNameMap != null && insuredNameMap.size() >= 1) {
					if (insuredNameMap.get(reRecognizee.getRecognizeeName()) != null
							&& !"".equals(insuredNameMap.get(reRecognizee
									.getRecognizeeName()))) {
						if (insuredNameMap
								.get(reRecognizee.getRecognizeeName()) <= 0) {
							insuredNameMap.put(
									reRecognizee.getRecognizeeName(), 1);
							if (reRecognizee != null) {
								sdreInsuredList.add(reRecognizee);
							}
						} else {
							insuredNameMap.put(
									reRecognizee.getRecognizeeName(), 1);
						}
					} else {
						insuredNameMap.put(reRecognizee.getRecognizeeName(), 1);
						if (reRecognizee != null) {
							sdreInsuredList.add(reRecognizee);
						}
					}
				} else {
					insuredNameMap.put(reRecognizee.getRecognizeeName(), 1);
					if (reRecognizee != null) {
						sdreInsuredList.add(reRecognizee);
					}
				}
			}
		}

		return sdreInsuredList;
	}

	/**
	 * 删除除订单所有信息
	 * 
	 * @param orderSn
	 * @return
	 */
	public static boolean deleteAll(String orderSn) {

		Transaction trans = new Transaction();
		StringBuffer querySQL = new StringBuffer(
				" SELECT informationSn FROM sdinformation WHERE orderSn = ? ");
		QueryBuilder tQueryBuilder = new QueryBuilder(querySQL.toString());
		tQueryBuilder.add(orderSn);
		Object informationSn = tQueryBuilder.executeOneValue();
		if (informationSn != null && !"".equals(informationSn)) {
			informationSn = tQueryBuilder.executeOneValue().toString();
		} else {
			logger.error("没有查到该订单相关的详细信息或者订单号不存在，orderSn :{}", orderSn);
			return true;
		}
		// 订单表
		trans.add(new QueryBuilder("DELETE FROM sdorders WHERE orderSn = ?",
				orderSn));
		// 交易表
		trans.add(new QueryBuilder(
				"DELETE FROM tradeinformation WHERE ordid = ?", orderSn));
		// 订单项表
		trans.add(new QueryBuilder("DELETE FROM sdorderitem WHERE orderSn = ?",
				orderSn));
		// 订单项表2
		trans.add(new QueryBuilder(
				"DELETE FROM sdorderitemoth WHERE orderSn = ?", orderSn));
		// 订单详细信息表
		trans.add(new QueryBuilder(
				"DELETE FROM sdinformation WHERE informationSn = ?",
				informationSn));
		// 投保人表
		trans.add(new QueryBuilder(
				"DELETE FROM sdinformationappnt WHERE informationSn = ?",
				informationSn));
		// 被保人表
		trans.add(new QueryBuilder(
				"DELETE FROM sdinformationinsured WHERE informationSn = ?",
				informationSn));
		// 受益人表
		trans.add(new QueryBuilder(
				"DELETE FROM sdinformationbnf WHERE informationSn = ?",
				informationSn));
		// 责任表
		trans.add(new QueryBuilder(
				"DELETE FROM sdinformationduty WHERE informationSn = ?",
				informationSn));
		// 投保要素表
		trans.add(new QueryBuilder(
				"DELETE FROM sdinformationinsuredelements WHERE informationSn = ?",
				informationSn));
		// 健康告知表
		trans.add(new QueryBuilder(
				"DELETE FROM sdinsuredhealth WHERE informationSn = ?",
				informationSn));
		// 保单信息表
		trans.add(new QueryBuilder(
				"DELETE FROM sdinformationrisktype WHERE informationSn = ?",
				informationSn));
		if (trans.commit()) {
			logger.info("订单信息删除操作成功 orderSn:{}", orderSn);
		} else {
			logger.error("订单信息删除操作失败，请检查订单信心！  orderSn:{}", orderSn);
			return false;
		}
		return true;
	}

	/**
	 * 删除除订单所有信息--返回Map
	 * 
	 * @param orderSn
	 * @return
	 */
	public static void deleteAll(String orderSn, SDOrderService sdorderService,
			TradeInformationService tTradeInformationService,
			LinkedHashMap<Object, String> mLMap) {

		SDOrder order = sdorderService.getOrderByOrderSn(orderSn);

		Set<SDInformation> infoSet = order.getSdinformationSet();
		SDInformation info = infoSet.iterator().next();

		SDOrderItem item = order.getSdorderitem();

		Set<SDInformationInsured> insSet = info.getSdinformationinsuredSet();

		List<SDInformationInsured> insList = new ArrayList<SDInformationInsured>();

		List<SDInformationRiskType> riskList = new ArrayList<SDInformationRiskType>();

		List<SDOrderItemOth> itothList = new ArrayList<SDOrderItemOth>();

		List<SDInsuredHealth> healthList = new ArrayList<SDInsuredHealth>();

		List<SDInformationDuty> dutyList = new ArrayList<SDInformationDuty>();

		List<SDInformationInsuredElements> eleList = new ArrayList<SDInformationInsuredElements>();

		for (SDInformationInsured ins : insSet) {
			insList.add(ins);
			riskList.add(ins.getSdinformaitonrisktype());
			for (SDOrderItemOth oth : ins.getSdorderitemothSet()) {
				itothList.add(oth);
			}
			for (SDInsuredHealth health : ins.getSdinsuredHealthSet()) {
				healthList.add(health);
			}
			for (SDInformationInsuredElements ele : ins
					.getSdinforinselementsSet()) {
				eleList.add(ele);
			}
		}
		SDInformationAppnt app = info.getSdinformationappntSet().iterator()
				.next();

		Set<SDInformationDuty> dutySet = info.getSdinformationDutySet();

		for (SDInformationDuty duty : dutySet) {
			dutyList.add(duty);
		}
		if (healthList.size() >= 1) {
			mLMap.put(healthList, "delete");
		}
		mLMap.put(eleList, "delete");
		mLMap.put(itothList, "delete");
		mLMap.put(insList, "delete");
		mLMap.put(riskList, "delete");
		mLMap.put(dutyList, "delete");
		mLMap.put(app, "delete");
		mLMap.put(info, "delete");
		mLMap.put(item, "delete");
		// mLMap.put(order, "delete");
		// mLMap.put(tradeinfo, "delete");

	}

	/**
	 * 判断订单是否合法
	 * 
	 * @param orderSn
	 * @return
	 */
	public static boolean checkOrderNumber(String orderSn) {

		if (!orderSn.toUpperCase().startsWith("WP")) {
			return false;
		}
		StringBuffer querySQL = new StringBuffer(
				" SELECT a.OrderStatus,c.AppStatus FROM sdorders a,sdinformation b,sdinformationrisktype c WHERE a.orderSn = b.orderSn AND b.informationSn=c.informationSn AND a.orderSn=? ");
		QueryBuilder tQueryBuilder = new QueryBuilder(querySQL.toString());
		tQueryBuilder.add(orderSn);
		DataTable dt = tQueryBuilder.executeDataTable();
		if (dt == null || dt.getRowCount() <= 0) {
			return false;
		}
		if ("7".equals(String.valueOf(dt.get(0, "OrderStatus")))
				|| "2".equals(String.valueOf(dt.get(0, "OrderStatus")))
				|| "3".equals(String.valueOf(dt.get(0, "OrderStatus")))
				|| "8".equals(String.valueOf(dt.get(0, "OrderStatus")))
				|| "9".equals(String.valueOf(dt.get(0, "OrderStatus")))
				|| "10".equals(String.valueOf(dt.get(0, "OrderStatus")))
				|| "1".equals(String.valueOf(dt.get(0, "AppStatus")))) {
			return false;
		}
		return true;
	}

	/**
	 * 获取订单信息
	 * 
	 * @param oPolicyInfo
	 * @param paramter
	 * @return
	 */
	public static SDOrder getSDOrder(SDOrder sdorder,
			Map<String, Object> oPolicyInfo, Map<String, Object> paramter,
			Map<String, Object> oCpsInfo) {
		// SDOrder sdorder = new SDOrder();// 改造后订单，add by cuishg
		String[] BaseInformation = new String[17];
		BaseInformation = (String[]) paramter.get("baseInformation");// 产品基础数据
		BigDecimal tTotalAmount = new BigDecimal(String.valueOf(oPolicyInfo
				.get("Premium")));
		BigDecimal tProductTotalPrice = new BigDecimal(
				String.valueOf(oPolicyInfo.get("PrePremium").toString()));
		sdorder.setProductTotalPrice(tProductTotalPrice);// 商品总价打折前
		sdorder.setTotalAmount(tTotalAmount);// 保费即打折后商品价格
		sdorder.setDiscountRates(BaseInformation[10].toString());// 订单折扣费率
		sdorder.setDiscountAmount("");// 订单优惠金额
		// TODO 处理实际支付金额，活动打折
		String tActualAmountPaid = String.valueOf(oPolicyInfo
				.get("ActualAmountPaid"));
		String from = String.valueOf(oPolicyInfo.get("From"));

		// 11 代表代理人 12 代表合作商户
		String CPSSource = "";
		String cpsUserID = "";
		if (oCpsInfo != null) {
			CPSSource = String.valueOf(oCpsInfo.get("CPSUserSource"));
			cpsUserID = String.valueOf(oCpsInfo.get("CPSUserID"));
		}

		String channelSn = "wap_ht";
		if ("wx".equals(from)) {
			channelSn = "wap_wx";// wap站-微信
		}
		// 目前合作商户存在wap连接转换
		// 代理人存在微信公共帐号
		if (StringUtil.isNotEmpty(cpsUserID)) {
			if (StringUtil.isNotEmpty(CPSSource) && "11".equals(CPSSource)) {
				channelSn = "wap_cps_agent";

			} else {
				channelSn = "wap_cps";

			}
		}

		if (StringUtil.isNotEmpty(cpsUserID) && "wx".equals(from)) {
			channelSn = "cps_wx";
		}
		sdorder.setChannelsn(channelSn);
		sdorder.setPayPrice(tActualAmountPaid);// wap站，暂存活动打折后金额
		sdorder.setOrderStatus(SDOrderStatus.prepay);
		sdorder.setPayStatus(SDPayStatus.unpaid);
		sdorder.setProductNum(1);
		sdorder.setModifyDate(new Date());
		return sdorder;

	}

	/**
	 * 获取订单明细信息
	 * 
	 * @param oPolicyInfo
	 * @param paramter
	 * @return
	 */
	public static SDInformation getSDInformation(
			Map<String, Object> oPolicyInfo, Map<String, Object> paramter) {
		SDInformation sdinformation = new SDInformation();
		String[] BaseInformation = new String[17];
		BaseInformation = (String[]) paramter.get("baseInformation");// 产品基础数据
		String tProdCode = oPolicyInfo.get("ProdCode").toString();
		sdinformation.setProductPrice(String.valueOf(oPolicyInfo
				.get("PrePremium")));// 商品原价
		sdinformation.setProductDiscountPrice(String.valueOf(oPolicyInfo
				.get("Premium")));// 产品折扣价
		sdinformation.setInsuranceCompany(String.valueOf(oPolicyInfo
				.get("CompanyID")));
		sdinformation.setProductId(tProdCode);
		sdinformation.setProductName(BaseInformation[1]);
		sdinformation.setProductQuantity("1");
		SimpleDateFormat sdf_2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date startdate = sdf_2.parse(String.valueOf(oPolicyInfo
					.get("PolicyDateFrom")));
			Date enddate = sdf_2.parse(String.valueOf(oPolicyInfo
					.get("PolicyDateTo")));
			sdinformation.setStartDate(startdate);
			sdinformation.setEndDate(enddate);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}

		String tAppType = getDisplayValue("AppType",
				String.valueOf(oPolicyInfo.get("AppType")), tProdCode);// 获取缴费方式名称
		sdinformation.setAppType(String.valueOf(oPolicyInfo.get("AppType")));
		sdinformation.setAppTypeName(tAppType);
		String tEnsureName = getDisplayValue("Period",
				String.valueOf(oPolicyInfo.get("Period")), tProdCode);// 获取保障期限名称
		String tEnsure = String.valueOf(oPolicyInfo.get("Period"));
		if (tEnsure != null && !"".equals(tEnsure)) {
			sdinformation.setEnsure(tEnsure);
			String test[] = tEnsure.split("-");
			if (test != null && test.length > 1) {
				String splitEnsure = test[1];
				sdinformation.setEnsureLimit(splitEnsure.substring(0,
						splitEnsure.length() - 1));
				sdinformation.setEnsureLimitType(splitEnsure.substring(
						splitEnsure.length() - 1, splitEnsure.length()));
			} else {
				sdinformation.setEnsureLimit(tEnsure.substring(0,
						tEnsure.length() - 1));
				sdinformation.setEnsureLimitType(tEnsure.substring(
						tEnsure.length() - 1, tEnsure.length()));
			}
		}

		sdinformation.setEnsureDisplay(tEnsureName);
		String tchargeTypeName = getDisplayValue("FeeYear",
				String.valueOf(oPolicyInfo.get("FeeYear")), tProdCode);// 获取缴费年期名称
		sdinformation.setChargeDisplay(tchargeTypeName);
		sdinformation.setChargeYear(String.valueOf(oPolicyInfo.get("FeeYear")));// 缴费年期
		sdinformation.setProductOutCode(BaseInformation[4].toString());// 产品外部编码
		sdinformation.setDiscountRates(BaseInformation[10].toString());// 产品折扣率
		sdinformation.setProductHtmlFilePath("");
		sdinformation.setPlanCode(String.valueOf(oPolicyInfo.get("Plan")));// 产品计划编码
		String tPlanName = getDisplayValue("Plan",
				String.valueOf(oPolicyInfo.get("Plan")), tProdCode);// 获取产品计划名称
		sdinformation.setPlanName(tPlanName);// 产品计划名称
		sdinformation.setPoint("");
		sdinformation.setPointStatus("");
		sdinformation.setSupKindCode(BaseInformation[12].toString());
		sdinformation.setSupRiskCode(BaseInformation[13].toString());
		sdinformation.setRiskType(BaseInformation[5].toString());// 内部统计险种中类别
		sdinformation.setSubRiskType(BaseInformation[7].toString());// 内部统计险种小类别
		sdinformation.setPrimitiveProductTitle(String
				.valueOf(BaseInformation[16]));// 保险公司原名称
		return sdinformation;

	}

	/**
	 * 获取投保人信息
	 * 
	 * @param tHolderInfo
	 * @param paramter
	 * @return
	 */
	public static SDInformationAppnt getSDInformationAppnt(
			Map<String, Object> tHolderInfo, Map<String, Object> paramter,
			Map<String, Object> mServiceMap,String productId) {
		SDInformationAppnt sdInformationAppnt = new SDInformationAppnt();
		String tApplicantSn = PubFun.GetWapSn("WPAppntSn");// 需要修改规则
		sdInformationAppnt.setApplicantSn(tApplicantSn);
		if (StringUtil.isNotEmpty(String.valueOf(tHolderInfo
				.get("AppntPayperTypeCode")))) {
			sdInformationAppnt.setApplicantIdentityType(String
					.valueOf(tHolderInfo.get("AppntPayperTypeCode")));
		}
		if (StringUtil.isNotEmpty(String.valueOf(tHolderInfo
				.get("AppntPayperType")))) {
			sdInformationAppnt.setApplicantIdentityTypeName(String
					.valueOf(tHolderInfo.get("AppntPayperType")));
		}
		if (StringUtil.isNotEmpty(String.valueOf(tHolderInfo
				.get("AppntPayperNumber")))) {
			sdInformationAppnt.setApplicantIdentityId(String
					.valueOf(tHolderInfo.get("AppntPayperNumber")));
		}
		if (StringUtil.isNotEmpty(String.valueOf(tHolderInfo.get("AppntName")))) {
			sdInformationAppnt.setApplicantName(String.valueOf(tHolderInfo
					.get("AppntName")));
		}
		if (StringUtil
				.isNotEmpty(String.valueOf(tHolderInfo.get("AppntEName")))) {
			sdInformationAppnt.setApplicantEnName(String.valueOf(tHolderInfo
					.get("AppntEName")));
		}
		if (StringUtil.isNotEmpty(String.valueOf(tHolderInfo
				.get("AppntBirthday")))) {
			sdInformationAppnt.setApplicantBirthday(String.valueOf(tHolderInfo
					.get("AppntBirthday")));
			int appAge = com.sinosoft.sms.messageinterface.pubfun.PubFun
					.getAge(sdInformationAppnt.getApplicantBirthday());// 获取投保人年龄
			sdInformationAppnt.setApplicantAge(String.valueOf(appAge));// 投保人年龄
		}
		if (StringUtil.isNotEmpty(String.valueOf(tHolderInfo
				.get("AppnttGenderCode")))) {
			sdInformationAppnt.setApplicantSex(String.valueOf(tHolderInfo
					.get("AppnttGenderCode")));
		}
		if (StringUtil.isNotEmpty(String.valueOf(tHolderInfo
				.get("AppnttGenderCode")))) {
			sdInformationAppnt.setApplicantSex(String.valueOf(tHolderInfo
					.get("AppnttGenderCode")));
		}
		if (StringUtil.isNotEmpty(String.valueOf(tHolderInfo
				.get("AppnttGender")))) {
			sdInformationAppnt.setApplicantSexName(String.valueOf(tHolderInfo
					.get("AppnttGender")));
		}
		if (StringUtil
				.isNotEmpty(String.valueOf(tHolderInfo.get("AppntEmail")))) {
			sdInformationAppnt.setApplicantMail(String.valueOf(tHolderInfo
					.get("AppntEmail")));
		}
		if (StringUtil.isNotEmpty(String.valueOf(tHolderInfo
				.get("AppntAddress")))) {
			sdInformationAppnt.setApplicantAddress(String.valueOf(tHolderInfo
					.get("AppntAddress")));
		}
		String zipcode = "000000";
		AreaService areaService = (AreaService) mServiceMap.get("AreaService");
		if (StringUtil.isNotEmpty(String.valueOf(tHolderInfo
				.get("AppntPostCode")))) {
			zipcode = String.valueOf(tHolderInfo.get("AppntPostCode"));
		} else {
			if (StringUtil.isEmpty(sdInformationAppnt.getApplicantZipCode())
					&& !"".equals(sdInformationAppnt.getApplicantArea1())
					&& sdInformationAppnt.getApplicantArea1() != null) {
				OrderConfigNewAction orderconfig = new OrderConfigNewAction();
				zipcode = orderconfig.getZipInfo(areaService
						.getAreaName(sdInformationAppnt.getApplicantArea1()),
						areaService.getAreaName(sdInformationAppnt
								.getApplicantArea2()));
			}else{
				if(StringUtil.isNotEmpty(productId)){
					String Product_zipcode= Config.getValue("Company_zipcode");
					String[] Product_id = Product_zipcode.split(",");
					for (int k = 0; k < Product_id.length; k++) {
						if (productId.startsWith(Product_id[k])) {
							zipcode="";
							break;
						}
					}		
				}
				
			}
		}
		sdInformationAppnt.setApplicantZipCode(zipcode);
		if (StringUtil
				.isNotEmpty(String.valueOf(tHolderInfo.get("AppntMobile")))) {
			sdInformationAppnt.setApplicantMobile(String.valueOf(tHolderInfo
					.get("AppntMobile")));
		}
		if (StringUtil.isNotEmpty(String.valueOf(tHolderInfo
				.get("AppntArea1Code")))) {
			sdInformationAppnt.setApplicantArea1(String.valueOf(tHolderInfo
					.get("AppntArea1Code")));
		}
		if (StringUtil.isNotEmpty(String.valueOf(tHolderInfo
				.get("AppntArea2Code")))) {
			sdInformationAppnt.setApplicantArea2(String.valueOf(tHolderInfo
					.get("AppntArea2Code")));
		}
		if (StringUtil.isNotEmpty(String.valueOf(tHolderInfo
				.get("AppntArea3Code")))) {
			sdInformationAppnt.setApplicantArea3(String.valueOf(tHolderInfo
					.get("AppntArea3Code")));
		}
		if (StringUtil.isNotEmpty(String.valueOf(tHolderInfo
				.get("AppntPayperStartDate")))) {
			sdInformationAppnt.setApplicantStartID(String.valueOf(tHolderInfo
					.get("AppntPayperStartDate")));
		}
		if (StringUtil.isNotEmpty(String.valueOf(tHolderInfo
				.get("AppntPayperEndDate")))) {
			sdInformationAppnt.setApplicantEndID(String.valueOf(tHolderInfo
					.get("AppntPayperEndDate")));
		}
		if (StringUtil.isNotEmpty(String.valueOf(tHolderInfo
				.get("AppntOccupation1Code")))) {
			sdInformationAppnt.setApplicantOccupation1(String
					.valueOf(tHolderInfo.get("AppntOccupation1Code")));
		}
		if (StringUtil.isNotEmpty(String.valueOf(tHolderInfo
				.get("AppntOccupation2Code")))) {
			sdInformationAppnt.setApplicantOccupation2(String
					.valueOf(tHolderInfo.get("AppntOccupation2Code")));
		}
		if (StringUtil.isNotEmpty(String.valueOf(tHolderInfo
				.get("AppntOccupation3Code")))) {
			sdInformationAppnt.setApplicantOccupation3(String
					.valueOf(tHolderInfo.get("AppntOccupation3Code")));
		}

		return sdInformationAppnt;
	}

	/**
	 * 获取被保人信息
	 * 
	 * @param tInsuredInfos
	 * @param paramter
	 * @return
	 */
	public static List<SDInformationInsured> getSDInformationInsured(
			List<Map<String, Object>> tInsuredInfoslist,
			Map<String, Object> mServiceMap, Map<String, Object> oPolicyInfo,
			SDInformationAppnt sdInformationAppnt) {
		DictionaryService dictionaryService = (DictionaryService) mServiceMap
				.get("DictionaryService");
		OrderConfigNewService orderConfigNewService = (OrderConfigNewService) mServiceMap
				.get("OrderConfigNewService");
		List<SDInformationInsured> Insuredlist = new ArrayList<SDInformationInsured>();
		for (int i = 0; i < tInsuredInfoslist.size(); i++) {
			SDInformationInsured sdInformationInsured = new SDInformationInsured();
			Map<String, Object> tInsuredInfos = tInsuredInfoslist.get(i);
			String recognizeeSn = PubFun.GetWapSn("WPInsureSn");
			sdInformationInsured.setRecognizeeSn(recognizeeSn);
			// int index = 1;
			// sdInformationInsured.setInsuredSn(recognizeeSn + "_" + index);
			// sdInformationInsured.setRecognizeeKey(recognizeeSn + "_" +
			// index);
			if (StringUtil.isNotEmpty(String.valueOf(tInsuredInfos
					.get("RecognizeeRelationShipCode")))) {
				sdInformationInsured.setRecognizeeAppntRelation(String
						.valueOf(tInsuredInfos
								.get("RecognizeeRelationShipCode")));
			}
			if (StringUtil.isNotEmpty(String.valueOf(tInsuredInfos
					.get("RecognizeeRelationShip")))) {
				sdInformationInsured.setRecognizeeAppntRelationName(String
						.valueOf(tInsuredInfos.get("RecognizeeRelationShip")));
			}
			if (StringUtil.isNotEmpty(String.valueOf(tInsuredInfos
					.get("RecognizeeName")))) {
				sdInformationInsured.setRecognizeeName(String
						.valueOf(tInsuredInfos.get("RecognizeeName")));
			}
			if (StringUtil.isNotEmpty(String.valueOf(tInsuredInfos
					.get("RecognizeeEName")))) {
				sdInformationInsured.setRecognizeeEnName(String
						.valueOf(tInsuredInfos.get("RecognizeeEName")));
			}
			if (StringUtil.isNotEmpty(String.valueOf(tInsuredInfos
					.get("RecognizeePayperTypeCode")))) {
				sdInformationInsured
						.setRecognizeeIdentityType(String.valueOf(tInsuredInfos
								.get("RecognizeePayperTypeCode")));
			}
			if (StringUtil.isNotEmpty(String.valueOf(tInsuredInfos
					.get("RecognizeePayperType")))) {
				sdInformationInsured.setRecognizeeIdentityTypeName(String
						.valueOf(tInsuredInfos.get("RecognizeePayperType")));
			}
			if (StringUtil.isNotEmpty(String.valueOf(tInsuredInfos
					.get("RecognizeePayperNumber")))) {
				sdInformationInsured.setRecognizeeIdentityId(String
						.valueOf(tInsuredInfos.get("RecognizeePayperNumber")));
			}
			if (StringUtil.isNotEmpty(String.valueOf(tInsuredInfos
					.get("RecognizeeGenderCode")))) {
				sdInformationInsured.setRecognizeeSex(String
						.valueOf(tInsuredInfos.get("RecognizeeGenderCode")));
			}
			if (StringUtil.isNotEmpty(String.valueOf(tInsuredInfos
					.get("RecognizeeGenderName")))) {
				sdInformationInsured.setRecognizeeSexName(String
						.valueOf(tInsuredInfos.get("RecognizeeGenderName")));
			}
			if (StringUtil.isNotEmpty(String.valueOf(tInsuredInfos
					.get("RecognizeeBirthday")))) {
				sdInformationInsured.setRecognizeeBirthday(String
						.valueOf(tInsuredInfos.get("RecognizeeBirthday")));
				int tInsuredAge = com.sinosoft.sms.messageinterface.pubfun.PubFun
						.getAge(sdInformationInsured.getRecognizeeBirthday());
				sdInformationInsured.setRecognizeeAge(String
						.valueOf(tInsuredAge));
			}
			if (StringUtil.isNotEmpty(String.valueOf(tInsuredInfos
					.get("RecognizeeEmail")))) {
				sdInformationInsured.setRecognizeeMail(String
						.valueOf(tInsuredInfos.get("RecognizeeEmail")));
			}
			if (StringUtil.isNotEmpty(String.valueOf(tInsuredInfos
					.get("RecognizeeMobile")))) {
				sdInformationInsured.setRecognizeeMobile(String
						.valueOf(tInsuredInfos.get("RecognizeeMobile")));
			}
			if (StringUtil.isNotEmpty(String.valueOf(tInsuredInfos
					.get("RecognizeeAddress")))) {
				sdInformationInsured.setRecognizeeAddress(String
						.valueOf(tInsuredInfos.get("RecognizeeAddress")));
			}
			String zipcode = "000000";
			AreaService areaService = (AreaService) mServiceMap
					.get("AreaService");
			if (StringUtil.isNotEmpty(String.valueOf(tInsuredInfos
					.get("RecognizeePostCode")))) {
				zipcode = String.valueOf(tInsuredInfos
						.get("RecognizeePostCode"));
			} else {
				if (StringUtil.isEmpty(sdInformationInsured
						.getRecognizeeArea1())
						&& !"".equals(sdInformationInsured.getRecognizeeArea1())
						&& sdInformationInsured.getRecognizeeArea1() != null) {
					OrderConfigNewAction orderconfig = new OrderConfigNewAction();
					zipcode = orderconfig.getZipInfo(areaService
							.getAreaName(sdInformationInsured
									.getRecognizeeArea1()), areaService
							.getAreaName(sdInformationInsured
									.getRecognizeeArea2()));
				}else{
					if(StringUtil.isNotEmpty(oPolicyInfo.get("ProdCode").toString())){
						String Product_zipcode= Config.getValue("Company_zipcode");
						String[] Product_id = Product_zipcode.split(",");
						for (int k = 0; k < Product_id.length; k++) {
							if (oPolicyInfo.get("ProdCode").toString().startsWith(Product_id[k])) {
								zipcode="";
								break;
							}
						}		
					}
					
				}
			}
			sdInformationInsured.setRecognizeeZipCode(zipcode);

			if (StringUtil.isNotEmpty(String.valueOf(tInsuredInfos
					.get("FlightNumber")))) {
				sdInformationInsured.setFlightNo(String.valueOf(tInsuredInfos
						.get("FlightNumber")));
			}
			if (StringUtil.isNotEmpty(String.valueOf(tInsuredInfos
					.get("RecognizeeStudyAbroadCompany")))) {
				sdInformationInsured.setSchoolOrCompany(String
						.valueOf(tInsuredInfos
								.get("RecognizeeStudyAbroadCompany")));
			}
			if (StringUtil.isNotEmpty(String.valueOf(tInsuredInfos
					.get("BuyCopies")))) {
				sdInformationInsured.setRecognizeeMul(String
						.valueOf(tInsuredInfos.get("BuyCopies")));
			}
			if (StringUtil.isNotEmpty(String.valueOf(tInsuredInfos
					.get("RecognizeePayperStartDate")))) {
				sdInformationInsured
						.setRecognizeeStartID(String.valueOf(tInsuredInfos
								.get("RecognizeePayperStartDate")));
			}
			if (StringUtil.isNotEmpty(String.valueOf(tInsuredInfos
					.get("RecognizeePayperEndDate")))) {
				sdInformationInsured.setRecognizeeEndID(String
						.valueOf(tInsuredInfos.get("RecognizeePayperEndDate")));
			}
			if (StringUtil.isNotEmpty(String.valueOf(tInsuredInfos
					.get("TakeOffLocation")))) {
				sdInformationInsured.setFlightLocation(String
						.valueOf(tInsuredInfos.get("TakeOffLocation")));
			}
			if (StringUtil.isNotEmpty(String.valueOf(tInsuredInfos
					.get("TakeOffTime")))) {
				sdInformationInsured.setFlightTime(String.valueOf(tInsuredInfos
						.get("TakeOffTime")));
			}
			if (StringUtil.isNotEmpty(String.valueOf(tInsuredInfos
					.get("IsHolderSelf")))) {
				sdInformationInsured.setIsSelf(String.valueOf(tInsuredInfos
						.get("IsHolderSelf")));
			}
			if (StringUtil.isNotEmpty(String.valueOf(tInsuredInfos
					.get("RecognizeeHeight")))) {
				sdInformationInsured.setHeight(String.valueOf(tInsuredInfos
						.get("RecognizeeHeight")));
			}
			if (StringUtil.isNotEmpty(String.valueOf(tInsuredInfos
					.get("RecognizeeWeight")))) {
				sdInformationInsured.setWeight(String.valueOf(tInsuredInfos
						.get("RecognizeeWeight")));
			}
			if (StringUtil.isNotEmpty(String.valueOf(tInsuredInfos
					.get("RecognizeeDrivingSchoolName")))) {
				sdInformationInsured.setDriverSchoolName(String
						.valueOf(tInsuredInfos
								.get("RecognizeeDrivingSchoolName")));
			}
			if (StringUtil.isNotEmpty(String.valueOf(tInsuredInfos
					.get("RecognizeeStudentCode")))) {
				sdInformationInsured.setDriverNo(String.valueOf(tInsuredInfos
						.get("RecognizeeStudentCode")));
			}
			if (StringUtil.isNotEmpty(String.valueOf(tInsuredInfos
					.get("RecognizeeNational")))) {
				sdInformationInsured.setNationality(String
						.valueOf(tInsuredInfos.get("RecognizeeNational")));
			}
			if (StringUtil.isNotEmpty(String.valueOf(tInsuredInfos
					.get("RecognizeeOverseasOccupation")))) {
				sdInformationInsured.setOverseasOccupation(String
						.valueOf(tInsuredInfos
								.get("RecognizeeOverseasOccupation")));
			}
			if (StringUtil.isNotEmpty(String.valueOf(tInsuredInfos
					.get("RecognizeeHasBuyLifeCov")))) {
				sdInformationInsured.setHaveBuy(String.valueOf(tInsuredInfos
						.get("RecognizeeHasBuyLifeCov")));
			}
			if (StringUtil.isNotEmpty(String.valueOf(tInsuredInfos
					.get("RecognizeeOccupation1Code")))) {
				sdInformationInsured
						.setRecognizeeOccupation1(String.valueOf(tInsuredInfos
								.get("RecognizeeOccupation1Code")));
			}
			if (StringUtil.isNotEmpty(String.valueOf(tInsuredInfos
					.get("RecognizeeOccupation2Code")))) {
				sdInformationInsured
						.setRecognizeeOccupation2(String.valueOf(tInsuredInfos
								.get("RecognizeeOccupation2Code")));
			}
			if (StringUtil.isNotEmpty(String.valueOf(tInsuredInfos
					.get("RecognizeeOccupation3Code")))) {
				sdInformationInsured
						.setRecognizeeOccupation3(String.valueOf(tInsuredInfos
								.get("RecognizeeOccupation3Code")));
			}
			if (StringUtil.isNotEmpty(String.valueOf(tInsuredInfos
					.get("RecognizeeArea1Code")))) {
				sdInformationInsured.setRecognizeeArea1(String
						.valueOf(tInsuredInfos.get("RecognizeeArea1Code")));
			}
			if (StringUtil.isNotEmpty(String.valueOf(tInsuredInfos
					.get("RecognizeeArea2Code")))) {
				sdInformationInsured.setRecognizeeArea2(String
						.valueOf(tInsuredInfos.get("RecognizeeArea2Code")));
			}
			if (StringUtil.isNotEmpty(String.valueOf(tInsuredInfos
					.get("RecognizeeArea3Code")))) {
				sdInformationInsured.setRecognizeeArea3(String
						.valueOf(tInsuredInfos.get("RecognizeeArea3Code")));
			}

			String tdestinationCountry1 = String.valueOf(tInsuredInfos
					.get("RecognizeeTravelDestinationInput"));
			String tdestinationCountry2 = String.valueOf(tInsuredInfos
					.get("RecognizeeTravelDestinationSel"));
			if ("".equals(tdestinationCountry1)
					|| StringUtil.isEmpty(tdestinationCountry1)) {
				sdInformationInsured
						.setDestinationCountry(tdestinationCountry2);

			} else {
				sdInformationInsured
						.setDestinationCountry(tdestinationCountry1);
			}
			// 设置旅游目的地字段
			String tDestinationCountry = sdInformationInsured
					.getDestinationCountry();
			String destinationCountryText = "";
			String tProductId = String.valueOf(oPolicyInfo.get("ProdCode"));
			String tInsuranceCompany = String.valueOf(oPolicyInfo
					.get("CompanyID"));
			if (StringUtil.isNotEmpty(sdInformationInsured
					.getDestinationCountry())) {
				if (StringUtil.isNotEmpty(sdInformationInsured
						.getDestinationCountry())
						&& !"1015".equals(tInsuranceCompany)) {

					destinationCountryText = dictionaryService
							.getNameByCodeTypePro(tProductId,
									tInsuranceCompany, "CountryCode",
									sdInformationInsured
											.getDestinationCountry());
					sdInformationInsured
							.setDestinationCountryText(destinationCountryText);
				}

				destinationCountryText = settDestinationCountry(
						tInsuranceCompany, sdInformationInsured,
						dictionaryService, tDestinationCountry);
				if ("2007".equals(tInsuranceCompany)
						&& tDestinationCountry != null
						&& !"".equals(tDestinationCountry)) {
					if (tDestinationCountry != null
							&& !"".equals(tDestinationCountry)) {
						String CountryText = orderConfigNewService
								.getCountryText2007("2007", tDestinationCountry);
						sdInformationInsured
								.setDestinationCountry(tDestinationCountry);
						sdInformationInsured
								.setDestinationCountryText(CountryText);
					}
				} else if ("1015".equals(tInsuranceCompany)
						&& tDestinationCountry != null
						&& !"".equals(tDestinationCountry)) {
					sdInformationInsured
							.setDestinationCountry(tDestinationCountry);
					sdInformationInsured
							.setDestinationCountryText(orderConfigNewService
									.getCountryText1015("1015",
											tDestinationCountry));
				} else if (("2011".equals(tInsuranceCompany)
						|| "2023".equals(tInsuranceCompany) || "2071"
							.equals(tInsuranceCompany))
						&& tDestinationCountry != null
						&& !"".equals(tDestinationCountry)) {
					sdInformationInsured
							.setDestinationCountry(tDestinationCountry);
					sdInformationInsured
							.setDestinationCountryText(orderConfigNewService
									.getSchengenCountryText(tInsuranceCompany,
											tDestinationCountry, tProductId));
				} else {
					if (tDestinationCountry != null
							&& !"".equals(tDestinationCountry)) {
						sdInformationInsured
								.setDestinationCountry(tDestinationCountry);
						sdInformationInsured
								.setDestinationCountryText(destinationCountryText);
					}
				}

			}
			if (StringUtil.isNotEmpty(String.valueOf(tInsuredInfos
					.get("RecognizeePrem")))) {
				sdInformationInsured.setRecognizeePrem(String
						.valueOf(tInsuredInfos.get("RecognizeePrem")));
			} else {
				sdInformationInsured.setRecognizeePrem(String.valueOf(
						oPolicyInfo.get("Premium")).toString());
			}
			if (StringUtil.isNotEmpty(String.valueOf(tInsuredInfos
					.get("RecognizeeActivePrem")))) {
				sdInformationInsured.setDiscountPrice(String
						.valueOf(tInsuredInfos.get("RecognizeeActivePrem")));// 活动折后价
			} else {
				sdInformationInsured.setDiscountPrice(String
						.valueOf(oPolicyInfo.get("PrePremium").toString()));// 活动折后价
			}

			String RelationShip = String.valueOf(
					tInsuredInfos.get("RecognizeeRelationShip")).trim();
			if (RelationShip.contains("本人")) {
				copyAppttoInsured(sdInformationAppnt, sdInformationInsured);
			}
			Insuredlist.add(sdInformationInsured);
		}

		return Insuredlist;
	}

	/**
	 * 获取订单项1信息
	 * 
	 * @param sdinformation
	 * @param sdorder
	 * @return
	 */
	public static SDOrderItem getSDOrderItem(SDInformation sdinformation,
			SDOrder sdorder) {
		SDOrderItem tSDOrderItem = new SDOrderItem();
		tSDOrderItem.setOrderSn(sdorder.getOrderSn());
		tSDOrderItem.setSdorder(sdorder);
		tSDOrderItem.setOrderPoint("0");
		tSDOrderItem.setPointStatus("1");
		tSDOrderItem.setTypeFlag("02");
		tSDOrderItem.setOrderitemSn(PubFun.GetWapSn("WPItemSn"));
		return tSDOrderItem;
	}

	/**
	 * 获取保单信息
	 * 
	 * @param sdorder
	 * @param sdinformation
	 * @param sdInformationAppnt
	 * @param sdInformationInsured
	 * @return
	 */
	public SDInformationRiskType getSDInformationRiskType(SDOrder sdorder,
			SDInformation sdinformation, SDInformationAppnt sdInformationAppnt,
			SDInformationInsured sdInformationInsured) {
		SDInformationRiskType sdinformationRiskTypes = new SDInformationRiskType();
		// sdinformationRiskTypes.setId("200001");// 险种，由接口获得
		// 订单号
		sdinformationRiskTypes.setOrderSn(sdinformation.getOrderSn());
		// 订单明细编号
		sdinformationRiskTypes.setInformationSn(sdinformation
				.getInformationSn());
		// 被保人编号
		sdinformationRiskTypes.setRecognizeeSn(sdInformationInsured
				.getRecognizeeSn());
		// 投保人编号
		sdinformationRiskTypes.setApplicantSn(sdInformationAppnt
				.getApplicantSn());
		// 保单号，需要处理
		sdinformationRiskTypes.setPolicyNo("");
		// 险种编码
		sdinformationRiskTypes.setRiskCode(sdinformation.getProductId());
		// 险种名称
		sdinformationRiskTypes.setRiskName(sdinformation.getProductName());
		// 保额，需要处理
		sdinformationRiskTypes.setAmnt("");
		// 份数，需要处理
		sdinformationRiskTypes.setMult("1");
		// 保费，需要处理
		if (StringUtil.isEmpty(sdInformationInsured.getRecognizeePrem())) {
			sdinformationRiskTypes.setTimePrem(sdorder.getTotalAmount()
					.toString());
		} else {
			sdinformationRiskTypes.setTimePrem(sdInformationInsured
					.getRecognizeePrem());
		}
		if (StringUtil.isEmpty(sdInformationInsured.getRecognizeeTotalPrem())) {
			sdinformationRiskTypes.setProductPrice(sdorder.getTotalAmount()
					.toString());
		} else {
			sdinformationRiskTypes.setProductPrice(sdInformationInsured
					.getRecognizeeTotalPrem());
		}

		// 生效日期
		sdinformationRiskTypes.setSvaliDate(sdinformation.getStartDate());
		// 失效日期
		sdinformationRiskTypes.setEvaliDate(sdinformation.getEndDate());
		// 以下字段待定
		// 缴费年期类型
		sdinformationRiskTypes.setPeriodFlag(sdinformation.getChargeType());
		sdinformationRiskTypes.setPeriod(sdinformation.getChargeYear());// 缴费年期
		sdinformationRiskTypes.setAmnt(String.valueOf(new BigDecimal(dAmnt)));
		sdinformationRiskTypes.setElectronicCout("");// 电子保单保险公司路径
		sdinformationRiskTypes.setElectronicPath("");// 电子保单物理路径
		sdinformationRiskTypes.setInsurerFlag("");
		sdinformationRiskTypes.setInsureMsg("");
		// sdinformationRiskTypes.setProductPrice(productPrice);
		// sdinformationRiskTypes.setBalanceDate("");
		// sdinformationRiskTypes.setBalanceFlag("");
		// sdinformationRiskTypes.setBalanceMsg("");
		// sdinformationRiskTypes.setBalanceStatus("");
		sdinformationRiskTypes.setSdorder(sdorder);
		sdinformationRiskTypes.setSdinformationinsured(sdInformationInsured);
		return sdinformationRiskTypes;
	}

	/**
	 * 复制投保人信息到被保人信息
	 * 
	 * @param Appnt
	 * @param Insured
	 * @return
	 */
	public static SDInformationInsured copyAppttoInsured(
			SDInformationAppnt Appnt, SDInformationInsured Insured) {
		Insured.setRecognizeeName(Appnt.getApplicantName());
		Insured.setRecognizeeEnName(Appnt.getApplicantEnName());
		Insured.setRecognizeeIdentityType(Appnt.getApplicantIdentityType());
		Insured.setRecognizeeIdentityTypeName(Appnt
				.getApplicantIdentityTypeName());
		Insured.setRecognizeeIdentityId(Appnt.getApplicantIdentityId());
		Insured.setRecognizeeSex(Appnt.getApplicantSex());
		Insured.setRecognizeeSexName(Appnt.getApplicantSexName());
		Insured.setRecognizeeBirthday(Appnt.getApplicantBirthday());
		Insured.setRecognizeeMobile(Appnt.getApplicantMobile());
		if (StringUtil.isNotEmpty(Insured.getRecognizeeMail())) {
			Insured.setRecognizeeMail(Appnt.getApplicantMail());
		}
		if (StringUtil.isNotEmpty(Insured.getRecognizeeZipCode())) {
			Insured.setRecognizeeZipCode(Appnt.getApplicantZipCode());
		}
		if (StringUtil.isNotEmpty(Appnt.getApplicantOccupation1())) {
			Insured.setRecognizeeOccupation1(Appnt.getApplicantOccupation1());
		}
		if (StringUtil.isNotEmpty(Appnt.getApplicantOccupation2())) {
			Insured.setRecognizeeOccupation2(Appnt.getApplicantOccupation2());
		}
		if (StringUtil.isNotEmpty(Appnt.getApplicantOccupation3())) {
			Insured.setRecognizeeOccupation3(Appnt.getApplicantOccupation3());
		}
		if (StringUtil.isNotEmpty(Appnt.getApplicantArea1())) {
			Insured.setRecognizeeArea1(Appnt.getApplicantArea1());
		}
		if (StringUtil.isNotEmpty(Appnt.getApplicantArea2())) {
			Insured.setRecognizeeArea2(Appnt.getApplicantArea2());
		}
		if (StringUtil.isNotEmpty(Appnt.getApplicantArea3())) {
			Insured.setRecognizeeArea3(Appnt.getApplicantArea3());
		}
		if (StringUtil.isNotEmpty(Appnt.getApplicantAddress())) {
			Insured.setRecognizeeAddress(Appnt.getApplicantAddress());
		}
		Insured.setRecognizeeAge(Appnt.getApplicantAge());
		if (StringUtil.isNotEmpty(Appnt.getApplicantStartID())) {
			Insured.setRecognizeeStartID(Appnt.getApplicantStartID());
		}
		if (StringUtil.isNotEmpty(Appnt.getApplicantEndID())) {
			Insured.setRecognizeeEndID(Appnt.getApplicantEndID());
		}
		return Insured;
	}

	/**
	 * 设置目的地国家名称字段
	 * 
	 * @author sunny
	 * @param insuranceCode
	 * @param insured
	 * @param dictionaryService
	 */
	private static String settDestinationCountry(String insuranceCode,
			SDInformationInsured insured, DictionaryService dictionaryService,
			String tDestinationCountry) {
		if ((null == tDestinationCountry) || (null == dictionaryService)
				|| StringUtil.isEmpty(tDestinationCountry)) {
			return "";
		}

		// String[] destinations = insured.gettDestinationCountry().split(",");
		String[] destinations = tDestinationCountry.split(",");
		Set<String> countrySet = new HashSet<String>();
		StringBuffer destinationText = new StringBuffer();
		int j = 0;
		String sql = "select 1 from zdcode where CodeType=? and ParentCode=? and CodeValue=?";
		QueryBuilder qb = new QueryBuilder(sql);
		qb.add("tDestinationCountryText");
		qb.add("tDestinationCountryText");
		qb.add(insuranceCode);
		boolean saveEnNameFlag = qb.executeInt() > 0;
		for (int i = 0; i < destinations.length; i++) {
			String name = null;
			String CountryCode = destinations[i].trim();
			if (saveEnNameFlag) {
				name = (dictionaryService.getCodeEnNameByCodeValue(
						insuranceCode, "CountryCode", CountryCode));
			} else {
				name = (dictionaryService.findCountryNameByCode(insuranceCode,
						CountryCode));
			}

			if (StringUtil.isEmpty(name)) {
				continue;
			}
			countrySet.add(CountryCode);
			if (j > 0) {
				if (destinationText.indexOf(name) == -1) {
					destinationText.append(",");
				}
			}
			if (destinationText.indexOf(name) == -1) {
				destinationText.append(name);
			}

			j++;
		}
		int m = 0;
		tDestinationCountry = "";
		for (String cCode : countrySet) {
			if (m > 0) {
				tDestinationCountry = tDestinationCountry + ",";
			}
			tDestinationCountry = tDestinationCountry + cCode;
			m = m + 1;
		}
		return destinationText.toString();
		// insured.setDestinationCountryText();
	}

	/**
	 * 根据名称查编码
	 * 
	 * @param type
	 * @param DisplayValue
	 * @param ProdCode
	 * @return
	 */
	public static String getFactorValue(String type, String DisplayValue,
			String ProdCode) {
		QueryBuilder tQueryBuilder = new QueryBuilder();
		StringBuffer tradeSQL = new StringBuffer(
				"SELECT  b.FactorValue FROM FERiskAppFactor a LEFT JOIN FEMRiskFactorList b ON a.AppFactorCode = b.AppFactorCode WHERE a.riskcode = '"
						+ ProdCode
						+ "' AND a.FactorType='"
						+ type
						+ "' AND b.FactorDisplayValue='"
						+ DisplayValue
						+ "' ; ");
		tQueryBuilder.setSQL(tradeSQL.toString());
		DataTable tradeDT = tQueryBuilder.executeDataTable();
		String tFactorValue = "";
		if (tradeDT != null && tradeDT.getRowCount() > 0) {
			tFactorValue = tradeDT.getString(0, 0);
		}
		return tFactorValue;

	}

	/**
	 * 根据编码反查名称
	 * 
	 * @param type
	 * @param FactorValue
	 * @param ProdCode
	 * @return
	 */
	public static String getDisplayValue(String type, String FactorValue,
			String ProdCode) {
		QueryBuilder tQueryBuilder = new QueryBuilder();
		StringBuffer tradeSQL = new StringBuffer(
				"SELECT  b.FactorDisplayValue  FROM FERiskAppFactor a LEFT JOIN FEMRiskFactorList b ON a.AppFactorCode = b.AppFactorCode WHERE a.riskcode = '"
						+ ProdCode
						+ "' AND a.FactorType='"
						+ type
						+ "' AND b.FactorValue='" + FactorValue + "' ; ");
		tQueryBuilder.setSQL(tradeSQL.toString());
		DataTable tradeDT = tQueryBuilder.executeDataTable();
		String tDisplayValue = "";
		if (tradeDT != null && tradeDT.getRowCount() > 0) {
			tDisplayValue = tradeDT.getString(0, 0);
		}
		return tDisplayValue;

	}

	/**
	 * 获取投保要素信息
	 * 
	 * @param cMap
	 * @param order
	 * @param infor
	 * @param ins
	 * @return
	 */
	public static Set<SDInformationInsuredElements> getInfomationInsuredElements(
			Map<String, Object> cMap, SDOrder order, SDInformation infor,
			SDInformationInsured ins, List<OrderRiskAppFactor> riskAppFactior) {

		Set<SDInformationInsuredElements> tInformationInsuredElementsSet = new HashSet<SDInformationInsuredElements>();
		if (riskAppFactior != null) {
			for (int i = 0; i < riskAppFactior.size(); i++) {
				String appFactiorType = riskAppFactior.get(i).getFactorType()
						.toString();
				Object factorValueTemp = cMap.get(appFactiorType);
				SDInformationInsuredElements informationInsuredElements = new SDInformationInsuredElements();
				if (!"TextAge".equals(appFactiorType) && riskAppFactior != null
						&& riskAppFactior.get(i) != null) {

					informationInsuredElements.setElementsType(appFactiorType);
					informationInsuredElements.setElementsSn(PubFun
							.GetWapSn("WPElementSn"));
					informationInsuredElements.setOrderSn(order.getOrderSn());
					informationInsuredElements.setRecognizeeSn(ins
							.getRecognizeeSn());
					informationInsuredElements.setSdinformationInsured(ins);
					informationInsuredElements.setInformationSn(infor
							.getInformationSn());
					informationInsuredElements.setElementsSn(riskAppFactior
							.get(i).getAppFactorCode());
					if (factorValueTemp != null) {
						informationInsuredElements
								.setElementsValue(factorValueTemp.toString());
					}
				}
				if (informationInsuredElements != null) {
					tInformationInsuredElementsSet
							.add(informationInsuredElements);
				}

			}
		}

		return tInformationInsuredElementsSet;
	}

	/**
	 * 获取责任项信息
	 * 
	 * @param order
	 * @param infor
	 * @return
	 */
	public List<SDInformationDuty> getInformationDuty(SDOrder order,
			SDInformation infor, List<OrderDutyFactor> dutyFactor) {

		List<SDInformationDuty> tInformationDutyList = new ArrayList<SDInformationDuty>();
		if (dutyFactor != null) {
			for (int i = 0; i < dutyFactor.size(); i++) {
				String cPlanCode = infor.getPlanCode();
				Object showDutyValue = "";
				Object premDutyValue = "";
				Object factorValueTemp = "";
				for (FEMDutyAmntPremList fEMDutyAmntPremList : dutyFactor
						.get(i).getFdAmntPremList()) {
					showDutyValue = fEMDutyAmntPremList.getAmnt();
					premDutyValue = fEMDutyAmntPremList.getPrem();
					factorValueTemp = fEMDutyAmntPremList.getBackUp1();
					if (StringUtil.isNotEmpty(cPlanCode) && cPlanCode.equals(fEMDutyAmntPremList.getAppFactorValue())) {
						showDutyValue = fEMDutyAmntPremList.getAmnt();
						premDutyValue = fEMDutyAmntPremList.getPrem();
						factorValueTemp = fEMDutyAmntPremList.getBackUp1();
						break;
					}
				}
				if ((StringUtil.isEmpty((String) factorValueTemp))
						|| "nvalue".equals(factorValueTemp)) {
					continue;
				}
				SDInformationDuty sdinformationduty = new SDInformationDuty();

				sdinformationduty.setDutySn(dutyFactor.get(i).getDutyCode());// 责任编码
				sdinformationduty.setInformationSn(infor.getInformationSn());// 订单明细表编码
				sdinformationduty.setOrderSn(order.getOrderSn());// 保单号
				sdinformationduty.setDutyName(dutyFactor.get(i)
						.getDudtyFactorName());// 责任名称
				sdinformationduty.setDutyFullName(dutyFactor.get(i)
						.getDutyFullName());// 责任全称
				sdinformationduty.setCoverage(dutyFactor.get(i).getDefine());
				sdinformationduty.setRiskCode("");// 险种编码
				if (!"nvalue".equals(showDutyValue)) {
					sdinformationduty.setShowAmnt(showDutyValue.toString());
				} else {
					sdinformationduty.setShowAmnt("0.0");// 显示值
				}
				if (!"nvalue".equals(premDutyValue)) {
					sdinformationduty.setPremium(premDutyValue + "");
				} else {
					sdinformationduty.setPremium("0.0");// 保费
				}
				sdinformationduty.setAmt(factorValueTemp.toString());// 计算值保额
				sdinformationduty.setMainRiskFlag(dutyFactor.get(i)
						.getCurrency());// 主险标志
				sdinformationduty.setSupplierDutyCode(dutyFactor.get(i)
						.getSupplierDutyCode());// 保险公司责任/险别编码
				sdinformationduty.setDutyEnName(dutyFactor.get(i)
						.getDutyEnName());// 责任英文名
				sdinformationduty.setEnCoverage(dutyFactor.get(i)
						.getEnCoverage());// 责任英文说明
				sdinformationduty.setSdinformation(infor);// 级联保存订单明细表信息

				tInformationDutyList.add(sdinformationduty);
				dAmnt += Double.parseDouble(sdinformationduty.getAmt());
			}
		}

		return tInformationDutyList;

	}

	public static List<SDInsuredHealth> getHealthyInfo(
			List<Map<String, Object>> tHealthyInfoslist,
			Map<String, Object> mServiceMap,
			List<SDInformationInsured> minsuredList,
			Map<String, Object> oPolicyInfo) {

		String tProductId = String.valueOf(oPolicyInfo.get("ProdCode"));
		String tInsuranceCompany = String.valueOf(oPolicyInfo.get("CompanyID"));
		HealthyInfoService healthyInfoService = (HealthyInfoService) mServiceMap
				.get("HealthyInfoService");
		SDInsuredHealthService insuredHealthService = (SDInsuredHealthService) mServiceMap
				.get("SDInsuredHealthService");
		List<SDInsuredHealth> tSDInsuredHealthList = new ArrayList<SDInsuredHealth>();
		if (tHealthyInfoslist != null && tHealthyInfoslist.size() >= 1) {
			for (SDInformationInsured insure : minsuredList) {
				List<HealthyInfo> healthList = healthyInfoService
						.findByComAndProduct(tProductId, tInsuranceCompany);
				List<SDInsuredHealth> sdinsuredHealthList = insuredHealthService
						.createShowInformation(healthList);
				if (sdinsuredHealthList != null
						&& sdinsuredHealthList.size() > 0) {
					for (SDInsuredHealth ih : sdinsuredHealthList) {
						for (int i = 0; i < tHealthyInfoslist.size(); i++) {
							Map<String, Object> tHealthyInfos = tHealthyInfoslist
									.get(i);
							if (ih.getHealthyInfoId().equals(
									tHealthyInfos.get("HealthSn"))) {
								String selectFlag = String
										.valueOf(tHealthyInfos.get("Selected"));
								ih.setSelectFlag(selectFlag);
								String typeShowOrder = String.valueOf(i + 1);
								ih.setTypeShowOrder(typeShowOrder);
								ih.setSdinformationinsured(insure);
								ih.setOrderSn(insure.getOrderSn());
								ih.setInformationSn(insure.getInformationSn());
								ih.setRecognizeeSn(insure.getRecognizeeSn());
								tSDInsuredHealthList.add(ih);
							}
						}
					}
				}
			}
		}
		return tSDInsuredHealthList;
	}

	public static List<SDInformationProperty> getPropertyInfo(
			List<Map<String, Object>> tHourseInfoList,
			List<SDInformationInsured> minsuredList) {

		List<SDInformationProperty> tSDInformationPropertyList = new ArrayList<SDInformationProperty>();
		if (tHourseInfoList != null && tHourseInfoList.size() >= 1) {
			for (SDInformationInsured insure : minsuredList) {
				for (int i = 0; i < tHourseInfoList.size(); i++) {
					Map<String, Object> tHourseInfos = tHourseInfoList.get(i);
					if (tHourseInfos != null && tHourseInfos.size() >= 1) {
						SDInformationProperty tSDInformationProperty = new SDInformationProperty();
						tSDInformationProperty.setHourseType(String
								.valueOf(tHourseInfos.get("HourseType")));
						tSDInformationProperty.setHourseAge(String
								.valueOf(tHourseInfos.get("HourseAge")));
						tSDInformationProperty.setRecognizeeSn(insure
								.getRecognizeeSn());
						tSDInformationProperty.setHourseNo(String
								.valueOf(tHourseInfos.get("HourseNo")));
						tSDInformationProperty.setCarPlateNo(String
								.valueOf(tHourseInfos.get("CarPlateNo")));
						tSDInformationProperty.setLicenseNumber(String
								.valueOf(tHourseInfos.get("LicenseNumber")));
						tSDInformationProperty.setChassisNumber(String
								.valueOf(tHourseInfos.get("ClassisNumber")));
						tSDInformationProperty.setPropertyArea1(String
								.valueOf(tHourseInfos.get("PropertyArea1")));
						tSDInformationProperty.setPropertyArea2(String
								.valueOf(tHourseInfos.get("PropertyArea2")));
						tSDInformationProperty.setPropertyAdress(String
								.valueOf(tHourseInfos.get("PropertyAddress")));
						tSDInformationProperty.setPropertyZip(String
								.valueOf(tHourseInfos.get("PropertyZip")));
						tSDInformationProperty.setPropertyToRecognizee(String
								.valueOf(tHourseInfos
										.get("PropertyToRecognizee")));
						tSDInformationProperty.setSdinformationinsured(insure);
						tSDInformationPropertyList.add(tSDInformationProperty);
					}
				}
			}
		}
		return tSDInformationPropertyList;
	}

	/**
	 * 获取投保信息模板
	 * 
	 * @return
	 */
	public static Map<String, Object> wapApplicationTempMap(
			Map<String, Object> tPARAMETERS) {
		List<String> arrayList = new ArrayList<String>();
		String productID = String.valueOf(tPARAMETERS.get("ProdCode"));// 产品编码
		if (StringUtil.isEmpty(productID)) {
			arrayList.add("G000002");
			return WapErrorUtil.dealErrorInfo(arrayList, "KXBJRT0011", null);
		}
		Map<String, Object> applicationTempMap = new HashMap<String, Object>();
		try {
			// 跟军产品查询所需要的投保要素
			StringBuffer fb = new StringBuffer();
			fb.append(" SELECT e.ElementType,f.* FROM ProductTempInfo a,ProductToTemplate b,ModuleInfo c,ModuleElement d,ModuleElementInfo e,ElementRelaInfo f ");
			fb.append(" WHERE a.ProductID = ? ");// '101501013'
			fb.append(" AND a.TemplateId = b.FactorID AND b.TemplateCode = c.ID AND c.Id = d.ModuleCode ");
			fb.append(" AND e.Id = d.ElementCode AND e.Remark1 = 'Y' AND e.ID = f.ElementID ORDER BY d.OrderFlag ASC,cast(prop1 as unsigned int) ASC ");

			QueryBuilder qb = new QueryBuilder();
			qb.setSQL(fb.toString());
			qb.add(productID);

			DataTable dt = qb.executeDataTable();

			int len = dt.getRowCount();
			if (len <= 0) {
				arrayList.add("wapShopping00010");
				return WapErrorUtil
						.dealErrorInfo(arrayList, "KXBJRT0011", null);
			}
			// 投保要素
			Map<String, Object> appitemMap = new HashMap<String, Object>();
			// 信息数组
			JSONArray appitemArr = new JSONArray();
			JSONArray appntArr = new JSONArray();
			JSONArray insredArr = new JSONArray();
			JSONArray hourseArr = new JSONArray();
			// 处理投保要素
			for (int i = 0; i < len; i++) {
				Map<String, Object> subInfoMap = new HashMap<String, Object>();
				// 输入项ID
				subInfoMap.put("ItemID", dt.getString(i, "InputCode"));
				// 输入项名称
				subInfoMap.put("ItemName", dt.getString(i, "InputName"));
				// 输入项类型
				subInfoMap.put("InputType", dt.getString(i, "InfoType"));
				// 元素是否显示
				subInfoMap.put("IsDisplay", "Y");
				// 输入项是否可编辑
				subInfoMap.put("IsEnabled", dt.getString(i, "IsModify"));
				// 输入项是否可编辑
				subInfoMap.put("UnitDes", dt.getString(i, "Prop2"));

				// 输入项是否可为空
				subInfoMap.put("IsNonEmpty", "N");

				if ("select".equals(dt.getString(i, "InfoType"))) {
					// 默认值
					subInfoMap.put("DefaultValue", "请选择");
				} else {
					// 默认值
					subInfoMap.put("DefaultValue", "");
				}

				// 输入项取值范围
				// subInfoMap.put("ValueScope", "");
				// 输入项校验类型
				subInfoMap.put("ValidType", dt.getString(i, "ValidateInfo"));// 已","为分隔

				// 投保人信息节点
				/*
				 * if ("A".equals(dt.getString(i, "ElementType"))) {
				 * appitemMap.put(dt.getString(i, "InputCode"), subInfoMap); }
				 * else
				 */if ("B".equals(dt.getString(i, "ElementType"))) {
					// appntinfoMap.put(dt.getString(i, "InputCode"),
					// subInfoMap);
					appntArr.put(subInfoMap);
				} else if ("C".equals(dt.getString(i, "ElementType"))) {
					// 被保人信息节点
					/*
					 * insuredinfoMap .put(dt.getString(i, "InputCode"),
					 * subInfoMap);
					 */
					insredArr.put(subInfoMap);
				} else if ("F".equals(dt.getString(i, "ElementType"))) {
					// 被保人信息节点
					/*
					 * insuredinfoMap .put(dt.getString(i, "InputCode"),
					 * subInfoMap);
					 */
					hourseArr.put(subInfoMap);
				}

			}
			appitemArr.put(appitemMap);
			// appntArr.put(appntinfoMap);
			// insredArr.put(insuredinfoMap);

			Map<String, Object> applicationMap = new HashMap<String, Object>();

			applicationMap.put("APPITEM", appitemArr);
			applicationMap.put("APPNT", appntArr);
			applicationMap.put("INSURED", insredArr);
			applicationMap.put("HOURSE", hourseArr);

			applicationTempMap.put("USER", "kaixinbao");
			applicationTempMap.put("REQUESTTYPE", "KXBJRT0011");
			applicationTempMap.put("STATYS", "true");
			applicationTempMap.put("RESULTS", applicationMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			arrayList.add("G000001");
			return WapErrorUtil.dealErrorInfo(arrayList, "KXBJRT0011", null);
		}

		return applicationTempMap;
	}

	/**
	 * 自动注册，联合登录绑定信息
	 * 
	 * @param old
	 */
	public static void dealUnionLogin(SDOrder sdorder, TradeInformation old,
			Map<String, Object> mServiceMap, String openID, String bindType) {
		// QQ绑定处理
		AutoRegister ar = new AutoRegister();
		ar.userRegistedCheck(sdorder, sdorder.getOrderSn(), old);
		BindInfoForLoginService bindInfoForLoginService = (BindInfoForLoginService) mServiceMap
				.get("BindInfoForLoginService");
		MemberService memberService = (MemberService) mServiceMap
				.get("MemberService");
		Member mem = new Member();
		mem.setEmail(sdorder.getSdinformationSet().iterator().next()
				.getSdinformationappntSet().iterator().next()
				.getApplicantMail());
		mem.setMobileNO(sdorder.getSdinformationSet().iterator().next()
				.getSdinformationappntSet().iterator().next()
				.getApplicantMobile());
		Member member = memberService.getUser(mem);
		BindInfoForLogin bindinfo = bindInfoForLoginService
				.getBindInfoForLoginByOpenID(openID);
		String bEmail = bindinfo.getKxbUserEmail();
		if ("".equals(bEmail) || bEmail == null) {
			// 未做绑定处理
			bindinfo.setmMember(member);
			bindinfo.setKxbUserEmail(member.getEmail());
			bindinfo.setKxbUserPhone(member.getMobileNO());
			bindinfo.setComCode(bindType);
			bindInfoForLoginService.update(bindinfo);
			member.setmBindInfoForLogin(bindinfo);
			memberService.update(member);
		} else if (!bindinfo.getKxbUserEmail().equals(member.getEmail())
				|| !bindinfo.getKxbUserPhone().equals(member.getMobileNO())) {
			// 绑定邮箱与投保人邮箱不一致,处理保单归属
			Member member_new = bindinfo.getmMember();
			Transaction trans = new Transaction();
			trans.add(new QueryBuilder(
					"update sdorders set memberid=? where ordersn=?",
					member_new.getId(), sdorder.getOrderSn()));
			if (!trans.commit()) {
				logger.error("orderSn：{}归属MemberID：{}没有成功！",sdorder.getOrderSn(), member_new.getId());
			}
		}
	}

	public static void main(String[] args) {
		// checkOrderNumber("wptest0000000041");
		BigDecimal tBigDecimal = new BigDecimal("5").divide(
				new BigDecimal("4"), BigDecimal.ROUND_HALF_DOWN);
//		System.out.println(tBigDecimal);

	}

}
