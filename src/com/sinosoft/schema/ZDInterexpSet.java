package com.sinosoft.schema;

import com.sinosoft.schema.ZDInterexpSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class ZDInterexpSet extends SchemaSet {
	public ZDInterexpSet() {
		this(10,0);
	}

	public ZDInterexpSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZDInterexpSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZDInterexpSchema._TableCode;
		Columns = ZDInterexpSchema._Columns;
		NameSpace = ZDInterexpSchema._NameSpace;
		InsertAllSQL = ZDInterexpSchema._InsertAllSQL;
		UpdateAllSQL = ZDInterexpSchema._UpdateAllSQL;
		FillAllSQL = ZDInterexpSchema._FillAllSQL;
		DeleteSQL = ZDInterexpSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZDInterexpSet();
	}

	public boolean add(ZDInterexpSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZDInterexpSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZDInterexpSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZDInterexpSchema get(int index) {
		ZDInterexpSchema tSchema = (ZDInterexpSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZDInterexpSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZDInterexpSet aSet) {
		return super.set(aSet);
	}
}
 