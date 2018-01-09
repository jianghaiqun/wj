package com.sinosoft.product;

import cn.com.sinosoft.action.shop.FilterAction;
import com.finance.util.JedisCommonUtil;
import com.sinosoft.cms.api.SearchAPI;
import com.sinosoft.cms.datachannel.Deploy;
import com.sinosoft.cms.dataservice.ColumnUtil;
import com.sinosoft.cms.pub.CatalogUtil;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.cms.publish.AutomaticPublishArticle;
import com.sinosoft.cms.publish.PublishSearchCache;
import com.sinosoft.cms.site.CatalogExtend;
import com.sinosoft.cms.template.PageGenerator;
import com.sinosoft.cms.webservice.CmsServiceImpl;
import com.sinosoft.framework.Ajax;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.User;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.messages.LongTimeTask;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.LogUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.platform.pub.OrderUtil;
import com.sinosoft.schema.FEMSearchConditionInfoSchema;
import com.sinosoft.schema.FEMSearchConditionInfoSet;
import com.sinosoft.schema.SDSearchCacheSchema;
import com.sinosoft.schema.ZCCatalogSchema;
import com.sinosoft.schema.ZCCatalogSet;
import com.sinosoft.schema.ZDColumnRelaSchema;
import com.sinosoft.schema.ZDColumnRelaSet;
import com.sinosoft.schema.ZDColumnSchema;
import com.sinosoft.schema.ZDColumnValueSchema;
import com.sinosoft.schema.ZDColumnValueSet;
import com.sinosoft.webservice.ProductWebservice;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FEMSearchRelaList;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FMRisk;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.ProductInfoResponse;
import com.sinosoft.webservice.searchInfo.SearchInfoServiceStub.FEMSearchProperties;
import com.sinosoft.webservice.searchInfo.SearchInfoServiceStub.SearchInfoResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ProductSearch extends Ajax {
	@SuppressWarnings("unchecked")
	public void search() {
		Iterator<String> i = Request.keySet().iterator();
		String key = "";
		Map<String, String> map = new HashMap<String, String>();
		String productOrder = Request.getString("ProductsOrder");
		String eRiskType = Request.getString("ProductType");
		// String catalogID = Request.getString("CatalogID");
		int iCount = 0;
		while (i.hasNext()) {
			key = i.next();
			if (key.startsWith("Cookie.") || key.startsWith("Header.")) {
				continue;
			}
			if ("CatalogID".equals(key) || "ProductsOrder".equals(key)
					|| "ProductType".equals(key)) {
				iCount++;
			}
			map.put(key, Request.getString(key));
		}
		String risks = "";
		String pageURL = "";

		try {
			if (eRiskType == null || "".equals(eRiskType)) {
				return;
			}
			GetDBdata db = new GetDBdata();
			String search = db
					.getOneValue("select value from zdconfig where type='search' ");
			if (StringUtil.isNotEmpty(search) && "Y".equals(search)) {
				pageURL = PublishSearchCache.getCacheHTMLPath(map);
				if (pageURL != null && !"".equals(pageURL)) {
					Response.setStatus(1);
					Response.put("resultPageURL", pageURL);
					logger.info("产品筛选.缓存页面路径:{}", pageURL);
					return;
				}
			}
			//TODO---组合筛选条件从表中进行查询
//			String mapValues = "";
//			DataTable db1 = new QueryBuilder("select codevalue from zdcode where codetype='SearchCondition'and parentcode= '"+Request.getString("ProductType")+"' order by codeorder asc ").executeDataTable();
//			for(int m=0;m<db1.getRowCount();m++ ){
//				if(map.containsKey(db1.getString(m, 0))){
//					mapValues += "-" + map.get(db1.getString(m, 0));
//				}
//			}
//			GetDBdata db = new GetDBdata();
//			if ("".equals(productOrder) || productOrder == null) {
//				productOrder = "";
//			}
//			productOrder = db
//					.getOneValue("select codename from zdcode where codetype='home_productsorder' and codevalue='"
//							+ productOrder + "' ");
//			String searchCondition = productOrder + "-" + eRiskType + mapValues;		 // map.values();
//			 String SearchPOrder = db
//			 .getOneValue("select cacheurl from sdsearchcache where conditioncode = '"+searchCondition+"'");
//			 if (SearchPOrder != null && !"".equals(SearchPOrder)) {
//			 Response.setStatus(1);
//			 Response.put("resultPageURL", SearchPOrder);
//		    LogUtil.getLogger().info("产品筛选.SDSearchCache表页面路径:" + SearchPOrder);
//			 return;
//			 }

			FEMSearchRelaList[] list = null;
			Map<String, Object> sMap = new HashMap<String, Object>();
			sMap.put("SubRiskTypeCode", eRiskType);
			sMap.put("BU1", "N");
			if (map.size() - iCount > 0) {
				list = new FEMSearchRelaList[map.size() - iCount];
				Iterator<String> itr = map.keySet().iterator();
				int j = 0;
				while (itr.hasNext()) {
					key = itr.next();
					if ("CatalogID".equals(key) || "ProductsOrder".equals(key)
							|| "ProductType".equals(key)) {
						continue;
					}
					list[j] = new FEMSearchRelaList();
					list[j].setERiskSubType(eRiskType);
					list[j].setSearchCode(key);
					list[j].setSearchValue(map.get(key));
					j++;
				}
				sMap.put("FEMSearchRelaList", list); // 查询全部产品 不加这个 测试用
			}
			logger.info("产品筛选.调用接口:{}", eRiskType + ":" + productOrder);
			ProductInfoResponse productInfo = ProductWebservice
					.ProductInfoSereviceImpl(sMap, productOrder);
			FMRisk[] productList = productInfo.getFMRisk();
			if (productList != null && productList.length > 0
					&& productList[0] != null) {
				for (int j = 0; j < productList.length; j++) {
					risks += "," + productList[j].getRiskCode();
				}
				if (risks.startsWith(",")) {
					risks = risks.substring(1);
				}
			}
		} catch (Exception e) {
			logger.error("产品查询失败：" + e.getMessage(), e);
		}
		logger.info("产品筛选.查询结果:{}", risks);
		pageURL = PublishSearchCache.Publish(risks, map);
		if (pageURL != null && !"".equals(pageURL)) {
			Response.setStatus(1);
			Response.put("resultPageURL", pageURL);
		}
		logger.info("产品筛选.生成页面路径:{}", pageURL);
	}

	@SuppressWarnings("unchecked")
	public void quicksearch() {
		String productOrder = Request.getString("ProductsOrder");
		String eRiskType = Request.getString("ProductType");
		if (eRiskType == null || "".equals(eRiskType)) {
			return;
		}
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setName("快速查询");
		catalog.setSiteID(Request.getString("_CurrentSiteID"));
		ZCCatalogSet catalogs = catalog.query();
		if (catalogs == null || catalogs.size() <= 0) {
			Response.setStatus(0);
			logger.info("产品速查.失败:未找到速查栏目,需要建立。");
			return;
		}
		Map<String, String> map = new HashMap<String, String>();
		int iCount = 0;
		if (Request.getString("SupplierCode") != null
				&& !"".equals(Request.getString("SupplierCode"))
				&& !"0".equals(Request.getString("SupplierCode"))) {
			iCount++;
			map.put("SupplierCode", Request.getString("SupplierCode"));
		}
		if (Request.getString("Age") != null
				&& !"".equals(Request.getString("Age"))
				&& !"0".equals(Request.getString("Age"))) {
			iCount++;
			map.put("Age", Request.getString("Age"));
		}
		if (Request.getString("Sex") != null
				&& !"".equals(Request.getString("Sex"))
				&& !"0".equals(Request.getString("Sex"))) {
			iCount++;
			map.put("Sex", Request.getString("Sex"));
		}
		map.put("CatalogID", String.valueOf(catalogs.get(0).getID()));
		map.put("ProductType", eRiskType);
		map.put("ERiskType", eRiskType);
		map.put("SearchType", Request.getString("SearchType"));
		map.put("ProductType", "quicksearch");
		String risks = "";
		String pageURL = "";
		try {
			GetDBdata db = new GetDBdata();
			String search = db
					.getOneValue("select value from zdconfig where type='quicksearch' ");
			if (StringUtil.isNotEmpty(search) && "Y".equals(search)) {
				pageURL = PublishSearchCache.getCacheHTMLPath(map);
				if (pageURL != null && !"".equals(pageURL)) {
					Response.setStatus(1);
					Response.put("resultPageURL", pageURL);
					logger.info("产品筛选.缓存页面路径:{}", pageURL);
					return;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		FEMSearchRelaList[] list = null;
		Map<String, Object> sMap = new HashMap<String, Object>();
		sMap.put("SubRiskTypeCode", eRiskType);
		sMap.put("BU1", "N");
		sMap.put("Sex", Request.getString("Sex"));
		sMap.put("SupplierCode", Request.getString("SupplierCode"));

		if (iCount - 1 > 0) {
			list = new FEMSearchRelaList[iCount - 1];
			Iterator<String> itr = map.keySet().iterator();
			int j = 0;
			String key = "";
			while (itr.hasNext()) {
				key = itr.next();
				if ("CatalogID".equals(key) || "ProductType".equals(key)
						|| "SupplierCode".equals(key)
						|| "SearchType".equals(key) || "ERiskType".equals(key)) {
					continue;
				}
				list[j] = new FEMSearchRelaList();
				list[j].setERiskSubType(eRiskType);
				list[j].setSearchCode(key);
				list[j].setSearchValue(map.get(key));
				j++;
			}
			// sMap.put("FEMSearchRelaList", list);
		}
		logger.info("产品速查.调用接口:{}", eRiskType + ":" + productOrder);
		ProductInfoResponse productInfo = null;
		try {
			productInfo = ProductWebservice.ProductInfoSereviceImpl(sMap,
					productOrder);
		} catch (Exception e) {
			logger.error("产品速查失败：" + e.getMessage(), e);
		}
		if (productInfo != null) {
			FMRisk[] productList = productInfo.getFMRisk();
			if (productList != null && productList.length > 0
					&& productList[0] != null) {
				for (int i = 0; i < productList.length; i++) {
					risks += "," + productList[i].getRiskCode();
				}
				if (risks.startsWith(",")) {
					risks = risks.substring(1);
				}
			}
		}
		map.put("CatalogID2",
				new QueryBuilder(
						"select id from zccatalog where SiteID = ? and ProductType = ?",
						Request.getString("_CurrentSiteID"), eRiskType)
						.executeString());
		logger.info("产品速查.查询结果:{}", risks);
		map.put("ProductType", "quicksearch");
		pageURL = PublishSearchCache.Publish(risks, map);
		if (pageURL != null && !"".equals(pageURL)) {
			Response.setStatus(1);
			Response.put("resultPageURL", pageURL);
		}
		logger.info("产品速查.生成页面路径:{}", pageURL);
	}

	public void syncSearchCondition() {
		LongTimeTask ltt = new LongTimeTask() {
			public void execute() {
				PublishSearchCache.syncSearchCondition(this);
				publishSearchCondition();
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
		ltt.setCurrentInfo("开始重置......");
		$S("TaskID", "" + ltt.getTaskID());
	}

	public void syncAllProduct() {
		LongTimeTask ltt = new LongTimeTask() {
			public void execute() {
				AutomaticPublishArticle.allProduct(this);
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
		ltt.setCurrentInfo("开始重置......");
		$S("TaskID", "" + ltt.getTaskID());
	}
	public void syncAllProductArea() {
		LongTimeTask ltt = new LongTimeTask() {
			//mod by wangej 20160107 地区同步增加产品和保险公司的范围选择
			String productID = $V("ProductID");
			String companyID = $V("CompanyID");
			public void execute() {
				AutomaticPublishArticle.allProductArea(this,productID,companyID);
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
		ltt.setCurrentInfo("开始重置......");
		$S("TaskID", "" + ltt.getTaskID());
		String  productID = $V("ProductID");
		JedisCommonUtil.remove(0,productID + "_BUYINIT");
	}
	public void syncAllProductHI() {
		LongTimeTask ltt = new LongTimeTask() {
			//mod by wangej 20160107 地区同步增加产品和保险公司的范围选择
			String productID = $V("ProductID");
			String companyID = $V("CompanyID");
			public void execute() {
				AutomaticPublishArticle.allProductHI(this,productID,companyID);
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
		ltt.setCurrentInfo("开始重置......");
		$S("TaskID", "" + ltt.getTaskID());
	}

	public void publishAllProduct() {
		LongTimeTask ltt = new LongTimeTask() {
			public void execute() {
				AutomaticPublishArticle.publishAllProduct(this);
			}
		};
		ltt.start();
		ltt.setCurrentInfo("开始发布......");
		$S("TaskID", "" + ltt.getTaskID());
	}

	public void publishQuickSearchProduct() {
		LongTimeTask ltt = new LongTimeTask() {
			public void execute() {
				
				// AutomaticPublishArticle.publishQuickSearchProduct(this);
				publishQuickSearchStaticPages(this);
			}
		};
		PageGenerator p = new PageGenerator(ltt);
		if (p.staticSiteIndex(Application.getCurrentSiteID())) {
			Deploy d = new Deploy();
			d.addJobs(Application.getCurrentSiteID(), p.getFileList());
		}
		ltt.setUser(User.getCurrent());
		ltt.start();
		ltt.setCurrentInfo("开始发布......");
		$S("TaskID", "" + ltt.getTaskID());
	}

	public void publishQuickSearchStaticPages(LongTimeTask lTT) {
		// 1 拼map组合条件
		// 2 for循环调用方法
		String eRiskType = "";
		String pageURL = "";

		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setName("快速查询");
		catalog.setSiteID("221");
		ZCCatalogSet catalogs = catalog.query();
		if (catalogs == null || catalogs.size() <= 0) {
			Response.setStatus(0);
			logger.info("产品速查.失败:未找到速查栏目,需要建立。");
			return;
		}

		String sql1 = "select codevalue,codename from  zdcode where codetype='home_suppliercode'";
		DataTable db1 = new QueryBuilder(sql1).executeDataTable();
		for (int m = 0; m < db1.getRowCount(); m++) {
			String SupplierCode = db1.getString(m, "codevalue");
			if ("0000".equals(SupplierCode)) {
				SupplierCode = "";
			}
			String sql2 = "select codevalue,codename from  zdcode where codetype='home_producttype'";
			DataTable db2 = new QueryBuilder(sql2).executeDataTable();
			for (int n = 0; n < db2.getRowCount(); n++) {
				eRiskType = db2.getString(n, "codevalue");

				String sql3 = "select codevalue,codename from  zdcode where codetype='home_sex'";
				DataTable db3 = new QueryBuilder(sql3).executeDataTable();
				for (int h = 0; h < db3.getRowCount(); h++) {
					String sex = db3.getString(h, "codevalue");
					if ("00".equals(sex)) {
						sex = "";
					}
					Map<String, String> map = new HashMap<String, String>();
					int iCount = 0;

					map.put("SupplierCode", SupplierCode);
					map.put("Sex", sex);
					map.put("CatalogID",
							String.valueOf(catalogs.get(0).getID()));
					map.put("ProductType", eRiskType);
					map.put("ERiskType", eRiskType);
					map.put("SearchType", "quick");
					String risks = "";

					FEMSearchRelaList[] list = null;
					Map<String, Object> sMap = new HashMap<String, Object>();
					sMap.put("SubRiskTypeCode", eRiskType);
					sMap.put("BU1", "N");
					sMap.put("Sex", sex);
					sMap.put("SupplierCode", SupplierCode);

					// if (iCount - 1 > 0) {
					list = new FEMSearchRelaList[iCount + 1];
					Iterator<String> itr = map.keySet().iterator();
					int j = 0;
					String key = "";
					while (itr.hasNext()) {
						key = itr.next();
						if ("CatalogID".equals(key)
								|| "ProductType".equals(key)
								|| "SupplierCode".equals(key)
								|| "SearchType".equals(key)
								|| "ERiskType".equals(key)) {
							continue;
						}
						list[j] = new FEMSearchRelaList();
						list[j].setERiskSubType(eRiskType);
						list[j].setSearchCode(key);
						list[j].setSearchValue(map.get(key));
						j++;
					}
					// sMap.put("FEMSearchRelaList", list);
					// }
					logger.info("产品速查.调用接口:{}", eRiskType);
					ProductInfoResponse productInfo = null;
					try {
						productInfo = ProductWebservice
								.ProductInfoSereviceImpl(sMap, null);
					} catch (Exception e) {
						logger.error("产品速查失败：" + e.getMessage(), e);
					}
					FMRisk[] productList = null;
					if (productInfo != null) {
						productList = productInfo.getFMRisk();
						if (productList != null && productList.length > 0
								&& productList[0] != null) {
							for (int i = 0; i < productList.length; i++) {
								risks += "," + productList[i].getRiskCode();
							}
							if (risks.startsWith(",")) {
								risks = risks.substring(1);
							}
						}
					}
					map.put("CatalogID2", new QueryBuilder(
							"select id from zccatalog where  ProductType = ?",
							eRiskType).executeString());
					logger.info("产品速查.查询结果:{}", risks);
					map.put("ProductType", "quicksearch");
					pageURL = PublishSearchCache.Publish(risks, map);

					int sl1 = db1.getRowCount();//保险细类
					int sl2 = db2.getRowCount();//保险公司
					int sl3 = db3.getRowCount();//保险大类
					int iTotal = sl1 * sl2 * sl3;
					int fz = (sl1 - m - 1) * sl2 * sl3 + (sl2 - n - 1) * sl3
							+ sl3 - h - 1;
					if (iTotal > 0 && iTotal != fz) {
						if (lTT != null) {
							lTT.setCurrentInfo("正在发布静态页面(" + (iTotal - fz)
									+ "/" + iTotal + ")");
							lTT.setPercent(Integer.valueOf((iTotal - fz) * 100
									/ (iTotal + 1)));
						}
					}
					if (iTotal == fz) {
						lTT.setPercent(100);
					}
					String pageURL1 = PublishSearchCache.getSearchPath(map);
					String name1 = db1.getString(m, "codename");
					String name2 = db2.getString(n, "codename");
					String name3 = db3.getString(h, "codename");
					String conditionName = name1 + "-" + name2 + "-" + name3;
					String SupplierCode1 = "";
					String sex1 = "";
					if ("".equals(SupplierCode)) {
						SupplierCode1 = "0000";
					} else {
						SupplierCode1 = SupplierCode;
					}
					if ("".equals(sex)) {
						sex1 = "00";
					} else {
						sex1 = sex;
					}
					String conditionCode = SupplierCode1 + "-" + eRiskType
							+ "-" + sex1;
					SDSearchCacheSchema tSDSearchCacheSchema = new SDSearchCacheSchema();

					String sql4 = "select id from SDSearchCache where conditionCode='"
							+ conditionCode + "'";
					DataTable db4 = new QueryBuilder(sql4).executeDataTable();
					for (int mm = 0; mm < db4.getRowCount(); mm++) {
						String id1 = db4.getString(mm, "id");
						tSDSearchCacheSchema.setID(id1);
						tSDSearchCacheSchema.delete();
					}

					tSDSearchCacheSchema.setID(NoUtil
							.getSDSearchCacheID());
					tSDSearchCacheSchema.setConditionName(conditionName);
					tSDSearchCacheSchema.setConditionCode(conditionCode);
					tSDSearchCacheSchema.setCacheURL(pageURL);
					tSDSearchCacheSchema.setCreateDate(PubFun.getCurrentDate());
					tSDSearchCacheSchema.setRealPath(pageURL1);
					tSDSearchCacheSchema.setSearchType("QuickSearch");
					tSDSearchCacheSchema.insert();

				}
			}
		}
		logger.info("产品速查.生成页面路径:{}", pageURL);
	}

	public void publishSearchProduct() {
		LongTimeTask ltt = new LongTimeTask() {
			public void execute() {
				publishSearchStaticPages(this);
			}
		};
		PageGenerator p = new PageGenerator(ltt);
		if (p.staticSiteIndex(Application.getCurrentSiteID())) {
			Deploy d = new Deploy();
			d.addJobs(Application.getCurrentSiteID(), p.getFileList());
		}
		ltt.setUser(User.getCurrent());
		ltt.start();
		ltt.setCurrentInfo("开始发布......");
		$S("TaskID", "" + ltt.getTaskID());
	}

	public void publishSearchStaticPages(LongTimeTask lTT) {

		String eRiskType = "";
		String pageURL = "";
		String CatalogID = "";
		int pageNumber = 0;
		int productSearchFlag;
		Map<String, String> map1 = new HashMap<String, String>();
		String sql1 = "select codevalue,codename,parentcode from  zdcode where codetype='home_productsorder'";
		DataTable db1 = new QueryBuilder(sql1).executeDataTable();
		int db1length = db1.getRowCount();
		for (int n = 0; n < db1length; n++) {
			String productOrder = db1.getString(n, "codevalue");
			String OrderName = db1.getString(n, "codename");
			String parentcode = db1.getString(n, "parentcode");
			boolean defaultOrder = false;
			if ("".equals(productOrder)) {
				productOrder = null;
				defaultOrder = true;
			}  
			
			DataTable dt = new QueryBuilder(
					"select codevalue,codename from zdcode where codetype='ProductType' and parentcode='ProductType' order by codevalue ")
					.executeDataTable();
			int iTotal = dt.getRowCount();
			int i;
			for (i = 0; i < iTotal; i++) {
				String sql5 = "select id from zccatalog where producttype='"
						+ dt.getString(i, 0) + "'";
				DataTable db5 = new QueryBuilder(sql5).executeDataTable();
				CatalogID = db5.getString(0, "id");

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("ERiskSubType", dt.getString(i, 0));
				try {
					SearchInfoResponse siInfo = null;
					try {
						siInfo = ProductWebservice.SearchInfoServiceImpl(map,
								null);
					} catch (Exception e) {
						logger.error( "产品筛选失败：" + e.getMessage(), e);
					}
					List<String[]> strList = new ArrayList<String[]>();
					FEMSearchProperties[] femp = siInfo
							.getFEMSearchProperties();

					for (int j = 0; femp != null && j < femp.length; j++) {
						FEMSearchProperties fem = femp[j];
						femp[j].getSearchCode();
						String[] str = new String[fem.getFEMSearchRela().length + 1];
						for (int k = 0; fem.getFEMSearchRela() != null
								&& k < fem.getFEMSearchRela().length + 1; k++) {
							if (k == fem.getFEMSearchRela().length) {
								str[k] = " ";// 不限
							} else {
								str[k] = fem.getFEMSearchRela()[k]
										.getSearchValue();
							}
						}
						strList.add(str);
					}
					while (strList.size() > 1) {
						strList = compose(strList);
					}
					for (String ss[] : strList) {
						for (String s : ss) {
							String[] strs = s.split("-");
							int iCount = 0;
							productSearchFlag = 0;
							map1 = new HashMap<String, String>();
							for (int ii = 0; ii < strs.length; ii++) { // !"SupplierCode".equals(femp[ii].getSearchCode())
																		// &&
								if (!" ".equals(strs[ii])) {
									map1.put(femp[ii].getSearchCode(), strs[ii]);
									iCount++;
								}
							}
							map1.put("CatalogID", CatalogID);
							map1.put("ProductType", dt.getString(i, 0));
							map1.put("ProductsOrder", productOrder);

							String risks = "";
							try {
								FEMSearchRelaList[] list = null;
								Map<String, Object> sMap = new HashMap<String, Object>();
								sMap.put("SubRiskTypeCode", dt.getString(i, 0));
								sMap.put("BU1", "N");

								String key = "";
							//	if(iCount>0){
									list = new FEMSearchRelaList[iCount];// - iCount
									Iterator<String> itr = map1.keySet().iterator();
									int y = 0;
									while (itr.hasNext()) {
										key = itr.next();
										if ("CatalogID".equals(key) || "ProductsOrder".equals(key) || "ProductType".equals(key)) {
											continue;
										}
										list[y] = new FEMSearchRelaList();
										list[y].setERiskSubType(dt.getString(i, 0));
										list[y].setSearchCode(key);
										list[y].setSearchValue(map1.get(key));
										y++;
									}
									sMap.put("FEMSearchRelaList", list); // 查询全部产品不加这个测试用
							//	}										
																		

								logger.info("产品筛选.调用接口:{}", eRiskType);
								ProductInfoResponse productInfo = ProductWebservice.ProductInfoSereviceImpl(sMap, productOrder);// productOrder
								if (productInfo != null) {
									FMRisk[] productList = productInfo.getFMRisk();
									if ( productList != null && productList.length > 0 && productList[0] != null ) {
										for (int j = 0; j < productList.length; j++) {
											risks += "," + productList[j].getRiskCode();
										}
										if (risks.startsWith(",")) {
											risks = risks.substring(1);
										}
									}
									// 判断是否为默认排序
									if(defaultOrder){
										// add by guobin 筛选页面根据排序功能进行排序  30 - 已发布, 60 - 重新编辑
										QueryBuilder qb = new QueryBuilder(
												" select z3.textvalue from zcarticle z1,zccatalog z2 ,ZDColumnValue z3 "
														+ " where z1.catalogid=z2.id and z2.producttype= '" + dt.getString(i, 0) + "' and z1.id=z3.relaid and" 
														+ " z3.columncode='RiskCode' and z1.status in (30,60) and z3.textvalue in (" + risks + ")  order by z1.topflag desc,z1.orderflag desc "  );
										DataTable risk = qb.executeDataTable();
										risks = "";
										for (int j = 0; j < risk.getRowCount(); j++) {
											risks += "," + risk.getString(j, 0);
										}
										if (risks.startsWith(",")) {
											risks = risks.substring(1);
										}
									}
									pageNumber = productList.length / 20 + 1;
								} else {
									DataTable risk = new QueryBuilder(
											" select z3.textvalue from zcarticle z1,zccatalog z2 ,ZDColumnValue z3 "
													+ " where z1.catalogid=z2.id and z2.producttype='"
													+ dt.getString(i, 0)
													+ "'  and z1.id=z3.relaid and z3.columncode='RiskCode' and z1.status in (30,60) order by z1.topflag desc,z1.orderflag desc ")
											.executeDataTable();
									for (int j = 0; j < risk.getRowCount(); j++) {
										risks += "," + risk.getString(j, 0);
									}
									if (risks.startsWith(",")) {
										risks = risks.substring(1);
									}
									pageNumber = risk.getRowCount() / 20 + 1;
									productSearchFlag = 1;
								}
							} catch (Exception e) {
								logger.error("产品查询失败：" + e.getMessage(), e);
							}
							logger.info("产品筛选.查询结果:{}", risks);
							pageURL = PublishSearchCache.Publish(risks, map1);

							int fz = (db1length - n) * iTotal + iTotal - i - 1;
							int iTotal1 = iTotal * (db1length + 1);
							lTT.setCurrentInfo("正在发布静态页面第(" + (iTotal1 - fz)
									+ "/" + iTotal1 + ")类");
							lTT.setPercent(Integer.valueOf((iTotal1 - fz) * 100
									/ (iTotal1 + 1)));

							String pageURL1 = PublishSearchCache
									.getSearchPath(map1);

							String conditionName = "";
							for (int ii = 0; ii < strs.length; ii++) {
								if (" ".equals(strs[ii])) {
									conditionName += "";// conditionName +=
														// "-不限";
								} else {
									conditionName += "-" + strs[ii];
								}
							}
							String conditionName1 = dt.getString(i, 1)
									+ conditionName;
							String conditionCode = db1.getString(n, "codename")
									+ "-" + dt.getString(i, 0) + conditionName;
							for (int pages = 1; pages <= pageNumber; pages++) {
								String pageURL3 = pageURL1;
								if (pages >= 2) {
									pageURL3 = pageURL3.substring(0,
											pageURL3.indexOf(".shtml"))
											+ "_"
											+ Integer.toString(pages)
											+ ".shtml";
								}
								String pageURL2 = pageURL3
										.replaceAll("/", "//");
								String wordsText = FileUtil.readText(pageURL2);
								String file1 = wordsText.substring(0,
										wordsText.indexOf("<title>") + 7);
								String file2 = wordsText
										.substring(
												wordsText.indexOf("<title>") + 7,
												wordsText.indexOf("<span class=\"CConditionName\">"
														+ femp[0]
																.getSearchName()
														+ "："));
								String str2 = "";
								String a1 = "";
								int b = 0;
								for (int nu = 0; nu < femp.length; nu++) {
									if (wordsText
											.indexOf("<span class=\"CConditionName\">"
													+ femp[nu].getSearchName()
													+ "：") != -1) {
										int a = wordsText
												.indexOf("<span class=\"CConditionName\">"
														+ femp[nu]
																.getSearchName()
														+ "：");
										a1 = wordsText.substring(a);
										b = a1.indexOf("</ul>") + 5;
										String str1 = wordsText.substring(a, a
												+ b);
										if (!" ".equals(strs[nu])) {
											str1 = str1.replaceAll(
													"li_selected", "不限");
											str1 = str1
													.replace(
															"<li><span onclick=\"searchInfo('SearchCondition_"
																	+ femp[nu]
																			.getSearchCode()
																	+ "', this);\" name=\""
																	+ femp[nu]
																			.getSearchCode()
																	+ "\">"
																	+ strs[nu]
																	+ "</span>",
															"<li class=\"li_selected\"><span onclick=\"searchInfo('SearchCondition_"
																	+ femp[nu]
																			.getSearchCode()
																	+ "', this);\" name=\""
																	+ femp[nu]
																			.getSearchCode()
																	+ "\">"
																	+ strs[nu]
																	+ "</span>");
										}
										str2 += str1;
									}
								}
								String file4 = a1.substring(b);
								String OrderName1 = "";
								if (!"默认排序".equals(OrderName)) {
									OrderName1 = OrderName + "-";
								}
								if (conditionName.startsWith("-")) {
									conditionName = conditionName.substring(1)+"-";
								}
//								if("".equals(conditionName)){
//									
//								}
								String file3 = file1 + OrderName1
										+ conditionName+ file2 + str2
										+ file4;

								if (!"默认排序".equals(OrderName)
										&& productSearchFlag == 0) {
									file3 = file3
											.replace(
													"<span id=\"order_default\" class=\"one\" onclick=\"doOrder(this);\">默认排序</span>",
													"<span id=\"order_default1\" class=\"one\" onclick=\"doOrder(this);\">默认排序</span>");
									if (productOrder.indexOf("desc") != -1) {
										file3 = file3.replace(
												"class=\"two\" onclick=\"doOrder(this);\">"
														+ parentcode
														+ "</span>",
												"class=\"twodesc\" onclick=\"doOrder(this);\">"
														+ parentcode
														+ "</span>");
									} else {
										file3 = file3.replace(
												"class=\"two\" onclick=\"doOrder(this);\">"
														+ parentcode
														+ "</span>",
												"class=\"twoasc\" onclick=\"doOrder(this);\">"
														+ parentcode
														+ "</span>");
									}
								}
								if ("默认排序".equals(OrderName)) {
									file3 = file3
											.replace(
													"<span id=\"order_default\" class=\"one\" onclick=\"doOrder(this);\">默认排序</span>",
													"<span id=\"order_default\" class=\"onesel\" onclick=\"doOrder(this);\">默认排序</span>");
								}

								if (productSearchFlag == 1) {
									String file21 = file3.substring(0, file3
											.indexOf("<div id=\"products\">"));
									String file22 = file3.substring(file3
											.indexOf("<div id=\"products\">")); // style=\"color:#000000;font-size:20px;margin-top:
																				// 15px;
																				// margin-left:16px;margin-bottom:15px;\"
									String file23 = "<div class=\"top_b\"><span><p class=\"searchNoData\">没有符合筛选条件的保险产品，以下是"
											+ dt.getString(i, 1)
											+ "下的热门产品供您选择！</p></span></div>";
									file3 = file21 + file23 + file22;
								}

								FileUtil.writeText(pageURL2, file3);
							}

							SDSearchCacheSchema tSDSearchCacheSchema = new SDSearchCacheSchema();
							String sql4 = "select id from SDSearchCache where conditionCode='" + conditionCode + "'";
							DataTable db4 = new QueryBuilder(sql4) .executeDataTable();
							for (int mm = 0; mm < db4.getRowCount(); mm++) {
								String id1 = db4.getString(mm, "id");
								tSDSearchCacheSchema.setID(id1);
								tSDSearchCacheSchema.delete();
							}
							tSDSearchCacheSchema.setID(NoUtil.getSDSearchCacheID());
							tSDSearchCacheSchema.setConditionName(conditionName1);
							tSDSearchCacheSchema.setConditionCode(conditionCode);
							tSDSearchCacheSchema.setCacheURL(pageURL);
							tSDSearchCacheSchema.setCreateDate(PubFun.getCurrentDate());
							tSDSearchCacheSchema.setRealPath(pageURL1);
							tSDSearchCacheSchema.setSearchType("Search");
							tSDSearchCacheSchema.setProp1(Integer.toString(pageNumber));
							tSDSearchCacheSchema.insert();
						}
					}
				} catch (Exception e) {
					logger.error("筛选页面接口获取数据失败：" + e.getMessage(), e);
				}
			}
		}
		//TODO---更新链接
		//updateSearchDate(); 
		logger.info("产品筛选.生成页面路径:{}", pageURL);
	}

	private static List<String[]> compose(List<String[]> list) {
		if (list == null || list.size() == 0) {
			return null;
		}
		List<String[]> result = new ArrayList<String[]>();
		for (int i = 0; list != null && i < list.size(); i = i + 2) {
			if (i != list.size() - 1) {
				String[] s = compose(list.get(i), list.get(i + 1));
				if (s != null)
					result.add(s);
			} else {
				result.add(list.get(i));
			}
		}
		return result;
	}

	private static String[] compose(String[] s1, String[] s2) {
		if (s1 == null || s1.length == 0) {
			return null;
		}
		if (s2 == null || s2.length == 0) {
			return null;
		}
		String[] str = new String[s1.length * s2.length];
		int index = 0;
		for (String ss1 : s1) {
			for (String ss2 : s2) {
				str[index] = ss1 + "-" + ss2;
				index++;
			}
		}
		return str;
	}
	/**
	 * 重置时生成所有筛选页面
	 */
	private static void publishSearchCondition(){
		String catalogProductsql = "select innercode from zccatalog where id = (select value from zdconfig where type ='CatalogId')";
		String catalogBXSCsql = "select innercode from zccatalog where id = (select value from zdconfig where type ='CatalogBXSCID')";
		DataTable dt = new QueryBuilder(catalogProductsql).executeDataTable();
		if(dt.getRowCount()==1){
			createSearchCondition(dt.getString(0, 0),true);
		}
		dt = new QueryBuilder(catalogBXSCsql).executeDataTable();
		if(dt.getRowCount()==1){
			createSearchCondition(dt.getString(0, 0),false);
		}
	}
	private static void createSearchCondition(String innerCode,boolean flagType){
		String nERiskSubType="";
		String tCatalogID = "";
		String tERiskSubType="";
		String tUrl = "";
		Map<String,String> catalogUrl = new HashMap<String,String>();
		String sql3 = "select ID,ProductType,URL from zccatalog where innerCode like '"+innerCode+"%'";
		QueryBuilder qb = new QueryBuilder(sql3);
		String contextPath = Config.getFrontServerContextPath();
		DataTable dt0 = qb.executeDataTable();
		for(int t=0;t<dt0.getRowCount();t++){
			tERiskSubType = dt0.get(t).getString("ProductType");
			tUrl = dt0.get(t).getString("URL");
			if (StringUtil.isNotEmpty(tERiskSubType)) {
				if (StringUtil.isNotEmpty(tUrl)) {
					tUrl = contextPath + "/" + tUrl;
					catalogUrl.put(tERiskSubType, tUrl);
				} else {
					catalogUrl.put(tERiskSubType, "");
				}
			}
		}
		
		// 取得险种说明
		Map<String, String> descMap = new HashMap<String, String>();
		DataTable descDT = new QueryBuilder("select CodeValue,Memo from zdcode where CodeType='ProductTypeDesc' and ParentCode='ProductTypeDesc'").executeDataTable();
		if (descDT != null && descDT.getRowCount() > 0) {
			int rowcount = descDT.getRowCount();
			for (int i = 0; i < rowcount; i++) {
				if (StringUtil.isNotEmpty(descDT.getString(i, 1))) {
					descMap.put(descDT.getString(i, 0), descDT.getString(i, 1));
				}
			}
		}
		
		for(int t=0;t<dt0.getRowCount();t++){
			tCatalogID = dt0.get(t).getString("ID");
			tERiskSubType = dt0.get(t).getString("ProductType");
			tUrl = dt0.get(t).getString("URL");
			if (StringUtil.isNotEmpty(tUrl)) {
				tUrl = contextPath + "/" + tUrl;
			}
			if(StringUtil.isNotEmpty(tERiskSubType)){
				if(flagType){
					nERiskSubType = tERiskSubType.substring(0, 1);
				}else{
					nERiskSubType = tERiskSubType.substring(1, 2);
				}
			}
			
		String tHTML = "";
		String tHTML1 = "";
		//百年人寿公司
		String BNCompany = Config.getValue("BNCompany");
		Transaction trans = new Transaction();
		FEMSearchConditionInfoSchema SCschema = new FEMSearchConditionInfoSchema(); 
		FEMSearchConditionInfoSet SCset = new FEMSearchConditionInfoSet();
		if(StringUtil.isNotEmpty(tERiskSubType)){
			//根据险种类型查询出所有一级查询条件
			SCset = SCschema.query(new QueryBuilder("where SearchLevel='1' and ERiskType=? order by extra3 DESC,-SearchOrder" , nERiskSubType));

			tHTML = "<div id=\"radiolist\" class=\"radiolist CsearchConditions\">";
			tHTML1 = "<div id=\"radiolist\" class=\"radiolist CsearchConditions\">";
			String productTypeDesc = "";
			for (int i = 0; i < SCset.size(); i++) 
			{	
				//拼装不限和全部
				boolean flag = false;
				SCschema = SCset.get(i);
				FEMSearchConditionInfoSchema SCschema1 = new FEMSearchConditionInfoSchema();
				FEMSearchConditionInfoSet SCset1 = new FEMSearchConditionInfoSet();
				FEMSearchConditionInfoSchema SCschema2 = new FEMSearchConditionInfoSchema();
				FEMSearchConditionInfoSet SCset2 = new FEMSearchConditionInfoSet();
				FEMSearchConditionInfoSet SCset3 = new FEMSearchConditionInfoSet();
				int[] subNode = new int[10];
				int k = 0;
				// 查询出一级查询条件对应的子条件
				if(StringUtil.isEmpty(BNCompany)){
					SCset1 = SCschema1.query(new QueryBuilder("where UpperId='" + SCschema.getId() + "' order by -SearchOrder"));
					
				} else {
					SCset1 = SCschema1.query(new QueryBuilder("where UpperId=? and SearchCode <> ? order by -SearchOrder", SCschema.getId(), BNCompany));
				}
				//SCset1 = SCschema1.query(new QueryBuilder("where UpperId='" + SCschema.getId() + "' order by -SearchOrder"));
				if("Y".equals(SCschema.getIsMultipleChoice())){
					//如果本条件为多选在外成多套一个“chklist”
					tHTML += "<div class=\"chklist\" >";
					tHTML1 += "<div class=\"chklist\" >";
				}
				//如果是默认显示，则显示，否则不显示 add by cuishigang 20131211
				if("Y".equals(SCschema.getExtra3())){
					tHTML += "<div class=\"bznx-z\" id=\"CsearchConditions_"+SCschema.getSearchCode()+"\">";
					tHTML1 += "<div class=\"bznx-z\" id=\"CsearchConditions_"+SCschema.getSearchCode()+"\">";
				}else{
					tHTML += "<div class=\"bznx-no\" id=\"CsearchConditions_"+SCschema.getSearchCode()+"\">";
					tHTML1 += "<div class=\"bznx-no\" id=\"CsearchConditions_"+SCschema.getSearchCode()+"\">";
				}
				tHTML += "<span class=\"CConditionName\">" + SCschema.getSearchName() + "：</span>";
				tHTML1 += "<span class=\"CConditionName\">" + SCschema.getSearchName() + "：</span>";
				tHTML += "<div class=\"select_nav\"><ul>";
				tHTML1 += "<div class=\"select_nav\"><ul>";
				//IF判断是多选而且是并集时 02—Y为全部
				if ("02".equals(SCschema.getIntersection()) && "Y".equals(SCschema.getIsMultipleChoice())) 
				{ 
					tHTML += " <li class=\"jiange\"><input type=\"radio\" id=\""+ SCschema.getSearchCode() +"_all\" value=\"default_"+SCschema.getSearchCode()+"_"+SCschema.getSearchLevel()+"\" name=\""+ SCschema.getSearchCode() 
					+"\" style=\"display: none;\"><label class=\"hRadio\" >全部</label></li>";
					tHTML1 += " <li class=\"jiange\"><input type=\"radio\" id=\""+ SCschema.getSearchCode() +"_all\" value=\"default_"+SCschema.getSearchCode()+"_"+SCschema.getSearchLevel()+"\" name=\""+ SCschema.getSearchCode() 
							+"\" style=\"display: none;\"><label class=\"hRadio\" >全部</label></li>";
				} 
				//02以外-Y为不限
				else if ("Y".equals(SCschema.getIsMultipleChoice())&&!"02".equals(SCschema.getIntersection()))
				{
					tHTML += "<li  class=\"jiange\"><input type=\"radio\" value=\"default_"+SCschema.getSearchCode()+"_"+SCschema.getSearchLevel()+"\" name=\"" + SCschema.getSearchCode()
					+ "\" id=\""+ SCschema.getSearchCode() +"_all\"  style=\"display: none;\"><label class=\"hRadio\" >不限</label></li>";
					tHTML1 += "<li  class=\"jiange\"><input type=\"radio\" value=\"default_"+SCschema.getSearchCode()+"_"+SCschema.getSearchLevel()+"\" name=\"" + SCschema.getSearchCode()
							+ "\" id=\""+ SCschema.getSearchCode() +"_all\"  style=\"display: none;\"><label class=\"hRadio\" >不限</label></li>";
				}
				else
				{
					tHTML += "<li  class=\"jiange\"><input type=\"radio\" value=\"default_"+SCschema.getSearchCode()+"_"+SCschema.getSearchLevel()+"\" name=\"" + SCschema.getSearchCode()+"";
					tHTML1 += "<li  class=\"jiange\"><input type=\"radio\" value=\"default_"+SCschema.getSearchCode()+"_"+SCschema.getSearchLevel()+"\" name=\"" + SCschema.getSearchCode()+"";
					if("".equals(SCschema.getSubColumnCategory()) || SCschema.getSubColumnCategory()==null){
						tHTML += "\" style=\"display: none;\"><label class=\"hRadio\" >不限</label></li>";
						tHTML1 += "\" style=\"display: none;\"><label class=\"hRadio\" >不限</label></li>";
					}else{
						tHTML += "\" style=\"display: none;\"><label onclick=\"queryRecommendProduct('"+SCschema.getSubColumnCategory()+"','999')\" class=\"hRadio\" >不限</label></li>";
						if (!tERiskSubType.equals(SCschema.getSubColumnCategory()) && StringUtil.isNotEmpty(catalogUrl.get(SCschema.getSubColumnCategory()))) {
							tHTML1 += "\" style=\"display: none;\"><a href=\" "+catalogUrl.get(SCschema.getSubColumnCategory())+"\" class=\"hRadio\">不限</a>";
						} else {
							tHTML1 += "\" style=\"display: none;\"><label class=\"hRadio\" >不限</label></li>";
						}
						
					}
				}
				//拼装除不限和全部以外的子条件
				for (int j = 0; j < SCset1.size(); j++) 
				{	
					int m = j+1;
					SCschema1 = SCset1.get(j);
					if(tERiskSubType.equals(SCschema1.getSubColumnCategory())){
						tHTML += "<input id=\"hdn_"+SCschema.getSearchCode()+"\" type=\"hidden\" value=\""+SCschema1.getId()+"\" name=\"hdn_"+SCschema.getSearchCode()+"\" />"; 
						tHTML1 += "<input id=\"hdn_"+SCschema.getSearchCode()+"\" type=\"hidden\" value=\""+SCschema1.getId()+"\" name=\"hdn_"+SCschema.getSearchCode()+"\" />";
						flag = true;
					}
					if ("Y".equals(SCschema.getIsMultipleChoice())) 
					//IF判断是多选条件，多选条件单独样式
					{
						tHTML += "<li><input type=\"checkbox\" name=\""+ SCschema.getSearchCode() +"\" value=\""+ SCschema1.getId() +"\" style=\"display: none;\"><label class=\"checkbox\" >" + SCschema1.getSearchName()
								+ "</label></li>";
						tHTML1 += "<li><input type=\"checkbox\" name=\""+ SCschema.getSearchCode() +"\" value=\""+ SCschema1.getId() +"\" style=\"display: none;\"><label class=\"checkbox\" >" + SCschema1.getSearchName()
								+ "</label></li>";
					} 
					else 
					{	//境内、外旅游有子菜单，需要单独拼装样式
						if("02".equals(SCschema.getIntersection()) && "N".equals(SCschema.getIsMultipleChoice())){
							tHTML += "<li><input type=\"radio\" id=\"first"+m+"\" value=\"" + SCschema1.getId() + "\" name=\"" + SCschema.getSearchCode()
							+ "\" style=\"display: none;\"><label class=\"hRadio\" >" + SCschema1.getSearchName() + "</label></li>";
							tHTML1 += "<li><input type=\"radio\" id=\"first"+m+"\" value=\"" + SCschema1.getId() + "\" name=\"" + SCschema.getSearchCode()
									+ "\" style=\"display: none;\"><label class=\"hRadio\" >" + SCschema1.getSearchName() + "</label></li>";
						}
						else 
						{	
							tHTML += "<li><input type=\"radio\"  value=\"" + SCschema1.getId() + "\" name=\"" + SCschema.getSearchCode()
									+ "\" style=\"display: none;\">";
							tHTML1 += "<li><input type=\"radio\"  value=\"" + SCschema1.getId() + "\" name=\"" + SCschema.getSearchCode()
									+ "\" style=\"display: none;\">";
							if("".equals(SCschema1.getSubColumnCategory()) || SCschema1.getSubColumnCategory()==null){
								tHTML += " <label class=\"hRadio\" >"+SCschema1.getSearchName()+"</label></li>";
								tHTML1 += " <label class=\"hRadio\" >"+SCschema1.getSearchName()+"</label></li>";
							}else{
								tHTML += " <label onclick=\"queryRecommendProduct('"+SCschema1.getSubColumnCategory()+"','"+SCschema1.getSearchName()+"')\" class=\"hRadio\" > "+SCschema1.getSearchName()+"</label></li>";
								productTypeDesc = "";
								if (StringUtil.isNotEmpty(descMap.get(SCschema1.getSubColumnCategory()))) {
									productTypeDesc = "<em class=\"m-list-tip2\"><i class=\"tagshow\" style=\"display:none\">"+descMap.get(SCschema1.getSubColumnCategory())+"</i></em>";
								}
								if (!tERiskSubType.equals(SCschema1.getSubColumnCategory()) && StringUtil.isNotEmpty(catalogUrl.get(SCschema1.getSubColumnCategory()))) {
									tHTML1 += "<a href=\" "+catalogUrl.get(SCschema1.getSubColumnCategory())+"\" class=\"hRadio\">"+SCschema1.getSearchName()+productTypeDesc+"</a>";
								} else {
									tHTML1 += " <label class=\"hRadio\" > "+SCschema1.getSearchName()+productTypeDesc+"</label></li>";
								}
							}
						}
						if(StringUtil.isNotEmpty(SCschema1.getSubNodeNum()) && Integer.parseInt(SCschema1.getSubNodeNum())>0){
							SCset3.add(SCschema1);
							SCset2.add(SCschema2.query(new QueryBuilder("where Upperid='" + SCschema1.getId() + "' order by -SearchOrder")));
							subNode[k] = SCset2.size();
							k++;
						}
						
					}
					//mod by wangej 如果小项目的总数为4的倍数，那么最后一次循环不需要增加if中的代码 20151022
					if(j!=0 &&  (j+1)%4==0 && (j+1) <SCset1.size()){
						tHTML += " <li class=\"jiange\"></li>";
						tHTML1 += " <li class=\"jiange\"></li>";
					}
				}
				tHTML += "</ul></div>";
				tHTML1 += "</ul></div>";
				tHTML += "</div>";
				tHTML1 += "</div>";
				if("Y".equals(SCschema.getIsMultipleChoice())){
					tHTML += "</div>";
					tHTML1 += "</div>";
				}
				String tempHtml = "";
				//拼装境内境外旅游的子菜单
				if (SCset2.size()>0) 
				{	
					int m = 0;
					tempHtml += "<div class=\"jn-k\" style=\"display: none;\" id=\"CsearchConditions_"+SCschema.getSearchCode()+"_\">";
					// 境外旅游险
					if (tERiskSubType.equals("A02")) {
						tempHtml += "<div id=\"CsearchConditions_"+SCschema.getSearchCode()+"_jn-sj\" class=\"jn-sj\" style=\"margin: 0px 0px -4px 183px;\"><img alt=\"\" src=\""+Config.getFrontServerContextPath()+"/images/sj.gif\"></div><div class=\"jn-qbbg\" >";
						
					} else {
						tempHtml += "<div id=\"CsearchConditions_"+SCschema.getSearchCode()+"_jn-sj\" class=\"jn-sj\" style=\"margin: 0px 0px -4px 60px;\"><img alt=\"\" src=\""+Config.getFrontServerContextPath()+"/images/sj.gif\"></div><div class=\"jn-qbbg\" >";
					}
					
					for (int n = 0 ; n < SCset3.size(); n++) 
					{	
						tempHtml += "<ul style=\"display: block;width: 430px;\" id=\"CsearchConditions_"+SCschema.getSearchCode()+"_"+SCset3.get(n).getId()+"\">";
						tempHtml += "<li class=\"filter_width\"><input type=\"radio\" id=\""+SCschema.getSearchCode()+"_sub_"+""+SCset3.get(n).getId()+"\" value=\""+SCset3.get(n).getId()+"\" name=\""+SCschema.getSearchCode()+"_sub\" style=\"display: none;\"><label class=\"hRadio\" >全部</label></li>";
						for(; m < subNode[n]; m++){
							SCschema2 = SCset2.get(m);
							tempHtml += "<li class=\"filter_width\"><input type=\"radio\" value=\"" + SCschema2.getId() + "\" name=\""+SCschema.getSearchCode()+"_sub\" style=\"display: none;\"><label class=\"hRadio\" >" + SCschema2.getSearchName() + "</label></li>";
						}
						tempHtml += "</ul>";
					}
					tempHtml +="</div></div>";
				}
				if(!flag){
					
					tempHtml += "<input id=\"hdn_"+SCschema.getSearchCode()+"\" type=\"hidden\" value=\"default_"+SCschema.getSearchCode()+"_1\" name=\"hdn_"+SCschema.getSearchCode()+"\" />"; 
				}
				tHTML += tempHtml;
				tHTML1 += tempHtml;
			}
			tHTML += "</div>";
			tHTML1 += "</div>";

			try{
					if (!setOneSearchInfo(trans, "Catalog_SearchConditions",
							"产品筛选条件", tHTML,tCatalogID)) {
						
					}
					if (!setOneSearchInfo(trans, "Catalog_SearchConditions_new",
							"新版产品筛选条件", tHTML1,tCatalogID)) {
						
					}
					if (!trans.commit()) {
					
					}
				} catch (Exception e) {
					logger.error("筛选条件保存提交出现异常："+e.getMessage(), e);
				}
			}
		}
	}
	
	public static boolean setOneSearchInfo(Transaction trans, String tColumnCode,
			String tColumnName, String tTextValue,String mCatalogID) {
		ZDColumnRelaSchema rela = new ZDColumnRelaSchema();
		rela.setRelaType(ColumnUtil.RELA_TYPE_CATALOG_EXTEND);
		rela.setRelaID(String.valueOf(mCatalogID));
		rela.setColumnCode(tColumnCode);
		ZDColumnRelaSet relaSet = rela.query();
		if (relaSet != null && relaSet.size() > 0) {
			rela = relaSet.get(0);
			rela.setModifyTime(new Date());
			rela.setModifyUser(User.getUserName());
		} else {
			rela.setID(NoUtil.getMaxID("ColumnRelaID"));
			rela.setColumnID(NoUtil.getMaxID("ColumnID"));
			rela.setAddTime(new Date());
			rela.setAddUser(User.getUserName());
			rela.setModifyTime(rela.getAddTime());
			rela.setModifyUser(rela.getAddUser());
		}

		ZDColumnSchema column = new ZDColumnSchema();
		column.setID(rela.getColumnID());
		column.setCode(tColumnCode);
		column.setName(tColumnName);
		column.setSiteID(Application.getCurrentSiteID());
		column.setInputType(CatalogExtend.HtmlInput);
		column.setVerifyType(ColumnUtil.STRING);
		column.setIsMandatory("N");
		column.setOrderFlag(OrderUtil.getDefaultOrder());
		column.setAddTime(rela.getAddTime());
		column.setAddUser(rela.getAddUser());
		rela.setModifyTime(rela.getAddTime());
		rela.setModifyUser(rela.getAddUser());

		ZDColumnValueSchema value = new ZDColumnValueSchema();
		value.setColumnID(rela.getColumnID());
		value.setColumnCode(rela.getColumnCode());
		value.setRelaType(ColumnUtil.RELA_TYPE_CATALOG_EXTEND);
		value.setRelaID(String.valueOf(mCatalogID));
		ZDColumnValueSet valueSet = value.query();
		if (valueSet != null && valueSet.size() > 0) {
			value = valueSet.get(0);
		} else {
			value.setID(NoUtil.getMaxID("ColumnValueID"));
		}
		value.setTextValue(tTextValue);

		ZCCatalogSchema catalog = CatalogUtil.getSchema(mCatalogID);
		catalog.setModifyTime(new Date());
		catalog.setModifyUser(User.getUserName());
		trans.add(catalog, Transaction.UPDATE);

		trans.add(column, Transaction.DELETE_AND_INSERT);
		trans.add(rela, Transaction.DELETE_AND_INSERT);
		trans.add(value, Transaction.DELETE_AND_INSERT);
		return true;
	}
	
	/**
	 * 检验前台传来的值是否是多选
	 * @param tconditionsplit 前台传递的字符串经过拆分后得到字符串
	 * @return
	 */
	public static String ValueCheck(String tconditionsplit)
	{
		String sql = "select IsMultipleChoice from FEMSearchConditionInfo where SearchCode=? ";
		QueryBuilder qb = new QueryBuilder(sql, tconditionsplit);
		String IsMultipleChoice = (String) qb.executeOneValue();
		return IsMultipleChoice;
	}
	
	
	/**
	 * 检验前台传来的值是交集还是并集
	 * @param tconditionsplit 前台传递的字符串经过拆分后得到字符串
	 * @return
	 */
	public static String ValueCheck1(String tconditionsplit)
	{
		String sql = "select Intersection from FEMSearchConditionInfo where SearchCode=? ";
		QueryBuilder qb = new QueryBuilder(sql, tconditionsplit);
		String Intersection = (String) qb.executeOneValue();
		return  Intersection;
	}
	
	public static void main(String[] args) {
		updateSearchDate();
	}
	private static boolean updateSearchDate() {
		// 1,查询数据库zdconde表 ,需要替换的筛选条件
		String zdSql = "select codevalue,codename from zdcode where codetype='auto_index' and parentcode='auto_index'";
		DataTable zdDB = new QueryBuilder(zdSql).executeDataTable();
		for (int k = 0; zdDB != null && k < zdDB.getRowCount(); k++) {
			String codeValue = zdDB.getString(k, 0);
			String codeName = zdDB.getString(k, 1);
			SDSearchCacheSchema sdSchema = new SDSearchCacheSchema();
			String sql4 = "select id from SDSearchCache where conditionCode='"
					+ codeValue + "'";
			Object db4 = new QueryBuilder(sql4).executeOneValue();
			if (StringUtil.isNotEmpty(db4 + "")) {
				sdSchema.setID(db4 + "");
				if (sdSchema.fill()) {
					sdSchema.setCacheURL(codeName);
					sdSchema.update();
				}
			}
		}
		return false;
	}
	/**
	 * 搜索结果排序调用方法
	 */
	@SuppressWarnings("unchecked")
	public void SearchOrder(){		
		String productOrder = Request.getString("ProductsOrder");
//		String queryStr = Request.getString("queryStr");
//		int page =Integer.parseInt(Request.getString("page")) ;
		int pageindex = Request.getInt("pageIndex");
		if(pageindex!=0){
			pageindex = pageindex - 1;
		}
		int nextPage = pageindex + 2;
		int start = pageindex*10;
		List<DataRow> alist = new ArrayList<DataRow>();
		DataTable dt1 = (DataTable)User.getValue("dt");
		int total = dt1.getRowCount();
		int pageCount = new Double(Math.ceil(total * 1.0 / 10)).intValue();
		DataTable dt = new DataTable();
		for(int i=0;i<dt1.getRowCount();i++){
			alist.add(dt1.get(i));
		}
		if(StringUtil.isNotEmpty(productOrder)){
			String[] str1 = productOrder.split(" ");
			SortList<DataRow> sortList = new SortList<DataRow>();
			sortList.Sort(alist,str1[0],str1[1]);   	
		}
		for(DataRow dt2:alist){
			dt.insertRow(dt2);	
		}
		StringBuffer sb = new StringBuffer();
		String sb1 = "";
		StringBuffer sb2 = new StringBuffer();
		String sb3 = "";
	try{	
		for(int i=start;i<start+10&&i<dt.getRowCount();i++){
			
			sb.append("<div class=\"nlist_con cf\">");
			//活动
			sb.append("<div class=\"shop_nlist_img\">");
			//是否有满减活动
			FilterAction filteraction = new FilterAction();
			String productid=dt.get(i).getString("ProductID");
			Map<String,String> map=filteraction.searchProductListAvtivity(dt.get(i).getString("RiskCode"),"wj");
			String diyactivity="";
			if(map==null||map.size()==0){
				sb.append(""+dt.get(i).getString("ProductActive")+"");
			}else{
				for (String key : map.keySet()) {
					String value=map.get(key);
					String type=key.substring(key.indexOf("@")+1,key.length());
					if(!"8".equals(type)){//非自定义活动
						sb.append(value);
						break;
					}else{
						String title=value.substring(0,value.indexOf("@"));
						String description=value.substring(value.indexOf("@")+1,value.length());
						if(description.length()>20){
							description=description.substring(0, 20);
						}
						diyactivity="<em class=\"list_hddes\"  id=\"Diy_em_Activity_"+productid +"\" > <i class=\"list_hd_t\" id=\"Diy_Title_Activity_"+productid+"\">"+title+"</i><span id=\"Diy_Activity_"+productid+"\" >"+description+"</span></em>";
					}
				}
				
			}
			//title
			sb.append("<a rel=\"nofollow\" target=\"_blank\" href=\""+dt.get(i).getString("URL")+"\"><img width=\"190\" height=\"190\" alt=\""+dt.get(i).getString("Title")+"\" src=\""+dt.get(i).getString("LogoLink")+"\" class=\"lazy\" data-original=\""+dt.get(i).getString("LogoLink")+"\" style=\"display: inline;\"></a>");
			sb.append("</div>");
			sb.append("<div class=\"nlist_des\">");
			sb.append("<a href=\""+dt.get(i).getString("URL")+"\" target=\"_blank\" rel=\"nofollow\" class=\"shop_pos_a\"></a>");
			sb.append(diyactivity);
			//适合人群
			sb.append("<div class=\"shop_tj_shrq cf\"> <span class=\"shop_shrq_bg\">适合人群</span>");
			sb.append("<p class=\"shop_shrd_con\">"+dt.get(i).getString("AdaptPeopleInfoListV3")+"</p></div>");
            sb.append("<div class=\"clear\"></div>");
            //保险责任
            sb.append("<ul class=\"recommend_list\">");
            sb.append(""+dt.get(i).getString("DutyHTMLV3")+"</ul>");
            //价格
			sb.append("<div class=\"nlist_price\">");
			sb.append("<span class=\"nlist_pay moneys\">"+dt.get(i).getString("InitPrem")+"</span><em class='nlist_pay_t moneys'>"+dt.get(i).getString("BasePremV3")+"</em>");
			sb.append("<div class=\"price_tj\">");
			sb.append("<span class=\"recom_xl\">已有"+dt.get(i).getString("SalesVolume")+"人投保</span>");
			sb.append("<span class=\"recom_xl\"><i>（<a class=\"shop_tj_num\" href="+dt.get(i).getString("URL")+" target=\"_blank\">"+SearchAPI.getCommentCount(dt.get(i).getString("RiskCode"))+"</a>） </i> 条评论</span>");
			sb.append("</div>");
			
			
			sb.append("<span class=\"remcon_desmore\"><label class=\"nlist_add_db\">");
			sb.append("<input type=\"checkbox\" onclick=\"showcp('" + dt.get(i).getString("Title") + "','" + dt.get(i).getString("logo") + "','" + dt.get(i).getString("RiskCode") + "','"+Config.getServerContext() + "','" + dt.get(i).getString("ProductType") + "','"+dt.get(i).getString("LogoLink")+"','"+dt.get(i).getString("InitPrem")+"');\">加入对比</label>");
			sb.append("<a target=\"_blank\" href="+dt.get(i).getString("URL")+">去看看</a></span>");
			
			sb.append("</div></div></div>");
		}
		sb1 = sb.toString();
		sb2.append("<table width='100%' border='0' class='noBorder' align='center'><tr>");
		sb2.append("<td height='18' align='center' valign='middle' style='border-width: 0px;color:#525252'>");
		sb2.append("共" + total + "条记录，每页" + 10 + "条，当前第<span class='fc_ch1'>" + ((total == 0) ? 0: (pageindex + 1)) 
				+ "</span>/<span class='fc_ch1'>" + pageCount + "</span>页&nbsp;&nbsp;&nbsp;&nbsp;");
		if (pageindex > 0) {
				
			sb2.append("<a href='javascript:void(0)' onClick='doOrder2(\""+1+"\");'><span class='fc_ch1'>第一页</span></a>|");
			if(1==pageindex){
				sb2.append("<a href='javascript:void(0)' onClick='doOrder2(\""+pageindex+"\");'><span class='fc_ch1'>上一页</span></a>|");
			}else{
				sb2.append("<a href='javascript:void(0)' onClick='doOrder2(\""+pageindex+"\");'><span class='fc_ch1'>上一页</span></a>|");
			}
			
		} else {
			sb2.append("<span class='fc_hui2'>第一页</span>|");
			sb2.append("<span class='fc_hui2'>上一页</span>|");
		}
		if (pageindex + 1 != pageCount && pageCount>0) {
			sb2.append("<a href='javascript:void(0)' onClick='doOrder2(\""+nextPage+"\");'><span class='fc_ch1'>下一页</span></a>|");
			sb2.append("<a href='javascript:void(0)' onClick='doOrder2(\""+pageCount+"\");'><span class='fc_ch1'>最末页</span></a>");
		} else {
			sb2.append("<span class='fc_hui2'>下一页</span>|");
			sb2.append("<span class='fc_hui2'>最末页</span>");
		}

		sb2.append("&nbsp;&nbsp;转到第<input id='_PageBar_Index_' type='text' size='4' style='width:30px' ");
		sb2.append("style='' onKeyUp=\"value=value.replace(/\\D/g,'')\">页");
		
		sb2.append("&nbsp;&nbsp;<input type='button' onclick=\"if(/[^\\d]/.test(document.getElementById('_PageBar_Index_').value)){alert('错误的页码');$('_PageBar_Index_').focus();}else if(document.getElementById('_PageBar_Index_').value>" + pageCount + "){alert('错误的页码');document.getElementById('_PageBar_Index_').focus();}else{var PageIndex = (document.getElementById('_PageBar_Index_').value)>0?document.getElementById('_PageBar_Index_').value:1;if(PageIndex==1){newJump();}else{newJump();}}\" style='' value='跳转'></td>");
		sb2.append("</tr></table>");
//		sb2.append("<input type='hidden' name='newPageIndex' value='"+pageindex+++"' id='newPageIndex' />");
		sb3 = sb2.toString();
		
		Response.setStatus(1);
		Response.put("sb1",sb1);
		Response.put("sb3",sb3);
	}catch(Exception e){
		Response.setStatus(0);
		logger.error("搜索排序方法出现异常："+e.getMessage(), e);
		}
	}
	

	
	/**
	 * 跳转调用方法
	 */
	@SuppressWarnings("unchecked")
	public void Jump(){
		try {
			String JumpPage = Request.getString("JumpPage");
			String query1 = Request.getString("query");
			String query = query1.substring(0, 6) + StringUtil.leftPad(JumpPage + "", '0', 6);
			GetDBdata db = new GetDBdata();
			String path = "";
			path = db.getOneValue("select value from zdconfig where type='FrontServerContextPath'");
			String tagHtml = path + "/keylist/" +query + ".shtml";
//			String tagHtml = path + "/wj/Search/SearchKeyList.jsp?query=" + query+ "&page=" + JumpPage + "&id=50&site=221";
			Response.setStatus(1);
			Response.put("tagHtml",tagHtml);
		} catch (Exception e) {
			Response.setStatus(0);
			logger.error("跳转方法出现异常："+e.getMessage(), e);
		}
		
	}
	
	public void syncPointProduct() {
		// 产品编码
		String productID = $V("ProductID");
		if (StringUtil.isEmpty(productID)) {
			Response.setLogInfo(0, "请填写产品编码！");
			return;
		}
		// 积分兑换保险产品栏目ID
		String catalogID = Config.getValue("PointProductCatalogID");
		if (StringUtil.isEmpty(catalogID)) {
			Response.setLogInfo(0, "请在系统管理-配置项管理 设置积分兑换保险产品栏目ID！PointProductCatalogID");
			return;
		}
		
		CmsServiceImpl csi = new CmsServiceImpl();
		if (csi.publishArticle(productID, catalogID)) {
			Response.setLogInfo(1, "同步成功");
		} else {
			Response.setLogInfo(0, "同步失败");
		}
	}
	
	public void syncPointProductArea() {
		// 产品编码
		String productID = $V("ProductID");
		if (StringUtil.isEmpty(productID)) {
			Response.setLogInfo(0, "请填写产品编码！");
			return;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("RiskCode", new String[] { productID });
		try {
			ProductInfoResponse productInfo = ProductWebservice.ProductInfoSereviceImpl(map, null);
			FMRisk[] list = productInfo.getFMRisk();
			if (list != null && list.length > 0 && list[0] != null) {
				try {
					if (AutomaticPublishArticle.publishingArea(list[0])) {
						Response.setLogInfo(1, "同步成功");
					} else {
						Response.setLogInfo(0, "同步失败");
					}
				} catch (Exception e) {
					logger.error("同步产品销售地区失败：" + list[0].getRiskName() + e.getMessage(), e);
					Response.setLogInfo(0, "同步失败!");
				}
			} else  {
				Response.setLogInfo(0, "同步失败!");
			}
		} catch (Exception e) {
			logger.error("同步产品信息失败！" + e.getMessage(), e);
			Response.setLogInfo(0, "同步失败!");
		}
	}
	
	public void syncPointProductHI() {
		// 产品编码
		String productID = $V("ProductID");
		if (StringUtil.isEmpty(productID)) {
			Response.setLogInfo(0, "请填写产品编码！");
			return;
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("RiskCode", new String[] { productID });
		try {
			ProductInfoResponse productInfo = ProductWebservice.ProductInfoSereviceImpl(map, null);
			FMRisk[] list = productInfo.getFMRisk();
			if (list != null && list.length > 0 && list[0] != null) {
				try {
					if (AutomaticPublishArticle.publishingHI(list[0])) {
						Response.setLogInfo(1, "同步成功");
					} else {
						Response.setLogInfo(0, "同步失败");
					}
				} catch (Exception e) {
					logger.error(
							"同步产品健康告知失败：" + list[0].getRiskName() + e.getMessage(), e);
					Response.setLogInfo(0, "同步失败!");
				}
			} else  {
				Response.setLogInfo(0, "同步失败!");
			}
		} catch (Exception e) {
			logger.error("同步产品信息失败！" + e.getMessage(), e);
			Response.setLogInfo(0, "同步失败!");
		}
	}
}
