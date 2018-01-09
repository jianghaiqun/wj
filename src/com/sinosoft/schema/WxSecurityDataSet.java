package com.sinosoft.schema;

import com.sinosoft.schema.WxSecurityDataSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class WxSecurityDataSet extends SchemaSet {
	public WxSecurityDataSet() {
		this(10,0);
	}

	public WxSecurityDataSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public WxSecurityDataSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = WxSecurityDataSchema._TableCode;
		Columns = WxSecurityDataSchema._Columns;
		NameSpace = WxSecurityDataSchema._NameSpace;
		InsertAllSQL = WxSecurityDataSchema._InsertAllSQL;
		UpdateAllSQL = WxSecurityDataSchema._UpdateAllSQL;
		FillAllSQL = WxSecurityDataSchema._FillAllSQL;
		DeleteSQL = WxSecurityDataSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new WxSecurityDataSet();
	}

	public boolean add(WxSecurityDataSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(WxSecurityDataSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(WxSecurityDataSchema aSchema) {
		return super.remove(aSchema);
	}

	public WxSecurityDataSchema get(int index) {
		WxSecurityDataSchema tSchema = (WxSecurityDataSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, WxSecurityDataSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(WxSecurityDataSet aSet) {
		return super.set(aSet);
	}
}
 