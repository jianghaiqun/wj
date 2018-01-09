package com.sinosoft.search;

import com.sinosoft.cms.api.SearchAPI;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.wangjin.search.UpdateStaticPage;
import org.apache.lucene.search.SortField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

/**
 * 文章搜索类
 */
public class ArticleSearcher {
	private static final Logger logger = LoggerFactory.getLogger(ArticleSearcher.class);

	/**
	 * 普通搜索，只传入站点或索引ID/查询关键字/时间/是否按时间逆序
	 * 
	 * @param map
	 * @param pageSize
	 * @return
	 */
	public static SearchResult search(Mapx map) {
		SearchParameters sps = new SearchParameters();
		String site = map.getString("site");
		String id = map.getString("id");
		String catalog = map.getString("catalog");
		if (StringUtil.isEmpty(catalog)) {
			catalog = map.getString("Catalog");
		}
		String order = map.getString("order");
		String time = map.getString("time");
		String keyword = map.getString("keyword");
		String query = map.getString("query");
		if (StringUtil.isEmpty(keyword)) {
			keyword = query;// 和旧版本保持一致
		}
		String page = map.getString("page");
		String size = map.getString("size");

		if (StringUtil.isEmpty(id)) {
			id = SearchAPI.getIndexIDBySiteID(site);
		}

		// 检索关键字
		if (StringUtil.isNotEmpty(keyword)) {
			if (keyword.toUpperCase().trim().startsWith("TAG:")) {
				sps.addFulltextField("Tag", keyword.trim().replace("TAG:", ""), false);
			} else {
				sps.addFulltextField("Title", keyword, false);
				sps.addFulltextField("Content", keyword, false);
				sps.addFulltextField("_Keyword", keyword, true);
				
			}
		}

		// 排序
		if ("time".equalsIgnoreCase(order)) {
			sps.setSortField("PublishDate", SortField.STRING, true);
		}
//		else if("top".equalsIgnoreCase(order)){
//			sps.setSortField("TopDate", SortField.STRING, true);
//		}
		
		// 时间范围
		if (StringUtil.isNotEmpty(time)) {
			Date today = new Date();
			String StartDate = DateUtil.toString(DateUtil.addDay(today, -36500));
			if (time.equals("week")) {
				StartDate = DateUtil.toString(DateUtil.addDay(today, -7));
			} else if (time.equals("month")) {
				StartDate = DateUtil.toString(DateUtil.addDay(today, -30));
			} else if (time.equals("quarter")) {
				StartDate = DateUtil.toString(DateUtil.addDay(today, -90));
			}
			String EndDate = "2999-01-01";
			sps.setDateRange("PublishDate", StartDate, EndDate);
		}
		if (StringUtil.isNotEmpty(catalog)) {
			sps.addLeftLikeField("CatalogInnerCode", catalog);
		}
		if (StringUtil.isNotEmpty(page)) {
			sps.setPageIndex(Integer.parseInt(page) - 1);
		}
		if (StringUtil.isNotEmpty(size)) {
			sps.setPageSize(Integer.parseInt(size));
		}
		if (StringUtil.isEmpty(id)) {
			SearchResult sr = new SearchResult();
			sr.Data = new DataTable();
			return sr;
		}
		sps.setIndexID(Long.parseLong(id));
		return ArticleIndexer.search(sps);
	}
	
