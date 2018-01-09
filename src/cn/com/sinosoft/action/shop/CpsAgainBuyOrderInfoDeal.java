package cn.com.sinosoft.action.shop;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.sinosoft.entity.DirectPayBankInfo;
import cn.com.sinosoft.entity.GiftClassify;
import cn.com.sinosoft.entity.PointExchangeInfo;
import cn.com.sinosoft.entity.SDInformation;
import cn.com.sinosoft.entity.SDInformationAppnt;
import cn.com.sinosoft.entity.SDInformationDuty;
import cn.com.sinosoft.entity.SDInformationDutyTemp;
import cn.com.sinosoft.entity.SDInformationInsured;
import cn.com.sinosoft.entity.SDInformationInsuredElements;
import cn.com.sinosoft.entity.SDInformationProperty;
import cn.com.sinosoft.entity.SDInformationRiskType;
import cn.com.sinosoft.entity.SDInsuredHealth;
import cn.com.sinosoft.entity.SDOrder;
import cn.com.sinosoft.entity.SDOrder.SDOrderStatus;
import cn.com.sinosoft.entity.SDOrder.SDPayStatus;
import cn.com.sinosoft.entity.SDOrderItem;
import cn.com.sinosoft.entity.SDOrderItemOth;

import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;

public class CpsAgainBuyOrderInfoDeal { 

	/**
	 * 订单表
	 * 
	 * @param order
	 * @return
	 */
	public SDOrder dealSDorder(SDOrder order, String channelsn) {

		SDOrder newOrder = cloneSDorder(order, channelsn);

		return newOrder;
	}

	/**
	 * 订单对象克隆
	 * 
	 * @param order
	 * @return
	 */
	private SDOrder cloneSDorder(SDOrder order, String channelsn) {
		SDOrder newOrder = new SDOrder();

		// 订单暂存
		newOrder.setOrderStatus(SDOrderStatus.temptorysave);
		newOrder.setPayType(null);
		newOrder.setPayStatus(SDPayStatus.unpaid);
		newOrder.setPaySn(null);

		newOrder.setOffsetPoint("0");
		// 订单编号
		String newOrderSn = "";
//		if (StringUtil.isNotEmpty(order.getChannelsn()) && order.getChannelsn().indexOf("tb") >= 0) {
//			newOrderSn = PubFun.GetTBOrderSn("TB");
//		} else {
			newOrderSn = PubFun.GetOrderSn();
//		}
		
		newOrder.setOrderSn(newOrderSn);
		newOrder.setMemberId(order.getMemberId());
		newOrder.setProductNum(order.getProductNum());
		newOrder.setProductTotalPrice(order.getProductTotalPrice());
		newOrder.setDiscountRates(order.getDiscountRates());
		newOrder.setDiscountAmount(order.getDiscountAmount());
		newOrder.setTotalAmount(order.getTotalAmount());
		newOrder.setPayAmount(order.getPayAmount());
		newOrder.setRemark(order.getRemark());
		//newOrder.setTbTradeSeriNo(order.getTbTradeSeriNo());
		newOrder.setTbComCode(order.getTbComCode());
		//newOrder.setSdorderitem(order.getSdorderitem());
		//newOrder.setSdinformationSet(order.getSdinformationSet());
		//newOrder.setSdinformationrisktypeSet(order.getSdinformationrisktypeSet());
		newOrder.setCouponSn(order.getCouponSn());
		newOrder.setCommentId(null);
		newOrder.setOrderCoupon(order.getOrderCoupon());
		newOrder.setOrderIntegral(order.getOrderIntegral());
		newOrder.setSumCoupon(order.getSumCoupon());
		newOrder.setSumIntegral(order.getSumIntegral());
		newOrder.setActivitySn(order.getActivitySn());
		newOrder.setOrderActivity(order.getOrderActivity());
		newOrder.setPayPrice(order.getPayPrice());
		newOrder.setSumActivity(order.getSumActivity());
		// bug 0002080 渠道可能是wap_cps。
		if (StringUtil.isNotEmpty(channelsn) && channelsn.startsWith("cps")) {
			newOrder.setChannelsn(channelsn);
		} else {
			newOrder.setChannelsn(order.getChannelsn());
		}
		newOrder.setDellFlag(order.getDellFlag());
		newOrder.setDiyActivitySn(order.getDiyActivitySn());
		newOrder.setDiyActivityDescription(order.getDiyActivityDescription());
		return newOrder;
	}

	/**
	 * 订单明细表
	 * 
	 * @param information
	 * @return
	 */
	public SDInformation dealSDInformation(SDInformation information,
			SDOrder sdorder) {

		SDInformation newInformation = cloneSDInformation(information, sdorder);

		return newInformation;
	}

