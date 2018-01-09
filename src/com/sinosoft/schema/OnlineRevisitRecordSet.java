package com.sinosoft.schema;

import com.sinosoft.schema.OnlineRevisitRecordSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class OnlineRevisitRecordSet extends SchemaSet {
	public OnlineRevisitRecordSet() {
		this(10,0);
	}

	public OnlineRevisitRecordSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public OnlineRevisitRecordSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = OnlineRevisitRecordSchema._TableCode;
		Columns = OnlineRevisitRecordSchema._Columns;
		NameSpace = OnlineRevisitRecordSchema._NameSpace;
		InsertAllSQL = OnlineRevisitRecordSchema._InsertAllSQL;
		UpdateAllSQL = OnlineRevisitRecordSchema._UpdateAllSQL;
		FillAllSQL = OnlineRevisitRecordSchema._FillAllSQL;
		DeleteSQL = OnlineRevisitRecordSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new OnlineRevisitRecordSet();
	}

	public boolean add(OnlineRevisitRecordSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(OnlineRevisitRecordSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(OnlineRevisitRecordSchema aSchema) {
		return super.remove(aSchema);
	}

	public OnlineRevisitRecordSchema get(int index) {
		OnlineRevisitRecordSchema tSchema = (OnlineRevisitRecordSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, OnlineRevisitRecordSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(OnlineRevisitRecordSet aSet) {
		return super.set(aSet);
	}
}
