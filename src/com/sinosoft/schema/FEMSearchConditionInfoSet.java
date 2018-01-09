package com.sinosoft.schema;

import com.sinosoft.schema.FEMSearchConditionInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class FEMSearchConditionInfoSet extends SchemaSet {
	public FEMSearchConditionInfoSet() {
		this(10,0);
	}

	public FEMSearchConditionInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public FEMSearchConditionInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = FEMSearchConditionInfoSchema._TableCode;
		Columns = FEMSearchConditionInfoSchema._Columns;
		NameSpace = FEMSearchConditionInfoSchema._NameSpace;
		InsertAllSQL = FEMSearchConditionInfoSchema._InsertAllSQL;
		UpdateAllSQL = FEMSearchConditionInfoSchema._UpdateAllSQL;
		FillAllSQL = FEMSearchConditionInfoSchema._FillAllSQL;
		DeleteSQL = FEMSearchConditionInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new FEMSearchConditionInfoSet();
	}

	public boolean add(FEMSearchConditionInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(FEMSearchConditionInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(FEMSearchConditionInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public FEMSearchConditionInfoSchema get(int index) {
		FEMSearchConditionInfoSchema tSchema = (FEMSearchConditionInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, FEMSearchConditionInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(FEMSearchConditionInfoSet aSet) {
		return super.set(aSet);
	}
}
 