	/**
	 * 订单明细克隆
	 * 
	 * @param info
	 * @return
	 */
	private SDInformation cloneSDInformation(SDInformation info,SDOrder sdorder) {
		SDInformation newInfo = new SDInformation();
		String informationSn = "";
//		if (StringUtil.isNotEmpty(sdorder.getChannelsn()) && sdorder.getChannelsn().indexOf("tb") >= 0) {
//			informationSn = PubFun.GetTBOrderSn("TBInform");
//		} else {
			informationSn = PubFun.GetSDInformationSn();
//		}
		newInfo.setInformationSn(informationSn);
		newInfo.setOrderSn(sdorder.getOrderSn());
		newInfo.setProductId(info.getProductId());
		newInfo.setProductOutCode(info.getProductOutCode());
		newInfo.setProductName(info.getProductName());
		newInfo.setDiscountRates(info.getDiscountRates());
		newInfo.setProductPrice(info.getProductPrice());
		newInfo.setProductDiscountPrice(info.getProductDiscountPrice());
		newInfo.setProductHtmlFilePath(info.getProductHtmlFilePath());
		newInfo.setProductQuantity(info.getProductQuantity());
		newInfo.setInsuranceCompany(info.getInsuranceCompany());
		newInfo.setPoint(info.getPoint());
		newInfo.setPointStatus(info.getPointStatus());
		newInfo.setStartDate(info.getStartDate());
		newInfo.setEndDate(info.getEndDate());
		newInfo.setEnsureLimitType(info.getEnsureLimitType());
		newInfo.setEnsureLimit(info.getEnsureLimit());
		newInfo.setEnsure(info.getEnsure());
		newInfo.setChargeType(info.getChargeType());
		newInfo.setChargeYear(info.getChargeYear());
		newInfo.setChargeYearName(info.getChargeYearName());
		newInfo.setPlanCode(info.getPlanCode());
		newInfo.setPlanName(info.getPlanName());
		newInfo.setSubRiskType(info.getSubRiskType());
		newInfo.setRemark(info.getRemark());
		newInfo.setEnsureDisplay(info.getEnsureDisplay());
		newInfo.setAppLevel(info.getAppLevel());
		newInfo.setAppLevelName(info.getAppLevelName());
		newInfo.setAppType(info.getAppType());
		newInfo.setAppTypeName(info.getAppTypeName());
		newInfo.setChargeDisplay(info.getChargeDisplay());
		newInfo.setRiskType(info.getRiskType());
		newInfo.setSupKindCode(info.getSupKindCode());
		newInfo.setSupRiskCode(info.getSupRiskCode());
		newInfo.setPrimitiveProductTitle(info.getPrimitiveProductTitle());
		newInfo.setTextAge(info.getTextAge());
		newInfo.setPolicyNum(info.getPolicyNum());
		newInfo.setAppMult(info.getAppMult());
		newInfo.setSdorder(sdorder);
		//newInfo.setSdinformationDutySet(info.getSdinformationDutySet());
		//newInfo.setSdinformationappntSet(info.getSdinformationappntSet());
		//newInfo.setSdinformationinsuredSet(info.getSdinformationinsuredSet());
		return newInfo;
	}

	/**
	 * 处理投保人信息
	 * 
	 * @param informationAppnt
	 * @return
	 */
	public SDInformationAppnt dealSDInformationAppnt(
			SDInformationAppnt informationAppnt, SDInformation sdinformation) {

		SDInformationAppnt newAppnt = cloneSDInformationAppnt(informationAppnt, sdinformation);
		return newAppnt;
	}
	
	/**
	 * 克隆
	 * 
	 * @return
	 */
	private SDInformationAppnt cloneSDInformationAppnt(SDInformationAppnt appnt, SDInformation sdinformation){
		SDInformationAppnt newAppnt = new SDInformationAppnt();
		newAppnt.setInformationSn(sdinformation.getInformationSn());
		String applicantSn = "";
//		if (sdinformation.getInformationSn().indexOf("tb") >= 0) {
//			applicantSn = PubFun.GetTBOrderSn("TBAppnt");
//		} else {
			applicantSn = PubFun.GetSDAppntSn();
//		}
		newAppnt.setApplicantSn(applicantSn);
		newAppnt.setSdinformaiton(sdinformation);
		newAppnt.setApplicantName(appnt.getApplicantName());
		String ApplicantEnName=appnt.getApplicantEnName();
		//去除英文名中的空白字符乱码
		if(StringUtil.isNotEmpty(ApplicantEnName)){
			newAppnt.setApplicantEnName(ApplicantEnName.replaceAll(" "," "));
		}
		newAppnt.setApplicantLastName(appnt.getApplicantLastName());
		newAppnt.setApplicantFirstName(appnt.getApplicantFirstName());
		newAppnt.setApplicantIdentityType(appnt.getApplicantIdentityType());
		newAppnt.setApplicantIdentityTypeName(appnt.getApplicantIdentityTypeName());
		newAppnt.setApplicantIdentityId(appnt.getApplicantIdentityId());
		newAppnt.setApplicantSex(appnt.getApplicantSex());
		newAppnt.setApplicantAge(appnt.getApplicantAge());
		newAppnt.setApplicantSexName(appnt.getApplicantSexName());
		newAppnt.setApplicantBirthday(appnt.getApplicantBirthday());
		newAppnt.setApplicantMail(appnt.getApplicantMail());
		newAppnt.setApplicantArea1(appnt.getApplicantArea1());
		newAppnt.setApplicantArea2(appnt.getApplicantArea2());
		newAppnt.setApplicantAddress(appnt.getApplicantAddress());
		newAppnt.setApplicantZipCode(appnt.getApplicantZipCode());
		newAppnt.setApplicantMobile(appnt.getApplicantMobile());
		newAppnt.setApplicantTel(appnt.getApplicantTel());
		newAppnt.setApplicantOccupation1(appnt.getApplicantOccupation1());
		newAppnt.setApplicantOccupation2(appnt.getApplicantOccupation2());
		newAppnt.setApplicantOccupation3(appnt.getApplicantOccupation3());
		newAppnt.setInvoiceHeading(appnt.getInvoiceHeading());
		newAppnt.setBankCode(appnt.getBankCode());
		newAppnt.setBankAccNo(appnt.getBankAccNo());
		newAppnt.setAccName(appnt.getAccName());
		newAppnt.setRemark(appnt.getRemark());
		newAppnt.setApplicantArea3(appnt.getApplicantArea3());
		newAppnt.setApplicantStartID(appnt.getApplicantStartID());
		newAppnt.setApplicantEndID(appnt.getApplicantEndID());
		newAppnt.setSocialSecurity(appnt.getSocialSecurity());
		return newAppnt;
	}

