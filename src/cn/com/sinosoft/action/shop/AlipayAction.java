package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.bean.CommentInfo;
import cn.com.sinosoft.entity.CouponInfo;
import cn.com.sinosoft.entity.GALog;
import cn.com.sinosoft.entity.InsuredCompanyReturnData;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.SDInformation;
import cn.com.sinosoft.entity.SDOrder;
import cn.com.sinosoft.entity.TradeInformation;
import cn.com.sinosoft.service.CouponInfoService;
import cn.com.sinosoft.service.GALogService;
import cn.com.sinosoft.service.PayBaseService;
import cn.com.sinosoft.service.SDOrderService;
import cn.com.sinosoft.service.TradeInformationService;
import cn.com.sinosoft.util.Constant;

import com.alipay.util.AlipayNotify;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.cache.CacheManager;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.LogUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.points.PointsCalculate;
import com.wangjin.infoseeker.OrderSeeker;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@ParentPackage("shop")
public class AlipayAction extends BaseShopAction {
	private static final long serialVersionUID = 4912132035942378458L;
	@Resource
	private SDOrderService sdorderService;
	@Resource
	private TradeInformationService tradeInformationService;
	@Resource
	private CouponInfoService couponInfoService;
	
	@Resource
	protected PayBaseService payService;
	/**
	 * GALog服务: gaLogService
	 */
	@Resource
	private GALogService gaLogService;
	private SDOrder sdorder;
	private TradeInformation tTradeInformation;
	private String ErrMsg;
	private InsuredCompanyReturnData tInsuredCompanyReturnData;
	private TradeInformation tradeInformation;

	private String orderId;
	private String paySn;//支付流水号

	private String isLogin;

	private String userName;

	private String productName;

	private String productId;

	private String productGenera;

	private String orderSn;
	private String OrdAmt;
	private String ChkValue;
	private String TrxId;
	private String payType;

	private String paymentSn;//支付交易流水号
	private String typeFlag;
	private String jrhsURL = "";// 代理人工程路径

	// 优惠劵实体类
	private CouponInfo couponInfo;
	// 优惠劵实付金额
	private String callBackAmount;
	private String needUWCheckFlag;//是否需要核保标记
	
	HttpServletResponse response = ServletActionContext.getResponse();
	PrintWriter out;
	// 新用户名
	private String newUserName;
	//会员等级及积分信息
	private Map map_point_result;
	private List<CommentInfo> commentList = new ArrayList<CommentInfo>();
	private String channelsn;
	// 投保人姓名
	private String appntName;
	// 理财保险区分
	private String lcbxFlag;
	// 线上回访信息
	private Map<String, String> onLineCallBackInfo;
	
	//private String BD_INFO = ""; //百分点合作结束，百分点核心代码暂存
	/**
	 * 支付宝后台回调方法.
	 * 
	 * @return
	 */
	public String notifyUrl() {
		try {
			HttpServletRequest request = getRequest();
			// 获取支付宝POST过来反馈信息
			Map<String, String> params = new HashMap<String, String>();
			Map requestParams = request.getParameterMap();
			for (Iterator iter = requestParams.keySet().iterator(); iter
					.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				// valueStr = new String(valueStr.getBytes("ISO-8859-1"),
				// "UTF-8");
				params.put(name, valueStr);
			}
			// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
			String trade_no = request.getParameter("trade_no"); // 支付宝交易号
			String order_no = request.getParameter("out_trade_no"); // 获取订单号
			String total_fee = request.getParameter("total_fee"); // 获取总金额
			String sign = request.getParameter("sign"); // 获取总金额
			// String subject = request.getParameter("subject");// 商品名称、订单名称

			paySn = order_no;
			OrdAmt = total_fee;
			ChkValue = sign;
			TrxId = trade_no;
			payType = "zfb";

			String body = "";
			if (request.getParameter("body") != null) {
				body = request.getParameter("body");// 商品描述、订单备注、描述
			}
			String buyer_email = request.getParameter("buyer_email"); // 买家支付宝账号
			String trade_status = request.getParameter("trade_status"); // 交易状态
			logger.info("--支付宝后台回调: paySn{},支付状态：{}",order_no, trade_status);
			if (AlipayNotify.verify(params)) {// 验证成功
				out = response.getWriter();
				if (trade_status.equals("TRADE_FINISHED") ) {
					out.print("success");
				} else if (trade_status.equals("WAIT_BUYER_PAY")) {
					out.print("success");
				} else  if ( trade_status.equals("TRADE_SUCCESS")) {
					out.print("success");
					
				    //确认支付回调，发送到第三方消息队列-订单编号,status:0000-成功；1111-失败；
				    payService.sendOrderSnToReceiver(order_no,"0000",payType);

					PayHttpUrlConnection.veerAction(paySn, OrdAmt, TrxId, ChkValue, "2",payType);
					return null;
				}
			} else {// 验证失败
				ErrMsg = "支付宝后台回调签名验证失败";
			  //确认支付回调，发送到第三方消息队列-订单编号,status:0000-成功；1111-失败；
			  payService.sendOrderSnToReceiver(order_no,"1111",payType);
				logger.error("ErrMsg:{}", ErrMsg);
				return ERROR;
			}
		} catch (Exception e) {
			ErrMsg = "支付宝后台返回主程序异常:" + e.getMessage();
			logger.error(ErrMsg, e);
			sdorderService.sendErrorMailByPaySn(paySn, ErrMsg, "",
					getRequest());
		}

		return "success";
	}

