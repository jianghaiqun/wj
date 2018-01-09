package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.entity.Area;
import cn.com.sinosoft.entity.Dictionary;
import cn.com.sinosoft.entity.HealthyInfo;
import cn.com.sinosoft.entity.Information;
import cn.com.sinosoft.entity.InformationAppnt;
import cn.com.sinosoft.entity.InformationDuty;
import cn.com.sinosoft.entity.InformationInsured;
import cn.com.sinosoft.entity.InformationInsuredElements;
import cn.com.sinosoft.entity.InformationRiskType;
import cn.com.sinosoft.entity.InsuredHealth;
import cn.com.sinosoft.entity.InsuredShow;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.Occupation;
import cn.com.sinosoft.entity.Order;
import cn.com.sinosoft.entity.Order.OrderStatus;
import cn.com.sinosoft.entity.Order.PaymentStatus;
import cn.com.sinosoft.entity.OrderDutyFactor;
import cn.com.sinosoft.entity.OrderItem;
import cn.com.sinosoft.entity.OrderRiskAppFactor;
import cn.com.sinosoft.entity.ProductConfig;
import cn.com.sinosoft.entity.QuestionPaper;
import cn.com.sinosoft.entity.ZdrecordCps;
import cn.com.sinosoft.service.AreaService;
import cn.com.sinosoft.service.DictionaryService;
import cn.com.sinosoft.service.HealthyInfoService;
import cn.com.sinosoft.service.InformationAppntService;
import cn.com.sinosoft.service.InformationDutyService;
import cn.com.sinosoft.service.InformationInsuredElementsService;
import cn.com.sinosoft.service.InformationInsuredService;
import cn.com.sinosoft.service.InformationRiskTypeService;
import cn.com.sinosoft.service.InformationService;
import cn.com.sinosoft.service.InsuredHealthService;
import cn.com.sinosoft.service.OccupationService;
import cn.com.sinosoft.service.OrderConfigService;
import cn.com.sinosoft.service.OrderItemService;
import cn.com.sinosoft.service.OrderService;
import cn.com.sinosoft.service.PageLayoutService;
import cn.com.sinosoft.service.ProductConfigService;
import cn.com.sinosoft.service.ProductPeriodService;
import cn.com.sinosoft.service.QuestionPaperService;
import cn.com.sinosoft.service.ZdrecordCpsService;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FEMDutyAmntPremList;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FEMRiskFactorList;
import com.wangjin.infoseeker.OrderSeeker;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@ParentPackage("shop")       
public class OrderConfigAction extends BaseShopAction {

	private static final long serialVersionUID = -7227426726084770262L;
	private List<ProductConfig> productConfigs = new ArrayList<ProductConfig>();
	private InformationAppnt informationAppnt = new InformationAppnt();// 投保人
	private InformationInsured informationInsureds = new InformationInsured();// 第一个被保人
	private List<Dictionary> certificateList = new ArrayList<Dictionary>();// 证件类型
	private List<Dictionary> relationList = new ArrayList<Dictionary>();// 投被保人关系
	private List<Occupation> occupationList = new ArrayList<Occupation>();// 一级职业
	private List<Dictionary> sexList = new ArrayList<Dictionary>();// 性别
	private List<Dictionary> marriageStutas = new ArrayList<Dictionary>();//婚否
	private List<Area> areaList = new ArrayList<Area>();// 一级地区
	private List<OrderDutyFactor> dutyFactor = new ArrayList<OrderDutyFactor>();// 产品投保要素列表
	private List<OrderRiskAppFactor> riskAppFactior = new ArrayList<OrderRiskAppFactor>();// 责任投保要素列表
	private List<Dictionary> listCountryCode = new ArrayList<Dictionary>();// 旅游目的地
	private List<FEMRiskFactorList> periodList = new ArrayList<FEMRiskFactorList>();// 保障期限列表
	private List<FEMRiskFactorList> planList = new ArrayList<FEMRiskFactorList>();// 计划值列表
	private List<FEMRiskFactorList> occupList = new ArrayList<FEMRiskFactorList>();// 职业类别
	private List<FEMRiskFactorList> gradeList = new ArrayList<FEMRiskFactorList>();// 产品级别列表
	private List<FEMRiskFactorList> feeYearList = new ArrayList<FEMRiskFactorList>();// 缴费年期列表
	private List<FEMRiskFactorList> appLevelList = new ArrayList<FEMRiskFactorList>();// 缴费方式列表
	private List<FEMRiskFactorList> appTypeList = new ArrayList<FEMRiskFactorList>();// 投保类别列表
	private List<FEMRiskFactorList> testAgeList = new ArrayList<FEMRiskFactorList>();// 产品级别列表
	private List<FEMRiskFactorList> mulPeopleList = new ArrayList<FEMRiskFactorList>();// 保险责任多类人群列表
	private List<List<InsuredShow>> showInsureds = new ArrayList<List<InsuredShow>>();//页面显示被保人信息
	private List<Dictionary> nationalityList = new ArrayList<Dictionary>();// 国籍值列表
	private List<InformationDuty> showDuty = null;// 订单结果页责任显示集合
	private List<InsuredHealth> insuredHealthList = new ArrayList<InsuredHealth>();// 健康告知列表
	private List<Dictionary> travelModelList = new ArrayList<Dictionary>();
	private List<Dictionary> travelTypeList = new ArrayList<Dictionary>();
	private String orderId;
	private Order order = new Order();// 订单
	private Information information = new Information();
	private QuestionPaper questionPaper = new QuestionPaper();
	private String productId;// 产品id
	private String loginFlag;
	private String textAgeFlag = "false";
	private String amntNum = "0";// 计算保额
	private String protectionPeriodTy;// 保障期限类型
	private String protectionPeriodValue = "";// 保障期限
	private String feeYear;//缴费年限
	private String appLevel;//投保类别
	private String appType;//缴费方式
	private String protectionPeriodLast;// 保障期限后段数值
	private String protectionPeriodFlag;// 是否存在保障期限
	private String mulPeople;// 保险责任多类人群列表
	private String period;// 保险期限
	private String plan;// 计划
	private String textAge;// 文字年龄
	private String brkRiskCode;// 计划编码
	private Date effective;
	private Date fail;
	private String dutyReq;
	private String dutyDisReq;//保存用动态分项责任显示保额
	private String dutyPremReq;//保存用动态分项保费
	private String insureReq;
	private String orderStatus;// 订单状态
	private String status;// 是否修改标志
	private String[] baseInformations = new String[6];// 产品基本信息存session
	private String insureJson;// ajax传递投保试算参数
	private String dutyJson;// ajax传递责任参数
	private String orderSn;// 订单号
	private Integer startPeriod = 1;//起始日期与当前日期差
	private String insureRiskType;
	private String KID;
	private String supplierCode2;//保险公司编码
	private Boolean quesFlag;//问卷调查标记
	private String mainAmnt;//主险保额
	
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
	@Resource
	private ProductConfigService productConfigService;
	@Resource
	private DictionaryService dictionaryService;
	@Resource
	private OccupationService occupationService;
	@Resource
	private AreaService areaService;
	@Resource
	private OrderService orderService;
	@Resource
	private PageLayoutService pagelayoutService;
	@Resource
	private OrderConfigService orderConfigService;
	@Resource
	private OrderItemService orderItemService;
	@Resource
	private InformationService informationService;
	@Resource
	private InformationAppntService informationAppntService;
	@Resource
	private InformationInsuredService informationInsuredService;
	@Resource
	private InformationRiskTypeService informationRiskTypeService;
	@Resource
	private InformationDutyService informationDutyService;
	@Resource
	private InformationInsuredElementsService informationInsuredElementsService;
	@Resource
	private HealthyInfoService healthyInfoService;
	@Resource
	private InsuredHealthService insuredHealthService;
	@Resource
	private ProductPeriodService productPeriodService;
	@Resource
	private ZdrecordCpsService zdrecordCpsService;
	@Resource
	private QuestionPaperService questionPaperService;

