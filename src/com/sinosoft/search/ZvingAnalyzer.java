package com.sinosoft.search;

import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;

public class ZvingAnalyzer extends Analyzer {
	public TokenStream tokenStream(String fieldName, Reader reader) {
		return new ZvingTokenizer(fieldName, reader);
	}
}