	/**
	 * 处理被保人信息
	 * 
	 * @param informationInsureds
	 * @param sdinformation
	 * @return
	 */
	public List<SDInformationInsured> dealSDInformationInsured(
			List<SDInformationInsured> informationInsureds,
			SDInformation sdinformation) {

		List<SDInformationInsured> newInsuredList = new ArrayList<SDInformationInsured>();
		int i = 0;
		
		for (SDInformationInsured insured : informationInsureds) {
			
			SDInformationInsured newInsured = cloneSDInformationInsured(insured, sdinformation);
			String orderSn = sdinformation.getOrderSn();
			newInsured.setInsuredSn(orderSn + "_" + (i + 1));
			newInsured.setRecognizeeKey(orderSn + "_" + (i + 1));
			
			newInsuredList.add(newInsured);
			i++;
		}

		return newInsuredList;
	}

	/**
	 * 被保人克隆
	 * @return
	 */
	private SDInformationInsured cloneSDInformationInsured(SDInformationInsured insured, SDInformation sdinformation) {
		SDInformationInsured newInsured = new SDInformationInsured();
		newInsured.setOrderSn(sdinformation.getOrderSn());
		newInsured.setInformationSn(sdinformation.getInformationSn());
		String recognizeeSn = "";
//		if (sdinformation.getInformationSn().indexOf("tb") >= 0) {
//			recognizeeSn = PubFun.GetTBOrderSn("TBInsured");
//		} else {
			recognizeeSn = PubFun.GetSDInsuredSn();
//		}
		newInsured.setRecognizeeSn(recognizeeSn);
		newInsured.setRecognizeeAppntRelation(insured.getRecognizeeAppntRelation());
		newInsured.setRecognizeeAppntRelationName(insured.getRecognizeeAppntRelationName());
		newInsured.setRecognizeeName(insured.getRecognizeeName());
		
		//去除英文名中的空白字符乱码
		String RecognizeeEnName=insured.getRecognizeeEnName();
		if(StringUtil.isNotEmpty(RecognizeeEnName)){
			newInsured.setRecognizeeEnName(RecognizeeEnName.replaceAll(" "," "));
		}
		newInsured.setRecognizeeLashName(insured.getRecognizeeLashName());
		newInsured.setRecognizeeFirstName(insured.getRecognizeeFirstName());
		newInsured.setRecognizeeIdentityType(insured.getRecognizeeIdentityType());
		newInsured.setRecognizeeIdentityTypeName(insured.getRecognizeeIdentityTypeName());
		newInsured.setRecognizeeIdentityId(insured.getRecognizeeIdentityId());
		newInsured.setRecognizeeSex(insured.getRecognizeeSex());
		newInsured.setRecognizeeSexName(insured.getRecognizeeSexName());
		newInsured.setRecognizeeBirthday(insured.getRecognizeeBirthday());
		newInsured.setRecognizeeMobile(insured.getRecognizeeMobile());
		newInsured.setRecognizeeTel(insured.getRecognizeeTel());
		newInsured.setRecognizeeMail(insured.getRecognizeeMail());
		newInsured.setRecognizeeOccupation1(insured.getRecognizeeOccupation1());
		newInsured.setRecognizeeOccupation2(insured.getRecognizeeOccupation2());
		newInsured.setRecognizeeOccupation3(insured.getRecognizeeOccupation3());
		newInsured.setRecognizeeArea1(insured.getRecognizeeArea1());
		newInsured.setRecognizeeArea2(insured.getRecognizeeArea2());
		newInsured.setRecognizeeAddress(insured.getRecognizeeAddress());
		newInsured.setRecognizeeZipCode(insured.getRecognizeeZipCode());
		newInsured.setRecognizeeIsMarry(insured.getRecognizeeIsMarry());
		newInsured.setRecognizeeAge(insured.getRecognizeeAge());
		newInsured.setSchoolOrCompany(insured.getSchoolOrCompany());
		newInsured.setOutGoingParpose(insured.getOutGoingParpose());
		newInsured.setFlightNo(insured.getFlightNo());
		newInsured.setFlightTime(insured.getFlightTime());
		newInsured.setFlightLocation(insured.getFlightLocation());
		newInsured.setDriverSchoolName(insured.getDriverSchoolName());
		newInsured.setDriverNo(insured.getDriverNo());
		newInsured.setDestinationCountry(insured.getDestinationCountry());
		newInsured.setDestinationCountryText(insured.getDestinationCountryText());
		newInsured.setIsSelf(insured.getIsSelf());
		newInsured.setHeight(insured.getHeight());
		newInsured.setWeight(insured.getWeight());
		newInsured.setOverseasOccupation(insured.getOverseasOccupation());
		newInsured.setTravelType(insured.getTravelType());
		newInsured.setTravelMode(insured.getTravelMode());
		newInsured.setNationality(insured.getNationality());
		newInsured.setHaveBuy(insured.getHaveBuy());
		newInsured.setUwCheckFlag(insured.getUwCheckFlag());
		newInsured.setRemark(insured.getRemark());
		newInsured.setInsuredSn(insured.getInsuredSn());
		newInsured.setRecognizeePrem(insured.getRecognizeePrem());
		newInsured.setRecognizeeOperate(insured.getRecognizeeOperate());
		newInsured.setMulInsuredFlag(insured.getMulInsuredFlag());
		newInsured.setRecognizeeTotalPrem(insured.getRecognizeeTotalPrem());
		newInsured.setRecognizeeArea3(insured.getRecognizeeArea3());
		newInsured.setRecognizeeStartID(insured.getRecognizeeStartID());
		newInsured.setRecognizeeEndID(insured.getRecognizeeEndID());
		newInsured.setRecognizeeMul(insured.getRecognizeeMul());
		newInsured.setRecognizeeKey(insured.getRecognizeeKey());
		newInsured.setSocialSecurity(insured.getSocialSecurity());
		newInsured.setDiscountPrice(insured.getDiscountPrice());
		if (StringUtil.isEmpty(newInsured.getDiscountPrice())) {
			newInsured.setDiscountPrice(insured.getRecognizeePrem());
		}
		newInsured.setSdinformation(sdinformation);
		//newInsured.setSdinsuredHealthSet(insured.getSdinsuredHealthSet());
		//newInsured.setSdorderitemothSet(insured.getSdorderitemothSet());
		//newInsured.setSdinforinselementsSet(insured.getSdinforinselementsSet());
		//newInsured.setSdinformationbnfSet(insured.getSdinformationbnfSet());
		//newInsured.setSdinformaitonrisktype(insured.getSdinformaitonrisktype());
		//newInsured.setQuestionPaper(insured.getQuestionPaper());
		//newInsured.setSdinformationproperty(insured.getSdinformationproperty());
		return newInsured;
	}
	/**
	 * 保单信息处理
	 * 
	 * @param informationRiskTypeList
	 * @param sdinformation
	 * @param sdinformationAppnt
	 * @return
	 */
	public List<SDInformationRiskType> dealSDInformationRiskTypeList(
			SDOrder sdorder, SDInformation sdinformation,
			SDInformationAppnt sdinformationAppnt,
			List<SDInformationInsured> sdInformationInsureds,
			Map<String, Object> insureInfoMap) {
		List<SDInformationRiskType> riskTypes = new ArrayList<SDInformationRiskType>();

		for (SDInformationInsured insured : sdInformationInsureds) {

			SDInformationRiskType sdInformationRiskType = new SDInformationRiskType();
			// 订单号
			sdInformationRiskType.setOrderSn(sdorder.getOrderSn());
			// 订单明细编号
			sdInformationRiskType.setInformationSn(sdinformation
					.getInformationSn());
			// 被保人编号
			sdInformationRiskType.setRecognizeeSn(insured.getRecognizeeSn());
			// 投保人编号
			sdInformationRiskType.setApplicantSn(sdinformationAppnt
					.getApplicantSn());
			// 保单号，需要处理
			sdInformationRiskType.setPolicyNo("");
			// 险种编码
			sdInformationRiskType.setRiskCode(sdinformation.getProductId());
			// 险种名称
			sdInformationRiskType.setRiskName(sdinformation.getProductName());
			// 保额，需要处理
			sdInformationRiskType.setAmnt(String.valueOf(insureInfoMap
					.get(sdinformation.getProductId() + "_"
							+ sdinformation.getPlanCode() + "_Amnt")));// 去缓存中的值
			// 份数，需要处理
			sdInformationRiskType.setMult("1");
			// 保费，需要处理
			sdInformationRiskType.setTimePrem(insured.getRecognizeePrem());
			sdInformationRiskType.setProductPrice(insured
					.getRecognizeeTotalPrem());
			// 生效日期
			sdInformationRiskType.setSvaliDate(sdinformation.getStartDate());
			// 失效日期
			sdInformationRiskType.setEvaliDate(sdinformation.getEndDate());
			// 以下字段待定
			// 缴费年期类型
			sdInformationRiskType.setPeriodFlag(sdinformation.getChargeType());
			sdInformationRiskType.setPeriod(sdinformation.getChargeYear());// 缴费年期
			sdInformationRiskType.setElectronicCout("");// 电子保单保险公司路径
			sdInformationRiskType.setElectronicPath("");// 电子保单物理路径
			sdInformationRiskType.setInsurerFlag("");
			sdInformationRiskType.setInsureMsg("");
			sdInformationRiskType.setSdorder(sdorder);
			sdInformationRiskType.setSdinformationinsured(insured);

			riskTypes.add(sdInformationRiskType);

		}
		return riskTypes;
	}

