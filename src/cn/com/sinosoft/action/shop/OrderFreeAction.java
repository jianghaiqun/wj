package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.OrderDutyFactor;
import cn.com.sinosoft.entity.OrderRiskAppFactor;
import cn.com.sinosoft.entity.SDInformation;
import cn.com.sinosoft.entity.SDInformationAppnt;
import cn.com.sinosoft.entity.SDInformationDuty;
import cn.com.sinosoft.entity.SDInformationInsured;
import cn.com.sinosoft.entity.SDInformationInsuredElements;
import cn.com.sinosoft.entity.SDInformationRiskType;
import cn.com.sinosoft.entity.SDOrder;
import cn.com.sinosoft.entity.SDOrderItem;
import cn.com.sinosoft.entity.SDOrderItemOth;
import cn.com.sinosoft.entity.TradeInformation;
import cn.com.sinosoft.service.DealDataService;
import cn.com.sinosoft.service.MemberService;
import cn.com.sinosoft.service.OrderFreeService;
import cn.com.sinosoft.service.SDOrderService;
import cn.com.sinosoft.service.TradeInformationService;
import cn.com.sinosoft.util.OrderFreeUtil;
import cn.com.sinosoft.util.WapShoppingUtil;
import com.octo.captcha.service.CaptchaService;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.wangjin.optimize.register.AutoRegister;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 春节赠险活动
 * 
 * @author sinosoft
 * 
 */
