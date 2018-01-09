package cn.com.sinosoft.service.impl;

import cn.com.sinosoft.entity.City;
import cn.com.sinosoft.entity.CompareNonAuto;
import cn.com.sinosoft.entity.RiskAppFactor;
import cn.com.sinosoft.service.CityService;
import cn.com.sinosoft.service.CompareService;
import cn.com.sinosoft.util.CookieUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActivityCalculate;
import com.sinosoft.sms.messageinterface.pubfun.PubFun;
import com.sinosoft.webservice.ProductWebservice;
import com.sinosoft.webservice.compareInfo.CompareInfoServiceStub;
import com.sinosoft.webservice.fDInsCom.FDInsComServiceStub;
import com.sinosoft.webservice.fDInsCom.FDInsComServiceStub.FDInsCom;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FEMDutyAmntPremList;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FEMRiskFactorList;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FERiskAppFactor;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FMDuty;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CompareServiceImpl extends BaseServiceImpl<Object, String> implements CompareService<Object> {
	private static final Logger logger = LoggerFactory.getLogger(CompareServiceImpl.class);
	@Resource
	private CityService cityService;

	public Map<String, Object> getNonAutoTrial(String[] riskCode, CompareInfoServiceStub.FEMRiskCompareProperties[] fcps) {
		HttpServletRequest request = ServletActionContext.getRequest();
		Map<String, Object> returnResult = new HashMap<String, Object>();
		ProductInfoWebServiceStub.FMRisk[] fmr = null;
		String newriskCode[]=new String[riskCode.length];
		for(int i=0 ; i < riskCode.length ;i++){
			newriskCode[i]=riskCode[i];
		}
		ProductInfoWebServiceStub.ProductInfoResponse prifr = getProductInfoResponse(riskCode);
		List<Map<String, Object>> compareInformation = new ArrayList<Map<String, Object>>();
		List<RiskAppFactor> rafs = new ArrayList<RiskAppFactor>();
		fmr = prifr.getFMRisk();
		if (fcps != null && fcps.length > 0) {
			if (newriskCode != null && newriskCode.length > 0) {
				int riskFactorColumnNo = 0;
				for (String productCode : newriskCode) {
					Map<String, Object> productMap = new HashMap<String, Object>();
					productMap.put("riskCode", productCode);
					for (ProductInfoWebServiceStub.FMRisk fm : fmr) {
						if (productCode.equals(fm.getRiskCode())) {
							try {
								productMap.put("comCode", reflectMap(fm, "SupplierCode"));
								productMap.put("RiskName", reflectMap(fm, "RiskName"));
								productMap.put("ProductLog", reflectMap(fm, "ERiskPicRelaPath"));
								productMap.put("complicatedFlag", reflectMap(fm, "BackUp4"));// 复杂产品标识
								String initPremValue = reflectMap(fm, "InitPrem").toString();// 原始价格
								
								
								String DiscountPrice = reflectMap(fm, "DiscountPrice").toString();// 折扣价格
								
								productMap.put("InitPrem", "<span id='InitPrem" + productCode + "_c"+(riskFactorColumnNo+1)+"'>" + initPremValue + "</span>");
								productMap.put("DiscountRate", reflectMap(fm, "BackUp2"));// 折扣率
								String Channel = "";
								
								Cookie ck;
								ck = CookieUtil.getCookieByName(request, "cpsUserId");
								if(StringUtil.isEmpty(ck)){
									Channel = "wj";
								}else{
									Channel = "cps";
								}
								
								Map<String,String> ProductIDMap = new HashMap<String, String>();
								ProductIDMap.put(productCode, DiscountPrice);
								
								Map<String, Map<String, String>> resultProductDetail = ActivityCalculate.ProductCalculate(ProductIDMap,"",Channel);
								Map<String, String> productDetail = resultProductDetail.get(productCode);
								
								String rAmount = productDetail.get("Amount");
								String rIntegral = productDetail.get("Integral");
								//String activityPrice = ActivityCalculate.ProductCalculate(productCode, DiscountPrice, "wj");
								//全站活动开启 Y N 价格显示
								productMap.put("DiscountPrem", "<span id='DiscountPrem" + productCode + "_c"+(riskFactorColumnNo+1)+"'>" + rAmount + "</span>");
								productMap.put("ProductInt", "<span id='Integral" + productCode + "_c"+(riskFactorColumnNo+1)+"'>" + rIntegral + "</span>");
								productMap.put("InitPremValue", initPremValue);
							} catch (Exception e1) {
								logger.error(e1.getMessage(), e1);
							}
							for (CompareInfoServiceStub.FEMRiskCompareProperties fcp : fcps) {
								if ("A".equals(fcp.getCompareInfoType())) {
									try {
										if ("RiskType".equals(fcp.getPropertyType())) {
											String flag = (String) reflectMap(fm, fcp.getPropertyType());
											if ("01".equals(flag)) {
												productMap.put(fcp.getPropertyType(), "寿险");
											} else if ("02".equals(flag)) {
												productMap.put(fcp.getPropertyType(), "财险");
											}
										} else if ("RiskBrightSpot".equals(fcp.getPropertyType())) {
											if (fm.getFEMRiskBrightSpotList() != null && fm.getFEMRiskBrightSpotList().length > 0) {
												StringBuffer spotListSB = new StringBuffer();
												for (ProductInfoWebServiceStub.FEMRiskBrightSpotList frbsl : fm.getFEMRiskBrightSpotList()) {
													if(frbsl!=null){
														spotListSB.append(" " + frbsl.getBrightSpotName() + "<br>");
													}
												}
												productMap.put(fcp.getPropertyType(), spotListSB.toString());
											}
										} else if ("RiskSaleArea".equals(fcp.getPropertyType())) {
											if (fm.getFEMRiskSaleArea() != null && fm.getFEMRiskSaleArea().length > 0) {
												StringBuffer saleArea = new StringBuffer();
												for (ProductInfoWebServiceStub.FEMRiskSaleArea fsa : fm.getFEMRiskSaleArea()) {
													City c = new City();
													if (fsa.getProvinceCode() != null && !"".equals(fsa.getProvinceCode())) {
														c = cityService.get(fsa.getProvinceCode());
													} else {
														c = cityService.get("900000");
													}
													saleArea.append(c.getPlaceName() + " ");
												}
												productMap.put(fcp.getPropertyType(), saleArea.toString());
											}
										} else if ("ERiskFeatures".equals(fcp.getPropertyType())) {
											productMap.put(fcp.getPropertyType(), fm.getERiskFeatures().replace(" ", "<br>"));
										} else {
											productMap.put(fcp.getPropertyType(), reflectMap(fm, fcp.getPropertyType()));
										}
									} catch (Exception e) {
										logger.error(e.getMessage(), e);
									}
								}
								if ("B".equals(fcp.getCompareInfoType())) {
									try {
										if (fm.getFERiskAppFactor() != null && fm.getFERiskAppFactor().length > 0) {
											for (FERiskAppFactor fraf : fm.getFERiskAppFactor()) {
												String[] argArr = {fraf.getFactorType(), fraf.getFactorTypeName(), fcp.getPropertyType()};
												logger.info("=======投保项目==={}==========={}=========对比项=={}", argArr);
												if (fraf.getFEMRiskFactorList() != null && fraf.getFEMRiskFactorList().length > 0) {
													if (fraf.getFactorType() != null && fraf.getFactorType().equals(fcp.getPropertyType())) {
														if("Plan".equals(fraf.getFactorType())){
															List<FEMRiskFactorList> splan = new ArrayList<FEMRiskFactorList>();
															for(FEMRiskFactorList femr : fraf.getFEMRiskFactorList()){
																splan.add(femr);
															}
															productMap.put("planlist", splan);
														}
														StringBuffer sb = new StringBuffer();
														
														if (fraf.getFEMRiskFactorList().length > 1 && !"Y".equalsIgnoreCase(fm.getBackUp4())) {
															//添加了 对比列属性 zhouxiang
															sb.append("<select onchange=\"getPremTrial(this.value,'" + productCode + "','" + fcp.getPropertyType()+ "','1','','','c"+(riskFactorColumnNo+1)+"');\" id=\"SELECT_"
																	+ fcp.getPropertyType() + "_" + productCode + "\">");
															for (ProductInfoWebServiceStub.FEMRiskFactorList frfl : fraf.getFEMRiskFactorList()) {
																sb.append("<option value='" + frfl.getFactorValue() + "'>");
																sb.append(frfl.getFactorDisplayValue() + "</option>");
															}
															sb.append("</select>");
														} else {
															if ("Y".equalsIgnoreCase(fm.getBackUp4())) {
																boolean flag = false;
																for (ProductInfoWebServiceStub.FEMRiskFactorList frfl : fraf.getFEMRiskFactorList()) {
																	if ("Y".equalsIgnoreCase(frfl.getDefaultValue())) {
																		sb.append(frfl.getFactorDisplayValue());
																		flag = true;
																	}
																}
																if (!flag) {
																	sb.append(fraf.getFEMRiskFactorList()[0].getFactorDisplayValue());
																}
																	
															} else {
																sb.append(fraf.getFEMRiskFactorList()[0].getFactorDisplayValue());
															}
														}
														if ("Y".equals(fraf.getFEMRiskFactorList()[0].getIsPremCalFacotor())) {
															if ("TextAge".equals(fcp.getPropertyType())) {
																String age = fraf.getFEMRiskFactorList()[0].getFactorValue();
																String startdate=PubFun.getCurrentDate();
																String birthday ="1991-01-01";
																if (age != null && !"".equals(age)) {
																	String ages[] = age.split("-");
																	/*if(ages[0].endsWith("D") || ages[0].endsWith("M") || ages[1].endsWith("D") || ages[0].endsWith("M")){
																		age = "1";
																	}*/
																	if(ages[0].endsWith("D")){ 
																		age=ages[0].substring(0, ages[0].indexOf("D"));
																		if ("0".equals(age)) {
																			age = "1";
																		} 
																		birthday = PubFun.calSDate(startdate, 0 - Integer.parseInt(age), "D");
																	}else if(ages[0].endsWith("M")){
																		age=ages[0].substring(0, ages[0].indexOf("M"));
																		if ("0".equals(age)) {
																			age = "1";
																		} 
																		birthday = PubFun.calSDate(startdate, 0 - Integer.parseInt(age), "M");
																	}else if(ages[0].endsWith("Y")){
																		age=ages[0].substring(0, ages[0].indexOf("Y"));
																		if ("0".equals(age)) {
																			age = "1";
																		} 
																		birthday = PubFun.calSDate(startdate, 0 - Integer.parseInt(age), "Y");
																	}else{
																		birthday = age;
																	}
																	rafs.add(getNewRiskAppFactor(productCode, fcp.getPropertyType(), birthday, "1", "", ""));
																}
															} else {
																rafs.add(getNewRiskAppFactor(productCode, fcp.getPropertyType(), fraf.getFEMRiskFactorList()[0].getFactorValue(), "1", "", ""));
															}
														}
														productMap.put(fcp.getPropertyType(), sb.toString());
													}
												}
											}
										}
									} catch (Exception e) {
										logger.error(e.getMessage(), e);
									}
								}
								if ("C".equals(fcp.getCompareInfoType())) {
									productMap.put("DutyFactor", fm.getFMDuty());
									// try {
									// if("DutyFactor".equals(fcp.getPropertyType())){
									// if(fm.getFMDuty()!=null&&fm.getFMDuty().length>0){
									// StringBuffer sb = new StringBuffer();
									// for(ProductInfoWebServiceStub.FMDuty fmd
									// : fm.getFMDuty()){
									// log.info("==================="+fmd.getDutyType());
									// if(fmd.getFEMDutyFactor()!=null){
									// fmd.getFEMDutyFactor().getDutyFactorCode();
									// if(fmd.getFEMDutyFactor()!=null){
									// if(fmd.getFEMDutyFactor().getFEMDutyAmntPremList()!=null&&fmd.getFEMDutyFactor().getFEMDutyAmntPremList().length>1){
									// sb.append("<li>"+fmd.getDutyName()+":"+"<select id=\""+fcp.getPropertyType()+riskFactorColumnNo+"\" onchange=\"getPremTrial('','"+productCode+"','','2',this.value,'"+fmd.getFEMDutyFactor().getFEMDutyAmntPremList()[0].getDutyFactorID()+"');\">");
									// for(ProductInfoWebServiceStub.FEMDutyAmntPremList
									// fapl :
									// fmd.getFEMDutyFactor().getFEMDutyAmntPremList()){
									// sb.append("<option value='"+fapl.getBackUp1()+"'>");
									// sb.append(fapl.getAmnt()+"</option>");
									// }
									// sb.append("</select></li>");
									// }else{
									// if(fmd.getDutyName()!=null&&fmd.getFEMDutyFactor()!=null&&fmd.getFEMDutyFactor().getFEMDutyAmntPremList()!=null&&fmd.getFEMDutyFactor().getFEMDutyAmntPremList().length>0&&fmd.getFEMDutyFactor().getFEMDutyAmntPremList()[0]!=null){
									// sb.append("<li>"+fmd.getDutyName()+":"+fmd.getFEMDutyFactor().getFEMDutyAmntPremList()[0].getAmnt()+"</li>");
									// }
									// }
									// if("Y".equals(fmd.getFEMDutyFactor().getIsPremCalFacotor())){
									// dutyFactor.add(getNewRiskAppFactor(productCode,"","","2",fmd.getFEMDutyFactor().getFEMDutyAmntPremList()[0].getBackUp1(),fmd.getFEMDutyFactor().getFEMDutyAmntPremList()[0].getDutyFactorID()));
									// }
									// }
									// }
									// }
									// productMap.put(fcp.getPropertyType(),
									// sb.toString());
									// }
									// }
									// } catch (Exception e) {
									// // TODO Auto-generated catch block
									// e.printStackTrace();
									// }
								}
							}
						}
					}
					compareInformation.add(productMap);
					riskFactorColumnNo = riskFactorColumnNo + 1;
				}
			}
		}
		returnResult.put("fmr", fmr);
		returnResult.put("rafs", rafs);
		returnResult.put("compareInformation", compareInformation);
		return returnResult;
	}

	/**
	 * 
	 * 获得对比项
	 */
	public CompareInfoServiceStub.FEMRiskCompareProperties[] getCompareInformation(String eriskType) {
		CompareInfoServiceStub.FEMRiskCompareProperties[] fcps = null;
		Map<String, Object> paramter = new HashMap<String, Object>();
		paramter.put("ERiskType", eriskType);
		try {
			CompareInfoServiceStub.CompareInfoResponse compareInformation = ProductWebservice.CompareInfoServiceImpl(paramter, null);
			fcps = compareInformation.getFEMRiskComparePropertiesList();
			logger.info("=======fcps===={}", fcps.length);
			if (fcps.length > 0) {
				for (CompareInfoServiceStub.FEMRiskCompareProperties fcp : fcps) {
					String[] argArr = {fcp.getPropertyType(), fcp.getCompareInfoType(), fcp.getPropertyTypeName()};
					logger.info("===PropertyType======{}======CompareInfoType========={}===PropertyTypeName====={}", argArr);
					fcp.getPropertyType();
					fcp.getCompareInfoType();
				}
			}
			return fcps;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 获得产品所属保险公司信息
	 */
	public FDInsCom getCompanyInformation(String comCode) {
		Map<String, Object> paramter = new HashMap<String, Object>();
		paramter.put("SupplierCode", comCode);
		try {
			FDInsComServiceStub.FDInsComResponse comIformation = ProductWebservice.FDInsComServiceImpl(paramter, null);
			logger.info("=========companylog========{}", comIformation.getFDInsComList()[0].getLogoRelaPath());
			return comIformation.getFDInsComList()[0];
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	private ProductInfoWebServiceStub.ProductInfoResponse getProductInfoResponse(String[] riskCode) {
		Map<String, Object> paramter = new HashMap<String, Object>();
		paramter.put("RiskCode", riskCode);
		try {
			ProductInfoWebServiceStub.ProductInfoResponse pifr = ProductWebservice.ProductInfoSereviceImpl(paramter, null);
			return pifr;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
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

	/**
	 * 组装投保要素
	 */
	private RiskAppFactor getNewRiskAppFactor(String productCode, String factorType, String factorValue, String flag, String amnt, String dutyCode) {
		RiskAppFactor raf = new RiskAppFactor();
		raf.setProductCode(productCode);
		raf.setFactorType(factorType);
		raf.setFactorValue(factorValue);
		raf.setFlag(flag);
		raf.setAmnt(amnt);
		raf.setDutyCode(dutyCode);
		return raf;
	}

	public Map<String, Object> createProductDuty(List<Map<String, Object>> compareIF) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<RiskAppFactor> dutyFactor = new ArrayList<RiskAppFactor>();
		List<CompareNonAuto> cmsNAs = new ArrayList<CompareNonAuto>();
		if (compareIF != null && compareIF.size() > 0) {
			for (int i = 0; i < compareIF.size(); i++) {
				Map<String, Object> map = compareIF.get(i);
				List<FEMRiskFactorList> pl= (List<FEMRiskFactorList>) map.get("planlist");
				if (map.get("DutyFactor") != null) {
					String riskCode = (String) map.get("riskCode");
					FMDuty[] fds = (FMDuty[]) map.get("DutyFactor");
					for (FMDuty fd : fds) {
						String dutyType = fd.getDutyType();
						String dutyName = fd.getDutyName();
						StringBuffer sb = new StringBuffer();
						if (fd.getFEMDutyFactor() != null) {
							// 复杂产品
							if ("Y".equals(map.get("complicatedFlag"))) {
								boolean flag = false;
								
								if(pl!=null){
									FEMRiskFactorList femr = pl.get(0);
									if ("Y".equals(fd.getFEMDutyFactor().getIsRelaRiskFactor())) {
										String amnt = "";
										for (ProductInfoWebServiceStub.FEMDutyAmntPremList fapl : fd.getFEMDutyFactor().getFEMDutyAmntPremList()) {
											if (femr.getFactorValue().equals(fapl.getAppFactorValue())) {
												if ("".equals(amnt)) {
													amnt = fapl.getAmnt().trim();
												}
												if ("Y".equalsIgnoreCase(fapl.getBackUp2())) {
													amnt = fapl.getAmnt().trim();
												}
											}
										}
										if (!"".equals(amnt)) {
											sb.append("<li><span id=\"\">" + amnt + "</span></li>");
										}
										
									} else {
										for (ProductInfoWebServiceStub.FEMDutyAmntPremList fapl : fd.getFEMDutyFactor().getFEMDutyAmntPremList()) {
											if ("Y".equalsIgnoreCase(fapl.getBackUp2())) {
												sb.append("<li><span id=\"\">" + fapl.getAmnt().trim() + "</span></li>");
											}
										}
										if (!flag) {
											sb.append("<li><span id=\"\">" + fd.getFEMDutyFactor().getFEMDutyAmntPremList()[0].getAmnt().trim() + "</span></li>");
										}
									}
								} else {
									for (ProductInfoWebServiceStub.FEMDutyAmntPremList fapl : fd.getFEMDutyFactor().getFEMDutyAmntPremList()) {
										if ("Y".equalsIgnoreCase(fapl.getBackUp2())) {
											sb.append("<li><span id=\"\">" + fapl.getAmnt().trim() + "</span></li>");
										}
									}
									if (!flag) {
										sb.append("<li><span id=\"\">" + fd.getFEMDutyFactor().getFEMDutyAmntPremList()[0].getAmnt().trim() + "</span></li>");
									}
								}
								
								
							} else {
								if (fd.getFEMDutyFactor().getFEMDutyAmntPremList() != null && pl!=null && pl.size()>1) {
									
									sb.append("<li> ");
									if(pl!=null){
										for(FEMRiskFactorList femr : pl){
											boolean flag = findIsExist(femr,fd.getFEMDutyFactor().getFEMDutyAmntPremList());
											if(!flag){
												String spanID = riskCode + "_PLAN"+(i+1)+"_" + femr.getAppFactorCode() + "_" + femr.getFactorValue();
												sb.append("<span  id=\"").append(spanID).append("\" style=\"display:none;\">");
												sb.append("-</span>");
											}
										}
									}
									//添加了 对比列属性 zhouxiang
									for (ProductInfoWebServiceStub.FEMDutyAmntPremList fapl : fd.getFEMDutyFactor().getFEMDutyAmntPremList()) {
										String spanID = riskCode + "_PLAN"+(i+1)+"_" + fapl.getAppFactorCode() + "_" + fapl.getAppFactorValue();
										sb.append("<span  id=\"").append(spanID).append("\" style=\"display:none;\">");
										sb.append(fapl.getAmnt().trim() + "</span>");
									}
									sb.append(" </li>");
								} else {
									if (fd.getDutyName() != null && fd.getFEMDutyFactor() != null && fd.getFEMDutyFactor().getFEMDutyAmntPremList() != null
											&& fd.getFEMDutyFactor().getFEMDutyAmntPremList().length > 0 && fd.getFEMDutyFactor().getFEMDutyAmntPremList()[0] != null) {
										sb.append("<li><span id=\"\">" + fd.getFEMDutyFactor().getFEMDutyAmntPremList()[0].getAmnt().trim() + "</span></li>");
									}
								}
							}
							
							if ("Y".equals(fd.getFEMDutyFactor().getIsPremCalFacotor())) {
								if(fd.getFEMDutyFactor()!=null){
									if(fd.getFEMDutyFactor().getFEMDutyAmntPremList()!=null&&fd.getFEMDutyFactor().getFEMDutyAmntPremList().length>0){
										if(fd.getFEMDutyFactor().getFEMDutyAmntPremList()[0]!=null){
											dutyFactor.add(getNewRiskAppFactor(riskCode, "", "", "2", fd.getFEMDutyFactor().getFEMDutyAmntPremList()[0].getBackUp1(), fd.getFEMDutyFactor()
													.getFEMDutyAmntPremList()[0].getDutyFactorID()));
										}
									}
								}
							}
						}
						if (dutyType != null && !"".equals(dutyType) && i == 0 && sb.toString() != null && !"".equals(sb.toString())) {
							CompareNonAuto c = findNoAutoIsE(dutyType, cmsNAs , dutyName);
							if (c != null) {
								c.setInformationA("<ul>" + sb.toString().trim() + "</ul>");
							} else {
								CompareNonAuto comNA = new CompareNonAuto();
								comNA.setCompareName(dutyName);
								comNA.setInformationA("<ul>" + sb.toString().trim() + "</ul>");
								comNA.setInformationB("-");
								comNA.setInformationC("-");
								comNA.setInformationD("-");
								comNA.setType(dutyType);
								cmsNAs.add(comNA);
								
							}
						}
						if (dutyType != null && !"".equals(dutyType) && i == 1 && sb.toString() != null && !"".equals(sb.toString())) {
							CompareNonAuto c = findNoAutoIsE(dutyType, cmsNAs, dutyName);
							if (c != null) {
								c.setInformationB("<ul>" + sb.toString().trim() + "</ul>");
							} else {
								CompareNonAuto comNA = new CompareNonAuto();
								comNA.setCompareName(dutyName);
								comNA.setInformationA("-");
								comNA.setInformationB("<ul>" + sb.toString().trim() + "</ul>");
								comNA.setInformationC("-");
								comNA.setInformationD("-");
								comNA.setType(dutyType);
								cmsNAs.add(comNA);
							}
						}
						if (dutyType != null && !"".equals(dutyType) && i == 2 && sb.toString() != null && !"".equals(sb.toString())) {
							CompareNonAuto c = findNoAutoIsE(dutyType, cmsNAs, dutyName);
							if (c != null) {
								c.setInformationC("<ul>" + sb.toString().trim() + "</ul>");
							} else {
								CompareNonAuto comNA = new CompareNonAuto();
								comNA.setCompareName(dutyName);
								comNA.setInformationA("-");
								comNA.setInformationB("-");
								comNA.setInformationC("<ul>" + sb.toString().trim() + "</ul>");
								comNA.setInformationD("-");
								comNA.setType(dutyType);
								cmsNAs.add(comNA);
							}
						}
						if (dutyType != null && !"".equals(dutyType) && i == 3 && sb.toString() != null && !"".equals(sb.toString())) {
							CompareNonAuto c = findNoAutoIsE(dutyType, cmsNAs, dutyName);
							if (c != null) {
								c.setInformationD("<ul>" + sb.toString().trim() + "</ul>");
							} else {
								CompareNonAuto comNA = new CompareNonAuto();
								comNA.setCompareName(dutyName);
								comNA.setInformationA("-");
								comNA.setInformationB("-");
								comNA.setInformationC("-");
								comNA.setInformationD("<ul>" + sb.toString().trim() + "</ul>");
								comNA.setType(dutyType);
								cmsNAs.add(comNA);
							}
						}
					}
				}

			}
		}
		resultMap.put("compareDutyList", cmsNAs);
		resultMap.put("dutyFactor", dutyFactor);
		return resultMap;
	}

	private boolean findIsExist(FEMRiskFactorList f,
			FEMDutyAmntPremList[] femDutyAmntPremList) {
		for(FEMDutyAmntPremList fap : femDutyAmntPremList){
			if(f.getFactorValue().equals(fap.getAppFactorValue())){
				return true;
			}
		}
		return false;
	}

	private CompareNonAuto findNoAutoIsE(String dutyType, List<CompareNonAuto> comNs , String dutyName) {
		if (comNs != null && comNs.size() > 0) {
			for (CompareNonAuto c : comNs) {
				if (dutyType.equals(c.getType()) && dutyName.equals(c.getCompareName())) {
					return c;
				}
			}
		}
		return null;
	}
}
