package com.sinosoft.webservice;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.utility.LogUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.utility.DataCache;
import com.sinosoft.webservice.compareInfo.CompareInfoServiceStub;
import com.sinosoft.webservice.dutyPrem.DutyPremServiceStub;
import com.sinosoft.webservice.fDInsCom.FDInsComServiceStub;
import com.sinosoft.webservice.productCode.ProductCodeWebServiceStub;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FEMSearchRelaList;
import com.sinosoft.webservice.productPrem.ProductPremServiceStub;
import com.sinosoft.webservice.productPrem.ProductPremServiceStub.CalProductPrem;
import com.sinosoft.webservice.searchInfo.SearchInfoServiceStub;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Map;

public class ProductWebservice {
	private static final Logger logger = LoggerFactory.getLogger(ProductWebservice.class);

	/**
	 * 保险公司接口
	 * 
	 * @param paramter
	 * @param orderField
	 * @return
	 * @throws Exception
	 */
	public static FDInsComServiceStub.FDInsComResponse FDInsComServiceImpl(Map<String, Object> paramter, String orderField) throws Exception {

		String url = Config.interfaceMap.getString("FDInsComService");
//		String url = "http://120.55.73.0:8081/product/services/FDInsComService";
		String comCode = validateObj(paramter.get("SupplierCode"));
		Object obj = DataCache.getCompanyInfo(comCode);
		FDInsComServiceStub.FDInsComResponse re;
		if (obj != null) {
			re = (FDInsComServiceStub.FDInsComResponse) obj;
			return re;
		} else {
			try {
				FDInsComServiceStub fDInsComServiceStub = new FDInsComServiceStub(url);
				fDInsComServiceStub._getServiceClient().getOptions().setProperty(org.apache.axis2.transport.http.HTTPConstants.CHUNKED,Boolean.FALSE);
				
				FDInsComServiceStub.GetFDInsCom getFDInsCom = new FDInsComServiceStub.GetFDInsCom();
				FDInsComServiceStub.FDInsComRequest fDInsComRequest = new FDInsComServiceStub.FDInsComRequest();
				fDInsComRequest.setRequestType("PE001");
				fDInsComRequest.setRiskCode(validateObj(paramter.get("RiskCode")));// 险种代码
				fDInsComRequest.setRiskName(validateObj(paramter.get("RiskName")));// 产品名称
				// 产品电商大类别 A 旅游保险 B 意外保险 C 家财保险 D 健康保险 E 人寿保险 F 车辆保险
				fDInsComRequest.setERiskType(validateObj(paramter.get("ERiskType")));

				fDInsComRequest.setSupplierCode(validateObj(paramter.get("SupplierCode")));// 保险公司编码
				fDInsComRequest.setSupplierName(validateObj(paramter.get("SupplierName")));// 保险公司名称
				// 保险公司类别
				fDInsComRequest.setInsType(validateObj(paramter.get("InsType")));

				FDInsComServiceStub.RequestExtension requestExtension = new FDInsComServiceStub.RequestExtension();
				if (orderField != null && !"".equals(orderField)) {
					requestExtension.setOrderField(orderField);// 排序字段
				}
				fDInsComRequest.setRequestExtension(requestExtension);

				getFDInsCom.setFDInsComRequest(fDInsComRequest);

				re = fDInsComServiceStub.getFDInsCom(getFDInsCom).get_return();
				FDInsComServiceStub.ResultDTO rd = re.getResultDTO();
				logger.info("保险公司接口(ProductWebservice.FDInsComServiceImpl)   code:{} desc:{}",rd.getResultCode(), rd.getResultInfoDesc());

				// 0 成功 1 失败
				if ("0".equals(rd.getResultCode())) {
					DataCache.setCompanyInfo(comCode, obj);
					return re;
				} else {
					throw new Exception(rd.getResultInfoDesc());
				}

			} catch (Exception e) {
				throw e;
			}
		}
	}

