package com.sinosoft.schema;

import com.sinosoft.schema.FMBlackListSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class FMBlackListSet extends SchemaSet {
	public FMBlackListSet() {
		this(10,0);
	}

	public FMBlackListSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public FMBlackListSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = FMBlackListSchema._TableCode;
		Columns = FMBlackListSchema._Columns;
		NameSpace = FMBlackListSchema._NameSpace;
		InsertAllSQL = FMBlackListSchema._InsertAllSQL;
		UpdateAllSQL = FMBlackListSchema._UpdateAllSQL;
		FillAllSQL = FMBlackListSchema._FillAllSQL;
		DeleteSQL = FMBlackListSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new FMBlackListSet();
	}

	public boolean add(FMBlackListSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(FMBlackListSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(FMBlackListSchema aSchema) {
		return super.remove(aSchema);
	}

	public FMBlackListSchema get(int index) {
		FMBlackListSchema tSchema = (FMBlackListSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, FMBlackListSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(FMBlackListSet aSet) {
		return super.set(aSet);
	}
}
 