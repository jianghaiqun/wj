package com.sinosoft.schema;

import com.sinosoft.schema.SeoDataSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SeoDataSet extends SchemaSet {
	public SeoDataSet() {
		this(10,0);
	}

	public SeoDataSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SeoDataSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SeoDataSchema._TableCode;
		Columns = SeoDataSchema._Columns;
		NameSpace = SeoDataSchema._NameSpace;
		InsertAllSQL = SeoDataSchema._InsertAllSQL;
		UpdateAllSQL = SeoDataSchema._UpdateAllSQL;
		FillAllSQL = SeoDataSchema._FillAllSQL;
		DeleteSQL = SeoDataSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SeoDataSet();
	}

	public boolean add(SeoDataSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SeoDataSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SeoDataSchema aSchema) {
		return super.remove(aSchema);
	}

	public SeoDataSchema get(int index) {
		SeoDataSchema tSchema = (SeoDataSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SeoDataSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SeoDataSet aSet) {
		return super.set(aSet);
	}
}
 