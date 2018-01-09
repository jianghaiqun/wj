package cn.com.sinosoft.util;

import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.OrderDutyFactor;
import cn.com.sinosoft.entity.OrderRiskAppFactor;
import cn.com.sinosoft.entity.SDInformation;
import cn.com.sinosoft.entity.SDInformationAppnt;
import cn.com.sinosoft.entity.SDInformationDuty;
import cn.com.sinosoft.entity.SDInformationInsured;
import cn.com.sinosoft.entity.SDInformationInsuredElements;
import cn.com.sinosoft.entity.SDOrder;
import cn.com.sinosoft.service.SDOrderService;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FEMDutyAmntPremList;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
public class WapTestUtil {
	private static final Logger logger = LoggerFactory.getLogger(WapTestUtil.class);
	/**
	 * @param args
	 * wap测试工具类
	 */
	
	
	//登录测试
	public static Map<String,Object> wapLoginMap(){
		Map<String,Object> staticLoginMap = new HashMap<String,Object>();
		staticLoginMap.put("USER", "kaixinbao");
		staticLoginMap.put("REQUESTTYPE", "KXBJRT0001");
		//JSON 子节点
		Map<String,Object> loginSubMap = new HashMap<String,Object>();
		loginSubMap.put("UserName", "kaixinbao");
		loginSubMap.put("Nickname", "kxb@test.com");
		 
		JSONArray arr = new JSONArray();
		arr.put(loginSubMap);
		 
		
		staticLoginMap.put("RESULTS", arr);
		return staticLoginMap;
		
	}
	//产品信息测试
	public static Map<String,Object> wapGetProductList(){
		Map<String,Object> productListMap = new HashMap<String,Object>();
		productListMap.put("LogoUrl", Config.getValue("ProductURL")+"//eriskfile/ERiskPic/100101001.jpg");
		productListMap.put("ProdName", "中国人寿中华游保障计划A");
		productListMap.put("ProdCode", "100101001");
		productListMap.put("CompanyID", "1001");
		productListMap.put("AccumulateSales", "100");
		productListMap.put("MinPremium", "20");
		productListMap.put("InsuredAgeScope", "1-80周岁");
		productListMap.put("BenefitTermScope", "1天-1年");
		
		Map<String,Object> benefitItemsMap = new HashMap<String,Object>();
		benefitItemsMap.put("Name", "意外身故");
		benefitItemsMap.put("Amount", "200");
		Map<String,Object> benefitItemsMap1 = new HashMap<String,Object>();
		benefitItemsMap1.put("Name", "意外身故");
		benefitItemsMap1.put("Amount", "200");
		Map<String,Object> benefitItemsMap2 = new HashMap<String,Object>();
		benefitItemsMap2.put("Name", "意外身故");
		benefitItemsMap2.put("Amount", "200");
		JSONArray arrBenefitItems = new JSONArray();
		arrBenefitItems.put(benefitItemsMap2);
		arrBenefitItems.put(benefitItemsMap1);
		arrBenefitItems.put(benefitItemsMap);
		
		Map<String,Object> prodLightspotsMap = new HashMap<String,Object>();
		prodLightspotsMap.put("Name", "意外保障");
		Map<String,Object> prodLightspotsMap1 = new HashMap<String,Object>();
		prodLightspotsMap1.put("Name", "全程保障");
		
		JSONArray arrProdLightspots = new JSONArray();
		arrProdLightspots.put(prodLightspotsMap);
		arrProdLightspots.put(prodLightspotsMap1);
		
		Map<String,Object> prodPlansMap = new HashMap<String,Object>();
		prodPlansMap.put("Name", "计划A");
		Map<String,Object> prodPlansMap1 = new HashMap<String,Object>();
		prodPlansMap1.put("Name", "计划B");
		 
		JSONArray arrProdPlans = new JSONArray();
		arrProdPlans.put(prodPlansMap);
		arrProdPlans.put(prodPlansMap1);

		productListMap.put("ProdPlans", arrProdPlans);
		productListMap.put("ProdLightspots", arrProdLightspots);
		productListMap.put("BenefitItems", prodLightspotsMap1);
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		
		
		
		
		
		
		
		resultMap.put("USER", "kxb");
		resultMap.put("REQUESTTYPE", "KXBJRT0007");
		resultMap.put("STATYS", "true");
		resultMap.put("RESULTS", productListMap);
		return resultMap;
		
	}
	//会员信息
	public static Map<String,Object> wapGetUserInfo(){
		
		Map<String,Object> userInfoMap = new HashMap<String,Object>();
		userInfoMap.put("IntegralSum", "2135");
		userInfoMap.put("DiscCouponCount", "4");
		
		Map<String,Object> orderInfoMap = new HashMap<String,Object>();
		orderInfoMap.put("OrderNumber", "WJ20140123001G");
		orderInfoMap.put("OrderStatus", "已支付");
		orderInfoMap.put("ProdLogoUrl", Config.getValue("ProductURL")+"//eriskfile/ERiskPic/100101001.jpg");
		orderInfoMap.put("ProdName", "中国人寿中华游保障计划A");
		orderInfoMap.put("IsUseDiscCoupon", "True");
		orderInfoMap.put("Premium", "235");
		orderInfoMap.put("SubmitTime", "2014-01-23 15:09:10");
		
		Map<String,Object> orderInfoMap1 = new HashMap<String,Object>();
		orderInfoMap1.put("OrderNumber", "WJ20140123002G");
		orderInfoMap1.put("OrderStatus", "已支付");
		orderInfoMap1.put("ProdLogoUrl", Config.getValue("ProductURL")+"//eriskfile/ERiskPic/100101001.jpg");
		orderInfoMap1.put("ProdName", "中国人寿中华游保障计划A");
		orderInfoMap1.put("IsUseDiscCoupon", "True");
		orderInfoMap1.put("Premium", "235");
		orderInfoMap1.put("SubmitTime", "2014-01-23 15:09:10");
		
		Map<String,Object> orderInfoMap2 = new HashMap<String,Object>();
		orderInfoMap2.put("OrderNumber", "WJ20140123003G");
		orderInfoMap2.put("OrderStatus", "已支付");
		orderInfoMap2.put("ProdLogoUrl", Config.getValue("ProductURL")+"//eriskfile/ERiskPic/100101001.jpg");
		orderInfoMap2.put("ProdName", "中国人寿中华游保障计划A");
		orderInfoMap2.put("IsUseDiscCoupon", "True");
		orderInfoMap2.put("Premium", "235");
		orderInfoMap2.put("SubmitTime", "2014-01-23 15:09:10");
		
		JSONArray arrOrderInfo = new JSONArray();
		arrOrderInfo.put(orderInfoMap2);
		arrOrderInfo.put(orderInfoMap1);
		arrOrderInfo.put(orderInfoMap);
		
		userInfoMap.put("Orders", arrOrderInfo);
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("USER", "kxb");
		resultMap.put("REQUESTTYPE", "KXBJRT0018");
		resultMap.put("STATYS", "true");
		resultMap.put("RESULTS", userInfoMap);
		return resultMap;
	}
	//优惠劵列表信息查询
	public static Map<String,Object> wapGetDiscCouponList(){
		Map<String,Object> couponListMap = new HashMap<String,Object>();
		Map<String,Object> couponListSubMap = new HashMap<String,Object>();
		couponListSubMap.put("DiscCouponNumber", "5f846bab3b7493d9013b7a1a2f59008d");
		couponListSubMap.put("DiscCouponStatus", "未使用");
		couponListSubMap.put("DiscCouponName", "优惠劵A");
		couponListSubMap.put("Amount", "50");
		couponListSubMap.put("ValidDateFrom", "2014-01-01 10:10:10");
		couponListSubMap.put("ValidDateTo", "2014-01-31 10:10:10");
		Map<String,Object> couponListSubMap1 = new HashMap<String,Object>();
		couponListSubMap1.put("DiscCouponNumber", "5f846bab3b7493d9013b7a1a2f59008d");
		couponListSubMap1.put("DiscCouponStatus", "未使用");
		couponListSubMap1.put("DiscCouponName", "优惠劵A");
		couponListSubMap1.put("Amount", "50");
		couponListSubMap1.put("ValidDateFrom", "2014-01-01 10:10:10");
		couponListSubMap1.put("ValidDateTo", "2014-01-31 10:10:10");
		
		JSONArray couponLisInfo = new JSONArray();
		couponLisInfo.put(couponListSubMap1);
		couponLisInfo.put(couponListSubMap);
		
		couponListMap.put("DiscCoupons", couponLisInfo);
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("USER", "kxb");
		resultMap.put("REQUESTTYPE", "KXBJRT0021");
		resultMap.put("STATYS", "true");
		resultMap.put("RESULTS",couponListMap);
		return resultMap;
	}
	//优惠劵信息查询GetDiscCouponInfo
	public static Map<String,Object> wapGetDiscCouponInfo(){
		Map<String,Object> couponListMap = new HashMap<String,Object>();
		couponListMap.put("Amount", "50");
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("USER", "kxb");
		resultMap.put("REQUESTTYPE", "KXBJRT0016");
		resultMap.put("STATYS", "true");
		resultMap.put("RESULTS",couponListMap);
		return resultMap;
	}
	//订单信息查询
	public static Map<String,Object> wapGetApplicationInfo(){
		
		Map<String,Object> orderInfoMap = new HashMap<String,Object>();
		orderInfoMap.put("OrderNumber", "WJ20140123001G");
		
		Map<String,Object> policyInfoMap = new HashMap<String,Object>();
		
		Map<String,Object> policySubInfoMap = new HashMap<String,Object>();
		policySubInfoMap.put("ProdClass", "");
		policySubInfoMap.put("ProdType", "");
		policySubInfoMap.put("CompanyID", "1001");
		policySubInfoMap.put("ProdCode", "100101001");
		policySubInfoMap.put("BenefitTerm", "8天");
		policySubInfoMap.put("PaymentMode", "趸交");
		policySubInfoMap.put("PaymentTerm", "");
		policySubInfoMap.put("PolicyDateFrom", "2014-01-01 10:10:10");
		policySubInfoMap.put("PolicyDateTo", "2014-01-09 10:10:10");
		policySubInfoMap.put("ProdPlan", "计划A");
		policySubInfoMap.put("BuyCopies", "1");
		policySubInfoMap.put("Premium", "235");
		//投保人信息
		Map<String,Object> appntInfoMap = new HashMap<String,Object>();
		appntInfoMap.put("AppntArea1Name", "北京");
		appntInfoMap.put("AppntArea2Name", "北京");
		appntInfoMap.put("AppntArea3Name", "昌平");
		appntInfoMap.put("AppntArea", "史各庄");
		appntInfoMap.put("AppntOccupation1Name", "A001");
		appntInfoMap.put("AppntOccupation2Name", "A001001");
		appntInfoMap.put("AppntOccupation3Name", "A001001001");
		appntInfoMap.put("AppntBirthday", "1986-01-01");
		appntInfoMap.put("AppntName", "测试");
		appntInfoMap.put("AppnttGender", "女");
		appntInfoMap.put("AppntMobile", "13222222222");
		appntInfoMap.put("AppntEmail", "test@test.com.cn");
		appntInfoMap.put("AppntAddress", "史各庄");
		appntInfoMap.put("AppntEName", "ceshi");
		appntInfoMap.put("AppntPayperValidDate", "20年");
		appntInfoMap.put("AppntPayperType", "身份证");
		appntInfoMap.put("AppntPayperNumber", "111111111111111111");
		appntInfoMap.put("AppntPostCode", "111111");
		
		JSONArray arrAppntInfo = new JSONArray();
		arrAppntInfo.put(appntInfoMap);
		//投保人信息
		Map<String,Object> insuredInfoMap = new HashMap<String,Object>();
		insuredInfoMap.put("RecognizeeArea1Name", "北京");
		insuredInfoMap.put("RecognizeeArea2Name", "北京");
		insuredInfoMap.put("RecognizeeArea3Name", "昌平");
		insuredInfoMap.put("RecognizeeArea", "史各庄");
		insuredInfoMap.put("RecognizeeOccupation1Name", "A001");
		insuredInfoMap.put("RecognizeeOccupation2Name", "A001001");
		insuredInfoMap.put("RecognizeeOccupation3Name", "A001001001");
		insuredInfoMap.put("RecognizeeBirthday", "1986-01-01");
		insuredInfoMap.put("RecognizeeName", "测试");
		insuredInfoMap.put("RecognizeetGender", "女");
		insuredInfoMap.put("RecognizeeMobile", "13222222222");
		insuredInfoMap.put("RecognizeeEmail", "test@test.com.cn");
		insuredInfoMap.put("RecognizeeAddress", "史各庄");
		insuredInfoMap.put("RecognizeeEName", "ceshi");
		insuredInfoMap.put("RecognizeePayperValidDate", "20年");
		insuredInfoMap.put("RecognizeePayperType", "身份证");
		insuredInfoMap.put("RecognizeePayperNumber", "111111111111111111");
		insuredInfoMap.put("RecognizeePostCode", "111111");
		insuredInfoMap.put("RecognizeeRelationShip", "其他");
		insuredInfoMap.put("RecognizeeNational", "");
		insuredInfoMap.put("RecognizeeOverseasOccupation", "");
		insuredInfoMap.put("RecognizeeStudentCode", "");
		insuredInfoMap.put("RecognizeeHasBuyLifeCov", "");
		insuredInfoMap.put("RecognizeeHasBuyLifeCovAmount", "");
		insuredInfoMap.put("RecognizeeTravelDestinationInput", "");
		insuredInfoMap.put("RecognizeeTravelDestinationSel", "");
		insuredInfoMap.put("RecognizeeIsStudyAbroad", "");
		insuredInfoMap.put("RecognizeeStudyAbroadCompany", "");
		insuredInfoMap.put("FlightNumber", "");
		insuredInfoMap.put("BuyCopies", "");
		insuredInfoMap.put("TakeOffLocation", "");
		insuredInfoMap.put("TakeOffDate", "");
		insuredInfoMap.put("TakeOffTime", "");
		insuredInfoMap.put("RecognizeeHeight", "");
		insuredInfoMap.put("RecognizeeWeight", "");
		insuredInfoMap.put("RecognizeePostCode", "");
		insuredInfoMap.put("RecognizeeDrivingSchoolName", "");
		
		JSONArray arrInsuredInfo = new JSONArray();
		arrInsuredInfo.put(insuredInfoMap);
		
		policyInfoMap.put("HolderInfo", arrAppntInfo);
		
		policyInfoMap.put("InsuredInfos", arrInsuredInfo);
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("USER", "kxb");
		resultMap.put("REQUESTTYPE", "KXBJRT0022");
		resultMap.put("STATYS", "true");
		resultMap.put("RESULTS",policyInfoMap);
		return resultMap;
	}
	public static Map<String,Object> wapRegisterInfo(){
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("USER", "kxb");
		resultMap.put("REQUESTTYPE", "KXBJRT0002");
		resultMap.put("STATYS", "true");
		resultMap.put("RESULTS","");
		return resultMap;
	}
    public static Map<String,Object> wapUpdateUserBaseInfo(){
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("USER", "kxb");
		resultMap.put("REQUESTTYPE", "KXBJRT0019");
		resultMap.put("STATYS", "true");
		resultMap.put("RESULTS","");
		return resultMap;
	}
    public static Map<String,Object> wapChangePassword(){
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("USER", "kxb");
		resultMap.put("REQUESTTYPE", "KXBJRT0020");
		resultMap.put("STATYS", "true");
		resultMap.put("RESULTS","");
		return resultMap;
	}
	
