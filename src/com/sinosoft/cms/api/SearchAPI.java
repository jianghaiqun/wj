package com.sinosoft.cms.api;

import cn.com.sinosoft.util.RedisConsts;

import com.alibaba.fastjson.JSONObject;
import com.finance.util.JedisCommonUtil;
import com.sinosoft.cms.pub.SiteUtil;
import com.sinosoft.cms.site.Catalog;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.LogUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.ServletUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.product.Filtrate;
import com.sinosoft.schema.FEMSearchConditionInfoSchema;
import com.sinosoft.schema.FEMSearchConditionInfoSet;
import com.sinosoft.schema.ZCFullTextSchema;
import com.sinosoft.schema.ZCFullTextSet;
import com.sinosoft.schema.ZCSiteSchema;
import com.sinosoft.search.ArticleIndexer;
import com.sinosoft.search.ArticleSearcher;
import com.sinosoft.search.ProductIndexer;
import com.sinosoft.search.ResourceIndexer;
import com.sinosoft.search.SearchParameters;
import com.sinosoft.search.SearchResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SearchAPI {
	private static final Logger logger = LoggerFactory.getLogger(SearchAPI.class);

	private static Mapx siteMap = new Mapx();
	public static String getIndexIDBySiteID(String site) {
		if (StringUtil.isEmpty(site) || !StringUtil.isDigit(site)) {
			return null;
		} else {
			String id = siteMap.getString(site);
			if (id == null) {
				ZCFullTextSchema ft = new ZCFullTextSchema();
				ft.setSiteID(site);
				ft.setProp1("AutoIndex"); 
				ZCFullTextSet set = ft.query();
				if (set.size() > 0) {
					id = "" + set.get(0).getID();
					siteMap.put(site, id);
				} else {
					return null;
				}
			}
			return id;
		}
	}

	public static void update(long id) {
		ZCFullTextSchema ft = new ZCFullTextSchema();
		ft.setID(id);
		if (ft.fill()) {
			if (55 == id) {
				ProductIndexer productIndexer = new ProductIndexer();
				productIndexer.createProductIndex(id);
			} else {
				if (ft.getType().equals("Article")) {
					updateArticleIndex(id,ft.getProp1());
				} else {
					updateResourceIndex(id);
				}
			}
		}
	}

	/**
	 * 更新文章索引
	 */
	public static void updateArticleIndex(long id,String prop1) { 
		String path = Config.getContextRealPath() + "WEB-INF/data/index/" +1025+ "/";
		String pathNew = Config.getContextRealPath() + "WEB-INF/data/index/" + id + "/";
		FileUtil.deleteFromDir(path);
		ArticleIndexer ai = new ArticleIndexer(id);
		if(prop1!= null && ("1".equals(prop1)||"2".equals(prop1))){						/*产品部分调用单独分词器-add by lilang*/
		ai.proStart();
		}else{
		ai.start();	
		}
		FileUtil.deleteFromDir(pathNew);
		FileUtil.copy(path,pathNew);
	
	}

	/**
	 * 更新资源文件索引
	 */
	public static void updateResourceIndex(long id) {
		ResourceIndexer ri = new ResourceIndexer(1025,id);
		ri.start();
	}
	public static void main(String[] args) {
		updateResourceIndex(55);
	}

	public static SearchResult searchArticle(long id, String keyword, int pageSize, int pageIndex) {
		SearchParameters sps = new SearchParameters();
		sps.addFulltextField("_KeyWord", keyword);
		sps.setPageIndex(pageIndex);
		sps.setPageSize(pageSize);
		sps.setIndexID(id);
		return ArticleIndexer.search(sps);
	}

	public static SearchResult searchArticle(String keyword, int pageSize, int pageIndex, Mapx map) {
		map.put("keyword", keyword);
		map.put("page", "" + pageIndex);
		map.put("size", "" + pageSize);
		return ArticleSearcher.search(map);
	}

	public static SearchResult searchArticle(long id, String keyword, int pageSize, int pageIndex, String catalog) {
		Mapx map = new Mapx();
		map.put("catalog", catalog);
		map.put("keyword", keyword);
		map.put("page", "" + pageIndex);
		map.put("size", "" + pageSize);
		return ArticleSearcher.search(map);
	}

	public static SearchResult search(long id, String keyword, int pageSize, int pageIndex, Mapx map) {
		map.put("keyword", keyword);
		map.put("page", "" + pageIndex);
		map.put("size", "" + pageSize);
		return ArticleSearcher.search(map);
	}

	public static String getPageURL(Mapx map, int pageNo) {
		map.put("page", "" + pageNo);
		return ServletUtil.getQueryStringFromMap(map, true);
	}
	
	/**
	 * 获取栏目名称方法
	 * @param ERiskSubType
	 * @return
	 */
	public static String getCatalogName(String ERiskSubType){
		try{
			String CatalogName = "";
			QueryBuilder qb = new QueryBuilder("select Name from zccatalog where ProductType = ?",ERiskSubType);
			DataTable dt = new DataTable();
			dt = qb.executeDataTable();
			if(dt.getRowCount()>0){
				CatalogName = dt.get(0).getString("Name");
			}
			return CatalogName;
		} catch (Exception e){
			logger.error("获取栏目名称方法异常："+e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 获取栏目URL方法
	 * @param ERiskSubType
	 * @return
	 */
	public static String getCatalogUrl(String ERiskSubType){
		try{
			String CatalogURL = "";
			QueryBuilder qb = new QueryBuilder("select URL from zccatalog where ProductType = ?",ERiskSubType);
			DataTable dt = new DataTable();
			dt = qb.executeDataTable();
			if(dt.getRowCount()>0){
				CatalogURL = dt.get(0).getString("URL");
			}
			return CatalogURL;
		} catch (Exception e){
			logger.error("获取栏目URL方法异常："+e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 获取列表页背景色
	 * @param ERiskSubType
	 * @return
	 */
	public static String getListColor(String ERiskSubType) {
		QueryBuilder qb = new QueryBuilder("select b.TextValue from zccatalog a, ZDColumnValue b where a.ProductType = ? and a.Id=b.RelaId and b.ColumnCode='liebiaoyebeijingse'",ERiskSubType);
		return qb.executeString();
	}
	
	/**
	 * 对输入的查询条件进行拼装、排序
	 * @param ERiskSubType
	 * @param SearchID
	 * @return
	 */
	public static String getSearchID(String ERiskSubType,String AddressID){
	try{
		QueryBuilder qb1 = new QueryBuilder("select SearchID from SDSearchAddress where id = ?");
		qb1.add(AddressID);
		String SearchID = (String)qb1.executeOneValue();
		DataTable dt = new DataTable();
		String nRiskSubType = "";
		String newSort = "";
		if(StringUtil.isNotEmpty(ERiskSubType)){
			nRiskSubType = ERiskSubType.substring(0, 1);
		}
		//将当前险种类别的searchCode添加到list中
		ArrayList<String> list=new ArrayList<String>();
		QueryBuilder qb = new QueryBuilder("select SearchCode from FEMSearchConditionInfo where ERiskType = ? and searchlevel = '1' order by -SearchOrder",nRiskSubType);
		dt = qb.executeDataTable();
		for(int j=0;j<dt.getRowCount();j++){
			list.add(dt.get(j).getString("SearchCode"));
		}
		//把SearchID进行拆分，并按照key，value保存
		Map<String, String> map = new HashMap<String, String>();
		String[] sea = SearchID.split("\\|");
		for(int j=0;j<sea.length;j++){
			if(sea[j].startsWith("default_")){
				continue;
			}else{
				//如果sea[j]中是多选，根据第一个值获取searchCode
				if(sea[j].indexOf(",")!=-1){
					String[] mul = sea[j].split(",");
					map.put(Filtrate.getSearchCode(mul[0]), sea[j]);
				}else{
					map.put(Filtrate.getSearchCode(sea[j]), sea[j]);
				}
			}
		}
		Iterator<String> itr = map.keySet().iterator();
		String[] key = new String[map.keySet().size()];
		int n=0;
		while(itr.hasNext()){
			key[n] = itr.next();
			n++;
		}
		String[] sortSea = new String[sea.length];
		//再按照顺序添加到数组中
		for(int k=0;k<list.size();k++){
			Boolean flag = false;
			for(int l=0;l<n;l++){
				if(list.get(k).equals(key[l])){
					sortSea[k] = map.get(key[l]);
					flag = true;
				}
			}
			if(!flag){
				sortSea[k] = "default_"+list.get(k)+"_1";
			}
		}
		//拼装成串
		for(int p=0;p<sortSea.length;p++){
			newSort += sortSea[p];
			if(p!=sortSea.length-1){
				newSort += "|";
			}
		}
		
		return newSort;
	} catch (Exception e){
		logger.error("getSearchID方法发生异常:"+e.getMessage(), e);
		return null;
	}
}	

	public static Map<String,String> getNewlistSearchCondition(String nERiskSubType,
			String tERiskSubType, String hotERiskSubType,Map<String,String> searchValueMap) {
		Map<String,String> map = new HashMap<String,String>();
		String BNCompany = Config.getValue("BNCompany");
		String sql3 = "select ID,ProductType,URL from zccatalog where ProductType like '"
				+ nERiskSubType + "%'";
		QueryBuilder qb = new QueryBuilder(sql3);
		String contextPath = Config.getFrontServerContextPath();
		DataTable dt0 = qb.executeDataTable();
		String tUrl = "";
		String mmERiskSubType = "";
		Map<String, String> catalogUrl = new HashMap<String, String>();
		for (int t = 0; t < dt0.getRowCount(); t++) {
			mmERiskSubType = dt0.get(t).getString("ProductType");
			tUrl = dt0.get(t).getString("URL");
			if (StringUtil.isNotEmpty(mmERiskSubType)) {
				if (StringUtil.isNotEmpty(tUrl)) {
					tUrl = contextPath + "/" + tUrl;
					catalogUrl.put(mmERiskSubType, tUrl);
				} else {
					catalogUrl.put(mmERiskSubType, "");
				}
			}
		}

		// 取得险种说明
		Map<String, String> descMap = new HashMap<String, String>();
		DataTable descDT = new QueryBuilder(
				"select CodeValue,Memo from zdcode where CodeType='ProductTypeDesc' and ParentCode='ProductTypeDesc'")
				.executeDataTable();
		if (descDT != null && descDT.getRowCount() > 0) {
			int rowcount = descDT.getRowCount();
			for (int i = 0; i < rowcount; i++) {
				if (StringUtil.isNotEmpty(descDT.getString(i, 1))) {
					descMap.put(descDT.getString(i, 0), descDT.getString(i, 1));
				}
			}
		}
		StringBuffer tHTML = new StringBuffer();
		FEMSearchConditionInfoSchema SCschema = new FEMSearchConditionInfoSchema();
		FEMSearchConditionInfoSet SCset = new FEMSearchConditionInfoSet();
		if (StringUtil.isNotEmpty(tERiskSubType)) {
			SCset = SCschema
					.query(new QueryBuilder(
							"where SearchLevel='1' and ERiskType=? order by extra3 DESC,-SearchOrder",
							nERiskSubType));

			tHTML.append("<div id=\"radiolist\" class=\"radiolist CsearchConditions\">");
			for (int i = 0; i < SCset.size(); i++) {
				// 拼装不限和全部
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
				if (StringUtil.isEmpty(BNCompany)) {
					SCset1 = SCschema1.query(new QueryBuilder(
							"where UpperId=? order by -SearchOrder", SCschema
									.getId()));

				} else {
					SCset1 = SCschema1
							.query(new QueryBuilder(
									"where UpperId=? and SearchCode <> ? order by -SearchOrder",
									SCschema.getId(), BNCompany));
				}

				if ("Y".equals(SCschema.getIsMultipleChoice())) {
					// 如果本条件为多选在外成多套一个“chklist”
					tHTML.append("<div class=\"chklist\" >");
				}
				if ("Y".equals(SCschema.getExtra3())) {
					tHTML.append("<div class=\"bznx-z\" id=\"CsearchConditions_"
							+ SCschema.getSearchCode() + "\">");
				} else {
					tHTML.append("<div class=\"bznx-no\" id=\"CsearchConditions_"
							+ SCschema.getSearchCode() + "\">");
				}
				tHTML.append("<span class=\"CConditionName\">"
						+ SCschema.getSearchName() + "：</span>");
				tHTML.append("<div class=\"select_nav\"><ul>");
				// IF判断是多选而且是并集时 02—Y为全部
				if ("02".equals(SCschema.getIntersection())
						&& "Y".equals(SCschema.getIsMultipleChoice())) {
					tHTML.append(" <li class=\"jiange\"><input type=\"radio\" id=\""
							+ SCschema.getSearchCode()
							+ "_all\" value=\"default_"
							+ SCschema.getSearchCode()
							+ "_"
							+ SCschema.getSearchLevel()
							+ "\" name=\""
							+ SCschema.getSearchCode());
					tHTML.append("\" style=\"display: none;\"><label class=\"hRadio\" >全部</label></li>");
				}
				// 02以外-Y为不限
				else if ("Y".equals(SCschema.getIsMultipleChoice())
						&& !"02".equals(SCschema.getIntersection())) {
					tHTML.append("<li  class=\"jiange\"><input type=\"radio\" value=\"default_"
							+ SCschema.getSearchCode()
							+ "_"
							+ SCschema.getSearchLevel()
							+ "\" name=\""
							+ SCschema.getSearchCode());
					tHTML.append("\" id=\""
							+ SCschema.getSearchCode()
							+ "_all\"  style=\"display: none;\"><label class=\"hRadio\" >不限</label></li>");
				} else {
					tHTML.append("<li  class=\"jiange\"><input type=\"radio\" value=\"default_"
							+ SCschema.getSearchCode()
							+ "_"
							+ SCschema.getSearchLevel()
							+ "\" name=\""
							+ SCschema.getSearchCode());

					if ("".equals(SCschema.getSubColumnCategory())
							|| SCschema.getSubColumnCategory() == null) {
						tHTML.append("\" style=\"display: none;\"><label class=\"hRadio\" >不限</label></li>");
					} else {
						if (StringUtil.isNotEmpty(catalogUrl.get(SCschema
								.getSubColumnCategory()))) {
							tHTML.append("\" style=\"display: none;\"><a class=\"hRadio\" href=\" "
									+ catalogUrl.get(SCschema
											.getSubColumnCategory())
									+ "\">不限</a>");
						} else {
							tHTML.append("\" style=\"display: none;\"><label class=\"hRadio\" >不限</label></li>");
						}
					}

				}
				// 拼装除不限和全部以外的子条件
				for (int j = 0; j < SCset1.size(); j++) {
					int m = j + 1;
					SCschema1 = SCset1.get(j);
					if(tERiskSubType.equals(SCschema1.getSubColumnCategory())){
						tHTML.append("<input id=\"hdn_"+SCschema.getSearchCode()+"\" type=\"hidden\" value=\""+SCschema1.getId()+"\" name=\"hdn_"+SCschema.getSearchCode()+"\" />"); 
						flag = true;
					}
					if ("Y".equals(SCschema.getIsMultipleChoice()))
					// IF判断是多选条件，多选条件单独样式
					{
						tHTML.append("<li><input type=\"checkbox\" name=\""
								+ SCschema.getSearchCode()
								+ "\" value=\""
								+ SCschema1.getId()
								+ "\" style=\"display: none;\"><label class=\"checkbox\" >"
								+ SCschema1.getSearchName());
						tHTML.append("</label></li>");
					} else { // 境内、外旅游有子菜单，需要单独拼装样式
						if ("02".equals(SCschema.getIntersection())
								&& "N".equals(SCschema.getIsMultipleChoice())) {
							tHTML.append("<li><input type=\"radio\" id=\"first"
									+ m + "\" value=\"" + SCschema1.getId()
									+ "\" name=\"" + SCschema.getSearchCode());
							tHTML.append("\" style=\"display: none;\"><label class=\"hRadio\" >"
									+ SCschema1.getSearchName()
									+ "</label></li>");
						} else {
							tHTML.append("<li><input type=\"radio\"  value=\""
									+ SCschema1.getId() + "\" name=\""
									+ SCschema.getSearchCode());
							tHTML.append("\" style=\"display: none;\">");
							if ("".equals(SCschema1.getSubColumnCategory())
									|| SCschema1.getSubColumnCategory() == null) {
								tHTML.append("<label class=\"hRadio\" >"
										+ SCschema1.getSearchName()
										+ "</label></li>");
							} else {

								String productTypeDesc = "";
								if (StringUtil.isNotEmpty(descMap.get(SCschema1
										.getSubColumnCategory()))) {
									productTypeDesc = "<em class=\"m-list-tip2\"><i class=\"tagshow\" style=\"display:none\">"
											+ descMap.get(SCschema1
													.getSubColumnCategory())
											+ "</i></em>";
								}
								if (StringUtil.isNotEmpty(catalogUrl.get(SCschema1
														.getSubColumnCategory()))) {
									tHTML.append("<a class=\"hRadio\" href=\" "
											+ catalogUrl.get(SCschema1
													.getSubColumnCategory())
											+ "\">"
											+ SCschema1.getSearchName()
											+ productTypeDesc + "</a>");
								} else {
									tHTML.append(" <label class=\"hRadio\" > "
											+ SCschema1.getSearchName()
											+ productTypeDesc + "</label></li>");
								}

							}
						}
						if (StringUtil.isNotEmpty(SCschema1.getSubNodeNum())
								&& Integer.parseInt(SCschema1.getSubNodeNum()) > 0) {
							SCset3.add(SCschema1);
							SCset2.add(SCschema2.query(new QueryBuilder(
									"where Upperid=? order by -SearchOrder",
									SCschema1.getId())));
							subNode[k] = SCset2.size();
							k++;
						}

					}
					if (j != 0 && (j + 1) % 4 == 0) {
						tHTML.append(" <li class=\"jiange\"></li>");
					}
				}
				tHTML.append("</ul></div>");
				tHTML.append("</div>");
				if ("Y".equals(SCschema.getIsMultipleChoice())) {
					tHTML.append("</div>");
				}
				// 拼装境内境外旅游的子菜单
				if (SCset2.size() > 0) {
					int m = 0;
					tHTML.append("<div class=\"jn-k\" style=\"display: none;\" id=\"CsearchConditions_"
							+ SCschema.getSearchCode() + "_\">");

					// 境外旅游险
					if (hotERiskSubType.equals("A02")) {
						tHTML.append("<div id=\"CsearchConditions_"
								+ SCschema.getSearchCode()
								+ "_jn-sj\" class=\"jn-sj\" style=\"margin: 0px 0px -4px 183px;\"><img alt=\"\" src=\""
								+ Config.getFrontServerContextPath()
								+ "/images/sj.gif\"></div><div class=\"jn-qbbg\" >");

					} else {
						tHTML.append("<div id=\"CsearchConditions_"
								+ SCschema.getSearchCode()
								+ "_jn-sj\" class=\"jn-sj\" style=\"margin: 0px 0px -4px 60px;\"><img alt=\"\" src=\""
								+ Config.getFrontServerContextPath()
								+ "/images/sj.gif\"></div><div class=\"jn-qbbg\" >");
					}

					for (int n = 0; n < SCset3.size(); n++) {
						tHTML.append("<ul style=\"display: block;width: 430px;\" id=\"CsearchConditions_"
								+ SCschema.getSearchCode()
								+ "_"
								+ SCset3.get(n).getId() + "\">");
						tHTML.append("<li style=\"padding-right:20px\"><input type=\"radio\" id=\""
								+ SCschema.getSearchCode()
								+ "_sub_"
								+ ""
								+ SCset3.get(n).getId()
								+ "\" value=\""
								+ SCset3.get(n).getId()
								+ "\" name=\""
								+ SCschema.getSearchCode()
								+ "_sub\" style=\"display: none;\"><label class=\"hRadio\" >全部</label></li>");
						for (; m < subNode[n]; m++) {
							SCschema2 = SCset2.get(m);
							tHTML.append("<li style=\"padding-right:20px\"><input type=\"radio\" value=\""
									+ SCschema2.getId()
									+ "\" name=\""
									+ SCschema.getSearchCode()
									+ "_sub\" style=\"display: none;\"><label class=\"hRadio\" >"
									+ SCschema2.getSearchName()
									+ "</label></li>");
						}
						tHTML.append("</ul>");
					}
					tHTML.append("</div></div>");
				}
				if(!flag){
					if (searchValueMap.containsKey(SCschema.getSearchCode())) {
						tHTML.append("<input id=\"hdn_"+SCschema.getSearchCode()+"\" type=\"hidden\" value=\""+searchValueMap.get(SCschema.getSearchCode())+"\" name=\"hdn_"+SCschema.getSearchCode()+"\" />"); 
						
					} else {
						tHTML.append("<input id=\"hdn_"+SCschema.getSearchCode()+"\" type=\"hidden\" value=\"default_"+SCschema.getSearchCode()+"_1\" name=\"hdn_"+SCschema.getSearchCode()+"\" />"); 
					}
				}
			}

			tHTML.append("</div>");
		}
		
		map.put("SearchCondition", tHTML.toString());
		map.put("hotERiskSubType", hotERiskSubType);
		return map;
	}
	public static Map<String,String> getSearchCondition(String nERiskSubType,
			String tERiskSubType, String hotERiskSubType, String[] adds) {
		Map<String,String> map = new HashMap<String,String>();
		String BNCompany = Config.getValue("BNCompany");
		String sql3 = "select ID,ProductType,URL from zccatalog where ProductType like '"
				+ nERiskSubType + "%'";
		QueryBuilder qb = new QueryBuilder(sql3);
		String contextPath = Config.getFrontServerContextPath();
		DataTable dt0 = qb.executeDataTable();
		String tUrl = "";
		String mmERiskSubType = "";
		Map<String, String> catalogUrl = new HashMap<String, String>();
		for (int t = 0; t < dt0.getRowCount(); t++) {
			mmERiskSubType = dt0.get(t).getString("ProductType");
			tUrl = dt0.get(t).getString("URL");
			if (StringUtil.isNotEmpty(mmERiskSubType)) {
				if (StringUtil.isNotEmpty(tUrl)) {
					tUrl = contextPath + "/" + tUrl;
					catalogUrl.put(mmERiskSubType, tUrl);
				} else {
					catalogUrl.put(mmERiskSubType, "");
				}
			}
		}

		// 取得险种说明
		Map<String, String> descMap = new HashMap<String, String>();
		DataTable descDT = new QueryBuilder(
				"select CodeValue,Memo from zdcode where CodeType='ProductTypeDesc' and ParentCode='ProductTypeDesc'")
				.executeDataTable();
		if (descDT != null && descDT.getRowCount() > 0) {
			int rowcount = descDT.getRowCount();
			for (int i = 0; i < rowcount; i++) {
				if (StringUtil.isNotEmpty(descDT.getString(i, 1))) {
					descMap.put(descDT.getString(i, 0), descDT.getString(i, 1));
				}
			}
		}
		StringBuffer tHTML = new StringBuffer();
		FEMSearchConditionInfoSchema SCschema = new FEMSearchConditionInfoSchema();
		FEMSearchConditionInfoSet SCset = new FEMSearchConditionInfoSet();
		if (StringUtil.isNotEmpty(tERiskSubType)) {
			SCset = SCschema
					.query(new QueryBuilder(
							"where SearchLevel='1' and ERiskType=? order by extra3 DESC,-SearchOrder",
							nERiskSubType));

			tHTML.append("<div id=\"radiolist\" class=\"radiolist CsearchConditions\">");
			for (int i = 0; i < SCset.size(); i++) {
				// 拼装不限和全部
				SCschema = SCset.get(i);
				FEMSearchConditionInfoSchema SCschema1 = new FEMSearchConditionInfoSchema();
				FEMSearchConditionInfoSet SCset1 = new FEMSearchConditionInfoSet();
				FEMSearchConditionInfoSchema SCschema2 = new FEMSearchConditionInfoSchema();
				FEMSearchConditionInfoSet SCset2 = new FEMSearchConditionInfoSet();
				FEMSearchConditionInfoSet SCset3 = new FEMSearchConditionInfoSet();
				int[] subNode = new int[10];
				int k = 0;
				// 查询出一级查询条件对应的子条件
				if (StringUtil.isEmpty(BNCompany)) {
					SCset1 = SCschema1.query(new QueryBuilder(
							"where UpperId=? order by -SearchOrder", SCschema
									.getId()));

				} else {
					SCset1 = SCschema1
							.query(new QueryBuilder(
									"where UpperId=? and SearchCode <> ? order by -SearchOrder",
									SCschema.getId(), BNCompany));
				}

				if ("Y".equals(SCschema.getIsMultipleChoice())) {
					// 如果本条件为多选在外成多套一个“chklist”
					tHTML.append("<div class=\"chklist\" >");
				}
				if ("Y".equals(SCschema.getExtra3())) {
					tHTML.append("<div class=\"bznx-z\" id=\"CsearchConditions_"
							+ SCschema.getSearchCode() + "\">");
				} else {
					tHTML.append("<div class=\"bznx-no\" id=\"CsearchConditions_"
							+ SCschema.getSearchCode() + "\">");
				}
				tHTML.append("<span class=\"CConditionName\">"
						+ SCschema.getSearchName() + "：</span>");
				tHTML.append("<div class=\"select_nav\"><ul>");
				// IF判断是多选而且是并集时 02—Y为全部
				if ("02".equals(SCschema.getIntersection())
						&& "Y".equals(SCschema.getIsMultipleChoice())) {
					tHTML.append(" <li class=\"jiange\"><input type=\"radio\" id=\""
							+ SCschema.getSearchCode()
							+ "_all\" value=\"default_"
							+ SCschema.getSearchCode()
							+ "_"
							+ SCschema.getSearchLevel()
							+ "\" name=\""
							+ SCschema.getSearchCode());
					tHTML.append("\" style=\"display: none;\"><label class=\"hRadio\" >全部</label></li>");
				}
				// 02以外-Y为不限
				else if ("Y".equals(SCschema.getIsMultipleChoice())
						&& !"02".equals(SCschema.getIntersection())) {
					tHTML.append("<li  class=\"jiange\"><input type=\"radio\" value=\"default_"
							+ SCschema.getSearchCode()
							+ "_"
							+ SCschema.getSearchLevel()
							+ "\" name=\""
							+ SCschema.getSearchCode());
					tHTML.append("\" id=\""
							+ SCschema.getSearchCode()
							+ "_all\"  style=\"display: none;\"><label class=\"hRadio\" >不限</label></li>");
				} else {
					tHTML.append("<li  class=\"jiange\"><input type=\"radio\" value=\"default_"
							+ SCschema.getSearchCode()
							+ "_"
							+ SCschema.getSearchLevel()
							+ "\" name=\""
							+ SCschema.getSearchCode());

					if ("".equals(SCschema.getSubColumnCategory())
							|| SCschema.getSubColumnCategory() == null) {
						tHTML.append("\" style=\"display: none;\"><label class=\"hRadio\" >不限</label></li>");
					} else {
						if (StringUtil.isNotEmpty(catalogUrl.get(SCschema
								.getSubColumnCategory()))) {
							tHTML.append("\" style=\"display: none;\"><a class=\"hRadio\" href=\" "
									+ catalogUrl.get(SCschema
											.getSubColumnCategory())
									+ "\">不限</a>");
						} else {
							tHTML.append("\" style=\"display: none;\"><label class=\"hRadio\" >不限</label></li>");
						}
					}

				}
				// 拼装除不限和全部以外的子条件
				for (int j = 0; j < SCset1.size(); j++) {
					int m = j + 1;
					SCschema1 = SCset1.get(j);
					if ("Y".equals(SCschema.getIsMultipleChoice()))
					// IF判断是多选条件，多选条件单独样式
					{
						tHTML.append("<li><input type=\"checkbox\" name=\""
								+ SCschema.getSearchCode()
								+ "\" value=\""
								+ SCschema1.getId()
								+ "\" style=\"display: none;\"><label class=\"checkbox\" >"
								+ SCschema1.getSearchName());
						tHTML.append("</label></li>");
					} else { // 境内、外旅游有子菜单，需要单独拼装样式
						if ("02".equals(SCschema.getIntersection())
								&& "N".equals(SCschema.getIsMultipleChoice())) {
							tHTML.append("<li><input type=\"radio\" id=\"first"
									+ m + "\" value=\"" + SCschema1.getId()
									+ "\" name=\"" + SCschema.getSearchCode());
							tHTML.append("\" style=\"display: none;\"><label class=\"hRadio\" >"
									+ SCschema1.getSearchName()
									+ "</label></li>");
						} else {
							tHTML.append("<li><input type=\"radio\"  value=\""
									+ SCschema1.getId() + "\" name=\""
									+ SCschema.getSearchCode());
							tHTML.append("\" style=\"display: none;\">");
							if ("".equals(SCschema1.getSubColumnCategory())
									|| SCschema1.getSubColumnCategory() == null) {
								tHTML.append("<label class=\"hRadio\" >"
										+ SCschema1.getSearchName()
										+ "</label></li>");
							} else {

								if (StringUtil.isNotEmpty(SCschema1.getId())) {
									String addsValue;
									for (int kk = 0; kk < adds.length; kk++) {
										if (adds[kk].indexOf(":") >= 0) {
											if (SCschema
													.getSearchCode()
													.equals(adds[kk].split(":")[0])) {
												addsValue = adds[kk].split(":")[1];
												String[] vals = addsValue
														.split(",");
												for (int v = 0; v < vals.length; v++) {
													if (SCschema1.getId()
															.equals(vals[v])) {
														hotERiskSubType = SCschema1
																.getSubColumnCategory();
													}
												}
											}
										}
									}
								}
								String productTypeDesc = "";
								if (StringUtil.isNotEmpty(descMap.get(SCschema1
										.getSubColumnCategory()))) {
									productTypeDesc = "<em class=\"m-list-tip2\"><i class=\"tagshow\" style=\"display:none\">"
											+ descMap.get(SCschema1
													.getSubColumnCategory())
											+ "</i></em>";
								}
								if (!hotERiskSubType.equals(SCschema1
										.getSubColumnCategory())
										&& StringUtil
												.isNotEmpty(catalogUrl.get(SCschema1
														.getSubColumnCategory()))) {
									tHTML.append("<a class=\"hRadio\" href=\" "
											+ catalogUrl.get(SCschema1
													.getSubColumnCategory())
											+ "\">"
											+ SCschema1.getSearchName()
											+ productTypeDesc + "</a>");
								} else {
									tHTML.append(" <label class=\"hRadio\" > "
											+ SCschema1.getSearchName()
											+ productTypeDesc + "</label></li>");
								}

							}
						}
						if (StringUtil.isNotEmpty(SCschema1.getSubNodeNum())
								&& Integer.parseInt(SCschema1.getSubNodeNum()) > 0) {
							SCset3.add(SCschema1);
							SCset2.add(SCschema2.query(new QueryBuilder(
									"where Upperid=? order by -SearchOrder",
									SCschema1.getId())));
							subNode[k] = SCset2.size();
							k++;
						}

					}
					if (j != 0 && (j + 1) % 4 == 0) {
						tHTML.append(" <li class=\"jiange\"></li>");
					}
				}
				tHTML.append("</ul></div>");
				tHTML.append("</div>");
				if ("Y".equals(SCschema.getIsMultipleChoice())) {
					tHTML.append("</div>");
				}
				// 拼装境内境外旅游的子菜单
				if (SCset2.size() > 0) {
					int m = 0;
					tHTML.append("<div class=\"jn-k\" style=\"display: none;\" id=\"CsearchConditions_"
							+ SCschema.getSearchCode() + "_\">");

					// 境外旅游险
					if (hotERiskSubType.equals("A02")) {
						tHTML.append("<div id=\"CsearchConditions_"
								+ SCschema.getSearchCode()
								+ "_jn-sj\" class=\"jn-sj\" style=\"margin: 0px 0px -4px 183px;\"><img alt=\"\" src=\""
								+ Config.getFrontServerContextPath()
								+ "/images/sj.gif\"></div><div class=\"jn-qbbg\" >");

					} else {
						tHTML.append("<div id=\"CsearchConditions_"
								+ SCschema.getSearchCode()
								+ "_jn-sj\" class=\"jn-sj\" style=\"margin: 0px 0px -4px 60px;\"><img alt=\"\" src=\""
								+ Config.getFrontServerContextPath()
								+ "/images/sj.gif\"></div><div class=\"jn-qbbg\" >");
					}

					for (int n = 0; n < SCset3.size(); n++) {
						tHTML.append("<ul style=\"display: block;width: 430px;\" id=\"CsearchConditions_"
								+ SCschema.getSearchCode()
								+ "_"
								+ SCset3.get(n).getId() + "\">");
						tHTML.append("<li style=\"padding-right:20px\"><input type=\"radio\" id=\""
								+ SCschema.getSearchCode()
								+ "_sub_"
								+ ""
								+ SCset3.get(n).getId()
								+ "\" value=\""
								+ SCset3.get(n).getId()
								+ "\" name=\""
								+ SCschema.getSearchCode()
								+ "_sub\" style=\"display: none;\"><label class=\"hRadio\" >全部</label></li>");
						for (; m < subNode[n]; m++) {
							SCschema2 = SCset2.get(m);
							tHTML.append("<li style=\"padding-right:20px\"><input type=\"radio\" value=\""
									+ SCschema2.getId()
									+ "\" name=\""
									+ SCschema.getSearchCode()
									+ "_sub\" style=\"display: none;\"><label class=\"hRadio\" >"
									+ SCschema2.getSearchName()
									+ "</label></li>");
						}
						tHTML.append("</ul>");
					}
					tHTML.append("</div></div>");
				}

			}
			for (int i = 0; i < adds.length; i++) {
				if (adds[i].indexOf(":") == -1) {
					tHTML.append("<input id=\"hdn_" + adds[i]
							+ "\" type=\"hidden\" value=\"default_" + adds[i]
							+ "_1\" name=\"hdn_" + adds[i] + "\" />");
				} else {
					String[] nadds = adds[i].split(":");
					tHTML.append("<input id=\"hdn_" + nadds[0]
							+ "\" type=\"hidden\" value=\"" + nadds[1]
							+ "\" name=\"hdn_" + nadds[0] + "\" />");
				}
			}

			tHTML.append("</div>");
		}
		map.put("SearchCondition", tHTML.toString());
		map.put("hotERiskSubType", hotERiskSubType);
		return map;
	}
	
	/**
	 * 获取推荐产品
	 * @param ERiskSubType
	 * @return
	 */
	public static DataTable getRecommendProduct(String ERiskSubType){
		try{
			DataTable dt = new DataTable();
			QueryBuilder qb = new QueryBuilder("select innercode from zccatalog where producttype = ?",ERiskSubType);
			String innercode = (String)qb.executeOneValue();
			qb = new QueryBuilder("select a.ID,a.title,a.SITEID,a.URL, b.SupplierCode2, b.InitPrem,  b.AdaptPeopleInfo, b.SalesVolume, b.BasePremV3, b.DutyHTML,b.LogoLink,b.ProductID,b.DutyHTML2,b.ProductName,b.FEMRiskBrightSpotNew, " +
			        " b.RecomDutyV3,b.AdaptPeopleInfoV3,'' as RecommendImg, '' as RecommendInfo "+
					" from zcarticle a left join sdsearchrelaproduct b on ( b.Productid=a.prop4 ) where a.cataloginnercode = ? and a.attribute COLLATE utf8_bin like '%newRecommend%' and a.status = '30'  order by a.topflag,a.orderflag desc,a.publishdate desc, a.id desc limit 3",innercode);
			dt = qb.executeDataTable();
			dt = setURL(dt);
			// 取得推荐图标和推荐文案
			if (dt != null && dt.getRowCount() > 0) {
				int rowCount = dt.getRowCount();
				String sql = "select TextValue from zdcolumnvalue where ColumnCode = ? and RelaID = ?";
				for (int i = 0; i < rowCount; i++) {
					qb = new QueryBuilder(sql,"RecommendImg", dt.get(i, 0));
					String imgInfo = qb.executeString();
					if(StringUtil.isNotEmpty(imgInfo) && imgInfo.indexOf("nopicture.jpg")==-1){
						dt.set(i, "RecommendImg", imgInfo);
					}else{
						dt.set(i, "RecommendImg", "");
					}
					qb = new QueryBuilder(sql,"RecommendInfo", dt.get(i, 0));
					dt.set(i, "RecommendInfo", qb.executeString());
				}
			}
			//全站活动价格
			//dt = setActivityPrice(dt);
			return dt;
			} catch (Exception e) {
				logger.error("获取推荐产品方法中发生异常："+e.getMessage(), e);
				return null;
			}
	}
	/**
	 * 截取产品标题方法
	 * @param dt
	 * @return
	 */
	public static DataTable setTitle(DataTable dt){
		for(int k=0;k<dt.getRowCount();k++){
			String title = dt.getString(k, "title");
			if(title.length()>16){
				title = title.substring(0, 16);
			}
			dt.set(k, "title", title);
		}
		return dt;
	}
	
	/**
	 * 设置标题和产品链接方法
	 * @param dt
	 * @return
	 */
	public static DataTable setURL(DataTable dt){
		for(int k=0;k<dt.getRowCount();k++){
			String url = dt.getString(k, "URL");
			if (url.indexOf("://") < 0) {
				String siteUrl = SiteUtil.getURL(dt.getString(k,"SITEID"));
				if (!siteUrl.endsWith("/")) {
					siteUrl = siteUrl + "/";
				}
				if (url.startsWith("/")) {
					url = url.substring(1);
				}
				url = siteUrl + url;
			}
			dt.set(k, "URL", url);
		}
		return dt;
	}
	
	 
	/**
	 * 设置活动价格.
	 * @param dt
	 * @return
	 */
	/*public static DataTable setActivityPrice(DataTable dt){
		dt.insertColumn("newInitPrem");
		for(int i = 0; i < dt.getRowCount(); i++){
			String initPrem = dt.getString(i, "InitPrem");
			String activityPrice = ActivityCalculate.ProductCalculate(dt.getString(i,"ProductID"), initPrem, "wj");
			dt.set(i, "newInitPrem", "￥"+activityPrice);
			}
		return dt;
	}*/
	
	/**
	 * 获取保险知识文章
	 * @return
	 */
	public static DataTable getInsKnowledge(){
		try{
			DataTable dt = new DataTable();
			QueryBuilder qb = new QueryBuilder("select title,url from zcarticle where type='1' and status = '30' and cataloginnercode like '002306%' order by rand() limit 5");
			dt = qb.executeDataTable();
			dt = setURL(dt);
			dt = setTitle(dt);
			return dt;
		} catch (Exception e){
			logger.error("获取保险知识文章方法中发生异常："+e.getMessage(), e);
			return null;
		}
	}
	/**
	 * 获取保险问答文章
	 * @return
	 */
	public static DataTable getInsQuestion(){
		try{
			DataTable dt = new DataTable();
			QueryBuilder qb = new QueryBuilder("select title,url from zcarticle where type='1' and status = '30' and cataloginnercode like '002308%' order by rand() limit 5");
			dt = qb.executeDataTable();
			dt = setURL(dt);
			dt = setTitle(dt);
			return dt;
		} catch (Exception e){
			logger.error("获取保险问答方法中发生异常："+e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 根据AddressID获取险种类型
	 * @param AddressID
	 * @return
	 */
	public static String getERiskSubType(String AddressID){
		try{
			String tERiskSubType = "";
			QueryBuilder qb = new QueryBuilder("select ERiskType from SDSearchAddress where ID = ?",AddressID);
			tERiskSubType = (String)qb.executeOneValue();
			return tERiskSubType;
		} catch (Exception e){
			logger.error("获取险种类型方法中发生异常："+e.getMessage(), e);
			return null;
		}
	}
	/**
	 * 根据SearchID 得到相关查询产品
	 * @param SearchID
	 * @return
	 */
	public static DataTable getSearchProduct(String SearchID,String ERiskSubType){
		try{
			DataTable dt1 = new DataTable();
			String condtion1 = "";
			String[] str3 = SearchID.split("\\|");
			for(int j=0;j<str3.length;j++){
				if(str3[j].indexOf(",")!=-1){
					String[] str4 = str3[j].split(",");
					for(int k=0;k<str4.length;k++){
						if("N".equals(Filtrate.ValueCheck(str4[k])) && "01".equals(Filtrate.ValueCheck1(str4[k])) ){
							str3[j] = str4[k+1];
							break;
						}
					}
				}
			}
			for(int j=0;j<str3.length;j++){
				condtion1 += str3[j];
				if(j!=str3.length-1){
					condtion1 += "|";
				}
			}
			SearchID = condtion1;
			String[] conditionsplit = SearchID.split("\\|");
//			String sql0 = "select  a.productID from sdproduct a where a.productType = '" + ERiskSubType + "'";
			String sql0 = "select  a.productID from sdproduct a where 1=1 ";
			boolean balanceFlag=false;
			for (int j = 0; j < conditionsplit.length; j++) {
				if ((conditionsplit[j]).startsWith("default_")) {
					continue;
				} else {
					balanceFlag=true;
					if(conditionsplit[j].indexOf(",")!=0){
						String[] conditionsplit2 = conditionsplit[j].split(",");
						for (int k = 0; k < conditionsplit2.length; k++) {
							if("Y".equals(Filtrate.ValueCheck(conditionsplit2[k])) && "02".equals(Filtrate.ValueCheck1(conditionsplit2[k]))){
								sql0 += " and exists (select 1 from FEMProductRelaCondition where searchcode in (" + conditionsplit[j] + ") and riskcode = a.productID )" ;
								break;
							}else{
								sql0 += " and exists (select 1 from FEMProductRelaCondition where searchcode ='" + conditionsplit2[k] + "' and riskcode = a.productID )" ;
							}
						}
					} else {
						sql0 += " and exists (select 1 from FEMProductRelaCondition where searchcode='" + conditionsplit[j] + "' and riskcode = a.productID )" ;
					}
				}
			}
			if(!balanceFlag){
				sql0+="and a.productType = '" + ERiskSubType + "'";
			}
			QueryBuilder qb0 = new QueryBuilder(sql0);
			DataTable dt0 = qb0.executeDataTable();
			if(dt0.getRowCount()>0){
				String productIDs = "";
				for(int i=0;i<dt0.getRowCount();i++){
					productIDs +=dt0.get(i).getString("productID");
					if(i!=dt0.getRowCount()-1){
						productIDs += ",";
					}
				}
				String catalogidSql="select id from zccatalog where productType=?";
				DataTable catalogDt = new QueryBuilder(catalogidSql,ERiskSubType+"00").executeDataTable();
				String catalogId = "";
				if(catalogDt.getRowCount()>0){
					catalogId = catalogDt.getString(0, 0);
				}
				StringBuffer sbSerch = new StringBuffer();
				String sql = "select * from SDSearchRelaProduct where productId in ("+productIDs+") ";
				sbSerch.append("select distinct e.*,c.ProductType,f.ComplateDate from zcarticle a,sdsearchrelaproduct e,sdproduct c left join fmrisk f on c.productID = f.RiskCode ");
				sbSerch.append("where a.catalogid='"+catalogId+"' and a.status='30' AND (a.id = e.prop1 or e.Prop1=a.ReferSourceID) and c.productID=e.productID and e.productID in ("+productIDs+")  ");
				sbSerch.append(" order by a.topflag desc ,a.orderflag desc ");
				QueryBuilder qb1 = new QueryBuilder(sbSerch.toString());
				dt1 = qb1.executeDataTable();
			}
			
			return dt1;
		} catch (Exception e){
			logger.error("得到查询产品方法异常："+e.getMessage(), e);
			return null;
		}
	}
	/**
	 * 根据年龄、保险区间、旅游地点、特殊保障、保障金额 得到相关查询产品
	 * @param SearchID
	 * @return
	 */
	public static DataTable getSearchProduct(String productType, String ageSearchCode,String period,String travelSearchCode,String featuresSearchCode,String premSearchCode){

		String catalogidSql = "select id from zccatalog where productType=?";
		DataTable catalogDt = new QueryBuilder(catalogidSql, productType).executeDataTable();
		boolean balanceFlag = false;
		String catalogId = "";
		if (catalogDt.getRowCount() > 0) {
			catalogId = catalogDt.getString(0, 0);
		}
		
		StringBuffer sbSerch = new StringBuffer();
		sbSerch.append(" SELECT DISTINCT e.*,c.ProductType FROM zcarticle a,sdproduct c,sdsearchrelaproduct e ");
		sbSerch.append(" WHERE a.catalogid='" + catalogId + "' and a.status='30' AND (a.id = e.prop1 or e.Prop1=a.ReferSourceID) and c.productID=e.productID ");
		if(StringUtil.isNotEmpty(period) && !"default".equals(period)){
			if(period.indexOf("-")!=-1){
				period = period.split("-")[1];
			}
			
			sbSerch.append(" AND ("+period+" <= func_splitString(c.remark2,'-',1) or "+period+" <=func_splitString(c.remark2,'-',2)) ");//出行天数
		}
		if(StringUtil.isNotEmpty(travelSearchCode)){
			balanceFlag = true;
			sbSerch.append(" AND EXISTS (SELECT 1 FROM femproductrelacondition f WHERE f.searchcode IN "+travelSearchCode+"  AND c.productid = f.riskcode) ");//旅游地点
		}
        if(StringUtil.isNotEmpty(ageSearchCode)){
        	balanceFlag = true;
			sbSerch.append(" AND EXISTS (SELECT 1 FROM femproductrelacondition f WHERE f.searchcode IN "+ageSearchCode+" AND c.productid = f.riskcode) ");//年龄
		}
        if(StringUtil.isNotEmpty(featuresSearchCode)){
        	balanceFlag = true;
			sbSerch.append(" AND EXISTS (SELECT 1 FROM femproductrelacondition f WHERE f.searchcode IN "+featuresSearchCode+" AND c.productid = f.riskcode) ");//年龄
		}
        if(StringUtil.isNotEmpty(premSearchCode)){
        	balanceFlag = true;
			sbSerch.append(" AND EXISTS (SELECT 1 FROM femproductrelacondition f WHERE f.searchcode IN "+premSearchCode+" AND c.productid = f.riskcode) ");//年龄
		}
        String proType = productType.substring(0, 1);
        if (!balanceFlag) {
			sbSerch.append(" and c.productType = '" + proType + "'  ");
		}
		sbSerch.append(" order by a.topflag desc ,a.orderflag desc");
		QueryBuilder qb0 = new QueryBuilder(sbSerch.toString());
		DataTable dt0 = qb0.executeDataTable();
		
		return dt0;
	}
	/**
	 * 根据年龄、保险区间、履行地点 得到相关查询产品
	 * @param SearchID
	 * @return
	 */
	public static DataTable getSearchProduct(String[] ages,String period,String travel){

		String tSearchCode = SearchAPI.getSearchCodeByTravelAddress(travel);
		String ageSearchCode = SearchAPI.getSearchCodeByAge(ages);
		StringBuffer sbSerch = new StringBuffer();
		sbSerch.append(" SELECT DISTINCT e.*,c.ProductType FROM sdproduct c,sdsearchrelaproduct e ");
		sbSerch.append(" WHERE c.productID=e.productID ");
		sbSerch.append(" AND c.IsPublish='Y' ");
		if(StringUtil.isNotEmpty(period) && !"default".equals(period)){
			if(period.indexOf("-")!=-1){
				period = period.split("-")[1];
			}
			sbSerch.append(" AND ("+period+" <= func_splitString(c.remark2,'-',1) or "+period+" <=func_splitString(c.remark2,'-',2)) ");//出行天数
		}
		if(StringUtil.isNotEmpty(tSearchCode)){
			sbSerch.append(" AND EXISTS (SELECT 1 FROM femproductrelacondition f WHERE f.searchcode IN "+tSearchCode+"  AND c.productid = f.riskcode) ");//旅游地点
		}
        if(StringUtil.isNotEmpty(ageSearchCode)){
			sbSerch.append(" AND EXISTS (SELECT 1 FROM femproductrelacondition f WHERE f.searchcode IN "+ageSearchCode+" AND c.productid = f.riskcode) ");//年龄
		}
		sbSerch.append(" order by c.feerate desc");
		QueryBuilder qb0 = new QueryBuilder(sbSerch.toString());
		DataTable dt0 = qb0.executeDataTable();
		
		return dt0;
	}
	/**
	 * 根据年龄、保险区间、履行地点 得到相关查询产品
	 * @param SearchID
	 * @return
	 */
	public static DataTable getQuickSearchProduct(String param){

		String[] tParam = param.split(",");
		String ages = tParam[0];
		String travel = tParam[1];
		String period = tParam[2];
		String features = tParam[3];
		String prem = tParam[4];
		String[] agesarr = null;
		if("default".equals(ages)){
			ages = "";
			agesarr = new String[]{ages};
		}
		String tSearchCode = SearchAPI.getSearchCodeByTravelAddress(travel);
		if("('')".equals(tSearchCode)){
			tSearchCode = "";
		}
		String ageSearchCode = SearchAPI.getSearchCodeByAge(agesarr);
		StringBuffer sbSerch = new StringBuffer();
		sbSerch.append(" SELECT DISTINCT e.ProductId,e.ProductActive,e.URL,e.ProductName,e.LogoLink,e.AdaptPeopleInfoListV3,e.DutyHTMLV3,e.InitPrem,e.BasePremV3,c.ProductType FROM sdproduct c,sdsearchrelaproduct e ");
		sbSerch.append(" WHERE c.productID=e.productID ");
		sbSerch.append(" AND c.IsPublish='Y' ");
		if(StringUtil.isNotEmpty(period) && "default".equals(period)){
			if(period.indexOf("-")!=-1){
				period = period.split("-")[1];
			}
			sbSerch.append(" AND ("+period+" <= func_splitString(c.remark2,'-',1) or "+period+" <=func_splitString(c.remark2,'-',2)) ");//出行天数
		}
		if(StringUtil.isNotEmpty(tSearchCode)){
			sbSerch.append(" AND EXISTS (SELECT 1 FROM femproductrelacondition f WHERE f.searchcode IN "+tSearchCode+"  AND c.productid = f.riskcode) ");//旅游地点
		}
        if(StringUtil.isNotEmpty(ageSearchCode)){
			sbSerch.append(" AND EXISTS (SELECT 1 FROM femproductrelacondition f WHERE f.searchcode IN "+ageSearchCode+" AND c.productid = f.riskcode) ");//年龄
		}
		if(!"default".equals(features)){
			sbSerch.append(" AND EXISTS (SELECT 1 FROM femproductrelacondition f WHERE f.searchcode in ('"+features+"') AND c.productid = f.riskcode) ");//保障疾患
		}
		if(!"default".equals(prem)){
			sbSerch.append(" AND EXISTS (SELECT 1 FROM femproductrelacondition f WHERE f.searchcode in ('"+prem+"') AND c.productid = f.riskcode )");//保额
		}
		sbSerch.append(" order by c.feerate desc limit 4");
		QueryBuilder qb0 = new QueryBuilder(sbSerch.toString());
		DataTable dt0 = qb0.executeDataTable();
		
		return dt0;
	}
	/**
	 * 根据年龄、保险区间、履行地点 得到相关查询产品
	 * @param SearchID
	 * @return
	 */
	public static DataTable getDefaltProduct(){

		StringBuffer sbSerch = new StringBuffer();
		sbSerch = new StringBuffer(" SELECT DISTINCT e.*,c.ProductType,t1.ComplateDate FROM zcarticle a,sdsearchrelaproduct e,femproductrelacondition f,sdproduct c left join fmrisk t1 on c.productID = t1.RiskCode ");
		sbSerch.append(" WHERE a.status='30' AND a.id = e.prop1  AND c.productID=e.productID ");
		sbSerch.append(" AND c.productid = f.riskcode  and c.IsPublish='Y' ");
		sbSerch.append(" order by a.topflag desc ,a.orderflag desc");
		QueryBuilder qb0 = new QueryBuilder(sbSerch.toString());
		DataTable dt0 = qb0.executeDataTable();
		
		return dt0;
	}
	public static List<String> getAgeList(String[] ages){
		List<String> ageList = new ArrayList<String>();
		if(ages.length>=1){
			for(int j=0;j<ages.length;j++){
				if(StringUtil.isNotEmpty(ages[j])){
					ageList.add(ages[j]);
				}
			}
		}
		return ageList;
	}
	
	public static String getSearchValue(String searchIDs) {
		String searIDs = searchIDs.replace("(", "").replace(")", "").replace("'", "");
		String sql = "SELECT SearchCode,SearchLevel,Id,UpperId from femsearchconditioninfo WHERE Id = ";
		String wheSql = "( SELECT f.UpperId FROM femsearchconditioninfo f WHERE f.id IN "+searchIDs+" limit 1)";
		DataTable dt = new QueryBuilder(sql+wheSql).executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			while (!"1".equals(dt.getString(0, 1))) {
				searIDs = (dt.getString(0, 2)+"," + searIDs);
				dt = new QueryBuilder(sql+"'" + dt.getString(0, 3)+"'").executeDataTable();
				if (dt == null || dt.getRowCount() == 0) {
					return "";
				}
			}
			return dt.getString(0, 0)+"@"+searIDs;
		
		} else {
			return "";
		}
	}
	
	/**
	 * 根据履行地点得到searchcode
	 * @param travelAddress
	 * @return
	 */
    public static String getSearchCodeByTravelAddress(String travelAddress){
		
		StringBuffer tSearchCode= new StringBuffer("('");
		StringBuffer sb = new StringBuffer("select prop2 from zdcode where codetype='TravelAddress' ");
		if(StringUtil.isNotEmpty(travelAddress)){
			if(travelAddress.indexOf(" ")==-1){
				sb.append(" and codevalue='"+travelAddress+"' ");
			}else{
				String[] travels = travelAddress.split(" ");
				StringBuffer sb1 = new StringBuffer("('");
				for(int i=0;i<travels.length;i++){
					sb1.append(travels[i]+"','");
				}
				sb.append(" and codevalue in "+sb1.toString().substring(0, sb1.toString().length() - 2)+" ");
				sb.append(")");
			}
		}else{
			return "";
		}
		QueryBuilder qb = new QueryBuilder(sb.toString());
		DataTable dt = qb.executeDataTable();
		if(dt!=null && dt.getRowCount()>=1){
			for(int j=0;j<dt.getRowCount();j++){
				tSearchCode.append(dt.get(j, 0)+"','");
			}
		}else{
			return "('')";
		}
		return tSearchCode.toString().substring(0,tSearchCode.toString().length()-2)+")";
	}
	/**
	 * 根据年龄得到searchcode
	 * @param travelAddress
	 * @return
	 */
    public static String getSearchIDBySearchCode(String nERiskSubType,String searchCode){
		
		StringBuffer tSearchCode= new StringBuffer("('");
		StringBuffer sb = new StringBuffer("SELECT Id FROM femsearchconditioninfo WHERE 1=1 and ERiskType=? ");
		
		if(StringUtil.isEmpty(searchCode)){
			return "";
		}
		sb.append(" and SearchCode = ? ");
		
		QueryBuilder qb = new QueryBuilder(sb.toString(), nERiskSubType, searchCode);
		DataTable dt = qb.executeDataTable();
		if(dt!=null && dt.getRowCount()>=1){
			for(int j=0;j<dt.getRowCount();j++){
				tSearchCode.append(dt.get(j, 0)+"','");
			}
		}else{
			return "('')";
		}
		return tSearchCode.toString().substring(0,tSearchCode.toString().length()-2)+")";
	}
	/**
	 * 根据年龄得到searchcode
	 * @param travelAddress
	 * @return
	 */
    public static String getSearchCodeByAge(String[] ages){
		
		StringBuffer tSearchCode= new StringBuffer("('");
		StringBuffer sb = new StringBuffer("SELECT Id FROM femsearchconditioninfo WHERE 1=1");
		
		boolean ageflag = false;
		if(ages==null || ages.length<=0){
			return "";
		}
		if(ages.length>=1){
			StringBuffer sb1 = new StringBuffer("('");
			int ageLen = ages.length;
			for(int i=0;i<ageLen;i++){
				if(StringUtil.isNotEmpty(ages[i])){
					sb1.append(ages[i]+"','");
					ageflag = true;
				}
				
			}
			sb.append(" and SearchCode in "+sb1.toString().substring(0, sb1.toString().length() - 2)+") ");
		}
		if(!ageflag){
			return "";
		}
		QueryBuilder qb = new QueryBuilder(sb.toString());
		DataTable dt = qb.executeDataTable();
		if(dt!=null && dt.getRowCount()>=1){
			for(int j=0;j<dt.getRowCount();j++){
				tSearchCode.append(dt.get(j, 0)+"','");
			}
		}else{
			return "('')";
		}
		return tSearchCode.toString().substring(0,tSearchCode.toString().length()-2)+")";
	}
	/**
	 * 获取searchCode名称
	 * @param SearchID
	 * @return
	 */
	public static String getSearchName(String SearchID){
		try{
			String SearchName = "";
			String sql = "select SearchName from SDAllCondtion where SearchID = ?";
			QueryBuilder qb1 = new QueryBuilder(sql);
			qb1.add(SearchID);
			SearchName = (String)qb1.executeOneValue();
			SearchName = SearchName.replaceAll("\\|", "_");
			return SearchName;
		} catch(Exception e){
			logger.error("获取名称方法异常："+e.getMessage(), e);
			return null;
		}
	}
	
	public static String getDaohangSearName(String AddressID) {
		QueryBuilder qb = new QueryBuilder("select Name from zclink where URl like '%/"+AddressID+".shtml' ");
		if (StringUtil.isNotEmpty(Config.getValue("HuiXuanLinkGroup"))) {
			qb.append(" and LinkGroupID in ("+Config.getValue("HuiXuanLinkGroup")+") ");
		}
		return qb.executeString();
	}
	
	public static String getSearchCode(String SearchID){
		try{
			String AddressCode = "";
			String sql = "select AddressCode from SDSearchAddress where SearchID = ?";
			QueryBuilder qb1 = new QueryBuilder(sql);
			qb1.add(SearchID);
			AddressCode = (String)qb1.executeOneValue();
			return AddressCode;
		} catch(Exception e){
			logger.error("获取编码方法异常："+e.getMessage(), e);
			return null;
		}
	}
	public static String getSearchAddress(String SearchID){
		try{
			String SearchAddress = "";
			String sql = "select SearchAddress from SDSearchAddress where SearchID = ?";
			QueryBuilder qb1 = new QueryBuilder(sql);
			qb1.add(SearchID);
			SearchAddress = (String)qb1.executeOneValue();
			return SearchAddress;
		} catch(Exception e){
			logger.error("获取地址方法异常："+e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 获取险种方法名称
	 * @param tERiskSubType
	 * @return
	 */
	public static String getERiskSubTypeName(String tERiskSubType){
		try{
			String RiskSubTypeName = "";
			String sql = "select Name from zccatalog where ProductType = ?";
			QueryBuilder qb1 = new QueryBuilder(sql);
			qb1.add(tERiskSubType);
			RiskSubTypeName = (String)qb1.executeOneValue();
			return RiskSubTypeName;
			
		} catch (Exception e){
			logger.error("获取险种名称方法异常："+e.getMessage(), e);
			return null;
		}
	}
	
	public static String getTagPageURL(Mapx map, int pageNo) {
		map.put("page", "" + pageNo);
		String temp = (String) map.get("query");
		String id=(String) map.get("id");
		String query = temp.substring(0, 6) + StringUtil.leftPad(pageNo + "", '0', 6);
		GetDBdata db = new GetDBdata();
		String path = "";
		try {
			path = db.getOneValue("select value from zdconfig where type='FrontServerContextPath'");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		String tagHtml = path + "/tag/" +query + "-" + id + ".html";
		return tagHtml;
	}
	
	public static String getNewTagPageURL(Mapx map, int pageNo) {
		map.put("page", "" + pageNo);
		String query1 = (String) map.get("query1");
		String query = query1.substring(0, 6) + StringUtil.leftPad(pageNo + "", '0', 6);
		String path = "";
		try {
			ZCSiteSchema site = SiteUtil.getSchema("221");
			path = site.getProp4();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		String tagHtml = path + "/keylist/" +query + ".shtml";
		return tagHtml;
	}

	public static String getURL(Mapx map, String text, String type, String value) {
		Mapx map2 = (Mapx) map.clone();// 必须要先克隆，否则会影响分页
		if (value != null && value.equals(map2.get(type))) {
			return " ·" + text;
		} else {
			map2.put(type, value);
			return " ·<a href='" + ServletUtil.getQueryStringFromMap(map2, true) + "'>" + text + "</a>";
		}
	}

	public static String getParameter(HttpServletRequest request, String key) {
		String queryString = request.getQueryString();
		return getParameter(queryString, key);
	}

	public static String getParameter(String url, String key) {
		if (url.indexOf('?') > 0) {
			url = url.substring(url.indexOf('?') + 1);
		}
		Mapx map = StringUtil.splitToMapx(url, "&", "=");
		String keyword = map.getString(key);
		if (StringUtil.isNotEmpty(keyword)) {
			if (keyword.indexOf('%') < 0) {
				return keyword;
			}
			if (keyword.startsWith("%00")) {
				keyword = keyword.substring(3);
				keyword = StringUtil.urlDecode(keyword, "UTF-8");
				return keyword;
			}
			if (keyword.indexOf("%") >= 0) {
				keyword = StringUtil.replaceEx(keyword, "?", "");
				keyword = StringUtil.replaceEx(keyword, "+", "%20");
				keyword = StringUtil.replaceEx(keyword, " ", "%20");// 统计处可能传空格过来
				keyword = StringUtil.replaceEx(keyword, "%", "");
				byte[] bs = StringUtil.hexDecode(keyword);
				try {
					String result = null;
					if (bs.length >= 3 && StringUtil.isUTF8(bs)) {
						result = new String(bs, "UTF-8");
						String test = new String(result.getBytes());
						if (test.indexOf("?") >= 0) {
							result = new String(bs, "GBK");
						}
					} else {
						result = new String(bs, "GBK");
						String test = new String(result.getBytes());
						if (test.indexOf("?") >= 0) {
							result = new String(bs, "UTF-8");
						}
					}
					return result;
				} catch (UnsupportedEncodingException e) {
					logger.error(e.getMessage(), e);
				}
			}
			return keyword;
		}
		return null;
	}

	public static String showCatalogList(String siteID, String catalogInnerCode) {
		if (StringUtil.isEmpty(siteID)) {
			return "";
		}
		DataTable dt = new QueryBuilder("select ID,Name,ParentID,TreeLevel,InnerCode from ZCCatalog where SiteID=? and Type=? order by orderflag,innercode", siteID, Catalog.CATALOG).executeDataTable();
		dt = DataGridAction.sortTreeDataTable(dt, "ID", "ParentID");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < dt.getRowCount(); i++) {
			String prefix = "";
			for (int j = 1; j < dt.getInt(i, "TreeLevel"); j++) {
				prefix += "　";
			}
			String name = prefix + dt.getString(i, "Name");
			String innerCode = dt.getString(i, "InnerCode");
			String checked = "";
			if (innerCode.equals(catalogInnerCode)) {
				checked = "selected";
			}
			sb.append("<option value=\"" + innerCode + "\" " + checked + ">" + name + "</option>");
		}
		return sb.toString();
	}
	
	
	/**
	 * 通过险种查询栏目扩展属性-侧边栏概述图片.
	 * @param riskType
	 * @return
	 */
	public static String getSummarizeImg(String riskType){
		GetDBdata dBdata = new GetDBdata();
		
		String resultStr = "";
		
		String querySummarizeSql = "select textValue from ZDColumnValue where relaid=(select id from zccatalog where productType=?) and columncode='describeImg'";
		String[] tempParame = new String[1];
		tempParame[0] = riskType;
		
		try {
			List<HashMap<String, String>> list = dBdata.query(querySummarizeSql, tempParame);
			for(Map<String,String> map : list){
				resultStr = map.get("TextValue");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
		}
		
		return resultStr;
	}
	
	/**
	 * 通过险种查询栏目扩展属性-侧边栏概述内容.
	 * @param riskType
	 * @return
	 */
	public static String getSummarize(String riskType){
		GetDBdata dBdata = new GetDBdata();
		
		String resultStr = "";
		
		String querySummarizeSql = "select textValue from ZDColumnValue where relaid=(select id from zccatalog where productType=?) and columncode='productDescribe'";
		String[] tempParame = new String[1];
		tempParame[0] = riskType;
		
		try {
			List<HashMap<String, String>> list = dBdata.query(querySummarizeSql, tempParame);
			for(Map<String,String> map : list){
				resultStr = map.get("TextValue");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
		}
		
		return resultStr;
	}
	
	/**
	 * 获取热销产品
	 * @param ERiskSubType
	 * @return
	 */
	public static DataTable getHotProduct(String ERiskSubType){
		try{ 
			DataTable dt = new DataTable();
			QueryBuilder qb = new QueryBuilder("select innercode from zccatalog where producttype = ?",ERiskSubType);
			String innercode = (String)qb.executeOneValue();
			qb = new QueryBuilder("select a.title,a.SITEID,b.AdaptPeopleInfoListV3,a.URL,b.SupplierCode2,b.InitPrem,b.SalesVolume,b.BasePremV3,b.ProductID " +
					"from zcarticle a,sdsearchrelaproduct b where  b.ProductID=a.prop4 and a.attribute COLLATE utf8_bin like '%hot%' and a.status = '30' and a.cataloginnercode = ? order by topflag desc,orderflag desc,publishdate desc, id desc limit 4",innercode);
			dt = qb.executeDataTable();
			dt = setURL(dt);
			//全站活动价格
			//dt = setActivityPrice(dt);
			return dt;
			} catch (Exception e) {
				logger.error("获取热销产品方法中发生异常："+e.getMessage(), e);
				return null;
			}
	}
	/**
	 * 获取产品评论
	* @Title: getCommentCount 
	* @return String    返回类型 
	 */
	public static String getCommentCount(String productID){
		String cacheKey = RedisConsts.KEY_PREFIX_PRODUCTATTR + productID;//key
		String mapKey = RedisConsts.MAPKEY_PRODUCTATTR_COMMENTCOUNT;//map key名
		int dbIndex = RedisConsts.DB_PRODUCT_ATTR;
		List<String> values = JedisCommonUtil.getMap(dbIndex, cacheKey, mapKey);
		String cacheStr = null;
		if (values.get(0) != null) {
			cacheStr = values.get(0);
		}
		int commentCount = 0;
		if (cacheStr != null) {
			commentCount = JSONObject.parseObject(cacheStr, Integer.class);
		} else {
			QueryBuilder qb = new QueryBuilder("SELECT COUNT(1) FROM sdsearchrelaproduct a,zcarticle b,zccomment c "
					+ "WHERE a.prop1 = b.id AND b.id = c.relaid AND verifyflag='Y' AND a.productid = ?",productID);
			DataTable dt = qb.executeDataTable();
			if(dt!=null && dt.getRowCount()>=1){
				commentCount =  dt.getInt(0, 0);
			}else{
				commentCount =  0;
			} 
			cacheStr = String.valueOf(commentCount);
			Map<String, String> propertys = new HashMap<String, String>();
			propertys.put(mapKey, cacheStr);
			JedisCommonUtil.setMap(dbIndex, cacheKey, propertys);
		}
		return String.valueOf(commentCount);
	}
}
