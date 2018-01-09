package com.sinosoft.schema;

import com.sinosoft.schema.SDProductDutySchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDProductDutySet extends SchemaSet {
	public SDProductDutySet() {
		this(10,0);
	}

	public SDProductDutySet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDProductDutySet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDProductDutySchema._TableCode;
		Columns = SDProductDutySchema._Columns;
		NameSpace = SDProductDutySchema._NameSpace;
		InsertAllSQL = SDProductDutySchema._InsertAllSQL;
		UpdateAllSQL = SDProductDutySchema._UpdateAllSQL;
		FillAllSQL = SDProductDutySchema._FillAllSQL;
		DeleteSQL = SDProductDutySchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDProductDutySet();
	}

	public boolean add(SDProductDutySchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDProductDutySet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDProductDutySchema aSchema) {
		return super.remove(aSchema);
	}

	public SDProductDutySchema get(int index) {
		SDProductDutySchema tSchema = (SDProductDutySchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDProductDutySchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDProductDutySet aSet) {
		return super.set(aSet);
	}
}
 