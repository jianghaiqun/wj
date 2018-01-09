package com.sinosoft.cms.dataservice;

import java.util.ArrayList;
import java.util.List;

import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FEMRiskFactorList;

import cn.com.sinosoft.entity.OrderRiskAppFactor;
import cn.com.sinosoft.service.SDOrderService;
import cn.com.sinosoft.service.impl.SDOrderServiceImpl;

public class DealRiskAppFactor {
	private SDOrderService sdorderService = new SDOrderServiceImpl();
	// 责任投保要素列表
	private List<OrderRiskAppFactor> riskAppFactior = new ArrayList<OrderRiskAppFactor>();
	// 保障期限列表
	private List<FEMRiskFactorList> periodList = new ArrayList<FEMRiskFactorList>();
	// 计划值列表
	private List<FEMRiskFactorList> planList = new ArrayList<FEMRiskFactorList>();
	// 职业类别
	private List<FEMRiskFactorList> occupList = new ArrayList<FEMRiskFactorList>();
	// 产品级别列表
	private List<FEMRiskFactorList> gradeList = new ArrayList<FEMRiskFactorList>();
	// 缴费年期列表
	private List<FEMRiskFactorList> feeYearList = new ArrayList<FEMRiskFactorList>();
	// 缴费方式
	private List<FEMRiskFactorList> appLevelList = new ArrayList<FEMRiskFactorList>();
	// 投保类别
	private List<FEMRiskFactorList> appTypeList = new ArrayList<FEMRiskFactorList>();
	// 投保年龄
	private List<FEMRiskFactorList> textAgeList = new ArrayList<FEMRiskFactorList>();
	// 保险责任多类人群
	private List<FEMRiskFactorList> mulPeopleList = new ArrayList<FEMRiskFactorList>();
	// 性别
	private List<FEMRiskFactorList> sexList = new ArrayList<FEMRiskFactorList>();
	// 保障期限类型
	private String protectionPeriodTy;
	// 保障期限后段数值
	private String protectionPeriodLast;
	// 是否存在保障期限
	private String protectionPeriodFlag;
	/**
	 * 
	 * 处理保障期限与计划
	 */
	public void dealRiskAppFactor(List<OrderRiskAppFactor> riskAppFactior) {
		if (riskAppFactior != null && riskAppFactior.size() > 0) {
			for (OrderRiskAppFactor orderRiskAppFactor : riskAppFactior) {
				if (orderRiskAppFactor.getFactorValue() != null && orderRiskAppFactor.getFactorValue().size() > 0) {
					for (FEMRiskFactorList femRiskFactorList : orderRiskAppFactor.getFactorValue()) {
						if (!StringUtil.isEmpty(femRiskFactorList.getFactorValue())) {
							// 计划
							if ("Plan".equals(orderRiskAppFactor.getFactorType())){
								planList.add(femRiskFactorList);
							}
							// 保险期限
							if ("Period".equals(orderRiskAppFactor.getFactorType())){
								if (periodList.size() == 0) {
									this.getPeriod(femRiskFactorList.getFactorValue().split("-"));
								}
								periodList.add(femRiskFactorList);
							}
							// 职业类别
							if ("Occup".equals(orderRiskAppFactor.getFactorType())){
								occupList.add(femRiskFactorList);
							}
							// 产品级别
							if ("Grade".equals(orderRiskAppFactor.getFactorType())) {
								gradeList.add(femRiskFactorList);
							}
							// 缴费年期
							if ("FeeYear".equals(orderRiskAppFactor.getFactorType())) {
								feeYearList.add(femRiskFactorList);
							}
							// 投保档次列表
							if ("AppLevel".equals(orderRiskAppFactor.getFactorType())) {
								appLevelList.add(femRiskFactorList);
							}
							// 缴费方式列表
							if ("AppType".equals(orderRiskAppFactor.getFactorType())) {
								appTypeList.add(femRiskFactorList);
							}
							// 投保年龄列表
							if ("TextAge".equals(orderRiskAppFactor.getFactorType())) {
								textAgeList.add(femRiskFactorList);
							}
							// 保险责任多类人群列表
							if ("MulPeople".equals(orderRiskAppFactor.getFactorType())) {
								mulPeopleList.add(femRiskFactorList);
							}
							// 性别
							if ("Sex".equals(orderRiskAppFactor.getFactorType())) {
								sexList.add(femRiskFactorList);
							}
						}
					}
				}
			}
		}
	}

