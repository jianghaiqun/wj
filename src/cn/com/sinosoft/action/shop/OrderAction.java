package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.action.shop.uw.UWCheckInterface;
import cn.com.sinosoft.bean.SystemConfig.PointType;
import cn.com.sinosoft.bean.SystemConfig.StoreFreezeTime;
import cn.com.sinosoft.entity.Area;
import cn.com.sinosoft.entity.CartItem;
import cn.com.sinosoft.entity.DeliveryType;
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
import cn.com.sinosoft.entity.Order;
import cn.com.sinosoft.entity.Order.OrderStatus;
import cn.com.sinosoft.entity.Order.PaymentStatus;
import cn.com.sinosoft.entity.OrderDutyFactor;
import cn.com.sinosoft.entity.OrderItem;
import cn.com.sinosoft.entity.OrderLog;
import cn.com.sinosoft.entity.OrderLog.OrderLogType;
import cn.com.sinosoft.entity.OrderProduct;
import cn.com.sinosoft.entity.OrderRiskAppFactor;
import cn.com.sinosoft.entity.PayBase;
import cn.com.sinosoft.entity.PaymentConfig;
import cn.com.sinosoft.entity.Product;
import cn.com.sinosoft.entity.ProductInsAttribute;
import cn.com.sinosoft.entity.ProductInsType;
import cn.com.sinosoft.entity.TradeInformation;
import cn.com.sinosoft.service.AreaService;
import cn.com.sinosoft.service.CartItemService;
import cn.com.sinosoft.service.DeliveryTypeService;
import cn.com.sinosoft.service.DictionaryService;
import cn.com.sinosoft.service.HealthyInfoService;
import cn.com.sinosoft.service.HtmlService;
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
import cn.com.sinosoft.service.OrderLogService;
import cn.com.sinosoft.service.OrderProductService;
import cn.com.sinosoft.service.OrderService;
import cn.com.sinosoft.service.PageLayoutService;
import cn.com.sinosoft.service.PayBaseService;
import cn.com.sinosoft.service.PaymentConfigService;
import cn.com.sinosoft.service.ProductInsAttributeService;
import cn.com.sinosoft.service.ProductService;
import cn.com.sinosoft.service.TradeInformationService;
import cn.com.sinosoft.util.JdbcTemplateData;
import cn.com.sinosoft.util.SystemConfigUtil;
import com.opensymphony.oscache.base.Cache;
import com.opensymphony.oscache.web.ServletCacheAdministrator;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.webservice.ProductWebservice;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FEMDutyAmntPremList;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FEMRiskFactorList;
import com.sinosoft.webservice.productPrem.ProductPremServiceStub.CalProductPrem;
import com.sinosoft.webservice.productPrem.ProductPremServiceStub.ProductPremResponse;
import com.wangjin.infoseeker.OrderSeeker;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.hibernate.Hibernate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * 前台Action类 - 订单处理 ============================================================================
 * 
 * KEY:SINOSOFT66590FE3DF97C81824D62C168A48301D ============================================================================
 */

@ParentPackage("shop")
// @InterceptorRefs( {
// @InterceptorRef(value = "token", params = {
// "excludeMethods",
// "info,list,view,bbxinfo,bbxresult,infosave,bbxinfoedit,result,errorresult,buyNow,accinsur,propertyinsur,tarvelinsur"
// })
// // @InterceptorRef(value = "memberStack") })
public class OrderAction extends BaseShopAction {

	private static final long serialVersionUID = 2553137844831167917L;
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
	private String orderItemId;
	private Information information = new Information();
	private Information information1 = new Information();
	private Information information2 = new Information();
	private Information information3 = new Information();
	private Order order = new Order();// 订单
	private OrderLog orderLog = new OrderLog();// 订单日志
	private Set<CartItem> cartItemSet = new HashSet<CartItem>();
	private OrderItem orderItem = new OrderItem();
	private CartItem cartItem = new CartItem();
	private Set<OrderItem> orderItemSet = new HashSet<OrderItem>();
	private Set<Information> informationSet = new HashSet<Information>();
	private Set<InformationInsured> informationInsuredSet = new HashSet<InformationInsured>();
	private Set<InformationInsuredElements> informationInsuredElementsSet = new HashSet<InformationInsuredElements>();
	private Set<InformationDuty> informationDutyElementsSet = new HashSet<InformationDuty>();
	private List<List<InsuredShow>> showInsureds = new ArrayList<List<InsuredShow>>();//页面显示被保人信息
	private List<InsuredHealth> insuredHealthList = new ArrayList<InsuredHealth>();// 健康告知列表

	private String applicantName;// 投保人姓名
	private String applicantIdentityType;// 投保人证件类型
	private String applicantIdentityId;// 投保人证件号码
	private String applicantSex;// 投保人性别
	private String applicantBirthday;// 投保人出生日期
	private String applicantTel;// 投保人电话
	private String applicantMail;// 投保人电子邮箱
	private String applicantArea;// 投保人所在地
	private String applicantMobile;// 投保人所在地
	private String orderSn2;// 订单号
	private String price;// 价格
	private String protectionPeriodTy;// 保障期限类型
	private String protectionPeriodValue = "";// 保障期限
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
	private String applicantOccupation1;// 投保人职业
	private String applicantOccupation2;// 投保人职业
	private String applicantOccupation3;// 投保人职业
	private String height;//被保人身高
	private String weight;//被保人体重
	private String recognizeeArea;// 被保人所在地
	private String insuranceCompany;// 保险公司
	private String insuranceCompanySn;// 保险公司
	private String payType;

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
	private String amntNum = "0";// 计算保额
	private String plan;// 计划
	private Date effective;
	private Date fail;
	private String discount;// 折扣比率
	private String countPrice;// 折扣价格
	private BigDecimal currentTermAmount;// 最终折扣价格
	private String brkKindCode;// 险种代码
	private String brkRiskCode;// 计划编码
	private String brkRiskName;// 计划名称

	private List<String> detisnateList = new ArrayList<String>();

	private List<FEMRiskFactorList> periodList = new ArrayList<FEMRiskFactorList>();// 计划值列表
	private List<FEMRiskFactorList> amntList = new ArrayList<FEMRiskFactorList>();// 保额值列表
	private String factorType = "";
	private List<Map> listOrders;
	private List<OrderDutyFactor> dutyFactor = new ArrayList<OrderDutyFactor>();
	private List<OrderRiskAppFactor> riskAppFactior = new ArrayList<OrderRiskAppFactor>();
	private String textAgeFlag = "false";
	private Map<String, String> associate = new HashMap<String, String>(); // 投保元素映射
	private List<FEMRiskFactorList> planList = new ArrayList<FEMRiskFactorList>();// 计划值列表
	private List<FEMRiskFactorList> occupList = new ArrayList<FEMRiskFactorList>();// 职业类别
	private List<FEMRiskFactorList> gradeList = new ArrayList<FEMRiskFactorList>();// 产品级别列表
	private List<FEMRiskFactorList> feeYearList = new ArrayList<FEMRiskFactorList>();// 缴费年期列表
	private List<FEMRiskFactorList> appLevelList = new ArrayList<FEMRiskFactorList>();// 缴费方式列表
	private List<FEMRiskFactorList> appTypeList = new ArrayList<FEMRiskFactorList>();// 投保类别列表
	private List<FEMRiskFactorList> testAgeList = new ArrayList<FEMRiskFactorList>();// 产品级别列表
	private List<FEMRiskFactorList> mulPeopleList = new ArrayList<FEMRiskFactorList>();// 保险责任多类人群列表
	private String insureJson;// ajax传递投保试算参数
	private String dutyJson;// ajax传递责任参数
	private String[] baseInformations = new String[6];
	private List<Product> allProductAttr;
	private Map<ProductInsAttribute, List<String>> proattrMap;
	private InformationInsured informationInsureds = new InformationInsured();
	private InformationInsured informationInsureds1 = new InformationInsured();
	private InformationInsured informationInsureds2 = new InformationInsured();
	private InformationInsured informationInsureds3 = new InformationInsured();
	private InformationAppnt informationAppnt = new InformationAppnt();
	private String insureReq;
	private String dutyReq;
	private String dutyPremReq;//保存用动态分项保费
	private String dutyDisReq;
	private String relationFlag1;
	private String relationFlag2;
	private String relationFlag3;
	private String yoriginalAmount;
	private String ydiscountAmount;
	private String hour;
	private String minute;
	private String rarea;// 被保人显示地区
	private String iarea;// 投保人显示地区
	private String KID;//Session标记
	/**
	 * 产品投保录入页配置标记，如果在productConfig表中有配置，则配置为"1"
	 */
	private String configInputFlag = "0";
	/**
	 * 是否需要核保的标记
	 */
	private String needUWCheckFlag = "0";
	private List<Map> listRelation = new ArrayList<Map>();;// 与被保人关系
	private List<Map> listRelation2 = new ArrayList<Map>();// 与被保人关系
	private List<Map> listCountryCode;// 国家
	private List<Map> opts;// 职业
	private List<Map> opts2;// 职业
	private List<Map> opts3;// 职业
	private List<Map> areas;// 地区
	private List<Map> areas2;// 地区
	private List<Map> appntAreas2;// 投保人二级地区
	private List<Map> regAreas2;// 被保人一的二级地区
	private List<Map> sexs;// 性别
	private List<Map> certificateCode;// 职业
	private String relation;// 投被保人关系
	private List<PayBase> bank1;
	private List<PayBase> bank2;
	private List<PayBase> bank3;
	private Map<String, Object> sdinteraction = new HashMap<String, Object>();// 横向接口调用参数
	private List<InformationDuty> showDuty = null;// 订单结果页责任显示集合
	private String ffrelas = null;
	private String fdapAll = null;	
	private TradeInformation tradeInformation;
	private String judge;
	private Member member;

	@Resource
	private AreaService areaService;
	@Resource
	private DeliveryTypeService deliveryTypeService;
	@Resource
	private PaymentConfigService paymentConfigService;
	@Resource
	private OrderService orderService;
	@Resource
	private OrderLogService orderLogService;
	@Resource
	private CartItemService cartItemService;
	@Resource
	private OrderItemService orderItemService;
	@Resource
	private InformationService informationService;
	@Resource
	private ProductService productService;
	@Resource
	private ProductInsAttributeService productInsAttributeService;
	@Resource
	private HtmlService htmlService;
	@Resource
	private InformationAppntService informationAppntService;
	@Resource
	private InformationInsuredService informationInsuredService;
	@Resource
	private InformationRiskTypeService informationRiskTypeService;
	@Resource
	private OccupationService occupationService;
	@Resource
	private InformationInsuredElementsService informationInsuredElementsService;
	@Resource
	private InformationDutyService informationDutyService;

	@Resource
	private HealthyInfoService healthyInfoService;
	@Resource
	private PayBaseService payBaseService;
	@Resource
	private PageLayoutService pagelayoutService;
	@Resource
	private InsuredHealthService insuredHealthService;
	@Resource
	private DictionaryService dictionaryService;
	
	@Resource
	private OrderConfigService orderConfigService;
	@Resource
	private TradeInformationService tradeInformationService;
	/**
	 * 
	 * @param order 订单
	 * @return 判断支付日期是否已过
	 */
	private String isOutPeriod(Order order) {
		try {
			GetDBdata db = new GetDBdata();
			List<HashMap<String, String>> sdproduct;
				String dateSql = "select effective, fail from information where orderitem_id=(select id from orderitem where order_id=?)";
				String periodSql="select startPeriod from productPeriod where riskCode=(select productId from orders where id=?)";
				String[] dateSqltemp = {order.getId()};
				List<Map<String, Object>> datePeriod = db.queryObj(dateSql,dateSqltemp);
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat sfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Map<String, Object> period1 =datePeriod.get(0);
				Date periodfail = null;
				Date periodeffective = null;
				if(period1.get("fail")!=null){
					periodfail = sfTime.parse(period1.get("fail").toString());
					periodeffective = sfTime.parse(period1.get("effective").toString());
				}
				int stertPerid=1;
				if(StringUtil.isNotEmpty(db.getOneValue(periodSql,dateSqltemp))){
					stertPerid=Integer.parseInt(db.getOneValue(periodSql,dateSqltemp));
				}
				String tDate = com.sinosoft.sms.messageinterface.pubfun.PubFun.calSDate(sf.format(new Date()), stertPerid, "D");
				Date nowDate = com.sinosoft.sms.messageinterface.pubfun.PubFun.StringToDate(tDate);
				if ((periodeffective.getTime() < nowDate.getTime()) && (periodfail.getTime() >= periodeffective.getTime())) {
					addActionError("保单支付日期有误！！");
					return ERROR;
				}
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
			return ERROR;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ERROR;
		}
		return SUCCESS;
		
	};

	/* zhangjinquan 11180 2012-10-10 判断是否产品已经下架的公共方法 */
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
					logger.info("保险公司限购份数的值：{} -- {}", backup, LimitCount);
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

