package com.sinosoft.cms.publish;

import com.sinosoft.cms.api.CatalogAPI;
import com.sinosoft.cms.api.ImageAPI;
import com.sinosoft.cms.datachannel.Publisher;
import com.sinosoft.cms.dataservice.ColumnUtil;
import com.sinosoft.cms.dataservice.ProductListYX;
import com.sinosoft.cms.document.Article;
import com.sinosoft.cms.pub.SiteUtil;
import com.sinosoft.cms.webservice.CmsService;
import com.sinosoft.cms.webservice.CmsServiceImpl;
import com.sinosoft.framework.Config;
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
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.LogUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActivityCalculate;
import com.sinosoft.platform.UserList;
import com.sinosoft.schema.AreaSchema;
import com.sinosoft.schema.AreaSet;
import com.sinosoft.schema.HealthyInfoSchema;
import com.sinosoft.schema.HealthyInfoSet;
import com.sinosoft.schema.ZCArticleSchema;
import com.sinosoft.schema.ZCArticleSet;
import com.sinosoft.schema.ZCCatalogSchema;
import com.sinosoft.schema.ZCCatalogSet;
import com.sinosoft.schema.ZDConfigSchema;
import com.sinosoft.schema.ZDFEUIInfoValueSchema;
import com.sinosoft.schema.ZDFEUIInfoValueSet;
import com.sinosoft.schema.ZDHealthyInfoSchema;
import com.sinosoft.schema.ZDHealthyInfoSet;
import com.sinosoft.sms.messageinterface.pubfun.PubFun;
import com.sinosoft.webservice.ProductWebservice;
import com.sinosoft.webservice.fDInsCom.FDInsComServiceStub;
import com.sinosoft.webservice.fDInsCom.FDInsComServiceStub.FDInsCom;
import com.sinosoft.webservice.fDInsCom.FDInsComServiceStub.FMClaimData;
import com.sinosoft.webservice.fDInsCom.FDInsComServiceStub.FMClaimDataTitle;
import com.sinosoft.webservice.fDInsCom.FDInsComServiceStub.FMClaimGuide;
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
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FMRelaDutyCodeList;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FMRisk;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.ProductInfoResponse;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class AutomaticPublishArticle {

	private static final Logger logger = LoggerFactory.getLogger(AutomaticPublishArticle.class);
	/**
	 * **保险条款.pdf 统一为 "条款下载".
	 */
	private final String INSURANCECLAUSE = "条款下载";
	private final String ESCAPECLAUSE = "责任免除下载";
	private final static String PROEXAMPLE = "保单样本下载";
	private final static String INVOICEEXAMPLE = "发票样本下载";
	private String mRiskcode = "";
	private List<String> mCatalogIDs;
	private List<String> mArticleIDs;
	private FMRisk mFMRisk = null;
	private static Map<String, String> mERiskTypes = null;
	private Map<String, Object> mProductFactors = new HashMap<String, Object>();
	//暂时性存储勾稽关系
	private static Map<String, List<String>> relaMapCache;
	// 是否是积分兑换保险产品  true:是
	private boolean isPointProductflag = false;
	// 是否有现金价值显示
	private boolean cashValueFlag = false;
	// 利益演示标识
	private boolean showBenefitFlag = false;

	public static boolean publishing(FMRisk fmRisk, String catalogID) {
		AutomaticPublishArticle apa = new AutomaticPublishArticle(fmRisk);
		if (apa.mRiskcode == null || apa.mRiskcode.equals("")) {
			return false;
		}
		User.setCurrent(new UserData());
		User.setRealName("系统管理员");
		User.setUserName(UserList.ADMINISTRATOR);
		User.setLogin(true);
		User.setManager(true);
		User.setBranchInnerCode("0001");
		if (!apa.syncArticlegAttribute(catalogID)) {
			return false;
		}
		return true;
	}

	public static boolean publishingArea(FMRisk fmRisk) {
		AutomaticPublishArticle apa = new AutomaticPublishArticle(fmRisk);
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
		AutomaticPublishArticle apa = new AutomaticPublishArticle(fmRisk);
		if (apa.mRiskcode == null || apa.mRiskcode.equals("")) {
			return false;
		}
		// 同步产品健康告知
		if (!apa.prepareHealthyInfo()) {
			return false;
		}

		return true;
	}

	public AutomaticPublishArticle(FMRisk fmRisk) {
		if (fmRisk != null) {
			mFMRisk = fmRisk;
			mRiskcode = fmRisk.getRiskCode();
		}
		mProductFactors.clear();
	}

	private boolean syncArticlegAttribute(String catalogID) {
		if (StringUtil.isEmpty(catalogID)) {
			if (!getCatalogID()) {
				return false;
			}
		} else {
			mCatalogIDs = new ArrayList<String>(0);
			mCatalogIDs.add(catalogID);
			isPointProductflag = true;
		}

		// 判断产品是否有现金价值页
		String sql = "select memo from zdcode where CodeType = 'BuyFlow.CodeFlag' and CodeValue = 'ShowCashValueProducts'";
		QueryBuilder qb = new QueryBuilder(sql);
		String riskCodes = qb.executeString();

		if (StringUtil.isNotEmpty(riskCodes) && riskCodes.contains(mRiskcode)) {
			cashValueFlag = true;
		}

		// 年金险标识
		int count = new QueryBuilder("SELECT COUNT(*) FROM zdcode WHERE codetype = 'yearCash.riskCode' AND FIND_IN_SET('"+ mRiskcode +"', CodeValue);").executeInt();
		if (count > 0) {
			showBenefitFlag = true;
		}

		mArticleIDs = new ArrayList<String>();
		for (int i = 0; i < mCatalogIDs.size(); i++) {
			if (!prepareProdcutFactorsForArticle(mCatalogIDs.get(i))) {
				return false;
			}
			
			if (!syncOneArticlegAttribute(mCatalogIDs.get(i), false)) {
				return false;
			}
			// 单独生成现金价值页
			if (i == 0 && cashValueFlag) {
				// 获取现金价值页的catalogID
				sql = "SELECT id FROM ZCCatalog WHERE NAME = '现金价值'";
				qb = new QueryBuilder(sql);
				String cashValCatalogId = qb.executeString();
				if (StringUtil.isNotEmpty(cashValCatalogId)) {
					if (!syncOneArticlegAttribute(cashValCatalogId, true)) {
						return false;
					}
				}
			}
			// 单独生成利益演示页面
			else if (i == 0 && showBenefitFlag) {
				// 获取现金价值页的catalogID
				sql = "SELECT id FROM ZCCatalog WHERE NAME = '利益演示'";
				qb = new QueryBuilder(sql);
				String showBenefitId = qb.executeString();
				if (StringUtil.isNotEmpty(showBenefitId)) {
					if (!syncOneArticlegAttribute(showBenefitId, true)) {
						return false;
					}
				}
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
		} else {
			ZDHealthyInfoSchema zdHealthyInfoSchema1 = new ZDHealthyInfoSchema();
			ZDHealthyInfoSet zdHealthyInfoSet = zdHealthyInfoSchema1
					.query(new QueryBuilder("where riskcode='"
							+ mFMRisk.getRiskCode() + "'"));
			if (zdHealthyInfoSet.size() > 0) {
				if (zdHealthyInfoSet.delete()) {
					return false;
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
			// add by wangej 20160108 地区同步支持3级
			StringBuffer sqlCity = new StringBuffer();
			sqlCity.append("SELECT DISTINCT CityCode,CityName,SupplierCode,ProvinceCode,ProvinceName,ERiskID ");
			sqlCity.append("FROM FEMRiskSaleAreab WHERE ERiskID = '" + mFMRisk.getRiskCode()+"' ");
			QueryBuilder qbCity = new QueryBuilder(sqlCity.toString());
			DataTable dt = qbCity.executeDataTable();
			if (provinceInfo != null && provinceInfo.size() > 0) {
				Transaction trans = new Transaction();
				AreaSet areaSet1 = new AreaSet();
				//省份入库
				for (String province : provinceInfo) {
					String provinceCode = province.split("-")[0];
					String provinceName = province.split("-")[1];
					AreaSchema tSCAreaSchema = new AreaSchema();
					String id = mFMRisk.getSupplierCode()
							+ mFMRisk.getRiskCode() + provinceCode +"1";
					tSCAreaSchema.setId(id);
					tSCAreaSchema.setcreateDate(new Date());
					tSCAreaSchema.setmodifyDate(new Date());
					tSCAreaSchema.setname(provinceName);
					tSCAreaSchema
							.setinsuranceCompany(mFMRisk.getSupplierCode());
					tSCAreaSchema.setareaValue(provinceCode);
					tSCAreaSchema.setproductId(mFMRisk.getRiskCode());
					areaSet1.add(tSCAreaSchema);
					if(dt.getRowCount() > 0){
						//城市入库
						for (int j = 0,jj = dt.getRowCount() ; j < jj ; j++) {
							DataRow cityDR = dt.get(j);
							if (provinceCode.equals(cityDR.get("ProvinceCode"))) {
								tSCAreaSchema = new AreaSchema();
								String son_id = cityDR.get("SupplierCode")
										+ mFMRisk.getRiskCode()
										+ cityDR.get("CityCode")+"2";
								tSCAreaSchema.setId(son_id);
								tSCAreaSchema.setcreateDate(new Date());
								tSCAreaSchema.setmodifyDate(new Date());
								tSCAreaSchema.setname(cityDR.get("CityName").toString());
								tSCAreaSchema.setpath(id + "," + son_id);
								tSCAreaSchema.setparent_id(id);
								tSCAreaSchema.setinsuranceCompany(cityDR.get("SupplierCode").toString());
								tSCAreaSchema.setareaValue(cityDR.get("CityCode").toString());
								tSCAreaSchema.setproductId(mFMRisk.getRiskCode());
								areaSet1.add(tSCAreaSchema);
								// add by wangej 20160108 地区同步支持3级  区入库
								for (int k = 0; k < tFEMRiskSaleArea.length; k++) {
									if( StringUtil.isNotEmpty(cityDR.get("CityCode").toString())&&
											StringUtil.isNotEmpty(tFEMRiskSaleArea[k].getAreaCode()))
									{
										if (cityDR.get("CityCode").toString().equals(tFEMRiskSaleArea[k].getCityCode())) {
											tSCAreaSchema = new AreaSchema();
											String sons_id = tFEMRiskSaleArea[k]
													.getSupplierCode()
													+ mFMRisk.getRiskCode()
													+ tFEMRiskSaleArea[k].getCityCode()+tFEMRiskSaleArea[k].getAreaCode()+"3";
											tSCAreaSchema.setId(sons_id);
											tSCAreaSchema.setcreateDate(new Date());
											tSCAreaSchema.setmodifyDate(new Date());
											tSCAreaSchema.setname(tFEMRiskSaleArea[k].getAreaName());
											tSCAreaSchema.setpath(son_id + "," + sons_id);
											tSCAreaSchema.setparent_id(son_id);
											tSCAreaSchema.setinsuranceCompany(tFEMRiskSaleArea[k].getSupplierCode());
											tSCAreaSchema.setareaValue(tFEMRiskSaleArea[k].getAreaCode());
											tSCAreaSchema.setproductId(mFMRisk.getRiskCode());
											areaSet1.add(tSCAreaSchema);
										}
									}
								}
							}
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
	 * 取得投保要素和责任的关系(新逻辑已废弃此方法)
	 * @param tFMRisk
	 * @return
	 */
	private String getRiskAppFacDutyRela(FMRisk tFMRisk, Map<String, String> mapApp, List<String> dutySns, Map<String, String> mapDuty) {
		String tHTML = "";
		FERiskAppFactor[] appFactors = tFMRisk.getFERiskAppFactor();
		if (appFactors != null && appFactors.length > 0
				&& appFactors[0] != null) {
			String key = "";
			Map<String, Map<String, String>> dutyMap = new HashMap<String, Map<String, String>>();
			String[] dutySnAmnt;
			String dutyInfo;
			FEMRiskFactorList[] femRiskFactorList;
			Map<String, FEMDutyAmntPremList[]> dutyInfoMap = new HashMap<String, FEMDutyAmntPremList[]>();
			// 取得责任下各保额的顺序 用于页面保额显示的顺序
			if (tFMRisk.getFMDuty() != null && tFMRisk.getFMDuty().length > 0) {
				FMDuty[] tFMDuty = tFMRisk.getFMDuty();
				for (int i = 0; i < tFMDuty.length; i++) {
					dutyInfoMap.put(tFMDuty[i].getDutyCode(), tFMDuty[i].getFEMDutyFactor().getFEMDutyAmntPremList());
				}
			}
			
			for (int i = 0; i < appFactors.length; i++) {
				if (!"Duty".equalsIgnoreCase(appFactors[i].getFactorType())) {
					key = appFactors[i].getAppFactorCode();
					femRiskFactorList = appFactors[i].getFEMRiskFactorList();
					if (femRiskFactorList == null
							|| femRiskFactorList.length <= 0) {
						continue;
					}
					int len = femRiskFactorList.length;
					for (int j = 0; j < len; j++) {
						if (femRiskFactorList[j].getDutySnAmnt() == null
								|| femRiskFactorList[j].getDutySnAmnt().length <= 0
								|| (femRiskFactorList[j].getDutySnAmnt().length == 1 && femRiskFactorList[j]
										.getDutySnAmnt()[0] == null)) {
							continue;
						}
						dutySnAmnt = femRiskFactorList[j].getDutySnAmnt();
						dutyMap = new HashMap<String, Map<String, String>>();
						for (int k = 0; k < dutySnAmnt.length; k++) {
							if (dutyMap
									.containsKey(dutySnAmnt[k].split("-")[0])) {
										dutyMap.get(dutySnAmnt[k].split("-")[0]).put(mapDuty.get(dutySnAmnt[k]), mapDuty.get(dutySnAmnt[k]));
							} else {
								Map<String, String> amntMap = new HashMap<String, String>();
								amntMap.put(mapDuty.get(dutySnAmnt[k]), mapDuty.get(dutySnAmnt[k]));
								dutyMap.put(dutySnAmnt[k].split("-")[0], amntMap);
							}
						}

						if (!dutyMap.isEmpty()) {
							dutyInfo = "";
							for (int n = 0; n < dutySns.size(); n++) {
								if (dutyMap.containsKey(dutySns.get(n))) {
									String amnts = "";
									Map<String, String> amntMap = dutyMap.get(dutySns.get(n));
									if (amntMap != null && !amntMap.isEmpty()) {
										// 排序
										FEMDutyAmntPremList[] tFEMDutyAmntPremList = dutyInfoMap.get(dutySns.get(n));
										if (tFEMDutyAmntPremList != null && tFEMDutyAmntPremList.length > 0) {
											int count = 0;
											for (int k = 0; k < tFEMDutyAmntPremList.length; k++) {
												if (amntMap.containsKey(tFEMDutyAmntPremList[k].getAmnt())) {
													amnts += "'" + tFEMDutyAmntPremList[k].getAmnt() +"',";
													count++;
												}
												if (amntMap.size() == count) {
													break;
												}
											}
										} else {
											for (String key1 : amntMap.keySet()) {
												amnts += "'" + amntMap.get(key1) + "',";
												
											}
										}
										dutyInfo += "Z"+dutySns.get(n) + ":[" + amnts.substring(0, amnts.length() - 1) + "],";
										
									} else {
										dutyInfo += "Z"+dutySns.get(n) + ":[],";
									}
									
								} else {
									dutyInfo += "Z"+dutySns.get(n) + ":[],";
								}
							}
							
							tHTML += key + "_" + mapApp.get(femRiskFactorList[j].getFactorValue())
									+ ":{"
									+ dutyInfo.substring(0,
											dutyInfo.length() - 1) + "}, ";
						}
					}

				}
			}
			if (StringUtil.isNotEmpty(tHTML)) {
				tHTML = "{" + tHTML.substring(0, tHTML.length() - 2) + "}";
			} else {
				tHTML = "{}";
			}
		}
		return tHTML;
	}
//	/**
//	 * 针对长期险多个投保要素勾稽关系(使用的是费率表，而非投保要素之间关系)
//	 * @param tFMRisk
//	 * @return
//	 */
//	private String getRiskAppFacDutyRelaForLongInsurance(FMRisk tFMRisk, Map<String, String> mapApp, List<String> dutySns, Map<String, String> mapDuty) {
//		Map<Object,Map<Object,List<String>>> map_Rela=new LinkedHashMap<Object,Map<Object,List<String>>>();//当无顺序要求时，建议使用HashMap,提高遍历效率
//		//投保要素关系
//		//FEMRiskFactorRela[] femriskfactorrela=tFMRisk.getFEMRiskFactorRela();
//		//全部投保要素
//		FERiskAppFactor[] appFactors = tFMRisk.getFERiskAppFactor();
//		appFactors[0].getFactorType();
//		//获取费率表数据，以便拼装投保要素勾稽关系
//		DataTable dt=new QueryBuilder("select * from  riskcode"+tFMRisk.getRiskCode()+"dutycode000b ").executeDataTable();
//		for (int i = 0; i < dt.getRowCount(); i++) {
//			//费率表数据
//			DataRow dr=dt.get(i);
//			for (int j = 0; j < dt.getColCount(); j++) {
//				String ColumnName=dt.getColumnName(j);
//				//剔除保费和保额字段
//				if("Prem".equals(ColumnName)||"Amnt".equals(ColumnName)){
//					continue;
//				}
//				//费率表一行中以某一数据为单位
//				String duty_value=dr.getString(j);
//				//年龄去除  Y
//				if("TextAge".equals(ColumnName)){
//					duty_value=duty_value.substring(0,duty_value.indexOf("Y"));
//				}
//				Map<Object,List<String>> map_second=new LinkedHashMap<Object,List<String>>();
//				if(map_Rela.containsKey(duty_value)){
//					map_second=map_Rela.get(duty_value);
//				}
//				//遍历每条记录字段值
//				for (int k = 0; k < dt.getColCount(); k++) {
//					//费率表一行中以其他数据
//					String duty_othervalue=dr.getString(k);
//					//判断关系是否存在缓存中
//					if(relaMapCache.containsKey(duty_othervalue)){
//						List<String> list_third=new ArrayList<String>();
//						String ul_id="";
//						String span_id="";
//						//缓存中取数据
//						List<String> list=relaMapCache.get(duty_othervalue);
//						if(list!=null&&list.size()!=0){
//							ul_id=list.get(0);
//							span_id=list.get(1);
//						}else{
//							//取年龄的默认值
//							list=relaMapCache.get("TextAge_ID");
//							if(list!=null&&list.size()!=0){
//								ul_id=list.get(0);
//								span_id=list.get(1);
//							}
//						}
//						if(map_second.containsKey(ul_id)){
//							list_third=map_second.get(ul_id);
//						}
//						if(!list_third.contains(span_id)){
//							list_third.add(span_id);
//						}
//						if(j==k&&k!=0){
//							map_second.put(ul_id, relaMapCache.get(ColumnName));
//						}else{
//							map_second.put(ul_id, list_third);
//						}
//					}
//				}
//				if(!map_Rela.containsKey(duty_value)){
//					map_Rela.put(duty_value,map_second);
//				}
//			}
//		}
//		JSONObject object_Rela = JSONObject.fromObject(map_Rela);
//		System.out.println(object_Rela.toString());
//		return object_Rela.toString();
//	}
	
	/**
	 * 取得投保要素和责任的关系,以及针对长期险多个投保要素间的勾稽关系(使用的是费率表，而非投保要素之间关系)
	 * @param tFMRisk
	 * @return
	 */
	private String getRiskAppFacDutyRelaNew(FMRisk tFMRisk, Map<String, String> mapApp, List<String> dutySns, Map<String, String> mapDuty) {
		// 存放拼装结果的变量
		Map<String, Map<String, Object>> result = new HashMap<String, Map<String, Object>>(); 
		
		FERiskAppFactor[] appFactors = tFMRisk.getFERiskAppFactor();
		if (appFactors != null && appFactors.length > 0 && appFactors[0] != null) {
			// 投保要素中是否有数据类型为日期的投保年龄
			boolean hasDateTextAge = false;
			String textAgeCode = "";
			String sexCode = "";
			
			// 取得责任下各保额的顺序 用于页面保额显示的顺序
			Map<String, FEMDutyAmntPremList[]> dutyInfoMap = new HashMap<String, FEMDutyAmntPremList[]>();
			if (tFMRisk.getFMDuty() != null && tFMRisk.getFMDuty().length > 0) {
				FMDuty[] tFMDuty = tFMRisk.getFMDuty();
				for (int i = 0; i < tFMDuty.length; i++) {
					dutyInfoMap.put(tFMDuty[i].getDutyCode(), tFMDuty[i].getFEMDutyFactor().getFEMDutyAmntPremList());
				}
			}
			
			// 获取费率表中的所有字段
			List<String> colums = new ArrayList<String>();
			DataTable dt = new QueryBuilder("SELECT * FROM riskcode" + tFMRisk.getRiskCode() + "dutycode000b LIMIT 1").executeDataTable();
			for (int i = 0; i < dt.getColCount(); i++) {
				colums.add(dt.getColumnName(i).toUpperCase());
			}
			// 当前产品缴费期限投保要素编码
			String feeYearAppFacCode = "";
			// 缴费期限拆分的关联缴费期限id
			String[] splitFeeYear = null;
			if (StringUtil.isNotEmpty(mapApp.get("splitFeeYear"))) {
				splitFeeYear = mapApp.get("splitFeeYear").split(",");
			}
			
			// 分别获取参与保费试算和不参与保费试算的投保要素和其对应的页面id
			Map<String, String> premColums = new HashMap<String, String>();
			Map<String, String> unPremColums = new HashMap<String, String>();
			for (int i = 0; i < appFactors.length; i++) {
				// 投保年龄数据类型为日期时需要拼装特殊格式的数据,不参与常规数据拼装,此时年龄要与性别组成联合投保要素
				if ("TextAge".equalsIgnoreCase(appFactors[i].getFactorType()) && "07".equals(appFactors[i].getDataType())) {
					hasDateTextAge = true;
					textAgeCode = appFactors[i].getAppFactorCode();
					continue;
				}
				if (colums.contains(appFactors[i].getFactorType().toUpperCase())) {
					premColums.put(appFactors[i].getFactorType(), appFactors[i].getAppFactorCode());
				} else {
					unPremColums.put(appFactors[i].getFactorType(), appFactors[i].getAppFactorCode());
				}
				if ("FeeYear".equalsIgnoreCase(appFactors[i].getFactorType())) {
					feeYearAppFacCode = appFactors[i].getAppFactorCode()+"_FeeYear";
				}
				
			}
			
			if (hasDateTextAge) {
				sexCode = premColums.get("Sex");
			}
			
			// 根据费率表获取所有勾稽关系(特殊字段除外,如日期类型投保年龄)
			String columString = StringUtil.join(premColums.keySet().toArray());
			String sql2 = "SELECT " + columString + " FROM riskcode" + tFMRisk.getRiskCode() + "dutycode000b GROUP BY " + columString;
			DataTable dt2 = new QueryBuilder(sql2).executeDataTable();
			
			// 开始拼装
			for (String mapAppKey : mapApp.keySet()) {
				if (mapAppKey.indexOf("_") == -1) {
					continue;
				}
				if (hasDateTextAge && mapAppKey.indexOf("Sex") != -1) {
					continue;
				}
				String appType = mapAppKey.substring(0, mapAppKey.indexOf("_"));
				if (premColums.keySet().contains(appType)) {
					// 当前字段结果
					String tKey = premColums.get(appType) + "_" + mapAppKey;
					Map<String, Object> tResult = new HashMap<String, Object>();
					
					// 拼装其他字段所有可能的勾稽关系
					Set<String> otherColums = new HashSet<String>(premColums.keySet());
					otherColums.remove(appType);
					Map<String,List<String>> otherAppResult = new HashMap<String, List<String>>();  // 当前勾稽关系拼装结果
					Map<String, Set<String>> dutyResult = new HashMap<String, Set<String>>();  // 责任关系拼装结果
					
					// 初始化勾稽关系拼装结果
					for (String tColumnName : otherColums) {
						if (!"duty".equalsIgnoreCase(tColumnName)) {
							otherAppResult.put(premColums.get(tColumnName) + "_" + tColumnName, new ArrayList<String>());
						}
					}
					for (int i = 0; i < dt2.getRowCount(); i++) {
						if (mapApp.get(mapAppKey).equals(dt2.getString(i, appType))) {
							for (String tColumnName : otherColums) {
								if (!"duty".equalsIgnoreCase(tColumnName)) {
									String tOtherKey = premColums.get(tColumnName) + "_" + tColumnName;
									String tOtherValue = "";
									for (String tMapAppKey : mapApp.keySet()) {
										if (tMapAppKey.indexOf(tColumnName) != -1 
												&& mapApp.get(tMapAppKey).equals(dt2.getString(i, tColumnName))) {
											tOtherValue = tMapAppKey;
											break;
										}
									}
									
									if (!otherAppResult.get(tOtherKey).contains(tOtherValue)) {
										otherAppResult.get(tOtherKey).add(tOtherValue);
									}
								} else {
									String tDutyCode = dt2.getString(i, tColumnName);
									String tDutyId = tDutyCode.substring(0, tDutyCode.indexOf("-"));
									if (dutyResult.get(tDutyId) == null) {
										dutyResult.put(tDutyId, new HashSet<String>());
									}
									dutyResult.get(tDutyId).add(mapDuty.get(tDutyCode));
								}
							}
						}
					}
					for (String tColumnName : unPremColums.keySet()) {
						List<String> valueList = new ArrayList<String>();
						valueList.add(tColumnName + "_0");
						otherAppResult.put(unPremColums.get(tColumnName) + "_" + tColumnName, valueList);
					}
					
					// 对于拆分多页面产品，需要对Plan做特殊处理
					String isSplitProduct = String.valueOf(tFMRisk.getBackUp5());
					if (StringUtil.isNotEmpty(isSplitProduct)) {
						for (String otherAppResultKey : otherAppResult.keySet()) {
							if (otherAppResultKey.indexOf("Plan") != -1) {
								List<String> planIds = new ArrayList<String>();
								for (int i = 0; i < isSplitProduct.split(",").length; i++) {
									planIds.add("Plan_" + i);
								}
								otherAppResult.put(otherAppResultKey, planIds);
							}
						}
					}
					
					// 重构责任关系结果
					List<List<String>> realDutyResult = new ArrayList<List<String>>();
					for (String dutyTypeCode : dutySns) {
						List<String> subDutyResult = new ArrayList<String>();
						for (FEMDutyAmntPremList tDutyAmnt : dutyInfoMap.get(dutyTypeCode)) {
							if (tDutyAmnt!=null&&dutyResult.containsKey(dutyTypeCode) 
									&& dutyResult.get(dutyTypeCode).contains(tDutyAmnt.getAmnt()) 
									&& !subDutyResult.contains(tDutyAmnt.getAmnt())) {
								subDutyResult.add(tDutyAmnt.getAmnt());
							}
						}
						realDutyResult.add(subDutyResult);
					}
					if (StringUtil.isNotEmpty(feeYearAppFacCode) && splitFeeYear != null && splitFeeYear.length > 0) {
						if (otherAppResult.get(feeYearAppFacCode) != null && otherAppResult.get(feeYearAppFacCode).size() > 0) {
							for (String feeYear : splitFeeYear) {
								otherAppResult.get(feeYearAppFacCode).add(feeYear);
							}
						}
					}
					
					tResult.put("cascade", otherAppResult);
					tResult.put("dutyList", realDutyResult);
					result.put(tKey, tResult);
				} else {
					String tKey = unPremColums.get(appType) + "_" + mapAppKey;
					Map<String, Object> tResult = new HashMap<String, Object>();
					Map<String,List<String>> otherAppResult = new HashMap<String, List<String>>();  // 当前勾稽关系拼装结果
					Set<String> dutyResult = new HashSet<String>();  // 责任关系拼装结果
					
					// 初始化勾稽关系拼装结果
					for (String tColumnName : premColums.keySet()) {
						if (!"duty".equalsIgnoreCase(tColumnName)) {
							otherAppResult.put(premColums.get(tColumnName) + "_" + tColumnName, new ArrayList<String>());
						}
					}
					for (int i = 0; i < dt2.getRowCount(); i++) {
						for (String tColumnName : premColums.keySet()) {
							if (!"duty".equalsIgnoreCase(tColumnName)) {
								String tOtherKey = premColums.get(tColumnName) + "_" + tColumnName;
								String tOtherValue = "";
								for (String tMapAppKey : mapApp.keySet()) {
									if (tMapAppKey.indexOf(tColumnName) != -1 
											&& mapApp.get(tMapAppKey).equals(dt2.getString(i, tColumnName))) {
										tOtherValue = tMapAppKey;
										break;
									}
								}
								
								if (!otherAppResult.get(tOtherKey).contains(tOtherValue)) {
									otherAppResult.get(tOtherKey).add(tOtherValue);
								}
							} else {
								dutyResult.add(mapDuty.get(dt2.getString(i, tColumnName)));
							}
						}
					}
					
					// 对于拆分多页面产品，需要对Plan做特殊处理
					String isSplitProduct = String.valueOf(tFMRisk.getBackUp5());
					if (StringUtil.isNotEmpty(isSplitProduct)) {
						for (String otherAppResultKey : otherAppResult.keySet()) {
							if (otherAppResultKey.indexOf("Plan") != -1) {
								List<String> planIds = new ArrayList<String>();
								for (int i = 0; i < isSplitProduct.split(",").length; i++) {
									planIds.add("Plan_" + i);
								}
								otherAppResult.put(otherAppResultKey, planIds);
							}
						}
					}
					
					// 重构责任关系结果
					List<List<String>> realDutyResult = new ArrayList<List<String>>();
					for (String dutyTypeCode : dutySns) {
						List<String> subDutyResult = new ArrayList<String>();
						for (FEMDutyAmntPremList tDutyAmnt : dutyInfoMap.get(dutyTypeCode)) {
							if (dutyResult.contains(tDutyAmnt.getAmnt()) && !subDutyResult.contains(tDutyAmnt.getAmnt())) {
								subDutyResult.add(tDutyAmnt.getAmnt());
							}
						}
						realDutyResult.add(subDutyResult);
					}
					if (StringUtil.isNotEmpty(feeYearAppFacCode) && splitFeeYear != null && splitFeeYear.length > 0) {
						if (otherAppResult.get(feeYearAppFacCode) != null && otherAppResult.get(feeYearAppFacCode).size() > 0) {
							for (String feeYear : splitFeeYear) {
								otherAppResult.get(feeYearAppFacCode).add(feeYear);
							}
						}
					}
					tResult.put("cascade", otherAppResult);
					tResult.put("dutyList", realDutyResult);
					result.put(tKey, tResult);
				}
			}
			
			// 投保年龄数据类型为日期时需要拼装特殊格式的数据,并与性别组成联合投保要素
			if (hasDateTextAge) {
				// 获取费率表数据
				premColums.put("TextAge", textAgeCode);
				String columString3 = StringUtil.join(premColums.keySet().toArray());
				String sql3 = "SELECT " + columString3 + " FROM riskcode" + tFMRisk.getRiskCode() + "dutycode000b GROUP BY " + columString3;
				DataTable dt3 = new QueryBuilder(sql3).executeDataTable();
				
				// 获取年龄、性别集合(key为 年龄#性别编号,value为费率表中年龄实际值和性别实际值组成的数组)
				Map<String, String[]> ages = new HashMap<String, String[]>();
				for (int i = 0; i < dt3.getRowCount(); i++) {
					String textAgeStr = dt3.getString(i, "TextAge");
					String age = textAgeStr.substring(textAgeStr.indexOf("-") + 1, textAgeStr.lastIndexOf("Y"));
					String sex = dt3.getString(i, "Sex");
					String sexId = "";
					for (String tMapAppKey : mapApp.keySet()) {
						if (tMapAppKey.indexOf("Sex") != -1 && mapApp.get(tMapAppKey).equals(sex)) {
							sexId = tMapAppKey;
							break;
						}
					}
					String ageKey = age + "#" + sexId;
					if (!ages.containsKey(ageKey)) {
						ages.put(ageKey, new String[] {textAgeStr, sex});
					}
				}
				
				// 开始拼装
				for (String ageKey : ages.keySet()) {
					// 当前字段结果(年龄和性别组成联合投保要素)
					String age = ageKey.substring(0, ageKey.indexOf("#"));
					String sexId = ageKey.substring(ageKey.indexOf("#") + 1);
					String tKey = textAgeCode + "_TextAge_" + age + "_" + sexId;
					String tKey2 = sexCode + "_" + sexId + "_TextAge_" + age;
					
					Map<String, Object> tResult = new HashMap<String, Object>();
					
					// 拼装其他字段所有可能的勾稽关系
					Set<String> otherColums = new HashSet<String>(premColums.keySet());
					otherColums.remove("TextAge");
					Map<String,List<String>> otherAppResult = new HashMap<String, List<String>>();  // 当前勾稽关系拼装结果
					Set<String> dutyResult = new HashSet<String>();  // 责任关系拼装结果
					
					// 初始化勾稽关系拼装结果
					for (String tColumnName : otherColums) {
						if (!"duty".equalsIgnoreCase(tColumnName)) {
							otherAppResult.put(premColums.get(tColumnName) + "_" + tColumnName, new ArrayList<String>());
						}
					}
					otherColums.remove("Sex");
					for (int i = 0; i < dt3.getRowCount(); i++) {
						if (ages.get(ageKey)[0].equals(dt3.getString(i, "TextAge"))) {
							// 特殊处理性别
							String sexKey = premColums.get("Sex") + "_Sex";
							String sexValue = "";
							for (String tMapAppKey : mapApp.keySet()) {
								if (tMapAppKey.indexOf("Sex") != -1 && mapApp.get(tMapAppKey).equals(dt3.getString(i, "Sex"))) {
									sexValue = tMapAppKey;
									break;
								}
							}
							if (!otherAppResult.get(sexKey).contains(sexValue)) {
								otherAppResult.get(sexKey).add(sexValue);
							}
							
							if (ages.get(ageKey)[1].equals(dt3.getString(i, "Sex"))) {
								for (String tColumnName : otherColums) {
									if (!"duty".equalsIgnoreCase(tColumnName)) {
										String tOtherKey = premColums.get(tColumnName) + "_" + tColumnName;
										String tOtherValue = "";
										for (String tMapAppKey : mapApp.keySet()) {
											if (tMapAppKey.indexOf(tColumnName) != -1 
													&& mapApp.get(tMapAppKey).equals(dt3.getString(i, tColumnName))) {
												tOtherValue = tMapAppKey;
												break;
											}
										}
										
										if (!otherAppResult.get(tOtherKey).contains(tOtherValue)) {
											otherAppResult.get(tOtherKey).add(tOtherValue);
										}
									} else {
										dutyResult.add(mapDuty.get(dt3.getString(i, tColumnName)));
									}
								}
							}
						}
					}
					for (String tColumnName : unPremColums.keySet()) {
						List<String> valueList = new ArrayList<String>();
						valueList.add(tColumnName + "_0");
						otherAppResult.put(unPremColums.get(tColumnName) + "_" + tColumnName, valueList);
					}
					
					// 对于拆分多页面产品，需要对Plan做特殊处理
					String isSplitProduct = String.valueOf(tFMRisk.getBackUp5());
					if (StringUtil.isNotEmpty(isSplitProduct)) {
						for (String otherAppResultKey : otherAppResult.keySet()) {
							if (otherAppResultKey.indexOf("Plan") != -1) {
								List<String> planIds = new ArrayList<String>();
								for (int i = 0; i < isSplitProduct.split(",").length; i++) {
									planIds.add("Plan_" + i);
								}
								otherAppResult.put(otherAppResultKey, planIds);
							}
						}
					}
					
					// 重构责任关系结果
					List<List<String>> realDutyResult = new ArrayList<List<String>>();
					for (String dutyTypeCode : dutySns) {
						List<String> subDutyResult = new ArrayList<String>();
						for (FEMDutyAmntPremList tDutyAmnt : dutyInfoMap.get(dutyTypeCode)) {
							if (dutyResult.contains(tDutyAmnt.getAmnt()) && !subDutyResult.contains(tDutyAmnt.getAmnt())) {
								subDutyResult.add(tDutyAmnt.getAmnt());
							}
						}
						realDutyResult.add(subDutyResult);
					}
					if (StringUtil.isNotEmpty(feeYearAppFacCode) && splitFeeYear != null && splitFeeYear.length > 0) {
						if (otherAppResult.get(feeYearAppFacCode) != null && otherAppResult.get(feeYearAppFacCode).size() > 0) {
							for (String feeYear : splitFeeYear) {
								otherAppResult.get(feeYearAppFacCode).add(feeYear);
							}
						}
					}
					tResult.put("cascade", otherAppResult);
					tResult.put("dutyList", realDutyResult);
					result.put(tKey, tResult);
					
					Map<String, List<String>> otherAppResult2 = new HashMap<String, List<String>>(otherAppResult);  // 当前勾稽关系拼装结果(无性别)
					for (String otherAppResultKey : otherAppResult2.keySet()) {
						if (otherAppResultKey.contains("Sex")) {
							otherAppResult2.remove(otherAppResultKey);
							break;
						}
					}
					Map<String, Object> tResult2 = new HashMap<String, Object>();
					tResult2.put("cascade", otherAppResult2);
					tResult2.put("dutyList", realDutyResult);
					result.put(tKey2, tResult2);
				}
			}
		}
		JSONObject object_Rela = JSONObject.fromObject(result);
		return object_Rela.toString();
	}
	
	/**
	 * 取得责任默认显示保额及责任组合关系
	 * 
	 * @param tFMRisk
	 * @param mapApp
	 * @param dutySns
	 * @return
	 */
	private String getRiskAppFacDutyDef(FMRisk tFMRisk, Map<String, String> mapApp, List<String> dutySns) {
		String result = "";
		FEMRiskFactorList[] mPlanList = getPlanList(tFMRisk.getFERiskAppFactor());
		FMDuty[] fMDuty = tFMRisk.getFMDuty();
		// 每个计划的责任默认显示信息
		Map<String, Map<String, String>> planDutyMap = new HashMap<String, Map<String, String>>();
		Map<String, String> dutyTypeMap = new HashMap<String, String>();

		List<FMRelaDutyCodeList[]> fmRelaDutyCodeList = new ArrayList<FMRelaDutyCodeList[]>();
		String dutyrela = "";
		if (fMDuty != null && fMDuty.length > 0) {
			FEMDutyFactor dutyFactor = null;
			Map<String, String> mapDuty = new HashMap<String, String>();
			for (FMDuty duty : fMDuty) {
				dutyFactor = duty.getFEMDutyFactor();
				dutyTypeMap.put(duty.getDutyCode(), duty.getCurrency());
				// 责任显示并责任有详细信息
				if ("Y".equals(duty.getIsDisplay()) && dutyFactor != null) {
					FEMDutyAmntPremList[] AmntPremValues = dutyFactor.getFEMDutyAmntPremList();
					if (AmntPremValues != null && AmntPremValues.length > 0 && AmntPremValues[0]!=null) {
						String amnt = AmntPremValues[0].getAmnt();
						Map<String, String> dutys = new HashMap<String, String>();
						for (int j = 0; j < AmntPremValues.length; j++) {
							// 责任保额关联计划的情况 取得每个计划对应责任的默认显示保额
							if ("Y".equals(dutyFactor.getIsRelaRiskFactor())) {
								if (!dutys.containsKey(AmntPremValues[j].getAppFactorValue())) {
									dutys.put(AmntPremValues[j].getAppFactorValue(), AmntPremValues[j].getAmnt());
								}

								if ("Y".equals(AmntPremValues[j].getBackUp2())) {
									dutys.put(AmntPremValues[j].getAppFactorValue(), AmntPremValues[j].getAmnt());
								}
							} else {
								// 责任保额不关联计划 取得该责任的默认显示保额
								if ("Y".equals(AmntPremValues[j].getBackUp2())) {
									amnt = AmntPremValues[j].getAmnt();
								}
							}
						}
						
						if (mPlanList != null && mPlanList.length > 0) {
							// 循环计划 取得计划下所有责任的默认显示保额放入map中
							for (int k = 0; k < mPlanList.length; k++) {

								if (planDutyMap.containsKey(mPlanList[k].getFactorValue())) {
									// 责任关联计划的情况 取得该计划的责任、责任默认显示保额
									if ("Y".equals(dutyFactor.getIsRelaRiskFactor())) {
										planDutyMap.get(mPlanList[k].getFactorValue()).put(duty.getDutyCode(), dutys.get(mPlanList[k].getFactorValue()));
									} else {
										// 责任不关联计划的情况 计划的责任、责任默认显示保额都一致
										planDutyMap.get(mPlanList[k].getFactorValue()).put(duty.getDutyCode(), amnt);
									}
								} else {
									Map<String, String> dutyinfo = new HashMap<String, String>();
									// 责任关联计划的情况 取得该计划的责任、责任默认显示保额
									if ("Y".equals(dutyFactor.getIsRelaRiskFactor())) {
										dutyinfo.put(duty.getDutyCode(), dutys.get(mPlanList[k].getFactorValue()));
									} else {
										// 责任不关联计划的情况 计划的责任、责任默认显示保额都一致
										dutyinfo.put(duty.getDutyCode(), amnt);
									}
									planDutyMap.put(mPlanList[k].getFactorValue(), dutyinfo);
								}
							}
						} else {
							mapDuty.put(duty.getDutyCode(), amnt);
						}
					}
				}
				// 责任有关联的情况 取得责任关联
				if (duty.getFMRelaDutyCodeList() != null && duty.getFMRelaDutyCodeList().length > 0 && duty.getFMRelaDutyCodeList()[0] != null) {
					fmRelaDutyCodeList.add(duty.getFMRelaDutyCodeList());
				}
			}
			// 取得责任组合
			if (fmRelaDutyCodeList.size() > 0) {
				FMRelaDutyCodeList[] rela = null;
				int size = fmRelaDutyCodeList.size();
				dutyrela = "[";
				for (int i = 0; i < size; i++) {
					rela = fmRelaDutyCodeList.get(i);
					int len = rela.length;
					dutyrela += ("[\"" + rela[0].getDutyCode() + "_" + dutyTypeMap.get(rela[0].getDutyCode()) + "\",");
					for (int j = 0; j < len; j++) {
						dutyrela += ("\"" + rela[j].getRelaDutyCode() + "_" + dutyTypeMap.get(rela[j].getRelaDutyCode()) + "\",");
					}
					dutyrela = dutyrela.substring(0, dutyrela.length() - 1) + "],";
				}
				dutyrela = dutyrela.substring(0, dutyrela.length() - 1) + "]";
			} else {
				dutyrela = "[]";
			}

			// 拼前台用的json数据
			if (planDutyMap.isEmpty()) {
				if (mapDuty.isEmpty()) {
					result = "{}";
					
				} else {
					int size = dutySns.size();
					result = "{\"default\":[{";
					// 产品默认保额
					for (int i = 0; i < size; i++) {
						if (mapDuty.containsKey(dutySns.get(i))) {
							result += i + ":'" + mapDuty.get(dutySns.get(i)) + "',";
						}
					}
					result = result.substring(0, result.length() - 1) + "},";
					result = result + dutyrela + "]}";
				}
				
			} else {
				result += "{";
				int size = dutySns.size();
				for (String key : planDutyMap.keySet()) {
					String planId = "";
					for (Entry<String, String> entry : mapApp.entrySet()) {
						if (key.equals(entry.getValue())) {
							planId = entry.getKey();
							break;
						}
					}
					
					result += (planId + ":[{");
					Map<String, String> map = planDutyMap.get(key);
					for (int i = 0; i < size; i++) {
						if (map.containsKey(dutySns.get(i))) {
							result += (i + ":'" + map.get(dutySns.get(i)) + "',");
						}
					}
					result = (result.substring(0, result.length() - 1) + "}," + dutyrela + "],");
				}

				result = result.substring(0, result.length() - 1) + "}";
			}

		}

		return result;
	}
	
	/**
	 * 准备产品计算相关要素
	 * 
	 * @return
	 */
	private boolean prepareProdcutFactorsForArticle(String catalogID) {
		if (showBenefitFlag) {
			mProductFactors.put("yearCashFlag", "Y");
		} else {
			mProductFactors.put("yearCashFlag", "N");
		}

		mProductFactors.put("RiskAppFactors", getRiskAppFactors(mFMRisk));
		Map<String, String> mapApp = new HashMap<String, String>();
		// 投保要素
		mProductFactors.put("CalHTML", getRiskAppFactorsHTML(mFMRisk, mapApp));
		mProductFactors.put("CalHTML2", getRiskAppFactorsHTML2(mFMRisk));
		// 责任要素
		mProductFactors.put("DutyHTML", getDutyHTML(mFMRisk));
		// 记录按顺序显示的责任
		List<String> listDuty = new ArrayList<String>();
		// 责任对应的保额
		Map<String, String> mapDuty = new HashMap<String, String>();
		// 产品详细页责任要素
		mProductFactors.put("DutyHTML1", getDutyHTML1(mFMRisk, listDuty, mapDuty));
		if ("Y".equals(mFMRisk.getBackUp4()) || "L".equals(mFMRisk.getRiskPeriod())) {  // 复杂产品或长期险投保要素与责任之间的关系
			mProductFactors.put("AppFacDutyRela", getRiskAppFacDutyRelaNew(mFMRisk, mapApp, listDuty, mapDuty));
			// 取得责任默认值、责任组合关系
			mProductFactors.put("AppFacDutyDef", getRiskAppFacDutyDef(mFMRisk, mapApp, listDuty));
		} else {
			mProductFactors.put("AppFacDutyRela", "{}");
			mProductFactors.put("AppFacDutyDef", "{}");
		}
		//长期险标志
		mProductFactors.put("LongInsurance", mFMRisk.getRiskPeriod());
		// 险种编码
		mProductFactors.put("RiskCode", mFMRisk.getRiskCode());
		// 复杂产品标识
		mProductFactors.put("ComplicatedFlag", mFMRisk.getBackUp4());
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
		// 产品亮点--列表页
		mProductFactors.put("FEMRiskBrightSpot", getBrightSoptHTML(mFMRisk));
		// 产品亮点--详细页
		mProductFactors.put("ProductDetailBright", getProductDetailBright(mFMRisk,catalogID));
		// 详细页--产品介绍
		mProductFactors.put("ProductPresentation", getProductPresentation(mFMRisk));
		// 详细页--产品基本信息公共头部
		mProductFactors.put("ProductDetailFront", getProductDetailBase(mFMRisk,"Front"));
		// 详细页--产品基本信息公共尾部
		mProductFactors.put("ProductDetailTail", getProductDetailBase(mFMRisk,"Tail"));
		// 投保提示
		mProductFactors.put("FCDoc", getFCDoc(mFMRisk));
		// 产品折扣率
		mProductFactors.put("DiscountRate", mFMRisk.getBackUp2());
		
		// 原价
		mProductFactors.put("BasePrem", mFMRisk.getInitPrem());
		
		// 原价HTML显示--类淘宝列表页需求
		mProductFactors.put("BasePremHTML", getBasePremHTML(mFMRisk));
		//String activityPriceNew = ActivityCalculate.ProductCalculate(mFMRisk.getRiskCode(), mFMRisk.getDiscountPrice(), "wj");
		// 折扣保费  活动价格 小于 折扣价格 activityPrice.equals(mFMRisk.getDiscountPrice())
		Map<String,String> ProductIDMap = new HashMap<String, String>();
		ProductIDMap.put(mFMRisk.getRiskCode(), mFMRisk.getDiscountPrice());
		
		String rIntegral = "";
		String isSiteActivity = "N";
		Map<String, Map<String, String>> resultProductDetail;
		if (isPointProductflag) {
			resultProductDetail = ActivityCalculate.ProductCalculate(ProductIDMap,"","jfsc");
		} else {
			resultProductDetail = ActivityCalculate.ProductCalculate(ProductIDMap,"","wj");
		}
		Map<String, String> productDetail = resultProductDetail.get(mFMRisk.getRiskCode());
		
		String activityPrice = productDetail.get("Amount");
		rIntegral = productDetail.get("Integral");
		double dActivityPricenew = new BigDecimal(activityPrice).doubleValue();
		double dDiscountPrice = new BigDecimal(mFMRisk.getDiscountPrice()).doubleValue();
		
		if(dActivityPricenew < dDiscountPrice){
			//开启全站活动
			mProductFactors.put("BasePremValue", activityPrice);
			isSiteActivity = "Y";
		}else{
			mProductFactors.put("BasePremValue", mFMRisk.getDiscountPrice());
		}
		
		mProductFactors.put("InitPrem", mFMRisk.getDiscountPrice());
		//详细使用 
		mProductFactors.put(
				"prodcutMarkPrice",
				getProdcutMarkPrice(mFMRisk.getBackUp2(),
						mFMRisk.getInitPrem(), mFMRisk.getDiscountPrice(),
						mFMRisk.getRiskCode(), mFMRisk.getAutoRelatedClause()));
		/*CPS 产品价格*/
		Map<String, Map<String, String>> resultProductDetailCPS = ActivityCalculate.ProductCalculate(ProductIDMap,"","cps");
		Map<String, String> productDetailCPS = resultProductDetailCPS.get(mFMRisk.getRiskCode());
		String activityPriceCPS = productDetailCPS.get("Amount");
		double dActivityPricenewCPS = new BigDecimal(activityPriceCPS).doubleValue();
		double dDiscountPriceCPS = new BigDecimal(mFMRisk.getDiscountPrice()).doubleValue();
		if(dActivityPricenewCPS < dDiscountPriceCPS){
			//开启全站活动
			mProductFactors.put("InitPremCPS", activityPriceCPS);
		
			//详细使用 getProdcutMarkPrice 方法 参数1 固定设定费率
			mProductFactors.put(
					"prodcutMarkPriceCPS",
					getProdcutMarkPriceCPS("1",
							mFMRisk.getDiscountPrice(), activityPriceCPS,
							mFMRisk.getRiskCode(), mFMRisk.getAutoRelatedClause()));
		}else{
			mProductFactors.put("InitPremCPS", mFMRisk.getDiscountPrice());
			
			//详细使用 
			mProductFactors.put(
					"prodcutMarkPriceCPS",
					getProdcutMarkPriceCPS(mFMRisk.getBackUp2(),
							mFMRisk.getInitPrem(), mFMRisk.getDiscountPrice(),
							mFMRisk.getRiskCode(), mFMRisk.getAutoRelatedClause()));
		}
		/*CPS 产品价格 end*/
		// 原价  V3
		mProductFactors.put("BasePremV3", getBasePremV3(mFMRisk,isSiteActivity));
		
		//mProductFactors.put("BasePremValue", getBasePremValue(mFMRisk));
		mProductFactors.put("SplitRiskCode", mFMRisk.getBackUp5());//产品计划拆分
		mProductFactors.put("CanonicalProduct", mFMRisk.getBackUp7());//拆分最权威产品
		// 高倍积分
		mProductFactors.put("ProductInt", rIntegral);
		
		// 内部统计险种小类别面包屑 add by fhz 20121225
		mProductFactors.put("RiskBreadcrumb",
				getRiskBreadcrumb(mFMRisk.getBelongFlag()));

		// 投保须知
		mProductFactors.put("Instruction", getInstruction(catalogID));
		// 投保声明
		mProductFactors.put("InsuranceDec", getInsuranceDec());
		// 是否cps产品标示
		mProductFactors.put("isCpsProduct", mFMRisk.getAutoRelatedClause());
		// cps产品跳转路径
 		mProductFactors.put("cpsAdress", mFMRisk.getOptionalClause());
		// 是否财险标志
		mProductFactors.put("isFinancialRisks", getProductCategories());
		// 条款信息
		mProductFactors.put("termInformation", getTermInformation(catalogID));

		// 梅俊峰 add 20130614 start
		// 可购份数
		if (StringUtils.isNotBlank(mFMRisk.getBackUp1())) {
			mProductFactors.put("limcount", "<b>可购份数：</b>"+ mFMRisk.getBackUp1());
			mProductFactors.put("limcountNumber", mFMRisk.getBackUp1());
		} else {
			mProductFactors.put("limcount", "");
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
		// 保险品牌公司链接
		mProductFactors.put("CompanyLink", getCompanyLink(mFMRisk.getRiskCode()));
		// 类淘宝列表页-责任要素
		mProductFactors.put("DutyHTML2", getDutyHTML2(mFMRisk));
		// 类淘宝列表页-产品亮点
		mProductFactors.put("FEMRiskBrightSpotNew", getBrightSoptHTML1(mFMRisk));
		// 类淘宝列表页-产品活动
		mProductFactors.put("ProductActive", getProductActive(mFMRisk));
		// 理赔资料
		mProductFactors.put("ClaimData",getClaimData(fdInsCom));
		// 理赔指导
		mProductFactors.put("ClaimGuide",getClaimGuide(fdInsCom));
		
		// 列表页第三版
		
		//适合人群
		//mProductFactors.put("AdaptPeopleInfoV3", getAdaptPeopleInfoV3(mFMRisk));
		//适合人群
		mProductFactors.put("AdaptPeopleInfoListV3", getAdaptPeopleInfoListV3(mFMRisk));
		//责任条款
		mProductFactors.put("DutyHTMLV3", getDutyHTMLV3(mFMRisk));
		//责任条款V4 modify by cuishigang 2015-07-09 
		mProductFactors.put("DutyHTMLV4", getDutyHTMLV4(mFMRisk));
		//责任条款
		mProductFactors.put("RecomDutyV3", getRecommendDutyV3(mFMRisk));
		Map<String, String> map = getFCDoc2();
		if (map != null) {
			// 保险条款
			mProductFactors.put("Doc1", map.get("Doc1"));
			mProductFactors.put("Doc5", map.get("Doc5"));
			// 保单样本
			mProductFactors.put("Doc3", map.get("Doc3"));
			// 发票样本
			mProductFactors.put("Doc7", map.get("Doc7"));
			// 条款排序
			mProductFactors.put("SortDoc", map.get("SortDoc"));


			
			// 行业及职业分类表
			/*
			 * if(map.get("Doc2")!=null && map.get("Doc2").length()>=1){
			 * mProductFactors.put("Doc2", map.get("Doc2")); } //保单样本
			 * if(map.get("Doc3")!=null && map.get("Doc3").length()>=1){
			 * mProductFactors.put("Doc3", map.get("Doc3")); }
			 */
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

		// 现金价值标识
		if (cashValueFlag) {
			mProductFactors.put("cashValueFlag", "Y");
		} else {
			mProductFactors.put("cashValueFlag", "N");
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
				+ "<div class=\"li_list\"><b>职业等级：</b><a href=\""
				+ cMap.get("Doc2")
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
	
	
	/**
	 * @Title: updatePrice.
	 * @Description: TODO(SDProduct ADD产品原价。).
	 * @param p_ProductID
	 * @param p_DiscountPrice void.
	 * @author CongZN.
	 */
	public void updatePrice(String p_ProductID,String p_DiscountPrice){
		Transaction ts = new Transaction();
		try {
			if(StringUtil.isNotEmpty(p_ProductID) && StringUtil.isNotEmpty(p_DiscountPrice)){
				QueryBuilder qb = new QueryBuilder("update SDProduct set Remark4 = ? where ProductID = ?");
				qb.add(p_DiscountPrice);
				qb.add(p_ProductID);
				ts.add(qb);
				if(!ts.commit()){
					logger.error("产品同步 SDProduct Remark4 Add 产品原价 提交事务异常,信息：{}", ts.getExceptionMessage());
				}
				//增加字段 生成Schema
//			SDSearchRelaProductSchema sds = new SDSearchRelaProductSchema();
//			sds.setProductID(p_ProductID);
			//mFMRisk.getDiscountPrice()
			}
		} catch (Exception e) {
			logger.error("产品同步  updatePrice 方法异常,信息："+e.getMessage(), e);
		}
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

			String doc1 = "";
			String doc5 = "";
			for (int i = 0; i < fc.length; i++) {
				String href = fc[i].getDocPath().replace(ProductPath,
						ProductURL);

				if ("01".equals(fc[i].getDocTypeCode())) {
					// 保险条款
					String[] item = fc[i].getDocName().split("\\.");// 去掉保险条款后缀名
					doc1 = doc1 + "<li><a target=\"_blank\" href=\"" + href
							+ "\"><em class=\"r_icon\"></em>" + INSURANCECLAUSE + "</a></li>";

				} else if ("02".equals(fc[i].getDocTypeCode())) {
					// 行业及职业分类表
					map.put("Doc2", href);
				} else if ("03".equals(fc[i].getDocTypeCode())) {
					// 保单样本
					map.put("Doc3", "<li><a target=\"_blank\" href=\"" + href
							+ "\"><em class=\"r_icon\"></em>" + PROEXAMPLE + "</a></li>");
				} else if ("04".equals(fc[i].getDocTypeCode())) {
					// 拒保国家
					map.put("Doc4", href);
				} else if ("06".equals(fc[i].getDocTypeCode())){
					// 免除责任条款
					String[] item = fc[i].getDocName().split("\\.");// 去掉免除责任条款后缀名
					doc5 = doc5 + "<li><a target=\"_blank\" href=\"" + href
							+ "\"><em class=\"r_icon\"></em>" + ESCAPECLAUSE + "</a></li>";
				} else if ("07".equals(fc[i].getDocTypeCode())) {
					// 发票样本
					map.put("Doc7", "<li><a target=\"_blank\" href=\"" + href
							+ "\"><em class=\"r_icon\"></em>" + INVOICEEXAMPLE + "</a></li>");
				}
			}
			map.put("Doc1", doc1);
			map.put("Doc5", doc5);

			// 为了条款排序，再设置一个article属性SortDoc，把所有Doc排序后链接一起
			qb = new QueryBuilder("SELECT DocTypeCode,DocName,DocPath FROM fcdoc WHERE projcode = '"+ mFMRisk.getRiskCode() +"' ORDER BY fileno");
			DataTable dt = qb.executeDataTable();
			if (dt != null && dt.getRowCount() > 0) {
				String docTypeCode = null;
				String docName = null;
				StringBuilder sb = new StringBuilder();
				String doc;
				for (int i = 0; i < dt.getRowCount(); i++) {
					docTypeCode = dt.getString(i, "DocTypeCode");
					String href = dt.getString(i, "DocPath").replace(ProductPath, ProductURL);
					docName = dt.getString(i, "DocName").split("\\.")[0];
					if ("01".equals(docTypeCode) || "03".equals(docTypeCode)
							|| "06".equals(docTypeCode) || "07".equals(docTypeCode)) {
                        doc = "<li><a target=\"_blank\" href=\"" + href
                                + "\"><em class=\"r_icon\"></em>" + docName + "</a></li>";
					} else {
						continue;
					}
					sb.append(doc);
				}
				map.put("SortDoc", sb.toString());
			}
		} catch (Exception e) {
			return null;
		}
		return map;
	}

	// 梅俊峰 add 20130614 end

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
		// 这玩意这系统里也有
		SSRS aSSRS = new SSRS();
		try {
			// fhz 获得小类的URL和名称
			String querySql = "select url,name from zccatalog where producttype='"
					+ tSubType + "'";
			aSSRS = db.execSQL(querySql);
			if (aSSRS == null) {
				logger.error(
						"产品【{}_{}】,方法getRiskBreadcrumb查询险种小类名称和URL失败",
						mFMRisk.getRiskCode(), mFMRisk.getRiskName());
				return null;
			}
			String url = aSSRS.GetText(1, 1);
			String name = aSSRS.GetText(1, 2);
			tBreadcrumb = "<a href='../" + url + "'  target='_self' >" + name
					+ "</a>";
		} catch (Exception e) {
			logger.error(
					"同步产品信息,getRiskBreadcrumb方法,产品【" + mFMRisk.getRiskCode()
							+ "_" + mFMRisk.getRiskName()
							+ "】获取产品面包屑BelongFlag信息发生异常！！！" + e.getMessage(), e);
		}
		return tBreadcrumb;
	}

	/**
	 * 条款信息
	 *
	 * @param catalogID
	 * @return
	 */
	private String getTermInformation(String catalogID) {
		StringBuffer sb = new StringBuffer();
		FEUIInfo[] feArr = mFMRisk.getFEUIInfo();
		DataTable dtcatalog = new QueryBuilder("select type from zdconfig where value = (select parentid from zccatalog where id='"+catalogID+"')").executeDataTable();
		String catalogType = "";
		if(dtcatalog.getRowCount()>0){
			catalogType = dtcatalog.getString(0, 0);
		}
		if("CatalogBXSCID".equals(catalogType)){
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
				str = "<div class=\"ccp_box_con\"> <h3 class=\"n_title\">条款信息</h3><ul class=\"zr_list_conbs_term\">"
						+ sb.toString() + "</ul></div>";
			}
			return str;
		}else{
			for (int i = 0; feArr != null && i < feArr.length; i++) {
				FEUIInfo fe = feArr[i];
				if (fe != null && "TermInformation".equalsIgnoreCase(fe.getUIInfo())
						&& "Y".equalsIgnoreCase(fe.getIsDisplay())) {
					String[] showinfo = fe.getShowInfo().split("\n");
					int count = 0;
					for (String s : showinfo) {
						sb.append("<li>"+ s + "</li>");
					}
				}
			}
			String str = "";
			if (sb != null && !"".equals(sb.toString())) {
				str = "<p class=\"ttl\"><span>条款信息</span></p><ol class=\"notice_termlist\">" + sb.toString() + "</ol>";
			}
			return str;
		}
	}

	/**
	 * 获取投保须知
	 *
	 * @param catalogID
	 * @return
	 */
	private String getInstruction(String catalogID) {
		StringBuffer sb = new StringBuffer();
		FEUIInfo[] feArr = mFMRisk.getFEUIInfo();
		DataTable dtcatalog = new QueryBuilder("select type from zdconfig where value = (select parentid from zccatalog where id='"+catalogID+"')").executeDataTable();
		String catalogType = "";
		if(dtcatalog.getRowCount()>0){
			catalogType = dtcatalog.getString(0, 0);
		}
		if("CatalogBXSCID".equals(catalogType)){
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
		}else{
			for (int i = 0; feArr != null && i < feArr.length; i++) {
				FEUIInfo fe = feArr[i];
				if (fe != null && "Information".equalsIgnoreCase(fe.getUIInfo())
						&& "Y".equalsIgnoreCase(fe.getIsDisplay())) {
					String[] showinfo = fe.getShowInfo().split("\n");
					int count = 0;
					for (String s : showinfo) {
						int defaltLength=2;
						int commaLength=s.indexOf("、");
						if(-1!=commaLength){
							defaltLength=commaLength+1;
						}
						sb.append("<li>" + (++count) + "、"
								+ s.substring(defaltLength) + "</li>");
					}
				}
			}
			String str = "";
			if (sb != null && !"".equals(sb.toString())) {
				str = "<p class=\"ttl\"><span>投保须知</span></p><ol class=\"notice_list\">" + sb.toString() + "</ol>";
			}
			return str;
		}
	}

	/**
	 * 取得投保声明
	 * 
	 * @return 投保声明
	 */
	private String getInsuranceDec() {
		StringBuffer sb = new StringBuffer();
		FEUIInfo[] feArr = mFMRisk.getFEUIInfo();
		FEUIInfo fe;
		for (int i = 0; feArr != null && i < feArr.length; i++) {
			fe = feArr[i];
			// 投保声明信息 并且显示的情况
			if (fe != null && "InsuranceDec".equalsIgnoreCase(fe.getUIInfo())
					&& "Y".equalsIgnoreCase(fe.getIsDisplay())) {
				String[] showinfo = fe.getShowInfo().split("\n");
				for (String s : showinfo) {
					sb.append("<li>" + s + "</li>");
				}
			}
		}
		String str = "";
		if (sb != null && !"".equals(sb.toString())) {
			str = "<div class=\"ins-tit un_bot_bor\">投保声明及必读</div>"
					+ "<div class=\"msg-con bdmx_table_b\">"
					+ "<ol class=\"sz_list\">" + sb.toString() + "</ol></div>";
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
		// 隐藏域存储产品单价，份数价格计算时使用
		String content = "<input type=\"hidden\" id=\"cp-zprcie\" value=\""
				+ DiscountPrice
				+ "\"><input type=\"hidden\" id=\"cp-yprcie\" value=\""
				+ InitPrem
				+ "\">";
		
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
				content += "<li class=\"priceC\"> <span name=\"Ajax_Prict_"+productID+"\" id=\"productPrem_"
						+ productID
						+ "\" ><em>￥</em>"
						+ DiscountPrice
						+ "</span></li><li class=\"priceB\"><span id=\"Clear_li_Ajax_Prict_"+productID+"\" style=\"display:none\">原价：</span><span name=\"Clear_Ajax_Prict_"+productID+"\" style=\"display:none\" id=\"productRatePrem_"
						+ productID + "\"></span></li>";
			}
		} else {
			content += "<li class=\"priceA\"><span name=\"Ajax_Prict_"+productID+"\" id=\"productPrem_"
					+ productID + "\" class=\"CurrentPrice\"><em>￥</em>"
					+ DiscountPrice + "</span></li>";
			content += "<li class=\"priceB\"><span id=\"Clear_li_Ajax_Prict_"+productID+"\" style=\"display:none\">原价：</span><span name=\"Clear_Ajax_Prict_"+productID+"\" class=\"priceB_\" style=\"display:none\" id=\"productRatePrem_"
					+ productID + "\"  >" + InitPrem + "</span></li>";
		}
		return content;
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
	private String getProdcutMarkPriceCPS(String DiscountRate, String InitPrem,
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
			content += "<li class=\"priceB\"><span style=\"display:none\">原价：</span><span class=\"priceB_\" style=\"display:none\" id=\"productRatePrem_"
					+ productID + "\"  >" + InitPrem + "</span></li>";
		}
		return content;
	}
	
	private String getClaimData(FDInsCom fdInsCom) {

		FMClaimDataTitle[] fMClaimDataTitle = fdInsCom.getFMClaimDataTitle();
		String HTMLStr = "";
		for (int i = 0; i < fMClaimDataTitle.length; i++) {
			if(fMClaimDataTitle != null && fMClaimDataTitle[i] != null){
				HTMLStr += "<dt>" + fMClaimDataTitle[i].getTitle() + "</dt><dd><ol>";
				FMClaimData[] fMClaimData = fMClaimDataTitle[i].getMFMClaimDataList();
				for (int j = 0; j < fMClaimData.length; j++) {
					if (fMClaimData[j] != null) {
						HTMLStr += "<li>" + (j + 1) + "、" + fMClaimData[j].getContent() + "</li>";

					}
				}
				HTMLStr += "</ol></dd>";
			}
		}

		return HTMLStr;
	}

	private String getClaimGuide(FDInsCom fdInsCom) {

		FMClaimGuide[] FMClaimGuide = fdInsCom.getFMClaimGuide();
		String HTMLStr = "";
		for (int i = 0; i < FMClaimGuide.length; i++) {
			if(FMClaimGuide != null && FMClaimGuide[i] != null){
				HTMLStr += "<li class=\"guide_0" + (i + 1) + "\"><p>" + FMClaimGuide[i].getCGContent() + "</p><li>";
			}
		}

		return HTMLStr;
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
		// System.out.println(FCDoc);
		return FCDoc;
	}

	private String getSupplierCode(String supplierCode) {
		if (supplierCode != null && supplierCode.length() > 4) {
			return supplierCode.substring(0, 4);
		}
		return supplierCode;
	}

	/**
	 * 获取产品特色
	 *
	 * @param eRiskFeatures
	 * @return
	 */
	private String convertToHTML(String eRiskFeatures) {
		if (eRiskFeatures != null && !"".equals(eRiskFeatures)) {
			String[] eRiskFeaturesArr = eRiskFeatures.split("\n");
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < eRiskFeaturesArr.length; i++) {
				sb.append("<li>");
				sb.append(eRiskFeaturesArr[i]);
				sb.append("</li>");
			}
			return sb.toString();
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

	private String getRiskAppFactorsHTML(FMRisk tFMRisk, Map<String, String> map) {
		String tHTML = "<div id=\"divRiskAppFactor_" + tFMRisk.getRiskCode()
				+ "\" class=\"bz_time clearfix\">";
		FERiskAppFactor[] appFactors = tFMRisk.getFERiskAppFactor();
		String isSplitProduct = String.valueOf(tFMRisk.getBackUp5());// 标记支付是拆分产品
		if (appFactors != null && appFactors.length > 0
				&& appFactors[0] != null) {
			try {
				String eleHTML = "";
				String displayStr = "";
				// 缴费年期拆分
				map.put("FeeYearSplitProduct", tFMRisk.getBackUp6());
				relaMapCache=new LinkedHashMap<String,List<String>>();
				for (int i = 0; i < appFactors.length; i++) {
					
					if ("Y".equalsIgnoreCase(appFactors[i].getIsDisplay())) {
						displayStr = "";
					} else {
						displayStr = "style=\"display:none;\"";
					}
					switch (Integer.valueOf(appFactors[i].getDataType())) {
					case 7:
						if ("TextAge".equals(appFactors[i].getFactorType())) {
							int minAge = getDefaultAge(appFactors[i].getFEMRiskFactorList(), "min");
							int maxAge = getDefaultAge(appFactors[i].getFEMRiskFactorList(), "max");
							int defaultAge = getDefaultAge(appFactors[i].getFEMRiskFactorList(), "default");
							String defaultBir = getDefalutBirth(appFactors[i].getFEMRiskFactorList());
                            int days = PubFun.getOneDate(defaultBir,"min");
                            int maxDay = PubFun.getOneDate(maxAge + "Y","max") - 1;
                            String defaultBirthday = PubFun.calSDate(PubFun.getCurrentDate(), -days, "D");
                            String minAgeStr = getMinAgeStr(appFactors[i].getFEMRiskFactorList());
                            String minDayStr = "";
                            if (minAgeStr.contains("天")) {
								minDayStr = minAgeStr.replace("天", "D");
                            } else {
								minDayStr = minAgeStr + "Y";
                            }
                            int minDay = PubFun.getOneDate(minDayStr,"min");
							String html="<div class=\"tb_age_nocon clearfix\"><em class=\"cp_title_ms\">投保年龄</em><p class=\"cp_yh_mes\">"+minAgeStr+"-"+maxAge+"周岁</p></div><input id=\"qhuserAge\" type=\"hidden\" value=\""+minAge+"\" name=\"qhuserAge\"><input id=\"qhuserMaxAge\" type=\"hidden\" value=\""+maxAge+"\" name=\"qhuserMaxAge\"><input id=\"qhDayOrYear\" type=\"hidden\" value=\"1\" name=\"qhDayOrYear\"><div class=\"clear\"></div>";
							eleHTML = html+"<div class=\"tb_age clearfix\"><em class=\"cp_title_ms\">出生日期</em><ul id=\"applicantBirthday\" style=\"width:100px;\" ><li id=\"birthday_li_id\"><input type=\"text\" class=\"text_age\" id=\"inpRiskAppFactor_"
									+ appFactors[i].getFactorType()
									+ "\" name=\"inpRiskAppFactor_"
									+ appFactors[i].getFactorType()
									+"\" value=\""+defaultBirthday
									+ "\" data-id='"+ tFMRisk.getRiskCode()+"' data-area='"+appFactors[i].getAppFactorCode()
									+ "_"
									+ appFactors[i].getFactorType()+"' data-min=\"-"+maxDay+"\" data-max=\"-"+minDay+"\"/>"
									+"<em class=\"icon\"></em></li></ul><ul id=\""
									+ appFactors[i].getAppFactorCode()
									+ "_"
									+ appFactors[i].getFactorType()
									+ "\" class=\"up_li_width\">"//此处的样式文件切记不可更改，需要更改时请与前端沟通，此处影响长期险多个投保要素勾稽关系
									+ "<li class=\"li_selected\"><span id=\""
									+ appFactors[i].getFactorType()
									+ "_0\" class=\"tb_span_aga\" name=\""
									+ defaultAge
									+ "Y\" >"
									+ defaultAge + "周岁</span></li>" + "</ul></div><div class=\"clear\"></div>";
							//组合投保要素勾稽关系所需数据
							List<String> list=new ArrayList<String>();
							list.add((appFactors[i].getAppFactorCode()+ "_"+ appFactors[i].getFactorType()).trim());
							list.add((appFactors[i].getFactorType()+ "_"+defaultAge).trim());
							relaMapCache.put(defaultAge+"", list);
							relaMapCache.put("TextAge_ID", list);
							List<String> list_rela=new ArrayList<String>();
							list_rela.add((appFactors[i].getFactorType() + "_" + i).trim());
							relaMapCache.put(appFactors[i].getFactorType(), list_rela);
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
									+ "</em><ul id=\""
									+ appFactors[i].getAppFactorCode()
									+ "_"
									+ appFactors[i].getFactorType()
									+ "\">"
									+ getPeriodValuesHTML(
											tFMRisk.getRiskCode(),
											appFactors[i]
													.getFEMRiskFactorList(),appFactors[i], map, tFMRisk.getBackUp4());
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
									+ "</em><ul id=\""
									+ appFactors[i].getAppFactorCode()
									+ "_"
									+ appFactors[i].getFactorType()
									+ "\">"
									+ getRiskAppFactorValuesHTML(
											tFMRisk.getRiskCode(),
											appFactors[i]
													.getFEMRiskFactorList(),
											"00", map,appFactors[i]) + "</ul></div>";
							eleHTML = eleHTML + "<div class=\"clear\"></div>";

						} else if ("Plan".equals(appFactors[i].getFactorType())) {
							// 查询当前产品是否需要特殊处理计划名称显示值
							String sql = "SELECT Memo FROM ZDCode WHERE CodeType = 'SpecialPlanName' AND CodeValue = ?";
							QueryBuilder qb = new QueryBuilder(sql, tFMRisk.getRiskCode());
							DataTable dt = qb.executeDataTable();
							
							map.put("isSplitProduct", isSplitProduct);
							eleHTML = "<div class=\"paln_con clearfix\""
									+ displayStr
									+ " ><em class=\"cp_title_ms\">"
									// 如果需要处理计划名称显示值，将代码项备注作为显示值显示
									+ (dt.getRowCount() > 0 ? dt.getString(0, 0): appFactors[i].getFactorTypeName())
									+ "</em><ul id=\""
									+ appFactors[i].getAppFactorCode()
									+ "_"
									+ appFactors[i].getFactorType()
									+ "\">"
									+ getRiskAppFactorValuesHTML(
											tFMRisk.getRiskCode(),
											appFactors[i]
													.getFEMRiskFactorList(),
											"00", map,appFactors[i]) + "</ul></div>";
									// 计划对比隐藏变量periodFlag设置
							eleHTML = eleHTML +
									"<input id=\"periodFlag\" type=\"hidden\" value=\""+appFactors[i].getBackUp1()+"\" />" +
									"<div class=\"clear\"></div>";

						} else if ("Duty".equals(appFactors[i].getFactorType())) {
							eleHTML = "";
							break;
						} else if ("FeeYear".equals(appFactors[i].getFactorType())) {
							// 当前产品的缴费年期
							String currFeeYearHtml = getRiskAppFactorValuesHTML(tFMRisk.getRiskCode(), appFactors[i].getFEMRiskFactorList(), "00", map,appFactors[i]);
							// 缴费年期拆分产品
							String feeYearSplitProduct = map.get("FeeYearSplitProduct");
							if (StringUtil.isNotEmpty(feeYearSplitProduct)) {
								// 该产品是拆分产品时取得其他产品的缴费年期
								String feeYearHtml = getSplitProductFeeYear(feeYearSplitProduct, tFMRisk.getRiskCode(), currFeeYearHtml, map);
								if (StringUtil.isNotEmpty(feeYearHtml)) {
									currFeeYearHtml = feeYearHtml;
								}
							}
							eleHTML = "<div "
									+ displayStr
									+ " class=\"tb_age clearfix\" ><em class=\"cp_title_ms\">"
									+ appFactors[i].getFactorTypeName()
									+ "</em><ul id=\""
									+ appFactors[i].getAppFactorCode()
									+ "_"
									+ appFactors[i].getFactorType()
									+ "\">"
									+ currFeeYearHtml + "</ul></div><div class=\"clear\"></div>";
						} else if ("Mult".equals(appFactors[i].getFactorType())) {
							String innerEleHTML = "";
							// 如果是年金险
							if ("Y".equals(mProductFactors.get("yearCashFlag"))) {
								innerEleHTML = "<input type=\"text\" maxlength=\"3\" value=\"1\" />";
							} else {
								innerEleHTML = "<input class=\"number\" type=\"text\" onkeyup='this.value=this.value.replace(/\\D/gi,\"\")'  data-max=\""
										+ mFMRisk.getBackUp1() + "\" maxlength=\"9\" value=\"1\">";
							}
							eleHTML = "<div "
									+ displayStr
									+ " class=\"tb_age clearfix\"><em class=\"cp_title_ms\">"
									+ appFactors[i].getFactorTypeName()
									+ "</em><p class=\"add_mult\" onselectstart=\"return false;\">"
									+ "<em class=\"reduce\">-</em>"
									+ innerEleHTML
									+ "<em class=\"add\">+</em></p>"
									+ "<p class=\"tip_mult\">每份<span>"
									+ tFMRisk.getInitPrem()
									+ "元</span>，每个被保人最多投保 "
									+ mFMRisk.getBackUp1()
									+ "份</p>"
									+ "</div>";
							
						} else {
							eleHTML = "<div "
									+ displayStr
									+ " class=\"tb_age clearfix\" ><em class=\"cp_title_ms\">"
									+ appFactors[i].getFactorTypeName()
									+ "</em><ul id=\""
									+ appFactors[i].getAppFactorCode()
									+ "_"
									+ appFactors[i].getFactorType()
									+ "\">"
									+ getRiskAppFactorValuesHTML(
											tFMRisk.getRiskCode(),
											appFactors[i]
													.getFEMRiskFactorList(),
											"00", map,appFactors[i]) + "</ul>";
							// 职业类别后边增加查询职业类别
							if ("Occup".equals(appFactors[i].getFactorType())) {
								eleHTML += "<p id='job_search'>" + "<input type=\"hidden\" id=\"companyCode\" value=\""+tFMRisk.getRiskCode().substring(0, 4)+"\">\n" +
										"<input type=\"hidden\" id=\"productId\" value=\""+tFMRisk.getRiskCode()+"\">\n" +
										"<input type=\"hidden\" id=\"OccupLevel\" value=\""+ appFactors[i].getFEMRiskFactorList()[0].getFactorValue() +"\">\n" +
										"<a href=\"#\" class=\"ms_link\"><em class=\"icon\"></em><font><font>查询职业类别</font></font></a></p>";
							}
							eleHTML = eleHTML + "</div><div class=\"clear\"></div>";
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

	private String getDefalutBirth(FEMRiskFactorList[] femRiskFactorLists) {
		if (femRiskFactorLists != null && femRiskFactorLists.length > 0 && femRiskFactorLists[0] != null) {
			String value = null;
			for(FEMRiskFactorList riskFactor : femRiskFactorLists){
				if("Y".equals(riskFactor.getDefaultValue())){
					value = riskFactor.getFactorValue();
					break;
				}
				if(StringUtil.isEmpty(value)){
					value = femRiskFactorLists[0].getFactorValue();
				}
			}
			if (value.contains("-")) {
				return value.split("-")[0];
			} else {
				return value;
			}
		} else {
			return "18Y";
		}
	}
	/**
	 * 获取年龄默认极值
	 * 
	 * @param femRiskFactorLists
	 * @param string
	 * @return
	 */
	private int getDefaultAge(FEMRiskFactorList[] femRiskFactorLists, String tFlag) {
		if (femRiskFactorLists != null && femRiskFactorLists.length > 0 && femRiskFactorLists[0] != null) {
			String value1 = null;
			String value2 = null;
			if("default".equalsIgnoreCase(tFlag)){
				String value = null;
				for(FEMRiskFactorList riskFactor : femRiskFactorLists){
					if("Y".equals(riskFactor.getDefaultValue())){
						value = riskFactor.getFactorValue();
						break;
					}
				}
				if(StringUtil.isEmpty(value)){
					value1 = femRiskFactorLists[0].getFactorValue();
					value2 = femRiskFactorLists[femRiskFactorLists.length - 1].getFactorValue();
				}else{
					value1 = value2 = value;
				}
			}else{
				value1 = femRiskFactorLists[0].getFactorValue();
				value2 = femRiskFactorLists[femRiskFactorLists.length - 1].getFactorValue();
			}
			if (value1 == null || "".equals(value1)) {
				value1 = value2;
			}
			if (value2 == null || "".equals(value2)) {
				value1 = value2 = "18Y";
			}
			// 针对XX天至XX周岁的特殊处理
			if (value1.indexOf("-0Y") != -1) {
				value1 = "0Y-0Y";
			}
			if (value2.indexOf("-0Y") != -1) {
				value2 = "0Y-0Y";
			}
			int iValue1 = Integer.parseInt(value1.substring(0, value1.indexOf("Y")));
			int iValue2 = Integer.parseInt(value2.substring(0, value2.indexOf("Y")));
			if ("min".equalsIgnoreCase(tFlag) || "default".equalsIgnoreCase(tFlag)) {
				return iValue1 < iValue2 ? iValue1 : iValue2;
			}
			return iValue1 > iValue2 ? iValue1 : iValue2;
		} else {
			if ("min".equalsIgnoreCase(tFlag) || "default".equalsIgnoreCase(tFlag)) {
				return 18;
			}
			return 50;
		}
	}
	/**
	 * 获取年龄最小值显示值(当XX天至XX周岁时，显示XX天)
	 * 
	 * @param femRiskFactorLists
	 * @param string
	 * @return
	 */
	private String getMinAgeStr(FEMRiskFactorList[] femRiskFactorLists) {
		if (femRiskFactorLists != null && femRiskFactorLists.length > 0 && femRiskFactorLists[0] != null) {
			String value1 = femRiskFactorLists[0].getFactorValue();
			String value2 = femRiskFactorLists[femRiskFactorLists.length - 1].getFactorValue();
			if (value1 == null || "".equals(value1)) {
				value1 = value2;
			}
			if (value2 == null || "".equals(value2)) {
				value1 = value2 = "18Y";
			}
			// 针对XX天至XX周岁的特殊处理
			if (value1.indexOf("-0Y") != -1 && value1.indexOf("D") != -1) {
				value1 = value1.substring(0, value1.indexOf("D"));
				return value1 + "天";
			}
			if (value2.indexOf("-0Y") != -1) {
				return value1 + "天";
			}
			int iValue1 = Integer.parseInt(value1.substring(0, value1.indexOf("Y")));
			int iValue2 = Integer.parseInt(value2.substring(0, value2.indexOf("Y")));
			return (iValue1 < iValue2 ? iValue1 : iValue2) + "";
		} else {
			return "18";
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
				Map<String, String> map = new HashMap<String, String>();
				for (int i = 0; i < appFactors.length; i++) {

					if ("Y".equalsIgnoreCase(appFactors[i].getIsDisplay())) {
						displayStr = "";
					} else {
						displayStr = "style=\"display:none;\"";
					}
					if ("Occup".equals(appFactors[i].getFactorType())) {
						displayStr = "style=\"display:none;\"";
					}
					if ("Duty".equals(appFactors[i].getFactorType())){
						continue;
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
											"00", map,appFactors[i]) + "</ul></div>";
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
											"08", map,appFactors[i]) + "</select></div>";
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
			FEMRiskFactorList[] femRiskFactorList ,FERiskAppFactor femRiskFactor, Map<String, String> map, String ComplicatedFlag) {
		String tHTML = "";
		if (femRiskFactorList != null && femRiskFactorList.length > 0
				&& femRiskFactorList[0] != null) {
			int len = femRiskFactorList.length;
			String liClass = "";
			boolean selected = false;
			
			for (int i = 0; i < len; i++) {
				// 显示项字数多余5位，尺寸变大
				if ("".equals(liClass) && StringUtil.isNotEmpty(femRiskFactorList[i].getFactorDisplayValue())) {
					try {
						int lenght = femRiskFactorList[i].getFactorDisplayValue().getBytes("GBK").length;
						if (lenght > 10) {
							liClass = "long_width3";
						}
					} catch (UnsupportedEncodingException e) {
						logger.error(e.getMessage(), e);
					}
				}
				
				if (i == 0) {
					// 默认选中
					if ("Y".equals(femRiskFactorList[i].getDefaultValue())) {
						selected = true;
						tHTML += "<li class=\"li_selected\"";
					} else {
						tHTML += "<li";
					}
					tHTML += " onclick=\"Dayclear()\"><span id=\""
							+ femRiskFactorList[i].getFactorType() + "_" + i
							+ "\" class=\"long_width\" name=\""
							+ femRiskFactorList[i].getFactorValue()
							+ "\" onclick=\"premRecal('" + riskcode
							+ "', this, '"
							+ femRiskFactorList[i].getAppFactorCode() + "_"
							+ femRiskFactorList[i].getFactorType() + "');\">"
							+ femRiskFactorList[i].getFactorDisplayValue()
							+ "</span></li>";
				} else {
					if("N".equalsIgnoreCase(femRiskFactor.getBackUp2())){
						// 默认选中
						if ("Y".equals(femRiskFactorList[i].getDefaultValue())) {
							selected = true;
							tHTML += "<li class=\"li_selected\"";
						} else {
							tHTML += "<li";
						}
							tHTML += " onclick=\"Dayclear()\"><span id=\""
									+ femRiskFactorList[i].getFactorType() + "_" + i
									+ "\" class=\"long_width\" name=\""
									+ femRiskFactorList[i].getFactorValue()
									+ "\" onclick=\"premRecal('" + riskcode
									+ "', this, '"
									+ femRiskFactorList[i].getAppFactorCode() + "_"
									+ femRiskFactorList[i].getFactorType() + "');\">"
									+ femRiskFactorList[i].getFactorDisplayValue()
									+ "</span></li>";
					}else{
						if (len > 10 && (i > 7 && i < len - 2)) {
							if ("Y".equals(ComplicatedFlag)) {
								tHTML += "<li onclick=\"Dayclear()\" style=\"display:none\"><span id=\""
										+ femRiskFactorList[i].getFactorType() + "_" + i
										+ "\" class=\"long_width\" name=\""
										+ femRiskFactorList[i].getFactorValue()
										+ "\" onclick=\"premRecal('" + riskcode
										+ "', this, '"
										+ femRiskFactorList[i].getAppFactorCode() + "_"
										+ femRiskFactorList[i].getFactorType() + "');\">"
										+ femRiskFactorList[i].getFactorDisplayValue()
										+ "</span></li>";
//								map.put(femRiskFactorList[i].getFactorValue(), femRiskFactorList[i].getFactorType() + "_" + i);
								map.put(femRiskFactorList[i].getFactorType() + "_" + i, femRiskFactorList[i].getFactorValue());
							}
							continue;
						}
						// 默认选中
						if ("Y".equals(femRiskFactorList[i].getDefaultValue())) {
							selected = true;
							tHTML += "<li class=\"li_selected\"";
						} else {
							tHTML += "<li";
						}
						tHTML += " onclick=\"Dayclear()\"><span id=\""
								+ femRiskFactorList[i].getFactorType() + "_" + i
								+ "\" class=\"long_width\" name=\""
								+ femRiskFactorList[i].getFactorValue()
								+ "\" onclick=\"premRecal('" + riskcode
								+ "', this, '"
								+ femRiskFactorList[i].getAppFactorCode() + "_"
								+ femRiskFactorList[i].getFactorType() + "');\">"
								+ femRiskFactorList[i].getFactorDisplayValue()
								+ "</span></li>";
					}
				}
//				map.put(femRiskFactorList[i].getFactorValue(), femRiskFactorList[i].getFactorType() + "_" + i);
				map.put(femRiskFactorList[i].getFactorType() + "_" + i, femRiskFactorList[i].getFactorValue());
				//组合投保要素勾稽关系所需数据
				List<String> list=new ArrayList<String>();
				list.add((femRiskFactor.getAppFactorCode()+ "_"+ femRiskFactor.getFactorType()).trim());
				list.add((femRiskFactorList[i].getFactorType() + "_" + i).trim());
				relaMapCache.put(femRiskFactorList[i].getFactorValue(), list);
				List<String> list_rela=new ArrayList<String>();
				list_rela.add((femRiskFactorList[i].getFactorType() + "_" + i).trim());
				relaMapCache.put(femRiskFactorList[i].getFactorType(), list_rela);
				
			}
			
			// 若未设置默认选中值 则默认显示第一个
			if (!selected) {
				tHTML = "<li class=\"li_selected\"" + tHTML.substring(3);
			}
			if("N".equalsIgnoreCase(femRiskFactor.getBackUp2())){
				tHTML += "</ul></div>";
			}else{
				if (len > 10) {
					tHTML += "<li class=\"\"><span  id=\"LiDayItemAuto\" onClick=\"DaySelectAuto('DayItem','Auto')\" class=\"help_selec_ss\" >自主选择</span></li></ul></div>";

					tHTML = tHTML
							+ "<div class=\"clear\"></div>"
							+ "<div id=\"help_select_day\" class=\"clearfix\" style=\"display:none;\">"
							+ "<div class=\"seleday\">出发日期：</div>"
							+ "<div class=\"help_day\"><input id=\"txtStartDay\" type=\"text\" class=\"input_selectday\"  name=\"txtStartDay\" data-min=\"1\" /></div>"
							+ "<div class=\"seleday\">结束日期：</div>"
							+ "<div class=\"help_day\"><input name=\"txtEndDay\" type=\"text\" id=\"txtEndDay\" class=\"input_selectday\" data-min=\"1\" /></div>"
							+ "<div class=\"help_day clearfix\"><input name=\"\" type=\"button\" class=\"input_okday input_btn\" value=\"确定\"  onclick=\"selfDefineDay('txtStartDay','txtEndDay','"
							+ riskcode
							+ "')\"/></div>"
							+ "</div><div class=\"clear\"></div><div class=\"tishi_day \" id=\"DayE\" style=\"color: Red;display: none;\"></div>";
				} else {
					tHTML += "</ul></div>";
				}
			}
			if (!"".equals(liClass)) {
				tHTML = tHTML.replace("long_width", liClass);
			}
		}
		return tHTML;
	}

	private static String getSplitProductFeeYear(String feeYearSplitProduct, String riskcode, String currFeeYearHtml, Map<String, String> map) {
		String result = "";
		
		StringBuffer tHTML = new StringBuffer("");
		// 缴费年期关联产品
		String[] relap = feeYearSplitProduct.split(",");
		int rlen = relap.length;
		if (rlen>=1){
			QueryBuilder qb;
			DataTable dt;
			int rowcount=0;
			String factorDisplayValue = "";
			String liClass = "";
			if (StringUtil.isNotEmpty(currFeeYearHtml) && currFeeYearHtml.indexOf("long_width3") >= 0) {
				liClass = "long_width3";
			}
			String relaFeeYear = "";
			// 循环处理关联产品
			for (int i=0;i<rlen;i++){
				// 当前产品的情况 
				if (riskcode.equals(relap[i])) {
					tHTML.append(currFeeYearHtml.replace("long_width3", "long_width"));
				} else {
					// 取得产品缴费年期
					qb = new QueryBuilder("SELECT b.FactorDisplayValue FROM FERiskAppFactor a LEFT JOIN FEMRiskFactorList b ON a.AppFactorCode = b.AppFactorCode WHERE a.RiskCode = ? AND a.FactorType = 'FeeYear' order by FactorValueOrder asc ", relap[i]);
					dt = qb.executeDataTable();
					if (dt != null && dt.getRowCount() > 0) {
						rowcount = dt.getRowCount();
						for (int j = 0; j < rowcount; j++) {
							factorDisplayValue = dt.getString(j, 0);
							if (StringUtil.isNotEmpty(factorDisplayValue)) {
								// 产品详细页地址在详细页发布时替换
								tHTML.append("<li><span class='long_width' id='FeeYear_"+ relap[i] +"_"+ j +"'><a href=\"ProHref_" + relap[i] + "_ProductUrl\">" + factorDisplayValue + "</a></span></li>");;
								relaFeeYear += (",FeeYear_"+ relap[i] +"_"+ j);
								if (StringUtil.isNotEmpty(liClass)) {
									try {
									 	if (factorDisplayValue.getBytes("GBK").length > 10) {
											liClass = "long_width3";
										}
									} catch (UnsupportedEncodingException e) {
									 	logger.error(e.getMessage(), e);
									}
								}
							}
						}
					}
				}
			}
			if (StringUtil.isNotEmpty(relaFeeYear)) {
				map.put("splitFeeYear", relaFeeYear.substring(1));
			}
			
			if (StringUtil.isNotEmpty(liClass)) {
				result = tHTML.toString().replace("long_width", liClass);
			} else {
				result = tHTML.toString();
			}
			
		}
		 
		return result;
	}
	
	/**
	 * 
	 * @param femRiskFactorList
	 * @param listType
	 *            04-单选 05-多选 08-下拉选择
	 * @return
	 */
	private static String getRiskAppFactorValuesHTML(String riskcode,
			FEMRiskFactorList[] femRiskFactorList, String listType, Map<String, String> map,FERiskAppFactor femRiskFactor) {
		String tHTML = "";
		if (femRiskFactorList != null && femRiskFactorList.length > 0
				&& femRiskFactorList[0] != null) {
			
			int index = 0;
			
			if(StringUtil.isNotEmpty(map.get("isSplitProduct"))) {
				String[] spli = map.get("isSplitProduct").split(",");
				if (spli != null && spli.length > 0) {
					for (int i = 0; i < spli.length; i++) {
						if (riskcode.equals(spli[i].split("-")[0])) {
							index = i;
							break;
						}
					}
				}
			}
			// if ("04".equals(listType) || "05".equals(listType)) {
			if (!"08".equals(listType)) {
				String liClass = "";
				boolean selected = false;
				boolean split = false;
				for (int i = 0; i < femRiskFactorList.length; i++) {
					// 显示项字数多余5位，尺寸变大
					if ("".equals(liClass) && StringUtil.isNotEmpty(femRiskFactorList[i].getFactorDisplayValue())) {
						try {
							int len = femRiskFactorList[i].getFactorDisplayValue().getBytes("GBK").length;
							if (len > 10) {
								liClass = "long_width3";
							}
						} catch (UnsupportedEncodingException e) {
							logger.error(e.getMessage(), e);
						}
					}
					String isSplitProduct = map.get("isSplitProduct");
					if(StringUtil.isNotEmpty(isSplitProduct) && "PLAN".equals(femRiskFactorList[i].getFactorType().toUpperCase())){
						split = true;
						if(tHTML.indexOf("sinosofttfosonis")==-1){
							tHTML +="sinosofttfosonis";
						}
//						map.put(femRiskFactorList[i].getFactorValue(), femRiskFactorList[i].getFactorType() + "_" + (index + i));
						map.put(femRiskFactorList[i].getFactorType() + "_" + (index + i), femRiskFactorList[i].getFactorValue());
						
					}else{
						// 默认选中
						if ("Y".equals(femRiskFactorList[i].getDefaultValue())) {
							tHTML += "<li class=\"li_selected\">";
							selected = true;
						} else {
							tHTML += "<li>";
						}
						
						tHTML += "<span id=\""
								+ femRiskFactorList[i].getFactorType() + "_"
								+ i + "\" class=\"long_width\" name=\""
								+ femRiskFactorList[i].getFactorValue()
								+ "\" onclick=\"premRecal('" + riskcode
								+ "', this, '"
								+ femRiskFactorList[i].getAppFactorCode() + "_"
								+ femRiskFactorList[i].getFactorType()
								+ "');\">"
								+ femRiskFactorList[i].getFactorDisplayValue()
								+ "</span></li>";

//						map.put(femRiskFactorList[i].getFactorValue(), femRiskFactorList[i].getFactorType() + "_" + i);
						map.put(femRiskFactorList[i].getFactorType() + "_" + i, femRiskFactorList[i].getFactorValue());
						
						//组合投保要素勾稽关系所需数据
						List<String> list=new ArrayList<String>();
						list.add((femRiskFactor.getAppFactorCode()+ "_"+ femRiskFactor.getFactorType()).trim());
						list.add((femRiskFactorList[i].getFactorType() + "_" + i).trim());
						relaMapCache.put(femRiskFactorList[i].getFactorValue(), list);
						List<String> list_rela=new ArrayList<String>();
						list_rela.add((femRiskFactorList[i].getFactorType() + "_" + i).trim());
						relaMapCache.put(femRiskFactorList[i].getFactorType(), list_rela);
					}
					
				}
				// 若没有默认选中值，则默认选中第一个
				if (!selected && !split) {
					tHTML = "<li class=\"li_selected\">" + tHTML.substring(4);
				}
				if (!"".equals(liClass) && !split) {
					tHTML = tHTML.replace("long_width", liClass);
				}
				// } else if ("05".equals(listType)) {
				// for (int i = 0; i < femRiskFactorList.length; i++) {
				// tHTML += "<input id=\""
				// + femRiskFactorList[i].getFactorType() + "_" + i
				// + "\" name=\""
				// + femRiskFactorList[i].getFactorType()
				// + "\" type=\"checkbox\" />";
				// }
				// } else if ("08".equals(listType)) {
			} else {
				boolean selected = false;
				for (int i = 0; i < femRiskFactorList.length; i++) {
					// 默认选中
					if ("Y".equals(femRiskFactorList[i].getDefaultValue())) {
						tHTML += "<option selected value=\"";
						selected = true;
					} else {
						tHTML += "<option value=\"";
					}
					
					tHTML += femRiskFactorList[i].getFactorValue() + "\">"
							+ femRiskFactorList[i].getFactorDisplayValue()
							+ "</option>";
					if ("207101026".equals(riskcode)) {
						break;
					}
				}
				// 若没有默认选中值，则默认选中第一个
				if (!selected) {
					tHTML = "<option selected " + tHTML.substring(8);
				}
			}
		}
		return tHTML;
	}

	// public static String getDutyHTML(FMRisk tFMRisk) {
	// FMDuty[] fMDuty = tFMRisk.getFMDuty();
	// Map<String, String> map = new HashMap<String, String>();
	// String tHTML = "<div id=\"divRiskAppDuty_" + tFMRisk.getRiskCode() +
	// "\" class=\"CRiskAppDuty\" >";
	// if (fMDuty != null && fMDuty.length > 0 && fMDuty[0] != null) {
	// try {
	// FEMDutyFactor dutyFactor = null;
	// String defaultValue = "";
	// tHTML += "<div class=\"title\">" +
	// "<span class=\"CDutyCol_1\">责任项目</span>" +
	// "<span class=\"CDutyCol_2\">保额</span>" +
	// "<span class=\"CDutyCol_3\">责任描述</span></div>";
	// FEMRiskFactorList[]
	// mPlanList=getPlanList(tFMRisk.getFERiskAppFactor());//得到该产品的计划列表 add by
	// fhz 2013-1-6
	// int x = 0;
	// int y = 0;
	// for (FMDuty duty : fMDuty) {
	// if ("Y".equals(duty.getIsDisplay())) {
	// x++;
	// y++;
	// x = x % 2;
	// if (y >= 5) {
	// y = 5;
	// }
	// dutyFactor = duty.getFEMDutyFactor();
	// FEMDutyAmntPremList[] AmntPremValues =
	// dutyFactor.getFEMDutyAmntPremList();
	// if (AmntPremValues != null && AmntPremValues.length > 0 &&
	// AmntPremValues[0] != null) {
	// defaultValue = AmntPremValues[0].getAmnt();
	// } else {
	// defaultValue = "-";
	// }
	// tHTML += "<div class=\"list_" + y + " row" + x + "\" id=\"Duty_" +
	// dutyFactor.getDutyFactorID() + "\" >";
	// tHTML += "<span class=\"CDutyCol1\">" + duty.getDutyName() + "</span>";
	//
	// if ("Y".equals(dutyFactor.getIsRelaRiskFactor())) {
	// //getDutyFactorValuesHTML 加了一入参 mPlanList 产品的计划列表 add by fhz 2013-1-6
	// tHTML += getDutyFactorValuesHTML(AmntPremValues, defaultValue, 999,
	// tFMRisk.getFERiskAppFactor(), map,mPlanList);
	// } else if ("Y".equals(dutyFactor.getIsPremCalFacotor())) {
	// int iType = 1;
	// if (dutyFactor.getDataType() != null &&
	// "".equals(dutyFactor.getDataType())) {
	// iType = Integer.valueOf(dutyFactor.getDataType());
	// }
	// tHTML += getDutyFactorValuesHTML(AmntPremValues, defaultValue, iType,
	// null, map,mPlanList);
	// } else {
	// tHTML += "<span class=\"CDutyCol2\">" + defaultValue + "</span>";
	// }
	// tHTML += "<span class=\"CDutyCol3\">" + duty.getDefine() + "</span>";
	// tHTML += "</div>";
	// }
	// }
	//
	// } catch (Exception e) {
	// LogUtil.info("");
	// tHTML = "<div class=\"CRiskAppDuty\" >";
	// }
	// }
	// tHTML += "</div>";
	// return tHTML;
	// }

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
					String dutyHtml = "";
					
					// wangwenying update 20150612
					String dutyHtmlForWap = "";
					
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
						dutyHtml += "<div class=\"list_" + y + " row" + x
								+ "\" id=\"Duty_"
								+ dutyFactor.getDutyFactorID() + "\" >";
						dutyHtml += "<span class=\"CDutyCol1\">"
								+ duty.getDutyName() + "</span>";
						// wangwenying update 20150612
						dutyHtmlForWap += "<li><span class=\"intro_l\">" + duty.getDutyName() + "</span>";
						dutyHtmlForWap += "<span class=\"prix_r\">" + defaultValue + "</span></li>";

						if ("Y".equals(dutyFactor.getIsRelaRiskFactor())) {
							// getDutyFactorValuesHTML 加了一入参 mPlanList 产品的计划列表
							// add by fhz 2013-1-6
							dutyHtml += getDutyFactorValuesHTML(AmntPremValues,
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
							dutyHtml += getDutyFactorValuesHTML(AmntPremValues,
									defaultValue, iType, null, map, mPlanList);
						} else {
							dutyHtml += "<span class=\"CDutyCol2\">"
									+ defaultValue + "</span>";
						}
						dutyHtml += "<span class=\"CDutyCol3\">"
								+ duty.getDefine() + "</span>";
						dutyHtml += "</div>";
						ProductListYX.saveProductDutyHtml(tFMRisk.getRiskCode(),duty.getDutyCode(),dutyHtml,dutyHtmlForWap);
						tHTML += dutyHtml;
					}
				}

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				tHTML = "<div class=\"CRiskAppDuty\" >";
			}
		}
		tHTML += "</div>";
		ProductListYX.saveMarketProduct(tFMRisk.getRiskCode());
		return tHTML;
	}

	/**
	 * 获取保障权益
	 *
	 * @param tFMRisk
	 * @param listDuty
	 * @param mapDuty
	 * @return
	 */
	public String getDutyHTML1(FMRisk tFMRisk, List<String> listDuty, Map<String, String> mapDuty) {
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
		String tHTML = "<div id=\"divRiskAppDuty_"
				+ tFMRisk.getRiskCode()
				+ "\" class=\"bzzz_boxtable\" > <table width=\"100%\" border=\"0\" id=\"gh_tables\">";
		String hiddenHtml = "";
		if (fMDuty != null && fMDuty.length > 0 && fMDuty[0] != null) {
			try {
				FEMDutyFactor dutyFactor = null;
				String defaultValue = "";

				tHTML += " <colgroup><col width=\"30%\"></col><col width=\"15%\"></col><col width=\"55%\"></col></colgroup>";

				FEMRiskFactorList[] mPlanList = getPlanList(tFMRisk.getFERiskAppFactor());
				int x = 0;
				int y = 0;
				String dutyTypeP = "";
				int count = 0;
				String trHtml = "";
				for (FMDuty duty : fMDuty) {
					dutyFactor = duty.getFEMDutyFactor();
					FEMDutyAmntPremList[] AmntPremValues = dutyFactor
							.getFEMDutyAmntPremList();

					if ("Y".equals(duty.getIsDisplay())) {
						x++;
						y++;
						x = x % 2;
						if (y >= 5) {
							y = 5;
						}

						if (AmntPremValues != null && AmntPremValues.length > 0
								&& AmntPremValues[0] != null) {
							defaultValue = AmntPremValues[0].getAmnt();
						} else {
							defaultValue = "-";
						}
						if (AmntPremValues != null && AmntPremValues.length > 0 && AmntPremValues[0] != null) {
							for (int i = 0; i < AmntPremValues.length; i++) {
								mapDuty.put(duty.getDutyCode() + "-"
												+ AmntPremValues[i].getBackUp1(),
										AmntPremValues[i].getAmnt());
							}
						}


						listDuty.add(duty.getDutyCode());
						trHtml = "";
						if (0 == count
								|| !dutyTypeP.equals(duty.getDutyTypeP())) {
							trHtml += "<tr><td colspan=\"3\" class=\"td_ttl\"><b>"
									+ duty.getDutyNameP() + "</b></td></tr>";
							dutyTypeP = duty.getDutyTypeP();
						}
						count++;

						trHtml += "<tr ><div class=\"list_" + y + " row" + x
								+ "\" id=\"Duty_"
								+ dutyFactor.getDutyFactorID() + "\" >";
						trHtml += "<td class=\"CDutyCol1\"> <span>"
								+ duty.getDutyName() + "</span></td>";

						// 是否复杂产品
						if ("Y".equalsIgnoreCase(tFMRisk.getBackUp4())) {
							if (AmntPremValues == null || AmntPremValues.length <= 0) {
								trHtml += "<td class=\"CDutyCol2 ruleSelectUp \">-</td>";
							} else if (AmntPremValues.length == 1 && AmntPremValues[0] != null) {
								trHtml += "<td class=\"CDutyCol2  ruleSelectUp "
										+ "\" data-title=\""
										+ duty.getDutyFullName()
										+ "\" data-tip=\""
										+ duty.getDutyDesc()
										+"\"><span class=\"CDutyCol2\" id=\""
										+ duty.getDutyCode()
										+ "_"
										+ duty.getCurrency()
										+ "\" name=\""
										+ AmntPremValues[0].getBackUp1()
										+ " \">"
										+ AmntPremValues[0].getAmnt()
										+ "</span></td>";
							} else {
								if ("01".equals(duty.getCurrency()) && "不投保".equals(AmntPremValues[0].getAmnt())) {
									defaultValue = AmntPremValues[1].getAmnt();
								}
								trHtml += "<td class=\"CDutyCol2 ruleSelectUp "
										+ "\" data-title=\""
										+ duty.getDutyFullName()
										+ "\" data-tip=\""
										+ duty.getDutyDesc()
										+"\"><select class=\"CDutyCol2\" id=\""
										+ duty.getDutyCode()
										+ "_"
										+ duty.getCurrency()
										+ "\" name=\""
										+ duty.getDutyCode()
										+ "_"
										+ duty.getCurrency()
										+ "\" value=\""
										+ defaultValue
										+ "\">";
								for (int j = 0; j < AmntPremValues.length; j++) {
									if (AmntPremValues[0] != null) {
										trHtml += "<option name=\"" + AmntPremValues[j].getBackUp1() + "\">"
												+ AmntPremValues[j].getAmnt()
												+ "</option>";
									}
								}
								trHtml += "</select></td>";
							}

							// 非责任上调的不需要加ruleSelectUp
							if (!"Y".equals(duty.getDutyLibCode()) && trHtml.contains("ruleSelectUp")) {
								tHTML += trHtml.replaceAll("ruleSelectUp", "");
							} else {
								tHTML += trHtml;
							}

						} else {
							tHTML += trHtml;
							if ("Y".equals(dutyFactor.getIsRelaRiskFactor())) {
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
								tHTML += "<td class=\"CDutyCol2 \">" + defaultValue
										+ "</td>";
							}
						}
						tHTML += "<td class=\"CDutyCol3\"><span>"
								+ duty.getDefine() + "</span></td>";
						tHTML += "</div></tr>";

					} else {
						hiddenHtml += (duty.getDutyCode() + "-");
						if (AmntPremValues != null && AmntPremValues.length > 0) {
							hiddenHtml += AmntPremValues[0].getBackUp1();
						} else {
							hiddenHtml += "0";
						}
						hiddenHtml += ",";
					}
				}
				if (StringUtil.isNotEmpty(hiddenHtml)) {
					hiddenHtml = "<input type=\"hidden\" id=\"hidDutyInfo\" value=\""
							+ hiddenHtml.substring(0, hiddenHtml.length() - 1)
							+ "\"/>";
				}

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				tHTML = "</table><div class=\"CRiskAppDuty\" >";
			}
		}
		tHTML += "</table>" + hiddenHtml + "</div>";
		return tHTML;
	}
	/**
	 * 得到类淘宝列表页--责任条款
	 * @param tFMRisk
	 * @return
	 */
	public static String getDutyHTML2(FMRisk tFMRisk) {
		FMDuty[] fMDuty = tFMRisk.getFMDuty();
		String tHTML = "";
		if (fMDuty != null && fMDuty.length > 0 && fMDuty[0] != null) {
			try {
				FEMDutyFactor dutyFactor = null;
				String defaultValue = "";
				for (FMDuty duty : fMDuty) {
					if ("Y".equals(duty.getIsDisplay())) {
						
						dutyFactor = duty.getFEMDutyFactor();
						FEMDutyAmntPremList[] AmntPremValues = dutyFactor
								.getFEMDutyAmntPremList();
						if (AmntPremValues != null && AmntPremValues.length > 0
								&& AmntPremValues[0] != null) {
							defaultValue = AmntPremValues[0].getAmnt();
						} else {
							defaultValue = "-";
						}
						//重新组装责任信息--新列表页需求只展示责任
						tHTML += " <li class=\"rec_list_li\">"
								+ "<span class=\"rec_list_s\">"+duty.getDutyName()+"</span>"
								+ " <span class=\"rec_list_money\">"
								+ defaultValue
								+ " </span></li> ";
						 
					}
				}

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				tHTML = "";
			}
		}
		tHTML += "";
		return tHTML;
	}
	/**
	 * 类淘宝列表页-产品活动信息
	 * @return
	 */
	public static String getProductActive(FMRisk tFMRisk){
		String tReturn="";
		/*FilterAction tFilterAction = new FilterAction();
		tReturn = tFilterAction.searchProductListAvtivity(tFMRisk.getRiskCode());
		if(StringUtil.isNotEmpty(tReturn)){
			return tReturn;
		}*/
		//产品折扣
		String discountRate = tFMRisk.getBackUp2();
		
		if(discountRate!=null && !"".equals(discountRate)){
			// 折扣改为不显示
			//tReturn = "<span class=\"shop_activity activity1\" >"+Double.parseDouble(discountRate)*10+"折</span>";
			//tReturn = "<span class=\"shop_activity activity1\" title=\"满100减40\">"+Double.parseDouble(discountRate)*10+"折</span>";
		}
		return tReturn;
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
	private boolean syncOneArticlegAttribute(String tCatalogID, boolean innerFlag) {
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
		
		// 存储产品编码 
		request.put("Prop4", mProductFactors.get("RiskCode"));
		
		// 处理产品图片
		// request.put("Logo", dealImage(request.getString("Logo"),
		// String.valueOf(catalog.getSiteID())));
		// 电子商务险种类别
		request.put(ColumnUtil.PREFIX + "ERiskType",
				getESubRiskType(catalog.getProductType()));
		if (!setArticlegAttribute(request, tCatalogID)) {
			return false;
		}
		if (innerFlag) {
			request.put(ColumnUtil.PREFIX + "CalHTML",
					String.valueOf(mProductFactors.get("CalHTML")).replace("job_search", "job_search1"));
		}
		
		Article article = new Article();
		article.setRequest(request);

		// 设置文章是否是现金价值页
		if (innerFlag && cashValueFlag) {
			article.cashValueFlag = true;
		} else if (innerFlag && showBenefitFlag) {
		    article.showBenefitFlag = true;
        } else {
            article.cashValueFlag = false;
            article.showBenefitFlag = false;
        }

		if (!article.save()) {
			return false;
		}

		mArticleIDs.add(request.getString("ArticleID"));

		ZCArticleSchema articleSchema = new ZCArticleSchema();
		articleSchema.setID(request.getString("ArticleID"));
		articleSchema.fill();
		DataTable dt = new QueryBuilder("select type from zdconfig where value = (select parentid from zccatalog where id='"+tCatalogID+"')").executeDataTable();
		String catalogType = "";
		if(dt.getRowCount()>0){
			catalogType = dt.getString(0, 0);
		}
		if(!"CatalogBXSCID".equals(catalogType)){

			// 查询 主站path和积分商城的path
			String selectSQL = "select HtmlPath, JFHtmlPath from sdproduct where productid = ? ";
			QueryBuilder selectProduct = new QueryBuilder(selectSQL, mProductFactors.get("RiskCode"));
			DataTable pDt = selectProduct.executeDataTable();
			String htmlPath = "";
			String jFHtmlPath = "";

			if (pDt.getRowCount() > 0) {
				htmlPath = pDt.getString(0, "HtmlPath");
				jFHtmlPath = pDt.getString(0, "JFHtmlPath");
			}
			// 现金价值不用记录产品信息
			if (innerFlag) {
				return true;
			}
			// 非现价值，上架产品需要记录产品信息
			Transaction trans = new Transaction();
			String delSQL = "delete from sdproduct where productid = ?";
			String insertSQL = "INSERT INTO sdproduct VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
			trans.add(new QueryBuilder(delSQL, mProductFactors.get("RiskCode")));
			QueryBuilder qbProduct = new QueryBuilder(insertSQL);
			qbProduct.add(mFMRisk.getRiskCode());
			qbProduct.add(mFMRisk.getRiskName());
			try {
				GetDBdata db = new GetDBdata();
				String codename = db
						.getOneValue("select codename from ZDCode where codetype ='SupplierCode'  and parentcode='SupplierCode' and codevalue='"
								+ mFMRisk.getSupplierCode().substring(0, 4) + "'");
				qbProduct.add(codename);
				if (StringUtil.isNotEmpty(catalog.getProductType())) {
					qbProduct
					.add(db.getOneValue("select codename from ZDCode where   parentcode='ContentSign' and codevalue='"
							+ catalog.getProductType().substring(0, 1) + "'"));
					qbProduct.add(catalog.getProductType().substring(0, 1)); // 分类
				} else {
					qbProduct.add("");
					qbProduct.add("");
				}
				
				qbProduct.add(mFMRisk.getFeeRate()); // 手续费率
				qbProduct.add("Y");
				
				// 判断积分商城 不是积分商城插入HtmlPath
				if (!tCatalogID.equals(Config.getValue("PointProductCatalogID"))) {
					String FrontServerContextPath = db
							.getOneValue("select value from zdconfig where type='FrontServerContextPath'");
					qbProduct
					.add(FrontServerContextPath + "/" + articleSchema.getURL());
				} else {
					qbProduct.add(htmlPath);
				}
				
				// System.out.println("产品链接地址：" + FrontServerContextPath + "/" +
				// articleSchema.getURL());
				qbProduct.add(tCatalogID);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			
			qbProduct.add(mFMRisk.getBackUp1());// 限购份数
			String Occup = "";
			String SectionAge = "";
			String tPeriodInsurance = "";
			String tAgeInsurance = "";
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
							tAgeInsurance = getPeriodInsurance(femrList);
						}                              
						if (feaf.getFactorType().equalsIgnoreCase("Period")) {
							FEMRiskFactorList[] femrList = feaf
									.getFEMRiskFactorList();
							tPeriodInsurance = getPeriodInsurance(femrList);
						}
					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
			
			qbProduct.add(Occup);// 职业
			qbProduct.add(SectionAge);// 年龄
			qbProduct.add(tPeriodInsurance);//保险区间，用于首页快速导航
			qbProduct.add(tAgeInsurance);//年龄区间，用于首页快速导航
			qbProduct.add(mFMRisk.getDiscountPrice());
			qbProduct.add(mFMRisk.getAutoRelatedClause());//产品直付标记 cps
			// 增加保存保险公司编码
			qbProduct.add(mFMRisk.getSupplierCode().substring(0, 4));
			// 复杂产品标识
			qbProduct.add(mFMRisk.getBackUp4());
			
			//关联产品
			qbProduct.add(mFMRisk.getBackUp5());

			// 判断积分商城 不是积分商城插入HtmlPath
			if (tCatalogID.equals(Config.getValue("PointProductCatalogID"))) {
				qbProduct.add(Config.getValue("FrontServerContextPath") + "/" + articleSchema.getURL());
			} else {
				qbProduct.add(jFHtmlPath);
			}

			// 表sdprodouct中存入产品是否更新标识，更新时间
			qbProduct.add(mFMRisk.getBackUp9());   // 更新标识
			qbProduct.add("".equals(mFMRisk.getBackUp10()) ? null : mFMRisk.getBackUp10());  // 更新时间
			trans.add(qbProduct);
			
			trans.commit();
		}
		return true;
	}

	// /**
	// * 根据传回的险种类别子编码，得到复制引用的目录
	// * @author fhz
	// * **/
	// private String getDestCatalog(String subRiskType) {
	// String desCatalog="";
	// GetDBdata db = new GetDBdata();
	// String
	// querySql="select id from zccatalog where producttype='"+subRiskType+"'";
	// try {
	// desCatalog=db.getOneValue(querySql);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return desCatalog;
	// }

	@SuppressWarnings("unchecked")
	private boolean setArticlegAttribute(RequestImpl request, String tCatalogID) {
		request.put("Type", "1");
		request.put("Title", mFMRisk.getRiskName());
		request.put("ShortTitle", mFMRisk.getRiskShortName());
		request.put("SubTitle", null);
		request.put("Content", mFMRisk.getERiskDesc());
		request.put("Keyword", mFMRisk.getKeyWord());
		request.put("TopFlag", getRiskTopFlag(mFMRisk.getIsTop()));
		request.put("TemplateFlag", "0");
		request.put("Template", "");
		// 积分IntegralAction
		// request.put("Prop2", getIntegral(mFMRisk));
		request.put(
				"Logo",
				(mFMRisk.getERiskPicRelaPath().startsWith("/")) ? mFMRisk
						.getERiskPicRelaPath() : "/"
						+ mFMRisk.getERiskPicRelaPath());
		// request.put("ReferURL", null);
		// request.put("ReferName", null);
		// request.put("RelativeArticle", null);
		// request.put("RecommendArticle", null);
		// request.put("CommentFlag", null);
		// request.put("Priority", null);
		String id = request.get("ID") + "";
		if (StringUtil.isEmpty(id)) {
			request.put("Attribute", "");
		} else {
			ZCArticleSchema tZCArticleSchema = new ZCArticleSchema();
			tZCArticleSchema.setID(id);
			tZCArticleSchema.fill();
			request.put("Attribute", tZCArticleSchema.getAttribute());
		}
		// request.put("ContentSign",
		// mFMRisk.getFEMRiskType()[0].getERiskType());
		request.put("ContentSign", "");
		request.put("SourceSign", "");
		// request.put("Keyword", null);
		// request.put("MetaKeywords", null);
		// request.put("MetaDescription", null);
		// request.put("MetaTitle", null);
		request.put("ClusterTarget", "");
		// fhz 复制引用的地址
		request.put("ReferTarget", getDestCatalogs(tCatalogID));// 复制目的地
		request.put("ReferType", 2);// 引用复制
		// 内容标记
		request.put("RiskType", mFMRisk.getBelongFlag());
		request.put("CompanyID", "");
		// request.put("Author", null);
		// request.put("Summary", null);
		// request.put("PageTitles", null);
		// request.put("LocalImageFlag", null);
		// request.put("PublishDate", null);
		// request.put("PublishTime", null);
		// request.put("DownlineDate", null);
		// request.put("DownlineTime", null);
		// request.put("ArchiveDate", null);
		// request.put("ArchiveTime", null);
		// request.put("RedirectURL", null);
		// request.put("IssueID", null);
		// request.put("entryId", null);
		// request.put("actionId", null);
		// request.put("NextStepUser", null);
		// request.put("Memo", null);
		// request.put("NoteContent", null);
		// request.put("RelaImagePlayerID", null);
		// request.put("RelaChange", null);
		// request.put("RelaImageIDs", null);
		// ---------------------------------
		request.put(ColumnUtil.PREFIX + "AppFacDutyDef",
				mProductFactors.get("AppFacDutyDef"));
		request.put(ColumnUtil.PREFIX + "AppFacDutyRela",
				mProductFactors.get("AppFacDutyRela"));
		request.put(ColumnUtil.PREFIX + "ComplicatedFlag",
				mProductFactors.get("ComplicatedFlag"));
		//长期险标志
		request.put(ColumnUtil.PREFIX + "LongInsurance",
				mProductFactors.get("LongInsurance"));
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
		request.put(ColumnUtil.PREFIX + "BasePremHTML",
				mProductFactors.get("BasePremHTML"));
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
		request.put(ColumnUtil.PREFIX + "ProductDetailBright",
				mProductFactors.get("ProductDetailBright"));
		request.put(ColumnUtil.PREFIX + "ProductPresentation",
				mProductFactors.get("ProductPresentation"));
		request.put(ColumnUtil.PREFIX + "ProductDetailFront",
				mProductFactors.get("ProductDetailFront"));
		request.put(ColumnUtil.PREFIX + "ProductDetailTail",
				mProductFactors.get("ProductDetailTail"));
		request.put(ColumnUtil.PREFIX + "FCDoc", mProductFactors.get("FCDoc"));
		request.put(ColumnUtil.PREFIX + "ClaimData", mProductFactors.get("ClaimData"));
		request.put(ColumnUtil.PREFIX + "ClaimGuide", mProductFactors.get("ClaimGuide"));
		request.put(
				ColumnUtil.PREFIX + "ProductLogo",
				(mFMRisk.getERiskPicRelaPath().startsWith("/")) ? mFMRisk
						.getERiskPicRelaPath() : "/"
						+ mFMRisk.getERiskPicRelaPath());
		request.put(ColumnUtil.PREFIX + "ProductInt", mProductFactors.get("ProductInt"));
		//getIntegral(mFMRisk)
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
		// 投保声明
		request.put(ColumnUtil.PREFIX + "InsuranceDec",
				mProductFactors.get("InsuranceDec"));
		request.put(ColumnUtil.PREFIX + "isCpsProduct",
				mProductFactors.get("isCpsProduct"));
		request.put(ColumnUtil.PREFIX + "cpsAdress",
				mProductFactors.get("cpsAdress"));
		request.put(ColumnUtil.PREFIX + "isFinancialRisks",
				mProductFactors.get("isFinancialRisks"));
		request.put(ColumnUtil.PREFIX + "limcount",
				mProductFactors.get("limcount"));
		request.put(ColumnUtil.PREFIX + "limcountNumber",
				mProductFactors.get("limcountNumber"));
		request.put(ColumnUtil.PREFIX + "DeadlineDesc",
				mProductFactors.get("DeadlineDesc"));
		request.put(ColumnUtil.PREFIX + "Phone", mProductFactors.get("Phone"));
		request.put(ColumnUtil.PREFIX + "CompanyDesc",
				mProductFactors.get("CompanyDesc"));
		request.put(ColumnUtil.PREFIX + "CompanyShortName",
				mProductFactors.get("CompanyShortName"));
		request.put(ColumnUtil.PREFIX + "CompanyLink",
				mProductFactors.get("CompanyLink"));
		request.put(ColumnUtil.PREFIX + "Doc1", mProductFactors.get("Doc1"));
		request.put(ColumnUtil.PREFIX + "Doc2", mProductFactors.get("Doc2"));
		request.put(ColumnUtil.PREFIX + "Doc3", mProductFactors.get("Doc3"));
		request.put(ColumnUtil.PREFIX + "Doc5", mProductFactors.get("Doc5"));
		request.put(ColumnUtil.PREFIX + "Doc7", mProductFactors.get("Doc7"));
		request.put(ColumnUtil.PREFIX + "SortDoc", mProductFactors.get("SortDoc"));
		request.put(ColumnUtil.PREFIX + "question1",
				mProductFactors.get("question1"));
		request.put(ColumnUtil.PREFIX + "question2",
				mProductFactors.get("question2"));
		request.put(ColumnUtil.PREFIX + "answer1",
				mProductFactors.get("answer1"));
		request.put(ColumnUtil.PREFIX + "answer2",
				mProductFactors.get("answer2"));
		request.put(ColumnUtil.PREFIX + "DutyHTML2",
				mProductFactors.get("DutyHTML2"));
		request.put(ColumnUtil.PREFIX + "ProductActive",
				mProductFactors.get("ProductActive"));
		request.put(ColumnUtil.PREFIX + "FEMRiskBrightSpotNew",
				mProductFactors.get("FEMRiskBrightSpotNew"));
		request.put(ColumnUtil.PREFIX + "BasePremV3",
				mProductFactors.get("BasePremV3"));
		request.put(ColumnUtil.PREFIX + "BasePremValue",
				mProductFactors.get("BasePremValue"));
		request.put(ColumnUtil.PREFIX + "AdaptPeopleInfoV3",
				"publish");
		request.put(ColumnUtil.PREFIX + "AdaptPeopleInfoListV3",
				mProductFactors.get("AdaptPeopleInfoListV3"));
		request.put(ColumnUtil.PREFIX + "DutyHTMLV3",
				mProductFactors.get("DutyHTMLV3"));
		request.put(ColumnUtil.PREFIX + "DutyHTMLV4",
				mProductFactors.get("DutyHTMLV4"));
		request.put(ColumnUtil.PREFIX + "RecomDutyV3",
				mProductFactors.get("RecomDutyV3"));
		request.put(ColumnUtil.PREFIX + "InitPremCPS",
				mProductFactors.get("InitPremCPS"));
		request.put(ColumnUtil.PREFIX + "prodcutMarkPriceCPS",
				mProductFactors.get("prodcutMarkPriceCPS"));
		request.put(ColumnUtil.PREFIX + "SplitRiskCode",
				mProductFactors.get("SplitRiskCode"));
		request.put(ColumnUtil.PREFIX + "CanonicalProduct",
				mProductFactors.get("CanonicalProduct"));
		// 拍拍速赔
		request.put(ColumnUtil.PREFIX + "ClaimsComplateDate",
				mFMRisk.getBackUp8());
		// 责任信息告知
		request.put(ColumnUtil.PREFIX + "termInformation",
				mProductFactors.get("termInformation"));
		// 如果产品中心未设置产品logo，则取保险公司logo
		if (StringUtil.isNotEmpty(mFMRisk.getERiskPicRelaPath())) {
			request.put(ColumnUtil.PREFIX + "LogoLink", Config.getValue("ProductResourcePath")
					+ ((mFMRisk.getERiskPicRelaPath().startsWith("/")) ? mFMRisk.getERiskPicRelaPath() : "/" + mFMRisk.getERiskPicRelaPath()));
		
		} else {
			request.put(ColumnUtil.PREFIX + "LogoLink", Config.getValue("StaticResourcePath") + "/images/productimages/" + mFMRisk.getSupplierCode() + ".jpg");
			
		}

		// 现金价值
		request.put(ColumnUtil.PREFIX + "cashValueFlag", mProductFactors.get("cashValueFlag"));
		// 年金险
		request.put(ColumnUtil.PREFIX + "yearCashFlag", mProductFactors.get("yearCashFlag"));
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
			//推荐图片
			String value2 = db
					.getOneValue("select textvalue from ZDColumnValue where columncode='RecommendImg' and relaid='"
							+ id + "'");
			//产品上架、同步时如果列表页推荐图片空，则设置默认图片，在发布时，根据此图片标记判断是否需要显示推荐图片
			if(StringUtil.isEmpty(value2)){
				value2 = Config.getValue("StaticResourcePath")+ "/upload/Image/nopicture.jpg";
			}
			request.put(ColumnUtil.PREFIX + "RecommendImg",value2);
			//推荐文案
			String value3 = db
					.getOneValue("select textvalue from ZDColumnValue where columncode='RecommendInfo' and relaid='"
							+ id + "'");
			request.put(ColumnUtil.PREFIX + "RecommendInfo",value3);

			// 每日优惠
			String value4 = new QueryBuilder("select textvalue from ZDColumnValue where columncode='DisInfo' and relaid=? " , id).executeString();
			request.put(ColumnUtil.PREFIX + "DisInfo", value4);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return true;
	}

	/**
	 * 得到产品要复制的引用
	 * 
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

//			IntegralAction iA = new IntegralAction();
//			int result = iA.paySuccess(tFMRisk.getFeeRate(),
//					Double.parseDouble(tFMRisk.getDiscountPrice()),
//					db.getOneValue(sql));
			int result = new BigDecimal(tFMRisk.getDiscountPrice()).intValue();
			Object[] argArr = {tFMRisk.getFeeRate(), tFMRisk.getDiscountPrice(), result, tFMRisk.getInitPrem()};
			logger.info("FeeRate(初始手续费率):{} - InitPrem(折扣定价):{} 积分：{} 原价：{}", argArr);
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return 0;
		}
	}

	private String getRiskAttribute(FMRisk tFMRisk) {
		String str = "";
		if ("Y".equalsIgnoreCase(tFMRisk.getHot())) {
			str += "hot";
		}
		if ("Y".equalsIgnoreCase(tFMRisk.getIsCommend())) {
			str += ",newRecommend";
		}
		if (str.startsWith(",")) {
			str = str.substring(1);
		}
		return str;
	}

	private String getRiskTopFlag(String isTop) {
		if ("Y".equalsIgnoreCase(isTop)) {
			return "1";
		}
		return "0";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String dealImage(String imagePath, String tSiteID) {
		if (mFMRisk.getERiskPicRealPath() == null
				|| "".equals(mFMRisk.getERiskPicRealPath())) {
			return imagePath;
		}
		long imageCatalog = 0;
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setSiteID(tSiteID);
		catalog.setName("产品图片");
		catalog.setAlias("cptp");
		catalog.setType(4);
		catalog.setParentID(0);
		ZCCatalogSet set = catalog.query();
		if (set != null && set.size() > 0) {
			imageCatalog = set.get(0).getID();
		} else {
			Mapx params = new Mapx();
			params.put("ParentID", "0");
			params.put("Name", "产品图片");
			params.put("Type", 4);
			params.put("Alias", "cptp");
			params.put("SiteID", tSiteID);
			params.put("URL", "cptp/");
			CatalogAPI api = new CatalogAPI();
			api.setParams(params);
			if (api.insert() == 1) {
				imageCatalog = api.getMCatalogID();
			}
		}
		if (imageCatalog <= 0) {
			return imagePath;
		}
		if (imagePath == null || "".equals(imagePath)) {
			ImageAPI image = new ImageAPI();
			image.setFileName(mFMRisk.getERiskPicRealPath());
			image.setCatalogID(imageCatalog);
			image.insert();
			return image.getSchema().getPath()
					+ image.getSchema().getFileName();
		} else {
			String dir = Config.getContextRealPath()
					+ Config.getValue("UploadDir") + "/"
					+ SiteUtil.getAlias(tSiteID) + "/" + imagePath;
			dir = dir.replaceAll("//", "/");
			FileUtil.delete(dir);
			FileUtil.copy(mFMRisk.getERiskPicRealPath(), dir);
			return imagePath;
		}
	}

	public static String getBrightSoptHTML(FMRisk tFMRisk) {
		FEMRiskBrightSpotList[] femRiskBrightSpotList = tFMRisk .getFEMRiskBrightSpotList();
		if (femRiskBrightSpotList != null && femRiskBrightSpotList.length > 0 && femRiskBrightSpotList[0] != null) {
			String tHTML = "<ul class=\"d_list_con\">";
			String brightSpotName = "";
			for (int i = 0; i < femRiskBrightSpotList.length; i++) {
				// 亮点最多显示6个
				if (i > 5) {
					break;
				}
				// 每个亮点长度不能超过10个字符
				brightSpotName = femRiskBrightSpotList[i].getBrightSpotName();
				if (brightSpotName != null && brightSpotName.length() > 10) {
					brightSpotName = brightSpotName.substring(0, 10);
				}
				tHTML += "<li><span>" + brightSpotName + "</span></li>";
			}
			tHTML += "</ul>";
			return tHTML;
		}
		return "";
	}

	/**
	 * 获取产品介绍
	 *
	 * @param tFMRisk
	 * @return
	 */
	public static String getProductPresentation(FMRisk tFMRisk) {
		String riskCode = tFMRisk.getRiskCode();

		QueryBuilder qb = new QueryBuilder(" SELECT BaseInfo FROM ProductBaseInfo WHERE ProductID=? ");
		qb.add(riskCode);
		DataTable dt = qb.executeDataTable();
		String sHTML = "";
		if(dt!=null && dt.getRowCount()>=1){
			sHTML = dt.getString(0, "BaseInfo");
		}
		return sHTML;
	}

    /**
     * 获取产品详细页的产品亮点
     *
     * @param tFMRisk
     * @param catalogID
     * @return
     */
	public static String getProductDetailBright(FMRisk tFMRisk, String catalogID) {
		FEMRiskBrightSpotList[] femRiskBrightSpotList = tFMRisk .getFEMRiskBrightSpotList();
		if (femRiskBrightSpotList != null && femRiskBrightSpotList.length > 0
				&& femRiskBrightSpotList[0] != null) {
			DataTable dtcatalog = new QueryBuilder("select type from zdconfig where value = (select parentid from zccatalog where id='"+catalogID+"')").executeDataTable();
			String catalogType = "";
			if(dtcatalog.getRowCount()>0){
				catalogType = dtcatalog.getString(0, 0);
			}
			if("CatalogBXSCID".equals(catalogType)){
				String tHTML = "<div class=\"li_list li_list_up\"><b class=\"ld_b\">产品亮点：</b><ul class=\"ld_list_box\">";
				for (int i = 0; i < femRiskBrightSpotList.length; i++) {
					tHTML += "<li><span>"
							+ femRiskBrightSpotList[i].getBrightSpotName()
							+ "</span></li>";
				}
				tHTML += "</ul></div>";
				return tHTML;
			}else{
				String tHTML = "<ul>";
				String brightSpotName = "";
				for (int i = 0; i < femRiskBrightSpotList.length; i++) {
					// 亮点最多显示4个
					if (i > 3) {
						break;
					}
					// 每个亮点长度不能超过20个字符
					brightSpotName = femRiskBrightSpotList[i].getBrightSpotName();
					if (brightSpotName != null && brightSpotName.length() > 20) {
						brightSpotName = brightSpotName.substring(0, 20);
					}
					tHTML += "<li><em class=\"icon_0"+(i+1)+"\"></em>"
							+ brightSpotName
							+ "</li>";
				}
				tHTML += "</ul>";
				return tHTML;
			}
		}
		return "";
	}
    public static String getProductDetailBase(FMRisk tFMRisk,String Flag) {
		
		
		String riskCode = tFMRisk.getRiskCode();
		
		QueryBuilder qb = new QueryBuilder(" SELECT FrontInfo,TailInfo,IsFlag FROM ProductBaseInfo WHERE ProductID=? ");
		qb.add(riskCode);
		DataTable dt = qb.executeDataTable();
		String comCode = new QueryBuilder(" select Remark6 from sdproduct where productid='"+riskCode+"' ").executeString();
		QueryBuilder qb1 = new QueryBuilder(" SELECT FrontInfo,TailInfo FROM ProductBaseInfo WHERE ComCode='"+comCode+"' and productid != '"+riskCode+"' and ComFlag='Y' limit 1 ");
		DataTable dt1 = qb1.executeDataTable();
		if(dt!=null && dt.getRowCount()>=1){
			if("Front".equals(Flag)){
				String FrontInfo = dt.getString(0, "FrontInfo");
				String IsFlag = dt.getString(0, "IsFlag");
				if(StringUtil.isEmpty(FrontInfo)&&!"N".equals(IsFlag)){
					if(dt1!=null && dt1.getRowCount()>=1){
						FrontInfo = dt1.getString(0, "FrontInfo");
					}
				}
				return FrontInfo;
			}else if("Tail".equals(Flag)){
				String TailInfo = dt.getString(0, "TailInfo");
				String IsFlag = dt.getString(0, "IsFlag");
				if(StringUtil.isEmpty(TailInfo)&&!"N".equals(IsFlag)){
					if(dt1!=null && dt1.getRowCount()>=1){
						TailInfo = dt1.getString(0, "TailInfo");
					}
				}
				return TailInfo;
			}
		}else{
			if(dt1!=null && dt1.getRowCount()>=1){
				if("Front".equals(Flag)){
					return dt1.getString(0, "FrontInfo");
				}else if("Tail".equals(Flag)){
					return dt1.getString(0, "TailInfo");
				}
			} 
		}
		
		 
		return "";
	}
	public static String getBasePremHTML(FMRisk tFMRisk) {
		//产品原价
		String discountRate = tFMRisk.getBackUp2();
		String tReturn="";
		if(discountRate!=null && !"".equals(discountRate)){
			tReturn = "<i class=\"moneys img_pirce_del\">￥"+tFMRisk.getInitPrem()+"</i>";
			//tReturn = "<span class=\"shop_activity activity1\" title=\"满100减40\">"+Double.parseDouble(discountRate)*10+"折</span>";
		}
		return tReturn;
	}
	public static String getBrightSoptHTML1(FMRisk tFMRisk) {
		FEMRiskBrightSpotList[] femRiskBrightSpotList = tFMRisk
				.getFEMRiskBrightSpotList();
		if (femRiskBrightSpotList != null && femRiskBrightSpotList.length > 0
				&& femRiskBrightSpotList[0] != null) {
			String tHTML = "";
			for (int i = 0; i < femRiskBrightSpotList.length; i++) {
				tHTML += "<li><span>"
						+ femRiskBrightSpotList[i].getBrightSpotName()
						+ "</span></li>";
			}
			return tHTML;
		}
		return "";
	}
	private boolean getCatalogID() {
		mCatalogIDs = new ArrayList<String>(0);
		String tERiskType = mFMRisk.getERiskType();// 内部统计险种中类别 大类 ABCDE
		if (tERiskType == null || tERiskType.equals("")) {
			logger.error(
					"同步产品信息getCatalogID方法,产品【{}_{}】获取产品大类ERiskType信息失败,请联系产品中心管理员！！！",
					mFMRisk.getRiskCode(), mFMRisk.getRiskName());
			return false;
		}

		// 内部统计险种中类别 根据大类来判断 ，现在一个险种只有一个大类fhz
		GetDBdata db = new GetDBdata();
		try {
			String eRiskCatalog = db
					.getOneValue("select ID from ZCCatalog where producttype ='"
							+ tERiskType + "00'");
			String jrjRiskCatalog = db
					.getOneValue("select ID from ZCCatalog where producttype ='Z"
							+ tERiskType + "00'");
			if(StringUtil.isNotEmpty(eRiskCatalog)){
				mCatalogIDs.add(eRiskCatalog);
			}
			if(StringUtil.isNotEmpty(jrjRiskCatalog)){
				mCatalogIDs.add(jrjRiskCatalog);
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

	public static void main(String[] arg) {
//		Map<Object,Object> map_data=new LinkedHashMap<Object,Object>();
//		
//		Map<Object,Object> map_second=new LinkedHashMap<Object,Object>();
//		List<String> list_third=new ArrayList<String>();
//		list_third.add("男");
//		list_third.add("女");
//		map_second.put("性别", list_third);
//		map_second.put("别别", list_third);
//		map_data.put("50",map_second);
//		
//		
//		
//		Map<Object,Object> map_second1=new LinkedHashMap<Object,Object>();
//		List<String> list_third1=new ArrayList<String>();
//		list_third1.add("好");
//		list_third1.add("人");
//		map_second1.put("性别", list_third1);
//		map_second1.put("好啊好", list_third1);
//		map_data.put("51",map_second1);
//		
//		JSONObject object_DutyRela = JSONObject.fromObject(map_data);
//		String jsonstr = object_DutyRela.toString();
//		System.out.println(jsonstr);


		// 测试同步所有产品
		boolean needRiskList = false;
		boolean isALL = false;
		List<String> allRisk = new ArrayList<String>();
		try {
			if (needRiskList) {
				QueryBuilder qb = new QueryBuilder(
						"select productid as riskcode from sdproduct ");
				DataTable dt = qb.executeDataTable();
				System.out.println(dt.getRowCount());
				for (int j = 0; j < dt.getRowCount(); j++) {
					Map<String, Object> map = new HashMap<String, Object>();
					System.out.println("----------------------"
							+ dt.getString(j, "riskcode"));
					map.put("RiskCode",
							new String[] { dt.getString(j, "riskcode") });

					ProductInfoResponse productInfo = ProductWebservice
							.ProductInfoSereviceImpl(map, null);
					try{
						FMRisk[] list = productInfo.getFMRisk();
						if (list != null && list.length > 0 && list[0] != null) {
							for (int i = 0; i < list.length; i++) {
								System.out.println("==" + list[i].getRiskName()
										+ "==");
								System.out.println("==riskcode:"
										+ list[i].getRiskCode());
								System.out.println("==FEMRiskType:"
										+ getFEMRiskType(list[i].getFEMRiskType()));
								System.out
										.println("=================================");
								allRisk.add(list[i].getRiskCode());
							}
						}
					}catch(Exception e){
						continue;
					}
					

				}
			}
			CmsService csi = new CmsServiceImpl();
			if (isALL) {
				for (String riskcode : allRisk) {
					System.out.println("==publishing " + riskcode + " ==");
					csi.publishArticle(riskcode, "");
					System.out.println("=================================");
				}
			} else {
				csi.publishArticle("225701001", "");
//				csi.publishArticle("106501035", "");
//				csi.publishArticle("106501036", "");
//				csi.publishArticle("106501037", "");
//				csi.publishArticle("106501038", "");
//				csi.publishArticle("203401025", "");
//				csi.publishArticle("207101069", "");
//				csi.publishArticle("100401003");
//				csi.publishArticle("100401004");
//				csi.publishArticle("101001001");
//				csi.publishArticle("101401001");
//				csi.publishArticle("101401006");
//				csi.publishArticle("101401008");
//				csi.publishArticle("101501008");
//				csi.publishArticle("101501009");
//				csi.publishArticle("101501010");
//				csi.publishArticle("101501012");
//				csi.publishArticle("101501013");
//				csi.publishArticle("101501014");
//				csi.publishArticle("101501015");
//				csi.publishArticle("101501016");
//				csi.publishArticle("101501017");
//				csi.publishArticle("101501018");
//				csi.publishArticle("101501019");
//				csi.publishArticle("101501020");
//				csi.publishArticle("101501022");
//				csi.publishArticle("101501023");
//				csi.publishArticle("101501026");
//				csi.publishArticle("101501027");
//				csi.publishArticle("101501028");
//				csi.publishArticle("101501029");
//				csi.publishArticle("101801003");
//				csi.publishArticle("101801004");
//				csi.publishArticle("101801005");
//				csi.publishArticle("101801006");
//				csi.publishArticle("101801007");
//				csi.publishArticle("101801008");
//				csi.publishArticle("101801009");
//				csi.publishArticle("10180101068");
//				csi.publishArticle("10180101071");
//				csi.publishArticle("10180101100");
//				csi.publishArticle("104801001");
//				csi.publishArticle("104801003");
//				csi.publishArticle("104801004");
//				csi.publishArticle("106101001");
//				csi.publishArticle("106101081");
//				csi.publishArticle("106101084");
//				csi.publishArticle("106101085");
//				csi.publishArticle("106101091");
//				csi.publishArticle("106501001");
//				csi.publishArticle("106501002");
//				csi.publishArticle("106501003");
//				csi.publishArticle("106501004");
//				csi.publishArticle("106501005");
//				csi.publishArticle("106501006");
//				csi.publishArticle("106501007");
//				csi.publishArticle("106501008");
//				csi.publishArticle("106501009");
//				csi.publishArticle("106501010");
//				csi.publishArticle("106501011");
//				csi.publishArticle("106501012");
//				csi.publishArticle("106501016");
//				csi.publishArticle("106501017");
//				csi.publishArticle("106501018");
//				csi.publishArticle("106501019");
//				csi.publishArticle("109101001");
//				csi.publishArticle("109101002");
//				csi.publishArticle("109201001");
//				csi.publishArticle("109201002");
//				csi.publishArticle("109201003");
//				csi.publishArticle("200701001");
//				csi.publishArticle("200701002");
//				csi.publishArticle("200701003");
//				csi.publishArticle("200701004");
//				csi.publishArticle("200701005");
//				csi.publishArticle("200701006");
//				csi.publishArticle("200701007");
//				csi.publishArticle("200701008");
//				csi.publishArticle("200701009");
//				csi.publishArticle("200701010");
//				csi.publishArticle("200701011");
//				csi.publishArticle("200701012");
//				csi.publishArticle("200701013");
//				csi.publishArticle("200701014");
//				csi.publishArticle("200701015");
//				csi.publishArticle("200701016");
//				csi.publishArticle("200701017");
//				csi.publishArticle("200701018");
//				csi.publishArticle("200701019");
//				csi.publishArticle("200701020");
//				csi.publishArticle("200701021");
//				csi.publishArticle("200701022");
//				csi.publishArticle("200701023");
//				csi.publishArticle("200701024");
//				csi.publishArticle("200701025");
//				csi.publishArticle("200701026");
//				csi.publishArticle("200701027");
//				csi.publishArticle("200701028");
//				csi.publishArticle("200701029");
//				csi.publishArticle("200701030");
//				csi.publishArticle("200701031");
//				csi.publishArticle("200701032");
//				csi.publishArticle("200701033");
//				csi.publishArticle("200701034");
//				csi.publishArticle("200701035");
//				csi.publishArticle("200701036");
//				csi.publishArticle("200701045");
//				csi.publishArticle("200701057");
//				csi.publishArticle("200701058");
//				csi.publishArticle("200701060");
//				csi.publishArticle("200701064");
//				csi.publishArticle("200701137");
//				csi.publishArticle("200731159101001");
//				csi.publishArticle("200731159101002");
//				csi.publishArticle("200731159101003");
//				csi.publishArticle("200731159101059");
//				csi.publishArticle("200731159101101");
//				csi.publishArticle("201101002");
//				csi.publishArticle("201101003");
//				csi.publishArticle("201101004");
//				csi.publishArticle("201101005");
//				csi.publishArticle("201101006");
//				csi.publishArticle("201101007");
//				csi.publishArticle("201101008");
//				csi.publishArticle("201101009");
//				csi.publishArticle("201101010");
//				csi.publishArticle("201101011");
//				csi.publishArticle("201101139");
//				csi.publishArticle("201102002");
//				csi.publishArticle("201102003");
//				csi.publishArticle("202301001");
//				csi.publishArticle("202301002");
//				csi.publishArticle("202301003");
//				csi.publishArticle("202302001");
//				csi.publishArticle("203401001");
//				csi.publishArticle("203401002");
//				csi.publishArticle("203401003");
//				csi.publishArticle("203401004");
//				csi.publishArticle("204201001");
//				csi.publishArticle("204301000");
//				csi.publishArticle("204301004");
//				csi.publishArticle("204301005");
//				csi.publishArticle("204301006");
//				csi.publishArticle("204301007");
//				csi.publishArticle("204301009");
//				csi.publishArticle("204302001");
//				csi.publishArticle("204901002");
//				csi.publishArticle("204901003");
//				csi.publishArticle("204901004");
//				csi.publishArticle("204901005");
//				csi.publishArticle("204901006");
//				csi.publishArticle("204901007");
//				csi.publishArticle("204901008");
//				csi.publishArticle("204901009");
//				csi.publishArticle("204901010");
//				csi.publishArticle("204901011");
//				csi.publishArticle("204901012");
//				csi.publishArticle("204901013");
//				csi.publishArticle("204901014");
//				csi.publishArticle("204901015");
//				csi.publishArticle("204901016");
//				csi.publishArticle("204901017");
//				csi.publishArticle("204901018");
//				csi.publishArticle("204901020");
//				csi.publishArticle("204901021");
//				csi.publishArticle("204901045");
//				csi.publishArticle("204901046");
//				csi.publishArticle("204901047");
//				csi.publishArticle("207101001");
//				csi.publishArticle("207101002");
//				csi.publishArticle("207101003");
//				csi.publishArticle("207101004");
//				csi.publishArticle("207101007");
//				csi.publishArticle("207101008");
//				csi.publishArticle("207101009");
//				csi.publishArticle("207101010");
//				csi.publishArticle("207101011");
//				csi.publishArticle("207101012");
//				csi.publishArticle("207101013");
//				csi.publishArticle("207101014");
//				csi.publishArticle("207101015");
//				csi.publishArticle("207101016");
//				csi.publishArticle("207101019");
//				csi.publishArticle("207101025");
//				csi.publishArticle("207101026");
//				csi.publishArticle("207401001");
//				csi.publishArticle("207401002");
//				csi.publishArticle("207401003");
//				csi.publishArticle("207401004");
//				csi.publishArticle("209601001");
//				csi.publishArticle("209601002");
//				csi.publishArticle("209601003");
//				csi.publishArticle("209601004");
//				csi.publishArticle("209601005");
//				csi.publishArticle("209601006");
//				csi.publishArticle("209601007");
//				csi.publishArticle("209601008");
//				csi.publishArticle("209601009");
//				csi.publishArticle("209601010");
//				csi.publishArticle("209601011");
//				csi.publishArticle("209601015");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ceshi：" + e.getMessage());
		}
		System.out.println("publishing over");
		return;
		// //
		//
		//
		// // ProductInfoResponse productInfo; // 测试所有险种getDutyHtml
		// // try {
		// // productInfo = ProductWebservice.ProductInfoSereviceImpl(new
		// HashMap<String, Object>(), null);
		// // FMRisk[] list = productInfo.getFMRisk();
		// //
		// // if (list != null && list.length > 0 && list[0] != null) {
		// // System.out.println("begin");
		// // for (int i = 0; i < list.length; i++) {
		// // AutomaticPublishArticle.getDutyHTML(list[i]);
		// // }
		// // }
		// // System.out.println("end");
		// // } catch (Exception e) {
		// // // TODO Auto-generated catch block
		// // e.printStackTrace();
		// // }
		//
		// // Map<String, Object> map = new HashMap<String, Object>();
		// // map.put("RiskCode", new String[] { "200701042" });
		// //
		// // try {
		// // ProductInfoResponse productInfo =
		// ProductWebservice.ProductInfoSereviceImpl(map, null);
		// // FMRisk[] list = productInfo.getFMRisk();
		// // System.out.println(AutomaticPublishArticle.getDutyHTML(list[0]));
		// //
		// // } catch (Exception e) {
		// // // TODO Auto-generated catch block
		// // e.printStackTrace();
		// // }
		//
		// //同步某些产品
		// // String[]
		// needTestRiskCode={"101501008","101501012","101501013","101501065","200701037","200701038","200701039","200701040","200701041","200701042","201101002","201101005","201101006","201101007","201101009","202301001","202301003"};
		// // CmsServiceImpl csi = new CmsServiceImpl();
		// // for (String riskcode : needTestRiskCode) {
		// // System.out.println("==publishing " + riskcode +
		// " ==begin%%%%%%%%%%%%%%%%%%%%%");
		// // csi.publishArticle(riskcode);
		// // System.out.println("==publishing " + riskcode +
		// " ==end&&&&&&&&&&&&&&&&&&&&&&&");
		// //
		// // }
		//
	
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
				"select f1.riskcode from  femriskb f1  ,fmriskb f2 where f1.IsPublish='Y' and f1.riskcode=f2.riskcode  and riskprop='I' ");
		DataTable dt = qb.executeDataTable();
		Map<String, Object> map = new HashMap<String, Object>();
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
				logger.info("ceshi：" + e.getMessage(), e);
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
						AutomaticPublishArticle.publishing(list[i], "");
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

	public static void allProductArea(LongTimeTask lTT,String productID,String companyID) {
		logger.info("全部产品同步开始:{}", new Date().toString());
		FMRisk[] list = null;
		Map<String, Object> map = new HashMap<String, Object>();
		//mod by wangej 20160107 地区同步增加产品和保险公司的范围选择
		StringBuffer sqlRiskcode = new StringBuffer();
		sqlRiskcode.append("select f1.riskcode from  femriskb f1  ,fmriskb f2 ");
		sqlRiskcode.append("where f1.IsPublish='Y' and f1.riskcode=f2.riskcode  and riskprop='I' ");
		if(StringUtil.isNotEmpty(productID)){
			sqlRiskcode.append("and  f1.riskcode = '" + productID + "' ");
		}
		if(StringUtil.isNotEmpty(companyID)){
			sqlRiskcode.append("and f2.SupplierCode = '" + companyID + "' ");
		}
		QueryBuilder qb = new QueryBuilder(sqlRiskcode.toString());
		
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
					logger.error(e.getMessage());
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
							AutomaticPublishArticle.publishingArea(list[i]);
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

	public static void allProductHI(LongTimeTask lTT,String productID,String companyID) {
		logger.info("全部产品同步开始:{}", new Date().toString());
		FMRisk[] list = null;
		Map<String, Object> map = new HashMap<String, Object>();
		
		//mod by wangej 20160107 地区同步增加产品和保险公司的范围选择
		StringBuffer sqlRiskcode = new StringBuffer();
		sqlRiskcode.append("select f1.riskcode from  femriskb f1  ,fmriskb f2 ");
		sqlRiskcode.append("where f1.IsPublish='Y' and f1.riskcode=f2.riskcode  and riskprop='I' ");
		if(StringUtil.isNotEmpty(productID)){
			sqlRiskcode.append("and  f1.riskcode = '" + productID + "' ");
		}
		
		if(StringUtil.isNotEmpty(companyID)){
			sqlRiskcode.append("and f2.SupplierCode = '" + companyID + "' ");
		}
		QueryBuilder qb = new QueryBuilder(sqlRiskcode.toString());		DataTable dt = qb.executeDataTable();
		if (dt.getRowCount() > 0) {
			for (int j = 0; j < dt.getRowCount(); j++) {
				DataRow ro = dt.get(j);
				map.put("RiskCode", new String[] { ro.get("riskcode") + "" });
				try {
					ProductInfoResponse productInfo = ProductWebservice
							.ProductInfoSereviceImpl(map, null);
					list = productInfo.getFMRisk();
				} catch (Exception e) {
					logger.error(e.getMessage());
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
							AutomaticPublishArticle.publishingHI(list[i]);
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
					AutomaticPublishArticle.publishing(list[i], "");
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
	
	/**
	 * 
	 * 获取和保险品牌下保险公司的链接
	 */
	private String getCompanyLink(String riskcode) {
		int i;
		String companylink="";
		String riskcode1=riskcode.substring(0, 4);
		String riskcode2;
		QueryBuilder qb = new QueryBuilder("select TextValue,RelaID from zdcolumnvalue where columncode='CompanyID'");
		DataTable dt = qb.executeDataTable();
		for(i=0;i<dt.getRowCount();i++)
		{	
			riskcode2=dt.get(i).getString("TextValue");
			// 前端应用访问路径
			String FrontServerContextPath = Config.getValue("FrontServerContextPath");
			if (!FrontServerContextPath.endsWith("/")) {
				FrontServerContextPath += "/"; 
			}
			if(riskcode2.contains(riskcode1))
			{
				QueryBuilder qb1 = new QueryBuilder("select URL from zcarticle where URL like 'gongsi%' and id='");
				qb1.append(dt.get(i).getString("RelaID"));
				qb1.append("'");
				DataTable dt1 = qb1.executeDataTable();
				if (dt1 != null && dt1.getRowCount() > 0) {
					companylink=FrontServerContextPath + dt1.get(0).getString("URL") ;
					break;
				} else {
					companylink=FrontServerContextPath;
				}
			}
			else
			{
				companylink=FrontServerContextPath;
			}
		}
		return companylink;
	}
	/**
	 * 得到产品保险期间
	 * @param femrList
	 * @return
	 */
	private String getPeriodInsurance(FEMRiskFactorList[] femrList){
		
		int len = femrList.length;
		List<Integer> arrList = new ArrayList<Integer>();
		String tPeriodInsurance = "";
		for (int i = 0; i < len; i++) {
			//保险期间已D、M、Y为单位，
			String values = femrList[i].getFactorValue();
			String[] arrValues = null;
			if(values.indexOf("-")!=-1){
				arrValues = values.split("-");
			}else{
				arrValues = new String[]{values};
			}
			int tLen = arrValues.length;
			for(int j=0;j<tLen;j++){
				arrList.add(this.getOnePeriod(arrValues[j]));
			}
		}
		//排序处理
		if(arrList.size()==1){
			arrList.add(1);
		}
		if(arrList.get(0)==0){
			arrList.set(0, 0);
		}
		Collections.sort(arrList);
		tPeriodInsurance = arrList.get(0)+"-"+arrList.get(arrList.size()-1);
		return tPeriodInsurance;
	}
	/**
	 * 把保险期间统一为D
	 * @param period
	 * @return
	 */
	private int getOnePeriod(String period){
		int tPeriod=1;
		if(period.indexOf("D")!=-1){
			tPeriod = Integer.parseInt(period.substring(0, period.length()-1));
		}else if(period.indexOf("M")!=-1){
			tPeriod = Integer.parseInt(period.substring(0, period.length()-1)) * 30;
		}else if(period.indexOf("Y")!=-1){
			tPeriod = Integer.parseInt(period.substring(0, period.length()-1)) * 365;
		}
		
		return tPeriod;
	}
	/**
	 * 原价
	* @Title: getBasePremV3 
	* @return String    返回类型 
	 */
	public String getBasePremV3(FMRisk tFMRisk,String isSiteActivity){
		
		String info = "";
		//保险公司折扣率.
		String discountRate = tFMRisk.getBackUp2();
		
		if("Y".equals(isSiteActivity)){
				info = "￥"+tFMRisk.getDiscountPrice()+"";
		}else if(discountRate!=null && !"".equals(discountRate)){
			if(StringUtil.isNotEmpty(tFMRisk.getInitPrem())){
				info = "￥"+tFMRisk.getInitPrem()+"";
			}
		}
		
		return info;
	}
	/**
	 * 原价
	* @Title: getBasePremV3 
	* @return String    返回类型 
	 */
	public String getBasePremValue(FMRisk tFMRisk){
		
		String info = "";
		//保险公司折扣率.
		String discountRate = tFMRisk.getBackUp2();
		
		if(discountRate!=null && !"".equals(discountRate)){
			info = tFMRisk.getDiscountPrice()+"";
		}else{
			info = tFMRisk.getInitPrem()+"";
		}
		return info;
	}
	/**
	 * 适合人群
	* @Title: getAdaptPeopleInfoV3 
	* @return String    返回类型 
	 */
	public String getAdaptPeopleInfoV3(FMRisk tFMRisk){
		// 产品适合人群
		String info = "";
		String cInfo = mFMRisk.getAdaptPeopleInfo();
		if(StringUtil.isNotEmpty(cInfo)){
			if(cInfo.length()>43){
				info = mFMRisk.getAdaptPeopleInfo().substring(0, 43)+"...";
			}else{
				info = mFMRisk.getAdaptPeopleInfo();
			}
			
		}
				
		return info;
	}
	public String getAdaptPeopleInfoListV3(FMRisk tFMRisk){
		// 产品适合人群
		String info = "";
		String cInfo = mFMRisk.getAdaptPeopleInfo();
		if(StringUtil.isNotEmpty(cInfo)){
			if(cInfo.length()>76){
				info = mFMRisk.getAdaptPeopleInfo().substring(0, 75)+"...";
			}else{
				info = mFMRisk.getAdaptPeopleInfo();
			}
			
		}
				
		return info;
	}
	/**
	 * list-责任条款
	 * @param tFMRisk
	 * @return
	 */
	public static String getDutyHTMLV3(FMRisk tFMRisk) {
		FMDuty[] fMDuty = tFMRisk.getFMDuty();
		String tHTML = "";
		if (fMDuty != null && fMDuty.length > 0 && fMDuty[0] != null) {
			try {
				FEMDutyFactor dutyFactor = null;
				String defaultValue = "";
				int k =1;
				for (FMDuty duty : fMDuty) {
					if ("Y".equals(duty.getIsDisplay())) {
						
						dutyFactor = duty.getFEMDutyFactor();
						FEMDutyAmntPremList[] AmntPremValues = dutyFactor
								.getFEMDutyAmntPremList();
						if (AmntPremValues != null && AmntPremValues.length > 0
								&& AmntPremValues[0] != null) {
							defaultValue = AmntPremValues[0].getAmnt();
						} else {
							defaultValue = "-";
						}
						//重新组装责任信息--新列表页需求只展示责任
						String cHtml = " <li class=\"rec_list_li\">";
						if(k % 2 ==0){
							cHtml = " <li class=\"rec_list_li nlist_rec_bg\">";
						}
						if (!"不投保".equals(defaultValue)) {
							defaultValue = "￥"+defaultValue;
						}
						tHTML += cHtml
								+ "<span class=\"rec_list_s\" title=\""+duty.getDutyName()+"\">"+duty.getDutyName()+"</span>"
								+ " <span class=\"rec_list_money\" title=\""+defaultValue+"\">"
								+ defaultValue
								+ " </span></li> ";
						k = k+1;
					}
				}

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				tHTML = "";
			}
		}
		tHTML += "";
		return tHTML;
	}
	/**
	 * list-责任条款
	 * @param tFMRisk
	 * @return
	 */
	public static String getDutyHTMLV4(FMRisk tFMRisk) {
		FMDuty[] fMDuty = tFMRisk.getFMDuty();
		String tHTML = "";
		if (fMDuty != null && fMDuty.length > 0 && fMDuty[0] != null) {
			try {
				FEMDutyFactor dutyFactor = null;
				String defaultValue = "";
				int k =1;
				for (FMDuty duty : fMDuty) {
					if ("Y".equals(duty.getIsDisplay())) {
						
						dutyFactor = duty.getFEMDutyFactor();
						FEMDutyAmntPremList[] AmntPremValues = dutyFactor
								.getFEMDutyAmntPremList();
						if (AmntPremValues != null && AmntPremValues.length > 0
								&& AmntPremValues[0] != null) {
							defaultValue = AmntPremValues[0].getAmnt();
						} else {
							defaultValue = "-";
						}
						tHTML +="<li><span>"+duty.getDutyName()+"</span><em>"+defaultValue+"</em></li>";
						k = k+1;
					}
				}

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				tHTML = "";
			}
		}
		tHTML += "";
		return tHTML;
	}
	/**
	 * Recommend-责任条款
	 * @param tFMRisk
	 * @return
	 */
	public static String getRecommendDutyV3(FMRisk tFMRisk) {
		FMDuty[] fMDuty = tFMRisk.getFMDuty();
		String tHTML = "";
		if (fMDuty != null && fMDuty.length > 0 && fMDuty[0] != null) {
			try {
				FEMDutyFactor dutyFactor = null;
				String defaultValue = "";
				int k =1;
				for (FMDuty duty : fMDuty) {
					if ("Y".equals(duty.getIsDisplay())) {
						
						dutyFactor = duty.getFEMDutyFactor();
						FEMDutyAmntPremList[] AmntPremValues = dutyFactor
								.getFEMDutyAmntPremList();
						if (AmntPremValues != null && AmntPremValues.length > 0
								&& AmntPremValues[0] != null) {
							defaultValue = AmntPremValues[0].getAmnt();
						} else {
							defaultValue = "-";
						}
						//重新组装责任信息--新列表页需求只展示责任
						String cHtml = " <li class=\"rec_list_li\">";
						if(k % 2 ==0){
							cHtml = " <li class=\"rec_list_li rec_listc_m\">";
						}
						tHTML += cHtml
								+ "<span class=\"rec_list_s\">"+duty.getDutyName()+"</span>"
								+ " <span class=\"rec_list_money\">"
								+ defaultValue
								+ " </span></li> ";
						k = k+1;
					}
				}

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				tHTML = "";
			}
		}
		tHTML += "";
		return tHTML;
	}
}
