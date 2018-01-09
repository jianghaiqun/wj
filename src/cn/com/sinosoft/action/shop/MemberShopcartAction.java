package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.common.email.MessageConstant;
import cn.com.sinosoft.entity.InsuredShow;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.PayBase;
import cn.com.sinosoft.entity.SDInformation;
import cn.com.sinosoft.entity.SDInformationDuty;
import cn.com.sinosoft.entity.SDOrder;
import cn.com.sinosoft.entity.SDOrder.SDOrderStatus;
import cn.com.sinosoft.entity.SDShoppingCart;
import cn.com.sinosoft.service.PayBaseService;
import cn.com.sinosoft.service.ProductPeriodService;
import cn.com.sinosoft.service.SDInformationAppntService;
import cn.com.sinosoft.service.SDInformationInsuredService;
import cn.com.sinosoft.service.SDInformationPropertyService;
import cn.com.sinosoft.service.SDInformationService;
import cn.com.sinosoft.service.SDOrderService;
import cn.com.sinosoft.service.SDShoppingCartService;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.inter.ActivityCalculate;
import com.sinosoft.inter.MailAction;
import com.sinosoft.points.IntegralConstant;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@ParentPackage("member")
public class MemberShopcartAction  extends BaseShopAction {
	
	private static final long serialVersionUID = 3588215185518241835L;
	private List<SDShoppingCart> cartList = new ArrayList<SDShoppingCart>();
	private String orderSn ;
	private SDOrder sdorder;
	private SDInformation sdinformation;
	private List<List<InsuredShow>> showPeriods = new ArrayList<List<InsuredShow>>();// 页面显示保险期限
	private List<List<InsuredShow>> showAppnts = new ArrayList<List<InsuredShow>>();// 页面显示投保人信息
	private List<SDInformationDuty> showDuty = null;// 订单结果页责任显示集合
	private List<List<InsuredShow>> showInsureds = new ArrayList<List<InsuredShow>>();// 页面显示被保人信息
	private List<InsuredShow> showPropertys = new ArrayList<InsuredShow>();// 单被保人财产信息
	private int insuredCount = 1;// 被保人人数，主要用于多被保人时--其他，
	private String insuredToCountry = "";// 旅游目的地
	private String KID = "";
	private List<PayBase> bank1;
	private List<PayBase> bank2;
	private List<PayBase> bank3;
	private String payType;
	private double shopTotleAmount = 0.0;//购物车总价格
	private String checkOrderSn;
	private String paySn;//支付流水号
    private String currentDate;// 记录用于会员中心-我的订单 继续支付起保日期过期的判断的当前时间
    private String startDate;// 记录用于会员中心-我的订单 继续支付起保日期过期的判断的起保时间 
    private String startPerid;// 记录起保时间间隔
    private String ordIDs;// 存储多个订单号放在待支付页上
    private String ordNum;//订单数目
    private Map<String,String> map_pointinfo;//积分信息集合
    //活动、折扣等优惠信息
    private HashMap<String,Object> activityMap=new HashMap<String,Object>();
    private List<Map<String,String>> list_activity=new ArrayList<Map<String,String>>();
    //活动总的优惠价格
    private String discountAmount;
    //支付总金额
    private String realAmount;
    //总金额
    private String totalAmount;
    @Resource
	private SDShoppingCartService sdShoppingCartService;
    @Resource 
    private SDOrderService sdorderService;
    @Resource
    private SDInformationService sdinformationService;
	@Resource
	private SDInformationAppntService sdinformationAppntService;
	@Resource
	private SDInformationInsuredService sdinformationInsuredService;
	@Resource
	private SDInformationPropertyService sdinformationPropertyService;
	@Resource
	private PayBaseService payBaseService;
	@Resource
	private ProductPeriodService productPeriodService;
	private String fkIsShow; //根据CPS用户查询配置 是否显示飞客ID输入框
	private List<List<Map<String, String>>> temptorysaveList = new ArrayList<List<Map<String, String>>>();
	private String activityFlag;
	
