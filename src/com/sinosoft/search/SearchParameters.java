package com.sinosoft.search;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.RangeFilter;
import org.apache.lucene.search.RangeQuery;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wltea.analyzer.lucene.IKQueryParser;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class SearchParameters {
	private static final Logger logger = LoggerFactory.getLogger(SearchParameters.class);

	private BooleanQuery booleanQuery = new BooleanQuery();

	private Map<String,Query> queryMap = new LinkedHashMap<String,Query>();
	
	private RangeFilter filter = null;

	private int pageSize = 10;

	private int pageIndex = 0;

	private long indexID;

	private ArrayList sortFieldList = new ArrayList(4);

	/**
	 * 添加一个全文检索条件，在指定字段中执行全文检索
	 */
	public void addFulltextField(String field, String query) {
		addFulltextField(field, query, true);
	}

	/**
	 * 添加一个全文检索条件，在指定字段中执行全文检索
	 */
	public void addFulltextField(String field, String query, boolean isMust) {
		try {
			Query q = IKQueryParser.parse(field.toUpperCase(), query);
			if (q == null) {
				logger.info("不符合条件的关键字:{}", query);
				return;
			}
			if (field.equalsIgnoreCase("Title")) {
				q.setBoost(10000);// 标题中的关键字分数应该较高
			}
			booleanQuery.add(q, isMust ? Occur.MUST : Occur.SHOULD);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 添加一个全文检索条件，在指定字段中执行全文检索,用于调用标准分词器
	 */
	public void addNewFulltextField(String field, String query, boolean isMust) {

        try {
             QueryParser parser = new QueryParser(Version.LUCENE_CURRENT, field.toUpperCase(), new StandardAnalyzer(Version.LUCENE_CURRENT));
             Query q = parser.parse(query);
             if (q == null) {
 				logger.info("不符合条件的关键字:{}", query);
 				return;
 			}
 			if (field.equalsIgnoreCase("Title")) {
 				q.setBoost(10000);// 标题中的关键字分数应该较高
 			}
 			booleanQuery.add(q, isMust ? Occur.MUST : Occur.SHOULD);

        } catch (Exception e) {

                 logger.error(e.getMessage(), e);

        }

}


	
	public void addNotEqualField(String field, String query) {
		TermQuery q = new TermQuery(new Term(field.toUpperCase(), query));
		booleanQuery.add(q, Occur.MUST_NOT);
	}

	/**
	 * 添加一个查询条件，类似于SQL中的field like '%query'<br>
	 * 如果使用本方法，请注意测试搜索性能。
	 */
	public void addRightLikeField(String field, String query) {
		addRightLikeField(field, query, true);
	}

	/**
	 * 添加一个查询条件，类似于SQL中的field like '%query'<br>
	 * 如果使用本方法，请注意测试搜索性能。
	 */
	public void addRightLikeField(String field, String query, boolean isMust) {
		WildcardQuery q = new WildcardQuery(new Term(field.toUpperCase(), "*" + query));
		booleanQuery.add(q, isMust ? Occur.MUST : Occur.SHOULD);
	}

	/**
	 * 添加一个查询条件，类似于SQL中的field like 'query%'
	 */
	public void addLeftLikeField(String field, String query) {
		addLeftLikeField(field, query, true);
	}

	/**
	 * 添加一个查询条件，类似于SQL中的field like 'query%'
	 */
	public void addLeftLikeField(String field, String query, boolean isMust) {
		PrefixQuery q = new PrefixQuery(new Term(field.toUpperCase(), query));
		booleanQuery.add(q, isMust ? Occur.MUST : Occur.SHOULD);
	}

	/**
	 * 添加一个查询条件，类似于SQL中的field like '%query%'<br>
	 * 如果使用本方法，请注意测试搜索性能。
	 */
	public void addLikeField(String field, String query) {
		addLikeField(field, query, true);
	}

	/**
	 * 添加一个查询条件，类似于SQL中的field like '%query%'<br>
	 * 如果使用本方法，请注意测试搜索性能。
	 */
	public void addLikeField(String field, String query, boolean isMust) {
		WildcardQuery q = new WildcardQuery(new Term(field.toUpperCase(), "*" + query + "*"));
		booleanQuery.add(q, isMust ? Occur.MUST : Occur.SHOULD);
	}

	/**
	 * 添加一个查询条件，要求字段值完全等于指定值
	 */
	public void addEqualField(String field, String query) {
		addEqualField(field, query, true);
	}

	/**
	 * 添加一个查询条件，要求字段值完全等于指定值
	 */
	public void addEqualField(String field, String query, boolean isMust) {
		TermQuery q = new TermQuery(new Term(field.toUpperCase(), query));
		booleanQuery.add(q, isMust ? Occur.MUST : Occur.SHOULD);
	}

	/**
	 * 添加一个查询条件，要求字段值在指定的范围内,检索结果包含范围的边界值
	 */
	public void addRangeField(String field, String valueBegin, String valueEnd) {
		addRangeField(field, valueBegin, valueEnd, true);
	}

	/**
	 * 添加一个查询条件，要求字段值在指定的范围内,检索结果包含范围的边界值
	 */
	public void addRangeField(String field, String valueBegin, String valueEnd, boolean isMust) {
		Term tBegin = new Term(field,valueBegin);
		Term tEnd = new Term(field,valueEnd);
		RangeQuery q = new RangeQuery(tBegin, tEnd, true);
		booleanQuery.add(q, isMust ? Occur.MUST : Occur.SHOULD);
	}

	/**
	 * 添加一个自定义的查询条件
	 */
	public void addQuery(Query q) {
		addQuery(q, true);
	}

	/**
	 * 添加一个自定义的查询条件
	 */
	public void addQuery(Query q, boolean isMust) {
		booleanQuery.add(q, isMust ? Occur.MUST : Occur.SHOULD);
	}

	/**
	 * 按指定字段排序
	 * 
	 * @deprecated
	 */
	public void setSortField(String field, int dataType, boolean descFlag) {
		sortFieldList.clear();
		sortFieldList.add(new SortField(field.toUpperCase(), dataType, descFlag));
	}

	/**
	 * 添加一个排序字段
	 */
	public void addSortField(String field, int dataType, boolean descFlag) {
		sortFieldList.add(new SortField(field.toUpperCase(), dataType, descFlag));
	}

	/**
	 * 设定指定日期字段的区间，这是因为Lucene在处理日期的RangeQuery时存在性能问题而采取的变通措施
	 */
	public void setDateRange(String field, String startDate, String endDate) {
		filter = new RangeFilter(field.toUpperCase(), startDate, endDate, true, true);
	}

	public BooleanQuery getBooleanQuery() {
		return booleanQuery;
	}

	public RangeFilter getDateRangeFilter() {
		return filter;
	}

	public Sort getSort() {
		if (sortFieldList.size() == 0) {
			return null;
		}
		Sort sort = new Sort();
		SortField[] sfs = new SortField[sortFieldList.size()];
		for (int i = 0; i < sortFieldList.size(); i++) {
			sfs[i] = (SortField) sortFieldList.get(i);
		}
		sort.setSort(sfs);
		return sort;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		if (pageIndex < 0) {
			pageIndex = 0;
		}
		this.pageIndex = pageIndex;
	}

	public long getIndexID() {
		return indexID;
	}

	public void setIndexID(long indexID) {
		this.indexID = indexID;
	}

	public Map<String, Query> getQueryMap() {
		return queryMap;
	}

	public void setQueryMap(Map<String, Query> queryMap) {
		this.queryMap = queryMap;
	}
	
}
