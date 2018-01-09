package com.sinosoft.schema;

import com.sinosoft.schema.SDSearchCacheSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDSearchCacheSet extends SchemaSet {
	public SDSearchCacheSet() {
		this(10,0);
	}

	public SDSearchCacheSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDSearchCacheSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDSearchCacheSchema._TableCode;
		Columns = SDSearchCacheSchema._Columns;
		NameSpace = SDSearchCacheSchema._NameSpace;
		InsertAllSQL = SDSearchCacheSchema._InsertAllSQL;
		UpdateAllSQL = SDSearchCacheSchema._UpdateAllSQL;
		FillAllSQL = SDSearchCacheSchema._FillAllSQL;
		DeleteSQL = SDSearchCacheSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDSearchCacheSet();
	}

	public boolean add(SDSearchCacheSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDSearchCacheSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDSearchCacheSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDSearchCacheSchema get(int index) {
		SDSearchCacheSchema tSchema = (SDSearchCacheSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDSearchCacheSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDSearchCacheSet aSet) {
		return super.set(aSet);
	}
}
 