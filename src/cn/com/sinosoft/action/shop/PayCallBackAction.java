package cn.com.sinosoft.action.shop;

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

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;

import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.cms.resend.ResendMain;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.LogUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.framework.utility.WeixiManage;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.inter.ActivityCalculate;
import com.sinosoft.jdt.InsureTransferNew;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.TradeInfoSchema;
import com.sinosoft.schema.TradeSummaryInfoSchema;
import com.wangjin.optimize.register.AutoRegister;
import cn.com.sinosoft.entity.BindInfoForLogin;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.MemberChannel;
import cn.com.sinosoft.entity.SDInformation;
import cn.com.sinosoft.entity.SDInformationAppnt;
import cn.com.sinosoft.entity.SDInformationInsured;
import cn.com.sinosoft.entity.SDOrder;
import cn.com.sinosoft.entity.TradeInfo;
import cn.com.sinosoft.entity.TradeInformation;
import cn.com.sinosoft.service.CpsProductBuyService;
import cn.com.sinosoft.service.PayCallBackService;
import cn.com.sinosoft.service.SDOrderService;
import cn.com.sinosoft.service.SDShoppingCartService;
import cn.com.sinosoft.util.DownloadNet;
import cn.com.sinosoft.util.SpringUtil;

/**
 * 
 * @ClassName: PayCallBackAction
 * @Description: TODO(支付回调公共处理逻辑)
 * @author xxx
 * @date 2014年8月15日 上午8:38:05
 * 
 *       <p>
 *       修改历史
 *       </p>
 *       <p>
 *       序号 日期 修改人 修改原因
 *       </p>
 */
@ParentPackage("shop")
public class PayCallBackAction extends PayAction {

	private static final long serialVersionUID = 1L;

	private List<String> productIdList = new ArrayList<String>();

	// 订单号
	private String orderSn;
	// 支付金额
	private String payAmount;
	// 支付类型
	private String payType;
	// 交易流水号
	private String TrxId;
	// 签名数据
	private String ChkValue;
	// 记录错误信息
	private String ErrMsg;
	// 交易结果返回类型
	private String Btype;
	// 支付流水号
	private String paySn;
	private String usepoint = "";
	private String typeFlag = "";
	private String jrhsURL = "";// 代理人工程路径
	private PrintWriter out;

	@Resource
	private SDShoppingCartService sdshopCartService;
	@Resource
	protected SDOrderService sdorderService;

	/**
	 * 支付主方法.
	 */
	public String payCallback() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String[] argArr = {paySn, payAmount, payType, TrxId, ChkValue};
		logger.info("支付流水号:{},支付金额:{},支付类型:{},交易流水号:{},签名数据:{}", argArr);

		PayCallBackService pcbs = (PayCallBackService) SpringUtil.getBean("payCallBackService");

		String result = pcbs.doPay(paySn, payAmount, TrxId, ChkValue, Btype, payType, null, request);