	/**
	 * 直接购买 创建日期 2012-八月-3
	 * 
	 * @param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@InputConfig(resultName = "error")
	public String buyNow() {

		Map<String, Object> paramter = new HashMap<String, Object>();
		try {
			paramter = orderService.getProductInformation(productId, "N");// 产品ID
			String[] BaseInformation = new String[14];
			BaseInformation = (String[]) paramter.get("baseInformation");// 产品基础数据
			setSession("baseInformation", BaseInformation);
			productId = BaseInformation[0].toString();// 产品ID
			productName = BaseInformation[1].toString();// 产品ID
			insuranceCompanySn = BaseInformation[2].toString();// 保险公司编码
			outRiskCode = BaseInformation[4].toString();// 保险公司产品编码
			insuranceCompanySn = insuranceCompanySn.substring(0, 4);
			insuranceCompany = BaseInformation[3].toString();// 保险公司名称
			insurType = BaseInformation[5].toString();// 内部统计险种中类别
			String[] insurTypes = insurType.split(",");
			insurType = insurTypes[0];
			insurTypeChild = BaseInformation[7].toString();// 内部统计险种子类别
			productImg = BaseInformation[8].toString();// 产品类别
			price = BaseInformation[6].toString();// 基础价格
			discount = BaseInformation[10].toString();// 折扣比率
			countPrice = BaseInformation[11].toString();// 折扣价格
			brkKindCode = BaseInformation[12].toString();// 险种编码
			JSONArray array = JSONArray.fromObject(paramter.get("ffrelas"));
		    ffrelas = array.toString();
		    JSONArray array1 = JSONArray.fromObject(paramter.get("fdapAll"));
		    fdapAll = array1.toString();

		} catch (Exception e) {
			addActionError("没有找到该产品，请重新选择！");
			return ERROR;
		}
		
		try {
			// 产品投保要素
			Map<String, String> map = new HashMap<String, String>();
			riskAppFactior = (List<OrderRiskAppFactor>) paramter.get("riskAppFactor");
			if (riskAppFactior.size() > 0) {
				setSession("riskAppFactior", riskAppFactior);
				for (int i = 0; i < riskAppFactior.size(); i++) {
					OrderRiskAppFactor orderRiskAppFactor = riskAppFactior.get(i);
					// 拆分保障期限值，用于页面
					if ("Period".equals(orderRiskAppFactor.getFactorType().toString())) {
						orderRiskAppFactor.getFactorValue();
						// String[] insurTypes =
						// orderRiskAppFactor.getFactorValue().split("-");
						List<FEMRiskFactorList> periodList = orderRiskAppFactor.getFactorValue();
						FEMRiskFactorList fEMRiskFactorList = periodList.get(0);
						String[] periods = fEMRiskFactorList.getFactorValue().split("-");
						// 填写前台保险期间
						if (this.period != null && !"".equals(this.period)) {
							periods = period.split("-");
							// 为计算保费做准备
							for (FEMRiskFactorList tempPeriodList : periodList) {
								String tPer = tempPeriodList.getFactorValue();
								if (period.equals(tPer)) {
									map.put(productId + "_" + tempPeriodList.getFactorType(), tempPeriodList.getFactorValue());
								}
							}
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
					if ("TextAge".equals(orderRiskAppFactor.getFactorType().toString())) {
						textAgeFlag = "true";
						map.put(orderRiskAppFactor.getAppFactorCode() + "_" + orderRiskAppFactor.getFactorType(), orderRiskAppFactor.getFactorValue().get(0).getFactorValue());
						if (StringUtils.isNotEmpty(textAge)) {
							map.put(orderRiskAppFactor.getAppFactorCode() + "_" + orderRiskAppFactor.getFactorType(), textAge);
						}
					}
					if ("Plan".equals(orderRiskAppFactor.getFactorType().toString())) {
						map.put(orderRiskAppFactor.getAppFactorCode() + "_" + orderRiskAppFactor.getFactorType(), orderRiskAppFactor.getFactorValue().get(0).getFactorValue());
						// brkRiskCode = orderRiskAppFactor.getFactorValue().get(0).getFactorDisplayValue();
						if (StringUtils.isNotEmpty(plan)) {
							map.put(orderRiskAppFactor.getAppFactorCode() + "_" + orderRiskAppFactor.getFactorType(), plan);
							brkRiskCode = plan;
						}
					}
					if ("MulPeople".equals(orderRiskAppFactor.getFactorType().toString())) {
						map.put(orderRiskAppFactor.getAppFactorCode() + "_" + orderRiskAppFactor.getFactorType(), orderRiskAppFactor.getFactorValue().get(0).getFactorValue());
					}
					if ("Period".equals(orderRiskAppFactor.getFactorType().toString())) {
						map.put(orderRiskAppFactor.getAppFactorCode() + "_" + orderRiskAppFactor.getFactorType(), orderRiskAppFactor.getFactorValue().get(0).getFactorValue());
						if (StringUtils.isNotEmpty(period)) {
							map.put(orderRiskAppFactor.getAppFactorCode() + "_" + orderRiskAppFactor.getFactorType(), period);
						}
					}
					if("Sex".equals(orderRiskAppFactor.getFactorType().toString())){
						if(StringUtils.isNotEmpty(sex)){
							map.put(orderRiskAppFactor.getAppFactorCode() + "_" + orderRiskAppFactor.getFactorType(), sex);
						}else{
							map.put(orderRiskAppFactor.getAppFactorCode() + "_" + orderRiskAppFactor.getFactorType(), orderRiskAppFactor.getFactorValue().get(0).getFactorValue());
						}
					}
					if("Amnt".equals(orderRiskAppFactor.getFactorType().toString())){
						if(StringUtils.isNotEmpty(amnt)){
							map.put(orderRiskAppFactor.getAppFactorCode() + "_" + orderRiskAppFactor.getFactorType(), amnt);
						}else{
							map.put(orderRiskAppFactor.getAppFactorCode() + "_" + orderRiskAppFactor.getFactorType(), orderRiskAppFactor.getFactorValue().get(0).getFactorValue());
						}
					}
					if("FeeYear".equals(orderRiskAppFactor.getFactorType().toString())){
						if(StringUtils.isNotEmpty(feeYear)){
							map.put(orderRiskAppFactor.getAppFactorCode() + "_" + orderRiskAppFactor.getFactorType(), feeYear);
						}else{
							map.put(orderRiskAppFactor.getAppFactorCode() + "_" + orderRiskAppFactor.getFactorType(), orderRiskAppFactor.getFactorValue().get(0).getFactorValue());
						}
					}
					String appFactiorType = riskAppFactior.get(i).getFactorType().toString();
				}
			}
			// 调用函数，同时修改价格。 start
			// map.put("00000000000000000279_TextAge", "0Y-80Y");
			// map.put("200701001_Period", "4D-6D");
			map.put("RiskCode", productId);
			Map<String, String> tprice = this.CalPrice(productId, map);
			if (tprice != null && !"".equals(tprice)) {
				// 获取价格
				this.price = tprice.get("DiscountPrice");
				discount = tprice.get("DiscountRate");
				countPrice = tprice.get("Prem");
			}
			// end
			// 产品责任要素
			dutyFactor = (List<OrderDutyFactor>) paramter.get("dutyFactor");
			List backup = new ArrayList();
			if (dutyFactor.size() > 0) {
				for (OrderDutyFactor orderDutyFactor : dutyFactor) {
					for (FEMDutyAmntPremList fEMDutyAmntPremList : orderDutyFactor.getFdAmntPremList()) {
						if(fEMDutyAmntPremList!=null){
							backup.add(fEMDutyAmntPremList.getBackUp1());
						}
					}
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
			order.setOrderStatus(OrderStatus.prepay);
			order.setPaymentStatus(PaymentStatus.unpaid);
			order.setProductTotalPrice(prem);
			order.setPaidAmount(new BigDecimal("0"));
			order.setTotalAmount(prem);
			order.setProductTotalQuantity(1);
			order.setProductName(productName);
			order.setProductId(productId);
			order.setOutRiskCode(outRiskCode);
			order.setInsuranceCompany(insuranceCompany);
			order.setInsuranceCompanySn(insuranceCompanySn);
			order.setInsuranceSn(brkKindCode);
			order.setBrkRiskCode(brkRiskCode);
			if (getLoginMember() != null) {
				order.setMemberId(getLoginMember().getId());
				loginFlag = "true";
			}
			orderItem.setProductId(productId);
			orderItem.setProductName(productName);
			orderItem.setProductPrice(PreferentialPrice);
			orderItem.setProductQuantity(productQuantity);
			orderItem.setProductHtmlFilePath(productHtmlFilePath);
			orderItem.setOrder(order);
			Integer backNum = 0;
			// 得到投保要素信息
			// 根据险别跳转投保信息页
		} catch (Exception e) {
			logger.error(e.getMessage() + e);
			addActionError("页面加载失败，请返回重试！");
			return ERROR;
		}

		String pageString = "";
		if (insuranceCompanySn != null && !"".equals(insuranceCompanySn) && productId != null && !"".equals(productId)) {
			pageString = pagelayoutService.getReturnPageByProduct(productId, insuranceCompanySn);
		}
		if (pageString == null || "".equals(pageString)) {
			if (insurTypeChild != null && !"".equals(insurTypeChild) && insuranceCompanySn != null && !"".equals(insuranceCompanySn)) {
				pageString = pagelayoutService.getReturnPage(insuranceCompanySn, insurTypeChild);
			}
		}

		if (StringUtils.isNotEmpty(pageString)) {

			return pageString;
		} else {
			if ("02".equals(insurType)) {
				return "accinsur";
			} else if ("03".equals(insurType)) {
				return "tarvelinsur";
			} else {
				return null;
			}
		}
	}

	private Map<String, String> CalPrice(String RiskCode, Map<String, String> map) {
		String riskcode = RiskCode;
		CalProductPrem[] calProductPrem = new CalProductPrem[1];
		calProductPrem[0] = new CalProductPrem();
		calProductPrem[0].setRiskCode(riskcode);
		// Map<String, String> map = new HashMap<String, String>();
		// map.put("00000000000000000279_TextAge", "0Y-80Y");
		// map.put("200701001_Period", "4D-6D");
		// map.put("RiskCode", "200701001");
		com.sinosoft.webservice.productPrem.ProductPremServiceStub.FEMRiskFactorList[] fEMRiskFactorList = new com.sinosoft.webservice.productPrem.ProductPremServiceStub.FEMRiskFactorList[map.size() - 1];
		Iterator<String> itr = map.keySet().iterator();
		String key = "";
		int iCount = 0;
		while (itr.hasNext()) {
			key = itr.next();
			if ("RiskCode".equals(key)) {
				continue;
			}
			fEMRiskFactorList[iCount] = new com.sinosoft.webservice.productPrem.ProductPremServiceStub.FEMRiskFactorList();
			fEMRiskFactorList[iCount].setAppFactorCode(key.split("_")[0]);
			fEMRiskFactorList[iCount].setFactorType(key.split("_")[1]);
			fEMRiskFactorList[iCount].setFactorValue(map.get(key));
			if ("TextAge".equals(fEMRiskFactorList[iCount].getFactorType())) {
				String age = map.get(key);
				if (age != null && !"".equals(age) && age.indexOf("Y") >= 0) {
					age = age.substring(0, age.indexOf("Y"));
					if ("0".equals(age)) {
						age = "1";
					}
					String birthday = com.sinosoft.sms.messageinterface.pubfun.PubFun.calSDate(PubFun.getCurrentDate(), 0 - Integer.parseInt(age), "Y");
					fEMRiskFactorList[iCount].setFactorValue(birthday);
				}
			}
			fEMRiskFactorList[iCount].setIsPremCalFacotor("Y");
			iCount++;
		}
		calProductPrem[0].setFEMRiskFactorList(fEMRiskFactorList);
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("CalProductPrem", calProductPrem);
		Map<String, String> result = new HashMap<String, String>();
		ProductPremResponse ProductPrem;
		try {
			ProductPrem = ProductWebservice.ProductPremSereviceImpl(mp, null);
			result.put("Prem", ProductPrem.getPrem()[0] + "");// 原价
			result.put("DiscountPrice", ProductPrem.getDiscountPrice()[0] + "");// 折扣价
			result.put("DiscountRate", ProductPrem.getDiscountRate()[0] + "");// 折扣率
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public long fromDateStringToLong(String inVal) {
		Date date = null;
		SimpleDateFormat inputFormat = new SimpleDateFormat("hh:mm:ss");
		try {
			date = inputFormat.parse(inVal);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return date.getTime();
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
				String tDate = com.sinosoft.sms.messageinterface.pubfun.PubFun.calSDate(sf.format(d), 1, "D");
				Date nowDate = com.sinosoft.sms.messageinterface.pubfun.PubFun.StringToDate(tDate);
				if ((effective.getTime() < nowDate.getTime()) && (fail.getTime() >= effective.getTime())) {
					addActionError("保单起保日期或者保单终止日期有误！");
					return ERROR;
				}
				String LimitCount = product.get("LimitCount");
				logger.info("保险公司限购份数：{}", LimitCount);
				if (StringUtil.isNotEmpty(LimitCount) && !"0".equals(LimitCount)) {
					String backup_ = "select count(orders.id) from orders where  orderStatus=7 and productid='" + productId + "' and  id in( select order_id from orderitem where id in(";
					backup_ += "select information.orderitem_id from information where (('"+ sf.format(fail) +" 23:59:59'>=effective and effective>='"+ sf.format(effective) +" 00:00:00') or ('"+ sf.format(effective) +" 00:00:00'<=fail and fail<='"+ sf.format(fail) +" 23:59:59')) and id in (select information_id from informationinsured ";
					backup_ +=  "where recognizeeIdentityType=? and recognizeeIdentityId=?)))";
					logger.info("保险公司限购份数（sql）：{}", backup_);
					String[] backuptemp = {informationInsureds1.getRecognizeeIdentityType(),informationInsureds1.getRecognizeeIdentityId()};
					String backup = db.getOneValue(backup_ , backuptemp);
					logger.info("保险公司限购份数的值：{}--{}", backup, LimitCount);
					if (StringUtil.isNotEmpty(backup)) {
						if (Integer.parseInt(backup) >= Integer.parseInt(LimitCount)) {
							addActionError("此被保人的证件号已经购买过此产品，超过限购份数！");
							return ERROR;
						}
					}
				}

				logger.info("职业校验：{}  productId:{}", informationInsureds1.getOccupation3(), productId);
				if (informationInsureds1.getOccupation3() != null) {
					if (!validateOccup(informationInsureds1.getOccupation3(), productId)) {
						addActionError("您无法购买此产品(occup)！");
						return ERROR;
					}
				}

				String birthday = informationInsureds1.getRecognizeeBirthday();
				if (StringUtil.isEmpty(birthday)) {
					birthday = informationAppnt.getApplicantBirthday();
					logger.info("保险人生日：{}", birthday);
				}
				logger.info("年龄校验：{}  productId:", birthday, productId);
				if (!validateAge(birthday, productId,effective)) {
					addActionError("您无法购买此产品(birthday)！");
					return ERROR;
				}

				// 保险日期校验
				if (effective == null || fail == null) {
					addActionError("您无法购买此产品(effective)！");
					return ERROR;
				}
				Date flightTime = informationInsureds1.getFlightTime();
				/* zhangjinquan 太平洋的航空旅客意外伤害保险才有起飞时间控制 2012-10-21 */
				if (("2011".equals(insuranceCompanySn)) && ("201101013".equals(order.getProductId()))) {
					if (flightTime == null) {
						addActionError("您无法购买此产品(flightTime)！");
						return ERROR;
					}

					// 拼装起飞时间
					SimpleDateFormat f2 = new SimpleDateFormat("yyyy-MM-dd");
					String flightDate = f2.format(flightTime);
					String time = hour + ":" + minute + ":00";
					String dateTime = flightDate + " " + time;
					DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					Date date = null;
					try {
						date = sdf.parse(dateTime);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
					java.text.DateFormat format2 = new java.text.SimpleDateFormat("yyyy-MM-dd");
					String sysDate = format2.format(new Date());
					if (flightDate.equals(sysDate)) {
						java.text.DateFormat format1 = new java.text.SimpleDateFormat("hh:mm:ss");
						String s = format1.format(new Date());
						long startT = fromDateStringToLong(s);
						long endT = fromDateStringToLong(time);
						long secd = (endT - startT) / (1000);
						if (secd < 600) {
							addActionError("您无法购买此产品！当日航班起飞时间应大于当前系统时间10分钟");
							return ERROR;
						}
					}
					informationInsureds1.setFlightTime(date);
				}

				if ("22".equals(insurTypeChild) || "23".equals(insurTypeChild) || "24".equals(insurTypeChild) || "25".equals(insurTypeChild) || "26".equals(insurTypeChild)) {
					if ("true".equals(relationFlag1)) {
						if ("男".equals(informationAppnt.getApplicantSexName())) {
							addActionError("您无法购买此产品(被保人必须为女性)！");
							return ERROR;
						}
					} else {
						if ("男".equals(informationInsureds1.getRecognizeeSexName())) {
							addActionError("您无法购买此产品(被保人必须为女性)！");
							return ERROR;
						}
					}
				}
				// if (!DateCompare(effective, fail)) {
				// addActionError("您无法购买此产品(effective)！");
				// return ERROR;
				// }
			}
			String totelPrem = (String) getSession("TotelPrem_" + productId);
			productTotalPrice = new BigDecimal(price);// 保费 ，接口试算后获得
			if ("2011".equals(insuranceCompanySn)) {
				currentTermAmount = new BigDecimal(countPrice);// 优惠后保费
			}
			if (StringUtil.isEmpty(price)) {
				addActionError("此操作非法(price is null)！");
				return ERROR;
			}
			logger.info("保费校验：session:{}  页面:{}", totelPrem, productTotalPrice);
			if (StringUtil.isNotEmpty(totelPrem)) {
				String b1 = new BigDecimal(totelPrem).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
				String b2 = new BigDecimal(price).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
				if (!b1.equals(b2)) {
					logger.info("保费校验：session:{}  页面:{}", b1, b2);
					addActionError("此操作非法(price)！");
					return ERROR;
				}
			}

			if ("temptorysave".equals(orderStatus)) {
				order.setOrderStatus(OrderStatus.temptorysave);
			} else {
				// order.setOrderStatus(OrderStatus.prepay);
				// modify guobin 20120911 投保要素页面认为是订单暂存状态
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
			order.setPaymentStatus(PaymentStatus.unpaid);
			order.setProductTotalPrice(productTotalPrice);
			order.setPaidAmount(new BigDecimal("0"));
			order.setPaymentFee(paymentFee);
			order.setTotalAmount(productTotalPrice);
			order.setCurrentTermAmount(currentTermAmount);// 太平洋原始价格存储
			order.setProductTotalQuantity(0);
			order.setPoint(0);
			order.setProductName(productName);
			order.setInsuranceSn(brkKindCode);
			order.setProductId(productId);
			order.setInsuranceCompany(insuranceCompany);
			order.setInsuranceCompanySn(insuranceCompanySn);
			order.setOrderItemSet(orderItemSet);
			order.setOrderValid(true);
			order.setOutRiskCode(outRiskCode);
			order.setSubRiskTypeCode(insurTypeChild);
			order.seteRiskType(insurType);
			order.setAmntPrice(new BigDecimal(getAllAmount()));
			orderSn = PubFun.GetOrderSn();
			KID=StringUtil.md5Hex(this.request.getSession().getId()+orderSn);
			order.setBrkRiskCode(brkRiskCode);
			order.setBrkRiskName(brkRiskName);
			order.setDiscountRates(discount);
			order.setOrderSn(orderSn);
			orderService.save(order);
			orderItem.setOrder(order);
			orderId = order.getId();
			if ("2011".equals(insuranceCompanySn)) {
				order.setTpySn(PubFun.GetTpySn());// 存入太平洋核保后生成特殊编号
			}
			informationSet.add(information1);
			orderItem.setInformationSet(informationSet);
			orderItemService.save(orderItem);
			information1.setInformationAppnt(informationAppnt);
			information1.setOrderItem(orderItem);// information保存orderItem
			information1.setEffective(effective); // 保单起始日期
			information1.setFail(fail); // 保单终止日期
			information1.setAllPeriod(period);
			information1.setFeeYear(feeYear);
			information1.setInformationDutyElementsSet(informationDutyElementsSet);
			information1.setInformationInsuredSet(informationInsuredSet);
			if ("Y".equals(protectionPeriodTy)) {
				protectionPeriodTy = "y";
			}
			if ("D".equals(protectionPeriodTy) || "".equals(protectionPeriodTy)) {
				protectionPeriodTy = "d";
			}
			if ("M".equals(protectionPeriodTy)) {
				protectionPeriodTy = "m";
			}
			if("A".equals(protectionPeriodTy)){
				protectionPeriodTy = "a";
			}
			information1.setProtectionPeriodTy(protectionPeriodTy);
			if ("".equals(protectionPeriodLast)) {
				protectionPeriodLast = "1";
			}
			information1.setProtectionPeriod(protectionPeriodLast);
			/* zhangjinquan 11180 针对太平洋产品，终止日期的时间设定为23:59:59 */
			information1.setFail(DateUtil.setTimeOfDate(information1.getFail(), 23, 59, 59));
			information1.setAppType(appType);
			informationService.save(information1);
			this.informationAppntSave(information1);// 投保人信息存储

			orderItem.setValid(true);
			orderItemService.update(orderItem);
			order = orderService.load(orderId);
			orderService.update(order);
			orderItem = orderItemService.getByOrP(productId, orderId);// p:productId;
			information = informationService.getByProduct(productId, orderId);

			String ordersn = (String) getSession("OrderSn");
			if (StringUtil.isNotEmpty(ordersn)) {
				if (ordersn.indexOf(order.getOrderSn()) == -1) {
					setSession("OrderSn", ordersn + "," + order.getOrderSn());
				}
			} else {
				setSession("OrderSn", order.getOrderSn());
			}
			logger.info("OrderSn放入到session内{}", getSession("OrderSn"));

			/* 转移到此处 */
			this.informationInsuredSave(information1,insuranceCompanySn);// 被保人信息存储
			
			//保存健康告知信息，从和众开始    begin add by fhz  on 2012-11-29
			this.insuredHealthySave(informationInsureds1, order);
		    // 保存健康告知信息 fhz  end 
			
			// req20121128002:CPS 梅俊峰 2012/12/25 add start
			//OrderInfoCommit.infoCommitForEmar(request, order);
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
                //目前只有合众的
				if("1061".equals(insuranceCompanySn)&&insuredHealthList!=null && insuredHealthList.size()>0){
					return "healthy";
				}else{
					showInformaton(order.getId());//显示信息
					this.setNeedUWCheckFlag(getNeedUWFlagFromDB(order.getInsuranceCompanySn()));
					return "result";
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionError("订单保存失败，请重试！");
			return ERROR;
		}

	}
	
