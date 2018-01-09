package com.sinosoft.search;

import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class ProductTokenizer extends Tokenizer {
	public TokenStream st;

	public Token currentToken;

	public boolean splitingFlag = false;// 标志是否正在拆分

	public int offSet;

	public StringBuffer sb = new StringBuffer();

	public ProductTokenizer(String fieldName, Reader reader) {
		st = new StandardAnalyzer(Version.LUCENE_CURRENT).tokenStream(fieldName, reader);
		this.input = reader;
	}

	/**
	 * 这个方法只需要将中文进行分词索引  需要修改！
	 */
	public Token next() throws IOException {
		if (!splitingFlag) {
			currentToken = st.next();
			if (currentToken == null) {
				splitingFlag = true;
			} else {
				sb.append(new String(currentToken.termBuffer(), 0, currentToken.termLength()));
				return currentToken;
			}
		}
		if (splitingFlag) {
			if (offSet < sb.length()) {
				Token t = new Token(sb.substring(offSet, offSet + 1), offSet, ++offSet);
//				System.out.println("currentToken_t:" + new String(t.termBuffer()));
				return t;
			}
		}
		return null;
	}
}