	public String buyNow() {
		Map<String, Object> paramter = new HashMap<String, Object>();
		try {
			paramter = orderService.getProductInformation(productId, "N");// 产品ID
			String[] BaseInformation = new String[16];
			BaseInformation = (String[]) paramter.get("baseInformation");// 产品基础数据
			setSession("baseInformation", BaseInformation);
			productId = BaseInformation[0].toString();// 产品ID
			if(BaseInformation[14]!=null&&"01".equals(BaseInformation[14])){
				return cpsProductJump(productId,BaseInformation[15]);
			}
			order.setProductId(productId);
			order.setProductName(BaseInformation[1].toString());// 产品名称
			String insuranceCompanySn = BaseInformation[2].toString();// 保险公司编码
			insuranceCompanySn = insuranceCompanySn.substring(0, 4);
			order.setInsuranceCompanySn(insuranceCompanySn);
			order.setInsuranceCompany(BaseInformation[3].toString());// 保险公司名称
			order.setOutRiskCode(BaseInformation[4].toString());// 保险公司产品编码
			String insurType = BaseInformation[5].toString();// 内部统计险种中类别
			String[] insurTypes = insurType.split(",");
			insureRiskType = insurTypes[0];
			order.seteRiskType(insureRiskType);
			order.setSubRiskTypeCode(BaseInformation[7].toString());// 内部统计险种子类别
			// productImg = BaseInformation[8].toString();// 产品类别
			String price = BaseInformation[6].toString();// 商品原价
			order.setCurrentTermAmount(new BigDecimal(price));
			String countPrice = BaseInformation[11].toString();// 折扣后价格
			order.setTotalAmount(new BigDecimal(countPrice));
			order.setProductTotalPrice(new BigDecimal(countPrice));
			order.setDiscountRates(BaseInformation[10].toString());// 折扣比率
			order.setInsuranceSn(BaseInformation[12].toString());// 险种编码
			order.setOrderStatus(OrderStatus.prepay);
			order.setPaymentStatus(PaymentStatus.unpaid);
			riskAppFactior = (List<OrderRiskAppFactor>) paramter
					.get("riskAppFactor");
			dutyFactor = (List<OrderDutyFactor>) paramter.get("dutyFactor");
			this.setDutyFactorAndAmntNum(riskAppFactior, dutyFactor);
			order.setBrkRiskCode(brkRiskCode);
			dataBuild();
			init(order.getInsuranceCompanySn());
			if (getLoginMember() != null) {
				order.setMemberId(getLoginMember().getId());
				loginFlag = "true";
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionError("没有找到该产品，请重新选择！");
			return ERROR;
		}

		return resultPage(order.getInsuranceCompanySn(), order.geteRiskType());
	}

	/**
	 * 
	 * 保费重算
	 */
	private void setDutyFactorAndAmntNum(List<OrderRiskAppFactor> riskAppFactior2, List<OrderDutyFactor> dutyFactor2) {
		//zhangjinquan 11180 解决默认保费为0问题，删除掉多余代码 2012-12-04
		if (riskAppFactior2.size() > 0) {
			setSession("riskAppFactior", riskAppFactior2);
			for (int i = 0; i < riskAppFactior2.size(); i++) {
				OrderRiskAppFactor orderRiskAppFactor = riskAppFactior2.get(i);
				// 拆分保障期限值，用于页面
				if ("Period".equals(orderRiskAppFactor.getFactorType()
						.toString())) {
					orderRiskAppFactor.getFactorValue();
					List<FEMRiskFactorList> periodList = orderRiskAppFactor
							.getFactorValue();
					FEMRiskFactorList fEMRiskFactorList = periodList.get(0);
					String[] periods = fEMRiskFactorList.getFactorValue()
							.split("-");
					// 填写前台保险期间
					if (this.period != null && !"".equals(this.period)) {
						periods = period.split("-");
					}
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
					if (periodBe != null && !"".equals(periodBe)
							&& periodBe.length() > 1) {
						protectionPeriodTy = periodBe.substring(periodBe
								.length() - 1);// 保障期限类型
					} else {
						protectionPeriodTy = "";
					}
					if ("".equals(periodAf) || periodAf == "") {
						periodAf = periodBe;
					}
					if (periodAf != null && !"".equals(periodAf)
							&& periodAf.length() > 1) {
						protectionPeriodLast = periodAf.substring(0,
								periodAf.length() - 1);
					} else {
						protectionPeriodLast = "";
					}
					protectionPeriodFlag = "true";
				}
				if ("TextAge".equals(orderRiskAppFactor.getFactorType()
						.toString())) {
					textAgeFlag = "true";
				}
				if ("Plan".equals(orderRiskAppFactor.getFactorType().toString())) {
					if (StringUtils.isNotEmpty(plan)) {
						brkRiskCode = plan;
					}
				}
			}
		}
		List backup = new ArrayList();
		if (dutyFactor2.size() > 0) {
			for (OrderDutyFactor orderDutyFactor : dutyFactor2) {
				for (FEMDutyAmntPremList fEMDutyAmntPremList : orderDutyFactor
						.getFdAmntPremList()) {
					if(fEMDutyAmntPremList!=null){
						backup.add(fEMDutyAmntPremList.getBackUp1());
					}
				}
				if (orderDutyFactor.getFdAmntPremList() == null
						|| orderDutyFactor.getFdAmntPremList().get(0) == null) {
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
		amntNum = backNum.toString();
	}

	/**
	 * 设置目的地国家名称字段
	 * @author zhangjinquan 11180 2012-11-29
	 * @param insuranceCode
	 * @param insured
	 * @param dictionaryService
	 */
	private void setDestinationCountry(String insuranceCode, InformationInsured insured, DictionaryService dictionaryService)
	{
		if ((null == insured) || (null == dictionaryService) || StringUtil.isEmpty(insured.getDestinationCountry()))
		{
			return;
		}
		
		String[] destinations = insured.getDestinationCountry().split(",");
		StringBuffer destinationText = new StringBuffer();
		int j=0;
		String sql = "select 1 from zdcode where CodeType=? and ParentCode=? and CodeValue=?";
		QueryBuilder qb = new QueryBuilder(sql);
		qb.add("DestinationCountryText");
		qb.add("DestinationCountryText");
		qb.add(insuranceCode);
		boolean saveEnNameFlag = qb.executeInt()>0;
		for (int i=0; i<destinations.length; i++)
		{
			String name = null;
			if (saveEnNameFlag)
			{
				name = (this.dictionaryService.getCodeEnNameByCodeValue(insuranceCode, "CountryCode", destinations[i].trim()));
			}
			else
			{
				name = (this.dictionaryService.findCountryNameByCode(insuranceCode, destinations[i].trim()));
			}
			
			if (StringUtil.isEmpty(name))
			{
				continue;
			}
			
			if (j>0)
			{
				destinationText.append(",");
			}
			destinationText.append(name);
			j++;
		}
		
		insured.setDestinationCountryText(destinationText.toString());
	}
	
	
	/**
	 * 保存订单信息
	 */
	public String saveOrder() {
		if (getLoginMember() != null) {
			order.setMemberId(getLoginMember().getId());
		}
		try {
			productId = order.getProductId();
			quesFlag = questionPaper(productId);
			if (order.getOrderStatus() != null) {
				orderStatus = order.getOrderStatus().toString();
			}
			if (!"temptorysave".equals(orderStatus)) {

				String sql = "select LimitCount,Occup,SectionAge,IsPublish from sdproduct where productid=?";
				String[] sqltemp = {productId};
				GetDBdata db = new GetDBdata();
				List<HashMap<String, String>> sdproduct = db.query(sql,sqltemp);
				if (sdproduct == null || sdproduct.size() != 1) {
					addActionError("产品不存在，请上架产品！");
					return ERROR;
				}
				HashMap<String, String> product = sdproduct.get(0);
				String IsPublish = product.get("IsPublish");
				if (StringUtil.isEmpty(IsPublish) || "N".equals(IsPublish)) {
					addActionError("产品已下架！");
					return ERROR;
				}
				Date d = new Date();
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
				String tDate = com.sinosoft.sms.messageinterface.pubfun.PubFun
						.calSDate(sf.format(d), 1, "D");
				Date nowDate = com.sinosoft.sms.messageinterface.pubfun.PubFun
						.StringToDate(tDate);
				if ((effective.getTime() < nowDate.getTime())
						&& (fail.getTime() >= effective.getTime())) {
					addActionError("保单起保日期或者保单终止日期有误！");
					return ERROR;
				}
				String LimitCount = product.get("LimitCount");
				logger.info("保险公司限购份数：{}", LimitCount);
				if (StringUtil.isNotEmpty(LimitCount)
						&& !"0".equals(LimitCount)) {
					String backup_ = "select count(orders.id) from orders where  orderStatus=7 and productid='"
							+ productId
							+ "' and  id in( select order_id from orderitem where id in(";
					backup_ += "select information.orderitem_id from information where (('"+ sf.format(fail) +" 23:59:59'>=effective and effective>='"+ sf.format(effective) +" 00:00:00') or ('"+ sf.format(effective) +" 00:00:00'<=fail and fail<='"+ sf.format(fail) +" 23:59:59')) and  id in (select information_id from informationinsured ";
					backup_ += "where recognizeeIdentityType=? and recognizeeIdentityId=?)))";
					logger.info("保险公司限购份数（sql）：{}", backup_);
					String[] backuptemp = {informationInsureds.getRecognizeeIdentityType(),informationInsureds.getRecognizeeIdentityId()};
					String backup = db.getOneValue(backup_ , backuptemp);
					logger.info("保险公司限购份数的值：{} -- {}", backup, LimitCount);
					if (StringUtil.isNotEmpty(backup)) {
						if (Integer.parseInt(backup) >= Integer
								.parseInt(LimitCount)) {
							addActionError("此被保人的证件号已经购买过此产品，超过限购份数！");
							return ERROR;
						}
					}
				}
				/**
				 * 保险投保与生效时间 间隔校验
				 */
				if("1065".equals(order.getInsuranceCompanySn())){
					
					int day = daysBetween(new Date(),effective);
					if(day>180){
						addActionError("投保与生效日期时间间隔最长为180天！");
						return ERROR;
					}
				
				}
				
				logger.info("职业校验：{}  productId:{}", informationInsureds.getOccupation3(), productId);
				if (informationInsureds.getOccupation3() != null) {
					if("1065".equals(order.getInsuranceCompanySn())){
						if("Y".equals(informationInsureds.getIsSelf())){
							if (!orderConfigService.validateOccup(informationAppnt.getApplicantOccupation3(), productId)) {
								addActionError("您的职业无法购买此产品(occup)！");
								return ERROR;
							}
						}
					}
				}
				String birthday = informationInsureds.getRecognizeeBirthday();
				if (StringUtil.isEmpty(birthday)) {
					birthday = informationAppnt.getApplicantBirthday();
					logger.info("保险人生日：{}", birthday);
				}
				logger.info("年龄校验：{}  productId:{}", birthday, productId);
			
				if (!orderConfigService.validateAge(birthday, productId,effective)) {
					addActionError("您无法购买此产品(birthday)！");
					return ERROR;
				}

				// 保险日期校验
				if (effective == null || fail == null) {
					addActionError("您无法购买此产品(effective)！");
					return ERROR;
				}
				String insurTypeChild = order.getSubRiskTypeCode();
				if ("22".equals(insurTypeChild) || "23".equals(insurTypeChild)
						|| "24".equals(insurTypeChild)
						|| "25".equals(insurTypeChild)
						|| "26".equals(insurTypeChild)) {
					if ("Y".equals(informationInsureds.getIsSelf())) {
						if ("男".equals(informationAppnt.getApplicantSexName())) {
							addActionError("您无法购买此产品(被保人必须为女性)！");
							return ERROR;
						}
					} else {
						if ("男".equals(informationInsureds.getIsSelf())) {
							addActionError("您无法购买此产品(被保人必须为女性)！");
							return ERROR;
						}
					}
				}
			}
			BigDecimal paymentFee = new BigDecimal("0");
			OrderItem orderItem = new OrderItem();
			Set<OrderItem> orderItemSet = new HashSet<OrderItem>();
			Set<Information> informationSet = new HashSet<Information>();
			Set<InformationInsured> informationInsuredSet = new HashSet<InformationInsured>();
			Set<InformationDuty> informationDutyElementsSet = new HashSet<InformationDuty>();
			orderItem.setProductId(productId);
			orderItem.setProductName(order.getProductName());// 产品名称，详细页或接口获得
			orderItem.setProductPrice(order.getProductTotalPrice());
			orderItem.setProductQuantity(1);
			orderItem.setProductHtmlFilePath("");// 产品详细页URL,由产品详细页获得
			orderItemSet.add(orderItem);
			order.setOrderStatus(OrderStatus.temptorysave);
			order.seteRiskType(insureRiskType);
			order.setPaidAmount(new BigDecimal("0"));
			order.setPaymentFee(paymentFee);
			order.setProductTotalQuantity(0);
			order.setPoint(0);
			order.setOrderItemSet(orderItemSet);
			order.setTotalAmount(order.getProductTotalPrice());
			order.setOrderValid(true);
			order.setAmntPrice(new BigDecimal(amntNum));
			String orderSn = PubFun.GetOrderSn();
			KID=StringUtil.md5Hex(this.getRequest().getSession().getId()+orderSn);
			order.setBrkRiskCode(plan);
			order.setOrderSn(orderSn);
			if ("2023".equals(order.getInsuranceCompanySn())) {
				String outcode = PubFun.getHTSN(order.getOutRiskCode());
				order.setTpySn(outcode);// 存入华泰特殊编号
			}
			orderService.save(order);
			orderItem.setOrder(order);
			orderId = order.getId();
			informationSet.add(information);
			orderItem.setInformationSet(informationSet);
			orderItemService.save(orderItem);
			information.setOrderItem(orderItem);// information保存orderItem
			information.setEffective(effective); // 保单起始日期
			information.setFail(DateUtil.setTimeOfDate(fail, 23, 59, 59)); // 保单终止日期
			information.setAppType(appType);
			information.setFeeYear(feeYear);
			information.setAllPeriod(period);
			information.setInformationInsuredSet(informationInsuredSet);
			if ("Y".equals(protectionPeriodTy)) {
				protectionPeriodTy = "y";
			}
			if ("D".equals(protectionPeriodTy) || "".equals(protectionPeriodTy)) {
				protectionPeriodTy = "d";
			}
			if ("M".equals(protectionPeriodTy)) {
				protectionPeriodTy = "m";
			}
			information.setProtectionPeriodTy(protectionPeriodTy);
			if ("".equals(protectionPeriodLast)) {
				protectionPeriodLast = "1";
			}
			information.setProtectionPeriod(protectionPeriodLast);
			//zhangjinquan 11180 保存投保人时将information的投保人信息进行更新 2012-11-28
			information.setInformationAppnt(informationAppnt);
			information.setInformationDutyElementsSet(informationDutyElementsSet);
			informationService.save(information);
			informationInsuredDutySave(information, informationDutyElementsSet);
			this.informationAppntSave(information);// 投保人信息存储
			this.setDestinationCountry(order.getInsuranceCompanySn(), informationInsureds, dictionaryService);
			this.informationInsuredSave(information, informationInsuredSet,
					order,quesFlag);// 被保人信息存储

			orderItem.setValid(true);
			orderItemService.update(orderItem);
			String ordersn = (String) getSession("OrderSn");
			if (StringUtil.isNotEmpty(ordersn)) {
				if (ordersn.indexOf(order.getOrderSn()) == -1) {
					setSession("OrderSn", ordersn + "," + order.getOrderSn());
				}
			} else {
				setSession("OrderSn", order.getOrderSn());
			}
			logger.info("OrderSn放入到session内{}", getSession("OrderSn"));
			// req20121128002:CPS 梅俊峰 2012/12/25 add start
			//OrderInfoCommit.infoCommitForEmar(getRequest(), order);
			// req20121128002:CPS 梅俊峰 2012/12/25 add end
			
			//----	yushanchun	获取下订单ip  2013-01-29 start
			try {
				String ip = getRequest().getHeader("X-Forwarded-For");
				if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = getRequest().getHeader("Proxy-Client-IP");
				}
				if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = getRequest().getHeader("WL-Proxy-Client-IP");
				}
				if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = getRequest().getRemoteAddr();
				}
				OrderSeeker.orderInfoInsert(order.getOrderSn(), ip);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			//----	yushanchun	获取下订单ip  2013-01-29 end
			
			if ("temptorysave".equals(orderStatus)) {
				return "tempsucces";// 确定成功页面后跳转用
			} else {
				if(insuredHealthList!=null && insuredHealthList.size()>0){
					return "healthy";
					
				}else{
					if(quesFlag){	
						mainAmnt = getAmnt(order.getId());
						init(order.getInsuranceCompanySn());	//查询地区
						if(StringUtil.isNotEmpty(productId)){
							supplierCode2 = productId.substring(0, 4);
						}
						return "wjpage";
					}else{
						showInformaton(orderId);
						this.setNeedUWCheckFlag(OrderAction.getNeedUWFlagFromDB(order.getInsuranceCompanySn()));
						return "result";
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionError("订单保存失败，请重试！");
			return ERROR;
		}
	}
	/***
	 * 获取两日期之间的天数
	 * @param early 起期
	 * @param late  止期
	 * @return
	 */
	public int daysBetween(Date early, Date late) {
	    
        java.util.Calendar calst = java.util.Calendar.getInstance();  
        java.util.Calendar caled = java.util.Calendar.getInstance();  
        calst.setTime(early);  
         caled.setTime(late);  
         //设置时间为0时  
         calst.set(java.util.Calendar.HOUR_OF_DAY, 0);  
         calst.set(java.util.Calendar.MINUTE, 0);  
         calst.set(java.util.Calendar.SECOND, 0);  
         caled.set(java.util.Calendar.HOUR_OF_DAY, 0);  
         caled.set(java.util.Calendar.MINUTE, 0);  
         caled.set(java.util.Calendar.SECOND, 0);  
        //得到两个日期相差的天数  
         int days = ((int) (caled.getTime().getTime() / 1000) - (int) (calst  
                .getTime().getTime() / 1000)) / 3600 / 24;  
        
        return days;  
   }   
	/**
	 * 判断是否需要进入调查问卷
	 * @param productId
	 * @return
	 */
	public boolean questionPaper(String productId) {
		try {
			questionPaper.setEffective(com.sinosoft.sms.messageinterface.pubfun.PubFun.getCurrentDate());
			String proId = "";
			String sql = "select value from zdconfig where type = 'questionPaper' and value = ?";
			String[] sql1temp = { productId };
			GetDBdata db0 = new GetDBdata();
			proId = db0.getOneValue(sql, sql1temp);
			if (StringUtil.isNotEmpty(proId)) {
				return true;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return false;
	}
	/**
	 * 获取主险保额
	 * @return
	 */
	public String getAmnt(String orderId){
		try{
		String mAmnt="";	
		String sql = "select amnt from informationduty where dutySn = '2071010010001' and orderId = ?";
		String[] sql1temp = { orderId };
		GetDBdata db0 = new GetDBdata();
		mAmnt = db0.getOneValue(sql, sql1temp);
		return mAmnt;
		} catch (Exception e){
			logger.error("获取主险保额方法发生异常："+e.getMessage(), e);
			  return null;
		}
	  
	}
	
	/**
	 * 调查问卷页保存方法
	 * @return
	 */
	public String questionPaperSave(){
		try{
			showInformaton(order.getId());
			QuestionPaper q = questionPaperService.get(questionPaper.getId());
			questionPaperService.updateQuestion(q,questionPaper);
			questionPaperService.update(q);
			GetDBdata db = new GetDBdata();
			String paidSQL = " select orders.paymentStatus from orders where orders.id=? ";
			String[] paidtemp = {this.order.getId()} ;
			String paidState = db.getOneValue(paidSQL ,paidtemp);
			if("2".equals(paidState)){
				addActionError("该订单已经付款成功，请重新购买！");
				return ERROR;
			} 
			KID=StringUtil.md5Hex(this.getRequest().getSession().getId()+order.getOrderSn()) ; 
			this.setNeedUWCheckFlag(OrderAction.getNeedUWFlagFromDB(order.getInsuranceCompanySn()));
		return "result";
		}catch(Exception e){
			logger.error("调查问卷保存方法出现异常："+e.getMessage(), e);
			return ERROR;
		}
	}
	
	// zhangjinquan 11180 2012-10-10 判断是否产品已经下架的公共方法 (zhangjinquan 11180 从OrderAction.java拷贝 2012-11-24)
	private String isProductDownShelf(String productId, String productName, String message,Order o) {
		if (null == productId) {
			addActionError("查询产品时传入参数错误！");
			return ERROR;
		}
		logger.info("产品id：========{}", productId);
		String sql = "select LimitCount,Occup,SectionAge,IsPublish from sdproduct where productid=?";
		String[] sqltemp = {productId};
		GetDBdata db = new GetDBdata();
		List<HashMap<String, String>> sdproduct;
		try {
			sdproduct = db.query(sql,sqltemp);
			if (sdproduct == null || sdproduct.size() != 1) {
				addActionError("该订单中险种" + productName + "不存在，无法" + message + "！");
				return ERROR;
			}
			HashMap<String, String> product = sdproduct.get(0);
			String IsPublish = product.get("IsPublish");
			if (StringUtil.isEmpty(IsPublish) || "N".equals(IsPublish)) {
				addActionError("该订单中险种" + productName + "已经下架，无法" + message + "！");
				return ERROR;
			}
			
			String qixianSql = "select effective, fail from information where orderitem_id=(select id from orderitem where order_id=?)";
			String[] qixianSqltemp = {o.getId()};
			List<Map<String, Object>> qixain = db.queryObj(qixianSql,qixianSqltemp);
			if(qixain == null || qixain.size() !=1){
				addActionError("此订单不存在！");
				return ERROR;
			}
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Map<String, Object> qx = qixain.get(0);
			Date qxfail = null;
			Date qxeffective = null;
			if(qx.get("fail")!=null){
				qxfail = sfTime.parse(qx.get("fail").toString());
				qxeffective = sfTime.parse(qx.get("effective").toString());
			}
			String tDate = com.sinosoft.sms.messageinterface.pubfun.PubFun.calSDate(sf.format(new Date()), 1, "D");
			Date nowDate = com.sinosoft.sms.messageinterface.pubfun.PubFun.StringToDate(tDate);
			if (nowDate.getTime() > qxfail.getTime()) {
				addActionError("保单已过期！");
				return ERROR;
			}
			String LimitCount = product.get("LimitCount");
			logger.info("保险公司限购份数：{}", LimitCount);
			if (StringUtil.isNotEmpty(LimitCount) && !"0".equals(LimitCount)) {
				String insuredSql = "select recognizeeAppntRelationName,recognizeeIdentityType,recognizeeIdentityId from informationinsured where information_id=(select id from information where orderitem_id=(select id from orderitem where order_id=?))";
				String[] insuredSqltemp = {o.getId()};
				List<HashMap<String, String>> insuredList = db.query(insuredSql,insuredSqltemp);;
				HashMap<String, String> insured = insuredList.get(0);
				String selfName = insured.get("recognizeeAppntRelationName");
				String insuredIdType = insured.get("recognizeeIdentityType");
				String insuredIdNo = insured.get("recognizeeIdentityId");
				if(selfName!=null){
					String backup_ = "select count(o.id) from orders o,orderitem oi,information inf,informationinsured ised, informationappnt iap " +
							"where o.id=oi.order_id and oi.id= inf.orderitem_id and inf.id= ised.information_id and inf.id=iap.information_id and o.orderStatus=7 and o.productid=?" +
							" and (('"+ sf.format(qxfail) +" 23:59:59'>=effective and effective>='"+ sf.format(qxeffective) +" 00:00:00') or ('"+ sf.format(qxeffective) +" 00:00:00'<=fail and fail<='"+ sf.format(qxfail) +" 23:59:59'))";
					backup_ += " and ised.recognizeeIdentityType =? and ised.recognizeeIdentityId=?";
					String[] backuptemp = {o.getProductId(),insuredIdType,insuredIdNo};
					logger.info("保险公司限购份数（sql）：{}", backup_);
					String backup = db.getOneValue(backup_ , backuptemp);
					logger.info("保险公司限购份数的值：{} -- {}",backup,LimitCount);
					if (StringUtil.isNotEmpty(backup)) {
						if (Integer.parseInt(backup) >= Integer.parseInt(LimitCount)) {
							addActionError("被保人已经购买过该产品了 ！");
							return ERROR;
						}
					}
				}
			}
			
			return SUCCESS;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionError("查询产品失败！");
			return ERROR;
		}
	}

	// 链接继续录入页面(zhangjinquan 11180 从OrderAction.java拷贝 2012-11-24)
	public String keepInput() {
		try {
			order = orderService.getOrderByOrderSn(orderSn);
			if (order == null) {
				addActionError("系统中无该订单信息啊!");
				return ERROR;
			}
			
			/* zhangjinquan 11180 2012-10-10 增加产品下架判断 start */
			if (ERROR.equals(isProductDownShelf(order.getProductId(), order.getProductName(), "继续录入" , order))) {
				/* 下架产品状态自动修改为自动取消 */
			
				if(!order.getOrderStatus().equals(OrderStatus.paid)){
					order.setOrderStatus(OrderStatus.autoinvalid);
					orderService.update(order);
					}
					return ERROR;
			}
			String orderSat = order.getOrderStatus()+"";
			if("paid".equals(orderSat)){
				return orderStatusPaidCheck(order);
			}else{
				orderId = order.getId();
				String pageString = buyNowUpdate();
				return pageString;
			}
		} catch (Exception e) {
			addActionError("出错了，请返回重试！");
			return ERROR;
		}

	}
	
	public String buyNowUpdate() {
		try {
			order = orderService.get(orderId);
			insureRiskType = order.geteRiskType();
			productId = order.getProductId();
			init(order.getInsuranceCompanySn());
			Set<OrderItem> orderItemSet = order.getOrderItemSet();
			OrderItem orderItem = null;
			for (OrderItem orderItems : orderItemSet) {
				orderItem = orderItems;
			}
			Set<Information> informationSet = orderItem.getInformationSet();
			for (Information infmn : informationSet) {
				information = infmn;
			}
			List<InformationInsured> infs = new ArrayList<InformationInsured>(
					information.getInformationInsuredSet());
			if (infs != null && infs.size() > 0) {
				informationInsureds = infs.get(0);
			}
			informationAppnt = information.getInformationAppnt();
			productId = order.getProductId();
			Map<String, Object> paramter = new HashMap<String, Object>();
			paramter = orderService.getProductInformation(productId, "N");
			String[] BaseInformation = new String[14];
			BaseInformation = (String[]) paramter.get("baseInformation");// 产品基础数据
			setSession("baseInformation", BaseInformation);
			// 产品投保要素
			riskAppFactior = (List<OrderRiskAppFactor>) paramter
					.get("riskAppFactor");
			if (riskAppFactior.size() > 0) {
				setSession("riskAppFactior", riskAppFactior);
				for (int i = 0; i < riskAppFactior.size(); i++) {
					OrderRiskAppFactor orderRiskAppFactor = riskAppFactior
							.get(i);
					// 拆分保障期限值，用于页面
					if ("Period".equals(orderRiskAppFactor.getFactorType()
							.toString())) {
						orderRiskAppFactor.getFactorValue();
						List<FEMRiskFactorList> periodList = orderRiskAppFactor
								.getFactorValue();
						FEMRiskFactorList fEMRiskFactorList = periodList.get(0);
						String[] periods = fEMRiskFactorList.getFactorValue()
								.split("-");
						String periodBe = "";
						String periodAf = "";
						if (periods != null && periods.length == 1) {
							periodBe = periods[0].toString();// 保障期限前段
							periodAf = periods[0].toString();// 保障期限后段

						} else if (periods != null && periods.length == 2) {
							periodBe = periods[0].toString();// 保障期限前段
							periodAf = periods[1].toString();// 保障期限后段
						}
						if (periodBe != null && !"".equals(periodBe)
								&& periodBe.length() > 1) {
							protectionPeriodTy = periodBe.substring(periodBe
									.length() - 1);// 保障期限类型
						} else {
							protectionPeriodTy = "";
						}
						if ("".equals(periodAf) || periodAf == "") {
							periodAf = periodBe;
						}
						if (periodAf != null && !"".equals(periodAf)
								&& periodAf.length() > 1) {
							protectionPeriodLast = periodAf.substring(0,
									periodAf.length() - 1);
						} else {
							protectionPeriodLast = "";
						}
						protectionPeriodFlag = "true";
					}
					if ("TextAge".equals(orderRiskAppFactor.getFactorType()
							.toString())) {
						textAgeFlag = "true";
					}
					if ("MulPeople".equals(orderRiskAppFactor.getFactorType()
							.toString())) {
						// this.mulPeople =
						// informationInsureds1.getInformationInsuredElementsSet();
						for (InformationInsuredElements informationInsuredElements : informationInsureds
								.getInformationInsuredElementsSet()) {
							if ("MulPeople".equals(informationInsuredElements
									.getElementsType())) {
								mulPeople = informationInsuredElements
										.getElementsValue();
							}
						}
					}
				}
			}
			// 产品责任要素
			dutyFactor = (List<OrderDutyFactor>) paramter.get("dutyFactor");
			if (dutyFactor.size() > 0) {
				setSession("dutyFactor", dutyFactor);
			}
			this.dataBuild();
			status = "update";
			if (getLoginMember() != null) {
				order.setMemberId(getLoginMember().getId());
				loginFlag = "true";
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionError("出错了，请返回重试！");
			return ERROR;
		}
		return resultPage(order.getInsuranceCompanySn(), order.geteRiskType());
	}

	public String orderUpdate() {
		try {
			productId = order.getProductId();
			quesFlag = questionPaper(productId);
			if (order.getOrderStatus() != null) {
				orderStatus = order.getOrderStatus().toString();
			}
			GetDBdata db = new GetDBdata();
			String paidSQL = " select orders.paymentStatus from orders where orders.id=? ";
			String[] paidtemp = {this.order.getId()} ;
			String paidState = db.getOneValue(paidSQL ,paidtemp);
			if("2".equals(paidState)){ 
				addActionError("该订单已经付款成功，请重新购买！");
				return ERROR;
			}
			if (!"temptorysave".equals(orderStatus)) {

				String sql = "select LimitCount,Occup,SectionAge,IsPublish from sdproduct where productid=?";
				String[] sqltemp = {productId};   
				//GetDBdata db = new GetDBdata();
				List<HashMap<String, String>> sdproduct = db.query(sql,sqltemp);   
				if (sdproduct == null || sdproduct.size() != 1) {
					addActionError("产品不存在，请上架产品！");
					return ERROR;
				}
				HashMap<String, String> product = sdproduct.get(0);
				String IsPublish = product.get("IsPublish");
				if (StringUtil.isEmpty(IsPublish) || "N".equals(IsPublish)) {
					addActionError("产品已下架！");
					return ERROR;
				}
				Date d = new Date();
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
				String tDate = com.sinosoft.sms.messageinterface.pubfun.PubFun
						.calSDate(sf.format(d), 1, "D");
				Date nowDate = com.sinosoft.sms.messageinterface.pubfun.PubFun
						.StringToDate(tDate);
				if ((effective.getTime() < nowDate.getTime())
						&& (fail.getTime() >= effective.getTime())) {
					addActionError("保单起保日期或者保单终止日期有误！");
					return ERROR;
				}
				String LimitCount = product.get("LimitCount");
				logger.info("保险公司限购份数：{}", LimitCount);
				if (StringUtil.isNotEmpty(LimitCount)
						&& !"0".equals(LimitCount)) {
					String backup_ = "select count(orders.id) from orders where  orderStatus=7 and productid='"
							+ productId
							+ "' and  id in( select order_id from orderitem where id in(";
					backup_ += "select information.orderitem_id from information where (('"+ sf.format(fail) +" 23:59:59'>=effective and effective>='"+ sf.format(effective) +" 00:00:00') or ('"+ sf.format(effective) +" 00:00:00'<=fail and fail<='"+ sf.format(fail) +" 23:59:59')) and  id in (select information_id from informationinsured ";
					backup_ += "where recognizeeIdentityType=? and recognizeeIdentityId=?)))";
					logger.info("保险公司限购份数（sql）：{}", backup_);
					String[] backuptemp = {informationInsureds.getRecognizeeIdentityType(),informationInsureds.getRecognizeeIdentityId()} ;
					String backup = db.getOneValue(backup_ ,backuptemp);
					logger.info("保险公司限购份数的值：{} -- {}", backup, LimitCount);
					if (StringUtil.isNotEmpty(backup)) {
						if (Integer.parseInt(backup) >= Integer
								.parseInt(LimitCount)) {
							addActionError("此被保人的证件号已经购买过此产品，超过限购份数！");
							return ERROR;
						}
					}
				}
				
				/**
				 * 保险投保与生效时间 间隔校验
				 */
                if("1065".equals(order.getInsuranceCompanySn())){
					
					int day = daysBetween(new Date(),effective);
					if(day>180){
						addActionError("投保与生效日期时间间隔最长为180天！");
						return ERROR;
					}
				
				}
				
				logger.info("职业校验：{}  productId:{}", informationInsureds.getOccupation3(), productId);
				if (informationInsureds.getOccupation3() != null) {
					if("1065".equals(order.getInsuranceCompanySn())){
						if("Y".equals(informationInsureds.getIsSelf())){
							if (!orderConfigService.validateOccup(informationAppnt.getApplicantOccupation3(), productId)) {
								addActionError("您的职业无法购买此产品(occup)！");
								return ERROR;
							}
						}
					}
				}
				String birthday = informationInsureds.getRecognizeeBirthday();
				if (StringUtil.isEmpty(birthday)) {
					birthday = informationAppnt.getApplicantBirthday();
					logger.info("保险人生日：{}", birthday);
				}
				logger.info("年龄校验：{}  productId:{}", birthday, productId);
				if (!orderConfigService.validateAge(birthday, productId,effective)) {
					addActionError("您无法购买此产品(birthday)！");
					return ERROR;
				}

				// 保险日期校验
				if (effective == null || fail == null) {
					addActionError("您无法购买此产品(effective)！");
					return ERROR;
				}
				String insurTypeChild = order.getSubRiskTypeCode();
				if ("22".equals(insurTypeChild) || "23".equals(insurTypeChild)
						|| "24".equals(insurTypeChild)
						|| "25".equals(insurTypeChild)
						|| "26".equals(insurTypeChild)) {
					if ("Y".equals(informationInsureds.getIsSelf())) {
						if ("男".equals(informationAppnt.getApplicantSexName())) {
							addActionError("您无法购买此产品(被保人必须为女性)！");
							return ERROR;
						}
					} else {
						if ("男".equals(informationInsureds.getIsSelf())) {
							addActionError("您无法购买此产品(被保人必须为女性)！");
							return ERROR;
						}
					}
				}
			}
			Order oldOrder = orderService.get(order.getId());
			Set<OrderItem> orderItemSet = oldOrder.getOrderItemSet();
			order.setBrkRiskCode(plan);
			order.seteRiskType(insureRiskType);
			oldOrder = orderConfigService.updateOrder(oldOrder, order);
			orderService.update(oldOrder);
			order = oldOrder;
			orderId = order.getId();
			OrderItem orderItem = null;
			for (OrderItem orderItems : orderItemSet) {
				orderItem = orderItems;
			}
			KID=StringUtil.md5Hex(this.getRequest().getSession().getId()+order.getOrderSn());
			Set<Information> informationSet = orderItem.getInformationSet();
			orderItem.setProductPrice(order.getProductTotalPrice());
			orderItemService.update(orderItem);
			for (Information infmn : informationSet) {
				information = infmn;
			}
			InformationAppnt ifa = information.getInformationAppnt();
			Set<InformationDuty> informationDutyElementsSet = information
					.getInformationDutyElementsSet();
			List<InformationInsured> infs = new ArrayList<InformationInsured>(
					information.getInformationInsuredSet());
			information.setEffective(effective); // 保单起始日期
			information.setFail(DateUtil.setTimeOfDate(fail, 23, 59, 59)); // 保单终止日期
			information.setAllPeriod(period);
			if ("Y".equals(protectionPeriodTy)) {
				protectionPeriodTy = "y";
			}
			if ("D".equals(protectionPeriodTy) || "".equals(protectionPeriodTy)) {
				protectionPeriodTy = "d";
			}
			if ("M".equals(protectionPeriodTy)) {
				protectionPeriodTy = "m";
			}
			information.setProtectionPeriodTy(protectionPeriodTy);
			if ("".equals(protectionPeriodLast)) {
				protectionPeriodLast = "1";
			}
			information.setProtectionPeriod(protectionPeriodLast);
			informationService.update(information);
			ifa = orderConfigService.updateInformationAppnt(ifa,
					informationAppnt);
			informationAppntService.update(ifa);
			InformationInsured infs1 = null;
			if (infs != null && infs.size() > 0) {
				infs1 = infs.get(0);
			}
			//zhangjinquan 11180 被保人为本人时从投保人拷贝数据 2012-11-28
			this.setInsuredFromAppnt(informationInsureds, informationAppnt);
			infs1 = orderConfigService.updateInformationInsured(infs1,
					informationInsureds);
			this.setDestinationCountry(order.getInsuranceCompanySn(), infs1, dictionaryService);
			informationInsuredService.update(infs1);
			informationInsureds.setId(infs1.getId());
			insuredHealthList = new ArrayList<InsuredHealth>(infs1.getInsuredHealthSet());
			Set<InformationInsuredElements> informationInsuredElementsSet = infs1
					.getInformationInsuredElementsSet();
			informationInsuredElementsUpdate(infs1,
					informationInsuredElementsSet);
			informationInsuredDutyUpdate(information,
					informationDutyElementsSet);
			if ("temptorysave".equals(orderStatus)) {
				return "tempsucces";// 确定成功页面后跳转用
			} else {
				if(insuredHealthList!=null && insuredHealthList.size()>0){
					return "healthy";
					
				}else{

					if(quesFlag){	
						mainAmnt = getAmnt(order.getId());
					//	questionPaper = infs1.getQuestionPaper();
						init(order.getInsuranceCompanySn());	//查询地区
						if(StringUtil.isNotEmpty(productId)){
							supplierCode2 = productId.substring(0, 4);
						}
						return "wjpage";
					}else{
						showInformaton(order.getId());
						this.setNeedUWCheckFlag(OrderAction.getNeedUWFlagFromDB(order.getInsuranceCompanySn()));
						return "result";
					}
				
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionError("订单修改失败，请重试！");
			return ERROR;
		}

	}
	/**
	 *修改健康告知信息
	 */
	public String saveOrUpdateHealthInf(){
		try {
			InformationInsured infIned = informationInsuredService.get(informationInsureds.getId());
			Set<InsuredHealth> ihSet = infIned.getInsuredHealthSet();
			if(ihSet!=null && ihSet.size()>0){
				for(InsuredHealth ih : ihSet){
					String selectFlag = getParameter(ih.getHealthyInfoId());
					ih.setSelectFlag(selectFlag);
					String typeShowOrder = getParameter(ih.getHealthyInfoId()+"order");
					ih.setTypeShowOrder(typeShowOrder);
					insuredHealthService.update(ih);
				}
			}
			showInformaton(order.getId());
			GetDBdata db = new GetDBdata();
			String paidSQL = " select orders.paymentStatus from orders where orders.id=? ";
			String[] paidtemp = {this.order.getId()} ;
			String paidState = db.getOneValue(paidSQL ,paidtemp);
			if("2".equals(paidState)){
				addActionError("该订单已经付款成功，请重新购买！");
				return ERROR;
			} 
			KID=StringUtil.md5Hex(this.getRequest().getSession().getId()+order.getOrderSn()) ; 
			this.setNeedUWCheckFlag(OrderAction.getNeedUWFlagFromDB(order.getInsuranceCompanySn()));
			return "result";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionError("健康告知存储失败，请重试！");
			return ERROR;
		}
	}
	private void showInformaton(String order_Id){
		order = orderService.get(order_Id);
		Set<OrderItem> orderItemSet = order.getOrderItemSet();
		OrderItem orderItem = null;
		for (OrderItem orderItems : orderItemSet) {
			orderItem = orderItems;
		}
		Set<Information> informationSet = orderItem.getInformationSet();
		for (Information infmn : informationSet) {
			information = infmn;
		}
		informationAppnt = information.getInformationAppnt();
		if(StringUtil.isNotEmpty(informationAppnt.getApplicantArea2())){
			informationAppnt.setApplicantArea2(areaService.getAreaName(informationAppnt.getApplicantArea2()));
			
		}
		Set<InformationDuty> informationDutyElementsSet = information.getInformationDutyElementsSet();
		Set<InformationInsured> informationInsuredSet = information.getInformationInsuredSet();
		if(informationInsuredSet!=null && informationInsuredSet.size()>0){
			showInsureds = informationInsuredService.createShowInformationInsured(informationInsuredSet,order.getInsuranceCompanySn());
		}
		if(informationDutyElementsSet!=null && informationDutyElementsSet.size()>0){
			showDuty = new ArrayList<InformationDuty>(informationDutyElementsSet);
		}
	}

	/**
	 * 
	 * 投保人信息保存
	 */
	private void informationAppntSave(Information informations) {
		informationAppnt.setInformation(informations);
		informationAppntService.save(informationAppnt);
	}

	/**
	 * zhangjinquan 11180 单独提取出函数设置被保人为本人时的数据 2012-11-28
	 * @param insured 被保人
	 * @param appnt 投保人
	 */
	private void setInsuredFromAppnt(InformationInsured insured, InformationAppnt appnt)
	{
		if ((null == insured) || (null == appnt))
		{
			return;
		}
		if ("Y".equals(insured.getIsSelf()))
		{
			insured.setRecognizeeName(appnt.getApplicantName());
			insured.setRecognizeeIdentityType(appnt.getApplicantIdentityType());
			insured.setRecognizeeIdentityId(appnt.getApplicantIdentityId());
			insured.setRecognizeeIdentityTypeName(appnt.getApplicantIdentityTypeName());
			insured.setRecognizeeSex(appnt.getApplicantSex());
			insured.setRecognizeeSexName(appnt.getApplicantSexName());
			insured.setRecognizeeMail(appnt.getApplicantMail());
			insured.setRecognizeeArea1(appnt.getApplicantArea1());
			insured.setRecognizeeArea2(appnt.getApplicantArea2());
			insured.setRecognizeeBirthday(appnt.getApplicantBirthday());
			insured.setRecognizeeMobile(appnt.getApplicantMobile());
			insured.setRecognizeeFirstEnName(appnt.getApplicantFirstEnName());
			insured.setRecognizeeLastEnName(appnt.getApplicantLastEnName());
			insured.setRecognizeeAddress(appnt.getApplicantAddress());
			insured.setRecognizeeZipCode(appnt.getApplicantZipCode());
			//zhangjinquan 11180 保存被保险人的职业类别 2012-11-28
			 
			if(StringUtil.isNotEmpty(appnt.getApplicantOccupation1())){
			    insured.setOccupation1(appnt.getApplicantOccupation1());
			}
			if(StringUtil.isNotEmpty(appnt.getApplicantOccupation2()) ){
				insured.setOccupation2(appnt.getApplicantOccupation2());
				
			}
			if(StringUtil.isNotEmpty(appnt.getApplicantOccupation3()) ){
				insured.setOccupation3(appnt.getApplicantOccupation3());
				
			}
			
		}
	}
	
	/**
	 * 
	 * 被保人信息保存
	 */
	public void informationInsuredSave(Information informations,
			Set<InformationInsured> informationInsuredSet, Order or,Boolean flag) {
		Set<InformationInsuredElements> informationInsuredElementsSet = new HashSet<InformationInsuredElements>();
		Set<InsuredHealth> insuredHealthSet = new HashSet<InsuredHealth>();
		InformationRiskType informationRiskTypes = new InformationRiskType();
		informationRiskTypes.setId("200001");// 险种，由接口获得
		informationRiskTypeService.save(informationRiskTypes);
		informationInsureds.getRecognizeeAppntRelation();
		//zhangjinquan 11180 单独提取出函数设置被保人为本人时的数据 2012-11-28
		this.setInsuredFromAppnt(informationInsureds, informationAppnt);
		informationInsureds
				.setInformationInsuredElementsSet(informationInsuredElementsSet);
		informationInsureds.setInsuredHealthSet(insuredHealthSet);
		if(flag){
			//informationInsureds.setQuestionPaper(questionPaper);
		}
		informationInsureds.setInformation(informations);
		informationInsuredSet.add(informationInsureds);
		OccuCheck(or.getProductId(),informationInsureds);
		informationInsuredService.save(informationInsureds);
		informationInsuredElementsSave(informationInsureds,
				informationInsuredElementsSet);
		insuredHealtySave(informationInsureds, insuredHealthSet, or);
		if(flag){
			questionPaper.setOrdID(or.getOrderSn());
		//	questionPaper.setInformationInsured(informationInsureds);
			questionPaperService.save(questionPaper);
		}
	}
	/***
	 * 昆仑职业校验 少儿险只能选学龄前儿童和普通学生
	 * @param productid 产品id
	 * @param insured 被保人信息
	 */
	public void OccuCheck(String productid,InformationInsured insured){
		if(productid.equals("106501019") || productid.equals("106501002") || productid.equals("106501018") || productid.equals("106501017")){
			String birthday = insured.getRecognizeeBirthday();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date birthDay = dateFormat.parse(birthday);
				int age = getAge(birthDay);
				if(age<6){
					insured.setOccupation1("KL08");
					insured.setOccupation2("KL0801");
					insured.setOccupation3("KL0801001");
				}
				if(6<age && age<18){
					insured.setOccupation1("KL01");
					insured.setOccupation2("KL0103");
					insured.setOccupation3("KL0103002");
				}
			} catch (ParseException e) {
				logger.error("日期转换错误" + e.getMessage(), e);
			} catch (Exception e) {
				logger.error("获取年龄出现错误" + e.getMessage(), e);
			}
		}
	}
	/***
	 * 昆仑少儿产品根据生日获取被保人年龄
	 * @param birthDay 被保人生日
	 * @return age 被保人年龄
	 * @throws Exception
	 */
	 public static int getAge(Date birthDay) throws Exception {
	        Calendar cal = Calendar.getInstance();

	        if (cal.before(birthDay)) {
	            throw new IllegalArgumentException(
	                "The birthDay is before Now.It's unbelievable!");
	        }

	        int yearNow = cal.get(Calendar.YEAR);
	        int monthNow = cal.get(Calendar.MONTH);
	        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
	        cal.setTime(birthDay);

	        int yearBirth = cal.get(Calendar.YEAR);
	        int monthBirth = cal.get(Calendar.MONTH);
	        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

	        int age = yearNow - yearBirth;

	        if (monthNow <= monthBirth) {
	            if (monthNow == monthBirth) {
	                //monthNow==monthBirth
	                if (dayOfMonthNow < dayOfMonthBirth) {
	                    age--;
	                } else {
	                    //do nothing
	                }
	            } else {
	                //monthNow>monthBirth
	                age--;
	            }
	        } else {
	            //monthNow<monthBirth
	            //donothing
	        }

	        return age;
	    }

	private void insuredHealtySave(InformationInsured informationInsureds2,
			Set<InsuredHealth> insuredHealthSet, Order or) {
		String productId = or.getProductId();
		String comCode = or.getInsuranceCompanySn();
		List<HealthyInfo> healthList = healthyInfoService.findByComAndProduct(
				productId, comCode);
		insuredHealthList = insuredHealthService
				.createShowInformation(healthList);
		if (insuredHealthList != null && insuredHealthList.size() > 0) {
			for (InsuredHealth ih : insuredHealthList) {
				ih.setInformationInsured(informationInsureds2);
				insuredHealthService.save(ih);
			}
		}

	}
	

	/**
	 * 
	 * 保存责任
	 */
	private void informationInsuredDutySave(Information inf,
			Set<InformationDuty> informationDutyElementsSet) {
		dutyFactor = (List<OrderDutyFactor>) getSession("dutyFactor");
		JSONObject dutyJsonArray = JSONObject.fromObject(dutyReq);
		JSONObject showDutyJsonArray = JSONObject.fromObject(dutyDisReq);
		JSONObject premJsonArray = JSONObject.fromObject(dutyPremReq);
		
		if (dutyFactor != null) {
			for (int i = 0; i < dutyFactor.size(); i++) {
				Object factorValueTemp = dutyJsonArray.get(dutyFactor.get(i)
						.getDutyCode());
				//zhangjinquan 11180 保额为空时不保存该责任 2012-11-30
				if((StringUtil.isEmpty((String)factorValueTemp)) || "nvalue".equals(factorValueTemp))
				{
					continue;
				}
				Object showDutyValue = showDutyJsonArray.get(dutyFactor.get(i)
						.getDutyCode());
				Object premDutyValue = premJsonArray.get(dutyFactor.get(i)
						.getDutyCode());
				InformationDuty informationDuty = new InformationDuty();
				informationDuty.setDutySn(dutyFactor.get(i).getDutyCode());
				informationDuty.setDutyName(dutyFactor.get(i)
						.getDudtyFactorName());
				informationDuty.setAmnt(factorValueTemp.toString());
				informationDuty.setCoverage(dutyFactor.get(i).getDefine());
				informationDuty.setOrderId(orderId);
				if(!"nvalue".equals(showDutyValue)){
					informationDuty.setShowAmnt(showDutyValue + "");
				}else{
					informationDuty.setShowAmnt("0.0");
				}
				if(!"nvalue".equals(premDutyValue)){
					informationDuty.setTimePrem(premDutyValue+"");
				}else{
					informationDuty.setTimePrem("0.0");
				}
				informationDuty.setMainRiskFlag(dutyFactor.get(i).getCurrency());
				informationDuty.setInformation(inf);
				informationDuty.setSupplierDutyCode(dutyFactor.get(i).getSupplierDutyCode());
				informationDutyElementsSet.add(informationDuty);
				informationDutyService.save(informationDuty);
			}
		}
	}

	private void informationInsuredDutyUpdate(Information inf,
			Set<InformationDuty> informationDutyElementsSet) {
		if (informationDutyElementsSet != null
				&& informationDutyElementsSet.size() > 0) {
			for (InformationDuty infDuty : informationDutyElementsSet) {
				informationDutyService.delete(infDuty);
			}
			//zhangjinquan 11180 删除后，应该清除Set中的记录 2012-12-04
			informationDutyElementsSet.clear();
		}
		//zhangjinquan 11180 相同代码直接调用已有函数 2012-11-30
		this.informationInsuredDutySave(inf, informationDutyElementsSet);
	}

	/**
	 * 保存产品投保要素
	 */
	private void informationInsuredElementsSave(
			InformationInsured informationInsured,
			Set<InformationInsuredElements> informationInsuredElementsSet) {
		riskAppFactior = (List<OrderRiskAppFactor>) getSession("riskAppFactior");
		JSONObject insureJsonJsonArray = JSONObject.fromObject(insureReq);
		if (riskAppFactior != null) {
			for (int i = 0; i < riskAppFactior.size(); i++) {
				String appFactiorType = riskAppFactior.get(i).getFactorType()
						.toString();

				if (!"TextAge".equals(appFactiorType) && riskAppFactior != null
						&& riskAppFactior.get(i) != null
						&& insureJsonJsonArray != null) {
					Object factorValueTemp = insureJsonJsonArray
							.get(riskAppFactior.get(i).getFactorType());

					InformationInsuredElements informationInsuredElements = new InformationInsuredElements();
					informationInsuredElements.setElementsType(appFactiorType);
					informationInsuredElements.setElementsSn(riskAppFactior
							.get(i).getAppFactorCode());
					if (factorValueTemp != null)
						informationInsuredElements
								.setElementsValue(factorValueTemp.toString());

					informationInsuredElements
							.setInformationInsured(informationInsured);
					informationInsuredElementsSet
							.add(informationInsuredElements);
					informationInsuredElementsService
							.save(informationInsuredElements);

				}
			}
		}
	}

	/**
	 * 修改被保人的产品投保要素
	 */
	private void informationInsuredElementsUpdate(InformationInsured infs1,
			Set<InformationInsuredElements> informationInsuredElementsSet) {
		try {
			if (informationInsuredElementsSet != null
					&& informationInsuredElementsSet.size() > 0) {
				for (InformationInsuredElements infInsuredElement : informationInsuredElementsSet) {
					informationInsuredElementsService.delete(infInsuredElement);
				}
			}
			riskAppFactior = (List<OrderRiskAppFactor>) getSession("riskAppFactior");
			JSONObject insureJsonJsonArray = JSONObject.fromObject(insureReq);
			if (riskAppFactior != null) {
				for (int i = 0; i < riskAppFactior.size(); i++) {
					String appFactiorType = riskAppFactior.get(i)
							.getFactorType().toString();

					if (!"TextAge".equals(appFactiorType)
							&& riskAppFactior != null
							&& riskAppFactior.get(i) != null
							&& insureJsonJsonArray != null) {
						Object factorValueTemp = insureJsonJsonArray
								.get(riskAppFactior.get(i).getFactorType());

						InformationInsuredElements informationInsuredElements = new InformationInsuredElements();
						informationInsuredElements
								.setElementsType(appFactiorType);
						informationInsuredElements.setElementsSn(riskAppFactior
								.get(i).getAppFactorCode());
						if (factorValueTemp != null)
							informationInsuredElements
									.setElementsValue(factorValueTemp
											.toString());

						informationInsuredElements.setInformationInsured(infs1);
						informationInsuredElementsSet
								.add(informationInsuredElements);
						informationInsuredElementsService
								.save(informationInsuredElements);

					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 
	 * 产品投保要素组装
	 */
	private void dataBuild() {
		if (riskAppFactior != null && riskAppFactior.size() > 0) {
			for (OrderRiskAppFactor orderRiskAppFactor : riskAppFactior) {
				// 计划列表
				if ("Plan".equals(orderRiskAppFactor.getFactorType())) {
					planList.addAll(orderRiskAppFactor.getFactorValue());
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
				// 保险责任多类人群列表
				if ("MulPeople".equals(orderRiskAppFactor.getFactorType())) {
					mulPeopleList.addAll(orderRiskAppFactor.getFactorValue());
				}
			}
		}
	}

	/**
	 * 
	 * 根据保险公司查询返回页面
	 */
	private String resultPage(String insuranceCompanySn, String insurType) {
		try {
			String pageString = "";
			if (insuranceCompanySn != null && !"".equals(insuranceCompanySn)) {
				pageString = pagelayoutService
						.getReturnPageByCompany(insuranceCompanySn);
			}
			return pageString;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionError("查询页面失败，请重试！");
			return ERROR;
		}
	}

	private void init(String insuranceCompanySn) {
		certificateList = dictionaryService.findListByCom("certificate",
				insuranceCompanySn);
		relationList = dictionaryService.findListByCom("Relationship",
				insuranceCompanySn,productId);
		occupationList = occupationService.findSuperByCom(insuranceCompanySn,productId);// 查询一级职业
		sexList = dictionaryService.findListByCom("Sex", insuranceCompanySn);
		marriageStutas = dictionaryService.findListByCom("MarriageStutas", insuranceCompanySn);
		areaList = areaService.findSuperByCom(productId, insuranceCompanySn);
		listCountryCode = dictionaryService.findListByCom("CountryCode",
				insuranceCompanySn,productId);
		productConfigs = productConfigService.findPCByRiskCode(productId,
				insuranceCompanySn);// 根据产品编码查询配置项信息
		nationalityList = dictionaryService.findListByCom("nationality",
				insuranceCompanySn);// 查询国籍列表
		travelModelList = dictionaryService.findListByCom("travelMode", insuranceCompanySn);
		travelTypeList = dictionaryService.findListByCom("travelType", insuranceCompanySn);
		String stp = productPeriodService.getStartPeriod(insuranceCompanySn, productId);
		if(stp!=null && !"".equals(stp)){
			startPeriod = Integer.parseInt(stp);
		}
		logger.info("地区==========={}", areaList.size());
	}
	// 从我的订单链接到订单详细页面
	public String linkOrderDetails() {
		
		String nKID = StringUtil.md5Hex(this.getRequest().getSession().getId()+orderSn);
		if(!nKID.equals(KID)){
			addActionError("请登陆后，进行此操作！");
			return ERROR;
		} 
		
		try {
			order = orderService.getOrderByOrderSn(orderSn);
			showInformaton(order.getId());
		} catch (Exception e) {
			addActionError("连接错误");
			return ERROR;
		}
		this.setNeedUWCheckFlag(OrderAction.getNeedUWFlagFromDB(order.getInsuranceCompanySn()));
		this.setCanPreviewOperatorFlag("0");
		return "result";
	}
	/**
	 * 泰康之后的产品使用试算方法
	 */
	public String ajaxCounter() {
		// String [] factorValue =new String [1];
		baseInformations = (String[]) getSession("baseInformation");
		riskAppFactior = (List<OrderRiskAppFactor>) getSession("riskAppFactior");
		dutyFactor = (List<OrderDutyFactor>) getSession("dutyFactor");

		String productID = null;
		for (int i = 0; riskAppFactior != null && i < riskAppFactior.size(); i++) {
			OrderRiskAppFactor oraf = riskAppFactior.get(i);
			productID = oraf.getProductCode();
		}

		String productID_ = null;
		for (int i = 0; dutyFactor != null && i < dutyFactor.size(); i++) {
			OrderDutyFactor odf = dutyFactor.get(i);
			productID_ = odf.getProductCode();
		}

		if (StringUtil.isNotEmpty(productID)
				&& StringUtil.isNotEmpty(productID_)) {
			if (!productID.equals(productID_)) {
				return null;
			}
		}
		String pID = null;
		if (StringUtil.isNotEmpty(productID)) {
			pID = productID;
		}
		if (StringUtil.isNotEmpty(productID_)) {
			pID = productID_;
		}
		List<OrderDutyFactor> dutyFactorLast = new ArrayList<OrderDutyFactor>();
		try {
			insureJson = java.net.URLDecoder.decode(insureJson, "utf-8");

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		JSONObject insureJsonJsonArray = JSONObject.fromObject(insureJson);
		// System.out.println("=====" + insureJson);
		// System.out.println("=====" + dutyJson);
		for (int i = 0; i < riskAppFactior.size(); i++) {
			// OrderRiskAppFactor orderRiskAppFactor = riskAppFactior.get(i);
			if ("TextAge".equals(riskAppFactior.get(i).getFactorType())) {
				String textage = insureJsonJsonArray.get("TextAge").toString();
				Object factorValueTemp = insureJsonJsonArray.get(riskAppFactior
						.get(i).getFactorType());
				if ("".equals(factorValueTemp) || factorValueTemp == null) {
					FEMRiskFactorList femr = riskAppFactior.get(i).getFactorValue().get(0);
					List<FEMRiskFactorList> factorValue = new ArrayList<FEMRiskFactorList>();
					FEMRiskFactorList riskFactor = new FEMRiskFactorList();
					if ("".equals(textage)) {
						if(femr!=null && femr.getFactorValue()!=null){
							riskFactor.setFactorValue(orderConfigService.getBrithdayByFactor(femr.getFactorValue()));
						}else{
							riskFactor.setFactorValue("1991-01-01");
						}
					} else {
						riskFactor.setFactorValue(factorValueTemp.toString());
					}
					riskFactor.setAppFactorCode(riskAppFactior.get(i)
							.getAppFactorCode());
					riskFactor.setFactorType(riskAppFactior.get(i)
							.getFactorType());
					riskFactor.setIsPremCalFacotor(riskAppFactior.get(i)
							.getIsPremCalFacotor());
					factorValue.add(riskFactor);
					riskAppFactior.get(i).setFactorValue(factorValue);
				} else {
					riskAppFactior.get(i).getFactorValue().get(0);
					List<FEMRiskFactorList> factorValue = new ArrayList<FEMRiskFactorList>();
					FEMRiskFactorList riskFactor = new FEMRiskFactorList();
					if (factorValueTemp != null)
						riskFactor.setFactorValue(factorValueTemp.toString());
					riskFactor.setAppFactorCode(riskAppFactior.get(i)
							.getAppFactorCode());
					riskFactor.setFactorType(riskAppFactior.get(i)
							.getFactorType());
					riskFactor.setIsPremCalFacotor(riskAppFactior.get(i)
							.getIsPremCalFacotor());
					factorValue.add(riskFactor);
					riskAppFactior.get(i).setFactorValue(factorValue);
				}
			} else {
				Object factorValueTemp = insureJsonJsonArray.get(riskAppFactior
						.get(i).getFactorType());
				riskAppFactior.get(i).getFactorValue().get(0);
				List<FEMRiskFactorList> factorValue = new ArrayList<FEMRiskFactorList>();
				FEMRiskFactorList riskFactor = new FEMRiskFactorList();
				if (factorValueTemp != null)
					riskFactor.setFactorValue(factorValueTemp.toString());
				riskFactor.setAppFactorCode(riskAppFactior.get(i)
						.getAppFactorCode());
				riskFactor.setFactorType(riskAppFactior.get(i).getFactorType());
				riskFactor.setIsPremCalFacotor(riskAppFactior.get(i)
						.getIsPremCalFacotor());
				factorValue.add(riskFactor);
				riskAppFactior.get(i).setFactorValue(factorValue);
			}
		}

		JSONObject dutyJsonJsonArray = JSONObject.fromObject(dutyJson);

		for (OrderDutyFactor orderDutyFactor : dutyFactor) {
			String dutyValueTemp = dutyJsonJsonArray.get(
					orderDutyFactor.getDutyCode()).toString();
			if (orderDutyFactor.getFdAmntPremList() != null) {
				orderDutyFactor.getFdAmntPremList().clear();
			}
			FEMDutyAmntPremList fEMDutyAmntPremList = new FEMDutyAmntPremList();
			List<FEMDutyAmntPremList> fdAmntPremList = new ArrayList();
			if ("nvalue".equals(dutyValueTemp)) {
				// System.out.println("dutyValueTemp=" + dutyValueTemp);
			} else {
				fEMDutyAmntPremList.setBackUp1(dutyValueTemp);
				fdAmntPremList.add(fEMDutyAmntPremList);
				orderDutyFactor.setFdAmntPremList(fdAmntPremList);
				dutyFactorLast.add(orderDutyFactor);
			}
		}
		if (dutyFactorLast.size() == 0) {
			dutyFactorLast = null;
		}
		Map<String, Object> paramter = new HashMap<String, Object>();
		paramter.put("baseInformation", baseInformations);
		paramter.put("riskAppFactor", riskAppFactior);
		paramter.put("dutyFactor", dutyFactorLast);

		Map<String, Object> mapPrem = orderService
				.getProductPremDutyAmounts(paramter);
		String countPrice = mapPrem.get("countPrice").toString();
		String retCountPrem = String.valueOf(countPrice);
		setSession("TotelPrem_" + pID, retCountPrem);
		Map<String, Object> price = new HashMap<String, Object>();
		price.put("retTotlePrem", mapPrem.get("totlePrem"));// 总保费
		price.put("retCountPrem", mapPrem.get("countPrice"));// 折扣后保费
		price.put("retDutyAmounts", mapPrem.get("dutyAmounts"));// 责任明细
		price.put("discountRate", mapPrem.get("DiscountRate"));//折扣率
		JSONObject jsonObject = JSONObject.fromObject(price);
		return ajax(jsonObject.toString(), "text/html");
	}
	
	/**
	 * 已支付订单非法操作的验证
	 */
	
	private String orderStatusPaidCheck(Order or){
		informationAppnt = or.getOrderItemSet().iterator().next().getInformationSet().iterator().next().getInformationAppnt();
		showDuty = new ArrayList<InformationDuty>(or.getOrderItemSet().iterator().next().getInformationSet().iterator().next().getInformationDutyElementsSet());
		showInsureds = informationInsuredService.createShowInformationInsured(or.getOrderItemSet().iterator().next().getInformationSet().iterator().next().getInformationInsuredSet(),or.getInsuranceCompanySn());
		if (or == null) {
			addActionError("系统中没有该订单的存在!");
			return ERROR;
		}
		productId = or.getProductId();
		information = informationService.getByProduct(productId, or.getId());
		return "resultxx";
	}
	private String cpsProductJump(String pId,String jumpAddress){
		Member m = getLoginMember();
		try {
			String ip = getRequest().getHeader("x-forwarded-for");
			if (ip == null || ip.length() == 0
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = getRequest().getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = getRequest().getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = getRequest().getRemoteAddr();
			}
			ZdrecordCps zdc = zdrecordCpsService.createZDRecordCPS(productId, ip, m , jumpAddress);
			zdrecordCpsService.save(zdc);
			getResponse().sendRedirect(jumpAddress);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	public List<ProductConfig> getProductConfigs() {
		return productConfigs;
	}

	public void setProductConfigs(List<ProductConfig> productConfigs) {
		this.productConfigs = productConfigs;
	}

	public InformationAppnt getInformationAppnt() {
		return informationAppnt;
	}

	public void setInformationAppnt(InformationAppnt informationAppnt) {
		this.informationAppnt = informationAppnt;
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

	public List<FEMRiskFactorList> getMulPeopleList() {
		return mulPeopleList;
	}

	public void setMulPeopleList(List<FEMRiskFactorList> mulPeopleList) {
		this.mulPeopleList = mulPeopleList;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public InformationInsured getInformationInsureds() {
		return informationInsureds;
	}

	public void setInformationInsureds(InformationInsured informationInsureds) {
		this.informationInsureds = informationInsureds;
	}

	public Information getInformation() {
		return information;
	}

	public void setInformation(Information information) {
		this.information = information;
	}

	public String getLoginFlag() {
		return loginFlag;
	}

	public void setLoginFlag(String loginFlag) {
		this.loginFlag = loginFlag;
	}

	public String getTextAgeFlag() {
		return textAgeFlag;
	}

	public void setTextAgeFlag(String textAgeFlag) {
		this.textAgeFlag = textAgeFlag;
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

	public List<List<InsuredShow>> getShowInsureds() {
		return showInsureds;
	}

	public void setShowInsureds(List<List<InsuredShow>> showInsureds) {
		this.showInsureds = showInsureds;
	}

	public List<InformationDuty> getShowDuty() {
		return showDuty;
	}

	public void setShowDuty(List<InformationDuty> showDuty) {
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

	public String getBrkRiskCode() {
		return brkRiskCode;
	}

	public void setBrkRiskCode(String brkRiskCode) {
		this.brkRiskCode = brkRiskCode;
	}

	public Date getEffective() {
		return effective;
	}

	public void setEffective(Date effective) {
		this.effective = effective;
	}

	public Date getFail() {
		return fail;
	}

	public void setFail(Date fail) {
		this.fail = fail;
	}

	public String getDutyReq() {
		return dutyReq;
	}

	public void setDutyReq(String dutyReq) {
		this.dutyReq = dutyReq;
	}

	public String getDutyDisReq() {
		return dutyDisReq;
	}

	public void setDutyDisReq(String dutyDisReq) {
		this.dutyDisReq = dutyDisReq;
	}

	public String getInsureReq() {
		return insureReq;
	}

	public void setInsureReq(String insureReq) {
		this.insureReq = insureReq;
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

	public List<InsuredHealth> getInsuredHealthList() {
		return insuredHealthList;
	}

	public void setInsuredHealthList(List<InsuredHealth> insuredHealthList) {
		this.insuredHealthList = insuredHealthList;
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

	public String getFeeYear() {
		return feeYear;
	}

	public void setFeeYear(String feeYear) {
		this.feeYear = feeYear;
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

	public List<Dictionary> getMarriageStutas() {
		return marriageStutas;
	}

	public void setMarriageStutas(List<Dictionary> marriageStutas) {
		this.marriageStutas = marriageStutas;
	}

	public String getSupplierCode2() {
		return supplierCode2;
	}

	public void setSupplierCode2(String supplierCode2) {
		this.supplierCode2 = supplierCode2;
	}

	public QuestionPaper getQuestionPaper() {
		return questionPaper;
	}

	public void setQuestionPaper(QuestionPaper questionPaper) {
		this.questionPaper = questionPaper;
	}

	public String getMainAmnt() {
		return mainAmnt;
	}

	public void setMainAmnt(String mainAmnt) {
		this.mainAmnt = mainAmnt;
	}
	
	
}
