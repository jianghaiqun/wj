package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.entity.OrderDutyFactor;
import cn.com.sinosoft.entity.OrderRiskAppFactor;
import cn.com.sinosoft.entity.SDInformation;
import cn.com.sinosoft.entity.SDInformationAppnt;
import cn.com.sinosoft.entity.SDInformationDuty;
import cn.com.sinosoft.entity.SDInformationInsured;
import cn.com.sinosoft.entity.SDInformationInsuredElements;
import cn.com.sinosoft.entity.SDInformationRiskType;
import cn.com.sinosoft.entity.SDOrder;
import cn.com.sinosoft.entity.SDOrder.SDOrderStatus;
import cn.com.sinosoft.entity.SDOrder.SDPayStatus;
import cn.com.sinosoft.entity.SDOrderItem;
import cn.com.sinosoft.entity.SDOrderItemOth;
import cn.com.sinosoft.entity.TradeInformation;
import cn.com.sinosoft.service.DealDataService;
import cn.com.sinosoft.service.SDOrderService;
import cn.com.sinosoft.util.WapShoppingUtil;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.jdt.InsureTransferNew;
import com.sinosoft.lis.pubfun.IDCard;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.platform.pub.NoUtil;
import com.wangjin.optimize.register.AutoRegister;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.json.JSONObject;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
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

/**
 * 超白金H5接口处理类 v.1.0
 * 变更“感恩”安全座椅八周年店庆 赠民生出行福交通工具意外保险 处理 v.2.0
 * @author GaoHaijun
 *
 */
@ParentPackage("shop")
public class BrandShoppingAction extends BaseShopAction {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8747839778311011800L;
	private static final String channelSn = "cps_swpt";
	
	// 产品编码
	private String productId;
	// 保险起期,格式:yyyyMMddHHmmss
	private String startDate;
	// 保险止期,格式:yyyyMMddHHmmss
	private String endDate;
	// 姓名
	private String name;
	// 身份证号码
	private String identityId;
	// 手机号
	private String mobile;
	// 邮箱
	private String email;
	
	@Resource
	private SDOrderService sdorderService;
	@Resource
	private DealDataService mDealDataService;
	
