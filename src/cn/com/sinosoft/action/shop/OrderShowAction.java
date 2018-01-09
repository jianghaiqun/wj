package cn.com.sinosoft.action.shop;
 
import cn.com.sinosoft.entity.Area;
import cn.com.sinosoft.entity.Dictionary;
import cn.com.sinosoft.entity.InsuredShow;
import cn.com.sinosoft.entity.ModuleInfo;
import cn.com.sinosoft.entity.Occupation;
import cn.com.sinosoft.entity.OrderDutyFactor;
import cn.com.sinosoft.entity.OrderRiskAppFactor;
import cn.com.sinosoft.entity.ProductConfig;
import cn.com.sinosoft.entity.SDInformation;
import cn.com.sinosoft.entity.SDInformationAppnt;
import cn.com.sinosoft.entity.SDInformationDuty;
import cn.com.sinosoft.entity.SDInformationInsured;
import cn.com.sinosoft.entity.SDInformationProperty;
import cn.com.sinosoft.entity.SDInsuredHealth;
import cn.com.sinosoft.entity.SDOrder;
import cn.com.sinosoft.entity.SDOrderItem;
import cn.com.sinosoft.entity.SDRelationAppnt;
import cn.com.sinosoft.entity.TradeInformation;
import cn.com.sinosoft.service.AreaService;
import cn.com.sinosoft.service.DealDataService;
import cn.com.sinosoft.service.DictionaryService;
import cn.com.sinosoft.service.OccupationService;
import cn.com.sinosoft.service.OrderConfigNewService;
import cn.com.sinosoft.service.ProductConfigService;
import cn.com.sinosoft.service.ProductPeriodService;
import cn.com.sinosoft.service.QuestionPaperService;
import cn.com.sinosoft.service.SDInformationAppntService;
import cn.com.sinosoft.service.SDInformationInsuredService;
import cn.com.sinosoft.service.SDInformationPropertyService;
import cn.com.sinosoft.service.SDInformationRiskTypeService;
import cn.com.sinosoft.service.SDInformationService;
import cn.com.sinosoft.service.SDInsuredHealthService;
import cn.com.sinosoft.service.SDOrderService;
import cn.com.sinosoft.service.SDRelationAppntService;
import com.sinosoft.framework.utility.CountryChineseSpelling;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FEMDutyAmntPremList;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FEMRiskFactorList;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
 
/**
 * 购买流程改造后核心类
 * 
 * @author sinosoft
 * 
 */
@ParentPackage("shop")
public class OrderShowAction extends BaseShopAction { 

