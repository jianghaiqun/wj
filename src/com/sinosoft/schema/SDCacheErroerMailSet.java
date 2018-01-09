package com.sinosoft.schema;

import com.sinosoft.schema.SDCacheErroerMailSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDCacheErroerMailSet extends SchemaSet {
	public SDCacheErroerMailSet() {
		this(10,0);
	}

	public SDCacheErroerMailSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDCacheErroerMailSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDCacheErroerMailSchema._TableCode;
		Columns = SDCacheErroerMailSchema._Columns;
		NameSpace = SDCacheErroerMailSchema._NameSpace;
		InsertAllSQL = SDCacheErroerMailSchema._InsertAllSQL;
		UpdateAllSQL = SDCacheErroerMailSchema._UpdateAllSQL;
		FillAllSQL = SDCacheErroerMailSchema._FillAllSQL;
		DeleteSQL = SDCacheErroerMailSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDCacheErroerMailSet();
	}

	public boolean add(SDCacheErroerMailSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDCacheErroerMailSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDCacheErroerMailSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDCacheErroerMailSchema get(int index) {
		SDCacheErroerMailSchema tSchema = (SDCacheErroerMailSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDCacheErroerMailSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDCacheErroerMailSet aSet) {
		return super.set(aSet);
	}
}
 