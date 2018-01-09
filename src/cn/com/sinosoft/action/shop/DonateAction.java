package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.entity.Information;
import cn.com.sinosoft.entity.InformationAppnt;
import cn.com.sinosoft.entity.InformationDuty;
import cn.com.sinosoft.entity.InformationInsured;
import cn.com.sinosoft.entity.InformationInsuredElements;
import cn.com.sinosoft.entity.InformationRiskType;
import cn.com.sinosoft.entity.InsuredCompanyReturnData;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.MemberDonated;
import cn.com.sinosoft.entity.Order;
import cn.com.sinosoft.entity.Order.InsureStatus;
import cn.com.sinosoft.entity.Order.OrderStatus;
import cn.com.sinosoft.entity.Order.PaymentStatus;
import cn.com.sinosoft.entity.OrderDutyFactor;
import cn.com.sinosoft.entity.OrderItem;
import cn.com.sinosoft.entity.OrderLog;
import cn.com.sinosoft.entity.OrderRiskAppFactor;
import cn.com.sinosoft.entity.TradeInformation;
import cn.com.sinosoft.service.InformationAppntService;
import cn.com.sinosoft.service.InformationDutyService;
import cn.com.sinosoft.service.InformationInsuredElementsService;
import cn.com.sinosoft.service.InformationInsuredService;
import cn.com.sinosoft.service.InformationRiskTypeService;
import cn.com.sinosoft.service.InformationService;
import cn.com.sinosoft.service.InsuredCompanyReturnDataService;
import cn.com.sinosoft.service.MemberDonatedService;
import cn.com.sinosoft.service.MemberService;
import cn.com.sinosoft.service.OrderItemService;
import cn.com.sinosoft.service.OrderService;
import cn.com.sinosoft.service.PageLayoutService;
import cn.com.sinosoft.service.TradeInformationService;
import cn.com.sinosoft.util.JdbcTemplateData;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.jdt.InsureTransferNew;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FEMDutyAmntPremList;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FEMRiskFactorList;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
 
/**
 * 前台Action类 -赠送险处理
 * ============================================================================
 * KEY:SINOSOFT5FA5DE6C97F725ADBB195DC008BFB613
 * ============================================================================
 */
@ParentPackage("member")
public class DonateAction extends BaseShopAction {

	private HttpServletRequest request = ServletActionContext.getRequest();
	private int page = 1;
	public static final int pagesize = 11;
	private int count;
	private int lastpage;
	private Integer totalQuantity;// 商品总数
	private BigDecimal productTotalPrice;// 总计商品价格
	private String id = request.getParameter("id");
	private String productId;
	private String orderId;
	private Order order = new Order();// 订单
	private OrderLog orderLog = new OrderLog();// 订单日志
	private OrderItem orderItem = new OrderItem();
	private Information information = new Information();
	private Information information1 = new Information();
	private Set<OrderItem> orderItemSet = new HashSet<OrderItem>();
	private Set<Information> informationSet = new HashSet<Information>();
	private Set<InformationInsured> informationInsuredSet = new HashSet<InformationInsured>();
	private Set<InformationInsuredElements> informationInsuredElementsSet = new HashSet<InformationInsuredElements>();
	private Set<InformationDuty> informationDutyElementsSet = new HashSet<InformationDuty>();
	private List<InformationInsured> showInsureds = new ArrayList<InformationInsured>();

	private String applicantName;// 投保人姓名
	private String applicantIdentityType;// 投保人证件类型
	private String applicantIdentityId;// 投保人证件号码
	private String applicantSex;// 投保人性别
	private String applicantBirthday;// 投保人出生日期
	private String applicantTel;// 投保人电话
	private String applicantMail;// 投保人电子邮箱
	private String applicantArea;// 投保人所在地
	private String applicantMobile;// 投保人手机
	private String price;// 价格
	private String protectionPeriodTy;// 保障期限类型
	private String protectionPeriodLast;// 保障期限后段数值
	private String protectionPeriodFlag;// 是否存在保障期限
	private String recognizeeName;// 被保人姓名
	private String recognizeeIdentityType;// 被保人证件类型
	private String recognizeeIdentityId;// 被保人证件号码
	private String recognizeeSex;// 被保人性别
	private String recognizeeBirthday;// 被保人出生日期
	private String recognizeeTel;// 被保人电话
	private String recognizeeMail;// 被保人电子邮箱
	private String recognizeeZipCode;// 被保人邮政编码
	private String occupation;// 被保人邮政编码
	private String occupation1;// 职业1
	private String occupation2;// 职业2
	private String occupation3;// 职业3
	private String recognizeeArea;// 被保人所在地

	private String insuranceCompany;// 保险公司
	private String insuranceCompanySn;// 保险公司

	private String outRiskCode;// 保险公司产品编码
	private String productType;// 产品类型
	private String productImg;// 产品图片
	private String riskType;// 险种
	private String prem;// 保费
	private String productName;// 产品名称
	private String insurType;// 保险类型
	private String insurTypeChild;// 保险小类
	private String orderSn;// 订单号
	private String ldate;// 出单起期
	private String hdate;// 出单止期
	private String orderStatus;// 订单状态
	private String applicant;// 投保人

	private String associateBefore;// 条件映射1
	private String associateAfter;// 条件映射2
	private String appDate; // 投保时间
	private String appLevel;// 缴费方式
	private String appType;// 投保类别
	private String feeYear;// 缴费年期
	private String grade;// 产品级别
	private String mulPeople;// 保险责任多类人群列表
	private String numAge;// 年龄
	private String occup;// 职业类别
	private String period;// 保险期限
	private String sectionAge;// 分段年龄
	private String sex;// 性别
	private String textAge;// 文字年龄
	private String amnt;// 保额
	private String plan;// 计划
	private Date effective;
	private Date fail;
	private List<FEMRiskFactorList> periodList = new ArrayList<FEMRiskFactorList>();// 计划值列表
	private String factorType = "";
	private List<OrderDutyFactor> dutyFactor = new ArrayList<OrderDutyFactor>();
	private List<OrderRiskAppFactor> riskAppFactior = new ArrayList<OrderRiskAppFactor>();
	private Map<String, String> associate = new HashMap<String, String>(); // 投保元素映射
	private List<FEMRiskFactorList> planList = new ArrayList<FEMRiskFactorList>();// 计划值列表
	private List<FEMRiskFactorList> occupList = new ArrayList<FEMRiskFactorList>();// 职业类别
	private List<FEMRiskFactorList> gradeList = new ArrayList<FEMRiskFactorList>();// 产品级别列表
	private List<FEMRiskFactorList> feeYearList = new ArrayList<FEMRiskFactorList>();// 缴费年期列表
	private List<FEMRiskFactorList> appLevelList = new ArrayList<FEMRiskFactorList>();// 缴费方式列表
	private List<FEMRiskFactorList> appTypeList = new ArrayList<FEMRiskFactorList>();// 投保类别列表
	private String insureJson;// ajax传递投保试算参数
	private String dutyJson;// ajax传递责任参数
	private String[] baseInformations = new String[6];
	private InformationInsured informationInsureds = new InformationInsured();
	private InformationInsured informationInsureds1 = new InformationInsured();
	private InformationInsured informationInsureds2 = new InformationInsured();
	private InformationInsured informationInsureds3 = new InformationInsured();
	private InformationAppnt informationAppnt = new InformationAppnt();
	private String insureReq;
	private String dutyReq;
	private String dutyDisReq;
	private String relationFlag1;

