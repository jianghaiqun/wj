package com.sinosoft.schema;

import com.sinosoft.schema.BSDTradeErrorCacheSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class BSDTradeErrorCacheSet extends SchemaSet {
	public BSDTradeErrorCacheSet() {
		this(10,0);
	}

	public BSDTradeErrorCacheSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BSDTradeErrorCacheSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BSDTradeErrorCacheSchema._TableCode;
		Columns = BSDTradeErrorCacheSchema._Columns;
		NameSpace = BSDTradeErrorCacheSchema._NameSpace;
		InsertAllSQL = BSDTradeErrorCacheSchema._InsertAllSQL;
		UpdateAllSQL = BSDTradeErrorCacheSchema._UpdateAllSQL;
		FillAllSQL = BSDTradeErrorCacheSchema._FillAllSQL;
		DeleteSQL = BSDTradeErrorCacheSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BSDTradeErrorCacheSet();
	}

	public boolean add(BSDTradeErrorCacheSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BSDTradeErrorCacheSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BSDTradeErrorCacheSchema aSchema) {
		return super.remove(aSchema);
	}

	public BSDTradeErrorCacheSchema get(int index) {
		BSDTradeErrorCacheSchema tSchema = (BSDTradeErrorCacheSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BSDTradeErrorCacheSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BSDTradeErrorCacheSet aSet) {
		return super.set(aSet);
	}
}
 