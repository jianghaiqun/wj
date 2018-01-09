package com.sinosoft.schema;

import com.sinosoft.schema.ZDButtonSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class ZDButtonSet extends SchemaSet {
	public ZDButtonSet() {
		this(10,0);
	}

	public ZDButtonSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZDButtonSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZDButtonSchema._TableCode;
		Columns = ZDButtonSchema._Columns;
		NameSpace = ZDButtonSchema._NameSpace;
		InsertAllSQL = ZDButtonSchema._InsertAllSQL;
		UpdateAllSQL = ZDButtonSchema._UpdateAllSQL;
		FillAllSQL = ZDButtonSchema._FillAllSQL;
		DeleteSQL = ZDButtonSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZDButtonSet();
	}

	public boolean add(ZDButtonSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZDButtonSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZDButtonSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZDButtonSchema get(int index) {
		ZDButtonSchema tSchema = (ZDButtonSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZDButtonSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZDButtonSet aSet) {
		return super.set(aSet);
	}
}
 