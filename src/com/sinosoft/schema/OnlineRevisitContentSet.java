package com.sinosoft.schema;

import com.sinosoft.schema.OnlineRevisitContentSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class OnlineRevisitContentSet extends SchemaSet {
	public OnlineRevisitContentSet() {
		this(10,0);
	}

	public OnlineRevisitContentSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public OnlineRevisitContentSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = OnlineRevisitContentSchema._TableCode;
		Columns = OnlineRevisitContentSchema._Columns;
		NameSpace = OnlineRevisitContentSchema._NameSpace;
		InsertAllSQL = OnlineRevisitContentSchema._InsertAllSQL;
		UpdateAllSQL = OnlineRevisitContentSchema._UpdateAllSQL;
		FillAllSQL = OnlineRevisitContentSchema._FillAllSQL;
		DeleteSQL = OnlineRevisitContentSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new OnlineRevisitContentSet();
	}

	public boolean add(OnlineRevisitContentSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(OnlineRevisitContentSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(OnlineRevisitContentSchema aSchema) {
		return super.remove(aSchema);
	}

	public OnlineRevisitContentSchema get(int index) {
		OnlineRevisitContentSchema tSchema = (OnlineRevisitContentSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, OnlineRevisitContentSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(OnlineRevisitContentSet aSet) {
		return super.set(aSet);
	}
}
