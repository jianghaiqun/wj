package cn.com.sinosoft.action.shop; 

import cn.com.sinosoft.action.shop.uw.UsersUWCheck;
import cn.com.sinosoft.bean.CommentInfo;
import cn.com.sinosoft.common.FinancingCheck;
import cn.com.sinosoft.common.ShopCheckField;
import cn.com.sinosoft.common.ShopValidate;
import cn.com.sinosoft.common.email.MessageConstant;
import cn.com.sinosoft.entity.ActivityInfo;
import cn.com.sinosoft.entity.Area;
import cn.com.sinosoft.entity.CouponInfo;
import cn.com.sinosoft.entity.Dictionary;
import cn.com.sinosoft.entity.DirectPayBankInfo;
import cn.com.sinosoft.entity.GiftClassify;
import cn.com.sinosoft.entity.InsuredShow;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.ModuleInfo;
import cn.com.sinosoft.entity.Occupation;
import cn.com.sinosoft.entity.Order.OrderStatus;
import cn.com.sinosoft.entity.OrderDutyFactor;
import cn.com.sinosoft.entity.OrderRiskAppFactor;
import cn.com.sinosoft.entity.PayBase;
import cn.com.sinosoft.entity.PointExchangeInfo;
import cn.com.sinosoft.entity.ProductConfig;
import cn.com.sinosoft.entity.QuestionPaper;
import cn.com.sinosoft.entity.SDInformation;
import cn.com.sinosoft.entity.SDInformationAppnt;
import cn.com.sinosoft.entity.SDInformationBnf;
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
import cn.com.sinosoft.entity.SDRelationAppnt;
import cn.com.sinosoft.entity.SDRelationRecognizee;
import cn.com.sinosoft.entity.TradeInformation;
import cn.com.sinosoft.entity.ZdrecordCps;
import cn.com.sinosoft.service.AreaService;
import cn.com.sinosoft.service.CouponInfoService;
import cn.com.sinosoft.service.DealDataService;
import cn.com.sinosoft.service.DictionaryService;
import cn.com.sinosoft.service.DirectPayBankInfoService;
import cn.com.sinosoft.service.GiftClassifyService;
import cn.com.sinosoft.service.HealthyInfoService;
import cn.com.sinosoft.service.InsuredHealthService;
import cn.com.sinosoft.service.OccupationService;
import cn.com.sinosoft.service.OrderConfigNewService;
import cn.com.sinosoft.service.PayBaseService;
import cn.com.sinosoft.service.ProductConfigService;
import cn.com.sinosoft.service.ProductPeriodService;
import cn.com.sinosoft.service.QuestionPaperService;
import cn.com.sinosoft.service.SDInformationAppntService;
import cn.com.sinosoft.service.SDInformationBnfService;
import cn.com.sinosoft.service.SDInformationDutyService;
import cn.com.sinosoft.service.SDInformationDutyTempService;
import cn.com.sinosoft.service.SDInformationInsuredService;
import cn.com.sinosoft.service.SDInformationPropertyService;
import cn.com.sinosoft.service.SDInformationRiskTypeService;
import cn.com.sinosoft.service.SDInformationService;
import cn.com.sinosoft.service.SDInsuredHealthService;
import cn.com.sinosoft.service.SDOrderService;
import cn.com.sinosoft.service.SDRelationAppntService;
import cn.com.sinosoft.service.SDRelationRecognizeeService;
import cn.com.sinosoft.service.TradeInformationService;
import cn.com.sinosoft.service.ZdrecordCpsService;
import cn.com.sinosoft.util.Constant;
import cn.com.sinosoft.util.CookieUtil;
import cn.com.sinosoft.util.JdbcTemplateData;

import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.cache.CacheManager;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.CountryChineseSpelling;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.LogUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.inter.ActivityCalculate;
import com.sinosoft.inter.MailAction;
import com.sinosoft.jdt.ParseXMLToObject;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.points.IntegralConstant;
import com.sinosoft.points.PointsCalculate;
import com.sinosoft.product.PremCalculate;
import com.sinosoft.schema.BuyForCustomerOldSnSchema;
import com.sinosoft.schema.FMCheckFieldSchema;
import com.sinosoft.schema.FMCheckFieldSet;
import com.sinosoft.schema.ZhenAiRenewalSchema;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FEMDutyAmntPremList;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FEMRiskFactorList;
import com.sinosoft.webservice.productPrem.ProductPremServiceStub.DutyAmount;
import com.wangjin.emar.cps.OrderInfoCommit;
import com.wangjin.infoseeker.OrderSeeker;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
  
/**
 * 购买流程改造后核心类
 *  
 * @author sinosoft
 * 
 */
@ParentPackage("shop") 
public class OrderConfigNewAction extends BaseShopAction { 
	
	private static final long serialVersionUID = -7227426726084770262L;
	private HttpServletRequest request = ServletActionContext.getRequest();
	SimpleDateFormat sdf_1 = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdf_2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private List<ProductConfig> productConfigs = new ArrayList<ProductConfig>();
	private SDInformationAppnt sdinformationAppnt = new SDInformationAppnt();// 改造后投保人信息，add
																				// by
								 												// cuishg
	private List<SDInformationInsured> sdinformationinsuredList = new ArrayList<SDInformationInsured>();// 改造后添加，用于多被保人存储--多被人录入
																										// add
																										// bycuishg
	private List<SDInformationInsured> sdinsuredList = new ArrayList<SDInformationInsured>();// 改造后添加，用于多被保人excel上传
																								// add
																								// bycuishg
	private SDInformationInsured sdinformationinsured = new SDInformationInsured();// 改造后添加，用于多被保人存储--单个被保人add
																					// by
																					// cuishg
	private List<SDInformationInsured> sdinformationinsuredList_Imp = new ArrayList<SDInformationInsured>();// 改造后添加，用于多被保人存储--多被人导入
																											// add
																											// by
																											// cuishg
	private List<SDInformationBnf> sdinformationbnfList = new ArrayList<SDInformationBnf>();//XXX 受益人集合 req20150323004
	
	private List<SDOrderItemOth> sdorderitemothList = new ArrayList<SDOrderItemOth>();
	private List<Dictionary> certificateList = new ArrayList<Dictionary>();// 证件类型
	private List<Dictionary> relationList = new ArrayList<Dictionary>();// 投被保人关系
	private List<Dictionary> bnfRelationList = new ArrayList<Dictionary>();// 投被保人关系
	private List<Dictionary> sexList = new ArrayList<Dictionary>();// 性别
	private List<Dictionary> excelcertificateList = new ArrayList<Dictionary>();// 证件类型-excel导入
	private List<Dictionary> excelrelationList = new ArrayList<Dictionary>();// 投被保人关系-excel导入
	private List<Dictionary> excelsexList = new ArrayList<Dictionary>();// 性别-excel导入
	private List<Occupation> occupationList = new ArrayList<Occupation>();// 一级职业
	private List<Area> areaList = new ArrayList<Area>();// 一级地区
	private List<OrderDutyFactor> dutyFactor = new ArrayList<OrderDutyFactor>();// 产品投保要素列表
	private List<OrderRiskAppFactor> riskAppFactior = new ArrayList<OrderRiskAppFactor>();// 责任投保要素列表
	private List<Dictionary> listCountryCode = new ArrayList<Dictionary>();// 旅游目的地
	private Map<String, List<Dictionary>> mapCountryCode = new HashMap<String, List<Dictionary>>();// 旅游目的地Map集合
	private Map<String, Map<String, List<Dictionary>>> mapCountryCodeList = new HashMap<String, Map<String, List<Dictionary>>>();// 旅游目的地Map集合
	private List<SDInformationRiskType> sdRiskTypeOtherList = new ArrayList<SDInformationRiskType>();// 添加购买份数后，用于保存其他份数部分
	private List<SDInformationInsured> sdInsuredOtherList = new ArrayList<SDInformationInsured>();// 添加购买份数后，用于保存其他份数部分
	private List<Dictionary> securityList = new ArrayList<Dictionary>();// 是否有医保
	private List<Dictionary> bankList = new ArrayList<Dictionary>();// 开户银行

	private List<FEMRiskFactorList> periodList = new ArrayList<FEMRiskFactorList>();// 保障期限列表
	private List<FEMRiskFactorList> planList = new ArrayList<FEMRiskFactorList>();// 计划值列表
	private List<FEMRiskFactorList> occupList = new ArrayList<FEMRiskFactorList>();// 职业类别
	private List<FEMRiskFactorList> gradeList = new ArrayList<FEMRiskFactorList>();// 产品级别列表
	private List<FEMRiskFactorList> feeYearList = new ArrayList<FEMRiskFactorList>();// 缴费年期列表
	private List<FEMRiskFactorList> appLevelList = new ArrayList<FEMRiskFactorList>();// 缴费方式列表
	private List<FEMRiskFactorList> appTypeList = new ArrayList<FEMRiskFactorList>();// 投保类别列表
	private List<FEMRiskFactorList> testAgeList = new ArrayList<FEMRiskFactorList>();// 产品级别列表
	private List<FEMRiskFactorList> mulPeopleList = new ArrayList<FEMRiskFactorList>();// 保险责任多类人群列表
	private List<List<InsuredShow>> showAppnts = new ArrayList<List<InsuredShow>>();// 页面显示投保人信息
	private List<List<InsuredShow>> showInsureds = new ArrayList<List<InsuredShow>>();// 页面显示被保人信息
	private List<List<InsuredShow>> showBnfs = new ArrayList<List<InsuredShow>>();// 页面显示被保人信息
	private List<List<InsuredShow>> showPeriods = new ArrayList<List<InsuredShow>>();// 页面显示被保人信息
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
	private SDOrderItemOth sdorderitemoth = new SDOrderItemOth();
	private SDInformation sdinformation = new SDInformation();// 改造后订单详细
																// 表（产品表），add by
																// cuishg
	private DirectPayBankInfo directPayBankInfo = new DirectPayBankInfo(); // 续期交费信息
	private List<PayBase> bank1;
	private List<PayBase> bank2;
	private List<PayBase> bank3;
	private Map<String, Object> sdinteraction = new HashMap<String, Object>();// 横向接口调用参数
	LinkedHashMap<Object, String> mLMap = new LinkedHashMap<Object, String>();
	private List<SDInsuredHealth> sdinsuredHealthList = new ArrayList<SDInsuredHealth>();// 健康告知列表
	private Set<SDInformationRiskType> mSDInformationRiskTypeSet = new HashSet<SDInformationRiskType>();

	private QuestionPaper questionPaper = new QuestionPaper();// 高额信息表
	private List<QuestionPaper> questionList = new ArrayList<QuestionPaper>();// 健康告知列表
	private List<SDRelationAppnt> reappntList = new ArrayList<SDRelationAppnt>();// 快速录入被保人信息表
	private List<SDRelationRecognizee> recognizeeList = new ArrayList<SDRelationRecognizee>();// 快速录入被保人信息表

	private String applicantName;// 投保人姓名
	private String applicantMobile;
	private String applicantIdentityType; // 证件类型
	private String applicantIdentityTypeName; // 证件类型名称
	private String applicantIdentityid; //身份证号
	private String applicantBirthday;// 投保人出生日期
	private String productId;// 产品id
	private String loginFlag;// 登录标记
	private String reappntName;// 快速录入被保人姓名
	private String reinsuredName;// 快速录入被保人姓名
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
	private String informationSn;// 订单明细表编号
	private Integer startPeriod = 1;// 起始日期与当前日期差
	private Integer endPeriod = 730;
	private String insureRiskType;
	private String KID;
	private String riskType;// 产品小类。add by cuishg
	private String mulInsuredFlag;// 多被保人标志
	private String payType;
	private SDInformationInsuredElements informationInsuredElements;
	private List<SDInformationInsuredElements> informationInsuredElementsList = new ArrayList<SDInformationInsuredElements>();
	private String DestinationCountry;// 旅游木目的地
	private int insuredCount = 1;// 被保人人数，主要用于多被保人时--其他，
	private int beneficiaryCount = 1;//XXX 受益人个数 req20150323004
	private int insuredResultCount = 1;//预览页被保人数显示 req20150323004
	private int sdInformationBnfCount = 1;//XXX 预览页受益人人数显示 req20150323004
	private int insuredActCount = 1;// 被保人购买份数，主要用于多被保人时--其他，
	private int insuredMulCount = 1;// 被保人人数，主要用于多被保人时--本人，
	private int insuredImpCount = 1;// 被保人人数，主要用于多被保人Excel导入时，
	private String impValadate = "N";// 被保人人数，主要用于多被保人Excel导入时校验处理，
	private String appLevel = "";// 缴费类型
	private String appType = "";// 投保类别
	private String feeYear = "";// 缴费年期
	private String Sex = "";// 性别
	private String recognizeeOperate = "1";// 被保人操作标志 0 但被保人操作；1
											// 多被保人下本人操作；2多被保人下其他被保人操作。用于页面页签的显示\
	private String benefitOperate = "0";//XXX 收益人操作标识  0：法定受益人  1：指定受益人 req20150323004
	private String effdate = "";// 保单生效日期，用于生日校验
	private String effdateNew = "";// 保单生效日期
	private String m_startDate = "";
	private String m_endDate = "";
	private String judge;
	private BigDecimal totalAmnt = new BigDecimal("0.00");// 订单总金额
	private BigDecimal insTotalAmnt;// 被保人总金额
	private TradeInformation tradeInformation;
	private Boolean quesFlag;// 问卷调查标记
	private String mainAmnt;// 主险保额
	private String supplierCode2;// 保险公司编码
	private String orderFlag;// 购物车标志
	private List<SDInformationProperty> sdinformationpropertyList = new ArrayList<SDInformationProperty>();
	private List<Dictionary> propertyToRecognizeeList = new ArrayList<Dictionary>();// 财产与被保人关系
	private List<Dictionary> hourseTypeList = new ArrayList<Dictionary>();// 房屋类型
	private List<Dictionary> hourseAgeList = new ArrayList<Dictionary>();// 房龄
	private List<InsuredShow> showPropertys = new ArrayList<InsuredShow>();// 单被保人财产信息
	private Set<SDInformationProperty> mSDInformationPropertySet = new HashSet<SDInformationProperty>();
	private SDInformationProperty sdinformationProperty = new SDInformationProperty();
	private String typeFlag = "";// 渠道类型标记
	private String channelCode = "";// 渠道编码
	private String userCode = "";// 渠道人员编码
	private String jrhsURL = "";// 代理人工程路径
	private String relationIsSelf = "";// 被保人只能选本人标志
	private String productExcelTemp = "";// 被保人批量导入excle模板路径
	private String excleTempEnName = "N";// 被保人批量导入excle模板是否还有英文名
	private String productExcelFlag = "N";// 被保人批量导入excle标志
	private String uploadfile = "";// Excel临时文件路径
	private String codeType = "";// dictionary 编码类型
	private String codeName = "";// dictionary 名称
	private int limitCount = 1;// 产品限购，用于购买份数校验
	private String productHtml = "";// 产品详细页地址
	private String callBackAmount = "";// 显示金额
	private String effectiveNextDayFlag = "";// 产品是否设定推迟日期生效
	private String effectiveDayValue = "";// 产品起始生效日期
	private List<Map<String, String>> activitylist = new ArrayList<Map<String, String>>();// 此次支付可参加的商品活动
	private List<ActivityInfo> mjactivitylist = new ArrayList<ActivityInfo>();// 满减的商品活动
	private String paySn;
	private String insuranceDec;// 投保声明
	private String shopcarflag = "false";
	private String currentDate;// 记录用于会员中心-我的订单 继续支付起保日期过期的判断的当前时间
	private String startDate;// 记录用于会员中心-我的订单 继续支付起保日期过期的判断的起保时间
	private String startPerid;// 记录起保时间间隔
	private String LastUrl = "";// 用于CSP存储数据，区别于主站CPS_WEB
	private String appStartDate;
	private String appEndDate;
	private Integer appStartAge;
	private Integer appEndAge;
	private String appAgeTips;
	private String insStartDate;
	private String insEndDate;
	// 活动、折扣等优惠信息
	private HashMap<String, Object> activityMap = new HashMap<String, Object>();
	private BigDecimal totalAmount;// 订单金额 用于投保录入页 记录打折后金额
	private BigDecimal insuredPrem;// 被保人保费金额 用于投保录入页 记录打折后金额
	private String channelsn;
	private String sourceFlag;// CMS客服带出单，订单渠道来源
	private String wpViewFlag;// wap订单，继续支付后标记
	private String complicatedFlag;// 复杂产品标记
	private String dutyTempSerials;// 责任临时表序列号 用于复杂产品投保录入页的保费试算
	private String order_healthySn; // 健康告知临时订单号，订单保存时删掉
	private String beneficiaryNum = "3";//XXX 受益人数量默认最多为3个 req20150323004
	private String fkIsShow; //根据CPS用户查询配置 是否显示飞客ID输入框
	private String pointExchangeFlag;// 积分兑换标记
	private String PointScalerUnit;// 积分换算单位
	private String points;// 积分兑换支付页剩余积分数
	private String balance;// 代理人账户余额
	private Map<String,String> map_pointinfo;//积分信息集合
	private String isAllFree = "N";//是否赠险标记
	private String selfBnfFlag = "N";//受益人为被保险人本人标识(预览页使用)
	private String activityFlag = "0";// 投保录入页、预览页 价格是否有折扣 0：无折扣 1：有折扣
	private String memGradeDesc = "保单生效后可获得";// 投保录入页、预览页赠送积分会员描述
	private String sendPoints = "0";// 投保录入页、预览页赠送积分个数
	private String sendPointsValue = "0";// 投保录入页、预览页赠送积分价值
	private String activityInfo = "";// 投保录入页、预览页显示活动信息
	private String memberid;
	private String mult; // 份数
	private String specialEffDateFlag; // 特殊终止日期 //太平E宝贝重疾保障计划 终保日期根据被保人生日改变
	//会员等级及积分信息
	private Map map_point_result;
	private String againBuyOrderSn; // 再次购买被复制订单号
	private String buyForCustomerFlag; // 代客支付区分
	private String oldOrdersn; // 原始订单号
	private List<CommentInfo> commentList = new ArrayList<CommentInfo>();
	
	private String isLcx = "1"; // 理财险
	private List<Dictionary> lcxBank = new ArrayList<Dictionary>();// 理财险支付银行
	private String loginname ="";
	private String curdate = "";
	private Map bindBankInfo;
	private String memberActivity = "";
	
	//职业分类列表
	private String productyTypeFirstList;//产品一级分类列表页地址
	private String productDetailPagePath;//产品详情页地址
	private List<Map<String,String>> threeLevelOccupations;//三级职业列表
	private String occDownAddr;//职业列表附件下载地址
	private String OccupLevel;//职业等级
	private String needShowOccupLevel = "0";//投保页面是否需要在被保人出展示职业类别
	private String needShowOccup ="0";//简化特殊产品配置三级职业类别显示
	private String isUnderwritingOffline;//是否支持线下核保
	private String UnderwritingOfflineHealthy;//健康告知文字
	private String UnderwritingOfflineCode;// 线下核保编码
	private String policyNoOld;// 原保单号
	
	// 线上回访信息
	private Map<String, String> onLineCallBackInfo;
	@Resource
	private ProductConfigService productConfigService;
	@Resource
	protected DictionaryService dictionaryService;
	@Resource
	private OccupationService occupationService;
	@Resource
	private AreaService areaService;
	@Resource
	private HealthyInfoService healthyInfoService;
	@Resource
	private InsuredHealthService insuredHealthService;
	@Resource
	private ProductPeriodService productPeriodService;
	@Resource
	private ZdrecordCpsService zdrecordCpsService;
	// 改造后添加
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
	private PayBaseService payBaseService;
	@Resource
	private OrderConfigNewService orderConfigNewService;
	@Resource
	private TradeInformationService tradeInformationService;
	@Resource
	private QuestionPaperService questionPaperService;
	@Resource
	private SDInformationRiskTypeService sdinformationRiskTypeService;
	@Resource
	private SDInformationPropertyService sdinformationPropertyService;
	@Resource
	private SDRelationAppntService sdRelationAppntService;
	@Resource
	private SDRelationRecognizeeService sdRelationRecognizeeService;
	@Resource
	private CouponInfoService couponInfoService;
	@Resource
	private SDInformationDutyTempService sdInformationDutyTempService;
	@Resource
	private SDInformationDutyService sdInformationDutyService;
	@Resource
	private DirectPayBankInfoService directPayBankInfoService;
	@Resource
	private GiftClassifyService giftClassifyService;
	@Resource
	private SDInformationBnfService sdInformationBnfService;
	
	/**
	 * 产品详细页面点击“立即购买”，触发的方法
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String buyNow() {
		Map<String, Object> paramter = new HashMap<String, Object>();
		try {
			// Add by Meijunfeng start
			if (this.period.endsWith("X")) {
				this.period = PremCalculate.getPeriod(this.period, this.productId);
			}

			// Add by Meijunfeng end
			if (productId == null || "".equals(productId)) {
				addActionError("亲，您的投保遇到了点小问题，请咨询客服后进行购买哦！");
				logger.error("OrderConfigNewAction.buyNow方法异常①，产品编码为空");
				return ERROR;
			}
			//"000701007,000701010,000701011"
			String specialEffDateProductIds=new QueryBuilder("SELECT CodeValue FROM zdcode WHERE CodeType='specialEffDateFlag' and CodeValue=?", productId).executeString();
			if(StringUtil.isNotEmpty(specialEffDateProductIds)){
				specialEffDateFlag ="Y";
			}else{
				specialEffDateFlag ="N";
			}

			if (StringUtil.isEmpty(complicatedFlag)) {
				complicatedFlag = new QueryBuilder("select complicatedFlag from sdproduct where productId=?", productId).executeString();
			}
			dealInsuredDate(productId);
			paramter = sdorderService.getProductInformation(productId, "N", channelCode);// 产品ID
			// productid 需要在页面设置隐藏变量p
			if (paramter == null) {
				addActionError("亲，您的投保遇到了点小问题，请咨询客服后进行购买哦！");
				logger.error("OrderConfigNewAction.buyNow方法异常③，获取产品信息失败！");
				return ERROR;
			}
			try {
				if (StringUtil.isNotEmpty(plan)) {
					plan = java.net.URLDecoder.decode(plan, "UTF-8");
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			String[] BaseInformation = new String[17];
			BaseInformation = (String[]) paramter.get("baseInformation");// 产品基础数据
			riskAppFactior = (List<OrderRiskAppFactor>) paramter.get("riskAppFactor");
			dutyFactor = (List<OrderDutyFactor>) paramter.get("dutyFactor");
			setSession(productId + "baseInformation", BaseInformation);
			setSession(productId + "riskAppFactior", riskAppFactior);
			setSession(productId + "dutyFactor", dutyFactor);
			String price = BaseInformation[6].toString();// 商品原价
			String countPrice = BaseInformation[11].toString();// 折扣后价格
			String insuranceCompanySn = BaseInformation[2].toString();// 保险公司编码
			sdorder.setProductTotalPrice(new BigDecimal(countPrice));
			sdorder.setDiscountRates(BaseInformation[10].toString());// 折扣比率
			if (StringUtil.isNotEmpty(BaseInformation[10].toString())) {
				activityFlag = "1";
			} else {
				activityFlag = "0";
			}
			sdinformation.setProductPrice(price);// 商品原价
			sdinformation.setRiskType(BaseInformation[5].toString());
			sdinformation.setSubRiskType(BaseInformation[7].toString());
			// 处理产品保障期限与计划
			this.setDutyFactorAndAmntNum(riskAppFactior, dutyFactor);
			// 保额
			this.getInsuredAmnt(dutyFactor, plan);
			sdinformation.setEnsureLimitType(this.protectionPeriodTy.toUpperCase());// 保障期限类型
			setSession("productId", productId);
			productId = BaseInformation[0].toString();// 产品ID
			sdinformation.setProductId(productId);
			sdinformation.setProductName(BaseInformation[1].toString());
			// 返回CPS产品，投保录入页不同
			if (BaseInformation[14] != null && "01".equals(BaseInformation[14])) {
				return cpsProductJump(productId, BaseInformation[15]);
			}
			this.supplierCode2 = insuranceCompanySn;
			insuranceCompanySn = insuranceCompanySn.substring(0, 4);
			sdinformation.setInsuranceCompany(insuranceCompanySn);
			riskType = BaseInformation[7].toString();// 产品小类
			dataBuild();

			init(insuranceCompanySn);
			if (getLoginMember() != null) {
				sdorder.setMemberId(getLoginMember().getId());
				//登录情况自动加载出投保人的手机号码及电子邮箱
				String mobile = getLoginMember().getMobileNO();
				String email = getLoginMember().getEmail();
				if(mobile != null){
					sdinformationAppnt.setApplicantMobile(mobile);
				}
				if(email != null){
					sdinformationAppnt.setApplicantMail(email);
				}
				loginFlag = "true";
			}

			// 存储代客支付和原始订单号属性值
			if (StringUtil.isNotEmpty(buyForCustomerFlag) || StringUtil.isNotEmpty(oldOrdersn)) {
				setSession(productId + "buyForCustomerFlag", buyForCustomerFlag);
				setSession(productId + "oldOrdersn", oldOrdersn);
			}
		} catch (Exception e) {
			addActionError("亲，您的投保遇到了点小问题，请咨询客服后进行购买哦！");
			logger.error("OrderConfigNewAction.buyNow方法异常②，产品编码为空" + e.getMessage(), e);
			return ERROR;
		}
		// 暂时只针对淘宝代出单--修改可所有渠道
		//if (StringUtil.isNotEmpty(sourceFlag) && ("tb".equals(sourceFlag) || "cps".equals(sourceFlag))) {
		if (StringUtil.isNotEmpty(sourceFlag)) {
			sdorder.setChannelsn(sourceFlag);
		}
		
		if ("1".equals(pointExchangeFlag)) {
			PointScalerUnit = Config.getConfigValue("PointScalerUnit");
			sdorder.setChannelsn(Constant.WJ_JFSC_CHANNELSN);
		}
		
		// 根据产品ID，保险公司，产品小类（旅游，意外，健康，人寿）确定投保页面
		this.setAttribute("orderId", sdorder.getId());
		String productID = productId;// A01 产品ID
		// 得到产品限购份数
		dealLimitCount(sdinformation);
		// 取得活动信息
		String channelSn = orderConfigNewService.getChannelSn(request, sdorder.getChannelsn());
		
		sdorder.setChannelsn(channelSn);
		
		getActivityInfo(productId, channelSn);
		if (StringUtil.isNotEmpty(sdorder.getMemberId())) {
			// 查询产品积分规则信息
			List<String> products = new ArrayList<String>();
			products.add(productId);
			DataTable dt = PointsCalculate.getProductList(products);
			String productGivePointsPercent = null;
			if(dt.getRowCount() > 0){
				productGivePointsPercent = dt.getString(0, "GivePoints");
			}
			memCalInteger(0, sdorder.getMemberId(), "0",productGivePointsPercent);
		}
		
		// 判断是否进入健康告知页
		if (StringUtil.isEmpty(order_healthySn)) {
			sdinsuredHealthList = orderConfigNewService.getInsuredHealthy(healthyInfoService, sdinsuredHealthService, sdinformation);
			if (sdinsuredHealthList != null && sdinsuredHealthList.size() >= 1) {
				//根据产品id获取产品一级分类列表页地址
				QueryBuilder firstAddrQuery = new QueryBuilder("select HtmlPath from sdproduct "
						+ "where productID = ?",productId);
				String htmlPath = firstAddrQuery.executeString();
				if(htmlPath != null){
					productyTypeFirstList = htmlPath.substring(0,htmlPath.lastIndexOf("/")+1);
				}
				//产品详情页地址
				productDetailPagePath = htmlPath;
				//是否展示线下核保标识
				Mapx<String, String> underwritingOfflineProducts =  CacheManager.getMapx("Code", "underwritingOfflineProducts");
				if(underwritingOfflineProducts.containsKey(productId)){
					isUnderwritingOffline = "1";
					UnderwritingOfflineHealthy = new QueryBuilder("SELECT memo FROM zdcode WHERE codetype='UnderwritingOfflineText' AND codevalue='healthyTip_" + productId + "'").executeString();
				}
				return "healthy";
			}
		}
		// 模块路径
		PageModule pm = new PageModule();
		pageModuleList = pm.getPageModuleURL(productID);
		dealExcelModule(productID);
		if (StringUtil.isNotEmpty(productExcelTemp)) {
			productExcelFlag = "Y";
		}
		impValadate = "N";
		// add by cuishigang 投保人信息快速录入 start
		quickQueryAppnt();
		// add by cuishigang 投保人信息快速录入 end
		// 默认产品无次日生效，查询zdcode表中被要求次日生效的产品记录
		effectiveNextDayFlag = "false";
		QueryBuilder qb = new QueryBuilder("SELECT memo FROM zdcode WHERE codetype='ProductPeriod' AND codevalue=?");
		qb.add(productId);
		DataTable dt = qb.executeDataTable();
		if (dt.getRowCount() > 0) {
			effectiveNextDayFlag = "true";
			int num = Integer.parseInt(dt.get(0, 0).toString());
			startPeriod = num;
			Date date = DateUtil.addDay(new Date(), num);
			effectiveDayValue = DateUtil.toString(date);
		}
		
		//TODO 点击立即购买获取最大受益人个数 req20150323004
		qb = new QueryBuilder("SELECT memo FROM zdcode WHERE codetype='beneficiaryNum' AND codevalue=?");
		qb.add(productId.substring(0, 4));
		if(StringUtil.isNotEmpty(qb.executeString())){
			beneficiaryNum = qb.executeString();
		}
		//判断产品公司编码是否属于美亚，安联，史带，人保四种产品，如果属于则界面展示"确认被保人属于可投保职业"选择
		Mapx<String, String> codes =  CacheManager.getMapx("Code", "NewServiceComcode");
		//判断职业精简配置特殊产品
		Mapx<String, String> sproductcode =  CacheManager.getMapx("Code", "NewServiceSpcode");
		
		if(codes != null && codes.containsKey(supplierCode2)){
			needShowOccupLevel = "1";
			if (sproductcode != null) {
				if (sproductcode.containsKey(productID)) {//属于特殊产品配置
					needShowOccup = "1";
				}
			}
		}
		
		//判断产品是否为根据职业配置的批量上传产品,判断"确认被保人属于可投保职业"显隐
		Mapx<String, String> productcodes =  CacheManager.getMapx("Code", "NewServiceProductID");
		if (productcodes != null && productcodes.containsKey(productID)) {
			needShowOccupLevel = "1";
		}
		String templateURL = "inspageone";
		return templateURL;
	}


	
	public void dealExcelModule(String productID) {
		PageModule pm = new PageModule();
		DataTable dt = pm.getExcelModule(productID);
		if (dt != null && dt.getRowCount() >= 1) {
			productExcelTemp = dt.getString(0, 0);
			String enName = dt.getString(0, 1);
			if (enName.indexOf("英文名") != -1) {
				excleTempEnName = "Y";
			}
		}
	}

	/**
	 * 
	 * 处理保障期限与计划
	 */
	private void setDutyFactorAndAmntNum(List<OrderRiskAppFactor> riskAppFactior2, List<OrderDutyFactor> dutyFactor2) {
		// 解决默认保费为0问题，删除掉多余代码
		if (riskAppFactior2.size() > 0) {
			setSession("riskAppFactior", riskAppFactior2);
			for (int i = 0; i < riskAppFactior2.size(); i++) {
				OrderRiskAppFactor orderRiskAppFactor = riskAppFactior2.get(i);

				// 计划列表
				List<FEMRiskFactorList> tFEMRiskFactorList = null;
				tFEMRiskFactorList = orderRiskAppFactor.getFactorValue();
				for (FEMRiskFactorList t : tFEMRiskFactorList) {
					if ("Plan".equals(orderRiskAppFactor.getFactorType())) {
						try {
							if (StringUtil.isNotEmpty(plan)) {
								plan = java.net.URLDecoder.decode(plan, "UTF-8");
							}
						} catch (Exception e) {
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
					//性别
					if ("Sex".equals(orderRiskAppFactor.getFactorType())) {
						if (Sex.equals(t.getFactorValue())) {
							String tSexName = t.getFactorDisplayValue();
							this.sdinformationAppnt.setApplicantSex(Sex);
							this.sdinformationAppnt.setApplicantSexName(tSexName);
						}
					}
					//年龄与出生日期
					if (this.sdinformation.getTextAge() == null || "".equals(this.sdinformation.getTextAge())) {
						if (StringUtil.isNotEmpty(applicantBirthday)) {
							String appAge = com.sinosoft.sms.messageinterface.pubfun.PubFun.getAgeNum(applicantBirthday);
							if (appAge.endsWith("D")) {
								this.sdinformation.setTextAge(appAge.substring(0, 1) + "天");
							} else {
								this.sdinformation.setTextAge(appAge.substring(0,appAge.indexOf("Y"))+"岁");
							}
						}
					}
					//购买份数
					if ("Mult".equals(orderRiskAppFactor.getFactorType())) {
						if (StringUtil.isNotEmpty(mult) && mult.equals(t.getFactorValue())) {
							sdinformation.setAppMult(mult);
						}
					}
					//职业
					if("Occup".equals(orderRiskAppFactor.getFactorType())){
						this.OccupLevel = t.getFactorValue();
					}
				}
			}
		}
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
				if (orderDutyFactor.getFdAmntPremList() == null || orderDutyFactor.getFdAmntPremList().size() <= 0 || orderDutyFactor.getFdAmntPremList().get(0) == null) {
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
				if (backup.get(i).toString().endsWith(".0")) {
					backNum = backNum + Integer.parseInt(backup.get(i).toString().substring(0, backup.get(i).toString().length() - 2));
				} else {
					backNum = backNum + Integer.parseInt(backup.get(i).toString());
				}
				
			}
		}
		return backNum.toString();
	}

	/**
	 * 设置目的地国家名称字段
	 * 
	 * @author sunny
	 * @param insuranceCode
	 * @param insured
	 * @param dictionaryService
	 */
	private String setDestinationCountry(String productId, String insuranceCode, SDInformationInsured insured, DictionaryService dictionaryService) {
		if ((null == DestinationCountry) || (null == dictionaryService) || StringUtil.isEmpty(DestinationCountry)) {
			return "";
		}

		// String[] destinations = insured.getDestinationCountry().split(",");
		String[] destinations = DestinationCountry.split(",");
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
				name = (this.dictionaryService.getCodeEnNameByCodeValue(insuranceCode, "CountryCode", CountryCode));
			} else {
				name = (this.dictionaryService.getNameByCodeTypePro(productId, insuranceCode, "CountryCode", CountryCode));
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
		int m = 0;
		DestinationCountry = "";
		for (String cCode : countrySet) {
			if (m > 0) {
				DestinationCountry = DestinationCountry + ",";
			}
			DestinationCountry = DestinationCountry + cCode;
			m = m + 1;
		}
		return destinationText.toString();
		// insured.setDestinationCountryText();
	}
	/**
	 * 通过身份证号获取生日和年龄
	 * 
	 */
	public void IDEntityTypeAndBirthdayRelationship(){
		String IdentityId ="",IdentityType="",IdentityTypeName="",Birthday="";
		IdentityId = this.sdinformationAppnt.getApplicantIdentityId();
		IdentityType = this.sdinformationAppnt.getApplicantIdentityType();
		IdentityTypeName = this.sdinformationAppnt.getApplicantIdentityTypeName();
		if("0".equals(IdentityType)||"01".equals(IdentityType)||"身份证".equals(IdentityTypeName)){
			Birthday = com.sinosoft.lis.pubfun.PubFun.getBirthdayFromId(IdentityId);
			this.sdinformationAppnt.setApplicantBirthday(Birthday);
		}
		
		for(int i=0;i<this.sdinformationinsuredList.size();i++){
			if(this.sdinformationinsuredList.get(i)!=null){
				IdentityId = this.sdinformationinsuredList.get(i).getRecognizeeIdentityId();
				IdentityType = this.sdinformationinsuredList.get(i).getRecognizeeIdentityType();
				IdentityTypeName = this.sdinformationinsuredList.get(i).getRecognizeeIdentityTypeName();
				if("0".equals(IdentityType)||"01".equals(IdentityType)||"身份证".equals(IdentityTypeName)){
					Birthday = com.sinosoft.lis.pubfun.PubFun.getBirthdayFromId(IdentityId);
					this.sdinformationinsuredList.get(i).setRecognizeeBirthday(Birthday);
				}
			}
		}
	}
	
	/**
	 * 保存订单信息
	 */
	public String saveOrder() {

		Map<String, Object> tData = new HashMap<String, Object>();
		IDEntityTypeAndBirthdayRelationship();
			try {
				// 保障期限为终身时的特殊处理	
				if(!"L".equals(this.protectionPeriodTy.toUpperCase())){
					sdinformation.setStartDate(sdf_1.parse(m_startDate));
					sdinformation.setEndDate(sdf_1.parse(m_endDate));
					
				}else{
					sdinformation.setStartDate(sdf_1.parse(m_startDate));
					sdinformation.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse("9999-12-31"));
				}
				//判断起保日期是否在有效选择期内,如果不在选择有效期内则设置为空让用户选择
				if(!verifyInsurStartDate(supplierCode2,productId,
						sdinformation.getStartDate())){
					logger.warn("保单起保日期不在可选择日期范围内，请重新输入！");
					tData.put("Flag", "Err");//
					tData.put("Msg", "保单起保日期不在可选择日期范围内，请重新输入！");
					JSONObject jsonObject = JSONObject.fromObject(tData);
					return ajax(jsonObject.toString(), "text/html");
				}
			} catch (ParseException e1) {
				logger.error("保单起保日期或保单止保日期格式错误，请重新输入！" + e1.getMessage(), e1);
				tData.put("Flag", "Err");//
				tData.put("Msg", "保单起保日期或保单止保日期格式错误，请重新输入！");// 错误信息
				JSONObject jsonObject = JSONObject.fromObject(tData);
				return ajax(jsonObject.toString(), "text/html");
			}
		String[] BaseInformation = new String[17];
		Member member = getLoginMember();
		try {
			mLMap = new LinkedHashMap<Object, String>();
			Map<String, Object> paramter = new HashMap<String, Object>();
			insTotalAmnt = new BigDecimal("0.00");
			productId = request.getParameter("productId");
			if (StringUtil.isEmpty(productId)) {
				productId = (String) getSession("productId");
			}
			quesFlag = questionPaper(productId);
			BaseInformation = (String[]) getSession(productId + "baseInformation");
			riskAppFactior = (List<OrderRiskAppFactor>) getSession(productId + "riskAppFactior");
			dutyFactor = (List<OrderDutyFactor>) getSession(productId + "dutyFactor");

			if (BaseInformation == null || BaseInformation.length <= 0 || riskAppFactior == null || riskAppFactior.size() <= 0 || dutyFactor == null || dutyFactor.size() <= 0) {

				paramter = sdorderService.getProductInformation(productId, "N", channelCode);// 产品ID

				BaseInformation = (String[]) paramter.get("baseInformation");// 产品基础数据
				riskAppFactior = (List<OrderRiskAppFactor>) paramter.get("riskAppFactor");
				dutyFactor = (List<OrderDutyFactor>) paramter.get("dutyFactor");
			}

			riskType = BaseInformation[7].toString();// 产品小类
			// BaseInformation = (String[]) this.getSession("baseInformation");
			String insuranceCompanySn = BaseInformation[2].toString();// 保险公司编码
			insuranceCompanySn = insuranceCompanySn.substring(0, 4);
			// String price = BaseInformation[6].toString();// 商品原价
			// String countPrice = BaseInformation[11].toString();// 折扣后价格
			sdorder.setPayStatus(SDPayStatus.unpaid);
			// sdorder.setProductTotalPrice(new BigDecimal(countPrice));

			sdinformation.setProductPrice(sdorder.getProductTotalPrice().toString());// 商品原价
			sdinformation.setProductDiscountPrice(sdorder.getTotalAmount()==null ? "0":sdorder.getTotalAmount().toString());// 折扣后价格
			// sdorder.setTotalAmount(sdorder.getProductTotalPrice());
			sdorder.setDiscountRates(BaseInformation[10].toString());// 折扣比率
			if (StringUtil.isNotEmpty(BaseInformation[10].toString())) {
				activityFlag = "1";
			} else {
				activityFlag = "0";
			}

			// add by cuishg 订单详细表（产品表）
			sdinformation.setProductId(BaseInformation[0].toString());// 产品ID
			sdinformation.setProductName(BaseInformation[1].toString());// 产品名称
			sdinformation.setDiscountRates(BaseInformation[10].toString()); // 产品折扣率
			sdinformation.setInsuranceCompany(insuranceCompanySn);// 保险公司编码
			sdinformation.setProductOutCode(BaseInformation[4].toString());// 保险公司产品编码
			sdinformation.setSupKindCode(BaseInformation[12].toString());// 保险公司险种编码，平安核保报文
			sdinformation.setRiskType(BaseInformation[5].toString());// 内部统计险种中类别
			sdinformation.setSubRiskType(BaseInformation[7].toString());// 内部统计险种小类别
			sdinformation.setProductHtmlFilePath("");// 产品详细页地址
			sdinformation.setProductQuantity("1");
			sdinformation.setPoint("");// 积分，，需要补充
			sdinformation.setPointStatus("");// 积分状态，需要补充
			sdinformation.setPrimitiveProductTitle(String.valueOf(BaseInformation[16]));
			// endDate需要特殊处理
			
			// 保障期限为终身时的特殊处理
			if (!"L".equals(this.protectionPeriodTy.toUpperCase())) {
				sdinformation.setEndDate(this.getEndDate(sdinformation.getEndDate()));

			} else {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				sdinformation.setEndDate(format.parse("9999-12-31"));
			}
		 
			if (member != null) {
				// 会员编码
				sdorder.setMemberId(getLoginMember().getId());
			}
			if ("1".equals(pointExchangeFlag) && member == null) {
				tData.put("Flag", "Err");//
				tData.put("Msg", "请登录后再兑换！");// 错误信息
				JSONObject jsonObject = JSONObject.fromObject(tData);
				logger.warn("请登录后再兑换！,产品编码:{}", productId);
				return ajax(jsonObject.toString(), "text/html");
			}
			
		} catch (Exception e) {
			tData.put("Flag", "Err");// 总保费
			tData.put(
					"Msg",
					"矮油，出了点小状况①,点击<a vlpageid=\"xiaoneng\" exturl=\"http://www.kaixinbao.com/xiaoneng\" id=\"qqwap2\" onclick=\"return(VL_FileDL(this));return false;\" href=\"javascript:void(0);\"><这里></a>！请客服MM帮帮忙吧！");// 错误信息
			JSONObject jsonObject = JSONObject.fromObject(tData);
			logger.error("产品信息异常" + jsonObject.toString() + e.getMessage(), e);
			return ajax(jsonObject.toString(), "text/html");
		}
		
		try {
			// 产品ID
			productId = sdinformation.getProductId();
			if (sdorder.getOrderStatus() != null) {
				orderStatus = sdorder.getOrderStatus().toString();
			}
			Set<SDInformationDuty> sdInformationDutyElementsSet = new HashSet<SDInformationDuty>();

			sdorder.setProductNum(1);
			orderSn = PubFun.GetOrderSn();
			KID = StringUtil.md5Hex(PubFun.getKeyValue() + orderSn);
			tData.put("kid", KID);
			sdorder.setOrderSn(orderSn);

			orderId = sdorder.getId();
			informationSn = PubFun.GetSDInformationSn();
			sdinformation.setInformationSn(informationSn);// 订单明细表编号
			sdinformation.setOrderSn(orderSn);// 保单号
			// 配置保险期限
			this.dealPeriod();

			sdinformation.setEnsureLimitType(this.protectionPeriodTy.toUpperCase());// 保障期限类型
			sdinformation.setEnsureLimit(this.protectionPeriodLast);// 保障期限

			Set<SDInformation> tSDInformationSet = new HashSet<SDInformation>();
			tSDInformationSet.add(this.sdinformation);
			sdorder.setSdinformationSet(tSDInformationSet);

			sdinformation.setSdorder(sdorder);

			// 保存投保人时将information的投保人信息进行更新
			Set<SDInformationAppnt> appSet = new HashSet<SDInformationAppnt>();
			
			//TODO 针对新华投保人性别异常特殊处理 req20150323004
			String applicantSex = StringUtil.noNull(sdinformationAppnt.getApplicantSex());
			if(applicantSex.indexOf("M") > -1){
				sdinformationAppnt.setApplicantSex("M");
			}else if(applicantSex.indexOf("F") > -1){
				sdinformationAppnt.setApplicantSex("F");
			}
			
			appSet.add(sdinformationAppnt);
			sdinformation.setSdinformationappntSet(appSet);
			// 保存责任信息
			sdinformation.setSdinformationDutySet(sdInformationDutyElementsSet);
			// 处理投保人信息
			sdinformationAppnt.setSdinformaiton(sdinformation);
			informationAppntSave(sdinformation);
			List<SDRelationAppnt> sdrelationappntList = sdrelationappntSave(sdinformationAppnt);
			// 保存旅游目的地

			// 处理被保人信息---多被保人-本人
			this.sdinformationOneInsuredSave(sdinformation, sdorder, this.sdinformationAppnt, quesFlag);
			// 处理被保人信息---多被保人-多人
			List<SDInformationInsured> tSDInformationInsuredList = this.sdinformationMulInsuredSave(sdorder, sdinformation, sdinformationAppnt, quesFlag, sdinformation.getRiskType());
			if (tSDInformationInsuredList == null || tSDInformationInsuredList.size() <= 0) {
				tSDInformationInsuredList = this.excelInsuredSave(sdorder, sdinformation, sdinformationAppnt, quesFlag, sdinformation.getRiskType());
			}
			// 处理保存产品投保要素
			// sdInformationInsuredElementsSave();
			// 处理责任信息
			List<SDInformationDuty> tSDInformationDutyList;
			if ("Y".equals(complicatedFlag) && StringUtil.isNotEmpty(dutyTempSerials)) {
				List<SDInformationDutyTemp> dutyTempList = sdInformationDutyTempService.getDutyTemp(dutyTempSerials);
				tSDInformationDutyList = informationInsuredDutySave(sdinformation, dutyTempList);
				mLMap.put(dutyTempList, "update");
			} else {
				tSDInformationDutyList = informationInsuredDutySave(sdinformation, null);
			}
			
			// 处理银行卡信息
			List<DirectPayBankInfo> tDirectPayBankInfoList = new ArrayList<DirectPayBankInfo>();
			if (directPayBankInfo != null) {
				tDirectPayBankInfoList = directPayBankInfoSave("");
			}

			sdorder.setSdinformationrisktypeSet(this.mSDInformationRiskTypeSet);
			// 处理订单项信息
			this.orderItemSave();

			// 校验保险期限，
			// 重新计算总保费,
			String errMessage = "";
			if (!"rid_td".equals(this.mulInsuredFlag)) {
				errMessage = this.relCalPrem(BaseInformation, riskAppFactior, dutyFactor, this.sdinformation);
			} else {
				errMessage = this.impRelCalPrem();
			}
			
			if (StringUtil.isNotEmpty(errMessage)) {
				tData.put("Flag", "Err");//
				tData.put("Msg", errMessage);// 错误信息
				JSONObject jsonObject = JSONObject.fromObject(tData);
				logger.info("产品编码:{}", productId + errMessage);
				return ajax(jsonObject.toString(), "text/html");
			}
			
			if ("1".equals(pointExchangeFlag)) {
				PointScalerUnit = Config.getConfigValue("PointScalerUnit");
				sdorder.setOrderIntegral(new BigDecimal(sdorder
						.getOffsetPoint()).divide(
						new BigDecimal(PointScalerUnit), 2,
						BigDecimal.ROUND_HALF_UP).toString());
				sdorder.setSumIntegral(sdorder.getOrderIntegral());

				BigDecimal nTotalAmnt = totalAmnt.multiply(new BigDecimal(PointScalerUnit)).setScale(0, BigDecimal.ROUND_UP);
				BigDecimal oTotalAmnt = new BigDecimal(sdorder.getOffsetPoint());
				BigDecimal oInsTotalAmnt = this.insTotalAmnt.multiply(new BigDecimal(PointScalerUnit)).setScale(0, BigDecimal.ROUND_UP);
				//后台增加保费==0 的校验
				Object[] argArr = {productId, nTotalAmnt, oTotalAmnt};
				if(nTotalAmnt.compareTo(new BigDecimal("0"))==0 || oTotalAmnt.compareTo(new BigDecimal("0"))==0){
					tData.put("Flag", "Err");//
					tData.put("Msg", "订单兑换积分数量为0,请重新提交试试！");// 错误信息
					JSONObject jsonObject = JSONObject.fromObject(tData);

					logger.info("订单兑换积分数量为0,产品编码:{},重新结算后积分数量:{},原积分数量:{}, 请重新提交试试！", argArr);
					return ajax(jsonObject.toString(), "text/html");
				}
				if (nTotalAmnt.compareTo(oTotalAmnt) != 0) {
					tData.put("Flag", "Err");//
					tData.put("Msg", "两次计算的保费不一样，请重新提交试试！");// 错误信息
					JSONObject jsonObject = JSONObject.fromObject(tData);
					logger.warn("两次计算的保费不一样，产品编码:{},重新结算后保费:{},原保费:{}, 请重新提交试试！", argArr);
					return ajax(jsonObject.toString(), "text/html");
				}
				if (nTotalAmnt.compareTo(oInsTotalAmnt) != 0) {
					tData.put("Flag", "Err");//
					tData.put("Msg", "保费计算有误，请重新提交试试，或联系客服MM");// 错误信息
					JSONObject jsonObject = JSONObject.fromObject(tData);
					logger.error("保费计算有误,产品编码：{},重新结算后保费:{},被保人总保费:{},请重新提交试试，或联系客服MM", argArr);
					return ajax(jsonObject.toString(), "text/html");
				}
				if (oTotalAmnt.compareTo(new BigDecimal(member
						.getCurrentValidatePoint())) > 0) {
					tData.put("Flag", "Err");//
					tData.put("Msg", "您的积分不够了，快去赚吧！");// 错误信息
					JSONObject jsonObject = JSONObject.fromObject(tData);
					argArr = new Object[] {member.getId(), member.getCurrentValidatePoint(), sdorder.getOffsetPoint()};
					logger.info("会员（{}）的可用积分（{}）小于订单要兑换用的积分（{}）", argArr);
					return ajax(jsonObject.toString(), "text/html");
				}
				
				
			} else {
				// 因为年险225801001的保费是固定的，所以不需要进行比较
				int count = new QueryBuilder("SELECT COUNT(*) FROM zdcode WHERE codetype = 'yearCash.riskCode' AND FIND_IN_SET('"+ productId +"', CodeValue);").executeInt();
				if (count == 0) {
					BigDecimal nTotalAmnt = totalAmnt.setScale(2, BigDecimal.ROUND_HALF_UP);
					BigDecimal oTotalAmnt = new BigDecimal(sdorder.getPayPrice()).setScale(2, BigDecimal.ROUND_HALF_UP);
					BigDecimal oInsTotalAmnt = this.insTotalAmnt.setScale(2, BigDecimal.ROUND_HALF_UP);

					//是否为赠险
					String FreePayProduct= Config.getConfigValue("FreePayProduct");
					String FreePayProductArray[]=FreePayProduct.split(",");
					for (int i = 0; i < FreePayProductArray.length; i++) {
						if(productId.trim().equals(FreePayProductArray[i].trim())){
							isAllFree="Y";
							break;
						}
					}
					Object[] argArr = {productId, nTotalAmnt, oTotalAmnt};
					//后台增加保费==0 的校验
					if(!"Y".equals(isAllFree)){
						if(nTotalAmnt.compareTo(new BigDecimal("0"))==0 || oTotalAmnt.compareTo(new BigDecimal("0"))==0){
							tData.put("Flag", "Err");//
							tData.put("Msg", "订单保费为0,请重新提交试试！");// 错误信息
							JSONObject jsonObject = JSONObject.fromObject(tData);
							logger.info("订单保费为0,产品编码:{},重新结算后保费:{},原保费:{}, 请重新提交试试！", argArr);
							return ajax(jsonObject.toString(), "text/html");
						}
					}
					if (nTotalAmnt.compareTo(oTotalAmnt) != 0) {
						tData.put("Flag", "Err");//
						tData.put("Msg", "两次计算的保费不一样，请重新提交试试！");// 错误信息
						JSONObject jsonObject = JSONObject.fromObject(tData);
						logger.warn("两次计算的保费不一样，产品编码:{},重新结算后保费:{},原保费:{}, 请重新提交试试！", argArr);
						return ajax(jsonObject.toString(), "text/html");
					}
					if (nTotalAmnt.compareTo(oInsTotalAmnt) != 0) {
						tData.put("Flag", "Err");//
						tData.put("Msg", "保费计算有误，请重新提交试试，或联系客服MM");// 错误信息
						JSONObject jsonObject = JSONObject.fromObject(tData);
						logger.error("保费计算有误,产品编码：{},重新结算后保费:{},被保人总保费:{},请重新提交试试，或联系客服MM", argArr);
						return ajax(jsonObject.toString(), "text/html");
					}
				}
			}
			
			/*
			 * 调用校验报销校验框架对投保信息进行校验
			 */
			if (SDOrderStatus.temptorysave.equals(sdorder.getOrderStatus())) {
				sdorder.setOrderStatus(SDOrderStatus.temptorysave);
				this.status = "update";
				tData.put("status", "update");//
			} else {
				// 暂存状态
				sdorder.setOrderStatus(SDOrderStatus.temptorysave);

				String tMessage = shopValidate(this.sdorder, this.sdinformation, this.sdinformationAppnt, this.sdinformationinsured, this.sdinformationinsuredList, this.sdinformationpropertyList,
						this.sdinsuredList, tSDInformationDutyList);
				if (tMessage != null && !"".equals(tMessage)) {
					tData.put("Flag", "Err");//
					tData.put("Msg", tMessage);// 错误信息
					tData.put("status", "save");//
					JSONObject jsonObject = JSONObject.fromObject(tData);
					logger.error("订单信息错误，{}", tMessage);
					return ajax(jsonObject.toString(), "text/html");
				}

			}
			/**
			 * 判断投保期间是否被修改过
			 * 
			 */
			String recognizeeBirthday = "";
			if (sdinformationinsured != null && StringUtil.isNotEmpty(sdinformationinsured.getRecognizeeBirthday())) {
				recognizeeBirthday = sdinformationinsured.getRecognizeeBirthday();
			} else {
				recognizeeBirthday = sdinformationinsuredList.size() == 1 ? sdinformationinsuredList.get(0).getRecognizeeBirthday() 
						: sdinformationAppnt.getApplicantBirthday();
			}
			if (!checkYeMoDa(sdinformation.getStartDate(), sdinformation.getEnsureLimit(), sdinformation.getEnsureLimitType(), sdinformation.getEndDate(), recognizeeBirthday)) {
					tData.put("Flag", "Err");
					tData.put("Msg", "订单止期与订单起期计算出的保险期限与选择期限的不一致，请重新选择试试！");// 错误信息
					JSONObject jsonObject = JSONObject.fromObject(tData);
					logger.info("订单止期与订单起期计算出的保险期限与选择期限的不一致，请重新选择试试！");
					return ajax(jsonObject.toString(), "text/html");
			}
			// 保存订单表
			mLMap.put(sdorder, "insert");
			
			// 保存订单详细表
			mLMap.put(sdinformation, "insert");
			// 保存投保人信息
			mLMap.put(sdinformationAppnt, "insert");
			if (sdrelationappntList != null && sdrelationappntList.size() >= 1) {
				mLMap.put(sdrelationappntList, "insert&update");
			}
			// 保存被保人信息----多被保人-本人存储
			if ("rid_me".equals(this.mulInsuredFlag) || "Y".equals(this.relationIsSelf)) {
				// mLMap.put(sdinformationinsured, "insert");
				// 购买份数改造--start
				List<SDInformationInsured> tInsuredOneList = new ArrayList<SDInformationInsured>();
				tInsuredOneList.add(sdinformationinsured);
				if (this.sdInsuredOtherList.size() >= 1) {
					for (SDInformationInsured insured : sdInsuredOtherList) {
						tInsuredOneList.add(insured);
					}
				}
				mLMap.put(tInsuredOneList, "insert");
				// 购买份数改造--end
				mLMap.put(this.questionPaper, "insert");
			}
			// 保存被保人信息----与被保人关系只有个“本人”
			/*
			 * if ("Y".equals(this.relationIsSelf)) {
			 * mLMap.put(sdinformationinsured, "insert"); }
			 */
			// 保存被保人信息
			if (tSDInformationInsuredList != null && tSDInformationInsuredList.size() >= 1) {
				if (this.sdInsuredOtherList.size() >= 1) {
					for (SDInformationInsured insured : sdInsuredOtherList) {
						tSDInformationInsuredList.add(insured);
					}
				}
				mLMap.put(tSDInformationInsuredList, "insert");
			}
			if ("rid_orther".equals(this.mulInsuredFlag)) {
				List<SDRelationRecognizee> recognizeeList = this.sdrelationrecognizeeSave(tSDInformationInsuredList);
				mLMap.put(recognizeeList, "insert&update");
			}
			// 保存保单信息
			List<SDInformationRiskType> tSDInformationRiskTypeList = new ArrayList<SDInformationRiskType>();
			for (SDInformationRiskType tSDInformationRiskType : mSDInformationRiskTypeSet) {
				tSDInformationRiskType.setApplyPolicyNo("");
				tSDInformationRiskTypeList.add(tSDInformationRiskType);
			}
			if (this.sdRiskTypeOtherList.size() >= 1) {
				for (SDInformationRiskType risktype : sdRiskTypeOtherList) {
					risktype.setApplyPolicyNo("");
					tSDInformationRiskTypeList.add(risktype);
				}
			}
			
			if ("1".equals(pointExchangeFlag)) {
				int lastNum = giftClassifyService.getLastNum(productId);
				if (lastNum == 0) {
					tData.put("Flag", "Err");//
					tData.put("Msg", "矮油，您兑换的商品已全部兑换完了！请兑换其他商品！");// 错误信息
					JSONObject jsonObject = JSONObject.fromObject(tData);
					logger.info("矮油，您兑换的商品已全部兑换完了！请兑换其他商品！查询库存量为0，产品编码：{}", productId);
					return ajax(jsonObject.toString(), "text/html");
				}
				if (lastNum < tSDInformationRiskTypeList.size()) {
					tData.put("Flag", "Err");//
					tData.put("Msg", "矮油，您兑换的商品库存量只有"+ lastNum +"个了！请减少份数！");// 错误信息
					logger.info("矮油，您兑换的商品库存量只有{}个了！请减少份数！产品编码：{}", lastNum, productId);
					JSONObject jsonObject = JSONObject.fromObject(tData);
					return ajax(jsonObject.toString(), "text/html");
				}
				GiftClassify gift = giftClassifyService.getGiftClassifyByProductId(productId);
				
				PointExchangeInfo pointExchangeInfo = new PointExchangeInfo();
				pointExchangeInfo.setProductid(sdinformation.getProductId());
				pointExchangeInfo.setProductName(sdinformation.getProductName());
				pointExchangeInfo.setType("1");
				pointExchangeInfo.setPoints(sdorder.getOffsetPoint());
				pointExchangeInfo.setOrderSn(sdorder.getOrderSn());
				pointExchangeInfo.setMemberid(sdorder.getMemberId());
				pointExchangeInfo.setProp2(Constant.WJ_JFSC_CHANNELSN);
				if (gift != null) {
					pointExchangeInfo.setGiftClassifyID(gift.getId());
				}
				pointExchangeInfo.setStatus(String.valueOf(sdorder.getOrderStatus().ordinal()));
				mLMap.put(pointExchangeInfo, "insert");
			}
			mLMap.put(tSDInformationRiskTypeList, "insert");
			// 处理被保人购买份数--被保人信息
			/*
			 * if(this.sdInsuredOtherList.size()>=1){
			 * mLMap.put(this.sdInsuredOtherList, "insert");
			 * mLMap.put(this.sdRiskTypeOtherList, "insert"); }
			 */
			// 保存高额信息
			if (this.questionList != null && questionList.size() >= 1) {
				mLMap.put(questionList, "insert");
			}

			// 保存sdorderitemoth
			mLMap.put(this.sdorderitemothList, "insert");
			// 处理健康告知
			// 续保不处理健康告知
			if(StringUtil.isEmpty(sdorder.getRenewalId())){
				List<SDInsuredHealth> tSDInsuredHealthList = sdinsuredHealtySave(this.sdinformationinsuredList, sdinformationinsured, sdinformation, order_healthySn);
				if (tSDInsuredHealthList != null && tSDInsuredHealthList.size() >= 1) {
					mLMap.put(sdinsuredHealthList, "delete");
					mLMap.put(tSDInsuredHealthList, "insert");
				}
			}

			// 责任信息存储
			mLMap.put(tSDInformationDutyList, "insert");
			// 保存产品投保要素信息
			// mLMap.put(informationInsuredElements, "insert");
			// 现产品投保要素与被保人关联
			mLMap.put(informationInsuredElementsList, "insert");
			// 保存财产信息
			List<SDInformationProperty> tSDInformationPropertyList = new ArrayList<SDInformationProperty>();
			for (SDInformationProperty sdp : mSDInformationPropertySet) {
				tSDInformationPropertyList.add(sdp);
			}
			if (tSDInformationPropertyList.size() > 0) {
				mLMap.put(tSDInformationPropertyList, "insert");
			}
			
			//TODO 新增受益人 req20150323004
			List<SDInformationBnf> saveBnfList = new ArrayList<SDInformationBnf>();

			//判断如果受益人有数据，则默认取第一个被保人的信息，用于解决单被保人sdinformationinsured对象为空的问题
			//目前只支持单被保人下的指定受益人操作
			if(sdinformationbnfList.size()>0 && tSDInformationInsuredList.size()>0){
				sdinformationinsured = tSDInformationInsuredList.get(0);
			}
			
			for (SDInformationBnf sdinformationbnf : sdinformationbnfList) {
				if(sdinformationbnf == null){
					continue;
				}
				sdinformationbnf.setId(null);
				sdinformationbnf.setOrderSn(orderSn);
				sdinformationbnf.setInformationSn(informationSn);
				sdinformationbnf.setInformationSn2(informationSn);
				sdinformationbnf.setRecognizeeSn(sdinformationinsured.getRecognizeeSn());
				sdinformationbnf.setSdinformationInsured(sdinformationinsured);
				sdinformationbnf.setBnfSexName(this.dictionaryService.getNameByCodeTypePro(sdinformation.getProductId(), sdinformation.getInsuranceCompany(), "Sex", sdinformationbnf.getBnfSex()));
				sdinformationbnf.setRelationToInsuredName(this.dictionaryService.getNameByCodeTypePro(sdinformation.getProductId(), sdinformation.getInsuranceCompany(), "BnfRelationship", sdinformationbnf.getRelationToInsured()));
				sdinformationbnf.setBnfIDTypeName(this.dictionaryService.getNameByCodeTypePro(sdinformation.getProductId(), sdinformation.getInsuranceCompany(), "certificate", sdinformationbnf.getBnfIDType()));
				// 证件有效期勾选长期时
				if (StringUtil.isNotEmpty(sdinformationbnf.getBnfStartID()) && StringUtil.isEmpty(sdinformationbnf.getBnfEndID())) {
					sdinformationbnf.setBnfEndID("9999-12-31");
				}
				saveBnfList.add(sdinformationbnf);
			}
			if (!"0".equals(benefitOperate) && saveBnfList.size() > 0) {
				mLMap.put(saveBnfList, "insert");
			}
			
			// 保存银行卡信息
			if (tDirectPayBankInfoList.size() > 0) {
				mLMap.put(tDirectPayBankInfoList, "insert");
			}
			
			// 保存订单项
			mLMap.put(this.sdorderitem, "insert");
			// 线下核保关联订单
			Map<String, String> dealResult = dealUnderwritingOffline(UnderwritingOfflineCode, this.sdinformationinsuredList, sdinformationinsured, orderSn);
			if ("Err".equals(dealResult.get("Flag"))) {
				JSONObject jsonObject = JSONObject.fromObject(dealResult);
				return ajax(jsonObject.toString(), "text/html");
			}
			
			// 保存所有信息
			if (!this.mDealDataService.saveAll(mLMap)) {
				addActionError("矮油，出了点小状况②,点击<a vlpageid=\"xiaoneng\" exturl=\"http://www.kaixinbao.com/xiaoneng\" id=\"qqwap2\" onclick=\"return(VL_FileDL(this));return false;\" href=\"javascript:void(0);\"><这里></a>！请客服MM帮帮忙吧！");
				logger.error("点击 下一步，订单保存失败！");
				return ERROR;
			}

			// 存储代客支付和原始订单号属性值
			String flag = (String) getSession(productId + "buyForCustomerFlag");
			String oldsn = (String) getSession(productId + "oldOrdersn");
			if (StringUtil.isNotEmpty(flag) 
					|| StringUtil.isNotEmpty(oldsn)) {
				try{
					BuyForCustomerOldSnSchema buyForCustomerOldSnSchema = new BuyForCustomerOldSnSchema();
					buyForCustomerOldSnSchema.setid(NoUtil.getMaxNo("buyForCustomerOldSn"));
					buyForCustomerOldSnSchema.setOrdersn(orderSn);
					buyForCustomerOldSnSchema.setBuyForCustomerFlag(flag);
					buyForCustomerOldSnSchema.setOldOrderSn(oldsn);
					buyForCustomerOldSnSchema.setAddTime(new Date());
					if (null != getLoginMember()) {
						buyForCustomerOldSnSchema.setAddUser(getLoginMember().getId());
					}
					buyForCustomerOldSnSchema.insert();
				} catch (Exception e) {
					logger.error("存储代客支付和原始订单号属性值异常！" + e.getMessage(),e);
				}
				// 使用完删除session
				setSession(productId + "buyForCustomerFlag", null);
				setSession(productId + "oldOrdersn", null);
			}
			
			updateUnderwritingOffline(UnderwritingOfflineCode, orderSn);
			
			// 判断该产品是否需要核保
			this.setNeedUWCheckFlag(this.getNeedUWFlagFromDB(sdinformation.getInsuranceCompany()));
			String ordersn = (String) getSession("OrderSn");
			orderId = sdorder.getId();
			// req20130520001 王昌旸 add start
			//OrderInfoCommit.infoCommitForEmar(getRequest(), sdorder, sdinformation);
			// req20130520001 王昌旸 add end

			// ---- yushanchun 获取下订单ip 2013-01-29 start
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
				OrderSeeker.orderInfoInsert(sdorder.getOrderSn(), ip);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}

			tData.put("OrderSn", this.orderSn);
			tData.put("OrderId", this.sdorder.getId());
			// 处理状态
			if (quesFlag) {
				tData.put("Flag", "Question");
			} else {
				tData.put("Flag", "Suc");
			}
			
			// 页面提示信息
			tData.put("orderFlag", orderFlag);
			tData.put("Msg", "订单保存成功！");
			JSONObject jsonObject = JSONObject.fromObject(tData);

			//壹佰金、金融界、货运宝渠道
			if (StringUtil.isNotEmpty(this.channelCode)) {
				if ("02".equals(channelCode)) {
					
					recordCPS(orderSn, BaseInformation[1].toString(), sdorder.getTotalAmount().toString());
				}
				//更新cps子渠道,以下主要處理商務聯盟共建頻道、代理人分銷個人網站
				String cps_sub_channelSn = channelCode;
				if("05".equals(channelCode)){
					cps_sub_channelSn = "swpt";
				}else if("06".equals(channelCode)){
					cps_sub_channelSn = "dlr";
				}
				new QueryBuilder(" update sdorders set channelsn = 'cps_"+cps_sub_channelSn+"' where ordersn='"+this.orderSn+"'").executeNoQuery();
			}
			// CPS 分销联盟使用，读取用户Cookie
			Cookie ck;
			ck = CookieUtil.getCookieByName(request, "cpsUserId");
			if (!StringUtil.isEmpty(ck)) {
				if (!StringUtil.isEmpty(ck.getValue())) {
					String cpsUserId = ck.getValue();
					ck = CookieUtil.getCookieByName(request, "cpsUserSource");
					String cpsUserource = ck.getValue();
					String tCpsUserource = cpsUserource;
					String partners_uid = "";
					String channelsn = "cps_swpt";//默认商务平台
					
					if(StringUtil.isNotEmpty(CpsAction.isPartners(cpsUserId, "1"))){
						Cookie p_ck = CookieUtil.getCookieByName(request, "partners_uid");
						if (!StringUtil.isEmpty(p_ck)) {
							partners_uid = p_ck.getValue();
						}
						if(StringUtil.isNotEmpty(partners_uid)){
							channelsn = "cps_"+cpsUserId;
						}
					}
					if(cpsUserource.indexOf("_")!=-1){
						String[] arr = cpsUserource.split("_");
						tCpsUserource = arr[0];
						channelsn = "cps_"+arr[1];  
						
					}
					//判断从cookie中来源渠道是否为空
					Cookie ckcha = CookieUtil.getCookieByName(request, "channelSn"); 
					if (!StringUtil.isEmpty(ckcha)) {
						if (!StringUtil.isEmpty(ckcha.getValue())) {
							channelsn = ckcha.getValue();
						}
					}
					// 一起发、59秒分支
					if("emar_cps".equals(cpsUserource) || "59miao".equals(cpsUserource)){
						OrderInfoCommit.infoCommitForEmar(request, sdorder, sdinformation,cpsUserId,cpsUserource);
					}else{
						if(StringUtil.isNotEmpty(sdorder.getRenewalId())){
							ZhenAiRenewalSchema renewal = new ZhenAiRenewalSchema();
							renewal.setID(sdorder.getRenewalId());
							renewal.fill();
							String cpsNo = renewal.getCspNo();
							if(StringUtil.isNotEmpty(cpsNo)){
								//更新订单渠道
								String oldChannel = new QueryBuilder("select channelsn from sdorders "
										+ "where ordersn = (select orderSn from sdinformationrisktype where policyNo = ?)",renewal.getPolicyNo()).executeString();
								new QueryBuilder(" update sdorders set channelsn = '"+oldChannel+"' where ordersn='" + orderSn + "'").executeNoQuery();
								
								logger.info("更新cps订单渠道为:{}", oldChannel);
								
								if(StringUtil.isNotEmpty(cpsUserId)){
									cpsNo=cpsUserId;
								}
								recordCPS(cpsNo,"","pc", "05", orderSn, sdinformation.getProductName(), sdorder.getTotalAmount().toString());
							}
						}
						else{
							new QueryBuilder(" update sdorders set channelsn = '"+channelsn+"' where ordersn='" + orderSn + "'").executeNoQuery();
							recordCPS(cpsUserId,partners_uid,"pc", tCpsUserource, orderSn, sdinformation.getProductName(), sdorder.getTotalAmount().toString());
						}
					}
				
				}
			}
			
			
			
			if ("cps".equals(sourceFlag)) {
				recordCPS("admin", "","","admin", orderSn, sdinformation.getProductName(), sdorder.getTotalAmount().toString());
				new QueryBuilder(" update sdorders set channelsn = 'cps' where ordersn='" + orderSn + "'").executeNoQuery();
			}else if ("tb".equals(sourceFlag)||"qunar".equals(sourceFlag)) {
				//modified by yuzj for 淘宝蚂蚁的处理 @20160701 begin
				if(orderSn.startsWith("TBMY")){
					new QueryBuilder(" update sdorders set channelsn = 'tb_my' where ordersn='" + orderSn + "'").executeNoQuery();
				}else if(orderSn.startsWith("QN")){
					new QueryBuilder(" update sdorders set channelsn = 'qunar' where ordersn='" + orderSn + "'").executeNoQuery();
				}else{
					new QueryBuilder(" update sdorders set channelsn = 'tb_ht' where ordersn='" + orderSn + "'").executeNoQuery();
				}
				//modified by yuzj for 淘宝蚂蚁的处理 @20160701 end
			}
			return ajax(jsonObject.toString(), "text/html");
		} catch (Exception e) {
			logger.error("点击 下一步,处理订单失败，订单保存失败,失败原因：" + e.getMessage(), e);
			tData.put("OrderSn", this.orderSn);// 总保费
			tData.put("Flag", "Err");// 总保费
			tData.put(
					"Msg",
					"矮油，出了点小状况③,点击<a vlpageid=\"xiaoneng\" exturl=\"http://www.kaixinbao.com/xiaoneng\" id=\"qqwap2\" onclick=\"return(VL_FileDL(this));return false;\" href=\"javascript:void(0);\"><这里></a>！请客服MM帮帮忙吧！");// 错误信息
			JSONObject jsonObject = JSONObject.fromObject(tData);
			return ajax(jsonObject.toString(), "text/html");

		}
	}

	/**
	 * 更新线下核保信息，关联订单
	 * @param code
	 * @param orderSn
	 */
	private void updateUnderwritingOffline(String code, String orderSn) {
		if (StringUtil.isNotEmpty(code)) {
			int result = new QueryBuilder("update underwriting_offline_healthinfo set orderSn = ? where offlineCode = ?", orderSn, code).executeNoQuery();
			if (result < 1) {
				logger.error("更新线下核保信息失败！订单号：{}，线下核保编码：{}", orderSn, code);
			}
		}
	}
	
	private Map<String, String> dealUnderwritingOffline(String code, List<SDInformationInsured> tList, SDInformationInsured insured, String newOrderSn) {
		Map<String, String> tData = new HashMap<String, String>();
		tData.put("Flag", "Succ");
		if (StringUtil.isEmpty(code)) {
			return tData;
		}
		SDInformationInsured cIns = new SDInformationInsured();
		if ("rid_me".equals(this.mulInsuredFlag) || "Y".equals(this.relationIsSelf)) {
			cIns = insured;
		} else {
			if (tList != null && tList.size() >= 1) {
				for (SDInformationInsured t : tList) {
					if (t != null) {
						cIns = t;
					}
				}
			}
		}
		
		DataTable dt = new QueryBuilder("select i.name,h.IdNo,i.underwriting_status,h.orderSn from underwriting_offline_healthinfo h, underwriting_offline_info i where h.offlineCode = ? and h.info_id=i.id", code).executeDataTable();
		if (dt == null || dt.getRowCount() < 1) {
			tData.put("Flag", "Err");
			tData.put("Msg", "您填写的核保编码无效，请核实！");
		} else {
			String name = dt.getString(0, 0);
			String idNo = dt.getString(0, 1);
			String status = dt.getString(0, 2);
			String oldOrderSn = dt.getString(0, 3);
			if (StringUtil.isNotEmpty(oldOrderSn) && !oldOrderSn.equals(newOrderSn) ) {
				tData.put("Flag", "Err");
				tData.put("Msg", "您填写的核保编码已使用，不能再次使用！");
			} else if (!"1".equals(status)) {
				tData.put("Flag", "Err");
				tData.put("Msg", "该线下核保未通过，不能使用该核保编码！");
			} else if (!name.equalsIgnoreCase(cIns.getRecognizeeName())) {
				tData.put("Flag", "Err");
				tData.put("Msg", "您线下核保的姓名与被保人姓名不符，不能进行线下核保！");
			} else if (!idNo.equalsIgnoreCase(cIns.getRecognizeeIdentityId())) {
				tData.put("Flag", "Err");
				tData.put("Msg", "您线下核保的证件号与被保人证件号不符，不能进行线下核保！");
			}
		}
		
		return tData;
	}
	
	/**
	 * 取得活动信息
	 * @param productId
	 * @param orderSn
	 */
	private void getActivityInfo(String productId, String channelsn) {
		SalesVolumeAction salesVolumeAction = new SalesVolumeAction();
		if (StringUtil.isEmpty(channelsn)) {
			channelsn = "wj";
		}
		Map<String, String> map = salesVolumeAction.shoppingShowActivity(productId, channelsn);
		if (!"1".equals(activityFlag)) {
			activityFlag = map.get("activityFlag");
		}
		// 折扣活动是否是会员活动
		memberActivity = map.get("DiscountMemberChannel");
		activityInfo = map.get("activityInfo");
	}
	
	/**
	 * 获得购买送积分信息
	 * @param orderSn
	 * @param productId
	 * @param memberId
	 */
	private void getSendPoint(String orderSn, String productId, String memberId) {
		String PointScalerUnit = Config.getConfigValue("PointScalerUnit");
		SDOrder order = sdorderService.getOrderByOrderSn(orderSn);
		Map<String, String> ProductIDMap = new HashMap<String, String>();
		ProductIDMap.put(productId, order.getTotalAmount().toString());
		Map<String, Map<String, String>> resultProductDetail = ActivityCalculate.ProductCalculate(ProductIDMap, orderSn, order.getChannelsn());
		Map<String, String> productDetail = resultProductDetail.get(productId);

		String rAmount = productDetail.get("Amount");
		String rIntegral = productDetail.get("Integral");
		
		BigDecimal nowPrem = new BigDecimal(0);
		if (StringUtil.isNotEmpty(rAmount)) {
			nowPrem = new BigDecimal(rAmount);
		}
		int integral = 0;
		if (StringUtil.isNotEmpty(rIntegral)) {
			integral = Integer.parseInt(rIntegral);
		}
		String baseIntegral = nowPrem.multiply(new BigDecimal(PointScalerUnit)).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
		
		// 获得积分的数量用现价进行换算 一元得一积分，不满一元不送积分{
		if (StringUtil.isNotEmpty(memberId) && integral > 0) {
			String productGivePointsPercent = productDetail.get("pointrate");//产品赠送积分比例
			integral = memCalInteger(integral, memberId, baseIntegral,productGivePointsPercent);
		}
		
		sendPoints = String.valueOf(integral);
		sendPointsValue = new BigDecimal(integral).divide(new BigDecimal(PointScalerUnit), 1, BigDecimal.ROUND_HALF_UP).toString();
	}
	
	private int memCalInteger(int integral, String memberId, String baseIntegral,String productGivePointsPercent) {
		//会员等级
		Map<String,String>  map_result_grade=ActivityCalculate.getMemberGradeBirthdayPoints(IntegralConstant.POINT_SOURCE_BUY, memberId, baseIntegral,productGivePointsPercent);
		String flag_grade=map_result_grade.get("flag");
		if("true".equals(flag_grade)){
			String MemberGrade=map_result_grade.get("MemberGrade");
			integral = (integral + Integer.parseInt(map_result_grade.get("addpoints")));
			memGradeDesc = "您是"+MemberGrade+"会员,保单生效后可获得";
		}
		//会员生日月
		Map<String,String>  map_result=ActivityCalculate.getMemberGradeBirthdayPoints(IntegralConstant.POINT_SOURCE_BIRTH_MONTH,memberId, baseIntegral,productGivePointsPercent);
		String flag=map_result.get("flag");
		if("true".equals(flag)){
			String MemberGrade=map_result.get("MemberGrade");
			integral = (integral + Integer.parseInt(map_result.get("addpoints")));
			memGradeDesc = MemberGrade+"会员生日月享特权,保单生效后特别赠送";
		}
		
		return integral;
	}
	private void recordCPS(String ordersn, String productName, String totleprice) {
		GetDBdata db = new GetDBdata();
		try {
			String sql_insert = "insert into cps(id,cid,wi,`on`,ta,pna,dt,pp,sd,ud,os,ps,pw,openid,oo) values(LAST_INSERT_ID(id),'','','" + ordersn + "','1','" + productName + "','web','"
					+ totleprice + "'," + "date('" + PubFun.getCurrent() + "') " + ",date('" + PubFun.getCurrent() + "'),'','','','','" + channelCode + "') ";
			db.execUpdateSQL(sql_insert, null);
		} catch (Exception e) {
			logger.error("合作商城记录csp表失败，类型为"+ channelCode + ",订单号" +  ordersn + e.getMessage(), e);
		}
	}

	/**
	 * CPS分销联盟记录.
	 * 
	 * @param cpsUserId
	 *            用户ID.
	 * @param cpsUserource
	 *            来源：1.图片广告 2.链接转换 3.产品直连.
	 * @param ordersn
	 *            订单号.
	 * @param productName
	 *            产品名称.
	 * @param totleprice
	 *            价格.
	 */
	public static void recordCPS(String cpsUserId,String uID, String channel,String cpsUserource, String ordersn, String productName, String totleprice) {
		GetDBdata db = new GetDBdata();
		try {
			String sql_insert = "insert into cps(id,cid,wi,`on`,ta,pna,dt,pp,sd,ud,os,ps,pw,openid,oo) " +
					"values(LAST_INSERT_ID(id),'" + cpsUserId + "','"+uID+"','" + ordersn + "','1','" + productName
					+ "','cps','" + totleprice + "'," + "date('" + PubFun.getCurrent() + "') " + ",date('" + PubFun.getCurrent() + "'),'" + cpsUserource + "','','"+channel+"','','05') ";
			db.execUpdateSQL(sql_insert, null);
		} catch (Exception e) {
			logger.error("CPS记录csp表失败，类型为" + channel + ",订单号，" + ordersn + e.getMessage(), e);
		}
	}

	/**
	 * 判断是否产品已经下架的公共方法
	 * 
	 * @param productId
	 * @param productName
	 * @param message
	 * @return
	 */
	private String isProductDownShelf(String productId, String productName, String message) {
		if (null == productId) {
			addActionError("查询产品时传入参数错误！");
			logger.error("{}-核保失败：productId-{}- 查询产品时传入参数错误！",message,  productId);
			return ERROR;
		}
		logger.info("产品id：========{}", productId);
		String sql = "select LimitCount,Occup,SectionAge,IsPublish from sdproduct where productid=?";
		String[] sqltemp = { productId };
		GetDBdata db = new GetDBdata();
		List<HashMap<String, String>> sdproduct;
		try {
			sdproduct = db.query(sql, sqltemp);
			if (sdproduct == null || sdproduct.size() != 1) {
				addActionError("该订单中险种" + productName + "不存在，无法" + message + "！");

				logger.error("该订单中险种{}不存在，无法{}!", productName,message);
				return ERROR;
			}
			HashMap<String, String> product = sdproduct.get(0);
			String IsPublish = product.get("IsPublish");
			if (StringUtil.isEmpty(IsPublish) || "N".equals(IsPublish)) {
				addActionError("该订单中险种" + productName + "已经下架，无法" + message + "！");
				logger.error("该订单中险种{}已经下架，无法{}!", productName, message);
				return ERROR;
			}
			return SUCCESS;
		} catch (Exception e) {
			addActionError("查询产品失败！");
			logger.error("查询产品失败！" + e.getMessage(), e);
			return ERROR;
		}
	}

	/**
	 * 会员中心继续录入
	 * 
	 * @return
	 */
	public String keepInput() {
		try {
			String nKID = StringUtil.md5Hex(PubFun.getKeyValue() + orderSn);
			if (!nKID.equals(KID)) {
				// addActionError("矮油，出了点小状况,点击<a href=\"javascript:openChat('http://wpa.b.qq.com/cgi/wpa.php?ln=1&key=XzgwMDExODA2Nl8yNzg4MDVfODAwMTE4MDY2XzJf');\"><这里></a>！请客服MM帮帮忙吧！");
				return ERROR;
			}
			sdorder = sdorderService.getOrderByOrderSn(orderSn);
			sdinformation = sdorder.getSdinformationSet().iterator().next();
			if (sdorder == null) {
				addActionError("亲，这个订单不存在了①，请重新提交或者请联系客服MM!");
				logger.error("继续录入时，订单不存在，orderSn:{}", orderSn);
				return ERROR;
			}

			/* zhangjinquan 11180 2012-10-10 增加产品下架判断 start */
			if (ERROR.equals(isProductDownShelf(sdinformation.getProductId(), sdinformation.getProductName(), "继续录入"))) {

				logger.error("继续录入-核保失败：orderSn-{}- 订单状态：{}", orderSn, sdorder.getOrderStatus());

				if (!sdorder.getOrderStatus().equals(OrderStatus.paid)) {
					logger.error("继续录入-核保失败：orderSn-{} - 修改订单状态为自动取消。", orderSn);
					/* 下架产品状态自动修改为自动取消 */
					sdorder.setOrderStatus(SDOrderStatus.autoinvalid);
					sdorderService.update(sdorder);
					if (Constant.JFSC_CHANNELSN.equals(sdorder.getChannelsn())
							|| Constant.WJ_JFSC_CHANNELSN.equals(sdorder
									.getChannelsn())
							|| Constant.WAP_JFSC_CHANNELSN.equals(sdorder
									.getChannelsn())) {
						Transaction trans = new Transaction();
						trans.add(new QueryBuilder(
								"update PointExchangeInfo set status = ? where orderSn = ?",
								String.valueOf(sdorder.getOrderStatus()
										.ordinal()), sdorder.getOrderSn()));
						if (!trans.commit()) {
							logger.error("积分兑换保险产品，积分兑换表更新订单自动取消状态失败，orderSn:{}",  orderSn);
						}
					}
				}

				addActionError("亲，这个产品已经不存在了，请重新提交或者请联系客服MM!");
				logger.error("继续录入时，订单不存在，orderSn:{}", orderSn);
				return ERROR;
			}
			dealLimitCount(sdinformation);
			String healthyPage = healthyInput(orderSn, status);
			if (StringUtil.isNotEmpty(healthyPage)) {
				return healthyPage;
			}
			String orderSat = sdorder.getOrderStatus() + "";
			if ("paid".equals(orderSat)) {
				return orderStatusPaidCheck(sdorder);

			} else {
				orderId = sdorder.getId();
				String pageString = buyNowUpdate();
				return pageString;
			}
		} catch (Exception e) {
			addActionError("矮油，出了点小状况①。再试一次吧！");
			return ERROR;
		}

	}

	public String healthyInput(String cOrder_healthySn, String cStatus) {
		if (StringUtil.isNotEmpty(cOrder_healthySn) && !"updateHealthy".equals(cStatus)) {
			sdinsuredHealthList = sdinsuredHealthService.getInfoByOrderHealthySn(cOrder_healthySn);
			if (sdinsuredHealthList == null || sdinsuredHealthList.size() <= 0) {
				sdinsuredHealthList = orderConfigNewService.getInsuredHealthy(healthyInfoService, sdinsuredHealthService, sdinformation);
			}
			if (sdinsuredHealthList != null && sdinsuredHealthList.size() >= 1) {
				return "healthy";
			}
		}
		return "";
	}

	/**
	 * 返回修改--调用
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String buyNowUpdate() {
		//insuredbirthday判断截止到80周岁，计算继续录入的订单的截止日期所用
		String insuredbirthday="";
		try {
			String nKID = StringUtil.md5Hex(PubFun.getKeyValue() + orderSn);
			if (!nKID.equals(KID)) {
				// addActionError("矮油，出了点小状况,点击<a href=\"javascript:openChat('http://wpa.b.qq.com/cgi/wpa.php?ln=1&key=XzgwMDExODA2Nl8yNzg4MDVfODAwMTE4MDY2XzJf');\"><这里></a>！请客服MM帮帮忙吧！");
				return ERROR;
			}
			// 订单信息
			sdorder = sdorderService.getOrderByOrderSn(orderSn);
			if (sdorder == null) {
				sdorder = sdorderService.get(orderId);

			}
			sdorderitem = sdorder.getSdorderitem();
			typeFlag = sdorderitem.getTypeFlag();
			channelCode = sdorderitem.getChannelCode();
			//modified by yuzj for 淘宝蚂蚁的处理 @20160701 begin
			if("tb_ht".equals(sdorder.getChannelsn())||"tb_my".equals(sdorder.getChannelsn())){
				sourceFlag = "tb";
			}else if("qunar".equals(sdorder.getChannelsn())){
				sourceFlag = "qunar";
			}
			//modified by yuzj for 淘宝蚂蚁的处理 @20160701 end
			if (Constant.JFSC_CHANNELSN.equals(sdorder.getChannelsn())
					|| Constant.WJ_JFSC_CHANNELSN.equals(sdorder
							.getChannelsn())
					|| Constant.WAP_JFSC_CHANNELSN.equals(sdorder
							.getChannelsn())) {
				pointExchangeFlag = "1";
				PointScalerUnit = Config.getConfigValue("PointScalerUnit");
			}
			if (StringUtil.isNotEmpty(sdorder.getDiscountRates())) {
				activityFlag = "1";
			} else {
				activityFlag = "0";
			}
			
			userCode = sdorderitem.getChannelAgentCode();
			if (!this.checkPayStatus(sdorder.getId())) {
				addActionError("这个订单已经支付过了，不要重复支付哟！！");
				logger.info("buyNowUpdate-订单已经支付过了，不要重复支付哟！！id:{} ordersn:{}", sdorder.getId(), sdorder.getOrderSn());
				return ERROR;
			}
			// 订单详细信息
			Set<SDInformation> sdinformationSet = sdorder.getSdinformationSet();
			for (SDInformation infmn : sdinformationSet) {
				sdinformation = infmn;
			}
			this.supplierCode2 = sdinformation.getInsuranceCompany();
			productId = sdinformation.getProductId();
			init(sdinformation.getInsuranceCompany());
			//"000701007,000701010,000701011"
			String specialEffDateProductIds=new QueryBuilder("SELECT CodeValue FROM zdcode WHERE CodeType='specialEffDateFlag' and CodeValue=?", productId).executeString();
			if(StringUtil.isNotEmpty(specialEffDateProductIds)){
				specialEffDateFlag ="Y";
			}else{
				specialEffDateFlag ="N";
			}

			complicatedFlag = new QueryBuilder("select complicatedFlag from sdproduct where productId=?", productId).executeString();
			dutyTempSerials = new QueryBuilder("select DutySerials from sdinformationdutytemp where ordersn=?", orderSn).executeString();
			dealInsuredDate(productId);
			// 被保人
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Map<String, String> keyMap = new HashMap<String, String>();
			Map<String, String> premMap = new HashMap<String, String>();
			Member memberLogin = getLoginMember();
			String memberId="";
			if (memberLogin != null) {
				memberId = memberLogin.getId();
			}
			if (StringUtil.isEmpty(memberId) && StringUtil.isNotEmpty(sdorder.getMemberId())) {
				memberId = sdorder.getMemberId();
			}
			for (SDInformationInsured sdinf : sdinformation.getSdinformationinsuredSet()) {
				//TODO 放回修改保存受益人信息 req20150323004
				Set<SDInformationBnf> sdinformationbnfSet = sdinf.getSdinformationbnfSet();
				beneficiaryCount = sdinformationbnfSet.size();
				for (SDInformationBnf sdInformationBnf : sdinformationbnfSet) {
					sdInformationBnf.setBnfSexName(this.dictionaryService.getNameByCodeTypePro(sdinformation.getProductId(), sdinformation.getInsuranceCompany(), "Sex", sdInformationBnf.getBnfSex()));
					sdInformationBnf.setRelationToInsuredName(this.dictionaryService.getNameByCodeTypePro(sdinformation.getProductId(), sdinformation.getInsuranceCompany(), "BnfRelationship", sdInformationBnf.getRelationToInsured()));
					sdInformationBnf.setBnfIDTypeName(this.dictionaryService.getNameByCodeTypePro(sdinformation.getProductId(), sdinformation.getInsuranceCompany(), "certificate", sdInformationBnf.getBnfIDType()));
					sdinformationbnfList.add(sdInformationBnf);
				}
				if (("1".equals(sdinf.getRecognizeeOperate()) && "rid_me".equals(sdinf.getMulInsuredFlag())) || "4".equals(sdinf.getRecognizeeOperate())) {
					if (sdinf.getFlightTime() != null && !"".equals(sdinf.getFlightTime())) {
						sdinf.setFlightTime(sdf.format(sdf.parse(sdinf.getFlightTime())));
					}
					this.insuredMulCount = sdinformation.getSdinformationinsuredSet().size();
					this.sdinformationinsured = sdinf;
					this.recognizeeOperate = sdinformationinsured.getRecognizeeOperate();
					this.mulInsuredFlag = sdinformationinsured.getMulInsuredFlag();
					if (sdinformationinsured != null) {
						break;
					}
				} else if ("3".equals(sdinf.getRecognizeeOperate()) && "rid_td".equals(sdinf.getMulInsuredFlag())) {
					if (premMap.containsKey(sdinf.getRecognizeePrem())) {
						sdinf.setDiscountPrice(premMap.get(sdinf.getRecognizeePrem()));
					} else {
						sdinf.setDiscountPrice(ActivityCalculate.ProductCalculate(productId,sdorder.getOrderSn(),sdinf.getRecognizeePrem(), sdorder.getChannelsn(), memberId));
						premMap.put(sdinf.getRecognizeePrem(), sdinf.getDiscountPrice());
					}
					this.sdinsuredList.add(sdinf);
					if (sdinf.getFlightTime() != null && !"".equals(sdinf.getFlightTime())) {
						sdinf.setFlightTime(sdf.format(sdf.parse(sdinf.getFlightTime())));
					}
				} else {
					if (sdinf.getFlightTime() != null && !"".equals(sdinf.getFlightTime())) {
						sdinf.setFlightTime(sdf.format(sdf.parse(sdinf.getFlightTime())));
					}
					if (!"Y".equals(keyMap.get(sdinf.getRecognizeeKey()))) {
						this.sdinformationinsuredList.add(sdinf);
					}
					keyMap.put(sdinf.getRecognizeeKey(), "Y");
					SDInformationProperty sdp = sdinf.getSdinformationproperty();
					if (sdp != null) {
						this.sdinformationpropertyList.add(sdp);
					}
				}

			}

			if (sdinformationinsuredList != null && sdinformationinsuredList.size() >= 1) {
				this.insuredActCount = sdinformation.getSdinformationinsuredSet().size();// 采用未经处理的被保人信息获取“份数”
				insuredCount = sdinformationinsuredList.size();// 人数
				recognizeeOperate = sdinformationinsuredList.get(0).getRecognizeeOperate();
				this.mulInsuredFlag = sdinformationinsuredList.get(0).getMulInsuredFlag();
			} else if (this.sdinsuredList != null && this.sdinsuredList.size() >= 1) {
				this.insuredImpCount = sdinsuredList.size();
				recognizeeOperate = sdinsuredList.get(0).getRecognizeeOperate();
				this.mulInsuredFlag = sdinsuredList.get(0).getMulInsuredFlag();
			}
			if (insuredMulCount <= 0) {
				insuredMulCount = 1;
			}
			if (insuredCount <= 0) {
				insuredCount = 1;
			}
			if (insuredImpCount <= 0) {
				insuredImpCount = 0;
			}
			// 投保人
			List<SDInformationAppnt> sdapp = new ArrayList<SDInformationAppnt>(sdinformation.getSdinformationappntSet());
			if (sdapp != null && sdapp.size() > 0) {
				this.sdinformationAppnt = sdapp.get(0);
			}
			
			//add by wangej 20160202 长险重新计算投保人年龄 begin
			if (sdinformation.getTextAge() != null && !"".equals(sdinformation.getTextAge()) ) {
				if(sdinformation.getTextAge().indexOf("天") != -1 || sdinformation.getTextAge().indexOf("岁") != -1 ){
					if (StringUtil.isNotEmpty(this.sdinformationAppnt.getApplicantBirthday())) {
						String appAge = "";
						appAge = com.sinosoft.sms.messageinterface.pubfun.PubFun.getAgeNum(this.sdinformationAppnt.getApplicantBirthday());
						if (appAge.endsWith("D")) {
							sdinformation.setTextAge(appAge.substring(0,appAge.indexOf("D")) + "天");
						} else {
							sdinformation.setTextAge(appAge.substring(0,appAge.indexOf("Y"))+"岁");
						}
					}
				}
			}
			textAge = sdinformation.getTextAge();
			//add by wangej 20160202 长险重新计算投保人年龄 end
			
			JdbcTemplateData jtd = new JdbcTemplateData();
			SDInformationInsured t = new SDInformationInsured();
			if (sdinformationinsured != null && !"".equals(sdinformationinsured.getId()) && sdinformationinsured.getId() != null) {
				t = sdinformationinsured;
			} else {
				for (SDInformationInsured s : sdinformationinsuredList) {
					if (s != null && !"".equals(s.getId()) && s.getId() != null) {
						t = s;
					}
				}
				for (SDInformationInsured s : this.sdinsuredList) {
					if (s != null && !"".equals(s.getId()) && s.getId() != null) {
						t = s;
					}
				}

			}
			if (t.getDestinationCountry() != null && !"".equals(t.getDestinationCountry())) {
				String[] destinate = t.getDestinationCountry().split(",");
				String destinationCountryText = "";
				if (destinate != null && destinate.length > 0) {
					int kk = 0;
					for (kk = 0; kk < destinate.length; kk++) {
						int ttt = new QueryBuilder(" SELECT COUNT(1) FROM dictionary WHERE codetype='CountryCode' AND productId = ? ",sdinformation.getProductId()).executeInt();
						String wherepart = " and insuranceCode = '"+sdinformation.getInsuranceCompany()+"'";
						if(ttt>=1){
							wherepart = " and productId = '"+sdinformation.getProductId()+"'";
						}else{
							wherepart = " and (productid IS NULL OR productid ='') ";
						}
						String sqlCountryCodess = "select codename from dictionary where codetype=? and codevalue = ? "+wherepart;
						String[] sqlCountryCodesstemp = { "CountryCode", destinate[kk].trim() };
						List<Map> dlist = new ArrayList<Map>();
						dlist = jtd.obtainData(sqlCountryCodess, sqlCountryCodesstemp);
						Iterator<Map> it = dlist.iterator();
						while (it.hasNext()) {
							Map map = it.next();
							detisnateList.add((String) map.get("codeName"));
							destinationCountryText += map.get("codeName")+",";
						}
					}
				}
				sdinformationinsured.setDestinationCountryText(destinationCountryText.substring(0, destinationCountryText.length()-1));
				logger.info("选择大小：{}", detisnateList.size());
			}
			insuredbirthday = t.getRecognizeeBirthday();
			// productId = order.getProductId();
			Map<String, Object> paramter = new HashMap<String, Object>();
			paramter = sdorderService.getProductInformation(productId, "N", channelCode);// 产品ID
			String[] BaseInformation = new String[14];
			BaseInformation = (String[]) paramter.get("baseInformation");// 产品基础数据
			riskType = BaseInformation[7].toString();// 产品小类
			setSession("baseInformation", BaseInformation);
			// 产品投保要素
			riskAppFactior = (List<OrderRiskAppFactor>) paramter.get("riskAppFactor");
			if (riskAppFactior.size() > 0) {
				QueryBuilder qb = new QueryBuilder("SELECT ensure FROM sdinformation WHERE ordersn=?");
				qb.add(orderSn);
				String ensure = qb.executeString();
				protectionPeriodTy = ensure.trim().substring(ensure.trim().length() - 1, ensure.trim().length());
				protectionPeriodLast = ensure.trim().substring(ensure.trim().indexOf("-") + 1, ensure.trim().length() - 1);
				protectionPeriodFlag = "true";
			}

			// 产品责任要素
			dutyFactor = (List<OrderDutyFactor>) paramter.get("dutyFactor");
			Map<String, Object> tData = new HashMap<String, Object>();
			if (BaseInformation == null || BaseInformation.length <= 0 || riskAppFactior == null || riskAppFactior.size() <= 0 || dutyFactor == null || dutyFactor.size() <= 0) {
				try {
					paramter = sdorderService.getProductInformation(productId, "N", channelCode);// 产品ID
				} catch (Exception e) {
					tData.put("Flag", "Err");// 总保费
					tData.put("Msg", "与产品中心交互失败①！");// 错误信息
					JSONObject jsonObject = JSONObject.fromObject(tData);
					logger.error("与产品中心交互失败①,产品编码" + productId + e.getMessage(), e);
					return ajax(jsonObject.toString(), "text/html");
				}
				BaseInformation = (String[]) paramter.get("baseInformation");// 产品基础数据
				riskAppFactior = (List<OrderRiskAppFactor>) paramter.get("riskAppFactor");
				dutyFactor = (List<OrderDutyFactor>) paramter.get("dutyFactor");
			}
			// 保额
			plan = sdinformation.getPlanCode();
			this.getInsuredAmnt(dutyFactor, plan);
			if (dutyFactor.size() > 0) {
				setSession("dutyFactor", dutyFactor);
				setSession(productId + "dutyFactor", dutyFactor);
			}
			this.dataBuild();
			
			if (memberLogin != null) {
				sdorder.setMemberId(memberId);
				loginFlag = "true";
				// 根据是否登陆判断是否显示积分抵值信息
				map_pointinfo=new HashMap<String,String>();
				Map map=new HashMap();
				List list=new ArrayList();
				list.add(productId);
				map.put("ProductList",list);
				try {
					Map result=new PointsCalculate().pointsManage(IntegralConstant.POINT_PRODUCT, "", map);
					DataTable dt_result=(DataTable) result.get(IntegralConstant.DATA);
					if(dt_result.getRowCount()>0){
						String BuyPoints=dt_result.getString(0,"BuyPoints");
						//String GivePoints=dt_result.getString(0,"GivePoints");
						BigDecimal TotalAmount=new BigDecimal(sdorder.getPayPrice());
						String PointScalerUnit = Config.getConfigValue("PointScalerUnit");
						java.text.DecimalFormat myformat = new java.text.DecimalFormat("0.000");
						String str = myformat.format(new BigDecimal(Double
								.parseDouble(String.valueOf(TotalAmount))
								* Double.parseDouble(BuyPoints)
								* Double.parseDouble(PointScalerUnit)));
						BigDecimal points=new BigDecimal(str).setScale(0, BigDecimal.ROUND_UP);
						BigDecimal CurrentValidatePoint=new BigDecimal(memberLogin.getCurrentValidatePoint());
						if(points.compareTo(CurrentValidatePoint)>0){
							map_pointinfo.put("points",String.valueOf(CurrentValidatePoint));
							BigDecimal pointValue = CurrentValidatePoint.divide(new BigDecimal(PointScalerUnit), 1,BigDecimal.ROUND_UP);
							map_pointinfo.put("pointsprice",String.valueOf(pointValue));
						}else{
							map_pointinfo.put("points",String.valueOf(points));
							BigDecimal pointValue = points.divide(new BigDecimal(PointScalerUnit), 1,BigDecimal.ROUND_UP);
							map_pointinfo.put("pointsprice", String.valueOf(pointValue));
						}
					}else{
						map_pointinfo.put("points", "");
						map_pointinfo.put("pointsprice", "");
					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			} else {
				loginFlag = "false";
			}
			this.directPayBankInfo = directPayBankInfoService.getByOrderSn(orderSn);
			if  (directPayBankInfo == null) {
				directPayBankInfo = new DirectPayBankInfo();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionError("矮油，出了点小状况②。再试一次吧！");
			return ERROR;
		}

		String productID = productId;// A01 产品
		// 模块路径
		PageModule pm = new PageModule();
		pageModuleList = pm.getPageModuleURL(productID);
		dealExcelModule(productID);
		if (StringUtil.isNotEmpty(productExcelTemp)) {
			productExcelFlag = "Y";
		}
		impValadate = "Y";
		// add by cuishigang 投保人信息快速录入 start
		quickQueryAppnt();
		// add by cuishigang 投保人信息快速录入 end
		// 得到产品限购份数
		dealLimitCount(sdinformation);
		// 默认产品无次日生效，查询zdcode表中被要求次日生效的产品记录
		effectiveNextDayFlag = "false";
		QueryBuilder qb = new QueryBuilder("SELECT memo FROM zdcode WHERE codetype='ProductPeriod' AND codevalue=?");
		qb.add(productId);
		DataTable dt = qb.executeDataTable();
	
		if (dt.getRowCount() > 0) {
			effectiveNextDayFlag = "true";
			int num = Integer.parseInt(dt.get(0, 0).toString());
			startPeriod = num;
			Date date = DateUtil.addDay(new Date(), num);
			effectiveDayValue = DateUtil.toString(date);
			if("A".equals(sdinformation.getEnsureLimitType())){
				String agelmit = sdinformation.getEnsureLimit();
				String limitType = sdinformation.getEnsureLimitType();
				Timestamp endDayValue=checkYeMoDa_endAge( effectiveDayValue, agelmit, limitType, insuredbirthday);
				sdinformation.setEndDate(endDayValue);
			}		
		}
		// 取得活动信息
		getActivityInfo(productId, sdorder.getChannelsn());
		// 获得购买送积分信息
		getSendPoint(orderSn, productId, sdorder.getMemberId());
		
		String healthyPage = healthyInput(sdorder.getOrderSn(), status);
		if (StringUtil.isNotEmpty(healthyPage)) {
			return healthyPage;
		}
		status = "update";
		// 判断是否为购物车，即显示“保存”或者“下一步”
		if ("ShopCart".equals(orderFlag)) {
			shopcarflag = "true";
		}
		
		//TODO 返回修改获取最大受益人个数 req20150323004
		qb = new QueryBuilder("SELECT memo FROM zdcode WHERE codetype='beneficiaryNum' AND codevalue=?");
		qb.add(productId.substring(0, 4));
		if(StringUtil.isNotEmpty(qb.executeString())){
			beneficiaryNum = qb.executeString();
		}
		
		//TODO 返回修改初始化受益人操作标示 req20150323004
		if(beneficiaryCount > 0){
			benefitOperate = "1";
		}else{
			benefitOperate = "0";
			beneficiaryCount = 1;
		}
		qb = new QueryBuilder("SELECT Occup FROM sdproduct WHERE productid='"+productID+"'");
		OccupLevel = qb.executeString();//职业类别显示
		//判断产品公司编码是否属于美亚，安联，史带，人保四种产品，如果属于则界面展示"确认被保人属于可投保职业"选择
		Mapx<String, String> codes =  CacheManager.getMapx("Code", "NewServiceComcode");
		if(codes != null && codes.containsKey(supplierCode2)){
			needShowOccupLevel = "1";
		}
		//判断产品是否为根据职业配置的批量上传产品,判断"确认被保人属于可投保职业"显隐
		Mapx<String, String> productcodes =  CacheManager.getMapx("Code", "NewServiceProductID");
		if (productcodes != null && productcodes.containsKey(productID)) {
			needShowOccupLevel = "1";
		}
		
		String templateURL = "inspageone";
		return templateURL;
	}
	/**
	 * 订单修改--保存
	 * 
	 * @return
	 */
	public String orderUpdate() { 
		Map<String, Object> tData = new HashMap<String, Object>();
		IDEntityTypeAndBirthdayRelationship();
		try {
			
			// 保障期限为终身时的特殊处理
			if (!"L".equals(this.protectionPeriodTy.toUpperCase())) {
				sdinformation.setStartDate(sdf_1.parse(m_startDate));
				sdinformation.setEndDate(sdf_1.parse(m_endDate));

			} else {
				sdinformation.setStartDate(sdf_1.parse(m_startDate));
				sdinformation.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse("9999-12-31"));
			}
			//判断起保日期是否在有效选择期内,如果不在选择有效期内则设置为空让用户选择
			if(!verifyInsurStartDate(supplierCode2,productId,
					sdinformation.getStartDate())){
				logger.error("保单起保日期不在可选择日期范围内，请重新选择！");
				tData.put("Flag", "Err");//
				tData.put("Msg", "保单起保日期不在可选择日期范围内，请重新选择！");
				JSONObject jsonObject = JSONObject.fromObject(tData);
				return ajax(jsonObject.toString(), "text/html");
			}
		} catch (ParseException e1) {
			logger.error("保单起保日期或保单止保日期格式错误，请重新输入！" + e1.getMessage(), e1);
			tData.put("Flag", "Err");//
			tData.put("Msg", "保单起保日期或保单止保日期格式错误，请重新输入！");// 错误信息
			JSONObject jsonObject = JSONObject.fromObject(tData);
			return ajax(jsonObject.toString(), "text/html");
		}
		try {
			// 这对返回修改订单号保存错误的判断
			if (StringUtil.isEmpty(this.orderId) && StringUtil.isEmpty(request.getParameter("orderId"))) {
				tData.put("Flag", "Err");
				tData.put("Msg", "修改保存订单有异常①，请联系客服人员！");// 错误信息
				JSONObject jsonObject = JSONObject.fromObject(tData);
				logger.error("返回修改后点击下一步，订单号为空");
				return ajax(jsonObject.toString(), "text/html");
			} else if (StringUtil.isNotEmpty(this.orderId) && StringUtil.isNotEmpty(request.getParameter("orderId")) && (!(this.orderId.equals(request.getParameter("orderId"))))) {
				tData.put("Flag", "Err");
				tData.put("Msg", "修改保存订单有异常②，请联系客服人员！");// 错误信息
				JSONObject jsonObject = JSONObject.fromObject(tData);
				logger.error("返回修改后点击下一步，订单号不一致，分别为：{},{}",this.orderId, request.getParameter("orderId"));
				return ajax(jsonObject.toString(), "text/html");
			} else if (StringUtil.isNotEmpty(request.getParameter("orderId"))) {
				orderId = request.getParameter("orderId");
			}

			insTotalAmnt = new BigDecimal("0.00");
			totalAmnt = new BigDecimal("0.00");
			SDOrder oldSDOrder = sdorderService.get(this.orderId);

			Map<String, Object> paramter = new HashMap<String, Object>();
			if (StringUtil.isEmpty(productId)) {
				productId = (String) getSession("productId");
			}
			/*
			 * try{ paramter = sdorderService.getProductInformation(productId,
			 * "N");// 产品ID }catch(Exception e){ tData.put("Flag", "Err");// 总保费
			 * tData.put("Msg", "与产品中心交互失败！");// 错误信息 JSONObject jsonObject =
			 * JSONObject.fromObject(tData); log.info("==========" +
			 * jsonObject.toString()); return ajax(jsonObject.toString(),
			 * "text/html"); }
			 */
			quesFlag = questionPaper(productId);
			String[] BaseInformation = new String[17];
			BaseInformation = (String[]) getSession(productId + "baseInformation");
			riskAppFactior = (List<OrderRiskAppFactor>) getSession(productId + "riskAppFactior");
			dutyFactor = (List<OrderDutyFactor>) getSession(productId + "dutyFactor");
			if (BaseInformation == null || BaseInformation.length <= 0 || riskAppFactior == null || riskAppFactior.size() <= 0 || dutyFactor == null || dutyFactor.size() <= 0) {
				try {
					paramter = sdorderService.getProductInformation(productId, "N", channelCode);// 产品ID
				} catch (Exception e) {
					tData.put("Flag", "Err");// 总保费
					tData.put("Msg", "与产品中心交互失败②！");// 错误信息
					JSONObject jsonObject = JSONObject.fromObject(tData);
					logger.error("与产品中心交互失败②,产品编码，" + productId + e.getMessage(), e);
					return ajax(jsonObject.toString(), "text/html");
				}
				BaseInformation = (String[]) paramter.get("baseInformation");// 产品基础数据
				// riskType = BaseInformation[7].toString();// 产品小类
				riskAppFactior = (List<OrderRiskAppFactor>) paramter.get("riskAppFactor");
				dutyFactor = (List<OrderDutyFactor>) paramter.get("dutyFactor");
			}
			riskType = BaseInformation[7].toString();// 产品小类
			if (StringUtil.isNotEmpty(BaseInformation[10].toString())) {
				activityFlag = "1";
			}
			
			SDOrder nOrder = sdorder;
			// this.sdorder = oldSDOrder;

			if (!this.checkPayStatus(sdorder.getId())) {
				addActionError("这个订单已经支付过了，不要重复支付哟！！");
				logger.error("orderUpdate-订单已经支付过了，不要重复支付哟！！id:{} ordersn:{}", sdorder.getId(), sdorder.getOrderSn());
				return ERROR;
			}

			this.mLMap = new LinkedHashMap<Object, String>();
			oldSDOrder = orderConfigNewService.updateOrder(oldSDOrder, nOrder);
			KID = StringUtil.md5Hex(PubFun.getKeyValue() + oldSDOrder.getOrderSn());
			tData.put("kid", KID);
			tData.put("orderFlag", orderFlag);
			if ("ShopCart".equals(orderFlag)) {
				// 购物车 待支付状态
				oldSDOrder.setOrderStatus(SDOrderStatus.prepay);

			} else {
				// 暂存状态
				oldSDOrder.setOrderStatus(SDOrderStatus.temptorysave);
			}

			// 设置会员登录
			Member memberLogin = getLoginMember();
			if (memberLogin != null) {
				if (oldSDOrder.getMemberId() == null || "".equals(oldSDOrder.getMemberId())) {
					if (memberLogin != null) {
						oldSDOrder.setMemberId(memberLogin.getId());
					}
				}
			}
			sdorder.setMemberId(oldSDOrder.getMemberId());
			if ("1".equals(pointExchangeFlag) && memberLogin == null) {
				addActionError("请登录后再兑换！");
				logger.error("请登录后再兑换！产品编码：{}", productId);
				return ERROR;
			}
			if ("1".equals(pointExchangeFlag)) {
				PointScalerUnit = Config.getConfigValue("PointScalerUnit");
				oldSDOrder.setOrderIntegral(new BigDecimal(oldSDOrder
						.getOffsetPoint()).divide(
						new BigDecimal(PointScalerUnit), 2,
						BigDecimal.ROUND_HALF_UP).toString());
				oldSDOrder.setSumIntegral(oldSDOrder.getOrderIntegral());
			}
			// 取得活动信息
			getActivityInfo(productId, oldSDOrder.getChannelsn());
			// 获得购买送积分信息
			getSendPoint(oldSDOrder.getOrderSn(), productId, oldSDOrder.getMemberId());
			
			mLMap.put(oldSDOrder, "update");
			mLMap.put(oldSDOrder.getSdorderitem(), "update");
			Set<SDInformation> sdinformationSet = oldSDOrder.getSdinformationSet();
			SDInformation oldSDInf = null;
			for (SDInformation infmn : sdinformationSet) {
				oldSDInf = infmn;
			}
			oldSDInf = orderConfigNewService.updateInformation(oldSDInf, sdinformation);
			mLMap.put(oldSDInf, "update");
			Set<SDInformationAppnt> sdinformationAppntSet = oldSDInf.getSdinformationappntSet();
			SDInformationAppnt oldInfApp = null;
			for (SDInformationAppnt infmnapp : sdinformationAppntSet) {
				oldInfApp = infmnapp;
			}
			
			//TODO 针对新华投保人性别异常特殊处理 req20150323004
			String applicantSex = StringUtil.noNull(sdinformationAppnt.getApplicantSex());
			if(applicantSex.indexOf("M") > -1){
				sdinformationAppnt.setApplicantSex("M");
			}else if(applicantSex.indexOf("F") > -1){
				sdinformationAppnt.setApplicantSex("F");
			}
			
			oldInfApp = orderConfigNewService.updateInformationAppnt(oldSDInf, oldInfApp, this.sdinformationAppnt);
			mLMap.put(oldInfApp, "update");
			// 快速录入投保人信息
			List<SDRelationAppnt> sdrelationappntList = sdrelationappntSave(oldInfApp);
			if (sdrelationappntList != null && sdrelationappntList.size() >= 1) {
				mLMap.put(sdrelationappntList, "insert&update");
			}
			
			// 处理责任信息
			//List<SDInformationDuty> tSDInformationDutyList = null;
			//返回修改点击下一步：复杂险须更新责任信息表、瑞泰校验用
//			CpsAgainBuyOrderInfoDeal infoDeal = new CpsAgainBuyOrderInfoDeal();
//			List<SDInformationDuty> tSDInformationDutyList = infoDeal.selectSDInformationDutyList(oldSDOrder.getOrderSn());
			List<SDInformationDuty> tSDInformationDutyList =sdInformationDutyService.getByOrderSn(oldSDOrder.getOrderSn());
			if ("Y".equals(complicatedFlag) && StringUtil.isNotEmpty(dutyTempSerials)) {
				List<SDInformationDutyTemp> dutyTempList = sdInformationDutyTempService.getDutyTemp(dutyTempSerials);
				tSDInformationDutyList = SDInformationDutyListUpdate(tSDInformationDutyList, dutyTempList );
				mLMap.put(tSDInformationDutyList, "update");// 责任信息存储
			} 
			
			// 保存银行卡信息
			DirectPayBankInfo oldBankInfo = directPayBankInfoService.getByOrderSn(oldSDOrder.getOrderSn());
			if (oldBankInfo != null && directPayBankInfo != null) {
				if(!"N".equals(directPayBankInfo.getProp5())){
					oldBankInfo = directPayBankInfoUpdate(oldBankInfo, oldInfApp, oldSDInf);
					mLMap.put(oldBankInfo, "update");
				}else{
					mLMap.put(oldBankInfo, "delete");
				}
			}else if(oldBankInfo == null && directPayBankInfo != null && "Y".equals(directPayBankInfo.getProp5())){
				orderSn  = oldSDOrder.getOrderSn();
				DirectPayBankInfo newBankInfo =directPayBankInfoSave(oldSDInf.getInsuranceCompany()).get(0);
				mLMap.put(newBankInfo, "insert");
			}
			
			Set<SDInformationInsured> oldSDinS = oldSDInf.getSdinformationinsuredSet();
			SDInformationInsured oldSDInsured = new SDInformationInsured();

			String tMulInsuredFlag = oldSDinS.iterator().next().getMulInsuredFlag();
			// 判断修改时选择哪种操作方式
			// 操作方式相同
			List<SDInformationInsured> tSDInformationInsuredList = new ArrayList<SDInformationInsured>();
			if ((tMulInsuredFlag == null && mulInsuredFlag == null) || tMulInsuredFlag.equals(this.mulInsuredFlag)) {

				if (("rid_td".equals(this.mulInsuredFlag) || "rid_orther".equals(this.mulInsuredFlag) || "".equals(mulInsuredFlag) || mulInsuredFlag == null) && !"Y".equals(this.relationIsSelf)) {

					// 处理需要删除的被保人相关信息
					dealMulInsuredDelete(oldSDOrder, sdinformationSet.iterator().next());
					tSDInformationInsuredList = this.sdinformationMulInsuredSave(oldSDOrder, oldSDInf, oldInfApp, quesFlag, oldSDInf.getRiskType());
					if (tSDInformationInsuredList == null || tSDInformationInsuredList.size() <= 0) {
						tSDInformationInsuredList = this.excelInsuredSave(oldSDOrder, oldSDInf, oldInfApp, quesFlag, oldSDInf.getRiskType());
					}
					// 保存被保人信息
					mLMap.put(tSDInformationInsuredList, "insert");
					if (this.sdInsuredOtherList.size() >= 1) {
						for (SDInformationInsured insured : sdInsuredOtherList) {
							tSDInformationInsuredList.add(insured);
						}
					}
					if ("rid_orther".equals(this.mulInsuredFlag) || (("".equals(this.mulInsuredFlag) || mulInsuredFlag == null) && "1".equals(this.recognizeeOperate))) {
						List<SDRelationRecognizee> recognizeeList = this.sdrelationrecognizeeSave(tSDInformationInsuredList);
						mLMap.put(recognizeeList, "insert&update");
					}
					// 保存sdorderitemoth
					mLMap.put(this.sdorderitemothList, "insert");
					// 保存产品投保要素信息
					// 现产品投保要素与被保人关联
					mLMap.put(informationInsuredElementsList, "insert");
					// 高额
					mLMap.put(this.questionList, "insert");
					// 保存保单信息
					List<SDInformationRiskType> tSDInformationRiskTypeList = new ArrayList<SDInformationRiskType>();
					for (SDInformationRiskType tSDInformationRiskType : mSDInformationRiskTypeSet) {
						tSDInformationRiskType.setApplyPolicyNo("");
						tSDInformationRiskTypeList.add(tSDInformationRiskType);
					}
					if (this.sdRiskTypeOtherList.size() >= 1) {
						for (SDInformationRiskType risktype : sdRiskTypeOtherList) {
							risktype.setApplyPolicyNo("");
							tSDInformationRiskTypeList.add(risktype);
						}
					}
					if ("1".equals(pointExchangeFlag)) {
						int lastNum = giftClassifyService.getLastNum(productId);
						if (lastNum == 0) {
							tData.put("Flag", "Err");//
							tData.put("Msg", "矮油，您兑换的商品已全部兑换完了！请兑换其他商品！");// 错误信息
							JSONObject jsonObject = JSONObject.fromObject(tData);
							logger.info("矮油，您兑换的商品已全部兑换完了！请兑换其他商品！查询库存量为0，产品编码：{}", productId);
							return ajax(jsonObject.toString(), "text/html");
						}
						if (lastNum < tSDInformationRiskTypeList.size()) {
							tData.put("Flag", "Err");//
							tData.put("Msg", "矮油，您兑换的商品库存量只有"+ lastNum +"个了！请减少份数！");// 错误信息
							logger.info("矮油，您兑换的商品库存量只有{}个了！请减少份数！产品编码：{}", lastNum, productId);
							JSONObject jsonObject = JSONObject.fromObject(tData);
							return ajax(jsonObject.toString(), "text/html");
						}
					}
					mLMap.put(tSDInformationRiskTypeList, "insert");
					// 保存财产信息
					List<SDInformationProperty> tSDInformationPropertyList = new ArrayList<SDInformationProperty>();
					for (SDInformationProperty sdp : mSDInformationPropertySet) {
						tSDInformationPropertyList.add(sdp);
					}
					mLMap.put(tSDInformationPropertyList, "insert");
					List<SDInsuredHealth> t = sdinsuredHealtySave(tSDInformationInsuredList, null, oldSDInf, order_healthySn);
					if (sdinsuredHealthList != null && sdinsuredHealthList.size() >= 1) {
						mLMap.put(sdinsuredHealthList, "delete");
						mLMap.put(t, "insert");
					}
				} else if ("rid_me".equals(this.mulInsuredFlag) || "Y".equals(this.relationIsSelf)) {
					dealMulInsuredDelete(oldSDOrder, sdinformationSet.iterator().next());

					// 处理被保人信息---多被保人-本人
					this.sdinformationOneInsuredSave(oldSDInf, oldSDOrder, oldInfApp, quesFlag);
					List<SDInsuredHealth> t = sdinsuredHealtySave(null, this.sdinformationinsured, oldSDInf, order_healthySn);
					if (sdinsuredHealthList != null && sdinsuredHealthList.size() >= 1) {
						mLMap.put(sdinsuredHealthList, "delete");
						mLMap.put(t, "insert");
					}
					// 保存sdorderitemoth
					mLMap.put(this.sdorderitemothList, "insert");
					// 保存产品投保要素信息
					// 现产品投保要素与被保人关联
					mLMap.put(informationInsuredElementsList, "insert");
					// 高额
					mLMap.put(this.questionList, "insert");
					tSDInformationInsuredList = new ArrayList<SDInformationInsured>();
					tSDInformationInsuredList.add(this.sdinformationinsured);
					if (this.sdInsuredOtherList.size() >= 1) {
						for (SDInformationInsured insured : sdInsuredOtherList) {
							tSDInformationInsuredList.add(insured);
						}
					}
					mLMap.put(tSDInformationInsuredList, "insert");
					List<SDInformationRiskType> tSDInformationRiskTypeList = new ArrayList<SDInformationRiskType>();
					for (SDInformationRiskType tSDInformationRiskType : mSDInformationRiskTypeSet) {
						tSDInformationRiskType.setApplyPolicyNo("");
						tSDInformationRiskTypeList.add(tSDInformationRiskType);
					}
					if (this.sdRiskTypeOtherList.size() >= 1) {
						for (SDInformationRiskType risktype : sdRiskTypeOtherList) {
							risktype.setApplyPolicyNo("");
							tSDInformationRiskTypeList.add(risktype);
						}
					}
					if ("1".equals(pointExchangeFlag)) {
						int lastNum = giftClassifyService.getLastNum(productId);
						if (lastNum == 0) {
							tData.put("Flag", "Err");//
							tData.put("Msg", "矮油，您兑换的商品已全部兑换完了！请兑换其他商品！");// 错误信息
							JSONObject jsonObject = JSONObject.fromObject(tData);
							logger.info("矮油，您兑换的商品已全部兑换完了！请兑换其他商品！查询库存量为0，产品编码：{}", productId);
							return ajax(jsonObject.toString(), "text/html");
						}
						if (lastNum < tSDInformationRiskTypeList.size()) {
							tData.put("Flag", "Err");//
							tData.put("Msg", "矮油，您兑换的商品库存量只有"+ lastNum +"个了！请减少份数！");// 错误信息
							logger.info("矮油，您兑换的商品库存量只有{}个了！请减少份数！产品编码：{}",lastNum, productId);
							JSONObject jsonObject = JSONObject.fromObject(tData);
							return ajax(jsonObject.toString(), "text/html");
						}
					}
					mLMap.put(tSDInformationRiskTypeList, "insert");
				}
			} else {
				// 操作方式不同(多被保人模式下，由多被保人页签修改为本人页签)

				// 处理需要删除的被保人相关信息
				dealMulInsuredDelete(oldSDOrder, sdinformationSet.iterator().next());
				// 处理被保人信息
				// 处理被保人信息---多被保人-本人
				this.sdinformationOneInsuredSave(oldSDInf, oldSDOrder, oldInfApp, quesFlag);
				// 处理被保人信息---多被保人-多人
				tSDInformationInsuredList = this.sdinformationMulInsuredSave(oldSDOrder, oldSDInf, oldInfApp, quesFlag, oldSDInf.getRiskType());
				if (tSDInformationInsuredList == null || tSDInformationInsuredList.size() <= 0) {
					tSDInformationInsuredList = this.excelInsuredSave(oldSDOrder, oldSDInf, oldInfApp, quesFlag, oldSDInf.getRiskType());
				}
				/*
				 * if ("rid_me".equals(this.mulInsuredFlag)) {
				 * mLMap.put(sdinformationinsured, "insert"); }
				 */
				tSDInformationInsuredList.add(sdinformationinsured);
				if (this.sdInsuredOtherList.size() >= 1) {
					for (SDInformationInsured insured : sdInsuredOtherList) {
						tSDInformationInsuredList.add(insured);
					}
				}
				// 保存被保人信息
				mLMap.put(tSDInformationInsuredList, "insert");
				if ("rid_orther".equals(this.mulInsuredFlag) || (("".equals(this.mulInsuredFlag) || mulInsuredFlag == null) && "1".equals(this.recognizeeOperate))) {
					List<SDRelationRecognizee> recognizeeList = this.sdrelationrecognizeeSave(tSDInformationInsuredList);
					mLMap.put(recognizeeList, "insert&update");
				}
				// 保存sdorderitemoth
				mLMap.put(this.sdorderitemothList, "insert");
				// 保存产品投保要素信息
				// 现产品投保要素与被保人关联
				mLMap.put(informationInsuredElementsList, "insert");

				// 保存保单信息
				List<SDInformationRiskType> tSDInformationRiskTypeList = new ArrayList<SDInformationRiskType>();
				for (SDInformationRiskType tSDInformationRiskType : mSDInformationRiskTypeSet) {
					tSDInformationRiskType.setApplyPolicyNo("");
					tSDInformationRiskTypeList.add(tSDInformationRiskType);
				}
				if (this.sdRiskTypeOtherList.size() >= 1) {
					for (SDInformationRiskType risktype : sdRiskTypeOtherList) {
						risktype.setApplyPolicyNo("");
						tSDInformationRiskTypeList.add(risktype);
					}
				}
				if ("1".equals(pointExchangeFlag)) {
					int lastNum = giftClassifyService.getLastNum(productId);
					if (lastNum == 0) {
						tData.put("Flag", "Err");//
						tData.put("Msg", "矮油，您兑换的商品已全部兑换完了！请兑换其他商品！");// 错误信息
						JSONObject jsonObject = JSONObject.fromObject(tData);
						logger.info("矮油，您兑换的商品已全部兑换完了！请兑换其他商品！查询库存量为0，产品编码：{}", productId);
						return ajax(jsonObject.toString(), "text/html");
					}
					if (lastNum < tSDInformationRiskTypeList.size()) {
						tData.put("Flag", "Err");//
						tData.put("Msg", "矮油，您兑换的商品库存量只有"+ lastNum +"个了！请减少份数！");// 错误信息
						logger.info("矮油，您兑换的商品库存量只有{}个了！请减少份数！产品编码：{}", lastNum, productId);
						JSONObject jsonObject = JSONObject.fromObject(tData);
						return ajax(jsonObject.toString(), "text/html");
					}
					// 更新积分兑换表状态
					Transaction trans = new Transaction();
					QueryBuilder qb = new QueryBuilder("update PointExchangeInfo set status = ?, points = ? where orderSn = ?");
					qb.add(String.valueOf(oldSDOrder.getOrderStatus().ordinal()));
					qb.add(oldSDOrder.getOffsetPoint());
					qb.add(oldSDOrder.getOrderSn());
					trans.add(qb);
					if (!trans.commit()) {
						logger.error("积分兑换保险产品，积分兑换表更新订单状态失败（OrderConfigNewAction-orderUpdate），orderSn:{}", orderSn);
					}
					
				}
				mLMap.put(tSDInformationRiskTypeList, "insert");
				// 保存财产信息
				List<SDInformationProperty> tSDInformationPropertyList = new ArrayList<SDInformationProperty>();
				for (SDInformationProperty sdp : mSDInformationPropertySet) {
					tSDInformationPropertyList.add(sdp);
				}
				mLMap.put(tSDInformationPropertyList, "insert");

			}
			
			//TODO 返回修改受益人 req20150323004
			//判断如果受益人有数据，则默认取第一个被保人的信息，用于解决单被保人sdinformationinsured对象为空的问题
			//目前只支持单被保人下的指定受益人操作
			if(sdinformationbnfList.size()>0 && tSDInformationInsuredList.size()>0){
				sdinformationinsured = tSDInformationInsuredList.get(0);
			}
			
			List<SDInformationBnf> saveBnfList = new ArrayList<SDInformationBnf>();
			for (SDInformationBnf sdinformationbnf : sdinformationbnfList) {
				if(sdinformationbnf == null){
					continue;
				}
				sdinformationbnf.setId(null);
				sdinformationbnf.setOrderSn(oldSDInf.getOrderSn());
				sdinformationbnf.setInformationSn(oldSDInf.getInformationSn());
				sdinformationbnf.setInformationSn2(oldSDInf.getInformationSn());
				sdinformationbnf.setRecognizeeSn(sdinformationinsured.getRecognizeeSn());
				sdinformationbnf.setSdinformationInsured(sdinformationinsured);
				sdinformationbnf.setBnfSexName(this.dictionaryService.getNameByCodeTypePro(oldSDInf.getProductId(), oldSDInf.getInsuranceCompany(), "Sex", sdinformationbnf.getBnfSex()));
				sdinformationbnf.setRelationToInsuredName(this.dictionaryService.getNameByCodeTypePro(oldSDInf.getProductId(), oldSDInf.getInsuranceCompany(), "BnfRelationship", sdinformationbnf.getRelationToInsured()));
				sdinformationbnf.setBnfIDTypeName(this.dictionaryService.getNameByCodeTypePro(oldSDInf.getProductId(), oldSDInf.getInsuranceCompany(), "certificate", sdinformationbnf.getBnfIDType()));
				// 证件有效期勾选长期时
				if (StringUtil.isNotEmpty(sdinformationbnf.getBnfStartID()) && StringUtil.isEmpty(sdinformationbnf.getBnfEndID())) {
					sdinformationbnf.setBnfEndID("9999-12-31");
				}
				saveBnfList.add(sdinformationbnf);
				
			}
			
			if(!"0".equals(benefitOperate) && saveBnfList.size() > 0){
				mLMap.put(saveBnfList, "insert");
			}
			
			// 处理健康告知
			List<SDInformationInsured> inslist = new ArrayList<SDInformationInsured>();
			for (SDInformationInsured i : oldSDinS) {
				inslist.add(i);
			}
			
			String tMessage = shopValidate(oldSDOrder, oldSDInf, oldInfApp, this.sdinformationinsured, this.sdinformationinsuredList, this.sdinformationpropertyList, this.sdinsuredList, tSDInformationDutyList);
			if (tMessage != null && !"".equals(tMessage)) {
				tData.put("Flag", "Err");// 总保费
				tData.put("Msg", tMessage);// 错误信息
				JSONObject jsonObject = JSONObject.fromObject(tData);
				logger.error("订单错误信息，{}", tMessage);
				return ajax(jsonObject.toString(), "text/html");
			}
			String errMessage = "";
			if (!"rid_td".equals(this.mulInsuredFlag)) {
				sdinformation.setOrderSn(oldSDOrder.getOrderSn());
				errMessage = this.relCalPrem(BaseInformation, riskAppFactior, dutyFactor, this.sdinformation);
			} else {
				errMessage = this.impRelCalPrem();
			}
			if (StringUtil.isNotEmpty(errMessage)) {
				tData.put("Flag", "Err");//
				tData.put("Msg", errMessage);// 错误信息
				JSONObject jsonObject = JSONObject.fromObject(tData);
				logger.info("产品编码:{}", productId + errMessage);
				return ajax(jsonObject.toString(), "text/html");
			}
			if ("1".equals(pointExchangeFlag)) {
				
				BigDecimal nTotalAmnt = totalAmnt.multiply(new BigDecimal(PointScalerUnit)).setScale(0, BigDecimal.ROUND_UP);
				BigDecimal oTotalAmnt = new BigDecimal(sdorder.getOffsetPoint());
				BigDecimal oInsTotalAmnt = this.insTotalAmnt.multiply(new BigDecimal(PointScalerUnit)).setScale(0, BigDecimal.ROUND_UP);
				Object[] argArr = {productId, nTotalAmnt, oTotalAmnt};
				//后台增加保费==0 的校验
				if(nTotalAmnt.compareTo(new BigDecimal("0"))==0 || oTotalAmnt.compareTo(new BigDecimal("0"))==0){
					tData.put("Flag", "Err");//
					tData.put("Msg", "订单兑换积分数量为0,请重新提交试试！");// 错误信息
					JSONObject jsonObject = JSONObject.fromObject(tData);
					logger.info("订单兑换积分数量为0,产品编码:{},重新结算后积分数量:{},原积分数量:{}, 请重新提交试试！", argArr);
					return ajax(jsonObject.toString(), "text/html");
				}
				if (nTotalAmnt.compareTo(oTotalAmnt) != 0) {
					tData.put("Flag", "Err");//
					tData.put("Msg", "两次计算的保费不一样，请重新提交试试！");// 错误信息
					JSONObject jsonObject = JSONObject.fromObject(tData);
					logger.info("两次计算的保费不一样，产品编码:{},重新结算后保费:{},原保费:{}, 请重新提交试试", argArr);
					return ajax(jsonObject.toString(), "text/html");
				}
				if (nTotalAmnt.compareTo(oInsTotalAmnt) != 0) {
					tData.put("Flag", "Err");//
					tData.put("Msg", "保费计算有误，请重新提交试试，或联系客服MM");// 错误信息
					JSONObject jsonObject = JSONObject.fromObject(tData);
					logger.info("保费计算有误,产品编码：{},重新结算后保费:{},被保人总保费:{},请重新提交试试，或联系客服MM",argArr);
					return ajax(jsonObject.toString(), "text/html");
				}
				
				if (oTotalAmnt.compareTo(new BigDecimal(memberLogin
						.getCurrentValidatePoint())) > 0) {
					argArr = new Object[]{memberLogin.getId(), memberLogin.getCurrentValidatePoint(), sdorder.getOffsetPoint()};
					tData.put("Flag", "Err");//
					tData.put("Msg", "您的积分不够了，快去赚吧！");// 错误信息
					JSONObject jsonObject = JSONObject.fromObject(tData);
					logger.info("会员（{}）的可用积分（{}）小于订单要兑换用的积分（{}）", argArr);
					return ajax(jsonObject.toString(), "text/html");
				}
				
			} else {
				// 因为年险225801001的保费是固定的，所以不需要进行比较
				int count = new QueryBuilder("SELECT COUNT(*) FROM zdcode WHERE codetype = 'yearCash.riskCode' AND FIND_IN_SET('"+ productId +"', CodeValue);").executeInt();
				if (count == 0) {
					BigDecimal nTotalAmnt = totalAmnt.setScale(2, BigDecimal.ROUND_HALF_UP);
					BigDecimal oTotalAmnt = new BigDecimal(sdorder.getPayPrice()).setScale(2, BigDecimal.ROUND_HALF_UP);
					BigDecimal oInsTotalAmnt = this.insTotalAmnt.setScale(2, BigDecimal.ROUND_HALF_UP);
					Object[] argArr = {productId, nTotalAmnt, oTotalAmnt};
					if (nTotalAmnt.compareTo(oTotalAmnt) != 0) {
						tData.put("Flag", "Err");//
						tData.put("Msg", "两次计算的保费不一样，请重新提交试试！");// 错误信息
						JSONObject jsonObject = JSONObject.fromObject(tData);
						logger.error("两次计算的保费不一样，产品编码:" + productId + ",重新结算后保费:" + nTotalAmnt + ",原保费:" + oTotalAmnt + ", 请重新提交试试！");
						return ajax(jsonObject.toString(), "text/html");
					}
					if (nTotalAmnt.compareTo(oInsTotalAmnt) != 0) {
						tData.put("Flag", "Err");//
						tData.put("Msg", "保费计算有误，请重新提交试试，或联系客服MM");// 错误信息
						JSONObject jsonObject = JSONObject.fromObject(tData);
						logger.error("保费计算有误,产品编码：{},重新结算后保费:{},被保人总保费:{},请重新提交试试，或联系客服MM", argArr);
						return ajax(jsonObject.toString(), "text/html");
					}
				}
			}
			/**
			 * 判断投保期间是否被修改过
			 * 
			 */
			String recognizeeBirthday = "";
			if (sdinformationinsured != null && StringUtil.isNotEmpty(sdinformationinsured.getRecognizeeBirthday())) {
				recognizeeBirthday = sdinformationinsured.getRecognizeeBirthday();
			} else {
				recognizeeBirthday = sdinformationinsuredList.size() == 1 ? sdinformationinsuredList.get(0).getRecognizeeBirthday() 
						: sdinformationAppnt.getApplicantBirthday();
			}
			if (!checkYeMoDa(oldSDInf.getStartDate(), oldSDInf.getEnsureLimit(), oldSDInf.getEnsureLimitType(), oldSDInf.getEndDate(), recognizeeBirthday)) {
				tData.put("Flag", "Err");//
				tData.put("Msg", "订单止期与订单起期计算出的保险期限与选择期限的不一致，请重新选择试试！");// 错误信息
				JSONObject jsonObject = JSONObject.fromObject(tData);
				logger.info("orderupdate-订单止期与订单起期计算出的保险期限与选择期限的不一致，请重新选择试试！");
				return ajax(jsonObject.toString(), "text/html");
			}
			
			// 线下核保关联订单
			Map<String, String> dealResult = dealUnderwritingOffline(UnderwritingOfflineCode, this.sdinformationinsuredList, sdinformationinsured, oldSDInf.getOrderSn());
			if ("Err".equals(dealResult.get("Flag"))) {
				JSONObject jsonObject = JSONObject.fromObject(dealResult);
				return ajax(jsonObject.toString(), "text/html");
			}
			
			// 校验保费
			if (!mDealDataService.saveAll(mLMap)) {
				addActionError("矮油，出了点小状况④,点击<a vlpageid=\"xiaoneng\" exturl=\"http://www.kaixinbao.com/xiaoneng\" id=\"qqwap2\" onclick=\"return(VL_FileDL(this));return false;\" href=\"javascript:void(0);\"><这里></a>！请客服MM帮帮忙吧！");
				logger.error("返回修改后，订单信息保存失败！");
				return ERROR;
			}
			
			updateUnderwritingOffline(UnderwritingOfflineCode, oldSDOrder.getOrderSn());
			
			if ("temptorysave".equals(orderStatus)) {
				return "tempsucces";// 确定成功页面后跳转用
			} else {
				if (quesFlag) {
					tData.put("Flag", "Question");
					tData.put("OrderSn", oldSDInf.getSdorder().getOrderSn());// 总保费
					tData.put("OrderId", oldSDInf.getSdorder().getId());
					tData.put("orderFlag", orderFlag);
					JSONObject jsonObject = JSONObject.fromObject(tData);
					return ajax(jsonObject.toString(), "text/html");
				} else {
					this.setNeedUWCheckFlag(this.getNeedUWFlagFromDB(oldSDInf.getInsuranceCompany()));
					tData.put("OrderSn", oldSDInf.getSdorder().getOrderSn());// 总保费
					tData.put("OrderId", oldSDInf.getSdorder().getId());// 总保费
					tData.put("orderFlag", orderFlag);
					tData.put("Flag", "Suc");// 总保
					JSONObject jsonObject = JSONObject.fromObject(tData);
					return ajax(jsonObject.toString(), "text/html");
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			tData.put(
					"Msg",
					"矮油，出了点小状况⑤,点击<a vlpageid=\"xiaoneng\" exturl=\"http://www.kaixinbao.com/xiaoneng\" id=\"qqwap2\" onclick=\"return(VL_FileDL(this));return false;\" href=\"javascript:void(0);\"><这里></a>！请客服MM帮帮忙吧！");// 总保费
			tData.put("Flag", "Err");// 总保
			JSONObject jsonObject = JSONObject.fromObject(tData);
			return ajax(jsonObject.toString(), "text/html");
		}
	}

	/**
	 * 被保人批量导入
	 * 
	 * @return
	 * @throws Exception
	 */
	public String importBatch() throws Exception {
		logger.info("开始导入...");
		Map<String, Object> rMap = new HashMap<String, Object>();
		InputStream inp = new FileInputStream(new File(uploadfile));

		if (!inp.markSupported()) {
			inp = new PushbackInputStream(inp, 8);
		}

		if (!POIFSFileSystem.hasPOIFSHeader(inp)) {
			rMap.put("state", "2");
			rMap.put("errorMsg", "请使用正确的模板进行上传操作！");
			JSONObject jsonObject = JSONObject.fromObject(rMap);
			logger.error("被保人批量导入，模版错误");
			return ajax(jsonObject.toString(), "text/html");
		}
		if (StringUtil.isEmpty(this.effdateNew)) {
			rMap.put("state", "2");
			rMap.put("errorMsg", "请先输入起保日期！");
			JSONObject jsonObject = JSONObject.fromObject(rMap);
			logger.error("被保人批量导入，未输入起保日期");
			return ajax(jsonObject.toString(), "text/html");
		}
		File file = new File(uploadfile);
		File pFile = file.getParentFile();
		if (!pFile.exists()) {
			pFile.mkdir();
		}
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		Map<String, Object> paramter = new HashMap<String, Object>();// 产品中心信息集合
		Map<String, Object> tData = new HashMap<String, Object>();// 返回前台信息集合
		Map<String, Object> cMap = new HashMap<String, Object>();// 参数集合

		String[] BaseInformation = (String[]) getSession(productId + "baseInformation");
		riskAppFactior = (List<OrderRiskAppFactor>) getSession(productId + "riskAppFactior");
		dutyFactor = (List<OrderDutyFactor>) getSession(productId + "dutyFactor");

		if (BaseInformation == null || BaseInformation.length <= 0 || riskAppFactior == null || riskAppFactior.size() <= 0 || dutyFactor == null || dutyFactor.size() <= 0) {
			try {
				paramter = sdorderService.getProductInformation(productId, "N", channelCode);// 产品ID
			} catch (Exception e) {
				tData.put("Flag", "Err");// 总保费
				tData.put("Msg", "与产品中心交互失败③！");// 错误信息
				JSONObject jsonObject = JSONObject.fromObject(tData);
				logger.error("与产品中心交互失败，产品编码{}", productId);
				return ajax(jsonObject.toString(), "text/html");
			}
			BaseInformation = (String[]) paramter.get("baseInformation");// 产品基础数据
			riskAppFactior = (List<OrderRiskAppFactor>) paramter.get("riskAppFactor");
			dutyFactor = (List<OrderDutyFactor>) paramter.get("dutyFactor");
		}
		String tDate = effdateNew + " 00:00:00";
		Date date = sdf_2.parse(tDate);
		sdinformation.setStartDate(date);
		cMap.put("baseInformations", BaseInformation);
		cMap.put("riskAppFactior", riskAppFactior);
		cMap.put("dutyFactor", dutyFactor);
		cMap.put("productId", productId);
		cMap.put("sdinformation", sdinformation);
		cMap.put("insureJson", insureJson);
		cMap.put("dutyJson", dutyJson);
		cMap.put("productId", productId);
		cMap.put("effdate", effdateNew);
		cMap.put("channelsn", sdorder.getChannelsn());
		// cMap.put("insuredBirthDay", insuredBirthDay); 需要在后续封装被保人的生日
		cMap.put("complicatedFlag", complicatedFlag);
		cMap.put("dutyTempSerials", dutyTempSerials);
		cMap.put("textAge", textAge);
		
		rMap = orderConfigNewService.getInsuredList(supplierCode2, productId, uploadfile, cMap, memberid);

		JSONObject jsonObject = JSONObject.fromObject(rMap);
		return ajax(jsonObject.toString(), "text/html");
	}

	/**
	 * 返回修改--删除被保人相关信息
	 * 
	 * @param or
	 * @param sdinf
	 */
	public void dealMulInsuredDelete(SDOrder or, SDInformation sdinf) {
		// 被保人信息
		Set<SDInformationInsured> sdinsSet = sdinf.getSdinformationinsuredSet();
		// old 被保人集合
		List<SDInformationInsured> oldsdinsList = new ArrayList<SDInformationInsured>();
		// old 受益人集合
		List<SDInformationBnf> oldsdbnfList = new ArrayList<SDInformationBnf>();
		// old投保要素集合
		List<SDInformationInsuredElements> oldsdeleList = new ArrayList<SDInformationInsuredElements>();
		// old订单项表2
		List<SDOrderItemOth> olditemothList = new ArrayList<SDOrderItemOth>();
		// old保单信息集合
		List<SDInformationRiskType> oldrisktypeList = new ArrayList<SDInformationRiskType>();
		// old财产信息集合
		List<SDInformationProperty> oldPropertyList = new ArrayList<SDInformationProperty>();
		// 健康告知表
		List<SDInsuredHealth> oldsdinshealth = new ArrayList<SDInsuredHealth>();
		// 高额
		QuestionPaper tP = new QuestionPaper();
		for (SDInformationInsured i : sdinsSet) {
			// 被保人
			oldsdinsList.add(i);
			if (i.getSdinformationproperty() != null) {
				oldPropertyList.add(i.getSdinformationproperty());
			}
			// 受益人信息
			Set<SDInformationBnf> sdbnfSet = i.getSdinformationbnfSet();
			for (SDInformationBnf k : sdbnfSet) {
				oldsdbnfList.add(k);
			}
			// 产品投保要素
			Set<SDInformationInsuredElements> sdeleSet = i.getSdinforinselementsSet();
			for (SDInformationInsuredElements j : sdeleSet) {
				oldsdeleList.add(j);
			}
			// 订单项表2
			Set<SDOrderItemOth> itemothSet = i.getSdorderitemothSet();
			for (SDOrderItemOth m : itemothSet) {
				olditemothList.add(m);
			}
			// 保单表
			SDInformationRiskType risktype = i.getSdinformaitonrisktype();
			if (risktype != null) {
				oldrisktypeList.add(risktype);
			}
			// 健康告知
			Set<SDInsuredHealth> hthset = i.getSdinsuredHealthSet();
			for (SDInsuredHealth t : hthset) {
				if (t != null) {
					oldsdinshealth.add(t);
				}
			}
			// 高额
			tP = i.getQuestionPaper();

		}
		if (oldsdinsList != null && oldsdinsList.size() >= 1) {
			this.mLMap.put(oldsdinsList, "delete");
		}
		if (oldsdbnfList != null && oldsdbnfList.size() >= 1) {
			this.mLMap.put(oldsdbnfList, "delete");
		}
		if (oldsdeleList != null && oldsdeleList.size() >= 1) {
			this.mLMap.put(oldsdeleList, "delete");
		}
		if (olditemothList != null && olditemothList.size() >= 1) {
			this.mLMap.put(olditemothList, "delete");
		}
		if (oldrisktypeList != null && oldrisktypeList.size() >= 1) {
			this.mLMap.put(oldrisktypeList, "delete");
		}
		if (oldsdinshealth != null && oldsdinshealth.size() >= 1) {
			this.mLMap.put(oldsdinshealth, "delete");
		}
		if (oldPropertyList != null && oldPropertyList.size() >= 1) {
			this.mLMap.put(oldPropertyList, "delete");
		}
		if (tP != null) {
			this.mLMap.put(tP, "delete");
		}
	}

	/**
	 * 修改健康告知信息
	 */
	public String saveOrUpdateHealthInf() {
		try {
			List<SDInsuredHealth> tsdinsuredHealthList = new ArrayList<SDInsuredHealth>();
			order_healthySn = "HE" + NoUtil.getMaxNoUseLock("ORDERSN_HEALTHYSN", "SN");
			status = "saveHealthy";
			if (sdinsuredHealthList != null && sdinsuredHealthList.size() >= 1) {
				for (SDInsuredHealth t : sdinsuredHealthList) {
					if (t != null) {
						if (StringUtil.isNotEmpty(t.getOrderSn())) {
							status = "updateHealthy";
							orderSn = t.getOrderSn();
						}
						t.setOrderSn(order_healthySn);// 临时订单号，订单保存时删除
						t.setSelectFlag("N");
						tsdinsuredHealthList.add(t);
					}
				}
				mLMap.put(tsdinsuredHealthList, "insert&update");
			}
			
			Map<String, Object> tData = new HashMap<String, Object>();
			if (StringUtil.isNotEmpty(UnderwritingOfflineCode)) {
				DataTable dt = new QueryBuilder("select i.underwriting_status,h.orderSn from underwriting_offline_healthinfo h, underwriting_offline_info i where h.offlineCode = ? and h.info_id=i.id", UnderwritingOfflineCode).executeDataTable();
				if (dt == null || dt.getRowCount() < 1) {
					tData.put("Flag", "Err");
					tData.put("Msg", "您填写的核保编码无效，请核实！");
					return ajaxMap2Json(tData);
				} else {
					String status = dt.getString(0, 0);
					String strOrderSn = dt.getString(0, 1);
					if (StringUtil.isNotEmpty(strOrderSn) && !strOrderSn.equals(orderSn)) {
						tData.put("Flag", "Err");
						tData.put("Msg", "您填写的核保编码已使用，不能再次使用！");
						return ajaxMap2Json(tData);
					} else if (!"1".equals(status)) {
						tData.put("Flag", "Err");
						tData.put("Msg", "该线下核保未通过，不能使用该核保编码！");
						return ajaxMap2Json(tData);
					}
				}
			}
			
			if (!this.mDealDataService.saveAll(mLMap)) {
				addActionError("健康信息保存失败,请重试！");
				return ERROR;
			}
			mLMap = null;
			if (StringUtil.isNotEmpty(orderSn)) {
				KID = StringUtil.md5Hex(PubFun.getKeyValue() + orderSn);
				sdorder = sdorderService.getOrderByOrderSn(orderSn);
				orderId = sdorder.getId();
			}
			
			tData.put("ORDERSN_HEALTHYSN", order_healthySn);
			tData.put("productId", productId);
			tData.put("TextAge", textAge);
			tData.put("Period", period);
			tData.put("Plan", plan);
			tData.put("Flag", "healthpass");// 总保
			tData.put("saveFlag", status);
			tData.put("orderSn", orderSn);
			tData.put("KID", KID);
			tData.put("orderId", orderId);
			tData.put("sourceFlag", sourceFlag);
			tData.put("feeYear", feeYear);
			tData.put("appLevel", appLevel);
			tData.put("appType", appType);
			tData.put("Sex", Sex);
			tData.put("applicantBirthday", applicantBirthday);
			tData.put("complicatedFlag", complicatedFlag);
			tData.put("dutyTempSerials", dutyTempSerials);
			tData.put("pointExchangeFlag", pointExchangeFlag);
			JSONObject jsonObject = JSONObject.fromObject(tData);
			return ajax(jsonObject.toString(), "text/html");

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionError("健康告知存储失败，请重试！");
			return ERROR;
		}
	}

	/**
	 * 订单预览主方法
	 * 
	 * @param cOrderSn
	 *            订单号
	 */
	private void showInsuredInfomation(String cOrderSn) {
		// 根据OrderSn获取SDOrder实体类
		sdorder = this.sdorderService.getOrderByOrderSn(cOrderSn);

		// 订单详细表
		Set<SDInformation> tSDInformationSet = sdorder.getSdinformationSet();
		for (SDInformation tSDInformation : tSDInformationSet) {
			this.sdinformation = tSDInformation;
		}
		// 保险期限信息
		showPeriods = this.sdinformationService.createShowInformationPeriod(sdinformation);
		// 投保人信息
		Set<SDInformationAppnt> tSDInformationAppnt = sdinformation.getSdinformationappntSet();
		if (tSDInformationAppnt != null && tSDInformationAppnt.size() >= 1) {
			showAppnts = this.sdinformationAppntService.createShowInformationAppnt(tSDInformationAppnt, sdinformation.getInsuranceCompany());
		}
		// 投保人地区2级
		if (StringUtil.isNotEmpty(sdinformationAppnt.getApplicantArea2()) && StringUtil.isEmpty(sdinformationAppnt.getApplicantArea3())) {
			sdinformationAppnt.setApplicantArea2(areaService.getAreaName(sdinformationAppnt.getApplicantArea2()));
		}
		
		// 续期交费信息
		directPayBankInfo = directPayBankInfoService.getByOrderSn(cOrderSn);
		if(null == directPayBankInfo){
			directPayBankInfo = new DirectPayBankInfo();
		}else if(StringUtil.isNotEmpty(directPayBankInfo.getBankProvince())){
			setBankCityAndBankProvince();
		}
		// 责任投保要素信息
		Set<SDInformationDuty> tSDInformationDutySet = sdinformation.getSdinformationDutySet();
		if (tSDInformationDutySet != null && tSDInformationDutySet.size() > 0) {
			showDuty = new ArrayList<SDInformationDuty>(tSDInformationDutySet);
			if (showDuty != null && showDuty.size() > 0) {
				for (int i = 0; i < showDuty.size(); i++) {
					if ("N".equals(showDuty.get(i).getIsDisplay())) {
						showDuty.remove(i);
						i--;
					}
				}
			}
		}
		// 被保人信息
		Set<SDInformationInsured> tSDInformationInsured = sdinformation.getSdinformationinsuredSet();
		if (tSDInformationInsured != null && tSDInformationInsured.size() > 0) {
			// 重新整理被保人信息
			
			showInsureds = sdinformationInsuredService.createShowInformationInsuredNew(tSDInformationInsured, sdinformation.getInsuranceCompany(),cOrderSn);
			if (tSDInformationInsured.size() == 1) {
				for (SDInformationInsured insured : tSDInformationInsured) {
					SDInformationProperty sdp = insured.getSdinformationproperty();
					showPropertys = sdinformationPropertyService.getcreateShowOneProperty(sdp, sdinformation.getInsuranceCompany(), sdinformation.getProductId());
				}
			}
		}
		//TODO 订单预览获取受益人信息 req20150323004
		if (tSDInformationInsured != null && tSDInformationInsured.size() > 0) {
			// 重新整理收益人信息
			showBnfs = sdinformationInsuredService.createBnfShow(tSDInformationInsured);
		}
		QueryBuilder qbScope = new QueryBuilder("SELECT codevalue FROM zdcode WHERE codetype='singleDestination' AND parentcode='singleDestination'");
		DataTable dtScope = qbScope.executeDataTable();
		boolean singleFlag = false;
		if (dtScope != null && dtScope.getRowCount() > 0) {
			for (int j = 0; j < dtScope.getRowCount(); j++) {
				if (dtScope.getString(j, "codevalue").equals(sdinformation.getInsuranceCompany())) {
					singleFlag = true;
					break;
				}
			}
		}
		// 旅游目的地
		if (!singleFlag) {
			for (SDInformationInsured t : tSDInformationInsured) {
				if ((t.getDestinationCountry() != null && !"".equals(t.getDestinationCountry())) && (t.getDestinationCountryText() != null && !"".equals(t.getDestinationCountryText()))) {
					this.insuredToCountry = sdinformationInsuredService.getInsuredToCountry(t);
				}
				break;
			}
		}
		// 由于excle导入方式与正常录入方式的订单预览显示规则相同，故被保人数量用同一个字段表示
		// 但是返回修改时，用的是不同的
		List<SDInformationInsured> sdinfquery = new ArrayList<SDInformationInsured>();
		Map<String, String> keyMap = new HashMap<String, String>();
		for (SDInformationInsured sdinf : sdinformation.getSdinformationinsuredSet()) {
			if (!"Y".equals(keyMap.get(sdinf.getRecognizeeKey()))) {
				sdinfquery.add(sdinf);
			}
			keyMap.put(sdinf.getRecognizeeKey(), "Y");
		}
		this.insuredResultCount = sdinfquery.size();
		this.insuredCount = sdinformation.getSdinformationinsuredSet().size();
		//TODO 订单预览多受益人显示数量 req20150323004
		for(SDInformationInsured sdinf:sdinformation.getSdinformationinsuredSet()){
			Set<SDInformationBnf> bnfSet = sdinf.getSdinformationbnfSet();
			this.sdInformationBnfCount += bnfSet.size();
		}
		if(this.sdInformationBnfCount != 1){
			this.sdInformationBnfCount = this.sdInformationBnfCount - 1;
		}
		// 取得复杂产品标识
		complicatedFlag = new QueryBuilder("select complicatedFlag from sdproduct where productId=?", sdinformation.getProductId()).executeString();

	}
	
	
	
	public void setBankCityAndBankProvince(){

		String sqlArea = "select id,name from area where 1=1 and id in(?,?) order by id asc";
		QueryBuilder qb = new QueryBuilder(sqlArea);
		qb.add(directPayBankInfo.getBankProvince());
		qb.add(directPayBankInfo.getBankCity());
		DataTable dt = qb.executeDataTable();
		for(int i=0; i<dt.getRowCount();i++){
			if(StringUtil.isNotEmpty(directPayBankInfo.getBankProvince()) && directPayBankInfo.getBankProvince().equals(dt.get(i).get("id"))){
				directPayBankInfo.setBankProvince(dt.get(i).get("name").toString());
			}
            if(StringUtil.isNotEmpty(directPayBankInfo.getBankCity()) &&  directPayBankInfo.getBankCity().equals(dt.get(i).get("id")) ){
				directPayBankInfo.setBankCity(dt.get(i).get("name").toString());	
			}
		}
		
	}

	/**
	 * 
	 * 投保人信息保存
	 */
	private void informationAppntSave(SDInformation sdinformations) {
		sdinformationAppnt.setSdinformaiton(sdinformations);
		String tApplicantSn = PubFun.GetSDAppntSn();// 需要修改规则
		sdinformationAppnt.setApplicantSn(tApplicantSn);
		sdinformationAppnt.setInformationSn(sdinformations.getInformationSn());
		// 得到证件类型名称
		String applicantIdentityTypeName = this.dictionaryService.getNameByCodeTypePro(sdinformation.getProductId(), sdinformation.getInsuranceCompany(), "certificate",
				sdinformationAppnt.getApplicantIdentityType());
		String applicantSexName = this.dictionaryService.getNameByCodeTypePro(sdinformation.getProductId(), sdinformation.getInsuranceCompany(), "Sex", sdinformationAppnt.getApplicantSex());
		// 投保人中英文姓名信息
		// sdinformationAppnt.setApplicantLastName(CountryChineseSpelling.convertLastName(sdinformationAppnt.getApplicantName()));
		// sdinformationAppnt.setApplicantFirstName(CountryChineseSpelling.convertFirstName(sdinformationAppnt.getApplicantName()));
		// sdinformationAppnt.setApplicantEnName(CountryChineseSpelling.convert(sdinformationAppnt.getApplicantName()));
		sdinformationAppnt.setApplicantSexName(applicantSexName);
		int appAge = com.sinosoft.sms.messageinterface.pubfun.PubFun.getAge(sdinformationAppnt.getApplicantBirthday());
		sdinformationAppnt.setApplicantAge(String.valueOf(appAge));
		// 投保人所在地区邮编

		if (StringUtil.isEmpty(sdinformationAppnt.getApplicantZipCode()) && !"".equals(sdinformationAppnt.getApplicantArea1()) && sdinformationAppnt.getApplicantArea1() != null) {
			sdinformationAppnt.setApplicantZipCode(this.getZipInfo(this.areaService.getAreaName(sdinformationAppnt.getApplicantArea1()),
					this.areaService.getAreaName(sdinformationAppnt.getApplicantArea2())));
		}
		sdinformationAppnt.setApplicantIdentityTypeName(applicantIdentityTypeName);
		
		// 证件有效期勾选长期时
		if (StringUtil.isNotEmpty(sdinformationAppnt.getApplicantStartID()) && StringUtil.isEmpty(sdinformationAppnt.getApplicantEndID())) {
			sdinformationAppnt.setApplicantEndID("9999-12-31");
		}
	}

	/**
	 * 复制投保人信息到被保人
	 * 
	 * @param insured
	 *            被保人信息
	 * @param appnt
	 *            投保人信息
	 */
	private SDInformationInsured setInsuredFromAppnt(SDInformationInsured cInsured, SDInformationAppnt appnt) {
		SDInformationInsured insured = cInsured;
		if ((null == cInsured) || (null == appnt)) {
			return insured;
		}
		if ("Y".equals(cInsured.getIsSelf())) {
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
			insured.setRecognizeeFirstName("");
			insured.setRecognizeeLashName("");
			insured.setRecognizeeAddress(appnt.getApplicantAddress());
			insured.setRecognizeeZipCode(appnt.getApplicantZipCode());
			// zhangjinquan 11180 保存被保险人的职业类别 2012-11-28
			if (appnt.getApplicantOccupation1() != null && !"".equals(appnt.getApplicantOccupation1())) {
				insured.setRecognizeeOccupation1(appnt.getApplicantOccupation1());
			} else {
				insured.setRecognizeeOccupation1(cInsured.getRecognizeeOccupation1());
			}
			if (appnt.getApplicantOccupation2() != null && !"".equals(appnt.getApplicantOccupation2())) {
				insured.setRecognizeeOccupation2(appnt.getApplicantOccupation2());
			} else {
				insured.setRecognizeeOccupation2(cInsured.getRecognizeeOccupation2());
			}
			if (appnt.getApplicantOccupation3() != null && !"".equals(appnt.getApplicantOccupation3())) {
				insured.setRecognizeeOccupation3(appnt.getApplicantOccupation3());
			} else {
				insured.setRecognizeeOccupation3(cInsured.getRecognizeeOccupation3());
			}

		}
		return insured;
	}

	/**
	 * 多被保人情况下选择本人页签
	 * 
	 * @param sdinf
	 *            保单详细信息
	 */
	public void sdinformationOneInsuredSave(SDInformation sdinf, SDOrder sdor, SDInformationAppnt sdapp, Boolean cFlag) {
		// this.sdinformationinsured;
		// this.sdinformationAppnt;
		// 多被保人选择本人
		int index = 1;
		if (("rid_me".equals(mulInsuredFlag) || "Y".equals(this.relationIsSelf)) && !"rid_td".equals(this.mulInsuredFlag)) {

			String recognizeeSn = PubFun.GetSDInsuredSn();// 被保人编号，规则需要修改
			SDInformationRiskType sdinformationRiskTypes = new SDInformationRiskType();
			// sdinformationRiskTypes.setId("200001");// 险种，由接口获得
			sdinformationRiskTypes.setOrderSn(sdor.getOrderSn());// 订单号
			sdinformationRiskTypes.setInformationSn(sdinf.getInformationSn());// 订单明细编号
			sdinformationRiskTypes.setRecognizeeSn(recognizeeSn);// 被保人编号
			sdinformationRiskTypes.setApplicantSn(sdapp.getApplicantSn());// 投保人编号
			sdinformationRiskTypes.setPolicyNo("");// 保单号，需要处理
			sdinformationRiskTypes.setRiskCode(sdinf.getProductId());// 险种编码
			sdinformationRiskTypes.setRiskName(sdinf.getProductName());// 险种名称
			sdinformationRiskTypes.setAmnt(getInsuredAmnt(this.dutyFactor, sdinf.getPlanCode()));// 保额，需要处理
			sdinformationRiskTypes.setMult("1");// 份数，需要处理

			sdinformationRiskTypes.setTimePrem(sdorder.getTotalAmount().toString());// 保费，需要处理
			sdinformationRiskTypes.setProductPrice(sdorder.getProductTotalPrice().toString());
			sdinformationRiskTypes.setSvaliDate(sdinf.getStartDate());// 生效日期
			sdinformationRiskTypes.setEvaliDate(sdinf.getEndDate());// 失效日期
			// 以下字段待定
			sdinformationRiskTypes.setPeriodFlag(sdinf.getChargeType());// 缴费年期类型
			sdinformationRiskTypes.setPeriod(sdinf.getChargeYear());// 缴费年期
			sdinformationRiskTypes.setElectronicCout("");// 电子保单保险公司路径
			sdinformationRiskTypes.setElectronicPath("");// 电子保单物理路径
			sdinformationRiskTypes.setInsurerFlag("");
			sdinformationRiskTypes.setInsureMsg("");
			
			sdinformationRiskTypes.setSdorder(sdor);
			sdinformationRiskTypes.setPolicyNoOld(policyNoOld);
			sdinformationRiskTypes.setSdinformationinsured(sdinformationinsured);
			mSDInformationRiskTypeSet.add(sdinformationRiskTypes);

			sdinformationinsured.setId("");
			sdinformationinsured.setSdinformaitonrisktype(sdinformationRiskTypes);
			sdinformationinsured.setRecognizeeSn(recognizeeSn);
			sdinformationinsured.setInformationSn(sdinf.getInformationSn());
			sdinformationinsured.setRecognizeeName(sdapp.getApplicantName());
			sdinformationinsured.setRecognizeeIdentityType(sdapp.getApplicantIdentityType());
			sdinformationinsured.setRecognizeeIdentityId(sdapp.getApplicantIdentityId());
			sdinformationinsured.setRecognizeeIdentityTypeName(sdapp.getApplicantIdentityTypeName());
			sdinformationinsured.setRecognizeeSex(sdapp.getApplicantSex());
			sdinformationinsured.setRecognizeeSexName(sdapp.getApplicantSexName());
			sdinformationinsured.setRecognizeeMail(sdapp.getApplicantMail());
			int tInsuredAge = com.sinosoft.sms.messageinterface.pubfun.PubFun.getAge(sdapp.getApplicantBirthday());
			sdinformationinsured.setRecognizeeAge(String.valueOf(tInsuredAge));
			String recognizeeAppntRelation = this.dictionaryService.getCodeValueByCodeName(sdinf.getInsuranceCompany(), "Relationship", "本人");
			sdinformationinsured.setRecognizeeAppntRelation(recognizeeAppntRelation);
			sdinformationinsured.setRecognizeeAppntRelationName("本人");
			// 被保人唯一编码
			sdinformationinsured.setInsuredSn(orderConfigNewService.getInsuredSn(sdor.getOrderSn(), index, sdinf.getInsuranceCompany()));
			sdinformationinsured.setRecognizeeKey(sdor.getOrderSn() + "_" + index);
			if (StringUtil.isNotEmpty(sdapp.getApplicantArea1())) {
				sdinformationinsured.setRecognizeeArea1(sdapp.getApplicantArea1());
			}
			if (StringUtil.isNotEmpty(sdapp.getApplicantArea2())) {
				sdinformationinsured.setRecognizeeArea2(sdapp.getApplicantArea2());
			}
			if (StringUtil.isNotEmpty(sdapp.getApplicantArea3())) {
				sdinformationinsured.setRecognizeeArea3(sdapp.getApplicantArea3());
			}
			if (StringUtil.isNotEmpty(sdinformationinsured.getDestinationCountryText())) {
				
				sdinformationinsured.setDestinationCountryText(sdinformationinsured.getDestinationCountryText());
			}


			
			
			if (StringUtil.isNotEmpty(sdapp.getApplicantStartID())) {
				sdinformationinsured.setRecognizeeStartID(sdapp.getApplicantStartID());
				// 证件有效期勾选长期时
				if (StringUtil.isEmpty(sdapp.getApplicantEndID())) {
					sdinformationinsured.setRecognizeeEndID("9999-12-31");
				}
			}
			if (StringUtil.isNotEmpty(sdapp.getApplicantEndID())) {
				sdinformationinsured.setRecognizeeEndID(sdapp.getApplicantEndID());
			}
			if (StringUtil.isNotEmpty(sdapp.getApplicantBirthday())) {
				sdinformationinsured.setRecognizeeBirthday(sdapp.getApplicantBirthday());
			}
			if (StringUtil.isNotEmpty(sdapp.getApplicantMobile())) {
				sdinformationinsured.setRecognizeeMobile(sdapp.getApplicantMobile());
			}
			if (StringUtil.isNotEmpty(sdapp.getApplicantAddress())) {
				sdinformationinsured.setRecognizeeAddress(sdapp.getApplicantAddress());
			}
			if (StringUtil.isNotEmpty(sdapp.getApplicantZipCode())) {
				sdinformationinsured.setRecognizeeZipCode(sdapp.getApplicantZipCode());
			}
			// 保存被保险人的职业类别
			if (StringUtil.isNotEmpty(sdapp.getApplicantOccupation1())) {
				sdinformationinsured.setRecognizeeOccupation1(sdapp.getApplicantOccupation1());
			}
			if (StringUtil.isNotEmpty(sdapp.getApplicantOccupation2())) {
				sdinformationinsured.setRecognizeeOccupation2(sdapp.getApplicantOccupation2());
			}
			if (StringUtil.isNotEmpty(sdapp.getApplicantOccupation3())) {
				sdinformationinsured.setRecognizeeOccupation3(sdapp.getApplicantOccupation3());
			}
			if (StringUtil.isNotEmpty(sdapp.getApplicantEnName())) {
				sdinformationinsured.setRecognizeeEnName(sdapp.getApplicantEnName());
			}
			if (StringUtil.isNotEmpty(sdapp.getSocialSecurity())) {
				sdinformationinsured.setSocialSecurity(sdapp.getSocialSecurity());
			}
			sdinformationinsured.setInformationSn(sdinf.getInformationSn());
			sdinformationinsured.setOrderSn(sdor.getOrderSn());
			String recognizeeIdentityTypeName = this.dictionaryService.getNameByCodeTypePro(sdinf.getProductId(), sdinf.getInsuranceCompany(), "certificate",
					sdinformationinsured.getRecognizeeIdentityId());

			// sdinformationinsured.setRecognizeeIdentityTypeName(recognizeeIdentityTypeName);
			// 设置旅游目的地字段
			String destinationCountryText = "";
			if (StringUtil.isNotEmpty(sdinformationinsured.getDestinationCountry()) && !"1015".equals(sdinf.getInsuranceCompany())) {
				destinationCountryText = this.dictionaryService.getNameByCodeTypePro(sdinf.getProductId(),sdinf.getInsuranceCompany(), "CountryCode", sdinformationinsured.getDestinationCountry());
				sdinformationinsured.setDestinationCountryText(destinationCountryText);
			} else {
				destinationCountryText = setDestinationCountry(sdinf.getProductId(), sdinf.getInsuranceCompany(), sdinformationinsured, dictionaryService);
				if ("2007".equals(sdinf.getInsuranceCompany()) && DestinationCountry != null && !"".equals(DestinationCountry)) {
					if (DestinationCountry != null && !"".equals(DestinationCountry)) {
						String CountryText = this.orderConfigNewService.getCountryText2007("2007", DestinationCountry);
						sdinformationinsured.setDestinationCountry(this.DestinationCountry);
						sdinformationinsured.setDestinationCountryText(CountryText);
					}
				} else if ("1015".equals(sdinf.getInsuranceCompany()) && DestinationCountry != null && !"".equals(DestinationCountry)) {
					sdinformationinsured.setDestinationCountry(this.DestinationCountry);
					sdinformationinsured.setDestinationCountryText(this.orderConfigNewService.getCountryText1015("1015", DestinationCountry));
				} else if (("2011".equals(sdinf.getInsuranceCompany()) || "2023".equals(sdinf.getInsuranceCompany()) || "2071".equals(sdinf.getInsuranceCompany())) && DestinationCountry != null
						&& !"".equals(DestinationCountry)) {
					sdinformationinsured.setDestinationCountry(this.DestinationCountry);
					sdinformationinsured.setDestinationCountryText(this.orderConfigNewService.getSchengenCountryText(sdinf.getInsuranceCompany(), DestinationCountry,sdinf.getProductId()));
				} else {
					if (DestinationCountry != null && !"".equals(DestinationCountry)) {
						sdinformationinsured.setDestinationCountry(this.DestinationCountry);
						sdinformationinsured.setDestinationCountryText(destinationCountryText);
					}
				}
			}
			
			// Modify By 梅俊峰 20130620 end
			// 被保人邮编
			if (StringUtil.isEmpty(sdinformationinsured.getRecognizeeZipCode()) && !"".equals(sdinformationinsured.getRecognizeeArea1()) && sdinformationinsured.getRecognizeeArea1() != null) {
				sdinformationinsured.setRecognizeeZipCode(this.getZipInfo(this.areaService.getAreaName(sdinformationinsured.getRecognizeeArea1()),
						this.areaService.getAreaName(sdinformationinsured.getRecognizeeArea2())));
			}
			// 被保人操作符
			if (this.mulInsuredFlag != null && !"".equals(this.mulInsuredFlag)) {
				sdinformationinsured.setMulInsuredFlag(this.mulInsuredFlag);
			}
			sdinformationinsured.setRecognizeePrem(sdor.getTotalAmount().toString());
			sdinformationinsured.setRecognizeeTotalPrem(sdor.getProductTotalPrice().toString());
			sdinformationinsured.setDiscountPrice(sdor.getPayPrice());
			// 购买份数处理
			if (sdinformationinsured.getRecognizeeMul() != null && !"".equals(sdinformationinsured.getRecognizeeMul())) {
				sdinformationinsured.setRecognizeePrem(String.valueOf((Double.parseDouble(sdinformationinsured.getRecognizeePrem()) / Double.parseDouble(sdinformationinsured.getRecognizeeMul()))));
				sdinformationinsured.setRecognizeeTotalPrem(String.valueOf((Double.parseDouble(sdinformationinsured.getRecognizeeTotalPrem()) / Double.parseDouble(sdinformationinsured
						.getRecognizeeMul()))));
				sdinformationinsured.setDiscountPrice(String.valueOf((Double.parseDouble(sdinformationinsured.getDiscountPrice()) / Double.parseDouble(sdinformationinsured.getRecognizeeMul()))));
				sdinformationRiskTypes.setTimePrem(sdinformationinsured.getRecognizeePrem());// 保费，需要处理
				sdinformationRiskTypes.setProductPrice(sdinformationinsured.getRecognizeeTotalPrem());
			}
			if (Constant.JFSC_CHANNELSN.equals(sdor.getChannelsn())
					|| Constant.WJ_JFSC_CHANNELSN.equals(sdor
							.getChannelsn())
					|| Constant.WAP_JFSC_CHANNELSN.equals(sdor
							.getChannelsn())) {
				this.insTotalAmnt = new BigDecimal(sdinformationinsured.getRecognizeePrem());
			} else {
				this.insTotalAmnt = new BigDecimal(sdinformationinsured.getDiscountPrice());
			}
			
			
			sdinformationinsured.setRecognizeeOperate(this.recognizeeOperate);
			if ("Y".equals(this.relationIsSelf)) {
				sdinformationinsured.setRecognizeeOperate("4");
			}
			sdinformationinsured.setSdinformation(sdinf);
			sdinformationinsured.setSdinformaitonrisktype(sdinformationRiskTypes);
			if (cFlag) {
				sdinformationinsured.setQuestionPaper(questionPaper);
				questionPaper.setOrdID(sdor.getOrderSn());
				questionPaper.setEffective(com.sinosoft.sms.messageinterface.pubfun.PubFun.getCurrentDate());
				questionPaper.setSdinformationInsured(sdinformationinsured);
				// questionPaperService.save(questionPaper);
			}
			
			
			// 处理SDOrderItemOth信息
			this.orderItemOthSave(sdinformationinsured, sdinf);
			// 处理产品投保要素信息
			sdInformationInsuredElementsSave(sdinformationinsured);

			// 购买份数处理----拆分被保人信息
			dealInsuredBuyCount(sdinformationinsured, sdinformationRiskTypes, sdinf);
		}
	}

	/**
	 * 订单保存--处理多被保人--其他被保人、单被保人
	 * 
	 * @param sdor
	 * @param sdinf
	 * @param sdapp
	 * @param cFlag
	 * @param crisktype
	 * @return
	 */
	public List<SDInformationInsured> sdinformationMulInsuredSave(SDOrder sdor, SDInformation sdinf, SDInformationAppnt sdapp, Boolean cFlag, String crisktype) {
		/*
		 * 適用於多被保人存儲 循環處理多被保人
		 */
		questionList = new ArrayList<QuestionPaper>();
		List<SDInformationInsured> tSDInformationInsuredList = new ArrayList<SDInformationInsured>();
		double insPrem = 0.00;
		if (("rid_orther".equals(this.mulInsuredFlag) || "".equals(mulInsuredFlag) || mulInsuredFlag == null) && !"Y".equals(relationIsSelf) && !"rid_td".equals(this.mulInsuredFlag)) {

			if (sdinformationinsuredList.size() >= 1 && sdinformationinsuredList != null) {
				int index = 1;
				for (SDInformationInsured sdinformationinsured : sdinformationinsuredList) {

					if (sdinformationinsured != null) {

						QuestionPaper questionPaper = new QuestionPaper();
						// 被保人编号，规则需要修改
						String recognizeeSn = PubFun.GetSDInsuredSn();
						SDInformationRiskType sdinformationRiskTypes = new SDInformationRiskType();
						// sdinformationRiskTypes.setId("200001");// 险种，由接口获得
						// 订单号
						sdinformationRiskTypes.setOrderSn(sdor.getOrderSn());
						// 订单明细编号
						sdinformationRiskTypes.setInformationSn(sdinf.getInformationSn());
						// 被保人编号
						sdinformationRiskTypes.setRecognizeeSn(recognizeeSn);
						// 投保人编号
						sdinformationRiskTypes.setApplicantSn(sdapp.getApplicantSn());
						// 保单号，需要处理
						sdinformationRiskTypes.setPolicyNo("");
						// 险种编码
						sdinformationRiskTypes.setRiskCode(sdinf.getProductId());
						// 险种名称
						sdinformationRiskTypes.setRiskName(sdinf.getProductName());
						// 保额，需要处理
						sdinformationRiskTypes.setAmnt(this.getInsuredAmnt(dutyFactor, sdinf.getPlanCode()));
						// 份数，需要处理
						sdinformationRiskTypes.setMult("1");
						// 保费，需要处理
						sdinformationRiskTypes.setTimePrem(sdinformationinsured.getRecognizeePrem());
						sdinformationRiskTypes.setProductPrice(sdinformationinsured.getRecognizeeTotalPrem());
						// 生效日期
						sdinformationRiskTypes.setSvaliDate(sdinf.getStartDate());
						// 失效日期
						sdinformationRiskTypes.setEvaliDate(sdinf.getEndDate());
						// 以下字段待定
						// 缴费年期类型
						sdinformationRiskTypes.setPeriodFlag(sdinf.getChargeType());
						sdinformationRiskTypes.setPeriod(sdinf.getChargeYear());// 缴费年期
						sdinformationRiskTypes.setElectronicCout("");// 电子保单保险公司路径
						sdinformationRiskTypes.setElectronicPath("");// 电子保单物理路径
						sdinformationRiskTypes.setInsurerFlag("");
						sdinformationRiskTypes.setInsureMsg("");
						// sdinformationRiskTypes.setBalanceDate("");
						// sdinformationRiskTypes.setBalanceFlag("");
						// sdinformationRiskTypes.setBalanceMsg("");
						// sdinformationRiskTypes.setBalanceStatus("");
						sdinformationRiskTypes.setSdorder(sdor);
						sdinformationRiskTypes.setPolicyNoOld(policyNoOld);
						sdinformationRiskTypes.setSdinformationinsured(sdinformationinsured);
						mSDInformationRiskTypeSet.add(sdinformationRiskTypes);

						// 单独提取出函数设置被保人为本人时的数据
						// sdinformationinsured =
						// this.setInsuredFromAppnt(sdinformationinsured,
						// sdapp);
						sdinformationinsured.setSdinformaitonrisktype(sdinformationRiskTypes);
						sdinformationinsured.setInformationSn(sdinf.getInformationSn());
						sdinformationinsured.setOrderSn(sdor.getOrderSn());
						sdinformationinsured.setRecognizeeSn(recognizeeSn);
						String recognizeeIdentityTypeName = this.dictionaryService.getNameByCodeTypePro(sdinf.getProductId(), sdinf.getInsuranceCompany(), "certificate",
								sdinformationinsured.getRecognizeeIdentityType());
						String recognizeeAppntRelationName = this.dictionaryService.getNameByCodeTypePro(sdinf.getProductId(), sdinf.getInsuranceCompany(), "Relationship",
								sdinformationinsured.getRecognizeeAppntRelation());

						String recognizeeSexName = this.dictionaryService.getNameByCodeTypePro(sdinf.getProductId(), sdinf.getInsuranceCompany(), "Sex", sdinformationinsured.getRecognizeeSex());
						sdinformationinsured.setRecognizeeSexName(recognizeeSexName);
						sdinformationinsured.setRecognizeeIdentityTypeName(recognizeeIdentityTypeName);
						sdinformationinsured.setRecognizeeAppntRelationName(recognizeeAppntRelationName);
						sdinformationinsured.setDestinationCountryText(sdinformationinsured.getDestinationCountryText());
 						// 设置旅游目的地字段
						String destinationCountryText = "";
						if (StringUtil.isNotEmpty(sdinformationinsured.getDestinationCountry()) && !"1015".equals(sdinformation.getInsuranceCompany())) {
							destinationCountryText = this.dictionaryService.getNameByCodeTypePro(sdinf.getProductId(), sdinf.getInsuranceCompany(), "CountryCode",
									sdinformationinsured.getDestinationCountry());
							
							sdinformationinsured.setDestinationCountryText(destinationCountryText);
						} else {
							destinationCountryText = setDestinationCountry(sdinf.getProductId(), sdinformation.getInsuranceCompany(), sdinformationinsured, dictionaryService);
						
					 
							if ("2007".equals(sdinf.getInsuranceCompany()) && DestinationCountry != null && !"".equals(DestinationCountry)) {
								if (DestinationCountry != null && !"".equals(DestinationCountry)) {
									String CountryText = this.orderConfigNewService.getCountryText2007("2007", DestinationCountry);
									sdinformationinsured.setDestinationCountry(this.DestinationCountry);
									sdinformationinsured.setDestinationCountryText(CountryText);
								}
							} else if ("1015".equals(sdinf.getInsuranceCompany()) && DestinationCountry != null && !"".equals(DestinationCountry)) {
								sdinformationinsured.setDestinationCountry(this.DestinationCountry);
								sdinformationinsured.setDestinationCountryText(this.orderConfigNewService.getCountryText1015("1015", DestinationCountry));
							} else if (("2011".equals(sdinf.getInsuranceCompany()) || "2023".equals(sdinf.getInsuranceCompany()) || "2071".equals(sdinf.getInsuranceCompany()))
									&& DestinationCountry != null && !"".equals(DestinationCountry)) {
								sdinformationinsured.setDestinationCountry(this.DestinationCountry);
								sdinformationinsured.setDestinationCountryText(this.orderConfigNewService.getSchengenCountryText(sdinf.getInsuranceCompany(), DestinationCountry,sdinf.getProductId()));
							} else {
								if (DestinationCountry != null && !"".equals(DestinationCountry)) {
									sdinformationinsured.setDestinationCountry(this.DestinationCountry);
									sdinformationinsured.setDestinationCountryText(destinationCountryText);
								}
							}
						}
						
						sdinformationinsured.setSdinformation(sdinf);
						// 被保人邮编
						if (StringUtil.isEmpty(sdinformationinsured.getRecognizeeZipCode()) && !"".equals(sdinformationinsured.getRecognizeeArea1())
								&& sdinformationinsured.getRecognizeeArea1() != null) {
							sdinformationinsured.setRecognizeeZipCode(this.getZipInfo(this.areaService.getAreaName(sdinformationinsured.getRecognizeeArea1()),
									this.areaService.getAreaName(sdinformationinsured.getRecognizeeArea2())));
						}
						sdinformationinsured.setInsuredSn(orderConfigNewService.getInsuredSn(sdor.getOrderSn(), index, sdinf.getInsuranceCompany()));
						sdinformationinsured.setRecognizeeKey(sdor.getOrderSn() + "_" + index);
						
						// 证件有效期勾选长期时
						if (StringUtil.isNotEmpty(sdinformationinsured.getRecognizeeStartID()) && StringUtil.isEmpty(sdinformationinsured.getRecognizeeEndID())) {
							sdinformationinsured.setRecognizeeEndID("9999-12-31");
						}
						
						index = index + 1;
						int tInsuredAge = com.sinosoft.sms.messageinterface.pubfun.PubFun.getAge(sdinformationinsured.getRecognizeeBirthday());
						sdinformationinsured.setRecognizeeAge(String.valueOf(tInsuredAge));
						if (this.mulInsuredFlag != null && !"".equals(this.mulInsuredFlag)) {
							sdinformationinsured.setMulInsuredFlag(this.mulInsuredFlag);
						}
						if (Constant.JFSC_CHANNELSN.equals(sdor.getChannelsn())
								|| Constant.WJ_JFSC_CHANNELSN.equals(sdor.getChannelsn())
								|| Constant.WAP_JFSC_CHANNELSN.equals(sdor.getChannelsn())) {
							if (StringUtil.isNotEmpty(sdinformationinsured.getRecognizeePrem())) {
								insPrem = insPrem + Double.parseDouble(sdinformationinsured.getRecognizeePrem());
							}
						} else {
							if (StringUtil.isNotEmpty(sdinformationinsured.getDiscountPrice())) {
								insPrem = insPrem + Double.parseDouble(sdinformationinsured.getDiscountPrice());
							}
						}
						
						sdinformationinsured.setRecognizeeOperate(this.recognizeeOperate);
						// 处理SDOrderItemOth信息
						this.orderItemOthSave(sdinformationinsured, sdinf);
						sdInformationInsuredElementsSave(sdinformationinsured);
						sdinformationinsured.setId("");
						if (cFlag) {
							sdinformationinsured.setQuestionPaper(questionPaper);
							questionPaper.setOrdID(sdor.getOrderSn());
							questionPaper.setEffective(com.sinosoft.sms.messageinterface.pubfun.PubFun.getCurrentDate());
							questionPaper.setSdinformationInsured(sdinformationinsured);
							questionList.add(questionPaper);
							// questionPaperService.save(questionPaper);
						}

						tSDInformationInsuredList.add(sdinformationinsured);
						// 购买份数处理----拆分被保人信息
						dealInsuredBuyCount(sdinformationinsured, sdinformationRiskTypes, sdinf);
					}
				}
				
				this.insTotalAmnt = insTotalAmnt.add(new BigDecimal(insPrem));
				
			} else if ("11".equals(crisktype)) {
				SDInformationInsured sdInsured = sdinformationInsuredService.getInsuredByAppnt(sdinf, sdapp);
				String recognizeeSn = sdInsured.getRecognizeeSn();
				SDInformationRiskType informationRiskType = sdinformationRiskTypeService.getInformationRiskType(sdor, sdinf, sdapp, recognizeeSn);
				informationRiskType.setAmnt(this.getInsuredAmnt(dutyFactor, sdinf.getPlanCode()));
				if (sdor.getTotalAmount() != null) {
					informationRiskType.setTimePrem(sdor.getTotalAmount().toString());
					sdInsured.setRecognizeePrem(sdor.getTotalAmount().toString());
					sdInsured.setDiscountPrice(sdor.getPayPrice());
				}
				if(sdor.getProductTotalPrice() != null){
					informationRiskType.setProductPrice(sdor.getProductTotalPrice().toString());
					sdInsured.setRecognizeeTotalPrem(sdor.getProductTotalPrice().toString());
				}
				informationRiskType.setSdinformationinsured(sdInsured);
				sdInsured.setSdinformaitonrisktype(informationRiskType);
				sdInsured.setInsuredSn(orderConfigNewService.getInsuredSn(sdor.getOrderSn(), 1, sdinf.getInsuranceCompany()));
				sdInsured.setRecognizeeKey(sdor.getOrderSn() + "_1");
				sdInsured.setOrderSn(sdor.getOrderSn());
				mSDInformationRiskTypeSet.add(informationRiskType);
				if (sdinformationpropertyList != null && sdinformationpropertyList.size() == 1) {
					SDInformationProperty sdp = sdinformationpropertyList.get(0);
					sdp.setRecognizeeSn(recognizeeSn);
					sdp.setSdinformationinsured(sdInsured);
					mSDInformationPropertySet.add(sdp);
					sdInsured.setSdinformationproperty(sdp);
					if (Constant.JFSC_CHANNELSN.equals(sdor.getChannelsn())
							|| Constant.WJ_JFSC_CHANNELSN.equals(sdor.getChannelsn())
							|| Constant.WAP_JFSC_CHANNELSN.equals(sdor.getChannelsn())) {
						insPrem = insPrem + Double.parseDouble(sdInsured.getRecognizeePrem());
					} else {
						insPrem = insPrem + Double.parseDouble(sdInsured.getDiscountPrice());
					}
					
					this.insTotalAmnt = new BigDecimal(insPrem);
				}
				this.orderItemOthSave(sdInsured, sdinf);
				tSDInformationInsuredList.add(sdInsured);
			}
		}

		return tSDInformationInsuredList;
	}

	/**
	 * excel 批量导入--保存订单
	 * 
	 * @param sdor
	 * @param sdinf
	 * @param sdapp
	 * @param cFlag
	 * @param crisktype
	 * @return
	 */
	public List<SDInformationInsured> excelInsuredSave(SDOrder sdor, SDInformation sdinf, SDInformationAppnt sdapp, Boolean cFlag, String crisktype) {
		List<SDInformationInsured> tSDInformationInsuredList = new ArrayList<SDInformationInsured>();
		double insPrem = 0.00;
		if ("rid_td".equals(this.mulInsuredFlag)) {

			Map<String, Object> insureInfoMap = new HashMap<String, Object>();
			insureInfoMap = getInsureInfoMap(sdinf, insureInfoMap);

			if (sdinsuredList.size() >= 1 && sdinsuredList != null) {
				int index = 1;
				for (SDInformationInsured sdinformationinsured : sdinsuredList) {

					if (sdinformationinsured != null && StringUtil.isNotEmpty(sdinformationinsured.getDiscountPrice())) {
						if (Double.parseDouble(sdinformationinsured.getDiscountPrice()) <= 0) {
							logger.error("被保人保费为空.数据不进行保存.ordersn==>{}", sdor.getOrderSn());
							continue;
						}

						QuestionPaper questionPaper = new QuestionPaper();
						// 被保人编号，规则需要修改
						String recognizeeSn = PubFun.GetSDInsuredSn();
						SDInformationRiskType sdinformationRiskTypes = new SDInformationRiskType();
						// sdinformationRiskTypes.setId("200001");// 险种，由接口获得
						// 订单号
						sdinformationRiskTypes.setOrderSn(sdor.getOrderSn()); 
						// 订单明细编号
						sdinformationRiskTypes.setInformationSn(sdinf.getInformationSn());
						// 被保人编号
						sdinformationRiskTypes.setRecognizeeSn(recognizeeSn);
						// 投保人编号
						sdinformationRiskTypes.setApplicantSn(sdapp.getApplicantSn());
						// 保单号，需要处理
						sdinformationRiskTypes.setPolicyNo("");
						// 险种编码
						sdinformationRiskTypes.setRiskCode(sdinf.getProductId());
						// 险种名称
						sdinformationRiskTypes.setRiskName(sdinf.getProductName());
						// 保额，需要处理
						sdinformationRiskTypes.setAmnt(String.valueOf(insureInfoMap.get(sdinf.getProductId() + "_" + sdinf.getPlanCode() + "_Amnt")));// 去缓存中的值
						// 份数，需要处理
						sdinformationRiskTypes.setMult("1");
						// 保费，需要处理
						sdinformationRiskTypes.setTimePrem(sdinformationinsured.getRecognizeePrem());
						sdinformationRiskTypes.setProductPrice(sdinformationinsured.getRecognizeeTotalPrem());
						// 生效日期
						sdinformationRiskTypes.setSvaliDate(sdinf.getStartDate());
						// 失效日期
						sdinformationRiskTypes.setEvaliDate(sdinf.getEndDate());
						// 以下字段待定
						// 缴费年期类型
						sdinformationRiskTypes.setPeriodFlag(sdinf.getChargeType());
						sdinformationRiskTypes.setPeriod(sdinf.getChargeYear());// 缴费年期
						sdinformationRiskTypes.setElectronicCout("");// 电子保单保险公司路径
						sdinformationRiskTypes.setElectronicPath("");// 电子保单物理路径
						sdinformationRiskTypes.setInsurerFlag("");
						sdinformationRiskTypes.setInsureMsg("");
						// sdinformationRiskTypes.setBalanceDate("");
						// sdinformationRiskTypes.setBalanceFlag("");
						// sdinformationRiskTypes.setBalanceMsg("");
						// sdinformationRiskTypes.setBalanceStatus("");
						sdinformationRiskTypes.setSdorder(sdor);
						sdinformationRiskTypes.setSdinformationinsured(sdinformationinsured);
						mSDInformationRiskTypeSet.add(sdinformationRiskTypes);

						// 单独提取出函数设置被保人为本人时的数据
						// sdinformationinsured =
						// this.setInsuredFromAppnt(sdinformationinsured,
						// sdapp);
						sdinformationinsured.setSdinformaitonrisktype(sdinformationRiskTypes);
						sdinformationinsured.setInformationSn(sdinf.getInformationSn());
						sdinformationinsured.setOrderSn(sdor.getOrderSn());
						sdinformationinsured.setRecognizeeSn(recognizeeSn);
						String recognizeeIdentityType = String.valueOf(insureInfoMap.get(sdinf.getProductId() + "_Identity_" + sdinformationinsured.getRecognizeeIdentityTypeName()));
						String recognizeeAppntRelation = String.valueOf(insureInfoMap.get(sdinf.getProductId() + "_Relationship_" + sdinformationinsured.getRecognizeeAppntRelationName()));

						String recognizeeSex = String.valueOf(insureInfoMap.get(sdinf.getProductId() + "_Sex_" + sdinformationinsured.getRecognizeeSexName()));
						sdinformationinsured.setRecognizeeSex(recognizeeSex);
						sdinformationinsured.setRecognizeeIdentityType(recognizeeIdentityType);
						sdinformationinsured.setRecognizeeAppntRelation(recognizeeAppntRelation);
						// 设置旅游目的地字段
						String destinationCountryText = "";
						if (StringUtil.isNotEmpty(sdinformationinsured.getDestinationCountry()) && !"1015".equals(sdinformation.getInsuranceCompany())) {
							destinationCountryText = this.dictionaryService.getNameByCodeTypePro(sdinf.getProductId(), sdinf.getInsuranceCompany(), "CountryCode",
									sdinformationinsured.getDestinationCountry());
							sdinformationinsured.setDestinationCountryText(destinationCountryText);
						} else {
							destinationCountryText = setDestinationCountry(sdinf.getProductId(), sdinformation.getInsuranceCompany(), sdinformationinsured, dictionaryService);
							if ("2007".equals(sdinf.getInsuranceCompany()) && DestinationCountry != null && !"".equals(DestinationCountry)) {
								if (DestinationCountry != null && !"".equals(DestinationCountry)) {
									String CountryText = this.orderConfigNewService.getCountryText2007("2007", DestinationCountry);
									sdinformationinsured.setDestinationCountry(this.DestinationCountry);
									sdinformationinsured.setDestinationCountryText(CountryText);
								}
							} else if ("1015".equals(sdinf.getInsuranceCompany()) && DestinationCountry != null && !"".equals(DestinationCountry)) {
								sdinformationinsured.setDestinationCountry(this.DestinationCountry);
								sdinformationinsured.setDestinationCountryText(this.orderConfigNewService.getCountryText1015("1015", DestinationCountry));
							} else if (("2011".equals(sdinf.getInsuranceCompany()) || "2023".equals(sdinf.getInsuranceCompany()) || "2071".equals(sdinf.getInsuranceCompany()))
									&& DestinationCountry != null && !"".equals(DestinationCountry)) {
								sdinformationinsured.setDestinationCountry(this.DestinationCountry);
								sdinformationinsured.setDestinationCountryText(this.orderConfigNewService.getSchengenCountryText(sdinf.getInsuranceCompany(), DestinationCountry,sdinf.getProductId()));
							} else {
								if (DestinationCountry != null && !"".equals(DestinationCountry)) {
									sdinformationinsured.setDestinationCountry(this.DestinationCountry);
									sdinformationinsured.setDestinationCountryText(destinationCountryText);
								}
							}
						}
						sdinformationinsured.setSdinformation(sdinf);
						// 被保人邮编
						if (StringUtil.isEmpty(sdinformationinsured.getRecognizeeZipCode()) && !"".equals(sdinformationinsured.getRecognizeeArea1())
								&& sdinformationinsured.getRecognizeeArea1() != null) {
							sdinformationinsured.setRecognizeeZipCode(this.getZipInfo(this.areaService.getAreaName(sdinformationinsured.getRecognizeeArea1()),
									this.areaService.getAreaName(sdinformationinsured.getRecognizeeArea2())));
						}
						sdinformationinsured.setInsuredSn(orderConfigNewService.getInsuredSn(sdor.getOrderSn(), index, sdinf.getInsuranceCompany()));
						sdinformationinsured.setRecognizeeKey(sdor.getOrderSn() + "_" + index);
						index = index + 1;
						int tInsuredAge = com.sinosoft.sms.messageinterface.pubfun.PubFun.getAge(sdinformationinsured.getRecognizeeBirthday());
						sdinformationinsured.setRecognizeeAge(String.valueOf(tInsuredAge));
						if (this.mulInsuredFlag != null && !"".equals(this.mulInsuredFlag)) {
							sdinformationinsured.setMulInsuredFlag(this.mulInsuredFlag);
						}
						
						if (Constant.JFSC_CHANNELSN.equals(sdor.getChannelsn())
								|| Constant.WJ_JFSC_CHANNELSN.equals(sdor.getChannelsn())
								|| Constant.WAP_JFSC_CHANNELSN.equals(sdor.getChannelsn())) {
							insPrem = insPrem + Double.parseDouble(sdinformationinsured.getRecognizeePrem());
						} else {
							insPrem = insPrem + Double.parseDouble(sdinformationinsured.getDiscountPrice());
						}
						
						sdinformationinsured.setRecognizeeOperate(this.recognizeeOperate);
						// 处理SDOrderItemOth信息
						this.orderItemOthSave(sdinformationinsured, sdinf);
						sdInformationInsuredElementsSave(sdinformationinsured);
						sdinformationinsured.setId("");
						if (cFlag) {
							sdinformationinsured.setQuestionPaper(questionPaper);
							questionPaper.setOrdID(sdor.getOrderSn());
							questionPaper.setEffective(com.sinosoft.sms.messageinterface.pubfun.PubFun.getCurrentDate());
							questionPaper.setSdinformationInsured(sdinformationinsured);
							questionList.add(questionPaper);
							// questionPaperService.save(questionPaper);
						}

						tSDInformationInsuredList.add(sdinformationinsured);

					}
				}
				this.insTotalAmnt = new BigDecimal(insPrem);
			}
		}

		return tSDInformationInsuredList;
	}

	// 缓存数据
	public Map<String, Object> getInsureInfoMap(SDInformation sdinf, Map<String, Object> insureInfoMap) {

		insureInfoMap = new HashMap<String, Object>();
		if (insureInfoMap != null && insureInfoMap.size() >= 1) {
			if (insureInfoMap.get(sdinf.getProductId() + "_" + sdinf.getPlanCode() + "_Amnt") == null) {
				String insuredAmnt = this.getInsuredAmnt(dutyFactor, sdinf.getPlanCode());
				insureInfoMap.put(sdinf.getProductId() + "_" + sdinf.getPlanCode() + "_Amnt", insuredAmnt);
			}
			// 证件类型
			if (insureInfoMap.get(sdinf.getProductId() + "_Identity") == null) {
				insureInfoMap.put(sdinf.getProductId() + "_Identity", "Y");// 表示已经存储到缓存中
				List<Dictionary> certlist = dictionaryService.findListByCom("certificate", sdinf.getInsuranceCompany(), sdinf.getProductId());
				for (Dictionary dc : certlist) {
					// insureInfoMap.put(sdinf.getProductId()+"_Identity_"+dc.getCodeValue(),
					// dc.getCodeName());
					insureInfoMap.put(sdinf.getProductId() + "_Identity_" + dc.getCodeName(), dc.getCodeValue());
				}
			}
			// 性别
			if (insureInfoMap.get(sdinf.getProductId() + "_Sex") == null) {
				insureInfoMap.put(sdinf.getProductId() + "_Sex", "Y");// 表示已经存储到缓存中
				List<Dictionary> setlist = dictionaryService.findListByCom("Sex", sdinf.getInsuranceCompany(), sdinf.getProductId());
				for (Dictionary ds : setlist) {
					// insureInfoMap.put(sdinf.getProductId()+"_Sex_"+ds.getCodeValue(),
					// ds.getCodeName());
					insureInfoMap.put(sdinf.getProductId() + "_Sex_" + ds.getCodeName(), ds.getCodeValue());
				}
			}
			// 与投保人关系
			if (insureInfoMap.get(sdinf.getProductId() + "_Relationship") == null) {
				insureInfoMap.put(sdinf.getProductId() + "_Relationship", "Y");// 表示已经存储到缓存中
				List<Dictionary> relationlist = dictionaryService.findListByCom("Relationship", sdinf.getInsuranceCompany(), sdinf.getProductId());
				for (Dictionary dr : relationlist) {
					// insureInfoMap.put(sdinf.getProductId()+"_Relationship_"+dr.getCodeValue(),
					// dr.getCodeName());
					insureInfoMap.put(sdinf.getProductId() + "_Relationship_" + dr.getCodeName(), dr.getCodeValue());
				}
			}
		} else {
			String insuredAmnt = this.getInsuredAmnt(dutyFactor, sdinf.getPlanCode());
			insureInfoMap.put(sdinf.getProductId() + "_" + sdinf.getPlanCode() + "_Amnt", insuredAmnt);
			// 证件类型
			if (insureInfoMap.get(sdinf.getProductId() + "_Identity") == null) {
				insureInfoMap.put(sdinf.getProductId() + "_Identity", "Y");// 表示已经存储到缓存中
				List<Dictionary> certlist = dictionaryService.findListByCom("certificate", sdinf.getInsuranceCompany(), sdinf.getProductId());
				for (Dictionary dc : certlist) {
					// insureInfoMap.put(sdinf.getProductId()+"_Identity_"+dc.getCodeValue(),
					// dc.getCodeName());
					insureInfoMap.put(sdinf.getProductId() + "_Identity_" + dc.getCodeName(), dc.getCodeValue());
				}
			}
			// 性别
			if (insureInfoMap.get(sdinf.getProductId() + "_Sex") == null) {
				insureInfoMap.put(sdinf.getProductId() + "_Sex", "Y");// 表示已经存储到缓存中
				List<Dictionary> setlist = dictionaryService.findListByCom("Sex", sdinf.getInsuranceCompany(), sdinf.getProductId());
				for (Dictionary ds : setlist) {
					// insureInfoMap.put(sdinf.getProductId()+"_Sex_"+ds.getCodeValue(),
					// ds.getCodeName());
					insureInfoMap.put(sdinf.getProductId() + "_Sex_" + ds.getCodeName(), ds.getCodeValue());
				}
			}
			// 与投保人关系
			if (insureInfoMap.get(sdinf.getProductId() + "_Relationship") == null) {
				insureInfoMap.put(sdinf.getProductId() + "_Relationship", "Y");// 表示已经存储到缓存中
				List<Dictionary> relationlist = dictionaryService.findListByCom("Relationship", sdinf.getInsuranceCompany(), sdinf.getProductId());
				for (Dictionary dr : relationlist) {
					// insureInfoMap.put(sdinf.getProductId()+"_Relationship_"+dr.getCodeValue(),
					// dr.getCodeName());
					insureInfoMap.put(sdinf.getProductId() + "_Relationship_" + dr.getCodeName(), dr.getCodeValue());
				}
			}
		}
		return insureInfoMap;
	}

	/**
	 * 购买流程改造后处理健康告知
	 * 
	 * @param informationInsureds2
	 *            被保人信息
	 * @param sdinf
	 *            订单详细信息
	 */
	private List<SDInsuredHealth> sdinsuredHealtySave(List<SDInformationInsured> tList, SDInformationInsured informationInsureds2, SDInformation sdinf, String order_healthySn) {
		SDInformationInsured cIns = null;
		if (tList != null && tList.size() >= 1) {
			for (SDInformationInsured t : tList) {
				if (t != null) {
					cIns = t;
				}
			}
		}
		if (cIns == null) {
			cIns = informationInsureds2;
		}
		// 已存有的健康告知信息

		List<SDInsuredHealth> tSDInsuredHealthList = this.sdinsuredHealthService.getInfoByOrderHealthySn(order_healthySn);
		if (tSDInsuredHealthList == null || tSDInsuredHealthList.size() <= 0) {
			tSDInsuredHealthList = this.sdinsuredHealthService.getInfoByOrderHealthySn(sdinf.getOrderSn());
		}
		List<SDInsuredHealth> newSDInsuredHealthList = new ArrayList<SDInsuredHealth>();
		// 急
		sdinsuredHealthList = this.orderConfigNewService.getInsuredHealthy(healthyInfoService, sdinsuredHealthService, sdinf);
		if (sdinsuredHealthList != null && sdinsuredHealthList.size() > 0) {
			for (SDInsuredHealth ih : sdinsuredHealthList) {

				for (SDInsuredHealth in : tSDInsuredHealthList) {
					if (in.getHealthyInfoId().equals(ih.getHealthyInfoId()) || "#".equals(ih.getShowInfoType())) {
						ih.setSelectFlag(in.getSelectFlag());
						ih.setTypeShowOrder(in.getTypeShowOrder());
						ih.setSdinformationinsured(cIns);
						ih.setOrderSn(sdinf.getOrderSn());
						ih.setInformationSn(sdinf.getInformationSn());
						ih.setRecognizeeSn(cIns.getRecognizeeSn());
						newSDInsuredHealthList.add(ih);
					}
				}
			}
		}
		order_healthySn = sdinf.getOrderSn();
		sdinsuredHealthList = tSDInsuredHealthList;
		return newSDInsuredHealthList;
	}

	/**
	 * 
	 * 保存頂底-處理责任
	 */
	private List<SDInformationDuty> informationInsuredDutySave(SDInformation inf, List<SDInformationDutyTemp> dutyTempList) {
		// dutyFactor = dutyFactor;
		String tShowDutyJsonArrary = this.getRequest().getParameter("dutyDisReq");
		try {
			tShowDutyJsonArrary = java.net.URLDecoder.decode(tShowDutyJsonArrary, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			logger.error(e1.getMessage(), e1);
		}
		JSONObject dutyJsonArray = null;
		JSONObject showDutyJsonArray = null;
		JSONObject premJsonArray = null;
		try {
			dutyJsonArray = JSONObject.fromObject(java.net.URLDecoder.decode(dutyJson, "UTF-8"));
			showDutyJsonArray = JSONObject.fromObject(java.net.URLDecoder.decode(dutyDisReq, "UTF-8"));
			premJsonArray = JSONObject.fromObject(java.net.URLDecoder.decode(dutyPremReq, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		List<SDInformationDuty> tSDInformationDutyList = new ArrayList<SDInformationDuty>();
		if (dutyTempList != null && dutyTempList.size() > 0) {
			// 用责任编码作为key
			Map<String, OrderDutyFactor> dutyFactorMap = new HashMap<String, OrderDutyFactor>();
			if (dutyFactor != null && dutyFactor.size() > 0) {
				int size = dutyFactor.size();
				for (int i = 0; i < size; i++) {
					dutyFactorMap.put(dutyFactor.get(i).getDutyCode(), dutyFactor.get(i));
				}
				SDInformationDuty sdinformationduty;
				String dutyCode = "";
				for (int i = 0; i < dutyTempList.size(); i++) {
					dutyCode = dutyTempList.get(i).getDutySn();
					dutyTempList.get(i).setOrderSn(this.orderSn);
					sdinformationduty = new SDInformationDuty();
					sdinformationduty.setDutySn(dutyCode);// 责任编码
					sdinformationduty.setInformationSn(this.informationSn);// 订单明细表编码
					sdinformationduty.setOrderSn(this.orderSn);// 订单号
					sdinformationduty.setDutyName(dutyFactorMap.get(dutyCode).getDudtyFactorName());// 责任名称
					sdinformationduty.setDutyFullName(dutyFactorMap.get(dutyCode).getDutyFullName());// 责任全称
					sdinformationduty.setCoverage(dutyFactorMap.get(dutyCode).getDefine());
					sdinformationduty.setRiskCode(inf.getProductId());// 险种编码
					sdinformationduty.setDutyEnName(dutyFactorMap.get(dutyCode).getDutyEnName());
					sdinformationduty.setEnCoverage(dutyFactorMap.get(dutyCode).getEnCoverage());
					sdinformationduty.setOrderFlag(dutyFactorMap.get(dutyCode).getOrderFlag());
					List<FEMDutyAmntPremList> fdAmntPremList = dutyFactorMap.get(dutyCode).getFdAmntPremList();
					for (int j = 0; j < fdAmntPremList.size(); j++) {
						if (dutyTempList.get(i).getAmnt().equals(fdAmntPremList.get(j).getBackUp1())) {
							sdinformationduty.setShowAmnt(fdAmntPremList.get(j).getAmnt());
						} else {
							sdinformationduty.setShowAmnt("");
						}
					}
					sdinformationduty.setDiscountPrice(dutyTempList.get(i).getDiscountPrice());
					sdinformationduty.setPremium(dutyTempList.get(i).getPremium());
					if ("101401010".equals(inf.getProductId()) || "101401011".equals(inf.getProductId())) {
						sdinformationduty.setDiscountRates(inf.getDiscountRates());
					}
					sdinformationduty.setAmt(dutyTempList.get(i).getAmnt());// 计算值保额
					sdinformationduty.setMainRiskFlag(dutyFactorMap.get(dutyCode).getCurrency());// 主险标志
					sdinformationduty.setSupplierDutyCode(dutyFactorMap.get(dutyCode).getSupplierDutyCode());// 保险公司责任/险别编码
					sdinformationduty.setSdinformation(inf);// 级联保存订单明细表信息
					sdinformationduty.setIsDisplay(dutyTempList.get(i).getRemark2());
					
					tSDInformationDutyList.add(sdinformationduty);
				}
			}
		} else {
			if (dutyFactor != null) {
				for (int i = 0; i < dutyFactor.size(); i++) {
					Object factorValueTemp = dutyJsonArray.get(dutyFactor.get(i).getDutyCode());
					// zhangjinquan 11180 保额为空时不保存该责任 2012-11-30
					if ((StringUtil.isEmpty((String) factorValueTemp)) || "nvalue".equals(factorValueTemp)) {
						continue;
					}
					Object showDutyValue = showDutyJsonArray.get(dutyFactor.get(i).getDutyCode());
					Object premDutyValue = premJsonArray.get(dutyFactor.get(i).getDutyCode());
					SDInformationDuty sdinformationduty = new SDInformationDuty();

					sdinformationduty.setDutySn(dutyFactor.get(i).getDutyCode());// 责任编码
					sdinformationduty.setInformationSn(this.informationSn);// 订单明细表编码
					sdinformationduty.setOrderSn(this.orderSn);// 保单号
					sdinformationduty.setDutyName(dutyFactor.get(i).getDudtyFactorName());// 责任名称
					sdinformationduty.setDutyFullName(dutyFactor.get(i).getDutyFullName());// 责任全称
					sdinformationduty.setCoverage(dutyFactor.get(i).getDefine());
					sdinformationduty.setRiskCode("");// 险种编码
					sdinformationduty.setDutyEnName(dutyFactor.get(i).getDutyEnName());
					sdinformationduty.setEnCoverage(dutyFactor.get(i).getEnCoverage());
					sdinformationduty.setOrderFlag(dutyFactor.get(i).getOrderFlag());
					if (!"nvalue".equals(showDutyValue)) {
						sdinformationduty.setShowAmnt(String.valueOf(showDutyValue + ""));
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
					sdinformationduty.setSdinformation(inf);// 级联保存订单明细表信息

					tSDInformationDutyList.add(sdinformationduty);
				}
			}
		}

		return tSDInformationDutyList;
	}
	
	/**
	 * 处理银行卡信息
	 * @return
	 */
	private List<DirectPayBankInfo> directPayBankInfoSave(String InsuranceCompany) {
		List<DirectPayBankInfo> tDirectPayBankInfoList = new ArrayList<DirectPayBankInfo>();
		if("N".equals(directPayBankInfo.getProp5())){
			return tDirectPayBankInfoList;
		}
		if(StringUtil.isEmpty(InsuranceCompany)){
			InsuranceCompany = sdinformation.getInsuranceCompany();
		}
		if (StringUtil.isNotEmpty(directPayBankInfo.getBankCode()) || StringUtil.isNotEmpty(directPayBankInfo.getBankNo())) {
			DirectPayBankInfo tDirectPayBankInfo = new DirectPayBankInfo();
			tDirectPayBankInfo.setOrderSn(orderSn);
			tDirectPayBankInfo.setPaySn(paySn);
			tDirectPayBankInfo.setBankCode(directPayBankInfo.getBankCode());
			tDirectPayBankInfo.setBankNo(directPayBankInfo.getBankNo());
			tDirectPayBankInfo.setBankUserName(sdinformationAppnt.getApplicantName());
			tDirectPayBankInfo.setBankProvince(directPayBankInfo.getBankProvince());
			tDirectPayBankInfo.setBankCity(directPayBankInfo.getBankCity());
			tDirectPayBankInfo.setProp5(directPayBankInfo.getProp5());
			bankList = dictionaryService.findListByCom("Bank", InsuranceCompany);
			for (Dictionary bank : bankList) {
				if (directPayBankInfo.getBankCode().equals(bank.getCodeValue())) {
					tDirectPayBankInfo.setBankName(bank.getCodeName());
					break;
				}
			}
			tDirectPayBankInfoList.add(tDirectPayBankInfo);
		}
		return tDirectPayBankInfoList;
	}
	
	/**
	 * 更新银行卡信息
	 * @return
	 */
	private DirectPayBankInfo directPayBankInfoUpdate(DirectPayBankInfo bankInfo, SDInformationAppnt appnt, SDInformation sdInf) {
		bankInfo.setProp5(directPayBankInfo.getProp5());
		bankInfo.setBankCode(directPayBankInfo.getBankCode());
		bankInfo.setBankNo(directPayBankInfo.getBankNo());
		bankInfo.setBankUserName(sdinformationAppnt.getApplicantName());
		bankInfo.setBankProvince(directPayBankInfo.getBankProvince());
		bankList = dictionaryService.findListByCom("Bank", sdInf.getInsuranceCompany());
		for (Dictionary bank : bankList) {
			if (directPayBankInfo.getBankCode().equals(bank.getCodeValue())) {
				bankInfo.setBankName(bank.getCodeName());
				break;
			}
		}
		return bankInfo;
	}
	/**
	 * 复杂险更新责任信息
	 */
	private List<SDInformationDuty> SDInformationDutyListUpdate(List<SDInformationDuty> sdDutys, List<SDInformationDutyTemp> dutyTempList ){
		if(null != sdDutys && null != dutyTempList && sdDutys.size()>0 && dutyTempList.size()>0){
			for(SDInformationDuty sdDuty: sdDutys){
				String dutySn = sdDuty.getDutySn();
				if(StringUtil.isEmpty(dutySn)){
					continue;
				}
				for(SDInformationDutyTemp dutyTemp : dutyTempList){
					String tempSn = dutyTemp.getDutySn();
					if(StringUtil.isEmpty(tempSn)){
						continue;
					}
					if(tempSn.equals(dutySn)){
						sdDuty.setPremium(dutyTemp.getPremium());
						sdDuty.setDiscountPrice(dutyTemp.getDiscountPrice());
						sdDuty.setAmt(dutyTemp.getAmnt());
						break;
					}
				}
			}
		}
		return sdDutys;
	}

	/**
	 * 保存訂單--處理產品投保要素
	 * 
	 * @param ins
	 */
	private void sdInformationInsuredElementsSave(SDInformationInsured ins) {
		// riskAppFactior = (List<OrderRiskAppFactor>)
		// getSession("riskAppFactior");
		JSONObject insureJsonJsonArray = null;
		try {
			insureJsonJsonArray = JSONObject.fromObject(java.net.URLDecoder.decode(insureJson, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		if (riskAppFactior != null) {
			for (int i = 0; i < riskAppFactior.size(); i++) {
				String appFactiorType = riskAppFactior.get(i).getFactorType().toString();

				if (!"TextAge".equals(appFactiorType) && riskAppFactior != null && riskAppFactior.get(i) != null && insureJsonJsonArray != null) {
					Object factorValueTemp = insureJsonJsonArray.get(riskAppFactior.get(i).getFactorType());

					informationInsuredElements = new SDInformationInsuredElements();
					informationInsuredElements.setElementsType(appFactiorType);
					informationInsuredElements.setElementsSn(PubFun.GetSDElementSn());
					informationInsuredElements.setOrderSn(this.sdorder.getOrderSn());
					informationInsuredElements.setRecognizeeSn(ins.getRecognizeeSn());
					informationInsuredElements.setSdinformationInsured(ins);
					informationInsuredElements.setInformationSn(this.sdinformation.getInformationSn());
					informationInsuredElements.setElementsSn(riskAppFactior.get(i).getAppFactorCode());
					if (factorValueTemp != null)
						informationInsuredElements.setElementsValue(factorValueTemp.toString());
				}
				if (informationInsuredElements != null) {
					informationInsuredElementsList.add(informationInsuredElements);
				}

			}
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

					for (int i = 0; i < orderRiskAppFactor.getFactorValue().size(); i++) {
						if (orderRiskAppFactor.getFactorValue().get(i).getFactorValue().equals(plan)) {
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
				// 保险责任多类人群列表
				if ("MulPeople".equals(orderRiskAppFactor.getFactorType())) {
					mulPeopleList.addAll(orderRiskAppFactor.getFactorValue());
				}
			}
		}
	}

	/**
	 * 根據保險公司進行頁面元素初始化
	 * 
	 * @param insuranceCompanySn
	 */
	private void init(String insuranceCompanySn) {
		//理财险查询
		isLcx = FinancingCheck.getLcx(productId);
		
		certificateList = dictionaryService.findListByCom("certificate", insuranceCompanySn,productId);
		relationList = dictionaryService.findListByCom("Relationship", insuranceCompanySn, productId);
		bnfRelationList = dictionaryService.findListByCom("BnfRelationship", insuranceCompanySn, productId);
		sexList = dictionaryService.findListByCom("Sex", insuranceCompanySn);
		bankList = dictionaryService.findListByCom("Bank", insuranceCompanySn);
		if ("2071".equals(insuranceCompanySn)) {
			excelcertificateList = dictionaryService.findListByCom("excel_certificate", "excel_2071");
		} else {
			excelcertificateList = dictionaryService.findListByCom("excel_certificate", "excel");
		}
		excelrelationList = dictionaryService.findListByCom("excel_Relationship", "excel");
		excelsexList = dictionaryService.findListByCom("excel_Sex", "excel");
		occupationList = occupationService.findSuperByCom(insuranceCompanySn, productId);// 查询一级职业
		areaList = areaService.findSuperByCom(productId, insuranceCompanySn);
		listCountryCode = dictionaryService.findListByCom("CountryCode", insuranceCompanySn, productId);
		mapCountryCode = this.getCountryMap();
		propertyToRecognizeeList = dictionaryService.findListByCom("propertyToRecognizee", insuranceCompanySn, productId);// 财产与被保人关系
		hourseTypeList = dictionaryService.findListByCom("hourseType", insuranceCompanySn, productId);// 房屋类型
		hourseAgeList = dictionaryService.findListByCom("hourseAge", insuranceCompanySn, productId);// 房龄
		// 处理国家按照首字母排序
		productConfigs = productConfigService.findPCByRiskCode(productId, insuranceCompanySn);// 根据产品编码查询配置项信息
		nationalityList = dictionaryService.findListByCom("nationality", insuranceCompanySn);// 查询国籍列表
		travelModelList = dictionaryService.findListByCom("travelMode", insuranceCompanySn);
		travelTypeList = dictionaryService.findListByCom("travelType", insuranceCompanySn);
		securityList = dictionaryService.findListByCom("socialSecurity", insuranceCompanySn);
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
		if (StringUtil.isNotEmpty(channelCode)) {
			jrhsURL = sdorderService.getJRHSURLByConfig(channelCode);
		}
		logger.info("地区==========={}", areaList.size());
	}

	/**
	 * 會員中心連接到訂單預覽頁
	 * 
	 * @return
	 */
	public String linkOrderDetails() {
		try {

			String nKID = StringUtil.md5Hex(PubFun.getKeyValue() + orderSn);
			if (!nKID.equals(KID)) {
				addActionError("查看订单请点击<a href=\"" + Config.getServerContext() + "/shop/member!newLogin.action\"><这里></a>！登录一下！");
				// addActionError("矮油，出了点小状况,点击<a href=\"javascript:openChat('http://wpa.b.qq.com/cgi/wpa.php?ln=1&key=XzgwMDExODA2Nl8yNzg4MDVfODAwMTE4MDY2XzJf');\"><这里></a>！请客服MM帮帮忙吧！");
				return ERROR;
			}
			sdorder = sdorderService.getOrderByOrderSn(orderSn);
			// 针对淘宝订单数据，数据不完整处理---sdorder_id为空
			ParseXMLToObject pxml = new ParseXMLToObject();
			if (!pxml.dealDataId(orderSn)) {
				addActionError("亲，这个订单不存在了②，请重新提交或者请联系客服MM!");
				logger.error("会员中心-查了订单信息，未查询到相关信息，订单号为空");
				return ERROR;
			}

			showInsuredInfomation(sdorder.getOrderSn());
			if (sdorder == null || sdinformation == null) {
				addActionError("亲，这个订单不存在了③，请重新提交或者请联系客服MM!");
				logger.error("会员中心-查了订单信息，未查询到相关信息，订单数据为空");
				return ERROR;
			}

			/*
			// 已支付或已撤销或部分撤销的订单 重置payPrice支付金额为打折后金额 用于预览页显示
			if (SDOrderStatus.paid.equals(sdorder.getOrderStatus()) || SDOrderStatus.cancel.equals(sdorder.getOrderStatus()) || SDOrderStatus.partcancel.equals(sdorder.getOrderStatus())) {
				// 参加了活动的情况，判断活动是否为打折活动
				if (StringUtil.isNotEmpty(sdorder.getActivitySn())) {
					QueryBuilder qb = new QueryBuilder("select count(1) from sdcouponactivityinfo where type='6' and activitysn=?", sdorder.getActivitySn());
					// 打折活动的情况 订单原价-订单活动金额就是折后金额
					if (qb.executeInt() > 0) {
						BigDecimal orderActity = new BigDecimal(0);
						if (StringUtil.isNotEmpty(sdorder.getOrderActivity())) {
							orderActity = new BigDecimal(sdorder.getOrderActivity());
						}
						sdorder.setPayPrice(sdorder.getTotalAmount().subtract(orderActity).toString());
						
					} else {
						// 不是打折活动 显示原价
						sdorder.setPayPrice(sdorder.getTotalAmount().toString());
					}
				} else {
					// 未参加活动 显示原价
					sdorder.setPayPrice(sdorder.getTotalAmount().toString());
				}
			}
			*/
			productId = sdinformation.getProductId();

			if (StringUtil.isNotEmpty(sdorder.getDiscountRates())) {
				activityFlag = "1";
			} else {
				if (sdorder.getTotalAmount().compareTo(new BigDecimal(sdorder.getPayPrice())) > 0) {
					activityFlag = "1";
				}
			}
		} catch (Exception e) {
			addActionError("linkOrderDetails-连接错误，orderSn，" + orderSn);
			return ERROR;
		}
		this.setCanPreviewOperatorFlag("0");
		// 没看出 payresult 和这个 result 有啥区别？ 对比代码发现payresult这个没有result新，因此注释掉
		// if (sdorder.getOrderStatus() == SDOrderStatus.paid) {
		// return "payresult";
		// }
		return "result";
	}

	/**
	 * 产品试算方法--页面调用
	 */
	public String ajaxCounter() {
		Map<String, Object> tData = new HashMap<String, Object>();
		Map<String, Object> paramter1 = new HashMap<String, Object>();
		String[] BaseInformation = new String[17];
		if (StringUtil.isEmpty(productId)) {
			productId = (String) getSession("productId");
		}
		BaseInformation = (String[]) getSession(productId + "baseInformation");
		riskAppFactior = (List<OrderRiskAppFactor>) getSession(productId + "riskAppFactior");
		dutyFactor = (List<OrderDutyFactor>) getSession(productId + "dutyFactor");
		if (BaseInformation == null || BaseInformation.length <= 0 || riskAppFactior == null || riskAppFactior.size() <= 0 || dutyFactor == null || dutyFactor.size() <= 0) {
			try {
				paramter1 = sdorderService.getProductInformation(productId, "N", channelCode);// 产品ID
			} catch (Exception e) {
				tData.put("Flag", "Err");// 总保费
				tData.put("Msg", "与产品中心交互失败⑤！");// 错误信息
				logger.error("保费试算时，与产品中心交互失败，productId，" + productId + ",失败原因：" + e.getMessage(), e);
				JSONObject jsonObject = JSONObject.fromObject(tData);
				return ajax(jsonObject.toString(), "text/html");
			}
			BaseInformation = (String[]) paramter1.get("baseInformation");// 产品基础数据
			// riskType = BaseInformation[7].toString();// 产品小类
			riskAppFactior = (List<OrderRiskAppFactor>) paramter1.get("riskAppFactor");
			dutyFactor = (List<OrderDutyFactor>) paramter1.get("dutyFactor");
		}
		riskType = BaseInformation[7].toString();// 产品小类

		// 投保要素
		try {
			insureJson = java.net.URLDecoder.decode(insureJson, "utf-8");
		} catch (Exception e) {
			logger.error("投保要素insureJson解析错误。" + e.getMessage(), e);
		}
		JSONObject insureJsonJsonArray = JSONObject.fromObject(insureJson);

		// 被保人生日，性别
		try {
			recoJson = java.net.URLDecoder.decode(recoJson, "utf-8");
		} catch (Exception e) {
			logger.error("被保人生日，性别recoJson解析错误。" + e.getMessage(), e);
		}
		JSONObject recognizeeInfoArray = null;
		int tLength = 1;
		if (StringUtil.isNotEmpty(recoJson)) {
			recognizeeInfoArray = JSONObject.fromObject(recoJson);
			tLength = recognizeeInfoArray.size();
		}

		Double totlePrem = 0.0;// 总保费
		Double countPrice = 0.0;// 折扣后保费
		Double DiscountRate = 0.0;// 折扣率
		//解决8.39999999999问题
		BigDecimal countPrice_b=new BigDecimal(Double.toString(countPrice));
		// 返回前台数据
		Map<String, Object> price = new HashMap<String, Object>();
		List<String> returnList = new ArrayList<String>();
        // 因为recognizeeInfoArray是json，key是从“1”开始，所有下边的循环“k”是从1开始的
		for (int k = 1; k <= tLength; k++) {
			List<OrderDutyFactor> dutyFactorLast = new ArrayList<OrderDutyFactor>();
			String tRecognizeeId = "";
			// 根据购买份数处理同一个被保人信息需要循环的次数
			String strCount = "";
			String IdentityType = recognizeeInfoArray.get("" + k + "").toString().split(",")[4];
			String thisBirthday = recognizeeInfoArray.get("" + k + "").toString().split(",")[1];
			if(recognizeeInfoArray.get("" + k + "").toString().split(",").length>=6  ){				
				if("0".equals(IdentityType)||"01".equals(IdentityType)||"身份证".equals(IdentityType)){
					thisBirthday = com.sinosoft.lis.pubfun.PubFun.getBirthdayFromId(recognizeeInfoArray.get("" + k + "").toString().split(",")[5]);
				}
			}
			if (recognizeeInfoArray != null && recognizeeInfoArray.size() > 0) {
				tRecognizeeId = recognizeeInfoArray.get("" + k + "").toString().split(",")[0];
				if (recognizeeInfoArray.get("" + k + "").toString().split(",")[3] != null && !"".equals(recognizeeInfoArray.get("" + k + "").toString().split(",")[3])) {
					strCount = recognizeeInfoArray.get("" + k + "").toString().split(",")[3];
				}
			}
			int intCount = 1;
			if (strCount != null && !"".equals(strCount)) {
				intCount = Integer.parseInt(strCount);
			}
			// 循环该产品的投保要素
            List<FEMRiskFactorList> factorValue = null;
            FEMRiskFactorList riskFactor = null;
			Object factorValueTemp;
			for (int i = 0; i < riskAppFactior.size(); i++) {
                factorValue = new ArrayList<FEMRiskFactorList>();
                riskFactor = new FEMRiskFactorList();
				if ("TextAge".equals(riskAppFactior.get(i).getFactorType())) {
				    // 详细页的出生日期
					String initAge = insureJsonJsonArray.get("TextAge").toString();
                    // 被保人的出生日期
					factorValueTemp = thisBirthday;
					if (StringUtil.isEmpty(factorValueTemp)) {
						FEMRiskFactorList femr = riskAppFactior.get(i).getFactorValue().get(0);
                        if (initAge != null && !"".equals(initAge)) {
                            riskFactor.setFactorValue(initAge);
                        } else if (femr != null && femr.getFactorValue() != null) {
                            riskFactor.setFactorValue(orderConfigNewService.getBrithdayByFactor(getRequest().getParameter("effective"), femr.getFactorValue()));
                        } else {
                            riskFactor.setFactorValue("1991-01-01");
                        }
					} else {
                        riskFactor.setFactorValue(factorValueTemp.toString());
					}
				} else {
					factorValueTemp = insureJsonJsonArray.get(riskAppFactior.get(i).getFactorType());
					if (factorValueTemp != null) {
						riskFactor.setFactorValue(factorValueTemp.toString());
                    }
				}
                riskFactor.setAppFactorCode(riskAppFactior.get(i).getAppFactorCode());
                riskFactor.setFactorType(riskAppFactior.get(i).getFactorType());
                riskFactor.setIsPremCalFacotor(riskAppFactior.get(i).getIsPremCalFacotor());
                factorValue.add(riskFactor);
                riskAppFactior.get(i).setFactorValue(factorValue);
			}
			// 复杂产品处理
			if ("Y".equals(complicatedFlag)) {
				if (StringUtil.isNotEmpty(dutyTempSerials)) {
					String sql;
					// 如果产品是年金险，责任后边跟的是保费，不是保额，所以要获取保费
					int count = new QueryBuilder("SELECT COUNT(*) FROM zdcode WHERE codetype = 'yearCash.riskCode' AND FIND_IN_SET('"+ productId +"', CodeValue);").executeInt();
					if (count > 0) {
						// 根据责任序列号，从临时表中获取责任编码，保费计算值
						sql = "select DutySn,Premium from sdinformationdutytemp where DutySerials = ? and (Remark2 is null or Remark2 = '' or Remark2 != 'N')";
					} else {
						// 根据责任序列号，从临时表中获取责任编码，保额计算值
						sql = "select DutySn,Amnt from sdinformationdutytemp where DutySerials = ? and (Remark2 is null or Remark2 = '' or Remark2 != 'N')";
					}

					DataTable dt = new QueryBuilder(sql, dutyTempSerials).executeDataTable();
					if (dt != null && dt.getRowCount() > 0) {
						Map<String, String> dutyMap = new HashMap<String, String>();
						int rowCount = dt.getRowCount();
						for (int i = 0; i < rowCount; i++) {
							dutyMap.put(dt.getString(i, 0), dt.getString(i, 1));
						}

						for (OrderDutyFactor orderDutyFactor : dutyFactor) {
							String dutyValueTemp = dutyMap.get(orderDutyFactor.getDutyCode());
							List<FEMDutyAmntPremList> fdAmntPremList = new ArrayList<FEMDutyAmntPremList>();
							if (StringUtil.isNotEmpty(dutyValueTemp)) {
								for (FEMDutyAmntPremList femd : orderDutyFactor.getFdAmntPremList()) {
									if (dutyValueTemp.equals(femd.getBackUp1())) {
										fdAmntPremList.add(femd);
									}
								}
								orderDutyFactor.setFdAmntPremList(fdAmntPremList);
								dutyFactorLast.add(orderDutyFactor);
							}
						}
					}
				}
			} else {
				try {
					dutyJson = java.net.URLDecoder.decode(dutyJson, "utf-8");

				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
				JSONObject dutyJsonJsonArray = JSONObject.fromObject(dutyJson);

				for (OrderDutyFactor orderDutyFactor : dutyFactor) {
					String dutyValueTemp = dutyJsonJsonArray.get(orderDutyFactor.getDutyCode()).toString();
					if (orderDutyFactor.getFdAmntPremList() != null) {
						// modify by cuishig 20130625,责任不清除
						// orderDutyFactor.getFdAmntPremList().clear();
						// orderDutyFactor.
					}
					List<FEMDutyAmntPremList> fdAmntPremList = new ArrayList<FEMDutyAmntPremList>();
					if ("nvalue".equals(dutyValueTemp)) {
						// System.out.println("dutyValueTemp=" + dutyValueTemp);
					} else {
						for (FEMDutyAmntPremList femd : orderDutyFactor.getFdAmntPremList()) {
							if (dutyValueTemp.equals(femd.getBackUp1())) {
								fdAmntPremList.add(femd);
							}
						}
						orderDutyFactor.setFdAmntPremList(fdAmntPremList);
						dutyFactorLast.add(orderDutyFactor);
					}
				}
			}
			if (dutyFactorLast.size() == 0) {
				dutyFactorLast = null;
			}
			Map<String, Object> paramter = new HashMap<String, Object>();
			paramter.put("baseInformation", BaseInformation);
			paramter.put("riskAppFactor", riskAppFactior);
			paramter.put("dutyFactor", dutyFactorLast);
			paramter.put("complicatedFlag", complicatedFlag);
			// 保费试算根据保险公司判断是起保日期还是当前日期 heyang 2013-7-19
			String startdate = getRequest().getParameter("effective");
			String specialPremCalFlag =new QueryBuilder("SELECT CodeValue FROM zdcode WHERE "
					+ "CodeType='StartDateCompanyCode' and CodeValue=?", productId.substring(0, 4)).executeString();//特殊险保费计算标识
			if (specialPremCalFlag != null){
				startdate = PubFun.getCurrentDate();
			}
			paramter.put("effective", startdate);
			Map<String, Object> mapPrem = null;
			try {
				mapPrem = sdorderService.getProductPremDutyAmounts(paramter);
			} catch (Exception e) {
				logger.error("保费试算时，调用保费试算接口异常：" + e.getMessage(), e);
			}
			String memberId = "";
			Member member = getLoginMember();
			if (member != null) {
				memberId = member.getId();
			}
			String orderSnTem = getRequest().getParameter("orderSnTem");
			if (StringUtil.isEmpty(memberId) && StringUtil.isNotEmpty(orderSnTem)) {
				memberId = new QueryBuilder("select memberId from sdorders where ordersn=?", orderSnTem).executeString();
			}

			// 设置一个逻辑判断开关，用于判断是否是年金险，保费与保额对调
			boolean pramAndAmntFlag = false;
			// 年金险，保费与保额对调后的保费
			String prem = null;
			String amnt = null;
			int count = new QueryBuilder("SELECT COUNT(*) FROM zdcode WHERE codetype = 'yearCash.riskCode' AND FIND_IN_SET('"+ productId +"', CodeValue);").executeInt();
			if (count > 0) {
				pramAndAmntFlag = true;
				String sql = "select Premium from sdinformationdutytemp where DutySerials = ? order by Premium desc";
				// 保费
				prem = new QueryBuilder(sql, dutyTempSerials).executeString();
				// 获取试算后的保额
				amnt = mapPrem.get("totlePrem").toString();

				// 修改返回对象map中的保费
				mapPrem.put("countPrice", prem);
				mapPrem.put("totlePrem", prem);

				// 修改返回对象map中的dutyAmounts
				DutyAmount[] tDutyAmountlist = (DutyAmount[])mapPrem.get("dutyAmounts");
				if (tDutyAmountlist != null) {
					for (DutyAmount t : tDutyAmountlist) {
						t.setPrem(prem);
						t.setDiscountPrice(prem);
					}
				}
			}

			if (mapPrem.containsKey("errMessage") && StringUtil.isNotEmpty((String)mapPrem.get("errMessage"))) {
				price.put("errMessage", mapPrem.get("errMessage"));
				price.put("retTotlePrem", "0");// 总保费
				price.put("retPrem", "0");// 折前保费
				price.put("retCountPrem", "0");// 折扣后保费
			    price.put("discountRate", "0");// 折扣率
				JSONObject jsonObject = JSONObject.fromObject(price);
				return ajax(jsonObject.toString(), "text/html");
			} else {
				if (mapPrem.containsKey("warnMessage") && StringUtil.isNotEmpty((String)mapPrem.get("warnMessage"))) {
					price.put("warnMessage", mapPrem.get("warnMessage"));
				}
				String retCountPrem = String.valueOf(mapPrem.get("countPrice").toString());
				setSession("TotelPrem_" + productId, retCountPrem);
				if (StringUtil.isEmpty(channelsn)) {
					if (StringUtil.isNotEmpty(sdorder.getChannelsn())) {
						channelsn = sdorder.getChannelsn();
					} else if ("1".equals(pointExchangeFlag)) {
						channelsn = Constant.WJ_JFSC_CHANNELSN;
					} else {
						channelsn = "wj";
					}
				}
				totlePrem = totlePrem + Double.parseDouble(mapPrem.get("totlePrem").toString()) * Double.parseDouble(String.valueOf(intCount));// 总保费
				// 把每个被保人的保费传到前台显示
				returnList.add(tRecognizeeId + "-" + Double.parseDouble(mapPrem.get("countPrice").toString()) + "-" + Double.parseDouble(mapPrem.get("totlePrem").toString()) + "-"
						+ ActivityCalculate.ProductCalculate(productId,"", mapPrem.get("countPrice").toString(), channelsn, memberId));
				countPrice_b = countPrice_b.add(new BigDecimal(mapPrem.get("countPrice").toString()).multiply(new BigDecimal(String.valueOf(intCount))));// 折扣后保费
				countPrice = countPrice_b.doubleValue();
				// dutyAmounts = dutyAmounts +
				// Double.parseDouble(mapPrem.get("dutyAmounts").toString());// 责任明细
				price.put("retDutyAmounts", mapPrem.get("dutyAmounts"));// 责任明细
				if (mapPrem.get("DiscountRate").toString() == null || "".equals(mapPrem.get("DiscountRate").toString())) {
					DiscountRate = 0.0;
				} else {
					DiscountRate = Double.parseDouble(mapPrem.get("DiscountRate").toString());// 折扣率
				}
	
			}
			// }
			//更新责任临时表中的保额和保费 dutyTempSerials dutySn addby cuishigang
			if("Y".equals(this.complicatedFlag) && StringUtil.isNotEmpty(dutyTempSerials)){
				DutyAmount[] tDutyAmountlist = (DutyAmount[])mapPrem.get("dutyAmounts");
				if (tDutyAmountlist != null) {
					for(DutyAmount t:tDutyAmountlist){
						String tempdutycode = t.getDutyCode();
						List<SDInformationDutyTemp> dutytemplist = sdInformationDutyTempService.getDutyTemp(dutyTempSerials);
						for(SDInformationDutyTemp tduty:dutytemplist){
							if(tempdutycode.equals(tduty.getDutySn())){
								// 年金险,更新责任临时表中的保额
								if (pramAndAmntFlag) {
									tduty.setAmnt(amnt);
								} else {
									tduty.setDiscountPrice(t.getDiscountPrice());
									tduty.setPremium(t.getPrem());
								}
								sdInformationDutyTempService.update(tduty);
							}
						}
					}
				}
			}
			if (orderSn != null && !"".equals(orderSn)) {
				sdorder = sdorderService.getOrderByOrderSn(orderSn);
				mulInsuredFlag = sdorder.getSdinformationSet().iterator().next().getSdinformationinsuredSet().iterator().next().getMulInsuredFlag();
			}
			
			if ("rid_td".equals(this.mulInsuredFlag) && this.sdorder.getTotalAmount() != new BigDecimal(0) && this.sdorder.getProductTotalPrice() != new BigDecimal(0)) {
				price.put("retTotlePrem", sdorder.getProductTotalPrice());// 总保费
				price.put("retPrem", sdorder.getTotalAmount());// 折前保费
				price.put("retCountPrem", ActivityCalculate.ProductCalculate(productId, orderSn,String.valueOf(sdorder.getTotalAmount()), channelsn, memberId));// 折扣后保费
				price.put("insFlag", "rid_td");// excel导入标志
	
			} else {
				price.put("retTotlePrem", totlePrem);// 总保费
				price.put("retPrem", countPrice);// 折前保费
				price.put("retCountPrem", ActivityCalculate.ProductCalculate(productId, "",String.valueOf(countPrice), channelsn, memberId));// 折扣后保费
			}
			price.put("returnList", returnList);// 被保人分项保费
			sdorder.setPayPrice(String.valueOf(price.get("retCountPrem")));
			if (orderSn != null && !"".equals(orderSn)) {
				//解决异步更新
				sdorderService.update(sdorder);
			}	
			// price.put("retDutyAmounts", dutyAmounts);// 责任明细
			price.put("discountRate", DiscountRate);// 折扣率
		}
		
		JSONObject jsonObject = JSONObject.fromObject(price);
		return ajax(jsonObject.toString(), "text/html");
	}
	
	/**
	 * 产品试算方法--页面调用
	 */
	public String ajaxAlreadySave() {
		QueryBuilder qb = new QueryBuilder("SELECT a.createDate, DATE_FORMAT(a.modifydate, '%Y-%m-%d') as modifydate,a.ordersn,case when b.startdate<=now() then '0' else '1' END isoverdue FROM sdorders a,sdinformation b WHERE a.ordersn = b.ordersn AND a.memberid =? "
				+ " AND a.orderstatus ='4' AND b.productid =? ORDER BY a.createdate DESC LIMIT 1");
		qb.add(memberid);
		qb.add(productId);
		DataTable dt = qb.executeDataTable();
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(dt.getRowCount()>0){
			// 获取产品更新标识
			String tempSql = "SELECT IsUpdateFlag, IsUpdateDate FROM sdproduct WHERE productid = ?";
			DataTable tempDt = new QueryBuilder(tempSql, productId).executeDataTable();
			if (tempDt != null && tempDt.getRowCount() > 0) {
				String isUpdateFlag = tempDt.getString(0, "IsUpdateFlag");
				String isUpdateDate = tempDt.getString(0, "IsUpdateDate");
				String orderCreateDate = dt.getString(0, "createDate");
				// 如果产品中心设置了更新标识为“Y”, 并且更新时间大于下单时间，则不用弹出继续上次录入的提示
				if ("Y".equals(isUpdateFlag) && DateUtil.compare(isUpdateDate, orderCreateDate, DateUtil.Format_DateTime) >= 0) {
					map.put("status", "0");
				} else {
					map.put("date", dt.getString(0, "modifydate"));
					String kid = StringUtil.md5Hex(PubFun.getKeyValue() + dt.getString(0, "ordersn"));
					String fillUrl = "/shop/order_config_new!keepInput.action?orderSn="+dt.getString(0, "ordersn")+"&KID="+kid;
					map.put("fillUrl", fillUrl);
					map.put("status", "1");
					map.put("isoverdue", dt.getString(0, "isoverdue"));
				}
			}
		}else{
			map.put("status", "0");
		}
		 
		JSONObject jsonObject = JSONObject.fromObject(map);
		return ajax(jsonObject.toString(), "text/html");
	}
	

	/**
	 * 产品保费重算方法---非导入类
	 */
	public String relCalPrem(String[] a, List<OrderRiskAppFactor> b, List<OrderDutyFactor> c, SDInformation sdi) {
		// String [] factorValue =new String [1];
		String[] baseInformations = a;
		List<OrderRiskAppFactor> riskAppFactior = b;
		List<OrderDutyFactor> dutyFactor = c;

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

		try {
			recoJson = java.net.URLDecoder.decode(recoJson, "utf-8");

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		JSONObject recognizeeInfoArray = null;
		int tLength = 1;
		if (StringUtil.isNotEmpty(recoJson)) {
			recognizeeInfoArray = JSONObject.fromObject(recoJson);
			tLength = recognizeeInfoArray.size();
		}
		Double totlePrem = 0.0;// 总保费
		Double countPrice = 0.0;// 折扣后保费
		Double DiscountRate = 0.0;// 折扣率
		// 返回前台数据
		Map<String, Object> price = new HashMap<String, Object>();
		List<String> returnList = new ArrayList<String>();
		List<OrderDutyFactor> dutyFactorLast = null;
		for (int k = 1; k <= tLength; k++) {
			dutyFactorLast = new ArrayList<OrderDutyFactor>();
			String tRecognizeeId = "";
			// 根据购买份数处理同一个被保人信息需要循环的次数
			String strCount = "";
			if (recognizeeInfoArray != null && recognizeeInfoArray.size() > 0) {
				tRecognizeeId = recognizeeInfoArray.get("" + k + "").toString().split(",")[0];
				if (recognizeeInfoArray.get("" + k + "").toString().split(",")[3] != null && !"".equals(recognizeeInfoArray.get("" + k + "").toString().split(",")[3])) {
					strCount = recognizeeInfoArray.get("" + k + "").toString().split(",")[3];
				}
			}
			int intCount = 1;
			if (strCount != null && !"".equals(strCount)) {
				intCount = Integer.parseInt(strCount);
			}
			// for(int ii=1;ii<=intCount;ii++){
			for (int i = 0; i < riskAppFactior.size(); i++) {
				// OrderRiskAppFactor orderRiskAppFactor =
				// riskAppFactior.get(i);
				if ("TextAge".equals(riskAppFactior.get(i).getFactorType())) {
					String textage = insureJsonJsonArray.get("TextAge").toString();
					textage = recognizeeInfoArray.get("" + k + "").toString().split(",")[1];
					Object factorValueTemp = insureJsonJsonArray.get(riskAppFactior.get(i).getFactorType());
					factorValueTemp = recognizeeInfoArray.get("" + k + "").toString().split(",")[1];
					if ("".equals(factorValueTemp) || factorValueTemp == null) {
						FEMRiskFactorList femr = riskAppFactior.get(i).getFactorValue().get(0);
						List<FEMRiskFactorList> factorValue = new ArrayList<FEMRiskFactorList>();
						FEMRiskFactorList riskFactor = new FEMRiskFactorList();
						if ("".equals(textage)) {
							if (femr != null && femr.getFactorValue() != null) {
								riskFactor.setFactorValue(orderConfigNewService.getBrithdayByFactor(getRequest().getParameter("effective"), femr.getFactorValue()));
							} else {
								riskFactor.setFactorValue("1991-01-01");
							}
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
					
					if ("Period".equals(riskAppFactior.get(i).getFactorType())) {
						String tbperiod = com.sinosoft.cms.dataservice.Member.dealTBorder(sdi.getProductId(),sdi.getPlanCode(), sdi.getEnsure());
						if (StringUtil.isNotEmpty(tbperiod)) {
							riskFactor.setFactorValue(tbperiod);
						}
					}
					
					riskFactor.setAppFactorCode(riskAppFactior.get(i).getAppFactorCode());
					riskFactor.setFactorType(riskAppFactior.get(i).getFactorType());
					riskFactor.setIsPremCalFacotor(riskAppFactior.get(i).getIsPremCalFacotor());
					factorValue.add(riskFactor);
					riskAppFactior.get(i).setFactorValue(factorValue);
				}
			}
			// 复杂产品处理
			if ("Y".equals(complicatedFlag)) {
				if (StringUtil.isNotEmpty(dutyTempSerials)) {
					String sql;
					// 如果产品是年金险，责任后边跟的是保费，不是保额，所以要获取保费
					int count = new QueryBuilder("SELECT COUNT(*) FROM zdcode WHERE codetype = 'yearCash.riskCode' AND FIND_IN_SET('"+ productId +"', CodeValue);").executeInt();
					if (count > 0) {
						// 根据责任序列号，从临时表中获取责任编码，保费计算值
						sql = "select DutySn,Premium from sdinformationdutytemp where DutySerials = ? and (Remark2 is null or Remark2 = '' or Remark2 != 'N')";
					} else {
						// 根据责任序列号，从临时表中获取责任编码，保额计算值
						sql = "select DutySn,Amnt from sdinformationdutytemp where DutySerials = ? and (Remark2 is null or Remark2 = '' or Remark2 != 'N')";
					}
					DataTable dt = new QueryBuilder(sql, dutyTempSerials).executeDataTable();
					if (dt != null && dt.getRowCount() > 0) {
						Map<String, String> dutyMap = new HashMap<String, String>();
						int rowCount = dt.getRowCount();
						for (int i = 0; i < rowCount; i++) {
							dutyMap.put(dt.getString(i, 0), dt.getString(i, 1));
						}

						for (OrderDutyFactor orderDutyFactor : dutyFactor) {
							String dutyValueTemp = dutyMap.get(orderDutyFactor.getDutyCode());
							List<FEMDutyAmntPremList> fdAmntPremList = new ArrayList<FEMDutyAmntPremList>();
							if (StringUtil.isNotEmpty(dutyValueTemp)) {
								for (FEMDutyAmntPremList femd : orderDutyFactor.getFdAmntPremList()) {
									if (dutyValueTemp.equals(femd.getBackUp1())) {
										fdAmntPremList.add(femd);
									}
								}
								orderDutyFactor.setFdAmntPremList(fdAmntPremList);
								dutyFactorLast.add(orderDutyFactor);
							}
						}
					}

				}
			} else {
				try {
					dutyJson = java.net.URLDecoder.decode(dutyJson, "utf-8");

				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
				JSONObject dutyJsonJsonArray = JSONObject.fromObject(dutyJson);

				for (OrderDutyFactor orderDutyFactor : dutyFactor) {
					String dutyValueTemp = dutyJsonJsonArray.get(orderDutyFactor.getDutyCode()).toString();
					if (orderDutyFactor.getFdAmntPremList() != null) {
						// orderDutyFactor.getFdAmntPremList().clear();
					}
					List<FEMDutyAmntPremList> fdAmntPremList = new ArrayList<FEMDutyAmntPremList>();
					if ("nvalue".equals(dutyValueTemp)) {
						// System.out.println("dutyValueTemp=" + dutyValueTemp);
					} else {
						for (FEMDutyAmntPremList femd : orderDutyFactor.getFdAmntPremList()) {
							if (dutyValueTemp.equals(femd.getBackUp1())) {
								fdAmntPremList.add(femd);
							}
						}
						orderDutyFactor.setFdAmntPremList(fdAmntPremList);
						dutyFactorLast.add(orderDutyFactor);
					}
				}
			}
			if (dutyFactorLast.size() == 0) {
				dutyFactorLast = null;
			}
			Map<String, Object> paramter = new HashMap<String, Object>();
			paramter.put("baseInformation", baseInformations);
			paramter.put("riskAppFactor", riskAppFactior);
			paramter.put("dutyFactor", dutyFactorLast);
			paramter.put("complicatedFlag", complicatedFlag);
			if (sdi.getStartDate() != null) {
				/**
				 * 保费试算根据保险公司判断是起保日期还是当前日期 heyang 2013-7-19
				 */
				String startdate = sdf_1.format(sdi.getStartDate());
				String specialPremCalFlag =new QueryBuilder("SELECT CodeValue FROM zdcode WHERE "
						+ "CodeType='StartDateCompanyCode' and CodeValue=?", productId.substring(0, 4)).executeString();//特殊险保费计算标识
				if (specialPremCalFlag != null){
					startdate = PubFun.getCurrentDate();
				}
				paramter.put("effective", startdate);
			}
			Map<String, Object> mapPrem = sdorderService.getProductPremDutyAmounts(paramter);
			if (mapPrem.containsKey("errMessage") && StringUtil.isNotEmpty((String)mapPrem.get("errMessage"))) {
				return (String)mapPrem.get("errMessage");
			}

			// 设置一个逻辑判断开关，用于判断是否是年金险，保费与保额对调
			boolean pramAndAmntFlag = false;
			// 年金险，保费与保额对调后的保费
			String prem = null;
			String amnt = null;
			int count = new QueryBuilder("SELECT COUNT(*) FROM zdcode WHERE codetype = 'yearCash.riskCode' AND FIND_IN_SET('"+ productId +"', CodeValue);").executeInt();
			if (count > 0) {
				pramAndAmntFlag = true;
				String sql = "select Premium from sdinformationdutytemp where DutySerials = ? and (Remark2 is null or Remark2 = '' or Remark2 != 'N')";
				// 保费
				prem = new QueryBuilder(sql, dutyTempSerials).executeString();
				// 获取试算后的保额
				amnt = mapPrem.get("totlePrem").toString();

				// 修改返回对象map中的保费
				mapPrem.put("countPrice", prem);
				mapPrem.put("totlePrem", prem);

				// 修改返回对象map中的dutyAmounts
				DutyAmount[] tDutyAmountlist = (DutyAmount[])mapPrem.get("dutyAmounts");
				if (tDutyAmountlist != null) {
					for (DutyAmount t : tDutyAmountlist) {
						t.setPrem(prem);
						t.setDiscountPrice(prem);
					}
				}
			}

			String retCountPrem = String.valueOf(mapPrem.get("countPrice").toString());
			setSession("TotelPrem_" + pID, retCountPrem);

			totlePrem = totlePrem + Double.parseDouble(mapPrem.get("totlePrem").toString()) * Double.parseDouble(String.valueOf(intCount));// 总保费
			// 把每个被保人的保费传到前台显示
			returnList.add(tRecognizeeId + "-" + mapPrem.get("countPrice").toString());
			countPrice = countPrice + Double.parseDouble(mapPrem.get("countPrice").toString()) * Double.parseDouble(String.valueOf(intCount));// 折扣后保费
			// dutyAmounts = dutyAmounts +
			// Double.parseDouble(mapPrem.get("dutyAmounts").toString());// 责任明细
			Member member = getLoginMember();
			String memberId = "";
			if (member != null) {
				memberId = member.getId();
			}
			if (StringUtil.isEmpty(memberId) && StringUtil.isNotEmpty(sdorder.getMemberId())) {
				memberId = sdorder.getMemberId();
			}
 			this.totalAmnt = totalAmnt.add(new BigDecimal(ActivityCalculate.ProductCalculate(productId,sdorder.getOrderSn(), String.valueOf(Double.parseDouble(mapPrem.get("countPrice").toString()) * Double.parseDouble(String.valueOf(intCount))), sdorder.getChannelsn(), memberId)));
			price.put("retDutyAmounts", mapPrem.get("dutyAmounts"));// 责任明细
			if (mapPrem.get("DiscountRate").toString() == null || "".equals(mapPrem.get("DiscountRate").toString())) {
				DiscountRate = 0.0;
			} else {
				DiscountRate = Double.parseDouble(mapPrem.get("DiscountRate").toString());// 折扣率
			}
		}
		// }

		// price.put("returnList", returnList);// 被保人分项保费
		// price.put("retTotlePrem", totlePrem);// 总保费
		// price.put("retCountPrem", countPrice);// 折扣后保费
		// price.put("retDutyAmounts", dutyAmounts);// 责任明细
		// price.put("discountRate", DiscountRate);// 折扣率
		return null;
	}

	/**
	 * 产品保费重算--导入类
	 */
	public String impRelCalPrem() {
		Map<String, Object> paramter = new HashMap<String, Object>();// 产品中心信息集合
		Map<String, Object> tData = new HashMap<String, Object>();// 返回前台信息集合
		Map<String, Object> cMap = new HashMap<String, Object>();// 参数集合
		String[] BaseInformation = (String[]) getSession(productId + "baseInformation");
		riskAppFactior = (List<OrderRiskAppFactor>) getSession(productId + "riskAppFactior");
		dutyFactor = (List<OrderDutyFactor>) getSession(productId + "dutyFactor");

		if (BaseInformation == null || BaseInformation.length <= 0 || riskAppFactior == null || riskAppFactior.size() <= 0 || dutyFactor == null || dutyFactor.size() <= 0) {
			try {
				paramter = sdorderService.getProductInformation(productId, "N", channelCode);// 产品ID
			} catch (Exception e) {
				tData.put("Flag", "Err");// 总保费
				tData.put("Msg", "与产品中心交互失败④！");// 错误信息
				logger.error("被保人批量导入，保费试算失败，productId，" + productId + ",失败原因：" + e.getMessage(), e);
				JSONObject jsonObject = JSONObject.fromObject(tData);
			}
			BaseInformation = (String[]) paramter.get("baseInformation");// 产品基础数据
			riskAppFactior = (List<OrderRiskAppFactor>) paramter.get("riskAppFactor");
			dutyFactor = (List<OrderDutyFactor>) paramter.get("dutyFactor");
		}
		Member member = getLoginMember();
		String memberId = "";
		if (member != null) {
			memberId = member.getId();
		}
		if (StringUtil.isEmpty(memberId) && StringUtil.isNotEmpty(sdorder.getMemberId())) {
			memberId = sdorder.getMemberId();
		}
		
		cMap.put("baseInformations", BaseInformation);
		cMap.put("riskAppFactior", riskAppFactior);
		cMap.put("dutyFactor", dutyFactor);
		cMap.put("productId", productId);
		cMap.put("sdinformation", sdinformation);
		cMap.put("insureJson", insureJson);
		cMap.put("dutyJson", dutyJson);
		cMap.put("productId", productId);
		cMap.put("effdate", this.sdf_1.format(this.sdinformation.getStartDate()));
		cMap.put("complicatedFlag", complicatedFlag);
		cMap.put("dutyTempSerials", dutyTempSerials);
		String limitAge = orderConfigNewService.getSectionAge(productId);// 产品年龄限制
		Map<String, Object> insuredPremMap = new HashMap<String, Object>();
		String effdate = String.valueOf(cMap.get("effdate"));
		double insuredPrem = 0.0;
		for (SDInformationInsured insured : this.sdinsuredList) {
			if (insured != null) {
				cMap.put("insuredBirthDay", insured.getRecognizeeBirthday());
				cMap.put("insuredSex", insured.getRecognizeeSex());
				String insuredSex = insured.getRecognizeeSex();
				String realSecAge = orderConfigNewService.getRealSectionAge(insured.getRecognizeeBirthday(), limitAge, effdate);
				if (!"-1".equals(realSecAge)) {
					if (String.valueOf(insuredPremMap.get(insuredSex + "-" + realSecAge)) != null && !"null".equals(String.valueOf(insuredPremMap.get(insuredSex + "-" + realSecAge)))
							&& !"".equals(String.valueOf(insuredPremMap.get(insuredSex + "-" + realSecAge)))) {
						Map<String, Object> tempMap = (Map<String, Object>) insuredPremMap.get(insuredSex + "-" + realSecAge);
						insuredPrem = insuredPrem + Double.parseDouble(String.valueOf(tempMap.get("retCountPrem")));
					} else {
						Map<String, Object> rMap = orderConfigNewService.calPrem(cMap, memberId);
						if (rMap.containsKey("errMessage") && StringUtil.isNotEmpty((String)rMap.get("errMessage"))) {
							return rMap.get("errMessage").toString();
						}
					
						insuredPrem = insuredPrem + Double.parseDouble(String.valueOf(rMap.get("retCountPrem")));
						insuredPremMap.put(insuredSex + "-" + realSecAge, rMap);
					}
				} else {
					insuredPrem = insuredPrem + Double.parseDouble("0");
				}
			}
		}
		this.totalAmnt = new BigDecimal(insuredPrem);
		return null;
	}

	/**
	 * 已支付订单非法操作的验证
	 */

	private String orderStatusPaidCheck(SDOrder or) {
		this.showInsuredInfomation(or.getOrderSn());
		this.setCanPreviewOperatorFlag("0");
		return "result";
	}

	private String cpsProductJump(String pId, String jumpAddress) {
		Member m = getLoginMember();
		try {
			String ip = getRequest().getHeader("x-forwarded-for");
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = getRequest().getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = getRequest().getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = getRequest().getRemoteAddr();
			}
			ZdrecordCps zdc = zdrecordCpsService.createZDRecordCPS(productId, ip, m, jumpAddress);
			zdrecordCpsService.save(zdc);
			getResponse().sendRedirect(jumpAddress);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 投保信息校验方法
	 * 
	 * @return
	 */
	public String shopValidate(SDOrder order, SDInformation sdinf, SDInformationAppnt sdapp, SDInformationInsured sdins, List<SDInformationInsured> sdinsList, List<SDInformationProperty> sdProList,
			List<SDInformationInsured> sdimpinsList, List<SDInformationDuty> dutyList) {

		String tReturn = "";

		ShopCheckField tShopCheckField = new ShopCheckField();// 校验参数类

		tShopCheckField.setSdorder(order);// 订单信息
		tShopCheckField.setSdinf(sdinf); // 订单详细信息
		tShopCheckField.setApp(sdapp);// 投保人信息 
		tShopCheckField.setSubRiskType(this.riskType);
		tShopCheckField.setSdproList(sdProList);
		tShopCheckField.setDutyList(dutyList);//责任信息
		List<SDInformationInsured> insList = new ArrayList<SDInformationInsured>();
		if ("rid_me".equals(this.mulInsuredFlag) || "Y".equals(this.relationIsSelf)) {
			insList.add(sdins);
			tShopCheckField.setInsList(insList);
		} else if ("rid_td".equals(this.mulInsuredFlag)) {
			tShopCheckField.setInsList(sdimpinsList);// 被保人信息
		} else {
			tShopCheckField.setInsList(sdinsList);// 被保人信息
		}
		
		//如果被保人是投保人本人，那么进行一致性校验
		for(SDInformationInsured ins : tShopCheckField.getInsList()){
			if(ins != null){
				String relationName = ins.getRecognizeeAppntRelationName();
				if(relationName != null && relationName.equals("本人")){
					tReturn =  validateConsistent("Validate_SelfInsured",sdapp,ins);
					if(tReturn != null){
						return tReturn;
					}
				}
			}
		}
		//如果受益人有数据，则校验受益人和被保人一致性（目前只支持单被保人下的指定受益人操作）
		if(sdinformationbnfList != null && sdinformationbnfList.size() > 0){
			SDInformationInsured insutrd = tShopCheckField.getInsList().get(0);
			for(SDInformationBnf bnf : sdinformationbnfList){
				if(bnf != null){
					String relationName = bnf.getRelationToInsuredName();
					if(relationName != null && relationName.equals("本人")){
						tReturn =  validateConsistent("Validate_SelfBnf",bnf,insutrd);
						if(tReturn != null){
							return tReturn;
						}
					}
				}
			}
		}

		FMCheckFieldSchema tFMCheckFieldSchema = new FMCheckFieldSchema();              
		QueryBuilder qb  = new QueryBuilder("Where ProductId='000000' or ProductId=? or CompanySn =?  ");
		qb.add(sdinf.getProductId());
		qb.add(sdinf.getInsuranceCompany());
		FMCheckFieldSet tFMCheckFieldSet = tFMCheckFieldSchema.query(qb);
 		// 针对保险公司下该产品的所有需要校验信息进行校验  
		for (int i = 0; i < tFMCheckFieldSet.size(); i++) {
			ShopValidate tShopValidate = new ShopValidate(tShopCheckField, tFMCheckFieldSet.get(i), "");// 校验核心类
			String tFlag = tShopValidate.BaseCalculate();// 计算校验结果
			// 校验通过返回“Y”，校验不通过返回“N”
			if (tFlag.equals("N")) {
				tReturn = tFMCheckFieldSet.get(i).getMsg();
				// 航延险 被保人参数替换
				if ("K00011".equals(tFMCheckFieldSet.get(i).getCalCode())) {
					tReturn = tReturn.replace("【XX】", "【" + sdins.getRecognizeeName() + "】");
				}
				logger.warn("投保信息不符合要求，具体信息为 ：{} ", tReturn);
				// 每次只校验一条信息
				break;
			}
		}
		return tReturn;
	}

	/**
	 * 一致性校验
	 * 被保人是投保人本人时校验被保人和投保人共有元素是否一致
	 * 受益人是被保人本人时校验受益人和被保人共有元素是否一致
	 */
	public String validateConsistent(String validName,Object obj1,Object obj2){
		String msg = null;
		Map valids = CacheManager.getMapx("Code", validName);
		if(valids != null){
			Object[] keys = valids.keySet().toArray();
			for (int i = 0; i < keys.length; i++) {
				try {
					String field = (String)keys[i];
					String field1 = String.valueOf(field.split(",")[0]);
					String field2 = String.valueOf(field.split(",")[1]);
					PropertyDescriptor pd1 = new PropertyDescriptor(field1, obj1.getClass());
					PropertyDescriptor pd2 = new PropertyDescriptor(field2, obj2.getClass()); 
					Object obj1V = pd1.getReadMethod().invoke(obj1);
					Object obj2V = pd2.getReadMethod().invoke(obj2);
					if(obj1V != null && obj2V != null && !obj1V.equals(obj2V)){
						msg = (String)valids.get(field);
						logger.warn("投保信息不符合要求，具体信息为 ： {}", msg);
						return msg;
					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}  
			}
		}
		return msg;
	}
	
	/**
	 * Ajax提交后，重定向调用方法
	 * 
	 * @return 跳转页面
	 */
	public String sendDirectUrl() {
		
		String tFlag = request.getParameter("Flag");
		// 根据orderSn 查询保单信息
		if ("ShopCart".equals(orderFlag) && "Suc".equals(tFlag)) {
			return "shopCart";
		}
		sdorder = sdorderService.getOrderByOrderSn(orderSn);

		if (sdorder == null) {
			sdorder = sdorderService.get(orderId);
		}
		typeFlag = sdorder.getSdorderitem().getTypeFlag();
		channelCode = sdorder.getSdorderitem().getChannelCode();
		if (StringUtil.isNotEmpty(channelCode)) {
			jrhsURL = sdorderService.getJRHSURLByConfig(channelCode);
		}
		for (SDInformation t : sdorder.getSdinformationSet()) {
			this.sdinformation = t;
		}
		for (SDInformationInsured t : sdinformation.getSdinformationinsuredSet()) {
			this.sdinformationinsured = t;
		}

		if (null != sdinformationinsured.getSdinsuredHealthSet()) {
			this.sdinsuredHealthList = new ArrayList<SDInsuredHealth>(sdinformationinsured.getSdinsuredHealthSet());
		}
		this.insuredCount = sdinformation.getSdinformationinsuredSet().size();
		List<SDInformationInsured> sdinfquery = new ArrayList<SDInformationInsured>();
		Map<String, String> keyMap = new HashMap<String, String>();
		for (SDInformationInsured sdinf : sdinformation.getSdinformationinsuredSet()) {
			if (!"Y".equals(keyMap.get(sdinf.getRecognizeeKey()))) {
				sdinfquery.add(sdinf);
			}
			keyMap.put(sdinf.getRecognizeeKey(), "Y");
		}
		this.insuredResultCount = sdinfquery.size();
		this.directPayBankInfo = directPayBankInfoService.getByOrderSn(orderSn);
		if  (directPayBankInfo == null) {
			directPayBankInfo = new DirectPayBankInfo();
		}else if(StringUtil.isNotEmpty(directPayBankInfo.getBankProvince())){
			setBankCityAndBankProvince();
			}

		// 订单预览页面
		this.setNeedUWCheckFlag(this.getNeedUWFlagFromDB(sdinformation.getInsuranceCompany()));
		String tReturn = "";
		if (sdorder != null) {

			String nKID = StringUtil.md5Hex(PubFun.getKeyValue() + sdorder.getOrderSn());
			if (!nKID.equals(KID)) {
				addActionError("查看订单请点击<a href=\"" + Config.getServerContext() + "/shop/member!newLogin.action\"><这里></a>！登录一下！");
				return ERROR;
			}
		}
		String memberId = sdorder.getMemberId();
		if (StringUtil.isEmpty(memberId)) {
			Member member = getLoginMember();
			if (member != null) {
				memberId = member.getId();
			}
		}
		
		sdorder.setPayPrice(ActivityCalculate.ProductCalculate(sdinformation.getProductId(),sdorder.getOrderSn(),String.valueOf(sdorder.getTotalAmount()), sdorder.getChannelsn(), memberId));
		productId = sdinformation.getProductId();
		
		isLcx = FinancingCheck.getLcx(productId);
		//是否为赠险
		String FreePayProduct= Config.getConfigValue("FreePayProduct");
		String FreePayProductArray[]=FreePayProduct.split(",");
		for (int i = 0; i < FreePayProductArray.length; i++) {
			if(productId.trim().equals(FreePayProductArray[i].trim())){
				isAllFree="Y";
				break;
			}
		}
		this.questionPaper = this.sdinformationinsured.getQuestionPaper();
		selfBnfFlag = changeSelfBnfFlag(sdinformation.getProductId());
		if (StringUtil.isNotEmpty(sdorder.getDiscountRates())) {
			activityFlag = "1";
		}
		// 取得活动信息
		getActivityInfo(productId, sdorderService.getOrderByOrderSn(orderSn).getChannelsn());
		// 获得购买送积分信息
		getSendPoint(orderSn, productId, sdorder.getMemberId());
		
		if ("Suc".equals(tFlag)) {
			// 订单保存成功
			this.showInsuredInfomation(sdorder.getOrderSn());
			// 取得投保声明
			String sql = "select b.TextValue from sdsearchrelaproduct a, zdcolumnvalue b " + "where a.productid=? and a.prop1=b.RelaID and b.ColumnCode='InsuranceDec'";
			QueryBuilder qb = new QueryBuilder(sql);
			qb.add(productId);
			String insuranceDec = qb.executeString();
			this.setInsuranceDec(insuranceDec);
			tReturn = "result";
		} else if ("Question".equals(tFlag)) {
			mainAmnt = getAmnt(sdorder.getOrderSn());
			init(sdinformation.getInsuranceCompany()); // 查询地区
			supplierCode2 = sdinformation.getInsuranceCompany();
			tReturn = "wjpage";
		}
		Member memberLogin = getLoginMember();
		if (memberLogin != null && !Constant.JFSC_CHANNELSN.equals(sdorder.getChannelsn()) 
				&& !Constant.WJ_JFSC_CHANNELSN.equals(sdorder.getChannelsn()) 
				&& !Constant.WAP_JFSC_CHANNELSN.equals(sdorder.getChannelsn())) {
			loginFlag = "true";
			// 根据是否登陆判断是否显示积分抵值信息
			map_pointinfo=new HashMap<String,String>();
			Map map=new HashMap();
			List list=new ArrayList();
			list.add(productId);
			map.put("ProductList",list);
			try {
				Map result=new PointsCalculate().pointsManage(IntegralConstant.POINT_PRODUCT, "", map);
				DataTable dt_result=(DataTable) result.get(IntegralConstant.DATA);
				if(dt_result.getRowCount()>0){
					String BuyPoints=dt_result.getString(0,"BuyPoints");
					//String GivePoints=dt_result.getString(0,"GivePoints");
					BigDecimal TotalAmount=new BigDecimal(sdorder.getPayPrice());
					String PointScalerUnit = Config.getConfigValue("PointScalerUnit");
					java.text.DecimalFormat myformat = new java.text.DecimalFormat("0.000");
					String str = myformat.format(new BigDecimal(Double
							.parseDouble(String.valueOf(TotalAmount))
							* Double.parseDouble(BuyPoints)
							* Double.parseDouble(PointScalerUnit)));
					BigDecimal points=new BigDecimal(str).setScale(0, BigDecimal.ROUND_UP);
					BigDecimal CurrentValidatePoint=new BigDecimal(memberLogin.getCurrentValidatePoint());
					if(points.compareTo(CurrentValidatePoint)>0){
						map_pointinfo.put("points",String.valueOf(CurrentValidatePoint));
						BigDecimal pointValue = CurrentValidatePoint.divide(new BigDecimal(PointScalerUnit), 1,BigDecimal.ROUND_UP);
						map_pointinfo.put("pointsprice",String.valueOf(pointValue));
					}else{
						map_pointinfo.put("points",String.valueOf(points));
						BigDecimal pointValue = points.divide(new BigDecimal(PointScalerUnit), 1,BigDecimal.ROUND_UP);
						map_pointinfo.put("pointsprice", String.valueOf(pointValue));
					}
				}else{
					map_pointinfo.put("points", "");
					map_pointinfo.put("pointsprice", "");
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		} else {
			// 根据是否登陆判断是否显示积分抵值
			loginFlag = "false";
		}
		return tReturn;
	}
	
	/**
	 * 获取当前产品收益人是否为被保险人本人(用于保单预览页显示)
	 * @return
	 */
	private String changeSelfBnfFlag(String productCode) {
		String sql = "SELECT * FROM ZDCode WHERE CodeType = 'SelfBnfProduct' AND CodeValue = ?";
		QueryBuilder qb = new QueryBuilder(sql, productCode);
		DataTable dt = qb.executeDataTable();
		if (dt.getRowCount() > 0) {
			return "Y";
		}
		return "N";
	}

	/**
	 * 返回旅游目的，根据国家首字母查询
	 * 
	 * @param cList
	 *            ：国家信息集合
	 * @return 返回可按照首字母查询国家信息集合
	 */
	public List<Dictionary> getCountryDictionary(String cOrder, List<Dictionary> cList) {

		List<Dictionary> tCountDictionary = new ArrayList<Dictionary>();
		CountryChineseSpelling ccs = new CountryChineseSpelling();
		String tFirst = "";
		for (Dictionary dt : cList) {
			// 取得国家
			tFirst = String.valueOf(ccs.getFirstAlpha(dt.getCodeName()).toCharArray()[0]);
			if (cOrder.equals(tFirst)) {
				tCountDictionary.add(dt);
			}
		}
		return tCountDictionary;
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
				// 申根国家要求显示在第一位
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
	 * 从数据库查询对应保险公司是否需要核保
	 * 
	 * @param insuranceCode
	 */
	public static String getNeedUWFlagFromDB(String insuranceCode) {
		if (StringUtil.isEmpty(insuranceCode)) {
			return "0";
		}
		String sql = "select prop1 from zdcode where CodeType='UWCheckClassName' and ParentCode='UWCheckClassName' and CodeValue=?";
		DataTable dt = new QueryBuilder(sql, insuranceCode).executeDataTable();
		if (dt.getRowCount() > 0) {
			String flag = dt.getString(0, 0);
			if (!"N".equals(flag)) {
				return "1";
			}
		}
		return "0";
	}

	/**
	 * 购买调用
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked", "static-access" })
	public String pay() {
		try {
			String oldkid = KID;
			// paySn = PubFun.GetPaySn()+"D";
			sdorder = sdorderService.getOrderByOrderSn(orderSn);
			String oldPaySn = MailAction.ylQueryPaySn(orderSn);

			if (sdorder == null) {
				sdorder = sdorderService.get(orderId);
			}
			KID = StringUtil.md5Hex(PubFun.getKeyValue() + sdorder.getOrderSn());
			paySn = sdorder.getPaySn();
			// 如果没有则新建 或有号是银联的支付号重新生成
			if (StringUtil.isEmpty(paySn) || paySn.endsWith("00") || paySn.endsWith("01")) {
				paySn = PubFun.GetPaySn(sdorder.getTotalAmount() + "", "D");
			}

			channelCode = sdorder.getSdorderitem().getChannelCode();
			if(!"03".equals(typeFlag)){
				typeFlag = sdorder.getSdorderitem().getTypeFlag();
			}
			if("cps_dlr".equals(sdorder.getChannelsn()) || "03".equals(typeFlag)){
				GetDBdata db = new GetDBdata();
				String sql = "SELECT a.balance FROM cps_agent.sduser a,cps b WHERE a.userCode = b.cid AND b.on = '"+orderSn+"'";
				balance = db.getOneValue(sql);
				if (StringUtil.isEmpty(balance) || Double.valueOf(balance) == 0) {
					balance = "0.00";
				} else {
					balance = new BigDecimal(balance).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
				}
			}
			if ("02".equals(channelCode)) {
				jrhsURL = sdorderService.getJRHSURLByConfig(channelCode);
			} else if ("05".equals(channelCode)) {
				// CPS-IFRAME 自适应高度 配置 使用jrhsURL 变量.
				jrhsURL = sdorderService.getJRHSURLByConfig(channelCode);
			}
			Member memberLogin = getLoginMember();
			
			try {
				GetDBdata db = new GetDBdata();
				String sql = "select value from zdconfig where type= 'payUrl'";
				String path = db.getOneValue(sql);
				// 在已支付的情况下
				if (String.valueOf(sdorder.getOrderStatus().ordinal()).equals("7")) {
					// 会员已经登录
					if (memberLogin != null) {
						// 会员已经登录,且支付完成，跳转到支付成功页面；
						String memberId = sdorder.getMemberId();
						// 如果已登录会员和购买会员不同，跳转到
						if (!memberId.equals(memberLogin.getId())) {
							return "memberCenter";
						}
						tradeInformation = tradeInformationService.isPayFinnish(orderSn);
						callBackAmount = String.valueOf(sdorder.getTotalAmount());
						orderId = orderSn;
						channelsn = sdorder.getChannelsn();
						paySn = sdorder.getPaySn();
						// 页面返回金额处理
						DataTable dt_amount = new QueryBuilder("select tradeamount from tradesummaryinfo where paysn like '%" + PubFun.getPaySnUnion(sdorder.getPaySn()) + "%' ").executeDataTable();
						if (dt_amount.getRowCount() > 0) {
							callBackAmount = String.valueOf(new BigDecimal(dt_amount.getString(0, 0)).setScale(2, BigDecimal.ROUND_HALF_UP));
						} else {
							logger.error("支付成功,校验订单已支付，查询交易表中支付金额错误，支付流水号为：" + paySn);
							addActionError("支付异常");
							return ERROR;
						}
						
						if (Constant.JFSC_CHANNELSN.equals(channelsn)
								|| Constant.WJ_JFSC_CHANNELSN.equals(channelsn)
								|| Constant.WAP_JFSC_CHANNELSN
										.equals(channelsn)) {
							callBackAmount = sdorder.getOffsetPoint();
						}
						//积分及会员等级信息
						if (StringUtil.isNotEmpty(memberId)){
							List<SDOrder> sorderList = new ArrayList<SDOrder>();
							sorderList.add(sdorder);
							map_point_result=new PointsCalculate().getMemberUpgradeInfo(memberId, sorderList);
							
						} else {
							map_point_result=null;
						}
						// 支付成功页评论展示
						commentList = new ArrayList<CommentInfo>();
						CommentInfo commentInfo = new CommentInfo();
						commentInfo.setOrderSn(orderSn);
						DataTable dt = new QueryBuilder("select i.productName, p.productType from sdinformation i, sdproduct p where i.ordersn = ? and i.productId=p.productId ", orderSn).executeDataTable();
						if (dt != null) {
							commentInfo.setProductName(dt.getString(0, 0));
							String productType = dt.getString(0, 1);
							// 取得产品投保目的
							if (StringUtil.isNotEmpty(productType)) {
								MemberCommentAction memberCommentAction = new MemberCommentAction();
								commentInfo.setPurpose(memberCommentAction.getPurpose(productType));
							}
						}
						if (sdorder.getCommentId() != null) {
							commentInfo.setDisCommented("");
							commentInfo.setDisComment("none");
							commentInfo.setPoints(new QueryBuilder("select Integral from SDIntCalendar where memberId = ? and businessid = ? and Source = '1'", sdorder.getMemberId(), sdorder.getOrderSn()).executeString());
						} else {
							commentInfo.setDisCommented("none");
							commentInfo.setDisComment("");
						}
						commentList.add(commentInfo);
						// 线上回访的产品
						@SuppressWarnings("unchecked")
						Mapx<String, String> productcodes =  CacheManager.getMapx("Code", "OnLineCallBackProductID");
						if (StringUtil.isNotEmpty(sdinformation.getProductId()) && productcodes.containsKey(sdinformation.getProductId())) {
							// 取得线上回访展示信息
							AlipayAction alipayAction = new AlipayAction();
							onLineCallBackInfo = alipayAction.getOnLineCallBackInfo(sdinformation, sdorder.getTotalAmount().toString(), sdorder.getPayPrice());
						} else {
							onLineCallBackInfo = null;
						}
						return "callsuccess";
					}
					// 会员未登录
					else {
						try {
							String redirectionJudge = path + "?orderSn=" + orderSn + "&payType=0";
							request.getSession().setAttribute(Member.LOGIN_REDIRECTION_URL_SESSION_NAME, redirectionJudge);
							judge = "judge";
						} catch (Exception e) {
							addActionError("亲，这个订单不存在了,点击<a vlpageid=\"xiaoneng\" exturl=\"http://www.kaixinbao.com/xiaoneng\" id=\"qqwap2\" onclick=\"return(VL_FileDL(this));return false;\" href=\"javascript:void(0);\"><这里></a>！请客服MM帮帮忙吧！");
							logger.error("OrderConfigNew-pay,支付时没有查询到订单信息，orderSn，" + orderSn + ";paySn," + paySn + e.getMessage(), e);
							return ERROR;
						}
						// 会员已经登录,且支付完成，跳转到登录页面；
						return "login";
					}
				}
				if ((Constant.JFSC_CHANNELSN.equals(sdorder.getChannelsn())
						|| Constant.WJ_JFSC_CHANNELSN.equals(sdorder.getChannelsn())
						|| Constant.WAP_JFSC_CHANNELSN.equals(sdorder.getChannelsn())) && memberLogin == null) {
					logger.warn("请登录后再兑换！");
					addActionError("请登录后再兑换！");
					return ERROR;
				}
				sdinformation = sdorder.getSdinformationSet().iterator().next();
				if (sdorder != null) {
					if (String.valueOf(sdorder.getOrderStatus().ordinal()).equals("3") || String.valueOf(sdorder.getOrderStatus().ordinal()).equals("8")) {
						String sql1 = "select value from zdconfig where type= 'ContextPath'";
						String path1 = db.getOneValue(sql1);
						this.redirectionUrl = path1;
						if (Constant.JFSC_CHANNELSN.equals(sdorder.getChannelsn())
								|| Constant.WJ_JFSC_CHANNELSN.equals(sdorder.getChannelsn())
								|| Constant.WAP_JFSC_CHANNELSN.equals(sdorder.getChannelsn())) {
							addActionError("这个订单已经被您取消了，重新兑换一份吧！");
						} else {
							addActionError("这个订单已经被您取消了，重新购买一份吧！");
						}
						logger.info("OrderConfigNew-pay,订单已取消，orderSn:{};paySn:{}", orderSn, paySn);
						return ERROR;

					} else if (!String.valueOf(sdorder.getOrderStatus().ordinal()).equals("5")) {
						// String nKID = StringUtil.md5Hex(PubFun.getKeyValue()
						// + orderSn);
						/*
						 * if (!nKID.equals(KID)) {
						 * addActionError("请登陆后，进行此操作！"); return ERROR; }
						 */
					}

					if (ERROR.equals(isOutPeriod(sdorder))) {
						if (sdorder.getOrderSn().startsWith("wp")) {
							sdorder.setOrderStatus(SDOrderStatus.invalid);
							sdorderService.update(sdorder);
							wpViewFlag = "wp";
						}
						return "stimeout";
					}
				}
				// 判断订单表中是否已存有memberID，modify by cuishigang
				if (sdorder.getMemberId() == null || "".equals(sdorder.getMemberId())) {
					if (memberLogin != null) {
						sdorder.setMemberId(memberLogin.getId());
					}
				}
				if (memberLogin != null) {
					// 根据是否登陆判断是否显示优惠券和积分抵值
					loginFlag = "true";
				} else {
					// 根据是否登陆判断是否显示优惠券和积分抵值
					loginFlag = "false";
				}
				sdorder.setOrderStatus(SDOrderStatus.prepay);
				sdorder.setPaySn(paySn);
				sdorderService.update(sdorder);
				if(StringUtil.isNotEmpty(sdorder.getDiscountRates())) {
					activityFlag = "1";
				} else {
					activityFlag = "0";
				}
				
				this.insuredCount = sdinformation.getSdinformationinsuredSet().size();
				// 判断是否是理财险，是理财险显示理财支付方式
				QueryBuilder qb = new QueryBuilder("SELECT count(1) FROM zdcode WHERE CodeType='LCProduct' AND ParentCode='LCProduct' AND codevalue=?", sdinformation.getProductId());
				if (qb.executeInt() > 0) { 
					bank1 = payBaseService.getPayBaseList("lc");
				} else {
					bank1 = payBaseService.getPayBaseList("1");
					bank2 = payBaseService.getPayBaseList("2");
					bank3 = payBaseService.getPayBaseList("3");
				}
				//理财险
				isLcx = FinancingCheck.getLcx(productId);
				loginname = String.valueOf(getSession("loginName"));
				
				curdate = DateUtil.getCurrentDate("yyyy年MM月dd日");
				String sql4 = "select applicantName,applicantMobile,applicantMail,applicantIdentityid,applicantIdentityType,applicantIdentityTypeName from sdinformationappnt where informationSn in(select informationSn from sdinformation where orderSn=?)";
				String[] sql4Temp = { orderSn };
				List<HashMap<String, String>> appntList = db.query(sql4, sql4Temp);
				if (appntList == null || appntList.size() == 0) {
					addActionError("矮油，出了点小状况⑥,点击<a vlpageid=\"xiaoneng\" exturl=\"http://www.kaixinbao.com/xiaoneng\" id=\"qqwap2\" onclick=\"return(VL_FileDL(this));return false;\" href=\"javascript:void(0);\"><这里></a>！请客服MM帮帮忙吧！");
					logger.error("OrderConfigNew-pay,支付时没有查询到投保人相关信息，orderSn:{};paySn:{}", orderSn, paySn);
					return ERROR;
				}
				applicantIdentityid = appntList.get(0).get("applicantIdentityId");
				applicantName = appntList.get(0).get("applicantName");
				applicantMobile = appntList.get(0).get("applicantMobile");
				applicantIdentityType = appntList.get(0).get("applicantIdentityType");
				applicantIdentityTypeName = appntList.get(0).get("applicantIdentityTypeName");
				if (!Constant.JFSC_CHANNELSN.equals(sdorder.getChannelsn()) 
						&& !Constant.WJ_JFSC_CHANNELSN.equals(sdorder.getChannelsn()) 
						&& !Constant.WAP_JFSC_CHANNELSN.equals(sdorder.getChannelsn())) {
					Member member = new Member();
					member.setEmail(appntList.get(0).get("applicantMail"));
	
					sdinteraction.put("PaySn", paySn);
					sdinteraction.put("ChannelSn", "wj");
					sdinteraction.put("Member", member);
					sdinteraction.put("MemberName", applicantName);
					sdinteraction.put("OrderSn", sdorder.getOrderSn());
					sdinteraction.put("ProductName", sdinformation.getProductName());
					sdinteraction.put("path", (path + "?orderSn=" + sdorder.getOrderSn() + "&payType=0").replace("&", "#"));
					// cps_58bb不发送短信和邮件
					if (StringUtil.isEmpty(payType) && (StringUtil.isNotEmpty(Config.getValue("NoSendChannelsn")) && Config.getValue("NoSendChannelsn").indexOf(sdorder.getChannelsn()) == -1)) {
						MailAction.updateNoPaymentMail(oldPaySn, paySn, "a0010");
						Map<String,Object> data = new HashMap<String,Object>();
						data.put("paySn", paySn);
						data.put(MessageConstant.PARAM_RECEIVER_NAME, member.getEmail());
						ActionUtil.sendMessage("wj00015", data);
						
					}
					// 成功跳转之前，清空该订单所绑定的优惠劵
					CouponInfo coupon = couponInfoService.getCouponInfoByOrderSn(paySn);
					if (coupon != null) {
						coupon.setOrderSn("");
						couponInfoService.update(coupon);
					}
					// 清空活动信息
					sdorder.setCouponSn("");
					sdorderService.update(sdorder);
				}

				// 理财险
				if (isLcx.equals("0")) {
					
					String bankInfoSql = "SELECT CONCAT(BankAccNo, '!', z.CodeValue) AS VALUE, "
							+ " CONCAT(z.CodeName,' ', SUBSTR(BankAccNo, 1, 4),'*******',SUBSTR(BankAccNo, LENGTH(BankAccNo) - 3, 4)) AS NAME "
							+ " FROM SDCardBind s, zdcode z WHERE s.BankCode = z.CodeValue AND z.CodeType = 'LicaiBaoxianBankCode' AND z.parentcode = 'LicaiBaoxianBankCode' "
							+ " AND MemberId = ? and AccName = ? and UseFlag = 'Y' and BindFlag = 'Y' order by ValidatedDate desc, createdate desc ";
					qb = new QueryBuilder(bankInfoSql, sdorder.getMemberId(), applicantName);
					DataTable bankInfo = qb.executeDataTable();
					if (bankInfo.getRowCount() > 0) {
						bindBankInfo = bankInfo.toMapx("VALUE", "NAME");
					}
					
					qb = new QueryBuilder(" SELECT codevalue,codename FROM zdcode WHERE codetype='LicaiBaoxianBankCode' AND parentcode ='LicaiBaoxianBankCode'");
					DataTable dt = qb.executeDataTable();
					if (dt.getRowCount() > 0) {
						//遍历理财险银行信息
						for (int i = 0; i <dt.getRowCount(); i++) {
							String key = dt.getString(i, "codevalue");
							String val = dt.getString(i, "codename");
							Dictionary di = new Dictionary();
							di.setFlagType(key);
							di.setCodeValue(key);
							di.setCodeValue(val);
							lcxBank.add(di);
						}
					}
				}
				
				// 清空优惠劵信息后，页面显示原价
				callBackAmount = String.valueOf(sdorder.getTotalAmount());
			} catch (Exception e) {
				logger.error("待支付出现异常情况,错误见下面：" + e.getMessage(), e);
				addActionError("矮油，出了点小状况⑦,点击<a vlpageid=\"xiaoneng\" exturl=\"http://www.kaixinbao.com/xiaoneng\" id=\"qqwap2\" onclick=\"return(VL_FileDL(this));return false;\" href=\"javascript:void(0);\"><这里></a>！请客服MM帮帮忙吧！");
				return ERROR;
			}
			
			// 优惠活动的筛选
			List<SDOrder> paramterList = new ArrayList<SDOrder>();
			paramterList.add(sdorder);
			String realAmount = "";
			Map<String, Map<String, Object>> activity_product_info1 = ActivityCalculate.ProductShoppingCartInfo(paramterList, sdorder.getChannelsn(),true);
			if (activity_product_info1 != null && activity_product_info1.size() > 0) {
				// 遍历优惠信息Map
				Set keySet = activity_product_info1.keySet();
				for (Iterator it = keySet.iterator(); it.hasNext();) {
					// 活动号（包含“_no_activity”）
					String activitysn = String.valueOf(it.next());
					// 获取该活动关联的所有信息（包括活动信息，产品信息，优惠信息）
					Map<String, Object> map_info = activity_product_info1.get(activitysn);
					// 获取产品信息list
					List<Map<String, String>> list_product = (List<Map<String, String>>) map_info.get("ProductInfo");
					Map<String, String> map_activityamont = (Map<String, String>) map_info.get("ActivityAmont");
					Map<String, String> map_activityinfo = (Map<String, String>) map_info.get("ActivityInfo");
					if ("6".equals(map_activityinfo.get("type"))) {
						activityFlag = "1";
						String RealAmount = map_activityamont.get("RealAmount");
						callBackAmount = String.valueOf(new BigDecimal(RealAmount).setScale(2, BigDecimal.ROUND_HALF_UP));
					}
					if (!"_no_activity".equals(activitysn)) {
						realAmount = map_activityamont.get("RealAmount");
					}
					for (int j = 0; j < list_product.size(); j++) {
						// 活动每个产品对应的Map
						Map<String, String> map_product = list_product.get(j);
						if (sdorder.getOrderSn().equals(map_product.get("OrderSn"))) {
							map_product.put("ApplicantName", applicantName);
							map_product.put("ProductName", sdinformation.getProductName());
							map_product.put("InsuredCount", String.valueOf(insuredCount));
							map_product.put("ApplicantName", applicantName);
						}
					}
				}
				activityMap.put("activityMap", activity_product_info1);
			} else {
				logger.error("查询该订单的活动信息时发生异常，订单号为：{}", sdorder.getOrderSn());
			}
		
			// 将价格保留两位小数
			if (StringUtil.isDouble(callBackAmount)) {
				callBackAmount = String.valueOf(new BigDecimal(callBackAmount).setScale(2, BigDecimal.ROUND_HALF_UP));
			}
			if (StringUtil.isEmpty(realAmount)) {
				realAmount = callBackAmount;
			}
			// 处理满送、买送、高倍积分活动
			activitylist = ActivityCalculate.buySendActivityInfo(paramterList, paramterList.get(0).getChannelsn());
			//是否显示飞客ID
			fkIsShow = queryCpsOrder(orderSn);
			String memberid="";
			if (memberLogin != null) {
				memberid=memberLogin.getId();
			} else if (StringUtil.isNotEmpty(sdorder.getMemberId())) {
				memberLogin = memberService.get(sdorder.getMemberId());
				memberid=memberLogin.getId();
			}
			Map map=ActivityCalculate.payPointInfo(paramterList, sdorder.getChannelsn(),0,"", memberid);
			String givepoint=String.valueOf(map.get("givepoint"));
			//购买成功后获得积分
			map_pointinfo=new HashMap<String,String>();
			map_pointinfo.put("pointDesFlag", "false");
			
			String baseIntegral = new BigDecimal(realAmount).multiply(new BigDecimal(Config.getValue("PointScalerUnit"))).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
			if (memberLogin != null && Integer.parseInt(givepoint) > 0) {
				List<Map<String,Object>> productToPointRates = JSONArray.fromObject(map.get("productToPointRates"));
				//会员等级
				Map<String,String>  map_result_grade=ActivityCalculate.getMemberGradeBirthdayPoints(IntegralConstant.POINT_SOURCE_BUY,memberLogin.getId(), baseIntegral,productToPointRates);
				String flag_grade=map_result_grade.get("flag");
				if("true".equals(flag_grade)){
					String MemberGrade=map_result_grade.get("MemberGrade");
					givepoint=String.valueOf(Integer.parseInt(givepoint) + Integer.parseInt(map_result_grade.get("addpoints")));
					map_pointinfo.put("pointDesFlag", "true");
					map_pointinfo.put("pointDesStart", "您是"+MemberGrade+"会员,保单生效后可获得");
					map_pointinfo.put("pointDesEnd",givepoint);
				}
				//会员生日月
				Map<String,String>  map_result=ActivityCalculate.getMemberGradeBirthdayPoints(IntegralConstant.POINT_SOURCE_BIRTH_MONTH,memberLogin.getId(), baseIntegral,productToPointRates);
				String flag=map_result.get("flag");
				if("true".equals(flag)){
					String MemberGrade=map_result.get("MemberGrade");
					givepoint=String.valueOf(Integer.parseInt(givepoint) + Integer.parseInt(map_result.get("addpoints")));
					map_pointinfo.put("pointDesFlag", "true");
					map_pointinfo.put("pointDesStart", MemberGrade+"会员生日月享特权,保单生效后可获得");
					map_pointinfo.put("pointDesEnd",givepoint);
				}
			}
			map_pointinfo.put("givepoints",givepoint);
			if (StringUtil.isNotEmpty(givepoint)) {
				map_pointinfo.put("pointValue",new BigDecimal(givepoint).divide(new BigDecimal(Config.getValue("PointScalerUnit")), 1, BigDecimal.ROUND_HALF_UP).toString());
			}
			
			if (Constant.JFSC_CHANNELSN.equals(sdorder.getChannelsn())
					|| Constant.WJ_JFSC_CHANNELSN.equals(sdorder.getChannelsn())
					|| Constant.WAP_JFSC_CHANNELSN.equals(sdorder.getChannelsn())) {
				if (StringUtil.isEmpty(sdorder.getOffsetPoint()) || "0".equals(sdorder.getOffsetPoint())) {
					addActionError("订单兑换积分错误，请重新提交试试！");
					logger.error("订单兑换积分错误，请重新提交试试！订单表订单积分为空！");
					return ERROR;
				} else if (new BigDecimal(memberLogin.getCurrentValidatePoint()).compareTo(new BigDecimal(sdorder.getOffsetPoint())) < 0) {
					addActionError("您的积分不够了，快去赚吧！");
					logger.error("您的积分不够了，快去赚吧！会员（" + memberLogin.getId()
							+ "）" + "的可用积分（"
							+ memberLogin.getCurrentValidatePoint() + "）低于订单（"
							+ sdorder.getOrderSn() + "）兑换积分("
							+ sdorder.getOffsetPoint() + ")");
					return ERROR;
				}
				callBackAmount = sdorder.getOffsetPoint();
				points=String.valueOf(memberLogin.getCurrentValidatePoint()-Integer.valueOf(sdorder.getOffsetPoint()));
				
				// 更新积分兑换表状态
				Transaction trans = new Transaction();
				trans.add(new QueryBuilder(
						"update PointExchangeInfo set status = ? where orderSn = ?",
						String.valueOf(sdorder.getOrderStatus().ordinal()),
						sdorder.getOrderSn()));
				if (!trans.commit()) {
					logger.error("积分兑换保险产品，积分兑换表更新订单状态失败（OrderConfigNewAction-pay），orderSn:{}",  orderSn);
				}
				
			}
			
			// 安联支付特殊处理
			Mapx<String, String> allinpayProductsCode =  CacheManager.getMapx("Code", "allinpay.products");
			if(allinpayProductsCode != null && allinpayProductsCode.containsKey(sdinformation.getProductId())){
				this.directPayBankInfo = directPayBankInfoService.getByOrderSn(sdorder.getOrderSn());
				return "allinpay";
			}
			return "pay";
		} catch (Exception e) {
		}
		return null;
	}
	
	

	/**
	 * @Title: queryCpsOrder.
	 * @param OrderSn.
	 * @author congzn.
	 */
	public String queryCpsOrder(String OrderSn){
		String result = "N";
		if(StringUtil.isNotEmpty(OrderSn)){
			String queryCpsUserCodeSql = "SELECT CodeValue FROM zdcode WHERE CodeType = 'CPS.FKID' AND CodeValue IN(SELECT cid FROM cps c WHERE c.on = ?)";
			QueryBuilder queryCpsUserCode = new QueryBuilder(queryCpsUserCodeSql);
			queryCpsUserCode.add(OrderSn);
			String cpsUserCode = queryCpsUserCode.executeString();
			if (StringUtil.isNotEmpty(cpsUserCode)) {
				result = "Y";
			}
		}
		return result;
	}

	/**
	 * 
	 * @Title: objToBigDec
	 * @Description: (处理BigDecimal数据)
	 * @return BigDecimal 返回类型
	 * @author zhangjing
	 */
	private static BigDecimal objToBigDec(Object obj) {
		if (null == obj) {
			return new BigDecimal(0);
		} else {
			return new BigDecimal(obj.toString().trim().replaceAll(",", ""));
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
			tZipCode = qb1.executeString();
		}

		return tZipCode;
	}

	/**
	 * 支付前核保
	 */
	public String checkInsurePay() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String jsonpname = request.getParameter("callback");

		sdorder = sdorderService.getOrderByOrderSn(orderSn);
		channelCode = sdorder.getSdorderitem().getChannelCode();
		typeFlag = sdorder.getSdorderitem().getTypeFlag();
		if ("02".equals(channelCode)) {
			jrhsURL = sdorderService.getJRHSURLByConfig(channelCode);
		} else if ("05".equals(channelCode)) {
			// CPS-IFRAME 自适应高度 配置 使用jrhsURL 变量.
			jrhsURL = sdorderService.getJRHSURLByConfig(channelCode);
		}
		sdinformation = sdorder.getSdinformationSet().iterator().next();
		KID = StringUtil.md5Hex(PubFun.getKeyValue() + orderSn);
		try {

			UsersUWCheck uwCheck = new UsersUWCheck();
			Map<String, Object> tMap = uwCheck.moreUWCheck(sdorder.getOrderSn());
			String tPassFlag = tMap.get("passFlag").toString();
			String tMessage = "";
			if (!"0".equals(tPassFlag)) {
				String passFlags = "{passFlag:'pass',rtnMessage:'noRtnMssage','KID':'" + KID + "'}";
				JSONObject jsonArrays = JSONObject.fromObject(passFlags);
				String jsonstrs = jsonArrays.toString();
				return ajaxHtml(jsonpname + "(" + jsonstrs + ");");
			} else {
				List<Map<String, String>> tList = (List<Map<String, String>>) tMap.get("result");
				for (Map<String, String> m : tList) {
					if ("0".equals(m.get("passFlag"))) {
						tMessage = tMessage + m.get("RecognizeeName") + "," + m.get("rtnMessage") + ";<br/>";
					}
				}

				//理财险查询
				isLcx = FinancingCheck.getLcx(sdinformation.getProductId());
				if (isLcx.equals("0")) {
					orderStatus = String.valueOf(sdorder.getOrderStatus().ordinal());
				}
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("rtnMessage", tMessage);
				map.put("passFlag", "nopass");
				map.put("KID", KID);
				map.put("isLcx", isLcx);
				map.put("orderStatus", orderStatus);
				
				JSONObject jsonArrays = JSONObject.fromObject(map);
				String jsonstrs = jsonArrays.toString();
				// return ajaxJson(jsonstrs);
				return ajaxHtml(jsonpname + "(" + jsonstrs + ");");

			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		String passFlags = "{passFlag:'nopass',rtnMessage:'noRtnMessage'}";
		JSONObject jsonArrays = JSONObject.fromObject(passFlags);
		String jsonstrs = jsonArrays.toString();
		return ajaxHtml(jsonpname + "(" + jsonstrs + ");");

	}

	/**
	 * 支付前核保--会员中心
	 */
	public String tpyCheckPay() {
		sdorder = sdorderService.getOrderByOrderSn(orderSn);
		if (ERROR.equals(isOutPeriod(sdorder))) {
			if (sdorder.getOrderSn().startsWith("wp")) {
				sdorder.setOrderStatus(SDOrderStatus.invalid);
				sdorderService.update(sdorder);
				wpViewFlag = "wp";
			}
			return "stimeout";
		}
		KID = StringUtil.md5Hex(PubFun.getKeyValue() + orderSn);
		String orderSat = sdorder.getOrderStatus() + "";
		if ("paid".equals(orderSat)) {
			return orderStatusPaidCheck(sdorder);
		} else {
			return "tpyCheckInsure";
		}
	}

	/**
	 * 
	 * @param order
	 *            订单
	 * @return 判断支付日期是否已过
	 */
	private String isOutPeriod(SDOrder order) {
		try {
			GetDBdata db = new GetDBdata();
			String dateSql = "SELECT startDate,endDate,productId FROM sdinformation WHERE orderSn = ? ";
			String periodSql = "SELECT startPeriod FROM productPeriod WHERE riskCode=(SELECT productid FROM sdinformation WHERE ordersn = ? )";
			String[] dateSqltemp = { order.getOrderSn() };
			List<Map<String, Object>> datePeriod = db.queryObj(dateSql, dateSqltemp);
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Map<String, Object> period1 = datePeriod.get(0);
			Date periodfail = null;
			Date periodeffective = null;
			if (period1.get("endDate") != null) {
				periodfail = sfTime.parse(period1.get("endDate").toString());
				periodeffective = sfTime.parse(period1.get("startDate").toString());
			}
			productId = (String) period1.get("productId");
			// 起保时间间隔
			int stertPerid = 1;
			if (StringUtil.isNotEmpty(db.getOneValue(periodSql, dateSqltemp))) {
				stertPerid = Integer.parseInt(db.getOneValue(periodSql, dateSqltemp));
			}
			// 默认产品无次日生效，查询zdcode表中被要求次日生效的产品记录
			QueryBuilder qb = new QueryBuilder("SELECT memo FROM zdcode WHERE codetype='ProductPeriod' AND codevalue=?");
			qb.add(productId);
			DataTable dt = qb.executeDataTable();
			if (dt.getRowCount() > 0) {
				stertPerid = Integer.parseInt(dt.get(0, 0).toString());
			}
			// 当前时间
			Date now = new Date();
			currentDate = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分").format(now);
			// 起保日期
			setStartDate(new SimpleDateFormat("yyyy年MM月dd日 HH时mm分").format(periodeffective));
			// 当前时间超出起保时间
			if (stertPerid != 0 && now.after(periodeffective)) {
				return ERROR;
			} else if (stertPerid == 0) {
				if (new SimpleDateFormat("yyyyMMdd").format(now).equals(new SimpleDateFormat("yyyyMMdd").format(periodeffective))) {
					return SUCCESS;
				} else if (now.after(periodeffective)) {
					return ERROR;
				}
			}

			String tDate = com.sinosoft.sms.messageinterface.pubfun.PubFun.calSDate(sf.format(now), stertPerid, "D");
			Date nowDate = com.sinosoft.sms.messageinterface.pubfun.PubFun.StringToDate(tDate);
			if ((periodeffective.getTime() < nowDate.getTime()) && (periodfail.getTime() >= periodeffective.getTime())) {
				setStartPerid("<P>不满足该产品起保时间间隔 " + stertPerid + " 天</P>");
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
		} else if(periods != null && periods.length == 1&&"L".equals(periods[0])) {
			protectionPeriodTy = "L";
		}else {
			protectionPeriodTy = "";
		}
		if ("".equals(periodAf) || periodAf == "") {
			periodAf = periodBe;
		}
		if (periodAf != null && !"".equals(periodAf) && periodAf.length() > 1) {
			protectionPeriodLast = periodAf.substring(0, periodAf.length() - 1);
		} else if(periods != null && periods.length == 1&&"L".equals(periods[0])) {
			protectionPeriodLast = "L";
		}else {
			protectionPeriodLast = "";
		}
		protectionPeriodFlag = "true";

	}

	/**
	 * 處理保單終止日期
	 * 
	 * @param cDate
	 * @return
	 */
	public Date getEndDate(Date cDate) {
		
		String tDate = sdf_1.format(cDate) + " 23:59:59";
		Date mDate = new Date();
		try {
			mDate = sdf_2.parse(tDate);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		return mDate;
	}

	/**
	 * 年龄校验
	 * 
	 * @return
	 */
	public String ajaxAge() {
		// System.out.println(getRequest().getRequestURI()+"-"+getRequest().getRequestURL());
		String[] argArr = {applicantBirthday, productId, effdate};
		logger.info("生日编码：{} 产品id：{},生效日期：{}", argArr);
		boolean ageFlag = validateAge(applicantBirthday, productId, effdate);
		logger.info("年龄校验结果，ageFlag：{}", ageFlag);
		if (ageFlag) {
			return ajaxJson("true");
		} else {
			logger.error("投保年龄校验失败，具体信息，生日编码：" + applicantBirthday + " 产品id：" + productId + ",生效日期：" + effdate);
			return ajaxJson("false");
		}
	}
	
	/**
	 * 生日是否为明天
	 * 
	 * @return
	 */
	public String ajaxAgeAndCheckTomorrowBirthday() {
		Map<String, String> jsonMap = new HashMap<String, String>();
		String tomorrow = DateUtil.toString(DateUtil.addDay(new Date(),1),"MM-dd");
		LogUtil.info("生日编码：" + applicantBirthday + " 产品id：" + productId + ",生效日期：" + effdate);
		boolean ageFlag = validateAge(applicantBirthday, productId, effdate);
		LogUtil.info("年龄校验结果，ageFlag：" + ageFlag);
		if (ageFlag) {
			jsonMap.put("ageFlag", "true");
		} else {
			LogUtil.error("投保年龄校验失败，具体信息，生日编码：" + applicantBirthday + " 产品id：" + productId + ",生效日期：" + effdate);
			jsonMap.put("ageFlag", "false");
		}
		if(applicantBirthday.indexOf(tomorrow)>=0){
			jsonMap.put("tomorrowIsBirthday", "Y");
		}else{
			jsonMap.put("tomorrowIsBirthday", "N");
		}
		return ajaxJson(jsonMap);
	}

	/**
	 * 年齡校驗
	 * 
	 * @param BirthdayId
	 * @param productId
	 * @param effDate
	 * @return
	 */
	private boolean validateAge(String BirthdayId, String productId, String effDate) {
		Boolean tFlag = false;
		try {
			GetDBdata db = new GetDBdata();
			Date start, end = null;
			String sql_sectionage = "select SectionAge,Remark6 from sdproduct where productid=?";
			String[] sql_sectionageTemp = { productId };
			List<HashMap<String, String>> a = db.query(sql_sectionage, sql_sectionageTemp);
			HashMap<String, String> b = a.get(0);
			String sectionage = b.get("SectionAge");
			String conmancom = b.get("Remark6");
			String specialPremCalFlag =new QueryBuilder("SELECT CodeValue FROM zdcode WHERE "
					+ "CodeType='StartDateCompanyCode' and CodeValue=?", conmancom).executeString();//特殊险保费计算标识
			if (specialPremCalFlag != null){
				effDate = PubFun.getCurrentDate();
			}
			if (StringUtil.isEmpty(sectionage)) {
				return true;
			} else {
				// 多个断段间
				if (sectionage.indexOf("|") != -1) {
					String[] op = sectionage.split("\\|");
					for (int i = 0; i < op.length; i++) {

						if (op[i].indexOf("-") != -1) {
							String[] age = op[i].split("-");
							if (age[0].endsWith("D") && age[1].endsWith("D")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[0].replaceAll("D", "")), "D", sdf_1.parse(effDate));
								// if(i!=op.length-1){
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[1].replaceAll("D", "")) - 1, "D", sdf_1.parse(effDate));
								// }else{
								// end =
								// com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate),
								// -Integer.parseInt(age[1].replaceAll("D",
								// "")), "D", sdf_1.parse(effDate));
								// }
								if (compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId)) {
									tFlag = true;
								}
							} else if (age[0].endsWith("D") && age[1].endsWith("M")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[0].replaceAll("D", "")), "D", sdf_1.parse(effDate));
								// if(i!=op.length-1){
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[1].replaceAll("M", "")) - 1, "M", sdf_1.parse(effDate));
								// }else{
								// end =
								// com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate),
								// -Integer.parseInt(age[1].replaceAll("M",
								// "")), "M", sdf_1.parse(effDate));

								// }
								if (compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId)) {
									tFlag = true;
								}
							} else if (age[0].endsWith("D") && age[1].endsWith("Y")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[0].replaceAll("D", "")), "D", sdf_1.parse(effDate));
								// if(i!=op.length-1){
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[1].replaceAll("Y", "")) - 1, "Y", sdf_1.parse(effDate));
								// }else{
								// end =
								// com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate),
								// -Integer.parseInt(age[1].replaceAll("Y",
								// "")), "Y", sdf_1.parse(effDate));
								// }
								if (compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId)) {
									tFlag = true;
								}
							} else if (age[0].endsWith("M") && age[1].endsWith("M")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[0].replaceAll("M", "")), "M", sdf_1.parse(effDate));
								// if(i!=op.length-1){
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[1].replaceAll("M", "")) - 1, "M", sdf_1.parse(effDate));
								// }else{
								// end =
								// com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate),
								// -Integer.parseInt(age[1].replaceAll("M",
								// "")), "M", sdf_1.parse(effDate));
								// }
								if (compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId)) {
									tFlag = true;
								}
							} else if (age[0].endsWith("M") && age[1].endsWith("Y")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[0].replaceAll("M", "")), "M", sdf_1.parse(effDate));
								// if(i!=op.length-1){
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[1].replaceAll("Y", "")) - 1, "Y", sdf_1.parse(effDate));
								// }else{
								// end =
								// com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate),
								// -Integer.parseInt(age[1].replaceAll("Y",
								// "")), "Y", sdf_1.parse(effDate));
								// }
								if (compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId)) {
									tFlag = true;
								}
							} else if (age[0].endsWith("Y") && age[1].endsWith("Y")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[0].replaceAll("Y", "")), "Y", sdf_1.parse(effDate));
								// if(i!=op.length-1){
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[1].replaceAll("Y", "")) - 1, "Y", sdf_1.parse(effDate));
								// }else{
								// end =
								// com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate),
								// -Integer.parseInt(age[1].replaceAll("Y",
								// "")), "Y", sdf_1.parse(effDate));
								// }
								if (compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId)) {
									tFlag = true;
								}
							}
						} else {

						}
					}
				} else {
					String[] op = sectionage.split("\\|");
					for (int i = 0; i < op.length; i++) {
						if (op[i].indexOf("-") != -1) {
							String[] age = op[i].split("-");
							if (age[0].endsWith("D") && age[1].endsWith("D")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[0].replaceAll("D", "")), "D", sdf_1.parse(effDate));
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[1].replaceAll("D", "")), "D", sdf_1.parse(effDate));
								return compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId);
							} else if (age[0].endsWith("D") && age[1].endsWith("M")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[0].replaceAll("D", "")), "D", sdf_1.parse(effDate));
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[1].replaceAll("M", "")) - 1, "M", sdf_1.parse(effDate));
								return compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId);
							} else if (age[0].endsWith("D") && age[1].endsWith("Y")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[0].replaceAll("D", "")), "D", sdf_1.parse(effDate));
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[1].replaceAll("Y", "")) - 1, "Y", sdf_1.parse(effDate));
								return compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId);
							} else if (age[0].endsWith("M") && age[1].endsWith("M")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[0].replaceAll("M", "")), "M", sdf_1.parse(effDate));
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[1].replaceAll("M", "")) - 1, "M", sdf_1.parse(effDate));
								return compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId);
							} else if (age[0].endsWith("M") && age[1].endsWith("Y")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[0].replaceAll("M", "")), "M", sdf_1.parse(effDate));
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[1].replaceAll("Y", "")) - 1, "Y", sdf_1.parse(effDate));
								return compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId);
							} else if (age[0].endsWith("Y") && age[1].endsWith("Y")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[0].replaceAll("Y", "")), "Y", sdf_1.parse(effDate));
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[1].replaceAll("Y", "")) - 1, "Y", sdf_1.parse(effDate));
								return compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId);
							}
						} else {
							String[] age = op[i].split("-");
							if (age[0].endsWith("D") && age[1].endsWith("D")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[0].replaceAll("D", "")), "D", sdf_1.parse(effDate));
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[1].replaceAll("D", "")) - 1, "D", sdf_1.parse(effDate));
								return compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId);
							} else if (age[0].endsWith("D") && age[1].endsWith("M")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[0].replaceAll("D", "")), "D", sdf_1.parse(effDate));
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[1].replaceAll("M", "")) - 1, "M", sdf_1.parse(effDate));
								return compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId);
							} else if (age[0].endsWith("D") && age[1].endsWith("Y")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[0].replaceAll("D", "")), "D", sdf_1.parse(effDate));
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[1].replaceAll("Y", "")) - 1, "Y", sdf_1.parse(effDate));
								return compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId);
							} else if (age[0].endsWith("M") && age[1].endsWith("M")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[0].replaceAll("M", "")), "M", sdf_1.parse(effDate));
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[1].replaceAll("M", "")) - 1, "M", sdf_1.parse(effDate));
								return compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId);
							} else if (age[0].endsWith("M") && age[1].endsWith("Y")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[0].replaceAll("M", "")), "M", sdf_1.parse(effDate));
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[1].replaceAll("Y", "")) - 1, "Y", sdf_1.parse(effDate));
								return compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId);
							} else if (age[0].endsWith("Y") && age[1].endsWith("Y")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[0].replaceAll("Y", "")), "Y", sdf_1.parse(effDate));
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[1].replaceAll("Y", "")) - 1, "Y", sdf_1.parse(effDate));
								return compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId);
							}
						}
					}

				}
			}

		} catch (Exception e) {
			logger.error("validateAge(),发成错误：" + e.getMessage(), e);
			return false;
		}
		return tFlag;
	}

	public boolean compare_date(String startdate, String enddate, String birthday) {
		try {
			Date start = sdf_1.parse(startdate);
			Date end = sdf_1.parse(enddate);
			Date Dbirthday = sdf_1.parse(birthday);
			if (Dbirthday.getTime() <= start.getTime() && Dbirthday.getTime() > end.getTime()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.error("OrderConfigNewAction.compare_date(),发生错误：startdate("+startdate+")、enddate("+enddate+")、birthday("+birthday+")");
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * 处理orderitem
	 */
	public void orderItemSave() {
		sdorderitem.setOrderSn(sdorder.getOrderSn());
		sdorderitem.setSdorder(sdorder);
		sdorderitem.setOrderPoint("0");
		sdorderitem.setPointStatus("1");
		sdorderitem.setOrderitemSn(PubFun.GetItemNo());
		sdorderitem.setChannelCode(channelCode);
		sdorderitem.setTypeFlag(typeFlag);
		sdorderitem.setChannelAgentCode(userCode);
		// sdorder.setSdorderitem(sdorderitem);
	}

	/**
	 * 处理orderitemoht
	 * 
	 * @param t
	 */
	public void orderItemOthSave(SDInformationInsured t, SDInformation sdinf) {
		sdorderitemoth = new SDOrderItemOth();
		sdorderitemoth.setOrderSn(sdinf.getOrderSn());
		// sdorderitemoth.setOrderSn(this.orderSn);
		sdorderitemoth.setRecognizeeSn(t.getRecognizeeSn());
		sdorderitemoth.setOrderitemSn(PubFun.GetItemOthNo());

		if ("2023".equals(sdinf.getInsuranceCompany())) {
			String outcode = PubFun.getHTSN(sdinf.getProductOutCode());
			sdorderitemoth.setTpySn(outcode);// 存入华泰特殊编号
		}
		sdorderitemoth.setSdinformationinsured(t);
		sdorderitemothList.add(sdorderitemoth);
		// sdorder.setSdorderitem(sdorderitem);
	}

	public boolean checkPayStatus(String cOrderId) {
		GetDBdata db = new GetDBdata();
		String paidSQL = " SELECT sdorders.payStatus FROM sdorders WHERE sdorders.id=? ";
		String[] paidtemp = { cOrderId };
		String paidState = "";
		try {
			paidState = db.getOneValue(paidSQL, paidtemp);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		if ("2".equals(paidState)) {
			return false;
			/*
			 * addActionError("这个订单已经支付过了，不要重复支付哟！！"); return ERROR;
			 */
		}
		return true;
	}

	public String cancelTripOrder() {
		int count = new QueryBuilder("select count(1) from otorderinfo where orderSn=?", orderSn).executeInt();
		if (count < 1) {
			addActionError("系统不存在该订单");
			return ERROR;
		}
		Transaction trans = new Transaction();
		trans.add(new QueryBuilder("update otorderinfo set orderStatus=3, modifyDate=? where orderSn=? ", new Date(), orderSn));
		if (!trans.commit()) {
			logger.error("机票订单取消失败，orderSn:{}", orderSn);
			addActionError("机票订单取消失败！");
			return ERROR;
		}
		redirectionUrl = "order_query!memberQueryTripOrder.action";
		return SUCCESS;
	}
	
	/**
	 * 取消订单
	 * 
	 * @return
	 */
	public String cancel() {
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put("status", "Y");
		sdorder = sdorderService.getOrderByOrderSn(orderSn);
		if (sdorder == null) {
			addActionError("系统不存在该订单");
			jsonMap.put("status", "N");
			jsonMap.put("message", "系统不存在该订单");
			return ajaxJson(jsonMap);
		}

		// 判断订单状态是否为暂存或是待支付
		SDOrderStatus sdOrderStatus = sdorder.getOrderStatus();
		if (sdOrderStatus != SDOrderStatus.temptorysave && sdOrderStatus != SDOrderStatus.prepay) {
			addActionError("该订单状态已经更新，不能取消订单。");
			jsonMap.put("status", "N");
			jsonMap.put("message", "该订单状态已经更新，不能取消订单。");
			return ajaxJson(jsonMap);
		}
		sdorder.setOrderStatus(SDOrderStatus.invalid);
		sdorderService.update(sdorder);
		// 删除购物车
		QueryBuilder qb_delete = new QueryBuilder("delete from SDShoppingCart where orderSn=?", orderSn);
		qb_delete.executeNoQuery();
		orderSn = "";
		if ("1".equals(pointExchangeFlag)) {
			Transaction trans = new Transaction();
			trans.add(new QueryBuilder(
					"update PointExchangeInfo set status = ? where orderSn = ?",
					String.valueOf(sdorder.getOrderStatus()
							.ordinal()), sdorder.getOrderSn()));
			if (!trans.commit()) {
				logger.error("积分兑换保险产品，积分兑换表更新订单自动取消状态失败，orderSn:{}", orderSn);
				jsonMap.put("status", "N");
			}
			//redirectionUrl = "point!exchangeOrder.action";
		}
		return ajaxJson(jsonMap);
	}

	/**
	 * 處理保險期限
	 */
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
	 * 判断是否需要进入调查问卷
	 * 
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
	 * 调查问卷页保存方法
	 * 
	 * @return
	 */
	public String questionPaperSave() {
		try {
			sdorder = sdorderService.getOrderByOrderSn(this.orderSn);
			showInsuredInfomation(sdorder.getOrderSn());
			sdinformation = sdorder.getSdinformationSet().iterator().next();
			// showInformaton(order.getId());
			QuestionPaper q = questionPaperService.get(questionPaper.getId());
			questionPaperService.updateQuestion(q, questionPaper);
			questionPaperService.update(q);
			GetDBdata db = new GetDBdata();
			String paidSQL = " select orders.paymentStatus from orders where orders.id=? ";
			String[] paidtemp = { this.sdorder.getId() };
			String paidState = db.getOneValue(paidSQL, paidtemp);
			if ("2".equals(paidState)) {
				addActionError("这个订单已经支付过了，不要重复支付哟！！");
				logger.warn("questionPaperSave-订单已经支付过了，不要重复支付哟！！id:{} ordersn{}",sdorder.getId(),sdorder.getOrderSn());
				return ERROR;
			}
			KID = StringUtil.md5Hex(PubFun.getKeyValue() + sdorder.getOrderSn());
			this.setNeedUWCheckFlag(this.getNeedUWFlagFromDB(sdinformation.getInsuranceCompany()));
			if ("ShopCart".equals(orderFlag)) {
				return "shopCart";
			}
			// 取得投保声明
			String sql = "select b.TextValue from sdsearchrelaproduct a, zdcolumnvalue b " + "where a.productid=? and a.prop1=b.RelaID and b.ColumnCode='InsuranceDec'";
			QueryBuilder qb = new QueryBuilder(sql);
			qb.add(sdinformation.getProductId());
			String insuranceDec = qb.executeString();
			this.setInsuranceDec(insuranceDec);
			return "result";
		} catch (Exception e) {
			logger.error("调查问卷保存方法出现异常：" + e.getMessage(),e);
			return ERROR;
		}
	}

	/**
	 * 获取主险保额
	 * 
	 * @return
	 */
	public String getAmnt(String orderId) {
		try {
			String mAmnt = "";
			String sql = "select amt from sdinformationduty where dutySn = '2071010010001' and orderSn = ?";
			String[] sql1temp = { orderId };
			GetDBdata db0 = new GetDBdata();
			mAmnt = db0.getOneValue(sql, sql1temp);
			return mAmnt;
		} catch (Exception e) {
			logger.error("获取主险保额方法发生异常：" + e.getMessage(), e);
			return null;
		}

	}

	/**
	 * 获取地區子集
	 * 
	 * @return
	 */
	public String getChildernArea() {
		JdbcTemplateData jtd = new JdbcTemplateData();
		String id = getParameter("id");
		String sqlArea = "select id,name from area where 1=1 and parent_id = ? order by id asc";
		String[] sqlAreatemp = { id };
		try {
			List<Map> areas = jtd.obtainData(sqlArea, sqlAreatemp);
			JSONArray jsonArray = JSONArray.fromObject(areas);
			String jsonString = jsonArray.toString();
			return ajaxJson(jsonString);
		} catch (Exception e) {
			logger.error("getChildernArea-获取地区子集方法发生异常：" + e.getMessage(), e);
		}
		return ajaxJson("");
	}

	/**
	 * 
	 * 获得职业子集
	 */
	public String getChildernOPT() {
		JdbcTemplateData jtd = new JdbcTemplateData();
		String id = getParameter("id");
		String level = getParameter("level");
		String productId = getParameter("productId");
		
		QueryBuilder qb = new QueryBuilder("select * from occupation where productId=?", productId);
		boolean hasProductId = qb.executeDataTable().getRowCount() > 0;
		String sqlOpts = "";
		if (hasProductId) {
			sqlOpts = "select id,name from occupation where 1=1 and productId = '" + productId + "' and parent_id = ? order by id asc";
		} else {
			sqlOpts = "select id,name from occupation where 1=1 and (productId is null or productId = '') and parent_id = ? order by id asc";
		}
		String occupLimit = occupationService.getOccupLimitBy(productId);
		if (StringUtil.isNotEmpty(occupLimit)) {
			String[] limitArray = occupLimit.split("-");
			if (limitArray.length == 2) {
				if ("2".equals(level)) {
					sqlOpts = "select a.id,a.name from occupation a  where 1=1  and parent_id = ? and exists (select 1 from occupation b where b.parent_id = a.id and b.grade>=" + limitArray[0]
							+ " and b.grade<=" + limitArray[1] + ")  order by id asc";
				} else {
					sqlOpts = "select id,name from occupation where 1=1  and parent_id = ? and grade>=" + limitArray[0] + " and grade <= " + limitArray[1] + " order by id asc";
				}
			}
		}
		String[] sqlOptstemp = { id };
		List<Map> childerns;
		try {
			childerns = jtd.obtainData(sqlOpts, sqlOptstemp);
			JSONArray jsonArray = JSONArray.fromObject(childerns);
			String jsonString = jsonArray.toString();
			// System.out.println("=====子集大小========" + childerns.size() +
			// "====子集=====" + jsonString);
			return ajaxJson(jsonString);
		} catch (Exception e) {
			logger.error("getChildernOPT-获取职业子集方法发生异常：" + e.getMessage(), e);
		}
		return ajaxJson("");
	}

	/**
	 * 投保人快速录入查询方法
	 * 
	 * @return
	 */
	public String quickInputAppnt() {
		Map<String, Object> rMap = new HashMap<String, Object>();
		Member loginMember = getLoginMember();
		if (loginMember != null) {
			Set<SDRelationAppnt> reappntSet = loginMember.getSdrelationappntSet();
			int i = 1;
			// 现根据产品匹配
			for (SDRelationAppnt tSDRelationAppnt : reappntSet) {
				tSDRelationAppnt.setmMember(null);
				if (productId.equals(tSDRelationAppnt.getProductId())) {
					i = i + 1;
					this.reappntList.add(tSDRelationAppnt);
					if (i >= 11) {
						break;
					}
				}
			}
			// 如果根据产品匹配没有到20个，那么根据保险公司匹配
			if (i < 11) {
				for (SDRelationAppnt tSDRelationAppnt : reappntSet) {
					tSDRelationAppnt.setmMember(null);
					if (!productId.equals(tSDRelationAppnt.getProductId()) && this.supplierCode2.equals(tSDRelationAppnt.getRemark())) {
						i = i + 1;
						this.reappntList.add(tSDRelationAppnt);
						if (i >= 11) {
							break;
						}
					}
				}
			}
			// 如果根据产品、保险公司匹配都没有到20个，那么全站匹配
			if (i < 11) {
				for (SDRelationAppnt tSDRelationAppnt : reappntSet) {
					tSDRelationAppnt.setmMember(null);
					if (!productId.equals(tSDRelationAppnt.getProductId()) && !this.supplierCode2.equals(tSDRelationAppnt.getRemark())) {
						i = i + 1;
						this.reappntList.add(tSDRelationAppnt);
						if (i >= 11) {
							break;
						}
					}
				}
			}
		}
		rMap.put("sdrelationappnt", reappntList);
		JSONObject jsonObject = JSONObject.fromObject(rMap);
		return ajax(jsonObject.toString(), "text/html");
	}

	/**
	 * 投保人快速录入--选中方法
	 * 
	 * @return
	 */
	public String quickInputAppntOne() {
		try {
			reappntName = java.net.URLDecoder.decode(reappntName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		Map<String, Object> rMap = new HashMap<String, Object>();
		Member loginMember = getLoginMember();
		if (loginMember != null) {
			Set<SDRelationAppnt> reappntSet = loginMember.getSdrelationappntSet();
			if (!"".equals(reappntName) && reappntName != null && !"null".equals(reappntName)) {
				for (SDRelationAppnt tSDRelationAppnt : reappntSet) {
					tSDRelationAppnt.setmMember(null);
					// 根据产品匹配
					if (productId.equals(tSDRelationAppnt.getProductId()) && reappntName.equals(tSDRelationAppnt.getApplicantName())) {
						this.reappntList.add(tSDRelationAppnt);
						rMap.put("sdrelationappnt", reappntList);
						rMap.put("appflag", "01");// 产品匹配
						rMap.put("message", "Suc");
						break;
					} else if (!productId.equals(tSDRelationAppnt.getProductId()) && supplierCode2.equals(tSDRelationAppnt.getRemark()) && reappntName.equals(tSDRelationAppnt.getApplicantName())) {
						this.reappntList.add(tSDRelationAppnt);
						rMap.put("sdrelationappnt", reappntList);
						rMap.put("appflag", "02");// 保险公司匹配
						rMap.put("message", "Suc");
						break;
					} else if (!productId.equals(tSDRelationAppnt.getProductId()) && !supplierCode2.equals(tSDRelationAppnt.getRemark()) && reappntName.equals(tSDRelationAppnt.getApplicantName())) {
						this.reappntList.add(tSDRelationAppnt);
						rMap.put("sdrelationappnt", reappntList);
						rMap.put("appflag", "03");// 全站匹配
						rMap.put("message", "Suc");
						break;
					}
				}
			} else {
				rMap.put("message", "Fal");
			}
		} else {
			rMap.put("message", "error");
			rMap.put("Emessage", "请重新登陆！");
		}
		String tomorrow = DateUtil.toString(DateUtil.addDay(new Date(),1),"MM-dd");
		if(reappntList.get(0).getApplicantBirthday().indexOf(tomorrow)>=0){
			rMap.put("tomorrowIsBirthday", "Y");
		}else{
			rMap.put("tomorrowIsBirthday", "N");
		}
		JSONObject jsonObject = JSONObject.fromObject(rMap);
		return ajax(jsonObject.toString(), "text/html");
	}

	/**
	 * 投保人、被保人快速录入--初始化方法
	 */
	public void quickQueryAppnt() {
		if ("true".equals(loginFlag)) {
			Member loginMember = getLoginMember();
			Set<SDRelationAppnt> reappntSet = loginMember.getSdrelationappntSet();
			Set<SDRelationRecognizee> reinsuredSet = loginMember.getSdrelationrecognizeeSet();
			// 处理投保人
			if (!"".equals(reappntName) && reappntName != null && "null".equals(reappntName)) {
				for (SDRelationAppnt tSDRelationAppnt : reappntSet) {
					tSDRelationAppnt.setmMember(null);
					if (productId.equals(tSDRelationAppnt.getProductId()) && reappntName.equals(tSDRelationAppnt.getApplicantName())) {
						this.reappntList.add(tSDRelationAppnt);
						break;
					}
				}
			} else {
				int i = 1;
				// 先根据产品匹配
				for (SDRelationAppnt tSDRelationAppnt : reappntSet) {
					tSDRelationAppnt.setmMember(null);
					if (productId.equals(tSDRelationAppnt.getProductId())) {
						i = i + 1;
						this.reappntList.add(tSDRelationAppnt);
						if (i >= 11) {
							break;
						}
					}
				}
				// 如果根据产品匹配没有到20个，那么根据保险公司匹配
				if (i < 11) {
					for (SDRelationAppnt tSDRelationAppnt : reappntSet) {
						tSDRelationAppnt.setmMember(null);
						if (!productId.equals(tSDRelationAppnt.getProductId()) && this.supplierCode2.equals(tSDRelationAppnt.getRemark())) {
							i = i + 1;
							this.reappntList.add(tSDRelationAppnt);
							if (i >= 11) {
								break;
							}
						}
					}
				}
				// 如果根据产品、保险公司匹配都没有到20个，那么全站匹配
				if (i < 11) {
					for (SDRelationAppnt tSDRelationAppnt : reappntSet) {
						tSDRelationAppnt.setmMember(null);
						if (!productId.equals(tSDRelationAppnt.getProductId()) && !this.supplierCode2.equals(tSDRelationAppnt.getRemark())) {
							i = i + 1;
							this.reappntList.add(tSDRelationAppnt);
							if (i >= 11) {
								break;
							}
						}
					}
				}
			}
			// 处理被保人
			if (!"".equals(reinsuredName) && reinsuredName != null && "null".equals(reinsuredName)) {
				for (SDRelationRecognizee tSDRelationRecognizee : reinsuredSet) {
					tSDRelationRecognizee.setmMember(null);
					if (productId.equals(tSDRelationRecognizee.getProductId()) && reinsuredName.equals(tSDRelationRecognizee.getRecognizeeName())) {
						this.recognizeeList.add(tSDRelationRecognizee);
						break;
					}
				}
			} else {
				int i = 1;
				// 先根据产品匹配
				for (SDRelationRecognizee tSDRelationRecognizee : reinsuredSet) {
					tSDRelationRecognizee.setmMember(null);
					if (productId.equals(tSDRelationRecognizee.getProductId())) {
						i = i + 1;
						this.recognizeeList.add(tSDRelationRecognizee);
						if (i >= 11) {
							break;
						}
					}
				}
				// 如果根据产品匹配没有到20个，那么根据保险公司匹配
				if (i < 11) {
					for (SDRelationRecognizee tSDRelationRecognizee : reinsuredSet) {
						tSDRelationRecognizee.setmMember(null);
						if (!productId.equals(tSDRelationRecognizee.getProductId()) && this.supplierCode2.equals(tSDRelationRecognizee.getRemark())) {
							i = i + 1;
							this.recognizeeList.add(tSDRelationRecognizee);
							if (i >= 11) {
								break;
							}
						}
					}
				}
				// 如果根据产品、保险公司匹配都没有到20个，那么全站匹配
				if (i < 11) {
					for (SDRelationRecognizee tSDRelationRecognizee : reinsuredSet) {
						tSDRelationRecognizee.setmMember(null);
						if (!productId.equals(tSDRelationRecognizee.getProductId()) && !this.supplierCode2.equals(tSDRelationRecognizee.getRemark())) {
							i = i + 1;
							this.recognizeeList.add(tSDRelationRecognizee);
							if (i >= 11) {
								break;
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 投保人快速录入--保存方法
	 * 
	 * @param appnt
	 * @return
	 */
	public List<SDRelationAppnt> sdrelationappntSave(SDInformationAppnt appnt) {
		List<SDRelationAppnt> sdreappntList = new ArrayList<SDRelationAppnt>();
		Member loginMember = this.getLoginMember();
		SDRelationAppnt reAppnt;
		if (loginMember != null) {
			// 根据productID、memberId、appntName查询关联投保人信息
			reAppnt = this.sdRelationAppntService.getSDRelationAppntInfo(this.supplierCode2, productId, loginMember.getId(), appnt.getApplicantName());
			int insuredCount = loginMember.getSdrelationappntSet().size();
			if (reAppnt == null) {
				if (insuredCount <= 9) {
					reAppnt = new SDRelationAppnt();
					reAppnt = cotySDRelationAppnt(loginMember, reAppnt, appnt, productId, this.sdinformation.getInsuranceCompany());
				}
			} else {
				reAppnt = cotySDRelationAppnt(loginMember, reAppnt, appnt, productId, this.sdinformation.getInsuranceCompany());
			}
			if (reAppnt != null) {
				sdreappntList.add(reAppnt);
			}
		}

		return sdreappntList;
	}

	/**
	 * 复制投保人信息
	 * 
	 * @param loginMember
	 * @param reAppnt
	 * @param appnt
	 * @param productId
	 * @param insuranceCompany
	 * @return
	 */
	public SDRelationAppnt cotySDRelationAppnt(Member loginMember, SDRelationAppnt reAppnt, SDInformationAppnt appnt, String productId, String insuranceCompany) {

		reAppnt.setProductId(productId);
		reAppnt.setRemark(insuranceCompany);// 保险公司
		reAppnt.setApplicantName(appnt.getApplicantName());
		reAppnt.setApplicantEnName(appnt.getApplicantEnName());
		reAppnt.setApplicantIdentityType(appnt.getApplicantIdentityType());
		reAppnt.setApplicantIdentityId(appnt.getApplicantIdentityId());
		reAppnt.setApplicantIdentityTypeName(appnt.getApplicantIdentityTypeName());
		reAppnt.setApplicantSex(appnt.getApplicantSex());
		reAppnt.setApplicantSexName(appnt.getApplicantSexName());
		reAppnt.setApplicantBirthday(appnt.getApplicantBirthday());
		if (StringUtil.isNotEmpty(appnt.getApplicantMail())) {
			reAppnt.setApplicantMail(appnt.getApplicantMail());
		}
		reAppnt.setApplicantArea1(appnt.getApplicantArea1());
		reAppnt.setApplicantArea2(appnt.getApplicantArea2());
		reAppnt.setApplicantArea3(appnt.getApplicantArea3());
		reAppnt.setApplicantAddress(appnt.getApplicantAddress());
		reAppnt.setApplicantZipCode(appnt.getApplicantZipCode());
		if (StringUtil.isNotEmpty(appnt.getApplicantMobile())) {
			reAppnt.setApplicantMobile(appnt.getApplicantMobile());
		}
		reAppnt.setApplicantTel(appnt.getApplicantTel());
		reAppnt.setApplicantOccupation1(appnt.getApplicantOccupation1());
		reAppnt.setApplicantOccupation2(appnt.getApplicantOccupation2());
		reAppnt.setApplicantOccupation3(appnt.getApplicantOccupation3());
		reAppnt.setApplicantStartID(appnt.getApplicantStartID());
		reAppnt.setApplicantEndID(appnt.getApplicantEndID());
		reAppnt.setmMember(loginMember);
		reAppnt.setMemberId(loginMember.getId());
		reAppnt.setSocialSecurity(appnt.getSocialSecurity());
		try {
			reAppnt.setModifyDate(this.sdf_2.parse(PubFun.getCurrentDate() + " " + PubFun.getCurrentTime()));
		} catch (ParseException e) {
			logger.error("cotySDRelationAppnt,日期转型错误：" + e.getMessage(), e);
		}

		return reAppnt;

	}

	/**
	 * 被保人快速录入--保存方法
	 * 如做修改，WapShoppingUtil.java 中需要修改
	 * @param appnt
	 * @return
	 */
	public List<SDRelationRecognizee> sdrelationrecognizeeSave(List<SDInformationInsured> insuredList) {
		List<SDRelationRecognizee> sdreInsuredList = new ArrayList<SDRelationRecognizee>();
		Member loginMember = this.getLoginMember();
		SDRelationRecognizee reRecognizee;
		Map<String, Integer> insuredNameMap = new HashMap<String, Integer>();
		if (loginMember != null) {
			for (SDInformationInsured insured : insuredList) {
				// 根据productID、memberId、appntName查询关联投保人信息
				if (StringUtil.isEmpty(insured.getRecognizeeName())) {
					continue;
				}
				reRecognizee = this.sdRelationRecognizeeService.getSDRelationRecognizeeInfo(this.supplierCode2, productId, loginMember.getId(), insured.getRecognizeeName());
				if (reRecognizee == null) {
					int insuredCount = loginMember.getSdrelationrecognizeeSet().size() + sdreInsuredList.size();
					if (insuredCount >= 10)
						continue;
					reRecognizee = new SDRelationRecognizee();
					insuredCount = insuredCount + 1;
				}
				reRecognizee.setProductId(productId);
				reRecognizee.setRemark(this.sdinformation.getInsuranceCompany());// 保险公司
				reRecognizee.setRecognizeeName(insured.getRecognizeeName());
				reRecognizee.setRecognizeeEnName(insured.getRecognizeeEnName());
				reRecognizee.setRecognizeeIdentityType(insured.getRecognizeeIdentityType());
				reRecognizee.setRecognizeeIdentityId(insured.getRecognizeeIdentityId());
				reRecognizee.setRecognizeeIdentityTypeName(insured.getRecognizeeIdentityTypeName());
				reRecognizee.setRecognizeeSex(insured.getRecognizeeSex());
				reRecognizee.setRecognizeeSexName(insured.getRecognizeeSexName());
				reRecognizee.setRecognizeeBirthday(insured.getRecognizeeBirthday());
				if (StringUtil.isNotEmpty(insured.getRecognizeeMail())) {
					reRecognizee.setRecognizeeMail(insured.getRecognizeeMail());
				}
				reRecognizee.setRecognizeeArea1(insured.getRecognizeeArea1());
				reRecognizee.setRecognizeeArea2(insured.getRecognizeeArea2());
				reRecognizee.setRecognizeeArea3(insured.getRecognizeeArea3());
				reRecognizee.setRecognizeeAddress(insured.getRecognizeeAddress());
				reRecognizee.setRecognizeeZipCode(insured.getRecognizeeZipCode());
				if (StringUtil.isNotEmpty(insured.getRecognizeeMobile())) {
					reRecognizee.setRecognizeeMobile(insured.getRecognizeeMobile());
				}
				reRecognizee.setRecognizeeTel(insured.getRecognizeeTel());
				reRecognizee.setRecognizeeOccupation1(insured.getRecognizeeOccupation1());
				reRecognizee.setRecognizeeOccupation2(insured.getRecognizeeOccupation2());
				reRecognizee.setRecognizeeOccupation3(insured.getRecognizeeOccupation3());
				reRecognizee.setRecognizeeStartID(insured.getRecognizeeStartID());
				reRecognizee.setRecognizeeEndID(insured.getRecognizeeEndID());
				reRecognizee.setmMember(loginMember);
				reRecognizee.setMemberId(loginMember.getId());
				try {
					reRecognizee.setModifyDate(this.sdf_2.parse(PubFun.getCurrentDate() + " " + PubFun.getCurrentTime()));
				} catch (ParseException e) {
					logger.error("sdrelationrecognizeeSave,日期转型错误：" + e.getMessage(), e);
				}
				if (insuredNameMap != null && insuredNameMap.size() >= 1) {
					if (insuredNameMap.get(reRecognizee.getRecognizeeName()) != null && !"".equals(insuredNameMap.get(reRecognizee.getRecognizeeName()))) {
						if (insuredNameMap.get(reRecognizee.getRecognizeeName()) <= 0) {
							insuredNameMap.put(reRecognizee.getRecognizeeName(), 1);
							if (reRecognizee != null) {
								sdreInsuredList.add(reRecognizee);
							}
						} else {
							insuredNameMap.put(reRecognizee.getRecognizeeName(), 1);
						}
					} else {
						insuredNameMap.put(reRecognizee.getRecognizeeName(), 1);
						if (reRecognizee != null) {
							sdreInsuredList.add(reRecognizee);
						}
					}
				} else {
					insuredNameMap.put(reRecognizee.getRecognizeeName(), 1);
					if (reRecognizee != null) {
						sdreInsuredList.add(reRecognizee);
					}
				}
			}
		}

		return sdreInsuredList;
	}

	/**
	 * 被保人快速录入查询方法
	 * 
	 * @return
	 */
	public String quickInputInsured() {
		Map<String, Object> rMap = new HashMap<String, Object>();
		Member loginMember = getLoginMember();
		if (loginMember != null) {
			Set<SDRelationRecognizee> reinsuredSet = loginMember.getSdrelationrecognizeeSet();
			int i = 1;
			// 现根据产品匹配
			for (SDRelationRecognizee tSDRelationRecognizee : reinsuredSet) {
				tSDRelationRecognizee.setmMember(null);
				if (productId.equals(tSDRelationRecognizee.getProductId())) {
					i = i + 1;
					this.recognizeeList.add(tSDRelationRecognizee);
					if (i >= 11) {
						break;
					}
				}
			}
			// 如果根据产品匹配没有到20个，那么根据保险公司匹配
			if (i < 11) {
				for (SDRelationRecognizee tSDRelationRecognizee : reinsuredSet) {
					tSDRelationRecognizee.setmMember(null);
					if (!productId.equals(tSDRelationRecognizee.getProductId()) && this.supplierCode2.equals(tSDRelationRecognizee.getRemark())) {
						i = i + 1;
						this.recognizeeList.add(tSDRelationRecognizee);
						if (i >= 11) {
							break;
						}
					}
				}
			}
			// 如果根据产品、保险公司匹配都没有到20个，那么全站匹配
			if (i < 11) {
				for (SDRelationRecognizee tSDRelationRecognizee : reinsuredSet) {
					tSDRelationRecognizee.setmMember(null);
					if (!productId.equals(tSDRelationRecognizee.getProductId()) && !this.supplierCode2.equals(tSDRelationRecognizee.getRemark())) {
						i = i + 1;
						this.recognizeeList.add(tSDRelationRecognizee);
						if (i >= 11) {
							break;
						}
					}
				}
			}
		}
		rMap.put("relationrecognizee", recognizeeList);
		JSONObject jsonObject = JSONObject.fromObject(rMap);
		return ajax(jsonObject.toString(), "text/html");
	}

	/**
	 * 被保人快速录入--选中方法
	 * 
	 * @return
	 */
	public String quickInputinsuredOne() {
		try {
			this.reinsuredName = java.net.URLDecoder.decode(reinsuredName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		Map<String, Object> rMap = new HashMap<String, Object>();
		Member loginMember = getLoginMember();
		if (loginMember != null) {
			Set<SDRelationRecognizee> reinsuredSet = loginMember.getSdrelationrecognizeeSet();
			if (!"".equals(reinsuredName) && reinsuredName != null && !"null".equals(reinsuredName)) {
				for (SDRelationRecognizee SDRelationRecognizee : reinsuredSet) {
					SDRelationRecognizee.setmMember(null);
					// 根据产品匹配
					if (productId.equals(SDRelationRecognizee.getProductId()) && reinsuredName.equals(SDRelationRecognizee.getRecognizeeName())) {
						this.recognizeeList.add(SDRelationRecognizee);
						rMap.put("relationrecognizee", recognizeeList);
						rMap.put("appflag", "01");// 产品匹配
						rMap.put("message", "Suc");
						break;
					} else if (!productId.equals(SDRelationRecognizee.getProductId()) && supplierCode2.equals(SDRelationRecognizee.getRemark())
							&& reinsuredName.equals(SDRelationRecognizee.getRecognizeeName())) {
						this.recognizeeList.add(SDRelationRecognizee);
						rMap.put("relationrecognizee", recognizeeList);
						rMap.put("appflag", "02");// 保险公司匹配
						rMap.put("message", "Suc");
						break;
					} else if (!productId.equals(SDRelationRecognizee.getProductId()) && !supplierCode2.equals(SDRelationRecognizee.getRemark())
							&& reinsuredName.equals(SDRelationRecognizee.getRecognizeeName())) {
						this.recognizeeList.add(SDRelationRecognizee);
						rMap.put("relationrecognizee", recognizeeList);
						rMap.put("appflag", "03");// 全站匹配
						rMap.put("message", "Suc");
						break;
					}
				}
			} else {
				rMap.put("message", "Fal");
			}
		} else {
			rMap.put("message", "error");
			rMap.put("Emessage", "请重新登陆！");
		}
		String tomorrow = DateUtil.toString(DateUtil.addDay(new Date(),1),"MM-dd");
		if(recognizeeList.get(0).getRecognizeeBirthday().indexOf(tomorrow)>=0){
			rMap.put("tomorrowIsBirthday", "Y");
		}else{
			rMap.put("tomorrowIsBirthday", "N");
		}
		JSONObject jsonObject = JSONObject.fromObject(rMap);
		return ajax(jsonObject.toString(), "text/html");
	}

	/**
	 * 根据保险公司、编码类型、编码名称得到编码
	 * 
	 * @return
	 */
	public String ajaxGetCode() {
		Map<String, Object> rMap = new HashMap<String, Object>();
		try {
			codeName = java.net.URLDecoder.decode(codeName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		String appDictionaryCode = this.dictionaryService.getCodeValueByCodeName(this.supplierCode2, codeType, codeName.trim());
		rMap.put("appDictionaryCode", appDictionaryCode);
		JSONObject jsonObject = JSONObject.fromObject(rMap);
		return ajax(jsonObject.toString(), "text/html");
	}

	/**
	 * 根据保险公司、编码类型、编码名称得到编码
	 * 
	 * @return
	 */
	public String ajaxValidateCode() {
		Map<String, Object> rMap = new HashMap<String, Object>();
		try {
			codeName = java.net.URLDecoder.decode(codeName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		String cCodeName = this.dictionaryService.getNameByCodeType(this.supplierCode2, codeType, codeName.trim());
		if (StringUtil.isNotEmpty(cCodeName)) {
			rMap.put("codeNameFlag", "Y");
		} else {
			rMap.put("codeNameFlag", "N");
		}
		JSONObject jsonObject = JSONObject.fromObject(rMap);
		return ajax(jsonObject.toString(), "text/html");
	}

	/**
	 * 得到产品限购份数--判断页面是否需要显示“购买份数”录入项
	 * 
	 * @param cProductID
	 *            产品ID
	 */
	public void dealLimitCount(SDInformation sdinf) {
		logger.info("产品id：========{}", sdinf.getProductId());
		String sql = "select LimitCount,Occup,SectionAge,IsPublish,HtmlPath from sdproduct where productid=?";
		String[] sqltemp = { sdinf.getProductId() };
		GetDBdata db = new GetDBdata();
		List<HashMap<String, String>> sdproduct;
		try {
			sdproduct = db.query(sql, sqltemp);

			HashMap<String, String> product = sdproduct.get(0);
			if (product.get("LimitCount") == null || "".equals(product.get("LimitCount"))) {
				limitCount = 10;
			} else {
				limitCount = Integer.parseInt(product.get("LimitCount"));
				if (limitCount >= 11) {
					limitCount = 10;
				}
			}
			if (!this.orderConfigNewService.buyCountFlag(sdinf)) {
				limitCount = 1;
			}
			productHtml = product.get("HtmlPath");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 复制被保人信息
	 * 
	 * @param insured
	 * @param risktype
	 * @param index
	 * @return
	 */
	public Map<String, Object> dealInsuredCount(SDInformationInsured insured, SDInformationRiskType risktype, int index) {

		Map<String, Object> insuredMap = new HashMap<String, Object>();
		insuredMap = orderConfigNewService.copySDInsured(insured, risktype, index);

		return insuredMap;
	}

	/**
	 * 处理购买份数信息
	 * 
	 * @param insured
	 *            被保人信息
	 * @param risktype
	 *            保单信息
	 * @param sdinf
	 *            订单详细
	 */
	public void dealInsuredBuyCount(SDInformationInsured insured, SDInformationRiskType risktype, SDInformation sdinf) {

		double insPrem = 0.00;
		if (insured.getRecognizeeMul() != null && !"".equals(insured.getRecognizeeMul())) {
			int tMul = Integer.parseInt(insured.getRecognizeeMul());
			if (tMul >= 2) {
				for (int i = 2; i <= tMul; i++) {
					Map<String, Object> insuredMap = dealInsuredCount(insured, risktype, i);
					if (insuredMap.size() >= 1) {
						SDInformationInsured newInsured = (SDInformationInsured) insuredMap.get("insured");
						SDInformationRiskType newRiskType = (SDInformationRiskType) insuredMap.get("risktype");
						newInsured.setSdinformaitonrisktype(newRiskType);
						newRiskType.setSdinformationinsured(newInsured);
						// 处理SDOrderItemOth信息
						this.orderItemOthSave(newInsured, sdinf);
						// 处理产品投保要素信息
						sdInformationInsuredElementsSave(newInsured);
						this.sdInsuredOtherList.add(newInsured);
						this.sdRiskTypeOtherList.add(newRiskType);

						if (Constant.JFSC_CHANNELSN.equals(sdorder.getChannelsn())
								|| Constant.WJ_JFSC_CHANNELSN.equals(sdorder.getChannelsn())
								|| Constant.WAP_JFSC_CHANNELSN.equals(sdorder.getChannelsn())) {
							insPrem = insPrem + Double.parseDouble(newInsured.getRecognizeePrem());
						} else {
							insPrem = insPrem + Double.parseDouble(newInsured.getDiscountPrice());
						}
					}
				}
			}
		}
		this.insTotalAmnt = insTotalAmnt.add(new BigDecimal(insPrem));
	}

	/**
	 * 
	 * @param startDate
	 *            保险起期
	 * @param limit
	 *            保险期限
	 * @param limitType
	 *            保险期限类型
	 * @param compareDate
	 *            保险终止日期
	 * @return 保险期是否被人为修改过
	 */
	public boolean checkYeMoDa(Date startDate, String limit, String limitType, Date compareDate, String recognizeeBirthday) {
		try {

			if (StringUtil.isEmpty(limitType)) {
				return true;
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cdS = Calendar.getInstance();
			Calendar cdC = Calendar.getInstance();
			int limitInt = 0;
			if(!"L".equals(limitType))
				limitInt = Integer.parseInt(limit);
			cdS.setTime(sdf.parse(sdf.format(startDate)));
			cdC.setTime(sdf.parse(sdf.format(compareDate)));
			if ("Y".equals(limitType.trim())) {
				cdS.add(Calendar.DATE, -1);
				cdS.add(Calendar.YEAR, limitInt);
				//String re1g = sdf.format(cdS.getTime());
				String reg = sdf.format(cdS.getTime());
				String comp = sdf.format(cdC.getTime());
				if (!StringUtil.isEmpty(reg)) {
					if (reg.equalsIgnoreCase(comp)) {
						return true;
					}
				}
			} else if ("M".equals(limitType.trim())) {
				cdS.add(Calendar.DATE, -1);
				cdS.add(Calendar.MONTH, limitInt);
				//String re1g = sdf.format(cdS.getTime());
				
				String reg = sdf.format(cdS.getTime());
				String comp = sdf.format(cdC.getTime());
				if (!StringUtil.isEmpty(reg)) {
					if (reg.equalsIgnoreCase(comp)) {
						return true;
					}
				}
			} else if ("D".equals(limitType.trim())) {
				cdS.add(Calendar.DATE, limitInt);
				cdS.add(Calendar.DATE, -1);
				String reg = sdf.format(cdS.getTime());
				String comp = sdf.format(cdC.getTime());
				if (!StringUtil.isEmpty(reg)) {
					if (reg.equalsIgnoreCase(comp)) {
						return true;
					}
				}
			} else if ("A".equals(limitType.trim()) && StringUtil.isNotEmpty(recognizeeBirthday)) {
				String[] startArr = sdf.format(cdS.getTime()).split("-");
				//根据保单终止日期计算方式进行验证
				String periodFlagCode=new QueryBuilder("SELECT CodeValue FROM zdcode "
						+ "WHERE CodeType='specialEffDateFlag' and CodeValue=?", productId).executeString();
				Calendar bir = Calendar.getInstance();
				bir.setTime(sdf.parse(recognizeeBirthday));
				Calendar newDate = null;
				if (bir.get(Calendar.MONTH) < cdS.get(Calendar.MONTH)) {
					newDate = dateAdd(limitInt, bir);
				}else if(bir.get(Calendar.MONTH) > cdS.get(Calendar.MONTH)){
					newDate = dateAdd(limitInt + 1,bir);
				}else{
					if (bir.get(Calendar.DATE) <= cdS.get(Calendar.DATE)) {
						newDate = dateAdd(limitInt,bir);
					} else {
						newDate = dateAdd(limitInt + 1,bir);
					}
				}
				if(periodFlagCode == null){
					newDate.set(Calendar.MONTH, Integer.parseInt(startArr[1]) - 1);
					newDate.set(Calendar.DATE,Integer.parseInt(startArr[2]));
					newDate.add(Calendar.DATE, -1);
				}
				if(!StringUtil.isEmpty(sdf.format(cdS.getTime())) && 
						sdf.format(newDate.getTime()).equalsIgnoreCase(sdf.format(compareDate))){
					return true;
				}
			} else if ("L".equals(limitType.trim())) {

				if ("9999-12-31".equals(new SimpleDateFormat("yyyy-MM-dd").format(compareDate))) {
					return true;
				}
			}
			return false;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}
	
	/**
	 * 计算终止日期
	 * @param number 至多少岁值
	 * @param date
	 * @return
	 */
	private Calendar dateAdd(int number, Calendar date){
		date.set(Calendar.YEAR, date.get(Calendar.YEAR) + number);
		date.set(Calendar.DATE,date.get(Calendar.DATE) - 1);
		return date;
	}

	/**
	 * 
	 * @param startDate
	 *            保险起期
	 * @param limit
	 *            保险期限
	 * @param limitType
	 *            保险期限类型
	 * @return 保险止期
	 */
	public Timestamp checkYeMoDa_endAge(String startDate, String limit, String limitType, String recognizeeBirthday) {
		String cDate="";
		Timestamp ts = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cdS = Calendar.getInstance();
			int limitInt = Integer.parseInt(limit);
			cdS.setTime(sdf.parse(startDate));
			if ("A".equals(limitType.trim()) && StringUtil.isNotEmpty(recognizeeBirthday)) {
				// 保险期限为至XX岁时，需要改变保单终止日期(被保险人XX岁的保单生效对应日前一天)
				String[] startArr = sdf.format(cdS.getTime()).split("-");
				String[] birthArr = recognizeeBirthday.split("-");
				String periodFlagCode=new QueryBuilder("SELECT CodeValue FROM zdcode "
						+ "WHERE CodeType='specialEffDateFlag' and CodeValue=?", productId).executeString();
				if (Integer.valueOf(birthArr[1]) < Integer.valueOf(startArr[1])) {
					cdS.set(Calendar.YEAR, Integer.valueOf(birthArr[0]) + limitInt);
				} else if (Integer.valueOf(birthArr[1]) > Integer.valueOf(startArr[1])) {
					cdS.set(Calendar.YEAR, Integer.valueOf(birthArr[0]) + limitInt + 1);
				} else {
					if (Integer.valueOf(birthArr[2]) <= Integer.valueOf(startArr[2])) {
						cdS.set(Calendar.YEAR, Integer.valueOf(birthArr[0]) + limitInt);
					} else {
						cdS.set(Calendar.YEAR, Integer.valueOf(birthArr[0]) + limitInt + 1);
					}
				}

				if (periodFlagCode == null) {
					cdS.set(Calendar.MONTH, Integer.parseInt(startArr[1]) - 1);
					cdS.set(Calendar.DATE, Integer.parseInt(startArr[2]));
				}
				cdS.add(Calendar.DATE, -1);
				cDate = sdf.format(cdS.getTime());
			}

			String tDate = cDate + " 23:59:59";
			ts = new Timestamp(System.currentTimeMillis());
		    ts = Timestamp.valueOf(tDate);

			return ts;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ts;
		}
	}

	/**
	 * 处理投、被保人允许购买的身日起止日期
	 * 
	 * @Title: dealInsuredDate
	 * @return void 返回类型
	 */
	public void dealInsuredDate(String productID) {

		// 被保人
		QueryBuilder qb = new QueryBuilder(" select SectionAge from sdproduct where productid=? ");
		qb.add(productID);
		DataTable dt = qb.executeDataTable();
		String insuredAge = "0Y-80Y";
		if (dt != null && dt.getRowCount() >= 1) {
			if (StringUtil.isNotEmpty(dt.getString(0, "SectionAge"))) {
				insuredAge = dt.getString(0, "SectionAge");
			}
		}
		
		String insAge = com.sinosoft.sms.messageinterface.pubfun.PubFun.getInsureDate(insuredAge);
		if (insAge.indexOf("-") != -1) {
			this.insStartDate = insAge.split("-")[0];
			this.insEndDate = insAge.split("-")[1];
		}

		// 投保人
		qb = new QueryBuilder(" SELECT a.prop1,a.prop2,a.Memo FROM(SELECT prop1,prop2,Memo FROM zdcode WHERE codetype = 'InsuredDate' AND CodeValue = ? "
				+ "UNION SELECT prop1,prop2,Memo FROM zdcode WHERE codetype = 'InsuredDate' AND CodeValue = 'default') a LIMIT 1",productID);
		dt = qb.executeDataTable();
		if (dt != null && dt.getRowCount() >= 1) {
			this.appStartAge = dt.getInt(0, "prop1");
			this.appEndAge = dt.getInt(0, "prop2");
			this.appAgeTips = dt.getString(0, "Memo");
			this.appStartDate = String.valueOf(com.sinosoft.sms.messageinterface.pubfun.PubFun.getSumDay(appStartAge, "min"));
			this.appEndDate = String.valueOf(com.sinosoft.sms.messageinterface.pubfun.PubFun.getSumDay(appEndAge, "max"));
		} else {
			this.appStartDate = String.valueOf(com.sinosoft.sms.messageinterface.pubfun.PubFun.getSumDay(18, "min"));
			this.appEndDate = String.valueOf(com.sinosoft.sms.messageinterface.pubfun.PubFun.getSumDay(65, "max"));
		}
	}
	
	/**
	 * 详细页动态获取投保人最大值、最小值
	 * @return
	 */
	public String ajaxDealInsuredDate() {
		Map<String, Object> rMap = new HashMap<String, Object>();
		
		// 详细页投保人(适用于长期险本人投保)
		QueryBuilder qb = new QueryBuilder(" select SectionAge from sdproduct where productid=? ", productId);
		DataTable dt = qb.executeDataTable();
		String insuredAge = "0Y-80Y";
		if (dt != null && dt.getRowCount() >= 1) {
			if (StringUtil.isNotEmpty(dt.getString(0, "SectionAge"))) {
				insuredAge = dt.getString(0, "SectionAge");
			}
		}
		
		String insAge = com.sinosoft.sms.messageinterface.pubfun.PubFun.getInsureDate(insuredAge);
		if (insAge.indexOf("-") != -1) {
			rMap.put("status", "success");
			String insStartDate = insAge.split("-")[0];
			String insEndDate = insAge.split("-")[1];
			
			// T+1承保，最大年龄值需要减一天
			insEndDate = new Integer(Integer.parseInt(insEndDate) - 1).toString();
			String minDate = com.sinosoft.sms.messageinterface.pubfun.PubFun.calSDate(PubFun.getCurrentDate(), -Integer.parseInt(insStartDate), "D");
			
			rMap.put("insStartDate", insStartDate);
			rMap.put("insEndDate", insEndDate);
			rMap.put("minDate", minDate);
		} else {
			rMap.put("status", "fail");
			rMap.put("message", "投保人年龄范围获取失败！");
		}
		JSONObject jsonObject = JSONObject.fromObject(rMap);
		return ajax(jsonObject.toString(), "text/html");
	}

	/**
	 * CPS再次购买功能
	 * 
	 * @return
	 */
	public String againBuy () {
		if (StringUtil.isNotEmpty(againBuyOrderSn)) {
			Map<String, Object> data = saveAgainBuyOrder("cps_swpt");
			if ("Err".equals(data.get("Flag"))) {
				addActionError((String)data.get("Msg"));
				return ERROR;
			}
			String result = againBuyShow((String)data.get("orderSn"));
			return result;
		}
		return ERROR;
	}
	
	public String renewalInsure() {
		Map<String, Object> rMap = new HashMap<String, Object>();
		String nKID = StringUtil.md5Hex(PubFun.getKeyValue() + againBuyOrderSn);
		if (!nKID.equals(KID)) {
			rMap.put("status", "fail");
			rMap.put("message", "订单号非法！");
		} else {
			Map<String, Object> data = saveAgainBuyOrder("wj");
			if ("Err".equals(data.get("Flag"))) {
				rMap.put("status", "fail");
				rMap.put("message", (String)data.get("Msg"));
			} else {
				rMap.put("status", "success");
				String orderSn = (String)data.get("orderSn");
				rMap.put("orderSn", orderSn);
				rMap.put("KID", StringUtil.md5Hex(PubFun.getKeyValue() + orderSn));
			}
		}
		
		JSONObject jsonObject = JSONObject.fromObject(rMap);
		return ajax(jsonObject.toString(), "text/html");
	}

	/**
	 * 保存再次购买订单信息
	 */
	public Map<String, Object> saveAgainBuyOrder(String channelSn) {

		Map<String, Object> tData = new HashMap<String, Object>();
		try {
			mLMap = new LinkedHashMap<Object, String>();
			
			if (StringUtil.isEmpty(againBuyOrderSn)) { 
				tData.put("Flag", "Err");//
				tData.put("Msg", "订单号不存在！");// 错误信息
				logger.info("再次购买，订单号不存在！");
				return tData;
			}
			
			// 保存订单信息处理类
			CpsAgainBuyOrderInfoDeal infoDeal = new CpsAgainBuyOrderInfoDeal();
			// 订单信息
			SDOrder oldSDOrder = sdorderService.getOrderByOrderSn(againBuyOrderSn);
			// 获取产品ID
			//QueryBuilder qb = new QueryBuilder("select productId from sdinformation where ordersn=?", againBuyOrderSn);
			//String productId = qb.executeString();
			// 订单详细信息
			SDInformation oldSDInformation = sdinformationService.getByOrderId(oldSDOrder.getId());
			
			// 判断产品下架
			String sql = "select LimitCount,Occup,SectionAge,IsPublish,ComplicatedFlag from sdproduct where productid=?";
			String[] sqltemp = {oldSDInformation.getProductId()};
			GetDBdata db = new GetDBdata();
			List<HashMap<String, String>> sdproduct = db.query(sql,sqltemp);
			if (sdproduct == null || sdproduct.size() != 1) {
				tData.put("Flag", "Err");//
				tData.put("Msg", "该订单中产品" + oldSDInformation.getProductName() + "已不存在！");// 错误信息
				logger.warn("该订单中产品{}已不存在！", oldSDInformation.getProductName());
				return tData;
			}
			HashMap<String, String> product = sdproduct.get(0);
			String IsPublish = product.get("IsPublish");
			if (StringUtil.isEmpty(oldSDOrder.getChannelsn()) || oldSDOrder.getChannelsn().indexOf("tb") < 0) {
				if (StringUtil.isEmpty(IsPublish) || "N".equals(IsPublish)) {
					tData.put("Flag", "Err");//
					tData.put("Msg", "该订单中产品" + oldSDInformation.getProductName() + "已经下架！");// 错误信息
					logger.error("该订单中产品{}已经下架！", oldSDInformation.getProductName());
					return tData;
				}
			}
			String complicatedFlag = product.get("ComplicatedFlag");
			// 保存投保人信息
			SDInformationAppnt oldSDInformationAppnt = sdinformationAppntService.getByOrParentId(oldSDInformation.getId());
			// 被保人信息
			List<SDInformationInsured> informationInsuredList = sdinformationInsuredService.getListByOrParentId(oldSDInformation.getId());
			// 保单表
			//List<SDInformationRiskType> informationRiskTypeList = sdinformationRiskTypeService.getInformationRiskTypeByOrderSn(againBuyOrderSn);
			// 积分
			PointExchangeInfo pointExchangeInfo = infoDeal.selectPointExchangeInfo(againBuyOrderSn);
			// 健康告知
			List<SDInsuredHealth> insuredHealthList = infoDeal.selectSDInsuredHealthList(againBuyOrderSn);
			// 责任信息存储
			List<SDInformationDuty> informationDutyList = infoDeal.selectSDInformationDutyList(againBuyOrderSn);
			
			// 现产品投保要素与被保人关联
			List<SDInformationInsuredElements> informationInsuredElements = infoDeal.selectInformationInsuredElements(againBuyOrderSn);
			// 保存财产信息
			List<SDInformationProperty> informationPropertyList = new ArrayList<SDInformationProperty>();
			for (SDInformationInsured insured : informationInsuredList) {
				SDInformationProperty informationProperty = sdinformationPropertyService.getByInsuredId(insured.getId());
				if (null != informationProperty) {
					informationPropertyList.add(informationProperty);
				}
			}
			
			// 保存订单项
			SDOrderItem sdOrderItem = infoDeal.selectSDOrderItem(againBuyOrderSn, oldSDOrder);
			
			// 保存订单表
			SDOrder newSDorder = infoDeal.dealSDorder(oldSDOrder, channelSn);

			tData.put("orderSn", newSDorder.getOrderSn());
			
			mLMap = new LinkedHashMap<Object, String>();
			
			mLMap.put(newSDorder, "insert");
			// 保存订单详细表
			SDInformation newInformation = infoDeal.dealSDInformation(oldSDInformation, newSDorder);
			if ("wj".equals(channelSn)) {
				Date now = new Date();
				QueryBuilder qb = new QueryBuilder("SELECT memo FROM zdcode WHERE codetype='ProductPeriod' AND codevalue=?");
				qb.add(newInformation.getProductId());
				DataTable dt = qb.executeDataTable();
				int num = 1;
				if (dt.getRowCount() > 0) {
					num = Integer.parseInt(dt.get(0, 0).toString());
				} else {
					String stp = productPeriodService.getStartPeriod(newInformation.getInsuranceCompany(), newInformation.getProductId());
					if (stp != null && !"".equals(stp)) {
						num = Integer.parseInt(stp);
					} else {
						num = 1;
					}
				}
				
				
				// 取得现在可购买的最近的起保日期
				Date date = DateUtil.parse(DateUtil.toString(DateUtil.addDay(now, num)) + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
				if ("L".equals(oldSDInformation.getEnsureLimitType())) {
					newInformation.setStartDate(date);
					newInformation.setEndDate(oldSDInformation.getEndDate());
				} else {
					// 已过期订单 新订单生效起期为可承保的最近日期
					if (oldSDInformation.getEndDate().compareTo(now) <= 0) {
						newInformation.setStartDate(date);
						
					} else {
						if (date.compareTo(oldSDInformation.getEndDate()) >= 0) {
							newInformation.setStartDate(date);
						} else {
							newInformation.setStartDate(DateUtil.parse(DateUtil.toString(DateUtil.addDay(oldSDInformation.getEndDate(), 1)) + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
						}
					}
					
					// 算起保止期
					if ("A".equals(oldSDInformation.getEnsureLimitType())) {
						newInformation.setEndDate(oldSDInformation.getEndDate());
					} else {
						String endDate = PubFun.getEvaliDate(newInformation.getStartDate(), oldSDInformation.getEnsureLimit(), oldSDInformation.getEnsureLimitType());
						if (StringUtil.isNotEmpty(endDate)) {
							newInformation.setEndDate(DateUtil.parse(endDate , "yyyy-MM-dd HH:mm:ss"));
						}
					}
				}
				
				//判断起保日期是否在有效选择期内,如果不在选择有效期内则设置为空让用户选择
				if(!verifyInsurStartDate(newInformation.getInsuranceCompany(),newInformation.getProductId(),
						newInformation.getStartDate())){
					newInformation.setStartDate(null);
					newInformation.setEndDate(null);
				}
				
				
			}
			mLMap.put(newInformation, "insert");

			// 保存投保人信息
			SDInformationAppnt newInformationAppnt = infoDeal.dealSDInformationAppnt(oldSDInformationAppnt, newInformation);
			mLMap.put(newInformationAppnt, "insert");
			// 被保人信息
			List<SDInformationInsured> newInformationInsuredList = infoDeal.dealSDInformationInsured(informationInsuredList, newInformation);
			mLMap.put(newInformationInsuredList, "insert");

			// 保单表
			Map<String, Object> insureInfoMap = new HashMap<String, Object>();
			insureInfoMap = getInsureInfoMap(newInformation, insureInfoMap);
			List<SDInformationRiskType> newInformationRiskTypeList = infoDeal.dealSDInformationRiskTypeList(newSDorder, newInformation, newInformationAppnt, newInformationInsuredList, insureInfoMap);
			if (null != newInformationRiskTypeList && newInformationRiskTypeList.size() > 0) {
				mLMap.put(newInformationRiskTypeList, "insert");
			}
			mSDInformationRiskTypeSet.clear();
			for (SDInformationRiskType risktype : newInformationRiskTypeList) {
				mSDInformationRiskTypeSet.add(risktype);
			}
			newSDorder.setSdinformationrisktypeSet(mSDInformationRiskTypeSet);

			// 订单项
			List<SDOrderItemOth> orderItemOthList = infoDeal.dealSDOrderItemOthList(newInformationInsuredList, newSDorder, newInformation);
			if (null != orderItemOthList && orderItemOthList.size() > 0) {
				mLMap.put(orderItemOthList, "insert");
			}
			
			// 积分
			GiftClassify gift = giftClassifyService.getGiftClassifyByProductId(newInformation.getProductId());
			if (null != pointExchangeInfo) {
				pointExchangeInfo = infoDeal.dealPointExchangeInfo(pointExchangeInfo, newSDorder, newInformation, gift);
				mLMap.put(pointExchangeInfo, "insert");
			}
			
			// 健康告知
			if (null != insuredHealthList && insuredHealthList.size() > 0) {
				insuredHealthList = infoDeal.dealSDInsuredHealthList(insuredHealthList, newSDorder, newInformationInsuredList, newInformation);
				mLMap.put(insuredHealthList, "insert");
			}

			// 责任信息存储
			if (null != informationDutyList && informationDutyList.size() > 0) {
				informationDutyList = infoDeal.dealSDInformationDutyList(informationDutyList, newSDorder, newInformation);
				mLMap.put(informationDutyList, "insert");
				if ("Y".equals(complicatedFlag)) {
					List<SDInformationDutyTemp> informationDutyTempList = infoDeal.dealSDInformationDutyTempList(informationDutyList);
					mLMap.put(informationDutyTempList, "insert");
				}
			}
			
			// 现产品投保要素与被保人关联
			if (null != informationInsuredElements && informationInsuredElements.size() > 0) {
				informationInsuredElements = infoDeal.dealInformationInsuredElements(informationInsuredElements, newSDorder, newInformationInsuredList, newInformation);
				mLMap.put(informationInsuredElements, "insert");
			}
			
			// 保存财产信息
			if (null != informationPropertyList && informationPropertyList.size() > 0) {
				List<SDInformationProperty> newInformationPropertyList = infoDeal.dealSDInformationPropertyList(informationPropertyList, newSDorder, newInformationInsuredList);
				mLMap.put(newInformationPropertyList, "insert");
			}
			
			// 保存订单项
			sdOrderItem = infoDeal.dealSDOrderItem(sdOrderItem, newSDorder);
			if (sdOrderItem != null) {
				//add by wangej 20160217 再次购买时，保存typeFlag
				if(StringUtil.isNotEmpty(typeFlag)){
					sdOrderItem.setTypeFlag(typeFlag);
				}
				mLMap.put(sdOrderItem, "insert");
			}

			// 保存所有信息
			if (!this.mDealDataService.saveAll(mLMap)) {
				tData.put("Flag", "Err");
				return tData;
			}

			if ("cps".equals(channelSn) || (StringUtil.isNotEmpty(newSDorder.getChannelsn()) && newSDorder.getChannelsn().indexOf("cps") >= 0)) {
				QueryBuilder qb = new QueryBuilder(
						"select cid, wi, os from cps where `on` = ?",
						againBuyOrderSn);

				DataTable dt = qb.executeDataTable();
				String cpsUserId = "";
				String partners_uid = "";
				String tCpsUserource = "";
				if (dt.getRowCount() > 0) {
					cpsUserId = dt.getString(0, 0);
					partners_uid = dt.getString(0, 1);
					tCpsUserource = dt.getString(0, 2);
				}
				
				recordCPS(cpsUserId,partners_uid,"pc", tCpsUserource, newSDorder.getOrderSn(), newInformation.getProductName(), newSDorder.getTotalAmount().toString());
			}
			
			tData.put("Flag", "Suc");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			tData.put("Flag", "Err");
			tData.put("Msg", "生成新订单错误！");
		}
		return tData;
	}
	
	

	/**
	 * 商务联盟再次购买--调用
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private String againBuyShow (String orderSn) {
		try {
			// 订单信息
			sdorder = sdorderService.getOrderByOrderSn(orderSn);
			
			sdorderitem = sdorder.getSdorderitem();
			if (StringUtil.isEmpty(typeFlag)) {
				typeFlag = sdorderitem.getTypeFlag();
			}
			if (StringUtil.isEmpty(channelCode)) {
				channelCode = sdorderitem.getChannelCode();
			}
			//modified by yuzj for 淘宝蚂蚁的处理 @20160701 begin
			if("tb_ht".equals(sdorder.getChannelsn())||"tb_my".equals(sdorder.getChannelsn())){
				sourceFlag = "tb";
			}else if("qunar".equals(sdorder.getChannelsn())){
				sourceFlag = "qunar";
			}
			//modified by yuzj for 淘宝蚂蚁的处理 @20160701 end
			if (StringUtil.isNotEmpty(sdorder.getDiscountRates())) {
				activityFlag = "1";
			} else {
				activityFlag = "0";
			}
			
			userCode = sdorderitem.getChannelAgentCode();
			if (!this.checkPayStatus(sdorder.getId())) {
				addActionError("这个订单已经支付过了，不要重复支付哟！！");
				logger.error("buyNowUpdate-订单已经支付过了，不要重复支付哟！！id:" + sdorder.getId() + " ordersn" + sdorder.getOrderSn());
				return ERROR;
			}
			// 订单详细信息
			Set<SDInformation> sdinformationSet = sdorder.getSdinformationSet();
			for (SDInformation infmn : sdinformationSet) {
				sdinformation = infmn;
			}
			this.supplierCode2 = sdinformation.getInsuranceCompany();
			productId = sdinformation.getProductId();
			init(sdinformation.getInsuranceCompany());

			complicatedFlag = new QueryBuilder("select complicatedFlag from sdproduct where productId=?", productId).executeString();
			dutyTempSerials = new QueryBuilder("select DutySerials from sdinformationdutytemp where ordersn=?", orderSn).executeString();
			textAge = sdinformation.getTextAge();
			dealInsuredDate(productId);
			// 被保人
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Map<String, String> keyMap = new HashMap<String, String>();
			Map<String, String> premMap = new HashMap<String, String>();
			for (SDInformationInsured sdinf : sdinformation.getSdinformationinsuredSet()) {
				if (("1".equals(sdinf.getRecognizeeOperate()) && "rid_me".equals(sdinf.getMulInsuredFlag())) || "4".equals(sdinf.getRecognizeeOperate())) {
					if (sdinf.getFlightTime() != null && !"".equals(sdinf.getFlightTime())) {
						sdinf.setFlightTime(sdf.format(sdf.parse(sdinf.getFlightTime())));
					}
					this.insuredMulCount = sdinformation.getSdinformationinsuredSet().size();
					this.sdinformationinsured = sdinf;
					this.recognizeeOperate = sdinformationinsured.getRecognizeeOperate();
					this.mulInsuredFlag = sdinformationinsured.getMulInsuredFlag();
					if (sdinformationinsured != null) {
						break;
					}
				} else if ("3".equals(sdinf.getRecognizeeOperate()) && "rid_td".equals(sdinf.getMulInsuredFlag())) {
					if (premMap.containsKey(sdinf.getRecognizeePrem())) {
						sdinf.setDiscountPrice(premMap.get(sdinf.getRecognizeePrem()));
					} else {
						// 商务联盟无会员等级概念
						sdinf.setDiscountPrice(ActivityCalculate.ProductCalculate(productId,sdorder.getOrderSn(),sdinf.getRecognizeePrem(), sdorder.getChannelsn(), ""));
						premMap.put(sdinf.getRecognizeePrem(), sdinf.getDiscountPrice());
					}
					this.sdinsuredList.add(sdinf);
					if (sdinf.getFlightTime() != null && !"".equals(sdinf.getFlightTime())) {
						sdinf.setFlightTime(sdf.format(sdf.parse(sdinf.getFlightTime())));
					}
				} else {
					if (sdinf.getFlightTime() != null && !"".equals(sdinf.getFlightTime())) {
						sdinf.setFlightTime(sdf.format(sdf.parse(sdinf.getFlightTime())));
					}
					if (!"Y".equals(keyMap.get(sdinf.getRecognizeeKey()))) {
						this.sdinformationinsuredList.add(sdinf);
					}
					keyMap.put(sdinf.getRecognizeeKey(), "Y");
					SDInformationProperty sdp = sdinf.getSdinformationproperty();
					if (sdp != null) {
						this.sdinformationpropertyList.add(sdp);
					}
				}

			}

			if (sdinformationinsuredList != null && sdinformationinsuredList.size() >= 1) {
				this.insuredActCount = sdinformation.getSdinformationinsuredSet().size();// 采用未经处理的被保人信息获取“份数”
				insuredCount = sdinformationinsuredList.size();// 人数
				recognizeeOperate = sdinformationinsuredList.get(0).getRecognizeeOperate();
				this.mulInsuredFlag = sdinformationinsuredList.get(0).getMulInsuredFlag();
			} else if (this.sdinsuredList != null && this.sdinsuredList.size() >= 1) {
				this.insuredImpCount = sdinsuredList.size();
				recognizeeOperate = sdinsuredList.get(0).getRecognizeeOperate();
				this.mulInsuredFlag = sdinsuredList.get(0).getMulInsuredFlag();
			}
			if (insuredMulCount <= 0) {
				insuredMulCount = 1;
			}
			if (insuredCount <= 0) {
				insuredCount = 1;
			}
			if (insuredImpCount <= 0) {
				insuredImpCount = 0;
			}
			// 被保人
			List<SDInformationAppnt> sdapp = new ArrayList<SDInformationAppnt>(sdinformation.getSdinformationappntSet());
			if (sdapp != null && sdapp.size() > 0) {
				this.sdinformationAppnt = sdapp.get(0);
			}
			JdbcTemplateData jtd = new JdbcTemplateData();
			SDInformationInsured t = new SDInformationInsured();
			if (sdinformationinsured != null && !"".equals(sdinformationinsured.getId()) && sdinformationinsured.getId() != null) {
				t = sdinformationinsured;
			} else {
				for (SDInformationInsured s : sdinformationinsuredList) {
					if (s != null && !"".equals(s.getId()) && s.getId() != null) {
						t = s;
					}
				}
				for (SDInformationInsured s : this.sdinsuredList) {
					if (s != null && !"".equals(s.getId()) && s.getId() != null) {
						t = s;
					}
				}

			}
			if (t.getDestinationCountry() != null && !"".equals(t.getDestinationCountry())) {
				String[] destinate = t.getDestinationCountry().split(",");
				if (destinate != null && destinate.length > 0) {
					int kk = 0;
					for (kk = 0; kk < destinate.length; kk++) {
						int ttt = new QueryBuilder(" SELECT COUNT(1) FROM dictionary WHERE codetype='CountryCode' AND productId = ? ",sdinformation.getProductId()).executeInt();
						String wherepart = " and insuranceCode = '"+sdinformation.getInsuranceCompany()+"'";
						if(ttt>=1){
							wherepart = " and productId = '"+sdinformation.getProductId()+"'";
						}
						String sqlCountryCodess = "select codename from dictionary where codetype=? and codevalue = ? "+wherepart;
						String[] sqlCountryCodesstemp = { "CountryCode", destinate[kk].trim() };
						List<Map> dlist = new ArrayList<Map>();
						dlist = jtd.obtainData(sqlCountryCodess, sqlCountryCodesstemp);
						Iterator<Map> it = dlist.iterator();
						while (it.hasNext()) {
							Map map = it.next();
							detisnateList.add((String) map.get("codeName"));
						}
					}
				}
				logger.info("选择大小：{}", detisnateList.size());
			}
			// productId = order.getProductId();
			Map<String, Object> paramter = new HashMap<String, Object>();
			paramter = sdorderService.getProductInformation(productId, "N", channelCode);// 产品ID
			String[] BaseInformation = new String[14];
			BaseInformation = (String[]) paramter.get("baseInformation");// 产品基础数据
			riskType = BaseInformation[7].toString();// 产品小类
			setSession("baseInformation", BaseInformation);
			// 产品投保要素
			riskAppFactior = (List<OrderRiskAppFactor>) paramter.get("riskAppFactor");
			if (riskAppFactior.size() > 0) {
				QueryBuilder qb = new QueryBuilder("SELECT ensure FROM sdinformation WHERE ordersn=?");
				qb.add(orderSn);
				String ensure = qb.executeString();
				protectionPeriodTy = ensure.trim().substring(ensure.trim().length() - 1, ensure.trim().length());
				protectionPeriodLast = ensure.trim().substring(ensure.trim().indexOf("-") + 1, ensure.trim().length() - 1);
				protectionPeriodFlag = "true";
			}

			// 产品责任要素
			dutyFactor = (List<OrderDutyFactor>) paramter.get("dutyFactor");
			Map<String, Object> tData = new HashMap<String, Object>();
			if (BaseInformation == null || BaseInformation.length <= 0 || riskAppFactior == null || riskAppFactior.size() <= 0 || dutyFactor == null || dutyFactor.size() <= 0) {
				try {
					paramter = sdorderService.getProductInformation(productId, "N", channelCode);// 产品ID
				} catch (Exception e) {
					tData.put("Flag", "Err");// 总保费
					tData.put("Msg", "与产品中心交互失败①！");// 错误信息
					JSONObject jsonObject = JSONObject.fromObject(tData);
					logger.error("与产品中心交互失败①,产品编码" + productId+e.getMessage(),e);
					return ajax(jsonObject.toString(), "text/html");
				}
				BaseInformation = (String[]) paramter.get("baseInformation");// 产品基础数据
				riskAppFactior = (List<OrderRiskAppFactor>) paramter.get("riskAppFactor");
				dutyFactor = (List<OrderDutyFactor>) paramter.get("dutyFactor");
			}
			// 保额
			plan = sdinformation.getPlanCode();
			this.getInsuredAmnt(dutyFactor, plan);
			if (dutyFactor.size() > 0) {
				setSession("dutyFactor", dutyFactor);
			}
			this.dataBuild();
			Member memberLogin = getLoginMember();
			if (memberLogin != null) {
				sdorder.setMemberId(getLoginMember().getId());
				loginFlag = "true";
				// 根据是否登陆判断是否显示积分抵值信息
				map_pointinfo=new HashMap<String,String>();
				Map map=new HashMap();
				List list=new ArrayList();
				list.add(productId);
				map.put("ProductList",list);
				try {
					Map result=new PointsCalculate().pointsManage(IntegralConstant.POINT_PRODUCT, "", map);
					DataTable dt_result=(DataTable) result.get(IntegralConstant.DATA);
					if(dt_result.getRowCount()>0){
						String BuyPoints=dt_result.getString(0,"BuyPoints");
						//String GivePoints=dt_result.getString(0,"GivePoints");
						BigDecimal TotalAmount=new BigDecimal(sdorder.getPayPrice());
						String PointScalerUnit = Config.getConfigValue("PointScalerUnit");
						java.text.DecimalFormat myformat = new java.text.DecimalFormat("0.000");
						String str = myformat.format(new BigDecimal(Double
								.parseDouble(String.valueOf(TotalAmount))
								* Double.parseDouble(BuyPoints)
								* Double.parseDouble(PointScalerUnit)));
						BigDecimal points=new BigDecimal(str).setScale(0, BigDecimal.ROUND_UP);
						BigDecimal CurrentValidatePoint=new BigDecimal(memberLogin.getCurrentValidatePoint());
						if(points.compareTo(CurrentValidatePoint)>0){
							map_pointinfo.put("points",String.valueOf(CurrentValidatePoint));
							BigDecimal pointValue = CurrentValidatePoint.divide(new BigDecimal(PointScalerUnit), 1,BigDecimal.ROUND_UP);
							map_pointinfo.put("pointsprice",String.valueOf(pointValue));
						}else{
							map_pointinfo.put("points",String.valueOf(points));
							BigDecimal pointValue = points.divide(new BigDecimal(PointScalerUnit), 1,BigDecimal.ROUND_UP);
							map_pointinfo.put("pointsprice", String.valueOf(pointValue));
						}
					}else{
						map_pointinfo.put("points", "");
						map_pointinfo.put("pointsprice", "");
					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			} else {
				loginFlag = "false";
			}
			this.directPayBankInfo = directPayBankInfoService.getByOrderSn(orderSn);
			if  (directPayBankInfo == null) {
				directPayBankInfo = new DirectPayBankInfo();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionError("矮油，出了点小状况②。再试一次吧！");
			return ERROR;
		}

		String productID = productId;// A01 产品
		// 模块路径
		PageModule pm = new PageModule();
		pageModuleList = pm.getPageModuleURL(productID);
		dealExcelModule(productID);
		if (StringUtil.isNotEmpty(productExcelTemp)) {
			productExcelFlag = "Y";
		}
		impValadate = "Y";
		// add by cuishigang 投保人信息快速录入 start
		quickQueryAppnt();
		// add by cuishigang 投保人信息快速录入 end
		// 得到产品限购份数
		dealLimitCount(sdinformation);
		// 默认产品无次日生效，查询zdcode表中被要求次日生效的产品记录
		effectiveNextDayFlag = "false";
		QueryBuilder qb = new QueryBuilder("SELECT memo FROM zdcode WHERE codetype='ProductPeriod' AND codevalue=?");
		qb.add(productId);
		DataTable dt = qb.executeDataTable();
		if (dt.getRowCount() > 0) {
			effectiveNextDayFlag = "true";
			int num = Integer.parseInt(dt.get(0, 0).toString());
			startPeriod = num;
			Date date = DateUtil.addDay(new Date(), num);
			effectiveDayValue = DateUtil.toString(date);
		}
		// 取得活动信息
		getActivityInfo(productId, sdorder.getChannelsn());
		// 获得购买送积分信息
		getSendPoint(orderSn, productId, sdorder.getMemberId());
		
		String healthyPage = healthyInput(sdorder.getOrderSn(), status);
		if (StringUtil.isNotEmpty(healthyPage)) {
			return healthyPage;
		}
		status = "update";
		// 判断是否为购物车，即显示“保存”或者“下一步”
		if ("ShopCart".equals(orderFlag)) {
			shopcarflag = "true";
		}

		if (StringUtil.isNotEmpty(channelCode)) {
			jrhsURL = sdorderService.getJRHSURLByConfig(channelCode);
		}
		sdinformation.setStartDate(null);
		sdinformation.setEndDate(null);
		String templateURL = "inspageone";
		return templateURL;
	}
	
	/**
	 * 判断起保日期是否在有效选择期内
	 * @param comCode 保险公司编码
	 * @param productId 产品id
	 * @param now 当前时间
	 * @param startDate 保单起始日期
	 * @return true:在有效期内,false:不在有效期内
	 */
	public boolean verifyInsurStartDate(String comCode,String productId,Date startDate){
		if(startDate != null){
			Integer end = null;
			String sep = null;
			if(StringUtil.isEmpty(sdorder.getRenewalId())){
				sep = productPeriodService.getEndPeriod(comCode, productId);
			}
			if (sep != null && !"".equals(sep)) {
				end = Integer.parseInt(sep);
			} else {
				end = 700;
			}
			Date dateEndPeriod = DateUtil.parse(DateUtil.toString(DateUtil.addDay(new Date(), end)) + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
			if(dateEndPeriod.compareTo(startDate) == -1){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 被保人点击职业类别表查看职业类别
	 * @return
	 */
	public String showOccupations(){
		//职业三级列表数据
		threeLevelOccupations = occupationService.findThreeLevelOccupation(supplierCode2, productId, OccupLevel);
		//职业附件下载地址
		QueryBuilder qb = new QueryBuilder("select path,FileName from zcattachment where name like '%职业%' and Prop2 = ?",supplierCode2);
		DataTable dt = qb.executeDataTable();
		if(dt.getRowCount() != 0){
			occDownAddr = Config.getFrontServerContextPath()+"/"+dt.getString(0, "path")+dt.getString(0, "FileName");
		}
		return "occupations";
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

	public List<Dictionary> getBnfRelationList() {
		return bnfRelationList;
	}

	public void setBnfRelationList(List<Dictionary> bnfRelationList) {
		this.bnfRelationList = bnfRelationList;
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
	
	public List<Dictionary> getBankList() {
		return bankList;
	}

	public void setBankList(List<Dictionary> bankList) {
		this.bankList = bankList;
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

	public List<List<InsuredShow>> getShowInsureds() {
		return showInsureds;
	}

	public void setShowInsureds(List<List<InsuredShow>> showInsureds) {
		this.showInsureds = showInsureds;
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

	public ProductConfigService getProductConfigService() {
		return productConfigService;
	}

	public void setProductConfigService(ProductConfigService productConfigService) {
		this.productConfigService = productConfigService;
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

	public HealthyInfoService getHealthyInfoService() {
		return healthyInfoService;
	}

	public void setHealthyInfoService(HealthyInfoService healthyInfoService) {
		this.healthyInfoService = healthyInfoService;
	}

	public InsuredHealthService getInsuredHealthService() {
		return insuredHealthService;
	}

	public void setInsuredHealthService(InsuredHealthService insuredHealthService) {
		this.insuredHealthService = insuredHealthService;
	}

	public ProductPeriodService getProductPeriodService() {
		return productPeriodService;
	}

	public void setProductPeriodService(ProductPeriodService productPeriodService) {
		this.productPeriodService = productPeriodService;
	}

	public ZdrecordCpsService getZdrecordCpsService() {
		return zdrecordCpsService;
	}

	public void setZdrecordCpsService(ZdrecordCpsService zdrecordCpsService) {
		this.zdrecordCpsService = zdrecordCpsService;
	}

	public List<SDInformationInsured> getSdinformationinsuredList() {
		return sdinformationinsuredList;
	}

	public void setSdinformationinsuredList(List<SDInformationInsured> sdinformationinsuredList) {
		this.sdinformationinsuredList = sdinformationinsuredList;
	}
	
	public List<SDInformationBnf> getSdinformationbnfList() {
		return sdinformationbnfList;
	}

	public void setSdinformationbnfList(List<SDInformationBnf> sdinformationbnfList) {
		this.sdinformationbnfList = sdinformationbnfList;
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

	public List<List<InsuredShow>> getShowAppnts() {
		return showAppnts;
	}

	public void setShowAppnts(List<List<InsuredShow>> showAppnts) {
		this.showAppnts = showAppnts;
	}

	public List<List<InsuredShow>> getShowPeriods() {
		return showPeriods;
	}

	public void setShowPeriods(List<List<InsuredShow>> showPeriods) {
		this.showPeriods = showPeriods;
	}

	public DirectPayBankInfo getDirectPayBankInfo() {
		return directPayBankInfo;
	}

	public void setDirectPayBankInfo(DirectPayBankInfo directPayBankInfo) {
		this.directPayBankInfo = directPayBankInfo;
	}

	public String getInsuredToCountry() {
		return insuredToCountry;
	}

	public void setInsuredToCountry(String insuredToCountry) {
		this.insuredToCountry = insuredToCountry;
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
	
	public String getBenefitOperate() {
		return benefitOperate;
	}

	public void setBenefitOperate(String benefitOperate) {
		this.benefitOperate = benefitOperate;
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

	public QuestionPaper getQuestionPaper() {
		return questionPaper;
	}

	public void setQuestionPaper(QuestionPaper questionPaper) {
		this.questionPaper = questionPaper;
	}

	public List<QuestionPaper> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<QuestionPaper> questionList) {
		this.questionList = questionList;
	}

	public Boolean getQuesFlag() {
		return quesFlag;
	}

	public void setQuesFlag(Boolean quesFlag) {
		this.quesFlag = quesFlag;
	}

	public String getMainAmnt() {
		return mainAmnt;
	}

	public void setMainAmnt(String mainAmnt) {
		this.mainAmnt = mainAmnt;
	}

	public String getSupplierCode2() {
		return supplierCode2;
	}

	public void setSupplierCode2(String supplierCode2) {
		this.supplierCode2 = supplierCode2;
	}

	public Map<String, Map<String, List<Dictionary>>> getMapCountryCodeList() {
		return mapCountryCodeList;
	}

	public void setMapCountryCodeList(Map<String, Map<String, List<Dictionary>>> mapCountryCodeList) {
		this.mapCountryCodeList = mapCountryCodeList;
	}

	public String getOrderFlag() {
		return orderFlag;
	}

	public void setOrderFlag(String orderFlag) {
		this.orderFlag = orderFlag;
	}

	public List<SDInformationProperty> getSdinformationpropertyList() {
		return sdinformationpropertyList;
	}

	public void setSdinformationpropertyList(List<SDInformationProperty> sdinformationpropertyList) {
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

	public void setPropertyToRecognizeeList(List<Dictionary> propertyToRecognizeeList) {
		this.propertyToRecognizeeList = propertyToRecognizeeList;
	}

	public String getTypeFlag() {
		return typeFlag;
	}

	public void setTypeFlag(String typeFlag) {
		this.typeFlag = typeFlag;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getJrhsURL() {
		return jrhsURL;
	}

	public void setJrhsURL(String jrhsURL) {
		this.jrhsURL = jrhsURL;
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

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
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

	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public int getLimitCount() {
		return limitCount;
	}

	public void setLimitCount(int limitCount) {
		this.limitCount = limitCount;
	}

	public int getInsuredMulCount() {
		return insuredMulCount;
	}

	public void setInsuredMulCount(int insuredMulCount) {
		this.insuredMulCount = insuredMulCount;
	}

	public int getInsuredActCount() {
		return insuredActCount;
	}

	public void setInsuredActCount(int insuredActCount) {
		this.insuredActCount = insuredActCount;
	}

	public int getInsuredResultCount() {
		return insuredResultCount;
	}

	public void setInsuredResultCount(int insuredResultCount) {
		this.insuredResultCount = insuredResultCount;
	}

	public String getCallBackAmount() {
		return callBackAmount;
	}

	public void setCallBackAmount(String callBackAmount) {
		this.callBackAmount = callBackAmount;
	}

	public String getEffectiveNextDayFlag() {
		return effectiveNextDayFlag;
	}

	public void setEffectiveNextDayFlag(String effectiveNextDayFlag) {
		this.effectiveNextDayFlag = effectiveNextDayFlag;
	}

	public String getEffectiveDayValue() {
		return effectiveDayValue;
	}

	public void setEffectiveDayValue(String effectiveDayValue) {
		this.effectiveDayValue = effectiveDayValue;
	}

	public List<SDRelationRecognizee> getRecognizeeList() {
		return recognizeeList;
	}

	public void setRecognizeeList(List<SDRelationRecognizee> recognizeeList) {
		this.recognizeeList = recognizeeList;
	}

	public String getReinsuredName() {
		return reinsuredName;
	}

	public void setReinsuredName(String reinsuredName) {
		this.reinsuredName = reinsuredName;
	}

	public String getPaySn() {
		return paySn;
	}

	public void setPaySn(String paySn) {
		this.paySn = paySn;
	}

	public List<ActivityInfo> getMjactivitylist() {
		return mjactivitylist;
	}

	public void setMjactivitylist(List<ActivityInfo> mjactivitylist) {
		this.mjactivitylist = mjactivitylist;
	}

	public String getInsuranceDec() {
		return insuranceDec;
	}

	public void setInsuranceDec(String insuranceDec) {
		this.insuranceDec = insuranceDec;
	}

	public String getShopcarflag() {
		return shopcarflag;
	}

	public void setShopcarflag(String shopcarflag) {
		this.shopcarflag = shopcarflag;
	}

	public List<Dictionary> getSecurityList() {
		return securityList;
	}

	public void setSecurityList(List<Dictionary> securityList) {
		this.securityList = securityList;
	}

	public String getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStartPerid() {
		return startPerid;
	}

	public void setStartPerid(String startPerid) {
		this.startPerid = startPerid;
	}

	public String getLastUrl() {
		return LastUrl;
	}

	public void setLastUrl(String lastUrl) {
		LastUrl = lastUrl;
	}

	public String getM_startDate() {
		return m_startDate;
	}

	public void setM_startDate(String m_startDate) {
		this.m_startDate = m_startDate;
	}

	public String getM_endDate() {
		return m_endDate;
	}

	public void setM_endDate(String m_endDate) {
		this.m_endDate = m_endDate;
	}

	public String getAppStartDate() {
		return appStartDate;
	}

	public void setAppStartDate(String appStartDate) {
		this.appStartDate = appStartDate;
	}

	public String getAppEndDate() {
		return appEndDate;
	}

	public void setAppEndDate(String appEndDate) {
		this.appEndDate = appEndDate;
	}

	public String getInsStartDate() {
		return insStartDate;
	}

	public void setInsStartDate(String insStartDate) {
		this.insStartDate = insStartDate;
	}

	public String getInsEndDate() {
		return insEndDate;
	}

	public void setInsEndDate(String insEndDate) {
		this.insEndDate = insEndDate;
	}

	public HashMap<String, Object> getActivityMap() {
		return activityMap;
	}

	public void setActivityMap(HashMap<String, Object> activityMap) {
		this.activityMap = activityMap;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getInsuredPrem() {
		return insuredPrem;
	}

	public void setInsuredPrem(BigDecimal insuredPrem) {
		this.insuredPrem = insuredPrem;
	}

	public String getChannelsn() {
		return channelsn;
	}

	public void setChannelsn(String channelsn) {
		this.channelsn = channelsn;
	}

	// @Override
	// public void addActionError(String anErrorMessage) {
	// String s = anErrorMessage;
	// System.out.println("OrderConfigNewAction addActionError:"+s);
	// }
	@Override
	public void addActionMessage(String aMessage) {
		String s = aMessage;
		logger.info("OrderConfigNewAction addActionMessage:{}", s);

	}

	@Override
	public void addFieldError(String fieldName, String errorMessage) {
		String s = errorMessage;
		String f = fieldName;
		logger.error("OrderConfigNewAction fieldName:{}---------addFieldError:{}", f, s);
	}

	public List<Map<String, String>> getActivitylist() {
		return activitylist;
	}

	public void setActivitylist(List<Map<String, String>> activitylist) {
		this.activitylist = activitylist;
	}

	public String getWpViewFlag() {
		return wpViewFlag;
	}

	public void setWpViewFlag(String wpViewFlag) {
		this.wpViewFlag = wpViewFlag;
	}

	public String getSourceFlag() {
		return sourceFlag;
	}

	public void setSourceFlag(String sourceFlag) {
		this.sourceFlag = sourceFlag;
	}

	public String getExcleTempEnName() {
		return excleTempEnName;
	}

	public void setExcleTempEnName(String excleTempEnName) {
		this.excleTempEnName = excleTempEnName;
	}

	public List<Dictionary> getExcelcertificateList() {
		return excelcertificateList;
	}

	public void setExcelcertificateList(List<Dictionary> excelcertificateList) {
		this.excelcertificateList = excelcertificateList;
	}

	public List<Dictionary> getExcelrelationList() {
		return excelrelationList;
	}

	public void setExcelrelationList(List<Dictionary> excelrelationList) {
		this.excelrelationList = excelrelationList;
	}

	public List<Dictionary> getExcelsexList() {
		return excelsexList;
	}

	public void setExcelsexList(List<Dictionary> excelsexList) {
		this.excelsexList = excelsexList;
	}

	public String getComplicatedFlag() {
		return complicatedFlag;
	}

	public void setComplicatedFlag(String complicatedFlag) {
		this.complicatedFlag = complicatedFlag;
	}

	public String getDutyTempSerials() {
		return dutyTempSerials;
	}

	public void setDutyTempSerials(String dutyTempSerials) {
		this.dutyTempSerials = dutyTempSerials;
	}

	public String getOrder_healthySn() {
		return order_healthySn;
	}

	public void setOrder_healthySn(String order_healthySn) {
		this.order_healthySn = order_healthySn;
	}

	public String getProductHtml() {
		return productHtml;
	}

	public void setProductHtml(String productHtml) {
		this.productHtml = productHtml;
	}

	public String getSex() {
		return Sex;
	}

	public void setSex(String sex) {
		Sex = sex;
	}

	public String getBeneficiaryNum() {
		return beneficiaryNum;
	}

	public void setBeneficiaryNum(String beneficiaryNum) {
		this.beneficiaryNum = beneficiaryNum;
	}
	
	public void setFkIsShow(String fkIsShow) {
		this.fkIsShow = fkIsShow;
	}

	public String getFkIsShow() {
		return fkIsShow;
	}
	public String getPointExchangeFlag() {
		return pointExchangeFlag;
	}

	public void setPointExchangeFlag(String pointExchangeFlag) {
		this.pointExchangeFlag = pointExchangeFlag;
	}

	public String getPointScalerUnit() {
		return PointScalerUnit;
	}

	public void setPointScalerUnit(String pointScalerUnit) {
		PointScalerUnit = pointScalerUnit;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	public Map<String, String> getMap_pointinfo() {
		return map_pointinfo;
	}

	public void setMap_pointinfo(Map<String, String> map_pointinfo) {
		this.map_pointinfo = map_pointinfo;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}
	
	public String getIsAllFree() {
		return isAllFree;
	}

	public void setIsAllFree(String isAllFree) {
		this.isAllFree = isAllFree;
	}

	public String getSelfBnfFlag() {
		return selfBnfFlag;
	}

	public void setSelfBnfFlag(String selfBnfFlag) {
		this.selfBnfFlag = selfBnfFlag;
	}

	public String getActivityFlag() {
		return activityFlag;
	}

	public void setActivityFlag(String activityFlag) {
		this.activityFlag = activityFlag;
	}

	public String getSendPoints() {
		return sendPoints;
	}

	public void setSendPoints(String sendPoints) {
		this.sendPoints = sendPoints;
	}

	public String getSendPointsValue() {
		return sendPointsValue;
	}

	public void setSendPointsValue(String sendPointsValue) {
		this.sendPointsValue = sendPointsValue;
	}

	public String getActivityInfo() {
		return activityInfo;
	}

	public void setActivityInfo(String activityInfo) {
		this.activityInfo = activityInfo;
	}

	public String getMemGradeDesc() {
		return memGradeDesc;
	}

	public void setMemGradeDesc(String memGradeDesc) {
		this.memGradeDesc = memGradeDesc;
	}

	public String getMemberid() {
		return memberid;
	}

	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}
	public Map getMap_point_result() {
		return map_point_result;
	}

	public void setMap_point_result(Map map_point_result) {
		this.map_point_result = map_point_result;
	}

	public String getAgainBuyOrderSn() {
		return againBuyOrderSn;
	}

	public void setAgainBuyOrderSn(String againBuyOrderSn) {
		this.againBuyOrderSn = againBuyOrderSn;
	}

	public String getMult() {
		return mult;
	}

	public void setMult(String mult) {
		this.mult = mult;
	}
	
	public List<List<InsuredShow>> getShowBnfs() {
		return showBnfs;
	}

	public void setShowBnfs(List<List<InsuredShow>> showBnfs) {
		this.showBnfs = showBnfs;
	}

	public int getSdInformationBnfCount() {
		return sdInformationBnfCount;
	}

	public void setSdInformationBnfCount(int sdInformationBnfCount) {
		this.sdInformationBnfCount = sdInformationBnfCount;
	}

	public int getBeneficiaryCount() {
		return beneficiaryCount;
	}

	public void setBeneficiaryCount(int beneficiaryCount) {
		this.beneficiaryCount = beneficiaryCount;
	}

	public String getBuyForCustomerFlag() {
		return buyForCustomerFlag;
	}

	public void setBuyForCustomerFlag(String buyForCustomerFlag) {
		this.buyForCustomerFlag = buyForCustomerFlag;
	}

	public String getOldOrdersn() {
		return oldOrdersn;
	}

	public void setOldOrdersn(String oldOrdersn) {
		this.oldOrdersn = oldOrdersn;
	}

	public List<CommentInfo> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<CommentInfo> commentList) {
		this.commentList = commentList;
	}
	public String getIsLcx() {
		return isLcx;
	}
	public void setIsLcx(String isLcx) {
		this.isLcx = isLcx;
	}
	public List<Dictionary> getLcxBank() {
		return lcxBank;
	}
	public void setLcxBank(List<Dictionary> lcxBank) {
		this.lcxBank = lcxBank;
	}
	public String getApplicantIdentityid() {
		return applicantIdentityid;
	}
	public void setApplicantIdentityid(String applicantIdentityid) {
		this.applicantIdentityid = applicantIdentityid;
	}
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public String getCurdate() {
		return curdate;
	}
	public void setCurdate(String curdate) {
		this.curdate = curdate;
	}
	public Map getBindBankInfo() {
		return bindBankInfo;
	}
	public void setBindBankInfo(Map bindBankInfo) {
		this.bindBankInfo = bindBankInfo;
	}
	public String getProductyTypeFirstList() {
		return productyTypeFirstList;
	}
	public void setProductyTypeFirstList(String productyTypeFirstList) {
		this.productyTypeFirstList = productyTypeFirstList;
	}
	public List<Map<String, String>> getThreeLevelOccupations() {
		return threeLevelOccupations;
	}
	public void setThreeLevelOccupations(List<Map<String, String>> threeLevelOccupations) {
		this.threeLevelOccupations = threeLevelOccupations;
	}
	public String getOccDownAddr() {
		return occDownAddr;
	}
	public void setOccDownAddr(String occDownAddr) {
		this.occDownAddr = occDownAddr;
	}
	public String getOccupLevel() {
		return OccupLevel;
	}
	public void setOccupLevel(String occupLevel) {
		OccupLevel = occupLevel;
	}
	public String getNeedShowOccupLevel() {
		return needShowOccupLevel;
	}
	public void setNeedShowOccupLevel(String needShowOccupLevel) {
		this.needShowOccupLevel = needShowOccupLevel;
	}
	
	public String getNeedShowOccup() {
		return needShowOccup;
	}
	public void setNeedShowOccup(String needShowOccup) {
		this.needShowOccup = needShowOccup;
	}

	public String getSpecialEffDateFlag() {
		return specialEffDateFlag;
	}
	public void setSpecialEffDateFlag(String specialEffDateFlag) {
		this.specialEffDateFlag = specialEffDateFlag;
	}

	public String getApplicantMobile() {
		return applicantMobile;
	}

	public void setApplicantMobile(String applicantMobile) {
		this.applicantMobile = applicantMobile;
	}

	public String getMemberActivity() {
		return memberActivity;
	}

	public void setMemberActivity(String memberActivity) {
		this.memberActivity = memberActivity;
	}

	public String getProductDetailPagePath() {
		return productDetailPagePath;
	}

	public void setProductDetailPagePath(String productDetailPagePath) {
		this.productDetailPagePath = productDetailPagePath;
	}

	public String getIsUnderwritingOffline() {
		return isUnderwritingOffline;
	}

	public void setIsUnderwritingOffline(String isUnderwritingOffline) {
		this.isUnderwritingOffline = isUnderwritingOffline;
	}
	
	public String getUnderwritingOfflineHealthy() {
		return UnderwritingOfflineHealthy;
	}
	
	public void setUnderwritingOfflineHealthy(String underwritingOfflineHealthy) {
		UnderwritingOfflineHealthy = underwritingOfflineHealthy;
	}

	public Map<String, String> getOnLineCallBackInfo() {
		return onLineCallBackInfo;
	}

	public void setOnLineCallBackInfo(Map<String, String> onLineCallBackInfo) {
		this.onLineCallBackInfo = onLineCallBackInfo;
	}

	public String getUnderwritingOfflineCode() {
		return UnderwritingOfflineCode;
	}
	
	public void setUnderwritingOfflineCode(String underwritingOfflineCode) {
		UnderwritingOfflineCode = underwritingOfflineCode;
	}

	public String getPolicyNoOld() {
		return policyNoOld;
	}

	public void setPolicyNoOld(String policyNoOld) {
		this.policyNoOld = policyNoOld;
	}


	public String getApplicantIdentityType() {
		return applicantIdentityType;
	}

	public void setApplicantIdentityType(String applicantIdentityType) {
		this.applicantIdentityType = applicantIdentityType;
	}

	public String getApplicantIdentityTypeName() {
		return applicantIdentityTypeName;
	}

	public void setApplicantIdentityTypeName(String applicantIdentityTypeName) {
		this.applicantIdentityTypeName = applicantIdentityTypeName;
	}

	public Integer getAppStartAge() {
		return appStartAge;
	}

	public void setAppStartAge(Integer appStartAge) {
		this.appStartAge = appStartAge;
	}

	public Integer getAppEndAge() {
		return appEndAge;
	}

	public void setAppEndAge(Integer appEndAge) {
		this.appEndAge = appEndAge;
	}

	public String getAppAgeTips() {
		return appAgeTips;
	}

	public void setAppAgeTips(String appAgeTips) {
		this.appAgeTips = appAgeTips;
	}
	
}
