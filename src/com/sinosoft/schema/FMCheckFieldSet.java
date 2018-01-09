package com.sinosoft.schema;

import com.sinosoft.schema.FMCheckFieldSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class FMCheckFieldSet extends SchemaSet {
	public FMCheckFieldSet() {
		this(10,0);
	}

	public FMCheckFieldSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public FMCheckFieldSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = FMCheckFieldSchema._TableCode;
		Columns = FMCheckFieldSchema._Columns;
		NameSpace = FMCheckFieldSchema._NameSpace;
		InsertAllSQL = FMCheckFieldSchema._InsertAllSQL;
		UpdateAllSQL = FMCheckFieldSchema._UpdateAllSQL;
		FillAllSQL = FMCheckFieldSchema._FillAllSQL;
		DeleteSQL = FMCheckFieldSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new FMCheckFieldSet();
	}

	public boolean add(FMCheckFieldSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(FMCheckFieldSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(FMCheckFieldSchema aSchema) {
		return super.remove(aSchema);
	}

	public FMCheckFieldSchema get(int index) {
		FMCheckFieldSchema tSchema = (FMCheckFieldSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, FMCheckFieldSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(FMCheckFieldSet aSet) {
		return super.set(aSet);
	}
}
 