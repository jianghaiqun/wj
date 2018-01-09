package cn.com.sinosoft.action.shop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.activemq.ProducerMQ;
import com.alibaba.fastjson.JSON;
import com.allinpay.bean.Rnpa;
import com.allinpay.bean.Trans;
import com.allinpay.ets.client.PaymentResult;
import com.allinpay.service.AllinpayService;
import com.finance.util.JedisCommonUtil;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.cms.resend.ResendMain;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.cache.CacheManager;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.NumberUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.inter.ActivityCalculate;
import com.sinosoft.inter.ActivityGiveDetail;
import com.sinosoft.inter.MailAction;
import com.sinosoft.jdt.InsureTransferNew;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.points.IntegralConstant;
import com.sinosoft.points.PointsCalculate;
import com.sinosoft.schema.ActivitySendCouponLogSchema;
import com.sinosoft.schema.OnlineRevisitRecordSchema;
import com.sinosoft.schema.SDCouponInfoSchema;
import com.sinosoft.schema.TradeInfoSchema;
import com.sinosoft.schema.TradeSummaryInfoSchema;
import com.tenpay.RequestHandler;
import com.tenpay.ResponseHandler;
import com.tenpay.client.ClientResponseHandler;
import com.tenpay.client.TenpayHttpClient;
import com.wangjin.optimize.register.AutoRegister;
import com.wxpay.wxap.util.Sha1Util;
import com.wxpay.wxap.util.TenpayUtil;
import com.wxpay.wxap.util.WxPayConfig;
import com.wxpay.wxap.util.XMLUtil;
import com.yeepay.Configuration;
import com.yeepay.PaymentForOnlineService;

import chinapay.PrivateKey;
import chinapnr.SecureLink;
import cn.com.sinosoft.bean.CommentInfo;
import cn.com.sinosoft.entity.BindInfoForLogin;
import cn.com.sinosoft.entity.CouponInfo;
import cn.com.sinosoft.entity.DirectPayBankInfo;
import cn.com.sinosoft.entity.GALog;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.MemberChannel;
import cn.com.sinosoft.entity.Order.OrderStatus;
import cn.com.sinosoft.entity.PayBase;
import cn.com.sinosoft.entity.RefundBase;
import cn.com.sinosoft.entity.SDInformation;
import cn.com.sinosoft.entity.SDInformationAppnt;
import cn.com.sinosoft.entity.SDInformationInsured;
import cn.com.sinosoft.entity.SDOrder;
import cn.com.sinosoft.entity.SDOrder.SDOrderStatus;
import cn.com.sinosoft.entity.SDOrder.SDPayStatus;
import cn.com.sinosoft.entity.TradeInfo;
import cn.com.sinosoft.entity.TradeInformation;
import cn.com.sinosoft.service.BindInfoForLoginService;
import cn.com.sinosoft.service.CouponInfoService;
import cn.com.sinosoft.service.DirectPayBankInfoService;
import cn.com.sinosoft.service.GALogService;
import cn.com.sinosoft.service.GiftClassifyService;
import cn.com.sinosoft.service.InsuredCompanyReturnDataService;
import cn.com.sinosoft.service.LicaiBaoxianService;
import cn.com.sinosoft.service.MemberChannelService;
import cn.com.sinosoft.service.MemberService;
import cn.com.sinosoft.service.PayBaseService;
import cn.com.sinosoft.service.RefundBaseService;
import cn.com.sinosoft.service.SDInformationRiskTypeService;
import cn.com.sinosoft.service.SDInformationService;
import cn.com.sinosoft.service.SDOrderService;
import cn.com.sinosoft.service.TradeInfoService;
import cn.com.sinosoft.service.TradeInformationService;
import cn.com.sinosoft.util.ArithUtil;
import cn.com.sinosoft.util.CommonUtil;
import cn.com.sinosoft.util.Constant;
import cn.com.sinosoft.util.CookieUtil;
import cn.com.sinosoft.util.Points1;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@ParentPackage("shop")
@Results({
		@Result(name = "input", location = "/WEB-INF/template/shop/error.ftl"),
})
public class PayAction extends BaseShopAction {
	private static final Logger LOG = LoggerFactory.getLogger(PayAction.class);

	@Resource
	protected PayBaseService payService;
	@Resource
	protected MemberChannelService memberChannelService;
	@Resource
	protected InsuredCompanyReturnDataService tInsuredCompanyReturnDataService;
	@Resource
	protected TradeInformationService tradeInformationService;
	@Resource
	protected SDOrderService sdorderService;
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
	protected LicaiBaoxianService licaiBaoxianService;
	@Resource
	protected DirectPayBankInfoService directPayBankInfoService;

	/*
	 * GALog服务: gaLogService
	 */
	@Resource
	private GALogService gaLogService;
	private TradeInformation tradeInformation;
	private SDOrder sdorder;
	private static final long serialVersionUID = 4881872749793702799L;
	private String payType;
	private String paytype;
	private String ErrMsg;
	private String Version;
	private String CmdId;
	private String MerId;
	private String OrdId;
	private String OrdAmt;
	private String CurCode;
	private String Pid;
	private String RetUrl;
	private String BgRetUrl;
	private String MerPriv;
	private String GateId;
	private String UsrMp;
	private String DivDetails;
	private String PayUsrId;
	private String ChkValue;
	private String GateUrl;
	private String RespCode;// 应答回应码
	private String TrxId;// 钱管家交易唯一标识
	private String RetType;// 返回类型
	private String result;// 交易结果
	private String transaction_id;
	private String out_trade_no;
	private String total_fee;
	private String trade_state;
	private String trade_mode;
	private String notify_id;
	private String retcode;
	private String discount;
	private String OldOrdId;
	private String RefAmt;
	private String subject;
	private String alibody;
	private String orderSn;
	private String path = "";
	private String type;
	private String p0_Cmd;
	private String p1_MerId;
	private String p2_Order;
	private String p3_Amt;
	private String p4_Cur;
	private String p5_Pid;
	private String p6_Pcat;
	private String p7_Pdesc;
	private String p8_Url;
	private String p9_SAF;
	private String pa_MP;
	private String pd_FrpId;
	private String pr_NeedResponse;
	private String hmac;
	String userName;
	private String typeFlag = "";
	private String jrhsURL = "";// 代理人工程路径
	private String isLogin;
	private String orderId;
	private String paySn;// 支付流水号
	private String paymentSn;// 支付交易流水号
	private String needUWCheckFlag;// 是否需要核保标记

	private String orderSnS = "";// 订单号数组
	private String memberid; // 会员id
	private String totalamount;// 订单金额
	private String pieces;// 被保人个数
	private String productid;// 产品id
	private String companyname;// 公司名称
	private String riskname;// 险种名称
	// 维析需要的cookie信息
	private String weixiCookie;
	private String wait;
	// 理财保险区分
	private String lcbxFlag;


	private String partnerUrl;// 合作商户跳转URL
	/**
	 * 支付回调返回类型. 为“1”: 浏览器重定向; 为“2”: 服务器点对点通讯.
	 */
	private String Btype; // 交易结果返回类型
	// 优惠劵实体类
	private CouponInfo couponInfo;
	// 优惠劵码
	private String CouponSn;
	// 活动抵消金额
	private BigDecimal activityParValue = new BigDecimal("0");;
	// 优惠劵实付金额
	private String callBackAmount;
	// 优惠券的实际优惠金额
	private BigDecimal couponprice;

	PrintWriter out;

	// 新用户名
	private String newUserName;
	// 交易日期
	private String TransDate;
	// 支付金额
	private String payPrice;
	// 抵值的积分
	private String offsetPoint;
	// 支付-ajax校验-状态.
	private String ajaxVerify = "true";
	// 支付-ajax校验-错误信息.
	private String ajaxVerifyErrorMesage;
	// 飞客ID
	private String fkID;
	// 代理人账户余额
	private String balance;
	// 代理人支付账户
	private String paypassword;
	// 支付有礼优惠券显示flag
	private String couponShowFlag;

	private List<String> productIdList = new ArrayList<String>();
	// 会员等级及积分信息
	private Map map_point_result;

	private List<CommentInfo> commentList = new ArrayList<CommentInfo>();
	// 线上回访信息
	private Map<String, String> onLineCallBackInfo;

	// private String BD_INFO = ""; //百分点合作结束，百分点核心代码暂存

	private String key = "";

	private static String authorizeUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=!!!&redirect_uri=@@@&response_type=code&scope=snsapi_base&state=WDBD#wechat_redirect";

	static Properties props = new Properties();

