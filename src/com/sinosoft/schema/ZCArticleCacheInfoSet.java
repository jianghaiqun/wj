package com.sinosoft.schema;

import com.sinosoft.schema.ZCArticleCacheInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class ZCArticleCacheInfoSet extends SchemaSet {
	public ZCArticleCacheInfoSet() {
		this(10,0);
	}

	public ZCArticleCacheInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCArticleCacheInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCArticleCacheInfoSchema._TableCode;
		Columns = ZCArticleCacheInfoSchema._Columns;
		NameSpace = ZCArticleCacheInfoSchema._NameSpace;
		InsertAllSQL = ZCArticleCacheInfoSchema._InsertAllSQL;
		UpdateAllSQL = ZCArticleCacheInfoSchema._UpdateAllSQL;
		FillAllSQL = ZCArticleCacheInfoSchema._FillAllSQL;
		DeleteSQL = ZCArticleCacheInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCArticleCacheInfoSet();
	}

	public boolean add(ZCArticleCacheInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCArticleCacheInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCArticleCacheInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCArticleCacheInfoSchema get(int index) {
		ZCArticleCacheInfoSchema tSchema = (ZCArticleCacheInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCArticleCacheInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCArticleCacheInfoSet aSet) {
		return super.set(aSet);
	}
}
 