	/**
	 * 订单保存
	 * @return json格式返回信息:Status 接口状态0-成功 1-异常; Msg 接口信息; LoginName 会员登录名; Password 初始密码; PolicyMsg 保单查询信息
	 */
	@SuppressWarnings("unchecked")
	public String saveOrder() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		productId = "103801001";
		try {
			name = java.net.URLDecoder.decode(name, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			logger.error(e1.getMessage(), e1);
		}
		// 身份证校验
		identityId = identityId.toLowerCase();
		String valMsg = IDCard.IDCardValidate(identityId);
		if (StringUtil.isNotEmpty(valMsg)) {
			resultMap.put("Status", "1");
			resultMap.put("Msg", valMsg);
			return ajax(new JSONObject(resultMap).toString(), "text/html");
		}
		
		// 解析身份证获取生日
		String birthday = PubFun.getBirthdayFromId(identityId);
		if (StringUtil.isEmpty(birthday)) {
			resultMap.put("Status", "1");
			resultMap.put("Msg", "身份证生日无效");
			return ajax(new JSONObject(resultMap).toString(), "text/html");
		}
		
		// 解析身份证获取性别
		String sex = PubFun.getSexFromId(identityId);
		if (StringUtil.isEmpty(sex)) {
			resultMap.put("Status", "1");
			resultMap.put("Msg", "身份证性别无效");
			return ajax(new JSONObject(resultMap).toString(), "text/html");
		}
		sex = "0".equals(sex) ? "M" : "F";
		
		// 判断手机号合法性
		if (!StringUtil.isMobileNO(mobile)) {
			resultMap.put("Status", "1");
			resultMap.put("Msg", "手机号不正确");
			return ajax(new JSONObject(resultMap).toString(), "text/html");
		}
		
		// 判断邮箱合法性
		if (!StringUtil.isMail(email)) {
			resultMap.put("Status", "1");
			resultMap.put("Msg", "邮箱不正确");
			return ajax(new JSONObject(resultMap).toString(), "text/html");
		}
		// 次日生效，保障90天
		startDate = DateUtil.toString(DateUtil.addDay(new Date(), 1)) + " 00:00:00";
		endDate = DateUtil.toString(DateUtil.addDay(new Date(), 90)) + " 23:59:59";
		// 开始保存订单信息，直接将订单状态置为已支付
		String orderSn = com.sinosoft.cms.pub.PubFun.GetOrderSn();
		String informationSn = com.sinosoft.cms.pub.PubFun.GetSDInformationSn();
		String paySn = com.sinosoft.cms.pub.PubFun.GetPaySn();
		// 获取产品信息
		Map<String, Object> paramter = new HashMap<String, Object>();
		paramter = sdorderService.getProductInformation(productId, "N", "");// 产品ID
		List<OrderRiskAppFactor> riskAppFactior = (List<OrderRiskAppFactor>) paramter.get("riskAppFactor");
		List<OrderDutyFactor> dutyFactor = (List<OrderDutyFactor>) paramter.get("dutyFactor");
		String initPrem = ((String[])paramter.get("baseInformation"))[6];
		
		// 获取订单表信息 sdorders
		SDOrder sdorder = getSDOrder(orderSn, paySn, initPrem);
		// 获取订单明细表信息 sdinformation
		SDInformation sdInformation = getSDInformation(informationSn, paramter);
		sdInformation.setOrderSn(orderSn);
		sdInformation.setSdorder(sdorder);
		Set<SDInformation> tSDInformationSet = new HashSet<SDInformation>();
		tSDInformationSet.add(sdInformation);
		sdorder.setSdinformationSet(tSDInformationSet);
		// 获取投保人表信息 sdInformationAppnt
		SDInformationAppnt sdInformationAppnt = getSDInformationAppnt(sex, birthday);
		sdInformationAppnt.setInformationSn(informationSn);
		// 保存投保人时将information的投保人信息进行更新
		Set<SDInformationAppnt> appSet = new HashSet<SDInformationAppnt>();
		appSet.add(sdInformationAppnt);
		sdInformation.setSdinformationappntSet(appSet);
		sdInformationAppnt.setSdinformaiton(sdInformation);
		// 获取订单项1
		SDOrderItem sdorderitem = getSDOrderItem(sdorder);
		// 获取被保人表信息 sdInformationInsured
		List<SDInformationInsured> minsuredList = getSDInformationInsured(initPrem, sex, birthday);
		List<SDOrderItemOth> sdorderitemothList = new ArrayList<SDOrderItemOth>();
		List<SDInformationRiskType> mSDInformationRiskTypeList = new ArrayList<SDInformationRiskType>();
		List<SDInformationInsuredElements> melementslist = new ArrayList<SDInformationInsuredElements>();
		// 获取责任信息
		WapShoppingUtil tWapShoppingUtil = new WapShoppingUtil();
		List<SDInformationDuty> mdutylist = tWapShoppingUtil.getInformationDuty(sdorder, sdInformation, dutyFactor);
		
		for (int i = 0; i < minsuredList.size(); i++) {
			SDInformationInsured sdInformationInsured = minsuredList.get(i);
			sdInformationInsured.setOrderSn(orderSn);
			sdInformationInsured.setInsuredSn(orderSn + "_" + (i + 1));
			sdInformationInsured.setRecognizeeKey(orderSn + "_" + (i + 1));
			sdInformationInsured.setInformationSn(informationSn);
			sdInformationInsured.setSdinformation(sdInformation);
			// 获取订单项2
			SDOrderItemOth sdorderitemoth = new SDOrderItemOth();
			sdorderitemoth.setOrderSn(orderSn);
			sdorderitemoth.setRecognizeeSn(sdInformationInsured.getRecognizeeSn());
			sdorderitemoth.setOrderitemSn(com.sinosoft.cms.pub.PubFun.GetItemOthNo());
			sdorderitemoth.setSdinformationinsured(sdInformationInsured);
			sdorderitemothList.add(sdorderitemoth);
			sdInformationInsured.setRecognizeeTotalPrem(initPrem);
			// 获取保单信息
			SDInformationRiskType sdInformationRiskType = 
					tWapShoppingUtil.getSDInformationRiskType(sdorder, sdInformation, sdInformationAppnt, sdInformationInsured);
			// wap站逻辑意外的处理
			sdInformationRiskType = dealSDInformationRiskType(sdInformationRiskType, initPrem);
			
			sdInformationInsured.setSdinformaitonrisktype(sdInformationRiskType);
			mSDInformationRiskTypeList.add(sdInformationRiskType);
			// 获取投保要素信息
			Set<SDInformationInsuredElements> sdinforinselementsSet = 
					getInfomationInsuredElements(sdorder, sdInformation, sdInformationInsured, riskAppFactior);
			for (SDInformationInsuredElements element : sdinforinselementsSet) {
				melementslist.add(element);
			}
			sdInformationInsured.setSdinforinselementsSet(sdinforinselementsSet);
		}
		// 获取交易表信息
		TradeInformation tTradeInformation = getTradeInformation(orderSn, paySn, initPrem);
		List<TradeInformation> tradeList = new ArrayList<TradeInformation>();
		tradeList.add(tTradeInformation);
		// 保存订单表
		List<SDOrder> orderList = new ArrayList<SDOrder>();
		orderList.add(sdorder);
		// 限购份数校验
		if (!checkLimit(sdInformation.getProductOutCode(), "0")) {
			resultMap.put("Status", "1");
			resultMap.put("Msg", "购买超过最高限购份数!");
			return ajax(new JSONObject(resultMap).toString(), "text/html");
		}
		
		// 保存信息到数据库
		try {
			LinkedHashMap<Object, String> mLMap = new LinkedHashMap<Object, String>();
			mLMap.put(orderList, "insert");
			// 保存订单详细表
			mLMap.put(sdInformation, "insert");
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
			// 保存交易表
			mLMap.put(tradeList, "insert");
			
			if (!mDealDataService.saveAll(mLMap)) {
				resultMap.put("Status", "1");
				resultMap.put("Msg", "系统异常");
				return ajax(new JSONObject(resultMap).toString(), "text/html");
			}
			
			// 处理交易信息
			if (!dealTradeInfo(orderSn, paySn, initPrem)) {
				resultMap.put("Status", "1");
				resultMap.put("Msg", "系统异常");
				return ajax(new JSONObject(resultMap).toString(), "text/html");
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			resultMap.put("Status", "1");
			resultMap.put("Msg", "系统异常");
			return ajax(new JSONObject(resultMap).toString(), "text/html");
		}
		
		// 使用手机号注册会员，若未注册过，直接注册并返回初始密码为:123456；若注册过，则不注册并返回初始密码为：您已设置过
		try {
			AutoRegister autoRegister = new AutoRegister();
			String LoginName = "";
			String memberId =autoRegister.getEmailMemberId(email);
			LoginName = email;
			if (StringUtil.isEmpty(memberId)) {
				memberId = autoRegister.getMobileMemberId(mobile);
				LoginName = mobile;
			}
			
			if (StringUtil.isNotEmpty(memberId)) {
				Transaction trans = new Transaction();
		        trans.add(new QueryBuilder("update sdorders set memberid=? where ordersn=?" ,memberId,orderSn));
		        trans.add(new QueryBuilder("update tradeInformation set merId=? where ordID=?" ,memberId,orderSn));
		        if (!trans.commit()) {
		            logger.error("orderSn：{}归属 MemberID：{}没有成功！", orderSn, memberId);
		        }
		        
				resultMap.put("Status", "0");
				resultMap.put("Msg", "交易成功");
				resultMap.put("LoginName", LoginName);
				resultMap.put("Password", "您已设置过");
				resultMap.put("PolicyMsg", "请登陆www.kaixinbao.com会员中心可查看订单。");
				
			} else {
				autoRegister.registForBrand(email, mobile, sdorder);
				resultMap.put("Status", "0");
				resultMap.put("Msg", "交易成功");
				resultMap.put("LoginName", email);
				resultMap.put("Password", "123456");
				resultMap.put("PolicyMsg", "请登陆www.kaixinbao.com会员中心可查看订单。");
			}
			// 承保
			SDOrder sdor = sdorderService.getOrderByOrderSn(orderSn);
			for (SDInformation sdi : sdor.getSdinformationSet()) {
				Set<SDInformationInsured> sdinforInsured = sdi.getSdinformationinsuredSet();
				if (sdinforInsured == null || sdinforInsured.isEmpty()) {
					break;
				}
				try {
					// 调用经代通发送报文
					class MutliThread extends Thread {
						private SDInformation sdi;
						private SDOrder sdo;
						private Set<SDInformationInsured> sdinformationInsuredSet;
						
						MutliThread(SDInformation sdi1,SDOrder sdo1, Set<SDInformationInsured> insured){
							sdi = sdi1;
							sdo = sdo1;
							sdinformationInsuredSet = insured;
						}
						
						@Override
						public void run() {
							InsureTransferNew itn = new InsureTransferNew();
							List<String> UWUNSucess = sdorderService.checkUnderwriting(sdo, null);
							boolean flag = true;
							String appStatus = "";
							String insureMsg = "";
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
							
							if(flag){
							itn.callInsTransInterface(sdi.getInsuranceCompany(), sdo.getOrderSn(), UWUNSucess);
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

								String ErrMsg = "经代通失败:" + insureMsg;

								if (StringUtil.isEmpty(insureMsg)) {
									ErrMsg = "经代通失败:未收到保险公司回写结果";
								}
								PayAction payAction = new PayAction();
								payAction.sendErrorMail(sdo.getOrderSn(), ErrMsg);

							}
						}
					}
					}
					new MutliThread(sdi,sdor,sdinforInsured).start();
				} catch (Exception e) {
					logger.error("支付成功后经代通发送报文发生异常" + e.getMessage(), e);
				}
				List<String> productIdList = new ArrayList<String>();
				// 增加产品购买数量
				productIdList.add(sdi.getProductId());
				PayCallBackAction payCallBackAction = new PayCallBackAction();
				payCallBackAction.setProductIdList(productIdList);
				List<String> countList = new ArrayList<String>();
				countList.add("1");
				payCallBackAction.addProductSalesVolume(countList);
			}
			
			return ajax(new JSONObject(resultMap).toString(), "text/html");
		} catch (Exception e) {
			resultMap.put("Status", "1");
			resultMap.put("Msg", "系统异常,注册会员失败！");
			return ajax(new JSONObject(resultMap).toString(), "text/html");
		}
	}
	
