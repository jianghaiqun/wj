package com.sinosoft.search;

import org.apache.lucene.search.Similarity;;

public class ProductSimilarity extends Similarity {
	private static final long serialVersionUID = 1L;

	/*
	 * 提高词元命中个数在相似度比较中的权重影响，即，当有多个词元得到匹配时，文档的相似度将提高 (non-Javadoc)
	 * 
	 * @see org.apache.lucene.search.Similarity#coord(int, int)
	 */
	public float coord(int overlap, int maxOverlap) {			//每一个Document中所有匹配的关键字与当前关键字的匹配比例因素影响
		float overlap2 = (float) Math.pow(2, overlap);
		float maxOverlap2 = (float) Math.pow(2,maxOverlap);
//		System.out.println("--------coord-"+overlap2 / maxOverlap2);	
		return (overlap2 / maxOverlap2);
//		return 1;
	}

	public float idf(int docFreq, int numDocs) {				//匹配的docuemnt在全部document的影响因素
//		return (float) (Math.log(numDocs / (double) (docFreq + 1)) + 1.0);
		//System.out.println("---------idf");
		return 1;
	}

	/**
	 * 文本长度不应该影响评分
	 */
	public float lengthNorm(String fieldName, int numTokens) {	
		//System.out.println("---------lengthNorm:"+fieldName+":"+numTokens);
		return 1;
//		return 1;
	}

	public float queryNorm(float sumOfSquaredWeights) {			//用来使不同查询间的分数更可比较（comparable）。这个因子不影响文档的排名（ranking）
//		System.out.println("---------queryNorm"+sumOfSquaredWeights);
		//return (float) (1.0 / Math.sqrt(sumOfSquaredWeights));
		return 1;
	}

	public float tf(float freq) {								//表示 term 在一个document的出现次数
		return 1;
	}

	@Override
	public float sloppyFreq(int distance) {						// 匹配的term　与 term之间的距离因素
//		System.out.println("---------sloppyFreq"+distance);
		return 1;
	}
}
