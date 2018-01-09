package com.sinosoft.cms.document;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DBUtil;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.NumberUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.search.SearchParameters;
import com.sinosoft.search.SearchResult;
import com.sinosoft.search.index.Indexer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

import java.io.File;

/**
 */
public class ArticleRelaIndexer extends Indexer {
	private static final int PageSize = 100;
	private static Object mutex = new Object();

	public ArticleRelaIndexer() {
		setPath(Config.getContextRealPath() + "WEB-INF/data/index/keyword/");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sinosoft.search.index.Indexer#create()
	 */
	public void create() {
		synchronized (mutex) {
			QueryBuilder qb = new QueryBuilder("select InnerCode,KeywordSetting from ZCCatalog where KeywordFlag='Y'");
			Mapx map = qb.executeDataTable().toMapx(0, 1);
			Object[] ks = map.keyArray();
			for (int i = 0; i < ks.length; i++) {
				String k = ks[i].toString();
				boolean existParentFlag = false;
				while (k.length() > 4) {
					k = k.substring(0, k.length() - 4);
					if (map.containsKey(k)) {
						existParentFlag = true;
						break;
					}
				}
				k = ks[i].toString();
				if (!existParentFlag) {
					qb = new QueryBuilder("select ID,CatalogID,CatalogInnerCode,Title,Keyword from ZCArticle "
							+ "where CatalogInnerCode like '" + k + "%'");
					int count = DBUtil.getCount(qb);
					for (int j = 0; j <= count / PageSize; j++) {
						DataTable dt = qb.executePagedDataTable(PageSize, j);
						for (int m = 0; m < dt.getRowCount(); m++) {
							String CatalogInnerCode = dt.getString(m, "CatalogInnerCode");
							String Keyword = dt.getString(m, "Keyword");
							String Title = dt.getString(m, "Title");
							String ID = dt.getString(m, "ID");
							String CatalogID = dt.getString(m, "CatalogID");
							if (StringUtil.isEmpty(Keyword)) {
								Keyword = Title;
							} else {
								Keyword = Keyword + " " + Title;
							}
							k = CatalogInnerCode;
							String Setting = map.getString(k);
							while (StringUtil.isEmpty(Setting) && k.length() >= 6) {
								k = k.substring(0, k.length() - 6);
								Setting = map.getString(k);
							}
							if (StringUtil.isEmpty(Setting)) {
								continue;
							}
							Setting = "," + Setting + ",";
							Document doc = new Document();
							doc.add(new Field("CATALOGINNERCODE", CatalogInnerCode, Field.Store.YES,
									Field.Index.NOT_ANALYZED));
							doc.add(new Field("ID", "" + ID, Field.Store.YES, Field.Index.NOT_ANALYZED));
							doc.add(new Field("CatalogID", "" + CatalogID, Field.Store.YES, Field.Index.NOT_ANALYZED));
							doc.add(new Field("SETTING", Setting, Field.Store.YES, Field.Index.NOT_ANALYZED));
							doc.add(new Field("KEYWORD", Keyword, Field.Store.NO, Field.Index.ANALYZED));
							doc.add(new Field("TITLE", Title, Field.Store.YES, Field.Index.NO));
							try {
								writer.addDocument(doc);
							} catch (Exception e) {
								logger.error(e.getMessage(), e);
							}
						}
					}
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sinosoft.search.index.Indexer#update()
	 */
	public void update() {
		synchronized (mutex) {
			QueryBuilder qb = new QueryBuilder("select InnerCode,KeywordSetting from ZCCatalog where KeywordFlag='Y'");
			Mapx map = qb.executeDataTable().toMapx(0, 1);

			DataTable catalogs = new QueryBuilder("select distinct CatalogID from ZCArticle where (addtime>=?"
					+ " or modifytime>=?) and status=" + Article.STATUS_PUBLISHED, lastDate, lastDate)
					.executeDataTable();
			for (int j = 0; j < catalogs.getRowCount(); j++) {
				qb = new QueryBuilder("select ID,CatalogID,CatalogInnerCode,Title,Keyword from ZCArticle "
						+ "where CatalogID=? and (addtime>=? or modifytime>=?) and status=" + Article.STATUS_PUBLISHED,
						catalogs.getLong(j, "CatalogID"), lastDate);
				qb.add(lastDate);
				int total = DBUtil.getCount(qb);
				for (int i = 0; i * PageSize < total; i++) {
					DataTable dt = qb.executePagedDataTable(PageSize, i);
					for (int m = 0; m < dt.getRowCount(); m++) {
						String CatalogInnerCode = dt.getString(m, "CatalogInnerCode");
						String Keyword = dt.getString(m, "Keyword");
						String Title = dt.getString(m, "Title");
						String ID = dt.getString(m, "ID");
						String CatalogID = dt.getString(m, "CatalogID");
						if (StringUtil.isEmpty(Keyword)) {
							Keyword = Title;
						} else {
							Keyword = Keyword + " " + Title;
						}
						String k = CatalogInnerCode;
						String Setting = map.getString(k);
						while (StringUtil.isEmpty(Setting) && k.length() >= 6) {
							k = k.substring(0, k.length() - 6);
							Setting = map.getString(k);
						}
						if (StringUtil.isEmpty(Setting)) {
							continue;
						}
						Setting = "," + Setting.replaceAll("[\\s\\,，]+", ",") + ",";
						Document doc = new Document();
						doc.add(new Field("CATALOGINNERCODE", CatalogInnerCode, Field.Store.YES,
								Field.Index.NOT_ANALYZED));
						doc.add(new Field("CatalogID", CatalogID, Field.Store.YES, Field.Index.NOT_ANALYZED));
						doc.add(new Field("ID", ID, Field.Store.YES, Field.Index.NOT_ANALYZED));
						doc.add(new Field("SETTING", Setting, Field.Store.YES, Field.Index.NOT_ANALYZED));
						doc.add(new Field("KEYWORD", Keyword, Field.Store.NO, Field.Index.ANALYZED));
						doc.add(new Field("TITLE", Title, Field.Store.YES, Field.Index.NO));
						try {
							writer.updateDocument(new Term("ID", ID), doc);
						} catch (Exception e) {
							logger.error(e.getMessage(), e);
						}
					}
				}
			}
			DataTable dt = new QueryBuilder("select id from BZCArticle where status=" + Article.STATUS_PUBLISHED
					+ " and backuptime>?", lastDate).executeDataTable();
			for (int i = 0; i < dt.getRowCount(); i++) {
				try {
					writer.deleteDocuments(new Term("ID", dt.getString(i, 0)));
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}

	public static void updateCatalog(long catalogID) {
		synchronized (mutex) {

		}
	}

	public static SearchResult search(SearchParameters sps) {
		FSDirectory directory = null;
		IndexReader reader = null;
		IndexSearcher searcher = null;
		try {
			long t = System.currentTimeMillis();
			directory = FSDirectory.getDirectory(new File(Config.getContextRealPath() + "WEB-INF/data/index/keyword"), true);
			reader = IndexReader.open(directory, true);
			searcher = new IndexSearcher(reader);

			if (sps.getBooleanQuery().clauses().size() == 0) {
				sps.addLeftLikeField("CatalogInnerCode", "");
			}

			DataTable dt = new DataTable();
			int start = sps.getPageIndex() * sps.getPageSize();
			TopDocs docs = null;
			if (sps.getSort() != null) {
				docs = searcher.search(sps.getBooleanQuery(), sps.getDateRangeFilter(), start + sps.getPageSize(), sps
						.getSort());
			} else {
				docs = searcher.search(sps.getBooleanQuery(), sps.getDateRangeFilter(), start + sps.getPageSize());
			}

			for (int i = start; i < start + sps.getPageSize() && i < docs.scoreDocs.length; i++) {
				Document doc = searcher.doc(docs.scoreDocs[i].doc);
				if (i == start) {
					Object[] fields = doc.getFields().toArray();
					for (int j = 0; j < fields.length; j++) {
						String name = ((Field) fields[j]).name();
						dt.insertColumn(name);
					}
				}
				dt.insertRow(new String[dt.getColCount()]);
				for (int j = 0; j < dt.getColCount(); j++) {
					dt.set(i - start, j, doc.get(dt.getDataColumn(j).getColumnName()));
				}
			}

			SearchResult r = new SearchResult();
			r.Data = dt;
			r.Total = docs.totalHits;
			r.UsedTime = NumberUtil.round((System.currentTimeMillis() - t) * 1.0 / 1000, 3);
			return r;
		} catch (Exception e) {
			logger.error("搜索引擎异常！" +  e.getMessage(), e);
		} finally {
			try {
				if (directory != null)
					directory.close();
				if (reader != null)
					reader.close();
				if (searcher != null)
					searcher.close();
			} catch (Exception e) {
				logger.error("关闭搜索引擎异常！" +  e.getMessage(), e);
			}
		}
		return null;
	}

	public static SearchResult getRelaArticles(String articleID, String setting, String title, String keywords) {
		return getRelaArticles(null, articleID, setting, title, keywords);
	}

	public static SearchResult getRelaArticles(String catalogID, String articleID, String setting, String title,
			String keywords) {
		SearchParameters sps = new SearchParameters();
		sps.setPageSize(30);
		sps.addFulltextField("Keyword", keywords);
		if (StringUtil.isNotEmpty(setting)) {
			String[] arr = setting.split("[\\s\\,，]+");
			for (int i = 0; i < arr.length; i++) {
				if (StringUtil.isNotEmpty(arr[i])) {
					sps.addLikeField("Setting", "," + arr[i] + ",");
				}
			}
		}
		if (StringUtil.isNotEmpty(articleID)) {
			sps.addNotEqualField("ID", "" + articleID);
		}
		if (StringUtil.isNotEmpty(catalogID)) {
			sps.addEqualField("CatalogID", catalogID);
		}
		SearchResult sr = search(sps);
		return sr;
	}

	public static void main(String[] args) {
		ArticleRelaIndexer ari = new ArticleRelaIndexer();
		ari.start();
		long t = System.currentTimeMillis();
		for (int i = 0; i < 1; i++) {
			SearchResult sr = getRelaArticles(null, "226411", "海外", "科学家揭开火星海洋消失之谜", "火星 海洋 科学家 消失 太阳风暴");
//			System.out.println(sr.Data);
//			System.out.println(sr.Total);
		}
//		System.out.println(System.currentTimeMillis() - t);
	}
}