	public static String listToOptions(List<FEMRiskFactorList> list) {
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < list.size(); i++) {
			sb.append("<span value=\"");
			sb.append(list.get(i).getFactorValue());
			if (i == 0) {
				sb.append("\" selected='true' >");
			} else {
				sb.append("\">");
			}
			sb.append(list.get(i).getFactorDisplayValue());
			sb.append("</span>");
		}
		return sb.toString();
	}
	
	/**
	 * 拆分保障期限
	 * 
	 * @param cPeriod
	 * @return
	 */
	public void getPeriod(String[] periods) {
		// String[] periods = new String[]{};

		// periods = cPeriod.split("-");
		String periodBe = "";
		String periodAf = "";
		if (periods != null && periods.length == 1) {
			periodBe = periods[0].toString();// 保障期限前段
			periodAf = periods[0].toString();
			// 保障期限后段

		} else if (periods != null && periods.length == 2) {
			periodBe = periods[0].toString();// 保障期限前段
			periodAf = periods[1].toString();// 保障期限后段
		}
		if (periodBe != null && !"".equals(periodBe) && periodBe.length() > 1) {
			protectionPeriodTy = periodBe.substring(periodBe.length() - 1);// 保障期限类型
		} else {
			protectionPeriodTy = "";
		}
		if (StringUtil.isEmpty(periodAf)) {
			periodAf = periodBe;
		}
		if (periodAf != null && !"".equals(periodAf) && periodAf.length() > 1) {
			protectionPeriodLast = periodAf.substring(0, periodAf.length() - 1);
		} else {
			protectionPeriodLast = "";
		}
		protectionPeriodFlag = "true";

	}

	public SDOrderService getSdorderService() {
		return sdorderService;
	}

	public void setSdorderService(SDOrderService sdorderService) {
		this.sdorderService = sdorderService;
	}

	public List<OrderRiskAppFactor> getRiskAppFactior() {
		return riskAppFactior;
	}

	public void setRiskAppFactior(List<OrderRiskAppFactor> riskAppFactior) {
		this.riskAppFactior = riskAppFactior;
	}

	public List<FEMRiskFactorList> getPeriodList() {
		return periodList;
	}

	public void setPeriodList(List<FEMRiskFactorList> periodList) {
		this.periodList = periodList;
	}

	public List<FEMRiskFactorList> getPlanList() {
		return planList;
	}

	public void setPlanList(List<FEMRiskFactorList> planList) {
		this.planList = planList;
	}

	public List<FEMRiskFactorList> getOccupList() {
		return occupList;
	}

	public void setOccupList(List<FEMRiskFactorList> occupList) {
		this.occupList = occupList;
	}

	public List<FEMRiskFactorList> getGradeList() {
		return gradeList;
	}

	public void setGradeList(List<FEMRiskFactorList> gradeList) {
		this.gradeList = gradeList;
	}

	public List<FEMRiskFactorList> getFeeYearList() {
		return feeYearList;
	}

	public void setFeeYearList(List<FEMRiskFactorList> feeYearList) {
		this.feeYearList = feeYearList;
	}

	public List<FEMRiskFactorList> getAppLevelList() {
		return appLevelList;
	}

	public void setAppLevelList(List<FEMRiskFactorList> appLevelList) {
		this.appLevelList = appLevelList;
	}

	public List<FEMRiskFactorList> getAppTypeList() {
		return appTypeList;
	}

	public void setAppTypeList(List<FEMRiskFactorList> appTypeList) {
		this.appTypeList = appTypeList;
	}

	public List<FEMRiskFactorList> getTextAgeList() {
		return textAgeList;
	}

	public void setTextAgeList(List<FEMRiskFactorList> textAgeList) {
		this.textAgeList = textAgeList;
	}

	public List<FEMRiskFactorList> getMulPeopleList() {
		return mulPeopleList;
	}

	public void setMulPeopleList(List<FEMRiskFactorList> mulPeopleList) {
		this.mulPeopleList = mulPeopleList;
	}

	public List<FEMRiskFactorList> getSexList() {
		return sexList;
	}

	public void setSexList(List<FEMRiskFactorList> sexList) {
		this.sexList = sexList;
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

	public String getProtectionPeriodFlag() {
		return protectionPeriodFlag;
	}

	public void setProtectionPeriodFlag(String protectionPeriodFlag) {
		this.protectionPeriodFlag = protectionPeriodFlag;
	}
}