		return result;
	}

	private void deleteShopCartInfo(List<SDOrder> sdorderList) {

		if (sdorderList != null && sdorderList.size() > 0) {
			StringBuffer sb = new StringBuffer();
			sb.append("delete from SDShoppingCart where orderSn in (");
			for (int i = 0; i < sdorderList.size(); i++) {
				sb.append("'" + sdorderList.get(i).getOrderSn() + "'");
				if (i < sdorderList.size() - 1) {
					sb.append(",");
				}
			}
			sb.append(")");
			com.sinosoft.framework.data.QueryBuilder qb = new com.sinosoft.framework.data.QueryBuilder(sb.toString());
			qb.executeNoQuery();

		}

	}

	public static void main(String[] args) {

		DownloadNet DownloadNet = new DownloadNet();
		Map<String, Object> emailMap = DownloadNet.findInformationAppntByOrderSn("2013000000014424");
		emailMap.put("FilePath", "");
		emailMap.put("FileName", "");
		Member meber = new Member();
		meber.setEmail(emailMap.get("mail").toString());
		emailMap.put("Member", meber);
		emailMap.put("OrderSn", "2013000000014424");
//		ActionUtil.deal("wj00041", emailMap, null);

		// Map<String, Object> map = new HashMap<String, Object>();
		// Member member = new Member();
		// member.setEmail("wangcaiyun@kaixinbao.com");
		// map.put("Member", member);
		// map.put("PaySn", "W151000000760002900D");
		// List<SDOrder> sdorderList = new ArrayList<SDOrder>();
		// SDOrder sDOrder = new SDOrder();
		// sDOrder.setOrderSn("2013000000014424");
		// sdorderList.add(sDOrder);
		// map.put("SDOrderList", sdorderList);
		// ActionUtil.deal("wj00097", map, null);
	}

	/**
	 * 根据订单号删除责任临时信息
	 * 
	 * @param orderSns
	 */
	private void deleteDutyTemp(String orderSns) {

		Transaction ts = new Transaction();
		ts.add(new QueryBuilder("delete from SDInformationDutyTemp where OrderSn in ('" + orderSns + "')"));
		ts.commit();
	}

	/**
	 * 通过支付中间表更新订单表. 影响字段:couponsn、offsetPoint. 优惠券更新，通过券号更新支付流水号.
	 * 
	 * @param tradeInfo
	 */
	@SuppressWarnings("rawtypes")
	private void writeBackPayInfo(TradeInfo tradeInfo, List<SDOrder> sdorderList) {

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
				tradeinfoschema.setpayType(payType);
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
			update_tradeinformation.add(paySn);
			update_tradeinformation.add(TrxId);
			update_tradeinformation.add(formatter.format(currentTime));
			update_tradeinformation.add(ChkValue);
			update_tradeinformation.add(payType);

			trans.add(update_tradeinformation);
			// 更新tradesummaryinfo表
			TradeSummaryInfoSchema tradesummaryinfoschema = new TradeSummaryInfoSchema();
			tradesummaryinfoschema.setid(NoUtil.getMaxNo("TradeSummaryID", 11));
			tradesummaryinfoschema.setPaySn(tradeInfo.getPaySn());
			tradesummaryinfoschema.setTradeSn(TrxId);
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
			usepoint = tradeInfo.getIntegral();
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
						paySn, tradeInfo.getCouponSn()));
			}
			tradesummaryinfoschema.setPayType(payType);
			DataTable dt_paytypename = new QueryBuilder("SELECT description FROM paybase WHERE paytype=?", payType)
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
	public void addProductSalesVolume(List<String> countList) {

		Transaction trans = new Transaction();
		int length = productIdList.size();
		String sql = "select RelaID from zdcolumnvalue where columncode ='RiskCode' and textvalue= ?";
		DataTable dt;
		String condition = "";
		for (int i = 0; i < length; i++) {
			// 更新产品中心
			trans.add(new QueryBuilder("update femrisk set SalesVolume = (SalesVolume + "+countList.get(i)+") where RiskCode=?", productIdList
					.get(i)));

			trans.add(new QueryBuilder("update femriskb set SalesVolume = (SalesVolume + "+countList.get(i)+") where RiskCode=?", productIdList
					.get(i)));

			// 更新搜索产品销量数据同步表
			trans.add(new QueryBuilder("update sdsearchrelaproduct set SalesVolume = (SalesVolume + "+countList.get(i)+") where ProductID=?",
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
				trans.add(new QueryBuilder("update zdcolumnvalue set TextValue = (TextValue + "+countList.get(i)+") where RelaID in ("
						+ condition + ") and columncode ='SalesVolume'"));
			}
		}
		if (!trans.commit()) {
			logger.error("产品中心产品增加销量失败");
		}
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
	 * 
	 * @Title: LoginSuccessPay
	 * @Description: TODO(订单支付成功后邮件通知会员)
	 * @return void 返回类型
	 * @author XXX
	 */
	@SuppressWarnings("static-access")
	private void LoginSuccessPay(List<SDOrder> sdorderList) {

		try {
			Map<String, Object> map = new HashMap<String, Object>();
			List<String> orderSns = new ArrayList<String>();
			for (SDOrder sorder : sdorderList) {
				orderSns.add(sorder.getOrderSn());
			}
			map.put("orderSns", orderSns);
			ActionUtil.sendMessage("wj00016", map);
			ActionUtil.dealAction("wj00016", map, getRequest());

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
	private String getCpsParam(String flag, SDOrder sdorder, TradeInformation tradeInformation) {

		Cookie[] cookies = getRequest().getCookies();
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
					logger.error("orderSn：{}归属MemberID：{}没有成功！", sdorder.getOrderSn(), "没有成功！");
				}
			}
		}
	}

	/**
	 * 调用经代通处理.
	 * 
	 * @param sorder
	 * @param old
	 * @param OrdId
	 */
	@SuppressWarnings("static-access")
	protected void callInsureTransfer(SDOrder sorder, TradeInformation old, String OrdId) {

		// 调用横向接口
		List<String> UWUNSucess = sdorderService.checkUnderwriting(sorder, getRequest());
		Member member = getLoginMember();
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
				tInsureTransfer.callInsTransInterface(comCode, sorder.getOrderSn(), UWUNSucess);
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

					ErrMsg = "经代通失败:" + insureMsg;

					if (StringUtil.isEmpty(insureMsg)) {
						ErrMsg = "经代通失败:未收到保险公司回写结果";
					}
					sdorderService.sendErrorMail(sorder.getOrderSn(), ErrMsg, "", getRequest());

					ResendMain.resendCacheAdd(sorder.getOrderSn(), comCode, ErrMsg);
				} else if (flag) {
					// Member isLoginMember = getLoginMember();
					// if (isLoginMember == null) {// 表示操作后注册
					// LogUtil.info("=后台回调方法=表示操作后注册");
					// Map<String, Object> map = new HashMap<String, Object>();
					// map.put("OrderSn", sorder.getOrderSn());
					// Member mem = new Member();
					// GetDBdata db = new GetDBdata();
					// String ss =
					// "select b.applicantMail from SDInformation a , SDInformationAppnt b where a.informationSn=b.informationSn and a.orderSn=?";
					// String[] sstemp = { OrdId };
					// String email;
					// Object applicantName = new
					// QueryBuilder("select a.applicantName from sdinformationappnt a ,sdinformation b where a.informationSn=b.informationSn and b.OrderSn=?",
					// sorder
					// .getOrderSn()).executeOneValue();
					// Object productName = new
					// QueryBuilder("select productName from sdinformation where  OrderSn=? ",
					// sorder.getOrderSn()).executeOneValue();
					// map.put("ApplicantName", applicantName);
					// map.put("ProductName", productName);
					// try {
					// email = db.getOneValue(ss, sstemp);
					// System.out.println("email:" + email);
					// if (!StringUtil.isEmpty(email)) {
					// mem.setEmail(email);
					// map.put("Member", mem);
					// String sql =
					// "select value from zdconfig where type= 'operationRegister '";
					//
					// String path = db.getOneValue(sql);
					// // codetype 0表示非车险产品 1表示车险一键报价 2
					// // 预约投保 source 0 直接 1间接
					// map.put("path", path + "?serialNO=" + sorder.getOrderSn()
					// + "&codeType=0&source=1");// 操作后链接地址
					// map.put("paidOrdAmt", old.getOrdAmt());
					// map.put("tradeSerialNO", old.getTradeSeriNO());
					// map.put("paidData", old.getReceiveDate().substring(0,
					// 11));// 支付时间
					// map.put("Points", sdinformation.getPoint());
					// ActionUtil tActionUtil = new ActionUtil();
					// tActionUtil.deal("wj00029", map, getRequest());
					// }
					// } catch (Exception e) {
					// LogUtil.error("出现异常:" + "callInsureTransfer方法出现异常" +
					// e.getMessage());
					// }
					//
					// } else {
					//
					// Map<String, Object> map = new HashMap<String, Object>();
					// map.put("Member", member);
					// if (!"".equals(member.getUsername())) {
					// map.put("MemberName", member.getUsername());
					// } else if (!"".equals(member.getRealName())) {
					// map.put("MemberName", member.getRealName());
					// } else {
					// map.put("MemberName", "");
					// }
					//
					// StringBuffer sb = new StringBuffer();
					//
					// // Map<String, String> PolicyMap;
					// // List<Map<String,String>> PolicyList = new
					// // ArrayList<Map<String,String>>();
					//
					// for (SDInformationInsured sdInsured :
					// sdinformationInsuredSet) {
					// // PolicyMap = new HashMap<String, String>();
					// String ordersn = sdInsured.getOrderSn();
					// String recognizeeSn = sdInsured.getRecognizeeSn();
					// // 被保人姓名
					// String recognizeeName = sdInsured.getRecognizeeName();
					// // 保单号
					// String policyNo = "";
					// // 产品名称
					// String productName = "";
					// String sql =
					// "select policyNo,riskName from SDInformationRiskType where orderSn = ? and recognizeeSn = ?";
					// DataTable dt = new QueryBuilder(sql, ordersn,
					// recognizeeSn).executeDataTable();
					// if (dt.getRowCount() > 0) {
					// policyNo = dt.getString(0, 0);
					// productName = dt.getString(0, 1);
					// }
					//
					// sb.append("<tr>");
					// sb
					// .append("<td align=\"center\" style=\"color:#999999;  border-bottom:1px solid  #BCCDE7;  text-align: center;\" height=\"48px;\"><font style=\"color:#3399CC; font-size:12px; \">"
					// + ordersn + "</font></td>");
					// sb.append("<td align=\"center\" style=\"color:#999999; border-bottom:1px solid  #BCCDE7;  text-align: center;  font-size:12px;\">"
					// + productName + "</td>");
					// sb.append("<td align=\"center\" style=\"color:#999999; border-bottom:1px solid  #BCCDE7;   text-align: center; font-size:12px;\">"
					// + recognizeeName + "</td>");
					// sb.append("<td align=\"center\" style=\"color:#F15717; border-bottom:1px solid  #BCCDE7;  text-align: center; font-size:12px; font-family:'Microsoft YaHei' \">"
					// + policyNo
					// + "</td>");
					// sb.append("</tr>");
					//
					// // 暂时注释,改为内部发送启用
					// // PolicyMap.put("OrderSn", ordersn);
					// // PolicyMap.put("policyNo", policyNo);
					// // PolicyMap.put("recognizeeName", recognizeeName);
					// // PolicyMap.put("productName", productName);
					//
					// // PolicyList.add(PolicyMap);
					// }
					//
					// // map.put("PolicyList", PolicyList);
					//
					// map.put("detail", sb);
					// map.put("Points", sdinformation.getPoint());
					// map.put("paidOrdAmt", old.getOrdAmt());
					// map.put("tradeSerialNO", old.getTradeSeriNO());
					// map.put("paidData", old.getReceiveDate().substring(0,
					// 11));// 支付时间
					// ActionUtil tActionUtil = new ActionUtil();
					// tActionUtil.deal("wj00030", map, getRequest());
					// }
				}
			} catch (Exception e) {
				ErrMsg = "callInsureTransfer方法出现异常:" + e.getMessage();
				sdorderService.sendErrorMail(OrdId, ErrMsg, "", getRequest());
				logger.error("调用经代通处理出现异常: callInsureTransfer方法出现异常" + e.getMessage(), e);
				ResendMain.resendCacheAdd(sorder.getOrderSn(), comCode, ErrMsg);
			}
		}
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
		// QueryBuilder qb_companyname = new
		// QueryBuilder("SELECT codename FROM zdcode WHERE codetype = 'SupplierCode'  AND codevalue=?",
		// sdorder.getSdinformationSet().iterator().next().getProductId()
		// .substring(0, 4));
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

				} else if (sdorder.getChannelsn().endsWith("swpt")) {
					webServiceUrl = Config.getValue("CPSSERVERICEURL");

				} else {
					return;
				}

				Service servicemodel = new ObjectServiceFactory().create(CpsProductBuyService.class);
				CpsProductBuyService service = (CpsProductBuyService) new XFireProxyFactory().create(servicemodel,
						webServiceUrl);
				service.saveOrder(sdorder.getOrderSn());
			}
		} catch (MalformedURLException e) {
			logger.error(e.getMessage(), e);
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

	public String getPayType() {

		return payType;
	}

	public void setPayType(String payType) {

		this.payType = payType;
	}

	public String getTrxId() {

		return TrxId;
	}

	public void setTrxId(String trxId) {

		TrxId = trxId;
	}

	public String getChkValue() {

		return ChkValue;
	}

	public void setChkValue(String chkValue) {

		ChkValue = chkValue;
	}

	public String getBtype() {

		return Btype;
	}

	public void setBtype(String btype) {

		Btype = btype;
	}

	public String getPaySn() {

		return paySn;
	}

	public void setPaySn(String paySn) {

		this.paySn = paySn;
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