    public static Map<String,Object> wapFetchPassword(){
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("USER", "kxb");
		resultMap.put("REQUESTTYPE", "KXBJRT0005");
		resultMap.put("STATYS", "true");
		resultMap.put("RESULTS","");
		return resultMap;
	}
	public static Map<String,Object> wapGetMainPageInfo(){
		
		Map<String,Object> mainPageInfoMap = new HashMap<String,Object>();
		
		Map<String,Object> noticesInfoMap = new HashMap<String,Object>();
		noticesInfoMap.put("Title", "限时优惠");
		noticesInfoMap.put("Content", "美亚万国游限时优惠");
		Map<String,Object> noticesInfoMap1 = new HashMap<String,Object>();
		noticesInfoMap1.put("Title", "双倍积分");
		noticesInfoMap1.put("Content", "手机购买美亚万国游双倍积分");
		
		JSONArray noticesInfo = new JSONArray();
		noticesInfo.put(noticesInfoMap);
		noticesInfo.put(noticesInfoMap1);
		mainPageInfoMap.put("Notices", noticesInfo);
		
		Map<String,Object> discountProdsMap = new HashMap<String,Object>();
		discountProdsMap.put("RemainTimes", "30分");
		discountProdsMap.put("ProdName", "中国人寿中华游保障计划A");
		discountProdsMap.put("ProdCode", "100101001");
		discountProdsMap.put("ProdLogo", Config.getValue("ProductURL")+"//eriskfile/ERiskPic/100101001.jpg");
		discountProdsMap.put("OriginalPremium", "30元");
		discountProdsMap.put("DiscPremium", "25元");
		Map<String,Object> discountProdsMap1 = new HashMap<String,Object>();
		discountProdsMap1.put("RemainTimes", "30分");
		discountProdsMap1.put("ProdName", "中国人寿中华游保障计划A");
		discountProdsMap1.put("ProdCode", "100101001");
		discountProdsMap1.put("ProdLogo", Config.getValue("ProductURL")+"//eriskfile/ERiskPic/100101001.jpg");
		discountProdsMap1.put("OriginalPremium", "30元");
		discountProdsMap1.put("DiscPremium", "25元");
		
		JSONArray discountProdsInfo = new JSONArray();
		discountProdsInfo.put(discountProdsMap1);
		discountProdsInfo.put(discountProdsMap);
		mainPageInfoMap.put("DiscountProds", discountProdsInfo);
		
		Map<String,Object> recommendProdsMap = new HashMap<String,Object>();
		recommendProdsMap.put("ProdName", "中国人寿中华游保障计划A");
		recommendProdsMap.put("ProdCode", "100101001");
		recommendProdsMap.put("ProdLogo", Config.getValue("ProductURL")+"//eriskfile/ERiskPic/100101001.jpg");
		recommendProdsMap.put("Premium", "25");
		Map<String,Object> recommendProdsMap1 = new HashMap<String,Object>();
		recommendProdsMap1.put("ProdName", "中国人寿中华游保障计划A");
		recommendProdsMap1.put("ProdCode", "100101001");
		recommendProdsMap1.put("ProdLogo", Config.getValue("ProductURL")+"//eriskfile/ERiskPic/100101001.jpg");
		recommendProdsMap1.put("Premium", "25");
		Map<String,Object> recommendProdsMap2 = new HashMap<String,Object>();
		recommendProdsMap2.put("ProdName", "中国人寿中华游保障计划A");
		recommendProdsMap2.put("ProdCode", "100101001");
		recommendProdsMap2.put("ProdLogo", Config.getValue("ProductURL")+"//eriskfile/ERiskPic/100101001.jpg");
		recommendProdsMap2.put("Premium", "25");
		
		JSONArray recommendProdsInfo = new JSONArray();
		recommendProdsInfo.put(recommendProdsMap2);
		recommendProdsInfo.put(recommendProdsMap1);
		recommendProdsInfo.put(recommendProdsMap);
		mainPageInfoMap.put("RecommendProds", recommendProdsInfo);
		
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("USER", "kxb");
		resultMap.put("REQUESTTYPE", "KXBJRT0002");
		resultMap.put("STATYS", "true");
		resultMap.put("RESULTS",mainPageInfoMap);
		return resultMap;
	}
	public static Map<String,Object> wapGetProductFilters(){
		
		Map<String,Object> filtersMap = new HashMap<String,Object>();
		filtersMap.put("ModuleName", "保障年龄");
		filtersMap.put("ModuleID", "Age");
		filtersMap.put("SelectType", "1");
		
		Map<String,Object> filterItemsMap = new HashMap<String,Object>();
		filterItemsMap.put("ItemName", "1岁-17岁");
		filterItemsMap.put("ItemValue", "1D-17D");
		filterItemsMap.put("SelectOption", "1");
		Map<String,Object> filterItemsMap2 = new HashMap<String,Object>();
		filterItemsMap2.put("ItemName", "18岁-80岁");
		filterItemsMap2.put("ItemValue", "18D-80D");
		filterItemsMap2.put("SelectOption", "1");
		
		JSONArray filterItemInfo = new JSONArray();
		filterItemInfo.put(filterItemsMap);
		filterItemInfo.put(filterItemsMap2);
		filtersMap.put("FilterItems", filterItemInfo);
		
		Map<String,Object> filtersMap1 = new HashMap<String,Object>();
		filtersMap1.put("ModuleName", "出行地区");
		filtersMap1.put("ModuleID", "Area");
		filtersMap1.put("SelectType", "1");
		
		Map<String,Object> filterItemsMap3 = new HashMap<String,Object>();
		filterItemsMap3.put("ItemName", "国内");
		filterItemsMap3.put("ItemValue", "A01");
		filterItemsMap3.put("SelectOption", "1");
		Map<String,Object> filterItemsMap4 = new HashMap<String,Object>();
		filterItemsMap4.put("ItemName", "境外");
		filterItemsMap4.put("ItemValue", "A02");
		filterItemsMap4.put("SelectOption", "1");
		
		JSONArray filterItemInfo1 = new JSONArray();
		filterItemInfo1.put(filterItemsMap3);
		filterItemInfo1.put(filterItemsMap4);
		filtersMap1.put("FilterItems", filterItemInfo1);
		
		JSONArray filtersInfo = new JSONArray();
		filtersInfo.put(filtersMap1);
		filtersInfo.put(filtersMap);
		
		Map<String,Object> productFiltersMap = new HashMap<String,Object>();
		productFiltersMap.put("Filters", filtersInfo);
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("USER", "kxb");
		resultMap.put("REQUESTTYPE", "KXBJRT0008");
		resultMap.put("STATYS", "true");
		resultMap.put("RESULTS",productFiltersMap);
		
		return resultMap;
	}
	public static Map<String,Object> wapGetAreaInfo(){
		
		Map<String,Object> areaInfoMap = new HashMap<String,Object>();
		
		/*if(""==""){
			String[] arr = new String[]{"A000001"};
			return WapErrorUtil.dealErrorInfo(arr, "KXBJRT0008");
		}*/
		
		Map<String,Object> areaSubMap = new HashMap<String,Object>();
		areaSubMap.put("Name", "长春");
		areaSubMap.put("Code", "JL001001");
		areaSubMap.put("ParentCode", "JL001");
		Map<String,Object> areaSubMap1 = new HashMap<String,Object>();
		areaSubMap1.put("Name", "吉林");
		areaSubMap1.put("Code", "JL001001");
		areaSubMap1.put("ParentCode", "JL002");
		Map<String,Object> areaSubMap2 = new HashMap<String,Object>();
		areaSubMap2.put("Name", "白城");
		areaSubMap2.put("Code", "JL001003");
		areaSubMap2.put("ParentCode", "JL001");
		
		JSONArray areaSubInfo = new JSONArray();
		areaSubInfo.put(areaSubMap2);
		areaSubInfo.put(areaSubMap1);
		areaSubInfo.put(areaSubMap);
		
		areaInfoMap.put("AreaInfos", areaSubInfo);
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("USER", "kxb");
		resultMap.put("REQUESTTYPE", "KXBJRT0012");
		resultMap.put("STATYS", "true");
		resultMap.put("RESULTS",areaInfoMap);
		
		return resultMap;
	}
    public static Map<String,Object> wapGetOccuInfo(){
		
		Map<String,Object> OccuInfoMap = new HashMap<String,Object>();
		
		Map<String,Object> OccuSubMap = new HashMap<String,Object>();
		OccuSubMap.put("Name", "教師");
		OccuSubMap.put("Code", "JL001001");
		OccuSubMap.put("ParentCode", "JL001");
		Map<String,Object> OccuSubMap1 = new HashMap<String,Object>();
		OccuSubMap1.put("Name", "學生");
		OccuSubMap1.put("Code", "JL001001");
		OccuSubMap1.put("ParentCode", "JL002");
		Map<String,Object> OccuSubMap2 = new HashMap<String,Object>();
		OccuSubMap2.put("Name", "科員");
		OccuSubMap2.put("Code", "JL001003");
		OccuSubMap2.put("ParentCode", "JL001");
		
		JSONArray OccuSubInfo = new JSONArray();
		OccuSubInfo.put(OccuSubMap2);
		OccuSubInfo.put(OccuSubMap1);
		OccuSubInfo.put(OccuSubMap);
		
		OccuInfoMap.put("OccupationInfos", OccuSubInfo);
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("USER", "kxb");
		resultMap.put("REQUESTTYPE", "KXBJRT0013");
		resultMap.put("STATYS", "true");
		resultMap.put("RESULTS",OccuInfoMap);
		
		return resultMap;
	}
    public static Map<String,Object> wapGetErrorInfo(){
    	
    	JSONArray errorsInfo = new JSONArray();
    	Map<String,Object> errorMap = new HashMap<String,Object>();
    	errorMap.put("A00000001", "用户不存在");
    	Map<String,Object> errorMap1 = new HashMap<String,Object>();
    	errorMap1.put("A00000002", "密码错误");
    	Map<String,Object> errorMap2 = new HashMap<String,Object>();
    	errorMap2.put("A00000003", "用户名与密码不匹配");
    	
    	errorsInfo.put(errorMap);
    	errorsInfo.put(errorMap1);
    	errorsInfo.put(errorMap2);
    	
    	Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("USER", "kxb");
		resultMap.put("REQUESTTYPE", "KXBJRT0013");
		resultMap.put("STATYS", "false");
		resultMap.put("ERRORS",errorsInfo);
		
		return resultMap;
    }
    /**
     * 保存投保要素
     * @param cMap
     * @param order
     * @param infor
     * @param ins
     * @return
     */
    public List<SDInformationInsuredElements> getInfomationInsuredElements(Map<String,Object>cMap,SDOrder order,SDInformation infor,SDInformationInsured ins){
    	
        List<OrderRiskAppFactor> riskAppFactior = new ArrayList<OrderRiskAppFactor>();// 责任投保要素列表
        List<SDInformationInsuredElements> tInformationInsuredElementsList = new ArrayList<SDInformationInsuredElements>();
        if (riskAppFactior != null) {
			for (int i = 0; i < riskAppFactior.size(); i++) {
				String appFactiorType = riskAppFactior.get(i).getFactorType().toString();
				Object factorValueTemp = "";
				SDInformationInsuredElements informationInsuredElements = new SDInformationInsuredElements();
				factorValueTemp = cMap.get(appFactiorType);
				if (!"TextAge".equals(appFactiorType) && riskAppFactior != null && riskAppFactior.get(i) != null) {

					informationInsuredElements.setElementsType(appFactiorType);
					informationInsuredElements.setElementsSn(PubFun.GetSDElementSn());
					informationInsuredElements.setOrderSn(order.getOrderSn());
					informationInsuredElements.setRecognizeeSn(ins.getRecognizeeSn());
					informationInsuredElements.setSdinformationInsured(ins);
					informationInsuredElements.setInformationSn(infor.getInformationSn());
					informationInsuredElements.setElementsSn(riskAppFactior.get(i).getAppFactorCode());
					if (factorValueTemp != null)
						informationInsuredElements.setElementsValue(factorValueTemp.toString());
				}
				if(informationInsuredElements!=null){
					tInformationInsuredElementsList.add(informationInsuredElements);
				}
				
			}
		}
        
        
        return tInformationInsuredElementsList;
    }
    /**
     * 保存责任项
     * @param order
     * @param infor
     * @return
     */
    public List<SDInformationDuty> getInformationDuty(SDOrder order,SDInformation infor){
    	
    	List<SDInformationDuty> tInformationDutyList = new ArrayList<SDInformationDuty>();
        List<OrderDutyFactor> dutyFactor = new ArrayList<OrderDutyFactor>();// 产品投保要素列表
    	if (dutyFactor != null) {
			for (int i = 0; i < dutyFactor.size(); i++) {
				SDInformationDuty sdinformationduty = new SDInformationDuty();

				sdinformationduty.setDutySn(dutyFactor.get(i).getDutyCode());// 责任编码
				sdinformationduty.setInformationSn(infor.getInformationSn());// 订单明细表编码
				sdinformationduty.setOrderSn(order.getOrderSn());// 保单号
				sdinformationduty.setDutyName(dutyFactor.get(i).getDudtyFactorName());// 责任名称
				sdinformationduty.setDutyFullName(dutyFactor.get(i).getDutyFullName());// 责任全称
				sdinformationduty.setCoverage(dutyFactor.get(i).getDefine());
				sdinformationduty.setRiskCode("");// 险种编码
				String cPlanCode = infor.getPlanCode();
				Object showDutyValue = "";
				Object premDutyValue = "";
				Object factorValueTemp = "";
				for(FEMDutyAmntPremList fEMDutyAmntPremList : dutyFactor.get(i).getFdAmntPremList()){
					if (cPlanCode != null && !"".equals(cPlanCode)) {
						if (cPlanCode.equals(fEMDutyAmntPremList.getAppFactorValue())) {
							showDutyValue = fEMDutyAmntPremList.getAmnt();
							premDutyValue = fEMDutyAmntPremList.getPrem();
							factorValueTemp = fEMDutyAmntPremList.getBackUp1();
						}
					} else {
						showDutyValue = fEMDutyAmntPremList.getAmnt();
						premDutyValue = fEMDutyAmntPremList.getPrem();
						factorValueTemp = fEMDutyAmntPremList.getBackUp1();
					}
				}
				if (!"nvalue".equals(showDutyValue)) {
					sdinformationduty.setShowAmnt(showDutyValue.toString());
				} else {
					sdinformationduty.setShowAmnt("0.0");// 显示值
				}
				if (!"nvalue".equals(premDutyValue)) {
					sdinformationduty.setPremium(premDutyValue + "");
				} else {
					sdinformationduty.setPremium("0.0");// 保费
				}
				sdinformationduty.setAmt(factorValueTemp.toString());// 计算值保额
				sdinformationduty.setMainRiskFlag(dutyFactor.get(i).getCurrency());// 主险标志
				sdinformationduty.setSupplierDutyCode(dutyFactor.get(i).getSupplierDutyCode());// 保险公司责任/险别编码
				sdinformationduty.setSdinformation(infor);// 级联保存订单明细表信息

				tInformationDutyList.add(sdinformationduty);
			}
		}
    	
    	return tInformationDutyList;
    	
    }
    
