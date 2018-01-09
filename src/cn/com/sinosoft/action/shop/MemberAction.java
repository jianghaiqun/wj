package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.bean.CartItemCookie;
import cn.com.sinosoft.bean.SystemConfig;
import cn.com.sinosoft.common.JCaptchaEngine;
import cn.com.sinosoft.common.email.MessageConstant;
import cn.com.sinosoft.entity.BindInfoForLogin;
import cn.com.sinosoft.entity.CartItem;
import cn.com.sinosoft.entity.Dict;
import cn.com.sinosoft.entity.GALog;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.MemberChannel;
import cn.com.sinosoft.entity.MemberHobby;
import cn.com.sinosoft.entity.Product;
import cn.com.sinosoft.entity.SDOrder;
import cn.com.sinosoft.entity.SDShoppingCart;
import cn.com.sinosoft.entity.SDtargetInformation;
import cn.com.sinosoft.entity.ShowInsurance;
import cn.com.sinosoft.service.AgreementService;
import cn.com.sinosoft.service.BindInfoForLoginService;
import cn.com.sinosoft.service.CartItemService;
import cn.com.sinosoft.service.GALogService;
import cn.com.sinosoft.service.MailService;
import cn.com.sinosoft.service.MemberChannelService;
import cn.com.sinosoft.service.MemberHobbyService;
import cn.com.sinosoft.service.MemberRankService;
import cn.com.sinosoft.service.MemberService;
import cn.com.sinosoft.service.ProductService;
import cn.com.sinosoft.service.SDOrderService;
import cn.com.sinosoft.service.SDShoppingCartService;
import cn.com.sinosoft.service.SDtargetInformationService;
import cn.com.sinosoft.service.ShowInsuranceService;
import cn.com.sinosoft.util.JdbcTemplateData;
import com.finance.util.JedisCommonUtil;
import com.octo.captcha.service.CaptchaService;
import com.octo.captcha.service.CaptchaServiceException;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Constant;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.framework.utility.UnionLoginUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.inter.ActivityCalculate;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.points.IntegralConstant;
import com.sinosoft.points.PointsCalculate;
import com.sinosoft.schema.MemberIntegralLogSchema;
import com.sinosoft.schema.SDCouponInfoSchema;
import com.sinosoft.schema.SDExpCalendarSchema;
import com.sinosoft.schema.SDExpCalendarSet;
import com.sinosoft.schema.SDIntCalendarSchema;
import com.sinosoft.schema.memberSchema;
import com.sinosoft.schema.memberSet;
import com.sinosoft.sms.messageinterface.process.Md5security;
import com.wangjin.emar.cpa.RegistSuccessInfoCommit;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 简短介绍
 * <p>
 * Date :
 * </p>
 * <p>
 * Module :前台Action类 -会员
 * </p>
 * <p>
 * Description: 描述信息
 * </p>
 * <p>
 * Remark :
 * </p>
 * 
 * @author XXX
 * @version <p>
 *          ------------------------------------------------------------
 *          </p>
 *          <p>
 *          修改历史
 *          </p>
 *          <p> 
 *          序号 日期 修改人 修改原因
 *          </p>
 *          <p>
 *          1 2014-07-08 jiaomengying req20140619001-登录验证码需求
 *          </p>
 */
@ParentPackage("shop")
@Results({
		@Result(name = "memberCenterIndexAction", location = "member_center!index.action", type = "redirect"),
		@Result(name = "index", location = "/wwwroot/kxb/index.shtml", type = "redirect") })
public class MemberAction extends BaseShopAction {

	private static final long serialVersionUID = 1115660086350733102L;
	private String[] love;
	private Member member;
	private BindInfoForLogin bindInfoForLogin;
	private MemberHobby memberHobby;
	private Boolean isAgreeAgreement;
	private String passwordRecoverKey;
	// --------------吴高强添加开始-------------------------//
	private String validateCode;// 注册时的验证码
	private String rePassword;// 确认密码
	private String loginName;// 用户登录字段
	private String em;// 通过参数传递过来的邮箱号
	private String NO;// 通过参数传递过来的手机号
	private String rtype;// 通过参数传递过来的登陆类型
	private String ways;// 通过参数传递 过来的手机号或邮箱
	private String mobileNoOrEmail;// 找回密码时邮箱号或密码
	private String mobileOrEmail;// 邮箱或密码
	private String uname;// 一下四个字段为用户完善页面传过来的值 用户名
	private String mobileNO;// 手机号
	private String email;// 邮箱
	private String qqno;// QQ号
	private String realName;
	private String bandingEmail;
	private String bandingMobile;
	private String mid;
	private String sdcodef;
	private String sdcodes;
	private String mobileNOO;// 手机号
	private String emailo;// 邮箱
	private String EmailBinding;
	private String MobileNOBinding;
	private String registerName;
	private String brithday;
	private String source;// 操作后注册来源
	private String codeType;// 操作后注册传递过来的类型(以区别非车产品/车险一键报价/预约投保)
	private String appName;
	private String orderNo;
	private String mobileNOwgq;
	private String emailwgq;
	private String dgree;
	private String backURL;// 注册页面来源 fhz
	private String emailCom;// 得到邮件服务商网址
	private String frontPage; // 首页地址
	private String reVerifyURL; // 重新验证地址
	private String verifyError; // 验证失败提示信息
	private String loginBackURL;// 登录地址
	private String loginPassword;
	private String tagid;// 登录/注册页面切换标记
	private String productDetailUrl;
	private String productId;
	private String ipRepeat;// 同一IP注册
	private String rmemId;// 推荐人会员ID
	private String points;// 注册送积分的个数
	private String isLcx;

	
	public String getIsLcx() {
		return isLcx;
	}
	public void setIsLcx(String isLcx) {
		this.isLcx = isLcx;
	}

	public String getProductId() {

		return productId;
	}

	public void setProductId(String productId) {

		this.productId = productId;
	}

	public String getProductDetailUrl() {

		return productDetailUrl;
	}

	public void setProductDetailUrl(String productDetailUrl) {

		this.productDetailUrl = productDetailUrl;
	}

	public String getLoginBackURL() {

		return loginBackURL;
	}

	public void setLoginBackURL(String loginBackURL) {

		this.loginBackURL = loginBackURL;
	}

	public String getVerifyError() {

		return verifyError;
	}

	public void setVerifyError(String verifyError) {

		this.verifyError = verifyError;
	}

	public String getReVerifyURL() {

		return reVerifyURL;
	}

	public void setReVerifyURL(String reVerifyURL) {

		this.reVerifyURL = reVerifyURL;
	}

	public String getFrontPage() {

		return frontPage;
	}

	public void setFrontPage(String frontPage) {

		this.frontPage = frontPage;
	}

	public String getEmailCom() {

		return emailCom;
	}

	public void setEmailCom(String emailCom) {

		this.emailCom = emailCom;
	}

	public String getBackURL() {

		return backURL;
	}

	public void setBackURL(String backURL) {

		this.backURL = backURL;
	}

	public String getDgree() {

		return dgree;
	}

	public void setDgree(String dgree) {

		this.dgree = dgree;
	}

	public String getMobileNOwgq() {

		return mobileNOwgq;
	}

	public void setMobileNOwgq(String mobileNOwgq) {

		this.mobileNOwgq = mobileNOwgq;
	}

	public String getEmailwgq() {

		return emailwgq;
	}

	public void setEmailwgq(String emailwgq) {

		this.emailwgq = emailwgq;
	}

	public String getOrderNo() {

		return orderNo;
	}

	public void setOrderNo(String orderNo) {

		this.orderNo = orderNo;
	}

	public String getAppName() {

		return appName;
	}

	public void setAppName(String appName) {

		this.appName = appName;
	}

	public String getSource() {

		return source;
	}

	public void setSource(String source) {

		this.source = source;
	}

	public String getCodeType() {

		return codeType;
	}

	public void setCodeType(String codeType) {

		this.codeType = codeType;
	}

	public String getSerialNO() {

		return serialNO;
	}

	public void setSerialNO(String serialNO) {

		this.serialNO = serialNO;
	}

	public String getIpRepeat() {

		return ipRepeat;
	}

	public void setIpRepeat(String ipRepeat) {

		this.ipRepeat = ipRepeat;
	}

	public String getRmemId() {

		return rmemId;
	}

	public void setRmemId(String rmemId) {

		this.rmemId = rmemId;
	}

	public String getPoints() {

		return points;
	}

	public void setPoints(String points) {

		this.points = points;
	}

	private String serialNO;// 操作后注册传递过来的的字段值
	private List<Dict> listid = new ArrayList<Dict>();
	private List<Dict> listSex = new ArrayList<Dict>();
	private List<Dict> listms = new ArrayList<Dict>();
	private List<Dict> listLove = new ArrayList<Dict>();
	private List<Dict> listHobby = new ArrayList<Dict>();
	private List<Dict> listLocation = new ArrayList<Dict>();

	public List<Dict> getListLove() {

		return listLove;
	}

	public List<Dict> getListLocation() {

		return listLocation;
	}

	public void setListLocation(List<Dict> listLocation) {

		this.listLocation = listLocation;
	}

	public void setListLove(List<Dict> listLove) {

		this.listLove = listLove;
	}

	public List<Dict> getListms() {

		return listms;
	}

	public void setListms(List<Dict> listms) {

		this.listms = listms;
	}

	public List<Dict> getListSex() {

		return listSex;
	}

	public void setListSex(List<Dict> listSex) {

		this.listSex = listSex;
	}

	public List<Dict> getListid() {

		return listid;
	}

	public void setListid(List<Dict> listid) {

		this.listid = listid;
	}