	/**
	 * 通过被保人信息获取订单项
	 * 
	 * @param informationInsuredList
	 * @param sdorder
	 * @return
	 */
	public List<SDOrderItemOth> dealSDOrderItemOthList(
			List<SDInformationInsured> informationInsuredList, SDOrder sdorder,
			SDInformation sdinf) {

		List<SDOrderItemOth> orderItemOthList = new ArrayList<SDOrderItemOth>();

		for (SDInformationInsured insured : informationInsuredList) {
			SDOrderItemOth orderItemOth = new SDOrderItemOth();
			orderItemOth.setOrderSn(insured.getOrderSn());
			String orderitemSn = "";
//			if (StringUtil.isNotEmpty(sdorder.getChannelsn()) && sdorder.getChannelsn().indexOf("tb") >= 0) {
//				orderitemSn = PubFun.GetTBOrderSn("TBorderItem");
//			} else {
				orderitemSn = PubFun.GetItemNo();
//			}
			orderItemOth.setOrderitemSn(orderitemSn);
			orderItemOth.setRecognizeeSn(insured.getRecognizeeSn());
			orderItemOth.setSdinformationinsured(insured);

			if ("2023".equals(sdinf.getInsuranceCompany())) {
				orderItemOth
						.setTpySn(PubFun.getHTSN(sdinf.getProductOutCode()));
			}

			orderItemOthList.add(orderItemOth);
		}

		return orderItemOthList;
	}

