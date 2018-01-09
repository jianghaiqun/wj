package com.sinosoft.schema;

import com.sinosoft.schema.SDMarketProductSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDMarketProductSet extends SchemaSet {
	public SDMarketProductSet() {
		this(10,0);
	}

	public SDMarketProductSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDMarketProductSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDMarketProductSchema._TableCode;
		Columns = SDMarketProductSchema._Columns;
		NameSpace = SDMarketProductSchema._NameSpace;
		InsertAllSQL = SDMarketProductSchema._InsertAllSQL;
		UpdateAllSQL = SDMarketProductSchema._UpdateAllSQL;
		FillAllSQL = SDMarketProductSchema._FillAllSQL;
		DeleteSQL = SDMarketProductSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDMarketProductSet();
	}

	public boolean add(SDMarketProductSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDMarketProductSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDMarketProductSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDMarketProductSchema get(int index) {
		SDMarketProductSchema tSchema = (SDMarketProductSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDMarketProductSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDMarketProductSet aSet) {
		return super.set(aSet);
	}
}
 