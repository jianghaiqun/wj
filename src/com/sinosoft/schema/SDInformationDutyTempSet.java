package com.sinosoft.schema;

import com.sinosoft.schema.SDInformationDutyTempSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDInformationDutyTempSet extends SchemaSet {
	public SDInformationDutyTempSet() { 
		this(10,0);
	}

	public SDInformationDutyTempSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDInformationDutyTempSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDInformationDutyTempSchema._TableCode;
		Columns = SDInformationDutyTempSchema._Columns;
		NameSpace = SDInformationDutyTempSchema._NameSpace;
		InsertAllSQL = SDInformationDutyTempSchema._InsertAllSQL;
		UpdateAllSQL = SDInformationDutyTempSchema._UpdateAllSQL;
		FillAllSQL = SDInformationDutyTempSchema._FillAllSQL;
		DeleteSQL = SDInformationDutyTempSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDInformationDutyTempSet();
	}

	public boolean add(SDInformationDutyTempSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDInformationDutyTempSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDInformationDutyTempSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDInformationDutyTempSchema get(int index) {
		SDInformationDutyTempSchema tSchema = (SDInformationDutyTempSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDInformationDutyTempSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDInformationDutyTempSet aSet) {
		return super.set(aSet);
	}
}
 