	/**
	 * 积分处理
	 * 
	 * @param pointExchangeInfo
	 * @param sdorder
	 * @param sdinformation
	 * @param gift
	 * @return
	 */
	public PointExchangeInfo dealPointExchangeInfo(
			PointExchangeInfo pointExchangeInfo, SDOrder sdorder,
			SDInformation sdinformation, GiftClassify gift) {
		PointExchangeInfo newInfo = new PointExchangeInfo();
		newInfo.setProductid(sdinformation.getProductId());
		newInfo.setProductName(sdinformation.getProductName());
		newInfo.setType("1");
		newInfo.setPoints(sdorder.getOffsetPoint());
		newInfo.setOrderSn(sdorder.getOrderSn());
		newInfo.setMemberid(sdorder.getMemberId());
		if (gift != null) {
			newInfo.setGiftClassifyID(gift.getId());
		}
		newInfo.setStatus(String.valueOf(sdorder.getOrderStatus()
				.ordinal()));
		newInfo.setProp1(pointExchangeInfo.getProp1());
		newInfo.setProp2(pointExchangeInfo.getProp2());
		newInfo.setProp3(pointExchangeInfo.getProp3());
		newInfo.setProp4(pointExchangeInfo.getProp4());
		newInfo.setProp5(pointExchangeInfo.getProp5());
		newInfo.setProp6(pointExchangeInfo.getProp6());
		newInfo.setProp7(pointExchangeInfo.getProp7());
		return newInfo;
	}

	/**
	 * 健康告知
	 * 
	 * @param insuredHealthList
	 * @param sdorder
	 * @param informationInsuredList
	 * @param sdinformation
	 * @return
	 */
	public List<SDInsuredHealth> dealSDInsuredHealthList(
			List<SDInsuredHealth> insuredHealthList, SDOrder sdorder,
			List<SDInformationInsured> informationInsuredList,
			SDInformation sdinformation) {
		
		List<SDInsuredHealth> newHealthList = new ArrayList<SDInsuredHealth>();
		
		SDInformationInsured insured = informationInsuredList
				.get(informationInsuredList.size() - 1);
		for (SDInsuredHealth in : insuredHealthList) {
			in.setSdinformationinsured(insured);
			in.setOrderSn(sdinformation.getOrderSn());
			in.setInformationSn(sdinformation.getInformationSn());
			in.setRecognizeeSn(insured.getRecognizeeSn());
			
			SDInsuredHealth newHealth = cloneSDInsuredHealth(in);
			
			newHealthList.add(newHealth);
		}
		return newHealthList;
	}
	
	/**
	 * 克隆
	 * 
	 * @return
	 */
	public SDInsuredHealth cloneSDInsuredHealth(SDInsuredHealth health) {
		SDInsuredHealth newHealth = new SDInsuredHealth();
		newHealth.setOrderSn(health.getOrderSn());
		newHealth.setInformationSn(health.getInformationSn());
		newHealth.setRecognizeeSn(health.getRecognizeeSn());
		newHealth.setProductId(health.getProductId());
		newHealth.setInsuranceCompany(health.getInsuranceCompany());
		newHealth.setShowOrder(health.getShowOrder());
		newHealth.setShowInfo(health.getShowInfo());
		newHealth.setUiInfo(health.getUiInfo());
		newHealth.setShowDistrict(health.getShowDistrict());
		newHealth.setIsMustInput(health.getIsMustInput());
		newHealth.setIsDisplay(health.getIsDisplay());
		newHealth.setDataType(health.getDataType());
		newHealth.setShowInfoType(health.getShowInfoType());
		newHealth.setShowInfoCode(health.getShowInfoCode());
		newHealth.setBackUp1(health.getBackUp1());
		newHealth.setSelectFlag(health.getSelectFlag());
		newHealth.setTypeShowOrder(health.getTypeShowOrder());
		newHealth.setValueTypeFlag(health.getValueTypeFlag());
		newHealth.setHealthyInfo(health.getHealthyInfo());
		newHealth.setHealthyInfoNum(health.getHealthyInfoNum());
		newHealth.setChildInfoValue(health.getChildInfoValue());
		newHealth.setHealthyInfoId(health.getHealthyInfoId());
		newHealth.setSdinformationinsured(health.getSdinformationinsured());
		return newHealth;
	}

	/**
	 * 责任
	 * 
	 * @param informationDutyList
	 * @param sdorder
	 * @param sdinformation
	 * 
	 * @return
	 */
	public List<SDInformationDuty> dealSDInformationDutyList(
			List<SDInformationDuty> informationDutyList, SDOrder sdorder,
			SDInformation sdinformation) {

		List<SDInformationDuty> newDutyList = new ArrayList<SDInformationDuty>();
		for (SDInformationDuty dity : informationDutyList) {
			dity.setOrderSn(sdorder.getOrderSn());
			dity.setSdinformation(sdinformation);
			dity.setInformationSn(sdinformation.getInformationSn());
			SDInformationDuty newDuty = cloneSDInformationDuty(dity);
			newDutyList.add(newDuty);
		}
		return newDutyList;
	}
	
