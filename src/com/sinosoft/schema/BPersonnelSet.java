package com.sinosoft.schema;

import com.sinosoft.framework.orm.SchemaSet;

public class BPersonnelSet extends SchemaSet {
	public BPersonnelSet() {
		this(11,0);
	}

	public BPersonnelSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BPersonnelSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BPersonnelSchema._TableCode;
		Columns = BPersonnelSchema._Columns;
		NameSpace = BPersonnelSchema._NameSpace;
		InsertAllSQL = BPersonnelSchema._InsertAllSQL;
		UpdateAllSQL = BPersonnelSchema._UpdateAllSQL;
		FillAllSQL = BPersonnelSchema._FillAllSQL;
		DeleteSQL = BPersonnelSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BPersonnelSet();
	}

	public boolean add(BPersonnelSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BPersonnelSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BPersonnelSchema aSchema) {
		return super.remove(aSchema);
	}

	public BPersonnelSchema get(int index) {
		BPersonnelSchema tSchema = (BPersonnelSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BPersonnelSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BPersonnelSet aSet) {
		return super.set(aSet);
	}
}
 