	/**
	 * 保存健康告知信息
	 * @author fhz
	 * copy OrderConfigAction.insuredHealtySave  
	 * create 2012-11-29
	 * */
	private void insuredHealthySave(InformationInsured informationInsuredsSave, Order or) {
		
			String productId = or.getProductId();
			String comCode = or.getInsuranceCompanySn();
			List<HealthyInfo> healthList = healthyInfoService.findByComAndProduct(
					productId, comCode);
			insuredHealthList = insuredHealthService
					.createShowInformation(healthList);
		
		if (insuredHealthList != null && insuredHealthList.size() > 0) {
			for (InsuredHealth ih : insuredHealthList) {
				ih.setInformationInsured(informationInsuredsSave);
				insuredHealthService.save(ih);
			}
		}
	}

	
	/**
	 * 从数据库查询对应保险公司是否需要核保
	 * @param insuranceCode
	 */
	public static String getNeedUWFlagFromDB(String insuranceCode)
	{
		if (StringUtil.isEmpty(insuranceCode))
		{
			return "0";
		}
		String sql = "select 1 from zdcode where CodeType=? and ParentCode=? and CodeValue=?";
		QueryBuilder qb = new QueryBuilder(sql);
		qb.add("UWCheckClassName");
		qb.add("UWCheckClassName");
		qb.add(insuranceCode);
		if (StringUtil.isNotEmpty(qb.executeString()))
		{
			return "1";
		}
		return "0";
	}
	
	/**
	 * 从数据库查询对应保险公司及产品是否做了投保要素录入项配置
	 * @param insuranceCode
	 * @param productid
	 */
	public static String getConfigInputFlagFromDB(String insuranceCode)
	{
		if (StringUtil.isEmpty(insuranceCode))
		{
			return "0";
		}
		String sql = "select 1 from zdcode where CodeType=? and ParentCode=? and CodeValue=?";
		QueryBuilder qb = new QueryBuilder(sql);
		qb.add("ConfigInput");
		qb.add("ConfigInput");
		qb.add(insuranceCode);
		if (StringUtil.isNotEmpty(qb.executeString()))
		{
			return "1";
		}
		return "0";
	}


	
	/**
	 *修改健康告知信息
	 *@author fhz 
	 *copy OrderConfigAction.saveOrUpdateHealthInf
	 *create 2012-11-29
	 */
	public String saveOrUpdateHealthInf(){
		try {
			InformationInsured infIned = informationInsuredService.get(informationInsureds1.getId());
			Set<InsuredHealth> ihSet = infIned.getInsuredHealthSet();
			if(ihSet!=null && ihSet.size()>0){
				for(InsuredHealth ih : ihSet){
					String selectFlag = getParameter(ih.getHealthyInfoId());
					String isFZProduct = getParameter("isFZProduct");
					//如果不是合众复杂健康告知则执行下面验证（有选择否则不允许投保支付操作）
					if(isFZProduct == null || "".equals(isFZProduct)){
						if("1061".equals(ih.getInsuranceCompany())&&"Y".equals(selectFlag)){
							addActionError("您的情况不符合本产品的投保条件，请购买其他同类产品！");
							return ERROR;
						}
					}
					ih.setSelectFlag(selectFlag);
					String typeShowOrder = getParameter(ih.getHealthyInfoId()+"order");
					ih.setTypeShowOrder(typeShowOrder);
					if("Y".equals(ih.getValueTypeFlag())){
						String childInfoValue = getParameter(ih.getHealthyInfoId()+"childValue");
						ih.setChildInfoValue(childInfoValue);
					}
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
			KID=StringUtil.md5Hex(this.request.getSession().getId()+order.getOrderSn()) ; 
			return "result";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionError("健康告知存储失败，请重试！");
			return ERROR;
		}
	}

	
	/**
	 * 输入完健康告知信息后 显示订单信息
	 * @author fhz
	 * copy OrderConfigAction.showInformaton create on 20121129
	 * */
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
	

	private String getAreaName(String code) {
		Area a = areaService.get(code);
		if (a != null) {
			return a.getName();
		}
		return "";
	}

	// 从我的订单链接到订单详细页面
	public String linkOrderDetails() {
		try {
			String nKID = StringUtil.md5Hex(this.request.getSession().getId()+orderSn);
			if(!nKID.equals(KID)){
				addActionError("请登陆后，进行此操作！");
				return ERROR;
			} 
			order = orderService.getOrderByOrderSn(orderSn);
			showInformaton(order.getId());
			informationAppnt = order.getOrderItemSet().iterator().next().getInformationSet().iterator().next().getInformationAppnt();
			showDuty = new ArrayList<InformationDuty>(order.getOrderItemSet().iterator().next().getInformationSet().iterator().next().getInformationDutyElementsSet());
			showInsureds = informationInsuredService.createShowInformationInsured(order.getOrderItemSet().iterator().next().getInformationSet().iterator().next().getInformationInsuredSet(),order.getInsuranceCompanySn());
			if (order == null) {
				addActionError("系统中没有该订单的存在!");
				return ERROR;
			}
			productId = order.getProductId();
			information = informationService.getByProduct(productId, order.getId());
			if (information == null) {
				addActionError("系统中没有该订单的存在!");
				return ERROR;
			}
		} catch (Exception e) {
			addActionError("连接错误");
			return ERROR;
		}
		return "resultxx";
	}
	/**
	 * 立即购买重新填写 创建日期 2012-八月-3keep
	 * 
	 * @param
	 * @return
	 */
	@InputConfig(resultName = "error")
	public String buyNowUpdate() {
		JdbcTemplateData jtd = new JdbcTemplateData();
		order = orderService.load(orderId);
		insuranceCompanySn = order.getInsuranceCompanySn();
		outRiskCode = order.getOutRiskCode();
		try {
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
			orderItemSet = order.getOrderItemSet();
			for (OrderItem orderItems : orderItemSet) {
				orderItem = orderItems;
			}
			informationSet = orderItem.getInformationSet();
			for (Information orderItemss : informationSet) {
				information = orderItemss;
			}
			// orderItem = orderItemService.getByOrP(productId, orderId);
			List<InformationInsured> infs = new ArrayList<InformationInsured>(information.getInformationInsuredSet());
			for (int i = 0; i < infs.size(); i++) {
				if (i == 0) {
					informationInsureds1 = infs.get(0);
					String Occupation1 = informationInsureds1.getOccupation1();
					String Occupation2 = informationInsureds1.getOccupation2();
					String recognizeeArea1 = informationInsureds1.getRecognizeeArea1();
					String sqlreArea2;
					if (insuranceCompanySn != null && !"".equals(insuranceCompanySn) && recognizeeArea1 != null && !"".equals(recognizeeArea1)) {
						sqlreArea2 = "select id,name from area where insuranceCompany=? and parent_id = ? order by id asc";
						String[] sqlreArea2temp = {insuranceCompanySn,recognizeeArea1};
						regAreas2 = jtd.obtainData(sqlreArea2,sqlreArea2temp);
					}
					String sqlOpts2, sqlOpts3;
					String[] temp2 = new String[2];
					String[] temp3 = new String[2];
					if ("1061".equals(insuranceCompanySn) || "1018".equals(insuranceCompanySn)) {
						temp2[0] = insuranceCompanySn;
						temp2[1] = Occupation1;
						temp3[0] = insuranceCompanySn;
						temp3[1] = Occupation2;
						sqlOpts2 = "select id,name from occupation where insuranceCompany= ? and parent_id =? order by id asc";
						sqlOpts3 = "select id,name from occupation where insuranceCompany= ? and parent_id =? order by id asc";
					} else {
						temp2[0] = "1061";
						temp2[1] = Occupation1;
						temp3[0] = "1061";
						temp3[1] = Occupation2;
						sqlOpts2 = "select id,name from occupation where insuranceCompany=?  and parent_id =? order by id asc";
						sqlOpts3 = "select id,name from occupation where insuranceCompany=?  and parent_id =? order by id asc";
					}
					opts2 = jtd.obtainData(sqlOpts2,temp2);// 职业
					opts3 = jtd.obtainData(sqlOpts3,temp3);// 职业
					if (informationInsureds1.getDestinationCountry() != null && !"".equals(informationInsureds1.getDestinationCountry())) {
						String[] destinate = informationInsureds1.getDestinationCountry().split(",");
						if (destinate != null && destinate.length > 0) {
							int kk = 0;
							for (kk = 0; kk < destinate.length; kk++) {
								String sqlCountryCodess = "select codename from dictionary where codetype=? and codevalue = ? and insuranceCode=?";
								String[] sqlCountryCodesstemp = {"CountryCode",destinate[kk].trim(),insuranceCompanySn};
								List<Map> dlist = new ArrayList<Map>();
								dlist = jtd.obtainData(sqlCountryCodess,sqlCountryCodesstemp);
								Iterator<Map> it = dlist.iterator();
								while (it.hasNext()) {
									Map map = it.next();
									detisnateList.add((String) map.get("codeName"));
								}
							}
						}
					}
				}
				if (i == 1) {
					informationInsureds2 = infs.get(1);
				}
				if (i == 2) {
					informationInsureds3 = infs.get(2);
				}
			}

			informationAppnt = information.getInformationAppnt();
			String applicantArea1 = informationAppnt.getApplicantArea1();
			String sqlapArea2;
			if (insuranceCompanySn != null && !"".equals(insuranceCompanySn) && applicantArea1 != null && !"".equals(applicantArea1)) {
				sqlapArea2 = "select id,name from area where insuranceCompany=? and parent_id = ? order by id asc";
				String[] sqlapArea2temp = {insuranceCompanySn,applicantArea1};
				appntAreas2 = jtd.obtainData(sqlapArea2,sqlapArea2temp);
			}
			// for (InformationInsured informationInsured :
			// information.getInformationInsuredSet()) {
			// informationInsureds1 = informationInsured;
			// }
			productId = order.getProductId();
			Map<String, Object> paramter = new HashMap<String, Object>();
			// 获取产品id\sn、类别、险种、投保要素、价格等
			paramter = orderService.getProductInformation(productId, "N");
			String[] BaseInformation = new String[14];
			BaseInformation = (String[]) paramter.get("baseInformation");// 产品基础数据
			JSONArray array = JSONArray.fromObject(paramter.get("ffrelas"));
		    ffrelas = array.toString();
		    JSONArray array1 = JSONArray.fromObject(paramter.get("fdapAll"));
		    fdapAll = array1.toString();
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
					if ("TextAge".equals(orderRiskAppFactor.getFactorType().toString())) {
						textAgeFlag = "true";
					}
					if ("MulPeople".equals(orderRiskAppFactor.getFactorType().toString())) {
						// this.mulPeople = informationInsureds1.getInformationInsuredElementsSet();
						for (InformationInsuredElements informationInsuredElements : informationInsureds1.getInformationInsuredElementsSet()) {
							if ("MulPeople".equals(informationInsuredElements.getElementsType())) {
								this.mulPeople = informationInsuredElements.getElementsValue();
							}
						}
					}
				}
			}
			getInformationInsuredElementsValue(informationInsureds1.getInformationInsuredElementsSet());
			// 产品责任要素
			dutyFactor = (List<OrderDutyFactor>) paramter.get("dutyFactor");
			if (dutyFactor.size() > 0) {
				setSession("dutyFactor", dutyFactor);
			}
			this.dataBuild();
			productId = order.getProductId();
			productName = order.getProductName();
			if (StringUtil.isEmpty(order.getCurrentTermAmount() + "")) {
				countPrice = order.getCurrentTermAmount() + "";
			}
			discount = order.getDiscountRates();
			insuranceCompany = order.getInsuranceCompany();
			insurType = BaseInformation[5].toString();
			String[] insurTypes = insurType.split(",");
			insurType = insurTypes[0];
			insurTypeChild = BaseInformation[7].toString();// 产品小类别
			productImg = BaseInformation[8].toString();// 产品类别
			String[] insurTypeChilds = insurTypeChild.split(",");
			insurTypeChild = insurTypeChilds[0];
			outRiskCode = BaseInformation[4].toString();// 保险公司产品编码
			brkKindCode = BaseInformation[12].toString();// 险种编码
			/* zhangjinquan 11180 2012-10-11 bug880继续录入提示价格非法，应该先查看订单是否有金额，没有才使用基础价格 */
			/*
			 * price = BaseInformation[6].toString();// 基础价格 if (price.equals("0")) { price = String.valueOf(order.getTotalAmount()); }
			 */
			price = order.getTotalAmount().toString();/* 订单已保存的金额 */
			countPrice = order.getCurrentTermAmount() + "";
			if (price.equals("0") || (order.getTotalAmount().doubleValue() == 0.00)) {
				price = BaseInformation[6].toString();// 基础价格
			}
			BigDecimal productTotalPriceTemp = new BigDecimal(price);
			order.setProductTotalPrice(productTotalPriceTemp);
			order.setInsuranceSn(brkKindCode);
			setSession("TotelPrem_" + productId, price);
			showDuty = new ArrayList<InformationDuty>(informationDutyElementsSet);
			if (getLoginMember() != null) {
				order.setMemberId(getLoginMember().getId());
				loginFlag = "true";
			}
			request.setAttribute("status", "update");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionError("出错了，请返回重试！");
			return ERROR;
		}
		String pageString = "";
		if (insuranceCompanySn != null && !"".equals(insuranceCompanySn) && productId != null && !"".equals(productId)) {
			pageString = pagelayoutService.getReturnPageByProduct(productId, insuranceCompanySn);
		}
		if (pageString == null || "".equals(pageString)) {
			if (insurTypeChild != null && !"".equals(insurTypeChild) && insuranceCompanySn != null && !"".equals(insuranceCompanySn)) {
				pageString = pagelayoutService.getReturnPage(insuranceCompanySn, insurTypeChild);
			}
		}
		if (StringUtils.isNotEmpty(pageString)) {
			return pageString;
		} else {
			if ("02".equals(insurType)) {
				return "accinsur";
			} else if ("03".equals(insurType)) {
				return "tarvelinsur";
			} else {
				return null;
			}

		}
	}

	private void getInformationInsuredElementsValue(Set<InformationInsuredElements> insuredElementsSet) {
		if(insuredElementsSet != null && insuredElementsSet.size()>0){
			for (InformationInsuredElements informationInsuredElements : informationInsureds1.getInformationInsuredElementsSet()) {
				if ("MulPeople".equals(informationInsuredElements.getElementsType())) {
					this.mulPeople = informationInsuredElements.getElementsValue();
				}else if("AppLevel".equals(informationInsuredElements.getElementsType())){
					this.appLevel = informationInsuredElements.getElementsValue();
				}else if("AppType".equals(informationInsuredElements.getElementsType())){
					this.appType = informationInsuredElements.getElementsValue();
				}else if("FeeYear".equals(informationInsuredElements.getElementsType())){
					this.feeYear = informationInsuredElements.getElementsValue();
				}else if("Grade".equals(informationInsuredElements.getElementsType())){
					this.grade = informationInsuredElements.getElementsValue();
				}else if("Period".equals(informationInsuredElements.getElementsType())){
					this.period = informationInsuredElements.getElementsValue();
				}else if("Plan".equals(informationInsuredElements.getElementsType())){
					this.plan = informationInsuredElements.getElementsValue();
				}else if("Amnt".equals(informationInsuredElements.getElementsType())){
					this.amnt = informationInsuredElements.getElementsValue();
				}
			}
		}
		
	}