	public String getLoginPassword() {

		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {

		this.loginPassword = loginPassword;
	}

	// --------------吴高强添加结束-------------------------//
	@Resource
	private MemberService memberService;
	@Resource
	private SDOrderService sdorderService;
	@Resource
	private SDtargetInformationService sdTargetInformationService;
	@Resource
	private ShowInsuranceService showInsuranceService;
	@Resource
	private MemberRankService memberRankService;
	@Resource
	private AgreementService agreementService;
	@Resource
	private CaptchaService captchaService;
	@Resource
	private MailService mailService;
	@Resource
	private ProductService productService;
	@Resource
	private CartItemService cartItemService;
	@Resource
	private MemberHobbyService memberHobbyService;

	@Resource
	private GALogService gaLogService;
	@Resource
	private BindInfoForLoginService mBindInfoForLoginService;

	@Resource
	private MemberChannelService memberChannelService;
	@Resource
	private SDShoppingCartService sdShoppingCartService;

	// --------------吴高强添加开始-------------------------//

	private String type;

	public String getRealName() {

		return realName;
	}

	public String getUname() {

		return uname;
	}

	public void setUname(String uname) {

		this.uname = uname;
	}

	public void setRealName(String realName) {

		this.realName = realName;
	}

	public String getMobileNO() {

		return mobileNO;
	}

	public void setMobileNO(String mobileNO) {

		this.mobileNO = mobileNO;
	}

	public String getEmail() {

		return email;
	}

	public void setEmail(String email) {

		this.email = email;
	}

	public String getQqno() {

		return qqno;
	}

	public void setQqno(String qqno) {

		this.qqno = qqno;
	}

	public String getValidateCode() {

		return validateCode;
	}

	public String getRtype() {

		return rtype;
	}

	public void setRtype(String rtype) {

		this.rtype = rtype;
	}

	public String getNO() {

		return NO;
	}

	public void setNO(String NO) {

		this.NO = NO;
	}

	public String getWays() {

		return ways;
	}

	public void setWays(String ways) {

		this.ways = ways;
	}

	public String getEm() {

		return em;
	}

	public void setEm(String em) {

		this.em = em;
	}

	public String getLoginName() {

		return loginName;
	}

	public void setLoginName(String loginName) {

		this.loginName = loginName;
	}

	public void setValidateCode(String validateCode) {

		this.validateCode = validateCode;
	}

	public String getRePassword() {

		return rePassword;
	}

	public void setRePassword(String rePassword) {

		this.rePassword = rePassword;
	}

	public String getMobileNoOrEmail() {

		return mobileNoOrEmail;
	}

	public void setMobileNoOrEmail(String mobileNoOrEmail) {

		this.mobileNoOrEmail = mobileNoOrEmail;
	}

	public String getMobileOrEmail() {

		return mobileOrEmail;
	}

	public void setMobileOrEmail(String mobileOrEmail) {

		this.mobileOrEmail = mobileOrEmail;
	}

	public String checkLogin() {

		Member lm = null;
		lm = getLoginMember();
		HttpServletRequest request = ServletActionContext.getRequest();
		String jsonpname = request.getParameter("callback");
		Map<String, Object> jsonMap = new HashMap<String, Object>();

		if (lm != null) {
			jsonMap.put("status", "0");
			jsonMap.put("MemberUsername", getSession("loginName"));
			jsonMap.put("loginMemberId", lm.getId());
			jsonMap.put("MemberPoints", String.valueOf(lm.getCurrentValidatePoint()));
			jsonMap.put("grade", lm.getGrade());
			String picpath = "";
			if (StringUtil.isEmpty(lm.getHeadPicPath())) {
				picpath = Config.getValue("FrontServerContextPath") + "/images/redesign/photo_06.gif";
			} else {
				picpath = Config.getValue("StaticResourcePath") + "/" + lm.getHeadPicPath();
			}
			jsonMap.put("headPicPath", picpath);

			MemberCenterAction mem = new MemberCenterAction();
			Map<String, String> gradeMap = mem.getGradeIcon(lm.getVipFlag(),
					lm.getGrade(), "", lm.getBirthday(), lm.getBirthYear());
			if (StringUtil.isNotEmpty(gradeMap.get("gradeClass"))) {
				jsonMap.put("gradeClass", gradeMap.get("gradeClass").replace("vip_k ", "vip_k_top vip_top "));
			}
			int tCount = 0;
			// 优惠卷
			StringBuffer sb = new StringBuffer(
					" select count(1) from CouponInfo  where status='2' and memberId = ? and DATE_FORMAT(endTime,'%Y-%m-%d %H:%i:%s') > DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%s')");
			tCount = new QueryBuilder(sb.toString(), lm.getId()).executeInt();
			jsonMap.put("CouponCount", String.valueOf(tCount));
			// 待支付订单数量 
			sb = new StringBuffer(
					"SELECT COUNT(DISTINCT COALESCE(paysn, id)) FROM sdorders WHERE memberid=? AND orderstatus=? AND channelsn != 'jfsc' AND channelsn not in (SELECT cb.channelcode FROM channelinfo ca, channelinfo cb  WHERE ca.channelcode = 'jfsc' AND ca.InnerChannelCode = cb.ParentInnerChanelCode) AND ( dellFlag IS NULL OR dellFlag = '')");
			tCount = new QueryBuilder(sb.toString(), lm.getId(), "5").executeInt();
			jsonMap.put("PrePay", String.valueOf(tCount));
			// 带生效
			sb = new StringBuffer(
					"SELECT COUNT(DISTINCT COALESCE(paysn, b.id)) FROM sdorders a,sdinformation b WHERE a.ordersn = b.ordersn AND a.memberid=? AND a.orderstatus IN ('7','10','12','14') AND DATE_FORMAT(b.startDate,'%Y-%m-%d %H:%i:%s')>DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%s') AND channelsn != 'jfsc' AND channelsn not in (SELECT cb.channelcode FROM channelinfo ca, channelinfo cb  WHERE ca.channelcode = 'jfsc' AND ca.InnerChannelCode = cb.ParentInnerChanelCode) AND ( dellFlag IS NULL OR dellFlag = '')");
			tCount = new QueryBuilder(sb.toString(), lm.getId()).executeInt();
			jsonMap.put("Payed", String.valueOf(tCount));
			// 已生效
			sb = new StringBuffer(
					"SELECT COUNT(DISTINCT COALESCE(paysn, b.id)) FROM sdorders a,sdinformation b WHERE a.ordersn = b.ordersn AND a.memberid=? AND a.orderstatus IN ('7','10','12','14')  AND DATE_FORMAT(b.startDate,'%Y-%m-%d %H:%i:%s')<=DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s') AND DATE_FORMAT(b.endDate,'%Y-%m-%d %H:%i:%s')>=DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s') AND channelsn != 'jfsc' AND channelsn not in (SELECT cb.channelcode FROM channelinfo ca, channelinfo cb  WHERE ca.channelcode = 'jfsc' AND ca.InnerChannelCode = cb.ParentInnerChanelCode) AND ( dellFlag IS NULL OR dellFlag = '')");
			tCount = new QueryBuilder(sb.toString(), lm.getId()).executeInt();
			jsonMap.put("Effective", String.valueOf(tCount));
			// 购物车信息
			List<SDShoppingCart> cartList = sdShoppingCartService.getShowShopCartList(lm.getId());
			// 会员升级信息
			sb = new StringBuffer(
					"SELECT b.gradecode,b.sumprem-if (a.consumeamount is null, 0, a.consumeamount),CAST(round(b.ordercount-if (a.buycount is null, 0, a.buycount) ,0) AS CHAR) FROM member a LEFT JOIN membergrade b ON a.grade = b.pregradecode WHERE a.id=? ");
			DataTable dt = new QueryBuilder(sb.toString(), lm.getId()).executeDataTable();
			if (dt != null && dt.getRowCount() >= 1 && StringUtil.isNotEmpty(dt.getString(0, 0))) {
				// jsonMap.put("gradeinfo", "消费"+dt.getString(0,
				// 2)+"次满"+dt.getString(0, 1)+"元升级"+dt.getString(0, 0)+"会员");
				jsonMap.put("gradeinfo", gradeMap.get("gradeInfo"));
			} else if ("Y".equals(lm.getVipFlag())) {
				jsonMap.put("gradeinfo", gradeMap.get("gradeInfo"));
			} else {
				jsonMap.put("gradeinfo", "申请VIP！享受更多特权！");
			}
			sb = new StringBuffer("");
			BigDecimal amount = new BigDecimal("0");
			List<SDOrder> paramterList = new ArrayList<SDOrder>();
			for (SDShoppingCart t : cartList) {
				SDOrder order = sdorderService.getOrderByOrderSn(t.getOrderSn());
				paramterList.add(order);
			}

			if (paramterList.size() > 0) {
				sb = new StringBuffer("");
				// 筛选优惠活动
				Map<String, Map<String, Object>> activity_product_info1 = ActivityCalculate.ProductShoppingCartInfo(
						paramterList, paramterList.get(0).getChannelsn(), true);
				// 总金额
				BigDecimal totalamount = new BigDecimal("0");
				for (int i = 0; i < cartList.size(); i++) {
					SDShoppingCart sdshoppingcart = cartList.get(i);
					// 遍历优惠信息Map
					Set keySet = activity_product_info1.keySet();
					for (Iterator it = keySet.iterator(); it.hasNext();) {
						// 活动号（包含“_no_activity”）
						String activitysn = String.valueOf(it.next());
						// 获取该活动关联的所有信息（包括活动信息，产品信息，优惠信息）
						Map<String, Object> map_info = activity_product_info1.get(activitysn);
						// 获取产品信息list
						List<Map<String, String>> list_product = (List<Map<String, String>>) map_info.get("ProductInfo");
						Map<String, String> map_activityinfo = (Map<String, String>) map_info.get("ActivityInfo");
						Map<String, String> map_activityamont = (Map<String, String>) map_info.get("ActivityAmont");
						String type = map_activityinfo.get("type");
						if (i == 0) {
							// 总金额
							if ("6".equals(type)) {
								String TotalAmount = map_activityamont.get("RealAmount");
								totalamount = totalamount.add(new BigDecimal(TotalAmount));
							} else {
								String TotalAmount = map_activityamont.get("TotalAmount");
								totalamount = totalamount.add(new BigDecimal(TotalAmount));
							}
						}
						for (int j = 0; j < list_product.size(); j++) {
							// 活动每个产品对应的Map
							Map<String, String> map_product = list_product.get(j);
							if (sdshoppingcart.getOrderSn().equals(map_product.get("OrderSn"))) {
								sb.append("<li><a href='" + sdshoppingcart.getDetailPath() + "'>"
										+ sdshoppingcart.getProductName() + "</a><em><i>￥"
										+ map_product.get("ActivityeAmount") + "</i>×1</em></li>");
							}

						}
					}
				}
				amount = totalamount;
			}

			jsonMap.put("carlist", sb.toString());
			jsonMap.put("shoptotal", "￥" + amount.toString());
			jsonMap.put("shopcount", cartList.size());
		} else {
			jsonMap.put("status", "1");
		}
		return ajaxHtml(jsonpname + "("
				+ JSONObject.fromObject(jsonMap).toString() + ");");
	}

	public String getMemLeftMenuNum() {
		Member mem = getLoginMember();
		Map<String, String> jsonMap = new HashMap<String, String>();
		if (mem == null) {
			jsonMap.put("status", "N");
			jsonMap.put("message", "请登录！");
			return ajaxJson(jsonMap);
		}
		
		int tCount = 0;
		String memberId = mem.getId(); 
		// 优惠卷
		StringBuffer sb = new StringBuffer(
				" select count(1) from CouponInfo  where status='2' and memberId = ? and DATE_FORMAT(endTime,'%Y-%m-%d %H:%i:%s') > DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%s')");
		tCount = new QueryBuilder(sb.toString(), memberId).executeInt();
		if (tCount > 0) {
			jsonMap.put("CouponCount", String.valueOf(tCount));
		}
		
		//查询用户是否购买理财险
		String sql = "SELECT COUNT(1) FROM sdinformation WHERE sdorder_id IN (SELECT id FROM sdorders WHERE memberid = ?) AND FIND_IN_SET(productid, (SELECT VALUE FROM zdconfig WHERE TYPE = 'LicaiBaoxianProducts'))";
		tCount = new QueryBuilder(sql, memberId).executeInt();
		if (tCount > 0) {
			jsonMap.put("isLcx", "Y");
		}else{
			jsonMap.put("isLcx", "N");
		}
		
		
		// 所有订单数
		sb = new StringBuffer("select COUNT(DISTINCT COALESCE(a.paysn, a.id)) from sdorders a,sdinformation b where a.ordersn=b.ordersn and  a.memberId=? AND a.channelsn != 'jfsc' AND a.channelsn not in (SELECT cb.channelcode FROM channelinfo ca, channelinfo cb  WHERE ca.channelcode = 'jfsc' AND ca.InnerChannelCode = cb.ParentInnerChanelCode) AND ( a.dellFlag IS NULL OR dellFlag = '')");
		tCount = new QueryBuilder(sb.toString(), memberId).executeInt();
		if (tCount > 0) {
			jsonMap.put("AllOrder", String.valueOf(tCount));
		}
		
		// 待支付订单数量
		sb = new StringBuffer(
				"SELECT COUNT(DISTINCT COALESCE(a.paysn, a.id)) FROM sdorders a,sdinformation b WHERE a.ordersn=b.ordersn and a.memberid=? AND a.orderstatus=? AND a.channelsn != 'jfsc' AND a.channelsn not in (SELECT cb.channelcode FROM channelinfo ca, channelinfo cb  WHERE ca.channelcode = 'jfsc' AND ca.InnerChannelCode = cb.ParentInnerChanelCode) AND ( a.dellFlag IS NULL OR a.dellFlag = '')");
		tCount = new QueryBuilder(sb.toString(), memberId, "5").executeInt();
		if (tCount > 0) {
			jsonMap.put("PrePay", String.valueOf(tCount));
		}
		
		// 待生效订单数
		sb = new StringBuffer(
				"SELECT COUNT(DISTINCT COALESCE(paysn, b.id)) FROM sdorders a,sdinformation b WHERE a.ordersn = b.ordersn AND  a.memberid=? AND a.orderstatus IN ('7','10','12','14') AND DATE_FORMAT(b.startDate,'%Y-%m-%d %H:%i:%s')>DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%s') AND channelsn != 'jfsc' AND channelsn not in (SELECT cb.channelcode FROM channelinfo ca, channelinfo cb  WHERE ca.channelcode = 'jfsc' AND ca.InnerChannelCode = cb.ParentInnerChanelCode) AND ( dellFlag IS NULL OR dellFlag = '')");
		tCount = new QueryBuilder(sb.toString(), memberId).executeInt();
		if (tCount > 0) {
			jsonMap.put("Payed", String.valueOf(tCount));
		}
		
		// 已生效订单数
		sb = new StringBuffer(
				"SELECT COUNT(DISTINCT COALESCE(paysn, b.id)) FROM sdorders a,sdinformation b WHERE a.ordersn = b.ordersn AND a.memberid=? AND a.orderstatus IN ('7','10','12','14')  AND DATE_FORMAT(b.startDate,'%Y-%m-%d %H:%i:%s')<=DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s') AND DATE_FORMAT(b.endDate,'%Y-%m-%d %H:%i:%s')>=DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s') AND channelsn != 'jfsc' AND channelsn not in (SELECT cb.channelcode FROM channelinfo ca, channelinfo cb  WHERE ca.channelcode = 'jfsc' AND ca.InnerChannelCode = cb.ParentInnerChanelCode) AND ( dellFlag IS NULL OR dellFlag = '')");
		tCount = new QueryBuilder(sb.toString(), memberId).executeInt();
		if (tCount > 0) {
			jsonMap.put("Effective", String.valueOf(tCount));
		}
		
		// 已过期订单数
		sb = new StringBuffer(
				"SELECT COUNT(DISTINCT COALESCE(paysn, b.id)) FROM sdorders a,sdinformation b WHERE a.ordersn = b.ordersn AND a.memberid=? AND a.orderstatus IN ('7','10','12','14') AND DATE_FORMAT(b.endDate,'%Y-%m-%d %H:%i:%s')<DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%s') AND channelsn != 'jfsc' AND channelsn not in (SELECT cb.channelcode FROM channelinfo ca, channelinfo cb  WHERE ca.channelcode = 'jfsc' AND ca.InnerChannelCode = cb.ParentInnerChanelCode) AND ( dellFlag IS NULL OR dellFlag = '')");
		tCount = new QueryBuilder(sb.toString(), memberId).executeInt();
		if (tCount > 0) {
			jsonMap.put("OutEffective", String.valueOf(tCount));
		}
		
		// 待评价订单数
		sb = new StringBuffer(
				"SELECT COUNT(1) FROM sdorders WHERE memberid=? AND orderstatus IN ('7','10','12','14') AND commentId IS NULL AND channelsn != 'jfsc' AND channelsn not in (SELECT cb.channelcode FROM channelinfo ca, channelinfo cb  WHERE ca.channelcode = 'jfsc' AND ca.InnerChannelCode = cb.ParentInnerChanelCode) AND ( dellFlag IS NULL OR dellFlag = '')");
		tCount = new QueryBuilder(sb.toString(), memberId).executeInt();
		if (tCount > 0) {
			jsonMap.put("CommentNum", String.valueOf(tCount));
		}
		// 机票订单数
		sb = new StringBuffer(
				"SELECT COUNT(1) FROM otorderinfo WHERE memberid=? ");
		tCount = new QueryBuilder(sb.toString(), memberId).executeInt();
		if (tCount > 0) {
			jsonMap.put("TripOrderNum", String.valueOf(tCount));
		}
		
		// 我的可用积分
		jsonMap.put("currentValidatePoint", String.valueOf(mem.getCurrentValidatePoint()));
		// 我的收藏
		sb = new StringBuffer(
				"SELECT COUNT(1) FROM ProductCollection WHERE memberid=? ");
		tCount = new QueryBuilder(sb.toString(), memberId).executeInt();
		if (tCount > 0) {
			jsonMap.put("collectionNum", String.valueOf(tCount));
		}
		// 对比记录
		sb = new StringBuffer(
				"SELECT COUNT(1) FROM mytrailnonauto WHERE memeberId=? ");
		tCount = new QueryBuilder(sb.toString(), memberId).executeInt();
		if (tCount > 0) {
			jsonMap.put("compareNum", String.valueOf(tCount));
		}
		// 基本信息完成度
		jsonMap.put("fullDegree", mem.getFullDegree());
		jsonMap.put("status", "Y");
		
		//缺口保障存档条数
		String timesql = "select createddatetime from gapguaranteearchive where memberid = ? ORDER BY  createddatetime desc limit 3";
		DataTable dtable = new QueryBuilder(timesql, memberId).executeDataTable();
		if (dtable.getRowCount() > 0) {
			for (int i = 0; i < dtable.getRowCount(); i++) {
				jsonMap.put("datetime" + (i + 1), dtable.getString(i, "createddatetime").substring(0, 10));
			}
		}
		jsonMap.put("count", String.valueOf(dtable.getRowCount()));
		
		return ajaxJson(jsonMap);
	}
	
	/**
	 * 
	 * @return 校验邮箱号是否已被注册
	 */
	public String checkEmail() {

//		try {
//			String email = getParameter("em");
//			if (StringUtil.isMail(email)) {
//				if (memberService.isExistByMailbox(email)) {
//					return ajaxJson("false");// 邮箱被注册
//				} else {
//					return ajaxJson("true");
//				}
//			} else if (StringUtil.isMobileNO(email)) {
//				if (memberService.isExistByMobileNO(email)) {
//					return ajaxJson("false");// 手机号被注册
//				} else {
//					return ajaxJson("true");
//				}
//			} else {
//				return ajaxJson("false");
//			}
//		} catch (Exception e) {
//			return ajaxJson("false");
//		}
		return "";
	}

	/**
	 * 查询登录次数是否超过5次
	 * @param loginName
	 * @return 超过5次：false 小于5次：true
	 */
	public boolean queryLoginSuccRemark(String loginName) {
	
		try {
			// 查询redis中用户登录的次数
			String key = "WJ-SuccLogin-"+loginName;
			String info = JedisCommonUtil.getString(3, key);
			String date = DateUtil.getCurrentDate("yyyyMMdd");
			if (StringUtil.isNotEmpty(info)) {
				String[] remarks = info.split("&");
				// 是今天登陆的记录，则判断登陆次数是否超过5次
				if (date.equals(remarks[0]) && Integer.valueOf(remarks[1]) > 5) {
					return false;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return true;
	}
	
	/**
	 * 
	 * @return 校验客户填入的用户名是否已经被注册
	 * @author fhz
	 */
	public String checkRegisterName() {
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put("remarkFlag", "false");
	
		String regName = getParameter("regName");
		jsonMap.put("status", "true");
		if (!queryLoginSuccRemark(regName) || !queryLoginReqCountRemark(2)) {
			jsonMap.put("remarkFlag", "true");
		}
		return ajaxJson(jsonMap);
	}

	/**
	 * 
	 * @return 前台会员中心验证邮箱方法 不同会员可使用相同的邮箱接收开心保发送的邮件
	 * @author fhz
	 */
	public String checkVerifyEmail() {

		try {
			String email = getParameter("em");// 用户输入的邮箱
			String myEm = getParameter("myEm");// 用户当前的邮箱
			if (StringUtil.isMail(email)) {
				if (myEm != null && myEm.equals(email)) {// 用户验证注册邮箱
					return ajaxJson("true");
				}
				// 如果此邮箱不是该用户注册时的邮箱，要验证系统内是否有此邮箱
				else {
					if (memberService.isExistByMailbox(email)) {
						return ajaxJson("false");// 邮箱被注册
					} else {
						return ajaxJson("true");
					}
				}
			} else {
				return ajaxJson("false");
			}
		} catch (Exception e) {
			return ajaxJson("false");
		}
	}

	/**
	 * 
	 * @return 前台会员中心手机号码
	 * @author fhz
	 */
	public String checkVerifyMobileNO() {

		try {
			String mobileNO = getParameter("mobileNO");// 用户输入的手机号
			String myMoblieNO = getParameter("myMoblieNO");// 用户当前的手机
			if (StringUtil.isMobileNO(mobileNO)) {
				if (myMoblieNO != null && myMoblieNO.equals(mobileNO)) {// 用户验证注册手机
					return ajaxJson("true");
				} else { // 如果此手机不是该用户注册时的手机，要验证系统内是否有此手机
					if (memberService.isExistByMobileNO(mobileNO)) {
						return ajaxJson("false");// 手机被注册
					} else {
						return ajaxJson("true");
					}
				}
			} else {
				return ajaxJson("false");
			}
		} catch (Exception e) {
			return ajaxJson("false");
		}
	}


	/**
	 * 
	 * @return 用户名是否已被注册
	 */
	public String checkUserName() {

		try {
			String userName = getParameter("em");
			// 2012/12/10 bug:389 yushanchun add start
			String name = getParameter("en");
			if (userName.equals(name)) {
				return ajaxJson("true");
			}
			// 2012/12/10 bug:389 yushanchun add end
			else if (memberService.isExistByUserName(userName)) {
				return ajaxJson("false");// 用户名已被注册
			} else {
				return ajaxJson("true");
			}
		} catch (Exception e) {
			return ajaxJson("false");// 用户名已被注册
		}
	}

	/**
	 * 
	 * @return 校验手机号是否已被注册
	 */
	public String checkMobileNO() {

		try {
			String NOq = getParameter("NO");
			if (memberService.isExistByMobileNO(NOq)) {
				return ajaxJson("false");// 手机号被注册
			} else {
				return ajaxJson("true");
			}
		} catch (Exception e) {
			return ajaxJson("false");// 手机号被注册
		}
	}

	/**
	 * 
	 * @return 校验验证码是否填写正确
	 * @author fhz banji
	 */
	public String checkCaptcha() {

		try {
			String captchaID = getRequest().getSession().getId();
			String challengeResponse = StringUtils.upperCase(getRequest()
					.getParameter(JCaptchaEngine.CAPTCHA_INPUT_NAME));
			if (StringUtils.isEmpty(challengeResponse)
					|| captchaService.validateResponseForID(captchaID,
							challengeResponse) == false) {
				return ajaxJson("false");
			}
		} catch (Exception e) {
			return ajaxJson("false");
		}
		return ajaxJson("true");
	}

	/**
	 * 
	 * @return 生成四位验证码
	 */
	public String VCFactory() {

		int length = 6;
		char[] arr = "0123456789abcdefghijklmnopqrstuvwxyz".toCharArray();
		Random rand = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append(arr[rand.nextInt(arr.length)]);
		}
		return sb.toString();
	}

	/**
	 * 发送验证码到邮箱
	 * 
	 * @param svcode验证码
	 * @param way邮箱号
	 * @return 是否发送成功
	 */

	public boolean SVCToEmail(String svcode, String way, String isModify) {

		boolean flag = false;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Captcha", svcode);
		if ("N".equals(isModify)) {
			if (ActionUtil.dealAction("wj0001", map, getRequest())) {// 注册发送验证吗
				// session Hold only the latest verification code
				if (getSession("sendValidateCode") != null) {
					getSession().remove("sendValidateCode");
					getSession().remove("sendTime");
					setSession("sendTime", System.currentTimeMillis());
					setSession("sendValidateCode", svcode);
				} else {
					setSession("sendTime", System.currentTimeMillis());
					setSession("sendValidateCode", svcode);
				}
				flag = true;
			} else {
				logger.error("发送验证码失败");
			}
		} else {
			map.put("MemberRealName", memberService.getNickNameForMail(null));
			map.put(MessageConstant.PARAM_SHOW_ACTIVITY_PRODUCT, "1");
			if (ActionUtil.sendMail("wj0007",way, map)) {// 修改密码发送验证吗
				// session Hold only the latest verification code
				if (getSession("sendValidateCode") != null) {
					getSession().remove("sendValidateCode");
					getSession().remove("sendTime");
					setSession("sendTime", System.currentTimeMillis());
					setSession("sendValidateCode", svcode);
				} else {
					setSession("sendTime", System.currentTimeMillis());
					setSession("sendValidateCode", svcode);
				}
				flag = true;
			} else {
				logger.error("发送验证码失败");
			}

		}
		return flag;
	}

	/**
	 * 发送验证码到手机
	 * 
	 * @param svcode
	 *            验证码
	 * @param way
	 *            手机号
	 * @return 是否发送成功
	 */

	public boolean SVCToPhone(String svcode, String way, String isModify) {

		boolean flag = false;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ToMobileNO", way);
		map.put("ToName", "");
		map.put("Captcha", svcode);
		if ("N".equals(isModify)) {
			if (ActionUtil.dealAction("wj0002", map, getRequest())) {// 注册发送到手机
				if (getSession("sendValidateCode") != null) {
					getSession().remove("sendValidateCode");
					getSession().remove("sendTime");
					setSession("sendTime", System.currentTimeMillis());
					setSession("sendValidateCode", svcode);
				} else {
					setSession("sendTime", System.currentTimeMillis());
					setSession("sendValidateCode", svcode);
				}
				flag = true;
			} else {
				logger.error("发送验证码失败");
			}
		} else {
			if (ActionUtil.sendSms("wj00010", way, svcode)) {// 修改密码发送到手机
				if (getSession("sendValidateCode") != null) {
					getSession().remove("sendValidateCode");
					getSession().remove("sendTime");
					getSession().remove("validateCodeAndMobile");
					setSession("sendTime", System.currentTimeMillis());
					setSession("sendValidateCode", svcode);
					setSession("validateCodeAndMobile", way + "_" + svcode);
				} else {
					setSession("sendTime", System.currentTimeMillis());
					setSession("sendValidateCode", svcode);
					setSession("validateCodeAndMobile", way + "_" + svcode);
				}
				flag = true;
			} else {
				logger.error("发送验证码失败");
			}
		}
		return flag;
	}

	public String validatePhones() {
		String svcode = VCFactory();
		String way = getParameter("ways");
		boolean flag = false;
		Member sMember = getLoginMember();
		String toName = "";
		if (sMember != null) {
			if (sMember.getRealName() != null
					&& !"".equals(sMember.getRealName())) {
				toName = sMember.getRealName();
			} else if (sMember.getUsername() != null
					&& !"".equals(sMember.getUsername())) {
				toName = sMember.getUsername();
			} 
		}
		if (ActionUtil.sendSms("wj0008", way, toName + ";" + svcode)) {// 手机绑定
			if (getSession("sendValidateCode") != null) {
				getSession().remove("sendValidateCode");
				getSession().remove("sendTime");
				setSession("sendTime", System.currentTimeMillis());
				setSession("sendValidateCode", svcode);
			} else {
				setSession("sendTimem", System.currentTimeMillis());
				setSession("sendValidateCode", svcode);
			}
			this.bandingMobile = "Y";
			flag = true;
		}
		if (flag) {
			return ajaxJson("true");
		} else {
			return ajaxJson("false");
		}
	}
	
	/**
	 * 绑定手机或修改绑定手机
	 * 
	 * @return
	 */
	public String SVCToPhones() {

		try {
			String captchaID = getRequest().getSession().getId();
			String challengeResponse = StringUtils.upperCase(getRequest().getParameter("inputTPYZM"));
			if (StringUtils.isEmpty(challengeResponse)
					|| captchaService.validateResponseForID(captchaID, challengeResponse) == false) {
				return ajaxText("yzmerror");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ajaxText("yzmerror");
		}
		String svcode = VCFactory();
		String way = getParameter("ways");
		Member sMember = getLoginMember();
		// 是否被注册
		if (!way.equals(sMember.getMobileNO()) && memberService.isExistByMobileNO(way)) {
			return ajaxText("registered");
		}
		
		boolean flag = false;
		String toName = "";
		if (sMember != null) {
			if (sMember.getRealName() != null
					&& !"".equals(sMember.getRealName())) {
				toName = sMember.getRealName();
			} else if (sMember.getUsername() != null
					&& !"".equals(sMember.getUsername())) {
				toName = sMember.getUsername();
			}
		} 
		if (sMember.getIsMobileNOBinding().equals("Y")) {
			if (ActionUtil.sendSms("wj0006", way, toName + ";" + svcode)) {// 修改手机绑定
				if (getSession("sendValidateCode") != null) {
					getSession().remove("sendValidateCode");
					getSession().remove("sendTime");
					setSession("sendTime", System.currentTimeMillis());
					setSession("sendValidateCode", svcode);
				} else {
					setSession("sendTime", System.currentTimeMillis());
					setSession("sendValidateCode", svcode);
				}
				this.bandingMobile = "Y";
				flag = true;
			}
		} else {
			if (ActionUtil.sendSms("wj0008", way, toName + ";" + svcode)) {// 手机绑定
				if (getSession("sendValidateCode") != null) {
					getSession().remove("sendValidateCode");
					getSession().remove("sendTime");
					setSession("sendTime", System.currentTimeMillis());
					setSession("sendValidateCode", svcode);
				} else {
					setSession("sendTimem", System.currentTimeMillis());
					setSession("sendValidateCode", svcode);
				}
				this.bandingMobile = "Y";
				flag = true;
			}

		}

		if (flag) {
			return ajaxJson("true");
		} else {
			return ajaxJson("false");
		}
	}

	/**
	 * 手机找回密码，验证码重新发送
	 * 
	 * @author fhz
	 * @return
	 */
	public String reSendSVCToPhone() {

		try {
			Member retriveMember;// 找回密码的member
			if (mobileNoOrEmail == null || "".equals(mobileNoOrEmail)) {
				return ajaxJson("手机号为空，发送失败！");
			}
			Pattern pattern1 = Pattern.compile("^\\d{11}$");
			Matcher matcher1 = pattern1.matcher(mobileNoOrEmail.trim());
			if (matcher1.matches()) {
				Pattern pattern = Pattern
						.compile("^(13[0-9]{9}|15[5-9][0-9]{8}|15[0-3][0-9]{8}|18[0-9][0-9]{8}|147[0-9]{8}|17[6-8][0-9]{8})$");
				Matcher matcher = pattern.matcher(mobileNoOrEmail.trim());
				if (!matcher.matches()) {
					return ajaxJson("手机号格式错误，发送失败！");
				}
			}
			retriveMember = memberService
					.getMemberByLoginNameNoBinding(mobileNoOrEmail);
			if (retriveMember == null) {
				return ajaxJson("系统中不存在此用户，发送失败！");
			}

			// 开始重新发送短信
			String svcode = "";
			// 如果session中有验证码 那么重新发送时要发送未验证过的验证吗
			if (getSession("sendValidateCode") != null
					&& !"".equals(getSession("sendValidateCode"))) {
				svcode = (String) getSession("sendValidateCode");
			} else {
				svcode = VCFactory();
			}
			if (ActionUtil.sendSms("wj00010", mobileNoOrEmail, svcode)) {// 修改密码发送到手机
				if (getSession("sendValidateCode") != null) {
					getSession().remove("sendTime");
					setSession("sendTime", System.currentTimeMillis());
				} else {
					setSession("sendTime", System.currentTimeMillis());
					setSession("sendValidateCode", svcode);
				}
			} else {
				logger.error("发送验证码失败");
				return ajaxJson("发送失败,请联系在线客服或打热线电话4009-789-789！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ajaxJson("发送失败,请联系在线客服或打热线电话4009-789-789！");
		}

		return ajaxJson("true");

	}

	/**
	 * 绑定邮箱或修改绑定邮箱
	 * 
	 * @return
	 */
	public String SVCToEmails() {
		
		String svcode = VCFactory();
		boolean flag = false;
		Map<String, Object> map = new HashMap<String, Object>();
		Member sMember = getLoginMember();
		map.put("MemberRealName", memberService.getNickNameForMail(sMember));
		map.put("Captcha", svcode);
		map.put(MessageConstant.PARAM_SHOW_ACTIVITY_PRODUCT, "1");
		if (sMember.getIsEmailBinding().equals("Y")) {
			if (ActionUtil.sendMail("wj0005", ways, map)) {// 修改邮箱绑定
				if (getSession("sendValidateCode") != null) {
					getSession().remove("sendValidateCode");
					getSession().remove("sendTime");
					setSession("sendTime", System.currentTimeMillis());
					setSession("sendValidateCode", svcode);
				} else {
					setSession("sendTime", System.currentTimeMillis());
					setSession("sendValidateCode", svcode);
				}
				this.bandingEmail = "Y";
				flag = true;
			}
		} else {
			if (ActionUtil.sendMail("wj0007", ways, map)) {// 邮箱绑定
				if (getSession("sendValidateCode") != null) {
					getSession().remove("sendValidateCode");
					getSession().remove("sendTime");
					setSession("sendTime", System.currentTimeMillis());
					setSession("sendValidateCode", svcode);
				} else {
					setSession("sendTimee", System.currentTimeMillis());
					setSession("sendValidateCode", svcode);
				}
				this.bandingEmail = "Y";
				flag = true;
			}
		}
		if (flag) {
			return ajaxJson("true");
		} else {
			return ajaxJson("false");
		}

	}

	/**
	 * 
	 * 发送验证码到手机或邮箱
	 */
	public String sendCode() {

		String svcode = VCFactory();
		logger.info("注册验证码：{}", svcode);

		setSession("sendValidateCode", svcode);
		String rgtype = getParameter("rtype");
		String way = getParameter("ways");// 邮箱号或手机号
		if (StringUtil.isMail(way) && !"0".equals(rgtype)) {
			return ajaxText("邮件校验失败");

		} else if (StringUtil.isMobileNO(way) && !"1".equals(rgtype)) {
			return ajaxText("手机号校验失败");
		}
		java.text.DateFormat format1 = new java.text.SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss");
		String s = format1.format(new Date());
		setSession("ValidateTime", s);
		if ("0".equals(rgtype)) {
			if (SVCToEmail(svcode, way, "N")) {
				return ajaxText("发送成功");
			}
		}
		if ("1".equals(rgtype)) {
			if (StringUtil
					.isEmpty((String) getSession("sendValidateCode_count"))) {
				setSession("sendValidateCode_count", "1");

			} else {
				int sendValidateCode_count = Integer
						.parseInt((String) getSession("sendValidateCode_count"));
				if (sendValidateCode_count >= 5) {
					return ajaxText("发送次数太频繁，请稍候再试！");
				}
				setSession("sendValidateCode_count",
						(sendValidateCode_count + 1) + "");
			}

			if (SVCToPhone(svcode, way, "N")) {
				return ajaxText("发送成功");
			}
		}
		return ajaxText("发送失败");
	}

	/**
	 * 
	 * 会员登录
	 * 
	 * @throws Exception
	 */
	@Validations(requiredStrings = { @RequiredStringValidator(fieldName = "member.password", message = "密码不允许为空!") })
	@InputConfig(resultName = "error")
	@SuppressWarnings("unchecked")
	public String login() throws Exception {

		try {
			String captchaID = getRequest().getSession().getId();
			String challengeResponse = StringUtils.upperCase(getRequest()
					.getParameter(JCaptchaEngine.CAPTCHA_INPUT_NAME));
			if (StringUtils.isEmpty(challengeResponse)
					|| captchaService.validateResponseForID(captchaID,
							challengeResponse) == false) {
				addActionError("验证码输入错误!");
				return ERROR;
			}
		} catch (Exception e) {
			addActionError("验证码输入错误!");
			return ERROR;
		}
		SystemConfig systemConfig = getSystemConfig();
		Member loginMember = memberService.getMemberByLoginName(loginName);
		if (loginMember != null) {
			// 解除会员账户锁定
			if ("Y".equalsIgnoreCase(loginMember.getIsAccountLocked())) {
				Date lockedDate = loginMember.getLockedDate();
				Date now = new Date();
				Calendar c1 = Calendar.getInstance();
				c1.setTime(lockedDate);
				Calendar c2 = Calendar.getInstance();
				c2.setTime(now);
				if (c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR) >= 1) {
					loginMember.setLoginFailureCount(0);
					loginMember.setIsAccountLocked("N");
					loginMember.setLockedDate(null);
					memberService.update(loginMember);
				}
			}
			if ("N".equalsIgnoreCase(loginMember.getIsAccountEnabled())) {
				addActionError("您的账号已被禁用,无法登录!");
				return ERROR;

			}
			if ("Y".equalsIgnoreCase(loginMember.getIsAccountLocked())) {
				addActionError("您的账号已被锁定,无法登录!");
				return ERROR;
			}
			if (!memberService.verifyMember(loginName, member.getPassword())) {
				Date lockedDate = loginMember.getLockedDate();
				if (lockedDate == null) {
					lockedDate = new Date();
				}
				Date now = new Date();
				Calendar c1 = Calendar.getInstance();
				c1.setTime(lockedDate);
				Calendar c2 = Calendar.getInstance();
				c2.setTime(now);
				if (c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR) >= 1) {
					loginMember.setLoginFailureCount(0);
					loginMember.setIsAccountLocked("N");
					loginMember.setLockedDate(null);
					memberService.update(loginMember);
				}
				int loginFailureCount = loginMember.getLoginFailureCount() + 1;
				if (loginFailureCount == 5) {
					loginMember.setIsAccountLocked("Y");
					loginMember.setLockedDate(now);// 最后一次密码错误时间
					loginMember.setLoginFailureCount(loginFailureCount);
					memberService.update(loginMember);
					addActionError("您的账户在一天之内已有5次密码输入错误,您的账号已被锁定! 您可以通过忘记密码的方式来解锁并重设密码!");
					return ERROR;
				}

				if (loginFailureCount < 5) {
					int remained = 5 - loginFailureCount;
					loginMember.setLockedDate(now);// 最后一次密码错误时间
					loginMember.setLoginFailureCount(loginFailureCount);
					memberService.update(loginMember);
					addActionError("您今天还有" + remained + "次密码错误机会!");
					return ERROR;
				}
				return ERROR;
			}
		} else {
			addActionError("用户名或密码错误!");
			return ERROR;
		}
		loginMember.setLoginFailureCount(0);
		loginMember.setLoginIp(getRequest().getRemoteAddr());
		loginMember.setLoginDate(new Date());
		memberService.update(loginMember);
		if (loginMember.getUsername() != null
				&& !"".equals(loginMember.getUsername())) {
			loginName = loginMember.getUsername();
		}
		// 写入会员登录Session
		setSession(Member.LOGIN_MEMBER_ID_SESSION_NAME, loginMember.getId());

		// 写入会员登录Cookie
		Cookie loginMemberUsernameCookie = new Cookie(
				Member.LOGIN_MEMBER_USERNAME_COOKIE_NAME, URLEncoder.encode(
						loginName.toLowerCase(), "UTF-8"));
		loginMemberUsernameCookie.setPath(getRequest().getContextPath() + "/");
		getResponse().addCookie(loginMemberUsernameCookie);
		// cookie 加入memberId
		Cookie loginMemberIdCookie = new Cookie(
				Member.LOGIN_MEMBER_ID_SESSION_NAME, loginMember.getId());
		loginMemberIdCookie.setPath(getRequest().getContextPath() + "/");
		getResponse().addCookie(loginMemberIdCookie);
		
		// FrontServerContext
		
		// 获取服务器应用地址
		GetDBdata db = new GetDBdata();
		try {
			String[] temp = { "FrontServerContext" };
			String url = db.getOneValue(
					"select value from zdconfig where type=?", temp);
			if (url.endsWith("/")) {
				loginMemberUsernameCookie.setPath(url);
			} else {
				loginMemberUsernameCookie.setPath(url + "/");
			}
			loginMemberUsernameCookie.setMaxAge(30 * 100000);
			getResponse().addCookie(loginMemberUsernameCookie);
		} catch (Exception e) {
		}

		// 合并购物车
		Cookie[] cookies = getRequest().getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (StringUtils.equalsIgnoreCase(cookie.getName(),
						CartItemCookie.CART_ITEM_LIST_COOKIE_NAME)) {
					if (StringUtils.isNotEmpty(cookie.getValue())) {
						JsonConfig jsonConfig = new JsonConfig();
						jsonConfig.setRootClass(CartItemCookie.class);
						JSONArray jsonArray = JSONArray.fromObject(cookie
								.getValue());
						List<CartItemCookie> cartItemCookieList = (List<CartItemCookie>) JSONSerializer
								.toJava(jsonArray, jsonConfig);
						for (CartItemCookie cartItemCookie : cartItemCookieList) {
							Product product = productService
									.load(cartItemCookie.getI());
							if (product != null) {
								CartItem cartItem = new CartItem();
								cartItem.setMember(loginMember);
								cartItem.setProduct(product);
								cartItem.setQuantity(cartItemCookie.getQ());
								cartItemService.save(cartItem);
							}
						}
					}
				}
			}
		}

		// 清空临时购物车Cookie
		Cookie cartItemCookie = new Cookie(
				CartItemCookie.CART_ITEM_LIST_COOKIE_NAME, null);
		cartItemCookie.setPath(getRequest().getContextPath() + "/");
		cartItemCookie.setMaxAge(0);
		getResponse().addCookie(cartItemCookie);

		// 调用横向接口
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Member", loginMember);
		try {
			ActionUtil.dealAction("wj00011", map, getRequest());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		String redirectionUrl = (String) getSession(Member.LOGIN_REDIRECTION_URL_SESSION_NAME);
		getRequest().getSession().removeAttribute(
				Member.LOGIN_REDIRECTION_URL_SESSION_NAME);
		if (StringUtils.isEmpty(redirectionUrl)) {
			return "memberCenterIndexAction";
		} else {
			getResponse().sendRedirect(redirectionUrl);
			return null;
		}
	}

	/***
	 * 新登录逻辑
	 * 
	 * @author fhz
	 * */
	public String artAjaxLogin() throws Exception {
		remarkLoginRequestCount();
		HttpServletRequest request = ServletActionContext.getRequest();
		String jsonpname = request.getParameter("jsonpCallback");

		boolean remark = true;
		// 判断上次登录与这次登陆积分是否发生变化标志 0：无变化 1：发生变化
		String pointsChange = "0";
		try {
			String captchaID = getRequest().getSession().getId();
			String challengeResponse = StringUtils.upperCase(getRequest()
					.getParameter(JCaptchaEngine.CAPTCHA_INPUT_NAME));
			if (StringUtils.isEmpty(challengeResponse)
					|| captchaService.validateResponseForID(captchaID,
							challengeResponse) == false) {
				remark = shopRemarkError("Y");
				// 记录登录错误次数
				if (!remark|| !queryLoginReqCountRemark(3) || !queryLoginSuccRemark(loginName)) {
					// return ajaxJsonErrorMessage("验证码没有写对哟");
					return ajaxHtml(jsonpname + "(" + ajaxJsonErrorMessageContent("验证码没有写对哟") + ");");
				}
			}
		} catch (CaptchaServiceException ce) {
			logger.error(ce.getMessage(), ce);
			return ajaxHtml(jsonpname + "(" + ajaxJsonErrorMessageContent("验证码没有写对哟") + ");");
		} catch (Exception e) {
			if (!remark) {
				// return ajaxJsonErrorMessage("验证码没有写对哟");
				return ajaxHtml(jsonpname + "(" + ajaxJsonErrorMessageContent("验证码没有写对哟") + ");");
			}
		}

		// 追加域名 session
		/*
		 * Cookie c = new Cookie("JSESSIONID",
		 * getRequest().getSession().getId());
		 * c.setDomain(".testkaixinbao.com"); c.setMaxAge(0);
		 * getResponse().addCookie(c);
		 */

		GetDBdata db = new GetDBdata();
		// 去掉用户名登录的控制
		String[] temp = { loginName, loginName };
		String firstLogin = db
				.getOneValue(
						"select 'X' from member where (loginDate is null or loginDate = '') and (email=? or mobileNo=?)",
						temp);
		if (loginName == null || "".equals(loginName)) {
			// return ajaxJsonErrorMessage("请填写正确账号才行哦");
			return ajaxHtml(jsonpname + "(" + ajaxJsonErrorMessageContent("请填写正确账号才行哦") + ");");
		}
		// 不需要绑定
		Member loginMember = memberService
				.getMemberByLoginNameNoBinding(loginName);
		if (loginMember != null) {
			// 解除会员账户锁定
			if ("Y".equalsIgnoreCase(loginMember.getIsAccountLocked())) {
				Date lockedDate = loginMember.getLockedDate();
				Date now = new Date();
				Calendar c1 = Calendar.getInstance();
				c1.setTime(lockedDate);
				Calendar c2 = Calendar.getInstance();
				c2.setTime(now);
				if (c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR) >= 1) {
					loginMember.setLoginFailureCount(0);
					loginMember.setIsAccountLocked("N");
					loginMember.setLockedDate(null);
					memberService.update(loginMember);
				}
			}
			if ("N".equalsIgnoreCase(loginMember.getIsAccountEnabled())) {
				// return ajaxJsonErrorMessage("您的账号已被禁用,无法登录!");
				return ajaxHtml(jsonpname + "(" + ajaxJsonErrorMessageContent("您的账号已被禁用,无法登录!") + ");");
			}
			// if ("Y".equalsIgnoreCase(loginMember.getIsAccountLocked())) {
			// return ajaxJsonErrorMessage("您的账号已被锁定,无法登录!");
			// }
			// 不需要绑定
			if (!memberService.verifyMemberNoNeedBinding(loginMember,
					member.getPassword())) {
				// 记录注册错误次数
				shopRemarkError("");
				// return ajaxJsonErrorMessage("密码没有写对哟");
				return ajaxHtml(jsonpname + "(" + ajaxJsonErrorMessageContent("密码没有写对哟") + ");");
				// Date lockedDate = loginMember.getLockedDate();
				// if (lockedDate == null) {
				// lockedDate = new Date();
				// }
				// Date now = new Date();
				// Calendar c1 = Calendar.getInstance();
				// c1.setTime(lockedDate);
				// Calendar c2 = Calendar.getInstance();
				// c2.setTime(now);
				// if (c2.get(Calendar.DAY_OF_YEAR) -
				// c1.get(Calendar.DAY_OF_YEAR) >= 1) {
				// loginMember.setLoginFailureCount(0);
				// loginMember.setIsAccountLocked("N");
				// loginMember.setLockedDate(null);
				// memberService.update(loginMember);
				// }
				// int loginFailureCount = loginMember.getLoginFailureCount() +
				// 1;
				// if (loginFailureCount == 5) {
				// loginMember.setIsAccountLocked("Y");
				// loginMember.setLockedDate(now);// 最后一次密码错误时间
				// loginMember.setLoginFailureCount(loginFailureCount);
				// memberService.update(loginMember);
				// // return
				// ajaxJsonErrorMessage("您的账户在一天之内已有5次密码输入错误,您的账号已被锁定! 您可以通过忘记密码的方式来解锁并重设密码!");
				// }
				//
				// if (loginFailureCount < 5) {
				// int remained = 5 - loginFailureCount;
				// loginMember.setLockedDate(now);// 最后一次密码错误时间
				// loginMember.setLoginFailureCount(loginFailureCount);
				// memberService.update(loginMember);
				// // return ajaxJsonErrorMessage("密码错误！您今天还有" + remained +
				// "次密码错误机会!");
				// }
				// return ajaxJsonErrorMessage("密码错误");

			}
		} else {
			// 记录注册错误次数
			shopRemarkError("");
			// return ajaxJsonErrorMessage("您的用户名或密码错误");
			return ajaxHtml(jsonpname + "(" + ajaxJsonErrorMessageContent("您的用户名或密码错误") + ");");
		}

		if (loginMember.getPreLoginPoints() == null) {
			loginMember.setPreLoginPoints(0);
		}
		// 判断积分与上次登陆积分是否有变化
		if (loginMember.getCurrentValidatePoint().compareTo(loginMember.getPreLoginPoints()) != 0) {
			pointsChange = "1";
		}

		loginMember.setLoginFailureCount(0);
		loginMember.setLoginIp(getRequest().getRemoteAddr());
		loginMember.setLoginDate(new Date());
		loginMember.setPreLoginPoints(loginMember.getCurrentValidatePoint());
		memberService.update(loginMember);
		
		// 设置可用积分
		setValiintegral(loginMember);
		
		remarkLoginSuccCount(loginName);
		if (loginMember.getUsername() != null
				&& !"".equals(loginMember.getUsername())) {
			loginName = loginMember.getUsername();
		}

		// 写入会员登录Session
		setSession(Member.LOGIN_MEMBER_ID_SESSION_NAME, loginMember.getId());
		setSession("loginName", loginName);
		// 写入会员登录Cookie
		Cookie loginMemberUsernameCookie = new Cookie(
				Member.LOGIN_MEMBER_USERNAME_COOKIE_NAME, URLEncoder.encode(
						loginName.toLowerCase(), "UTF-8"));
		loginMemberUsernameCookie.setPath(getRequest().getContextPath() + "/");
		getResponse().addCookie(loginMemberUsernameCookie);

		// cookie 加入memberId
		Cookie loginMemberIdCookie = new Cookie(
				Member.LOGIN_MEMBER_ID_SESSION_NAME, loginMember.getId());
		loginMemberIdCookie.setPath(getRequest().getContextPath() + "/");
		getResponse().addCookie(loginMemberIdCookie);
		
		// FrontServerContext

		// 获取服务器应用地址
		try {
			String[] temp1 = { "FrontServerContext" };
			String url = db.getOneValue(
					"select value from zdconfig where type=?", temp1);
			if (url.endsWith("/")) {
				loginMemberUsernameCookie.setPath(url);
			} else {
				loginMemberUsernameCookie.setPath(url + "/");
			}
			loginMemberUsernameCookie.setMaxAge(30 * 100000);
			getResponse().addCookie(loginMemberUsernameCookie);
		} catch (Exception e) {
		}

		// 合并购物车
		Cookie[] cookies = getRequest().getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (StringUtils.equalsIgnoreCase(cookie.getName(),
						CartItemCookie.CART_ITEM_LIST_COOKIE_NAME)) {
					if (StringUtils.isNotEmpty(cookie.getValue())) {
						JsonConfig jsonConfig = new JsonConfig();
						jsonConfig.setRootClass(CartItemCookie.class);
						JSONArray jsonArray = JSONArray.fromObject(cookie
								.getValue());
						List<CartItemCookie> cartItemCookieList = (List<CartItemCookie>) JSONSerializer
								.toJava(jsonArray, jsonConfig);
						for (CartItemCookie cartItemCookie : cartItemCookieList) {
							Product product = productService
									.load(cartItemCookie.getI());
							if (product != null) {
								CartItem cartItem = new CartItem();
								cartItem.setMember(loginMember);
								cartItem.setProduct(product);
								cartItem.setQuantity(cartItemCookie.getQ());
								cartItemService.save(cartItem);
							}
						}
					}
				}
			}
		}

		// 清空临时购物车Cookie
		Cookie cartItemCookie = new Cookie(
				CartItemCookie.CART_ITEM_LIST_COOKIE_NAME, null);
		cartItemCookie.setPath(getRequest().getContextPath() + "/");
		cartItemCookie.setMaxAge(0);
		getResponse().addCookie(cartItemCookie);

		Cookie[] ChannelCookie1 = getRequest().getCookies();

		/* 开始 */
		MemberChannel memberChannel = new MemberChannel();
		memberChannel.setMemberId(member.getId());
		String channelId = "";
		String channelTemp = (String) getSession("channelId");
		if (StringUtil.isNotEmpty(channelTemp)) {
			channelId = channelTemp;
		} else {
			Cookie[] ChannelCookie = getRequest().getCookies();
			for (Cookie cookie : ChannelCookie) {
				if ("channelId".equals(cookie.getName())) {
					channelId = cookie.getValue();
				}
			}
		}
		if (StringUtil.isNotEmpty(channelId)) {

			memberChannel.setChannelId(channelId);
			memberChannel.setType("0");
			memberChannel.setCreateDate(new Date());
			memberChannelService.save(memberChannel);
		}

		/* 结束 */


		MemberCookieUtil.addCookie(getRequest(), getResponse(), loginMember.getId(), loginName);

		// 调用横向接口
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Member", loginMember);
		try {
			ActionUtil.dealAction("wj00011", map, getRequest());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		String message = "";
		try {
			SDExpCalendarSchema tSDExpCalendarSchema = new SDExpCalendarSchema();
			SDExpCalendarSet tSDExpCalendarSet = tSDExpCalendarSchema
					.query(new QueryBuilder("where memberid ='"
							+ loginMember.getId() + "' "));

			message = "会员登录成功！";
			fullDegreeSess();
			setSession(receiveUserIp(), "");
			/*
			 * SDExpCalendarSchema tSDExpCalendarSchema1 = tSDExpCalendarSet
			 * .get(tSDExpCalendarSet.size() - 1); String exp =
			 * tSDExpCalendarSchema1.getExperience(); if (Long.parseLong(exp) ==
			 * 0) { message = "会员登录成功！"; } else { message = "会员登录成功！恭喜您获得" + exp
			 * + "经验值!"; }
			 */

		} catch (Exception e) {
			message = "会员登录成功！";
		}
		try {
			String judge = getParameter("judge");
			String orderSn = getParameter("orderSn");
			// 判断登录会员与购买会员是否相同
			if ("judge".equals(judge)) {
				SDOrder sdorder = sdorderService.getOrderByOrderSn(orderSn);
				String memberId = sdorder.getMemberId();
				// 不相同跳转到会员中心
				if (!memberId.equals(loginMember.getId())) {
					Map<String, String> jsonMap = new HashMap<String, String>();
					jsonMap.put(STATUS, "memberCenter");
					jsonMap.put(MESSAGE, message);
					JSONObject jsonObject = JSONObject.fromObject(jsonMap);
					// return ajax(jsonObject.toString(), "text/html");
					return ajaxHtml(jsonpname + "(" + jsonObject.toString() + ");");
				}
				// 相同跳转到支付成功页面
				else {
					String redirectionJudge = (String) getSession(Member.LOGIN_REDIRECTION_URL_SESSION_NAME);
					getRequest().getSession().removeAttribute(
							Member.LOGIN_REDIRECTION_URL_SESSION_NAME);
					// 如果为空跳转到会员中心
					if (StringUtils.isEmpty(redirectionJudge)) {
						String sql = "select value from zdconfig where type= 'ServerContext'";
						String path = db.getOneValue(sql);
						message = path + "/shop/member_center!index.action";
					} else {
						message = redirectionJudge;
					}

					Map<String, String> jsonMap = new HashMap<String, String>();
					jsonMap.put(STATUS, "MailJump");
					jsonMap.put(MESSAGE, message);
					JSONObject jsonObject = JSONObject.fromObject(jsonMap);
					// return ajax(jsonObject.toString(), "text/html");
					return ajaxHtml(jsonpname + "(" + jsonObject.toString() + ");");
				}
			}

			if (firstLogin != null && "X".equals(firstLogin)) {
				Map<String, String> jsonMap = new HashMap<String, String>();
				jsonMap.put(STATUS, CONTENT);
				jsonMap.put(MESSAGE, message);
				JSONObject jsonObject = JSONObject.fromObject(jsonMap);
				// return ajax(jsonObject.toString(), "text/html");
				return ajaxHtml(jsonpname + "(" + jsonObject.toString() + ");");
			} else {
				// return ajaxJsonSuccessMessage(message, pointsChange,
				// loginMember);
				return ajaxHtml(jsonpname + "(" + ajaxJsonSuccessMessageContent(message, pointsChange, loginMember) + ")");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			// return ajaxJsonSuccessMessage(message, pointsChange, null);
			return ajaxHtml(jsonpname + "(" + ajaxJsonSuccessMessageContent(message, pointsChange, null) + ");");
		}
	}

	// 输出JSON错误消息，返回null
	public String ajaxJsonErrorMessageContent(String message) {

		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS, ERROR);
		jsonMap.put(MESSAGE, message);
		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		return jsonObject.toString();
	}

	public String ajaxJsonSuccessMessageContent(String message, String pointsChange, Member loginMember) {

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		if (loginMember != null) {
			ajaxChecklogin(jsonMap, loginMember);
			jsonMap.put(STATUS, SUCCESS);
			jsonMap.put("pointsChange", pointsChange);
			jsonMap.put(MESSAGE, message);
			JSONObject jsonObject = JSONObject.fromObject(jsonMap);
			return jsonObject.toString();
		} else {
			jsonMap.put("CurrentValidatePoint", "");
			jsonMap.put("grade", "");
			jsonMap.put("gradeClass", "");
		}
		jsonMap.put(STATUS, SUCCESS);
		jsonMap.put("pointsChange", pointsChange);
		jsonMap.put(MESSAGE, message);
		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		return jsonObject.toString();
	}

	public String ajaxJsonSuccessMessage(String message, String pointsChange, Member loginMember) {

		Map<String, String> jsonMap = new HashMap<String, String>();
		if (loginMember != null) {
			jsonMap.put("CurrentValidatePoint", String.valueOf(loginMember.getCurrentValidatePoint()));
			jsonMap.put("grade", loginMember.getGrade());
			MemberCenterAction mem = new MemberCenterAction();
			Map<String, String> gradeMap = mem.getGradeIcon(loginMember.getVipFlag(),
					loginMember.getGrade(), "", loginMember.getBirthday(), loginMember.getBirthYear());
			if (StringUtil.isNotEmpty(gradeMap.get("gradeClass"))) {
				jsonMap.put("gradeClass", gradeMap.get("gradeClass").replace("vip_k ", "vip_k vip_top "));
			}
		} else {
			jsonMap.put("CurrentValidatePoint", "");
			jsonMap.put("grade", "");
			jsonMap.put("gradeClass", "");
		}

		jsonMap.put(STATUS, SUCCESS);
		jsonMap.put("pointsChange", pointsChange);

		jsonMap.put(MESSAGE, message);
		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		return ajax(jsonObject.toString(), "text/html");
	}

	// 获取操作用户IP
	public String receiveUserIp() {

		// 获取操作用户ip
		HttpServletRequest request = ServletActionContext.getRequest();
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
			if (ip == null || ip.length() == 0
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
				if (ip == null || ip.length() == 0
						|| "unknown".equalsIgnoreCase(ip)) {
					ip = request.getRemoteAddr();
				}
			}
		}
		String[] str = ip.split(",");
		if (str != null && str.length > 1) {
			ip = str[0];
		}
		return ip;
	}

	/***
	 * 
	 * 标记30分钟内登录/注册失败次数
	 * 
	 * @author jiaomengying
	 * */
	public String remarkError() {

		String ip = receiveUserIp();
		int count = 1;
		String date = "";
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date1 = sf.format(new Date());
		date = date1;
		Object map = new HashMap<String, String>();
		long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
		long nh = 1000 * 60 * 60;// 一小时的毫秒数
		long nm = 1000 * 60;// 一分钟的毫秒数
		long diff;

		map = getSession().get(ip);
		if (!StringUtil.isEmpty(map)) {
			String date2 = sf.format(new Date());
			try {

				String strsub[] = map.toString().split("&");
				date = strsub[0];

				diff = sf.parse(date2).getTime() - sf.parse(date).getTime();
				long min = diff % nd % nh / nm;// 计算差多少分钟
				if (min <= 30) {
					// 30分钟内输错3次显示验证码
					if (Integer.valueOf(strsub[1]) < 2) {
						count += Integer.valueOf(strsub[1]);
					} else {
						count += Integer.valueOf(strsub[1]);
						setSession(ip, date + "&" + count);
						// 显示验证码
						return ajaxJson("false");
					}
					// 超过30分钟显示验证码
				} else {
					count = 1;
					setSession(ip, date1 + "&" + count);
					return ajaxJson("true");
				}

			} catch (ParseException e) {
				logger.error(e.getMessage(), e);
				return ajaxJson("false");
			}

		}
		setSession(ip, date + "&" + count);
		return ajaxJson("true");
	}

	/***
	 * 
	 * 标记30分钟内登录/注册失败次数 后台调用
	 * 
	 * @author jiaomengying
	 * */
	public boolean shopRemarkError(String flag) {

		String ip = receiveUserIp();
		int count = 1;
		String date = "";
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date1 = sf.format(new Date());
		date = date1;
		Object map = new HashMap<String, String>();
		long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
		long nh = 1000 * 60 * 60;// 一小时的毫秒数
		long nm = 1000 * 60;// 一分钟的毫秒数
		long diff;

		map = getSession().get(ip);
		if (!StringUtil.isEmpty(map)) {
			String date2 = sf.format(new Date());
			try {

				String strsub[] = map.toString().split("&");
				date = strsub[0];

				diff = sf.parse(date2).getTime() - sf.parse(date).getTime();
				long min = diff % nd % nh / nm;// 计算差多少分钟
				if (min <= 30) {
					// 30分钟内输错3次显示验证码
					if (Integer.valueOf(strsub[1]) < 3) {
						if (!"Y".equals(flag)) {
							count += Integer.valueOf(strsub[1]);
						}
					} else {
						return false;
					}
					// 超过30分钟显示验证码
				} else {
					if (!"Y".equals(flag)) {
						count += Integer.valueOf(strsub[1]);
						return false;
					}
					// 显示验证码
				}

			} catch (ParseException e) {
				logger.error(e.getMessage(), e);
			}
		}
		if (!"Y".equals(flag)) {
			setSession(ip, date + "&" + count);
		}
		return true;
	}
	/***
	 *
	 * 标记同一IP注册请求的次数
	 *
	 * */
	private void remarkRegisterCount() {
		try{
			String ip = receiveUserIp();
			String date = DateUtil.getCurrentDate("yyyyMMdd");
			Md5security md51 = new Md5security();
			md51.md5s(ip);
			String key = "WJ-IPRegister-"+md51.str;
			String info = JedisCommonUtil.getString(3, key);
			JedisCommonUtilPutIn(info,date,key);
		} catch(Exception e) {
			logger.error("记录IP登录请求次数异常！" + e.getMessage(), e);
		}
	}

	/**
	 * 查询同一IP注册次数
	 *
	 * @return
	 */
	public String queryRegisterCount() {
		String ip = receiveUserIp();
		return ajaxJson(String.valueOf(checkIPRegister(ip, 10)));
	}

