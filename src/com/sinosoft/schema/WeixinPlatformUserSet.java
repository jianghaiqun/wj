package com.sinosoft.schema;

import com.sinosoft.schema.WeixinPlatformUserSchema;
import com.sinosoft.framework.orm.SchemaSet;

@SuppressWarnings("serial")
public class WeixinPlatformUserSet extends SchemaSet {
	public WeixinPlatformUserSet() {
		this(10,0);
	}

	public WeixinPlatformUserSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public WeixinPlatformUserSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = WeixinPlatformUserSchema._TableCode;
		Columns = WeixinPlatformUserSchema._Columns;
		NameSpace = WeixinPlatformUserSchema._NameSpace;
		InsertAllSQL = WeixinPlatformUserSchema._InsertAllSQL;
		UpdateAllSQL = WeixinPlatformUserSchema._UpdateAllSQL;
		FillAllSQL = WeixinPlatformUserSchema._FillAllSQL;
		DeleteSQL = WeixinPlatformUserSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new WeixinPlatformUserSet();
	}

	public boolean add(WeixinPlatformUserSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(WeixinPlatformUserSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(WeixinPlatformUserSchema aSchema) {
		return super.remove(aSchema);
	}

	public WeixinPlatformUserSchema get(int index) {
		WeixinPlatformUserSchema tSchema = (WeixinPlatformUserSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, WeixinPlatformUserSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(WeixinPlatformUserSet aSet) {
		return super.set(aSet);
	}
}
 