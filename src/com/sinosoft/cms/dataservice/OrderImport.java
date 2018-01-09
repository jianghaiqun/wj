package com.sinosoft.cms.dataservice;

import cn.com.sinosoft.entity.Dictionary;
import cn.com.sinosoft.entity.OrderDutyFactor;
import cn.com.sinosoft.entity.OrderRiskAppFactor;
import cn.com.sinosoft.service.SDOrderService;
import cn.com.sinosoft.service.impl.SDOrderServiceImpl;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.cms.pub.SiteUtil;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.CountryChineseSpelling;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.LogUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.BuyForCustomerOldSnSchema;
import com.sinosoft.schema.SDInformationAppntSchema;
import com.sinosoft.schema.SDInformationDutySchema;
import com.sinosoft.schema.SDInformationInsuredElementsSchema;
import com.sinosoft.schema.SDInformationInsuredSchema;
import com.sinosoft.schema.SDInformationRiskTypeSchema;
import com.sinosoft.schema.SDInformationSchema;
import com.sinosoft.schema.SDOrderItemOthSchema;
import com.sinosoft.schema.SDOrderItemSchema;
import com.sinosoft.schema.SDOrdersSchema;
import com.sinosoft.schema.ZCSiteSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FEMDutyAmntPremList;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FEMRiskFactorList;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;
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

public class OrderImport extends Page {

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private Transaction trans;
	// 产品投保要素列表
	private List<OrderDutyFactor> dutyFactor = new ArrayList<OrderDutyFactor>();
	// 责任投保要素列表
	private List<OrderRiskAppFactor> riskAppFactior = new ArrayList<OrderRiskAppFactor>();
	private SDOrderService sdorderService = new SDOrderServiceImpl();
	// 订单详细表（产品表）
	private SDInformationSchema sdInformation = new SDInformationSchema();
	// 订单
	private SDOrdersSchema sdorder = new SDOrdersSchema();
	// 投保人信息
	private SDInformationAppntSchema sdinformationAppnt = new SDInformationAppntSchema();
	// 多被保人存储
	private List<SDInformationInsuredSchema> sdinformationinsuredList = new ArrayList<SDInformationInsuredSchema>();
	private SDOrderItemOthSchema sdorderitemoth = new SDOrderItemOthSchema();
	private SDInformationInsuredElementsSchema informationInsuredElements;
	SimpleDateFormat sdf_1 = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdf_2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private String productId;
	private String startDate;
	private String endDate;
	private Map<String, String> appfactorMap = new HashMap<String, String>();
	private String protectionPeriodTy;// 保障期限类型
	private String protectionPeriodLast;// 保障期限后段数值
	private Date createDate = new Date();
	private Date modifyDate = new Date();
	// 一级地区
	private Map<String, String> areaMap;
	// 投保人地区1
	private String applicantArea1;
	// 投保人地区2
	private String applicantArea2;
	// 被保人地区1
	private String recognizeeArea1;
	// 被保人地区1
	private String recognizeeArea2;

	public static Mapx init(Mapx params) {
		// 保险公司下拉框
		params.put("supplier", HtmlUtil.codeToOptions("SupplierCode", true));
		return params;
	}

	public static Mapx<String, String> initDialog(Mapx<String, String> params) {
		Map<String, Object> paramter = new HashMap<String, Object>();
		// 产品ID
		String productID = params.getString("productID");
		// 产品名称
		String productName = "";

		productName = params.getString("productName");

		DealRiskAppFactor dealRiskAppFactor = new DealRiskAppFactor();

		paramter = dealRiskAppFactor.getSdorderService().getProductInformation(
				productID, "N", "");// 产品ID
		String[] BaseInformation = new String[16];
		// 产品基础数据
		BaseInformation = (String[]) paramter.get("baseInformation");

		dealRiskAppFactor.dealRiskAppFactor((List<OrderRiskAppFactor>) paramter
				.get("riskAppFactor"));
		params.put("protectionPeriodTy",
				dealRiskAppFactor.getProtectionPeriodTy());

		params.put("protectionPeriodLast",
				dealRiskAppFactor.getProtectionPeriodLast());
		params.put("protectionPeriodFlag",
				dealRiskAppFactor.getProtectionPeriodFlag());
		params.put("productName", productName);
		// 保险期限
		if (dealRiskAppFactor.getPeriodList().size() > 0) {
			params.put("period", dealRiskAppFactor
					.listToOptions(dealRiskAppFactor.getPeriodList()));
		}

		// 计划
		if (dealRiskAppFactor.getPlanList().size() > 0) {
			params.put("plan", dealRiskAppFactor
					.listToOptions(dealRiskAppFactor.getPlanList()));
		}

		// 职业类别
		if (dealRiskAppFactor.getOccupList().size() > 0) {
			params.put("occup", dealRiskAppFactor
					.listToOptions(dealRiskAppFactor.getOccupList()));
		}

		// 投保年龄
		if (dealRiskAppFactor.getTextAgeList().size() > 0) {
			params.put("textAge", dealRiskAppFactor
					.listToOptions(dealRiskAppFactor.getTextAgeList()));
		}

		// 缴费年期
		if (dealRiskAppFactor.getFeeYearList().size() > 0) {
			params.put("feeYear", dealRiskAppFactor
					.listToOptions(dealRiskAppFactor.getFeeYearList()));
		}

		// 缴费方式
		if (dealRiskAppFactor.getAppTypeList().size() > 0) {
			params.put("appType", dealRiskAppFactor
					.listToOptions(dealRiskAppFactor.getAppTypeList()));
		}

		// 产品级别
		if (dealRiskAppFactor.getGradeList().size() > 0) {
			params.put("grade", dealRiskAppFactor
					.listToOptions(dealRiskAppFactor.getGradeList()));
		}

		// 投保档次
		if (dealRiskAppFactor.getAppLevelList().size() > 0) {
			params.put("appLevel", dealRiskAppFactor
					.listToOptions(dealRiskAppFactor.getAppLevelList()));
		}

		// 保险责任多类人群
		if (dealRiskAppFactor.getMulPeopleList().size() > 0) {
			params.put("mulPeople", dealRiskAppFactor
					.listToOptions(dealRiskAppFactor.getMulPeopleList()));
		}

		// 性别
		if (dealRiskAppFactor.getSexList().size() > 0) {
			params.put("sex", dealRiskAppFactor
					.listToOptions(dealRiskAppFactor.getSexList()));
		}
		
		params.put("CmsBuySource", HtmlUtil.codeToOptions("Activity.channel", false));
		return params;
	}

	public static Mapx<String, String> initDialog1(Mapx<String, String> params) {
		params.put("CmsBuySource", HtmlUtil.codeToOptions("Activity.channel", true));
		return params;
	}
	
	public static Mapx initDialog2(Mapx params) {
		return params;
	}
	
