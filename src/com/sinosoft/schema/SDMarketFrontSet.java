package com.sinosoft.schema;

import com.sinosoft.schema.SDMarketFrontSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDMarketFrontSet extends SchemaSet {
	public SDMarketFrontSet() {
		this(10,0);
	}

	public SDMarketFrontSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDMarketFrontSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDMarketFrontSchema._TableCode;
		Columns = SDMarketFrontSchema._Columns;
		NameSpace = SDMarketFrontSchema._NameSpace;
		InsertAllSQL = SDMarketFrontSchema._InsertAllSQL;
		UpdateAllSQL = SDMarketFrontSchema._UpdateAllSQL;
		FillAllSQL = SDMarketFrontSchema._FillAllSQL;
		DeleteSQL = SDMarketFrontSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDMarketFrontSet();
	}

	public boolean add(SDMarketFrontSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDMarketFrontSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDMarketFrontSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDMarketFrontSchema get(int index) {
		SDMarketFrontSchema tSchema = (SDMarketFrontSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDMarketFrontSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDMarketFrontSet aSet) {
		return super.set(aSet);
	}
}
 