	private boolean checkIPRegister(String ip, int limitCount) {
		String date = DateUtil.getCurrentDate("yyyyMMdd");
		Md5security md51 = new Md5security();
		md51.md5s(ip);
		String key = "WJ-IPRegister-"+md51.str;
		String info = JedisCommonUtil.getString(3, key);
		if (StringUtil.isNotEmpty(info)) {
			String[] remark = info.split("&");
			if (date.equals(remark[0])) {
				int count = Integer.valueOf(remark[1]);
				if (count > limitCount) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	/**
	 * 查询登录/注册失败次数、同一IP登陆请求的次数
	 * @return
	 */
	public String queryLoginRemark() {
		String ip = receiveUserIp();
		Object map = new HashMap<String, String>();
		map = getSession().get(ip);
		int count = 0;
		if (!StringUtil.isEmpty(map)) {
			String strsub[] = map.toString().split("&");
			count = Integer.valueOf(strsub[1]);
		}
		if (count >= 3) {
			return ajaxJson("false");
		}
		
		if (!queryLoginReqCountRemark(2)) {
			return ajaxJson("false");
		}
		
		return ajaxJson("true");
	}
	
	public boolean queryLoginReqCountRemark(int limitCount) {
		
		try {
			int count = 0;
			String ip = receiveUserIp();
			Md5security md51 = new Md5security();
			md51.md5s(ip);
			String info = JedisCommonUtil.getString(3, "WJ-IPLoginRequest-"+md51.str);
			if (StringUtil.isNotEmpty(info)) {
				String date = DateUtil.getCurrentDate("yyyyMMdd");
				String[] remark = info.split("&");
				if (date.equals(remark[0])) {
					count = Integer.valueOf(remark[1]);
					// 同一IP登陆请求的次数超过限制次数需要输入验证码
					if (count > limitCount) {
						return false;
					}
				}
			}
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return true;
	}
	
	/***
	 * 
	 * 查询登录/注册失败次数
	 * 
	 * @author jiaomengying
	 * */
	public String queryRemarkError() {

		String ip = receiveUserIp();
		Object map = new HashMap<String, String>();
		map = getSession().get(ip);
		int count = 0;
		if (!StringUtil.isEmpty(map)) {
			String strsub[] = map.toString().split("&");
			count = Integer.valueOf(strsub[1]);
		}
		if (count >= 3) {
			return ajaxJson("false");
		}
		return ajaxJson(String.valueOf(checkIPRegister(ip, 9)));
	}

	/***
	 * 
	 * 浮动层登录 ，由于浮动层使用跨域写法
	 * 
	 * @author fhz
	 * */
	public String artAjaxLoginDoamin() throws Exception {
		remarkLoginRequestCount();
		HttpServletRequest request = ServletActionContext.getRequest();
		String jsonpname = request.getParameter("callback");
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put("status", "0");
		boolean remark = true;
		try {
			String captchaID = getRequest().getSession().getId();
			String challengeResponse = StringUtils.upperCase(getRequest()
					.getParameter(JCaptchaEngine.CAPTCHA_INPUT_NAME));
			if (StringUtils.isEmpty(challengeResponse)
					|| captchaService.validateResponseForID(captchaID,
							challengeResponse) == false) {

				// 记录登录错误次数
				remark = shopRemarkError("Y");
				if (!remark || !queryLoginReqCountRemark(3) || !queryLoginSuccRemark(loginName)) {
					jsonMap.put("message", "验证码没有写对哟");
					return ajaxHtml(jsonpname + "("
							+ JSONObject.fromObject(jsonMap).toString() + ");");
				}

			}
			
		} catch (CaptchaServiceException ce) {
			logger.error(ce.getMessage(), ce);
			jsonMap.put("message", "验证码没有写对哟");
			return ajaxHtml(jsonpname + "("
					+ JSONObject.fromObject(jsonMap).toString() + ");");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			// 记录登录错误次数
			if (!remark) {
				jsonMap.put("message", "验证码没有写对哟");
				return ajaxHtml(jsonpname + "("
						+ JSONObject.fromObject(jsonMap).toString() + ");");
			}
		}

		GetDBdata db = new GetDBdata();
		// 去掉用户名登录的控制
		String[] temp = { loginName, loginName };
		String firstLogin = db
				.getOneValue(
						"select 'X' from member where (loginDate is null or loginDate = '') and (email=? or mobileNo=?)",
						temp);
		if (loginName == null || "".equals(loginName)) {
			jsonMap.put("message", "请填写正确账号才行哦");
			return ajaxHtml(jsonpname + "("
					+ JSONObject.fromObject(jsonMap).toString() + ");");
		}
		// 不需要绑定
		Member loginMember = memberService
				.getMemberByLoginNameNoBinding(loginName);
		if (loginMember != null) {
			// 解除会员账户锁定
			if ("Y".equalsIgnoreCase(loginMember.getIsAccountLocked())) {
				Date lockedDate = loginMember.getLockedDate();
				Date now = new Date();
				Calendar c1 = Calendar.getInstance();
				c1.setTime(lockedDate);
				Calendar c2 = Calendar.getInstance();
				c2.setTime(now);
				if (c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR) >= 1) {
					loginMember.setLoginFailureCount(0);
					loginMember.setIsAccountLocked("N");
					loginMember.setLockedDate(null);
					memberService.update(loginMember);
				}
			}
			if ("N".equalsIgnoreCase(loginMember.getIsAccountEnabled())) {
				jsonMap.put("message", "您的账号已被禁用,无法登录!");
				return ajaxHtml(jsonpname + "("
						+ JSONObject.fromObject(jsonMap).toString() + ");");

			}
			// if ("Y".equalsIgnoreCase(loginMember.getIsAccountLocked())) {
			// return ajaxJsonErrorMessage("您的账号已被锁定,无法登录!");
			// }
			// 不需要绑定
			if (!memberService.verifyMemberNoNeedBinding(loginMember,
					this.loginPassword)) {
				shopRemarkError("");
				jsonMap.put("message", "密码没有写对哟");
				return ajaxHtml(jsonpname + "("
						+ JSONObject.fromObject(jsonMap).toString() + ");");
			}
		} else {
			shopRemarkError("");
			jsonMap.put("message", "您的用户名或密码错误");
			return ajaxHtml(jsonpname + "("
					+ JSONObject.fromObject(jsonMap).toString() + ");");
		}
		loginMember.setLoginFailureCount(0);
		loginMember.setLoginIp(getRequest().getRemoteAddr());
		loginMember.setLoginDate(new Date());
		memberService.update(loginMember);
		
		// 设置可用积分
		setValiintegral(loginMember);
		
		remarkLoginSuccCount(loginName);
		if (loginMember.getUsername() != null
				&& !"".equals(loginMember.getUsername())) {
			loginName = loginMember.getUsername();
		}
		
		// 写入会员登录Session
		setSession(Member.LOGIN_MEMBER_ID_SESSION_NAME, loginMember.getId());

		// 写入会员登录Cookie
		Cookie loginMemberUsernameCookie = new Cookie(
				Member.LOGIN_MEMBER_USERNAME_COOKIE_NAME, URLEncoder.encode(
						loginName.toLowerCase(), "UTF-8"));
		loginMemberUsernameCookie.setPath(getRequest().getContextPath() + "/");
		getResponse().addCookie(loginMemberUsernameCookie);

		// cookie 加入memberId
		Cookie loginMemberIdCookie = new Cookie(
				Member.LOGIN_MEMBER_ID_SESSION_NAME, loginMember.getId());
		loginMemberIdCookie.setPath(getRequest().getContextPath() + "/");
		getResponse().addCookie(loginMemberIdCookie);
		
		// FrontServerContext

		// 获取服务器应用地址
		try {
			String[] temp1 = { "FrontServerContext" };
			String url = db.getOneValue(
					"select value from zdconfig where type=?", temp1);
			if (url.endsWith("/")) {
				loginMemberUsernameCookie.setPath(url);
			} else {
				loginMemberUsernameCookie.setPath(url + "/");
			}
			loginMemberUsernameCookie.setMaxAge(30 * 100000);
			getResponse().addCookie(loginMemberUsernameCookie);
		} catch (Exception e) {
		}

		// 合并购物车
		Cookie[] cookies = getRequest().getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (StringUtils.equalsIgnoreCase(cookie.getName(),
						CartItemCookie.CART_ITEM_LIST_COOKIE_NAME)) {
					if (StringUtils.isNotEmpty(cookie.getValue())) {
						JsonConfig jsonConfig = new JsonConfig();
						jsonConfig.setRootClass(CartItemCookie.class);
						JSONArray jsonArray = JSONArray.fromObject(cookie
								.getValue());
						List<CartItemCookie> cartItemCookieList = (List<CartItemCookie>) JSONSerializer
								.toJava(jsonArray, jsonConfig);
						for (CartItemCookie cartItemCookie : cartItemCookieList) {
							Product product = productService
									.load(cartItemCookie.getI());
							if (product != null) {
								CartItem cartItem = new CartItem();
								cartItem.setMember(loginMember);
								cartItem.setProduct(product);
								cartItem.setQuantity(cartItemCookie.getQ());
								cartItemService.save(cartItem);
							}
						}
					}
				}
			}
		}

		// 清空临时购物车Cookie
		Cookie cartItemCookie = new Cookie(
				CartItemCookie.CART_ITEM_LIST_COOKIE_NAME, null);
		cartItemCookie.setPath(getRequest().getContextPath() + "/");
		cartItemCookie.setMaxAge(0);
		getResponse().addCookie(cartItemCookie);

		Cookie[] ChannelCookie1 = getRequest().getCookies();

		/* 开始 */
		MemberChannel memberChannel = new MemberChannel();
		memberChannel.setMemberId(loginMember.getId());
		String channelId = "";
		String channelTemp = (String) getSession("channelId");
		if (StringUtil.isNotEmpty(channelTemp)) {
			channelId = channelTemp;
		} else {
			Cookie[] ChannelCookie = getRequest().getCookies();
			for (Cookie cookie : ChannelCookie) {
				if ("channelId".equals(cookie.getName())) {
					channelId = cookie.getValue();
				}
			}
		}
		if (StringUtil.isNotEmpty(channelId)) {

			memberChannel.setChannelId(channelId);
			memberChannel.setType("0");
			memberChannel.setCreateDate(new Date());
			memberChannelService.save(memberChannel);
		}

		/* 结束 */
		// 调用横向接口
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Member", loginMember);
		try {
			ActionUtil.dealAction("wj00011", map, getRequest());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		String message = "";
		try {
			SDExpCalendarSchema tSDExpCalendarSchema = new SDExpCalendarSchema();
			SDExpCalendarSet tSDExpCalendarSet = tSDExpCalendarSchema
					.query(new QueryBuilder("where memberid ='"
							+ loginMember.getId() + "' "));
			message = "会员登录成功！";
			jsonMap.put("memberid", loginMember.getId());
			// 获取资料完整度
			fullDegreeSess();
			// 登录成功 清空session中的ip值
			setSession(receiveUserIp(), "");
			/*
			 * SDExpCalendarSchema tSDExpCalendarSchema1 =
			 * tSDExpCalendarSet.get(tSDExpCalendarSet.size() - 1); String exp =
			 * tSDExpCalendarSchema1.getExperience();
			 * 
			 * if (Long.parseLong(exp) == 0) { message = "会员登录成功！"; } else {
			 * message = "会员登录成功！恭喜您获得" + exp + "经验值!"; }
			 */

		} catch (Exception e) {
			message = "会员登录成功！";
			// 获取资料完整度
			fullDegreeSess();
			// 登录成功 清空session中的ip值
			setSession(receiveUserIp(), "");
		}
		try {
			String judge = getParameter("judge");
			String orderSn = getParameter("orderSn");
			// 判断登录会员与购买会员是否相同
			if ("judge".equals(judge)) {
				SDOrder sdorder = sdorderService.getOrderByOrderSn(orderSn);
				String memberId = sdorder.getMemberId();
				// 不相同跳转到会员中心
				if (!memberId.equals(loginMember.getId())) {
					jsonMap.put(STATUS, "memberCenter");
					jsonMap.put(MESSAGE, message);
					return ajaxHtml(jsonpname + "("
							+ JSONObject.fromObject(jsonMap).toString() + ");");
				}
				// 相同跳转到支付成功页面
				else {
					String redirectionJudge = (String) getSession(Member.LOGIN_REDIRECTION_URL_SESSION_NAME);
					getRequest().getSession().removeAttribute(
							Member.LOGIN_REDIRECTION_URL_SESSION_NAME);
					// 如果为空跳转到会员中心
					if (StringUtils.isEmpty(redirectionJudge)) {
						String sql = "select value from zdconfig where type= 'ServerContext'";
						String path = db.getOneValue(sql);
						message = path + "/shop/member_center!index.action";
					} else {
						message = redirectionJudge;
					}

					jsonMap.put(STATUS, "MailJump");
					jsonMap.put(MESSAGE, message);
					return ajaxHtml(jsonpname + "("
							+ JSONObject.fromObject(jsonMap).toString() + ");");
				}
			}

			if (firstLogin != null && "X".equals(firstLogin)) {
				jsonMap.put(STATUS, CONTENT);
				jsonMap.put(MESSAGE, message);
				return ajaxHtml(jsonpname + "("
						+ JSONObject.fromObject(jsonMap).toString() + ");");
			} else {
				jsonMap.put(STATUS, SUCCESS);
				jsonMap.put(MESSAGE, message);
				return ajaxHtml(jsonpname + "("
						+ JSONObject.fromObject(jsonMap).toString() + ");");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			jsonMap.put(STATUS, SUCCESS);
			jsonMap.put(MESSAGE, message);
			return ajaxHtml(jsonpname + "("
					+ JSONObject.fromObject(jsonMap).toString() + ");");
		}
	}

	/**
	 * 记录IP登录请求次数
	 */
	public void remarkLoginRequestCount() {
		try {
			String ip = receiveUserIp();
			String date = DateUtil.getCurrentDate("yyyyMMdd");
			Md5security md51 = new Md5security();
			md51.md5s(ip);
			String key = "WJ-IPLoginRequest-"+md51.str;
			String info = JedisCommonUtil.getString(3, key);
			JedisCommonUtilPutIn(info,date,key);
		} catch(Exception e) {
			logger.error("记录IP登录请求次数异常！" + e.getMessage(), e);
		}
	}
	
	/**
	 * 记录用户当天登录成功的次数
	 * @param loginName
	 */
	public void remarkLoginSuccCount(String loginName) {
		try {
			// 查询redis中用户登录的次数
			String key = "WJ-SuccLogin-"+loginName;
			String info = JedisCommonUtil.getString(3, key);
			String date = DateUtil.getCurrentDate("yyyyMMdd");
			JedisCommonUtilPutIn(info,date,key);
		} catch (Exception e) {
			logger.error("记录用户当天登录成功的次数异常" + e.getMessage(), e);
		}
	}
	
	// 会员中心获取资料完整度
	public void fullDegreeSess() {

		String fullDegree = "";
		int count = 0;
		if (!(getLoginMember().getBirthday() == null || ""
				.equals(getLoginMember().getBirthday())))
			count += 15;
		if (!(getLoginMember().getEmail() == null || "".equals(getLoginMember()
				.getEmail())))
			count += 15;
		if (!(getLoginMember().getIDType() == null
				|| "".equals(getLoginMember().getIDType()) || "-1"
					.equals(getLoginMember().getIDType())))
			count += 4;
		if (!(getLoginMember().getIndustryType() == null || ""
				.equals(getLoginMember().getIndustryType())))
			count += 3;
		if (!(getLoginMember().getLocation() == null
				|| "".equals(getLoginMember().getLocation()) || "-1"
					.equals(getLoginMember().getLocation())))
			count += 4;
		if (!(getLoginMember().getMarriageStatus() == null
				|| "".equals(getLoginMember().getMarriageStatus()) || "-1"
					.equals(getLoginMember().getMarriageStatus())))
			count += 4;
		if (!(getLoginMember().getMobileNO() == null || ""
				.equals(getLoginMember().getMobileNO())))
			count += 15;
		if (!(getLoginMember().getPosition() == null || ""
				.equals(getLoginMember().getPosition())))
			count += 2;
		if (!(getLoginMember().getRealName() == null || ""
				.equals(getLoginMember().getRealName())))
			count += 15;
		if (!(getLoginMember().getSex() == null || "".equals(getLoginMember()
				.getSex())))
			count += 15;
		if (!(getLoginMember().getVIPType() == null
				|| "".equalsIgnoreCase(getLoginMember().getVIPType()) || "-1"
					.equals(getLoginMember().getVIPType())))
			count += 4;
		if (!(getLoginMember().getZipcode() == null || ""
				.equals(getLoginMember().getZipcode())))
			count += 4;

		fullDegree = count + "%";
		setSession("fullDegree", fullDegree);
	}

	@SuppressWarnings("unchecked")
	@Validations(requiredStrings = {
			// @RequiredStringValidator(fieldName = "member.username", message =
			// "用户名不允许为空!"),
			@RequiredStringValidator(fieldName = "member.password", message = "密码不允许为空!") })
	@InputConfig(resultName = "error")
	public String ajaxLogin() throws Exception {

		GetDBdata db = new GetDBdata();
		String[] temp = { loginName, loginName, loginName };
		String firstLogin = db
				.getOneValue(
						"select 'X' from member where (loginDate is null or loginDate = '') and (email=? or mobileNo=? "
								+ "or username=?)", temp);
		try {
			String captchaID = getRequest().getSession().getId();
			String challengeResponse = StringUtils.upperCase(getRequest()
					.getParameter(JCaptchaEngine.CAPTCHA_INPUT_NAME));
			if (StringUtils.isEmpty(challengeResponse)
					|| captchaService.validateResponseForID(captchaID,
							challengeResponse) == false) {
				return ajaxJsonErrorMessage("验证码输入错误！");
			}
		} catch (Exception e) {
			return ajaxJsonErrorMessage("验证码输入错误！");
		}
		if (loginName == null || "".equals(loginName)) {
			return ajaxJsonErrorMessage("用户名不允许为空！");
		}
		Member loginMember = memberService.getMemberByLoginName(loginName);
		if (loginMember != null) {
			// 解除会员账户锁定
			if ("Y".equalsIgnoreCase(loginMember.getIsAccountLocked())) {
				Date lockedDate = loginMember.getLockedDate();
				Date now = new Date();
				Calendar c1 = Calendar.getInstance();
				c1.setTime(lockedDate);
				Calendar c2 = Calendar.getInstance();
				c2.setTime(now);
				if (c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR) >= 1) {
					loginMember.setLoginFailureCount(0);
					loginMember.setIsAccountLocked("N");
					loginMember.setLockedDate(null);
					memberService.update(loginMember);
				}
			}
			if ("N".equalsIgnoreCase(loginMember.getIsAccountEnabled())) {
				return ajaxJsonErrorMessage("您的账号已被禁用,无法登录!");

			}
			if ("Y".equalsIgnoreCase(loginMember.getIsAccountLocked())) {
				return ajaxJsonErrorMessage("您的账号已被锁定,无法登录!");
			}
			if (!memberService.verifyMember(loginName, member.getPassword())) {
				Date lockedDate = loginMember.getLockedDate();
				if (lockedDate == null) {
					lockedDate = new Date();
				}
				Date now = new Date();
				Calendar c1 = Calendar.getInstance();
				c1.setTime(lockedDate);
				Calendar c2 = Calendar.getInstance();
				c2.setTime(now);
				if (c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR) >= 1) {
					loginMember.setLoginFailureCount(0);
					loginMember.setIsAccountLocked("N");
					loginMember.setLockedDate(null);
					memberService.update(loginMember);
				}
				int loginFailureCount = loginMember.getLoginFailureCount() + 1;
				if (loginFailureCount == 5) {
					loginMember.setIsAccountLocked("Y");
					loginMember.setLockedDate(now);// 最后一次密码错误时间
					loginMember.setLoginFailureCount(loginFailureCount);
					memberService.update(loginMember);
					return ajaxJsonErrorMessage("您的账户在一天之内已有5次密码输入错误,您的账号已被锁定! 您可以通过忘记密码的方式来解锁并重设密码!");
				}

				if (loginFailureCount < 5) {
					int remained = 5 - loginFailureCount;
					loginMember.setLockedDate(now);// 最后一次密码错误时间
					loginMember.setLoginFailureCount(loginFailureCount);
					memberService.update(loginMember);
					return ajaxJsonErrorMessage("您今天还有" + remained + "次密码错误机会!");
				}
				return ERROR;
			}
		} else {
			return ajaxJsonErrorMessage("您的用户名或密码错误！");
		}
		loginMember.setLoginFailureCount(0);
		loginMember.setLoginIp(getRequest().getRemoteAddr());
		loginMember.setLoginDate(new Date());
		memberService.update(loginMember);

		if (loginMember.getUsername() != null
				&& !"".equals(loginMember.getUsername())) {
			loginName = loginMember.getUsername();
		}

		// 写入会员登录Session
		setSession(Member.LOGIN_MEMBER_ID_SESSION_NAME, loginMember.getId());

		// 写入会员登录Cookie
		Cookie loginMemberUsernameCookie = new Cookie(
				Member.LOGIN_MEMBER_USERNAME_COOKIE_NAME, URLEncoder.encode(
						loginName.toLowerCase(), "UTF-8"));
		loginMemberUsernameCookie.setPath(getRequest().getContextPath() + "/");
		getResponse().addCookie(loginMemberUsernameCookie);

		// cookie 加入memberId
		Cookie loginMemberIdCookie = new Cookie(
				Member.LOGIN_MEMBER_ID_SESSION_NAME, loginMember.getId());
		loginMemberIdCookie.setPath(getRequest().getContextPath() + "/");
		getResponse().addCookie(loginMemberIdCookie);
		
		// FrontServerContext

		// 获取服务器应用地址
		try {
			String[] temp2 = { "FrontServerContext" };
			String url = db.getOneValue(
					"select value from zdconfig where type=?", temp2);
			if (url.endsWith("/")) {
				loginMemberUsernameCookie.setPath(url);
			} else {
				loginMemberUsernameCookie.setPath(url + "/");
			}
			loginMemberUsernameCookie.setMaxAge(30 * 100000);
			getResponse().addCookie(loginMemberUsernameCookie);
		} catch (Exception e) {
		}

		// 合并购物车
		Cookie[] cookies = getRequest().getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (StringUtils.equalsIgnoreCase(cookie.getName(),
						CartItemCookie.CART_ITEM_LIST_COOKIE_NAME)) {
					if (StringUtils.isNotEmpty(cookie.getValue())) {
						JsonConfig jsonConfig = new JsonConfig();
						jsonConfig.setRootClass(CartItemCookie.class);
						JSONArray jsonArray = JSONArray.fromObject(cookie
								.getValue());
						List<CartItemCookie> cartItemCookieList = (List<CartItemCookie>) JSONSerializer
								.toJava(jsonArray, jsonConfig);
						for (CartItemCookie cartItemCookie : cartItemCookieList) {
							Product product = productService
									.load(cartItemCookie.getI());
							if (product != null) {
								CartItem cartItem = new CartItem();
								cartItem.setMember(loginMember);
								cartItem.setProduct(product);
								cartItem.setQuantity(cartItemCookie.getQ());
								cartItemService.save(cartItem);
							}
						}
					}
				}
			}
		}

		// 清空临时购物车Cookie
		Cookie cartItemCookie = new Cookie(
				CartItemCookie.CART_ITEM_LIST_COOKIE_NAME, null);
		cartItemCookie.setPath(getRequest().getContextPath() + "/");
		cartItemCookie.setMaxAge(0);
		getResponse().addCookie(cartItemCookie);

		Cookie[] ChannelCookie1 = getRequest().getCookies();

		/* 开始 */
		MemberChannel memberChannel = new MemberChannel();
		memberChannel.setMemberId(member.getId());
		String channelId = "";
		String channelTemp = (String) getSession("channelId");
		if (StringUtil.isNotEmpty(channelTemp)) {
			channelId = channelTemp;
		} else {
			Cookie[] ChannelCookie = getRequest().getCookies();
			for (Cookie cookie : ChannelCookie) {
				if ("channelId".equals(cookie.getName())) {
					channelId = cookie.getValue();
				}
			}
		}
		if (StringUtil.isNotEmpty(channelId)) {

			memberChannel.setChannelId(channelId);
			memberChannel.setType("0");
			memberChannel.setCreateDate(new Date());
			memberChannelService.save(memberChannel);
		}

		// 调用横向接口
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Member", loginMember);
		try {
			ActionUtil.dealAction("wj00011", map, getRequest());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		String message = "";
		try {
			SDExpCalendarSchema tSDExpCalendarSchema = new SDExpCalendarSchema();
			SDExpCalendarSet tSDExpCalendarSet = tSDExpCalendarSchema
					.query(new QueryBuilder("where memberid ='"
							+ loginMember.getId() + "' "));
			message = "会员登录成功！";
			/*
			 * SDExpCalendarSchema tSDExpCalendarSchema1 = tSDExpCalendarSet
			 * .get(tSDExpCalendarSet.size() - 1); String exp =
			 * tSDExpCalendarSchema1.getExperience();
			 * 
			 * if (Long.parseLong(exp) == 0) { message = "会员登录成功！"; } else {
			 * message = "会员登录成功！恭喜您获得" + exp + "经验值!"; }
			 */

		} catch (Exception e) {
			message = "会员登录成功！";
		}

		if (firstLogin != null && "X".equals(firstLogin)) {
			Map<String, String> jsonMap = new HashMap<String, String>();
			jsonMap.put(STATUS, CONTENT);
			jsonMap.put(MESSAGE, message);
			JSONObject jsonObject = JSONObject.fromObject(jsonMap);
			return ajax(jsonObject.toString(), "text/html");
		} else {
			return ajaxJsonSuccessMessage(message);
		}
	}

	// 会员注销
	public String logout() {

		MemberCookieUtil.delCookie(getRequest(),getSession().get(Member.LOGIN_MEMBER_ID_SESSION_NAME) + "");

		getRequest().getSession().removeAttribute(
				Member.LOGIN_MEMBER_ID_SESSION_NAME);
		Cookie cookie = new Cookie(Member.LOGIN_MEMBER_USERNAME_COOKIE_NAME,
				null);
		cookie.setPath(getRequest().getContextPath() + "/");
		cookie.setMaxAge(0);
		getResponse().addCookie(cookie);
		
		Cookie cookie1 = new Cookie(Member.LOGIN_MEMBER_ID_SESSION_NAME,
				null);
		cookie1.setPath(getRequest().getContextPath() + "/");
		cookie1.setMaxAge(0);
		getResponse().addCookie(cookie1);

		// 针对QQ彩贝的cookie销毁
		Cookie cookid_headshow = new Cookie("QQCB_headshow", null);
		cookid_headshow.setPath(getRequest().getContextPath() + "/");
		cookid_headshow.setMaxAge(0);
		getResponse().addCookie(cookid_headshow);
		Cookie cookie_JifenUrl = new Cookie("QQCB_JifenUrl", null);
		cookie_JifenUrl.setPath(getRequest().getContextPath() + "/");
		cookie_JifenUrl.setMaxAge(0);
		getResponse().addCookie(cookie_JifenUrl);
		// 获取服务器应用地址
		GetDBdata db = new GetDBdata();
		try {
			String[] temp3 = { "FrontServerContext" };
			String url = db.getOneValue(
					"select value from zdconfig where type=?", temp3);
			if (url.endsWith("/")) {
				cookie.setPath(url);
				// 针对QQ彩贝的cookie
				cookid_headshow.setPath(url);
				cookie_JifenUrl.setPath(url);
			} else {
				cookie.setPath(url + "/");
				// 针对QQ彩贝的cookie
				cookid_headshow.setPath(url + "/");
				cookie_JifenUrl.setPath(url + "/");
			}
			cookie.setMaxAge(0);
			getResponse().addCookie(cookie);
			// 针对QQ彩贝的cookie
			cookid_headshow.setMaxAge(0);
			getResponse().addCookie(cookid_headshow);
			cookie_JifenUrl.setMaxAge(0);
			getResponse().addCookie(cookid_headshow);
		} catch (Exception e) {
		}

		return "validate_finish";
	}

	// 会员注销
	public String logoutAjax() {

		MemberCookieUtil.delCookie(getRequest(),getSession().get(Member.LOGIN_MEMBER_ID_SESSION_NAME) + "");
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String jsonpname = request.getParameter("callback");
		Map<String, String> jsonMap = new HashMap<String, String>();

		try {
			getRequest().getSession().removeAttribute(
					Member.LOGIN_MEMBER_ID_SESSION_NAME);
			Cookie cookie = new Cookie(
					Member.LOGIN_MEMBER_USERNAME_COOKIE_NAME, null);
			cookie.setPath(getRequest().getContextPath() + "/");
			cookie.setMaxAge(0);
			getResponse().addCookie(cookie);
			Cookie cookie1 = new Cookie(Member.LOGIN_MEMBER_ID_SESSION_NAME,
					null);
			cookie1.setPath(getRequest().getContextPath() + "/");
			cookie1.setMaxAge(0);
			getResponse().addCookie(cookie1);

			// 针对QQ彩贝的cookie销毁
			Cookie cookid_headshow = new Cookie("QQCB_headshow", null);
			cookid_headshow.setPath(getRequest().getContextPath() + "/");
			cookid_headshow.setMaxAge(0);
			getResponse().addCookie(cookid_headshow);
			Cookie cookie_JifenUrl = new Cookie("QQCB_JifenUrl", null);
			cookie_JifenUrl.setPath(getRequest().getContextPath() + "/");
			cookie_JifenUrl.setMaxAge(0);
			getResponse().addCookie(cookie_JifenUrl);

			// 获取服务器应用地址
			GetDBdata db = new GetDBdata();
			String[] temp3 = { "FrontServerContext" };
			String url = db.getOneValue(
					"select value from zdconfig where type=?", temp3);
			if (url.endsWith("/")) {
				cookie.setPath(url);
				cookie1.setPath(url);
				// 针对QQ彩贝的cookie
				cookid_headshow.setPath(url);
				cookie_JifenUrl.setPath(url);
			} else {
				cookie.setPath(url + "/");
				cookie1.setPath(url + "/");
				// 针对QQ彩贝的cookie
				cookid_headshow.setPath(url + "/");
				cookie_JifenUrl.setPath(url + "/");
			}
			cookie.setMaxAge(0);
			getResponse().addCookie(cookie1);
			cookie1.setMaxAge(0);
			getResponse().addCookie(cookie);

			// 针对QQ彩贝的cookie
			cookid_headshow.setMaxAge(0);
			getResponse().addCookie(cookid_headshow);
			cookie_JifenUrl.setMaxAge(0);
			getResponse().addCookie(cookid_headshow);
			jsonMap.put("status", "1");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			jsonMap.put("status", "0");
		}
		// return ajaxJson("true");
		return ajaxHtml(jsonpname + "("
				+ JSONObject.fromObject(jsonMap).toString() + ");");
	}

	// 获取注册协议内容
	public String getAgreement() {

		return ajaxText(agreementService.getAgreement().getContent());
	}

	// 检查用户名是否存在
	public String checkUsername() {

		try {
			String username = member.getUsername();
			if (memberService.isExistByUsername(username)) {
				return ajaxText("false");
			} else {
				return ajaxText("true");
			}
		} catch (Exception e) {
			return ajaxText("false");
		}
	}

	// Ajax会员注册保存
	private String registerUserName;

	public String getRegisterUserName() {

		return registerUserName;
	}

	public void setRegisterUserName(String registerUserName) {

		this.registerUserName = registerUserName;
	}

