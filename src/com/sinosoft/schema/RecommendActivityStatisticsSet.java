package com.sinosoft.schema;

import com.sinosoft.framework.orm.SchemaSet;

public class RecommendActivityStatisticsSet extends SchemaSet {
	public RecommendActivityStatisticsSet() {
		this(10,0);
	}

	public RecommendActivityStatisticsSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public RecommendActivityStatisticsSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = RecommendActivityStatisticsSchema._TableCode;
		Columns = RecommendActivityStatisticsSchema._Columns;
		NameSpace = RecommendActivityStatisticsSchema._NameSpace;
		InsertAllSQL = RecommendActivityStatisticsSchema._InsertAllSQL;
		UpdateAllSQL = RecommendActivityStatisticsSchema._UpdateAllSQL;
		FillAllSQL = RecommendActivityStatisticsSchema._FillAllSQL;
		DeleteSQL = RecommendActivityStatisticsSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new RecommendActivityStatisticsSet();
	}

	public boolean add(RecommendActivityStatisticsSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(RecommendActivityStatisticsSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(RecommendActivityStatisticsSchema aSchema) {
		return super.remove(aSchema);
	}

	public RecommendActivityStatisticsSchema get(int index) {
		RecommendActivityStatisticsSchema tSchema = (RecommendActivityStatisticsSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, RecommendActivityStatisticsSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(RecommendActivityStatisticsSet aSet) {
		return super.set(aSet);
	}
}
