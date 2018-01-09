package com.sinosoft.schema;

import com.sinosoft.schema.WeixinPlatformRecordSchema;
import com.sinosoft.framework.orm.SchemaSet;

@SuppressWarnings("serial")
public class WeixinPlatformRecordSet extends SchemaSet {
	public WeixinPlatformRecordSet() {
		this(10,0);
	}

	public WeixinPlatformRecordSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public WeixinPlatformRecordSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = WeixinPlatformRecordSchema._TableCode;
		Columns = WeixinPlatformRecordSchema._Columns;
		NameSpace = WeixinPlatformRecordSchema._NameSpace;
		InsertAllSQL = WeixinPlatformRecordSchema._InsertAllSQL;
		UpdateAllSQL = WeixinPlatformRecordSchema._UpdateAllSQL;
		FillAllSQL = WeixinPlatformRecordSchema._FillAllSQL;
		DeleteSQL = WeixinPlatformRecordSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new WeixinPlatformRecordSet();
	}

	public boolean add(WeixinPlatformRecordSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(WeixinPlatformRecordSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(WeixinPlatformRecordSchema aSchema) {
		return super.remove(aSchema);
	}

	public WeixinPlatformRecordSchema get(int index) {
		WeixinPlatformRecordSchema tSchema = (WeixinPlatformRecordSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, WeixinPlatformRecordSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(WeixinPlatformRecordSet aSet) {
		return super.set(aSet);
	}
}
 