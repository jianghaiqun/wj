package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.common.email.MessageConstant;
import cn.com.sinosoft.entity.GiftClassify;
import cn.com.sinosoft.entity.GoodsStock;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.PointExchangeInfo;
import cn.com.sinosoft.entity.Present;
import cn.com.sinosoft.service.BindInfoForLoginService;
import cn.com.sinosoft.service.ExchangeGoodsService;
import cn.com.sinosoft.service.GiftClassifyService;
import cn.com.sinosoft.service.GiftService;
import cn.com.sinosoft.service.GoodsStockService;
import cn.com.sinosoft.service.MemberService;
import cn.com.sinosoft.service.PointExchangeInfoService;
import cn.com.sinosoft.service.PointsService;
import cn.com.sinosoft.service.PresentService;
import cn.com.sinosoft.service.StockService;
import cn.com.sinosoft.util.ArithUtil;
import cn.com.sinosoft.util.PointExchangeInterface;
import com.octo.captcha.service.CaptchaService;
import com.octo.captcha.service.CaptchaServiceException;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.NumberUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.points.IntegralConstant;
import com.sinosoft.points.PointsCalculate;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @ClassName: PointsAction
 * @Description: TODO(积分兑换处理逻辑)
 * @author XXX
 * @date 2014年10月24日 下午2:40:47
 *       <p>
 *       修改历史
 *       </p>
 *       <p>
 *       序号 日期 修改人 修改原因
 *       </p>
 */
@ParentPackage("shop")
public class PointsAction extends BaseShopAction {
	private static final long serialVersionUID = -5943498229417204496L;
	private String presentID;// 活动ID
	private String articleUrl;
	private String articleLogo;
	private String TotalPoints;
	private String SparePoint;
	private String carNo;
	private String errormsg;
	private String orderSn;//
	private String siteName;
	// 登录信息
	private Map<String, String> loginmap;
	// 推荐产品集合
	private List<Present> list;

	// 热销礼品
	private List<GiftClassify> hotgiftlist;

	// 推荐礼品
	private List<GiftClassify> recgifglist;

	// 最新上架
	private List<GiftClassify> newGiftlist;
	//礼品信息
	private List<GiftClassify> list_data;

	// 礼品实体类
	private GiftClassify mgiftclassfy;

	// 库存实体类
	private GoodsStock mgoodsstock;// 订单结果页使用

	// 兑换结果实体类
	private PointExchangeInfo mpointxxchangeInfo;// 订单结果页使用

	// 积分处理类标记
	private String dealpointflag = "mobile";
	
	private List<Map<String,Object>> modelList;
	@Resource
	private PointsService pointsService;
	@Resource
	private MemberService memberService;
	@Resource
	private GiftService giftService;
	@Resource
	private StockService stockService;
	@Resource
	private PresentService presentService;
	@Resource
	private GiftClassifyService mGiftClassifyService;
	@Resource
	private GoodsStockService mGoodsStockService;
	@Resource
	private PointExchangeInfoService mPointExchangeInfoService;
	@Resource
	private BindInfoForLoginService bindInfoForLoginService;
	@Resource
	private CaptchaService captchaService;
	@Resource
	private ExchangeGoodsService exchangeGoodsService;
	/**
	 * 
	 * @Title: integralMall
	 * @Description: TODO(积分商城首页)
	 * @return String 返回类型
	 * @author
	 */
	@InputConfig(resultName = "error")
	public String integralMall() {
		//推荐产品
		//Map<String,String> map=new HashMap<String,String>();
//		map.put("startindex","0");
//		map.put("size","4");
//		map.put("recommend","Y");
		// 热门兑换
		hotgiftlist = mGiftClassifyService.hotExchange();
		for (int j = 0; j < hotgiftlist.size(); j++) {
			GiftClassify giftclassify=hotgiftlist.get(j);
			if (!"1".equals(giftclassify.getType())) {
				giftclassify.setLinkUrl(Config.getValue("ServerContext") + "/shop/points!integralMallInformation.action?presentID=" + giftclassify.getId());
			}
		}
		//最新上架
		Map<String,String> map_param=new HashMap<String,String>();
		map_param.put("startindex","0");
		map_param.put("size","7");
		newGiftlist=mGiftClassifyService.getGiftClassifyList(map_param);
		for (int j = 0; j < newGiftlist.size(); j++) {
			GiftClassify giftclassify=newGiftlist.get(j);
			if (!"1".equals(giftclassify.getType())) {
				giftclassify.setLinkUrl(Config.getValue("ServerContext") + "/shop/points!integralMallInformation.action?presentID=" + giftclassify.getId());
			}
		}
		//归属模块
		modelList=new ArrayList<Map<String,Object>>();
		DataTable dt_PointsModel=new QueryBuilder("SELECT codename,memo,codevalue  FROM  zdcode WHERE codetype = 'PointsMall.Model' AND codevalue!='System' ORDER BY codeorder ").executeDataTable();
		if(dt_PointsModel.getRowCount()>0){
			for (int i = 0; i < dt_PointsModel.getRowCount(); i++) {
				Map<String,Object> map_model=new HashMap<String,Object>();
				map_model.put("ModelName", dt_PointsModel.getString(i,0));
				map_model.put("Describtion", dt_PointsModel.getString(i,1));
				Map<String,String> map_pram=new HashMap<String,String>();
				map_pram.put("startindex","0");
				map_pram.put("size","5");
				String modeltype=dt_PointsModel.getString(i,2);
				map_model.put("modeltype",modeltype);
				map_pram.put("modelType",modeltype);
				//积分段落
				DataTable dt_PointPart=new QueryBuilder("SELECT codename,codevalue FROM  zdcode WHERE codetype='PointPart' AND codevalue!='System' and memo=? ORDER BY codeorder ",modeltype).executeDataTable();
				if(dt_PointPart.getRowCount()>0){
					List<Map<String,String>> pointPartList=new ArrayList<Map<String,String>>();
					for (int j = 0; j < dt_PointPart.getRowCount(); j++) {
						Map<String,String> map_code=new HashMap<String,String>();
						map_code.put("codename",dt_PointPart.getString(j,0));
						map_code.put("codevalue",dt_PointPart.getString(j,1));
						pointPartList.add(map_code);
					}
					map_model.put("modelList", pointPartList);
				}
				modelList.add(map_model);
			}
		}
		siteName = new QueryBuilder("SELECT Meta_Keywords FROM zcsite").executeString();
		getLoginInfo();
		
		return "integral_index";
	}
	
