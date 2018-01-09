package com.sinosoft.cms.publish;

import com.sinosoft.cms.datachannel.Publisher;
import com.sinosoft.cms.dataservice.ColumnUtil;
import com.sinosoft.cms.document.Article;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.RequestImpl;
import com.sinosoft.framework.SSRS;
import com.sinosoft.framework.User;
import com.sinosoft.framework.User.UserData;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.messages.LongTimeTask;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.IntegralAction;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.platform.UserList;
import com.sinosoft.schema.AreaSchema;
import com.sinosoft.schema.AreaSet;
import com.sinosoft.schema.HealthyInfoSchema;
import com.sinosoft.schema.HealthyInfoSet;
import com.sinosoft.schema.ZCArticleSchema;
import com.sinosoft.schema.ZCArticleSet;
import com.sinosoft.schema.ZCCatalogSchema;
import com.sinosoft.schema.ZDConfigSchema;
import com.sinosoft.schema.ZDFEUIInfoValueSchema;
import com.sinosoft.schema.ZDFEUIInfoValueSet;
import com.sinosoft.schema.ZDHealthyInfoSchema;
import com.sinosoft.schema.ZDHealthyInfoSet;
import com.sinosoft.webservice.ProductWebservice;
import com.sinosoft.webservice.fDInsCom.FDInsComServiceStub;
import com.sinosoft.webservice.fDInsCom.FDInsComServiceStub.FDInsCom;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FCDoc;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FEHealthyInfo;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FEMDutyAmntPremList;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FEMDutyFactor;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FEMRiskBrightSpotList;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FEMRiskFactorList;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FEMRiskSaleArea;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FEMRiskType;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FERiskAppFactor;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FEUIInfo;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FEUIInfoValue;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FMDuty;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FMRisk;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.ProductInfoResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class DlrSynProducts {
	private static final Logger logger = LoggerFactory.getLogger(DlrSynProducts.class);

	private String mRiskcode = "";
	private List<String> mCatalogIDs;
	private List<String> mArticleIDs;
	private FMRisk mFMRisk = null;
	private static Map<String, String> mERiskTypes = null;
	private Map<String, Object> mProductFactors = new HashMap<String, Object>();

	public static boolean publishing(FMRisk fmRisk) {
		DlrSynProducts apa = new DlrSynProducts(fmRisk);
		if (apa.mRiskcode == null || apa.mRiskcode.equals("")) {
			return false;
		}
		User.setCurrent(new UserData());
		User.setRealName("系统管理员");
		User.setUserName(UserList.ADMINISTRATOR);
		User.setLogin(true);
		User.setManager(true);
		User.setBranchInnerCode("0001");
		if (!apa.syncArticlegAttribute()) {
			return false;
		}
		return true;
	}

	public static boolean publishingArea(FMRisk fmRisk) {
		DlrSynProducts apa = new DlrSynProducts(fmRisk);
		if (apa.mRiskcode == null || apa.mRiskcode.equals("")) {
			return false;
		}
		// 同步产品销售区域
		if (!apa.prepareProdcutArea()) {
			return false;
		}

		return true;
	}

	public static boolean publishingHI(FMRisk fmRisk) {
		DlrSynProducts apa = new DlrSynProducts(fmRisk);
		if (apa.mRiskcode == null || apa.mRiskcode.equals("")) {
			return false;
		}
		// 同步产品健康告知
		if (!apa.prepareHealthyInfo()) {
			return false;
		}

		return true;
	}

	public DlrSynProducts(FMRisk fmRisk) {
		if (fmRisk != null) {
			mFMRisk = fmRisk;
			mRiskcode = fmRisk.getRiskCode();
		}
		mProductFactors.clear();
	}

	private boolean syncArticlegAttribute() {
		if (!getCatalogID()) {
			return false;
		}
		if (!prepareProdcutFactorsForArticle()) {
			return false;
		}
		mArticleIDs = new ArrayList<String>();
		for (int i = 0; i < mCatalogIDs.size(); i++) {
			if (!syncOneArticlegAttribute(mCatalogIDs.get(i))) {
				return false;
			}
		}
		return true;
	}

	private boolean prepareHealthyInfo() {
		FEUIInfo[] tFEUIInfo = mFMRisk.getFEUIInfo();
		if (tFEUIInfo != null && tFEUIInfo.length > 0) {
			HealthyInfoSchema tHealthyInfoSchema1 = new HealthyInfoSchema();
			HealthyInfoSet tHealthyInfoSet = tHealthyInfoSchema1
					.query(new QueryBuilder("where productId='"
							+ mFMRisk.getRiskCode()
							+ "' and insuranceCompany='"
							+ mFMRisk.getSupplierCode() + "'"));
			if (tHealthyInfoSet.size() > 0) {
				if (!tHealthyInfoSet.delete()) {
					return false;
				}
			}
			ZDFEUIInfoValueSchema zdFEUIInfovalue1 = new ZDFEUIInfoValueSchema();
			ZDFEUIInfoValueSet zdFEUIInfovalueSet = zdFEUIInfovalue1
					.query(new QueryBuilder("where riskcode='"
							+ mFMRisk.getRiskCode() + "'"));
			if (zdFEUIInfovalueSet.size() > 0) {
				if (!zdFEUIInfovalueSet.delete()) {
					return false;
				}
			}
			ZDHealthyInfoSchema zdHealthyInfoSchema1 = new ZDHealthyInfoSchema();
			ZDHealthyInfoSet zdHealthyInfoSet = zdHealthyInfoSchema1
					.query(new QueryBuilder("where riskcode='"
							+ mFMRisk.getRiskCode() + "'"));
			if (zdHealthyInfoSet.size() > 0) {
				if (zdHealthyInfoSet.delete()) {
					return false;
				}
			}
			for (int i = 0; i < tFEUIInfo.length; i++) {
				if (tFEUIInfo[i] != null
						&& "HealthyShow".equalsIgnoreCase(tFEUIInfo[i]
								.getUIInfo())) {
					String id = mFMRisk.getSupplierCode()
							+ tFEUIInfo[i].getRiskCode()
							+ tFEUIInfo[i].getShowOrder();
					HealthyInfoSchema tSDHealthyInfoSchema = new HealthyInfoSchema();
					tSDHealthyInfoSchema.setId(id);
					tSDHealthyInfoSchema.setcreateDate(new Date());
					tSDHealthyInfoSchema.setmodifyDate(new Date());
					tSDHealthyInfoSchema.setUIInfo(tFEUIInfo[i].getUIInfo());
					tSDHealthyInfoSchema.setshowOrder(tFEUIInfo[i]
							.getShowOrder());
					tSDHealthyInfoSchema.setshowDistrict(tFEUIInfo[i]
							.getShowDistrict());
					tSDHealthyInfoSchema.setIsMustInput(tFEUIInfo[i]
							.getIsMustInput());
					tSDHealthyInfoSchema.setIsDisplay(tFEUIInfo[i]
							.getIsDisplay());
					tSDHealthyInfoSchema
							.setdataType(tFEUIInfo[i].getDataType());
					tSDHealthyInfoSchema.setinsuranceCompany(mFMRisk
							.getSupplierCode());
					tSDHealthyInfoSchema.setproductId(tFEUIInfo[i]
							.getRiskCode());
					tSDHealthyInfoSchema
							.setshowInfo(tFEUIInfo[i].getShowInfo());
					tSDHealthyInfoSchema.setshowInfoType(tFEUIInfo[i]
							.getShowInfoType());
					tSDHealthyInfoSchema.setshowInfoCode(tFEUIInfo[i]
							.getShowInfoCode());
					tSDHealthyInfoSchema.setBackUp1(tFEUIInfo[i].getBackUp1());
					if (!tSDHealthyInfoSchema.insert()) {
						return false;
					}
					FEUIInfoValue[] feuiiValue = tFEUIInfo[i]
							.getFEUIInfoValue();
					if (feuiiValue != null && feuiiValue.length > 0) {
						for (FEUIInfoValue fe : feuiiValue) {
							if (fe != null) {
								ZDFEUIInfoValueSchema zdFEUIInfoValue = new ZDFEUIInfoValueSchema();
								zdFEUIInfoValue.setRiskCode(fe.getRiskCode());
								zdFEUIInfoValue.setValueOrder(fe
										.getValueOrder());
								zdFEUIInfoValue.setValueCode(fe.getValueCode());
								zdFEUIInfoValue.setValueName(fe.getValueName());
								zdFEUIInfoValue.setRemark(fe.getRemark());
								zdFEUIInfoValue.setIsDefault(fe.getIsDefault());
								zdFEUIInfoValue.setUIInfoID(id);
								zdFEUIInfoValue.setUIInfoValueID(fe
										.getUIInfoValueID());
								FEHealthyInfo[] feHealthyInfo = fe
										.getFEHealthyInfo();
								if (!zdFEUIInfoValue.insert()) {
									return false;
								}
								if (feHealthyInfo != null
										&& feHealthyInfo.length > 0) {
									for (FEHealthyInfo feh : feHealthyInfo) {
										if (feh != null) {
											ZDHealthyInfoSchema zdHealthyInfo = new ZDHealthyInfoSchema();
											zdHealthyInfo.setRiskCode(feh
													.getRiskCode());
											zdHealthyInfo.setID(feh
													.getHealthyID());
											zdHealthyInfo.setUIInfoID(id);
											zdHealthyInfo.setUIInfoValueID(feh
													.getUIInfoValueID());
											zdHealthyInfo.setHealthyType(feh
													.getHealthyType());
											zdHealthyInfo.setIsVerify(feh
													.getIsVerify());
											zdHealthyInfo.setHealthyInfo(feh
													.getHealthyInfo());
											if (!zdHealthyInfo.insert()) {
												return false;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}

		return true;
	}

	/**
	 * 准备产品销售区域
	 * 
	 * @return
	 */
	private boolean prepareProdcutArea() {
		FEMRiskSaleArea[] tFEMRiskSaleArea = mFMRisk.getFEMRiskSaleArea();
		if (tFEMRiskSaleArea != null && tFEMRiskSaleArea.length > 0) {
			AreaSchema tSCAreaSchema1 = new AreaSchema();
			AreaSet areaSet = tSCAreaSchema1.query(new QueryBuilder(
					"where productId='" + mFMRisk.getRiskCode()
							+ "' and insuranceCompany='"
							+ mFMRisk.getSupplierCode() + "'"));
			if (areaSet.size() > 0) {
				if (!areaSet.delete()) {
					return false;
				}
			}
			HashSet<String> provinceInfo = new HashSet<String>();
			for (FEMRiskSaleArea one : tFEMRiskSaleArea) {
				if (one != null) {
					if (StringUtil.isNotEmpty(one.getProvinceCode())
							&& StringUtil.isNotEmpty(one.getProvinceName())) {

						provinceInfo.add(one.getProvinceCode() + "-"
								+ one.getProvinceName());
					}
				}
			}

			if (provinceInfo != null && provinceInfo.size() > 0) {
				Transaction trans = new Transaction();
				AreaSet areaSet1 = new AreaSet();
				for (String province : provinceInfo) {
					String provinceCode = province.split("-")[0];
					String provinceName = province.split("-")[1];
					AreaSchema tSCAreaSchema = new AreaSchema();
					String id = mFMRisk.getSupplierCode()
							+ mFMRisk.getRiskCode() + provinceCode;
					tSCAreaSchema.setId(id);
					tSCAreaSchema.setcreateDate(new Date());
					tSCAreaSchema.setmodifyDate(new Date());
					tSCAreaSchema.setname(provinceName);
					tSCAreaSchema
							.setinsuranceCompany(mFMRisk.getSupplierCode());
					tSCAreaSchema.setareaValue(provinceCode);
					tSCAreaSchema.setproductId(mFMRisk.getRiskCode());
					areaSet1.add(tSCAreaSchema);
					for (int j = 0; j < tFEMRiskSaleArea.length; j++) {
						if (provinceCode.equals(tFEMRiskSaleArea[j]
								.getProvinceCode())) {
							tSCAreaSchema = new AreaSchema();
							String son_id = tFEMRiskSaleArea[j]
									.getSupplierCode()
									+ mFMRisk.getRiskCode()
									+ tFEMRiskSaleArea[j].getCityCode();
							tSCAreaSchema.setId(son_id);
							tSCAreaSchema.setcreateDate(new Date());
							tSCAreaSchema.setmodifyDate(new Date());
							tSCAreaSchema.setname(tFEMRiskSaleArea[j]
									.getCityName());
							tSCAreaSchema.setpath(id + "," + son_id);
							tSCAreaSchema.setparent_id(id);
							tSCAreaSchema
									.setinsuranceCompany(tFEMRiskSaleArea[j]
											.getSupplierCode());
							tSCAreaSchema.setareaValue(tFEMRiskSaleArea[j]
									.getCityCode());
							tSCAreaSchema.setproductId(mFMRisk.getRiskCode());
							areaSet1.add(tSCAreaSchema);
						}
					}
				}
				trans.add(areaSet1, Transaction.INSERT);
				if (!trans.commit()) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 准备产品计算相关要素
	 * 
	 * @return
	 */
	private boolean prepareProdcutFactorsForArticle() {
		mProductFactors.put("RiskAppFactors", getRiskAppFactors(mFMRisk));
		// 投保要素
		mProductFactors.put("CalHTML", getRiskAppFactorsHTML(mFMRisk));
		mProductFactors.put("CalHTML2", getRiskAppFactorsHTML2(mFMRisk));
		// 责任要素
		mProductFactors.put("DutyHTML", getDutyHTML(mFMRisk));
		// 产品详细页责任要素
		mProductFactors.put("DutyHTML1", getDutyHTML1(mFMRisk));
		// 险种编码
		mProductFactors.put("RiskCode", mFMRisk.getRiskCode());
		// 保险公司编码
		mProductFactors.put("SupplierCode", mFMRisk.getSupplierCode());
		mProductFactors.put("SupplierCode2",
				getSupplierCode(mFMRisk.getSupplierCode()));
		// 产品人气
		mProductFactors.put("Popular", getPopular(mFMRisk));
		// 产品销量
		mProductFactors.put("SalesVolume", mFMRisk.getSalesVolume());
		// 产品适合人群
		mProductFactors.put("AdaptPeopleInfo", mFMRisk.getAdaptPeopleInfo());
		// 产品特色
		mProductFactors.put("ERiskFeatures",
				convertToHTML(mFMRisk.getERiskFeatures()));
		// 产品上线时间
		mProductFactors.put("PublishDate", mFMRisk.getPublishDate());
		// 产品亮点
		mProductFactors.put("FEMRiskBrightSpot", getBrightSoptHTML(mFMRisk));
		// 投保提示
		mProductFactors.put("FCDoc", getFCDoc(mFMRisk));
		// 产品折扣率
		mProductFactors.put("DiscountRate", mFMRisk.getBackUp2());
		// 原价
		mProductFactors.put("BasePrem", mFMRisk.getInitPrem());
		// 折扣保费
		mProductFactors.put("InitPrem", mFMRisk.getDiscountPrice());
		// 列表页面价格标签
		mProductFactors.put(
				"prodcutMarkPrice",
				getProdcutMarkPrice(mFMRisk.getBackUp2(),
						mFMRisk.getInitPrem(), mFMRisk.getDiscountPrice(),
						mFMRisk.getRiskCode(), mFMRisk.getAutoRelatedClause()));
		// 内部统计险种小类别面包屑 add by fhz 20121225
		mProductFactors.put("RiskBreadcrumb",
				getRiskBreadcrumb(mFMRisk.getBelongFlag()));

		// 投保须知
		mProductFactors.put("Instruction", getInstruction());
		// 责任信心告知
		mProductFactors.put("termInformation", getTermInformation());
		// 是否cps产品标示
		mProductFactors.put("isCpsProduct", mFMRisk.getAutoRelatedClause());
		// cps产品跳转路径
		mProductFactors.put("cpsAdress", mFMRisk.getOptionalClause());
		// 是否财险标志
		mProductFactors.put("isFinancialRisks", getProductCategories());

		// 梅俊峰 add 20130614 start
		// 可购份数
		if (StringUtils.isNotBlank(mFMRisk.getBackUp1())) {
			mProductFactors.put("limcount", mFMRisk.getBackUp1());
		} else {
			mProductFactors.put("limcount", "不限");
		}
		// 保障期限
		mProductFactors.put("DeadlineDesc", mFMRisk.getDeadlineDesc());
		// 保险公司报案理赔专线
		FDInsCom fdInsCom = getCompanyInfo(mFMRisk.getSupplierCode());
		mProductFactors.put("Phone", fdInsCom.getPhone());
		// 保险公司简述
		mProductFactors.put("CompanyDesc", fdInsCom.getCompanyDesc());
		// 保险公司名称
		mProductFactors.put("CompanyShortName", fdInsCom.getShortName());

		Map<String, String> map = getFCDoc2();
		if (map != null) {
			// 保险条款
			mProductFactors.put("Doc1", map.get("Doc1"));
			mProductFactors.put("Doc5", map.get("Doc5"));

			// 行业及职业分类表
			String tHtml = this.getOppAndPol(map);
			if (tHtml != null && !"".equals(tHtml)) {
				mProductFactors.put("Doc2", tHtml);
			}

			// 产品问题1
			mProductFactors.put("question1", map.get("question1"));
			// 产品问题2
			mProductFactors.put("question2", map.get("question2"));
			// 产品回答1
			mProductFactors.put("answer1", map.get("answer1"));
			// 产品回答2
			mProductFactors.put("answer2", map.get("answer2"));
			// 梅俊峰 add 20130614 end
		}
		return true;
	} 

	public String getOppAndPol(Map<String, String> cMap) { 
		String tHtml = "";
		if (cMap.get("Doc2") != null && cMap.get("Doc2").length() >= 1) {
		String Occup = "";
		FERiskAppFactor[] appFactors = mFMRisk.getFERiskAppFactor();
		if (appFactors != null && appFactors.length > 0
				&& appFactors[0] != null) {
			try {
				for (int i = 0; i < appFactors.length; i++) {
					FERiskAppFactor feaf = appFactors[i];
					if (feaf.getFactorType().equalsIgnoreCase("Occup")) {
						FEMRiskFactorList[] femrList = feaf
								.getFEMRiskFactorList();
						for (int j = 0; femrList != null && j < femrList.length; j++) {
							Occup += femrList[j].getFactorValue();
							if (j != femrList.length - 1) {
								Occup += "|";
							}
						}
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		if(StringUtil.isNotEmpty(Occup)){
			tHtml = tHtml
					+ "<div class=\"li_list\"><b>职业等级：</b>"+Occup+"类 <a href=\""
					+ cMap.get("Doc2")
					+ "\" target=\"_blank\">查看行业及职业分类表</a></div>";
		}else{
			tHtml = tHtml
			+ "<div class=\"li_list\"><b>职业等级：</b>"
			+ "\" target=\"_blank\">查看行业及职业分类表</a></div>";
		}
		}
		// 境外旅游险和申根签证险的情况详细页显示拒保国家
		if ("A02".equals(mFMRisk.getBelongFlag())
				|| ("A03".equals(mFMRisk.getBelongFlag()))) {
			if (cMap.get("Doc4") != null && cMap.get("Doc4").length() >= 1) {
				tHtml = tHtml
						+ "<div class=\"li_list\"><b>拒保国家：</b> <a href=\""
						+ cMap.get("Doc4")
						+ "\" target=\"_blank\">查看拒保国家明细表</a></div>";
			}
		}
		if (cMap.get("Doc3") != null && cMap.get("Doc3").length() >= 1) {
			tHtml = tHtml + "<div class=\"li_list\"><b>保单样本：</b> <a href=\""
					+ cMap.get("Doc3")
					+ "\" target=\"_blank\">查看保单样本</a></div>";
		}
		return tHtml;
	}

	// 梅俊峰 add 20130614 start
	/**
	 * 
	 * 获得保险公司名称
	 */
	public FDInsCom getCompanyInfo(String comCode) {
		Map<String, Object> paramter = new HashMap<String, Object>();
		paramter.put("SupplierCode", comCode);
		try {
			FDInsComServiceStub.FDInsComResponse comIformation = ProductWebservice
					.FDInsComServiceImpl(paramter, null);
			if (comIformation != null
					&& comIformation.getFDInsComList() != null
					&& comIformation.getFDInsComList().length > 0
					&& comIformation.getFDInsComList()[0] != null) {
				return comIformation.getFDInsComList()[0];
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	private Map<String, String> getFCDoc2() {
		Map<String, String> map = new HashMap<String, String>();
		FCDoc[] fc = mFMRisk.getFCDoc();
		if (fc == null || fc.length == 0 || fc[0] == null) {
			return null;
		}
		try {
			QueryBuilder qb = new QueryBuilder(
					"select value from zdconfig where type='ProductPath' ");
			String ProductPath = qb.executeString();
			qb = new QueryBuilder(
					"select value from zdconfig where type='ProductURL' ");
			String ProductURL = qb.executeString();

			String doc3 = "";
			String doc5 = "";
			for (int i = 0; i < fc.length; i++) {
				String href = fc[i].getDocPath().replace(ProductPath,
						ProductURL);

				if ("01".equals(fc[i].getDocTypeCode())) {
					// 保险条款
					String[] item = fc[i].getDocName().split("\\.");// 去掉保险条款后缀名
					doc3 = doc3 + "<li>下载：<a target=\"_blank\" href=\"" + href
							+ "\">" + item[0] + "</a>";

				} else if ("02".equals(fc[i].getDocTypeCode())) {
					// 行业及职业分类表
					map.put("Doc2", href);
				} else if ("03".equals(fc[i].getDocTypeCode())) {
					// 保单样本
					map.put("Doc3", href);
				} else if ("04".equals(fc[i].getDocTypeCode())) {
					// 拒保国家
					map.put("Doc4", href);
				} else if ("06".equals(fc[i].getDocTypeCode())){
					// 免除责任条款
					String[] item = fc[i].getDocName().split("\\.");// 去掉保险条款后缀名
					doc5 = doc5 + "<li>下载：<a target=\"_blank\" href=\"" + href
							+ "\">" + item[0] + "</a>";
				}
			}
			map.put("Doc1", doc3);
			map.put("Doc5", doc5);
		} catch (Exception e) {
			return null;
		}
		return map;
	}

	/**
	 * 得到险种小类的面包屑
	 * 
	 * @author fhz
	 * */
	public String getRiskBreadcrumb(String tSubType) {

		if (tSubType == null || "".equals(tSubType)) {
			logger.error(
					"同步产品信息,getRiskBreadcrumb方法,产品【{}_{}】获取产品面包屑BelongFlag信息失败！请联系产品中心管理员。",
					mFMRisk.getRiskCode(), mFMRisk.getRiskName());
			return null;
		}
		String tBreadcrumb = "";
		GetDBdata db = new GetDBdata();
		SSRS aSSRS = new SSRS();
		try {
			String querySql = "select url,name from zccatalog where producttype='"
					+ tSubType + "'";
			aSSRS = db.execSQL(querySql);
			if (aSSRS == null) {
				logger.error(
						"产品【{}_{}】,方法getRiskBreadcrumb查询险种小类名称和URL失败,",
						mFMRisk.getRiskCode(), mFMRisk.getRiskName());
				return null;
			}
			String url = aSSRS.GetText(1, 1);
			String name = aSSRS.GetText(1, 2);
			tBreadcrumb = "<a href='../" + url + "'  target='_self' >" + name
					+ "</a>";
		} catch (Exception e) {
			logger.error(
					"同步产品信息,getRiskBreadcrumb方法,产品【{}_{}】获取产品面包屑BelongFlag信息发生异常！！！",
					mFMRisk.getRiskCode(), mFMRisk.getRiskName());
		}
		return tBreadcrumb;
	}

	/**
	 *  责任信息告知
	 * 
	 * @return
	 */
	private String getTermInformation() {
		StringBuffer sb = new StringBuffer();
		FEUIInfo[] feArr = mFMRisk.getFEUIInfo();
		for (int i = 0; feArr != null && i < feArr.length; i++) {
			FEUIInfo fe = feArr[i];
			if (fe != null && "TermInformation".equalsIgnoreCase(fe.getUIInfo())
					&& "Y".equalsIgnoreCase(fe.getIsDisplay())) {
				String[] showinfo = fe.getShowInfo().split("\n");
				int count = 0;
				for (String s : showinfo) {
					sb.append("<li>"
							+ s + "</li>");
				}
			}
		}
		String str = "";
		if (sb != null && !"".equals(sb.toString())) {
			str = "<div class=\"ccp_box_con\"> <h3 class=\"n_title\">条款信息</h3><ul class=\"zr_list_conbs\">"
					+ sb.toString() + "</ul></div>";
		}
		return str;
	}
	/**
	 * 投保须知
	 * 
	 * @return
	 */
	private String getInstruction() {
		StringBuffer sb = new StringBuffer();
		FEUIInfo[] feArr = mFMRisk.getFEUIInfo();
		for (int i = 0; feArr != null && i < feArr.length; i++) {
			FEUIInfo fe = feArr[i];
			if (fe != null && "Information".equalsIgnoreCase(fe.getUIInfo())
					&& "Y".equalsIgnoreCase(fe.getIsDisplay())) {
				String[] showinfo = fe.getShowInfo().split("\n");
				int count = 0;
				for (String s : showinfo) {
					sb.append("<li><em>" + (++count) + "、</em>"
							+ s.substring(2) + "</li>");
				}
			}
		}
		String str = "";
		if (sb != null && !"".equals(sb.toString())) {
			str = "<div class=\"ccp_box_con\"> <h3 class=\"n_title\">投保须知</h3><ul class=\"zr_list_conbs\">"
					+ sb.toString() + "</ul></div>";
		}
		return str;
	}
	/**
	 * 
	 * @param DiscountRate
	 *            折扣率
	 * @param InitPrem
	 *            折扣保费
	 * @param BasePrem
	 *            原价
	 * @param productID
	 *            产品编码
	 * @return
	 */
	private String getProdcutMarkPrice(String DiscountRate, String InitPrem,
			String DiscountPrice, String productID, String isCpsProduct) {
		String content = "";
		if (StringUtil.isEmpty(DiscountRate)) {
			if ("01".equals(isCpsProduct)) {
				DecimalFormat df = new DecimalFormat("0");
				content += "<li class=\"priceC\"> <span id=\"productPrem_"
						+ productID
						+ "\" ><em>￥</em>"
						+ df.format(Double.parseDouble(DiscountPrice))
						+ "<i>起</i></span><span style=\"display:none\" id=\"productRatePrem_"
						+ productID + "\"></span></li>";
			} else {
				content += "<li class=\"priceC\"> <span id=\"productPrem_"
						+ productID
						+ "\" ><em>￥</em>"
						+ DiscountPrice
						+ "</span><span style=\"display:none\" id=\"productRatePrem_"
						+ productID + "\"></span></li>";
			}
		} else {
			content += "<li class=\"priceA\"><span id=\"productPrem_"
					+ productID + "\" class=\"CurrentPrice\"><em>￥</em>"
					+ DiscountPrice + "</span></li>";
			content += "<li class=\"priceB\"><span >原价：</span><span class=\"priceB_\" id=\"productRatePrem_"
					+ productID + "\"  >" + InitPrem + "</span></li>";
		}
		return content;
	}

	private String getFCDoc(FMRisk tFMRisk) {
		FCDoc[] fc = mFMRisk.getFCDoc();
		String FCDoc = "";
		if (fc == null || fc.length == 0 || fc[0] == null) {
			return "";
		}
		try {
			QueryBuilder qb = new QueryBuilder(
					"select value from zdconfig where type='ProductPath' ");
			String ProductPath = qb.executeString();
			qb = new QueryBuilder(
					"select value from zdconfig where type='ProductURL' ");
			String ProductURL = qb.executeString();

			FCDoc = "<ul >";
			for (int i = 0; i < fc.length; i++) {
				String href = fc[i].getDocPath().replace(ProductPath,
						ProductURL);
				FCDoc += "<li style=\"padding-top: 3px;\">";
				FCDoc += "<a href=\"" + href
						+ "\" target=\"_blank\" style=\"color: blue;\"> ";
				FCDoc += fc[i].getDocName() + "</a></li>";
			}
			FCDoc += "</ul>";
		} catch (Exception e) {
			return "";
		}
		return FCDoc;
	}

	private String getSupplierCode(String supplierCode) {
		if (supplierCode != null && supplierCode.length() > 4) {
			return supplierCode.substring(0, 4);
		}
		return supplierCode;
	}

	private String convertToHTML(String eRiskFeatures) {
		if (eRiskFeatures != null && !"".equals(eRiskFeatures)) {
			eRiskFeatures = eRiskFeatures.replaceAll("\n", "<br />");
			return eRiskFeatures;
		}
		return null;
	}

	private Object getPopular(FMRisk mFMRisk2) {
		String popular = mFMRisk.getPopular();
		if (popular != null && !"".equals(popular)) {
			popular = popular.replaceAll("星", "").replace(".", "");
		} else {
			popular = "0";
		}
		return popular;
	}

	private String getRiskAppFactorsHTML(FMRisk tFMRisk) {
		String tHTML = "<div id=\"divRiskAppFactor_" + tFMRisk.getRiskCode()
				+ "\" class=\"bz_time clearfix\">";
		FERiskAppFactor[] appFactors = tFMRisk.getFERiskAppFactor();
		if (appFactors != null && appFactors.length > 0
				&& appFactors[0] != null) {
			try {
				String eleHTML = "";
				String displayStr = "";
				for (int i = 0; i < appFactors.length; i++) {
					if ("Y".equalsIgnoreCase(appFactors[i].getIsDisplay())) {
						displayStr = "";
					} else {
						displayStr = "style=\"display:none;\"";
					}
					switch (Integer.valueOf(appFactors[i].getDataType())) {
					case 7:
						if ("TextAge".equals(appFactors[i].getFactorType())) {
							int minAge = getDefaultAge(
									appFactors[i].getFEMRiskFactorList(), "min");
							int maxAge = getDefaultAge(
									appFactors[i].getFEMRiskFactorList(), "max");
							String minLmtDate = PubFun
									.calDate(PubFun.getCurrentDate(), -maxAge,
											"Y", null);
							String maxLmtDate = PubFun
									.calDate(PubFun.getCurrentDate(), -minAge,
											"Y", null);
							eleHTML = "<div><span class=\"CAppFactorName\">生日</span><input style=\"float:left;\" id=\"inpRiskAppFactor_"
									+ appFactors[i].getFactorType()
									+ "\" name=\"inpRiskAppFactor_"
									+ appFactors[i].getFactorType()
									+ "\" value=\""
									+ maxLmtDate
									+ "\""
									+ "\" onclick=\"WdatePicker({skin:'whyGreen',minDate:'"
									+ minLmtDate
									+ "',maxDate:'"
									+ maxLmtDate
									+ "'});\" onchange=\"premRecal('"
									+ tFMRisk.getRiskCode()
									+ "', this, '"
									+ appFactors[i].getAppFactorCode()
									+ "_"
									+ appFactors[i].getFactorType()
									+ "');\" /><ul id=\""
									+ appFactors[i].getAppFactorCode()
									+ "_"
									+ appFactors[i].getFactorType()
									+ "\" style=\"width:200px;\">"
									+ "<li class=\"li_selected\"><span id=\""
									+ appFactors[i].getFactorType()
									+ "_0\" name=\""
									+ minAge
									+ "Y\" >"
									+ minAge + "周岁</span></li>" + "</ul></div>";
							break;
						}
					case 1:
					case 2:
					case 3:
					case 4:
					case 5:
					case 6:
					case 8:

						if ("Period".equals(appFactors[i].getFactorType())) {
							eleHTML = "<div "
									+ displayStr
									+ " id='UlDayBelongs' class=\"tb_age clearfix\" ><em class=\"cp_title_ms\">"
									+ appFactors[i].getFactorTypeName()
									+ "：</em><ul id=\""
									+ appFactors[i].getAppFactorCode()
									+ "_"
									+ appFactors[i].getFactorType()
									+ "\">"
									+ getPeriodValuesHTML(
											tFMRisk.getRiskCode(),
											appFactors[i]
													.getFEMRiskFactorList());
							if (StringUtils.isNotBlank(mFMRisk
									.getDeadlineDesc())) {
								eleHTML = eleHTML
										+ "<div class=\"tishi_day clearfix\">"
										+ mFMRisk.getDeadlineDesc() + "</div>";
							}
							eleHTML = eleHTML + "<div class=\"clear\"></div>";
						} else if ("TextAge".equals(appFactors[i]
								.getFactorType())) {
							eleHTML = "<div class=\"tb_age clearfix\""
									+ displayStr
									+ " ><em class=\"cp_title_ms\">"
									+ appFactors[i].getFactorTypeName()
									+ "：</em><ul id=\""
									+ appFactors[i].getAppFactorCode()
									+ "_"
									+ appFactors[i].getFactorType()
									+ "\">"
									+ getRiskAppFactorValuesHTML(
											tFMRisk.getRiskCode(),
											appFactors[i]
													.getFEMRiskFactorList(),
											"00") + "</ul></div>";
							eleHTML = eleHTML + "<div class=\"clear\"></div>";

						} else if ("Plan".equals(appFactors[i].getFactorType())) {
							eleHTML = "<div class=\"paln_con clearfix\""
									+ displayStr
									+ " ><em class=\"cp_title_ms\">"
									+ appFactors[i].getFactorTypeName()
									+ "：</em><ul id=\""
									+ appFactors[i].getAppFactorCode()
									+ "_"
									+ appFactors[i].getFactorType()
									+ "\">"
									+ getRiskAppFactorValuesHTML(
											tFMRisk.getRiskCode(),
											appFactors[i]
													.getFEMRiskFactorList(),
											"00") + "</ul></div>";
							eleHTML = eleHTML + "<div class=\"clear\"></div>";

						} else {
							eleHTML = "<div "
									+ displayStr
									+ " class=\"tb_age clearfix\" ><em class=\"cp_title_ms\">"
									+ appFactors[i].getFactorTypeName()
									+ "：</em><ul id=\""
									+ appFactors[i].getAppFactorCode()
									+ "_"
									+ appFactors[i].getFactorType()
									+ "\">"
									+ getRiskAppFactorValuesHTML(
											tFMRisk.getRiskCode(),
											appFactors[i]
													.getFEMRiskFactorList(),
											"00") + "</ul></div>";
						}
						break;
					default:
						eleHTML = "";
						break;
					}
					tHTML += eleHTML;
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				tHTML = "<div class=\"CRiskAppFactor\">";
			}
		}
		tHTML += "</div>";
		return tHTML;
	}

	/**
	 * 获取年龄默认极值
	 * 
	 * @param femRiskFactorLists
	 * @param string
	 * @return
	 */
	private static int getDefaultAge(FEMRiskFactorList[] femRiskFactorLists,
			String tFlag) {
		if (femRiskFactorLists != null && femRiskFactorLists.length > 0
				&& femRiskFactorLists[0] != null) {
			String value1 = femRiskFactorLists[0].getFactorValue();
			String value2 = femRiskFactorLists[femRiskFactorLists.length - 1]
					.getFactorValue();
			if (value1 == null || "".equals(value1)) {
				value1 = value2;
			}
			if (value2 == null || "".equals(value2)) {
				value1 = value2 = "18Y";
			}
			if (value1.indexOf("-") > 0) {
				// 比较复杂，一般情况下，年龄通过日期的方式展示，应该不存在年龄段，先不实现了。
			}
			int iValue1 = Integer.parseInt(value1.substring(0,
					value1.indexOf("Y")));
			int iValue2 = Integer.parseInt(value2.substring(0,
					value2.indexOf("Y")));
			if ("min".equalsIgnoreCase(tFlag)) {
				return iValue1 < iValue2 ? iValue1 : iValue2;
			}
			return iValue1 > iValue2 ? iValue1 : iValue2;
		} else {
			if ("min".equalsIgnoreCase(tFlag)) {
				return 18;
			}
			return 50;
		}
	}

	private static String getRiskAppFactorsHTML2(FMRisk tFMRisk) {
		String tHTML = "<div id=\"divRiskAppFactor_" + tFMRisk.getRiskCode()
				+ "\" class=\"CRiskAppFactor\">";
		FERiskAppFactor[] appFactors = tFMRisk.getFERiskAppFactor();
		if (appFactors != null && appFactors.length > 0
				&& appFactors[0] != null) {
			try {
				String eleHTML = "";
				String displayStr = "";
				for (int i = 0; i < appFactors.length; i++) {

					if ("Y".equalsIgnoreCase(appFactors[i].getIsDisplay())) {
						displayStr = "";
					} else {
						displayStr = "style=\"display:none;\"";
					}
					if ("Occup".equals(appFactors[i].getFactorType())) {
						displayStr = "style=\"display:none;\"";
					}
					if (appFactors[i].getFEMRiskFactorList() != null
							&& appFactors[i].getFEMRiskFactorList().length > 0
							&& appFactors[i].getFEMRiskFactorList()[0] != null) {
						if (appFactors[i].getFEMRiskFactorList().length == 1) {
							eleHTML = "<div "
									+ displayStr
									+ " ><span>"
									+ appFactors[i].getFactorTypeName()
									+ "</span><ul id=\""
									+ appFactors[i].getAppFactorCode()
									+ "_"
									+ appFactors[i].getFactorType()
									+ "\">"
									+ getRiskAppFactorValuesHTML(
											tFMRisk.getRiskCode(),
											appFactors[i]
													.getFEMRiskFactorList(),
											"00") + "</ul></div>";
						} else {
							eleHTML = "<div "
									+ displayStr
									+ " ><span>"
									+ appFactors[i].getFactorTypeName()
									+ "</span><select id=\""
									+ tFMRisk.getRiskCode()
									+ "_"
									+ appFactors[i].getFactorType()
									+ "\" name=\""
									+ appFactors[i].getFactorType()
									+ "\" onchange=\"premRecal('"
									+ tFMRisk.getRiskCode()
									+ "', this, '"
									+ tFMRisk.getRiskCode()
									+ "_"
									+ appFactors[i].getFactorType()
									+ "');\" >"
									+ getRiskAppFactorValuesHTML(
											tFMRisk.getRiskCode(),
											appFactors[i]
													.getFEMRiskFactorList(),
											"08") + "</select></div>";
						}
					}
					tHTML += eleHTML;
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				tHTML = "<div class=\"CRiskAppFactor\">";
			}
		}
		tHTML += "</div>";
		return tHTML;
	}

	/**
	 * 
	 * @param femRiskFactorList
	 * @param riskcode
	 * @return
	 */
	private static String getPeriodValuesHTML(String riskcode,
			FEMRiskFactorList[] femRiskFactorList) {
		String tHTML = "";
		if (femRiskFactorList != null && femRiskFactorList.length > 0
				&& femRiskFactorList[0] != null) {
			int len = femRiskFactorList.length;
			for (int i = 0; i < len; i++) {
				if (i == 0) {
					tHTML += "<li class=\"li_selected\"><span id=\""
							+ femRiskFactorList[i].getFactorType() + "_" + i
							+ "\" name=\""
							+ femRiskFactorList[i].getFactorValue()
							+ "\" onclick=\"premRecal('" + riskcode
							+ "', this, '"
							+ femRiskFactorList[i].getAppFactorCode() + "_"
							+ femRiskFactorList[i].getFactorType() + "');\">"
							+ femRiskFactorList[i].getFactorDisplayValue()
							+ "</span></li>";
				} else {
					if (len > 10 && (i > 7 && i < len - 2)) {
						continue;
					}
					tHTML += "<li><span id=\""
							+ femRiskFactorList[i].getFactorType() + "_" + i
							+ "\" name=\""
							+ femRiskFactorList[i].getFactorValue()
							+ "\" onclick=\"premRecal('" + riskcode
							+ "', this, '"
							+ femRiskFactorList[i].getAppFactorCode() + "_"
							+ femRiskFactorList[i].getFactorType() + "');\">"
							+ femRiskFactorList[i].getFactorDisplayValue()
							+ "</span></li>";
				}

			}
			if (len > 10) {
				tHTML += "<li class=\"\"><span  id=\"LiDayItemAuto\" onClick=\"DaySelectAuto('DayItem','Auto')\" class=\"help_selec_ss\" >帮您选天数▼</span></li></ul></div>";

				tHTML = tHTML
						+ "<div class=\"clear\"></div>"
						+ "<div id=\"help_select_day\" class=\"clearfix\" style=\"display:none;\">"
						+ "<div class=\"seleday\">出发日期：</div>"
						+ "<div class=\"help_day\"><input id=\"txtStartDay\" type=\"text\" class=\"input_selectday\"  name=\"txtStartDay\" onClick=\"WdatePicker({skin:'whyGreen',minDate:'%y-%M-#{%d+1}'})\" onFocus=\"ErroClear('DayE')\" onBlur=\"isBirthDate('txtStartDay','DayE')\" /></div>"
						+ "<div class=\"seleday\">结束日期：</div>"
						+ "<div class=\"help_day\"><input name=\"txtEndDay\" type=\"text\" id=\"txtEndDay\"  onclick=\"WdatePicker({skin:'whyGreen',minDate:'%y-%M-{%d+1}'})\" class=\"input_selectday\" onFocus=\"ErroClear('DayE')\"  onblur=\"isBirthDate('txtEndDay','DayE')\"/></div>"
						+ "<div class=\"help_day clearfix\"><input name=\"\" type=\"button\" class=\"input_okday input_btn\" value=\"确定\"  onclick=\"selfDefineDay('txtStartDay','txtEndDay','"
						+ riskcode
						+ "')\"/></div>"
						+ "</div><div class=\"tishi_day \" id=\"DayE\" style=\"color: Red\"></div>";
			} else {
				tHTML += "</ul></div>";
			}
		}
		return tHTML;
	}

	/**
	 * 
	 * @param femRiskFactorList
	 * @param listType
	 *            04-单选 05-多选 08-下拉选择
	 * @return
	 */
	private static String getRiskAppFactorValuesHTML(String riskcode,
			FEMRiskFactorList[] femRiskFactorList, String listType) {
		String tHTML = "";
		if (femRiskFactorList != null && femRiskFactorList.length > 0
				&& femRiskFactorList[0] != null) {
			// if ("04".equals(listType) || "05".equals(listType)) {
			if (!"08".equals(listType)) {
				for (int i = 0; i < femRiskFactorList.length; i++) {
					if (i == 0) {
						tHTML += "<li class=\"li_selected\"><span id=\""
								+ femRiskFactorList[i].getFactorType() + "_"
								+ i + "\" name=\""
								+ femRiskFactorList[i].getFactorValue()
								+ "\" onclick=\"premRecal('" + riskcode
								+ "', this, '"
								+ femRiskFactorList[i].getAppFactorCode() + "_"
								+ femRiskFactorList[i].getFactorType()
								+ "');\">"
								+ femRiskFactorList[i].getFactorDisplayValue()
								+ "</span></li>";
					} else {
						tHTML += "<li><span id=\""
								+ femRiskFactorList[i].getFactorType() + "_"
								+ i + "\" name=\""
								+ femRiskFactorList[i].getFactorValue()
								+ "\" onclick=\"premRecal('" + riskcode
								+ "', this, '"
								+ femRiskFactorList[i].getAppFactorCode() + "_"
								+ femRiskFactorList[i].getFactorType()
								+ "');\">"
								+ femRiskFactorList[i].getFactorDisplayValue()
								+ "</span></li>";
					}

				}
			} else {
				for (int i = 0; i < femRiskFactorList.length; i++) {
					if (i == 0) {
						tHTML += "<option selected value=\""
								+ femRiskFactorList[i].getFactorValue() + "\">"
								+ femRiskFactorList[i].getFactorDisplayValue()
								+ "</option>";
					} else {
						tHTML += "<option value=\""
								+ femRiskFactorList[i].getFactorValue() + "\">"
								+ femRiskFactorList[i].getFactorDisplayValue()
								+ "</option>";
					}
				}
			}
		}
		return tHTML;
	}

	public static String getDutyHTML(FMRisk tFMRisk) {
		FMDuty[] fMDuty = tFMRisk.getFMDuty();
		Map<String, String> map = new HashMap<String, String>();
		String tHTML = "<div id=\"divRiskAppDuty_" + tFMRisk.getRiskCode()
				+ "\" class=\"CRiskAppDuty\" >";
		if (fMDuty != null && fMDuty.length > 0 && fMDuty[0] != null) {
			try {
				FEMDutyFactor dutyFactor = null;
				String defaultValue = "";
				tHTML += "<div class=\"title\">"
						+ "<span class=\"CDutyCol_1\">责任项目</span>"
						+ "<span class=\"CDutyCol_2\">保额</span>"
						+ "<span class=\"CDutyCol_3\">责任描述</span></div>";
				FEMRiskFactorList[] mPlanList = getPlanList(tFMRisk
						.getFERiskAppFactor());// 得到该产品的计划列表 add by fhz 2013-1-6
				int x = 0;
				int y = 0;
				for (FMDuty duty : fMDuty) {
					if ("Y".equals(duty.getIsDisplay())) {
						x++;
						y++;
						x = x % 2;
						if (y >= 5) {
							y = 5;
						}
						dutyFactor = duty.getFEMDutyFactor();
						FEMDutyAmntPremList[] AmntPremValues = dutyFactor
								.getFEMDutyAmntPremList();
						if (AmntPremValues != null && AmntPremValues.length > 0
								&& AmntPremValues[0] != null) {
							defaultValue = AmntPremValues[0].getAmnt();
						} else {
							defaultValue = "-";
						}
						tHTML += "<div class=\"list_" + y + " row" + x
								+ "\" id=\"Duty_"
								+ dutyFactor.getDutyFactorID() + "\" >";
						tHTML += "<span class=\"CDutyCol1\">"
								+ duty.getDutyName() + "</span>";

						if ("Y".equals(dutyFactor.getIsRelaRiskFactor())) {
							// getDutyFactorValuesHTML 加了一入参 mPlanList 产品的计划列表
							// add by fhz 2013-1-6
							tHTML += getDutyFactorValuesHTML(AmntPremValues,
									defaultValue, 999,
									tFMRisk.getFERiskAppFactor(), map,
									mPlanList);
						} else if ("Y".equals(dutyFactor.getIsPremCalFacotor())) {
							int iType = 1;
							if (dutyFactor.getDataType() != null
									&& "".equals(dutyFactor.getDataType())) {
								iType = Integer.valueOf(dutyFactor
										.getDataType());
							}
							tHTML += getDutyFactorValuesHTML(AmntPremValues,
									defaultValue, iType, null, map, mPlanList);
						} else {
							tHTML += "<span class=\"CDutyCol2\">"
									+ defaultValue + "</span>";
						}
						tHTML += "<span class=\"CDutyCol3\">"
								+ duty.getDefine() + "</span>";
						tHTML += "</div>";
					}
				}

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				tHTML = "<div class=\"CRiskAppDuty\" >";
			}
		}
		tHTML += "</div>";
		return tHTML;
	}

	public static String getDutyHTML1(FMRisk tFMRisk) {
		FMDuty[] fMDuty = tFMRisk.getFMDuty();
		int len = fMDuty.length;
		if (len > 1) {
			FMDuty sort = new FMDuty();
			for (int i = 0; i < len; i++) {
				if (i != len - 1) {
					if (fMDuty[i].getDutyTypeP().compareTo(
							fMDuty[i + 1].getDutyTypeP()) > 0) {
						sort = fMDuty[i];
						fMDuty[i] = fMDuty[i + 1];
						fMDuty[i + 1] = sort;
					}
				} else {
					i = 0;
					len--;

				}
			}
			len = fMDuty.length;
			for (int i = 0; i < len; i++) {
				if (i != len - 1) {
					if (fMDuty[i].getDutyTypeP().compareTo(
							fMDuty[i + 1].getDutyTypeP()) > 0) {
						sort = fMDuty[i];
						fMDuty[i] = fMDuty[i + 1];
						fMDuty[i + 1] = sort;
					}
				}
			}

		}
		Map<String, String> map = new HashMap<String, String>();
		// String tHTML = "<div id=\"divRiskAppDuty_" + tFMRisk.getRiskCode() +
		// "\" class=\"CRiskAppDuty\" >";
		String tHTML = "<div id=\"divRiskAppDuty_"
				+ tFMRisk.getRiskCode()
				+ "\" class=\"bzzz_boxtable\" > <table width=\"100%\" border=\"0\" id=\"gh_tables\">";
		if (fMDuty != null && fMDuty.length > 0 && fMDuty[0] != null) {
			try {
				FEMDutyFactor dutyFactor = null;
				String defaultValue = "";
				// tHTML += "<div class=\"title\">" +
				// "<span class=\"CDutyCol_1\">责任项目</span>" +
				// "<span class=\"CDutyCol_2\">保额</span>" +
				// "<span class=\"CDutyCol_3\">责任描述</span></div>";

				tHTML += " <tr>  <th scope=\"col\" width=\"25%\">责任项目</th>    <th scope=\"col\" width=\"13%\">保额</th>	    <th scope=\"col\">责任描述</th>  </tr>";
				FEMRiskFactorList[] mPlanList = getPlanList(tFMRisk
						.getFERiskAppFactor());// 得到该产品的计划列表 add by fhz 2013-1-6
				int x = 0;
				int y = 0;
				String dutyTypeP = "";
				int count = 0;
				String dutyDsc = "";
				for (FMDuty duty : fMDuty) {
					if ("Y".equals(duty.getIsDisplay())) {
						x++;
						y++;
						x = x % 2;
						if (y >= 5) {
							y = 5;
						}
						dutyFactor = duty.getFEMDutyFactor();
						FEMDutyAmntPremList[] AmntPremValues = dutyFactor
								.getFEMDutyAmntPremList();
						if (AmntPremValues != null && AmntPremValues.length > 0
								&& AmntPremValues[0] != null) {
							defaultValue = AmntPremValues[0].getAmnt();
						} else {
							defaultValue = "-";
						}

						if (0 == count
								|| !dutyTypeP.equals(duty.getDutyTypeP())) {
							tHTML += "<tr><td colspan=\"3\"><b>"
//									+ duty.getDutyTypeP() + " "        wangcaiyun 2013/08/26 详细页 
									+ duty.getDutyNameP() + "</b></td></tr>";
							dutyTypeP = duty.getDutyTypeP();
						}
						count++;
						if (StringUtils.isNotBlank(duty.getDutyDesc())) {
							dutyDsc = "<img = \"tap\"  title=\""
									+ duty.getDutyDesc()
									+ "\"  class=\"demo-basic\" src=\"../images/ask_03.jpg\">";
						} else {
							dutyDsc = "";
						}
						tHTML += "<tr ><div class=\"list_" + y + " row" + x
								+ "\" id=\"Duty_"
								+ dutyFactor.getDutyFactorID() + "\" >";
						tHTML += "<td class=\"CDutyCol1\"> <span>"
								+ duty.getDutyName() + dutyDsc + "</span></td>";

						if ("Y".equals(dutyFactor.getIsRelaRiskFactor())) {
							// getDutyFactorValuesHTML 加了一入参 mPlanList 产品的计划列表
							// add by fhz 2013-1-6
							tHTML += getDutyFactorValuesHTML(AmntPremValues,
									defaultValue, 999,
									tFMRisk.getFERiskAppFactor(), map,
									mPlanList);
						} else if ("Y".equals(dutyFactor.getIsPremCalFacotor())) {
							int iType = 1;
							if (dutyFactor.getDataType() != null
									&& "".equals(dutyFactor.getDataType())) {
								iType = Integer.valueOf(dutyFactor
										.getDataType());
							}
							tHTML += getDutyFactorValuesHTML(AmntPremValues,
									defaultValue, iType, null, map, mPlanList);
						} else {
							tHTML += "<td class=\"CDutyCol2\">" + defaultValue
									+ "</td>";
						}
						tHTML += "<td class=\"CDutyCol3\"><span>"
								+ duty.getDefine() + "</span></td>";
						tHTML += "</div></tr>";
					}
				}

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				tHTML = "</table><div class=\"CRiskAppDuty\" >";
			}
		}
		tHTML += "</table></div>";
		return tHTML;
	}

	/**
	 * 得到产品计划列表
	 * 
	 * @author fhz 20130108
	 * 
	 * ***/
	private static FEMRiskFactorList[] getPlanList(
			FERiskAppFactor[] feRiskAppFactor) {
		FEMRiskFactorList[] tPlanList = null;
		for (int i = 0; i < feRiskAppFactor.length; i++) {
			if ("plan".equalsIgnoreCase(feRiskAppFactor[i].getFactorType())) {// 得到计划要素的数组
				tPlanList = feRiskAppFactor[i].getFEMRiskFactorList().clone();
			}
		}
		return tPlanList;
	}

	/**
	 * 
	 * @param amntPremValues
	 * @param defaultValue
	 * @param iDataType
	 *            4-单选 5-多选 8-下拉选择 999-关联投保要素
	 * @return
	 */
	private static String getDutyFactorValuesHTML(
			FEMDutyAmntPremList[] amntPremValues, String defaultValue,
			int iDataType, FERiskAppFactor[] tFERiskAppFactors,
			Map<String, String> map, FEMRiskFactorList[] tPlanList) {
		String tHTML = "";
		if (amntPremValues != null && amntPremValues.length > 0
				&& amntPremValues[0] != null) {
			try {
				String eleHTML = "";

				switch (iDataType) {
				case 1:
				case 2:
				case 3:
				case 7:
					eleHTML = "<td class=\"CDutyCol2\">" + defaultValue
							+ "</td>";
					break;
				case 4:
					for (int j = 0; j < amntPremValues.length; j++) {
						eleHTML = "<input id=\""
								+ amntPremValues[j].getDutyFactorID() + "_" + j
								+ "\" name=\"" + amntPremValues[j].getAmnt()
								+ "\" type=\"checkbox\"";
						if (j > 0) {
							eleHTML += " checked=\"true\"";
						}
						eleHTML += "/>";
					}
					break;
				case 5:
					for (int j = 0; j < amntPremValues.length; j++) {
						eleHTML = "<input id=\""
								+ amntPremValues[j].getDutyFactorID() + "_" + j
								+ "\" name=\"" + amntPremValues[j].getAmnt()
								+ "\" type=\"radio\"";
						if (j > 0) {
							eleHTML += " selected=\"true\"";
						}
						eleHTML += "/>";
					}
					break;
				case 6:
					eleHTML = "<textarea class=\"CDutyCol2\" id=\""
							+ amntPremValues[0].getDutyFactorID()
							+ "\" name=\""
							+ amntPremValues[0].getDutyFactorID() + "\" >"
							+ defaultValue + "</textarea>";
					break;
				case 8:
					eleHTML = "<select class=\"CDutyCol2\" id=\""
							+ amntPremValues[0].getDutyFactorID()
							+ "\" name=\""
							+ amntPremValues[0].getDutyFactorID()
							+ "\" value=\"" + defaultValue + "\" >";
					for (int j = 0; j < amntPremValues.length; j++) {
						eleHTML += "<option>"
								+ amntPremValues[j].getAppFactorValue()
								+ "</option>";
					}
					eleHTML += "</select>";
					break;
				case 999:
					tHTML += "<td class=\"CDutyCol2\">";

					for (int j = 0; j < amntPremValues.length; j++) {
						eleHTML = "<span class=\"CDutyCol2\" id=\"" + amntPremValues[j].getDutyFactorID() + "_" + amntPremValues[j].getAppFactorCode() + "_" + amntPremValues[j].getAppFactorValue() + "\"";
						if (!amntPremValues[j].getAppFactorValue().equals(
								getDutyAppFactorDefaultValue(map,
										amntPremValues[j].getAppFactorCode(),
										tFERiskAppFactors))) {
							eleHTML += " style=\"display:none;\"";
						}

						eleHTML += ">" + amntPremValues[j].getAmnt();
						eleHTML += "</span>";
						tHTML += eleHTML;
						eleHTML = "";
					}
					if (tPlanList == null) {
						tHTML += "</span></td>";
						break;
						// ↓↓↓↓↓↓ to fhz 要是关联的不是计划呢，空指针了
					}
					// 得到产品计划列表 ,clone 原来的数组，防止下面操作改变原LIST的值 begin fhz 20130108
					FEMRiskFactorList[] planList = tPlanList.clone();
					// 如果产品计划的个数与该责任保额条数不相等，找出planList中多余的元素，补上他的保额 begin fhz
					if (planList != null && planList[0] != null
							&& planList.length > 0 && amntPremValues.length > 0
							&& amntPremValues.length != planList.length) {
						for (int i = 0; i < planList.length; i++) {
							for (int j = 0; j < amntPremValues.length; j++) {
								if (planList[i] != null
										&& planList[i].getFactorValue() != null
										&& amntPremValues[j]
												.getAppFactorValue() != null
										&& planList[i]
												.getFactorValue()
												.equalsIgnoreCase(
														amntPremValues[j]
																.getAppFactorValue())) {
									planList[i] = null;
									break;// 置空后结束当前循环
								}
							}
							// 不为空，则该计划没有响应的保额，"-"
							if (planList[i] != null) {
								eleHTML = "<span class=\"CDutyCol2\" id=\"" + amntPremValues[0].getDutyFactorID() + "_" + amntPremValues[0].getAppFactorCode() + "_" + planList[i].getFactorValue() + "\"";
								// if
								// (!planList[i].getFactorValue().equals(getDutyAppFactorDefaultValue(map,
								// amntPremValues[0].getAppFactorCode(),
								// tFERiskAppFactors))) {
								// 这里此时的map在前面已经有值了，但是接口传来的信息，amntPremValues[0].getAppFactorCode()代码都和FERiskAppFactorsAppFactorCode不一样，上面那个方法此时失效
								// 这里以后最好改webservice的传参，程序处理不好控制
								if (!map.containsValue(planList[i]
										.getFactorValue())) {
									eleHTML += " style=\"display:none;\"";
								}
								eleHTML += ">" + "-";
								eleHTML += "</span>";
								tHTML += eleHTML;
								eleHTML = "";
							}
						}
					}
					// end fhz
					tHTML += "</span></td>";
					break;
				default:
					eleHTML = "";
					break;
				}
				tHTML += eleHTML;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				tHTML = "";
			}
		}
		return tHTML;
	}

	private static Object getDutyAppFactorDefaultValue(Map<String, String> map,
			String appFactorCode, FERiskAppFactor[] tFERiskAppFactors) {
		if (map.get(appFactorCode) != null) {
			return map.get(appFactorCode);
		} else {
			if (tFERiskAppFactors != null && tFERiskAppFactors.length > 0
					&& tFERiskAppFactors[0] != null) {
				String defaultValue = "";
				FEMRiskFactorList[] factorValues = null;
				for (int i = 0; i < tFERiskAppFactors.length; i++) {
					if (appFactorCode.equals(tFERiskAppFactors[i]
							.getAppFactorCode())) {
						factorValues = tFERiskAppFactors[i]
								.getFEMRiskFactorList();
						if (factorValues != null && factorValues.length > 0
								&& factorValues[0] != null) {
							defaultValue = factorValues[0].getFactorValue();
							break;
						}
					}
				}
				if (defaultValue != null && !"".equals(defaultValue)) {
					map.put(appFactorCode, defaultValue);
					return defaultValue;
				}
			}
		}
		return null;
	}

	public static Map<String, Object> getRiskAppFactors(FMRisk tFMRisk) {
		FERiskAppFactor[] fERiskAppFactors = tFMRisk.getFERiskAppFactor();
		Map<String, Object> map = new HashMap<String, Object>();
		if (fERiskAppFactors != null && fERiskAppFactors.length > 0
				&& fERiskAppFactors[0] != null) {
			for (int i = 0; i < fERiskAppFactors.length; i++) {
				if ("Y".equalsIgnoreCase(fERiskAppFactors[i].getIsMustInput())) {
					map.put(fERiskAppFactors[i].getFactorType(),
							getRiskAppFactorValues(fERiskAppFactors[i]
									.getFEMRiskFactorList()));
				}
			}
		}
		return map;
	}

	private static String[] getRiskAppFactorValues(
			FEMRiskFactorList[] appFactorValues) {
		String[] factorValues = null;
		if (appFactorValues != null && appFactorValues.length > 0
				&& appFactorValues[0] != null) {
			factorValues = new String[appFactorValues.length];
			for (int i = 0; i < appFactorValues.length; i++) {
				factorValues[i] = appFactorValues[i].getFactorValue();
			}
		}
		return factorValues;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean syncOneArticlegAttribute(String tCatalogID) {
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(tCatalogID);
		catalog.fill();
		User.getCurrent().getMap()
				.put("_CurrentSiteID", String.valueOf(catalog.getSiteID()));

		QueryBuilder qb = new QueryBuilder(
				"select b.ID from zdcolumnvalue a, zcarticle b where "
						+ "a.RelaID =  b.ID and a.ColumnCode = 'RiskCode' "
						+ "and a.RelaType = '2' and b.CatalogID = ? and TextValue = ?",
				tCatalogID, mRiskcode);
		long id = qb.executeLong();

		String articleID = "";
		if (id > 0) {
			articleID = String.valueOf(id);
		}
		Mapx mp = new Mapx();
		mp.put("ArticleID", articleID);
		mp.put("CatalogID", tCatalogID);

		RequestImpl request = new RequestImpl();
		request.putAll(Article.init(mp));
		request.put("ArticleID", String.valueOf(request.get("ID")));

		request.put(ColumnUtil.PREFIX + "ERiskType",
				getESubRiskType(catalog.getProductType()));
		if (!setArticlegAttribute(request, tCatalogID)) {
			return false;
		}
		Article article = new Article();
		article.setRequest(request);

		if (!article.save()) {
			return false;
		}

		mArticleIDs.add(request.getString("ArticleID"));

		ZCArticleSchema articleSchema = new ZCArticleSchema();
		articleSchema.setID(request.getString("ArticleID"));
		articleSchema.fill();
		DataTable dt = new QueryBuilder("select type from zdconfig where value = '"+tCatalogID+"'").executeDataTable();
		String catalogType = "";
		if(dt.getRowCount()>0){
			catalogType = dt.getString(0, 0);
		}
		if("dlrIdZX".equals(catalogType)){
			// 记录产品信息
			Transaction trans = new Transaction();
			String delSQL = "delete from sdproduct where productid = ?";
			String insertSQL = "INSERT INTO sdproduct VALUES (?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ? ,? ,?,?,?);";
			trans.add(new QueryBuilder(delSQL, mProductFactors.get("RiskCode")));
			QueryBuilder qbProduct = new QueryBuilder(insertSQL);
			qbProduct.add(mFMRisk.getRiskCode());
			qbProduct.add(mFMRisk.getRiskName());
			try {
				qbProduct.add("");
				qbProduct.add("");
				qbProduct.add(""); // 分类
				qbProduct.add(mFMRisk.getFeeRate()); // 手续费率
				qbProduct.add("Y");
				dt = new QueryBuilder("select value from zdconfig where type='FrontServerContextPath'").executeDataTable();
				String FrontServerContextPath = "";
				if(dt.getRowCount()>0){
					FrontServerContextPath = dt.getString(0, 0);
				}
				qbProduct.add(FrontServerContextPath + "/" + articleSchema.getURL());
				qbProduct.add(tCatalogID);
			} catch (Exception e) {
			}
			
			qbProduct.add(mFMRisk.getBackUp1());// 限购份数
			String Occup = "";
			String SectionAge = "";
			FERiskAppFactor[] appFactors = mFMRisk.getFERiskAppFactor();
			if (appFactors != null && appFactors.length > 0
					&& appFactors[0] != null) {
				try {
					for (int i = 0; i < appFactors.length; i++) {
						FERiskAppFactor feaf = appFactors[i];
						if (feaf.getFactorType().equalsIgnoreCase("Occup")) {
							FEMRiskFactorList[] femrList = feaf
									.getFEMRiskFactorList();
							for (int j = 0; femrList != null && j < femrList.length; j++) {
								Occup += femrList[j].getFactorValue();
								if (j != femrList.length - 1) {
									Occup += "|";
								}
							}
						}
						
						if (feaf.getFactorType().equalsIgnoreCase("TextAge")) {
							FEMRiskFactorList[] femrList = feaf
									.getFEMRiskFactorList();
							for (int j = 0; femrList != null && j < femrList.length; j++) {
								SectionAge += femrList[j].getFactorValue();
								if (j != femrList.length - 1) {
									SectionAge += "|";
								}
							}
						}
					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
			
			qbProduct.add(Occup);// 职业
			qbProduct.add(SectionAge);// 年龄
			qbProduct.add("");
			qbProduct.add("");
			qbProduct.add("");
			qbProduct.add("");
			// 增加保存保险公司编码
			qbProduct.add(mFMRisk.getSupplierCode().substring(0, 4));
			qbProduct.add(""); //积分商城 产品地址链接 追加
			trans.add(qbProduct);
			
			trans.commit();
		}
		return true;
	}
	@SuppressWarnings("unchecked")
	private boolean setArticlegAttribute(RequestImpl request, String tCatalogID) {
		request.put("Type", "1");
		request.put("Title", mFMRisk.getRiskName());
		request.put("ShortTitle", null);
		request.put("SubTitle", null);
		request.put("Content", mFMRisk.getERiskDesc());
		request.put("Keyword", mFMRisk.getKeyWord());
		request.put("TopFlag", getRiskTopFlag(mFMRisk.getIsTop()));
		request.put("TemplateFlag", "0");
		request.put("Template", "");
		request.put(
				"Logo",
				(mFMRisk.getERiskPicRelaPath().startsWith("/")) ? mFMRisk
						.getERiskPicRelaPath() : "/"
						+ mFMRisk.getERiskPicRelaPath());
		String id = request.get("ID") + "";
		if (StringUtil.isEmpty(id)) {
			request.put("Attribute", "");
		} else {
			ZCArticleSchema tZCArticleSchema = new ZCArticleSchema();
			tZCArticleSchema.setID(id);
			tZCArticleSchema.fill();
			request.put("Attribute", tZCArticleSchema.getAttribute());
		}
		request.put("ContentSign", "");
		request.put("SourceSign", "");
		request.put("ClusterTarget", "");
		request.put("ReferTarget", null);// 复制目的地
		request.put("ReferType", 2);// 引用复制
		// 内容标记
		request.put("RiskType", mFMRisk.getBelongFlag());
		request.put("CompanyID", "");
		request.put(ColumnUtil.PREFIX + "CalHTML",
				mProductFactors.get("CalHTML"));
		request.put(ColumnUtil.PREFIX + "CalHTML2",
				mProductFactors.get("CalHTML2"));
		request.put(ColumnUtil.PREFIX + "DutyHTML",
				mProductFactors.get("DutyHTML"));
		request.put(ColumnUtil.PREFIX + "DutyHTML1",
				mProductFactors.get("DutyHTML1"));
		request.put(ColumnUtil.PREFIX + "RiskCode",
				mProductFactors.get("RiskCode"));
		request.put(ColumnUtil.PREFIX + "SupplierCode",
				mProductFactors.get("SupplierCode"));
		request.put(ColumnUtil.PREFIX + "SupplierCode2",
				mProductFactors.get("SupplierCode2"));
		request.put(ColumnUtil.PREFIX + "InitPrem",
				mProductFactors.get("InitPrem"));
		request.put(ColumnUtil.PREFIX + "Popular",
				mProductFactors.get("Popular"));
		request.put(ColumnUtil.PREFIX + "SalesVolume",
				mProductFactors.get("SalesVolume"));
		request.put(ColumnUtil.PREFIX + "AdaptPeopleInfo",
				mProductFactors.get("AdaptPeopleInfo"));
		request.put(ColumnUtil.PREFIX + "ERiskFeatures",
				mProductFactors.get("ERiskFeatures"));
		request.put(ColumnUtil.PREFIX + "PublishDate",
				mProductFactors.get("PublishDate"));
		request.put(ColumnUtil.PREFIX + "FEMRiskBrightSpot",
				mProductFactors.get("FEMRiskBrightSpot"));
		request.put(ColumnUtil.PREFIX + "FCDoc", mProductFactors.get("FCDoc"));
		request.put(
				ColumnUtil.PREFIX + "ProductLogo",
				(mFMRisk.getERiskPicRelaPath().startsWith("/")) ? mFMRisk
						.getERiskPicRelaPath() : "/"
						+ mFMRisk.getERiskPicRelaPath());
		request.put(ColumnUtil.PREFIX + "ProductInt", getIntegral(mFMRisk));
		request.put(ColumnUtil.PREFIX + "DiscountRate",
				mProductFactors.get("DiscountRate"));
		request.put(ColumnUtil.PREFIX + "BasePrem",
				mProductFactors.get("BasePrem"));
		request.put(ColumnUtil.PREFIX + "prodcutMarkPrice",
				mProductFactors.get("prodcutMarkPrice"));
		// fhz 险种子类的面包屑
		request.put(ColumnUtil.PREFIX + "RiskBreadcrumb",
				mProductFactors.get("RiskBreadcrumb"));

		request.put(ColumnUtil.PREFIX + "Instruction",
				mProductFactors.get("Instruction"));
		request.put(ColumnUtil.PREFIX + "isCpsProduct",
				mProductFactors.get("isCpsProduct"));
		request.put(ColumnUtil.PREFIX + "cpsAdress",
				mProductFactors.get("cpsAdress"));
		request.put(ColumnUtil.PREFIX + "isFinancialRisks",
				mProductFactors.get("isFinancialRisks"));
		request.put(ColumnUtil.PREFIX + "limcount",
				mProductFactors.get("limcount"));
		request.put(ColumnUtil.PREFIX + "DeadlineDesc",
				mProductFactors.get("DeadlineDesc"));
		request.put(ColumnUtil.PREFIX + "Phone", mProductFactors.get("Phone"));
		request.put(ColumnUtil.PREFIX + "CompanyDesc",
				mProductFactors.get("CompanyDesc"));
		request.put(ColumnUtil.PREFIX + "CompanyShortName",
				mProductFactors.get("CompanyShortName"));
		request.put(ColumnUtil.PREFIX + "Doc1", mProductFactors.get("Doc1"));
		request.put(ColumnUtil.PREFIX + "Doc2", mProductFactors.get("Doc2"));
		request.put(ColumnUtil.PREFIX + "Doc3", mProductFactors.get("Doc3"));
		request.put(ColumnUtil.PREFIX + "Doc5", mProductFactors.get("Doc5"));
		request.put(ColumnUtil.PREFIX + "question1",
				mProductFactors.get("question1"));
		request.put(ColumnUtil.PREFIX + "question2",
				mProductFactors.get("question2"));
		request.put(ColumnUtil.PREFIX + "answer1",
				mProductFactors.get("answer1"));
		request.put(ColumnUtil.PREFIX + "answer2",
				mProductFactors.get("answer2"));
		request.put(ColumnUtil.PREFIX + "termInformation",
				mProductFactors.get("termInformation"));
		GetDBdata db = new GetDBdata();
		try {
			String value = db
					.getOneValue("select textvalue from ZDColumnValue where columncode='FAQ' and relaid='"
							+ id + "'");
			request.put(ColumnUtil.PREFIX + "FAQ", value);
			String value1 = db
					.getOneValue("select textvalue from ZDColumnValue where columncode='toubaoshili' and relaid='"
							+ id + "'");
			request.put(ColumnUtil.PREFIX + "toubaoshili", value1);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return true;
	}

	/**
	 * 得到产品要复制的引用
	 * 代理人不用
	 * @author fhz
	 * */
	private String getDestCatalogs(String tCatalogID) {
		String desCatalogIDs = "";
		String eSubRiskType = "";
		String eSubRiskType2 = "";
		FEMRiskType[] list = mFMRisk.getFEMRiskType();
		if (list != null && list.length > 0 && list[0] != null) {
			for (int i = 0; i < list.length; i++) {
				// 去掉00的大类
				if (list[i].getSubRiskTypeCode() == null
						|| "".equals(list[i].getSubRiskTypeCode().trim())
						|| (mFMRisk.getERiskType() + "00").equals(list[i]
								.getSubRiskTypeCode())) {
					continue;
				}
				eSubRiskType += ",'" + list[i].getSubRiskTypeCode() + "'";
				eSubRiskType2 += ",'Z" + list[i].getSubRiskTypeCode() + "'";
			}
		} else {
			logger.error(
					"同步产品信息getDestCatalogs方法,产品【{}_{}】获取小类信息失败！请联系产品中心管理员。",
					mFMRisk.getRiskCode(), mFMRisk.getRiskName());
			return null;
		}
		if (eSubRiskType.length() > 0) {
			eSubRiskType = eSubRiskType.substring(1);
		} else {
			logger.error(
					"同步产品信息getDestCatalogs方法,产品【{}_{}】获取小类信息失败！",
					mFMRisk.getRiskCode(), mFMRisk.getRiskName());
			return null;
		}
		if (eSubRiskType2.length() > 0) {
			eSubRiskType2 = eSubRiskType2.substring(1);
		} else {
			logger.error(
					"金融界同步产品信息getDestCatalogs方法,产品【{}_{}】获取小类信息失败！",
					mFMRisk.getRiskCode(), mFMRisk.getRiskName());
			return null;
		}
		String typeSql = "select Type from ZDConfig where value = ( select parentid from ZCCatalog where id='"
				+ tCatalogID + "')";
		DataTable dtType = new QueryBuilder(typeSql).executeDataTable();
		if (dtType.getRowCount() > 0) {
			String typeFlag = dtType.getString(0, 0);
			QueryBuilder qb = null;
			if ("CatalogId".equals(typeFlag)) {
				qb = new QueryBuilder(
						"select ID from ZCCatalog where ProductType in ("
								+ eSubRiskType + ")");
			} else if ("CatalogBXSCID".equals(typeFlag)) {
				qb = new QueryBuilder(
						"select ID from ZCCatalog where ProductType in ("
								+ eSubRiskType2 + ")");
			}
			if (qb != null) {
				DataTable dt = qb.executeDataTable();
				for (int i = 0; i < dt.getRowCount(); i++) {
					desCatalogIDs += "," + dt.getString(i, "ID");
				}
				if (desCatalogIDs != null && !"".equals(desCatalogIDs)) {
					desCatalogIDs = desCatalogIDs.substring(1);
				}
			}
		}
		return desCatalogIDs;
	}

	private int getIntegral(FMRisk tFMRisk) {
		try {
			String sql = "select ProductType from sdproduct where productid='"
					+ mProductFactors.get("RiskCode") + "'";
			GetDBdata db = new GetDBdata();

			IntegralAction iA = new IntegralAction();
			int result = iA.paySuccess(tFMRisk.getFeeRate(),
					Double.parseDouble(tFMRisk.getDiscountPrice()),
					db.getOneValue(sql));
			Object[] argArr = {tFMRisk.getFeeRate(), tFMRisk.getDiscountPrice(), result, tFMRisk.getInitPrem()};
			logger.info("FeeRate(初始手续费率):{} - InitPrem(折扣定价):{} 积分：{} 原价：{}", argArr);
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return 0;
		}
	}

	private String getRiskTopFlag(String isTop) {
		if ("Y".equalsIgnoreCase(isTop)) {
			return "1";
		}
		return "0";
	}

	public static String getBrightSoptHTML(FMRisk tFMRisk) {
		FEMRiskBrightSpotList[] femRiskBrightSpotList = tFMRisk
				.getFEMRiskBrightSpotList();
		if (femRiskBrightSpotList != null && femRiskBrightSpotList.length > 0
				&& femRiskBrightSpotList[0] != null) {
			// No3 Modify by meijunfeng 2013/06/13 start
			// String tHTML = "<ul class=\"CRiskBrightSpot\">";
			String tHTML = "<ul class=\"d_list_con\">";
			// No3 Modify by meijunfeng 2013/06/13 end
			for (int i = 0; i < femRiskBrightSpotList.length; i++) {
				tHTML += "<li><span>"
						+ femRiskBrightSpotList[i].getBrightSpotName()
						+ "</span></li>";
			}
			tHTML += "</ul>";
			return tHTML;
		}
		return "";
	}

	private boolean getCatalogID() {
		mCatalogIDs = new ArrayList<String>();
		String tERiskType=mFMRisk.getERiskType();//内部统计险种中类别 大类 ABCDE
		if(tERiskType==null||tERiskType.equals("")){
		    logger.error("同步产品信息getCatalogID方法,产品【{}_{}】获取产品大类ERiskType信息失败,请联系产品中心管理员！！！",
					mFMRisk.getRiskCode(), mFMRisk.getRiskName());
			return false;
		}
		try {
			String sql = "select value from zdconfig where type  in('dlrIdZX','dlrIdWX') ";
			DataTable dt = new QueryBuilder(sql).executeDataTable();
			if(dt.getRowCount()>0){
				for(int i=0 ; i<dt.getRowCount();i++){
					mCatalogIDs.add(dt.getString(i, 0));
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return true;
	}

	private String getESubRiskType(String code) {
		String cESubRiskTypeName = "";
		if (mERiskTypes == null || mERiskTypes.size() < 1) {
			DataTable dt = new QueryBuilder(
					"select CodeValue,CodeName from ZDCode where ParentCode ='ProductType' order by CodeValue,ParentCode")
					.executeDataTable();
			if (dt != null && dt.getRowCount() > 0) {
				mERiskTypes = new HashMap<String, String>();
				for (int i = 0; i < dt.getRowCount(); i++) {
					mERiskTypes.put(dt.getString(i, 0), dt.getString(i, 1));
				}
				cESubRiskTypeName = mERiskTypes.get(code);
			}
		} else {
			cESubRiskTypeName = mERiskTypes.get(code);
		}
		return cESubRiskTypeName;
	}

	public static String getFEMRiskType(FEMRiskType[] femRiskTypes) {
		if (femRiskTypes != null && femRiskTypes.length > 0
				&& femRiskTypes[0] != null) {
			String str = "";
			for (int i = 0; i < femRiskTypes.length; i++) {
				str += femRiskTypes[i].getSubRiskTypeCode() + ";";
			}
			return str;
		}
		return "";
	}

	public static void allProduct(LongTimeTask lTT) {
		logger.info("全部产品同步开始:{}", new Date().toString());
		FMRisk[] list = null;
		QueryBuilder qb = new QueryBuilder(
				"select f1.riskcode from  femriskb f1  ,fmriskb f2 where f1.IsPublish='Y' and f1.riskcode=f2.riskcode  and riskprop='F'");
		DataTable dt = qb.executeDataTable();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("RiskProp", "F");
		for (int j = 0; j < dt.getRowCount(); j++) {
			map.put("RiskCode", new String[] { dt.getString(j, "riskcode") });
			try {
				ProductInfoResponse productInfo = ProductWebservice
						.ProductInfoSereviceImpl(map, null);
				if (productInfo != null) {
					list = productInfo.getFMRisk();
				} else {
					continue;
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				if (lTT != null) {
					lTT.setPercent(100);
				}
				return;
			}

			if (list == null) {
				if (lTT != null) {
					lTT.setPercent(100);
				}
				return;
			}
			int iTotal = list.length;
			if (list != null && iTotal > 0 && list[0] != null) {
				for (int i = 0; i < list.length; i++) {
					if (lTT != null) {
						lTT.setCurrentInfo("正在同步(" + (i + 1) + "/" + iTotal
								+ ")：" + list[i].getRiskName());
						lTT.setPercent(Integer.valueOf((i + 1) * 100
								/ (iTotal + 1)));
					}
					try {
						DlrSynProducts.publishing(list[i]);
					} catch (Exception e) {
						logger.error(
								"同步产品：" + list[i].getRiskName()
										+ "失败 continue..." + e.getMessage(), e);
					}
				}
				if (lTT != null) {
					lTT.setPercent(100);
				}
			}
		}

		logger.info("全部产品同步完成:{}", new Date().toString());
	}

	public static void allProductArea(LongTimeTask lTT) {
		logger.info("全部产品同步开始:{}", new Date().toString());
		FMRisk[] list = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("RiskProp", "F");
		QueryBuilder qb = new QueryBuilder(
				"select f1.riskcode from  femriskb f1  ,fmriskb f2 where f1.IsPublish='Y' and f1.riskcode=f2.riskcode  and riskprop='F' ");
		DataTable dt = qb.executeDataTable();
		if (dt.getRowCount() > 0) {
			for (int j = 0; j < dt.getRowCount(); j++) {
				DataRow ro = dt.get(j);
				map.put("RiskCode", new String[] { ro.get("riskcode") + "" });
				try {
					ProductInfoResponse productInfo = ProductWebservice
							.ProductInfoSereviceImpl(map, null);
					list = productInfo.getFMRisk();
				} catch (Exception e) {
					logger.error("ceshi：" + "productid:"
							+ ro.get("riskcode")+ e.getMessage(), e);
					if (lTT != null) {
						lTT.setPercent(100);
					}
					return;
				}
				if (list == null) {
					if (lTT != null) {
						lTT.setPercent(100);
					}
					return;
				}
				int iTotal = list.length;
				if (list != null && iTotal > 0 && list[0] != null) {
					for (int i = 0; i < list.length; i++) {
						if (lTT != null) {
							lTT.setCurrentInfo("正在同步(" + (i + 1) + "/" + iTotal
									+ ")：" + list[i].getRiskName());
							lTT.setPercent(Integer.valueOf((i + 1) * 100
									/ (iTotal + 1)));
						}
						try {
							DlrSynProducts.publishingArea(list[i]);
						} catch (Exception e) {
							logger.error(
									"同步产品：" + list[i].getRiskName()
											+ "失败 continue..." + e.getMessage(), e);
						}
					}
					if (lTT != null) {
						lTT.setPercent(100);
					}
				}
			}
		}

		logger.info("全部产品同步完成:{}", new Date().toString());
	}

	public static void allProductHI(LongTimeTask lTT) {
		logger.info("全部产品同步开始:{}", new Date().toString());
		FMRisk[] list = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("RiskProp", "F");
		QueryBuilder qb = new QueryBuilder(
				"select f1.riskcode from  femriskb f1  ,fmriskb f2 where f1.IsPublish='Y' and f1.riskcode=f2.riskcode  and riskprop='F' ");
		DataTable dt = qb.executeDataTable();
		if (dt.getRowCount() > 0) {
			for (int j = 0; j < dt.getRowCount(); j++) {
				DataRow ro = dt.get(j);
				map.put("RiskCode", new String[] { ro.get("riskcode") + "" });
				try {
					ProductInfoResponse productInfo = ProductWebservice
							.ProductInfoSereviceImpl(map, null);
					list = productInfo.getFMRisk();
				} catch (Exception e) {
					logger.error("ceshi：" + "productid:"
							+ ro.get("riskcode") + e.getMessage(), e);
					if (lTT != null) {
						lTT.setPercent(100);
					}
					return;
				}
				if (list == null) {
					if (lTT != null) {
						lTT.setPercent(100);
					}
					return;
				}
				int iTotal = list.length;
				if (list != null && iTotal > 0 && list[0] != null) {
					for (int i = 0; i < list.length; i++) {
						if (lTT != null) {
							lTT.setCurrentInfo("正在同步(" + (i + 1) + "/" + iTotal
									+ ")：" + list[i].getRiskName());
							lTT.setPercent(Integer.valueOf((i + 1) * 100
									/ (iTotal + 1)));
						}
						try {
							DlrSynProducts.publishingHI(list[i]);
						} catch (Exception e) {
							logger.error(
									"同步产品：" + list[i].getRiskName()
											+ "失败 continue..." + e.getMessage(), e);
						}
					}
					if (lTT != null) {
						lTT.setPercent(100);
					}
				}
			}
		}

		logger.info("全部产品同步完成:{}", new Date().toString());
	}

	public static void publishAllProduct(LongTimeTask lTT) {
		logger.info("全部产品发布开始:" + new Date().toString());
		FMRisk[] list = null;
		try {
			ZDConfigSchema tZDConfigSchema = new ZDConfigSchema();
			tZDConfigSchema.setType("CatalogId");
			tZDConfigSchema.fill();
			long catalogID = Long.parseLong(tZDConfigSchema.getValue());
			ZCCatalogSchema tZCCatalogSchema = new ZCCatalogSchema();
			tZCCatalogSchema.setID(catalogID);
			tZCCatalogSchema.fill();

			ZCArticleSchema tZCArticleSchema = new ZCArticleSchema();
			ZCArticleSet tZCArticleSet = tZCArticleSchema
					.query(new QueryBuilder(" where cataloginnercode like '"
							+ tZCCatalogSchema.getInnerCode() + "%'; "));
			Publisher p = new Publisher();
			p.publishArticle(tZCArticleSet, lTT);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			if (lTT != null) {
				lTT.setPercent(100);
			}
			return;
		}
		if (list == null) {
			if (lTT != null) {
				lTT.setPercent(100);
			}
			return;
		}
		int iTotal = list.length;
		if (list != null && iTotal > 0 && list[0] != null) {
			for (int i = 0; i < list.length; i++) {
				if (lTT != null) {
					lTT.setCurrentInfo("正在发布(" + (i + 1) + "/" + iTotal + ")："
							+ list[i].getRiskName());
					lTT.setPercent(Integer
							.valueOf((i + 1) * 100 / (iTotal + 1)));
				}
				try {
					DlrSynProducts.publishing(list[i]);
				} catch (Exception e) {
					logger.error(
							"发布产品：" + list[i].getRiskName() + "失败 continue..." + e.getMessage(), e);
				}
			}
			if (lTT != null) {
				lTT.setPercent(100);
			}
		}

		logger.info("全部产品发布完成:{}", new Date().toString());
	}

	/**
	 * 
	 * 判断是否是理财险产品
	 */
	private String getProductCategories() {
		String eSubRiskType = "";
		FEMRiskType[] list = mFMRisk.getFEMRiskType();
		if (list != null && list.length > 0 && list[0] != null) {
			for (int i = 0; i < list.length; i++) {
				// 去掉00的大类
				if (list[i].getSubRiskTypeCode() == null
						|| "".equals(list[i].getSubRiskTypeCode().trim())
						|| (mFMRisk.getERiskType() + "00").equals(list[i]
								.getSubRiskTypeCode())) {
					continue;
				}
				eSubRiskType += "," + list[i].getSubRiskTypeCode() + "";
			}
		} else {
			logger.error(
					"同步产品信息getProductCategories方法,产品【{}_{}】获取小类信息失败！请联系产品中心管理员。",
					mFMRisk.getRiskCode(), mFMRisk.getRiskName());
			return null;
		}
		if (eSubRiskType.length() > 0) {
			eSubRiskType = eSubRiskType.substring(1);
		} else {
			logger.error(
					"同步产品信息getProductCategories方法,产品【{}_{}】获取小类信息失败！",
					mFMRisk.getRiskCode(), mFMRisk.getRiskName());
			return null;
		}
		String[] categories = eSubRiskType.split(",");
		for (int i = 0; i < categories.length; i++) {
			if ("E03".equals(categories[i])) {
				return "Y";
			}
		}
		return "N";
	}
//	public static void main(String[] arg) {
//
//		// 测试同步所有产品
//		boolean isALL = false;
////		boolean isALL = true;
//		FMRisk[] list = null;
//		try {
//			if (isALL) {
//				Map<String, Object> map = new HashMap<String, Object>();
//				map.put("RiskProp", "F");
//				ProductInfoResponse productInfo = ProductWebservice.ProductInfoSereviceImpl(map, null);
//				list = productInfo.getFMRisk();
//				for (FMRisk f : list) {
//					DlrSynProducts.publishing(f);
//				}
//			} else {
//				Map<String, Object> map = new HashMap<String, Object>();
//				map.put("RiskCode", new String[] {"000501035"});
//				map.put("RiskProp", "F");
//				ProductInfoResponse productInfo = ProductWebservice
//						.ProductInfoSereviceImpl(map, null);
//				list = productInfo.getFMRisk();
//				DlrSynProducts.publishing(list[0]);
//			}
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//		}
//		System.out.println("publishing over");
//		return;
//	}
}