	private boolean checkLimit(String productOutCode, String identityType) {
		// 取得限购份数和产品类型
		String sql = "SELECT LimitCount FROM sdproduct WHERE productid=? AND limitcount IS NOT NULL AND limitcount <> ''";
		int limitCount = new QueryBuilder(sql, productId).executeInt();
		if (limitCount > 0) {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT COUNT(1) FROM SDInformation a, SDInformationInsured b, ");
			sb.append("SDInformationRiskType c WHERE a.InformationSn = b.InformationSn ");
			sb.append("AND b.RecognizeeSn = c.RecognizeeSn AND c.AppStatus ='1' ");
			sb.append("AND a.InformationSn = c.InformationSn AND b.RecognizeeIdentityType = ? ");
			sb.append("AND b.RecognizeeIdentityId = ? AND a.productoutcode = ? ");
			sb.append("AND ((EndDate >=? AND StartDate <= ?) OR (startDate<=? AND endDate>= ?) ");
			sb.append("OR (StartDate >= ? AND EndDate <= ? )) ");
			QueryBuilder qb = new QueryBuilder(sb.toString());
			qb.add(identityType);
			qb.add(identityId);
			qb.add(productOutCode);
			qb.add(startDate);
			qb.add(startDate);
			qb.add(endDate);
			qb.add(endDate);
			qb.add(startDate);
			qb.add(endDate);
			if (limitCount < qb.executeInt()) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 获取订单表信息
	 * @param orderSn
	 * @param paySn
	 * @param initPrem
	 * @return
	 */
	private SDOrder getSDOrder(String orderSn, String paySn, String initPrem) {
		SDOrder sdorder = new SDOrder();
		BigDecimal price = BigDecimal.valueOf(Double.valueOf(initPrem));
		sdorder.setProductTotalPrice(price);
		sdorder.setTotalAmount(price);
		sdorder.setPaySn(paySn);
		sdorder.setDiscountRates("");// 订单折扣费率
		sdorder.setDiscountAmount("");// 订单优惠金额
		sdorder.setChannelsn(channelSn);
		sdorder.setPayPrice("0");
		sdorder.setOrderStatus(SDOrderStatus.paid);
		sdorder.setPayStatus(SDPayStatus.paid);
		sdorder.setPayType("freePay");
		sdorder.setProductNum(1);
		sdorder.setOrderSn(orderSn);
		return sdorder;
	}
	
	/**
	 * 获取订单明细表信息
	 * @param informationSn
	 * @param paramter
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private SDInformation getSDInformation(String informationSn, Map<String, Object> paramter) {
		SDInformation sdInformation = new SDInformation();
		String[] baseInformation = new String[17];
		baseInformation = (String[]) paramter.get("baseInformation");// 产品基础数据
		List<OrderRiskAppFactor> riskAppFactior = (List<OrderRiskAppFactor>) paramter.get("riskAppFactor");
		String productCode = baseInformation[0];
		String initPrem = ((String[])paramter.get("baseInformation"))[6];
		
		sdInformation.setInformationSn(informationSn);
		sdInformation.setProductPrice(initPrem);
		sdInformation.setProductDiscountPrice(initPrem);
		sdInformation.setInsuranceCompany(baseInformation[2]);
		sdInformation.setProductId(productCode);
		sdInformation.setProductName(baseInformation[1]);
		sdInformation.setProductQuantity("1");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date startdate = sdf.parse(startDate);
			Date enddate = sdf.parse(endDate);
			sdInformation.setStartDate(startdate);
			sdInformation.setEndDate(enddate);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		
		for (OrderRiskAppFactor tOrderRiskAppFactor : riskAppFactior) {
			if ("Period".equals(tOrderRiskAppFactor.getFactorType())) {
				String ensure = tOrderRiskAppFactor.getFactorValue().get(0).getFactorValue();
				String ensureDisplay = tOrderRiskAppFactor.getFactorValue().get(0).getFactorDisplayValue();
				sdInformation.setEnsure(ensure);
				String test[] = ensure.split("-");
				if (test != null && test.length > 1) {
					String splitEnsure = test[1];
					sdInformation.setEnsureLimit(splitEnsure.substring(0, splitEnsure.length() - 1));
					sdInformation.setEnsureLimitType(splitEnsure.substring( splitEnsure.length() - 1, splitEnsure.length()));
				} else {
					sdInformation.setEnsureLimit(ensure.substring(0, ensure.length() - 1));
					sdInformation.setEnsureLimitType(ensure.substring(ensure.length() - 1, ensure.length()));
				}
				sdInformation.setEnsureDisplay(ensureDisplay);
				
			} else if ("Plan".equals(tOrderRiskAppFactor.getFactorType())) {
				sdInformation.setPlanCode(tOrderRiskAppFactor.getFactorValue().get(0).getFactorValue());// 产品计划编码
				sdInformation.setPlanName(tOrderRiskAppFactor.getFactorValue().get(0).getFactorDisplayValue());// 产品计划名称
			}
		}
		
		sdInformation.setProductOutCode(baseInformation[4].toString());// 产品外部编码
		sdInformation.setDiscountRates("");
		sdInformation.setProductHtmlFilePath("");
		sdInformation.setPoint("0");
		sdInformation.setPointStatus("0");
		sdInformation.setSupKindCode(baseInformation[12].toString());
		sdInformation.setSupRiskCode(baseInformation[13].toString());
		sdInformation.setRiskType(baseInformation[5].toString());// 内部统计险种中类别
		sdInformation.setSubRiskType(baseInformation[7].toString());// 内部统计险种小类别
		sdInformation.setPrimitiveProductTitle(String.valueOf(baseInformation[16]));// 保险公司原名称
		return sdInformation;
	}
	
	/**
	 * 获取投保人表信息
	 * @param sex
	 * @param birthday
	 * @return
	 */
	private SDInformationAppnt getSDInformationAppnt(String sex, String birthday) {
		SDInformationAppnt sdInformationAppnt = new SDInformationAppnt();
		sdInformationAppnt.setApplicantSn(com.sinosoft.cms.pub.PubFun.GetSDAppntSn());
		sdInformationAppnt.setApplicantIdentityType("0");
		sdInformationAppnt.setApplicantIdentityTypeName("身份证");
		sdInformationAppnt.setApplicantIdentityId(identityId);
		sdInformationAppnt.setApplicantName(name);
		sdInformationAppnt.setApplicantBirthday(birthday);
		int appAge = com.sinosoft.sms.messageinterface.pubfun.PubFun.getAge(birthday);// 获取投保人年龄
		sdInformationAppnt.setApplicantAge(String.valueOf(appAge));// 投保人年龄
		sdInformationAppnt.setApplicantSex(sex);
		sdInformationAppnt.setApplicantSexName("M".equals(sex) ? "男" : "女");
		sdInformationAppnt.setApplicantMobile(mobile);
		sdInformationAppnt.setApplicantMail(email);

		return sdInformationAppnt;
	}
	
	/**
	 * 获取被保人表信息
	 * @param initPrem
	 * @param sex
	 * @param birthday
	 * @return
	 */
	private List<SDInformationInsured> getSDInformationInsured(String initPrem, String sex, String birthday) {
		SDInformationInsured sdInformationInsured = new SDInformationInsured();
		sdInformationInsured.setRecognizeeSn(com.sinosoft.cms.pub.PubFun.GetSDInsuredSn());
		sdInformationInsured.setRecognizeeAppntRelation("00");
		sdInformationInsured.setRecognizeeAppntRelationName("本人");
		sdInformationInsured.setRecognizeeName(name);
		sdInformationInsured.setRecognizeeIdentityType("0");
		sdInformationInsured.setRecognizeeIdentityTypeName("身份证");
		sdInformationInsured.setRecognizeeIdentityId(identityId);
		sdInformationInsured.setRecognizeeSex(sex);
		sdInformationInsured.setRecognizeeSexName("M".equals(sex) ? "男" : "女");
		sdInformationInsured.setRecognizeeBirthday(birthday);
		int tInsuredAge = com.sinosoft.sms.messageinterface.pubfun.PubFun.getAge(birthday);
		sdInformationInsured.setRecognizeeAge(String.valueOf(tInsuredAge));
		sdInformationInsured.setRecognizeeMobile(mobile);
		sdInformationInsured.setRecognizeeMail(email);
		sdInformationInsured.setIsSelf("Y");
		sdInformationInsured.setRecognizeePrem(initPrem);
		sdInformationInsured.setDiscountPrice(initPrem);
		sdInformationInsured.setRecognizeeTotalPrem(initPrem);
		sdInformationInsured.setRecognizeeOperate("4");
		
		List<SDInformationInsured> Insuredlist = new ArrayList<SDInformationInsured>();
		Insuredlist.add(sdInformationInsured);
		return Insuredlist;
	}
	
	/**
	 * 获取订单项1信息
	 * @param sdorder
	 * @return
	 */
	private SDOrderItem getSDOrderItem(SDOrder sdorder) {
		SDOrderItem tSDOrderItem = new SDOrderItem();
		tSDOrderItem.setOrderitemSn(com.sinosoft.cms.pub.PubFun.GetItemNo());
		tSDOrderItem.setOrderSn(sdorder.getOrderSn());
		tSDOrderItem.setSdorder(sdorder);
		tSDOrderItem.setOrderPoint("0");
		tSDOrderItem.setPointStatus("0");
		return tSDOrderItem;
	}
	
	/**
	 * 获取投保要素信息
	 * @param order
	 * @param infor
	 * @param ins
	 * @param riskAppFactior
	 * @return
	 */
	private Set<SDInformationInsuredElements> getInfomationInsuredElements(SDOrder order, SDInformation infor,
			SDInformationInsured ins, List<OrderRiskAppFactor> riskAppFactior) {

		Set<SDInformationInsuredElements> tInformationInsuredElementsSet = new HashSet<SDInformationInsuredElements>();
		if (riskAppFactior != null) {
			for (int i = 0; i < riskAppFactior.size(); i++) {
				String appFactiorType = riskAppFactior.get(i).getFactorType();
				SDInformationInsuredElements informationInsuredElements = new SDInformationInsuredElements();
				if (!"TextAge".equals(appFactiorType) && riskAppFactior != null && riskAppFactior.get(i) != null) {
					informationInsuredElements.setElementsType(appFactiorType);
					informationInsuredElements.setElementsSn(com.sinosoft.cms.pub.PubFun.GetSDElementSn());
					informationInsuredElements.setOrderSn(order.getOrderSn());
					informationInsuredElements.setRecognizeeSn(ins.getRecognizeeSn());
					informationInsuredElements.setSdinformationInsured(ins);
					informationInsuredElements.setInformationSn(infor.getInformationSn());
					informationInsuredElements.setElementsSn(riskAppFactior.get(i).getAppFactorCode());
					informationInsuredElements.setElementsValue(riskAppFactior.get(i).getFactorValue().get(0).getFactorValue());
					tInformationInsuredElementsSet.add(informationInsuredElements);
				}
			}
		}
		return tInformationInsuredElementsSet;
	}
	
	/**
	 * 获取交易表信息
	 * @param orderSn
	 * @param paySn
	 * @param initPrem
	 * @return
	 */
	private TradeInformation getTradeInformation(String orderSn, String paySn, String initPrem) {
		TradeInformation tradeInformation = new TradeInformation();
		tradeInformation.setOrdID(orderSn);
		tradeInformation.setTradeSeriNO(paySn);
		tradeInformation.setPayType("freePay");
		tradeInformation.setPayStatus("1");
		tradeInformation.setOrdAmt(initPrem);
		tradeInformation.setTradeCheckSeriNo(paySn);
		tradeInformation.setTradeResult("0");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = sdf.format(new Date());
		tradeInformation.setSendDate(now);
		tradeInformation.setReceiveDate(now);
		tradeInformation.setModifyDate(new Date());
		return tradeInformation;
	}
	
	private SDInformationRiskType dealSDInformationRiskType(SDInformationRiskType sdInformationRiskType, String initPrem) {
		// 设置保单活动优惠金额
		sdInformationRiskType.setCouponValue("");
		sdInformationRiskType.setIntegralValue("");
		sdInformationRiskType.setActivityValue("");
		sdInformationRiskType.setPayPrice(initPrem);
		
		return sdInformationRiskType;
	}
	
	/**
	 * 处理交易信息
	 * @param orderSn
	 * @param paySn
	 * @param initPrem
	 * @return
	 */
	private boolean dealTradeInfo(String orderSn, String paySn, String initPrem) {
		Transaction trans = new Transaction();
		
		try {
			QueryBuilder inserttradesummaryinfoQB = new QueryBuilder("INSERT INTO tradesummaryinfo VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			inserttradesummaryinfoQB.add(NoUtil.getMaxNo("TradeSummaryID", 11));
			inserttradesummaryinfoQB.add(paySn);
			inserttradesummaryinfoQB.add(paySn);
			inserttradesummaryinfoQB.add("0");
			inserttradesummaryinfoQB.add(orderSn);
			inserttradesummaryinfoQB.add("");
			inserttradesummaryinfoQB.add(initPrem);
			inserttradesummaryinfoQB.add("");
			inserttradesummaryinfoQB.add("");
			inserttradesummaryinfoQB.add("freepay");
			inserttradesummaryinfoQB.add("赠险支付");
			inserttradesummaryinfoQB.add(initPrem);
			inserttradesummaryinfoQB.add("0");
			inserttradesummaryinfoQB.add(new Date());
			inserttradesummaryinfoQB.add(new Date());
			trans.add(inserttradesummaryinfoQB);
			
			if (!trans.commit()) {
				logger.error("感恩 八周年赠险处理交易信息失败!");
				return false;
			}
		} catch (Exception e) {
			logger.error("感恩 八周年赠险处理交易信息失败!" + e.getMessage(), e);
		}
		return true;
	}
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdentityId() {
		return identityId;
	}
	public void setIdentityId(String identityId) {
		this.identityId = identityId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
