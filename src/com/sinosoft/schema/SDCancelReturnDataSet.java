package com.sinosoft.schema;

import com.sinosoft.schema.SDCancelReturnDataSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDCancelReturnDataSet extends SchemaSet {
	public SDCancelReturnDataSet() {
		this(10,0);
	}

	public SDCancelReturnDataSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDCancelReturnDataSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDCancelReturnDataSchema._TableCode;
		Columns = SDCancelReturnDataSchema._Columns;
		NameSpace = SDCancelReturnDataSchema._NameSpace;
		InsertAllSQL = SDCancelReturnDataSchema._InsertAllSQL;
		UpdateAllSQL = SDCancelReturnDataSchema._UpdateAllSQL;
		FillAllSQL = SDCancelReturnDataSchema._FillAllSQL;
		DeleteSQL = SDCancelReturnDataSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDCancelReturnDataSet();
	}

	public boolean add(SDCancelReturnDataSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDCancelReturnDataSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDCancelReturnDataSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDCancelReturnDataSchema get(int index) {
		SDCancelReturnDataSchema tSchema = (SDCancelReturnDataSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDCancelReturnDataSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDCancelReturnDataSet aSet) {
		return super.set(aSet);
	}
}
 