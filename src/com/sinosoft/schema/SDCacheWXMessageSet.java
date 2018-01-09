package com.sinosoft.schema;

import com.sinosoft.schema.SDCacheWXMessageSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDCacheWXMessageSet extends SchemaSet {
	public SDCacheWXMessageSet() {
		this(10,0);
	}

	public SDCacheWXMessageSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDCacheWXMessageSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDCacheWXMessageSchema._TableCode;
		Columns = SDCacheWXMessageSchema._Columns;
		NameSpace = SDCacheWXMessageSchema._NameSpace;
		InsertAllSQL = SDCacheWXMessageSchema._InsertAllSQL;
		UpdateAllSQL = SDCacheWXMessageSchema._UpdateAllSQL;
		FillAllSQL = SDCacheWXMessageSchema._FillAllSQL;
		DeleteSQL = SDCacheWXMessageSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDCacheWXMessageSet();
	}

	public boolean add(SDCacheWXMessageSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDCacheWXMessageSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDCacheWXMessageSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDCacheWXMessageSchema get(int index) {
		SDCacheWXMessageSchema tSchema = (SDCacheWXMessageSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDCacheWXMessageSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDCacheWXMessageSet aSet) {
		return super.set(aSet);
	}
}
 