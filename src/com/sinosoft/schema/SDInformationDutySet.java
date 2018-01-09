package com.sinosoft.schema;

import com.sinosoft.schema.SDInformationDutySchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDInformationDutySet extends SchemaSet {
	public SDInformationDutySet() {
		this(10,0);  
	} 

	public SDInformationDutySet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDInformationDutySet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDInformationDutySchema._TableCode;
		Columns = SDInformationDutySchema._Columns;
		NameSpace = SDInformationDutySchema._NameSpace;
		InsertAllSQL = SDInformationDutySchema._InsertAllSQL;
		UpdateAllSQL = SDInformationDutySchema._UpdateAllSQL;
		FillAllSQL = SDInformationDutySchema._FillAllSQL;
		DeleteSQL = SDInformationDutySchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDInformationDutySet();
	}

	public boolean add(SDInformationDutySchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDInformationDutySet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDInformationDutySchema aSchema) {
		return super.remove(aSchema);
	}

	public SDInformationDutySchema get(int index) {
		SDInformationDutySchema tSchema = (SDInformationDutySchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDInformationDutySchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDInformationDutySet aSet) {
		return super.set(aSet);
	}
}
 