	private void getLoginInfo() {
		Member member = getLoginMember();
		loginmap = new HashMap<String, String>();
		if (member == null || StringUtil.isEmpty(member.getId())) {
			return;
		}
		
		loginmap.put("realName", member.getRealName());
		
		String currentvalidatepoint = String.valueOf(member.getCurrentValidatePoint());
		loginmap.put("currentvalidatepoint", currentvalidatepoint);
		String PointScalerUnit = Config.getConfigValue("PointScalerUnit");
		loginmap.put("pointprice", changeIntegral(Integer.parseInt(currentvalidatepoint), Integer.parseInt(PointScalerUnit)));
		String sql = "SELECT COUNT(1) FROM sdorders a,sdinformation b, sdsearchrelaproduct c WHERE a.memberid = ? AND a.orderStatus IN ('7','10') and a.orderSn = b.orderSn and b.productid = c.productid AND a.commentId IS NULL";
		DataTable dt_commentNum = new QueryBuilder(sql, member.getId()).executeDataTable();
		if (dt_commentNum.getRowCount() > 0) {
			String commentNum = dt_commentNum.getString(0, 0);
			loginmap.put("commentnum", commentNum);
			Map param=new HashMap();
			param.put("PointsGive","01");
			try {
				Map<String, Object> map = new PointsCalculate().pointsManage(IntegralConstant.POINT_SEARCH,"1", param);
				List<Map<String, Object>> list=(List<Map<String, Object>>) map.get(IntegralConstant.DATA);
				if(list.size()>0){
					Map map_data=list.get(0);
					loginmap.put("commentpoint", String.valueOf(new BigDecimal(String.valueOf(map_data.get("PointsNum"))).multiply(new BigDecimal(commentNum)).setScale(0, BigDecimal.ROUND_HALF_UP)));
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		} else {
			loginmap.put("commentnum", "0");
			loginmap.put("commentpoint", "0");
		}
		String point = String.valueOf(member.getPoint());
		if (StringUtil.isEmpty(point)) {
			loginmap.put("point", "0");
		} else {
			loginmap.put("point", point);
		}
		String headpicpath = member.getHeadPicPath();
		if (StringUtil.isEmpty(headpicpath)) {
			loginmap.put("headpicpath", "/images/redesign/photo_06.gif");
		} else {
			loginmap.put("headpicpath", headpicpath);
		}
		
		MemberCenterAction mem = new MemberCenterAction();
		Map<String, String> gradeMap = mem.getGradeIcon(member.getVipFlag(),
				member.getGrade(), "", member.getBirthday(), member.getBirthYear());
		if (StringUtil.isNotEmpty(gradeMap.get("gradeClass"))) {
			loginmap.put("gradeClass", gradeMap.get("gradeClass").replace("vip_k ", "vip_k vip_top "));
		} else {
			loginmap.put("gradeClass", "");
		}
	}
	
	/**
	 * 
	 * @Title: integralMallModelInfo
	 * @Description: TODO(积分商城首页模块信息)
	 * @return String 返回类型
	 * @author
	 */
	@InputConfig(resultName = "error")
	public String integralMallModelInfo() {
		String modeltype=getRequest().getParameter("modeltype");
		String idalias=getRequest().getParameter("idalias");
		String page_Index=getRequest().getParameter("page_Index");
		modeltype=modeltype.substring(modeltype.lastIndexOf("_")+1,modeltype.length());
		//模块数据
		Map<String,String> map_pram=new HashMap<String,String>();
		Map<String,String>  map_page=new HashMap<String,String>();
		if(StringUtil.isEmpty(page_Index)||"1".equals(page_Index)){
			map_pram.put("startindex","0");
			map_pram.put("size","5");
		}else{
			int index=(Integer.parseInt(page_Index)-2)*6+5 ;
			map_pram.put("startindex",String.valueOf(index));
			map_pram.put("size","6");
		}
		map_pram.put("modelType",modeltype);
		//积分段落
		String pointvalue=getRequest().getParameter("pointvalue");
		if(StringUtil.isNotEmpty(pointvalue)){
			pointvalue=pointvalue.substring(pointvalue.indexOf("@")+1,pointvalue.length());
			if(pointvalue.indexOf("-")!=-1){
				String points[]=pointvalue.split("-");
				map_pram.put("startpoints",points[0]);
				map_pram.put("endpoints",points[1]);
				map_page.put("startpoints",points[0]);
				map_page.put("endpoints",points[1]);
				
			}else{
				map_pram.put("startpoints",pointvalue);
				map_page.put("startpoints",pointvalue);
			}
		}
		//模块数据
		list_data = mGiftClassifyService.getGiftClassifyList(map_pram);
		for (int j = 0; j < list_data.size(); j++) {
			GiftClassify giftclassify=list_data.get(j);
			if (!"1".equals(giftclassify.getType())) {
				giftclassify.setLinkUrl(Config.getValue("ServerContext") + "/shop/points!integralMallInformation.action?presentID=" + giftclassify.getId());
			}
			//礼品名称显示截取
			String giftTitle=giftclassify.getGiftTitle();
			if(giftTitle.length()>22){
				giftclassify.setGiftTitle(giftTitle.substring(0, 22)+"...");
			}
		}
		//分页
		map_page.put("modelType",modeltype);
		//总条数
		int totalCounts=mGiftClassifyService.getGiftClassifyListNum(map_page);
		map_page.put("totalCounts",String.valueOf(totalCounts));
		if(StringUtil.isEmpty(page_Index)||"1".equals(page_Index)){
			map_page.put("pagesize","5");
		}else{
			map_page.put("pagesize","6");
		}
		map_page.put("idalias",idalias);
		//分页底部数据
		getPageDataList(map_page);
		return "model";
	}
	/**
	 * 
	 * @Title: integralMall
	 * @Description: TODO(积分商城礼品详细页)
	 * @return String 返回类型
	 * @author modify by cuishigang
	 */
	@InputConfig(resultName = "error")
	public String integralMallInformation() {

		try {
			// 根据礼品id产讯礼品
			mgiftclassfy = mGiftClassifyService.getGiftClassify(presentID);

			if (mgiftclassfy == null) {
				addActionError("亲，您的投保遇到了点小问题，请咨询客服后进行购买哦！");
				return ERROR;
			}

		} catch (Exception e) {
			logger.error("类 PointsAction。integralMallInformation 积分初始化异常，" + e.getMessage(), e);
		}
		
		siteName = new QueryBuilder("SELECT Meta_Keywords FROM zcsite").executeString();
		
		return "information";

	}
	


	/**
	 * 
	 * @Title: queryPayPrice
	 * @Description: TODO(校验用户购买产生积分是否足够兑换产品)
	 * @return String 返回类型
	 * @author
	 */
	@InputConfig(resultName = "error")
	public String queryPayPrice() {
		Map<String, String> jsonMap = new HashMap<String, String>();
		loginmap = new HashMap<String, String>();
		Member member = getLoginMember();
		if (member == null) {
			jsonMap.put(STATUS, "2");
			return ajax(JSONObject.fromObject(jsonMap).toString(), "text/html");
		}
		
		String totalPayPrice = new QueryBuilder("SELECT ROUND(SUM(a.payprice),2) FROM sdinformationrisktype a,sdorders b WHERE a.`orderSn` = b.`orderSn` AND a.appstatus = '1' AND b.memberid ='"+member.getId()+"'").executeString();
		if(StringUtil.isEmpty(totalPayPrice)){
			totalPayPrice = "0.00";
		}
		double shortPrice = ArithUtil.sub(new BigDecimal(totalPayPrice).doubleValue(), new BigDecimal(Config.getValue("exchangeBoundary")).doubleValue());
		if(shortPrice >= 0){
			jsonMap.put(STATUS, "1");
		}else{
			jsonMap.put(STATUS, "3");
			jsonMap.put(MESSAGE, String.valueOf(Config.getValue("exchangeBoundary")));
		}
		
		// 判断用户可用积分是否能兑换产品
		String points = getParameter("prem");
		if (StringUtil.isNotEmpty(points) && NumberUtil.isInt(points)) {
			if (member.getCurrentValidatePoint() < Integer.parseInt(points)) {
				jsonMap.put(STATUS, "4");
				jsonMap.put(MESSAGE, String.valueOf(member.getCurrentValidatePoint()));
			}
		}
		return ajax(JSONObject.fromObject(jsonMap).toString(), "text/html");
	}

	/**
	 * 
	 * @Title: initIntegralMallLoginfo
	 * @Description: TODO(初始化登录信息)
	 * @return String 返回类型
	 * @author
	 */
	@InputConfig(resultName = "error")
	public String initIntegralMallLoginfo() {
		String id = (String) getSession(Member.LOGIN_MEMBER_ID_SESSION_NAME);
		loginmap = new HashMap<String, String>();
		if (StringUtil.isEmpty(id)) {
			return "login";
		}
		Member member = memberService.load(id);
		if(!"tencent".equals(id)){
			member = memberService.load(id);
		}else{
			member = bindInfoForLoginService.get(getSession("loginBindId").toString()).getmMember();
		}
		if (member == null) {
			return "login";
		}
		
		String currentvalidatepoint = String.valueOf(member.getCurrentValidatePoint());
		loginmap.put("currentvalidatepoint", currentvalidatepoint);
		String PointScalerUnit = Config.getConfigValue("PointScalerUnit");
		loginmap.put("pointprice", changeIntegral(Integer.parseInt(currentvalidatepoint), Integer.parseInt(PointScalerUnit)));
		String sql = "SELECT COUNT(1) FROM sdorders a,sdinformation b, sdsearchrelaproduct c WHERE a.memberid = ? AND a.orderStatus IN ('7','10') and a.orderSn = b.orderSn and b.productid = c.productid AND a.commentId IS NULL";
		DataTable dt_commentNum = new QueryBuilder(sql, member.getId()).executeDataTable();
		if (dt_commentNum.getRowCount() > 0) {
			String commentNum = dt_commentNum.getString(0, 0);
			loginmap.put("commentnum", commentNum);
			Map param=new HashMap();
			param.put("PointsGive","01");
			try {
				Map<String, Object> map = new PointsCalculate().pointsManage(IntegralConstant.POINT_SEARCH,"1", param);
				List<Map<String, Object>> list=(List<Map<String, Object>>) map.get(IntegralConstant.DATA);
				if(list.size()>0){
					Map map_data=list.get(0);
					loginmap.put("commentpoint", String.valueOf(new BigDecimal(String.valueOf(map_data.get("PointsNum"))).multiply(new BigDecimal(commentNum)).setScale(0, BigDecimal.ROUND_HALF_UP)));
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		} else {
			loginmap.put("commentnum", "0");
			loginmap.put("commentpoint", "0");
		}
		String point = String.valueOf(member.getPoint());
		if (StringUtil.isEmpty(point)) {
			loginmap.put("point", "0");
		} else {
			loginmap.put("point", point);
		}
		String headpicpath = member.getHeadPicPath();
		if (StringUtil.isEmpty(headpicpath)) {
			loginmap.put("headpicpath", "/images/jifen/header_03.gif");
		} else {
			loginmap.put("headpicpath",  headpicpath);
		}

		return "login";
	}

	/**
	 * 
	 * @Title: Exchange
	 * @Description: TODO(校验会员积分是否足够兑换)
	 * @return String 返回类型
	 * @author
	 */
	@InputConfig(resultName = "error")
	public String checkPoint() {

		Map<String, String> jsonMap = new HashMap<String, String>();
		// 根据礼品id产讯礼品
		mgiftclassfy = mGiftClassifyService.getGiftClassify(presentID);
		if (mgiftclassfy == null) {
			jsonMap.put(MESSAGE, "产品礼品不存在!");
			jsonMap.put(STATUS, "1");
			return ajax(JSONObject.fromObject(jsonMap).toString(), "text/html");

		}
		String mobile = getRequest().getParameter("mobile");
		Member member = getLoginMember();
		if (member == null) {
			jsonMap.put(STATUS, "2");
			return ajax(JSONObject.fromObject(jsonMap).toString(), "text/html");
		}
		String memberid= member.getId();
		String totalPayPrice = new QueryBuilder("SELECT ROUND(SUM(a.payprice),2) FROM sdinformationrisktype a,sdorders b WHERE a.`orderSn` = b.`orderSn` AND a.appstatus = '1' AND b.memberid =?", memberid).executeString();
		if(StringUtil.isEmpty(totalPayPrice)){
			totalPayPrice = "0.00";
		}
		double shortPrice = ArithUtil.sub(new BigDecimal(totalPayPrice).doubleValue(), new BigDecimal(Config.getValue("exchangeBoundary")).doubleValue());
		if(shortPrice < 0){
			jsonMap.put(STATUS, "3");
			jsonMap.put(MESSAGE, String.valueOf(Config.getValue("exchangeBoundary")));
			return ajax(JSONObject.fromObject(jsonMap).toString(), "text/html");
		}
		
		try {

			Class<?> tbDeal = Class.forName("cn.com.sinosoft.util.Points" + mgiftclassfy.getType());
			PointExchangeInterface pointdeal = (PointExchangeInterface) tbDeal.newInstance();
			pointdeal.init(mGiftClassifyService, mGoodsStockService, memberService, memberid, presentID);
			JSONObject jsonObject = JSONObject.fromObject(pointdeal.checkPoint(mobile));
			
			return ajax(jsonObject.toString(), "text/html");

		} catch (Exception e) {
			logger.error("类 PointsAction。Exchange 积分兑换失败，处理方法：" + mgiftclassfy.getId() + e.getMessage(), e);
			return null;
		}

	}

	public String checkYZM() {
		Map<String, String> jsonMap = new HashMap<String, String>();
		Member member = getLoginMember();
		if (member == null) {
			jsonMap.put(STATUS, "NoLogin");
			return ajaxJson(jsonMap);
		}
		// 验证图片验证码是否正确
		String inputTPYZM = getRequest().getParameter("inputTPYZM");
		
		try {
			String captchaID = getRequest().getSession().getId();
			
			String challengeResponse = StringUtils.upperCase(inputTPYZM);
			
			if (StringUtils.isEmpty(challengeResponse)
					|| captchaService.validateResponseForID(captchaID, challengeResponse) == false) {
				jsonMap.put(STATUS, "yzmerror");
				return ajaxJson(jsonMap);
			}
			setSession("exchangeUserYzm", challengeResponse);
		} catch (CaptchaServiceException ce) {
			logger.error(ce.getMessage(), ce);
			jsonMap.put(STATUS, "yzmerror");
			return ajaxJson(jsonMap);
		}  catch (Exception e) {
			logger.error(e.getMessage(), e);
			jsonMap.put(STATUS, "yzmerror");
			return ajaxJson(jsonMap);
		}
		
		jsonMap.put(STATUS, "0");
		return ajaxJson(jsonMap);
	}
	public String sendYZM() {
		
		Map<String, String> jsonMap = new HashMap<String, String>();
		Member member = getLoginMember();
		if (member == null) {
			jsonMap.put(STATUS, "NoLogin");
			return ajaxJson(jsonMap);
		}
		
		// 验证图片验证码是否正确
		String inputTPYZM = getRequest().getParameter("inputTPYZM");
		try {
			String captchaID = getRequest().getSession().getId();
			String challengeResponse = StringUtils.upperCase(inputTPYZM);
			if (StringUtils.isEmpty(challengeResponse)
					|| captchaService.validateResponseForID(captchaID, challengeResponse) == false) {
				jsonMap.put(STATUS, "yzmerror");
				return ajaxJson(jsonMap);
			}
		} catch (CaptchaServiceException ce) {
			logger.error(ce.getMessage(), ce);
			jsonMap.put(STATUS, "yzmerror");
			return ajaxJson(jsonMap);
		}  catch (Exception e) {
			logger.error(e.getMessage(), e);
			jsonMap.put(STATUS, "yzmerror");
			return ajaxJson(jsonMap);
		}
		
		// 发送验证码
		String email = "";
		String mobileNo = "";
		// 邮箱注册，发送验证码邮件
		if ("0".equals(member.getRegisterType()) && StringUtil.isNotEmpty(member.getEmail())) {
			email = member.getEmail();
			
			// 手机注册，发送验证码短信
		} else if ("1".equals(member.getRegisterType()) && StringUtil.isNotEmpty(member.getMobileNO())) {
			mobileNo = member.getMobileNO();
			
			// 绑定手机，发送验证码短信
		} else if ("Y".equals(member.getIsMobileNOBinding()) && StringUtil.isNotEmpty(member.getMobileNO())) {
			mobileNo = member.getMobileNO();
			
			// 绑定邮箱，发送验证码邮件
		} else if ("Y".equals(member.getIsEmailBinding()) && StringUtil.isNotEmpty(member.getEmail())) {
			email = member.getEmail();

			// 被动注册手机号
		} else if ("2".equals(member.getRegisterType()) && StringUtil.isNotEmpty(member.getMobileNO())) {
			mobileNo = member.getMobileNO();

			// 被动注册的邮箱
		} else if ("2".equals(member.getRegisterType()) && StringUtil.isNotEmpty(member.getEmail())) {
			email = member.getEmail();
		}
		
		// 获取验证码
		MemberAction memberAction = new MemberAction();
		String svcode = memberAction.VCFactory();
		// 发送短信
		if (StringUtil.isNotEmpty(mobileNo)) {
			if (ActionUtil.sendSms("wj00312", mobileNo, svcode)) {
				setSession("exchange_sendTime", System.currentTimeMillis());
				setSession("exchange_sendValidateCode", svcode);
			}
			jsonMap.put(MESSAGE, "兑换验证码已发送到您的注册手机<br><em style='color:#ff6666'>"+mobileNo+"</em>中，请注意查收。");
			// 发送邮件
		} else if (StringUtil.isNotEmpty(email)) {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("Captcha", svcode);
			data.put("MemberRealName", member.getRealName() == null? 
					"尊敬的会员您好:":"尊敬的&nbsp;" + member.getRealName() + "&nbsp;您好:");
			// 邮件中显示活动、推荐产品信息
			data.put(MessageConstant.PARAM_SHOW_ACTIVITY_PRODUCT, "1");
			// 接收人
			data.put(MessageConstant.PARAM_RECEIVER_NAME, email);
			if (ActionUtil.sendMail("wj00314", email, data)) {
				setSession("exchange_sendTime", System.currentTimeMillis());
				setSession("exchange_sendValidateCode", svcode);
			}
			jsonMap.put(MESSAGE, "兑换验证码已发送到您的注册邮箱<br><em style='color:#ff6666'>"+email+"</em>中，请注意查收。");
		}
		jsonMap.put(STATUS, "0");
		return ajaxJson(jsonMap);
		
	}
	
	/**
	 * 
	 * @Title: Exchange
	 * @Description: TODO(兑换礼品)
	 * @return String 返回类型
	 * @author moidfy by cuishigang 新积分兑换流程
	 */
	@InputConfig(resultName = "error")
	public String Exchange() {

		Map<String, String> jsonMap = new HashMap<String, String>();
		// 根据礼品id产讯礼品
		mgiftclassfy = mGiftClassifyService.getGiftClassify(presentID);
		if (mgiftclassfy == null) {
			jsonMap.put(MESSAGE, "产品礼品不存在!");
			jsonMap.put(STATUS, "1");
			return ajax(JSONObject.fromObject(jsonMap).toString(), "text/html");

		}

		String mobile = getRequest().getParameter("mobile");
		Member member = getLoginMember();
		if (member == null) {
			jsonMap.put(STATUS, "2");
			return ajax(JSONObject.fromObject(jsonMap).toString(), "text/html");
		}
		String memberid= member.getId();
		String totalPayPrice = new QueryBuilder("SELECT ROUND(SUM(a.payprice),2) FROM sdinformationrisktype a,sdorders b WHERE a.`orderSn` = b.`orderSn` AND a.appstatus = '1' AND b.memberid =?", memberid).executeString();
		if(StringUtil.isEmpty(totalPayPrice)){
			totalPayPrice = "0.00";
		}
		double shortPrice = ArithUtil.sub(new BigDecimal(totalPayPrice).doubleValue(), new BigDecimal(Config.getValue("exchangeBoundary")).doubleValue());
		if(shortPrice < 0){
			jsonMap.put(STATUS, "3");
			jsonMap.put(MESSAGE, String.valueOf(Config.getValue("exchangeBoundary")));
			return ajax(JSONObject.fromObject(jsonMap).toString(), "text/html");
		}
		
		String sdcodes = getParameter("sdcodef");
		String scd = (String) getSession("exchange_sendValidateCode");
		// 验证码失效
		if (StringUtil.isEmpty(scd)) {
			jsonMap.put(STATUS, "yzmerror");
			jsonMap.put(MESSAGE, "验证码失效！");
			return ajax(JSONObject.fromObject(jsonMap).toString(), "text/html");
		}

		// 验证码错误
		if (!scd.equals(sdcodes)) {
			jsonMap.put(STATUS, "yzmerror");
			jsonMap.put(MESSAGE, "验证码输入错误！");
			return ajax(JSONObject.fromObject(jsonMap).toString(), "text/html");
		}
		getRequest().getSession().setAttribute("exchange_sendValidateCode", "");
		try {
			Class<?> tbDeal = Class.forName("cn.com.sinosoft.util.Points" + mgiftclassfy.getType());
			PointExchangeInterface pointdeal = (PointExchangeInterface) tbDeal.newInstance();
			pointdeal.init(mGiftClassifyService, mGoodsStockService, memberService, memberid, presentID);
			JSONObject jsonObject = JSONObject.fromObject(pointdeal.Exchange(mobile));
			
			return ajax(jsonObject.toString(), "text/html");

		} catch (Exception e) {
			logger.error("类 PointsAction。Exchange 积分兑换失败，处理方法：" + mgiftclassfy.getId() + e.getMessage(), e);
			getRequest().getSession().setAttribute("exchange_sendValidateCode", "");
			return null;
		}
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
			BigDecimal result = new BigDecimal(Integral).divide(new BigDecimal(Multiple)).setScale(2, BigDecimal.ROUND_HALF_UP);
			return String.valueOf(result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "0";
		}
	}

	/**
	 * 获取热销、推荐礼品列表
	 * 
	 * @return
	 */
	public String hotgiftlist() {

		String thtml = "";
		try {
			StringBuffer sb = new StringBuffer(" SELECT a.id,a.GiftTitle,a.Points,a.LinkUrl,a.type FROM  GiftClassify a WHERE a.Recommend='Y' and a.status ='0' and (a.lastNum+0) > 0 and UNIX_TIMESTAMP(DATE_FORMAT(a.startDate,'%Y-%m-%d %H:%i:%S')) <= UNIX_TIMESTAMP(DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%S')) and UNIX_TIMESTAMP(DATE_FORMAT(a.endDate,'%Y-%m-%d %H:%i:%S')) >= UNIX_TIMESTAMP(DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%S')) ORDER BY a.createDate DESC LIMIT 5 ");
			QueryBuilder qb = new QueryBuilder(sb.toString());
			DataTable dt = qb.executeDataTable();
			int len = dt.getRowCount();
			if (dt != null && len >= 1) {
				for (int i = 0; i < len; i++) {

					String type = dt.getString(i, "type");
					String jumpurl = dt.getString(i, "LinkUrl");
					if (!"1".equals(type)) {
						jumpurl = Config.getValue("ServerContext") + "/shop/points!integralMallInformation.action?presentID=" + dt.getString(i, "id");
					}

					String chtml = "<li><a target='_blank' href=" + jumpurl + ">" + dt.getString(i, "GiftTitle") + "</a><span>" + dt.getString(i, "Points") + "积分</span></li> ";

					thtml = thtml + chtml;
				}
			}
		} catch (Exception e) {
			logger.error("类 PointsUtil。hotgiftlist 获取热销礼品列表异常：" + e.getMessage(), e);
		}

		String thtml1 = "";
		try {
			StringBuffer sb1 = new StringBuffer("  SELECT a.id,a.GiftName,a.Points,a.LinkUrl,a.LogoUrl,a.type,a.points,a.popularity FROM GiftClassify a where a.status ='0' and (a.lastNum+0) > 0 and UNIX_TIMESTAMP(DATE_FORMAT(a.startDate,'%Y-%m-%d %H:%i:%S')) <= UNIX_TIMESTAMP(DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%S')) and UNIX_TIMESTAMP(DATE_FORMAT(a.endDate,'%Y-%m-%d %H:%i:%S')) >= UNIX_TIMESTAMP(DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%S')) ORDER BY (a.Popularity+0) DESC,a.createDate DESC LIMIT 5 ");
			QueryBuilder qb1 = new QueryBuilder(sb1.toString());
			DataTable dt1 = qb1.executeDataTable();
			int len1 = dt1.getRowCount();
			if (dt1 != null && len1 >= 1) {
				for (int i = 0; i < len1; i++) {
					
					String type = dt1.getString(i, "type");
					String jumpurl = dt1.getString(i, "LinkUrl");
					if (!"1".equals(type)) {
						jumpurl = Config.getValue("ServerContext") + "/shop/points!integralMallInformation.action?presentID=" + dt1.getString(i, "id");
						
					}

					String chtml1 = "<dl class='hot_giftdl'> <dt class='hot_gifttit'><a href='" + jumpurl + "' target='_blank'>" + dt1.getString(i, 1) + "</a></dt>";
					if (i == 0) {
						chtml1 += " <dd class='hot_giftdd'> <img src=" + Config.getValue("StaticResourcePath") + "/" + dt1.getString(i, 4) + " width='77px;' alt='' />";
					} else {
						chtml1 += " <dd style='display:none' class='hot_giftdd'> <img src=" + Config.getValue("StaticResourcePath") + "/" + dt1.getString(i, 4) + " width='77px;' alt='' />";
					}
					String popularity = dt1.getString(i, "popularity");
					if (StringUtil.isNotEmpty(popularity) && popularity.length() > 4) {
						popularity = popularity.substring(0, popularity.length() - 4) + "万";
					}
					chtml1 += " <div class='hot_gift'><em>" + dt1.getString(i, "points") + "积分</em><br />人气：<em>" + popularity + "</em></div></dd></dl> ";
					thtml1 = thtml1 + chtml1;
				}
			}
		} catch (Exception e) {
			logger.error("类 PointsUtil。recgiftlist 获取推荐礼品列表异常：" + e.getMessage(), e);
		}
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put("pointhothtml", thtml1);
		jsonMap.put("pointrechtml", thtml);
		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		return ajax(jsonObject.toString(), "text/html");

	}

	/**
	 * 积分兑换结果页
	 * 
	 * @return
	 */
	public String pointresult() {

		try {
			if (StringUtil.isNotEmpty(orderSn)) {

				mpointxxchangeInfo = mPointExchangeInfoService.pointexchangelist(orderSn).get(0);
				// 处理积分兑换状态
				if ("20".equals(mpointxxchangeInfo.getStatus())) {
					mpointxxchangeInfo.setStatus("已发送");
				} else if("21".equals(mpointxxchangeInfo.getStatus())) {
					mpointxxchangeInfo.setStatus("已充值");
				}else {
					mpointxxchangeInfo.setStatus("已兑换");
				}
				if (StringUtil.isNotEmpty(mpointxxchangeInfo.getMobileNo())) {
					mpointxxchangeInfo.setMobileNo(mpointxxchangeInfo.getMobileNo().substring(0, 3) + "****" + mpointxxchangeInfo.getMobileNo().substring(7, 11));
				} else {
					mpointxxchangeInfo.setMobileNo("未知");
				}
				String giftid = mpointxxchangeInfo.getGiftClassifyID();
				String goodsid = mpointxxchangeInfo.getGoodsStockID();
				mgiftclassfy = mGiftClassifyService.getGiftClassify(giftid);
				mgoodsstock = mGoodsStockService.load(goodsid);
				
				HttpServletRequest request = ServletActionContext.getRequest();
				int points = 0;
				String exchangeFlag = "N";
				if (StringUtil.isNotEmpty(mpointxxchangeInfo.getMemberid())) {
					Member member = memberService.load(mpointxxchangeInfo.getMemberid());
					if (member != null) {
						if (member.getCurrentValidatePoint() != null && member.getCurrentValidatePoint() != 0) {
							points = member.getCurrentValidatePoint();
							String sql1 = "select count(1) from GiftClassify where status = '0' and (LastNum + 0) > 0 and UNIX_TIMESTAMP(DATE_FORMAT(StartDate,'%Y-%m-%d %H:%i:%S')) <= UNIX_TIMESTAMP(DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%S')) and UNIX_TIMESTAMP(DATE_FORMAT(EndDate,'%Y-%m-%d %H:%i:%S')) >= UNIX_TIMESTAMP(DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%S')) and Points+0 <= ? ";
							QueryBuilder qb = new QueryBuilder(sql1, points);
							if (qb.executeInt() > 0) {
								exchangeFlag = "Y";
							}
						}
					}
				}
				request.setAttribute("points", String.valueOf(points));
				request.setAttribute("exchangeFlag", exchangeFlag);
				// 模式是现货的情况
				if ("1".equals(mgiftclassfy.getPointsExchangeType())) {
					request.setAttribute("orderDesc", "兑换礼品发送手机");
				} else {
					request.setAttribute("orderDesc", "兑换礼品充值手机");
					if ("6".equals(mpointxxchangeInfo.getType())) {
						request.setAttribute("orderDesc", "兑换礼品");
					}
				}
				
			} else {
				logger.error("类 PointsAction。pointresult 积分兑换订单结果异常,积分订单号为空");
				addActionError("查询积分兑换结果失败，积分订单号为空，请联系管理员");
				return ERROR;
			}

		} catch (Exception e) {
			logger.error("类 PointsAction。pointresult 积分兑换订单结果异常" + e.getMessage(), e);
			addActionError("查询积分兑换结果失败，请联系管理员");
			return ERROR;
		}

		return "pointsresult";

	}

	public String getErrormsg() {
		return errormsg;
	}

	public void setErrormsg(String errormsg) {
		this.errormsg = errormsg;
	}

	public String getSparePoint() {
		return SparePoint;
	}

	public void setSparePoint(String sparePoint) {
		SparePoint = sparePoint;
	}

	public String getPresentID() {
		return presentID;
	}

	public void setPresentID(String presentID) {
		this.presentID = presentID;
	}

	public String getArticleUrl() {
		return articleUrl;
	}

	public void setArticleUrl(String articleUrl) {
		this.articleUrl = articleUrl;
	}

	public String getArticleLogo() {
		return articleLogo;
	}

	public void setArticleLogo(String articleLogo) {
		this.articleLogo = articleLogo;
	}

	public String getTotalPoints() {
		return TotalPoints;
	}

	public void setTotalPoints(String totalPoints) {
		TotalPoints = totalPoints;
	}

	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	public Map<String, String> getLoginmap() {
		return loginmap;
	}

	public void setLoginmap(Map<String, String> loginmap) {
		this.loginmap = loginmap;
	}

	public List<Present> getList() {
		return list;
	}

	public void setList(List<Present> list) {
		this.list = list;
	}

	public List<GiftClassify> getHotgiftlist() {
		return hotgiftlist;
	}

	public void setHotgiftlist(List<GiftClassify> hotgiftlist) {
		this.hotgiftlist = hotgiftlist;
	}

	public List<GiftClassify> getRecgifglist() {
		return recgifglist;
	}

	public void setRecgifglist(List<GiftClassify> recgifglist) {
		this.recgifglist = recgifglist;
	}

	public GiftClassify getMgiftclassfy() {
		return mgiftclassfy;
	}

	public void setMgiftclassfy(GiftClassify mgiftclassfy) {
		this.mgiftclassfy = mgiftclassfy;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public GoodsStock getMgoodsstock() {
		return mgoodsstock;
	}

	public void setMgoodsstock(GoodsStock mgoodsstock) {
		this.mgoodsstock = mgoodsstock;
	}

	public PointExchangeInfo getMpointxxchangeInfo() {
		return mpointxxchangeInfo;
	}

	public void setMpointxxchangeInfo(PointExchangeInfo mpointxxchangeInfo) {
		this.mpointxxchangeInfo = mpointxxchangeInfo;
	}

	public String getDealpointflag() {
		return dealpointflag;
	}

	public void setDealpointflag(String dealpointflag) {
		this.dealpointflag = dealpointflag;
	}

	public List<GiftClassify> getNewGiftlist() {
		return newGiftlist;
	}

	public void setNewGiftlist(List<GiftClassify> newGiftlist) {
		this.newGiftlist = newGiftlist;
	}

	public List<Map<String, Object>> getModelList() {
		return modelList;
	}

	public void setModelList(List<Map<String, Object>> modelList) {
		this.modelList = modelList;
	}
	public List<GiftClassify> getList_data() {
		return list_data;
	}
	public void setList_data(List<GiftClassify> list_data) {
		this.list_data = list_data;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	
}
