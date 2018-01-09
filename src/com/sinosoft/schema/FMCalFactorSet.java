package com.sinosoft.schema;

import com.sinosoft.schema.FMCalFactorSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class FMCalFactorSet extends SchemaSet {
	public FMCalFactorSet() {
		this(10,0);
	}

	public FMCalFactorSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public FMCalFactorSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = FMCalFactorSchema._TableCode;
		Columns = FMCalFactorSchema._Columns;
		NameSpace = FMCalFactorSchema._NameSpace;
		InsertAllSQL = FMCalFactorSchema._InsertAllSQL;
		UpdateAllSQL = FMCalFactorSchema._UpdateAllSQL;
		FillAllSQL = FMCalFactorSchema._FillAllSQL;
		DeleteSQL = FMCalFactorSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new FMCalFactorSet();
	}

	public boolean add(FMCalFactorSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(FMCalFactorSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(FMCalFactorSchema aSchema) {
		return super.remove(aSchema);
	}

	public FMCalFactorSchema get(int index) {
		FMCalFactorSchema tSchema = (FMCalFactorSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, FMCalFactorSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(FMCalFactorSet aSet) {
		return super.set(aSet);
	}
}
 