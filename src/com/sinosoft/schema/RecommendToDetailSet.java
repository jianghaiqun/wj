package com.sinosoft.schema;

import com.sinosoft.schema.RecommendToDetailSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class RecommendToDetailSet extends SchemaSet {
	public RecommendToDetailSet() {
		this(10,0);
	}

	public RecommendToDetailSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public RecommendToDetailSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = RecommendToDetailSchema._TableCode;
		Columns = RecommendToDetailSchema._Columns;
		NameSpace = RecommendToDetailSchema._NameSpace;
		InsertAllSQL = RecommendToDetailSchema._InsertAllSQL;
		UpdateAllSQL = RecommendToDetailSchema._UpdateAllSQL;
		FillAllSQL = RecommendToDetailSchema._FillAllSQL;
		DeleteSQL = RecommendToDetailSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new RecommendToDetailSet();
	}

	public boolean add(RecommendToDetailSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(RecommendToDetailSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(RecommendToDetailSchema aSchema) {
		return super.remove(aSchema);
	}

	public RecommendToDetailSchema get(int index) {
		RecommendToDetailSchema tSchema = (RecommendToDetailSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, RecommendToDetailSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(RecommendToDetailSet aSet) {
		return super.set(aSet);
	}
}
 