@ParentPackage("shop")
public class OrderFreeAction extends BaseShopAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private OrderFreeService mOrderFreeService;
	@Resource
	private SDOrderService sdorderService;
	@Resource
	private DealDataService mDealDataService;
	@Resource
	private TradeInformationService tradeInformationService;
	@Resource
	private CaptchaService captchaService;
	@Resource
	private MemberService memberService;
	
	private SDInformationAppnt sdinformationAppnt = new SDInformationAppnt();
	private String mProductID = "";
	private String mFrom = "wj";// 订单来源：wj、wap站
	private String insuredDate = Config.getValue("FreeInsuredDate");//起保日期
	private String verifyCode;//验证码
	private String freeInsuredCount;//赠险领取人数
	private String memberid;//预览页使用，会员
    private String orderSn;//预览页使用，订单号
    private String loginFlag="Y";//判断是否为新会员，N：不是新会员;Y:是新会员
	

	public String buyNow() {

		if(StringUtil.isEmpty(mFrom)){
			mFrom = "wj";
		}
		mProductID = Config.getValue("FreeProductID");//产品
		freeInsuredCount =  new QueryBuilder(" SELECT `value` FROM zdconfig WHERE `type` ='FreeInsuedCount'").executeString();
		if("wap".equals(mFrom)){
			return "wapinput";
		}
		return "input";
	}

	@SuppressWarnings("unchecked")
	public String save() {
		mProductID = Config.getValue("FreeProductID");//产品
	    Properties props = new Properties();
	    InputStream is = null;
		try {
		    is = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("freeproduct.properties");
			InputStreamReader rd = new InputStreamReader(is, "UTF-8");
			props.load(rd);
			// props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("waperrorinfos.properties"));
		} catch (IOException e) {
			logger.error("赠险活动，获取产品信息失败！" + e.getMessage(), e);
		} finally{
			try {
				is.close();
			} catch (IOException e) {
				logger.error("赠险活动，获取产品信息失败！" + e.getMessage(), e);
			}
		}
		mOrderFreeService.setProp(props);
		String cProductID =  String.valueOf(props.get("ProductID"));//产品
		if(!mProductID.equals(cProductID)){
			logger.error("未查询到参与活动的产品，请联系客服！");
			addActionError("未查询到参与活动的产品，请联系客服！");
			return "freeerror";
		}
		//起保日期校验
		if(DateUtil.compareDateTime(DateUtil.toDateTimeString(new Date()), insuredDate)>=0){
			logger.error("开心保新年赠险活动已过期！");
			addActionError("开心保新年赠险活动已过期！");
			return "freeerror";
		}
		// 身份证校验
		if (!mOrderFreeService.checkCardID(mProductID,
				sdinformationAppnt.getApplicantIdentityId())) {
			logger.error("被保人超过最大限购份数！");
			addActionError("被保人超过最大限购份数！");
			return "freeerror";
		}
		Map<String, Object> paramter = new HashMap<String, Object>();
		paramter = sdorderService.getProductInformation(mProductID, "N", "");// 产品ID
		String[] BaseInformation = new String[17];
		BaseInformation = (String[]) paramter.get("baseInformation");// 产品基础数据
		List<OrderRiskAppFactor> riskAppFactior = (List<OrderRiskAppFactor>) paramter
				.get("riskAppFactor");
		List<OrderDutyFactor> dutyFactor = (List<OrderDutyFactor>) paramter
				.get("dutyFactor");
		SDOrder order = new SDOrder();
		String orderSn = PubFun.GetOrderSn();
		String informationSn = PubFun.GetSDInformationSn();
		String applicantSn = PubFun.GetSDAppntSn();
		String orderitemSn = PubFun.GetItemNo();
		String recognizeeSn = PubFun.GetSDInsuredSn();
		String orderitemOthSn = PubFun.GetItemOthNo();
		// String elementSn = PubFun.GetSDElementSn();
		if ("wap".equals(mFrom)) {
			orderSn = PubFun.GetWapSn("WPOrderSn");
			informationSn = PubFun.GetWapSn("WPInfoSn");
			applicantSn = PubFun.GetWapSn("WPAppntSn");
			orderitemSn = PubFun.GetWapSn("WPItemSn");
			recognizeeSn = PubFun.GetWapSn("WPInsureSn");
			orderitemOthSn = PubFun.GetWapSn("WPItemOthSn");
			// elementSn = PubFun.GetWapSn("WPElementSn");
		}
		// 订单数据
		order.setOrderSn(orderSn);
		String paySn = PubFun.GetPaySn() + "F";
		order.setPaySn(paySn);
		order = mOrderFreeService.getSDOrder(order, BaseInformation, mFrom);
		// 订单-产品信息
		SDInformation sdinformation = new SDInformation();
		sdinformation.setInformationSn(informationSn);
		sdinformation.setOrderSn(orderSn);
		// 获取订单明细表信息 sdinformation
		sdinformation = mOrderFreeService.getSDInformation(sdinformation,
				BaseInformation);// 改造后订单详细 表（产品表），add by cuishg
		Set<SDInformation> tSDInformationSet = new HashSet<SDInformation>();
		tSDInformationSet.add(sdinformation);
		order.setSdinformationSet(tSDInformationSet);
		sdinformation.setSdorder(order);
		// 获取投保人表信息 sdInformationAppnt
		sdinformationAppnt = mOrderFreeService
				.getSDInformationAppnt(sdinformationAppnt);
		sdinformationAppnt.setApplicantSn(applicantSn);
		sdinformationAppnt.setInformationSn(informationSn);
		// 保存投保人时将information的投保人信息进行更新
		Set<SDInformationAppnt> appSet = new HashSet<SDInformationAppnt>();
		appSet.add(sdinformationAppnt);
		sdinformationAppnt.setSdinformaiton(sdinformation);
		// 获取订单项1
		SDOrderItem sdorderitem = new SDOrderItem();
		sdorderitem.setOrderitemSn(orderitemSn);
		sdorderitem = mOrderFreeService.getSDOrderItem(sdorderitem,
				sdinformation, order);
		// 被保人信息
		SDInformationInsured sdInformationInsured = new SDInformationInsured();
		sdInformationInsured.setRecognizeeSn(recognizeeSn);
		sdInformationInsured.setOrderSn(order.getOrderSn());
		sdInformationInsured.setInsuredSn(order.getOrderSn() + "_1");
		sdInformationInsured.setRecognizeeKey(order.getOrderSn() + "_1");
		sdInformationInsured.setInformationSn(informationSn);
		sdInformationInsured.setSdinformation(sdinformation);
		// 获取被保人表信息 sdInformationAppnt
		List<SDInformationInsured> minsuredList = mOrderFreeService
				.getSDInformationInsured(sdInformationInsured, order,
						sdinformationAppnt);
		Set<SDInformationInsured> insuredSet = new HashSet<SDInformationInsured>();
		for(SDInformationInsured t:minsuredList){
			insuredSet.add(t);
		}
		sdinformation.setSdinformationinsuredSet(insuredSet);
		List<SDOrderItemOth> sdorderitemothList = new ArrayList<SDOrderItemOth>();
		List<SDInformationRiskType> mSDInformationRiskTypeList = new ArrayList<SDInformationRiskType>();
		List<SDInformationInsuredElements> melementslist = new ArrayList<SDInformationInsuredElements>();
		// 获取责任信息
		WapShoppingUtil tWapShoppingUtil = new WapShoppingUtil();
		List<SDInformationDuty> mdutylist = tWapShoppingUtil
				.getInformationDuty(order, sdinformation, dutyFactor);
		SDOrderItemOth tSDOrderItemOth = new SDOrderItemOth();
		tSDOrderItemOth.setOrderitemSn(orderitemOthSn);
		tSDOrderItemOth = mOrderFreeService.getSDOrderItemOth(tSDOrderItemOth,
				sdInformationInsured, order);
		sdorderitemothList.add(tSDOrderItemOth);
		SDInformationRiskType sdinformationRiskTypes = tWapShoppingUtil
				.getSDInformationRiskType(order, sdinformation,
						sdinformationAppnt, sdInformationInsured);
		mSDInformationRiskTypeList.add(sdinformationRiskTypes);
		sdInformationInsured.setSdinformaitonrisktype(sdinformationRiskTypes);
		Map<String, Object> cMap = new HashMap<String, Object>();
		cMap.put("Period", "7D");
		Set<SDInformationInsuredElements> sdinforinselementsSet = WapShoppingUtil
				.getInfomationInsuredElements(cMap, order, sdinformation,
						sdInformationInsured, riskAppFactior);
		for (SDInformationInsuredElements element : sdinforinselementsSet) {
			melementslist.add(element);
		}
		sdInformationInsured.setSdinforinselementsSet(sdinforinselementsSet);
		LinkedHashMap<Object, String> mLMap = new LinkedHashMap<Object, String>();
		// 获取交易表信息
		TradeInformation tTradeInformation = tradeInformationService
				.getTradeInformationByOrdSn(orderSn);
		if (tTradeInformation == null) {
			tTradeInformation = new TradeInformation();
		}
		tTradeInformation.setOrdID(orderSn);
		tTradeInformation.setModifyDate(new Date());
		tTradeInformation = mOrderFreeService.dealTradeInfo(order, tTradeInformation);
		// 保存订单表
		List<SDOrder> orderList = new ArrayList<SDOrder>();
		orderList.add(order);
		mLMap.put(orderList, "insert&update");
		// 保存订单详细表
		mLMap.put(sdinformation, "insert");
		// 保存投保人信息
		mLMap.put(sdinformationAppnt, "insert");
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
		// 保存交易表
		List<TradeInformation> tradeList = new ArrayList<TradeInformation>();
		tradeList.add(tTradeInformation);
		mLMap.put(tradeList, "insert&update");
		try {
			if (!mDealDataService.saveAll(mLMap)) {
				logger.error("春节赠险活动，保存订单失败！");
				addActionError("春节赠险活动，保存订单失败！！");
				return "freeerror";
			}
		} catch (Exception e) {
			logger.error("春节赠险活动，保存订单失败！" + e.getMessage(), e);
			addActionError("春节赠险活动，保存订单失败！！");
			return "freeerror";
		}

		Member tmember = new Member();
		tmember.setEmail(sdinformationAppnt.getApplicantMail());
		tmember.setMobileNO(sdinformationAppnt.getApplicantMobile());
		tmember = memberService.getUser(tmember);
		if(tmember!=null){
			if(StringUtil.isNotEmpty(tmember.getId())){
				loginFlag = "N";
			}
		}
		//处理交易、支付、承保逻辑
		//更新赠险领取人数
		new QueryBuilder("UPDATE zdconfig SET `value`=`value`+1 WHERE `type` ='FreeInsuedCount'").executeNoQuery();
		//自动注册
		AutoRegister ar = new AutoRegister();
		ar.userRegistedCheck(order, order.getOrderSn(),
				tTradeInformation);
		
		//处理承保逻辑
		Thread t1 = new OrderFreeUtil(order, tTradeInformation, sdorderService, getRequest(), getLoginMember());
		t1.start();
		if("wap".equals(mFrom)){
			return "wapresult";
		}
		return "result";
	}

	/**
	 * 验证码校验
	 * @return
	 */
	public String checkVerifyCode(){
		//验证码
		Map<String, Object> tData = new HashMap<String, Object>();
		tData.put("VerifyFlag", "Y");
		tData.put("Msg", "");
		try {
			String captchaID = getRequest().getSession().getId();
			String challengeResponse = StringUtils.upperCase(verifyCode);
			if (StringUtils.isEmpty(challengeResponse)
					|| captchaService.validateResponseForID(captchaID,
							challengeResponse) == false) {
				tData.put("VerifyFlag", "N");// 
				tData.put("Msg", "验证码输入错误！");// 错误信息
				JSONObject jsonObject = JSONObject.fromObject(tData);
				return ajax(jsonObject.toString(), "text/html");
			}
		} catch (Exception e) {
			tData.put("VerifyFlag", "N");// 
			tData.put("Msg", "验证码输入错误！");// 错误信息
			JSONObject jsonObject = JSONObject.fromObject(tData);
			return ajax(jsonObject.toString(), "text/html");
		}
		JSONObject jsonObject = JSONObject.fromObject(tData);
		return ajax(jsonObject.toString(), "text/html");
	}
	public SDInformationAppnt getSdinformationAppnt() {
		return sdinformationAppnt;
	}
	public void setSdinformationAppnt(SDInformationAppnt sdinformationAppnt) {
		this.sdinformationAppnt = sdinformationAppnt;
	}
	public String getmProductID() {
		return mProductID;
	}
	public void setmProductID(String mProductID) {
		this.mProductID = mProductID;
	}
	public String getmFrom() {
		return mFrom;
	}
	public void setmFrom(String mFrom) {
		this.mFrom = mFrom;
	}
	public String getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	public String getFreeInsuredCount() {
		return freeInsuredCount;
	}
	public void setFreeInsuredCount(String freeInsuredCount) {
		this.freeInsuredCount = freeInsuredCount;
	}
	public String getMemberid() {
		return memberid;
	}
	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}
	public String getOrderSn() {
		return orderSn;
	}
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	public String getLoginFlag() {
		return loginFlag;
	}
	public void setLoginFlag(String loginFlag) {
		this.loginFlag = loginFlag;
	}

}
