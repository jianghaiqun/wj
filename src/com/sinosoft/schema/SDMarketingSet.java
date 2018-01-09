package com.sinosoft.schema;

import com.sinosoft.schema.SDMarketingSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDMarketingSet extends SchemaSet {
	public SDMarketingSet() {
		this(10,0);
	}

	public SDMarketingSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDMarketingSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDMarketingSchema._TableCode;
		Columns = SDMarketingSchema._Columns;
		NameSpace = SDMarketingSchema._NameSpace;
		InsertAllSQL = SDMarketingSchema._InsertAllSQL;
		UpdateAllSQL = SDMarketingSchema._UpdateAllSQL;
		FillAllSQL = SDMarketingSchema._FillAllSQL;
		DeleteSQL = SDMarketingSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDMarketingSet();
	}

	public boolean add(SDMarketingSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDMarketingSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDMarketingSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDMarketingSchema get(int index) {
		SDMarketingSchema tSchema = (SDMarketingSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDMarketingSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDMarketingSet aSet) {
		return super.set(aSet);
	}
}
 