	public static DataTable getKeyWord1() {
		String locahosturl = Config.getFrontServerContextPath();
		String sql = "select b.WordId, a.KeyWord, '' url from keyword a, keyword_index b where a.type != '0' and a.KeyWord !='' and LENGTH(TRIM(a.KeyWord))>0 and a.Id=b.Id order by rand() limit 0, 20";
		DataTable dt = new QueryBuilder(sql).executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				dt.set(i, 2, (locahosturl + "/keylist/" + UpdateStaticPage.getPageID(Integer.valueOf(dt.getString(i, 0))) + "000001.shtml"));
			}
		}
		
		return dt;
	}
	
	public static DataTable getKeyWord2() {
		String locahosturl = Config.getFrontServerContextPath();
		String sql = "select c.WordId, c.KeyWord, '' url from (select b.WordId, a.KeyWord from keyword a, keyword_index b where a.type != '0' and a.KeyWord !='' and LENGTH(TRIM(a.KeyWord))>0 and a.Id=b.Id order by a.Count desc limit 0, 20) c order by rand() limit 0, 8";
		DataTable dt = new QueryBuilder(sql).executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				dt.set(i, 2, (locahosturl + "/keylist/" + UpdateStaticPage.getPageID(Integer.valueOf(dt.getString(i, 0))) + "000001.shtml"));
			}
		}
		
		return dt;
	}
	
	/**
	 * 普通搜索，只传入站点或索引ID/查询关键字/时间/是否按时间逆序  -----产品搜索单独方法
	 * 
	 * @param map
	 * @param pageSize
	 * @return
	 */
	public static SearchResult proSearch(Mapx map) {
		SearchParameters sps = new SearchParameters();
		String site = map.getString("site");
		String id = map.getString("id");
		String catalog = map.getString("catalog");
		if (StringUtil.isEmpty(catalog)) {
			catalog = map.getString("Catalog");
		}
		String order = map.getString("order");
		String time = map.getString("time");
		String keyword = map.getString("keyword");
		String query = map.getString("query");
		//query = appendSpace(query);													//在输入的每个字后面加一个空格
		if (StringUtil.isEmpty(keyword)) {
			keyword = query;// 和旧版本保持一致
		}
		String page = map.getString("page");
		String size = map.getString("size");

		if (StringUtil.isEmpty(id)) {
			id = SearchAPI.getIndexIDBySiteID(site);
		}

		// 检索关键字
		if (StringUtil.isNotEmpty(keyword)) {
			if (keyword.toUpperCase().trim().startsWith("TAG:")) {
				sps.addNewFulltextField("Tag", keyword.trim().replace("TAG:", ""), false);
			} else {
				sps.addNewFulltextField("Title", keyword, false);
//				sps.addNewFulltextField("Content", keyword, false);
				sps.addNewFulltextField("AdaptPeopleInfo", keyword, false);
				sps.addNewFulltextField("FEMRiskBrightSpot", keyword, false);
//				if(!"50".equals(id)){													//产品添加适合人群和产品亮点全部搜索，去掉此条件
//					sps.addFulltextField("_Keyword", keyword, true);				
//				}
				
			}
		}

		// 排序
		if ("time".equalsIgnoreCase(order)) {
			sps.setSortField("PublishDate", SortField.STRING, true);
		}
//		else if("top".equalsIgnoreCase(order)){
//			sps.setSortField("TopDate", SortField.STRING, true);
//		}
		
		// 时间范围
		if (StringUtil.isNotEmpty(time)) {
			Date today = new Date();
			String StartDate = DateUtil.toString(DateUtil.addDay(today, -36500));
			if (time.equals("week")) {
				StartDate = DateUtil.toString(DateUtil.addDay(today, -7));
			} else if (time.equals("month")) {
				StartDate = DateUtil.toString(DateUtil.addDay(today, -30));
			} else if (time.equals("quarter")) {
				StartDate = DateUtil.toString(DateUtil.addDay(today, -90));
			}
			String EndDate = "2999-01-01";
			sps.setDateRange("PublishDate", StartDate, EndDate);
		}
		if (StringUtil.isNotEmpty(catalog)) {
			sps.addLeftLikeField("CatalogInnerCode", catalog);
		}
		if (StringUtil.isNotEmpty(page)) {
			sps.setPageIndex(Integer.parseInt(page) - 1);
		}
		if (StringUtil.isNotEmpty(size)) {
			sps.setPageSize(Integer.parseInt(size));
		}
		if (StringUtil.isEmpty(id)) {
			SearchResult sr = new SearchResult();
			sr.Data = new DataTable();
			return sr;
		}
		sps.setIndexID(Long.parseLong(id));
		SearchResult searchResult = ArticleIndexer.proSearch(sps);
		// 全词搜索无产品时进行分词搜索
		if (searchResult == null || searchResult.NoResult == 1) {
			searchResult = proSingleSearch(keyword, sps);
		}
		return searchResult;
	}
	
	/**
	 * 分词搜索
	 * 
	 * @param keyword
	 * @param sps
	 * @return
	 */
	public static SearchResult proSingleSearch(String keyword,
			SearchParameters sps) {
		keyword = appendSpace(keyword);
		// 检索关键字
		if (StringUtil.isNotEmpty(keyword)) {
			if (keyword.toUpperCase().trim().startsWith("TAG:")) {
				sps.addNewFulltextField("Tag",
						keyword.trim().replace("TAG:", ""), false);
			} else {
				sps.addNewFulltextField("Title", keyword, false);
				sps.addNewFulltextField("AdaptPeopleInfo", keyword, false);
				sps.addNewFulltextField("FEMRiskBrightSpot", keyword, false);
			}
		}

		return ArticleIndexer.proSearch(sps);
	}
	
	/**
	 * 普通搜索，只传入站点或索引ID/查询关键字/时间/是否按时间逆序  -----产品搜索单独方法
	 * 
	 * @param map
	 * @param pageSize
	 * @return
	 */
	public static SearchResult leftSearch(Mapx map) {
		SearchParameters sps = new SearchParameters();
		String site = map.getString("site");
		String id = map.getString("id");
		String catalog = map.getString("catalog");
		if (StringUtil.isEmpty(catalog)) {
			catalog = map.getString("Catalog");
		}
		String order = map.getString("order");
		String time = map.getString("time");
		String keyword = map.getString("keyword");
		String query = map.getString("query");
		if (StringUtil.isEmpty(keyword)) {
			keyword = query;// 和旧版本保持一致
		}
		String page = map.getString("page");
		String size = map.getString("size");

		if (StringUtil.isEmpty(id)) {
			id = SearchAPI.getIndexIDBySiteID(site);
		}

		// 检索关键字
		if (StringUtil.isNotEmpty(keyword)) {
			if (keyword.toUpperCase().trim().startsWith("TAG:")) {
				sps.addNewFulltextField("Tag", keyword.trim().replace("TAG:", ""), false);
			} else {
				sps.addNewFulltextField("Title", keyword, false);
				sps.addNewFulltextField("Content", keyword, false);
//				sps.addNewFulltextField("AdaptPeopleInfo", keyword, false);
//				sps.addNewFulltextField("FEMRiskBrightSpot", keyword, false);
//				if(!"50".equals(id)){													//产品添加适合人群和产品亮点全部搜索，去掉此条件
//				sps.addNewFulltextField("_Keyword", keyword, true);				
//				}
				
			}
		}

		// 排序
		if ("time".equalsIgnoreCase(order)) {
			sps.setSortField("PublishDate", SortField.STRING, true);
		}
//		else if("top".equalsIgnoreCase(order)){
//			sps.setSortField("TopDate", SortField.STRING, true);
//		}
		
		// 时间范围
		if (StringUtil.isNotEmpty(time)) {
			Date today = new Date();
			String StartDate = DateUtil.toString(DateUtil.addDay(today, -36500));
			if (time.equals("week")) {
				StartDate = DateUtil.toString(DateUtil.addDay(today, -7));
			} else if (time.equals("month")) {
				StartDate = DateUtil.toString(DateUtil.addDay(today, -30));
			} else if (time.equals("quarter")) {
				StartDate = DateUtil.toString(DateUtil.addDay(today, -90));
			}
			String EndDate = "2999-01-01";
			sps.setDateRange("PublishDate", StartDate, EndDate);
		}
		if (StringUtil.isNotEmpty(catalog)) {
			sps.addLeftLikeField("CatalogInnerCode", catalog);
		}
		if (StringUtil.isNotEmpty(page)) {
			sps.setPageIndex(Integer.parseInt(page) - 1);
		}
		if (StringUtil.isNotEmpty(size)) {
			sps.setPageSize(Integer.parseInt(size));
		}
		if (StringUtil.isEmpty(id)) {
			SearchResult sr = new SearchResult();
			sr.Data = new DataTable();
			return sr;
		}
		sps.setIndexID(Long.parseLong(id));
		return ArticleIndexer.leftSearch(sps);
	}
	
	
	public static String appendSpace(String  para){					//在每个字后面加空格  add by lilang 
//		int length = para.length();
//		int k = 0;
//		char[] value = new char[length << 1];
//		char[] value1 = new char[length << 1];
//		for (int i=0, j=0; i<length; ++i, j = i << 1) {
//			CharType c = checkType(para.charAt(i));
//			if(c.name().equals("NUM")||c.name().equals("LETTER")){
//				value1[k] = para.charAt(i);
//				k++;
//			}
//			else{
//				value[j] = para.charAt(i);
//				value[1 + j] = ' ';
//			}
//		}
//		if(k==length){												//如果全部是数字或者字母，则返回数字或字母
//			return new String(value1);
//		}
//		return new String(value);									//部分数字、字母时只返回有中文的部分
		// 完全没搞懂上面啥意思
		if (StringUtil.isEmpty(para)) {
			return null;
		}
		char space = ' ';
		String reuslt = para + space;
		for (int i = 0; i < para.length(); i++) {
			reuslt += para.charAt(i) + ""+ space;
		}
		logger.info("搜索词:{}", reuslt);
		return reuslt;
	}
	
	
	enum CharType{
	    DELIMITER,													 //非字母截止字符，例如，．）（　等等　（ 包含U0000-U0080）
	    NUM, 														 //2字节数字１２３４
	    LETTER,														 //gb2312中的，例如:ＡＢＣ，2字节字符同时包含 1字节能表示的 basic latin and latin-1 OTHER,// 其他字符
	    CHINESE,													 //中文字
	    OTHER;														 //其他类型
	}
	
	private static CharType checkType(char c){						//判断字符类型  add by lilang
        CharType ct =null;

        															//中文，编码区间0x4e00-0x9fbb                

   if ((c >= 0x4e00)&&(c <= 0x9fbb)){ ct = CharType.CHINESE; }

        															//Halfwidth and Fullwidth Forms， 编码区间0xff00-0xffef
        else if ( (c >= 0xff00) &&(c <= 0xffef)) {					 //        2字节英文字
        if ((( c >= 0xff21 )&&( c <= 0xff3a)) || (( c >= 0xff41 )&&( c<= 0xff5a)))
        { ct = CharType.LETTER; }

        															//2字节数字                       
        else if (( c >=0xff10 )&&( c <= 0xff19)  ){ ct = CharType.NUM; }

        															//其他字符，可以认为是标点符号
        else ct = CharType.DELIMITER; }

   																	//basic latin，编码区间 0000-007f            
   else if ( (c>= 0x0021) &&(c <= 0x007e)){ 						//1字节数字 
	   if (( c >= 0x0030 )&&(c <= 0x0039)  ){ ct = CharType.NUM; } //1字节字符 
	   else if ((( c>= 0x0041 )&&( c <= 0x005a)) || (( c >= 0x0061 )&&( c <= 0x007a)))   
	   { ct = CharType.LETTER; }

        															//其他字符，可以认为是标点符号
	   else ct = CharType.DELIMITER; }

   																	//latin-1，编码区间0080-00ff            
   else if ( (c >=0x00a1) &&(c <= 0x00ff)){ if (( c >= 0x00c0 )&&( c <= 0x00ff)){
        ct = CharType.LETTER; } else ct = CharType.DELIMITER; }
   else ct = CharType.OTHER;

        return ct;
}

	
	/**
	 * 标签搜索
	 */
	public static SearchResult tagSearch(Mapx map) {
		SearchParameters sps = new SearchParameters();
		String site = map.getString("site");
		String order = map.getString("order");
		String keyword = map.getString("keyword");
		String query = map.getString("query");
		if (StringUtil.isEmpty(keyword)) {
			keyword = query;// 和旧版本保持一致
		}
		String page = map.getString("page");
		String size = map.getString("size");

		// 检索关键字
		if (StringUtil.isNotEmpty(keyword)) {
			sps.addLikeField("Tag", keyword, false);
		}

		// 排序
		if ("time".equalsIgnoreCase(order)) {
			sps.setSortField("PublishDate", SortField.STRING, true);
		}
		if (StringUtil.isNotEmpty(page)) {
			sps.setPageIndex(Integer.parseInt(page) - 1);
		}
		if (StringUtil.isNotEmpty(size)) {
			sps.setPageSize(Integer.parseInt(size));
		}
		String id = SearchAPI.getIndexIDBySiteID(site);
		sps.setIndexID(Long.parseLong(id));
		return ArticleIndexer.search(sps);
	}

	/**
	 * 高级检索，传入多项搜索条件
	 * 
	 * @param map
	 * @return
	 */
	public static SearchResult advanceSearch(Mapx map) {
		SearchParameters sps = new SearchParameters();

		String site = map.getString("site");
		String id = map.getString("id");
		String startDate = map.getString("startdate");
		String endDate = map.getString("enddate");
		String catalog = map.getString("catalog");
		String author = map.getString("author");
		String title = map.getString("title");
		String content = map.getString("content");
		String prop1 = map.getString("prop1");//是否作废
		String keyword = map.getString("keyword");
		String query = map.getString("query");
		if (StringUtil.isEmpty(keyword)) {
			keyword = query;// 和旧版本保持一致
		}
		String orderField = map.getString("orderfield");
		String descFlag = map.getString("descflag");
		String page = map.getString("page");
		String size = map.getString("size");

		if (StringUtil.isEmpty(id)) {
			id = SearchAPI.getIndexIDBySiteID(site);
		}

		if (StringUtil.isNotEmpty(startDate) && StringUtil.isEmpty(endDate)) {
			endDate = "2099-01-01";
		}
		if (StringUtil.isNotEmpty(endDate) && StringUtil.isEmpty(startDate)) {
			startDate = "1900-01-01";
		}
		if (StringUtil.isNotEmpty(startDate)) {
			sps.setDateRange("PublishDate", startDate, endDate);
		}
		if (StringUtil.isNotEmpty(catalog)) {
			sps.addLeftLikeField("CatalogInnerCode", catalog, true);
		}
		if (StringUtil.isNotEmpty(title)) {
			sps.addFulltextField("Title", title);
		}
		if (StringUtil.isNotEmpty(content)) {
			sps.addFulltextField("Content", content);
		}
		if (StringUtil.isNotEmpty(keyword)) {
			sps.addFulltextField("Title", keyword, false);// 标题中有的排前面
			sps.addFulltextField("Content", keyword, false);
			sps.addFulltextField("_Keyword", keyword, true);
		}
		if (StringUtil.isNotEmpty(orderField)) {
			boolean isDesc = "true".equals(descFlag);
			sps.setSortField(orderField, SortField.STRING, isDesc);
		}
		if (StringUtil.isNotEmpty(author)) {
			sps.addEqualField("Author", author);
		}
		if (StringUtil.isNotEmpty(page)) {
			sps.setPageIndex(Integer.parseInt(page) - 1);
		}
		if (StringUtil.isNotEmpty(size)) {
			sps.setPageSize(Integer.parseInt(size));
		}
		if (StringUtil.isEmpty(id)) {
			SearchResult sr = new SearchResult();
			sr.Data = new DataTable();
			return sr;
		}
		//是否作废
		if (StringUtil.isNotEmpty(prop1)) {
			sps.addEqualField("Prop1", prop1);
		}
		sps.setIndexID(Long.parseLong(id));
		return ArticleIndexer.search(sps);
	}
	
	public static String getKeyWord(String query){
		try {
			if(StringUtil.isNotEmpty(query) && query.length()>=6){
				
				while(query.startsWith("0")){
					query = query.substring(1 , query.length() );
				}
				if(StringUtil.isDigit(query)){
					String sql = "select keyword from keyword_index where wordid = ?";
					String[] sql1temp = {query};
					GetDBdata db0 = new GetDBdata();
					return db0.getOneValue(sql, sql1temp);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	
	public static DataTable getKnwn(List<String> id){
		String x = "";
		for(int i = 0;i < id.size();i++){
			if(i!=0){
				x+=",";
			}
			x += id.get(i);
		}
		String sql = "select * from zcarticle where catalogInnercode like '002306%' ";
		if (StringUtil.isNotEmpty(x)) {
			sql += " and id not in("+x+") ";
		}
		sql += " order by modifytime DESC ";
		DataTable dt = new QueryBuilder(sql).executeDataTable();
		DataTable result = new DataTable();
		String ContextPath = Config.getValue("ContextPath");
		if(!ContextPath.endsWith("/")){
			ContextPath += "/";
		}
		for(int i=0;i<dt.getRowCount();i++){
			DataRow dr = dt.get(i);
			dr.set("URL",ContextPath + "" + dr.getString("URL") );
			result.insertRow(dr);
		 }
		return  result;
	}

	public static DataTable getNews(List<String> id){
		String x = "";
		for(int i = 0;i < id.size();i++){
			if(i!=0){
				x+=",";
			}
			x += id.get(i);
		}
		String sql = "select * from zcarticle where catalogInnercode like '002307%' ";
		if (StringUtil.isNotEmpty(x)) {
			sql += " and id not in("+x+") ";
		}
		sql += " order by modifytime DESC ";
		DataTable dt = new QueryBuilder(sql).executeDataTable();
		DataTable result = new DataTable();
		String ContextPath = Config.getValue("ContextPath");
		if(!ContextPath.endsWith("/")){
			ContextPath += "/";
		}
		for(int i=0;i<dt.getRowCount();i++){
			DataRow dr = dt.get(i);
			dr.set("URL",ContextPath + "" + dr.getString("URL") );
			result.insertRow(dr);
		 }
		return  result;
	}
	
	public static DataTable getQues(List<String> id){
		String x = "";
		for(int i = 0;i < id.size();i++){
			if(i!=0){
				x+=",";
			}
			x += id.get(i);
		}
		String sql = "select * from zcarticle where catalogInnercode like '002308%' ";
		if (StringUtil.isNotEmpty(x)) {
			sql += " and id not in("+x+") ";
		}
		sql += "order by modifytime DESC";
		DataTable dt = new QueryBuilder(sql).executeDataTable();
		DataTable result = new DataTable();
		String ContextPath = Config.getValue("ContextPath");
		if(!ContextPath.endsWith("/")){
			ContextPath += "/";
		}
		if (dt != null) {
			for(int i=0;i<dt.getRowCount();i++){
				DataRow dr = dt.get(i);
				dr.set("URL",ContextPath + "" + dr.getString("URL") );
				result.insertRow(dr);
			 }
		}
		return  result;
	}
	
	public static void main(String[] args) {
		
		// String file = Config.getContextRealPath() + "WEB-INF/data/index/" +
		// 12 + "/time.lock";
		// FileUtil.delete(file);
		// SearchAPI.update(12);
		//
		// long t = System.currentTimeMillis();
		// for (int i = 0; i < 100; i++) {
		// Mapx map = new Mapx();
		// map.put("id", "12");
		// // map.put("time", "all");
		// // map.put("catalog", "2049");
		// map.put("page", "0");
		// map.put("size", "10");
		// // map.put("orderfield", "PublishDate");
		// // map.put("descflag", "true");
		// map.put("keyword", "天文 流星 ");
		// map.put("ArticleImage", num)
		// //SearchResult sr = advanceSearch(map);
		// //System.out.println(sr.Data);
		// // System.out.println(sr.Total);
		// }
		// System.out.println(System.currentTimeMillis() - t);
//		System.out.println("--------"  + getKeyWord("0000003"));		
		// sps.addLikeField("ArticleImage", "upload");
		// sps.addEqualField("ID", "224792");
		
		Mapx map = new Mapx();
		map.put("query", "平安");
		map.put("id", "50");
		map.put("site", "221");
		SearchResult sr = ArticleSearcher.proSearch(map);
		DataTable dt = sr.Data;
		for(int i=0;i<dt.getRowCount();i++){
			DataRow dr = dt.getDataRow(i);
			//System.out.println(dr.getString("Title"));
		}
		
//		SearchParameters sps = new SearchParameters();
//		sps.setIndexID(50);
//		String keyword = "平安";
//		sps.addNewFulltextField("Title", "平安" , false);
//		sps.addNewFulltextField("AdaptPeopleInfo", keyword, false);
//		sps.addNewFulltextField("FEMRiskBrightSpot", keyword, false);
//		SearchResult sr = ArticleIndexer.proSearch(sps);
	}
	/**
	 * tag标签页问答，百科搜索，推荐专题只传入关键词，搜索该栏目下tag词匹配该关键词的文章，如果少于5篇，剩下的随机调取该栏目下文章
	 * 
	 * @param map
	 * @return
	 */
	public static DataTable leftSearch1(Mapx map) {
		String query = map.getString("query");
		QueryBuilder qb = new QueryBuilder("select url,title from zcarticle where cataloginnercode like '002308%' and status='30' and tag like '%"+query+"%' order by rand()");
		DataTable dt = qb.executeDataTable();
		return dt;
	}
	public static DataTable leftSearch2(Mapx map) {
		String query = map.getString("query");
		QueryBuilder qb = new QueryBuilder("select url,title from zcarticle where cataloginnercode like '002308%' and status='30'  order by rand()");
		DataTable dt = qb.executeDataTable();
		return dt;
	}
	public static DataTable leftSearch3(Mapx map) {
		String query = map.getString("query");
		QueryBuilder qb = new QueryBuilder("select url,title from zcarticle where cataloginnercode like '002347%' and status='30' and tag like '%"+query+"%' order by rand()");
		DataTable dt = qb.executeDataTable();
		return dt;
	}
	public static DataTable leftSearch4(Mapx map) {
		String query = map.getString("query");
		QueryBuilder qb = new QueryBuilder("select url,title from zcarticle where cataloginnercode  like '002347%' and status='30'  order by rand()");
		DataTable dt = qb.executeDataTable();
		return dt;
	}
	public static DataTable leftSearch5(Mapx map) {
		String query = map.getString("query");
		QueryBuilder qb = new QueryBuilder("select url,title from zcarticle where cataloginnercode like '002354%' and status='30'  and tag like '%"+query+"%'  order by rand()");
		DataTable dt = qb.executeDataTable();
		return dt;
	}
	public static DataTable leftSearch6(Mapx map) {
		String query = map.getString("query");
		QueryBuilder qb = new QueryBuilder("select url,title from zcarticle where cataloginnercode like '002354%' and status='30'  order by rand()");
		DataTable dt = qb.executeDataTable();
		return dt;
	}
	/**
	 * tag标签页推荐标签只传入关键词，搜索ZCTAG下TAG词匹配该关键词的，如果少于20个，剩下的随机调取TAG
	 * 
	 * @param map
	 * @return
	 */
	public static DataTable leftSearch7(Mapx map) {
		String query = map.getString("query");
		QueryBuilder qb = new QueryBuilder("select LinkURL,Tag,UsedCount from zctag where  Tag like '%"+query+"%' order by rand()");
		DataTable dt = qb.executeDataTable();
		return dt;
	}
	public static DataTable leftSearch8(Mapx map) {
		String query = map.getString("query");
		QueryBuilder qb = new QueryBuilder("select LinkURL,Tag,UsedCount from zctag order by rand()");
		DataTable dt = qb.executeDataTable();
		return dt;
	}
	/**
	 * tag标签页右侧搜索知识，资讯栏目下tag词匹配该关键词的所有文章
	 * 
	 * @param map
	 * @return
	 */
	public static DataTable leftSearch9(Mapx map) {
		String query = map.getString("query");

		QueryBuilder qbScope = new QueryBuilder(
				"SELECT codevalue FROM zdcode WHERE codetype='tagScope' AND parentcode='tagScope'");
		DataTable dtScope = qbScope.executeDataTable();
		String partSQL = "";
		if (dtScope != null && dtScope.getRowCount() > 0) {
			for (int i = 0; i < dtScope.getRowCount(); i++) {
				if (i == 0) {
					partSQL += " (cataloginnercode like '"
							+ dtScope.getString(i, "codevalue") + "%' ";
				}
				if (i == (dtScope.getRowCount() - 1)) {
					partSQL += " or cataloginnercode like '"
							+ dtScope.getString(i, "codevalue") + "%') and ";
				}
				if (i > 0 && i < (dtScope.getRowCount() - 1)) {
					partSQL += " or cataloginnercode like '"
							+ dtScope.getString(i, "codevalue") + "%' ";
				}

			}

		}

		QueryBuilder qb = new QueryBuilder(
				"select url,title,tag,content,publishdate,cataloginnercode from zcarticle where "
						+ partSQL
						+ "  status='30' and tag like '%"
						+ query
						+ "%' order by publishdate desc");
		DataTable dt = qb.executeDataTable();
		return dt;
	}
}