	static {
		try {
			InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("weixin.properties");
			InputStreamReader rd = new InputStreamReader(is, "UTF-8");
			props.load(rd);
			rd.close();
			is.close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	// 支付展示页面
	public String list() {

		return LIST;
	}

	private List<SDOrder> orderList = new ArrayList<SDOrder>();// 订单

	private HashMap<String, String> exchangeCheck(String orderId, Map<String, String> paramExchange) {

		HashMap<String, String> map = new HashMap<String, String>();
		Member memberLogin = getLoginMember();
		if (memberLogin == null) {
			map.put(STATUS, "1");
			map.put(MESSAGE, "您未登录不能兑换，请登录后再支付！");
			return map;
		}
		paramExchange.put("MemberId", memberLogin.getId());
		payType = "zerozf";
		sdorder = sdorderService.getOrderByOrderSn(orderId);
		if (sdorder == null) {
			map.put(STATUS, "1");
			logger.info("积分兑换保险产品支付时未查询到订单信息，会员ID：{} 订单号：{}", memberLogin.getId(), orderId);
			return map;
		}
		// 订单兑换积分必须有效
		if (StringUtil.isEmpty(sdorder.getOffsetPoint())
				|| "0".equals(sdorder.getOffsetPoint())) {
			map.put(STATUS, "1");
			logger.info("积分兑换保险产品支付时订单兑换积分为空或是0，会员ID：{}, 订单号：{}", memberLogin.getId(), orderId);
			return map;
		}
		// 判断会员积分是否足够支付
		if (memberLogin.getCurrentValidatePoint() == null
				|| memberLogin.getCurrentValidatePoint() == 0) {
			map.put(STATUS, "1");
			map.put(MESSAGE, "您的积分不够了，快去赚吧！");
			logger.warn("会员无可用积分进行兑换保险产品，会员ID：{},  订单号：{}", memberLogin.getId(),orderId);
			return map;
		}

		if (memberLogin.getCurrentValidatePoint().compareTo(
				Integer.valueOf(sdorder.getOffsetPoint())) < 0) {
			map.put(STATUS, "1");
			map.put(MESSAGE, "您的积分不够了，快去赚吧！");
			Object[] argArr = {memberLogin.getCurrentValidatePoint(), sdorder.getOffsetPoint(), memberLogin.getId()};
			logger.info("会员可用积分（{}）小于兑换保险产品积分（{}），会员ID：{} 订单号：{}", argArr);
			return map;
		}
		Set<SDInformation> sdinformationSet = sdorder.getSdinformationSet();
		SDInformation sdinformation = null;
		if (sdinformationSet != null && sdinformationSet.size() > 0) {
			for (SDInformation sd : sdinformationSet) {
				sdinformation = sd;
			}
		}
		String sql = "select count(1) from sdinformationrisktype where ordersn = ? ";
		// 兑换保单数量
		int count = new QueryBuilder(sql, orderId).executeInt();
		// 查看库存量
		int lastnum = giftClassifyService.getLastNum(sdinformation
				.getProductId());

		if (lastnum == 0 || lastnum < count) {
			map.put(STATUS, "1");
			map.put(MESSAGE, "对不起，库存不足！");
			Object[] argArr = {lastnum, memberLogin.getId(), orderId};
			logger.info("会员用积分进行兑换保险产品库存（{}个）不足，会员ID：{} 订单号：{}", argArr);
			return map;
		}
		paramExchange.put("count", count + "");
		paramExchange.put("lastnum", lastnum + "");
		String tSDOrderStatus = String.valueOf(sdorder.getOrderStatus()
				.ordinal());
		if (!"5".equals(tSDOrderStatus)) {
			map.put(STATUS, "1");
			map.put(MESSAGE, "该订单 （" + OrdId + "） 不是待支付状态，不允许重新支付！");
			logger.warn("该订单不是待支付状态，不允许重新支付，orderSn,{}", OrdId);
			return map;
		}

		String paidState = String.valueOf(sdorder.getPayStatus().ordinal());
		if ("2".equals(paidState)) {
			map.put(STATUS, "0");
			map.put(MESSAGE, "已支付成功！");
			return map;
		}

		String totalPayPrice = new QueryBuilder(
				"SELECT ROUND(SUM(a.payprice),2) FROM sdinformationrisktype a,sdorders b WHERE a.`orderSn` = b.`orderSn` AND a.appstatus = '1' AND b.memberid =?",
				memberLogin.getId()).executeString();
		if (StringUtil.isEmpty(totalPayPrice)) {
			totalPayPrice = "0.00";
		}
		double shortPrice = ArithUtil.sub(new BigDecimal(totalPayPrice).doubleValue(),
				new BigDecimal(Config.getValue("exchangeBoundary")).doubleValue());
		if (shortPrice < 0) {
			map.put(STATUS, "1");
			map.put(MESSAGE, "会员需在网站累计购物满" + String.valueOf(Config.getValue("exchangeBoundary")) + "元后，方可在积分商城兑换商品。");
			logger.info("会员需在网站累计购物满{}元后，方可在积分商城兑换商品。orderSn:{}",
					String.valueOf(Config.getValue("exchangeBoundary")),  OrdId);
			return map;
		}
		return null;
	}

	public String exchange() {

		HashMap<String, String> map = new HashMap<String, String>();
		String orderId = getParameter("orderId");
		String sdcodef = getParameter("sdcodef");
		Member memberLogin = getLoginMember();
		if (memberLogin == null) {
			map.put(STATUS, "1");
			map.put(MESSAGE, "您未登录不能兑换，请登录后再支付！");
			return ajax(JSONObject.fromObject(map).toString(), "text/html");
		}

		String scd = (String) getSession("exchange_sendValidateCode");
		// 验证码失效
		if (StringUtil.isEmpty(scd)) {
			map.put(STATUS, "yzmerror");
			map.put(MESSAGE, "验证码失效！");
			return ajax(JSONObject.fromObject(map).toString(), "text/html");
		}

		// 验证码错误
		if (!scd.equals(sdcodef)) {
			map.put(STATUS, "yzmerror");
			map.put(MESSAGE, "验证码输入错误！");
			return ajax(JSONObject.fromObject(map).toString(), "text/html");
		}
		removeSession("exchange_sendValidateCode");
		Points1 points1 = new Points1();
		points1.init(giftClassifyService, null, memberService, memberLogin.getId(), null);
		Map<String, String> resultEx = points1.Exchange(orderId);
		if (IntegralConstant.SUCCESS.equals(resultEx.get(STATUS))) {
			InsureThread InsureThread = new InsureThread(orderId);
			InsureThread.start();
		}

		return ajax(JSONObject.fromObject(resultEx).toString(), "text/html");
	}

	public String isYezf() {

		HashMap<String, String> map = new HashMap<String, String>();
		String userCode = (String) new QueryBuilder("SELECT cid FROM cps WHERE `on` = '" + orderSn + "' ").executeOneValue();

		logger.info("payAction - isYezf : userCode={},orderSn={}", userCode, orderSn);

		DataTable dt = new QueryBuilder("select PayPassword,Balance from cps_agent.sduser where usercode='" + userCode + "'")
				.executeDataTable();
		if (dt.getRowCount() > 0) {
			logger.info("支付金额 - 账户余额:{}", ArithUtil.sub(balance, dt.getString(0, "Balance")));
			if (!"0.00".equals(ArithUtil.sub(balance, dt.getString(0, "Balance")))) {
				map.put("Status", "0");
				map.put("Message", "支付余额数值不正确！");
				return ajaxJson(map);
			}
			if (!dt.getString(0, "PayPassword").equals(DigestUtils.md5Hex(paypassword))) {
				map.put("Status", "0");
				map.put("Message", "支付密码输入错误！");
				return ajaxJson(map);
			}

			String newbal = ArithUtil.sub(dt.getString(0, "Balance"), payPrice);
			BigDecimal b = new BigDecimal(newbal);
			if (b.doubleValue() >= 0) {
				new QueryBuilder("update cps_agent.sduser set Balance = '" + newbal + "' where usercode='" + userCode + "'")
						.executeNoQuery();
				map.put("Status", "1");
			} else {
				map.put("Status", "0");
				map.put("Message", "余额不足，请充值后再进行支付！");
			}
		}
		return ajaxJson(map);
	}

	/**
	 * 判断起保日期是否过期或已快过期
	 * 
	 *            订单号
	 * @return
	 */
	public String isOutPeriod() {

		String memberid = (String) getSession(Member.LOGIN_MEMBER_ID_SESSION_NAME);
		// 前段地址
		String front = "";
		if (StringUtil.isEmpty(memberid)) {
			front = Config.getFrontServerContextPath();
		}

		String orderId = getRequest().getParameter("orderId");
		paySn = getRequest().getParameter("paySn");
		String exchangeFlag = getRequest().getParameter("exchangeFlag");
		String[] orderIds = orderId.split(",");
		List<HashMap<String, String>> listMap = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map = new HashMap<String, String>();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		GetDBdata db = new GetDBdata();
		String dateSql = "SELECT startDate,endDate,productId FROM sdinformation WHERE orderSn = ? ";
		String periodSql = "SELECT startPeriod FROM productPeriod WHERE riskCode=(SELECT productid FROM sdinformation WHERE ordersn = ? )";
		String insuredCountSql = "SELECT COUNT(1) FROM sdinformationinsured WHERE orderSn=?";
		QueryBuilder countQB;
		List<Map<String, Object>> datePeriod = null;
		String[] dateSqltemp = new String[1];
		JSONArray json = new JSONArray();

		// 理财险 验签金额验证
		if ("zjzf".equals(payType)) {
			String bankCode = getRequest().getParameter("bankCode");
			String bankAccNo = getRequest().getParameter("bankAccNo");

			QueryBuilder lcbxQb = new QueryBuilder(
					"SELECT appnt.applicantName FROM zdconfig c, sdorders o, sdinformation info, SDInformationAppnt appnt "
							+ "WHERE appnt.informationSn = info.informationSn and o.ordersn = info.ordersn AND FIND_IN_SET(info.productid, c.value) AND c.TYPE = 'LicaiBaoxianProducts' AND o.ordersn = ?",
					orderIds[0]);
			DataTable dt = lcbxQb.executeDataTable();
			String applicantName = "";
			if (dt.getRowCount() > 0) {
				applicantName = dt.getString(0, "applicantName");
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("memberId", memberid);
				param.put("orderSn", orderIds[0]);
				param.put("accName", applicantName);
				param.put("bankAccNo", bankAccNo);
				param.put("bankCode", bankCode);
				//param.put("validateMoney", getRequest().getParameter("validateMoney"));
				param.put("vCode", getRequest().getParameter("vCode"));
				Map<String, Object> result = licaiBaoxianService.checkVCode(param);
				//Map<String, Object> result = licaiBaoxianService.checkBankInfo(param);

				if (Constant.FAIL.equals(result.get(Constant.STATUS))) {
					map.put("lcbxFlag", "true");
					map.put(Constant.STATUS, "1");
					map.put("message", (String) result.get(Constant.MSG));
					json.add(map);
					return ajax(json.toString(), "text/html");
				} else {
					DirectPayBankInfo record = directPayBankInfoService.getByOrderSn(orderIds[0]);
					QueryBuilder qb = new QueryBuilder(
							" select codename from zdcode where CodeType = 'LicaiBaoxianBankCode' and ParentCode='LicaiBaoxianBankCode' and CodeValue = ?",
							bankCode);
					String bankName = qb.executeString();
					if (null != record) {
						record.setBankCode(bankCode);
						record.setBankNo(bankAccNo);
						record.setBankUserName(applicantName);
						record.setBankName(bankName);
						record.setModifyDate(new Date());
						directPayBankInfoService.update(record);
					} else {
						record = new DirectPayBankInfo();
						record.setId(CommonUtil.getUUID());
						record.setOrderSn(orderIds[0]);
						record.setBankCode(bankCode);
						record.setBankNo(bankAccNo);
						record.setBankUserName(applicantName);
						record.setBankName(bankName);
						record.setCreateDate(new Date());
						record.setModifyDate(new Date());

						directPayBankInfoService.save(record);
					}
				}
			}
		}

		int insuredCount = 0;// 被保人总人数
		for (int i = 0; i < orderIds.length; i++) {
			countQB = new QueryBuilder(insuredCountSql);
			countQB.add(orderIds[i]);
			insuredCount = insuredCount + countQB.executeInt();// 统计被保人数
		}
		// 当前时间
		Date now = new Date();
		try {
			for (int i = 0; i < orderIds.length; i++) {
				map = new HashMap<String, String>();
				dateSqltemp[0] = orderIds[i];
				datePeriod = db.queryObj(dateSql, dateSqltemp);
				Map<String, Object> period1 = datePeriod.get(0);
				String productId = (String) period1.get("productId");
				Date periodeffective = null;
				if (period1.get("endDate") != null) {
					periodeffective = sfTime.parse(period1.get("startDate")
							.toString());
				}
				// 起保时间间隔
				int stertPerid = 1;
				if (StringUtil.isNotEmpty(db
						.getOneValue(periodSql, dateSqltemp))) {
					stertPerid = Integer.parseInt(db.getOneValue(periodSql,
							dateSqltemp));
				}

				// 默认产品无次日生效，查询zdcode表中被要求次日生效的产品记录
				QueryBuilder qb = new QueryBuilder(
						"SELECT memo FROM zdcode WHERE codetype='ProductPeriod' AND codevalue=?");
				qb.add(productId);
				String strPerid = qb.executeString();
				if (StringUtil.isNotEmpty(strPerid)) {
					stertPerid = Integer.parseInt(strPerid);
				}

				now = new Date();

				// 订单状态校验
				sdorder = sdorderService.getOrderByOrderSn(orderIds[i]);
				Set<SDInformation> sdinformationSet = sdorder.getSdinformationSet();
				SDInformation sdinformation = null;
				for (SDInformation sd : sdinformationSet) {
					sdinformation = sd;
				}
				HashMap<String, String> map_result = isProductDownShelf("支付页面", sdinformation);
				String result_status = map_result.get("status");
				if (ERROR.equals(result_status)) {
					logger.error("支付页面-核保失败：orderSn-{} - 订单状态：{}", orderIds[i], sdorder.getOrderStatus());
					if (!sdorder.getOrderStatus().equals(OrderStatus.paid)) {
						logger.error("支付页面-核保失败：orderSn-{} - 修改订单状态为自动取消。",  orderIds[i]);
						sdorder.setOrderStatus(SDOrderStatus.autoinvalid);
						sdorderService.update(sdorder);
						// 更新积分兑换表状态
						Transaction trans = new Transaction();
						trans.add(new QueryBuilder(
								"update PointExchangeInfo set status = ? where orderSn = ?",
								String.valueOf(sdorder.getOrderStatus().ordinal()),
								sdorder.getOrderSn()));
						if (!trans.commit()) {
							logger.error("积分兑换保险产品，积分兑换表更新订单状态失败（PayAction-isOutPeriod），orderSn:{}", orderSn);
						}
					}
					map.put("ProductName", sdinformation.getProductName());
					map.put("OrderId", orderIds[i]);
					map.put(STATUS, "false");
					map.put(MESSAGE, "您的订单存在异常,请联系客服!");
					logger.error("ajax核保异常! 信息：您的订单存在异常,请联系客服!订单号：{}", sdinformation.getOrderSn());
					listMap.add(map);
				} else if ("alreadyPay".equals(result_status)) {// 份数校验，是否重复购买
					map.put("OrderId", orderIds[i]);
					map.put("ProductName", sdinformation.getProductName());
					map.put(STATUS, "false");
					// 获取校验错误信息
					Collection<String> collection_error = getActionErrors();
					if (collection_error.size() > 0) {
						Object error[] = collection_error.toArray();
						String error_mess = String.valueOf(error[error.length - 1]);
						if (StringUtil.isEmpty(error_mess)) {
							map.put(MESSAGE, "您的订单存在异常,请联系客服!");
						} else {
							map.put(MESSAGE, error_mess);
						}
					} else {
						map.put(MESSAGE, "您的订单存在异常,请联系客服!");
					}
					if (!sdorder.getOrderStatus().equals(OrderStatus.paid)) {
						logger.error("支付页面-核保失败：orderSn-{} - 修改订单状态为自动取消。", orderIds[i]);
						sdorder.setOrderStatus(SDOrderStatus.temptorysave);
						sdorderService.update(sdorder);
						// 更新积分兑换表状态
						Transaction trans = new Transaction();
						trans.add(new QueryBuilder(
								"update PointExchangeInfo set status = ? where orderSn = ?",
								String.valueOf(sdorder.getOrderStatus().ordinal()),
								sdorder.getOrderSn()));
						if (!trans.commit()) {
							logger.error("积分兑换保险产品，积分兑换表更新订单状态失败（PayAction-isOutPeriod），orderSn:{}", orderSn);
						}
					}
					logger.error("ajax核保异常! 信息：您的订单存在异常,请联系客服!订单号：{}", sdinformation.getOrderSn());
					listMap.add(map);
				}

				// 当日生效的情况
				if (stertPerid == 0) {
					long time = periodeffective.getTime() + 24 * 3600000
							- now.getTime();
					// 过期
					if (time < 0) {
						map.put("front", front);
						map.put("OrderId", orderIds[i]);
						map.put("ProductName", sdinformation.getProductName());
						map.put(STATUS, "timeout");
						map.put("start",
								new SimpleDateFormat("yyyy年MM月dd日 HH时")
										.format(periodeffective));
						map.put("now", new SimpleDateFormat(
								"yyyy年MM月dd日 HH时mm分").format(now));
						listMap.add(map);
						// 距离起保终止时间小于10分钟时 提示修改起保期间才能支付
					} else if (time <= 600000) {
						map.put("OrderId", orderIds[i]);
						map.put("ProductName", sdinformation.getProductName());
						map.put(STATUS, "currBeTimeout");
						map.put("start",
								new SimpleDateFormat("yyyy年MM月dd日 HH时")
										.format(periodeffective.getTime() + 24 * 3600000));
						map.put("period", String.valueOf((int) NumberUtil
								.round((time * 1.0) / 60000, 2)));
						listMap.add(map);
					}
					if (time > 600000 && time <= 9600000 && insuredCount >= 20) {
						map.put("OrderId", orderIds[i]);
						map.put("ProductName", sdinformation.getProductName());
						map.put(STATUS, "morethaninsured");
						map.put("start",
								new SimpleDateFormat("yyyy年MM月dd日 HH时")
										.format(periodeffective.getTime()));
						listMap.add(map);
					}

				} else {
					// 当前时间超出起保时间 过期
					if (now.after(periodeffective)) {
						map.put("front", front);
						map.put("OrderId", orderIds[i]);
						map.put("ProductName", sdinformation.getProductName());
						map.put(STATUS, "timeout");
						map.put("start",
								new SimpleDateFormat("yyyy年MM月dd日 HH时")
										.format(periodeffective));
						map.put("now", new SimpleDateFormat(
								"yyyy年MM月dd日 HH时mm分").format(now));
						if (stertPerid > 1) {
							map.put("period", String.valueOf(stertPerid));
						}
						listMap.add(map);
						continue;
					}

					String tDate = com.sinosoft.sms.messageinterface.pubfun.PubFun
							.calSDate(sf.format(now), (stertPerid - 1), "D")
							+ " "
							+ new SimpleDateFormat("HH:mm:ss").format(now);
					Date nowDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.parse(tDate);
					long time = periodeffective.getTime() - nowDate.getTime();
					// 小于起保时间间隔的情况 已过期
					if (time <= 0) {
						map.put("front", front);
						map.put("OrderId", orderIds[i]);
						map.put("ProductName", sdinformation.getProductName());
						map.put(STATUS, "timeout");
						map.put("start",
								new SimpleDateFormat("yyyy年MM月dd日 HH时")
										.format(periodeffective));
						map.put("now", new SimpleDateFormat(
								"yyyy年MM月dd日 HH时mm分").format(now));
						if (stertPerid > 1) {
							map.put("period", String.valueOf(stertPerid));
						}

						listMap.add(map);

						// 距离起保开始时间小于10分钟时 提示
					} else if (time <= 600000) {
						map.put("OrderId", orderIds[i]);
						map.put("ProductName", sdinformation.getProductName());
						map.put(STATUS, "beTimeout");
						map.put("start",
								new SimpleDateFormat("yyyy年MM月dd日 HH时")
										.format(periodeffective.getTime()));
						map.put("newStart", new SimpleDateFormat(
								"yyyy年MM月dd日 HH时").format(periodeffective
								.getTime() + 24 * 3600000));
						if (stertPerid == 1) {
							map.put("period", (int) NumberUtil.round(
									(time * 1.0) / 60000, 2)
									+ "");
						} else {
							map.put("period", (stertPerid - 1)
									+ "天"
									+ (int) NumberUtil.round(
											(time * 1.0) / 60000, 2));
						}

						// 大于5分钟 可继续支付、小于五分钟必须修改起保日期才可支付
						if (time < 300000) {
							// 1:必须修改起保日期
							map.put("isModify", "1");
						} else {
							map.put("isModify", "0");
						}
						listMap.add(map);
					}
					if (time > 600000 && time <= 9600000 && insuredCount >= 20) {
						map.put("OrderId", orderIds[i]);
						map.put("ProductName", sdinformation.getProductName());
						map.put(STATUS, "morethaninsured");
						map.put("start",
								new SimpleDateFormat("yyyy年MM月dd日 HH时")
										.format(periodeffective.getTime()));
						listMap.add(map);
					}
				}
			}

			if ("1".equals(exchangeFlag) && listMap.size() == 0) {

				Map<String, String> paramExchange = new HashMap<String, String>();
				HashMap<String, String> checkMap = exchangeCheck(orderId, paramExchange);
				if (checkMap == null) {
					checkMap = new HashMap<String, String>();
					checkMap.put("status", "-1");
				}
				listMap.add(checkMap);
			}

			/*
			 * if(insuredOrderSn.length()>=1){insuredOrderSn.deleteCharAt(0);}
			 * if(periodFlag && insuredCount>=20){ HashMap<String, String> map1
			 * = new HashMap<String, String>(); map1.put(STATUS,
			 * "morethaninsured"); map1.put("orderSns",
			 * insuredOrderSn.toString()); listMap.add(map1); }
			 */
			json.add(listMap);
			return ajax(json.toString(), "text/html");

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			listMap = new ArrayList<HashMap<String, String>>();
			map.put(STATUS, ERROR);
			map.put(MESSAGE, e.getMessage());
			listMap.add(map);
			json.add(listMap);
			return ajax(json.toString(), "text/html");
		}
	}

	/**
	 * 选择支付方式.
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String show() {
		try {
			if (StringUtil.isEmpty(payType) && StringUtil.isEmpty(paytype)) {
				logger.warn("支付类型不能为空！");
				addActionError("支付类型不能为空！");
				return ERROR;
			}
			if (StringUtil.isNotEmpty(paytype) && StringUtil.isEmpty(payType)) {
				payType = paytype;
			}
			String oldPaySn = paySn;
			sdorder = sdorderService.getOrderByOrderSn(OrdId);
			orderList.add(sdorder);
			orderSnS = sdorder.getOrderSn();
			String paidState = String.valueOf(sdorder.getPayStatus().ordinal());
			String tSDOrderStatus = String.valueOf(sdorder.getOrderStatus().ordinal());
			if (!"5".equals(tSDOrderStatus)) {
				logger.warn("该订单不是待支付状态，不允许重新支付，orderSn:{}", OrdId);
				addActionError("该订单 （" + OrdId + "） 不是待支付状态，不允许重新支付！");
				return ERROR;
			}
			if ("2".equals(paidState)) {
				// 页面返回金额处理
				DataTable dt_amount = new QueryBuilder("select tradeamount from tradesummaryinfo where paysn =?",
						sdorder.getPaySn()).executeDataTable();
				if (dt_amount.getRowCount() > 0) {
					orderId = OrdId;
					callBackAmount = String.valueOf(new BigDecimal(dt_amount.getString(0, 0)).setScale(2,
							BigDecimal.ROUND_HALF_UP));
					tradeInformation = tradeInformationService.isPayFinnish(sdorder.getOrderSn());
				} else {
					logger.error("点击支付logo，查询交易表中支付金额错误，支付流水号为：{}", paySn);
					addActionError("支付异常");
					return "newerror";
				}
				// 积分及会员等级信息
				if (StringUtil.isNotEmpty(sdorder.getMemberId())) {
					List<SDOrder> sorderList = new ArrayList<SDOrder>();
					sorderList.add(sdorder);
					map_point_result = new PointsCalculate().getMemberUpgradeInfo(sdorder.getMemberId(), sorderList);
				} else {
					map_point_result = null;
				}
				
				SDInformation sdinformation = sdinformationService.getByOrderSn(sdorder.getOrderSn());
				if (sdinformation == null ) {
					logger.error("点击支付logo，订单信息表未查询到数据，订单号：{}", sdorder.getOrderSn());
					addActionError("支付订单数据异常");
					return "newerror";
				}
				commentList = new ArrayList<CommentInfo>();
				CommentInfo commentInfo = new CommentInfo();
				commentInfo.setOrderSn(sdorder.getOrderSn());
				
				commentInfo.setProductName(sdinformation.getProductName());
				String productType = new QueryBuilder("select p.productType from sdproduct p where p.productId =? ", sdinformation.getProductId()).executeString();
				// 取得产品投保目的
				if (StringUtil.isNotEmpty(productType)) {
					MemberCommentAction memberCommentAction = new MemberCommentAction();
					commentInfo.setPurpose(memberCommentAction.getPurpose(productType));
				}
				
				if (sdorder.getCommentId() != null) {
					commentInfo.setDisCommented("");
					commentInfo.setDisComment("none");
					commentInfo.setPoints(new QueryBuilder(
							"select Integral from SDIntCalendar where memberId = ? and businessid = ? and Source = '1'",
							sdorder.getMemberId(), sdorder.getOrderSn()).executeString());
				} else {
					commentInfo.setDisCommented("none");
					commentInfo.setDisComment("");
				}
				commentList.add(commentInfo);

				// 线上回访的产品
				@SuppressWarnings("unchecked")
				Mapx<String, String> productcodes =  CacheManager.getMapx("Code", "OnLineCallBackProductID");
				if (StringUtil.isNotEmpty(sdinformation.getProductId()) && productcodes.containsKey(sdinformation.getProductId())) {
					// 取得线上回访展示信息
					AlipayAction alipayAction = new AlipayAction();
					onLineCallBackInfo = alipayAction.getOnLineCallBackInfo(sdinformation, sdorder.getTotalAmount().toString(), sdorder.getPayPrice());
				} else {
					onLineCallBackInfo = null;
				}
				String[] lcx_arr = Config.getValue("LicaiBaoxianProducts").split(",");
				List<String> listLcx = Arrays.asList(lcx_arr);
				if (StringUtil.isNotEmpty(sdinformation.getProductId()) && listLcx.contains(sdinformation.getProductId())) {
					lcbxFlag = "true";
				}
				return "callsuccess";
			}
			memberid = (String) getSession(Member.LOGIN_MEMBER_ID_SESSION_NAME);
			// 积分是否为非负整数
			if (!StringUtil.isInteger(offsetPoint)) {
				logger.error("支付页面抵值积分的数值为非法数字，会员id为：{}", memberid);
				addActionError("支付页面抵值积分的数值为非法数字");
				return ERROR;
			}
			// 获取会员的抵值积分并与会员积分做大小校验
			if (StringUtil.isNotEmpty(memberid)) {
				if (StringUtil.isNotEmpty(sdorder.getMemberId())) {
					if (!("tencent".equals(memberid))) {
						if (!(memberid.equals(sdorder.getMemberId()))) {
							logger.warn("支付页面订单会员非当前用户");
							addActionError("支付页面订单会员非当前用户");
							return ERROR;
						}
					}
				}
				QueryBuilder qb = new QueryBuilder("select currentValidatePoint from member where id=?");

				qb.add(memberid);
				DataTable dt = qb.executeDataTable();
				if (dt.getRowCount() == 1) {
					String currentValidatePoint = qb.executeString();
					if (StringUtil.isEmpty(currentValidatePoint)) {
						currentValidatePoint = "0";
					}
					// 抵值积分必须小于会员的可用积分
					if (new BigDecimal(currentValidatePoint)
							.compareTo(new BigDecimal(offsetPoint)) == -1) {
						logger.warn("支付页面抵值积分大于会员的可用积分，会员id为：{}", memberid);
						addActionError("支付页面抵值积分大于会员的可用积分");
						return ERROR;
					}
				} else if ("tencent".equals(getSession().get(Member.LOGIN_MEMBER_ID_SESSION_NAME))) {
					Cookie cookie = CookieUtil.getCookieByName(getRequest(), "loginBindId");
					if (cookie != null && (!("".equals(cookie)))) {
						// 取得绑定的loginBindId值
						String loginBindId = cookie.getValue();
						QueryBuilder qb_tencent = new QueryBuilder(
								"select currentValidatePoint from member where mBindInfoForLogin_id=?");
						qb_tencent.add(loginBindId);
						DataTable dt_tencent = qb_tencent.executeDataTable();
						if (dt_tencent.getRowCount() == 1) {
							String currentValidatePoint = qb_tencent.executeString();
							if (StringUtil.isEmpty(currentValidatePoint)) {
								currentValidatePoint = "0";
							}
							// 抵值积分必须小于会员的可用积分
							if (new BigDecimal(currentValidatePoint)
									.compareTo(new BigDecimal(offsetPoint)) == -1) {
								logger.warn("支付页面抵值积分大于联合登陆会员的可用积分，联合登陆会员mBindInfoForLogin_id为：{}", loginBindId);
								offsetPoint = "0";
							}
						} else {
							logger.warn("支付页面联合登陆会员查询不到数据，联合登陆会员mBindInfoForLogin_id为：{}", loginBindId);
							offsetPoint = "0";
						}
					} else {
						logger.warn("支付页面查询联合登陆会员积分时，没有找到loginBindId的cookie,订单号为：{}", OrdId);
						offsetPoint = "0";
					}
				} else {
					logger.warn("支付页面查询会员积分时，既不是会员，又不是联合登陆的会员，会员id为：{}", memberid);
					offsetPoint = "0";
				}
			} else {
				offsetPoint = "0";
			}
			// 维析所需的客户浏览器cookie的值
			Cookie cookie = CookieUtil.getCookieByName(getRequest(),
					"vlid_1001");
			if (cookie != null) {
				weixiCookie = cookie.getValue();
			} else {
				weixiCookie = "";
			}
			if (StringUtil.isEmpty(OrdId)) {
				addActionError("订单号为空");
				return ERROR;
			}
			if (StringUtil.isEmpty(paySn)) {
				addActionError("支付流水号为空");
				return ERROR;
			}
			if ("02".equals(typeFlag)) {
				/* 货运包合作方式 */
				this.getNewTypeFlag(paySn);
			}
			sdorder.setOffsetPoint("0");
			sdorderService.update(sdorder);
			// BigDecimal total = sdorder.getTotalAmount();
			if (StringUtil.isNotEmpty(CouponSn) && !("0".equals(CouponSn))) {// 使用优惠券
				// 获取优惠劵对象
				couponInfo = couponInfoService.getCouponInfoByCouponSn(CouponSn);
			}
			List<SDOrder> paramterList = new ArrayList<SDOrder>();
			paramterList.add(sdorder);
			// 筛选优惠活动
			Map<String, Map<String, Object>> activity_product_info1 = ActivityCalculate.ProductShoppingCartInfo(
					paramterList, sdorder.getChannelsn(), true);
			Set keySet = activity_product_info1.keySet();
			for (Iterator it = keySet.iterator(); it.hasNext();) {
				// 活动号（包含“_no_activity”）
				String activitysn = String.valueOf(it.next());
				if (!("_no_activity".equals(activitysn))) {
					// 获取该活动关联的所有信息（包括活动信息，产品信息，优惠信息）
					Map<String, Object> map_info = activity_product_info1.get(activitysn);
					// 获取产品信息list
					Map<String, String> map_activityamont = (Map<String, String>) map_info.get("ActivityAmont");
					Map<String, String> map_activityinfo = (Map<String, String>) map_info.get("ActivityInfo");
					List<Map<String, String>> list_productinfo = (List<Map<String, String>>) map_info.get("ProductInfo");
					String activitySn = map_activityinfo.get("activitySn");
					for (int i = 0; i < list_productinfo.size(); i++) {
						Map<String, String> map_productinfo = list_productinfo.get(i);
						String OrderSn = map_productinfo.get("OrderSn");
						// 活动号更新到订单表
						SDOrder sdord = sdorderService.getOrderByOrderSn(OrderSn);
						sdord.setActivitySn(activitySn);
						sdorderService.update(sdord);
					}
					// 总优惠金额累计
					activityParValue = new BigDecimal(map_activityamont.get("DiscountAmount"));
				}
			}
			if (sdorder == null) {
				addActionError("系统不存在该订单" + OrdId);
				return ERROR;
			}
			PayBase payBase = payService.getPayBaseByPayType(payType);
			if (payBase == null) {
				addActionError("未知支付类型(" + payType + ")");
				return ERROR;
			}

			Set<SDInformation> sdinformationSet = sdorder.getSdinformationSet();
			SDInformation sdinformation = null;
			for (SDInformation sd : sdinformationSet) {
				sdinformation = sd;
			}

			/** 核保改为AJAX 暂时保留 **/
			// if (ERROR.equals(isProductDownShelf("继续支付", sdinformation))) {
			// if (!sdorder.getOrderStatus().equals(OrderStatus.paid)) {
			// sdorder.setOrderStatus(SDOrderStatus.autoinvalid);
			// sdorderService.update(sdorder);
			// }
			// return ERROR;
			// }

			// 会员在支付时登录.
			Member memberLogin = getLoginMember();
			if (sdorder.getMemberId() == null
					|| "".equals(sdorder.getMemberId())) {
				if (memberLogin != null) {
					sdorder.setMemberId(memberLogin.getId());
					sdorderService.update(sdorder);
				}
			}

			// 重复单号处理
			String sql = "select * from tradeInformation where ordid='" + OrdId
					+ "'";
			QueryBuilder qbCount = new QueryBuilder(sql);
			DataTable dtCountDt = qbCount.executeDataTable();
			Transaction transaction = new Transaction();
			if (dtCountDt != null && dtCountDt.getRowCount() > 1) {
				for (int i = 1; i < dtCountDt.getRowCount(); i++) {
					String idOfTradeinformation = dtCountDt.getString(i, "id");
					transaction.add(new QueryBuilder(
							"delete  from tradeInformation where id='"
									+ idOfTradeinformation + "'"));
					transaction.commit();
				}
			}
			Map<String, String> map = createPayInformation(OrdId, null,
					payBase, sdinformation, sdorder.getTotalAmount());

			MailAction.updateNoPaymentMail(oldPaySn, paySn, "a0010");

			if (map.get("errorFlag") != null
					&& "N".equals(map.get("errorFlag"))) {
				addActionError(map.get("errorMassage"));
				return ERROR;
			}

			if ("wx".equals(payType)) {
				this.payPrice = String.valueOf(new BigDecimal(total_fee).divide(new BigDecimal("100")));
			}

			if (StringUtil.isNotEmpty(fkID)) {
				updateCpsOrder(OrdId, "", fkID);
			}

			return payBase.getReturnUrl();// 跳转路径1
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.error(e.getMessage(), e);
			return ERROR;
		}
	}

	/**
	 * 购物车选择支付方式.
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Validations(requiredFields = { @RequiredFieldValidator(fieldName = "payType", message = "支付类型不能为空！") })
	public String showShopCart() {

		try {
			String oldPaySn = paySn;
			// 根据支付号准确查找
			List<SDOrder> orderList = sdorderService.getOrderByPaySn(paySn);

			// 购物车，在使用积分、优惠劵后
			// paySn根据最新金额计算得出，有可能与原来的paySn不一致；导致上边的程序查不到订单，在刷新页面时，抛出异常
			if (orderList == null || orderList.size() <= 0) {
				String[] orderArray = OrdId.split(",");
				String currentPaySn = "";
				int currentOrderLen = orderArray.length;
				for (int i = 0; i < currentOrderLen; i++) {
					SDOrder currentOrder = sdorderService.getOrderByOrderSn(orderArray[i]);
					if (StringUtil.isEmpty(currentPaySn)) {
						currentPaySn = currentOrder.getPaySn();
					}
					String newPaySn1 = currentOrder.getPaySn();
					if (!newPaySn1.equals(currentPaySn)) {
						addActionError("支付订单，不在同一个购物车中(" + currentPaySn + "," + newPaySn1 + ")");
						return ERROR;
					}
				}
				orderList = sdorderService.getOrderByPaySn(currentPaySn);
			}
			this.orderList = orderList;
			if (orderList == null || orderList.size() == 0) {
				addActionError("支付订单不能为空(" + paySn + ")");
				return ERROR;
			}
			for (SDOrder sdorder : orderList) {
				String tSDOrderStatus = String.valueOf(sdorder.getOrderStatus().ordinal());
				if (!"5".equals(tSDOrderStatus)) {
					logger.warn("该订单不是待支付状态，不允许重新支付，orderSn{}", sdorder.getOrderSn());
					addActionError("该订单（" + sdorder.getOrderSn() + "）不是待支付状态，不允许重新支付！");
					return ERROR;
				}
			}
			memberid = (String) getSession(Member.LOGIN_MEMBER_ID_SESSION_NAME);
			// 积分是否为非负整数
			if (!StringUtil.isInteger(offsetPoint)) {
				logger.warn("支付页面抵值积分的数值为非法数字，会员id为：{}", memberid);
				addActionError("支付页面抵值积分的数值为非法数字");
				return ERROR;
			}
			// 获取会员的抵值积分并与会员积分做大小校验
			if (StringUtil.isNotEmpty(memberid)) {
				if (StringUtil.isNotEmpty(orderList.get(0).getMemberId())) {
					if (!("tencent".equals(memberid))) {
						if (!(memberid.equals(orderList.get(0).getMemberId()))) {
							logger.warn("支付页面订单会员非当前用户");
							addActionError("支付页面订单会员非当前用户");
							return ERROR;
						}
					}
				}
				QueryBuilder qb = new QueryBuilder("select currentValidatePoint from member where id=?");

				qb.add(memberid);
				DataTable dt = qb.executeDataTable();
				if (dt.getRowCount() == 1) {
					String currentValidatePoint = qb.executeString();
					if (StringUtil.isEmpty(currentValidatePoint)) {
						currentValidatePoint = "0";
					}
					// 抵值积分必须小于会员的可用积分
					if (new BigDecimal(currentValidatePoint)
							.compareTo(new BigDecimal(offsetPoint)) == -1) {
						logger.warn("支付页面抵值积分大于会员的可用积分，会员id为：{}", memberid);
						addActionError("支付页面抵值积分大于会员的可用积分");
						return ERROR;
					}
				} else if ("tencent".equals(getSession().get(Member.LOGIN_MEMBER_ID_SESSION_NAME))) {
					Cookie cookie = CookieUtil.getCookieByName(getRequest(), "loginBindId");
					if (cookie != null && (!("".equals(cookie)))) {
						// 取得绑定的loginBindId值
						String loginBindId = cookie.getValue();
						QueryBuilder qb_tencent = new QueryBuilder(
								"select currentValidatePoint from member where mBindInfoForLogin_id=?");
						qb_tencent.add(loginBindId);
						DataTable dt_tencent = qb_tencent.executeDataTable();
						if (dt_tencent.getRowCount() == 1) {
							String currentValidatePoint = qb_tencent.executeString();
							if (StringUtil.isEmpty(currentValidatePoint)) {
								currentValidatePoint = "0";
							}
							// 抵值积分必须小于会员的可用积分
							if (new BigDecimal(currentValidatePoint)
									.compareTo(new BigDecimal(offsetPoint)) == -1) {
								logger.warn("支付页面抵值积分大于联合登陆会员的可用积分，联合登陆会员mBindInfoForLogin_id为：{}", loginBindId);
								offsetPoint = "0";
							}
						} else {
							logger.warn("支付页面联合登陆会员查询不到数据，联合登陆会员mBindInfoForLogin_id为：{}", loginBindId);
							offsetPoint = "0";
						}
					} else {
						logger.warn("支付页面查询联合登陆会员积分时，没有找到loginBindId的cookie,订单号为：{}", OrdId);
						offsetPoint = "0";
					}
				} else {
					logger.error("支付页面查询会员积分时，既不是会员，又不是联合登陆的会员，会员id为：{}", memberid);
					offsetPoint = "0";
				}
			} else {
				offsetPoint = "0";
			}
			// 维析所需的客户浏览器cookie的值
			Cookie cookie = CookieUtil.getCookieByName(getRequest(),
					"vlid_1001");
			if (cookie != null) {
				weixiCookie = cookie.getValue();
			} else {
				weixiCookie = "";
			}

			BigDecimal payPrice = new BigDecimal("0.00");

			if (StringUtil.isEmpty(paySn)) {
				addActionError("支付号为空");
				return ERROR;
			}
			PayBase payBase = payService.getPayBaseByPayType(payType);
			if (payBase == null) {
				addActionError("未知支付类型(" + payType + ")");
				return ERROR;
			}
			List<SDOrder> paramterList = new ArrayList<SDOrder>();

			List<SDOrder> paramList = new ArrayList<SDOrder>();
			if (orderList != null) {
				// 校验是否已支付，并显示支付订单号，支付金额
				int sorderCount = 0;
				String order_str = "";
				for (SDOrder order : orderList) {
					if (order.getOrderStatus() == Enum.valueOf(SDOrderStatus.class, "paid")) {
						paySn = order.getPaySn();
						sorderCount++;
						order_str = order_str + order.getOrderSn() + ",";
						// 多订单两个订单号一行
						if (sorderCount % 2 == 0 && sorderCount != orderList.size()) {
							order_str += "<br>";
						}
						orderList.add(order);
						paramList.add(order);
					}
					orderSnS = orderSnS + order.getOrderSn() + ",";
				}
				orderSnS = orderSnS.substring(0, orderSnS.length() - 1);
				// 支付成功显示页面
				if (sorderCount != 0) {
					orderId = order_str.substring(0, order_str.length() - 1);
					// 页面返回金额处理
					DataTable dt_amount = new QueryBuilder("select tradeamount from tradesummaryinfo where paysn =?", paySn)
							.executeDataTable();
					if (dt_amount.getRowCount() > 0) {
						callBackAmount = String.valueOf(new BigDecimal(dt_amount.getString(0, 0)).setScale(2,
								BigDecimal.ROUND_HALF_UP));
						tradeInformation = tradeInformationService.isPayFinnish(orderList.get(0).getOrderSn());
					} else {
						logger.error("支付成功后查询交易表中支付金额错误，支付流水号为：{}", paySn);
						addActionError("支付异常");
						return "newerror";
					}
					// 积分及会员等级信息
					if (StringUtil.isNotEmpty(memberid)) {
						map_point_result = new PointsCalculate().getMemberUpgradeInfo(memberid, orderList);
					} else {
						map_point_result = null;
					}

					commentList = new ArrayList<CommentInfo>();
					MemberCommentAction memberCommentAction = new MemberCommentAction();
					Map<String, Map<String, String>> purposeMap = new HashMap<String, Map<String, String>>();
					// 取得产品名称、产品类型
					Map<String, List<String>> proInfo = new HashMap<String, List<String>>();
					DataTable dt = new QueryBuilder(
							"select i.ordersn, i.productName, p.productType from sdinformation i, sdproduct p where i.ordersn in ('"
									+ orderId.replace("<br>", "") + "') and i.productId=p.productId ").executeDataTable();
					if (dt != null) {
						int dtCount = dt.getRowCount();
						List<String> list;
						for (int i = 0; i < dtCount; i++) {
							list = new ArrayList<String>();
							list.add(dt.getString(i, 1));
							list.add(dt.getString(i, 2));
							proInfo.put(dt.getString(i, 0), list);
						}

					}
					for (SDOrder order : paramList) {
						CommentInfo commentInfo = new CommentInfo();
						commentInfo.setProductName(proInfo.get(order.getOrderSn()).get(0));
						String productType = proInfo.get(order.getOrderSn()).get(1);
						// 取得产品投保目的
						if (!purposeMap.containsKey(productType)) {
							purposeMap.put(productType, memberCommentAction.getPurpose(productType));
						}
						commentInfo.setPurpose(purposeMap.get(productType));

						if (order.getCommentId() != null) {
							commentInfo.setDisCommented("");
							commentInfo.setDisComment("none");
							commentInfo
									.setPoints(new QueryBuilder(
											"select Integral from SDIntCalendar where memberId = ? and businessid = ? and Source = '1'",
											order.getMemberId(), order.getOrderSn()).executeString());
						} else {
							commentInfo.setDisCommented("none");
							commentInfo.setDisComment("");
						}
						commentList.add(commentInfo);
					}
					return "callsuccess";
				}
				for (SDOrder order : orderList) {
					order.setOffsetPoint("0");
					sdorderService.update(order);
					payPrice = payPrice.add(order.getTotalAmount());
					paramterList.add(order);
					if (order.getOrderStatus() == Enum.valueOf(
							SDOrderStatus.class, "paid")) {
						addActionError("该订单已经付款成功，请重新购买！");
						return ERROR;
					}

					/** 核保改为AJAX 暂时保留 **/
					// SDInformation sdinformation = order.getSdinformationSet()
					// .iterator().next();
					// if (ERROR.equals(isProductDownShelf("继续支付",
					// sdinformation))) {
					// if (!order.getOrderStatus().equals(OrderStatus.paid)) {
					// order.setOrderStatus(SDOrderStatus.autoinvalid);
					// sdorderService.update(order);
					// }
					// return ERROR;
					// }

					// 重复单号处理
					String sql = "select * from tradeInformation where ordid='"
							+ order.getOrderSn() + "'";
					QueryBuilder qbCount = new QueryBuilder(sql);
					DataTable dtCountDt = qbCount.executeDataTable();
					if (dtCountDt != null && dtCountDt.getRowCount() > 1) {
						Transaction transaction = new Transaction();
						for (int i = 1; i < dtCountDt.getRowCount(); i++) {
							String idOfTradeinformation = dtCountDt.getString(
									i, "id");
							transaction.add(new QueryBuilder(
									"delete  from tradeInformation where id='"
											+ idOfTradeinformation + "'"));
						}
						transaction.commit();
					}
				}
				if (StringUtil.isNotEmpty(CouponSn) && !("0".equals(CouponSn))) {// 使用优惠券
					// 获取优惠劵对象
					couponInfo = couponInfoService.getCouponInfoByCouponSn(CouponSn);
				}
				// 筛选优惠活动
				Map<String, Map<String, Object>> activity_product_info1 = ActivityCalculate.ProductShoppingCartInfo(
						paramterList, paramterList.get(0).getChannelsn(), true);
				Set keySet = activity_product_info1.keySet();
				for (Iterator it = keySet.iterator(); it.hasNext();) {
					// 活动号（包含“_no_activity”）
					String activitysn = String.valueOf(it.next());
					if (!("_no_activity".equals(activitysn))) {
						// 获取该活动关联的所有信息（包括活动信息，产品信息，优惠信息）
						Map<String, Object> map_info = activity_product_info1.get(activitysn);
						// 获取产品信息list
						Map<String, String> map_activityamont = (Map<String, String>) map_info.get("ActivityAmont");
						// 总优惠金额累计
						activityParValue = activityParValue.add(new BigDecimal(map_activityamont.get("DiscountAmount")));
						Map<String, String> map_activityinfo = (Map<String, String>) map_info.get("ActivityInfo");
						List<Map<String, String>> list_productinfo = (List<Map<String, String>>) map_info.get("ProductInfo");
						String activitySn = map_activityinfo.get("activitySn");
						for (int i = 0; i < list_productinfo.size(); i++) {
							Map<String, String> map_product = list_productinfo.get(i);
							if (!"无".equals(activitySn)) {
								// 活动号更新到订单表
								SDOrder sdord = sdorderService.getOrderByOrderSn(map_product.get("OrderSn"));
								sdord.setActivitySn(activitySn);
								sdorderService.update(sdord);
							}
						}
					}
				}
				Map<String, String> map = createPayInformation(null, orderList,
						payBase, null, payPrice);

				MailAction.updateNoPaymentMail(oldPaySn, paySn, "a0010");

				if (map.get("errorFlag") != null
						&& "N".equals(map.get("errorFlag"))) {
					addActionError(map.get("errorMassage"));
					return ERROR;
				}

			}
			if ("wx".equals(payType)) {
				this.payPrice = String.valueOf(new BigDecimal(total_fee).divide(new BigDecimal("100")));
			}

			if (StringUtil.isNotEmpty(fkID)) {
				updateCpsOrder("", paySn, fkID);
			}

			return payBase.getReturnUrl();// 跳转路径
		} catch (Exception e) {
			addActionError(e.getMessage());
			return ERROR;
		}
	}

	/**
	 * 通过订单号更新飞客ID.
	 * 
	 * @param orderSn
	 * @param fkID
	 */
	public void updateCpsOrder(String orderSn, String paySn, String fkID) {

		QueryBuilder updateCpsOrder = null;

		try {
			if (StringUtil.isNotEmpty(orderSn)) {
				updateCpsOrder = new QueryBuilder("UPDATE cps c SET c.ps = ? WHERE c.on = ?");
				updateCpsOrder.add(fkID);
				updateCpsOrder.add(orderSn);
			} else if (StringUtil.isNotEmpty(paySn)) {
				updateCpsOrder = new QueryBuilder(
						"UPDATE cps c SET c.ps = ? WHERE c.on IN(SELECT ordersn FROM sdorders WHERE paySn = ?)");
				updateCpsOrder.add(fkID);
				updateCpsOrder.add(paySn);
			}
			updateCpsOrder.executeNoQuery();
		} catch (Exception e) {
			logger.error("PayAction.updateCpsOrder 飞客ID更新异常!" + e.getMessage(), e);
		}
	}

	/**
	 * 组装支付信息
	 */
	private Map<String, String> createPayInformation(String orderSn,
			List<SDOrder> orderList, PayBase payBase,
			SDInformation sdinformation, BigDecimal payPrice) {

		Map<String, String> map = new HashMap<String, String>();
		String errorFlag = "";
		String errorMassage = "";
		try {
			String productName = "";
			if (sdinformation != null) {
				productName = sdinformation.getProductName();
				productid = sdinformation.getProductId();
			} else {
				productName = "组合产品";
			}

			GateId = payBase.getGateId();
			MerId = payBase.getMerId();
			// 支付宝
			if (payType.startsWith("zfb")) {
				// 待处理问题1
				this.subject = productName;
				this.alibody = productName;
				// 优惠劵金额抵扣处理.
				this.total_fee = coupons(payPrice, payType, orderList);

				ChkValue = "";

				// 汇付天下
			} else if (payType.startsWith("hftx")) {
				if (payPrice.compareTo(new BigDecimal("0.1")) < 0) {
					errorFlag = "N";
					errorMassage = "低于汇付天下支付平台交易的最低金额";
				}

				// 优惠劵金额抵扣处理
				OrdAmt = coupons(payPrice, payType, orderList);

				Version = payBase.getVersion();
				CmdId = "Buy";
				CurCode = "RMB";
				Pid = "";
				RetUrl = payBase.getRetUrl();
				BgRetUrl = payBase.getBgRetUrl();
				MerPriv = "";
				UsrMp = "";
				DivDetails = "";
				PayUsrId = "";
				GateUrl = payBase.getGateUrl();

				String MerKeyFile = Config.getClassesPath() + payBase.getCert();

				String MerData = Version + CmdId + MerId + paySn + OrdAmt
						+ CurCode + Pid + RetUrl + MerPriv + GateId + UsrMp
						+ DivDetails + PayUsrId + BgRetUrl;
				SecureLink sl = new SecureLink();
				int ret = sl.SignMsg(MerId, MerKeyFile, MerData);
				if (ret != 0) {
					errorFlag = "N";
					errorMassage = "签名错误 ret=" + ret;
				}
				this.ChkValue = sl.getChkValue();
				// 财付通
			} else if (payType.startsWith("cft")) {
				RequestHandler reqHandler = new RequestHandler(
						ServletActionContext.getRequest(), ServletActionContext
								.getResponse());
				reqHandler.init();
				// 设置密钥
				reqHandler.setKey(payBase.getCert());
				// 请求的url
				reqHandler.setGateUrl(payBase.getGateUrl());// 提交路径
				// 设置支付参数
				reqHandler.setParameter("partner", payBase.getMerId()); // 商户号

				// 优惠劵金额抵扣处理
				total_fee = coupons(payPrice, "cft", orderList);

				reqHandler.setParameter("out_trade_no", paySn); // 商家订单号

				reqHandler.setParameter("total_fee", total_fee); // 商品金额,以分为单位
				reqHandler.setParameter("return_url", payBase.getRetUrl()); // 交易完成后跳转的URL
				reqHandler.setParameter("notify_url", payBase.getBgRetUrl()); // 接收财付通通知的URL
				reqHandler.setParameter("body", productName); // 商品描述
				reqHandler.setParameter("bank_type", "DEFAULT"); // 银行类型
				reqHandler.setParameter("spbill_create_ip",
						ServletActionContext.getRequest().getRemoteAddr()); // 用户的公网ip
				reqHandler.setParameter("fee_type", "1");

				reqHandler.setParameter("sign_type", "MD5");
				reqHandler.setParameter("service_version", "1.0");
				reqHandler.setParameter("input_charset", "utf-8");// 对于本系统此选项为必填项
				reqHandler.setParameter("sign_key_index", "1");
				// 业务可选参数
				reqHandler.setParameter("attach", "");
				java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
						"yyyyMMddHHmmss");
				java.util.Date currentTime = new java.util.Date();// 得到当前系统时间
				reqHandler.setParameter("time_start", formatter
						.format(currentTime));
				reqHandler.setParameter("time_expire", "");
				reqHandler.setParameter("buyer_id", "");
				reqHandler.setParameter("goods_tag", "");
				// 获取debug信息,建议把请求和debug信息写入日志，方便定位问题
				// String debuginfo = reqHandler.getDebugInfo();
				ChkValue = "";
				GateUrl = reqHandler.getRequestURL();

				// 易宝支付
			} else if (payType.startsWith("ybzf")
					|| payType.startsWith("ybxyk")) {
				// 优惠劵金额抵扣处理
				p3_Amt = coupons(payPrice, "ybzf", orderList);

				// 易宝支付参数配置.
				ybzfParame(payBase);
				// 交易记录
				ChkValue = hmac;
				// 银联
			} else if (payType.startsWith("ylzf")) {
				// 购物车
				if (paySn.endsWith("G") || paySn.endsWith("01")) {
					// 重新生成银联的交易流水号 16位数字，尾数01代表购物车
					paySn = PubFun.GetYlPaySn("01");

					// 正常流程
				} else {
					// 重新生成银联的交易流水号 16位数字，尾数00代表正常购买
					paySn = PubFun.GetYlPaySn("00");
				}

				// 优惠劵金额抵扣处理
				total_fee = coupons(payPrice, "ylzf", orderList);
				// 12位长度，左补0
				total_fee = StringUtil.leftPad(total_fee, '0', 12);
				// 银联支付参数配置.
				if (!ylzfParame(payBase)) {
					errorFlag = "N";
					errorMassage = "创建私钥错误！";
				}
			}
			// 校验是否为零元支付
			else if ("zerozf".equals(payType)) {
				// 优惠劵金额抵扣处理
				RetUrl = payBase.getRetUrl();
				BgRetUrl = payBase.getBgRetUrl();
				GateUrl = payBase.getGateUrl();
				// 优惠劵金额抵扣处理.
				this.total_fee = coupons(payPrice, payType, orderList);
				if (new BigDecimal(total_fee).compareTo(new BigDecimal("0")) != 0) {
					errorFlag = "N";
					errorMassage = "非法的零元支付，支付金额不满足零元支付的条件！";
				}
			}
			// 校验是否为零元支付
			else if ("yezf".equals(payType)) {
				GateId = "yezf";
				// 优惠劵金额抵扣处理
				total_fee = coupons(payPrice, "test", orderList);
				GateUrl = payBase.getGateUrl();
				BgRetUrl = payBase.getBgRetUrl();
			}
			// 测试支付
			else if (payType.startsWith("test")) {
				GateId = "test";
				// 优惠劵金额抵扣处理
				total_fee = coupons(payPrice, "test", orderList);
				BgRetUrl = payBase.getBgRetUrl();

			} else if (payType.startsWith("wx")) {
				total_fee = coupons(payPrice, "wx", orderList);
			} else if (payType.startsWith("tlzf") || payType.startsWith("kqzf")) {
				// 优惠劵金额抵扣处理.
				this.total_fee = coupons(payPrice, payType, orderList);
				riskname = productName;
				tlzfParame(payBase);
			}
			// 直接支付
			else if ("zjzf".equals(payType)) {
				// 优惠劵金额抵扣处理
				RetUrl = payBase.getRetUrl();
				BgRetUrl = payBase.getBgRetUrl();
				GateUrl = payBase.getGateUrl();
				this.total_fee = coupons(payPrice, "zjzf", orderList);

			}

			// 记录交易信息
			if (StringUtil.isNotEmpty(orderSn)) {
				RecordTradeInformation(orderSn, GateId, payType, MerId,
						ChkValue, paySn);

			} else if (orderList != null) {

				// 根据订单最新金额更新支付交易流水号
				// 由于购物车优惠卷未上线 此代码无用
				/*
				 * String updatePaySn =
				 * "update sdorders set paySn = ?  where paySn = ? ";
				 * QueryBuilder qb = new QueryBuilder(updatePaySn); String
				 * paySn_new = PubFun.replacePaySn(payPrice + "", paySn);
				 * qb.add(paySn_new); qb.add(paySn); if
				 * (!paySn_new.equals(paySn)) { qb.executeNoQuery(); }
				 */

				for (SDOrder sdor : orderList) {
					RecordTradeInformation(sdor.getOrderSn(), GateId, payType,
							MerId, ChkValue, paySn);
				}
			}
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		map.put("errorFlag", errorFlag);
		map.put("errorMassage", errorMassage);
		return map;

	}

	// 根据订单最新金额更新支付交易流水号
	private void changePaySn(String product_totalamnout, List<SDOrder> orderList) {

		String p_orderSn = "";

		if (orderList != null) {
			p_orderSn = assemblyOrderSn(orderList);
		} else {
			p_orderSn = OrdId;
		}

		if (!payType.startsWith("ylzf")) {

			if (orderList == null) { // 非购物车情形
				// 根据订单最新金额更新支付交易流水号
				paySn = PubFun.replacePaySn(product_totalamnout, sdorder
						.getPaySn());
				sdorder.setPaySn(paySn);
				sdorder.setOffsetPoint(offsetPoint);
				sdorderService.update(sdorder);

				if (couponInfo != null) {
					// 最新生成的交易流水号关联到优惠券中
					couponInfo.setOrderSn(paySn);
					couponInfoService.update(couponInfo);

				}
				// 更新交易记录
				RecordTradeInformation(OrdId, GateId, payType, MerId, ChkValue,
						paySn);

			} else if (orderList != null) { // 购物车情形
				// 根据订单最新金额更新支付交易流水号
				String updatePaySn = "update sdorders set paySn = ? ,offsetpoint=? where paySn = ? ";
				QueryBuilder qb = new QueryBuilder(updatePaySn);
				String paySn_old = "";
				paySn_old = paySn;
				// 金额更改后的交易流水号
				paySn = PubFun.replacePaySn(product_totalamnout + "", paySn);
				qb.add(paySn);
				// 积分更新到订单中
				qb.add(offsetPoint);
				qb.add(paySn_old);
				if (!paySn_old.equals(paySn)) {
					// 更新交易流水号
					qb.executeNoQuery();
				}

				if (couponInfo != null) {
					// 最新生成的交易流水号关联到优惠券中
					couponInfo.setOrderSn(paySn);
					couponInfoService.update(couponInfo);

				}

				// 更新交易记录
				for (SDOrder sdor : orderList) {
					RecordTradeInformation(sdor.getOrderSn(), GateId, payType,
							MerId, ChkValue, paySn);
				}
			}
		} else {
			// 银联支付的情况
			if (orderList == null) {
				// 非购物车情形
				sdorder = sdorderService.getOrderByOrderSn(OrdId);
				sdorder.setOffsetPoint(offsetPoint);
				sdorderService.update(sdorder);
			} else {
				for (SDOrder sdor : orderList) {
					sdor.setOffsetPoint(offsetPoint);
					sdorderService.update(sdor);
				}
			}
		}

		tradeInfoAllpayType(paySn, p_orderSn, product_totalamnout, CouponSn,
				offsetPoint, payType);
	}

	/**
	 * 支付流水号. 记录所有支付方式. 缓存信息.
	 * 
	 * @param p_paySn
	 *            支付流水号.
	 * @param p_orderSn
	 *            订单号,多单逗号分隔.
	 * @param p_orderAmnout
	 *            订单金额.
	 * @param p_couponSn
	 *            券或活动号.
	 * @param p_integral
	 *            积分.
	 */
	public void tradeInfoAllpayType(String p_paySn, String p_orderSn,
			String p_orderAmnout, String p_couponSn, String p_integral, String p_payType) {

		TradeInfo tradeInfo;
		BigDecimal bResult = new BigDecimal(p_orderAmnout).setScale(2,
				BigDecimal.ROUND_DOWN);
		// 用订单号和交易金额查询交易缓存信息

		if ("ylzf".equals(p_payType)) {
			tradeInfo = tradeInfoService.getTradeInfoByOrder(p_orderSn,
					String.valueOf(bResult), p_payType);
		} else {
			tradeInfo = tradeInfoService.getTradeInfoByPaySn(p_paySn);
		}

		if (tradeInfo == null) {
			tradeInfo = new TradeInfo();
			tradeInfo.setPaySn(p_paySn);
			tradeInfo.setOrderSn(p_orderSn);
			tradeInfo.setCouponSn(p_couponSn);
			tradeInfo.setTotalAmnout(bResult.toString());
			tradeInfo.setIntegral(p_integral);
			tradeInfo.setRemark1("未支付");
			tradeInfo.setPayType(p_payType);
			tradeInfo.setRemark2(String.valueOf(couponprice));
			tradeInfoService.save(tradeInfo);
		} else {

			tradeInfo.setOrderSn(p_orderSn);
			tradeInfo.setCouponSn(p_couponSn);
			tradeInfo.setTotalAmnout(bResult.toString());
			tradeInfo.setIntegral(p_integral);
			tradeInfo.setPayType(p_payType);
			tradeInfo.setModifyDate(DateUtil.parseDateTime(DateUtil.getCurrentDateTime()));
			tradeInfo.setRemark2(String.valueOf(couponprice));
			tradeInfoService.update(tradeInfo);

			if ("ylzf".equals(p_payType)) {
				paySn = tradeInfo.getPaySn();
			}
		}
	}

	/**
	 * 组合订单号,逗号分隔.
	 * 
	 * @param orderList
	 * @return
	 */
	public String assemblyOrderSn(List<SDOrder> orderList) {

		StringBuffer strOrderSn = new StringBuffer();

		for (SDOrder sdor : orderList) {
			strOrderSn.append(sdor.getOrderSn() + ",");
		}

		strOrderSn.deleteCharAt(strOrderSn.length() - 1);

		return strOrderSn.toString();
	}

	/**
	 * ajax-核保. 点击支付LOG后. 易宝支付、财付通、支付宝、银联.
	 * 
	 * @return
	 */
	public String ajaxVerify() {

		Map<String, Object> jsonMap = new HashMap<String, Object>();

		if (StringUtil.isNotEmpty(OrdId)) {

			long start = System.currentTimeMillis();

			String[] orderArray = OrdId.split(",");

			for (int i = 0; i < orderArray.length; i++) {

				sdorder = sdorderService.getOrderByOrderSn(orderArray[i]);
				// 如果已经支付的订单则提示已经支付完成
				if (sdorder.getOrderStatus().equals(OrderStatus.paid)) {
					jsonMap.put("status", "false");
					jsonMap.put("error", "订单" + sdorder.getOrderSn() + "已经完成支付！");
					logger.error("ajax-核保失败：orderSn-{} - 已经完成支付", orderArray[i]);

					return ajax(JSONObject.fromObject(jsonMap).toString(), "text/html");

				} else if (sdorder.getOrderStatus().equals(OrderStatus.cancel) || "10".equals(sdorder.getOrderStatus())) {
					jsonMap.put("status", "false");
					jsonMap.put("error", "订单" + sdorder.getOrderSn() + "已经撤单，请重新进行出单操作！");
					logger.error("ajax-核保失败：orderSn-{} - 已经完成撤单", orderArray[i]);
					return ajax(JSONObject.fromObject(jsonMap).toString(), "text/html");

				} else if (sdorder.getOrderStatus().equals(OrderStatus.surrender)
						|| sdorder.getOrderStatus().equals(OrderStatus.partsurrender)) {
					jsonMap.put("status", "false");
					jsonMap.put("error", "订单" + sdorder.getOrderSn() + "已经退保，请重新进行出单操作！");
					logger.error("ajax-核保失败：orderSn-{} - 已经退保", orderArray[i]);
					return ajax(JSONObject.fromObject(jsonMap).toString(), "text/html");

				} else if (sdorder.getOrderStatus().equals(OrderStatus.change)
						|| sdorder.getOrderStatus().equals(OrderStatus.partchange)) {
					jsonMap.put("status", "false");
					jsonMap.put("error", "订单" + sdorder.getOrderSn() + "已经作废或有作废，请重新进行出单操作！");
					logger.error("ajax-核保失败：orderSn-{} - 已经作废或有作废", orderArray[i]);
					return ajax(JSONObject.fromObject(jsonMap).toString(), "text/html");

				} else if (sdorder.getOrderStatus().equals(OrderStatus.autoinvalid)) {
					jsonMap.put("status", "false");
					jsonMap.put("error", "订单" + sdorder.getOrderSn() + "已经取消，请重新进行出单操作！");
					logger.error("ajax-核保失败：orderSn-{} - 已经取消", orderArray[i]);
					return ajax(JSONObject.fromObject(jsonMap).toString(), "text/html");

				}

				Set<SDInformation> sdinformationSet = sdorder.getSdinformationSet();
				SDInformation sdinformation = null;
				for (SDInformation sd : sdinformationSet) {
					sdinformation = sd;
				}
				HashMap<String, String> map_result = isProductDownShelf("继续支付", sdinformation);
				String result_status = map_result.get("status");
				if (ERROR.equals(result_status)) {

					logger.error("继续支付-核保失败：orderSn-{} - 订单状态：{}",orderArray[i], sdorder.getOrderStatus());

					if (!sdorder.getOrderStatus().equals(OrderStatus.paid)) {

						logger.error("继续支付-核保失败：orderSn-{} - 修改订单状态为自动取消。", orderArray[i]);

						sdorder.setOrderStatus(SDOrderStatus.autoinvalid);
						sdorderService.update(sdorder);
					}
					ajaxVerify = "false";
					ajaxVerifyErrorMesage = "您的订单存在异常,请联系客服!";
					logger.error("ajax核保异常! 信息：{}订单号：{}", ajaxVerifyErrorMesage, sdinformation.getOrderSn());
				} else if ("alreadyPay".equals(result_status)) {// 份数校验，是否重复购买
					ajaxVerify = "false";
					// 获取校验错误信息
					Collection<String> collection_error = getActionErrors();
					if (collection_error.size() > 0) {
						Object error[] = collection_error.toArray();
						String error_mess = String.valueOf(error[error.length - 1]);
						if (StringUtil.isEmpty(error_mess)) {
							ajaxVerifyErrorMesage = "您的订单存在异常,请联系客服!";
						} else {
							ajaxVerifyErrorMesage = error_mess;
						}
					} else {
						ajaxVerifyErrorMesage = "您的订单存在异常,请联系客服!";
					}
					if (!sdorder.getOrderStatus().equals(OrderStatus.paid)) {
						sdorder.setOrderStatus(SDOrderStatus.temptorysave);
						sdorderService.update(sdorder);
					}
					logger.error("ajax核保异常! 信息：{}订单号：{}",ajaxVerifyErrorMesage, sdinformation.getOrderSn());
				}
			}
			logger.info("毫秒: {}", (System.currentTimeMillis() - start));
		} else {
			ajaxVerifyErrorMesage = "您的订单存在异常,请联系客服!";
			logger.error("ajax核保异常! 信息：订单号为空");
			ajaxVerify = "false";

		}
		jsonMap.put("status", ajaxVerify);
		jsonMap.put("error", ajaxVerifyErrorMesage);

		return ajax(JSONObject.fromObject(jsonMap).toString(), "text/html");

	}

	/**
	 * 交易记录.
	 * 
	 * @param ordId
	 *            orderID.
	 * @param geteId
	 *            通道编码.
	 * @param payType
	 *            支付类型.
	 * @param merId
	 *            商户ID.
	 * @param chkValue
	 *            加密串.
	 */
	public void RecordTradeInformation(String ordId, String geteId,
			String payType, String merId, String chkValue, String orderPaySn) {

		try {
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			Date currentTime = new Date();// 得到当前系统时间

			TradeInformation old = tradeInformationService
					.getTradeInformationByOrdSn(ordId);
			SDOrder sdor = sdorderService.getOrderByOrderSn(ordId);
			if (old == null) {
				tradeInformation = new TradeInformation();
				tradeInformation.setOrdAmt(String
						.valueOf(sdor.getTotalAmount()));
				tradeInformation.setOrdID(ordId);
				tradeInformation.setTradeBank(geteId);
				tradeInformation.setPayType(payType);
				tradeInformation.setMerId(merId);
				tradeInformation.setSendSign(chkValue);
				tradeInformation.setPayStatus("0");// 表示支付发送
				tradeInformation.setSendDate(formatter.format(currentTime));
				tradeInformation.setTradeCheckSeriNo(orderPaySn);
				// 维析所需的客户浏览器cookie信息
				if (weixiCookie == null || "".equals(weixiCookie)) {
					tradeInformation.setWeixiCookie("");
				} else {
					tradeInformation.setWeixiCookie(weixiCookie);
				}
				tradeInformationService.save(tradeInformation);

			} else {
				old.setOrdAmt(String.valueOf(sdor.getTotalAmount()));
				old.setOrdID(ordId);
				old.setTradeBank(geteId);
				old.setPayType(payType);
				old.setMerId(merId);
				old.setSendSign(chkValue);
				old.setPayStatus("0");// 表示支付发送
				old.setTradeCheckSeriNo(orderPaySn);
				old.setSendDate(formatter.format(currentTime));
				// 维析所需的客户浏览器cookie信息
				if (weixiCookie == null || "".equals(weixiCookie)) {
					old.setWeixiCookie("");
				} else {
					old.setWeixiCookie(weixiCookie);
				}

				tradeInformationService.update(old);
			}
		} catch (Exception e) {
			logger.error("支付记录交易方法异常：orderID:" + ordId + "|payType:" + payType
					+ "|geteId:" + geteId + e.getMessage(), e);
		}

	}

	/**
	 * /** 优惠劵金额抵扣处理.
	 * 
	 * @param TotalAmount
	 *            总金额.
	 * @param payType
	 *            支付类型.
	 * @return
	 */
	public String coupons(BigDecimal TotalAmount, String payType,
			List<SDOrder> orderList) {

		String resultTotalAmount = String.valueOf(TotalAmount);
		try {
			if (activityParValue.compareTo(new BigDecimal("0")) != 0) {
				resultTotalAmount = String.valueOf(TotalAmount.subtract(activityParValue));
			}
			if (couponInfo != null) {// 优惠券
				boolean verifyForPay = couponInfoService.couponVerifyForPay(couponInfo);
				if (verifyForPay) {
					BigDecimal b1 = new BigDecimal(resultTotalAmount);
					BigDecimal b2 = couponInfo.getParValue();
					// 优惠券类型 01：非折扣券 02：折扣券
					String couponType = "01";
					if (StringUtil.isNotEmpty(couponInfo.getProp3())) {
						couponType = couponInfo.getProp3();
					}
					if (orderList != null && orderList.size() > 0) {
						Map<String, Map<String, Object>> activity_product_info1 = ActivityCalculate.ProductShoppingCartInfo(
								orderList, orderList.get(0).getChannelsn(), true);
						BigDecimal amount = new BigDecimal("0");
						for (int j = 0; j < orderList.size(); j++) {
							boolean flag = checkCouponShow(orderList.get(j).getOrderSn().trim());
							if (flag == true) {
								// 获得扣除活动后价格
								BigDecimal total = new BigDecimal(getAmount(activity_product_info1, orderList.get(j)).get(
										"amount"));
								amount = amount.add(total);
							}
						}
						// 非折扣券的情况
						if ("01".equals(couponType)) {
							if (amount.compareTo(couponInfo.getParValue()) == -1) {
								b2 = amount;
							}
						} else {// 折扣券的情况
							// 计算出优惠的折扣 1-折扣（例1-7.5/10 = 0.25）
							BigDecimal discount = new BigDecimal(1).subtract(new BigDecimal(couponInfo.getProp4())
									.divide(new BigDecimal(10)));
							
							// 优惠金额
							b2 = amount.multiply(discount).setScale(2, BigDecimal.ROUND_HALF_UP);
							// 根据折扣券设置的最高抵扣金额设置优惠金额
							b2 = setFavPriceByMaxDeduction(b2,couponInfo.getMaxDeduction());
							
						}

					} else {
						List<SDOrder> order_list = new ArrayList<SDOrder>();
						order_list.add(sdorder);
						Map<String, Map<String, Object>> activity_product_info1 = ActivityCalculate.ProductShoppingCartInfo(
								order_list, order_list.get(0).getChannelsn(), true);
						boolean flag = checkCouponShow(sdorder.getOrderSn().trim());
						if (flag == true) {
							// 获得扣除活动后价格
							BigDecimal total = new BigDecimal(getAmount(activity_product_info1, sdorder).get("amount"));
							// 非折扣券的情况
							if ("01".equals(couponType)) {
								if (total.compareTo(couponInfo.getParValue()) == -1) {
									b2 = total;
								}
							} else {// 折扣券的情况
								// 计算出优惠的折扣 1-折扣（例1-7.5/10 = 0.25）
								BigDecimal discount = new BigDecimal(1).subtract(new BigDecimal(couponInfo.getProp4())
										.divide(new BigDecimal(10)));
								// 优惠金额
								b2 = total.multiply(discount).setScale(2, BigDecimal.ROUND_HALF_UP);
								// 根据折扣券设置的最高抵扣金额设置优惠金额
								b2 = setFavPriceByMaxDeduction(b2,couponInfo.getMaxDeduction());
							}
						}
					}
					couponprice = b2;
					BigDecimal b3 = b1.subtract(b2);
					resultTotalAmount = String.valueOf(b3.setScale(2,
							BigDecimal.ROUND_HALF_UP));
				} else {
					couponInfo.setOrderSn("");
					couponInfoService.update(couponInfo);
					addActionError("该订单的优惠劵已失效，如果需要使用优惠劵，请重新录入优惠劵号");
					return ERROR;
				}
			}
			// 积分抵值
			if (!("0".equals(offsetPoint))) {
				// 积分换算一元单位
				String PointScalerUnit = "200";
				// 积分换算规则修改时间点
				Date time = DateUtil.parseDateTime(Config
						.getConfigValue("PointScalerTime"));
				Date now = new Date();
				// 当前支付时间在积分换算规则修改时间点之后 积分规则修改
				if (now.compareTo(time) >= 0) {
					// 取得积分换算单位
					PointScalerUnit = Config.getConfigValue("PointScalerUnit");
				}
				BigDecimal pointValue = new BigDecimal(offsetPoint).divide(
						new BigDecimal(PointScalerUnit), 2,
						BigDecimal.ROUND_DOWN);
				resultTotalAmount = String.valueOf(new BigDecimal(
						resultTotalAmount).subtract(pointValue));
			}
			// 优惠抵值之后金额小于0时，置为0
			if (new BigDecimal(resultTotalAmount)
					.compareTo(new BigDecimal("0")) == -1) {
				resultTotalAmount = "0";
				// String ordersn = "";
				// ordersn=orderList.get(0).getOrderSn();
				// LogUtil.error("优惠抵值之后总金额小于0，订单号为："+ordersn);
			}
			// 根据订单最新金额更新支付交易流水号
			changePaySn(resultTotalAmount, orderList);
			//
			// 财付通单独处理.银联同样处理 以分为单位
			if ("cft".equals(payType) || "ylzf".equals(payType) || "wx".equals(payType) || "tlzf".equals(payType)
					|| "kqzf".equals(payType)) {
				resultTotalAmount = String.valueOf(new BigDecimal(
						resultTotalAmount).multiply(new BigDecimal(100)));
				if (resultTotalAmount.contains(".")) {
					resultTotalAmount = resultTotalAmount.substring(0,
							resultTotalAmount.indexOf("."));
				}
			}

		} catch (Exception e) {
			logger.error("优惠劵金额抵扣,支付类型：" + payType + "|订单号："
					+ couponInfo.getOrderSn() + "|金额：" + TotalAmount + e.getMessage(), e);
		}

		return resultTotalAmount;

	}

	/**
	 * 通联支付参数.
	 * 
	 * @param payBase
	 */
	public void tlzfParame(PayBase payBase) {

		// 支付接入版本号
		Version = payBase.getVersion();
		// 后台交易接收URL
		BgRetUrl = payBase.getBgRetUrl();
		// 页面交易接收URL
		RetUrl = payBase.getRetUrl();

		GateUrl = payBase.getGateUrl();

		// 交易日期
		TransDate = DateUtil.getCurrentDate("yyyyMMddHHmmss");
	}

	/**
	 * 支付参数.
	 * 
	 * @param payBase
	 */
	public boolean ylzfParame(PayBase payBase) {

		// 交易日期
		TransDate = DateUtil.getCurrentDate("yyyyMMdd");
		// 支付接入版本号
		Version = payBase.getVersion();
		// 后台交易接收URL
		BgRetUrl = payBase.getBgRetUrl();
		// 页面交易接收URL
		RetUrl = payBase.getRetUrl();

		GateUrl = payBase.getGateUrl();
		String MerKeyFile = Config.getClassesPath() + payBase.getCert();
		boolean buildOK = false;
		int KeyUsage = 0;
		PrivateKey key = new PrivateKey();
		try {
			// 创建私/公钥的对象
			buildOK = key.buildKey(MerId, KeyUsage, MerKeyFile);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		if (!buildOK) {
			logger.error("build error!");
			return false;
		}

		chinapay.SecureLink sl = new chinapay.SecureLink(key);
		// 数字签名
		ChkValue = sl.Sign(MerId + paySn + total_fee + "156" + TransDate
				+ "0001" + "");
		return true;
	}

	/**
	 * 
	 * @Title: zerobgReply
	 * @Description: TODO(零元支付回调)
	 * @return String 返回类型
	 * @author
	 */
	public String zerobgReply() {

		HttpServletRequest request = ServletActionContext.getRequest();
		this.paySn = request.getParameter("paySn");
		String TransAmt = request.getParameter("TransAmt");
		// 提交至PayCallBackAction.
		Btype = "1";
		this.OrdAmt = TransAmt;
		this.TrxId = this.paySn;
		this.payType = "zerozf";
		PayHttpUrlConnection.veerAction(paySn, OrdAmt, TrxId, ChkValue, Btype,
				payType);
		return "runjdt";
	}

	/**
	 * 
	 * @Title: zjzfbgReply
	 * @Description: (直接支付回调)
	 * @return String 返回类型
	 * @author
	 */
	public String zjzfbgReply() {

		String oldPaySn = paySn;

		sdorder = sdorderService.getOrderByOrderSn(OrdId);

		// show方法部分处理
		PayBase payBase = payService.getPayBaseByPayType(payType);
		if (payBase == null) {
			addActionError("未知支付类型(" + payType + ")");
			return ERROR;
		}

		Set<SDInformation> sdinformationSet = sdorder.getSdinformationSet();
		SDInformation sdinformation = null;
		for (SDInformation sd : sdinformationSet) {
			sdinformation = sd;
		}

		// 会员在支付时登录.
		Member memberLogin = getLoginMember();
		if (sdorder.getMemberId() == null
				|| "".equals(sdorder.getMemberId())) {
			if (memberLogin != null) {
				sdorder.setMemberId(memberLogin.getId());
				sdorderService.update(sdorder);
			}
		}

		// 重复单号处理
		String sql = "select * from tradeInformation where ordid=?";
		QueryBuilder qbCount = new QueryBuilder(sql, OrdId);
		DataTable dtCountDt = qbCount.executeDataTable();
		Transaction transaction = new Transaction();
		if (dtCountDt != null && dtCountDt.getRowCount() > 1) {
			for (int i = 1; i < dtCountDt.getRowCount(); i++) {
				String idOfTradeinformation = dtCountDt.getString(i, "id");
				transaction.add(new QueryBuilder(
						"delete  from tradeInformation where id='"
								+ idOfTradeinformation + "'"));
				transaction.commit();
			}
		}
		Map<String, String> map = createPayInformation(OrdId, null,
				payBase, sdinformation, sdorder.getTotalAmount());

		MailAction.updateNoPaymentMail(oldPaySn, paySn, "a0010");

		if (map.get("errorFlag") != null
				&& "N".equals(map.get("errorFlag"))) {
			addActionError(map.get("errorMassage"));
			return ERROR;
		}

		String TransAmt = sdorder.getTotalAmount().toString();
		// 提交至PayCallBackAction.
		Btype = "0";
		this.OrdAmt = TransAmt;
		this.TrxId = this.paySn;
		this.payType = "zjzf";
		String message = PayHttpUrlConnection.veerAction(paySn, OrdAmt, TrxId, ChkValue, Btype,
				payType);
		String[] msgArr = message.split(":");

		if (ERROR.equals(msgArr[0]) || "wait".equals(msgArr[0])) {
			if ("wait".equals(msgArr[0])) {
				wait = "true";

			} else {

				String msg = "select insuremsg from sdinformationrisktype where ordersn=?";
				ErrMsg = new QueryBuilder(msg, OrdId).executeString();
				if (StringUtil.isEmpty(ErrMsg)) {
					wait = "true";
				}

			}

			// if (msgArr.length == 2 && StringUtil.isNotEmpty(msgArr[1])) {
			// //addActionError(msgArr[1]);
			// ErrMsg = msgArr[1];
			// } else {
			// //addActionError("支付失败！");
			// ErrMsg = "支付失败！";
			// }
			return "error_zjzf";
		} else {
			return "runjdt";
		}
	}

	/**
	 * zjzferror:理财险直接支付错误页面. <br/>
	 *
	 * @author wwy
	 * @return
	 */
	public String zjzferror() {

		HttpServletRequest request = ServletActionContext.getRequest();
		orderSn = request.getParameter("orderSn");
		wait = request.getParameter("wait");
		ErrMsg = request.getParameter("errormsg");
		ErrMsg = URLDecoder.decode(ErrMsg);
		return "error_zjzf";
	}

	/**
	 * 
	 * @Title: yezfbgReply
	 * @Description: TODO(余额支付回调)
	 * @return String 返回类型
	 * @author
	 */
	public String yezfbgReply() {

		HttpServletRequest request = ServletActionContext.getRequest();
		this.paySn = request.getParameter("paySn");
		String TransAmt = request.getParameter("TransAmt");
		// 提交至PayCallBackAction.
		Btype = "1";
		this.OrdAmt = TransAmt;
		this.TrxId = this.paySn;
		this.payType = "yezf";
		logger.info("余额支付回调：paySn={}|TransAmt={}", paySn, TransAmt);
		PayHttpUrlConnection.veerAction(paySn, OrdAmt, TrxId, ChkValue, Btype,
				payType);
		return "runjdt";
	}
	
	

	/**
	 * 通联前台支付回调.
	 * 
	 * @return
	 */
	public String tlReply() {

		HttpServletRequest request = ServletActionContext.getRequest();
		boolean paySuccess = tlzfDeal(request);
		// 支付成功。
		if (paySuccess) {
			return "runjdt";
		} else {
			return ERROR;
		}
	}

	/**
	 * 通联后台支付回调.
	 * 
	 * @return
	 */
	public String tlbgReply() {

		HttpServletRequest request = ServletActionContext.getRequest();
		boolean paySuccess = tlzfDeal(request);
		// 支付成功。
		if (paySuccess) {
			// 提交至PayCallBackAction.
			PayHttpUrlConnection.veerAction(paySn, OrdAmt, TrxId, ChkValue,
					Btype, this.payType);
			return null;
		} else {
			return ERROR;
		}
	}

	private boolean tlzfDeal(HttpServletRequest request) {

		String merchantId = request.getParameter("merchantId");
		String version = request.getParameter("version");
		String language = request.getParameter("language");
		String signType = request.getParameter("signType");
		String payType = request.getParameter("payType");
		String issuerId = request.getParameter("issuerId");
		String paymentOrderId = request.getParameter("paymentOrderId");
		String orderNo = request.getParameter("orderNo");
		String orderDatetime = request.getParameter("orderDatetime");
		String orderAmount = request.getParameter("orderAmount");
		String payDatetime = request.getParameter("payDatetime");
		String payAmount = request.getParameter("payAmount");
		String ext1 = request.getParameter("ext1");
		String ext2 = request.getParameter("ext2");
		String payResult = request.getParameter("payResult");
		String errorCode = request.getParameter("errorCode");
		String returnDatetime = request.getParameter("returnDatetime");
		String signMsg = request.getParameter("signMsg");
		// 验签是商户为了验证接收到的报文数据确实是支付网关发送的。
		// 构造订单结果对象，验证签名。
		PaymentResult paymentResult = new PaymentResult();
		paymentResult.setMerchantId(merchantId);
		paymentResult.setVersion(version);
		paymentResult.setLanguage(language);
		paymentResult.setSignType(signType);
		paymentResult.setPayType(payType);
		paymentResult.setIssuerId(issuerId);
		paymentResult.setPaymentOrderId(paymentOrderId);
		paymentResult.setOrderNo(orderNo);
		paymentResult.setOrderDatetime(orderDatetime);
		paymentResult.setOrderAmount(orderAmount);
		paymentResult.setPayDatetime(payDatetime);
		paymentResult.setPayAmount(payAmount);
		paymentResult.setExt1(ext1);
		paymentResult.setExt2(ext2);
		paymentResult.setPayResult(payResult);
		paymentResult.setErrorCode(errorCode);
		paymentResult.setReturnDatetime(returnDatetime);
		// signMsg为服务器端返回的签名值。
		paymentResult.setSignMsg(signMsg);
		// signType为"1"时，必须设置证书路径。
		paymentResult.setCertPath(Config.getClassesPath() + "TLCert.cer");
		// 验证签名：返回true代表验签成功；否则验签失败。
		boolean verifyResult = paymentResult.verify();
		if (!verifyResult) {
			logger.error("验签失败!");
			return false;
		}
		// 验签成功，还需要判断订单状态，为"1"表示支付成功。
		if (payResult.equals("1")) {
			Btype = "2";
			this.paySn = orderNo;
			this.OrdAmt = payAmount;
			this.ChkValue = signMsg;
			this.TrxId = paymentOrderId;
			this.payType = "tlzf";
			return true;
		} else {
			logger.error("交易失败!");
			return false;
		}

	}

	private boolean kqDeal(HttpServletRequest request, String cerName) {

		try {
			// 人民币网关账号，该账号为11位人民币网关商户编号+01,该值与提交时相同。
			String merchantAcctId = request.getParameter("merchantAcctId");
			// 网关版本，固定值：v2.0,该值与提交时相同。
			String version = request.getParameter("version");
			// 语言种类，1代表中文显示，2代表英文显示。默认为1,该值与提交时相同。
			String language = request.getParameter("language");
			// 签名类型,该值为4，代表PKI加密方式,该值与提交时相同。
			String signType = request.getParameter("signType");
			// 支付方式，一般为00，代表所有的支付方式。如果是银行直连商户，该值为10,该值与提交时相同。
			String payType = request.getParameter("payType");
			// 银行代码，如果payType为00，该值为空；如果payType为10,该值与提交时相同。
			String bankId = request.getParameter("bankId");
			// 商户订单号，该值与提交时相同。
			String orderId = request.getParameter("orderId");
			// 订单提交时间，格式：yyyyMMddHHmmss，如：20071117020101,该值与提交时相同。
			String orderTime = request.getParameter("orderTime");
			// 订单金额，金额以“分”为单位，商户测试以1分测试即可，切勿以大金额测试,该值与支付时相同。
			String orderAmount = request.getParameter("orderAmount");
			// 快钱交易号，商户每一笔交易都会在快钱生成一个交易号。
			String dealId = request.getParameter("dealId");
			// 银行交易号 ，快钱交易在银行支付时对应的交易号，如果不是通过银行卡支付，则为空
			String bankDealId = request.getParameter("bankDealId");
			// 快钱交易时间，快钱对交易进行处理的时间,格式：yyyyMMddHHmmss，如：20071117020101
			String dealTime = request.getParameter("dealTime");
			// 商户实际支付金额 以分为单位。比方10元，提交时金额应为1000。该金额代表商户快钱账户最终收到的金额。
			String payAmount = request.getParameter("payAmount");
			// 费用，快钱收取商户的手续费，单位为分。
			String fee = request.getParameter("fee");
			// 扩展字段1，该值与提交时相同。
			String ext1 = request.getParameter("ext1");
			// 扩展字段2，该值与提交时相同。
			String ext2 = request.getParameter("ext2");
			// 处理结果， 10支付成功，11 支付失败，00订单申请成功，01 订单申请失败
			String payResult = request.getParameter("payResult");
			// 错误代码 ，请参照《人民币网关接口文档》最后部分的详细解释。
			String errCode = request.getParameter("errCode");
			// 签名字符串
			String signMsg = request.getParameter("signMsg");
			// 短卡号
			String bindCard = request.getParameter("bindCard");
			// 短手机尾号
			String bindMobile = request.getParameter("bindMobile");
			try {
				// 解决中文乱码
				bindMobile = java.net.URLDecoder.decode(bindMobile, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				logger.error("快钱支付bindMobile解码异常！" + e.getMessage(), e);
			}
			
			String merchantSignMsgVal = "";
			merchantSignMsgVal = appendParam(merchantSignMsgVal, "merchantAcctId", merchantAcctId);
			merchantSignMsgVal = appendParam(merchantSignMsgVal, "version", version);
			merchantSignMsgVal = appendParam(merchantSignMsgVal, "language", language);
			merchantSignMsgVal = appendParam(merchantSignMsgVal, "signType", signType);
			merchantSignMsgVal = appendParam(merchantSignMsgVal, "payType", payType);
			merchantSignMsgVal = appendParam(merchantSignMsgVal, "bankId", bankId);
			merchantSignMsgVal = appendParam(merchantSignMsgVal, "orderId", orderId);
			merchantSignMsgVal = appendParam(merchantSignMsgVal, "orderTime", orderTime);
			merchantSignMsgVal = appendParam(merchantSignMsgVal, "orderAmount", orderAmount);
			merchantSignMsgVal = appendParam(merchantSignMsgVal, "bindCard", bindCard);
			merchantSignMsgVal = appendParam(merchantSignMsgVal, "bindMobile", bindMobile);
			merchantSignMsgVal = appendParam(merchantSignMsgVal, "dealId", dealId);
			merchantSignMsgVal = appendParam(merchantSignMsgVal, "bankDealId", bankDealId);
			merchantSignMsgVal = appendParam(merchantSignMsgVal, "dealTime", dealTime);
			merchantSignMsgVal = appendParam(merchantSignMsgVal, "payAmount", payAmount);
			merchantSignMsgVal = appendParam(merchantSignMsgVal, "fee", fee);
			merchantSignMsgVal = appendParam(merchantSignMsgVal, "ext1", ext1);
			merchantSignMsgVal = appendParam(merchantSignMsgVal, "ext2", ext2);
			merchantSignMsgVal = appendParam(merchantSignMsgVal, "payResult", payResult);
			merchantSignMsgVal = appendParam(merchantSignMsgVal, "errCode", errCode);
			KqPkipair pki = new KqPkipair();
			boolean flag = pki.enCodeByCer(merchantSignMsgVal, signMsg, cerName);

			if (flag) {
				if (Integer.parseInt(payResult) != 10) {
					logger.error("快钱支付失败!返回错误码：{}!商家订单号：", errCode, orderId);
					return false;
				}
				Btype = "2";
				this.paySn = orderId;
				// 分转元
				this.OrdAmt = String.valueOf(new BigDecimal(payAmount).divide(new BigDecimal("100")));
				this.ChkValue = signMsg;
				this.TrxId = dealId;
				this.payType = "kqzf";
			} else {
				logger.error("快钱验签失败!商家订单号：{}", orderId);
				return false;

			}
			return true;
		} catch (Exception e) {
			logger.error("获取快钱返回数据错误!" + e.getMessage(), e);
			return false;
		}

	}

	public String kqbgReply() {

		try {
			DataTable dt = new QueryBuilder("select cert,retUrl from paybase where payType='kqzf'").executeDataTable();
			String cerName = dt.getString(0, 0);
			String retUrl = dt.getString(0, 1);
			HttpServletRequest request = ServletActionContext.getRequest();
			boolean paySuccess = kqDeal(request, cerName);
			HttpServletResponse response = ServletActionContext.getResponse();
			out = response.getWriter();
			out.print("<result>1</result><redirecturl>" + retUrl + "</redirecturl>");
			if (paySuccess) {
				// 确认支付回调，发送到第三方消息队列-订单编号;成功：status-0000;失败：status-1111;
				payService.sendOrderSnToReceiver(request.getParameter("orderId"), "0000", payType);
				PayHttpUrlConnection.veerAction(paySn, OrdAmt, TrxId, ChkValue, Btype, payType);
			} else {
				// 确认支付回调，发送到第三方消息队列-订单编号,成功：status-0000;失败：status-1111;
				payService.sendOrderSnToReceiver(request.getParameter("orderId"), "1111", payType);
			}
		} catch (Exception e) {
			logger.error("快钱支付回调kqbgReply处理错误!" + e.getMessage(), e);
		}
		return null;
	}

	public String kqReply() {

		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			String cerName = new QueryBuilder("select cert from paybase where payType='kqzf'").executeString();
			boolean paySuccess = kqDeal(request, cerName);

			String strPaySn = request.getParameter("orderId");
			// 前台回调--通过交易流水号,查询前台回调信息;
			Map<String, Object> result_data = payService.getReturnCallBackStatus(strPaySn);
			// 支付成功。
			if (paySuccess) {

				// 根据渠道编号，查询是否存在合作商户；0000-存在；1111-不存在
				if (result_data.get("isHavePartenrInfo").equals("0000")) {// 0000-存在合作商户
					this.partnerUrl = result_data.get("strUrl").toString();
					this.OrdId = result_data.get("orderSn").toString();
					return "partner";
				} else {
					return "runjdt";
				}
			} else {
				// 根据渠道编号，查询是否存在合作商户；0000-存在；1111-不存在
				if (result_data.get("isHavePartenrInfo").equals("0000")) {// 0000-存在合作商户
					this.partnerUrl = result_data.get("strErrorUrl").toString();
					this.OrdId = result_data.get("orderSn").toString();
					return "partner";
				} else {
					return ERROR;
				}
			}
		} catch (Exception e) {
			logger.error("快钱支付回调kqReply处理错误!" + e.getMessage(), e);
			return ERROR;
		}
	}

	public String appendParam(String returns, String paramId, String paramValue) {

		if (StringUtil.isNotEmpty(paramValue)) {
			if (!returns.equals("")) {
				returns += "&";
			}
			returns += (paramId + "=" + paramValue);
		}

		return returns;
	}

	/**
	 * 银联前台支付回调.
	 * 
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String ylReply() throws ServletException, IOException {

		HttpServletRequest request = ServletActionContext.getRequest();
		String PubKeyPath = Config.getClassesPath() + "PgPubk_yl.key";
		String MerId = request.getParameter("merid");
		String OrdId = request.getParameter("orderno");
		String TransAmt = request.getParameter("amount");// 12
		String CuryId = request.getParameter("currencycode");// 3
		String TransDate = request.getParameter("transdate");// 8
		String TransType = request.getParameter("transtype");// 4
		String Status = request.getParameter("status");
		String ChkValue = request.getParameter("checkvalue");

		boolean buildOK = false;
		boolean res = false;
		int KeyUsage = 0;
		PrivateKey key = new PrivateKey();
		try {
			// 创建公钥
			buildOK = key.buildKey("999999999999999", KeyUsage, PubKeyPath);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		// 前台回调--通过交易流水号,查询前台回调信息;
		Map<String, Object> result_data = payService.getReturnCallBackStatus(OrdId);
		if (!buildOK) {

			// 根据渠道编号，查询是否存在合作商户；0000-存在；1111-不存在
			if (result_data.get("isHavePartenrInfo").equals("0000")) {// 0000-存在合作商户
				this.partnerUrl = result_data.get("strErrorUrl").toString();
				this.OrdId = result_data.get("orderSn").toString();
				return "partner";
			} else {
				logger.error("创建公钥失败!");
				return ERROR;
			}
		}

		try {
			chinapay.SecureLink sl = new chinapay.SecureLink(key);
			res = sl.verifyTransResponse(MerId, OrdId, TransAmt, CuryId,
					TransDate, TransType, Status, ChkValue);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		if (res) {
			// 交易成功
			if ("1001".equals(Status)) {
				this.paySn = OrdId;
				// 根据渠道编号，查询是否存在合作商户；0000-存在；1111-不存在
				if (result_data.get("isHavePartenrInfo").equals("0000")) {// 0000-存在合作商户
					this.partnerUrl = result_data.get("strUrl").toString();
					this.OrdId = result_data.get("orderSn").toString();
					return "partner";
				} else {
					return "runjdt";
				}
			} else {
				// 根据渠道编号，查询是否存在合作商户；0000-存在；1111-不存在
				if (result_data.get("isHavePartenrInfo").equals("0000")) {// 0000-存在合作商户
					this.partnerUrl = result_data.get("strErrorUrl").toString();
					this.OrdId = result_data.get("orderSn").toString();
					return "partner";
				} else {
					logger.error("交易失败!");
					return ERROR;
				}
			}
		} else {
			// 根据渠道编号，查询是否存在合作商户；0000-存在；1111-不存在
			if (result_data.get("isHavePartenrInfo").equals("0000")) {// 0000-存在合作商户
				this.partnerUrl = result_data.get("strErrorUrl").toString();
				this.OrdId = result_data.get("orderSn").toString();
				return "partner";
			} else {
				logger.error("交易签名被篡改!");
				return ERROR;
			}
		}
	}

	/**
	 * 银联后台支付回调.
	 * 
	 * @return
	 */
	public String ylbgReply() {

		HttpServletRequest request = ServletActionContext.getRequest();
		String PubKeyPath = Config.getClassesPath() + "PgPubk_yl.key";
		String MerId = request.getParameter("merid");
		String OrdId = request.getParameter("orderno");
		String TransAmt = request.getParameter("amount");// 12
		String CuryId = request.getParameter("currencycode");// 3
		String TransDate = request.getParameter("transdate");// 8
		String TransType = request.getParameter("transtype");// 4
		String Status = request.getParameter("status");
		String ChkValue = request.getParameter("checkvalue");

		boolean buildOK = false;
		boolean res = false;
		int KeyUsage = 0;
		PrivateKey key = new PrivateKey();
		try {
			// 创建公钥
			buildOK = key.buildKey("999999999999999", KeyUsage, PubKeyPath);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		if (!buildOK) {
			// 确认支付回调，发送到第三方消息队列-订单编号,status:0000-成功；1111-失败；
			payService.sendOrderSnToReceiver(OrdId, "1111", "ylzf");
			logger.error("创建公钥失败!");
			return ERROR;
		}

		try {
			chinapay.SecureLink sl = new chinapay.SecureLink(key);
			res = sl.verifyTransResponse(MerId, OrdId, TransAmt, CuryId,
					TransDate, TransType, Status, ChkValue);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		if (res) {
			// 交易成功
			if ("1001".equals(Status)) {
				// 提交至PayCallBackAction.
				Btype = "2";
				this.paySn = OrdId;
				this.OrdAmt = TransAmt;
				this.ChkValue = ChkValue;
				this.TrxId = OrdId;
				this.payType = "ylzf";

				// 确认支付回调，发送到第三方消息队列-订单编号,status:0000-成功；1111-失败；
				payService.sendOrderSnToReceiver(OrdId, "0000", "ylzf");
				PayHttpUrlConnection.veerAction(paySn, OrdAmt, TrxId, ChkValue,
						Btype, payType);

				return null;
			} else {
				// 确认支付回调，发送到第三方消息队列-订单编号,status:0000-成功；1111-失败；
				payService.sendOrderSnToReceiver(OrdId, "1111", "ylzf");
				logger.error("交易失败!");
				return ERROR;
			}
		} else {
			// 确认支付回调，发送到第三方消息队列-订单编号,status:0000-成功；1111-失败；
			payService.sendOrderSnToReceiver(OrdId, "1111", "ylzf");
			logger.error("交易签名被篡改!");
			return ERROR;
		}
	}

	/**
	 * 易宝支付参数.
	 * 
	 * @param payBase
	 */
	public void ybzfParame(PayBase payBase) {

		// 商家密钥
		String keyValue = formatString(payBase.getCert());
		// 交易请求地址
		GateUrl = formatString(payBase.getGateUrl());
		// 在线支付请求，固定值 ”Buy”
		p0_Cmd = formatString("Buy");
		// 商户编号
		p1_MerId = formatString(payBase.getMerId());
		// 商户订单号
		p2_Order = formatString(paySn);
		// 支付金额
		p3_Amt = formatString(p3_Amt);
		// 交易币种
		p4_Cur = formatString("CNY");
		// 商品名称
		p5_Pid = formatString("");
		// 商品种类
		p6_Pcat = formatString("");
		// 商品描述
		p7_Pdesc = formatString("");
		// 商户接收支付成功数据的地址
		p8_Url = formatString(payBase.getRetUrl());
		// 需要填写送货信息 0：不需要 1:需要
		p9_SAF = formatString("0");
		// 商户扩展信息
		pa_MP = formatString("");
		GateId = formatString(payBase.getGateId());
		pd_FrpId = formatString(GateId); // 支付通道编码

		// 信用卡支付通道编码
		if (payType.startsWith("ybxyk")) {
			pd_FrpId = formatString("EPOS-NET");
		}
		// 默认为"1"，需要应答机制
		pr_NeedResponse = formatString("1");

		// 获得MD5-HMAC签名
		hmac = PaymentForOnlineService.getReqMd5HmacForOnlinePayment(p0_Cmd,
				p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc,
				p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, keyValue);
	}

	/**
	 * 易宝支付回调.
	 * 
	 * @return
	 */
	public String ybReply() {

		try {
			HttpServletRequest request = ServletActionContext.getRequest();

			String keyValue = formatString(Configuration.getInstance()
					.getValue("keyValue")); // 商家密钥
			String r0_Cmd = formatString(request.getParameter("r0_Cmd")); // 业务类型
			String p1_MerId = formatString(Configuration.getInstance()
					.getValue("p1_MerId")); // 商户编号
			String r1_Code = formatString(request.getParameter("r1_Code"));// 支付结果
			String r2_TrxId = formatString(request.getParameter("r2_TrxId"));// 易宝支付交易流水号
			String r3_Amt = formatString(request.getParameter("r3_Amt"));// 支付金额
			String r4_Cur = formatString(request.getParameter("r4_Cur"));// 交易币种
			String r5_Pid = formatString(request.getParameter("r5_Pid"));// 商品名称
			String r6_Order = formatString(request.getParameter("r6_Order"));// 商户订单号
			String r7_Uid = formatString(request.getParameter("r7_Uid"));// 易宝支付会员ID
			String r8_MP = formatString(request.getParameter("r8_MP"));// 商户扩展信息
			String r9_BType = formatString(request.getParameter("r9_BType"));// 交易结果返回类型
			String hmac = formatString(request.getParameter("hmac"));// 签名数据
			boolean isOK = false;
			this.paySn = r6_Order;
			this.OrdAmt = r3_Amt;
			this.ChkValue = hmac;
			this.TrxId = r2_TrxId;
			this.payType = "ybzf";

			/* 代理人回调显示 */
			this.getNewTypeFlag(paySn);

			// 校验返回数据包
			isOK = PaymentForOnlineService.verifyCallback(hmac, p1_MerId,
					r0_Cmd, r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid,
					r6_Order, r7_Uid, r8_MP, r9_BType, keyValue);

			Object[] argArr = {p1_MerId, r3_Amt, hmac, r2_TrxId, r1_Code, r9_BType, r6_Order, isOK};
			logger.info("易宝支付回调参数：p1_MerId:{}|r3_Amt:{}|hmac:{}|r2_TrxId:{}|r1_Code:{}|r9_BType:{}|r6_Order{}|isOK:{}", argArr);
			if (isOK) {
				// 在接收到支付结果通知后，判断是否进行过业务逻辑处理，不要重复进行业务逻辑处理
				if (r1_Code.equals("1")) {
					// 产品通用接口支付成功返回-浏览器重定向
					if (r9_BType.equals("1")) {
						Btype = "1";
						return "veer";
					} else if (r9_BType.equals("2")) {
						// 提交至PayCallBackAction.
						Btype = "2";
						HttpServletResponse response = ServletActionContext.getResponse();
						out = response.getWriter();
						out.print("success");
						PayHttpUrlConnection.veerAction(paySn, OrdAmt, TrxId,
								ChkValue, Btype, payType);
						return null;
					}
				}
			} else {
				logger.error("交易签名被篡改!");
				return ERROR;
			}
		} catch (Exception e) {
			logger.error("获取返回数据错误!" + e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 财付通 返回 前台页面.
	 * 
	 * @return
	 */
	public String cfReply() {

		// 密钥
		String key = "0d8026660350abf33c9396db7a96eb81";
		// 创建支付应答对象
		ResponseHandler resHandler = new ResponseHandler(getRequest(),
				getResponse());
		resHandler.setKey(key);
		// 判断签名
		if (resHandler.isTenpaySign()) {
			// 通知id
			notify_id = resHandler.getParameter("notify_id");
			// 商户订单号
			out_trade_no = resHandler.getParameter("out_trade_no");
			// 财付通订单号
			transaction_id = resHandler.getParameter("transaction_id");
			// 金额,以分为单位
			total_fee = resHandler.getParameter("total_fee");
			// 如果有使用折扣券，discount有值，total_fee+discount=原请求的total_fee
			discount = resHandler.getParameter("discount");
			// 支付结果
			trade_state = resHandler.getParameter("trade_state");
			trade_mode = resHandler.getParameter("trade_mode");

			this.paySn = this.out_trade_no;
			return "runjdt";
		} else {
			addActionError("财付通签名篡改!");
			return ERROR;
		}
	}

	/**
	 * 财付通后台返回.
	 */
	public String cfbgReply() {

		try {

			// 商户号
			String partner = "1214035701";
			// 密钥
			String key = "0d8026660350abf33c9396db7a96eb81";
			// 创建支付应答对象
			ResponseHandler resHandler = new ResponseHandler(getRequest(),
					getResponse());
			resHandler.setKey(key);
			// 判断签名
			if (resHandler.isTenpaySign()) {
				// 通知id
				String notify_id = resHandler.getParameter("notify_id");
				// 创建请求对象
				RequestHandler queryReq = new RequestHandler(null, null);
				// 通信对象
				TenpayHttpClient httpClient = new TenpayHttpClient();
				// 应答对象
				ClientResponseHandler queryRes = new ClientResponseHandler();
				// 通过通知ID查询，确保通知来至财付通
				queryReq.init();
				queryReq.setKey(key);
				queryReq
						.setGateUrl("https://gw.tenpay.com/gateway/verifynotifyid.xml");
				queryReq.setParameter("partner", partner);
				queryReq.setParameter("notify_id", notify_id);
				// 通信对象
				httpClient.setTimeOut(5);
				// 设置请求内容
				httpClient.setReqContent(queryReq.getRequestURL());
				// 后台调用
				if (httpClient.call()) {
					// 设置结果参数
					queryRes.setContent(httpClient.getResContent());
					queryRes.setKey(key);

					String retcode = queryRes.getParameter("retcode");
					String trade_state = resHandler.getParameter("trade_state");
					String trade_mode = resHandler.getParameter("trade_mode");

					// 订单号
					String out_trade_no = queryRes.getParameter("out_trade_no");
					// 支付金额
					total_fee = queryRes.getParameter("total_fee");
					// 支付流水号
					transaction_id = queryRes.getParameter("transaction_id");
					// 签名数据
					String sign = resHandler.getParameter("sign");

					this.paySn = out_trade_no;
					this.OrdAmt = total_fee;
					this.ChkValue = sign;
					this.TrxId = transaction_id;
					this.payType = "cft";
					Btype = "2";
					logger.info("财付通后台返回 out_trade_no:{}|total_fee:{}",out_trade_no, total_fee);
					if (queryRes.isTenpaySign() && "0".equals(retcode)
							&& "0".equals(trade_state)
							&& "1".equals(trade_mode)) {
						resHandler.sendToCFT("Success");
						return PayHttpUrlConnection.veerAction(paySn, OrdAmt,
								TrxId, ChkValue, Btype, payType);
					} else {
						TradeInformation old = tradeInformationService
								.getTradeInformationByOrdSn(out_trade_no);
						ErrMsg = queryRes.getParameter("retmsg");
						dealError(old, ErrMsg, transaction_id, resHandler
								.getParameter("sign"));
					}

				} else {
					ErrMsg = "后台调用通信失败" + httpClient.getResponseCode()
							+ httpClient.getErrInfo();
					logger.error("ErrMsg:{}", ErrMsg);
				}
			} else {
				ErrMsg = "通知签名验证失败";
				logger.error("ErrMsg:", ErrMsg);
			}

		} catch (Exception e) {
			ErrMsg = "财付通后台返回主程序出现异常:" + e.getMessage();
			logger.error("ErrMsg:" + ErrMsg, e);
			sdorderService
					.sendErrorMailByPaySn(paySn, ErrMsg, "", getRequest());
		}

		return null;
	}

	/**
	 * 微信扫码支付后台返回.
	 */
	@SuppressWarnings("unchecked")
	public String wxbgReply() {

		try {
			// 创建支付应答对象
			com.wxpay.wxap.ResponseHandler resHandler = new com.wxpay.wxap.ResponseHandler(getRequest(),
					getResponse());
			// 生成返回报文
			Element root = new Element("xml");
			Document Doc = new Document(root);
			// 组装返回信息
			String product_id = resHandler.getParameter("product_id");// 必填项

			com.wxpay.wxap.RequestHandler queryReq = new com.wxpay.wxap.RequestHandler(null, null);
			queryReq.init();
			queryReq.init(WxPayConfig.APP_ID, WxPayConfig.APP_SECRET, WxPayConfig.PARTNER_KEY, WxPayConfig.APP_KEY);

			String nonce_str = Sha1Util.getNonceStr();
			SortedMap<String, String> signParams = new TreeMap<String, String>();

			// 支付价格
			String cPayPrice = new QueryBuilder("SELECT totalAmnout FROM tradeinfo WHERE paysn=? ", product_id)
					.executeString();

			cPayPrice = TenpayUtil.fromYuanToFen(cPayPrice);
			String appId = WxPayConfig.APP_ID;
			Element appIdEle = new Element("appid").setText(appId);
			root.addContent(appIdEle);

			String mch_id = WxPayConfig.PARTNER;
			Element mch_idEle = new Element("mch_id").setText(mch_id);
			root.addContent(mch_idEle);

			String device_info = "";
			Element device_infoEle = new Element("device_info").setText(device_info);
			root.addContent(device_infoEle);

			Element nonce_strEle = new Element("nonce_str").setText(nonce_str);
			root.addContent(nonce_strEle);

			String body = "开心保保险订单";
			DataTable dt1 = new QueryBuilder(
					" SELECT a.productname FROM sdinformation a,sdorders b WHERE a.ordersn=b.ordersn AND b.paySn=? ",
					product_id).executeDataTable();
			if (dt1 != null && dt1.getRowCount() == 1) {
				body = dt1.getString(0, 0);
			}
			Element bodyEle = new Element("body").setText(body);
			root.addContent(bodyEle);

			String detail = "";// 非必填项
			Element detailEle = new Element("detail").setText(detail);
			root.addContent(detailEle);

			String attach = "";
			Element attachEle = new Element("attach").setText(attach);
			root.addContent(attachEle);

			String out_trade_no = product_id;
			Element out_trade_noEle = new Element("out_trade_no").setText(out_trade_no);
			root.addContent(out_trade_noEle);

			String fee_type = "CNY";
			Element fee_typeEle = new Element("fee_type").setText(fee_type);
			root.addContent(fee_typeEle);

			String total_fee = cPayPrice;
			Element total_feeEle = new Element("total_fee").setText(total_fee);
			root.addContent(total_feeEle);

			/*
			 * String is_subscribe = "N"; Element is_subscribeEle = new
			 * Element("is_subscribe").setText(is_subscribe);
			 * root.addContent(is_subscribeEle);
			 */

			String spbill_create_ip = getRequest().getRemoteAddr();
			Element spbill_create_ipELe = new Element("spbill_create_ip").setText(spbill_create_ip);
			root.addContent(spbill_create_ipELe);

			String time_start = "";
			Element time_startEle = new Element("time_start").setText(time_start);
			root.addContent(time_startEle);

			String time_expire = "";
			Element time_expireEle = new Element("time_expire").setText(time_expire);
			root.addContent(time_expireEle);

			String goods_tag = "";
			Element goods_tagEle = new Element("goods_tag").setText(goods_tag);
			root.addContent(goods_tagEle);

			String notify_url = WxPayConfig.NOTIFY_URL;// 必填
			Element notify_urlEle = new Element("notify_url").setText(notify_url);
			root.addContent(notify_urlEle);

			String trade_type = "NATIVE";
			Element trade_typeEle = new Element("trade_type").setText(trade_type);
			root.addContent(trade_typeEle);

			Element product_idEle = new Element("product_id").setText(product_id);
			root.addContent(product_idEle);

			String openid = resHandler.getParameter("openid");
			Element openidEle = new Element("openid").setText(openid);
			root.addContent(openidEle);

			signParams.put("appid", WxPayConfig.APP_ID);
			signParams.put("attach", attach);
			signParams.put("body", body);
			signParams.put("detail", detail);
			signParams.put("device_info", device_info);
			signParams.put("fee_type", fee_type);
			signParams.put("goods_tag", goods_tag);
			signParams.put("mch_id", WxPayConfig.PARTNER);
			signParams.put("nonce_str", nonce_str);
			signParams.put("notify_url", notify_url);
			signParams.put("openid", openid);
			signParams.put("out_trade_no", out_trade_no);
			signParams.put("product_id", product_id);
			signParams.put("spbill_create_ip", spbill_create_ip);
			signParams.put("time_expire", time_expire);
			signParams.put("time_start", time_start);
			signParams.put("total_fee", total_fee);
			signParams.put("trade_type", trade_type);

			String sign = queryReq.createSign(signParams);// 必填项
			Element signEle = new Element("sign").setText(sign);
			root.addContent(signEle);

			// String xmlPath1 = File.separator + "wxpay" + File.separator
			// +product_id+ "_1.xml";
			// String xmlPath = Config.getContextRealPath()+xmlPath1;
			Format format = Format.getPrettyFormat();
			format.setEncoding("gb2312");// 设置xml文件的字符为gb2312，解决中文问题
			XMLOutputter XMLOut = new XMLOutputter(format);
			String sReturn = "";
			try {
				// XMLOut.output(Doc1, new FileOutputStream(xmlPath3));
				ByteArrayOutputStream bo = new ByteArrayOutputStream();
				XMLOut.output(Doc, bo);
				// sReturn1 = XMLUtil.Encoding(xmlPath3, "UTF-8");
				sReturn = bo.toString();
			} catch (Exception e) {
				logger.error("微信支付，封装订单信息XMl转字符串异常，paySn:" + product_id + e.getMessage(), e);
			}
			// 通过通知ID查询，确保通知来至财付通
			com.wxpay.wxap.client.TenpayHttpClient httpClient = new com.wxpay.wxap.client.TenpayHttpClient();
			// 通信对象
			httpClient.setTimeOut(5);
			// 请求方式
			httpClient.setMethod("POST");
			// 证书
			/*
			 * String ca = Config.getClassesPath() + "1228919802_ca.pem";
			 * httpClient.setCaInfo(new File(ca)); // String f =
			 * Config.getClassesPath() + "1228919802_cert.pem";
			 * httpClient.setCertInfo(new File(f), "1228919802");
			 */
			// 设置请求内容
			httpClient.setReqContent(WxPayConfig.TO_POSTURL + "?" + sReturn);
			// 后台调用
			if (httpClient.call()) {
				// 创建支付应答对象
				String resContent = httpClient.getResContent();
				// System.out.println("验证ID返回字符串:" + resContent);
				Map<String, String> cMap = XMLUtil.doXMLParse(resContent);
				// 状态码
				String return_code = cMap.get("return_code");
				// 业务结果
				String result_code = cMap.get("result_code");

				if (return_code.equals(WxPayConfig.WXPAYSTATUSSUCCESS) && result_code.equals(WxPayConfig.WXPAYSTATUSSUCCESS)) {
					// 预支付交易会话标识
					String prepay_id = cMap.get("prepay_id");
					com.wxpay.wxap.RequestHandler queryReq1 = new com.wxpay.wxap.RequestHandler(null, null);
					queryReq1.init();
					queryReq1.init(WxPayConfig.APP_ID, WxPayConfig.APP_SECRET, WxPayConfig.PARTNER_KEY, WxPayConfig.APP_KEY);

					String nonce_str1 = Sha1Util.getNonceStr();

					SortedMap<String, String> signParams1 = new TreeMap<String, String>();
					signParams1.put("appid", WxPayConfig.APP_ID);
					signParams1.put("nonce_str", nonce_str1);
					signParams1.put("prepay_id", prepay_id);
					signParams1.put("result_code", "SUCCESS");
					signParams1.put("return_code", "SUCCESS");
					signParams1.put("return_msg", "");
					String sign1 = queryReq1.createSign(signParams1);// 必填项
					// 生成返回报文
					Element root1 = new Element("xml");
					Document Doc1 = new Document(root1);
					Element appidEle = new Element("appid").setText(WxPayConfig.APP_ID);
					root1.addContent(appidEle);
					Element nonce_str1Ele = new Element("nonce_str").setText(nonce_str1);
					root1.addContent(nonce_str1Ele);
					Element prepay_idEle = new Element("prepay_id").setText(prepay_id);
					root1.addContent(prepay_idEle);
					Element result_codeEle = new Element("result_code").setText("SUCCESS");
					root1.addContent(result_codeEle);
					Element return_codeEle = new Element("return_code").setText("SUCCESS");
					root1.addContent(return_codeEle);
					Element return_msgEle = new Element("return_msg").setText("");
					root1.addContent(return_msgEle);
					Element sign1Ele = new Element("sign").setText(sign1);
					root1.addContent(sign1Ele);
					// String xmlPath2 = File.separator + "wxpay" +
					// File.separator +product_id+ "_2.xml";
					// String xmlPath3 = Config.getContextRealPath()+xmlPath2;
					String sReturn1 = "";
					try {
						// XMLOut.output(Doc1, new FileOutputStream(xmlPath3));
						ByteArrayOutputStream bo = new ByteArrayOutputStream();
						XMLOut.output(Doc1, bo);
						// sReturn1 = XMLUtil.Encoding(xmlPath3, "UTF-8");
						sReturn1 = bo.toString();
					} catch (Exception e) {
						logger.error("微信支付，支付成功后，封装订单信息XMl转字符串异常，paySn:" + product_id + e.getMessage(), e);
					}
					OutputStream out = this.getResponse().getOutputStream();
					out.write(sReturn1.getBytes("utf-8"));
					out.flush();
					out.close();
				}

			} else {
				logger.error("后台调用通信失败,code:{};info:{}",httpClient.getResponseCode(), httpClient.getErrInfo());
				// 有可能因为网络原因，请求已经处理，但未收到应答。
				// 调用一次查询接口，如果返回正确结果，则进行成功操作
				if (queryPayResult(out_trade_no)) {
					return PayHttpUrlConnection.veerAction(paySn, OrdAmt,
							TrxId, ChkValue, Btype, payType);
				}
			}

		} catch (Exception e) {
			ErrMsg = "财付通后台返回主程序出现异常:" + e.getMessage();
			logger.error("ErrMsg:" + ErrMsg, e);
			sdorderService.sendErrorMailByPaySn(paySn, ErrMsg, "", getRequest());
		}

		return null;
	}

	/**
	 * 微信支付结果通知.
	 */
	@SuppressWarnings("unused")
	public void wxNotify() {

		// 接受微信后台发起支付请求的参数
		// 创建支付应答对象
		com.wxpay.wxap.ResponseHandler resHandler = new com.wxpay.wxap.ResponseHandler(getRequest(),
				getResponse());
		try {
			// 组装返回信息
			String result_code = resHandler.getParameter("result_code");// 通信标示
			String return_code = resHandler.getParameter("return_code");// 业务标识
			String transaction_id = resHandler.getParameter("transaction_id");// 微信支付流水
			String total_fee = resHandler.getParameter("total_fee");// 支付价格
			String trade_type = resHandler.getParameter("trade_type");// 交易类型
			String appid = resHandler.getParameter("appid");// appid
			String mch_id = resHandler.getParameter("mch_id");// 商户号
			String bank_type = resHandler.getParameter("bank_type");// 支付平台
																	// 财付通CFT
			String cash_fee = resHandler.getParameter("cash_fee");// 交易类型
			String fee_type = resHandler.getParameter("fee_type");// 货币类型 CNY
			String out_trade_no = resHandler.getParameter("out_trade_no");// 支付流水号
			String is_subscribe = resHandler.getParameter("is_subscribe");// 是否关注
			String nonce_str = resHandler.getParameter("nonce_str");// 随机串
			String time_end = resHandler.getParameter("time_end");// 交易类型
			String openid = resHandler.getParameter("openid");//
			String coupon_count = resHandler.getParameter("coupon_count");// 优惠信息
			String coupon_fee = resHandler.getParameter("coupon_fee");// 优惠信息
			String coupon_fee_0 = resHandler.getParameter("coupon_fee_0");// 优惠信息
			String coupon_id_0 = resHandler.getParameter("coupon_id_0");// 优惠信息
			String sign = resHandler.getParameter("sign");// 签名

			// 验签
			com.wxpay.wxap.RequestHandler queryReq = new com.wxpay.wxap.RequestHandler(null, null);
			queryReq.init();
			queryReq.init(WxPayConfig.APP_ID, WxPayConfig.APP_SECRET,
					WxPayConfig.PARTNER_KEY, WxPayConfig.APP_KEY);

			String noncestr = Sha1Util.getNonceStr();
			String timestamp = Sha1Util.getTimeStamp();
			SortedMap<String, String> signParams = new TreeMap<String, String>();
			signParams.put("appid", WxPayConfig.APP_ID);
			signParams.put("bank_type", bank_type);
			signParams.put("cash_fee", cash_fee);
			signParams.put("coupon_count", coupon_count);
			signParams.put("coupon_fee", coupon_fee);
			signParams.put("coupon_fee_0", coupon_fee_0);
			signParams.put("coupon_id_0", coupon_id_0);
			signParams.put("fee_type", fee_type);
			signParams.put("is_subscribe", is_subscribe);
			signParams.put("mch_id", WxPayConfig.PARTNER);
			signParams.put("nonce_str", nonce_str);
			signParams.put("openid", openid);
			signParams.put("out_trade_no", out_trade_no);
			signParams.put("result_code", result_code);
			signParams.put("return_code", return_code);
			signParams.put("time_end", time_end);
			signParams.put("total_fee", total_fee);
			signParams.put("trade_type", trade_type);
			signParams.put("transaction_id", transaction_id);

			String sign1 = queryReq.createSign(signParams);

			if (sign.equals(sign1)) {
				this.paySn = out_trade_no;
				this.OrdAmt = total_fee;
				this.ChkValue = sign;
				this.TrxId = transaction_id;
				this.payType = "wx";
				Btype = "2";
				logger.info("财付通后台返回 out_trade_no:{}|total_fee:{}",out_trade_no,total_fee);
				if (result_code.equals(WxPayConfig.WXPAYSTATUSSUCCESS) && return_code.equals(WxPayConfig.WXPAYSTATUSSUCCESS)) {
				    //确认支付回调，发送到第三方消息队列-订单编号,status:0000-成功；1111-失败；
				    payService.sendOrderSnToReceiver(paySn, "0000", payType);
				    
					PayHttpUrlConnection.veerAction(paySn, OrdAmt,
							TrxId, ChkValue, Btype, payType, openid);

					resHandler.sendToCFT(WxPayConfig.WXPAYSTATUSSUCCESS);
					return;

				} else {
					ErrMsg = "微信支付,微信平台返回信息:支付失败！";
					logger.error(ErrMsg);
				    //确认支付回调，发送到第三方消息队列-订单编号,status:0000-成功；1111-失败；
				    payService.sendOrderSnToReceiver(paySn, "1111", payType);
					sdorderService
							.sendErrorMailByPaySn(paySn, ErrMsg, "", getRequest());
				}
			} else {
				ErrMsg = "微信支付，验签失败!";
				logger.error(ErrMsg);
				sdorderService.sendErrorMailByPaySn(paySn, ErrMsg, "", getRequest());
			}

		} catch (Exception e) {
			ErrMsg = "微信支付，主程序异常," + e.getMessage();
			logger.error(ErrMsg, e);
			sdorderService.sendErrorMailByPaySn(paySn, ErrMsg, "", getRequest());
		}
		resHandler.sendToCFT(WxPayConfig.WXPAYSTATUSSUCCESS);
		return;
	}

	/**
	 * 微信支付-判断支付结果
	 * 
	 * @return
	 */
	public String checkPayResult() {

		int orderStatusCount = new QueryBuilder(
				" SELECT COUNT(1) FROM sdorders WHERE paySn=? AND orderStatus>=7 AND orderStatus!='8' ", paySn).executeInt();

		Map<String, Object> payResultMap = new HashMap<String, Object>();
		if (orderStatusCount >= 1) {
			payResultMap.put("state", "Suc");
		} else {
			payResultMap.put("state", "Err");
		}
		org.json.JSONObject jsonObject = new org.json.JSONObject(payResultMap);
		return ajax(jsonObject.toString(), "text/html");
	}

	/**
	 * 调用微信查询接口
	 * 
	 * @param paySn
	 *            支付流水号
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean queryPayResult(String paySn) {

		try {
			Format format = Format.getPrettyFormat();
			format.setEncoding("gb2312");// 设置xml文件的字符为gb2312，解决中文问题
			XMLOutputter XMLOut = new XMLOutputter(format);
			com.wxpay.wxap.RequestHandler queryReq = new com.wxpay.wxap.RequestHandler(null, null);
			queryReq.init();
			queryReq.init(WxPayConfig.APP_ID, WxPayConfig.APP_SECRET,
					WxPayConfig.PARTNER_KEY, WxPayConfig.APP_KEY);
			String noncestr = Sha1Util.getNonceStr();
			// 生成返回报文
			Element root1 = new Element("xml");
			Document Doc1 = new Document(root1);

			Element appidEle = new Element("appid").setText(WxPayConfig.APP_ID);
			root1.addContent(appidEle);
			Element nonce_str1Ele = new Element("mch_id").setText(WxPayConfig.PARTNER);
			root1.addContent(nonce_str1Ele);
			Element return_codeEle = new Element("nonce_str").setText(noncestr);
			root1.addContent(return_codeEle);
			Element result_codeEle = new Element("out_trade_no").setText(paySn);
			root1.addContent(result_codeEle);
			Element prepay_idEle = new Element("transaction_id").setText("");
			root1.addContent(prepay_idEle);

			SortedMap<String, String> signParams = new TreeMap<String, String>();
			signParams.put("appid", WxPayConfig.APP_ID);
			signParams.put("mch_id", WxPayConfig.PARTNER);
			signParams.put("nonce_str", noncestr);
			signParams.put("out_trade_no", paySn);
			signParams.put("transaction_id", "");

			String sign = queryReq.createSign(signParams);
			Element sign1Ele = new Element("sign").setText(sign);
			root1.addContent(sign1Ele);
			// String xmlPath = File.separator + "wxpay" + File.separator
			// +paySn+ "_query.xml";
			// String xmlPath1 = Config.getContextRealPath()+xmlPath;
			String sReturn = "";
			try {
				// XMLOut.output(Doc1, new FileOutputStream(xmlPath3));
				ByteArrayOutputStream bo = new ByteArrayOutputStream();
				XMLOut.output(Doc1, bo);
				// sReturn1 = XMLUtil.Encoding(xmlPath3, "UTF-8");
				sReturn = bo.toString();
			} catch (Exception e) {
				logger.error("微信支付，查询接口信息XMl转字符串异常，paySn:" + paySn + e.getMessage(), e);
			}
			// 通过通知ID查询，确保通知来至财付通
			com.wxpay.wxap.client.TenpayHttpClient httpClient = new com.wxpay.wxap.client.TenpayHttpClient();
			// 通信对象
			httpClient.setTimeOut(5);
			// 请求方式
			httpClient.setMethod("POST");
			/*
			 * //证书 String ca = Config.getClassesPath() + "1228919802_ca.pem";
			 * httpClient.setCaInfo(new File(ca)); // String f =
			 * Config.getClassesPath() + "1228919802_cert.pem";
			 * httpClient.setCertInfo(new File(f), "1228919802");
			 */
			// 设置请求内容
			httpClient.setReqContent(WxPayConfig.TO_QUERYURL + "?" + sReturn);
			// 后台调用微信支付查询接口
			if (httpClient.call()) {
				// 创建支付应答对象
				String resContent = httpClient.getResContent();
				// System.out.println("验证ID返回字符串:" + resContent);
				Map<String, String> cMap = XMLUtil.doXMLParse(resContent);
				// 状态码
				String return_code = cMap.get("return_code");
				if ("FAIL".equals(return_code)) {
					logger.error("调用微信支付查询接口，通信失败，错误信息：{}", cMap.get("return_msg"));
					return false;
				}
				// 业务结果
				String result_code = cMap.get("result_code");
				// 交易状态
				String trade_state = cMap.get("trade_state");
				// 支付
				String total_fee1 = cMap.get("total_fee");
				// 支付
				String transaction_id1 = cMap.get("transaction_id");
				if (return_code.equals(WxPayConfig.WXPAYSTATUSSUCCESS) && result_code.equals(WxPayConfig.WXPAYSTATUSSUCCESS)) {
					if (trade_state.equals(WxPayConfig.WXPAYSTATUSSUCCESS)) {
						this.paySn = paySn;
						this.OrdAmt = total_fee1;
						this.ChkValue = sign;
						this.TrxId = transaction_id1;
						this.payType = "wx";
						Btype = "2";
						return true;
					} else {
						logger.error("调用微信支付查询接口，支付结果：code:{},info:{}",
								trade_state, WxPayConfig.ERRORMAP.get(trade_state));
					}
				} else {
					logger.error("调用微信支付查询接口，通信失败：paySn:{}", paySn);
				}
			} else {
				logger.error("微信支付，调用查询接口网络异常!paySn:{}", paySn);
			}
		} catch (Exception e) {
			logger.error("微信支付，查询接口异常!paySn:" + paySn + e.getMessage(), e);
			return false;
		}

		return false;

	}

	/**
	 * 测试后台返回.
	 * 
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String testbgReply() throws ServletException, IOException {

		this.payType = "test";

		// Map<String,Object> result_data =
		// payService.getReturnCallBackStatus(paySn);
		// if(result_data.get("isHavePartenrInfo").equals("0000")){//0000-存在合作商户
		// this.partnerUrl = result_data.get("strErrorUrl").toString();
		// return "partner";
		// }else{
		return "veer";
		// }
	}

	/**
	 * 记录错误信息.
	 * 
	 * @param old
	 * @param ErrMsg
	 * @param TrxId
	 * @param ChkValue
	 */
	public void dealError(TradeInformation old, String ErrMsg, String TrxId,
			String ChkValue) {

		old.setErrorMsg(ErrMsg);
		old.setTradeSeriNO(TrxId);
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		java.util.Date currentTime = new java.util.Date();
		old.setReceiveDate(formatter.format(currentTime));
		old.setReturnSign(ChkValue);
		old.setTradeResult("1");
		old.setPayStatus("1");// 表示返回
		tradeInformationService.update(old);
		logger.error("ErrMsg:{}", ErrMsg);
	}

	/**
	 * 退款.
	 */
	public void refund() {

		// 由别人调用此方法 需传入订单号 正式时需加入参数
		String oldOrdId = "1000172345678157";
		SDOrder sdorder = sdorderService.getOrderByOrderSn(oldOrdId);// 根据传过来的参数查询订单
		// 判断是否支付成功
		if (sdorder.getOrderStatus() != Enum.valueOf(SDOrderStatus.class,
				"paid")) {
			addActionError("此订单未支付，不能退款");
		} else {
			// 获得支付交易结果信息 (必须是交易成功的)
			if (sdorder.getPayStatus() == Enum.valueOf(SDPayStatus.class,
					"refunded")) {
				addActionError("此订单已退款成功，不能再退款!");
			}
			TradeInformation resultTradeInfm = tradeInformationService
					.getTradeInformationByOrdId(oldOrdId);
			if (resultTradeInfm == null) {
				addActionError("此订单未支付，不能退款!");
			} else {
				String pType = resultTradeInfm.getPayType();// 取得当初的支付方式
				// 根据支付方式来确定退款方式
				RefundBase refundBase = refundBaseService
						.getRefundBaseByReturnType(pType);
				if (refundBase == null) {
					addActionError("当初支付类型(" + pType + ")不对啊！");
				}
				if (pType.startsWith("hftx")) {
					Version = refundBase.getVersion();
					CmdId = refundBase.getCmdId();
					MerId = refundBase.getMerId();
					OldOrdId = oldOrdId;
					RefAmt = resultTradeInfm.getOrdAmt();
					BgRetUrl = refundBase.getBgRetUrl();
					OrdId = "111111111111199880";// 又庞哥传过来啊 退单流水号
					GateUrl = refundBase.getFormUrl();
					DivDetails = "";
					String MerKeyFile = Config.getClassesPath()
							+ refundBase.getCert(); // 商户私钥文件路径
					// 请将MerPrK510010.key改为你的私钥文件名称
					String MerData = Version + CmdId + MerId + DivDetails
							+ RefAmt + OrdId + OldOrdId + BgRetUrl;
					SecureLink sl = new SecureLink();
					int ret = sl.SignMsg(MerId, MerKeyFile, MerData);
					if (ret != 0) {
						addActionError("签名错误");
					}
					ChkValue = sl.getChkValue();
					resultTradeInfm.setRefundId(OrdId);// 退款单号
					resultTradeInfm.setMerId(MerId);
					resultTradeInfm.setPayStatus("2");// 表示退款发送
					java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					java.util.Date currentTime = new java.util.Date();
					resultTradeInfm.setSendDate2(formatter.format(currentTime));
					resultTradeInfm.setSendSign2(ChkValue);
					tradeInformationService.update(resultTradeInfm);
					Refund.refund_hftx(Version, MerId, DivDetails, RefAmt,
							OldOrdId, BgRetUrl, ChkValue, OrdId, GateUrl);
				} else if (pType.startsWith("cft")) {
					// 商户号
					String partner = refundBase.getMerId();
					// 密钥
					String key = refundBase.getCert();
					// 创建查询请求对象
					RequestHandler reqHandler = new RequestHandler(null, null);
					// 通信对象
					TenpayHttpClient httpClient = new TenpayHttpClient();
					// 应答对象
					ClientResponseHandler resHandler = new ClientResponseHandler();
					// -----------------------------
					// 设置请求参数
					// -----------------------------
					String gateUrl = refundBase.getFormUrl();
					reqHandler.init();
					reqHandler.setKey(key);
					reqHandler.setGateUrl(gateUrl);
					// -----------------------------
					// 设置接口参数
					// -----------------------------
					reqHandler.setParameter("partner", partner);
					reqHandler.setParameter("out_trade_no", oldOrdId);
					reqHandler.setParameter("transaction_id", resultTradeInfm
							.getTradeSeriNO());
					reqHandler.setParameter("out_refund_no", "10335372743399");// 退款单号
					// 系统自动生成

					String sb1 = String.valueOf(Float
							.parseFloat(resultTradeInfm.getOrdAmt()) * 100);
					reqHandler.setParameter("total_fee", sb1.substring(0, sb1
							.length() - 2));
					reqHandler.setParameter("refund_fee", sb1.substring(0, sb1
							.length() - 2));
					reqHandler.setParameter("op_user_id", partner);
					reqHandler.setParameter("op_user_passwd", "qaz123");// 商户密码
					reqHandler.setParameter("rec_acc_truename", "");
					reqHandler.setParameter("recv_user_id", "");
					reqHandler.setParameter("reccv_user_name", "");
					// -----------------------------
					// 设置通信参数
					// -----------------------------
					// 设置请求返回的等待时间
					httpClient.setTimeOut(5);
					// 设置ca证书
					String ca = Config.getClassesPath() + "1214035701.pem";
					httpClient.setCaInfo(new File(ca)); //
					// 没有给我们这个文件啊
					// 设置个人(商户)证书
					String f = Config.getClassesPath() + "1214035701.pfx";
					httpClient.setCertInfo(new File(f), "1214035701");
					// 设置发送类型POST
					httpClient.setMethod("POST");
					// 设置请求内容
					String requestUrl = "";
					try {
						requestUrl = reqHandler.getRequestURL();
					} catch (UnsupportedEncodingException e) {
						logger.error(e.getMessage(), e);
					}
					java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					java.util.Date currentTime = new java.util.Date();
					resultTradeInfm.setSendDate2(formatter.format(currentTime));
					resultTradeInfm.setSendSign2(reqHandler
							.getParameter("sign"));

					resultTradeInfm.setPayStatus("2");// 表示退款发送

					httpClient.setReqContent(requestUrl);
					String rescontent = "null";
					// 后台调用
					if (httpClient.call()) {

						// 设置结果参数
						result = "0";// 表示退款发送成功
						resultTradeInfm.setTradeResult(result);
						tradeInformationService.update(resultTradeInfm);
						rescontent = httpClient.getResContent();
						try {
							resHandler.setContent(rescontent);
						} catch (Exception e) {
							logger.error(e.getMessage(), e);
						}
						resHandler.setKey(key);
						// 获取返回参数 retcode88222001
						String retcode = resHandler.getParameter("retcode");

						// 判断签名及结果
						if (resHandler.isTenpaySign() && "0".equals(retcode)) {
							if (sdorder.getPayStatus() == Enum.valueOf(
									SDPayStatus.class, "refunded")) {
							} else {
								result = "0";// 表示成功
								sdorder.setPayStatus(SDPayStatus.refunded);
								sdorderService.update(sdorder);
								java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat(
										"yyyy-MM-dd HH:mm:ss");
								java.util.Date currntTime = new java.util.Date();
								resultTradeInfm.setReceiveDate2(formater
										.format(currntTime));
								resultTradeInfm.setReturnSign2(resHandler
										.getParameter("sign"));
								resultTradeInfm.setTradeResult(result);
								resultTradeInfm.setReceiveRefundId(resHandler
										.getParameter("refund_id"));// 财付通返回的他系统的退款单号
								resultTradeInfm.setPayStatus("3");// 表示退款返回
								tradeInformationService.update(resultTradeInfm);
							}

						} else {
							result = "1";// 表示失败
							java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat(
									"yyyy-MM-dd HH:mm:ss");
							java.util.Date currntTime = new java.util.Date();
							resultTradeInfm.setReceiveDate2(formater
									.format(currntTime));
							resultTradeInfm.setReturnSign2(resHandler
									.getParameter("sign"));
							resultTradeInfm.setTradeResult(result);
							resultTradeInfm.setPayStatus("3");// 表示退款返回
							resultTradeInfm.setErrorMsg2(resHandler
									.getParameter("retmsg"));
							resultTradeInfm.setReceiveRefundId(resHandler
									.getParameter("refund_id"));
							tradeInformationService.update(resultTradeInfm);

						}
					} else {
						result = "1";// 表示退款发送失败
						resultTradeInfm.setTradeResult(result);
						tradeInformationService.update(resultTradeInfm);
						resultTradeInfm.setErrorMsg2("后台通讯失败："
								+ httpClient.getErrInfo());
					}
				}
			}

		}

	}

	/**
	 * 支付回调.
	 * 
	 * @return
	 */
	public String doCallBack() {

		String id = (String) getSession(Member.LOGIN_MEMBER_ID_SESSION_NAME);
		String[] argArr = {paySn, id, OrdId, payPrice};
		logger.info("支付回调(点击已完成支付),支付流水号：{} 会员ID:{} 订单号：{} 交易金额:{}", argArr);
		needUWCheckFlag = sdorderService.getUWFlagByPaySn(paySn);
		String[] orders = OrdId.split(",");
		SDOrder order = sdorderService.getOrderByOrderSn(orders[0]);
		paySn = order.getPaySn();
		this.getNewTypeFlag(paySn);
		if (!String.valueOf(order.getOrderStatus().ordinal()).equals("7")) {
			addActionError("该订单还没有支付！");
			return "newerror";
		}

		try {
			if (paySn == null || "".equals(paySn)) {
				addActionError("支付流水号不能为空");
				return ERROR;
			}
			List<SDOrder> sorderList = null;
			sorderList = sdorderService.getOrderByOrderSns(OrdId.replace(
					",", "','"));

			if (sorderList == null) {
				addActionError("系统不存在该支付流水号" + paySn);
				return ERROR;
			}

			boolean isPay = false;
			int index = 1;
			while (index <= 3) {
				for (SDOrder sdor : sorderList) {
					tradeInformation = tradeInformationService
							.isPayFinnish(sdor.getOrderSn());
					if (tradeInformation != null) {
						paymentSn = tradeInformation.getTradeSeriNO();
						isPay = true;
						break;
					}
				}
				if (isPay) {
					break;
				}
				Thread.sleep(1500);
				index++;
			}
			// 判断tradeInformation 一个订单号 对应的订单记录如果如果大于>=2 则只留一条记录
			orderId = "";
			memberid = ""; // 会员id
			totalamount = "";// 订单金额
			pieces = "";// 被保人个数
			productid = "";// 产品id
			companyname = "";// 公司名称
			riskname = "";// 险种名称
			int sorderCount = 0;
			commentList = new ArrayList<CommentInfo>();
			String strProductId = "";
			String productType = "";
			SDInformation Sdinformation;
			Map<String, Map<String, String>> purposeMap = new HashMap<String, Map<String, String>>();
			MemberCommentAction memberCommentAction = new MemberCommentAction();
			onLineCallBackInfo = null;
			// 线上回访的产品
			@SuppressWarnings("unchecked")
			Mapx<String, String> productcodes =  CacheManager.getMapx("Code", "OnLineCallBackProductID");
			for (SDOrder sdor : sorderList) {

				String sql = "select * from tradeInformation where ordid='"
						+ sdor.getOrderSn() + "'";
				QueryBuilder qbCount = new QueryBuilder(sql);
				DataTable dtCountDt = qbCount.executeDataTable();
				Transaction transaction = new Transaction();
				if (dtCountDt != null && dtCountDt.getRowCount() > 1) {
					for (int i = 1; i < dtCountDt.getRowCount(); i++) {
						String idOfTradeinformation = dtCountDt.getString(i,
								"id");
						transaction.add(new QueryBuilder(
								"delete  from tradeInformation where id='"
										+ idOfTradeinformation + "'"));
					}
					transaction.commit();
				}
				GALog.saveGALogRecord(gaLogService, GALog.PAY_SUCCESS, null,
						sdor.getOrderSn());
				sorderCount++;
				orderId = orderId + sdor.getOrderSn() + ",";

				// 多订单两个订单号一行
				if (sorderCount % 2 == 0 && sorderCount != sorderList.size()) {
					orderId += "<br>";
				}
				Sdinformation = sdor.getSdinformationSet().iterator().next();
				// 维析传递的参数
				try {
					memberid = memberid + sdor.getMemberId() + ",";
					totalamount = totalamount + sdor.getTotalAmount() + ",";

					strProductId = Sdinformation.getProductId();
					
					//理财险
					String[] lcx_arr = Config.getValue("LicaiBaoxianProducts").split(",");
					List<String> listLcx = Arrays.asList(lcx_arr);
					if (StringUtil.isNotEmpty(strProductId) && listLcx.contains(strProductId)) {
						lcbxFlag = "true";
					}
					
					pieces = pieces + Sdinformation.getSdinformationinsuredSet().size() + ",";

					productid = productid + strProductId + ",";
					// ++++++++++++++++百分点埋点需要
					// BD_INFO += strProductId + "@" + sdor.getTotalAmount() +
					// "@" + sdor.getProductNum() + "|";
					// ++++++++++++++++百分点埋点需要
					QueryBuilder qb_riskname = new QueryBuilder(
							"SELECT co.codename as codename, pro.producttype FROM sdproduct pro,zdcode co WHERE productid=?  AND co.codetype ='risktype' AND co.codevalue LIKE '%00' AND LEFT(co.codevalue,1) = pro.producttype",
							strProductId);
					DataTable dt = qb_riskname.executeDataTable();
					if (dt != null) {
						riskname = riskname + dt.getString(0, 0) + ",";
						productType = dt.getString(0, 1);
					}

					QueryBuilder qb_companyname = new QueryBuilder(
							"SELECT codename FROM zdcode WHERE codetype = 'SupplierCode'  AND codevalue=?",
							Sdinformation.getInsuranceCompany());
					companyname = companyname
							+ qb_companyname.executeDataTable().getString(0, 0)
							+ ",";

				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}

				CommentInfo commentInfo = new CommentInfo();
				commentInfo.setOrderSn(sdor.getOrderSn());
				commentInfo.setProductName(Sdinformation.getProductName());
				// 取得产品投保目的
				if (!purposeMap.containsKey(productType)) {
					purposeMap.put(productType, memberCommentAction.getPurpose(productType));
				}
				commentInfo.setPurpose(purposeMap.get(productType));
				if (sdor.getCommentId() != null) {
					commentInfo.setDisCommented("");
					commentInfo.setDisComment("none");
					commentInfo.setPoints(new QueryBuilder(
							"select Integral from SDIntCalendar where memberId = ? and businessid = ? and Source = '1'",
							sdor.getMemberId(), sdor.getOrderSn()).executeString());
				} else {
					commentInfo.setDisCommented("none");
					commentInfo.setDisComment("");
				}

				commentList.add(commentInfo);
				
				if (StringUtil.isNotEmpty(Sdinformation.getProductId()) && productcodes.containsKey(Sdinformation.getProductId())) {
					// 取得线上回访展示信息
					AlipayAction alipayAction = new AlipayAction();
					onLineCallBackInfo = alipayAction.getOnLineCallBackInfo(Sdinformation, sdor.getTotalAmount().toString(), sdor.getPayPrice());
				}
			}
			// ++++++++++++++++百分点埋点需要
			/*
			 * if (StringUtil.isNotEmpty(BD_INFO)) { BD_INFO =
			 * BD_INFO.substring(0, BD_INFO.length() - 1); }
			 */
			// ++++++++++++++++百分点埋点需要
			if (StringUtil.isNotEmpty(orderId)) {
				orderId = orderId.substring(0, orderId.length() - 1);
				try {
					memberid = memberid.substring(0, memberid.length() - 1);
					totalamount = totalamount.substring(0,
							totalamount.length() - 1);
					pieces = pieces.substring(0, pieces.length() - 1);
					productid = productid.substring(0, productid.length() - 1);
					riskname = riskname.substring(0, riskname.length() - 1);
					companyname = companyname.substring(0,
							companyname.length() - 1);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
			if (!isPay) {
				logger.info("未支付完成!支付流水号号：{}", paySn);
				addActionError("未支付完成");
				return "newerror";
			}
			// String paySnFlag = paySn.substring(paySn.length() - 1, paySn
			// .length());
			// 获取当前登录会员ID
			String memberID = (String) getSession(Member.LOGIN_MEMBER_ID_SESSION_NAME);
			// 取不到登录会员ID并且不是合并支付的情况
			if (StringUtil.isEmpty(memberID) && sorderList.size() == 1) {
				String ordersn = sorderList.get(0).getOrderSn();
				// 用订单号在session中取得新用户名
				newUserName = (String) getSession(ordersn);
				// 若取不到新用户名的情况
				if (StringUtil.isEmpty(newUserName)) {
					// 判断是否是新用户
					userRegistedCheck(ordersn);
				}
			}
			// 页面返回金额处理
			DataTable dt_amount = new QueryBuilder("select tradeamount from tradesummaryinfo where paysn =?",
					order.getPaySn()).executeDataTable();
			if (dt_amount.getRowCount() > 0) {
				callBackAmount = String.valueOf(new BigDecimal(dt_amount.getString(0, 0)).setScale(2,
						BigDecimal.ROUND_HALF_UP));
			} else {
				logger.error("支付成功后查询交易表中支付金额错误，支付流水号为：{}", paySn);
				addActionError("支付异常");
				return "newerror";
			}
			// 积分及会员等级信息
			if (StringUtil.isNotEmpty(memberID)) {
				map_point_result = new PointsCalculate().getMemberUpgradeInfo(memberID, sorderList);
			} else {
				map_point_result = null;
			}

			return "callsuccess";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionError(e.getMessage());
			return ERROR;
		}

	}

	/**
	 * 
	 * @Title: freePay
	 * @Description: TODO(赠险支付)
	 * @return String 返回类型
	 * @author
	 */
	public String freePay() {

		try {
			sdorder = sdorderService.getOrderByOrderSn(orderSn);
			if (sdorder == null) {
				sdorder = sdorderService.get(orderId);
			}
			if (sdorder == null) {
				logger.warn("赠险支付订单数据为空，订单号orderSn=：{};orderId={}", orderSn, orderId);
				addActionError("支付异常");
				return ERROR;
			}
			// 支付流水号
			paySn = sdorder.getPaySn();
			// 如果没有则新建 或有号是银联的支付号重新生成
			if (StringUtil.isEmpty(paySn) || paySn.endsWith("00") || paySn.endsWith("01")) {
				paySn = PubFun.GetPaySn(sdorder.getTotalAmount() + "", "D");
			}
			Member memberLogin = getLoginMember();
			if (sdorder.getMemberId() == null || "".equals(sdorder.getMemberId())) {
				if (memberLogin != null) {
					sdorder.setMemberId(memberLogin.getId());
				}
			}
			String paidState = String.valueOf(sdorder.getPayStatus().ordinal());
			if ("2".equals(paidState)) {
				// 页面返回金额处理
				DataTable dt_amount = new QueryBuilder("select tradeamount from tradesummaryinfo where paysn =?",
						sdorder.getPaySn()).executeDataTable();
				if (dt_amount.getRowCount() > 0) {
					orderId = OrdId;
					callBackAmount = String.valueOf(new BigDecimal(dt_amount.getString(0, 0)).setScale(2,
							BigDecimal.ROUND_HALF_UP));
					tradeInformation = tradeInformationService.isPayFinnish(sdorder.getOrderSn());
				} else {
					logger.error("点击支付logo，查询交易表中支付金额错误，支付流水号为：{}", paySn);
					addActionError("支付异常");
					return "newerror";
				}

				commentList = new ArrayList<CommentInfo>();
				CommentInfo commentInfo = new CommentInfo();
				commentInfo.setOrderSn(sdorder.getOrderSn());
				DataTable dt = new QueryBuilder(
						"select i.productName, p.productType from sdinformation i, sdproduct p where i.ordersn = ? and i.productId=p.productId ",
						sdorder.getOrderSn()).executeDataTable();
				if (dt != null) {
					commentInfo.setProductName(dt.getString(0, 0));
					String productType = dt.getString(0, 1);
					// 取得产品投保目的
					if (StringUtil.isNotEmpty(productType)) {
						MemberCommentAction memberCommentAction = new MemberCommentAction();
						commentInfo.setPurpose(memberCommentAction.getPurpose(productType));
					}
				}
				if (sdorder.getCommentId() != null) {
					commentInfo.setDisCommented("");
					commentInfo.setDisComment("none");
					commentInfo.setPoints(new QueryBuilder(
							"select Integral from SDIntCalendar where memberId = ? and businessid = ? and Source = '1'",
							sdorder.getMemberId(), sdorder.getOrderSn()).executeString());
				} else {
					commentInfo.setDisCommented("none");
					commentInfo.setDisComment("");
				}
				commentList.add(commentInfo);
				onLineCallBackInfo = null;
				return "callsuccess";
			}
			// 订单状态更新
			sdorder.setOrderStatus(SDOrderStatus.prepay);
			sdorder.setPaySn(paySn);
			sdorder.setPayType("freePay");
			sdorder.setPayStatus(SDPayStatus.paid);
			sdorder.setOrderStatus(SDOrderStatus.paid);
			sdorderService.update(sdorder);
			// 处理会员
			String memberID = "";
			boolean registerFlag = false;// 是否是未登录购买，false:不是
			if (StringUtil.isNotEmpty(sdorder.getMemberId())) {
				memberID = sdorder.getMemberId();
			} else {
				memberID = (String) getSession(Member.LOGIN_MEMBER_ID_SESSION_NAME);
			}
			// 处理生产环境，联合登录订单无法绑定会员的问题，原因：支付后session重置，获取不到任何信息
			if (paySn.endsWith("D") || paySn.endsWith("00")) {
				TradeInformation information = tradeInformationService.getTradeInformationByOrdSn(sdorder.getOrderSn());
				if (StringUtil.isEmpty(memberID)) {
					registerFlag = true;
					AutoRegister ar = new AutoRegister();
					ar.userRegistedCheck(sdorder, sdorder.getOrderSn(), information);
				} else if ("tencent".equals(memberID)) {
					registerFlag = true;
					dealUnionLogin(sdorder, information);
				}
			}
			// 实时查询订单表中数据，获取memberid
			DataTable dt_memberid = new QueryBuilder("select memberid from sdorders where ordersn=?", sdorder.getOrderSn())
					.executeDataTable();
			memberID = dt_memberid.getString(0, 0);
			try {
				// 重复单号处理
				String sql = "select * from tradeInformation where ordid='" + OrdId + "'";
				QueryBuilder qbCount = new QueryBuilder(sql);
				DataTable dtCountDt = qbCount.executeDataTable();
				Transaction transaction = new Transaction();
				if (dtCountDt != null && dtCountDt.getRowCount() > 1) {
					for (int i = 1; i < dtCountDt.getRowCount(); i++) {
						String idOfTradeinformation = dtCountDt.getString(i, "id");
						transaction.add(new QueryBuilder("delete  from tradeInformation where id='" + idOfTradeinformation
								+ "'"));
						transaction.commit();
					}
				}
				try {
					// 维析所需的客户浏览器cookie的值
					Cookie cookie = CookieUtil.getCookieByName(getRequest(),
							"vlid_1001");
					if (cookie != null) {
						weixiCookie = cookie.getValue();
					} else {
						weixiCookie = "";
					}
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date currentTime = new Date();// 得到当前系统时间
					TradeInformation old = tradeInformationService.getTradeInformationByOrdSn(orderSn);
					if (old == null) {
						tradeInformation = new TradeInformation();
						tradeInformation.setOrdAmt(String.valueOf(sdorder.getTotalAmount()));
						tradeInformation.setOrdID(orderSn);
						tradeInformation.setTradeBank("");
						tradeInformation.setPayType("freePay");
						tradeInformation.setMerId(memberID);
						tradeInformation.setSendSign("");
						tradeInformation.setPayStatus("1");// 表示支付发送
						tradeInformation.setSendDate(formatter.format(currentTime));
						tradeInformation.setTradeCheckSeriNo(paySn);
						// 维析所需的客户浏览器cookie信息
						if (weixiCookie == null || "".equals(weixiCookie)) {
							tradeInformation.setWeixiCookie("");
						} else {
							tradeInformation.setWeixiCookie(weixiCookie);
						}
						tradeInformation.setReceiveDate(formatter.format(currentTime));
						tradeInformationService.save(tradeInformation);
					} else {
						old.setOrdAmt(String.valueOf(sdorder.getTotalAmount()));
						old.setOrdID(orderSn);
						old.setTradeBank("");
						old.setPayType("freePay");
						old.setMerId(memberID);
						old.setSendSign("");
						old.setPayStatus("1");// 表示支付发送
						old.setTradeCheckSeriNo(paySn);
						old.setSendDate(formatter.format(currentTime));
						// 维析所需的客户浏览器cookie信息
						if (weixiCookie == null || "".equals(weixiCookie)) {
							old.setWeixiCookie("");
						} else {
							old.setWeixiCookie(weixiCookie);
						}
						old.setReceiveDate(formatter.format(currentTime));
						tradeInformationService.update(old);
						tradeInformation = old;
					}
				} catch (Exception e) {
					logger.error("支付记录交易方法异常：orderID:" + orderSn + "|payType:freepay" + e.getMessage(), e);
				}

				Transaction trans = new Transaction();
				// 定义时间格式
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date;
				try {
					date = sdf.parse(PubFun.getCurrent());
					TradeInfoSchema tradeinfoschema = new TradeInfoSchema();
					tradeinfoschema.setpayType("freepay");
					// remark1 记录是否支付
					tradeinfoschema.setremark1("已支付");
					tradeinfoschema.setCreateDate(date);
					tradeinfoschema.setModifyDate(date);
					tradeinfoschema.setpaySn(paySn);
					trans.add(tradeinfoschema, Transaction.INSERT);
					// 更新tradesummaryinfo表
					TradeSummaryInfoSchema tradesummaryinfoschema = new TradeSummaryInfoSchema();
					tradesummaryinfoschema.setid(NoUtil.getMaxNo("TradeSummaryID", 11));
					tradesummaryinfoschema.setPaySn(paySn);
					tradesummaryinfoschema.setTradeSn("");
					tradesummaryinfoschema.setTradeResult("0");
					tradesummaryinfoschema.setCouponSn("");
					tradesummaryinfoschema.setOrderSns(orderSn);
					tradesummaryinfoschema.setActivitySumAmount("");
					tradesummaryinfoschema.setPointSumAmount("");
					tradesummaryinfoschema.setCouponSumAmount("");
					tradesummaryinfoschema.setPayType("freepay");
					tradesummaryinfoschema.setPayTypeName("赠险支付");
					tradesummaryinfoschema.setTotalAmount(String.valueOf(sdorder.getTotalAmount()));
					// 支付金额
					tradesummaryinfoschema.setTradeAmount("0");
					tradesummaryinfoschema.setCreateDate(date);
					trans.add(tradesummaryinfoschema, Transaction.INSERT);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
				// 提交事务
				if (!trans.commit()) {
					logger.error("更新TradeInfo表时，提交事务发生异常！");
				}
				List<SDOrder> sdorderList = sdorderService.getOrderByOrderSns(sdorder.getOrderSn());
				// 订单支付成功后邮件通知会员
				if (StringUtil.isNotEmpty(memberID) && !registerFlag) {
					try {
						Map<String, Object> map = new HashMap<String, Object>();
						List<String> orderSns = new ArrayList<String>();
						for (SDOrder sorder : sdorderList) {
							orderSns.add(sorder.getOrderSn());
						}
						map.put("orderSns", orderSns);
						ActionUtil.sendMessage("wj00016", map);
					} catch (Exception e) {
						logger.error("订单支付完成(已登录)出现异常: LoginSuccessPay方法出现异常" + e.getMessage(), e);
					}

				}
				setSession(Member.LOGIN_MEMBER_ID_SESSION_NAME, memberID);
				try {
					Member member = getLoginMember();

					// 支付成功后短信通知会员
					sendMessage(sdorderList);
					// 统计渠道
					countChannel(member);
					//
					for (SDOrder sdorder : sdorderList) {

						for (SDInformation sdi : sdorder.getSdinformationSet()) {
							try {
								// 调用经代通发送报文
								class MutliThread extends Thread {

									private SDInformation sdi;
									private SDOrder sdo;
									private Set<SDInformationInsured> sdinformationInsuredSet;

									MutliThread(SDInformation sdi1, SDOrder sdo1) {

										sdi = sdi1;
										sdo = sdo1;
										sdinformationInsuredSet = sdi1
												.getSdinformationinsuredSet();
									}

									@Override
									public void run() {

										InsureTransferNew itn = new InsureTransferNew();
										List<String> UWUNSucess = sdorderService.checkUnderwriting(sdo, null);
										String comCode = "";
										boolean flag = true;
										String appStatus = "";
										String insureMsg = "";
										if (sdi != null) {
											comCode = sdi.getInsuranceCompany();
											sdinformationInsuredSet = sdi
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
										if (flag) {
											itn.callInsTransInterface(sdi.getInsuranceCompany(), sdo.getOrderSn(),
													UWUNSucess);
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

												ErrMsg = "经代通失败:" + insureMsg;

												if (StringUtil.isEmpty(insureMsg)) {
													ErrMsg = "经代通失败:未收到保险公司回写结果";
												}

												sendErrorMail(sdo.getOrderSn(), ErrMsg);

											}
										}
									}
								}
								new MutliThread(sdi, sdorder).start();
							} catch (Exception e) {
								logger.error("支付成功后经代通发送报文发生异常" + e.getMessage(), e);
							}
							// 增加产品购买数量
							productIdList.add(sdi.getProductId());
						}
						addProductSalesVolume();
					}
				} catch (Exception e) {
					logger.error("支付成功回调出现异常:" + "程序出现异常 paySn:" + paySn + e.getMessage(), e);
					ErrMsg = "支付后台返回主程序出现异常:" + e.getMessage();
					sdorderService.sendErrorMailByPaySn(paySn, ErrMsg, "", getRequest());
					return ERROR;
				}
			} catch (Exception e) {
				addActionError(e.getMessage());
				return ERROR;
			}
			callBackAmount = String.valueOf(new BigDecimal("0").setScale(2, BigDecimal.ROUND_HALF_UP));
			orderId = sdorder.getOrderSn();
			try {
				// 向维析发送支付完成数据,在经代通调用之前,防止经代通调用失败
				for (SDInformation sdi : sdorder.getSdinformationSet()) {
					new PayCallBackAction().sendWeixiData(sdorder, tradeInformation, sdi.getProductName());
				}
			} catch (Exception e) {
				logger.error("向维析发送支付完成数据时发生异常" + e.getMessage(), e);
			}

			commentList = new ArrayList<CommentInfo>();
			CommentInfo commentInfo = new CommentInfo();
			commentInfo.setOrderSn(sdorder.getOrderSn());
			DataTable dt = new QueryBuilder(
					"select i.productName, p.productType from sdinformation i, sdproduct p where i.ordersn = ? and i.productId=p.productId ",
					sdorder.getOrderSn()).executeDataTable();
			if (dt != null) {
				commentInfo.setProductName(dt.getString(0, 0));
				String productType = dt.getString(0, 1);
				// 取得产品投保目的
				if (StringUtil.isNotEmpty(productType)) {
					MemberCommentAction memberCommentAction = new MemberCommentAction();
					commentInfo.setPurpose(memberCommentAction.getPurpose(productType));
				}
			}
			if (sdorder.getCommentId() != null) {
				commentInfo.setDisCommented("");
				commentInfo.setDisComment("none");
				commentInfo.setPoints(new QueryBuilder(
						"select Integral from SDIntCalendar where memberId = ? and businessid = ? and Source = '1'", sdorder
								.getMemberId(), sdorder.getOrderSn()).executeString());
			} else {
				commentInfo.setDisCommented("none");
				commentInfo.setDisComment("");
			}
			commentList.add(commentInfo);
			onLineCallBackInfo = null;
			return "callsuccess";
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 统计渠道.
	 * 
	 * @param mem
	 */
	public void countChannel(Member mem) {

		/* 开始 jingrujia 1054 需求- 渠道统计2012-10-30 start */
		try {
			MemberChannel memberChannel = new MemberChannel();
			if (mem != null && !"".equals(mem)) {
				memberChannel.setMemberId(mem.getId());
			}
			String channelId = "";
			String channelWay = "";
			String keyWord = "";
			String channelTemp = (String) getSession("channelId");
			String channelWemp = (String) getSession("channelWay");

			if (StringUtil.isNotEmpty(channelTemp) || StringUtil.isNotEmpty(channelWemp)) {
				channelWay = channelWemp;
				channelId = channelTemp;
			} else {
				if (getRequest().getCookies() != null) {
					Cookie[] ChannelCookie = getRequest().getCookies();
					for (Cookie cookie : ChannelCookie) {
						if ("channelId".equals(cookie.getName())) {

							channelId = cookie.getValue();
						}
						if ("channelWay".equals(cookie.getName())) {

							channelWay = cookie.getValue();
						}
						if ("keyWord".equals(cookie.getName())) {

							keyWord = cookie.getValue();
						}
					}
				}
			}
			if (StringUtil.isNotEmpty(channelId)) {

				memberChannel.setChannelId(channelId);
				memberChannel.setType("1");// 0—代表注册 1-代表支付
				memberChannel.setChannelWay(channelWay);
				memberChannel.setSubType(keyWord);
				memberChannelService.save(memberChannel);

			}
		} catch (Exception e) {
			logger.error("统计渠道方法出现异常:countChannel方法出现异常" + e.getMessage(), e);
		}
		/* 结束 jingrujia 1054 需求- 渠道统计2012-10-30 end */
	}

	/**
	 * 增加产品销量
	 */
	private void addProductSalesVolume() {

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
		}
	}

	/**
	 * 
	 * @Title: sendMessage
	 * @Description: TODO(支付成功后给投保人发送短信)
	 * @return void 返回类型
	 * @author zhangjing
	 */
	@SuppressWarnings("static-access")
	private void sendMessage(List<SDOrder> sdorderList) {

		try {
			if (sdorderList != null && sdorderList.size() >= 1) {
				for (SDOrder sdor : sdorderList) {
					String orSn = sdor.getOrderSn();
					Set<SDInformation> informationSet = sdor.getSdinformationSet();
					if (informationSet != null && informationSet.size() > 0) {
						SDInformation information = informationSet.iterator().next();
						Set<SDInformationAppnt> appntSet = information.getSdinformationappntSet();
						if (appntSet != null && appntSet.size() > 0) {
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("orderSn", orSn);
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
	 * QQ联合登录绑定信息
	 * 
	 * @param old
	 */
	private void dealUnionLogin(SDOrder sdorder, TradeInformation old) {

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
		Cookie[] cookies = getRequest().getCookies();
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
			String bEmail = bindinfo.getKxbUserEmail();
			if ("".equals(bEmail) || bEmail == null) {
				// 未做绑定处理
				bindinfo.setmMember(member);
				bindinfo.setKxbUserEmail(member.getEmail());
				bindinfo.setKxbUserPhone(member.getMobileNO());
				bindInfoForLoginService.update(bindinfo);
				member.setmBindInfoForLogin(bindinfo);
				this.memberService.update(member);
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

	/**
	 * 判断是否是新用户，是新用户则保存用户名
	 * 
	 * @param ordId
	 *            订单号
	 */
	public void userRegistedCheck(String ordId) {

		// 操作手机，操作邮箱
		String ss = "select b.applicantMail, b.applicantMobile from SDInformation a, SDInformationAppnt b "
				+ "where a.informationSn=b.informationSn and a.orderSn=?";
		QueryBuilder qb = new QueryBuilder(ss, ordId);
		DataTable dt = qb.executeDataTable();
		String email = dt.getString(0, "applicantmail");
		String mobileNo = dt.getString(0, "applicantMobile");
		String Mobilesql = "select UNIX_TIMESTAMP(createdate) from member where mobileNO=? ";
		String mailsql = "select UNIX_TIMESTAMP(createdate) from member where email=? ";
		// 取得当前时间的毫秒数
		Date now = new Date();
		long seconds = now.getTime() / 60;

		// 用邮箱校验
		qb = new QueryBuilder(mailsql, email);
		Long createTime = (Long) qb.executeOneValue();
		if (createTime != null) {
			// member的创建时间距离当前时间不超过3分钟时 判断此用户为新用户
			boolean flag = createTime + 180 >= seconds;
			if (flag) {
				setNewUserName(email);
			}
			return;
		}

		// 用手机号校验
		qb = new QueryBuilder(Mobilesql, mobileNo);
		createTime = (Long) qb.executeOneValue();
		if (createTime != null) {
			// member的创建时间距离当前时间不超过3分钟时 判断此用户为新用户
			boolean flag = createTime + 180 >= seconds;
			if (flag) {
				setNewUserName(mobileNo);
			}
			return;
		}

		// 会员表中无用户信息 即为新用户 新用户名为邮箱
		setNewUserName(email);
	}

	private HashMap<String, String> isProductDownShelf(String message, SDInformation sdinformation) {

		HashMap<String, String> map = new HashMap<String, String>();
		String productId = "";
		String productName = "";
		if (sdinformation != null) {
			productId = sdinformation.getProductId();
			productName = sdinformation.getProductName();
		}
		if (sdinformation != null) {
			productId = sdinformation.getProductId();
			productName = sdinformation.getProductName();
		}
		if (null == productId) {
			addActionError("查询产品时传入参数错误！");
			logger.error(message + "{}-核保失败：orderSn-{} - 原因：查询产品时传入参数错误！",message, sdinformation.getOrderSn());
			map.put("status", ERROR);
			return map;
		}
		String sql = "select LimitCount,Occup,SectionAge,IsPublish from sdproduct where productid=?";
		String[] sqltemp = { productId };
		GetDBdata db = new GetDBdata();
		List<HashMap<String, String>> sdproduct;
		try {
			sdproduct = db.query(sql, sqltemp);
			if (sdproduct == null || sdproduct.size() != 1) {
				ajaxVerifyErrorMesage = "该订单中险种" + productName + "不存在，无法"
						+ message + "！";
				addActionError("该订单中险种" + productName + "不存在，无法" + message
						+ "！");
				logger.error(message + "-核保失败：orderSn-" + sdinformation.getOrderSn() + " - 原因：" + ajaxVerifyErrorMesage);
				map.put("status", ERROR);
				return map;
			}
			HashMap<String, String> product = sdproduct.get(0);
			String IsPublish = product.get("IsPublish");
			if (StringUtil.isEmpty(IsPublish) || "N".equals(IsPublish)) {
				ajaxVerifyErrorMesage = "该订单中险种" + productName + "已经下架，无法"
						+ message + "！";
				addActionError("该订单中险种" + productName + "已经下架，无法" + message
						+ "！");
				logger.error(message + "-核保失败：orderSn-" + sdinformation.getOrderSn() + " - 原因：" + ajaxVerifyErrorMesage);
				map.put("status", ERROR);
				return map;
			}
			String qixianSql = "select startDate, endDate from sdinformation where id =? ";
			String[] qixianSqltemp = { sdinformation.getId() };
			List<Map<String, Object>> qixain = db.queryObj(qixianSql,
					qixianSqltemp);
			if (qixain == null || qixain.size() != 1) {
				ajaxVerifyErrorMesage = "此订单不存在！";
				addActionError("此订单不存在！");
				logger.error(message + "-核保失败：orderSn-" + sdinformation.getOrderSn() + " - 原因：" + ajaxVerifyErrorMesage);
				map.put("status", ERROR);
				return map;
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
				ajaxVerifyErrorMesage = "保单已过期！";
				addActionError("保单已过期！");
				logger.error(message + "-核保失败：orderSn-" + sdinformation.getOrderSn() + " - 原因：" + ajaxVerifyErrorMesage);
				map.put("status", ERROR);
				return map;
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
							+ " and (('"
							+ qxfail
							+ "'>=a.startDate and a.startDate>='"
							+ qxeffective
							+ "') or ('"
							+ qxeffective
							+ "'<=a.endDate and a.endDate<='"
							+ qxfail
							+ "')) "
							+ "and b.recognizeeIdentityType=? and b.recognizeeIdentityId=? and c.appStatus='1' and a.productId=?";
					String[] backupTemp = { insuredIdType, insuredIdNo,
							productId };
					logger.info("保险公司限购份数（sql）：{}", backup_);
					String backup = db.getOneValue(backup_, backupTemp);
					logger.info("保险公司限购份数的值：{} -- {}", backup, LimitCount);
					if (StringUtil.isNotEmpty(backup)) {
						if (Integer.parseInt(backup) >= Integer
								.parseInt(LimitCount)) {
							ajaxVerifyErrorMesage = "被保人" + insuredName + "已经购买过该产品了 ！";
							addActionError("被保人" + insuredName + "已经购买过该产品了 ,请进入会员中心修改订单信息！");
							logger.error(message + "-核保失败：orderSn-" + sdinformation.getOrderSn() + " - 原因："
									+ ajaxVerifyErrorMesage);
							map.put("status", "alreadyPay");
							return map;
						}
					}
				}
			}
			map.put("status", SUCCESS);
			return map;
		} catch (Exception e) {
			addActionError("查询产品失败！");
			logger.error(message + "-核保失败：orderSn-" + sdinformation.getOrderSn() + " - 原因：查询产品失败！" + e.getMessage(), e);
			map.put("status", ERROR);
			return map;
		}
	}

	/**
	 * 金融界回调判断结果显示
	 * 
	 * @param paySn
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
	 * 
	 * @Title: couponVerify
	 * @Description: TODO(优惠劵验证)
	 * @return String 返回类型
	 * @author
	 */
	@SuppressWarnings("unchecked")
	public String couponVerify() {

		// 是否是激活校验
		String jh_flag = getParameter("flag");
		// String memberID = (String)
		// getSession(Member.LOGIN_MEMBER_ID_SESSION_NAME);
		String memberID = getLoginMember().getId();
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		if (StringUtil.isEmpty(memberID)) {
			jsonMap.put(STATUS, ERROR);
			jsonMap.put(MESSAGE, "会员没有登录，请重新登录！");
			return ajax(JSONObject.fromObject(jsonMap).toString(), "text/html");
		}
		List<SDOrder> paramterList = new ArrayList<SDOrder>();
		try {
			// 积分换算一元单位
			String PointScalerUnit = "200";
			// 积分换算规则修改时间点
			Date time = DateUtil.parseDateTime(Config.getConfigValue("PointScalerTime"));
			Date now = new Date();
			// 当前支付时间在积分换算规则修改时间点之后 积分规则修改
			if (now.compareTo(time) >= 0) {
				// 取得积分换算单位
				PointScalerUnit = Config.getConfigValue("PointScalerUnit");
			}
			jsonMap.put("PointScalerUnit", PointScalerUnit);
			BigDecimal amount = new BigDecimal("0");
			couponInfo = couponInfoService.getCouponInfoByCouponSn(CouponSn);
			if ("true".equals(jh_flag)) {
				if (couponInfo == null) {// 激活码不存在
					jsonMap.put(STATUS, ERROR);
					jsonMap.put(MESSAGE, "激活失败，您的激活码不存在！");
					return ajax(JSONObject.fromObject(jsonMap).toString(),
							"text/html");
				} else if (("".equals(couponInfo.getMemberId()))
						|| (couponInfo.getMemberId() == null)) {
					logger.info("激活的优惠券码：{}无关联会员", CouponSn);// 此处增加判断
																	// 激活码是否已关联其他用户
																	// my.memberid==couponInfo.getMemberId()
				} else {
					jsonMap.put(STATUS, ERROR);
					jsonMap.put(MESSAGE, "激活失败，该优惠劵已被激活绑定，不能重复激活！");
					return ajax(JSONObject.fromObject(jsonMap).toString(),
							"text/html");
				}
			}
			String[] order = OrdId.split(",");
			// 非购物车情况
			if (order.length == 1) {
				sdorder = sdorderService.getOrderByOrderSn(order[0].trim());
				paramterList.add(sdorder);
				Map<String, Object> map = getActivityAmount(paramterList);
				String realamount = String.valueOf(map.get("realamount"));
				BigDecimal realprice = new BigDecimal(realamount);
				// 优惠劵有效性校验
				if (couponInfo == null && "true".equals(jh_flag)) {
					jsonMap.put(STATUS, ERROR);
					jsonMap.put(MESSAGE, "激活失败，您的激活码不存在！");
					jsonMap.put("totalAmount", String.valueOf(new BigDecimal(realamount)));
					return ajax(JSONObject.fromObject(jsonMap).toString(),
							"text/html");
				}
				if (couponInfo != null) {
					// 进行使用限制金额校验
					BigDecimal payAmount = couponInfo.getPayAmount();
					List<Map<String, String>> list = (List<Map<String, String>>) map.get("list_product");
					boolean flag = checkCoupon(jsonMap, order[0].trim());
					if (flag == true) {
						amount = amount.add(new BigDecimal(getOrderActivityAmount(list, sdorder.getOrderSn())));
					}
					int amountFlag = amount.compareTo(payAmount);
					if (amountFlag == -1 || "0".equals(amount)) {
						jsonMap.put(STATUS, ERROR);
						if ("success".equals(jsonMap.get(MESSAGE))) {
							jsonMap.put(MESSAGE, "激活失败，您的优惠券金额不满足本次支付金额，请重试！");
						}
						realprice = new BigDecimal(realamount).setScale(2, BigDecimal.ROUND_HALF_UP);
						jsonMap.put("totalAmount", String.valueOf(realprice));
					} else {
						jsonMap.put(STATUS, SUCCESS);
						// 非折扣券的情况
						if (StringUtil.isEmpty(jsonMap.get("couponType"))
								|| "01".equals(String.valueOf(jsonMap.get("couponType")))) {
							if (amount.compareTo(new BigDecimal(String.valueOf(jsonMap.get("parValue")))) == -1) {
								jsonMap.put("parValue", amount);
							}
						} else {// 折扣券的情况
							// 计算出优惠的折扣 1-折扣（例1-7.5/10 = 0.25）
							BigDecimal discount = new BigDecimal(1)
									.subtract(new BigDecimal((String) jsonMap.get("discount")).divide(new BigDecimal(10)));
							// 优惠金额
							BigDecimal parValue = amount.multiply(discount).setScale(2, BigDecimal.ROUND_HALF_UP);
							// 根据折扣券设置的最高抵扣金额设置优惠金额
							parValue = setFavPriceByMaxDeduction(parValue,(String)jsonMap.get("maxDeduction"));
							jsonMap.put("parValue", parValue);
						}
						realprice = new BigDecimal(realamount).subtract(new BigDecimal(String.valueOf(jsonMap
								.get("parValue"))));
						jsonMap.put("totalAmount", String.valueOf(realprice));
					}
					jsonMap.put("ordernum", order.length);

					if ("true".equals(jh_flag) && !(ERROR.equals(jsonMap.get(STATUS)))) {
						couponInfo.setMemberId(memberID);
						couponInfoService.update(couponInfo);
					}
				} else {
					jsonMap.put("parValue", "0");
					realprice = new BigDecimal(realamount).subtract(new BigDecimal(String.valueOf(jsonMap.get("parValue"))));
					jsonMap.put("totalAmount", String.valueOf(realprice));
					jsonMap.put(STATUS, SUCCESS);
					jsonMap.put("ordernum", order.length);
				}
				String couponsn = "";
				if (couponInfo != null) {
					couponsn = couponInfo.getCouponSn();
				}
				Map map_point = ActivityCalculate.payPointInfo(paramterList, sdorder.getChannelsn(), 0, couponsn, memberID);
				Member loginMember = getLoginMember();
				String rIntegral = String.valueOf(map_point.get("givepoint"));
				if (loginMember != null) {
					String baseIntegral = realprice.multiply(new BigDecimal(PointScalerUnit))
							.setScale(0, BigDecimal.ROUND_HALF_UP).toString();
					List<Map<String,Object>> productToPointRates = JSONArray.fromObject(map_point.get("productToPointRates"));
					// 会员等级
					Map<String, String> map_result_grade = ActivityCalculate.getMemberGradeBirthdayPoints(
							IntegralConstant.POINT_SOURCE_BUY, loginMember.getId(), baseIntegral, productToPointRates);
					String flag_grade = map_result_grade.get("flag");
					if ("true".equals(flag_grade)) {
						// 无取舍操作的积分值
						// String pointsAll=map_result_grade.get("pointsAll");
						rIntegral = String.valueOf(Integer.parseInt(rIntegral)
								+ Integer.parseInt(map_result_grade.get("addpoints")));
					}

					// 会员生日月
					Map<String, String> map_result = ActivityCalculate.getMemberGradeBirthdayPoints(
							IntegralConstant.POINT_SOURCE_BIRTH_MONTH, loginMember.getId(), baseIntegral, productToPointRates);
					String flag = map_result.get("flag");
					if ("true".equals(flag)) {
						rIntegral = String.valueOf(Integer.parseInt(rIntegral)
								+ Integer.parseInt(map_result.get("addpoints")));
					}
				}
				// 购买成功后将获得积分
				jsonMap.put("givepoint", rIntegral);
				jsonMap.put("givepointvalue",
						new BigDecimal(rIntegral).divide(new BigDecimal(PointScalerUnit), 1, BigDecimal.ROUND_HALF_UP));
				jsonMap.put("maxpoints", String.valueOf(map_point.get("canusepoint")));
				return ajax(JSONObject.fromObject(jsonMap).toString(), "text/html");
			} else if (order.length > 1) {// 购物车情况
				for (int i = 0; i < order.length; i++) {
					sdorder = sdorderService.getOrderByOrderSn(order[i].trim());
					paramterList.add(sdorder);
				}
				Map<String, Object> map = getActivityAmount(paramterList);
				String realamount = String.valueOf(map.get("realamount"));
				BigDecimal realprice = new BigDecimal(realamount);
				// 优惠劵有效性校验
				if (couponInfo == null && "true".equals(jh_flag)) {
					jsonMap.put(STATUS, ERROR);
					jsonMap.put(MESSAGE, "激活失败，您的激活码不存在！");
					jsonMap.put("totalAmount", String.valueOf(new BigDecimal(realamount)));
					return ajax(JSONObject.fromObject(jsonMap).toString(),
							"text/html");
				}
				if (couponInfo != null) {
					List<Map<String, String>> list = (List<Map<String, String>>) map.get("list_product");
					for (int i = 0; i < order.length; i++) {
						sdorder = sdorderService.getOrderByOrderSn(order[i].trim());
						// paramterList.add(sdorder);
						boolean flag = checkCoupon(jsonMap, order[i].trim());
						if (flag == true) {
							amount = amount.add(new BigDecimal(getOrderActivityAmount(list, sdorder.getOrderSn())));
						}
					}
					// 进行使用限制金额校验
					BigDecimal payAmount = couponInfo.getPayAmount();
					int amountFlag = amount.compareTo(payAmount);
					if (amountFlag == -1) {
						jsonMap.put(STATUS, ERROR);
						if ("success".equals(jsonMap.get(MESSAGE))) {
							jsonMap.put(MESSAGE, "激活失败，您的优惠券金额不满足本次支付金额，请重试！");
						}
						realprice = new BigDecimal(realamount).setScale(2, BigDecimal.ROUND_HALF_UP);
						jsonMap.put("totalAmount", String.valueOf(realprice));
					} else {
						// 非折扣券的情况
						if (StringUtil.isEmpty(jsonMap.get("couponType"))
								|| "01".equals(String.valueOf(jsonMap.get("couponType")))) {
							if (amount.compareTo(new BigDecimal(String.valueOf(jsonMap.get("parValue")))) == -1) {
								jsonMap.put("parValue", amount);
							}
						} else {// 折扣券的情况
							// 计算出优惠的折扣 1-折扣（例1-7.5/10 = 0.25）
							BigDecimal discount = new BigDecimal(1)
									.subtract(new BigDecimal((String) jsonMap.get("discount")).divide(new BigDecimal(10)));
							// 优惠金额
							BigDecimal parValue = amount.multiply(discount).setScale(2, BigDecimal.ROUND_HALF_UP);
							// 根据折扣券设置的最高抵扣金额设置优惠金额
							parValue = setFavPriceByMaxDeduction(parValue,(String)jsonMap.get("maxDeduction"));
							jsonMap.put("parValue", parValue);
						}
						jsonMap.put(STATUS, SUCCESS);
						realprice = new BigDecimal(realamount).subtract(
								new BigDecimal(String.valueOf(jsonMap.get("parValue")))).setScale(2,
								BigDecimal.ROUND_HALF_UP);
						jsonMap.put("totalAmount", String.valueOf(realprice));
					}
					jsonMap.put("ordernum", order.length);
					if ("true".equals(jh_flag)
							&& !(ERROR.equals(jsonMap.get(STATUS)))) {
						couponInfo.setMemberId(memberID);
						couponInfoService.update(couponInfo);
					}
				} else {
					jsonMap.put("parValue", "0");
					realprice = new BigDecimal(realamount).subtract(new BigDecimal(String.valueOf(jsonMap.get("parValue"))));
					jsonMap.put("totalAmount", String.valueOf(realprice));
					jsonMap.put(STATUS, SUCCESS);
					jsonMap.put("ordernum", order.length);
				}
				String couponsn = "";
				if (couponInfo != null) {
					couponsn = couponInfo.getCouponSn();
				}
				// 购买成功后将获得积分
				Map map_point = ActivityCalculate.payPointInfo(paramterList, sdorder.getChannelsn(), 0, couponsn, memberID);
				Member loginMember = getLoginMember();
				String rIntegral = String.valueOf(map_point.get("givepoint"));
				if (loginMember != null) {
					String baseIntegral = realprice.multiply(new BigDecimal(PointScalerUnit))
							.setScale(0, BigDecimal.ROUND_HALF_UP).toString();
					List<Map<String,Object>> productToPointRates = JSONArray.fromObject(map_point.get("productToPointRates"));
					// 会员等级
					Map<String, String> map_result_grade = ActivityCalculate.getMemberGradeBirthdayPoints(
							IntegralConstant.POINT_SOURCE_BUY, loginMember.getId(), baseIntegral,productToPointRates);
					String flag_grade = map_result_grade.get("flag");
					if ("true".equals(flag_grade)) {
						// 无取舍操作的积分值
						// String pointsAll=map_result_grade.get("pointsAll");
						rIntegral = String.valueOf(Integer.parseInt(rIntegral)
								+ Integer.parseInt(map_result_grade.get("addpoints")));
					}

					// 会员生日月
					Map<String, String> map_result = ActivityCalculate.getMemberGradeBirthdayPoints(
							IntegralConstant.POINT_SOURCE_BIRTH_MONTH, loginMember.getId(), baseIntegral,productToPointRates);
					String flag = map_result.get("flag");
					if ("true".equals(flag)) {
						rIntegral = String.valueOf(Integer.parseInt(rIntegral)
								+ Integer.parseInt(map_result.get("addpoints")));
					}
				}
				jsonMap.put("maxpoints", String.valueOf(map_point.get("canusepoint")));
				// 购买成功后将获得积分
				jsonMap.put("givepoint", rIntegral);
				jsonMap.put("givepointvalue",
						new BigDecimal(rIntegral).divide(new BigDecimal(PointScalerUnit), 1, BigDecimal.ROUND_HALF_UP));
				return ajax(JSONObject.fromObject(jsonMap).toString(),
						"text/html");
			} else {
				jsonMap.put(STATUS, ERROR);
				jsonMap.put(MESSAGE, "优惠劵验证时，订单号为空，请联系客服！");
				return ajax(JSONObject.fromObject(jsonMap).toString(),
						"text/html");
			}
		} catch (Exception e) {
			logger.error("优惠劵验证发生异常" + e.getMessage(), e);
			couponInfo.setOrderSn("");
			couponInfoService.update(couponInfo);
			jsonMap.put(STATUS, ERROR);
			jsonMap.put(MESSAGE, "优惠劵验证发生异常，请联系客服");
			return ajax(JSONObject.fromObject(jsonMap).toString(), "text/html");
		}
	}

	/**
	 * 
	 * @Title: getActivityAmount
	 * @Description: TODO(计算扣除活动和折扣后的订单总价格)
	 * @return Map<String,String> 返回类型
	 * @author XXX
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map<String, Object> getActivityAmount(List<SDOrder> paramterList) {

		BigDecimal totlePrice = new BigDecimal("0");
		BigDecimal realamount = new BigDecimal("0");
		BigDecimal totalamount = new BigDecimal("0");
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> list_product = new ArrayList<Map<String, String>>();
		// 筛选优惠活动
		Map<String, Map<String, Object>> activity_product_info1 = ActivityCalculate.ProductShoppingCartInfo(paramterList,
				paramterList.get(0).getChannelsn(), true);
		// 遍历优惠信息Map
		Set keySet = activity_product_info1.keySet();
		for (Iterator it = keySet.iterator(); it.hasNext();) {
			// 活动号（包含“_no_activity”）
			String activitysn = String.valueOf(it.next());
			// 获取该活动关联的所有信息（包括活动信息，产品信息，优惠信息）
			Map<String, Object> map_info = activity_product_info1.get(activitysn);
			Map activityamont_map = (Map) map_info.get("ActivityAmont");
			String RealAmount = String.valueOf(activityamont_map.get("RealAmount"));
			String TotalAmount = String.valueOf(activityamont_map.get("TotalAmount"));
			Map activityinfot_map = (Map) map_info.get("ActivityInfo");
			if ("3".equals(activityinfot_map.get("type"))) {
				totlePrice = totlePrice.add(new BigDecimal(String.valueOf(activityamont_map.get("DiscountAmount"))));
			}
			realamount = realamount.add(new BigDecimal(String.valueOf(RealAmount)));
			totalamount = totalamount.add(new BigDecimal(String.valueOf(TotalAmount)));
			List product_list = (List) map_info.get("ProductInfo");
			for (int i = 0; i < product_list.size(); i++) {
				Map map_product = (Map) product_list.get(i);
				list_product.add(map_product);
			}
			// map.put(activitysn,RealAmount+"&"+DiscountAmount);
		}
		map.put("discountamount", String.valueOf(totlePrice.setScale(2, BigDecimal.ROUND_DOWN)));
		map.put("realamount", String.valueOf(realamount.setScale(2, BigDecimal.ROUND_DOWN)));
		map.put("totalamount", String.valueOf(totalamount.setScale(2, BigDecimal.ROUND_DOWN)));
		map.put("list_product", list_product);
		return map;
	}

	/**
	 * 
	 * @Title: getOrderActivityAmount
	 * @Description: TODO(获得该订单的活动扣除之后的金额)
	 * @return String 返回类型
	 * @author XXX
	 */
	private String getOrderActivityAmount(List list_product, String ordersn) {

		for (int i = 0; i < list_product.size(); i++) {
			Map map = (Map) list_product.get(i);
			if (ordersn.equals(map.get("OrderSn"))) {
				return String.valueOf(map.get("ActivityeAmount"));
			}
		}
		return "0";
	}

	/**
	 * 
	 * @Title: checkCoupon
	 * @Description: TODO(判断优惠券的业务逻辑)
	 * @return String 返回类型
	 * @author
	 */
	private boolean checkCoupon(Map<String, Object> jsonMap, String orderid) {

		SDInformation sdInformation = sdinformationService.get("orderSn",
				orderid);
		String productId = sdInformation.getProductId();
		String verifyResult = couponInfoService.couponVerify(couponInfo,
				productId, sdorder);
		if ("success".equals(verifyResult)) {
			CouponInfo coupon = couponInfoService.getCouponInfoByOrderSn(paySn);
			if (coupon != null) {
				coupon.setOrderSn("");
				couponInfoService.update(coupon);
			}
			couponInfo.setOrderSn(paySn);
			couponInfoService.update(couponInfo);
			jsonMap.put(STATUS, SUCCESS);
			jsonMap.put(MESSAGE, verifyResult);
			jsonMap.put("couponType", couponInfo.getProp3());
			jsonMap.put("parValue", couponInfo.getParValue());
			jsonMap.put("discount", couponInfo.getProp4());
			jsonMap.put("totalAmount", sdorder.getTotalAmount());
			jsonMap.put("maxDeduction", couponInfo.getMaxDeduction());
			return true;

		} else {
			CouponInfo coupon = couponInfoService.getCouponInfoByOrderSn(paySn);
			if (coupon != null) {
				coupon.setOrderSn("");
				couponInfoService.update(coupon);
			}
			jsonMap.put(STATUS, ERROR);
			jsonMap.put(MESSAGE, verifyResult);
			jsonMap.put("totalAmount", sdorder.getTotalAmount());
			return false;
		}
	}

	/**
	 * 
	 * @Title: checkCoupon
	 * @Description: TODO(判断优惠券是否符合购物车条件)
	 * @return String 返回类型
	 * @author
	 */
	private boolean checkCouponShow(String orderid) {

		SDInformation sdInformation = sdinformationService.get("orderSn",
				orderid);
		String productId = sdInformation.getProductId();
		SDOrder sdor = sdorder;
		if (sdorder == null) {
			sdor = sdorderService.getOrderByOrderSn(orderid);
		}

		String verifyResult = couponInfoService.couponVerify(couponInfo,
				productId, sdor);
		if ("success".equals(verifyResult)) {
			return true;

		} else {
			return false;
		}
	}

	/**
	 * 
	 * @Title: showCoupon
	 * @Description: TODO(支付页面获取优惠券信息)
	 * @return String 返回类型
	 * @author
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String showCoupon() {

		Map<String, String> jsonMap = new HashMap<String, String>();
		sdorder = sdorderService.getOrderByOrderSn(OrdId);
		SDInformation sdInformation = sdinformationService
				.get("orderSn", OrdId);
		String productId = sdInformation.getProductId();
		// String memberID = (String)
		// getSession(Member.LOGIN_MEMBER_ID_SESSION_NAME);
		String memberID = getLoginMember().getId();
		// 是否与活动同时使用
		String sql_prop1 = " ";
		// 优惠活动的筛选
		List<SDOrder> paramterList = new ArrayList<SDOrder>();
		paramterList.add(sdorder);
		BigDecimal RealAmount = sdorder.getTotalAmount();
		Map<String, Map<String, Object>> activity_product_info1 = ActivityCalculate.ProductShoppingCartInfo(paramterList,
				sdorder.getChannelsn(), true);
		try {
			if (activity_product_info1 != null && activity_product_info1.size() > 0) {
				// 遍历优惠信息Map
				Set keySet = activity_product_info1.keySet();
				for (Iterator it = keySet.iterator(); it.hasNext();) {
					// 活动号（包含“_no_activity”）
					String activitysn = String.valueOf(it.next());
					// 满减活动
					if (!"_no_activity".equals(activitysn)) {
						sql_prop1 = " and ac.prop1='Y' ";
					}
					// 获取该活动关联的所有信息（包括活动信息，产品信息，优惠信息）
					Map<String, Object> map_info = activity_product_info1.get(activitysn);
					// 获取产品信息list
					Map<String, String> map_activityamont = (Map<String, String>) map_info.get("ActivityAmont");
					if (map_activityamont.size() > 0) {
						RealAmount = new BigDecimal(map_activityamont.get("RealAmount"));
					} else {
						logger.error("非购物车购买流程，展示优惠券信息时，查询订单参加活动情况时，数据有异常，订单号为:{}", sdorder.getOrderSn());
					}
				}
			}
		} catch (Exception e) {
			logger.error("非购物车购买流程，展示优惠券信息时，查询订单参加活动情况时，方法发生异常，订单号为:" + sdorder.getOrderSn() + e.getMessage(), e);
		}
		// 查询优惠券信息
		QueryBuilder qb = new QueryBuilder(
				"SELECT couponsn,parvalue,direction,prop3,prop4,endTime,payamount FROM couponinfo ac WHERE  "
						+
						" ac.status='2' AND DATE_FORMAT(ac.starttime,'%Y-%m-%d %H:%i:%s')<=DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s') "
						+ "AND DATE_FORMAT(ac.endtime,'%Y-%m-%d %H:%i:%s')>=DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s') " +
						"and FIND_IN_SET('" + sdorder.getChannelsn() + "',channelsn)!=0 and  memberid=? " + sql_prop1);
		qb.add(memberID);
		DataTable dt = qb.executeDataTable();
		DataTable dt_html = new DataTable();
		for (int j = 0; j < dt.getRowCount(); j++) {
			// 获取优惠劵对象
			couponInfo = couponInfoService.getCouponInfoByCouponSn(dt.getString(j, 0));
			String verifyResult = couponInfoService.couponVerify(couponInfo,
					productId, sdorder);
			if ("success".equals(verifyResult)) {
				// 进行使用限制金额校验
				BigDecimal payAmount = new BigDecimal(dt.getString(j, 6));
				int amountFlag = RealAmount.compareTo(payAmount);
				if (amountFlag != -1) {
					dt_html.insertRow(dt.get(j));
				}
			}
		}
		if (dt_html.getRowCount() > 0) {
			String html = getActivityHtml(dt_html);
			jsonMap.put("success", html);

		} else {
			jsonMap.put("success", "");
		}
		return ajaxJson(jsonMap);
	}

	/**
	 * 
	 * @Title: showPoint
	 * @Description: TODO(展示用户积分)
	 * @return String 返回类型
	 * @author
	 */
	public String showPoint() {

		Map<String, String> jsonMap = new HashMap<String, String>();
		Member memberLogin = getLoginMember();
		String memberID = "";
		if (memberLogin != null) {
			memberID = memberLogin.getId();
		}
		if (StringUtil.isNotEmpty(memberID)) {
			String[] order = OrdId.split(",");
			List<SDOrder> paramterList = new ArrayList<SDOrder>();
			for (int i = 0; i < order.length; i++) {
				sdorder = sdorderService.getOrderByOrderSn(order[i].trim());
				paramterList.add(sdorder);
			}
			Map map_point = ActivityCalculate
					.payPointInfo(paramterList, paramterList.get(0).getChannelsn(), 0, "", memberID);
			// 积分换算一元单位
			String PointScalerUnit = "200";
			// 积分换算规则修改时间点
			Date time = DateUtil.parseDateTime(Config.getConfigValue("PointScalerTime"));
			Date now = new Date();
			// 当前支付时间在积分换算规则修改时间点之后 积分规则修改
			if (now.compareTo(time) >= 0) {
				// 取得积分换算单位
				PointScalerUnit = Config.getConfigValue("PointScalerUnit");
			}
			jsonMap.put("PointScalerUnit", PointScalerUnit);
			// 可用积分
			jsonMap.put("success", String.valueOf(memberLogin.getCurrentValidatePoint()));
			if (new BigDecimal(String.valueOf(memberLogin.getCurrentValidatePoint())).compareTo(new BigDecimal(String
					.valueOf(map_point.get("canusepoint")))) > 0) {
				jsonMap.put("paysuccess", String.valueOf(map_point.get("canusepoint")));
			} else {
				jsonMap.put("paysuccess", String.valueOf(memberLogin.getCurrentValidatePoint()));
			}
			// 后台配置基本积分（判断前台积分是否默认展开标识）
			jsonMap.put("BasePoint", Config.getConfigValue("BasePoint"));
		}
		return ajaxJson(jsonMap);
	}

	/**
	 * 
	 * @Title: changeIntegral
	 * @Description: TODO(积分格式化)
	 * @return String 返回类型
	 * @author XXX
	 */
	private String changeIntegral(int Integral, int Multiple) {

		try {
			int div = (Multiple / 10);
			int result = new BigDecimal(Integral / div).setScale(0, BigDecimal.ROUND_HALF_DOWN).intValue();
			return String.valueOf(result * Multiple / 10);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "0";
		}
	}

	/**
	 * 
	 * @Title: usePoint
	 * @Description: TODO(使用积分)
	 * @return String 返回类型
	 * @author
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String usePoint() {

		String memberID = (String) getSession(Member.LOGIN_MEMBER_ID_SESSION_NAME);
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		String point_str = getParameter("point");
		// 校验积分数字的合法性
		if (!StringUtil.isInteger(point_str)) {
			jsonMap.put("status", "false");
			jsonMap.put("message", "请输入正确的数字");
			logger.warn("使用积分为非正整数，订单号为:{};会员id为：{}", OrdId.toString(), memberID);
			return ajax(JSONObject.fromObject(jsonMap).toString(), "text/html");
		}
		BigDecimal point = new BigDecimal(point_str);
		String[] order = OrdId.split(",");

		if (point.compareTo(new BigDecimal("0")) != 1) {
			jsonMap.put("status", "false");
			jsonMap.put("message", "积分抵值的数额不能小于1");
			logger.info("使用积分为0，订单号为:{};会员id为：{}", OrdId.toString(), memberID);
			return ajax(JSONObject.fromObject(jsonMap).toString(), "text/html");
		}
		// 积分换算一元单位
		String PointScalerUnit = "200";
		// 积分换算规则修改时间点
		Date time = DateUtil.parseDateTime(Config
				.getConfigValue("PointScalerTime"));
		Date now = new Date();
		// 当前支付时间在积分换算规则修改时间点之后 积分规则修改
		if (now.compareTo(time) >= 0) {
			// 取得积分换算单位
			PointScalerUnit = Config.getConfigValue("PointScalerUnit");
		}
		jsonMap.put("PointScalerUnit", PointScalerUnit);
		if (StringUtil.isNotEmpty(memberID)) {
			QueryBuilder qb = new QueryBuilder(
					"select currentValidatePoint from member where id=?");
			qb.add(memberID);
			DataTable dt = qb.executeDataTable();
			String currentValidatePoint = "0";
			if (dt.getRowCount() > 0) {
				currentValidatePoint = dt.getString(0, 0);
				if (StringUtil.isEmpty(currentValidatePoint)) {
					currentValidatePoint = "0";
				}
			} else if ("tencent".equals(getSession().get(Member.LOGIN_MEMBER_ID_SESSION_NAME))) {
				Cookie cookie = CookieUtil.getCookieByName(getRequest(), "loginBindId");
				if (cookie != null && (!("".equals(cookie)))) {
					// 取得绑定的loginBindId值
					String loginBindId = cookie.getValue();
					QueryBuilder qb_tencent = new QueryBuilder(
							"select currentValidatePoint from member where mBindInfoForLogin_id=?");
					qb_tencent.add(loginBindId);
					DataTable dt_tencent = qb_tencent.executeDataTable();
					if (dt_tencent.getRowCount() == 1) {
						currentValidatePoint = qb_tencent.executeString();
						if (StringUtil.isEmpty(currentValidatePoint)) {
							currentValidatePoint = "0";
						}
					} else {
						logger.warn("支付页面查询会员总积分，联合登陆会员查询不到数据，联合登陆会员mBindInfoForLogin_id为：{}", loginBindId);
					}
				}
			} else {
				jsonMap.put("status", "false");
				jsonMap.put("message", "没有查询到会员积分，请重新登录");
				logger.warn("支付页面中使用积分的会员：{}查询不到积分！", memberID);
				return ajax(JSONObject.fromObject(jsonMap).toString(), "text/html");
			}

			if (new BigDecimal(currentValidatePoint).compareTo(point) != -1) {
				jsonMap.put("status", "success");
				jsonMap.put("point", new BigDecimal(currentValidatePoint)
						.subtract(point));
				jsonMap.put("usepoint", point);
				jsonMap.put("ordernum", order.length);
				BigDecimal total = new BigDecimal("0");
				BigDecimal realtotal = new BigDecimal("0");
				List<SDOrder> paramterList = new ArrayList<SDOrder>();

				for (int i = 0; i < order.length; i++) {
					sdorder = sdorderService.getOrderByOrderSn(order[i].trim());
					total = total.add(sdorder.getTotalAmount());
					paramterList.add(sdorder);
				}
				jsonMap.put("totalAmount", total);
				BigDecimal parValue = new BigDecimal("0");
				// BigDecimal parValue_coupon = new BigDecimal("0");
				BigDecimal realamount = new BigDecimal("0");
				// 筛选优惠活动
				Map<String, Map<String, Object>> activity_product_info1 = ActivityCalculate.ProductShoppingCartInfo(
						paramterList, paramterList.get(0).getChannelsn(), true);
				Set keySet = activity_product_info1.keySet();
				for (Iterator it = keySet.iterator(); it.hasNext();) {
					// 活动号（包含“_no_activity”）
					String activitysn = String.valueOf(it.next());
					// 获取该活动关联的所有信息（包括活动信息，产品信息，优惠信息）
					Map<String, Object> map_info = activity_product_info1.get(activitysn);
					// 获取产品信息list
					Map<String, String> map_activityamont = (Map<String, String>) map_info.get("ActivityAmont");
					Map<String, String> map_activityinfo = (Map<String, String>) map_info.get("ActivityInfo");
					if ("3".equals(map_activityinfo.get("type"))) {
						// 总优惠金额累计
						parValue = parValue.add(new BigDecimal(map_activityamont.get("DiscountAmount")));
					}
					// 总金额
					if ("6".equals(map_activityinfo.get("type"))) {
						String TotalAmount = map_activityamont.get("RealAmount");
						realtotal = realtotal.add(new BigDecimal(TotalAmount));
					} else {
						String TotalAmount = map_activityamont.get("TotalAmount");
						realtotal = realtotal.add(new BigDecimal(TotalAmount));
					}
					realamount = realamount.add(new BigDecimal(map_activityamont.get("RealAmount")));
				}
				// 优惠券的使用
				BigDecimal amount = new BigDecimal("0");
				if (StringUtil.isNotEmpty(CouponSn) && !("0".equals(CouponSn))) {
					// 获取优惠劵对象
					couponInfo = couponInfoService.getCouponInfoByCouponSn(CouponSn);
					for (int i = 0; i < order.length; i++) {
						sdorder = sdorderService.getOrderByOrderSn(order[i].trim());
						boolean flag = checkCouponShow(order[i].trim());
						if (flag == true) {
							// 获得扣除活动后价格
							BigDecimal totals = new BigDecimal(getAmount(activity_product_info1, sdorder).get("amount"));
							amount = amount.add(totals);
						}
					}
					// 非折扣券
					if (StringUtil.isEmpty(couponInfo.getProp3()) || "01".equals(couponInfo.getProp3())) {
						// 进行使用限制金额校验
						BigDecimal paramount = new BigDecimal(String.valueOf(couponInfo.getParValue()));
						int amountFlag = amount.compareTo(paramount);
						if (amountFlag == -1) {
							parValue = parValue.add(amount);
						} else {
							parValue = parValue.add(new BigDecimal(String.valueOf(couponInfo.getParValue())));
						}
					} else {// 折扣券
						// 计算出优惠的折扣 1-折扣（例1-7.5/10 = 0.25）
						BigDecimal discount = new BigDecimal(1).subtract(new BigDecimal(couponInfo.getProp4())
								.divide(new BigDecimal(10)));
						// 优惠金额
						parValue = parValue.add(amount.multiply(discount).setScale(2, BigDecimal.ROUND_HALF_UP));
					}

				}

				BigDecimal pointValue = point.divide(new BigDecimal(PointScalerUnit), 2, BigDecimal.ROUND_DOWN);
				jsonMap.put("parValue", parValue.setScale(2, BigDecimal.ROUND_HALF_UP));
				jsonMap.put("totalAmount", realtotal.setScale(2, BigDecimal.ROUND_HALF_UP));
				BigDecimal realprice = realtotal.subtract(pointValue).subtract(parValue)
						.setScale(2, BigDecimal.ROUND_HALF_UP);
				jsonMap.put("realamount", realprice);
				jsonMap.put("pointValue", pointValue.setScale(2, BigDecimal.ROUND_HALF_UP));
				if ("0".equals(CouponSn)) {
					CouponSn = "";
				}
				Map map_point = ActivityCalculate.payPointInfo(paramterList, paramterList.get(0).getChannelsn(),
						Integer.parseInt(point_str), CouponSn, memberID);
				Member loginMember = getLoginMember();
				String rIntegral = String.valueOf(map_point.get("givepoint"));
				if (loginMember != null) {
					String baseIntegral = realprice.multiply(new BigDecimal(PointScalerUnit))
							.setScale(0, BigDecimal.ROUND_HALF_UP).toString();
					List<Map<String,Object>> productToPointRates = JSONArray.fromObject(map_point.get("productToPointRates"));
					
					// 会员等级
					Map<String, String> map_result_grade = ActivityCalculate.getMemberGradeBirthdayPoints(
							IntegralConstant.POINT_SOURCE_BUY, loginMember.getId(), baseIntegral, productToPointRates);
					String flag_grade = map_result_grade.get("flag");
					if ("true".equals(flag_grade)) {
						String point_result = map_result_grade.get("points");
						// 无取舍操作的积分值
						// String pointsAll=map_result_grade.get("pointsAll");
						rIntegral = String.valueOf(Integer.parseInt(rIntegral)
								+ Integer.parseInt(map_result_grade.get("addpoints")));
					}
					// 会员生日月
					Map<String, String> map_result = ActivityCalculate.getMemberGradeBirthdayPoints(
							IntegralConstant.POINT_SOURCE_BIRTH_MONTH, loginMember.getId(), baseIntegral, productToPointRates);
					String flag = map_result.get("flag");
					if ("true".equals(flag)) {
						rIntegral = String.valueOf(Integer.parseInt(rIntegral)
								+ Integer.parseInt(map_result.get("addpoints")));

					}
				}
				// 购买成功后将获得积分
				jsonMap.put("givepoint", rIntegral);
				jsonMap.put("givepointvalue",
						new BigDecimal(rIntegral).divide(new BigDecimal(PointScalerUnit), 1, BigDecimal.ROUND_HALF_UP));
				jsonMap.put("maxpoints", String.valueOf(map_point.get("canusepoint")));
			} else {
				jsonMap.put("status", "false");
				jsonMap.put("message", "会员没有足够的积分");
				jsonMap.put("point", String.valueOf(Integer.parseInt(currentValidatePoint)));
				jsonMap.put("usepoint", String.valueOf(point));
			}
			return ajax(JSONObject.fromObject(jsonMap).toString(), "text/html");
		} else {
			jsonMap.put("status", "false");
			jsonMap.put("message", "没有查询到会员信息，请重新登录");
			jsonMap.put("point", point);
			jsonMap.put("usepoint", point);
			return ajax(JSONObject.fromObject(jsonMap).toString(), "text/html");
		}
	}

	/**
	 * 
	 * @Title: showShopCartCoupon
	 * @Description: TODO(展示购物车优惠券)
	 * @return String 返回类型
	 * @author
	 */
	public String showShopCartCoupon() {

		String memberID = getLoginMember().getId();
		Map<String, String> jsonMap = new HashMap<String, String>();
		if (!StringUtil.isNotEmpty(memberID)) {
			jsonMap.put("success", "");
			return ajaxJson(jsonMap);
		}
		DataTable dt_html = new DataTable();

		String[] order = OrdId.split(",");
		// 优惠活动的筛选
		List<SDOrder> paramterList = new ArrayList<SDOrder>();
		for (int i = 0; i < order.length; i++) {
			sdorder = sdorderService.getOrderByOrderSn(order[i].trim());
			paramterList.add(sdorder);
		}
		QueryBuilder qb = new QueryBuilder(
				"SELECT couponsn,parvalue,direction,prop3,prop4,endTime,payamount,prop1 FROM couponinfo ac WHERE  "
						+
						" ac.status='2' AND DATE_FORMAT(ac.starttime,'%Y-%m-%d %H:%i:%s')<=DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s') "
						+
						"AND DATE_FORMAT(ac.endtime,'%Y-%m-%d %H:%i:%s')>=DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s') " +
						"and FIND_IN_SET('" + sdorder.getChannelsn() + "',channelsn)!=0 and  memberid=?   ");
		qb.add(memberID);
		DataTable dt = qb.executeDataTable();
		// 获取优惠活动信息
		Map<String, Map<String, Object>> activity_product_info1 = ActivityCalculate.ProductShoppingCartInfo(paramterList,
				paramterList.get(0).getChannelsn(), true);
		// 是否有满减活动
		boolean activity_flag = false;
		for (int j = 0; j < dt.getRowCount(); j++) {
			// 符合条件的金额和
			BigDecimal amount = new BigDecimal("0");
			// 获取优惠劵对象
			couponInfo = couponInfoService.getCouponInfoByCouponSn(dt.getString(j, 0));
			for (int i = 0; i < order.length; i++) {
				sdorder = sdorderService.getOrderByOrderSn(order[i].trim());
				boolean flag = checkCouponShow(order[i].trim());
				if (flag == true) {
					Map<String, String> map_activity = getAmount(activity_product_info1, sdorder);
					// 有活动
					if ("true".equals(map_activity.get("activity")) && (!activity_flag)) {
						activity_flag = true;
					}
					// 获得扣除活动后价格
					BigDecimal total = new BigDecimal(map_activity.get("amount"));
					amount = amount.add(total);
				}
			}
			// 进行使用限制金额校验
			BigDecimal payAmount = new BigDecimal(dt.getString(j, 6));
			int amountFlag = amount.compareTo(payAmount);
			if (amountFlag != -1) {
				if ("Y".equals(dt.getString(j, 7))) {
					dt_html.insertRow(dt.get(j));
				} else {
					// 优惠券有限制，并且订单有活动参加
					if (!activity_flag) {
						dt_html.insertRow(dt.get(j));
					}
				}
			}
		}
		if (dt.getRowCount() > 0) {
			String html = getActivityHtml(dt_html);
			jsonMap.put("success", html);

		} else {
			jsonMap.put("success", "");
		}
		return ajaxJson(jsonMap);
	}

	/**
	 * 
	 * @Title: getAmount
	 * @Description: TODO(比对获取订单扣除活动后的金额)
	 * @return String 返回类型
	 * @author XXX
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map<String, String> getAmount(Map<String, Map<String, Object>> activity_product_info1, SDOrder sdorder) {

		Map<String, String> map = new HashMap<String, String>();
		if (activity_product_info1 != null && activity_product_info1.size() > 0) {
			// 遍历优惠信息Map
			Set keySet = activity_product_info1.keySet();
			for (Iterator it = keySet.iterator(); it.hasNext();) {
				// 活动号（包含“_no_activity”）
				String activitysn = String.valueOf(it.next());
				// 获取该活动关联的所有信息（包括活动信息，产品信息，优惠信息）
				Map<String, Object> map_info = activity_product_info1.get(activitysn);
				// 获取产品信息list
				List<Map<String, String>> list_product = (List<Map<String, String>>) map_info.get("ProductInfo");
				for (int j = 0; j < list_product.size(); j++) {
					// 活动每个产品对应的Map
					Map<String, String> map_product = list_product.get(j);
					if (sdorder.getOrderSn().equals(map_product.get("OrderSn"))) {
						if ("_no_activity".equals(activitysn)) {
							map.put("activity", "false");
							map.put("amount", String.valueOf(sdorder.getTotalAmount()));
							return map;
						} else {
							map.put("activity", "true");
							map.put("amount", map_product.get("ActivityeAmount"));
							return map;
						}
					}
				}
			}
		} else {
			logger.error("购物车展示优惠券时，查询订单的活动信息时发生异常，订单号为：{}", sdorder.getOrderSn());
			map.put("activity", "false");
			map.put("amount", String.valueOf(sdorder.getTotalAmount()));
			return map;
		}
		map.put("activity", "false");
		map.put("amount", String.valueOf(sdorder.getTotalAmount()));
		return map;
	}

	/**
	 * 
	 * @Title: getActivityHtml
	 * @Description: TODO(组装显示优惠券html)
	 * @return String 返回类型
	 * @author
	 */
	private String getActivityHtml(DataTable dt) {

		StringBuffer html = new StringBuffer("");
		if (dt != null && dt.getRowCount() > 0) {
			// 优惠券类型
			String couponType = "";
			for (int i = 0; i < dt.getRowCount(); i++) {
				couponType = dt.getString(i, 3);
				StringBuffer coupon_html = new StringBuffer("");
				coupon_html.append("<label><input type=\"checkbox\" name=\"yhj_dx_input\" class=\"shop_checksf\" value=\""
						+ dt.getString(i, 0)
						+ "\"  onclick=\"chooseYHJ(this)\"/>优惠券");
				// 非折扣券的情况
				if (StringUtil.isEmpty(couponType) || "01".equals(couponType)) {
					coupon_html
							.append(dt.getString(i, 1) + "元");
				} else {// 折扣券的情况
					coupon_html.append(dt.getString(i, 4) + "折");
				}

				coupon_html.append("（有效期至" + dt.getString(i, 5).substring(0, 10) + "）<span>" + dt.getString(i, 2)
						+ "</span></label>");
				html.append(coupon_html);
			}
		}

		return html.toString();
	}

	/**
	 * 易宝支付，字符串处理.
	 * 
	 * @param text
	 * @return
	 */
	String formatString(String text) {

		if (text == null) {
			return "";
		}
		return text;
	}

	public void sendErrorMail(String orderSn, String ErrMsg) {
		try {
			GetDBdata db1 = new GetDBdata();
			String receiver = db1
					.getOneValue("select value from zdconfig where type='InsureErrorMail'");
			if(StringUtil.isNotEmpty(receiver)){
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("errmsg", ErrMsg);
				data.put("receiver", receiver);
				data.put("orderSn", orderSn);
				ActionUtil.sendMessage("wj00031", data);
			}
		} catch (Exception e) {
			logger.error("出现异常:" + "sendErrorMail方法出现异常" + e.getMessage(), e);
		}
	}

    /**
     * 展示支付有礼的优惠券
     *
     * @return
     */
    public String showPayCoupon() {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

        String ordersnSql = "";
        String[] orderIdArray = orderId.replaceAll("<br>","").split(",");
        for (int i = 0; i < orderIdArray.length; i++) {
            ordersnSql += " t0.ordersn='" + orderIdArray[i] + "'";
            if (i != orderIdArray.length - 1) {
				ordersnSql += " or";
            }
        }

        // 根据订单号获得产品编码，保险公司编码，会员ID，paysn，总支付金额(购物车内多个订单)
        String sql = "SELECT t4.orderSn,t4.memberId,t4.payPrice,t4.paySn,t2.payamount,t2.batch,t2.activitysn,t2.memberRule,t5.email,t5.grade,t5.vipFlag,t4.channelsn "
                    + "FROM sdinformation t0,sdProductActivityLink t1,sdcouponactivityinfo t2,sdorders t4,member t5 "
                    + "WHERE ("+ ordersnSql +" ) and t2.type='11' AND t2.status='3' AND t0.productid=t1.productid  AND t1.ActivitySn=t2.activitySn "
                    + "AND t4.ordersn=t0.ordersn AND t4.memberId = t5.id AND t4.orderStatus = '7' AND t1.ActivityChannel = t4.channelsn";

        DataTable dt = new QueryBuilder(sql).executeDataTable();

        if (dt != null && dt.getRowCount() > 0) {
            Map<String, List<DataRow>> mActivityData = new HashMap<String, List<DataRow>>();
            List<DataRow> sActivityDataList = null;
			String paySn = null;
			String memberId = null;
            for (int i = 0; i < dt.getRowCount(); i++) {
                DataRow dr = dt.get(i);
                String ActivitySn = dr.getString("activitysn");
				paySn = dr.getString("paySn");
				memberId = dr.getString("memberId");
                if (mActivityData.get(ActivitySn) == null || mActivityData.get(ActivitySn).size() == 0) {
                    sActivityDataList = new ArrayList<DataRow>();
                } else {
                    sActivityDataList = mActivityData.get(ActivitySn);
                }
                sActivityDataList.add(dr);
                mActivityData.put(ActivitySn, sActivityDataList);
            }
			// 判断是否是画面刷新请求
			sql = "select count(*) from ActivitySendCouponLog where paySn = ? and memberId = ?";
			int count = new QueryBuilder(sql, paySn, memberId).executeInt();

			if (count > 0) {
				jsonMap.put(STATUS, "n");
				couponShowFlag = "n";
				logger.info("交易号==>{}会员ID==>{}已经发放过优惠券", paySn , memberId);
				return ajax(JSONObject.fromObject(jsonMap).toString(), "text/html");
			}

            // 遍历不同活动
            Set<Map.Entry<String, List<DataRow>>> sActivityData = mActivityData.entrySet();
            Iterator<Map.Entry<String, List<DataRow>>> ssActivityData_Iter = sActivityData.iterator();
            while (ssActivityData_Iter.hasNext()) {
                Map.Entry<String, List<DataRow>> entry = ssActivityData_Iter.next();
                double dActivityTotal = 0f;
                List<DataRow> dList = entry.getValue();
                String odersns = "";
                String activitySn = entry.getKey();
				double payamount = 0f;
                String batch = null;
                String vipFlag = null;
                String grade = null;
                String memberRule = null;
				String email = null;
				String couponSns = "";
				String paySnTemp = null;
				String channelsn = null;
                for (int i = 0; i < dList.size(); i++) {
                    // 同款活动，总支付金额
                    dActivityTotal += stirngToDouble(dList.get(i).getString("payPrice"));
                    odersns += "," + dList.get(i).getString("orderSn");
                    if (i == 0) {
                        payamount = stirngToDouble(dList.get(i).getString("payamount"));
                        type = dList.get(i).getString("type");
                        batch = dList.get(i).getString("batch");
                        vipFlag = dList.get(i).getString("vipFlag");
                        grade = dList.get(i).getString("grade");
                        memberRule = dList.get(i).getString("memberRule");
						memberId = dList.get(i).getString("memberId");
						email = dList.get(i).getString("email");
						paySnTemp = dList.get(i).getString("paySn");
						channelsn = dList.get(i).getString("channelsn");
                    }
                }

                // 检查会员等级是否满足活动要求
                if (StringUtil.isNotEmpty(vipFlag) && vipFlag.equals("Y")) {
					grade = "VIP";
                }
                if (StringUtil.isNotEmpty(memberRule) && !memberRule.contains(grade)) {
					logger.info("会员等级==>{}活动要求的会员等级==>{}", grade, memberRule);
					continue;
                }

                // 检查消费金额是否满足活动的满送要求
				if (dActivityTotal < payamount) {
					logger.info("总消费金额==>{}活动要求的消费金额==>{}", dActivityTotal, payamount);
					continue;
				}

				// 判断该用户是否有相同批次，并且未使用的优惠券
				sql = "SELECT COUNT(1) FROM couponinfo WHERE memberId = '" + memberId
						+ "' AND activitysn = '" + activitySn + "' AND status = '2' AND FIND_IN_SET(batch, '"+ batch +"')";
				QueryBuilder qb = new QueryBuilder(sql);
				count = qb.executeInt();
				if (count > 0) {
					logger.info("会员==>{}活动编码==>{}存在未使用的优惠券", memberId, activitySn);
					continue;
				}

				String[] batchArray = batch.split(",");
				StringBuilder sb = new StringBuilder();
				for (String batchTemp : batchArray) {
					sb.append(",'");
					sb.append(batchTemp);
					sb.append("'");
				}

				// 获取可发放优惠券详细信息
				sql = "select cou.batch,cou.shortName,cou.prop3 as couponType,cou.prop4 as discount,cou.parValue as parValue,DATE_FORMAT(cou.endtime,'%Y.%m.%d') as endtime,DATE_FORMAT(cou.starttime,'%Y.%m.%d') as starttime,cou.direction as direction, cou.couponsn as couponsn,activ.autocreate as autocreate, cou.status as status,cou.id,cou.usedUrl "
						+"from couponinfo cou,sdcouponactivityinfo activ, (select batch, MIN(STATUS) STATUS FROM couponinfo where batch in ("+sb.toString().substring(1)+") GROUP BY batch) temp "
						+" WHERE cou.batch = temp.batch AND cou.status = temp.status AND cou.activitysn = activ.activitysn AND activ.activitysn = '"+ activitySn +"' GROUP BY cou.batch";


				qb = new QueryBuilder(sql);
				dt = qb.executeDataTable();

				if (dt != null && dt.getRowCount() > 0) {
					for (int i = 0; i < dt.getRowCount(); i++) {
						DataRow dr = dt.getDataRow(i);
						// 发送优惠券
						String couponSnTemp = activityDealCommon (dr, email, memberId);
						couponSns +=  "," + couponSnTemp;
						if (StringUtil.isNotEmpty(couponSnTemp)) {
							// 优惠券展示最多3条
							if (list.size() < 3) {
								Map<String, String> paramMap = new HashMap<String, String>();
								String name = dr.getString("parValue") + "元优惠券";
								paramMap.put("name", name);
								paramMap.put("value", dr.getString("parValue"));
								paramMap.put("confine", dr.getString("direction"));
								paramMap.put("classname", dr.getString("shortName"));
								String time = dr.getString("starttime") + "-" + dr.getString("endtime").substring(5);
								paramMap.put("time", time);
								paramMap.put("link", dr.getString("usedUrl"));
								list.add(paramMap);
							}
						}
					}
				}

				// 更新活动发送优惠券记录表
				if (list.size() > 0) {
					ActivitySendCouponLogSchema schema = new ActivitySendCouponLogSchema();
					// id
					schema.setid(CommonUtil.getUUID());
					// 活动编码
					schema.setactivitySn(activitySn);
					// 优惠券批次
					schema.setbatchs(batch);
					// 优惠券编码
					if (StringUtil.isNotEmpty(couponSns)) {
						schema.setcouponSns(couponSns.substring(1));
					}
					// 会员ID
					schema.setmemberId(memberId);
					// 商家订单号
					schema.setpaySn(paySnTemp);
					// 订单号
					schema.setorderSns(odersns.substring(1));
					// 发放渠道
					schema.setchannelSn(channelsn);
					// 创建时间
					schema.setcreateDate(new Date());
					schema.insert();
				}
            }
        }
        if (list.size() > 0) {
			jsonMap.put(STATUS, "y");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("coupon", list);
			jsonMap.put("couponDateJSON", map);
			couponShowFlag = "y";
		} else {
			jsonMap.put(STATUS, "n");
			couponShowFlag = "n";
		}
        return ajax(JSONObject.fromObject(jsonMap).toString(), "text/html");
    }

	/**
	 * 优惠卷邮件发送 共同代码提取
	 *
	 * @param dr
	 * @param email
	 * @param memberID
	 */
	private String activityDealCommon (DataRow dr, String email, String memberID) {
		String autocreate = dr.getString("autocreate");
		String status = dr.getString("status");
		String now = DateUtil.getCurrentDateTime();
		String id = dr.getString("id");;

		// autocreate 1 表示优惠卷发放完毕后，则不处理；0 表示发放完毕后，自动创建
		// status 0 未使用 2 已发放
		if ("0".equals(status)) {
			// 更新数据库状态
			QueryBuilder qb_update = new QueryBuilder("update couponinfo set status='2',mail=?,memberid=?,modifyuser='system',modifydate=now(),prop2=? where couponsn=? and status='0'");
			qb_update.add(email);
			qb_update.add(memberID);
			qb_update.add(now);
			qb_update.add(dr.getString("couponsn").trim());
			int result = qb_update.executeNoQuery();

			// 发送消息提醒
			if (result == 1) {
				ActivityGiveDetail activityGiveDetail = new ActivityGiveDetail();
				activityGiveDetail.couponRemind(dr.getString("couponsn"), memberID, "");
				logger.info("会员==>{}发放优惠卷==>{}",memberID, dr.getString("couponsn"));
				return dr.getString("couponsn");
			} else {
				return createCouponInfo(id, now, memberID, email);
			}
		}
		// 自动创建
		else if ("0".equals(autocreate)) {
			return createCouponInfo(id, now, memberID, email);
		}

		return null;
	}

	/**
	 * 创建优惠券
	 *
	 * @param id
	 * @param now
	 * @param memberID
	 * @param email
	 * @return
	 */
	private String createCouponInfo (String id, String now, String memberID, String email) {
		SDCouponInfoSchema sdcouponinfoschema = new SDCouponInfoSchema();
		sdcouponinfoschema.setId(id);

		boolean result = false;
		if (sdcouponinfoschema.fill()) {
			// 如果没有优惠卷则生成新的
			// 生成优惠劵ID
			String coupon_id = DateUtil.getCurrentDateTime("yyyy") + NoUtil.getMaxNo("CouponSn", 12);
			// 优惠券ID值
			sdcouponinfoschema.setId(coupon_id);
			// 优惠券号
			sdcouponinfoschema.setCouponSn(DigestUtils.md5Hex(coupon_id));

			sdcouponinfoschema.setCreateDate(new Date());
			sdcouponinfoschema.setOrderSn("");
			sdcouponinfoschema.setStatus("2");
			// 发放时间
			sdcouponinfoschema.setProp2(now);
			// 将会员id关联到优惠券表中
			sdcouponinfoschema.setMemberId(memberID);
			sdcouponinfoschema.setMail(email);
			sdcouponinfoschema.setModifyUser("system");
			sdcouponinfoschema.setModifyDate(new Date());

			result = sdcouponinfoschema.insert();

			// 发送邮件
			if (result) {
				logger.info("会员==>{}发放优惠券==>{}",memberID, sdcouponinfoschema.getCouponSn());
				ActivityGiveDetail activityGiveDetail = new ActivityGiveDetail();
				activityGiveDetail.couponRemind(sdcouponinfoschema.getCouponSn(), memberID, "");
				return sdcouponinfoschema.getCouponSn();
			}
		}

		return null;
	}
	
	/**
	 * saveOnlineRevisit:保存线下回访记录. <br/>
	 * @author guozc
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String saveOnlineRevisit() {
		JSONObject ret = new JSONObject();
		ret.put("status", "0");
		ret.put("msg", "");
		String orderSns = getRequest().getParameter("orderSn");
		if(StringUtils.isEmpty(orderSns)){
			ret.put("status", "1");
			ret.put("msg", "订单号不能为空");
			return ajax(ret.toString(), "text/html");
		}
		
		Mapx<String, String> onlineRevisitProducts =  CacheManager.getMapx("Code", "OnLineCallBackProductID");// 支持线上回访的产品
		
		for(String orderSn : orderSns.split(",")){
			QueryBuilder qb = new QueryBuilder("SELECT COUNT(id) from onlinerevisitrecord where orderSn = ?",orderSn);
			if(qb.executeInt() > 0){
				ret.put("status", "1");
				ret.put("msg", ret.get("msg")+"|"+"订单["+orderSn+"]回访记录已经存在");
				continue;
			}
			
			OnlineRevisitRecordSchema record = new OnlineRevisitRecordSchema();
			QueryBuilder qbAppnt = new QueryBuilder("select a.ProductName as productname, b.ApplicantName as applicantname,"
					+ "b.ApplicantMail as mail,a.insuranceCompany, a.point, a.productid,b.applicantMobile "
					+ "from SDInformation a , SDInformationAppnt b "
					+ "where a.InformationSn = b.InformationSn and  a.orderSn = ?",orderSn);
			DataTable dt =  qbAppnt.executeDataTable();
			if(dt.getRowCount() > 0){
				DataRow dr = dt.get(0);
				if(!onlineRevisitProducts.containsKey(dr.getString("productid"))){
					continue;
				}
				record.setApplicantName(dr.getString("applicantname"));
		    	record.setApplicantMobile(dr.getString("applicantMobile"));
		    	record.setProductName(dr.getString("productname"));
		    	record.setCreateDate(new Date());
		    	record.setStatus("0");
		    	record.setOrderSn(orderSn);
		    	record.setProp1("wj");
		    	boolean insertResult = record.insert();
		    	if(!insertResult){
		    		ret.put("status", "1");
					ret.put("msg", ret.get("msg")+"|"+"保存订单["+orderSn+"]回访记录失败");
					continue;
		    	}
			}
		}
		return ajax(ret.toString(), "text/html");
	}
	
    private double stirngToDouble(String data) {
        if (StringUtil.isEmpty(data)) {
            return 0;
        }
        return Double.parseDouble(data);
    }
	/**
	 * 手动调用后台测试。请不要删除.
	 * 
	 * @return
	 */
	public String ybzftest() {

		return "test";
	}

	public String getType() {

		return type;
	}

	public void setType(String type) {

		this.type = type;
	}

	public String getPath() {

		return path;
	}

	public void setPath(String path) {

		this.path = path;
	}

	public void setOrderSn(String orderSn) {

		this.orderSn = orderSn;
	}

	public String getOrderSn() {

		return orderSn;
	}

	public void setErrMsg(String errMsg) {

		ErrMsg = errMsg;
	}

	public String getErrMsg() {

		return ErrMsg;
	}

	public String getOldOrdId() {

		return OldOrdId;
	}

	public void setOldOrdId(String oldOrdId) {

		OldOrdId = oldOrdId;
	}

	public String getOut_trade_no() {

		return out_trade_no;
	}

	public void setOut_trade_no(String outTradeNo) {

		out_trade_no = outTradeNo;
	}

	public String getTotal_fee() {

		return total_fee;
	}

	public void setTotal_fee(String totalFee) {

		total_fee = totalFee;
	}

	public String getTrade_state() {

		return trade_state;
	}

	public void setTrade_state(String tradeState) {

		trade_state = tradeState;
	}

	public String getTrade_mode() {

		return trade_mode;
	}

	public void setTrade_mode(String tradeMode) {

		trade_mode = tradeMode;
	}

	public String getNotify_id() {

		return notify_id;
	}

	public void setNotify_id(String notifyId) {

		notify_id = notifyId;
	}

	public String getRetcode() {

		return retcode;
	}

	public void setRetcode(String retcode) {

		this.retcode = retcode;
	}

	public void setSubject(String subject) {

		this.subject = subject;
	}

	public String getSubject() {

		return subject;
	}

	public void setAlibody(String alibody) {

		this.alibody = alibody;
	}

	public String getAlibody() {

		return alibody;
	}

	public String getRespCode() {

		return RespCode;
	}

	public void setRespCode(String respCode) {

		RespCode = respCode;
	}

	public String getTrxId() {

		return TrxId;
	}

	public void setTrxId(String trxId) {

		TrxId = trxId;
	}

	public String getRetType() {

		return RetType;
	}

	public void setRetType(String retType) {

		RetType = retType;
	}

	public String getResult() {

		return result;
	}

	public void setResult(String result) {

		this.result = result;
	}

	public String getPayType() {

		return payType;
	}

	public void setPayType(String payType) {

		this.payType = payType;
	}

	public String getVersion() {

		return Version;
	}

	public void setVersion(String version) {

		Version = version;
	}

	public String getCmdId() {

		return CmdId;
	}

	public void setCmdId(String cmdId) {

		CmdId = cmdId;
	}

	public String getMerId() {

		return MerId;
	}

	public void setMerId(String merId) {

		MerId = merId;
	}

	public String getOrdId() {

		return OrdId;
	}

	public void setOrdId(String ordId) {

		OrdId = ordId;
	}

	public String getOrdAmt() {

		return OrdAmt;
	}

	public void setOrdAmt(String ordAmt) {

		OrdAmt = ordAmt;
	}

	public String getCurCode() {

		return CurCode;
	}

	public void setCurCode(String curCode) {

		CurCode = curCode;
	}

	public String getPid() {

		return Pid;
	}

	public void setPid(String pid) {

		Pid = pid;
	}

	public void setRefAmt(String refAmt) {

		RefAmt = refAmt;
	}

	public String getRefAmt() {

		return RefAmt;
	}

	public String getRetUrl() {

		return RetUrl;
	}

	public void setRetUrl(String retUrl) {

		RetUrl = retUrl;
	}

	public String getBgRetUrl() {

		return BgRetUrl;
	}

	public void setBgRetUrl(String bgRetUrl) {

		BgRetUrl = bgRetUrl;
	}

	public String getMerPriv() {

		return MerPriv;
	}

	public void setMerPriv(String merPriv) {

		MerPriv = merPriv;
	}

	public String getGateId() {

		return GateId;
	}

	public void setGateId(String gateId) {

		GateId = gateId;
	}

	public String getUsrMp() {

		return UsrMp;
	}

	public void setUsrMp(String usrMp) {

		UsrMp = usrMp;
	}

	public String getDivDetails() {

		return DivDetails;
	}

	public void setDivDetails(String divDetails) {

		DivDetails = divDetails;
	}

	public String getPayUsrId() {

		return PayUsrId;
	}

	public void setPayUsrId(String payUsrId) {

		PayUsrId = payUsrId;
	}

	public String getChkValue() {

		return ChkValue;
	}

	public void setChkValue(String chkValue) {

		this.ChkValue = chkValue;
	}

	public String getGateUrl() {

		return GateUrl;
	}

	public void setGateUrl(String gateUrl) {

		GateUrl = gateUrl;
	}

	public void setTradeInformation(TradeInformation tradeInformation) {

		this.tradeInformation = tradeInformation;
	}

	public TradeInformation getTradeInformation() {

		return tradeInformation;
	}

	public SDOrder getSdorder() {

		return sdorder;
	}

	public void setSdorder(SDOrder sdorder) {

		this.sdorder = sdorder;
	}

	public void setTransaction_id(String transaction_id) {

		this.transaction_id = transaction_id;
	}

	public String getTransaction_id() {

		return transaction_id;
	}

	public void setDiscount(String discount) {

		this.discount = discount;
	}

	public String getDiscount() {

		return discount;
	}

	public String getUserName() {

		return userName;
	}

	public void setUserName(String userName) {

		this.userName = userName;
	}

	public String getP0_Cmd() {

		return p0_Cmd;
	}

	public void setP0_Cmd(String p0Cmd) {

		p0_Cmd = p0Cmd;
	}

	public String getP1_MerId() {

		return p1_MerId;
	}

	public void setP1_MerId(String p1MerId) {

		p1_MerId = p1MerId;
	}

	public String getP2_Order() {

		return p2_Order;
	}

	public void setP2_Order(String p2Order) {

		p2_Order = p2Order;
	}

	public String getP3_Amt() {

		return p3_Amt;
	}

	public void setP3_Amt(String p3Amt) {

		p3_Amt = p3Amt;
	}

	public String getP4_Cur() {

		return p4_Cur;
	}

	public void setP4_Cur(String p4Cur) {

		p4_Cur = p4Cur;
	}

	public String getP5_Pid() {

		return p5_Pid;
	}

	public void setP5_Pid(String p5Pid) {

		p5_Pid = p5Pid;
	}

	public String getP6_Pcat() {

		return p6_Pcat;
	}

	public void setP6_Pcat(String p6Pcat) {

		p6_Pcat = p6Pcat;
	}

	public String getP7_Pdesc() {

		return p7_Pdesc;
	}

	public void setP7_Pdesc(String p7Pdesc) {

		p7_Pdesc = p7Pdesc;
	}

	public String getP8_Url() {

		return p8_Url;
	}

	public void setP8_Url(String p8Url) {

		p8_Url = p8Url;
	}

	public String getP9_SAF() {

		return p9_SAF;
	}

	public void setP9_SAF(String p9SAF) {

		p9_SAF = p9SAF;
	}

	public String getPa_MP() {

		return pa_MP;
	}

	public void setPa_MP(String paMP) {

		pa_MP = paMP;
	}

	public String getPd_FrpId() {

		return pd_FrpId;
	}

	public void setPd_FrpId(String pdFrpId) {

		pd_FrpId = pdFrpId;
	}

	public String getPr_NeedResponse() {

		return pr_NeedResponse;
	}

	public void setPr_NeedResponse(String prNeedResponse) {

		pr_NeedResponse = prNeedResponse;
	}

	public String getHmac() {

		return hmac;
	}

	public void setHmac(String hmac) {

		this.hmac = hmac;
	}

	public String getTypeFlag() {

		return typeFlag;
	}

	public void setTypeFlag(String typeFlag) {

		this.typeFlag = typeFlag;
	}

	public String getCouponSn() {

		return CouponSn;
	}

	public void setCouponSn(String CouponSn) {

		this.CouponSn = CouponSn;
	}

	public CouponInfo getCouponInfo() {

		return couponInfo;
	}

	public void setCouponInfo(CouponInfo couponInfo) {

		this.couponInfo = couponInfo;
	}

	public String getCallBackAmount() {

		return callBackAmount;
	}

	public void setCallBackAmount(String callBackAmount) {

		this.callBackAmount = callBackAmount;
	}

	public String getJrhsURL() {

		return jrhsURL;
	}

	public void setJrhsURL(String jrhsURL) {

		this.jrhsURL = jrhsURL;
	}

	public String getIsLogin() {

		return isLogin;
	}

	public void setIsLogin(String isLogin) {

		this.isLogin = isLogin;
	}

	public String getBtype() {

		return Btype;
	}

	public void setBtype(String btype) {

		Btype = btype;
	}

	public String getOrderId() {

		return orderId;
	}

	public void setOrderId(String orderId) {

		this.orderId = orderId;
	}

	public String getPaySn() {

		return paySn;
	}

	public void setPaySn(String paySn) {

		this.paySn = paySn;
	}

	public String getPaymentSn() {

		return paymentSn;
	}

	public void setPaymentSn(String paymentSn) {

		this.paymentSn = paymentSn;
	}

	public String getNeedUWCheckFlag() {

		return needUWCheckFlag;
	}

	public void setNeedUWCheckFlag(String needUWCheckFlag) {

		this.needUWCheckFlag = needUWCheckFlag;
	}

	public String getMemberid() {

		return memberid;
	}

	public void setMemberid(String memberid) {

		this.memberid = memberid;
	}

	public String getTotalamount() {

		return totalamount;
	}

	public void setTotalamount(String totalamount) {

		this.totalamount = totalamount;
	}

	public String getPartnerUrl() {

		return partnerUrl;
	}

	public void setPartnerUrl(String partnerUrl) {

		this.partnerUrl = partnerUrl;
	}

	public String getPieces() {

		return pieces;
	}

	public void setPieces(String pieces) {

		this.pieces = pieces;
	}

	public String getProductid() {

		return productid;
	}

	public void setProductid(String productid) {

		this.productid = productid;
	}

	public String getCompanyname() {

		return companyname;
	}

	public void setCompanyname(String companyname) {

		this.companyname = companyname;
	}

	public String getRiskname() {

		return riskname;
	}

	public void setRiskname(String riskname) {

		this.riskname = riskname;
	}

	public BigDecimal getActivityParValue() {

		return activityParValue;
	}

	public void setActivityParValue(BigDecimal activityParValue) {

		this.activityParValue = activityParValue;
	}

	public String getNewUserName() {

		return newUserName;
	}

	public void setNewUserName(String newUserName) {

		this.newUserName = newUserName;
	}

	public String getTransDate() {

		return TransDate;
	}

	public void setTransDate(String transDate) {

		TransDate = transDate;
	}

	public String getPayPrice() {

		return payPrice;
	}

	public void setPayPrice(String payPrice) {

		this.payPrice = payPrice;
	}

	public String getOffsetPoint() {

		return offsetPoint;
	}

	public void setOffsetPoint(String offsetPoint) {

		this.offsetPoint = offsetPoint;
	}

	public String getPaytype() {

		return paytype;
	}

	public void setPaytype(String paytype) {

		this.paytype = paytype;
	}

	public String getCouponShowFlag() {
		return couponShowFlag;
	}

	public void setCouponShowFlag(String couponShowFlag) {
		this.couponShowFlag = couponShowFlag;
	}

	/*
	 * @Override public void addActionError(String anErrorMessage) { String s =
	 * anErrorMessage; System.out.println("PayAction addActionError:"+s); }
	 * 
	 * @Override public void addActionMessage(String aMessage) { String s =
	 * aMessage; System.out.println("PayAction addActionMessage:"+s);
	 * 
	 * }
	 * 
	 * @Override public void addFieldError(String fieldName, String
	 * errorMessage) { String s = errorMessage; String f = fieldName;
	 * System.out.
	 * println("PayAction fieldName:"+f+"---------addFieldError:"+s);;
	 * 
	 * }
	 */

	public List<SDOrder> getOrderList() {

		return orderList;
	}

	public void setOrderList(List<SDOrder> orderList) {

		this.orderList = orderList;
	}

	public String getOrderSnS() {

		return orderSnS;
	}

	public void setOrderSnS(String orderSnS) {

		this.orderSnS = orderSnS;
	}

	public void setFkID(String fkID) {

		this.fkID = fkID;
	}

	public class InsureThread extends Thread {

		private String orderSn;

		public InsureThread(String orderId) {

			orderSn = orderId;
		}

		public void run() {

			SDOrder sdorders = sdorderService.getOrderByOrderSn(orderSn);
			// 优惠拆分
			ActivityCalculate.activityeSplit(sdorders.getPaySn(), sdorders.getChannelsn());

			List<String> UWUNSucess = sdorderService.checkUnderwriting(sdorders, null);
			SDInformation sdinformation = sdinformationService.getByOrderSn(orderSn);
			try {

				// 调用经代通
				InsureTransferNew itn = new InsureTransferNew();
				itn.callInsTransInterface(sdinformation.getInsuranceCompany(), sdorders.getOrderSn(), UWUNSucess);
				String insureMsg = "";
				boolean flag = true;
				String sql = "select appStatus,insureMsg from SDInformationRiskType where orderSn = ?";
				DataTable dt = new QueryBuilder(sql, orderSn).executeDataTable();
				if (dt != null && dt.getRowCount() > 0) {
					int rowCount = dt.getRowCount();
					for (int i = 0; i < rowCount; i++) {
						if (!"1".equals(dt.getString(0, 0))) {
							flag = false;
							insureMsg = dt.getString(0, 1);
							break;
						}
					}
				} else {
					logger.warn("未查询到SDInformationRiskType表中订单相关信息");
					flag = false;
				}

				if (!flag) {// 表示投保失败
					logger.info("=后台回调方法=经代通失败 调用客户邮件");

					ErrMsg = "经代通失败:" + insureMsg;

					if (StringUtil.isEmpty(insureMsg)) {
						ErrMsg = "经代通失败:未收到保险公司回写结果";
					}
					sdorderService.sendErrorMail(sdorders.getOrderSn(), ErrMsg, "", getRequest());

					ResendMain.resendCacheAdd(sdorders.getOrderSn(), sdinformation.getInsuranceCompany(), ErrMsg);
				}
			} catch (Exception e) {
				ErrMsg = "callInsureTransfer方法出现异常:" + e.getMessage();
				sdorderService.sendErrorMail(OrdId, ErrMsg, "", getRequest());
				logger.error("调用经代通处理出现异常: callInsureTransfer方法出现异常" + e.getMessage(), e);
				ResendMain.resendCacheAdd(sdorders.getOrderSn(), sdinformation.getInsuranceCompany(), ErrMsg);
			}
		}
	}
	
	/**
	 * setFavPriceByMaxDeduction:根据折扣券设置的最高抵扣金额重新设置优惠金额. <br/>
	 * TODO 如果最高抵扣金额未设置，那么返回原优惠金额
	 * TODO 如果原优惠金额大于最高抵扣金额，那么返回最高抵扣金额<br/>
	 * TODO 如果原优惠金额小于等于最高抵扣金额，那么返回原优惠金额.<br/>
	 *
	 * @author guozc
	 * @param favPrice 原优惠金额
	 * @param maxDeductionStr 最高抵扣金额
	 * @return
	 */
	public BigDecimal setFavPriceByMaxDeduction(BigDecimal favPrice, String maxDeductionStr){
		if(StringUtil.isNotEmpty(maxDeductionStr)){
			BigDecimal maxDeduction = new BigDecimal(maxDeductionStr);
			favPrice = favPrice.compareTo(maxDeduction) > 0?maxDeduction:favPrice;
		}
		return favPrice;
	}
	
	/**
	 * sendAllinpayAuthCode:通联实名付发送短信验证码. <br/>
	 * @author guozc
	 * @return
	 */
	public String sendAllinpayAuthCode() {
    	String insureCode = getParameter("insureCode");
    	String bankCode = getParameter("bankCode");
    	String accountNo = getParameter("accountNo");
    	String accountName = getParameter("accountName");
    	String idType = getParameter("idType");
    	String idNo = getParameter("idNo");
    	String mobile = getParameter("mobile");
    	String paySn = getParameter("paySn");
    	String reqSnDieflag = getParameter("reqSnDieflag");
    	if(StringUtil.isEmpty(insureCode)){
			return ajaxJsonErrorMessage("保险公司编码不能为空");
    	}
    	if(StringUtil.isEmpty(bankCode)){
			return ajaxJsonErrorMessage("银行编码不能为空");
    	}
    	if(StringUtil.isEmpty(accountNo)){
			return ajaxJsonErrorMessage("银行卡号不能为空");
    	}
    	if(StringUtil.isEmpty(accountName)){
			return ajaxJsonErrorMessage("银行卡账户名不能为空");
    	}
    	if(StringUtil.isEmpty(idType)){
			return ajaxJsonErrorMessage("证件类型不能为空");
    	}
    	if(StringUtil.isEmpty(idNo)){
			return ajaxJsonErrorMessage("证件号码不能为空");
    	}
    	if(StringUtil.isEmpty(mobile)){
			return ajaxJsonErrorMessage("手机号不能为空");
    	}
    	if(StringUtil.isEmpty(paySn)){
			return ajaxJsonErrorMessage("paySn不能为空");
    	}
    	
    	String applyReqSn = null;
    	String cacheKey = "allinPay-reqSn-" + paySn;
    	if(StringUtil.isNotEmpty(reqSnDieflag)){
    		JedisCommonUtil.remove(3, cacheKey);
    	}
    	int count = 0;
    	if(JedisCommonUtil.hExists(3, cacheKey, "count")){
    		List<String> cacheData = JedisCommonUtil.getMap(3, cacheKey, "count", "applyReqSn");
    		count = Integer.parseInt(cacheData.get(0));
    		if(count >= 5){
    			return ajaxJsonErrorMessage("您已发送5次，请换卡支付或24小时后再次验证。客服电话4009-789-789");
			}
    		applyReqSn = cacheData.get(1);
    	}
    	
    	AllinpayService allinpayService = new AllinpayService();
    	// 实名认证+发送短信
    	if(StringUtil.isEmpty(applyReqSn)){
    		// 转换银行编码为通联类型
    		QueryBuilder qbRelation = new QueryBuilder("select payCodeValue From InsureToPayDicRelation where codeType = ? "
    				+ "and payType = ? and insureCode = ? and insureCodeValue = ?");
    		qbRelation.add("Bank");
    		qbRelation.add("tlzf");
    		qbRelation.add(insureCode);
    		qbRelation.add(bankCode);
        	bankCode = qbRelation.executeString();
        	// 转换证件号码为通联类型
        	qbRelation = new QueryBuilder("select payCodeValue From InsureToPayDicRelation where codeType = ? "
    				+ "and payType = ? and insureCode = ? and insureCodeValue = ?");
        	qbRelation.add("certificate");
        	qbRelation.add("tlzf");
        	qbRelation.add(insureCode);
        	qbRelation.add(idType);
        	idType = qbRelation.executeString();
        	
    		Rnpa rnpa = new Rnpa();
    		rnpa.setBANK_CODE(bankCode);
    		rnpa.setACCOUNT_NO(accountNo);
    		rnpa.setACCOUNT_NAME(accountName);
    		rnpa.setID_TYPE(idType);
    		rnpa.setID(idNo);
    		rnpa.setTEL(mobile);
    		Map<String, Object> appliRet = null;
    		try {
    			appliRet = allinpayService.payApply(rnpa);
    		} catch (Exception e) {
    			LOG.error("调用通联实名申请接口(211006)失败",e);
				return ajaxJsonErrorMessage("调用短信验证接口失败,请重新发送");
    		}
    		
    		String resultCode = (String) appliRet.get(Constant.RESULTCODE);
    		String msg = (String) appliRet.get(Constant.MSG);
    		String status = (String) appliRet.get(Constant.STATUS);
    		if (Constant.SUCCESS.equals(status)) {
    			applyReqSn = (String) appliRet.get(Constant.DATA);
    			// 放入缓存,有效期为15分钟
    			Map<String,String> data = new HashMap<String,String>();
    			data.put("applyReqSn", applyReqSn);
    			data.put("count", "1");
    			JedisCommonUtil.setMap(3, cacheKey, data);
    			JedisCommonUtil.expire(3, cacheKey, 15 * 60);
    			LOG.info("实名付申请成功,流水号是:" + applyReqSn);
    		} else {
    			if("sendAuthCodeFail".equals(resultCode)){
    				return ajaxJsonErrorMessage("调用短信验证接口失败，请重新发送");
    			}
    			LOG.error("实名付申请失败,返回状态码:" + resultCode + ",错误描述:" + msg);
    			String errorMsg = "<p class='ttl'>投保人信息错误！</p><span class='txt'>"+msg+"</span>";
				return ajaxJsonErrorMessage(errorMsg);
    		}
    	}
    	// 发送短信
    	else {
    		Map<String, Object> appliAgainRet;
			try {
				appliAgainRet = allinpayService.payApplyAgain(applyReqSn);
			} catch (Exception e) {
				LOG.error("调用通联实名申请接口(211006R)失败", e);
				return ajaxJsonErrorMessage("调用短信验证接口失败,请重新发送");
			}
    		String resultCode = (String) appliAgainRet.get(Constant.RESULTCODE);
    		String msg = (String) appliAgainRet.get(Constant.MSG);
    		String status = (String) appliAgainRet.get(Constant.STATUS);
    		if(JedisCommonUtil.hExists(3, cacheKey, "count")){
				JedisCommonUtil.hincrBy(3, cacheKey, "count", 1);
			}else{
				Map<String,String> data = new HashMap<String,String>();
				data.put("applyReqSn", applyReqSn);
				data.put("count", "1");
    			JedisCommonUtil.setMap(3, cacheKey, data);
    			JedisCommonUtil.expire(3, cacheKey, 15 * 60);
			}
    		if (Constant.SUCCESS.equals(status)) {
    			LOG.info("实名付短信重发成功,流水号是:" + applyReqSn);
    		} else {
    			LOG.error("实名付短信重发失败,返回状态码:" + resultCode + ",错误描述:" + msg);
    			String errorMsg = "<p class='ttl'>验证发送失败！</p><span class='txt'>60秒后还可以发送<em>"+(5-count-1)+"</em>次，"
    					+ "超过次数需换卡支付<br />或联系客服4009-789-789</span>";
				return ajaxJsonErrorMessage(errorMsg);
    		}
    	}
    	String successMsg = "<p class='ttl'>验证发送成功！</p><span class='txt'>请查看手机中的验证码,60秒后还可以发送<em>"+(5-count-1)+"</em>次，"
    			+ "超过次数需换卡支付<br />或联系客服4009-789-789</span>";
		return ajaxJsonSuccessMessage(successMsg);
	}
	
	@SuppressWarnings("unchecked")
	public String allinpayPay(){
		Map<String,Object> ret = new HashMap<String,Object>();
    	String authCode = getParameter("authCode");
    	String insureCode = getParameter("insureCode");
    	String bankCode = getParameter("bankCode");
    	String accountNo = getParameter("accountNo");
    	String accountName = getParameter("accountName");
    	String idType = getParameter("idType");
    	String idNo = getParameter("idNo");
    	String mobile = getParameter("mobile");
    	String orderSn = getParameter("orderSn");
    	String sendSmsPaySn = getParameter("sendSmsPaySn");
    	String reqSnDieflag = getParameter("reqSnDieflag");
    	if(StringUtil.isEmpty(authCode)){
			return ajaxJsonErrorMessage("验证码不能为空");
    	}
    	if(StringUtil.isEmpty(insureCode)){
			return ajaxJsonErrorMessage("保险公司编码不能为空");
    	}
    	if(StringUtil.isEmpty(bankCode)){
			return ajaxJsonErrorMessage("银行编码不能为空");
    	}
    	if(StringUtil.isEmpty(accountNo)){
			return ajaxJsonErrorMessage("银行卡号不能为空");
    	}
    	if(StringUtil.isEmpty(accountName)){
			return ajaxJsonErrorMessage("银行卡账户名不能为空");
    	}
    	if(StringUtil.isEmpty(idType)){
			return ajaxJsonErrorMessage("证件类型不能为空");
    	}
    	if(StringUtil.isEmpty(idNo)){
			return ajaxJsonErrorMessage("证件号码不能为空");
    	}
    	if(StringUtil.isEmpty(mobile)){
			return ajaxJsonErrorMessage("手机号不能为空");
    	}
    	if(StringUtil.isEmpty(orderSn)){
			return ajaxJsonErrorMessage("订单号不能为空");
    	}
    	if(StringUtil.isEmpty(paySn)){
			return ajaxJsonErrorMessage("paySn不能为空");
    	}
		String oldPaySn = paySn;
		sdorder = sdorderService.getOrderByOrderSn(OrdId);
		orderList.add(sdorder);
		orderSnS = sdorder.getOrderSn();
		String tSDOrderStatus = String.valueOf(sdorder.getOrderStatus().ordinal());
		if (!"5".equals(tSDOrderStatus)) {
			return ajaxJsonErrorMessage("该订单 （" + OrdId + "） 不是待支付状态，不允许重新支付！");
		}
		memberid = (String) getSession(Member.LOGIN_MEMBER_ID_SESSION_NAME);
		// 积分是否为非负整数
		if (!StringUtil.isInteger(offsetPoint)) {
			logger.error("支付页面抵值积分的数值为非法数字，会员id为：{}", memberid);
			return ajaxJsonErrorMessage("支付页面抵值积分的数值为非法数字");
		}
		// 获取会员的抵值积分并与会员积分做大小校验
		if (StringUtil.isNotEmpty(memberid)) {
			if (StringUtil.isNotEmpty(sdorder.getMemberId())) {
				if (!("tencent".equals(memberid))) {
					if (!(memberid.equals(sdorder.getMemberId()))) {
						logger.warn("支付页面订单会员非当前用户");
						return ajaxJsonErrorMessage("支付页面订单会员非当前用户");
					}
				}
			}
			QueryBuilder qb = new QueryBuilder("select currentValidatePoint from member where id=?");

			qb.add(memberid);
			DataTable dt = qb.executeDataTable();
			if (dt.getRowCount() == 1) {
				String currentValidatePoint = qb.executeString();
				if (StringUtil.isEmpty(currentValidatePoint)) {
					currentValidatePoint = "0";
				}
				// 抵值积分必须小于会员的可用积分
				if (new BigDecimal(currentValidatePoint)
						.compareTo(new BigDecimal(offsetPoint)) == -1) {
					logger.warn("支付页面抵值积分大于会员的可用积分，会员id为：{}", memberid);
					return ajaxJsonErrorMessage("支付页面抵值积分大于会员的可用积分");
				}
			} else if ("tencent".equals(getSession().get(Member.LOGIN_MEMBER_ID_SESSION_NAME))) {
				Cookie cookie = CookieUtil.getCookieByName(getRequest(), "loginBindId");
				if (cookie != null && (!("".equals(cookie)))) {
					// 取得绑定的loginBindId值
					String loginBindId = cookie.getValue();
					QueryBuilder qb_tencent = new QueryBuilder(
							"select currentValidatePoint from member where mBindInfoForLogin_id=?");
					qb_tencent.add(loginBindId);
					DataTable dt_tencent = qb_tencent.executeDataTable();
					if (dt_tencent.getRowCount() == 1) {
						String currentValidatePoint = qb_tencent.executeString();
						if (StringUtil.isEmpty(currentValidatePoint)) {
							currentValidatePoint = "0";
						}
						// 抵值积分必须小于会员的可用积分
						if (new BigDecimal(currentValidatePoint)
								.compareTo(new BigDecimal(offsetPoint)) == -1) {
							logger.warn("支付页面抵值积分大于联合登陆会员的可用积分，联合登陆会员mBindInfoForLogin_id为：{}", loginBindId);
							offsetPoint = "0";
						}
					} else {
						logger.warn("支付页面联合登陆会员查询不到数据，联合登陆会员mBindInfoForLogin_id为：{}", loginBindId);
						offsetPoint = "0";
					}
				} else {
					logger.warn("支付页面查询联合登陆会员积分时，没有找到loginBindId的cookie,订单号为：{}", OrdId);
					offsetPoint = "0";
				}
			} else {
				logger.warn("支付页面查询会员积分时，既不是会员，又不是联合登陆的会员，会员id为：{}", memberid);
				offsetPoint = "0";
			}
		} else {
			offsetPoint = "0";
		}
		// 维析所需的客户浏览器cookie的值
		Cookie cookie = CookieUtil.getCookieByName(getRequest(),
				"vlid_1001");
		if (cookie != null) {
			weixiCookie = cookie.getValue();
		} else {
			weixiCookie = "";
		}
		if (StringUtil.isEmpty(OrdId)) {
			return ajaxJsonErrorMessage("订单号为空");
		}
		if (StringUtil.isEmpty(paySn)) {
			return ajaxJsonErrorMessage("支付流水号为空");
		}
		if ("02".equals(typeFlag)) {
			/* 货运包合作方式 */
			this.getNewTypeFlag(paySn);
		}
		sdorder.setOffsetPoint("0");
		sdorderService.update(sdorder);
		// BigDecimal total = sdorder.getTotalAmount();
		if (StringUtil.isNotEmpty(CouponSn) && !("0".equals(CouponSn))) {// 使用优惠券
			// 获取优惠劵对象
			couponInfo = couponInfoService.getCouponInfoByCouponSn(CouponSn);
		}
		List<SDOrder> paramterList = new ArrayList<SDOrder>();
		paramterList.add(sdorder);
		// 筛选优惠活动
		Map<String, Map<String, Object>> activity_product_info1 = ActivityCalculate.ProductShoppingCartInfo(
				paramterList, sdorder.getChannelsn(), true);
		Set keySet = activity_product_info1.keySet();
		for (Iterator it = keySet.iterator(); it.hasNext();) {
			// 活动号（包含“_no_activity”）
			String activitysn = String.valueOf(it.next());
			if (!("_no_activity".equals(activitysn))) {
				// 获取该活动关联的所有信息（包括活动信息，产品信息，优惠信息）
				Map<String, Object> map_info = activity_product_info1.get(activitysn);
				// 获取产品信息list
				Map<String, String> map_activityamont = (Map<String, String>) map_info.get("ActivityAmont");
				Map<String, String> map_activityinfo = (Map<String, String>) map_info.get("ActivityInfo");
				List<Map<String, String>> list_productinfo = (List<Map<String, String>>) map_info.get("ProductInfo");
				String activitySn = map_activityinfo.get("activitySn");
				for (int i = 0; i < list_productinfo.size(); i++) {
					Map<String, String> map_productinfo = list_productinfo.get(i);
					String OrderSn = map_productinfo.get("OrderSn");
					// 活动号更新到订单表
					SDOrder sdord = sdorderService.getOrderByOrderSn(OrderSn);
					sdord.setActivitySn(activitySn);
					sdorderService.update(sdord);
				}
				// 总优惠金额累计
				activityParValue = new BigDecimal(map_activityamont.get("DiscountAmount"));
			}
		}
		if (sdorder == null) {
			return ajaxJsonErrorMessage("系统不存在该订单" + OrdId);
		}
		PayBase payBase = payService.getPayBaseByPayType(payType);
		if (payBase == null) {
			return ajaxJsonErrorMessage("未知支付类型(" + payType + ")");
		}

		Set<SDInformation> sdinformationSet = sdorder.getSdinformationSet();
		SDInformation sdinformation = null;
		for (SDInformation sd : sdinformationSet) {
			sdinformation = sd;
		}

		// 会员在支付时登录.
		Member memberLogin = getLoginMember();
		if (sdorder.getMemberId() == null
				|| "".equals(sdorder.getMemberId())) {
			if (memberLogin != null) {
				sdorder.setMemberId(memberLogin.getId());
				sdorderService.update(sdorder);
			}
		}

		// 重复单号处理
		String sql = "select * from tradeInformation where ordid='" + OrdId
				+ "'";
		QueryBuilder qbCount = new QueryBuilder(sql);
		DataTable dtCountDt = qbCount.executeDataTable();
		Transaction transaction = new Transaction();
		if (dtCountDt != null && dtCountDt.getRowCount() > 1) {
			for (int i = 1; i < dtCountDt.getRowCount(); i++) {
				String idOfTradeinformation = dtCountDt.getString(i, "id");
				transaction.add(new QueryBuilder(
						"delete  from tradeInformation where id='"
								+ idOfTradeinformation + "'"));
				transaction.commit();
			}
		}
		Map<String, String> map = createPayInformation(OrdId, null,
				payBase, sdinformation, sdorder.getTotalAmount());

		String payAmount = this.total_fee;
		
		if (map.get("errorFlag") != null
				&& "N".equals(map.get("errorFlag"))) {
			return ajaxJsonErrorMessage(map.get("errorMassage"));
		}

		if ("wx".equals(payType)) {
			this.payPrice = String.valueOf(new BigDecimal(total_fee).divide(new BigDecimal("100")));
		}

		if (StringUtil.isNotEmpty(fkID)) {
			updateCpsOrder(OrdId, "", fkID);
		}
		
    	String applyReqSn = null;
    	String cacheKey = "allinPay-reqSn-" + sendSmsPaySn;
    	if(StringUtil.isNotEmpty(reqSnDieflag)){
    		JedisCommonUtil.remove(3, cacheKey);
    	}
    	if(JedisCommonUtil.hExists(3, cacheKey, "count")){
    		List<String> cacheData = JedisCommonUtil.getMap(3, cacheKey, "applyReqSn");
    		applyReqSn = cacheData.get(0);
    	}else{
    		return ajaxJsonErrorMessage("prePayError", "验证码无效，请重新发送验证码");
    	}
    	
    	// 转换银行编码为通联类型
		QueryBuilder qbRelation = new QueryBuilder("select payCodeValue From InsureToPayDicRelation where codeType = ? "
				+ "and payType = ? and insureCode = ? and insureCodeValue = ?");
		qbRelation.add("Bank");
		qbRelation.add("tlzf");
		qbRelation.add(insureCode);
		qbRelation.add(bankCode);
    	bankCode = qbRelation.executeString();
    	
    	AllinpayService allinpayService = new AllinpayService();
    	Map<String, Object> confirmRet = null;
		try {
			confirmRet = allinpayService.payConfirm(applyReqSn, authCode);
		} catch (Exception e) {
			LOG.error("通联支付确认失败", e);
			return ajaxJsonErrorMessage("支付失败");
		}
		String resultCode = (String) confirmRet.get(Constant.RESULTCODE);
		String msg = (String) confirmRet.get(Constant.MSG);
		String status = (String) confirmRet.get(Constant.STATUS);
		if (Constant.SUCCESS.equals(status)) {
			LOG.info("实名确认成功");
		} else {
			if ("4001".equals(resultCode) || "4002".equals(resultCode)) {
				LOG.info("已验证成功或无需验证,继续往下走");
			} else {
				LOG.error("实名确认失败,返回状态码:" + resultCode + ",错误描述:" + msg);
				if("4003".equals(resultCode)){
					JedisCommonUtil.remove(3, cacheKey);
				}
				return ajaxJsonErrorMessage("prePayError", msg);
			}
		}
		JedisCommonUtil.remove(3, cacheKey);
		
    	// 支付
		Trans trans = new Trans();
		trans.setACCOUNT_NAME(accountName);
		trans.setACCOUNT_NO(accountNo);
		trans.setACCOUNT_PROP("0");
		trans.setAMOUNT(payAmount);
		trans.setBANK_CODE(bankCode);
		trans.setCURRENCY("CNY");
		trans.setCUST_USERID(paySn);
		
		Map<String, Object> payRet = null;
		try {
			payRet = allinpayService.singleTranx(trans);
		} catch (Exception e) {
			LOG.error("通联调用单笔代收接口出错", e);
			return ajaxJsonErrorMessage("支付失败");
		}
		if (Constant.SUCCESS.equals(payRet.get(Constant.STATUS))) {
			LOG.info("支付成功");
			ret.put("payStatus", "success");
		} else {
			if ("WAIT_POLLING".equals(payRet.get(Constant.RESULTCODE))) {
				ret.put("payStatus", "waiting");
				LOG.info("返回等待状态");
				QueryBuilder qb = new QueryBuilder("update sdorders set orderStatus = '6' where orderSn = ?");
				qb.add(sdorder.getOrderSn());
				qb.executeNoQuery();
				// 发送消息队列到消费者
				// 发送回调消息
				Map<String, Object> dataSendMap = new HashMap<String, Object>();
				dataSendMap.put("paySn", paySn);
				dataSendMap.put("reqSn", payRet.get(Constant.DATA));
				dataSendMap.put("channelsn", "wj");
				dataSendMap.put("operator", "allipayCallBack");
				ProducerMQ mq = new ProducerMQ();
				mq.sendToCallBack(JSON.toJSONString(dataSendMap));
				return ajaxJsonSuccessMessage(ret);
			}else{
				return ajaxJsonErrorMessage((String)payRet.get(Constant.MSG));
			}
		}
		try{
			MailAction.updateNoPaymentMail(oldPaySn, paySn, "a0010");
			// 提交至PayCallBackAction.
			Btype = "1";
			this.OrdAmt = payAmount;
			this.TrxId = this.paySn;
			this.payType = "tlzf";
			PayHttpUrlConnection.veerAction(paySn, OrdAmt, TrxId, ChkValue,
					Btype, this.payType);
			ret.put("paySn", paySn);
		}catch(Exception e){
			LOG.error("通联支付成功回调失败");
			e.printStackTrace();
			return ajaxJsonErrorMessage("支付成功，回调失败");
		}
		return ajaxJsonSuccessMessage(ret);
	}
	
	public String getBalance() {

		return balance;
	}

	public void setBalance(String balance) {

		this.balance = balance;
	}

	public String getPaypassword() {

		return paypassword;
	}

	public void setPaypassword(String paypassword) {

		this.paypassword = paypassword;
	}

	public Map getMap_point_result() {

		return map_point_result;
	}

	public void setMap_point_result(Map map_point_result) {

		this.map_point_result = map_point_result;
	}

	public List<CommentInfo> getCommentList() {

		return commentList;
	}

	public void setCommentList(List<CommentInfo> commentList) {

		this.commentList = commentList;
	}

	/*
	 * public String getBD_INFO() { return BD_INFO; }
	 * 
	 * public void setBD_INFO(String bD_INFO) { BD_INFO = bD_INFO; }
	 */

	public String getKey() {

		return key;
	}

	public void setKey(String key) {

		this.key = key;
	}

	public String getWait() {

		return wait;
	}

	public void setWait(String wait) {

		this.wait = wait;
	}
	
	public String getLcbxFlag() {
	
		return lcbxFlag;
	}
	
	public void setLcbxFlag(String lcbxFlag) {
	
		this.lcbxFlag = lcbxFlag;
	}

	public Map<String, String> getOnLineCallBackInfo() {
		return onLineCallBackInfo;
	}

	public void setOnLineCallBackInfo(Map<String, String> onLineCallBackInfo) {
		this.onLineCallBackInfo = onLineCallBackInfo;
	}
}