	public List<SDInformationDutyTemp> dealSDInformationDutyTempList(List<SDInformationDuty> informationDutyList) {
		List<SDInformationDutyTemp> newDutyTempList = new ArrayList<SDInformationDutyTemp>();
		SDInformationDutyTemp temp = null;
		String serials = NoUtil.getMaxNo("DutyTempID", "ID", 20);
		for (SDInformationDuty duty : informationDutyList) {
			temp = new SDInformationDutyTemp();
			temp.setDutySerials(serials);
			temp.setDutySn(duty.getDutySn());
			temp.setOrderSn(duty.getOrderSn());
			temp.setAmnt(duty.getAmt());
			temp.setDiscountPrice(duty.getDiscountPrice());
			temp.setDiscountRates(temp.getDiscountRates());
			temp.setPremium(duty.getPremium());
			newDutyTempList.add(temp);
		}
		
		return newDutyTempList;
	}
	
	/**
	 * 责任克隆
	 * 
	 * @param dity
	 * @return
	 */
	private SDInformationDuty cloneSDInformationDuty (SDInformationDuty dity) {
		SDInformationDuty newDuty = new SDInformationDuty();
		newDuty.setDutySn(dity.getDutySn());
		newDuty.setInformationSn(dity.getInformationSn());
		newDuty.setOrderSn(dity.getOrderSn());
		newDuty.setDutyFullName(dity.getDutyFullName());
		newDuty.setDutyName(dity.getDutyName());
		newDuty.setCoverage(dity.getCoverage());
		newDuty.setRiskCode(dity.getRiskCode());
		newDuty.setPremium(dity.getPremium());
		newDuty.setShowAmnt	(dity.getShowAmnt	());
		newDuty.setAmt(dity.getAmt());
		newDuty.setMainRiskFlag	(dity.getMainRiskFlag	());
		newDuty.setSupplierDutyCode(dity.getSupplierDutyCode());
		newDuty.setDutyEnName(dity.getDutyEnName());
		newDuty.setEnCoverage(dity.getEnCoverage());
		newDuty.setOrderFlag(dity.getOrderFlag());
		newDuty.setIsDisplay(dity.getIsDisplay());
		newDuty.setDiscountRates(dity.getDiscountRates());
		newDuty.setDiscountPrice(dity.getDiscountPrice());
		newDuty.setSdinformation(dity.getSdinformation());
		return newDuty;
	}

	/**
	 * 
	 * @param informationInsuredElements
	 * @param sdorder
	 * @return
	 */
	public List<SDInformationInsuredElements> dealInformationInsuredElements(
			List<SDInformationInsuredElements> informationInsuredElements,
			SDOrder sdorder, List<SDInformationInsured> informationInsuredList,
			SDInformation sdinformation) {
		SDInformationInsured insured = informationInsuredList
				.get(informationInsuredList.size() - 1);

		List<SDInformationInsuredElements> newElementsList = new ArrayList<SDInformationInsuredElements>();
		for (SDInformationInsuredElements elements : informationInsuredElements) {
			SDInformationInsuredElements newElements = new SDInformationInsuredElements();
			newElements.setOrderSn(sdorder.getOrderSn());
			newElements.setRecognizeeSn(insured.getRecognizeeSn());
			newElements.setSdinformationInsured(insured);
			newElements.setInformationSn(sdinformation.getInformationSn());
			
			newElements.setElementsType(elements.getElementsType());
			newElements.setElementsValue(elements.getElementsValue());
			newElements.setElementsSn(elements.getElementsSn());
			
			newElementsList.add(newElements);
			
		}
		return newElementsList;
	}

	/**
	 * 财产
	 * 
	 * @param informationPropertyList
	 * @param sdorder
	 * @return
	 */
	public List<SDInformationProperty> dealSDInformationPropertyList(
			List<SDInformationProperty> informationPropertyList,
			SDOrder sdorder, List<SDInformationInsured> informationInsuredList) {
		SDInformationInsured insured = informationInsuredList
				.get(informationInsuredList.size() - 1);
		
		List<SDInformationProperty> newPropertyList = new ArrayList<SDInformationProperty>();
		
		for (SDInformationProperty property : informationPropertyList) {
			//property.setPropertyToRecognizee(insured.getRecognizeeSn());
//			property.setRecognizeeSn(insured.getRecognizeeSn());
//			property.setSdinformationinsured(insured);
			
			SDInformationProperty newProperty = cloneSDInformationProperty(property);
			newProperty.setRecognizeeSn(insured.getRecognizeeSn());
			newProperty.setSdinformationinsured(insured);
			newPropertyList.add(newProperty);
		}
		return newPropertyList;
	}
	
	/**
	 * 克隆
	 * 
	 * @param property
	 * @return
	 */
	private SDInformationProperty cloneSDInformationProperty(SDInformationProperty property) {
		SDInformationProperty newProperty = new SDInformationProperty();
		newProperty.setHourseType(property.getHourseType());
		newProperty.setHourseNo(property.getHourseNo());
		newProperty.setCarPlateNo(property.getCarPlateNo());
		newProperty.setLicenseNumber(property.getLicenseNumber());
		newProperty.setChassisNumber(property.getChassisNumber());
		newProperty.setPropertyArea1(property.getPropertyArea1());
		newProperty.setPropertyArea2(property.getPropertyArea2());
		newProperty.setPropertyArea3(property.getPropertyArea3());
		newProperty.setPropertyAdress(property.getPropertyAdress());
		newProperty.setPropertyZip(property.getPropertyZip());
		newProperty.setPropertyToRecognizee(property.getPropertyToRecognizee());
		newProperty.setHourseAge(property.getHourseAge());
		newProperty.setRemark1(property.getRemark1());
		return newProperty;
	}

