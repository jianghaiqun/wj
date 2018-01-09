package com.sinosoft.schema;

import com.sinosoft.schema.FMCalModeSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class FMCalModeSet extends SchemaSet {
	public FMCalModeSet() {
		this(10,0);
	}

	public FMCalModeSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public FMCalModeSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = FMCalModeSchema._TableCode;
		Columns = FMCalModeSchema._Columns;
		NameSpace = FMCalModeSchema._NameSpace;
		InsertAllSQL = FMCalModeSchema._InsertAllSQL;
		UpdateAllSQL = FMCalModeSchema._UpdateAllSQL;
		FillAllSQL = FMCalModeSchema._FillAllSQL;
		DeleteSQL = FMCalModeSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new FMCalModeSet();
	}

	public boolean add(FMCalModeSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(FMCalModeSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(FMCalModeSchema aSchema) {
		return super.remove(aSchema);
	}

	public FMCalModeSchema get(int index) {
		FMCalModeSchema tSchema = (FMCalModeSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, FMCalModeSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(FMCalModeSet aSet) {
		return super.set(aSet);
	}
}
 