	/**
	 * 
	 * 根据会员id取得购物车中的产品信息
	 */
	public String getShopCartINF(){
		try {
			Member logmember = getLoginMember();
			cartList = sdShoppingCartService.getShowShopCartList(logmember.getId());
			
			// 取得会员暂存的订单
			temptorysaveList = getTemptoryOrderInfo(logmember.getId());
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		if(cartList!=null && cartList.size()>0){
			List<SDOrder> paramterList = new ArrayList<SDOrder>();
			if(cartList!=null && cartList.size()>0){
				for(SDShoppingCart cart:cartList){
					String tKID = StringUtil.md5Hex(PubFun.getKeyValue() + cart.getOrderSn());
					cart.setOrderSnKid(tKID);
					SDOrder order=sdorderService.getOrderByOrderSn(cart.getOrderSn());
					paramterList.add(order);
				}
			}
			//处理优惠活动信息
			dealActivityData(paramterList,true);
			return "shopCartIndex";
		}else{
			return "noCart";
		}
	}
	
	private List<List<Map<String, String>>> getTemptoryOrderInfo(String memberId) {
		List<List<Map<String, String>>> result = new ArrayList<List<Map<String, String>>>();
		Map<String, String> recoNameMap = new HashMap<String, String>();
		DataTable dt = new QueryBuilder("select a.insuredSn, a.recognizeeName from sdinformationinsured a,sdorders o where o.memberId = ? AND o.orderStatus=4 AND o.orderSn=a.orderSn AND a.insuredSn = (SELECT MIN(insuredSn) FROM sdinformationinsured  WHERE orderSn=a.orderSn)", memberId).executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			int rowCount = dt.getRowCount();
			for (int i = 0; i < rowCount; i++) {
				recoNameMap.put(dt.getString(i, 0), dt.getString(i, 1));
			}
		}
		
		String sql = "select o.orderSn, i.productName, i.insuranceCompany, a.applicantName, min(insuredSn) insuredSn, count(1) num,'' insuredName, p.HtmlPath from sdorders o,sdinformation i,sdinformationappnt a,sdinformationinsured u, sdproduct p where o.memberId = ? and o.orderStatus=4 and o.orderSn=i.orderSn and i.informationSn=a.informationSn and o.orderSn=u.orderSn and i.productId=p.productId group by o.orderSn order by o.modifyDate desc ";
		
		dt = new QueryBuilder(sql, memberId).executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			
			int rowCount = dt.getRowCount();
			Map<String, String> map = new HashMap<String, String>();
			
			for (int i = 0; i < rowCount; i++) {
				map = new HashMap<String, String>();
				for (int j = 0; j < dt.getColCount(); j++) {
					map.put(dt.getColumnName(j), dt.getString(i, j));
				}
				if (dt.getInt(i, "num") > 1) {
					map.put("insuredName", recoNameMap.get(dt.getString(i, "insuredSn"))+"（等）");
				} else {
					map.put("insuredName", recoNameMap.get(dt.getString(i, "insuredSn")));
				}
				list.add(map);
				if ((i+1) % 5 == 0) {
					result.add(list);
					list = new ArrayList<Map<String, String>>();
				}
			}
			if (rowCount % 5 != 0) {
				result.add(list);
			}
		}
		