	/**
	 * 处理订单项信息
	 * 
	 * @param sdOrderItem
	 * @param sdorder
	 * @return
	 */
	public SDOrderItem dealSDOrderItem(SDOrderItem sdOrderItem, SDOrder sdorder) {
		SDOrderItem newSDOrderItem = new SDOrderItem();
		newSDOrderItem.setOrderSn(sdorder.getOrderSn());
		newSDOrderItem.setSdorder(sdorder);
		newSDOrderItem.setOrderPoint("0");
		newSDOrderItem.setPointStatus("1");
		String orderitemSn = "";
//		if (StringUtil.isNotEmpty(sdorder.getChannelsn()) && sdorder.getChannelsn().indexOf("tb") >= 0) {
//			orderitemSn = PubFun.GetTBOrderSn("TBorderItem");
//		} else {
			orderitemSn = PubFun.GetItemNo();
//		}
		newSDOrderItem.setOrderitemSn(orderitemSn);
		newSDOrderItem.setChannelCode(sdorder.getChannelsn());
		newSDOrderItem.setChannelAgentCode(sdOrderItem.getChannelAgentCode());
		newSDOrderItem.setTypeFlag(sdOrderItem.getTypeFlag());
		return newSDOrderItem;
	}

	/**
	 * 获取积分兑换表
	 * 
	 * @param againBuyOrderSn
	 * @return
	 */
	public PointExchangeInfo selectPointExchangeInfo(String againBuyOrderSn) {

		QueryBuilder qb = new QueryBuilder(
				"select * from PointExchangeInfo where ordersn = ?",
				againBuyOrderSn);

		DataTable dt = qb.executeDataTable();
		if (dt.getRowCount() > 0) {

			PointExchangeInfo pointExchangeInfo = new PointExchangeInfo();
			DataRow row = dt.get(0);

			pointExchangeInfo.setProductName(row.getString("productName"));
			pointExchangeInfo.setProductid(row.getString("productid"));
			pointExchangeInfo.setType(row.getString("type"));
			pointExchangeInfo.setPoints(row.getString("points"));
			pointExchangeInfo.setMemberid(row.getString("memberid"));
			pointExchangeInfo.setStatus("5");
			pointExchangeInfo.setOrderSn(null);
			pointExchangeInfo.setMobileNo(row.getString("mobileNo"));
			pointExchangeInfo.setGoodsStockID(row.getString("goodsStockID"));
			pointExchangeInfo
					.setGiftClassifyID(row.getString("giftClassifyID"));
			pointExchangeInfo.setProp1(row.getString("prop1"));
			pointExchangeInfo.setProp2(row.getString("prop2"));
			pointExchangeInfo.setProp3(row.getString("prop3"));
			pointExchangeInfo.setProp4(row.getString("prop4"));
			pointExchangeInfo.setProp5(row.getString("prop5"));
			pointExchangeInfo.setProp6(row.getString("prop6"));
			pointExchangeInfo.setProp7(row.getString("prop7"));

			return pointExchangeInfo;
		}
		return null;
	}

	/**
	 * 获取健康告知
	 * 
	 * @param againBuyOrderSn
	 * @return
	 */
	public List<SDInsuredHealth> selectSDInsuredHealthList(
			String againBuyOrderSn) {

		QueryBuilder qb = new QueryBuilder(
				"select * from SDInsuredHealth where ordersn = ?",
				againBuyOrderSn);

		DataTable dt = qb.executeDataTable();

		if (dt.getRowCount() > 0) {
			List<SDInsuredHealth> list = new ArrayList<SDInsuredHealth>();

			for (DataRow row : dt) {
				SDInsuredHealth health = new SDInsuredHealth();
				health.setProductId(row.getString("productId"));
				health.setInsuranceCompany(row.getString("insuranceCompany"));
				health.setShowOrder(String.valueOf(row.getInt("showOrder")));
				health.setShowInfo(row.getString("showInfo"));
				health.setUiInfo(row.getString("uiInfo"));
				health.setShowDistrict(row.getString("showDistrict"));
				health.setIsMustInput(row.getString("isMustInput"));
				health.setIsDisplay(row.getString("isDisplay"));
				health.setDataType(row.getString("dataType"));
				health.setShowInfoType(row.getString("showInfoType"));
				health.setShowInfoCode(row.getString("showInfoCode"));
				health.setBackUp1(row.getString("backUp1"));
				health.setSelectFlag(row.getString("selectFlag"));
				health.setTypeShowOrder(row.getString("typeShowOrder"));
				health.setValueTypeFlag(row.getString("valueTypeFlag"));
				health.setHealthyInfo(row.getString("healthyInfo"));
				if (StringUtil.isNotEmpty(row.getString("healthyInfoNum"))) {
					health.setHealthyInfoNum(row.getString("healthyInfoNum"));
				}
				health.setChildInfoValue(row.getString("childInfoValue"));
				health.setHealthyInfoId(row.getString("healthyInfoId"));
				list.add(health);
			}
			return list;
		}

		return null;
	}

