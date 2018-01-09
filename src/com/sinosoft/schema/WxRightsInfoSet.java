package com.sinosoft.schema;

import com.sinosoft.schema.WxRightsInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class WxRightsInfoSet extends SchemaSet {
	public WxRightsInfoSet() {
		this(10,0);
	}

	public WxRightsInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public WxRightsInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = WxRightsInfoSchema._TableCode;
		Columns = WxRightsInfoSchema._Columns;
		NameSpace = WxRightsInfoSchema._NameSpace;
		InsertAllSQL = WxRightsInfoSchema._InsertAllSQL;
		UpdateAllSQL = WxRightsInfoSchema._UpdateAllSQL;
		FillAllSQL = WxRightsInfoSchema._FillAllSQL;
		DeleteSQL = WxRightsInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new WxRightsInfoSet();
	}

	public boolean add(WxRightsInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(WxRightsInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(WxRightsInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public WxRightsInfoSchema get(int index) {
		WxRightsInfoSchema tSchema = (WxRightsInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, WxRightsInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(WxRightsInfoSet aSet) {
		return super.set(aSet);
	}
}
 