	/**
	 * 直接购买更新 创建日期 2012-八月-3
	 * 
	 * @param
	 * @return
	 */
	public String bbxinfoUpdate() {
		if (orderId == null && "".equals(orderId)) {
			addActionError("操作有误，请重试！");
			return ERROR;
		}
		String resultString = bbxinfosaveforupdate(orderId);
		KID=StringUtil.md5Hex(this.request.getSession().getId()+order.getOrderSn()) ; 
		return resultString;
	}

	
	public String bbxinfosaveforupdate(String oldOrderId) {
		if (getLoginMember() != null) {
			order.setMemberId(getLoginMember().getId());
		}
		try {
			GetDBdata db = new GetDBdata();
			String paidSQL = " select orders.paymentStatus from orders where orders.id=? ";
			String[] paidtemp = {orderId} ;
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
				String tDate = com.sinosoft.sms.messageinterface.pubfun.PubFun.calSDate(sf.format(d), 1, "D");
				Date nowDate = com.sinosoft.sms.messageinterface.pubfun.PubFun.StringToDate(tDate);
				if ((effective.getTime() < nowDate.getTime()) && (fail.getTime() >= effective.getTime())) {
					addActionError("保单起保日期或者保单终止日期有误！");
					return ERROR;
				}
				String LimitCount = product.get("LimitCount");
				logger.info("保险公司限购份数：{}", LimitCount);
				if (StringUtil.isNotEmpty(LimitCount) && !"0".equals(LimitCount)) {
					String backup_ = "select count(orders.id) from orders where  orderStatus=7 and productid='" + productId + "' and  id in( select order_id from orderitem where id in(";
					backup_ += "select information.orderitem_id from information where (('"+ sf.format(fail) +" 23:59:59'>=effective and effective>='"+ sf.format(effective) +" 00:00:00') or ('"+ sf.format(effective) +" 00:00:00'<=fail and fail<='"+ sf.format(fail) +" 23:59:59')) and id in (select information_id from informationinsured ";
					backup_ += "where recognizeeIdentityType=? and recognizeeIdentityId=?)))";
					logger.info("保险公司限购份数（sql）：{}", backup_);
					String[] backuptemp = {informationInsureds1.getRecognizeeIdentityType(),informationInsureds1.getRecognizeeIdentityId()};
					String backup = db.getOneValue(backup_ ,backuptemp);
					logger.info("保险公司限购份数的值：{} -- {}", backup, LimitCount);
					if (StringUtil.isNotEmpty(backup)) {
						if (Integer.parseInt(backup) >= Integer.parseInt(LimitCount)) {
							addActionError("此被保人的证件号已经购买过此产品，超过限购份数！");
							return ERROR;
						}
					}
				}

				logger.info("职业校验：{}  productId:{}", informationInsureds1.getOccupation3(), productId);
				if (informationInsureds1.getOccupation3() != null) {
					if (!validateOccup(informationInsureds1.getOccupation3(), productId)) {
						addActionError("您无法购买此产品(occup)！");
						return ERROR;
					}
				}

				String birthday = informationInsureds1.getRecognizeeBirthday();
				if (StringUtil.isEmpty(birthday)) {
					birthday = informationAppnt.getApplicantBirthday();
					logger.info("保险人生日：{}", birthday);
				}
				logger.info("年龄校验：{}  productId:{}", birthday, productId);
				if (!validateAge(birthday, productId,effective)) {
					addActionError("您无法购买此产品(birthday)！");
					return ERROR;
				}

				// 保险日期校验
				if (effective == null || fail == null) {
					addActionError("您无法购买此产品(effective)！");
					return ERROR;
				}
				Date flightTime = informationInsureds1.getFlightTime();
				/* zhangjinquan 太平洋的航空旅客意外伤害保险才有起飞时间控制 2012-10-21 */
				if (("2011".equals(insuranceCompanySn)) && ("201101013".equals(order.getProductId()))) {
					if (flightTime == null) {
						addActionError("您无法购买此产品(flightTime)！");
						return ERROR;
					}

					// 拼装起飞时间
					SimpleDateFormat f2 = new SimpleDateFormat("yyyy-MM-dd");
					String flightDate = f2.format(flightTime);
					String time = hour + ":" + minute + ":00";
					String dateTime = flightDate + " " + time;
					DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					Date date = null;
					try {
						date = sdf.parse(dateTime);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
					java.text.DateFormat format2 = new java.text.SimpleDateFormat("yyyy-MM-dd");
					String sysDate = format2.format(new Date());
					if (flightDate.equals(sysDate)) {
						java.text.DateFormat format1 = new java.text.SimpleDateFormat("hh:mm:ss");
						String s = format1.format(new Date());
						long startT = fromDateStringToLong(s);
						long endT = fromDateStringToLong(time);
						long secd = (endT - startT) / (1000);
						if (secd < 600) {
							addActionError("您无法购买此产品！当日航班起飞时间应大于当前系统时间10分钟");
							return ERROR;
						}
					}
					informationInsureds1.setFlightTime(date);
				}

				if ("22".equals(insurTypeChild) || "23".equals(insurTypeChild) || "24".equals(insurTypeChild) || "25".equals(insurTypeChild) || "26".equals(insurTypeChild)) {
					if ("true".equals(relationFlag1)) {
						if ("男".equals(informationAppnt.getApplicantSexName())) {
							addActionError("您无法购买此产品(被保人必须为女性)！");
							return ERROR;
						}
					} else {
						if ("男".equals(informationInsureds1.getRecognizeeSexName())) {
							addActionError("您无法购买此产品(被保人必须为女性)！");
							return ERROR;
						}
					}
				}
				// if (!DateCompare(effective, fail)) {
				// addActionError("您无法购买此产品(effective)！");
				// return ERROR;
				// }
			}
			String totelPrem = (String) getSession("TotelPrem_" + productId);
			productTotalPrice = new BigDecimal(price);// 保费 ，接口试算后获得
			if ("2011".equals(insuranceCompanySn)) {
				currentTermAmount = new BigDecimal(countPrice);// 优惠后保费
			}
			if (StringUtil.isEmpty(price)) {
				addActionError("此操作非法(price is null)！");
				return ERROR;
			}
			logger.info("保费校验：session:{}  页面:{}", totelPrem, productTotalPrice);
			if (StringUtil.isNotEmpty(totelPrem)) {
				String b1 = new BigDecimal(totelPrem).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
				String b2 = new BigDecimal(price).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
				if (!b1.equals(b2)) {
					logger.info("保费校验：session:{}  页面:{}", b1, b2);
					addActionError("此操作非法(price)！");
					return ERROR;
				}
			}

			if ("temptorysave".equals(orderStatus)) {
				order.setOrderStatus(OrderStatus.temptorysave);
			} else {
				// order.setOrderStatus(OrderStatus.prepay);
				// modify guobin 20120911 投保要素页面认为是订单暂存状态
				order.setOrderStatus(OrderStatus.temptorysave);
			}
			BigDecimal paymentFee = new BigDecimal("0");
			information = new Information();// 订单投保界面所有属性
			// orderItem.setProductId(productId);
			// orderItem.setProductName(productName);// 产品名称，详细页或接口获得
			// orderItem.setProductPrice(productTotalPrice);
			// orderItem.setProductQuantity(1);
			// orderItem.setProductHtmlFilePath("");// 产品详细页URL,由产品详细页获得
			// orderItemSet.add(orderItem);
			Order oldOrder = orderService.get(oldOrderId);
			Set<OrderItem> oldOrderItemSet = oldOrder.getOrderItemSet();
			oldOrder.setPaymentStatus(PaymentStatus.unpaid);
			oldOrder.setProductTotalPrice(productTotalPrice);
			oldOrder.setPaidAmount(new BigDecimal("0"));
			oldOrder.setPaymentFee(paymentFee);
			oldOrder.setTotalAmount(productTotalPrice);
			oldOrder.setCurrentTermAmount(currentTermAmount);// 太平洋原始价格存储
			oldOrder.setProductName(productName);
			oldOrder.setInsuranceSn(brkKindCode);
			oldOrder.setProductId(productId);
			oldOrder.setInsuranceCompany(insuranceCompany);
			oldOrder.setInsuranceCompanySn(insuranceCompanySn);
			oldOrder.setOutRiskCode(outRiskCode);
			oldOrder.setSubRiskTypeCode(insurTypeChild);
			oldOrder.seteRiskType(insurType);
			oldOrder.setAmntPrice(new BigDecimal(getAllAmount()));
			oldOrder.setBrkRiskCode(brkRiskCode);
			oldOrder.setBrkRiskName(brkRiskName);
			oldOrder.setDiscountRates(discount);
			orderService.update(oldOrder);
			order = oldOrder;
			orderId = order.getId();
			if ("2011".equals(insuranceCompanySn)) {
				order.setTpySn(PubFun.GetTpySn());// 存入太平洋核保后生成特殊编号
			}
			OrderItem orderItem = null;
			for (OrderItem orderItems : oldOrderItemSet) {
				orderItem = orderItems;
			}
			Set<Information> informationSet = orderItem.getInformationSet();
			orderItem.setProductPrice(order.getProductTotalPrice());
			orderItemService.update(orderItem);

			for (Information infmn : informationSet) {
				information = infmn;
			}
			InformationAppnt ifa = information.getInformationAppnt();
			Set<InformationDuty> informationDutyElementsSet = information.getInformationDutyElementsSet();
			List<InformationInsured> infs = new ArrayList<InformationInsured>(information.getInformationInsuredSet());
			information.setEffective(effective); // 保单起始日期
			information.setFail(DateUtil.setTimeOfDate(fail, 23, 59, 59)); // 保单终止日期
			information.setAllPeriod(period);
			information.setAppType(appType);
			information.setFeeYear(feeYear);
			if ("Y".equals(protectionPeriodTy)) {
				protectionPeriodTy = "y";
			}
			if ("D".equals(protectionPeriodTy) || "".equals(protectionPeriodTy)) {
				protectionPeriodTy = "d";
			}
			if ("M".equals(protectionPeriodTy)) {
				protectionPeriodTy = "m";
			}
			if("A".equals(protectionPeriodTy)){
				protectionPeriodTy = "a";
			}
			information.setProtectionPeriodTy(protectionPeriodTy);
			if ("".equals(protectionPeriodLast)) {
				protectionPeriodLast = "1";
			}
			information.setProtectionPeriod(protectionPeriodLast);
			informationService.update(information);

			ifa = orderConfigService.updateInformationAppnt(ifa, informationAppnt);
			informationAppntService.update(ifa);

			String ordersn = (String) getSession("OrderSn");
			if (StringUtil.isNotEmpty(ordersn)) {
				if (ordersn.indexOf(order.getOrderSn()) == -1) {
					setSession("OrderSn", ordersn + "," + order.getOrderSn());
				}
			} else {
				setSession("OrderSn", order.getOrderSn());
			}
			logger.info("OrderSn放入到session内{}", getSession("OrderSn"));

			InformationInsured infs1 = null;
			if (infs != null && infs.size() > 0) {
				infs1 = infs.get(0);
			}
			informationInsureds1.setDestinationCountryText(getCountryText(informationInsureds1.getDestinationCountry(),insuranceCompanySn));
			this.setInsuredFromAppnt(informationInsureds1, informationAppnt);
			infs1 = orderConfigService.updateInformationInsured(infs1, informationInsureds1);
			informationInsuredService.update(infs1);
			informationInsureds1.setId(infs1.getId());
			Set<InformationInsuredElements> informationInsuredElementsSet = infs1.getInformationInsuredElementsSet();
			informationInsuredElementsUpdate(infs1, informationInsuredElementsSet);
			informationInsuredDutyUpdate(information, informationDutyElementsSet);
			//和众健康告知
			insuredHealthList = new ArrayList<InsuredHealth>(infs1.getInsuredHealthSet());
			if ("temptorysave".equals(orderStatus)) {
				return "tempsucces";// 确定成功页面后跳转用
			} else {
				   //目前只有合众的
				if("1061".equals(insuranceCompanySn)&&insuredHealthList!=null && insuredHealthList.size()>0){
					return "healthy";
				}else{
					showInformaton(order.getId());
					this.setNeedUWCheckFlag(getNeedUWFlagFromDB(order.getInsuranceCompanySn()));
					return "result";
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionError("订单保存失败，请重试！");
			return ERROR;
		}

	}

	
	private void setInsuredFromAppnt(InformationInsured insured, InformationAppnt appnt) {
		if ((null == insured) || (null == appnt)) {
			return;
		}
		if ("true".equals(relationFlag1)) {
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
			if (StringUtil.isNotEmpty(appnt.getApplicantOccupation1())) {

				insured.setOccupation1(appnt.getApplicantOccupation1());
			}
			if (StringUtil.isNotEmpty(appnt.getApplicantOccupation2())) {

				insured.setOccupation2(appnt.getApplicantOccupation2());
			}
			if (StringUtil.isNotEmpty(appnt.getApplicantOccupation3())) {

				insured.setOccupation3(appnt.getApplicantOccupation3());
			}
		}
	}
	
	/**
	 * 修改被保人的产品投保要素
	 */
	private void informationInsuredElementsUpdate(InformationInsured infs1, Set<InformationInsuredElements> informationInsuredElementsSet) {
		try {
			if (informationInsuredElementsSet != null && informationInsuredElementsSet.size() > 0) {
				for (InformationInsuredElements infInsuredElement : informationInsuredElementsSet) {
					informationInsuredElementsService.delete(infInsuredElement);
				}
			}
			riskAppFactior = (List<OrderRiskAppFactor>) getSession("riskAppFactior");
			JSONObject insureJsonJsonArray = JSONObject.fromObject(insureReq);
			if (riskAppFactior != null) {
				for (int i = 0; i < riskAppFactior.size(); i++) {
					String appFactiorType = riskAppFactior.get(i).getFactorType().toString();

					if (!"TextAge".equals(appFactiorType) && riskAppFactior != null && riskAppFactior.get(i) != null && insureJsonJsonArray != null) {
						Object factorValueTemp = insureJsonJsonArray.get(riskAppFactior.get(i).getFactorType());

						InformationInsuredElements informationInsuredElements = new InformationInsuredElements();
						informationInsuredElements.setElementsType(appFactiorType);
						informationInsuredElements.setElementsSn(riskAppFactior.get(i).getAppFactorCode());
						if (factorValueTemp != null)
							informationInsuredElements.setElementsValue(factorValueTemp.toString());

						informationInsuredElements.setInformationInsured(infs1);
						informationInsuredElementsSet.add(informationInsuredElements);
						informationInsuredElementsService.save(informationInsuredElements);

					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	private void informationInsuredDutyUpdate(Information inf, Set<InformationDuty> informationDutyElementsSet) {
		if (informationDutyElementsSet != null && informationDutyElementsSet.size() > 0) {
			for (InformationDuty infDuty : informationDutyElementsSet) {
				informationDutyService.delete(infDuty);
			}
		}
		this.informationInsuredDutySave(inf);
	}
	// 订单信息
	@InputConfig(resultName = "error")
	public String info() {
		// product = productService.load(id);
		// System.out.println("产品是："+product.getName());
		// 游客身份，取购物车列表。
		if (getLoginMember() == null) {
			// cartItemSet
		}
		cartItemSet = getLoginMember().getCartItemSet();
		if (cartItemSet == null || cartItemSet.size() == 0) {
			addActionError("购物车目前没有加入任何商品！");
			return ERROR;
		}
		for (CartItem cartItem : cartItemSet) {
			Product product = cartItem.getProduct();
			// System.out.println("产品是："+product.getName());

			if (product.getStore() != null && (cartItem.getQuantity() + product.getFreezeStore()) > product.getStore()) {
				addActionError("商品库存不足，请返回修改！");
				return ERROR;
			}
		}
		totalQuantity = 0;
		productTotalPrice = new BigDecimal("0");
		for (CartItem cartItem : cartItemSet) {
			totalQuantity += cartItem.getQuantity();
			if (getSystemConfig().getPointType() == PointType.productSet) {
			}
			productTotalPrice = cartItem.getProduct().getPreferentialPrice(getLoginMember()).multiply(new BigDecimal(cartItem.getQuantity().toString())).add(productTotalPrice);

			Product product = cartItem.getProduct();
			Double weightGram = DeliveryType.toWeightGram(product.getWeight(), product.getWeightUnit());
		}
		productTotalPrice = SystemConfigUtil.getOrderScaleBigDecimal(productTotalPrice);
		if (getSystemConfig().getPointType() == PointType.orderAmount) {
		}

		setResponseNoCache();
		return "info";
	}

	// 订单保存
	// @Validations(
	// requiredStrings = {
	// @RequiredStringValidator(fieldName = "deliveryType.id", message =
	// "请选择配送方式！")
	// }
	// )
	@InputConfig(resultName = "error")
	public String save() {
		// product = new Product();
		cartItemSet = getLoginMember().getCartItemSet();
		if (cartItemSet == null || cartItemSet.size() == 0) {
			addActionError("购物车目前没有加入任何商品！");
			return ERROR;
		}

		for (CartItem cartItem : cartItemSet) {
			Product product = cartItem.getProduct();
			if (product.getStore() != null && (cartItem.getQuantity() + product.getFreezeStore() > product.getStore())) {
				addActionError("商品[" + product.getName() + "]库存不足！");
				return ERROR;
			}
		}
		totalQuantity = 0;
		productTotalPrice = new BigDecimal("0");
		for (CartItem cartItem : cartItemSet) {
			Product product = cartItem.getProduct();
			totalQuantity += cartItem.getQuantity();
			productTotalPrice = cartItem.getProduct().getPreferentialPrice(getLoginMember()).multiply(new BigDecimal(cartItem.getQuantity().toString())).add(productTotalPrice);
			Double weightGram = DeliveryType.toWeightGram(product.getWeight(), product.getWeightUnit());
		}
		productTotalPrice = SystemConfigUtil.getOrderScaleBigDecimal(productTotalPrice);

		String paymentConfigName = null;
		BigDecimal paymentFee = null;

		paymentFee = productTotalPrice;

		/**
		 * 购物车有新添加的产品，存入新的orderItem
		 */
		for (CartItem cartItem0 : cartItemSet) {
			order = orderService.getLastOrder();
			if (order != null && !order.getOrderValid()) {
				// product =
				// productService.load(cartItem0.getProduct().getId());
				OrderItem orderItem0 = orderItemService.getByOrP(productId, order.getId());
				if (orderItem0 == null) {
					return "saveitem";
				}

			}
		}
		/**
		 * 订单已经存在，获取上一次订单
		 */

		// for (CartItem cartItem0 : cartItemSet) {
		// product = productService.load(cartItem0.getProduct().getId());
		// order = orderService.getLastOrder();
		//
		// if (order != null) {
		// orderItem = orderItemService.getByOrP(product.getId(), order
		// .getId());
		// information = informationService.getByProduct(product.getId(),
		// order.getId());
		// if ((information != null) && (cartItem0.getValid() == true)) {
		// return "infosave";
		// }
		// }
		// }
		BigDecimal totalAmount = productTotalPrice;

		order = new Order();
		order.setOrderStatus(OrderStatus.unprocessed);
		order.setPaymentStatus(PaymentStatus.unpaid);
		order.setPaymentConfigName(paymentConfigName);
		order.setProductTotalPrice(productTotalPrice);
		order.setPaymentFee(paymentFee);
		order.setTotalAmount(totalAmount);
		order.setPaidAmount(new BigDecimal("0"));
		order.setProductTotalQuantity(totalQuantity);
		// order.setMember(getLoginMember());
		orderService.save(order);
		// ====================================================================================================================================================
		for (CartItem cartItem : cartItemSet) {
			Product product = cartItem.getProduct();
			// ======================属性存入数据库测试=====================================
			ProductInsType productInsType = product.getProductInsType();
			if (productInsType != null) {
				Map<ProductInsAttribute, List<String>> productInsAttributeMap = new HashMap<ProductInsAttribute, List<String>>();
				List<ProductInsAttribute> enabledProductInsAttributeList = productInsAttributeService.getEnabledProductInsAttributeList(productInsType);
				for (ProductInsAttribute productInsAttribute : enabledProductInsAttributeList) {
					String name = productInsAttribute.getName();
					OrderProduct orderProduct = new OrderProduct();
					orderProduct.setProductId(product.getId());
					orderProduct.setProductInsAttrName(name);
					String[] parameterValues = getParameterValues(productInsAttribute.getId());
					orderProduct.setOrderId(order.getOrderSn());
					orderProductService.save(orderProduct);

					if (parameterValues != null && parameterValues.length > 0 && StringUtils.isNotEmpty(parameterValues[0])) {
						productInsAttributeMap.put(productInsAttribute, Arrays.asList(parameterValues));
					}
				}
				product.setProductInsAttributeMap(productInsAttributeMap);
			}
			product.setProductInsType(productInsType);
			// =====================属性存入数据库测试======================================
			// cartItemService.delete(cartItem);
		}
		// ====================================================================================================================================================
		// 商品项
		for (CartItem cartItem : cartItemSet) {
			Product product = cartItem.getProduct();
			OrderItem orderItem = new OrderItem();
			information = new Information();// 订单投保界面所有属性
			orderItem.setProductName(product.getName());
			orderItem.setProductPrice(product.getPreferentialPrice(getLoginMember()));
			orderItem.setProductQuantity(cartItem.getQuantity());
			orderItem.setProductHtmlFilePath(product.getHtmlFilePath());
			orderItem.setOrder(order);
			// orderItem.setProduct(product);

			orderItemService.save(orderItem);
		}

		// 库存处理
		if (getSystemConfig().getStoreFreezeTime() == StoreFreezeTime.order) {
			for (CartItem cartItem : cartItemSet) {
				Product product = cartItem.getProduct();
				if (product.getStore() != null) {
					product.setFreezeStore(product.getFreezeStore() + cartItem.getQuantity());
					if (product.getIsOutOfStock()) {
						Hibernate.initialize(cartItem.getProduct().getProductattrib());
					}
					productService.update(product);
					if (product.getIsOutOfStock()) {
						flushCache();
						htmlService.productContentBuildHtml(product);
					}
				}
			}
		}

		// 订单日志
		OrderLog orderLog = new OrderLog();
		orderLog.setOrderLogType(OrderLogType.create);
		orderLog.setOrderSn(order.getOrderSn());
		orderLog.setOperator(null);
		orderLog.setInfo(null);
		orderLog.setOrder(order);
		orderLogService.save(orderLog);
		return "infosave";
	}

	@InputConfig(resultName = "error")
	public String infosave() {
		order = orderService.load(orderId);
		// product = productService.load(productId);
		orderItem = orderItemService.getByOrP(productId, orderId);
		cartItemSet = getLoginMember().getCartItemSet();

		if (cartItemSet == null || cartItemSet.size() == 0) {
			addActionError("购物车目前没有加入任何商品！");
			return ERROR;
		}
		for (CartItem cartItem0 : cartItemSet) {
			Product product0 = productService.load(cartItem0.getProduct().getId());
			OrderItem orderItem0 = orderItemService.getByOrP(product0.getId(), orderId);
			if (orderItem0 == null) {
				OrderItem orderItem = new OrderItem();
				orderItem.setProductName(product0.getName());
				orderItem.setProductPrice(product0.getPreferentialPrice(getLoginMember()));
				orderItem.setProductQuantity(cartItem0.getQuantity());
				orderItem.setProductHtmlFilePath(product0.getHtmlFilePath());
				orderItem.setOrder(order);
				// orderItem.setProduct(product0);
				orderItemService.save(orderItem);
			}
		}
		// ======================= 附加后期修改=========================
		totalQuantity = 0;
		productTotalPrice = new BigDecimal("0");
		// totalWeightGram = 0D;
		for (CartItem cartItem : cartItemSet) {
			Product product = cartItem.getProduct();
			totalQuantity += cartItem.getQuantity();
			productTotalPrice = cartItem.getProduct().getPreferentialPrice(getLoginMember()).multiply(new BigDecimal(cartItem.getQuantity().toString())).add(productTotalPrice);
			Double weightGram = DeliveryType.toWeightGram(product.getWeight(), product.getWeightUnit());
			// totalWeightGram = ArithUtil.add(totalWeightGram, ArithUtil.mul(
			// weightGram, cartItem.getQuantity()));
		}
		productTotalPrice = SystemConfigUtil.getOrderScaleBigDecimal(productTotalPrice);
		order.setProductTotalPrice(productTotalPrice);
		orderService.update(order);
		return "infosave";
	}

	// 订单信息
	@InputConfig(resultName = "error")
	public String bbxinfo() {
		// product = productService.load(productId);
		order = orderService.load(orderId);
		orderItem = orderItemService.getByOrP(productId, orderId);
		cartItemSet = getLoginMember().getCartItemSet();
		if (cartItemSet == null || cartItemSet.size() == 0) {
			addActionError("购物车目前没有加入任何商品！");
			return ERROR;
		}
		for (CartItem cartItem : cartItemSet) {
			Product product = cartItem.getProduct();

			if (product.getStore() != null && (cartItem.getQuantity() + product.getFreezeStore()) > product.getStore()) {
				addActionError("商品库存不足，请返回修改！");
				return ERROR;
			}
		}
		totalQuantity = 0;
		// totalPoint = 0;
		// totalWeightGram = 0D;
		productTotalPrice = new BigDecimal("0");
		for (CartItem cartItem : cartItemSet) {
			totalQuantity += cartItem.getQuantity();
			if (getSystemConfig().getPointType() == PointType.productSet) {
				// totalPoint = cartItem.getProduct().getPoint()
				// * cartItem.getQuantity() + totalPoint;
			}
			productTotalPrice = cartItem.getProduct().getPreferentialPrice(getLoginMember()).multiply(new BigDecimal(cartItem.getQuantity().toString())).add(productTotalPrice);

			Product product = cartItem.getProduct();
			Double weightGram = DeliveryType.toWeightGram(product.getWeight(), product.getWeightUnit());
			// totalWeightGram = ArithUtil.add(totalWeightGram, ArithUtil.mul(
			// weightGram, cartItem.getQuantity()));
		}
		productTotalPrice = SystemConfigUtil.getOrderScaleBigDecimal(productTotalPrice);
		if (getSystemConfig().getPointType() == PointType.orderAmount) {
			// totalPoint = productTotalPrice
			// .multiply(
			// new BigDecimal(getSystemConfig().getPointScale()
			// .toString()))
			// .setScale(0, RoundingMode.DOWN).intValue();
		}

		setResponseNoCache();
		return "bbxinfo";
	}

	// 投保更改信息
	@InputConfig(resultName = "error")
	public String bbxinfoedit() {
		// product = productService.load(productId);
		order = orderService.load(orderId);
		orderItem = orderItemService.getByOrP(productId, orderId);
		information = informationService.getByProduct(productId, orderId);
		cartItemSet = getLoginMember().getCartItemSet();
		if (cartItemSet == null || cartItemSet.size() == 0) {
			addActionError("购物车目前没有加入任何商品！");
			return ERROR;
		}
		for (CartItem cartItem : cartItemSet) {
			Product product = cartItem.getProduct();
			if (product.getStore() != null && (cartItem.getQuantity() + product.getFreezeStore()) > product.getStore()) {
				addActionError("商品库存不足，请返回修改！");
				return ERROR;
			}
		}
		totalQuantity = 0;
		// totalPoint = 0;
		// totalWeightGram = 0D;
		productTotalPrice = new BigDecimal("0");
		for (CartItem cartItem : cartItemSet) {
			totalQuantity += cartItem.getQuantity();
			if (getSystemConfig().getPointType() == PointType.productSet) {
				// totalPoint = cartItem.getProduct().getPoint()
				// * cartItem.getQuantity() + totalPoint;
			}
			productTotalPrice = cartItem.getProduct().getPreferentialPrice(getLoginMember()).multiply(new BigDecimal(cartItem.getQuantity().toString())).add(productTotalPrice);

			Product product = cartItem.getProduct();
			Double weightGram = DeliveryType.toWeightGram(product.getWeight(), product.getWeightUnit());
			// totalWeightGram = ArithUtil.add(totalWeightGram, ArithUtil.mul(
			// weightGram, cartItem.getQuantity()));
		}
		productTotalPrice = SystemConfigUtil.getOrderScaleBigDecimal(productTotalPrice);
		if (getSystemConfig().getPointType() == PointType.orderAmount) {
			// totalPoint = productTotalPrice
			// .multiply(
			// new BigDecimal(getSystemConfig().getPointScale()
			// .toString()))
			// .setScale(0, RoundingMode.DOWN).intValue();
		}

		setResponseNoCache();
		return "bbxinfoedit";
	}

	public String bbxinfoupdate() {
		information = informationService.getByProduct(productId, orderId);
		// product = productService.load(productId);
		order = orderService.load(orderId);
		orderItem = orderItemService.getByOrP(productId, orderId);
		information.setOrderItem(orderItem);// information保存orderItem
		informationService.update(information);
		orderItem.setValid(true);

		return "bbxresult";
	}

	public void informationAppntSave(Information informations) {
		informationAppnt.setInformation(informations);
		informationAppntService.save(informationAppnt);
	}

	public void informationInsuredSave(Information informations,String comCode) {
		informationInsuredDutySave(informations);
		InformationRiskType informationRiskTypes = new InformationRiskType();
		informationRiskTypes.setId("200001");// 险种，由接口获得
		informationRiskTypeService.save(informationRiskTypes);
		informationInsureds1.getRecognizeeAppntRelation();
		if ("true".equals(relationFlag1)) {
			informationInsureds1.setRecognizeeName(informationAppnt.getApplicantName());
			informationInsureds1.setRecognizeeIdentityType(informationAppnt.getApplicantIdentityType());
			informationInsureds1.setRecognizeeIdentityId(informationAppnt.getApplicantIdentityId());
			informationInsureds1.setRecognizeeIdentityTypeName(informationAppnt.getApplicantIdentityTypeName());
			informationInsureds1.setRecognizeeSex(informationAppnt.getApplicantSex());
			informationInsureds1.setRecognizeeSexName(informationAppnt.getApplicantSexName());
			informationInsureds1.setRecognizeeMail(informationAppnt.getApplicantMail());
			informationInsureds1.setRecognizeeArea1(informationAppnt.getApplicantArea1());
			informationInsureds1.setRecognizeeArea2(informationAppnt.getApplicantArea2());
			informationInsureds1.setRecognizeeBirthday(informationAppnt.getApplicantBirthday());
			informationInsureds1.setRecognizeeMobile(informationAppnt.getApplicantMobile());
			informationInsureds1.setRecognizeeFirstEnName(informationAppnt.getApplicantFirstEnName());
			informationInsureds1.setRecognizeeLastEnName(informationAppnt.getApplicantLastEnName());
			informationInsureds1.setRecognizeeEnName(informationAppnt.getApplicantEnName());
			informationInsureds1.setRecognizeeAddress(informationAppnt.getApplicantAddress());
			informationInsureds1.setRecognizeeZipCode(informationAppnt.getApplicantZipCode());
		}
		informationInsureds1.setInformationInsuredElementsSet(informationInsuredElementsSet);
		informationInsureds1.setInformation(informations);
		informationInsureds1.setDestinationCountryText(getCountryText(informationInsureds1.getDestinationCountry(),comCode));
		if("2011" .equals(comCode)){
			if(StringUtil.isNotEmpty(informationInsureds1.getDestinationCountryText())){
				String CountryText[] = informationInsureds1.getDestinationCountryText().split(",");
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < CountryText.length; i++) {
					String DestinationCountryText = CountryText[i];
					QueryBuilder qb = new QueryBuilder(
					"select codeEnName from dictionary where insurancecode='2011' and codetype='CountryCode' and codename=? ");
					qb.add(DestinationCountryText);
					DestinationCountryText = DestinationCountryText + "/"
					+ (String) qb.executeOneValue();
					if(i == (CountryText.length-1)){
						sb.append(DestinationCountryText);
					}else{
						sb.append(DestinationCountryText + ",");
					}
				}
				informationInsureds1.setDestinationCountryText(sb.toString());
			}
		}
		informationInsuredSet.add(informationInsureds1);
		informationInsuredService.save(informationInsureds1);
		informationInsuredElementsSave(informationInsureds1);
		//JSONObject dutyReqJson = JSONObject.fromObject(dutyReq);
}

	public void informationInsuredElementsSave(InformationInsured informationInsured) {
		riskAppFactior = (List<OrderRiskAppFactor>) getSession("riskAppFactior");
		dutyFactor = (List<OrderDutyFactor>) getSession("dutyFactor");
		List<OrderDutyFactor> dutyFactorLast = new ArrayList<OrderDutyFactor>();
		JSONObject insureJsonJsonArray = JSONObject.fromObject(insureReq);
		if (riskAppFactior != null) {
			for (int i = 0; i < riskAppFactior.size(); i++) {
				// OrderRiskAppFactor orderRiskAppFactor =
				// riskAppFactior.get(i);
				String appFactiorType = riskAppFactior.get(i).getFactorType().toString();

				if (!"TextAge".equals(appFactiorType) && riskAppFactior != null && riskAppFactior.get(i) != null && insureJsonJsonArray != null) {
					Object factorValueTemp = insureJsonJsonArray.get(riskAppFactior.get(i).getFactorType());

					InformationInsuredElements informationInsuredElements = new InformationInsuredElements();
					informationInsuredElements.setElementsType(appFactiorType);
					if (factorValueTemp != null)
						informationInsuredElements.setElementsValue(factorValueTemp.toString());

					informationInsuredElements.setInformationInsured(informationInsured);
					informationInsuredElementsSet.add(informationInsuredElements);
					informationInsuredElementsService.save(informationInsuredElements);

				}
			}
		}
	}

	public void informationInsuredDutySave(Information inf) {
		riskAppFactior = (List<OrderRiskAppFactor>) getSession("riskAppFactior");
		dutyFactor = (List<OrderDutyFactor>) getSession("dutyFactor");
		JSONObject dutyJsonArray = JSONObject.fromObject(dutyReq);
		JSONObject showDutyJsonArray = JSONObject.fromObject(dutyDisReq);
		JSONObject premJsonArray = JSONObject.fromObject(dutyPremReq);
		// System.out.println("=====" + dutyReq);
		if (dutyFactor != null) {
			for (int i = 0; i < dutyFactor.size(); i++) {
				// OrderRiskAppFactor orderRiskAppFactor =
				// riskAppFactior.get(i);
				Object factorValueTemp = dutyJsonArray.get(dutyFactor.get(i).getDutyCode());
				Object showDutyValue = showDutyJsonArray.get(dutyFactor.get(i).getDutyCode());
				InformationDuty informationDuty = new InformationDuty();
				informationDuty.setDutySn(dutyFactor.get(i).getDutyCode());
				informationDuty.setDutyName(dutyFactor.get(i).getDudtyFactorName());
				informationDuty.setDutyFullName(dutyFactor.get(i).getDutyFullName());
				informationDuty.setAmnt(factorValueTemp + "");
				informationDuty.setCoverage(dutyFactor.get(i).getDefine());
//				informationDuty.setA(dutyFactor.get(i).getA());
				if(!premJsonArray.isNullObject()){
					Object premDutyValue = premJsonArray.get(dutyFactor.get(i).getDutyCode());
					if(!"nvalue".equals(premDutyValue)){
						informationDuty.setTimePrem(premDutyValue+"");
					}else{
						informationDuty.setTimePrem("0.0");
					}
				}
				informationDuty.setOrderId(orderId);
				informationDuty.setShowAmnt(showDutyValue + "");
				informationDuty.setInformation(inf);
				informationDutyElementsSet.add(informationDuty);
				informationDutyService.save(informationDuty);
			}
		}
	}
	private double getAllAmount(){
		dutyFactor = (List<OrderDutyFactor>) getSession("dutyFactor");
		double amnt = 0.0;
		JSONObject dutyJsonArray = JSONObject.fromObject(dutyReq);
		// System.out.println("=====" + dutyReq);
		if (dutyFactor != null) {
			for (int i = 0; i < dutyFactor.size(); i++) {
				Object factorValueTemp = dutyJsonArray.get(dutyFactor.get(i).getDutyCode());
				if(factorValueTemp!=null && !"".equals(factorValueTemp)){
					amnt = amnt + Double.parseDouble(factorValueTemp.toString());
				}
			}
		}
		return amnt;
	}

	// 支付
	public String result() {
		order = orderService.load(orderId);
		order.setOrderValid(true);
		orderService.update(order);
		// cartItemSet = getLoginMember().getCartItemSet();
		// for (CartItem cartItem0 : cartItemSet) {
		// product = productService.load(cartItem0.getProduct().getId());
		// information = informationService.getByProduct(product.getId(),
		// order.getId());
		// if ((information == null) || (cartItem0.getValid() == false)) {
		// orderItem = orderItemService.getByOrP(product.getId(), orderId);
		// return "errorresult";
		// }
		// }
		// product = productService.load(productId);
		orderItem = orderItemService.getByOrP(productId, orderId);
		// for (CartItem cartItem0 : cartItemSet) {
		// information = informationService.getByProduct(product.getId(),
		// order.getId());
		// if ((information == null) || (cartItem0.getValid() == false)) {
		// return "errorresult";
		// }
		// cartItemService.delete(cartItem0);
		//
		// }
		// information = informationService.getByProduct(product.getId(),
		// order.getId());
		this.setNeedUWCheckFlag(getNeedUWFlagFromDB(order.getInsuranceCompanySn()));
		return "result";
	}

	// 购物车中订单保存
	// @Validations(
	// requiredStrings = {
	// @RequiredStringValidator(fieldName = "deliveryType.id", message =
	// "请选择配送方式！")
	// }
	// )
	@InputConfig(resultName = "error")
	public String saveCartOrder() {
		// product = new Product();
		cartItemSet = getLoginMember().getCartItemSet();
		if (cartItemSet == null || cartItemSet.size() == 0) {
			addActionError("购物车目前没有加入任何商品！");
			return ERROR;
		}

		for (CartItem cartItem : cartItemSet) {
			Product product = cartItem.getProduct();
			if (product.getStore() != null && (cartItem.getQuantity() + product.getFreezeStore() > product.getStore())) {
				addActionError("商品[" + product.getName() + "]库存不足！");
				return ERROR;
			}
		}
		totalQuantity = 0;
		productTotalPrice = new BigDecimal("0");
		// totalWeightGram = 0D;
		for (CartItem cartItem : cartItemSet) {
			Product product = cartItem.getProduct();
			totalQuantity += cartItem.getQuantity();
			productTotalPrice = cartItem.getProduct().getPreferentialPrice(getLoginMember()).multiply(new BigDecimal(cartItem.getQuantity().toString())).add(productTotalPrice);
			Double weightGram = DeliveryType.toWeightGram(product.getWeight(), product.getWeightUnit());
			// totalWeightGram = ArithUtil.add(totalWeightGram, ArithUtil.mul(
			// weightGram, cartItem.getQuantity()));
			// cartItemService.delete(cartItem);
		}
		productTotalPrice = SystemConfigUtil.getOrderScaleBigDecimal(productTotalPrice);

		String paymentConfigName = null;
		BigDecimal paymentFee = null;
		paymentFee = productTotalPrice;

		/**
		 * 购物车有新添加的产品，存入新的orderItem
		 */
		for (CartItem cartItem0 : cartItemSet) {
			order = orderService.getLastOrder();
			if (order != null && !order.getOrderValid()) {
				// product =
				// productService.load(cartItem0.getProduct().getId());
				// OrderItem orderItem0 = orderItemService.getByOrP(product
				// .getId(), order.getId());
				// if (orderItem0 == null) {
				// return "saveitem";
				// }

			}
		}
		/**
		 * 订单已经存在，获取上一次订单
		 */

		// for (CartItem cartItem0 : cartItemSet) {
		// product = productService.load(cartItem0.getProduct().getId());
		// order = orderService.getLastOrder();
		//
		// if (order != null) {
		// orderItem = orderItemService.getByOrP(product.getId(), order
		// .getId());
		// information = informationService.getByProduct(product.getId(),
		// order.getId());
		// if ((information != null) && (cartItem0.getValid() == true)) {
		// return "infosave";
		// }
		// }
		// }
		BigDecimal totalAmount = productTotalPrice;

		order = new Order();
		order.setOrderStatus(OrderStatus.unprocessed);
		order.setPaymentStatus(PaymentStatus.unpaid);
		order.setPaymentConfigName(paymentConfigName);
		order.setProductTotalPrice(productTotalPrice);
		order.setPaymentFee(paymentFee);
		order.setTotalAmount(totalAmount);
		order.setPaidAmount(new BigDecimal("0"));
		order.setProductTotalQuantity(totalQuantity);
		// order.setMember(getLoginMember());
		orderService.save(order);
		// ====================================================================================================================================================
		for (CartItem cartItem : cartItemSet) {
			Product product = cartItem.getProduct();
			// ======================属性存入数据库测试=====================================
			ProductInsType productInsType = product.getProductInsType();
			if (productInsType != null) {
				Map<ProductInsAttribute, List<String>> productInsAttributeMap = new HashMap<ProductInsAttribute, List<String>>();
				List<ProductInsAttribute> enabledProductInsAttributeList = productInsAttributeService.getEnabledProductInsAttributeList(productInsType);
				for (ProductInsAttribute productInsAttribute : enabledProductInsAttributeList) {
					String name = productInsAttribute.getName();
					OrderProduct orderProduct = new OrderProduct();
					orderProduct.setProductId(product.getId());
					orderProduct.setProductInsAttrName(name);
					String[] parameterValues = getParameterValues(productInsAttribute.getId());
					orderProduct.setOrderId(order.getOrderSn());
					orderProductService.save(orderProduct);

					if (parameterValues != null && parameterValues.length > 0 && StringUtils.isNotEmpty(parameterValues[0])) {
						productInsAttributeMap.put(productInsAttribute, Arrays.asList(parameterValues));
					}
				}
				product.setProductInsAttributeMap(productInsAttributeMap);
			}
			product.setProductInsType(productInsType);
			// =====================属性存入数据库测试======================================
			// cartItemService.delete(cartItem);
		}
		// ====================================================================================================================================================
		// 商品项
		for (CartItem cartItem : cartItemSet) {
			Product product = cartItem.getProduct();
			OrderItem orderItem = new OrderItem();
			information = new Information();// 订单投保界面所有属性
			orderItem.setProductName(product.getName());
			orderItem.setProductPrice(product.getPreferentialPrice(getLoginMember()));
			orderItem.setProductQuantity(cartItem.getQuantity());
			orderItem.setProductHtmlFilePath(product.getHtmlFilePath());
			orderItem.setOrder(order);
			// orderItem.setProduct(product);

			// //保存每个产品的保存投保人和被保人信息结束

			orderItemService.save(orderItem);

			// informationService.save(information);

		}

		// 库存处理
		if (getSystemConfig().getStoreFreezeTime() == StoreFreezeTime.order) {
			for (CartItem cartItem : cartItemSet) {
				Product product = cartItem.getProduct();
				if (product.getStore() != null) {
					product.setFreezeStore(product.getFreezeStore() + cartItem.getQuantity());
					if (product.getIsOutOfStock()) {
						Hibernate.initialize(cartItem.getProduct().getProductattrib());
					}
					productService.update(product);
					if (product.getIsOutOfStock()) {
						flushCache();
						htmlService.productContentBuildHtml(product);
					}
				}
			}
		}

		// 订单日志
		OrderLog orderLog = new OrderLog();
		orderLog.setOrderLogType(OrderLogType.create);
		orderLog.setOrderSn(order.getOrderSn());
		orderLog.setOperator(null);
		orderLog.setInfo(null);
		orderLog.setOrder(order);
		orderLogService.save(orderLog);
		return "infosave";
	}

	// 订单详情
	public String view() {
		order = orderService.load(id);
		// totalPoint = 0;
		if (getSystemConfig().getPointType() == PointType.productSet) {
			// for (OrderItem orderItem : order.getOrderItemSet()) {
			// totalPoint = orderItem.getProduct().getPoint()
			// * orderItem.getProductQuantity() + totalPoint;
			// }
		} else if (getSystemConfig().getPointType() == PointType.orderAmount) {
			// totalPoint = order.getProductTotalPrice()
			// .multiply(
			// new BigDecimal(getSystemConfig().getPointScale()
			// .toString()))
			// .setScale(0, RoundingMode.DOWN).intValue();
		}
		return "view"; 
	}

	// 更新页面缓存
	private void flushCache() {
		Cache cache = ServletCacheAdministrator.getInstance(getRequest().getSession().getServletContext()).getCache(getRequest(), PageContext.APPLICATION_SCOPE);
		cache.flushAll(new Date());
	}

	/**
	 * 
	 * @return 根据条件筛选查询的结果
	 */
	public String list() {
		if (getLoginMember() == null) {
			addActionError("请先登录");
			return ERROR;
		}
		String memberId = getLoginMember().getId();
		String sql = "select a.id, a.createdate, e.codename, a.orderStatus , a.totalamount,d.productname,d.ispublish ,a.orderSn ,a.insuranceCompanySn" + ",(case when exists(select 1 from zdcode where codetype='ConfigInput' and parentcode='ConfigInput' and codevalue=a.insuranceCompanySn) then '1' else '0' end) as configFlag"
				+ ",(case when exists(select 1 from zdcode where codetype='UWCheckClassName' and parentcode='UWCheckClassName' and codevalue=a.insuranceCompanySn) then '1' else '0' end) as needUWCheckFlag  from orders a, orderitem b,information c, sdproduct d ,zdcode e,informationappnt f" + " where a.id=b.order_id and b.productid=d.productid "
		+ " and b.id=c.orderitem_id  and f.information_id = c.id and a.memberid=? " + " and e.codevalue= a.orderstatus and e.codetype='orderstatus' and e.parentcode='status'";
		String sqlPart = "";
		if (ldate != null && !"".equals(ldate)) {
			sqlPart = sqlPart + "and a.createdate >= '" + ldate + " 00:00:00'";
		}
		if (hdate != null && !"".equals(hdate)) {
			sqlPart = sqlPart + "and a.createdate <= '" + hdate + " 23:59:59'";
		}
		if (orderStatus != null && !"".equals(orderStatus) && !"99".equals(orderStatus)) {
			sqlPart = sqlPart + " and  e.codevalue='" + orderStatus + "'";
		}
		if (orderSn != null && !"".equals(orderSn)) {
			sqlPart = sqlPart + "and a.orderSn='" + orderSn + "'";
		}
		if (applicant != null && !"".equals(applicant)) {
			sqlPart = sqlPart + "and f.applicantname='" + applicant + "'";
		}
		String sqlPart2 = " order by createDate desc limit " + (page - 1) * pagesize + "," + pagesize;
		String sql3 =sql + sqlPart;
		sql = sql + sqlPart + sqlPart2;
		String[] temp = {memberId};

		// System.out.println("******************:" + sql2);
		JdbcTemplateData jtd = new JdbcTemplateData();

		try {
			listOrders = jtd.obtainData(sql3,temp);
			count = listOrders.size();
			listOrders = jtd.obtainData(sql,temp);
			for(Map map:listOrders){
				map.put("KID", StringUtil.md5Hex(this.request.getSession().getId()+map.get("orderSn"))) ; 
			} 
			this.lastpage = ((count + Order.DEFAULT_ORDER_LIST_PAGE_SIZE - 1) / (Order.DEFAULT_ORDER_LIST_PAGE_SIZE));
		} catch (InstantiationException e) {
			logger.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ActionUtil.dealAction("wj00038", map, getRequest());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			return "list";
		}

	}

	public List<Map> getListOrders() {
		return listOrders;
	}

	public void setListOrders(List<Map> listOrders) {
		this.listOrders = listOrders;
	}

	public String update() {
		order.setOrderStatus(OrderStatus.autoinvalid);
		orderService.update(order);
		return list();
	}

	/**
	 * 
	 * @return 根据被保人生日计算年龄
	 */
	public Integer informationAge(String birthDay) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 得到当前的年份
		String cYear = sdf.format(new Date()).substring(0, 4);
		// 得到生日年份
		String birthYear = birthDay.substring(0, 4);
		int age = Integer.parseInt(cYear) - Integer.parseInt(birthYear);
		return age;
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
			// 投保类别列表
			if ("TestAge".equals(orderRiskAppFactor.getFactorType())) {
				testAgeList.addAll(orderRiskAppFactor.getFactorValue());
			}
			// 保险责任多类人群列表
			if ("MulPeople".equals(orderRiskAppFactor.getFactorType())) {
				mulPeopleList.addAll(orderRiskAppFactor.getFactorValue());
			}
			if ("Amnt".equals(orderRiskAppFactor.getFactorType())) {
				amntList.addAll(orderRiskAppFactor.getFactorValue());
			}
		}
	}

	/**
	 * 调用保费试算
	 * 
	 */
	public String getPremium() {
		double totlePrem = 0.0;
		JSONObject jsonArray = JSONObject.fromObject(totlePrem);
		String jsonstr = jsonArray.toString();
		return ajax(jsonstr, "text/html");
	}

	/**
	 * 职业校验
	 * 
	 * @return
	 */
	public String ajaxOccup() {
		logger.info("职业校验编码：{} 产品id：{}", occupation3, productId);
		if (validateOccup(occupation3, productId)) {
			return ajaxJson("true");
		} else {
			return ajaxJson("false");
		}
	}

	private boolean validateOccup(String occupId, String productId) {
		try {
			String sql = "select grade from occupation where id='" + occupId + "'";
			GetDBdata db = new GetDBdata();
			String grade = db.getOneValue(sql);
			if (StringUtil.isEmpty(grade)) {
				logger.warn("职业编码为空无验证");
				return false;
			} else {
				int start, end = 0;
				String sql_occup = "select occup from sdproduct where productid=?";
				String[] sql_occupTemp = {productId};
				String occup = db.getOneValue(sql_occup,sql_occupTemp);
				// 没有限制职业
				if (StringUtil.isEmpty(occup)) {
					return true;
				} else {
					if (occup.indexOf("-") != -1) {
						String[] op = occup.split("-");
						start = Integer.parseInt(op[0]);
						end = Integer.parseInt(op[1]);
					} else {
						start = end = Integer.parseInt(occup);
					}
				}
				int g = Integer.parseInt(grade);
				Object[] argArr = {g, start, end};
				logger.info("职业校验比较：选择的职业等级{} 规定的等级：{} 规定的等级：{}", argArr);
				if (g >= start && g <= end) {
					return true;

				} else {
					return false;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * 年龄校验
	 * 
	 * @return
	 */
	public String ajaxAge() {
		logger.info("生日编码：{} 产品id：{}",applicantBirthday, productId);
		if (validateAge(applicantBirthday, productId,effective)) {
			return ajaxJson("true");
		} else {
			return ajaxJson("false");
			
			
		}
	}

	private boolean validateAge(String BirthdayId, String productId,Date effective) {
		try {
			GetDBdata db = new GetDBdata();
			int start, end = 0;
			String sql_sectionage = "select sectionage from sdproduct where productid=?";
			String[] sql_sectionageTemp = {productId};
			String sectionage = db.getOneValue(sql_sectionage,sql_sectionageTemp);
			ArrayList<Map<String, Integer>> list = new ArrayList<Map<String, Integer>>();
			Map<String, Integer> map = new HashMap<String, Integer>();
			
			// 没有限制年龄
			if (StringUtil.isEmpty(sectionage)) {
				return true;
			} else {
				// 多个断段间
				if (sectionage.indexOf("|") != -1) {
					String[] op = sectionage.split("\\|");
					for (int i = 0; i < op.length; i++) {
						map = new HashMap<String, Integer>();
						if (op[i].indexOf("-") != -1) {
							String[] age = op[i].split("-");
							map.put("start", Integer.parseInt(age[0].replace("Y", "")));
							map.put("end", Integer.parseInt(age[1].replace("Y", "")));
							list.add(map);
						}else{
							map.put("start", Integer.parseInt(op[i].replace("Y", "")));
							map.put("end", Integer.parseInt(op[i].replace("Y", "")));
							list.add(map);
						}
					}
				} else {
					if (sectionage.indexOf("-") != -1) {
						String[] age = sectionage.split("-");
						map.put("start", Integer.parseInt(age[0].replace("Y", "")));
						map.put("end", Integer.parseInt(age[1].replace("Y", "")));
						list.add(map);
					} else {
						map.put("start", Integer.parseInt(sectionage.replace("Y", "")));
						map.put("end", Integer.parseInt(sectionage.replace("Y", "")));
						list.add(map);
					}
				}
			}
			int g = com.sinosoft.sms.messageinterface.pubfun.PubFun.getAge(BirthdayId,effective);

			logger.info("年龄校验比较：选择的年龄{} 规定的年龄：{}", g, sectionage);
			boolean flag = false;
			for (Map<String, Integer> map1 : list) {
				start = map1.get("start");
				end = map1.get("end");
				if (g >= start && g <= end) {
					return true;
				} else {
					flag = false;
				}
			}
			return flag;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

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

		if (StringUtil.isNotEmpty(productID) && StringUtil.isNotEmpty(productID_)) {
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
				Object factorValueTemp = insureJsonJsonArray.get(riskAppFactior.get(i).getFactorType());
				if ("".equals(factorValueTemp) || factorValueTemp == null) {
					riskAppFactior.get(i).getFactorValue().get(0);
					List<FEMRiskFactorList> factorValue = new ArrayList<FEMRiskFactorList>();
					FEMRiskFactorList riskFactor = new FEMRiskFactorList();
					if ("".equals(textage)) {
						riskFactor.setFactorValue("1991-01-01");
					} else {
						riskFactor.setFactorValue(factorValueTemp.toString());
					}
					riskFactor.setAppFactorCode(riskAppFactior.get(i).getAppFactorCode());
					riskFactor.setFactorType(riskAppFactior.get(i).getFactorType());
					riskFactor.setIsPremCalFacotor(riskAppFactior.get(i).getIsPremCalFacotor());
					factorValue.add(riskFactor);
					riskAppFactior.get(i).setFactorValue(factorValue);
				} else {
					riskAppFactior.get(i).getFactorValue().get(0);
					List<FEMRiskFactorList> factorValue = new ArrayList<FEMRiskFactorList>();
					FEMRiskFactorList riskFactor = new FEMRiskFactorList();
					if (factorValueTemp != null)
						riskFactor.setFactorValue(factorValueTemp.toString());
					riskFactor.setAppFactorCode(riskAppFactior.get(i).getAppFactorCode());
					riskFactor.setFactorType(riskAppFactior.get(i).getFactorType());
					riskFactor.setIsPremCalFacotor(riskAppFactior.get(i).getIsPremCalFacotor());
					factorValue.add(riskFactor);
					riskAppFactior.get(i).setFactorValue(factorValue);
				}
			} else {
				Object factorValueTemp = insureJsonJsonArray.get(riskAppFactior.get(i).getFactorType());
				riskAppFactior.get(i).getFactorValue().get(0);
				List<FEMRiskFactorList> factorValue = new ArrayList<FEMRiskFactorList>();
				FEMRiskFactorList riskFactor = new FEMRiskFactorList();
				if (factorValueTemp != null)
					riskFactor.setFactorValue(factorValueTemp.toString());
				riskFactor.setAppFactorCode(riskAppFactior.get(i).getAppFactorCode());
				riskFactor.setFactorType(riskAppFactior.get(i).getFactorType());
				riskFactor.setIsPremCalFacotor(riskAppFactior.get(i).getIsPremCalFacotor());
				factorValue.add(riskFactor);
				riskAppFactior.get(i).setFactorValue(factorValue);
			}
		}

		JSONObject dutyJsonJsonArray = JSONObject.fromObject(dutyJson);
		List<OrderDutyFactor> dutyFactorLast = new ArrayList<OrderDutyFactor>();
		for (OrderDutyFactor orderDutyFactor : dutyFactor) {
			if(orderDutyFactor!=null && orderDutyFactor.getDutyCode()!=null){
				String dutyValueTemp = dutyJsonJsonArray.get(orderDutyFactor.getDutyCode()).toString();
				dutyFactorLast.add(orderService.getNewDutyFactor(orderDutyFactor, dutyValueTemp));
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
		double countPrice = (Double) mapPrem.get("countPrice");
		String retTotlePrem = String.valueOf(totlePrem);
		String retCountPrem = String.valueOf(countPrice);
		setSession("TotelPrem_" + pID, retCountPrem);
		Map<String, Object> price = new HashMap<String, Object>();
		price.put("retTotlePrem", retCountPrem);// 折扣保费
		price.put("retCountPrem", retTotlePrem);// 原价
		price.put("retDutyAmounts", mapPrem.get("dutyAmounts"));// 责任明细
		JSONObject jsonObject = JSONObject.fromObject(price);
		return ajax(jsonObject.toString(), "text/html");
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
			// System.out.println("=====子集大小========" + childerns.size() +
			// "====子集=====" + jsonString);
			return ajaxJson(jsonString);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return ajaxJson("");
	}

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

	// 链接继续录入页面
	public String keepInput() {
		try {
			order = orderService.getOrderByOrderSn(orderSn);
			if (order == null) {
				addActionError("系统中无该订单信息啊!");
				return ERROR;
			}
			
			/* zhangjinquan 11180 2012-10-10 增加产品下架判断 start */
			if (ERROR.equals(isProductDownShelf(order.getProductId(), order.getProductName(), "继续录入",order))) {
				/* 下架产品状态自动修改为自动取消 */
				/*判断产品*/
				
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
			order = orderService.get(orderId);
			String orderSat = order.getOrderStatus()+"";
			if("paid".equals(orderSat)){
				return orderStatusPaidCheck(order);
			}else{
				orderService.delete(orderId);
				return "orderRevoke";
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ERROR;
		}
	}

	public String pay() {
		
		
		order = orderService.getOrderByOrderSn(orderSn);
		member = getLoginMember();
		
		if(order!=null){
			
			//在已支付的情况下
			if (String.valueOf(order.getOrderStatus().ordinal()).equals("7")) {
				// 会员已经登录
				if (member != null) {
					// 会员已经登录,且支付完成，跳转到支付成功页面；
					order = orderService.getOrderByOrderSn(orderSn);
					String memberId = order.getMemberId();
					//如果已登录会员和购买会员不同，跳转到
					if(!memberId.equals(member.getId())){		
						return "memberCenter";
					}
					tradeInformation = tradeInformationService.isPayFinnish(orderSn);
					return "callsuccess";
				} 
				// 会员未登录
				else {	
//					String redirectionJudge = request.getRequestURL().toString();
					GetDBdata db = new GetDBdata();
					String sql = "select value from zdconfig where type= 'ServerContext'";	
					try {
						String path= db.getOneValue(sql);
						String redirectionJudge = path + "/shop/order!pay.action?orderSn=" + orderSn + "&payType=0";
						request.getSession().setAttribute(Member.LOGIN_REDIRECTION_URL_SESSION_NAME, redirectionJudge);
						judge = "judge";
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
					// 会员已经登录,且支付完成，跳转到登录页面；
					return "login";
				}
			}
			if(!String.valueOf(order.getOrderStatus().ordinal()).equals("5")){
				String nKID = StringUtil.md5Hex(this.request.getSession().getId()+orderSn);
				if(!nKID.equals(KID)){
					addActionError("请登陆后，进行此操作！");
					return "login";
				} 
			} 
		}
		
		/* zhangjinquan 11180 2012-10-10 增加产品下架判断 start */
		if (ERROR.equals(isProductDownShelf(order.getProductId(), order.getProductName(), "继续支付",order))) {
			/* 下架产品状态自动修改为自动取消 */
		

			if(!order.getOrderStatus().equals(OrderStatus.paid)){
				order.setOrderStatus(OrderStatus.autoinvalid);
				orderService.update(order);
				}
				return ERROR;
		
		}
		//判断支付日期是否已过
		if(ERROR.equals(isOutPeriod(order))){
			return ERROR;
		};
		outRiskCode = order.getInsuranceCompanySn();
		GetDBdata db = new GetDBdata();
		/* zhangjinquan 11180 2012-10-10 增加产品下架判断 end */
		String orderSat = order.getOrderStatus()+"";
		if("paid".equals(orderSat)){
			return orderStatusPaidCheck(order);
		}else{
			if (orderSn == null || order == null) {
				addActionError("订单号或订单为空!");
				return ERROR;
			}
			// modify guobin 20120911 订单预览确认支付页面认为是订单待支付状态
			order.setOrderStatus(OrderStatus.prepay);
			orderService.update(order);
			
			String sql3 = "select applicantName from informationappnt where information_id in(select id from information where orderItem_id in (select id from orderitem where order_id in(select id from orders where ordersn=?)))";
			String[] sql3Temp = {orderSn};
			
			try {
				applicantName = db.getOneValue(sql3,sql3Temp);
				bank1 = payBaseService.getPayBaseList("1");
				bank2 = payBaseService.getPayBaseList("2");
				bank3 = payBaseService.getPayBaseList("3");
				
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return ERROR;
			}
			try {
				String sql4 = "select applicantMail from informationappnt where information_id in(select id from information where orderItem_id in (select id from orderitem where order_id in(select id from orders where ordersn=?)))";
				String[] sql4Temp = {orderSn};
				String sql = "select value from zdconfig where type= 'payUrl '";
				
				String path = db.getOneValue(sql);
				ActionUtil actionUtil = new ActionUtil();
				Member member = new Member();
				member.setEmail(db.getOneValue(sql4,sql4Temp));
				sdinteraction.put("Member", member);
				sdinteraction.put("MemberName", applicantName);
				sdinteraction.put("OrderSn", order.getOrderSn());
				sdinteraction.put("ProductName", order.getProductName());
				sdinteraction.put("path", (path + "?orderSn=" + order.getOrderSn() + "&payType=0").replace("&", "#"));
				if (StringUtil.isEmpty(payType)) {
					
					actionUtil.dealAction("wj00015", sdinteraction, request);
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			
			String ordersn = (String) getSession("OrderSn");
			if (StringUtil.isNotEmpty(ordersn)) {
				if (ordersn.indexOf(order.getOrderSn()) == -1) {
					setSession("OrderSn", ordersn + "," + order.getOrderSn());
				}
			} else {
				setSession("OrderSn", order.getOrderSn());
			}
			logger.info("OrderSn放入到session内{}", getSession("OrderSn"));
			
			return "pay";
		}
	}

	/**
	 * 支付前核保
	 */
	public String tpyCheckPay() {
		order = orderService.getOrderByOrderSn(orderSn);
		if(ERROR.equals(isOutPeriod(order))){
			return ERROR;
		};
		KID=StringUtil.md5Hex(this.request.getSession().getId()+orderSn);
		String orderSat = order.getOrderStatus()+"";
		if("paid".equals(orderSat)){
			return orderStatusPaidCheck(order);
		}else{
			return "tpyCheckInsure";
		}
	}

	/**
	 * 支付前核保
	 */
	public String checkInsurePay() {
		order = orderService.getOrderByOrderSn(orderSn);
		KID=StringUtil.md5Hex(this.request.getSession().getId()+orderSn);
		try
		{
			String sql = "select Memo from zdcode where CodeType=? and ParentCode=? and CodeValue=?";
			QueryBuilder qb = new QueryBuilder(sql);
			qb.add("UWCheckClassName");
			qb.add("UWCheckClassName");
			qb.add(order.getInsuranceCompanySn());
			String className = qb.executeString();
			if (StringUtil.isEmpty(className))
			{
				logger.error("核保异常，没有配置核保类路径！");
				String passFlags = "{passFlag:'nopass',rtnMessage:'noRtnMssage'}";
				JSONObject jsonArrays = JSONObject.fromObject(passFlags);
				String jsonstrs = jsonArrays.toString();
				return ajaxJson(jsonstrs);
			}
			Class<?> uwCheckClass = Class.forName(className);
			UWCheckInterface uwCheck = (UWCheckInterface)uwCheckClass.newInstance();
			return uwCheck.dealData(this);
		}
		catch (ClassNotFoundException e)
		{
			logger.error(e.getMessage(), e);
		}
		catch (SecurityException e)
		{
			logger.error(e.getMessage(), e);
		}
		catch (IllegalArgumentException e)
		{
			logger.error(e.getMessage(), e);
		}
		catch (IllegalAccessException e)
		{
			logger.error(e.getMessage(), e);
		}
		catch (InstantiationException e)
		{
			logger.error(e.getMessage(), e);
		}
		
		logger.error("调用核保类时发生异常！");
		String passFlags = "{passFlag:'nopass',rtnMessage:'noRtnMssage'}";
		JSONObject jsonArrays = JSONObject.fromObject(passFlags);
		String jsonstrs = jsonArrays.toString();
		return ajaxJson(jsonstrs);
	}
	
	public OrderService getOrderService()
	{
		return this.orderService;
	}
	// 核保接口模拟
	public Map<String, String> checkInsure(String orderSn) {
		Map<String, String> resMap = new HashMap<String, String>();
		resMap.put("passFlag", "nopass");// 核保是否通过请返回pass或nopass
		resMap.put("tpySysSn", "TPYSYS000001");// 核保返回的太平洋系统订单号
		resMap.put("tpySysPaySn", "TPYPAY000001");// 核保返回的太平洋系统支付流水号
		return resMap;
	}

	/**
	 * 生成订单号，12位时间戳（截取后12位）+4位随机数
	 */
	public String getOrderSnRad() {
		Date d = new Date();
		Long orderTimeSnBeg = d.getTime();// 订单号前半
		String orderSnBeg = getSplits(Long.toString(orderTimeSnBeg), 12);
		logger.info("-------前-----{}", orderSnBeg);
		Random rand = new Random();
		Integer ran = rand.nextInt(9999); // 订单号后半
		if (ran < 10) {
			ran = ran * 1000;
		} else if (ran < 100 && ran > 9) {
			ran = ran * 100;
		} else if (ran < 1000 && ran > 99) {
			ran = ran * 10;
		} else {
			ran = 0000;
		}
		logger.info("-------后-----{}", ran);
		String OrderSnRad = orderSnBeg + Integer.toString(ran);
		return OrderSnRad;
	}
	/**
	 * 根据旅游目的地编码查询目的地名称
	 */
	private String getCountryText(String destinationCountry,
			String comcode) {
		StringBuffer sb = new StringBuffer();
		String countryText = "";
		try {
			if (destinationCountry != null && !"".equals(destinationCountry)) {
				JdbcTemplateData jtd = new JdbcTemplateData();
				String[] destinate = destinationCountry.split(",");
				if (destinate != null && destinate.length > 0) {
					int kk = 0;
					for (kk = 0; kk < destinate.length; kk++) {
						String sqlCountryCodess = "select codename from dictionary where codetype=? and codevalue = ? and insuranceCode=?";
						String[] sqlCountryCodesstemp = {"CountryCode",destinate[kk].trim(),comcode};
						List<Map> dlist = new ArrayList<Map>();
						dlist = jtd.obtainData(sqlCountryCodess,sqlCountryCodesstemp);
						Iterator<Map> it = dlist.iterator();
						while (it.hasNext()) {
							Map map = it.next();
							if(map.get("codeName")!=null && !"".equals((String) map.get("codeName"))){
								sb.append((String) map.get("codeName")+",");
							}
						}
					}
				}
				if(sb!=null && sb.length()>0){
					String s = sb.toString();
					countryText = s.substring(0,s.length()-1);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} 
		return countryText;
	}
	/**
	 * 截字符串
	 */
	public String getSplits(String tempStr, int num) {
		return tempStr.substring(tempStr.length() - num);
	}

	public String[] getBaseInformations() {
		return baseInformations;
	}

	public void setBaseInformations(String[] baseInformations) {
		this.baseInformations = baseInformations;
	}

	public String getDutyJson() {
		return dutyJson;
	}

	public void setDutyJson(String dutyJson) {
		this.dutyJson = dutyJson;
	}

	public String getInsureJson() {
		return insureJson;
	}

	public void setInsureJson(String insureJson) {
		this.insureJson = insureJson;
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

	public String getFactorType() {
		return factorType;
	}

	public void setFactorType(String factorType) {
		this.factorType = factorType;
	}

	private String loginFlag;

	public String getLoginFlag() {
		return loginFlag;
	}

	public void setLoginFlag(String loginFlag) {
		this.loginFlag = loginFlag;
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

	public static int getPagesize() {
		return pagesize;
	}

	public void setLastpage(int lastpage) {
		this.lastpage = lastpage;
	}

	public int getLastpage() {
		return lastpage;
	}

	public String getInsuranceCompany() {
		return insuranceCompany;
	}

	public void setInsuranceCompany(String insuranceCompany) {
		this.insuranceCompany = insuranceCompany;
	}

	@Resource
	private OrderProductService orderProductService;

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
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

	public String getPrem() {
		return prem;
	}

	public void setPrem(String prem) {
		this.prem = prem;
	}

	public String getRiskType() {
		return riskType;
	}

	public void setRiskType(String riskType) {
		this.riskType = riskType;
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

	public String getApplicantMobile() {
		return applicantMobile;
	}

	public void setApplicantMobile(String applicantMobile) {
		this.applicantMobile = applicantMobile;
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public Information getInformation2() {
		return information2;
	}

	public void setInformation2(Information information2) {
		this.information2 = information2;
	}

	public Information getInformation3() {
		return information3;
	}

	public void setInformation3(Information information3) {
		this.information3 = information3;
	}

	public Map<String, String> getAssociate() {
		associate.put("AppDate", ""); // 投保时间
		associate.put("AppLevel", ""); // 缴费方式
		associate.put("AppType", ""); // 投保类别
		associate.put("FeeYear", ""); // 缴费年期
		associate.put("Grade", ""); // 产品级别
		associate.put("MulPeople", ""); // 保险责任多类人群列表
		associate.put("NumAge", "recognizeeBirthday"); // 年龄
		associate.put("Occup", ""); // 职业类别
		associate.put("Period", ""); // 保险期限
		associate.put("Plan", "plan"); // 计划
		associate.put("SectionAge", ""); // 分段年龄
		associate.put("Sex", "recognizeeSex"); // 性别
		associate.put("TextAge", ""); // 文字年龄
		associate.put("Amnt", "amnt"); // 保额
		return associate;
	}

	public void setAssociate(Map<String, String> associate) {
		this.associate = associate;
	}

	public String getAssociateBefore() {
		associateBefore = "AppDate,AppLevel,AppType,FeeYear,Grade,MulPeople,NumAge,Occup,Period,Plan,SectionAge,Sex,TextAge,Amnt";
		return associateBefore;
	}

	public void setAssociateBefore(String associateBefore) {
		this.associateBefore = associateBefore;
	}

	public String getAssociateAfter() {
		associateAfter = "novalue,novalue,novalue,novalue,novalue,novalue,recognizeeBirthday,novalue,novalue,plan,novalue,recognizeeSex,novalue,amnt";
		return associateAfter;
	}

	public void setAssociateAfter(String associateAfter) {
		this.associateAfter = associateAfter;
	}

	public List<FEMRiskFactorList> getPlanList() {
		return planList;
	}

	public void setPlanList(List<FEMRiskFactorList> planList) {
		this.planList = planList;
	}

	public List<FEMRiskFactorList> getPeriodList() {
		return periodList;
	}

	public void setPeriodList(List<FEMRiskFactorList> periodList) {
		this.periodList = periodList;
	}

	public Map<ProductInsAttribute, List<String>> getProattrMap() {
		return proattrMap;
	}

	public void setProattrMap(Map<ProductInsAttribute, List<String>> proattrMap) {
		this.proattrMap = proattrMap;
	}

	// 获取所有配送方式
	public List<DeliveryType> getAllDeliveryType() {
		return deliveryTypeService.getAll();
	}

	// 获取所有支付方式
	public List<PaymentConfig> getAllPaymentConfig() {
		return paymentConfigService.getAll();
	}

	// ==================获取product属性测试==========================================
	// 获取product属性方式
	public List<Product> getAllProductAttr() {
		return allProductAttr;
	}

	public String attrList() {
		List<Product> allProuctAttr = productService.getAllProductAttr(id);
		ActionContext.getContext().put("allProuctAttr", allProuctAttr);
		return "info";
	}

	public void setAllProductAttr(List<Product> allProductAttr) {
		this.allProductAttr = allProductAttr;
	}

	// ===================获取product属性测试========================================

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

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Set<CartItem> getCartItemSet() {
		return cartItemSet;
	}

	public void setCartItemSet(Set<CartItem> cartItemSet) {
		this.cartItemSet = cartItemSet;
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
	
	
	public String getApplicantOccupation1() {
		return applicantOccupation1;
	}

	public void setApplicantOccupation1(String applicantOccupation1) {
		this.applicantOccupation1 = applicantOccupation1;
	}

	public String getApplicantOccupation2() {
		return applicantOccupation2;
	}

	public void setApplicantOccupation2(String applicantOccupation2) {
		this.applicantOccupation2 = applicantOccupation2;
	}

	public String getApplicantOccupation3() {
		return applicantOccupation3;
	}

	public void setApplicantOccupation3(String applicantOccupation3) {
		this.applicantOccupation3 = applicantOccupation3;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public Information getInformation() {
		return information;
	}

	public void setInformation(Information information) {
		this.information = information;
	}

	public String getApplicantArea() {
		return applicantArea;
	}

	public void setApplicantArea(String applicantArea) {
		this.applicantArea = applicantArea;
	}

	public String getRecognizeeArea() {
		return recognizeeArea;
	}

	public void setRecognizeeArea(String recognizeeArea) {
		this.recognizeeArea = recognizeeArea;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(String orderItemId) {
		this.orderItemId = orderItemId;
	}

	public OrderItem getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(OrderItem orderItem) {
		this.orderItem = orderItem;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public CartItem getCartItem() {
		return cartItem;
	}

	public void setCartItem(CartItem cartItem) {
		this.cartItem = cartItem;
	}

	public OrderLog getOrderLog() {
		return orderLog;
	}

	public void setOrderLog(OrderLog orderLog) {
		this.orderLog = orderLog;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
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

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public InformationInsured getInformationInsureds() {
		return informationInsureds;
	}

	public void setInformationInsureds(InformationInsured informationInsureds) {
		this.informationInsureds = informationInsureds;
	}

	public InformationAppnt getInformationAppnt() {
		return informationAppnt;
	}

	public void setInformationAppnt(InformationAppnt informationAppnt) {
		this.informationAppnt = informationAppnt;
	}

	public Information getInformation1() {
		return information1;
	}

	public void setInformation1(Information information1) {
		this.information1 = information1;
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

	public Set<InformationInsured> getInformationInsuredSet() {
		return informationInsuredSet;
	}

	public void setInformationInsuredSet(Set<InformationInsured> informationInsuredSet) {
		this.informationInsuredSet = informationInsuredSet;
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

	public List<Map> getListRelation() {
		return listRelation;
	}

	public void setListRelation(List<Map> listRelation) {
		this.listRelation = listRelation;
	}

	public List<Map> getListCountryCode() {
		return listCountryCode;
	}

	public void setListCountryCode(List<Map> listCountryCode) {
		this.listCountryCode = listCountryCode;
	}

	public List<PayBase> getBank1() {
		return bank1;
	}

	public void setBank1(List<PayBase> bank1) {
		this.bank1 = bank1;
	}

	public List<PayBase> getBank2() {
		return bank2;
	}

	public void setBank2(List<PayBase> bank2) {
		this.bank2 = bank2;
	}

	public List<PayBase> getBank3() {
		return bank3;
	}

	public void setBank3(List<PayBase> bank3) {
		this.bank3 = bank3;
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

	public String getOrderSn2() {
		return orderSn2;
	}

	public void setOrderSn2(String orderSn2) {
		this.orderSn2 = orderSn2;
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

	public List<Map> getOpts2() {
		return opts2;
	}

	public void setOpts2(List<Map> opts2) {
		this.opts2 = opts2;
	}

	public List<Map> getOpts3() {
		return opts3;
	}

	public void setOpts3(List<Map> opts3) {
		this.opts3 = opts3;
	}

	public List<Map> getAreas2() {
		return areas2;
	}

	public void setAreas2(List<Map> areas2) {
		this.areas2 = areas2;
	}

	public String getRelationFlag1() {
		return relationFlag1;
	}

	public void setRelationFlag1(String relationFlag1) {
		this.relationFlag1 = relationFlag1;
	}

	public String getRelationFlag2() {
		return relationFlag2;
	}

	public void setRelationFlag2(String relationFlag2) {
		this.relationFlag2 = relationFlag2;
	}

	public String getRelationFlag3() {
		return relationFlag3;
	}

	public void setRelationFlag3(String relationFlag3) {
		this.relationFlag3 = relationFlag3;
	}

	public String getInsurTypeChild() {
		return insurTypeChild;
	}

	public void setInsurTypeChild(String insurTypeChild) {
		this.insurTypeChild = insurTypeChild;
	}

	public Set<InformationDuty> getInformationDutyElementsSet() {
		return informationDutyElementsSet;
	}

	public void setInformationDutyElementsSet(Set<InformationDuty> informationDutyElementsSet) {
		this.informationDutyElementsSet = informationDutyElementsSet;
	}

	public Map<String, Object> getSdinteraction() {
		return sdinteraction;
	}

	public void setSdinteraction(Map<String, Object> sdinteraction) {
		this.sdinteraction = sdinteraction;
	}

	public List<Map> getListRelation2() {
		return listRelation2;
	}

	public void setListRelation2(List<Map> listRelation2) {
		this.listRelation2 = listRelation2;
	}

	public String getOutRiskCode() {
		return outRiskCode;
	}

	public void setOutRiskCode(String outRiskCode) {
		this.outRiskCode = outRiskCode;
	}

	public String getProductImg() {
		return productImg;
	}

	public void setProductImg(String productImg) {
		this.productImg = productImg;
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

	public List<InformationDuty> getShowDuty() {
		return showDuty;
	}

	public void setShowDuty(List<InformationDuty> showDuty) {
		this.showDuty = showDuty;
	}

	public List<List<InsuredShow>> getShowInsureds() {
		return showInsureds;
	}

	public void setShowInsureds(List<List<InsuredShow>> showInsureds) {
		this.showInsureds = showInsureds;
	}

	public String getDutyDisReq() {
		return dutyDisReq;
	}

	public void setDutyDisReq(String dutyDisReq) {
		this.dutyDisReq = dutyDisReq;
	}

	public List<FEMRiskFactorList> getTestAgeList() {
		return testAgeList;
	}

	public void setTestAgeList(List<FEMRiskFactorList> testAgeList) {
		this.testAgeList = testAgeList;
	}
	
	public List<InsuredHealth> getInsuredHealthList() {
		return insuredHealthList;
	}

	public void setInsuredHealthList(List<InsuredHealth> insuredHealthList) {
		this.insuredHealthList = insuredHealthList;
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

	public String getProtectionPeriodValue() {
		return protectionPeriodValue;
	}

	public void setProtectionPeriodValue(String protectionPeriodValue) {
		this.protectionPeriodValue = protectionPeriodValue;
	}

	public String cancel() {
		order = orderService.getOrderByOrderSn(orderSn);
		if (order == null) {
			addActionError("系统不存在该订单");
			return ERROR;
		}
		order.setOrderStatus(OrderStatus.invalid);
		orderService.update(order);
		orderSn = "";
		return list();
	}

	public List<String> getDetisnateList() {
		return detisnateList;
	}

	public void setDetisnateList(List<String> detisnateList) {
		this.detisnateList = detisnateList;
	}

	public static boolean DateCompare(Date d1, Date d2) {
		try {
			Date now = new Date();
			if (now.after(d1) || now.after(d2)) {
				return false;
			}
			if (d2.before(d1)) {
				return false;
			}
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
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
    private void init(){
    	JdbcTemplateData jtd = new JdbcTemplateData();   
		//-----------------------个别产品去掉投被保人中“本人”的关系----------------------------begin
		try {
			String sqlRelationId = "select codevalue,codename from dictionary where codetype=? and productId=? order by id asc";
			String[] sqlRelationIdTemp = {"Relationship",productId};
			//-----------------------个别产品去掉投被保人中“本人”的关系----------------------------end
			if ("1061".equals(insuranceCompanySn) || "1018".equals(insuranceCompanySn) || "2007".equals(insuranceCompanySn) || "2011".equals(insuranceCompanySn)) {
				String sqlCertificateCode = "select codevalue,codename from dictionary where codetype=? and insuranceCode=? order by id asc";
				String[] sqlCertificateCodetemp = {"certificate",insuranceCompanySn};
				certificateCode = jtd.obtainData(sqlCertificateCode,sqlCertificateCodetemp);// 证件类型
			} else {
				String sqlCertificateCode = "select codevalue,codename from dictionary where codetype=? and insuranceCode=? order by id asc";
				String[] sqlCertificateCodetemp = {"certificate","1061"};
				certificateCode = jtd.obtainData(sqlCertificateCode,sqlCertificateCodetemp);// 证件类型
			}
			if ("1061".equals(insuranceCompanySn) || "1018".equals(insuranceCompanySn)) {
				String sqlOpts = "select id,name from occupation where insuranceCompany= ?  and parent_id is null order by id asc";
				String[] sqlOptstemp = {insuranceCompanySn};
				opts = jtd.obtainData(sqlOpts,sqlOptstemp);// 职业
			} else {
				String[] sqlOptstemp = {"1061"};
				String sqlOpts = "select id,name from occupation where insuranceCompany= ?  and parent_id is null order by id asc";
				opts = jtd.obtainData(sqlOpts,sqlOptstemp);// 职业
			}
			if ("1061".equals(insuranceCompanySn) || "1018".equals(insuranceCompanySn) || "2007".equals(insuranceCompanySn) || "2011".equals(insuranceCompanySn)) {
				String sqlSexCode = "select codevalue,codename from dictionary where codetype=? and insuranceCode=? order by id asc";
				String[] sqlSexCodetemp = {"Sex",insuranceCompanySn};
				sexs = jtd.obtainData(sqlSexCode,sqlSexCodetemp);// 性别
			} 
			if (!"".equals(insuranceCompanySn)) {
				String[] temp = {insuranceCompanySn};
				String sqlCountryCode = "select codevalue,codename,codeenname from dictionary where codetype='CountryCode' and insuranceCode=? order by id asc";
				String sqlAreaOpts = "select id,name from area where insuranceCompany=?  and parent_id = '' order by id asc";
				listCountryCode = jtd.obtainData(sqlCountryCode,temp);// 目的地
				areas = jtd.obtainData(sqlAreaOpts,temp);// 地区
			}
			//-----------------------个别产品去掉投被保人中“本人”的关系----------------------------begin
			listRelation2 = jtd.obtainData(sqlRelationId,sqlRelationIdTemp);   
			if(listRelation2.isEmpty()||listRelation2.size()<=0){
				String sqlRelation = "select codevalue,codename from dictionary where codetype=? and insuranceCode=?  order by id asc";
				String[] sqlRelationtemp = {"Relationship",insuranceCompanySn};
				listRelation2 = jtd.obtainData(sqlRelation,sqlRelationtemp);// 投被保人关系
			}
			//-----------------------个别产品去掉投被保人中“本人”的关系----------------------------end
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} 
    }
	public List<Map> getAppntAreas2() {
		return appntAreas2;
	}

	public void setAppntAreas2(List<Map> appntAreas2) {
		this.appntAreas2 = appntAreas2;
	}

	public List<Map> getRegAreas2() {
		return regAreas2;
	}

	public void setRegAreas2(List<Map> regAreas2) {
		this.regAreas2 = regAreas2;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public BigDecimal getCurrentTermAmount() {
		return currentTermAmount;
	}

	public void setCurrentTermAmount(BigDecimal currentTermAmount) {
		this.currentTermAmount = currentTermAmount;
	}

	public String getBrkKindCode() {
		return brkKindCode;
	}

	public void setBrkKindCode(String brkKindCode) {
		this.brkKindCode = brkKindCode;
	}

	public String getBrkRiskCode() {
		return brkRiskCode;
	}

	public void setBrkRiskCode(String brkRiskCode) {
		this.brkRiskCode = brkRiskCode;
	}

	public String getCountPrice() {
		return countPrice;
	}

	public void setCountPrice(String countPrice) {
		this.countPrice = countPrice;
	}

	public String getBrkRiskName() {
		return brkRiskName;
	}

	public void setBrkRiskName(String brkRiskName) {
		this.brkRiskName = brkRiskName;
	}

	public List<FEMRiskFactorList> getMulPeopleList() {
		return mulPeopleList;
	}

	public void setMulPeopleList(List<FEMRiskFactorList> mulPeopleList) {
		this.mulPeopleList = mulPeopleList;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getMinute() {
		return minute;
	}

	public void setMinute(String minute) {
		this.minute = minute;
	}

	public String getRarea() {
		return rarea;
	}

	public void setRarea(String rarea) {
		this.rarea = rarea;
	}

	public String getIarea() {
		return iarea;
	}

	public void setIarea(String iarea) {
		this.iarea = iarea;
	}

	public String getFfrelas() {
		return ffrelas;
	}

	public void setFfrelas(String ffrelas) {
		this.ffrelas = ffrelas;
	}
	public String getNeedUWCheckFlag() {
		return needUWCheckFlag;
	}

	public void setNeedUWCheckFlag(String needUWCheckFlag) {
		this.needUWCheckFlag = needUWCheckFlag;
	}

	public String getYoriginalAmount() {
		return yoriginalAmount;
	}

	public void setYoriginalAmount(String yoriginalAmount) {
		this.yoriginalAmount = yoriginalAmount;
	}

	public String getYdiscountAmount() {
		return ydiscountAmount;
	}

	public void setYdiscountAmount(String ydiscountAmount) {
		this.ydiscountAmount = ydiscountAmount;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getInsuranceCompanySn() {
		return insuranceCompanySn;
	}

	public void setInsuranceCompanySn(String insuranceCompanySn) {
		this.insuranceCompanySn = insuranceCompanySn;
	}

	public String getConfigInputFlag() {
		return configInputFlag;
	}

	public void setConfigInputFlag(String configInputFlag) {
		this.configInputFlag = configInputFlag;
	}

	public String getFdapAll() {
		return fdapAll;
	}

	public void setFdapAll(String fdapAll) {
		this.fdapAll = fdapAll;
	}

	public List<FEMRiskFactorList> getAmntList() {
		return amntList;
	}

	public void setAmntList(List<FEMRiskFactorList> amntList) {
		this.amntList = amntList;
	}

	public String getKID() {
		return KID;
	}

	public void setKID(String kID) {
		KID = kID;
	}

	public String getDutyPremReq() {
		return dutyPremReq;
	}

	public void setDutyPremReq(String dutyPremReq) {
		this.dutyPremReq = dutyPremReq;
	}

	public TradeInformation getTradeInformation() {
		return tradeInformation;
	}

	public void setTradeInformation(TradeInformation tradeInformation) {
		this.tradeInformation = tradeInformation;
	}

	public String getJudge() {
		return judge;
	}

	public void setJudge(String judge) {
		this.judge = judge;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	
	
}