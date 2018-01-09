package com.sinosoft.schema;

import com.sinosoft.schema.SDTradeErrorCacheSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDTradeErrorCacheSet extends SchemaSet {
	public SDTradeErrorCacheSet() {
		this(10,0);
	}

	public SDTradeErrorCacheSet(int initialCapacity) {
		this(initialCapacity,0);
	} 

	public SDTradeErrorCacheSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDTradeErrorCacheSchema._TableCode;
		Columns = SDTradeErrorCacheSchema._Columns;
		NameSpace = SDTradeErrorCacheSchema._NameSpace;
		InsertAllSQL = SDTradeErrorCacheSchema._InsertAllSQL;
		UpdateAllSQL = SDTradeErrorCacheSchema._UpdateAllSQL;
		FillAllSQL = SDTradeErrorCacheSchema._FillAllSQL;
		DeleteSQL = SDTradeErrorCacheSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDTradeErrorCacheSet();
	}

	public boolean add(SDTradeErrorCacheSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDTradeErrorCacheSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDTradeErrorCacheSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDTradeErrorCacheSchema get(int index) {
		SDTradeErrorCacheSchema tSchema = (SDTradeErrorCacheSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDTradeErrorCacheSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDTradeErrorCacheSet aSet) {
		return super.set(aSet);
	}
}
 