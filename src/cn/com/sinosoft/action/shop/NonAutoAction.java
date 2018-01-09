package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.entity.CompareNonAuto;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.MyTrailNonAuto;
import cn.com.sinosoft.entity.Product;
import cn.com.sinosoft.entity.RiskAppFactor;
import cn.com.sinosoft.entity.TrailProduct;
import cn.com.sinosoft.service.CompareService;
import cn.com.sinosoft.service.MyTrailNonAutoService;
import cn.com.sinosoft.service.TrailProductService;
import cn.com.sinosoft.util.CookieUtil;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.inter.ActivityCalculate;
import com.sinosoft.sms.messageinterface.pubfun.PubFun;
import com.sinosoft.webservice.ProductWebservice;
import com.sinosoft.webservice.compareInfo.CompareInfoServiceStub;
import com.sinosoft.webservice.compareInfo.CompareInfoServiceStub.FEMRiskCompareProperties;
import com.sinosoft.webservice.fDInsCom.FDInsComServiceStub.FDInsCom;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FMRisk;
import com.sinosoft.webservice.productPrem.ProductPremServiceStub;
import com.sinosoft.webservice.productPrem.ProductPremServiceStub.CalProductPrem;
import com.sinosoft.webservice.productPrem.ProductPremServiceStub.FEMRiskFactorList;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LiuXin
 * 
 */
@ParentPackage("shop")
public class NonAutoAction extends BaseShopAction {

	private static final long serialVersionUID = 6563851705898499152L;
	private CompareInfoServiceStub.FEMRiskCompareProperties[] fcps;
	private ProductInfoWebServiceStub.FMRisk[] fmr;
	private List<Product> products = new ArrayList<Product>();
	private String productIdA = "";
	private String productIdB = "";
	private String productIdC = "";
	private String productIdD = "";
	private String productPathA = "";
	private String productPathB = "";
	private String productPathC = "";
	private String productPathD = "";
	//zhangjinquan 11180 增加是否配置投保页面录入项标记 2012-12-06
	private String configFlagA = "0";
	public String getConfigFlagA() {
		return configFlagA;
	}

	public void setConfigFlagA(String configFlagA) {
		this.configFlagA = configFlagA;
	}

	public String getConfigFlagB() {
		return configFlagB;
	}

	public void setConfigFlagB(String configFlagB) {
		this.configFlagB = configFlagB;
	}

	public String getConfigFlagC() {
		return configFlagC;
	}

	public void setConfigFlagC(String configFlagC) {
		this.configFlagC = configFlagC;
	}

	public String getConfigFlagD() {
		return configFlagD;
	}

	public void setConfigFlagD(String configFlagD) {
		this.configFlagD = configFlagD;
	}

	private String configFlagB = "0";
	private String configFlagC = "0";
	private String configFlagD = "0";
	private List<Map<String, Object>> compareInformation = new ArrayList<Map<String, Object>>();
	private List<RiskAppFactor> rafs = new ArrayList<RiskAppFactor>();
	private List<RiskAppFactor> dutyFactor = new ArrayList<RiskAppFactor>();
	private List<CompareNonAuto> compareNonAutos = new ArrayList<CompareNonAuto>();
	private List<CompareNonAuto> compareNonAutoAppFs = new ArrayList<CompareNonAuto>();
	private List<CompareNonAuto> compareNonAutoDutys = new ArrayList<CompareNonAuto>();
	private int compareSize;
	@Resource
	private MyTrailNonAutoService myTNAutoService;
	@Resource
	private CompareService compareService;
	@Resource
	private TrailProductService trailProductService;

	private String A;
	private String B;
	private String C;
	private String D;

	/**
	 * 
	 * 非车险
	 */
	@SuppressWarnings("unchecked")
	public String list() {

		try {
			logger.info("=======非车险方法====");
			Map<String, Object> map = new HashMap<String, Object>();
			String eriskType = getParameter("eriskType");
			String productA = getParameter("productA");
			String productB = getParameter("productB");
			String productC = getParameter("productC");
			String productD = getParameter("productD");
			List<String> idList = new ArrayList<String>();
			if (productA != null && !"".equals(productA)) {
				productIdA = productA;
				idList.add(productA);
			}
			if (productB != null && !"".equals(productB)) {
				productIdB = productB;
				idList.add(productB);
			}
			if (productC != null && !"".equals(productC)) {
				productIdC = productC;
				idList.add(productC);
			}
			if (productD != null && !"".equals(productD)) {
				productIdD = productD;
				idList.add(productD);
			}
			for (int i=0; i<idList.size(); i++)
			{
				
				switch(i)
				{
					case 0:
						productIdB = null;
						productIdC = null;
						productIdD = null;
						productIdA = idList.get(i);
						productPathA = getPathByProductId(productIdA);
						break;
					case 1:
						productIdB = idList.get(i);
						productPathB = getPathByProductId(productIdB);
						break;
					case 2:
						productIdC = idList.get(i);
						productPathC = getPathByProductId(productIdC);
						break;
					case 3:
						productIdD = idList.get(i);
						productPathD = getPathByProductId(productIdD);
						break;
					default:
						return ERROR;
				}
			}
			String compareProducts[] = new String[idList.size()];
			if (idList != null && idList.size() > 0) {
				for (int i = 0; i < idList.size(); i++) {
					compareProducts[i] = idList.get(i);
				}
			}
			fcps = compareService.getCompareInformation(eriskType);
			map = compareService.getNonAutoTrial(compareProducts, fcps);
			fmr = (FMRisk[]) map.get("fmr");
			compareInformation = (List<Map<String, Object>>) map.get("compareInformation");
			rafs = (List<RiskAppFactor>) map.get("rafs");
			if (compareInformation != null && compareInformation.size() > 0) {
				for (Map<String, Object> mp : compareInformation) {
					if (mp.get("comCode") != null) {
						FDInsCom fdi = compareService.getCompanyInformation(mp.get("comCode").toString());
						mp.put("companyLog", Config.interfaceMap.get("companyLog") + fdi.getLogoRelaPath());
						mp.put("companyName", fdi.getShortName());
					}
				}
				compareSize = compareInformation.size() + 1;
			}
			saveMyTrailNonAuto(idList, eriskType, compareInformation);
			AssemblyCompareIF(fcps, compareInformation);
			for (int i=1;i<= idList.size();i++) {
				if (i==1) {
					A = "1";
				}
				if (i==2) {
					B = "1";
				}
				if (i==3) {
					C = "1";
				}
				if (i==4) {
					D = "1";
				}
			}
			getSession().put("rafs", rafs);
			getSession().put("dutyFactor", dutyFactor);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			ActionUtil.dealAction("wj00013", dataMap, getRequest());
			return "list";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionError("查询产品失败，请联系管理员！");
			return ERROR;
		}
	}