	private static final long serialVersionUID = -7227426726084770262L;
	SimpleDateFormat sdf_1 = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdf_2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private List<ProductConfig> productConfigs = new ArrayList<ProductConfig>();
	private SDInformationAppnt sdinformationAppnt = new SDInformationAppnt();// 改造后投保人信息，add by cuishg
	private List<SDInformationInsured> sdinformationinsuredList = new ArrayList<SDInformationInsured>();// 改造后添加，用于多被保人存储--多被人录入 add bycuishg
	private List<SDInformationInsured> sdinsuredList = new ArrayList<SDInformationInsured>();// 改造后添加，用于多被保人excel上传 add bycuishg
	private SDInformationInsured sdinformationinsured = new SDInformationInsured();// 改造后添加，用于多被保人存储--单个被保人add by cuishg
	private List<SDInformationInsured> sdinformationinsuredList_Imp = new ArrayList<SDInformationInsured>();// 改造后添加，用于多被保人存储--多被人导入 add by cuishg
	private List<Dictionary> certificateList = new ArrayList<Dictionary>();// 证件类型
	private List<Dictionary> relationList = new ArrayList<Dictionary>();// 投被保人关系
	private List<Occupation> occupationList = new ArrayList<Occupation>();// 一级职业
	private List<Dictionary> sexList = new ArrayList<Dictionary>();// 性别
	private List<Area> areaList = new ArrayList<Area>();// 一级地区
	private List<OrderDutyFactor> dutyFactor = new ArrayList<OrderDutyFactor>();// 产品投保要素列表
	private List<OrderRiskAppFactor> riskAppFactior = new ArrayList<OrderRiskAppFactor>();// 责任投保要素列表
	private List<Dictionary> listCountryCode = new ArrayList<Dictionary>();// 旅游目的地
	private Map<String, List<Dictionary>> mapCountryCode = new HashMap<String, List<Dictionary>>();// 旅游目的地Map集合
	private Map<String,Map<String,List<Dictionary>>> mapCountryCodeList = new HashMap<String,Map<String,List<Dictionary>>>();// 旅游目的地Map集合
	
	
	private List<FEMRiskFactorList> periodList = new ArrayList<FEMRiskFactorList>();// 保障期限列表
	private List<FEMRiskFactorList> planList = new ArrayList<FEMRiskFactorList>();// 计划值列表
	private List<FEMRiskFactorList> occupList = new ArrayList<FEMRiskFactorList>();// 职业类别
	private List<FEMRiskFactorList> gradeList = new ArrayList<FEMRiskFactorList>();// 产品级别列表
	private List<FEMRiskFactorList> feeYearList = new ArrayList<FEMRiskFactorList>();// 缴费年期列表
	private List<FEMRiskFactorList> appLevelList = new ArrayList<FEMRiskFactorList>();// 缴费方式列表
	private List<FEMRiskFactorList> appTypeList = new ArrayList<FEMRiskFactorList>();// 投保类别列表
	private List<FEMRiskFactorList> testAgeList = new ArrayList<FEMRiskFactorList>();// 产品级别列表
	private List<String> detisnateList = new ArrayList<String>();// 旅游目的地集合
	private String insuredToCountry = "";// 旅游目的地
	private List<Dictionary> nationalityList = new ArrayList<Dictionary>();// 国籍值列表
	private List<SDInformationDuty> showDuty = null;// 订单结果页责任显示集合
	private List<Dictionary> travelModelList = new ArrayList<Dictionary>();
	private List<Dictionary> travelTypeList = new ArrayList<Dictionary>();
	private List<ModuleInfo> pageModuleList = new ArrayList<ModuleInfo>();
	private String orderId;
	private SDOrder sdorder = new SDOrder();// 改造后订单，add by cuishg
	private SDOrderItem sdorderitem = new SDOrderItem();
	private SDInformation sdinformation = new SDInformation();// 改造后订单详细 表（产品表），add by cuishg
	private Map<String, Object> sdinteraction = new HashMap<String, Object>();// 横向接口调用参数
	LinkedHashMap<Object, String> mLMap = new LinkedHashMap<Object, String>();
	private List<SDInsuredHealth> sdinsuredHealthList = new ArrayList<SDInsuredHealth>();// 健康告知列表
	private List<SDRelationAppnt> reappntList = new ArrayList<SDRelationAppnt>();// 快速录入被保人信息表

