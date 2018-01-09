package com.sinosoft.schema;

import com.sinosoft.schema.SDProductPointRateSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDProductPointRateSet extends SchemaSet {
	public SDProductPointRateSet() {
		this(10,0);
	}

	public SDProductPointRateSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDProductPointRateSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDProductPointRateSchema._TableCode;
		Columns = SDProductPointRateSchema._Columns;
		NameSpace = SDProductPointRateSchema._NameSpace;
		InsertAllSQL = SDProductPointRateSchema._InsertAllSQL;
		UpdateAllSQL = SDProductPointRateSchema._UpdateAllSQL;
		FillAllSQL = SDProductPointRateSchema._FillAllSQL;
		DeleteSQL = SDProductPointRateSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDProductPointRateSet();
	}

	public boolean add(SDProductPointRateSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDProductPointRateSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDProductPointRateSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDProductPointRateSchema get(int index) {
		SDProductPointRateSchema tSchema = (SDProductPointRateSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDProductPointRateSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDProductPointRateSet aSet) {
		return super.set(aSet);
	}
}
 