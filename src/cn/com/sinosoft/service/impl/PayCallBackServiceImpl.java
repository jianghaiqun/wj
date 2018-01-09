/**
 * Project Name:wj
 * File Name:PayCallBackServiceImpl.java
 * Package Name:cn.com.sinosoft.service.impl
 * Date:2016年6月30日下午2:17:29
 * Copyright (c) 2016, www.kaixinbao.com All Rights Reserved.
 *
 */

package cn.com.sinosoft.service.impl;

import cn.com.sinosoft.action.shop.CpsAction;
import cn.com.sinosoft.action.shop.MemberCookieUtil;
import cn.com.sinosoft.action.shop.OrderConfigNewAction;
import cn.com.sinosoft.common.email.MessageConstant;
import cn.com.sinosoft.entity.BindInfoForLogin;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.SDInformation;
import cn.com.sinosoft.entity.SDInformationAppnt;
import cn.com.sinosoft.entity.SDInformationInsured;
import cn.com.sinosoft.entity.SDOrder;
import cn.com.sinosoft.entity.SDOrder.SDOrderStatus;
import cn.com.sinosoft.entity.SDOrder.SDPayStatus;
import cn.com.sinosoft.entity.TradeInfo;
import cn.com.sinosoft.entity.TradeInformation;
import cn.com.sinosoft.entity.Wxbind;
import cn.com.sinosoft.service.BindInfoForLoginService;
import cn.com.sinosoft.service.CouponInfoService;
import cn.com.sinosoft.service.CpsProductBuyService;
import cn.com.sinosoft.service.GiftClassifyService;
import cn.com.sinosoft.service.InsuredCompanyReturnDataService;
import cn.com.sinosoft.service.MemberChannelService;
import cn.com.sinosoft.service.MemberService;
import cn.com.sinosoft.service.PayBaseService;
import cn.com.sinosoft.service.PayCallBackService;
import cn.com.sinosoft.service.RefundBaseService;
import cn.com.sinosoft.service.SDInformationRiskTypeService;
import cn.com.sinosoft.service.SDInformationService;
import cn.com.sinosoft.service.SDOrderService;
import cn.com.sinosoft.service.SDShoppingCartService;
import cn.com.sinosoft.service.TradeInfoService;
import cn.com.sinosoft.service.TradeInformationService;
import cn.com.sinosoft.service.WxbindService;
import cn.com.sinosoft.util.Constant;
import cn.com.sinosoft.util.RedisConsts;

import com.finance.util.JedisCommonUtil;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.cms.resend.ResendMain;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.servlets.TBContInsureServlet;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.framework.utility.WeixiManage;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.inter.ActivityCalculate;
import com.sinosoft.inter.ActivityGiveDetail;
import com.sinosoft.inter.MailAction;
import com.sinosoft.jdt.InsureTransferNew;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.points.IntegralConstant;
import com.sinosoft.points.PointsCalculate;
import com.sinosoft.schema.SDIntCalendarSchema;
import com.sinosoft.schema.TradeInfoSchema;
import com.sinosoft.schema.TradeSummaryInfoSchema;
import com.sinosoft.weixin.WeiXinCommon;
import com.wangjin.optimize.register.AutoRegister;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * ClassName:PayCallBackServiceImpl <br/>
 * Function:TODO ADD 支付成功回调. <br/>
 * Date:2016年6月30日 下午2:17:29 <br/>
 *
 * @author:郭斌
 */
@org.springframework.stereotype.Service("payCallBackService")
public class PayCallBackServiceImpl implements PayCallBackService {

	private static final Logger logger = LoggerFactory.getLogger(PayCallBackServiceImpl.class);

	@Resource
	protected PayBaseService payService;
	@Resource
	protected MemberChannelService memberChannelService;
	@Resource
	protected InsuredCompanyReturnDataService tInsuredCompanyReturnDataService;
	@Resource
	protected TradeInformationService tradeInformationService;
	@Resource
	protected RefundBaseService refundBaseService;
	@Resource
	protected SDInformationService sdinformationService;
	@Resource
	protected MemberService memberService;
	@Resource
	protected BindInfoForLoginService bindInfoForLoginService;
	@Resource
	protected CouponInfoService couponInfoService;
	@Resource
	protected TradeInfoService tradeInfoService;
	@Resource
	private GiftClassifyService giftClassifyService;
	@Resource
	protected SDInformationRiskTypeService risktypeService;
	@Resource
	protected WxbindService wxbindService;

	private List<String> productIdList = new ArrayList<String>();

	// 订单号
	private String orderSn;
	// 支付金额
	private String payAmount;
//	// 支付类型
//	private String _payType;
//	// 交易流水号
//	private String _TrxId;
//	// 签名数据
//	private String _ChkValue;
//	// 记录错误信息
//	private String _ErrMsg;
//	// 交易结果返回类型
//	private String _Btype;
//	// 支付流水号
//	private String _paySn;
	private String usepoint = "";
	private String typeFlag = "";
	private String jrhsURL = "";// 代理人工程路径
	private PrintWriter out;

	@Resource
	private SDShoppingCartService sdshopCartService;
	@Resource
	protected SDOrderService sdorderService;
	
	/**
	 * 金融界回调判断结果显示
	 * 
	 * @param orderSn
	 */
	public void getNewTypeFlag(String paySn) {

		String ss = "SELECT a.channelCode,a.typeFlag FROM sdorderitem a,sdorders b WHERE a.ordersn=b.ordersn and b.paySn=? LIMIT 1";
		DataTable dt = new QueryBuilder(ss, paySn).executeDataTable();
		try {
			if (dt.getRowCount() > 0) {
				String channelCode = dt.getString(0, 0);
				this.typeFlag = dt.getString(0, 1);
				if (StringUtil.isNotEmpty(channelCode)) {
					jrhsURL = sdorderService.getJRHSURLByConfig(channelCode);
				}
			}
		} catch (Exception e) {
			logger.error("金融界回调判断结果显示-支付回调发生异常!" + e.getMessage(), e);
		}

	}
	
	/**
	 * 支付主方法.
	 */
	public String doPay(String paySn, String payAmt, String TrxId, String ChkValue, String Btype,
			String payType, String openId, HttpServletRequest request) {
		String[] payArr = {paySn, payType, TrxId, ChkValue};
		logger.info("支付成功后后台回调参数：paySn:{}|TrxId:{}|payAmount:{}|payType:{}|Btype:{}");
		if (!StringUtil.isEmpty(paySn)) {
			HttpServletResponse response = ServletActionContext.getResponse();
			try {
				out = response.getWriter();
			} catch (IOException e2) {
				logger.error("支付成功回调获得响应对象发生异常！" + e2.getMessage(), e2);
			}
			// 理财险
			if ("zjzf".equals(payType)) {
				String dealResult = dealZjzf(paySn, request);
				if (StringUtil.isNotEmpty(dealResult)) {
					return dealResult;
				}
			}
			/**
			 * 0、支付成功-删除待支付邮件.
			 */
			MailAction.deleteNoPaymentMail(paySn);
			/**
			 * 1、货运保操作
			 */
			// 货运保回调显示
			this.getNewTypeFlag(paySn);
			/**
			 * 2、支付类型判断
			 */
			TrxId = dealPayType(TrxId, payType, payAmt);
			/**
			 * 3、获取交易订单、校验等处理
			 */
			List<SDOrder> sdorderList = dealTradeOrder(payArr);
			if (sdorderList == null) {
				return "ERROR";
			}
			//校验订单状态是否正确
			String dealResult = checkOrderStatus(sdorderList, paySn, payType);
			if (StringUtil.isNotEmpty(dealResult)) {
				return dealResult;
			}
			
			/**
			 * 4、是否是理财险
			 */
			boolean financeFlag = isFinance(sdorderList);
			
			/**
			 * 5、处理积分
			 */
			if (!financeFlag) {
				// 优惠拆分
				ActivityCalculate.activityeSplit(paySn, sdorderList.get(0).getChannelsn());

				ActivityCalculate.TransactionDealIntegral(paySn, sdorderList.get(0).getChannelsn());
			} else {
				QueryBuilder qb = new QueryBuilder("update SDInformationRiskType set couponValue=?,integralValue=?,activityValue=?,payPrice=?  where orderSn=?");
				qb.add("0.00");// 保单优惠券优惠金额
				qb.add("0.00");// 保单积分抵值金额
				qb.add("0.00");// 保单活动优惠金额-满减、折扣
				qb.add(payAmount);// 保单实际支付金额
				qb.add(sdorderList.get(0).getOrderSn());// 保单实际支付金额
				
				qb.executeNoQuery();
			}
			
			/**
			 * 6、处理会员
			 */
			// 是否已经注册 上面参数是是否登录 map中包括 是否注册和注册密码
			Map<String, Object> registerMap = new HashMap<String, Object>();
			Member member = dealMember(sdorderList, paySn, financeFlag, registerMap, request);
			String memberID = "";
			if (member != null) {
				memberID = member.getId();
			}
			
			/**
			 * 7、将用户可用积分扣除，并记录积分记录表
			 */
			if (!financeFlag && member != null) {
				dealResult = dealUsePoint(paySn, member, Btype, payType, request);
				if (StringUtil.isNotEmpty(dealResult)) {
					return dealResult;
				}
			}
			
			/**
			 * 8、处理购物车、邮件和短信通知等一系列处理逻辑
			 */
			// 删除购物车中的数据
			deleteShopCartInfo(sdorderList);
			// 邮件和短信通知
			dealEmailSms(sdorderList, member, financeFlag, registerMap, request);
			// 承保处理
			dealResult = dealInsured(sdorderList, paySn, payType, request);
			if (StringUtil.isNotEmpty(dealResult)) {
				return dealResult;
			}
			
			/**
			 * 9、处理优惠活动一系列逻辑
			 */
			if (!financeFlag) {
				ActivityCalculate.TransactionDeal(sdorderList, memberID, sdorderList.get(0).getChannelsn());
			}
			/**
			 * 10、处理分销联盟购买订单
			 */
			dealCpsOrder(sdorderList);
			/**
			 * 11、微信账户与用户ID绑定
			 */
			dealWXbind(openId, memberID);
			/**
			 * 12、调用结算中心接口
			 */
			callFCContService(sdorderList);
			
			// 判断是否为前台返回.
			if ("1".equals(Btype)) {
				return "runjdt";
			} 
			else if ("zjzf".equals(payType)) {
				return "success";
			}
			else {
				out.print(sendToMessage(payType));
			}
		} else {
			String _ErrMsg = "严重错误,支付后台支付流水号为空!请即时联系管理员! 支付方式：" + payType + "|订单金额:" + payAmt;
			sdorderService.sendErrorMailByPaySn(paySn, _ErrMsg, "", request);
			logger.error("支付后台出现异常，支付流水号为 :{}", paySn);
			return "ERROR";
		}
		return null;
	}