	private String applicantName;// 投保人姓名
	private String applicantBirthday;// 投保人出生日期
	private String productId;// 产品id
	private String loginFlag;//登录标记
	private String reappntName;//快速录入被保人姓名
	private String amntNum = "0";// 计算保额
	private String protectionPeriodTy;// 保障期限类型
	private String protectionPeriodValue = "";// 保障期限
	private String protectionPeriodLast;// 保障期限后段数值
	private String protectionPeriodFlag;// 是否存在保障期限
	private String mulPeople;// 保险责任多类人群列表
	private String period;// 保险期限
	private String plan;// 计划
	private String textAge;// 文字年龄
	private String dutyDisReq;// 保存用动态分项责任显示保额
	private String dutyPremReq;// 保存用动态分项保费
	private String orderStatus;// 订单状态
	private String status;// 是否修改标志
	private String[] baseInformations = new String[6];// 产品基本信息存session
	private String insureJson;// ajax传递投保试算参数
	private String dutyJson;// ajax传递责任参数
	private String recoJson;// ajax传递被保人生日，性别信息
	private String orderSn;// 订单号
	private Integer startPeriod = 1;// 起始日期与当前日期差
	private Integer endPeriod = 730;
	private String insureRiskType;
	private String KID;
	private String riskType;// 产品小类。add by cuishg
	private String mulInsuredFlag;// 多被保人标志
	private String payType;
	private String DestinationCountry;// 旅游木目的地
	private int insuredCount = 1;// 被保人人数，主要用于多被保人时，
	private int insuredImpCount = 1;// 被保人人数，主要用于多被保人Excel导入时，
	private String impValadate = "N";// 被保人人数，主要用于多被保人Excel导入时校验处理，
	private String appLevel = "";// 缴费类型
	private String appType = "";// 投保类别
	private String feeYear = "";// 缴费年期
	private String recognizeeOperate = "1";// 被保人操作标志 0 但被保人操作；1 多被保人下本人操作；2多被保人下其他被保人操作。用于页面页签的显示\
	private String effdate="";//保单生效日期，用于生日校验
	private String effdateNew="";//保单生效日期
	private String judge;
	private TradeInformation tradeInformation;
	private String supplierCode2;//保险公司编码
	private List<SDInformationProperty> sdinformationpropertyList = new ArrayList<SDInformationProperty>();
	private List<Dictionary> propertyToRecognizeeList = new ArrayList<Dictionary>();//财产与被保人关系
	private List<Dictionary> hourseTypeList = new ArrayList<Dictionary>();//房屋类型
	private List<Dictionary> hourseAgeList = new ArrayList<Dictionary>();//房龄
	private List<InsuredShow> showPropertys = new ArrayList<InsuredShow>();// 单被保人财产信息
	private SDInformationProperty sdinformationProperty = new SDInformationProperty();
    private String relationIsSelf = "";//被保人只能选本人标志
    private String productExcelTemp = "";//被保人批量导入excle模板路径
    private String productExcelFlag = "N";//被保人批量导入excle标志
    private String uploadfile = "";//Excel临时文件路径
	@Resource
	private DictionaryService dictionaryService;
	@Resource
	private OccupationService occupationService;
	@Resource
	private AreaService areaService;
	@Resource
	private ProductPeriodService productPeriodService;
	// 改造后添加
	@Resource
	private ProductConfigService productConfigService;
	@Resource
	private SDOrderService sdorderService;
	@Resource
	private SDInformationService sdinformationService;
	@Resource
	private SDInformationInsuredService sdinformationInsuredService;
	@Resource
	private SDInsuredHealthService sdinsuredHealthService;
	@Resource
	private SDInformationAppntService sdinformationAppntService;
	@Resource
	private DealDataService mDealDataService;
	@Resource
	private OrderConfigNewService orderConfigNewService;
	@Resource
	private QuestionPaperService questionPaperService;
	@Resource
	private SDInformationRiskTypeService sdinformationRiskTypeService;
	@Resource
	private SDInformationPropertyService sdinformationPropertyService;
	@Resource
	private SDRelationAppntService sdRelationAppntService;
	/**
	 * 产品详细页面点击“立即购买”，触发的方法
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String buyNow() {
	    
		Map<String, Object> paramter = new HashMap<String, Object>();
		try {
			sdorder.setProductTotalPrice(new BigDecimal("0.00"));
			 
			sdinformation.setProductPrice("0.00");// 商品原价
			// 处理产品保障期限与计划
			//this.setDutyFactorAndAmntNum(riskAppFactior, dutyFactor);
			// 保额
			//this.getInsuredAmnt(dutyFactor, plan);
			// sdinformation.setEnsure(this.period);//保障（产品中心值）
			//sdinformation.setEnsureLimitType(this.protectionPeriodTy.toUpperCase());// 保障期限类型
			//sdinformation.setEnsureLimit(this.protectionPeriodLast);// 保障期限
			//setSession("baseInformation", BaseInformation);
			sdinformation.setEnsure("365");
			sdinformation.setEnsureDisplay("1年(此项信息与是否配置无关)");
			sdinformation.setPlanCode("A");
			sdinformation.setPlanName("A计划(此项信息与是否配置无关)");
			sdinformation.setProductId("000000000");
			sdinformation.setProductName("000000000");
			String insuranceCompanySn = "000000000";// 保险公司编码
			insuranceCompanySn = insuranceCompanySn.substring(0, 4);
			sdinformation.setInsuranceCompany(insuranceCompanySn);
			riskType = "0000";// 产品小类
			dataBuild();

			init(insuranceCompanySn);
			if (getLoginMember() != null) {
				sdorder.setMemberId(getLoginMember().getId());
				loginFlag = "true";
			}
			 
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionError("没有找到该产品，请重新选择！");
			return ERROR;
		}
		String productID = productId;// A01 产品ID
		// 模块路径
		PageModule pm = new PageModule();
		pageModuleList = pm.showPage(productID);
		productExcelTemp = pm.showExcelModule(productID);
		if (StringUtil.isNotEmpty(productExcelTemp)) {
			productExcelFlag = "Y";
		}
		String templateURL = "inspageone";
		return templateURL;
	}
	/**
	 * 
	 * 产品投保要素组装
	 */
	private void dataBuild() {
		if (riskAppFactior != null && riskAppFactior.size() > 0) {
			for (OrderRiskAppFactor orderRiskAppFactor : riskAppFactior) {
				// 计划列表
				if ("Plan".equals(orderRiskAppFactor.getFactorType())){
					
					for(int i=0;i<orderRiskAppFactor.getFactorValue().size();i++){
						if(orderRiskAppFactor.getFactorValue().get(i).getFactorValue().equals(plan)){
							planList.addAll(orderRiskAppFactor.getFactorValue());
						}
					} 
				}
				// 保险期限列表
				if ("Period".equals(orderRiskAppFactor.getFactorType())) {
					periodList.addAll(orderRiskAppFactor.getFactorValue());
				}
				// 职业类别列表
				if ("Occup".equals(orderRiskAppFactor.getFactorType())) {
					occupList.addAll(orderRiskAppFactor.getFactorValue());
				}
				// 产品级别列表
				if ("Grade".equals(orderRiskAppFactor.getFactorType())) {
					gradeList.addAll(orderRiskAppFactor.getFactorValue());
				}
				// 缴费年期列表
				if ("FeeYear".equals(orderRiskAppFactor.getFactorType())) {
					feeYearList.addAll(orderRiskAppFactor.getFactorValue());
				}
				// 缴费方式列表
				if ("AppLevel".equals(orderRiskAppFactor.getFactorType())) {
					appLevelList.addAll(orderRiskAppFactor.getFactorValue());
				}
				// 投保类别列表
				if ("AppType".equals(orderRiskAppFactor.getFactorType())) {
					appTypeList.addAll(orderRiskAppFactor.getFactorValue());
				}
				// 投保类别列表
				if ("TestAge".equals(orderRiskAppFactor.getFactorType())) {
					testAgeList.addAll(orderRiskAppFactor.getFactorValue());
				}
			}
		}
	}
	private void init(String insuranceCompanySn) {
		certificateList = dictionaryService.findListByCom("certificate", insuranceCompanySn);
		Dictionary d = new Dictionary();
		d.setCodeName("本人");
		relationList.add(d);
		occupationList = occupationService.findSuperByCom(insuranceCompanySn,productId);// 查询一级职业
		sexList = dictionaryService.findListByCom("Sex", insuranceCompanySn);
		areaList = areaService.findSuperByCom(productId, insuranceCompanySn);
		listCountryCode = dictionaryService.findListByCom("CountryCode", insuranceCompanySn, productId);
		mapCountryCode = this.getCountryMap();
		propertyToRecognizeeList = dictionaryService.findListByCom("propertyToRecognizee", insuranceCompanySn, productId);//财产与被保人关系
		hourseTypeList = dictionaryService.findListByCom("hourseType", insuranceCompanySn, productId);//房屋类型
		hourseAgeList =dictionaryService.findListByCom("hourseAge", insuranceCompanySn, productId);//房龄
		// 处理国家按照首字母排序
		productConfigs = productConfigService.findPCByRiskCode(productId, insuranceCompanySn);// 根据产品编码查询配置项信息
		nationalityList = dictionaryService.findListByCom("nationality", insuranceCompanySn);// 查询国籍列表
		travelModelList = dictionaryService.findListByCom("travelMode", insuranceCompanySn);
		travelTypeList = dictionaryService.findListByCom("travelType", insuranceCompanySn);
		String stp = productPeriodService.getStartPeriod(insuranceCompanySn, productId);
		if (stp != null && !"".equals(stp)) {
			startPeriod = Integer.parseInt(stp);
		} else {
			startPeriod = 1;
		}
		String sep = productPeriodService.getEndPeriod(insuranceCompanySn, productId);
		if (sep != null && !"".equals(sep)) {
			endPeriod = Integer.parseInt(sep);
		} else {
			endPeriod = 700;
		}
		 
		logger.info("地区==========={]", areaList.size());
	}
	/**
	 * 
	 * 处理保障期限与计划
	 */
	private void setDutyFactorAndAmntNum(List<OrderRiskAppFactor> riskAppFactior2, List<OrderDutyFactor> dutyFactor2) {
		//解决默认保费为0问题，删除掉多余代码 
		if (riskAppFactior2.size() > 0) {
			setSession("riskAppFactior", riskAppFactior2);
			for (int i = 0; i < riskAppFactior2.size(); i++) {
				OrderRiskAppFactor orderRiskAppFactor = riskAppFactior2.get(i);

				// 计划列表
				List<FEMRiskFactorList> tFEMRiskFactorList = null;
				tFEMRiskFactorList = orderRiskAppFactor.getFactorValue();
				for (FEMRiskFactorList t : tFEMRiskFactorList) {
					if ("Plan".equals(orderRiskAppFactor.getFactorType())) {
						try{
							if(StringUtil.isNotEmpty(plan)){
								plan = java.net.URLDecoder.decode(plan, "UTF-8"); 
							} 
						}catch(Exception e){
							logger.error(e.getMessage(), e);
						}
						if (plan.equals(t.getFactorValue())) {
							String tPlanName = t.getFactorDisplayValue();
							this.sdinformation.setPlanCode(java.net.URLDecoder.decode(plan));
							this.sdinformation.setPlanName(tPlanName);
						}

					}
					// 保险期限
					if ("Period".equals(orderRiskAppFactor.getFactorType())) {

						if (period.equals(t.getFactorValue())) {
							String tPeriodName = t.getFactorDisplayValue();
							String[] periods = t.getFactorValue().split("-");

							if (this.period != null && !"".equals(this.period)) {
								periods = period.split("-");
							}
							this.getPeriod(periods);
							sdinformation.setEnsure(period);
							this.sdinformation.setEnsureDisplay(tPeriodName);
						}
					}
					// 缴费年期
					if ("FeeYear".equals(orderRiskAppFactor.getFactorType())) {

						if (feeYear.equals(t.getFactorValue())) {
							String tFeeYearName = t.getFactorDisplayValue();
							sdinformation.setChargeYear(feeYear);
							this.sdinformation.setChargeYearName(tFeeYearName);
						}
					}
					// 缴费方式
					if ("AppLevel".equals(orderRiskAppFactor.getFactorType())) {

						if (appLevel.equals(t.getFactorValue())) {
							String tAppLevelName = t.getFactorDisplayValue();
							sdinformation.setAppLevel(appLevel);
							this.sdinformation.setAppLevelName(tAppLevelName);
						}
					}
					// 投保类别
					if ("AppType".equals(orderRiskAppFactor.getFactorType())) {

						if (appType.equals(t.getFactorValue())) {
							String tAppTypeName = t.getFactorDisplayValue();
							sdinformation.setAppType(appType);
							this.sdinformation.setAppTypeName(tAppTypeName);
						}
					}
				}
			}
		}
	}
	/**
	 * 重构旅游目的地集合
	 * 
	 * @return
	 */
	public Map<String, List<Dictionary>> getCountryMap() {

		Map<String, List<Dictionary>> tMap = new LinkedHashMap<String, List<Dictionary>>();
		List<Dictionary> tCountDictionaryList; 
		String fKey;
		for (Dictionary dic : listCountryCode) {
			fKey = String.valueOf(CountryChineseSpelling.getFirstAlpha(dic.getCodeName()).toCharArray()[0]);
			if ("申根国家".equals(dic.getCodeName())) {
				//申根国家要求显示在第一位
				fKey = "0" + fKey;
			}
			tCountDictionaryList = tMap.get(fKey);
			if (tCountDictionaryList == null) {
				tCountDictionaryList = new ArrayList<Dictionary>();
				tMap.put(fKey, tCountDictionaryList);
			}
			tCountDictionaryList.add(dic);
		}
		return new TreeMap<String, List<Dictionary>>(tMap);
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
		protectionPeriodFlag = "true";

	}

