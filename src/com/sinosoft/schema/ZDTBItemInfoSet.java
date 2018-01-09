package com.sinosoft.schema;

import com.sinosoft.schema.ZDTBItemInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class ZDTBItemInfoSet extends SchemaSet {
	public ZDTBItemInfoSet() {
		this(10,0);
	}

	public ZDTBItemInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZDTBItemInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZDTBItemInfoSchema._TableCode;
		Columns = ZDTBItemInfoSchema._Columns;
		NameSpace = ZDTBItemInfoSchema._NameSpace;
		InsertAllSQL = ZDTBItemInfoSchema._InsertAllSQL;
		UpdateAllSQL = ZDTBItemInfoSchema._UpdateAllSQL;
		FillAllSQL = ZDTBItemInfoSchema._FillAllSQL;
		DeleteSQL = ZDTBItemInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZDTBItemInfoSet();
	}

	public boolean add(ZDTBItemInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZDTBItemInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZDTBItemInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZDTBItemInfoSchema get(int index) {
		ZDTBItemInfoSchema tSchema = (ZDTBItemInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZDTBItemInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZDTBItemInfoSet aSet) {
		return super.set(aSet);
	}
}
 