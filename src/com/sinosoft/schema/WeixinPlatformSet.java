package com.sinosoft.schema;

import com.sinosoft.schema.WeixinPlatformSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class WeixinPlatformSet extends SchemaSet {
	public WeixinPlatformSet() {
		this(10,0);
	}

	public WeixinPlatformSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public WeixinPlatformSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = WeixinPlatformSchema._TableCode;
		Columns = WeixinPlatformSchema._Columns;
		NameSpace = WeixinPlatformSchema._NameSpace;
		InsertAllSQL = WeixinPlatformSchema._InsertAllSQL;
		UpdateAllSQL = WeixinPlatformSchema._UpdateAllSQL;
		FillAllSQL = WeixinPlatformSchema._FillAllSQL;
		DeleteSQL = WeixinPlatformSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new WeixinPlatformSet();
	}

	public boolean add(WeixinPlatformSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(WeixinPlatformSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(WeixinPlatformSchema aSchema) {
		return super.remove(aSchema);
	}

	public WeixinPlatformSchema get(int index) {
		WeixinPlatformSchema tSchema = (WeixinPlatformSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, WeixinPlatformSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(WeixinPlatformSet aSet) {
		return super.set(aSet);
	}
}
 