	private List<Map> listRelation = new ArrayList<Map>();;// 与被保人关系
	private List<Map> listRelation2 = new ArrayList<Map>();// 与被保人关系
	private List<Map> listCountryCode;// 国家
	private List<Map> opts;// 职业
	private List<Map> areas;// 地区
	private List<Map> sexs;// 性别
	private List<Map> certificateCode;// 职业

	private Map<String, Object> sdinteraction = new HashMap<String, Object>();// 横向接口调用参数
	private List<InformationDuty> showDuty = null;// 订单结果页责任显示集合

	private TradeInformation tradeInformation = new TradeInformation();
	private InsuredCompanyReturnData icr;

	@Resource
	private OrderService orderService;
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
	private InformationInsuredElementsService informationInsuredElementsService;
	@Resource
	private InformationDutyService informationDutyService;

	@Resource
	private MemberDonatedService memberDonatedService;
	@Resource
	private InsuredCompanyReturnDataService tInsuredCompanyReturnDataService;
	@Resource
	private TradeInformationService tradeInformationService;
	@Resource
	private PageLayoutService pagelayoutService;
	@Resource
	private MemberService memberService;

	/**
	 * 直接购买 创建日期 2012-八月-3
	 * 
	 * @param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@InputConfig(resultName = "error")
	public String buyNow() {
		String id = (String) getSession(Member.LOGIN_MEMBER_ID_SESSION_NAME);
		if (StringUtil.isEmpty(id)) {
			addActionError("请登录或注册！");
			return ERROR;
		}
		Member member = memberService.load(id);
		if (member == null) {
			addActionError("请登录或注册！");
			return ERROR;
		} else {
			// 判断会员是否已经获得过赠险
			if (checkDonated(member, productId)) {
//				addActionError("对不起，您已获赠险！");
//				return ERROR;
				return "accessed";
			}
		}
		Map<String, Object> paramter = new HashMap<String, Object>();
		try {
			paramter = orderService.getProductInformation(productId, "Y");// 产品ID
			String[] BaseInformation = new String[6];
			BaseInformation = (String[]) paramter.get("baseInformation");// 产品基础数据
			setSession("baseInformation", BaseInformation);
			productId = BaseInformation[0].toString();// 产品ID
			productName = BaseInformation[1].toString();// 产品名称
			insuranceCompanySn = BaseInformation[2].toString().substring(0, 4);// 保险公司编码
			insuranceCompany = BaseInformation[3].toString();// 保险公司名称
			outRiskCode = BaseInformation[4].toString();// 保险公司产品编码
			insurType = BaseInformation[5].toString();// 内部统计险种中类别
			String[] insurTypes = insurType.split(",");
			insurType = insurTypes[0];
			price = BaseInformation[6].toString();// 基础价格
			insurTypeChild = BaseInformation[7].toString();// 内部统计险种子类别
			productImg = BaseInformation[8].toString();// 产品类别

		} catch (Exception e) {
			addActionError("没有找到该产品，请重新选择！");
			return ERROR;
		}
		try {
			// 产品投保要素
			riskAppFactior = (List<OrderRiskAppFactor>) paramter.get("riskAppFactor");
			if (riskAppFactior.size() > 0) {
				setSession("riskAppFactior", riskAppFactior);
				for (int i = 0; i < riskAppFactior.size(); i++) {
					OrderRiskAppFactor orderRiskAppFactor = riskAppFactior.get(i);
					// 拆分保障期限值，用于页面
					if ("Period".equals(orderRiskAppFactor.getFactorType().toString())) {
						orderRiskAppFactor.getFactorValue();
						List<FEMRiskFactorList> periodList = orderRiskAppFactor.getFactorValue();
						FEMRiskFactorList fEMRiskFactorList = periodList.get(0);
						String[] periods = fEMRiskFactorList.getFactorValue().split("-");
						String periodBe = "";
						String periodAf = "";
						if (periods != null && periods.length == 1) {
							periodBe = periods[0].toString();// 保障期限前段
							periodAf = periods[0].toString();// 保障期限后段

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
					if ("Amnt".equals(orderRiskAppFactor.getFactorType().toString())) {
						FEMRiskFactorList femRiskFacList = orderRiskAppFactor.getFactorValue().get(0);
						amnt = femRiskFacList.getFactorValue(); // 获取保额
					}
				}
			}

			// 产品责任要素
			dutyFactor = (List<OrderDutyFactor>) paramter.get("dutyFactor");
			if (dutyFactor.size() > 0) {
				for (OrderDutyFactor orderDutyFactor : dutyFactor) {
					if (orderDutyFactor.getFdAmntPremList() == null || orderDutyFactor.getFdAmntPremList().get(0) == null) {
						FEMDutyAmntPremList fEMDutyAmntPremList = new FEMDutyAmntPremList();
						List<FEMDutyAmntPremList> fdap = new ArrayList<FEMDutyAmntPremList>();
						fdap.add(fEMDutyAmntPremList);
						orderDutyFactor.setFdAmntPremList(fdap);
					}
				}
				setSession("dutyFactor", dutyFactor);
			}

			this.dataBuild();
			init();
			for (int i = 0; i < listRelation2.size(); i++) {
				Map<String, Object> mapTemp = new HashMap<String, Object>();
				mapTemp = listRelation2.get(i);
				String codeName = mapTemp.get("codeName").toString();
				if ("本人".equals(codeName)) {
					listRelation.add(0, listRelation2.get(i));
				} else {
					listRelation.add(listRelation2.get(i));
				}
			}
			String info = "orderinfo";
			String productHtmlFilePath = "";// 产品详细页路径
			Integer productQuantity = 1;// 数量
			BigDecimal prem = new BigDecimal("0.00"); // 总保额
			BigDecimal PreferentialPrice = new BigDecimal("0.00");// 优惠价格
			order.setOrderStatus(OrderStatus.prepay);// 待支付
			order.setPaymentStatus(PaymentStatus.unpaid);// 未支付
			order.setProductTotalPrice(prem);
			order.setPaidAmount(new BigDecimal("0"));
			order.setTotalAmount(prem);
			order.setProductTotalQuantity(1);
			order.setProductName(productName);
			order.setProductId(productId);
			order.setOutRiskCode(outRiskCode);
			order.setInsuranceCompany(insuranceCompany);
			order.setInsuranceCompanySn(insuranceCompanySn);
			if (getLoginMember() != null) {
				order.setMemberId(getLoginMember().getId());
				loginFlag = "true";
			}
			orderItem.setProductId(productId);
			orderItem.setProductName(productName);
			orderItem.setProductPrice(PreferentialPrice);// 优惠价格
			orderItem.setProductQuantity(productQuantity);
			orderItem.setProductHtmlFilePath(productHtmlFilePath);
			orderItem.setOrder(order);
		} catch (Exception e) {
			logger.error("类DonateAction.buyNow()方法异常{}", e.getMessage());
			addActionError("页面加载失败，请返回重试！");
			return ERROR;
		}
		logger.info("-----------------产品大类别---------------{}", insurType);
		logger.info("-----------------产品小类别---------------{}", insurTypeChild);
		logger.info("-----------------保险公司编码-------------{}", insuranceCompanySn);

		return "bnaccidentinsur";// 跳到订单录入页面
	}

	/**
	 * 判断会员是否已经获赠
	 * 
	 * @param memberId
	 * @return
	 */
	private boolean checkDonated(Member tMember, String outRiskCode) {
		boolean isDonatedFlag = false;
		try {
			List<MemberDonated> tMDList = new ArrayList<MemberDonated>();
			tMDList = memberDonatedService.findByQBs(tMember, outRiskCode);
			if (!tMDList.isEmpty() && tMDList.size() > 0) {
				isDonatedFlag = true;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return isDonatedFlag;
	}

	/**
	 * 填写订单，生成订单预览页
	 * 
	 * @return
	 */
	public String buildOrder() {
		try {
			String id = (String) getSession(Member.LOGIN_MEMBER_ID_SESSION_NAME);
			// 判断该投保人是否已获赠该产品
			if (checkInsured(informationAppnt)) {
//				addActionError("对不起，该被保人已获赠险，不能再次获赠！");
//				return ERROR;
				return "accessed";
			} else {
				// 保存订单信息
				return this.bbxinfosave();
			}
		} catch (Exception e) {
			addActionError("保存订单信息失败，请重试!");
			return ERROR;
		}
	}

	/**
	 * 取消订单
	 * 
	 * 创建日期 2012-八月-3
	 * 
	 * @param
	 * @return
	 */
	@InputConfig(resultName = "error")
	public String orderStatusUpdate() {
		try {
			orderService.delete(orderId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ERROR;
		}
		return "orderRevoke";
	}

	/**
	 * 接口数据组织供前台页面使用
	 * 
	 * @return
	 */
	public void dataBuild() {
		for (OrderRiskAppFactor orderRiskAppFactor : riskAppFactior) {
			factorType = factorType + orderRiskAppFactor.getFactorType() + ",";
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
		}
	}

	/**
	 * 直接购买保存 创建日期 2012-八月-3
	 * 
	 * @param
	 * @return
	 */
	public String bbxinfosave() {
		if (getLoginMember() != null) {
			order.setMemberId(getLoginMember().getId());
		}
		try {
			productTotalPrice = new BigDecimal(price);// 保费 ，接口试算后获得
			if ("temptorysave".equals(orderStatus)) {
				order.setOrderStatus(OrderStatus.temptorysave);
			} else {
				order.setOrderStatus(OrderStatus.temptorysave);
			}
			BigDecimal paymentFee = new BigDecimal("0");
			information = new Information();// 订单投保界面所有属性
			orderItem.setProductId(productId);
			orderItem.setProductName(productName);// 产品名称，详细页或接口获得
			orderItem.setProductPrice(productTotalPrice);
			orderItem.setProductQuantity(1);
			orderItem.setProductHtmlFilePath("");// 产品详细页URL,由产品详细页获得
			orderItemSet.add(orderItem);

			order.setAmntPrice(new BigDecimal("200000.00"));// 保额amnt
			order.setPaymentStatus(PaymentStatus.unpaid);
			order.setProductTotalPrice(productTotalPrice);
			order.setPaidAmount(new BigDecimal("0"));
			order.setPaymentFee(paymentFee);
			order.setTotalAmount(productTotalPrice);
			order.setProductTotalQuantity(0);
			order.setPoint(0);
			order.setProductName(productName);
			order.setProductId(productId);
			order.setInsuranceCompany(insuranceCompany);
			order.setInsuranceCompanySn(insuranceCompanySn);
			order.setOrderItemSet(orderItemSet);
			order.setOrderValid(true);
			order.setOutRiskCode(outRiskCode);
			order.setSubRiskTypeCode(insurTypeChild);
			order.seteRiskType(insurType);
			orderSn = PubFun.GetOrderSn();
			order.setOrderSn(orderSn);
			order.setOrderStatus(OrderStatus.prepay);// 已完成
			order.setPaymentStatus(PaymentStatus.paid);// 已支付
			order.setInsureStatus(InsureStatus.uninsured);// 已支付
			orderService.save(order);

			orderItem.setOrder(order);
			orderId = order.getId();
			informationSet.add(information1);
			orderItem.setInformationSet(informationSet);
			orderItemService.save(orderItem);
			information1.setOrderItem(orderItem);// information保存orderItem
			information1.setEffective(effective); // 保单起始日期
			information1.setFail(fail); // 保单终止日期
			information1.setProtectionPeriodTy(protectionPeriodTy);// 保障期间类型
			information1.setProtectionPeriod(protectionPeriodLast);// 保障期间
			information1.setInformationInsuredSet(informationInsuredSet);
			informationService.save(information1);

			this.informationAppntSave(information1);// 投保人信息存储
			this.informationInsuredSave(information1);// 被保人信息存储
			orderItem.setValid(true);
			orderItemService.update(orderItem);
			order = orderService.load(orderId);
			orderService.update(order);
			orderItem = orderItemService.getByOrP(productId, orderId);// p:productId;
			information = informationService.getByProduct(productId, orderId);
			showDuty = new ArrayList<InformationDuty>(informationDutyElementsSet);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionError("订单保存失败，请重试！");
			return ERROR;
		}
		return "result";
	}

	/**
	 * 直接购买更新 创建日期 2012-八月-3
	 * 
	 * @param
	 * @return
	 */
	public String bbxinfoUpdate() {
		if (orderId != null && !"".equals(orderId)) {
			Order od = orderService.load(orderId);
			orderService.delete(od);
		}
		String resultString = bbxinfosave();
		return resultString;
	}

	private void init(){
		JdbcTemplateData jtd = new JdbcTemplateData();
		try {
			String sqlRelation = "select codevalue,codename from dictionary where codetype=? and insuranceCode=?  order by id asc";
			String[] sqlRelationtemp = {"Relationship","1061"};
			String sqlCertificateCode = "select codevalue,codename from dictionary where codetype=? and insuranceCode=? order by id asc";
			String[] sqlCertificateCodetemp = {"certificate","1061"};
			String sqlOpts = "select id,name from occupation where insuranceCompany=?  and parent_id is null order by id asc";
			String[] sqlOptstemp = {"1061"};
			String sqlSexCode = "select codevalue,codename from dictionary where codetype=? and insuranceCode=? order by id asc";
			String[] sqlSexCodetemp = {"Sex","1061"};
			String sqlCountryCode = "select codevalue,codename,codeenname from dictionary where codetype=? and insuranceCode=? order by id asc";
			String[] sqlCountryCodetemp = {"CountryCode","1061"};
			String sqlAreaOpts = "select id,name from area where insuranceCompany=? and parent_id = '' order by id asc";
			String[] sqlAreaOptstemp = {"1061"};
			listRelation2 = jtd.obtainData(sqlRelation,sqlRelationtemp);// 投被保人关系     
			listCountryCode = jtd.obtainData(sqlCountryCode,sqlCountryCodetemp);// 目的地  
			opts = jtd.obtainData(sqlOpts,sqlOptstemp);// 职业
			areas = jtd.obtainData(sqlAreaOpts,sqlAreaOptstemp);// 地区   
			sexs = jtd.obtainData(sqlSexCode,sqlSexCodetemp);// 性别        
			certificateCode = jtd.obtainData(sqlCertificateCode,sqlCertificateCodetemp);// 证件类型     
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} 
	}
	/**
	 * 重新填写
	 * 
	 * @param
	 * @return
	 */
	@InputConfig(resultName = "error")
	public String buyNowUpdate() {
		// 重新填写，删除暂存订单数据
		order = orderService.load(orderId);
		insuranceCompanySn = order.getInsuranceCompanySn();
		outRiskCode = order.getOutRiskCode();
		try {
			init();

			orderItemSet = order.getOrderItemSet();
			for (OrderItem orderItems : orderItemSet) {
				orderItem = orderItems;
			}
			informationSet = orderItem.getInformationSet();
			for (Information orderItemss : informationSet) {
				information = orderItemss;
			}

			informationAppnt = information.getInformationAppnt();
			Map<String, Object> paramter = new HashMap<String, Object>();
			// 获取产品id\sn、类别、险种、投保要素、价格等
			paramter = orderService.getProductInformation(productId, "Y");
			String[] BaseInformation = new String[6];
			BaseInformation = (String[]) paramter.get("baseInformation");// 产品基础数据
			setSession("baseInformation", BaseInformation);
			// 产品投保要素
			riskAppFactior = (List<OrderRiskAppFactor>) paramter.get("riskAppFactor");
			if (riskAppFactior.size() > 0) {
				setSession("riskAppFactior", riskAppFactior);
				for (int i = 0; i < riskAppFactior.size(); i++) {
					OrderRiskAppFactor orderRiskAppFactor = riskAppFactior.get(i);
					// 拆分保障期限值，用于页面
					if ("Period".equals(orderRiskAppFactor.getFactorType().toString())) {
						orderRiskAppFactor.getFactorValue();
						List<FEMRiskFactorList> periodList = orderRiskAppFactor.getFactorValue();
						FEMRiskFactorList fEMRiskFactorList = periodList.get(0);
						String[] periods = fEMRiskFactorList.getFactorValue().split("-");
						String periodBe = "";
						String periodAf = "";
						if (periods != null && periods.length == 1) {
							periodBe = periods[0].toString();// 保障期限前段
							periodAf = periods[0].toString();// 保障期限后段

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
//					if ("TextAge".equals(orderRiskAppFactor.getFactorType().toString())) {
//						textAgeFlag = "true";
//					}
				}
			}
			// 产品责任要素
			dutyFactor = (List<OrderDutyFactor>) paramter.get("dutyFactor");
			if (dutyFactor.size() > 0) {
				setSession("dutyFactor", dutyFactor);
			}
			this.dataBuild();
			productId = order.getProductId();
			productName = order.getProductName();
			insuranceCompany = order.getInsuranceCompany();
			insurType = BaseInformation[5].toString();
			String[] insurTypes = insurType.split(",");
			insurType = insurTypes[0];
			insurTypeChild = BaseInformation[7].toString();// 产品小类别
			productImg = BaseInformation[8].toString();// 产品类别
			String[] insurTypeChilds = insurTypeChild.split(",");
			insurTypeChild = insurTypeChilds[0];
			price = BaseInformation[6].toString();// 基础价格
			showDuty = new ArrayList<InformationDuty>(informationDutyElementsSet);
			if (getLoginMember() != null) {
				order.setMemberId(getLoginMember().getId());
				loginFlag = "true";
			}
			request.setAttribute("status", "update");
		} catch (Exception e) {
			addActionError("出错了，请返回重试！");
			return ERROR;
		}
		return "bnaccidentinsur";
	}

	/**
	 * 保存投保人信息
	 * 
	 * @param informations
	 */
	public void informationAppntSave(Information informations) {
		informationAppnt.setInformation(informations);
		informationAppntService.save(informationAppnt);
	}

	/**
	 * 被保人信息保存
	 * 
	 * @param informations
	 */
	public void informationInsuredSave(Information informations) {
		Set<InformationDuty> informationDutyElementsAallSet = new HashSet<InformationDuty>();
		informationDutyElementsAallSet = informations.getInformationDutyElementsSet();
		if (informationDutyElementsAallSet != null) {
			for (InformationDuty informationDutyElementsOne : informationDutyElementsAallSet) {
				informationDutyService.delete(informationDutyElementsOne);
			}
		}
		informationInsuredDutySave(informations);
		InformationRiskType informationRiskTypes = new InformationRiskType();
		informationRiskTypes.setId("200001");// 险种，由接口获得
		informationRiskTypeService.save(informationRiskTypes);
		informationInsureds1.getRecognizeeAppntRelation();
		relationFlag1 = "true";
		if ("true".equals(relationFlag1)) {
			informationInsureds1.setRecognizeeName(informationAppnt.getApplicantName());
			informationInsureds1.setRecognizeeIdentityType(informationAppnt.getApplicantIdentityType());
			informationInsureds1.setRecognizeeIdentityId(informationAppnt.getApplicantIdentityId());
			informationInsureds1.setRecognizeeSex(informationAppnt.getApplicantSex());
			informationInsureds1.setRecognizeeMail(informationAppnt.getApplicantMail());
			informationInsureds1.setRecognizeeArea1(informationAppnt.getApplicantArea1());
			informationInsureds1.setRecognizeeArea2(informationAppnt.getApplicantArea2());
			informationInsureds1.setRecognizeeBirthday(informationAppnt.getApplicantBirthday());
			informationInsureds1.setRecognizeeMobile(informationAppnt.getApplicantMobile());
			informationInsureds1.setRecognizeeIdentityTypeName(informationAppnt.getApplicantIdentityTypeName());
			informationInsureds1.setRecognizeeSexName(informationAppnt.getApplicantSexName());
		}
		informationInsureds1.setInformationInsuredElementsSet(informationInsuredElementsSet);
		informationInsureds1.setInformation(informations);
		informationInsuredSet.add(informationInsureds1);
		informationInsuredService.save(informationInsureds1);
		informationInsuredElementsSave(informationInsureds1);
		showInsureds.add(informationInsureds1);
	}

	/**
	 * 投保要素保存
	 * 
	 * @param informationInsured
	 */
	public void informationInsuredElementsSave(InformationInsured informationInsured) {
		riskAppFactior = (List<OrderRiskAppFactor>) getSession("riskAppFactior");
		dutyFactor = (List<OrderDutyFactor>) getSession("dutyFactor");
		List<OrderDutyFactor> dutyFactorLast = new ArrayList<OrderDutyFactor>();
		JSONObject insureJsonJsonArray = JSONObject.fromObject(insureReq);
		if (riskAppFactior != null) {
			for (int i = 0; i < riskAppFactior.size(); i++) {
				String appFactiorType = riskAppFactior.get(i).getFactorType().toString();
				if (!"TextAge".equals(appFactiorType)) {
					String factorValueTemp = "";
					if (!insureJsonJsonArray.isNullObject() && !insureJsonJsonArray.isEmpty() && !riskAppFactior.isEmpty() && riskAppFactior.size() > 0) {
						factorValueTemp = insureJsonJsonArray.get(riskAppFactior.get(i).getFactorType()).toString();
					}
					InformationInsuredElements informationInsuredElements = new InformationInsuredElements();
					informationInsuredElements.setElementsType(appFactiorType);
					informationInsuredElements.setElementsValue(factorValueTemp);
					informationInsuredElements.setInformationInsured(informationInsured);
					informationInsuredElementsSet.add(informationInsuredElements);
					informationInsuredElementsService.save(informationInsuredElements);
				}
			}
		}
	}

	/**
	 * 投保责任保存
	 * 
	 * @param inf
	 */
	public void informationInsuredDutySave(Information inf) {
		try {
			riskAppFactior = (List<OrderRiskAppFactor>) getSession("riskAppFactior");
			dutyFactor = (List<OrderDutyFactor>) getSession("dutyFactor");
			JSONObject dutyJsonArray = null;
			if (StringUtil.isNotEmpty(dutyReq)) {
				dutyJsonArray = JSONObject.fromObject(dutyReq);
			}
			JSONObject showDutyJsonArray = null;
			if (StringUtil.isNotEmpty(dutyDisReq)) {
				showDutyJsonArray = JSONObject.fromObject(dutyDisReq);
			}

			if (dutyFactor != null) {
				for (int i = 0; i < dutyFactor.size(); i++) {
					Object factorValueTemp = null;
					Object showDutyValue = null;
					if (!dutyJsonArray.isNullObject() && !dutyJsonArray.isEmpty() && !dutyFactor.isEmpty() && dutyFactor.size() > 0) {
						factorValueTemp = dutyJsonArray.get(dutyFactor.get(i).getDutyCode());
					}
					if (!showDutyJsonArray.isNullObject() && !showDutyJsonArray.isEmpty() && !dutyFactor.isEmpty() && dutyFactor.size() > 0) {
						showDutyValue = showDutyJsonArray.get(dutyFactor.get(i).getDutyCode());
					}

					InformationDuty informationDuty = new InformationDuty();
					informationDuty.setDutySn(dutyFactor.get(i).getDutyCode());
					informationDuty.setDutyName(dutyFactor.get(i).getDudtyFactorName());
					informationDuty.setAmnt((factorValueTemp == null) ? "" : factorValueTemp.toString());
					informationDuty.setCoverage(dutyFactor.get(i).getDefine());
					informationDuty.setOrderId(orderId);
					informationDuty.setShowAmnt((showDutyValue == null) ? "" : showDutyValue.toString());
					informationDuty.setInformation(inf);
					informationDutyElementsSet.add(informationDuty);
					informationDutyService.save(informationDuty);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 判断被保人是否已经获赠
	 * 
	 * @param infoInsured
	 * @return
	 */
	public boolean checkInsured(InformationAppnt infoAppnt) {
		return memberDonatedService.isInsuredByOrder(productId, infoAppnt);
	}

	/**
	 * 保存交易信息
	 * 
	 * @param orderSn
	 * @return
	 */
	private boolean saveTradeInfo(String orderSn) {
		try {
			tradeInformation.setOrdID(orderSn);
			// java.text.SimpleDateFormat format = new
			// java.text.SimpleDateFormat("yyyyMMddHHmmss");
			// tradeInformation.setTradeSeriNO(format.format(new
			// java.util.Date()));
			java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			tradeInformation.setReceiveDate(formatter.format(new java.util.Date()));
			tradeInformation.setReturnSign("1");
			tradeInformation.setTradeResult("0");
			tradeInformation.setPayStatus("1");// 表示返回
			tradeInformationService.save(tradeInformation);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * 保存会员赠送信息
	 * @param member 
	 */
	private void saveDonatedInfo(Member mem) {
		try {
			MemberDonated memDonated = new MemberDonated();
			memDonated.setOrderId(orderId);
			memDonated.setProductId(productId);
			memDonated.setMemberId(mem.getId());
			memDonated.setIdentityType(informationAppnt.getApplicantIdentityType());
			memDonated.setIdentityId(informationAppnt.getApplicantIdentityId());
			memDonated.setMobile(mem.getMobileNO());
			memDonated.setEmail(mem.getEmail());
			memberDonatedService.save(memDonated);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 确认并提交
	 * 
	 * @return
	 */
	public String submit() {
		try {
			order = orderService.getOrderByOrderSn(orderSn);
			if (orderSn == null || order == null) {
				addActionError("订单号或订单为空!");
				return ERROR;
			}
			// 调用经代通
			if (saveTradeInfo(orderSn)) {
				InsureTransferNew tInsureTransfer = new InsureTransferNew();
				tInsureTransfer.callInsTransInterface(order.getInsuranceCompanySn(), order.getOrderSn(),null);
				icr = tInsuredCompanyReturnDataService.getInsuredCompanyReturnDataByOrderSn(orderSn);// 取得经代通返回的结果
			}
			if (icr != null) {
				if (StringUtil.isNotEmpty(informationAppnt.getApplicantMail())) {
					String id = (String) getSession(Member.LOGIN_MEMBER_ID_SESSION_NAME);
					Member member = memberService.get(id);
					if ("0".equals(icr.getAppStatus())) {// 表示投保失败
						order.setInsureStatus(InsureStatus.uninsured);
						order.setPolicyNumber(icr.getPolicyNo());
						orderService.update(order);
						// 调用发送邮件接口
						// Map<String, Object> map = new HashMap<String,
						// Object>();
						// map.put("Member", member);
						// map.put("OrderSn", order.getOrderSn());
						// tActionUtil.deal("wj00031", map, getRequest());

					} else if ("1".equals(icr.getAppStatus())) {// 表示投保成功
						this.saveDonatedInfo(member);
						order.setInsureStatus(InsureStatus.insured);// 已承保
						order.setOrderStatus(OrderStatus.paid);// 已完成
						order.setPaymentStatus(PaymentStatus.paid);// 已支付
						order.setPolicyNumber(icr.getPolicyNo());
						orderService.update(order);
						Map<String, Object> map = new HashMap<String, Object>();
						member.setEmail(informationAppnt.getApplicantMail());
						map.put("Member", member);
						map.put("OrderSn", order.getOrderSn());
						map.put("PolicyNo", icr.getPolicyNo());
						map.put("MemberName", informationAppnt.getApplicantName());
						if (effective != null) {
							map.put("Effective", calSDate(new java.text.SimpleDateFormat("yyyy-MM-dd").format(effective), 1, "D"));
						} else {
							logger.error("类DonateAction中submit()方法异常：发送邮件异常！");
						}
						// if (!"".equals(member.getUsername())) {
						// map.put("MemberName", member.getUsername());
						// } else if (!"".equals(member.getRealName())) {
						// map.put("MemberName", member.getRealName());
						// } else {
						// map.put("MemberName", "");
						// }
						// map.put("paidData",
						// tradeInformation.getReceiveDate());// 支付时间
						ActionUtil.dealAction("wj00019", map, getRequest());
					}
				}
				return "success";
			}
			return null;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	public static String calSDate(String tbaseDate, int interval, String unit) {
		Date returnDate = null;
		String ReturnDate = null;

		Date baseDate = new Date();
		try {
			baseDate = StringToDate(tbaseDate);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}

		GregorianCalendar mCalendar = new GregorianCalendar();
		mCalendar.setTime(baseDate);
		if (unit.equals("Y"))
			mCalendar.add(Calendar.YEAR, interval);
		if (unit.equals("M"))
			mCalendar.add(Calendar.MONTH, interval);
		if (unit.equals("D"))
			mCalendar.add(Calendar.DATE, interval);

		returnDate = mCalendar.getTime();

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		ReturnDate = df.format(returnDate);

		return ReturnDate;
	}

	public static Date StringToDate(String str) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(str);
		return date;
	}

	/**
	 * ajax调用函数
	 * 
	 * @return
	 */
	public String ajaxCounter() {
		baseInformations = (String[]) getSession("baseInformation");
		riskAppFactior = (List<OrderRiskAppFactor>) getSession("riskAppFactior");
		dutyFactor = (List<OrderDutyFactor>) getSession("dutyFactor");
		List<OrderDutyFactor> dutyFactorLast = new ArrayList<OrderDutyFactor>();
		JSONObject insureJsonJsonArray = JSONObject.fromObject(insureJson);
		for (int i = 0; i < riskAppFactior.size(); i++) {
			String factorValueTemp = insureJsonJsonArray.get(riskAppFactior.get(i).getFactorType()).toString();
			List<FEMRiskFactorList> factorValue = new ArrayList<FEMRiskFactorList>();
			FEMRiskFactorList riskFactor = new FEMRiskFactorList();
			riskFactor.setFactorValue(factorValueTemp);
			factorValue.add(riskFactor);
			riskAppFactior.get(i).setFactorValue(factorValue);
		}

		JSONObject dutyJsonJsonArray = JSONObject.fromObject(dutyJson);

		for (OrderDutyFactor orderDutyFactor : dutyFactor) {
			String dutyValueTemp = dutyJsonJsonArray.get(orderDutyFactor.getDutyCode()).toString();
			if (orderDutyFactor.getFdAmntPremList() != null) {
				orderDutyFactor.getFdAmntPremList().clear();
			}
			FEMDutyAmntPremList fEMDutyAmntPremList = new FEMDutyAmntPremList();
			List<FEMDutyAmntPremList> fdAmntPremList = new ArrayList();
			if ("nvalue".equals(dutyValueTemp)) {
			} else {
				fEMDutyAmntPremList.setAmnt(dutyValueTemp);
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

		Map<String, Object> mapPrem = orderService.getProductPrem(paramter);
		double totlePrem = (Double) mapPrem.get("totlePrem");
		double feeRate = (Double) mapPrem.get("feeRate");
		String retTotlePrem = String.valueOf(totlePrem);
		return ajax(retTotlePrem, "text/html");
	}

	/**
	 * 
	 * 获得职业子集
	 */
	public String getChildernOPT() {
		JdbcTemplateData jtd = new JdbcTemplateData();
		String id = getParameter("id");
		String sqlOpts = "select id,name from occupation where 1=1  and parent_id = ? order by id asc";
		String[] sqlOptstemp = {id};
		List<Map> childerns;
		try {
			childerns = jtd.obtainData(sqlOpts,sqlOptstemp);
			JSONArray jsonArray = JSONArray.fromObject(childerns);
			String jsonString = jsonArray.toString();
			return ajaxJson(jsonString);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return ajaxJson("");
	}

	/**
	 * 获得地区子集
	 * 
	 * @return
	 */
	public String getChildernArea() {
		JdbcTemplateData jtd = new JdbcTemplateData();
		String id = getParameter("id");
		String sqlArea = "select id,name from area where 1=1 and parent_id = ? order by id asc";
		String[] sqlAreatemp = {id};
		try {
			List<Map> areas = jtd.obtainData(sqlArea,sqlAreatemp);
			JSONArray jsonArray = JSONArray.fromObject(areas);
			String jsonString = jsonArray.toString();
			return ajaxJson(jsonString);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return ajaxJson("");
	}

	private String loginFlag;

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getLastpage() {
		return lastpage;
	}

	public void setLastpage(int lastpage) {
		this.lastpage = lastpage;
	}

	public Integer getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(Integer totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public BigDecimal getProductTotalPrice() {
		return productTotalPrice;
	}

	public void setProductTotalPrice(BigDecimal productTotalPrice) {
		this.productTotalPrice = productTotalPrice;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Information getInformation() {
		return information;
	}

	public void setInformation(Information information) {
		this.information = information;
	}

	public Information getInformation1() {
		return information1;
	}

	public void setInformation1(Information information1) {
		this.information1 = information1;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public OrderLog getOrderLog() {
		return orderLog;
	}

	public void setOrderLog(OrderLog orderLog) {
		this.orderLog = orderLog;
	}

	public OrderItem getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(OrderItem orderItem) {
		this.orderItem = orderItem;
	}

	public Set<OrderItem> getOrderItemSet() {
		return orderItemSet;
	}

	public void setOrderItemSet(Set<OrderItem> orderItemSet) {
		this.orderItemSet = orderItemSet;
	}

	public Set<Information> getInformationSet() {
		return informationSet;
	}

	public void setInformationSet(Set<Information> informationSet) {
		this.informationSet = informationSet;
	}

	public Set<InformationInsured> getInformationInsuredSet() {
		return informationInsuredSet;
	}

	public void setInformationInsuredSet(Set<InformationInsured> informationInsuredSet) {
		this.informationInsuredSet = informationInsuredSet;
	}

	public Set<InformationInsuredElements> getInformationInsuredElementsSet() {
		return informationInsuredElementsSet;
	}

	public void setInformationInsuredElementsSet(Set<InformationInsuredElements> informationInsuredElementsSet) {
		this.informationInsuredElementsSet = informationInsuredElementsSet;
	}

	public Set<InformationDuty> getInformationDutyElementsSet() {
		return informationDutyElementsSet;
	}

	public void setInformationDutyElementsSet(Set<InformationDuty> informationDutyElementsSet) {
		this.informationDutyElementsSet = informationDutyElementsSet;
	}

	public List<InformationInsured> getShowInsureds() {
		return showInsureds;
	}

	public void setShowInsureds(List<InformationInsured> showInsureds) {
		this.showInsureds = showInsureds;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public String getApplicantIdentityType() {
		return applicantIdentityType;
	}

	public void setApplicantIdentityType(String applicantIdentityType) {
		this.applicantIdentityType = applicantIdentityType;
	}

	public String getApplicantIdentityId() {
		return applicantIdentityId;
	}

	public void setApplicantIdentityId(String applicantIdentityId) {
		this.applicantIdentityId = applicantIdentityId;
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

	public String getApplicantTel() {
		return applicantTel;
	}

	public void setApplicantTel(String applicantTel) {
		this.applicantTel = applicantTel;
	}

	public String getApplicantMail() {
		return applicantMail;
	}

	public void setApplicantMail(String applicantMail) {
		this.applicantMail = applicantMail;
	}

	public String getApplicantArea() {
		return applicantArea;
	}

	public void setApplicantArea(String applicantArea) {
		this.applicantArea = applicantArea;
	}

	public String getApplicantMobile() {
		return applicantMobile;
	}

	public void setApplicantMobile(String applicantMobile) {
		this.applicantMobile = applicantMobile;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
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

	public String getRecognizeeName() {
		return recognizeeName;
	}

	public void setRecognizeeName(String recognizeeName) {
		this.recognizeeName = recognizeeName;
	}

	public String getRecognizeeIdentityType() {
		return recognizeeIdentityType;
	}

	public void setRecognizeeIdentityType(String recognizeeIdentityType) {
		this.recognizeeIdentityType = recognizeeIdentityType;
	}

	public String getRecognizeeIdentityId() {
		return recognizeeIdentityId;
	}

	public void setRecognizeeIdentityId(String recognizeeIdentityId) {
		this.recognizeeIdentityId = recognizeeIdentityId;
	}

	public String getRecognizeeSex() {
		return recognizeeSex;
	}

	public void setRecognizeeSex(String recognizeeSex) {
		this.recognizeeSex = recognizeeSex;
	}

	public String getRecognizeeBirthday() {
		return recognizeeBirthday;
	}

	public void setRecognizeeBirthday(String recognizeeBirthday) {
		this.recognizeeBirthday = recognizeeBirthday;
	}

	public String getRecognizeeTel() {
		return recognizeeTel;
	}

	public void setRecognizeeTel(String recognizeeTel) {
		this.recognizeeTel = recognizeeTel;
	}

	public String getRecognizeeMail() {
		return recognizeeMail;
	}

	public void setRecognizeeMail(String recognizeeMail) {
		this.recognizeeMail = recognizeeMail;
	}

	public String getRecognizeeZipCode() {
		return recognizeeZipCode;
	}

	public void setRecognizeeZipCode(String recognizeeZipCode) {
		this.recognizeeZipCode = recognizeeZipCode;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getOccupation1() {
		return occupation1;
	}

	public void setOccupation1(String occupation1) {
		this.occupation1 = occupation1;
	}

	public String getOccupation2() {
		return occupation2;
	}

	public void setOccupation2(String occupation2) {
		this.occupation2 = occupation2;
	}

	public String getOccupation3() {
		return occupation3;
	}

	public void setOccupation3(String occupation3) {
		this.occupation3 = occupation3;
	}

	public String getRecognizeeArea() {
		return recognizeeArea;
	}

	public void setRecognizeeArea(String recognizeeArea) {
		this.recognizeeArea = recognizeeArea;
	}

	public String getInsuranceCompany() {
		return insuranceCompany;
	}

	public void setInsuranceCompany(String insuranceCompany) {
		this.insuranceCompany = insuranceCompany;
	}

	public String getOutRiskCode() {
		return outRiskCode;
	}

	public void setOutRiskCode(String outRiskCode) {
		this.outRiskCode = outRiskCode;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductImg() {
		return productImg;
	}

	public void setProductImg(String productImg) {
		this.productImg = productImg;
	}

	public String getRiskType() {
		return riskType;
	}

	public void setRiskType(String riskType) {
		this.riskType = riskType;
	}

	public String getPrem() {
		return prem;
	}

	public void setPrem(String prem) {
		this.prem = prem;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getInsurType() {
		return insurType;
	}

	public void setInsurType(String insurType) {
		this.insurType = insurType;
	}

	public String getInsurTypeChild() {
		return insurTypeChild;
	}

	public void setInsurTypeChild(String insurTypeChild) {
		this.insurTypeChild = insurTypeChild;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public String getLdate() {
		return ldate;
	}

	public void setLdate(String ldate) {
		this.ldate = ldate;
	}

	public String getHdate() {
		return hdate;
	}

	public void setHdate(String hdate) {
		this.hdate = hdate;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getApplicant() {
		return applicant;
	}

	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}

	public String getAssociateBefore() {
		return associateBefore;
	}

	public void setAssociateBefore(String associateBefore) {
		this.associateBefore = associateBefore;
	}

	public String getAssociateAfter() {
		return associateAfter;
	}

	public void setAssociateAfter(String associateAfter) {
		this.associateAfter = associateAfter;
	}

	public String getAppDate() {
		return appDate;
	}

	public void setAppDate(String appDate) {
		this.appDate = appDate;
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

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getMulPeople() {
		return mulPeople;
	}

	public void setMulPeople(String mulPeople) {
		this.mulPeople = mulPeople;
	}

	public String getNumAge() {
		return numAge;
	}

	public void setNumAge(String numAge) {
		this.numAge = numAge;
	}

	public String getOccup() {
		return occup;
	}

	public void setOccup(String occup) {
		this.occup = occup;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getSectionAge() {
		return sectionAge;
	}

	public void setSectionAge(String sectionAge) {
		this.sectionAge = sectionAge;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getTextAge() {
		return textAge;
	}

	public void setTextAge(String textAge) {
		this.textAge = textAge;
	}

	public String getAmnt() {
		return amnt;
	}

	public void setAmnt(String amnt) {
		this.amnt = amnt;
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
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

	public List<FEMRiskFactorList> getPeriodList() {
		return periodList;
	}

	public void setPeriodList(List<FEMRiskFactorList> periodList) {
		this.periodList = periodList;
	}

	public String getFactorType() {
		return factorType;
	}

	public void setFactorType(String factorType) {
		this.factorType = factorType;
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

	public Map<String, String> getAssociate() {
		return associate;
	}

	public void setAssociate(Map<String, String> associate) {
		this.associate = associate;
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

	public String[] getBaseInformations() {
		return baseInformations;
	}

	public void setBaseInformations(String[] baseInformations) {
		this.baseInformations = baseInformations;
	}

	public InformationInsured getInformationInsureds() {
		return informationInsureds;
	}

	public void setInformationInsureds(InformationInsured informationInsureds) {
		this.informationInsureds = informationInsureds;
	}

	public InformationInsured getInformationInsureds1() {
		return informationInsureds1;
	}

	public void setInformationInsureds1(InformationInsured informationInsureds1) {
		this.informationInsureds1 = informationInsureds1;
	}

	public InformationInsured getInformationInsureds2() {
		return informationInsureds2;
	}

	public void setInformationInsureds2(InformationInsured informationInsureds2) {
		this.informationInsureds2 = informationInsureds2;
	}

	public InformationInsured getInformationInsureds3() {
		return informationInsureds3;
	}

	public void setInformationInsureds3(InformationInsured informationInsureds3) {
		this.informationInsureds3 = informationInsureds3;
	}

	public InformationAppnt getInformationAppnt() {
		return informationAppnt;
	}

	public void setInformationAppnt(InformationAppnt informationAppnt) {
		this.informationAppnt = informationAppnt;
	}

	public String getInsureReq() {
		return insureReq;
	}

	public void setInsureReq(String insureReq) {
		this.insureReq = insureReq;
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

	public String getRelationFlag1() {
		return relationFlag1;
	}

	public void setRelationFlag1(String relationFlag1) {
		this.relationFlag1 = relationFlag1;
	}

	public List<Map> getListRelation() {
		return listRelation;
	}

	public void setListRelation(List<Map> listRelation) {
		this.listRelation = listRelation;
	}

	public List<Map> getListRelation2() {
		return listRelation2;
	}

	public void setListRelation2(List<Map> listRelation2) {
		this.listRelation2 = listRelation2;
	}

	public List<Map> getListCountryCode() {
		return listCountryCode;
	}

	public void setListCountryCode(List<Map> listCountryCode) {
		this.listCountryCode = listCountryCode;
	}

	public List<Map> getOpts() {
		return opts;
	}

	public void setOpts(List<Map> opts) {
		this.opts = opts;
	}

	public List<Map> getAreas() {
		return areas;
	}

	public void setAreas(List<Map> areas) {
		this.areas = areas;
	}

	public List<Map> getSexs() {
		return sexs;
	}

	public void setSexs(List<Map> sexs) {
		this.sexs = sexs;
	}

	public List<Map> getCertificateCode() {
		return certificateCode;
	}

	public void setCertificateCode(List<Map> certificateCode) {
		this.certificateCode = certificateCode;
	}

	public Map<String, Object> getSdinteraction() {
		return sdinteraction;
	}

	public void setSdinteraction(Map<String, Object> sdinteraction) {
		this.sdinteraction = sdinteraction;
	}

	public List<InformationDuty> getShowDuty() {
		return showDuty;
	}

	public void setShowDuty(List<InformationDuty> showDuty) {
		this.showDuty = showDuty;
	}

	public TradeInformation getTradeInformation() {
		return tradeInformation;
	}

	public void setTradeInformation(TradeInformation tradeInformation) {
		this.tradeInformation = tradeInformation;
	}

	public String getLoginFlag() {
		return loginFlag;
	}

	public void setLoginFlag(String loginFlag) {
		this.loginFlag = loginFlag;
	}

	public String getInsuranceCompanySn() {
		return insuranceCompanySn;
	}

	public void setInsuranceCompanySn(String insuranceCompanySn) {
		this.insuranceCompanySn = insuranceCompanySn;
	}

	public InsuredCompanyReturnData getIcr() {
		return icr;
	}

	public void setIcr(InsuredCompanyReturnData icr) {
		this.icr = icr;
	}

	public static void main(String[] args) {
		DonateAction tda = new DonateAction();
		tda.calSDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date("2012-09-18")), 1, "D");
	}
}
