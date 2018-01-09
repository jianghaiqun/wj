package com.sinosoft.search;

import com.alibaba.fastjson.JSON;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.LogUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.NumberUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.search.index.Indexer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文章索引类
 * 
 */
public class ProductIndexer extends Indexer {
	private static final Logger logger = LoggerFactory.getLogger(ProductIndexer.class);
	
	//临时文件id
	
	private long id = 1024;
	private long finalId =55;

	public ProductIndexer() {

		setPath(Config.getContextRealPath() + "WEB-INF/data/index/" + id + "/");
	}

	
	public ProductIndexer(int id) {

		setPath(Config.getContextRealPath() + "WEB-INF/data/index/" + id + "/");
	}

	/**
	 * 第一次需创建索引
	 */
	public void create() {

		try {
			String productSql = " select a.id,b.productid,a.url,a.Title,a.ShortTitle, b.ProductID,a.Status,f.AdaptPeopleInfo,f.ERiskFeatures,b.popular,b.InitPrem,b.LogoLink,"
					+
					"c.ProductType,c.remark6 as companyCode,SUBSTR(c.HtmlPath,LENGTH(SUBSTRING_INDEX(c.HtmlPath,'/',3))+1) as HtmlPath,d.prop5,d.IsPublish,case when d.IsPublish ='Y' and (d.prop5 is null or d.prop5='')  "
					+
					"then 'ALL' else d.prop5 end  as wapPublish,'' as SalesVolume,'' as CalHTML,'' as TextAgeSection,'' as OccupSection,'' as PeriodSection,'' as FEMRiskBrightSpot,'' as ComplateDate, a.AddTime,a.ModifyTime,d.MakeDate,d.Modifydate ";
			productSql += " from zcarticle a, SDSearchRelaProduct b left join femriskb f on f.RiskCode=b.ProductID ,sdproduct c left join productrelainfo d on d.ProductID=c.ProductID LEFT JOIN FMRISKB FF ON c.ProductID=FF.RiskCode ";
			productSql += " where type ='1' and b.productid=c.productid and a.id = b.prop1 and cataloginnercode like '002313%' and a.type='1' and c.IsPublish='Y' and (ff.riskprop!='T' or ff.riskprop is null)";

			QueryBuilder qb = new QueryBuilder(productSql);

			DataTable dt = qb.executeDataTable();

			logger.info("创建索引 id:{},数据", id, dt.getRowCount());

			for (int i = 0; i < dt.getRowCount(); i++) {
				String productid = dt.getString(i, "productid");
				dt.set(i, "CalHTML", queryDutyStr(productid));
				dt.set(i, "TextAgeSection", queryTextAge(productid));
				dt.set(i, "PeriodSection", queryPeriod(productid));
				dt.set(i, "OccupSection", queryOccup(productid));
				dt.set(i, "SalesVolume", querySalesVolume(productid));
				dt.set(i, "FEMRiskBrightSpot", queryFEMRiskBrightSpot(productid));
				dt.set(i, "ComplateDate", queryComplateDate(productid));
			}

			if (dt != null && dt.getRowCount() > 0)
				index(dt, writer, false);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 查询拍拍速赔标志
	 *
	 * @param riskcode
	 * @return
	 */
	private String queryComplateDate(String riskcode) {

		String dutySql = " SELECT ComplateDate FROM fmrisk  where RiskCode=?";
		QueryBuilder qb = new QueryBuilder(dutySql, riskcode);
		DataTable dt = qb.executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			return dt.getString(0, "ComplateDate");
		} else {
			return "";
		}
	}

	private String queryFEMRiskBrightSpot(String riskcode) {

		String dutySql = " select BrightSpotName,'' as val from femriskbrightspotlistb  ";
		dutySql += " where RiskCode=? order by BrightSpotOrder asc";
		QueryBuilder qb = new QueryBuilder(dutySql, riskcode);
		DataTable dt = qb.executeDataTable();
		dt.toString();
		return JSON.toJSONString(dt.toMapx("BrightSpotName", "val"));
	}

	/**
	 * 查询责任信息
	 *
	 * @author 郭斌
	 * @return
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	private String queryDutyStr(String riskcode) {

		String dutySql = " select  f3.DutyName , GROUP_CONCAT(f1.amnt) dutyVal ";
		dutySql += " from femdutyamntpremlistb f1,femdutyfactorb f2,  ";
		dutySql += " (select DutyName, DutyCode from fmdutyb where riskcode=? union all ";
		dutySql += "select RiskKindName as DutyName,RiskKindCode as DutyCode from FMKindb where riskcode=?) f3 ";
		dutySql += " where f2.DutyFactorID=f1.DutyFactorID and f2.riskcode=? and f2.DutyFactorCode=f3.DutyCode ";
		dutySql += "  group by f3.DutyName  ";

		QueryBuilder qb = new QueryBuilder(dutySql);
		qb.add(riskcode);
		qb.add(riskcode);
		qb.add(riskcode);
		DataTable dt = qb.executeDataTable();
		return JSON.toJSONString(dt.toMapx("DutyName", "dutyVal"));
	}

	/**
	 * 查询承保年龄
	 *
	 * @author 李垠锋
	 * @return
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	private String queryTextAge(String riskcode) {

		String textAgeSql = " SELECT FactorValue FROM femriskFactorListb WHERE riskcode=? AND FactorType='TextAge'";

		QueryBuilder qb = new QueryBuilder(textAgeSql, riskcode);
		DataTable dt = qb.executeDataTable();

		List<String> result = new ArrayList<String>();
		for (int i = 0; i < dt.getRowCount(); i++) {
			String factorValue = dt.getString(i, "FactorValue");
			result.add(factorValue);
		}

		String strTextAge = getPeriodInsurance(result, true);

		return strTextAge;
	}

	/**
	 * 查询保障期限
	 *
	 * @author 李垠锋
	 * @return
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	private String queryPeriod(String riskcode) {

		String perioSql = " SELECT FactorValue FROM femriskFactorListb WHERE riskcode=? AND FactorType='Period'";

		QueryBuilder qb = new QueryBuilder(perioSql, riskcode);
		DataTable dt = qb.executeDataTable();

		List<String> result = new ArrayList<String>();
		for (int i = 0; i < dt.getRowCount(); i++) {
			String factorValue = dt.getString(i, "FactorValue");
			result.add(factorValue);
		}

		String strPeriod = getPeriodInsurance(result, false);

		return strPeriod;
	}

	/**
	 * 查询职业类别
	 *
	 * @author 李垠锋
	 * @return
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	private String queryOccup(String riskcode) {

		String occupSql = "SELECT FactorDisplayValue FROM femriskFactorListb WHERE riskcode=? AND FactorType='Occup' LIMIT 1";

		QueryBuilder qb = new QueryBuilder(occupSql, riskcode);
		DataTable dt = qb.executeDataTable();

		if (dt.getRowCount() > 0) {
			return dt.getString(0, "FactorDisplayValue");
		} else {
			return "";
		}
	}

	/**
	 * 销量查询
	 *
	 * @author 李垠锋
	 * @return
	 */

	private String querySalesVolume(String productId) {

		String sql = "SELECT a.SalesVolume, b.SplitRiskCode, a.ProductID " +
				"FROM sdsearchrelaproduct a, sdproduct b " +
				"WHERE a.productid = b.productid  AND a.ProductId = ? ";

		QueryBuilder qb = new QueryBuilder(sql, productId);
		DataTable dt = qb.executeDataTable();

		String tSalesVolume = "0";
		if (dt.getRowCount() > 0) {
			// 判断产品的分类
			String tSplitRiskCode = dt.getString(0, "SplitRiskCode");

			if (!StringUtil.isEmpty(tSplitRiskCode)) {
				if (tSplitRiskCode.indexOf(",") != -1) {
					String[] src_plan = tSplitRiskCode.split(",");
					for (int j = 0; j < src_plan.length; j++) {
						if (src_plan[j].indexOf("-") != -1) {
							String[] src = src_plan[j].split("-");
							String cProductId = src[0];

							String sql1 = "SELECT a.SalesVolume FROM sdsearchrelaproduct a WHERE a.ProductID = ?";

							QueryBuilder qb1 = new QueryBuilder(sql1, cProductId);
							DataTable dt1 = qb1.executeDataTable();

							if (dt1.getRowCount() > 0) {
								String cSalesVolume = dt1.getString(0, "SalesVolume");
								tSalesVolume = String.valueOf(Integer.parseInt(tSalesVolume)
										+ Integer.parseInt(cSalesVolume));
							}
						}
					}
				}
			} else {
				tSalesVolume = dt.getString(0, "SalesVolume");
			}
		}
		return tSalesVolume;
	}

	/**
	 * 更新索引
	 */
	public void update() {

		try {

			String productSql = " select '' as FEMRiskBrightSpot ,  a.id,b.productid,a.url,a.Title,a.ShortTitle, b.ProductID,a.Status,f.AdaptPeopleInfo,f.ERiskFeatures,b.Popular,b.InitPrem,b.LogoLink,"
					+
					"c.ProductType,c.remark6 as companyCode,SUBSTR(c.HtmlPath,LENGTH(SUBSTRING_INDEX(c.HtmlPath,'/',3))+1) as HtmlPath,d.prop5,d.IsPublish,case when d.IsPublish ='Y' and (d.prop5 is null or d.prop5='')  "
					+
					"then 'ALL' else d.prop5 end  as wapPublish,'' as SalesVolume,'' as CalHTML,'' as TextAgeSection,'' as OccupSection,'' as PeriodSection,a.AddTime,a.ModifyTime,d.MakeDate,d.Modifydate ";
			productSql += " from zcarticle a, SDSearchRelaProduct b left join femriskb f on f.RiskCode=b.ProductID ,sdproduct c left join productrelainfo d on d.ProductID=c.ProductID";
			productSql += " where type ='1' and b.productid=c.productid and a.id = b.prop1 and cataloginnercode like '002313%' and a.type='1' and c.IsPublish='Y'";
			productSql += " and (a.AddTime >=? or a.ModifyTime >=? or d.MakeDate >=? or d.Modifydate>=?   )";

			QueryBuilder qb = new QueryBuilder(productSql);

			qb.add(lastDate);
			qb.add(lastDate);
			qb.add(lastDate);
			qb.add(lastDate);

			DataTable dt = qb.executeDataTable();

			Object[] argArr = {id, dt.getRowCount(), DateUtil.toDateTimeString(lastDate)};
			logger.info("更新索引 id: {} ,数据<{}> 更新时间: {}", argArr);

			for (int i = 0; i < dt.getRowCount(); i++) {
				String productid = dt.getString(i, "productid");
				dt.set(i, "CalHTML", queryDutyStr(productid));
				dt.set(i, "TextAgeSection", queryTextAge(productid));
				dt.set(i, "PeriodSection", queryPeriod(productid));
				dt.set(i, "OccupSection", queryOccup(productid));
				dt.set(i, "FEMRiskBrightSpot", queryFEMRiskBrightSpot(productid));
			}

			if (dt != null && dt.getRowCount() > 0)
				index(dt, writer, true);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	private void index(DataTable dt, IndexWriter writer, boolean updateFlag) throws CorruptIndexException, IOException {

		Field[] fields = new Field[dt.getColCount() + 1];
		for (int n = 0; n < dt.getColCount(); n++) {
			String columnName = dt.getDataColumn(n).getColumnName();
			if (columnName.equalsIgnoreCase("TITLE")) {
				fields[n] = new Field("TITLE", "", Field.Store.YES, Field.Index.ANALYZED);

			} else if (columnName.equalsIgnoreCase("AdaptPeopleInfo")) {
				fields[n] = new Field("ADAPTPEOPLEINFO", "", Field.Store.YES, Field.Index.ANALYZED);

			} else if (columnName.equalsIgnoreCase("FEMRiskBrightSpot")) {
				fields[n] = new Field("FEMRISKBRIGHTSPOT", "", Field.Store.YES, Field.Index.ANALYZED);

			} else {
				fields[n] = new Field(columnName.toUpperCase(), "",
						Field.Store.YES, Field.Index.NOT_ANALYZED);
			}
		}

		for (int i = 0; i < dt.getRowCount(); i++) {
			Document doc = new Document();
			DataRow dr = dt.getDataRow(i);

			Date d1 = dr.getDate("AddTime");
			Date d2 = dr.getDate("ModifyTime");
			Date d3 = dr.getDate("MakeDate");
			Date d4 = dr.getDate("Modifydate");
			if (d1 != null && d1.getTime() > nextLastDate.getTime()) {
				nextLastDate = d1;
			}
			if (d2 != null && d2.getTime() > nextLastDate.getTime()) {
				nextLastDate = d2;
			}
			if (d3 != null && d3.getTime() > nextLastDate.getTime()) {
				nextLastDate = d3;
			}
			if (d4 != null && d4.getTime() > nextLastDate.getTime()) {
				nextLastDate = d4;
			}

			String allProductChanel = Config.getValue("OutProductList");

			try {
				for (int n = 0; n < dt.getColCount(); n++) {
					String columnName = dt.getDataColumn(n).getColumnName();
					Field field = fields[n];

					if (columnName.equalsIgnoreCase("WAPPUBLISH")) {
						if ("ALL".equalsIgnoreCase(dr.getString(n))) {
							field.setValue(allProductChanel);

						} else if (StringUtil.isNotEmpty(dr.getString(n))) {
							field.setValue(dr.getString(n)); 

						} else {
							field.setValue("Offline");

						}

					} else {
						field.setValue(dr.getString(n));
					}
					doc.add(field);
				}

				if (updateFlag) {
					writer.updateDocument(new Term("ID", dt.getString(i, "ID")), doc);

				} else {
					writer.addDocument(doc);

				}
			} catch (Throwable t) {
				logger.error(t.getMessage(), t);
			}
		}
	}

	/**
	 * 
	 * search:(查询). <br/>
	 *
	 * @author 郭斌
	 * @param sps
	 * @return
	 */
	public static SearchResult search(SearchParameters sps, String keyword) {

		FSDirectory directory = null;// dirrctory 索引储存在磁盘上的位置
		IndexReader reader = null;//
		IndexSearcher searcher = null;// IndexSearcher 用于在建立好的索引上进行搜索的句柄类
		SimpleHTMLFormatter formatter = null;
		try {
			long t = System.currentTimeMillis();
			directory = FSDirectory.getDirectory(new File(Config.getContextRealPath() + "WEB-INF/data/index/"
					+ sps.getIndexID()));
			reader = IndexReader.open(directory, true);
			searcher = new IndexSearcher(reader);// 生成检索器
			searcher.setSimilarity(new ProductSimilarity());
			if (sps.getBooleanQuery().clauses().size() == 0) {// BooleanQuery逻辑组合检索
				MatchAllDocsQuery maq = new MatchAllDocsQuery();
				sps.addQuery(maq, false);
			}

			int abstractLength = 150;
			DataTable dt = new DataTable();

			int start = sps.getPageIndex() * sps.getPageSize();
			TopDocs docs = null;
			if (sps.getSort() != null) {
				// 利用参数控制检索（检索方式，对结果进行过滤的方式，权重，排序方式）
				docs = searcher.search(sps.getBooleanQuery(), sps
						.getDateRangeFilter(), start + sps.getPageSize(), sps
						.getSort());
			} else {
				docs = searcher.search(sps.getBooleanQuery(), sps
						.getDateRangeFilter(), start + sps.getPageSize());
			}

			for (int i = start; i < start + sps.getPageSize() && i < docs.scoreDocs.length; i++) {
				Document doc = searcher.doc(docs.scoreDocs[i].doc);// 返回第几个记录
				if (i == start) {
					Object[] fields = doc.getFields().toArray();
					for (int j = 0; j < fields.length; j++) {
						String name = ((Field) fields[j]).name();
						if (!dt.containsColumn(name)) {
							dt.insertColumn(name);
						}
					}
				}
				String title = doc.get("TITLE");
				String adaptpeopleinfo = doc.get("ADAPTPEOPLEINFO");
				String femriskbrightspot = doc.get("FEMRISKBRIGHTSPOT");

				dt.insertRow(new String[dt.getColCount()]);
				for (int j = 0; j < dt.getColCount(); j++) {
					dt.set(i - start, j, doc.get(dt.getDataColumn(j).getColumnName()));
				}

				String titleTemp = title;
				String adaptpeopleinfoTemp = adaptpeopleinfo;
				String femriskbrightspotTemp = femriskbrightspot;
				// 标红关键字
				if (StringUtil.isNotEmpty(keyword)) {

					// 剔除特殊字符
					String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
					Pattern p = Pattern.compile(regEx);

					// 利用set去除关键字中重复的
					Set<String> set = new HashSet<String>();
					for (int j = 0; j < keyword.length(); j++) {
						String skw = keyword.charAt(j) + "";
						set.add(skw);
					}

					// 去除特殊字符
					Iterator<String> it = set.iterator();
					while (it.hasNext()) {
						String skw = it.next();
						Matcher m = p.matcher(skw);
						if (m.matches()) {
							continue;
						}

						String skwTemp = "#" + skw + "#";
						if (StringUtil.isEmpty(skw)) {
							continue;
						}

						if (StringUtil.isNotEmpty(title) && title.contains(skw)) {
							titleTemp = titleTemp.replaceAll(skw, skwTemp);
						}

						if (StringUtil.isNotEmpty(adaptpeopleinfo) && adaptpeopleinfo.contains(skw)) {
							adaptpeopleinfoTemp = adaptpeopleinfoTemp.replaceAll(skw, skwTemp);
						}

						if (StringUtil.isNotEmpty(femriskbrightspot) && title.contains(femriskbrightspot)) {
							femriskbrightspotTemp = femriskbrightspotTemp.replaceAll(skw, skwTemp);
						}
					}

					it = set.iterator();
					while (it.hasNext()) {
						String skw = it.next();
						Matcher m = p.matcher(skw);
						if (m.matches()) {
							continue;
						}
						String skwTemp = "#" + skw + "#";
						if (StringUtil.isEmpty(skw)) {
							continue;
						}

						titleTemp = titleTemp.replaceAll(skwTemp, "<font color=red>" + skw + "</font>");
						adaptpeopleinfo = adaptpeopleinfo.replaceAll(skwTemp, "<font color=red>" + skw + "</font>");
						femriskbrightspot = femriskbrightspot.replaceAll(skwTemp, "<font color=red>" + skw + "</font>");
					}


					dt.set(i - start, "TITLE", titleTemp);
					dt.set(i - start, "ADAPTPEOPLEINFO", adaptpeopleinfo);
					dt.set(i - start, "FEMRISKBRIGHTSPOT", femriskbrightspot);

				} else {
					dt.set(i - start, "TITLE", title);

				}
			}
			SearchResult r = new SearchResult();
			r.Data = dt;
			r.Total = docs.totalHits;
			r.UsedTime = NumberUtil.round((System.currentTimeMillis() - t) * 1.0 / 1000, 3);
			return r;
		} catch (Exception e) {
			logger.error("搜索引擎异常！" + e.getMessage(), e);
		} finally {
			try {
				if (directory != null)
					directory.close();
				if (reader != null)
					reader.close();
				if (searcher != null)
					searcher.close();
			} catch (Exception e) {
				logger.error("关闭搜索引擎异常！" + e.getMessage(), e);
			}
		}
		return null;
	}

	/**
	 * 更新产品索引
	 */
	public void updateProductIndex(long id) {

		
		String path = Config.getContextRealPath() + "WEB-INF/data/index/" + this.id + "/";
		boolean delFlag = FileUtil.deleteFromDir(path);
		logger.info("删除原产品索引:{}", delFlag);
		
		ProductIndexer ri = new ProductIndexer();
		ri.start();
		fileCopy();
	}

	/**
	 * 创建产品索引
	 */
	public void createProductIndex(long id) {


	

		
		String path = Config.getContextRealPath() + "WEB-INF/data/index/" + this.id + "/";
		boolean delFlag = FileUtil.deleteFromDir(path);
		logger.info("删除原产品索引:{}", delFlag);

		updateProductIndex(this.id);
		
 
	}

	 
	public void fileCopy(){
		
		String path = Config.getContextRealPath() + "WEB-INF/data/index/" + this.id + "/";
		String pathNew = Config.getContextRealPath() + "WEB-INF/data/index/" + this.finalId + "/";
		
		boolean delFlagNew = FileUtil.deleteFromDir(pathNew);
		logger.info("删除备份产品索引:{}", delFlagNew);
		FileUtil.copy(path,pathNew);
	}
	 
	/**
	 * 
	 * doSear搜索). <br/>
	 *
	 * @author 郭斌
	 * @param map
	 *            扩展字段，page、size等
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public SearchResult doSearch(Mapx map) {

		try {
			SearchParameters sps = new SearchParameters();

			String keyword = map.getString("keyword");
			String channel = map.getString("channel");
			String page = map.getString("page");
			String size = map.getString("size");
			String sortColumn = map.getString("sortColumn");
			String sortDesc = map.getString("sortDesc");
			boolean descFlag = true;
			if ("desc".equals(sortDesc)) {
				descFlag = true;
			} else if ("asc".equals(sortDesc)) {
				descFlag = false;
			}

			if (StringUtil.isEmpty(channel)) {
				channel = "wap_ht";
			}

			if (StringUtil.isNotEmpty(page)) {
				sps.setPageIndex(Integer.parseInt(page) - 1);
			}

			if (StringUtil.isNotEmpty(size)) {
				sps.setPageSize(Integer.parseInt(size));
			}

			// 排序
			// 人气
			if ("popular".equals(sortColumn)) {
				sps.setSortField("Popular", SortField.INT, descFlag);
			}
			// 价格
			else if ("price".equals(sortColumn)) {
				sps.setSortField("InitPrem", SortField.FLOAT, descFlag);
			}
			// 销量
			else if ("sales".equals(sortColumn)) {
				sps.setSortField("SalesVolume", SortField.INT, descFlag);
			}

			// 检索关键字
			if (StringUtil.isNotEmpty(keyword)) {

				BooleanQuery booleanQuery = new BooleanQuery();

				keyword = appendSpace(keyword);

				booleanQuery.add(searchParam(keyword, 10000, "TITLE"), Occur.SHOULD);
				booleanQuery.add(searchParam(keyword, 100, "ADAPTPEOPLEINFO"), Occur.SHOULD);
				booleanQuery.add(searchParam(keyword, 1, "FEMRISKBRIGHTSPOT"), Occur.SHOULD);

				// Occur.SHOULD 或 Occur.Must 并且
				sps.addQuery(booleanQuery);
			}

			// String articleid = map.getString("articleid");
			//
			// if (StringUtil.isNotEmpty(articleid)) {
			// TermQuery q = new TermQuery(new Term("ID", articleid));
			// sps.addQuery(q);
			//
			// }

			sps.addQuery(new WildcardQuery(new Term("WAPPUBLISH", "*" +
					channel + "*")));

			logger.info("检索条件：{}", sps.getBooleanQuery());
			int finalId = 55; //最终检索文件为55
			sps.setIndexID(55);
			SearchResult searchResult = search(sps, map.getString("keyword"));
			return searchResult;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			SearchResult sr = new SearchResult();
			sr.Data = new DataTable();
			return sr;
		}
	}

	private Query searchParam(String keyword, float fenshu, String title) throws Exception {

		QueryParser parser = new QueryParser(Version.LUCENE_CURRENT, title.toUpperCase(), new StandardAnalyzer(
				Version.LUCENE_CURRENT));
		Query q = parser.parse(keyword);
		q.setBoost(fenshu);// 标题中的关键字分数应该较高
		return q;
	}

	/**
	 *
	 * getPeriodInsurance:(获取保险期间或年龄区间--非精确数据，只供显示). <br/>
	 * 展示方式：（最大值-最小值），如 1天-50周岁,0岁-50周岁,1个月-1年。。。。
	 *
	 * @author zhangjing
	 * @param femrList
	 * @return
	 */
	private String getPeriodInsurance(List<String> femrList, boolean Ageflag) {

		List<Integer> list_D = new ArrayList<Integer>();
		List<Integer> list_M = new ArrayList<Integer>();
		List<Integer> list_Y = new ArrayList<Integer>();
		List<Integer> list_A = new ArrayList<Integer>();
		String[] arrValues = new String[] {};

		for (int i = 0; i < femrList.size(); i++) {
			// 保险期间已D、M、Y为单位，
			String values = femrList.get(i);
			if (values.indexOf("L") != -1) {
				return "终身";
			}
			if (values.indexOf("-") != -1) {
				arrValues = values.split("-");
			} else {
				arrValues = new String[] { values };
			}
			for (int j = 0; j < arrValues.length; j++) {
				int tPeriod = 1;
				if (arrValues[j].indexOf("D") != -1) {
					tPeriod = Integer.parseInt(arrValues[j].substring(0, arrValues[j].length() - 1));
					list_D.add(tPeriod);
				} else if (arrValues[j].indexOf("M") != -1) {
					tPeriod = Integer.parseInt(arrValues[j].substring(0, arrValues[j].length() - 1));
					list_M.add(tPeriod);
				} else if (arrValues[j].indexOf("Y") != -1) {
					tPeriod = Integer.parseInt(arrValues[j].substring(0, arrValues[j].length() - 1));
					list_Y.add(tPeriod);
				} else if (arrValues[j].indexOf("A") != -1) {
					tPeriod = Integer.parseInt(arrValues[j].substring(0, arrValues[j].length() - 1));
					list_A.add(tPeriod);
				}
			}
		}
		String start = "";
		String end = "";
		String Y = "年";
		if (Ageflag) {
			Y = "岁";
		}
		boolean flag = false;// A-年龄(岁) 标志
		if (list_A.size() > 0) {
			Collections.sort(list_A);
			end = "-" + list_A.get(list_A.size() - 1) + "周岁";
			start = "0周岁";
			flag = true;
		} else if (list_Y.size() > 0) {
			Collections.sort(list_Y);
			end = "-" + list_Y.get(list_Y.size() - 1) + Y;
		} else if (list_M.size() > 0) {
			Collections.sort(list_M);
			end = "-" + list_M.get(list_M.size() - 1) + "个月";
		} else if (list_D.size() > 0) {
			Collections.sort(list_D);
			end = "-" + list_D.get(list_D.size() - 1) + "天";
		}
		if (!flag && arrValues.length != 1) {
			if (list_D.size() > 0) {
				start = list_D.get(0) + "天";
			} else if (list_M.size() > 0) {
				start = list_M.get(0) + "个月";
			} else if (list_Y.size() > 0) {
				start = "-" + list_Y.get(0) + Y;
			}
		}
		String result = start + end;
		if (result.startsWith("-")) {
			result = result.substring(result.indexOf("-") + 1);
		}
		return result;
	}

	public static String appendSpace(String para) {

		if (StringUtil.isEmpty(para)) {
			return null;
		} 
		char space = ' ';
		String reuslt = para + space;
		for (int i = 0; i < para.length(); i++) {
			reuslt += para.charAt(i) + "" + space;
		}
		return reuslt;
	}

	public static void main(String[] args) {

		

		
		// 更新索引
		 
		//

		// 创建索引
		ProductIndexer pi = new ProductIndexer();
		pi.updateProductIndex(55);
	//	pi.createProductIndex(55);
//		//
		
		Mapx map = new Mapx();
		map.put("page", "1");
		map.put("size", "20");
		map.put("keyword", "百年康惠");
		SearchResult sr = pi.doSearch(map);
		System.out.println("搜索结果总数:" + sr.Total);

		
		
		for (int i = 0; i < sr.Data.getRowCount(); i++) {
			DataRow dr = sr.Data.get(i);
			System.out.println(dr.getString("ID") + "-" +
					dr.getString("productid") + "-" + dr.getString("URL") + "-"
					+ dr.getString("Title") + "-" + dr.getString("AdaptPeopleInfo") + "-"
					+ dr.getString("FEMRiskBrightSpot") + "-" +
					dr.getString("wapPublish") + "-" +
					dr.getString("calhtml"));
		}
	}
	
	
}
