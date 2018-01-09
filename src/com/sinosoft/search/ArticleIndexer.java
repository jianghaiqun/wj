package com.sinosoft.search;

import com.sinosoft.cms.dataservice.ColumnUtil;
import com.sinosoft.cms.document.Article;
import com.sinosoft.cms.pub.CatalogUtil;
import com.sinosoft.cms.pub.SiteUtil;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DBConnPool;
import com.sinosoft.framework.data.DataAccess;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.NumberUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.ZCFullTextSchema;
import com.sinosoft.search.index.IndexUtil;
import com.sinosoft.search.index.Indexer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Date;

/**
 * 文章索引类
 * 
 */
public class ArticleIndexer extends Indexer {
	private long id;
 
	private long finalId =1025;
	private int PageSize = 200;

	public ArticleIndexer(long fullTextID) {
		id = fullTextID;
		setPath(Config.getContextRealPath() + "WEB-INF/data/index/" + this.finalId + "/");
	}

	/**
	 * 第一次需创建索引
	 */
	public void create() {
		try {
			ZCFullTextSchema ft = new ZCFullTextSchema();
			ft.setID(id);
			if (!ft.fill()) {
				return;
			}
			if (!ft.getType().equals("Article")) {// 暂时只支持文章索引
				return;
			}
			if ("true".equals(Config.getValue("App.MinimalMemory"))) {
				PageSize = 50;
			}
			String rela = ft.getRelaText();
			if ("1".equals(ft.getProp1())) {
				if (rela.indexOf("-1") >= 0) {
					QueryBuilder qb = new QueryBuilder(
							"select ID from ZCCatalog where SiteID=?", ft
									.getSiteID());
					DataTable catalogs = qb.executeDataTable();
					for (int j = 0; j < catalogs.getRowCount(); j++) {
						qb = new QueryBuilder(
								"select a.*, b.ProductID,b.ProductName,b.SalesVolume,b.CalHTML2,b.AdaptPeopleInfo,b.FEMRiskBrightSpot,b.DutyHTML,b.prodcutMarkPrice,"
									    +" b.SupplierCode2,b.Popular,b.InitPrem,b.fEMRiskBrightSpotNew,b.DutyHTML2,b.ProductActive,b.LogoLink,b.BasePrem,b.BasePremV3,b.AdaptPeopleInfoV3,"
									    +" b.AdaptPeopleInfoListV3,b.DutyHTMLV3,b.RecomDutyV3,c.ProductType "
										+ " from zcarticle a, SDSearchRelaProduct b,sdproduct c where type ='1' and b.productid=c.productid and a.id = b.prop1 and a.siteid="
										+ ft.getSiteID()
										+ " and status="
										+ Article.STATUS_PUBLISHED
										+ " and catalogid=?", catalogs.getLong(
										j, "ID"));
						int total = DataAccess.getCount(DBConnPool
								.getDBConnConfig().DBType, qb);
						for (int i = 0; i * PageSize < total; i++) {
							DataTable dt = qb
									.executePagedDataTable(PageSize, i);
							CatalogUtil.addCatalogName(dt, "CatalogID");
							ColumnUtil.extendDocColumnData(dt, catalogs
									.getString(j, "ID"));
							add(dt, writer,ft.getProp1());
						}
					}
				} else {
					String[] catalogs = rela.split(",");
					for (int j = 0; j < catalogs.length; j++) {
						QueryBuilder qb = new QueryBuilder(
								"select a.*, b.ProductID,b.ProductName,b.SalesVolume,b.CalHTML2,b.AdaptPeopleInfo,b.FEMRiskBrightSpot,b.DutyHTML,b.prodcutMarkPrice,"
									    +" b.SupplierCode2,b.Popular,b.InitPrem,b.fEMRiskBrightSpotNew,b.DutyHTML2,b.ProductActive,b.LogoLink,b.BasePrem,b.BasePremV3,b.AdaptPeopleInfoV3,"
									    +" b.AdaptPeopleInfoListV3,b.DutyHTMLV3,b.RecomDutyV3,c.ProductType "
										+ " from zcarticle a, SDSearchRelaProduct b,sdproduct c where type ='1' and b.productid=c.productid and a.id = b.prop1 and a.siteid="
										+ ft.getSiteID()
										+ " and a.status="
										+ Article.STATUS_PUBLISHED
										+ " and a.catalogid=?", Long
										.parseLong(catalogs[j]));
						int total = DataAccess.getCount(DBConnPool
								.getDBConnConfig().DBType, qb);
						for (int i = 0; i * PageSize < total; i++) {
							DataTable dt = qb
									.executePagedDataTable(PageSize, i);
							CatalogUtil.addCatalogName(dt, "CatalogID");
							ColumnUtil.extendDocColumnData(dt, catalogs[j]);
							add(dt, writer,ft.getProp1());
						}
					}
				}
			}else if("2".equals(ft.getProp1())){
				if (rela.indexOf("-1") >= 0) {
					QueryBuilder qb = new QueryBuilder(
							"select ID from ZCCatalog where SiteID=?", ft
									.getSiteID());
					DataTable catalogs = qb.executeDataTable();
					for (int j = 0; j < catalogs.getRowCount(); j++) {
						qb = new QueryBuilder(
								"select * from zcarticle where siteid="
										+ ft.getSiteID() + " and status="
										+ Article.STATUS_PUBLISHED
										+ " and catalogid=?", catalogs.getLong(
										j, "ID"));
						int total = DataAccess.getCount(DBConnPool
								.getDBConnConfig().DBType, qb);
						for (int i = 0; i * PageSize < total; i++) {
							DataTable dt = qb
									.executePagedDataTable(PageSize, i);
							CatalogUtil.addCatalogName(dt, "CatalogID");
							ColumnUtil.extendDocColumnData(dt, catalogs
									.getString(j, "ID"));
							add(dt, writer,ft.getProp1());
						}
					}
				} else {
					String[] catalogs = rela.split(",");
					for (int j = 0; j < catalogs.length; j++) {
						QueryBuilder qb = new QueryBuilder(
								"select * from zcarticle where siteid="
										+ ft.getSiteID() + " and status="
										+ Article.STATUS_PUBLISHED
										+ " and catalogid=?", Long
										.parseLong(catalogs[j]));
						int total = DataAccess.getCount(DBConnPool
								.getDBConnConfig().DBType, qb);
						for (int i = 0; i * PageSize < total; i++) {
							DataTable dt = qb
									.executePagedDataTable(PageSize, i);
							CatalogUtil.addCatalogName(dt, "CatalogID");
							ColumnUtil.extendDocColumnData(dt, catalogs[j]);
							add(dt, writer,ft.getProp1());
						}
					}
				}
			} else {
				if (rela.indexOf("-1") >= 0) {
					QueryBuilder qb = new QueryBuilder(
							"select ID from ZCCatalog where SiteID=?", ft
									.getSiteID());
					DataTable catalogs = qb.executeDataTable();
					for (int j = 0; j < catalogs.getRowCount(); j++) {
						qb = new QueryBuilder(
								"select * from zcarticle where siteid="
										+ ft.getSiteID() + " and status="
										+ Article.STATUS_PUBLISHED
										+ " and catalogid=?", catalogs.getLong(
										j, "ID"));
						int total = DataAccess.getCount(DBConnPool
								.getDBConnConfig().DBType, qb);
						for (int i = 0; i * PageSize < total; i++) {
							DataTable dt = qb
									.executePagedDataTable(PageSize, i);
							CatalogUtil.addCatalogName(dt, "CatalogID");
							ColumnUtil.extendDocColumnData(dt, catalogs
									.getString(j, "ID"));
							add(dt, writer,ft.getProp1());
						}
					}
				} else {
					String[] catalogs = rela.split(",");
					for (int j = 0; j < catalogs.length; j++) {
						QueryBuilder qb = new QueryBuilder(
								"select * from zcarticle where siteid="
										+ ft.getSiteID() + " and status="
										+ Article.STATUS_PUBLISHED
										+ " and catalogid=?", Long
										.parseLong(catalogs[j]));
						int total = DataAccess.getCount(DBConnPool
								.getDBConnConfig().DBType, qb);
						for (int i = 0; i * PageSize < total; i++) {
							DataTable dt = qb
									.executePagedDataTable(PageSize, i);
							CatalogUtil.addCatalogName(dt, "CatalogID");
							ColumnUtil.extendDocColumnData(dt, catalogs[j]);
							add(dt, writer,ft.getProp1());
						}
					}
				}
			}
		} catch (CorruptIndexException e) {
			logger.error(e.getMessage(), e);
		} catch (LockObtainFailedException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	private void index(DataTable dt, IndexWriter writer, boolean updateFlag ,String prop1)
			throws CorruptIndexException, IOException {
		Field[] fields = new Field[dt.getColCount() + 1];
		for (int n = 0; n < dt.getColCount(); n++) {
			String columnName = dt.getDataColumn(n).getColumnName();
			if (columnName.equalsIgnoreCase("URL")) {
				fields[n] = new Field("URL", "", Field.Store.YES,
						Field.Index.NO);
			} else if (columnName.equalsIgnoreCase("TITLE")) {
				if("2".equals(prop1)){
					fields[n] = new Field("TITLE", "", Field.Store.YES, Field.Index.TOKENIZED, Field.TermVector.WITH_POSITIONS_OFFSETS);
				}else{
					fields[n] = new Field("TITLE", "", Field.Store.YES,
							Field.Index.ANALYZED);
				}
			} else if (columnName.equalsIgnoreCase("Content")) {
				if("2".equals(prop1)){
					fields[n] = new Field("CONTENT", "", Field.Store.YES, Field.Index.TOKENIZED, Field.TermVector.WITH_POSITIONS_OFFSETS);
				}else{
					fields[n] = new Field("CONTENT", "", Field.Store.YES,
							Field.Index.ANALYZED);
				}
			} else if (columnName.equalsIgnoreCase("Tag")) {
				fields[n] = new Field("TAG", "", Field.Store.YES,
						Field.Index.ANALYZED);
			} else if (columnName.equalsIgnoreCase("AdaptPeopleInfo")) {
				fields[n] = new Field("ADAPTPEOPLEINFO", "", Field.Store.YES,
						Field.Index.ANALYZED);
			} else if (columnName.equalsIgnoreCase("FEMRiskBrightSpot")) {
				fields[n] = new Field("FEMRISKBRIGHTSPOT", "", Field.Store.YES,
						Field.Index.ANALYZED);
			} else {
				fields[n] = new Field(columnName.toUpperCase(), "",
						Field.Store.YES, Field.Index.NOT_ANALYZED);
			}
		}
		fields[fields.length - 1] = new Field("_KEYWORD", "", Field.Store.NO,
				Field.Index.ANALYZED);
		for (int i = 0; i < dt.getRowCount(); i++) {
			Document doc = new Document();
			DataRow dr = dt.getDataRow(i);
			String content = IndexUtil.getTextFromHtml(dr.getString("Content"));

			Date d1 = dr.getDate("AddTime");
			Date d2 = dr.getDate("ModifyTime");
			if (d1 != null && d1.getTime() > nextLastDate.getTime()) {
				nextLastDate = d1;
			}
			if (d2 != null && d2.getTime() > nextLastDate.getTime()) {
				nextLastDate = d2;
			}

			StringBuffer sb = new StringBuffer();
			sb.append(dr.getString("Title"));
			sb.append(" ");
			sb.append(content);
			try {
				// String url = PubFun.getDocURL(dr);
				String url = "";
				String ReferType = dr.getString("ReferType");
				if (ReferType.equals("1")) {
					url = dr.getString("url");
				} else if (ReferType.equals("2")) {
					if (StringUtil.isNotEmpty(dr.getString("RedirectURL"))) {
						// url = getPageId(dr.getString("RedirectURL"));
						continue;
					}
				} else {
					url = dr.getString("url");
				}
				url = url.replaceAll("/+", "/");
				if (StringUtil.isEmpty(url)) {
					continue;
				}
				for (int n = 0; n < dt.getColCount(); n++) {
					String columnName = dt.getDataColumn(n).getColumnName();
					Field field = fields[n];
					if (columnName.equalsIgnoreCase("URL")) {
						field.setValue(url);
					} else if (columnName.equalsIgnoreCase("Content")) {
						field.setValue(content);
					} else {
						field.setValue(dr.getString(n));
					}
					doc.add(field);
				}
				Field field = fields[fields.length - 1];
				field.setValue(sb.toString());
				doc.add(field);
				if (updateFlag
						&& StringUtil.isNotEmpty(dt.getString(i, "ModifyTime"))) {
					writer.updateDocument(
							new Term("ID", dt.getString(i, "ID")), doc);
				} else {
					writer.addDocument(doc);
				}
			} catch (Throwable t) {
				logger.error(t.getMessage(), t);
			}
		}
	}

	// private static String getPageId(String url){
	//		
	// return url.split("/",4)[3];
	//
	// }

	private void add(DataTable dt, IndexWriter writer , String prop1)
			throws CorruptIndexException, IOException {
		index(dt, writer, false ,prop1);
	}

	private void update(DataTable dt, IndexWriter writer, String prop1)
			throws CorruptIndexException, IOException {
		index(dt, writer, true,prop1);
	}

	/**
	 * 更新索引
	 */
	public void update() {
		try {
			ZCFullTextSchema ft = new ZCFullTextSchema();
			ft.setID(id);
			if (!ft.fill()) {
				return;
			}
			if (!ft.getType().equals("Article")) {// 暂时只支持文章索引
				return;
			}
			if ("true".equals(Config.getValue("App.MinimalMemory"))) {
				PageSize = 50;
			}
			String rela = ft.getRelaText();
			String prop1 = ft.getProp1();
			if (rela.indexOf("-1") >= 0) {
				DataTable catalogs = new QueryBuilder(
						"select id from zccatalog where siteid = "
								+ ft.getSiteID()
								+ " and exists (select '' from zcarticle where zcarticle.catalogid=zccatalog.id and siteid="
								+ ft.getSiteID() + " and (addtime>=?"
								+ " or modifytime>=?) and status="
								+ Article.STATUS_PUBLISHED + ")", lastDate,
						lastDate).executeDataTable();
				for (int j = 0; j < catalogs.getRowCount(); j++) {
					QueryBuilder qb = new QueryBuilder(
							"select * from zcarticle where siteid="
									+ ft.getSiteID()
									+ " and CatalogID=? and (addtime>=? or modifytime>=?) and status="
									+ Article.STATUS_PUBLISHED, catalogs
									.getLong(j, "id"), lastDate);
					qb.add(lastDate);
					int total = DataAccess.getCount(DBConnPool
							.getDBConnConfig().DBType, qb);
					for (int i = 0; i * PageSize < total; i++) {
						DataTable dt = qb.executePagedDataTable(PageSize, i);
						CatalogUtil.addCatalogName(dt, "CatalogID");
						ColumnUtil.extendDocColumnData(dt, catalogs.getLong(j,
								"id"));
						update(dt, writer,prop1);
					}
				}
				DataTable dt = new QueryBuilder(
						"select id from bzcarticle where status="
								+ Article.STATUS_PUBLISHED
								+ " and siteid=? and backuptime>?", ft
								.getSiteID(), lastDate).executeDataTable();
				for (int i = 0; i < dt.getRowCount(); i++) {
					writer.deleteDocuments(new Term("ID", dt.getString(i, 0)));
				}
				dt = new QueryBuilder("select id from zcarticle where status="
						+ Article.STATUS_OFFLINE
						+ " and siteid=? and modifytime>?", ft.getSiteID(),
						lastDate).executeDataTable();
				for (int i = 0; i < dt.getRowCount(); i++) {
					writer.deleteDocuments(new Term("ID", dt.getString(i, 0)));
				}
			} else {
				String[] catalogs = rela.split(",");
				for (int j = 0; j < catalogs.length; j++) {
					QueryBuilder qb = new QueryBuilder(
							"select * from zcarticle where status="
									+ Article.STATUS_PUBLISHED
									+ " and catalogid=? and (addtime>=? or modifytime>=?)",
							Long.parseLong(catalogs[j]), lastDate);
					qb.add(lastDate);
					int total = DataAccess.getCount(DBConnPool
							.getDBConnConfig().DBType, qb);
					for (int i = 0; i * PageSize < total; i++) {
						DataTable dt = qb.executePagedDataTable(PageSize, i);
						CatalogUtil.addCatalogName(dt, "CatalogID");
						ColumnUtil.extendDocColumnData(dt, catalogs[j]);
						update(dt, writer,prop1);
					}
				}
				DataTable dt = new QueryBuilder(
						"select id from bzcarticle where status="
								+ Article.STATUS_PUBLISHED
								+ " and siteid=? and catalogid in (" + rela
								+ ") and backuptime>?", ft.getSiteID(),
						lastDate).executeDataTable();
				for (int i = 0; i < dt.getRowCount(); i++) {
					writer.deleteDocuments(new Term("ID", dt.getString(i, 0)));
				}
				dt = new QueryBuilder("select id from zcarticle where status="
						+ Article.STATUS_OFFLINE
						+ " and siteid=? and modifytime>?", ft.getSiteID(),
						lastDate).executeDataTable();
				for (int i = 0; i < dt.getRowCount(); i++) {
					writer.deleteDocuments(new Term("ID", dt.getString(i, 0)));
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static void main(String[] args) {
		new ArticleIndexer(20).update();
	}
	/**
	 * 无产品返回时调用，返回推荐产品
	 * @return
	 */
	private DataTable NoResult() { 
		try {
			ZCFullTextSchema ft = new ZCFullTextSchema();
			ft.setID(id);
			if (!ft.fill()) {
				return null;
			}
			QueryBuilder qb = new QueryBuilder(
					"select a.*, c.ProductID RiskCode,b.ProductID,b.ProductName,b.SalesVolume,b.CalHTML2,b.AdaptPeopleInfo,b.FEMRiskBrightSpot,b.DutyHTML,b.prodcutMarkPrice,"
						    +" b.SupplierCode2,b.Popular,b.InitPrem,b.fEMRiskBrightSpotNew,b.DutyHTML2,b.ProductActive,b.LogoLink,b.BasePrem,b.BasePremV3,b.AdaptPeopleInfoV3,"
						    +" b.AdaptPeopleInfoListV3,b.DutyHTMLV3,b.RecomDutyV3,c.ProductType "
							+ " from zcarticle a,SDSearchRelaProduct b,sdproduct c where type ='1' and b.productid=c.productid and a.id=b.prop1 and a.siteid=? and a.status=? and a.Attribute like '%hp_recommend%'"
							+ "and a.cataloginnercode like '002313%' limit 10");
			qb.add(ft.getSiteID());
			qb.add(Article.STATUS_PUBLISHED);
			DataTable dt = qb.executeDataTable();
			return dt;
		} catch (Exception e) {
			logger.error("无产品搜索结果方法异常:"+e.getMessage(), e);
		}
		return null;
	}
	/**
	 * 无搜索结果时调用
	 * @return
	 */
	private DataTable NoResult1() { 
		try {
			ZCFullTextSchema ft = new ZCFullTextSchema();
			ft.setID(id);
			if (!ft.fill()) {
				return null;
			}
			String innerCode = "";
			if(id==51){
				innerCode = "002306%";
			}else if(id==52){
				innerCode = "002308%";
			}else if(id==53){
				innerCode = "002307%";
			}
			QueryBuilder qb = new QueryBuilder(
					" select * from zcarticle where cataloginnercode like '"+innerCode+"' and siteid="+ ft.getSiteID() +" and status="+ Article.STATUS_PUBLISHED + " order by publishDate desc limit 5");
			DataTable dt = qb.executeDataTable();
			return dt;
		} catch (Exception e) {
			logger.error("无文章搜索结果方法异常："+e.getMessage(), e);
		}
		return null;
	}
	
	public static SearchResult search(SearchParameters sps) {
		FSDirectory directory = null;// dirrctory 索引储存在磁盘上的位置
		IndexReader reader = null;// 
		IndexSearcher searcher = null;// IndexSearcher 用于在建立好的索引上进行搜索的句柄类
		SimpleHTMLFormatter formatter = null;
		Highlighter highlighter = null;
		try {
			long t = System.currentTimeMillis();
			directory = FSDirectory.getDirectory(new File(Config
					.getContextRealPath()
					+ "WEB-INF/data/index/" + sps.getIndexID()));
			reader = IndexReader.open(directory, true);
			searcher = new IndexSearcher(reader);// 生成检索器

			if (sps.getBooleanQuery().clauses().size() == 0) {// BooleanQuery逻辑组合检索
				MatchAllDocsQuery maq = new MatchAllDocsQuery();
				sps.addQuery(maq);
			}
			if (sps.getIndexID() == 51 || sps.getIndexID() == 52
					|| sps.getIndexID() == 53) { // 侧边栏知识、问答、资讯不加颜色
				formatter = new SimpleHTMLFormatter("", "");
			} else {
				formatter = new SimpleHTMLFormatter("<font color=red>",
						"</font>");
			}
			highlighter = new Highlighter(formatter, new QueryScorer(sps
					.getBooleanQuery()));

			int abstractLength = 150;
			highlighter.setTextFragmenter(new SimpleFragmenter(abstractLength));// 设置语句高亮
			DataTable dt = new DataTable();

			int start = sps.getPageIndex() * sps.getPageSize();
			TopDocs docs = null;// 搜索结果的集合
			if (sps.getSort() != null) {
				// 利用参数控制检索（检索方式，对结果进行过滤的方式，权重，排序方式）
				docs = searcher.search(sps.getBooleanQuery(), sps
						.getDateRangeFilter(), start + sps.getPageSize(), sps
						.getSort());
			} else {
				docs = searcher.search(sps.getBooleanQuery(), sps
						.getDateRangeFilter(), start + sps.getPageSize());
			}

			for (int i = start; i < start + sps.getPageSize()
					&& i < docs.scoreDocs.length; i++) {
				Document doc = searcher.doc(docs.scoreDocs[i].doc);// 返回第几个记录
				if (i == start) {
					Object[] fields = doc.getFields().toArray();
					for (int j = 0; j < fields.length; j++) {
						String name = ((Field) fields[j]).name();
						if ("TAG".equals(name)) {
							// 2013/3/12 于善春修改运行中报“TAG1”错误 del
							// ->dt.insertColumn("TAG1");
							// 2013/3/12 于善春修改运行中报“TAG1”错误,追加非空判断 add start
							// String value=((Field) fields[j]).stringValue();
							// if(StringUtil.isNotEmpty(value)){
							if (!dt.containsColumn("TAG1")) {
								dt.insertColumn("TAG1");
							}
							// }
							// 2013/3/12 于善春修改运行中报“TAG1”错误,追加非空判断 add end
						}
						if (!dt.containsColumn(name)) {
							dt.insertColumn(name);
						}
					}
				}
				String title = doc.get("TITLE");
				TokenStream tokenStream = new IKAnalyzer().tokenStream("TITLE",
						new StringReader(title));
				// System.out.println(title);
				String tmp = highlighter.getBestFragment(tokenStream, title);
				if (StringUtil.isNotEmpty(tmp)) {
					title = tmp;
				}

				String tag = doc.get("TAG");
				tokenStream = new IKAnalyzer().tokenStream("TAG",
						new StringReader(tag));
				// System.out.println(title);
				tmp = highlighter.getBestFragment(tokenStream, tag);
				if (StringUtil.isNotEmpty(tmp)) {
					tag = tmp;
				}

				String content = doc.get("CONTENT");
				content = StringUtil.replaceEx(content, "<", "&lt;");
				content = StringUtil.replaceEx(content, ">", "&gt;");
				tokenStream = new IKAnalyzer().tokenStream("CONTENT",
						new StringReader(content));
				tmp = highlighter.getBestFragment(tokenStream, content);
				if (StringUtil.isNotEmpty(tmp)) {
					content = tmp.trim();
				} else {
					if (content.length() > abstractLength) {
						int index1 = content.indexOf("</", abstractLength);
						int index2 = content.indexOf("<", abstractLength);
						int index3 = content.indexOf(">", abstractLength);
						if (index1 >= 0 && index1 == index2) {// 说明最近一个的标签是</font>
							content = content.substring(0, index3 + 1);
						} else if (index3 >= 0 && index3 < index2) {// 说明从</font>中间拆开了,并且后面还有标签
							content = content.substring(0, index3 + 1);
						} else if (index3 >= 0 && index2 < 0) {// 说明从</font>中间拆开了,并且后面再也没有标签了
							content = content.substring(0, index3 + 1);
						} else {
							content = content.substring(0, abstractLength);
						}
					}
				}

				dt.insertRow(new String[dt.getColCount()]);
				for (int j = 0; j < dt.getColCount(); j++) {
					if ("TAG1".equals(dt.getDataColumn(j).getColumnName())) {// 为tag使用
						dt.set(i - start, j, doc.get("TAG"));
					} else {
						dt.set(i - start, j, doc.get(dt.getDataColumn(j)
								.getColumnName()));
					}
				}
				dt.set(i - start, "TITLE", title);
				dt.set(i - start, "TAG", tag);
				dt.set(i - start, "CONTENT", content);
				String url = dt.getString(i - start, "URL");
				if (url.indexOf("://") < 0) {
					String siteUrl = SiteUtil.getURL(dt.getString(i - start,
							"SITEID"));
					if (!siteUrl.endsWith("/")) {
						siteUrl = siteUrl + "/";
					}
					if (url.startsWith("/")) {
						url = url.substring(1);
					}
					url = siteUrl + url;
				}
				dt.set(i - start, "URL", url);
			}
			SearchResult r = new SearchResult();
			if ((sps.getIndexID() == 51 || sps.getIndexID() == 52|| sps.getIndexID() == 53)&&(dt.getRowCount()==0)) {
				ArticleIndexer indexer = new ArticleIndexer(sps.getIndexID());
				DataTable dt1 = indexer.NoResult1();
				for(int k=0;k<dt1.getRowCount();k++){
					String url = dt1.getString(k, "URL");
					if (url.indexOf("://") < 0) {
						String siteUrl = SiteUtil.getURL(dt1.getString(k,
								"SITEID"));
						if (!siteUrl.endsWith("/")) {
							siteUrl = siteUrl + "/";
						}
						if (url.startsWith("/")) {
							url = url.substring(1);
						}
						url = siteUrl + url;
					}
					dt1.set(k, "URL", url);
				}
				r.Data = dt1;
			}else{
				r.Data = dt;
			}
			r.Total = docs.totalHits;
			r.UsedTime = NumberUtil.round(
					(System.currentTimeMillis() - t) * 1.0 / 1000, 3);
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
	 * 查询置顶个数
	 * @param r 搜索结果
	 * @return
	 */
	public static SearchResult proSearch1(SearchResult r) {				

	try{
		DataTable dt1 = new DataTable();
		QueryBuilder qb = new QueryBuilder(
				"select a.*,b.SalesVolume as SalesVolume,b.CalHTML2 as CalHTML2,b.AdaptPeopleInfo as AdaptPeopleInfo,b.FEMRiskBrightSpot as FEMRiskBrightSpot,"
						+ "b.DutyHTML as DutyHTML,b.prodcutMarkPrice as prodcutMarkPrice,b.SupplierCode2 as SupplierCode2,b.productId as riskcode  "
						+ " from zcarticle a,SDSearchRelaProduct b where type ='1' and a.id=b.prop1 "
						+ " and a.status=? and a.topflag = '1' and a.cataloginnercode like '002313%'");
		qb.add(Article.STATUS_PUBLISHED);
		DataTable dt = qb.executeDataTable();
		for(int n=0;n<r.Data.getColCount();n++){							
			if (!dt1.containsColumn(r.Data.getColumnName(n))) {
				dt1.insertColumn(r.Data.getColumnName(n));
			}
		}
		for(int i=0;i<dt.getRowCount();i++){								//将置顶的插入到dt1中
			for(int j=0;j<r.Data.getRowCount();j++){
				if(r.Data.getString(j, "RiskCode").equals(dt.getDataRow(i).get("RiskCode"))){
					dt1.insertRow(r.Data.getDataRow(j));
					r.Data.deleteRow(j);
				}
			}
		}
		for(int k=0;k<r.Data.getRowCount();k++){						//将剩下的未置顶的插入到dt1中
			dt1.insertRow(r.Data.getDataRow(k));
		}
//		User.setValue("", "");
		r.Data = dt1;
	}catch(Exception e){
		logger.error("取置顶方法异常："+e.getMessage(), e);
	}
		return r;
	}
	/**
	 * 产品搜索单独调用方法 
	 * @param sps
	 * @return
	 */
	public static SearchResult proSearch(SearchParameters sps) {
		/* 产品搜索单独调用方法 */
		FSDirectory directory = null;// dirrctory 索引储存在磁盘上的位置
		IndexReader reader = null;// 
		IndexSearcher searcher = null;// IndexSearcher 用于在建立好的索引上进行搜索的句柄类
		SimpleHTMLFormatter formatter = null;
		Highlighter highlighter = null;
		try {
			long t = System.currentTimeMillis();
			directory = FSDirectory.getDirectory(new File(Config
					.getContextRealPath()
					+ "WEB-INF/data/index/" + sps.getIndexID()));
			reader = IndexReader.open(directory, true);
			searcher = new IndexSearcher(reader);// 生成检索器
			searcher.setSimilarity(new ProductSimilarity());
			if (sps.getBooleanQuery().clauses().size() == 0) {// BooleanQuery逻辑组合检索
				MatchAllDocsQuery maq = new MatchAllDocsQuery();
				sps.addQuery(maq);
			}

			formatter = new SimpleHTMLFormatter("<font color=#E84720>",
					"</font>"); // 产品部分颜色为#E84720
			highlighter = new Highlighter(formatter, new QueryScorer(sps
					.getBooleanQuery()));
			int abstractLength = 500;
			highlighter.setTextFragmenter(new SimpleFragmenter(abstractLength));// 设置语句高亮
			DataTable dt = new DataTable();

			// int start = sps.getPageIndex() * sps.getPageSize();
			int start = 0;
			TopDocs docs = null;// 搜索结果的集合
			if (sps.getSort() != null) {
				// 利用参数控制检索（检索方式，对结果进行过滤的方式，权重，排序方式）
				docs = searcher.search(sps.getBooleanQuery(), sps
						.getDateRangeFilter(), 1000000, sps.getSort());
			} else {
				docs = searcher.search(sps.getBooleanQuery(), sps
						.getDateRangeFilter(), 1000000);
			}

			for (int i = start; i < docs.scoreDocs.length; i++) {
				Document doc = searcher.doc(docs.scoreDocs[i].doc);// 返回第几个记录		
//System.out.println(doc.get("TITLE").toString()+":"+docs.scoreDocs[i].score);	
				if (i == start) {
					Object[] fields = doc.getFields().toArray();
					for (int j = 0; j < fields.length; j++) {
						String name = ((Field) fields[j]).name();
						if ("TAG".equals(name)) {
							// 2013/3/12 于善春修改运行中报“TAG1”错误 del
							// ->dt.insertColumn("TAG1");
							// 2013/3/12 于善春修改运行中报“TAG1”错误,追加非空判断 add start
							// String value=((Field) fields[j]).stringValue();
							// if(StringUtil.isNotEmpty(value)){
							if (!dt.containsColumn("TAG1")) {
								dt.insertColumn("TAG1");
							}
							// }
							// 2013/3/12 于善春修改运行中报“TAG1”错误,追加非空判断 add end
						}
						if (!dt.containsColumn(name)) {
							dt.insertColumn(name);
						}
					}
				}
				String title = doc.get("TITLE");
				TokenStream tokenStream = new StandardAnalyzer(
						Version.LUCENE_29).tokenStream("TITLE",
						new StringReader(title));
				// System.out.println(title);
				String tmp = highlighter.getBestFragment(tokenStream, title);
				if (StringUtil.isNotEmpty(tmp)) {
					title = tmp;
				}

				String tag = doc.get("TAG");
				tokenStream = new StandardAnalyzer(Version.LUCENE_29)
						.tokenStream("TAG", new StringReader(tag));
				// System.out.println(title);
				tmp = highlighter.getBestFragment(tokenStream, tag);
				if (StringUtil.isNotEmpty(tmp)) {
					tag = tmp;
				}

				String content = doc.get("CONTENT");
				content = StringUtil.replaceEx(content, "<", "&lt;");
				content = StringUtil.replaceEx(content, ">", "&gt;");
				tokenStream = new StandardAnalyzer(Version.LUCENE_29)
						.tokenStream("CONTENT", new StringReader(content));
				tmp = highlighter.getBestFragment(tokenStream, content);
				if (StringUtil.isNotEmpty(tmp)) {
					content = tmp.trim();
				} else {
					if (content.length() > abstractLength) {
						int index1 = content.indexOf("</", abstractLength);
						int index2 = content.indexOf("<", abstractLength);
						int index3 = content.indexOf(">", abstractLength);
						if (index1 >= 0 && index1 == index2) {// 说明最近一个的标签是</font>
							content = content.substring(0, index3 + 1);
						} else if (index3 >= 0 && index3 < index2) {// 说明从</font>中间拆开了,并且后面还有标签
							content = content.substring(0, index3 + 1);
						} else if (index3 >= 0 && index2 < 0) {// 说明从</font>中间拆开了,并且后面再也没有标签了
							content = content.substring(0, index3 + 1);
						} else {
							content = content.substring(0, abstractLength);
						}
					}
				}
				String adaptpeopleinfo = doc.get("ADAPTPEOPLEINFO");
				tokenStream = new StandardAnalyzer(Version.LUCENE_29)
						.tokenStream("ADAPTPEOPLEINFO", new StringReader(
								adaptpeopleinfo));
				tmp = highlighter.getBestFragment(tokenStream, adaptpeopleinfo);
				if (StringUtil.isNotEmpty(tmp)) {
					adaptpeopleinfo = tmp;
				}

				String femriskbrightspot = doc.get("FEMRISKBRIGHTSPOT");
				tokenStream = new StandardAnalyzer(Version.LUCENE_29)
						.tokenStream("FEMRISKBRIGHTSPOT", new StringReader(
								femriskbrightspot));
				tmp = highlighter.getBestFragment(tokenStream,
						femriskbrightspot);
				if (StringUtil.isNotEmpty(tmp)) {
					femriskbrightspot = tmp;
				}

				dt.insertRow(new String[dt.getColCount()]);
				for (int j = 0; j < dt.getColCount(); j++) {
					if ("TAG1".equals(dt.getDataColumn(j).getColumnName())) {// 为tag使用
						dt.set(i - start, j, doc.get("TAG"));
					} else {
						dt.set(i - start, j, doc.get(dt.getDataColumn(j)
								.getColumnName()));
					}
				}
				dt.set(i - start, "TITLE", title);
				dt.set(i - start, "TAG", tag);
				dt.set(i - start, "CONTENT", content);
				String url = dt.getString(i - start, "URL");
				dt.set(i - start, "ADAPTPEOPLEINFO", adaptpeopleinfo);
				dt.set(i - start, "FEMRISKBRIGHTSPOT", femriskbrightspot);
				if (url.indexOf("://") < 0) {
					String siteUrl = SiteUtil.getURL(dt.getString(i - start,
							"SITEID"));
					if (!siteUrl.endsWith("/")) {
						siteUrl = siteUrl + "/";
					}
					if (url.startsWith("/")) {
						url = url.substring(1);
					}
					url = siteUrl + url;
				}
				dt.set(i - start, "URL", url);
			}
			SearchResult r = new SearchResult();
			if (dt.getRowCount() == 0) { // 无产品结果返回
				ArticleIndexer indexer = new ArticleIndexer(50);
				DataTable dt1 = indexer.NoResult();
				for(int k=0;k<dt1.getRowCount();k++){
					String url = dt1.getString(k, "URL");
					if (url.indexOf("://") < 0) {
						String siteUrl = SiteUtil.getURL(dt1.getString(k,
								"SITEID"));
						if (!siteUrl.endsWith("/")) {
							siteUrl = siteUrl + "/";
						}
						if (url.startsWith("/")) {
							url = url.substring(1);
						}
						url = siteUrl + url;
					}
					dt1.set(k, "URL", url);
				}
				r.Data = dt1;
				r.NoResult = 1; // 无产品时置为1
			} else {
				r.Data = dt;
			}
			
			//User.setValue("dt", r.Data);
			r.Total = docs.totalHits;
			r.UsedTime = NumberUtil.round(
					(System.currentTimeMillis() - t) * 1.0 / 1000, 3);
			//TODO 置顶
//			if(r.NoResult!=1){
//				r = proSearch1(r);
//			}
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
	 * 知识、资讯、问答单独调用方法
	 * @param sps
	 * @return
	 */
	public static SearchResult leftSearch(SearchParameters sps) {
		FSDirectory directory = null;// dirrctory 索引储存在磁盘上的位置
		IndexReader reader = null;// 
		IndexSearcher searcher = null;// IndexSearcher 用于在建立好的索引上进行搜索的句柄类
		SimpleHTMLFormatter formatter = null;
		Highlighter highlighter = null;
		try {
			long t = System.currentTimeMillis();
			directory = FSDirectory.getDirectory(new File(Config
					.getContextRealPath()
					+ "WEB-INF/data/index/" + sps.getIndexID()));
			reader = IndexReader.open(directory, true);
			searcher = new IndexSearcher(reader);// 生成检索器
			searcher.setSimilarity(new ProductSimilarity());
			if (sps.getBooleanQuery().clauses().size() == 0) {// BooleanQuery逻辑组合检索
				MatchAllDocsQuery maq = new MatchAllDocsQuery();
				sps.addQuery(maq,false);
			}
			formatter = new SimpleHTMLFormatter("", "");
			highlighter = new Highlighter(formatter, new QueryScorer(sps
					.getBooleanQuery()));

			int abstractLength = 150;
			highlighter.setTextFragmenter(new SimpleFragmenter(abstractLength));// 设置语句高亮
			DataTable dt = new DataTable();

			int start = sps.getPageIndex() * sps.getPageSize();
			TopDocs docs = null;// 搜索结果的集合
			if (sps.getSort() != null) {
				// 利用参数控制检索（检索方式，对结果进行过滤的方式，权重，排序方式）
				docs = searcher.search(sps.getBooleanQuery(), sps
						.getDateRangeFilter(), start + sps.getPageSize(), sps
						.getSort());
			} else {
				docs = searcher.search(sps.getBooleanQuery(), sps
						.getDateRangeFilter(), start + sps.getPageSize());
			}

			for (int i = start; i < start + sps.getPageSize()
					&& i < docs.scoreDocs.length; i++) {
				Document doc = searcher.doc(docs.scoreDocs[i].doc);// 返回第几个记录
//System.out.println(doc.get("TITLE").toString()+":"+docs.scoreDocs[i].score);				
				if (i == start) {
					Object[] fields = doc.getFields().toArray();
					for (int j = 0; j < fields.length; j++) {
						String name = ((Field) fields[j]).name();
						if ("TAG".equals(name)) {
							// 2013/3/12 于善春修改运行中报“TAG1”错误 del
							// ->dt.insertColumn("TAG1");
							// 2013/3/12 于善春修改运行中报“TAG1”错误,追加非空判断 add start
							// String value=((Field) fields[j]).stringValue();
							// if(StringUtil.isNotEmpty(value)){
							if (!dt.containsColumn("TAG1")) {
								dt.insertColumn("TAG1");
							}
							// }
							// 2013/3/12 于善春修改运行中报“TAG1”错误,追加非空判断 add end
						}
						if (!dt.containsColumn(name)) {
							dt.insertColumn(name);
						}
					}
				}
				String title = doc.get("TITLE");
				TokenStream tokenStream = new IKAnalyzer().tokenStream("TITLE",
						new StringReader(title));
				// System.out.println(title);
				String tmp = highlighter.getBestFragment(tokenStream, title);
				if (StringUtil.isNotEmpty(tmp)) {
					title = tmp;
				}

				String tag = doc.get("TAG");
				tokenStream =  new IKAnalyzer().tokenStream("TAG",
						new StringReader(tag));
				// System.out.println(title);
				tmp = highlighter.getBestFragment(tokenStream, tag);
				if (StringUtil.isNotEmpty(tmp)) {
					tag = tmp;
				}

				String content = doc.get("CONTENT");
				content = StringUtil.replaceEx(content, "<", "&lt;");
				content = StringUtil.replaceEx(content, ">", "&gt;");
				tokenStream = new IKAnalyzer().tokenStream("CONTENT",
						new StringReader(content));
				tmp = highlighter.getBestFragment(tokenStream, content);
				if (StringUtil.isNotEmpty(tmp)) {
					content = tmp.trim();
				} else {
					if (content.length() > abstractLength) {
						int index1 = content.indexOf("</", abstractLength);
						int index2 = content.indexOf("<", abstractLength);
						int index3 = content.indexOf(">", abstractLength);
						if (index1 >= 0 && index1 == index2) {// 说明最近一个的标签是</font>
							content = content.substring(0, index3 + 1);
						} else if (index3 >= 0 && index3 < index2) {// 说明从</font>中间拆开了,并且后面还有标签
							content = content.substring(0, index3 + 1);
						} else if (index3 >= 0 && index2 < 0) {// 说明从</font>中间拆开了,并且后面再也没有标签了
							content = content.substring(0, index3 + 1);
						} else {
							content = content.substring(0, abstractLength);
						}
					}
				}

				dt.insertRow(new String[dt.getColCount()]);
				for (int j = 0; j < dt.getColCount(); j++) {
					if ("TAG1".equals(dt.getDataColumn(j).getColumnName())) {// 为tag使用
						dt.set(i - start, j, doc.get("TAG"));
					} else {
						dt.set(i - start, j, doc.get(dt.getDataColumn(j)
								.getColumnName()));
					}
				}
				dt.set(i - start, "TITLE", title);
				dt.set(i - start, "TAG", tag);
				dt.set(i - start, "CONTENT", content);
				String url = dt.getString(i - start, "URL");
				if (url.indexOf("://") < 0) {
					String siteUrl = SiteUtil.getURL(dt.getString(i - start,
							"SITEID"));
					if (!siteUrl.endsWith("/")) {
						siteUrl = siteUrl + "/";
					}
					if (url.startsWith("/")) {
						url = url.substring(1);
					}
					url = siteUrl + url;
				}
				dt.set(i - start, "URL", url);
			}
			SearchResult r = new SearchResult();
			if ((dt.getRowCount()==0)) {
				ArticleIndexer indexer = new ArticleIndexer(sps.getIndexID());
				DataTable dt1 = indexer.NoResult1();
				for(int k=0;k<dt1.getRowCount();k++){
					String url = dt1.getString(k, "URL");
					if (url.indexOf("://") < 0) {
						String siteUrl = SiteUtil.getURL(dt1.getString(k,
								"SITEID"));
						if (!siteUrl.endsWith("/")) {
							siteUrl = siteUrl + "/";
						}
						if (url.startsWith("/")) {
							url = url.substring(1);
						}
						url = siteUrl + url;
					}
					dt1.set(k, "URL", url);
				}
				r.Data = dt1;
			}else{
				r.Data = dt;
			}
			r.Total = docs.totalHits;
			r.UsedTime = NumberUtil.round(
					(System.currentTimeMillis() - t) * 1.0 / 1000, 3);
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
}