	private String getPathByProductId(String productId) {
		String path = "";
		String sql = "select HtmlPath from sdproduct where productId = ?";
		DataTable dt = new QueryBuilder(sql,productId).executeDataTable();
		if(dt.getRowCount()>0){
			path = dt.getString(0, 0);
		}
		return path;
	}

	/**
	 * 
	 *我的试算详情
	 */
	@SuppressWarnings( { "unchecked", "static-access" })
	public String detail() {
		try {
			String eriskType = getParameter("eriskType");
			String serialNumber = getParameter("serialNumber");
			Map<String, Object> map = new HashMap<String, Object>();
			List<TrailProduct> trailProducts = myTNAutoService.getTrailProductBySerialNo(serialNumber);
			String compareProducts[] = new String[trailProducts.size()];
			if (trailProducts != null && trailProducts.size() > 0) {
				for (int i = 0; i < trailProducts.size(); i++) {
					compareProducts[i] = trailProducts.get(i).getProductCode();
					if (i == 0) {
						productIdA = trailProducts.get(0).getProductCode();
						productPathA = getPathByProductId(productIdA);
					}
					if (i == 1) {
						productIdB = trailProducts.get(1).getProductCode();
						productPathB = getPathByProductId(productIdB);
					}
					if (i == 2) {
						productIdC = trailProducts.get(2).getProductCode();
						productPathC = getPathByProductId(productIdC);
					}
					if (i == 3) {
						productIdD = trailProducts.get(3).getProductCode();
						productPathD = getPathByProductId(productIdD);
					}
				}
			}
			fcps = compareService.getCompareInformation(eriskType);
			map = compareService.getNonAutoTrial(compareProducts, fcps);
			fmr = (FMRisk[]) map.get("fmr");
			compareInformation = (List<Map<String, Object>>) map.get("compareInformation");
			rafs = (List<RiskAppFactor>) map.get("rafs");
			dutyFactor = (List<RiskAppFactor>) map.get("dutyFactor");
			if (compareInformation != null && compareInformation.size() > 0) {
				for (Map<String, Object> mp : compareInformation) {
					if (mp.get("comCode") != null) {
						FDInsCom fdi = compareService.getCompanyInformation(mp.get("comCode").toString());
						mp.put("companyLog", Config.interfaceMap.get("companyLog") + fdi.getLogoRelaPath());
						mp.put("companyName", fdi.getShortName());
					}
				}
				compareSize = compareInformation.size() + 1;
			}
			AssemblyCompareIF(fcps, compareInformation);
			if (!StringUtil.isEmpty(productIdA)) {
				A = "1";
			}
			if (!StringUtil.isEmpty(productIdB)) {
				B = "1";
			}
			if (!StringUtil.isEmpty(productIdC)) {
				C = "1";
			}
			if (!StringUtil.isEmpty(productIdD)) {
				D = "1";
			}
			getSession().put("rafs", rafs);
			getSession().put("dutyFactor", dutyFactor);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			ActionUtil.dealAction("wj00014", dataMap, getRequest());
			return "list";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionError("查询产品失败，请联系管理员！");
			return ERROR;
		}
	}

	/**
	 * 获取对象指定值
	 * 
	 * @throws Exception
	 */
	public static Object reflectMap(Object obj, String paramter) throws Exception {
		if (obj == null || paramter == null)
			return null;
		try {
			Class<?> c = obj.getClass();
			Method m[] = c.getDeclaredMethods();
			for (int i = 0; m != null && i < m.length; i++) {
				if (m[i].getName().equalsIgnoreCase("get" + paramter)) {
					return m[i].invoke(obj, new Object[0]);
				}
			}
			return null;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new Exception("获取值错误。");
		}
	}

