package com.sinosoft.schema;

import com.sinosoft.schema.WxActiveCodeInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class WxActiveCodeInfoSet extends SchemaSet {
	public WxActiveCodeInfoSet() {
		this(10,0);
	}

	public WxActiveCodeInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public WxActiveCodeInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = WxActiveCodeInfoSchema._TableCode;
		Columns = WxActiveCodeInfoSchema._Columns;
		NameSpace = WxActiveCodeInfoSchema._NameSpace;
		InsertAllSQL = WxActiveCodeInfoSchema._InsertAllSQL;
		UpdateAllSQL = WxActiveCodeInfoSchema._UpdateAllSQL;
		FillAllSQL = WxActiveCodeInfoSchema._FillAllSQL;
		DeleteSQL = WxActiveCodeInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new WxActiveCodeInfoSet();
	}

	public boolean add(WxActiveCodeInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(WxActiveCodeInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(WxActiveCodeInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public WxActiveCodeInfoSchema get(int index) {
		WxActiveCodeInfoSchema tSchema = (WxActiveCodeInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, WxActiveCodeInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(WxActiveCodeInfoSet aSet) {
		return super.set(aSet);
	}
}
 