    public void getDutyInfo(List<OrderDutyFactor> dutyFactor2, String cPlanCode){

		List<OrderDutyFactor> dutyFactor = new ArrayList<OrderDutyFactor>();
		if (dutyFactor2.size() > 0) {
			for (OrderDutyFactor orderDutyFactor : dutyFactor2) {

				for (FEMDutyAmntPremList fEMDutyAmntPremList : orderDutyFactor.getFdAmntPremList()) {
					if (fEMDutyAmntPremList != null) {
						// 根据计划选择责任的保额
						if (cPlanCode != null && !"".equals(cPlanCode)) {
							if (cPlanCode.equals(fEMDutyAmntPremList.getAppFactorValue())) {
								dutyFactor.add(orderDutyFactor);
							}
						} else {
							dutyFactor.add(orderDutyFactor);
						}

					}
				}
				if (orderDutyFactor.getFdAmntPremList() == null || orderDutyFactor.getFdAmntPremList().size()<=0|| orderDutyFactor.getFdAmntPremList().get(0) == null) {
					FEMDutyAmntPremList fEMDutyAmntPremList = new FEMDutyAmntPremList();
					List<FEMDutyAmntPremList> fdap = new ArrayList<FEMDutyAmntPremList>();
					fdap.add(fEMDutyAmntPremList);
					orderDutyFactor.setFdAmntPremList(fdap);
				}
			}
		}
    }
    /**
     * 支付前确认
     * @param orderService
     * @return
     */
    public Map<String,Object> checkPayInfo(SDOrderService orderService){
    	SimpleDateFormat sdf_1 = new SimpleDateFormat("yyyy-MM-dd");
    	String cOrderSn = "";
    	String cAmnt = "";
    	SDOrder order = orderService.getOrderByOrderSn(cOrderSn);
    	SDInformation information = order.getSdinformationSet().iterator().next();
    	SDInformationAppnt appnt = information.getSdinformationappntSet().iterator().next();
    	Map<String,Object> payResultMap = new HashMap<String,Object>();
    	//是过期
    	try {
			if(sdf_1.parse(sdf_1.format(information.getStartDate())).getTime()<=sdf_1.parse(sdf_1.format(new Date())).getTime()){
				
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
		}
    	//保费校验
    	
    	//支付金额
    	if(!cAmnt.equals(order.getTotalAmount().toString())){
    		
    	}
    	//是否已支付
    	if (String.valueOf(order.getOrderStatus().ordinal()).equals("7")) {
    		
    	}
    	//订单是否已取消
    	if (String.valueOf(order.getOrderStatus().ordinal()).equals("3") || String.valueOf(order.getOrderStatus().ordinal()).equals("8")){
    		
    	}
    	//已撤单
    	
    	//限购
    	if(!isProductDownShelf(information)){
    		
    	}
    	//发送待支付邮件
        Map<String, Object> sdinteraction = new HashMap<String, Object>();// 横向接口调用参数
    	Member member = new Member();
		String applicantName = appnt.getApplicantName();
		member.setEmail(appnt.getApplicantMail());
		ActionUtil actionUtil = new ActionUtil();
		sdinteraction.put("Member", member);
		sdinteraction.put("MemberName", applicantName);
		sdinteraction.put("OrderSn", order.getOrderSn());
		sdinteraction.put("ProductName", information.getProductName());
		//sdinteraction.put("path", (path + "?orderSn=" + order.getOrderSn() + "&payType=0").replace("&", "#"));
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("paySn", order.getPaySn());
		ActionUtil.sendMessage("wj00015", data);
    	return payResultMap;
    	
    }
    
    /**
     * 支付信息同步
     * @param orderService
     */
   /* public void dealPayInfo(SDOrderService orderService){
    	
    	//处理订单表数据（需要生成结算单号）
    	结算单号 String paySn = PubFun.GetPaySn(money, "W")
    			sdorders.paySN
    			支付状态
    			sdorders.orderstatus
    			paystatus
    	//处理交易表数据（需要生成结算单号）
    			支付流水号
    			结算单号 tradeinformation.tradeCheckSeriNo
    	//调用经带通处理承保
    	
    }*/
    @SuppressWarnings("unused")
	private boolean isProductDownShelf(SDInformation sdinformation) {
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
			return false;
		}
		String sql = "select LimitCount,Occup,SectionAge,IsPublish from sdproduct where productid=?";
		String[] sqltemp = { productId };
		GetDBdata db = new GetDBdata();
		List<HashMap<String, String>> sdproduct;
		try {
			sdproduct = db.query(sql, sqltemp);
			if (sdproduct == null || sdproduct.size() != 1) {
				return false;
			}
			HashMap<String, String> product = sdproduct.get(0);
			String IsPublish = product.get("IsPublish");
			if (StringUtil.isEmpty(IsPublish) || "N".equals(IsPublish)) {
				return false;
			}
			String qixianSql = "select startDate, endDate from sdinformation where id =? ";
			String[] qixianSqltemp = { sdinformation.getId() };
			List<Map<String, Object>> qixain = db.queryObj(qixianSql,
					qixianSqltemp);
			if (qixain == null || qixain.size() != 1) {
				return false;
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
				return false;
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
							+ "and c.appStatus='1' and (('"
							+ qxfail
							+ "'>=a.startDate and a.startDate>='"
							+ qxeffective
							+ "') or ('"
							+ qxeffective
							+ "'<=a.endDate and a.endDate<='"
							+ qxfail
							+ "')) "
							+ "and b.recognizeeIdentityType=? and b.recognizeeIdentityId=? and a.productId=?";
					String[] backupTemp = { insuredIdType, insuredIdNo,
							productId };
					logger.info("保险公司限购份数（sql）：{}", backup_);
					String backup = db.getOneValue(backup_, backupTemp);
					logger.info("保险公司限购份数的值：{} -- {}", backup, LimitCount);
					if (StringUtil.isNotEmpty(backup)) {
						if (Integer.parseInt(backup) >= Integer
								.parseInt(LimitCount)) {
							return false;
						}
					}
				}
			}
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			return false;
		}
	}
	public static void main1(String[] args) {
		// TODO Auto-generated method stub
		/*JSONObject obj = new JSONObject(WapTestUtil.wapGetApplicationInfo());
		
		System.out.println(obj.toString());
		try {
			Map<String,Object> mapAll= (HashMap<String,Object>)obj.get("RESULTS");
			JSONArray  arr= (JSONArray)mapAll.get("HolderInfo");
			arr.get(0).toString();
			JSONObject t = new JSONObject(arr.get(0).toString());
			 
			System.out.println(t.get("AppntEName"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
		}*/
		JSONArray errorsInfo = new JSONArray();
    	Map<String,Object> errorMap = new HashMap<String,Object>();
    	errorMap.put("A00000001", "用户不存在");
    	Map<String,Object> errorMap1 = new HashMap<String,Object>();
    	errorMap1.put("A00000002", "密码错误");
    	Map<String,Object> errorMap2 = new HashMap<String,Object>();
    	errorMap2.put("A00000003", "用户名与密码不匹配");
    	
    	errorsInfo.put(errorMap);
    	errorsInfo.put(errorMap1);
    	errorsInfo.put(errorMap2);
//    	try {
//			System.out.println(errorsInfo.toString());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			logger.error(e.getMessage(), e);
//		}

	}
	
	
	public static void main(String[] args) {

          
		//System.out.println(wapGetApplicationInfo());
		
		//Date a  = new Date();
		SimpleDateFormat sdf_2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date a;
		try {
			a = sdf_2.parse("2014-02-08 10:10:10");
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("date", a);
			SDInformationInsured tSDInformationInsured = new  SDInformationInsured();
			tSDInformationInsured.setFlightTime(map.get("date").toString());
//			System.out.println(tSDInformationInsured.getFlightTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
		}
		
	}

}