	@SuppressWarnings("unchecked")
	public String getPremiumTrial() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String productCode = getRequest().getParameter("productCode");
		String factortype = getRequest().getParameter("factortype");
		String factorValue = getRequest().getParameter("factorValue");
		String flag = getRequest().getParameter("flag");
		String dutyCode = getRequest().getParameter("dutyCode");
		String amnt = getRequest().getParameter("amnt");
		RiskAppFactor returnRaf = new RiskAppFactor();
		List<RiskAppFactor> changeRafs = (List<RiskAppFactor>) getSession().get("rafs");
		List<RiskAppFactor> changeDuty = (List<RiskAppFactor>) getSession().get("dutyFactor");
		List<RiskAppFactor> trailRafs = new ArrayList<RiskAppFactor>();
		List<RiskAppFactor> trailDuty = new ArrayList<RiskAppFactor>();
		if (changeRafs != null && "1".equals(flag) && changeRafs.size() > 0) {
			for (RiskAppFactor rf : changeRafs) {
				if (productCode.equals(rf.getProductCode())) {
					if (factortype.equals(rf.getFactorType())) {
						if ("TextAge".equals(rf.getFactorType())) {
							String age = factorValue;
							if (age != null && !"".equals(age) && age.indexOf("Y") >= 0) {
								age = age.substring(0, age.indexOf("Y"));
								if ("0".equals(age)) {
									age = "1";
								}
								String birthday = PubFun.calSDate(PubFun.getCurrentDate(), 0 - Integer.parseInt(age), "Y");
								rf.setFactorValue(birthday);
							}
						} else {
							rf.setFactorValue(factorValue);
						}
					}
					trailRafs.add(rf);
				}
			}
			getSession().put("rafs", changeRafs);
		}
		if (changeDuty != null && "2".equals(flag) && changeDuty.size() > 0) {
			for (RiskAppFactor df : changeDuty) {
				if (productCode.equals(df.getProductCode())) {
					if (dutyCode.equals(df.getDutyCode())) {
						df.setAmnt(amnt);
					}
					trailDuty.add(df);
				}
			}
			getSession().put("dutyFactor", changeDuty);
		}
		if (productCode != null && !"".equals(productCode)) {
			returnRaf.setProductCode(productCode);
			Map<String, Object> paramter = new HashMap<String, Object>();
			try {
				ProductPremServiceStub.FEMRiskFactorList[] frafactor = new ProductPremServiceStub.FEMRiskFactorList[trailRafs.size()];
				ProductPremServiceStub.CalProductPrem[] calPPrems = null;
				for (int i = 0; i < trailRafs.size(); i++) {
					frafactor[i] = getFEMRiskFactorList(trailRafs.get(i));
				}
				if (trailDuty.size() > 0) {
					calPPrems = new ProductPremServiceStub.CalProductPrem[trailDuty.size()];
					for (int i = 0; i < trailDuty.size(); i++) {
						calPPrems[i] = getCalProductPrem(trailDuty.get(i), frafactor, productCode);
					}
				} else {
					calPPrems = new ProductPremServiceStub.CalProductPrem[1];
					calPPrems[0] = getCalProductPrem(null, frafactor, productCode);
				}
				paramter.put("CalProductPrem", calPPrems);
				double totlePrem = 0.0;
				double discountPrem = 0.0;
				if (changeRafs != null && changeRafs.size() > 0) {
					ProductPremServiceStub.ProductPremResponse ppr = ProductWebservice.ProductPremSereviceImpl(paramter, null);
					// 原价
					if (ppr.getPrem().length > 0) {
						for (String d : ppr.getPrem()) {
							if(d!=null && !d.equals("")){
								totlePrem = totlePrem + Double.parseDouble(d);
							}
						}
					}
					// 折扣价
					if(ppr.getDiscountPrice().length>0){
						for(String d : ppr.getDiscountPrice()){
							discountPrem = discountPrem + Double.parseDouble(d);
						}
					}
				}
				String Channel = "";
				Cookie ck;
				ck = CookieUtil.getCookieByName(request, "cpsUserId");
				if(StringUtil.isEmpty(ck)){
					Channel = "wj";
				}else{
					Channel = "cps";
				}
				//全站活动开启 Y N 价格显示
				//String activityPrice = ActivityCalculate.ProductCalculate(productCode, discountPrem+"", "wj");
				Map<String,String> ProductIDMap = new HashMap<String, String>();
				ProductIDMap.put(productCode, String.valueOf(discountPrem));
				
				Map<String, Map<String, String>> resultProductDetail = ActivityCalculate.ProductCalculate(ProductIDMap,"",Channel);
				Map<String, String> productDetail = resultProductDetail.get(productCode);
				
				String rAmount = productDetail.get("Amount");
				String rIntegral = productDetail.get("Integral");
				returnRaf.setDiscountPrem(rAmount);
				returnRaf.setProductInt(rIntegral);
				returnRaf.setPrem(Double.toString(totlePrem));
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		JSONObject jsonArray = JSONObject.fromObject(returnRaf);
		String jsonstr = jsonArray.toString();
		return ajax(jsonstr, "text/html");
	}

	private CalProductPrem getCalProductPrem(RiskAppFactor raf, ProductPremServiceStub.FEMRiskFactorList[] frafactor, String productCode) {
		CalProductPrem cpp = new CalProductPrem();
		cpp.setRiskCode(productCode);
		if (raf != null) {
			cpp.setAmnt(raf.getAmnt());
			cpp.setDutyCode(raf.getDutyCode());
		}
		cpp.setFEMRiskFactorList(frafactor);
		return cpp;
	}

	private FEMRiskFactorList getFEMRiskFactorList(RiskAppFactor riskAppFactor) {
		ProductPremServiceStub.FEMRiskFactorList femflist = new ProductPremServiceStub.FEMRiskFactorList();
		femflist.setFactorType(riskAppFactor.getFactorType());
		femflist.setFactorValue(riskAppFactor.getFactorValue());
		femflist.setIsPremCalFacotor("Y");
		return femflist;
	}

	private void AssemblyCompareIF(FEMRiskCompareProperties[] fcps2, List<Map<String, Object>> compareInformation2) {
		if (fcps2 != null && fcps2.length > 0) {
			if (compareInformation2.size() == 1) {
				
				configFlagA = OrderAction.getConfigInputFlagFromDB((String)compareInformation2.get(0).get("comCode"));
				compareNonAutos.add(createCNA("产品名称", getLogoString(compareInformation2.get(0).get("RiskName"),compareInformation2.get(0).get("riskCode"),compareInformation2.get(0).get("DiscountPrem"),compareInformation2.get(0).get("InitPremValue"),productPathA), null, null, null, "A"));
				compareNonAutos.add(createCNA("保障计划", getTypeString(compareInformation2.get(0).get("Plan")), null, null, null, "A"));
				compareNonAutos.add(createCNA("投保年龄", getTypeString(compareInformation2.get(0).get("TextAge")), null, null, null, "A"));
				compareNonAutos.add(createCNA("保险期限", getTypeString(compareInformation2.get(0).get("Period")), null, null, null, "A"));
				compareNonAutos.add(createCNA("产品价格", getPriceString(compareInformation2.get(0).get("riskCode"),compareInformation2.get(0).get("InitPrem"), compareInformation2.get(0).get("DiscountRate"), compareInformation2.get(0).get(
						"DiscountPrem"),compareInformation2.get(0).get("InitPremValue")), null, null, null, "A"));
				compareNonAutos.add(createCNA("优惠信息", getProductInt(compareInformation2.get(0).get("riskCode"),compareInformation2.get(0).get("ProductInt")),null,null,null, "A"));
			
			}
			if (compareInformation2.size() == 2) {
				configFlagA = OrderAction.getConfigInputFlagFromDB((String)compareInformation2.get(0).get("comCode"));
				configFlagB = OrderAction.getConfigInputFlagFromDB((String)compareInformation2.get(1).get("comCode"));
				compareNonAutos.add(createCNA("产品名称", getLogoString(compareInformation2.get(0).get("RiskName"),compareInformation2.get(0).get("riskCode"),compareInformation2.get(0).get("DiscountPrem"),compareInformation2.get(0).get("InitPremValue"),productPathA), 
						getLogoString(compareInformation2.get(1).get("RiskName"),compareInformation2.get(1).get("riskCode"),compareInformation2.get(1).get("DiscountPrem"),compareInformation2.get(1).get("InitPremValue"),productPathB), null, null, "A"));
				compareNonAutos.add(createCNA("保障计划", getTypeString(compareInformation2.get(0).get("Plan")), getTypeString(compareInformation2.get(1).get("Plan")), null, null, "A"));
				compareNonAutos.add(createCNA("投保年龄", getTypeString(compareInformation2.get(0).get("TextAge")),  getTypeString(compareInformation2.get(1).get("TextAge")), null, null, "A"));
				compareNonAutos.add(createCNA("保险期限", getTypeString(compareInformation2.get(0).get("Period")), getTypeString(compareInformation2.get(1).get("Period")), null, null, "A"));
				compareNonAutos.add(createCNA("产品价格", getPriceString(compareInformation2.get(0).get("riskCode"),compareInformation2.get(0).get("InitPrem"), compareInformation2.get(0).get("DiscountRate"), compareInformation2.get(0).get(
						"DiscountPrem"),compareInformation2.get(0).get("InitPremValue")), getPriceString(compareInformation2.get(1).get("riskCode"),compareInformation2.get(1).get("InitPrem"), compareInformation2.get(1).get("DiscountRate"), compareInformation2.get(1).get("DiscountPrem"),compareInformation2.get(1).get("InitPremValue")),
						null, null, "A"));
				compareNonAutos.add(createCNA("优惠信息", getProductInt(compareInformation2.get(0).get("riskCode"),compareInformation2.get(0).get("ProductInt")),getProductInt(compareInformation2.get(1).get("riskCode"),compareInformation2.get(1).get("ProductInt")),null,null, "A"));
			}
			if (compareInformation2.size() == 3) {
				configFlagA = OrderAction.getConfigInputFlagFromDB((String)compareInformation2.get(0).get("comCode"));
				configFlagB = OrderAction.getConfigInputFlagFromDB((String)compareInformation2.get(1).get("comCode"));
				configFlagC = OrderAction.getConfigInputFlagFromDB((String)compareInformation2.get(2).get("comCode"));
				compareNonAutos.add(createCNA("产品名称", getLogoString(compareInformation2.get(0).get("RiskName"),compareInformation2.get(0).get("riskCode"),compareInformation2.get(0).get("DiscountPrem"),compareInformation2.get(0).get("InitPremValue"),productPathA), 
						getLogoString(compareInformation2.get(1).get("RiskName"),compareInformation2.get(1).get("riskCode"),compareInformation2.get(1).get("DiscountPrem"),compareInformation2.get(1).get("InitPremValue"),productPathB),
						getLogoString(compareInformation2.get(2).get("RiskName"),compareInformation2.get(2).get("riskCode"),compareInformation2.get(2).get("DiscountPrem"),compareInformation2.get(2).get("InitPremValue"),productPathC), null, "A"));
				/*compareNonAutos.add(createCNA("公司名称", getImageString(compareInformation2.get(0).get("companyLog")), getImageString(compareInformation2.get(1).get("companyLog")),
						getImageString(compareInformation2.get(2).get("companyLog")), null, "A"));*/
				compareNonAutos.add(createCNA("保障计划", getTypeString(compareInformation2.get(0).get("Plan")), getTypeString(compareInformation2.get(1).get("Plan")), getTypeString(compareInformation2.get(2).get("Plan")), null, "A"));
				compareNonAutos.add(createCNA("投保年龄", getTypeString(compareInformation2.get(0).get("TextAge")),  getTypeString(compareInformation2.get(1).get("TextAge")), getTypeString(compareInformation2.get(2).get("TextAge")), null, "A"));
				compareNonAutos.add(createCNA("保险期限", getTypeString(compareInformation2.get(0).get("Period")), getTypeString(compareInformation2.get(1).get("Period")), getTypeString(compareInformation2.get(2).get("Period")), null, "A"));
				compareNonAutos.add(createCNA("产品价格", getPriceString(compareInformation2.get(0).get("riskCode"),compareInformation2.get(0).get("InitPrem"), compareInformation2.get(0).get("DiscountRate"), compareInformation2.get(0).get(
						"DiscountPrem"),compareInformation2.get(0).get("InitPremValue")), getPriceString(compareInformation2.get(1).get("riskCode"),compareInformation2.get(1).get("InitPrem"), compareInformation2.get(1).get("DiscountRate"), compareInformation2.get(1).get("DiscountPrem"),compareInformation2.get(1).get("InitPremValue")),
						getPriceString(compareInformation2.get(2).get("riskCode"),compareInformation2.get(2).get("InitPrem"), compareInformation2.get(2).get("DiscountRate"), compareInformation2.get(2).get("DiscountPrem"),compareInformation2.get(2).get("InitPremValue")), null, "A"));
				compareNonAutos.add(createCNA("优惠信息", getProductInt(compareInformation2.get(0).get("riskCode"),compareInformation2.get(0).get("ProductInt")),getProductInt(compareInformation2.get(1).get("riskCode"),compareInformation2.get(1).get("ProductInt")),
						getProductInt(compareInformation2.get(2).get("riskCode"),compareInformation2.get(2).get("ProductInt")),null, "A"));
			}
			if (compareInformation2.size() == 4) {
				configFlagA = OrderAction.getConfigInputFlagFromDB((String)compareInformation2.get(0).get("comCode"));
				configFlagB = OrderAction.getConfigInputFlagFromDB((String)compareInformation2.get(1).get("comCode"));
				configFlagC = OrderAction.getConfigInputFlagFromDB((String)compareInformation2.get(2).get("comCode"));
				configFlagD = OrderAction.getConfigInputFlagFromDB((String)compareInformation2.get(3).get("comCode"));
				compareNonAutos.add(createCNA("产品名称", getLogoString(compareInformation2.get(0).get("RiskName"),compareInformation2.get(0).get("riskCode"),compareInformation2.get(0).get("DiscountPrem"),compareInformation2.get(0).get("InitPremValue"),productPathA), 
						getLogoString(compareInformation2.get(1).get("RiskName"),compareInformation2.get(1).get("riskCode"),compareInformation2.get(1).get("DiscountPrem"),compareInformation2.get(1).get("InitPremValue"),productPathB),
						getLogoString(compareInformation2.get(2).get("RiskName"),compareInformation2.get(2).get("riskCode"),compareInformation2.get(2).get("DiscountPrem"),compareInformation2.get(2).get("InitPremValue"),productPathC), 
						getLogoString(compareInformation2.get(3).get("RiskName"),compareInformation2.get(3).get("riskCode"),compareInformation2.get(3).get("DiscountPrem"),compareInformation2.get(3).get("InitPremValue"),productPathD), "A"));
				/*compareNonAutos.add(createCNA("公司名称", getImageString(compareInformation2.get(0).get("companyLog")), getImageString(compareInformation2.get(1).get("companyLog")),
						getImageString(compareInformation2.get(2).get("companyLog")), getImageString(compareInformation2.get(3).get("companyLog")), "A"));*/
				compareNonAutos.add(createCNA("保障计划", getTypeString(compareInformation2.get(0).get("Plan")), getTypeString(compareInformation2.get(1).get("Plan")), getTypeString(compareInformation2.get(2).get("Plan")), getTypeString(compareInformation2.get(3).get("Plan")), "A"));
				compareNonAutos.add(createCNA("保险期限", getTypeString(compareInformation2.get(0).get("Period")), getTypeString(compareInformation2.get(1).get("Period")), getTypeString(compareInformation2.get(2).get("Period")), getTypeString(compareInformation2.get(3).get("Period")), "A"));
				compareNonAutos.add(createCNA("产品价格", getPriceString(compareInformation2.get(0).get("riskCode"),compareInformation2.get(0).get("InitPrem"), compareInformation2.get(0).get("DiscountRate"), compareInformation2.get(0).get(
						"DiscountPrem"),compareInformation2.get(0).get("InitPremValue")), getPriceString(compareInformation2.get(1).get("riskCode"),compareInformation2.get(1).get("InitPrem"), compareInformation2.get(1).get("DiscountRate"), compareInformation2.get(1).get("DiscountPrem"),compareInformation2.get(1).get("InitPremValue")),
						getPriceString(compareInformation2.get(2).get("riskCode"),compareInformation2.get(2).get("InitPrem"), compareInformation2.get(2).get("DiscountRate"), compareInformation2.get(2).get("DiscountPrem"),compareInformation2.get(2).get("InitPremValue")), getPriceString(
								compareInformation2.get(3).get("riskCode"),compareInformation2.get(3).get("InitPrem"), compareInformation2.get(3).get("DiscountRate"), compareInformation2.get(3).get("DiscountPrem"),compareInformation2.get(3).get("InitPremValue")), "A"));
				compareNonAutos.add(createCNA("优惠信息", getProductInt(compareInformation2.get(0).get("riskCode"),compareInformation2.get(0).get("ProductInt")),getProductInt(compareInformation2.get(1).get("riskCode"),compareInformation2.get(1).get("ProductInt")),
						getProductInt(compareInformation2.get(2).get("riskCode"),compareInformation2.get(2).get("ProductInt")),getProductInt(compareInformation2.get(3).get("riskCode"),compareInformation2.get(3).get("ProductInt")), "A"));
			}
			String informationA =  null;
			String informationB =  null;
			String informationC =  null;
			String informationD =  null;
			/*for (int i = 0; i < fcps2.length; i++) {
				CompareNonAuto cna = new CompareNonAuto();
				cna.setCompareName(fcps2[i].getPropertyTypeName());
				log.info("==========="+cna.getCompareName()+"======="+fcps2[i].getPropertyType());
				if (compareInformation2.size() == 1) {
					if (compareInformation2.get(0).get(fcps2[i].getPropertyType()) != null) {
						informationA = compareInformation2.get(0).get(fcps2[i].getPropertyType()).toString();
						if(informationA == null || "".equals(informationA)){
							cna.setInformationA("-");
						}else{
							cna.setInformationA(informationA);
						}
					}else{
						cna.setInformationA("-");
					}
					compareNonAutos.add(cna);
					if ("A".equals(fcps2[i].getCompareInfoType())) {
						cna.setType("A");
						compareNonAutos.add(cna);
					} else if ("B".equals(fcps2[i].getCompareInfoType())) {
						cna.setType("B");
						compareNonAutoAppFs.add(cna);
					}
				}
				if (compareInformation2.size() == 2) {
					if (compareInformation2.get(0).get(fcps2[i].getPropertyType()) != null) {
						informationA = compareInformation2.get(0).get(fcps2[i].getPropertyType()).toString();
						if(informationA == null || "".equals(informationA)){
							cna.setInformationA("-");
						}else{
							cna.setInformationA(informationA);
						}
					}else{
						cna.setInformationA("-");
					}
					if (compareInformation2.get(1).get(fcps2[i].getPropertyType()) != null) {
						informationB = compareInformation2.get(1).get(fcps2[i].getPropertyType()).toString();
						if(informationB == null || "".equals(informationB)){
							cna.setInformationB("-");
						}else{
							cna.setInformationB(informationB);
						}
					}else{
						cna.setInformationB("-");
					}
					compareNonAutos.add(cna);
					if ("A".equals(fcps2[i].getCompareInfoType())) {
						cna.setType("A");
						compareNonAutos.add(cna);
					} else if ("B".equals(fcps2[i].getCompareInfoType())) {
						cna.setType("B");
						compareNonAutoAppFs.add(cna);
					}
				}
				if (compareInformation2.size() == 3) {
					if (compareInformation2.get(0).get(fcps2[i].getPropertyType()) != null) {
						informationA = compareInformation2.get(0).get(fcps2[i].getPropertyType()).toString();
						if(informationA == null || "".equals(informationA)){
							cna.setInformationA("-");
						}else{
							cna.setInformationA(informationA);
						}
					}else{
						cna.setInformationA("-");
					}
					if (compareInformation2.get(1).get(fcps2[i].getPropertyType()) != null) {
						informationB = compareInformation2.get(1).get(fcps2[i].getPropertyType()).toString();
						if(informationB == null || "".equals(informationB)){
							cna.setInformationB("-");
						}else{
							cna.setInformationB(informationB);
						}
					}else{
						cna.setInformationB("-");
					}
					if (compareInformation2.get(2).get(fcps2[i].getPropertyType()) != null) {
						informationC = compareInformation2.get(2).get(fcps2[i].getPropertyType()).toString();
						if(informationC == null || "".equals(informationC)){
							cna.setInformationC("-");
						}else{
							cna.setInformationC(informationC);
						}
					}else{
						cna.setInformationC("-");
					}
					compareNonAutos.add(cna);
					if ("A".equals(fcps2[i].getCompareInfoType())) {
						cna.setType("A");
						compareNonAutos.add(cna);
					} else if ("B".equals(fcps2[i].getCompareInfoType())) {
						cna.setType("B");
						compareNonAutoAppFs.add(cna);
					}
				}
				if (compareInformation2.size() == 4) {
					if (compareInformation2.get(0).get(fcps2[i].getPropertyType()) != null) {
						informationA = compareInformation2.get(0).get(fcps2[i].getPropertyType()).toString();
						if(informationA == null || "".equals(informationA)){
							cna.setInformationA("-");
						}else{
							cna.setInformationA(informationA);
						}
					}else{
						cna.setInformationA("-");
					}
					if (compareInformation2.get(1).get(fcps2[i].getPropertyType()) != null) {
						informationB = compareInformation2.get(1).get(fcps2[i].getPropertyType()).toString();
						if(informationB == null || "".equals(informationB)){
							cna.setInformationB("-");
						}else{
							cna.setInformationB(informationB);
						}
					}else{
						cna.setInformationB("-");
					}
					if (compareInformation2.get(2).get(fcps2[i].getPropertyType()) != null) {
						informationC = compareInformation2.get(2).get(fcps2[i].getPropertyType()).toString();
						if(informationC == null || "".equals(informationC)){
							cna.setInformationC("-");
						}else{
							cna.setInformationC(informationC);
						}
					}else{
						cna.setInformationC("-");
					}
					if (compareInformation2.get(3).get(fcps2[i].getPropertyType()) != null) {
						informationD = compareInformation2.get(3).get(fcps2[i].getPropertyType()).toString();
						if(informationD == null || "".equals(informationD)){
							cna.setInformationD("-");
						}else{
							cna.setInformationD(informationD);
						}
					}else{
						cna.setInformationD("-");
					}
					compareNonAutos.add(cna);
					if ("A".equals(fcps2[i].getCompareInfoType())) {
						cna.setType("A");
						compareNonAutos.add(cna);
					} else if ("B".equals(fcps2[i].getCompareInfoType())) {
						cna.setType("B");
						compareNonAutoAppFs.add(cna);
					}
				}
			}*/
		}
		Map<String, Object> resultMap = compareService.createProductDuty(compareInformation2);
		compareNonAutoDutys = (List<CompareNonAuto>) resultMap.get("compareDutyList");
		dutyFactor = (List<RiskAppFactor>) resultMap.get("dutyFactor");
		createSaleAndEvaluate(compareInformation2);
	}
	
	private void createSaleAndEvaluate(List<Map<String, Object>> compareInformation2) {
		String tSalesVolume = "0";
		String[] saleArr = new String[compareInformation2.size()];
		String[] commentArr = new String[compareInformation2.size()];
		for(int i=0;i<compareInformation2.size();i++){
			String riskCode = (String) compareInformation2.get(i).get("riskCode");
			tSalesVolume = SalesVolumeAction.dealSalvesVolumn(riskCode);
			saleArr[i]=tSalesVolume;
			// 取得评论条数
			QueryBuilder qb = new QueryBuilder("SELECT id FROM zcarticle t WHERE t.prop4=? AND t.type='1' AND t.status = '30' LIMIT 1",riskCode);
			DataTable dt = qb.executeDataTable();
			String relaid = dt.getString(0, "id");
			QueryBuilder qb1 = new QueryBuilder("select count(*) count from zccomment where relaid=?  and verifyflag='Y'",relaid);
			DataTable dt1 = qb1.executeDataTable();
			String count = dt1.getString(0, "count");
			commentArr[i]=count+"人评价";
		}
		if(saleArr.length==1){
			compareNonAutoAppFs.add(createCNA("销量", saleArr[0], null, null, null, "A"));
			compareNonAutoAppFs.add(createCNA("评论", commentArr[0], null, null, null, "A"));
		}
		if(saleArr.length==2){
			compareNonAutoAppFs.add(createCNA("销量",saleArr[0],saleArr[1], null, null, "A"));
			compareNonAutoAppFs.add(createCNA("评论", commentArr[0], commentArr[1], null, null, "A"));
		}
		if(saleArr.length==3){
			compareNonAutoAppFs.add(createCNA("销量",saleArr[0], saleArr[1], saleArr[2], null, "A"));
			compareNonAutoAppFs.add(createCNA("评论", commentArr[0], commentArr[1], commentArr[2], null, "A"));
		}
		if(saleArr.length==4){
			compareNonAutoAppFs.add(createCNA("销量",  saleArr[0],  saleArr[1],  saleArr[2], saleArr[3], "A"));
			compareNonAutoAppFs.add(createCNA("评论", commentArr[0], commentArr[1], commentArr[2], commentArr[3], "A"));
		}
		
	}
	
	/**
	 * 
	 * @param InitPrice
	 *            原价
	 * @param DiscountRate
	 *            折扣率
	 * @param DiscountPrem
	 *            折扣价
	 * @return
	 */
	private String getPriceString(Object cProductID,Object InitPrice, Object DiscountRate, Object DiscountPrem,Object InitPremValue) {
		String resultStr = "";
		Object[] argArray = {InitPrice, DiscountRate, DiscountPrem};
		logger.info("InitPrice:{} DiscountRate:{} DiscountPrem:{}", argArray);
		if (DiscountPrem != null) {
//			String prem = DiscountPrem.toString();
//			prem = prem.substring(prem.indexOf(">")+1);
//			prem = prem.substring(0,prem.indexOf("<"));
//			String activityPrice = ActivityCalculate.ProductCalculate(cProductID.toString(), prem+"", "wj");
//			resultStr = "<font class=\"DiscountPrice\" >￥" + activityPrice + "</font>";
			resultStr = "<font class=\"DiscountPrice\" >￥" + DiscountPrem + "</font>";
		}

		/*if (DiscountRate != null && StringUtil.isNotEmpty(DiscountRate.toString()) && !"0".equals(DiscountRate)) {
			resultStr += "<font class=\"InitPrice\" >| 原价:￥<s>" + InitPrice + "</s></font>";

		} else {
			resultStr += "<font class=\"InitPrice\" style=\"display:none\" >| 原价:￥<s>" + InitPrice + "</s></font>";
		}*/
		if(DiscountPrem.toString().contains(InitPremValue.toString())){
			resultStr += "<font class=\"InitPrice\" style=\"display:none\" > 原价:￥<s>" + InitPrice + "</s></font>";
		}else{
			resultStr += "<font class=\"InitPrice\" style=\"display:none\" > 原价:￥<s>" + InitPrice + "</s></font>";
		}
		return resultStr;
	}
	private String getProductInt(Object cProductID,Object cProductInt){
		
		String ProductInt = "";
		String ProductID = "";
		if (cProductInt != null) {
			ProductInt = cProductInt.toString();
		}
		if (cProductID != null) {
			ProductID = cProductID.toString();
		}
		
		String productInt = "<ul id='yhinfo_"+ProductID+"' class='at_list'><li><span class='tb_icon active_01'>积分</span><span class='tb_text'>"
				+"购买您将获得"+ProductInt+"个积分 <span></li></ul>";
		return productInt;
	}
	

	private String getTypeString(Object ob) {
		String ss = "";
		if (ob != null) {
			ss = ob.toString();
		}
		return ss;
	}

	private String getLogoString(Object riskName,Object riskCode,Object DiscountPrem,Object InitPremValue,Object productPath) {
		String ss = riskCode.toString().substring(0, 4);	
		String reStr = "";
		//if(!(DiscountPrem.toString().contains(InitPremValue.toString()))){//促销
		reStr = "<p id='Activity_" + riskCode + "' ></p>";
		//}
		reStr += "<a href='"+productPath+"' target=\"_blank\" ><div class=\"icon_C"+ss+" cp_logos\"></div></a>";
		reStr += "<a href='"+productPath+"' target=\"_blank\" >" + riskName.toString() + "</a>";
		return reStr;
	}

	/*private String getImageString(Object ob) {
		String imageString = "";
		if (ob != null) {
			imageString = "<img height=\"40px\" width=\"130px\" src=\"" + ob.toString() + "\" />";
		} else {
			imageString = "<img height=\"40px\" width=\"130px\" src=\"\" />";
		}
		return imageString;
	}*/

	private CompareNonAuto createCNA(String name, String information1, String information2, String information3, String information4, String type) {
		CompareNonAuto cn = new CompareNonAuto();
		cn.setCompareName(name);
		if(information1==null || "".equals(information1)){
			cn.setInformationA("-");
		}else{
			cn.setInformationA(information1);
		}
		if(information2==null || "".equals(information2)){
			cn.setInformationB("-");
		}else{
			cn.setInformationB(information2);
		}
		if(information3==null || "".equals(information3)){
			cn.setInformationC("-");
		}else{
			cn.setInformationC(information3);
		}
		if(information4==null || "".equals(information4)){
			cn.setInformationD("-");
		}else{
			cn.setInformationD(information4);
		}
		cn.setType(type);
		return cn;
	}

	// private String getPriceInformation(Map<String, Object> map) {
	// log.info("====产品log============" + map.get("ProductLog"));
	// StringBuffer sb = new StringBuffer();
	// sb.append("<div style=\"margin-top:5px;\"><img height=\"40px\" width=\"130px\" src=\""
	// + map.get("companyLog") + "\" /></div>");
	// sb.append("<h2>" + map.get("RiskName") + "</h2>");
	// sb.append("<div class=\"ProductImgArea\">");
	// sb.append("<img src=\"" + map.get("ProductLog") +
	// "\" width=\"82px\" height=\"82px\" style=\"float:left;\"/><br  />");
	// sb.append("<div class=\"PrizeArea\">");
	// sb.append("<div class=\"AllPriceArea mt10\">");
	// sb.append("￥<strong  >" + map.get("InitPrem") + "</strong>");
	// sb.append(" </div></div>");
	// log.info("==========" + sb.toString());
	// return sb.toString();
	// }

	public String accidentInsurance() {
		products.add(getNewProduct("1", "测试1"));
		products.add(getNewProduct("2", "测试2"));
		products.add(getNewProduct("3", "测试3"));
		products.add(getNewProduct("4", "测试4"));
		products.add(getNewProduct("5", "测试5"));
		return "accidentInsurance";
	}

	private Product getNewProduct(String code, String name) {
		Product p = new Product();
		p.setName(name);
		p.setId(code);
		return p;
	}

	private void saveMyTrailNonAuto(List<String> idList, String productType, List<Map<String, Object>> compareList) {
		String memberId = (String) getSession(Member.LOGIN_MEMBER_ID_SESSION_NAME);
		if (memberId != null && !"".equals(memberId)) {
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyMMddHHmmssSSS");
			String serialNumber = sdf1.format(new Date());
			if (idList != null && idList.size() > 0) {
				MyTrailNonAuto mtna = new MyTrailNonAuto();
				mtna.setCreateDate(new Date());
				mtna.setModifyDate(new Date());
				mtna.setMemeberId(memberId);
				mtna.setProductType(productType);
				mtna.setSerialNumber(serialNumber);
				String comName = "";
				String productName = "";
				String initPrem = "";
				String DiscountPrem = "";
				List<TrailProduct> trailProducts = new ArrayList<TrailProduct>();
				for (String productCode : idList) {
					if (compareList != null && compareList.size() > 0) {
						for (Map<String, Object> mp : compareList) {
							if (productCode.equals(mp.get("riskCode").toString())) {
								if (mp.get("companyName") != null) {
									comName = mp.get("companyName").toString();
								}
								if (mp.get("RiskName") != null) {
									productName = mp.get("RiskName").toString();
								}
								if (mp.get("InitPremValue") != null) {
								
									initPrem = mp.get("InitPremValue").toString();
								}
								if (StringUtil.isNotEmpty(mp.get("DiscountPrem").toString())) {
									DiscountPrem = mp.get("DiscountPrem").toString();
									DiscountPrem = DiscountPrem.substring(DiscountPrem.indexOf(">")+1);
									DiscountPrem = DiscountPrem.substring(0,DiscountPrem.indexOf("<"));
								}
							}
						}
						
						
						
						TrailProduct tp = new TrailProduct();
						tp.setSerialNumber(serialNumber);
						tp.setProductCode(productCode);
						tp.setComName(comName);
						tp.setProductName(productName);
						if(StringUtil.isNotEmpty(DiscountPrem)){
							tp.setInitPrem(DiscountPrem);
						}else{
							tp.setInitPrem(initPrem);
						}
						trailProducts.add(tp);
					}
				}
				mtna.setTrailProducts(trailProducts);
				String mtnaId = myTNAutoService.save(mtna);
				if (trailProducts != null && trailProducts.size() > 0) {
					for (TrailProduct t : trailProducts) {
						t.setMyTrailNonAuto(myTNAutoService.get(mtnaId));
						trailProductService.save(t);
					}
				}
			}
		}
	}

	public CompareInfoServiceStub.FEMRiskCompareProperties[] getFcps() {
		return fcps;
	}

	public void setFcps(CompareInfoServiceStub.FEMRiskCompareProperties[] fcps) {
		this.fcps = fcps;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public List<Map<String, Object>> getCompareInformation() {
		return compareInformation;
	}

	public void setCompareInformation(List<Map<String, Object>> compareInformation) {
		this.compareInformation = compareInformation;
	}

	public List<RiskAppFactor> getRafs() {
		return rafs;
	}

	public void setRafs(List<RiskAppFactor> rafs) {
		this.rafs = rafs;
	}

	public List<RiskAppFactor> getDutyFactor() {
		return dutyFactor;
	}

	public void setDutyFactor(List<RiskAppFactor> dutyFactor) {
		this.dutyFactor = dutyFactor;
	}

	public ProductInfoWebServiceStub.FMRisk[] getFmr() {
		return fmr;
	}

	public void setFmr(ProductInfoWebServiceStub.FMRisk[] fmr) {
		this.fmr = fmr;
	}

	public List<CompareNonAuto> getCompareNonAutos() {
		return compareNonAutos;
	}

	public void setCompareNonAutos(List<CompareNonAuto> compareNonAutos) {
		this.compareNonAutos = compareNonAutos;
	}

	public String getA() {
		return A;
	}

	public void setA(String a) {
		A = a;
	}

	public String getB() {
		return B;
	}

	public void setB(String b) {
		B = b;
	}

	public String getC() {
		return C;
	}

	public void setC(String c) {
		C = c;
	}

	public List<CompareNonAuto> getCompareNonAutoAppFs() {
		return compareNonAutoAppFs;
	}

	public void setCompareNonAutoAppFs(List<CompareNonAuto> compareNonAutoAppFs) {
		this.compareNonAutoAppFs = compareNonAutoAppFs;
	}

	public List<CompareNonAuto> getCompareNonAutoDutys() {
		return compareNonAutoDutys;
	}

	public void setCompareNonAutoDutys(List<CompareNonAuto> compareNonAutoDutys) {
		this.compareNonAutoDutys = compareNonAutoDutys;
	}

	public int getCompareSize() {
		return compareSize;
	}

	public void setCompareSize(int compareSize) {
		this.compareSize = compareSize;
	}

	public String getD() {
		return D;
	}

	public void setD(String d) {
		D = d;
	}

	public String getProductIdA() {
		return productIdA;
	}

	public void setProductIdA(String productIdA) {
		this.productIdA = productIdA;
	}

	public String getProductIdB() {
		return productIdB;
	}

	public void setProductIdB(String productIdB) {
		this.productIdB = productIdB;
	}

	public String getProductIdC() {
		return productIdC;
	}

	public void setProductIdC(String productIdC) {
		this.productIdC = productIdC;
	}

	public String getProductIdD() {
		return productIdD;
	}

	public void setProductIdD(String productIdD) {
		this.productIdD = productIdD;
	}

	public String getProductPathA() {
		return productPathA;
	}

	public void setProductPathA(String productPathA) {
		this.productPathA = productPathA;
	}

	public String getProductPathB() {
		return productPathB;
	}

	public void setProductPathB(String productPathB) {
		this.productPathB = productPathB;
	}

	public String getProductPathC() {
		return productPathC;
	}

	public void setProductPathC(String productPathC) {
		this.productPathC = productPathC;
	}

	public String getProductPathD() {
		return productPathD;
	}

	public void setProductPathD(String productPathD) {
		this.productPathD = productPathD;
	}
}
