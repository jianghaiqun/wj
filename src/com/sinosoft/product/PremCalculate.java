package com.sinosoft.product;

import cn.com.sinosoft.service.impl.OrderConfigNewServiceImpl;
import com.sinosoft.framework.Ajax;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.IntegralAction;
import com.sinosoft.webservice.ProductWebservice;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FERiskAppFactor;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FMRisk;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.ProductInfoResponse;
import com.sinosoft.webservice.productPrem.ProductPremServiceStub.CalProductPrem;
import com.sinosoft.webservice.productPrem.ProductPremServiceStub.FEMRiskFactorList;
import com.sinosoft.webservice.productPrem.ProductPremServiceStub.ProductPremResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PremCalculate extends Ajax {
	private static final Logger logger = LoggerFactory.getLogger(PremCalculate.class);
	@SuppressWarnings("unchecked")
	public void premDoCal() {
		Iterator<String> i = Request.keySet().iterator();

		String key = "";
		Map<String, String> map = new HashMap<String, String>();
		while (i.hasNext()) {
			key = i.next();
			if (key.startsWith("Cookie.") || key.startsWith("Header.")) {
				continue;
			}
			map.put(key, Request.getString(key));
			logger.info("{}:{}", key, Request.getString(key));
		}
		try {
			String riskcode = map.get("RiskCode");
			if (riskcode != null && !"".equals(riskcode.trim())) {
				CalProductPrem[] calProductPrem = new CalProductPrem[1];
				Iterator<String> itr = map.keySet().iterator();
				int jCount = 0;
				while (itr.hasNext()) {
					calProductPrem[jCount] = new CalProductPrem();
					calProductPrem[jCount].setRiskCode(riskcode);
					if ("DutyId".equals(key.split("_")[0])) {
						calProductPrem[jCount].setDutyCode(key.split("_")[1]);
						calProductPrem[jCount].setAmnt(map.get(key));
					}
					FEMRiskFactorList[] fEMRiskFactorList = new FEMRiskFactorList[map.size() - 1];
					int iCount = 0;
					while (itr.hasNext()) {
						key = itr.next();
						if ("RiskCode".equals(key)) {
							continue;
						}
						fEMRiskFactorList[iCount] = new FEMRiskFactorList();
						fEMRiskFactorList[iCount].setAppFactorCode(key.split("_")[0]);
						fEMRiskFactorList[iCount].setFactorType(key.split("_")[1]);
						fEMRiskFactorList[iCount].setFactorValue(map.get(key));
						if ("TextAge".equals(fEMRiskFactorList[iCount].getFactorType())) {
							String age = map.get(key);
							if (StringUtil.isNotEmpty(age)) {
								String birthday = new OrderConfigNewServiceImpl().getBrithdayByFactor(null, age);
								fEMRiskFactorList[iCount].setFactorValue(birthday);
							}
						}
						fEMRiskFactorList[iCount].setIsPremCalFacotor("Y");
						iCount++;
					}
					calProductPrem[jCount].setFEMRiskFactorList(fEMRiskFactorList);
					jCount++;
				}

				Map<String, Object> mp = new HashMap<String, Object>();
				mp.put("CalProductPrem", changeAotuPeriod(calProductPrem));
				ProductPremResponse ProductPrem = ProductWebservice.ProductPremSereviceImpl(mp, null);
				String[] prems = ProductPrem.getPrem();
				double[] rates = ProductPrem.getFeeRate();
				String[] DiscountPrice = ProductPrem.getDiscountPrice();

				String sql = "select ProductType from sdproduct where productid='" + riskcode + "'";
				GetDBdata db = new GetDBdata();
				List<HashMap<String, String>> a = db.query(sql);
				double tPrems = Double.parseDouble(prems[0]);
				if (!riskcode.startsWith("1015")) {
					if (prems != null && prems.length > 0 && tPrems > 0) {
						Response.setStatus(1);
						$S("productPrem", "" + DiscountPrice[0]);// 现价
						$S("productRatePrem", tPrems);// 原价

						if (rates != null && rates.length > 0 && rates[0] > 0 && a.size() > 0 && a.get(0) != null && DiscountPrice != null && DiscountPrice.length > 0) {
							$S("productIntegral", "" + getIntegral(Double.parseDouble(DiscountPrice[0]), rates[0], a.get(0).get("ProductType")));
						} else {
							$S("productIntegral", "");
						}
					}
				} else {
					String DiscountTotalPrice = ProductPrem.getDiscountTotalPrice();
					String TotalPrice = ProductPrem.getTotalPrice();
					if (StringUtil.isNotEmpty(DiscountTotalPrice) && StringUtil.isNotEmpty(TotalPrice)) {
						Response.setStatus(1);
						$S("productPrem", DiscountTotalPrice);// 现价
						$S("productRatePrem", TotalPrice);// 原价

						if (rates != null && rates.length > 0 && rates[0] > 0 && a.get(0) != null && DiscountPrice != null && DiscountPrice.length > 0) {
							$S("productIntegral", "" + getIntegral(Double.parseDouble(DiscountTotalPrice), rates[0], a.get(0).get("ProductType")));
						} else {
							$S("productIntegral", "");
						}
					}
				}
			}
		} catch (Exception e) {
			Response.setStatus(0);
			$S("productPrem", "");
			logger.error("保费计算失败：" + e.getMessage(), e);
		}
	}

	private CalProductPrem[] changeAotuPeriod(CalProductPrem[] calProductPrem) throws Exception {
		for (CalProductPrem cal : calProductPrem) {
			for (FEMRiskFactorList fEMRiskFactorList : cal.getFEMRiskFactorList()) {
				if ("Period".equals(fEMRiskFactorList.getFactorType())) {
					String periodvalue = fEMRiskFactorList.getFactorValue();
					if (periodvalue.endsWith("#")) {
						fEMRiskFactorList.setFactorValue(getPeriod(periodvalue, cal.getRiskCode()));
					}
					return calProductPrem;
				}
			}
		}
		return calProductPrem;
	}

	public static String[] getProS(String productId){
		QueryBuilder qbsales = new QueryBuilder("select b.SplitRiskCode from sdproduct b where b.ProductID = ?",productId);
		DataTable dtsales = qbsales.executeDataTable();
		int tLen  =dtsales.getRowCount();
		String[] pro = null;
		if (dtsales!=null && tLen>=1) {
			String tSplitRiskCode = dtsales.getString(0, "SplitRiskCode");
			if(StringUtil.isNotEmpty(tSplitRiskCode)){
				if(tSplitRiskCode.indexOf(",")!=-1){
					String[] src_plan = tSplitRiskCode.split(",");
					pro = new String[src_plan.length];
					for(int j=0;j<src_plan.length;j++){
						if(src_plan[j].indexOf("-")!=-1){
							String[] src = src_plan[j].split("-");
							String cProductId = src[0];
							pro[j]=cProductId;
						}
					}
				}
			}else{
				pro = new String[]{productId};
			}
		}else{
			pro = new String[]{productId};
		}
		return pro;
	}
	public static String getPeriod(String period, String productId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		// 拆分后計劃對比顯示保險期間，只取一款產品的即可
		map.put("RiskCode",new String[]{productId});
		ProductInfoResponse productInfo = ProductWebservice.ProductInfoSereviceImpl(map, null);
		FMRisk[] list = productInfo.getFMRisk();
		if (list != null && list.length > 0 && list[0] != null) {
			try {
				FMRisk fmRisk = list[0];
				FERiskAppFactor[] appFactors = fmRisk.getFERiskAppFactor();
				for (FERiskAppFactor app : appFactors) {
					if ("Period".equals(app.getFactorType())) {
						com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FEMRiskFactorList[] femRiskFactorList = app.getFEMRiskFactorList();

						int day = 0;
						if (period != null && period.indexOf("-") != -1) {
							period = period.substring(period.indexOf("-") + 1);
							day = Integer.valueOf(period.substring(0, period.length() - 1));

						} else {
							day = Integer.valueOf(period.substring(0, period.length() - 1));

						}

						int len = femRiskFactorList.length;
						String[] arr = null;
						for (int i = 0; i < len; i++) {
							String str = femRiskFactorList[i].getFactorValue();
							arr = str.split("-");
							if (arr.length == 1) {
								if (day <= getDay(arr[0])) {
									return str;
								}
							} else {
								if (day <= getDay(arr[1])) {
									return str;
								}
							}
						}
					}
				}
			} catch (Exception e) {
				logger.error("日期格式化异常：  -  period:" + period +" productId:" + productId + e.getMessage(), e);

			}
			
		} else {
			throw new NullPointerException();
		}
		return period;
	}

	private static int getDay(String period) {
		int size = period.length();
		if (size == 0) {
			return 0;
		}
		if (period.substring(size - 1, size).equals("D")) {
			return Integer.valueOf(period.substring(0, size - 1));
		} else if (period.substring(size - 1, size).equals("Y")) {
			return Integer.valueOf(period.substring(0, size - 1)) * 365;
		} else if (period.substring(size - 1, size).equals("M")) {
			return Integer.valueOf(period.substring(0, size - 1)) * 30;
		}
		return 0;
	}

	/**
	 * 调用积分
	 * 
	 * @param tPrem
	 * @param tRate
	 * @param productType
	 * @return
	 */
	private int getIntegral(double tPrem, double tRate, String productType) {
		try {
			if (StringUtil.isEmpty(productType)) {
				logger.info("调用积分规则险类productType：{}", productType);
				return 0;
			}

			IntegralAction iA = new IntegralAction();
			int result = iA.paySuccess(tRate, tPrem, productType);
			Object[] argArr = {tRate, tPrem, productType, result};
			logger.info("FeeRate(初始手续费率){} - InitPrem(初始定价) {}险类{} 积分：{}", argArr);
			return result;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return 0;
		}
	}

}
