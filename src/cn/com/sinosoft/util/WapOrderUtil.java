package cn.com.sinosoft.util;

import cn.com.sinosoft.entity.SDInformation;
import cn.com.sinosoft.entity.SDInformationAppnt;
import cn.com.sinosoft.entity.SDInformationInsured;
import cn.com.sinosoft.entity.SDInformationProperty;
import cn.com.sinosoft.entity.SDOrder;
import cn.com.sinosoft.entity.SDOrder.SDOrderStatus;
import cn.com.sinosoft.service.AreaService;
import cn.com.sinosoft.service.DictionaryService;
import cn.com.sinosoft.service.OccupationService;
import cn.com.sinosoft.service.SDInformationAppntService;
import cn.com.sinosoft.service.SDInformationInsuredService;
import cn.com.sinosoft.service.SDInformationService;
import cn.com.sinosoft.service.SDOrderService;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.StringUtil;
import org.hibernate.ObjectNotFoundException;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * wap订单接口工具类
 */
public class WapOrderUtil {
	private static final Logger logger = LoggerFactory.getLogger(WapOrderUtil.class);

	/** 
	 * wap订单信息查询接口
	 * @return
	 */
	public static Map<String,Object> wapGetApplicationInfo(Map<String,Object> parameters,SDOrderService sdorderService,SDInformationService sdinformationService,
			SDInformationInsuredService sdinformationInsuredService,SDInformationAppntService sdinformationAppntService,
			AreaService areaService,OccupationService occupationService,DictionaryService dictionaryService){
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		String orderSn = (String) parameters.get("OrderNumber");
		SimpleDateFormat sdf_1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(StringUtil.isEmpty(orderSn)){ 
			String[] errorArr = {"G000002"}; 
			resultMap = WapErrorUtil.dealErrorInfo(errorArr, "KXBJRT0022");
			return resultMap;
		}
		
		try{
			// 订单表
			SDOrder sdorder = sdorderService.getOrderByOrderSn(orderSn);
			
			// 订单详细表
			SDInformation sdinformation = null;
			Set<SDInformation> tSDInformationSet = sdorder.getSdinformationSet();
			for (SDInformation tSDInformation : tSDInformationSet) {
				sdinformation = tSDInformation;
			}
			
			resultMap.put("USER", "kxb");
			resultMap.put("REQUESTTYPE", "KXBJRT0022");
			
			if (sdorder == null || sdinformation == null) {
				String[] errorArr = {"OrderAction000001"};
				resultMap = WapErrorUtil.dealErrorInfo(errorArr, "KXBJRT0022");
				return resultMap;
			}
	
			// 投保人信息
			Set<SDInformationAppnt> tSDInformationAppnt = sdinformation.getSdinformationappntSet();
			
			// 被保人信息
			Set<SDInformationInsured> tSDInformationInsured = sdinformation.getSdinformationinsuredSet();
			
			//家财信息
			SDInformationProperty tSDInformationProperty = null;
			// 旅游目的地
			String insuredToCountry = "";
			for (SDInformationInsured t : tSDInformationInsured) {
				//家财信息
				tSDInformationProperty = t.getSdinformationproperty();
				break;
			}
			
			Map<String,Object> policyInfoMap = new HashMap<String,Object>();
			policyInfoMap.put("OrderNumber", orderSn);
			policyInfoMap.put("OrderStatus", sdorder.getOrderStatus().ordinal());
			policyInfoMap.put("ProdClass", "");
			policyInfoMap.put("ProdType", "");
			policyInfoMap.put("CompanyID", sdinformation.getInsuranceCompany());
			policyInfoMap.put("ProdCode", sdinformation.getProductId());
			policyInfoMap.put("ProdName", sdinformation.getProductName());
			policyInfoMap.put("Plan", sdinformation.getPlanCode());
			policyInfoMap.put("PlanName", sdinformation.getPlanName());
			policyInfoMap.put("Period", sdinformation.getEnsure());
			policyInfoMap.put("PeriodName", sdinformation.getEnsureDisplay());
			policyInfoMap.put("AppType", sdinformation.getAppType());
			policyInfoMap.put("AppTypeName", sdinformation.getAppTypeName());
			policyInfoMap.put("FeeYear", sdinformation.getChargeYear());
			policyInfoMap.put("FeeYearName", sdinformation.getChargeYearName());
			policyInfoMap.put("AppLevel", "");
			policyInfoMap.put("Grade", "");
			policyInfoMap.put("TextAge", "");
			policyInfoMap.put("Sex", "");
			/*policyInfoMap.put("BenefitTerm", sdinformation.getEnsureDisplay());
			policyInfoMap.put("PaymentMode", sdinformation.getChargeDisplay());
			policyInfoMap.put("PaymentTerm", sdinformation.getChargeYearName());*/
			policyInfoMap.put("PolicyDateFrom", DateUtil.toDateTimeString(sdinformation.getStartDate()));
			policyInfoMap.put("PolicyDateTo", DateUtil.toDateTimeString(sdinformation.getEndDate()));
			//policyInfoMap.put("ProdPlan", sdinformation.getPlanName());
			//购买份数默认为1
			policyInfoMap.put("BuyCopies", "1");
			policyInfoMap.put("Premium", String.valueOf(new BigDecimal(sdorder.getTotalAmount().toString()).setScale(2,BigDecimal.ROUND_HALF_UP)));
			policyInfoMap.put("PrePremium", "");
			policyInfoMap.put("Amnt", "");
			policyInfoMap.put("CouponAmount", sdorder.getOrderCoupon());
			policyInfoMap.put("PointAmount", sdorder.getOrderIntegral());
			policyInfoMap.put("ActiveAmount", sdorder.getOrderActivity());
			//返回修改使用
			policyInfoMap.put("ActiveDisRate", WapCommonUtil.getDisRate(sdinformation.getProductId(), sdorder.getChannelsn()));
			policyInfoMap.put("ActualAmountPaid", new BigDecimal(sdorder.getPayPrice()));
			
			boolean occupationFlag = true;
			JSONArray arrAppntInfo = new JSONArray();
			for(SDInformationAppnt sa: tSDInformationAppnt){
				//投保人信息
				Map<String,Object> appntInfoMap = new HashMap<String,Object>();
				appntInfoMap.put("AppntArea1Name", areaService.getAreaName(StringUtil.noNull(sa.getApplicantArea1())));
				appntInfoMap.put("AppntArea1Code", sa.getApplicantArea1());
				appntInfoMap.put("AppntArea2Name", areaService.getAreaName(StringUtil.noNull(sa.getApplicantArea2())));
				appntInfoMap.put("AppntArea2Code", sa.getApplicantArea2());
				//HIBERNATE映射错误的特殊处理
				try{
					appntInfoMap.put("AppntArea3Name", areaService.getAreaName(StringUtil.noNull(sa.getApplicantArea3())));
				}catch(ObjectNotFoundException e){
					appntInfoMap.put("AppntArea3Name", "");
				}
				appntInfoMap.put("AppntArea3Code", sa.getApplicantArea3());
				appntInfoMap.put("AppntOccupation1Name", occupationService.getOccupationName(StringUtil.noNull(sa.getApplicantOccupation1())));
				appntInfoMap.put("AppntOccupation1Code", sa.getApplicantOccupation1());
				//针对主站二级职业的特殊存储方式（职业一、职业三），HIBERNATE映射错误的特殊处理
				try{
					appntInfoMap.put("AppntOccupation2Name", occupationService.getOccupationName(StringUtil.noNull(sa.getApplicantOccupation2())));
				}catch(ObjectNotFoundException e){
					occupationFlag  = false;
				}
				if(occupationFlag){
					appntInfoMap.put("AppntOccupation2Code",sa.getApplicantOccupation2());
					appntInfoMap.put("AppntOccupation3Name",occupationService.getOccupationName(StringUtil.noNull(sa.getApplicantOccupation3())));
					appntInfoMap.put("AppntOccupation3Code",sa.getApplicantOccupation3());
				}else{
					appntInfoMap.put("AppntOccupation2Name", occupationService.getOccupationName(StringUtil.noNull(sa.getApplicantOccupation3())));
					appntInfoMap.put("AppntOccupation2Code",sa.getApplicantOccupation3());
					appntInfoMap.put("AppntOccupation3Name","");
					appntInfoMap.put("AppntOccupation3Code",null);
				}
				appntInfoMap.put("AppntBirthday", sa.getApplicantBirthday());
				appntInfoMap.put("AppntName", sa.getApplicantName());
				appntInfoMap.put("AppnttGender", sa.getApplicantSexName());
				appntInfoMap.put("AppnttGenderCode", sa.getApplicantSex());
				appntInfoMap.put("AppntMobile", sa.getApplicantMobile());
				appntInfoMap.put("AppntEmail", sa.getApplicantMail());
				appntInfoMap.put("AppntAddress", sa.getApplicantAddress());
				appntInfoMap.put("AppntEName", sa.getApplicantEnName());
				appntInfoMap.put("AppntPayperStartDate", sa.getApplicantStartID());
				appntInfoMap.put("AppntPayperEndDate", sa.getApplicantEndID());
				appntInfoMap.put("AppntPayperType", sa.getApplicantIdentityTypeName());
				appntInfoMap.put("AppntPayperTypeCode", sa.getApplicantIdentityType());
				appntInfoMap.put("AppntPayperNumber", sa.getApplicantIdentityId());
				if("000000".equals(sa.getApplicantZipCode())){
					appntInfoMap.put("AppntPostCode", "");
				}else{
					appntInfoMap.put("AppntPostCode", sa.getApplicantZipCode());
				}
				
				arrAppntInfo.put(appntInfoMap);
				
			}
			
			JSONArray arrInsuredInfo = new JSONArray();
			for(SDInformationInsured si: tSDInformationInsured){
				//被保人信息
				Map<String,Object> insuredInfoMap = new HashMap<String,Object>();

				insuredInfoMap.put("RecognizeeRelationShip", si.getRecognizeeAppntRelationName());
				insuredInfoMap.put("RecognizeeRelationShipCode", si.getRecognizeeAppntRelation());
				if(StringUtil.isNotEmpty(si.getRecognizeePrem())){
					insuredInfoMap.put("RecognizeePrem", String.valueOf(new BigDecimal(si.getRecognizeePrem().toString()).setScale(2,BigDecimal.ROUND_HALF_UP)));
				}else{
					insuredInfoMap.put("RecognizeePrem","0.00");
				}
				if(StringUtil.isNotEmpty(si.getRecognizeeTotalPrem())){
					insuredInfoMap.put("ProductPrice", String.valueOf(new BigDecimal(si.getRecognizeeTotalPrem().toString()).setScale(2,BigDecimal.ROUND_HALF_UP)));
				}else{
					insuredInfoMap.put("ProductPrice","0.00");
				}
				//add by wangej 20150818  接口定义，但是数据没有传
				if(StringUtil.isNotEmpty(si.getDiscountPrice())){
					insuredInfoMap.put("RecognizeeActivePrem", String.valueOf(new BigDecimal(si.getDiscountPrice().toString()).setScale(2,BigDecimal.ROUND_HALF_UP)));
				}else{
					insuredInfoMap.put("RecognizeeActivePrem","0.00");
				}
				insuredInfoMap.put("IsHolderSelf", si.getIsSelf());
				insuredInfoMap.put("RecognizeeArea1Name", areaService.getAreaName(StringUtil.noNull(si.getRecognizeeArea1())));
				insuredInfoMap.put("RecognizeeArea1Code", si.getRecognizeeArea1());
				insuredInfoMap.put("RecognizeeArea2Name", areaService.getAreaName(StringUtil.noNull(si.getRecognizeeArea2())));
				insuredInfoMap.put("RecognizeeArea2Code", si.getRecognizeeArea2());
				try{
					insuredInfoMap.put("RecognizeeArea3Name", areaService.getAreaName(StringUtil.noNull(si.getRecognizeeArea3())));
				}catch(ObjectNotFoundException e){
					insuredInfoMap.put("RecognizeeArea3Name", "");
				}
				insuredInfoMap.put("RecognizeeArea3Code", si.getRecognizeeArea3());
				insuredInfoMap.put("RecognizeeOccupation1Name", occupationService.getOccupationName(StringUtil.noNull(si.getRecognizeeOccupation1())));
				insuredInfoMap.put("RecognizeeOccupation1Code", si.getRecognizeeOccupation1());
				//针对主站二级职业的特殊存储方式（职业一、职业三），HIBERNATE映射错误的特殊处理
				try{
					insuredInfoMap.put("RecognizeeOccupation2Name", occupationService.getOccupationName(StringUtil.noNull(si.getRecognizeeOccupation2())));
				}catch(ObjectNotFoundException e){
					occupationFlag  = false;
				}
				if(occupationFlag){ 
					insuredInfoMap.put("RecognizeeOccupation2Code", si.getRecognizeeOccupation2());
					insuredInfoMap.put("RecognizeeOccupation3Name", occupationService.getOccupationName(StringUtil.noNull(si.getRecognizeeOccupation3())));
					insuredInfoMap.put("RecognizeeOccupation3Code", si.getRecognizeeOccupation3());
				}else{
					insuredInfoMap.put("RecognizeeOccupation2Name", occupationService.getOccupationName(StringUtil.noNull(si.getRecognizeeOccupation3())));
					insuredInfoMap.put("RecognizeeOccupation2Code", si.getRecognizeeOccupation3());
					insuredInfoMap.put("RecognizeeOccupation3Name", "");
					insuredInfoMap.put("RecognizeeOccupation3Code", null);
				}
				insuredInfoMap.put("RecognizeeBirthday", si.getRecognizeeBirthday());
				insuredInfoMap.put("RecognizeeNational", getNationalityDisplay((String)si.getNationality(),sdinformation.getInsuranceCompany()));
				insuredInfoMap.put("RecognizeeOverseasOccupation", si.getOverseasOccupation());
				insuredInfoMap.put("RecognizeeName", si.getRecognizeeName());
				String comCode = sdinformation.getInsuranceCompany();
				insuredInfoMap.put("RecognizeeStudentCode", si.getDriverNo());
				if(!StringUtil.isEmpty(si.getHaveBuy())){
					if((comCode.equals("2042"))){
						insuredInfoMap.put("RecognizeeHasBuyLifeCovAmount", si.getHaveBuy()+"元");
					}else{
						insuredInfoMap.put("RecognizeeHasBuyLifeCovAmount", si.getHaveBuy()+"万元");
					}
				}
				//mod by wangenjian 20151217 wap站多被保人每个被保人的目的地都支持选择，需要单独拼串 
				if((si.getDestinationCountry()!=null&&!"".equals(si.getDestinationCountry())) && (si.getDestinationCountryText()!=null&&!"".equals(si.getDestinationCountryText()))){
					insuredToCountry = sdinformationInsuredService.getInsuredToCountry(si);
				}
				
				insuredInfoMap.put("RecognizeeGenderCode", si.getRecognizeeSex());
				insuredInfoMap.put("RecognizeeGenderName", si.getRecognizeeSexName()); 
				insuredInfoMap.put("RecognizeeMobile", si.getRecognizeeMobile());
				insuredInfoMap.put("RecognizeeTravelDestinationInput", String.valueOf(si.getDestinationCountry()));
				insuredInfoMap.put("RecognizeeTravelDestinationInputName", insuredToCountry);
				insuredInfoMap.put("RecognizeeTravelDestinationSel", String.valueOf(si.getDestinationCountry()));
				insuredInfoMap.put("RecognizeeTravelDestinationSelName", insuredToCountry);
				insuredInfoMap.put("RecognizeeEmail", si.getRecognizeeMail());
				insuredInfoMap.put("RecognizeeStudyAbroadCompany", si.getSchoolOrCompany());
				insuredInfoMap.put("RecognizeeAddress", si.getRecognizeeAddress());
				insuredInfoMap.put("FlightNumber", si.getFlightNo());
				insuredInfoMap.put("RecognizeeEName", si.getRecognizeeEnName());
				insuredInfoMap.put("RecognizeePayperStartDate", si.getRecognizeeStartID());
				insuredInfoMap.put("RecognizeePayperEndDate", si.getRecognizeeEndID());
				insuredInfoMap.put("RecognizeePayperType", si.getRecognizeeIdentityTypeName());
				insuredInfoMap.put("RecognizeePayperTypeCode", si.getRecognizeeIdentityType());
				insuredInfoMap.put("RecognizeePayperNumber", si.getRecognizeeIdentityId());
				insuredInfoMap.put("BuyCopies", si.getRecognizeeMul());
				insuredInfoMap.put("TakeOffLocation", si.getFlightLocation());
				/*insuredInfoMap.put("TakeOffDate", StringUtil.isEmpty(si.getFlightTime())?si.getFlightTime():(si.getFlightTime()).split(" ")[0]);
				insuredInfoMap.put("TakeOffTime", StringUtil.isEmpty(si.getFlightTime())?si.getFlightTime():(si.getFlightTime()).split(" ")[1].replace(".0", ""));*/
				if(!StringUtil.isEmpty(si.getFlightTime())){
					try {
						insuredInfoMap.put("TakeOffTime", sdf_1.format(sdf_1.parse(si.getFlightTime())));
					} catch (ParseException e) {
						logger.error(e.getMessage(), e);
					}
				}else{ 
					insuredInfoMap.put("TakeOffTime", "");
				}
				
				insuredInfoMap.put("RecognizeeHeight", si.getHeight());
				insuredInfoMap.put("RecognizeeWeight", si.getWeight());
				if("000000".equals(si.getRecognizeeZipCode())){
					insuredInfoMap.put("RecognizeePostCode", "");
				}else{
					insuredInfoMap.put("RecognizeePostCode", si.getRecognizeeZipCode());
				}
				insuredInfoMap.put("RecognizeeDrivingSchoolName", si.getDriverSchoolName());
			
				arrInsuredInfo.put(insuredInfoMap);
				
			}
			// 家财信息
			JSONArray HourseInfo = new JSONArray();
			Map<String,Object> HourseMap = new HashMap<String,Object>();
			if(tSDInformationProperty!=null && StringUtil.isNotEmpty(tSDInformationProperty.getRecognizeeSn())){
				HourseMap.put("HourseType", tSDInformationProperty.getHourseType());
				HourseMap.put("HourseTypeName", new QueryBuilder(" SELECT codeName FROM dictionary WHERE id=? ",tSDInformationProperty.getHourseType()).executeString());
				HourseMap.put("HourseNo", tSDInformationProperty.getHourseNo());
				HourseMap.put("CarPlateNo", tSDInformationProperty.getCarPlateNo());
				HourseMap.put("LicenseNumber", tSDInformationProperty.getLicenseNumber());
				HourseMap.put("ClassisNumber", tSDInformationProperty.getChassisNumber());
				HourseMap.put("PropertyArea1", tSDInformationProperty.getPropertyArea1());
				HourseMap.put("PropertyArea1Name", areaService.getAreaName(StringUtil.noNull(tSDInformationProperty.getPropertyArea1())));
				HourseMap.put("PropertyArea2", tSDInformationProperty.getPropertyArea2());
				HourseMap.put("PropertyArea2Name", areaService.getAreaName(StringUtil.noNull(tSDInformationProperty.getPropertyArea2())));
				HourseMap.put("PropertyAddress", tSDInformationProperty.getPropertyAdress());
				HourseMap.put("PropertyZip", tSDInformationProperty.getPropertyZip());
				HourseMap.put("PropertyToRecognizee", tSDInformationProperty.getPropertyToRecognizee());
				HourseMap.put("PropertyToRecognizeeName", new QueryBuilder(" SELECT codeName FROM dictionary WHERE id=? ",tSDInformationProperty.getPropertyToRecognizee()).executeString());
				HourseMap.put("HourseAge", tSDInformationProperty.getHourseAge());
				HourseMap.put("HourseAgeName", new QueryBuilder(" SELECT codeName FROM dictionary WHERE id=? ",tSDInformationProperty.getHourseAge()).executeString());
				HourseInfo.put(HourseMap);
			}
			JSONArray CPSInfo = new JSONArray();
			Map<String,Object> CPSInfoMap = new HashMap<String,Object>();
			// CPSInfo
			QueryBuilder qb = new QueryBuilder(" SELECT os,cid,oo FROM cps WHERE `on` = ? ",orderSn);
			DataTable dt = qb.executeDataTable();
			if(dt!=null&&dt.getRowCount()>=1){
				CPSInfoMap.put("ChanneCode", dt.getString(0, "oo"));
				CPSInfoMap.put("CPSUserID", dt.getString(0, "cid"));
				CPSInfoMap.put("CPSUserSource", dt.getString(0, "os"));
			}else{
				CPSInfoMap.put("ChanneCode", "");
				CPSInfoMap.put("CPSUserID", "");
				CPSInfoMap.put("CPSUserSource", "");
			}
			CPSInfo.put(CPSInfoMap);
			policyInfoMap.put("HolderInfo", arrAppntInfo);
			policyInfoMap.put("InsuredInfos", arrInsuredInfo);
			policyInfoMap.put("CPSInfo", CPSInfo);
			policyInfoMap.put("HourseInfo", HourseInfo);
			resultMap.put("STATYS", "true");
			resultMap.put("RESULTS",policyInfoMap);
			
		}catch(Exception e){
			logger.error("wap站订单信息查询接口异常"+e.getMessage(), e);
			String[] errorArr = {"G000001"};
			resultMap = WapErrorUtil.dealErrorInfo(errorArr, "KXBJRT0022");
			return resultMap;
		}
		
		return resultMap;
	}
	
