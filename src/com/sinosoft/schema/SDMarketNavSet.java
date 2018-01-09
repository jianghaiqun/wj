package com.sinosoft.schema;

import com.sinosoft.schema.SDMarketNavSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDMarketNavSet extends SchemaSet {
	public SDMarketNavSet() {
		this(10,0);
	}

	public SDMarketNavSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDMarketNavSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDMarketNavSchema._TableCode;
		Columns = SDMarketNavSchema._Columns;
		NameSpace = SDMarketNavSchema._NameSpace;
		InsertAllSQL = SDMarketNavSchema._InsertAllSQL;
		UpdateAllSQL = SDMarketNavSchema._UpdateAllSQL;
		FillAllSQL = SDMarketNavSchema._FillAllSQL;
		DeleteSQL = SDMarketNavSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDMarketNavSet();
	}

	public boolean add(SDMarketNavSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDMarketNavSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDMarketNavSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDMarketNavSchema get(int index) {
		SDMarketNavSchema tSchema = (SDMarketNavSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDMarketNavSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDMarketNavSet aSet) {
		return super.set(aSet);
	}
}
 