	/**
	 * 产品信息接口
	 * 
	 * @param paramter
	 * @param orderField
	 * @return
	 * @throws Exception
	 */
	public static ProductInfoWebServiceStub.ProductInfoResponse ProductInfoSereviceImpl(Map<String, Object> paramter, String orderField) throws Exception {
		try {
//			String url = "http://120.55.73.0:8081/product/services/ProductInfoWebService";
			String url = Config.interfaceMap.getString("ProductInfoWebService");
			//String url =  "http://localhost:8081/product/services/ProductInfoWebService";
			String[] productCodeInfo = (String[]) paramter.get("RiskCode");
			String productCode = "";
			if (productCodeInfo != null && productCodeInfo.length > 0) {
				Arrays.sort(productCodeInfo);
				for (int i = 0; i < productCodeInfo.length; i++) {
					productCode += String.valueOf(productCodeInfo[i]);
				}
			}
			ProductInfoWebServiceStub.ProductInfoResponse response;
			String ProductInfo_key = String.valueOf(paramter.get("ComCode_RiskType"));
			String Info_key = String.valueOf(paramter.get("SupplierCode")) + "_" + String.valueOf(paramter.get("SubRiskTypeCode"));
			Object obj = DataCache.getProductInfo(productCode);
			if (obj != null) {
				response = (ProductInfoWebServiceStub.ProductInfoResponse) obj;
				return response;
			} else {

				obj = DataCache.getProductInfoByCom(Info_key);
				if (obj != null) {
					if ("ComCode_RiskType".equals(ProductInfo_key)) {
						response = (ProductInfoWebServiceStub.ProductInfoResponse) obj;
						return response;
					}
				}
				ProductInfoWebServiceStub pisStub = new ProductInfoWebServiceStub(url);
				pisStub._getServiceClient().getOptions().setProperty(org.apache.axis2.transport.http.HTTPConstants.CHUNKED,Boolean.FALSE);
				
				ProductInfoWebServiceStub.GetProductInfo getProductInfo = new ProductInfoWebServiceStub.GetProductInfo();
				ProductInfoWebServiceStub.ProductInfoRequest request = new ProductInfoWebServiceStub.ProductInfoRequest();
				request.setRequestType("PE002");
				if (null != paramter.get("RiskCode")) {
					request.setRiskCode((String[]) paramter.get("RiskCode"));// 险种代码
				}
				if (null != paramter.get("BU1")) {
					request.setBU1(validateObj(paramter.get("BU1")));// 判断是否是赠险
				}
				request.setRiskName(validateObj(paramter.get("RiskName")));// 产品名称
				request.setSupplierCode(validateObj(paramter.get("SupplierCode")));// 所属保险公司编码
				request.setOutRiskCode(validateObj(paramter.get("OutRiskCode")));// 保险公司产品编码
				request.setRiskType(validateObj(paramter.get("RiskType")));// 财寿险标志
				request.setMainRiskFlag(validateObj(paramter.get("MainRiskFlag")));// 主附险标志
				request.setRiskPeriod(validateObj(paramter.get("RiskPeriod")));// 长短险标志
				request.setRiskProp(validateObj(paramter.get("RiskProp")));// 团个险标志
				request.setVersion(validateObj(paramter.get("Version")));// 险种版本
				request.setMainFlag(validateObj(paramter.get("MainFlag")));// 保监险种类别
				request.setRiskKind(validateObj(paramter.get("RiskKind")));// 内部统计险种类别
				// request.setRiskTypeCode
				// (validateObj(paramter.get("RiskTypeCode")));// 内部统计险种子类别
				request.setERiskType(validateObj(paramter.get("ERiskType")));// 电商险种类别
				request.setSubRiskTypeCode(validateObj(paramter.get("SubRiskTypeCode")));// 电商险种子类别代码
				request.setCommendType(validateObj(paramter.get("CommendType")));// 推荐类型
				request.setERiskType(validateObj(paramter.get("ERiskType")));// 推荐分类类型
				request.setSex(validateObj(paramter.get("Sex")));
				if (paramter.get("FEMSearchRelaList") != null) {
					request.setRequestType("PE002002");
					// 筛选条件值列表
					request.setFEMSearchRelaList((FEMSearchRelaList[]) paramter.get("FEMSearchRelaList"));
				}

				ProductInfoWebServiceStub.RequestExtension requestExtension = new ProductInfoWebServiceStub.RequestExtension();
				if (orderField != null && !"".equals(orderField)) {
					requestExtension.setOrderField(orderField);// 排序字段
				}
				request.setRequestExtension(requestExtension);
				getProductInfo.setProductInfoRequest(request);

				response = pisStub.getProductInfo(getProductInfo).get_return();
				ProductInfoWebServiceStub.ResultDTO rd = response.getResultDTO();
				logger.info("产品信息接口(ProductWebservice.ProductInfoSereviceImpl)   code:{} desc:{}",rd.getResultCode(), rd.getResultInfoDesc());

				// 0 成功 1 失败
				if ("0".equals(rd.getResultCode())) {
					if (StringUtil.isNotEmpty(productCode)) {
						DataCache.setProductInfo(productCode, response);
					}
					if (StringUtil.isNotEmpty(ProductInfo_key)) {
						DataCache.setProductInfoByCom(Info_key, response);
					}
					return response;
				} else {
					return null;
				}

			}

		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 产品保费计算接口
	 * 
	 * @param paramter
	 * @param orderField
	 * @return
	 * @throws Exception
	 */
	public static ProductPremServiceStub.ProductPremResponse ProductPremSereviceImpl(Map<String, Object> paramter, String orderField) throws Exception {
		try {
			String url = Config.interfaceMap.getString("ProductPremService");
			// String url =  "http://120.55.73.0:8081/product/services/ProductPremService?wsdl";
			ProductPremServiceStub pps = new ProductPremServiceStub(url);
			pps._getServiceClient().getOptions().setProperty(org.apache.axis2.transport.http.HTTPConstants.CHUNKED,Boolean.FALSE);
			
			ProductPremServiceStub.GetProductPrem getProductPem = new ProductPremServiceStub.GetProductPrem();
			ProductPremServiceStub.ProductPremRequest request = new ProductPremServiceStub.ProductPremRequest();
			request.setRequestType("PE003");
			request.setCalProductPrem((CalProductPrem[]) paramter.get("CalProductPrem"));// 计算投保要素列表
			ProductPremServiceStub.RequestExtension requestExtension = new ProductPremServiceStub.RequestExtension();
			if (orderField != null && !"".equals(orderField)) {
				requestExtension.setOrderField(orderField);// 排序字段
			}
			request.setRequestExtension(requestExtension);
			getProductPem.setProductPremRequest(request);
			ProductPremServiceStub.ProductPremResponse re = pps.getProductPrem(getProductPem).get_return();
			ProductPremServiceStub.ResultDTO rd = re.getResultDTO();
			logger.info("产品保费计算接口(ProductWebservice.ProductPremSereviceImpl)   code:{} desc:{}",rd.getResultCode(), rd.getResultInfoDesc());

			// 0 成功 1 失败
			if ("0".equals(rd.getResultCode())
					|| "2".equals(rd.getResultCode())
					|| "3".equals(rd.getResultCode())) {
				return re;
			} else {
				throw new Exception(rd.getResultInfoDesc());
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 责任保费计算信息接口
	 * 
	 * @param paramter
	 * @param orderField
	 * @return
	 * @throws Exception
	 */
	public static DutyPremServiceStub.DutyPremResponse DutyPremServiceImpl(Map<String, Object> paramter, String orderField) throws Exception {
		try {
			String url = Config.interfaceMap.getString("DutyPremService");
			DutyPremServiceStub dps = new DutyPremServiceStub(url);
			dps._getServiceClient().getOptions().setProperty(org.apache.axis2.transport.http.HTTPConstants.CHUNKED,Boolean.FALSE);
			
			DutyPremServiceStub.GetDutyPrem getDutyPrem = new DutyPremServiceStub.GetDutyPrem();
			DutyPremServiceStub.DutyPremRequest request = new DutyPremServiceStub.DutyPremRequest();
			request.setRequestType("PE004");
			request.setDutyFactorCode(validateObj(paramter.get("DutyFactorCode")));
			request.setAppFactorCode(validateObj(paramter.get("AppFactorCode")));
			request.setAppFactorValue(validateObj(paramter.get("AppFactorCode")));
			request.setAmnt(validateObj(paramter.get("Amn")));
			request.setPayPeriod(validateObj(paramter.get("PayPeriod")));
			DutyPremServiceStub.RequestExtension requestExtension = new DutyPremServiceStub.RequestExtension();
			if (orderField != null && !"".equals(orderField)) {
				requestExtension.setOrderField(orderField);// 排序字段
			}
			request.setRequestExtension(requestExtension);
			getDutyPrem.setRequest(request);
			DutyPremServiceStub.DutyPremResponse re = dps.getDutyPrem(getDutyPrem).get_return();
			DutyPremServiceStub.ResultDTO rd = re.getResultDTO();
			logger.info("责任保费计算信息接口(ProductWebservice.DutyPremServiceImpl)   code:{} desc:{}",
					rd.getResultCode(), rd.getResultInfoDesc());

			// 0 成功 1 失败
			if ("0".equals(rd.getResultCode())) {
				return re;
			} else {
				throw new Exception(rd.getResultInfoDesc());
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 产品对比接口
	 * 
	 * @param paramter
	 * @param orderField
	 * @return
	 * @throws Exception
	 */
	public static CompareInfoServiceStub.CompareInfoResponse CompareInfoServiceImpl(Map<String, Object> paramter, String orderField) throws Exception {
		try {
			String url = Config.interfaceMap.getString("CompareInfoService");
			String riskType = validateObj(paramter.get("ERiskType"));
			Object obj = DataCache.getRiskTypeInfo(riskType);
			CompareInfoServiceStub.CompareInfoResponse re;
			if (obj != null) {
				re = (CompareInfoServiceStub.CompareInfoResponse) obj;
				return re;
			} else {
				CompareInfoServiceStub gis = new CompareInfoServiceStub(url);
				gis._getServiceClient().getOptions().setProperty(org.apache.axis2.transport.http.HTTPConstants.CHUNKED,Boolean.FALSE);
				
				CompareInfoServiceStub.GetCompareInfo getCompareInfo = new CompareInfoServiceStub.GetCompareInfo();
				CompareInfoServiceStub.CompareInfoRequest request = new CompareInfoServiceStub.CompareInfoRequest();
				request.setRequestType("PE005");
				request.setERiskType(validateObj(paramter.get("ERiskType")));
				CompareInfoServiceStub.RequestExtension requestExtension = new CompareInfoServiceStub.RequestExtension();
				if (orderField != null && !"".equals(orderField)) {
					requestExtension.setOrderField(orderField);// 排序字段
				}
				request.setRequestExtension(requestExtension);
				getCompareInfo.setFCompareInfoRequest(request);
				re = gis.getCompareInfo(getCompareInfo).get_return();
				CompareInfoServiceStub.ResultDTO rd = re.getResultDTO();
				logger.info("产品对比接口(ProductWebservice.CompareInfoServiceImpl)   code:{} desc:{}",
						rd.getResultCode(), rd.getResultInfoDesc());

				// 0 成功 1 失败
				if ("0".equals(rd.getResultCode())) {
					DataCache.setRiskTypeInfo(riskType, re);
					return re;
				} else {
					throw new Exception(rd.getResultInfoDesc());
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 筛选页面接口
	 * 
	 * @param paramter
	 * @param orderField
	 * @return
	 * @throws Exception
	 */
	public static SearchInfoServiceStub.SearchInfoResponse SearchInfoServiceImpl(Map<String, Object> paramter, String orderField) throws Exception {
		try {
			String url = Config.interfaceMap.getString("SearchInfoService");
			SearchInfoServiceStub sis = new SearchInfoServiceStub(url);
			sis._getServiceClient().getOptions().setProperty(org.apache.axis2.transport.http.HTTPConstants.CHUNKED,Boolean.FALSE);
			
			SearchInfoServiceStub.GetSearchInfo getSearchInfo = new SearchInfoServiceStub.GetSearchInfo();
			SearchInfoServiceStub.SearchInfoRequest request = new SearchInfoServiceStub.SearchInfoRequest();
			request.setRequestType("PE006");
			request.setERiskSubType(validateObj(paramter.get("ERiskSubType")));
			SearchInfoServiceStub.RequestExtension requestExtension = new SearchInfoServiceStub.RequestExtension();
			if (orderField != null && !"".equals(orderField)) {
				requestExtension.setOrderField(orderField);// 排序字段
			}
			request.setRequestExtension(requestExtension);
			getSearchInfo.setSearchInfoRequest(request);
			SearchInfoServiceStub.SearchInfoResponse re = sis.getSearchInfo(getSearchInfo).get_return();
			SearchInfoServiceStub.ResultDTO rd = re.getResultDTO();
			logger.info("筛选页面接口(ProductWebservice.SearchInfoServiceImpl)   code:{} desc:{}", rd.getResultCode(), rd.getResultInfoDesc());
			// 0 成功 1 失败
			if ("0".equals(rd.getResultCode())) {
				return re;
			} else {
				throw new Exception(rd.getResultInfoDesc());
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 产品编码列表接口
	 * 
	 * @param paramter
	 * @return
	 * @throws Exception
	 */
	public static ProductCodeWebServiceStub.ProductCodeResponse ProductCodeServiceImpl(
			Map<String, Object> paramter) throws Exception {
		try {
			String url = Config.interfaceMap.getString("ProductCodeWebService");
			ProductCodeWebServiceStub stub = new ProductCodeWebServiceStub(url);
			stub._getServiceClient()
					.getOptions()
					.setProperty(
							org.apache.axis2.transport.http.HTTPConstants.CHUNKED,
							Boolean.FALSE);
			ProductCodeWebServiceStub.GetProductCode getProductCode = new ProductCodeWebServiceStub.GetProductCode();
			ProductCodeWebServiceStub.ProductCodeRequest request = new ProductCodeWebServiceStub.ProductCodeRequest();
			request.setRequestType("PE007");
			// 渠道：个险（I）、团险（G）、代理人（F）
			request.setRiskProp(validateObj(paramter.get("RiskProp")));
			// 保险公司编码
			request.setSupplierCode(validateObj(paramter.get("SupplierCode")));
			// 电商子类
			request.setBU1(validateObj(paramter.get("SubRiskTypeCode")));
			// 产品编码
			if (null != paramter.get("RiskCode")) {
				request.setRiskCode((String[]) paramter.get("RiskCode"));
			}
			getProductCode.setProductCodeRequest(request);
			ProductCodeWebServiceStub.ProductCodeResponse re = stub
					.getProductCode(getProductCode).get_return();
			
			return re;
		} catch (Exception e) {
			throw e;
		}
	}
	
	private static String validateObj(Object temp) {
		if (temp == null || "".equals(temp) || "null".equalsIgnoreCase(temp + ""))
			return null;
		else
			return String.valueOf(temp);
	}

	public static void main(String[] args) {

//		Map<String, Object> paramter = new HashMap<String, Object>();
//		try {

			// // 保险公司接口
			// paramter = new HashMap<String, Object>();
			// FDInsComServiceStub.FDInsComResponse re1 =
			// FDInsComServiceImpl(paramter, null);
			// 产品信息接口
			// paramter = new HashMap<String, Object>();
			// String[] riskCode = new String[] { "106101074" };
			// paramter.put("RiskCode", riskCode);
			// ProductInfoWebServiceStub.ProductInfoResponse re2 =
			// ProductInfoSereviceImpl(
			// paramter, null);
			// for (int i = 0; i < re2.getFMRisk().length; i++) {
			// System.out.println("产品编码：" + re2.getFMRisk()[i].getRiskCode());
			// System.out.println("产品名称：" + re2.getFMRisk()[i].getRiskName());
			// for (int j = 0; j < re2.getFMRisk()[i].getFEMRiskType().length;
			// j++) {
			//
			// System.out.println("产品类别："
			// + re2.getFMRisk()[i].getFEMRiskType()[j]
			// .getERiskType());
			// }
			// }

			// 对比接口
			// paramter = new HashMap<String, Object>();
			// paramter.put("ERiskType", "A");
			// CompareInfoServiceStub.CompareInfoResponse re5 =
			// CompareInfoServiceImpl(paramter, null);
			// CompareInfoServiceStub.FEMRiskCompareProperties[] re555 =
			// re5.getFEMRiskComparePropertiesList();
			// for(int i=0;i<re555.length;i++){
			// System.out.println("=CompareInfoType===="
			// +re555[i].getCompareInfoType());
			// System.out.println("=getCompareInfoTypeName===="
			// +re555[i].getCompareInfoTypeName());
			// System.out.println("=PropertyType===="
			// +re555[i].getPropertyType());
			// System.out.println("=PropertyTypeName===="
			// +re555[i].getPropertyTypeName());
			// }
			// // 筛选页面接口
			// paramter = new HashMap<String, Object>();
			// paramter.put("ERiskSubType", "A01");
			// CompareInfoServiceStub.CompareInfoResponse re6 =
			// CompareInfoServiceImpl(paramter, null);

//			Map<String, Object> mp = new HashMap<String, Object>();
//			mp.put("TextAge", "1Y-17Y");
//			mp.put("Period", "11D-14D");
//			mp.put("Plan", "Plan_1");
//			ProductPremResponse ProductPrem = ProductWebservice.ProductPremSereviceImpl(mp, null);
//			String[] prems = ProductPrem.getPrem();
//			System.out.println(prems);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

	}
}
