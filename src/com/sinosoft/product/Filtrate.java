package com.sinosoft.product;

import com.sinosoft.cms.pub.SiteUtil;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.messages.LongTimeTask;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.SDAllCondtionSchema;
import com.sinosoft.schema.SDAllCondtionSet;
import com.sinosoft.schema.SDSearchAddressSchema;
import com.sinosoft.schema.SDSearchAddressSet;
import com.sinosoft.schema.SDSearchProductInfoSchema;
import com.sinosoft.schema.SDSearchProductInfoSet;
import com.sinosoft.schema.SDSearchRelaProductSchema;
import com.sinosoft.schema.SDSearchRelaProductSet;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Filtrate extends Page {
	/**
	 * 条件线程记录器
	 */
	protected int CurrentThreadCount = 0;
	/**
	 * 设置需要启动线程的查询条件组合数据个数
	 */
	protected int AllProduct_DealCount = 10;
	protected Map<String, String> SearchConditionMap = new HashMap<String, String>();

	/**
	 * 页面初始化
	 * 
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Mapx init(Mapx params) {
		Map<String, String> ERiskSubType = new HashMap<String, String>();
		DataTable dt = new QueryBuilder("select a.ERiskType,b.Name from (select distinct ERiskType from SDSearchAddress ) a,zccatalog b"
				+ " where a.ERiskType = b.ProductType  order by a.ERiskType ").executeDataTable();
		for (int i = 0; i < dt.getRowCount(); i++) {
			ERiskSubType.put(dt.getString(i, "ERiskType"), dt.getString(i, "ERiskType") + "-" + dt.getString(i, "Name"));
		}
		params.put("ERiskSubTypeList", HtmlUtil.mapxToOptions(ERiskSubType, true));
		return params;
	}

	/**
	 * 查询全部筛选条件
	 */
	public void ProAllCondtion() {
		LongTimeTask ltt = new LongTimeTask() {
			public void execute() {
				AllCondtion(this);
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
		ltt.setCurrentInfo("开始生成......");
		$S("TaskID", "" + ltt.getTaskID());
	}

	/**
	 * 查询全部筛选条件
	 */
	private void AllCondtion(LongTimeTask ltt) {
		ltt.setPercent(0);
		String sql = "select producttype from zccatalog where (producttype is not null and producttype!='' ) and innercode like '002313%' and type='1' order by orderflag,innercode";
		QueryBuilder qb = new QueryBuilder(sql);
		DataTable dt = qb.executeDataTable();
		int plan = 0;

		if (dt != null) {
			plan = 100 / dt.getRowCount();
			String delete = "delete from SDAllCondtion";
			new QueryBuilder(delete).executeNoQuery();
			ltt.setCurrentInfo("清空数据......");
			ltt.setPercent(5);

			DataTable qbAll = new QueryBuilder("select Id,SearchCode,SearchName,SubNodeNum from FEMSearchConditionInfo ").executeDataTable();
			for (int i = 0; i < qbAll.getRowCount(); i++) {
				String all = "";
				if (StringUtil.isNotEmpty(qbAll.getString(i, "SubNodeNum")) && !"0".equals(qbAll.getString(i, "SubNodeNum"))) {
					all = "(不限)";
				}
				SearchConditionMap.put(qbAll.getString(i, "Id"), qbAll.getString(i, "SearchName") + all);
				SearchConditionMap.put(qbAll.getString(i, "SearchCode"), qbAll.getString(i, "SearchName") + all);
			}
		}
		// 生成数据
		ltt.setCurrentInfo("生成筛选条件中......");
		CurrentThreadCount = dt.getRowCount();
		for (int i = 0; i < dt.getRowCount(); i++) {
			String producttype = dt.getString(i, "producttype");
			if (StringUtil.isNotEmpty(producttype)) {
				AllCombination t = new AllCombination();
				t.setERiskSubType(producttype);
				t.start();
			} else {
				CurrentThreadCount--;
			}
		}
		// 线程进度控制
		while (CurrentThreadCount > 0) {
			ltt.setPercent(100 - (plan * CurrentThreadCount));

			try {
				Thread.sleep(5 * 1000);
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
			}

			if (CurrentThreadCount <= 0) {
				break;
			}
		}
		ltt.setPercent(100);
	}

	/**
	 * 线程处理发布条件
	 * 
	 * @author guobin
	 * 
	 */
	class AllCombination extends Thread {
		private String ERiskSubType;

		public void run() {
			try {
				Long start_time = System.currentTimeMillis();
				List<List<String>> list = Combination.getAllCombination(ERiskSubType);
				if (list != null && list.size() != 0) {
					for (int i = 0; i < list.size(); i++) {
						List<String> obj = list.get(i);
						if (list.get(i) != null) {
							Transaction trans = new Transaction();
							for (int j = 0; j < obj.size(); j++) {
								SDAllCondtionSchema tSDAllCondtion = new SDAllCondtionSchema();
								tSDAllCondtion.setId(NoUtil.getMaxIDLocal("SDAllCondtionID"));
								tSDAllCondtion.setSearchID(obj.get(j));
								tSDAllCondtion.setERiskSubType(ERiskSubType);
								tSDAllCondtion.setCreateDate(new Date());
								String SearchCode[] = obj.get(j).replaceAll("default_", "").replaceAll("_1", "").replaceAll("_2", "").split("\\|");
								String SearchTitle = "";
								for (int m = 0; m < SearchCode.length; m++) {
									SearchTitle += CodeToName(SearchCode[m]);
									if (m != SearchCode.length - 1) {
										SearchTitle += "|";
									}
								}
								tSDAllCondtion.setSearchName(SearchTitle);
								trans.add(tSDAllCondtion, Transaction.INSERT);
							}
							trans.commit();
						}
					}
				} else {
					// LogUtil.error("产品中心无配置具体的查询条件!联系管理员" + ERiskSubType);
				}

				CurrentThreadCount--;
				Object[] argArr = {ERiskSubType, CurrentThreadCount, getDateTimeByMillisecond(System.currentTimeMillis() - start_time)};
				logger.info("生成数据结束: {} - 当前线程个数：{} - 耗时：{}" , argArr);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}

		public String getERiskSubType() {
			return ERiskSubType;
		}

		public void setERiskSubType(String eRiskSubType) {
			ERiskSubType = eRiskSubType;
		}
	}

	/**
	 * 毫秒转日期字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String getDateTimeByMillisecond(Long str) {
		Date date = new Date(str);
		SimpleDateFormat format = new SimpleDateFormat("mm分ss秒");
		return format.format(date);
	}

	/**
	 * 生成全部筛选产品
	 */
	public void ProAllProduct() {
		LongTimeTask ltt = new LongTimeTask() {
			public void execute() {
				ProAllProduct(this);
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
		ltt.setCurrentInfo("开始生成......");
		$S("TaskID", "" + ltt.getTaskID());
	}

	/**
	 * 生成全部筛选产品
	 */
	private void ProAllProduct(LongTimeTask ltt) {
		ltt.setPercent(0);
		String sql = "select count(1) from SDAllCondtion";
		String total = new QueryBuilder(sql).executeOneValue() + "";

		if (StringUtil.isEmpty(total) || "0".equals(total)) {
			logger.warn("查询SDAllCondtion数据为空!");
			ltt.setCurrentInfo("发布筛选产品异常：SDAllCondtion数据为空!");
			ltt.setPercent(100);
			return;
		}
		// 生成数据
		int totalCondtion = Integer.parseInt(total);
		int pageCount = totalCondtion / AllProduct_DealCount + 1;
		ltt.setCurrentInfo("生成产品中......启动进程数 :" + pageCount);
		CurrentThreadCount = pageCount;
		for (int i = 1; i <= pageCount; i++) {
			int start = (i - 1) * AllProduct_DealCount;
			int end = i * AllProduct_DealCount - 1;
			if (end > totalCondtion) {
				end = totalCondtion;
			}
			SDAllCondtionSchema tCondtionSchema = new SDAllCondtionSchema();
			SDAllCondtionSet tCondtionSet = tCondtionSchema.query(new QueryBuilder("where 1=1 limit ?,?", start, end - start));
			AllProduct t = new AllProduct();
			t.settSDAllCondtionSet(tCondtionSet);
			t.start();
		}

		// 线程进度控制
		while (CurrentThreadCount > 0) {
			ltt.setCurrentInfo("生成产品中......进程数 :" + CurrentThreadCount + "/" + pageCount);
			ltt.setPercent(100 - (100 / pageCount * CurrentThreadCount));
			try {
				Thread.sleep(10 * 1000);
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
			}
			if (CurrentThreadCount <= 0) {
				break;
			}
		}
		ltt.setPercent(100);
	}

	/**
	 * 生成全部筛选产品
	 */
	public void synProductByAll() {
		LongTimeTask ltt = new LongTimeTask() {
			public void execute() {
				synProductByAll(this);
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
		ltt.setCurrentInfo("开始生成......");
		$S("TaskID", "" + ltt.getTaskID());
	}

	/**
	 * 生成全部筛选产品
	 */
	private void synProductByAll(LongTimeTask ltt) {
		String sql = "select  count(1)   from SDAllCondtion  ";
		QueryBuilder qb = new QueryBuilder(sql);
		QueryBuilder qb_ = new QueryBuilder("where 1=1");
		String SearchID = $V("SearchID");
		String SearchName = $V("SearchName");
		String ERiskSubType = $V("ERiskSubType");
		if (StringUtil.isNotEmpty(SearchID)) {
			qb.append(" and SearchID like ? ", "%" + SearchID + "%");
			qb_.append(" and SearchID like ? ", "%" + SearchID + "%");
		}
		if (StringUtil.isNotEmpty(SearchName)) {
			qb.append(" and SearchName like ? ", "%" + SearchName + "%");
			qb_.append(" and SearchName like ? ", "%" + SearchName + "%");
		}
		if (StringUtil.isNotEmpty(ERiskSubType)) {
			qb.append(" and ERiskSubType = ? ", ERiskSubType);
			qb_.append(" and ERiskSubType = ? ", ERiskSubType);
		}

		ltt.setPercent(0);
		String total = qb.executeOneValue() + "";
		if (StringUtil.isEmpty(total) || "0".equals(total)) {
			logger.warn("查询SDAllCondtion数据为空!{}", sql);
			ltt.setCurrentInfo("发布筛选产品异常：SDAllCondtion数据为空!");
			ltt.setPercent(100);
			return;
		}
		// 生成数据
		int totalCondtion = Integer.parseInt(total);
		int pageCount = totalCondtion / AllProduct_DealCount + 1;
		ltt.setCurrentInfo("生成产品中......启动进程数 :" + pageCount);
		CurrentThreadCount = pageCount;
		for (int i = 1; i <= pageCount; i++) {
			int start = (i - 1) * AllProduct_DealCount;
			int end = i * AllProduct_DealCount - 1;
			if (end > totalCondtion) {
				end = totalCondtion;
			}
			SDAllCondtionSchema tCondtionSchema = new SDAllCondtionSchema();
			qb_.append(" limit  " + start + "," + (end - start));
			SDAllCondtionSet tCondtionSet = tCondtionSchema.query(qb_);
			AllProduct t = new AllProduct();
			t.settSDAllCondtionSet(tCondtionSet);
			t.start();
		}

		// 线程进度控制
		while (CurrentThreadCount > 0) {
			ltt.setCurrentInfo("生成产品中......进程数 :" + CurrentThreadCount + "/" + pageCount);
			ltt.setPercent(100 - (100 / pageCount * CurrentThreadCount));
			try {
				Thread.sleep(10 * 1000);
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
			}
			if (CurrentThreadCount <= 0) {
				break;
			}
		}
		ltt.setPercent(100);
	}

	/**
	 * 线程处理发布条件
	 * 
	 * @author guobin
	 * 
	 */
	class AllProduct extends Thread {
		private SDAllCondtionSet tSDAllCondtionSet;

		public void run() {
			try {
				Long start_time = System.currentTimeMillis();
				if (tSDAllCondtionSet != null && tSDAllCondtionSet.size() > 0) {
					for (int i = 0; i < tSDAllCondtionSet.size(); i++) {
						SDAllCondtionSchema tSDAllCondtion = tSDAllCondtionSet.get(i);
						publishing(tSDAllCondtion.getSearchID(), tSDAllCondtion.getERiskSubType());
					}
				}
				CurrentThreadCount--;
				Object[] argArr = {tSDAllCondtionSet.size(), CurrentThreadCount, getDateTimeByMillisecond(System.currentTimeMillis() - start_time)};
				logger.info("生成产品数据数据结束:: {} - 当前线程个数：{} - 耗时：{}" , argArr);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}

		public SDAllCondtionSet gettSDAllCondtionSet() {
			return tSDAllCondtionSet;
		}

		public void settSDAllCondtionSet(SDAllCondtionSet tSDAllCondtionSet) {
			this.tSDAllCondtionSet = tSDAllCondtionSet;
		}

	}

	/**
	 * 查询条件编码转换
	 * 
	 * @param SearchCode
	 * @return
	 */
	private String CodeToName(String SearchCode) {
		String SearchTitle = "";
		if (SearchCode.indexOf(",") == -1) {
			String condtion = SearchConditionMap.get(SearchCode);
			if (StringUtil.isEmpty(SearchCode) || StringUtil.isEmpty(condtion)) {
				logger.info(" 无配置具体的查询条件 {}", SearchCode);
				SearchTitle += "<font color='red'>未知</font>";
			} else {
				SearchTitle += condtion;
			}
		} else {
			for (int i = 0; i < SearchCode.split(",").length; i++) {
				String condtion = SearchConditionMap.get(SearchCode.split(",")[i]);
				if (StringUtil.isEmpty(SearchCode) || StringUtil.isEmpty(condtion)) {
					logger.info(" 无配置具体的查询条件 {}", SearchCode);
					SearchTitle += "<font color='red'>未知</font>";
				} else {
					SearchTitle += condtion + ",";
				}
			}
			if (SearchTitle.endsWith(",")) {
				SearchTitle = SearchTitle.substring(0, SearchTitle.length() - 1);
			}
		}
		return SearchTitle;
	}

	/**
	 * 数据查询
	 * 
	 * @param dga
	 */
	public static void dg1DataBind(DataGridAction dga) {
		String sql = "select Id,SearchID,ERiskSubType,SearchName,CreateDate ,ProductCount,(select name from zccatalog where innercode like '002313%' and type='1' and producttype=ERiskSubType ) as ERiskSubTypeName "
				+ " from SDAllCondtion  where 1=1 ";

		QueryBuilder qb = new QueryBuilder(sql);

		String SearchID = (String) dga.getParams().get("SearchID");
		String SearchName = (String) dga.getParams().get("SearchName");
		String ERiskSubType = (String) dga.getParams().get("ERiskSubType");

		if (StringUtil.isNotEmpty(SearchID)) {
			qb.append(" and SearchID like ? ", "%" + SearchID + "%");
		}

		if (StringUtil.isNotEmpty(SearchName)) {
			qb.append(" and SearchName like ? ", "%" + SearchName + "%");
		}

		if (StringUtil.isNotEmpty(ERiskSubType)) {
			qb.append(" and ERiskSubType = ? ", ERiskSubType);
		}

		qb.append(" order by id");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.bindData(dt);
	}
	
	/**
	 * 数据查询
	 * 
	 * @param dga
	 */
	public static void dg1DataBind1(DataGridAction dga) {
		String sql = "select Id,SearchAddress,ERiskType,(select SearchName from SDAllCondtion where SearchID = SDSearchAddress.SearchID) as SearchName,CreateDate ,ModifyDate,(select name from zccatalog where innercode like '002313%' and type='1' and producttype=ERiskType ) as ERiskSubTypeName "
				+ " from SDSearchAddress where 1=1 ";

		QueryBuilder qb = new QueryBuilder(sql);

		String Id = (String) dga.getParams().get("Id");
//		String SearchName = (String) dga.getParams().get("SearchName");
		String ERiskType = (String) dga.getParams().get("ERiskType");

		if (StringUtil.isNotEmpty(Id)) {
			qb.append(" and Id like ? ", "%" + Id + "%" );
		}

//		if (StringUtil.isNotEmpty(SearchName)) {
//			qb.append(" and exists( select SearchName from (select SearchName from SDAllCondtion,SDSearchAddress where SDAllCondtion.SearchID = SDSearchAddress.SearchID) as SearchName where SearchName like ? ", "'%" + SearchName + "%'");
//			qb.append(")");
//		}

		if (StringUtil.isNotEmpty(ERiskType)) {
			qb.append(" and ERiskType = ? ", ERiskType);
		}

		qb.append(" order by modifydate desc");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.bindData(dt);
	}

	
	/**
	 * 生成产品数据
	 * 
	 * @param dga
	 */
	public void synProductByOne() {
		try {
			String IDs = $V("IDs");

			if (StringUtil.isEmpty(IDs)) {
				Response.setLogInfo(0, "查询失败!编码不存在:" + IDs);
				return;
			}

			if (!StringUtil.checkID(IDs)) {
				Response.setStatus(0);
				Response.setMessage("传入ID时发生错误!");
				return;
			}
			SDAllCondtionSchema tSDAllCondtion = new SDAllCondtionSchema();
			SDAllCondtionSet tSDAllCondtion_set = tSDAllCondtion.query(new QueryBuilder("where Id in (" + IDs + ")"));

			if (tSDAllCondtion_set == null || tSDAllCondtion_set.size() == 0) {
				Response.setStatus(0);
				Response.setMessage("查询失败!编码不存在!" + IDs);
				return;
			}

			for (int i = 0; i < tSDAllCondtion_set.size(); i++) {
				SDAllCondtionSchema tSDAllCondtion1 = tSDAllCondtion_set.get(i);
				publishing(tSDAllCondtion1.getSearchID(), tSDAllCondtion1.getERiskSubType());
			}

			Response.setLogInfo(1, "生成成功!");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setLogInfo(0, "保存失败!" + e.getMessage());
		}
	}

	/**
	 * 获取查询发布
	 * 
	 * @param searchCode
	 * @param tERiskSubType
	 * @return
	 */
	public static boolean publishing(String searchCode, String tERiskSubType) {
		Long start_time = System.currentTimeMillis();
		String[] conditionsplit = searchCode.split("\\|");
		if(StringUtil.isNotEmpty(tERiskSubType)){
			tERiskSubType = tERiskSubType.substring(0,1);
		}
		String sql = "select distinct a.riskcode from femrisk a,FEMProductRelaCondition b where a.riskcode = b.riskcode and b.ERiskSubType like '" + tERiskSubType + "%'";
		for (int j = 0; j < conditionsplit.length; j++) {
			if ((conditionsplit[j]).startsWith("default_")) {
				continue;
			} else {
//				if ("Y".equals(ValueCheck(conditionsplit[j])) && "01".equals(ValueCheck1(conditionsplit[j]))) {
				if(conditionsplit[j].indexOf(",")!=0){
					String[] conditionsplit2 = conditionsplit[j].split(",");
					for (int k = 0; k < conditionsplit2.length; k++) {
						if("Y".equals(ValueCheck(conditionsplit2[k])) && "02".equals(ValueCheck1(conditionsplit2[k]))){
							sql += " and exists (select 1 from FEMProductRelaCondition where searchcode in (" + conditionsplit[j] + ") and riskcode = a.riskcode )" ;
							break;
						}else{
							sql += " and exists (select 1 from FEMProductRelaCondition where searchcode ='" + conditionsplit2[k] + "' and riskcode = a.riskcode )" ;
						}
					}
				} else {
					sql += " and exists (select 1 from FEMProductRelaCondition where searchcode='" + conditionsplit[j] + "' and riskcode = a.riskcode )" ;
				}
			}
		}
		QueryBuilder qb1 = new QueryBuilder(sql);
		DataTable dt = qb1.executeDataTable();
		DataTable dt1 = new DataTable();
		Transaction tran = new Transaction();
//		String innerCode = "";
//		String sql1 = "select InnerCode from zccatalog where productType = ?";
//		QueryBuilder qb3 = new QueryBuilder(sql1);
//		qb3.add(tERiskSubType);
//		try{
//		innerCode = (String)qb3.executeOneValue();
//		} catch (Exception e){
//			LogUtil.error("获取栏目内部编码异常："+e.getMessage());
//			logger.error(e.getMessage(), e);
//		}
		
		SDSearchProductInfoSchema SDSearchProductInfo = new SDSearchProductInfoSchema();
		SDSearchProductInfoSet SDSearchSet = new SDSearchProductInfoSet();
		SDSearchProductInfoSet SDSearchSet2 = new SDSearchProductInfoSet();
		SDSearchProductInfo.setSearchID(searchCode);
		SDSearchSet = SDSearchProductInfo.query();
		tran.add(SDSearchSet, Transaction.DELETE);
		
		StringBuffer sb = new StringBuffer();
		sb.append("select a.title,a.SITEID,a.URL,a.logo,(select textvalue from zdcolumnvalue where columncode = 'SalesVolume' and relaid = a.id) as SalesVolume,");
		sb.append("(select textvalue from zdcolumnvalue where ColumnCode = 'CalHTML2' and relaid = a.id) as CalHTML2,");
		sb.append("(select textvalue from zdcolumnvalue where ColumnCode = 'AdaptPeopleInfo' and relaid = a.id) as AdaptPeopleInfo,");
		sb.append("(select textvalue from zdcolumnvalue where ColumnCode = 'FEMRiskBrightSpot' and relaid = a.id) as FEMRiskBrightSpot,");
		sb.append("(select textvalue from zdcolumnvalue where ColumnCode = 'DutyHTML' and relaid = a.id) as DutyHTML,");
		sb.append("(select textvalue from zdcolumnvalue where ColumnCode = 'prodcutMarkPrice' and relaid = a.id) as prodcutMarkPrice ,");
		sb.append("(select textvalue from zdcolumnvalue where ColumnCode = 'SupplierCode2' and relaid = a.id) as SupplierCode2, ");
		sb.append("(select textvalue from zdcolumnvalue where ColumnCode = 'Popular' and relaid = a.id) as Popular, ");
		sb.append("(select textvalue from zdcolumnvalue where ColumnCode = 'InitPrem' and relaid = a.id) as InitPrem,");
		sb.append("(select textvalue from zdcolumnvalue where ColumnCode = 'riskcode' and relaid = a.id ) as RiskCode" + " from zcarticle a where type='1' ");
		sb.append("and exists (select 1 from zdcolumnvalue where ColumnCode = 'RiskCode' and TextValue = ? and a.id = RelaID)" + " and a.status = '30' and cataloginnercode like '002313%' ");
		
		for (int j = 0; j < dt.getRowCount(); j++) {
			QueryBuilder qb2 = new QueryBuilder(sb.toString());
			qb2.add(dt.get(j).getString("riskcode"));
//			qb2.add(innerCode);
			dt1 = qb2.executeDataTable();
			if (dt1.getRowCount() > 0) {
				String url = dt1.get(0).getString("URL");
				if (url.indexOf("://") < 0) {
						String siteUrl = SiteUtil.getURL(dt1.get(0).getString("SITEID"));
						if (!siteUrl.endsWith("/")) {
							siteUrl = siteUrl + "/";
						}
						if (url.startsWith("/")) {
							url = url.substring(1);
						}
						url = siteUrl + url;
					};
				StringBuffer html = new StringBuffer();
				html.append("<div class=\"product_title\">");
				html.append("<span class=\"CInsuranceCompany icon_C" + dt1.get(0).getString("SupplierCode2") + "\"  ></span> <span class=\"productName\"> ");
				html.append("<a href=\"" + url + "\" target=\"_blank\"><h2 class=\"ziti\">" + dt1.get(0).getString("Title") + "</h2></a></span>");
				html.append("<span class=\"SalesVolume\">(累计销量：" + dt1.get(0).getString("SalesVolume") + ")</span>");
				html.append("<span style=\"display: none;\" id=\"productIntegral_" + dt1.get(0).getString("RiskCode") + "}\">${Article.Prop2}</span>");
				html.append("</div><div class=\"product_condition\">");
				html.append(dt1.get(0).getString("CalHTML2"));
				html.append("</div><div class=\"product_info\"><div class=\"product_info_bor\"><div class=\"prodcutMark\">");
				html.append("<ul class=\"price\" >" + dt1.get(0).getString("prodcutMarkPrice") + "</ul><ul class=\"btn\">");
				html.append("<li class=\"btn1\"><span onclick=\"chakan('"+ url +"')\">查看详情</span></li>");
				html.append("<li class=\"btn2\"><span onclick=\"submitp('"+ dt1.get(0).getString("RiskCode") +"');\">加入收藏</span><span class=\"add_cp_list\" style=\"margin-left: 6px;\"");
				html.append("onclick=\"showcp('"+ dt1.get(0).getString("Title") +"','"+ dt1.get(0).getString("logo") +"','"+ dt1.get(0).getString("RiskCode") +"','"+Config.getServerContext()+"','"+tERiskSubType+"');\">加入对比</span></li>");
				html.append("</ul></div></div>");
				html.append("<div class=\"AdaptPeopleInfo\">" + dt1.get(0).getString("AdaptPeopleInfo") + "</div>");
				html.append("<div class=\"productFeature\">" + dt1.get(0).getString("FEMRiskBrightSpot") + "</div>");
				html.append(dt1.get(0).getString("DutyHTML"));
				html.append("</div>");

				SDSearchProductInfoSchema SDSearchProductInfo1 = new SDSearchProductInfoSchema();
				SDSearchProductInfo1.setid(NoUtil.getMaxID("FEMSearchID"));
				SDSearchProductInfo1.setSearchID(searchCode);
				SDSearchProductInfo1.setTitle(dt1.get(0).getString("Title"));
				SDSearchProductInfo1.setFEMRiskBrightSpot(dt1.get(0).getString("FEMRiskBrightSpot"));
				SDSearchProductInfo1.setAdaptPeopleInfo(dt1.get(0).getString("AdaptPeopleInfo"));
				SDSearchProductInfo1.setSupplierCode2(dt1.get(0).getString("SupplierCode2"));
				SDSearchProductInfo1.setURL(dt1.get(0).getString("URL"));
				SDSearchProductInfo1.setCalHTML2(dt1.get(0).getString("CalHTML2"));
				SDSearchProductInfo1.setDutyHTML(dt1.get(0).getString("DutyHTML"));
				SDSearchProductInfo1.setprodcutMarkPrice(dt1.get(0).getString("ProdcutMarkPrice"));
				SDSearchProductInfo1.setSalesVolume(dt1.get(0).getString("SalesVolume"));
				SDSearchProductInfo1.setPopular(dt1.get(0).getString("Popular"));
				SDSearchProductInfo1.setRiskCode(dt1.get(0).getString("RiskCode"));
				SDSearchProductInfo1.setInitPrem(dt1.get(0).getString("InitPrem"));
				SDSearchProductInfo1.setDynamicHTML(html.toString());
				SDSearchProductInfo1.setProp1("");
				SDSearchProductInfo1.setProp2("");
				SDSearchProductInfo1.setProp3("");
				SDSearchProductInfo1.setProp4("");
				SDSearchProductInfo1.setcreateDate(new Date());
				SDSearchProductInfo1.setModifyDate(new Date());
				SDSearchSet2.add(SDSearchProductInfo1);
			}
		}
		try {
			// update 条件表
			tran.add(SDSearchSet2, Transaction.INSERT);
			tran.add(new QueryBuilder("update SDAllCondtion set ProductCount = ? where searchid=? ", SDSearchSet2.size(), searchCode));
			Object[] argArr ={searchCode, SDSearchSet2.size(), getDateTimeByMillisecond(System.currentTimeMillis() - start_time)};
			logger.info("searchCode:{}生成产品数据: {} - 耗时：{}", argArr);
			return tran.commit();
		} catch (Exception e) {
			logger.error("FEMSearchProductInfo表数据提交发生异常：" + e.getMessage(), e);
			return false;
		}
	}

	/**
	 * 检验前台传来的值是否是多选
	 * 
	 * @param tconditionsplit
	 *            前台传递的字符串经过拆分后得到字符串
	 * @return
	 */
	public static String ValueCheck(String tconditionsplit) {
		String sql = "select IsMultipleChoice from FEMSearchConditionInfo where id =? ";
		QueryBuilder qb = new QueryBuilder(sql, tconditionsplit);
		String IsMultipleChoice = (String) qb.executeOneValue();
		return IsMultipleChoice;
	}

	/**
	 * 检验前台传来的值是交集还是并集
	 * 
	 * @param tconditionsplit
	 *            前台传递的字符串经过拆分后得到字符串
	 * @return
	 */
	public static String ValueCheck1(String tconditionsplit) {
		String sql = "select Intersection from FEMSearchConditionInfo where id =? ";
		QueryBuilder qb = new QueryBuilder(sql, tconditionsplit);
		String Intersection = (String) qb.executeOneValue();
		return Intersection;
	}
	
	/**
	 * 页面初始化同步产品.
	 * 
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Mapx initProductSynchro(Mapx params) {
		Map<String, String> ERiskSubType = new HashMap<String, String>();
		DataTable dt = new QueryBuilder("select codevalue,codename from zdcode where codetype = 'home_suppliercode' and codevalue!='0000' order by codevalue desc").executeDataTable();
		for (int i = 0; i < dt.getRowCount(); i++) {
			ERiskSubType.put(dt.getString(i, "codevalue"),dt.getString(i, "codename"));
		}
		params.put("ERiskSubTypeList",
				HtmlUtil.mapxToOptions(ERiskSubType, true));
		return params;
	}
	
	/**
	 * 数据查询同步.
	 * 
	 * @param dga
	 */
	public static void dg1DataBindTb(DataGridAction dga) {
		String sql = "select productId ,productName,URL,createDate from SDSearchRelaProduct ";
		QueryBuilder qb = new QueryBuilder(sql);

		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(),
				dga.getPageIndex());
		dga.bindData(dt);
	}
	

	
	/**
	 * 同步全部产品.
	 */
	public void productSynchroAll(){
		try{
			Transaction trans = new Transaction();
			SDSearchRelaProductSet SDSearchRelaSet = new SDSearchRelaProductSet();
			String sql = "delete from SDSearchRelaProduct where 1=1";
			trans.add(new QueryBuilder(sql));
			String selectSql = "select a.id, a.title,a.SITEID,a.URL,a.logo from zcarticle a where type='1' and ( a.status = '30' or a.status = '50' )  and cataloginnercode like '002313%'";
			DataTable dt1 = new DataTable();
			DataTable dt = new QueryBuilder(selectSql).executeDataTable();
			String innerCodeBXSCSql = "select innercode from zccatalog where id=(select value from zdconfig where type='CatalogBXSCID')";
			String innerCode = new QueryBuilder(innerCodeBXSCSql).executeString();
			for(int i= 0; i<dt.getRowCount();i++){
				boolean flag = false;
				String id = dt.getString(i,0);
				if(StringUtil.isNotEmpty(id)){
					SDSearchRelaProductSchema sdSearchRelaProduct = new SDSearchRelaProductSchema();
					dt1 = new QueryBuilder("select textvalue from zdcolumnvalue where ColumnCode = 'riskcode' and relaid = '"+id+"'").executeDataTable();
					if(dt1.getRowCount()>0){
						String productId = dt1.getString(0, 0);
						sdSearchRelaProduct.setProductID(productId);
						for(int j = 0 ;j<SDSearchRelaSet.size();j++){
							if(SDSearchRelaSet.get(j).getProductID().equals(productId)){
								flag = true;
								break;
							}
						}
					}
					if(flag){
						continue;
					}
					sdSearchRelaProduct.setProductName(dt.getString(i, 1));
					sdSearchRelaProduct.setSiteID(dt.getString(i, 2));
					sdSearchRelaProduct.setProp1(id);
					String url = dt.getString(i, 3);
					if (url.indexOf("://") < 0) {
						String siteUrl = SiteUtil.getURL(dt.getString(i, 2));
						if (!siteUrl.endsWith("/")) {
							siteUrl = siteUrl + "/";
						}
						if (url.startsWith("/")) {
							url = url.substring(1);
						}
						url = siteUrl + url;
					};
					sdSearchRelaProduct.setURL(url);
					sdSearchRelaProduct.setlogo(dt.getString(i, 4));
					dt1 = new QueryBuilder("select textvalue from zdcolumnvalue where columncode = 'SalesVolume' and relaid = '"+id+"'").executeDataTable();
					if(dt1.getRowCount()>0){
						sdSearchRelaProduct.setSalesVolume(dt1.getString(0, 0));
					}
					dt1 = new QueryBuilder("select textvalue from zdcolumnvalue where ColumnCode = 'CalHTML2' and relaid = '"+id+"'").executeDataTable();
					if(dt1.getRowCount()>0){
						sdSearchRelaProduct.setCalHTML2(dt1.getString(0, 0));
					}
					dt1 = new QueryBuilder("select textvalue from zdcolumnvalue where ColumnCode = 'AdaptPeopleInfo' and relaid = '"+id+"'").executeDataTable();
					if(dt1.getRowCount()>0){
						sdSearchRelaProduct.setAdaptPeopleInfo(dt1.getString(0, 0));
					}
					dt1 = new QueryBuilder("select textvalue from zdcolumnvalue where ColumnCode = 'fEMRiskBrightSpot' and relaid = '"+id+"'").executeDataTable();
					if(dt1.getRowCount()>0){
						sdSearchRelaProduct.setFEMRiskBrightSpot(dt1.getString(0, 0));
					}
					dt1 = new QueryBuilder("select textvalue from zdcolumnvalue where ColumnCode = 'DutyHTML' and relaid = '"+id+"'").executeDataTable();
					if(dt1.getRowCount()>0){
						sdSearchRelaProduct.setDutyHTML(dt1.getString(0, 0));
					}
					dt1 = new QueryBuilder("select textvalue from zdcolumnvalue where ColumnCode = 'prodcutMarkPrice' and relaid = '"+id+"'").executeDataTable();
					if(dt1.getRowCount()>0){
						sdSearchRelaProduct.setprodcutMarkPrice(dt1.getString(0, 0));
					}
					dt1 = new QueryBuilder("select textvalue from zdcolumnvalue where ColumnCode = 'SupplierCode2' and relaid = '"+id+"'").executeDataTable();
					if(dt1.getRowCount()>0){
						sdSearchRelaProduct.setSupplierCode2(dt1.getString(0, 0));
					}
					dt1 = new QueryBuilder("select textvalue from zdcolumnvalue where ColumnCode = 'Popular' and relaid = '"+id+"'").executeDataTable();
					if(dt1.getRowCount()>0){
						sdSearchRelaProduct.setPopular(dt1.getString(0, 0));
					}
					dt1 = new QueryBuilder("select textvalue from zdcolumnvalue where ColumnCode = 'InitPrem' and relaid = '"+id+"'").executeDataTable();
					if(dt1.getRowCount()>0){
						sdSearchRelaProduct.setInitPrem(dt1.getString(0, 0));		
					}
					dt1 = new QueryBuilder("select textvalue from zdcolumnvalue where ColumnCode = 'fEMRiskBrightSpotNew' and relaid = '"+id+"'").executeDataTable();
					if(dt1.getRowCount()>0){
						sdSearchRelaProduct.setFEMRiskBrightSpotNew(dt1.getString(0, 0));		
					}
					dt1 = new QueryBuilder("select textvalue from zdcolumnvalue where ColumnCode = 'DutyHTML2' and relaid = '"+id+"'").executeDataTable();
					if(dt1.getRowCount()>0){
						sdSearchRelaProduct.setDutyHTML2(dt1.getString(0, 0));		
					}
					dt1 = new QueryBuilder("select textvalue from zdcolumnvalue where ColumnCode = 'ProductActive' and relaid = '"+id+"'").executeDataTable();
					if(dt1.getRowCount()>0){
						sdSearchRelaProduct.setProductActive(dt1.getString(0, 0));		
					}
					dt1 = new QueryBuilder("select textvalue from zdcolumnvalue where ColumnCode = 'LogoLink' and relaid = '"+id+"'").executeDataTable();
					if(dt1.getRowCount()>0){
						sdSearchRelaProduct.setLogoLink(dt1.getString(0, 0));		
					}
					dt1 = new QueryBuilder("select textvalue from zdcolumnvalue where ColumnCode = 'BasePremHTML' and relaid = '"+id+"'").executeDataTable();
					if(dt1.getRowCount()>0){
						sdSearchRelaProduct.setBasePrem(dt1.getString(0, 0));		
					}
					dt1 = new QueryBuilder("select textvalue from zdcolumnvalue where ColumnCode = 'BasePremV3' and relaid = '"+id+"'").executeDataTable();
					if(dt1.getRowCount()>0){
						sdSearchRelaProduct.setBasePremV3(dt1.getString(0, 0));		
					}
					dt1 = new QueryBuilder("select textvalue from zdcolumnvalue where ColumnCode = 'AdaptPeopleInfoV3' and relaid = '"+id+"'").executeDataTable();
					if(dt1.getRowCount()>0){
						sdSearchRelaProduct.setAdaptPeopleInfoV3(dt1.getString(0, 0));		
					}
					dt1 = new QueryBuilder("select textvalue from zdcolumnvalue where ColumnCode = 'AdaptPeopleInfoListV3' and relaid = '"+id+"'").executeDataTable();
					if(dt1.getRowCount()>0){
						sdSearchRelaProduct.setAdaptPeopleInfoListV3(dt1.getString(0, 0));		
					}
					dt1 = new QueryBuilder("select textvalue from zdcolumnvalue where ColumnCode = 'DutyHTMLV3' and relaid = '"+id+"'").executeDataTable();
					if(dt1.getRowCount()>0){
						sdSearchRelaProduct.setDutyHTMLV3(dt1.getString(0, 0));		
					}
					dt1 = new QueryBuilder("select textvalue from zdcolumnvalue where ColumnCode = 'RecomDutyV3' and relaid = '"+id+"'").executeDataTable();
					if(dt1.getRowCount()>0){
						sdSearchRelaProduct.setRecomDutyV3(dt1.getString(0, 0));		
					}
					dt1 = new QueryBuilder("select textvalue from zdcolumnvalue where ColumnCode = 'InitPrem' and relaid = '"+id+"'").executeDataTable();
					if(dt1.getRowCount()>0){
						sdSearchRelaProduct.setBasePremValue(dt1.getString(0, 0));		
					}
					//cps保费
					dt1 = new QueryBuilder("  SELECT textvalue FROM zdcolumnvalue WHERE ColumnCode = 'InitPremCPS' AND EXISTS (SELECT 1 FROM zcarticle WHERE prop4='"+sdSearchRelaProduct.getProductID()+"' AND id = relaid AND TYPE='1' AND cataloginnercode LIKE '"+innerCode+"%')").executeDataTable();
					if(dt1.getRowCount()>0 && StringUtil.isNotEmpty(dt1.getString(0, 0))){
						sdSearchRelaProduct.setInitPremCPS(dt1.getString(0, 0));		
					}
					dt1 = new QueryBuilder("select textvalue from zdcolumnvalue where ColumnCode = 'prodcutMarkPriceCPS' AND EXISTS (SELECT 1 FROM zcarticle WHERE prop4='"+sdSearchRelaProduct.getProductID()+"' AND id = relaid AND TYPE='1' AND cataloginnercode LIKE '"+innerCode+"%')").executeDataTable();
					if(dt1.getRowCount()>0 && StringUtil.isNotEmpty(dt1.getString(0, 0))){
						sdSearchRelaProduct.setprodcutMarkPriceCPS(dt1.getString(0, 0));		
					}
					// 产品保险公司折扣
					dt1 = new QueryBuilder("select textvalue from zdcolumnvalue where ColumnCode = 'DiscountRate' and relaid = '"+id+"'").executeDataTable();
					if(dt1.getRowCount()>0){
						sdSearchRelaProduct.setProp2(dt1.getString(0, 0));		
					}
					sdSearchRelaProduct.setCreateDate(new Date());
					sdSearchRelaProduct.setModifyDate(new Date());
					SDSearchRelaSet.add(sdSearchRelaProduct);
					
				}
			}
			trans.add(SDSearchRelaSet, Transaction.INSERT);
			trans.commit();
		} catch (Exception e){
			logger.error("产品信息同步方法异常："+e.getMessage(), e);
		}
	}

	/**
	 * 产品同步.
	 */
	public void productSynchro() {
		try {
			String IDs = $V("IDs");

			if (StringUtil.isEmpty(IDs)) {
				Response.setLogInfo(0, "查询失败!编码不存在:" + IDs);
				return;
			}

			if (!StringUtil.checkID(IDs)) {
				Response.setStatus(0);
				Response.setMessage("传入ID时发生错误!");
				return;
			}
			     
			SDAllCondtionSet tSDAllCondtion_set = queryId(IDs);
			
			if(tSDAllCondtion_set.size() > 0){
				execProductSynchro(tSDAllCondtion_set);
				Response.setLogInfo(1, "同步成功!");
			}else{
				return;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setLogInfo(0, "同步失败!" + e.getMessage());
		}
	}

	/**
	 * 通过ID返回规则.
	 * @param IDs
	 * @return
	 */
	public SDAllCondtionSet queryId(String IDs) {
		
		SDAllCondtionSchema tSDAllCondtion = new SDAllCondtionSchema();
		SDAllCondtionSet tSDAllCondtion_set = tSDAllCondtion
				.query(new QueryBuilder("where Id in (" + IDs + ")"));

		if (tSDAllCondtion_set == null || tSDAllCondtion_set.size() == 0) {
			Response.setStatus(0);
			Response.setMessage("查询失败!编码不存在!" + IDs);
		}
		return tSDAllCondtion_set;
	}

	/**
	 * 执行同步.
	 * @param tSDAllCondtion_set
	 * @return
	 * @throws Exception
	 */
	public void execProductSynchro(SDAllCondtionSet tSDAllCondtion_set)
			throws Exception {
		GetDBdata db = new GetDBdata();
		for (int i = 0; i < tSDAllCondtion_set.size(); i++) {
			SDAllCondtionSchema tSDAllCondtion1 = tSDAllCondtion_set.get(i);

			// 通过规则ID(条件)查询产品
			// 返回 符合规则的产品名称
			String queryProductSql = "select id from zcarticle  where title in(select title  from sdsearchproductinfo where searchid='"
					+ tSDAllCondtion1.getSearchID() + "')";
			List<HashMap<String, String>> productIdList = db
					.query(queryProductSql);

			StringBuffer buffer = new StringBuffer();
			for (int y = 0; y < productIdList.size(); y++) {
				if (y == 0) {
					buffer.append(productIdList.get(y).get("ID"));
				} else {
					buffer.append("," + productIdList.get(y).get("ID"));
				}
			}

			if (productIdList.size() > 0) {
				// 查询产品信息
				String productSql = "select z.title,z.SITEID,z.url,z.logo,"
						+ "(select textvalue from zdcolumnvalue where relaid = z.id and columncode = 'SalesVolume'  ) as SalesVolume,"
						+ "(select textvalue from zdcolumnvalue where relaid = z.id and ColumnCode = 'CalHTML2') as CalHTML2,"
						+ "(select textvalue from zdcolumnvalue where relaid = z.id and ColumnCode = 'AdaptPeopleInfo') as AdaptPeopleInfo,"
						+ "(select textvalue from zdcolumnvalue where relaid = z.id and ColumnCode = 'FEMRiskBrightSpot') as FEMRiskBrightSpot,"
						+ "(select textvalue from zdcolumnvalue where relaid = z.id and ColumnCode = 'DutyHTML') as DutyHTML,"
						+ "(select textvalue from zdcolumnvalue where relaid = z.id and ColumnCode = 'prodcutMarkPrice') as prodcutMarkPrice ,"
						+ "(select textvalue from zdcolumnvalue where relaid = z.id and ColumnCode = 'SupplierCode2') as SupplierCode2, "
						+ "(select textvalue from zdcolumnvalue where relaid = z.id and ColumnCode = 'Popular') as Popular, "
						+ "(select textvalue from zdcolumnvalue where relaid = z.id and ColumnCode = 'InitPrem') as InitPrem,"
						+ "(select textvalue from zdcolumnvalue where relaid = z.id and ColumnCode = 'riskcode') as RiskCode "
						+ "from zcarticle z,zdcolumnvalue z1 where z.type='1' and z.id = z1.relaid and z1.relaid in("
						+ buffer + ")";
				List<HashMap<String, String>> productList = db
						.query(productSql);

				for (int j = 0; j < productList.size(); j++) {
					// 通过 产品规则 名称 更新
					String url = productList.get(j).get("URL");
					if (url.indexOf("://") < 0) {
							String siteUrl = SiteUtil.getURL(productList.get(j).get("SITEID"));
							if (!siteUrl.endsWith("/")) {
								siteUrl = siteUrl + "/";
							}
							if (url.startsWith("/")) {
								url = url.substring(1);
							}
							url = siteUrl + url;
						};
					String update = "update sdsearchproductinfo " + "set "
							+ "title='"
							+ productList.get(j).get("Title")
							+ "', "
							+ "FEMRiskBrightSpot='"
							+ productList.get(j).get("FEMRiskBrightSpot")
							+ "', "
							+ "AdaptPeopleInfo='"
							+ productList.get(j).get("AdaptPeopleInfo")
							+ "', "
							+ "SupplierCode2='"
							+ productList.get(j).get("SupplierCode2")
							+ "', "
							+ "url='"
							+ url
							+ "', "
							+ "prodcutMarkPrice='"
							+ productList.get(j).get("prodcutMarkPrice")
							+ "', "
							+ "SalesVolume='"
							+ productList.get(j).get("SalesVolume")
							+ "', "
							+ "Popular='"
							+ productList.get(j).get("Popular")
							+ "', "
							+ "calhtml2 = '"
							+ productList.get(j).get("DutyHTML")
							+ "', "
							+ "RiskCode='"
							+ productList.get(j).get("RiskCode")
							+ "', "
							+ "initPrem='"
							+ productList.get(j).get("InitPrem")
							+ "' "
							+ "where  title = '"
							+ productList.get(j).get("Title")
							+ "' and searchid='"
							+ tSDAllCondtion1.getSearchID() + "'";
					db.execUpdateSQL(update, null);
				}
			}
		}
	}
	public void ProAllAddress(){
		LongTimeTask ltt = new LongTimeTask() {
			public void execute() {
				AllAddress(this);
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
		ltt.setCurrentInfo("开始生成......");
		$S("TaskID", "" + ltt.getTaskID());
	}
	
	/**
	 * 查询全部筛选条件
	 */
	private void AllAddress(LongTimeTask ltt) {
		try{
			ltt.setPercent(0);
			String SearchID = "";
			String sql = "select * from SDAllCondtion ";
			QueryBuilder qb = new QueryBuilder(sql);
			DataTable dt = qb.executeDataTable();
//			int plan = 0;
//			plan = 100 / dt.getRowCount();
			// 生成数据
			ltt.setCurrentInfo("生成筛选地址中......");
			SDSearchAddressSet updateSet = new SDSearchAddressSet();
			SDSearchAddressSet insertSet = new SDSearchAddressSet();
			Transaction trans = new Transaction();
			String sql1 = "select SearchCode from FEMSearchConditionInfo where ERiskType = ? and searchlevel = '1' order by -SearchOrder";
			for(int i=0;i<dt.getRowCount();i++){
				Map<String, String> map = new HashMap<String, String>();
				String newSort = "";
				ltt.setPercent(20);
				SearchID = dt.get(i).getString("SearchID");
				String[] sea = SearchID.split("\\|");
				for(int j=0;j<sea.length;j++){
					if(sea[j].startsWith("default_")){
						continue;
					}else{
						//如果sea[j]中是多选，根据第一个值获取searchCode
						if(sea[j].indexOf(",")!=-1){
							String[] mul = sea[j].split(",");
							map.put(getSearchCode(mul[0]), sea[j]);
						}else{
							map.put(getSearchCode(sea[j]), sea[j]);
						}
					}
				}
				//去掉difault之后，把map中根据key按字母顺序排序
				map = sort(map);
				Iterator<String> itr = map.keySet().iterator();
				String[] key = new String[map.keySet().size()];
				//把map的key值存到数组中
				int n=0;
				while(itr.hasNext()){
					key[n] = itr.next();
					n++;
				}
				String[] sortSea = new String[sea.length];
				ArrayList<String> list=new ArrayList<String>();
				QueryBuilder qb1 = new QueryBuilder(sql1);
				DataTable dt1 = new DataTable();
				qb1.add(dt.get(i).getString("ERiskSubType").substring(0,1));
				dt1 = qb1.executeDataTable();
				//取当前险种类型的searchCode，根据字母顺序排序
				for(int j=0;j<dt1.getRowCount();j++){
					list.add(dt1.get(j).getString("SearchCode"));
				}
				Collections.sort(list);
				//将排好序的searchcode进行组合
				for(int k=0;k<list.size();k++){
					Boolean flag = false;
					for(int l=0;l<n;l++){
						if(list.get(k).equals(key[l])){
							sortSea[k] = key[l]+":"+map.get(key[l]);
							flag = true;
						}
					}
					if(!flag){
						sortSea[k] = list.get(k);
					}
				}
				//拼装成串
				for(int p=0;p<sortSea.length;p++){
					newSort += sortSea[p];
					if(p!=sortSea.length-1){
						newSort += "|";
					}
				}
				//更新到SDSearchAddress表中
				SDSearchAddressSchema searchAddressSchema = new SDSearchAddressSchema();
				SDSearchAddressSet searchAddressSet = new SDSearchAddressSet();
				searchAddressSchema.setAddressCode(newSort);
				String ID = "";
				searchAddressSet = searchAddressSchema.query();
				if(searchAddressSet.size()>0){
					searchAddressSchema = searchAddressSet.get(0);
					searchAddressSchema.setModifyDate(new Date());
					searchAddressSchema.setSearchAddress(getSearchAddress(searchAddressSchema.getID()));
					updateSet.add(searchAddressSchema);
				}else{
					ID = StringUtil.leftPad(String.valueOf(NoUtil.getMaxID("SearchAddressID")),'0',10);
					searchAddressSchema.setID(ID);
					searchAddressSchema.setSearchID(SearchID);
					searchAddressSchema.setSearchAddress(getSearchAddress(ID));
					searchAddressSchema.setERiskType(dt.get(i).getString("ERiskSubType"));
					searchAddressSchema.setCreateDate(new Date());
					searchAddressSchema.setModifyDate(new Date());
					insertSet.add(searchAddressSchema);
				}
				
			}
			ltt.setPercent(70);
			trans.add(updateSet, Transaction.UPDATE);
			trans.add(insertSet, Transaction.INSERT);
			trans.commit();
			ltt.setPercent(100);
			Response.setStatus(1);
		} catch (Exception e) {
			logger.error("生成筛选地址ID方法中发生异常："+e.getMessage(), e);
		}
	}
	/**
	 * 生成地址方法
	 * @param ID
	 * @return
	 */
	public static String getSearchAddress(String ID){
		try{
			String SearchAddress = "";
			String DetailAddress = "";
			String sql = "select value from zdconfig where type = 'SearchAddress'";
			QueryBuilder qb = new QueryBuilder(sql);
			SearchAddress = (String)qb.executeOneValue();
			DetailAddress = SearchAddress+ID+".shtml";
			return DetailAddress;
		} catch (Exception e){
			logger.error("生成地址方法异常"+e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 根据ID获取SearchCode
	 * @param ID
	 * @return
	 */
	public static String getSearchCode(String ID){
		try{
			String SearchCode = "";
			String SearchGroupID = "";
			String ERiskType = "";
			String sql = "select SearchGroupID,ERiskType from FEMSearchConditionInfo where id = ?";
			QueryBuilder qb = new QueryBuilder(sql);
			qb.add(ID);
			DataTable dt = qb.executeDataTable();
			if(dt.getRowCount()>0){
				SearchGroupID = dt.get(0).getString("SearchGroupID").substring(0, 4);
				ERiskType = dt.get(0).getString("ERiskType");
				sql = "select SearchCode from FEMSearchConditionInfo where SearchGroupID = ? and ERiskType = ?";
				qb = new QueryBuilder(sql);
				qb.add(SearchGroupID);
				qb.add(ERiskType);
				dt = qb.executeDataTable();
				if(dt.getRowCount()>0){
					SearchCode = dt.get(0).getString("SearchCode");
				}
			}
			return SearchCode ;
		} catch (Exception e){
			logger.error("获取searchcode方法中发生异常："+e.getMessage(), e);
			return null;
		}
	}
	/**
	 * Map把key根据字母顺序排序
	 * @param map
	 * @return
	 */
	 @SuppressWarnings("unchecked")
	public static Map<String, String> sort(Map map) {
	        Map<String, String> mapVK = new TreeMap<String, String>(
	            new Comparator<String>() {
	                public int compare(String obj1, String obj2) {
	                    String v1 = (String)obj1;
	                    String v2 = (String)obj2;
	                    int s = v1.compareTo(v2);
	                    return s;
	                }
	            }
	        );
	        Set col = map.keySet();
	        Iterator iter = col.iterator();
	        while (iter.hasNext()) {
	            String key = (String) iter.next();
	            String value = (String) map.get(key);
	            mapVK.put(key, value);
	        }
	        return mapVK;
	    }
	 
	/**
	 * 取得产品详细页地址
	 * 
	 * @param cProductid
	 *            产品编码
	 * @param cFrom
	 *            平台来源 1：主站 0：保险商城
	 * @return 产品详细页地址
	 */
	public static String getUrl(String cProductid, String cFrom) {
		String url = "";
		// 主站
		if ("1".equals(cFrom)) {
			url = new QueryBuilder(
					"select HtmlPath from sdproduct where ProductID = ?",
					cProductid).executeString();

			// 保险商城
		} else if ("0".equals(cFrom)) {
			url = new QueryBuilder(
					"SELECT URL FROM zcarticle WHERE prop4 = ? AND URL LIKE 'bxsc%' AND `type`='1'",
					cProductid).executeString();
			if (StringUtil.isNotEmpty(url)) {
				url = Config.getValue("FrontServerContextPath") + "/" + url;
			}
		}

		return url;
	}
	 
	/**
	 * 取得缴费年期拆分的产品详细页地址替换投保要素中的链接
	 * 
	 * @param calHtml
	 *            投保要素
	 * @param cFrom
	 *            平台来源 1：主站 0：保险商城
	 * @return
	 */
	public static String getProductUrl(String calHtml, String cFrom) {

		String[] html = calHtml.split("_ProductUrl");
		if (html != null && html.length > 0) {
			String riskcodes = "";
			String riskcode = "";
			int len = html.length;
			// 取得产品编码
			for (int i = 0; i < len; i++) {
				if (html[i].contains("ProHref_")) {
					riskcode = html[i].split("ProHref_")[1];
					if (StringUtil.isNotEmpty(riskcode)
							&& !riskcodes.contains(riskcode)) {
						riskcodes += (",'" + riskcode + "'");
					}
				}
			}
			if (StringUtil.isNotEmpty(riskcodes)) {
				riskcodes = riskcodes.substring(1);
				DataTable dt = null;
				// 取得产品详细页URL
				// 主站
				if ("1".equals(cFrom)) {
					dt = new QueryBuilder(
							"select ProductID,HtmlPath from sdproduct where ProductID in ("+riskcodes+")"
							).executeDataTable();

					// 保险商城
				} else if ("0".equals(cFrom)) {
					String contextPath = Config
							.getValue("FrontServerContextPath") + "/";
					dt = new QueryBuilder(
							"SELECT prop4, CONCAT('"
									+ contextPath
									+ "',URL) FROM zcarticle WHERE prop4 in ("+riskcodes+") AND URL LIKE 'bxsc%' AND `type`='1'"
							).executeDataTable();
				}
				// 替换产品详细页URL
				if (dt != null && dt.getRowCount() > 0) {
					int rowCount = dt.getRowCount();
					for (int i = 0; i < rowCount; i++) {
						calHtml = calHtml
								.replace("ProHref_" + dt.getString(i, 0)
										+ "_ProductUrl", dt.getString(i, 1));
					}
				}
			}
		}

		return calHtml;
	}
	 
	 /**
	  * 根据关联产品处理详细页计划显示
	  * @param cRelaProduct 关联产品ID 已“，” 分隔
	  * @param cProductid 本产品
	  * @return
	  */
	 public static String getCalHTML(String cProductid,String cRelaProduct,String cFrom){
		 String tCalHTML="";
		 if(StringUtil.isNotEmpty(cRelaProduct)){
			 try{
				 String[] relap = cRelaProduct.split(",");
				 int rlen = relap.length;
				 if(rlen>=1){
					 for(int i=0;i<rlen;i++){
						 String[] tProductidInfo = relap[i].split("-");
						 
						 String tProductid = tProductidInfo[0];//产品
						 String tPlanCode = tProductidInfo[1];//计划编码
						
						 QueryBuilder qb1 = new QueryBuilder(" SELECT b.FactorDisplayValue FROM FERiskAppFactorB a LEFT JOIN FEMRiskFactorListB b ON a.AppFactorCode = b.AppFactorCode WHERE a.RiskCode = ? AND a.FactorType = 'Plan' AND factorvalue = ? ");
						 qb1.add(tProductid);
						 qb1.add(tPlanCode);
						 String tPlanName = qb1.executeString();//计划名称
						 if(StringUtil.isEmpty(tPlanName)){
							 continue;
						 }
						 //tPlanName = "计划一";
						 QueryBuilder qb = new QueryBuilder("select HtmlPath from sdproduct where ProductID=?",tProductid);
						 QueryBuilder qb11 = new QueryBuilder("SELECT URL FROM zcarticle WHERE prop4=? AND URL LIKE 'bxsc%' AND `type`='1'",tProductid);
						 String htmlpath = "javascript:void(0);";
						 if("0".equals(cFrom)){
							 if(StringUtil.isNotEmpty(qb11.executeString())){
								 htmlpath = Config.getValue("FrontServerContextPath")+"/"+qb11.executeString();
							 }
						 }else  if("1".equals(cFrom)){
							 if(StringUtil.isNotEmpty(qb.executeString())){
								 htmlpath = qb.executeString();
							 }
						 }
						 
						 if(StringUtil.isEmpty(htmlpath)){
							 continue;
						 }
						 String tHTML = "";
						 if(cProductid.equals(tProductid)){
							 tHTML = tHTML+"<li class=\"li_selected\">";
						 }else{
							 tHTML = tHTML+"<li>";
						 }
						 String liClass = "long_width";
						 int len;
						 try {
						 	len = tPlanName.getBytes("GBK").length;
						 	if (len > 10) {
								liClass = "long_width3";
							}
						 } catch (UnsupportedEncodingException e) {
						 	logger.error(e.getMessage(), e);
						 }
						 tHTML = tHTML+"<span id='Plan_"+i+"' class='"+liClass+"' name='"+tPlanCode+"'><a href=\""+htmlpath+"\">"+tPlanName+"</a></span>";
					     tHTML = tHTML+"</li>";
					     tCalHTML = tCalHTML+tHTML;
						} 
					 }
			 }catch(Exception e){
				 logger.error("产品详细页-计划拆分异常：", e.getMessage(), e);
			 }
		 }
		 return tCalHTML;
	 }
	 
	 /**
	  * 保费试算根据保险公司判断是起保日期0还是当前日期1
	  * @return
	  */
	 public static String getSpecialPremCalFlag(String productId){
		 String specialPremCalFlag =new QueryBuilder("SELECT CodeValue FROM zdcode WHERE "
					+ "CodeType='StartDateCompanyCode' and CodeValue=?", productId.substring(0, 4)).executeString();//特殊险保费计算标识
		 if (specialPremCalFlag != null){
			return "1";
		 }
		 return "0";
	 }
	 
	 public static void main(String[] args){
		 
//		 AllAddress(null);
	 }
}