	/**
	 * wap取消订单接口
	 * @return
	 */
	public static Map<String,Object> wapCancelApplication(Map<String,Object> parameters,SDOrderService sdorderService){
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		
		String orderSn = (String)parameters.get("OrderNumber");
		
		if(StringUtil.isEmpty(orderSn)){
			String[] errorArr = {"G000002"};
			resultMap = WapErrorUtil.dealErrorInfo(errorArr, "KXBJRT0023");
			return resultMap;
		}
		// 是否已支付
		QueryBuilder qb = new QueryBuilder(" Select OrderStatus from sdorders where orderSn=? ",orderSn);
		if (String.valueOf(qb.executeString()).equals("7")) {
			String[] errorArr = {"wapShopping00003"};
			resultMap = WapErrorUtil.dealErrorInfo(errorArr, "KXBJRT0023");
			return resultMap;
		}
		try{
			
			SDOrder sdorder = sdorderService.getOrderByOrderSn(orderSn);
			
			if (sdorder == null) {
				String[] errorArr = {"OrderAction000001"};
				resultMap = WapErrorUtil.dealErrorInfo(errorArr, "KXBJRT0023");
				return resultMap;
			}
			
			resultMap.put("USER", "kxb");
			resultMap.put("REQUESTTYPE", "KXBJRT0023");
			resultMap.put("STATYS", "true");
			
			sdorder.setOrderStatus(SDOrderStatus.invalid);
			sdorderService.update(sdorder);
			
			Map<String,Object> cancelApplicationMap = new HashMap<String,Object>();
			cancelApplicationMap.put("OrderStatus", "已取消");
			resultMap.put("RESULTS", cancelApplicationMap);
			
		}catch(Exception e){
			logger.error("wap取消订单接口异常"+e.getMessage(), e);
			String[] errorArr = {"G000001"};
			resultMap = WapErrorUtil.dealErrorInfo(errorArr, "KXBJRT0023");
			return resultMap;
		}
		
		return resultMap;
	}
	
	/**
	 * 取得国籍信息
	 * @return
	 */
	public static String getNationalityDisplay(String nationalityCode,String insuranceCode) {
		
		GetDBdata db = new GetDBdata();
		String[] temp = {nationalityCode,insuranceCode};
		try {
			String display = db
			.getOneValue(
					"SELECT codeName FROM dictionary WHERE codeType='nationality' and codeValue = ? and insuranceCode = ?",
					temp);
			return display;
		} catch (Exception e) {
			return "";
		}
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	}
	
}
