package com.sinosoft.schema;

import com.sinosoft.schema.FMAppntBlackListSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class FMAppntBlackListSet extends SchemaSet {
	public FMAppntBlackListSet() {
		this(10,0);
	}

	public FMAppntBlackListSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public FMAppntBlackListSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = FMAppntBlackListSchema._TableCode;
		Columns = FMAppntBlackListSchema._Columns;
		NameSpace = FMAppntBlackListSchema._NameSpace;
		InsertAllSQL = FMAppntBlackListSchema._InsertAllSQL;
		UpdateAllSQL = FMAppntBlackListSchema._UpdateAllSQL;
		FillAllSQL = FMAppntBlackListSchema._FillAllSQL;
		DeleteSQL = FMAppntBlackListSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new FMAppntBlackListSet();
	}

	public boolean add(FMAppntBlackListSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(FMAppntBlackListSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(FMAppntBlackListSchema aSchema) {
		return super.remove(aSchema);
	}

	public FMAppntBlackListSchema get(int index) {
		FMAppntBlackListSchema tSchema = (FMAppntBlackListSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, FMAppntBlackListSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(FMAppntBlackListSet aSet) {
		return super.set(aSet);
	}
}
 