	/**
	 * 根据计划确定责任总保额
	 * 
	 * @param dutyFactor2
	 *            责任集合
	 * @return
	 */
	private String getInsuredAmnt(List<OrderDutyFactor> dutyFactor2, String cPlanCode) {

		List backup = new ArrayList();
		if (dutyFactor2.size() > 0) {
			for (OrderDutyFactor orderDutyFactor : dutyFactor2) {

				for (FEMDutyAmntPremList fEMDutyAmntPremList : orderDutyFactor.getFdAmntPremList()) {
					if (fEMDutyAmntPremList != null) {
						// 根据计划选择责任的保额
						if (cPlanCode != null && !"".equals(cPlanCode)) {
							if (cPlanCode.equals(fEMDutyAmntPremList.getAppFactorValue())) {
								backup.add(fEMDutyAmntPremList.getBackUp1());
							}
						} else {
							backup.add(fEMDutyAmntPremList.getBackUp1());
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
			setSession("dutyFactor", dutyFactor2);
		}
		Integer backNum = 0;
		for (int i = 0; i < backup.size(); i++) {
			if (backup.get(i) != null && !"".equals(backup.get(i).toString())) {
				backNum = backNum + Integer.parseInt(backup.get(i).toString());
			}
		} 
		return backNum.toString();
	}
	public List<ProductConfig> getProductConfigs() {
		return productConfigs;
	}

	public void setProductConfigs(List<ProductConfig> productConfigs) {
		this.productConfigs = productConfigs;
	}

	public List<Dictionary> getCertificateList() {
		return certificateList;
	}

	public void setCertificateList(List<Dictionary> certificateList) {
		this.certificateList = certificateList;
	}

	public List<Dictionary> getRelationList() {
		return relationList;
	}

	public void setRelationList(List<Dictionary> relationList) {
		this.relationList = relationList;
	}

	public List<Occupation> getOccupationList() {
		return occupationList;
	}

	public void setOccupationList(List<Occupation> occupationList) {
		this.occupationList = occupationList;
	}

	public List<Dictionary> getSexList() {
		return sexList;
	}

	public void setSexList(List<Dictionary> sexList) {
		this.sexList = sexList;
	}

	public List<Area> getAreaList() {
		return areaList;
	}

	public void setAreaList(List<Area> areaList) {
		this.areaList = areaList;
	}

	public List<Dictionary> getListCountryCode() {
		return listCountryCode;
	}

	public void setListCountryCode(List<Dictionary> listCountryCode) {
		this.listCountryCode = listCountryCode;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public List<OrderDutyFactor> getDutyFactor() {
		return dutyFactor;
	}

	public void setDutyFactor(List<OrderDutyFactor> dutyFactor) {
		this.dutyFactor = dutyFactor;
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

	public List<FEMRiskFactorList> getTestAgeList() {
		return testAgeList;
	}

	public void setTestAgeList(List<FEMRiskFactorList> testAgeList) {
		this.testAgeList = testAgeList;
	}
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public SDInformationAppnt getSdinformationAppnt() {
		return sdinformationAppnt;
	}

	public void setSdinformationAppnt(SDInformationAppnt sdinformationAppnt) {
		this.sdinformationAppnt = sdinformationAppnt;
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

	public String getLoginFlag() {
		return loginFlag;
	}

	public void setLoginFlag(String loginFlag) {
		this.loginFlag = loginFlag;
	}

	public String getAmntNum() {
		return amntNum;
	}

	public void setAmntNum(String amntNum) {
		this.amntNum = amntNum;
	}

	public String getProtectionPeriodTy() {
		return protectionPeriodTy;
	}

	public void setProtectionPeriodTy(String protectionPeriodTy) {
		this.protectionPeriodTy = protectionPeriodTy;
	}

	public String getProtectionPeriodValue() {
		return protectionPeriodValue;
	}

	public void setProtectionPeriodValue(String protectionPeriodValue) {
		this.protectionPeriodValue = protectionPeriodValue;
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
 
	public List<SDInformationDuty> getShowDuty() {
		return showDuty;
	}

	public void setShowDuty(List<SDInformationDuty> showDuty) {
		this.showDuty = showDuty;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public String getTextAge() {
		return textAge;
	}

	public void setTextAge(String textAge) {
		this.textAge = textAge;
	}
	public String getDutyDisReq() {
		return dutyDisReq;
	}

	public void setDutyDisReq(String dutyDisReq) {
		this.dutyDisReq = dutyDisReq;
	}
	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public List<Dictionary> getNationalityList() {
		return nationalityList;
	}

	public void setNationalityList(List<Dictionary> nationalityList) {
		this.nationalityList = nationalityList;
	}

	public String getMulPeople() {
		return mulPeople;
	}

	public void setMulPeople(String mulPeople) {
		this.mulPeople = mulPeople;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String[] getBaseInformations() {
		return baseInformations;
	}

	public void setBaseInformations(String[] baseInformations) {
		this.baseInformations = baseInformations;
	}

	public String getInsureJson() {
		return insureJson;
	}

	public void setInsureJson(String insureJson) {
		this.insureJson = insureJson;
	}

	public String getDutyJson() {
		return dutyJson;
	}

	public void setDutyJson(String dutyJson) {
		this.dutyJson = dutyJson;
	}

	public String getDutyPremReq() {
		return dutyPremReq;
	}

	public void setDutyPremReq(String dutyPremReq) {
		this.dutyPremReq = dutyPremReq;
	}

	public Integer getStartPeriod() {
		return startPeriod;
	}

	public void setStartPeriod(Integer startPeriod) {
		this.startPeriod = startPeriod;
	}

	public List<Dictionary> getTravelModelList() {
		return travelModelList;
	}

	public void setTravelModelList(List<Dictionary> travelModelList) {
		this.travelModelList = travelModelList;
	}

	public List<Dictionary> getTravelTypeList() {
		return travelTypeList;
	}

	public void setTravelTypeList(List<Dictionary> travelTypeList) {
		this.travelTypeList = travelTypeList;
	}

	public String getInsureRiskType() {
		return insureRiskType;
	}

	public void setInsureRiskType(String insureRiskType) {
		this.insureRiskType = insureRiskType;
	}

	public String getKID() {
		return KID;
	}

	public void setKID(String kID) {
		KID = kID;
	}

	public List<ModuleInfo> getPageModuleList() {
		return pageModuleList;
	}

	public void setPageModuleList(List<ModuleInfo> pageModuleList) {
		this.pageModuleList = pageModuleList;
	}
 
	public DictionaryService getDictionaryService() {
		return dictionaryService;
	}

	public void setDictionaryService(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}

	public OccupationService getOccupationService() {
		return occupationService;
	}

	public void setOccupationService(OccupationService occupationService) {
		this.occupationService = occupationService;
	}

	public AreaService getAreaService() {
		return areaService;
	}

	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}
	 
	public ProductPeriodService getProductPeriodService() {
		return productPeriodService;
	}

	public void setProductPeriodService(ProductPeriodService productPeriodService) {
		this.productPeriodService = productPeriodService;
	}

 
	public List<SDInformationInsured> getSdinformationinsuredList() {
		return sdinformationinsuredList;
	}

	public void setSdinformationinsuredList(List<SDInformationInsured> sdinformationinsuredList) {
		this.sdinformationinsuredList = sdinformationinsuredList;
	}

	public SDInformationInsured getSdinformationinsured() {
		return sdinformationinsured;
	}

	public void setSdinformationinsured(SDInformationInsured sdinformationinsured) {
		this.sdinformationinsured = sdinformationinsured;
	}

	public List<SDInformationInsured> getSdinformationinsuredList_Imp() {
		return sdinformationinsuredList_Imp;
	}

	public void setSdinformationinsuredList_Imp(List<SDInformationInsured> sdinformationinsuredList_Imp) {
		this.sdinformationinsuredList_Imp = sdinformationinsuredList_Imp;
	}

	public String getMulInsuredFlag() {
		return mulInsuredFlag;
	}

	public void setMulInsuredFlag(String mulInsuredFlag) {
		this.mulInsuredFlag = mulInsuredFlag;
	}

	public int getInsuredCount() {
		return insuredCount;
	}

	public void setInsuredCount(int insuredCount) {
		this.insuredCount = insuredCount;
	}

	public Map<String, List<Dictionary>> getMapCountryCode() {
		return mapCountryCode;
	}

	public void setMapCountryCode(Map<String, List<Dictionary>> mapCountryCode) {
		this.mapCountryCode = mapCountryCode;
	}

	public String getDestinationCountry() {
		return DestinationCountry;
	}

	public void setDestinationCountry(String destinationCountry) {
		DestinationCountry = destinationCountry;
	}

	public String getInsuredToCountry() {
		return insuredToCountry;
	}

	public void setInsuredToCountry(String insuredToCountry) {
		this.insuredToCountry = insuredToCountry;
	}
 
	public Map<String, Object> getSdinteraction() {
		return sdinteraction;
	}

	public void setSdinteraction(Map<String, Object> sdinteraction) {
		this.sdinteraction = sdinteraction;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getRecoJson() {
		return recoJson;
	}

	public void setRecoJson(String recoJson) {
		this.recoJson = recoJson;
	}

	public List<SDInsuredHealth> getSdinsuredHealthList() {
		return sdinsuredHealthList;
	}

	public void setSdinsuredHealthList(List<SDInsuredHealth> sdinsuredHealthList) {
		this.sdinsuredHealthList = sdinsuredHealthList;
	}

	public String getAppLevel() {
		return appLevel;
	}

	public void setAppLevel(String appLevel) {
		this.appLevel = appLevel;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getFeeYear() {
		return feeYear;
	}

	public void setFeeYear(String feeYear) {
		this.feeYear = feeYear;
	}

	public SDOrderItem getSdorderitem() {
		return sdorderitem;
	}

	public void setSdorderitem(SDOrderItem sdorderitem) {
		this.sdorderitem = sdorderitem;
	}

	public String getApplicantBirthday() {
		return applicantBirthday;
	}

	public void setApplicantBirthday(String applicantBirthday) {
		this.applicantBirthday = applicantBirthday;
	}

	public Integer getEndPeriod() {
		return endPeriod;
	}

	public void setEndPeriod(Integer endPeriod) {
		this.endPeriod = endPeriod;
	}

	public String getRecognizeeOperate() {
		return recognizeeOperate;
	}

	public void setRecognizeeOperate(String recognizeeOperate) {
		this.recognizeeOperate = recognizeeOperate;
	}

	public List<String> getDetisnateList() {
		return detisnateList;
	}

	public void setDetisnateList(List<String> detisnateList) {
		this.detisnateList = detisnateList;
	}

	public String getRiskType() {
		return riskType;
	}

	public void setRiskType(String riskType) {
		this.riskType = riskType;
	}
	/**
	 * 预览页面是否可以操作标记
	 */
	private String canPreviewOperatorFlag = "1";

	public String getCanPreviewOperatorFlag() {
		return canPreviewOperatorFlag;
	}

	public void setCanPreviewOperatorFlag(String canPreviewOperatorFlag) {
		this.canPreviewOperatorFlag = canPreviewOperatorFlag;
	}

	/**
	 * 是否需要核保的标记
	 */
	private String needUWCheckFlag = "0";

	public String getNeedUWCheckFlag() {
		return needUWCheckFlag;
	}

	public void setNeedUWCheckFlag(String needUWCheckFlag) {
		this.needUWCheckFlag = needUWCheckFlag;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public String getEffdate() {
		return effdate;
	}

	public void setEffdate(String effdate) {
		this.effdate = effdate;
	}
	public String getJudge() {
		return judge;
		}
		 
	public void setJudge(String judge) {
	this.judge = judge;
	}
	 
	public TradeInformation getTradeInformation() {
	return tradeInformation;
	}
	 
	public void setTradeInformation(TradeInformation tradeInformation) {
	this.tradeInformation = tradeInformation;
	} 
 
	public String getSupplierCode2() {
		return supplierCode2;
	}

	public void setSupplierCode2(String supplierCode2) {
		this.supplierCode2 = supplierCode2;
	}

	public Map<String,Map<String,List<Dictionary>>> getMapCountryCodeList() {
		return mapCountryCodeList;
	}

	public void setMapCountryCodeList(Map<String,Map<String,List<Dictionary>>> mapCountryCodeList) {
		this.mapCountryCodeList = mapCountryCodeList;
	} 
	
	public List<SDInformationProperty> getSdinformationpropertyList() {
		return sdinformationpropertyList;
	}

	public void setSdinformationpropertyList(
			List<SDInformationProperty> sdinformationpropertyList) {
		this.sdinformationpropertyList = sdinformationpropertyList;
	}

	public List<Dictionary> getHourseTypeList() {
		return hourseTypeList;
	}

	public void setHourseTypeList(List<Dictionary> hourseTypeList) {
		this.hourseTypeList = hourseTypeList;
	}

	public List<Dictionary> getHourseAgeList() {
		return hourseAgeList;
	}

	public void setHourseAgeList(List<Dictionary> hourseAgeList) {
		this.hourseAgeList = hourseAgeList;
	}

	public List<InsuredShow> getShowPropertys() {
		return showPropertys;
	}

	public void setShowPropertys(List<InsuredShow> showPropertys) {
		this.showPropertys = showPropertys;
	}

	public SDInformationProperty getSdinformationProperty() {
		return sdinformationProperty;
	}

	public void setSdinformationProperty(SDInformationProperty sdinformationProperty) {
		this.sdinformationProperty = sdinformationProperty;
	}

	public List<Dictionary> getPropertyToRecognizeeList() {
		return propertyToRecognizeeList;
	}

	public void setPropertyToRecognizeeList(
			List<Dictionary> propertyToRecognizeeList) {
		this.propertyToRecognizeeList = propertyToRecognizeeList;
	}
	
	public String getRelationIsSelf() {
		return relationIsSelf;
	}

	public void setRelationIsSelf(String relationIsSelf) {
		this.relationIsSelf = relationIsSelf;
	}
	
	public String getProductExcelTemp() {
		return productExcelTemp;
	}

	public void setProductExcelTemp(String productExcelTemp) {
		this.productExcelTemp = productExcelTemp;
	}

	public String getUploadfile() {
		return uploadfile;
	}

	public void setUploadfile(String uploadfile) {
		this.uploadfile = uploadfile;
	}

	
	public String getEffdateNew() {
		return effdateNew;
	}

	public void setEffdateNew(String effdateNew) {
		this.effdateNew = effdateNew;
	}

	public List<SDInformationInsured> getSdinsuredList() {
		return sdinsuredList;
	}

	public void setSdinsuredList(List<SDInformationInsured> sdinsuredList) {
		this.sdinsuredList = sdinsuredList;
	}

	public int getInsuredImpCount() {
		return insuredImpCount;
	}

	public void setInsuredImpCount(int insuredImpCount) {
		this.insuredImpCount = insuredImpCount;
	}

	public String getProductExcelFlag() {
		return productExcelFlag;
	}

	public void setProductExcelFlag(String productExcelFlag) {
		this.productExcelFlag = productExcelFlag;
	}

	
	public String getImpValadate() {
		return impValadate;
	}

	public void setImpValadate(String impValadate) {
		this.impValadate = impValadate;
	}

	public List<SDRelationAppnt> getReappntList() {
		return reappntList;
	}

	public void setReappntList(List<SDRelationAppnt> reappntList) {
		this.reappntList = reappntList;
	}

	public String getReappntName() {
		return reappntName;
	}

	public void setReappntName(String reappntName) {
		this.reappntName = reappntName;
	}

	public static void main(String[] args) {
//		HashMap<String,String> map = new HashMap<String, String>();
//		map.put("a", "a'");
//		JSONObject jsonArrays = JSONObject.fromObject(map);
//		String jsonstrs = jsonArrays.toString();
//		System.out.println(jsonstrs);
	}



}
