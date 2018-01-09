/**
 * 
 */
package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.entity.OrderDutyFactor;
import cn.com.sinosoft.entity.OrderRiskAppFactor;
import cn.com.sinosoft.entity.SDInformation;
import cn.com.sinosoft.entity.SDInformationAppnt;
import cn.com.sinosoft.entity.SDInformationDuty;
import cn.com.sinosoft.entity.SDInformationInsured;
import cn.com.sinosoft.entity.SDInformationInsuredElements;
import cn.com.sinosoft.entity.SDInformationRiskType;
import cn.com.sinosoft.entity.SDInsuredHealth;
import cn.com.sinosoft.entity.SDOrder;
import cn.com.sinosoft.entity.SDOrder.SDOrderStatus;
import cn.com.sinosoft.entity.SDOrder.SDPayStatus;
import cn.com.sinosoft.entity.SDOrderItem;
import cn.com.sinosoft.entity.SDOrderItemOth;
import cn.com.sinosoft.entity.TradeInformation;
import cn.com.sinosoft.entity.TradeSummaryInfo;
import cn.com.sinosoft.service.DealDataService;
import cn.com.sinosoft.service.DictionaryService;
import cn.com.sinosoft.service.HealthyInfoService;
import cn.com.sinosoft.service.OrderConfigNewService;
import cn.com.sinosoft.service.SDInformationInsuredService;
import cn.com.sinosoft.service.SDInformationService;
import cn.com.sinosoft.service.SDInsuredHealthService;
import cn.com.sinosoft.service.SDOrderService;
import cn.com.sinosoft.util.WapShoppingUtil;
import com.sinosoft.cms.dataservice.OrderImport;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.User;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.LogUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.jdt.InsureTransferNew;
import com.sinosoft.platform.UserLog;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FEMDutyAmntPremList;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FEMRiskFactorList;
import com.wangjin.optimize.register.AutoRegister;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import java.io.FileInputStream;
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
import java.util.regex.Pattern;

/**
 * @author wangcaiyun
 *
 */
@ParentPackage("shop")
public class OrderAutoInsureAction extends BaseShopAction {

	@Resource
	private DictionaryService dictionaryService;
	@Resource
	private SDOrderService sdorderService;
	@Resource
	private DealDataService mDealDataService;
	@Resource
	private OrderConfigNewService orderConfigNewService;
	@Resource
	private HealthyInfoService healthyInfoService;
	@Resource
	private SDInsuredHealthService sdinsuredHealthService;
	@Resource
	private SDInformationService sdInformationService;
	@Resource
	private SDInformationInsuredService sdInformationInsuredService;

	SimpleDateFormat sdf_1 = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdf_2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// 产品投保要素列表
	private List<OrderDutyFactor> dutyFactor = new ArrayList<OrderDutyFactor>();
	// 责任投保要素列表
	private List<OrderRiskAppFactor> riskAppFactior = new ArrayList<OrderRiskAppFactor>();
	private String importInsureFile;
	private String productId;
	private String comCode;
	private String startDate;
	private String endDate;
	private String protectionPeriodTy;// 保障期限类型
	private String protectionPeriodLast;// 保障期限后段数值
	private String channelSn;
	private String activitySn;
	private Date svaliDate;
	private Date evaliDate;
	private String healthInfo;
	private String applicantName;
	private String applicantEnName;
	private String applicantIDtype;
	private String applicantID;
	private String applicantSex;
	private String applicantBirthday;
	private String applicantMobile;
	private String applicantMail;
	private String applicantAge;
	private boolean childFlag = false;
	private String periodValue;
	private String planValue;
	private String occupValue;
	private String textAgeValue;
	private String appTypeValue;
	private String feeYearValue;
	private String gradeValue;
	private String appLevelValue;
	private String mulPeopleValue;
	private String activityDesc;
    private String occupationLevelOne;
    private String occupationLevelTwo;
    private String occupationLevelThree;
    private String sendEmailFlag;
	Map<String, Object> resultMap = new HashMap<String, Object>();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1474949682051964255L;
	
	private List<OrderRiskAppFactor> getAppFactor(List<OrderRiskAppFactor> appFacList) {
		List<OrderRiskAppFactor> result = new ArrayList<OrderRiskAppFactor>();
		for (OrderRiskAppFactor app : appFacList) {
			OrderRiskAppFactor orderRiskAppFactor = new OrderRiskAppFactor();
			orderRiskAppFactor.setAppFactorCode(app.getAppFactorCode());
			orderRiskAppFactor.setDataType(app.getDataType());
			orderRiskAppFactor.setFactorType(app.getFactorType());
			orderRiskAppFactor.setFactorTypeName(app.getFactorTypeName());
			orderRiskAppFactor.setIsPremCalFacotor(app.getIsPremCalFacotor());
			orderRiskAppFactor.setProductCode(app.getProductCode());
			orderRiskAppFactor.setFactorValue(getFactorList(app.getFactorValue()));
			result.add(orderRiskAppFactor);
		}
		
		return result;
	}
	
	private List<FEMRiskFactorList> getFactorList(List<FEMRiskFactorList> factorList) {
		List<FEMRiskFactorList> result = new ArrayList<FEMRiskFactorList>();
		for (FEMRiskFactorList factor : factorList) {
			FEMRiskFactorList femRiskFactorList = new FEMRiskFactorList();
			femRiskFactorList.setAppFactorCode(factor.getAppFactorCode());
			femRiskFactorList.setDefaultValue(factor.getDefaultValue());
			femRiskFactorList.setDutySnAmnt(factor.getDutySnAmnt());
			femRiskFactorList.setFactorDisplayValue(factor.getFactorDisplayValue());
			femRiskFactorList.setFactorERiskDesc(factor.getFactorERiskDesc());
			femRiskFactorList.setFactorListID(factor.getFactorListID());
			femRiskFactorList.setFactorType(factor.getFactorType());
			femRiskFactorList.setFactorValue(factor.getFactorValue());
			femRiskFactorList.setFactorValueFlag(factor.getFactorValueFlag());
			femRiskFactorList.setFactorValueOrder(factor.getFactorValueOrder());
			femRiskFactorList.setFEMRiskBrightSpotList(factor.getFEMRiskBrightSpotList());
			femRiskFactorList.setIsDisplay(factor.getIsDisplay());
			femRiskFactorList.setIsPremCalFacotor(factor.getIsPremCalFacotor());
			femRiskFactorList.setMakeDate(factor.getMakeDate());
			femRiskFactorList.setMakeTime(factor.getMakeTime());
			femRiskFactorList.setModifyDate(factor.getModifyDate());
			femRiskFactorList.setModifyTime(factor.getModifyTime());
			femRiskFactorList.setOperator(factor.getOperator());
			femRiskFactorList.setRiskCode(factor.getRiskCode());
			result.add(femRiskFactorList);
		}
		return result;
	}
	