	/**
	 * 理财险的处理
	 * @param paySn
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String dealZjzf(String paySn, HttpServletRequest request) {
		try {
			List<SDOrder> orders = sdorderService.getOrderByPaySn(paySn);
			Map<String, Object> insureResult = null;
			try {
				// 调用经代通发送报文
				insureResult = this.callInsureTransfer(orders.get(0), null, orders.get(0).getOrderSn(),request);
				//通过订单号查询订单信息，发送到保单回调第三方
				payService.sendPolicyToReceiver(orders.get(0).getOrderSn());
				
			} catch (Exception e) {
				logger.error("支付成功后经代通发送报文发生异常" + e.getMessage(), e);
				return "error:" + ":出单异常！";
			}
			if (null == insureResult) {
				return "error:" + ":出单异常！";
			}
			if (Constant.FAIL.equals(insureResult.get(Constant.STATUS))) {
				Map<String, Object> returnData = (Map<String, Object>) insureResult.get(Constant.DATA);
	
				if ("222222".equals(returnData.get("PA_RSLT_CODE"))) {
					// 订单状态处理中
					String updateSql="update sdorders t set t.orderStatus="+SDOrderStatus.processe.ordinal()+" where t.orderSn='"+orders.get(0).getOrderSn()+"'";
					QueryBuilder updateQb = new QueryBuilder(updateSql);
					updateQb.executeNoQuery();
					return "wait:处理中";
				} else {
					String _ErrMsg = String.valueOf(returnData.get("PA_RSLT_MESG"));
					return "error:" + _ErrMsg;
				}
			}
		} catch(Exception e) {
			logger.error("支付成功，直接支付处理异常-dealZjzf：paySn-"+paySn + e.getMessage(), e);
			return "error:" + ":出单异常！";
		}
		return "";
	}

	/**
	 * 不同的支付类型的特殊处理
	 * @param TrxId 支付流水号
	 * @param payType 支付类型
	 * @param payAmt 支付金额
	 * @return 支付流水号
	 */
	private String dealPayType(String TrxId, String payType, String payAmt) {
		// 如果为测试支付，生成测试支付流水号;
		if ("test".startsWith(payType)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			TrxId = "test" + sdf.format(new Date());
		}
		// 财付通特殊转钱.银联金额单位转换 分->元
		if ("cft".startsWith(payType) || "ylzf".startsWith(payType) || "wx".startsWith(payType)
				|| "tlzf".startsWith(payType)) {
			payAmount = String.valueOf(new BigDecimal(payAmt).divide(new BigDecimal("100")));
		} else {
			payAmount = String.valueOf(new BigDecimal(payAmt));
		}
		
		return TrxId;
	}
	
