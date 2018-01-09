package com.sinosoft.search;

import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;

public class ProductAnalyzer extends Analyzer {
	public TokenStream tokenStream(String fieldName, Reader reader) {
		return new ProductTokenizer(fieldName, reader);
	}
}
