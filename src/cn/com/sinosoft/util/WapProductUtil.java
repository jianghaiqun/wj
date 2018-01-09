package cn.com.sinosoft.util;

import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.SDOrder;
import cn.com.sinosoft.service.MemberService;
import cn.com.sinosoft.service.SDOrderService;
import com.sinosoft.cms.api.SearchAPI;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.points.IntegralConstant;
import com.sinosoft.points.PointsCalculate;
import com.sinosoft.product.Filtrate;
import com.sinosoft.product.SortList;
import com.sinosoft.schema.FEMSearchConditionInfoSchema;
import com.sinosoft.schema.FEMSearchConditionInfoSet;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WapProductUtil {
	private static final Logger logger = LoggerFactory.getLogger(WapProductUtil.class);
	/**
	 * @Method: wapGetProductList
	 * @Description: 产品列表接口
	 * @param tPARAMETERS
	 * @return Map<String,Object>
	 * @throws:
	 * @MakeDate:2014-2-17 下午02:10:56
	 */
	public static Map<String, Object> wapGetProductList(
			Map<String, Object> tPARAMETERS) {

		// 获取请求参数ID、SubRiskType、FilterParams、OrderBy
		String ID = String.valueOf(tPARAMETERS.get("ID"));// sdsearchaddress id
		String subRiskType = String.valueOf(tPARAMETERS.get("SubRiskType"));// 产品归属
		String tFilterParams = String.valueOf(tPARAMETERS.get("FilterParams"));// 筛选条件
		String tOrderBy = String.valueOf(tPARAMETERS.get("OrderBy"));// 排序条件
		String tOrderByType = String.valueOf(tPARAMETERS.get("OrderByType"));// 排序条件
		String tFrom = String.valueOf(tPARAMETERS.get("From"));// 排序条件
		List<String> arrayList = new ArrayList<String>();
		DataTable dt = new DataTable();

		String contions = "";

		if (StringUtil.isNotEmpty(subRiskType) && StringUtil.isNotEmpty(ID)) {
			contions = SearchAPI.getSearchID(subRiskType, ID);
		} else {
			arrayList.add("WapProduct00001");
			return WapErrorUtil.dealErrorInfo(arrayList, "KXBJRT0008", null);
		}

		if (StringUtil.isNotEmpty(tFilterParams)) {
			try {
				contions = getContions(tFilterParams);
			} catch (Exception e) {
				arrayList.add("WapProduct00002");
				return WapErrorUtil
						.dealErrorInfo(arrayList, "KXBJRT0008", null);
			}
		}

		try {
			dt = getDt_ProductIds(contions, subRiskType, tOrderBy, tOrderByType,tFrom);
		} catch (Exception e) {
			arrayList.add("WapProduct00003");
			return WapErrorUtil.dealErrorInfo(arrayList, "KXBJRT0008", null);
		}
		// 返回的 data 和 排序类型相反
		int rowCount = dt.getRowCount();

		// 存放产品编码
		JSONArray prodsInfo = new JSONArray();
		// List<String> prodsInfo = new ArrayList<String>();
		Map<String, Object> prodsIdMap = new HashMap<String, Object>();
		if (rowCount > 0) {

			for (int i = rowCount; i > 0; i--) {

				prodsInfo.put(dt.get(i - 1, 0) + "-" + dt.get(i - 1, 2));
			}
		}
		prodsIdMap.put("ProdCode", prodsInfo);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("USER", "");
		resultMap.put("REQUESTTYPE", "KXBJRT0007");
		resultMap.put("STATYS", "true");
		resultMap.put("RESULTS", prodsIdMap);
		return resultMap;

	}

	/**
	 * 代理人微信产品列表
	 * 
	 * @param tPARAMETERS
	 * @return
	 */
	public static Map<String, Object> wapGetDlrRecProductList(
			Map<String, Object> tPARAMETERS) {

		String userCode = String.valueOf(tPARAMETERS.get("CPSUserID"));

		StringBuffer sbSerch = new StringBuffer();
		sbSerch.append(" SELECT b.ProductId FROM ZDSetProductInformation a,ZDProductInformation b  ");
		sbSerch.append(" WHERE a.ProductId = b.ProductId AND a.CPSOnlineFlag = 'Y'");
		sbSerch.append(" AND EXISTS (SELECT productId FROM SDProductRecommend  ");
		sbSerch.append(" WHERE productid = b.ProductId ");
		sbSerch.append(" AND usercode = '" + userCode + "'");
		sbSerch.append(" ) ");
		sbSerch.append(" ORDER BY a.cpsmodifydate DESC LIMIT 12  ");
		QueryBuilder qb0 = new QueryBuilder(sbSerch.toString());

		DataTable dt = qb0.executeDataTable("CPSAGENT");
		if (dt == null || dt.getRowCount() <= 0) {

			sbSerch = new StringBuffer();
			sbSerch.append(" SELECT b.* FROM ZDSetProductInformation a, ZDProductInformation b   ");
			sbSerch.append(" WHERE a.ProductId=b.ProductId AND a.CPSOnlineFlag='Y'  AND a.CPSIsRecommend='Y' ");
			sbSerch.append(" order by Commissionrates desc LIMIT 8  ");
			sbSerch.append(" WHERE productid = b.ProductId ");

			qb0 = new QueryBuilder(sbSerch.toString());
			dt = qb0.executeDataTable("CPSAGENT");

		}
		// 返回的 data 和 排序类型相反
		int rowCount = dt.getRowCount();

		// 存放产品编码
		JSONArray prodsInfo = new JSONArray();
		// List<String> prodsInfo = new ArrayList<String>();
		Map<String, Object> prodsIdMap = new HashMap<String, Object>();
		if (rowCount > 0) {

			for (int i = rowCount; i > 0; i--) {

				prodsInfo.put(dt.get(i - 1, 0));
			}
		}
		prodsIdMap.put("ProdCode", prodsInfo);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("USER", "");
		resultMap.put("REQUESTTYPE", "KXBJRT0007");
		resultMap.put("STATYS", "true");
		resultMap.put("RESULTS", prodsIdMap);
		return resultMap;

	}

	/**
	 * @Method: wapGetProductFilters
	 * @Description: 产品筛选接口
	 * @param tPARAMETERS
	 * @return:Map
	 * @throws:
	 * @MakeDate:2014-2-18 : 上午11:41:54
	 */
	public static Map<String, Object> wapGetProductFilters(
			Map<String, Object> tPARAMETERS) {

		String ID = String.valueOf(tPARAMETERS.get("ID"));// sdsearchaddress id
		String subRiskType = String.valueOf(tPARAMETERS.get("SubRiskType"));// 产品归属
		String searchCode = String.valueOf(tPARAMETERS.get("SearchCode"));// 产品归属

		List<String> arrayList = new ArrayList<String>();
		Map<String, Object> productFiltersMap = new HashMap<String, Object>();

		if (StringUtil.isNotEmpty(subRiskType)) {

			// TODO 校验请求参数是否合理 可能 SubRiskType和 SearchCode不是同一级的筛选条件 目前不加校验
			try {
				productFiltersMap = getProdConditions(ID, subRiskType,
						searchCode);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				arrayList.add("WapProduct00003");
				return WapErrorUtil
						.dealErrorInfo(arrayList, "KXBJRT0008", null);
			}

		} else {
			arrayList.add("WapProduct00001");
			return WapErrorUtil.dealErrorInfo(arrayList, "KXBJRT0008", null);
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("USER", "");
		resultMap.put("REQUESTTYPE", "KXBJRT0008");
		resultMap.put("STATYS", "true");
		resultMap.put("RESULTS", productFiltersMap);

		return resultMap;
	}

	/**
	 * @Method: getProdConditions
	 * @Description:
	 * @param subRiskType
	 *            A00,B00,D00
	 * @return 对应type下的所有条件列表
	 * @throws Exception
	 *             Map<String,Object>
	 * @throws: Exception
	 * @MakeDate:2014-2-19 : 上午11:42:16
	 */
	private static Map<String, Object> getProdConditions(String ID,
			String subRiskType, String searchCode) throws Exception {

		Map<String, Object> productFiltersMap = new HashMap<String, Object>();

		String nERiskSubType = subRiskType.substring(0, 1);

		// 根据险种类型查询出所有一级查询条件
		FEMSearchConditionInfoSchema SCschema = new FEMSearchConditionInfoSchema();
		FEMSearchConditionInfoSet SCset = new FEMSearchConditionInfoSet();
		SCset = SCschema
				.query(new QueryBuilder(
						"where SearchLevel='1' and ERiskType=? order by extra3 DESC,-SearchOrder",
						nERiskSubType));

		JSONArray filtersInfo = new JSONArray();
		// 一级筛选条件
		for (int i = 0; i < SCset.size(); i++) {
			SCschema = SCset.get(i);

			Map<String, Object> filtersMap = new HashMap<String, Object>();
			filtersMap.put("ModuleName", SCschema.getSearchName());
			filtersMap.put("ModuleID", SCschema.getSearchCode());

			// 显示 不限、全部
			// IF判断是多选而且是并集时 02—Y为全部
			if ("02".equals(SCschema.getIntersection())
					&& "Y".equals(SCschema.getIsMultipleChoice())) {
				filtersMap.put("ModuleRCShowName", "全部");
			} else {
				filtersMap.put("ModuleRCShowName", "不限");
			}
			filtersMap.put("ModuleRCSelectType", "radio");
			filtersMap.put(
					"ModuleRCValue",
					"default_" + SCschema.getSearchCode() + "_"
							+ SCschema.getSearchLevel());
			// 2 表示选中 在这里默认选中、后面操作子节点时如果有选中的话将其置为 1 不选中
			filtersMap.put("ModuleRCOption", "2");

			if ("N".equals(SCschema.getIsMultipleChoice())) {
				filtersMap.put("SelectType", "1");
			} else {
				filtersMap.put("SelectType", "2");
			}

			// 根据upperID 查询二及以下的级查询条件
			FEMSearchConditionInfoSchema SCschema1 = new FEMSearchConditionInfoSchema();
			FEMSearchConditionInfoSet SCset1 = new FEMSearchConditionInfoSet();
			SCset1 = SCschema1.query(new QueryBuilder("where UpperId='"
					+ SCschema.getId() + "' order by -SearchOrder"));
			JSONArray filterItemInfo = new JSONArray();
			for (int j = 0; j < SCset1.size(); j++) {

				SCschema1 = SCset1.get(j);
				Map<String, Object> filterItemsMap = new HashMap<String, Object>();
				filterItemsMap.put("UpperID", SCschema1.getUpperId());
				filterItemsMap.put("ItemLever", SCschema1.getSearchLevel());
				filterItemsMap.put("ItemName", SCschema1.getSearchName());
				// filterItemsMap.put("ItemValue", SCschema1.getSearchCode());
				filterItemsMap.put("ItemValue", SCschema1.getId());
				if (searchCode.equalsIgnoreCase(SCschema1.getSearchCode())
						|| subRiskType.equalsIgnoreCase(SCschema1
								.getSearchCode())) {
					filterItemsMap.put("SelectOption", "2");
					filtersMap.put("ModuleRCOption", "1");
				} else {
					filterItemsMap.put("SelectOption", "1");
				}

				// filtersMap.put("FilterItems", filterItemInfo);

				// 三级查询条件 目前只有国内外旅游有三级条件
				FEMSearchConditionInfoSchema SCschema2 = new FEMSearchConditionInfoSchema();
				FEMSearchConditionInfoSet SCset2 = new FEMSearchConditionInfoSet();
				SCset2 = SCschema2.query(new QueryBuilder("where UpperId='"
						+ SCschema1.getId() + "' order by -SearchOrder"));
				filterItemsMap.put("Children", "");
				int subNodeNum = Integer.parseInt(SCschema1.getSubNodeNum());
				if (subNodeNum > 0) {

					// 返回三级节点 全部（目前写死的）
					Map<String, Object> filterItemsMap1 = null;
					if (SCschema1.getSearchCode().equals("A01")
							|| SCschema1.getSearchCode().equals("A02")) {

						filterItemsMap1 = new HashMap<String, Object>();

						filterItemsMap1.put("UpperID", SCschema1.getId());
						filterItemsMap1.put("ItemLever", "3");
						filterItemsMap1.put("ItemName", "全部");
						filterItemsMap1.put("ItemValue", SCschema1.getId());
						/*
						 * if(StringUtil.isNotEmpty(searchCode) ||
						 * "wp0000000001".equals(ID) ||
						 * "wp0000000002".equals(ID)){ // 默认三级条件全部是选中的
						 * ,在后面循环操作中判断是否有子节点选中如有 在那里将其置为1不选中 // 例、 searchCode =
						 * dongnanya:subRiskType = A01 // 就要求全部不选中东南亚选中
						 * filterItemsMap1.put("SelectOption", "1"); }else{
						 * filterItemsMap1.put("SelectOption", "2"); }
						 */
						filterItemsMap1.put("SelectOption", "1");
						if (SCschema1.getSearchCode().equals(searchCode)) {
							filterItemsMap1.put("SelectOption", "2");
						}
						// filterItemInfo.put(filterItemsMap);
					}
					// 三级筛选条件
					JSONArray filterItemInfo3 = new JSONArray();
					for (int k = 0; k < SCset2.size(); k++) {

						SCschema2 = SCset2.get(k);

						Map<String, Object> filterItemsMap2 = new HashMap<String, Object>();
						filterItemsMap2.put("UpperID", SCschema2.getUpperId());
						filterItemsMap2.put("ItemLever",
								SCschema2.getSearchLevel());
						filterItemsMap2.put("ItemName",
								SCschema2.getSearchName());
						// filterItemsMap.put("ItemValue",
						// SCschema2.getSearchCode());
						filterItemsMap2.put("ItemValue", SCschema2.getId());

						// 三级节点默认都不选中
						if (searchCode.equals(SCschema2.getSearchCode())) {
							filterItemsMap2.put("SelectOption", "2");
							// 将上面设置覆盖掉
							filterItemsMap1.put("SelectOption", "1");
						} else {
							filterItemsMap2.put("SelectOption", "1");
						}
						filterItemsMap2.put("Children", "");
						filterItemInfo3.put(filterItemsMap2);
						// filterItemInfo.put(filterItemsMap);
					}
					filterItemInfo3.put(filterItemsMap1);
					filterItemsMap.put("Children", filterItemInfo3);
					// filterItemsMap.put("Children", filterItemsMap1);
					// filterItemInfo.put(filterItemsMap1);
				}
				filterItemInfo.put(filterItemsMap);
			}
			filtersMap.put("FilterItems", filterItemInfo);
			filtersInfo.put(filtersMap);
		}

		productFiltersMap.put("Filters", filtersInfo);

		return productFiltersMap;
	}

	/**
	 * 
	 * @Method: getContions
	 * @Description: 解析数据 例
	 *               //Age,default_Age_1;FeaturesRisk,00016,00019,88888;baoxiangongsi
	 *               ,default_baoxiangongsi_1
	 * @param tFilterParams
	 * @return searchId 形式的条件数据
	 * @throws: Exception
	 * @MakeDate:2014-2-17 : 下午11:35:33
	 */
	public static String getContions(String tFilterParams) throws Exception {
		String condtion = "";

		String[] paramArr = tFilterParams.split(";");

		for (int i = 0; i < paramArr.length; i++) {
			String[] conditionArr = paramArr[i].split(",");
			if (conditionArr.length == 2) {
				condtion += "|" + conditionArr[1];
			} else {
				for (int j = 1; j < conditionArr.length; j++) {
					if (j == 1) {
						condtion += "|" + conditionArr[j];
					} else {
						condtion += "," + conditionArr[j];
					}
				}
			}
		}
		if (condtion != "") {
			condtion = condtion.substring(1);
		}

		return condtion;
	}

	public static DataTable getDt_ProductIds(String condtion,
			String ProductType, String tOrderBy, String tOrderByType,String tFrom)
			throws Exception {

		DataTable dt0 = new DataTable();

		String condtion1 = "";
		String a = ProductType;// 将原始数据保存

		// 拆封条件，去查询产品中心数据库，获取产品编码

		ProductType = ProductType.substring(0, 1);
		String[] str3 = condtion.split("\\|");
		for (int j = 0; j < str3.length; j++) {
			if (str3[j].indexOf(",") != -1) {
				String[] str4 = str3[j].split(",");
				for (int k = 0; k < str4.length; k++) {
					if ("N".equals(Filtrate.ValueCheck(str4[k]))
							&& "01".equals(Filtrate.ValueCheck1(str4[k]))) {
						str3[j] = str4[k + 1];
						break;
					}
				}
			}
		}
		for (int j = 0; j < str3.length; j++) {
			condtion1 += str3[j];
			if (j != str3.length - 1) {
				condtion1 += "|";
			}
		}
		String catalogidSql = "select id from zccatalog where productType=?";
		DataTable catalogDt = new QueryBuilder(catalogidSql, a)
				.executeDataTable();
		String catalogId = "";
		if (catalogDt.getRowCount() > 0) {
			catalogId = catalogDt.getString(0, 0);
		}
		condtion = condtion1;
		String[] conditionsplit = condtion.split("\\|");
		boolean balanceFlag = false;
		StringBuffer sbSerch = new StringBuffer();
		sbSerch.append("select distinct c.productId,e.initPrem,e.SalesVolume from zcarticle a,sdproduct c,sdsearchrelaproduct e,productrelainfo f ");
		sbSerch.append(" where a.catalogid='" + catalogId
		// + "' and a.status='30' "
		// +//wap站不需要主站cms文章是发布状态，只需要根据产品是否上架状态判断
				+ "' AND (a.id = e.prop1 or e.Prop1=a.ReferSourceID) and c.productID=e.productID and f.productID = c.productID");

		for (int j = 0; j < conditionsplit.length; j++) {
			if ((conditionsplit[j]).startsWith("default_")) {
				continue;
			} else {
				balanceFlag = true;
				if (conditionsplit[j].indexOf(",") != 0) {
					String[] conditionsplit2 = conditionsplit[j].split(",");
					for (int k = 0; k < conditionsplit2.length; k++) {
						if ("Y".equals(Filtrate.ValueCheck(conditionsplit2[k]))
								&& "02".equals(Filtrate
										.ValueCheck1(conditionsplit2[k]))) {
							sbSerch.append(" and exists (select 1 from FEMProductRelaCondition where searchcode in ("
									+ conditionsplit[j]
									+ ") and riskcode = e.productID) ");
							break;
						} else {
							sbSerch.append(" and exists (select 1 from FEMProductRelaCondition where searchcode ='"
									+ conditionsplit2[k]
									+ "' and riskcode = e.productID)  ");
						}
					}
				} else {
					sbSerch.append(" and exists (select 1 from FEMProductRelaCondition where searchcode='"
							+ conditionsplit[j]
							+ "' and riskcode = e.productID)  ");
				}
			}
		}
		if (!balanceFlag) {
			sbSerch.append(" and c.productType = '" + ProductType + "'  ");
		} 
		sbSerch.append(" AND c.producttype IN ('A','B','C','D','E') and c.IsPublish = 'Y' and f.IsPublish = 'Y' ");
		// 2014-11-12 wap站健康告知上线
		// sbSerch.append(" AND NOT EXISTS ( SELECT 1 FROM healthyInfo WHERE ProductId =c.productid)  ");
		sbSerch.append(" AND NOT EXISTS ( SELECT 1 FROM zdconfig WHERE TYPE = 'questionPaper' AND VALUE=c.productid) ");

		// if(StringUtil.isNotEmpty(tOrderBy)){
		// sbSerch.append(" order by a.topflag desc ,a.orderflag desc");
		// }
		String strSql = "";
		if("cps".equals(tFrom)){
			strSql = " and c.productId in (";
			DataTable strDt = new QueryBuilder("SELECT productid FROM cps_agent.ZDSetProductInformation WHERE WAPOnlineFlag = 'Y'").executeDataTable();
			if(strDt.getRowCount()>0){
				for(int f=0;f<strDt.getRowCount();f++){
					if(f==0){
						strSql += "'"+strDt.getString(f, 0)+"'";
					}else{
						strSql += ",'"+strDt.getString(f, 0)+"'";
					}
				}
			}
			strSql += ")";
		}
		sbSerch.append(strSql+" order by f.Prop1 ASC,f.Modifydate ASC,a.topflag ,a.orderflag ");

		QueryBuilder qb0 = new QueryBuilder(sbSerch.toString());

		dt0 = qb0.executeDataTable();

		// 排序
		if (dt0.getRowCount() > 0 && StringUtil.isNotEmpty(tOrderBy)
				&& StringUtil.isNotEmpty(tOrderByType)) {

			DataTable dt1 = new DataTable();
			List<DataRow> alist = new ArrayList<DataRow>();
			for (int i = 0; i < dt0.getRowCount(); i++) {
				alist.add(dt0.get(i));
			}
			SortList<DataRow> sortList = new SortList<DataRow>();
			tOrderBy = tOrderBy.toLowerCase();
			tOrderByType = tOrderByType.toLowerCase();
			sortList.Sort(alist, tOrderBy, tOrderByType);
			for (DataRow dt3 : alist) {
				dt1.insertRow(dt3);
			}

			return dt1;

		} else {
			return dt0;
		}

	}

	/**
	 * @Method: wapGetProductList
	 * @Description: 产品列表接口
	 * @param tPARAMETERS
	 * @return Map<String,Object>
	 * @throws:
	 * @MakeDate:2014-2-17 下午02:10:56
	 */
	public static Map<String, Object> wapGetOnLineProductList(
			Map<String, Object> tPARAMETERS) {
		StringBuffer sql = new StringBuffer(
				"  SELECT a.productID id,a.ProductName,a.Ispublish wapIspublish,b.IsPublish,b.HotFlag,b.RecommendFlag,b.Preferential,b.StartDate,b.EndDate "
						+ " FROM sdproduct a "
						+ " LEFT JOIN productrelainfo b ON (a.productid = b.productid) WHERE a.Ispublish = 'Y' AND a.producttype IN ('A','B','C','D','E') "
						// 2014-11-12 wap站健康告知上线
						// +
						// " AND NOT EXISTS ( SELECT 1 FROM healthyInfo c WHERE c.ProductId =a.productid)"
						+ " AND NOT EXISTS ( SELECT 1 FROM zdconfig WHERE TYPE = 'questionPaper' AND VALUE=a.productid) ");
		QueryBuilder qb = new QueryBuilder(sql.toString());
		String tFrom = String.valueOf(tPARAMETERS.get("From"));// 信息来源
		// b.IsPublish =Y 表示 wap站上线
		if ("wap".equalsIgnoreCase(tFrom)) {
			qb.append(" And b.IsPublish='Y' ");
		}
		qb.append(" order by id");
		DataTable dt = qb.executeDataTable();
		// 存放产品编码
		JSONArray prodsInfo = new JSONArray();
		int rowCount = dt.getRowCount();
		Map<String, Object> prodsIdMap = new HashMap<String, Object>();
		if (rowCount > 0) {
			for (int i = rowCount; i > 0; i--) {
				prodsInfo.put(dt.get(i - 1, 0));
			}
		}
		prodsIdMap.put("ProdCode", prodsInfo);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("USER", "");
		resultMap.put("REQUESTTYPE", "KXBJRT0007");
		resultMap.put("STATYS", "true");
		resultMap.put("RESULTS", prodsInfo);
		return resultMap;

	}

	/**
	 * @Method: wapGetProductRealTimeInfo
	 * @Description: 产品实时信息接口
	 * @param tPARAMETERS
	 * @return Map<String,Object>
	 * @throws:
	 * @MakeDate:2014-2-17 下午02:10:56
	 */
	public static Map<String, Object> wapGetProductRealTimeInfo(
			Map<String, Object> tPARAMETERS, Map<String, Object> mServiceMap) {
		StringBuffer sql = new StringBuffer(
				"select SalesVolume from sdsearchrelaproduct where ProductID = ?");
		QueryBuilder qb = new QueryBuilder(sql.toString());
		JSONArray ProductIds = (JSONArray) tPARAMETERS.get("ProductIds");
		String channelCode = (String) tPARAMETERS.get("From");// "wap"
		String orderSn = (String) tPARAMETERS.get("OrderSn");// 订单号
		String interfaceFlag = (String) tPARAMETERS.get("InterfaceFlag");// 接口使用标记
																			// pay:支付页，list:列表页，详细页等
		String UserName = (String) tPARAMETERS.get("UserName");
		// String UnitID = (String)tPARAMETERS.get("UnitID");
		org.json.JSONObject ProductId = null;
		List<String> arrayList = new ArrayList<String>();
		try {
			ProductId = ProductIds.getJSONObject(0);
			if ("pay".equals(interfaceFlag) && ProductIds.length() >= 2) {
				arrayList.add("WapProduct00004");
				return WapErrorUtil
						.dealErrorInfo(arrayList, "KXBJRT0036", null);
			}
			qb.add(ProductId.get("ProductId"));

			DataTable dt = qb.executeDataTable();
			int rowCount = dt.getRowCount();
			Map<String, Object> prodsIdMap = new HashMap<String, Object>();
			if (rowCount > 0) {
				prodsIdMap.put("SalesVolume", dt.get(0, 0));
			} else {
				prodsIdMap.put("SalesVolume", "0");
			}
			MemberService tMemberService = (MemberService) mServiceMap
					.get("MemberService");
			SDOrderService tSDOrderService = (SDOrderService) mServiceMap
					.get("SDOrderService");
			Member member = tMemberService.get(UserName);
			int pLen = ProductIds.length();
			// 会员优惠劵
			JSONArray unusedInfo = new JSONArray();
			SDOrder sdorder = tSDOrderService.getOrderByOrderSn(orderSn);
			prodsIdMap.put("DiscCoupons", unusedInfo);

			// 会员积分
			JSONArray pointInfo = new JSONArray();
			Map<String, Object> pointMap = new HashMap<String, Object>();
			if (member != null) {
				qb = new QueryBuilder(
						" SELECT currentValidatePoint,POINT FROM member WHERE id = ? ");
				qb.add(member.getId());
				dt = qb.executeDataTable();
				String currentPoint = dt.getString(0, "currentValidatePoint");// 可用积分
				pointMap.put("DiscActivePoint", currentPoint);
			}
			QueryBuilder pointqb = new QueryBuilder(
					" select value from zdconfig where type='PointScalerUnit' ");
			String pointScalerUnit = pointqb.executeString();
			pointMap.put("PointScalerUnit", pointScalerUnit);
			// add by wangej 20150520 产品积分使用和积分获取的比例 begin
			Map<String, Object> map_inner = new HashMap<String, Object>();
			Map<String, Object> map_return = new HashMap<String, Object>();
			PointsCalculate PointsCalculate = new PointsCalculate();
			List<String> arrayListProduct = new ArrayList<String>();
			if (StringUtil.isEmpty(ProductId.get("ProductId"))) {
				pointMap.put("PointGetUnit", "");
				pointMap.put("PointUseUnit", "");
			} else {
				arrayListProduct.add((String) ProductId.get("ProductId"));
				map_inner.put("ProductList", arrayListProduct);
				// 调用common方法获取积分规则列表
				map_return = PointsCalculate.pointsManage(
						IntegralConstant.POINT_PRODUCT, "", map_inner);
				DataTable dtPoint = (DataTable) map_return
						.get(IntegralConstant.DATA);
				if (dtPoint == null || dtPoint.getRowCount() <= 0) {
					pointMap.put("PointGetUnit", "");
					pointMap.put("PointUseUnit", "");
				} else {
					pointMap.put("PointGetUnit",
							dtPoint.getString(0, "GivePoints"));
					pointMap.put("PointUseUnit",
							dtPoint.getString(0, "BuyPoints"));
				}
			}
			pointInfo.put(pointMap);
			// add by wangej 20150520 产品积分使用和积分获取的比例 begin
			prodsIdMap.put("DiscPoint", pointInfo);
			// 产品活动信息
			JSONArray activeInfo = new JSONArray();
			for (int i = 0; i < pLen; i++) {
				org.json.JSONObject cProductId = ProductIds.getJSONObject(i);
				activeInfo = WapCommonUtil.getActiveInfoByProductID(activeInfo,
						cProductId.get("ProductId").toString(), channelCode,
						interfaceFlag, sdorder);
			}
			prodsIdMap.put("DiscActive", activeInfo);
			// prodsIdMap.put("SalesVolume1", prodsInfo);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("USER", "");
			resultMap.put("REQUESTTYPE", "KXBJRT0036");
			resultMap.put("STATYS", "true");
			resultMap.put("RESULTS", prodsIdMap);

			return resultMap;
		} catch (Exception e) {
			logger.error("wap站产品实时信息接口，调用失败。" + e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 可用优惠劵接口
	 * 
	 * @Title: wapGetPayCouponInfo
	 */
	public static Map<String, Object> getPayCouponInfo(
			Map<String, Object> tPARAMETERS, Map<String, Object> mServiceMap) {
		JSONArray ProductIds = (JSONArray) tPARAMETERS.get("ProductIds");
		String channelCode = (String) tPARAMETERS.get("From");// "wap":
																// cps:代理人渠道
		String StandardPrem = (String) tPARAMETERS.get("StandardPrem");// 标准保费
		String UserName = (String) tPARAMETERS.get("UserName");
		String CPSUserID = (String) tPARAMETERS.get("CPSUserID");
		// String UnitID = (String)tPARAMETERS.get("UnitID");
		org.json.JSONObject ProductId = null;
		List<String> arrayList = new ArrayList<String>();
		try {
			ProductId = ProductIds.getJSONObject(0);
			if (ProductIds.length() >= 2) {
				arrayList.add("WapProduct00004");
				return WapErrorUtil
						.dealErrorInfo(arrayList, "KXBJRT0044", null);
			}

			Map<String, Object> prodsIdMap = new HashMap<String, Object>();
			MemberService tMemberService = (MemberService) mServiceMap
					.get("MemberService");
			Member member = tMemberService.get(UserName);
			if (StringUtil.isEmpty(CPSUserID)) {
				if (member == null) {
					arrayList.add("MemberAction000006");
					return WapErrorUtil.dealErrorInfo(arrayList, "KXBJRT0044",
							null);
				}
			}
			String memberid = "";
			if (member != null) {
				memberid = member.getId();
			}
			// 会员优惠劵
			JSONArray unusedInfo = new JSONArray();
			QueryBuilder qb = new QueryBuilder(
					"SELECT couponsn,parvalue,direction,startTime,endTime,prop1,prop3,prop4 FROM couponinfo ac WHERE IF (ac.riskcode IS NOT NULL AND ac.riskcode != '', (FIND_IN_SET((SELECT BelongFlag FROM fmrisk fm WHERE fm.riskcode=? ),ac.riskcode)!=0), 1=1) AND IF (ac.insurancecompany IS NOT NULL AND ac.insurancecompany != '', FIND_IN_SET(?, ac.insurancecompany) != 0, 1=1) AND IF (ac.product IS NOT NULL AND ac.product != '', FIND_IN_SET(?, ac.product) != 0, 1=1) and  ac.status='2'   AND  UNIX_TIMESTAMP(DATE_FORMAT(ac.starttime,'%Y-%m-%d %H:%i:%s')) <=UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s'))  AND  UNIX_TIMESTAMP(DATE_FORMAT(ac.endtime,'%Y-%m-%d %H:%i:%s')) >=UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')) and memberid=?  and CAST(payamount AS SIGNED) <= CAST(? AS SIGNED) and channelsn LIKE '%"
							+ channelCode + "%'  ");
			qb.add(ProductId.get("ProductId"));
			qb.add(ProductId.get("ProductId").toString().substring(0, 4));
			qb.add(ProductId.get("ProductId"));
			qb.add(memberid);
			qb.add(StandardPrem);
			if (StringUtil.isNotEmpty(CPSUserID) && "cps".equals(channelCode)
					&& StringUtil.isEmpty(memberid)) {
				QueryBuilder qb_agent = new QueryBuilder(
						" SELECT email,mobile UserName FROM sduser WHERE UserCode=? ",
						CPSUserID);
				DataTable agentdt = qb_agent.executeDataTable("CPSAGENT");
				String agentemail = "";
				String agentmobile = "";
				if (agentdt != null && agentdt.getRowCount() >= 1) {
					agentemail = agentdt.getString(0, 0);
					agentmobile = agentdt.getString(0, 1);
					StringBuffer sbagent = new StringBuffer(
							" SELECT couponsn,parvalue,direction,startTime,endTime,prop1,prop3,prop4 FROM couponinfo ac WHERE IF (ac.riskcode IS NOT NULL AND ac.riskcode != '', (FIND_IN_SET((SELECT BelongFlag FROM fmrisk fm WHERE fm.riskcode=? ),ac.riskcode)!=0), 1=1) AND IF (ac.insurancecompany IS NOT NULL AND ac.insurancecompany != '', FIND_IN_SET(?, ac.insurancecompany) != 0, 1=1) "
									+ " AND IF (ac.product IS NOT NULL AND ac.product != '', FIND_IN_SET(?, ac.product) != 0, 1=1) and  ac.status='2'   AND  UNIX_TIMESTAMP(DATE_FORMAT(ac.starttime,'%Y-%m-%d %H:%i:%s')) <=UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s'))  "
									+ " AND  UNIX_TIMESTAMP(DATE_FORMAT(ac.endtime,'%Y-%m-%d %H:%i:%s')) >=UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')) and CAST(payamount AS SIGNED) <= CAST(? AS SIGNED) and memberid is null  ");
					if (StringUtil.isNotEmpty(agentemail)
							&& StringUtil.isNotEmpty(agentmobile)) {
						sbagent.append(" and (mail='" + agentemail
								+ "' or mobile='" + agentmobile + "') ");
					} else if (StringUtil.isNotEmpty(agentemail)) {
						sbagent.append(" and mail='" + agentemail + "' ");
					} else if (StringUtil.isNotEmpty(agentmobile)) {
						sbagent.append(" and mobile='" + agentmobile + "' ");
					}
					qb = new QueryBuilder(sbagent.toString());
					qb.add(ProductId.get("ProductId"));
					qb.add(ProductId.get("ProductId").toString()
							.substring(0, 4));
					qb.add(ProductId.get("ProductId"));
					qb.add(StandardPrem);
				}
			}
			DataTable dt = qb.executeDataTable();
			int dtLen = dt.getRowCount();
			for (int i = 0; i < dtLen; i++) {
				Map<String, Object> unusedMap = new HashMap<String, Object>();
				unusedMap.put("DiscCouponNumber", dt.getString(i, "couponSn"));
				unusedMap.put("DiscCouponStatus", "2");
				unusedMap.put("DiscCouponName", dt.getString(i, "direction"));
				unusedMap.put("DiscCouponType", "");
				unusedMap.put("Amount", dt.getString(i, "parValue"));
				unusedMap.put("DiscCouponDis", "1");
				String disFlag = String.valueOf(dt.getString(i, "prop3"));
				// 折扣优惠劵
				if ("02".equals(disFlag)) {
					String dis = String.valueOf(dt.getString(i, "prop4"));
					BigDecimal discount = new BigDecimal(1)
							.subtract(new BigDecimal((StringUtil
									.isNotEmpty(dis) ? dis : "1"))
									.divide(new BigDecimal(10)));
					unusedMap.put("Amount", String.valueOf(new BigDecimal(
							StandardPrem).multiply(discount).setScale(2,
							BigDecimal.ROUND_HALF_UP)));
					unusedMap.put("DiscCouponDis", dis);
					unusedMap.put("DiscCouponType", disFlag);
				}
				unusedMap.put("ValidDateFrom", dt.getString(i, "startTime"));
				unusedMap.put("ValidDateTo", dt.getString(i, "endTime"));
				unusedMap.put("IsSharedActiveFlag", dt.getString(i, "prop1"));
				unusedInfo.put(unusedMap);
			}
			prodsIdMap.put("DiscCoupons", unusedInfo);

			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("USER", "");
			resultMap.put("REQUESTTYPE", "KXBJRT0044");
			resultMap.put("STATYS", "true");
			resultMap.put("RESULTS", prodsIdMap);

			return resultMap;
		} catch (Exception e) {
			logger.error("wap站产品实时信息接口，调用失败。" + e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 产品评论接口
	 * 
	 * @Title: memberPointInfo
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	public static Map<String, Object> productCommentInfo(
			Map<String, Object> tPARAMETERS, String USER) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> commentMap = new HashMap<String, Object>();
		List<String> errorinfos = new ArrayList<String>();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (errorinfos.size() > 0) {
			return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0044",
					resultMap);
		}
		// 解析入参
		try {
			String productID = (String) tPARAMETERS.get("ProductID");
			if (StringUtil.isEmpty(productID)) {
				errorinfos.add("G000002");
				return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0045",
						null);
			}
			QueryBuilder qb = new QueryBuilder(
					" SELECT a.productid,c.AddUser,c.AddTime,c.Content FROM sdsearchrelaproduct a,zcarticle b,zccomment c WHERE a.prop1 = b.id AND b.id = c.relaid AND verifyflag='Y' AND a.productid =? ORDER BY c.AddTime DESC  ");// '204901017'
			qb.add(productID);
			DataTable dt = qb.executeDataTable();
			int commentLen = dt.getRowCount();
			JSONArray commentInfo = new JSONArray();
			for (int i = 0; i < commentLen; i++) {
				Map<String, Object> subcommentMap = new HashMap<String, Object>();
				subcommentMap.put("CommentUser", dt.get(i, "AddUser"));
				subcommentMap.put("CommentTime",
						sdf.format(dt.get(i, "AddTime")));
				subcommentMap.put("CommentContent",
						convert(String.valueOf(dt.get(i, "Content"))));
				commentInfo.put(subcommentMap);
			}
			commentMap.put("CommentCount", commentLen);
			commentMap.put("DiscComment", commentInfo);
			resultMap.put("RESULTS", commentMap);

		} catch (Exception e) {
			errorinfos.add("G000001");
			logger.error("错误编码:G000001;错误信息：" + e.getMessage(), e);
			return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0044",
					resultMap);
		}
		resultMap.put("USER", "kxb");
		resultMap.put("REQUESTTYPE", "KXBJRT0043");
		resultMap.put("STATYS", "true");
		return resultMap;
	}

	/**
	 * 产品特殊标识信息接口 add by wangej 20150625
	 * 
	 * @Title: getProductSpecialInfo
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	public static Map<String, Object> getProductSpecialInfo(
			Map<String, Object> tPARAMETERS, String USER) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> productSpecialInfoMap = new HashMap<String, Object>();
		List<String> errorinfos = new ArrayList<String>();

		if (errorinfos.size() > 0) {
			return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0057",
					resultMap);
		}
		// 解析入参
		try {
			StringBuffer sqlS = new StringBuffer();
			sqlS.append(" SELECT a.ProductID ProductID,IFNULL(b.startperiod, '1') StartPeriod, ");
			sqlS.append(" IFNULL(b.endperiod, '730') EndPeriod, ");
			sqlS.append(" ( SELECT COUNT(1) FROM healthyinfo c WHERE a.productid = c.productid ) HealthCount ");
			sqlS.append(" FROM sdproduct a ");
			sqlS.append(" LEFT JOIN productperiod b ON a.productid = b.riskcode ");
			sqlS.append(" WHERE a.`IsPublish` = 'Y' ");
			QueryBuilder qb = new QueryBuilder(sqlS.toString());
			DataTable dt = qb.executeDataTable();
			JSONArray productSpecialInfo = new JSONArray();
			if (dt == null || dt.getRowCount() <= 0) {
				productSpecialInfoMap.put("productSpecialInfo", "");
			} else {
				for (int i = 0, j = dt.getRowCount(); i < j; i++) {
					Map<String, Object> specialInfoM = new HashMap<String, Object>();
					specialInfoM.put("ProductID", dt.get(i, "ProductID"));
					specialInfoM.put("StartPeriod", dt.get(i, "StartPeriod"));
					specialInfoM.put("EndPeriod", dt.get(i, "EndPeriod"));
					specialInfoM.put("HealthCount", dt.get(i, "HealthCount"));
					productSpecialInfo.put(specialInfoM);
				}
				productSpecialInfoMap.put("productSpecialInfo",
						productSpecialInfo);
			}
			resultMap.put("RESULTS", productSpecialInfoMap);

		} catch (Exception e) {
			errorinfos.add("G000001");
			logger.error("错误编码:G000001;getProductSpecialInfo-错误信息："
					+ e.getMessage(), e);
			return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0057",
					resultMap);
		}
		resultMap.put("USER", "kxb");
		resultMap.put("REQUESTTYPE", "KXBJRT0057");
		resultMap.put("STATYS", "true");
		return resultMap;
	}

	/**
	 * unicode转中文
	 * 
	 * @return String 返回类型
	 */
	public static String convert(String str) {
		Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
		Matcher matcher = pattern.matcher(str);
		char ch;
		while (matcher.find()) {
			ch = (char) Integer.parseInt(matcher.group(2), 16);
			str = str.replace(matcher.group(1), ch + "");
		}
		return str;
	}

	public static void main(String[] args) {
		// Map<String, Object> tPARAMETERS = new HashMap<String, Object>();
		// tPARAMETERS.put("ProdParams", "0000000364");
		// /.put("ProductId", "200701001");
		// tPARAMETERS.put("SubRiskType", "A00");
		// Age|FeaturesRisk|TravelArea:00016,00019|baoxiangongsi
		// default_Age_1|00016,00019|default_FeaturesRisk_1|default_baoxiangongsi_1
		// 00051|default_Prem_1|default_FeaturesRisk_1|default_baoxiangongsi_1|default_Price_1
		// FeaturesRisk|Prem|Price|baoxiangongsi|baozhangleixing:00051
		// String FilterParams =
		// "FeaturesRisk,00051;baoxiangongsi,default_baoxiangongsi_1";
		// String FilterParams =
		// "Age,default_Age_1;FeaturesRisk,00016,00019,88888;baoxiangongsi,default_baoxiangongsi_1";
		// String FilterParams =
		// "Age,default_Age_1;baoxiangongsi,default_baoxiangongsi_1";
		// tPARAMETERS.put("FilterParams", FilterParams);
		// wapGetProductList(tPARAMETERS);
		// getDt_ProductIds("", "B");
		// SearchAPI.getSearchProduct("default_Age_1|00016,00019|default_FeaturesRisk_1|default_baoxiangongsi_1",
		// "");

		// System.out.println(wapGetProductList(tPARAMETERS).toString());

		// System.out.println(wapGetProductFilters(tPARAMETERS).toString());

		QueryBuilder qb = new QueryBuilder(
				" SELECT a.productid,c.AddUser,c.AddTime,c.Prop2 FROM sdsearchrelaproduct a,zcarticle b,zccomment c WHERE a.prop1 = b.id AND b.id = c.relaid AND verifyflag='Y' AND a.productid =? ");// '204901017'
		qb.add("");
		DataTable dt = qb.executeDataTable();
		int commentLen = dt.getRowCount();
//		System.out.println(commentLen);

	}

}