	/**
	 * 获取交易订单、校验等处理
	 * @param payArr {paySn, payType, TrxId, ChkValue}
	 * @param payType
	 * @return
	 */
	private List<SDOrder> dealTradeOrder(String[] payArr) {
		// 根据交易信息缓存表，通过支付流水号查询订单号，再用订单号查询订单
		try {
			List<SDOrder> sdorderList = null;
			TradeInfo tradeInfo = tradeInfoService.getTradeInfoByPaySn(payArr[0]);
			if (tradeInfo != null) {
				usepoint = tradeInfo.getIntegral();
				// 获得订单号（逗号分割）
				String orderSns = tradeInfo.getOrderSn().replace(",", "','");
				sdorderList = sdorderService.getOrderByOrderSns(orderSns);
				// 根据交易信息缓存表中的记录，回写更新tradeinfomation表和添加tradesummaryinfo记录
				dealTradeInfo(payArr, sdorderList, tradeInfo);
				// 删除责任临时信息
				deleteDutyTemp(orderSns);
				
			} else {
				logger.warn("支付成功后回调，paysn与交易信息缓存表tradeinfo中数据不一致，paysn为：{}", payArr[0]);
				// 交易信息缓存表中数据不存在，通过paysn直接查询订单表
				sdorderList = sdorderService.getOrderByPaySn(PubFun.getPaySnUnion(payArr[0]));
			}
			if (sdorderList == null || sdorderList.size() == 0) {
				logger.error("支付成功回调查询订单失败，paysn为：{}", payArr[0]);
				return null;
			}
			
			return sdorderList;
		} catch (Exception e) {
			logger.error("支付回调查询交易信息缓存表tradeinfo失败，支付流水号为：" + payArr[0] + e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 根据交易信息缓存表中的记录，回写更新tradeinfomation表和添加tradesummaryinfo记录
	 * @param payArr {paySn, payType, TrxId, ChkValue}
	 * @param sdorderList
	 * @param tradeInfo
	 */
	private void dealTradeInfo(String[] payArr, List<SDOrder> sdorderList, TradeInfo tradeInfo) {
		int count = new QueryBuilder("select count(1) from tradesummaryinfo where paysn=?", payArr[0]).executeInt();
		if (count > 0) {
			new QueryBuilder("delete from tradesummaryinfo where paysn=?", payArr[0]).executeNoQuery();
		}
		writeBackPayInfo(payArr, tradeInfo, sdorderList);
	}
	
	/**
	 * 校验订单状态是否正确
	 * @param sdorderList
	 * @param paySn
	 * @param payType
	 * @return
	 */
	private String checkOrderStatus(List<SDOrder> sdorderList, String paySn, String payType) {
		for (SDOrder sorder : sdorderList) {
			// 6.判断该单是否以支付.
			if (sorder.getOrderStatus() == Enum.valueOf(SDOrderStatus.class, "paid")) {
				logger.info("订单已完成支付! 订单号为：{};paySn：{}", sorder.getOrderSn(), paySn);
				return "success";
			}
			if (sorder.getOrderStatus() == Enum.valueOf(SDOrderStatus.class, "cancel")
					|| sorder.getOrderStatus() == Enum.valueOf(SDOrderStatus.class, "partcancel")) {
				logger.info("订单已经撤单或有撤单! paySn：{}", paySn);
				return "runjdt";
			}
			if (sorder.getOrderStatus() == Enum.valueOf(SDOrderStatus.class, "surrender")
					|| sorder.getOrderStatus() == Enum.valueOf(SDOrderStatus.class, "partsurrender")) {
				logger.info("订单已经退保或有退保! paySn：{}", paySn);
				return "runjdt";
			}
			if (sorder.getOrderStatus() == Enum.valueOf(SDOrderStatus.class, "change")
					|| sorder.getOrderStatus() == Enum.valueOf(SDOrderStatus.class, "partchange")) {
				logger.info("订单已经作废或有作废! paySn：{}", paySn);
				return "runjdt";
			}
			// 订单状态更新
			String orderSn = sorder.getOrderSn();
			String updateSql="update sdorders t set t.paySn='"+paySn
					+"',t.offsetPoint="+usepoint+",t.payType='"+payType+"',t.payStatus="+SDPayStatus.paid.ordinal()
					+",t.orderStatus="+SDOrderStatus.paid.ordinal()+" where t.orderSn='"+orderSn+"'";
			QueryBuilder updateQb = new QueryBuilder(updateSql);
			updateQb.executeNoQuery();
		}
		return "";
	}
	
	/**
	 * 判断是否是理财险
	 * @param sdorderList
	 * @return
	 */
	private boolean isFinance(List<SDOrder> sdorderList) {
		String riskCode = sdorderList.get(0).getSdinformationSet().iterator().next().getProductId();
		String financProductsIds = Config.getValue("LicaiBaoxianProducts");//理财产品ids
		return financProductsIds.contains(riskCode);
	}
	
	/**
	 * 处理会员
	 * @param sdorderList
	 * @param paySn
	 * @param financeFlag
	 * @param request
	 * @return
	 */
	private Member dealMember(List<SDOrder> sdorderList, String paySn, boolean financeFlag, Map<String, Object> registerMap, HttpServletRequest request) {
		SDOrder sorder = sdorderList.get(0);
		String memberID = "";
		boolean registerFlag = false;// 是否是未登录购买，false:不是

		if (StringUtil.isNotEmpty(sorder.getMemberId())) {
			memberID = sorder.getMemberId();
		} else {
			memberID = request.getSession().getAttribute(Member.LOGIN_MEMBER_ID_SESSION_NAME) + "";
		}
		// 处理生产环境，联合登录订单无法绑定会员的问题，原因：支付后session重置，获取不到任何信息
		if (paySn.endsWith("D") || paySn.endsWith("00")) {
			TradeInformation information = tradeInformationService.getTradeInformationByOrdSn(sorder.getOrderSn());
			if (StringUtil.isEmpty(memberID)) {
				registerFlag = true;
				AutoRegister ar = new AutoRegister();
				Map<String, Object> returnMap = ar.userRegistedCheck(sorder, sorder.getOrderSn(), information);
				if (returnMap != null) {
					registerMap.putAll(returnMap);
				}
			} else if ("tencent".equals(memberID)) {
				registerFlag = true;
				dealUnionLogin(sorder, information, request);
			} 
		}
		// 实时查询订单表中数据，获取memberid
		DataTable dt_memberid = new QueryBuilder("select memberid from sdorders where ordersn=?", sorder.getOrderSn())
				.executeDataTable();
		memberID = dt_memberid.getString(0, 0);
		
		// 积分转增用户表
		if (!financeFlag) {
			ActivityCalculate.transPointToMember(paySn);
		}
		
		// 订单支付成功后邮件通知会员
		if (StringUtil.isNotEmpty(memberID) && !registerFlag) {
			LoginSuccessPay(sdorderList, request, memberID);
		}
		Member member = null;
		// 订单号是否为空
		if (StringUtil.isEmpty(memberID)) {
			logger.info("支付成功回调memberid为空，支付流水号为：{}", paySn);
		} else {
			member = memberService.get(memberID);
		}
		
		return member;
	}

	/**
	 * 将用户可用积分扣除，并记录积分记录表
	 * @param paySn
	 * @param member
	 * @param Btype
	 * @param payType
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String dealUsePoint(String paySn, Member member, String Btype, String payType, HttpServletRequest request) {
		try {
			String memberID = member.getId();
			DataTable dt_trdadesummary = new QueryBuilder("select integral from tradeinfo where paysn=?", paySn)
					.executeDataTable();
			BigDecimal integral = new BigDecimal("0");
			if (dt_trdadesummary.getRowCount() > 0) {
				if (StringUtil.isNotEmpty(dt_trdadesummary.getString(0, 0))) {
					integral = new BigDecimal(dt_trdadesummary.getString(0, 0));
				}
			} else {
				logger.error("支付成功回调时,处理使用积分情况时，查询支付记录表tradeinfo失败！交易流水号为：{}", paySn);
			}
			if (integral.compareTo(new BigDecimal("0")) == 1) {
				QueryBuilder qb = new QueryBuilder(
						"select currentValidatePoint,aboutToExpirePoints,usedPoint from member where id=?");
				qb.add(memberID);
				DataTable dt_point = qb.executeDataTable();
				if (dt_point.getRowCount() > 0) {
					String point = dt_point.getString(0, 0);
					if (StringUtil.isEmpty(point)) {
						point = "0";
					}
					BigDecimal member_point = new BigDecimal(point);
					if (member_point.compareTo(integral) != -1) {
						String aboutToExpirePoints = dt_point.getString(0, 1);
						String usedPoint = dt_point.getString(0, 2);
						if (StringUtil.isEmpty(usedPoint)) {
							usedPoint = "0";
						}
						if (StringUtil.isEmpty(aboutToExpirePoints)) {
							aboutToExpirePoints = "0";
						}
						BigDecimal member_usedPoint = new BigDecimal(usedPoint);
						// 更新可用积分
						QueryBuilder qb_point = new QueryBuilder(
								"update member set currentValidatePoint =?,aboutToExpirePoints=?,usedPoint=? where id=?");
						qb_point.add(String.valueOf(member_point.subtract(integral)));
						if (!"0".equals(aboutToExpirePoints)) {
							int intUsedPoint = 0;
							BigDecimal bigAboutToExpirePoints = new BigDecimal(aboutToExpirePoints);
							if (integral.compareTo(bigAboutToExpirePoints) == 1) {
								intUsedPoint = integral.subtract(bigAboutToExpirePoints).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
								bigAboutToExpirePoints = new BigDecimal("0");
							} else {
								bigAboutToExpirePoints = bigAboutToExpirePoints.subtract(integral).setScale(0, BigDecimal.ROUND_HALF_UP);
							}
							String now = PubFun.getCurrent();
							String operationDate = new QueryBuilder("select operationDate from pointOverTimeRecord where memberId=?", memberID).executeString();
							if (StringUtil.isEmpty(operationDate)) {
								new QueryBuilder(" insert into pointOverTimeRecord values (?,?,now())", memberID, intUsedPoint).executeNoQuery();
							} else {
								if (now.compareTo(operationDate) >= 0) {
									new QueryBuilder(" update pointOverTimeRecord set usedPoints=usedPoints+?,operationDate=now() where memberId=?", intUsedPoint, memberID).executeNoQuery();
								} else if (intUsedPoint > 0) {
									new QueryBuilder(" update pointOverTimeRecord set usedPoints=usedPoints+? where memberId=?", intUsedPoint, memberID).executeNoQuery();
								}
							}
							qb_point.add(bigAboutToExpirePoints.toString());
						} else {
							qb_point.add(aboutToExpirePoints);
						}
						
						qb_point.add(member_usedPoint.add(integral));
						qb_point.add(memberID);
						qb_point.executeNoQuery();
						// 记录流水表
						SDIntCalendarSchema tSDIntCalendarSchema = new SDIntCalendarSchema();
						tSDIntCalendarSchema.setID(NoUtil.getMaxID("IntID") + "");
						tSDIntCalendarSchema.setMemberId(memberID);// 会员号
						tSDIntCalendarSchema.setIntegral(String.valueOf(integral));// 积分值
						tSDIntCalendarSchema.setSource(IntegralConstant.POINT_SOURCE_WORTH);// 积分抵值
						tSDIntCalendarSchema.setBusinessid(paySn);// 交易流水号
						tSDIntCalendarSchema.setManner("1");// 表示支出
						tSDIntCalendarSchema.setStatus("0"); // 正常
						Map<String, Object> param = new HashMap<String, Object>();
						param.put("PointsGive", "01");
						try {
							Map<String, Object> map = new PointsCalculate().pointsManage(
									IntegralConstant.POINT_SEARCH, "2",
									param);
							List<Map<String, Object>> list = (List<Map<String, Object>>) map
									.get(IntegralConstant.DATA);
							if (list.size() > 0) {
								Map<String, Object> map_data = list.get(0);
								double d1 = integral.doubleValue()
										/ Double.parseDouble(Config.getValue("PointScalerUnit"));

								tSDIntCalendarSchema.setDescription(new ActivityGiveDetail().formatDouble(d1, 1)
										+ "元 "
										+ String.valueOf(map_data.get("PointDes")) + " 交易号：" + paySn);
							} else {
								tSDIntCalendarSchema.setDescription("购买产品积分抵值");
								logger.info("购买产品积分抵值查询规则无数据，");
							}
						} catch (Exception e) {
							logger.error(e.getMessage(), e);
						}
						tSDIntCalendarSchema.setCreateDate(PubFun.getCurrentDate());
						tSDIntCalendarSchema.setCreateTime(PubFun.getCurrentTime());
						tSDIntCalendarSchema.insert();
						// 用户消耗大于等于500积分时，给用户发送积分扣除提醒
						if (integral.compareTo(new BigDecimal("500")) >= 0) {
							if (member != null && StringUtil.isNotEmpty(member.getEmail())) {
								// 发送积分扣除提醒邮件.
								Map<String, Object> map = new HashMap<String, Object>();
								map.put("MemberEmail", member.getEmail());
								map.put("StandardDate", PubFun.getCurrentDateStandard());
								map.put("UsedIntegral", integral.toString());
								map.put("memberId",member.getId());
								map.put(MessageConstant.PARAM_SHOW_ACTIVITY_PRODUCT,"1");
								ActionUtil.sendMail("wj00130", member.getEmail(), map);
							}

						}
					} else {
						logger.warn("用户积分不足以支付此次支付！交易流水号为：{}", paySn);
						String _ErrMsg = "用户积分不足以支付此次支付！账户剩余积分：" + member_point.toString() + ",使用积分：" + integral.toString();
						sdorderService.sendErrorMailByPaySn(paySn, _ErrMsg, "", request);
						// 判断是否为前台返回.
						if ("1".equals(Btype)) {
							return "runjdt";
						} else {
							out.print(sendToMessage(payType));
						}
					}
				}
			}
			
			return "";
		} catch (Exception e) {
			logger.error("将用户可用积分扣除，并记录积分记录表处理异常:" + paySn + e.getMessage(), e);
			String _ErrMsg = "将用户可用积分扣除，并记录积分记录表异常:" + paySn + ",异常信息" + e.getMessage();
			sdorderService.sendErrorMailByPaySn(paySn, _ErrMsg, "", request);
			return "ERROR";
		}
	}
	
	private void deleteShopCartInfo(List<SDOrder> sdorderList) {
		
		if (sdorderList != null && sdorderList.size() > 0) {
			String ordersns = "";
			try {
				for (SDOrder order : sdorderList) {
					ordersns += ("," + order.getOrderSn());
				}
				ordersns = (ordersns.substring(1)).replace(",", "','");
				StringBuffer sb = new StringBuffer();
				sb.append("delete from SDShoppingCart where orderSn in ('");
				sb.append(ordersns+"')");
				new QueryBuilder(sb.toString()).executeNoQuery();
			} catch(Exception e) {
				logger.error("支付成功后购物车删除订单错误！订单号："+ordersns.replace("'", "") + e.getMessage(), e);
			}
		}
	}

	
	/**
	 * 处理邮件和短信通知
	 * 
	 * @param sdorderList
	 * @param member
	 * @param financeFlag
	 * @param returnMap
	 * @param request
	 */
	private void dealEmailSms(List<SDOrder> sdorderList, Member member,
			boolean financeFlag, Map<String, Object> returnMap,
			HttpServletRequest request) {

		// 支付成功后短信通知会员
		sendMessage(sdorderList, request, returnMap, financeFlag);
	}
	
	/**
	 * 承保处理
	 * @param sdorderList
	 * @param member
	 * @param paySn
	 * @param payType
	 * @param request
	 * @return
	 */
	private String dealInsured(List<SDOrder> sdorderList, String paySn, String payType, HttpServletRequest request) {
		try {
			if (sdorderList != null && sdorderList.size() > 0) {
				for (SDOrder sdorder : sdorderList) {
					TradeInformation information = tradeInformationService.getTradeInformationByOrdSn(sdorder.getOrderSn());
					SDInformation sdi = sdorder.getSdinformationSet().iterator().next();
					productIdList.add(sdi.getProductId());
					try {
						// 向维析发送支付完成数据,在经代通调用之前,防止经代通调用失败
						this.sendWeixiData(sdorder, information, sdi.getProductName());
					} catch (Exception e) {
						logger.error("向维析发送支付完成数据时发生异常" + e.getMessage(), e);
					}
					try {
						// 调用经代通发送报文
						if (!"zjzf".equals(payType)) {
							try {
								// 调用经代通发送报文
								callInsureTransfer(sdorder, information, sdorder.getOrderSn(),request);
							} catch (Exception e) {
								logger.error("支付成功后经代通发送报文发生异常" + e.getMessage(), e);
							}

							// 出单回调-发到到第三方消息队列
							try {
								//通过订单号查询订单信息，发送到保单回调第三方
								logger.info("sendPolicyToReceiver：" + sdorder.getOrderSn());
								payService.sendPolicyToReceiver(sdorder.getOrderSn());
							} catch (Exception e) {
								logger.error("出单回调-发到到第三方消息队列出现异常=======payUrlService.sendPolicyDetailToReceiver" + e.getMessage(), e);
							}
						}
					} catch (Exception e) {
						logger.error("支付成功后经代通发送报文发生异常" + e.getMessage(), e);
					}
					// CPS信息推送
					dealCpsInfo(paySn, sdorder, information, request);
				}
				// 增加产品购买数量
				addProductSalesVolume();
			}
			return "";
		} catch (Exception e) {
			logger.error("支付成功回调出现异常:" + "程序出现异常 paySn:" + paySn + e.getMessage(), e);
			String _ErrMsg = "支付后台返回主程序出现异常:" + e.getMessage();
			sdorderService.sendErrorMailByPaySn(paySn, _ErrMsg, "", request);
			return "ERROR";
		}
	}
	
	/**
	 * CPS信息推送
	 * @param paySn
	 * @param sdorder
	 * @param information
	 * @param request
	 */
	private void dealCpsInfo(String paySn, SDOrder sdorder, TradeInformation information, HttpServletRequest request) {
		try {
			// CPS信息推送(现阶段只有‘59秒’处于合作状态)
			DataTable dt_cps = new QueryBuilder("SELECT * FROM cps WHERE cps.oo='04' AND cps.on=?",
					sdorder.getOrderSn()).executeDataTable();
			if (dt_cps != null && dt_cps.getRowCount() > 0) {
				String param = getCpsParam("04", sdorder, information, request);
				String url = Config.map_property.get("miao_send_url");
				sendCpsInfo(url, param);
			}
	
			// CPS推送
			Map<String, String> result_cpsInfo = CpsAction.queryOrders(sdorder.getOrderSn());
	
			if (result_cpsInfo != null) {
				String partners_id = result_cpsInfo.get("PartnersCode");
				if (StringUtil.isNotEmpty(CpsAction.isPartners(partners_id, "2"))) {
					Map<String, String> partnersInfo = CpsAction.queryPartnersInfo(partners_id);
					String sendStr = CpsAction.CpsParam(paySn, partnersInfo, result_cpsInfo, "send");
					String sendUrl = partnersInfo.get("SendUrl");
					if (StringUtil.isEmpty(sendUrl)) {
						logger.info("CPS sendOrderInfo error,Url is null.orderSn :{}, partnersID:{}", sdorder.getOrderSn(), partners_id);
					} else {
						logger.info("CPS推送数据:{}", sendStr);
						sendCpsInfo(sendUrl, "?data=" + sendStr);
					}
				}
			}
		} catch(Exception e) {
			logger.error("CPS推送数据异常！paySn:"+paySn + e.getMessage(), e);
		}
	}
	
	/**
	 * 处理微信账户与用户ID绑定
	 * @param openId
	 * @param memberID
	 */
	private void dealWXbind(String openId, String memberID) {
		if (StringUtil.isNotEmpty(openId) && StringUtil.isNotEmpty(memberID)) {
			try {
				// 判断用户是否关注微信公众账号
				String jsonInfo = WeiXinCommon.ajaxFromUserInfo(WeiXinCommon.ajaxtoken(), openId);
				JSONObject jsonObject = JSONObject.fromObject(jsonInfo);
				if (jsonObject.has("subscribe") && "0".equals(jsonObject.getString("subscribe"))) {
					return;
				}
				Wxbind wxbind = new Wxbind();
				DataTable db = new QueryBuilder("select MobileNO,Email from member where id =?", memberID).executeDataTable();
				if (db != null && db.getRowCount() > 0) {
					String mobileNo = db.getString(0, 0);
					String email = db.getString(0, 1);
					if (StringUtil.isNotEmpty(mobileNo)) {
						wxbind.setMemAccount(mobileNo);
					} else {
						wxbind.setMemAccount(email);
					}
					wxbind.setMemberId(memberID);
					wxbind.setOpenId(openId);
					wxbind.setRemark("微信支付绑定");

					wxbindService.bindOpenIdAndMemberId(wxbind);
				}
			} catch (Exception e) {
				logger.error("微信支付绑定异常：" + e.getMessage(), e);
			}
		}
	}
	
	/**
	 * 根据订单号删除责任临时信息
	 * 
	 * @param orderSns
	 */
	private void deleteDutyTemp(String orderSns) {
		try {
			Transaction ts = new Transaction();
			ts.add(new QueryBuilder("delete from SDInformationDutyTemp where OrderSn in ('" + orderSns + "')"));
			ts.commit();
		} catch(Exception e) {
			logger.error("支付成功删除责任临时表异常-deleteDutyTemp:"+orderSns + e.getMessage(), e);
		}
	}

	/**
	 * 通过支付中间表更新订单表. 影响字段:couponsn、offsetPoint. 优惠券更新，通过券号更新支付流水号.
	 * @param payArr {paySn, payType, TrxId, ChkValue}
	 * @param tradeInfo
	 */
	@SuppressWarnings("rawtypes")
	private void writeBackPayInfo(String[] payArr, TradeInfo tradeInfo, List<SDOrder> sdorderList) {

		Transaction trans = new Transaction();
		// 通过支付记录信息(券号/活动号,积分更新至订单).
		String newOrderSn = tradeInfo.getOrderSn().replace(",", "','");

		TradeInfoSchema tradeinfoschema = new TradeInfoSchema();
		tradeinfoschema.setpaySn(tradeInfo.getPaySn());
		// 定义时间格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date;
		try {
			date = sdf.parse(PubFun.getCurrent());
			// 更新TradeInfo表
			if (tradeinfoschema.fill()) {
				tradeinfoschema.setpayType(payArr[1]);
				// remark1 记录是否支付
				tradeinfoschema.setremark1("已支付");
				tradeinfoschema.setModifyDate(date);
				trans.add(tradeinfoschema, Transaction.UPDATE);
			}
			// 更新tradeinformation表
			java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			java.util.Date currentTime = new java.util.Date();
			QueryBuilder update_tradeinformation = new QueryBuilder(
					"update Tradeinformation set tradeCheckSeriNo=?,tradeserino=?,ReceiveDate=?,ReturnSign=?,TradeResult='0',PayStatus='1',"
							+ "payType=? where ordid in('" + newOrderSn + "')");
			update_tradeinformation.add(payArr[0]);
			update_tradeinformation.add(payArr[2]);
			update_tradeinformation.add(formatter.format(currentTime));
			update_tradeinformation.add(payArr[3]);
			update_tradeinformation.add(payArr[1]);

			trans.add(update_tradeinformation);
			// 更新tradesummaryinfo表
			TradeSummaryInfoSchema tradesummaryinfoschema = new TradeSummaryInfoSchema();
			tradesummaryinfoschema.setid(NoUtil.getMaxNo("TradeSummaryID", 11));
			tradesummaryinfoschema.setPaySn(tradeInfo.getPaySn());
			tradesummaryinfoschema.setTradeSn(payArr[2]);
			tradesummaryinfoschema.setTradeResult("0");
			tradesummaryinfoschema.setCouponSn(tradeInfo.getCouponSn());
			tradesummaryinfoschema.setOrderSns(tradeInfo.getOrderSn());
			// 活动优惠信息
			Map map = dealActivityData(sdorderList);
			if (StringUtil.isNotEmpty(String.valueOf(map.get("discount")))) {
				tradesummaryinfoschema.setActivitySumAmount(String.valueOf(map.get("discount")));
			}
			// 取得积分换算单位
			String PointScalerUnit = Config.getConfigValue("PointScalerUnit");
			// 积分
			BigDecimal pointValue = new BigDecimal(tradeInfo.getIntegral()).divide(new BigDecimal(PointScalerUnit), 2,
					BigDecimal.ROUND_DOWN);
			tradesummaryinfoschema.setPointSumAmount(String.valueOf(pointValue));
			// 优惠券优惠信息
			if (StringUtil.isNotEmpty(tradeInfo.getCouponSn()) && !("0".equals(tradeInfo.getCouponSn()))) {
				// DataTable dt_coupon = new
				// QueryBuilder("SELECT parvalue FROM  couponinfo WHERE couponsn=?",
				// tradeInfo.getCouponSn()).executeDataTable();
				tradesummaryinfoschema.setCouponSumAmount(tradeinfoschema.getremark2());
				trans.add(new QueryBuilder(
						"update  couponinfo  set status='1', payTime=now(),modifyDate=now(),orderSn=? WHERE couponsn=?",
						payArr[0], tradeInfo.getCouponSn()));
			}
			tradesummaryinfoschema.setPayType(payArr[1]);
			DataTable dt_paytypename = new QueryBuilder("SELECT description FROM paybase WHERE paytype=?", payArr[1])
					.executeDataTable();
			if (dt_paytypename.getRowCount() > 0) {
				tradesummaryinfoschema.setPayTypeName(dt_paytypename.getString(0, 0));
			} else {
				tradesummaryinfoschema.setPayTypeName("无支付名称");
			}
			// 总金额
			if (StringUtil.isNotEmpty(String.valueOf(map.get("totalAmount")))) {
				tradesummaryinfoschema.setTotalAmount(String.valueOf(map.get("totalAmount")));
			}
			// 支付金额
			tradesummaryinfoschema.setTradeAmount(payAmount);
			tradesummaryinfoschema.setCreateDate(date);
			trans.add(tradesummaryinfoschema, Transaction.INSERT);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		// 提交事务
		if (!trans.commit()) {
			logger.error("更新TradeInfo表时，提交事务发生异常！");
		}
	}

	/**
	 * 
	 * @Title: sendMessage
	 * @Description: TODO(支付成功后给投保人发送短信)
	 * @return void 返回类型
	 * @author zhangjing
	 */
	private void sendMessage(List<SDOrder> sdorderList, HttpServletRequest request, Map<String, Object> returnMap, boolean financeFlag) {

		try {
			if (sdorderList != null && sdorderList.size() >= 1) {
				for (SDOrder sdor : sdorderList) {
					String orSn = sdor.getOrderSn();
					Set<SDInformation> informationSet = sdor.getSdinformationSet();
					// cps_58bb不发送短信和邮件
					if (informationSet != null && informationSet.size() > 0 && (StringUtil.isNotEmpty(Config.getValue("NoSendChannelsn")) && Config.getValue("NoSendChannelsn").indexOf(sdor.getChannelsn()) == -1)) {
						SDInformation information = informationSet.iterator().next();
						Set<SDInformationAppnt> appntSet = information.getSdinformationappntSet();
						if (appntSet != null && appntSet.size() > 0) {
							SDInformationAppnt appnt = appntSet.iterator().next();
							if(Config.getValue("ZHDRIVING").indexOf(information.getProductId()) != -1){
								ActionUtil.sendSms("wj1028",appnt.getApplicantMobile(), information.getProductName());
							}else{
								Map<String, Object> map = new HashMap<String, Object>();
								map.put("orderSn", orSn);
								if (null != returnMap && !returnMap.isEmpty() && !(Boolean) returnMap.get("userisExist")) {
									ActionUtil.sendSms("wj00099", appnt.getApplicantMobile()
											, orSn+";"+returnMap.get("username")+";"+returnMap.get("password")+";");
								} else {
									ActionUtil.sendMessage("wj00091", map);
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	/**
	 * 回写成功标记.
	 * 
	 * @param payType
	 * @return
	 */
	public static String sendToMessage(String payType) {

		String mes = "";
		if ("ybzf".startsWith(payType)) {
			mes = "success";
		} else if ("cft".startsWith(payType)) {
			mes = "Success";
		} else {
			mes = "success";
		}
		return mes;
	}

	/**
	 * 下单后，冻结积分处理 sdorderList 订单
	 * 
	 * @param sdorderList
	 */

	/**
	 * 增加产品销量
	 */
	public void addProductSalesVolume() {
		try {
			Transaction trans = new Transaction();
			int length = productIdList.size();
			String sql = "select RelaID from zdcolumnvalue where columncode ='RiskCode' and textvalue= ?";
			DataTable dt;
			String condition = "";
			for (int i = 0; i < length; i++) {
				// 更新产品中心
				trans.add(new QueryBuilder("update femrisk set SalesVolume = (SalesVolume + 1) where RiskCode=?", productIdList
						.get(i)));
	
				trans.add(new QueryBuilder("update femriskb set SalesVolume = (SalesVolume + 1) where RiskCode=?", productIdList
						.get(i)));
	
				// 更新搜索产品销量数据同步表
				trans.add(new QueryBuilder("update sdsearchrelaproduct set SalesVolume = (SalesVolume + 1) where ProductID=?",
						productIdList.get(i)));
				condition = "";
				// 取得关联ID
				dt = new QueryBuilder(sql, productIdList.get(i)).executeDataTable();
				for (int j = 0; j < dt.getRowCount(); j++) {
					condition += dt.getString(j, 0);
					if (j != dt.getRowCount() - 1) {
						condition += ",";
					}
				}
				if (!StringUtil.isEmpty(condition)) {
					// 更新电商cms产品中心产品销量字段
					trans.add(new QueryBuilder("update zdcolumnvalue set TextValue = (TextValue + 1) where RelaID in ("
							+ condition + ") and columncode ='SalesVolume'"));
				}
			}
			if (!trans.commit()) {
				logger.error("产品中心产品增加销量失败");
			}else{
				updateSearchVolumeInRedis(productIdList);
			}
		} catch(Exception e) {
			logger.error("产品中心产品增加销量异常！"+productIdList + e.getMessage(), e);
		}
	}
	
	/**
	 * updateSearchVolumeInRedis:(更新redis中搜索产品销量). <br/>
	 * TODO(如果产品有多个计划，那么同时更新多个计划的销量).<br/>
	 * @author guozc
	 * @param productIds 多个产品id
	 */
	private void updateSearchVolumeInRedis(List<String> productIds){
		String cacheKey = null;//redis中key名
		String keyPrefix = RedisConsts.KEY_PREFIX_PRODUCTATTR;//key前缀
		String mapKey = RedisConsts.MAPKEY_PRODUCTATTR_SALESVLUME;//map key名
		int dbIndex = RedisConsts.DB_PRODUCT_ATTR;
		for (String productId : productIds) {
			cacheKey = keyPrefix + productId;
			// 更新本产品销量
			if(JedisCommonUtil.hExists(dbIndex, cacheKey, mapKey)){
				JedisCommonUtil.hincrBy(dbIndex, cacheKey, mapKey, 1);
				logger.info("支付成功：增加产品{}redis销量",productId);
			}
			// 更新同计划产品销量
			String splitRiskCode = new QueryBuilder("select SplitRiskCode from sdproduct where ProductID = ?",productId)
					.executeString();
			if (StringUtil.isNotEmpty(splitRiskCode)) {
				String[] src_plan = splitRiskCode.split(",");
				for (int j = 0; j < src_plan.length; j++) {
					String[] src = src_plan[j].split("-");
					String cProductId = src[0];
					if(!productId.equals(cProductId)){
						cacheKey = keyPrefix + cProductId;
						if(JedisCommonUtil.hExists(dbIndex, cacheKey, mapKey)){
							JedisCommonUtil.hincrBy(dbIndex, cacheKey, mapKey, 1);
							logger.info("支付成功：增加产品{}redis销量",cProductId);
						}
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * @Title: LoginSuccessPay
	 * @Description: TODO(订单支付成功后邮件通知会员)
	 * @return void 返回类型
	 * @author XXX
	 */
	private void LoginSuccessPay(List<SDOrder> sdorderList, HttpServletRequest request, String memberID) {

		try {
			Map<String, Object> map = new HashMap<String, Object>();
			List<String> orderSns = new ArrayList<String>();
			for (SDOrder sorder : sdorderList) {
				// cps_58bb不发送短信和邮件
				if(StringUtil.isNotEmpty(Config.getValue("NoSendChannelsn")) && Config.getValue("NoSendChannelsn").indexOf(sorder.getChannelsn()) == -1){
					orderSns.add(sorder.getOrderSn());
				}
			}
			map.put("orderSns", orderSns);
			ActionUtil.sendMessage("wj00016", map);
		} catch (Exception e) {
			logger.error("订单支付完成(已登录)出现异常: LoginSuccessPay方法出现异常" + e.getMessage(), e);
		}
	}

	/**
	 * 
	 * @Title: getCpsParam
	 * @Description: TODO( 获取CPS推送参数--渠道区分 04-59秒 05-今题网 06-我搜联盟)
	 * @return String 返回类型
	 * @author XXX
	 */
	private String getCpsParam(String flag, SDOrder sdorder, TradeInformation tradeInformation, HttpServletRequest request) {

		Cookie[] cookies = request.getCookies();
		String fnMiao_uid = "";
		String jinTi_extcode = "";
		String SiteId = "";

		if (cookies != null && cookies.length > 0) {
			for (Cookie c : cookies) {
				if ("fnMiao_uid".equals(c.getName())) {
					fnMiao_uid = c.getValue();
				} else if ("jinTi_extcode".equals(c.getName())) {
					jinTi_extcode = c.getValue();
				} else if ("SiteId".equals(c.getName())) {
					SiteId = c.getValue();
				}
			}
		}

		StringBuffer param = new StringBuffer();
		if ("04".equals(flag)) {
			param.append("?created=");
			param.append(DateUtil.toDateTimeString(sdorder.getCreateDate()));
			param.append("&order_id=");
			param.append(tradeInformation.getOrdID());
			param.append("&order_amount=");
			Double ordAmt = Double.parseDouble(tradeInformation.getOrdAmt());
			DecimalFormat df = new DecimalFormat("#0.00");
			param.append(df.format(ordAmt));
			param.append("&commission=");
			Double commission = ordAmt * 0.15;
			param.append(df.format(commission));
			param.append("&outer_code=");
			param.append(fnMiao_uid);
			param.append("&status=");
			// 01-交易完成
			param.append("01");

		} else if ("05".equals(flag)) {
			param.append("?orderID=");
			param.append(tradeInformation.getOrdID());
			param.append("&Amount=");
			Double ordAmt = Double.parseDouble(tradeInformation.getOrdAmt());
			DecimalFormat df = new DecimalFormat("#0.00");
			param.append(df.format(ordAmt));
			param.append("&extCode=");
			param.append(jinTi_extcode);
			param.append("&partnerUserName=");
			param.append("kaixinbao");
			param.append("&partnerAppkey=");
			param.append("BB01AE6E-E02B-4F2C-B2CC-041FD756A043");
			param.append("&op=");
			param.append("InsertOrder");

		}
		// “我搜”推广联盟推送订单
		else if ("06".equals(flag)) {
			param.append("?Uid=20");
			param.append("&SiteId=");
			param.append(SiteId);
			param.append("&On=");
			param.append(tradeInformation.getOrdID());
			// 与"我搜"技术人员协调之后传递商品信息的参数全为0
			param.append("&Pn=0");// 商品编号
			param.append("&Num=0");// 购买商品数量
			param.append("&Price=0");// 一个商品的单价
			param.append("&Dm=0");// 此商品金额

			param.append("&Fm=");// 此订单总金额
			Double ordAmt = Double.parseDouble(tradeInformation.getOrdAmt());
			DecimalFormat df = new DecimalFormat("#0.00");
			param.append(df.format(ordAmt));

			param.append("&Am=");// 此订单的总分成金额
			Double commission = ordAmt * 0.15;
			param.append(df.format(commission));

			param.append("&St=1");// 订单状态 (确认付款’用‘1’表示‘未付款’用 ‘2’表示‘已退款’ 用 ‘3’
			// 表示)

			param.append("&Sd=");// 下单时间
			param.append(DateUtil.toDateTimeString(tradeInformation.getCreateDate()));

			param.append("&Pd=");// 付款时间
			param.append(tradeInformation.getReceiveDate());

			param.append("&Encoding=UTF-8");// 编码方式

		}
		return param.toString();
	}

	/**
	 * CPS订单推送
	 * 
	 * @param url
	 *            推送地址
	 * @param param
	 *            推送参数
	 * @throws IOException
	 */
	public void sendCpsInfo(String url, String param) {

		HttpClient httpClient = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
		PostMethod post = new PostMethod();
		try {
			// 把参数按照UTF-8格式编码
			URI uri = new URI(param, false, "UTF-8");
			param = uri.toString();
			post = new PostMethod(url + param);
			httpClient.executeMethod(post);

		} catch (Exception e) {
			logger.error("支付成功后回调，第三方推广平台推送信息异常！" + e.getMessage(), e);
		} finally {
			post.releaseConnection();
		}
	}

	/**
	 * QQ联合登录绑定信息
	 * 
	 * @param old
	 */
	private void dealUnionLogin(SDOrder sdorder, TradeInformation old, HttpServletRequest request) {

		// QQ绑定处理
		AutoRegister ar = new AutoRegister();
		ar.userRegistedCheck(sdorder, sdorder.getOrderSn(), old);
		sdorder = sdorderService.get(sdorder.getId());
		Member mem = new Member();
		mem.setEmail(sdorder.getSdinformationSet().iterator().next().getSdinformationappntSet().iterator().next()
				.getApplicantMail());
		mem.setMobileNO(sdorder.getSdinformationSet().iterator().next().getSdinformationappntSet().iterator().next()
				.getApplicantMobile());
		Member member = this.memberService.getUser(mem);
		String loginbindId = "";
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie c : cookies) {
				if ("loginBindId".equals(c.getName())) {
					loginbindId = c.getValue();
					break;
				}
			}
		}
		if (!"".equals(loginbindId)) {
			BindInfoForLogin bindinfo = this.bindInfoForLoginService.load(loginbindId);
			if(bindinfo == null){
				logger.error("QQ联合登录bindinfo获取错误，可能因为cookie缓存造成");
				return;
			}
			String bEmail = bindinfo.getKxbUserEmail();
			String mobileno = bindinfo.getKxbUserPhone();
			if (("".equals(bEmail) || bEmail == null) && ("".equals(mobileno) || mobileno == null)) {// 未做绑定处理
				dealUnionLoginWhenMemberNull(mem.getEmail(),mem.getMobileNO(),loginbindId);
				/*if(member == null){
					dealUnionLoginWhenMemberNull(mem.getEmail(),mem.getMobileNO(),loginbindId);
				}else{//原逻辑 某些时候不好使
					bindinfo.setmMember(member);
					bindinfo.setKxbUserEmail(member.getEmail());
					bindinfo.setKxbUserPhone(member.getMobileNO());
					bindInfoForLoginService.update(bindinfo);
					member.setmBindInfoForLogin(bindinfo);
					this.memberService.update(member);
				}*/
			} else if (!bindinfo.getKxbUserEmail().equals(member.getEmail())
					|| !bindinfo.getKxbUserPhone().equals(member.getMobileNO())) {
				// 绑定邮箱与投保人邮箱不一致,处理保单归属
				Member member_new = bindinfo.getmMember();
				Transaction trans = new Transaction();
				trans.add(new QueryBuilder("update sdorders set memberid=? where ordersn=?", member_new.getId(), sdorder
						.getOrderSn()));
				if (!trans.commit()) {
					logger.error("orderSn：{}归属MemberID：{}没有成功！", sdorder.getOrderSn(), member_new.getId());
				}
			}
		}
	}

	private void dealUnionLoginWhenMemberNull(String email,String mobileNO,String loginbindId){
		String memberId = "";
		memberId = new QueryBuilder("SELECT id FROM member WHERE Mobileno= ?  Limit 1 ",mobileNO).executeString();
		String sqlWhere = ""; 
		if(StringUtil.isEmpty(memberId)){
			memberId = new QueryBuilder("SELECT id FROM member WHERE email= ? Limit 1 ",email).executeString();
			if(StringUtil.isEmpty(memberId)){
				logger.error("QQ联合登录绑定信息，未做绑定处理代码逻辑错误");
				return;
			}
		}
		sqlWhere = " where id = '"+memberId +"'";
		Transaction trans = new Transaction();
		trans.add(new QueryBuilder("update member set mBindInfoForLogin_id=? "+sqlWhere, loginbindId));
		trans.add(new QueryBuilder("update BindInfoForLogin set KxbUserEmail=? , KxbUserPhone=? where id = '"+loginbindId+ "'", email, mobileNO));
		if (!trans.commit()) {
			logger.error("联合登录绑定信息失败！");
		}
	}
	/**
	 * 调用经代通处理.
	 * 
	 * @param sorder
	 * @param old
	 * @param OrdId
	 */
	protected Map<String, Object> callInsureTransfer(SDOrder sorder, TradeInformation old, String OrdId, HttpServletRequest request) {

		// 调用横向接口
		List<String> UWUNSucess = sdorderService.checkUnderwriting(sorder, request);
		String comCode = "";
		Set<SDInformationInsured> sdinformationInsuredSet = null;
		SDInformation sdinformation = null;
		// SDInformationRiskType sdiRiskType = null;
		String appStatus = "";
		boolean flag = true;
		if (sorder != null) {
			Set<SDInformation> sdInformationSet = sorder.getSdinformationSet();
			for (SDInformation sdi : sdInformationSet) {
				sdinformation = sdi;
			}
			if (sdinformation != null) {
				comCode = sdinformation.getInsuranceCompany();
				sdinformationInsuredSet = sdinformation.getSdinformationinsuredSet();
				for (SDInformationInsured sdInsured : sdinformationInsuredSet) {
					String ordersn = sdInsured.getOrderSn();
					String recognizeeSn = sdInsured.getRecognizeeSn();
					String sql = "select appStatus,insureMsg from SDInformationRiskType where orderSn = ? and recognizeeSn = ?";
					DataTable dt = new QueryBuilder(sql, ordersn, recognizeeSn).executeDataTable();
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
				Map<String, Object> result = tInsureTransfer.callInsTransInterface(comCode, sorder.getOrderSn(), UWUNSucess);
				for (SDInformationInsured sdInsured : sdinformationInsuredSet) {
					String ordersn = sdInsured.getOrderSn();
					String recognizeeSn = sdInsured.getRecognizeeSn();
					String sql = "select appStatus,insureMsg from SDInformationRiskType where orderSn = ? and recognizeeSn = ?";
					DataTable dt = new QueryBuilder(sql, ordersn, recognizeeSn).executeDataTable();
					if (dt.getRowCount() > 0) {
						appStatus = dt.getString(0, 0);
						insureMsg = dt.getString(0, 1);
					} else {
						logger.warn("未查询到SDInformationRiskType表中订单相关信息");
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

					String _ErrMsg = "经代通失败:" + insureMsg;

					if (StringUtil.isEmpty(insureMsg)) {
						_ErrMsg = "经代通失败:未收到保险公司回写结果";
					}
					sdorderService.sendErrorMail(sorder.getOrderSn(), _ErrMsg, "", request);

					ResendMain.resendCacheAdd(sorder.getOrderSn(), comCode, _ErrMsg);
					// 理财险 同步返回
					String insureTypeCode = sdinformation.getInsuranceCompany();
					if (insureTypeCode.startsWith("2103") && Constant.FAIL.equals(result.get(Constant.STATUS))) {
						return PubFun.errMsg(null, result.get(Constant.DATA));
					}
					return PubFun.errMsg(_ErrMsg);
				} else if (flag) {
				}
			} catch (Exception e) {
				String _ErrMsg = "callInsureTransfer方法出现异常:" + e.getMessage();
				sdorderService.sendErrorMail(OrdId, _ErrMsg, "", request);
				logger.error("调用经代通处理出现异常: callInsureTransfer方法出现异常" + e.getMessage(), e);
				ResendMain.resendCacheAdd(sorder.getOrderSn(), comCode, _ErrMsg);
			}
		}
		return PubFun.sucMsg();
	}

	/**
	 * 
	 * @Title: dealActivityData
	 * @Description: TODO(计算活动的优惠金额和总金额)
	 * @return void 返回类型
	 * @author XXX
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map<String, String> dealActivityData(List<SDOrder> paramterList) {

		Map<String, String> map = new HashMap<String, String>();
		try {
			// 筛选优惠活动
			Map<String, Map<String, Object>> activity_product_info1 = ActivityCalculate.ProductShoppingCartInfo(
					paramterList, paramterList.get(0).getChannelsn(), true);
			// 总优惠金额
			BigDecimal discount = new BigDecimal("0");
			// 总金额
			BigDecimal totalAmount = new BigDecimal("0");
			// 遍历优惠信息Map
			Set keySet = activity_product_info1.keySet();
			for (Iterator it = keySet.iterator(); it.hasNext();) {
				// 活动号（包含“_no_activity”）
				String activitysn = String.valueOf(it.next());
				// 获取该活动关联的所有信息（包括活动信息，产品信息，优惠信息）
				Map<String, Object> map_info = activity_product_info1.get(activitysn);
				// 获取产品信息list
				Map<String, String> map_activityamont = (Map<String, String>) map_info.get("ActivityAmont");
				// 总优惠金额累计
				String discountamount = map_activityamont.get("DiscountAmount");
				discount = discount.add(new BigDecimal(discountamount));
				// 总优惠金额累计
				String TotalAmount = map_activityamont.get("TotalAmount");
				totalAmount = totalAmount.add(new BigDecimal(TotalAmount));
			}
			map.put("discount", String.valueOf(discount.setScale(2, BigDecimal.ROUND_HALF_UP)));
			map.put("totalAmount", String.valueOf(totalAmount.setScale(2, BigDecimal.ROUND_HALF_UP)));
		} catch (Exception e) {
			map.put("discount", "");
			map.put("totalAmount", "");
			logger.error("计算活动的优惠金额和总金额发生异常，活动信息为：" + e.getMessage(), e);
		}
		return map;
	}

	/**
	 * @Title: sendWeixiData
	 * @Description: TODO(维析发送数据)
	 * @return void 返回类型
	 * @author zhangjing
	 */
	public void sendWeixiData(SDOrder sdorder, TradeInformation information, String productname) {

		// 维析发送数据
		Map<String, String> map_weixi = new HashMap<String, String>();
		// flag 为1 发送page和action数据
		map_weixi.put("flag", "1");
		// 分组名称（及weixitrack.js中的正则表达式的分组名称）
		map_weixi.put("groupname", "paysuccess");
		// html页面title内容
		map_weixi.put("title", "order_success");
		// html页面url
		map_weixi.put("url", "paysuccess.shtml");
		// dataID a1 a0000002 表示支付一览
		map_weixi.put("a1", "a0000002");
		// dataID a2 a0000003 表示支付详细
		map_weixi.put("a2", "a0000003");
		// 渠道编码
		map_weixi.put("channelsn", sdorder.getChannelsn());
		if (sdorder.getMemberId() == null || "".equals(sdorder.getMemberId())) {
			map_weixi.put("memberid", "");
		} else {
			map_weixi.put("memberid", sdorder.getMemberId());
		}
		map_weixi.put("memberid", sdorder.getMemberId());
		map_weixi.put("productname", productname);
		map_weixi.put("orderid", sdorder.getOrderSn());
		map_weixi.put("totalamout", String.valueOf(sdorder.getTotalAmount().multiply(new BigDecimal("100"))));
		map_weixi.put("pieces",
				String.valueOf(sdorder.getSdinformationSet().iterator().next().getSdinformationinsuredSet().size()));
		map_weixi.put("productid", sdorder.getSdinformationSet().iterator().next().getProductId());
		QueryBuilder qb_riskname = new QueryBuilder(
				"SELECT co.codename as codename FROM sdproduct pro,zdcode co WHERE productid=?  AND co.codetype ='risktype' AND co.codevalue LIKE '%00' AND LEFT(co.codevalue,1) = pro.producttype",
				sdorder.getSdinformationSet().iterator().next().getProductId());
		map_weixi.put("riskname", qb_riskname.executeString());
		map_weixi.put("companycode", sdorder.getSdinformationSet().iterator().next().getProductId().substring(0, 4));
		WeixiManage.sendWeixiPaySuccess(information.getWeixiCookie(), map_weixi);
	}

	/**
	 * CPS保存订单接口
	 */
	private void dealCpsOrder(List<SDOrder> sdorderList) {

		try {
			for (SDOrder sdorder : sdorderList) {

				String webServiceUrl = null;

				if (sdorder == null || StringUtil.isEmpty(sdorder.getChannelsn())) {
					return;
				}

				if (sdorder.getChannelsn().endsWith("dlr")) {
					webServiceUrl = Config.getValue("AGENTSERVERICEURL");
					
					//网金所系统 订单实时同步
					Service servicemodel = new ObjectServiceFactory().create(CpsProductBuyService.class);
					CpsProductBuyService service = (CpsProductBuyService) new XFireProxyFactory().create(servicemodel,
							webServiceUrl);
					service.saveOrder(sdorder.getOrderSn());

				} else if (sdorder.getChannelsn().indexOf("swpt") != -1 || "cps_drlm_wap".equals(sdorder.getChannelsn())) {
					webServiceUrl = Config.getValue("CPSSERVERICEURL");
					//cps实时订单同步 
					org.codehaus.xfire.service.Service servicemodel = new
							ObjectServiceFactory().create(CpsProductBuyService.class);
					CpsProductBuyService service = (CpsProductBuyService) new
							XFireProxyFactory().create(servicemodel, webServiceUrl);
					service.saveOrder(sdorder.getOrderSn());
				}else if ("kxb_app".equals(sdorder.getChannelsn()) || "wj".equals(sdorder.getChannelsn()) || "wap_wx".equals(sdorder.getChannelsn())
						|| "wap_ht".equals(sdorder.getChannelsn())) {
					// 20171128701-需求系统开发需求申请单-联盟订单跟踪（一年期） 增加规则
					
					String appntsql = "SELECT b.applicantIdentityType,b.applicantIdentityTypeName,b.applicantIdentityId,b.applicantMobile from SDInformation a , SDInformationAppnt b where a.informationSn=b.informationSn and a.orderSn=? ";
					DataTable dtappnt = new QueryBuilder(appntsql,sdorder.getOrderSn()).executeDataTable();
					
					String applicantIdentityId = dtappnt.getString(0,"applicantIdentityId");
					String applicantMobile = dtappnt.getString(0,"applicantMobile");
					
					String userCodesql =" SELECT Usercode FROM "+Config.getValue("CPSDataBase")+".Usercheckreward WHERE isflag='0' and ( applicantIdentityId=? or applicantMobile=? ) and  startdate <= now()  and  enddate >=now()";
					String cpsNo = new QueryBuilder(userCodesql,applicantIdentityId,applicantMobile).executeString();
					
					if(StringUtil.isNotEmpty(cpsNo)){
						
						// 保存cps信息
						SDInformation sdinformation = sdinformationService.getByOrderSn(sdorder.getOrderSn());
						OrderConfigNewAction.recordCPS(cpsNo,"","pc", "05", sdorder.getOrderSn(), sdinformation.getProductName(), sdorder.getTotalAmount().toString());
						
						webServiceUrl = Config.getValue("CPSSERVERICEURL");
						//cps实时订单同步 
						org.codehaus.xfire.service.Service servicemodel = new
								ObjectServiceFactory().create(CpsProductBuyService.class);
						CpsProductBuyService service = (CpsProductBuyService) new
								XFireProxyFactory().create(servicemodel, webServiceUrl);
						service.saveOrder(sdorder.getOrderSn());
					}
					
					
				} else { 
					return;
				}

			}
		} catch (MalformedURLException e) {
			logger.error(e.getMessage(), e);
		}
	}

	// 获取当前登录会员，若未登录则返回null
	public Member getLoginMember(HttpServletRequest request) {

		String id = request.getSession().getAttribute(Member.LOGIN_MEMBER_ID_SESSION_NAME) + "";
		if (StringUtils.isEmpty(id)) {
			id = MemberCookieUtil.searchMember(request, null);
			if (StringUtils.isEmpty(id)) {
				return null;

			}
		}

		MemberCookieUtil.checkMember(request, id);

		Member loginMember = null;
		if (!"tencent".equals(id)) {
			loginMember = memberService.get(id);

		} else {
			loginMember = bindInfoForLoginService.get(String.valueOf(request.getAttribute("loginBindId"))).getmMember();
		}
		return loginMember;
	}
	
	/**
	 * 
	 * @Title: callFCContService
	 * @return String 调用结算中心接口
	 * @author
	 */
	private void  callFCContService(List<SDOrder> sdorderList) {
		
		if (sdorderList != null && sdorderList.size() > 0) {
			TBContInsureServlet tb = new TBContInsureServlet();
			for (SDOrder sdo : sdorderList) {
				tb.callFCContService(sdo.getOrderSn());
			}
		}
		
	}

	public String getOrderSn() {

		return orderSn;
	}

	public void setOrderSn(String orderSn) {

		this.orderSn = orderSn;
	}

	public String getPayAmount() {

		return payAmount;
	}

	public void setPayAmount(String payAmount) {

		this.payAmount = payAmount;
	}

	public String getTypeFlag() {

		return typeFlag;
	}

	public void setTypeFlag(String typeFlag) {

		this.typeFlag = typeFlag;
	}

	public String getJrhsURL() {

		return jrhsURL;
	}

	public void setJrhsURL(String jrhsURL) {

		this.jrhsURL = jrhsURL;
	}

	public List<String> getProductIdList() {

		return productIdList;
	}

	public void setProductIdList(List<String> productIdList) {

		this.productIdList = productIdList;
	}

}
