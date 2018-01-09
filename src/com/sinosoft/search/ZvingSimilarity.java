package com.sinosoft.search;

import org.apache.lucene.search.Similarity;

public class ZvingSimilarity extends Similarity {
	private static final long serialVersionUID = 1L;

	/*
	 * 提高词元命中个数在相似度比较中的权重影响，即，当有多个词元得到匹配时，文档的相似度将提高 (non-Javadoc)
	 * 
	 * @see org.apache.lucene.search.Similarity#coord(int, int)
	 */
	public float coord(int overlap, int maxOverlap) {
		float overlap2 = (float) Math.pow(2, overlap);
		float maxOverlap2 = (float) Math.pow(2, maxOverlap);
		return (overlap2 / maxOverlap2);
	}

	public float idf(int docFreq, int numDocs) {
		return (float) (Math.log(numDocs / (double) (docFreq + 1)) + 1.0);
	}

	/**
	 * 文本长度不应该影响评分
	 */
	public float lengthNorm(String fieldName, int numTokens) {
		return 1;
	}

	public float queryNorm(float sumOfSquaredWeights) {
		return (float) (1.0 / Math.sqrt(sumOfSquaredWeights));
	}

	public float sloppyFreq(int distance) {
		return 1.0f / (distance + 1);
	}

	public float tf(float freq) {
		return (float) Math.sqrt(freq);
	}

}
