package com.sinosoft.schema;

import com.sinosoft.schema.WxActiveSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class WxActiveSet extends SchemaSet {
	public WxActiveSet() {
		this(10,0);
	}

	public WxActiveSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public WxActiveSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = WxActiveSchema._TableCode;
		Columns = WxActiveSchema._Columns;
		NameSpace = WxActiveSchema._NameSpace;
		InsertAllSQL = WxActiveSchema._InsertAllSQL;
		UpdateAllSQL = WxActiveSchema._UpdateAllSQL;
		FillAllSQL = WxActiveSchema._FillAllSQL;
		DeleteSQL = WxActiveSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new WxActiveSet();
	}

	public boolean add(WxActiveSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(WxActiveSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(WxActiveSchema aSchema) {
		return super.remove(aSchema);
	}

	public WxActiveSchema get(int index) {
		WxActiveSchema tSchema = (WxActiveSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, WxActiveSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(WxActiveSet aSet) {
		return super.set(aSet);
	}
}
 