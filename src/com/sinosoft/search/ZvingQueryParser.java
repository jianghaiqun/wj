package com.sinosoft.search;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
//import org.apache.lucene.util.Version;

/**
 * 用途一：在于防止多个线程共用一个QueryParser时抛EOF异常<br>
 * 用途二：过滤掉非法字符
 */
public class ZvingQueryParser extends QueryParser {
	private static final char[] Filters = "+-&|!(){}[]^\"~*?:\\".toCharArray();

	public ZvingQueryParser(String field, Analyzer analyzer) {
		super(field, analyzer);
	}

	public synchronized Query parse(String content) throws ParseException {
		StringBuffer sb = new StringBuffer();
		char[] cs = content.toCharArray();
		for (int i = 0; i < cs.length; i++) {
			for (int j = 0; j < Filters.length; j++) {
				if (cs[i] == Filters[j]) {
					sb.append("\\");
					break;
				}
			}
			sb.append(cs[i]);
		}
		return super.parse(sb.toString());
	}
}
