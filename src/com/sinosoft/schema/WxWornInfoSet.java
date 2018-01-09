package com.sinosoft.schema;

import com.sinosoft.schema.WxWornInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class WxWornInfoSet extends SchemaSet {
	public WxWornInfoSet() {
		this(10,0);
	}

	public WxWornInfoSet(int initialCapacity) {
		this(initialCapacity,0); 
	}

	public WxWornInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = WxWornInfoSchema._TableCode;
		Columns = WxWornInfoSchema._Columns;
		NameSpace = WxWornInfoSchema._NameSpace;
		InsertAllSQL = WxWornInfoSchema._InsertAllSQL;
		UpdateAllSQL = WxWornInfoSchema._UpdateAllSQL;
		FillAllSQL = WxWornInfoSchema._FillAllSQL;
		DeleteSQL = WxWornInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new WxWornInfoSet();
	}

	public boolean add(WxWornInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(WxWornInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(WxWornInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public WxWornInfoSchema get(int index) {
		WxWornInfoSchema tSchema = (WxWornInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, WxWornInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(WxWornInfoSet aSet) {
		return super.set(aSet);
	}
}
 