		return result;
	}
	
	/**
	 * 删除购物车中的订单
	 * @return
	 */
	public String deleteInfo(){
		List<SDOrder> paramterList = new ArrayList<SDOrder>();
		try {
			Member logmember = getLoginMember();
			if (StringUtil.isNotEmpty(orderSn)) {
				List<SDShoppingCart> deleteList = new ArrayList<SDShoppingCart>();
				if (orderSn.contains(",")) {
					String[] orderSns = orderSn.split(",");
					for (String ordersn : orderSns) {
						deleteList = new ArrayList<SDShoppingCart>();
						deleteList = sdShoppingCartService.getDeleteInfoByOrderSn(ordersn);
						for (SDShoppingCart deleteCart : deleteList) {
							sdShoppingCartService.delete(deleteCart);
						}
					}
					
				} else {
					deleteList = sdShoppingCartService.getDeleteInfoByOrderSn(orderSn);
					for(SDShoppingCart deleteCart : deleteList){
						sdShoppingCartService.delete(deleteCart);
					}
				}
			}
			
			// 取得会员暂存的订单
			temptorysaveList = getTemptoryOrderInfo(logmember.getId());
			
			cartList = sdShoppingCartService.getShowShopCartList(logmember.getId());
			if(cartList!=null && cartList.size()>0){
				for(SDShoppingCart cart:cartList){
					String tKID = StringUtil.md5Hex(PubFun.getKeyValue() + cart.getOrderSn());
					cart.setOrderSnKid(tKID);
					SDOrder order=sdorderService.getOrderByOrderSn(cart.getOrderSn());
					paramterList.add(order);
				}
				//处理优惠活动信息
				dealActivityData(paramterList,true);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		if(cartList!=null && cartList.size()>0){
			return "shopCartIndex";
		}else{
			return "noCart";
		}
	}
	public String toCheckInsure(){
		return "checkInsure";
	}
	/**
	 * 支付前核保
	 */
	public String checkInsure() {
		Member logmember = getLoginMember();
		String jsonpname = getRequest().getParameter("callback");
		Map<String, String> checkMassage = null;
		String returnMassage = "";
		String passFlag = "pass";
		String jsonstrs = "";
		try {
			KID = StringUtil.md5Hex(PubFun.getKeyValue() + logmember.getId());
			if(StringUtil.isNotEmpty(checkOrderSn)){
				String[] orderSns = checkOrderSn.split(",");
				for(String osn : orderSns){
					checkMassage = sdShoppingCartService.checkOrderByOrderSn(osn.trim(),KID);
					if("0".equals(checkMassage.get("passFlag"))||StringUtil.isEmpty(checkMassage.get("passFlag"))){
						passFlag = "nopass";
						returnMassage = returnMassage + checkMassage.get("productName") + ":" + checkMassage.get("rtnMessage");
					}
					SDOrder tSDOrder = this.sdorderService.getOrderByOrderSn(osn.trim());
					String tSDOrderStatus = String.valueOf(tSDOrder.getOrderStatus().ordinal());
					if(!"5".equals(tSDOrderStatus)){
						logger.warn("该订单不是待支付状态，不允许重新支付，orderSn:{}", tSDOrder.getOrderSn());
						passFlag = "nopass";
						returnMassage = returnMassage + "订单（ "+tSDOrder.getOrderSn()+" ）不是待支付状态，不允许在购物车中进行结算;";
					}
				}
			}
			if("pass".equals(passFlag)){
				String passFlags = "{passFlag:'pass',rtnMessage:'noRtnMssage','KID':'"+KID+"'}";
				JSONObject jsonArrays = JSONObject.fromObject(passFlags);
				jsonstrs = jsonArrays.toString();
				
				
			}else{
				Map<String,String> map = new HashMap<String,String>();
				map.put("rtnMessage", returnMassage);
				map.put("passFlag", "nopass");
				map.put("KID", KID); 
				JSONObject jsonArrays = JSONObject.fromObject(map);
				jsonstrs = jsonArrays.toString();
			} 
			return ajaxHtml(jsonpname + "(" + jsonstrs + ");"); 
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		String passFlags = "{passFlag:'nopass',rtnMessage:'noRtnMessage'}";
		JSONObject jsonArrays = JSONObject.fromObject(passFlags);
		jsonstrs = jsonArrays.toString();
		return ajaxHtml(jsonpname + "(" + jsonstrs + ");"); 

	}
	/**
	 * 支付页
	 * @return
	 */
	@SuppressWarnings({ "static-access", "rawtypes" })
	public String shopCartPay(){
		
		String oldPaySn = "";
		
		//订单list，优惠活动处理公共方法的入参,sdorder对象的集合
		List<SDOrder> paramterList = new ArrayList<SDOrder>();
		try {
			Member memberLogin = getLoginMember();
			Member member = new Member();
			member.setEmail(memberLogin.getEmail());
			BigDecimal totlePrice = new BigDecimal("0.0");
			if(StringUtil.isNotEmpty(checkOrderSn)){
//				paySn = PubFun.GetPaySn()+ "G";
//				paySn = PubFun.GetPaySn(null , "G");
				String[] orderSns = checkOrderSn.split(",");
				Set<String> paySnSet = new HashSet<String>();
				ordIDs = "";
				ordNum=String.valueOf(orderSns.length);
				for(String osn : orderSns){
					SDOrder sdorder = sdorderService.getOrderByOrderSn(osn.trim());
					oldPaySn = sdorder.getPaySn();
					paramterList.add(sdorder);
					paySnSet.add(sdorder.getPaySn());
					
					if(String.valueOf(sdorder.getOrderStatus().ordinal()).equals("7")){
						addActionError("订单已经支付！");
						return ERROR;
					}
					
					SDInformation sdinformation = sdorder.getSdinformationSet().iterator().next();
					if(sdinformation != null){
						if(!checkPeriodEffectiveness(sdinformation)){
							orderSn = sdorder.getOrderSn();
							KID = StringUtil.md5Hex(PubFun.getKeyValue() + orderSn);
							return "stimeout";
						}
					}
					SDShoppingCart shopCart = sdShoppingCartService.getShopCartByOrderSn(osn.trim());
					SDInformation information = sdorder.getSdinformationSet().iterator().next();
					sdorder.setOrderStatus(SDOrderStatus.prepay);
//					sdorder.setPaySn(paySn);
					sdorderService.update(sdorder);
					if(shopCart!=null){
						cartList.add(shopCart);
						ordIDs += shopCart.getOrderSn() + ",";
						totlePrice = totlePrice.add(shopCart.getTotleAmount());
					}
					
					sdinformation=sdorder.getSdinformationSet().iterator().next();

			    	MailAction.deleteOldNoPaymentMail(oldPaySn);
				}
				
				// 根据订单状态判断支付号是否更新 如果支付号为1个则不更新，如果是多个则统一更新
			    if (paySnSet == null || paySnSet.size() == 0 || paySnSet.size() > 1) { 
					String updatePaySn = "update sdorders set paySn = ?  where ordersn in('" + checkOrderSn.replaceAll(" ", "").replaceAll(",", "','") + "' )";
					QueryBuilder qb = new QueryBuilder(updatePaySn);
					String paySn_new = PubFun.GetPaySn(totlePrice + "", "G") ;
					qb.add(paySn_new);
					qb.executeNoQuery();
					paySn = paySn_new;
					
				 } else if(paySnSet.size() == 1){
					Transaction tran = new Transaction();
					
					String updatePaySn = "update sdorders set paySn = ?  where ordersn in('" + checkOrderSn.replaceAll(" ", "").replaceAll(",", "','") + "' )";
					QueryBuilder qb = new QueryBuilder(updatePaySn);
					
					String paysn_old = paySnSet.iterator().next();
					String paySn_new = null;
					if (StringUtil.isEmpty(paysn_old) || paysn_old.endsWith("00") || paysn_old.endsWith("01")) {
						paySn_new = PubFun.GetPaySn(totlePrice + "", "G");
						
					} else {
						tran.add(new QueryBuilder("update sdorders set paySn = '' where paySn = ?" ,paysn_old));
						paySn_new = PubFun.replacePaySn(totlePrice + "", paysn_old);
					}
					qb.add(paySn_new);
					tran.add(qb);
					tran.commit();
					paySn = paySn_new;
				 }
			}
			//发送待支付邮件
			if (StringUtil.isEmpty(payType)) {
				Map<String,Object> data = new HashMap<String,Object>();
				data.put("paySn", paySn);
				data.put(MessageConstant.PARAM_RECEIVER_NAME, member.getEmail());
				ActionUtil.sendMessage("wj00015", data);
				ActionUtil.dealAction("wj00015", data, getRequest());
			}
			if (StringUtil.isNotEmpty(ordIDs)) {
				ordIDs = ordIDs.substring(0, ordIDs.length() - 1);
			}
			bank1 = payBaseService.getPayBaseList("1");
			bank2 = payBaseService.getPayBaseList("2");
			bank3 = payBaseService.getPayBaseList("3");
			shopTotleAmount = totlePrice.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		} catch (Exception e) {
			logger.error("待支付出现异常情况,错误见下面：" + e.getMessage() , e);
			addActionError("待支付出现异常情况，请联系管理员!");
			return ERROR;
		}
		//处理满减、折扣优惠活动信息
		dealActivityData(paramterList,true);
		//处理满送、买送、高倍积分活动
		list_activity=ActivityCalculate.buySendActivityInfo(paramterList,paramterList.get(0).getChannelsn());
		
		//是否显示飞客ID
		fkIsShow = queryCpsOrder(paySn);
		String memberID = (String) getSession(Member.LOGIN_MEMBER_ID_SESSION_NAME);
		Map map_point=ActivityCalculate.payPointInfo(paramterList, paramterList.get(0).getChannelsn(),0,"", memberID);
		//购买成功后获得积分
		String givepoint=String.valueOf(map_point.get("givepoint"));
		Member loginMember = getLoginMember();
		map_pointinfo=new HashMap<String,String>();
		map_pointinfo.put("pointDesFlag", "false");
		String baseIntegral = new BigDecimal(realAmount).multiply(new BigDecimal(Config.getValue("PointScalerUnit"))).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
		if (loginMember != null ) {
			List<Map<String,Object>> productToPointRates = JSONArray.fromObject(map_point.get("productToPointRates"));
			//会员等级
			if (StringUtil.isNotEmpty(givepoint) && Integer.parseInt(givepoint) > 0) {
				Map<String,String>  map_result_grade=ActivityCalculate.getMemberGradeBirthdayPoints(IntegralConstant.POINT_SOURCE_BUY,loginMember.getId(), baseIntegral,productToPointRates);
				String flag_grade=map_result_grade.get("flag");
				if("true".equals(flag_grade)){
					String MemberGrade=map_result_grade.get("MemberGrade");
					//无取舍操作的积分值
					//String pointsAll=map_result_grade.get("pointsAll");
					givepoint=String.valueOf(Integer.parseInt(givepoint) + Integer.parseInt(map_result_grade.get("addpoints")));
					
					map_pointinfo.put("pointDesFlag", "true");
					map_pointinfo.put("pointDesStart", "您是"+MemberGrade+"会员,保单生效后可获得");
					map_pointinfo.put("pointDesEnd",givepoint);
					
				}
				//会员生日月
				Map<String,String>  map_result=ActivityCalculate.getMemberGradeBirthdayPoints(IntegralConstant.POINT_SOURCE_BIRTH_MONTH,loginMember.getId(), baseIntegral,productToPointRates);
				String flag=map_result.get("flag");
				if("true".equals(flag)){
					String MemberGrade=map_result.get("MemberGrade");
					givepoint=String.valueOf(Integer.parseInt(givepoint) + Integer.parseInt(map_result.get("addpoints")));
					map_pointinfo.put("pointDesFlag", "true");
					map_pointinfo.put("pointDesStart", MemberGrade+"会员生日月享特权,保单生效后可获得");
					map_pointinfo.put("pointDesEnd",givepoint);
				}
			}
		}
		map_pointinfo.put("givepoints",givepoint);
		if (StringUtil.isNotEmpty(givepoint)) {
			map_pointinfo.put("pointValue",new BigDecimal(givepoint).divide(new BigDecimal(Config.getValue("PointScalerUnit")), 1, BigDecimal.ROUND_HALF_UP).toString());
		}
		map_pointinfo.put("points",givepoint);
		return "shopCartPay";
	}
	
	/**
	 * @Title: queryCpsOrder.
	 * @param OrderSn.
	 * @author congzn.
	 */
	public String queryCpsOrder(String paySn){
		String result = "N";
		try {
			if(StringUtil.isNotEmpty(paySn)){
				String queryCpsUserCodeSql = "SELECT CodeValue FROM zdcode WHERE CodeType = 'CPS.FKID' AND CodeValue IN(SELECT DISTINCT cid FROM cps c WHERE c.on IN(SELECT ordersn FROM sdorders WHERE paySn = ?))";
				QueryBuilder queryCpsUserCode = new QueryBuilder(queryCpsUserCodeSql);
				queryCpsUserCode.add(paySn);
				String cpsUserCode = queryCpsUserCode.executeString();
				if (StringUtil.isNotEmpty(cpsUserCode)) {
					result = "Y";
				}
			}
		} catch (Exception e) {
			logger.error("MemberShopcartAction.queryCpsOrder 异常!飞客ID输入框无法正常显示.");
		}
		return result;
	}
	
	public boolean checkPeriodEffectiveness(SDInformation sdinformation) {
		// 起保时间
		Date checkStartDay = sdinformation.getStartDate();
		setStartDate(new SimpleDateFormat("yyyy年MM月dd日 HH时mm分").format(checkStartDay));
		// 当前时间
		Date now = new Date();
		currentDate = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分").format(now);

		int startPeriod = 1;
		String productId = sdinformation.getProductId();
		String comCode = sdinformation.getInsuranceCompany();
		String stp = productPeriodService.getStartPeriod(comCode, productId);
		if(StringUtil.isNotEmpty(stp)){
			startPeriod = Integer.parseInt(stp);
		}
		// 默认产品无次日生效，查询zdcode表中被要求次日生效的产品记录
		QueryBuilder qb = new QueryBuilder(
						"SELECT memo FROM zdcode WHERE codetype='ProductPeriod' AND codevalue=?");
		qb.add(productId);
		DataTable dt = qb.executeDataTable();
		if (dt.getRowCount() > 0 && dt.get(0, 0) != null) {
				startPeriod = Integer.parseInt(dt.get(0, 0).toString());
		}
		
		// 当日生效的情况
		if (startPeriod == 0) {
			if (new SimpleDateFormat("yyyyMMdd").format(now).equals(new SimpleDateFormat("yyyyMMdd").format(checkStartDay))) {
				return true;
			} else if (now.after(checkStartDay)) {
				return false;
			}
			
		} else {
			// 当前时间超出起保时间 
			if (now.after(checkStartDay)) {
				return false;
			}
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String newDate = sdf.format(com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(new Date(), startPeriod, "D",null));
		if(StringUtil.isNotEmpty(newDate)){
			newDate = newDate + " 00:00:00";
		}
		try {
			Date startDate = sdf.parse(newDate);
			if(checkStartDay.getTime()-startDate.getTime()>=0){
				return true;
			}
			setStartPerid("<P>不满足该产品起保时间间隔 " + startPeriod + " 天</P>");
			
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		return false;
	}
	
	/**
	 * 订单预览
	 * 
	 * @param cOrderSn
	 *            订单号
	 */
	/*public String showInsuredInfomation() {
		// 根据OrderSn获取SDOrder实体类
		sdorder = sdorderService.getOrderByOrderSn(orderSn);
		// 订单详细表
		Set<SDInformation> tSDInformationSet = sdorder.getSdinformationSet();
		for (SDInformation tSDInformation : tSDInformationSet) {
			this.sdinformation = tSDInformation;
		}
		// 保险期限信息
		showPeriods = this.sdinformationService.createShowInformationPeriod(sdinformation);
		// 投保人信息
		Set<SDInformationAppnt> tSDInformationAppnt = sdinformation.getSdinformationappntSet();
		if (tSDInformationAppnt != null && tSDInformationAppnt.size() >= 1) {
			showAppnts = this.sdinformationAppntService.createShowInformationAppnt(tSDInformationAppnt, sdinformation.getInsuranceCompany());
		} 
		// 责任投保要素信息
		Set<SDInformationDuty> tSDInformationDutySet = sdinformation.getSdinformationDutySet();
		if (tSDInformationDutySet != null && tSDInformationDutySet.size() > 0) {
			showDuty = new ArrayList<SDInformationDuty>(tSDInformationDutySet);
		}
		// 被保人信息
		Set<SDInformationInsured> tSDInformationInsured = sdinformation.getSdinformationinsuredSet();
		if (tSDInformationInsured != null && tSDInformationInsured.size() > 0) {
			// 重新整理被保人信息
			showInsureds = sdinformationInsuredService.createShowInformationInsured(tSDInformationInsured, sdinformation.getInsuranceCompany());
			if(tSDInformationInsured.size() == 1){
				for(SDInformationInsured insured : tSDInformationInsured){
					SDInformationProperty sdp = insured.getSdinformationproperty();
					showPropertys = sdinformationPropertyService.getcreateShowOneProperty(sdp,sdinformation.getInsuranceCompany(),sdinformation.getProductId());
				}
			}
		}
		// 旅游目的地
		for (SDInformationInsured t : tSDInformationInsured) {
			if((t.getDestinationCountry()!=null&&!"".equals(t.getDestinationCountry())) && (t.getDestinationCountryText()!=null&&!"".equals(t.getDestinationCountryText()))){
				this.insuredToCountry = sdinformationInsuredService.getInsuredToCountry(t);
			}
			break;
		}
		//由于excle导入方式与正常录入方式的订单预览显示规则相同，故被保人数量用同一个字段表示
		//但是返回修改时，用的是不同的
		this.insuredCount = sdinformation.getSdinformationinsuredSet().size();
		return "result";
	}*/
	/**
	 * 
	* @Title: dealActivityData 
	* @Description: TODO(封装优惠活动信息) 
	* @return void    返回类型 
	* @author XXX
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void dealActivityData(List<SDOrder> paramterList,boolean  groupFlag){
		// 购物车所有产品折扣活动标识 0：所有产品无折扣价 1：有产品有折扣价
		activityFlag = "0";
		//筛选优惠活动
		Map<String, Map<String, Object>> activity_product_info1 = ActivityCalculate.ProductShoppingCartInfo(paramterList,paramterList.get(0).getChannelsn(),groupFlag);
		//总优惠金额
		BigDecimal discount=new BigDecimal("0");
		//支付总金额
		BigDecimal realamount=new BigDecimal("0");
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
		        	 //总优惠金额累计
		        	if("3".equals(type)){
		        		 String discountamount=map_activityamont.get("DiscountAmount");
					     discount=discount.add(new BigDecimal(discountamount));
		        	}
			        //支付总金额
			        String RealAmount=map_activityamont.get("RealAmount");
			        realamount=realamount.add(new BigDecimal(RealAmount));
			        //总金额
			        if("6".equals(type)){
			        	 String TotalAmount=map_activityamont.get("RealAmount");
					     totalamount=totalamount.add(new BigDecimal(TotalAmount));
			        }else{
			        	 String TotalAmount=map_activityamont.get("TotalAmount");
					     totalamount=totalamount.add(new BigDecimal(TotalAmount));
			        }
		        }
		        //String type=map_activityinfo.get("type");
		        for (int j = 0; j < list_product.size(); j++) {
		        	//活动每个产品对应的Map
		        	Map<String,String> map_product=list_product.get(j);
		        	if(sdshoppingcart.getOrderSn().equals(map_product.get("OrderSn"))){
		        		map_product.put("ProductName",sdshoppingcart.getProductName());
		        		map_product.put("StartDate",sdshoppingcart.getStartDate());
		        		map_product.put("EndDate",sdshoppingcart.getEndDate());
		        		map_product.put("AppntName",sdshoppingcart.getAppntName());
		        		map_product.put("AppntEmail",sdshoppingcart.getAppntEmail());
		        		map_product.put("FirstInsuredName",sdshoppingcart.getFirstInsuredName());
		        		map_product.put("RecognizeeMul",sdshoppingcart.getRecognizeeMul());
		        		map_product.put("AllInsuredName",sdshoppingcart.getAllInsuredName());
		        		map_product.put("IsEffective",sdshoppingcart.getIsEffective());
		        		map_product.put("ProductLog",sdshoppingcart.getProductLog());
		        		map_product.put("DetailPath",sdshoppingcart.getDetailPath());
		        		map_product.put("OrderSnKid",sdshoppingcart.getOrderSnKid());
		        		map_product.put("ProductTotalPrice", sdshoppingcart.getProductTotalPrice());
		        		map_product.put("ProductId", sdshoppingcart.getProductId());
		        		map_product.put("ComanyCode", sdshoppingcart.getProductId().substring(0, 4));
		        		map_product.put("riskType", sdshoppingcart.getRiskType());
		        		if (StringUtil.isNotEmpty(sdshoppingcart.getDiscountRates()) || "6".equals(type)) {
		        			map_product.put("ActivityFlag","1");
		        			activityFlag = "1";
		        		} else {
		        			map_product.put("ActivityFlag","0");
		        		}
					}
				}
		    }  
		}
		discountAmount=String.valueOf(discount.setScale(2, BigDecimal.ROUND_HALF_UP));
		realAmount=String.valueOf(realamount.setScale(2, BigDecimal.ROUND_HALF_UP));
		totalAmount=String.valueOf(totalamount.setScale(2, BigDecimal.ROUND_HALF_UP));
		activityMap.put("activityMap",activity_product_info1);
	}
	public List<SDShoppingCart> getCartList() {
		return cartList;
	}
	public void setCartList(List<SDShoppingCart> cartList) {
		this.cartList = cartList;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	public SDOrder getSdorder() {
		return sdorder;
	}
	public void setSdorder(SDOrder sdorder) {
		this.sdorder = sdorder;
	}
	public SDInformation getSdinformation() {
		return sdinformation;
	}
	public void setSdinformation(SDInformation sdinformation) {
		this.sdinformation = sdinformation;
	}
	public List<List<InsuredShow>> getShowPeriods() {
		return showPeriods;
	}
	public void setShowPeriods(List<List<InsuredShow>> showPeriods) {
		this.showPeriods = showPeriods;
	}
	public List<List<InsuredShow>> getShowAppnts() {
		return showAppnts;
	}
	public void setShowAppnts(List<List<InsuredShow>> showAppnts) {
		this.showAppnts = showAppnts;
	}
	public List<SDInformationDuty> getShowDuty() {
		return showDuty;
	}
	public void setShowDuty(List<SDInformationDuty> showDuty) {
		this.showDuty = showDuty;
	}
	public List<List<InsuredShow>> getShowInsureds() {
		return showInsureds;
	}
	public void setShowInsureds(List<List<InsuredShow>> showInsureds) {
		this.showInsureds = showInsureds;
	}
	public List<InsuredShow> getShowPropertys() {
		return showPropertys;
	}
	public void setShowPropertys(List<InsuredShow> showPropertys) {
		this.showPropertys = showPropertys;
	}
	public int getInsuredCount() {
		return insuredCount;
	}
	public void setInsuredCount(int insuredCount) {
		this.insuredCount = insuredCount;
	}
	public String getInsuredToCountry() {
		return insuredToCountry;
	}
	public void setInsuredToCountry(String insuredToCountry) {
		this.insuredToCountry = insuredToCountry;
	}
	public String getKID() {
		return KID;
	}
	public void setKID(String kID) {
		KID = kID;
	}
	public List<PayBase> getBank1() {
		return bank1;
	}
	public void setBank1(List<PayBase> bank1) {
		this.bank1 = bank1;
	}
	public List<PayBase> getBank2() {
		return bank2;
	}
	public void setBank2(List<PayBase> bank2) {
		this.bank2 = bank2;
	}
	public List<PayBase> getBank3() {
		return bank3;
	}
	public void setBank3(List<PayBase> bank3) {
		this.bank3 = bank3;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public double getShopTotleAmount() {
		return shopTotleAmount;
	}
	public void setShopTotleAmount(double shopTotleAmount) {
		this.shopTotleAmount = shopTotleAmount;
	}
	public String getCheckOrderSn() {
		return checkOrderSn;
	}
	public void setCheckOrderSn(String checkOrderSn) {
		this.checkOrderSn = checkOrderSn;
	}
	public String getPaySn() {
		return paySn;
	}
	public void setPaySn(String paySn) {
		this.paySn = paySn;
	}
	public String getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStartPerid() {
		return startPerid;
	}

	public void setStartPerid(String startPerid) {
		this.startPerid = startPerid;
	}
	
	public String getOrdIDs() {
		return ordIDs;
	}
	
	public void setOrdIDs(String ordIDs) {
		this.ordIDs = ordIDs;
	}
	public String getOrdNum() {
		return ordNum;
	}
	public void setOrdNum(String ordNum) {
		this.ordNum = ordNum;
	}
	public HashMap<String, Object> getActivityMap() {
		return activityMap;
	}
	public void setActivityMap(HashMap<String, Object> activityMap) {
		this.activityMap = activityMap;
	}
	public String getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(String discountAmount) {
		this.discountAmount = discountAmount;
	}
	public String getRealAmount() {
		return realAmount;
	}
	public void setRealAmount(String realAmount) {
		this.realAmount = realAmount;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public List<Map<String, String>> getList_activity() {
		return list_activity;
	}
	public void setList_activity(List<Map<String, String>> list_activity) {
		this.list_activity = list_activity;
	}
	public String getFkIsShow() {
		return fkIsShow;
	}
	public void setFkIsShow(String fkIsShow) {
		this.fkIsShow = fkIsShow;
	}
	public Map<String, String> getMap_pointinfo() {
		return map_pointinfo;
	}
	public void setMap_pointinfo(Map<String, String> map_pointinfo) {
		this.map_pointinfo = map_pointinfo;
	}
	public List<List<Map<String, String>>> getTemptorysaveList() {
		return temptorysaveList;
	}
	public void setTemptorysaveList(List<List<Map<String, String>>> temptorysaveList) {
		this.temptorysaveList = temptorysaveList;
	}

	public String getActivityFlag() {
		return activityFlag;
	}

	public void setActivityFlag(String activityFlag) {
		this.activityFlag = activityFlag;
	}
	
}