	/**
	 * 得到订单信息
	 * 
	 * @param dga
	 */
	public void dg1DataBind(DataGridAction dga) {

		// 得到查询SQL
		String querySql = "select (select codename from zdcode where codetype='SupplierCode' and codevalue=a.Remark6) supplierName,"
				+ " a.ProductName, a.ProductID,a.Remark6 from sdproduct a where (a.ProductType = 'A' or a.ProductType = 'B') ";

		// 查询条件
		StringBuffer wherePart = new StringBuffer();
		// 产品名称
		String productName = dga.getParams().getString("productName");
		if (StringUtil.isNotEmpty(productName)) {
			wherePart.append(" and a.ProductName like '%" + productName.trim()
					+ "%'");
		}
		// 产品编码
		String productCode = dga.getParams().getString("productCode");
		if (StringUtil.isNotEmpty(productCode)) {
			wherePart.append(" and a.ProductID like '%" + productCode.trim()
					+ "%'");
		}

		// 保险公司
		String supplierCode = dga.getParams().getString("supplierCode");
		if (StringUtil.isNotEmpty(supplierCode)) {
			wherePart.append(" and a.Remark6 = '" + supplierCode.trim() + "'");
		}

		if (StringUtils.isNotEmpty(wherePart.toString())) {
			querySql += wherePart.toString();
		}
		querySql += " order by a.ProductID asc ";
		// 取得结果
		QueryBuilder qb = new QueryBuilder(querySql);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(),
				dga.getPageIndex());
		dga.setTotal(qb);
		dga.bindData(dt);
	}

	/**
	 * 得到产品信息
	 * 
	 * @param dga
	 */
	public void dg2DataBind(DataGridAction dga) {
		// 得到查询SQL
		String querySql = "select (select codename from zdcode where codetype='SupplierCode' and codevalue=a.Remark6) supplierName,"
				+ " a.ProductName, a.ProductID,a.Remark6,a.HtmlPath from sdproduct a where a.Remark6!='0005' ";

		// 查询条件
		StringBuffer wherePart = new StringBuffer();
		// 产品名称
		String productName = dga.getParams().getString("productName");
		if (StringUtil.isNotEmpty(productName)) {
			wherePart.append(" and a.ProductName like '%" + productName.trim()
					+ "%'");
		}
		// 产品编码
		String productCode = dga.getParams().getString("productCode");
		if (StringUtil.isNotEmpty(productCode)) {
			wherePart.append(" and a.ProductID like '%" + productCode.trim()
					+ "%'");
		}

		// 保险公司
		String supplierCode = dga.getParams().getString("supplierCode");
		if (StringUtil.isNotEmpty(supplierCode)) {
			wherePart.append(" and a.Remark6 = '" + supplierCode.trim() + "'");
		}

		if (StringUtils.isNotEmpty(wherePart.toString())) {
			querySql += wherePart.toString();
		}
		querySql += " order by a.ProductID asc ";
		// 取得结果
		QueryBuilder qb = new QueryBuilder(querySql);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(),
				dga.getPageIndex());
		dga.setTotal(qb);
		dga.bindData(dt);
	}
	
	/**
	 * 根据计划确定责任总保额
	 * 
	 * @param dutyFactor2
	 *            责任集合
	 * @return
	 */
	private String getInsuredAmnt(List<OrderDutyFactor> dutyFactor2,
			String cPlanCode) {

		List<String> backup = new ArrayList<String>();
		if (dutyFactor2.size() > 0) {
			for (OrderDutyFactor orderDutyFactor : dutyFactor2) {

				for (FEMDutyAmntPremList fEMDutyAmntPremList : orderDutyFactor
						.getFdAmntPremList()) {
					if (fEMDutyAmntPremList != null) {
						// 根据计划选择责任的保额
						if (cPlanCode != null && !"".equals(cPlanCode)) {
							if (cPlanCode.equals(fEMDutyAmntPremList
									.getAppFactorValue())) {
								backup.add(fEMDutyAmntPremList.getBackUp1());
							}
						} else {
							backup.add(fEMDutyAmntPremList.getBackUp1());
						}
					}
				}
				if (orderDutyFactor.getFdAmntPremList() == null
						|| orderDutyFactor.getFdAmntPremList().size() <= 0
						|| orderDutyFactor.getFdAmntPremList().get(0) == null) {
					FEMDutyAmntPremList fEMDutyAmntPremList = new FEMDutyAmntPremList();
					List<FEMDutyAmntPremList> fdap = new ArrayList<FEMDutyAmntPremList>();
					fdap.add(fEMDutyAmntPremList);
					orderDutyFactor.setFdAmntPremList(fdap);
				}
			}
		}
		Integer backNum = 0;
		for (int i = 0; i < backup.size(); i++) {
			if (backup.get(i) != null && !"".equals(backup.get(i).toString())) {
				backNum = backNum + Integer.parseInt(backup.get(i).toString());
			}
		}
		return backNum.toString();
	}

	public void dealPeriod() {
		if ("Y".equals(protectionPeriodTy)) {
			protectionPeriodTy = "y";
		}
		if ("D".equals(protectionPeriodTy) || "".equals(protectionPeriodTy)) {
			protectionPeriodTy = "d";
		}
		if ("M".equals(protectionPeriodTy)) {
			protectionPeriodTy = "m";
		}
		if ("".equals(protectionPeriodLast)) {
			protectionPeriodLast = "1";
		}
	}

	/**
	 * 设置基础信息
	 */
	@SuppressWarnings("unchecked")
	private void setInfo() {
		Map<String, Object> paramter = new HashMap<String, Object>();
		String[] BaseInformation = new String[16];

		try {
			paramter = sdorderService.getProductInformation(productId, "N", "");
		} catch (Exception e) {
			this.addError("与产品中心交互失败！");
		}
		// 产品基础数据
		BaseInformation = (String[]) paramter.get("baseInformation");
		riskAppFactior = (List<OrderRiskAppFactor>) paramter
				.get("riskAppFactor");
		dutyFactor = (List<OrderDutyFactor>) paramter.get("dutyFactor");
		// 处理产品保障期限与计划
		this.setDutyFactorAndAmntNum(riskAppFactior);
		// 保险公司编码
		String insuranceCompanySn = BaseInformation[2].toString();
		insuranceCompanySn = insuranceCompanySn.substring(0, 4);
		sdInformation.setinsuranceCompany(insuranceCompanySn);
		// 折扣后价格
		String countPrice = BaseInformation[11].toString();
		sdorder.setproductTotalPrice(countPrice);
		// 折扣比率
		sdorder.setdiscountRates(BaseInformation[10].toString());
		sdorder.setpayStatus(0);
		// 商品原价
		String price = BaseInformation[6].toString();
		// 商品原价
		sdInformation.setproductPrice(price);
		// 折扣比率
		sdorder.setdiscountRates(BaseInformation[10].toString());
		sdorder.setorderStatus(5);
		// 订单详细表（产品表）
		// 产品ID
		sdInformation.setproductId(BaseInformation[0].toString());
		// 产品名称
		sdInformation.setproductName(BaseInformation[1].toString());
		// 产品折扣率
		sdInformation.setdiscountRates(BaseInformation[10].toString());
		// 保险公司编码
		sdInformation.setinsuranceCompany(insuranceCompanySn);
		// 保险公司产品编码
		sdInformation.setproductOutCode(BaseInformation[4].toString());
		// 保险公司险种编码，平安核保报文
		sdInformation.setsupKindCode(BaseInformation[12].toString());
		// 内部统计险种中类别
		sdInformation.setriskType(BaseInformation[5].toString());
		// 产品详细页地址
		sdInformation.setproductHtmlFilePath("");
		sdInformation.setproductQuantity("1");
		// 积分，需要补充
		sdInformation.setpoint("");
		// 积分状态，需要补充
		sdInformation.setpointStatus("");
		try {
			if (StringUtil.isNotEmpty(this.getStartDate())
					&& this.getStartDate().trim().length() == 10) {
				sdInformation.setstartDate(sdf_2.parse(this.getStartDate()
						+ " 00:00:00"));
			}

		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		// endDate需要特殊处理
		sdInformation.setendDate(this.getEndDate(this.getEndDate()));
		// 产品ID
		productId = sdInformation.getproductId();
		sdorder.setproductNum("1");
		String orderSn = PubFun.GetOrderSn();
		sdorder.setorderSn(orderSn);
		// 订单明细表编号
		String informationSn = PubFun.GetSDInformationSn();
		sdInformation.setinformationSn(informationSn);
		sdInformation.setorderSn(orderSn);// 保单号
		// 配置保险期限
		this.dealPeriod();
		// 保障期限类型
		sdInformation.setensureLimitType(this.getProtectionPeriodTy()
				.toUpperCase());
		// 保障期限
		sdInformation.setensureLimit(this.getProtectionPeriodLast());
		sdorder.setid(PubFun.GetOrderID(orderSn));
		sdorder.setcreateDate(createDate);
		sdorder.setmodifyDate(modifyDate);
		sdInformation.setsdorder_id(sdorder.getid());
		sdInformation.setId(PubFun.GetInformationID(orderSn));
		sdInformation.setcreateDate(createDate);
		sdInformation.setmodifyDate(modifyDate);
	}

	/**
	 * 处理保障期限与计划
	 */
	private void setDutyFactorAndAmntNum(
			List<OrderRiskAppFactor> riskAppFactior2) {
		// 解决默认保费为0问题，删除掉多余代码
		if (riskAppFactior2.size() > 0) {
			String plan = appfactorMap.get("Plan");
			String period = appfactorMap.get("Period");
			String feeYear = appfactorMap.get("FeeYear");
			String appLevel = appfactorMap.get("AppLevel");
			String appType = appfactorMap.get("AppType");

			for (int i = 0; i < riskAppFactior2.size(); i++) {
				OrderRiskAppFactor orderRiskAppFactor = riskAppFactior2.get(i);

				// 计划列表
				List<FEMRiskFactorList> tFEMRiskFactorList = null;
				tFEMRiskFactorList = orderRiskAppFactor.getFactorValue();
				for (FEMRiskFactorList t : tFEMRiskFactorList) {
					if ("Plan".equals(orderRiskAppFactor.getFactorType())) {
						try {
							if (StringUtil.isNotEmpty(plan)) {
								plan = java.net.URLDecoder
										.decode(plan, "UTF-8");
								if (plan.equals(t.getFactorValue())) {
									String tPlanName = t
											.getFactorDisplayValue();
									this.sdInformation
											.setplanCode(java.net.URLDecoder
													.decode(plan));
									this.sdInformation.setplanName(tPlanName);
								}
							}
						} catch (Exception e) {
							logger.error(e.getMessage(), e);
						}
					}
					// 保险期限
					if ("Period".equals(orderRiskAppFactor.getFactorType())) {

						if (StringUtil.isNotEmpty(period)
								&& period.equals(t.getFactorValue())) {
							String tPeriodName = t.getFactorDisplayValue();
							String[] periods = t.getFactorValue().split("-");
							this.getPeriod(periods);
							sdInformation.setensure(period);
							sdInformation.setensureDisplay(tPeriodName);
						}
					}
					// 缴费年期
					if ("FeeYear".equals(orderRiskAppFactor.getFactorType())) {

						if (StringUtil.isNotEmpty(feeYear)
								&& feeYear.equals(t.getFactorValue())) {
							String tFeeYearName = t.getFactorDisplayValue();
							sdInformation.setchargeYear(feeYear);
							sdInformation.setchargeYearName(tFeeYearName);
						}
					}
					// 缴费方式
					if ("AppLevel".equals(orderRiskAppFactor.getFactorType())) {

						if (StringUtil.isNotEmpty(appLevel)
								&& appLevel.equals(t.getFactorValue())) {
							String tAppLevelName = t.getFactorDisplayValue();
							sdInformation.setappLevel(appLevel);
							sdInformation.setappLevelName(tAppLevelName);
						}
					}
					// 投保类别
					if ("AppType".equals(orderRiskAppFactor.getFactorType())) {

						if (StringUtil.isNotEmpty(appType)
								&& appType.equals(t.getFactorValue())) {
							String tAppTypeName = t.getFactorDisplayValue();
							sdInformation.setappType(appType);
							sdInformation.setappTypeName(tAppTypeName);
						}
					}
				}
			}
		}
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
		if ("".equals(periodAf) || periodAf == "") {
			periodAf = periodBe;
		}
		if (periodAf != null && !"".equals(periodAf) && periodAf.length() > 1) {
			protectionPeriodLast = periodAf.substring(0, periodAf.length() - 1);
		} else {
			protectionPeriodLast = "";
		}
	}

	/**
	 * 保存责任
	 * 
	 * @param inf
	 */
	private void informationInsuredDutySave(SDInformationSchema inf) {

		List<FEMDutyAmntPremList> fdAmntPremList;
		if (dutyFactor != null) {
			SDInformationDutySchema sdinformationduty = new SDInformationDutySchema();
			for (int i = 0; i < dutyFactor.size(); i++) {
				// 保额显示值
				String showAmnt = "";
				// 保额计算值
				String amt = "";
				// 保费
				String premium = "";
				fdAmntPremList = dutyFactor.get(i).getFdAmntPremList();
				if (fdAmntPremList != null && fdAmntPremList.size() > 0) {
					if ("Y".equals(dutyFactor.get(i).getIsRelaRiskFactor())) {
						for (int j = 0; j < fdAmntPremList.size(); j++) {
							String appFactorValue = fdAmntPremList.get(j)
									.getAppFactorValue();
							String backUp2 = fdAmntPremList.get(j).getBackUp2();
							if (StringUtil.isNotEmpty(appFactorValue)
									&& StringUtil.isNotEmpty(backUp2)) {
								if (appFactorValue.equals(appfactorMap
										.get(backUp2))) {
									showAmnt = fdAmntPremList.get(j).getAmnt();
									amt = fdAmntPremList.get(j).getBackUp1();
									premium = fdAmntPremList.get(j).getPrem();
								}
							}
						}

					} else {
						showAmnt = fdAmntPremList.get(0).getAmnt();
						amt = fdAmntPremList.get(0).getBackUp1();
						premium = fdAmntPremList.get(0).getPrem();
					}
				}

				if (StringUtil.isEmpty(amt)) {
					continue;
				}
				sdinformationduty = new SDInformationDutySchema();
				sdinformationduty.setId(PubFun.GetInformationDutyID(inf
						.getorderSn()));
				sdinformationduty.setcreateDate(createDate);
				sdinformationduty.setmodifyDate(modifyDate);
				sdinformationduty.setsdinformation_id(inf.getId());
				sdinformationduty.setdutySn(dutyFactor.get(i).getDutyCode());// 责任编码
				sdinformationduty.setinformationSn(inf.getinformationSn());// 订单明细表编码
				sdinformationduty.setorderSn(inf.getorderSn());// 保单号
				sdinformationduty.setdutyName(dutyFactor.get(i)
						.getDudtyFactorName());// 责任名称
				sdinformationduty.setdutyFullName(dutyFactor.get(i)
						.getDutyFullName());// 责任全称
				sdinformationduty.setcoverage(dutyFactor.get(i).getDefine());
				sdinformationduty.setriskCode("");// 险种编码
				sdinformationduty.setshowAmnt(showAmnt);// 显示值
				sdinformationduty.setpremium(premium);// 保费
				sdinformationduty.setamt(amt);// 计算值保额
				sdinformationduty.setmainRiskFlag(dutyFactor.get(i)
						.getCurrency());// 主险标志
				sdinformationduty.setsupplierDutyCode(dutyFactor.get(i)
						.getSupplierDutyCode());// 保险公司责任/险别编码
				trans.add(sdinformationduty, Transaction.INSERT);
			}
		}
	}

	/**
	 * 保存上传文件
	 * 
	 * @param upLoadFileName
	 *            上传文件
	 * @return
	 */
	public String upLoadSave(String upLoadFileName) {
		String result = "Succ";

		setInfo();
		// 解析Excel内容成功，保存数据
		if (resolvingExcel(upLoadFileName)) {
			if (!trans.commit()) {
				this.addError("执行插入语句失败！");
				result = "Fail";
			}
		} else {
			result = "Fail";
		}
		ZCSiteSchema site = SiteUtil.getSchema(Application.getCurrentSiteID());
		String content = site.getProp6()
				+ "/shop/order_config_new!buyNowUpdate.action?orderSn="
				+ sdInformation.getorderSn() + "&orderId=" + sdorder.getid()
				+ "&Flag=Suc";
		if ("Succ".equals(result)) {
			result = content;
		}

		return result;
	}

	/**
	 * 取得一级地区信息
	 * 
	 * @param productId
	 *            产品编码
	 * @param comCode
	 *            保险公司编码
	 * @return
	 */
	public Map<String, String> findSuperByCom(String productId, String comCode) {
		Map<String, String> areaMap = new HashMap<String, String>();
		String sql = " select id, name from area where parent_id is null and insuranceCompany = ? and productId = ? order by id ASC ";
		QueryBuilder qb = new QueryBuilder(sql);
		qb.add(comCode);
		qb.add(productId);
		DataTable dataTable = qb.executeDataTable();
		if (dataTable == null || dataTable.getRowCount() == 0) {
			if ("1061".equals(comCode) || "2007".equals(comCode)
					|| "2011".equals(comCode) || "1018".equals(comCode)) {
				sql = "select id, name from area o where o.insuranceCompany= ? and (o.parent_id is null or o.parent_id='') and (o.productid is null or o.productid='') order by id ASC";
				qb = new QueryBuilder(sql);
				qb.add(comCode);
				dataTable = qb.executeDataTable();
			}
		}

		if (dataTable != null) {
			for (int i = 0; i < dataTable.getRowCount(); i++) {
				areaMap.put(dataTable.get(i).getString(0), dataTable.get(i)
						.getString(1));
			}
		}
		return areaMap;
	}

	/**
	 * 取得二级地区信息
	 * 
	 * @param code
	 *            上级地区编码
	 * @param productId
	 *            产品编码
	 * @param comCode
	 *            保险公司编码
	 * @return 二级地区信息
	 */
	public Map<String, String> getArea2(String code, String productId,
			String comCode) {
		Map<String, String> areaMap = new HashMap<String, String>();
		String sql = " select id, name from area where parent_id = ? and (insuranceCompany = ? or productid = ?)";
		QueryBuilder qb = new QueryBuilder(sql);
		qb.add(code);
		qb.add(comCode);
		qb.add(productId);
		DataTable dataTable = qb.executeDataTable();
		if (dataTable != null) {
			for (int i = 0; i < dataTable.getRowCount(); i++) {
				areaMap.put(dataTable.get(i).getString(0), dataTable.get(i)
						.getString(1));
			}
		}
		return areaMap;
	}

	/**
	 * 解析Excel内容
	 * 
	 * @param upLoadFileName
	 *            上传文件
	 * @return 解析是否成功
	 */
	public boolean resolvingExcel(String upLoadFileName) {
		try {
			trans = new Transaction();
			// 查询一级地区
			areaMap = findSuperByCom(productId,
					sdInformation.getinsuranceCompany());
			// 取得并解析文件头部
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(
					upLoadFileName));
			HSSFSheet sheet = workbook.getSheetAt(0);// 取头一个sheet
			// 取得投保人信息
			boolean result = getInformationAppnt(sheet);
			if (result) {
				informationAppntSave(sdInformation);
			} else {
				return false;
			}

			// 取得被保人信息
			result = getInformationinsured(workbook.getSheetAt(1));
			if (result) {
				// 处理被保人信息---多被保人-多人
				this.sdinformationMulInsuredSave(sdorder, sdInformation,
						sdinformationAppnt, sdInformation.getriskType());
				// 处理订单项信息
				this.orderItemSave();
			} else {
				return false;
			}
			informationInsuredDutySave(sdInformation);
			trans.add(sdinformationAppnt, Transaction.INSERT);
			trans.add(sdInformation, Transaction.INSERT);
			trans.add(sdorder, Transaction.INSERT);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * 处理orderitem
	 */
	public void orderItemSave() {
		SDOrderItemSchema sdorderitem = new SDOrderItemSchema();
		sdorderitem.setid(PubFun.GetOrderItemID(sdInformation.getorderSn()));
		sdorderitem.setcreateDate(createDate);
		sdorderitem.setmodifyDate(modifyDate);
		sdorderitem.setsdorder_id(sdorder.getid());
		sdorderitem.setorderSn(sdorder.getorderSn());
		sdorderitem.setorderPoint("0");
		sdorderitem.setpointStatus("1");
		sdorderitem.setorderitemSn(PubFun.GetItemNo());
		trans.add(sdorderitem, Transaction.INSERT);
	}

	/**
	 * 用编码取得对应名称
	 * 
	 * @param productId
	 *            产品编码
	 * @param insuranceCode
	 *            保险公司编码
	 * @param codetype
	 *            类型
	 * @param codeValue
	 *            编码
	 * @return
	 */
	public String getNameByCodeTypePro(String productId, String insuranceCode,
			String codetype, String codeValue) {
		String sql = " select codeName from dictionary where codeType = ? and codeValue = ? and productId = ? order by id asc ";
		QueryBuilder qb = new QueryBuilder(sql);
		qb.add(codetype);
		qb.add(codeValue);
		qb.add(productId);
		String codeName = qb.executeString();

		if (StringUtil.isEmpty(codeName)) {
			sql = "select codeName from dictionary where codeType = ? and codeValue = ? and insuranceCode = ? order by id asc ";
			qb = new QueryBuilder(sql);
			qb.add(codetype);
			qb.add(codeValue);
			qb.add(insuranceCode);
			codeName = qb.executeString();
		}
		return codeName;
	}

	/**
	 * 取得英文名
	 * 
	 * @param insuranceCode
	 *            保险公司编码
	 * @param codeType
	 *            类型
	 * @param codeValue
	 *            编码
	 * @return 英文名
	 */
	public String getCodeEnNameByCodeValue(String insuranceCode,
			String codeType, String codeValue) {
		String sql = " select codeEnName from dictionary where codeType = ? and codeValue = ? and insuranceCode = ? order by id asc ";
		QueryBuilder qb = new QueryBuilder(sql);
		qb.add(codeType);
		qb.add(codeValue);
		qb.add(insuranceCode);
		String codeEnName = qb.executeString();
		if (StringUtil.isNotEmpty(codeEnName)) {
			// 需要特殊处理&符号
			return codeEnName.replaceAll("&amp;", "&");
		}
		return null;
	}

	/**
	 * 取得旅游目的地英文名
	 * 
	 * @param trim
	 *            编码
	 * @return
	 */
	public String findCountryNameByCode(String trim) {
		String sql = " select codeEnName from dictionary where codeType = 'CountryCode' and codeValue = ? order by id asc ";
		QueryBuilder qb = new QueryBuilder(sql);
		qb.add(trim);
		String codeName = qb.executeString();
		return codeName;
	}

	/**
	 * 取得旅游目的地
	 * 
	 * @param type
	 *            类型
	 * @param comCode
	 *            保险公司编码
	 * @param productId
	 *            产品编码
	 * @return 旅游目的地
	 */
	public List<Dictionary> findListByCom(String type, String comCode,
			String productId) {
		List<Dictionary> list = new ArrayList<Dictionary>();
		String sql = " select * from dictionary where codeType = ? and productId = ? order by id asc ";
		QueryBuilder qb = new QueryBuilder(sql);
		qb.add(type);
		qb.add(productId);
		DataTable dataTable = qb.executeDataTable();
		if (dataTable == null || dataTable.getRowCount() <= 0) {
			sql = " select * from dictionary where codeType = ? and insuranceCode = ? order by id asc ";
			qb = new QueryBuilder(sql);
			qb.add(type);
			qb.add(comCode);
			dataTable = qb.executeDataTable();
		}

		if (dataTable != null && dataTable.getRowCount() > 0) {
			Dictionary dictionary;
			for (int i = 0; i < dataTable.getRowCount(); i++) {
				dictionary = new Dictionary();
				// 类别代码值
				dictionary.setCodeType(dataTable.get(i).getString("codeType"));
				// 类别名称
				dictionary.setCodeTypeName(dataTable.get(i).getString(
						"codeTypeName"));
				// 属性值
				dictionary
						.setCodeValue(dataTable.get(i).getString("codeValue"));
				// 属性中文
				dictionary.setCodeName(dataTable.get(i).getString("codeName"));
				// 英文缩写
				dictionary.setCodeEnName(dataTable.get(i).getString(
						"codeEnName"));
				// 保险公司
				dictionary.setInsuranceCode(dataTable.get(i).getString(
						"insuranceCode"));
				// 是否申根国家标志 Y是N是
				dictionary.setFlagType(dataTable.get(i).getString("flagType"));
				// 产品ID
				dictionary
						.setProductId(dataTable.get(i).getString("productId"));
				// 常用地区标志Y是 N不是
				dictionary.setComFlag(dataTable.get(i).getString("comflag"));
				list.add(dictionary);
			}
		}
		return list;
	}

	public String getCountryText2007(String cComCode, String cCountryCode) {
		List<HashMap<String, String>> countryList = new ArrayList<HashMap<String, String>>();
		String shenGenEn = "";
		String shenGenCh = "";
		String unShenGenEn = "";
		String unShenGenCh = "";
		String dest = "";
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select codeEnName,codeName,flagType from dictionary where codetype='CountryCode' and insuranceCode = ? and codeValue in (");
			if (StringUtil.isNotEmpty(cCountryCode)) {
				String[] s = cCountryCode.split(",");
				for (int i = 0; i < s.length; i++) {
					if (i != (s.length - 1)) {
						sb.append("'" + s[i] + "',");
					} else {
						sb.append("'" + s[i] + "'");
					}
				}
			}
			sb.append(")");
			String[] sqltemp = { cComCode };
			countryList = new GetDBdata().query(sb.toString(), sqltemp);
			Iterator<HashMap<String, String>> countryIt = countryList
					.iterator();
			while (countryIt.hasNext()) {
				HashMap<String, String> countryMap = (HashMap<String, String>) countryIt
						.next();
				if ("Y".equals(countryMap.get("flagType"))) {
					shenGenEn += countryMap.get("codeEnName") + ",";
					shenGenCh += countryMap.get("codeName") + ",";
				} else if ("N".equals(countryMap.get("flagType"))) {
					unShenGenEn += countryMap.get("codeEnName") + ",";
					unShenGenCh += countryMap.get("codeName") + ",";
				}
			}
			if (StringUtil.isNotEmpty(unShenGenCh)
					&& StringUtil.isNotEmpty(unShenGenEn)) {
				dest = shenGenCh
						+ "申根协议国家"
						+ " "
						+ shenGenEn
						+ "SCHENGEN STATES"
						+ " "
						+ unShenGenCh
								.substring(0, unShenGenCh.lastIndexOf(","))
						+ " "
						+ unShenGenEn
								.substring(0, unShenGenEn.lastIndexOf(","));
			} else {
				dest = shenGenCh + "申根协议国家" + " " + shenGenEn
						+ "SCHENGEN STATES" + " ";
			}
		} catch (Exception e) {
			logger.info("查询目的地异常" + e.getMessage(), e);
		}
		return dest;
	}

	/**
	 * 根据具体的编码取得对应的旅游目的地
	 * 
	 * @param insuranceCode
	 *            保险公司编码
	 * @param codetype
	 *            类型
	 * @param codeValue
	 *            编码
	 * @return 旅游目的地
	 */
	public Dictionary getNameByCodeValue(String insuranceCode, String codetype,
			String codeValue) {
		String sql = " select * from dictionary where codeType = ? and codeValue = ? and insuranceCode = ? order by id asc ";
		QueryBuilder qb = new QueryBuilder(sql);
		qb.add(codetype);
		qb.add(codeValue);
		qb.add(insuranceCode);
		DataTable dataTable = qb.executeDataTable();
		Dictionary dictionary = new Dictionary();
		if (dataTable != null && dataTable.getRowCount() > 0) {
			// 类别代码值
			dictionary.setCodeType(dataTable.get(0).getString("codeType"));
			// 类别名称
			dictionary.setCodeTypeName(dataTable.get(0).getString(
					"codeTypeName"));
			// 属性值
			dictionary.setCodeValue(dataTable.get(0).getString("codeValue"));
			// 属性中文
			dictionary.setCodeName(dataTable.get(0).getString("codeName"));
			// 英文缩写
			dictionary.setCodeEnName(dataTable.get(0).getString("codeEnName"));
			// 保险公司
			dictionary.setInsuranceCode(dataTable.get(0).getString(
					"insuranceCode"));
			// 是否申根国家标志 Y是N是
			dictionary.setFlagType(dataTable.get(0).getString("flagType"));
			// 产品ID
			dictionary.setProductId(dataTable.get(0).getString("productId"));
			// 常用地区标志Y是 N不是
			dictionary.setComFlag(dataTable.get(0).getString("comflag"));
		}
		return dictionary;
	}

	public String getSchengenCountryText(String cComCode, String cCountryCode) {

		List<HashMap<String, String>> countryList = new ArrayList<HashMap<String, String>>();
		String shenGen = "";
		String unShenGen = "";
		String dest = "";
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select codeEnName,codeName,flagType from dictionary where codetype='CountryCode' and insuranceCode = ? and codeValue in (");
			if (StringUtil.isNotEmpty(cCountryCode)) {
				String[] s = cCountryCode.split(",");
				for (int i = 0; i < s.length; i++) {
					if (i != (s.length - 1)) {
						sb.append("'" + s[i] + "',");
					} else {
						sb.append("'" + s[i] + "'");
					}
				}
			}
			sb.append(")");
			String[] sqltemp = { cComCode };
			countryList = new GetDBdata().query(sb.toString(), sqltemp);
			Iterator<HashMap<String, String>> countryIt = countryList
					.iterator();
			boolean schengenFlag = false;
			while (countryIt.hasNext()) {
				HashMap<String, String> countryMap = (HashMap<String, String>) countryIt
						.next();
				if ("Y".equals(countryMap.get("flagType"))) {
					if (countryMap.get("codeEnName").toUpperCase()
							.indexOf("SCHENGEN") == -1) {
						shenGen += countryMap.get("codeName") + "/"
								+ countryMap.get("codeEnName") + "，";
					} else {
						schengenFlag = true;
					}
				} else if ("N".equals(countryMap.get("flagType"))
						|| "".equals(countryMap.get("flagType"))) {

					unShenGen += countryMap.get("codeName") + "/"
							+ countryMap.get("codeEnName") + "，";
				}
			}
			String shenGenChar = "申根国家/Schengen ";
			if (StringUtil.isNotEmpty(shenGen)) {
				dest = dest + shenGen + shenGenChar;
			}
			if (StringUtil.isEmpty(shenGen) && schengenFlag) {
				dest = dest + shenGenChar;
			}
			if (StringUtil.isNotEmpty(unShenGen)) {
				dest = dest + unShenGen;
			}
		} catch (Exception e) {
			logger.info("查询目的地异常" + e.getMessage(), e);
		}
		dest = dest.substring(0, dest.length() - 1) + ";";
		return dest;
	}

	public String getCountryText1015(String comCode, String destinationCountry) {
		String[] country = destinationCountry.split(",");
		String countryEn = "";
		if (country != null && country.length > 0) {
			for (String s : country) {
				Dictionary d = getNameByCodeValue(comCode, "CountryCode",
						s.trim());
				countryEn = d.getCodeEnName();
				if (StringUtil.isEmpty(d.getFlagType())
						&& "Y".equals(d.getFlagType().trim())) {
					return countryEn;
				}
			}
		}
		return countryEn;
	}

	public void sdinformationMulInsuredSave(SDOrdersSchema sdor,
			SDInformationSchema sdinf, SDInformationAppntSchema sdapp,
			String crisktype) {
		int index = 1;
		for (SDInformationInsuredSchema sdinformationinsured : sdinformationinsuredList) {

			if (sdinformationinsured != null) {

				sdinformationinsured.setId(PubFun.GetInformationInsuredID(sdinf
						.getorderSn()));
				sdinformationinsured.setcreateDate(createDate);
				sdinformationinsured.setmodifyDate(modifyDate);
				sdinformationinsured.setsdinformation_id(sdinf.getId());
				sdinformationinsured.setrecognizeeOperate("2");
				sdinformationinsured.setmulInsuredFlag("rid_orther");
				// 被保人编号，规则需要修改
				String recognizeeSn = PubFun.GetSDInsuredSn();
				SDInformationRiskTypeSchema sdinformationRiskTypes = new SDInformationRiskTypeSchema();
				sdinformationRiskTypes.setId(PubFun
						.GetInformationRiskTypeID(sdinf.getorderSn()));
				sdinformationRiskTypes.setcreateDate(createDate);
				sdinformationRiskTypes.setmodifyDate(modifyDate);
				sdinformationRiskTypes
						.setsdinformationinsured_id(sdinformationinsured
								.getId());
				// 订单号
				sdinformationRiskTypes.setorderSn(sdor.getorderSn());
				// 订单明细编号
				sdinformationRiskTypes.setinformationSn(sdinf
						.getinformationSn());
				// 被保人编号
				sdinformationRiskTypes.setrecognizeeSn(recognizeeSn);
				// 投保人编号
				sdinformationRiskTypes.setapplicantSn(sdapp.getapplicantSn());
				// 保单号，需要处理
				sdinformationRiskTypes.setpolicyNo("");
				// 险种编码
				sdinformationRiskTypes.setriskCode(sdinf.getproductId());
				// 险种名称
				sdinformationRiskTypes.setriskName(sdinf.getproductName());
				// 保额，需要处理
				sdinformationRiskTypes.setamnt(this.getInsuredAmnt(dutyFactor,
						sdinf.getplanCode()));
				// 份数，需要处理
				sdinformationRiskTypes.setmult("1");
				// 保费，需要处理
				sdinformationRiskTypes.settimePrem(sdinformationinsured
						.getrecognizeePrem());
				sdinformationRiskTypes.setproductPrice(sdinformationinsured
						.getrecognizeeTotalPrem());
				// 生效日期
				sdinformationRiskTypes.setsvaliDate(sdinf.getstartDate());
				// 失效日期
				sdinformationRiskTypes.setevaliDate(sdinf.getendDate());
				// 以下字段待定
				// 缴费年期类型
				sdinformationRiskTypes.setperiodFlag(sdinf.getchargeType());
				sdinformationRiskTypes.setperiod(sdinf.getchargeYear());// 缴费年期
				sdinformationRiskTypes.setelectronicCout("");// 电子保单保险公司路径
				sdinformationRiskTypes.setelectronicPath("");// 电子保单物理路径
				sdinformationRiskTypes.setinsurerFlag("");
				sdinformationRiskTypes.setinsureMsg("");
				trans.add(sdinformationRiskTypes, Transaction.INSERT);

				// 单独提取出函数设置被保人为本人时的数据
				sdinformationinsured.setinformationSn(sdinf.getinformationSn());
				sdinformationinsured.setorderSn(sdor.getorderSn());
				sdinformationinsured.setrecognizeeSn(recognizeeSn);
				String recognizeeIdentityTypeName = getNameByCodeTypePro(
						sdinf.getproductId(), sdinf.getinsuranceCompany(),
						"certificate",
						sdinformationinsured.getrecognizeeIdentityType());
				String recognizeeAppntRelationName = getNameByCodeTypePro(
						sdinf.getproductId(), sdinf.getinsuranceCompany(),
						"Relationship",
						sdinformationinsured.getrecognizeeAppntRelation());

				String recognizeeSexName = getNameByCodeTypePro(
						sdinf.getproductId(), sdinf.getinsuranceCompany(),
						"Sex", sdinformationinsured.getrecognizeeSex());
				sdinformationinsured.setrecognizeeSexName(recognizeeSexName);
				sdinformationinsured
						.setrecognizeeIdentityTypeName(recognizeeIdentityTypeName);
				sdinformationinsured
						.setrecognizeeAppntRelationName(recognizeeAppntRelationName);
				// 设置旅游目的地字段
				String destinationCountryText = "";
				if (StringUtil.isNotEmpty(sdinformationinsured
						.getdestinationCountry())
						&& !"1015".equals(sdInformation.getinsuranceCompany())) {
					destinationCountryText = getNameByCodeTypePro(
							sdinf.getproductId(), sdinf.getinsuranceCompany(),
							"CountryCode",
							sdinformationinsured.getdestinationCountry());
					sdinformationinsured
							.setdestinationCountryText(destinationCountryText);
				} else {
					String destinationCountry = sdinformationinsured
							.getdestinationCountry();
					destinationCountryText = setDestinationCountry(
							sdInformation.getinsuranceCompany(),
							destinationCountry);
					if ("2007".equals(sdinf.getinsuranceCompany())
							&& !StringUtil.isEmpty(destinationCountry)) {
						String CountryText = getCountryText2007("2007",
								destinationCountry);
						sdinformationinsured
								.setdestinationCountryText(CountryText);

					} else if ("1015".equals(sdinf.getinsuranceCompany())
							&& !StringUtil.isEmpty(destinationCountry)) {
						sdinformationinsured
								.setdestinationCountryText(getCountryText1015(
										"1015", destinationCountry));

					} else if (("2011".equals(sdinf.getinsuranceCompany())
							|| "2023".equals(sdinf.getinsuranceCompany()) || "2071"
								.equals(sdinf.getinsuranceCompany()))
							&& !StringUtil.isEmpty(destinationCountry)) {
						sdinformationinsured
								.setdestinationCountryText(getSchengenCountryText(
										sdinf.getinsuranceCompany(),
										destinationCountry));

					} else {
						if (!StringUtil.isEmpty(destinationCountry)) {
							sdinformationinsured
									.setdestinationCountryText(destinationCountryText);
						}
					}
				}

				// 设置被保人中英文姓名
				sdinformationinsured
						.setrecognizeeLashName(CountryChineseSpelling
								.convertLastName(sdinformationinsured
										.getrecognizeeName()));
				sdinformationinsured
						.setrecognizeeFirstName(CountryChineseSpelling
								.convertFirstName(sdinformationinsured
										.getrecognizeeName()));
				sdinformationinsured.setrecognizeeEnName(CountryChineseSpelling
						.convert(sdinformationinsured.getrecognizeeName()));
				// 被保人邮编
				if (StringUtil.isEmpty(sdinformationinsured
						.getrecognizeeZipCode())
						&& StringUtil.isNotEmpty(sdinformationinsured
								.getrecognizeeArea1())) {
					sdinformationinsured.setrecognizeeZipCode(this.getZipInfo(
							recognizeeArea1, recognizeeArea2));
				}
				sdinformationinsured.setinsuredSn(sdor.getorderSn() + "_"
						+ index);
				index = index + 1;
				int tInsuredAge = com.sinosoft.sms.messageinterface.pubfun.PubFun
						.getAge(sdinformationinsured.getrecognizeeBirthday());
				sdinformationinsured.setrecognizeeAge(String
						.valueOf(tInsuredAge));
				// 处理SDOrderItemOth信息
				this.orderItemOthSave(sdinformationinsured, sdinf);
				sdInformationInsuredElementsSave(sdinformationinsured);
				trans.add(sdinformationinsured, Transaction.INSERT);
			}
		}
	}

	/**
	 * 设置目的地国家名称字段
	 * 
	 * @param insuranceCode
	 * @param destinationCountry
	 * 
	 */
	private String setDestinationCountry(String insuranceCode,
			String destinationCountry) {
		if (StringUtil.isEmpty(destinationCountry)) {
			return "";
		}

		String[] destinations = destinationCountry.split(",");
		Set<String> countrySet = new HashSet<String>();
		StringBuffer destinationText = new StringBuffer();
		int j = 0;
		String sql = "select 1 from zdcode where CodeType=? and ParentCode=? and CodeValue=?";
		QueryBuilder qb = new QueryBuilder(sql);
		qb.add("DestinationCountryText");
		qb.add("DestinationCountryText");
		qb.add(insuranceCode);
		boolean saveEnNameFlag = qb.executeInt() > 0;
		for (int i = 0; i < destinations.length; i++) {
			String name = null;
			String CountryCode = destinations[i].trim();
			if (saveEnNameFlag) {
				name = (getCodeEnNameByCodeValue(insuranceCode, "CountryCode",
						CountryCode));
			} else {
				name = (findCountryNameByCode(CountryCode));
			}

			if (StringUtil.isEmpty(name)) {
				continue;
			}
			countrySet.add(CountryCode);
			if (j > 0) {
				if (destinationText.indexOf(name) == -1) {
					destinationText.append(",");
				}
			}
			if (destinationText.indexOf(name) == -1) {
				destinationText.append(name);
			}

			j++;
		}
		return destinationText.toString();
	}

	private void sdInformationInsuredElementsSave(SDInformationInsuredSchema ins) {
		if (riskAppFactior != null) {
			for (int i = 0; i < riskAppFactior.size(); i++) {
				String appFactiorType = riskAppFactior.get(i).getFactorType()
						.toString();
				if (StringUtil.isNotEmpty(appfactorMap.get(appFactiorType))) {
					informationInsuredElements = new SDInformationInsuredElementsSchema();
					informationInsuredElements.setid(PubFun
							.GetInformationInsuredElementsID(sdInformation
									.getorderSn()));
					informationInsuredElements.setcreateDate(createDate);
					informationInsuredElements.setmodifyDate(modifyDate);
					informationInsuredElements.setsdinformationInsured_id(ins
							.getId());
					informationInsuredElements.setelementsType(appFactiorType);
					informationInsuredElements.setelementsSn(PubFun
							.GetSDElementSn());
					informationInsuredElements.setorderSn(sdorder.getorderSn());
					informationInsuredElements.setrecognizeeSn(ins
							.getrecognizeeSn());
					informationInsuredElements.setinformationSn(sdInformation
							.getinformationSn());
					informationInsuredElements.setelementsSn(riskAppFactior
							.get(i).getAppFactorCode());
					informationInsuredElements.setelementsValue(appfactorMap
							.get(appFactiorType));

					trans.add(informationInsuredElements, Transaction.INSERT);
				}
			}
		}
	}

	/**
	 * 处理orderitemoht
	 * 
	 * @param t
	 */
	public void orderItemOthSave(SDInformationInsuredSchema t,
			SDInformationSchema sdinf) {
		sdorderitemoth = new SDOrderItemOthSchema();
		sdorderitemoth.setid(PubFun.GetOrderItemOthID(sdinf.getorderSn()));
		sdorderitemoth.setcreateDate(createDate);
		sdorderitemoth.setmodifyDate(modifyDate);
		sdorderitemoth.setorderSn(sdinf.getorderSn());
		sdorderitemoth.setrecognizeeSn(t.getrecognizeeSn());
		sdorderitemoth.setorderitemSn(PubFun.GetItemOthNo());
		sdorderitemoth.setsdinformationinsured_id(t.getId());
		if ("2023".equals(sdinf.getinsuranceCompany())
				|| "2011".equals(sdinf.getinsuranceCompany())) {
			String outcode = PubFun.getHTSN(sdinf.getproductOutCode());
			sdorderitemoth.settpySn(outcode);// 存入华泰特殊编号
		}
		trans.add(sdorderitemoth, Transaction.INSERT);
	}

	public Date getEndDate(String cDate) {

		String tDate = cDate + " 23:59:59";
		Date mDate = new Date();
		try {
			mDate = sdf_2.parse(tDate);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		return mDate;
	}

	/**
	 * 取得被保人信息
	 * 
	 * @param sheet
	 *            被保人信息
	 * @return
	 */
	public boolean getInformationinsured(HSSFSheet sheet) {
		if (sheet == null || sheet.getLastRowNum() < 11) {
			this.addError("被保人模板中没有数据!");
			return false;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SDInformationInsuredSchema sdInformationInsured;
		int selfCount = 0;
		for (int i = 11; i <= sheet.getLastRowNum(); i++) {
			sdInformationInsured = new SDInformationInsuredSchema();
			HSSFRow row = sheet.getRow(i);
			if (row != null) {
				// 与投保人关系
				if (row.getCell(0) != null
						&& !StringUtil.isEmpty(row.getCell(0)
								.getStringCellValue())) {
					String relation = row.getCell(0).getStringCellValue()
							.trim();
					String relationName = getNameByCodeTypePro(
							sdInformation.getproductId(),
							sdInformation.getinsuranceCompany(),
							"Relationship", relation);
					if (!StringUtil.isEmpty(relationName)) {
						sdInformationInsured
								.setrecognizeeAppntRelation(relation);
						sdInformationInsured
								.setrecognizeeAppntRelationName(relationName);
						if ("本人".equals(relationName.trim())) {
							sdInformationInsured.setisSelf("Y");
								selfCount++;
						} else {
							sdInformationInsured.setisSelf("N");
						}
					} else {
						this.addError("被保人模板第" + (i + 1) + "行,与投保人关系无效！");
						return false;
					}

				} else {
					this.addError("被保人模板第" + (i + 1) + "行,与投保人关系不能是空！");
					return false;
				}

				if (selfCount >= 2) {
					this.addError("被保人模板第" + (i + 1) + "行,与投保人关系:已添加本人为被保人,不能重复添加！");
					return false;
				}
				
				// 姓名
				if (row.getCell(1) != null
						&& !StringUtil.isEmpty(row.getCell(1)
								.getStringCellValue())) {
					sdInformationInsured.setrecognizeeName(row.getCell(1)
							.getStringCellValue().trim());
				} else {
					this.addError("被保人模板第" + (i + 1) + "行,姓名不能是空！");
					return false;
				}

				// 证件类型
				if (row.getCell(2) != null
						&& !StringUtil.isEmpty(row.getCell(2)
								.getStringCellValue())) {
					String certificate = row.getCell(2).getStringCellValue()
							.trim();
					// 得到证件类型名称
					String identityTypeName = getNameByCodeTypePro(
							sdInformation.getproductId(),
							sdInformation.getinsuranceCompany(), "certificate",
							certificate);
					// 证件类型是否有效
					if (StringUtil.isEmpty(identityTypeName)) {
						this.addError("被保人模板第" + (i + 1) + "行,证件类型无效！");
						return false;

					} else {
						sdInformationInsured
								.setrecognizeeIdentityType(certificate);
						sdInformationInsured
								.setrecognizeeIdentityTypeName(identityTypeName);
					}

				} else {
					this.addError("被保人模板第" + (i + 1) + "行,证件类型不能是空！");
					return false;
				}

				// 证件号
				if (row.getCell(3) != null
						&& !StringUtil.isEmpty(row.getCell(3)
								.getStringCellValue())) {
					sdInformationInsured.setrecognizeeIdentityId(row.getCell(3)
							.getStringCellValue().trim());
				} else {
					this.addError("被保人模板第" + (i + 1) + "行,证件号不能是空！");
					return false;
				}

				// 性别
				if (row.getCell(4) != null
						&& !StringUtil.isEmpty(row.getCell(4)
								.getStringCellValue())) {
					String sex = row.getCell(4).getStringCellValue().trim();
					String recognizeeSexName = getNameByCodeTypePro(
							sdInformation.getproductId(),
							sdInformation.getinsuranceCompany(), "Sex", sex);
					// 性别是否有效
					if (StringUtil.isEmpty(recognizeeSexName)) {
						this.addError("被保人模板第" + (i + 1) + "行,性别无效！");
						return false;

					} else {
						sdInformationInsured.setrecognizeeSex(sex);
						sdInformationInsured
								.setrecognizeeSexName(recognizeeSexName);
					}
				} else {
					this.addError("被保人模板第" + (i + 1) + "行,性别不能是空！");
					return false;
				}

				// 生日
				if (row.getCell(5) != null
						&& !StringUtil.isEmpty(row.getCell(5)
								.getStringCellValue())) {
					sdInformationInsured.setrecognizeeBirthday(row.getCell(5)
							.getStringCellValue().trim());
				}

				// 手机号码
				if (row.getCell(6) != null
						&& !StringUtil.isEmpty(row.getCell(6)
								.getStringCellValue())) {
					sdInformationInsured.setrecognizeeMobile(row.getCell(6)
							.getStringCellValue().trim());
				} else {
					this.addError("被保人模板第" + (i + 1) + "行,手机号码不能是空！");
					return false;
				}

				// 电子邮箱
				if (row.getCell(7) != null
						&& !StringUtil.isEmpty(row.getCell(7)
								.getStringCellValue())) {
					sdInformationInsured.setrecognizeeMail(row.getCell(7)
							.getStringCellValue().trim());
				} else {
					this.addError("被保人模板第" + (i + 1) + "行,电子邮箱不能是空！");
					return false;
				}

				if (areaMap.size() > 0) {
					String area1 = "";
					// 所在地区1
					if (row.getCell(8) != null
							&& !StringUtil.isEmpty(row.getCell(8)
									.getStringCellValue())) {
						area1 = row.getCell(8).getStringCellValue().trim();
						// 取得所在一级地区
						recognizeeArea1 = areaMap.get(area1);
						if (StringUtil.isNotEmpty(recognizeeArea1)) {
							sdInformationInsured.setrecognizeeArea1(area1);
						} else {
							this.addError("被保人模板第" + (i + 1) + "行,所在地区1无效！");
							return false;
						}

					}

					// 所在地区2
					if (row.getCell(9) != null
							&& !StringUtil.isEmpty(row.getCell(9)
									.getStringCellValue())) {
						String strArea2 = row.getCell(9).getStringCellValue()
								.trim();
						// 取得所在二级地区
						Map<String, String> area2Map = getArea2(area1,
								productId, sdInformation.getinsuranceCompany());
						if (area2Map != null) {
							recognizeeArea2 = area2Map.get(strArea2);
						}
						if (StringUtil.isNotEmpty(recognizeeArea2)) {
							sdInformationInsured.setrecognizeeArea2(strArea2);
						} else {
							this.addError("被保人模板第" + (i + 1) + "行,所在地区2无效！");
							return false;
						}

					}
				}

				// 联系地址
				if (row.getCell(10) != null
						&& !StringUtil.isEmpty(row.getCell(10)
								.getStringCellValue())) {
					sdInformationInsured.setrecognizeeAddress(row.getCell(10)
							.getStringCellValue().trim());
				}

				// 邮编
				if (row.getCell(11) != null
						&& !StringUtil.isEmpty(row.getCell(11)
								.getStringCellValue())) {
					sdInformationInsured.setrecognizeeZipCode(row.getCell(11)
							.getStringCellValue().trim());
				}

				// 职业
				if (row.getCell(12) != null
						&& !StringUtil.isEmpty(row.getCell(12)
								.getStringCellValue())) {
					String occupation3 = row.getCell(12).getStringCellValue()
							.trim();
					if (validOccupCheck(sdInformation.getinsuranceCompany(),
							occupation3)) {
						sdInformationInsured
								.setrecognizeeOccupation3(occupation3);
					} else {
						this.addError("被保人模板第" + (i + 1) + "行,职业无效！");
						return false;
					}

					String occupation2 = getParentOccup(
							sdInformation.getinsuranceCompany(), occupation3);
					if (!StringUtil.isEmpty(occupation2)) {
						String occupation1 = getParentOccup(
								sdInformation.getinsuranceCompany(),
								occupation2);
						if (!StringUtil.isEmpty(occupation1)) {
							sdInformationInsured
									.setrecognizeeOccupation1(occupation1);
							sdInformationInsured
									.setrecognizeeOccupation2(occupation2);
						} else {
							sdInformationInsured
									.setrecognizeeOccupation1(occupation2);
						}
					}
				}

				// 旅游目的地
				List<Dictionary> listCountryCode = findListByCom("CountryCode",
						sdInformation.getinsuranceCompany(), productId);
				if (listCountryCode != null && listCountryCode.size() > 0) {
					if (row.getCell(13) != null
							&& !StringUtil.isEmpty(row.getCell(13)
									.getStringCellValue())) {
						String countryName = row.getCell(13)
								.getStringCellValue().trim();
						String[] codeList = countryName.split(",");
						String countryCode = "";
						int flag = 0;
						countryName = "";
						for (int h = 0; h < codeList.length; h++) {
							for (int k = 0; k < listCountryCode.size(); k++) {
								if (codeList[h].equals(listCountryCode.get(k)
										.getCodeName())) {
									if (!StringUtil.isEmpty(countryCode)) {
										countryCode += ",";
										countryName += ",";
									}
									countryCode += listCountryCode.get(k)
											.getCodeValue();
									countryName += listCountryCode.get(k)
											.getCodeName();
									flag++;
								}
							}
						}

						if (flag == codeList.length) {
							sdInformationInsured
									.setdestinationCountry(countryCode);
							sdInformationInsured
									.setdestinationCountryText(countryName);
						} else {
							this.addError("被保人模板第" + (i + 1) + "行,旅游目的地无效！");
							return false;
						}

					}
				}

				// 国籍
				if (row.getCell(14) != null
						&& !StringUtil.isEmpty(row.getCell(14)
								.getStringCellValue())) {
					String nationality = row.getCell(14).getStringCellValue()
							.trim();
					nationality = getNationalityCode(
							sdInformation.getinsuranceCompany(), "nationality",
							nationality);
					sdInformationInsured.setnationality(nationality);
				}

				// 身高
				if (row.getCell(15) != null
						&& !StringUtil.isEmpty(row.getCell(15)
								.getStringCellValue())) {
					sdInformationInsured.setheight(row.getCell(15)
							.getStringCellValue().trim());
				}

				// 体重
				if (row.getCell(16) != null
						&& !StringUtil.isEmpty(row.getCell(16)
								.getStringCellValue())) {
					sdInformationInsured.setheight(row.getCell(16)
							.getStringCellValue().trim());
				}

				// 已购身故保额
				if (row.getCell(17) != null
						&& !StringUtil.isEmpty(row.getCell(17)
								.getStringCellValue())) {
					sdInformationInsured.sethaveBuy(row.getCell(17)
							.getStringCellValue().trim());
				}

				// 航班号
				if (row.getCell(18) != null
						&& !StringUtil.isEmpty(row.getCell(18)
								.getStringCellValue())) {
					sdInformationInsured.setflightNo(row.getCell(18)
							.getStringCellValue().trim());
				}

				// 起飞日期
				if (row.getCell(19) != null
						&& !StringUtil.isEmpty(row.getCell(19)
								.getStringCellValue())) {
					try {
						sdInformationInsured.setflightTime(sdf.parse(row
								.getCell(19).getStringCellValue().trim()));
					} catch (ParseException e) {
						logger.error(e.getMessage(), e);
						this.addError("被保人模板第" + (i + 1) + "行,起飞日期格式错误！");
						return false;
					}
				}

				// 起飞地点
				if (row.getCell(20) != null
						&& !StringUtil.isEmpty(row.getCell(20)
								.getStringCellValue())) {
					sdInformationInsured.setflightLocation(row.getCell(20)
							.getStringCellValue().trim());
				}

				// 留学学校或境外工作公司
				if (row.getCell(21) != null
						&& !StringUtil.isEmpty(row.getCell(21)
								.getStringCellValue())) {
					sdInformationInsured.setschoolOrCompany(row.getCell(21)
							.getStringCellValue().trim());
				}

				// 境外工作职业
				if (row.getCell(22) != null
						&& !StringUtil.isEmpty(row.getCell(22)
								.getStringCellValue())) {
					sdInformationInsured.setoverseasOccupation(row.getCell(22)
							.getStringCellValue().trim());
				}

				// 驾校名称
				if (row.getCell(23) != null
						&& !StringUtil.isEmpty(row.getCell(23)
								.getStringCellValue())) {
					sdInformationInsured.setdriverSchoolName(row.getCell(23)
							.getStringCellValue().trim());
				}

				// 学员编号
				if (row.getCell(24) != null
						&& !StringUtil.isEmpty(row.getCell(24)
								.getStringCellValue())) {
					sdInformationInsured.setdriverNo(row.getCell(24)
							.getStringCellValue().trim());
				}

				sdinformationinsuredList.add(sdInformationInsured);
			}
		}
		return true;
	}

	/**
	 * 取得国籍编码
	 * 
	 * @param insuranceCode
	 *            保险公司编码
	 * @param codetype
	 *            类型
	 * @param codeName
	 *            国籍名称
	 * @return 国籍编码
	 */
	public String getNationalityCode(String insuranceCode, String codetype,
			String codeName) {
		String sql = " select codeValue from dictionary where codeType = ? and insuranceCode = ? and codeName = ? order by id asc ";
		QueryBuilder qb = new QueryBuilder(sql);
		qb.add(codetype);
		qb.add(insuranceCode);
		qb.add(codeName);
		return qb.executeString();
	}

	/**
	 * 取得投保人信息
	 * 
	 * @param sheet
	 *            投保人信息
	 * @return
	 */
	public boolean getInformationAppnt(HSSFSheet sheet) {
		if (sheet == null || sheet.getLastRowNum() < 11) {
			this.addError("投保人模板中没有数据!");
			return false;
		}

		if (sheet.getLastRowNum() >= 12) {
			this.addError("投保人信息只能录一条记录!");
			return false;
		}

		for (int i = 11; i <= sheet.getLastRowNum(); i++) {
			sdinformationAppnt = new SDInformationAppntSchema();
			HSSFRow row = sheet.getRow(i);
			if (row != null) {
				// 姓名
				if (row.getCell(0) != null
						&& !StringUtil.isEmpty(row.getCell(0)
								.getStringCellValue())) {
					sdinformationAppnt.setapplicantName(row.getCell(0)
							.getStringCellValue().trim());
				} else {
					this.addError("投保人模板第" + (i + 1) + "行,姓名不能是空！");
					return false;
				}

				// 证件类型
				if (row.getCell(1) != null
						&& !StringUtil.isEmpty(row.getCell(1)
								.getStringCellValue())) {
					String certificate = row.getCell(1).getStringCellValue()
							.trim();
					// 得到证件类型名称
					String applicantIdentityTypeName = getNameByCodeTypePro(
							sdInformation.getproductId(),
							sdInformation.getinsuranceCompany(), "certificate",
							certificate);
					// 证件类型是否有效
					if (StringUtil.isEmpty(applicantIdentityTypeName)) {
						this.addError("投保人模板第" + (i + 1) + "行,证件类型无效！");
						return false;

					} else {
						sdinformationAppnt
								.setapplicantIdentityType(certificate);
						sdinformationAppnt
								.setapplicantIdentityTypeName(applicantIdentityTypeName);
					}

				} else {
					this.addError("投保人模板第" + (i + 1) + "行,证件类型不能是空！");
					return false;
				}

				// 证件号
				if (row.getCell(2) != null
						&& !StringUtil.isEmpty(row.getCell(2)
								.getStringCellValue())) {
					sdinformationAppnt.setapplicantIdentityId(row.getCell(2)
							.getStringCellValue().trim());
				} else {
					this.addError("投保人模板第" + (i + 1) + "行,证件号不能是空！");
					return false;
				}

				// 性别
				if (row.getCell(3) != null
						&& !StringUtil.isEmpty(row.getCell(3)
								.getStringCellValue())) {
					String sex = row.getCell(3).getStringCellValue().trim();
					String applicantSexName = getNameByCodeTypePro(
							sdInformation.getproductId(),
							sdInformation.getinsuranceCompany(), "Sex", sex);
					// 性别是否有效
					if (StringUtil.isEmpty(applicantSexName)) {
						this.addError("投保人模板第" + (i + 1) + "行,性别无效！");
						return false;

					} else {
						sdinformationAppnt.setapplicantSex(sex);
						sdinformationAppnt
								.setapplicantSexName(applicantSexName);
					}
				} else {
					this.addError("投保人模板第" + (i + 1) + "行,性别不能是空！");
					return false;
				}

				// 生日
				if (row.getCell(4) != null
						&& !StringUtil.isEmpty(row.getCell(4)
								.getStringCellValue())) {
					sdinformationAppnt.setapplicantBirthday(row.getCell(4)
							.getStringCellValue().trim());
				}

				// 手机号码
				if (row.getCell(5) != null
						&& !StringUtil.isEmpty(row.getCell(5)
								.getStringCellValue())) {
					sdinformationAppnt.setapplicantMobile(row.getCell(5)
							.getStringCellValue().trim());
				} else {
					this.addError("投保人模板第" + (i + 1) + "行,手机号码不能是空！");
					return false;
				}

				// 电子邮箱
				if (row.getCell(6) != null
						&& !StringUtil.isEmpty(row.getCell(6)
								.getStringCellValue())) {
					sdinformationAppnt.setapplicantMail(row.getCell(6)
							.getStringCellValue().trim());
				} else {
					this.addError("投保人模板第" + (i + 1) + "行,电子邮箱不能是空！");
					return false;
				}

				if (areaMap.size() > 0) {
					String area1 = "";
					// 所在地区1
					if (row.getCell(7) != null
							&& !StringUtil.isEmpty(row.getCell(7)
									.getStringCellValue())) {
						area1 = row.getCell(7).getStringCellValue().trim();
						// 取得所在一级地区
						applicantArea1 = areaMap.get(area1);
						if (StringUtil.isNotEmpty(applicantArea1)) {
							sdinformationAppnt.setapplicantArea1(area1);
						} else {
							this.addError("投保人模板第" + (i + 1) + "行,所在地区1无效！");
							return false;
						}

					}

					// 所在地区2
					if (row.getCell(8) != null
							&& !StringUtil.isEmpty(row.getCell(8)
									.getStringCellValue())) {
						String strArea2 = row.getCell(8).getStringCellValue()
								.trim();
						// 取得所在二级地区
						Map<String, String> area2Map = getArea2(area1,
								productId, sdInformation.getinsuranceCompany());
						if (area2Map != null) {
							applicantArea2 = area2Map.get(strArea2);
						}
						if (StringUtil.isNotEmpty(applicantArea2)) {
							sdinformationAppnt.setapplicantArea2(strArea2);
						} else {
							this.addError("投保人模板第" + (i + 1) + "行,所在地区2无效！");
							return false;
						}

					}
				}

				// 联系地址
				if (row.getCell(9) != null
						&& !StringUtil.isEmpty(row.getCell(9)
								.getStringCellValue())) {
					sdinformationAppnt.setapplicantAddress(row.getCell(9)
							.getStringCellValue().trim());
				}

				// 邮编
				if (row.getCell(10) != null
						&& !StringUtil.isEmpty(row.getCell(10)
								.getStringCellValue())) {
					sdinformationAppnt.setapplicantZipCode(row.getCell(10)
							.getStringCellValue().trim());
				}

				// 职业
				if (row.getCell(11) != null
						&& !StringUtil.isEmpty(row.getCell(11)
								.getStringCellValue())) {
					String occupation3 = row.getCell(11).getStringCellValue()
							.trim();
					if (validOccupCheck(sdInformation.getinsuranceCompany(),
							occupation3)) {
						sdinformationAppnt.setapplicantOccupation3(occupation3);
					} else {
						this.addError("投保人模板第" + (i + 1) + "行,职业无效！");
						return false;
					}

					String occupation2 = getParentOccup(
							sdInformation.getinsuranceCompany(), occupation3);
					if (!StringUtil.isEmpty(occupation2)) {
						String occupation1 = getParentOccup(
								sdInformation.getinsuranceCompany(),
								occupation2);
						if (!StringUtil.isEmpty(occupation1)) {
							sdinformationAppnt
									.setapplicantOccupation1(occupation1);
							sdinformationAppnt
									.setapplicantOccupation2(occupation2);
						} else {
							sdinformationAppnt
									.setapplicantOccupation1(occupation2);
						}
					}
				}
			}
		}

		return true;
	}

	/**
	 * 职业编码是否合法
	 * 
	 * @param insuranceCompany
	 *            公司
	 * @param occup
	 *            职业编码
	 * @return
	 */
	private boolean validOccupCheck(String insuranceCompany, String occup) {
		String sql = "select count(*) from Occupation o where o.insuranceCompany= ? and o.id = ?";
		QueryBuilder queryBuilder = new QueryBuilder(sql);
		queryBuilder.add(insuranceCompany);
		queryBuilder.add(occup);
		int count = queryBuilder.executeInt();
		if (count > 0) {
			sql = "select count(*) from Occupation o where o.insuranceCompany= ? and o.parent_id = ?";
			queryBuilder = new QueryBuilder(sql);
			queryBuilder.add(insuranceCompany);
			queryBuilder.add(occup);
			count = queryBuilder.executeInt();
			if (count > 0) {
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	/**
	 * 取得上一级职业
	 * 
	 * @param insuranceCompany
	 *            公司
	 * @param occup
	 *            职业编码
	 * @return
	 */
	private String getParentOccup(String insuranceCompany, String occup) {
		String sql = "select a.id from Occupation a, Occupation o where a.insuranceCompany = o.insuranceCompany "
				+ "and o.parent_id = a.id and o.insuranceCompany = ? and o.id = ?";
		QueryBuilder queryBuilder = new QueryBuilder(sql);
		queryBuilder.add(insuranceCompany);
		queryBuilder.add(occup);
		DataTable dataTable = queryBuilder.executeDataTable();
		if (dataTable != null && dataTable.getRowCount() > 0) {
			return dataTable.getString(0, 0);
		} else {
			return "";
		}
	}

	/**
	 * 投保人信息保存
	 * 
	 * @param sdinformation
	 */
	private void informationAppntSave(SDInformationSchema sdinformation) {
		String tApplicantSn = PubFun.GetSDAppntSn();// 需要修改规则
		sdinformationAppnt.setId(PubFun.GetInformationAppntID(sdinformation
				.getorderSn()));
		sdinformationAppnt.setcreateDate(createDate);
		sdinformationAppnt.setmodifyDate(modifyDate);
		sdinformationAppnt.setsdinformaiton_id(sdinformation.getId());
		sdinformationAppnt.setapplicantSn(tApplicantSn);
		sdinformationAppnt.setinformationSn(sdinformation.getinformationSn());

		// 投保人中英文姓名信息
		sdinformationAppnt.setapplicantLastName(CountryChineseSpelling
				.convertLastName(sdinformationAppnt.getapplicantName()));
		sdinformationAppnt.setapplicantFirstName(CountryChineseSpelling
				.convertFirstName(sdinformationAppnt.getapplicantName()));
		sdinformationAppnt.setapplicantEnName(CountryChineseSpelling
				.convert(sdinformationAppnt.getapplicantName()));
		int appAge = com.sinosoft.sms.messageinterface.pubfun.PubFun
				.getAge(sdinformationAppnt.getapplicantBirthday());
		sdinformationAppnt.setapplicantAge(String.valueOf(appAge));
		// 投保人所在地区邮编
		if (StringUtil.isEmpty(sdinformationAppnt.getapplicantZipCode())
				&& !StringUtil.isEmpty(sdinformationAppnt.getapplicantArea1())) {
			sdinformationAppnt.setapplicantZipCode(this.getZipInfo(
					applicantArea1, applicantArea2));
		}
	}

	/**
	 * 根据省、市得到邮编
	 * 
	 * @param cProvince
	 *            省
	 * @param cCity
	 *            市
	 * @return 邮编
	 */
	public String getZipInfo(String cProvince, String cCity) {
		String tZipCode = "000000";
		String sql = " SELECT zipcode FROM sdzipinfo WHERE (INSTR(?,province) OR province LIKE ?) AND (INSTR(?,city) OR city LIKE ?) LIMIT 1 ";
		String sql1 = " SELECT zipcode FROM sdzipinfo WHERE (INSTR(?,province) OR province LIKE ?) ORDER BY zipcode ASC LIMIT 1 ";

		QueryBuilder qb = new QueryBuilder(sql);

		qb.add(cProvince);
		qb.add("%" + cProvince + "%");
		qb.add(cCity);
		qb.add("%" + cCity + "%");
		if (qb.executeInt() >= 1) {
			tZipCode = qb.executeOneValue().toString();
		} else {
			QueryBuilder qb1 = new QueryBuilder(sql1);

			qb1.add(cProvince);
			qb1.add("%" + cProvince + "%");
			tZipCode = qb1.executeOneValue().toString();
		}

		return tZipCode;
	}

	/**
	 * 添加错误信息
	 * 
	 * @param errorMessage
	 *            错误信息
	 */
	protected void addError(String errorMessage) {
		CError tError = new CError();
		tError.moduleName = this.getClass().getName();
		tError.errorMessage = errorMessage;
		this.mErrors.addOneError(tError);
	}

	public void updateOrder() {
		String ordersn = $V("ordersn");
		String CmsBuySource = $V("CmsBuySource");
		String buyForCustomerFlag = $V("buyForCustomerFlag");
		String oldOrdersn = $V("oldOrdersn");
		Transaction trans = new Transaction();
		trans.add(new QueryBuilder("update sdorders set channelsn=? where orderSn=? ", CmsBuySource, ordersn));
		if (StringUtil.isNotEmpty(buyForCustomerFlag)) {
			int count = new QueryBuilder("select count(1) from buyforcustomeroldsn where Ordersn=? ", ordersn).executeInt();
			if (count > 0) {
				QueryBuilder qb = new QueryBuilder("update buyforcustomeroldsn set BuyForCustomerFlag=?,OldOrderSn=? where Ordersn=? ");
				qb.add(buyForCustomerFlag);
				qb.add(oldOrdersn);
				qb.add(ordersn);
				trans.add(qb);
			} else {
				String memberId = new QueryBuilder("select memberId from sdorders where orderSn=? ", ordersn).executeString();
				BuyForCustomerOldSnSchema buyForCustomerOldSnSchema = new BuyForCustomerOldSnSchema();
				buyForCustomerOldSnSchema.setid(NoUtil.getMaxNo("buyForCustomerOldSn"));
				buyForCustomerOldSnSchema.setOrdersn(ordersn);
				buyForCustomerOldSnSchema.setBuyForCustomerFlag(buyForCustomerFlag);
				buyForCustomerOldSnSchema.setOldOrderSn(oldOrdersn);
				buyForCustomerOldSnSchema.setAddTime(new Date());
				buyForCustomerOldSnSchema.setAddUser(memberId);
				trans.add(buyForCustomerOldSnSchema, Transaction.INSERT);
				
			}
		}
		if (trans.commit()) {
			Response.setLogInfo(1, "更新成功！");
			return;
		} else {
			Response.setLogInfo(0, "更新失败！");
			return;
		}
		
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

	public Map<String, String> getAppfactorMap() {
		return appfactorMap;
	}

	public void setAppfactorMap(Map<String, String> appfactorMap) {
		this.appfactorMap = appfactorMap;
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

	public Map<String, String> getAreaMap() {
		return areaMap;
	}

	public void setAreaMap(Map<String, String> areaMap) {
		this.areaMap = areaMap;
	}

	public String getApplicantArea1() {
		return applicantArea1;
	}

	public void setApplicantArea1(String applicantArea1) {
		this.applicantArea1 = applicantArea1;
	}

	public String getApplicantArea2() {
		return applicantArea2;
	}

	public void setApplicantArea2(String applicantArea2) {
		this.applicantArea2 = applicantArea2;
	}

	public String getRecognizeeArea1() {
		return recognizeeArea1;
	}

	public void setRecognizeeArea1(String recognizeeArea1) {
		this.recognizeeArea1 = recognizeeArea1;
	}

	public String getRecognizeeArea2() {
		return recognizeeArea2;
	}

	public void setRecognizeeArea2(String recognizeeArea2) {
		this.recognizeeArea2 = recognizeeArea2;
	}
}