	/**
	 * 新注册流程
	 * 
	 * @author fhz
	 * **/
	public String newRegister() throws Exception {

		try {
			if (StringUtils.isEmpty(getRequest().getParameter(
					JCaptchaEngine.CAPTCHA_INPUT_NAME))) {
				addActionError("验证码没有写对哟");
				return ERROR;
			}
			// log.info(registerUserName);
			if (!StringUtil.isMail(registerUserName)
					&& !StringUtil.isMobileNO(registerUserName)) {
				addActionError("正确的邮箱地址或手机号是登录的唯一凭证哟！");
				return ERROR;
			}

			if (StringUtil.isMail(registerUserName)) {// 邮箱注册
				try {
					if (memberService.isExistByMailbox(registerUserName)) {
						addActionError("邮箱已被注册！");
						return ERROR;
					}
				} catch (Exception e) {
					addActionError("矮油，出了点小状况,点击<a vlpageid=\"xiaoneng\" exturl=\"http://www.kaixinbao.com/xiaoneng\" id=\"qqwap2\" onclick=\"return(VL_FileDL(this));return false;\" href=\"javascript:void(0);\"><这里></a>！请客服MM帮帮忙吧！");
					return ERROR;
				}
				this.registerName = registerUserName;
				member.setEmail(registerUserName);
				member.setRegisterType("0");// 表示邮箱注册
			}
			if (StringUtil.isMobileNO(registerUserName)) {// 手机注册
				try {
					if (memberService.isExistByMobileNO(registerUserName)) {
						addActionError("这个手机号已经注册过了！");
						return ERROR;
					}
				} catch (Exception e) {
					addActionError("矮油，出了点小状况,点击<a vlpageid=\"xiaoneng\" exturl=\"http://www.kaixinbao.com/xiaoneng\" id=\"qqwap2\" onclick=\"return(VL_FileDL(this));return false;\" href=\"javascript:void(0);\"><这里></a>！请客服MM帮帮忙吧！");
					return ERROR;
				}
				this.registerName = registerUserName;
				member.setMobileNO(registerUserName);
				member.setRegisterType("1");// 表示手机注册
			}
			if (member.getPassword().length() > 16
					|| member.getPassword().length() < 6) {
				addActionError("密码需在6-16位之间！");
				return ERROR;

			}
			if (member.getPassword().indexOf(" ") != -1) {
				addActionError("密码是不能填写空格的，改一下吧！");
				return ERROR;
			}
			if (StringUtil.isEmpty(rePassword)) {
				addActionError("密码确认不能为空！");
				return ERROR;

			}
			if (!rePassword.equals(member.getPassword())) {
				addActionError("两次密码输入不一致！");
				return ERROR;
			}

			if (isAgreeAgreement == null || isAgreeAgreement == false) {
				addActionError("小手勾选同意协议，成功注册so easy！");
				return ERROR;

			}
			if (!getSystemConfig().getIsRegister()) {
				addActionError("亲，本站暂时不支持会员注册，敬请期待开放！");
				return ERROR;
			}

			int count = 0;
			if (!(member.getAddress() == null || "".equals(member.getAddress())))
				count++;
			if (!(member.getBirthday() == null || "".equals(member
					.getBirthday())))
				count++;
			if (!(member.getEmail() == null || "".equals(member.getEmail())))
				count++;
			if (!(member.getFaxNO() == null || "".equals(member.getFaxNO())))
				count++;
			if (love != null)
				count++;
			if (!(member.getIDNO() == null || "".equals(member.getIDNO())))
				count++;
			if (!(member.getIDType() == null || "".equals(member.getIDType()) || "-1"
					.equals(member.getIDType())))
				count++;
			if (!(member.getIndustryType() == null || "".equals(member
					.getIndustryType())))
				count++;
			if (!(member.getLocation() == null || "".equals(member
					.getLocation())))
				count++;
			if (!(member.getMarriageStatus() == null
					|| "".equals(member.getMarriageStatus()) || "-1"
						.equals(member.getIDType())))
				count++;
			if (!(member.getMobileNO() == null || "".equals(member
					.getMobileNO())))
				count++;
			if (!(member.getPersonalURL() == null || "".equals(member
					.getPersonalURL())))
				count++;
			if (!(member.getPosition() == null || "".equals(member
					.getPosition())))
				count++;
			if (!(member.getQQNO() == null || "".equals(member.getQQNO())))
				count++;
			if (!(member.getRealName() == null || "".equals(member
					.getRealName())))
				count++;
			if (!(member.getSex() == null || "".equals(member.getSex())))
				count++;
			if (!(member.getTelephoneNO() == null || "".equals(member
					.getTelephoneNO())))
				count++;
			if (!(member.getUsername() == null || "".equals(member
					.getUsername())))
				count++;
			if (!(member.getVIPType() == null
					|| "".equalsIgnoreCase(member.getVIPType()) || "-1"
						.equals(member.getIDType())))
				count++;
			if (!(member.getZipcode() == null || "".equals(member.getZipcode())))
				count++;
			double a = (count / 20.0) * 100;
			DecimalFormat myformat = new DecimalFormat("#####0");
			String fullDegree = myformat.format(a) + "%";
			member.setFullDegree(fullDegree);

			member.setPassword(DigestUtils.md5Hex(member.getPassword()));
			member.setSafeQuestion(null);
			member.setSafeAnswer(null);
			member.setMemberRank(memberRankService.getDefaultMemberRank());
			member.setPoint(0);
			member.setDeposit(new BigDecimal("0"));
			member.setIsAccountEnabled("Y");
			member.setIsAccountLocked("N");
			member.setLoginFailureCount(0);
			member.setPasswordRecoverKey(null);
			member.setLockedDate(null);
			member.setLoginDate(new Date());
			member.setRegisterIp(getRequest().getRemoteAddr());
			member.setLoginIp(getRequest().getRemoteAddr());
			member.setMemberAttributeMap(null);
			member.setReceiverSet(null);
			member.setFavoriteProductSet(null);
			member.setUsedPoint(0);
			member.setCurrentValidatePoint(0);
			member.setExpiricalValue(0);
			// fhz 这里都没有验证
			member.setIsMobileNOBinding("N");
			member.setIsEmailBinding("N");
			if(StringUtil.isEmpty(member.getEmail())){
				member.setEmail("");
			}
			if(StringUtil.isEmpty(member.getMobileNO())){
				member.setMobileNO("");
			}
			if(StringUtil.isEmpty(member.getmBindInfoForLogin())){
				BindInfoForLogin bindInfoForLogin = new BindInfoForLogin();
				bindInfoForLogin.setId("");
				member.setmBindInfoForLogin(bindInfoForLogin);
			}
			memberService.save(member);

			if ("0".equals(member.getRegisterType())
					&& member.getVIPFrom().equals("0")) {
				// 发送邮件日期
				member.setVerifyEmailSendDate(new Date());
				memberService.save(member);// 两个save fhz 这里不报错么？？
				if (!sendVerifyEMail(member)) {
					logger.error("发送验证邮件失败！");
				}

				Map<String, Object> map = new HashMap<String, Object>();
				if (member.getRealName() != null
						&& !member.getRealName().equals("")) {
					map.put("MemberName", member.getRealName());
				} else if (member.getUsername() != null
						&& !member.getUsername().equals("")) {
					map.put("MemberNameMemberName", member.getUsername());
				} else {
					map.put("MemberName", "");
				}

				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				int rigisterDate = Integer.parseInt(sdf.format(new Date()));
				map.put("rigisterDate", rigisterDate);
				map.put("Member", member);
				try {
					Map<String,Object> mailParam = new HashMap<String,Object>();
					mailParam.put("memberId", member.getId());
					ActionUtil.sendMessage("wj0003", mailParam);
					ActionUtil.dealAction("wj0003", map, getRequest());
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}

			} else if ("1".equals(member.getRegisterType())
					&& member.getVIPFrom().equals("0")) {

				memberService.save(member);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("Member", member);
				try {
					ActionUtil.sendSms("wj0004", member.getMobileNO(), "");
					ActionUtil.dealAction("wj0004", map, getRequest());
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}

			if (member.getVIPFrom().equals("1")) {// 表示操作后注册
				// if (codeType.equals("0")) {
				// SDOrder sdorder = sdorderService.getOrderByOrderSn(serialNO);
				// order.setMemberId(member.getId());
				// orderService.update(order);
				// member.setCurrentValidatePoint(order.getPoint());
				// memberService.update(member);
				//
				// }
				if (codeType.equals("1")) {// 车险一键报价
					SDtargetInformation sdTargetInformation = sdTargetInformationService
							.get(serialNO);
					sdTargetInformation.setMemberId(member.getId());
					sdTargetInformationService.update(sdTargetInformation);
					ShowInsurance showInsurance = showInsuranceService
							.get(serialNO);
					showInsurance.setMemberId(member.getId());
					showInsuranceService.update(showInsurance);

				}

			}
			JdbcTemplateData jtd = new JdbcTemplateData();
			String sql3 = "select codevalue from zdcode where codetype=? and parentcode=? order by codevalue asc";
			String[] sql3temp = { "member.Hobby", "hobby" };
			List<Map> listhy;
			try {
				listhy = jtd.obtainData(sql3, sql3temp);
				Iterator<Map> it = listhy.iterator();
				while (it.hasNext()) {
					MemberHobby mh = new MemberHobby();
					mh.setCodeValue((String) it.next().get("CodeValue"));
					mh.setIsSelected("N");
					mh.setMember(member);
					memberHobbyService.save(mh);
				}
			} catch (InstantiationException e) {
				logger.error(e.getMessage(), e);
			} catch (IllegalAccessException e) {
				logger.error(e.getMessage(), e);
			} catch (ClassNotFoundException e) {
				logger.error(e.getMessage(), e);
			}

			if (love != null && love.length > 0) {
				for (String s : love) {
					String sql5 = "update memberhobby set isselected='Y' where codevalue=? and  member_id=?";
					String[] sql5temp = { s, member.getId() };
					JdbcTemplateData jtdss = new JdbcTemplateData();
					jtdss.updateOrSaveOrDelete(sql5, sql5temp);
				}
			}
			// 写入会员登录Session
			setSession(Member.LOGIN_MEMBER_ID_SESSION_NAME, member.getId());

			if (member.getUsername() != null
					&& !"".equals(member.getUsername())) {
				registerName = member.getUsername();
			}
			// 写入会员登录Cookie
			Cookie loginMemberCookie = new Cookie(
					Member.LOGIN_MEMBER_USERNAME_COOKIE_NAME,
					URLEncoder.encode(registerName, "UTF-8"));
			loginMemberCookie.setPath(getRequest().getContextPath() + "/");
			getResponse().addCookie(loginMemberCookie);
			GetDBdata db = new GetDBdata();
			try {
				String[] temp4 = { "FrontServerContext" };
				String url = db.getOneValue(
						"select value from zdconfig where type=?", temp4);
				if (url.endsWith("/")) {
					loginMemberCookie.setPath(url);
				} else {
					loginMemberCookie.setPath(url + "/");
				}
				loginMemberCookie.setMaxAge(30 * 100000);
				getResponse().addCookie(loginMemberCookie);
			} catch (Exception e) {
			}
			// 合并购物车
			Cookie[] cookies = getRequest().getCookies();
			if (cookies != null && cookies.length > 0) {
				for (Cookie cookie : cookies) {
					if (StringUtils.equalsIgnoreCase(cookie.getName(),
							CartItemCookie.CART_ITEM_LIST_COOKIE_NAME)) {
						if (StringUtils.isNotEmpty(cookie.getValue())) {
							JsonConfig jsonConfig = new JsonConfig();
							jsonConfig.setRootClass(CartItemCookie.class);
							JSONArray jsonArray = JSONArray.fromObject(cookie
									.getValue());
							List<CartItemCookie> cartItemCookieList = (List<CartItemCookie>) JSONSerializer
									.toJava(jsonArray, jsonConfig);
							for (CartItemCookie cartItemCookie : cartItemCookieList) {
								Product product = productService
										.load(cartItemCookie.getI());
								if (product != null) {
									CartItem cartItem = new CartItem();
									cartItem.setMember(member);
									cartItem.setProduct(product);
									cartItem.setQuantity(cartItemCookie.getQ());
									cartItemService.save(cartItem);
								}
							}
						}
					}
				}
			}
			/* 开始 jingrujia 1054 需求- 渠道统计2012-10-30 start */

			MemberChannel memberChannel = new MemberChannel();
			memberChannel.setMemberId(member.getId());
			String channelId = "";
			String channelWay = "";
			String keyWord = "";
			String channelTemp = (String) getSession("channelId");
			String channelWemp = (String) getSession("channelWay");

			if (StringUtil.isNotEmpty(channelTemp)
					|| StringUtil.isNotEmpty(channelWemp)) {
				channelWay = channelWemp;
				channelId = channelTemp;
			} else {
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
			if (StringUtil.isNotEmpty(channelId)) {

				memberChannel.setChannelId(channelId);
				memberChannel.setType("0");// 0—代表注册 1-代表支付
				memberChannel.setChannelWay(channelWay);
				memberChannel.setSubType(keyWord);
				memberChannelService.save(memberChannel);
			}

			/* 结束 jingrujia 1054 需求- 渠道统计2012-10-30 end */

			// 清空临时购物车Cookie
			Cookie cartItemCookie = new Cookie(
					CartItemCookie.CART_ITEM_LIST_COOKIE_NAME, null);
			cartItemCookie.setPath(getRequest().getContextPath() + "/");
			cartItemCookie.setMaxAge(0);
			getResponse().addCookie(cartItemCookie);
			/* zhangjinquan 11180 需求-注册增加统计页面-订单结果注册 2012-10-26 start */
			/*
			 * if (member.getVIPFrom().equals("1")) { return "index"; }
			 */

			// if(StringUtil.isNotEmpty(orderNo)){
			// Order order=orderService.getOrderByOrderSn(orderNo);
			// order.setMemberId(memberId);
			// }
			//
			String redirectionUrl = (String) getSession(Member.LOGIN_REDIRECTION_URL_SESSION_NAME);
			getRequest().getSession().removeAttribute(
					Member.LOGIN_REDIRECTION_URL_SESSION_NAME);
			/* zhangjinquan 11180 需求-注册增加统计页面-订单结果注册 2012-10-26 start */
			if (StringUtils.isEmpty(redirectionUrl)) {
				if (StringUtil.isEmpty(this.getWindowNewAddr())) {
					// return "memberCenterIndexAction";
					String sql = "select value from zdconfig where type= 'ServerContext'";
					String url = db.getOneValue(sql);
					String path = url + "/shop/member_center!index.action";
					this.setWindowNewAddr(path);
				}
			} else {
				this.setWindowNewAddr(redirectionUrl);
			}

			// return "validate_finish";
			GALog.saveGALogRecord(gaLogService, GALog.REGISTER_SUCCESS,
					member.getId(), null);
			/* meijunfeng 20121128002-需求系统开发需求申请单-CPA申请需求 2012-12-12 start */
			RegistSuccessInfoCommit.infoCommitForEmar(getRequest(), member);
			/* meijunfeng 20121128002-需求系统开发需求申请单-CPA申请需求 2012-12-12 end */
			// return "registerGA";
			return "register_success";
			/* zhangjinquan 11180 需求-注册增加统计页面-订单结果注册 2012-10-26 end */
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionError(e.getMessage());
			return ERROR;
		}
	}

	/**
	 * 新注册流程 ajax
	 * 
	 * @author fhz
	 * **/
	public String newAjaxRegister() throws Exception {
		// 记录同一个IP注册的次数
		remarkRegisterCount();
		try {
			boolean remark = true;
			try {
				String captchaID = getRequest().getSession().getId();
				String challengeResponse = StringUtils.upperCase(getRequest()
						.getParameter(JCaptchaEngine.CAPTCHA_INPUT_NAME));
				if (StringUtils.isEmpty(challengeResponse)
						|| captchaService.validateResponseForID(captchaID,
								challengeResponse) == false) {
					// 记录注册错误次数
					remark = shopRemarkError("Y");
					if (!remark || !checkIPRegister(receiveUserIp(), 10)) {
						return ajaxJsonErrorMessage("验证码没有写对哟");
					}
				}
				
			} catch (CaptchaServiceException ce) {
				logger.error(ce.getMessage(), ce);
				return ajaxJsonErrorMessage("验证码没有写对哟");
			} catch (Exception e) {
				// 记录注册错误次数
				if (!remark) {
					return ajaxJsonErrorMessage("验证码没有写对哟");
				}
			}

			if (!StringUtil.isMail(registerUserName)
					&& !StringUtil.isMobileNO(registerUserName)) {
				// 记录注册错误次数
				shopRemarkError("");
				return ajaxJsonErrorMessage("正确的邮箱地址或手机号是登录的唯一凭证哟");
			}
			
			if (StringUtil.isMail(registerUserName)) {// 邮箱注册
				try {
					if (memberService.isExistByMailbox(registerUserName)) {
						// 记录注册错误次数
						shopRemarkError("");
						return ajaxJsonErrorMessage("邮箱已被注册");
					}
				} catch (Exception e) {
					return ajaxJsonErrorMessage("矮油，出了点小状况，请客服MM帮帮忙吧！");
				}
				this.registerName = registerUserName;
				member.setEmail(registerUserName);
				member.setRegisterType("0");// 表示邮箱注册
			}
			if (StringUtil.isMobileNO(registerUserName)) {// 手机注册
				try {
					if (memberService.isExistByMobileNO(registerUserName)) {
						// 记录注册错误次数
						shopRemarkError("");
						return ajaxJsonErrorMessage("这个手机号已经注册过了");
					}
				} catch (Exception e) {
					return ajaxJsonErrorMessage("矮油，出了点小状况，请客服MM帮帮忙吧！");
				}
				this.registerName = registerUserName;
				member.setMobileNO(registerUserName);
				member.setRegisterType("1");// 表示手机注册
			}
			if (member.getPassword().length() > 16
					|| member.getPassword().length() < 6) {
				return ajaxJsonErrorMessage("您的密码太短了，灰常不安全");

			}
			if (member.getPassword().indexOf(" ") != -1) {
				// 记录注册错误次数
				shopRemarkError("");
				return ajaxJsonErrorMessage("密码是不能填写空格的，改一下吧");
			}
			if (StringUtil.isEmpty(rePassword)) {
				// 记录注册错误次数
				shopRemarkError("");
				return ajaxJsonErrorMessage("密码确认不能为空");

			}
			if (!rePassword.equals(member.getPassword())) {
				// 记录注册错误次数
				shopRemarkError("");
				return ajaxJsonErrorMessage("两次密码输入不一致");

			}

			if (isAgreeAgreement == null || isAgreeAgreement == false) {
				return ajaxJsonErrorMessage("小手勾选同意协议，成功注册so easy");

			}
			if (!getSystemConfig().getIsRegister()) {
				return ajaxJsonErrorMessage("亲，本站暂时不支持会员注册，敬请期待开放");
			}

			int count = 0;
			if (!(member.getEmail() == null || "".equals(member.getEmail())))
				count += 15;
			if (!(member.getMobileNO() == null || "".equals(member
					.getMobileNO())))
				count += 15;
			String fullDegree = count + "%";
			member.setFullDegree(fullDegree);

			String pointScalerTime = Config.getValue("PointScalerTime");
			// 升级后注册的用户 无提示升级信息
			if (StringUtil.isEmpty(pointScalerTime) || DateUtil.compare(DateUtil.getCurrentDateTime(),
					pointScalerTime, DateUtil.Format_DateTime) >= 0) {
				member.setLoginDateAfterUngrade(new Date());
			}

			member.setPassword(DigestUtils.md5Hex(member.getPassword()));
			member.setSafeQuestion(null);
			member.setSafeAnswer(null);
			member.setMemberRank(memberRankService.getDefaultMemberRank());
			member.setPoint(0);
			member.setDeposit(new BigDecimal("0"));
			member.setIsAccountEnabled("Y");
			member.setIsAccountLocked("N");
			member.setLoginFailureCount(0);
			member.setPasswordRecoverKey(null);
			member.setLockedDate(null);
			member.setLoginDate(new Date());
			member.setRegisterIp(getRequest().getRemoteAddr());
			member.setLoginIp(getRequest().getRemoteAddr());
			member.setMemberAttributeMap(null);
			member.setReceiverSet(null);
			member.setFavoriteProductSet(null);
			member.setUsedPoint(0);
			member.setExpiricalValue(0);
			// fhz 这里都没有验证
			member.setIsMobileNOBinding("N");
			member.setIsEmailBinding("N");
			member.setRecommendMemId(rmemId);
			member.setPreLoginPoints(0);
			member.setCurrentValidatePoint(0);
			member.setGrade("K0");
			member.setFromWap("wj");
			member.setVipFlag("N");
			if(StringUtil.isEmpty(member.getEmail())){
				member.setEmail("");
			}
			if(StringUtil.isEmpty(member.getMobileNO())){
				member.setMobileNO("");
			}
			BindInfoForLogin bindInfoForLogin = new BindInfoForLogin();
			bindInfoForLogin.setId("");
			member.setmBindInfoForLogin(bindInfoForLogin);
			memberService.save(member);

			if ("0".equals(member.getRegisterType())
					&& member.getVIPFrom().equals("0")) {
				// 发送邮件日期
				member.setVerifyEmailSendDate(new Date());
				memberService.save(member);
				if (!sendVerifyEMail(member)) {
					logger.error("发送验证邮件失败！");
				}
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("Member", member);
				try {
					Map<String,Object> mailParam = new HashMap<String,Object>();
					mailParam.put("memberId", member.getId());
					ActionUtil.sendMessage("wj0003", mailParam);
					ActionUtil.dealAction("wj0003", map, getRequest());
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}

			} else if ("1".equals(member.getRegisterType())
					&& member.getVIPFrom().equals("0")) {

				memberService.save(member);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("Member", member);
				try {
					ActionUtil.sendSms("wj0004", member.getMobileNO(), "");
					ActionUtil.dealAction("wj0004", map, getRequest());
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
			if (member.getVIPFrom().equals("1")) {// 表示操作后注册
				// if (codeType.equals("0")) {
				// Order order = orderService.getOrderByOrderSn(serialNO);
				// order.setMemberId(member.getId());
				// orderService.update(order);
				// member.setCurrentValidatePoint(order.getPoint());
				// memberService.update(member);
				//
				// }
				if (codeType.equals("1")) {// 车险一键报价
					SDtargetInformation sdTargetInformation = sdTargetInformationService
							.get(serialNO);
					sdTargetInformation.setMemberId(member.getId());
					sdTargetInformationService.update(sdTargetInformation);
					ShowInsurance showInsurance = showInsuranceService
							.get(serialNO);
					showInsurance.setMemberId(member.getId());
					showInsuranceService.update(showInsurance);

				}

			}
			JdbcTemplateData jtd = new JdbcTemplateData();
			String sql3 = "select codevalue from zdcode where codetype=? and parentcode=? order by codevalue asc";
			String[] sql3temp = { "member.Hobby", "hobby" };
			List<Map> listhy;
			try {
				listhy = jtd.obtainData(sql3, sql3temp);
				Iterator<Map> it = listhy.iterator();
				while (it.hasNext()) {
					MemberHobby mh = new MemberHobby();
					mh.setCodeValue((String) it.next().get("CodeValue"));
					mh.setIsSelected("N");
					mh.setMember(member);
					memberHobbyService.save(mh);
				}
			} catch (InstantiationException e) {
				logger.error(e.getMessage(), e);
			} catch (IllegalAccessException e) {
				logger.error(e.getMessage(), e);
			} catch (ClassNotFoundException e) {
				logger.error(e.getMessage(), e);
			}

			if (love != null && love.length > 0) {
				for (String s : love) {
					String sql5 = "update memberhobby set isselected='Y' where codevalue=? and  member_id=?";
					String[] sql5temp = { s, member.getId() };
					JdbcTemplateData jtdss = new JdbcTemplateData();
					jtdss.updateOrSaveOrDelete(sql5, sql5temp);
				}
			}
			// 写入会员登录Session
			setSession(Member.LOGIN_MEMBER_ID_SESSION_NAME, member.getId());

			if (member.getUsername() != null
					&& !"".equals(member.getUsername())) {
				registerName = member.getUsername();
			}
			// 写入会员登录Cookie
			Cookie loginMemberCookie = new Cookie(
					Member.LOGIN_MEMBER_USERNAME_COOKIE_NAME,
					URLEncoder.encode(registerName, "UTF-8"));
			loginMemberCookie.setPath(getRequest().getContextPath() + "/");
			getResponse().addCookie(loginMemberCookie);
			GetDBdata db = new GetDBdata();
			try {
				String[] temp = { "FrontServerContext" };
				String url = db.getOneValue(
						"select value from zdconfig where type=?", temp);
				if (url.endsWith("/")) {
					loginMemberCookie.setPath(url);
				} else {
					loginMemberCookie.setPath(url + "/");
				}
				loginMemberCookie.setMaxAge(30 * 100000);
				getResponse().addCookie(loginMemberCookie);
			} catch (Exception e) {
			}
			// 合并购物车
			Cookie[] cookies = getRequest().getCookies();
			if (cookies != null && cookies.length > 0) {
				for (Cookie cookie : cookies) {
					if (StringUtils.equalsIgnoreCase(cookie.getName(),
							CartItemCookie.CART_ITEM_LIST_COOKIE_NAME)) {
						if (StringUtils.isNotEmpty(cookie.getValue())) {
							JsonConfig jsonConfig = new JsonConfig();
							jsonConfig.setRootClass(CartItemCookie.class);
							JSONArray jsonArray = JSONArray.fromObject(cookie
									.getValue());
							List<CartItemCookie> cartItemCookieList = (List<CartItemCookie>) JSONSerializer
									.toJava(jsonArray, jsonConfig);
							for (CartItemCookie cartItemCookie : cartItemCookieList) {
								Product product = productService
										.load(cartItemCookie.getI());
								if (product != null) {
									CartItem cartItem = new CartItem();
									cartItem.setMember(member);
									cartItem.setProduct(product);
									cartItem.setQuantity(cartItemCookie.getQ());
									cartItemService.save(cartItem);
								}
							}
						}
					}
				}
			}
			/* 开始 jingrujia 1054 需求- 渠道统计2012-10-30 start */

			MemberChannel memberChannel = new MemberChannel();
			memberChannel.setMemberId(member.getId());
			String channelId = "";
			String channelWay = "";
			String keyWord = "";
			String channelTemp = (String) getSession("channelId");
			String channelWemp = (String) getSession("channelWay");

			if (StringUtil.isNotEmpty(channelTemp)
					|| StringUtil.isNotEmpty(channelWemp)) {
				channelWay = channelWemp;
				channelId = channelTemp;
			} else {
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
			if (StringUtil.isNotEmpty(channelId)) {

				memberChannel.setChannelId(channelId);
				memberChannel.setType("0");// 0—代表注册 1-代表支付
				memberChannel.setChannelWay(channelWay);
				memberChannel.setSubType(keyWord);
				memberChannelService.save(memberChannel);
			}
			/* 结束 jingrujia 1054 需求- 渠道统计2012-10-30 end */

			// 清空临时购物车Cookie
			Cookie cartItemCookie = new Cookie(
					CartItemCookie.CART_ITEM_LIST_COOKIE_NAME, null);
			cartItemCookie.setPath(getRequest().getContextPath() + "/");
			cartItemCookie.setMaxAge(0);
			getResponse().addCookie(cartItemCookie);
			/* zhangjinquan 11180 需求-注册增加统计页面-订单结果注册 2012-10-26 start */
			/*
			 * if (member.getVIPFrom().equals("1")) { return "index"; }
			 */

			// if(StringUtil.isNotEmpty(orderNo)){
			// Order order=orderService.getOrderByOrderSn(orderNo);
			// order.setMemberId(memberId);
			// }
			//
			String redirectionUrl = (String) getSession(Member.LOGIN_REDIRECTION_URL_SESSION_NAME);
			getRequest().getSession().removeAttribute(
					Member.LOGIN_REDIRECTION_URL_SESSION_NAME);
			/* zhangjinquan 11180 需求-注册增加统计页面-订单结果注册 2012-10-26 start */
			if (StringUtils.isEmpty(redirectionUrl)) {
				if (StringUtil.isEmpty(this.getWindowNewAddr())) {
					String sql = "select value from zdconfig where type= 'ServerContext'";
					String url = db.getOneValue(sql);
					String path = url + "/shop/member_center!index.action";
					this.setWindowNewAddr(path);
				}
			} else {
				this.setWindowNewAddr(redirectionUrl);
			}

			GALog.saveGALogRecord(gaLogService, GALog.REGISTER_SUCCESS,
					member.getId(), null);
			/* meijunfeng 20121128002-需求系统开发需求申请单-CPA申请需求 2012-12-12 start */
			RegistSuccessInfoCommit.infoCommitForEmar(getRequest(), member);
			/* meijunfeng 20121128002-需求系统开发需求申请单-CPA申请需求 2012-12-12 end */
			// 触发活动
			QueryBuilder qb_activity = new QueryBuilder(
					"SELECT id from sdcouponactivityinfo  WHERE TYPE='0' AND STATUS='3' and DATE_FORMAT(endtime,'%Y-%m-%d %H:%i:%s') >= DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s') ");
			DataTable qt_activity = qb_activity.executeDataTable();
			if (qt_activity.getRowCount() == 1) {
				String mail = member.getEmail();
				String mobile = member.getMobileNO();
				// 发送优惠券
				provideCoupon(mail, mobile);
			} else if (qt_activity.getRowCount() > 1) {
				return ajaxJsonSuccessMessage("注册活动数量多于一个！");
			}
			
			return ajaxJsonSuccessMessage("注册成功！");

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ajaxJsonErrorMessage("矮油，出了点小状况，请客服MM帮帮忙吧！");
		}
	}

	/**
	 * 
	 * @Title: provideCoupon
	 * @Description: TODO(注册自动发送优惠券)
	 * @return String 返回类型
	 * @author zhangjing
	 */
	private String provideCoupon(String mail, String mobile) {

		// 公共参数
		Map<String, Object> data = new HashMap<String, Object>();
		SDCouponInfoSchema sdcouponschema = new SDCouponInfoSchema();
		QueryBuilder qb_coupon = new QueryBuilder(
				" SELECT cou.id AS id ,cou.status as status FROM couponinfo cou,sdcouponactivityinfo sdac WHERE sdac.TYPE='0' AND sdac.STATUS='3'  AND cou.batch=sdac.batch order by cou.status ");
		DataTable qt = qb_coupon.executeDataTable();
		// id值
		String coupon_id = qt.getString(0, 0);
		// 状态
		String status = qt.getString(0, 1);
		sdcouponschema.setId(coupon_id);
		Transaction trans = new Transaction();
		// 查询
		if (sdcouponschema.fill()) {
			if (StringUtil.isMail(mail)) {
				if (!("0".equals(status))) {
					// 生成优惠劵ID
					String year = DateUtil.getCurrentDateTime("yyyy");
					String id = year + NoUtil.getMaxNo("CouponSn", 12);
					// 优惠券ID值
					sdcouponschema.setId(id);
					// 优惠券号
					sdcouponschema.setCouponSn(DigestUtils.md5Hex(id));
					// 创建时间
					sdcouponschema.setCreateDate(new Date());
					// 创建者
					sdcouponschema.setCreateUser("admin");
				}
				// 状态2已发放
				sdcouponschema.setStatus("2");
				// 将会员id关联到优惠券表中
				sdcouponschema.setMemberId(member.getId());
				sdcouponschema.setMail(mail);
				// 更改人和更改时间
				String now = DateUtil.getCurrentDateTime();

				// 发放时间
				sdcouponschema.setProp2(now);
				if (!("0".equals(status))) {
					trans.add(sdcouponschema, Transaction.INSERT);
				} else {
					trans.add(sdcouponschema, Transaction.UPDATE);
				}
				if (trans.commit()) {
					// 发送邮件
					Member member = new Member();
					data.put("Member", member);
					// 优惠券说明
					try {
						data.put(
								"direction",
								java.net.URLDecoder.decode(
										sdcouponschema.getDirection(), "utf-8"));
					} catch (UnsupportedEncodingException e) {
						logger.error(e.getMessage(), e);
					}
					// 优惠金额
					data.put("parValue", sdcouponschema.getParValue());
					// 优惠券开始时间
					String starttime = sdcouponschema.getStartTime().toString();
					// 优惠券结束时间
					String endtime = sdcouponschema.getEndTime().toString();

					// 开始时间
					data.put("starttime", starttime.substring(0, 10));
					// 结束时间
					data.put("endtime", endtime.substring(0, 10));
					// 优惠券编号
					data.put("couponsn", sdcouponschema.getCouponSn());
					data.put("url", Config.getValue("FrontServerContextPath")
							+ "/wj/shop/coupon!queryCoupon.action");
					// 邮箱地址
					member.setEmail(mail);
                    // 如果需要信息提醒
                    if ("Y".equals(sdcouponschema.getRemindFlag())) {
                        // 非折扣券
                        if (StringUtil.isEmpty(sdcouponschema.getProp3()) || "01".equals(sdcouponschema.getProp3())) {
                            // 优惠金额
                            data.put("parValueShow", sdcouponschema.getParValue() + "元");

                            if (ActionUtil.sendMail("wj00088", mail,data)) {
                                logger.info("注册送优惠券活动，发送邮件成功");
                            } else {
                                logger.warn("注册送优惠券活动，发送邮件失败");
                            }
                        } else { // 折扣券
                            // 优惠金额
                            data.put("parValueShow", sdcouponschema.getProp4() + "折");

                            if (ActionUtil.sendMail("wj00113",mail, data)) {
                                logger.info("注册送优惠券活动，发送邮件成功");
                            } else {
                                logger.warn("注册送优惠券活动，发送邮件失败");
                            }
                        }
                    }
				} else {
					logger.warn("注册送优惠券活动，更改优惠券状态错误");
				}
			} else if (StringUtil.isMobileNO(mobile)) {
				if (!("0".equals(status))) {
					// 生成优惠劵ID
					String year = DateUtil.getCurrentDateTime("yyyy");
					String id = year + NoUtil.getMaxNo("CouponSn", 12);
					// 优惠券ID值
					sdcouponschema.setId(id);
					// 优惠券号
					sdcouponschema.setCouponSn(DigestUtils.md5Hex(id));
					// 创建时间
					sdcouponschema.setCreateDate(new Date());
					// 创建者
					sdcouponschema.setCreateUser("admin");
				}
				// 状态2已发放
				sdcouponschema.setStatus("2");
				// 将会员id关联到优惠券表中
				sdcouponschema.setMemberId(member.getId());
				sdcouponschema.setMobile(mobile);
				// 发放时间
				sdcouponschema.setProp2(DateUtil.getCurrentDateTime());
				if (!("0".equals(status))) {
					trans.add(sdcouponschema, Transaction.INSERT);
				} else {
					trans.add(sdcouponschema, Transaction.UPDATE);
				}
				if (trans.commit()) {
                    // 如果需要信息提醒
                    if ("Y".equals(sdcouponschema.getRemindFlag())) {
                        // 优惠券结束时间
                        String endtime = String
                                .valueOf(sdcouponschema.getEndTime());
                        String month = endtime.substring(5, 7);
                        if (month.startsWith("0")) {
                            month = month.substring(1, 2);
                        }
                        String day = endtime.substring(8, 10);
                        if (day.startsWith("0")) {
                            day = day.substring(1, 2);
                        }
                        // 优惠券发放发送短信动作代码为wj00090
                        String sendData = sdcouponschema.getParValue() + ";" + endtime.substring(0, 4) + "-" + month
                                + "-" + day;
                        if (ActionUtil.sendSms("wj00090", mobile, sendData)) {
                            logger.info("注册送优惠券活动，发送短信成功");
                        } else {
                            logger.warn("注册送优惠券活动，发送短信失败");
                        }
                    }
				}
			} else {
				logger.warn("注册送优惠券活动邮箱和手机号错误！");
			}
		}
		return "";
	}

	/**
	 * 联合登陆---登陆并绑定第三方账号
	 * 
	 * @return
	 * @throws Exception
	 */
	public String otherLogin() throws Exception {
		remarkLoginRequestCount();
		boolean remark = true;
		// 判断上次登录与这次登陆积分是否发生变化标志 0：无变化 1：发生变化
		String pointsChange = "0";
		try {
			String captchaID = getRequest().getSession().getId();
			String challengeResponse = StringUtils.upperCase(getRequest()
					.getParameter(JCaptchaEngine.CAPTCHA_INPUT_NAME));
			if (StringUtils.isEmpty(challengeResponse)
					|| captchaService.validateResponseForID(captchaID,
							challengeResponse) == false) {
				remark = shopRemarkError("Y");
				// 记录登录错误次数
				if (!remark || !queryLoginReqCountRemark(3) || !queryLoginSuccRemark(loginName)) {
					return ajaxJsonErrorMessage("验证码没有写对哟");
				}
			}
		} catch (CaptchaServiceException ce) {
			logger.error(ce.getMessage(), ce);
			return ajaxJsonErrorMessage("验证码没有写对哟");
		} catch (Exception e) {
			if (!remark) {
				return ajaxJsonErrorMessage("验证码没有写对哟");
			}
		}

		GetDBdata db = new GetDBdata();
		// 去掉用户名登录的控制
		String[] temp = { loginName, loginName };
		String firstLogin = db
				.getOneValue(
						"select 'X' from member where (loginDate is null or loginDate = '') and (email=? or mobileNo=?)",
						temp);
		if (loginName == null || "".equals(loginName)) {
			return ajaxJsonErrorMessage("请填写正确账号才行哦");
		}
		// 不需要绑定
		Member loginMember = memberService
				.getMemberByLoginNameNoBinding(loginName);
		if (loginMember != null) {
			// 解除会员账户锁定
			if ("Y".equalsIgnoreCase(loginMember.getIsAccountLocked())) {
				Date lockedDate = loginMember.getLockedDate();
				Date now = new Date();
				Calendar c1 = Calendar.getInstance();
				c1.setTime(lockedDate);
				Calendar c2 = Calendar.getInstance();
				c2.setTime(now);
				if (c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR) >= 1) {
					loginMember.setLoginFailureCount(0);
					loginMember.setIsAccountLocked("N");
					loginMember.setLockedDate(null);
					memberService.update(loginMember);
				}
			}
			if ("N".equalsIgnoreCase(loginMember.getIsAccountEnabled())) {
				return ajaxJsonErrorMessage("您的账号已被禁用,无法登录!");

			}
			// if ("Y".equalsIgnoreCase(loginMember.getIsAccountLocked())) {
			// return ajaxJsonErrorMessage("您的账号已被锁定,无法登录!");
			// }
			// 不需要绑定
			if (!memberService.verifyMemberNoNeedBinding(loginMember,
					member.getPassword())) {
				// 记录注册错误次数
				shopRemarkError("");
				return ajaxJsonErrorMessage("密码没有写对哟");
			}
		} else {
			// 记录注册错误次数
			shopRemarkError("");
			return ajaxJsonErrorMessage("您的用户名或密码错误");
		}

		if (loginMember.getPreLoginPoints() == null) {
			loginMember.setPreLoginPoints(0);
		}
		// 判断积分与上次登陆积分是否有变化
		if (loginMember.getCurrentValidatePoint().compareTo(loginMember.getPreLoginPoints()) != 0) {
			pointsChange = "1";
		}

		loginMember.setLoginFailureCount(0);
		loginMember.setLoginIp(getRequest().getRemoteAddr());
		loginMember.setLoginDate(new Date());
		loginMember.setPreLoginPoints(loginMember.getCurrentValidatePoint());
		if (bindInfoForLogin.getOpenID() != null
				&& !"".equals(bindInfoForLogin.getOpenID())) {
			bindInfoForLogin = mBindInfoForLoginService
					.getBindInfoForLoginByOpenID(bindInfoForLogin
							.getOpenID());

			String mBindInfoForLogin_id = new QueryBuilder("select mBindInfoForLogin_id from member where id=?",
					loginMember.getId()).executeString();
			if (StringUtil.isNotEmpty(mBindInfoForLogin_id) && !mBindInfoForLogin_id.equals(bindInfoForLogin.getId())) {
				shopRemarkError("");
				return ajaxJsonErrorMessage("您输入的账号已经绑定过" + loginMember.getmBindInfoForLogin().getComName());
			}
		}

		// 保存绑定信息
		SimpleDateFormat sdf_1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		bindInfoForLogin.setModifyDate(sdf_1.parse(PubFun.getCurrent()));
		bindInfoForLogin.setKxbUserEmail(loginMember.getEmail());
		bindInfoForLogin.setKxbUserPhone(loginMember.getMobileNO());
		bindInfoForLogin.setRegisterType(loginMember.getRegisterType());
		bindInfoForLogin.setmMember(loginMember);
		otherloginUseMailAndPhone();
		if (bindInfoForLogin.getId() != null
				&& !"".equals(bindInfoForLogin.getId())) {
			this.mBindInfoForLoginService.update(bindInfoForLogin);
		} else {
			this.mBindInfoForLoginService.save(bindInfoForLogin);
		}

		loginMember.setmBindInfoForLogin(bindInfoForLogin);
		memberService.update(loginMember);
		remarkLoginSuccCount(loginName);

		if (loginMember.getUsername() != null
				&& !"".equals(loginMember.getUsername())) {
			loginName = loginMember.getUsername();
		}

		// 写入会员登录Session
		setSession(Member.LOGIN_MEMBER_ID_SESSION_NAME, loginMember.getId());

		// 写入会员登录Cookie
		Cookie loginMemberUsernameCookie = new Cookie(
				Member.LOGIN_MEMBER_USERNAME_COOKIE_NAME, URLEncoder.encode(
						loginName.toLowerCase(), "UTF-8"));
		loginMemberUsernameCookie.setPath(getRequest().getContextPath() + "/");
		getResponse().addCookie(loginMemberUsernameCookie);

		// cookie 加入memberId
		Cookie loginMemberIdCookie = new Cookie(
				Member.LOGIN_MEMBER_ID_SESSION_NAME, loginMember.getId());
		loginMemberIdCookie.setPath(getRequest().getContextPath() + "/");
		getResponse().addCookie(loginMemberIdCookie);
		
		// FrontServerContext

		// 获取服务器应用地址
		try {
			String[] temp1 = { "FrontServerContext" };
			String url = db.getOneValue(
					"select value from zdconfig where type=?", temp1);
			if (url.endsWith("/")) {
				loginMemberUsernameCookie.setPath(url);
			} else {
				loginMemberUsernameCookie.setPath(url + "/");
			}
			loginMemberUsernameCookie.setMaxAge(30 * 100000);
			getResponse().addCookie(loginMemberUsernameCookie);
		} catch (Exception e) {
		}

		// 合并购物车
		Cookie[] cookies = getRequest().getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (StringUtils.equalsIgnoreCase(cookie.getName(),
						CartItemCookie.CART_ITEM_LIST_COOKIE_NAME)) {
					if (StringUtils.isNotEmpty(cookie.getValue())) {
						JsonConfig jsonConfig = new JsonConfig();
						jsonConfig.setRootClass(CartItemCookie.class);
						JSONArray jsonArray = JSONArray.fromObject(cookie
								.getValue());
						List<CartItemCookie> cartItemCookieList = (List<CartItemCookie>) JSONSerializer
								.toJava(jsonArray, jsonConfig);
						for (CartItemCookie cartItemCookie : cartItemCookieList) {
							Product product = productService
									.load(cartItemCookie.getI());
							if (product != null) {
								CartItem cartItem = new CartItem();
								cartItem.setMember(loginMember);
								cartItem.setProduct(product);
								cartItem.setQuantity(cartItemCookie.getQ());
								cartItemService.save(cartItem);
							}
						}
					}
				}
			}
		}

		// 清空临时购物车Cookie
		Cookie cartItemCookie = new Cookie(
				CartItemCookie.CART_ITEM_LIST_COOKIE_NAME, null);
		cartItemCookie.setPath(getRequest().getContextPath() + "/");
		cartItemCookie.setMaxAge(0);
		getResponse().addCookie(cartItemCookie);

		Cookie[] ChannelCookie1 = getRequest().getCookies();

		/* 开始 */
		MemberChannel memberChannel = new MemberChannel();
		memberChannel.setMemberId(loginMember.getId());
		String channelId = "";
		String channelTemp = (String) getSession("channelId");
		if (StringUtil.isNotEmpty(channelTemp)) {
			channelId = channelTemp;
		} else {
			Cookie[] ChannelCookie = getRequest().getCookies();
			for (Cookie cookie : ChannelCookie) {
				if ("channelId".equals(cookie.getName())) {
					channelId = cookie.getValue();
				}
			}
		}
		if (StringUtil.isNotEmpty(channelId)) {

			memberChannel.setChannelId(channelId);
			memberChannel.setType("0");
			memberChannel.setCreateDate(new Date());
			memberChannelService.save(memberChannel);
		}

		/* 结束 */

		// 调用横向接口
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Member", loginMember);
		try {
			ActionUtil.dealAction("wj00011", map, getRequest());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		String message = "";
		try {
			SDExpCalendarSchema tSDExpCalendarSchema = new SDExpCalendarSchema();
			SDExpCalendarSet tSDExpCalendarSet = tSDExpCalendarSchema
					.query(new QueryBuilder("where memberid ='"
							+ loginMember.getId() + "' "));

			message = "会员登录成功！";
			fullDegreeSess();
			setSession(receiveUserIp(), "");
			/*
			 * SDExpCalendarSchema tSDExpCalendarSchema1 = tSDExpCalendarSet
			 * .get(tSDExpCalendarSet.size() - 1); String exp =
			 * tSDExpCalendarSchema1.getExperience(); if (Long.parseLong(exp) ==
			 * 0) { message = "会员登录成功！"; } else { message = "会员登录成功！恭喜您获得" + exp
			 * + "经验值!"; }
			 */

		} catch (Exception e) {
			message = "会员登录成功！";
		}
		try {
			String judge = getParameter("judge");
			String orderSn = getParameter("orderSn");
			// 判断登录会员与购买会员是否相同
			if ("judge".equals(judge)) {
				SDOrder sdorder = sdorderService.getOrderByOrderSn(orderSn);
				String memberId = sdorder.getMemberId();
				// 不相同跳转到会员中心
				if (!memberId.equals(loginMember.getId())) {
					Map<String, String> jsonMap = new HashMap<String, String>();
					jsonMap.put(STATUS, "memberCenter");
					jsonMap.put(MESSAGE, message);
					JSONObject jsonObject = JSONObject.fromObject(jsonMap);
					return ajax(jsonObject.toString(), "text/html");
				}
				// 相同跳转到支付成功页面
				else {
					String redirectionJudge = (String) getSession(Member.LOGIN_REDIRECTION_URL_SESSION_NAME);
					getRequest().getSession().removeAttribute(
							Member.LOGIN_REDIRECTION_URL_SESSION_NAME);
					// 如果为空跳转到会员中心
					if (StringUtils.isEmpty(redirectionJudge)) {
						String sql = "select value from zdconfig where type= 'ServerContext'";
						String path = db.getOneValue(sql);
						message = path + "/shop/member_center!index.action";
					} else {
						message = redirectionJudge;
					}

					Map<String, String> jsonMap = new HashMap<String, String>();
					jsonMap.put(STATUS, "MailJump");
					jsonMap.put(MESSAGE, message);
					JSONObject jsonObject = JSONObject.fromObject(jsonMap);
					return ajax(jsonObject.toString(), "text/html");
				}
			}

			if (firstLogin != null && "X".equals(firstLogin)) {
				Map<String, String> jsonMap = new HashMap<String, String>();
				jsonMap.put(STATUS, CONTENT);
				jsonMap.put(MESSAGE, message);
				JSONObject jsonObject = JSONObject.fromObject(jsonMap);
				return ajax(jsonObject.toString(), "text/html");
			} else {
				return ajaxJsonSuccessMessage(message, pointsChange, loginMember);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ajaxJsonSuccessMessage(message, pointsChange, null);
		}
	}

	/**
	 * 联合登陆---电话号和邮箱不能为null
	 * 
	 * @return
	 * @throws Exception
	 */
	public void otherloginUseMailAndPhone(){
		if(StringUtil.isNotEmpty(bindInfoForLogin.getId())){
			if(StringUtil.isEmpty(bindInfoForLogin.getKxbUserEmail())){
				bindInfoForLogin.setKxbUserEmail("");
			}
			if(StringUtil.isEmpty(bindInfoForLogin.getKxbUserPhone())){
				bindInfoForLogin.setKxbUserPhone("");
			}
			if(StringUtil.isEmpty(bindInfoForLogin.getUserEmail())){
				bindInfoForLogin.setUserEmail("");
			}
			if(StringUtil.isEmpty(bindInfoForLogin.getUserPhone())){
				bindInfoForLogin.setUserPhone("");
			}
		}else{
			bindInfoForLogin.setId("");
		}
	}
	/**
	 * 联合登陆---注册并绑定第三方账号
	 * 
	 * @return
	 * @throws Exception
	 */
	public String otherRegister() throws Exception {
		remarkRegisterCount();
		try {

			boolean remark = true;
			try {
				String captchaID = getRequest().getSession().getId();
				String challengeResponse = StringUtils.upperCase(getRequest()
						.getParameter(JCaptchaEngine.CAPTCHA_INPUT_NAME));
				if (StringUtils.isEmpty(challengeResponse)
						|| captchaService.validateResponseForID(captchaID,
								challengeResponse) == false) {
					// 记录注册错误次数
					remark = shopRemarkError("Y");
					if (!remark || !checkIPRegister(receiveUserIp(), 10)) {
						return ajaxJsonErrorMessage("验证码没有写对哟");
					}
				}
				
			} catch (CaptchaServiceException ce) {
				logger.error(ce.getMessage(), ce);
				return ajaxJsonErrorMessage("验证码没有写对哟");
			} catch (Exception e) {
				// 记录注册错误次数
				if (!remark) {
					return ajaxJsonErrorMessage("验证码没有写对哟");
				}
			}
			
			if (!StringUtil.isMail(registerUserName)
					&& !StringUtil.isMobileNO(registerUserName)) {
				return ajaxJsonErrorMessage("正确的邮箱地址或手机号是登录的唯一凭证哟");
			}

			if (StringUtil.isMail(registerUserName)) {// 邮箱注册
				try {
					if (memberService.isExistByMailbox(registerUserName)) {
						return ajaxJsonErrorMessage("邮箱已被注册");
					}
				} catch (Exception e) {
					return ajaxJsonErrorMessage("矮油，出了点小状况，请客服MM帮帮忙吧！");
				}
				this.registerName = registerUserName;
				member.setEmail(registerUserName);
				member.setRegisterType("0");// 表示邮箱注册
			}
			if (StringUtil.isMobileNO(registerUserName)) {// 手机注册
				try {
					if (memberService.isExistByMobileNO(registerUserName)) {
						return ajaxJsonErrorMessage("这个手机号已经注册过了");
					}
				} catch (Exception e) {
					return ajaxJsonErrorMessage("矮油，出了点小状况，请客服MM帮帮忙吧！");
				}
				this.registerName = registerUserName;
				member.setMobileNO(registerUserName);
				member.setRegisterType("1");// 表示手机注册
			}
			if (member.getPassword().length() > 16
					|| member.getPassword().length() < 6) {
				return ajaxJsonErrorMessage("您的密码太短了，灰常不安全");

			}
			if (member.getPassword().indexOf(" ") != -1) {
				return ajaxJsonErrorMessage("密码是不能填写空格的，改一下吧");
			}
			if (StringUtil.isEmpty(rePassword)) {
				return ajaxJsonErrorMessage("密码确认不能为空");

			}
			if (!rePassword.equals(member.getPassword())) {
				return ajaxJsonErrorMessage("两次密码输入不一致");

			}

			if (isAgreeAgreement == null || isAgreeAgreement == false) {
				return ajaxJsonErrorMessage("小手勾选同意协议，成功注册so easy");

			}
			if (!getSystemConfig().getIsRegister()) {
				return ajaxJsonErrorMessage("亲，本站暂时不支持会员注册，敬请期待开放");
			}

			int count = 0;
			if (!(member.getAddress() == null || "".equals(member.getAddress())))
				count++;
			if (!(member.getBirthday() == null || "".equals(member
					.getBirthday())))
				count++;
			if (!(member.getEmail() == null || "".equals(member.getEmail())))
				count++;
			if (!(member.getFaxNO() == null || "".equals(member.getFaxNO())))
				count++;
			if (love != null)
				count++;
			if (!(member.getIDNO() == null || "".equals(member.getIDNO())))
				count++;
			if (!(member.getIDType() == null || "".equals(member.getIDType()) || "-1"
					.equals(member.getIDType())))
				count++;
			if (!(member.getIndustryType() == null || "".equals(member
					.getIndustryType())))
				count++;
			if (!(member.getLocation() == null || "".equals(member
					.getLocation())))
				count++;
			if (!(member.getMarriageStatus() == null
					|| "".equals(member.getMarriageStatus()) || "-1"
						.equals(member.getIDType())))
				count++;
			if (!(member.getMobileNO() == null || "".equals(member
					.getMobileNO())))
				count++;
			if (!(member.getPersonalURL() == null || "".equals(member
					.getPersonalURL())))
				count++;
			if (!(member.getPosition() == null || "".equals(member
					.getPosition())))
				count++;
			if (!(member.getQQNO() == null || "".equals(member.getQQNO())))
				count++;
			if (!(member.getRealName() == null || "".equals(member
					.getRealName())))
				count++;
			if (!(member.getSex() == null || "".equals(member.getSex())))
				count++;
			if (!(member.getTelephoneNO() == null || "".equals(member
					.getTelephoneNO())))
				count++;
			if (!(member.getUsername() == null || "".equals(member
					.getUsername())))
				count++;
			if (!(member.getVIPType() == null
					|| "".equalsIgnoreCase(member.getVIPType()) || "-1"
						.equals(member.getIDType())))
				count++;
			if (!(member.getZipcode() == null || "".equals(member.getZipcode())))
				count++;
			double a = (count / 20.0) * 100;
			DecimalFormat myformat = new DecimalFormat("#####0");
			String fullDegree = myformat.format(a) + "%";
			member.setFullDegree(fullDegree);

			member.setPassword(DigestUtils.md5Hex(member.getPassword()));
			member.setSafeQuestion(null);
			member.setSafeAnswer(null);
			member.setMemberRank(memberRankService.getDefaultMemberRank());
			member.setPoint(0);
			member.setDeposit(new BigDecimal("0"));
			member.setIsAccountEnabled("Y");
			member.setIsAccountLocked("N");
			member.setLoginFailureCount(0);
			member.setPasswordRecoverKey(null);
			member.setLockedDate(null);
			member.setLoginDate(new Date());
			member.setRegisterIp(getRequest().getRemoteAddr());
			member.setLoginIp(getRequest().getRemoteAddr());
			member.setMemberAttributeMap(null);
			member.setReceiverSet(null);
			member.setFavoriteProductSet(null);
			member.setUsedPoint(0);
			member.setCurrentValidatePoint(0);
			// add by wangej 20151013 联合登录设置默认值 begin
			member.setGrade("K0");
			member.setVipFlag("N");
			// add by wangej 联合登录设置默认值 end
			member.setExpiricalValue(0);
			// fhz 这里都没有验证
			member.setIsMobileNOBinding("N");
			member.setIsEmailBinding("N");
			if (bindInfoForLogin.getOpenID() != null
					&& !"".equals(bindInfoForLogin.getOpenID())) {
				bindInfoForLogin = mBindInfoForLoginService
						.getBindInfoForLoginByOpenID(bindInfoForLogin
								.getOpenID());
				member.setUsername(bindInfoForLogin.getUserNickName());// 设置会员名称为QQ等的昵称
				member.setRealName(bindInfoForLogin.getUserNickName());// 设置会员真实名称为QQ等的昵称
			}

			// 处理头像信息
			UnionLoginUtil tUnionLoginUtil = new UnionLoginUtil();

			String imgPath = tUnionLoginUtil.getImage(
					bindInfoForLogin.getAvatar(), member);

			member.setHeadPicPath(imgPath);

			// 保存绑定信息
			// mBindInfoForLogin.setComCode("");
			// mBindInfoForLogin.setComName("");
			// mBindInfoForLogin.setKxbUserAccount("");
			SimpleDateFormat sdf_1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			bindInfoForLogin.setModifyDate(sdf_1.parse(PubFun.getCurrent()));
			bindInfoForLogin.setKxbUserEmail(member.getEmail());
			bindInfoForLogin.setKxbUserPhone(member.getMobileNO());
			bindInfoForLogin.setRegisterType(member.getRegisterType());
			bindInfoForLogin.setmMember(member);
			otherloginUseMailAndPhone();
			if (bindInfoForLogin.getId() != null
					&& !"".equals(bindInfoForLogin.getId())) {
				this.mBindInfoForLoginService.update(bindInfoForLogin);
			} else {
				this.mBindInfoForLoginService.save(bindInfoForLogin);
			}

			member.setmBindInfoForLogin(bindInfoForLogin);
			if(StringUtil.isEmpty(member.getEmail())){
				member.setEmail("");
			}
			if(StringUtil.isEmpty(member.getMobileNO())){
				member.setMobileNO("");
			}
			memberService.save(member);
			if ("0".equals(member.getRegisterType())) {
				// 发送邮件日期
				member.setVerifyEmailSendDate(new Date());
				memberService.save(member);
				if (!sendVerifyEMail(member)) {
					logger.error("发送验证邮件失败！");
				}

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("Member", member);
				try {
					Map<String,Object> mailParam = new HashMap<String,Object>();
					mailParam.put("memberId", member.getId());
					ActionUtil.sendMessage("wj0003", mailParam);
					ActionUtil.dealAction("wj0003", map, getRequest());
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}

			} else if ("1".equals(member.getRegisterType())) {

				memberService.save(member);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("Member", member);
				try {
					ActionUtil.sendSms("wj0004", member.getMobileNO(), "");
					ActionUtil.dealAction("wj0004", map, getRequest());
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}

			JdbcTemplateData jtd = new JdbcTemplateData();
			String sql3 = "select codevalue from zdcode where codetype=? and parentcode=? order by codevalue asc";
			String[] sql3temp = { "member.Hobby", "hobby" };
			List<Map> listhy;
			try {
				listhy = jtd.obtainData(sql3, sql3temp);
				Iterator<Map> it = listhy.iterator();
				while (it.hasNext()) {
					MemberHobby mh = new MemberHobby();
					mh.setCodeValue((String) it.next().get("CodeValue"));
					mh.setIsSelected("N");
					mh.setMember(member);
					memberHobbyService.save(mh);
				}
			} catch (InstantiationException e) {
				logger.error(e.getMessage(), e);
			} catch (IllegalAccessException e) {
				logger.error(e.getMessage(), e);
			} catch (ClassNotFoundException e) {
				logger.error(e.getMessage(), e);
			}

			if (love != null && love.length > 0) {
				for (String s : love) {
					String sql5 = "update memberhobby set isselected='Y' where codevalue=? and  member_id=?";
					String[] sql5temp = { s, member.getId() };
					JdbcTemplateData jtdss = new JdbcTemplateData();
					jtdss.updateOrSaveOrDelete(sql5, sql5temp);
				}
			}
			// 写入会员登录Session
			if ("Tencent".equals(bindInfoForLogin.getComCode())
					|| "Sina".equals(bindInfoForLogin.getComCode())
					|| "Alipay".equals(bindInfoForLogin.getComCode())) {
				setSession(Member.LOGIN_MEMBER_ID_SESSION_NAME, "tencent");
			} else {
				setSession(Member.LOGIN_MEMBER_ID_SESSION_NAME, member.getId());
			}

			if (member.getUsername() != null
					&& !"".equals(member.getUsername())) {
				registerName = member.getUsername();
			}

			// 写入会员登录Cookie
			Cookie loginMemberCookie = null;
			if ("Tencent".equals(bindInfoForLogin.getComCode())
					|| "Sina".equals(bindInfoForLogin.getComCode())
					|| "Alipay".equals(bindInfoForLogin.getComCode())) {
				loginMemberCookie = new Cookie(
						Member.LOGIN_MEMBER_USERNAME_COOKIE_NAME,
						URLEncoder.encode(bindInfoForLogin.getUserNickName(),
								"UTF-8"));
			} else {
				loginMemberCookie = new Cookie(
						Member.LOGIN_MEMBER_USERNAME_COOKIE_NAME,
						URLEncoder.encode(registerName, "UTF-8"));
			}

			loginMemberCookie.setPath(getRequest().getContextPath() + "/");
			getResponse().addCookie(loginMemberCookie);
			GetDBdata db = new GetDBdata();
			try {
				String[] temp = { "FrontServerContext" };
				String url = db.getOneValue(
						"select value from zdconfig where type=?", temp);
				if (url.endsWith("/")) {
					loginMemberCookie.setPath(url);
				} else {
					loginMemberCookie.setPath(url + "/");
				}
				loginMemberCookie.setMaxAge(30 * 100000);
				getResponse().addCookie(loginMemberCookie);
			} catch (Exception e) {
			}
			// 合并购物车
			Cookie[] cookies = getRequest().getCookies();
			if (cookies != null && cookies.length > 0) {
				for (Cookie cookie : cookies) {
					if (StringUtils.equalsIgnoreCase(cookie.getName(),
							CartItemCookie.CART_ITEM_LIST_COOKIE_NAME)) {
						if (StringUtils.isNotEmpty(cookie.getValue())) {
							JsonConfig jsonConfig = new JsonConfig();
							jsonConfig.setRootClass(CartItemCookie.class);
							JSONArray jsonArray = JSONArray.fromObject(cookie
									.getValue());
							List<CartItemCookie> cartItemCookieList = (List<CartItemCookie>) JSONSerializer
									.toJava(jsonArray, jsonConfig);
							for (CartItemCookie cartItemCookie : cartItemCookieList) {
								Product product = productService
										.load(cartItemCookie.getI());
								if (product != null) {
									CartItem cartItem = new CartItem();
									cartItem.setMember(member);
									cartItem.setProduct(product);
									cartItem.setQuantity(cartItemCookie.getQ());
									cartItemService.save(cartItem);
								}
							}
						}
					}
				}
			}
			/* 开始 jingrujia 1054 需求- 渠道统计2012-10-30 start */

			MemberChannel memberChannel = new MemberChannel();
			memberChannel.setMemberId(member.getId());
			String channelId = "";
			String channelWay = "";
			String keyWord = "";
			String channelTemp = (String) getSession("channelId");
			String channelWemp = (String) getSession("channelWay");

			if (StringUtil.isNotEmpty(channelTemp)
					|| StringUtil.isNotEmpty(channelWemp)) {
				channelWay = channelWemp;
				channelId = channelTemp;
			} else {
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
			if (StringUtil.isNotEmpty(channelId)) {

				memberChannel.setChannelId(channelId);
				memberChannel.setType("0");// 0—代表注册 1-代表支付
				memberChannel.setChannelWay(channelWay);
				memberChannel.setSubType(keyWord);
				memberChannelService.save(memberChannel);
			}

			/* 结束 jingrujia 1054 需求- 渠道统计2012-10-30 end */

			// 清空临时购物车Cookie
			Cookie cartItemCookie = new Cookie(
					CartItemCookie.CART_ITEM_LIST_COOKIE_NAME, null);
			cartItemCookie.setPath(getRequest().getContextPath() + "/");
			cartItemCookie.setMaxAge(0);
			getResponse().addCookie(cartItemCookie);
			/* zhangjinquan 11180 需求-注册增加统计页面-订单结果注册 2012-10-26 start */
			/*
			 * if (member.getVIPFrom().equals("1")) { return "index"; }
			 */

			// if(StringUtil.isNotEmpty(orderNo)){
			// Order order=orderService.getOrderByOrderSn(orderNo);
			// order.setMemberId(memberId);
			// }
			//
			String redirectionUrl = (String) getSession(Member.LOGIN_REDIRECTION_URL_SESSION_NAME);
			getRequest().getSession().removeAttribute(
					Member.LOGIN_REDIRECTION_URL_SESSION_NAME);
			/* zhangjinquan 11180 需求-注册增加统计页面-订单结果注册 2012-10-26 start */
			if (StringUtils.isEmpty(redirectionUrl)) {
				if (StringUtil.isEmpty(this.getWindowNewAddr())) {
					String sql = "select value from zdconfig where type= 'ServerContext'";
					String url = db.getOneValue(sql);
					String path = url + "/shop/member_center!index.action";
					this.setWindowNewAddr(path);
				}
			} else {
				this.setWindowNewAddr(redirectionUrl);
			}

			GALog.saveGALogRecord(gaLogService, GALog.REGISTER_SUCCESS,
					member.getId(), null);
			/* meijunfeng 20121128002-需求系统开发需求申请单-CPA申请需求 2012-12-12 start */
			RegistSuccessInfoCommit.infoCommitForEmar(getRequest(), member);
			/* meijunfeng 20121128002-需求系统开发需求申请单-CPA申请需求 2012-12-12 end */
			// 触发活动
			QueryBuilder qb_activity = new QueryBuilder(
					"SELECT id from sdcouponactivityinfo  WHERE TYPE='0' AND STATUS='3' ");
			DataTable qt_activity = qb_activity.executeDataTable();
			if (qt_activity.getRowCount() == 1) {
				String mail = member.getEmail();
				String mobile = member.getMobileNO();
				provideCoupon(mail, mobile);
			} else if (qt_activity.getRowCount() > 1) {
				return ajaxJsonSuccessMessage("注册活动数量多于一个！");
			}
			return ajaxJsonSuccessMessage("注册成功！");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ajaxJsonErrorMessage("矮油，出了点小状况，请客服MM帮帮忙吧！");
		}

	}

	public String normalRegister() throws Exception {

		try {
			if (!StringUtil.isMail(registerUserName)
					&& !StringUtil.isMobileNO(registerUserName)) {
				addActionError("正确的邮箱地址或手机号是登录的唯一凭证哟！");
				return ERROR;

			}
			if (StringUtil.isEmpty(validateCode)) {
				addActionError("这里一定要写上验证码才行哟！");
				return ERROR;

			}
			String sendCode = (String) getSession("sendValidateCode");
			if (StringUtil.isEmpty(sendCode) || !validateCode.equals(sendCode)) {
				addActionError("验证码输入不正确！");
				return ERROR;

			}

			if (StringUtil.isMail(registerUserName)) {// 邮箱注册
				if (!isOntime(180)) {
					addActionError("啊哦，验证码过期了，点击换一个吧");
					return ERROR;

				}
				try {
					if (memberService.isExistByMailbox(registerUserName)) {
						addActionError("邮箱号已被注册！");
						return ERROR;
					}
				} catch (Exception e) {
					addActionError("矮油，出了点小状况,点击<a vlpageid=\"xiaoneng\" exturl=\"http://www.kaixinbao.com/xiaoneng\" id=\"qqwap2\" onclick=\"return(VL_FileDL(this));return false;\" href=\"javascript:void(0);\"><这里></a>！请客服MM帮帮忙吧！");
					return ERROR;
				}
				this.registerName = registerUserName;
				member.setEmail(registerUserName);
				member.setRegisterType("0");// 表示邮箱注册
			}
			if (StringUtil.isMobileNO(registerUserName)) {// 手机注册
				if (!isOntime(60)) {
					addActionError("啊哦，验证码过期了，点击换一个吧");
					return ERROR;
				}
				try {
					if (memberService.isExistByMobileNO(registerUserName)) {
						addActionError("这个手机号已经注册过了！");
						return ERROR;
					}
				} catch (Exception e) {
					addActionError("矮油，出了点小状况,点击<a vlpageid=\"xiaoneng\" exturl=\"http://www.kaixinbao.com/xiaoneng\" id=\"qqwap2\" onclick=\"return(VL_FileDL(this));return false;\" href=\"javascript:void(0);\"><这里></a>！请客服MM帮帮忙吧！");
					return ERROR;
				}
				this.registerName = registerUserName;
				member.setMobileNO(registerUserName);
				member.setRegisterType("1");// 表示手机注册
			}
			if (!StringUtil.isPasword(member.getPassword())) {
				addActionError("密码由6-16个英文、数字、及下划线组成且不能全部是数字！");
				return ERROR;

			}
			if (StringUtil.isEmpty(rePassword)) {
				addActionError("密码确认不能为空！");
				return ERROR;

			}
			if (!rePassword.equals(member.getPassword())) {
				addActionError("两次密码输入不一致！");
				return ERROR;

			}
			// long currentTime = System.currentTimeMillis();
			// long sendTime = (Long) getSession("sendTime");
			// if ("0".equals(member.getRegisterType())) {
			// if ((currentTime - sendTime) > 5 * 60 * 1000) {
			// addActionError("验证码时间已超过5分钟！");
			// return ERROR;
			// }
			// } else {
			// if ((currentTime - sendTime) > 120 * 60 * 1000) {
			//
			// addActionError("验证码时间已超过120分钟！");
			// return ERROR;
			// }
			// }

			if (!(member.getUsername() == null || "".equals(member
					.getUsername()))) {
				if (member.getUsername().length() > 16
						|| member.getUsername().trim().length() < 4) {
					addActionError("用户名由4-16个英文字符、数字、下划线组成且不能以数字开头！");
					return ERROR;

				}
				Pattern pattern = Pattern
						.compile("^[a-zA-Z_][a-zA-Z_0-9]{3,16}$");
				Matcher matcher = pattern.matcher(member.getUsername());
				if (!matcher.matches()) {
					addActionError("用户名由4-16个英文字符、数字、下划线组成且不能以数字开头！");
					return ERROR;

				}
				try {
					if (memberService.isExistByUsername(member.getUsername())) {
						addActionError("该用户名已存在！");
						return ERROR;

					}
				} catch (Exception e) {
					addActionError("矮油，出了点小状况,点击<a vlpageid=\"xiaoneng\" exturl=\"http://www.kaixinbao.com/xiaoneng\" id=\"qqwap2\" onclick=\"return(VL_FileDL(this));return false;\" href=\"javascript:void(0);\"><这里></a>！请客服MM帮帮忙吧！");
					return ERROR;
				}
			}
			if (!(member.getQQNO() == null || "".equals(member.getQQNO()))) {
				try {
					if (memberService.isExistByUsername(member.getQQNO())) {
						addActionError("该QQ号已存在！");
						return ERROR;

					}
				} catch (Exception e) {
					addActionError("矮油，出了点小状况,点击<a vlpageid=\"xiaoneng\" exturl=\"http://www.kaixinbao.com/xiaoneng\" id=\"qqwap2\" onclick=\"return(VL_FileDL(this));return false;\" href=\"javascript:void(0);\"><这里></a>！请客服MM帮帮忙吧！");
					return ERROR;
				}
			}

			if (!(member.getRealName() == null || "".equals(member
					.getRealName()))) {
				Pattern pattern = Pattern.compile("^[a-zA-Z\u4e00-\u9fa5]+$");
				Matcher matcher = pattern.matcher(member.getRealName());
				if (!matcher.matches()) {
					addActionError("真实姓名只能中文或者英文，不能有特殊字符！");
					return ERROR;

				}

			}
			if (isAgreeAgreement == null || isAgreeAgreement == false) {
				addActionError("小手勾选同意协议，成功注册so easy！");
				return ERROR;

			}
			if (!getSystemConfig().getIsRegister()) {
				addActionError("亲，本站暂时不支持会员注册，敬请期待开放！");
				return ERROR;
			}

			int count = 0;
			if (!(member.getAddress() == null || "".equals(member.getAddress())))
				count++;
			if (!(member.getBirthday() == null || "".equals(member
					.getBirthday())))
				count++;
			if (!(member.getEmail() == null || "".equals(member.getEmail())))
				count++;
			if (!(member.getFaxNO() == null || "".equals(member.getFaxNO())))
				count++;
			if (love != null)
				count++;
			if (!(member.getIDNO() == null || "".equals(member.getIDNO())))
				count++;
			if (!(member.getIDType() == null || "".equals(member.getIDType()) || "-1"
					.equals(member.getIDType())))
				count++;
			if (!(member.getIndustryType() == null || "".equals(member
					.getIndustryType())))
				count++;
			if (!(member.getLocation() == null || "".equals(member
					.getLocation())))
				count++;
			if (!(member.getMarriageStatus() == null
					|| "".equals(member.getMarriageStatus()) || "-1"
						.equals(member.getIDType())))
				count++;
			if (!(member.getMobileNO() == null || "".equals(member
					.getMobileNO())))
				count++;
			if (!(member.getPersonalURL() == null || "".equals(member
					.getPersonalURL())))
				count++;
			if (!(member.getPosition() == null || "".equals(member
					.getPosition())))
				count++;
			if (!(member.getQQNO() == null || "".equals(member.getQQNO())))
				count++;
			if (!(member.getRealName() == null || "".equals(member
					.getRealName())))
				count++;
			if (!(member.getSex() == null || "".equals(member.getSex())))
				count++;
			if (!(member.getTelephoneNO() == null || "".equals(member
					.getTelephoneNO())))
				count++;
			if (!(member.getUsername() == null || "".equals(member
					.getUsername())))
				count++;
			if (!(member.getVIPType() == null
					|| "".equalsIgnoreCase(member.getVIPType()) || "-1"
						.equals(member.getIDType())))
				count++;
			if (!(member.getZipcode() == null || "".equals(member.getZipcode())))
				count++;
			double a = (count / 20.0) * 100;
			DecimalFormat myformat = new DecimalFormat("#####0");
			String fullDegree = myformat.format(a) + "%";
			member.setFullDegree(fullDegree);

			member.setPassword(DigestUtils.md5Hex(member.getPassword()));
			member.setSafeQuestion(null);
			member.setSafeAnswer(null);
			member.setMemberRank(memberRankService.getDefaultMemberRank());
			member.setPoint(0);
			member.setDeposit(new BigDecimal("0"));
			member.setIsAccountEnabled("Y");
			member.setIsAccountLocked("N");
			member.setLoginFailureCount(0);
			member.setPasswordRecoverKey(null);
			member.setLockedDate(null);
			member.setLoginDate(new Date());
			member.setRegisterIp(getRequest().getRemoteAddr());
			member.setLoginIp(getRequest().getRemoteAddr());
			member.setMemberAttributeMap(null);
			member.setReceiverSet(null);
			member.setFavoriteProductSet(null);
			member.setUsedPoint(0);
			member.setCurrentValidatePoint(0);
			member.setExpiricalValue(0);
			BindInfoForLogin bindInfoForLogin = new BindInfoForLogin();
			bindInfoForLogin.setId("");
			member.setmBindInfoForLogin(bindInfoForLogin);
			if(StringUtil.isEmpty(member.getEmail())){
				member.setEmail("");
			}
			if(StringUtil.isEmpty(member.getMobileNO())){
				member.setMobileNO("");
			}
			memberService.save(member);

			if ("0".equals(member.getRegisterType())
					&& member.getVIPFrom().equals("0")) {
				member.setIsEmailBinding("Y");
				member.setIsMobileNOBinding("N");
				memberService.save(member);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("Member", member);

				try {
					Map<String,Object> mailParam = new HashMap<String,Object>();
					mailParam.put("memberId", member.getId());
					ActionUtil.sendMessage("wj0003", mailParam);
					ActionUtil.dealAction("wj0003", map, getRequest());
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			} else if ("1".equals(member.getRegisterType())
					&& member.getVIPFrom().equals("0")) {
				member.setIsMobileNOBinding("Y");
				member.setIsEmailBinding("N");
				memberService.save(member);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("Member", member);
				try {
					ActionUtil.sendSms("wj0004", member.getMobileNO(), "");
					ActionUtil.dealAction("wj0004", map, getRequest());
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}

			if (member.getVIPFrom().equals("1")) {// 表示操作后注册
				// if (codeType.equals("0")) {
				// Order order = orderService.getOrderByOrderSn(serialNO);
				// order.setMemberId(member.getId());
				// orderService.update(order);
				// member.setCurrentValidatePoint(order.getPoint());
				// memberService.update(member);
				//
				// }
				if (codeType.equals("1")) {// 车险一键报价
					SDtargetInformation sdTargetInformation = sdTargetInformationService
							.get(serialNO);
					sdTargetInformation.setMemberId(member.getId());
					sdTargetInformationService.update(sdTargetInformation);
					ShowInsurance showInsurance = showInsuranceService
							.get(serialNO);
					showInsurance.setMemberId(member.getId());
					showInsuranceService.update(showInsurance);

				}

			}
			JdbcTemplateData jtd = new JdbcTemplateData();
			String sql3 = "select codevalue from zdcode where codetype=? and parentcode=? order by codevalue asc";
			String[] sql3temp = { "member.Hobby", "hobby" };
			List<Map> listhy;
			try {
				listhy = jtd.obtainData(sql3, sql3temp);
				Iterator<Map> it = listhy.iterator();
				while (it.hasNext()) {
					MemberHobby mh = new MemberHobby();
					mh.setCodeValue((String) it.next().get("CodeValue"));
					mh.setIsSelected("N");
					mh.setMember(member);
					memberHobbyService.save(mh);
				}
			} catch (InstantiationException e) {
				logger.error(e.getMessage(), e);
			} catch (IllegalAccessException e) {
				logger.error(e.getMessage(), e);
			} catch (ClassNotFoundException e) {
				logger.error(e.getMessage(), e);
			}

			if (love != null && love.length > 0) {
				for (String s : love) {
					String sql5 = "update memberhobby set isselected='Y' where codevalue=? and  member_id=?";
					String[] sql5temp = { s, member.getId() };
					JdbcTemplateData jtdss = new JdbcTemplateData();
					jtdss.updateOrSaveOrDelete(sql5, sql5temp);
				}
			}
			// 写入会员登录Session
			setSession(Member.LOGIN_MEMBER_ID_SESSION_NAME, member.getId());

			if (member.getUsername() != null
					&& !"".equals(member.getUsername())) {
				registerName = member.getUsername();
			}
			// 写入会员登录Cookie
			Cookie loginMemberCookie = new Cookie(
					Member.LOGIN_MEMBER_USERNAME_COOKIE_NAME,
					URLEncoder.encode(registerName, "UTF-8"));
			loginMemberCookie.setPath(getRequest().getContextPath() + "/");
			getResponse().addCookie(loginMemberCookie);
			GetDBdata db = new GetDBdata();
			try {
				String[] temp = { "FrontServerContext" };
				String url = db.getOneValue(
						"select value from zdconfig where type=?", temp);
				if (url.endsWith("/")) {
					loginMemberCookie.setPath(url);
				} else {
					loginMemberCookie.setPath(url + "/");
				}
				loginMemberCookie.setMaxAge(30 * 100000);
				getResponse().addCookie(loginMemberCookie);
			} catch (Exception e) {
			}
			// 合并购物车
			Cookie[] cookies = getRequest().getCookies();
			if (cookies != null && cookies.length > 0) {
				for (Cookie cookie : cookies) {
					if (StringUtils.equalsIgnoreCase(cookie.getName(),
							CartItemCookie.CART_ITEM_LIST_COOKIE_NAME)) {
						if (StringUtils.isNotEmpty(cookie.getValue())) {
							JsonConfig jsonConfig = new JsonConfig();
							jsonConfig.setRootClass(CartItemCookie.class);
							JSONArray jsonArray = JSONArray.fromObject(cookie
									.getValue());
							List<CartItemCookie> cartItemCookieList = (List<CartItemCookie>) JSONSerializer
									.toJava(jsonArray, jsonConfig);
							for (CartItemCookie cartItemCookie : cartItemCookieList) {
								Product product = productService
										.load(cartItemCookie.getI());
								if (product != null) {
									CartItem cartItem = new CartItem();
									cartItem.setMember(member);
									cartItem.setProduct(product);
									cartItem.setQuantity(cartItemCookie.getQ());
									cartItemService.save(cartItem);
								}
							}
						}
					}
				}
			}
			/* 开始 jingrujia 1054 需求- 渠道统计2012-10-30 start */

			MemberChannel memberChannel = new MemberChannel();
			memberChannel.setMemberId(member.getId());
			String channelId = "";
			String channelWay = "";
			String keyWord = "";
			String channelTemp = (String) getSession("channelId");
			String channelWemp = (String) getSession("channelWay");

			if (StringUtil.isNotEmpty(channelTemp)
					|| StringUtil.isNotEmpty(channelWemp)) {
				channelWay = channelWemp;
				channelId = channelTemp;
			} else {
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
			if (StringUtil.isNotEmpty(channelId)) {

				memberChannel.setChannelId(channelId);
				memberChannel.setType("0");// 0—代表注册 1-代表支付
				memberChannel.setChannelWay(channelWay);
				memberChannel.setSubType(keyWord);
				memberChannelService.save(memberChannel);
			}

			/* 结束 jingrujia 1054 需求- 渠道统计2012-10-30 end */

			// 清空临时购物车Cookie
			Cookie cartItemCookie = new Cookie(
					CartItemCookie.CART_ITEM_LIST_COOKIE_NAME, null);
			cartItemCookie.setPath(getRequest().getContextPath() + "/");
			cartItemCookie.setMaxAge(0);
			getResponse().addCookie(cartItemCookie);
			/* zhangjinquan 11180 需求-注册增加统计页面-订单结果注册 2012-10-26 start */
			/*
			 * if (member.getVIPFrom().equals("1")) { return "index"; }
			 */

			// if(StringUtil.isNotEmpty(orderNo)){
			// Order order=orderService.getOrderByOrderSn(orderNo);
			// order.setMemberId(memberId);
			// }
			// fhz
			String redirectionUrl = (String) getSession(Member.LOGIN_REDIRECTION_URL_SESSION_NAME);
			getRequest().getSession().removeAttribute(
					Member.LOGIN_REDIRECTION_URL_SESSION_NAME);
			/* zhangjinquan 11180 需求-注册增加统计页面-订单结果注册 2012-10-26 start */
			if (StringUtils.isEmpty(redirectionUrl)) {
				if (StringUtil.isEmpty(this.getWindowNewAddr())) {
					// return "memberCenterIndexAction";
					String sql = "select value from zdconfig where type= 'ServerContext'";
					String url = db.getOneValue(sql);
					String path = url + "/shop/member_center!index.action";
					this.setWindowNewAddr(path);
				}
			} else {
				this.setWindowNewAddr(redirectionUrl);
			}

			// return "validate_finish";
			GALog.saveGALogRecord(gaLogService, GALog.REGISTER_SUCCESS,
					member.getId(), null);
			/* meijunfeng 20121128002-需求系统开发需求申请单-CPA申请需求 2012-12-12 start */
			RegistSuccessInfoCommit.infoCommitForEmar(getRequest(), member);
			/* meijunfeng 20121128002-需求系统开发需求申请单-CPA申请需求 2012-12-12 end */
			return "registerGA";
			/* zhangjinquan 11180 需求-注册增加统计页面-订单结果注册 2012-10-26 end */
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionError(e.getMessage());
			return ERROR;
		}
	}

	public long fromDateStringToLong(String inVal) {

		Date date = null;
		SimpleDateFormat inputFormat = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss");
		try {
			date = inputFormat.parse(inVal);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return date.getTime();
	}

	private boolean isOntime(int time) {

		// String validateTime = (String) getSession("ValidateTime");
		// java.text.DateFormat format1 = new
		// java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		// String s = format1.format(new Date());
		// long startT = fromDateStringToLong(s);
		// long endT = fromDateStringToLong(validateTime);
		// long secd = (startT - endT) / (1000);
		// if(secd>time){
		// return false;
		// }

		return true;
	}

	public String ajaxRegister() throws Exception {

		if (!StringUtil.isMail(registerUserName)
				&& !StringUtil.isMobileNO(registerUserName)) {
			return ajaxJsonErrorMessage("正确的邮箱地址或手机号是登录的唯一凭证哟!");

		}
		if (StringUtil.isEmpty(validateCode)) {
			return ajaxJsonErrorMessage("这里一定要写上验证码才行哟!");

		}
		String sendCode = (String) getSession("sendValidateCode");
		if (StringUtil.isEmpty(sendCode) || !validateCode.equals(sendCode)) {
			return ajaxJsonErrorMessage("验证码输入不正确!");

		}

		if (StringUtil.isMail(registerUserName)) {// 邮箱注册
			if (!isOntime(180)) {
				return ajaxJsonErrorMessage("啊哦，验证码过期了，点击换一个吧!");

			}
			try {
				if (memberService.isExistByMailbox(registerUserName)) {
					return ajaxJsonErrorMessage("邮箱号已被注册!");// 邮箱号被注册
				}
			} catch (Exception e) {
				return ajaxJsonErrorMessage("矮油，出了点小状况，请客服MM帮帮忙吧!");// 邮箱号被注册
			}
			this.registerName = registerUserName;
			member.setEmail(registerUserName);
			member.setRegisterType("0");// 表示邮箱注册
		}
		if (StringUtil.isMobileNO(registerUserName)) {// 手机注册
			if (!isOntime(60)) {
				return ajaxJsonErrorMessage("啊哦，验证码过期了，点击换一个吧!");

			}
			try {
				if (memberService.isExistByMobileNO(member.getMobileNO())) {
					return ajaxJsonErrorMessage("这个手机号已经注册过了!");// 手机号被注册
				}
			} catch (Exception e) {
				return ajaxJsonErrorMessage("矮油，出了点小状况，请客服MM帮帮忙吧!");// 手机号被注册
			}
			this.registerName = registerUserName;
			member.setMobileNO(registerUserName);
			member.setRegisterType("1");// 表示手机注册
		}
		if (!StringUtil.isPasword(member.getPassword())) {
			return ajaxJsonErrorMessage("密码由6-16个英文、数字、及下划线组成且不能全部是数字!");

		}
		if (StringUtil.isEmpty(rePassword)) {
			return ajaxJsonErrorMessage("密码确认不能为空!");

		}
		if (!rePassword.equals(member.getPassword())) {
			return ajaxJsonErrorMessage("两次密码输入不一致!");

		}
		long currentTime = System.currentTimeMillis();
		long sendTime = (Long) getSession("sendTime");
		// if ("0".equals(member.getRegisterType())) {
		// if ((currentTime - sendTime) > 5 * 60 * 1000) {
		// return ajaxJsonErrorMessage("验证码时间已超过5分钟!");
		//
		// }
		// } else {
		// if ((currentTime - sendTime) > 120 * 60 * 1000) {
		//
		// return ajaxJsonErrorMessage("验证码时间已超过120分钟!");
		// }
		// }

		if (!(member.getUsername() == null || "".equals(member.getUsername()))) {
			if (member.getUsername().length() > 16
					|| member.getUsername().trim().length() < 4) {
				return ajaxJsonErrorMessage("用户名由4-16个英文字符、数字、下划线组成且不能以数字开头!");

			}
			Pattern pattern = Pattern.compile("^[a-zA-Z_]+\\w*$");
			Matcher matcher = pattern.matcher(member.getUsername());
			if (!matcher.matches()) {
				return ajaxJsonErrorMessage("用户名由4-16个英文字符、数字、下划线组成且不能以数字开头!");

			}
			try {
				if (memberService.isExistByUsername(member.getUsername())) {
					return ajaxJsonErrorMessage("该用户名已存在啊");

				}
			} catch (Exception e) {
				return ajaxJsonErrorMessage("矮油，出了点小状况，请客服MM帮帮忙吧");
			}
		}
		if (!(member.getQQNO() == null || "".equals(member.getQQNO()))) {
			try {
				if (memberService.isExistByUsername(member.getQQNO())) {
					return ajaxJsonErrorMessage("该QQ号已存在啊");

				}
			} catch (Exception e) {
				return ajaxJsonErrorMessage("矮油，出了点小状况，请客服MM帮帮忙吧");
			}
		}

		if (!(member.getRealName() == null || "".equals(member.getRealName()))) {
			Pattern pattern = Pattern.compile("^[a-zA-Z\u4e00-\u9fa5]+$");
			Matcher matcher = pattern.matcher(member.getRealName());
			if (!matcher.matches()) {
				return ajaxJsonErrorMessage("真实姓名只能中文或者英文，不能有特殊字符!");

			}

		}
		if (isAgreeAgreement == null || isAgreeAgreement == false) {
			return ajaxJsonErrorMessage("小手勾选同意协议，成功注册so easy！");
		}
		if (!getSystemConfig().getIsRegister()) {
			return ajaxJsonErrorMessage("亲，本站暂时不支持会员注册，敬请期待开放！");
		}
		int count = 0;
		if (!(member.getAddress() == null || "".equals(member.getAddress())))
			count++;
		if (!(member.getBirthday() == null))
			count++;
		if (!(member.getEmail() == null || "".equals(member.getEmail())))
			count++;
		if (!(member.getFaxNO() == null || "".equals(member.getFaxNO())))
			count++;
		if (love != null)
			count++;
		if (!(member.getIDNO() == null || "".equals(member.getIDNO())))
			count++;
		if (!(member.getIDType() == null || "".equals(member.getIDType()) || "-1"
				.equals(member.getVIPType())))
			count++;
		if (!(member.getIndustryType() == null || "".equals(member
				.getIndustryType())))
			count++;
		if (!(member.getLocation() == null || "".equals(member.getLocation())))
			count++;
		if (!(member.getMarriageStatus() == null
				|| "".equals(member.getMarriageStatus()) || "-1".equals(member
				.getVIPType())))
			count++;
		if (!(member.getMobileNO() == null || "".equals(member.getMobileNO())))
			count++;
		if (!(member.getPersonalURL() == null || "".equals(member
				.getPersonalURL())))
			count++;
		if (!(member.getPosition() == null || "".equals(member.getPosition())))
			count++;
		if (!(member.getQQNO() == null || "".equals(member.getQQNO())))
			count++;
		if (!(member.getRealName() == null || "".equals(member.getRealName())))
			count++;
		if (!(member.getSex() == null || "".equals(member.getSex())))
			count++;
		if (!(member.getTelephoneNO() == null || "".equals(member
				.getTelephoneNO())))
			count++;
		if (!(member.getUsername() == null || "".equals(member.getUsername())))
			count++;
		if (!(member.getVIPType() == null
				|| "".equalsIgnoreCase(member.getVIPType()) || "-1"
					.equals(member.getVIPType())))
			count++;
		if (!(member.getZipcode() == null || "".equals(member.getZipcode())))
			count++;
		double a = (count / 20.0) * 100;
		DecimalFormat myformat = new DecimalFormat("#####0");
		String fullDegree = myformat.format(a) + "%";
		member.setFullDegree(fullDegree);

		member.setPassword(DigestUtils.md5Hex(member.getPassword()));
		member.setSafeQuestion(null);
		member.setSafeAnswer(null);
		member.setMemberRank(memberRankService.getDefaultMemberRank());
		member.setPoint(0);
		member.setDeposit(new BigDecimal("0"));
		member.setIsAccountEnabled("Y");
		member.setIsAccountLocked("N");
		member.setLoginFailureCount(0);
		member.setPasswordRecoverKey(null);
		member.setLockedDate(null);
		member.setLoginDate(new Date());
		member.setRegisterIp(getRequest().getRemoteAddr());
		member.setLoginIp(getRequest().getRemoteAddr());
		member.setMemberAttributeMap(null);
		member.setReceiverSet(null);
		member.setFavoriteProductSet(null);
		member.setUsedPoint(0);
		member.setCurrentValidatePoint(0);
		member.setExpiricalValue(0);
		member.setFromWap("wj");
		member.setGrade("K0");
		BindInfoForLogin bindInfoForLogin = new BindInfoForLogin();
		bindInfoForLogin.setId("");
		member.setmBindInfoForLogin(bindInfoForLogin);
 		if(StringUtil.isEmpty(member.getEmail())){
			member.setEmail("");
		}
		if(StringUtil.isEmpty(member.getMobileNO())){
			member.setMobileNO("");
		}
		memberService.save(member);

		if ("0".equals(member.getRegisterType())
				&& member.getVIPFrom().equals("0")) {
			member.setIsEmailBinding("Y");
			member.setIsMobileNOBinding("N");
			memberService.save(member);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Member", member);
			try {
				Map<String,Object> mailParam = new HashMap<String,Object>();
				mailParam.put("memberId", member.getId());
				ActionUtil.sendMessage("wj0003", mailParam);
				ActionUtil.dealAction("wj0003", map, getRequest());
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		} else if ("1".equals(member.getRegisterType())
				&& member.getVIPFrom().equals("0")) {
			member.setIsMobileNOBinding("Y");
			member.setIsEmailBinding("N");
			memberService.save(member);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Member", member);
			try {
				ActionUtil.sendSms("wj0004", member.getMobileNO(), "");
				ActionUtil.dealAction("wj0004", map, getRequest());
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}

		if (member.getVIPFrom().equals("1")) {// 表示操作后注册
			// if (codeType.equals("0")) {
			// Order order = orderService.getOrderByOrderSn(serialNO);
			// order.setMemberId(member.getId());
			// orderService.update(order);
			// member.setCurrentValidatePoint(order.getPoint());
			// memberService.update(member);
			//
			// }
			if (codeType.equals("1")) {// 车险一键报价
				SDtargetInformation sdTargetInformation = sdTargetInformationService
						.get(serialNO);
				sdTargetInformation.setMemberId(member.getId());
				sdTargetInformationService.update(sdTargetInformation);
				ShowInsurance showInsurance = showInsuranceService
						.get(serialNO);
				showInsurance.setMemberId(member.getId());
				showInsuranceService.update(showInsurance);

			}

		}
		JdbcTemplateData jtd = new JdbcTemplateData();
		String sql3 = "select codevalue from zdcode where codetype=? and parentcode=? order by codevalue asc";
		String[] sql3temp = { "member.Hobby", "hobby" };
		List<Map> listhy;
		try {
			listhy = jtd.obtainData(sql3, sql3temp);
			Iterator<Map> it = listhy.iterator();
			while (it.hasNext()) {
				MemberHobby mh = new MemberHobby();
				mh.setCodeValue((String) it.next().get("CodeValue"));
				mh.setIsSelected("N");
				mh.setMember(member);
				memberHobbyService.save(mh);
			}
		} catch (InstantiationException e) {
			logger.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
		}

		if (love != null && love.length > 0) {
			for (String s : love) {
				String sql5 = "update memberhobby set isselected='Y' where codevalue=? and  member_id=?";
				String[] sql5temp = { s, member.getId() };
				JdbcTemplateData jtdss = new JdbcTemplateData();
				jtdss.updateOrSaveOrDelete(sql5, sql5temp);
			}
		}
		// 写入会员登录Session
		setSession(Member.LOGIN_MEMBER_ID_SESSION_NAME, member.getId());

		// 写入会员登录Cookie
		Cookie loginMemberCookie = new Cookie(
				Member.LOGIN_MEMBER_USERNAME_COOKIE_NAME, URLEncoder.encode(
						registerName, "UTF-8"));
		loginMemberCookie.setPath(getRequest().getContextPath() + "/");
		getResponse().addCookie(loginMemberCookie);
		GetDBdata db = new GetDBdata();
		try {
			String[] temp = { "FrontServerContext" };
			String url = db.getOneValue(
					"select value from zdconfig where type=?", temp);
			if (url.endsWith("/")) {
				loginMemberCookie.setPath(url);
			} else {
				loginMemberCookie.setPath(url + "/");
			}
			loginMemberCookie.setMaxAge(30 * 100000);
			getResponse().addCookie(loginMemberCookie);
		} catch (Exception e) {
		}
		// 合并购物车
		Cookie[] cookies = getRequest().getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (StringUtils.equalsIgnoreCase(cookie.getName(),
						CartItemCookie.CART_ITEM_LIST_COOKIE_NAME)) {
					if (StringUtils.isNotEmpty(cookie.getValue())) {
						JsonConfig jsonConfig = new JsonConfig();
						jsonConfig.setRootClass(CartItemCookie.class);
						JSONArray jsonArray = JSONArray.fromObject(cookie
								.getValue());
						List<CartItemCookie> cartItemCookieList = (List<CartItemCookie>) JSONSerializer
								.toJava(jsonArray, jsonConfig);
						for (CartItemCookie cartItemCookie : cartItemCookieList) {
							Product product = productService
									.load(cartItemCookie.getI());
							if (product != null) {
								CartItem cartItem = new CartItem();
								cartItem.setMember(member);
								cartItem.setProduct(product);
								cartItem.setQuantity(cartItemCookie.getQ());
								cartItemService.save(cartItem);
							}
						}
					}
				}
			}
		}

		// 清空临时购物车Cookie
		Cookie cartItemCookie = new Cookie(
				CartItemCookie.CART_ITEM_LIST_COOKIE_NAME, null);
		cartItemCookie.setPath(getRequest().getContextPath() + "/");
		cartItemCookie.setMaxAge(0);
		getResponse().addCookie(cartItemCookie);
		/* zhangjinquan 11180 注册增加GA统计 2012-10-26 start */
		GALog.saveGALogRecord(gaLogService, GALog.REGISTER_SUCCESS,
				member.getId(), null);
		/* meijunfeng 20121128002-需求系统开发需求申请单-CPA申请需求 2012-12-12 start */
		RegistSuccessInfoCommit.infoCommitForEmar(getRequest(), member);
		/* meijunfeng 20121128002-需求系统开发需求申请单-CPA申请需求 2012-12-12 end */
		if (member.getVIPFrom().equals("1")) {
			// return "index";
			return "registerGA";
		}
		/* zhangjinquan 11180 注册增加GA统计 2012-10-26 end */
		try {
			SDExpCalendarSchema tSDExpCalendarSchema = new SDExpCalendarSchema();
			SDExpCalendarSet tSDExpCalendarSet = tSDExpCalendarSchema
					.query(new QueryBuilder("where memberid ='"
							+ member.getId() + "' "));
			return ajaxJsonSuccessMessage("会员注册成功！");
			/*
			 * SDExpCalendarSchema tSDExpCalendarSchema1 = tSDExpCalendarSet
			 * .get(tSDExpCalendarSet.size() - 1); String exp =
			 * tSDExpCalendarSchema1.getExperience();
			 * 
			 * if (Long.parseLong(exp) == 0) { return
			 * ajaxJsonSuccessMessage("会员注册成功！"); } else { return
			 * ajaxJsonSuccessMessage("会员注册成功！恭喜您获得" + exp + "经验值！"); }
			 */
		} catch (Exception e) {
			return ajaxJsonSuccessMessage("会员注册成功！");
		}
	}

	// 弹出窗口登录 fhz
	public String newLogin() throws Exception {

		// 数据安全加密SSL部署 https跳转httpsession共享。 by 20151202 wangwenying
//		Cookie c = new Cookie("JSESSIONID", getRequest().getSession().getId());
//		c.setDomain(Config.getValue("ShareSessionDomain"));
//		c.setPath("/");
//		getResponse().addCookie(c);

		GetDBdata db = new GetDBdata();
		String sql = "select value from zdconfig where type= 'FrontServerContextPath'";
		frontPage = db.getOneValue(sql);
		if ("regtag".equals(this.tagid)) {
			if (windowAddr == null || "".equals(windowAddr)) {
				windowAddr = getRequest().getHeader("Referer");
				String serverPath = Config.getValue("FrontServerContextPath");
				// 若windowAddr 来源页面
				// 是用POST方式提交，那么URL中会有？,或者action结束，这样直接跳回去必然报错，还是返回首页~
				if (StringUtil.isNotEmpty(serverPath) && serverPath.replace("http://", "").indexOf(":") != -1) {
					if (windowAddr == null || "".equals(windowAddr) || windowAddr.endsWith("?")
							|| windowAddr.endsWith("action")
							|| windowAddr.indexOf(serverPath.substring(7, serverPath.lastIndexOf(":"))) < 0) {
						windowAddr = serverPath;
					}

				} else {
					if (windowAddr == null || "".equals(windowAddr) || windowAddr.endsWith("?")
							|| windowAddr.endsWith("action")
							|| windowAddr.indexOf(serverPath.substring(7)) < 0) {
						windowAddr = serverPath;
					}
				}
			}
		}
		// 推荐人不是空的情况
		if (StringUtil.isNotEmpty(rmemId)) {
			// 判断请求IP是否注册过，注册过再注册需填写验证码
			if (memberService.isExistByRecommentMemberIdAndIP(rmemId, getRequest().getRemoteAddr())) {
				ipRepeat = "1";
			}
		}
		try {
			PointsCalculate PointsCalculate = new PointsCalculate();
			// 获得注册送积分个数
			Map<String, Object> map1 = PointsCalculate.pointsManage(IntegralConstant.POINT_SEARCH,
					IntegralConstant.POINT_SOURCE_REGISTER, null);
			if (map1.get(IntegralConstant.ACTION_POINTS) != null
					&& !"0".equals(map1.get(IntegralConstant.ACTION_POINTS))) {
				points = map1.get(IntegralConstant.ACTION_POINTS)
						.toString();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return "new_login";
	}

	// 弹出注册成功页面
	public String registerSuccess() throws Exception {

		// 如果这个页面回不去那么跳转到首页
		if (backURL == null || "".equals(backURL)
				|| "?".equals(backURL.charAt(backURL.length() - 1))) {
			GetDBdata db = new GetDBdata();
			String sql = "select value from zdconfig where type= 'FrontServerContextPath'";
			backURL = db.getOneValue(sql);
		}
		member = memberService.getMemberByLoginNameNoBinding(registerUserName);
		if (member == null) {
			addActionError("系统不存在此邮箱号或手机号");
			return ERROR;
		}
		// 注册成功清空session中ip信息
		setSession(receiveUserIp(), "");
		return "register_success";
	}

	// 密码找回
	public String passwordRecover() throws Exception {

		return "password_recover";
	}

	// 密码找回新 add by fhz
	public String passwordRetrieve() throws Exception {

		return "password_retrieve";
	}

	public String sendPasswordRecoverMail() throws Exception {

		String sendType = null;
		if (mobileNoOrEmail == null || "".equals(mobileNoOrEmail)) {
			return ajaxJsonErrorMessage("手机号/邮箱不能为空!");
		}
		Pattern pattern1 = Pattern.compile("^\\d{11}$");
		Matcher matcher1 = pattern1.matcher(mobileNoOrEmail.trim());
		if (matcher1.matches()) {
			Pattern pattern = Pattern
					.compile("^(13[0-9]{9}|15[5-9][0-9]{8}|15[0-3][0-9]{8}|18[0-9][0-9]{8}|147[0-9]{8}|17[6-8][0-9]{8})$");
			Matcher matcher = pattern.matcher(mobileNoOrEmail.trim());
			if (!matcher.matches()) {
				return ajaxJsonErrorMessage("手机号格式错误!");

			}
			sendType = "1";
		} else {
			Pattern pattern = Pattern
					.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
			Matcher matcher = pattern.matcher(mobileNoOrEmail.trim());
			if (!matcher.matches()) {
				return ajaxJsonErrorMessage("E-mail格式错误!");
			}
			sendType = "0";
		}
		try {
			Member persistent = memberService
					.getMemberByLoginName(mobileNoOrEmail);
			if (persistent == null) {
				addActionError("系统不存在此邮箱号或手机号");
				return ERROR;
			}
		} catch (Exception e) {
			addActionError("矮油，出了点小状况,点击<a vlpageid=\"xiaoneng\" exturl=\"http://www.kaixinbao.com/xiaoneng\" id=\"qqwap2\" onclick=\"return(VL_FileDL(this));return false;\" href=\"javascript:void(0);\"><这里></a>！请客服MM帮帮忙吧！");
			return ERROR;
		}

		String svc = VCFactory();
		if ("0".equals(sendType)) {
			if (SVCToEmail(svc, mobileNoOrEmail, "Y")) {
				return "validate_input";
			}
		}
		if ("1".equals(sendType)) {
			if (SVCToPhone(svc, mobileNoOrEmail, "Y")) {
				return "validate_input";
			}
		}
		return "password_recover";
	}

	/**
	 * 新密码找回方法 20130129
	 * 
	 * @author fhz
	 * */
	public String sendPasswordRetrieveMessage() throws Exception {

	    Member retriveMember;// 找回密码的member
		String sendType = null;
		if (mobileNoOrEmail == null || "".equals(mobileNoOrEmail)) {
			addActionError("手机号/邮箱不能为空!");
			return "password_retrieve";
		}
		try {
			String captchaID = getRequest().getSession().getId();
			String challengeResponse = StringUtils.upperCase(getRequest()
					.getParameter(JCaptchaEngine.CAPTCHA_INPUT_NAME));
			if (StringUtils.isEmpty(challengeResponse)
					|| captchaService.validateResponseForID(captchaID,
							challengeResponse) == false) {
				addActionError("验证码输入错误!");
				return "password_retrieve";
			}
		} catch (Exception e) {
			addActionError("验证码输入错误!");
			return "password_retrieve";
		}
		Pattern pattern1 = Pattern.compile("^\\d{11}$");
		Matcher matcher1 = pattern1.matcher(mobileNoOrEmail.trim());
		if (matcher1.matches()) {
			Pattern pattern = Pattern
					.compile("^(13[0-9]{9}|15[5-9][0-9]{8}|15[0-3][0-9]{8}|18[0-9][0-9]{8}|147[0-9]{8}|17[6-8][0-9]{8})$");
			Matcher matcher = pattern.matcher(mobileNoOrEmail.trim());
			if (!matcher.matches()) {
				addActionError("手机号格式错误!");
				return "password_retrieve";
			}
			sendType = "1";
		} else {
			Pattern pattern = Pattern
					.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
			Matcher matcher = pattern.matcher(mobileNoOrEmail.trim());
			if (!matcher.matches()) {
				addActionError("邮箱格式错误!");
				return "password_retrieve";
			}
			sendType = "0";
		}
		try {
			retriveMember = memberService
					.getMemberByLoginNameNoBinding(mobileNoOrEmail);
			if (retriveMember == null) {
				addActionError("系统不存在此邮箱号或手机号");
				return "password_retrieve";
			}
			retriveMember.setRetrieveEmailvalid("0");
		} catch (Exception e) {
			addActionError("矮油，出了点小状况,点击<a vlpageid=\"xiaoneng\" exturl=\"http://www.kaixinbao.com/xiaoneng\" id=\"qqwap2\" onclick=\"return(VL_FileDL(this));return false;\" href=\"javascript:void(0);\"><这里></a>！请客服MM帮帮忙吧！");
			return "password_retrieve";
		}
		// 邮件找回密码，发送连接
		if ("0".equals(sendType)) {
			if (!sendRetrieveMail(retriveMember)) {
				addActionError("发送找回密码邮件失败！");
				return "password_retrieve";
			}
			retriveMember.setRetrieveEmailSendDate(new Date());
			memberService.update(retriveMember);
			return "retrieve_email";
		}

		if ("1".equals(sendType)) {
			String svc = VCFactory();
			if (SVCToPhone(svc, mobileNoOrEmail, "Y")) {
				return "retrieve_mobile";
			}
		}
		return "password_retrieve";
	}

	/**
	 * 通过邮件链接到邮件修改密码的页面 fhz
	 * */
	public String passwordRetrieveByEmail() {

		try {
			Member reMember = memberService.load(member.getId());
			if (reMember == null) {
				addActionError("您的账号状态异常，请联系在线客服或拨打热线电话4009-789-789！");
				return ERROR;
			}
			Date mailExpiredDate = DateUtils.addMinutes(
					reMember.getRetrieveEmailSendDate(), 24 * 60);// 单位是分钟
			Date now = new Date();
			if (now.after(mailExpiredDate)) {
				addActionError("操作失败，您的找回密码链接已经超过24小时,请重新申请找回密码！");
				return ERROR;
			}
			String retrieveEmailvalid = reMember.getRetrieveEmailvalid();
			if ("1".equals(retrieveEmailvalid)) {
				addActionError("操作失败，您的密码已经重置。请重申请密码找回！");
				return ERROR;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionError("您的账号状态异常，请联系在线客服或拨打热线电话4009-789-789！");
			return ERROR;
		}
		return "mail_retrieve";
	}

	/**
	 * 重新发送找回密码邮件
	 * 
	 * @author fhz
	 * */
	public String reSendRetrieveMail() {

		try {
			Member resMember = memberService.load(member.getId());
			memberSchema smemberSchema = new memberSchema();
			smemberSchema.setid(member.getId());
			memberSet smemberSet = smemberSchema.query();
			memberSchema tmemberSchema = smemberSet.get(0);
			tmemberSchema.setretrieveEmailvalid("0");
			if (!tmemberSchema.update()) {
				addActionError("重新发送失败");
				return ERROR;
			}
			if (resMember == null) {
				addActionError("查询用户信息失败！");
				return ERROR;
			}
			if (!"Y".equals(resMember.getIsEmailBinding())
					&& !"0".equals(resMember.getRegisterType())) {
				addActionError("此邮箱未进行验证，不能找回密码！");
				return ERROR;
			}
			if (!sendRetrieveMail(resMember)) {
				addActionError("发送找回密码邮件失败！");
				return ERROR;
			}
			resMember.setRetrieveEmailSendDate(new Date());
			memberService.update(resMember);
		} catch (Exception e) {
			addActionError("重新发送邮件失败！");
			logger.error(e.getMessage(), e);
			return ERROR;
		}

		return "retrieve_email";
	}

	/**
	 * 重新发送注册成功验证邮件
	 * 
	 * @author fhz
	 * */
	public String reSendVerifyEMail() {

		try {
			Member resMember = memberService.load(member.getId());
			if (resMember == null) {
				addActionError("查询用户信息失败！");
				return ERROR;
			}
			logger.info("重新发送邮件地址:{}", resMember.getEmail());
			if (!sendVerifyEMail(resMember)) {
				addActionError("发送验证邮件失败！");
				return ERROR;
			}
			resMember.setVerifyEmailSendDate(new Date());// 更新验证邮件发送时间
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionError("您的账号状态异常，请联系在线客服或拨打热线电话4009-789-789！");
			return ERROR;
		}

		return SUCCESS;
	}

	/**
	 * 手机找回密码，校验验证码
	 * 
	 * */
	public String checkSVC() {

		String inputSVC = getParameter("svc");// 用户输入验证码
		String sessionSVC = (String) getSession("sendValidateCode");// session中验证码
		if (inputSVC == null || sessionSVC == null
				|| !inputSVC.equals(sessionSVC)) {
			return ajaxJson("false");
		} else {
			return ajaxJson("true");
		}
	}

	/***
	 * 邮件找回 修改密码
	 * 
	 * **/
	public String doRetrieveByEmail() {

		try {
			GetDBdata db = new GetDBdata();
			String sql = "select value from zdconfig where type= 'FrontServerContextPath'";
			frontPage = db.getOneValue(sql);
			Member reMember = memberService.load(member.getId());

			if (reMember == null) {
				addActionError("查询用户信息失败！");
				return ERROR;
			}

			if (member.getPassword() == null || "".equals(member.getPassword())) {
				addActionError("密码不能为空!");
				return ERROR;

			} else {
				if (member.getPassword().length() < 6
						|| member.getPassword().length() > 16) {
					addActionError("密码的长度应在6-16位之间!");
					return ERROR;
				}
			}

			if (rePassword == null || "".equals(rePassword)) {
				addActionError("密码确认不能为空!");
				return ERROR;

			}
			if (!rePassword.equals(member.getPassword())) {
				addActionError("两次密码输入不一致!");
				return ERROR;
			}

			reMember.setPassword(DigestUtils.md5Hex(member.getPassword()));
			reMember.setIsAccountEnabled("Y");
			reMember.setIsAccountLocked("N");
			reMember.setLoginFailureCount(0);
			reMember.setLockedDate(null);
			reMember.setLoginDate(new Date());
			reMember.setRegisterIp(getRequest().getRemoteAddr());
			reMember.setLoginIp(getRequest().getRemoteAddr());
			reMember.setMemberAttributeMap(null);
			reMember.setReceiverSet(null);
			reMember.setFavoriteProductSet(null);
			reMember.setRetrieveEmailvalid("1");
			memberService.update(reMember);

			/*
			 * Member resMember = memberService.load(member.getId());
			 * memberSchema smemberSchema = new memberSchema();
			 * smemberSchema.setid(member.getId()); memberSet smemberSet =
			 * smemberSchema.query(); memberSchema tmemberSchema =
			 * smemberSet.get(0); tmemberSchema.setretrieveEmailvalid("0");
			 */

			return "retrieve_success";
		} catch (Exception e) {
			addActionError("您的账号状态异常，请联系在线客服或拨打热线电话4009-789-789！");
			logger.error(e.getMessage(), e);
			return ERROR;

		}
	}

	/**
	 * 发送找回密码邮件
	 * 
	 * @author fhz
	 * **/
	public boolean sendRetrieveMail(Member tMember) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			// 得到邮箱验证的地址
			if(StringUtil.isNotEmpty(tMember.getEmail())){
				emailCom = "http://mail."
						+ tMember.getEmail().substring(
								tMember.getEmail().indexOf('@') + 1);
			}
			GetDBdata db = new GetDBdata();
			String sql = "select value from zdconfig where type= 'ServerContext'";
			String path = db.getOneValue(sql);
			String retrieveURL = path
					+ "/shop/member!passwordRetrieveByEmail.action?member.id="
					+ tMember.getId();
			map.put("retrieveURL", retrieveURL);// 得到验证地址
			map.put("MemberRealName", memberService.getNickNameForMail(tMember));
			map.put(MessageConstant.PARAM_SHOW_ACTIVITY_PRODUCT, "1");
			ActionUtil.sendMail("wj00049", tMember.getEmail(), map);
			ActionUtil.dealAction("wj00049", map, getRequest());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

	/**
	 * 发送找回密码邮件
	 * 
	 * @author fhz
	 * **/
	public boolean sendVerifyEMail(Member tMember) {

		try {
			Map<String, Object> map = new HashMap<String, Object>();
			// 得到邮箱验证的地址
			GetDBdata db = new GetDBdata();
			String sql = "select value from zdconfig where type= 'ServerContext'";
			String path = db.getOneValue(sql);
			String verifyURL = path
					+ "/shop/member!verifyEmail.action?member.id="
					+ member.getId();
			map.put("verifyURL", verifyURL);// 得到验证地址

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			int rigisterDate = Integer.parseInt(sdf.format(new Date()));
			map.put("rigisterDate", rigisterDate);
			map.put("Points", "");
			if (StringUtil.isNotEmpty(member.getId())) {
				try {
					PointsCalculate PointsCalculate = new PointsCalculate();
					// 邮箱校验赠送积分
					Map<String, Object> map_param = new HashMap<String, Object>();
					map_param.put("PointsGive", IntegralConstant.POINT_GIVE_01);
					// 积分处理
					Map map_result = PointsCalculate.pointsManage(IntegralConstant.POINT_SEARCH,
							IntegralConstant.POINT_SOURCE_EMAIL, map_param);
					Object points = map_result.get(IntegralConstant.ACTION_POINTS);
					if(points == null){
						points = "";
					}
					map.put("Points", points);
				} catch (Exception e) {
					logger.error("注册送积分异常！" + e.getMessage(), e);
				}
			}
			map.put("MemberRealName", memberService.getNickNameForMail(tMember));
			map.put(MessageConstant.PARAM_SHOW_ACTIVITY_PRODUCT, "1");
			ActionUtil.sendMail("wj00048", member.getEmail(), map);
			map.put("Member", member);
			ActionUtil.dealAction("wj00048", map, getRequest());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		return true;

	}

	public String modifyPassword() throws Exception {

		Member persistent = memberService.getMemberByLoginName(mobileOrEmail);
		if (persistent == null) {
			addActionError("此邮箱或手机号系统不存在啊");
			return ERROR;

		}
		if (member.getPassword() == null || "".equals(member.getPassword())) {
			addActionError("密码不能为空!");
			return ERROR;

		} else {
			Pattern pattern = Pattern.compile("^\\d{6,16}$");
			Matcher matcher = pattern.matcher(member.getPassword().trim());
			if (matcher.matches()) {
				addActionError("密码由6-16个英文、数字、及下划线组成且不能全部是数字!");
				return ERROR;

			}
			Pattern pattern1 = Pattern.compile("^\\w{6,16}$");
			Matcher matcher1 = pattern1.matcher(member.getPassword().trim());
			if (!matcher1.matches()) {
				addActionError("密码由6-16个英文、数字、及下划线组成且不能全部是数字!");
				return ERROR;

			}
		}
		if (rePassword == null || "".equals(rePassword)) {
			addActionError("密码确认不能为空!");
			return ERROR;

		}
		if (!rePassword.equals(member.getPassword())) {
			addActionError("两次密码输入不一致!");
			return ERROR;
		}
		if (validateCode == null || "".equals(validateCode)) {
			addActionError("这里一定要写上验证码才行哟!");
			return ERROR;
		}
		// long currentTime = System.currentTimeMillis();
		// long sendTime = (Long) getSession("sendTime");
		// if ((currentTime - sendTime) > 5 * 60 * 1000) {
		// addActionError("验证码时间已超过5分钟!");
		// return ERROR;
		//
		// }

		String sendCode = (String) getSession("sendValidateCode");
		if (!validateCode.equals(sendCode)) {
			addActionError("验证码不一致!");
			return ERROR;
		}
		persistent.setPassword(DigestUtils.md5Hex(member.getPassword()));
		persistent.setIsAccountEnabled("Y");
		persistent.setIsAccountLocked("N");
		persistent.setLoginFailureCount(0);
		persistent.setLockedDate(null);
		persistent.setLoginDate(new Date());
		persistent.setRegisterIp(getRequest().getRemoteAddr());
		persistent.setLoginIp(getRequest().getRemoteAddr());
		persistent.setMemberAttributeMap(null);
		persistent.setReceiverSet(null);
		persistent.setFavoriteProductSet(null);
		memberService.update(persistent);

		return "validate_finish";

	}

	/**
	 * 手机找回密码
	 * */
	public String mobileRetrievePassword() throws Exception {

		GetDBdata db = new GetDBdata();
		String sql = "select value from zdconfig where type= 'FrontServerContextPath'";
		frontPage = db.getOneValue(sql);
		Member persistent = memberService
				.getMemberByLoginNameNoBinding(mobileNoOrEmail);
		if (persistent == null) {
			addActionError("此邮箱或手机号系统不存在啊");
			return ERROR;

		}
		if (member.getPassword() == null || "".equals(member.getPassword())) {
			addActionError("密码不能为空!");
			return ERROR;

		} else {
			if (member.getPassword().length() > 16
					|| member.getPassword().length() < 6) {
				addActionError("密码需在6-16位之间！");
				return ERROR;

			}
		}
		if (rePassword == null || "".equals(rePassword)) {
			addActionError("密码确认不能为空!");
			return ERROR;

		}
		if (!rePassword.equals(member.getPassword())) {
			addActionError("两次密码输入不一致!");
			return ERROR;
		}
		if (validateCode == null || "".equals(validateCode)) {
			addActionError("这里一定要写上验证码才行哟!");
			return ERROR;
		}

		long currentTime = System.currentTimeMillis();
		long sendTime = (Long) getSession("sendTime");
		if ((currentTime - sendTime) > 5 * 60 * 1000) {
			addActionError("验证码时间已超过5分钟!");
			return ERROR;
		}

		String moblie = ((String) getSession("validateCodeAndMobile")).split("_")[0];
		String sendCode = ((String) getSession("validateCodeAndMobile")).split("_")[1];
		if (!validateCode.equals(sendCode) || !mobileNoOrEmail.equals(moblie)) {
			addActionError("验证码不一致!");
			return ERROR;
		}
		persistent.setPassword(DigestUtils.md5Hex(member.getPassword()));
		persistent.setIsAccountEnabled("Y");
		persistent.setIsAccountLocked("N");
		persistent.setLoginFailureCount(0);
		persistent.setLockedDate(null);
		persistent.setLoginDate(new Date());
		persistent.setRegisterIp(getRequest().getRemoteAddr());
		persistent.setLoginIp(getRequest().getRemoteAddr());
		persistent.setMemberAttributeMap(null);
		persistent.setReceiverSet(null);
		persistent.setFavoriteProductSet(null);
		memberService.update(persistent);

		return "retrieve_success";

	}

	// 密码修改
	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "id", message = "ID不允许为空!"),
			@RequiredStringValidator(fieldName = "passwordRecoverKey", message = "passwordRecoverKey不允许为空!") })
	@InputConfig(resultName = "error")
	public String passwordModify() throws Exception {

		Member persistent = memberService.get(id);
		if (persistent == null
				|| StringUtils.equalsIgnoreCase(
						persistent.getPasswordRecoverKey(), passwordRecoverKey) == false) {
			addActionError("对不起，此密码找回链接已失效！");
			return ERROR;
		}
		Date passwordRecoverKeyBuildDate = memberService
				.getPasswordRecoverKeyBuildDate(passwordRecoverKey);
		Date passwordRecoverKeyExpiredDate = DateUtils
				.addMinutes(passwordRecoverKeyBuildDate,
						Member.PASSWORD_RECOVER_KEY_PERIOD);
		Date now = new Date();
		if (now.after(passwordRecoverKeyExpiredDate)) {
			addActionError("对不起，此密码找回链接已过期！");
			return ERROR;
		}
		return "password_modify";
	}

	// 密码更新
	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "id", message = "ID不允许为空!"),
			@RequiredStringValidator(fieldName = "passwordRecoverKey", message = "passwordRecoverKey不允许为空!"),
			@RequiredStringValidator(fieldName = "member.password", message = "密码不允许为空!") }, stringLengthFields = { @StringLengthFieldValidator(fieldName = "member.password", minLength = "4", maxLength = "20", message = "密码长度必须在${minLength}到${maxLength}之间!") })
	@InputConfig(resultName = "error")
	public String passwordUpdate() throws Exception {

		Member persistent = memberService.get(id);
		if (persistent == null
				|| StringUtils.equalsIgnoreCase(
						persistent.getPasswordRecoverKey(), passwordRecoverKey) == false) {
			addActionError("对不起，此密码找回链接已失效！");
			return ERROR;
		}
		Date passwordRecoverKeyBuildDate = memberService
				.getPasswordRecoverKeyBuildDate(passwordRecoverKey);
		Date passwordRecoverKeyExpiredDate = DateUtils
				.addMinutes(passwordRecoverKeyBuildDate,
						Member.PASSWORD_RECOVER_KEY_PERIOD);
		Date now = new Date();
		if (now.after(passwordRecoverKeyExpiredDate)) {
			addActionError("对不起，此密码找回链接已过期！");
			return ERROR;
		}
		persistent.setPassword(DigestUtils.md5Hex(member.getPassword()));
		persistent.setPasswordRecoverKey(null);
		memberService.update(persistent);
		redirectionUrl = getRequest().getContextPath() + "/";
		addActionMessage("密码修改成功！");
		return SUCCESS;
	}

	public Member getMember() {

		return member;
	}

	public void setMember(Member member) {

		this.member = member;
	}

	public Boolean getIsAgreeAgreement() {

		return isAgreeAgreement;
	}

	public void setIsAgreeAgreement(Boolean isAgreeAgreement) {

		this.isAgreeAgreement = isAgreeAgreement;
	}

	public String getPasswordRecoverKey() {

		return passwordRecoverKey;
	}

	public void setPasswordRecoverKey(String passwordRecoverKey) {

		this.passwordRecoverKey = passwordRecoverKey;
	}

	// ------------吴高强添加开始--------------------------------------

	public String profect() {

		HttpServletRequest request = ServletActionContext.getRequest();
		Member oldmember = memberService.load(member.getId());
		String isupdateQEMU = "0";// 表示未修改登陆所用项
		
		if (uname == null || "".equals(uname)) {
		}

		else {
			if (uname.length() > 16 || uname.length() < 4) {
				addActionError("用户名由4-16个英文字符、数字、下划线组成且不能以数字开头!");
				return ERROR;
			}

			Pattern patterno = Pattern
					.compile("^([a-zA-Z_][a-zA-Z_0-9]{3,16})$");
			Matcher matchero = patterno.matcher(uname);
			if (!matchero.matches()) {
				addActionError("用户名由4-16个英文字符、数字、下划线组成且不能以数字开头!");
				return ERROR;
			}
			String olduserName = oldmember.getUsername();
			if (!(olduserName == null || "".equals(olduserName))) {
				if (!olduserName.equals(uname)) {
					try {
						if (memberService.isExistByUsername(uname)) {
							addActionError("该用户名已存在啊");
							return ERROR;
						} else {
							isupdateQEMU = "1";// 表示修改过登陆项
						}
					} catch (Exception e) {
						addActionError("矮油，出了点小状况,点击<a vlpageid=\"xiaoneng\" exturl=\"http://www.kaixinbao.com/xiaoneng\" id=\"qqwap2\" onclick=\"return(VL_FileDL(this));return false;\" href=\"javascript:void(0);\"><这里></a>！请客服MM帮帮忙吧！");
						return ERROR;
					}
				}
			}
		}

		if (realName == null || "".equals(realName)) {
		} else {
			Pattern pattern = Pattern.compile("^[a-zA-Z\u4e00-\u9fa5]+$");
			Matcher matcher = pattern.matcher(realName.trim());
			if (!matcher.matches()) {
				addActionError("真实姓名只能中文或者英文，不能有特殊字符!");
				return ERROR;
			}

		}
		if (love != null && love.length > 0) {
			for (String s : love) {
				String sql6 = "update memberhobby set isselected='Y' where codevalue=? and  member_id=?";
				String[] sql6temp = { s, member.getId() };
				JdbcTemplateData jtdss = new JdbcTemplateData();
				try {
					jtdss.updateOrSaveOrDelete(sql6, sql6temp);
				} catch (InstantiationException e) {
					logger.error(e.getMessage(), e);
				} catch (IllegalAccessException e) {
					logger.error(e.getMessage(), e);
				} catch (ClassNotFoundException e) {
					logger.error(e.getMessage(), e);
				}
			}
			String sql7 = "update memberhobby set isselected='N'  where  member_id=?";
			String[] sql7temp = { member.getId() };
			int length = love.length;
			JdbcTemplateData jtdst = new JdbcTemplateData();
			while (length != 0) {
				int i = --length;
				sql7 = sql7 + " and codevalue <> '" + love[i] + "'";
			}
			try {
				jtdst.updateOrSaveOrDelete(sql7, sql7temp);

			} catch (InstantiationException e) {
				logger.error(e.getMessage(), e);
			} catch (IllegalAccessException e) {
				logger.error(e.getMessage(), e);
			} catch (ClassNotFoundException e) {
				logger.error(e.getMessage(), e);
			}
		} else {
			String sql8 = "update memberhobby set isselected='N'  where  member_id=?";
			String[] sql8temp = { member.getId() };
			JdbcTemplateData jtdst = new JdbcTemplateData();
			try {
				jtdst.updateOrSaveOrDelete(sql8, sql8temp);

			} catch (InstantiationException e1) {
				logger.error(e1.getMessage(), e1);
			} catch (IllegalAccessException e1) {
				logger.error(e1.getMessage(), e1);
			} catch (ClassNotFoundException e1) {
				logger.error(e1.getMessage(), e1);
			}
		}
		double integrals = 0;
		String columns = "";
		memberSchema tMemberSchema = new memberSchema();
		tMemberSchema.setid(member.getId());
		if (tMemberSchema.fill()) {
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtil.isNotEmpty(member.getBirthday())
					&& StringUtil.isEmpty(tMemberSchema.getbirthday())
					&& hasUpdated("birthday")) {
				columns += "birthday,";
				map = new HashMap<String, Object>();
				try {
					PointsCalculate PointsCalculate = new PointsCalculate();
					// 会员ID
					map.put(IntegralConstant.MEM_ID, member.getId());
					// 积分处理
					PointsCalculate.pointsManage(IntegralConstant.POINT_SEND,
							IntegralConstant.POINT_SOURCE_MEM_BIRTHDAY, map);
				} catch (Exception e) {
					logger.error("完善会员生日送积分异常！MemberId:" + member.getId() + e.getMessage(), e);
				}
			}

			if (StringUtil.isNotEmpty(member.getIDType())
					&& !"-1".equals(member.getIDType())
					&& (StringUtil.isEmpty(tMemberSchema.getIDType()) || "-1"
							.equals(tMemberSchema.getIDType()))
					&& hasUpdated("IDType")) {
				columns += "IDType,";
				map = new HashMap<String, Object>();
				try {
					PointsCalculate PointsCalculate = new PointsCalculate();
					// 会员ID
					map.put(IntegralConstant.MEM_ID, member.getId());
					// 积分处理
					PointsCalculate.pointsManage(IntegralConstant.POINT_SEND,
							IntegralConstant.POINT_SOURCE_MEM_IDENTITY_TYPE, map);
				} catch (Exception e) {
					logger.error("完善会员证件送积分异常！MemberId:" + member.getId() + e.getMessage(), e);
				}
			}
			if (StringUtil.isNotEmpty(member.getIndustryType())
					&& !"-1".equals(member.getIndustryType())
					&& StringUtil.isEmpty(tMemberSchema.getindustryType())
					&& hasUpdated("industryType")) {
				columns += "industryType,";
				map = new HashMap<String, Object>();
				try {
					PointsCalculate PointsCalculate = new PointsCalculate();
					// 会员ID
					map.put(IntegralConstant.MEM_ID, member.getId());
					// 积分处理
					PointsCalculate.pointsManage(IntegralConstant.POINT_SEND,
							IntegralConstant.POINT_SOURCE_MEM_OCCUP, map);
				} catch (Exception e) {
					logger.error("完善会员职业送积分异常！MemberId:" + member.getId() + e.getMessage(), e);
				}
			}
			if (StringUtil.isNotEmpty(member.getLocation())
					&& (StringUtil.isEmpty(tMemberSchema.getlocation()) || "-1"
							.equals(tMemberSchema.getlocation()))
					&& hasUpdated("location")) {
				columns += "location,";
				map = new HashMap<String, Object>();
				try {
					PointsCalculate PointsCalculate = new PointsCalculate();
					// 会员ID
					map.put(IntegralConstant.MEM_ID, member.getId());
					// 积分处理
					PointsCalculate.pointsManage(IntegralConstant.POINT_SEND,
							IntegralConstant.POINT_SOURCE_MEM_AREA, map);
				} catch (Exception e) {
					logger.error("完善会员所在地区送积分异常！MemberId:" + member.getId() + e.getMessage(), e);
				}
			}
			if (StringUtil.isNotEmpty(member.getMarriageStatus())
					&& !"-1".equals(member.getMarriageStatus())
					&& (StringUtil.isEmpty(tMemberSchema.getmarriageStatus()) || "-1"
							.equals(tMemberSchema.getmarriageStatus()))
					&& hasUpdated("marriageStatus")) {
				columns += "marriageStatus,";
				map = new HashMap<String, Object>();
				try {
					PointsCalculate PointsCalculate = new PointsCalculate();
					// 会员ID
					map.put(IntegralConstant.MEM_ID, member.getId());
					// 积分处理
					PointsCalculate.pointsManage(IntegralConstant.POINT_SEND,
							IntegralConstant.POINT_SOURCE_MEM_MARRAY, map);
				} catch (Exception e) {
					logger.error("完善会员婚姻状况送积分异常！MemberId:" + member.getId() + e.getMessage(), e);
				}
			}
			if (StringUtil.isNotEmpty(member.getPosition())
					&& StringUtil.isEmpty(tMemberSchema.getposition())
					&& hasUpdated("position")) {
				columns += "position,";
				map = new HashMap<String, Object>();
				try {
					PointsCalculate PointsCalculate = new PointsCalculate();
					// 会员ID
					map.put(IntegralConstant.MEM_ID, member.getId());
					// 积分处理
					PointsCalculate.pointsManage(IntegralConstant.POINT_SEND,
							IntegralConstant.POINT_SOURCE_MEM_JOB, map);
				} catch (Exception e) {
					logger.error("完善会员职位送积分异常！MemberId:" + member.getId() + e.getMessage(), e);
				}
			}
			if (StringUtil.isNotEmpty(realName)
					&& StringUtil.isEmpty(tMemberSchema.getrealName())
					&& hasUpdated("realName")) {
				columns += "realName,";
				map = new HashMap<String, Object>();
				try {
					PointsCalculate PointsCalculate = new PointsCalculate();
					// 会员ID
					map.put(IntegralConstant.MEM_ID, member.getId());
					// 积分处理
					PointsCalculate.pointsManage(IntegralConstant.POINT_SEND,
							IntegralConstant.POINT_SOURCE_MEM_NAME, map);
				} catch (Exception e) {
					logger.error("完善会员姓名送积分异常！MemberId:" + member.getId() + e.getMessage(), e);
				}
			}
			if (StringUtil.isNotEmpty(member.getSex())
					&& StringUtil.isEmpty(tMemberSchema.getsex())
					&& hasUpdated("sex")) {
				columns += "sex,";
				map = new HashMap<String, Object>();
				try {
					PointsCalculate PointsCalculate = new PointsCalculate();
					// 会员ID
					map.put(IntegralConstant.MEM_ID, member.getId());
					// 积分处理
					PointsCalculate.pointsManage(IntegralConstant.POINT_SEND,
							IntegralConstant.POINT_SOURCE_MEM_SEX, map);
				} catch (Exception e) {
					logger.error("完善会员性别送积分异常！MemberId:" + member.getId() + e.getMessage(), e);
				}
			}
			if (StringUtil.isNotEmpty(member.getVIPType())
					&& !"-1".equals(member.getVIPType())
					&& (StringUtil.isEmpty(tMemberSchema.getVIPType()) || "-1"
							.equals(tMemberSchema.getVIPType()))
					&& hasUpdated("VIPType")) {
				columns += "VIPType,";
				map = new HashMap<String, Object>();
				try {
					PointsCalculate PointsCalculate = new PointsCalculate();
					// 会员ID
					map.put(IntegralConstant.MEM_ID, member.getId());
					// 积分处理
					PointsCalculate.pointsManage(IntegralConstant.POINT_SEND,
							IntegralConstant.POINT_SOURCE_MEM_TYPE, map);
				} catch (Exception e) {
					logger.error("完善会员会员类型送积分异常！MemberId:" + member.getId() + e.getMessage(), e);
				}
			}
			if (StringUtil.isNotEmpty(member.getZipcode())
					&& StringUtil.isEmpty(tMemberSchema.getzipcode())
					&& hasUpdated("zipcode")) {
				columns += "zipcode,";
				map = new HashMap<String, Object>();
				try {
					PointsCalculate PointsCalculate = new PointsCalculate();
					// 会员ID
					map.put(IntegralConstant.MEM_ID, member.getId());
					// 积分处理
					PointsCalculate.pointsManage(IntegralConstant.POINT_SEND,
							IntegralConstant.POINT_SOURCE_MEM_ZIP, map);
				} catch (Exception e) {
					logger.error("完善会员邮编送积分异常！MemberId:" + member.getId() + e.getMessage(), e);
				}
			}

			memberSchema sMemberSchema = new memberSchema();
			sMemberSchema.setid(member.getId());
			if (sMemberSchema.fill()) {
				integrals = sMemberSchema.getcurrentValidatePoint() - tMemberSchema.getcurrentValidatePoint();
				if (integrals > 0) {
					request.setAttribute("integrals", "恭喜您获得" + integrals + "积分奖励！");
				}
			}
			columns += tMemberSchema.getHasUpdate();
		}
		oldmember = memberService.load(member.getId());
		oldmember.setHasUpdate(columns);
		oldmember.setAddress(member.getAddress());
		oldmember.setBirthday(member.getBirthday());
		oldmember.setEmail(email);
		oldmember.setFaxNO(member.getFaxNO());
		oldmember.setHobby(member.getHobby());
		oldmember.setIDNO(member.getIDNO());
		oldmember.setIDType(member.getIDType());
		oldmember.setIndustryType(member.getIndustryType());
		oldmember.setLocation(member.getLocation());
		oldmember.setMarriageStatus(member.getMarriageStatus());
		// oldmember.setMobileNO(mobileNO);
		oldmember.setPersonalURL(member.getPersonalURL());
		oldmember.setPosition(member.getPosition());
		oldmember.setQQNO(qqno);
		oldmember.setRealName(realName);
		oldmember.setSex(member.getSex());
		oldmember.setTelephoneNO(member.getTelephoneNO());
		if ("wap".equals(oldmember.getFromWap())) {
			// TODO 如果该会员来自于wap注册 在会员信息修改时 昵称维持wap站昵称
			oldmember.setUsername(oldmember.getUsername());
		} else {
			oldmember.setUsername(uname);
		}
		oldmember.setZipcode(member.getZipcode());
		// oldmember.setCurrentValidatePoint((int) integrals);
		/* zhangjinquan 11180 bug994 会员中心没有保存会员类型 2012-10-17 */
		oldmember.setVIPType(member.getVIPType());
		JdbcTemplateData jtd = new JdbcTemplateData();
		String sql3 = "select codevalue from memberhobby where  member_id=?  and isselected = ?";
		String[] sql3temp = { member.getId(), "Y" };
		List<Map> listhy;
		try {
			listhy = jtd.obtainData(sql3, sql3temp);
			Iterator<Map> it = listhy.iterator();
			while (it.hasNext()) {
				Map map = it.next();
				Dict love = new Dict();
				love.setDictSerial((String) map.get("codeValue"));
				listLove.add(love);

			}
		} catch (InstantiationException e) {
			logger.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
		}

		memberService.update(oldmember);
		Member newMember = memberService.load(oldmember.getId());
		int count = 0;
		if (!(newMember.getBirthday() == null || "".equals(newMember
				.getBirthday())))
			count += 15;
		if (!(newMember.getEmail() == null || "".equals(newMember.getEmail())))
			count += 15;
		if (!(newMember.getIDType() == null || "".equals(newMember.getIDType()) || "-1"
				.equals(newMember.getIDType())))
			count += 4;
		if (!(newMember.getIndustryType() == null || "".equals(newMember
				.getIndustryType())))
			count += 3;
		if (!(newMember.getLocation() == null
				|| "".equals(newMember.getLocation()) || "-1"
					.equals(getLoginMember().getLocation())))
			count += 4;
		if (!(newMember.getMarriageStatus() == null
				|| "".equals(newMember.getMarriageStatus()) || "-1"
					.equals(newMember.getMarriageStatus())))
			count += 4;
		if (!(newMember.getMobileNO() == null || "".equals(newMember
				.getMobileNO())))
			count += 15;
		if (!(newMember.getPosition() == null || "".equals(newMember
				.getPosition())))
			count += 2;
		if (!(newMember.getRealName() == null || "".equals(newMember
				.getRealName())))
			count += 15;
		if (!(newMember.getSex() == null || "".equals(newMember.getSex())))
			count += 15;
		if (!(newMember.getVIPType() == null
				|| "".equalsIgnoreCase(newMember.getVIPType()) || "-1"
					.equals(newMember.getVIPType())))
			count += 4;
		if (!(newMember.getZipcode() == null || "".equals(newMember
				.getZipcode())))
			count += 4;

		String fullDegree = count + "%";
		// 将完整度放入session
		request.getSession().setAttribute("fullDegree", fullDegree);
		memberSchema mmemberSchema = new memberSchema();
		mmemberSchema.setid(newMember.getId());
		if (mmemberSchema.fill()) {
			mmemberSchema.setfullDegree(fullDegree);
			mmemberSchema.update();
		}
		;

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Member", newMember);
		try {
			ActionUtil.dealAction("wj00012", map, getRequest());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		if (uname != null && !"".equals(uname)) {

			// 写入会员登录Cookie
			Cookie loginMemberUsernameCookie;
			try {
				loginMemberUsernameCookie = new Cookie(
						Member.LOGIN_MEMBER_USERNAME_COOKIE_NAME,
						URLEncoder.encode(uname.toLowerCase(), "UTF-8"));
				loginMemberUsernameCookie.setPath(getRequest().getContextPath()
						+ "/");
				getResponse().addCookie(loginMemberUsernameCookie);
				// FrontServerContext

				// 获取服务器应用地址
				GetDBdata db = new GetDBdata();
				String[] temp = { "FrontServerContext" };
				String url = db.getOneValue(
						"select value from zdconfig where type=?", temp);
				if (url.endsWith("/")) {
					loginMemberUsernameCookie.setPath(url);
				} else {
					loginMemberUsernameCookie.setPath(url + "/");
				}
				loginMemberUsernameCookie.setMaxAge(30 * 100000);
				getResponse().addCookie(loginMemberUsernameCookie);

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		redirectionUrl = "profile!edit.action";
		return "success";

	}

	public boolean hasUpdated(String column) {

		memberSchema tMemberSchema = new memberSchema();
		tMemberSchema.setid(member.getId());
		if (tMemberSchema.fill()) {
			String flag = "N";
			if (StringUtil.isNotEmpty(tMemberSchema.getHasUpdate())) {
				String str = tMemberSchema.getHasUpdate().substring(0,
						tMemberSchema.getHasUpdate().lastIndexOf(","));
				String temp[] = str.split(",");
				for (int i = 0; i < temp.length; i++) {
					if (column.equals(temp[i])) {
						flag = "Y";
					}
				}
				if ("N".equals(flag)) {
					return true;
				} else if ("Y".equals(flag)) {
					return false;
				}
			} else {
				return true;
			}
		}
		return false;
	}

	public double getIntegrals(String pointsType) {

		DataTable dt = new QueryBuilder(
				"SELECT points FROM zdPointInfo WHERE pointsType='"
						+ pointsType + "'").executeDataTable();

		return dt.getDouble(0, 0);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setIntegrals(String source, double points) {

		SDIntCalendarSchema tSDIntCalendarSchema = new SDIntCalendarSchema();
		tSDIntCalendarSchema.setID(NoUtil.getMaxID("IntID") + "");
		tSDIntCalendarSchema.setMemberId(member.getId());
		tSDIntCalendarSchema.setIntegral(points + "");
		tSDIntCalendarSchema.setSource(source);// "4"-表示注册赠送积分
		tSDIntCalendarSchema.setManner("0");// 表示收入
		tSDIntCalendarSchema.setStatus("0");
		Map param = new HashMap();
		param.put("PointsGive", "01");
		try {
			Map<String, Object> map = new PointsCalculate().pointsManage(IntegralConstant.POINT_SEARCH, "4", param);
			List<Map<String, Object>> list = (List<Map<String, Object>>) map.get(IntegralConstant.DATA);
			if (list.size() > 0) {
				Map map_data = list.get(0);
				tSDIntCalendarSchema.setDescription(String.valueOf(map_data.get("PointDes")));
			} else {
				tSDIntCalendarSchema.setDescription("注册送积分");
				logger.warn("注册送积分查询规则无数据");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		tSDIntCalendarSchema.setCreateDate(PubFun.getCurrentDate());
		tSDIntCalendarSchema.setCreateTime(PubFun.getCurrentTime());
		tSDIntCalendarSchema.insert();
	}

	// 用户资料预览
	public String readySee() {

		member.setFullDegree(dgree);
		member.setEmail(email);
		member.setMobileNO(mobileNO);
		member.setQQNO(qqno);
		member.setRealName(realName);
		member.setUsername(uname);
		String flag1 = this.getEmailBinding();
		if ("Y".equals(flag1)) {
			member.setIsEmailBinding("Y");
		} else
			member.setIsEmailBinding("N");
		String flag2 = this.getMobileNOBinding();
		if ("Y".equals(flag2)) {
			member.setIsMobileNOBinding("Y");
		} else
			member.setIsMobileNOBinding("N");
		if (member.getBirthday() != null) {
			this.setBrithday(member.getBirthday());
		}
		if (love != null && love.length > 0) {
			for (String s : love) {
				String sqllove = "select codename,codevalue  from  zdcode  where codetype=? and parentcode=?  and codevalue=?";
				String[] sqllovetemp = { "member.Hobby", "hobby", s };
				JdbcTemplateData jtd = new JdbcTemplateData();
				try {
					List<Map> list = jtd.obtainData(sqllove, sqllovetemp);
					Iterator<Map> it = list.iterator();
					while (it.hasNext()) {
						Map map = it.next();
						Dict hobby = new Dict();
						hobby.setDictName((String) map.get("CodeName"));
						hobby.setDictSerial((String) map.get("CodeValue"));
						listHobby.add(hobby);

					}
				} catch (InstantiationException e) {
					logger.error(e.getMessage(), e);
				} catch (IllegalAccessException e) {
					logger.error(e.getMessage(), e);
				} catch (ClassNotFoundException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		if (member.getLocation() != null && !"".equals(member.getLocation())) {
			String sqlLocation = "select name ,parent_id  from area where path=?";
			String[] sqlLocationtemp = { member.getLocation() };
			JdbcTemplateData jtd = new JdbcTemplateData();
			try {
				List<Map> list = jtd.obtainData(sqlLocation, sqlLocationtemp);

				Iterator<Map> it = list.iterator();
				while (it.hasNext()) {
					Map map = it.next();
					Dict location = new Dict();
					location.setDictName((String) map.get("name"));
					location.setDictSerial((String) map.get("parent_id"));
					listLocation.add(location);
					if (location.getDictSerial() != null
							&& !"".equals(location.getDictSerial())) {
						this.getLocationName(location.getDictSerial());
					}

				}
			} catch (InstantiationException e) {
				logger.error(e.getMessage(), e);
			} catch (IllegalAccessException e) {
				logger.error(e.getMessage(), e);
			} catch (ClassNotFoundException e) {
				logger.error(e.getMessage(), e);
			}
		}
		JdbcTemplateData jtd = new JdbcTemplateData();
		String sql = "select codevalue,codename from zdcode where codetype=? and  parentcode=? order by codevalue asc";
		String[] sqltemp = { "member.IDType", "IDType" };
		String sql2 = "select codevalue,codename from zdcode where codetype=? and parentCode=?";
		String[] sql2temp = { "Sex", "Sex" };
		String sql3 = "select codevalue,codename  from  zdcode  where  codetype=? and parentCode=?";
		String[] sql3temp = { "member.marriageStatus", "marriageStatus" };
		try {
			List<Map> list = jtd.obtainData(sql, sqltemp);
			List<Map> listsex = jtd.obtainData(sql2, sql2temp);
			List<Map> listmms = jtd.obtainData(sql3, sql3temp);
			Iterator<Map> it = list.iterator();
			while (it.hasNext()) {
				Map map = it.next();
				Dict idType = new Dict();
				idType.setDictName((String) map.get("CodeName"));
				idType.setDictSerial((String) map.get("CodeValue"));
				listid.add(idType);

			}
			Iterator<Map> itsex = listsex.iterator();
			while (itsex.hasNext()) {
				Map map = itsex.next();
				Dict sex = new Dict();
				sex.setDictName((String) map.get("CodeName"));
				sex.setDictSerial((String) map.get("CodeValue"));
				listSex.add(sex);
			}
			Iterator<Map> itms = listmms.iterator();
			while (itms.hasNext()) {
				Map map = itms.next();
				Dict ms = new Dict();
				ms.setDictName((String) map.get("CodeName"));
				ms.setDictSerial((String) map.get("CodeValue"));
				listms.add(ms);
			}
		} catch (InstantiationException e) {
			logger.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
		}

		return "readySee";
	}

	// 有预览返回到我的资料页面
	public String goBack() {

		member.setEmail(email);
		member.setMobileNO(mobileNO);
		member.setQQNO(qqno);
		member.setRealName(realName);
		member.setUsername(uname);
		String flag1 = this.getEmailBinding();
		if ("Y".equals(flag1)) {
			member.setIsEmailBinding("Y");
		} else
			member.setIsEmailBinding("N");
		String flag2 = this.getMobileNOBinding();
		if ("Y".equals(flag2)) {
			member.setIsMobileNOBinding("Y");
		} else
			member.setIsMobileNOBinding("N");
		if (this.brithday != null || !"".equals(this.brithday)) {
			member.setBirthday(this.brithday);
		}
		if (love != null && love.length > 0) {
			for (String s : love) {
				String sqllove = "select codename,codevalue  from  zdcode  where codetype=? and parentcode=?  and codevalue=?";
				String[] sqllovetemp = { "member.Hobby", "hobby", s };
				JdbcTemplateData jtd = new JdbcTemplateData();
				try {
					List<Map> list = jtd.obtainData(sqllove, sqllovetemp);
					Iterator<Map> it = list.iterator();
					while (it.hasNext()) {
						Map map = it.next();
						Dict hobby = new Dict();
						hobby.setDictName((String) map.get("CodeName"));
						hobby.setDictSerial((String) map.get("CodeValue"));
						listLove.add(hobby);

					}
				} catch (InstantiationException e) {
					logger.error(e.getMessage(), e);
				} catch (IllegalAccessException e) {
					logger.error(e.getMessage(), e);
				} catch (ClassNotFoundException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		JdbcTemplateData jtd = new JdbcTemplateData();
		String sql = "select codevalue,codename from zdcode where codetype=? and  parentcode=? order by codevalue asc";
		String[] sqltemp = { "member.IDType", "IDType" };
		String sql3 = "select codevalue,codename  from  zdcode  where  codetype=? and parentCode=?";
		String[] sql3temp = { "member.marriageStatus", "marriageStatus" };
		String sql2 = "select codevalue,codename from zdcode where codetype=? and parentcode=? order by codevalue asc";
		String[] sql2temp = { "member.Hobby", "hobby" };
		try {
			List<Map> list = jtd.obtainData(sql, sqltemp);
			List<Map> listmms = jtd.obtainData(sql3, sql3temp);
			List<Map> listhy = jtd.obtainData(sql2, sql2temp);
			Iterator<Map> ithy = listhy.iterator();
			while (ithy.hasNext()) {
				Map map = ithy.next();
				Dict ms = new Dict();
				ms.setDictName((String) map.get("CodeName"));
				ms.setDictSerial((String) map.get("CodeValue"));
				listHobby.add(ms);
			}
			Iterator<Map> it = list.iterator();
			while (it.hasNext()) {
				Map map = it.next();
				Dict idType = new Dict();
				idType.setDictName((String) map.get("CodeName"));
				idType.setDictSerial((String) map.get("CodeValue"));
				listid.add(idType);

			}
			Iterator<Map> itms = listmms.iterator();
			while (itms.hasNext()) {
				Map map = itms.next();
				Dict ms = new Dict();
				ms.setDictName((String) map.get("CodeName"));
				ms.setDictSerial((String) map.get("CodeValue"));
				listms.add(ms);
			}
		} catch (InstantiationException e) {
			logger.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
		}
		return "profile_input";
	}

	public String getBandingEmail() {

		return bandingEmail;
	}

	public void setBandingEmail(String bandingEmail) {

		this.bandingEmail = bandingEmail;
	}

	public String getBandingMobile() {

		return bandingMobile;
	}

	public void setBandingMobile(String bandingMobile) {

		this.bandingMobile = bandingMobile;
	}

	/**
	 * 邮箱绑定、修改邮箱.
	 * 
	 * @return
	 */
	public String bandingEmail() {

		HttpServletRequest request = ServletActionContext.getRequest();

		String mid = getParameter("mid");
		String sdcodef = getParameter("sdcodef");
		String svd = (String) getSession("sendValidateCode");
		String emailo = getParameter("emailo");
		String myEm = getParameter("myEm");// 排除自身Email modify by fhz

		try {

			// 验证码失效
			if (svd == null || "".equals(svd)) {
				return ajaxText("nullCode");
			}

			// 邮箱是否被注册
			if (!myEm.equals(emailo) && memberService.isExistByMailbox(emailo)) {
				// 清空验证码
				request.getSession().setAttribute("sendValidateCode", "");
				return ajaxText("repeat");
			}

			// 验证码错误
			if (!sdcodef.equals(svd)) {
				return ajaxText("emailError");
			}

			// 邮箱不能为空
			if (emailo == null || "".equals(emailo)) {
				addActionError("邮箱不能为空!");
				return ERROR;
			}

		} catch (Exception e) {
			addActionError("矮油，出了点小状况,点击<a vlpageid=\"xiaoneng\" exturl=\"http://www.kaixinbao.com/xiaoneng\" id=\"qqwap2\" onclick=\"return(VL_FileDL(this));return false;\" href=\"javascript:void(0);\"><这里></a>！请客服MM帮帮忙吧！");
			return ERROR;
		}

		Member sessionLoginMember = getLoginMember();
		if(sessionLoginMember == null || !sessionLoginMember.getId().equals(mid)){
			return ajaxText("illegal operation");
		}
		
		Member loginMember = memberService.load(mid);
	
		// 会员中心-邮箱绑定
		try {
			if (StringUtil.isEmpty(loginMember.getEmail())) {
				int full = Integer.parseInt(loginMember.getFullDegree()
						.replace("%", ""));
				full += 15;
				loginMember.setFullDegree(full + "%");
				request.getSession().setAttribute("fullDegree", full + "%");
			}

			loginMember.setIsEmailBinding("Y");
			loginMember.setEmail(emailo);
			memberService.update(loginMember);

			try {
				PointsCalculate PointsCalculate = new PointsCalculate();
				Map<String, Object> map = new HashMap<String, Object>();
				// 会员ID
				map.put(IntegralConstant.MEM_ID, loginMember.getId());
				// 邮箱绑定送积分处理
				PointsCalculate.pointsManage(IntegralConstant.POINT_SEND,
						IntegralConstant.POINT_SOURCE_EMAIL, map);
			} catch (Exception e) {
				logger.error("邮箱绑定送积分异常！MemberId:{}", loginMember.getId() + e.getMessage(), e);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ajaxText("sysError");
		} finally {
			// 清空验证码
			request.getSession().setAttribute("sendValidateCode", "");
		}

		if (loginMember.getUsername() != null
				&& !"".equals(loginMember.getUsername())) {

			// 写入会员登录Cookie
			Cookie loginMemberUsernameCookie;
			try {
				loginMemberUsernameCookie = new Cookie(
						Member.LOGIN_MEMBER_USERNAME_COOKIE_NAME,
						URLEncoder.encode(loginMember.getUsername()
								.toLowerCase(), "UTF-8"));
				loginMemberUsernameCookie.setPath(getRequest().getContextPath()
						+ "/");
				getResponse().addCookie(loginMemberUsernameCookie);
				// FrontServerContext

				// 获取服务器应用地址
				GetDBdata db = new GetDBdata();
				String[] temp = { "FrontServerContext" };
				String url = db.getOneValue(
						"select value from zdconfig where type=?", temp);
				if (url.endsWith("/")) {
					loginMemberUsernameCookie.setPath(url);
				} else {
					loginMemberUsernameCookie.setPath(url + "/");
				}
				loginMemberUsernameCookie.setMaxAge(30 * 100000);
				getResponse().addCookie(loginMemberUsernameCookie);

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		} else {

			// 写入会员登录Cookie
			Cookie loginMemberUsernameCookie;
			try {
				loginMemberUsernameCookie = new Cookie(
						Member.LOGIN_MEMBER_USERNAME_COOKIE_NAME,
						URLEncoder.encode(emailo, "UTF-8"));
				loginMemberUsernameCookie.setPath(getRequest().getContextPath()
						+ "/");
				getResponse().addCookie(loginMemberUsernameCookie);
				// FrontServerContext

				// 获取服务器应用地址
				GetDBdata db = new GetDBdata();
				String[] temp = { "FrontServerContext" };
				String url = db.getOneValue(
						"select value from zdconfig where type=?", temp);
				if (url.endsWith("/")) {
					loginMemberUsernameCookie.setPath(url);
				} else {
					loginMemberUsernameCookie.setPath(url + "/");
				}
				loginMemberUsernameCookie.setMaxAge(30 * 100000);
				getResponse().addCookie(loginMemberUsernameCookie);

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}

		}

		return ajaxText("success");

	}

	/**
	 * 注册成功后发送邮件
	 * 
	 * @author fhz
	 * */
	public String verifyEmail() {

		try {
			// 如果失败，重新发送连接
			GetDBdata db = new GetDBdata();
			String sql = "select value from zdconfig where type= 'FrontServerContextPath'";
			frontPage = db.getOneValue(sql);
			String sql1 = "select value from zdconfig where type= 'ServerContext'";
			String url = db.getOneValue(sql1);
			reVerifyURL = url
					+ "/shop/member!reSendVerifyEMail.action?member.id="
					+ member.getId();
			Member verifyMember = memberService.load(member.getId());
			if (verifyMember == null) {
				return "verify_fail";
			}
			mobileOrEmail = verifyMember.getEmail();// 返回页面用下
			Date mailExpiredDate = DateUtils.addMinutes(
					verifyMember.getVerifyEmailSendDate(), 24 * 60);// 单位是分钟
			Date now = new Date();
			String IsEmailBinding = verifyMember.getIsEmailBinding();
			if ("Y".equals(IsEmailBinding)) {
				return "verify_success";
			}
			if (now.after(mailExpiredDate)) {
				logger.info("您的验证链接已经超过24小时，");
				verifyError = "您的验证链接已经超过24小时，";
				return "verify_fail";
			}
			verifyMember.setIsEmailBinding("Y");
			memberService.update(verifyMember);
			try {
				PointsCalculate PointsCalculate = new PointsCalculate();
				Map<String, Object> map = new HashMap<String, Object>();
				// 会员ID
				map.put(IntegralConstant.MEM_ID, member.getId());
				// 邮箱绑定送积分处理
				PointsCalculate.pointsManage(IntegralConstant.POINT_SEND,
						IntegralConstant.POINT_SOURCE_EMAIL, map);
			} catch (Exception e) {
				logger.error("邮箱绑定送积分异常！MemberId:" + member.getId() + e.getMessage(), e);
			}

			return "verify_success";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "verify_fail";
		}

	}

	/**
	 * 绑定手机、修改手机.
	 * 
	 * @return
	 */
	public String bandingMobile() {

		HttpServletRequest request = ServletActionContext.getRequest();

		String mid = getParameter("mid");
		String sdcodes = getParameter("sdcodes");
		String scd = (String) getSession("sendValidateCode");
		String mobileNOO = getParameter("mobileNOO");
		String myMobileNO = getParameter("myMobileNO");// 排除注册手机号 fhz

		// 验证码失效
		if (scd == null || "".equals(scd)) {
			return ajaxText("nullCode");
		}

		// 是否被注册
		if (!myMobileNO.equals(mobileNOO)
				&& memberService.isExistByMobileNO(mobileNOO)) {
			// 清空验证码
			request.getSession().setAttribute("sendValidateCode", "");
			return ajaxText("repeat");
		}

		// 验证码错误
		if (!sdcodes.equals(scd)) {
			return ajaxText("codeError");
		}

		Member sessionLoginMember = getLoginMember();
		if(sessionLoginMember == null || !sessionLoginMember.getId().equals(mid)){
			return ajaxText("illegal operation");
		}
		
		Member loginMember = memberService.load(mid);
		
		// 会员中心-手机修改
		try {
			boolean bandingFlag = false;
			if (!"Y".equals(loginMember.getIsMobileNOBinding())) {
				if (StringUtil.isEmpty(loginMember.getMobileNO())) {
					int full = Integer.parseInt(loginMember.getFullDegree()
							.replace("%", ""));
					full += 15;
					loginMember.setFullDegree(full + "%");
					request.getSession().setAttribute("fullDegree", full + "%");
				}
				bandingFlag = true;
			}

			loginMember.setIsMobileNOBinding("Y");
			loginMember.setMobileNO(mobileNOO);
			memberService.update(loginMember);

			if (bandingFlag) {
				try {
					PointsCalculate PointsCalculate = new PointsCalculate();
					Map<String, Object> map = new HashMap<String, Object>();
					// 会员ID
					map.put(IntegralConstant.MEM_ID, loginMember.getId());
					map.put(IntegralConstant.MOBILENO, mobileNOO);
					// 手机绑定送积分处理
					PointsCalculate.pointsManage(IntegralConstant.POINT_SEND,
							IntegralConstant.POINT_SOURCE_MOBILE, map);
				} catch (Exception e) {
					logger.error("手机绑定送积分异常！MemberId:" + loginMember.getId() + e.getMessage(), e);
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ajaxText("sysError");
		} finally {
			// 清空验证码
			request.getSession().setAttribute("sendValidateCode", "");
		}

		if (loginMember.getUsername() != null
				&& !"".equals(loginMember.getUsername())) {

			// 写入会员登录Cookie
			Cookie loginMemberUsernameCookie;
			try {
				loginMemberUsernameCookie = new Cookie(
						Member.LOGIN_MEMBER_USERNAME_COOKIE_NAME,
						URLEncoder.encode(loginMember.getUsername()
								.toLowerCase(), "UTF-8"));
				loginMemberUsernameCookie.setPath(getRequest().getContextPath()
						+ "/");
				getResponse().addCookie(loginMemberUsernameCookie);
				// FrontServerContext

				// 获取服务器应用地址
				GetDBdata db = new GetDBdata();
				String[] temp = { "FrontServerContext" };
				String url = db.getOneValue(
						"select value from zdconfig where type=?", temp);
				if (url.endsWith("/")) {
					loginMemberUsernameCookie.setPath(url);
				} else {
					loginMemberUsernameCookie.setPath(url + "/");
				}
				loginMemberUsernameCookie.setMaxAge(30 * 100000);
				getResponse().addCookie(loginMemberUsernameCookie);

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		} else {
			// 写入会员登录Cookie
			Cookie loginMemberUsernameCookie;
			try {
				loginMemberUsernameCookie = new Cookie(
						Member.LOGIN_MEMBER_USERNAME_COOKIE_NAME,
						URLEncoder.encode(mobileNOO, "UTF-8"));
				loginMemberUsernameCookie.setPath(getRequest().getContextPath()
						+ "/");
				getResponse().addCookie(loginMemberUsernameCookie);
				// FrontServerContext

				// 获取服务器应用地址
				GetDBdata db = new GetDBdata();
				String[] temp = { "FrontServerContext" };
				String url = db.getOneValue(
						"select value from zdconfig where type=?", temp);
				if (url.endsWith("/")) {
					loginMemberUsernameCookie.setPath(url);
				} else {
					loginMemberUsernameCookie.setPath(url + "/");
				}
				loginMemberUsernameCookie.setMaxAge(30 * 100000);
				getResponse().addCookie(loginMemberUsernameCookie);

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}

		}
		return ajaxText("success");

	}

	public String getMid() {

		return mid;
	}

	public void setMid(String mid) {

		this.mid = mid;
	}

	public String getSdcodef() {

		return sdcodef;
	}

	public void setSdcodef(String sdcodef) {

		this.sdcodef = sdcodef;
	}

	public String getSdcodes() {

		return sdcodes;
	}

	public void setSdcodes(String sdcodes) {

		this.sdcodes = sdcodes;
	}

	public String getMobileNOO() {

		return mobileNOO;
	}

	public void setMobileNOO(String mobileNOO) {

		this.mobileNOO = mobileNOO;
	}

	public String getEmailo() {

		return emailo;
	}

	public void setEmailo(String emailo) {

		this.emailo = emailo;
	}

	public String getMobileNOBinding() {

		return MobileNOBinding;
	}

	public void setMobileNOBinding(String mobileNOBinding) {

		MobileNOBinding = mobileNOBinding;
	}

	public String getEmailBinding() {

		return EmailBinding;
	}

	public void setEmailBinding(String emailBinding) {

		EmailBinding = emailBinding;
	}

	public void setRegisterName(String registerName) {

		this.registerName = registerName;
	}

	public String getRegisterName() {

		return registerName;
	}

	public void setBrithday(String brithday) {

		this.brithday = brithday;
	}

	public String getBrithday() {

		return brithday;
	}

	public void setMemberHobby(MemberHobby memberHobby) {

		this.memberHobby = memberHobby;
	}

	public MemberHobby getMemberHobby() {

		return memberHobby;
	}

	public void setLove(String[] love) {

		this.love = love;
	}

	public String[] getLove() {

		return love;
	}

	public void setListHobby(List<Dict> listHobby) {

		this.listHobby = listHobby;
	}

	public List<Dict> getListHobby() {

		return listHobby;
	}

	public void getLocationName(String parent_id) {

		String sqlName = "select name ,parent_id from area where id=?";
		String[] sqlNametemp = { parent_id };
		JdbcTemplateData jtd = new JdbcTemplateData();
		try {
			List<Map> list = jtd.obtainData(sqlName, sqlNametemp);
			Iterator<Map> it = list.iterator();
			while (it.hasNext()) {
				Map map = it.next();
				Dict location = new Dict();
				location.setDictName((String) map.get("name"));
				location.setDictSerial((String) map.get("parent_id"));
				listLocation.add(location);
				if (location.getDictSerial() != null
						&& !"".equals(location.getDictSerial())) {
					this.getLocationName(location.getDictSerial());
				}

			}
		} catch (InstantiationException e) {
			logger.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
		}

	}

	public String operationRegister() {

		if ("0".equals(source)) {
			return "register";// 直接由页面链接过来，无需验证
		} else {
			return "check"; // 又邮箱链接过来，需要先验证
		}
	}

	public String validateData() {

		if (appName == null || appName.equals("")) {
			addActionError("投保人姓名不能为空");
			return ERROR;
		}
		if (mobileNO == null || mobileNO.equals("")) {
			addActionError("手机号不能为空");
			return ERROR;
		}
		String name;
		String phoneNO;
		String flag = "noPass";
		GetDBdata db = new GetDBdata();
		// if ("0".equals(codeType)) {// 表示非车险产品

		String sql = "select d.applicantname,d.applicantmobile from orders a , orderitem b, information c ,informationappnt d "
				+ " where a.id = b.order_id and c.orderitem_id = b.id and d.information_id = c.id "
				+ " and ordersn=?";
		String[] temp = { serialNO };
		List<HashMap<String, String>> list;
		try {
			list = db.query(sql, temp);
			Iterator<HashMap<String, String>> it = list.iterator();
			while (it.hasNext()) {
				Map map = it.next();
				name = (String) map.get("applicantname");
				phoneNO = (String) map.get("applicantmobile");
				if (name.equals(appName) && phoneNO.equals(mobileNO)) {
					flag = "pass";
				}
			}

			String ordersn = (String) getSession("OrderSn");
			if (StringUtil.isNotEmpty(ordersn)) {
				if (ordersn.indexOf(serialNO) == -1) {
					setSession("OrderSn", ordersn + "," + serialNO);
				}
			} else {
				setSession("OrderSn", serialNO);
			}
			logger.info("OrderSn放入到session内:{}", getSession("OrderSn"));

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		// }
		// else if ("1".equals(codeType)) {// 表示车险一键报价
		// String sql2 =
		// "select TELNO,INSUREDER from sdtargetinformation where id='" +
		// serialNO + "'";
		// List<Map> list2;
		// try {
		// JdbcTemplateData jtd = new JdbcTemplateData();
		// list2 = jtd.obtainData(sql2);
		// Iterator<Map> it2 = list2.iterator();
		// while (it2.hasNext()) {
		// Map map2 = it2.next();
		// name = (String) map2.get("INSUREDER");
		// phoneNO = (String) map2.get("TELNO");
		// if (name.equals(appName) && phoneNO.equals(mobileNO)) {
		// flag = "pass";
		// }
		// }
		// } catch (InstantiationException e) {
		//
		// e.printStackTrace();
		// } catch (IllegalAccessException e) {
		//
		// e.printStackTrace();
		// } catch (ClassNotFoundException e) {
		//
		// e.printStackTrace();
		// }
		//
		// }
		if (flag.equals("pass")) {
			return "register";
		} else {
			addActionError("数据不正确");
			return ERROR;
		}
	}

	public String register() throws Exception {

		if (!StringUtil.isMail(registerUserName)
				&& !StringUtil.isMobileNO(registerUserName)) {
			addActionError("正确的邮箱地址或手机号是登录的唯一凭证哟！");
			return ERROR;

		}
		if (StringUtil.isEmpty(validateCode)) {
			addActionError("这里一定要写上验证码才行哟！");
			return ERROR;

		}
		String sendCode = (String) getSession("sendValidateCode");
		if (StringUtil.isEmpty(sendCode) || !validateCode.equals(sendCode)) {
			addActionError("验证码输入不正确！");
			return ERROR;

		}
		try {
			if (StringUtil.isMail(registerUserName)) {// 邮箱注册
				if (memberService.isExistByMailbox(registerUserName)) {
					addActionError("邮箱号已被注册！");
					return ERROR;
				}
				this.registerName = registerUserName;
				member.setEmail(registerUserName);
				member.setRegisterType("0");// 表示邮箱注册
			}
		} catch (Exception e) {
			addActionError("矮油，出了点小状况,点击<a vlpageid=\"xiaoneng\" exturl=\"http://www.kaixinbao.com/xiaoneng\" id=\"qqwap2\" onclick=\"return(VL_FileDL(this));return false;\" href=\"javascript:void(0);\"><这里></a>！请客服MM帮帮忙吧！");
			return ERROR;
		}
		if (StringUtil.isMobileNO(registerUserName)) {// 手机注册
			try {
				if (memberService.isExistByMobileNO(registerUserName)) {
					addActionError("这个手机号已经注册过了！");
					return ERROR;
				}
			} catch (Exception e) {
				addActionError("矮油，出了点小状况，请客服MM帮帮忙吧！");
				return ERROR;
			}
			this.registerName = registerUserName;
			member.setMobileNO(registerUserName);
			member.setRegisterType("1");// 表示手机注册
		}
		if (!StringUtil.isPasword(member.getPassword())) {
			addActionError("密码由6-16个英文、数字、及下划线组成且不能全部是数字！");
			return ERROR;

		}
		if (StringUtil.isEmpty(rePassword)) {
			addActionError("密码确认不能为空！");
			return ERROR;

		}
		if (!rePassword.equals(member.getPassword())) {
			addActionError("两次密码输入不一致！");
			return ERROR;

		}
		long currentTime = System.currentTimeMillis();
		long sendTime = (Long) getSession("sendTime");
		// if ("0".equals(member.getRegisterType())) {
		// if ((currentTime - sendTime) > 5 * 60 * 1000) {
		// addActionError("验证码时间已超过5分钟！");
		// return ERROR;
		// }
		// } else {
		// if ((currentTime - sendTime) > 120 * 60 * 1000) {
		//
		// addActionError("验证码时间已超过120分钟！");
		// return ERROR;
		// }
		// }

		if (!(member.getUsername() == null || "".equals(member.getUsername()))) {
			if (member.getUsername().length() > 16
					|| member.getUsername().trim().length() < 4) {
				addActionError("用户名由4-16个英文字符、数字、下划线组成且不能以数字开头！");
				return ERROR;

			}
			Pattern pattern = Pattern.compile("^[a-zA-Z_][a-zA-Z_0-9]{3,16}$");
			Matcher matcher = pattern.matcher(member.getUsername());
			if (!matcher.matches()) {
				addActionError("用户名由4-16个英文字符、数字、下划线组成且不能以数字开头！");
				return ERROR;

			}
			try {
				if (memberService.isExistByUsername(member.getUsername())) {
					addActionError("该用户名已存在！");
					return ERROR;

				}
			} catch (Exception e) {
				addActionError("矮油，出了点小状况,点击<a vlpageid=\"xiaoneng\" exturl=\"http://www.kaixinbao.com/xiaoneng\" id=\"qqwap2\" onclick=\"return(VL_FileDL(this));return false;\" href=\"javascript:void(0);\"><这里></a>！请客服MM帮帮忙吧！");
				return ERROR;
			}
		}
		if (!(member.getQQNO() == null || "".equals(member.getQQNO()))) {
			try {
				if (memberService.isExistByUsername(member.getQQNO())) {
					addActionError("该QQ号已存在！");
					return ERROR;

				}
			} catch (Exception e) {
				addActionError("矮油，出了点小状况,点击<a vlpageid=\"xiaoneng\" exturl=\"http://www.kaixinbao.com/xiaoneng\" id=\"qqwap2\" onclick=\"return(VL_FileDL(this));return false;\" href=\"javascript:void(0);\"><这里></a>！请客服MM帮帮忙吧！");
				return ERROR;
			}
		}

		if (!(member.getRealName() == null || "".equals(member.getRealName()))) {
			Pattern pattern = Pattern.compile("^[a-zA-Z\u4e00-\u9fa5]+$");
			Matcher matcher = pattern.matcher(member.getRealName());
			if (!matcher.matches()) {
				addActionError("真实姓名只能中文或者英文，不能有特殊字符！");
				return ERROR;

			}

		}
		if (isAgreeAgreement == null || isAgreeAgreement == false) {
			addActionError("小手勾选同意协议，成功注册so easy！");
			return ERROR;

		}
		if (!getSystemConfig().getIsRegister()) {
			addActionError("亲，本站暂时不支持会员注册，敬请期待开放！");
			return ERROR;
		}

		int count = 0;
		if (!(member.getAddress() == null || "".equals(member.getAddress())))
			count++;
		if (!(member.getBirthday() == null || "".equals(member.getBirthday())))
			count++;
		if (!(member.getEmail() == null || "".equals(member.getEmail())))
			count++;
		if (!(member.getFaxNO() == null || "".equals(member.getFaxNO())))
			count++;
		if (love != null)
			count++;
		if (!(member.getIDNO() == null || "".equals(member.getIDNO())))
			count++;
		if (!(member.getIDType() == null || "".equals(member.getIDType()) || "-1"
				.equals(member.getIDType())))
			count++;
		if (!(member.getIndustryType() == null || "".equals(member
				.getIndustryType())))
			count++;
		if (!(member.getLocation() == null || "".equals(member.getLocation())))
			count++;
		if (!(member.getMarriageStatus() == null
				|| "".equals(member.getMarriageStatus()) || "-1".equals(member
				.getIDType())))
			count++;
		if (!(member.getMobileNO() == null || "".equals(member.getMobileNO())))
			count++;
		if (!(member.getPersonalURL() == null || "".equals(member
				.getPersonalURL())))
			count++;
		if (!(member.getPosition() == null || "".equals(member.getPosition())))
			count++;
		if (!(member.getQQNO() == null || "".equals(member.getQQNO())))
			count++;
		if (!(member.getRealName() == null || "".equals(member.getRealName())))
			count++;
		if (!(member.getSex() == null || "".equals(member.getSex())))
			count++;
		if (!(member.getTelephoneNO() == null || "".equals(member
				.getTelephoneNO())))
			count++;
		if (!(member.getUsername() == null || "".equals(member.getUsername())))
			count++;
		if (!(member.getVIPType() == null
				|| "".equalsIgnoreCase(member.getVIPType()) || "-1"
					.equals(member.getIDType())))
			count++;
		if (!(member.getZipcode() == null || "".equals(member.getZipcode())))
			count++;
		double a = (count / 20.0) * 100;
		DecimalFormat myformat = new DecimalFormat("#####0");
		String fullDegree = myformat.format(a) + "%";
		member.setFullDegree(fullDegree);

		member.setPassword(DigestUtils.md5Hex(member.getPassword()));
		member.setSafeQuestion(null);
		member.setSafeAnswer(null);
		member.setMemberRank(memberRankService.getDefaultMemberRank());
		member.setPoint(0);
		member.setDeposit(new BigDecimal("0"));
		member.setIsAccountEnabled("Y");
		member.setIsAccountLocked("N");
		member.setLoginFailureCount(0);
		member.setPasswordRecoverKey(null);
		member.setLockedDate(null);
		member.setLoginDate(new Date());
		member.setRegisterIp(getRequest().getRemoteAddr());
		member.setLoginIp(getRequest().getRemoteAddr());
		member.setMemberAttributeMap(null);
		member.setReceiverSet(null);
		member.setFavoriteProductSet(null);
		member.setUsedPoint(0);
		member.setCurrentValidatePoint(0);
		member.setExpiricalValue(0);
		BindInfoForLogin bindInfoForLogin = new BindInfoForLogin();
		bindInfoForLogin.setId("");
		member.setmBindInfoForLogin(bindInfoForLogin);
 		if(StringUtil.isEmpty(member.getEmail())){
			member.setEmail("");
		}
		if(StringUtil.isEmpty(member.getMobileNO())){
			member.setMobileNO("");
		}
		memberService.save(member);

		if ("0".equals(member.getRegisterType())
				&& member.getVIPFrom().equals("0")) {
			member.setIsEmailBinding("Y");
			member.setIsMobileNOBinding("N");
			memberService.save(member);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Member", member);
			try {
				Map<String,Object> mailParam = new HashMap<String,Object>();
				mailParam.put("memberId", member.getId());
				ActionUtil.sendMessage("wj0003", mailParam);
				ActionUtil.dealAction("wj0003", map, getRequest());
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		} else if ("1".equals(member.getRegisterType())
				&& member.getVIPFrom().equals("0")) {
			member.setIsMobileNOBinding("Y");
			member.setIsEmailBinding("N");
			memberService.save(member);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Member", member);
			try {
				ActionUtil.sendSms("wj0004", member.getMobileNO(), "");
				ActionUtil.dealAction("wj0004", map, getRequest());
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}

		// if (member.getVIPFrom().equals("1")) {// 表示操作后注册
		// if (codeType.equals("0")) {
		// Order order = orderService.getOrderByOrderSn(serialNO);
		// order.setMemberId(member.getId());
		// orderService.update(order);
		// member.setCurrentValidatePoint(order.getPoint());
		// memberService.update(member);
		//
		// }
		// // if (codeType.equals("1")) {// 车险一键报价
		// // SDtargetInformation sdTargetInformation =
		// sdTargetInformationService.get(serialNO);
		// // sdTargetInformation.setMemberId(member.getId());
		// // sdTargetInformationService.update(sdTargetInformation);
		// // ShowInsurance showInsurance = showInsuranceService.get(serialNO);
		// // showInsurance.setMemberId(member.getId());
		// // showInsuranceService.update(showInsurance);
		// //
		// // }
		//
		// }
		JdbcTemplateData jtd = new JdbcTemplateData();
		String sql3 = "select codevalue from zdcode where codetype=? and parentcode=? order by codevalue asc";
		String[] sql3temp = { "member.Hobby", "hobby" };
		List<Map> listhy;
		try {
			listhy = jtd.obtainData(sql3, sql3temp);
			Iterator<Map> it = listhy.iterator();
			while (it.hasNext()) {
				MemberHobby mh = new MemberHobby();
				mh.setCodeValue((String) it.next().get("CodeValue"));
				mh.setIsSelected("N");
				mh.setMember(member);
				memberHobbyService.save(mh);
			}
		} catch (InstantiationException e) {
			logger.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
		}

		if (love != null && love.length > 0) {
			for (String s : love) {
				String sql5 = "update memberhobby set isselected='Y' where codevalue=? and  member_id=?";
				String[] sql5temp = { s, member.getId() };
				JdbcTemplateData jtdss = new JdbcTemplateData();
				jtdss.updateOrSaveOrDelete(sql5, sql5temp);
			}
		}
		// 写入会员登录Session
		setSession(Member.LOGIN_MEMBER_ID_SESSION_NAME, member.getId());

		if (member.getUsername() != null && !"".equals(member.getUsername())) {
			registerName = member.getUsername();
		}
		// 写入会员登录Cookie
		Cookie loginMemberCookie = new Cookie(
				Member.LOGIN_MEMBER_USERNAME_COOKIE_NAME, URLEncoder.encode(
						registerName, "UTF-8"));
		loginMemberCookie.setPath(getRequest().getContextPath() + "/");
		getResponse().addCookie(loginMemberCookie);
		GetDBdata db = new GetDBdata();
		try {
			String[] temp = { "FrontServerContext" };
			String url = db.getOneValue(
					"select value from zdconfig where type=?", temp);
			if (url.endsWith("/")) {
				loginMemberCookie.setPath(url);
			} else {
				loginMemberCookie.setPath(url + "/");
			}
			loginMemberCookie.setMaxAge(30 * 100000);
			getResponse().addCookie(loginMemberCookie);
		} catch (Exception e) {
		}
		// 合并购物车
		Cookie[] cookies = getRequest().getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (StringUtils.equalsIgnoreCase(cookie.getName(),
						CartItemCookie.CART_ITEM_LIST_COOKIE_NAME)) {
					if (StringUtils.isNotEmpty(cookie.getValue())) {
						JsonConfig jsonConfig = new JsonConfig();
						jsonConfig.setRootClass(CartItemCookie.class);
						JSONArray jsonArray = JSONArray.fromObject(cookie
								.getValue());
						List<CartItemCookie> cartItemCookieList = (List<CartItemCookie>) JSONSerializer
								.toJava(jsonArray, jsonConfig);
						for (CartItemCookie cartItemCookie : cartItemCookieList) {
							Product product = productService
									.load(cartItemCookie.getI());
							if (product != null) {
								CartItem cartItem = new CartItem();
								cartItem.setMember(member);
								cartItem.setProduct(product);
								cartItem.setQuantity(cartItemCookie.getQ());
								cartItemService.save(cartItem);
							}
						}
					}
				}
			}
		}

		if (StringUtils.isNotEmpty(windowNewAddr)) {
			setSession(Member.LOGIN_REDIRECTION_URL_SESSION_NAME, windowNewAddr);
		}

		// 清空临时购物车Cookie
		Cookie cartItemCookie = new Cookie(
				CartItemCookie.CART_ITEM_LIST_COOKIE_NAME, null);
		cartItemCookie.setPath(getRequest().getContextPath() + "/");
		cartItemCookie.setMaxAge(0);
		getResponse().addCookie(cartItemCookie);

		String redirectionUrl = (String) getSession(Member.LOGIN_REDIRECTION_URL_SESSION_NAME);
		getRequest().getSession().removeAttribute(
				Member.LOGIN_REDIRECTION_URL_SESSION_NAME);
		/* zhangjinquan 11180 需求-注册增加统计页面-订单结果注册 2012-10-26 start */
		if (StringUtils.isEmpty(redirectionUrl)) {
			// return "memberCenterIndexAction";
			String sql = "select value from zdconfig where type= 'ServerContext'";
			String url = db.getOneValue(sql);
			String path = url + "/shop/member_center!index.action";
			this.setWindowNewAddr(path);

		} else {
			// getResponse().sendRedirect(redirectionUrl);
			// return null;
			this.setWindowNewAddr(redirectionUrl);
		}
		GALog.saveGALogRecord(gaLogService, GALog.REGISTER_SUCCESS,
				member.getId(), null);
		/* meijunfeng 20121128002-需求系统开发需求申请单-CPA申请需求 2012-12-12 start */
		RegistSuccessInfoCommit.infoCommitForEmar(getRequest(), member);
		/* meijunfeng 20121128002-需求系统开发需求申请单-CPA申请需求 2012-12-12 end */
		return "registerGA";
		/* zhangjinquan 11180 需求-注册增加统计页面-订单结果注册 2012-10-26 end */
	}

	public String isLogin() {

		String id = (String) getSession(Member.LOGIN_MEMBER_ID_SESSION_NAME);
		if (StringUtil.isEmpty(id)) {
			return ajaxJson("-1");

		}
		Member member = getLoginMember();
		if (member != null) {
			return ajaxJson(member.getCurrentValidatePoint() + "");
		}
		return ajaxJson("-1");
	}

	/* 保存网页跳转前的地址 */
	private String windowAddr;
	/* 保存网页跳转后的地址 */
	private String windowNewAddr;
	/* 保存注册来源：'ajaxregister'--浮动层注册； */
	private String registerFrom;
	/* 注册成功后显示的消息 */
	private String message;

	public String getWindowAddr() {

		return windowAddr;
	}

	public void setWindowAddr(String windowAddr) {

		this.windowAddr = windowAddr;
	}

	public String getWindowNewAddr() {

		return windowNewAddr;
	}

	public void setWindowNewAddr(String windowNewAddr) {

		this.windowNewAddr = windowNewAddr;
	}

	public String getRegisterFrom() {

		return registerFrom;
	}

	public void setRegisterFrom(String registerFrom) {

		this.registerFrom = registerFrom;
	}

	public String getMessage() {

		return message;
	}

	public void setMessage(String message) {

		this.message = message;
	}

	/* zhangjinquan 转向GA统计页面 meber_register_GA.ftl */
	public String registerGA() {

		return "registerGA";
	}

	public String getType() {

		return type;
	}

	public void setType(String type) {

		this.type = type;
	}

	public BindInfoForLogin getBindInfoForLogin() {

		return bindInfoForLogin;
	}

	public void setBindInfoForLogin(BindInfoForLogin bindInfoForLogin) {

		this.bindInfoForLogin = bindInfoForLogin;
	}

	public String getTagid() {

		return tagid;
	}

	public void setTagid(String tagid) {

		this.tagid = tagid;
	}

	public static void main(String[] args) throws Exception {

		MemberAction action = new MemberAction();
		action.setRegisterUserName("15040566026");
		action.setLoginPassword("wang2013");
		action.setRePassword("wang2013");
		action.newAjaxRegister();
	}

	/**
	 * @Title: renewalSkip.
	 * @Description: TODO(邮件点击续保、跳转).
	 * @return String.
	 * @author CongZN.
	 */
	public void renewalSkip() {

		if (StringUtil.isNotEmpty(productId)) {
			QueryBuilder query_productDetailUrl = new QueryBuilder(
					"select Url from SDSearchRelaProduct where ProductID = ?");
			query_productDetailUrl.add(productId);
			productDetailUrl = query_productDetailUrl.executeString();
		}
		try {
			getResponse().sendRedirect(productDetailUrl);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
	public void ajaxChecklogin(Map<String, Object> jsonMap, Member lm){
	
		if (lm != null) {
			jsonMap.put("status", "0");
			jsonMap.put("MemberUsername", getSession("loginName"));
			jsonMap.put("MemberPoints", String.valueOf(lm.getCurrentValidatePoint()));
			jsonMap.put("grade", lm.getGrade());
			String picpath = "";
			if(StringUtil.isEmpty(lm.getHeadPicPath())){
				picpath = Config.getValue("FrontServerContextPath")+"/images/redesign/photo_06.gif";
			}else{
				picpath = Config.getValue("StaticResourcePath")+"/"+lm.getHeadPicPath();
			}
			jsonMap.put("headPicPath", picpath);
			
			MemberCenterAction mem = new MemberCenterAction();
			Map<String, String> gradeMap = mem.getGradeIcon(lm.getVipFlag(),
					lm.getGrade(), "", lm.getBirthday(), lm.getBirthYear());
			if (StringUtil.isNotEmpty(gradeMap.get("gradeClass"))) {
				jsonMap.put("gradeClass", gradeMap.get("gradeClass").replace("vip_k ", "vip_k_top vip_top "));
			}
			int tCount = 0;
			//优惠卷
			StringBuffer sb = new StringBuffer(" select count(1) from CouponInfo  where status='2' and memberId = ? and DATE_FORMAT(endTime,'%Y-%m-%d %H:%i:%s') > DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%s')");
			tCount = new QueryBuilder(sb.toString(),lm.getId()).executeInt();
			jsonMap.put("CouponCount", String.valueOf(tCount));
			//待支付订单数量
			sb = new StringBuffer("SELECT COUNT(DISTINCT COALESCE(paysn, id)) FROM sdorders WHERE memberid=? AND orderstatus=? AND channelsn != 'jfsc' AND channelsn not in (SELECT cb.channelcode FROM channelinfo ca, channelinfo cb  WHERE ca.channelcode = 'jfsc' AND ca.InnerChannelCode = cb.ParentInnerChanelCode) AND ( dellFlag IS NULL OR dellFlag = '')");
			tCount = new QueryBuilder(sb.toString(),lm.getId(),"5").executeInt();
			jsonMap.put("PrePay", String.valueOf(tCount));
			//带生效
			sb = new StringBuffer("SELECT COUNT(DISTINCT COALESCE(paysn, b.id)) FROM sdorders a,sdinformation b WHERE a.ordersn = b.ordersn AND a.memberid=? AND a.orderstatus IN ('7','10','12','14') AND DATE_FORMAT(b.startDate,'%Y-%m-%d %H:%i:%s')>DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%s') AND channelsn != 'jfsc' AND channelsn not in (SELECT cb.channelcode FROM channelinfo ca, channelinfo cb  WHERE ca.channelcode = 'jfsc' AND ca.InnerChannelCode = cb.ParentInnerChanelCode) AND ( dellFlag IS NULL OR dellFlag = '')");
			tCount = new QueryBuilder(sb.toString(),lm.getId()).executeInt();
			jsonMap.put("Payed", String.valueOf(tCount));
			//已生效
			sb = new StringBuffer("SELECT COUNT(DISTINCT COALESCE(paysn, b.id)) FROM sdorders a,sdinformation b WHERE a.ordersn = b.ordersn AND a.memberid=? AND a.orderstatus IN ('7','10','12','14')  AND DATE_FORMAT(b.startDate,'%Y-%m-%d %H:%i:%s')<=DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s') AND DATE_FORMAT(b.endDate,'%Y-%m-%d %H:%i:%s')>=DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s') AND channelsn != 'jfsc' AND channelsn not in (SELECT cb.channelcode FROM channelinfo ca, channelinfo cb  WHERE ca.channelcode = 'jfsc' AND ca.InnerChannelCode = cb.ParentInnerChanelCode) AND ( dellFlag IS NULL OR dellFlag = '')");
			tCount = new QueryBuilder(sb.toString(),lm.getId()).executeInt();
			jsonMap.put("Effective", String.valueOf(tCount));
			//购物车信息
			List<SDShoppingCart> cartList = sdShoppingCartService.getShowShopCartList(lm.getId());
			//会员升级信息 
			sb = new StringBuffer("SELECT b.gradecode,b.sumprem-if (a.consumeamount is null, 0, a.consumeamount),CAST(round(b.ordercount-if (a.buycount is null, 0, a.buycount) ,0) AS CHAR) FROM member a LEFT JOIN membergrade b ON a.grade = b.pregradecode WHERE a.id=? ");
			DataTable dt = new QueryBuilder(sb.toString(),lm.getId()).executeDataTable();
			if(dt!=null && dt.getRowCount()>=1 && StringUtil.isNotEmpty(dt.getString(0, 0))){
//				jsonMap.put("gradeinfo", "消费"+dt.getString(0, 2)+"次满"+dt.getString(0, 1)+"元升级"+dt.getString(0, 0)+"会员");
				jsonMap.put("gradeinfo", gradeMap.get("gradeInfo"));
			}else if("Y".equals(lm.getVipFlag())){
				jsonMap.put("gradeinfo", gradeMap.get("gradeInfo"));
			}else{
				jsonMap.put("gradeinfo", "申请VIP！享受更多特权！");
			}
			sb = new StringBuffer("");
			BigDecimal amount = new BigDecimal("0");
			List<SDOrder> paramterList = new ArrayList<SDOrder>();
			for(SDShoppingCart t:cartList){
				SDOrder order=sdorderService.getOrderByOrderSn(t.getOrderSn());
				paramterList.add(order);
			}
			
			if (paramterList.size() > 0) {
				sb = new StringBuffer("");
				//筛选优惠活动
				Map<String, Map<String, Object>> activity_product_info1 = ActivityCalculate.ProductShoppingCartInfo(paramterList,paramterList.get(0).getChannelsn(),true);
				//总金额
				BigDecimal totalamount=new BigDecimal("0");
				for (int i = 0; i < cartList.size(); i++) {
					SDShoppingCart sdshoppingcart=cartList.get(i);
					//遍历优惠信息Map
					Set keySet = activity_product_info1.keySet();
				    for(Iterator it = keySet.iterator();it.hasNext();){ 
				    	//活动号（包含“_no_activity”）
				    	String activitysn=String.valueOf(it.next());
				    	//获取该活动关联的所有信息（包括活动信息，产品信息，优惠信息）
				        Map<String,Object> map_info = activity_product_info1.get(activitysn);
				        //获取产品信息list
				        List<Map<String,String>> list_product=(List<Map<String, String>>) map_info.get("ProductInfo");
				        Map<String,String> map_activityinfo=(Map<String, String>) map_info.get("ActivityInfo");
				        Map<String,String> map_activityamont=(Map<String, String>) map_info.get("ActivityAmont");
				        String type=map_activityinfo.get("type");
				        if(i==0){
					        //总金额
					        if("6".equals(type)){
					        	 String TotalAmount=map_activityamont.get("RealAmount");
							     totalamount=totalamount.add(new BigDecimal(TotalAmount));
					        }else{
					        	 String TotalAmount=map_activityamont.get("TotalAmount");
							     totalamount=totalamount.add(new BigDecimal(TotalAmount));
					        }
				        }
				        for (int j = 0; j < list_product.size(); j++) {
				        	//活动每个产品对应的Map
				        	Map<String,String> map_product=list_product.get(j);
				        	if(sdshoppingcart.getOrderSn().equals(map_product.get("OrderSn"))){
				        		sb.append("<li><a href='"+sdshoppingcart.getDetailPath()+"'>"+sdshoppingcart.getProductName()+"</a><em><i>￥"+map_product.get("ActivityeAmount")+"</i>×1</em></li>");
				        	}
				        	
				        }
				    }
				}
				amount = totalamount;
			}
			
			jsonMap.put("carlist", sb.toString());
			jsonMap.put("shoptotal", "￥"+amount.toString());
			jsonMap.put("shopcount", cartList.size());
		}
	}
	
	/**
	 * checkMemberIntegral:设置会员可用积分
	 * 如果会员可用积分大于积分详细表非冻结积分之和则返回详细表非冻结积分之和. <br/>
	 * @author guozc
	 * @param member 当前登录会员
	 */
	public void setValiintegral(Member loginMember) {
		int dealCount = 0;
		do{
			int currentValidatePoint = loginMember.getCurrentValidatePoint(); // 当前可用积分
			int revenueIntegral = 0; // 收入总积分
			int payoutIntegral = 0; // 支出总积分
			int integralSum = 0; // 详细表未冻结积分
			String memberId = loginMember.getId();
			String version = loginMember.getVersion();
			QueryBuilder qb = new QueryBuilder(
					"select sum(Integral),Manner from sdintcalendar where MemberId = ? and `Status` = '0' and Manner != '2' group by Manner",
					memberId);
			DataTable dt = qb.executeDataTable();
			for (int i = 0; i < dt.getRowCount(); i++) {
				int integral = dt.getInt(i, 0);
				String manner = dt.getString(i, 1);
				if ("0".equals(manner)) {
					revenueIntegral = integral;
				} else if("1".equals(manner)) {
					payoutIntegral = integral;
				}
			}
			integralSum = revenueIntegral - payoutIntegral;
			if (integralSum < 0 || currentValidatePoint > integralSum) {
				if(integralSum < 0){
					integralSum = 0;
				}
				QueryBuilder uptIntegral = new QueryBuilder(
						"update member set currentValidatePoint = ?,version = ? where id = ?");
				if(StringUtil.isEmpty(version)){
					uptIntegral.append(" and version is null");
				}else{
					uptIntegral.append(" and version = ?");
				}
				uptIntegral.add(integralSum);
				if(StringUtil.isNotEmpty(version)){
					uptIntegral.add(Integer.parseInt(version)+1);
				}else{
					uptIntegral.add("1");
				}
				uptIntegral.add(memberId);
				if(StringUtil.isNotEmpty(version)){
					uptIntegral.add(version);
				}
				int updateRows = uptIntegral.executeNoQuery();
				if(updateRows <= 0){
					loginMember = memberService.get(loginMember.getId());
					dealCount ++;
					continue;
				}else{
					MemberIntegralLogSchema log = new MemberIntegralLogSchema();
					log.setMemberId(memberId);
					log.setOldValidIntegral(currentValidatePoint);
					log.setRevenueIntegral(revenueIntegral);
					log.setPayoutIntegral(payoutIntegral);
					log.setNowValidIntegral(integralSum);
					log.setModifyDate(new Date());
					log.insert();
				}
			}
			break;
		}while(dealCount < Constant.UPDATE_MEMBER_DEAL_COUNT);
	}
	
	public void JedisCommonUtilPutIn(String info,String date ,String key){
		if (StringUtil.isEmpty(info)) {
			JedisCommonUtil.setString(3, key, date+"&1");
		} else {
			String[] remarks = info.split("&");
			// 判断redis中记录的是否是今天登陆的记录
			if (date.equals(remarks[0])) {
				// 是今天的记录，在其基础上+1
				JedisCommonUtil.setString(3, key, date+"&"+(Integer.valueOf(remarks[1])+1));
			} else {
				// 不是今天的记录则重置
				JedisCommonUtil.setString(3, key, date+"&1");
			}
		}
		JedisCommonUtil.expire(3, key, DateUtil.getSecondsToTodayZeroTime());
	}
}