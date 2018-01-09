package com.sinosoft.schema;

import com.sinosoft.schema.WxResponseInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class WxResponseInfoSet extends SchemaSet {
	public WxResponseInfoSet() {
		this(10,0);
	}

	public WxResponseInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public WxResponseInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = WxResponseInfoSchema._TableCode;
		Columns = WxResponseInfoSchema._Columns;
		NameSpace = WxResponseInfoSchema._NameSpace;
		InsertAllSQL = WxResponseInfoSchema._InsertAllSQL;
		UpdateAllSQL = WxResponseInfoSchema._UpdateAllSQL;
		FillAllSQL = WxResponseInfoSchema._FillAllSQL;
		DeleteSQL = WxResponseInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new WxResponseInfoSet();
	}

	public boolean add(WxResponseInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(WxResponseInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(WxResponseInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public WxResponseInfoSchema get(int index) {
		WxResponseInfoSchema tSchema = (WxResponseInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, WxResponseInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(WxResponseInfoSet aSet) {
		return super.set(aSet);
	}
}
 