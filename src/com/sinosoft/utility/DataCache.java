package com.sinosoft.utility;

import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DataCache {
	private static final Logger logger = LoggerFactory.getLogger(DataCache.class);

	private static Map<String, Map<String, Object>> cacheMap;

	public static final String PRODUCTINFO = "_Product_Info";//产品信息
	public static final String COMPANYINFO = "_Company_Info";//保险公司信息
	public static final String RISKTYPEINFO = "_RiskType_Info";//对比信息
	public static final String COMPANYRISKTYPEPRODUCTINFO = "_Company_RiskType_Info";//保险公司_产品类别-产品信息

	private static void init() {
		if (cacheMap == null) {
			cacheMap = new HashMap<String, Map<String, Object>>();
			cacheMap.put(PRODUCTINFO, new HashMap<String, Object>());
			cacheMap.put(COMPANYINFO, new HashMap<String, Object>());
			cacheMap.put(RISKTYPEINFO, new HashMap<String, Object>());
			cacheMap.put(COMPANYRISKTYPEPRODUCTINFO, new HashMap<String, Object>());
		}else{
			if(cacheMap.get(PRODUCTINFO)==null){
				cacheMap.put(PRODUCTINFO, new HashMap<String, Object>());
			}
			if(cacheMap.get(COMPANYINFO)==null){
				cacheMap.put(COMPANYINFO, new HashMap<String, Object>());
			}
			if(cacheMap.get(RISKTYPEINFO)==null){
				cacheMap.put(RISKTYPEINFO, new HashMap<String, Object>());
			}
			if(cacheMap.get(COMPANYRISKTYPEPRODUCTINFO)==null){
				cacheMap.put(COMPANYRISKTYPEPRODUCTINFO, new HashMap<String, Object>());
			}
		}
	}

	public static void clean() {
		if (cacheMap != null) {
			Iterator<Map.Entry<String, Map<String,Object>>> itr = cacheMap.entrySet().iterator();
			Map<String, Object> map;
			String key;
			while (itr.hasNext()) {
				Map.Entry<String, Map<String,Object>> entry = itr.next(); 
				key = entry.getKey();
				map = cacheMap.get(key);
				if (map != null) {
					map.clear();
					map = null;
				}
			}
			cacheMap.clear();
			cacheMap = null;
		}
	}
	//保险公司专题
	public static void cleanProductInfo() {
		if (cacheMap != null) {
			Map<String, Object> map = cacheMap.get(COMPANYRISKTYPEPRODUCTINFO);
			if (map != null) {
				map.clear();
				map = null;
			}
		}
	}
	public static void cleanOneProductInfo(String ProductID) {
		if (cacheMap != null) {
			Object object = (Object)cacheMap.get(PRODUCTINFO).get(ProductID);
			if(object != null){
				cacheMap.get(PRODUCTINFO).remove(ProductID);
			} 
			String sql = " SELECT remark6,producttype FROM sdproduct WHERE productid=? ";
			QueryBuilder qb = new QueryBuilder(sql);
			qb.add(ProductID);
			DataTable dt = qb.executeDataTable();
			//如果清除了产品缓存信息，那么同时清除所对应的的保险公司缓存信息与对比信息
			if(dt.getRowCount()>=1){
				logger.info("清除产品信息缓存！");
				cacheMap.get(COMPANYINFO).remove(dt.get(0, 0));
				cacheMap.get(RISKTYPEINFO).remove(dt.get(0, 1));
			}
		}
	}
	
	public static Object getProductInfo(String productId) {
		if (cacheMap != null) {
			return cacheMap.get(PRODUCTINFO).get(productId);
		}
		return null;
	}
	
	public static void setProductInfo(String productId, Object obj){
		init();
		cacheMap.get(PRODUCTINFO).put(productId, obj);
	}
	
	public static void setProductInfoByCom(String infoId, Object obj){
		init();
		cacheMap.get(COMPANYRISKTYPEPRODUCTINFO).put(infoId, obj);
	}
	public static Object getProductInfoByCom(String infoId) {
		if (cacheMap != null) {
			return cacheMap.get(COMPANYRISKTYPEPRODUCTINFO).get(infoId);
		}
		return null;
	}
	public static Object getCompanyInfo(String comCode) {
		if (cacheMap != null) {
			return cacheMap.get(COMPANYINFO).get(comCode);
		}
		return null;
	}
	
	public static void setCompanyInfo(String comCode, Object obj){
		init();
		cacheMap.get(COMPANYINFO).put(comCode, obj);
	}
	
	public static Object getRiskTypeInfo(String riskType) {
		if (cacheMap != null) {
			return cacheMap.get(RISKTYPEINFO).get(riskType);
		}
		return null;
	}
	
	public static void setRiskTypeInfo(String riskType, Object obj){
		init();
		cacheMap.get(RISKTYPEINFO).put(riskType, obj);
	}
	
}

