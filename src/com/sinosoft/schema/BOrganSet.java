package com.sinosoft.schema;

import com.sinosoft.framework.orm.SchemaSet;

public class BOrganSet extends SchemaSet {
	public BOrganSet() {
		this(10,0);
	}

	public BOrganSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BOrganSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BOrganSchema._TableCode;
		Columns = BOrganSchema._Columns;
		NameSpace = BOrganSchema._NameSpace;
		InsertAllSQL = BOrganSchema._InsertAllSQL;
		UpdateAllSQL = BOrganSchema._UpdateAllSQL;
		FillAllSQL = BOrganSchema._FillAllSQL;
		DeleteSQL = BOrganSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BOrganSet();
	}

	public boolean add(BOrganSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BOrganSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BOrganSchema aSchema) {
		return super.remove(aSchema);
	}

	public BOrganSchema get(int index) {
		BOrganSchema tSchema = (BOrganSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BOrganSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BOrganSet aSet) {
		return super.set(aSet);
	}
}
 