	/**
	 * 责任信息
	 * 
	 * @param againBuyOrderSn
	 * @param sdorder
	 * @return
	 */
	public List<SDInformationDuty> selectSDInformationDutyList(
			String againBuyOrderSn) {

		QueryBuilder qb = new QueryBuilder(
				"select * from SDInformationDuty where ordersn = ?",
				againBuyOrderSn);

		DataTable dt = qb.executeDataTable();

		if (dt.getRowCount() > 0) {
			List<SDInformationDuty> list = new ArrayList<SDInformationDuty>();

			for (DataRow row : dt) {
				SDInformationDuty duty = new SDInformationDuty();
				duty.setAmt(row.getString("amt"));
				duty.setCoverage(row.getString("coverage"));
				duty.setDiscountPrice(row.getString("discountPrice"));
				duty.setDiscountRates(row.getString("discountRates"));
				duty.setDutyEnName(row.getString("dutyEnName"));
				duty.setDutyFullName(row.getString("dutyFullName"));
				duty.setDutyName(row.getString("dutyName"));
				duty.setDutySn(row.getString("dutySn"));
				duty.setEnCoverage(row.getString("enCoverage"));
				duty.setIsDisplay(row.getString("isDisplay"));
				duty.setMainRiskFlag(row.getString("mainRiskFlag"));
				duty.setOrderFlag(row.getString("orderFlag"));
				duty.setPremium(row.getString("premium"));
				duty.setShowAmnt(row.getString("showAmnt"));
				duty.setSupplierDutyCode(row.getString("supplierDutyCode"));
				list.add(duty);
			}
			return list;
		}
		return null;
	}

	/**
	 * 投保要素
	 * 
	 * @param againBuyOrderSn
	 * @return
	 */
	public List<SDInformationInsuredElements> selectInformationInsuredElements(
			String againBuyOrderSn) {

		QueryBuilder qb = new QueryBuilder(
				"select * from SDInformationInsuredElements where ordersn = ?",
				againBuyOrderSn);

		DataTable dt = qb.executeDataTable();

		if (dt.getRowCount() > 0) {
			List<SDInformationInsuredElements> list = new ArrayList<SDInformationInsuredElements>();

			for (DataRow row : dt) {
				SDInformationInsuredElements element = new SDInformationInsuredElements();
				element.setElementsType(row.getString("elementsType"));
				element.setElementsValue(row.getString("elementsValue"));
				list.add(element);
			}
			return list;
		}
		return null;
	}

	/**
	 * 
	 * 
	 * @param againBuyOrderSn
	 * @param sdorder
	 * @return
	 */
	public List<SDInformationProperty> selectSDInformationPropertyList(
			String againBuyOrderSn, SDOrder sdorder) {

		QueryBuilder qb = new QueryBuilder(
				"select * from SDInformationProperty where ordersn = ?",
				againBuyOrderSn);

		DataTable dt = qb.executeDataTable();

		if (dt.getRowCount() > 0) {
			List<SDInformationProperty> list = new ArrayList<SDInformationProperty>();

			for (DataRow row : dt) {
				SDInformationProperty property = new SDInformationProperty();

				property.setCarPlateNo(row.getString("carPlateNo"));
				property.setChassisNumber(row.getString("chassisNumber"));
				property.setHourseAge(row.getString("hourseAge"));
				property.setHourseNo(row.getString("hourseNo"));
				property.setHourseType(row.getString("hourseType"));
				property.setLicenseNumber(row.getString("licenseNumber"));
				property.setPropertyAdress(row.getString("propertyAdress"));
				property.setPropertyArea1(row.getString("propertyArea1"));
				property.setPropertyArea2(row.getString("propertyArea2"));
				property.setPropertyToRecognizee(row
						.getString("propertyToRecognizee"));
				property.setPropertyZip(row.getString("propertyZip"));
				property.setRemark1(row.getString("remark1"));
				property.setPropertyArea3(row.getString("propertyArea3"));
				list.add(property);
			}
			return list;
		}
		return null;
	}

	/**
	 * 
	 * 
	 * @param againBuyOrderSn
	 * @param sdorder
	 * @return
	 */
	public List<DirectPayBankInfo> selectDirectPayBankInfoList(
			String againBuyOrderSn, SDOrder sdorder) {

		QueryBuilder qb = new QueryBuilder(
				"select * from DirectPayBankInfo where ordersn = ?",
				againBuyOrderSn);

		DataTable dt = qb.executeDataTable();

		if (dt.getRowCount() > 0) {
			List<DirectPayBankInfo> list = new ArrayList<DirectPayBankInfo>();

			for (DataRow row : dt) {
				DirectPayBankInfo info = new DirectPayBankInfo();

				info.setBankCity(row.getString("bankCity"));
				info.setBankCode(row.getString("bankCode"));
				info.setBankName(row.getString("bankName"));
				info.setBankNo(row.getString("bankNo"));
				info.setBankProvince(row.getString("bankProvince"));
				info.setBankUserName(row.getString("bankUserName"));
				info.setProp1(row.getString("prop1"));
				info.setProp2(row.getString("prop2"));
				info.setProp3(row.getString("prop3"));
				info.setProp4(row.getString("prop4"));
				info.setProp5(row.getString("prop5"));
				info.setProp6(row.getString("prop6"));
				info.setProp7(row.getString("prop7"));

				list.add(info);
			}
			return list;
		}
		return null;
	}

	/**
	 * 订单项
	 * 
	 * @param againBuyOrderSn
	 * @param sdorder
	 * @return
	 */
	public SDOrderItem selectSDOrderItem(String againBuyOrderSn, SDOrder sdorder) {

		QueryBuilder qb = new QueryBuilder(
				"select * from SDOrderItem where ordersn = ?", againBuyOrderSn);

		DataTable dt = qb.executeDataTable();

		if (dt.getRowCount() > 0) {
			DataRow row = dt.get(0);
			SDOrderItem item = new SDOrderItem();

			item.setChannelAgentCode(row.getString("channelAgentCode"));
			item.setChannelCode(row.getString("channelCode"));
			item.setOrderPoint(row.getString("orderPoint"));
			item.setPointStatus("1");
			item.setTypeFlag(row.getString("typeFlag"));

			return item;
		}
		return null;
	}

}