	/**
	 * 保存上传文件
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String upLoadSave() {
		resultMap.put("Status", "1");
		getValiDate();
		Map<String, Object> cMap = new HashMap<String, Object>();
		Map<String, Object> paramter = new HashMap<String, Object>();
		String[] BaseInformation = new String[16];

		try {
			paramter = sdorderService.getProductInformation(productId, "N", "");
		} catch (Exception e) {
			this.addError("与产品中心交互失败！");
			return "Fail";
		}
		// 产品基础数据
		BaseInformation = (String[]) paramter.get("baseInformation");
		riskAppFactior = getAppFactor((List<OrderRiskAppFactor>) paramter
				.get("riskAppFactor"));
		dutyFactor = (List<OrderDutyFactor>) paramter.get("dutyFactor");
		cMap.put("baseInformations", BaseInformation);
		cMap.put("riskAppFactior", paramter.get("riskAppFactor"));
		cMap.put("dutyFactor", (List<OrderDutyFactor>) paramter.get("dutyFactor"));
		cMap.put("productId", productId);
		SDInformation sdInformation = new SDInformation();
		sdInformation.setStartDate(svaliDate);
		sdInformation.setEndDate(evaliDate);
		cMap.put("sdinformation", sdInformation);
		// 投保要素信息
		Map<String, String> insureJson = new HashMap<String, String>();
		if (StringUtil.isNotEmpty(planValue)) {
			insureJson.put("Plan", planValue);
		}
		if (StringUtil.isNotEmpty(appTypeValue)) {
			insureJson.put("AppType", appTypeValue);
		}
		if (StringUtil.isNotEmpty(feeYearValue)) {
			insureJson.put("FeeYear", feeYearValue);
		}
		if (StringUtil.isNotEmpty(periodValue)) {
			insureJson.put("Period", periodValue);
		}
		if (StringUtil.isNotEmpty(textAgeValue)) {
			insureJson.put("TextAge", textAgeValue);
		}
		cMap.put("insureJson", JSONObject.fromObject(insureJson).toString());
		cMap.put("dutyJson", JSONObject.fromObject(getDutyInfo()).toString());
		cMap.put("productId", productId);
		cMap.put("effdate", startDate);
		cMap.put("channelsn", channelSn);
		cMap.put("complicatedFlag", "N");
		cMap.put("textAge", textAgeValue);
        cMap.put("recognizeeOccupation1", occupationLevelOne);
        cMap.put("recognizeeOccupation2", occupationLevelTwo);
        cMap.put("recognizeeOccupation3", occupationLevelThree);
		// 解析Excel内容
		List<SDInformationInsured> insuredList = resolvingExcel(importInsureFile, cMap);
        if (insuredList != null && insuredList.size() > 0) {
            if (makeOrders(paramter, insuredList)) {
                resultMap.put("Status", "0");
                resultMap.put("Msg", "上传成功！");
            }
        } else if (insuredList != null && insuredList.size() == 0) {
            this.addError("无数据，请填写后上传！");
        }
		return ajax(JSONObject.fromObject(resultMap).toString(), "text/html");
	}

	/**
	 * 取得承保起止期
	 */
	private void getValiDate() {
		try {
			if (StringUtil.isNotEmpty(this.getStartDate())
					&& this.getStartDate().trim().length() == 10) {
				svaliDate = sdf_2.parse(this.getStartDate() + " 00:00:00");
			}

		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		// endDate需要特殊处理
		OrderImport OrderImport = new OrderImport();
		evaliDate = OrderImport.getEndDate(this.getEndDate());
	}
	private boolean makeOrders(Map<String, Object> productInfo, List<SDInformationInsured> insuredList) {
		// 通过配置项获取活动名称，设置活动流水号 
		String activityName = Config.getValue("Brand01ActivityName");
		String actSql = "SELECT activitySn FROM sdCouponActivityInfo WHERE status <> 1 AND  title = ?";
		activitySn = new QueryBuilder(actSql, activityName).executeString();
		if (StringUtil.isEmpty(activitySn)) {
			logger.error("------赠送保险活动未设置！");
			this.addError("赠送保险活动未设置！");
			return false;
		}
		// 有未成年人，需校验页面上录入的投保人信息
		if (childFlag) {
			if (!checkAppntInfo()) {
				return false;
			}
		}

		List<SDInsuredHealth> healthList = getHealthyInfo();
		LinkedHashMap<Object, String> mLMap = new LinkedHashMap<Object, String>();
		Map<String, String[]> orderMap = new HashMap<String, String[]>();
		for (SDInformationInsured insured : insuredList) {
			saveOrder(productInfo, insured, mLMap, healthList, orderMap);
		}

        try {
			if (!mDealDataService.saveAll(mLMap)) {
				this.addError("提交数据失败！");
				return false;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			this.addError("提交数据系统异常！");
			return false;
		}
		// 承保
		MutliThread mutliThread = new MutliThread(orderMap, User.getUserName(), getRequest().getRemoteAddr(), sendEmailFlag);
		Thread thread = new Thread(mutliThread);
		thread.start();

		return true;
	}

	private void insure(Map<String, String[]> orderMap, String userName, String remoteAddr, String sendEmailFlag) {
		// 使用手机号注册会员，若未注册过，直接注册并返回初始密码为:123456；若注册过，则不注册并返回初始密码为：您已设置过
		try {
			String actSql = "SELECT GROUP_CONCAT(s.orderSn)  FROM sdorders s ,SDInformation AS t2, SDInformationInsured AS t1 " +
					"WHERE  t2.productId =? AND s.orderstatus='7' AND s.orderSn=t2.orderSn AND t2.startDate=? AND t2.endDate =? " +
					"AND t1.orderSn = t2.orderSn GROUP BY t1.recognizeeIdentityId " +
					"HAVING COUNT(1) > 1";

			QueryBuilder queryBuilder = new QueryBuilder(actSql);
			queryBuilder.add(productId);
			queryBuilder.add(svaliDate);
			queryBuilder.add(evaliDate);
			queryBuilder.set(0, productId);
			queryBuilder.set(1, svaliDate);
			queryBuilder.set(2, evaliDate);

			DataTable dataTable = queryBuilder.executeDataTable();
			String strOrderSns = "";
			for (int i = 0; i < dataTable.getRowCount(); i ++) {
				strOrderSns += ("," + dataTable.getString(i, 0));
			}
			List<String> orderSnList = new ArrayList<String>();
			if (!StringUtil.isEmpty(strOrderSns)) {
				for (String orderSn : strOrderSns.split(",")) {
					orderSnList.add(orderSn);
				}
			}

			List<String> repeatOrderSnList = new ArrayList<String>();
			AutoRegister autoRegister = new AutoRegister();
			String email;
			String mobile;
			int count = 0;
			Map<String, String> userInfo = null;
			for (String orderSn : orderMap.keySet()) {
				// 判断是否有重复的被投保人
				if (orderSnList.contains(orderSn)) {
					repeatOrderSnList.add(orderSn);
					continue;
				}

				email = orderMap.get(orderSn)[0];
				mobile = orderMap.get(orderSn)[1];
				// 承保
				SDOrder sdor = sdorderService.getOrderByOrderSn(orderSn);
				String memberId = autoRegister.getMobileMemberId(mobile);

				if (StringUtil.isNotEmpty(memberId)) {
					userInfo = null;
					Transaction trans = new Transaction();
					trans.add(new QueryBuilder(
							"update sdorders set memberid=? where ordersn=?",
							memberId, orderSn));
					trans.add(new QueryBuilder(
							"update tradeInformation set merId=? where ordID=?",
							memberId, orderSn));
					if (!trans.commit()) {
						logger.error("orderSn：{}归属MemberID：{}没有成功！", orderSn, memberId);
					}

				} else {
					userInfo = autoRegister.registForBrand("", mobile, sdor);
				}
				try {
					if (StringUtil.isNotEmpty(userName)) {
						UserLog.log("Order", "OrderAutoInsure", orderSn, remoteAddr, userName);
					}
					
				} catch(Exception e) {
					logger.error("OrderAutoInsureAction：记录用户操作失败！userName:"+userName+ e.getMessage(), e);
				}

				SDInformation sdi = sdInformationService.getByOrderSn(orderSn);
				List<SDInformationInsured> sdinforInsured =
						sdInformationInsuredService.getListByOrderSn(orderSn);
				if (sdinforInsured != null && !sdinforInsured.isEmpty()) {
					try {
						// 支付成功发送短信
						if (!StringUtil.isEmpty(mobile)) {
							String memberMsg = "！";
							if (userInfo != null && !userInfo.isEmpty()) {
								memberMsg = "！首次登录手机号"+userInfo.get("username")+"登录，密码为"+userInfo.get("password")+"！";
							}
							ActionUtil.sendSms("wj00313", mobile, activityDesc + ";" + memberMsg);
						}
					} catch (Exception e) {
						logger.error("OrderAutoInsureAction：支付成功发送短信失败！userName:"+userName + e.getMessage(), e);
					}
					List<String> UWUNSucess = sdorderService.checkUnderwriting(sdi.getInsuranceCompany(), orderSn, null);
					// 调用经代通发送报文
					sendInsured(sdi, sdor, sdinforInsured, UWUNSucess, sendEmailFlag);
				}
				count++;
			}

			if (repeatOrderSnList.size() > 0) {
			    StringBuilder stringBuilder = new StringBuilder();
			    for (String orderSn : repeatOrderSnList) {
                    stringBuilder.append("'");
                    stringBuilder.append(orderSn);
                    stringBuilder.append("',");
                }
                String strRepeatOrderSns = stringBuilder.toString();
                Transaction trans = new Transaction();
                trans.add(new QueryBuilder(
                        "update sdorders set orderstatus='9' where ordersn in ("
                                + strRepeatOrderSns.substring(0,strRepeatOrderSns.length() - 1)+")"
                        ));
                trans.commit();
            }


			// 增加产品购买数量
			List<String> productIdList = new ArrayList<String>();
			productIdList.add(productId);
			PayCallBackAction payCallBackAction = new PayCallBackAction();
			payCallBackAction.setProductIdList(productIdList);
			List<String> countList = new ArrayList<String>();
			countList.add(count+"");
			payCallBackAction.addProductSalesVolume(countList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 处理交易信息
	 * 
	 * @param orderSn
	 * @param paySn
	 * @param initPrem
	 * @return
	 */
	private TradeSummaryInfo dealTradeInfo(String orderSn, String paySn,
			String initPrem) {
		TradeSummaryInfo TradeSummaryInfo = new TradeSummaryInfo();
		TradeSummaryInfo.setPaySn(paySn);
		TradeSummaryInfo.setTradeSn(paySn);
		TradeSummaryInfo.setOrderSns(orderSn);
		TradeSummaryInfo.setTotalAmount(initPrem);
		TradeSummaryInfo.setTradeAmount("0");
		TradeSummaryInfo.setActivitySumAmount(initPrem);
		TradeSummaryInfo.setTradeResult("0");
		TradeSummaryInfo.setPayType("zerozf");
		TradeSummaryInfo.setPayTypeName("零元支付");
		return TradeSummaryInfo;
	}

	private List<SDInsuredHealth> getHealthyInfo() {
		List<SDInsuredHealth> healthList = new ArrayList<SDInsuredHealth>();
		if (StringUtil.isNotEmpty(healthInfo)) {
			String[] list = healthInfo.split("|");
			int len = list.length;
			Map<String, String> map = new HashMap<String, String>();
			String[] healthy;
			for (int i = 0; i < len; i++) {
				healthy = list[i].split("-");
				map.put(healthy[0], healthy[1]);
			}
			healthList = sdinsuredHealthService
					.createShowInformation(healthyInfoService
							.findByComAndProduct(productId, comCode));
			if (healthList != null && healthList.size() > 0) {
				int size = healthList.size();
				for (int i = 0; i < size; i++) {
					healthList.get(i).setSelectFlag(
							map.get(healthList.get(i).getHealthyInfoId()));
				}
			}
		}

		return healthList;
	}

	protected void addError(String errorMessage) {
		resultMap.put("Status", "1");
		resultMap.put("Msg", errorMessage);
	}
	
	private boolean checkAppntInfo() {
		if (StringUtil.isEmpty(applicantName)) {
			this.addError("请填写投保人姓名！");
			return false;
		}

		if (StringUtil.isEmpty(applicantIDtype)) {
			this.addError("请选择投保人证件类型！");
			return false;
		}
		if (StringUtil.isEmpty(applicantID)) {
			this.addError("请填写投保人证件号！");
			return false;
		}
		if (StringUtil.isEmpty(applicantSex)) {
			this.addError("请选择投保人性别！");
			return false;
		}
		if (StringUtil.isEmpty(applicantBirthday)) {
			this.addError("请填写投保人出生日期！");
			return false;
		}
		int age = com.sinosoft.sms.messageinterface.pubfun.PubFun
				.getAge(applicantBirthday);
		if (age < 18) {
			this.addError("投保人必须满18周岁！");
			return false;
		}
		applicantAge = String.valueOf(age);
		if (StringUtil.isEmpty(applicantMobile)) {
			this.addError("请填写投保人手机号！");
			return false;
		}
		if (StringUtil.isEmpty(applicantMail)) {
			this.addError("请填写投保人邮箱！");
			return false;
		}

		return true;
	}

	private void saveOrder(Map<String, Object> paramter,
			SDInformationInsured insured, LinkedHashMap<Object, String> mLMap,
			List<SDInsuredHealth> healthList, Map<String, String[]> orderMap) {
		String orderSn = com.sinosoft.cms.pub.PubFun.GetOrderSn();
		String informationSn = com.sinosoft.cms.pub.PubFun.GetSDInformationSn();
		String paySn = com.sinosoft.cms.pub.PubFun.GetPaySn();
		String initPrem = insured.getRecognizeePrem();
		// 获取订单表信息 sdorders
		SDOrder sdorder = getSDOrder(orderSn, paySn, initPrem);
		// 获取订单明细表信息 sdinformation
		SDInformation sdInformation = getSDInformation(informationSn, paramter, initPrem);
		sdInformation.setOrderSn(orderSn);
		sdInformation.setSdorder(sdorder);
		Set<SDInformation> tSDInformationSet = new HashSet<SDInformation>();
		tSDInformationSet.add(sdInformation);
		sdorder.setSdinformationSet(tSDInformationSet);

		// 获取投保人表信息 sdInformationAppnt
		SDInformationAppnt sdInformationAppnt = getSDInformationAppnt(insured);
		sdInformationAppnt.setInformationSn(informationSn);
		String[] emai = { sdInformationAppnt.getApplicantMail(),
				sdInformationAppnt.getApplicantMobile() };
		orderMap.put(orderSn, emai);
		// 保存投保人时将information的投保人信息进行更新
		Set<SDInformationAppnt> appSet = new HashSet<SDInformationAppnt>();
		appSet.add(sdInformationAppnt);
		sdInformation.setSdinformationappntSet(appSet);
		sdInformationAppnt.setSdinformaiton(sdInformation);
		// 获取订单项1
		SDOrderItem sdorderitem = getSDOrderItem(sdorder);
		insured.setInformationSn(informationSn);
		List<SDOrderItemOth> sdorderitemothList = new ArrayList<SDOrderItemOth>();
		List<SDInformationRiskType> mSDInformationRiskTypeList = new ArrayList<SDInformationRiskType>();
		// 获取责任信息
		WapShoppingUtil tWapShoppingUtil = new WapShoppingUtil();
		List<SDInformationDuty> mdutylist = tWapShoppingUtil
				.getInformationDuty(sdorder, sdInformation, dutyFactor);

		insured.setOrderSn(orderSn);
		insured.setInsuredSn(orderSn + "_1");
		insured.setRecognizeeKey(orderSn + "_1");
		insured.setInformationSn(informationSn);
		insured.setSdinformation(sdInformation);
		// 获取订单项2
		SDOrderItemOth sdorderitemoth = new SDOrderItemOth();
		sdorderitemoth.setOrderSn(orderSn);
		sdorderitemoth.setRecognizeeSn(insured.getRecognizeeSn());
		sdorderitemoth.setOrderitemSn(com.sinosoft.cms.pub.PubFun
				.GetItemOthNo());
		sdorderitemoth.setSdinformationinsured(insured);
		sdorderitemothList.add(sdorderitemoth);

		// 获取保单信息
		SDInformationRiskType sdInformationRiskType = tWapShoppingUtil
				.getSDInformationRiskType(sdorder, sdInformation,
						sdInformationAppnt, insured);
		// wap站逻辑意外的处理
		sdInformationRiskType = dealSDInformationRiskType(sdInformationRiskType);

		insured.setSdinformaitonrisktype(sdInformationRiskType);
		mSDInformationRiskTypeList.add(sdInformationRiskType);
		// 获取投保要素信息
		Set<SDInformationInsuredElements> sdinforinselementsSet = getInfomationInsuredElements(
				sdorder, sdInformation, insured, riskAppFactior);
		List<SDInformationInsuredElements> melementslist =
				new ArrayList<SDInformationInsuredElements>(sdinforinselementsSet);
		insured.setSdinforinselementsSet(sdinforinselementsSet);

		// 获取交易表信息
		TradeInformation tTradeInformation = getTradeInformation(orderSn,
				paySn, sdInformationRiskType.getTimePrem());
		List<TradeInformation> tradeList = new ArrayList<TradeInformation>();
		tradeList.add(tTradeInformation);
		// 保存订单表
		List<SDOrder> orderList = new ArrayList<SDOrder>();
		orderList.add(sdorder);

		mLMap.put(orderList, "insert");
		// 保存订单详细表
		mLMap.put(sdInformation, "insert");
		// 保存投保人信息
		mLMap.put(sdInformationAppnt, "insert");
		// 保存被保人信息
		mLMap.put(insured, "insert");
		// 保存保单信息
		mLMap.put(mSDInformationRiskTypeList, "insert");
		// 保存订单项1
		mLMap.put(sdorderitem, "insert");
		// 保存订单项2
		mLMap.put(sdorderitemothList, "insert");
		// 保存投保元素
		mLMap.put(melementslist, "insert");
		// 健康告知
		if (healthList.size() > 0) {
			mLMap.put(dealHealthInfo(healthList, sdInformation, insured),
					"insert");
		}
		// 保存责任信息
		mLMap.put(mdutylist, "insert");
		// 保存交易表
		mLMap.put(tradeList, "insert");

		mLMap.put(dealTradeInfo(orderSn, paySn, initPrem), "insert");

	}

	private Map<String, String> getDutyInfo() {
		// 责任信息
		Map<String, String> dutyJson = new HashMap<String, String>();
		if (dutyFactor != null) {
			for (int i = 0; i < dutyFactor.size(); i++) {
				String cPlanCode = planValue;
				String factorValueTemp = "";
				for (FEMDutyAmntPremList fEMDutyAmntPremList : dutyFactor
						.get(i).getFdAmntPremList()) {
					if (cPlanCode != null && !"".equals(cPlanCode)) {
						if (cPlanCode.equals(fEMDutyAmntPremList
								.getAppFactorValue())) {
							factorValueTemp = fEMDutyAmntPremList.getBackUp1();
						}
					} else {
						factorValueTemp = fEMDutyAmntPremList.getBackUp1();
					}
				}
				if ((StringUtil.isEmpty(factorValueTemp))) {
					factorValueTemp = "nvalue";
				}
				dutyJson.put(dutyFactor.get(i).getDutyCode(), factorValueTemp);
			}
		}
		return dutyJson;
	}
			
	private List<SDInsuredHealth> dealHealthInfo(
			List<SDInsuredHealth> healthList, SDInformation infor,
			SDInformationInsured ins) {
		List<SDInsuredHealth> newSDInsuredHealthList = new ArrayList<SDInsuredHealth>();
		CpsAgainBuyOrderInfoDeal CpsAgainBuyOrderInfoDeal = new CpsAgainBuyOrderInfoDeal();
		for (SDInsuredHealth in : healthList) {
			in.setSdinformationinsured(ins);
			in.setOrderSn(infor.getOrderSn());
			in.setInformationSn(infor.getInformationSn());
			in.setRecognizeeSn(ins.getRecognizeeSn());

			SDInsuredHealth newHealth = CpsAgainBuyOrderInfoDeal
					.cloneSDInsuredHealth(in);

			newSDInsuredHealthList.add(newHealth);
		}
		return newSDInsuredHealthList;
	}

	private SDInformationRiskType dealSDInformationRiskType(
			SDInformationRiskType sdInformationRiskType) {
		// 设置保单活动优惠金额
		sdInformationRiskType.setCouponValue("");
		sdInformationRiskType.setIntegralValue("");
		sdInformationRiskType.setActivityValue(sdInformationRiskType
				.getTimePrem());
		sdInformationRiskType.setPayPrice("0");

		return sdInformationRiskType;
	}

	/**
	 * 获取交易表信息
	 * 
	 * @param orderSn
	 * @param paySn
	 * @param initPrem
	 * @return
	 */
	private TradeInformation getTradeInformation(String orderSn, String paySn,
			String initPrem) {
		TradeInformation tradeInformation = new TradeInformation();
		tradeInformation.setOrdID(orderSn);
		tradeInformation.setTradeSeriNO(paySn);
		tradeInformation.setPayType("zerozf");
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

	/**
	 * 获取投保要素信息
	 * 
	 * @param order
	 * @param infor
	 * @param ins
	 * @param riskAppFactior
	 * @return
	 */
	private Set<SDInformationInsuredElements> getInfomationInsuredElements(
			SDOrder order, SDInformation infor, SDInformationInsured ins,
			List<OrderRiskAppFactor> riskAppFactior) {

		Set<SDInformationInsuredElements> tInformationInsuredElementsSet = new HashSet<SDInformationInsuredElements>();
		if (riskAppFactior != null) {
			for (int i = 0; i < riskAppFactior.size(); i++) {
				String appFactiorType = riskAppFactior.get(i).getFactorType();
				SDInformationInsuredElements informationInsuredElements = new SDInformationInsuredElements();
				if (!"TextAge".equals(appFactiorType) && riskAppFactior != null
						&& riskAppFactior.get(i) != null) {
					informationInsuredElements.setElementsType(appFactiorType);
					informationInsuredElements
							.setElementsSn(com.sinosoft.cms.pub.PubFun
									.GetSDElementSn());
					informationInsuredElements.setOrderSn(order.getOrderSn());
					informationInsuredElements.setRecognizeeSn(ins
							.getRecognizeeSn());
					informationInsuredElements.setSdinformationInsured(ins);
					informationInsuredElements.setInformationSn(infor
							.getInformationSn());
					informationInsuredElements.setElementsSn(riskAppFactior
							.get(i).getAppFactorCode());
					informationInsuredElements.setElementsValue(riskAppFactior
							.get(i).getFactorValue().get(0).getFactorValue());
					tInformationInsuredElementsSet
							.add(informationInsuredElements);
				}
			}
		}
		return tInformationInsuredElementsSet;
	}
	/**
	 * 获取投保人表信息
	 * 
	 * @param insured
	 * @return
	 */
	private SDInformationAppnt getSDInformationAppnt(
			SDInformationInsured insured) {
		SDInformationAppnt sdInformationAppnt = new SDInformationAppnt();
		sdInformationAppnt.setApplicantSn(com.sinosoft.cms.pub.PubFun
				.GetSDAppntSn());
		String comCode = productId.substring(0, 4);
		if (Integer.valueOf(insured.getRecognizeeAge()) < 18) {

			sdInformationAppnt.setApplicantName(applicantName);
			sdInformationAppnt.setApplicantEnName(applicantEnName);
			sdInformationAppnt.setApplicantIdentityType(applicantIDtype);
			sdInformationAppnt.setApplicantIdentityTypeName(dictionaryService.getNameByCodeTypePro(productId, comCode, "certificate",
							applicantIDtype));
			sdInformationAppnt.setApplicantIdentityId(applicantID);
			sdInformationAppnt.setApplicantSex(applicantSex);
			sdInformationAppnt.setApplicantSexName(dictionaryService.getNameByCodeTypePro(productId, productId.substring(0, 4),
							"Sex", applicantSex));
			sdInformationAppnt.setApplicantBirthday(applicantBirthday);
			sdInformationAppnt.setApplicantAge(applicantAge);
			sdInformationAppnt.setApplicantMobile(applicantMobile);
			sdInformationAppnt.setApplicantMail(applicantMail);
			insured.setRecognizeeAppntRelation(dictionaryService
					.getCodeValueByCodeName(comCode, "Relationship", "其他"));
			insured.setRecognizeeAppntRelationName("其他");
			insured.setRecognizeeOperate("2");
			insured.setMulInsuredFlag("rid_orther");

		} else {
			sdInformationAppnt.setApplicantName(insured.getRecognizeeName());
			sdInformationAppnt
					.setApplicantEnName(insured.getRecognizeeEnName());
			sdInformationAppnt.setApplicantIdentityType(insured
					.getRecognizeeIdentityType());
			sdInformationAppnt.setApplicantIdentityTypeName(insured
					.getRecognizeeIdentityTypeName());
			sdInformationAppnt.setApplicantIdentityId(insured
					.getRecognizeeIdentityId());
			sdInformationAppnt.setApplicantSex(insured.getRecognizeeSex());
			sdInformationAppnt.setApplicantSexName(insured
					.getRecognizeeSexName());
			sdInformationAppnt.setApplicantBirthday(insured
					.getRecognizeeBirthday());
			sdInformationAppnt.setApplicantAge(insured.getRecognizeeAge());
			sdInformationAppnt
					.setApplicantMobile(insured.getRecognizeeMobile());
			sdInformationAppnt.setApplicantMail(insured.getRecognizeeMail());
			insured.setRecognizeeOperate("1");
			insured.setMulInsuredFlag("rid_me");
		}
		insured.setRecognizeeSn(PubFun.GetSDInsuredSn());
		return sdInformationAppnt;
	}

	/**
	 * 获取订单表信息
	 * 
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
		sdorder.setPayAmount(BigDecimal.valueOf(Double.valueOf("0.00")));
		sdorder.setPaySn(paySn);
		sdorder.setOffsetPoint("0");
		sdorder.setActivitySn(activitySn);
		sdorder.setOrderActivity(initPrem);// 单个订单优惠金额
		sdorder.setSumActivity(initPrem);// 订单对应活动优惠的总金额
		sdorder.setOrderCoupon("0");
		sdorder.setOrderIntegral("0");
		sdorder.setDiscountRates("");// 订单折扣费率
		sdorder.setDiscountAmount("");// 订单优惠金额
		sdorder.setChannelsn(channelSn);
		sdorder.setPayPrice("0.00");
		sdorder.setOrderStatus(SDOrderStatus.paid);
		sdorder.setPayStatus(SDPayStatus.paid);
		sdorder.setPayType("zerozf");
		sdorder.setProductNum(1);
		sdorder.setOrderSn(orderSn);
		return sdorder;
	}

	/**
	 * 获取订单明细表信息
	 * 
	 * @param informationSn
	 * @param paramter
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private SDInformation getSDInformation(String informationSn,
			Map<String, Object> paramter, String initPrem) {
		SDInformation sdInformation = new SDInformation();
		String[] baseInformation = new String[17];
		baseInformation = (String[]) paramter.get("baseInformation");// 产品基础数据
		String productCode = baseInformation[0];

		sdInformation.setInformationSn(informationSn);
		sdInformation.setProductPrice(initPrem);// 商品原价
		sdInformation.setProductDiscountPrice(initPrem);// 产品折扣价
		sdInformation.setInsuranceCompany(baseInformation[2]);
		sdInformation.setProductId(productCode);
		sdInformation.setProductName(baseInformation[1]);
		sdInformation.setProductQuantity("1");
		sdInformation.setProductOutCode(baseInformation[4].toString());// 产品外部编码
		sdInformation.setDiscountRates(baseInformation[10].toString());// 产品折扣率
		sdInformation.setProductHtmlFilePath("");
		sdInformation.setPoint("0");
		sdInformation.setPointStatus("0");
		sdInformation.setSupKindCode(baseInformation[12].toString());
		sdInformation.setSupRiskCode(baseInformation[13].toString());
		sdInformation.setRiskType(baseInformation[5].toString());// 内部统计险种中类别
		sdInformation.setSubRiskType(baseInformation[7].toString());// 内部统计险种小类别
		sdInformation.setPrimitiveProductTitle(String
				.valueOf(baseInformation[16]));// 保险公司原名称
		sdInformation.setAppMult("1");
		sdInformation.setPolicyNum("1");

		sdInformation.setStartDate(svaliDate);
		sdInformation.setEndDate(evaliDate);
		sdInformation.setTextAge(textAgeValue);

		// 解决默认保费为0问题，删除掉多余代码
		if (riskAppFactior.size() > 0) {

			for (int i = 0; i < riskAppFactior.size(); i++) {
				OrderRiskAppFactor orderRiskAppFactor = riskAppFactior.get(i);

				// 计划列表
				List<FEMRiskFactorList> tFEMRiskFactorList = null;
				tFEMRiskFactorList = orderRiskAppFactor.getFactorValue();
				for (FEMRiskFactorList t : tFEMRiskFactorList) {
					if ("Plan".equals(orderRiskAppFactor.getFactorType())) {
						try {
							if (StringUtil.isNotEmpty(planValue)) {
								planValue = java.net.URLDecoder
										.decode(planValue, "UTF-8");
								if (planValue.equals(t.getFactorValue())) {
									String tPlanName = t
											.getFactorDisplayValue();
									sdInformation
											.setPlanCode(java.net.URLDecoder
													.decode(planValue));
									sdInformation.setPlanName(tPlanName);
								}
							}
						} catch (Exception e) {
							logger.error(e.getMessage(), e);
						}
					}
					// 保险期限
					if ("Period".equals(orderRiskAppFactor.getFactorType())) {

						if (StringUtil.isNotEmpty(periodValue)
								&& periodValue.equals(t.getFactorValue())) {
							String tPeriodName = t.getFactorDisplayValue();
							String periValue = t.getFactorValue();
							if (periValue.contains("-")) {
								String[] periods = periValue.split("-");
								String splitEnsure = periods[1];
								sdInformation.setEnsureLimit(splitEnsure.substring(
										0, splitEnsure.length() - 1));
								sdInformation.setEnsureLimitType(splitEnsure
										.substring(splitEnsure.length() - 1,
												splitEnsure.length()));
							} else {
								sdInformation.setEnsureLimit(periValue.substring(
										0, periValue.length() - 1));
								sdInformation.setEnsureLimitType(periValue
										.substring(periValue.length() - 1,
												periValue.length()));
							}
							
							sdInformation.setEnsure(periodValue);
							sdInformation.setEnsureDisplay(tPeriodName);
						}
					}
					// 缴费年期
					if ("FeeYear".equals(orderRiskAppFactor.getFactorType())) {

						if (StringUtil.isNotEmpty(feeYearValue)
								&& feeYearValue.equals(t.getFactorValue())) {
							String tFeeYearName = t.getFactorDisplayValue();
							sdInformation.setChargeYear(feeYearValue);
							sdInformation.setChargeYearName(tFeeYearName);
						}
					}
					// 缴费方式
					if ("AppLevel".equals(orderRiskAppFactor.getFactorType())) {

						if (StringUtil.isNotEmpty(appLevelValue)
								&& appLevelValue.equals(t.getFactorValue())) {
							String tAppLevelName = t.getFactorDisplayValue();
							sdInformation.setAppLevel(appLevelValue);
							sdInformation.setAppLevelName(tAppLevelName);
						}
					}
					// 投保类别
					if ("AppType".equals(orderRiskAppFactor.getFactorType())) {

						if (StringUtil.isNotEmpty(appTypeValue)
								&& appTypeValue.equals(t.getFactorValue())) {
							String tAppTypeName = t.getFactorDisplayValue();
							sdInformation.setAppType(appTypeValue);
							sdInformation.setAppTypeName(tAppTypeName);
						}
					}
				}
			}
		}
		return sdInformation;
	}

	/**
	 * 获取订单项1信息
	 * 
	 * @param sdorder
	 * @return
	 */
	private SDOrderItem getSDOrderItem(SDOrder sdorder) {
		SDOrderItem tSDOrderItem = new SDOrderItem();
		tSDOrderItem.setOrderitemSn(PubFun.GetItemNo());
		tSDOrderItem.setOrderSn(sdorder.getOrderSn());
		tSDOrderItem.setSdorder(sdorder);
		tSDOrderItem.setOrderPoint("0");
		tSDOrderItem.setPointStatus("0");
		return tSDOrderItem;
	}

	/**
	 * 解析Excel内容
	 *
	 * @param upLoadFileName
	 *            上传文件
	 * @return 成功：集合有值；失败：集合为空。
	 */
	public List<SDInformationInsured> resolvingExcel(String upLoadFileName, Map<String, Object> cMap) {
        List<SDInformationInsured> insuredList = new ArrayList<SDInformationInsured>();
	    if (StringUtil.isEmpty(productId)) {
			this.addError("产品编码不能为空！");
            return null;
		}

		String	errorMsg = "";

		try {
			// 取得并解析文件头部
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(upLoadFileName));
			Sheet sheet = workbook.getSheetAt(0);// 取头一个sheet
			// 获得sheet总行数
			int rowCount = sheet.getLastRowNum() > 213 ? 213 : sheet.getLastRowNum();

			// log输出
			logger.info("found excel rows count:{}", rowCount);

			// 检查最大人数上限
			if(!this.checkMaxNum(rowCount,sheet)){
				return null;
			}

			// 根据产品判断使用模板是否正确
			if(!this.checkModel(productId, sheet)) {
				this.addError("请不要恶意更改上传模板，请使用正确的模板进行导入");
			}

			comCode = productId.substring(0, 4);
			// 产品年龄限制
			String limitAge = orderConfigNewService.getSectionAge(productId);
			Row row;
			String cellTwoValue;
			String cellFourValue;
            String cellFiveValue;
			int cellCount;
			String cellTrueValue;
            Pattern pattern = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            // 模板title对象
            Row titleRow = sheet.getRow(13);
			List<String> recognizeeIdentityList = new ArrayList<String>();
            // 身份证flag
            boolean idTypeFlag;
            // title
            String titleName;
            String cellValue;
			// 遍历行row
			for (int rowIndex = 14; rowIndex <= rowCount; rowIndex++) {
                idTypeFlag = true;
				SDInformationInsured insured = new SDInformationInsured();
				// 获得行对象
				row = sheet.getRow(rowIndex);
				if (null != row) {
					// 如果没有值，跳出循环
					cellTwoValue = String.valueOf(orderConfigNewService.getCellString(row.getCell(2))).trim();
                    cellFourValue = String.valueOf(orderConfigNewService.getCellString(row.getCell(4))).trim();
					cellFiveValue = String.valueOf(orderConfigNewService.getCellString(row.getCell(5))).trim();
					if (StringUtil.isEmpty(cellTwoValue) && StringUtil.isEmpty(cellFourValue)) {
						break;
					}

					// 剔除证件号相同的被保人
					if (recognizeeIdentityList.contains(cellFiveValue)) {
						errorMsg +=  "证件号" + cellFiveValue + "已经存在;"  + "错误所在行数：" + (rowIndex + 1) + "\n";
					}

					// 获得本行中单元格的个数
					cellCount = row.getLastCellNum();
					// 遍历列cell
					for (int cellIndex = 2; cellIndex < cellCount; cellIndex++) {
                        // 获得指定单元格中的数据
                        cellValue = String.valueOf(orderConfigNewService.getCellString(row.getCell(cellIndex))).trim();
                        // 获得当前title
                        titleName = titleRow.getCell(cellIndex) == null ? null : titleRow.getCell(cellIndex).getStringCellValue();
                        if (!StringUtil.isEmpty(titleName)) {
                            if (StringUtil.isEmpty(cellValue)) {
                                errorMsg += titleName + "不能为空;"  + "错误所在行数：" + (rowIndex + 1) + "\n";
                            } else if (!orderConfigNewService.checkCellValue(titleName, cellValue)) {
                                errorMsg += titleName + cellValue + "含有非法字符！"  + "错误所在行数：" + (rowIndex + 1) + "\n";
                            } else if (titleName.contains("英文名")) {
                            	String tempMsg = orderConfigNewService.checkEnName("英文名", cellValue);
								if (!"".equals(tempMsg)) {
									errorMsg += tempMsg  + "错误所在行数：" + (rowIndex + 1) + "\n";
								}
							}
                        }

                        // 被保人三级职业
                        if (StringUtil.isNotEmpty((String)cMap.get("recognizeeOccupation1"))) {
                            insured.setRecognizeeOccupation1((String)cMap.get("recognizeeOccupation1"));
                        }
                        if (StringUtil.isNotEmpty((String)cMap.get("recognizeeOccupation2"))) {
                            insured.setRecognizeeOccupation2((String)cMap.get("recognizeeOccupation2"));
                        }
                        if (StringUtil.isNotEmpty((String)cMap.get("recognizeeOccupation3"))) {
                            insured.setRecognizeeOccupation3((String)cMap.get("recognizeeOccupation3"));
                        }

                        switch (cellIndex) {
                            case 2:
                                // 姓名
                                insured.setRecognizeeName(cellValue);
                                continue;
                            case 3:
                                // 与投保人关系:根据excel中的值、保险公司、产品id找到对应的编码值
                                cellTrueValue = this.dictionaryService
                                        .getCodeValueByCodeName(comCode, "Relationship", cellValue);
                                insured.setRecognizeeAppntRelation(cellTrueValue);
                                insured.setRecognizeeAppntRelationName(cellValue);
                                continue;
                            case 4:
                                // 证件类型:根据excel中的值、保险公司、产品id找到对应的编码值
                                cellTrueValue = this.dictionaryService
                                        .getCodeValueByCodeName(comCode, "certificate", cellValue);
                                insured.setRecognizeeIdentityType(cellTrueValue);
                                insured.setRecognizeeIdentityTypeName(cellValue);
                                if (!"身份证".equals(cellValue)) {
                                    idTypeFlag = false;
                                }
                                continue;
                            case 5:
                                // 证件号码
                                if (idTypeFlag && !orderConfigNewService.isIdentityCard(cellValue)) {
                                    errorMsg += "请输入正确的身份证号;"  + "错误所在行数：" + (rowIndex + 1) + "\n";;
                                } else {
                                    insured.setRecognizeeIdentityId(cellValue);
                                }
                                continue;
                            case 6:
                                // 年龄校验
                                if ((productId.startsWith("1087")
                                        || productId.startsWith("1001")
                                        || productId.startsWith("1004")
                                        || productId.startsWith("2043"))
                                        &&!orderConfigNewService.validateAgeNew(cellValue, limitAge)) {
                                    errorMsg +=  "此年龄不能购买本产品;"  + "错误所在行数：" + (rowIndex + 1) + "\n";
                                } else if (!(productId.startsWith("1087")
                                        || productId.startsWith("1001")
                                        || productId.startsWith("1004")
                                        || productId.startsWith("2043"))
                                        &&!orderConfigNewService
                                        .validateAgeNewBaseOnEffectiveDate(cellValue, limitAge, svaliDate)) {
                                    errorMsg +=  "此年龄不能购买本产品;"  + "错误所在行数：" + (rowIndex + 1) + "\n";
                                } else {
                                    if (StringUtil.isNotEmpty(cellValue)) {
                                        cellValue = sdf.format(sdf.parse(cellValue.replaceAll("/", "-")));
                                    }
                                    // 出生日期
                                    int age = com.sinosoft.sms.messageinterface.pubfun.PubFun.getAge(cellValue);
                                    if (age < 18) {
                                        childFlag = true;
                                    }
                                    insured.setRecognizeeBirthday(cellValue);
                                    insured.setRecognizeeAge(String.valueOf(age));
                                }
                                continue;
                            case 7:
                                // 性别
                                if (!"男".equals(cellValue) && !"女".equals(cellValue)) {
                                    errorMsg +=  "性别输入有误;"  + "错误所在行数：" + (rowIndex + 1) + "\n";
                                } else {
                                    // 与性别:根据excel中的值、保险公司、产品id找到对应的编码值
                                    cellTrueValue = this.dictionaryService
                                            .getCodeValueByCodeName(comCode, "Sex", cellValue);
                                    insured.setRecognizeeSex(cellTrueValue);
                                    insured.setRecognizeeSexName(cellValue);
                                }
                                continue;
                            case 8:
                                // 手机号
                                if (!orderConfigNewService.isMobile(cellValue)) {
                                    errorMsg += "请输入正确手机号码;"  + "错误所在行数：" + (rowIndex + 1) + "\n";
                                } else {
                                    insured.setRecognizeeMobile(cellValue);
                                }
                                continue;
                            case 9:
                                if (StringUtil.isEmpty(titleName)) {
                                    // 判断本行最后一列是否填写邮箱
                                    if (!pattern.matcher(cellValue).matches()) {
                                        errorMsg += ("邮箱不正确;请在" + titleRow.getCell(titleRow.getLastCellNum() - 1)
                                                .getStringCellValue() + "的后一列填写邮箱;"  + "错误所在行数：" + (rowIndex + 1)) + "\n";
                                    } else {
                                        insured.setRecognizeeMail(cellValue);
                                    }
                                } else {
                                    if (titleName.contains("航班号")) {
                                        insured.setFlightNo(cellValue);
                                    } else if (titleName.contains("英文名")) {
                                        insured.setRecognizeeEnName(cellValue);
                                    } else if (titleName.contains("旅游目的地")) {
                                        cellTrueValue = dictionaryService.getCodeValueByCodeName(
                                                comCode, "CountryCode", cellValue);
                                        insured.setDestinationCountry(cellTrueValue);
                                        insured.setDestinationCountryText(cellValue);
                                    }
                                }
                                continue;
                            case 10:
                                if (StringUtil.isEmpty(titleName)) {
                                    // 判断本行最后一列是否填写邮箱
                                    if (!pattern.matcher(cellValue).matches()) {
                                        errorMsg += ("邮箱不正确;请在" + titleRow.getCell(titleRow.getLastCellNum() - 1)
                                                .getStringCellValue() + "的后一列填写邮箱;"  + "错误所在行数：" + (rowIndex + 1)) + "\n";
                                    } else {
                                        insured.setRecognizeeMail(cellValue);
                                    }
                                } else {
                                    if (titleName.contains("旅游目的地")) {
                                        cellTrueValue = dictionaryService.getCodeValueByCodeName(
                                                comCode, "CountryCode", cellValue);
                                        insured.setDestinationCountry(cellTrueValue);
                                        insured.setDestinationCountryText(cellValue);
                                    } else if (titleName.contains("起飞时间")) {
                                        insured.setFlightTime(cellValue + ":00");
                                    }
                                }
                                continue;
                            case 11:
                                if (StringUtil.isEmpty(titleName)) {
                                    // 判断本行最后一列是否填写邮箱
                                    if (!pattern.matcher(cellValue).matches()) {
                                        errorMsg += ("邮箱不正确;请在" + titleRow.getCell(titleRow.getLastCellNum() - 1)
                                                .getStringCellValue() + "的后一列填写邮箱;" + "错误所在行数：" + (rowIndex + 1)) + "\n";
                                    } else {
                                        insured.setRecognizeeMail(cellValue);
                                    }
                                } else {
                                    if (titleName.contains("起飞地点")) {
                                        insured.setFlightLocation(cellValue);
                                    }
                                }
                                continue;
                            case 12:
                                if (StringUtil.isEmpty(titleName)) {
                                    // 判断本行最后一列是否填写邮箱
                                    if (!pattern.matcher(cellValue).matches()) {
                                        errorMsg += ("邮箱不正确;请在" + titleRow.getCell(titleRow.getLastCellNum() - 1)
                                                .getStringCellValue() + "的后一列填写邮箱;" + "错误所在行数：" + (rowIndex + 1)) + "\n";
                                    } else {
                                        insured.setRecognizeeMail(cellValue);
                                    }
                                }
                                continue;
                            default:
                                break;
                        }
                    }
					// 里层for循环结束 *******************************

                    if (!StringUtil.isEmpty(errorMsg)) {
                        logger.error("自助出单-------被保人错误信息：{}", errorMsg);
                    }

                    // 保费试算
                    if (!this.getPrem(cMap, insured, limitAge, errorMsg, rowIndex)) {
                    }

					if (StringUtil.isEmpty(errorMsg) && insured != null && StringUtil.isNotEmpty(insured.getRecognizeeName())) {
                        insuredList.add(insured);
					}

                    // 保存证件类型，证件号，剔除重复被保人
					if (insured != null && StringUtil.isNotEmpty(insured.getRecognizeeIdentityType())) {
						recognizeeIdentityList.add(insured.getRecognizeeIdentityId());
                    }
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		if (StringUtil.isNotEmpty(errorMsg)) {
			this.addError(errorMsg);
			return null;
		}
		return insuredList;
	}

	/**
	 * 检查上传人数上限
	 *
	 * @param rowCount
	 * @param sheet
	 * @return
	 */
	private boolean checkMaxNum(int rowCount, Sheet sheet) {
		if (rowCount < 15) {
			this.addError("请使用正确的模板进行上传操作！");
			return false;
		}
		Row rowMax = sheet.getRow(214);
		int maxCount = 200;
		if (productId.startsWith("2011")) {
			rowMax = sheet.getRow(64);
			maxCount = 50;
		}

		// 是否超过导入数量最大限制 （太平洋 50人，其他没有核保的公司为200人）
		if(rowMax != null && !StringUtil.isEmpty(rowMax.getCell(2).getStringCellValue())){
			this.addError("上传人数超过上限(" + maxCount + "人)，请重新上传！");
			return false;
		}

		return true;
	}
	/**
	 * 根据产品判断使用模板是否正确
	 *
	 * @param productId
	 * @param sheet
	 * @return
	 */
	private boolean checkModel(String productId, Sheet sheet) {

		String keyValue = orderConfigNewService.getExcelKeyValue(productId);

		boolean keyFlag = true;
		if (!"".equals(keyValue) || keyValue != null) {
			String[] arr = keyValue.split(";");
			Row row;
			int cellCount;
			for(String strKeyVal : arr) {
				keyFlag = false;
				row = sheet.getRow(13);
				cellCount = row.getLastCellNum();
				for (int i = 2; i < cellCount; i++) {
					if (strKeyVal.equals(row.getCell(i).getStringCellValue())) {
						keyFlag = true;
						break;
					}
				}
				if (!keyFlag) {
					break;
				}
			}
		}

		return keyFlag;
	}

    /**
     * 保费试算
     *
     * @param cMap
     * @param insured
     * @param limitAge
     * @param errorMsg
     * @return
     */
	private boolean getPrem (Map<String, Object> cMap, SDInformationInsured insured, String limitAge, String errorMsg, int rowIndex){
        String insuredSex = insured.getRecognizeeSex();
        cMap.put("insuredBirthDay", insured.getRecognizeeBirthday());
        cMap.put("insuredSex", insuredSex);
        String dateNow = PubFun.getCurrentDate();
        String realSecAge;
        Map<String, Map<String, Object>> insuredPremMap = new HashMap<String, Map<String, Object>>();
        Map<String, Object> tempMap = null;
        if (productId.startsWith("1087")
                || productId.startsWith("1001")
                || productId.startsWith("1004")
                || productId.startsWith("2043")) {
            realSecAge = orderConfigNewService.getRealSectionAge(
                    insured.getRecognizeeBirthday(), limitAge, dateNow);
        } else {
            realSecAge = orderConfigNewService.getRealSectionAge(
                    insured.getRecognizeeBirthday(), limitAge, startDate);
        }

        if (!"-1".equals(realSecAge)) {
            if (String.valueOf(insuredPremMap.get(insuredSex + "-" + realSecAge)) != null
                    && !"null".equals(String.valueOf(insuredPremMap.get(insuredSex + "-" + realSecAge)))
                    && !"".equals(String.valueOf(insuredPremMap.get(insuredSex + "-" + realSecAge)))) {
                tempMap = insuredPremMap.get(insuredSex + "-" + realSecAge);
            } else {
                tempMap = orderConfigNewService.calPrem(cMap, "");
                insuredPremMap.put(insuredSex + "-" + realSecAge, tempMap);
            }

            if (tempMap.containsKey("errMessage")
                    && StringUtil.isNotEmpty((String) tempMap.get("errMessage"))) {
                errorMsg += String.valueOf(tempMap.get("errMessage")) + "错误所在行数：" + (rowIndex + 1) + "\n";
                return false;
            } else if ("0".equals(String.valueOf(tempMap.get("retCountPrem")))
                    || "0".equals(String.valueOf(tempMap.get("productPrem")))) {
                errorMsg += "保费试算失败，请输入正确的日期!" + "错误所在行数：" + (rowIndex + 1) + "\n";
                return false;
            } else {
                insured.setDiscountPrice(String.valueOf(tempMap.get("retCountPrem")));
                insured.setRecognizeePrem(String.valueOf(tempMap.get("retPrem")));
                insured.setRecognizeeTotalPrem(String.valueOf(tempMap.get("productPrem")));
            }
        } else {
            insured.setRecognizeePrem(String.valueOf("0"));
            insured.setRecognizeeTotalPrem(String.valueOf("0"));
            insured.setDiscountPrice("0");
        }

        return true;
    }

	/**
	 * 发送承保
	 *
	 * @param sdi
	 * @param sdo
	 * @param sdinformationInsuredSet
	 * @param UWUNSuc
	 */
	private void sendInsured (SDInformation sdi, SDOrder sdo,
							  List<SDInformationInsured> sdinformationInsuredSet, List<String> UWUNSuc, String sendEmailFlag) {
		InsureTransferNew itn = new InsureTransferNew();
		boolean flag = true;
		String appStatus = "";
		String insureMsg = "";
		if (sdinformationInsuredSet != null && sdinformationInsuredSet.size() > 1) {

			for (SDInformationInsured sdInsured : sdinformationInsuredSet) {
				String ordersn = sdInsured.getOrderSn();
				String recognizeeSn = sdInsured
						.getRecognizeeSn();
				String sql = "select appStatus,insureMsg from SDInformationRiskType where orderSn = ? and recognizeeSn = ?";
				DataTable dt = new QueryBuilder(sql,
						ordersn, recognizeeSn)
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
			itn.callInsTransInterface(
					sdi.getInsuranceCompany(),
					sdo.getOrderSn(), UWUNSuc, sendEmailFlag);
			for (SDInformationInsured sdInsured : sdinformationInsuredSet) {
				String ordersn = sdInsured.getOrderSn();
				String recognizeeSn = sdInsured
						.getRecognizeeSn();
				String sql = "select appStatus,insureMsg from SDInformationRiskType where orderSn = ? and recognizeeSn = ?";
				DataTable dt = new QueryBuilder(sql,
						ordersn, recognizeeSn)
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
				payAction.sendErrorMail(
						sdo.getOrderSn(), ErrMsg);

			}
		}
	}

	/**
	 * 获取职业子集
	 *
	 * @return
	 */
	public String getNextLevelOccupation() {
		Map<String, String> map = new HashMap<String, String>();
		String id = getParameter("id");
		String sql = "SELECT id,name FROM Occupation WHERE parent_id = ?";
		QueryBuilder qb = new QueryBuilder(sql,id);
		DataTable dt = qb.executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				map.put(dt.getString(i,"id"),dt.getString(i,"name"));
			}
		}
		String strOptions = HtmlUtil.mapxToOptions(map,true);
		strOptions = strOptions.replaceAll("span","option");

		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("status","0");
		resultMap.put("data",strOptions);
		return ajaxJson(resultMap);
	}

	public String getImportInsureFile() {
		return importInsureFile;
	}

	public void setImportInsureFile(String importInsureFile) {
		this.importInsureFile = importInsureFile;
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

	public String getProtectionPeriodTy() {
		return protectionPeriodTy;
	}

	public void setProtectionPeriodTy(String protectionPeriodTy) {
		this.protectionPeriodTy = protectionPeriodTy;
	}

	public String getProtectionPeriodLast() {
		return protectionPeriodLast;
	}

	public void setProtectionPeriodLast(String protectionPeriodLast) {
		this.protectionPeriodLast = protectionPeriodLast;
	}

	public String getChannelSn() {
		return channelSn;
	}

	public void setChannelSn(String channelSn) {
		this.channelSn = channelSn;
	}

	public String getHealthInfo() {
		return healthInfo;
	}

	public void setHealthInfo(String healthInfo) {
		this.healthInfo = healthInfo;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public String getApplicantEnName() {
		return applicantEnName;
	}

	public void setApplicantEnName(String applicantEnName) {
		this.applicantEnName = applicantEnName;
	}

	public String getApplicantIDtype() {
		return applicantIDtype;
	}

	public void setApplicantIDtype(String applicantIDtype) {
		this.applicantIDtype = applicantIDtype;
	}

	public String getApplicantID() {
		return applicantID;
	}

	public void setApplicantID(String applicantID) {
		this.applicantID = applicantID;
	}

	public String getApplicantSex() {
		return applicantSex;
	}

	public void setApplicantSex(String applicantSex) {
		this.applicantSex = applicantSex;
	}

	public String getApplicantBirthday() {
		return applicantBirthday;
	}

	public void setApplicantBirthday(String applicantBirthday) {
		this.applicantBirthday = applicantBirthday;
	}

	public String getApplicantMobile() {
		return applicantMobile;
	}

	public void setApplicantMobile(String applicantMobile) {
		this.applicantMobile = applicantMobile;
	}

	public String getApplicantMail() {
		return applicantMail;
	}

	public void setApplicantMail(String applicantMail) {
		this.applicantMail = applicantMail;
	}

	public String getPeriodValue() {
		return periodValue;
	}

	public void setPeriodValue(String periodValue) {
		this.periodValue = periodValue;
	}

	public String getPlanValue() {
		return planValue;
	}

	public void setPlanValue(String planValue) {
		this.planValue = planValue;
	}

	public String getOccupValue() {
		return occupValue;
	}

	public void setOccupValue(String occupValue) {
		this.occupValue = occupValue;
	}

	public String getTextAgeValue() {
		return textAgeValue;
	}

	public void setTextAgeValue(String textAgeValue) {
		this.textAgeValue = textAgeValue;
	}

	public String getAppTypeValue() {
		return appTypeValue;
	}

	public void setAppTypeValue(String appTypeValue) {
		this.appTypeValue = appTypeValue;
	}

	public String getFeeYearValue() {
		return feeYearValue;
	}

	public void setFeeYearValue(String feeYearValue) {
		this.feeYearValue = feeYearValue;
	}

	public String getGradeValue() {
		return gradeValue;
	}

	public void setGradeValue(String gradeValue) {
		this.gradeValue = gradeValue;
	}

	public String getAppLevelValue() {
		return appLevelValue;
	}

	public void setAppLevelValue(String appLevelValue) {
		this.appLevelValue = appLevelValue;
	}

	public String getMulPeopleValue() {
		return mulPeopleValue;
	}

	public void setMulPeopleValue(String mulPeopleValue) {
		this.mulPeopleValue = mulPeopleValue;
	}

	public String getActivityDesc() {
		return activityDesc;
	}

	public void setActivityDesc(String activityDesc) {
		this.activityDesc = activityDesc;
	}

	public String getOccupationLevelOne() {
        return occupationLevelOne;
    }

    public void setOccupationLevelOne(String occupationLevelOne) {
        this.occupationLevelOne = occupationLevelOne;
    }

    public String getOccupationLevelTwo() {
        return occupationLevelTwo;
    }

    public void setOccupationLevelTwo(String occupationLevelTwo) {
        this.occupationLevelTwo = occupationLevelTwo;
    }

    public String getOccupationLevelThree() {
        return occupationLevelThree;
    }

    public void setOccupationLevelThree(String occupationLevelThree) {
        this.occupationLevelThree = occupationLevelThree;
    }
    
	public String getSendEmailFlag() {
		return sendEmailFlag;
	}

	public void setSendEmailFlag(String sendEmailFlag) {
		this.sendEmailFlag = sendEmailFlag;
	}

	public class MutliThread implements Runnable {
		private Map<String, String[]> orderMap;
		String userName;
		String remoteAddr;
		String sendEmailFlag;

		public MutliThread(Map<String, String[]> orderMap, String userName, String remoteAddr, String sendEmailFlag) {
			this.orderMap = orderMap;
			this.userName = userName;
			this.remoteAddr = remoteAddr;
			this.sendEmailFlag = sendEmailFlag;
		}

		@Override
		public void run() {
			insure(orderMap, userName, remoteAddr, sendEmailFlag);
		}
	}
}