	/**
	 * 支付宝前台回调方法.
	 * 
	 * @return
	 */
	public String returnUrl() {

		/* 货运保回调显示 */
		this.getNewTypeFlag(paySn);
		try {
			if (paySn == null) {
				addActionError("支付流水号为空！");
				return ERROR;
			}
			List<SDOrder> sorderList = null;
			if (paySn.startsWith("W")) {
				sorderList = sdorderService.getOrderByPaySn(PubFun.getPaySnUnion(paySn));
			} else {
				sorderList = sdorderService.getOrderByPaySn(paySn);
			}
			
			if (sorderList == null || sorderList.size() == 0) {
				addActionError("系统不存在该支付流水号" + paySn);
				return ERROR;
			}
			needUWCheckFlag = sdorderService.getUWFlagByPaySn(paySn);
			String orderPaySn = (String) getSession("paySn");
			if (StringUtil.isNotEmpty(orderPaySn)) {
				if (orderPaySn.indexOf(paySn) == -1) {
					setSession("paySn", orderPaySn + "," + paySn);
				}
			} else {
				setSession("paySn", paySn);
			}
			logger.info("paySn放入到session内{}", getSession("paySn"));

			boolean isPay = false;
			int index = 0;
			while (index <= 5) {
				for(SDOrder sdor : sorderList){
					tradeInformation = tradeInformationService
							.isPayFinnish(sdor.getOrderSn());
					if (tradeInformation != null) {
						paymentSn = tradeInformation.getTradeSeriNO();
						isPay = true;
						break;
					}
				}
				if(isPay){
					break;
				}
				Thread.sleep(1 * 3000);
				index++;
			}

			if (!isPay) {
				addActionError("未支付完成");
				return "newerror";
			}
			try {
				String ip = getRequest().getHeader("X-Forwarded-For");
				if (ip == null || ip.length() == 0
						|| "unknown".equalsIgnoreCase(ip)) {
					ip = getRequest().getHeader("Proxy-Client-IP");
				}
				if (ip == null || ip.length() == 0
						|| "unknown".equalsIgnoreCase(ip)) {
					ip = getRequest().getHeader("WL-Proxy-Client-IP");
				}
				if (ip == null || ip.length() == 0
						|| "unknown".equalsIgnoreCase(ip)) {
					ip = getRequest().getRemoteAddr();
				}
				orderId = "";
				int sorderCount = 0;
				commentList = new ArrayList<CommentInfo>();
				MemberCommentAction memberCommentAction = new MemberCommentAction();
				Map<String, Map<String, String>> purposeMap = new HashMap<String, Map<String, String>>();
				onLineCallBackInfo = null;
				for(SDOrder sorder : sorderList){
					SDInformation sdinformation = null;
					Set<SDInformation> sdInformationSet = sorder.getSdinformationSet();
					for (SDInformation sdi : sdInformationSet) {
						sdinformation = sdi;
					}
					if (sdinformation == null) {
						return ERROR;
					}
					channelsn = sorder.getChannelsn();
					sorderCount++;
					productId = sdinformation.getProductId();
					
					// 线上回访的产品
					@SuppressWarnings("unchecked")
					Mapx<String, String> productcodes =  CacheManager.getMapx("Code", "OnLineCallBackProductID");
					if (StringUtil.isNotEmpty(productId) && productcodes.containsKey(productId)) {
						// 取得线上回访展示信息
						onLineCallBackInfo = getOnLineCallBackInfo(sdinformation, sorder.getTotalAmount().toString(), sorder.getPayPrice());
					}
					
					String[] lcx_arr = Config.getValue("LicaiBaoxianProducts").split(",");
					List<String> listLcx = Arrays.asList(lcx_arr);
					if (StringUtil.isNotEmpty(productId) && listLcx.contains(productId)) {
						lcbxFlag = "true";
					}
					
					recordGAInformation(productId,ip,sorder.getOrderSn());
					orderId = orderId + sorder.getOrderSn() + ",";
					// 多订单两个订单号一行
					if (sorderCount % 2 == 0 && sorderCount != sorderList.size()) {
						orderId += "<br>";
					}
					
					CommentInfo commentInfo = new CommentInfo();
					commentInfo.setOrderSn(sorder.getOrderSn());
					commentInfo.setProductName(sdinformation.getProductName());
					String productType = new QueryBuilder("select ProductType from sdproduct where ProductID=?", productId).executeString();
					// 取得产品投保目的
					if (!purposeMap.containsKey(productType)) {
						purposeMap.put(productType, memberCommentAction.getPurpose(productType));
					}
					commentInfo.setPurpose(purposeMap.get(productType));
					if (sorder.getCommentId() != null) {
						commentInfo.setDisCommented("");
						commentInfo.setDisComment("none");
						commentInfo.setPoints(new QueryBuilder("select Integral from SDIntCalendar where memberId = ? and businessid = ? and Source = '1'", sorder.getMemberId(), sorder.getOrderSn()).executeString());
					} else {
						commentInfo.setDisCommented("none");
						commentInfo.setDisComment("");
					}
						
					commentList.add(commentInfo);
				}
				// ++++++++++++++++百分点埋点需要
				//if (StringUtil.isNotEmpty(BD_INFO)) {
					//BD_INFO = BD_INFO.substring(0, BD_INFO.length() - 1);
				//}
				// ++++++++++++++++百分点埋点需要
				
				if(StringUtil.isNotEmpty(orderId)){
					orderId = orderId.substring(0,orderId.length()-1);
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
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
			if (Constant.JFSC_CHANNELSN.equals(channelsn)
					|| Constant.WJ_JFSC_CHANNELSN.equals(channelsn)
					|| Constant.WAP_JFSC_CHANNELSN
							.equals(channelsn)) {
				callBackAmount = sorderList.get(0).getOffsetPoint();
			} else {
				// 页面返回金额处理
				DataTable dt_amount=new QueryBuilder("select tradeamount from tradesummaryinfo where paysn=?",paySn).executeDataTable();
				if(dt_amount.getRowCount()>0){
					callBackAmount=String.valueOf(new BigDecimal(dt_amount.getString(0, 0)).setScale(2,BigDecimal.ROUND_HALF_UP));
				}else{
					logger.error("支付成功后查询交易表中支付金额错误，支付流水号为：{}", paySn);
					addActionError("支付异常");
					return "newerror";
				}
			}
			if (StringUtil.isEmpty(memberID)) {
				memberID = sorderList.get(0).getMemberId();
			}
			//积分及会员等级信息
			if (StringUtil.isNotEmpty(memberID)){
				map_point_result=new PointsCalculate().getMemberUpgradeInfo(memberID, sorderList);
				
			} else {
				map_point_result=null;
			}
			return "result";
		} catch (Exception e) {
			addActionError("出现异常：" + e.getMessage());
			return ERROR;
		}
	}
	
	public Map<String, String> getOnLineCallBackInfo(SDInformation sdinformation, String totalAmount, String payPrice) {
		Map<String, String> onLineCallBackInfo = new HashMap<String, String>();
		onLineCallBackInfo.put("productId", sdinformation.getProductId());
		onLineCallBackInfo.put("productName", sdinformation.getProductName());
		onLineCallBackInfo.put("totalAmount", totalAmount);
		onLineCallBackInfo.put("chargeYear", sdinformation.getChargeYear());
		onLineCallBackInfo.put("chargeYearName", sdinformation.getChargeYearName());
		onLineCallBackInfo.put("ensureDisplay", sdinformation.getEnsureDisplay());
		String appntName = new QueryBuilder("select applicantName from sdinformationappnt where informationSn=?", sdinformation.getInformationSn()).executeString();
		onLineCallBackInfo.put("appntName", appntName);
		onLineCallBackInfo.put("hesitatePeriod", new QueryBuilder("select Hesitation from fmrisk where riskcode=?", sdinformation.getProductId()).executeString());
		
		return onLineCallBackInfo;
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

		// 用手机号校验
		qb = new QueryBuilder(Mobilesql, mobileNo);
		Long createTime = (Long) qb.executeOneValue();
		if (createTime != null) {
			// member的创建时间距离当前时间不超过3分钟时 判断此用户为新用户
			boolean flag = createTime + 180 >= seconds;
			if (flag) {
			    setNewUserName(mobileNo);
			}
			return;
		}

		// 用邮箱校验
		qb = new QueryBuilder(mailsql, email);
		createTime = (Long) qb.executeOneValue();
		if (createTime != null) {
			// member的创建时间距离当前时间不超过3分钟时 判断此用户为新用户
			boolean flag = createTime + 180 >= seconds;
			if (flag) {
				setNewUserName(email);
			} 
			return;
		}

		// 会员表中无用户信息 即为新用户  新用户名为邮箱
		// setNewUserName(email);
		// req20160331701 修改为以手机号为新用户名
		if (StringUtil.isNotEmpty(mobileNo)) {
			setNewUserName(mobileNo);
		} else {
			setNewUserName(email);
		}
	}
	
	private void recordGAInformation(String productId,String ip,String orderSn){
		try {
			GetDBdata db = new GetDBdata();
			String sa = "select productGenera from sdproduct where productID = ? limit 1";
			String[] satemp = { productId };
			productGenera = db.getOneValue(sa, satemp);
			GALog.saveGALogRecord(gaLogService, GALog.PAY_SUCCESS, null, orderSn);
			OrderSeeker.alipayInfoInsert(orderSn, ip);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 金融界回调判断结果显示
	 * 
	 * @param orderSn
	 */
	public void getNewTypeFlag(String paySn) {
		String ss = "SELECT a.channelCode,a.typeFlag FROM sdorderitem a,sdorders b WHERE a.ordersn=b.ordersn and b.paySn=? LIMIT 1";
		DataTable dt = new QueryBuilder(ss, orderSn).executeDataTable();
		try {
			if (dt.getRowCount() > 0) {
				String channelCode = dt.getString(0, 0);
				this.typeFlag = dt.getString(0, 1);
				if (StringUtil.isNotEmpty(channelCode)) {
					jrhsURL = sdorderService.getJRHSURLByConfig(channelCode);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}
	public String getIsLogin() {
		return isLogin;
	}

	public void setIsLogin(String isLogin) {
		this.isLogin = isLogin;
	}

	public InsuredCompanyReturnData gettInsuredCompanyReturnData() {
		return tInsuredCompanyReturnData;
	}

	public void settInsuredCompanyReturnData(
			InsuredCompanyReturnData tInsuredCompanyReturnData) {
		this.tInsuredCompanyReturnData = tInsuredCompanyReturnData;
	}

	public TradeInformation gettTradeInformation() {
		return tTradeInformation;
	}

	public void settTradeInformation(TradeInformation tTradeInformation) {
		this.tTradeInformation = tTradeInformation;
	}

	public SDOrder getSdorder() {
		return sdorder;
	}

	public void setSdorder(SDOrder sdorder) {
		this.sdorder = sdorder;
	}

	public void setErrMsg(String errMsg) {
		ErrMsg = errMsg;
	}

	public String getErrMsg() {
		return ErrMsg;
	}

	public void setTradeInformation(TradeInformation tradeInformation) {
		this.tradeInformation = tradeInformation;
	}

	public TradeInformation getTradeInformation() {
		return tradeInformation;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductGenera() {
		return productGenera;
	}

	public void setProductGenera(String productGenera) {
		this.productGenera = productGenera;
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

	public String getCallBackAmount() {
		return callBackAmount;
	}

	public void setCallBackAmount(String callBackAmount) {
		this.callBackAmount = callBackAmount;
	}

	public CouponInfo getCouponInfo() {
		return couponInfo;
	}

	public void setCouponInfo(CouponInfo couponInfo) {
		this.couponInfo = couponInfo;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public String getOrdAmt() {
		return OrdAmt;
	}

	public void setOrdAmt(String ordAmt) {
		OrdAmt = ordAmt;
	}

	public String getChkValue() {
		return ChkValue;
	}

	public void setChkValue(String chkValue) {
		ChkValue = chkValue;
	}

	public String getTrxId() {
		return TrxId;
	}

	public void setTrxId(String trxId) {
		TrxId = trxId;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
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

	public String getNewUserName() {
		return newUserName;
	}

	public void setNewUserName(String newUserName) {
		this.newUserName = newUserName;
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

	public String getChannelsn() {
		return channelsn;
	}

	public void setChannelsn(String channelsn) {
		this.channelsn = channelsn;
	}
	
	public String getAppntName() {
		return appntName;
	}
	
	public void setAppntName(String appntName) {
		this.appntName = appntName;
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
	
/*	public String getBD_INFO() {
		return BD_INFO;
	}

	public void setBD_INFO(String bD_INFO) {
		BD_INFO = bD_INFO;
	}*/
	
	
}
